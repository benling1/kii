package s2jh.biz.shop.crm.order.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.s2jh.core.service.RedisLockServiceImpl;
import lab.s2jh.core.util.NumberUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.order.entity.EffectPicture;
import s2jh.biz.shop.crm.order.pojo.SuccessOrderDetail;
import s2jh.biz.shop.crm.order.service.EffectPictureService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.TradeStatusUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

import com.taobao.api.SecretException;

/**
 * 指定号码发送效果分析Controller
 */
@Controller
@RequestMapping("/appointNumber")
public class AppointNumberEffectPictureController {
	
	private Logger logger = LoggerFactory.getLogger(AppointNumberEffectPictureController.class);
	
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	
	@Autowired
	private VipMemberService vipMemberService;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private EffectPictureService effectPictureService;
	
//	private Map<String,Object> map = new HashMap<String, Object>();

	/**
	 * 初次进入指定号码发送效果分析页面，只进行参数的保存和传递，不进行查询
	 * ZTK2017年8月14日上午10:27:19
	 */
	@Deprecated
//	@RequestMapping("/effectIndex")
	public String numberEffectIndex(HttpServletRequest request,Model model,Long msgId){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(msgId != null){
//			cacheService.putString(userId + "MSGID", msgId);
			redisLockServiceImpl.putStringValueWithExpireTime(userId + "msgId", msgId + "",TimeUnit.HOURS,1L);
		}else {
//			msgId = cacheService.getString(userId + "MSGID");
			String msgIdStr = redisLockServiceImpl.getStringValue(userId + "msgId");
			if(msgIdStr != null && !"".equals(msgIdStr)){
				msgId = Long.parseLong(msgIdStr);
			}
		}
		MsgSendRecord msgSendRecord = msgSendRecordService.findOne(msgId);
		Date beginTime = msgSendRecord.getSendCreat();
		Date endTime = DateUtils.getTimeByDay(beginTime, 3);
//		map.put(userId + "beginTime", beginTime);
//		map.put(userId + "endTime", endTime);
		model.addAttribute("beginTime", DateUtils.dateToStringHMS(beginTime));
		model.addAttribute("endTime", DateUtils.dateToStringHMS(endTime));
		model.addAttribute("message", 1);
		return "/crms/marketingCenter/appointNumberEffectPicture";
	}
	
	/**
	 * 初次进入客户详情页面，只进行参数的保存和传递，不进行查询
	 * ZTK2017年8月14日上午10:27:19
	 */
//	@RequestMapping("/customerIndex")
	@Deprecated
	public String numberCustomerIndex(Model model){
		model.addAttribute("message", 1);
		return "/crms/marketingCenter/appointNumberCustomerDetail";
	}
	
	/**
	 * 初次进入商品详情页面，只进行参数的保存和传递，不进行查询
	 * ZTK2017年8月14日上午10:27:19
	 */
//	@RequestMapping("/itemIndex")
	@Deprecated
	public String numberItemIndex(Model model){
		model.addAttribute("message", 1);
		return "/crms/marketingCenter/appointNumberItemDetail";
	}
	
	/**
	 * 指定号码发送群发列表
	 * ZTK2017年7月24日上午10:57:36
	 */
	@RequestMapping("/msgSendRecord")
	public String appointNumberMsgRecord(HttpServletRequest request,String activityName,String bTime,String eTime,Model model,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Date beginTime = DateUtils.convertDate(bTime);
		Date endTime = DateUtils.convertDate(eTime);
		String msgType= MsgType.MSG_ZDHMQF;
		String contextPath = request.getContextPath()+"/appointNumber/msgSendRecord";
		if(activityName != null){
			activityName=activityName.trim();
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("activityName", activityName);
		map.put("isSent", true);
		map.put("type", msgType);
		map.put("userId", userId);
		Pagination pagination = msgSendRecordService.sendRecordPagination(contextPath,pageNo,map);
		String url = request.getContextPath() + "/appointNumber/msgSendRecord";
		StringBuilder params = new StringBuilder();
		if(bTime != null && !"".equals(bTime)){
			params.append("&bTime=").append(bTime);
		}
		if(eTime != null && !"".equals(eTime)){
			params.append("&eTime=").append(eTime);
		}
		if(activityName != null && !"".equals(activityName)){
			params.append("&activityName=").append(activityName);
		}
		pagination.pageView(url, params.toString());
		model.addAttribute("pagination",pagination);
		model.addAttribute("bTime", bTime);
		model.addAttribute("eTime", eTime);
		model.addAttribute("activityName", activityName);
		return "crms/marketingCenter/appointNumberMsgRecord";
	}
	
	/**
	 * 指定号码发送效果分析
	 * ZTK2017年7月24日上午11:04:59
	 */
//	@RequestMapping("/effectPicture")
	@RequestMapping("/effectIndex")
	public String memberEffectPicture(HttpServletRequest request,HttpServletResponse response,
			String type,Long msgId,Model model,String orderSource,
			@RequestParam(value="dayNum",defaultValue="3",required=false)Integer dayNum){
			//根据userId和卖家筛选的天数查询发送记录 type=2
			String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		try {
			logger.info("======================开始进行效果分析~~~~" + new Date() + "~~~~~~~~~~~~~~~~~~");
			if("0".equals(orderSource) || "".equals(orderSource) || orderSource == null){
				orderSource = "TOTAL";
			}
			if(msgId != null && !"".equals(msgId)){
				redisLockServiceImpl.putStringValueWithExpireTime(userId + "msgId",msgId + "", TimeUnit.HOURS, 1L);
			}else {
				String msgIdStr = redisLockServiceImpl.getStringValue(userId + "msgId");
				if(msgIdStr != null && !"".equals(msgIdStr)){
					msgId = Long.parseLong(msgIdStr);
				}
			}
			redisLockServiceImpl.putStringValueWithExpireTime(userId + "dayNum", dayNum + "", TimeUnit.HOURS, 1L);
			MsgSendRecord msgSendRecord = msgSendRecordService.findOne(msgId);
			type = MsgType.MSG_ZDHMQF;
			Date beginTime = msgSendRecord.getSendCreat();
			Date endTime = DateUtils.getEndTimeOfDay(DateUtils.nDaysAfter(dayNum - 1, beginTime));
			redisLockServiceImpl.putStringValueWithExpireTime(userId + "beginTime", DateUtils.dateToStringHMS(beginTime), TimeUnit.HOURS, 1L);
			redisLockServiceImpl.putStringValueWithExpireTime(userId + "endTime", DateUtils.dateToStringHMS(endTime), TimeUnit.HOURS, 1L);
//			map.put(userId + "beginTime", beginTime);
//			map.put(userId + "endTime", endTime);
			Date log1 = new Date();
			logger.info("=========开始计算效果分析所需数据~~~~" + log1 + "    ~msgId："+msgId+"~~~~~~~~~~~~~~~~~");
			//计算短信长度
			int messageCount = msgSendRecord.getTemplateContent().length();
			if(messageCount<=70){
				messageCount=1;
			}else{
				messageCount = (messageCount+66)/67;
			}
			Integer totalSmsCustomer = msgSendRecord.getTotalCount();//群发客户总数
			Integer successSmsCustomer = msgSendRecord.getSucceedCount();//成功发送客户数
			Integer totalSmsNum = NumberUtils.getResult(msgSendRecord.getSucceedCount()) * messageCount;//消耗短信条数
			Double smsMoney = NumberUtils.getResult(msgSendRecord.getSucceedCount()) * messageCount * 0.05;//消费短信金额
			/*
			 * 实时查询的数据所需手机号，太慢了，暂时不用
			 */
			/*List<String> phoneList = new ArrayList<String>();
			Criteria criteria = new Criteria("userId").is(userId);
			criteria.and("status").is(2);
			criteria.and("type").is("34");
			if(msgId != null){
				criteria.and("msgId").is(msgId);
				phoneList = smsRecordService.findNumList(new Query(criteria), userId);
			}*/
			//下单客户数、下单总金额、订单总数、下单商品总数
			int totalOrderCustomer = 0;double totalOrderMoney = 0.00;
			int totalOrderNum = 0;long totalOrderItemNum = 0;
			
			//成交客户数、成交总金额、成交订单数、成交商品数
			int successCustomer = 0;double successMoney = 0.00;
			int successOrderNum = 0;long successItemNum = 0;
			
			//未付款客户数、未付款总金额、未付款订单数、未付款商品数
			int waitCustomer = 0;double waitMoney = 0.00;
			int waitOrderNum = 0;long waitItemNum = 0;
			
			//退款客户数、退款总金额、退款订单数、退款商品数
			int failCustomer = 0;double failMoney = 0.00;
			int failOrderNum = 0;long failItemNum = 0;
			//从mysql中查询出每日数据的总和
			EffectPicture effectPicture = effectPictureService.findEffectBySource(msgId, orderSource, userId, dayNum + 1);
			if(effectPicture != null){
				totalOrderMoney = NumberUtils.getResult(effectPicture.getTotalFee());//下单总金额
				totalOrderNum = NumberUtils.getResult(effectPicture.getTotalOrder());//订单总数
				totalOrderItemNum = NumberUtils.getResult(effectPicture.getTotalItem());//下单商品总数
				successMoney = NumberUtils.getResult(effectPicture.getPayFee());//成交总金额
				successOrderNum = NumberUtils.getResult(effectPicture.getPayOrder());//成交订单数
				successItemNum = NumberUtils.getResult(effectPicture.getPayItem());//成交商品数
				waitMoney = NumberUtils.getResult(effectPicture.getWaitPayFee());//未付款总金额
				waitOrderNum = NumberUtils.getResult(effectPicture.getWaitPayOrder());//未付款订单数
				waitItemNum = NumberUtils.getResult(effectPicture.getWaitPayItem());//未付款商品数
				failMoney = NumberUtils.getResult(effectPicture.getRefundFee());//退款总金额
				failOrderNum = NumberUtils.getResult(effectPicture.getRefundOrder());//退款订单数
				failItemNum = NumberUtils.getResult(effectPicture.getRefundItem());//退款商品数
			}
			//从mysql查询实际的客户数
			EffectPicture realEffectPicture = effectPictureService.findRealBuyerNum(msgId, dayNum + 1,orderSource);
			if(realEffectPicture != null){
				totalOrderCustomer = NumberUtils.getResult(realEffectPicture.getTotalBuyerReal());
				successCustomer = NumberUtils.getResult(realEffectPicture.getPayBuyerReal());//成交客户数
				waitCustomer = NumberUtils.getResult(realEffectPicture.getWaitPayBuyerReal());//未付款客户数
				failCustomer = NumberUtils.getResult(realEffectPicture.getRefundBuyerReal());//退款客户数
			}
			/*
			 * 实时查询的数据，太慢了，暂时不用
			 */
			//实时查询今日的数据(还未定时保存的),相加为最后的效果分析数据
			/*//时间的对比(发送时间、nowDate、endTime)
			long sendLongTime = msgSendRecord.getSendCreat().getTime();
			long todayLongTime = DateUtils.getStartTimeOfDay(new Date()).getTime();
			long endLongTime = endTime.getTime();
			if(endLongTime < todayLongTime){//效果分析结束时间小于现在时间，不需要计算实时的数据
				logger.info("指定号码群发的效果分析结束时间小于现在时间，不需要计算实时的数据,days:"+dayNum);
			}else {
				logger.info("指定号码群发的效果分析结束时间大于现在时间，需要计算实时的数据,days:"+dayNum);
				//效果分析结束时间大于现在时间，需要计算实时的数据
				//付款的订单状态的list
				List<String> payStatusList = new ArrayList<String>();
				payStatusList.add(TradeStatusUtils.TRADE_FINISHED);
				payStatusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
				payStatusList.add(TradeStatusUtils.TRADE_CLOSED);
				payStatusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
				payStatusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
				List<String> waitStatusList = new ArrayList<String>();
				waitStatusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
				waitStatusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
				try {
					Set<String> totalBuyer = new HashSet<String>(),successBuyer = new HashSet<String>(),waitBuyer = new HashSet<String>(),refundBuyer = new HashSet<String>();
					//获取效果分析时间段内所有的订单
					List<TradeDTO> tradeList = new ArrayList<TradeDTO>();
					if(sendLongTime < todayLongTime){
						tradeList = effectPictureService.todayEffectPictureNew(msgSendRecord, DateUtils.getStartTimeOfDay(new Date()), new Date(), phoneList,orderSource);
					}else {
						tradeList = effectPictureService.todayEffectPictureNew(msgSendRecord, msgSendRecord.getSendCreat(), new Date(), phoneList,orderSource);
					}
					if(tradeList != null && !tradeList.isEmpty()){
						for (TradeDTO tradeDTO : tradeList) {
							totalOrderMoney += NumberUtils.getResult(tradeDTO.getPayment());
							totalOrderNum += 1;
							totalOrderItemNum += NumberUtils.getResult(tradeDTO.getNum());
							totalBuyer.add(tradeDTO.getBuyerNick());
							if(payStatusList.contains(tradeDTO.getStatus())){
								successMoney += NumberUtils.getResult(tradeDTO.getPayment());
								successOrderNum += 1;
								successItemNum += NumberUtils.getResult(tradeDTO.getNum());
								successBuyer.add(tradeDTO.getBuyerNick());
							}
							if(waitStatusList.contains(tradeDTO.getStatus()) && false == tradeDTO.isRefundFlag()){
								waitMoney += NumberUtils.getResult(tradeDTO.getPayment());
								waitOrderNum += 1;
								waitItemNum += NumberUtils.getResult(tradeDTO.getNum());
								waitBuyer.add(tradeDTO.getBuyerNick());
							}
							if(true == tradeDTO.isRefundFlag()){
								failMoney += NumberUtils.getResult(tradeDTO.getPayment());
								failOrderNum += 1;
								failItemNum += NumberUtils.getResult(tradeDTO.getNum());
								refundBuyer.add(tradeDTO.getBuyerNick());
							}
						}
						totalOrderCustomer += totalBuyer.size();
						successCustomer += successBuyer.size();
						waitCustomer += waitBuyer.size();
						failCustomer += refundBuyer.size();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}*/
			Date log2 = new Date();
			logger.info("~~~~~~~~~~~~总页面数据查询完毕，开始计算每日数据~~~~~~~~" + (log2.getTime() - log1.getTime()) + "毫秒");
			//原始的每日数据，用来放Mysql和实时查询的数据，等待填充和排序
			List<EffectPicture> effectPictureList = new ArrayList<EffectPicture>();
			//查询mysql得到每日数据，因为凌晨同步，所以没有今日的数据
			List<EffectPicture> effectPictures = effectPictureService.allEffectByDay(msgId, orderSource, userId,dayNum + 1);
			/*
			 * 实时查询的数据，太慢了，暂时不用
			 */
			/*EffectPicture nowEffectPicture = null;
			if(todayLongTime > endLongTime){//效果分析结束时间小于现在时间，不需要计算实时的数据
			}else {//效果分析结束时间大于现在时间，需要计算实时的数据
				List<String> payStatusList = new ArrayList<String>();
				payStatusList.add(TradeStatusUtils.TRADE_FINISHED);
				payStatusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
				payStatusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
				payStatusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
				payStatusList.add(TradeStatusUtils.TRADE_CLOSED);
				//实时查询的今日数据
				Set<String> successBuyer = new HashSet<String>();
				long succItemNum = 0L;//zai zhe er
				double succMoney = 0.0;
				int succorderNum = 0;
				List<TradeDTO> tradeList = new ArrayList<TradeDTO>();
				Date endDate = null;
				try {
					if(sendLongTime < todayLongTime){
						tradeList = effectPictureService.todayEffectPictureNew(msgSendRecord, DateUtils.getStartTimeOfDay(new Date()), new Date(), phoneList,orderSource);
						endDate = DateUtils.getEndTimeOfDay(new Date());
					}else {
						tradeList = effectPictureService.todayEffectPictureNew(msgSendRecord, msgSendRecord.getSendCreat(), new Date(), phoneList,orderSource);
						endDate = DateUtils.getStartTimeOfDay(msgSendRecord.getSendCreat());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(tradeList != null && !tradeList.isEmpty()){
					for (TradeDTO tradeDTO : tradeList) {
						if(payStatusList.contains(tradeDTO.getStatus())){
							if("TOTAL".equals(orderSource)){
								successBuyer.add(tradeDTO.getBuyerNick());
								succItemNum += NumberUtils.getResult(tradeDTO.getNum());
								succMoney += NumberUtils.getResult(tradeDTO.getPayment());
								succorderNum += 1;
							}else {
								if(orderSource.equals(tradeDTO.getTradeFrom())){
									successBuyer.add(tradeDTO.getBuyerNick());
									succItemNum += NumberUtils.getResult(tradeDTO.getNum());
									succMoney += NumberUtils.getResult(tradeDTO.getPayment());
									succorderNum += 1;
								}
							}
						}
					}
				}
				//实时查询的今日数据，封装到EffectPicture对象
				nowEffectPicture = new EffectPicture();
				nowEffectPicture.setEffectTime(endDate);
				nowEffectPicture.setMsgId(msgId);
				nowEffectPicture.setPayBuyer(successBuyer.size());
				nowEffectPicture.setPayItem(succItemNum);
				nowEffectPicture.setPayFee(succMoney);
				nowEffectPicture.setPayOrder(succorderNum);
			}*/
			//原始的每日数据的集合添加mysql和实时查询的数据
			if(effectPictures != null && !effectPictures.isEmpty() && effectPictures.get(0) != null){
				effectPictureList.addAll(effectPictures);
//				effectPictureList.add(nowEffectPicture);
			}else {
//				effectPictureList.add(nowEffectPicture);
			}
			//封装并计算填充每日数据，返回到页面的每日数据(没有的填充为0)
			List<SuccessOrderDetail> finishedDetilList = new ArrayList<SuccessOrderDetail>();
			List<String> categoriesDataList = new ArrayList<String>();
			List<Double> seriesDataList = new ArrayList<Double>();
			for (int i = 0; i < dayNum; i++) {
				//每日的数据，先全部存成0
				SuccessOrderDetail unSetSOrderDetail = new SuccessOrderDetail();
				unSetSOrderDetail.setCustomerNum(0);
				unSetSOrderDetail.setEndTime(DateUtils.dateToString(DateUtils.nDaysAfter(i, msgSendRecord.getSendCreat())));
				unSetSOrderDetail.setItemNum(0);
				unSetSOrderDetail.setOrderNum(0);
				unSetSOrderDetail.setSuccessCusPrice(0.0);
				unSetSOrderDetail.setSuccessItemAverageNum(0.0);
				unSetSOrderDetail.setSuccessMoney(0.0);
				unSetSOrderDetail.setSuccessOrderRate(0.0);
				finishedDetilList.add(unSetSOrderDetail);
				categoriesDataList.add(DateUtils.dateToString(DateUtils.nDaysAfter(i, msgSendRecord.getSendCreat())));
				seriesDataList.add(0.0);
			}
			if(effectPictureList != null && !effectPictureList.isEmpty()){
				for (EffectPicture effectPicture2 : effectPictureList) {
					if(effectPicture2 != null){
						int effectDay = DateUtils.getDiffDay(msgSendRecord.getSendCreat(), DateUtils.getEndTimeOfDay(effectPicture2.getEffectTime())).intValue();
						logger.info("发送时间是：" + DateUtils.dateToStringHMS(msgSendRecord.getSendCreat()) + "分析日期是:" + DateUtils.dateToStringHMS(DateUtils.getEndTimeOfDay(effectPicture2.getEffectTime())) + "effectDay:" + effectDay);
						if(finishedDetilList.size() > effectDay){
							SuccessOrderDetail finishedDetail = new SuccessOrderDetail();
							int succCustomer = NumberUtils.getResult(effectPicture2.getPayBuyer());
							int succItemNum = (int)NumberUtils.getResult(effectPicture2.getPayItem());//zai zhe er
							double succMoney = NumberUtils.getResult(effectPicture2.getPayFee());
							int succorderNum = NumberUtils.getResult(effectPicture2.getPayOrder());
							finishedDetail.setCustomerNum(succCustomer);
							finishedDetail.setItemNum(succItemNum);
							finishedDetail.setSuccessMoney(succMoney);
							finishedDetail.setOrderNum(succorderNum);
							finishedDetail.setSuccessCusPrice(succCustomer == 0? 0.00 : NumberUtils.getTwoDouble(succMoney / (double)succCustomer));
							finishedDetail.setSuccessItemAverageNum(succCustomer == 0? 0.00:NumberUtils.getTwoDouble((double)succItemNum / (double)succCustomer));
							finishedDetail.setSuccessOrderRate(totalOrderCustomer == 0? 0.00 : NumberUtils.getFourDouble((double)succCustomer / (double)totalOrderCustomer));
							finishedDetail.setEndTime(DateUtils.dateToString(effectPicture2.getEffectTime()));
							finishedDetilList.set(effectDay, finishedDetail);
							seriesDataList.set(effectDay, NumberUtils.getTwoDouble(succMoney));
						}
					}
				}
			}
			Double smsSuccessRate = NumberUtils.getResult(totalSmsCustomer) == 0? 0.00 : ((double)NumberUtils.getResult(successSmsCustomer) / (double)NumberUtils.getResult(totalSmsCustomer));
			String RIO = creatROI(smsMoney, successMoney);
			double orderCusPrice = NumberUtils.getResult(totalOrderCustomer) == 0 ? 0.00 : NumberUtils.getResult(totalOrderMoney) / NumberUtils.getResult(totalOrderCustomer);//订单客单价
			double orderItemAverageNum = NumberUtils.getResult(totalOrderNum) == 0? 0.00:(double)NumberUtils.getResult(totalOrderItemNum) / (double)NumberUtils.getResult(totalOrderNum);//平均订单内商品数
			double placeOrderRate = NumberUtils.getResult(successSmsCustomer) == 0? 0.00 :  ((double)NumberUtils.getResult(totalOrderCustomer) / (double)NumberUtils.getResult(successSmsCustomer));//下订单率
			logger.info("！！！！！！！！！！！！！！！！！！！！！下订单率：" +placeOrderRate);
			logger.info("下单客户数:" + NumberUtils.getResult(totalOrderCustomer) +"   发送成功客户数："+ NumberUtils.getResult(successSmsCustomer));
			model.addAttribute("smsSuccessRate", NumberUtils.getFourDouble(NumberUtils.getResult(smsSuccessRate)));
			model.addAttribute("RIO", RIO);
			model.addAttribute("orderCusPrice", NumberUtils.getTwoDouble(orderCusPrice));
			model.addAttribute("orderItemAverageNum", NumberUtils.getTwoDouble(orderItemAverageNum));
			model.addAttribute("placeOrderRate", NumberUtils.getFourDouble(placeOrderRate));
			double successCusPrice = NumberUtils.getResult(successCustomer) == 0 ? 0.00 : NumberUtils.getResult(successMoney) / NumberUtils.getResult(successCustomer);//成交客单价
			double successItemAverageNum = NumberUtils.getResult(successOrderNum) == 0 ? 0.00 : (double)NumberUtils.getResult(successItemNum) / (double)NumberUtils.getResult(successOrderNum);//平均成交商品数
			double successOrderRate = NumberUtils.getResult(totalOrderCustomer) == 0 ? 0.00 :  ((double)NumberUtils.getResult(successCustomer) / (double)NumberUtils.getResult(totalOrderCustomer));//成交率
			model.addAttribute("successCusPrice", NumberUtils.getTwoDouble(successCusPrice));
			model.addAttribute("successItemAverageNum", NumberUtils.getTwoDouble(successItemAverageNum));
			model.addAttribute("successOrderRate", NumberUtils.getFourDouble(successOrderRate));
			double waitCusPrice = NumberUtils.getResult(waitCustomer) == 0? 0.00 : NumberUtils.getResult(waitMoney) / NumberUtils.getResult(waitCustomer);//未付款客单价
			double waitItemAverageNum = NumberUtils.getResult(waitOrderNum) == 0? 0.00 : (double)NumberUtils.getResult(waitItemNum) / (double)NumberUtils.getResult(waitOrderNum);//平均未付款商品数
			double waitOrderRate = NumberUtils.getResult(totalOrderCustomer) == 0 ? 0.00 :  ((double)NumberUtils.getResult(waitCustomer) / (double)NumberUtils.getResult(totalOrderCustomer));//未付款率
			model.addAttribute("waitCusPrice", NumberUtils.getTwoDouble(waitCusPrice));
			model.addAttribute("waitItemAverageNum", NumberUtils.getTwoDouble(waitItemAverageNum));
			model.addAttribute("waitOrderRate", NumberUtils.getFourDouble(waitOrderRate));
			double failCusPrice = NumberUtils.getResult(failCustomer) == 0? 0.00 : NumberUtils.getResult(failMoney) / NumberUtils.getResult(failCustomer);//退款客单价
			double failItemAverageNum = NumberUtils.getResult(failOrderNum) == 0? 0.00 : (double)(NumberUtils.getResult(failItemNum) / (double)NumberUtils.getResult(failOrderNum));//平均退款商品数
			double failOrderRate = NumberUtils.getResult(totalOrderCustomer) == 0? 0.00 :  ((double)NumberUtils.getResult(failCustomer) / (double)NumberUtils.getResult(totalOrderCustomer));//退款率
			model.addAttribute("failCusPrice", NumberUtils.getTwoDouble(failCusPrice));
			model.addAttribute("failItemAverageNum", NumberUtils.getTwoDouble(failItemAverageNum));
			model.addAttribute("failOrderRate", NumberUtils.getFourDouble(failOrderRate));
			int failOrderCustomer = NumberUtils.getResult(msgSendRecord.getSucceedCount()) - NumberUtils.getResult(totalOrderCustomer);//未下单客户数
			model.addAttribute("totalSmsCustomer", totalSmsCustomer);
			model.addAttribute("successSmsCustomer", successSmsCustomer);
			model.addAttribute("totalSmsNum", totalSmsNum);
			model.addAttribute("smsMoney", NumberUtils.getTwoDouble(NumberUtils.getResult(smsMoney)));
			model.addAttribute("failOrderCustomer", failOrderCustomer);
			
			model.addAttribute("totalOrderCustomer", totalOrderCustomer);
			model.addAttribute("totalOrderMoney", NumberUtils.getTwoDouble(NumberUtils.getResult(totalOrderMoney)));
			model.addAttribute("totalOrderNum", totalOrderNum);
			model.addAttribute("totalOrderItemNum", totalOrderItemNum);
			
			model.addAttribute("successCustomer", successCustomer);
			model.addAttribute("successMoney", NumberUtils.getTwoDouble(NumberUtils.getResult(successMoney)));
			model.addAttribute("successOrderNum", successOrderNum);
			model.addAttribute("successItemNum", successItemNum);
			
			model.addAttribute("waitCustomer", waitCustomer);
			model.addAttribute("waitMoney", NumberUtils.getTwoDouble(NumberUtils.getResult(waitMoney)));
			model.addAttribute("waitOrderNum", waitOrderNum);
			model.addAttribute("waitItemNum", waitItemNum);
			
			model.addAttribute("failCustomer", failCustomer);
			model.addAttribute("failMoney", NumberUtils.getTwoDouble(NumberUtils.getResult(failMoney)));
			model.addAttribute("failOrderNum", failOrderNum);
			model.addAttribute("failItemNum", failItemNum);
			//查询条件回显
			model.addAttribute("type", type);
			model.addAttribute("beginTime", DateUtils.dateToStringHMS(beginTime));
			model.addAttribute("endTime", DateUtils.dateToStringHMS(endTime));
			model.addAttribute("msgId", msgId);
			model.addAttribute("dayNum", dayNum);
			model.addAttribute("orderSource", orderSource);
			
			//效果分析的折线图X轴(时间)和Y轴(金额)
			model.addAttribute("finishedDetilList", finishedDetilList);
			model.addAttribute("categoriesDataList", categoriesDataList);
			model.addAttribute("seriesDataList", seriesDataList);
		} catch (NumberFormatException e) {
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~NumberFormatException!!!!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			e.printStackTrace();
		}
		logger.info("~~~~~~~~~~~~~~~~~reuturn到页面crms/marketingCenter/memberEffectPicture~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return "crms/marketingCenter/appointNumberEffectPicture";
	}
	
	/**
	 * 指定号码发送客户详情
	 * ZTK2017年8月15日下午2:14:39
	 */
//	@RequestMapping("/customerDetail")
	@RequestMapping("/customerIndex")
	public String customerDetail(HttpServletRequest request,String orderSource,Integer pageNo,
			String buyerNick,String phone,String itemId,Model model,
			@RequestParam(value="customerType",defaultValue="total")String customerType){
		if(orderSource == null || "".equals(orderSource) || "0".equals(orderSource)){
			orderSource = null;
		}
		if(pageNo == null){
			pageNo = 1;
		}
		if(itemId != null && !"".equals(itemId)){
			itemId.trim();
		}
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		Date beginTime = (Date) map.get(userId + "beginTime");
//		Date endTime = (Date) map.get(userId + "endTime");
		String bTimeStr = redisLockServiceImpl.getStringValue(userId + "beginTime");
		String eTimeStr = redisLockServiceImpl.getStringValue(userId + "endTime");
		Date beginTime = DateUtils.convertDate(bTimeStr);
		Date endTime = DateUtils.convertDate(eTimeStr);
		List<String> statusList = null;
		Boolean refundStatus = null;
		if("success".equals(customerType)){
			statusList = new ArrayList<String>();
			statusList.add(TradeStatusUtils.TRADE_FINISHED);
			statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
			statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
			statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
		}else if("wait".equals(customerType)){
			statusList = new ArrayList<String>();
			statusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
			statusList.add(TradeStatusUtils.TRADE_CLOSED);
			statusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
			refundStatus = false;
		}else if("fail".equals(customerType)){
			refundStatus = true;
		}
		Long msgId = null;
		String msgIdStr = redisLockServiceImpl.getStringValue(userId + "msgId");
		if(msgIdStr != null && !"".equals(msgIdStr)){
			msgId = Long.parseLong(msgIdStr);
		}
		List<String> phoneList = new ArrayList<String>();
		Criteria criteria = new Criteria("userId").is(userId);
		criteria.and("status").is(2);
		criteria.and("type").is("34");
		if(msgId != null){
			criteria.and("msgId").is(msgId);
			phoneList = smsRecordService.findNumList(new Query(criteria), userId);
		}
		logger.info("======================msgId是:" + msgId + "~~~~~~~~~~~~~~~~~~");
		logger.info("======================beginTime是:" + beginTime + "~~~~~~~~~~~~~~~~~~");
		logger.info("======================endTime是:" + endTime + "~~~~~~~~~~~~~~~~~~");
		Pagination pagination = null;
		try {
			pagination = tradeInfoService.customerDetailList(userId, statusList, beginTime, endTime, buyerNick, phone, itemId,pageNo,refundStatus,phoneList,orderSource);
		} catch (SecretException e) {
			e.printStackTrace();
		}
		String url = request.getContextPath() + "/appointNumber/customerIndex";
		StringBuilder params = new StringBuilder();
		if(orderSource != null && !"".equals(orderSource)){
			params.append("&orderSource=").append(orderSource);
		}
		if(customerType != null && !"".equals(customerType)){
			params.append("&customerType=").append(customerType);
		}
		if(buyerNick != null && !"".equals(buyerNick)){
			params.append("&buyerNick=").append(buyerNick);
		}
		if(phone != null && !"".equals(phone)){
			params.append("&phone=").append(phone);
		}
		if(itemId != null && !"".equals(itemId)){
			params.append("&itemId=").append(itemId);
		}
		if(pagination != null){
			pagination.pageView(url, params.toString());
		}
		model.addAttribute("orderSource", orderSource);
		model.addAttribute("customerType", customerType);
		model.addAttribute("buyerNick", buyerNick);
		model.addAttribute("phone", phone);
		model.addAttribute("itemId", itemId);
		model.addAttribute("beginTime", DateUtils.dateToStringHMS(beginTime));
		model.addAttribute("endTime", DateUtils.dateToStringHMS(endTime));
		model.addAttribute("pagination", pagination);
		Integer dayNum = 0;
		String dayNumStr = redisLockServiceImpl.getStringValue(userId + "dayNum");
		if(dayNumStr != null && !"".equals(dayNumStr)){
			dayNum = Integer.parseInt(dayNumStr);
		}
		model.addAttribute("dayNum", dayNum);
		return "crms/marketingCenter/appointNumberCustomerDetail";
	}
	
	/**
	 * 指定号码发送商品详情
	 * ZTK2017年8月15日下午2:33:50
	 */
//	@RequestMapping("/itemDetail")
	@RequestMapping("/itemIndex")
	public String itemDetail(HttpServletRequest request,String orderSource,
			Integer pageNo,Long itemId,Model model,
			@RequestParam(value="customerType",defaultValue="total")String customerType){
		logger.info("=========商品统计：   ：：刚刚进入商品统计方法~~~~" + new Date() +    "~~~~~~~~~~~~~~~~~");
		if(pageNo == null){
			pageNo = 1;
		}
		if(orderSource == null || "".equals(orderSource) || "0".equals(orderSource)){
			orderSource = null;
		}
		List<String> statusList = null;
		Boolean refundStatus = null;
		if("success".equals(customerType)){
			statusList = new ArrayList<String>();
			statusList.add(TradeStatusUtils.TRADE_FINISHED);
			statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
			statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
			statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
		}else if("wait".equals(customerType)){
			statusList = new ArrayList<String>();
			statusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
			statusList.add(TradeStatusUtils.TRADE_CLOSED);
			statusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
		}else if("fail".equals(customerType)){
			refundStatus = true;
		}
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Long msgId = null;
		String msgIdStr = redisLockServiceImpl.getStringValue(userId + "msgId");
		if(msgIdStr != null && !"".equals(msgIdStr)){
			msgId = Long.parseLong(msgIdStr) ;
		}
//		Date endTime = (Date) map.get(userId + "endTime");
//		Date beginTime = (Date) map.get(userId + "beginTime");
		String bTimeStr = redisLockServiceImpl.getStringValue(userId + "beginTime");
		String eTimeStr = redisLockServiceImpl.getStringValue(userId + "endTime");
		Date beginTime = DateUtils.convertDate(bTimeStr);
		Date endTime = DateUtils.convertDate(eTimeStr);
		logger.info("=========商品统计：：：进入商品统计方法~~~~msgId:" +msgId +    "~~~~~~~~~~~~~~~~~");
		logger.info("======================beginTime是:" + beginTime + "~~~~~~~~~~~~~~~~~~");
		logger.info("======================endTime是:" + endTime + "~~~~~~~~~~~~~~~~~~");
		List<String> phoneList = new ArrayList<String>();
		Criteria criteria = new Criteria("userId").is(userId);
		criteria.and("status").is(2);
		criteria.and("type").is("34");
		if(msgId != null){
			criteria.and("msgId").is(msgId);
			phoneList = smsRecordService.findNumList(new Query(criteria), userId);
		}
		Pagination pagination = null;
		try {
			pagination = tradeInfoService.findTradesEffect(userId, phoneList, orderSource, statusList, itemId, refundStatus, beginTime, endTime, pageNo);
		} catch (SecretException e) {
			e.printStackTrace();
		}
		logger.info("=========商品统计：：短信长度计算完毕~~~~" + new Date() + "    ~~~~~~~~~~~~~~~~~~");
		String url = request.getContextPath() + "/appointNumber/itemIndex";
		StringBuilder params = new StringBuilder();
		if(customerType != null && !"".equals(customerType)){
			params.append("&customerType=").append(customerType);
		}
		if(orderSource != null && !"".equals(orderSource)){
			params.append("&orderSource=").append(orderSource);
		}
		if(itemId != null && !"".equals(itemId)){
			params.append("&itemId=").append(itemId);
		}
		if(pagination != null){
			pagination.pageView(url, params.toString());
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("customerType", customerType);
		model.addAttribute("orderSource", orderSource);
		model.addAttribute("itemId", itemId);
		model.addAttribute("beginTime", DateUtils.dateToStringHMS(beginTime));
		model.addAttribute("endTime", DateUtils.dateToStringHMS(endTime));
		model.addAttribute("pagination", pagination);
		Integer dayNum = 0;
		String dayNumStr = redisLockServiceImpl.getStringValue(userId + "dayNum");
		if(dayNumStr != null && !"".equals(dayNumStr)){
			dayNum = Integer.parseInt(dayNumStr);
		}
		model.addAttribute("dayNum", dayNum);
		return "crms/marketingCenter/appointNumberItemDetail";
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
			return result + ":0";
		}
	}
}
