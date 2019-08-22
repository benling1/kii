package s2jh.biz.shop.crm.user.service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.other.entity.MobileSetting;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.info.SendMessageStatusInfo;
import s2jh.biz.shop.crm.taobao.info.TmcInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.tradecenter.service.TradeSetupService;
import s2jh.biz.shop.crm.user.dao.UserInfoDao;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.dao.UserRechargeDao;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.entity.UserRecharge;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.GetUserLevel;
import s2jh.biz.shop.utils.SpringContextUtil;

import com.taobao.api.ApiException;
import com.taobao.api.domain.TmcUser;
import com.taobao.api.request.TmcUserGetRequest;
import com.taobao.api.request.TmcUserPermitRequest;
import com.taobao.api.response.TmcUserGetResponse;

/**
 * @ClassName: UserInfoService
 * @author:jackstraw_yu
 * @date 2016年12月21日
 *
 */
@Service
public class UserInfoService extends BaseService<UserInfo, Long> {

	@Autowired
	private UserInfoDao userDao;
	
	@Autowired
	private UserRechargeService userRechargeService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
    private JudgeUserUtil judgeUsetUtil;
	
	@Autowired
    private TradeSetupService tradeSetupService;
	
	@Autowired
	private MobileSettingService mobileSettingService;
	
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Resource(name="mongoTemplate")
    protected MongoTemplate mongoTemplate;  

	@Resource(name = "redisTemplateLock")
	private StringRedisTemplate redisTemplate;

	@Override
	protected BaseDao<UserInfo, Long> getEntityDao() {
		return userDao;
	}
	
	private  Logger logger = LoggerFactory.getLogger(UserInfoService.class);

	/**
	 * 查询用户是否存在
	 * @author: wy
	 * @time: 2017年11月13日 下午6:14:23
	 * @param sellerNick 买家昵称
	 * @return 存在返回true，不成功返回false
	 */
	public Long getUserInfoId(String sellerNick){
	    Long id = this.myBatisDao.findBy(UserInfo.class.getName(), "findIdByCreateAccount", sellerNick);
        return id;
	}
	/**
	 * 根据用户昵称查询用户信息
	 */
	public UserInfo findUserInfo(String taobaoUserNick) {
		Logger logger = org.slf4j.LoggerFactory
				.getLogger(UserInfoService.class);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
				+ formatter.format(new Date())
				+ "<<<<<<<<<<<<<<<用户："+taobaoUserNick+"<<<<<<<<<<<<<<<<<<<创建Map<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
				+ formatter.format(new Date())
				+ "<<<<<<<<<<<<<<<用户："+taobaoUserNick+"<<<<<<<<<<<<<<<<<<<将用户添加到Map中<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		map.put("taobao_user_nick", taobaoUserNick);
		logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
				+ formatter.format(new Date())
				+ "<<<<<<<<<<<<<<<用户："+taobaoUserNick+"<<<<<<<<<<<<<<<<<<<开始根据用户昵称查询数据库<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		UserInfo user = myBatisDao.findBy(UserInfo.class.getName(),
				"findUserInfo", map);
		logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
				+ formatter.format(new Date())
				+ "<<<<<<<<<<<<<<<用户："+taobaoUserNick+"<<<<<<<<<<<<<<<<<<<查询完成，返回用户对象！<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return user;
	}

	/**
	 * 添加用户信息 
	 */
	public int addUserInfo(String sellerNick, String taoBaoUserId,
            String sessionKey, Date sellerExpireTime, String expiresIn) {
	    UserInfo user = new UserInfo();
	    user.setTaobaoUserNick(sellerNick);
	    user.setCreateTime(new Date());
	    user.setLastLoginDate(new Date());
	    user.setStatus(0);
	    user.setTaobaoUserId(taoBaoUserId);
	    user.setAccess_token(sessionKey);
	    user.setExpirationTime(sellerExpireTime);
	    user.setExpires_in(expiresIn);
	    user.setLevel(GetUserLevel.getSingleUserLevel(sessionKey));
		int data = myBatisDao.execute(UserInfo.class.getName(), "addUserInfo",
				user);
		this.userAccountService.doCreateUserAccountByUser(user.getTaobaoUserNick(), 0);
		return data;
	}
	

	/**
	 * @Title: updateUserInfo
	 * @Description: (买家充值成功后,修改买家的短信余额)
	 * @param @param user 参数
	 * @return void 返回类型
	 * @author:jackstraw_yu
	 * @throws
	 */
	@Deprecated
	public void updateUserInfo(UserRecharge userRecharge) {
		// 修改用户短信余额
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taobaoUserNick", userRecharge.getUserNick());
		map.put("smsNum", userRecharge.getRechargeNUm());
		logger.info("*********************************充值用户及条数："+map.toString());
		// 添加操作日志(使用一个对象时第一条数据会被覆盖)
		UserOperationLog userOperationLog = new UserOperationLog();
		UserOperationLog userOperationLog2 = new UserOperationLog();
		userOperationLog.setUserId("系统管理员");
		userOperationLog.setCreatedDate(new Date());
		userOperationLog.setDate(new Date());

		// 使用BeanUtil时需要try catch
		userOperationLog2.setUserId("系统管理员");
		userOperationLog2.setCreatedDate(new Date());
		userOperationLog2.setDate(new Date());

		UserOperationLogDao userOperationLogDao = SpringContextUtil
				.getBean("userOperationLogDao");
		UserRechargeDao userRechargeDao = SpringContextUtil
				.getBean("userRechargeDao");

		try {

			//MyBatisDao myBatisDao = SpringContextUtil.getBean("myBatisDao");
			//a.1充值首先获取用户的后台管理的余额提醒的数量
			//Integer messageCount = myBatisDao.findBy(MobileSetting.class.getName(), "getMessageCountByUserId", map);
			//a.2用户当前的剩余短信
			//Long smsNum = this.userAccountService.findUserAccountSms(userRecharge.getUserNick());
			
			// 1,修改用户表的余额数
			this.userAccountService.doUpdateUserSms(userRecharge.getUserNick(), SendMessageStatusInfo.ADD_SMS, 
			        userRecharge.getRechargeNUm(), "短信套餐购买", userRecharge.getUserNick(), null, "淘宝服务短信套餐充值，短信数量："+userRecharge.getRechargeNUm(),UserAccountService.NO_TIME);
			
			//a.3如果充值数量+当前剩余量>=设定的余额提醒的数量,充值flag=true&&startTime=null&&endTime=null
			//先充值,后重置用户设置
			/*if(messageCount != null){
				if((smsNum+=userRecharge.getRechargeNUm())>=messageCount){
					myBatisDao.execute(MobileSetting.class.getName(), "resetFlagByUserId", map);
				}
			}*/
			
			// 充值成功
			userRecharge.setStatus("1");

			// 1.1添加日志
			userOperationLog2.setType("修改");
			userOperationLog2.setRemark("修改用户短信余额");
			userOperationLog2.setFunctions("修改用户短信余额");
			// 判断删除操作是否成功或失败
			userOperationLog2.setState("成功");

			// 2.1添加日志
			userOperationLog.setType("添加");
			userOperationLog.setRemark("添加充值记录");
			userOperationLog.setFunctions("充值中心");
			// 判断删除操作是否成功或失败
			userOperationLog.setState("成功");

		} catch (Exception e) {
			// 充值失败
			userRecharge.setStatus("2");

			userOperationLog.setState("失败");
			userOperationLog2.setState("失败");
			e.printStackTrace();
		} finally {
		    if("1".equals(userRecharge.getStatus())){
		        this.addUserPermitByMySql(userRecharge.getUserNick(), null);
		    }
			try {
				// 保存日志
				userOperationLogDao.save(userOperationLog);
				userOperationLogDao.save(userOperationLog2);
				
				if(null != userRecharge.getId()){
					//id存在就修改充值状态和时间
					userRechargeService.updateUserRechargeStatus(userRecharge);
				}else{
					// 2,在充值记录中添加一条充值记录
					userRechargeDao.save(userRecharge);
				}
				
			} catch (Exception e2) {
			    e2.printStackTrace();
			}
		}

	}
	
	
	/** 
	* @Description: 用户充值
	* @param  userRecharge    充值信息实体 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年12月4日 下午6:15:38
	*/
	public void doUserRecharge(UserRecharge userRecharge) {
		logger.info("################################### 有充值进来,用户名称 : "
					+userRecharge.getUserNick()+";充值条数 : "+ userRecharge.getRechargeNUm());
		// 1,修改用户表的余额数
		boolean excute = this.userAccountService.doUpdateUserSms(userRecharge.getUserNick(), SendMessageStatusInfo.ADD_SMS, 
			        			userRecharge.getRechargeNUm(), "短信套餐购买", userRecharge.getUserNick(), null, "淘宝服务短信套餐充值，短信数量："+userRecharge.getRechargeNUm(),UserAccountService.NO_TIME);
		if(excute){// 充值成功
			userRecharge.setStatus("1");
			this.addUserPermitByMySql(userRecharge.getUserNick(), null);
		}else{// 充值失败
			userRecharge.setStatus("2");
		}
		//保存充值记录
		userRechargeService.saveUserRechargeRecord(userRecharge);
	}
	
	
	
	
	
	

	/**
	 * 根据用户昵称更新用户的access_token
	 * 
	 * @param map
	 */
	public void updateUserInfo(String sellerNick,String sessionKey, Date sellerExpireTime, String expiresIn) {
	    Map<String, Object> map = new HashMap<String, Object>(6);
        map.put("access_token", sessionKey);
        map.put("taobao_user_nick", sellerNick);
        map.put("expiration_time", sellerExpireTime);
        map.put("expires_in", expiresIn);
		myBatisDao.execute(UserInfo.class.getName(), "updateUserToken", map);
	}

	

	
	/** 
	* @Description: 根据用户昵称查询用户信息
	* @param  taobao_user_nick
	* @return UserInfo    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月27日 下午2:13:34
	*/
	public UserInfo queryUserInfoByNick(String userNick) {
		return myBatisDao.findBy(UserInfo.class.getName(), "queryUserInfoByNick", userNick);
	}

	
	//查询当前用户短信剩余
	public Integer findUserInfoSmsNum(String taobaoUserNick){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("taobaoUserNick", taobaoUserNick);
		return myBatisDao.findBy("s2jh.biz.shop.crm.user.entity.UserInfo", "findUserInfoSmsNum", map);
	}
	
	//查询当前用户邮件剩余
	public Integer findUserInfoEmailSum(String taobaoUserNick){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("taobaoUserNick", taobaoUserNick);
		return myBatisDao.findBy("s2jh.biz.shop.crm.user.entity.UserInfo", "findUserInfoEmailNum", map);
		
	}
	
	//查询当前用户软件到期时间
	public Date findUserInfoDq(String taobaoUserNick){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("taobaoUserNick", taobaoUserNick);
		return myBatisDao.findBy("s2jh.biz.shop.crm.user.entity.UserInfo", "findUserInfoDq", map);
	}

	/**
	 * 查询用户access_token数据
	 * helei 2017年1月10日下午6:35:41
	 */
	public List<UserInfo> findUserInfoToken(){
		Map<String,Object> map = new HashMap<String, Object>();
		List<UserInfo> userInfoList = myBatisDao.findList(UserInfo.class.getName(), "findUserInfoToken", map);
		return userInfoList;
	}

	
	/**
	 * @Title: queryUserInfo
	 * @Description:(通过userId查询出用户)
	 * @param  userId 用户昵称
	 * @return String    返回类型
	 * @author:jackstraw_yu
	 */
	public UserInfo queryUserTokenInfo(String userId) {
		return myBatisDao.findBy(UserInfo.class.getName(), "queryTokenInfo", userId);
	}

	
//	/**
//	 * @Title: queryActiveUserInfos
//	 * @Description: (通过用户昵称列表查询出用户对象集合/list为空是默认查询全部用户对象集合)
//	 * @param      参数
//	 * @return List<UserInfo>    返回类型
//	 * @author:jackstraw_yu
//	 * @throws
//	 */
//	public List<UserInfo> queryActiveUserInfos(List<String> userIds) {
//		Map<String, Object> hashMap = new HashMap<String,Object>();
//		hashMap.put("userIds", userIds);
//		return myBatisDao.findList(UserInfo.class.getName(), "findActiveUserList", hashMap);
//	}
	
	
	/**
	 * 通过taobaoUserNick获取token
	 * helei 2017年3月17日下午3:00:33
	 */
	public String findUserInfoTokens(String taobaoUserNick) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taobaoUserNick", taobaoUserNick);
		String accessToken = myBatisDao.findBy(UserInfo.class.getName(), "findUserInfoTokens", map);
	   return accessToken;
	}
	/**
	 * 充值给用户添加授权，扣费时校验如果余额为0取消用户授权
	 * @author: wy
	 * @time: 2017年10月10日 下午12:00:24
	 * @param sellerNick 卖家昵称
	 */
	public void doTmcUser(final String sellerNick,final boolean isDelete){
	    try {
            MyFixedThreadPool.getMyFixedThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    doTmcUserReal(sellerNick, isDelete);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	/**
     * 充值给用户添加授权，扣费时校验如果余额为0取消用户授权
     * @author: wy
     * @time: 2017年10月10日 下午12:00:24
     * @param sellerNick 卖家昵称
     */
	private void doTmcUserReal(String sellerNick,boolean isDelete){
	    if(isDelete){
            long smsNum = this.userAccountService.findUserAccountSms(sellerNick);
            if(smsNum<=0){
                String sessionKey = this.judgeUsetUtil.getUserTokenByRedis(sellerNick);
                try {
					if(this.tradeSetupService.findAutoRateExists(sellerNick)){
						this.openUserPermit(sessionKey, TmcInfo.TRADE_SUCCESS_TOPIC);
					}else{
						this.openUserPermit(sessionKey, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					this.openUserPermit(sessionKey, null);
				}
            }
        }else {
            this.addUserPermitByMySql(sellerNick, null);
        }
	}
	/**
	 * 根据用户名修改用户的店铺名称（短信签名）
	 * @param map
	 * @return
	 */
	public int updateShopName(Map<String,Object> map){
		int x = myBatisDao.execute(UserInfo.class.getName(), "saveShopName", map);
		return x;
	}
	
	/**
	 * 查询用户sessionKey，若为空再次查询
	 * ZTK2017年8月1日下午3:30:22
	 */
	public String validateFindSessionKey(String userId){
		//查询用户sessionKey
		String sessionKey = findUserInfoTokens(userId);
		//使用该accessToken查询用户表是否过期，如果过期就通过userInfoId查询信的accessToken;
		/*UserInfo userIn = myBatisDao.findBy(UserInfo.class.getName(), "findUserInfoBytoken", sessionKey);
		if(userIn != null ){
		}else{
			sessionKey = findUserInfoTokens(userId);
		}*/
		return sessionKey;
	}

	/**
	* @Title: queryShoNname
	* @Description: (通过用户名称查询店铺名称)
	* @return String    返回类型
	* @author:jackstraw_yu
	*/
	public String queryShopName(String userId) {
		return myBatisDao.findBy(UserInfo.class.getName(), "queryShopName", userId);
	}
	
	/**
	 * Gg
	 * 后台管理--短信签名回显
	 * @param userId
	 * @return
	 * Gg
	 */
	public String findShopName(String userId){
		return myBatisDao.findBy(UserInfo.class.getName(), "findShopName", userId);
	}
	
	/**
	 * Gg
	 * 后台管理---修改短信签名
	 * @param vo
	 * Gg
	 */
	public void modiftyShopName(String userId,String shopName){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("shopName", shopName);
		myBatisDao.execute(UserInfo.class.getName(), "modiftyShopName", map);
	}
	
	/**
	 * 查询出要发送短信的用户总量
	 * 用户没有过期,手机号码不能为空
	 * @return
	 */
	@Deprecated
	public Long findAllUserCount() {
		return myBatisDao.findBy(UserInfo.class.getName(), "findAllUserCount",null);
	}

	
	/**
	 * 分页查询用户 发送短信
	 * 用户没有过期,手机号不能为空
	 * @param map
	 * @return
	 */
	@Deprecated
	public List<UserInfo> findAllUserList(Map<String, Object> map) {
		return myBatisDao.findList(UserInfo.class.getName(), "findAllUserList",map);
	}
	/**
	 * 通过用户user_id,未过期,sessionKey不为空 判断用户是否存在
	 * 存在:true;不存在false
	 * @param map
	 * @return
	 */
	@Deprecated
	public boolean findUserInUse(String userId) {
		UserInfo  user = myBatisDao.findBy(UserInfo.class.getName(), "findUserInUse",userId);
		return user==null?false:true;
	}
	
	/**
     * 删除用户的消息监听
     * @author: wy
     * @time: 2017年8月23日 下午4:01:18
     * @param sellerNick 消息昵称
     * @param delType 要删除的类型（1--下单关怀）
     * @return 
     */
    public boolean removeUserPermitByTaoBao(String sellerNick,String delType){
        return this.addUserPermitByMySql(sellerNick, null);
    }
    /**
     * 给tmc用户授权，根据之前添加过的消息来添加消息，如果查询不到则会根据数据库中的设置来添加要监听的tmc消息
     * @author: wy
     * @time: 2017年8月23日 下午3:45:14
     * @param sellerNick 卖家昵称
     * @param addType 新增的消息
     */
    public boolean addUserPermitByTaoBao(String sellerNick,String addType){
        if(ValidateUtil.isEmpty(sellerNick)){
            return false;
        }
        List<String> list = this.getPermitsByTaoBao(sellerNick);
        if(ValidateUtil.isEmpty(list)){
            return this.addUserPermitByMySql(sellerNick,addType);
        }
        Set<String> set = new HashSet<String>(list);
        if(ValidateUtil.isNotNull(addType)){
            String addTopic = this.getTopicBySettingType(addType);
            if(ValidateUtil.isNotNull(addTopic)){
                set.add(addTopic);
            }
        }
        String sessionKey = this.judgeUsetUtil.getUserTokenByRedis(sellerNick);
        String topics = this.getCommaStringByCollection(set);
        return this.openUserPermit(sessionKey, topics);
    }
    /**
     * 给用户授权，通过查询数据库来添加要接收的tmc消息
     * @author: wy
     * @time: 2017年8月23日 下午3:43:58
     * @param sellerNick 卖家昵称
     * @param addType 要新添加的消息类型（如1--下单关怀），可以为空，为空则根据取出来的值添加消息
     */
    public boolean addUserPermitByMySql(String sellerNick,String addType){
        if(ValidateUtil.isEmpty(sellerNick)){
            return false;
        }
        List<String> list = this.tradeSetupService.findTypeBySellerNickTmc(sellerNick);
        Set<String> set = new HashSet<String>(28);
        if(ValidateUtil.isNotNull(list)){
            for (String string : list) {
                String s = this.getTopicBySettingType(string);
                if(s!=null){
                    set.add(s);
                }
            }
        }
        if(ValidateUtil.isNotNull(addType)){
            String addTopic = this.getTopicBySettingType(addType);
            if(ValidateUtil.isNotNull(addTopic)){
                set.add(addTopic);
            }
        }
        set.add(TmcInfo.FUWU_ORDERPAID_TOPIC);
        set.add(TmcInfo.FUWU_SERVICE_OPEN_TOPIC);
        String topics = this.getCommaStringByCollection(set);
        String sessionKey = this.judgeUsetUtil.getUserTokenByRedis(sellerNick);
        return this.openUserPermit(sessionKey, topics);
    }
    /**
     * 获取用户曾经授权过的消息
     * @author: wy
     * @time: 2017年8月23日 下午3:15:40
     * @param sellerNick 卖家昵称
     * @return list字符串集合
     */
    public List<String> getPermitsByTaoBao(String sellerNick) {
        if(ValidateUtil.isEmpty(sellerNick)){
            return null;
        }
         try {
            TmcUserGetRequest req = new TmcUserGetRequest();
             req.setFields("user_nick,topics,user_id,is_valid,created,modified");
             req.setNick(sellerNick);
             TmcUserGetResponse rsp = TaoBaoClientUtil.TAOBAO_CLIENT.execute(req);
             TmcUser tmc =  rsp.getTmcUser();
             if(tmc!=null){
                 List<String> list = tmc.getTopics();
                 if(ValidateUtil.isEmpty(list)){
                     return null;
                 }
                 return list;
             }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 根据用户的sessionKey和具体的Topic来为用户开启消息
     * @author: wy
     * @time: 2017年8月23日 下午2:59:26
     * @param sessionKey 用户的秘钥
     * @param topics 要为用户授权的消息，如果消息为空，则会强制加上服务开通和服务支付两个消息
     * @return
     */
    public boolean openUserPermit(String sessionKey,String topics){
        if(ValidateUtil.isEmpty(sessionKey)){
            return false;
        }
        if(ValidateUtil.isEmpty(topics)){
            topics = "";
        }
        if(!topics.contains(TmcInfo.FUWU_SERVICE_OPEN_TOPIC)){
            topics = topics +","+TmcInfo.FUWU_SERVICE_OPEN_TOPIC;
        }
        if(!topics.contains(TmcInfo.FUWU_ORDERPAID_TOPIC)){
            topics = topics +","+TmcInfo.FUWU_ORDERPAID_TOPIC;
        }
        if(topics.startsWith(",")){
            topics = topics.substring(1);
        }
        TmcUserPermitRequest req = new TmcUserPermitRequest();
        req.setTopics(topics);
        try {
            TaoBaoClientUtil.TAOBAO_CLIENT.execute(req, sessionKey);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    } 
    /**
     * 将字符串集合转为由英文逗号连接的字符串
     * @author: wy
     * @time: 2017年8月23日 下午2:43:47
     * @param c
     * @return
     */
    public String getCommaStringByCollection(Collection<? extends String> c){
        if(c == null || c.size()==0){
            return null;
        }
        StringBuffer s = new StringBuffer();
        int i = 0;
        for (String string : c) {
            if(string==null){
                continue;
            }
            if(i==0){
                s.append(string);
                i++;
            }else{
                s.append(",").append(string);
            }
        }
        return s.toString();
    }
    /**
     * 通过对应的状态取得对应的淘宝TMC消息TOPIC,不提供服务开通付款的消息返回
     * @author: wy
     * @time: 2017年8月23日 下午2:40:56
     * @param type
     * @return
     */
    public String getTopicBySettingType(String type){
        if(ValidateUtil.isEmpty(type)){
            return null;
        }
        switch (type) {
        case "1":{ //1-下单关怀 
            return TmcInfo.TRADE_CREATE_TOPIC;
        }
        case "2":{ //2-常规催付 
            return TmcInfo.TRADE_CREATE_TOPIC;
        }
        case "3":{ //3-二次催付
            return TmcInfo.TRADE_CREATE_TOPIC;
        }
        case "4":{ //4-聚划算催付
            return TmcInfo.TRADE_CREATE_TOPIC;
        }
        case "6":{ //6-发货提醒 
            return TmcInfo.LOGSTIC_DETAIL_TOPIC;
        }
        case "7":{ //7-到达同城提醒
            return TmcInfo.LOGSTIC_DETAIL_TOPIC;
        }
        case "8":{ //8-派件提醒
            return TmcInfo.LOGSTIC_DETAIL_TOPIC;
        }
        case "9":{ //9-签收提醒
            return TmcInfo.LOGSTIC_DETAIL_TOPIC;
        }
        case "11":{ //11-延时发货提醒
            return TmcInfo.TRADE_BUYERPAY_TOPIC;
        }
        case "12":{ //12-宝贝关怀
            return TmcInfo.LOGSTIC_DETAIL_TOPIC;
        }
        case "13":{ //13-付款关怀 
            return TmcInfo.TRADE_BUYERPAY_TOPIC;
        }
        case "14":{ //14-回款提醒
            return TmcInfo.LOGSTIC_DETAIL_TOPIC;
        }
        case "16":{ //16-自动评价
            return TmcInfo.TRADE_SUCCESS_TOPIC;
        }
        case "29":{ //29-买家申请退款
            return TmcInfo.REFUND_CREATED_TOPIC;
        }
        case "30":{ //30-退款成功
            return TmcInfo.REFUND_SUCCESS_TOPIC;
        }
        case "31":{ // 31-等待退货 
            return TmcInfo.REFUND_AGREE_TOPIC;
        }
        case "32":{ //32-拒绝退款
            return TmcInfo.REFUND_REFUSE_TOPIC;
        }
        case "37":{ //37-好评提醒
            return TmcInfo.TRADE_SUCCESS_TOPIC;
        }
        default:
            return null;
        }
    }
    
    /**
     * 根据最后登录时间查询所有的用户，同步发送记录的taskName为taskId
     * ztk2017年11月2日下午5:16:11
     */
    public List<String> findAllUserByLoginTime(){
    	List<String> userList = myBatisDao.findList(UserInfo.class.getName(), "findAllUserByLoginTime", "2017-10-11 00:00:00");
    	return userList;
    }
    
    
    /**
      * @Description: 批量修改用户的淘宝等级
      * @author Mr.H
      * @date 2017年12月4日 下午4:03:25
     */
	public Integer batchUpdateUserLevel(List<UserInfo> list) {
		int i = 0;
		try {
			i = myBatisDao.execute(UserInfo.class.getName(), "batchUpdateUserLevel", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
    
    
    /** 
    * @Description: 首页小红包标记为不显示状态
    * @param  userId
    * @param  hasProvide   
    * @return void    返回类型 
    * @author jackstraw_yu
    * @date 2017年11月28日 上午9:29:22
    */
    @Transactional
    public void updateUserHasProvide(String userId, boolean hasProvide) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("hasProvide", hasProvide);
		int execute = myBatisDao.execute(UserInfo.class.getName(), "updateUserHasProvide", map);
		if(execute != 1){
			throw new RuntimeException("影响的行数不为1! :"+userId);	
		}
	}
    
    
	/** 
	* @Description:首页小红包:
	* 1,保存用户填写的手机号,qq号<br/>
	* 2,保存一条初始化后台设置<br/>
	* 3,赠送用户500条短信
	* @param  mobile
	* @param  qqNum
	* @param  userId    设定文件 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月27日 下午3:20:32
	*/
    @Transactional
	public void saveUserMobileAndInitSetting(String mobile, String qqNum, String userId) {
    	//1,保存用户填写的手机号,qq号,标记赠送
    	boolean hasModify = this.saveUserMobileInfo(mobile,qqNum,userId);
    	if(hasModify){
    		//2,初始化后台设置
        	this.saveInitMobileSetting(userId,mobile);
        	//3,赠送500短信
        	boolean result = userAccountService.doUpdateUserSms(userId, SendMessageStatusInfo.ADD_SMS, 
    				ConstantUtils.INDEX_SEND_SMS_NUM, "首次保存手机号码", userId, null, "保存手机号码送500条短信",UserAccountService.NO_TIME);
        	if(!result){
        		throw new RuntimeException("保存手机号码送500条短信 失败! :"+userId);
        	}
    	}
	}
    
    /** 
    * @Description: 首页小红包,保存用户手机号,标记怎送500条短信 
    * @param  mobile
    * @param  qqNum
    * @param  userId    设定文件 
    * @author jackstraw_yu
    * @date 2017年11月27日 下午3:41:21
    */
    @Transactional
    public boolean saveUserMobileInfo(String mobile, String qqNum, String userId){
    	boolean hasModify =  true;
    	UserInfo user = this.queryUserInfoByNick(userId);
		if(user!=null){
			if(user.getMobile() == null && (user.getHasProvide() == null || user.getHasProvide().booleanValue()==false)){
				//手机号为空,是否赠送标记为空或者用户不希望在看到小红包时
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("phoneNum", mobile);
				map.put("qqNum", qqNum);
				map.put("userId", userId);
				map.put("hasProvide", Boolean.TRUE);
				int execute = myBatisDao.execute(UserInfo.class.getName(), "saveUserMobileInfo", map);
				if(execute != 1){
					throw new RuntimeException("影响的行数不为1! :"+userId);	
				}
			}else{
				logger.info("保存用户手机手机号,赠送500条短信:查询出用户手机号不为空或赠送短信已标记成true !!");
				throw new RuntimeException("用户手机号不为空或赠送已标记成true! :"+userId);
				//手机号&是否赠送标记已存在
				//hasModify = false;
			}
		}else{
			logger.info("保存用户手机号,查询到用户信息为空");
			throw new RuntimeException("查询不到用户信息! :"+userId);
		}
		return hasModify;
    }
    
    /** 
    * @Description: 判断用户是否保存了后台设置,没有保存的话初始化一条后台设置
    * @param  userId
    * @param  mobile    设定文件 
    * @return void    返回类型 
    * @author jackstraw_yu
    * @date 2017年11月27日 下午3:58:30
    */
    @Transactional
    private void saveInitMobileSetting(String userId,String mobile){
		MobileSetting result = mobileSettingService.findMobileSetting(userId);
		if(result !=null){
			throw new RuntimeException("用户的后台设置已存在 :"+userId);
		}
		MobileSetting mobileSetting = new MobileSetting();
		mobileSetting.setUserId(userId);
		mobileSetting.setActivityNotice(true);
		mobileSetting.setMessageCount(ConstantUtils.MESSAGE_REMINDER);
		mobileSetting.setMessageRemainder(true);
		mobileSetting.setPhoneNum(mobile);
		mobileSetting.setExpediting(false);
		mobileSetting.setServiceExpire(true);
		mobileSetting.setFlag(false);
		mobileSetting.setStartTime(null);
		mobileSetting.setEndTime(null);
		//写在mapper中
		//mobileSetting.setCreatedDate(new Date());
		//mobileSetting.setCreatedBy(userId);
		//mobileSetting.setLastModifiedBy(userId);
		//mobileSetting.setLastModifiedDate(new Date());
		mobileSettingService.saveMobileSetting(mobileSetting);
	}
    
    /**
     * 订单中心效果分析排除用户不进行统计
     * ztk2017年12月7日下午7:40:36
     */
    public List<String> findUserAccessNotNull(){
    	List<String> userList = myBatisDao.findList(UserInfo.class.getName(), "findUserAccessNotNull", null);
    	return userList;
    }
    
}
