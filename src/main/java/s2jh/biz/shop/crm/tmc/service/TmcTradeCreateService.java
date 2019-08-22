package s2jh.biz.shop.crm.tmc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.domain.Trade;

import lab.s2jh.core.handler.impl.DefaultHandlerChain;
import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.tmc.entity.TmcMessages;
import s2jh.biz.shop.crm.tmc.manage.TmcDistributeService;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;
import s2jh.biz.shop.crm.user.entity.UserInfo;

/** 
* @author wy
* @version 创建时间：2017年8月31日 上午11:17:27
*/
@Service
public class TmcTradeCreateService {
	
	@Resource(name="tradeCreateChain")
	private DefaultHandlerChain tradeCreateChain;
	
	@Resource(name="tradeFirstPaymentChain")
	private DefaultHandlerChain tradeFirstPaymentChain;
	
	@Resource(name="tradeSecondPaymentChain")
	private DefaultHandlerChain tradeSecondPaymentChain;
	
	@Resource(name="tradeDiscountPaymentChain")
	private DefaultHandlerChain tradeDiscountPaymentChain;
	
	@Autowired
	private TransactionOrderService transactionOrderService;
	
	@Autowired
	private SendSmsService sendSmsService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(TmcTradeCreateService.class);
	public void doHandle(JSONObject content) throws InterruptedException{
		String tid = content.getString("tid");
		String sellerNick = content.getString("seller_nick");
		UserInfo user = this.judgeUserUtil.isNormalUser(sellerNick);
		if(user == null){
			this.logger.debug("用户状态异常,"+tid + " 内容为："+content);
			return ;
		}
		Trade trade = transactionOrderService.queryTrade(tid);
		if (trade == null) {
			String sessionKey = judgeUserUtil.getUserTokenByRedis(sellerNick);
			trade = TaoBaoClientUtil.getTradeByTaoBaoAPI(Long.parseLong(tid),sessionKey);
		}
		if(trade==null){
			this.logger.debug("订单查询为空, 内容为："+content);
			return;
		}
		TmcMessages tmcMessage = new TmcMessages();
		tmcMessage.setUser(user);
		tmcMessage.setTid(Long.parseLong(tid));
		tmcMessage.setTrade(trade);
		long start = System.currentTimeMillis();
		try {
			//下单关怀
			this.tradeCreate(sellerNick,tmcMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("下单关怀花费时间过长,时间为：" + (end-start) +"s,"+content);
			}
		}
		String status = trade.getStatus();
		String stepTradeStatus = trade.getStepTradeStatus();
		if(ValidateUtil.isNotNull(stepTradeStatus)){
		    if(!TradesInfo.FRONT_NOPAID_FINAL_NOPAID.equals(stepTradeStatus)){
		        this.logger.debug("预售订单，不催付, 内容为："+content);
		        return ;
		    }
		}
		if (!(status.equals(TradesInfo.WAIT_BUYER_PAY)
				|| status.equals(TradesInfo.PAY_PENDING)
				|| status.equals(TradesInfo.TRADE_NO_CREATE_PAY))) {
			return ; //状态必须是等待付款的状态
		}
		start = System.currentTimeMillis();
		try {
			//常规催付
			this.tradeFirstPayment(sellerNick, tmcMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("常规催付花费时间过长,时间为：" + (end-start)+"s,"+content);
			}
		}
		start = System.currentTimeMillis();
		try {
			//二次催付
			this.tradeSecondPayment(sellerNick, tmcMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("二次催付花费时间过长,时间为：" + (end-start)+"s,"+content);
			}
		}
		start = System.currentTimeMillis();
		try {
			//聚划算催付
			this.tradeDiscountPayment(sellerNick, tmcMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("聚划算催付花费时间过长,时间为：" + (end-start)+"s,"+ content);
			}
		}
	}
	/**
	 * 开始执行下单关怀逻辑
	 * @author: wy
	 * @time: 2017年9月4日 上午11:46:16
	 * @param sellerNick 卖家昵称
	 * @param tmcMessage 消息流程判断中间类
	 */
	private void tradeCreate(String sellerNick,TmcMessages tmcMessages){
		if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
			this.logger.debug("订单关怀参数异常, "+tmcMessages.getTid() );
			return;
		}
		Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.CREATE_ORDER); 
		List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
		if(ValidateUtil.isEmpty(list)){
			this.logger.debug("下单关怀未开启,tid: " + tmcMessages.getTid());
			return ;
		}
		tmcMessages.setFlag(false);
		tmcMessages.setSettingType(OrderSettingInfo.CREATE_ORDER);
		tmcMessages.setSendSchedule(false);
		Map<String,Object> map = new HashMap<String,Object>(5);
		map.put("tmcMessages", tmcMessages);
		this.logger.debug("下单关怀开始处理  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
		for (TradeSetup tradeSetup : list) {
			try {
				if(!tradeSetup.getStatus()){
					continue;
				}
				tmcMessages.setFlag(true);
				tmcMessages.setTradeSetup(tradeSetup);
				tmcMessages.setSendTime(new Date());
				this.tradeCreateChain.doHandle(map);
				this.logger.debug("下单关怀流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
				if(tmcMessages.getFlag()){
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(tmcMessages.getFlag()){
			this.sendSmsService.doHandle(tmcMessages);
		}
	}
	/**
	 * 执行常规催付判断
	 * @author: wy
	 * @time: 2017年9月5日 上午11:34:30
	 * @param sellerNick 卖家昵称
	 * @param tmcMessages 消息判断中间类
	 */
	private void tradeFirstPayment(String sellerNick,TmcMessages tmcMessages){
		if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
			this.logger.debug("常规催付关怀参数异常, "+tmcMessages.getTid() );
			return;
		}
		if(tmcMessages.getTrade().getTradeFrom().contains(TradesInfo.ORDER_FROM_JHS)){ 
			//常规催付不针对聚划算订单
			return;
		}
		Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.FIRST_PUSH_PAYMENT); 
		List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
		if(ValidateUtil.isEmpty(list)){
			this.logger.debug("常规催付未开启,tid: " + tmcMessages.getTid());
			return ;
		}
		tmcMessages.setFlag(false);
		tmcMessages.setSettingType(OrderSettingInfo.FIRST_PUSH_PAYMENT);
		tmcMessages.setSendSchedule(true);
		Map<String,Object> map = new HashMap<String,Object>(5);
		map.put("tmcMessages", tmcMessages);
		this.logger.debug("常规催付开始判断  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
		for (TradeSetup tradeSetup : list) {
			try {
				if(!tradeSetup.getStatus()){
					continue;
				}
				tmcMessages.setSendTime(tmcMessages.getTrade().getCreated());

				this.logger.debug("常规催付流程处理完，tid: " + tmcMessages.getTid()+ "处理时间:"+tmcMessages.getTrade().getCreated());
				tmcMessages.setFlag(true);
				tmcMessages.setTradeSetup(tradeSetup);
				this.tradeFirstPaymentChain.doHandle(map);
				this.logger.debug("常规催付流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
				if(tmcMessages.getFlag()){
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(tmcMessages.getFlag()){
			this.sendSmsService.doHandle(tmcMessages);
		}
	}
	/**
	 * 二次催付
	 * @author: wy
	 * @time: 2017年9月7日 下午3:06:17
	 * @param sellerNick
	 * @param tmcMessages 
	 */
	private void tradeSecondPayment(String sellerNick,TmcMessages tmcMessages){
		if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
			this.logger.debug("二次催付参数异常, "+tmcMessages.getTid() );
			return;
		}
		if(tmcMessages.getTrade().getTradeFrom().contains(TradesInfo.ORDER_FROM_JHS)){ 
			//二次催付不针对聚划算订单
			return;
		}
		Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.SECOND_PUSH_PAYMENT); 
		List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
		if(ValidateUtil.isEmpty(list)){
			this.logger.debug("二次催付未开启,tid: " + tmcMessages.getTid());
			return ;
		}
		tmcMessages.setFlag(false);
		tmcMessages.setSettingType(OrderSettingInfo.SECOND_PUSH_PAYMENT);
		tmcMessages.setSendSchedule(true);
		//二次催付的开始时间未  第一次催付的短信的发送时间
		Date startDate = this.smsSendInfoScheduleService.findByTidAndType(tmcMessages.getTid(), OrderSettingInfo.FIRST_PUSH_PAYMENT);
		// 获取订单创建时间
		if(startDate==null){
			this.logger.debug("二次催付开始时间未能获取，二次催付无法执行, "+tmcMessages.getTid() );
			return ;
		}
		Map<String,Object> map = new HashMap<String,Object>(5);
		map.put("tmcMessages", tmcMessages);
		this.logger.debug("二次催付开始逻辑判断 " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
		for (TradeSetup tradeSetup : list) {
			try {
				if(!tradeSetup.getStatus()){
					continue;
				}
				tmcMessages.setSendTime(startDate);
				tmcMessages.setFlag(true);
				tmcMessages.setTradeSetup(tradeSetup);
				this.tradeSecondPaymentChain.doHandle(map);
				this.logger.debug("二次催付流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(tmcMessages.getFlag()){
			this.sendSmsService.doHandle(tmcMessages);
		}
	}
	/**
	 * 聚划算催付
	 * @author: wy
	 * @time: 2017年9月5日 下午2:02:30
	 * @param sellerNick
	 * @param tmcMessages
	 */
	private void tradeDiscountPayment(String sellerNick,TmcMessages tmcMessages){
		if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
			this.logger.debug("聚划算催付参数异常, "+tmcMessages.getTid() );
			return;
		}
		if(!tmcMessages.getTrade().getTradeFrom().contains(TradesInfo.ORDER_FROM_JHS)){ 
			//聚划算催付来源必须含有聚划算
			return;
		}
		Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT); 
		List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
		if(ValidateUtil.isEmpty(list)){
			this.logger.debug("聚划算催付未开启,tid: "+tmcMessages.getTid() );
			return ;
		}
		tmcMessages.setFlag(false);
		tmcMessages.setSettingType(OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT);
		tmcMessages.setSendSchedule(true);
		Map<String,Object> map = new HashMap<String,Object>(5);
		map.put("tmcMessages", tmcMessages);
		this.logger.debug("聚划算催付开始逻辑判断, "+tmcMessages.getTid() +" ，用户设置了"+list.size()+"个任务");
		for (TradeSetup tradeSetup : list) {
			try {
				if(!tradeSetup.getStatus()){
					continue;
				}
				tmcMessages.setSendTime(tmcMessages.getTrade().getCreated());
				//聚划算的开始时间为订单的创建时间
				tmcMessages.setFlag(true);
				tmcMessages.setTradeSetup(tradeSetup);
				this.tradeDiscountPaymentChain.doHandle(map);
				this.logger.debug("聚划算催付流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(tmcMessages.getFlag()){
			this.sendSmsService.doHandle(tmcMessages);
		}
	}
}
