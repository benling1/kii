package s2jh.biz.shop.crm.tmc.service;

import java.text.SimpleDateFormat;
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
import lab.s2jh.core.service.RedisLockServiceImpl;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.tmc.entity.TmcMessages;
import s2jh.biz.shop.crm.tmc.manage.TmcDistributeService;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;
import s2jh.biz.shop.crm.user.entity.UserInfo;

/** 
 * 发货提醒
* @author wy
* @version 创建时间：2017年9月13日 下午1:00:47
*/
@Service
public class TmcLogisticsService {
	@Resource(name="tradeGoodsSendChain")
	private DefaultHandlerChain tradeGoodsSendChain;
	
	@Resource(name="tradeArrivalCityChain")
	private DefaultHandlerChain tradeArrivalCityChain;
	
	@Resource(name="tradeSentScanChain")
	private DefaultHandlerChain tradeSentScanChain;
	
	@Resource(name="tradeGoodsSingnedChain")
	private DefaultHandlerChain tradeGoodsSingnedChain;
	
	@Resource(name="tradeGoodsCareChain")
	private DefaultHandlerChain tradeGoodsCareChain;
	
	@Resource(name="tradeRemindTradeFinshedChain")
	private DefaultHandlerChain tradeRemindTradeFinshedChain;
	
	@Autowired
	private TransactionOrderService transactionOrderService;
	
	@Autowired
	private RedisLockServiceImpl redisLock;
	
	@Autowired
	private SendSmsService sendSmsService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(TmcLogisticsService.class);
	
	public void doHandle(JSONObject content) throws Exception{
		//'action':'CONSIGN','company_name':'顺丰','desc':'已经到达地点1','out_sid':'078869350417','tid':'193546700400400','time':'2015-05-27 16:33:04'
		String tid = content.getString("tid");
		/**action 类型：CREATE:物流订单创建, CONSIGN:卖家发货, GOT:揽收成功, ARRIVAL:进站, DEPARTURE:出站, SIGNED:签收成功, SENT_SCAN:派件扫描, FAILED:签收失败/拒签, LOST:丢失, SENT_CITY:到货城市, TO_EMS:订单转给EMS, OTHER:其他事件/操作*/
		String action = content.getString("action");
		if(action==null){
			return ;
		}
		Trade trade = transactionOrderService.queryTrade(tid);
		if(trade==null){
			this.logger.debug("订单查询为空  "+content.toJSONString());
			return ; 
		}
		UserInfo user = this.judgeUserUtil.isNormalUser(trade.getSellerNick());
		if(user == null){
			this.logger.debug("用户状态异常,"+tid + " 内容为："+content);
			return ;
		}
		TmcMessages tmcMessage = new TmcMessages();
		tmcMessage.setUser(user);
		tmcMessage.setTid(Long.parseLong(tid));
		tmcMessage.setTrade(trade);
		tmcMessage.setTmcContent(content);
		//卖家发货
		if ("CONSIGN".equals(action)) {
			this.goodsConsign(trade.getSellerNick(), tmcMessage);
		}
		//到货城市
		else if ("SENT_CITY".equals(action)||"ARRIVAL".contains(action)||"TMS_STATION_IN".contains(action)) {
			String receiverCity = trade.getReceiverCity();
			String otherCity ="北京市，上海市，天津市，重庆市";
			if (otherCity.contains(receiverCity)) {
				receiverCity = trade.getReceiverState();
			}
			String desc = content.getString("desc");
			if(desc==null){
				this.logger.debug("到达城市信息错误,"+tid + " 内容为："+content);
				return ;
			}
			receiverCity = receiverCity.replaceAll("市", "");
			if(!desc.contains(receiverCity)){
				this.logger.debug("非同城物流,tid: "+tid + " ,用户的收货城市:"+trade.getReceiverCity()+"  内容为："+content);
				return ;
			}
			this.sentCity(trade.getSellerNick(), tmcMessage,content);
		}
		//派件
		else if ("SENT_SCAN".equals(action)||"TMS_DELIVERING".equals(action)) {
			this.sentScan(trade.getSellerNick(), tmcMessage,content);
		}
		//签收
		else if ("SIGNED".equals(action)||"TMS_SIGN".equals(action)||"STA_SIGN".equals(action) || action.endsWith("_SIGN")) {
			long tidFlag = cacheService.setnx(user.getId()+"_"+trade.getTid()+"_tidlock", System.currentTimeMillis()+"", 1800L);
			if(tidFlag==0){
				logger.debug("当前订单在redis中已缓存，tid:"+trade.getTid());
				return ;
			}
			Thread.sleep(60*10*1000L);	
			Trade trade_from = transactionOrderService.queryTrade(tid);
			if(!TradesInfo.LOGISTICS_SIGNED.equals(trade_from.getStatus())){
				logger.info("该消息不是签收消息 废弃 用户为"+user.getId()+"_订单号为"+trade_from.getTid()+"");
				return ;
			}
			tmcMessage.setTrade(trade_from);
			Date singedTime = new Date();
			if(content.containsKey("time")){
				String tmcSingedTime = content.getString("time");
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					singedTime = format.parse(tmcSingedTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			tmcMessage.setSendTime(singedTime);
			try {//签收提醒
				this.singnedGoods(trade_from.getSellerNick(), tmcMessage,content);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tmcMessage.setSendTime(singedTime);
			try {//宝贝关怀
				this.goodsCare(trade_from.getSellerNick(), tmcMessage,content);		
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(TradesInfo.TRADE_FINISHED.equals(trade_from.getStatus())){
				this.logger.debug("回款提醒订单已结束，不需要发送短信 内容为："+content);
				return ;
			}
			tmcMessage.setSendTime(singedTime);
			try {//回款提醒
				this.remindTradeFinshed(trade_from.getSellerNick(), tmcMessage,content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			this.logger.debug("无用物流消息  "+content.toJSONString());
		}
	}
	/**
	 * 发货提醒
	 * @author: wy
	 * @time: 2017年9月13日 下午2:21:57
	 * @param sellerNick
	 * @param tmcMessages
	 */
	private void goodsConsign(String sellerNick,TmcMessages tmcMessages){
		long start = System.currentTimeMillis();
		try {
			if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
				this.logger.debug("发货提醒参数异常, "+tmcMessages.getTid() );
				return;
			}
			Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.SHIPMENT_TO_REMIND); 
			List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
			if(ValidateUtil.isEmpty(list)){
				this.logger.debug("发货提醒未开启,tid: " + tmcMessages.getTid());
				return ;
			}
			tmcMessages.setFlag(false);
			tmcMessages.setSettingType(OrderSettingInfo.SHIPMENT_TO_REMIND);
			tmcMessages.setSendSchedule(false);
			Map<String,Object> map = new HashMap<String,Object>(5);
			map.put("tmcMessages", tmcMessages);
			this.logger.debug("发货提醒开始处理  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
			for (TradeSetup tradeSetup : list) {
				try {
					if(!tradeSetup.getStatus()){
						continue;
					}
					tmcMessages.setFlag(true);
					tmcMessages.setTradeSetup(tradeSetup);
					tmcMessages.setSendTime(new Date());
					this.tradeGoodsSendChain.doHandle(map);
					this.logger.debug("发货提醒流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
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
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("发货提醒花费时间过长,时间为：" + (end-start) +"s,tid: " + tmcMessages.getTid());
			}
		}
	}
	
	/**
	 * 到达同城
	 * @author: wy
	 * @time: 2017年9月13日 下午3:00:50
	 * @param sellerNick
	 * @param tmcMessages
	 */
	private void sentCity(String sellerNick,TmcMessages tmcMessages,JSONObject content){
		long start = System.currentTimeMillis();
		try {
			if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
				this.logger.debug("到达同城参数异常, "+tmcMessages.getTid() );
				return;
			}
			Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.ARRIVAL_LOCAL_REMIND); 
			List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
			if(ValidateUtil.isEmpty(list)){
				this.logger.debug("到达同城未开启,tid: " + tmcMessages.getTid());
				return ;
			}
			tmcMessages.setFlag(false);
			tmcMessages.setSettingType(OrderSettingInfo.ARRIVAL_LOCAL_REMIND);
			tmcMessages.setSendSchedule(false);
			Map<String,Object> map = new HashMap<String,Object>(5);
			map.put("tmcMessages", tmcMessages);
			Date singleTime = tmcMessages.getSendTime();
			this.logger.debug("到达同城开始处理  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
			for (TradeSetup tradeSetup : list) {
				try {
					if(!tradeSetup.getStatus()){
						continue;
					}
					tmcMessages.setFlag(true);
					tmcMessages.setTradeSetup(tradeSetup);
					tmcMessages.setSendTime(singleTime);
					this.tradeArrivalCityChain.doHandle(map);
					this.logger.debug("到达同城流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
					if(tmcMessages.getFlag()){
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(tmcMessages.getFlag()){
				if(content.containsKey("out_sid")){
					String outSid = content.getString("out_sid");
					if(!this.isLocked(outSid, OrderSettingInfo.ARRIVAL_LOCAL_REMIND)){
						this.logger.debug("物流单号加锁失败废弃本消息,tid: "+tmcMessages.getTid() + " 内容为："+tmcMessages.getTmcContent().toJSONString());
						return ;
					}
				}
				this.sendSmsService.doHandle(tmcMessages);
			}
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("到达同城花费时间过长,时间为：" + (end-start) +"s,tid: " + tmcMessages.getTid());
			}
		}
	}
	/**
	 * 派件提醒
	 * @author: wy
	 * @time: 2017年9月13日 下午3:18:47
	 * @param sellerNick
	 * @param tmcMessages
	 */
	private void sentScan(String sellerNick,TmcMessages tmcMessages,JSONObject content){
		long start = System.currentTimeMillis();
		try {
			if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
				this.logger.debug("派件提醒参数异常, "+tmcMessages.getTid() );
				return;
			}
			Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.SEND_GOODS_REMIND); 
			List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
			if(ValidateUtil.isEmpty(list)){
				this.logger.debug("派件提醒未开启,tid: " + tmcMessages.getTid());
				return ;
			}
			tmcMessages.setFlag(false);
			tmcMessages.setSettingType(OrderSettingInfo.SEND_GOODS_REMIND);
			tmcMessages.setSendSchedule(false);
			Map<String,Object> map = new HashMap<String,Object>(5);
			Date singleTime = tmcMessages.getSendTime();
			map.put("tmcMessages", tmcMessages);
			this.logger.debug("派件提醒开始处理  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
			for (TradeSetup tradeSetup : list) {
				try {
					if(!tradeSetup.getStatus()){
						continue;
					}
					tmcMessages.setFlag(true);
					tmcMessages.setTradeSetup(tradeSetup);
					tmcMessages.setSendTime(singleTime);
					this.tradeSentScanChain.doHandle(map);
					this.logger.debug("派件提醒流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
					if(tmcMessages.getFlag()){
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(tmcMessages.getFlag()){
				if(content.containsKey("out_sid")){
					String outSid = content.getString("out_sid");
					if(!this.isLocked(outSid, OrderSettingInfo.SEND_GOODS_REMIND)){
						this.logger.debug("物流单号加锁失败废弃本消息,tid: "+tmcMessages.getTid() + " 内容为："+tmcMessages.getTmcContent().toJSONString());
						return ;
					}
				}
				this.sendSmsService.doHandle(tmcMessages);
			}
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("派件提醒花费时间过长,时间为：" + (end-start) +"s,tid: " + tmcMessages.getTid());
			}
		}
	}
	/**
	 * 签收提醒
	 * @author: wy
	 * @time: 2017年9月13日 下午3:30:02
	 * @param sellerNick
	 * @param tmcMessages
	 */
	private void singnedGoods(String sellerNick,TmcMessages tmcMessages,JSONObject content){
		long start = System.currentTimeMillis();
		try {
			if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
				this.logger.debug("派件提醒参数异常, "+tmcMessages.getTid() );
				return;
			}
			Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.REMIND_SIGNFOR); 
			List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
			if(ValidateUtil.isEmpty(list)){
				this.logger.debug("签收提醒未开启,tid: " + tmcMessages.getTid());
				return ;
			}
			tmcMessages.setFlag(false);
			tmcMessages.setSettingType(OrderSettingInfo.REMIND_SIGNFOR);
			tmcMessages.setSendSchedule(false);
			Map<String,Object> map = new HashMap<String,Object>(5);
			Date singleTime = tmcMessages.getSendTime();
			map.put("tmcMessages", tmcMessages);
			this.logger.debug("签收提醒开始处理  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
			for (TradeSetup tradeSetup : list) {
				try {
					if(!tradeSetup.getStatus()){
						continue;
					}
					tmcMessages.setFlag(true);
					tmcMessages.setTradeSetup(tradeSetup);
					tmcMessages.setSendTime(singleTime);
					this.tradeGoodsSingnedChain.doHandle(map);
					this.logger.debug("签收提醒流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
					if(tmcMessages.getFlag()){
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(tmcMessages.getFlag()){
				if(content.containsKey("out_sid")){
					String outSid = content.getString("out_sid");
					if(!this.isLocked(outSid, OrderSettingInfo.REMIND_SIGNFOR)){
						this.logger.debug("物流单号加锁失败废弃本消息,tid: "+tmcMessages.getTid() + " 内容为："+tmcMessages.getTmcContent().toJSONString());
						return ;
					}
				}
				this.sendSmsService.doHandle(tmcMessages);
			}
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("签收提醒花费时间过长,时间为：" + (end-start) +"s,tid: " + tmcMessages.getTid());
			}
		}
	}
	/**
	 * 宝贝商品关怀
	 * @author: wy
	 * @time: 2017年9月13日 下午3:34:56
	 * @param sellerNick
	 * @param tmcMessages
	 */
	private void goodsCare(String sellerNick,TmcMessages tmcMessages,JSONObject content){
		long start = System.currentTimeMillis();
		try {
			if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
				this.logger.debug("宝贝关怀参数异常, "+tmcMessages.getTid() );
				return;
			}
			Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.COWRY_CARE); 
			List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
			if(ValidateUtil.isEmpty(list)){
				this.logger.debug("宝贝关怀未开启,tid: " + tmcMessages.getTid());
				return ;
			}
			tmcMessages.setFlag(false);
			tmcMessages.setSettingType(OrderSettingInfo.COWRY_CARE);
			tmcMessages.setSendSchedule(true);
			Map<String,Object> map = new HashMap<String,Object>(5);
			map.put("tmcMessages", tmcMessages);
			this.logger.debug("宝贝关怀开始处理  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
			for (TradeSetup tradeSetup : list) {
				try {
					if(!tradeSetup.getStatus()){
						continue;
					}
					tmcMessages.setFlag(true);
					tmcMessages.setTradeSetup(tradeSetup);
					this.tradeGoodsCareChain.doHandle(map);
					this.logger.debug("宝贝关怀流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
					if(tmcMessages.getFlag()){
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(tmcMessages.getFlag()){
				if(content.containsKey("out_sid")){
					String outSid = content.getString("out_sid");
					if(!this.isLocked(outSid, OrderSettingInfo.COWRY_CARE)){
						this.logger.debug("物流单号加锁失败废弃本消息,tid: "+tmcMessages.getTid() + " 内容为："+tmcMessages.getTmcContent().toJSONString());
						return ;
					}
				}
				this.sendSmsService.doHandle(tmcMessages);
			}
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("宝贝关怀花费时间过长,时间为：" + (end-start) +"s,tid: " + tmcMessages.getTid());
			}
		}
	}
	/**
	 * 回款提醒
	 * @author: wy
	 * @time: 2017年9月13日 下午3:37:10
	 */
	private void remindTradeFinshed(String sellerNick,TmcMessages tmcMessages,JSONObject content){
		long start = System.currentTimeMillis();
		try {
			if(ValidateUtil.isEmpty(sellerNick) || tmcMessages==null){
				this.logger.debug("回款提醒参数异常, "+tmcMessages.getTid() );
				return;
			}
			Map<Object,Object> tradeCreateSetupMaps = this.cacheService.hGetAll(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.RETURNED_PAYEMNT); 
			List<TradeSetup> list = TmcDistributeService.sortTradeSetup(tradeCreateSetupMaps.values());
			if(ValidateUtil.isEmpty(list)){
				this.logger.debug("回款提醒未开启,tid: " + tmcMessages.getTid());
				return ;
			}
			tmcMessages.setFlag(false);
			tmcMessages.setSettingType(OrderSettingInfo.RETURNED_PAYEMNT);
			tmcMessages.setSendSchedule(true);
			Map<String,Object> map = new HashMap<String,Object>(5);
			map.put("tmcMessages", tmcMessages);
			this.logger.debug("回款提醒开始处理  " + tmcMessages.getTid()+" ，用户设置了"+list.size()+"个任务");
			for (TradeSetup tradeSetup : list) {
				try {
					if(!tradeSetup.getStatus()){
						continue;
					}
					tmcMessages.setFlag(true);
					tmcMessages.setTradeSetup(tradeSetup);
					this.tradeRemindTradeFinshedChain.doHandle(map);
					this.logger.debug("回款提醒流程处理完，tid: " + tmcMessages.getTid()+ "处理结果:"+tmcMessages.getFlag()+" ,id:"+tmcMessages.getTradeSetup().getId());
					if(tmcMessages.getFlag()){
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(tmcMessages.getFlag()){
				if(content.containsKey("out_sid")){
					String outSid = content.getString("out_sid");
					if(!this.isLocked(outSid, OrderSettingInfo.RETURNED_PAYEMNT)){
						this.logger.debug("物流单号加锁失败废弃本消息,tid: "+tmcMessages.getTid() + " 内容为："+tmcMessages.getTmcContent().toJSONString());
						return ;
					}
				}
				this.sendSmsService.doHandle(tmcMessages);
			}
		}finally {
			long end = System.currentTimeMillis();
			if((end-start)>OrderSettingInfo.TMC_OVER_TIME){
				this.logger.debug("回款提醒花费时间过长,时间为：" + (end-start) +"s,tid: " + tmcMessages.getTid());
			}
		}
	}
	/**
	 * 物流加锁
	 * @author: wy
	 * @time: 2017年9月13日 下午3:06:23
	 * @param outSid 物流运单号
	 * @param orderType 类型
	 * @return true：加锁成功  false:加锁失败
	 */
	private boolean isLocked(String outSid,String orderType){
		if(ValidateUtil.isEmpty(outSid) ||ValidateUtil.isEmpty(orderType) ){
			return false;
		}
		long result = this.redisLock.setnx(outSid+"_"+orderType+"_outSidLock", System.currentTimeMillis()+"", 3600L);
		if(result==1){
			return true;
		}else{
			return false;
		}
	}
}
