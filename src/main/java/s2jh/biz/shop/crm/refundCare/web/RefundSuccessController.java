package s2jh.biz.shop.crm.refundCare.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.message.service.SmsTemplateService;
import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.OrderAdvancedSettingService;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.order.service.SmsSettingService;
import s2jh.biz.shop.crm.order.util.ResourceGetValueUtil;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.getIpAddress;

@Controller
@RequestMapping(value="")
public class RefundSuccessController {
	
	@Autowired
	private OrderSetupService  orderSetupService;
	
	@Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
	
	@Autowired
	private SmsSettingService smsSettingService;
    	
	@Autowired
	private SmsTemplateService smsTemplateService;
	
	@Autowired
	private UserInfoService userInfoService;
	/**
	 * 退款成功基本设置
	 * @return
	 */
	@RequestMapping(value = "/refundSuccess", method = RequestMethod.POST)
	public String saverefundSuccess(ModelMap model,OrderSetup orderSetup,HttpServletRequest request,
			@RequestParam(value="startTime",defaultValue="8:00")String startTime,
			@RequestParam(value="endTime",defaultValue="22:00")String endTime){
		orderSetup.setStartTime(startTime);
		orderSetup.setEndTime(endTime);
		String userId= (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId="crzzyboy";
		String settingType="30";
		UserOperationLog op = new UserOperationLog();
		boolean flag = false;
		if(orderSetup.getId() != null && !"".equals(orderSetup.getId())){
			try {
			    orderSetup.setLastModifiedDate(new Date());
			    flag =orderSetupService.updateRefundSuccess(orderSetup, request);
			    if(flag){
			    	op.setState("基本设置修改成功");
			    }else{
			    	op.setState("基本设置修改失败");
			    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				op.setState("基本设置保存失败");
			}finally{
				//添加操作日志
				op.setFunctions("退款成功高级设置添加");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens("30");
				orderSetupService.saveUserOperationLog(op);
			}
		}else{
			//用户Id
			orderSetup.setUserId(userId);
			//创建时间
			orderSetup.setCreatedDate(new Date());
			//设置类型
			orderSetup.setSettingType("30");
			//默认设置定时发送为关闭
			orderSetup.setStatus("1");
			//设置时间过滤条件
			orderSetup.setFiltingConditions("1,3");
			try {
				flag=orderSetupService.saverefundSuccess(orderSetup, request);
				if(flag){
					op.setState("基本设置保存成功");
				}else{
					op.setState("基本设置保存失败");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				op.setState("基本设置保存失败");
			}finally{
				//添加操作日志
				op.setFunctions("退款成功基本设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens("30");
				orderSetupService.saveUserOperationLog(op);
			}
		}
		
		//基本设置
		OrderSetup rufundSeccess = orderSetupService.findRefundSuccess(userId,request);
		model.addAttribute("rufundSeccess", rufundSeccess);
		model.addAttribute("msg", flag?ResourceGetValueUtil.getValue("order.setup.save.success.msg"):ResourceGetValueUtil.getValue("order.setup.save.failure.msg"));
		
		//高级设置
		OrderAdvancedSetting firstclass = orderAdvancedSettingService.findbuyerSuccessFirst(userId,request);
		model.addAttribute("firstclass", firstclass);
		
		//短信设置
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		
		return "crms/refundCare/refundSuccess";
	}
	
	
	/**
	 * 退款成功高级设置
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/refundSuccessfirstClass", method = RequestMethod.POST)
	public String saverefundSuccessGj(ModelMap model,OrderAdvancedSetting orderAdvancedSetting1,HttpServletRequest request){
		
		String userId= (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId="crzzyboy";
		boolean flag = false;
		String settingType="30";
		UserOperationLog op = new UserOperationLog();
		if(orderAdvancedSetting1.getId() != null && !"".equals(orderAdvancedSetting1.getId())){
			try {
				orderAdvancedSetting1.setLastModifiedDate(new Date());
				flag=orderAdvancedSettingService.updateRefundSuccess(orderAdvancedSetting1,request);
				if(flag){
					op.setState("高级设置修改成功");
				}else{
					op.setState("高级设置修改失败");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				op.setState("高级设置修改失败");
			}finally{
				//操作日志数据补全
				op.setFunctions("退款成功高级设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderAdvancedSetting1.getUserId());
				op.setFunctionGens("30");
				orderAdvancedSettingService.saveUserOperationLog(op);
			}
		}else{
			//用户Id
			orderAdvancedSetting1.setUserId(userId);
			//创建时间
			orderAdvancedSetting1.setCreatedDate(new Date());
			//设置类型
			orderAdvancedSetting1.setSettingType("30");
			//默认设置定时发送为关闭
			orderAdvancedSetting1.setStatus("1");
			try {
				flag = orderAdvancedSettingService.saveRefundSuccess(orderAdvancedSetting1, request);
				if(flag){
					op.setState("高级设置保存成功");
				}else{
					op.setState("高级设置保存失败");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				op.setState("高级设置保存失败");
			}finally{
				//操作日志数据补全
				op.setFunctions("退款成功高级设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderAdvancedSetting1.getUserId());
				op.setFunctionGens("30");
				orderAdvancedSettingService.saveUserOperationLog(op);
			}
		}
		//基本设置
		OrderSetup rufundSeccess = orderSetupService.findRefundSuccess(userId,request);
		model.addAttribute("rufundSeccess", rufundSeccess);
		
		//高级设置
		if(flag){
			model.addAttribute("orderAdvancedSetting1", orderAdvancedSetting1);
			model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.success.msg"));
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.failure.msg"));
			orderAdvancedSetting1 = orderAdvancedSettingService.findbuyerSuccessFirst(userId,request);
			model.addAttribute("orderAdvancedSetting1", orderAdvancedSetting1);
		}
		
		//短信设置
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		return "redirect:crms/refundCare/refundSuccess";
	}
	
	/**
	 * 退款成功短信设置
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/refuendSuccessSmsSetting",method=RequestMethod.POST)
	public String saveRefundSuccessSmsSetting(ModelMap modelMap,SmsSetting smsSetting,HttpServletRequest request){
		
		String userId= (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId="crzzyboy";
		boolean flag = false;
		UserOperationLog op = new UserOperationLog();
		if(smsSetting.getId()!=null && !"".equals(smsSetting.getId())){
			try {
				String messageSignature = smsSetting.getMessageSignature();
				if(messageSignature!=null && messageSignature!=""){
					smsSetting.setLastModifiedDate(new Date());
					flag=smsSettingService.updateRefundSuccessSmsSetting(smsSetting, request);
					if(flag){
						op.setState("短信设置修改成功");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.success.msg"));
					}else{
						op.setState("短信设置修改失败");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.failure.msg"));
					}
				}else{
					op.setState("短信签名不能为空");
					modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.null.msg"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				op.setState("短信设置修改失败");
			}finally{
				//操作日志数据补全
				op.setFunctions("退款成功短信设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(smsSetting.getUserId());
				op.setFunctionGens("30");
				smsSettingService.saveUserOperationLog(op);
			}
		}else{
			//用户Id
			smsSetting.setUserId(userId);
			//创建时间
			smsSetting.setCreatedDate(new Date());
			//设置类型
			smsSetting.setSettingType("30");
			//默认关闭
			smsSetting.setStatus("1");
			try {
				flag = smsSettingService.saveRefundSuccessSmsSetting(smsSetting, request);
				if(flag){
					op.setState("短息设置保存成功");
					modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.success.msg"));
				}else{
					op.setState("短信设置保存失败");
					modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.failure.msg"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				op.setState("短信设置保存失败");
			}finally{
				//操作日志数据补全
				op.setFunctions("退款成功短信设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(smsSetting.getUserId());
				op.setFunctionGens("30");
				smsSettingService.saveUserOperationLog(op);
			}
		}
		
		//修改用户的店铺签名
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("shopName", smsSetting.getMessageSignature());
		int status = userInfoService.updateShopName(map);
		if(status>0){
			HttpSession session = request.getSession();
			session.setAttribute("ShopName", smsSetting.getMessageSignature());
		}
		smsSettingService.findSmsSettingList(userId, smsSetting.getMessageSignature());
		smsSettingService.updateOrderReminderSmsContent(smsSetting.getMessageSignature(),userId);
		return "redirect:crms/refundCare/refundSuccess";
	}
	
	/**
	 * 保存短信模板
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/saveRefundCareSmsTemplate")
	public void saveRefundCareSmsTemplate(HttpServletRequest request,HttpServletResponse response,String smsName,String smsContent){
		JSONObject json =  new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute("taobao_user_nick");
			//String userId="crzzyboy";
			SmsTemplate smsTemplate = new SmsTemplate();
			smsTemplate.setContent(smsContent);
			smsTemplate.setName(smsName);
			smsTemplate.setCreatedDate(new Date());
			smsTemplate.setType("自定义");
			smsTemplateService.save(smsTemplate);
			json.put("message", true);
			response.setContentType("application/json; charset=utf-8");  
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			json.put("message", false);
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/crms/refundCare/refundSuccess",method = RequestMethod.GET)
	public String refundSuccess(String msg,ModelMap model,HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId="crzzyboy";
		String settingType="30";
		//基本设置
		OrderSetup rufundSeccess = orderSetupService.findRefundSuccess(userId,request);
		model.addAttribute("rufundSeccess", rufundSeccess);
		
		//高级设置
		OrderAdvancedSetting firstclass = orderAdvancedSettingService.findbuyerSuccessFirst(userId,request);
		model.addAttribute("firstclass", firstclass);
		
		//短信设置
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		
		if(msg!=null && msg!="" && msg!="null"){
			model.addAttribute("msg", msg);
		}
		
		return "crms/refundCare/refundSuccess";
	}
	
	
	/**
	 * 退款成功开启关闭
	 * @throws IOException 
	 */
	@RequestMapping(value="/refundSuccessOpen",method=RequestMethod.POST)
	public String refundSuccessOpen(String buttonId,String orderSetupId,HttpServletRequest request,HttpServletResponse response) throws IOException {
		OrderSetup orderSetup = new OrderSetup();
		orderSetup.setId(Long.parseLong(orderSetupId));
		orderSetup.setStatus(buttonId);
		
		
		//创建对象封装参数,输出到前台
		JSONObject  json = new JSONObject();
		
		try {
			orderSetupService.updateRefundSuccess(orderSetup, request);
			String sellerNick = (String) request.getSession().getAttribute("taobao_user_nick");
			this.userInfoService.addUserPermitByMySql(sellerNick, null);
			json.put("status", buttonId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//将封装好的json数据,输出到前台
		finally{
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(json.toString());
		}  
		
			
		return null;
	}

}
