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

import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.order.service.SmsSettingService;
import s2jh.biz.shop.crm.order.util.ResourceGetValueUtil;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.getIpAddress;

@Controller
@RequestMapping(value="cowryCare")
public class CowryCareController {
	@Autowired
	private OrderSetupService orderSetupCowryService;
	
	@Autowired
	private SmsSettingService smsSettingService;
	
	@Autowired
	private UserInfoService userInfoService;
	/**
	 * 查询基本设置和高级信息
	 */
	@RequestMapping(value = "/queryCowryCare" ,method=RequestMethod.GET)
	public String queryOrderSetupAndOrderAdvancedSetting(String msg,HttpServletRequest request, ModelMap model){
		//卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		if(userId!=null){
			//测试数据//======================
			//String userId = "crzzyboy";
			
			
			//操作类型
			String settingType = "12";
			/**
			 *	需求:查询基本信息
			 *	参数:卖家id: userId 操作类型:settingType
			 */
			OrderSetup orderSetup = orderSetupCowryService.findOrderSetupByUserIdAndSettingType(userId,settingType);
			
			/**
			 * 查询短信设置信息
			 */
			SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
			
			//放入域对象
			model.addAttribute("orderSetupCowry", orderSetup);
			
			model.addAttribute("smsSetting", smsSetting);
			
			if(msg!=null && msg!="" && msg!="null"){
				model.addAttribute("msg", msg);
			}
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		
		return "crms/orderCenterH/cowryCare";
	}
	
	
	/**
	 * 添加或修改基本设置==============================
	 * @return
	 */
	@RequestMapping(value="/saveOrderSetupToCowry" ,method=RequestMethod.POST)
	public String saveOrUpdateOrderSetup(ModelMap model,OrderSetup orderSetup,HttpServletRequest request,
			@RequestParam(value="reminderTime",defaultValue="15分钟")String reminderTime,
			@RequestParam(value="startTime",defaultValue="8:00")String startTime,
			@RequestParam(value="endTime",defaultValue="22:00")String endTime){
		orderSetup.setStartTime(startTime);
		orderSetup.setEndTime(endTime);
		//.卖家id
		HttpSession session = request.getSession();
		orderSetup.setReminderTime(reminderTime);
		String userId = (String) session.getAttribute("taobao_user_nick");
		if(userId!=null){
			//测试数据===============
			//String userId = "crzzyboy";
			String settingType = "12";
			boolean flag = false;
			//创建日志对象
			UserOperationLog op = new UserOperationLog();
			
			
			if(orderSetup.getId() != null && !"".equals(orderSetup.getId())){
				
				 /**
				  *  更新基本信息
				  */
				try {
					orderSetup.setLastModifiedDate(new Date());
					flag=orderSetupCowryService.updateOrderSetup(orderSetup);
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
					
					op.setFunctions("宝贝关怀基本设置");
					op.setType("修改");
					op.setDate(new Date());
					op.setRemark("宝贝关怀基本设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(userId);
					op.setFunctionGens("12");
					//调用日志方法添加日志
					orderSetupCowryService.saveUserOperationLog(op);
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
				orderSetup.setSettingType("12");
				//默认设置定时发送为关闭
				orderSetup.setStatus("1");
				//创建时间
				orderSetup.setCreatedDate(new Date());
				try {
					//添加基本信息
					flag=orderSetupCowryService.saveOrderSetup(orderSetup);
					if(flag){
						op.setState("基本设置保存失败");
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
					
					op.setFunctions("宝贝关怀基本设置");
					op.setType("添加");
					op.setDate(new Date());
					op.setRemark("宝贝关怀基本设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(userId);
					op.setFunctionGens("12");
					//调用日志方法添加日志
					orderSetupCowryService.saveUserOperationLog(op);
				}
				
			}
			
			OrderSetup orderSetup1 = orderSetupCowryService.findOrderSetupByUserIdAndSettingType(userId,settingType);
			model.addAttribute("orderSetup1", orderSetup1);
			model.addAttribute("msg", flag?ResourceGetValueUtil.getValue("order.setup.save.success.msg"):ResourceGetValueUtil.getValue("order.setup.save.failure.msg"));
			
			SmsSetting smsSetting = smsSettingService.findSmsSetting(userId, settingType);
			//放入域对象
			model.addAttribute("smsSetting", smsSetting);
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
		}
		return "redirect:queryCowryCare";
	}
	

	/**
	 * 短信设置保存,修改======================================
	 * 
	 */
	@RequestMapping(value="/saveOrUpdateSmsSetting" ,method=RequestMethod.POST)
	public String saveOrUpdateSmsSetting(ModelMap model,SmsSetting smsSetting,HttpServletRequest request){
		//.卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		if(userId!=null){
			//测试数据//===================
			//String userId = "crzzyboy";
			//boolean flag=false;
			//创建日志对象
			UserOperationLog op = new UserOperationLog();
			
			//判断id是否为空
			if(smsSetting.getId() != null && !"".equals(smsSetting.getId())){
				try {
					//不为空,修改
					smsSetting.setLastModifiedDate(new Date());
					smsSettingService.updateMessageSetting(smsSetting);
					op.setState("成功");
				} catch (Exception e) {
					op.setState("失败");
				}finally{
					/**
					 * 添加操作日志
					 */
					//操作日志数据补全
					op.setFunctions("宝贝关怀宝贝设置");
					op.setType("修改");
					op.setDate(new Date());
					op.setRemark("宝贝关怀宝贝设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(userId);
					op.setFunctionGens("12");
					//调用日志方法添加日志
					smsSettingService.saveUserOperationLog(op);
				}
			}else{
				
				//为空,添加,并设置参数,补全信息
				//1.卖家id
				smsSetting.setUserId(userId);
				//3.设置类型
				smsSetting.setSettingType("12");
				//4.默认设置定时发送为关闭
				smsSetting.setStatus("1");
				//5创建时间
				smsSetting.setCreatedDate(new Date());
				
				try {
					//添加高级设置
					smsSettingService.saveMessageSetting(smsSetting);
					op.setState("成功");
				} catch (Exception e) {
					op.setState("失败");
				}finally{
					/**
					 * 添加操作日志
					 */
					//操作日志数据补全
					
					op.setFunctions("宝贝关怀宝贝设置");
					op.setType("添加");
					op.setDate(new Date());
					op.setRemark("宝贝关怀宝贝设置");
					op.setIpAdd(getIpAddress.getIpAddress(request));
					op.setUserId(userId);
					op.setFunctionGens("12");
					//调用日志方法添加日志
					smsSettingService.saveUserOperationLog(op);
				}
			}
		}else{
			model.addAttribute("msg",ResourceGetValueUtil.getValue("user.account.time.out.msg"));
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
		return "redirect:queryCowryCare";
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
			orderSetupCowryService.updateOrderSetupStatus(orderSetup);
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
