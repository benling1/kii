package s2jh.biz.shop.crm.taobao.service.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.SecretException;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.SendMessageStatusInfo;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
/**
 * wy 2017-04-06  判断用户处理类
 * @author zhrt2
 *
 */
@Component
public class JudgeUserUtil {
	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserAccountService userAccountService;
	/**
	 * 获取正常用户 非黑名单未过期且未校验短信余额的用户对象（订单结束专用--自动评价不需要校验短信余额）
	 * @author: wy
	 * @time: 2017年9月6日 上午11:02:40
	 * @param sellerName 卖家昵称
	 * @return
	 */
	public UserInfo  findUserExpireTimeAndSms(String sellerName){
		UserInfo user = this.myBatisDao.findBy(UserInfo.class.getName(), "findExpireTimeAndSms", sellerName);
		if(user==null)
			return user;
		if(user.getStatus()==null)
			return null;
		if(user.getStatus()!=0)
			return null;
		if(user.getExpirationTime()==null)
			return null;
		if(user.getExpirationTime().getTime()<System.currentTimeMillis())
			return null;
		user.setTaobaoUserNick(sellerName);
		long userSms = this.userAccountService.findUserAccountSms(sellerName);
        user.setUserAccountSms(userSms);
		return user;
	}
	/**
	 * 判断用户是否是过期用户或者短信余额是否不足
	 * @param sellerName
	 * @return true 用户是正常用户且短信语言大于0
	 */
	public  UserInfo isNormalUser(String sellerName){
		if(ValidateUtil.isEmpty(sellerName))
			return null;
		UserInfo user = myBatisDao.findBy(UserInfo.class.getName(), "findExpireTimeAndSms", sellerName);
		if(user==null)
			return user;
		if(user.getExpirationTime()==null)
			return null;
		if(user.getExpirationTime().getTime()<System.currentTimeMillis())
			return null;
		if(user.getStatus()==null)
			return null;
		if(user.getStatus()!=0)
			return null;
		long userSms = this.userAccountService.findUserAccountSms(sellerName);
		if(userSms<=0){
		    this.userInfoService.doTmcUser(sellerName, SendMessageStatusInfo.DEL_SMS);
		    return null;
		}
		user.setTaobaoUserNick(sellerName);
		user.setUserAccountSms(userSms);
		return user;
	}
	/**
	 * 判断用户是否开启当前状态的判断
	 * @param sellerName
	 * @param orderSetupStatus
	 * @return
	 */
	public  OrderSetup isOrderSetupOpen(String sellerName,String orderSetupStatus){
		Map<String,Object> map = new HashMap<>();
		map.put("sellerName", sellerName);
		map.put("settingType", orderSetupStatus);
		OrderSetup orderSetup = myBatisDao.findBy(OrderSetup.class.getName(), "findOrderSetupByUserIdAndSettingTypeSend", map);
		if(orderSetup !=null && ValidateUtil.isNotNull(orderSetup.getStatus())){
			if(OrderSettingInfo.ORDER_SETUP_OPEN.equals(orderSetup.getStatus())){
				return orderSetup;
			}
		}
		return null;
	}
	/**
	 * 通过查询MySQL获取用户的sessionKey
	 * @param sellerName 用户昵称
	 */
	public  String getUserTokenByMySQL(String sellerName){
		if(ValidateUtil.isEmpty(sellerName))
			return null;
		UserInfo user = userInfoService.queryUserTokenInfo(sellerName);
		if(user==null)
			return null;
		if(null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
			 return user.getAccess_token(); 
		}
		return null;
	}
	/**
	 * 获取卖家的sessionKey
	 * @author: wy
	 * @time: 2017年7月26日 下午5:47:16
	 * @param sellerName 卖家昵称
	 * @return 用户的sessionKey，有可能出现空
	 */
	public  String getUserTokenByRedis(String sellerName){
		if(ValidateUtil.isEmpty(sellerName))
			return null;
		String token = null;
		try {
			token = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+sellerName);
			if(ValidateUtil.isEmpty(token))
				return getUserTokenByMySQL(sellerName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	/**
	 * 获取解密后的数据
	 * @author: wy
	 * @time: 2017年7月27日 上午11:00:40
	 * @param oldData 原始数据，不可以为空
	 * @param type 要解密的类型，不可以空
	 * @param sellerNick 卖家昵称（昵称秘钥二选一）
	 * @param sessionKey 卖家秘钥（昵称秘钥二选一）
	 * @return 
	 * @throws SecretException
	 */
	public String getDecryptData(String oldData,String type,String sellerNick,String sessionKey) throws SecretException{
		if(ValidateUtil.isEmpty(oldData) || ValidateUtil.isEmpty(type) || !EncrptAndDecryptClient.isEncryptData(oldData, type))
			return oldData;
		if(ValidateUtil.isNotNull(sessionKey))
			return EncrptAndDecryptClient.getInstance().decrypt(oldData,type,sessionKey);
		if(ValidateUtil.isNotNull(sellerNick))
			return EncrptAndDecryptClient.getInstance().decrypt(oldData,type,getUserTokenByRedis(sellerNick));
		return oldData;
	}
}
