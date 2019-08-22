package s2jh.biz.shop.crm.taobao.service.judgment.setup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.SecretException;
import com.taobao.api.domain.Trade;

import lab.s2jh.core.dao.mybatis.MyBatisDaoT;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.pojo.TbTransactionOrder;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;

//买家时间设置
@Component
@Deprecated 
public class JudgeSellerMessageUtil extends AbstractJudgeOrderSetUp {
	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(JudgeSellerMessageUtil.class);
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private TransactionOrderService transactionOrderService;
	
	@Autowired
	private VipMemberService vipMemberService;
	
	@Autowired
	private MyBatisDaoT myBatisDaoT;
	
	@Autowired
	private JudgeUserUtil judgeUserUtil;

	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		long startTimeSys = System.currentTimeMillis();
		boolean flag = (boolean) map.get("flag");
		if (flag) {
			SmsSendInfo smsInfo = (SmsSendInfo) map.get("smsInfo");
			Date myDate = new Date();
			String year = new SimpleDateFormat("yyyy").format(myDate);
			String month = new SimpleDateFormat("MM").format(myDate);
			String day = new SimpleDateFormat("dd").format(myDate);
			OrderSetup orderSetup = (OrderSetup) map.get("orderSetup");
			String orderStartTime = orderSetup.getStartTime();
			String orderEndTime = orderSetup.getEndTime();
			String dateString = year + "-" + month + "-" + day + " ";
			String sellerFiltingConditions = orderSetup.getFiltingConditions();
			String settingType = (String) map.get("settingType");
			String sellerNick = orderSetup.getUserId();
			String reminderTime  = orderSetup.getReminderTime();
			boolean remindFlag = ValidateUtil.isNotNull(reminderTime);
			if (flag && sellerFiltingConditions != null) {
				if (sellerFiltingConditions.contains("1")) {// 同一买家一天只催付一次
					smsInfo.setStatus(7);
					sellerNick = null;
					Trade trade = (Trade) map.get("trade");
					if(trade!=null){
						sellerNick= trade.getSellerNick();
					}
					boolean i =smsRecordService.findSmsRecordStatus1(trade.getBuyerNick(), sellerNick,settingType);
					if (i ) { // 今天已经发送过一次
						this.logger
								.debug("*******************tid:"
										+ map.get("tid")
										+ ",类型："
										+ map.get("settingType")
										+ ",同一买家一天只催付一次，当前已经发送过短信，不再发送短信提醒********************");
						map.put("flag", false);
						flag = false;
					} else {
						map.put("flag", true);
						flag = true;
					}
					long endSys = System.currentTimeMillis();
					if((endSys-startTimeSys)>1000){
						this.logger.info("花费时间："+(endSys-startTimeSys)+"ms,tid:"+map.get("tid"));
					}
				}
				if (flag && sellerFiltingConditions.contains("2")) {// 同一买家已付款1小时不催
					smsInfo.setStatus(8);
					Trade trade = (Trade)map.get("trade");
			        boolean countFlag = true;
					try {
						countFlag = this.getCountByOneHourPayment(trade.getSellerNick(),trade.getBuyerNick());
					} catch (SecretException e) {
						e.printStackTrace();
					}
			        if(countFlag){
			        	this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",1小时内已有付款的订单！");
			        	map.put("flag", false);
						flag = false;
			        }
			        long endSys = System.currentTimeMillis();
					if((endSys-startTimeSys)>1000){
						this.logger.info("花费时间："+(endSys-startTimeSys)+"ms,tid:"+map.get("tid"));
					}
				}
				if (flag && sellerFiltingConditions.contains("3")) {// 屏蔽黑名单用户
					// 查询是否是黑名单用户
					String buyerNick = null;
					sellerNick = null;
					Trade trade = (Trade) map.get("trade");
					if(map.containsKey("buyerName")){
						buyerNick = (String) map.get("buyerName");
					}else{
						buyerNick = trade.getBuyerNick();
					}
					sellerNick = trade.getSellerNick();
					MemberDTO md = vipMemberService.queryByMemberInfoDetails(buyerNick, sellerNick);
					if (md != null) {
						map.put("phone",trade.getReceiverMobile() == null ? trade.getReceiverPhone() : trade.getReceiverMobile());
//						MemberInfo memberInfo = myBatisDao.findBy(
//								MemberInfo.class.getName(),
//								"findBlackListByNick", map);
						if (md.getBlackStatus() != null&&md.getBlackStatus()==1) { // 根据买卖家昵称和黑名单表示查询出来用户不为空，即该买家在卖家的标记中为黑名单
							map.put("flag", false);
							this.logger
									.debug("*******************tid:"
											+ map.get("tid")
											+ ",类型："
											+ map.get("settingType")
											+ ",屏蔽黑名单用户--->用户是黑名单，短信不发送********************");
							flag = false;
						}
					}
					long endSys = System.currentTimeMillis();
					if((endSys-startTimeSys)>1000){
						this.logger.info("花费时间："+(endSys-startTimeSys)+"ms,tid:"+map.get("tid"));
					}
				}
				if (flag && sellerFiltingConditions.contains("4")) {// 预售订单不催付
					// 需要判断订单是否是预售订单
					smsInfo.setStatus(10);
					this.logger.debug("*******************tid:"+ map.get("tid") + ",类型："+ map.get("settingType")+ ",预售订单不催付--->暂未设置此逻辑判断~~********************");
				}
				if (flag && sellerFiltingConditions.contains("5")) { // 超出时间不催付
					if(remindFlag){
						map.put("isTomorrow", false);
					}else{
						if (orderStartTime != null && orderEndTime != null) {
							Date startTime, endTime;
							try {
								// 24小时制
								SimpleDateFormat parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
								startTime = parseDate.parse(dateString + orderStartTime);
								endTime = parseDate.parse(dateString + orderEndTime);
								if (startTime != null && endTime != null) {
									// 当前时间大于开始时间 且小于结束时间
									Long myDateLong = myDate.getTime();
									Long startTimeLong = startTime.getTime();
									Long endTimeLong = endTime.getTime();
									if (myDateLong >= startTimeLong && myDateLong <= endTimeLong) {
										flag = true;
									} else {
										this.logger.debug("*******************tid:"+ map.get("tid")+ ",类型："+ map.get("settingType")+ ",超出时间不催付--->短信发送时间超过了用户的设置********************");
										map.put("flag", false);
										flag = false;
									}
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
					long endSys = System.currentTimeMillis();
					if((endSys-startTimeSys)>1000){
						this.logger.info("花费时间："+(endSys-startTimeSys)+"ms,tid:"+map.get("tid"));
					}
				}
				if (flag && sellerFiltingConditions.contains("6")) {// 超出时间次日催付
					if(remindFlag){
						map.put("isTomorrow", true);
					}else{
						map.put("isTomorrow", false);
						if (orderStartTime != null && orderEndTime != null) {
							Date startTime, endTime;
							try {
								// 24小时制
								SimpleDateFormat parseDate = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm");
								startTime = parseDate.parse(dateString
										+ orderStartTime);
								endTime = parseDate
										.parse(dateString + orderEndTime);
								if (startTime != null && endTime != null) {
									// 当前时间大于开始时间 且小于结束时间 
									Long myDateLong = myDate.getTime();
									Long startTimeLong = startTime.getTime();
									Long endTimeLong = endTime.getTime();
									if (myDateLong >= startTimeLong  && myDateLong <= endTimeLong) {
										map.put("flag", true);
										flag = true;
									} else {// 次日发送 定时任务调度
										Calendar cal = Calendar.getInstance();
										if (myDateLong < startTimeLong) {
											if(startTimeLong > (System.currentTimeMillis()+60000)){
												// 如果当前时间小于开始时间 比如现在1.00 5.00开始发送
												// 时间不用次日发送，直接当日发送即可
												cal.setTimeInMillis(System.currentTimeMillis());
												cal.add(Calendar.MINUTE, 5);
												smsInfo.setStartSend(cal.getTime());
												cal.add(Calendar.DATE, 1);
												smsInfo.setEndSend(cal.getTime());
											}else{
												smsInfo.setStartSend(startTime);
												smsInfo.setEndSend(endTime);
											}
										} else {
											// 当期时间大于开始时间且远远大于结束时间，当期时间加一天
											cal.setTime(endTime);
											cal.add(Calendar.DATE, 1);
											smsInfo.setEndSend(cal.getTime());
											cal.setTime(startTime);
											cal.add(Calendar.DATE, 1);
											smsInfo.setStartSend(cal.getTime());
										}
										smsInfo.setStatus(6);
										this.smsSendInfoScheduleService.doAutoCreate(smsInfo);
										this.logger.debug("************tid:"+ map.get("tid")+ ",类型："+ map.get("settingType")+ "开始时间："+smsInfo.getStartSend()+"，结束时间"+smsInfo.getEndSend()+",超出时间次日催付--->短信发送时间超过了用户的设置，次日发送短信**********");
										map.put("flag", false);
										flag = false;
									}
								}

							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
					long endSys = System.currentTimeMillis();
					if((endSys-startTimeSys)>1000){
						this.logger.info("花费时间："+(endSys-startTimeSys)+"ms,tid:"+map.get("tid"));
					}
				}
			} else if (flag && settingType.contains("10")
					&& sellerFiltingConditions == null) {

				// 如果是疑难件并且无过滤条件
				// 当前短信为待发短信
				// 如果settingtype不是10，说明是物流未更新或者发货未签收，则加入schedule.当前短信为待发送短信
				if (!settingType.equals("10")) {
					smsInfo.setStatus(6);
					this.smsSendInfoScheduleService.doAutoCreate(smsInfo);
					this.logger
					.debug("*******************tid:"
							+ map.get("tid")
							+ ",类型："
							+ map
									.get("settingType")
							+ ",如果settingtype不是10，说明是物流未更新或者发货未签收，则加入schedule.当前短信为待发送短信********************");
					map.put("flag", false);
					flag = false;
				} else {

				}
				long endSys = System.currentTimeMillis();
				if((endSys-startTimeSys)>1000){
					this.logger.info("花费时间："+(endSys-startTimeSys)+"ms,tid:"+map.get("tid"));
				}
			}
			map.put("smsInfo", smsInfo);
		}
		long endTimeSys = System.currentTimeMillis();
		if((endTimeSys-startTimeSys)>1000){
			this.logger.info("花费时间："+(endTimeSys-startTimeSys)+"ms,tid:"+map.get("tid"));
		}
		// 责任链判断
		AbstractJudgeOrderSetUp next = super.getNext();
		if (next != null) {
			map = next.startJob(map);
		}
		return map;
	}
	/**
	 * 查询一小时内是否有付过款的订单
	 * @author: wy
	 * @time: 2017年7月26日 上午11:15:07
	 * @param sellerNick 卖家昵称
	 * @param buyerNick 买家昵称
	 * @return
	 * @throws SecretException 
	 */
	public boolean getCountByOneHourPayment(String sellerNick,String buyerNick) throws SecretException{
        if(sellerNick!=null&&buyerNick!=null){
        	String sessionKey = judgeUserUtil.getUserTokenByRedis(sellerNick);
        	// 需要取得买家支付的时间
			Date startTime = null;//开始时间
			Date endTime = new Date();//结束时间
			Calendar cal = Calendar.getInstance();
	        cal.setTime(endTime);//date 换成已经已知的Date对象
	        cal.add(Calendar.HOUR_OF_DAY, -1);// before 1 hou
	        startTime = cal.getTime();
	        if(!EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
				if(sessionKey == null){
					return true;
				}
				buyerNick = EncrptAndDecryptClient.getInstance().encrypt(buyerNick, EncrptAndDecryptClient.SEARCH, sessionKey);
			}
	        Map<String,Object> findmap = new HashMap<String, Object>();
	        findmap.put("bTime", startTime);
	        findmap.put("eTime",endTime);
	        findmap.put("sellerNick",sellerNick);
	        findmap.put("buyerNick",buyerNick);
	        List<Map<String,Object>> mapList = myBatisDaoT.findList(TbTransactionOrder.class.getName(), "findTradePayTime", findmap);
	        if(mapList!=null && mapList.size()>0){
	        	for (Map<String, Object> map : mapList) {
		        	try {
		        		String buyerNickMap = String.valueOf(map.get("buyerNick"));
						if(buyerNick.equals(buyerNickMap)){
							String status = String.valueOf(map.get("status"));
							if(TradesInfo.SELLER_CONSIGNED_PART.equals(status)||TradesInfo.WAIT_SELLER_SEND_GOODS.equals(status) ||TradesInfo.WAIT_BUYER_CONFIRM_GOODS.equals(status)){//三个状态 代表付款一小时
								String tid = String.valueOf(map.get("tid"));
								boolean flag = transactionOrderService.queryTradePaymentTime(Long.parseLong(tid), startTime, endTime);
								if(flag){
									return true;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	        }
        }
		return false;
	}
}
