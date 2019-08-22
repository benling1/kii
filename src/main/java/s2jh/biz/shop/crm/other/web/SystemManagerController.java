package s2jh.biz.shop.crm.other.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.service.RedisLockServiceImpl;
import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.other.entity.MobileSetting;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.taobao.util.SendMessageIndexUtil;
import s2jh.biz.shop.crm.taobao.util.SendMessageUtilForHangYe;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.phoneRegExp.PhoneRegUtils;


/** 
* @ClassName: SystemManagerController 
* @Description: 系统管理控制层(整合):<br/>
* 1:后台管理模块<br/>
* 2:验证码<br/>
* 3:短链接 <br/>
* @author jackstraw_yu
* @date 2017年11月21日 下午4:25:02 
*  
*/
@Controller
@RequestMapping("/systemManage")
public class SystemManagerController extends BaseController{

	private static final Log logger = LogFactory.getLog(SystemManagerController.class);
	/**
	 * 前台验证码标识
	 */
	private  final String INDEX_SIGN = "INDEX";
	/**
	 * 后台验证码标识
	 */
	private  final String BACK_SIGN = "BACK";
	/**
	 * 分隔符
	 */
	private  final String SEPARATOR= "-";
	/**
	 * 手机验证码正则表达式
	 * */
	private  final Pattern REGEX_CODE = Pattern.compile("([0-9]){6}");
	
	@Autowired
	private CacheService cacheService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private MobileSettingService mobileSettingService;
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	
	
	/**
	* @Title: createTaobaoLink
	* @Description: (生成短链接,订单中心获取短链接)
	* @return String    返回类型
	* @author:jackstraw_yu
	*/
	@RequestMapping(value="/getLink",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String createTaobaoLink(Model model,HttpServletRequest request,@RequestBody String params){ 
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String, String> map =null;
		String token =null;
		try {
			 map = parseLinkParamJson(params);
			 //获取token用于生成短链接
			 token  = getSessionkey(userId);
		} catch (Exception e) {
			logger.error("#####################  Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(map==null || token==null)
			return  rsMap(101, "操作失败").put("status", false).toJson();
		try {
			String link = TaoBaoClientUtil.creatLink(token, map.get("type"), map.get("value"));
			if(link==null)
				return rsMap(101, "操作失败").put("status", false).put("data",null).toJson();
			return rsMap(100, "操作成功").put("status", true).put("data"," "+link+" ").toJson();
		} catch (Exception e) {
			logger.error("#####################  Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
	}
	


//=================================RedPocket
	
	/** 
	* @Description: 首页加载后,判断用户: 
	* 1,有没有填写过手机号<br/>
	* 2,是否赠送了客户500条短信<br/>
	* @param  request
	* @param  response
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月27日 下午12:25:48
	*/
	@RequestMapping(value = "/queryUserInformation",method=RequestMethod.POST)
	@ResponseBody
	public String queryUserInfomation(HttpServletRequest request,HttpServletResponse response) {
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		UserInfo user = null;
		try {
			user = userInfoService.queryUserInfoByNick(userId);
		} catch (Exception e) {
			logger.error("##################### Exception:"+e.getMessage());
			return rsMap(102,"").put("status", false).toJson();
		}
		if(user!=null){
			//手机号为空时;是否赠送也为空,才显示小红包
			if(user.getMobile() == null)
				if(user.getHasProvide() == null)//|| user.getHasProvide().booleanValue() == false
					return rsMap(100, "").put("status", true).toJson();
		}else{
			return rsMap(101, "用户为空!").put("status", false).toJson();
		}
		return rsMap(101, "").put("status", false).toJson();
	}
	
	/** 
	* @Description: 标记用户,首页不在显示小红包
	* @param  request
	* @param  response
	* @param  flag
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月27日 下午5:25:55
	*/
	@RequestMapping(value = "/markIndexShow",method=RequestMethod.POST)
	@ResponseBody
	public String markIndexShow(HttpServletRequest request,HttpServletResponse response,Boolean flag) {
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(flag !=null && flag.booleanValue() == true){
			//将用户标记为首页小红包不显示!
			boolean hasProvide = false;
			try {
				 userInfoService.updateUserHasProvide(userId,hasProvide);
				 return rsMap(100, "").put("status", true).toJson();
			} catch (Exception e) {
				logger.error("#####################  Exception:"+e.getMessage());
				return rsMap(102,"").put("status", false).toJson();
			}
		}
		return rsMap(101, "参数错误!!").put("status", false).toJson();
	}
	
	
	
	/** 
	* @Title: indexSecurityCode
	* @Description:首页小红包,点击获取验证码接口 
	* @param mobile
	* @param request
	* @param response
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月21日 下午4:49:39
	*/
	@RequestMapping(value="/indexSecurityCode",method=RequestMethod.POST)
	public @ResponseBody
	String indexSecurityCode(String mobile,HttpServletRequest request,HttpServletResponse response){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(mobile==null || "".equals(mobile) || !PhoneRegUtils.phoneValidate(mobile))
			return rsMap(101, "手机号错误!").put("status", false).toJson();
		 //如果用户此次发送验证码的次数超过30次 --不予再次发送
		 int times = getSecurityCodeTop(userId);
		 //预防重复点击发送||一天之内的发送上限?
		 if(times>=30)
			 return rsMap(101, "您今天使用该软件发送验证码的次数已达上限,请明日再次使用,谢谢!").put("status", false).toJson();
		 //验证通过开始生成并发送验证码
		 String code = produceCode(),content =ConstantUtils.MESSAGE_VALIDATECODE_CONTNET;
		 content = content.replace("CODE", code);
		 String [] phones = {mobile};
		 SendMessageIndexUtil.sendMessage(phones, content, null, null);
		 redisLockServiceImpl.putStringValueWithExpireTime( 
				 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+this.SEPARATOR+this.INDEX_SIGN+this.SEPARATOR+userId+this.SEPARATOR+mobile,
				 code, TimeUnit.MINUTES, 5l);
		 //放入使用次数
		 putSecurityCodeTop(userId,++times);
		 return rsMap(100, "").put("status", true).toJson();
	}
	
	/** 
	* @Description: 首页填写小红包:
	* 1,判断用户是否有后台设置,没有后台设置,保存一条初始化后台设置<br/>
	* 2:保存用户的手机号,qq号,赠送500条短信<br/>
	* @param  request
	* @param  response
	* @param  mobile
	* @param  qqNum
	* @param  code
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月27日 下午2:55:10
	*/
	@RequestMapping(value="/saveUserInformation",method=RequestMethod.POST)
	@ResponseBody
	public String saveUserInformation(HttpServletRequest request,HttpServletResponse response,
												String mobile,String qqNum,String code){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(mobile==null || "".equals(mobile) || !PhoneRegUtils.phoneValidate(mobile))
			return rsMap(101, "手机号填写错误").put("status",false).toJson();
		if(code==null || "".equals(code) || !this.REGEX_CODE.matcher(code).matches())
			return rsMap(101, "验证码填写错误").put("status",false).toJson();
		String lastCode = redisLockServiceImpl.getStringValue(
											RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY
											+this.SEPARATOR+this.INDEX_SIGN+this.SEPARATOR+userId+this.SEPARATOR+mobile);
		if(lastCode ==null || !lastCode.equals(code))
			return rsMap(101, "验证码已过期或手机号错误!").put("status",false).toJson();
		try {
			//1,在控制层方法中,保存用户手机号qq号,给用户添加500条短信,初始化后台设置
			userInfoService.saveUserMobileAndInitSetting(mobile,qqNum,userId);
			//2,移除验证码
			redisLockServiceImpl.removeAll(
					RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+this.SEPARATOR+this.INDEX_SIGN+this.SEPARATOR+userId+this.SEPARATOR+mobile);
			return rsMap(100, "保存成功!").put("status",true).toJson();
		} catch (Exception e) {
			logger.error("#####################  Exception:"+e.getMessage());
			return rsMap(102, "保存失败,请重新操作或者请联系系统管理员!").put("status",false).toJson();
		}
		
		
	}
	
	

//=================================BackstageManange	

	/** 
	* 后台管理页面跳转 </br>
	* 查询出用户的后台设置,放入域中并跳转jsp页面</br>
	* @param  model
	* @param  request
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月15日 下午3:40:46
	*/
	@RequestMapping(value="/mobileSetting"/*,method=RequestMethod.GET*/)
	public String toBackstageManagement(Model model,HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		MobileSetting mobileSetting = mobileSettingService.findMobileSetting(userId);
		model.addAttribute("mobileSetting", mobileSetting);
		if(mobileSetting!=null){
			//催付效果
			model.addAttribute("expediting", mobileSetting.getExpediting());
			//短信余额是否提醒
			model.addAttribute("messageRemainder", mobileSetting.getMessageRemainder());
			//短信余额条数
			model.addAttribute("messageCount", mobileSetting.getMessageCount());
			//软件过期提醒
			model.addAttribute("serviceExpire", mobileSetting.getServiceExpire());
			//最新活动提醒
			model.addAttribute("activityNotice", mobileSetting.getActivityNotice());
			//手机号
			model.addAttribute("phoneNum", mobileSetting.getPhoneNum());
		}
		return "crms/backstageManagement/backstageManagement";
	}
	
//	/** 针对于接口/暂时不用
//	* @Description: 查询用户的后天设置;针对接口调用,暂时不用
//	* @param  model
//	* @param  request
//	* @return String    返回类型 
//	* @author jackstraw_yu
//	* @date 2017年11月27日 下午4:08:35
//	*/
//	@RequestMapping(value="/queryMobileSetting",method=RequestMethod.POST)
//	@ResponseBody
//	public String queryMobileSetting(Model model,HttpServletRequest request){
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		MobileSetting mobileSetting =null;
//		try {
//			mobileSetting = mobileSettingService.findMobileSetting(userId);
//		} catch (Exception e) {
//			//填坑:多一条后台设置,导致查询报错
//			return rsMap(102, "获取后台设置失败,请重新操作或者联系系统管理员").put("status", false).toJson();
//		}
//		return rsMap(100, "").put("status", true).put("data",mobileSetting).toJson();
//	}
	
	
	/** 
	* @Description 后台设置,修改手机号,获取后台设置的验证:<br/>
	* 后台设置获取验证码<br/>
	* 第一次保存时获取验证码<br/>
	* 修改后台设置并修改设置中的手机号时获取验证码<br/>
	* @param  request
	* @param  response
	* @param  mobile
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月15日 下午3:42:30
	*/
	@RequestMapping(value="/backstageSecurityCode",method=RequestMethod.POST)
	@ResponseBody
	public String backstageSecurityCode(HttpServletRequest request,HttpServletResponse response,
									String mobile){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(mobile==null || "".equals(mobile) || !PhoneRegUtils.phoneValidate(mobile))
			return rsMap(101, "手机号填写错误").put("status",false).toJson();
		//如果用户此次发送验证码的次数超过30次 --不予再次发送
		 int times = getSecurityCodeTop(userId);
		 if(times>=30)
			 return rsMap(101, "您今天使用该软件发送验证码的次数已达上限,请明日再次使用,谢谢!").put("status", false).toJson();
		 String code = produceCode(),content = ConstantUtils.MESSAGE_VALIDATECODE_CONTNET;
		 content = content.replace("CODE", code);
		 String [] phones = {mobile};
		 SendMessageUtilForHangYe.sendMessage(phones, content, null, null);
		 redisLockServiceImpl.putStringValueWithExpireTime( 
				 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY+this.SEPARATOR+this.BACK_SIGN+this.SEPARATOR+userId+this.SEPARATOR+mobile,
				 code, TimeUnit.MINUTES, 5l);
		 putSecurityCodeTop(userId,++times);
		 return rsMap(100, "").put("status", true).toJson();
	}
	
	/** 
	* @Description 保存后者或者更新后台设置
	* 1,保存用户后台设置<br/>
	* 1.1,保存后天设置,更新用户手机号,赠送短信标记<br/>
	* 1.2,赠送用户500条短信<br/>
	* 2,更新用户后台设置<br/>
	* @param request
	* @param response
	* @param mobileSetting
	* @param lastMobile
	* @param code
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月27日 下午7:42:17
	*/
	@RequestMapping(value="/saveMobileSetting",method=RequestMethod.POST)
	public @ResponseBody
	String saveMobileSetting(HttpServletRequest request,HttpServletResponse response,
							MobileSetting mobileSetting,String lastMobile,String code){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//1取出之前使用手机号存储的验证码
		//2..如果oldPNum 不存在 说明走的第一次保存,直接判断验证码是否相等;
		//2..如果存在说明是修改,判断手机号是否被修改过,没有修改该过,直接保存
		//2...............................,修改过,验证验证码是否相等;
		Map<String, Object> map = validateUserOperate(userId,mobileSetting.getPhoneNum(),lastMobile,code);
		if(!(boolean)map.get("status")){
			return rsMap(101, (String)map.get("message")).put("status", (boolean)map.get("status")).toJson();
		}else{
			mobileSetting.setUserId(userId);
			Long primaryKey = null;
			try {
				if(mobileSetting.getId()==null){
					primaryKey = mobileSettingService.saveInitMobileSetting(mobileSetting);
				}else{
					mobileSettingService.updateMobileSetting(mobileSetting);
				}
				redisLockServiceImpl.removeAll(
						RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY
						+this.SEPARATOR+this.BACK_SIGN+this.SEPARATOR+userId+this.SEPARATOR+mobileSetting.getPhoneNum());
				return rsMap(100, "保存设置成功!").put("status", true).put("key",primaryKey).toJson();
			} catch (Exception e) {
				logger.error("#####################  Exception:"+e.getMessage());
				return rsMap(102, "保存设置失败,请重新操作或者联系系统管理员").put("status", false).toJson();
			}
		}
		
	}
	 
//=============================================后台管理，修改签名===========================================================	 
	 
	 /**
	  * Gg
	  * 后台管理--短信签名回显
	  * @param request
	  * @return
	  * Gg
	  */
	 @RequestMapping(value="/showShopName",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	 @ResponseBody
	 public String findShopName(HttpServletRequest request){
		String userId = (String)request.getSession().getAttribute("taobao_user_nick");
		if(userId==null) return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		List<String> list = new ArrayList<String>();
		String shopName = this.getShopName(userId);
		if(shopName!=null){
			list.add(shopName);
		}else{
			return rsMap(101, "操作失败，请联系管理员或者重新操作！").put("status", false).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).put("shopName", list).toJson();
	}
	
	 /**
	  * Gg
	  * 后台管理--修改短信签名接口
	  * @param rquest
	  * @param params
	  * @return
	  * Gg
	  */
    @RequestMapping(value="/modifyShopName",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
    @ResponseBody
    public String modifyShopName(HttpServletRequest request,String shopName){
		String userId = (String)request.getSession().getAttribute("taobao_user_nick");
		if(shopName==null) return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", "短信签名不可为空！").toJson();
		if(userId==null) return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		try {
			userInfoService.modiftyShopName(userId,shopName);
			//将修改后的短信签名放入 缓存中============
			cacheService.putNoTime(RedisConstant.RedisCacheGroup.SHOP_NAME_CACHE,  
					RedisConstant.RediskeyCacheGroup.SHOP_NAME_KEY+userId,shopName);
		} catch (Exception e) {
			logger.error("********用户"+userId+"修改短信签名异常!*******"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).toJson();
	}
    
    /*@RequestMapping(value="/addForbiddenUser",method = RequestMethod.GET)
    @ResponseBody
    public String addForbiddenUser(HttpServletRequest request,String userName){
		if(userName!=null && !"".equals(userName)){
	    	redisLockServiceImpl.putStringValueWithoutExpireTime(
					RedisConstant.RediskeyCacheGroup.LOGIN_FORBIDDEN_USER
					+"-"+userName.trim(),userName);
		}
		return rsMap(100, "操作成功").put("status", true).toJson();
	}*/
		
//============================================后台管理修改签名调用方法体============================================================	
	
	/**	//"{\"params\":{\"type\":\"LT_TRADE\",\"value\":\"1234\"}}"
	* @Title: parseLinkParamJson
	* @return Map<String,String>
	* @author:jackstraw_yu
	*/
	private Map<String,String> parseLinkParamJson(String params){
		Map<String,String> map = null;
		String type =null,value=null;
		if(params!=null){
			JSONObject jsonObj = JSON.parseObject(params);
			if(jsonObj!=null){
				String param = jsonObj.getString("params");
				if(param!=null && !"".equals(param)){
					JSONObject json = JSON.parseObject(param);
					type = json.getString("type");
					value= json.getString("value");
				}
			}
		}
		if(type!=null &&!"".equals(type)){
			//店铺的link不需要value
			if("LT_SHOP".equals(type))
				value=null;
			map = new HashMap<String, String>();
			map.put("type", type);
			map.put("value", value);
		}
		return map;
	}
    
	
	/**	获取sessionKey 用来创建短链接
	* @Title: getSessionkey
	* @return Map<String,String>
	* @author:jackstraw_yu
	*/
	 private  String getSessionkey(String userNickName){
	    String  token = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName);
		if(null!=token&&!"".equals(token)){
			 return token;
		}else{
			UserInfo user = userInfoService.queryUserTokenInfo(userNickName);
			if(user!=null)
				if(null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
					 cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName,user.getAccess_token());
					 return user.getAccess_token(); 
				}
		}
		return null;
	 }
	
    /**
     * Gg
     * 获取shopName
     * @param userId
     * @return
     * Gg
     */
    private String getShopName(String userId){
    	String shopName=null;
    	shopName = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.SHOP_NAME_CACHE, 
    			RedisConstant.RediskeyCacheGroup.SHOP_NAME_KEY+userId);
    	if(shopName!=null && !"".equals(shopName)){
    		return shopName;
    	}else{
    		shopName=  userInfoService.findShopName(userId);
    		if(null!=shopName && !"".equals(shopName)){
    			cacheService.putNoTime(RedisConstant.RedisCacheGroup.SHOP_NAME_CACHE, 
    					RedisConstant.RediskeyCacheGroup.SHOP_NAME_KEY+userId,shopName );
    			return shopName;
    		}
    	}
    	return null;
    }
    
    /** 
    * 返回一个6位数的随机验证码<br/>
    * 所有线程共享一个ThreadLocalRandom实例<br/> 
    * @return String    返回类型 
    * @author jackstraw_yu
    * @date 2017年11月14日 下午5:43:30
    */
    private String produceCode(){
		String code = "";
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for(int i =0;i<6;i++){
			code += random.nextInt(10);
		}
		return code;
	}
	
	/** 
	* 获取用户一天之内使用发送验证码的次数
	* @param  userId
	* @return int   返回类型 
	* @author jackstraw_yu
	* @date 2017年11月15日 下午4:21:19
	*/
	private int getSecurityCodeTop(String userId){
		 //如果用户此次发送验证码的次数超过30次 --不予再次发送
		 String num = redisLockServiceImpl.getStringValue(RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_TOP_KEY+this.SEPARATOR+userId);
		 int times = 0;
		 if(num!=null&&!"".equals(num))
			 try{
				times =  Integer.valueOf(num);
			 }catch (Exception e) {
				times =0;
			 }
		 return times;
	}

	
	/** 
	* 放入用户一天之内使用发送验证码的次数
	* @param  userId  用户id
	* @param  times    使用次数 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月15日 下午3:33:19
	*/
	private void putSecurityCodeTop(String userId,int times){
		redisLockServiceImpl.putStringValueWithExpireTime(
				 RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_TOP_KEY+this.SEPARATOR+userId,
				 times+"",TimeUnit.MILLISECONDS,DateUtils.getMillisOverToday());
	}
	
	
	
	/** 
	* 用戶修改后台设置时校验用户操作
	* @param  userId 用户id
	* @param  newNum 新手机号
	* @param  oldNum 旧手机号
	* @param  code	 验证码
	* @return Map<String,Object>  返回值
	* @author jackstraw_yu
	* @date 2017年11月15日 下午3:37:19
	*/
	private Map<String,Object> validateUserOperate(String userId,String newNum,String oldNum,String code){
		//1取出之前使用手机号存储的验证码
		//2..如果oldNum 不存在 说明走的第一次保存,直接判断验证码是否相等;
		//3..如果存在说明是修改,判断手机号是否被修改过,没有修改该过,直接保存
		//4...............................,修改过,验证验证码是否相等;
		if(oldNum ==null || "".equals(oldNum)){
			if(newNum == null || "".equals(newNum) ){
				return resultMap(false,"手机号不能为空!");
			}else if(!PhoneRegUtils.phoneValidate(newNum)){
				return resultMap(false, "手机号不正确!");
			}
			if(code ==null || "".equals(code)){
				return resultMap(false,"验证码不能为空!");
			}else if( !this.REGEX_CODE.matcher(code).matches()){
				return resultMap(false,"验证码填写错误!");
			}
			String lastCode = redisLockServiceImpl.getStringValue(
					RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY
					+this.SEPARATOR+this.BACK_SIGN+this.SEPARATOR+userId+this.SEPARATOR+newNum);
			if(lastCode==null || "".equals(lastCode)){
				return resultMap(false,"验证码已过期!");
			}else if(!lastCode.equals(code)){
				return resultMap(false,"手机号或者验证码错误!");
			}
		}else{
			if(!PhoneRegUtils.phoneValidate(oldNum)){
				return resultMap(false, "参数错误!");
			}
			if(newNum == null || "".equals(newNum) ){
				return resultMap(false,"参数错误!");
			}else if(!PhoneRegUtils.phoneValidate(newNum)){
				return resultMap(false, "手机号不正确!");
			}
			//有修改手机号
			if(!newNum.equals(oldNum)){
				if(code ==null || "".equals(code)){
					return resultMap(false,"验证码不能为空!");
				}else if( !this.REGEX_CODE.matcher(code).matches()){
					return resultMap(false,"验证码填写错误!");
				}
				String lastCode = redisLockServiceImpl.getStringValue(
						RedisConstant.RediskeyCacheGroup.VALIDATE_CODE_KEY
						+this.SEPARATOR+this.BACK_SIGN+this.SEPARATOR+userId+this.SEPARATOR+newNum);
				if(lastCode==null || "".equals(lastCode)){
					return resultMap(false,"验证码已过期!");
				}else if(!lastCode.equals(code)){
					return resultMap(false,"手机号或者验证码错误!");
				}
			}
		}
		return resultMap(true, "");
	}
	
	/** 
	* 返回结果集
	* @param  status 状态值:true/false
	* @param  message 提示信息
	* @return Map<String,Object>    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月15日 下午3:38:29
	*/
	private Map<String,Object> resultMap(Boolean status,String message){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
}
