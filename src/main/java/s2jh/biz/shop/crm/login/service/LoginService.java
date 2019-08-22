package s2jh.biz.shop.crm.login.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.alipay.api.internal.util.WebUtils;
import com.taobao.api.ApiException;
import com.taobao.api.request.JushitaJdpUserAddRequest;
import com.taobao.api.request.TmcUserGetRequest;
import com.taobao.api.request.VasSubscribeGetRequest;
import com.taobao.api.response.JushitaJdpUserAddResponse;
import com.taobao.api.response.TmcUserGetResponse;
import com.taobao.api.response.VasSubscribeGetResponse;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.util.DateUtils;
import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.manage.entity.UserTableDTO;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.FirstGetUserTrade;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.user.service.UserLoginInfoService;
import s2jh.biz.shop.utils.JsonUtil;
import s2jh.biz.shop.utils.getIpAddress;

/** 
* @author wy
* @version 创建时间：2017年11月13日 下午1:58:01
*/
@Service
public class LoginService {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private FirstGetUserTrade firstGetUserTrade;
    @Autowired
    private UserLoginInfoService userLoginInfoService;
    
    @Resource(name="mongoTemplate")
    protected MongoTemplate mongoTemplate;  
    
    private  Logger logger = LoggerFactory.getLogger(LoginService.class);
    
    public String getIndexUrl(HttpServletRequest request, String code) {
        Map<String, String> props = new HashMap<String, String>(8);
        props.put("grant_type", "authorization_code");
        props.put("code", code);
        props.put("client_id", taobaoInfo.appKey);
        props.put("client_secret", taobaoInfo.appSecret);
        props.put("redirect_uri", taobaoInfo.hdUrl);
        props.put("view", "web");
        String json = null;
        try {
            json = WebUtils.doPost(taobaoInfo.tokenUrl, props, 30000, 30000);
            this.logger.info("获取用户信息源数据：" + json);
        } catch (Exception e) {
            logger.error("WebUtils.doPost异常!!^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
        }
        if(ValidateUtil.isEmpty(json)){
            return taobaoInfo.payUrl;
        }
        JSONObject userJson = JSONObject.fromObject(json);
        //卖家用户秘钥
        String sessionKey = userJson.getString("access_token");
        //卖家昵称
        String sellerNick = userJson.getString("taobao_user_nick");
        //卖家淘宝ID
        String taoBaoUserId = userJson.getString("taobao_user_id");
        //软件过期时间
        String expiresIn = userJson.getString("expires_in");
        try {
            sellerNick = URLDecoder.decode(sellerNick, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(ValidateUtil.isEmpty(sellerNick)){
            return taobaoInfo.payUrl;
        }
        sellerNick = sellerNick.trim();
        this.logger.info("转码后的数据-->卖家昵称："+sellerNick+"，卖家秘钥："+sessionKey+"，卖家淘宝ID："+taoBaoUserId+"，卖家过期时间："+expiresIn);
        cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE,RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY + sellerNick, sessionKey);
        Date sellerExpireTime = this.getUserExpireTimeByTaobao(sellerNick);
        if(sellerExpireTime==null){
            this.logger.info("获取用户过期时间失败：" + json);
            return taobaoInfo.payUrl;
        }
        cacheService.putNoTime(RedisConstant.RedisCacheGroup.SELLER_EXPIRATION_TIME,RedisConstant.RedisCacheGroup.SELLER_EXPIRATION_TIME + sellerNick, Long.parseLong(expiresIn) * 1000 + sellerExpireTime.getTime());
        //多线程更新用户信息
        if(this.userInfoService.getUserInfoId(sellerNick)==null){
            // 启动一个线程，同步新用户一天的订单数据
        	this.userInfoService.addUserInfo(sellerNick, taoBaoUserId, sessionKey, sellerExpireTime, expiresIn);
            asyncHandleData(sessionKey, sellerNick);
        }
        this.updateUserByLogin(sellerNick, sessionKey, taoBaoUserId, expiresIn, sellerExpireTime,getIpAddress.getIpAddress(request));
        //long dayCount = this.getUserExpireDayNum(sellerExpireTime);
        HttpSession session = request.getSession();
        Map<String, Long> map = getExpirationTime(sellerExpireTime);
        //到期天数
        if(map.get("days")!= null){
        	session.removeAttribute("hourCount");
			session.setAttribute("dayCount", map.get("days"));
		}else{
			session.removeAttribute("dayCount");
			session.setAttribute("hourCount", map.get("hours"));
		}
        session.setAttribute("taobao_user_id", sellerNick);
        session.setAttribute("taobao_user_nick", sellerNick);
        session.setAttribute("access_token", sessionKey);
        return "redirect:/member/index";
    }
    /**
     * 开启线程更新用户信息
     * @author: wy
     * @time: 2017年11月14日 上午10:41:43
     * @param sellerNick 卖家昵称
     * @param sessionKey 卖家密钥
     * @param taoBaoUserId 卖家淘宝id
     * @param expiresIn 卖家过期时间
     * @param sellerExpireTime 卖家过期时间
     */
    private void updateUserByLogin(final String sellerNick,final String sessionKey,final String taoBaoUserId,final String expiresIn,
                                        final Date sellerExpireTime,final String ipAddress){
        MyFixedThreadPool.getLoginThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                updateUser(sellerNick, sessionKey, taoBaoUserId, expiresIn, sellerExpireTime,ipAddress);
            }
        });
    }
    /**
     * 更新用户信息
     * @author: wy
     * @time: 2017年11月14日 上午10:41:43
     * @param sellerNick 卖家昵称
     * @param sessionKey 卖家密钥
     * @param taoBaoUserId 卖家淘宝id
     * @param expiresIn 卖家过期时间
     * @param sellerExpireTime 卖家过期时间
     */
    private void updateUser( String sellerNick, String sessionKey, String taoBaoUserId, String expiresIn, Date sellerExpireTime,String ipAddress){
        long startTime = System.currentTimeMillis();
        this.logger.info("卖家："+sellerNick+"开始更新信息");
        this.userInfoService.updateUserInfo(sellerNick, sessionKey, sellerExpireTime, expiresIn);
        // 将新用户添加到聚石塔同步列表
        this.addJstUser(sessionKey);
        if (!getTmcUserTopic(sellerNick)) {
            this.userInfoService.addUserPermitByMySql(sellerNick,null);
        }
        userLoginInfoService.addUserLoginInfo(ipAddress, sellerNick);
        this.logger.info("卖家："+sellerNick+"更新结束，花费了："+(System.currentTimeMillis()-startTime)+"ms");
        long startTime1 = System.currentTimeMillis();
        updateMongoTable(sellerNick);
        this.logger.info("更新"+sellerNick+"mongo 更新结束，花费了："+(System.currentTimeMillis()-startTime1)+"ms");
    }
    
    
    private void updateMongoTable(String usserName) {
		String taobaoUserNick="";
		try {
			if(usserName!=null&&!"".equals(usserName)){
				UserInfo userInfo = userInfoService.findUserInfo(usserName);
				if(null!=userInfo){ 
					UserTableDTO usertable = new UserTableDTO();
					usertable.setDataCount(0l);
					usertable.setUserId(String.valueOf(userInfo.getId()));
					usertable.setUserNickName(userInfo.getTaobaoUserNick());
					cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TABLE_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TABLE_CACHE_KEY+userInfo.getTaobaoUserNick(),JsonUtil.toJson(usertable));
					boolean memberDTOExists = mongoTemplate.collectionExists("MemberDTO"+userInfo.getId());
					if(!memberDTOExists){
						mongoTemplate.createCollection("MemberDTO"+userInfo.getId()).createIndex("buyerNick");
						mongoTemplate.getCollection("MemberDTO"+userInfo.getId()).createIndex("phone");
						mongoTemplate.getCollection("MemberDTO"+userInfo.getId()).createIndex("lastTradeTime");
						mongoTemplate.getCollection("MemberDTO"+userInfo.getId()).createIndex("msgId");
					}
					boolean tradeDTOExists = mongoTemplate.collectionExists("TradeDTO"+userInfo.getId());
					if(!tradeDTOExists){
						mongoTemplate.createCollection("TradeDTO"+userInfo.getId()).createIndex("tid");
					}
					boolean smsRecordDTOExists = mongoTemplate.collectionExists("SmsRecordDTO"+userInfo.getId());
					if(!smsRecordDTOExists){
						mongoTemplate.createCollection("SmsRecordDTO"+userInfo.getId()).createIndex("msgId");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.info("创建mongo表失败！"+taobaoUserNick);
		}
	}
    /**
     * 根据用户昵称查询用户是否添加到tmc同步列表中
     * 
     * @param taobao_user_nick
     * @return
     */
    public boolean getTmcUserTopic(String taobaoUserNick) {
        logger.info("================================<=>查询用户："+taobaoUserNick+"是否在tmc同步列表中================================");
        TmcUserGetRequest req = new TmcUserGetRequest();
        req.setFields("user_nick,topics,user_id,is_valid,created,modified");
        req.setNick(taobaoUserNick);
        req.setUserPlatform("tbUIC");
        TmcUserGetResponse rsp = null;
        try {
            rsp = TaoBaoClientUtil.TAOBAO_CLIENT.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        if (rsp.getTmcUser() != null && rsp.getTmcUser().getTopics() != null
                && rsp.getTmcUser().getTopics().size() > 0) {
            return true;
        }
        return false;
    }
    /**
     * 添加新用户到聚石塔
     * @author: wy
     * @time: 2017年11月14日 下午3:29:33
     * @param token
     */
    private void addJstUser(String token) {
        logger.info("================================"+ DateUtils.formatDate(new Date(),DateUtils.DEFAULT_TIME_FORMAT)+ "<=>添加用户到聚石塔数据同步列表================================");
        JushitaJdpUserAddRequest req = new JushitaJdpUserAddRequest();
        req.setRdsName(taobaoInfo.jst);
        req.setTopics("item,trade");
        req.setHistoryDays(90L);
        JushitaJdpUserAddResponse rsp = null;
        try {
            rsp = TaoBaoClientUtil.TAOBAO_CLIENT.execute(req, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.logger.info("聚石塔结果为："+rsp.getBody());
    }
    /**
    * 根据用户昵称和应用服务code获取用户应用的到期时间
    * @param taobao_user_nick
    * @return
    */
   private Date getUserExpireTimeByTaobao(String taobaoUserNick) {
       VasSubscribeGetRequest req = new VasSubscribeGetRequest();
       req.setArticleCode(taobaoInfo.appCode);
       req.setNick(taobaoUserNick);
       VasSubscribeGetResponse rsp = null;
       try {
           rsp = TaoBaoClientUtil.TAOBAO_CLIENT.execute(req);
       } catch (Exception e) {
           e.printStackTrace();
       }
       if (rsp.getArticleUserSubscribes() != null) {
           int timeCount = rsp.getArticleUserSubscribes().size();
           return rsp.getArticleUserSubscribes().get(timeCount - 1)
                   .getDeadline();
       }
       return null;
   }
   /**
    * 获取用户软件到期时间
    * @param model
    * @param userId
    * @param request
    * @throws ParseException
    */
   public Long getData(Date expirationTime, String userId) throws ParseException {
       UserInfo userInfo = null;
       if (expirationTime == null) {
           userInfo = userInfoService.findUserInfo(userId);
           expirationTime = userInfo.getExpirationTime();
       }
       return this.getUserExpireDayNum(expirationTime);
   }
   /**
    * 创建人：邱洋
    * 
    * @Title: 开启一个线程同步新用户一天之内的订单数据
    * @date 2017年4月7日--上午10:45:43
    * @return void
    * @throws
    */
   private void asyncHandleData(final String accessToken, final String userId) {
       MyFixedThreadPool.getLoginThreadPool().execute(new Runnable() {
           @Override
           public void run() {
        	   createMongoTable(userId);
        	   System.out.print("创建"+userId+"mongo 表");
               firstGetUserTrade.firstGetUserTrade(accessToken, userId);
           }
       });
   }
   private void createMongoTable(String userName){
		UserInfo userInfo = userInfoService.findUserInfo(userName);
		if(null!=userInfo){
			try {
				UserTableDTO usertable = new UserTableDTO();
				usertable.setDataCount(0l);
				usertable.setUserId(String.valueOf(userInfo.getId()));
				usertable.setUserNickName(userInfo.getTaobaoUserNick());
				UserTableDTO userTableDTO = cacheService.get(RedisConstant.RedisCacheGroup.USRENICK_TABLE_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TABLE_CACHE_KEY+userInfo.getTaobaoUserNick(), UserTableDTO.class);
				if(null==userTableDTO){
					cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TABLE_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TABLE_CACHE_KEY+userInfo.getTaobaoUserNick(),JsonUtil.toJson(usertable));
					mongoTemplate.createCollection("TradeDTO"+userInfo.getId()).createIndex("tid");
					mongoTemplate.createCollection("MemberDTO"+userInfo.getId()).createIndex("buyerNick");
					mongoTemplate.getCollection("MemberDTO"+userInfo.getId()).createIndex("phone");
					mongoTemplate.getCollection("MemberDTO"+userInfo.getId()).createIndex("lastTradeTime");
					mongoTemplate.getCollection("MemberDTO"+userInfo.getId()).createIndex("msgId");
					mongoTemplate.createCollection("SmsRecordDTO"+userInfo.getId()).createIndex("msgId");
				}else{
					  this.logger.info("查询用户为空" + userName+"未创建mongo表！");
				}
			} catch (Exception e) {
			}
		}
	}
   /**
    * 计算指定时间距离今天相差的天数
    * @author: wy
    * @time: 2017年11月14日 上午10:13:06
    * @param userExpireDate 要计算的过期时间
    * @return 计算好的天数
    */
   private Long getUserExpireDayNum(Date userExpireDate){
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       long dayCount = 0L;
       try {
           if (userExpireDate != null) {
               String start = sdf.format(new Date());
               String end = sdf.format(userExpireDate);
               long timeStart = sdf.parse(start).getTime();
               long timeEnd = sdf.parse(end).getTime();
               // 两个日期想减得到天数
               dayCount = (timeEnd - timeStart) / (24 * 3600 * 1000);
            }
       } catch (ParseException e) {
           e.printStackTrace();
       }
       return dayCount;
   }
   
   /** 
	* 获取用户的过期时间<br/>
	* 大于一天显示N+1天<br/>
	* 小于一天显示N小时<br/>
	* @param  userInfo
	* @param  userId
	* @return Map<String,Long>    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月17日 下午4:03:47
	*/
	private Map<String,Long> getExpirationTime(Date date){
		Map<String,Long> map = new HashMap<String,Long>();
		//软件到期时间
		if (date != null) {
			//小于一天显示小时,大于一天显示天,忽略整整一天的情况
			if((date.getTime()-System.currentTimeMillis()) < 86400000){
				map.put("hours", (date.getTime()-System.currentTimeMillis())/(3600000));
			}else{
				/*long start = DateUtils.parseDate(DateUtils.formatDate(new Date(), DateUtils.DEFAULT_DATE_FORMAT),
						DateUtils.DEFAULT_DATE_FORMAT).getTime();
				long end = DateUtils.parseDate(DateUtils.formatDate(userInfodate, DateUtils.DEFAULT_DATE_FORMAT),
						DateUtils.DEFAULT_DATE_FORMAT).getTime();*/
				map.put("days", (date.getTime()-System.currentTimeMillis())/(86400000)+1);
			}
		}
		return map;
	}
}
