package s2jh.biz.shop.crm.taobao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.taobao.util.SyncJudgeRequestUtil;
import s2jh.biz.shop.crm.taobao.util.TmcThreadUtil;
import s2jh.biz.shop.crm.user.entity.RechargeMenu;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.entity.UserRecharge;
import s2jh.biz.shop.crm.user.service.RechargeMenuService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.user.service.UserRechargeService;
import s2jh.biz.shop.utils.SpringContextUtil;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BillRecordDto;
import com.taobao.api.domain.IncomeConfirmDto;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.request.FuwuSpBillreordAddRequest;
import com.taobao.api.request.FuwuSpConfirmApplyRequest;
import com.taobao.api.response.FuwuSpBillreordAddResponse;
import com.taobao.api.response.FuwuSpConfirmApplyResponse;

@Component
@Deprecated 
public class GetTaobaoMSG implements Runnable {
	Logger logger = org.slf4j.LoggerFactory.getLogger(GetTaobaoMSG.class);
//	@Autowired
//	private TBMessageManage taoBaoMessageManage;

//  @Autowired
//  private JmsProducer producer;
	
	@Autowired
	private SyncJudgeRequestUtil syncJudgeRequestUtil;
	public static int size = 50000; // 队列数量
	public static int MaxSize = 100000; // 队列数量
	
	public static   Map<Long,Object> map = new HashMap<Long,Object>();

	@Override
	public void run() {
		logger.debug("第一次连接，准备连接淘宝接收消息");
		TmcClient client = new TmcClient(taobaoInfo.appKey,
				taobaoInfo.appSecret, "vip_user"); // 关于default参考消息分组说明client
		client.setThreadCount(100);
		client.setMessageHandler(new MessageHandler() {
			public void onMessage(Message message, MessageStatus status) {
				try {
					boolean canExecute = syncJudgeRequestUtil
							.canExecute(message.getContent());
					if (canExecute) {
						TmcThreadUtil.queue.put(message);
						logger.debug("主题："+message.getTopic()+"，内容："+message.getContent()+"成功向队列中插入一个元素，队列剩余空间："
								+ (size - TmcThreadUtil.queue.size()));
					} else {
						logger.info("tmc 重复" + message.getContent());
					}
					if ("taobao_fuwu_OrderPaid".equals(message.getTopic())) {
						String content = message.getContent();
						// 判空
						if (content != null && !"".equals(content)) {
							JSONObject json = JSONObject
									.fromObject(content);
							String article_code = json
									.getString("article_code");
							if (article_code.equals(taobaoInfo.appCode)) {
								addUser(message);
							} else {
								addUserRecharge(message);
							}
						}
					} else if (message.getTopic().equals(
							"taobao_fuwu_ServiceOpen")) { // 判断接收到的MSQ信息是否为用户购买应用即（taobao_fuwu_ServiceOpen）
						addUser(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
					// status.fail(); // 消息处理失败回滚，服务端需要重发
					// 重试注意：不是所有的异常都需要系统重试。
					// 对于字段不全、主键冲突问题，导致写DB异常，不可重试，否则消息会一直重发
					// 对于，由于网络问题，权限问题导致的失败，可重试。
					// 重试时间 5分钟不等，不要滥用，否则会引起雪崩
				}
			}
		});
		try {
			client.connect("ws://mc.api.taobao.com");
		} catch (Exception e) {
			e.printStackTrace();
		} // 消息环境地址：ws://mc.api.tbsandbox.com/
	}

	@SuppressWarnings("unused")
	public void addUserRecharge(Message message) {
		String content = message.getContent();
		// 判空
		if (content != null && !"".equals(content)) {
			JSONObject json = JSONObject.fromObject(content);

			// 获取订单id
			String orderId = json.getString("order_id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			// 查询充值列表,判断充值列表中是否存在该order_id
			UserRechargeService userRechargeService = SpringContextUtil
					.getBean("userRechargeService");
			long l = userRechargeService.getUserRechar(map);
			// 不存在该orderId
			if (l == 0) {
				UserRecharge userRecharge = new UserRecharge();
				// 充值用户昵称
				userRecharge.setUserNick(json.getString("nick"));
				// 充值用户编号
				userRecharge.setOrderId(json.getString("order_id"));

				// 调用RechargeMenuService获取到短信套餐的条数
				RechargeMenuService rechargeMenuService = SpringContextUtil
						.getBean("rechargeMenuService");
				RechargeMenu rechargeMenu = rechargeMenuService
						.queryRechargeMenu(json.getString("item_code"));
				if (rechargeMenu != null) {
					userRecharge.setRechargeNUm(rechargeMenu.getNum());
				}

				String string = json.getString("total_pay_fee");
				double rechargePrice = Double.parseDouble(json
						.getString("total_pay_fee"));
				// 1分换算成1元
				userRecharge.setRechargePrice(rechargePrice / 100);
				userRecharge.setRechargeDate(new Date());
				// 调用service保存
				UserInfoService userInfoService = SpringContextUtil
						.getBean("userInfoService");
				try {
					userInfoService.updateUserInfo(userRecharge);
				} catch (Exception e) {
					e.printStackTrace();
				}
				getUserRechargeInfo(json.getString("order_id"));

			}
		}
	}

	/**
	 * 根据订单编号查询订单信息，并生成帐单上传到淘宝
	 * 
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings("unused")
	public void getUserRechargeInfo(String orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		UserRechargeService userRechargeService = SpringContextUtil
				.getBean("userRechargeService");
		UserRecharge ur = userRechargeService.getUserRechargeInfo(map);

		// 根据卖家昵称查询用户信息（取出用户的sessionKey即access_token）
		UserInfoService userInfoService = SpringContextUtil
				.getBean("userInfoService");
		UserInfo ui = new UserInfo();
		ui = userInfoService.findUserInfo(ur.getUserNick());
		if (ur == null) {
			return;
		}
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
				taobaoInfo.appKey, taobaoInfo.appSecret);
		//上传充值订单详情
		FuwuSpBillreordAddRequest req = new FuwuSpBillreordAddRequest();
		BillRecordDto obj1 = new BillRecordDto();
		obj1.setStartDate(ur.getRechargeDate());
		if (ur.getStatus() != null) {
			obj1.setStatus(Long.parseLong(ur.getStatus()));
		}
		obj1.setAppkey(taobaoInfo.appKey);
		obj1.setType(1L);
		if (ui != null && ui.getMobile() != null) {
			obj1.setTargetNo(ui.getMobile());
		} else {
			obj1.setTargetNo(ui.getId().toString());
		}
		Long fee = new Double(ur.getRechargePrice() * 100).longValue();
		obj1.setFee(fee);
		obj1.setNick(ur.getUserNick());
		obj1.setOutConfirmId(ur.getId().toString());
		if (ur.getOrderId() != null) {
			obj1.setOrderId(Long.parseLong(ur.getOrderId()));
		}
		obj1.setOutOrderId(ur.getOrderId());
		req.setParamBillRecordDTO(obj1);
		FuwuSpBillreordAddResponse rsp = null;
		try {
			rsp = client.execute(req, ui.getAccess_token());
		} catch (ApiException e) {
			e.printStackTrace();
		}

		//创建确认单，申请用户确认
		FuwuSpConfirmApplyRequest req1 = new FuwuSpConfirmApplyRequest();
		IncomeConfirmDto obj2 = new IncomeConfirmDto();
		obj2.setFee(fee);
		obj2.setNick(ur.getUserNick());
		obj2.setAppkey(taobaoInfo.appKey);
		if (ur.getOrderId() != null) {
			obj2.setOrderId(Long.parseLong(ur.getOrderId()));
		}
		obj2.setOutConfirmId(ur.getId().toString());
		req1.setParamIncomeConfirmDTO(obj2);
		FuwuSpConfirmApplyResponse rsp1 = null;
		try {
			rsp1 = client.execute(req1, ui.getAccess_token());
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断用户是否存在，不存在则添加新用户；如果存在则更新用户应用到期时间
	 * 
	 * @param message
	 */
	public void addUser(Message message) {
		String content = message.getContent();
		// String
		// content="{'refund_fee':'0','biz_type':2,'order_cycle':'2','article_name':'客云CRM','article_code':'FW_GOODS-1952286','order_id':310022087290045,'create':'2017-03-10 11:20:03','order_status':3,'pay_status':1,'biz_order_id':310022087290045,'fee':'1500.0','prom_fee':'0.0','order_cycle_end':'2017-05-10 00:00:00','total_pay_fee':'1500.0','item_code':'FW_GOODS-1952286-1','nick':'哈数据库等哈','item_name':'经典版','version_no':1,'order_cycle_start':'2017-04-10 00:00:00','outer_trade_code':''}";
		if (content != null && !content.equals("")) {
			JSONObject json = JSONObject.fromObject(content);

			// 根据用户昵称查询用户是否存在
			UserInfoService userInfoService = SpringContextUtil
					.getBean("userInfoService");
			UserInfo ui = null;
			ui = userInfoService.findUserInfo(json.getString("nick"));

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			// 判断用户是否存在，不存在则添加新用户，存在则更新用户应用的到期时间
			if (ui == null) {
				ui = new UserInfo();
				// ui.setTaobaoUserId(json.getString("taobao_user_id"));
				ui.setTaobaoUserNick(json.getString("nick"));
				ui.setCreateTime(new Date());
				try {
					ui.setExpirationTime(dateFormat.parse(json
							.getString("order_cycle_end")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ui.setEmailNum(0);
				ui.setStatus(0);
//				userInfoService.addUserInfo(sellerNick, taoBaoUserId, sessionKey, sellerExpireTime, expiresIn)
			} else {
				// 更新用户应用到期时间
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("taobao_user_nick", ui.getTaobaoUserNick());
				try {
					map.put("expiration_time",
							dateFormat.parse(json.getString("order_cycle_end")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ui.setLastLoginDate(new Date());
//				userInfoService.updateUserInfo(json.getString("nick"), sessionKey, sellerExpireTime, expiresIn);
			}
		}
	}
}
