package s2jh.biz.shop.crm.taobao;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.internal.util.WebUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.JushitaJdpUserAddRequest;
import com.taobao.api.request.ShopGetRequest;
import com.taobao.api.request.TmcUserGetRequest;
import com.taobao.api.request.TmcUserPermitRequest;
import com.taobao.api.request.VasSubscribeGetRequest;
import com.taobao.api.response.JushitaJdpUserAddResponse;
import com.taobao.api.response.ShopGetResponse;
import com.taobao.api.response.TmcUserGetResponse;
import com.taobao.api.response.TmcUserPermitResponse;
import com.taobao.api.response.VasSubscribeGetResponse;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.service.RedisLockServiceImpl;
import lab.s2jh.core.util.DateUtils;
import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.data.entity.ShopDataStatistics;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.user.service.UserLoginInfoService;
import s2jh.biz.shop.utils.getIpAddress;

@Controller
@RequestMapping(value = "/getUserCode")
public class GetUserCode {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserLoginInfoService userLoginInfoService;
	
	@Autowired
	private FirstGetUserTrade firstGetUserTrade;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;

	@Autowired
	private TradeInfoService tradeInfoService;

	@Autowired
	private OrderSetupService orderSetupService;

	private TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
			taobaoInfo.appKey, taobaoInfo.appSecret);

	private static Logger logger = LoggerFactory.getLogger(GetUserCode.class);

	/**
	 * 客云CRM主调页面
	 * 
	 * @param code
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/login")
	public String getUserCode(HttpServletRequest request, Model model,
			String code) {
		// 判断用户是否订购，没有则跳转到购买应用页面
		String url = taobaoInfo.payUrl;
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT)
				+ "<=>开始根据买家code生成token================================");
		if (code != null && !"".equals(code.trim())) {
			try {
				url = this.getToken(request, code, model);
			} catch (Exception e) {
				logger.error("用户登录时生成token并获取url时异常!!^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
				url = taobaoInfo.payUrl;
			}
		}
		return url;
	}

	/**
	 * @Description:进入首页时重定向到改路径查询店铺数据
	 * @author jackstraw_yu
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/home/getData")
	public String toHomeIndex(HttpServletRequest request, Model model) {
		String userId = (String) request.getSession().getAttribute(
				"taobao_user_nick");
		/*
		 * String yData =
		 * cacheService.get(RedisConstant.RedisCacheGroup.SHOP_DATA_CACHE,
		 * RedisConstant
		 * .RediskeyCacheGroup.SHOP_DATA_KEY+"-"+userId+"-Y",String.class);
		 * String bData =
		 * cacheService.get(RedisConstant.RedisCacheGroup.SHOP_DATA_CACHE,
		 * RedisConstant
		 * .RediskeyCacheGroup.SHOP_DATA_KEY+"-"+userId+"-BY",String.class);
		 */
		String yData = redisLockServiceImpl.getValue(
				RedisConstant.RediskeyCacheGroup.SHOP_DATA_KEY + "-" + userId
						+ "-Y", String.class);
		String bData = redisLockServiceImpl.getValue(
				RedisConstant.RediskeyCacheGroup.SHOP_DATA_KEY + "-" + userId
						+ "-BY", String.class);

		// 兩天数据都为空,线程异步查询
		if ((yData == null || "".equals(yData))
				&& (bData == null || "".equals(bData))) {
			asyncGetTradeData(userId);
			model.addAttribute("dataFlag", "");// 可在页面加一个标识:店铺数据缓冲中
			return "crms/home/index";
		}
		if (yData != null && !"".equals(yData)) {
			// 解析后的list泛型是String===>页面能够解析!!
			List<ShopDataStatistics> list = JSON.parseObject(yData,
					new TypeReference<ArrayList>() {
					});
			model.addAttribute("shopDateStatistics", list);
		}
		if (bData != null && !"".equals(bData)) {
			List<ShopDataStatistics> list = JSON.parseObject(bData,
					new TypeReference<ArrayList>() {
					});
			model.addAttribute("BeforshopDateStatistics", list);
		}
		return "crms/home/index";
	}

	@RequestMapping(value = "/token")
	public String getUserToken(String code, HttpServletRequest request) {
		// if(code!=null){
		// getToken(request, code);
		// }
		System.out.println(code);
		return "crms/home/index";
	}

	/**
	 * 根据买家的code生成token（即sessionKey）
	 * 
	 * @param request
	 * @param code
	 * @throws ParseException
	 */
	private String getToken(HttpServletRequest request, String code, Model model) {
		// 将用户信息放入session中
		HttpSession session = request.getSession();
		/***
		 * 每次从淘宝跳转到 本项目中 的code都不一致 所以此处原有的代码暂时先删除
		 * **/
		Map<String, String> props = new HashMap<String, String>();
		props.put("grant_type", "authorization_code");
		// 测试时，需把test参数换成自己应用对应的值
		props.put("code", code);
		props.put("client_id", taobaoInfo.appKey);
		props.put("client_secret", taobaoInfo.appSecret);
		props.put("redirect_uri", taobaoInfo.hdUrl);
		props.put("view", "web");

		String json = null;
		try {
			json = WebUtils.doPost(taobaoInfo.tokenUrl, props, 30000, 30000);
		} catch (Exception e) {
			logger.error("WebUtils.doPost异常!!^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
			return taobaoInfo.payUrl;
		}
		String access_token = null, taobao_user_nick = null, taobao_user_id = null, expires_in = null;
		logger.debug("------------------- 用户登录请求的json原数据  -------------------------"
				+ json);
		if (json != null && !"".equals(json)) {
			JSONObject json_test = JSONObject.fromObject(json);
			access_token = json_test.getString("access_token");
			taobao_user_nick = json_test.getString("taobao_user_nick");
			taobao_user_id = json_test.getString("taobao_user_id");
			expires_in = json_test.getString("expires_in");
			try {
				taobao_user_nick = URLDecoder.decode(taobao_user_nick, "utf-8");
			} catch (Exception e) {
				logger.error("URLDecoder.decode编码异常!!^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
				return taobaoInfo.payUrl;
			}
			logger.info("================================ access_token："
					+ access_token + " taobao_user_nick:" + taobao_user_nick
					+ " taobao_user_id:" + taobao_user_id);
		}
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT) + "<=>用户："
				+ taobao_user_nick + "准备开始登录================================");
		// 如果用户名为空，跳转到登录页面
		if (taobao_user_nick == null || "".equals(taobao_user_nick))
			return taobaoInfo.payUrl;
		// 判断用户表是否存在用户信息
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT)
				+ "<=>用户："
				+ taobao_user_nick
				+ "================================开始从数据库中获取用户的信息================================");
		UserInfo user = userInfoService.findUserInfo(taobao_user_nick);
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT)
				+ "<=>用户："
				+ taobao_user_nick
				+ "================================获取用户数据完成，开始判断用户到期时间！================================");
		// 判断如果为空则添加用户信息
		Date exTime = null;
		if (user == null) {
			// 启动一个线程，同步新用户一天的订单数据
			asyncHandleData(access_token, taobao_user_nick);
			logger.info("================================"
					+ DateUtils.formatDate(new Date(),
							DateUtils.DEFAULT_TIME_FORMAT)
					+ "<=>用户："
					+ taobao_user_nick
					+ "================================获取用户的应用到期时间！！！================================");
			// 查询用户应用的到期时间
			exTime = this.getUserOrderInfo(taobao_user_nick);
			this.addNewUser(taobao_user_nick, taobao_user_id, access_token,
					exTime, expires_in);
			logger.info("================================"
					+ DateUtils.formatDate(new Date(),
							DateUtils.DEFAULT_TIME_FORMAT)
					+ "<=>用户："
					+ taobao_user_nick
					+ "================================添加新用户信息完成！================================");
		} else {
			if (null != user.getBlackStatus()
					&& user.getBlackStatus().equals(taobaoInfo.blackStatus)) {
				return taobaoInfo.payUrl;
			}
			logger.info("================================"
					+ DateUtils.formatDate(new Date(),
							DateUtils.DEFAULT_TIME_FORMAT)
					+ "<=>用户："
					+ taobao_user_nick
					+ "================================用户信息不为空，判断用户过期时间================================");
			// 判断用户的过期时间
			int expirationTimeStatus = -1;
			exTime = this.getUserOrderInfo(taobao_user_nick);
			if (exTime != null) {
				expirationTimeStatus = exTime.compareTo(new Date());
			} else {
				// 如果查询为空则判断数据库中存储的时间是否过期
				logger.info("================================"
						+ DateUtils.formatDate(new Date(),
								DateUtils.DEFAULT_TIME_FORMAT)
						+ "<=>用户："
						+ taobao_user_nick
						+ "================================重新获取用户的应用到期时间！！！================================");
				exTime = user.getExpirationTime();
				expirationTimeStatus = exTime.compareTo(new Date());
			}

			if (expirationTimeStatus >= 0) {
				// 更新用户token到数据库
				this.updateUserInfo(access_token, taobao_user_nick, exTime,
						expires_in);
			} else {
				logger.info("================================"
						+ DateUtils.formatDate(new Date(),
								DateUtils.DEFAULT_TIME_FORMAT)
						+ "<=>用户："
						+ taobao_user_nick
						+ "================================用户已过期，跳转到订购页面================================");
				return taobaoInfo.payUrl;
			}
		}
		// 将新用户添加到聚石塔同步列表
		this.addJstUser(access_token);
		if (!getTmcUserTopic(taobao_user_nick)) {
			this.userInfoService.addUserPermitByMySql(taobao_user_nick,null);
		}
		// 根据卖家昵称获取店铺名称(短信签名)
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT) + "<=>用户："
				+ taobao_user_nick + "================================");
		String ShopName = null;
		if (user.getShopName() != null && !"".equals(user.getShopName())) {
			ShopName = user.getShopName();
		} else {
			ShopName = this.getShopName(taobao_user_nick);
		}
		// 将用户信息放入session中
		try {
			Map<String,Long>  map = this.getExpirationTime(user, taobao_user_nick);
			/*if(map.get("days")!= null){
				session.setAttribute("dayCount", map.get("days"));
			}else{
				session.setAttribute("hourCount", map.get("hours"));
			}*/
			if(map.get("days")!= null){
				session.removeAttribute("hourCount");
				session.setAttribute("dayCount", map.get("days"));
			}else{
				session.removeAttribute("dayCount");
				session.setAttribute("hourCount", map.get("hours"));
			}
		} catch (Exception e) {
			logger.error("================================"
					+ DateUtils.formatDate(new Date(),
							DateUtils.DEFAULT_TIME_FORMAT)
					+ "<=>用户："
					+ taobao_user_nick
					+ "================================计算过期时间出错!================================");

		}
		session.setAttribute("taobao_user_id", taobao_user_id);
		session.setAttribute("taobao_user_nick", taobao_user_nick);
		session.setAttribute("access_token", access_token);
		session.setAttribute("ShopName", ShopName);

		// 将用户 的access_token->sessionKey放到redis中方便后期加密解密好获取
		// ????????

		// 获取页面数据
		// this.getTradeData(model,taobao_user_nick);
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT)
				+ "<=>用户："
				+ taobao_user_nick
				+ "================================用户验证成功,跳转首页!================================");

		userLoginInfoService.addUserLoginInfo(getIpAddress.getIpAddress(request), taobao_user_nick);
		return "redirect:/member/index";
	}

	/**
	 * @Description:保存新的用户信息到数据库
	 * @author jackstraw_yu
	 */
	private void addNewUser(String taobao_user_nick, String taobao_user_id,
			String access_token, Date exTime, String expires_in) {
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT)
				+ "<=>用户："
				+ taobao_user_nick
				+ "================================用户信息为空，添加新用户至数据库中！！！================================");
		UserInfo ui = new UserInfo();
		ui.setTaobaoUserNick(taobao_user_nick);
		ui.setCreateTime(new Date());
		ui.setLastLoginDate(new Date());
		ui.setStatus(0);
		ui.setTaobaoUserId(taobao_user_id);
		ui.setAccess_token(access_token);
		ui.setExpirationTime(exTime);
		ui.setExpires_in(expires_in);
		cacheService.putNoTime(
				RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE,
				RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY
						+ taobao_user_nick, access_token);
		cacheService.putNoTime(
				RedisConstant.RedisCacheGroup.SELLER_EXPIRATION_TIME,
				RedisConstant.RedisCacheGroup.SELLER_EXPIRATION_TIME
						+ taobao_user_nick, Long.parseLong(expires_in) * 1000
						+ exTime.getTime());

		try {
			userInfoService.addUserInfo(taobao_user_nick, taobao_user_id, access_token, exTime, expires_in);
			logger.info("================================"
					+ DateUtils.formatDate(new Date(),
							DateUtils.DEFAULT_TIME_FORMAT)
					+ "<=>用户："
					+ taobao_user_nick
					+ "================================添加新用户成功！！！！================================");
		} catch (Exception e) {
			logger.error("^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^"
					+ DateUtils.formatDate(new Date(),
							DateUtils.DEFAULT_TIME_FORMAT) + "<=>用户："
					+ taobao_user_nick
					+ "添加新用户失败！！！！^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		}
	}

	/**
	 * @Description:更新用户的access_token
	 * @author jackstraw_yu
	 */
	private void updateUserInfo(String access_token, String taobao_user_nick,
			Date exTime, String expires_in) {
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT)
				+ "<=>用户："
				+ taobao_user_nick
				+ "================================更新用户的access_token================================");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("access_token", access_token);
		map.put("taobao_user_nick", taobao_user_nick);
		map.put("expiration_time", exTime);
		map.put("expires_in", expires_in);
		cacheService.putNoTime(
				RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE,
				RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY
						+ taobao_user_nick, access_token);
		cacheService.putNoTime(
				RedisConstant.RedisCacheGroup.SELLER_EXPIRATION_TIME,
				RedisConstant.RedisCacheGroup.SELLER_EXPIRATION_TIME
						+ taobao_user_nick, Long.parseLong(expires_in) * 1000
						+ exTime.getTime());
		try {
			userInfoService.updateUserInfo(taobao_user_nick, access_token, exTime, expires_in);
			logger.info("================================"
					+ DateUtils.formatDate(new Date(),
							DateUtils.DEFAULT_TIME_FORMAT)
					+ "<=>用户："
					+ taobao_user_nick
					+ "================================更新用户信息成功！！！================================");
		} catch (Exception e) {
			logger.error("^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^"
					+ DateUtils.formatDate(new Date(),
							DateUtils.DEFAULT_TIME_FORMAT) + "<=>用户："
					+ taobao_user_nick
					+ "更新用户信息失败！！！^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		}
	}

	/**
	 * 将用户添加到聚石塔数据同步列表 2016-12-30 11:34 邱洋
	 * 
	 * @param token
	 */
	@SuppressWarnings("unused")
	private void addJstUser(String token) {
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT)
				+ "<=>添加用户到聚石塔数据同步列表================================");
		JushitaJdpUserAddRequest req = new JushitaJdpUserAddRequest();
		req.setRdsName(taobaoInfo.jst);
		req.setTopics("item,trade");
		req.setHistoryDays(90L);
		JushitaJdpUserAddResponse rsp = null;
		try {
			rsp = client.execute(req, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据用户昵称查询用户是否添加到tmc同步列表中
	 * 
	 * @param taobao_user_nick
	 * @return
	 */
	public boolean getTmcUserTopic(String taobao_user_nick) {
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT)
				+ "<=>查询用户是否在tmc同步列表中================================");
		TmcUserGetRequest req = new TmcUserGetRequest();
		req.setFields("user_nick,topics,user_id,is_valid,created,modified");
		req.setNick(taobao_user_nick);
		req.setUserPlatform("tbUIC");
		TmcUserGetResponse rsp = null;
		try {
			rsp = client.execute(req);
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
	 * 将已授权的用户添加到TMC消息同步列表中 邱洋 2017-01-05 15:37
	 * 
	 * @param token
	 */
	@SuppressWarnings("unused")
	private void addTmcUser(String token, Boolean flag) {
		logger.info("================================"
				+ DateUtils.formatDate(new Date(),
						DateUtils.DEFAULT_TIME_FORMAT)
				+ "<=>添加用户到tmc同步列表中================================");
		TmcUserPermitRequest req = new TmcUserPermitRequest();
		// req.setTopics("taobao_fuwu_OrderPaid,taobao_fuwu_ServiceOpen");
		// 判断用户是否开启了订单中心的设置，如果没开启则只添加基本的TMC推送服务
		if (flag) {
			req.setTopics("taobao_fuwu_OrderPaid,taobao_trade_TradeSuccess,taobao_trade_TradeClose,taobao_trade_TradeChanged,"
					+ "taobao_trade_TradeBuyerPay,taobao_trade_TradeCreate,taobao_refund_RefundSuccess,taobao_refund_RefundCreated,"
					+ "taobao_refund_RefundSellerRefuseAgreement,taobao_refund_RefundSellerAgreeAgreement,"
					+ "taobao_logistics_LogsticDetailTrace,taobao_fuwu_ServiceOpen,taobao_trade_TradeCreate,"
					+ "taobao_trade_TradeModifyFee,taobao_trade_TradeCloseAndModifyDetailOrder,taobao_trade_TradeClose,"
					+ "taobao_trade_TradeBuyerPay,taobao_trade_TradeSellerShip,taobao_trade_TradeDelayConfirmPay,"
					+ "taobao_trade_TradePartlyRefund,taobao_trade_TradePartlyConfirmPay,taobao_trade_TradeSuccess");
		} else {
			req.setTopics("taobao_fuwu_OrderPaid,taobao_fuwu_ServiceOpen");
		}
		TmcUserPermitResponse rsp = null;
		try {
			rsp = client.execute(req, token);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据用户昵称和应用服务code获取用户应用的到期时间
	 * 
	 * @param taobao_user_nick
	 * @return
	 */
	private Date getUserOrderInfo(String taobao_user_nick) {
		VasSubscribeGetRequest req = new VasSubscribeGetRequest();
		req.setArticleCode(taobaoInfo.appCode);
		req.setNick(taobao_user_nick);
		VasSubscribeGetResponse rsp = null;
		try {
			rsp = client.execute(req);
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
	 * 创建人：邱洋
	 * 
	 * @Title: 根据卖家昵称获取店铺名称
	 * @date 2017年1月23日--下午2:28:58
	 * @return String
	 * @throws
	 */
	private String getShopName(String taobao_user_nick) {
		ShopGetRequest req = new ShopGetRequest();
		req.setFields("sid,cid,title,nick,desc,bulletin,pic_path,created,modified");
		req.setNick(taobao_user_nick);
		ShopGetResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rsp.getShop() != null) {
			return rsp.getShop().getTitle();
		} else {
			return null;
		}

	}

	/**
	 * 创建人：邱洋
	 * 
	 * @Title: 开启一个线程同步新用户一天之内的订单数据
	 * @date 2017年4月7日--上午10:45:43
	 * @return void
	 * @throws
	 */
	private void asyncHandleData(final String access_token, final String userId) {
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread() {
			@Override
			public void run() {
				firstGetUserTrade.firstGetUserTrade(access_token, userId);
			}
		});
	}

	/**
	 * @Description:获取昨日,前日的各种订单数据
	 * @author jackstraw_yu
	 */
	// private void getTradeData(Model model,String userId){
	// Map<String, Date> yMap = DateUtils.caculateDate(-1);
	// List<ShopDataStatistics> shopDateStatistics =
	// tradeInfoService.getTradeDataByDate(yMap,userId);
	// model.addAttribute("shopDateStatistics", shopDateStatistics);
	// Map<String, Date> bYMap = DateUtils.caculateDate(-2);
	// List<ShopDataStatistics> beforeYestedayDate =
	// tradeInfoService.getTradeDataByDate(bYMap,userId);
	// model.addAttribute("beforeYestedayDate", beforeYestedayDate);
	// }

	/**
	 * @Description:获取昨日,前日的各种订单数据,店铺数据保存到redis中 当天有效!
	 * @author jackstraw_yu
	 */
	private void getTradeData(String userId) {
		Map<String, Date> yMap = DateUtils.caculateDate(-1);
		List<ShopDataStatistics> shopDateStatistics = tradeInfoService
				.getTradeDataByDate(yMap, userId);
		Map<String, Date> bYMap = DateUtils.caculateDate(-2);
		List<ShopDataStatistics> beforeYestedayDate = tradeInfoService
				.getTradeDataByDate(bYMap, userId);
		if (shopDateStatistics != null && !shopDateStatistics.isEmpty()) {
			String data = JSON.toJSONString(shopDateStatistics, true);
			/*
			 * cacheService.put(RedisConstant.RedisCacheGroup.SHOP_DATA_CACHE,
			 * RedisConstant.RediskeyCacheGroup.SHOP_DATA_KEY+"-"+userId+"-Y",
			 * data,TimeUnit.MILLISECONDS,DateUtils.getMillisOverToday());
			 */
			redisLockServiceImpl.putStringValueWithExpireTime(
					RedisConstant.RediskeyCacheGroup.SHOP_DATA_KEY + "-"
							+ userId + "-Y", data, TimeUnit.MILLISECONDS,
					DateUtils.getMillisOverToday());
		}
		if (beforeYestedayDate != null && !beforeYestedayDate.isEmpty()) {
			String data = JSON.toJSONString(beforeYestedayDate, true);
			/*
			 * cacheService.put(RedisConstant.RedisCacheGroup.SHOP_DATA_CACHE,
			 * RedisConstant
			 * .RediskeyCacheGroup.SHOP_DATA_KEY+"-"+userId+"-BY",data
			 * ,TimeUnit.MILLISECONDS,DateUtils.getMillisOverToday());
			 */
			redisLockServiceImpl.putStringValueWithExpireTime(
					RedisConstant.RediskeyCacheGroup.SHOP_DATA_KEY + "-"
							+ userId + "-BY", data, TimeUnit.MILLISECONDS,
					DateUtils.getMillisOverToday());
		}

	}

	private void asyncGetTradeData(final String userId) {
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread() {
			@Override
			public void run() {
				getTradeData(userId);
			}
		});
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
	
	/**
	* 创建人：邱洋
	* @Title: addTmcUserMethod 
	* @Description: TODO(判断用户是否开启订单中心设置，同时将用户添加到TMC列表) 
	* @param @param userId
	* @param @param token    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void addTmcUserMethod(String userId, String token) {
		// 判断用户是否开启了订单中心的设置，开启的用户则添加多个TMC推送消息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("status", "0");
		List<OrderSetup> list = orderSetupService.findOrderSetupOfStatus(map);
		Boolean flag = false;
		if (list != null && list.size() > 0) {
			flag = true;
		}
		// 判断用户是否已经添加到tmc同步列表，如果没有则添加
		// if (!getTmcUserTopic(taobao_user_nick)) {
		this.addTmcUser(token, flag);
		// }
	}
	
}
