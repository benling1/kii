package s2jh.biz.shop.crm.sendSms;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import s2jh.biz.shop.crm.manage.base.BaseController;

/**
* @ClassName: SendCodeController
* @Description: (补充资料==>获得验证码)
* @author:jackstraw_yu
* @date 2016年12月27日
*
*/
@Controller
@RequestMapping(value="/message")
@Deprecated
public class SendCodeController extends BaseController{
//
//	@Autowired
//	private UserInfoService userInfoService;
//	@Autowired
//	private CacheService cacheService;
//	@Autowired
//	private MobileSettingService mobileSettingService;
//	@Autowired
//	private SmsSendInfoService smsSendInfoService;
//	@Autowired
//	private RedisLockServiceImpl redisLockServiceImpl;
//	/**
//	* @Title: checkPhoneNum
//	* @Description:(失去焦点 判断手机号是否符合正则格式与数据库是否存在)
//	* @return void    返回类型
//	* @author:jackstraw_yu
//	* @throws
//	*/
//	@RequestMapping(value="/checkPhoneNum")
//	public @ResponseBody
//	String checkPhoneNum(String phoneNum,HttpServletResponse response){
//		 Map<String, Object> map = validateMobile(phoneNum);
//		 return rsMap(100, (String)map.get("message")).put("status", (boolean)map.get("status")).toJson();
//		 
//		 else{
//			 //通过手机号来查询用户并判断用户是否存在----(一个手机号只能使用一次//由于一个管理者管理多个店铺,该需求被取消)
//			UserInfo userInfo  = userInfoService.queryUserInfoByPhoneNum(phoneNum.trim());
//			if(userInfo !=null ){
//				js.put("status", false);
//				js.put("message", "该手机号已经被绑定,请重新输入手机号!");	
//			}else{
//				js.put("status", true);
//			}
//			 js.put("status", true);
//		 }
//	}
//========================================要了=========================	
	
	
	
	
//	
//	/**
//	* @Title: getIdentifyingCode
//	* @Description: (填写补充资料获得验证码)
//	* @author:jackstraw_yu
//	* @throws
//	*/
//	@RequestMapping(value="/getIdentifyingCode")
//	public @ResponseBody
//	String getIdentifyingCode(String phoneNum,HttpServletRequest request,HttpServletResponse response){
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		Map<String, Object> map = validateMobile(phoneNum);
//		if(!(boolean)map.get("status")){
//			 return rsMap(100, (String)map.get("message")).put("status", (boolean)map.get("status")).toJson();
//		 }else{
//			 //如果用户此次发送验证码的次数超过30次 --不予再次发送
//			 /*String num = redisLockServiceImpl.getJsonStr(RedisConstant.RedisCacheGroup.VALIDATE_CODE_TOP_CACHE+"-"+userId,
//					 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_TOP_KEY+"-"+userId);*/
//			 String num = redisLockServiceImpl.getStringValue(RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_TOP_KEY+"-"+userId);
//			 Integer times=0;
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
//				 /*redisLockServiceImpl.put(RedisConstant.RedisCacheGroup.VALIDATE_CODE_DATA_CACHE+"-"+userId, 
//						 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+"-INDEX-"+userId+"-"+phoneNum,
//						 code, TimeUnit.MINUTES, 5l);*/
//				 redisLockServiceImpl.putStringValueWithExpireTime( 
//						 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+"-INDEX-"+userId+"-"+phoneNum,
//						 code, TimeUnit.MINUTES, 5l);
//				//保存发送记录
//				// saveSystemSms(userId,phoneNum,content,result);
//				 /*redisLockServiceImpl.put(RedisConstant.RedisCacheGroup.VALIDATE_CODE_TOP_CACHE+"-"+userId,
//						 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_TOP_KEY+"-"+userId,
//						 times+"", TimeUnit.MILLISECONDS, DateUtils.getMillisOverToday());*/
//				 redisLockServiceImpl.putStringValueWithExpireTime(
//						 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_TOP_KEY+"-"+userId,
//						 times+"",TimeUnit.MILLISECONDS,DateUtils.getMillisOverToday());
//				 return rsMap(100, "").put("status", true).toJson();
//			 } 
//		 
//		 }
//	}
	
	

//	/**
//	* @Title: saveInformation
//	* @Description: (首页小红框--保存补充资料)
//	* @author:jackstraw_yu
//	* @throws
//	*/
//	@RequestMapping(value="/saveInformation")
//	@ResponseBody
//	public String saveInformation(HttpServletRequest request,HttpServletResponse response,
//												String phoneNum,String qqNum,String code){
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		//取出之前使用手机号存储的验证码
//		/*String lastCode = redisLockServiceImpl.getJsonStr(RedisConstant.RedisCacheGroup.VALIDATE_CODE_DATA_CACHE+"-"+userId, 
//				 								RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+"-INDEX-"+userId+"-"+phoneNum);*/
//		String lastCode = redisLockServiceImpl.getStringValue(
//											RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+"-INDEX-"+userId+"-"+phoneNum);
//		if(lastCode ==null || !lastCode.equals(code)){
//			return rsMap(100, "验证码已经过期或手机号错误!").put("status",false).toJson();
//		}else{
//			try {
//				//给一个初始化后台设置
////				saveInitMobileSetting(userId,phoneNum);
//				//保存用户信息
////				userInfoService.updateUserInfo(phoneNum,qqNum,userId);
//				return rsMap(100, "保存成功!").put("status",true).toJson();
//			} catch (Exception e) {
//				return rsMap(100, "保存失败,请重新操作或者请联系管理员!").put("status",false).toJson();
//			}
//		}
//		
//		
//	}
	
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
//	
//	/**
//	* @Title: produceCode
//	* @Description:(成六位的数字验证码)
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
	
	/**
	 * 用户填写小红框信息后,保存一个初始的后台设置,没有才保存
	 * **/
//	private void saveInitMobileSetting(String userId,String mobile){
//		MobileSetting result = mobileSettingService.findMobileSetting(userId);
//		if(result ==null){
//			MobileSetting mobileSetting = new MobileSetting();
//			mobileSetting.setUserId(userId);
//			mobileSetting.setActivityNotice(true);
//			mobileSetting.setCreatedDate(new Date());
//			mobileSetting.setMessageCount(50);
//			mobileSetting.setMessageRemainder(true);
//			mobileSetting.setPhoneNum(mobile);
//			mobileSetting.setExpediting(false);
//			mobileSetting.setServiceExpire(true);
//			mobileSetting.setFlag(false);
//			mobileSetting.setStartTime(null);
//			mobileSetting.setEndTime(null);
//			mobileSettingService.save(mobileSetting);
//		}
//	}
	
	
	
	/***
	 * 发送验证码保存一条验证码发送记录
	 * **/
//	private void saveSystemSms(String userId, String phoneNum, String content,String result) {
//		String status =null;
//		SmsSendInfo sms = new SmsSendInfo();
//		sms.setNickname(userId);
//		sms.setType(MsgType.SYSTEM_SMS_VALIDATECODE);
//		sms.setPhone(phoneNum);
//		sms.setUserId(MsgType.SYSTEM_ROLE_ADMIN);
//		sms.setContent(content);
//		if(result!=null && !"".equals(result)){
//			ReturnMessage returnMessage = JsonUtil.fromJson(result, ReturnMessage.class);
//			status = returnMessage.getReturnCode();
//		}
//		//发送成功则标记已发送
//		if("100".equals(status)){
//			sms.setStatus(1);
//		}else{
//			sms.setStatus(0);
//		}
//		smsSendInfoService.saveSystemSms(sms);
//	}
}
