package s2jh.biz.shop.crm.order.web;

import java.math.BigDecimal;
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

import lab.s2jh.core.service.CacheService;
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

import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;
import s2jh.biz.shop.crm.order.entity.EffectPicture;
import s2jh.biz.shop.crm.order.pojo.SuccessOrderDetail;
import s2jh.biz.shop.crm.order.service.EffectPictureService;
import s2jh.biz.shop.crm.order.service.OrderService;
import s2jh.biz.shop.crm.order.service.TransactionTradeService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.TradeStatusUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

import com.taobao.api.SecretException;

@Controller
public class MemberEffectPictureController {

	private Logger logger = LoggerFactory.getLogger(MemberEffectPictureController.class);
	
	@Autowired
	private SmsSendRecordService smsSendRecordService;
	
	@Autowired
	private TransactionTradeService transactionTradeService;
	
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private VipMemberService vipMemberService;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private EffectPictureService effectPictureService;
	
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	@Autowired
	private SmsRecordService smsRecordService;
	
	
	@Deprecated
//	@RequestMapping("/memberEffectIndex")
	public String memberEffectIndex(Model model,Long msgId,HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(msgId != null){
//			cacheService.putString(userId + "MSGID", msgId);
			redisLockServiceImpl.putStringValueWithExpireTime(userId + "msgId", msgId + "",TimeUnit.HOURS,1L);
		}else {
//			msgId = cacheService.getString(userId + "MSGID");
			String msgIdStr = redisLockServiceImpl.getStringValue(userId + "msgId");
			msgId = Long.parseLong(msgIdStr);
		}
		MsgSendRecord msgSendRecord = msgSendRecordService.findOne(msgId);
		Date beginTime = msgSendRecord.getSendCreat();
		Date endTime = DateUtils.getTimeByDay(beginTime, 3);
		model.addAttribute("beginTime", DateUtils.dateToStringHMS(beginTime));
		model.addAttribute("endTime", DateUtils.dateToStringHMS(endTime));
		model.addAttribute("message", 1);
		return "crms/marketingCenter/memberEffectPicture";
	}
	
//	@RequestMapping("/memberCustomerIndex")
	@Deprecated
	public String memberCustomerDetailIndex(Model model){
		model.addAttribute("message", 1);
		return "crms/marketingCenter/memberCustomerDetail";
	}
	
//	@RequestMapping("/memberItemIndex")
	@Deprecated
	public String memberItemDetailIndex(Model model){
		model.addAttribute("message", 1);
		return "crms/marketingCenter/memberItemDetail";
	}
	
	/**
	 * 会员短信群发列表
	 * ZTK2017年6月8日上午10:57:21
	 */
	@RequestMapping("/member/msgSendRecord")
	public String memberMsgRecord(HttpServletRequest request,String activityName,String bTime,String eTime,Model model,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Date beginTime = DateUtils.convertDate(bTime);
		Date endTime = DateUtils.convertDate(eTime);
		String msgType= MsgType.MSG_HYDXQF;
		String contextPath = request.getContextPath()+"/member/msgSendRecord";
		if(activityName!=null) activityName=activityName.trim();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("activityName", activityName);
		map.put("isSent", true);
		map.put("type", msgType);
		map.put("userId", userId);
		Pagination pagination = msgSendRecordService.sendRecordPagination(contextPath,pageNo,map);
		String url = request.getContextPath() + "/member/msgSendRecord";
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
		return "crms/marketingCenter/memberMsgRecord";
	}
	
	/**
	 * 效果分析
	 * 	ZTK2017年6月8日上午11:17:13
	 * @throws Exception 
	 */
//	@RequestMapping("/member/effectPicture")
	@RequestMapping("/memberEffectIndex")
	public String memberEffectPicture(HttpServletRequest request,HttpServletResponse response,
			String type,Long msgId,Model model,String orderSource,
			@RequestParam(value="dayNum",defaultValue="3",required=false)Integer dayNum) throws Exception{
			//根据userId和卖家筛选的天数查询发送记录 type=2
			String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		try {
			logger.info("======================开始进行效果分析~~~~" + new Date() + "~~~~~~~~~~~~~~~~~~");
			if("0".equals(orderSource) || "".equals(orderSource) || orderSource == null){
				orderSource = "TOTAL";
			}
			if(msgId != null && !"".equals(msgId)){
				redisLockServiceImpl.putStringValueWithExpireTime(userId + "msgId", msgId + "", TimeUnit.HOURS, 1L);
			}else {
				String msgIdStr = redisLockServiceImpl.getStringValue(userId + "msgId");
				if(msgIdStr != null && !"".equals(msgIdStr)){
					msgId = Long.parseLong(msgIdStr);
				}
			}
			redisLockServiceImpl.putStringValueWithExpireTime(userId + "dayNum", dayNum + "",TimeUnit.HOURS, 1L);
			MsgSendRecord msgSendRecord = msgSendRecordService.findOne(msgId);
			type = MsgType.MSG_HYDXQF;
			Date beginTime = msgSendRecord.getSendCreat();
			Date endTime = DateUtils.getEndTimeOfDay(DateUtils.nDaysAfter(dayNum - 1, beginTime));
			redisLockServiceImpl.putStringValueWithExpireTime(userId + "beginTime", DateUtils.dateToStringHMS(beginTime), TimeUnit.HOURS, 1L);
			redisLockServiceImpl.putStringValueWithExpireTime(userId + "endTime", DateUtils.dateToStringHMS(endTime), TimeUnit.HOURS, 1L);
			logger.info("=========开始计算所需数据~~~~" + new Date() + "    ~msgId："+msgId+"~~~~~~~~~~~~~~~~~");
			//计算短信长度
			int messageCount = msgSendRecord.getTemplateContent().length();
			if(messageCount<=70){
				messageCount=1;
			}else{
				messageCount = (messageCount+66)/67;
			}
			Integer totalSmsCustomer = msgSendRecord.getTotalCount();//群发客户总数
			Integer successSmsCustomer = msgSendRecord.getSucceedCount();//成功发送客户数
			Integer totalSmsNum = getResult(msgSendRecord.getSucceedCount()) * messageCount;//消耗短信条数
			Double smsMoney = getResult(msgSendRecord.getSucceedCount()) * messageCount * 0.05;//消费短信金额
			/*
			 * 实时查询的数据所需手机号，太慢了，暂时不用
			 */
//			List<String> phoneList = vipMemberService.findMemberPhoneList(userId, msgId, beginTime, endTime);
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
			//实时查询今日的数据(还未定时保存的),相加为最后的效果分析数据
			//时间的对比(发送时间、nowDate、endTime)
			/*
			 * 实时查询的数据，太慢了，暂时不用
			 */
			/*long sendLongTime = msgSendRecord.getSendCreat().getTime();
			long todayLongTime = DateUtils.getStartTimeOfDay(new Date()).getTime();
			long endLongTime = endTime.getTime();
			if(endLongTime < todayLongTime){//效果分析结束时间小于现在时间，不需要计算实时的数据
				logger.info("效果分析结束时间小于现在时间，不需要计算实时的数据");
			}else {//效果分析结束时间大于现在时间，需要计算实时的数据
				logger.info("效果分析结束时间da于现在时间，需要计算实时的数据");
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
//					List<TradeDTO> tradeList = effectPictureService.todayEffectPictureNew(msgSendRecord, msgSendRecord.getSendCreat(), new Date(), phoneList,orderSource);
					if(tradeList != null && !tradeList.isEmpty()){
						for (TradeDTO tradeDTO : tradeList) {
							logger.info("查询实时数据订单数:"+tradeList.size()+"订单金额："+tradeDTO.getPayment());
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
				} catch (SecretException e) {
					e.printStackTrace();
				}
			}*/
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~总页面数据查询完毕，开始计算每日数据~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			//原始的每日数据，用来放Mysql和实时查询的数据，等待填充和排序
			List<EffectPicture> effectPictureList = new ArrayList<EffectPicture>();
			//查询mysql得到每日数据，因为凌晨同步，所以没有今日的数据
			List<EffectPicture> effectPictures = effectPictureService.allEffectByDay(msgId, orderSource, userId, dayNum + 1);
			/*
			 * 实时查询的数据，太慢了，暂时不用
			 */
			/*//实时查询的今日数据，封装到EffectPicture对象
			EffectPicture nowEffectPicture = null;
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
				} catch (SecretException e) {
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
						logger.info("~~~~~~~~~~~~会员效果分析effectDay:" + effectDay + "effectPictureList.size:" + effectPictureList.size());
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
			Double smsSuccessRate = getResult(totalSmsCustomer) == 0? 0.00 : ((double)getResult(successSmsCustomer) / (double)getResult(totalSmsCustomer));
			String RIO = creatROI(smsMoney, successMoney);
			double orderCusPrice = getResult(totalOrderCustomer) == 0 ? 0.00 : getResult(totalOrderMoney) / getResult(totalOrderCustomer);//订单客单价
			double orderItemAverageNum = getResult(totalOrderNum) == 0? 0.00:(double)getResult(totalOrderItemNum) / (double)getResult(totalOrderNum);//平均订单内商品数
			double placeOrderRate = getResult(successSmsCustomer) == 0? 0.00 :  ((double)getResult(totalOrderCustomer) / (double)getResult(successSmsCustomer));//下订单率
			logger.info("！！！！！！！！！！！！！！！！！！！！！下订单率：" +placeOrderRate);
			logger.info("下单客户数:" + getResult(totalOrderCustomer) +"   发送成功客户数："+getResult(successSmsCustomer));
			model.addAttribute("smsSuccessRate", getFourDouble(getResult(smsSuccessRate)));
			model.addAttribute("RIO", RIO);
			model.addAttribute("orderCusPrice", getTwoDouble(orderCusPrice));
			model.addAttribute("orderItemAverageNum", getTwoDouble(orderItemAverageNum));
			model.addAttribute("placeOrderRate", getFourDouble(placeOrderRate));
			double successCusPrice = getResult(successCustomer) == 0 ? 0.00 : getResult(successMoney) / getResult(successCustomer);//成交客单价
			double successItemAverageNum = getResult(successOrderNum) == 0 ? 0.00 : (double)getResult(successItemNum) / (double)getResult(successOrderNum);//平均成交商品数
			double successOrderRate = getResult(totalOrderCustomer) == 0 ? 0.00 :  ((double)getResult(successCustomer) / (double)getResult(totalOrderCustomer));//成交率
			model.addAttribute("successCusPrice", getTwoDouble(successCusPrice));
			model.addAttribute("successItemAverageNum", getTwoDouble(successItemAverageNum));
			model.addAttribute("successOrderRate", getFourDouble(successOrderRate));
			double waitCusPrice = getResult(waitCustomer) == 0? 0.00 : getResult(waitMoney) / getResult(waitCustomer);//未付款客单价
			double waitItemAverageNum = getResult(waitOrderNum) == 0? 0.00 : (double)getResult(waitItemNum) / (double)getResult(waitOrderNum);//平均未付款商品数
			double waitOrderRate = getResult(totalOrderCustomer) == 0 ? 0.00 :  ((double)getResult(waitCustomer) / (double)getResult(totalOrderCustomer));//未付款率
			model.addAttribute("waitCusPrice", getTwoDouble(waitCusPrice));
			model.addAttribute("waitItemAverageNum", getTwoDouble(waitItemAverageNum));
			model.addAttribute("waitOrderRate", getFourDouble(waitOrderRate));
			double failCusPrice = getResult(failCustomer) == 0? 0.00 : getResult(failMoney) / getResult(failCustomer);//退款客单价
			double failItemAverageNum = getResult(failOrderNum) == 0? 0.00 : (double)(getResult(failItemNum) / (double)getResult(failOrderNum));//平均退款商品数
			double failOrderRate = getResult(totalOrderCustomer) == 0? 0.00 :  ((double)getResult(failCustomer) / (double)getResult(totalOrderCustomer));//退款率
			model.addAttribute("failCusPrice", getTwoDouble(failCusPrice));
			model.addAttribute("failItemAverageNum", getTwoDouble(failItemAverageNum));
			model.addAttribute("failOrderRate", getFourDouble(failOrderRate));
			int failOrderCustomer = getResult(msgSendRecord.getSucceedCount()) - getResult(totalOrderCustomer);//未下单客户数
			model.addAttribute("totalSmsCustomer", totalSmsCustomer);
			model.addAttribute("successSmsCustomer", successSmsCustomer);
			model.addAttribute("totalSmsNum", totalSmsNum);
			model.addAttribute("smsMoney", getTwoDouble(getResult(smsMoney)));
			model.addAttribute("failOrderCustomer", failOrderCustomer);
			
			model.addAttribute("totalOrderCustomer", totalOrderCustomer);
			model.addAttribute("totalOrderMoney", getTwoDouble(getResult(totalOrderMoney)));
			model.addAttribute("totalOrderNum", totalOrderNum);
			model.addAttribute("totalOrderItemNum", totalOrderItemNum);
			
			model.addAttribute("successCustomer", successCustomer);
			model.addAttribute("successMoney", getTwoDouble(getResult(successMoney)));
			model.addAttribute("successOrderNum", successOrderNum);
			model.addAttribute("successItemNum", successItemNum);
			
			model.addAttribute("waitCustomer", waitCustomer);
			model.addAttribute("waitMoney", getTwoDouble(getResult(waitMoney)));
			model.addAttribute("waitOrderNum", waitOrderNum);
			model.addAttribute("waitItemNum", waitItemNum);
			
			model.addAttribute("failCustomer", failCustomer);
			model.addAttribute("failMoney", getTwoDouble(getResult(failMoney)));
			model.addAttribute("failOrderNum", failOrderNum);
			model.addAttribute("failItemNum", failItemNum);
			
			//查询条件回显
			model.addAttribute("type", type);
			model.addAttribute("beginTime", DateUtils.dateToStringHMS(beginTime));
			model.addAttribute("endTime", DateUtils.dateToStringHMS(endTime));
			model.addAttribute("msgId", msgId);
			model.addAttribute("dayNum", dayNum);
			
			//效果分析的折线图X轴(时间)和Y轴(金额)
			model.addAttribute("finishedDetilList", finishedDetilList);
			model.addAttribute("categoriesDataList", categoriesDataList);
			model.addAttribute("seriesDataList", seriesDataList);
			model.addAttribute("orderSource", orderSource);
		} catch (NumberFormatException e) {
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~NumberFormatException!!!!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			e.printStackTrace();
		}
		logger.info("~~~~~~~~~~~~~~~~~reuturn到页面crms/marketingCenter/memberEffectPicture~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return "crms/marketingCenter/memberEffectPicture";
	}
	
	/**
	 * 客户详情
	 * ZTK2017年6月9日下午3:00:31
	 */
//	@RequestMapping("/member/memberCustomerDetail")
	@RequestMapping("/memberCustomerIndex")
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
		List<String> phoneList = vipMemberService.findMemberPhoneList(userId, msgId, beginTime, endTime);
		logger.info("msgId是:" + msgId + "beginTime是:" + beginTime + "=endTime是:" + endTime);
		Pagination pagination = null;
		try {
			pagination = tradeInfoService.customerDetailList(userId, statusList, beginTime, endTime, buyerNick, phone, itemId,pageNo,refundStatus,phoneList,orderSource);
		} catch (SecretException e) {
			e.printStackTrace();
		}
		String url = request.getContextPath() + "/memberCustomerIndex";
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
		model.addAttribute("dayNum", dayNum);
		return "crms/marketingCenter/memberCustomerDetail";
	}
	
	/**
	 * 商品详情
	 * ZTK2017年6月9日下午3:00:31
	 */
//	@RequestMapping("/member/memberItemDetail")
	@RequestMapping("/memberItemIndex")
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
		String bTimeStr = redisLockServiceImpl.getStringValue(userId + "beginTime");
		String eTimeStr = redisLockServiceImpl.getStringValue(userId + "endTime");
		Date beginTime = DateUtils.convertDate(bTimeStr);
		Date endTime = DateUtils.convertDate(eTimeStr);
		logger.info("=========商品统计：：：进入商品统计方法~~~~msgId:" +msgId +"beginTime是:" + beginTime + "endTime是:" + endTime);
		List<String> phoneList = vipMemberService.findMemberPhoneList(userId, msgId, beginTime, endTime);
		Pagination pagination = null;
		try {
			pagination = tradeInfoService.findTradesEffect(userId, phoneList, orderSource, statusList, itemId, refundStatus, beginTime, endTime, pageNo);
		} catch (SecretException e) {
			e.printStackTrace();
		}
		logger.info("=========商品统计：：短信长度计算完毕~~~~" + new Date() + "    ~~~~~~~~~~~~~~~~~~");
		String url = request.getContextPath() + "/memberItemIndex";
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
		model.addAttribute("dayNum", dayNum);
		return "crms/marketingCenter/memberItemDetail";
	}
	
	
	private int getResult(Integer i){
		if(i!=null){
			return i;
		}
		return 0;
	}
	private double getResult(Double d){
		if(d!=null){
			return d;
		}
		return 0.00;
	}
	
	private long getResult(Long l){
		if(l!=null){
			return l;
		}
		return 0l;
	}
	private double getFourDouble(double d){
		BigDecimal bigDecimal = new BigDecimal(d);
		bigDecimal = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);
		double doubleValue = bigDecimal.doubleValue();
		return doubleValue;
	}
	private double getTwoDouble(double d){
		BigDecimal bigDecimal = new BigDecimal(d);
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		double doubleValue = bigDecimal.doubleValue();
		return doubleValue;
	}
	
	/**
	 * 计算ROI
	 * ZTK2017年7月7日上午9:54:08
	 */
	public String creatROI(Double small,Double big){
		double result = getResult(small);
		double result2 = getResult(big);
		if(result != 0.00){
			double roi = result2 / result;
			return "1:" + getTwoDouble(roi);
		}else {
			return result + ":0";
		}
	}
	
	
}
