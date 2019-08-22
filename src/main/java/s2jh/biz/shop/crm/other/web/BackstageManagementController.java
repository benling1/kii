package s2jh.biz.shop.crm.other.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import s2jh.biz.shop.crm.manage.base.BaseComponent;

/**
* @ClassName: BackstageManagementController
* @Description: (后台管理)
* @author:jackstraw_yu
* @date 2016年12月1日
*
*/
@Controller
@RequestMapping(value="/backstageManagement")
@Deprecated
public class BackstageManagementController extends BaseComponent {

//	private static final Log logger = LogFactory.getLog(BackstageManagementController.class);
//	
//	@Autowired
//	private MobileSettingService mobileSettingService;
//	@Autowired
//	private CacheService cacheService;
//	@Autowired
//	private SmsSendInfoService smsSendInfoService;
//	@Autowired
//	private RedisLockServiceImpl redisLockServiceImpl;
//	
	/**
//	* @Title: toBackstageManagement
//	* @Description: (后台管理页面的跳转)
//	* @author:jackstraw_yu
//	* @throws
//	*/
//	@RequestMapping(value="/mobileSetting")
//	public String toBackstageManagement(Model model,HttpServletRequest request){
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		MobileSetting mobileSetting = mobileSettingService.findMobileSetting(userId);
//		model.addAttribute("mobileSetting", mobileSetting);
//		if(mobileSetting!=null){
//			//催付效果
//			model.addAttribute("expediting", mobileSetting.getExpediting());
//			//短信余额是否提醒
//			model.addAttribute("messageRemainder", mobileSetting.getMessageRemainder());
//			//短信余额条数
//			model.addAttribute("messageCount", mobileSetting.getMessageCount());
//			//软件过期提醒
//			model.addAttribute("serviceExpire", mobileSetting.getServiceExpire());
//			//最新活动提醒
//			model.addAttribute("activityNotice", mobileSetting.getActivityNotice());
//			//手机号
//			model.addAttribute("phoneNum", mobileSetting.getPhoneNum());
//		}
//		return "crms/backstageManagement/backstageManagement";
//	}
//	
	
	
	
//	
//	/**
//	* @Title: getPhoneCode
//	* @Description: (保存后台设置修改手机号时获取验证码)
//	* @author:jackstraw_yu
//	* @throws
//	*/
//	@RequestMapping(value="/getPhoneCode")
//	public String getPhoneCode(HttpServletRequest request,HttpServletResponse response,
//								String phoneNum){
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		Map<String, Object> map = validateMobile(phoneNum);
//		if(!(boolean)map.get("status")){
//			 return rsMap(100, (String)map.get("message")).put("status", (boolean)map.get("status")).toJson();
//		 }else{
//			 //如果用户使用本软件发送验证码的一天次数超过30次 --不予再次发送,次日归零
//			/* String num = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.VALIDATE_CODE_TOP_CACHE,
//					 					RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_TOP_KEY+"-"+userId);*/
//			 String num = redisLockServiceImpl.getStringValue(RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_TOP_KEY+"-"+userId);
//			 int times=0;
//			 if(num!=null&&!"".equals(num))
//				 try{
//					times =  Integer.valueOf(num);
//				 }catch (Exception e) {
//					times =0;
//				 }
//			 if(times>=30){
//				 //预防重复点击发送||一天之内的发送上限?
//				 return rsMap(100, "您今天使用该软件发送验证码的次数已达上限,请明日再次使用,谢谢!").put("status", false).toJson();
//			 }else{
//				 String code = produceCode();
//				 String content =ConstantUtils.MESSAGE_VALIDATECODE_CONTNET;
//				 content = content.replace("CODE", code);
//				 String [] phones = {phoneNum.trim()};
//				 String result = SendMessageUtilForHangYe.sendMessage(phones, content, null, null);
//				 times+=1;
//				/* cacheService.put(RedisConstant.RedisCacheGroup.VALIDATE_CODE_DATA_CACHE, 
//						 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+"-BACK-"+userId+"-"+phoneNum,
//						 code, TimeUnit.MINUTES, 5l);*/
//				 redisLockServiceImpl.putStringValueWithExpireTime( 
//						 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+"-BACK-"+userId+"-"+phoneNum,
//						 code, TimeUnit.MINUTES, 5l);
//				//保存发送记录
//				 //saveSystemSms(userId,phoneNum,content,result);
//				 /*cacheService.put(RedisConstant.RedisCacheGroup.VALIDATE_CODE_TOP_CACHE,
//						 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_TOP_KEY+"-"+userId,
//						 times+"", TimeUnit.MILLISECONDS, DateUtils.getMillisOverToday());*/
//				 redisLockServiceImpl.putStringValueWithExpireTime(
//						 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_TOP_KEY+"-"+userId,
//						 times+"",TimeUnit.MILLISECONDS,DateUtils.getMillisOverToday());
//				 return rsMap(100, "").put("status", true).toJson();
//			 } 
//		 }
//	}
//	
//	
//	/***
//	 * 发送验证码保存一条验证码发送记录
//	 * **/
////	private void saveSystemSms(String userId, String phoneNum, String content,String result) {
////		String status =null;
////		SmsSendInfo sms = new SmsSendInfo();
////		sms.setNickname(userId);
////		sms.setType(MsgType.SYSTEM_SMS_VALIDATECODE);
////		sms.setPhone(phoneNum);
////		sms.setUserId(MsgType.SYSTEM_ROLE_ADMIN);
////		sms.setContent(content);
////		if(result!=null && !"".equals(result)){
////			ReturnMessage returnMessage = JsonUtil.fromJson(result, ReturnMessage.class);
////			status = returnMessage.getReturnCode();
////		}
////		//发送成功则标记已发送
////		if("100".equals(status)){
////			sms.setStatus(1);
////		}else{
////			sms.setStatus(0);
////		}
////		smsSendInfoService.saveSystemSms(sms);
////	}
//
//
//
//	/**
//	* @Title: saveMobileSetting
//	* @Description: (保存修改手机号设置)
//	* @author:jackstraw_yu
//	* @throws
//	*/
//	@RequestMapping(value="/saveMobileSetting")
//	public @ResponseBody
//	String saveMobileSetting(Model model,HttpServletRequest request,HttpServletResponse response,
//							MobileSetting mobileSetting,String oldPNum,String phoneNum,
//							String phoneCode,String messageCount){
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		//1取出之前使用手机号存储的验证码
//		//2..如果oldPNum 不存在 说明走的第一次保存,直接判断验证码是否相等;
//		//2..如果存在说明是修改,判断手机号是否被修改过,没有修改该过,直接保存
//		//2...............................,修改过,验证验证码是否相等;
//		Map<String, Object> map = validateUserOperate(userId,phoneNum,oldPNum,messageCount,phoneCode);
//		if(!(boolean)map.get("status")){
//			return rsMap(100, (String)map.get("message")).put("status", (boolean)map.get("status")).toJson();
//		}else{
//			mobileSetting.setUserId(userId);
//			mobileSetting.setPhoneNum(phoneNum.trim());
//			mobileSetting.setMessageCount(Integer.valueOf(messageCount.trim()));
//			try {
//				mobileSettingService.saveMobileSetting(mobileSetting);
//				return rsMap(100, "保存设置成功!").put("status", true).toJson();
//			} catch (Exception e) {
//				logger.error("##################### saveMobileSetting Exception:"+e.getMessage());
//				return rsMap(100, "保存设置失败,请重新操作或者联系系统管理员").put("status", false).toJson();
//			}
//		}
//		
//	}
//	
//	
//	
//	
//	
//	/***
//	 * 验证手机号是否正确
//	 * **/
//	private Map<String,Object> validateMobile(String mobile){
//		Map<String,Object> map = new HashMap<String,Object>();
//		boolean status = true;
//		String message = "";
//		if(mobile == null|| "".equals(mobile.trim())){
//			status = false;
//			message = "手机号不能为空!";
//		}else if(!PhoneRegUtils.phoneValidate(mobile.trim())){
//			status = false;
//			message = "手机号输入错误!";
//		}
//		map.put("status", status);
//		map.put("message", message);
//		return map;
//	}
//	
//	
//	
//	/**
//	* @Title: produceCode
//	* @Description: (成六位的数字验证码)
//	* @author:jackstraw_yu
//	* @throws
//	*/
//	private String produceCode(){
//		String code = "";
//		Random random = new Random();
//		for(int i =0;i<6;i++){
//			int num = random.nextInt(10);
//			code +=num;
//		}
//		return code;
//	}
//	
//	/**
//	 * 用戶修改后台设置时校验用户操作
//	 * **/
//	private Map<String,Object> validateUserOperate(String userId,String newNum,String oldNum,String messageCount,String vCode){
//		Map<String,Object> map = new HashMap<String,Object>();
//		boolean status = true;
//		String message = "",rCode ="";
//		if(newNum !=null){
//			/*rCode = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.VALIDATE_CODE_DATA_CACHE, 
//						RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+"-BACK-"+userId+"-"+newNum.trim());*/
//			rCode = redisLockServiceImpl.getStringValue(
//					RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+"-BACK-"+userId+"-"+newNum.trim());
//		}
//		if((oldNum ==null || "".equals(newNum.trim())) || !oldNum.equals(newNum.trim())){
//			if(rCode==null || "".equals(rCode)){
//				status = false;
//				message = "验证码已过期!";
//			}else if(!rCode.equals(vCode)){
//				status = false;
//				message = "验证码错误!";
//			}
//		}
//		if(newNum == null || "".equals(newNum.trim())){
//			status = false;
//			message = "手机号不能为空!";
//		}else{
//			if(!PhoneRegUtils.phoneValidate(newNum.trim())){
//				status = false;
//				message = "手机号不正确!";
//			}
//		}
//		if(messageCount != null && !"".equals(messageCount.trim())){
//			String reg = "^\\d+$"; 
//			if(!messageCount.matches(reg)){
//				status = false;
//				message = "短信余额不是数字!";
//			}
//		}
//		map.put("status", status);
//		map.put("message", message);
//		return map;
//	}
//	
}
