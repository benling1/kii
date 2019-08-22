package s2jh.biz.shop.crm.order.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.message.service.SmsSettingWLTXService;
import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.OrderAdvancedSettingService;
import s2jh.biz.shop.crm.order.service.OrderAdvancedSettingWLTXService;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.order.service.OrderSetupWLTXService;
import s2jh.biz.shop.crm.order.service.ReminderNormalService;
import s2jh.biz.shop.crm.order.service.SmsSettingService;
import s2jh.biz.shop.crm.order.util.ResourceGetValueUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.getIpAddress;

/**
* @ClassName: LogisticsRemindController
* @Description: TODO(订单中心>>物流提醒)
* @author:jackstraw_yu
* @date 2016年12月7日
*
*/
@Controller
@RequestMapping(value="/logisticsRemind")
public class LogisticsRemindController {

	@Autowired
	private OrderAdvancedSettingWLTXService orderAdvancedSettingWLTXService;;
	@Autowired
	private OrderSetupWLTXService orderSetupWLTXService;
	@Autowired
	private OrderSetupService orderSetupService;
	@Autowired
	private ReminderNormalService reminderNormalService;
	@Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SmsSettingWLTXService smsSettingWLTXService;
	
	@Autowired
	private SmsSettingService smsSettingService;
	/**
	* @Title: queryAllSettings
	* @Description: TODO(订单中心>>物流提醒页面跳转)
	* @param @return    参数
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/queryAllSettings")
	public String queryAllSettings(String msg,Model model,HttpServletRequest request){
		
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			//String userId="crazyboy";
			String settingType  = "6";
			//基本设置
			OrderSetup orderSetup = new OrderSetup();
			orderSetup.setSettingType(settingType);
			orderSetup.setUserId(userId);
			//高级设置
			OrderAdvancedSetting orderAdvancedSetting = new OrderAdvancedSetting();
			orderAdvancedSetting.setSettingType("6");
			orderAdvancedSetting.setUserId(userId);
			
			
			orderSetup = orderSetupService.findOrderSetupByUserIdAndSettingType(userId,settingType);
			orderAdvancedSetting = orderAdvancedSettingService.findOrderAdvancedSettingByUserIdAndSettingType(userId,settingType);
			model.addAttribute("orderSetup", orderSetup);
			model.addAttribute("orderAdvancedSetting", orderAdvancedSetting);
			//短信设置
			SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, "6");
			model.addAttribute("smsSetting",smsSetting);
		
			if(orderSetup !=null && "0".equals(orderSetup.getStatus())){
				//开启
				model.addAttribute("show",0);
			}
			else if(orderSetup !=null && ("1".equals(orderSetup.getStatus()) || orderSetup.getStatus() ==null)){
				//关闭
				model.addAttribute("show",1);
			}
			if(msg!=null && msg !=""&& msg != "null"){
				model.addAttribute("msg", msg);
			}
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		return "crms/order/logisticsRemind";
	}
	
	
	/**	
	* @Title: saveOrderSetup
	* @Description: TODO(订单中心>>物流提醒保存基本设置)
	* @param @return    参数
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/saveOrderSetup")
	public String saveOrderSetup(ModelMap model,OrderSetup orderSetup,HttpServletRequest request,
			@RequestParam(value="filtingConditions",defaultValue="6")String filtingConditions,
			@RequestParam(value="startTime",defaultValue="8:00")String startTime,
			@RequestParam(value="endTime",defaultValue="22:00")String endTime){
		orderSetup.setStartTime(startTime);
		orderSetup.setEndTime(endTime);
		orderSetup.setFiltingConditions(filtingConditions);
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			//String userId ="crazyboy";
			boolean  flag = false;
			String settingType = "6";
			//基本设置类型>>发货提醒
			orderSetup.setSettingType(settingType);
			orderSetup.setUserId(userId);
			
			//添加操作日志
			UserOperationLog op = new UserOperationLog();
			//设置页面回显
			model.addAttribute("orderSetup", orderSetup);
			if(orderSetup.getId() != null && !"".equals(orderSetup.getId())){
				//操作日志数据补全
				op.setFunctions("二次催付基础设置");
				op.setType("更新");
				op.setDate(new Date());
				op.setRemark("二次催付基础设置");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens(settingType);
				try {
					orderSetup.setLastModifiedDate(new Date());
					orderSetup.setLastModifiedBy(userId);
					flag = orderSetupService.updateOrderSetup(orderSetup);
					if(flag){
						op.setState("基本设置保存成功");
					}else{
						op.setState("基本设置保存失败");
					}
				} catch (Exception e) {
					e.printStackTrace();
					op.setState("失败");
				}
			}else{
				//操作日志数据补全
				op.setFunctions("二次催付基础设置");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("二次催付基础设置");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens(settingType);
				
				//默认设置定时发送为关闭
				orderSetup.setStatus("1");
				try {
					flag = orderSetupService.saveOrderSetup(orderSetup);
					if(flag){
						op.setState("基本设置保存成功");
					}else{
						op.setState("基本设置保存失败");
					}
				} catch (Exception e) {
					e.printStackTrace();
					op.setState("失败");
				}
			}
			reminderNormalService.saveUserOperationLog(op);
			model.addAttribute("msg", flag?ResourceGetValueUtil.getValue("order.setup.save.success.msg"):ResourceGetValueUtil.getValue("order.setup.save.failure.msg"));
				
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		//重定向到查询链接
		return "redirect:/logisticsRemind/queryAllSettings";
	}
	
	
	/**
	* @Title: saveAdvancedSetting
	* @Description: TODO(修改高级设置)
	* @param @param orderAdvancedSetting
	* @param @param response    参数
	* @return void    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveAdvancedSetting")
	public void saveAdvancedSetting( OrderAdvancedSetting orderAdvancedSetting,HttpServletRequest request,HttpServletResponse response){
		
		/*String locality,String vendormark,String orderSource,
		 *String memberLevel,String productSelect,String ids
		 * 地区筛选,
		 * 卖家标记,
		 * 订单来源,
		 * 会员等级,
		 * 排除/指定商品
		 * 商品id
		 * **/
		
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		JSONObject js = new JSONObject();
		if(userId!=null){
			orderAdvancedSetting.setSettingType("6");
			orderAdvancedSetting.setUserId(userId);
			//优化内容
			if(null!=orderAdvancedSetting.getLocality()){
				orderAdvancedSetting.setLocality(stringUtils(orderAdvancedSetting.getLocality()));
			}
			orderAdvancedSetting.setFlagcolor(stringUtils(orderAdvancedSetting.getFlagcolor()));
			
			//添加操作日志
			UserOperationLog op = new UserOperationLog();
			op.setIpAdd(getIpAddress.getIpAddress(request));
			op.setUserId(orderAdvancedSetting.getUserId());
			op.setDate(new Date());
			try {
				//service层添加或修改高级设置
				orderAdvancedSettingWLTXService.saveOrderAdvancedSetting(orderAdvancedSetting,op);
				js.put("message", true);
			} catch (Exception e) {
					js.put("message", false);
			}
		}else{
			js.put("message", false);
		}
		//String userId ="crazyboy";
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	* @Title: saveSmsSetting
	* @Description: TODO(短信发送设置)
	* @param @param orderAdvancedSetting
	* @param @param response    参数
	* @return void    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveSmsSetting")
	public void saveSmsSetting(SmsSetting smsSetting,HttpServletRequest request,HttpServletResponse response){
		
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		JSONObject js = new JSONObject();
		if(userId!=null){
			smsSetting.setSettingType("6");
			smsSetting.setUserId(userId);
			//优化内容
			smsSetting.setMessageContent(smsSetting.getMessageContent().trim());
			
			//添加操作日志
			UserOperationLog op = new UserOperationLog();
			op.setIpAdd(getIpAddress.getIpAddress(request));
			op.setUserId(smsSetting.getUserId());
			op.setDate(new Date());
			try {
				//service层添加或修改高级设置
				smsSettingWLTXService.saveSmsSetting(smsSetting,op);
				js.put("message", true);
			} catch (Exception e) {
				js.put("message", false);
			}
		}else{
			js.put("message", false);
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
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	* @Title: updateStatus
	* @Description: TODO(开启或关闭物流提醒)
	* @param @param status
	* @param @param request
	* @param @param response    参数
	* @return void    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/updateStatus")
	public void updateStatus(String status,HttpServletRequest request,HttpServletResponse response){
		
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId ="crazyboy";
		
		OrderSetup orderSetup = new OrderSetup();
		orderSetup.setSettingType("6");
		orderSetup.setUserId(userId);
		orderSetup.setStatus(status);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderSetup.getUserId());
		op.setDate(new Date());
		op.setOperator(orderSetup.getUserId());
		
		JSONObject js = new JSONObject();
		try {
			//service层添加或修改高级设置
			orderSetupWLTXService.updateStatus(orderSetup,op);
			String sellerNick = (String) request.getSession().getAttribute("taobao_user_nick");
			this.userInfoService.addUserPermitByMySql(sellerNick, null);
			js.put("message", true);
		} catch (Exception e) {
				js.put("message", false);
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	* @Title: stringUtils
	* @Description: TODO(定义一个工具类用于切割字符串并整理)
	* @param @param str
	* @param @return    参数
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public String stringUtils(String str){
		if(ValidateUtil.isEmpty(str)){
			return null;
		}
		if(str.startsWith(",")){
			str = str.substring(1, str.length());
		}
		String content = "";
		if(str.contains(",") || str.contains("")){
			String[] split = str.split("[,|，]");
			for(int i= 0;i<split.length;i++){
				if(i==0){
					content = split[i];
					}else{
						content += ","+split[i];
					}
			}
		}
		
		return content;
	}
}
