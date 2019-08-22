package s2jh.biz.shop.crm.order.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.service.OrderReminderService;
import s2jh.biz.shop.crm.order.service.SmsSettingService;
import s2jh.biz.shop.crm.order.util.ResourceGetValueUtil;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.getIpAddress;

@Controller
@RequestMapping(value="/OrderReminder")
public class OrderReminderController {
	
	@Autowired
	public OrderReminderService orderReminderService;
	
	@Autowired
	private UserOperationLogDao userlog;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
//	@Autowired
//	private MyBatisDao myBatisDao;
	
//	@Autowired
//	private OrderSetupService orderSetupService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SmsSettingService smsSettingService;
	
	/**
	 * 跳转到订单手动提醒页面
	 */
	@RequestMapping(value="/jumOrderReminder")
	public String jumpOrderReminder(Model model,HttpServletRequest request){
		String userId=(String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			// TODO:sd
			//String userId="10010";
			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String startTime=null;
			 String endTime=null;
			 String timing=null;
			//根据卖家编号查询手动订单提醒条件
			OrderReminder or = orderReminderService.findOrderReminder(userId);
			if(or!=null&&or.getStartTime()!=null){
				startTime=format.format(or.getStartTime());
			}
			if(or!=null&&or.getEndTime()!=null){
				endTime=format.format(or.getEndTime());
			}
			if(or!=null&&or.getTiming()!=null){
				timing=format.format(or.getTiming());
			}
			model.addAttribute("endTime",endTime);
			model.addAttribute("startTime",startTime);
			model.addAttribute("timing",timing);
			model.addAttribute("orderReminder",or);
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		return "/crms/orderReminder/OrderReminder";
	}
	
	/**
	 * 根据id更新手动订单表的开启状态
	 * ZTK2017年1月10日下午4:16:52
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	public Map<String,Object> updateStatusById(HttpServletRequest request,HttpServletResponse response,String id,String status,String isTiming,String timing) throws IOException, ParseException{
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			if(("0".equals(isTiming))&& "1".equals(status)){  //用户准备开启且是定时的手动订单提醒
				Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timing);
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE, 30);
				Date nowDate = cal.getTime();
				if(startTime.getTime()>=nowDate.getTime()){ //开始时间大约等于当前时间+30分钟
				}else{ //开始时间小于当前时间+30分钟
					resultMap.put("msg", ResourceGetValueUtil.getValue("order.reminder.time.failure.msg"));
					return resultMap;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("msg", ResourceGetValueUtil.getValue("order.reminder.datetime.failure.msg"));//格式换失败，用户设置的日期时间有误
			return resultMap;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", (id==null||!id.equals("")?null:Long.parseLong(id)));
		map.put("userId", userId);
		map.put("status", status);
		map.put("lastModifiedDate", new Date());
		map.put("lastModifiedBy", userId);
		map.put("isTiming", isTiming);
		map.put("timing", timing);
		map.put("userName", userId);
		try {
			resultMap = orderReminderService.updateStatusById(map);
			if(resultMap.get("flag") != null){
				if((boolean)resultMap.get("flag")){
					resultMap.put("msg", "发送成功" + resultMap.get("successNum") + "条");
				}else{
				}
			}else{
				resultMap.put("flag", false);
				resultMap.put("msg", ResourceGetValueUtil.getValue("order.reminder.save.failure.msg"));
			}
			/*if(flag){
				resultMap.put("flag", true);
			}else{
				resultMap.put("flag", false);
				resultMap.put("msg", ResourceGetValueUtil.getValue("order.reminder.save.failure.msg"));
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("msg", "系统错误，请联系管理员进行处理");
		}
		return resultMap;
	}
	
	/**
	 * 修改手动订单提醒条件基本设置
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/updateOrderReminder",method=RequestMethod.POST)
	public String updateOrderReminder(ModelMap model,String orderTime,String startTime,String endTime,String bookingStatus,
			String orderStatus,String minOrderPrice,String maxOrderPrice,String minTradeNum,String maxTradeNum,
			String orderType,String evaluateStatus,String province,String sellerSign,String orderSource,
			String alreadySendMessages,String filterType,String selectCommodityType,String commodityIds,
			PrintWriter writer,HttpServletRequest request) throws ParseException{
		String userId=(String) request.getSession().getAttribute("taobao_user_nick");
		JSONObject jsonObj = new JSONObject();
		if(userId!=null){
			// TODO:sd
			//String userId="10001";//获取卖家用户编号
			String ip=getIpAddress.getIpAddress(request);//获取操作人的ip地址
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Map<String,Object> map = new HashMap<String,Object>();
			if(orderTime.equals("下单时间")){
				map.put("orderDateType", 1);
			}else if(orderTime.equals("付款时间")){
				map.put("orderDateType", 2);
			}else if(orderTime.equals("发货时间")){
				map.put("orderDateType", 3);
			}else if(orderTime.equals("变更时间")){
				map.put("orderDateType", 4);
			}else if(orderTime.equals("结束时间")){
				map.put("orderDateType", 5);
			}else{
				map.put("orderDateType", 0);
			}
			
			if(startTime!=null&&startTime!=""){				
				map.put("startTime", sdf.parse(startTime));
			}
			if(endTime!=null&&endTime!=""){			
				map.put("endTime", sdf.parse(endTime));
			}
			map.put("bookingStatus", bookingStatus);
			map.put("orderStatus", orderStatus);
			if(minOrderPrice!=null&&minOrderPrice!=""){
				map.put("minOrderPrice", Double.parseDouble(minOrderPrice));
			}
			if(maxOrderPrice!=null&&maxOrderPrice!=""){
				map.put("maxOrderPrice", Double.parseDouble(maxOrderPrice));
			}
			if(minTradeNum!=null&&minTradeNum!=""){
				map.put("minTradeNum", Integer.parseInt(minTradeNum));
			}
			if(maxTradeNum!=null&&maxTradeNum!=""){
				map.put("maxTradeNum", Integer.parseInt(maxTradeNum));
			}		
			map.put("orderType", orderType);
			map.put("evaluateStatus", evaluateStatus);
			map.put("province", province);
			map.put("sellerSign", sellerSign);
			map.put("orderSource", orderSource);
			map.put("alreadySendMessages", alreadySendMessages);
			map.put("filterType", filterType);
			map.put("selectCommodityType", selectCommodityType);
			map.put("commodityIds", commodityIds);
			map.put("userId", userId);		
					
			//根据卖家编号查询是否有手动订单提醒条件
			OrderReminder or = orderReminderService.findOrderReminder(userId);
			int data =0;//获取操作返回值
			if(or!=null){
				map.put("id", or.getId());
				map.put("lastModifiedDate", new Date());
				data = orderReminderService.updateOrderReminder(map);
			}else{
				map.put("createdDate", new Date());
				data = orderReminderService.addOrderReminder(map);
			}
			
			UserOperationLog log= new UserOperationLog();
			if(data>0){
				jsonObj.put("success", true);
				jsonObj.put("info", "修改数据成功!");
				log.setState("成功");
			}else{
				log.setState("失败");
			}
			if(or!=null){
				log.setRemark("修改手动订单提醒");
				log.setType("修改");
			}else{
				log.setRemark("添加手动订单提醒");
				log.setType("添加");
			}		
			log.setUserId(userId);
			log.setFunctions("手动订单提醒");
			log.setDate(new Date());
			log.setIpAdd(ip);
			userlog.save(log);
		}else{
			jsonObj.put("success", false);
			jsonObj.put("info", ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		writer.print(jsonObj);
		return null;
	}
	
	/**
	 * 修改手动订单提醒条件短信设置
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value="/updateOrderReminder1",method=RequestMethod.POST)
	public String updateOrderReminder(PrintWriter writer,HttpServletRequest request,String id,String content,String sSgin,String isTiming,String time,String status){
		long orid =0;
		Date date =null;
		String ip=getIpAddress.getIpAddress(request);//获取操作人的ip地址
		String userId=(String) request.getSession().getAttribute("taobao_user_nick");
		// TODO:sd
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
		//将id转为long类型
		if(id!=null&&!id.equals("")){
			orid=Long.parseLong(id);
		}
		
		//将时间转为date格式
		if(time!=null&&!time.equals("")){
			try {
				date = formatter.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("content", content);
		map.put("id", id);
		map.put("smsSign", sSgin);
		map.put("isTiming", isTiming);
		map.put("timing", time);
		map.put("status", status);
		map.put("lastModifiedDate", new Date());
		int data = orderReminderService.updateOr(map);
		
		//修改用户的店铺签名
		Map <String,Object> signMap = new HashMap<String,Object>();
		signMap.put("userId", userId);
		signMap.put("shopName", sSgin);
		int i = userInfoService.updateShopName(signMap);
		if(i>0){
			HttpSession session = request.getSession();
			session.setAttribute("ShopName", sSgin);
		}
		smsSettingService.findSmsSettingList(userId, sSgin);
		
		JSONObject jsonObj = new JSONObject();
		//添加操作日志
		UserOperationLog log= new UserOperationLog();
		if(data>0){
			jsonObj.put("success", "true");
			jsonObj.put("info", "修改数据成功!");
			log.setState("成功");
		}else{
			jsonObj.put("success", "false");
			jsonObj.put("info", "修改数据失败!");
			log.setState("失败");
		}	
		
		log.setRemark("修改手动订单提醒");
		log.setType("修改");		
		log.setUserId(userId);
		log.setFunctions("手动订单提醒");
		log.setDate(new Date());
		log.setIpAdd(ip);
		userlog.save(log);
		writer.print(jsonObj);
		return null;
	}
	
	/**
	 * 跳转手动订单提醒发送列表页面
	 * @return
	 */
//	@RequestMapping("/sendList")
//	public String sendListQuery(HttpServletRequest request,String bTime,String eTime,String recNum,String status,
//			String buyerNick,Integer pageNo,ModelMap model){
//		String contextPath = request.getContextPath();
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		//添加查询条件到map
//		Map<String,Object> map = new HashMap<String, Object>();
//		if("0".equals(status)){
//			map.put("status", null);
//		}else{
//			map.put("status", status);
//		}
//		map.put("userId", userId);
//		map.put("bTime",DateUtils.convertStringToDate(bTime));//DateUtils.convertDate(bTime)
//		map.put("eTime",DateUtils.convertStringToDate(eTime));// DateUtils.convertDate(eTime)
//		map.put("recNum", recNum);
//		map.put("buyerNick", buyerNick);
//		//查询到的pagination
//		Pagination pagination = smsSendRecordService.findReminderSendList(pageNo, map);
//		//分页查询再次跳转路径
//		String url = contextPath + "/OrderReminder/sendList";
//		//分页查询再次跳转参数拼接
//		StringBuilder params = new StringBuilder();
//		if(bTime != null && !"".equals(bTime)){
//			params.append("&bTime=").append(bTime);
//		}
//		if(eTime != null && !"".equals(eTime)){
//			params.append("&eTime=").append(eTime);
//		}
//		if(recNum != null && !"".equals(recNum)){
//			params.append("&recNum=").append(recNum);
//		}
//		if(status != null && !"".equals(status)){
//			params.append("&status=").append(status);
//		}
//		if(buyerNick != null && !"".equals(buyerNick)){
//			params.append("&buyerNick=").append(buyerNick);
//		}
//		pagination.pageView(url, params.toString());
//		//返回查询数据到页面
//		model.addAttribute("pagination", pagination);
//		//设置查询条件页面回显
//		model.addAttribute("bTime", bTime);
//		model.addAttribute("eTime", eTime);
//		model.addAttribute("recNum", recNum);
//		model.addAttribute("status", status);
//		model.addAttribute("buyerNick", buyerNick);
//		return "/crms/orderReminder/shoudong_fasongliebiao";
//	}
	
	@RequestMapping("/sendList")
	public String sendListQuery(HttpServletRequest request,String bTime,String eTime,String recNum,String status,
			String buyerNick,Integer pageNo,ModelMap model){
		String contextPath = request.getContextPath();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//添加查询条件到map
		Query query = new Query();
//		query.addCriteria(Criteria.where("_id").is(id));
		if("0".equals(status)){
//			query.addCriteria(Criteria.where("status").is(null));
		}else{
			query.addCriteria(Criteria.where("status").is(status));
		}
		if(userId != null && !"".equals(userId)){
			query.addCriteria(Criteria.where("userId").is(userId));
		}
		try {
			if(bTime != null && !"".equals(bTime) && bTime != null && !"".equals(eTime)){
				query.addCriteria(Criteria.where("sendLongTime").gte(DateUtils.stringToLong(bTime,DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(eTime, DateUtils.DEFAULT_TIME_FORMAT)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(recNum != null && !"".equals(recNum)){
			query.addCriteria(Criteria.where("recNum").is(recNum));
		}
		if(buyerNick != null && !"".equals(buyerNick)){
			query.addCriteria(Criteria.where("buyerNick").is(buyerNick));
		}
		//查询到的pagination
		Pageination<SmsRecordDTO> pagination = smsRecordService.findReminderSendList(pageNo, query,userId);
		//分页查询再次跳转路径
		String url = contextPath + "/OrderReminder/sendList";
		//分页查询再次跳转参数拼接
		StringBuilder params = new StringBuilder();
		if(bTime != null && !"".equals(bTime)){
			params.append("&bTime=").append(bTime);
		}
		if(eTime != null && !"".equals(eTime)){
			params.append("&eTime=").append(eTime);
		}
		if(recNum != null && !"".equals(recNum)){
			params.append("&recNum=").append(recNum);
		}
		if(status != null && !"".equals(status)){
			params.append("&status=").append(status);
		}
		if(buyerNick != null && !"".equals(buyerNick)){
			params.append("&buyerNick=").append(buyerNick);
		}
		pagination.pageView(url, params.toString());
		//返回查询数据到页面
		model.addAttribute("pagination", pagination);
		//设置查询条件页面回显
		model.addAttribute("bTime", bTime);
		model.addAttribute("eTime", eTime);
		model.addAttribute("recNum", recNum);
		model.addAttribute("status", status);
		model.addAttribute("buyerNick", buyerNick);
		return "/crms/orderReminder/shoudong_fasongliebiao";
	}
	
	/**
	 * 根据id更新活动名称
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateActivityName")
	public void updateActivityNameById(HttpServletRequest request,HttpServletResponse response,
			String id,String activityName){
			String userId = (String) request.getSession().getAttribute("taobao_user_id");
			JSONObject json = new JSONObject();
			UserOperationLog op = new UserOperationLog();
		try {
			//操作日志数据补全
			op.setState("成功");
			op.setFunctions("手动订单提醒活动名称");
			op.setType("更新");
			op.setDate(new Date());
			op.setRemark("手动订单提醒");
			op.setIpAdd(getIpAddress.getIpAddress(request));
			op.setUserId(userId);
			op.setFunctionGens("26");
			long idLong = Long.parseLong(id);
//			smsSendRecordService.updateActivityNameById(idLong, activityName,op);
			smsRecordService.updateActivityNameById(idLong, activityName,op,userId);
			json.put("message", true);
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			json.put("message", false);
			e.printStackTrace();
		}
	}

}
