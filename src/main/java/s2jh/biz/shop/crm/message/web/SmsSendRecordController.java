package s2jh.biz.shop.crm.message.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taobao.api.SecretException;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.manage.base.BaseComponent;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;

/**
* @ClassName: SmsSendRecordController
* @Description: (营销中心>>短信发送记录)
* @author:jackstraw_yu
* @date 2017年2月9日
*
*/
@Controller
@RequestMapping(value="/smsSendRecord")
public class SmsSendRecordController extends BaseComponent{
	
	
	@Autowired
	private SmsSendRecordService smsSendRecordService;
	@Autowired
	private SmsRecordService smsRecordService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private UserInfoService userInfoService;
	private static final Log logger = LogFactory.getLog(SmsSendRecordController.class);
	
	
	/** 
	* @Title: smsSendRecord 
	* @Description: (会员短信群发:查看详情) 
	* @date 2017年4月28日 下午4:12:11 
	* @author jackstraw_yu 
	*/
	@RequestMapping(value="/smsSendDetail")
	public void smsSendRecord(HttpServletRequest request,HttpServletResponse response,
							Integer recordId,String type,String mobile,Integer status,
							Pageination<SmsRecordDTO> page){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		boolean like = false;
		if(mobile!=null && !"".equals(mobile.trim())){
			mobile = mobile.trim();
			if(mobile.length()==4)
				like =true;
			mobile=encryptSearchMobiles(mobile.trim(),userId);
		}else{
			mobile=null;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("recordId", recordId);
		map.put("userId", userId);
		map.put("type", type);
		map.put("mobile", mobile);
		map.put("status", status);
		map.put("like", like);
		long s1 = System.currentTimeMillis();
		//MyPagination pagination  = smsSendRecordService.smsSendPagination(pageNo,map);
		//String aPagination = gson.toJson(pagination);
		page = smsRecordService.findSmsRecordPageList(map,page,userId);
		//解密手机号
		decryptSmsData(page,userId);
		logger.info("查询短信群发详情耗时"+(System.currentTimeMillis()-s1)+"^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		String aPagination = gson.toJson(page);
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(aPagination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * @Description:会员短信群发发送发送记录查询:
	 * 入参手机号长度大于4则直接加密
	 * 小于等于4模糊加密
	 * @author jackstraw_yu
	 */
	private String encryptSearchMobiles(String mobile,String nick) {
		EncrptAndDecryptClient client = EncrptAndDecryptClient.getInstance(); 
		String key = getSessionkey(nick,true),encrypt=null;
		if(mobile.length()!=4){
			try {
				encrypt = client.encrypt(mobile, EncrptAndDecryptClient.PHONE,key);
			} catch (Exception e) {
				logger.error("短信发送记录手机号查询加密失败!");
				key = getSessionkey(nick,false);
				try {
					encrypt = client.encrypt(mobile, EncrptAndDecryptClient.PHONE,key);
				} catch (Exception e1) {
					logger.error("短信发送记录手机号查询<>再次<>加密失败!");
				}
			}
		}else{
			try {
				encrypt = client.search(mobile, EncrptAndDecryptClient.PHONE,key).replace("$1$$","").replace("$", "").replace("=", "");
			} catch (Exception e) {
				logger.error("短信发送记录手机号模糊查询加密失败!");
				key = getSessionkey(nick,false);
				try {
					encrypt = client.search(mobile, EncrptAndDecryptClient.PHONE,key).replace("$1$$","").replace("$", "").replace("=", "");
				} catch (Exception e1) {
					logger.error("短信发送记录手机号模糊查询<>再次<>加密失败!");
				}
			}
		}
		
		return encrypt;
	}
	
	
	/**
	 * @Description:短信查询后的解密
	 * @author jackstraw_yu
	 */
	private void decryptSmsData(Pageination<SmsRecordDTO> page,String nick) {
		if(page.getDatas()!=null && !page.getDatas().isEmpty()){
			EncrptAndDecryptClient client = EncrptAndDecryptClient.getInstance(); 
			String key = getSessionkey(nick,true);
			for (SmsRecordDTO sms : page.getDatas()){
				if(sms.getRecNum()!=null && !"".equals(sms.getRecNum()))
					try {
						if(EncrptAndDecryptClient.isEncryptData(sms.getRecNum(), EncrptAndDecryptClient.PHONE))
							try {
								sms.setRecNum(client.decrypt(sms.getRecNum(), EncrptAndDecryptClient.PHONE,key));
							} catch (Exception e) {
								logger.error("短信发送记录详情手机号解密失败!");
								key = getSessionkey(nick,false);
								try {
									sms.setRecNum(client.decrypt(sms.getRecNum(), EncrptAndDecryptClient.PHONE,key));
								} catch (Exception e1) {
									logger.error("短信发送记录详情手机号解密<>再次<>失败!");
									//break;
								}
								continue;
							}
					} catch (Exception es) {
						logger.error("短信发送记录详情判断手机号是否加密失败!");
					}
			}	
		}
	}
	
	
	/**
	 * @Description:获取加密解密使用 的token
	 * @Copy_author jackstraw_yu
	 */
	 private  String getSessionkey(String userNickName,boolean flag){
		    String  token = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName);
			if(null!=token&&!"".equals(token)&&flag){
				 return token;
			}else{
				UserInfo user = userInfoService.queryUserTokenInfo(userNickName);
				if(user!=null)
					if(null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
						 cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName,user.getAccess_token());
						 return user.getAccess_token(); 
					}
			}
			return "";
	   }
	
	
//	/** 
//	* @Title: getEveryCount 
//	* @Description:  (短息发送记录>>统计详情) 
//	* @date 2017年4月28日 下午5:48:38 
//	* @author jackstraw_yu 
//	*/
//	@SuppressWarnings({ "unchecked", "unused" })
//	//@RequestMapping(value="/getEveryCount")
//	@Deprecated
//	public void getEveryCount(Model model,HttpServletRequest request,HttpServletResponse response,
//			Integer recordId,String type){
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		//String userId="crazyboy";
//		JSONObject js = new JSONObject();
//		/**
//		 * 1目标发送客户总数
//		 * 2成功发送客户数
//		 * 3计费条数
//		 * 4手机号码不正确
//		 * 5重复号码条数
//		 * 6黑名单
//		 * 7重复发送被屏蔽
//		 * 8其他失败原因(短息接口返回失败)
//		 * */
//		//1目标发送客户总数---可查询msgSendRecord表
//		Integer totalCustom = smsSendRecordService.getTotalCustom(userId,recordId,type);
//		js.put("totalCustom", totalCustom);
//		//2成功发送客户数
//		Integer successCustom = smsSendRecordService.getSuccessCustom(userId,recordId,type);
//		js.put("successCustom", successCustom);
//		//3计费条数
//		Integer actualCount = smsSendRecordService.getActualCount(userId,recordId,type);
//		js.put("actualCount", actualCount);
//		//4手机号码不正确
//		Integer wrongNum = smsSendRecordService.getWrongNum(userId,recordId,type);
//		js.put("wrongNum", wrongNum);
//		//5重复号码条数
//		Integer repeatNum = smsSendRecordService.getRepeatNum(userId,recordId,type);
//		js.put("repeatNum", repeatNum);
//		//6黑名单
//		Integer blackCount = smsSendRecordService.getBlackCount(userId,recordId,type);
//		js.put("blackCount", blackCount);
//		//7重复发送被屏蔽
//		Integer repeatSend = smsSendRecordService.getRepeatSend(userId,recordId,type);
//		js.put("repeatSend", repeatSend);
//		//8其他失败原因(短息接口返回失败)
//		Integer failedCount = smsSendRecordService.getFailedCount(userId,recordId,type);
//		js.put("failedCount", failedCount);
//		response.setContentType("application/json;charset=UTF-8");
//		try {
//			response.getWriter().write(js.toJSONString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
}
