package s2jh.biz.shop.crm.taobao.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.domain.Trade;

import lab.s2jh.core.service.RedisLockServiceImpl;
import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.service.TransactionTradeService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.AutoSellerRateService;
import s2jh.biz.shop.crm.taobao.service.SendMessageService;
import s2jh.biz.shop.crm.taobao.service.judgment.JudgmentMainUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.cowry.GoodsCareMainUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.returned.money.ReturnedPaymentMainUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;

@Service
@Deprecated 
public class SendMessageServiceImpl implements SendMessageService {
//	@Autowired
//	private MyBatisDao myBatisDao;
	@Autowired
	private JudgmentMainUtil judgmentMainUtil;
	@Autowired
	private GoodsCareMainUtil goodsCareMainUtil;
	@Autowired
	private ReturnedPaymentMainUtil returnedPaymentMainUtil;
	@Autowired
	private AutoSellerRateService autoSellerRateService;
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
//	@Autowired
//	private SmsRecordService smsRecordService;
	
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SendMessageServiceImpl.class);
	@Autowired
	private TransactionTradeService transactionTradeService;
	@Autowired
	private RedisLockServiceImpl redisLock;
	@Override
	// 回款提醒
	public boolean returnedMoneyWarn(JSONObject parseJSON) throws Exception {
		// 根据订单编号、短信发送类型、短信发送状态查询该订单是否发送过短信,如果查询到数据则放弃本次任务
//		String tid = parseJSON.getString("tid");
//		String seller_nick = "";
//		Trade trade = transactionTradeService.queryTrade(tid);
//		if(trade!=null)seller_nick = trade.getSellerNick();
//		if("".equals(seller_nick)) {
//			return false;
//		}
//		boolean status = smsRecordService.findSmsRecordStatus(tid,
//				OrderSettingInfo.RETURNED_PAYEMNT,seller_nick);
//		if (status) {
//			return true;
//		}
		this.returnedPaymentMainUtil.sendMessage(parseJSON, new Date(), OrderSettingInfo.RETURNED_PAYEMNT);
		return true;
	}

	@Override
	// 宝贝关怀
	public boolean goodsCowryCareSinged(JSONObject parseJSON) throws Exception {
//		String tid = parseJSON.getString("tid");
//		String seller_nick = "";
//		Trade trade = transactionTradeService.queryTrade(tid);
//		if(trade!=null)seller_nick = trade.getSellerNick();
//		if("".equals(seller_nick)) return false;
//		boolean status = smsRecordService.findSmsRecordStatus(tid,
//				OrderSettingInfo.COWRY_CARE,seller_nick);
//		if (status) {
//			return true;
//		}
		// 宝贝关怀
		this.goodsCareMainUtil.sendMessage(parseJSON,new Date(), OrderSettingInfo.COWRY_CARE,TradesInfo.SINGED_JSON_INFO); 
		return true;
	}

	@Override
	// 宝贝关怀
	public boolean goodsCowryCare(JSONObject parseJSON) throws Exception {
//		String tid = parseJSON.getString("tid");
//		String seller_nick = "";
//		Trade trade = transactionTradeService.queryTrade(tid);
//		if(trade!=null)seller_nick = trade.getSellerNick();
//		if("".equals(seller_nick)) return false;
//		boolean status = smsRecordService.findSmsRecordStatus(tid,
//				OrderSettingInfo.COWRY_CARE,seller_nick);
//		if (status) {
//			return true;
//		}
		// 宝贝关怀
		this.goodsCareMainUtil.sendMessage(parseJSON,new Date(), OrderSettingInfo.COWRY_CARE,TradesInfo.CHANGE_JSON_INFO);
		return true;
	}

	// 交易关闭
	@Override
	public void tradeClose(JSONObject parseJSON) throws Exception {
		if (TradesInfo.TRADE_CLOSED_BY_TAOBAO.equalsIgnoreCase(parseJSON
				.getString("status"))) {
			String tid = parseJSON.getString("tid");
			String seller_nick = "";
			Trade trade = transactionTradeService.queryTrade(tid);
			if(trade!=null)seller_nick = trade.getSellerNick();
			// 付款以前，卖家或买家主动关闭交易
			this.smsSendInfoScheduleService.delSmsScheduleByOrderSuccess(
					seller_nick,
					parseJSON.getLong("tid"));
		}
	}

	/**
	 * 买家付完款，或万人团买家付完尾款
	 * 
	 * @param json
	 * @throws Exception
	 */
	@Override
	public boolean tradeBuyerPay(JSONObject parseJSON) throws Exception {
		String tid = parseJSON.getString("tid");
		String seller_nick = "";
		Trade trade = transactionTradeService.queryTrade(tid);
		if(trade!=null)seller_nick = trade.getSellerNick();
		if("".equals(seller_nick)) return false;
//		boolean status = smsRecordService.findSmsRecordStatus(tid,
//				OrderSettingInfo.PAYMENT_CINCERN,seller_nick);
//		if (status) {
//			return true;
//		}
		// 删除卖家催付的短消息 根据订单ＩＤ、短信的类别和卖家昵称删除
		this.smsSendInfoScheduleService.delSmsScheduleByPay(
				seller_nick, parseJSON.getLong("tid"));
		// 进行业务的基本逻辑判断
		Map<String, Object> map = this.judgmentMainUtil.startJudgeOrder(
				parseJSON, new Date(), OrderSettingInfo.PAYMENT_CINCERN);
		// 逻辑判断都通过了 开始准备调用工具类发送短信
		if ((boolean) map.get("flag")) {
			map = taoBaoSendMessageUtil.sendSingleMessage(map);
		}
		return true;
	}

	/**
	 * 订单信息变更
	 * 
	 * @param json
	 * @throws Exception
	 */
	@Override
	public boolean tradeChanged(JSONObject parseJSON) throws Exception {
		String tid = parseJSON.getString("tid");
		String seller_nick = "";
		if(parseJSON.containsKey("status")){
			switch (parseJSON.getString("status")) {
			case TradesInfo.TRADE_NO_CREATE_PAY: {
				break;
			}
			case TradesInfo.TRADE_BUYER_SIGNED: {// 买家已签收,货到付款专用
				try {
//					
//					boolean status = smsRecordService.findSmsRecordStatus(tid,
//							OrderSettingInfo.COWRY_CARE,seller_nick);
//					if (status) {
//						return true;
//					}
//					this.returnedMoneyWarn(parseJSON);// 回款提醒
					// 宝贝关怀
					// 删除卖家催付的短消息 根据订单ＩＤ、短信的类别和卖家昵称删除
					if(tid!=null){
						this.smsSendInfoScheduleService.delSmsScheduleByPay(
								seller_nick,
								parseJSON.getLong("tid"));
					}
					// 进行业务的基本逻辑判断
//					Map<String, Object> map = this.goodsCareMainUtil.sendMessage(
//							parseJSON, myDate, OrderSettingInfo.COWRY_CARE,
//							TradesInfo.CHANGE_JSON_INFO);
//					// 逻辑判断都通过了 开始准备调用工具类发送短信
//					if ((boolean) map.get("flag") == true) {
//						map = taoBaoSendMessageUtil.sendSingleMessage(map);
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			case TradesInfo.TRADE_CLOSED_BY_TAOBAO: {
				Trade trade = transactionTradeService.queryTrade(tid);
				if(trade!=null)seller_nick = trade.getSellerNick();
				if("".equals(seller_nick)){
					return false;
				} 
				// 付款以前，卖家或买家主动关闭交易
				this.smsSendInfoScheduleService.delSmsScheduleByOrderSuccess(
						seller_nick,
						parseJSON.getLong("tid"));
				break;
			}
			default:
				break;
			}
		}else{
			logger.debug("订单变更状态异常,取值异常--->\r"+parseJSON.toString());
		}
		return true;
	}

	@Override
	public boolean tradeSuccess(JSONObject parseJSON) throws Exception {
		switch (parseJSON.getString("status")) {
		case TradesInfo.TRADE_BUYER_SIGNED: {// 买家已签收,货到付款专用
//			boolean status = smsRecordService.findSmsRecordStatus(tid,
//					OrderSettingInfo.COWRY_CARE,seller_nick);
//			if (status) {
//				return true;
//			}
			// 宝贝关怀
			// 进行业务的基本逻辑判断
//			Map<String, Object> map = this.goodsCareMainUtil.sendMessage(
//					parseJSON, myDate, OrderSettingInfo.COWRY_CARE,
//					TradesInfo.CHANGE_JSON_INFO);
			// 逻辑判断都通过了 开始准备调用工具类发送短信
//			if ((boolean) map.get("flag") == true) {
//				map = taoBaoSendMessageUtil.sendSingleMessage(map);
//			}
			break;
		}
		case TradesInfo.TRADE_FINISHED: {// 交易成功
			long start = System.currentTimeMillis();
			String tid = parseJSON.getString("tid");
			Trade trade = transactionTradeService.queryTrade(tid);
			long end = System.currentTimeMillis();
			if((end-start)>5000){
				logger.debug("订单交易成功执行花费时间："+(end-start)+"ms,tid: "+tid);
			}
			if(trade==null){
				this.logger.debug("订单交易结束，查询不到订单"+parseJSON.toString());
				return false;
			}
			// 交易成功 删除该订单的全部定时任务短信发送
			String oid = parseJSON.getString("oid");
			start = System.currentTimeMillis();
			this.smsSendInfoScheduleService.delSmsScheduleByOrderSuccess(trade.getSellerNick(),parseJSON.getLong("tid"));
			end = System.currentTimeMillis();
			if((end-start)>5000){
				logger.debug("订单交易成功执行花费时间："+(end-start)+"ms,tid: "+tid);
			}
			// 卖家自动评价
			try {
				start = System.currentTimeMillis();
				this.autoSellerRateService.judgeUserOrderRateCare(trade.getSellerNick(),tid, oid);
				end = System.currentTimeMillis();
				if((end-start)>5000){
					logger.debug("订单交易成功执行花费时间："+(end-start)+"ms,tid: "+tid);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		default:
			break;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see s2jh.biz.shop.crm.taobao.service.SendMessageService#payRemind(net.sf.json.JSONObject)
	 */
	@Override
	public boolean payRemind(JSONObject parseJSON) throws Exception {
		if (TradesInfo.WAIT_SELLER_SEND_GOODS.equalsIgnoreCase(parseJSON.getString("status"))) {
		// 等待卖家发货,即:买家已付款
			try {
//				long startTime = System.currentTimeMillis();
				// 删除卖家催付的短消息 根据订单ＩＤ、短信的类别和卖家昵称删除
				String tid = parseJSON.getString("tid");
				String seller_nick = "";
				Trade trade = transactionTradeService.queryTrade(tid);
				if(trade!=null)seller_nick = trade.getSellerNick();
				if("".equals(seller_nick)) {
					return false;
				}
				this.redisLock.setnx(trade.getSellerNick()+"_"+trade.getReceiverMobile()+"_"+"2"+"_smslock", System.currentTimeMillis()+"", 3600L);
				this.redisLock.setnx(trade.getSellerNick()+"_"+trade.getReceiverMobile()+"_"+"3"+"_smslock", System.currentTimeMillis()+"", 3600L);
				this.logger.debug("付款一小时后不催付"+tid);
				this.smsSendInfoScheduleService.delSmsBySellerNick(seller_nick,trade.getBuyerNick());
				this.smsSendInfoScheduleService.delSmsScheduleByPay(trade.getSellerNick(), trade.getTid());
//				boolean status = smsRecordService.findSmsRecordStatus(tid,
//						OrderSettingInfo.PAYMENT_CINCERN,seller_nick);
//				long endTime = System.currentTimeMillis();
//				if((endTime-startTime)>1000){
//					this.logger.info("花费时间："+(endTime-startTime)+"ms,tid:"+tid);
//				}
//				if (status) {
//					return true;
//				}
				// 进行业务的基本逻辑判断
				Map<String, Object> map = this.judgmentMainUtil.startJudgeOrder(parseJSON, new Date(),OrderSettingInfo.PAYMENT_CINCERN);
				// 逻辑判断都通过了 开始准备调用工具类发送短信
				if ((boolean) map.get("flag")) {
					map = taoBaoSendMessageUtil.sendSingleMessage(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
