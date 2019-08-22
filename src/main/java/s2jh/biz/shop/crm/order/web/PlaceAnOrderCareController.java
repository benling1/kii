package s2jh.biz.shop.crm.order.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
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
@RequestMapping(value="placeAnOrderCare")
public class PlaceAnOrderCareController {
	@Autowired
	private OrderSetupService orderSetupService;
	
	@Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
	
	@Autowired
	private SmsSettingService smsSettingService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 查询基本设置,高级,短信设置信息
	 */
	@RequestMapping(value = "/queryBasicAndAdvancedXDGH" ,method=RequestMethod.GET)
	public String queryOrderSetupAndOrderAdvancedSetting(String msg,HttpServletRequest request,ModelMap model){
//		//卖家id
//		HttpSession session = request.getSession();
//		String userId = (String) session.getAttribute("taobao_user_nick");
//		if(userId!=null){
//			//操作类型
//			String settingType = "1";
//			/**
//			 *	需求:查询基本信息
//			 *	参数:卖家id: userId 操作类型:settingType
//			 */
//			OrderSetup orderSetupXDGH = orderSetupService.findOrderSetupByUserIdAndSettingType(userId,settingType);
//			
//			/**
//			 * 需求:查询高级信息
//			 */
//			OrderAdvancedSetting orderAdvancedSetting = orderAdvancedSettingService.findOrderAdvancedSettingByUserIdAndSettingType(userId,settingType);
//			
//			/**
//			 * 查询短信设置信息
//			 */
//			SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
//			//放入域对象
//			model.addAttribute("orderSetupXDGH", orderSetupXDGH);
//			//放入域对象
//			model.addAttribute("orderAdvancedSettingXDGH", orderAdvancedSetting);
//			
//			model.addAttribute("smsSetting", smsSetting);
//			if(msg!=null && msg!="" && msg!="null"){
//				model.addAttribute("msg", msg);
//			}
//		}else{
//			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
//		}
		return "dist/index";
	}
	
	
	/**
	 * 添加或修改基本设置==============================
	 * @return
	 */
	@RequestMapping(value="/saveOrderSetupToXDGH" ,method=RequestMethod.POST)
	public String saveOrUpdateOrderSetup(ModelMap model,OrderSetup orderSetup,HttpServletRequest request,
			@RequestParam(value="startTime",defaultValue="8:00")String startTime,
			@RequestParam(value="endTime",defaultValue="22:00")String endTime){
		orderSetup.setStartTime(startTime);
		orderSetup.setEndTime(endTime);
		//.卖家id
		String settingType = "1";
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		if(userId!=null){
			//测试数据===============
			//String userId = "test1";
			//创建日志对象
			UserOperationLog op = new UserOperationLog();
			boolean  flag = false;
			orderSetup.setUserId(userId);
			orderSetup.setSettingType(settingType);
			Long orderSetupID = this.myBatisDao.findBy(OrderSetup.class.getName(), "findByUserAndSettiType", orderSetup);
			if(orderSetupID != null && !"".equals(orderSetupID)){
				/*
				 * 更新基本信息
				 */
				try {
					orderSetup.setId(orderSetupID);
					orderSetup.setLastModifiedDate(new Date());
					flag = orderSetupService.updateOrderSetup(orderSetup);
					if(flag){
						op.setState("基本设置保存成功");
					}else{
						op.setState("基本设置保存失败");
					}
				} catch (Exception e) {
					op.setState("基本设置保存失败");
				}finally{
					/**
					 * 添加操作日志
					 */
					//操作日志数据补全
					op.setFunctions("下单关怀基本设置");
					op.setType("修改");
					op.setDate(new Date());
					op.setRemark("下单关怀基本设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(userId);
					op.setFunctionGens("1");
					//调用日志方法添加日志
					orderSetupService.saveUserOperationLog(op);
				}
				
				
			}else{
				/**
				 * 参数1:添加基本信息
				 * 参数2:卖家id
				 * 参数3:买家id
				 * 参数4:订单id
				 */
				//补全信息
				//1.卖家id(测试)
				orderSetup.setUserId(userId);
				//4.设置类型
				orderSetup.setSettingType("1");
				//默认设置定时发送为关闭
				orderSetup.setStatus("1");
				//创建时间
				orderSetup.setCreatedDate(new Date());
				orderSetup.setFiltingConditions("5");//设置过滤条件为超出时间不发送
				try {
					//添加基本信息
					flag = orderSetupService.saveOrderSetup(orderSetup);
					if(flag){
						op.setState("基本设置保存成功");
					}else{
						op.setState("基本设置保存失败");
					}
				} catch (Exception e) {
					op.setState("基本设置保存失败");
				}finally{
					
					/**
					 * 添加操作日志
					 */
					//操作日志数据补全
					op.setFunctions("下单关怀基本设置");
					op.setType("添加");
					op.setDate(new Date());
					op.setRemark("下单关怀基本设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(userId);
					op.setFunctionGens("1");
					//调用日志方法添加日志
					orderSetupService.saveUserOperationLog(op);
				}
				
				
			}
			/**
			 * 需求:查询高级信息
			 */
			OrderAdvancedSetting orderAdvancedSetting = orderAdvancedSettingService.findOrderAdvancedSettingByUserIdAndSettingType(userId,settingType);
			/**
			 * 查询短信设置信息
			 */
			SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
			//放入域对象
			model.addAttribute("orderAdvancedSettingXDGH", orderAdvancedSetting);
			model.addAttribute("smsSetting", smsSetting);
			model.addAttribute("msg", flag?ResourceGetValueUtil.getValue("order.setup.save.success.msg"):ResourceGetValueUtil.getValue("order.setup.save.failure.msg"));
			OrderSetup orderSetupXDGH = orderSetupService.findOrderSetupByUserIdAndSettingType(userId,settingType);
			model.addAttribute("orderSetupXDGH", orderSetupXDGH);
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		
		return "crms/orderCenterH/placeAnOrderCare";
	}
	
	/**
	 * 添加或修改高级设置======================================= 
	 */
	@RequestMapping(value="/saveOrderAdvancedSettingXDGH" ,method=RequestMethod.POST)
	public String saveOrUpdateOrderAdvancedSetting(ModelMap model,OrderAdvancedSetting orderAdvancedSetting,HttpServletRequest request){
		boolean flag = false;
		String settingType = "1";
		//.卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		if(userId!=null){
			//测试数据//===================
			//String userId = "test1";
			
			//创建日志对象
			UserOperationLog op = new UserOperationLog();
			orderAdvancedSetting.setUserId(userId);
			orderAdvancedSetting.setSettingType(settingType);
			Long userOrderAdvancedID = this.myBatisDao.findBy(OrderAdvancedSetting.class.getName(), "findAdvanceByUserAndSettiType", orderAdvancedSetting);
			//判断id是否为空
			if(userOrderAdvancedID != null && !"".equals(userOrderAdvancedID)){
				try {
					orderAdvancedSetting.setId(userOrderAdvancedID);
					//不为空,修改
					orderAdvancedSetting.setLastModifiedDate(new Date());
					flag = orderAdvancedSettingService.updateOrderAdvancedSetting(orderAdvancedSetting);
					if(flag){
						op.setState("成功");
					}else{
						op.setState("失败");
					}
				} catch (Exception e) {
					op.setState("失败");
				}finally{
					/**
					 * 添加操作日志
					 */
					//操作日志数据补全
					
					op.setFunctions("下单关怀高级设置");
					op.setType("修改");
					op.setDate(new Date());
					op.setRemark("下单关怀高级设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(userId);
					op.setFunctionGens(settingType);
					//调用日志方法添加日志
					orderAdvancedSettingService.saveUserOperationLog(op);
				}
				
				
			}else{
				
				//为空,添加,并设置参数,补全信息
				//1.卖家id
				orderAdvancedSetting.setUserId(userId);
				//3.设置类型
				orderAdvancedSetting.setSettingType(settingType);
				//4.默认设置定时发送为关闭
				orderAdvancedSetting.setStatus("1");
				//5创建时间
				orderAdvancedSetting.setCreatedDate(new Date());
				
				try {
					//添加高级设置
					flag = orderAdvancedSettingService.saveOrderAdvancedSetting(orderAdvancedSetting);
					if(flag){
						op.setState("成功");
					}else{
						op.setState("失败");
					}
				} catch (Exception e) {
					op.setState("失败");
				}finally{
					/**
					 * 添加操作日志
					 */
					//操作日志数据补全
					
					op.setFunctions("下单关怀高级设置");
					op.setType("添加");
					op.setDate(new Date());
					op.setRemark("下单关怀高级设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(userId);
					op.setFunctionGens(settingType);
					//调用日志方法添加日志
					orderAdvancedSettingService.saveUserOperationLog(op);
				}
				
				
			}  
			
			OrderSetup orderSetupXDGH = orderSetupService.findOrderSetupByUserIdAndSettingType(userId,settingType);
			
			/**
			 * 查询短信设置信息
			 */
			SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
			
			//放入域对象
			model.addAttribute("orderSetupXDGH", orderSetupXDGH);
			//放入域对象
			if(flag){
				model.addAttribute("orderAdvancedSettingXDGH", orderAdvancedSetting);
				model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.success.msg"));
			}else{
				model.addAttribute("msg",ResourceGetValueUtil.getValue("order.advanced.setup.save.failure.msg"));
				orderAdvancedSetting = orderAdvancedSettingService.findOrderAdvancedSettingByUserIdAndSettingType(userId,settingType);
				model.addAttribute("orderAdvancedSettingXDGH", orderAdvancedSetting);
			}
			model.addAttribute("smsSetting", smsSetting);
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		
		return "crms/orderCenterH/placeAnOrderCare";
		
	}
	
	
	
	/**
	 * 短信设置保存,修改======================================
	 * 
	 */
	@RequestMapping(value="/saveOrUpdateSmsSetting" ,method=RequestMethod.POST)
	public String saveOrUpdateSmsSetting(ModelMap modelMap,SmsSetting smsSetting,HttpServletRequest request){
		boolean flag = false;
		String settingType = "1";
		//.卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		if(userId!=null){
			//测试数据//===================
			//String userId = "test1";
			smsSetting.setUserId(userId);
			smsSetting.setSettingType(settingType);
			//创建日志对象
			UserOperationLog op = new UserOperationLog();
			Long smsSettingID = this.myBatisDao.findBy(SmsSetting.class.getName(), "findIdBySmsSetting", smsSetting);
			//判断id是否为空
			if(smsSettingID != null && !"".equals(smsSettingID)){
				try {
					smsSetting.setId(smsSettingID);
					//不为空,修改
					String  messageSignature = smsSetting.getMessageSignature();
					if(messageSignature!=null && messageSignature!=""){
						smsSetting.setLastModifiedDate(new Date());
						flag = smsSettingService.updateMessageSetting(smsSetting);
						if(flag){
							op.setState("成功");
							modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.success.msg"));
						}else{
							op.setState("失败");
							modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.failure.msg"));
						}
					}else{
						op.setState("短信签名为空");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.null.msg"));
					}
				} catch (Exception e) {
					op.setState("失败");
				}finally{
					/**
					 * 添加操作日志
					 */
					//操作日志数据补全
					op.setFunctions("下单关怀短信设置");
					op.setType("修改");
					op.setDate(new Date());
					op.setRemark("下单关怀短信设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(userId);
					op.setFunctionGens("1");
					//调用日志方法添加日志
					smsSettingService.saveUserOperationLog(op);
				}
			}else{
				
				//为空,添加,并设置参数,补全信息
				//1.卖家id
				smsSetting.setUserId(userId);
				//3.设置类型
				smsSetting.setSettingType("1");
				//4.默认设置定时发送为关闭
				smsSetting.setStatus("1");
				//5创建时间
				smsSetting.setCreatedDate(new Date());
				
				try {
					//添加高级设置
					flag = smsSettingService.saveMessageSetting(smsSetting);
					if(flag){
						op.setState("成功");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.success.msg"));
					}else{
						op.setState("失败");
						modelMap.addAttribute("msg", ResourceGetValueUtil.getValue("sms.setting.save.failure.msg"));
					}
				} catch (Exception e) {
					op.setState("失败");
				}finally{
					/**
					 * 添加操作日志
					 */
					//操作日志数据补全
					
					op.setFunctions("下单关怀短信设置");
					op.setType("添加");
					op.setDate(new Date());
					op.setRemark("下单关怀短信设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(userId);
					op.setFunctionGens("1");
					//调用日志方法添加日志
					smsSettingService.saveUserOperationLog(op);
				}
			}
		}else{
			modelMap.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		
		//修改用户的店铺签名
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("shopName", smsSetting.getMessageSignature());
		int status = userInfoService.updateShopName(map);
		if(status>0){
			session.setAttribute("ShopName", smsSetting.getMessageSignature());
		}
		smsSettingService.findSmsSettingList(userId, smsSetting.getMessageSignature());
		smsSettingService.updateOrderReminderSmsContent(smsSetting.getMessageSignature(),userId);
		return "redirect:queryBasicAndAdvancedXDGH";
	}
	
	/**
	 * 改变开启或关闭状态
	 * helei 2017年1月10日下午12:06:12
	 */
	@RequestMapping(value="/openOrCloseStatus" ,method=RequestMethod.POST)
	public String openOrCloseStatus(String buttonId,String orderSetupId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		OrderSetup orderSetup = new OrderSetup();
		orderSetup.setId(Long.parseLong(orderSetupId));
		orderSetup.setStatus(buttonId);
		orderSetup.setLastModifiedDate(new Date());
		//创建对象封装参数,输出到前台
		JSONObject  json = new JSONObject();
		try {
			orderSetupService.updateOrderSetupStatus(orderSetup);
			String sellerNick = (String) request.getSession().getAttribute("taobao_user_nick");
			this.userInfoService.addUserPermitByMySql(sellerNick, null);
			json.put("status", buttonId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//将封装好的json数据,输出到前台
			response.setContentType("application/json; charset=utf-8");  
			response.getWriter().write(json.toString());
		}
		
		return null;
	}
	
}
