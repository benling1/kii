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
 * 付款tmc
* @author wy
* @version 创建时间：2017年9月13日 上午11:10:00
*/
@Service
public class TmcTradeTradeBuyerPayService {
	
	@Resource(name="tradeBuyerPayChain")
	private DefaultHandlerChain tradeBuyerPayChain;
	
	@Resource(name="tradeDelaySendGoodsChain")
	private DefaultHandlerChain tradeDelaySendGoodsChain;
	
	@Autowired
	private TransactionOrderService transactionOrderService;
	
	@Autowired
	private SendSmsService sendSmsService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(TmcTradeTradeBuyerPayService.class);
	
	public void doHandle(JSONObject content) throws Exception{
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
		//发送时间是付款时间
        if(trade.getPayTime()==null){
            trade.setPayTime(new Date());
        }
		try {
			//付款关怀
			this.buyerPayment(sellerNick,tmcMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("付款关怀花费时间过长,时间为：" + (end-start) +"s,"+content);
			}
		}
		String status = trade.getStatus();
		//等待卖家发货
		if(!(TradesInfo.WAIT_SELLER_SEND_GOODS.equals(status) || TradesInfo.WAIT_BUYER_PAY.equals(status))){
			this.logger.debug("卖家已发货，不需要进行卖家发货提醒,当前订单状态为："+status+", 内容为："+content);
			return ;
		}
		start = System.currentTimeMillis();
		try {
			//延迟发货关怀
			this.delaySendGoods(sellerNick,tmcMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("付款关怀花费时间过长,时间为：" + (end-start) +"s,"+content);
			}
		}
	}
	
	/**
	 * 付款关怀逻辑
	 * @author: wy
	 * @time: 2017年9月13日 上午11:29:22
	 * @param sellerNick
	 * @param tmcMessages
	 */
	private void buyerPayment(String sellerNick,TmcMessages tmcMessages){
		if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
			this.logger.debug("付款关怀参数异常, "+tmcMessages.getTid() );
			return;
		}
		Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.PAYMENT_CINCERN); 
		List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
		if(ValidateUtil.isEmpty(list)){
			this.logger.debug("付款关怀未开启,tid: " + tmcMessages.getTid());
			return ;
		}
		tmcMessages.setFlag(false);
		tmcMessages.setSettingType(OrderSettingInfo.PAYMENT_CINCERN);
		tmcMessages.setSendSchedule(false);
		Map<String,Object> map = new HashMap<String,Object>(5);
		map.put("tmcMessages", tmcMessages);
		this.logger.debug("付款关怀开始  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
		for (TradeSetup tradeSetup : list) {
			try {
				if(!tradeSetup.getStatus()){
					continue;
				}
				tmcMessages.setSendTime(tmcMessages.getTrade().getPayTime());
				tmcMessages.setFlag(true);
				tmcMessages.setTradeSetup(tradeSetup);
				this.tradeBuyerPayChain.doHandle(map);
				this.logger.debug("付款关怀处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
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
	 * 延迟发货
	 * @author: wy
	 * @time: 2017年9月13日 下午12:43:04
	 * @param sellerNick
	 * @param tmcMessages
	 */
	private void delaySendGoods(String sellerNick,TmcMessages tmcMessages){
		if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
			this.logger.debug("延迟发货关怀参数异常, "+tmcMessages.getTid() );
			return;
		}
		Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.DELAY_SEND_REMIND); 
		List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
		if(ValidateUtil.isEmpty(list)){
			this.logger.debug("延迟发货未开启,tid: " + tmcMessages.getTid());
			return ;
		}
		tmcMessages.setFlag(false);
		tmcMessages.setSettingType(OrderSettingInfo.DELAY_SEND_REMIND);
		tmcMessages.setSendSchedule(true);
		Map<String,Object> map = new HashMap<String,Object>(5);
		map.put("tmcMessages", tmcMessages);
		this.logger.debug("延迟发货开始判断  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
		for (TradeSetup tradeSetup : list) {
			try {
				if(!tradeSetup.getStatus()){
					continue;
				}
				tmcMessages.setSendTime(tmcMessages.getTrade().getPayTime());
				tmcMessages.setFlag(true);
				tmcMessages.setTradeSetup(tradeSetup);
				this.tradeDelaySendGoodsChain.doHandle(map);
				this.logger.debug("延迟发货流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
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
}
