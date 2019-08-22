package s2jh.biz.shop.crm.tradecenter.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lab.s2jh.core.util.DateUtils;
import lab.s2jh.core.util.NumberUtils;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.manage.dao.SmsRecordRepository;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;
import s2jh.biz.shop.crm.order.pojo.EarningOrderDetail;
import s2jh.biz.shop.crm.order.pojo.OrderReminderEffectVo;
import s2jh.biz.shop.crm.order.pojo.ReminderNum;
import s2jh.biz.shop.crm.order.service.TransactionTradeService;
import s2jh.biz.shop.crm.tradecenter.entity.TradeCenterEffect;
import s2jh.biz.shop.crm.tradecenter.service.TradeCenterEffectService;
import s2jh.biz.shop.crm.tradecenter.service.TradeSetupService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.view.service.ShortLinkService;
import s2jh.biz.shop.crm.view.util.GetCurrentPageUtil;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.RequestUtil;
import s2jh.biz.shop.utils.TradeStatusUtils;

@Controller
@RequestMapping("/tradeSetup")
public class TradeCenterEffectPictureController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(TradeCenterEffectPictureController.class);
	
	@Autowired
	private SmsSendRecordService smsSendRecordService;
	
	@Autowired
	private TransactionTradeService transactionTradeService;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private TradeSetupService tradeSetupService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private ShortLinkService shortLinkService;
	
	@Autowired
	private SmsRecordRepository smsRecordRepository;
	
	@Autowired
	private TradeCenterEffectService tradeCenterEffectService;
	
	//每页展示条数
	private static final Integer CURRENT_ROWS = 5;
	
	/**
	 * 物流提醒效果分析页面根据类型查询任务名称
	 * ztk2017年10月23日下午2:24:06
	 */
	@RequestMapping("/getTaskNames")
	@ResponseBody
	public String getTaskNameByType(HttpServletRequest request,Model model,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		OrderReminderEffectVo orderEffect = null;
		try {
			orderEffect = parseJsonToObj(params);
		} catch (Exception e) {
			e.printStackTrace();
			return rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(orderEffect == null){
			return rsMap(101, "操作失败").put("status", false).toJson();
		}
		orderEffect.setUserId(userId);
		List<OrderReminderEffectVo> effectVos = tradeSetupService.queryTradeSetupTaskNames(orderEffect);
		return rsMap(100, "操作成功").put("status", true).put("data",effectVos).toJson();
	}
	
	/**
	 * 查询订单中心效果分析，按照日期倒序
	 * ztk2017年12月1日下午4:09:11
	 */
	@RequestMapping("/effectIndex")
	@ResponseBody
	public String queryTradeCenterEffect(HttpServletRequest request,@RequestBody String params){
		OrderReminderEffectVo orderEffect = null;
		try {
			orderEffect = parseJsonToObj(params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~解析json失败");
			return rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(orderEffect == null){
			return rsMap(101, "操作失败").put("status", false).toJson();
		}
		String userId = (String)request.getSession().getAttribute("taobao_user_nick");
		if(userId ==null || "".equals(userId)){
			userId = orderEffect.getUserId();
		}
		String dayNum = orderEffect.getDayNum();
		Date startEffectTime = null;
		Date endEffectTime = null;
		if(dayNum != null && !"".equals(dayNum) && !"0".equals(dayNum)){
			endEffectTime = new Date();
			startEffectTime = DateUtils.getStartTimeOfDay(DateUtils.nDaysAgo(Integer.parseInt(dayNum) - 1, new Date()));
		}else {
			dayNum = "7";
			endEffectTime = new Date();
			startEffectTime = DateUtils.getStartTimeOfDay(DateUtils.nDaysAgo(Integer.parseInt(dayNum) - 1, new Date()));
		}
		orderEffect.setUserId(userId);
		orderEffect.setStartEffectTime(startEffectTime);
		orderEffect.setEndEffectTime(endEffectTime);
		List<TradeCenterEffect> tradeCenterEffectList = tradeCenterEffectService.aggregateTradeCenterList(orderEffect);
		List<Date> daysList = new ArrayList<Date>();//天数
		List<Double> orderFeeList = new ArrayList<Double>();//回款订单金额
		List<Integer> orderNumList = new ArrayList<Integer>();//回款订单数
		List<Double> smsMoneyList = new ArrayList<Double>();//短信金额
		List<Double> targetFeeList = new ArrayList<Double>();//催付金额
		ResultMap<String, Object> resultMap = rsMap(100, "操作成功").put("status", true);
		//计算汇总数据
		int smsNumAll = 0;//短信消费条数
		double smsNumMoneyAll = 0.00;//总消费金额
		int totalOrderAll = 0;//催付订单数
		double totalMoneyAll = 0.00;//催付金额
		double successMoneyAll = 0.00;//回款金额
		int successOrderAll = 0;//回款订单数
		int totalCustomerClick = 0;//客户点击量
		int totalPageClick = 0;//页面点击量
		int totalLink = 0;//客户总链接量
		String totalClickRate = "0.00%";//客户总点击率
		String roiValueAll = "0:0";//汇总数据的roi
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		if(tradeCenterEffectList != null && !tradeCenterEffectList.isEmpty()){
			for (int i = 0; i < tradeCenterEffectList.size(); i++) {
				TradeCenterEffect tradeCenterEffect = tradeCenterEffectList.get(i);
				if(tradeCenterEffect != null){
					daysList.add(tradeCenterEffect.getEffectTime());
					targetFeeList.add(Double.valueOf(decimalFormat.format(NumberUtils.getResult(tradeCenterEffect.getTargetFee()))));
					smsMoneyList.add(NumberUtils.getResult(tradeCenterEffect.getSmsMoney()));
					orderNumList.add(NumberUtils.getResult(tradeCenterEffect.getEarningOrder()));
					orderFeeList.add(Double.valueOf(decimalFormat.format(NumberUtils.getResult(tradeCenterEffect.getEarningFee()))));
					smsNumAll += NumberUtils.getResult(tradeCenterEffect.getSmsNum());
					totalOrderAll += NumberUtils.getResult(tradeCenterEffect.getTargetOrder());
					totalMoneyAll += NumberUtils.getResult(tradeCenterEffect.getTargetFee());
					successMoneyAll += NumberUtils.getResult(tradeCenterEffect.getEarningFee());
					successOrderAll += NumberUtils.getResult(tradeCenterEffect.getEarningOrder());
					totalCustomerClick += NumberUtils.getResult(tradeCenterEffect.getCustomerClick());
					totalPageClick += NumberUtils.getResult(tradeCenterEffect.getPageClick());
					totalLink += NumberUtils.getResult(tradeCenterEffect.getLinkNum());
					double earningsOrderRate = NumberUtils.getResult(tradeCenterEffect.getTargetOrder()) == 0? 0.0 : (double)NumberUtils.getResult(tradeCenterEffect.getEarningOrder()) / (double)NumberUtils.getResult(tradeCenterEffect.getTargetOrder());
					double earningsMoneyRate = NumberUtils.getResult(tradeCenterEffect.getTargetFee()) == 0? 0.0 : NumberUtils.getResult(tradeCenterEffect.getEarningFee()) / NumberUtils.getResult(tradeCenterEffect.getTargetFee());
					double customerClickRate = NumberUtils.getResult(tradeCenterEffect.getLinkNum()) == 0 ? 0.0:(double)NumberUtils.getResult(tradeCenterEffect.getCustomerClick()) / (double)NumberUtils.getResult(tradeCenterEffect.getLinkNum());
					String roiValue = creatROI(tradeCenterEffect.getSmsMoney(), tradeCenterEffect.getEarningFee());
					tradeCenterEffect.setEarningOrderRate(decimalFormat.format(earningsOrderRate * 100) + "%");
					tradeCenterEffect.setEarningMoneyRate(decimalFormat.format(earningsMoneyRate * 100) + "%");
					tradeCenterEffect.setClickRate(decimalFormat.format(customerClickRate * 100) + "%");
					tradeCenterEffect.setRoiValue(roiValue);
					tradeCenterEffect.setEffectTimeStr(DateUtils.dateToString(tradeCenterEffect.getEffectTime(), DateUtils.DEFAULT_DATE_FORMAT));
					tradeCenterEffect.setSmsMoney(NumberUtils.getTwoDouble(NumberUtils.getResult(tradeCenterEffect.getSmsMoney())));
					tradeCenterEffect.setCustomerClick(NumberUtils.getResult(tradeCenterEffect.getCustomerClick()));
					tradeCenterEffect.setPageClick(NumberUtils.getResult(tradeCenterEffect.getPageClick()));
					tradeCenterEffect.setLinkNum(NumberUtils.getResult(tradeCenterEffect.getLinkNum()));
					tradeCenterEffect.setTargetFee(Double.valueOf(decimalFormat.format(NumberUtils.getResult(tradeCenterEffect.getTargetFee()))));
					tradeCenterEffect.setEarningFee(Double.valueOf(decimalFormat.format(NumberUtils.getResult(tradeCenterEffect.getEarningFee()))));
				}
			}
		}
		Double earningsOrderRateAll = totalOrderAll == 0 ? 0.00 :  ((double)successOrderAll / (double)totalOrderAll);
		Double earningsMoneyRateAll = NumberUtils.getResult(totalMoneyAll) == 0? 0.00 : NumberUtils.getResult(successMoneyAll) / NumberUtils.getResult(totalMoneyAll);
		smsNumMoneyAll = NumberUtils.getResult(smsNumAll * 0.05);
		roiValueAll = creatROI(smsNumMoneyAll, successMoneyAll);
		totalClickRate = decimalFormat.format((totalLink == 0? 0.0:(double)totalCustomerClick / (double)totalLink) * 100) + "%";
		List<OrderReminderEffectVo> effectVos = tradeSetupService.queryTradeSetupTaskNames(orderEffect);
		resultMap.put("data", effectVos);
		resultMap.put("effects",tradeCenterEffectList);
		resultMap.put("smsNumAll", smsNumAll);
		resultMap.put("smsNumMoneyAll", NumberUtils.getTwoDouble(smsNumMoneyAll));
		resultMap.put("totalOrderAll", totalOrderAll);
		resultMap.put("totalMoneyAll",NumberUtils.getTwoDouble(totalMoneyAll));
		resultMap.put("successMoneyAll", NumberUtils.getTwoDouble(successMoneyAll));
		resultMap.put("successOrderAll", successOrderAll);
		resultMap.put("totalCustomerClick", totalCustomerClick);
		resultMap.put("totalPageClick",totalPageClick);
		resultMap.put("totalLink", totalLink);
		resultMap.put("totalClickRate", totalClickRate);
		resultMap.put("roiValueAll", roiValueAll);
		resultMap.put("earningsOrderRateAll", decimalFormat.format(earningsOrderRateAll * 100) + "%");
		resultMap.put("earningsMoneyRateAll", decimalFormat.format(earningsMoneyRateAll * 100) + "%");
		resultMap.put("daysList", daysList);
		resultMap.put("orderFeeList", orderFeeList);
		resultMap.put("orderNumList", orderNumList);
		resultMap.put("smsMoneyList", smsMoneyList);
		resultMap.put("targetFeeList", targetFeeList);
		//计算今日数据
		int smsNum = 0;//短信消费条数
		double smsMoneyDouble = 0.0;
		int totalOrder = 0;//催付订单数
		double totalMoney = 0.00;//催付金额
		double successMoney = 0.00;//回款金额
		int successOrder = 0;//回款订单数
		int customerClickNum = 0;//客户点击量
		int pageClickNum = 0;//页面点击量
		int totalClickNum = 0;//短信内链接量
		double customerClickRate = 0.0;//客户点击率
		double earningsOrderRate = 0.0;
		double earningsMoneyRate = 0.0;
		String RIO = "0:0";
		TradeCenterEffect tradeCenterEffect = new TradeCenterEffect();
		tradeCenterEffect.setUserId(userId);
		tradeCenterEffect.setTaskId(orderEffect.getId());
		tradeCenterEffect.setType(orderEffect.getType());
		tradeCenterEffect.setEffectTime(DateUtils.getStartTimeOfDay(new Date()));
		TradeCenterEffect todayEffect = tradeCenterEffectService.queryTradeEffect(tradeCenterEffect);
		if(todayEffect != null){
			smsNum = NumberUtils.getResult(todayEffect.getSmsNum());
			smsMoneyDouble = smsNum * 0.05;
			totalOrder = NumberUtils.getResult(todayEffect.getTargetOrder());
			totalMoney = NumberUtils.getResult(todayEffect.getTargetFee());
			successOrder = NumberUtils.getResult(todayEffect.getEarningOrder());
			successMoney = NumberUtils.getResult(todayEffect.getEarningFee());
			customerClickNum = NumberUtils.getResult(todayEffect.getCustomerClick());
			pageClickNum = NumberUtils.getResult(todayEffect.getPageClick());
			totalClickNum = NumberUtils.getResult(todayEffect.getLinkNum());
			customerClickRate = totalClickNum == 0? 0.00 : ((double)customerClickNum / (double)totalClickNum);
			earningsOrderRate = totalOrder == 0 ? 0.00 : ((double)successOrder / (double)totalOrder);
			earningsMoneyRate = totalMoney == 0? 0.00 : successMoney / totalMoney;
			RIO = creatROI(smsMoneyDouble, successMoney);
		}
		resultMap.put("smsNum", smsNum);
		resultMap.put("smsMoneyDouble", NumberUtils.getTwoDouble(smsMoneyDouble));
		resultMap.put("totalOrder", totalOrder);
		resultMap.put("totalMoney", NumberUtils.getTwoDouble(totalMoney));
		resultMap.put("earningsMoney", NumberUtils.getTwoDouble(successMoney));
		resultMap.put("earningsOrder", successOrder);
		resultMap.put("customerClickNum", customerClickNum);
		resultMap.put("pageClickNum", pageClickNum);
		resultMap.put("totalClickNum", totalClickNum);
		resultMap.put("customerClickRate", decimalFormat.format(customerClickRate * 100) + "%");
		resultMap.put("earningsOrderRate", decimalFormat.format(earningsOrderRate * 100) + "%");
		resultMap.put("earningsMoneyRate", decimalFormat.format(earningsMoneyRate * 100) + "%");
		resultMap.put("RIO", RIO);
		return resultMap.toJson();
	}
	
	/**
	 * 根据日期查询催付订单详细信息
	 * ztk2017年9月25日下午4:52:30
	 */
	@RequestMapping("/dayDetail")
	@ResponseBody 
	public String findEarningOrderDetails(HttpServletRequest request,@RequestBody String params
			){
		OrderReminderEffectVo orderEffect = null;
		try {
			orderEffect = parseJsonToObj(params);
		} catch (Exception e) {
			e.printStackTrace();
			return rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(orderEffect == null){
			return rsMap(101, "操作失败").put("status", false).toJson();
		}
		String dateStr = orderEffect.getDateStr();
		String type = orderEffect.getType();
		Integer pageNo = orderEffect.getPageNo();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId == null || "".equals(userId)){
			userId = orderEffect.getUserId();
		}
		orderEffect.setUserId(userId);
		Long taskId = orderEffect.getId();
		if(pageNo == null || pageNo == 0){
			pageNo = 1;
		}
		
		Date startTime = DateUtils.getStartTimeOfDay(DateUtils.parseDate(dateStr, DateUtils.DEFAULT_DATE_FORMAT));
		Date endTime = DateUtils.getEndTimeOfDay(DateUtils.parseDate(dateStr, DateUtils.DEFAULT_DATE_FORMAT));
		logger.info("~~~~~~~~~~~~某日详情的startTime：" + startTime + "endTime:" + endTime);
		Criteria criteria = new Criteria("sellerNick").is(userId);
		if(MsgType.MSG_CGCF.equals(type) || MsgType.MSG_ECCF.equals(type) || MsgType.MSG_JHSCF.equals(type)){
			if(startTime != null && endTime != null){
				criteria.and("payTime").gte(startTime.getTime()).lte(endTime.getTime());
			}
		}else if(MsgType.MSG_HKTX.equals(type)){
			List<String> statusList = new ArrayList<String>();
			statusList.add(TradeStatusUtils.TRADE_FINISHED);
			statusList.add(TradeStatusUtils.TRADE_CLOSED);
			if(startTime != null && endTime != null){
				criteria.and("endTime").gte(startTime.getTime()).lte(endTime.getTime());
			}
			criteria.and("status").in(statusList);
		}else {
			ReminderNum reminderNum = smsRecordService.findRecordTidAndSmsNum(type, userId, startTime, endTime,taskId);
			List<String> tidList = new ArrayList<String>();
			if(reminderNum != null){
				tidList = reminderNum.getTidList();
			}
			criteria.and("tid").in(tidList);
		}
//		Long trades = tradeInfoService.count(new Query(criteria), userId);
		List<TradeDTO> tradeList = tradeInfoService.findList(new Query(criteria), userId);
		List<EarningOrderDetail> successList = new ArrayList<EarningOrderDetail>();
//		EarningOrderDetail earningDetail = new EarningOrderDetail();
//		earningDetail.setBuyerNick("哈数据库等哈哈哈");
//		earningDetail.setEarningFee(9.89);
//		earningDetail.setOrderId("122333444455555");
//		earningDetail.setPayTime(new Date());
//		earningDetail.setRecNum("110119120");
//		earningDetail.setSendTime(DateUtils.getEndTimeOfDay(new Date()));
//		resultList.add(earningDetail);
		String sessionKey = userInfoService.validateFindSessionKey(userId);
		if(tradeList != null && !tradeList.isEmpty()){
			logger.info("~~~~~~~~~~~~某日详情的订单数：" + tradeList.size());
			for (int i = 0; i < tradeList.size(); i++) {
				TradeDTO tradeDTO = tradeList.get(i);
				if(tradeDTO != null && tradeDTO.getTid() != null && !"".equals(tradeDTO.getTid())){
					//如果是常规催付，查询该订单是否发过二次催付的短信，若发过，则不计入常规催付
					if(MsgType.MSG_CGCF.equals(type)){
						Long ECCF_sendTime = findRecordByQuery(tradeDTO.getTid(), userId, MsgType.MSG_ECCF);
						if(ECCF_sendTime != null){
							continue;
						}
					}
					EarningOrderDetail earningDetail = new EarningOrderDetail();
					Long sendTime = findRecordByQuery(tradeDTO.getTid(), userId, type);
					logger.info("~~~~~~~~~~~~tid:" + tradeDTO.getTid() + "的sendTime是：" + sendTime);
					if(sendTime != null){
						try {
							String buyerNick = EncrptAndDecryptClient.getInstance().decrypt(tradeDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey);
							earningDetail.setBuyerNick(buyerNick);
//							earningDetail.setBuyerNick(tradeDTO.getBuyerNick());
							earningDetail.setEarningFee(tradeDTO.getPayment());
							earningDetail.setOrderId(tradeDTO.getTid());
							earningDetail.setPayTime(DateUtils.longToDate(tradeDTO.getPayTime(), DateUtils.DEFAULT_TIME_FORMAT));
							String phone = EncrptAndDecryptClient.getInstance().decrypt(tradeDTO.getReceiverMobile(), EncrptAndDecryptClient.PHONE, sessionKey);
							earningDetail.setRecNum(phone);
//							earningDetail.setRecNum(tradeDTO.getReceiverMobile());
							earningDetail.setSendTime(DateUtils.longToDate(sendTime, DateUtils.DEFAULT_TIME_FORMAT));
							successList.add(earningDetail);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		List<EarningOrderDetail> resultList = new ArrayList<EarningOrderDetail>();
		int totalPage = 0;
		if(successList != null && !successList.isEmpty()){
			logger.info("resultList的大小：" + successList.size());
			totalPage = GetCurrentPageUtil.getTotalPage(successList.size(), CURRENT_ROWS);
			if(pageNo > (successList.size() / CURRENT_ROWS) + 1){
				return rsMap(101, "操作失败").put("status", false).toJson();
			}else if(CURRENT_ROWS * pageNo <= successList.size()){
				resultList = successList.subList(CURRENT_ROWS * (pageNo - 1), CURRENT_ROWS * (pageNo));
			}else{
				resultList = successList.subList(CURRENT_ROWS * (pageNo - 1), successList.size());
			}
			logger.info("~~~~~~总条数:" + successList.size() + "CURRENT_ROWS:" + CURRENT_ROWS + "totalPage:" + totalPage + "resultList:" + resultList);
		}
		return rsMap(100, "操作成功").put("status", true).put("data", resultList).put("totalPage", totalPage).toJson();
	}
	
	/**
	 * 客户以及页面点击量
	 * ztk2017年10月31日上午11:09:15
	 */
	@RequestMapping("/clickDetail")
	@ResponseBody
	public String findClickDetail(HttpServletRequest request,@RequestBody String params){
		OrderReminderEffectVo effectVo = null;
		try {
			effectVo = parseJsonToObj(params);
		} catch (Exception e) {
			e.printStackTrace();
			return rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(effectVo == null){
			return rsMap(101, "操作失败").put("status", false).toJson();
		}
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId == null || "".equals(userId)){
			userId = effectVo.getUserId();
		}
		effectVo.setUserId(userId);
		String type = effectVo.getType();
		String dateStr = effectVo.getDateStr();
		Integer pageNo = effectVo.getPageNo();
		Integer currentRows = CURRENT_ROWS;
		Long taskId = effectVo.getId();
		Date startTime = DateUtils.getStartTimeOfDay(DateUtils.parseDate(dateStr, DateUtils.DEFAULT_DATE_FORMAT));
		Date endTime = DateUtils.getEndTimeOfDay(DateUtils.parseDate(dateStr, DateUtils.DEFAULT_DATE_FORMAT));
		JSONObject showClickDetail = shortLinkService.showClickDetail(userId, type, taskId, startTime, endTime, 
																		pageNo, currentRows, effectVo.getForUser(),
																		RequestUtil.getRequestorIpAddress(request),
																		RequestUtil.getAtiValue(request));
		showClickDetail.put("rc", 100);
		showClickDetail.put("message", "操作成功");
		showClickDetail.put("status", true);
		String jsonString = showClickDetail.toJSONString();
		return jsonString;
	}
	
	/**
	 * 根据type和orderId查询发送时间
	 * ztk2017年9月26日上午11:53:58
	 */
	public Long findRecordByQuery(String orderId,String userId,String type){
		Criteria criteria = new Criteria("userId").is(userId);
		criteria.and("status").is(2);
		if(type != null && !"".equals(type)){
			criteria.and("type").is(type);
		}else {
			return null;
		}
		if(orderId != null && !"".equals(orderId)){
			criteria.and("orderId").is(orderId);
		}else {
			return null;
		}
		List<SmsRecordDTO> recordDTOs = smsRecordRepository.find(new Query(criteria), userId);
		if(recordDTOs != null && !recordDTOs.isEmpty()){
			Long sendTime = recordDTOs.get(0).getSendLongTime();
			return sendTime;
		}
		return null;
	}
	
	/**
	 * 计算ROI
	 * ZTK2017年7月7日上午9:54:08
	 */
	private String creatROI(Double small,Double big){
		double result = NumberUtils.getResult(small);
		double result2 = NumberUtils.getResult(big);
		if(result != 0.00){
			double roi = result2 / result;
			return "1:" + NumberUtils.getTwoDouble(roi);
		}else {
			return NumberUtils.getTwoDouble(result) + ":0";
		}
	}
	
	private OrderReminderEffectVo parseJsonToObj(String params){
		OrderReminderEffectVo orderEffect = null;
		if(params!=null){
			JSONObject parseObject = JSON.parseObject(params);
			String object = parseObject.getString("params");
			orderEffect = JSON.parseObject(object, OrderReminderEffectVo.class);
		}
		return orderEffect;
	}
	
	
//------------------------------------割割割以下为实时查询数据的方法-----------------------------------------------	
	
	/**
	 * 订单中心效果分析
	 * ZTK2017年6月23日上午10:15:34
	 */
//	@RequestMapping("/effectIndex")
//	@SuppressWarnings("unchecked")
//	@ResponseBody
	/*public String normalEffectPicture(HttpServletRequest request,@RequestBody String params){
		OrderReminderEffectVo orderEffect = null;
		try {
			orderEffect = parseJsonToObj(params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~解析json失败");
			return rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(orderEffect == null){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}
		Long taskId = orderEffect.getId();
		String dayNum = orderEffect.getDayNum();
		String type = orderEffect.getType();
//		String userId = orderEffect.getUserId();
		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~进入催付效果分析方法，type：" + type);
		//根据userId和卖家筛选的天数查询发送记录 type=2
		ResultMap<String, Object> resultMap;
		//计算今日数据
		int smsNum = 0;//短信消费条数
		int totalOrder = 0;//催付订单数
		double totalMoney = 0.00;//催付金额
		double successMoney = 0.00;//回款金额
		int successOrder = 0;//回款订单数
		try {
			String userId = (String) request.getSession().getAttribute("taobao_user_nick");
			orderEffect.setUserId(userId);
			Date beginTime = null;
			Date endTime = null;
			if(dayNum != null && !"".equals(dayNum) && !"0".equals(dayNum)){
				endTime = new Date();
				beginTime = DateUtils.getStartTimeOfDay(DateUtils.nDaysAgo(Integer.parseInt(dayNum) - 1, new Date()));
			}else {
				dayNum = "7";
				endTime = new Date();
				beginTime = DateUtils.getStartTimeOfDay(DateUtils.nDaysAgo(Integer.parseInt(dayNum) - 1, new Date()));
			}
			logger.info("~~订单中心效果分析beginTime:" + beginTime + "endTime:" + endTime + "type:" + type + "taskId:" + taskId);
			Map<String, Object> everyDayDataMap = paddingEveryDayData(beginTime, endTime,Integer.parseInt(dayNum));
			List<OrderReminderEffectVo> effects = (List<OrderReminderEffectVo>) everyDayDataMap.get("effects");
			List<String> daysList = (List<String>) everyDayDataMap.get("daysList");//天数
			List<Double> orderFeeList = (List<Double>) everyDayDataMap.get("orderFeeList");//回款订单金额
			List<Integer> orderNumList = (List<Integer>) everyDayDataMap.get("orderNumList");//回款订单数
			List<Double> smsMoneyList = (List<Double>) everyDayDataMap.get("smsMoneyList");//短信金额
			List<Double> targetFeeList = (List<Double>) everyDayDataMap.get("smsMoneyList");//短信金额
			//计算汇总数据
			int smsNumAll = 0;//短信消费条数
			int totalOrderAll = 0;//催付订单数
			double totalMoneyAll = 0.00;//催付金额
			double successMoneyAll = 0.00;//回款金额
			int successOrderAll = 0;//回款订单数
//			int totalCustomerClick = 0;//客户点击量
//			int totalPageClick = 0;//页面点击量
//			int totalLink = 0;//客户总链接量
//			String totalClickRate = "0.00%";//客户点击率
			for(int i = 0;i< Integer.parseInt(dayNum);i++){
				OrderReminderEffectVo reminderEffect = null;
				if(i == 0){
					reminderEffect = sumOrderReminderEffect(beginTime, DateUtils.getEndTimeOfDay(beginTime), type, userId,taskId);
				}else {
					reminderEffect = sumOrderReminderEffect(DateUtils.getStartTimeOfDay(DateUtils.nDaysAfter(i, beginTime)), 
							DateUtils.getEndTimeOfDay(DateUtils.nDaysAfter(i, beginTime)), type, userId,taskId);
				}
				if(reminderEffect != null){
					effects.set(i, reminderEffect);
					orderFeeList.set(i, NumberUtils.getTwoDouble(NumberUtils.getResult(reminderEffect.getEarningOrderFee())));
					orderNumList.set(i, NumberUtils.getResult(reminderEffect.getEarningsOrder()));
					smsMoneyList.set(i, NumberUtils.getTwoDouble(NumberUtils.getResult(reminderEffect.getSmsMoney())));
					targetFeeList.set(i, NumberUtils.getTwoDouble(NumberUtils.getResult(reminderEffect.getTargetOrderFee())));
//					totalCustomerClick += NumberUtils.getResult(reminderEffect.getCustomerClickNum());
//					totalLink += NumberUtils.getResult(reminderEffect.getTotalLink());
//					totalPageClick += NumberUtils.getResult(reminderEffect.getPageClickNum());
				}
			}
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
//			int customerClickNum = effects.get(effects.size() - 1).getCustomerClickNum();//客户点击量
//			int pageClickNum = effects.get(effects.size() - 1).getPageClickNum();//页面点击量
//			int totalClickNum = effects.get(effects.size() - 1).getTotalLink();//客户总链接量
			smsNum = effects.get(effects.size() - 1).getSmsNum();//短信消费条数
			totalOrder = effects.get(effects.size() - 1).getTargetOrder();//催付订单数
			totalMoney = effects.get(effects.size() - 1).getTargetOrderFee();//催付金额
			successMoney = effects.get(effects.size() - 1).getEarningOrderFee();//回款金额
			successOrder = effects.get(effects.size() - 1).getEarningsOrder();//回款订单数
			String customerClickRate = effects.get(effects.size() - 1).getCustomerClickRate();//客户点击率
//			if(totalLink != 0){
//				totalClickRate = decimalFormat.format(((double)totalCustomerClick / (double)totalLink) * 100) + "%";
//			}
			//计算汇总数据
			OrderReminderEffectVo totalReminderEffect = sumOrderReminderEffect(beginTime, endTime, type, userId,taskId);
			if(totalReminderEffect != null){
				smsNumAll = NumberUtils.getResult(totalReminderEffect.getSmsNum());
				totalOrderAll = NumberUtils.getResult(totalReminderEffect.getTargetOrder());
				totalMoneyAll = NumberUtils.getResult(totalReminderEffect.getTargetOrderFee());
				successMoneyAll = NumberUtils.getResult(totalReminderEffect.getEarningOrderFee()); 
				successOrderAll = NumberUtils.getResult(totalReminderEffect.getEarningsOrder());
			}
			Double smsMoneyDouble = smsNum * 0.05;//短信消费金额
			Double smsMoneyAll = smsNumAll * 0.05;//总短信消费金额
			Double earningsOrderRate = totalOrder == 0 ? 0.00 :  ((double)successOrder / (double)totalOrder);
			Double earningsMoneyRate = NumberUtils.getResult(totalMoney) == 0? 0.00 : NumberUtils.getResult(successMoney) / NumberUtils.getResult(totalMoney);
			Double earningsOrderRateAll = totalOrderAll == 0 ? 0.00 :  ((double)successOrderAll / (double)totalOrderAll);
			Double earningsMoneyRateAll = NumberUtils.getResult(totalMoneyAll) == 0? 0.00 : NumberUtils.getResult(successMoneyAll) / NumberUtils.getResult(totalMoneyAll);
			String RIO = creatROI(smsMoneyDouble, successMoney);
			String RIOAll = creatROI(smsMoneyAll, successMoneyAll);
			resultMap = rsMap(100, "操作成功").put("status", true).put("smsNum", smsNum);
			List<OrderReminderEffectVo> effectVos = tradeSetupService.queryTradeSetupTaskNames(orderEffect);
			
			//汇总数据-------
			resultMap.put("smsNumAll", smsNumAll);
			resultMap.put("smsMoneyAll", NumberUtils.getTwoDouble(smsMoneyAll));
			resultMap.put("RIOAll", RIOAll);
			resultMap.put("totalOrderAll", totalOrderAll);
			resultMap.put("successOrderAll", successOrderAll);
			resultMap.put("earningsOrderRateAll", decimalFormat.format(earningsOrderRateAll * 100) + "%");
			resultMap.put("totalMoneyAll", NumberUtils.getTwoDouble(totalMoneyAll));
			resultMap.put("successMoneyAll", NumberUtils.getTwoDouble(successMoneyAll));
			resultMap.put("earningsMoneyRateAll", decimalFormat.format(earningsMoneyRateAll * 100) + "%");
//			resultMap.put("totalCustomerClick", totalCustomerClick);
//			resultMap.put("totalClickRate", totalClickRate);
//			resultMap.put("totalPageClick", totalPageClick);
//			resultMap.put("totalLink", totalLink);
			//汇总数据--------
			resultMap.put("data",effectVos);
			resultMap.put("smsNum", smsNum);
			resultMap.put("smsMoneyDouble", NumberUtils.getTwoDouble(NumberUtils.getResult(smsMoneyDouble)));
			resultMap.put("totalMoney", NumberUtils.getTwoDouble(totalMoney));
			resultMap.put("earningsMoney", NumberUtils.getTwoDouble(successMoney));
			resultMap.put("totalOrder", totalOrder);
			resultMap.put("earningsOrder", successOrder);
			resultMap.put("earningsOrderRate", decimalFormat.format(earningsOrderRate * 100) + "%");
			resultMap.put("earningsMoneyRate", decimalFormat.format(earningsMoneyRate * 100) + "%");
			resultMap.put("RIO", RIO);
//			resultMap.put("customerClickNum", customerClickNum);
//			resultMap.put("pageClickNum", pageClickNum);
//			resultMap.put("totalClickNum", totalClickNum);
			resultMap.put("customerClickRate", customerClickRate);
			resultMap.put("beginTime", DateUtils.formatDate(beginTime, DateUtils.DEFAULT_TIME_FORMAT));
			resultMap.put("endTime", DateUtils.formatDate(endTime, DateUtils.DEFAULT_TIME_FORMAT));
			resultMap.put("daysList", daysList);
			resultMap.put("orderFeeList", orderFeeList);
			resultMap.put("orderNumList", orderNumList);
			resultMap.put("smsMoneyList", smsMoneyList);
			resultMap.put("targetFeeList",targetFeeList);
			Collections.reverse(effects);
			resultMap.put("effects",effects);
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~111return到页面/crms/orderCenterZ/changguicuifu_xiaoguofenxi");
			return resultMap.toJson();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}
	}*/
	
	/**
	 * 客户点击量
	 * ztk2017年10月31日上午11:09:15
	 */
	/*@RequestMapping("/customerDetail")
	@ResponseBody
	public String findCustomerClickDetail(HttpServletRequest request,@RequestBody String params){
		OrderReminderEffectVo effectVo = null;
		try {
			effectVo = parseJsonToObj(params);
		} catch (Exception e) {
			e.printStackTrace();
			return rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(effectVo == null){
			return rsMap(101, "操作失败").put("status", false).toJson();
		}
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		String userId = effectVo.getUserId();
		effectVo.setUserId(userId);
		String type = effectVo.getType();
		String dateStr = effectVo.getDateStr();
		Integer pageNo = effectVo.getPageNo();
		Integer currentRows = CURRENT_ROWS;
		Long taskId = effectVo.getId();
		Date startTime = DateUtils.getStartTimeOfDay(DateUtils.parseDate(dateStr, DateUtils.DEFAULT_DATE_FORMAT));
		Date endTime = DateUtils.getEndTimeOfDay(DateUtils.parseDate(dateStr, DateUtils.DEFAULT_DATE_FORMAT));
		JSONObject showUserClickDetail = shortLinkService.showUserClickDetail(userId, type, taskId, startTime, endTime, pageNo, currentRows);
		showUserClickDetail.put("rc", 100);
		showUserClickDetail.put("message", "操作成功");
		showUserClickDetail.put("status", true);
		String jsonString = showUserClickDetail.toJSONString();
		return jsonString;
	}*/
	
	/**
	 * 页面点击量
	 * ztk2017年10月31日上午11:09:49
	 */
	/*@RequestMapping("/pageDetail")
	@ResponseBody
	public String findPageClickDetail(HttpServletRequest request,@RequestBody String params){
		OrderReminderEffectVo effectVo = null;
		try {
			effectVo = parseJsonToObj(params);
		} catch (Exception e) {
			e.printStackTrace();
			return rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(effectVo == null){
			return rsMap(101, "操作失败").put("status", false).toJson();
		}
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		String userId = effectVo.getUserId();
		effectVo.setUserId(userId);
		String type = effectVo.getType();
		String dateStr = effectVo.getDateStr();
		Integer pageNo = effectVo.getPageNo();
		Integer currentRows = CURRENT_ROWS;
		Long taskId = effectVo.getId();
		Date startTime = DateUtils.getStartTimeOfDay(DateUtils.parseDate(dateStr, DateUtils.DEFAULT_DATE_FORMAT));
		Date endTime = DateUtils.getEndTimeOfDay(DateUtils.parseDate(dateStr, DateUtils.DEFAULT_DATE_FORMAT));
		JSONObject showPageClickDetail = shortLinkService.showPageClickDetail(userId, type, taskId, startTime, endTime, pageNo, currentRows);
		showPageClickDetail.put("rc", 100);
		showPageClickDetail.put("message", "操作成功");
		showPageClickDetail.put("status", true);
		String jsonString = showPageClickDetail.toJSONString();
		return jsonString;
	}*/
	
	/**
	 * 根据时间计算订单中心的效果分析
	 * ztk2017年9月23日下午4:03:54
	 */
	/*public OrderReminderEffectVo sumOrderReminderEffect(Date startTime,Date endTime,String type,
			String userId,Long taskId){
		OrderReminderEffectVo reminderEffect = new OrderReminderEffectVo();
		int smsNum = 0;//短信消费条数
		int totalOrder = 0;//催付订单数
		double totalMoney = 0.00;//催付金额
		double successMoney = 0.00;//回款金额
		int successOrder = 0;//回款订单数
		int userClickNum = 0;//客户点击量
		String customerClickRate = "0.00%";//客户点击率
		int pageClickNum = 0;//页面点击量
		int totalLink = 0;
		//订单状态
		List<String> statusList = new ArrayList<String>();
		//发送订单的总金额
		ReminderNum targetNum = null;
		List<String> tidList = new ArrayList<String>();
		if(MsgType.MSG_JHSCF.equals(type)){//聚划算
			ReminderNum JHSReminderNum = smsRecordService.findRecordTidAndSmsNum(MsgType.MSG_JHSCF, userId, startTime, endTime,taskId);
			if(JHSReminderNum != null){
				tidList = JHSReminderNum.getTidList();
				totalOrder = tidList.size();
				smsNum = NumberUtils.getResult(JHSReminderNum.getSmsNum());
				targetNum = tradeInfoService.sumReminderNum(userId, tidList, null,null,null);
				if(targetNum != null){
					totalMoney = NumberUtils.getResult(targetNum.getTargetMoney());
				}
			}
			statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
			statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
			statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
			statusList.add(TradeStatusUtils.TRADE_FINISHED);
			statusList.add(TradeStatusUtils.TRADE_CLOSED);
			ReminderNum successNum = tradeInfoService.sumReminderNum(userId, tidList, statusList,null,null);
			if(successNum != null){
				successMoney = NumberUtils.getResult(successNum.getTargetMoney());
				successOrder = NumberUtils.getResult(successNum.getTargetOrder());
			}
		}else if(MsgType.MSG_CGCF.equals(type) || MsgType.MSG_ECCF.equals(type)){//常规或二次
			List<String> ecTidList = new ArrayList<String>();//二次催付的结果tidList
			int erCiSmsNum = 0;//二次催付的结果短信条数
			ReminderNum erCiReminderNum = smsRecordService.findRecordTidAndSmsNum(MsgType.MSG_ECCF, userId, startTime, endTime,taskId);
			if(erCiReminderNum != null){
				ecTidList = erCiReminderNum.getTidList();
				totalOrder = ecTidList.size();
				erCiSmsNum = NumberUtils.getResult(erCiReminderNum.getSmsNum());
				targetNum = tradeInfoService.sumReminderNum(userId, ecTidList,null,null,null);
				if(targetNum != null){
					totalMoney = NumberUtils.getResult(targetNum.getTargetMoney());
				}
				logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~二次催付的结果ecOrderList.size === " + ecTidList.size());
			}
			if(MsgType.MSG_CGCF.equals(type)){
				List<String> cgOrderList = new ArrayList<String>();
				ReminderNum cGReminderNum = smsRecordService.findRecordTidAndSmsNum(MsgType.MSG_CGCF, userId, startTime, endTime,taskId);
				if(cGReminderNum != null){
					cgOrderList = cGReminderNum.getTidList();//常规催付的结果tidList
					totalOrder = cgOrderList.size();
					smsNum = NumberUtils.getResult(cGReminderNum.getSmsNum());//常规催付的结果短信条数
					targetNum = tradeInfoService.sumReminderNum(userId, cgOrderList,null,null,null);
					if(targetNum != null){
						totalMoney = NumberUtils.getResult(targetNum.getTargetMoney());
					}
				}
				if(cgOrderList != null){
					cgOrderList.removeAll(ecTidList);
				}
				tidList = cgOrderList;
			}else if(MsgType.MSG_ECCF.equals(type)){
				tidList = ecTidList;
				smsNum = erCiSmsNum;
			}else {
				tidList = null;
				smsNum = 0;
			}
			statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
			statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
			statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
			statusList.add(TradeStatusUtils.TRADE_FINISHED);
			statusList.add(TradeStatusUtils.TRADE_CLOSED);
			ReminderNum successNum = tradeInfoService.sumReminderNum(userId, tidList,statusList,null,null);
			if(successNum != null){
				successMoney = NumberUtils.getResult(successNum.getTargetMoney());
				successOrder = NumberUtils.getResult(successNum.getTargetOrder());
			}
		}else if(MsgType.MSG_HKTX.equals(type)){//回款提醒
			List<String> hkOrderList = new ArrayList<String>();
			ReminderNum hkReminderNum = smsRecordService.findRecordTidAndSmsNum(MsgType.MSG_HKTX, userId, startTime, endTime,taskId);
			if(hkReminderNum != null){
				hkOrderList = hkReminderNum.getTidList();
				totalOrder = hkOrderList.size();
				smsNum = NumberUtils.getResult(hkReminderNum.getSmsNum());//回款提醒的结果短信条数
				targetNum = tradeInfoService.sumReminderNum(userId, hkOrderList,null,null,null);
				if(targetNum != null){
					totalMoney = NumberUtils.getResult(targetNum.getTargetMoney());
				}
			}
			statusList.add(TradeStatusUtils.TRADE_FINISHED);
			statusList.add(TradeStatusUtils.TRADE_CLOSED);
			ReminderNum successNum = tradeInfoService.sumReminderNum(userId, hkOrderList,statusList,null,null);
			if(successNum != null){
				successMoney = NumberUtils.getResult(successNum.getTargetMoney());
				successOrder = NumberUtils.getResult(successNum.getTargetOrder());
			}
		}else if(MsgType.MSG_HPTX.equals(type)){//好评提醒
			List<String> hpOrderList = new ArrayList<String>();
			ReminderNum hpReminderNum = smsRecordService.findRecordTidAndSmsNum(MsgType.MSG_HPTX, userId, startTime, endTime,taskId);
			if(hpReminderNum != null){
				hpOrderList = hpReminderNum.getTidList();
				totalOrder = hpOrderList.size();
//				smsNum = NumberUtils.getResult(hpReminderNum.getSmsNum());//回款提醒的结果短信条数
//				targetNum = tradeInfoService.sumReminderNum(userId, hpOrderList,null,null,null);
//				if(targetNum != null){
//					totalMoney = NumberUtils.getResult(targetNum.getTargetMoney());
//				}
			}
		}else if(MsgType.MSG_FHTX.equals(type) || MsgType.MSG_DDTCTX.equals(type) || MsgType.MSG_PJTX.equals(type) || 
				MsgType.MSG_QSTX.equals(type) || MsgType.MSG_BBGH.equals(type)){//物流提醒和宝贝关怀
			List<String> wlOrderList = new ArrayList<String>();
			ReminderNum wlReminderNum = smsRecordService.findRecordTidAndSmsNum(type, userId, startTime, endTime,taskId);
			if(wlReminderNum != null){
				wlOrderList = wlReminderNum.getTidList();
				totalOrder = wlOrderList.size();
			}
		}
//		Map<String, Object> browseEffect = shortLinkService.getPageBrowseEffect(userId, type, taskId, startTime, endTime);
//		if(browseEffect != null){
//			userClickNum = (int) browseEffect.get("customerClickNum");//客户点击量
//			customerClickRate = (String) browseEffect.get("customerClickRate");//客户点击率
//			pageClickNum = (int) browseEffect.get("pageClickNum");//页面点击量
//			totalLink = (int) browseEffect.get("total");
//		}
		Double earningsOrderRate = totalOrder == 0 ? 0.00 :  ((double)successOrder / (double)totalOrder);
		Double earningsMoneyRate = NumberUtils.getResult(totalMoney) == 0? 0.00 : NumberUtils.getResult(successMoney) / NumberUtils.getResult(totalMoney);
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		reminderEffect.setSmsNum(smsNum);
		reminderEffect.setSmsMoney(NumberUtils.getTwoDouble(smsNum * 0.05));
		reminderEffect.setEarningsOrder(successOrder);
		reminderEffect.setEarningOrderFee(NumberUtils.getTwoDouble(successMoney));
		reminderEffect.setTargetOrder(totalOrder);
		reminderEffect.setTargetOrderFee(NumberUtils.getTwoDouble(totalMoney));
		reminderEffect.setEarningsOrderRate(decimalFormat.format(earningsOrderRate * 100) + "%");
		reminderEffect.setEffectDay(DateUtils.formatDate(startTime, DateUtils.DEFAULT_DATE_FORMAT));
//		reminderEffect.setCustomerClickNum(userClickNum);
//		reminderEffect.setCustomerClickRate(customerClickRate);
//		reminderEffect.setPageClickNum(pageClickNum);
		reminderEffect.setEarningsMoneyRate(decimalFormat.format(earningsMoneyRate * 100) + "%");
//		reminderEffect.setTotalLink(totalLink);
		String RIO = creatROI(NumberUtils.getTwoDouble(smsNum * 0.05), successMoney);
		reminderEffect.setRoi(RIO);
		return reminderEffect;
	}*/
	
	
	/**
	 * 根据用户选择时间，填充每日数据集合
	 * ztk2017年9月23日上午11:41:26
	 */
	/*public Map<String,Object> paddingEveryDayData(Date startDate,Date endDate,Integer days){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<OrderReminderEffectVo> effects = new ArrayList<OrderReminderEffectVo>();
		List<String> daysList = new ArrayList<String>();//天数
		List<Double> orderFeeList = new ArrayList<Double>();//回款订单金额
		List<Integer> orderNumList = new ArrayList<Integer>();//回款订单数
		List<Double> smsMoneyList = new ArrayList<Double>();//短信金额
		List<Double> targetFeeList = new ArrayList<Double>();//催付金额
//		Long days = DateUtils.getDiffDay(startDate, endDate);
		for(int i = 0;i < days;i++){
			OrderReminderEffectVo orderReminderEffect = new OrderReminderEffectVo();
			orderReminderEffect.setSmsNum(0);
			orderReminderEffect.setSmsMoney(0.0);
			orderReminderEffect.setTargetOrder(0);
			orderReminderEffect.setTargetOrderFee(0.0);
			orderReminderEffect.setEarningsOrder(0);
			orderReminderEffect.setEarningOrderFee(0.0);
			orderReminderEffect.setEarningsOrderRate("0.00%");
			orderReminderEffect.setRoi("0:0");
//			orderReminderEffect.setTotalLink(0);
//			orderReminderEffect.setCustomerClickNum(0);
//			orderReminderEffect.setCustomerClickRate("0.00%");
//			orderReminderEffect.setPageClickNum(0);
			orderReminderEffect.setEffectDay(DateUtils.formatDate(DateUtils.nDaysAfter(i, startDate), DateUtils.DEFAULT_DATE_FORMAT));
			daysList.add(DateUtils.formatDate(DateUtils.nDaysAfter(i, startDate), DateUtils.DEFAULT_DATE_FORMAT));
			orderFeeList.add(0.0);
			effects.add(orderReminderEffect);
			orderNumList.add(0);
			smsMoneyList.add(0.0);
			targetFeeList.add(0.0);
		}
		resultMap.put("effects", effects);
		resultMap.put("daysList", daysList);
		resultMap.put("orderFeeList", orderFeeList);
		resultMap.put("orderNumList", orderNumList);
		resultMap.put("smsMoneyList", smsMoneyList);
		resultMap.put("targetFeeList", targetFeeList);
		return resultMap;
	}*/
}
