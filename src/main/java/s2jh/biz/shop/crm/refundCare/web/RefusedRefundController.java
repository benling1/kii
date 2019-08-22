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
public class RefusedRefundController {
	
	@Autowired
	private OrderSetupService orderSetupService;
	
	@Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
	
	@Autowired
	private SmsSettingService smsSettingService;
	
	@Autowired
	private UserInfoService userInfoService;
	/**
	 * 拒绝退款基本设置
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/refusedRefund", method = RequestMethod.POST)
	public String saveRefusedrefund(ModelMap model,OrderSetup orderSetup,HttpServletRequest request,
			@RequestParam(value="startTime",defaultValue="8:00")String startTime,
			@RequestParam(value="endTime",defaultValue="22:00")String endTime){
		orderSetup.setStartTime(startTime);
		orderSetup.setEndTime(endTime);
		String userId= request.getSession().getAttribute("taobao_user_nick").toString();
		//String userId="crzzyboy";
		boolean flag = false;
		String settingType="32";
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		if(orderSetup.getId()!=null && !"".equals(orderSetup.getId())){
			try {
				orderSetup.setLastModifiedDate(new Date());
				flag = orderSetupService.updaterefusedRefund(orderSetup, request);
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
				op.setFunctions("等待退货基本设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens("32");
				orderSetupService.saveUserOperationLog(op);
			}
		}else{
			//用户Id
			orderSetup.setUserId(userId);
			//创建时间
			orderSetup.setCreatedDate(new Date());
			//设置类型
			orderSetup.setSettingType("32");
			//默认设置为关闭
			orderSetup.setStatus("1");
			//设置时间过滤条件
			orderSetup.setFiltingConditions("1,3");
			try {
				flag = orderSetupService.saverefusedRefund(orderSetup, request);
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
				op.setFunctions("拒绝退款基本设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens("32");
				orderSetupService.saveUserOperationLog(op);
			}
		}
		
		//基本设置
		OrderSetup refusedRefund = orderSetupService.findrefusedRefund(userId,request);
		model.addAttribute("refusedRefund", refusedRefund);
		
		model.addAttribute("msg", flag?ResourceGetValueUtil.getValue("order.setup.save.success.msg"):ResourceGetValueUtil.getValue("order.setup.save.failure.msg"));
		
		//高级设置
		OrderAdvancedSetting refusedRefundGj = orderAdvancedSettingService.findRefusedRefund(userId,request);
		model.addAttribute("refusedRefundGj", refusedRefundGj);
		
		//短信设置查询
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		return "crms/refundCare/refusedRefund";
	}
	
	/**
	 * 拒绝退款高级设置
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/refusedRefundGj", method = RequestMethod.POST)
	public String saverefusedRefund(ModelMap model,OrderAdvancedSetting orderAdvancedSetting3,HttpServletRequest request){
		
		String userId= request.getSession().getAttribute("taobao_user_nick").toString();
		//String userId="crzzyboy";
		boolean flag = false;
		String settingType="32";
		UserOperationLog op = new UserOperationLog();
		if(orderAdvancedSetting3.getId() != null && !"".equals(orderAdvancedSetting3.getId())){
			try {
				orderAdvancedSetting3.setLastModifiedDate(new Date());
				flag=orderAdvancedSettingService.updateRefusedrefundGj(orderAdvancedSetting3, request);
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
				op.setFunctions("拒绝退款高级设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderAdvancedSetting3.getUserId());
				op.setFunctionGens("32");
				orderAdvancedSettingService.saveUserOperationLog(op);
			}
		}else{
			//用户Id
			orderAdvancedSetting3.setUserId(userId);
			//创建时间
			orderAdvancedSetting3.setCreatedDate(new Date());
			//设置类型
			orderAdvancedSetting3.setSettingType("32");
			//默认设置定时发送为关闭
			orderAdvancedSetting3.setStatus("1");
			try {
				flag = orderAdvancedSettingService.saverefusedRefund(orderAdvancedSetting3, request);
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
				op.setFunctions("拒绝退款高级设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderAdvancedSetting3.getUserId());
				op.setFunctionGens("32");
				orderAdvancedSettingService.saveUserOperationLog(op);
			}
		}
		
		//基本设置
		OrderSetup refusedRefund = orderSetupService.findrefusedRefund(userId,request);
		model.addAttribute("refusedRefund", refusedRefund);
		
		//买家申请退款高级设置放入域
		if(flag){
			model.addAttribute("orderAdvancedSetting3", orderAdvancedSetting3);
			model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.success.msg"));
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.failure.msg"));
			orderAdvancedSetting3 = orderAdvancedSettingService.findBuyerRefundAdvanced(userId,request);
			model.addAttribute("orderAdvancedSetting3", orderAdvancedSetting3);
		}
		
		//短信设置查询
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		
		return "redirect:crms/refundCare/refusedRefund";
	}
	
	/**
	 * 拒绝退款短信设置
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/saverefusedRefundSmsSetting",method = RequestMethod.POST)
	public String saverefundseRefundSmsSetting(ModelMap modelMap,SmsSetting smsSetting,HttpServletRequest request){
		
		String userId= request.getSession().getAttribute("taobao_user_nick").toString();
		//String userId="crzzyboy";
		boolean flag=false;
		UserOperationLog op = new UserOperationLog();
		if(smsSetting.getId() != null && !"".equals(smsSetting.getId())){
			try {
				String messageSignature = smsSetting.getMessageSignature();
				if(messageSignature!=null && messageSignature!=""){
					smsSetting.setLastModifiedDate(new Date());
					flag = smsSettingService.updateRefusedSmsSetting(smsSetting, request);
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
				op.setFunctions("拒绝退款短信设置修改");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(smsSetting.getUserId());
				op.setFunctionGens("32");
				smsSettingService.saveUserOperationLog(op);
			}
		}else{
			smsSetting.setUserId(userId);
			smsSetting.setCreatedDate(new Date());
			smsSetting.setSettingType("32");
			smsSetting.setStatus("1");
			try {
				flag=smsSettingService.saveRefusedRefundSmsSetting(smsSetting,request);
				if(flag){
					op.setState("短息设置保存成功");	
					modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.success.msg"));
				}else{
					op.setState("短息设置保存失败");	
					modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.failure.msg"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				op.setState("短息设置保存失败");
			}finally{
				//操作日志数据补全
				op.setFunctions("拒绝退款短信设置添加");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("退款关怀");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(smsSetting.getUserId());
				op.setFunctionGens("32");
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
		return "redirect:crms/refundCare/refusedRefund";
	}
	
	
	@RequestMapping(value="/crms/refundCare/refusedRefund",method = RequestMethod.GET)
	public String refusedRefund(String msg,ModelMap model,HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId="crzzyboy";
		String settingType="32";
		//基本设置
		OrderSetup refusedRefund = orderSetupService.findrefusedRefund(userId,request);
		model.addAttribute("refusedRefund", refusedRefund);
		
		//高级设置
		OrderAdvancedSetting refusedRefundGj = orderAdvancedSettingService.findRefusedRefund(userId,request);
		model.addAttribute("refusedRefundGj", refusedRefundGj);
		
		//短信设置查询
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		model.addAttribute("smsSetting", smsSetting);
		
		if(msg!=null && msg!="" && msg!="null"){
			model.addAttribute("msg", msg);
		}
		
		return "crms/refundCare/refusedRefund";
	}
	
	/**
	 * 拒绝退款 开启关闭
	 * @throws IOException 
	 */
	@RequestMapping(value="/refusedRefundOpen",method=RequestMethod.POST)
	public String refusedRefundOpen(String buttonId,String orderSetupId,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		OrderSetup orderSetup = new OrderSetup();
		orderSetup.setId(Long.parseLong(orderSetupId));
		orderSetup.setStatus(buttonId);
		
		
		//创建对象封装参数,输出到前台
		JSONObject  json = new JSONObject();
			try {
				orderSetupService.updaterefusedRefund(orderSetup, request);
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
