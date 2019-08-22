package s2jh.biz.shop.crm.order.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taobao.api.SecretException;

import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsHistoryTemplate;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.message.service.SmsHistoryTemplateService;
import s2jh.biz.shop.crm.message.service.SmsTemplateService;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.order.entity.TradeRates;
import s2jh.biz.shop.crm.order.service.OrderRateCareSetupService;
import s2jh.biz.shop.crm.order.service.OrderService;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.order.service.TradeRatesService;
import s2jh.biz.shop.crm.order.util.ResourceGetValueUtil;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;
@RequestMapping("/crms")
@Controller
public class OrderRateCareController{

	@Autowired
	private TradeRatesService tradeRatesService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SmsTemplateService smsTemplateService;
	
	@Autowired
	private SmsHistoryTemplateService smsHistoryTemplateService;
	
	@Autowired
	private OrderRateCareSetupService orderRateCareSetupService;
	
	@Autowired
	private OrderSetupService orderSetupService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	private Logger logger = LoggerFactory.getLogger(OrderRateCareController.class);
	/**
	 * 跳转评价关怀自动评价页面
	 * ZTK2017年1月4日下午4:52:14
	 */
	@RequestMapping("/reviewCare")
	public String reviewCare(String msg,HttpServletRequest request,ModelMap model){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		OrderRateCareSetup orderRateCareSetup = orderRateCareSetupService.findOrderRateCareSetup(userId,"0");
		model.addAttribute("orderRateCareSetup", orderRateCareSetup);
		if(msg!=null && msg !=""&& msg != "null"){
			model.addAttribute("msg", msg);
		}
		return "/crms/orderCenterZ/pingjiaguanhuai";
	}
	
	/**
	 * 自动评价页面选择评价模板
	 * ZTK2017年1月4日下午4:52:14
	 * @throws IOException 
	 */
	@RequestMapping("/autoRateTemplate")
	@ResponseBody
	public Map<String,Object> autoRateTemplate(HttpServletRequest request,HttpServletResponse response,String type) throws IOException{
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if("历史使用".equals(type)){
			map.put("type", "好中差");
			map.put("userId", userId);
			List<SmsHistoryTemplate> historyTemp = smsHistoryTemplateService.findListAll(map);
			resultMap.put("historyTemp", historyTemp);
		}else{
			List<String> typeList = new ArrayList<String>();
			typeList.add("好评");
			typeList.add("中评");
			typeList.add("差评");
			map.put("typeList", typeList);
			List<SmsTemplate> smsTemplate = smsTemplateService.findAllSmsTemplates(map);
			resultMap.put("smsTemplate", smsTemplate);
		}
		return resultMap;
	}
	
	/**
	 * 保存用户自动评价页面设置
	 * ZTK2017年1月4日下午4:52:14
	 * 王勇 2017.2.9改
	 */
	@RequestMapping("/saveAutoRate")
	public String saveAutoRate(HttpServletRequest request,OrderRateCareSetup orderRateCareSetup,ModelMap model,
			@RequestParam(defaultValue="1",value="rateChoose")String rateChoose,RedirectAttributes attr){
		orderRateCareSetup.setRateChoose(rateChoose);
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		boolean flag =false;
		if(orderRateCareSetup.getId() != null && !"".equals(orderRateCareSetup.getId())){
			orderRateCareSetup.setUserId(userId);
			orderRateCareSetup.setLastModifiedDate(new Date());
			orderRateCareSetup.setAppraiseType("0");
			orderRateCareSetup.setLastModifiedBy(userId);
			flag = orderRateCareSetupService.updateOrderRateCareSetup(orderRateCareSetup);
		}else{
			orderRateCareSetup.setUserId(userId);
			orderRateCareSetup.setCreatedDate(new Date());
			orderRateCareSetup.setLastModifiedDate(new Date());
			orderRateCareSetup.setLastModifiedBy(userId);
			orderRateCareSetup.setAppraiseType("0");	
			flag = orderRateCareSetupService.saveOrderRateCareSetup(orderRateCareSetup);
		}
		this.userInfoService.addUserPermitByMySql(userId, null);
		String msg = flag?ResourceGetValueUtil.getValue("auto.rate.save.success.msg"):ResourceGetValueUtil.
				getValue("auto.rate.save.failure.msg");
		attr.addAttribute("msg", msg);
		return "redirect:/crms/reviewCare";
	}
	
	/**
	 * 用户点击评价类型时，页面展示相应的内容
	 * ZTK2017年5月15日下午2:30:56
	 */
	@ResponseBody
	@RequestMapping("/queryContentByType")
	public Map<String,Object> queryContentByType(String result,HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String userId = (String)request.getSession().getAttribute("taobao_user_nick");
		String content = orderRateCareSetupService.queryContentByType(userId, result);
		resultMap.put("content", content);
		return resultMap;
	}
	
	/**
	 * 自动评价页面设置(更新)评价不同类型的内容
	 * ZTK2017年5月19日下午12:00:17
	 */
	@RequestMapping("/setResultContent")
	@ResponseBody
	public Map<String,Object> updateResultContent(HttpServletRequest request,String result,String content){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		boolean flag = true;
		
		String resultContent = orderRateCareSetupService.queryContentByType(userId, result);
		if(resultContent != null && !"".equals(resultContent)){
			flag = orderRateCareSetupService.updateRateCareSetupByResult(result,content,"0",userId);
		}else{
			flag = orderRateCareSetupService.insertRateCareSetupByResult(result,content,"0",userId);
		}
		if(flag){
			resultMap.put("msg", "设置成功");
			return resultMap;
		}else {
			resultMap.put("msg", "设置失败");
			return resultMap;
		}
	}
	
	//-----------------------------------------------批量评价-----------------------------------------------------------------
	
	/**
	 * 跳转评价关怀批量评价页面
	 */
//	@RequestMapping("/batchReview")
//	public String batchReview(Integer pageNo,@RequestParam(value="buyerRate",defaultValue="all")String buyerRate,
//			String screen,String buyerNick,String bTime,String eTime,HttpServletRequest request,ModelMap model){
//		if(pageNo == null){
//			pageNo = 1;
//		}
//		//定义参数获取计算后的时间
//		Map<String ,Object> map = new HashMap<String, Object>();
//		String contextPath = request.getContextPath();
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		//获取当前时间
//		if("all".equals(buyerRate)){
//			map.put("buyerRate", null);
//		}else if ("0".equals(buyerRate)) {
//			map.put("buyerRate", false);
//		}else if ("1".equals(buyerRate)) {
//			map.put("buyerRate", true);
//		}
//		if("0".equals(screen)){
//			//全部
//			map.put("tarTime", null);
//		}else if("1".equals(screen)){
//			//7天后过期
//			map.put("tarTime", 192);
//		}else if("2".equals(screen)){
//			//1天后过期
//			map.put("tarTime", 336);
//		}else if("3".equals(screen)){
//			//1小时后 过期
//			map.put("tarTime", 359);
//		}else{
//			map.put("tarTime", null);
//		}
//		
//		map.put("buyerNick", buyerNick);
//		map.put("bTime", DateUtils.convertDate(bTime));
//		map.put("eTime", DateUtils.convertDate(eTime));
//		map.put("sellerRate", false);
//		map.put("sellerNick", userId);
//		Pagination pagination = null;
//		try {
//			pagination = orderService.findOrdersByCreatedAsc(pageNo, map);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		//拼接分页跳转路径
//		String url = contextPath + "/crms/batchReview";
//		//拼接参数
//		StringBuilder params = new StringBuilder();
//		if(buyerRate != null && !"".equals(buyerRate)){
//			params.append("&buyerRate=").append(buyerRate);
//		}
//		if(buyerNick != null && !"".equals(buyerNick)){
//			params.append("&buyerNick=").append(buyerNick);
//		}
//		if(screen != null && !"".equals(screen)){
//			params.append("&screen=").append(screen);
//		}
//		if(bTime != null && !"".equals(bTime)){
//			params.append("&bTime=").append(bTime);
//		}
//		if(eTime != null && !"".equals(eTime)){
//			params.append("&eTime=").append(eTime);
//		}
//		pagination.pageView(url, params.toString());
//		//设置页面回显
//		model.put("buyerRate", buyerRate);
//		model.put("bTime", bTime);
//		model.put("eTime", eTime);
//		model.put("buyerNick", buyerNick);
//		model.put("screen", screen);
//		model.put("pagination", pagination);
//		return "/crms/orderCenterZ/pingjiaguanhuai_2";
//	}
	
	/**
	 * 批量评价页面选择评价模板
	 * @throws IOException 
	 */
	@RequestMapping("/rateTemplate")
	@ResponseBody
	public Map<String, Object> rateTemplate(HttpServletRequest request,HttpServletResponse response,String type) throws IOException{
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "crzzyboy";
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if("历史使用".equals(type)){
			map.put("type", "批量评价");
			map.put("userId", userId);
			List<SmsHistoryTemplate> historyTemp = smsHistoryTemplateService.findListAll(map);
			resultMap.put("historyTemp", historyTemp);
		}else{
			List<String> typeList = new ArrayList<String>();
			typeList.add("好评");
			typeList.add("中评");
			typeList.add("差评");
			map.put("typeList", typeList);
			List<SmsTemplate> smsTemplate = smsTemplateService.findAllSmsTemplates(map);
			resultMap.put("smsTemplate", smsTemplate);
		}
		return resultMap;
	}
	
	/**
	 * 点击使用评价模板后添加模板到历史使用
	 * @throws IOException 
	 */
	@RequestMapping("/addHistoryTemp")
	@ResponseBody
	public Map<String,Object> addHistoryTemp(HttpServletResponse response,String type,String id,HttpServletRequest request) throws IOException{
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//根据userId和templateId查询历史使用是否存在
		SmsHistoryTemplate rateHisTemp = smsHistoryTemplateService.findTemplateBytemplateId(userId, id,"");
		SmsHistoryTemplate smsHistoryTemplate = new SmsHistoryTemplate();
		UserOperationLog op = new UserOperationLog(userId, userId, "评价模板历史使用", null, new Date(), null, 
				getIpAddress.getIpAddress(request), "评价模板历史使用", "17");
		//如果历史使用模板中存在，则更新最后操作时间；若果不存在，则添加历史使用
		if(rateHisTemp == null || "".equals(rateHisTemp)){
			//不存在，添加历史使用
			smsHistoryTemplate.setTemplateId(id);
			smsHistoryTemplate.setType("批量评价");
			smsHistoryTemplate.setUserId(userId);
			smsHistoryTemplate.setCreatedDate(new Date());
			smsHistoryTemplate.setLastModifiedBy(userId);
			smsHistoryTemplate.setLastModifiedDate(new Date());
			op.setType("添加");
			try {
				smsHistoryTemplateService.addHistoryTempById(smsHistoryTemplate);
				op.setState("成功");
			} catch (Exception e) {
				e.printStackTrace();
				op.setState("失败");
			}
		}else{
			//存在，更新lastModifiedDate
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", rateHisTemp.getId());
			map.put("type", "批量评价");
			map.put("templateId", id);
			map.put("userId", userId);
			map.put("lastModifiedBy", userId);
			map.put("lastModifiedDate", new Date());
			//操作日志数据补全
			op.setType("更新");
			try {
				smsHistoryTemplateService.updateHistoryTempById(map);
				op.setState("成功");
			} catch (Exception e) {
				e.printStackTrace();
				op.setState("失败");
			}
		}
		orderService.saveUserOperationLog(op);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("message", true);
		return resultMap;
	}
	
//	/**
//	 * 点击页面确定按钮进行评价订单
//	 * ZTK2017年1月6日下午12:19:29
//	 */
//	@RequestMapping("/rateOrder")
//	public void rateOrder(HttpServletRequest request,HttpServletResponse response,
//			String oids,String content,String tids,String result){
//		
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		String sessionKey = (String) request.getSession().getAttribute("access_token");
//		
//		DefaultTaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,taobaoInfo.appKey,taobaoInfo.appSecret);
//		TraderateAddRequest addRequest = new TraderateAddRequest();
//		//日志信息(order表卖家评价状态)
//		UserOperationLog orderLog = new UserOperationLog(userId, userId, "订单表卖家状态", null, new Date(), null, 
//				getIpAddress.getIpAddress(request), "订单表卖家状态", "17");
//		//日志信息(添加评价列表)remark存储的是rateResponse.getSubMsg;
//		UserOperationLog tradeRateLog = new UserOperationLog(userId, userId, "评价列表", null, new Date(), null, 
//				getIpAddress.getIpAddress(request), "评价列表", "17");
//		//修改本地数据库内容(区别于淘宝接口)
//		Map<String, Object> map = new HashMap<String, Object>();
//		JSONObject json = new JSONObject();
//		TradeRates tradeRates = new TradeRates();
//		String[] arrayOid = null;
//		if(oids != null && !"".equals(oids)){
//			arrayOid = oids.split(",");
//		}else{
//			json.put("message", "评价失败，请选择订单或联系管理员");
//		}
//		String[] arrayTid = null;
//		if(tids != null && !"".equals(tids)){
//			arrayTid = tids.split(",");
//		}else{
//			json.put("message", "评价失败，请选择订单或联系管理员");
//		}
//		if(arrayOid!=null&&arrayOid.length > 0){
//			try {
//				for (int i = 0; i < arrayOid.length; i++) {
//					addRequest.setOid(Long.parseLong(arrayOid[i]));
//					addRequest.setTid(Long.parseLong(arrayTid[i]));
//					addRequest.setContent(content);
//					addRequest.setResult(result);
//					addRequest.setRole("seller");
//					TraderateAddResponse rateResponse = client.execute(addRequest,sessionKey);
//					if(rateResponse.getErrorCode() != null){
//						json.put("message", "评价失败");
//						tradeRateLog.setState("失败");
//						tradeRateLog.setRemark(rateResponse.getSubMsg());
//					}else{
//						map.put("oid", arrayOid[i]+"");
//						map.put("tid", arrayTid[i]+"");
//						map.put("sellerRate", true);
//						map.put("sellerNick", userId);
//						map.put("lastModifiedBy", userId);
//						map.put("lastModifiedDate", new Date());
//						//操作日志数据补全
//						orderLog.setType("更新");
//						//修改订单状态为买家已评价
//						boolean updateRate = orderService.updateSellerRate(map);
//						if(updateRate){
//							orderLog.setState("成功");
//						}else {
//							orderLog.setState("失败");
//						}
//						//添加到评价列表
//						tradeRates.setContent(content);
//						tradeRates.setCreated(new Date());
//						map.put("lastModifiedBy", userId);
//						tradeRates.setLastModifiedDate(new Date());
//						tradeRates.setOid(arrayOid[i]);
//						tradeRates.setRole("seller");
//						tradeRates.setNick(userId);
//						tradeRates.setRateType("批量评价");
//						tradeRates.setTid(arrayTid[i]);
//						tradeRates.setResult(result);
//						//操作日志数据补全
//						tradeRateLog.setType("添加");
//						boolean saveRate = tradeRatesService.saveTradeRate(tradeRates);
//						if(saveRate){
//							tradeRateLog.setState("成功");
//						}else{
//							tradeRateLog.setState("失败");
//						}
//						orderService.saveUserOperationLog(orderLog);
//						tradeRateLog.setRemark(rateResponse.getSubMsg());
//						json.put("message", "评价成功");
//					}
//					orderService.saveUserOperationLog(tradeRateLog);
//				}
//			} catch (ApiException e) {
//				e.printStackTrace();
//				json.put("message", "评价失败，请联系管理员");
//			}
//		}
//		try {
//			response.getWriter().write(json.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	//---------------------------------------评价记录----------------------------------------------------
	/**
	 * 跳转评价关怀评价记录页面
	 * ZTK2017年8月1日上午11:41:04
	 */
	@RequestMapping("/reviewRecord")
	public String reviewRecord(HttpServletRequest request,TradeRates tradeRates,
			String bTime,String eTime,Integer pageNo,ModelMap model){
		if(pageNo == null){
			pageNo = 1;
		}
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		String contextPath = request.getContextPath();
		Map<String, Object> map = new HashMap<String, Object>();
		//查询用户sessionKey
		String sessionKey = userInfoService.validateFindSessionKey(userId);
		if(tradeRates != null && !"".equals(tradeRates)){
			if("all".equals(tradeRates.getResult()) || tradeRates.getResult() == null || "".equals(tradeRates.getResult())){
				map.put("result", null);
			}else {
				map.put("result", tradeRates.getResult());
			}
			if("全部".equals(tradeRates.getRateType()) || tradeRates.getRateType() == null || "".equals(tradeRates.getRateType())){
				map.put("rateType", null);
			}else{
				map.put("rateType", tradeRates.getRateType());
			}
			if(tradeRates.getNick() != null && !"".equals(tradeRates.getNick())){
				try {
					map.put("nick", decryptClient.encryptData(tradeRates.getNick(), EncrptAndDecryptClient.SEARCH, sessionKey));
//					map.put("nick", tradeRates.getNick());
				} catch (SecretException e) {
					e.printStackTrace();
				}
			}
			if(tradeRates.getOid() != null && !"".equals(tradeRates.getOid())){
				map.put("oid", tradeRates.getOid());
			}
			if(bTime != null && !"".equals(bTime)){
				map.put("bTime", DateUtils.convertDate(bTime));
			}
			if(eTime != null && !"".equals(eTime)){
				map.put("eTime", DateUtils.convertDate(eTime));
			}
			if(userId != null && !"".equals(userId)){
				try {
					if(EncrptAndDecryptClient.isEncryptData(userId, EncrptAndDecryptClient.SEARCH)){
						map.put("ratedNick", userId);
					}else {
						map.put("ratedNick", decryptClient.encryptData(userId, EncrptAndDecryptClient.SEARCH, sessionKey));
					}
				} catch (SecretException e) {
					e.printStackTrace();
				}
				
			}else {
				return "/crms/orderCenterZ/pingjiaguanhuai_3";
			}
			map.put("role", "buyer");
		}
		Pagination pagination = null;
		try {
			pagination = tradeRatesService.queryTradeRates(map,pageNo,userId);
		} catch (SecretException e) {
			e.printStackTrace();
		}
		//路径
		String url = contextPath + "/crms/reviewRecord";
		//封装params
		StringBuilder params = new StringBuilder();
		if(tradeRates.getRateType() != null && !"".equals(tradeRates.getRateType())){
			params.append("&rateType=").append(tradeRates.getRateType());
		}
		if(tradeRates.getNick() != null && !"".equals(tradeRates.getNick())){
			params.append("&nick=").append(tradeRates.getNick());
		}
		if(tradeRates.getOid() != null && !"".equals(tradeRates.getOid())){
			params.append("&oid=").append(tradeRates.getOid());
		}
		if(tradeRates.getResult() != null && !"".equals(tradeRates.getResult())){
			params.append("&result=").append(tradeRates.getResult());
		}
		if(tradeRates.getRatedNick() != null && !"".equals(tradeRates.getRatedNick())){
			params.append("$ratedNick=").append(tradeRates.getRatedNick());
		}
		if(bTime != null && !"".equals(bTime)){
			params.append("&bTime=").append(bTime);
		}
		if(eTime != null && !"".equals(eTime)){
			params.append("&eTime=").append(eTime);
		}
		if(pagination != null){
			pagination.pageView(url, params.toString());
		}
		//设置页面回显
		model.addAttribute("bTime", bTime);
		model.addAttribute("eTime", eTime);
		model.addAttribute("tradeRates", tradeRates);
		model.addAttribute("pagination", pagination);
		return "/crms/orderCenterZ/pingjiaguanhuai_3";
	}
}
