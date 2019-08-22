package s2jh.biz.shop.crm.order.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.entity.Pageination;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.message.entity.SmsHistoryTemplate;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.message.service.SmsHistoryTemplateService;
import s2jh.biz.shop.crm.message.service.SmsTemplateService;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.order.service.OrderRateCareSetupService;
import s2jh.biz.shop.crm.order.service.SmsSendRecordToAppraiseService;
import s2jh.biz.shop.crm.order.service.SmsSettingService;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.getIpAddress;

@Controller
@RequestMapping(value="/appraisePacify")
public class AppraisePacifyController {
	@Autowired 
	private SmsSendRecordToAppraiseService smsSendRecordToAppraiseService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private OrderRateCareSetupService orderRateCareSetupService;
	
	@Autowired
    private UserInfoService userInfoService;
	
	@Autowired
	private SmsSettingService smsSettingService;
	
	
	@RequestMapping(value="/showAppraisePacify")
	public String showAppraisePacify(HttpServletRequest request,String saveFlag,Model model){
		
		//.卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		
		//测试数据//===================
		//String userId = "crzzyboy";
		String appraiseType = "2";

		OrderRateCareSetup orderRateCareSetup = orderRateCareSetupService.findOrderRateCareSetup(userId,appraiseType);
		
		model.addAttribute("orderRateCareSetup", orderRateCareSetup);
		model.addAttribute("saveFlag", saveFlag);
		return "crms/appraise/appraisePacify";
	}
	

	@RequestMapping(value="/queryAppraisePacify")
	public String queryAppraisePacify(String bTime,
									String eTime,
									String buyerNick,
									String orderId,
									String recNum,
									String flag,
									Integer pageNo,
									HttpServletRequest request,
									Model model){
		//卖家id 
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		//测试数据//======================
		//String userId = "crzzyboy";
		
		if(pageNo == null){
			pageNo = 1;
		}
		String contextPath = request.getContextPath();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bTime", DateUtils.convertStringToDate(bTime));
		map.put("eTime", DateUtils.convertStringToDate(eTime));
		map.put("userId", userId);
		map.put("buyerNick", buyerNick);
		map.put("orderId", orderId);
		map.put("recNum", recNum);
		map.put("type", 21);
		Pageination<SmsRecordDTO> pagination = smsSendRecordToAppraiseService.findPaginationByCondition(pageNo, map);
		//路径
		String url = contextPath + "/appraisePacify/queryAppraisePacify";
		//封装params
		StringBuilder params = new StringBuilder();
		if(bTime != null && !"".equals(bTime)){
			params.append("&bTime=").append(bTime);
		}
		if(eTime != null && !"".equals(eTime)){
			params.append("&eTime=").append(eTime);
		}
		if(flag != null && !"".equals(flag)){
			params.append("&flag=").append(flag);
		}
		if(buyerNick != null && !"".equals(buyerNick)){
			params.append("&buyerNick=").append(buyerNick);
		}
		if(orderId != null && !"".equals(orderId)){
			params.append("&orderId=").append(orderId);
		}
		if(recNum != null && !"".equals(recNum)){
			params.append("&recNum=").append(recNum);
		}
		pagination.pageView(url, params.toString());
		
		//将数据回显页面
		model.addAttribute("bTime", bTime);
		model.addAttribute("eTime", eTime);
		model.addAttribute("flag", flag);
		model.addAttribute("buyerNick", buyerNick);
		model.addAttribute("orderId", orderId);
		model.addAttribute("recNum", recNum);
		model.addAttribute("pagination", pagination);
		
		return "crms/appraise/appraisePacify";
	}
	
	
	/**
	 *  查询短信模板,中评,差评,
	 * helei 2017年1月4日下午6:12:28
	 */
	@Autowired
	private SmsTemplateService smsTemplateService;
	@RequestMapping(value="/querySmsTemplate")
	public String querySmsTemplate(String smsType,HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		//测试数据//======================
		//String userId = "crzzyboy";
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", smsType);
		map.put("userNick", userId);
		List<SmsTemplate> smsTemplate = new ArrayList<SmsTemplate>();
		if("安抚".equals(smsType)){
			smsTemplate = smsTemplateService.findAllSmsTemplates(map);
		}else if ("自定义安抚".equals(smsType)){
			smsTemplate = smsTemplateService.findTemplateByTypeAnduserNick(map);
		}
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
	 * helei 2017年1月4日下午6:12:28
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
	 * 添加我的模板--中差评安抚
	 * helei 2017年1月5日上午11:20:11
	 * @throws IOException 
	 */
	@RequestMapping(value="/addMyTempltate")
	public String addMyTempltate(String type, String content,HttpServletResponse response,HttpServletRequest request) throws IOException{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		//测试数据//======================
		//String userId = "crzzyboy";
			
		
			//创建日志对象
			UserOperationLog op = new UserOperationLog();
			//操作日志数据补全
			op.setState("成功");
			op.setFunctions("中差评安抚");
			op.setType("添加");
			op.setDate(new Date());
			op.setRemark("中差评安抚添加我的模板");
			op.setIpAdd(getIpAddress.getIpAddress(request));
			op.setUserId(userId);
			op.setFunctionGens("21");
			
			//创建SmsTemplate对象补全数据添加,添加模板
			SmsTemplate smsTemplate = new SmsTemplate();
			smsTemplate.setUserNick(userId);
			smsTemplate.setType(type);
			smsTemplate.setCreateDate(new Date().toString());
			smsTemplate.setContent(content);
			
			//添加我的模板,短信内容
			smsTemplateService.saveSmsTemplate(op, smsTemplate);
			
			//创建对象封装参数,输出到前台
			JSONObject  json = new JSONObject();
			json.put("success", 1);
			//将封装好的json数据,输出到前台
			response.setContentType("application/json; charset=utf-8");  
			response.getWriter().write(json.toString());
		return null;
	}
	
	
	/**
	 * 中差评安抚设置
	 * helei 2017年1月5日下午2:32:08
	 */
	@RequestMapping(value="/saveOrUpdatePacify")
	public String saveOrUpdatePacify(RedirectAttributes attr,
			OrderRateCareSetup orderRateCareSetup, String smsSign,
			HttpServletRequest request, Model model) {
		//.卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		
		//测试数据//===================
		//String userId = "crzzyboy";
		
		//定义保存成功标识
		String saveFlag = "";
		
		//创建map封装参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		//创建日志对象
		UserOperationLog op = new UserOperationLog();
		
		OrderRateCareSetup orderRate  = orderRateCareSetupService.findOrderRateOfStatus(map);
		
		//判断id是否为空
		if(orderRate.getId() != null && !"".equals(orderRate.getId())){
			try {
				orderRateCareSetup.setLastModifiedDate(new Date());
				orderRateCareSetupService.updateOrderRateCareSetup(orderRateCareSetup);
				op.setState("成功");
				saveFlag = "success";
			} catch (Exception e) {
				op.setState("失败");
				saveFlag = "error";
			}finally{
				/**
				 * 添加操作日志
				 */
				//操作日志数据补全
				op.setFunctions("中差评安抚");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("中差评安抚设置");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(userId);
				op.setFunctionGens("21");
				//调用日志方法添加日志
				orderRateCareSetupService.saveUserOperationLog(op);
			}
		}else{
			//补全数据
			orderRateCareSetup.setUserId(userId);
			orderRateCareSetup.setAppraiseType("2");
			orderRateCareSetup.setCreatedDate(new Date());
			
			try {
				//添加基本信息
				orderRateCareSetupService.saveOrderRateCareSetup(orderRateCareSetup);
				op.setState("成功");
				saveFlag = "success";
			} catch (Exception e) {
				op.setState("失败");
				saveFlag = "error";
			}finally{
				
				/**
				 * 添加操作日志
				 */
				//操作日志数据补全
				op.setFunctions("中差评安抚");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("中差评安抚设置");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(userId);
				op.setFunctionGens("21");
				//调用日志方法添加日志
				orderRateCareSetupService.saveUserOperationLog(op);
			}
		}
		
		saveOrUpdateSmsSetting(orderRateCareSetup.getContent(), "21", smsSign, userId);
		attr.addAttribute("saveFlag", saveFlag);
		String sellerNick = (String) request.getSession().getAttribute("taobao_user_nick");
		this.userInfoService.addUserPermitByMySql(sellerNick, null);
		return "redirect:showAppraisePacify";
	}
	
	
	/**
	 * 短信设置保存,修改======================================
	 * 
	 */
	public void saveOrUpdateSmsSetting(String content,String type,String smsSign,String userId){
		//计算短信内容
		if(content!=null && !"".equals(content)){
			content = content.replace("退订回N", "")+"退订回N";
		}
		int smsNum = 0;
		if(content.length()<=70){
			smsNum = 1;
		}else{
			smsNum =(content.length() + 66) / 67;
		}
		
		// 查询短信设置信息
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, type);
		if(smsSetting.getId()!=null && !"".equals(smsSetting.getId())){
			smsSetting.setSmsNumber(smsNum);
			smsSetting.setMessageContent(content);
			smsSetting.setMessageSignature(smsSign);
			smsSettingService.updateMessageSetting(smsSetting);
		}else{
			//为空,添加,并设置参数,补全信息
			smsSetting.setUserId(userId);
			smsSetting.setSettingType(type);
			smsSetting.setStatus("0");
			smsSetting.setMessageSignature(smsSign);
			smsSetting.setSmsNumber(smsNum);
			smsSetting.setMessageContent(content);
			smsSetting.setCreatedDate(new Date());
			//保存短信内容
			myBatisDao.execute(SmsSetting.class.getName(), "doCreateAutoModel", smsSetting);
		}
	}
}
