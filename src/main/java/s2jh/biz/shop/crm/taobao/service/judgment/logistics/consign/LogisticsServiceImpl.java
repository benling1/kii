package s2jh.biz.shop.crm.taobao.service.judgment.logistics.consign;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.domain.Trade;

import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.service.TransactionTradeService;
import s2jh.biz.shop.crm.taobao.service.judgment.logistics.LogisticsMainUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;

@Service
@Deprecated 
public class LogisticsServiceImpl implements LogisticsService {

	@Autowired
	private LogisticsMainUtil logisticsMainUtil;
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;

	
	@Autowired
	private TransactionTradeService transactionTradeService;
	
//	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeUserOrderSetUpUtil.class);
	@Override
	public boolean logisticsConsign(JSONObject parseJSON) throws Exception {
		// 发货提醒
		// 进行业务的基本逻辑判断
		Map<String, Object> map = this.logisticsMainUtil.logisticsSendMessage(
				parseJSON, new Date(), "6");
		// 逻辑判断都通过了 开始准备调用工具类发送短信
		sendMsg(map);
		this.logisticsPuzzleRemind(parseJSON);
		return true;
	}

	@Override
	public boolean logisticsSentCity(JSONObject parseJSON) throws Exception {
		// 根据订单编号、短信发送类型、短信发送状态查询该订单是否发送过短信,如果查询到数据则放弃本次任务
		String tid = parseJSON.getString("tid");
		String seller_nick = "";
		Trade trade = transactionTradeService.queryTrade(tid);
		if(trade!=null)seller_nick = trade.getSellerNick();
		if("".equals(seller_nick)) return false;
//		boolean status = smsRecordService.findSmsRecordStatus(tid, "7",seller_nick);
//		if (status) {
//			return false;
//		}
		// 到达同城提醒
		// 进行业务的基本逻辑判断
		Map<String, Object> map = this.logisticsMainUtil.logisticsSendMessage(
				parseJSON, new Date(), "7");
		sendMsg(map);
		return true;
	}

	@Override
	public boolean logisticsSentScan(JSONObject parseJSON) throws Exception {
		// 根据订单编号、短信发送类型、短信发送状态查询该订单是否发送过短信,如果查询到数据则放弃本次任务
		String tid = parseJSON.getString("tid");
		String seller_nick = "";
		Trade trade = transactionTradeService.queryTrade(tid);
		if(trade!=null)seller_nick = trade.getSellerNick();
		if("".equals(seller_nick)) return false;
//		boolean status = smsRecordService.findSmsRecordStatus(tid, "8",seller_nick);
//		if (status) {
//			return false;
//		}
		// 派件提醒
		// 进行业务的基本逻辑判断
		Map<String, Object> map = this.logisticsMainUtil.logisticsSendMessage(
				parseJSON, new Date(), "8");
		sendMsg(map);
		return true;
	}

	/**
	 * 抽取出来的逻辑判断通过后的调用工具类发送短信的公共方法
	 * 
	 * @param map
	 * @throws Exception
	 */
	private void sendMsg(Map<String, Object> map) throws Exception {
		if ((boolean) map.get("flag")) {
			map = taoBaoSendMessageUtil.sendSingleMessage(map);
			/*
			 * SmsSendInfo smsInfo = (SmsSendInfo)map.get("smsInfo"); Integer
			 * SmsInfostatuts =smsInfo!=null?smsInfo.getStatus():99;
			 * if(SmsInfostatuts!=6 && SmsInfostatuts!=99){ int countSms =
			 * myBatisDao.execute(SmsSendInfo.class.getName(),
			 * "doCreateByMessage", smsInfo); if(countSms !=1){ //更改的用户条数不等1
			 * 或者日志未成功保存 错误 throw new Exception("保存的日志记录条数错误"); } }
			 */
		}
	}

	@Override
	public boolean logisticsSigned(JSONObject parseJSON) throws Exception {
		// 根据订单编号、短信发送类型、短信发送状态查询该订单是否发送过短信,如果查询到数据则放弃本次任务
		String tid = parseJSON.getString("tid");
		String seller_nick = "";
		Trade trade = transactionTradeService.queryTrade(tid);
		if(trade!=null)seller_nick = trade.getSellerNick();
		if("".equals(seller_nick)) return false;
//		boolean status = smsRecordService.findSmsRecordStatus(tid, "9",seller_nick);
//		if (status) {
//			return true;
//		}
		// 签收提醒
		// 进行业务的基本逻辑判断
		Map<String, Object> map = this.logisticsMainUtil.logisticsSendMessage(parseJSON, new Date(), "9");
		sendMsg(map);
		// 将当前订单的所有定时短信删除
		delMessage(map,parseJSON);
		return true;
	}

	@Override
	public boolean logisticsPuzzleRemind(JSONObject parseJSON) throws Exception {
		// 疑难件处理中的签收超时
		this.logisticsMainUtil.logisticsSendMessage(parseJSON, new Date(), "10");
		return true;
	}

	public boolean logisticsPuzzleRemind(JSONObject parseJSON, Date myDate,
			String settingType) throws Exception {
		// 疑难件,退回件，转头件的处理
		Map<String, Object> map = this.logisticsMainUtil.logisticsSendMessage(parseJSON, myDate, settingType);
		sendMsg(map);
		return true;
	}

	/**
	 * 删除所有定时短信的方法
	 * 
	 * @param parseJSON
	 */
	@SuppressWarnings("rawtypes")
	private void delMessage(Map map,JSONObject parseJSON) {
		this.smsSendInfoScheduleService.delSmsScheduleByOrderSuccess(
				(String)map.get("sellerName"),Long.valueOf(parseJSON.getString("tid")));
	}

	/**
	 * 延迟发货提醒
	 * 
	 * @throws Exception
	 */
	@Override
	public boolean delayRemind(String executeGenre, String orderScopeOne,
			String orderScopeTwo, String settingType, String sellerName)
			throws Exception {
		Map<String, Object> map = new ConcurrentHashMap<>();
		SmsSendInfo smsInfo = new SmsSendInfo();
		Date myDate = new Date();
		smsInfo.setUserId(sellerName);
		//@author:jackstraw_yu 修改
		map.put("sellerName",sellerName);
		map.put("settingType", settingType);
		map.put("flag", true);
		map.put("myDate", myDate);
		map.put("executeGenre", executeGenre);
		map.put("orderScopeOne", orderScopeOne);
		map.put("orderScopeTwo", orderScopeTwo);
		// 设置短信发送的 发送时间，卖家账号 ，短信内容（暂时未JSON数据），卖家姓名
		smsInfo.setSendTime(myDate);
		smsInfo.setChannel("淘宝");
		smsInfo.setUserId(sellerName);
		smsInfo.setStatus(99);// 初次短信判断标记--99
		smsInfo.setCreatedDate(myDate);
		smsInfo.setCreatedBy("1");
		smsInfo.setType(settingType);
		map.put("smsInfo", smsInfo);
		map = logisticsMainUtil.logisticsSendMessage(map);
		return true;
	}
}
