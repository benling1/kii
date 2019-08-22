package s2jh.biz.shop.crm.order.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lab.s2jh.core.dao.mybatis.MyBatisDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.OrderAdvancedSettingService;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.order.service.ReminderNormalService;
import s2jh.biz.shop.crm.order.service.SmsSettingService;
import s2jh.biz.shop.crm.order.util.ResourceGetValueUtil;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.getIpAddress;

@Controller
public class ReminderAgainController {

	@Autowired
	private OrderSetupService orderSetupService;
	
	@Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
	
	@Autowired
	private ReminderNormalService reminderNormalService;
	
	@Autowired
	private SmsSettingService smsSettingService;
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	/**
	 * 二次催付提醒页面
	 */
	@RequestMapping("/crms/order/again")
	public String againIndex(){
		return "forward:/crms/order/queryAgainReminder";
	}
	
	/**
	 * 保存或更新基本设置
	 * @return
	 */
	@RequestMapping("/crms/order/again/setup")
	public String saveOrUpdateSetup(ModelMap model,HttpServletRequest request,OrderSetup orderSetup,
			@RequestParam(value="filtingConditions",defaultValue="6")String filtingConditions,
			@RequestParam(value="reminderTime",defaultValue="15分钟")String reminderTime,
			@RequestParam(value="startTime",defaultValue="8:00")String startTime,
			@RequestParam(value="endTime",defaultValue="22:00")String endTime){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		orderSetup.setFiltingConditions("1,2,3," + filtingConditions);
		orderSetup.setReminderTime(reminderTime);
		orderSetup.setStartTime(startTime);
		orderSetup.setEndTime(endTime);
		/*String userId = "crzzyboy";*/
		boolean  flag = false;
		if(userId!=null){
			String settingType = "3";
			orderSetup.setUserId(userId);
			orderSetup.setSettingType(settingType);
			//添加操作日志
			UserOperationLog op = new UserOperationLog(userId, userId, "二次催付基础设置", null, new Date(), null, getIpAddress.getIpAddress(request), "二次催付基础设置", settingType);
			
			Long orderSetupID = orderSetupService.findByUserAndSettiType(userId, settingType);
			if(orderSetupID != null && !"".equals(orderSetupID)){
				orderSetup.setId(orderSetupID);
				//操作日志数据补全
				op.setType("更新");
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
				op.setType("添加");
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
		return "forward:/crms/order/queryAgainReminder";
	}
	
	/**
	 * 保存或更新高级设置
	 * @return
	 */
	@RequestMapping("/crms/order/again/advance")
	public String saveOrUpdateAdvancedSetting(OrderAdvancedSetting orderAdvancedSetting,HttpServletRequest request, Model model){
		boolean flag = false;
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			try {
				String settingType = "3";
				//添加操作日志
				UserOperationLog op = new UserOperationLog(userId, userId, "二次催付高级设置", null, new Date(), null, getIpAddress.getIpAddress(request), "二次催付高级设置", settingType);
				orderAdvancedSetting.setSettingType(settingType);
				orderAdvancedSetting.setUserId(userId);
				Long userOrderAdvancedID = this.myBatisDao.findBy(OrderAdvancedSetting.class.getName(), "findAdvanceByUserAndSettiType", orderAdvancedSetting);
				//判断id是否为空
				if(userOrderAdvancedID != null && !"".equals(userOrderAdvancedID)){
					//不为空,修改
					orderAdvancedSetting.setId(userOrderAdvancedID);
					orderAdvancedSetting.setUserId(userId);
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
					//为空,添加
					orderAdvancedSetting.setUserId(userId);
					orderAdvancedSetting.setSettingType("3");
					//操作日志数据补全
					op.setType("添加");
					try {
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
		return "forward:/crms/order/queryAgainReminder";
	}
	
	/**
	 * 保存或更新短信设置
	 */
	@RequestMapping("/crms/again/sms")
	public String saveOrUpdateSms(ModelMap modelMap,HttpServletRequest request,SmsSetting smsSetting,ModelMap model){
		//补全属性
		boolean flag = false;
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			//添加操作日志
			UserOperationLog op = new UserOperationLog(userId, userId, "二次催付短信设置", null, new Date(), null, getIpAddress.getIpAddress(request), "二次催付短信设置", "3");
			smsSetting.setUserId(userId);
			smsSetting.setSettingType("3");
			if(smsSetting.getId() != null && !"".equals(smsSetting.getId())){
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
		return "forward:/crms/order/queryAgainReminder";
	}
	
	/**
	 * 查询基本设置、高级设置和短信设置回显至页面
	 */
	@RequestMapping("/crms/order/queryAgainReminder")
	public String querySetting(String msg,HttpServletRequest request, ModelMap model){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		/*String userId = "crzzyboy";*/
		String settingType = "3";
		//需求:查询基本信息参数:卖家id: userId 操作类型:settingType
		OrderSetup orderSetupAgain = orderSetupService.findOrderSetupByUserIdAndSettingType(userId,settingType);
		//需求:查询高级信息
		OrderAdvancedSetting orderAdvancedSettingAgain = orderAdvancedSettingService.findOrderAdvancedSettingByUserIdAndSettingType(userId,settingType);
		//需求:查询 短信设置
		SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
		if(orderSetupAgain != null && !"".equals(orderSetupAgain)){
			if(orderSetupAgain.getFiltingConditions() != null && !"".equals(orderSetupAgain.getFiltingConditions())){
				if(orderSetupAgain.getFiltingConditions().length() > 6){
					String filterSub = orderSetupAgain.getFiltingConditions().substring(6, orderSetupAgain.getFiltingConditions().length());
					orderSetupAgain.setFiltingConditions(filterSub);
				}
			}else{
				orderSetupAgain.setFiltingConditions("6");
			}
		}
		model.addAttribute("orderSetupAgain", orderSetupAgain);
		model.addAttribute("orderAdvancedSettingAgain", orderAdvancedSettingAgain);
		model.addAttribute("smsSetting", smsSetting);
		if(msg!=null && msg !=""&& msg != "null"){
			model.addAttribute("msg", msg);
		}
		return "/crms/orderCenterZ/ercicuifu";
	}
}
