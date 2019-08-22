package s2jh.biz.shop.crm.order.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.message.entity.SmsHistoryTemplate;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.message.service.SmsHistoryTemplateService;
import s2jh.biz.shop.crm.message.service.SmsTemplateService;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.order.service.OrderService;
import s2jh.biz.shop.crm.order.service.TradeRatesService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

@Controller
@RequestMapping(value="/appraiseAmend")
public class AppraiseAmendController {
	
	@Autowired 
	TradeRatesService tradeRatesService;
	
	@Autowired 
	private TradeInfoService tradeInfoService;
	
	
	/**
	 * 查询判断参数: 订单查询,评价时间,以及选项卡,条件判断
	 * helei 2016年12月30日下午3:49:42
	 */
	@RequestMapping(value="/showAppraiseAmend")
	public String queryAppraiseAmend(String orderId,
									String bTime,
									String eTime,
									String result,
									Integer pageNo,
									HttpServletRequest request,
									Model model){
		//卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		//测试数据//======================
		//userId = "crzzyboy";
		
		//定义参数获取计算后的时间
		String countTime = "";
		
		//当前时间--也是结束数据
		String nowTime = "";
	
		if(orderId == null || "".equals(orderId)){
			if(bTime !=null && eTime !=null ){
				if(!bTime.equals("") || !eTime.equals("")){
					countTime = bTime;
					nowTime = eTime;
				}else{
					//获取当前时间
					Calendar calendar = Calendar.getInstance();//获取的是系统当前时间
					//获取当前时间
					nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
					
					//计算30天以内的时间
					calendar.add(Calendar.MONTH, -1);
					countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				}
			}else{
				//获取当前时间
				Calendar calendar = Calendar.getInstance();//获取的是系统当前时间
				//获取当前时间
				nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				
				//计算30天以内的时间
				calendar.add(Calendar.MONTH, -1);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
			}
		}else{
			countTime = bTime;
			nowTime = eTime;
		}
		
		if(pageNo == null){
			pageNo = 1;
		}
		String oid = "";//接收oid值
		String noOid = "";//接收不oid的值
		if(orderId != null && !orderId.equals("")){
			if(this.isOid(orderId)){
				oid = orderId;
			}else{
				noOid = orderId;
			}
		}
		
		
		String contextPath = request.getContextPath();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bTime", DateUtils.convertStringToDate(countTime));
		map.put("eTime", DateUtils.convertStringToDate(nowTime));
		map.put("ratedNick", userId);
		map.put("oid", oid);
		map.put("noOid", noOid);
		map.put("role", "buyer");
		map.put("result", result);
		Pagination pagination = tradeRatesService.findTradeRatesByQuery(pageNo, map);
		//路径
		String url = contextPath + "/appraiseAmend/showAppraiseAmend";
		//封装params
		StringBuilder params = new StringBuilder();
		if(bTime != null && !"".equals(bTime)){
			params.append("&bTime=").append(bTime);
		}
		if(eTime != null && !"".equals(eTime)){
			params.append("&eTime=").append(eTime);
		}
		if(result != null && !"".equals(result)){
			params.append("&result=").append(result);
		}
		if(orderId != null && !"".equals(orderId)){
			params.append("&orderId=").append(orderId);
		}
		pagination.pageView(url, params.toString());
		
		//将数据回显页面
		model.addAttribute("filtrateTime", "30天内");
		model.addAttribute("flag", result);
		if(result ==null){
			model.addAttribute("result", "all");
		}else{
			if(result.equals("good") || result.equals("iSDelete")){
				
			}else{
				model.addAttribute("result", "all");
			}
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("bTime", bTime);
		model.addAttribute("eTime", eTime);
		model.addAttribute("pagination", pagination);
		return "crms/appraise/appraiseAmend";
	}
	
	
	
	
	/**
	 * 点击选择框查询数据
	 * helei 2016年12月30日下午3:50:01
	 */
	 
	@RequestMapping(value="/queryAppraiseAmendClick")
	public String queryAppraiseAmendClick(String filtrateTime,
									String nowTime,
									String result,
									String flag,
									Integer pageNo,
									HttpServletRequest request,
									Model model){
		//卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		//测试数据//======================
		//String userId = "crzzyboy"; 
		
		
		//获取当前时间
		Calendar calendar = Calendar.getInstance();//获取的是系统当前时间
		
		//定义参数获取计算后的时间
		String countTime = "";
	
		//获取计算后的到期时间
		String dueTime ="";
		
		
		//1.判断时间,并计算出时间
		if(filtrateTime !=null && !"".equals(filtrateTime)){
			//当前时间
			nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
			if(filtrateTime.equals("180天内")){
				calendar.add(Calendar.MONTH, -6);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				
			}else if(filtrateTime.equals("30天内")){
				calendar.add(Calendar.MONTH, -1);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				
			}else if(filtrateTime.equals("今日新增")){
				dueTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				nowTime="";
				
			}else if(filtrateTime.equals("近三天新增")){
				calendar.add(Calendar.DATE, -2);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				
			}else if(filtrateTime.equals("一天后过期")){
				calendar.add(Calendar.DATE, -14);
				dueTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				nowTime="";
				
			}else if(filtrateTime.equals("三天后过期")){
				calendar.add(Calendar.DATE, -12);
				dueTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				nowTime="";
				
			}else if(filtrateTime.equals("已过期")){
				calendar.add(Calendar.DATE, -15);
				nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
			}
			
		}
		
		if(pageNo == null){
			pageNo = 1;
		}
		
		//定义接收result参数
		String resultTwo = "";
		//将选项卡和点击点击中差,差评时合并数据
		if(!result.equals("all") && !result.equals("neutral") && !result.equals("bad")){
			if(flag != null && !flag.equals("")){
				if(flag.equals("good")){
					resultTwo = flag;
				}
				if(flag.equals("iSDelete")){
					resultTwo = flag;
				}
			}
		}else{
			flag = "";
			if(!result.equals("all")){
				resultTwo = result;
			}
		}
		
		String contextPath = request.getContextPath();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bTime", DateUtils.convertDate(countTime));
		map.put("eTime", DateUtils.convertDate(nowTime));
		map.put("ratedNick", userId);
		map.put("role", "buyer");
		map.put("result", resultTwo);
		map.put("dueTime", DateUtils.convertDate(dueTime));
		Pagination pagination = tradeRatesService.findTradeRatesByQuery(pageNo, map);
		//路径
		String url = contextPath + "/appraiseAmend/queryAppraiseAmendClick";
		//封装params
		StringBuilder params = new StringBuilder();
		if(filtrateTime != null && !"".equals(filtrateTime)){
			params.append("&filtrateTime=").append(filtrateTime);
		}
		if(nowTime != null && !"".equals(nowTime)){
			params.append("&nowTime=").append(nowTime);
		}
		if(result != null && !"".equals(result)){
			params.append("&result=").append(result);
		}
		pagination.pageView(url, params.toString());
		
		model.addAttribute("filtrateTime", filtrateTime);
		model.addAttribute("result", result);
		model.addAttribute("flag", flag);
		model.addAttribute("pagination", pagination);
		//将数据回显页面
		return "crms/appraise/appraiseAmend";
	}
	
	
	
	
	/**
	 * 使用ajaxt通过订单id获取电话号码
	 * helei 2016年12月30日下午3:50:44
	 */
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/queryPhoneByOrderIds")
	public String queryPhoneByOrderIds(String orderIds,HttpServletRequest rquest,HttpServletResponse response) throws IOException{
		String userId = (String) rquest.getSession().getAttribute("taobao_user_nick");
		String phone = "";//接收的手机号
		String nickname = "";//买家昵称
		
		String[] orderIdList = orderIds.split(",");
		for (int i = 0; i < orderIdList.length; i++) {
			
			//Orders orders = orderService.queryPhoneByOrderId(orderIdList[i]);
			//@author:jackstraw_yu 修改
			OrdersDTO orders = tradeInfoService.queryMobileByOrderId(orderIdList[i],userId);
			if(orders==null) continue;
			phone+= orders.getReceiverMobile()+",";
			nickname+=orders.getBuyerNick()+",";
		}
		phone = phone.substring(0, phone.length()-1);
		nickname = nickname.substring(0, nickname.length()-1);
		//创建对象封装参数,输出到前台
		JSONObject  json = new JSONObject();
		json.put("phone", phone);
		json.put("nickname", nickname);
		//将封装好的json数据,输出到前台
		response.setContentType("application/json; charset=utf-8");  
		response.getWriter().write(json.toString());
		return null;
	}
	
	/**
	 * 查询短信模板,中评,差评,
	 * helei 2016年12月30日下午4:27:12
	 */
	@Autowired
	private SmsTemplateService smsTemplateService;
	@RequestMapping(value="/querySmsTemplate")
	public String querySmsTemplate(String smsType,HttpServletResponse response) throws IOException{
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", smsType);
		
		List<SmsTemplate> smsTemplate = smsTemplateService.findAllSmsTemplates(map);
		//创建对象封装参数,输出到前台
		JSONObject  json = new JSONObject();
		json.put("smsTemplate", smsTemplate);
		//将封装好的json数据,输出到前台
		response.setContentType("application/json; charset=utf-8");  
		response.getWriter().write(json.toString());
		return null;
	}
	
	
	/**
	 * 查询历史使用的短信模板
	 * helei 2016年12月31日下午5:26:40
	 */
	@Autowired
	private SmsHistoryTemplateService smsHistoryTemplateService;
	@RequestMapping(value="/querySmsHistoryTemplate")
	public String querySmsHistoryTemplate(String type,HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		//测试数据//======================
		//String userId = "crzzyboy";
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("userId", userId);
		List<SmsHistoryTemplate> findListAll = smsHistoryTemplateService.findListAll(map);
		//创建对象封装参数,输出到前台
		JSONObject  json = new JSONObject();
		json.put("smsTemplate", findListAll);
		//将封装好的json数据,输出到前台
		response.setContentType("application/json; charset=utf-8");  
		response.getWriter().write(json.toString());
		return null;
	}
	
	/**
	 * 验证订单编号
	 * helei 2017年3月15日下午4:49:16
	 */
	private boolean isOid(String oid) {
		Pattern p = Pattern.compile("^\\d{16}$");
		Matcher m = p.matcher(oid);
		return m.matches();
	}
}
