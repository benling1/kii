package s2jh.biz.shop.crm.validate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.handler.impl.DefaultHandlerChain;
import lab.s2jh.core.service.CacheService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.historyOrder.service.OrderHistoryImportService;
import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.manage.entity.LogAccessDTO;
import s2jh.biz.shop.crm.manage.entity.LogType;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.entity.UserTableDTO;
import s2jh.biz.shop.crm.manage.service.LogAccessQueueService;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.message.service.MsgSendService;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.taobao.FirstGetUserTrade;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.tmc.rabbit.RabbitProducer;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.user.service.UserLoginInfoService;
import s2jh.biz.shop.crm.user.service.UserRechargeService;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ShopGetRequest;
import com.taobao.api.response.ShopGetResponse;
 

@Controller
public class TestIndex extends BaseController {
	
	@Autowired
	private FirstGetUserTrade firstGetUserTrade;
//	
	@Autowired
	private CacheService cacheService;
	@Resource(name="mongoTemplate")
    protected MongoTemplate mongoTemplate;  
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	@Autowired
	private UserInfoService userInfoService;
	@Resource
	private DefaultHandlerChain orderHandlerChain;
	@Autowired
	private RabbitProducer rabbitProducer;
	@Autowired
	private MsgSendService msgSendService;
	@Autowired
	private UserRechargeService userRechargeService;
	@Autowired
	private OrderHistoryImportService orderHistoryImportService;
	@Autowired
	private OrderSetupService orderSetupService;
	@Autowired
	private ItemService itemService;
    @Autowired
    private UserLoginInfoService userLoginInfoService;
    @Autowired
    private LogAccessQueueService  logAccessQueueService ;
	
	private Logger log = Logger.getLogger(TestIndex.class);
	
	
	@RequestMapping("/local/test/index")
	public String index(HttpServletRequest request,HttpSession session , ModelMap model,String userName,String token){
		String userId = "哈数据库等哈";
		String atiCookie = getAtiCookie();
		System.out.println(atiCookie);
		
		session.setAttribute("taobao_user_nick", userId);
		if(!"".equals(userName)){
			UserInfo userInfo = userInfoService.findUserInfo(userName);
			if(null!=userInfo){
				session.setAttribute("taobao_user_nick", userInfo.getTaobaoUserNick());
				if(null!=token&&!"".equals(token)){
					 userInfo.setAccess_token(token);
					 Map<String,Object> map  = new HashMap<String,Object>();
					 map.put("access_token", token);
					 map.put("taobao_user_nick", userInfo.getTaobaoUserNick());
					 userInfoService.updateUserInfo(userInfo.getTaobaoUserNick(), token, null, null);
					 cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userInfo.getTaobaoUserNick(),token);
				}
				session.setAttribute("access_token", token);
				session.setAttribute("ShopName", getShopName(userInfo.getTaobaoUserNick()));
				System.out.println(session.getAttribute("ShopName"));
			}
		}
		//添加一个计算过期时间的功能 author:jackstraw_yu
		Map<String,Long>  map = this.getExpirationTime(null, userId);
		 if(map.get("days")!= null){
        	session.removeAttribute("hourCount");
			session.setAttribute("dayCount", map.get("days"));
		}else{
			session.removeAttribute("dayCount");
			session.setAttribute("hourCount", map.get("hours"));
		}
		return "redirect:/member/index";
	}
 
	@RequestMapping("/local/test/login")
	public String  testLogin(HttpServletResponse response){
		log.info("测试登录！");
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(LogType.class.getName(), LogType.valueOfAlias("LOGINTYPE"));
//		params.put(LogAccessDTO.class.getName(), new LogAccessDTO());
//		try {
//			LogAccessQueueService.queue.put(params);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return "/crms/home/testlogin";
	}
	
	
	@RequestMapping(value="/local/test/activeTest",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String activeTest(HttpServletRequest request,ModelMap model){
		String userId = "哈数据库等哈";
		int rc= 100;
		boolean status=true;
		String mysql="normal"; String redis="normal"; String mongo="normal";
		String mysqlTime=""; String redisTime="";  String mongoTime="";
		try {
			long startMysqlTime = System.currentTimeMillis();
			UserInfo userInfo = userInfoService.findUserInfo(userId);
			long endsqlTime = System.currentTimeMillis();
			mysqlTime+="mysql执行耗时"+(endsqlTime-startMysqlTime)+"，mysql正常";
		} catch (Exception e) {
			rc=101; status=false; mysql="error "+e.getStackTrace();
		}
		try {
			long startRedisTime = System.currentTimeMillis();
			UserTableDTO userTableDTO = cacheService.get(RedisConstant.RedisCacheGroup.USRENICK_TABLE_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TABLE_CACHE_KEY+userId, UserTableDTO.class);
			long endRedisTime = System.currentTimeMillis();
			redisTime+="redis执行耗时"+(endRedisTime-startRedisTime)+"，redis正常";
		} catch (Exception e) {
			rc=102; status=false; redis="error "+e.getStackTrace();
		}
		try {
			long startmongoTime = System.currentTimeMillis();
			Query  query  = new Query();
			List<TradeDTO> migrateTradeDataList = tradeInfoService.findMigrateTradeDataList(query,userId,"0",1,false);
			long endmongoTime = System.currentTimeMillis();
			mongoTime+="mongo执行耗时"+(endmongoTime-startmongoTime)+"，mongo正常";
		} catch (Exception e) {
			rc=103; status=false; mongo="error "+e.getStackTrace();
		}
		return  rsMap(rc, "操作成功!").put("status", status)
				.put("mysql",mysql) .put("redis", redis).put("mongo", mongo)
				.put("mysqlTime", mysqlTime) .put("redisTime", redisTime) .put("mongoTime", mongoTime)
				.toJson();
	}
	
	
	@Autowired
	private MobileSettingService mobileSettingService;
	
	/** 
	* @Description:模拟:后台管理催付效果,过期提醒,短信余额
	* @param  request
	* @param  model
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年12月4日 上午9:19:19
	*/
	@RequestMapping(value="/local/scanMobileSettingTable")
	@ResponseBody
	public String scanMobileSettingTable(HttpServletRequest request,ModelMap model){
		mobileSettingService.scanMobileSettingTable();
		return "true";
	}
	
	/** 
	* @Description:模拟短信余额不足提醒
	* @param  request
	* @param  model
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年12月4日 上午9:20:09
	*/
	@RequestMapping(value="/local/scanUserSmsCountRemind")
	@ResponseBody
	public String scanUserSmsCountRemind(HttpServletRequest request,ModelMap model){
		mobileSettingService.scanUserSmsCountRemind();
		return "true";
	}
	
	
	/** 
	* @Description: 模拟定时重置标记
	* @param  request
	* @param  model
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年12月4日 上午9:21:46
	*/
	@RequestMapping(value="/local/scheduleResetFlag")
	@ResponseBody
	public String scheduleResetFlag(HttpServletRequest request,ModelMap model){
		mobileSettingService.scheduleResetFlag();
		return "true";
	}
	
	
	/** 
	* @Description: 模拟充值标记
	* @param  request
	* @param  model
	* @param  userId
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2017年12月4日 上午9:22:44
	*/
	@RequestMapping(value="/local/resetSmsCountRemindMark")
	@ResponseBody
	public String resetSmsCountRemindMark(HttpServletRequest request,ModelMap model,String userId){
		if(userId!=null && !"".equals(userId)){
			mobileSettingService.resetSmsCountRemindMark(userId);
		}else{
			return "false";
		}
		return "true";
	}
	
	
	/** 
	* 获取用户的过期时间<br/>
	* 大于一天显示N+1天<br/>
	* 小于一天显示N小时<br/>
	* @param  userInfo
	* @param  userId
	* @return Map<String,Long>    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月17日 下午3:34:07
	*/
	private Map<String,Long> getExpirationTime(UserInfo userInfo, String userId){
		Map<String,Long> map = new HashMap<String,Long>();
		if (userInfo == null){
			userInfo = userInfoService.findUserInfo(userId);
		}
		//软件到期时间
		if (userInfo.getExpirationTime() != null) {
			//小于一天显示小时,大于一天显示天,忽略整整一天的情况
			if((userInfo.getExpirationTime().getTime()-System.currentTimeMillis()) < 86400000){
				map.put("hours", (userInfo.getExpirationTime().getTime()-System.currentTimeMillis())/3600000);
			}else{
				/*long start = DateUtils.parseDate(DateUtils.formatDate(new Date(), DateUtils.DEFAULT_DATE_FORMAT),
						DateUtils.DEFAULT_DATE_FORMAT).getTime();
				long end = DateUtils.parseDate(DateUtils.formatDate(userInfo.getExpirationTime(), DateUtils.DEFAULT_DATE_FORMAT),
						DateUtils.DEFAULT_DATE_FORMAT).getTime();*/
				map.put("days", (userInfo.getExpirationTime().getTime()-System.currentTimeMillis())/86400000+1);
			}
		}
		return map;
	}
	public static String getShopName(String taobao_user_nick) {
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
				taobaoInfo.appKey, taobaoInfo.appSecret);
		ShopGetRequest req = new ShopGetRequest();
		req.setFields("sid,cid,title,nick,desc,bulletin,pic_path,created,modified");
		req.setNick(taobao_user_nick);
		ShopGetResponse rsp = null;
		try {
			rsp = client.execute(req);
			System.out.println(rsp);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		if (rsp.getShop() != null) {
			return rsp.getShop().getTitle();
		} else {
			System.out.println(3333);
			return null;
		}
	}
}
