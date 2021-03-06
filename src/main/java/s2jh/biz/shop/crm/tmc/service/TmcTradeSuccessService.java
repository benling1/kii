package s2jh.biz.shop.crm.tmc.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.SecretException;
import com.taobao.api.domain.Trade;

import lab.s2jh.core.handler.impl.DefaultHandlerChain;
import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.SmsBlackListService;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.SendMessageStatusInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.tmc.entity.TmcMessages;
import s2jh.biz.shop.crm.tmc.manage.TmcDistributeService;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;

/** 
 * 订单结束确认收货
* @author wy
* @version 创建时间：2017年9月6日 上午10:43:04
*/
@Service
public class TmcTradeSuccessService {
	private Logger logger = LoggerFactory.getLogger(TmcTradeSuccessService.class);
	
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	@Autowired
	private TransactionOrderService transactionOrderService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	
	@Autowired
	private SmsBlackListService smsBlackListService;
	
	@Resource(name="tradeBuyerRatedRemindChain")
	private DefaultHandlerChain tradeBuyerRatedRemindChain;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SendSmsService sendSmsService;
	
	public void doHandle(JSONObject content) throws SecretException, InterruptedException{
		String tid = content.getString("tid");
		Long oid = content.getLong("oid");
		String sellerNick = content.getString("seller_nick");
		String buyerNick = content.getString("buyer_nick");
		UserInfo user = this.judgeUserUtil.findUserExpireTimeAndSms(sellerNick);
		if(user==null){
			this.logger.debug("用户状态异常,"+tid + " 内容为："+content);
			return ;
		}
		String sessionKey = judgeUserUtil.getUserTokenByRedis(sellerNick);
		if(EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
			buyerNick = EncrptAndDecryptClient.getInstance().decrypt(buyerNick, EncrptAndDecryptClient.SEARCH,sessionKey);
		}
		TmcMessages tmcMessage = new TmcMessages();
		tmcMessage.setUser(user);
		tmcMessage.setTid(Long.parseLong(tid));
		long start = System.currentTimeMillis();
		Trade trade = transactionOrderService.queryTrade(tid);
        if (trade == null) {
            trade = TaoBaoClientUtil.getTradeByTaoBaoAPI(Long.parseLong(tid),sessionKey);
        }
        if(trade==null){
            this.logger.debug("订单查询为空, 内容为："+content);
            return;
        }
        tmcMessage.setTrade(trade);
		try {
			//自动评价
			this.autoSellerRate(sellerNick, buyerNick, tmcMessage, oid);
		} catch (Exception e) {
			e.getMessage();
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("自动评价花费时间过长,时间为：" + (end-start) +"s,"+content);
			}
		}
		if(user.getUserAccountSms()==null){
			this.logger.debug("好评提醒短信余额为空："+content);
			return ;
		}
		if(user.getUserAccountSms()<=0){
			this.logger.debug("好评提醒短信余额不足："+content);
			this.userInfoService.doTmcUser(sellerNick, SendMessageStatusInfo.DEL_SMS);
			return ;
		}
		start = System.currentTimeMillis();
		//TODO 好评提醒
		if(trade.getBuyerRate()!=null){
			if(trade.getBuyerRate()){
				this.logger.debug("订单已评，不需要好评提醒短信   买家是否已评价： " +trade.getBuyerRate() +content );
				return ;
			}
		}
		try {
			this.buyeRateRemind(sellerNick, tmcMessage);
		} finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("好评提醒花费时间过长,时间为：" + (end-start) +"s,"+content);
			}
		}
	}
	/**
	 * 买家好评提醒
	 * @author: wy
	 * @time: 2017年9月7日 下午3:18:36
	 * @param sellerNick 卖家昵称
	 * @param tmcMessages 消息中间类
	 */
	private void buyeRateRemind(String sellerNick,TmcMessages tmcMessages){
		if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
			this.logger.debug("好评提醒参数异常, "+tmcMessages.getTid() );
			return;
		}
		Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.GOOD_VALUTION_REMIND); 
		List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
		if(ValidateUtil.isEmpty(list)){
			this.logger.debug("好评提醒未开启 " + tmcMessages.getTid());
			return ;
		}
		tmcMessages.setFlag(false);
		tmcMessages.setSettingType(OrderSettingInfo.GOOD_VALUTION_REMIND);
		tmcMessages.setSendSchedule(true);
		Map<String,Object> map = new HashMap<String,Object>(5);
		map.put("tmcMessages", tmcMessages);
		this.logger.debug("好评提醒开始处理  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
		for (TradeSetup tradeSetup : list) {
			if(!tradeSetup.getStatus()){
				continue;
			}
			tmcMessages.setFlag(true);
			tmcMessages.setTradeSetup(tradeSetup);
			this.tradeBuyerRatedRemindChain.doHandle(map);
			this.logger.debug("好评提醒处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" id:"+tmcMessages.getTradeSetup().getId());
			break;
		}
		if(tmcMessages.getFlag()){
			this.sendSmsService.doHandle(tmcMessages);
		}
	}
	/**
	 * 定时评价
	 * @author: wy
	 * @time: 2017年9月6日 下午5:43:36
	 * @param smsSendInfo 
	 */
	public void autoSellerRateBySchedule(SmsSendInfo smsSendInfo){
		boolean flag = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+smsSendInfo.getUserId()+"_"+OrderSettingInfo.AUTO_RATE);
		if(!flag){
			//查询用户是否开启对应设置
			logger.debug("定时自动评价,用户未开启对应设置  "+smsSendInfo.getUserId() + " 类型：" +smsSendInfo.getType());
			return;
		}
		String sessionKey = this.judgeUserUtil.getUserTokenByRedis(smsSendInfo.getUserId());
		TaoBaoClientUtil.autoTaobaoTraderate(smsSendInfo.getTid(), smsSendInfo.getOid(), smsSendInfo.getContent(), smsSendInfo.getRateType(),sessionKey);
	}
	/**
	 * 卖家自动评价
	 * @author: wy
	 * @throws Exception 
	 * @time: 2017年9月6日 上午11:06:43
	 */
	private void autoSellerRate(String sellerNick,String buyerNick,TmcMessages tmcMessages,Long oid) throws Exception{
		if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
			this.logger.debug("订单关怀参数异常, "+tmcMessages.getTid() );
			return;
		}
		Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.AUTO_RATE); 
		List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
		if(ValidateUtil.isEmpty(list)){
			logger.debug("自动评价，未找到对应的设置  "+tmcMessages.getTid() );
			return ;
		}
		tmcMessages.setSettingType(OrderSettingInfo.AUTO_RATE);
		//自动评价只有一个设置
		this.logger.debug("自动评价开始处理  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
		for (TradeSetup tradeSetup : list) {
			if(!tradeSetup.getStatus()){
				return;
			}
			tmcMessages.setFlag(true);
			tmcMessages.setTradeSetup(tradeSetup);
			this.doAutoRate(sellerNick, buyerNick, tmcMessages, oid);
			break;
		}
	}
	/**
	 * 执行自动评价
	 * @author: wy
	 * @time: 2017年9月6日 下午1:52:30
	 * @param sellerNick 卖家昵称
	 * @param buyerNick 买家昵称
	 * @param tmcMessages 中间类
	 * @throws SecretException 加解密异常
	 */
	private void doAutoRate(String sellerNick,String buyerNick,TmcMessages tmcMessages,Long oid) throws SecretException{
		if(ValidateUtil.isEmpty(sellerNick) || ValidateUtil.isEmpty(buyerNick) || tmcMessages==null){
			return;
		}
		if(!tmcMessages.getFlag()){
			return;
		}
		TradeSetup tradeSetup = tmcMessages.getTradeSetup();
		if(tradeSetup==null){
			return ;
		}
		boolean blackFlag = this.smsBlackListService.isExists(sellerNick, buyerNick,tmcMessages.getTrade().getReceiverMobile());
		if(blackFlag){ 
			//用户是黑名单时
			if(tradeSetup.getEvaluateBlack()!=null){
				if(tradeSetup.getEvaluateBlack()){ 
					//true:  黑名单用户不自动评价
					this.logger.debug("该用户是黑名单，不自动评价 " + tmcMessages.getTid());
				}else{
					this.sellerRateByBlackList(tmcMessages,oid);
				}
			}
			return;
		}
		this.sellerRateByNeutral(tmcMessages, oid);
		
	}
	/**
	 * 非黑名单用户时的自动评价
	 * @author: wy
	 * @time: 2017年9月6日 下午3:47:46
	 * @param tmcMessages
	 * @param oid
	 */
	private void sellerRateByNeutral(TmcMessages tmcMessages,Long oid){
		TradeSetup tradeSetup = tmcMessages.getTradeSetup();
		if(tradeSetup==null){
			return ;
		}
		String evaluateType = tradeSetup.getEvaluateType();
		if(!this.isRateType(evaluateType)){
			this.logger.debug("用户不是黑名单但评价类型有误    评价类型是："+evaluateType + "  " + tmcMessages.getTid());
			return ;
		}
		String content = tradeSetup.getSmsContent();
		if(ValidateUtil.isEmpty(content)){
			this.logger.debug("评价内容为空，无法评价 "+ tmcMessages.getTid());
			return ;
		}
		if(tradeSetup.getDelayEvaluate()!=null){ 
			if(tradeSetup.getDelayEvaluate()){
				//true:延时评价
				this.delayRate(tmcMessages,content,evaluateType, oid);
				return;
			}
		}
		String sessionKey = this.judgeUserUtil.getUserTokenByRedis(tmcMessages.getTradeSetup().getUserId());
		TaoBaoClientUtil.autoTaobaoTraderate(tmcMessages.getTid(), oid, content, evaluateType,sessionKey);
	}
	/**
	 * 黑名单时评价执行方法
	 * @author: wy
	 * @time: 2017年9月6日 下午1:54:28
	 * @param tmcMessages
	 */
	private void sellerRateByBlackList(TmcMessages tmcMessages,Long oid){
		TradeSetup tradeSetup = tmcMessages.getTradeSetup();
		if(tradeSetup==null){
			return ;
		}
		String evaluateBlackType = tradeSetup.getEvaluateBlackType();
		if(!this.isRateType(evaluateBlackType)){
			this.logger.debug("评价类型不对  " + tmcMessages.getTid());
			return ;
		}
		String evaluateBlackContent = tradeSetup.getEvaluateBlackContent();
		if(ValidateUtil.isEmpty(evaluateBlackContent)){
			this.logger.debug("无对应评价内容  " + tmcMessages.getTid());
			return ;
		}
		if(tradeSetup.getDelayEvaluate()!=null){ 
			if(tradeSetup.getDelayEvaluate()){
				//true:延时评价
				this.delayRate(tmcMessages,evaluateBlackContent,evaluateBlackType, oid);
				return;
			}
		}
		String sessionKey = this.judgeUserUtil.getUserTokenByRedis(tmcMessages.getTradeSetup().getUserId());
		TaoBaoClientUtil.autoTaobaoTraderate(tmcMessages.getTid(), oid, evaluateBlackContent, evaluateBlackType,sessionKey);
	}
	/**
	 * 延迟评价
	 * @author: wy
	 * @time: 2017年9月6日 下午3:20:47
	 * @param tmcMessages 消息中间类
	 * @param content 评价的内容
	 * @param rateType 评价的类型
	 * @param oid 子订单ID
	 */
	private void delayRate(TmcMessages tmcMessages,String content,String rateType,Long oid){
		TradeSetup tradeSetup = tmcMessages.getTradeSetup();
		if(tradeSetup==null){
			return ;
		}
		if(!tradeSetup.getDelayEvaluate()){ 
			//true:延时评价
			this.logger.debug("延迟评价无具体延迟设置  " + tmcMessages.getTid());
			return ;
		}
		if(tradeSetup.getDelayDate()==null){
			this.logger.debug("延迟评价无具体延迟设置  " + tmcMessages.getTid());
			return ;
		}
		int delayDate = tradeSetup.getDelayDate();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, delayDate);
		Date startDate = cal.getTime();
		this.smsSendInfoScheduleService.doCreateRate(tmcMessages.getTradeSetup().getUserId(), content, rateType, startDate,tmcMessages.getTid(),oid);
		this.logger.debug("创建定时评价信息成功  " + tmcMessages.getTid());
	}
	/**
	 * 字符串是否是评价的类型
	 * @author: wy
	 * @time: 2017年9月6日 下午2:00:00
	 * @param evaluateType 校验的评价类型
	 * @return true:字符串合法  false： 字符串错误
	 */
	private boolean isRateType(String evaluateType){
		if(ValidateUtil.isEmpty(evaluateType)){
			return false;
		}
		if(OrderSettingInfo.GOOD_RATE.equals(evaluateType)){
			return true;
		}else if(OrderSettingInfo.NEUTRAL_RATE.equals(evaluateType)){
			return true;
		}else if(OrderSettingInfo.BAD_RATE.equals(evaluateType)){
			return true;
		}else{
			return false;
		}
	}
	
}
