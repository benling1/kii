package s2jh.biz.shop.crm.tradecenter.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.util.DateUtils;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;
import s2jh.biz.shop.crm.tradecenter.vo.SmsRecordDTOVo;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.JsonUtil;


@Controller
@RequestMapping(value="/tradeSetup")
public class TradeCenterSendRecordController extends BaseController {

	@Autowired
	private SmsSendRecordService smsSendRecordService;
	@Autowired
	private SmsRecordService smsRecordService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private UserInfoService userInfoService;
	private static final Log logger = LogFactory.getLog(TradeCenterSendRecordController.class);
	
	
	/**
	 * @Description:订单中心短信发送记录查询:
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
				logger.error("订单中心-短信发送记录手机号查询加密失败!");
				key = getSessionkey(nick,false);
				try {
					encrypt = client.encrypt(mobile, EncrptAndDecryptClient.PHONE,key);
				} catch (Exception e1) {
					logger.error("订单中心-短信发送记录手机号查询<>再次<>加密失败!");
				}
			}
		}else{
			try {
				encrypt = client.search(mobile, EncrptAndDecryptClient.PHONE,key).replace("$1$$","").replace("$", "").replace("=", "");
			} catch (Exception e) {
				logger.error("订单中心-短信发送记录手机号模糊查询加密失败!");
				key = getSessionkey(nick,false);
				try {
					encrypt = client.search(mobile, EncrptAndDecryptClient.PHONE,key).replace("$1$$","").replace("$", "").replace("=", "");
				} catch (Exception e1) {
					logger.error("订单中心-短信发送记录手机号模糊查询<>再次<>加密失败!");
				}
			}
		}
		
		return encrypt;
	}
	
	/**
	 * @Description:订单中心短信发送记录查询,昵称加密
	 * @Copy_author jackstraw_yu
	 */
	private String encryptSearchNick(String nick,String userId) {
		EncrptAndDecryptClient client = EncrptAndDecryptClient.getInstance(); 
		String key = getSessionkey(userId,true),encrypt=null;
		try {
			encrypt = client.search(nick, EncrptAndDecryptClient.SEARCH,key);
		} catch (Exception e) {
			logger.error("订单中心-短信发送记录用户昵称模糊查询加密失败!");
			key = getSessionkey(userId,false);
			try {
				encrypt = client.search(nick, EncrptAndDecryptClient.SEARCH,key);
			} catch (Exception e1) {
				logger.error("订单中心-短信发送记录用户昵称模糊查询<>再次<>加密失败!");
			}
		}
		
		return encrypt;
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
	 
	 
	 
	 
	 
	 
	
	 	/**
	 	 * 订单中心所有页面的发送记录------hl
	 	 */
		@RequestMapping(value="/orderCenterSendRecord",produces="text/html;charset=UTF-8")
		@ResponseBody
 		public String orderCenterSendRecord(@RequestBody String params,
				HttpServletRequest request, HttpServletResponse response) {
			String userId = (String) request.getSession().getAttribute(
					"taobao_user_nick");
//			userId = "哈数据库等哈";
			SmsRecordDTOVo srdv = new SmsRecordDTOVo();
			if (null != params && !"".equals(params)) {
				JSONObject jsonVo = new JSONObject(params);
				srdv = JsonUtil.fromJson(jsonVo.getString("params"), SmsRecordDTOVo.class);
			}
			
			Integer pageNo = 1;
			try {
				pageNo = Integer.parseInt(srdv.getPageNo());
				if (pageNo <= 0) {
					pageNo = 1;
				}
			} catch (Exception e) {
				pageNo = 1;
			}
			
			/*封装参数*/
			this.packagingData(srdv, userId);
			
			Pageination<SmsRecordDTO> page = new Pageination<SmsRecordDTO>();
			page.setPageNo(pageNo);
			page.setPageSize(10);
			
			Pageination<SmsRecordDTO> pagination = smsRecordService
					.findSendRecordList(page, srdv, userId);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("smsRecordDTO", pagination.getDatas());
			map.put("totalPage", pagination.getTotalPage());
			map.put("pageNo", pagination.getPageNo());
			return rsMap(100, "操作成功").put("status", true).put("data",map).toJson();
		}


		
		/**
		 * 封装查询参数
		 */
		private void packagingData(SmsRecordDTOVo jsonVo,String userId) {

			if (null != jsonVo.getbTime() && !"".equals(jsonVo.getbTime())) {
				try {
					jsonVo.setBeginTime(DateUtils.stringToLong(jsonVo.getbTime(),
							DateUtils.DEFAULT_TIME_FORMAT));
				} catch (ParseException e) {
				}
			}
			if (null != jsonVo.geteTime() && !"".equals(jsonVo.geteTime())) {
				try {
					jsonVo.setEndTime(DateUtils.stringToLong(jsonVo.geteTime(),
							DateUtils.DEFAULT_TIME_FORMAT));
				} catch (ParseException e) {
				}
			}
			
			if(null != jsonVo.getParameters() && !"".equals(jsonVo.getParameters())){
				if(judgWhetherNumber(jsonVo.getParameters()) && jsonVo.getParameters().length()<=11){
					String phone = encryptSearchMobiles(jsonVo.getParameters(),userId);
					if(jsonVo.getParameters().length()==4){
						jsonVo.setLike(true);
					}
					/*手机号码*/
					jsonVo.setRecNum(phone);
				}
				/*买家昵称*/
				String buyer =encryptSearchNick(jsonVo.getParameters(),userId);
				jsonVo.setBuyerNick(buyer);
				/*订单编号 */
				jsonVo.setOrderId(jsonVo.getParameters());
				
			}
		}
		
		
		
		private boolean judgWhetherNumber(String parameters){
			try {  
	            Long num=Long.parseLong(parameters);
	            return true;//如果是数字，返回True
	        } catch (Exception e) {  
	            return false;//如果抛出异常，返回False   
	        } 
		}
}
