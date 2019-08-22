package s2jh.biz.shop.crm.refundCare.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping(value = "")
public class BuyerRefundController {
	
	@Autowired
	private OrderSetupService orderSetupXDGHService;
	
	@Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
	
	@Autowired
	private SmsSettingService smsSettingService;
	
	@Autowired
	private OrderSetupService orderSetupService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 基本设置
	 * @return
	 */
	@RequestMapping(value = "/basicSetup", method = RequestMethod.POST)
	public String basicSetup(ModelMap model,OrderSetup orderSetup,HttpServletRequest request,
			@RequestParam(value="startTime",defaultValue="8:00")String startTime,
			@RequestParam(value="endTime",defaultValue="22:00")String endTime){
		orderSetup.setStartTime(startTime);
		orderSetup.setEndTime(endTime);
		String userId = request.getSession().getAttribute("taobao_user_nick").toString();
		//String userId= "crzzyboy";
		String settingType="29";
		boolean  flag = false;
		UserOperationLog op = new UserOperationLog();
		if(orderSetup.getId() != null && !"".equals(orderSetup.getId())){
			/*
			 * 修改基本信息
			 */
			try {
				orderSetup.setLastModifiedDate(new Date());
				flag =orderSetupXDGHService.updateBuyerRefundSetting(orderSetup,request);
				if(flag){
					op.setState("基本设置保修改成功");
				}else{
					op.setState("基本设置修改失败");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				op.setState("基本设置保存失败");
			}finally{
				//操作日志数据补全
				op.setUserId(orderSetup.getUserId());
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setFunctions("买家申请退款基本设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens("29");
				orderSetupXDGHService.saveUserOperationLog(op);
			}
		}else{
			//获取userId
			orderSetup.setUserId(userId);
			//设置类型
			orderSetup.setSettingType("29");
			//默认设置定时发送为关闭
			orderSetup.setStatus("1");
			//设置时间过滤条件
			orderSetup.setFiltingConditions("1,3");
			//创建时间
			orderSetup.setCreatedDate(new Date()); 
			//保存基本设置
			try {
				flag=orderSetupXDGHService.savebuyerOrderSetup(orderSetup,  request);
				if(flag){
					op.setState("基本设置保存成功");
				}else{
					op.setState("基本设置保存失败");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				op.setState("基本设置保存失败");
			}finally{
				//操作日志数据补全
				op.setFunctions("买家申请退款基本设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens("29");
				orderSetupXDGHService.saveUserOperationLog(op);
			}
			
		}
		
		//查询买家申请退款基本设置
		OrderSetup buyerRefund = orderSetupXDGHService.findBuyerRefund(userId,request);
		model.addAttribute("buyerRefund", buyerRefund);
		
		model.addAttribute("msg", flag?ResourceGetValueUtil.getValue("order.setup.save.success.msg"):ResourceGetValueUtil.getValue("order.setup.save.failure.msg"));
		//查询买家申请退款高级设置
		OrderAdvancedSetting orderAdvancedSetting = orderAdvancedSettingService.findBuyerRefundAdvanced(userId,request);
		model.addAttribute("orderAdvancedSetting", orderAdvancedSetting);
		//查询买家申请退款 短信设置
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		return "crms/refundCare/buyerRefund";
		
	}
	
	/**
	 * 高级设置
	 * @return
	 */
	@RequestMapping(value = "/advancedSetting", method = RequestMethod.POST)
	public String advanedSetting(OrderAdvancedSetting orderAdvancedSetting,HttpServletRequest request,ModelMap model){
		String userId = request.getSession().getAttribute("taobao_user_nick").toString();
		//String userId= "crzzyboy";
		boolean flag = false;
		String settingType="29";
		UserOperationLog op = new UserOperationLog();
		if(orderAdvancedSetting.getId() != null && !"".equals(orderAdvancedSetting.getId())){
			try {
				orderAdvancedSetting.setLastModifiedDate(new Date());
				flag =orderAdvancedSettingService.updateBuyerRefundAvancedSetting(orderAdvancedSetting,request);
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
				op.setFunctions("买家申请退款高级设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderAdvancedSetting.getUserId());
				op.setFunctionGens("29");
				orderAdvancedSettingService.saveUserOperationLog(op);
			}
		}else{
			//用户Id
			orderAdvancedSetting.setUserId(userId);
			//设置类型
			orderAdvancedSetting.setSettingType("29");
			//修改时间
			orderAdvancedSetting.setCreatedDate(new Date());
			//保存高级设置
			try {
				flag = orderAdvancedSettingService.savebuyerAdvancedSetting(orderAdvancedSetting, request);
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
				op.setFunctions("买家申请退款高级设置保存");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderAdvancedSetting.getUserId());
				op.setFunctionGens("29");
				orderAdvancedSettingService.saveUserOperationLog(op);
			}
		}
		
		//基本设置查询
		OrderSetup buyerRefund = orderSetupXDGHService.findBuyerRefund(userId,request);
		model.addAttribute("buyerRefund", buyerRefund);
		
		//买家申请退款高级设置放入域
		if(flag){
			model.addAttribute("orderAdvancedSetting", orderAdvancedSetting);
			model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.success.msg"));
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.failure.msg"));
		    orderAdvancedSetting = orderAdvancedSettingService.findBuyerRefundAdvanced(userId,request);
			model.addAttribute("orderAdvancedSetting", orderAdvancedSetting);
		}
		//查询买家申请退款 短信设置
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		return "redirect:crms/refundCare/buyerRefund";
	}
	
	/**
	 * 短信设置
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/buyerRefundSmsSetting", method = RequestMethod.POST)
	public String savesmsSetting(ModelMap modelMap,SmsSetting smsSetting,HttpServletRequest request){
		boolean flag =false;
		String userId = request.getSession().getAttribute("taobao_user_nick").toString();
		//String userId= "crzzyboy";
		UserOperationLog op = new UserOperationLog();
		if(smsSetting.getId()!=null && !"".equals(smsSetting.getId())){
			try {
				String messageSignature = smsSetting.getMessageSignature();
				if(messageSignature!=null && messageSignature!=""){
					smsSetting.setLastModifiedDate(new Date());
					flag=smsSettingService.updateRefundBuyer(smsSetting, request);
					if(flag){
						op.setState("短信设置保存成功");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.success.msg"));
					}else{
						op.setState("短信设置保存失败");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.failure.msg"));
					}
				}else{
					op.setState("短信签名为空");
					modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.null.msg"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				op.setState("短信设置保存失败");
			}finally{
				op.setFunctions("买家申请退款短信设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(smsSetting.getUserId());
				op.setFunctionGens("29");
				smsSettingService.saveUserOperationLog(op);
			}
		}else{
			smsSetting.setUserId(userId);
			smsSetting.setStatus("1");
			smsSetting.setSettingType("29");
			smsSetting.setCreatedDate(new Date());
			try {
				flag=smsSettingService.saveBuyerrefund(smsSetting, request);
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
				//添加操作日志
				//操作日志数据补全
				op.setFunctions("买家申请退款短信设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(smsSetting.getUserId());
				op.setFunctionGens("29");
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
		return "redirect:crms/refundCare/buyerRefund";
		
	}
	
	
	@RequestMapping(value="/crms/refundCare/buyerRefund",method = RequestMethod.GET)
	public String buyerRefund(String msg,ModelMap model,HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId= "crzzyboy";
		String settingType="29";
		//查询买家申请退款基本设置
		OrderSetup buyerRefund = orderSetupXDGHService.findBuyerRefund(userId,request);
		model.addAttribute("buyerRefund", buyerRefund);
		
		//查询买家申请退款高级设置
		OrderAdvancedSetting orderAdvancedSetting = orderAdvancedSettingService.findBuyerRefundAdvanced(userId,request);
		model.addAttribute("orderAdvancedSetting", orderAdvancedSetting);
		//查询买家申请退款 短信设置
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		//查询设置状态显示顶部的流程图
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("status", "0");
		List<OrderSetup> statusList = orderSetupService.findOrderSetupOfStatus(map);
		model.addAttribute("statusList", statusList);
		model.addAttribute("smsSetting", smsSetting);
		
		if(msg!=null && msg!="" && msg!="null"){
			model.addAttribute("msg", msg);
		}
		return "crms/refundCare/buyerRefund";
	}
	
	
	/**
	 * 改变开启或关闭状态
	 * @throws IOException 
	 */
	@RequestMapping(value="/openTurnOffButton",method=RequestMethod.POST)
	public String  turnOffButton(String buttonId,String orderSetupId,HttpServletRequest request,HttpServletResponse response) throws IOException {
		OrderSetup orderSetup = new OrderSetup();
		orderSetup.setId(Long.parseLong(orderSetupId));
		orderSetup.setStatus(buttonId);
		
		
		
		//将封装好的json数据,输出到前台
		JSONObject  json = new JSONObject();
		
			//创建对象封装参数,输出到前台
			
			try {
				orderSetupXDGHService.updateBuyerRefundSetting(orderSetup, request);
				String sellerNick = (String) request.getSession().getAttribute("taobao_user_nick");
				this.userInfoService.addUserPermitByMySql(sellerNick, null);
				json.put("status", buttonId);
			} catch (JSONException e) {
				e.printStackTrace();
			}finally{
				response.setContentType("application/json; charset=utf-8");
				response.getWriter().write(json.toString());
			}
		return null;
	}

}
