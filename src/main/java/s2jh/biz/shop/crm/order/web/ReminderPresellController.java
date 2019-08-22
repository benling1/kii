package s2jh.biz.shop.crm.order.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import s2jh.biz.shop.utils.getIpAddress;

@Controller
public class ReminderPresellController {

	@Autowired
	private OrderSetupService orderSetupService;
	
	@Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private ReminderNormalService reminderNormalService;
	
	@Autowired
	private SmsSettingService smsSettingService;
	
	
	/**
	 * 预售催付提醒页面
	 */
	@RequestMapping("/crms/order/presell")
	public String presellIndex(){
		return "forward:/crms/order/queryPresellReminder";
	}
	/**
	 * 保存或更新基本设置
	 * @return
	 */
	@RequestMapping("/crms/order/presell/setup")
	public String saveOrUpdateSetup(HttpServletRequest request,OrderSetup orderSetup,ModelMap model,
			@RequestParam(value="filtingConditions",defaultValue="6")String filtingConditions,
			@RequestParam(value="reminderTime",defaultValue="15分钟")String reminderTime){
		//.卖家id
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		orderSetup.setFiltingConditions("1,2,3," + filtingConditions);
		orderSetup.setReminderTime(reminderTime);
		if(userId!=null){
			//添加操作日志
			UserOperationLog op = new UserOperationLog();
			orderSetup.setUserId(userId);
			orderSetup.setSettingType("5");
			//设置页面回显
			model.addAttribute("orderSetup", orderSetup);
			Long orderSetupID = orderSetupService.findByUserAndSettiType(userId, orderSetup.getSettingType());
			if(orderSetupID != null && !"".equals(orderSetupID)){
				orderSetup.setId(Long.parseLong(orderSetupID+""));
				//操作日志数据补全
				op.setFunctions("预售催付基础设置");
				op.setType("更新");
				op.setDate(new Date());
				op.setRemark("预售催付基础设置");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(userId);
				op.setFunctionGens("5");
				try {
					orderSetup.setLastModifiedBy(userId);
					orderSetup.setLastModifiedDate(new Date());
					reminderNormalService.updateOrderSetup(orderSetup);
					op.setState("成功");
				} catch (Exception e) {
					e.printStackTrace();
					op.setState("失败");
				}
			}else{
				//操作日志数据补全
				op.setFunctions("预售催付基础设置");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("预售催付基础设置");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(orderSetup.getUserId());
				op.setFunctionGens("5");
				try {
					orderSetup.setLastModifiedBy(userId);
					orderSetup.setLastModifiedDate(new Date());
					reminderNormalService.saveOrderSetup(orderSetup);
					op.setState("成功");
				} catch (Exception e) {
					e.printStackTrace();
					op.setState("失败");
				}
			}
			reminderNormalService.saveUserOperationLog(op);
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		return "forward:/crms/order/queryPresellReminder";
	}
	
	/**
	 * 保存或更新高级设置
	 * @return
	 */
	@RequestMapping("/crms/order/presell/advance")
	public String saveOrUpdateAdvancedSetting(OrderAdvancedSetting orderAdvancedSetting,HttpServletRequest request, Model model){
		try {
			String userId = (String) request.getSession().getAttribute("taobao_user_nick");
			if(userId!=null){
				orderAdvancedSetting.setUserId(userId);
				orderAdvancedSetting.setSettingType("5");
				//添加操作日志
				UserOperationLog op = new UserOperationLog();
				Long userOrderAdvancedID = this.myBatisDao.findBy(OrderAdvancedSetting.class.getName(), "findAdvanceByUserAndSettiType", orderAdvancedSetting);
				//判断id是否为空
				if(userOrderAdvancedID != null && !"".equals(userOrderAdvancedID)){
					//不为空,修改
					//操作日志数据补全
					orderAdvancedSetting.setId(userOrderAdvancedID);
					op.setFunctions("预售催付高级设置");
					op.setType("更新");
					op.setDate(new Date());
					op.setRemark("预售催付高级设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(orderAdvancedSetting.getUserId());
					op.setFunctionGens("5");
					try {
						orderAdvancedSetting.setLastModifiedBy(userId);
						orderAdvancedSetting.setLastModifiedDate(new Date());
						reminderNormalService.updateOrderAdvanceSetting(orderAdvancedSetting);
						op.setState("成功");
					} catch (Exception e) {
						e.printStackTrace();
						op.setState("失败");
					}
				}else{
					//为空,添加
					//操作日志数据补全
					op.setFunctions("预售催付高级设置");
					op.setType("添加");
					op.setDate(new Date());
					op.setRemark("预售催付高级设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(orderAdvancedSetting.getUserId());
					op.setFunctionGens("5");
					try {
						orderAdvancedSetting.setLastModifiedBy(userId);
						orderAdvancedSetting.setLastModifiedDate(new Date());
						reminderNormalService.saveOrderAdvanceSetting(orderAdvancedSetting);
						op.setState("成功");
					} catch (Exception e) {
						e.printStackTrace();
						op.setState("失败");
					}
				}
				reminderNormalService.saveUserOperationLog(op);
			}else{
				model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward:/crms/order/queryPresellReminder";
	}
	
	/**
	 * 保存或更新短信设置
	 */
	@RequestMapping("/crms/presell/sms")
	public String saveOrUpdateSms(HttpServletRequest request,SmsSetting smsSetting,ModelMap model){
		//补全属性
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			//添加操作日志
			UserOperationLog op = new UserOperationLog();
			smsSetting.setUserId(userId);
			smsSetting.setSettingType("5");
			Long smsSettingID = this.myBatisDao.findBy(SmsSetting.class.getName(), "findIdBySmsSetting", smsSetting);
			//判断id是否为空
			if(smsSettingID != null && !"".equals(smsSettingID)){
				//操作日志数据补全
				smsSetting.setId(smsSettingID);
				op.setFunctions("预售催付短信设置");
				op.setType("更新");
				op.setDate(new Date());
				op.setRemark("预售催付短信设置");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(smsSetting.getUserId());
				op.setFunctionGens("5");
				try {
					smsSetting.setLastModifiedBy(userId);
					smsSetting.setLastModifiedDate(new Date());
					reminderNormalService.updateMessageSetting(smsSetting);
					op.setState("成功");
				} catch (Exception e) {
					e.printStackTrace();
					op.setState("失败");
				}
			}else{
				//操作日志数据补全
				op.setFunctions("预售催付短信设置");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("预售催付短信设置");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(smsSetting.getUserId());
				op.setFunctionGens("5");
				try {
					smsSetting.setLastModifiedBy(userId);
					smsSetting.setLastModifiedDate(new Date());
					reminderNormalService.saveMessageSetting(smsSetting);
					op.setState("成功");
				} catch (Exception e) {
					e.printStackTrace();
					op.setState("失败");
				}
			}
			reminderNormalService.saveUserOperationLog(op);
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		return "forward:/crms/order/queryPresellReminder";
	}
	
	/**
	 * 查询基本设置、高级设置和短信设置回显至页面
	 */
	@RequestMapping("/crms/order/queryPresellReminder")
	public String querySetting(HttpServletRequest request, ModelMap model){
		//卖家id
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId!=null){
			//测试数据//======================
			//操作类型
			String settingType = "5";
			
			//需求:查询基本信息参数:卖家id: userId 操作类型:settingType
			OrderSetup orderSetupPresell = orderSetupService.findOrderSetupByUserIdAndSettingType(userId,settingType);
			if(orderSetupPresell != null && !"".equals(orderSetupPresell)){
				if(orderSetupPresell.getFiltingConditions() == null || "".equals(orderSetupPresell.getFiltingConditions())){
					orderSetupPresell.setFiltingConditions("6");
				}else{
					if(orderSetupPresell.getFiltingConditions().length() > 6){
						String filterSub = orderSetupPresell.getFiltingConditions().substring(6, orderSetupPresell.getFiltingConditions().length());
						orderSetupPresell.setFiltingConditions(filterSub);
					}
				}
			}
			//需求:查询高级信息
			OrderAdvancedSetting orderAdvancedSettingPresell = orderAdvancedSettingService.findOrderAdvancedSettingByUserIdAndSettingType(userId,settingType);
			//需求:查询 短信设置
			SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
			//查询设置状态显示顶部的流程图
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("status", "0");
			List<OrderSetup> statusList = orderSetupService.findOrderSetupOfStatus(map);
			model.addAttribute("statusList", statusList);
			//放入域对象
			model.addAttribute("orderSetupPresell", orderSetupPresell);
			//放入域对象
			model.addAttribute("orderAdvancedSettingPresell", orderAdvancedSettingPresell);
			model.addAttribute("smsSetting", smsSetting);
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		return "/crms/orderCenterZ/yushoucuifu";
	}
}
