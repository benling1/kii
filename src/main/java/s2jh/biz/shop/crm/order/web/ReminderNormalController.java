package s2jh.biz.shop.crm.order.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.message.service.SmsTemplateService;
import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.OrderAdvancedSettingService;
import s2jh.biz.shop.crm.order.service.OrderRateCareSetupService;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.order.service.ReminderNormalService;
import s2jh.biz.shop.crm.order.service.SmsSettingService;
import s2jh.biz.shop.crm.order.util.ResourceGetValueUtil;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.getIpAddress;

/**
 * 常规催付的Controller
 * @author Administrator
 *
 */
@Controller
public class ReminderNormalController {
	
	@Autowired
	private OrderSetupService orderSetupService;
	
	@Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
	
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private ReminderNormalService reminderNormalService;
	@Autowired
	private SmsSettingService smsSettingService;
	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private OrderRateCareSetupService orderRateCareSetupService;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	/**
	 * 常规催付提醒页面
	 */
	@RequestMapping("/crms/order/normal")
	public String normalIndex(){
		return "forward:/crms/order/queryReminder";
	}
	
	/**
	 * 查询基本设置、高级信息和短信设置
	 */
	@RequestMapping(value = "/crms/order/queryReminder" )
	public String queryOrderSetupAndOrderAdvancedSetting(HttpServletRequest request, ModelMap model){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			/*String userId = "crzzyboy";*/
			String settingType = "2";
			//需求:查询基本信息参数:卖家id: userId 操作类型:settingType
			OrderSetup orderSetupNormal = orderSetupService.findOrderSetupByUserIdAndSettingType(userId,settingType);
			if(orderSetupNormal != null && !"".equals(orderSetupNormal)){
				if(orderSetupNormal.getFiltingConditions() == null || "".equals(orderSetupNormal.getFiltingConditions())){
					orderSetupNormal.setFiltingConditions("6");
				}else{
					if(orderSetupNormal.getFiltingConditions().length() > 6){
						String filterSub = orderSetupNormal.getFiltingConditions().substring(6, orderSetupNormal.getFiltingConditions().length());
						orderSetupNormal.setFiltingConditions(filterSub);
					}
				}
			}
			//需求:查询高级信息
			OrderAdvancedSetting orderAdvancedSettingNormal = orderAdvancedSettingService.findOrderAdvancedSettingByUserIdAndSettingType(userId,settingType);
			//需求:查询 短信设置
			SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
			//查询设置状态显示顶部的流程图
			
			//1、使用JSONObject
	        //2、使用JSONArray
	       /* JSONArray listArray=JSONArray.fromObject(statusList);*/
			model.addAttribute("orderSetupNormal", orderSetupNormal);
			model.addAttribute("orderAdvancedSettingNormal", orderAdvancedSettingNormal);
			model.addAttribute("smsSetting", smsSetting);
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		return "/crms/orderCenterZ/changguicuifu";
	}
	/**
	 * 查询订单中心的流程图
	 * ZTK2017年4月13日下午3:37:21
	 */
	@RequestMapping("/getStatusList")
	@ResponseBody
	public Map<String,Object> getStatusList(HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//orderSetup设置为开启的类型
		List<String> settingTypeList = orderSetupService.findOrderSetupOfStatus(userId);
		//orderRateCareSetup设置为开启的类型(自动评价，中差评监控，中差评安抚)
		List<String> rateCareSetupList = orderRateCareSetupService.orderRateCareSetupIsOpen(userId);
		resultMap.put("rateCareSetupList", rateCareSetupList);
		resultMap.put("settingTypeList", settingTypeList);
		return resultMap;
	}
	/**
	 * 保存或更新基本设置
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/crms/order/normal/setup")
	public String saveSetup(HttpServletRequest request,OrderSetup orderSetup,ModelMap model,
			@RequestParam(value="filtingConditions",defaultValue="6")String filtingConditions,
			@RequestParam(value="reminderTime",defaultValue="15分钟")String reminderTime,
			@RequestParam(value="startTime",defaultValue="8:00")String startTime,
			@RequestParam(value="endTime",defaultValue="22:00")String endTime){
		boolean  flag = false;
		orderSetup.setFiltingConditions("1,2,3," + filtingConditions);
		orderSetup.setReminderTime(reminderTime);
		orderSetup.setStartTime(startTime);
		orderSetup.setEndTime(endTime);
		String settingType = "2";
		/*String userId = "crzzyboy";*/
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			SimpleDateFormat format = new SimpleDateFormat();
			orderSetup.setUserId(userId);
			orderSetup.setSettingType(settingType);
			//设置页面回显
			/*model.addAttribute("orderSetup", orderSetup);*/
			Long orderSetupID = orderSetupService.findByUserAndSettiType(userId, settingType);
			if(orderSetupID != null && !"".equals(orderSetupID)){
				orderSetup.setId(Long.parseLong(orderSetupID+""));
				/*
				 * 更新基本信息
				 */
				//添加操作日志
				UserOperationLog op = new UserOperationLog(userId, userId, "常规催付基础设置", "更新", new Date(), null, getIpAddress.getIpAddress(request), "常规催付基础设置", "2");
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
				orderSetupService.saveUserOperationLog(op);
			}else{
				//添加操作日志
				UserOperationLog op = new UserOperationLog(userId, userId, "常规催付基础设置", "添加", new Date(), null, getIpAddress.getIpAddress(request), "常规催付基础设置", "2");
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
				orderSetupService.saveUserOperationLog(op);
			}
			model.addAttribute("msg", flag?ResourceGetValueUtil.getValue("order.setup.save.success.msg"):ResourceGetValueUtil.getValue("order.setup.save.failure.msg"));
			if(orderSetup.getFiltingConditions() != null && !"".equals(orderSetup.getFiltingConditions())){
				if(orderSetup.getFiltingConditions().length() > 6){
					String filterSub = orderSetup.getFiltingConditions().substring(6, orderSetup.getFiltingConditions().length());
					orderSetup.setFiltingConditions(filterSub);
				}
			}else{
				orderSetup.setFiltingConditions("6");
			}
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		return "forward:/crms/order/queryReminder";
	}
	/**
	 * 根据id更新基本设置状态
	 * ZTK2017年1月10日上午11:54:32
	 */
	@RequestMapping(value="/crms/updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStatusById(HttpServletRequest request,HttpServletResponse response,String id,String status) throws IOException{
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		map.put("lastModifiedBy", userId);
		map.put("lastModifiedDate", new Date());
		map.put("userId", userId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean updateMsg = orderSetupService.updateStatusById(map);
		String sellerNick = (String) request.getSession().getAttribute("taobao_user_nick");
		this.userInfoService.addUserPermitByMySql(sellerNick, null);
		resultMap.put("message", updateMsg);
		return resultMap;
	}
	
	/**
	 * 添加或修改高级设置 
	 */
	@RequestMapping("/crms/order/normal/advance")
	public String saveOrUpdateOrderAdvancedSetting(OrderAdvancedSetting orderAdvancedSetting,HttpServletRequest request, Model model){
		boolean flag = false;
		String settingType = "2";
		//.卖家id
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			/*String userId = "crzzyboy";*/
			//不为空,修改
			orderAdvancedSetting.setUserId(userId);
			orderAdvancedSetting.setSettingType(settingType);
			//添加操作日志
			UserOperationLog op = new UserOperationLog(userId, userId, "常规催付高级设置", null, new Date(), null, getIpAddress.getIpAddress(request), "常规催付高级设置", "2");
			try {
				Long userOrderAdvancedID = this.myBatisDao.findBy(OrderAdvancedSetting.class.getName(), "findAdvanceByUserAndSettiType", orderAdvancedSetting);
				//判断id是否为空
				if(userOrderAdvancedID != null && !"".equals(userOrderAdvancedID)){
					orderAdvancedSetting.setId(userOrderAdvancedID);
					//操作日志数据补全
					op.setType("更新");
					try {
						orderAdvancedSetting.setLastModifiedBy(userId);
						orderAdvancedSetting.setLastModifiedDate(new Date());
						flag = orderAdvancedSettingService.updateOrderAdvancedSetting(orderAdvancedSetting);
						if(flag){
							op.setState("高级设置保存成功");
						}else{
							op.setState("高级设置保存失败");
						}
					} catch (Exception e) {
						e.printStackTrace();
						op.setState("失败");
					}
				}else{
					//操作日志数据补全
					op.setType("添加");
					try {
						orderAdvancedSetting.setLastModifiedBy(userId);
						orderAdvancedSetting.setLastModifiedDate(new Date());
						flag = orderAdvancedSettingService.saveOrderAdvancedSetting(orderAdvancedSetting);
						if(flag){
							op.setState("高级设置保存成功");
						}else{
							op.setState("高级设置保存失败");
						}
					} catch (Exception e) {
						e.printStackTrace();
						op.setState("失败");
					}
				}
				reminderNormalService.saveUserOperationLog(op);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(flag){
				model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.success.msg"));
			}else{
				model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.failure.msg"));
			}
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		
		return "forward:/crms/order/queryReminder";
	}
	
	
	/**
	 * 查询短信模板
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/crms/smsTemplate")
	@ResponseBody
	public Map<String, Object> findSmsTemplate(HttpServletRequest request,HttpServletResponse response,String type,Integer pageNo) throws IOException{
		String contextPath = request.getContextPath();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if("自定义".equals(type)){
			map.put("userId", userId);
			map.put("type", type);
			List<SmsTemplate> templateList = smsTemplateService.findAllSmsTemplates(map);
			resultMap.put("smsTemplate", templateList);
		}else{
			map.put("type", type);
			List<SmsTemplate> templateList = smsTemplateService.findAllSmsTemplates(map);
			resultMap.put("smsTemplate", templateList);
		}
		return resultMap;
	}
	
	/**
	 * 保存短信模板(设置类型为自定义)
	 */
	@RequestMapping("/crms/saveSmsTemplate")
	@ResponseBody
	public Map<String,Object> saveSmsTemplate(HttpServletRequest request,HttpServletResponse response,String smsName,String smsContent){
		Map<String,Object> resultMap = new HashMap<String, Object>();
			String userId = (String) request.getSession().getAttribute("taobao_user_nick");
			/*String userId = "crzzyboy";*/
			SmsTemplate smsTemplate = new SmsTemplate();
			smsTemplate.setContent(smsContent);
			smsTemplate.setName(smsName);
			smsTemplate.setCreatedDate(new Date());
			smsTemplate.setUserNick(userId);
			smsTemplate.setType("自定义");
			smsTemplate.setLastModifiedDate(new Date());
			smsTemplate.setLastModifiedBy(userId);
			//操作日志
			UserOperationLog log = new UserOperationLog(userId, userId, "添加自定义短信模板", "添加", new Date(), null, getIpAddress.getIpAddress(request), "添加模板", "2");
			smsTemplateService.saveSmsTemplate(log, smsTemplate);
			resultMap.put("message", true);
			return resultMap;
	}
	/**
	 * 保存或更新短信设置
	 * @param request
	 * @param smsSetting
	 * @param model
	 * @return
	 */
	@RequestMapping("/crms/normal/sms")
	public String saveOrUpdateMessage(ModelMap modelMap,HttpServletRequest request,SmsSetting smsSetting,ModelMap model){
		//补全属性
		boolean flag = false;
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			/*String userId = "crzzyboy";*/
			smsSetting.setUserId(userId);
			smsSetting.setSettingType("2");
			//添加操作日志
			UserOperationLog op = new UserOperationLog(userId, userId, "常规催付短信设置", null, new Date(), null, getIpAddress.getIpAddress(request), "常规催付短信设置", "2");
			Long smsSettingID = this.myBatisDao.findBy(SmsSetting.class.getName(), "findIdBySmsSetting", smsSetting);
			//判断id是否为空
			if(smsSettingID != null && !"".equals(smsSettingID)){
				smsSetting.setId(smsSettingID);
				//操作日志数据补全
				op.setType("更新");
				try {
					smsSetting.setLastModifiedBy(userId);
					smsSetting.setLastModifiedDate(new Date());
					flag = smsSettingService.updateMessageSetting(smsSetting);
					if(flag){
						op.setState("成功");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.success.msg"));
					}else{
						op.setState("失败");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.failure.msg"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					op.setState("失败");
				}
			}else{
				//操作日志数据补全
				op.setType("添加");
				try {
					smsSetting.setLastModifiedBy(userId);
					smsSetting.setLastModifiedDate(new Date());
					flag = smsSettingService.saveMessageSetting(smsSetting);
					if(flag){
						op.setState("成功");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.success.msg"));
					}else{
						op.setState("失败");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.failure.msg"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					op.setState("失败");
				}
			}
			reminderNormalService.saveUserOperationLog(op);
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
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
		return "forward:/crms/order/queryReminder";
	}
	
	/**
	 * 根据用户名称和类型查询设置是否开启
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/getOrderSetupInfo")
	public String getOrderSetupInfo(HttpServletRequest request,HttpServletResponse response,String type) throws IOException{
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//创建对象封装参数,输出到前台
		JSONObject  json = new JSONObject();
		try {
			OrderSetup os = judgeUserUtil.isOrderSetupOpen(userId,type);
			if(os!=null){
				if(!"0".equals(os.getStatus())){
					json.put("sussce", false);
				}else{
					json.put("sussce", true);
				}
			}else{
				json.put("sussce", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=utf-8");  
			response.getWriter().write(json.toString());
		}
		return null;
	}
}
