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
public class WaitingRefundController {
	
	@Autowired
	private OrderSetupService orderSetupService;
	
	@Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
	
	@Autowired
	private SmsSettingService smsSettingService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 等待退货 基本设置
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/waitingbasicSetup", method = RequestMethod.POST)
	public String waitingbasicSetup(ModelMap model,OrderSetup orderSetup,HttpServletRequest request,
			@RequestParam(value="startTime",defaultValue="8:00")String startTime,
			@RequestParam(value="endTime",defaultValue="22:00")String endTime){
		orderSetup.setStartTime(startTime);
		orderSetup.setEndTime(endTime);
		String userId= request.getSession().getAttribute("taobao_user_nick").toString();
		//String userId= "crzzyboy";
		String settingType="31";
		boolean flag = false;
		UserOperationLog op = new UserOperationLog();
		if(orderSetup.getId() != null && !"".equals(orderSetup.getId())){
			try {
				orderSetup.setLastModifiedDate(new Date());
				flag = orderSetupService.updatewaitingRefund(orderSetup, request);
				if(flag){
					op.setState("基本设置修改成功");
				}else{
					op.setState("基本设置修改失败");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				op.setState("基本设置修改失败");
			}finally{
				//操作日志数据补全
				op.setFunctions("等待退货基本设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens("31");
				orderSetupService.saveUserOperationLog(op);
			}
		}else{
			//用户Id
			orderSetup.setUserId(userId);
			//创建时间
			orderSetup.setCreatedDate(new Date());
			//设置类型
			orderSetup.setSettingType("31");
			//默认关闭
			orderSetup.setStatus("1");
			//设置时间过滤条件
			orderSetup.setFiltingConditions("1,3");
			try {
				flag = orderSetupService.savewaitingRefund(orderSetup,request);
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
				//操作日志数据补全
				op.setFunctions("等待退货基本设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens("31");
				orderSetupService.saveUserOperationLog(op);
			}
		}
		
		//基本设置
		OrderSetup waitingRefund = orderSetupService.findWaitingRefund(userId,request);
		model.addAttribute("waitingRefund", waitingRefund);
		
		model.addAttribute("msg", flag?ResourceGetValueUtil.getValue("order.setup.save.success.msg"):ResourceGetValueUtil.getValue("order.setup.save.failure.msg"));
		//高级设置
		OrderAdvancedSetting waitingRefundGj = orderAdvancedSettingService.findWaitingRefundGj(userId,request);
		model.addAttribute("waitingRefundGj", waitingRefundGj);
		//短信设置
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		
		return "redirect:crms/refundCare/waitingRefund";
	}
	
	/**
	 * 等待退货 高级设置
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/waitingRefundGjSetup", method = RequestMethod.POST)
	public String saveWaitingRefund(ModelMap model,OrderAdvancedSetting orderAdvancedSetting2,HttpServletRequest request){
		
		String userId= request.getSession().getAttribute("taobao_user_nick").toString();
		//String userId= "crzzyboy";
		boolean flag= false;
		String settingType="31";
		UserOperationLog op = new UserOperationLog();
		 if(orderAdvancedSetting2.getId()!=null && !"".equals(orderAdvancedSetting2.getId())){
			try {
				orderAdvancedSetting2.setLastModifiedDate(new Date());
				flag= orderAdvancedSettingService.updateWaitingRefundGj(orderAdvancedSetting2, request);
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
				op.setFunctions("等待退货高级设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderAdvancedSetting2.getUserId());
				op.setFunctionGens("31");
				orderAdvancedSettingService.saveUserOperationLog(op);
			}
		 }else{
			 //用户Id
			 orderAdvancedSetting2.setUserId(userId);
			 //创建时间
			 orderAdvancedSetting2.setCreatedDate(new Date());
			 //设置类型
			 orderAdvancedSetting2.setSettingType("31");
		     //默认设置定时发送为关闭
			 orderAdvancedSetting2.setStatus("1");
			try {
				flag=orderAdvancedSettingService.savewaitingRefundGj(orderAdvancedSetting2, request);
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
				op.setFunctions("等待退货高级设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderAdvancedSetting2.getUserId());
				op.setFunctionGens("31");
				orderAdvancedSettingService.saveUserOperationLog(op);
			}
		 }
		 
		//基本设置
		OrderSetup rufundSeccess = orderSetupService.findRefundSuccess(userId,request);
		model.addAttribute("rufundSeccess", rufundSeccess);
		
		//高级设置
		if(flag){
			model.addAttribute("orderAdvancedSetting2", orderAdvancedSetting2);
			model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.success.msg"));
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.failure.msg"));
			orderAdvancedSetting2 = orderAdvancedSettingService.findbuyerSuccessFirst(userId,request);
			model.addAttribute("orderAdvancedSetting2", orderAdvancedSetting2);
		}
		
		//短信设置
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		
		return "redirect:crms/refundCare/waitingRefund";
	}
	
	/**
	 * 等待退货 短信设置
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/saveWaitingRefundSmsSetting")
	public String saveWaitingRefundSmsSetting(ModelMap modelMap,SmsSetting smsSetting,HttpServletRequest request){
		
		String userId= request.getSession().getAttribute("taobao_user_nick").toString();
		//String userId= "crzzyboy";
		boolean flag = false;
		UserOperationLog op = new UserOperationLog();
		if(smsSetting.getId() != null && !"".equals(smsSetting.getId())){
			try {
				String messageSignature = smsSetting.getMessageSignature();
				if(messageSignature!=null && messageSignature!=""){
					smsSetting.setLastModifiedDate(new Date());
					flag= smsSettingService.updateWaitingRefundSmsSetting(smsSetting, request);
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
				op.setFunctions("等待退货短信设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(smsSetting.getUserId());
				op.setFunctionGens("31");
				smsSettingService.saveUserOperationLog(op);
			}
		}else{
			//用户Id
			smsSetting.setUserId(userId);
			//创建时间
			smsSetting.setCreatedDate(new Date());
			//补全属性
			smsSetting.setSettingType("31");
			//默认设置为关闭
			smsSetting.setStatus("1");
			try {
				flag = smsSettingService.saveWaitingRefundSmsSetting(smsSetting, request);
				if(flag){
					op.setState("短信设置保存成功");
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
				op.setFunctions("等待退货短信设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(smsSetting.getUserId());
				op.setFunctionGens("31");
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
		return "redirect:crms/refundCare/waitingRefund";
	}
	
	@RequestMapping(value="/crms/refundCare/waitingRefund",method = RequestMethod.GET)
	public String waitingRefund(String msg,ModelMap model,HttpServletRequest request){
        
		String userId= request.getSession().getAttribute("taobao_user_nick").toString();
		//String userId= "crzzyboy";
		String settingType="31";
		//基本设置
		OrderSetup waitingRefund = orderSetupService.findWaitingRefund(userId,request);
		model.addAttribute("waitingRefund", waitingRefund);
		//高级设置
		OrderAdvancedSetting waitingRefundGj = orderAdvancedSettingService.findWaitingRefundGj(userId,request);
		model.addAttribute("waitingRefundGj", waitingRefundGj);
		//短信设置
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		
		if(msg!=null && msg!="" && msg!="null"){
			model.addAttribute("msg", msg);
		}
		return "crms/refundCare/waitingRefund";
	}
	
	
	/**
	 * 等待退货 开启关闭
	 * @throws IOException 
	 */
	@RequestMapping(value="/waitingRefundOpen",method=RequestMethod.POST)
	public String waitingRefundOpen(String buttonId,String orderSetupId,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
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
