package s2jh.biz.shop.crm.taobao.service.judgment.manual;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;






import com.taobao.api.SecretException;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.schedule.job.auto.AutoManualOrderScheduleManual;
import s2jh.biz.shop.crm.schedule.triggers.TriggerManager;
import s2jh.biz.shop.crm.taobao.info.ScheduleJobNameInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.util.ScrabbleUpMessageUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;
import s2jh.biz.shop.utils.ConversionTime;

//手动订单主方法
@Component
@Deprecated 
public class JudgmentManualStartUtil {
	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private JudgeManualBlacklistUtil judgeManualBlacklistUtil;
	@Autowired
	private JudgeManualGoodsUtil judgeManualGoodsUtil;
	@Autowired
	private JudgeManualLocaltionUtil judgeManualLocaltionUtil;
	@Autowired
	private JudgeManualMessageCountUtil judgeManualMessageCountUtil;
	@Autowired
	private JudgeManualTradeCountUtil judgeManualTradeCountUtil;
	@Autowired
	private JudgeManualVendormarkUtil judgeManualVendormarkUtil;
	@Autowired
	private JudgeManualRefundUtil judgeManualRefundUtil;
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;
	@Autowired
	private TradeInfoService tradeInfoService;
	@Autowired
	private VipMemberService vipMemberService;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	public Map<String,Object> sendMessage(String sellerNick ) throws Exception {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		Long nowDate = cal.getTime().getTime();
		//定制每天的21:09:00执行，
//        calendar.set(year, month, day, 10, 29, 00);
		int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    int day = cal.get(Calendar.DAY_OF_MONTH);//每天
	    cal.set(year, month, day, 8, 00, 00);
	    Long startDate = cal.getTime().getTime();
	    cal.set(year, month, day, 22, 00, 00);
	    Long endDate = cal.getTime().getTime();
	    Date startTime = null;
		if(nowDate>=startDate&&nowDate<=endDate){  //当前时间在8.00-22.00之间
			resultMap = realSendMessage(sellerNick);
			/*System.err.println("sendMessage:" + resultMap.get("successNum"));*/
		}else if(nowDate < startDate){
			startTime = new Date(startDate);
			resultMap.put("flag", false);
			resultMap.put("msg", "现在时间在8:00之前，已添加到定时任务，8:00以后进行发送");
		}else if(nowDate > endDate){
			cal.setTime(new Date(startDate));
			cal.add(Calendar.DATE, 1);
			startTime =cal.getTime();
			resultMap.put("flag", false);
			resultMap.put("msg", "现在时间在22:00之后，已添加到定时任务，第二天8:00以后进行发送");
		}
		if(startTime!=null){  //时间不对  重置开始时间后   启动定时任务
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
	        ServletContext servletContext = webApplicationContext.getServletContext();  
	        ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			String timeSend = ConversionTime.conversionTime(startTime);
			String jobName = ScheduleJobNameInfo.AUTO_MANUAL_ORDERS+"_"+sellerNick; //jobName  定时的类型_卖家昵称     例子:manual_crazyboy   
			TriggerManager.addJob(jobName,ScheduleJobNameInfo.AUTO_MANUAL_ORDERS, timeSend, AutoManualOrderScheduleManual.class,jobName,application);
			Scheduler scheduler = null;
			try {
				//添加并启动
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.start();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}
	public Map<String, Object> realSendMessage(String sellerNick) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> map = new HashMap<String,Object>();//保存后续判断所需要的数据
		map.put("userId", sellerNick); //String sellerNick = "crazyboy"; 传进来的参数是买家昵称
		map.put("flag", true);
		map.put("status", "1");//是否开启或关闭状态 
		map.put("tmc", 1);
		int successNum = 0;//统计发送成功条数
		OrderReminder orderReminder = myBatisDao.findBy(OrderReminder.class.getName(), "findOrderReminderBySendMsg", map);
		if(orderReminder!=null){
			String orderDateType = orderReminder.getOrderDateType();//查询订单时间类型
			Date endTime = orderReminder.getEndTime(); //订单开始时间
			Date startTime = orderReminder.getStartTime();//定金结束时间
			Criteria criteria = Criteria.where("sellerNick").is(sellerNick);
			//添加时间类别和 开始时间和结束时间   添加部分后续有可能不会被设置值得key,SQL语句需要判断是否为空
//			map.put("date", null);
//			map.put("paymentTime", null);
//			map.put("orderSource", null);
//			map.put("userOrderStatus", null);
//			map.put("buyerRate", null);
//			map.put("buyerNoRate", null);
//			map.put("buyerNoRateSellerRate", null);
			String dateType = null;
			String userOrderStatus = null;
			Boolean paymentTime = null;
			String orderSource = null;
			if(orderDateType!=null){
//				map.put("endTime", endTime);
//				map.put("startTime", startTime);
				switch (orderDateType) {
				case "1":{//下单时间
//					map.put("date", "created");
					dateType = "createdUTC";
					break;
				}
				case "2":{//付款时间
//					map.put("paymentTime", true);
					paymentTime = true;
					break;
				}
				case "3":{//发货时间
//					map.put("date", "consignTime");
					dateType = "consignTimeUTC";
					break;
				}
				case "4":{//变更时间
//					map.put("date", "modified");
					dateType = "modifiedUTC";
					break;
				}
				case "5":{//结束时间
//					map.put("date", "end_time");
					//dateType = "end_time";
					dateType = "endTimeUTC";
					break;
				}
				default:
					break;
				}
			}
			//添加评价状态
			String evaluateStatus = orderReminder.getEvaluateStatus();
			if(evaluateStatus!=null){
				if("1".equals(evaluateStatus)){//买家未评价
//					map.put("buyerNoRate", true);
					criteria.and("buyerRate").is(false);
				}else if("2".equals(evaluateStatus)){//买家未评价，卖家已经评价
//					map.put("buyerNoRateSellerRate", true);
					criteria.and("sellerRate").is(true).and("buyerRate").is(false);
				}else if("3".equals(evaluateStatus)){//买家已评价
//					map.put("buyerRate", true);
					criteria.and("buyerRate").is(true);
				}
			}
			//添加订单金额
//			map.put("minOrderPrice", orderReminder.getMinOrderPrice());
//			map.put("maxOrderPice", orderReminder.getMaxOrderPrice());
			Double minOrderPrice = orderReminder.getMinOrderPrice();
			Double maxOrderPice = orderReminder.getMaxOrderPrice();
			if(maxOrderPice != null && minOrderPrice != null){
				criteria.and("payment").gte(minOrderPrice).lte(maxOrderPice);
			}else if(maxOrderPice != null && minOrderPrice == null){
				criteria.and("payment").lte(maxOrderPice);
			}else if(maxOrderPice == null && minOrderPrice != null){
				criteria.and("payment").gte(minOrderPrice);
			}
			//订单状态
			userOrderStatus = orderReminder.getOrderStatus();
			if(userOrderStatus!=null){
				if("REFUND_SUCCESS".equals(userOrderStatus)){
//					map.put("refundSuccess", true);//退款成功   是另外一个字段需和订单状态加以区分
					criteria.and("refundFlag").is(true);
				}else if("ALL_ORDERS".equals(userOrderStatus)){//全部订单，所有订单都需要查询
//					map.put("userOrderStatus", null);
				}else{
//					map.put("userOrderStatus", userOrderStatus);//订单状态
					criteria.and("status").is(userOrderStatus);
				}
					
			}
			//订单来源
			orderSource = orderReminder.getOrderSource();
			if(orderSource==null ||"0".equals(orderSource)){
				//订单来源为空  或者 设置了不限
			}else{
//				map.put("orderSource", orderReminder.getOrderSource());//订单来源
				criteria.and("tradeFrom").in(orderSource);
			}
			if(paymentTime != null && dateType == null){
				Criteria tidCriteria = Criteria.where("sellerNick").is(sellerNick);
				if(startTime != null && endTime != null){
					tidCriteria.and("payTime").gte(startTime.getTime()).lte(endTime.getTime());
				}else if(startTime == null && endTime != null){
					tidCriteria.and("payTime").lte(endTime.getTime());
				}else if(startTime != null && endTime == null){
					tidCriteria.and("payTime").gte(startTime.getTime());
				}
				List<TradeDTO> tradeDTOs = tradeInfoService.findList(new Query(tidCriteria), sellerNick);
				List<String> tidList = new ArrayList<String>();
				if(tradeDTOs != null && tradeDTOs.size() > 0){
					for (TradeDTO tradeDTO : tradeDTOs) {
						tidList.add(tradeDTO.getTid());
					}
				}
				criteria.and("tid").in(tidList);
			}else if(paymentTime == null && dateType != null){
				if(startTime != null && endTime != null){
					criteria.and(dateType).gte(startTime).lte(endTime);
				}else if(startTime != null && endTime == null){
					criteria.and(dateType).gte(startTime);
				}else if(startTime == null && endTime != null){
					criteria.and(dateType).lte(endTime);
				}
			}
			List<TradeDTO> tradeDTOS = tradeInfoService.findList(new Query(criteria), sellerNick);
//			List<TransactionOrder> tradeList = myBatisDao.findList(TransactionOrder.class.getName(), "findAllByManualOrderSetup", map);
			/*
			 * 测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试%%%%%%%%%%上传的时候删除掉！！！！！！！！！
			 */
			/*System.err.println(tradeList.size());
			if(tradeList != null && tradeList.size() > 0){
				System.err.println(tradeList.get(0).getBuyerNick());
				System.err.println(tradeList.get(0).getTotalFee());
				System.err.println(tradeList.get(0).getOrderList());
				System.err.println(tradeList.get(0).getId());
			}*/
			//取得要发的短信内容
			String contentMessage = orderReminder.getContent();
			if(!tradeDTOS.isEmpty()){
				//开始判断 黑名单、商品选择、地区、短信发送次数、交易次数
				this.judgeManualRefundUtil.setNext(this.judgeManualBlacklistUtil);
				this.judgeManualBlacklistUtil.setNext(this.judgeManualGoodsUtil);
				this.judgeManualGoodsUtil.setNext(this.judgeManualLocaltionUtil);
				this.judgeManualLocaltionUtil.setNext(this.judgeManualMessageCountUtil);
				this.judgeManualMessageCountUtil.setNext(this.judgeManualTradeCountUtil);
				this.judgeManualTradeCountUtil.setNext(this.judgeManualVendormarkUtil);
				Iterator<TradeDTO> tradeIter = tradeDTOS.iterator();
				String sessionKey = judgeUserUtil.getUserTokenByRedis(sellerNick);
				while (tradeIter.hasNext()) {
					TradeDTO tradeDTO =  tradeIter.next();
					try {
						if(EncrptAndDecryptClient.isEncryptData(tradeDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
							if(sessionKey == null){
								return null;
							}
							//判断买家昵称是否为密文，如果是则解密
							tradeDTO.setBuyerNick(judgeUserUtil.getDecryptData(tradeDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH, null, sessionKey));
							//判断买家手机号是否为密文，如果是则解密
							tradeDTO.setReceiverMobile(judgeUserUtil.getDecryptData(tradeDTO.getReceiverMobile(), EncrptAndDecryptClient.PHONE,null, sessionKey));
							//判断买家座机是否为密文，如果是则解密
							tradeDTO.setReceiverPhone(judgeUserUtil.getDecryptData(tradeDTO.getReceiverPhone(), EncrptAndDecryptClient.SIMPLE, null, sessionKey));
							//判断买家姓名是否为密文，如果是则解密
							tradeDTO.setReceiverName(judgeUserUtil.getDecryptData(tradeDTO.getReceiverName(), EncrptAndDecryptClient.SEARCH,null, sessionKey));
							//判断收货人街道地址是否为密文，如果是则解密
							tradeDTO.setReceiverAddress(judgeUserUtil.getDecryptData(tradeDTO.getReceiverAddress(), EncrptAndDecryptClient.SEARCH,null, sessionKey));
						}
					} catch (SecretException e) {
						return null;
					}
					map.put("trade", tradeDTO);
					map.put("orderReminder", orderReminder);
					map = judgeManualRefundUtil.startJob(map);
					boolean flag = (boolean)map.get("flag");
					if(flag){//前面的判断条件都通过了
						if(contentMessage!=null){
							contentMessage = ScrabbleUpMessageUtil.getMessage(orderReminder, sellerNick, tradeDTO);
							SmsSendInfo smsInfo =getSmsSendInfo(contentMessage,tradeDTO,orderReminder);
							map.put("smsInfo", smsInfo);
							taoBaoSendMessageUtil.sendSingleMessage(map);
							successNum ++;
						}
					}
				}
			}
			resultMap.put("flag", true);
			resultMap.put("successNum", successNum);
			/*System.err.println("successNum是:" + successNum);*/
		}else{
			//用户未设置手动订单
			resultMap.put("flag", false);
			resultMap.put("msg", "未查询到手动订单提醒条件,请重新设置或联系管理员");
		}
		return resultMap;
	}
	/**
	 * 实例化SmsSendInfo对象，并通过订单和手动订单设置为其赋值
	 * @param content 短信的内容
	 * @param order 订单对象
	 * @param orderReminder 手动订单提醒对象
	 * @return
	 */
	private SmsSendInfo getSmsSendInfo(String content,TradeDTO tradeDTO,OrderReminder orderReminder){
		SmsSendInfo smsInfo = new SmsSendInfo();
		int messageCount = content.length();
		if(messageCount<=70){
			messageCount=1;
		}else{
			messageCount = (messageCount+66)/67;
		}
		smsInfo.setActualDeduction(messageCount);//短信长度
		smsInfo.setUserId(tradeDTO.getSellerNick());//卖家昵称
		smsInfo.setPhone(tradeDTO.getReceiverMobile());//手机号码
		smsInfo.setNickname(tradeDTO.getBuyerNick());//卖家昵称
		smsInfo.setContent(content);//短信内容
		smsInfo.setType("26");//短信类型  26--收到提醒
		smsInfo.setSendTime(new Date());//发送时间
//		smsInfo.setAutograph(orderReminder.getSmsSign());//短信前面
		smsInfo.setStatus(99);//初始化短信
		try {
			smsInfo.setTid(Long.parseLong(tradeDTO.getTid()));
		} catch (Exception e) {
			smsInfo.setTid(null);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("buyerName", tradeDTO.getBuyerNick());
		map.put("sellerName",tradeDTO.getSellerNick() );
		map.put("phone",tradeDTO.getReceiverMobile());
		return smsInfo;
	}
}
