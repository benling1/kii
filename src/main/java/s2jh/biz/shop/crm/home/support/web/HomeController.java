package s2jh.biz.shop.crm.home.support.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import lab.s2jh.core.util.DateUtils;
import lab.s2jh.core.util.NumberUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.data.entity.ShopDataStatistics;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.message.entity.SmsReceiveInfo;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.message.service.SmsReceiveInfoService;
import s2jh.biz.shop.crm.order.pojo.EffectNum;
import s2jh.biz.shop.crm.order.pojo.ReminderNum;
import s2jh.biz.shop.crm.order.service.EffectPictureService;
import s2jh.biz.shop.crm.order.service.OrderRateCareSetupService;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.seller.entity.SellerGroup;
import s2jh.biz.shop.crm.seller.service.SellerGroupService;
import s2jh.biz.shop.crm.tradecenter.entity.TradeCenterEffect;
import s2jh.biz.shop.crm.tradecenter.service.TradeCenterEffectService;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.TradeStatusUtils;

import com.taobao.api.SecretException;



@Controller
@RequestMapping(value = "/")
public class HomeController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SellerGroupService sellerGroupService;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Autowired
	private VipMemberService vipMemberService;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	
	@Autowired
	private SmsReceiveInfoService smsReceiveInfoService;
	
	@Autowired
	private OrderSetupService orderSetupService;
	
	@Autowired
	private OrderRateCareSetupService orderRateCareSetupService;
	
	@Autowired
	private TransactionOrderService transactionOrderService;
	
	@Autowired
	private EffectPictureService effectPictureService;
	
	@Autowired
	private TradeCenterEffectService tradeCenterEffectService;
	/**
	 * 首页
	 *
	 * @param model
	 * @return
	 * @throws ParseException
	 */

	@RequestMapping(value = "/crms/home/index", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request)
			throws ParseException {
		return "crms/home/index";
	}

	// 跳转到营销中心模块下的会员群发短信
	@RequestMapping(value = "/marketingCenter", method = RequestMethod.GET)
	public String marketingCenter(HttpServletRequest request,Model model) {
		String userId = (String)request.getSession().getAttribute("taobao_user_nick");
		//获取卖家所有的分组信息
		List<SellerGroup> groupList = sellerGroupService.getAllSellerGroup(userId);
		model.addAttribute("groupList",groupList);
		return "crms/marketingCenter/memberMessageSend";
	}

	@RequestMapping(value = "/crms/home/notice")
	public String notice() {
		return "crms/home/notice";
	}

//	/**
//	 * 根据卖家用户昵称判断是否有手机号和QQ号
//	 * 
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/crms/home/findPhone")
//	public String findPhone(HttpServletRequest request,
//			HttpServletResponse response) {
//		// 从session中取出用户昵称
//		String userId = (String) request.getSession().getAttribute(
//				"taobao_user_nick");
//
//		JSONObject js = new JSONObject();
//		// 查询卖家用户信息
//		UserInfo ui = new UserInfo();
//		ui = userInfoService.getUserInfoByNick(userId);
//		// 判断是否有手机号和QQ号
//		if (ui!=null&&ui.getMobile() != null&&!"".equals(ui.getMobile())) {
//			js.put("status", 0);
//		}
//		try {
//			response.getWriter().write(js.toJSONString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
	
	
	/**
	 * 首页信息获取
	 * @param model
	 * @param userId
	 * @param request
	 * @throws ParseException
	 */
	@RequestMapping("/home/index/getData")
	public String getData(Model model,HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		// 首页用户信息查询
		UserInfo userInfo = userInfoService.findUserInfo(userId);
		// 软件到期天数
		/*Date expirationTime = userInfo.getExpirationTime();
		long dayCount = 0L;
		if (expirationTime != null)
			dayCount = (expirationTime.getTime() - new Date().getTime())/(24 * 3600 * 1000);*/
		//request.getSession().setAttribute("dayCount", dayCount);
		request.getSession().setAttribute("userInfo", userInfo);
		//昨日首页数据
		/*Map<String, Object> findMap = myBatisDao.findMap(ShopDataStatistics.class.getName(), "findYesterDayNums", params, "");*/
		//List<ShopDataStatistics> shopDateStatistics =myBatisDao.findList(ShopDataStatistics.class.getName(), "findYesterDayNums", map);
		Map<String, Date> map = DateUtils.caculateDate(-1);
		List<ShopDataStatistics> shopDateStatistics = tradeInfoService.getTradeDataByDate(map, userId);
		model.addAttribute("shopDateStatistics", shopDateStatistics);
		System.out.println("**************************"+shopDateStatistics.size()+"*****************************");
		
		//昨日首页数据
		//List<ShopDataStatistics> BeforshopDateStatistics =myBatisDao.findList(ShopDataStatistics.class.getName(), "findBeforeYesterDayNums", maps);
		Map<String, Date> ymap = DateUtils.caculateDate(-2);
		List<ShopDataStatistics> BeforshopDateStatistics = tradeInfoService.getTradeDataByDate(ymap, userId);
		model.addAttribute("BeforshopDateStatistics", BeforshopDateStatistics);
		
		// 判断用户信息是否包含手机号与qq号 ==>弹出小红框
		if (userInfo.getQqNum() == null || userInfo.getMobile() == null) {
			model.addAttribute("display", 1);
		}
	
		return "crms/home/index";
		
	}
	
	/**
	 * 首页数据(NEW)——————查询用户信息、软件到期天数、买家回复短信内容、店铺会员数、快速导航图
	 * ZTK2017年7月24日下午4:45:13
	 */
	@RequestMapping("/member/index")
	public String indexGetData(HttpServletRequest request,Model model){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Date log0 = new Date();
		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~首页数据开始计算：" + log0);
		// 首页用户信息查询
		UserInfo userInfo = userInfoService.findUserInfo(userId);
		// 软件到期天数
//		Date expirationTime = userInfo.getExpirationTime();
//		long dayCount = 0L;
//		if (expirationTime != null)dayCount = (expirationTime.getTime() - new Date().getTime())/(24 * 3600 * 1000);
		// 判断用户信息是否包含手机号与qq号 ==>弹出小红框
		if (userInfo.getQqNum() == null || userInfo.getMobile() == null) {
			model.addAttribute("display", 1);
		}
//		Date log1 = new Date();
//		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~软件到期天数查询时间：" + (log1.getTime() - log0.getTime())+"毫秒");
//		Date log4 = new Date(); 
//		//店铺买家回复内容查询
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("startRows", 0);
//		map.put("currentRows", 100);
//		map.put("notContainN", "N");
//		List<SmsReceiveInfo> receiveInfos = new ArrayList<SmsReceiveInfo>();
//		try {
//			receiveInfos = smsReceiveInfoService.findReceiveInfo(map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Date log5 = new Date();
//		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~店铺买家回复内容查询时间：" + (log5.getTime() - log4.getTime())+"毫秒");
		//计算店铺会员数
//		long memberCount = vipMemberService.findMemberCount(userId);
		Date log6 = new Date();
//		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~计算店铺会员数时间：" + (log6.getTime() - log5.getTime())+"毫秒");
		//首页流程图查询开启关闭
		List<String> settingTypeList = orderSetupService.findOrderSetupOfStatus(userId);
		List<String> rateCareSetupList = orderRateCareSetupService.orderRateCareSetupIsOpen(userId);
		if(settingTypeList != null && rateCareSetupList != null){
			if(rateCareSetupList.contains("0"))settingTypeList.add("16");
			if(rateCareSetupList.contains("1"))settingTypeList.add("20");
			if(rateCareSetupList.contains("2"))settingTypeList.add("21");
		}
		String settingTypeStr = "";
		if(settingTypeList != null && !settingTypeList.isEmpty()){
			for (String string : settingTypeList) {
				settingTypeStr = settingTypeStr + "," + string;
			}
		}
		Date log7 = new Date();
		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~首页流程图查询开启关闭时间：" + (log7.getTime() - log6.getTime())+"毫秒");
//		model.addAttribute("memberCount", memberCount);
//		model.addAttribute("receiveInfos", receiveInfos);
		model.addAttribute("settingTypeStr", settingTypeStr);
		return "/crms/home/index";
	}

	/**
	 * ajax请求首页的会员营销和昨日店铺数据
	 * ZTK2017年8月30日下午3:33:11
	 */
	@RequestMapping("/member/indexData")
	@ResponseBody
	public String getData(HttpServletRequest request){
		logger.info("首页效果分析开始查询()()()()()()()()()()()()(");
		long l1 = System.currentTimeMillis();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Date monthStart = DateUtils.getTimesMonthmorning();//本月起始时间
		Date nowDate = new Date();//当前时间，用户登陆的时间
		Date todayStart = DateUtils.getStartTimeOfDay(new Date());//今天0点
		Date yesterStart = DateUtils.getStartTimeOfDay(DateUtils.nDaysAgo(1, nowDate));//昨天0点
		Date yesterEnd = DateUtils.getEndTimeOfDay(DateUtils.nDaysAgo(1, nowDate));//昨天24点
		//本月数据,短信消费金额、常规催付金额、会员营销金额
		double monthSmsMoney = 0,monthReminderMoney = 0,monthMemberMoney = 0;
		//今日数据,短信消费金额、常规催付金额、会员营销金额
		double todaySmsMoney = 0,todayReminderMoney = 0,todayMemberMoney = 0;
		//昨日数据,短信消费金额、常规催付金额、会员营销金额
		double yesterSmsMoney = 0,yesterReminderMoney = 0,yesterMemberMoney = 0;
		try {
			long l2 = System.currentTimeMillis();
			logger.info("首页效果分析开始查询短信消费金额()()()()()()()()()()()()(，时间"+ (l2 - l1) + "毫秒");
			//短信消费金额
			ReminderNum monthSmsNum = smsRecordService.findRecordTidAndSmsNum(null, userId, monthStart, nowDate,null);
			ReminderNum todaySmsNum = smsRecordService.findRecordTidAndSmsNum(null, userId, todayStart, nowDate,null);
			ReminderNum yesterSmsNum = smsRecordService.findRecordTidAndSmsNum(null, userId, yesterStart, yesterEnd,null);
			if(monthSmsNum != null){
				monthSmsMoney = NumberUtils.getResult(monthSmsNum.getSmsNum()) * 0.05;
			}
			if(todaySmsNum != null){
				todaySmsMoney = NumberUtils.getResult(todaySmsNum.getSmsNum()) * 0.05;
			}
			if(yesterSmsNum != null){
				yesterSmsMoney = NumberUtils.getResult(yesterSmsNum.getSmsNum()) * 0.05;
			}
			long l3 = System.currentTimeMillis();
			logger.info("短信消费金额查询耗时()()()()()()()()()()()()(，时间"+ (l3 - l2) + "毫秒");
			//催付挽回金额
			TradeCenterEffect todayEarningEffect = tradeCenterEffectService.aggregateEarningFee(userId, MsgType.MSG_CGCF, todayStart,  new Date());
			TradeCenterEffect yesterEarningEffect = tradeCenterEffectService.aggregateEarningFee(userId, MsgType.MSG_CGCF, yesterStart,  yesterEnd);
			TradeCenterEffect monthEarningEffect = tradeCenterEffectService.aggregateEarningFee(userId, MsgType.MSG_CGCF, monthStart,  new Date());
//			ReminderNum todayReminderNum = smsRecordService.findReminderNum(MsgType.MSG_CGCF, userId, todayStart, new Date(),false);
//			ReminderNum yesterReminderNum = smsRecordService.findReminderNum(MsgType.MSG_CGCF, userId, yesterStart, yesterEnd,false);
//			ReminderNum monthReminderNum = smsRecordService.findReminderNum(MsgType.MSG_CGCF, userId, monthStart, new Date(),true);
			if(todayEarningEffect != null && todayEarningEffect.getEarningFee() != null){
				todayReminderMoney = todayEarningEffect.getEarningFee();
			}
			if(yesterEarningEffect != null && yesterEarningEffect.getEarningFee() != null){
				yesterReminderMoney = yesterEarningEffect.getEarningFee();
			}
			if(monthEarningEffect != null && monthEarningEffect.getEarningFee() != null){
				monthReminderMoney = monthEarningEffect.getEarningFee();
			}
//			if(todayReminderNum != null){
//				todayReminderMoney = NumberUtils.getResult(todayReminderNum.getTargetMoney());
//			}
//			if(yesterReminderNum != null){
//				yesterReminderMoney = NumberUtils.getResult(yesterReminderNum.getTargetMoney());
//			}
//			if(monthReminderNum != null){
//				monthReminderMoney = NumberUtils.getResult(monthReminderNum.getTargetMoney());
//			}
			long l4 = System.currentTimeMillis();
			logger.info("催付挽回金额耗时()()()()()()()()()()()()(，时间："+ (l4 - l3) + "毫秒");
			//计算今日数据
//			todayMemberMoney = effectPictureService.findTodayMemberMoney(userId,DateUtils.getStartTimeOfDay(nowDate),DateUtils.getEndTimeOfDay(nowDate));
			//本月数据，需要加上今日数据
			Date threeDaysAgo = DateUtils.nDaysAgo(3, yesterStart);
			Date twoDaysAgo = DateUtils.nDaysAgo(3, todayStart);
			List<Long> todayMsgIds = msgSendRecordService.findMemberMsgIdByTime(userId, twoDaysAgo, nowDate);
			List<Long> monthMsgIds = msgSendRecordService.findMemberMsgIdByTime(userId, monthStart, nowDate);
			List<Long> yesDayMsgIds = msgSendRecordService.findMemberMsgIdByTime(userId, threeDaysAgo, nowDate);
			if(todayMsgIds != null && !todayMsgIds.isEmpty()){
				for (Long msgId : todayMsgIds) {
					todayMemberMoney += effectPictureService.findSuccessPayFeeByTime(userId, todayStart, nowDate,msgId);
				}
			}
			if(monthMsgIds != null && !monthMsgIds.isEmpty()){
				for (Long msgId : monthMsgIds) {
					monthMemberMoney += effectPictureService.findSuccessPayFeeByTime(userId, monthStart, nowDate,msgId);
				}
			}
			if(yesDayMsgIds != null && !yesDayMsgIds.isEmpty()){
				for (Long msgId : yesDayMsgIds) {
					yesterMemberMoney += effectPictureService.findSuccessPayFeeByTime(userId, yesterStart, yesterEnd,msgId);
				}
			}
			long l5 = System.currentTimeMillis();
			logger.info("营销数据耗时()()()()()()()()()()()()(，时间"+ (l5 - l4) + "毫秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
		long l6 = System.currentTimeMillis();
		ResultMap<String, Object> resultMap = rsMap(100, "操作成功").put("status", true);
		//昨日店铺数据
		Map<String, Object> dataMap = yesterData(userId,yesterStart,yesterEnd);
		//订单数、未付款订单、已发货订单、待发货订单、退款中订单
		long totalTrade = 0,waitPayTrade = 0,succConsignTrade = 0,waitConsignTrade = 0;
		int refundTrade = 0;
		double succPayment = 0.00;//付款金额
		if(dataMap != null){
			totalTrade = NumberUtils.getResult((long) dataMap.get("totalTrade"));
			waitPayTrade = NumberUtils.getResult((long) dataMap.get("waitPayTrade"));
			succConsignTrade = NumberUtils.getResult((long) dataMap.get("succConsignTrade"));
			waitConsignTrade = NumberUtils.getResult((long) dataMap.get("waitConsignTrade"));
			refundTrade = NumberUtils.getResult((int) dataMap.get("refundTrade"));
			succPayment = NumberUtils.getResult((double) dataMap.get("succPayment"));
		}
		resultMap.put("totalTrade", totalTrade);
		resultMap.put("waitPayTrade", waitPayTrade);
		resultMap.put("succConsignTrade", succConsignTrade);
		resultMap.put("waitConsignTrade", waitConsignTrade);
		resultMap.put("refundTrade", refundTrade);
		resultMap.put("succPayment", succPayment);
		
		resultMap.put("monthMemberMoney", monthMemberMoney + todayMemberMoney);
		resultMap.put("todayMemberMoney", todayMemberMoney);
		resultMap.put("yesterMemberMoney", yesterMemberMoney);
		resultMap.put("monthSmsMoney", monthSmsMoney);
		resultMap.put("todaySmsMoney", todaySmsMoney);
		resultMap.put("yesterSmsMoney", yesterSmsMoney);
		resultMap.put("todayReminderMoney", todayReminderMoney);
		resultMap.put("yesterReminderMoney", yesterReminderMoney);
		resultMap.put("monthReminderMoney", monthReminderMoney);
		long l7 = System.currentTimeMillis();
		logger.info("~~~~~~~~~~~~~~~~~~首页效果分析查询成功，返回数据："+ (l7 - l6) + "毫秒");
		return resultMap.toJson();
	}
	
	
	/**
	 * 计算昨日店铺数据
	 * ZTK2017年7月25日上午10:33:47
	 */
	public Map<String,Object> yesterData(String userId,Date yesterStart ,Date yesterEnd){
		//订单数、未付款订单、已发货订单、待发货订单、退款中订单
		long totalTrade = 0,waitPayTrade = 0,succConsignTrade = 0,waitConsignTrade = 0;
		int refundTrade = 0;
		double succPayment = 0.00;//付款金额
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<String> statusList = new ArrayList<String>();
		totalTrade = tradeInfoService.count(new Query(Criteria.where("created").gte(yesterStart.getTime()).lte(yesterEnd.getTime())), userId);
		statusList.add("WAIT_BUYER_PAY");
		statusList.add("PAY_PENDING");
		statusList.add("TRADE_NO_CREATE_PAY");
		waitPayTrade = tradeInfoService.count(new Query(Criteria.where("status").in(statusList)), userId);
		Criteria payCriteria = new Criteria("payTime").gte(yesterStart.getTime()).lte(yesterEnd.getTime());
		Query payQuery = new Query(payCriteria);
		succPayment = tradeInfoService.sumPayment(payQuery, userId);
		Criteria consignCriteria = Criteria.where("sellerNick").is(userId);
		consignCriteria.and("consignTime").gte(yesterStart.getTime()).lte(yesterEnd.getTime());
		//昨日发货订单
		succConsignTrade = tradeInfoService.count(new Query(consignCriteria), userId);
		waitConsignTrade = tradeInfoService.count(new Query(Criteria.where("status").is("WAIT_SELLER_SEND_GOODS")), userId);
		//退款中订单
		refundTrade = transactionOrderService.getRefundNumber(userId, yesterStart, yesterEnd);
		resultMap.put("totalTrade", totalTrade);
		resultMap.put("waitPayTrade", waitPayTrade);
		resultMap.put("succConsignTrade", succConsignTrade);
		resultMap.put("waitConsignTrade", waitConsignTrade);
		resultMap.put("refundTrade", refundTrade);
		resultMap.put("succPayment", succPayment);
		return resultMap;
	}
	
	/**
	 * 会员营销转化金额
	 * ZTK2017年7月25日下午3:13:32
	 */
	public double memberMarktingData(String userId,Date beginTime,Date endTime,boolean isMonth){
		//会员营销默认前三天发送催付短信的效果,计算三天前的时间
		Date threeDaysAgo = DateUtils.nDaysAgo(3, beginTime);
		List<Long> msgIds = new ArrayList<Long>();
		if(isMonth){
			msgIds = msgSendRecordService.findMemberMsgIdByTime(userId, beginTime, endTime);
		}else {
			msgIds = msgSendRecordService.findMemberMsgIdByTime(userId, threeDaysAgo, endTime);
		}
		Set<String> phones = new HashSet<String>();
		if(msgIds != null && !msgIds.isEmpty()){
			logger.info("~~~~isMonth   msgIds.size:" + msgIds.size());
			for (Long msgId : msgIds) {
				if(msgId != null){
					List<String> phoneList = vipMemberService.findMemberPhoneList(userId, msgId, beginTime, endTime);
					phones.addAll(phoneList);
				}
			}
		}
		if(phones != null && !phones.isEmpty()){
			logger.info("~~~~phones.size:" + phones.size());
			if(phones.size() > 100000){
				return tradeMatchRecordEffect(userId, beginTime, endTime, phones);
			}else {
				return recordMatchTradeEffect(userId, beginTime, endTime, phones);
			}
		}
		return 0.0;
//		List<String> statusList = new ArrayList<String>();
//		statusList.add("TRADE_FINISHED");
//		statusList.add("WAIT_SELLER_SEND_GOODS");
//		statusList.add("WAIT_BUYER_CONFIRM_GOODS");
//		statusList.add("TRADE_BUYER_SIGNED");
//		statusList.add("TRADE_CLOSED");
//		if(phones != null && !phones.isEmpty()){
//			List<String> phoneArray = new ArrayList<String>(phones);
//			//统计营销转化金额
//		    Double payment = 0.0;
//			int end = 0, start = 0, totalCount = phones.size();
//			if(totalCount / ConstantUtils.PROCESS_PAGE_SIZE_OVER == 0){
//				end = 1;
//			}else if(totalCount % ConstantUtils.PROCESS_PAGE_SIZE_OVER == 0){
//				end = totalCount / ConstantUtils.PROCESS_PAGE_SIZE_OVER;
//			}else{
//				end = (totalCount + ConstantUtils.PROCESS_PAGE_SIZE_OVER) / ConstantUtils.PROCESS_PAGE_SIZE_OVER;
//			}
//			while(start<end){
//				EffectNum succEffect = null;
//				try {
//					if(start == (end-1)){
//						succEffect = tradeInfoService.findTotalOrderNum(userId, beginTime,phoneArray.subList(start * ConstantUtils.PROCESS_PAGE_SIZE_OVER, totalCount), endTime, null, statusList, null);
//					}else {
//						succEffect = tradeInfoService.findTotalOrderNum(userId, beginTime,phoneArray.subList(start * ConstantUtils.PROCESS_PAGE_SIZE_OVER, (start + 1) * ConstantUtils.PROCESS_PAGE_SIZE_OVER), endTime, null, statusList, null);
//					}
//				} catch (SecretException e) {
//					e.printStackTrace();
//				}
//				if(succEffect != null){
//					logger.info("~~~~第" +(start + 1)+ "次succEffect.getPayment():" + succEffect.getPayment());
//					payment += NumberUtils.getResult(succEffect.getPayment());
//				}
//				start++;
//			}
//			return payment;
//		}else{
//			return 0.0;
//		}
	}
	
	/**
	 * 通过订单匹配发送记录（效果分析发送手机号大于10w）
	 * ZTK2017年8月30日上午10:57:49
	 */
	public double tradeMatchRecordEffect(String userId,Date beginTime,Date endTime,Set<String> phones){
		double payment = 0.0;
		try {
			List<String> statusList = new ArrayList<String>();
			statusList.add(TradeStatusUtils.TRADE_FINISHED);
			statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
			statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
			statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
			statusList.add(TradeStatusUtils.TRADE_CLOSED);
			Criteria criteria = Criteria.where("sellerNick").is(userId);
			if(beginTime != null && endTime != null){
				criteria.and("created").gte(beginTime.getTime()).lte(endTime.getTime());
			}
			if(statusList != null && !statusList.isEmpty()){
				criteria.and("status").in(statusList);
			}
			List<TradeDTO> tradeDTOs = tradeInfoService.findList(new Query(criteria), userId);
			if(tradeDTOs != null){
				for (TradeDTO tradeDTO : tradeDTOs) {
					if(tradeDTO != null && tradeDTO.getReceiverMobile() != null && phones.contains(tradeDTO.getReceiverMobile())){
						payment += NumberUtils.getResult(tradeDTO.getReceivedPayment());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payment;
	}
	
	/**
	 * 通过发送记录匹配订单（效果分析发送手机号小于10w）
	 * ZTK2017年8月30日上午10:57:49
	 */
	public double recordMatchTradeEffect(String userId,Date beginTime,Date endTime,Set<String> phones){
		List<String> statusList = new ArrayList<String>();
		statusList.add(TradeStatusUtils.TRADE_FINISHED);
		statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
		statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
		statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
		statusList.add(TradeStatusUtils.TRADE_CLOSED);
		if(phones != null && !phones.isEmpty()){
			List<String> phoneArray = new ArrayList<String>(phones);
			//统计营销转化金额
		    Double payment = 0.0;
			try {
				int end = 0, start = 0, totalCount = phones.size();
				if(totalCount / ConstantUtils.PROCESS_PAGE_SIZE_OVER == 0){
					end = 1;
				}else if(totalCount % ConstantUtils.PROCESS_PAGE_SIZE_OVER == 0){
					end = totalCount / ConstantUtils.PROCESS_PAGE_SIZE_OVER;
				}else{
					end = (totalCount + ConstantUtils.PROCESS_PAGE_SIZE_OVER) / ConstantUtils.PROCESS_PAGE_SIZE_OVER;
				}
				while(start<end){
					EffectNum succEffect = null;
					try {
						if(start == (end-1)){
							succEffect = tradeInfoService.findTotalOrderNum(userId, beginTime,phoneArray.subList(start * ConstantUtils.PROCESS_PAGE_SIZE_OVER, totalCount), endTime, null, statusList, null);
						}else {
							succEffect = tradeInfoService.findTotalOrderNum(userId, beginTime,phoneArray.subList(start * ConstantUtils.PROCESS_PAGE_SIZE_OVER, (start + 1) * ConstantUtils.PROCESS_PAGE_SIZE_OVER), endTime, null, statusList, null);
						}
					} catch (SecretException e) {
						e.printStackTrace();
					}
					if(succEffect != null){
						logger.info("~~~~第" +(start + 1)+ "次succEffect.getPayment():" + succEffect.getPayment());
						payment += NumberUtils.getResult(succEffect.getPayment());
					}
					start++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return payment;
		}else{
			return 0.0;
		}
	}
	
	

	@RequestMapping(value="/home/getIndexInfo", produces="text/json;charset=UTF-8")
	public @ResponseBody String getIndexInfo(String userId, Model model,HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			paramMap.put("startRows", 0);
			paramMap.put("currentRows", 100);
			paramMap.put("notContainN", "N");
			List<SmsReceiveInfo> receiveInfos = smsReceiveInfoService.findReceiveInfoLimitList(paramMap);
			long memberCount = vipMemberService.findMemberCount(userId);
			resultMap.put("receiveInfos", receiveInfos);
			resultMap.put("memberCount", memberCount);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("receiveInfos", null);
			resultMap.put("memberCount", 0);
			logger.info(userId+"首页查询上行或会员数出错" +e.getStackTrace());
			return rsMap(102, "操作成功").put("status", true).put("data",resultMap).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).put("data",resultMap).toJson();
	}

	
}
