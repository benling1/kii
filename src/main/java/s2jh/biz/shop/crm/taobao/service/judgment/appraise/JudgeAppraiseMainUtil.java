package s2jh.biz.shop.crm.taobao.service.judgment.appraise;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.SecretException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.TradeRate;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderatesGetResponse;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
/**
 * 判断中差评的工具类
 * @author Administrator
 *
 */
import s2jh.biz.shop.crm.order.entity.TradeRates;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;

@Component
@Deprecated 
public class JudgeAppraiseMainUtil {

	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private JudgeAppraiseLocationUtil judgeAppraiseLocationUtil;
	@Autowired
	private JudgeAppraiseMoneyUtil judgeAppraiseMoneyUtil;
	@Autowired
	private JudgeAppraiseOrderSourceUtil judgeAppraiseOrderSourceUtil;
	@Autowired
	private JudgeAppraiseReminderTimeUtil judgeAppraiseReminderTimeUtil;
	@Autowired
	private JudgeAppraiseVendormarkUtil judgeAppraiseVendormarkUtil;
	@Autowired
	private JudgeAppraiseUserAccountUtil judgeAppraiseUserAccountUtil;
	@Autowired
	private SmsRecordService smsRecordService;
	@Autowired
	private JudgeUserUtil judgeUserUtil;

	// 日志
	private  final Log logger = LogFactory
			.getLog(JudgeAppraiseMainUtil.class);

	public Map<String, Object> AppraiseSendMessage(Map<String, Object> map,
			String appraiseType) throws Exception {
		map.put("appraiseType", appraiseType);
		OrderRateCareSetup orderRateCareSetup = myBatisDao.findBy(
				OrderRateCareSetup.class.getName(),
				"findOrderRateCareSetupandStatus", map);
		if (orderRateCareSetup == null) {
			map.put("flag", false);
			return map;
		}
		// 从淘宝中获得评价详情
		TradeRates tradeRates = findTradeRatesFromTB(map);
		// --------------假数据-----
		// TradeRates tradeRates = new TradeRates();
		// tradeRates.setResult("bad");

		// 查询当前用户的中差评监控设置
		// 如果中差评监控启用
		if ((boolean) map.get("flag")) {
			// 如果是中差评监控设置
			if (appraiseType.equals("1")) {

				map = appraiseSendMsg(map);
				String tid = (String) map.get("tid");
				String seller_nick = (String) map.get("sellerName");
				logger.info("评价tmc-----中差评监控--条件筛选、短信拼凑， 订单编号：  " + tid);
				// 根据订单编号、短信发送类型、短信发送状态查询该订单是否发送过短信,如果查询到数据则放弃本次任务
//				boolean status = smsRecordService.findSmsRecordStatus(tid,
//						"20", seller_nick);
//				if (status) {
//					map.put("flag", false);
//					return map;
//				}
				String statusFiltrate = (String) map.get("statusFiltrate");
				// 获取到评价类型
				if (tradeRates != null) {
					String result = tradeRates.getResult();
					logger.info("评价tmc-----中差评监控--评价接口状态：" + result
							+ "保存设置评价状态，0-中评，1-差评" + statusFiltrate + " "+tid);
					if (result != null && !result.equals("")
							&& statusFiltrate != null) {
						// 当有中评时通知
						if (statusFiltrate.contains("0")
								&& result.equals("neutral")) {
							// 发送信息
							logger.info("评价tmc------中差评监控--中评过滤完毕---返回待发送数据  "+tid);
							return map;
						} else if (statusFiltrate.contains("1")
								&& result.equals("bad")) {
							// 发送信息
							logger.info("评价tmc-----中差评监控--差评过滤完毕---返回待发送数据 "+tid);
							return map;
						} else {
							map.put("flag", false);
							return map;
						}
					} else {
						map.put("flag", false);
						logger.info("评价tmc-----中差评监控--没有进行中评，差评判断 "+tid);
					}

				} else {
					map.put("flag", false);
				}

			} else if (appraiseType.equals("2")) {
				String tid = (String) map.get("tid");
				logger.info("评价tmc-----中差评安抚--条件筛选、短信拼凑 "+tid);
				// 任务链判断
				judgeAppraiseUserAccountUtil
						.setNext(judgeAppraiseVendormarkUtil);
				judgeAppraiseVendormarkUtil
						.setNext(judgeAppraiseReminderTimeUtil);
				judgeAppraiseReminderTimeUtil
						.setNext(judgeAppraiseOrderSourceUtil);
				judgeAppraiseOrderSourceUtil
						.setNext(judgeAppraiseMoneyUtil);
				judgeAppraiseMoneyUtil.setNext(judgeAppraiseLocationUtil);

				try {
					// 开始执行任务链
					if (tradeRates != null && tradeRates.getResult() != null
							&& "bad".equals(tradeRates.getResult())
							|| "neutral".equals(tradeRates.getResult())) {
						map = judgeAppraiseUserAccountUtil.startJob(map);
						logger.info("评价tmc-----中差评安抚--条件过滤完毕---返回待发送数据  "+tid);
					} else {
						map.put("flag", false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				map.put("flag", false);
			}
		}
		return map;
	}

	/**
	 * 查询中差评监控设置
	 * 
	 * @param Map
	 *            <String,Object>
	 */
	/*
	 * private Boolean findOrderRateCareSetup(Map<String,Object> map) {
	 * 
	 * OrderRateCareSetup orderRateCareSetup =
	 * myBatisDao.findBy(OrderRateCareSetup.class.getName(),
	 * "findOrderRateCareSetup", map); //获取到中差评监控状态是否为开启 String status=null;
	 * if(orderRateCareSetup!=null){ status = orderRateCareSetup.getStatus(); }
	 * //如果是开启状态 if (status!=null && status.equals("0")) { //获取到筛选条件 String
	 * statusFiltrate = orderRateCareSetup.getStatusFiltrate(); //如果筛选条件不为空 if
	 * (statusFiltrate!=null && !statusFiltrate.equals("")) {
	 * map.put("orderRateCareSetup", orderRateCareSetup);
	 * map.put("statusFiltrate", statusFiltrate); //如果手机号不为空 String phone =
	 * orderRateCareSetup.getAcceptSmsPhone(); if (phone!=null &&
	 * !phone.equals("")) { SmsSendInfo smsInfo = (SmsSendInfo)
	 * map.get("smsInfo"); map.put("phone", phone); //设置发送的手机号
	 * smsInfo.setPhone(phone); return true; } } return true;
	 * 
	 * }else{ return false;
	 * 
	 * }
	 * 
	 * }
	 */
	/**
	 * 从淘宝中查出评价数据
	 */
	private TradeRates findTradeRatesFromTB(Map<String, Object> map) {
		// 获取到评价者类型，一定是买家
//		String rater = (String) map.get("rater");
		TradeRates tradeRates = new TradeRates();
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
				taobaoInfo.appKey, taobaoInfo.appSecret);
		TraderatesGetRequest req = new TraderatesGetRequest();
		req.setFields("tid,oid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid");
		req.setRateType("get");
		req.setRole("buyer");
		String tid = String.valueOf(map.get("tid"));
		req.setTid(Long.parseLong(tid));

		// 从数据库中查出用户是否存在
		String sellerName = (String) map.get("sellerName");
		// 查询出卖家用户
		UserInfo user = myBatisDao.findBy(UserInfo.class.getName(),
				"findUserInfo", sellerName);
		if (user != null) {
			map.put("user", user);
			// 设置用户id
			map.put("userId", sellerName);
			String accessToken = user.getAccess_token();
			try {
				TraderatesGetResponse rsp = client.execute(req, accessToken);
				List<TradeRate> tradeRateList = rsp.getTradeRates();
				if (tradeRateList != null && tradeRateList.size() > 0) {
					TradeRate tradeRate = tradeRateList.get(0);
					logger.info("评价tmc----调用淘宝评价接口，返回评价数据：" + tid +"  评价对象："+ tradeRate);
					BeanUtils.copyProperties(tradeRate, tradeRates);
//					tradeRates.setResult("bad");//TODO
					//判断买家昵称是否是加密类型，如果是   则解密，变成名文
					try {
						if (EncrptAndDecryptClient.isEncryptData(tradeRates.getNick(), EncrptAndDecryptClient.SEARCH)) {
							String nick = EncrptAndDecryptClient.getInstance().decryptData(tradeRates.getNick(),EncrptAndDecryptClient.SEARCH, accessToken);
							tradeRates.setNick(nick);
						} else{
							tradeRates.setNick(tradeRates.getNick());
						}
					} catch (SecretException e) {
						e.printStackTrace();
					}
				}
			} catch (ApiException e) {
				e.printStackTrace();
			}

		} else {
			map.put("flag", false);
		}
		return tradeRates;
	}

	/**
	 * 中差评监控发送信息的方法
	 */
	private Map<String, Object> appraiseSendMsg(Map<String, Object> map) {
		// 用户不是被锁定的，用户的账号到期时间大于当前时间 ,用户的短信剩余数量大于0
		String sellerName = (String) map.get("sellerName");
		String buyerName = (String) map.get("buyerName");
		String tid = String.valueOf(map.get("tid"));

		UserInfo user = judgeUserUtil.isNormalUser(sellerName);
		if (user != null) {
			map.put("user", user);
		} else {
			// user is null
			map.put("flag", false);
			return map;
		}
		Date myDate = (Date) map.get("myDate");
		// 从map中获取到信息
		OrderRateCareSetup orderRateCareSetup = myBatisDao.findBy(
				OrderRateCareSetup.class.getName(),
				"findOrderRateCareSetupandStatus", map);
		if (orderRateCareSetup != null) {
			// 获取到筛选条件
			String statusFiltrate = orderRateCareSetup.getStatusFiltrate();
			// 如果筛选条件不为空
			if (statusFiltrate != null && !statusFiltrate.equals("")) {
				// 创建短信
				SmsSendInfo smsInfo = new SmsSendInfo();
				smsInfo.setTid(Long.parseLong(tid));
				smsInfo.setNickname(buyerName);
				smsInfo.setUserId(sellerName);
				smsInfo.setSendTime(myDate);
				map.put("smsInfo", smsInfo);
				map.put("orderRateCareSetup", orderRateCareSetup);
				map.put("statusFiltrate", statusFiltrate);
				// 如果手机号不为空
				String phone = orderRateCareSetup.getAcceptSmsPhone();
				if (phone != null && !phone.equals("")) {
					map.put("phone", phone);
					// 设置发送的手机号
					smsInfo.setPhone(phone);
				}
				// 短信内容为
				String content = orderRateCareSetup.getContent();
				smsInfo.setContent(content);

				smsInfo.setSendTime(myDate);
				smsInfo.setUserId(sellerName);
				smsInfo.setChannel("淘宝");
				smsInfo.setNickname(sellerName);
				smsInfo.setStatus(99);// 初次短信判断标记--99
				smsInfo.setCreatedDate(myDate);
				smsInfo.setCreatedBy("1");
				smsInfo.setType("20");
				// 计算短信长度
				int messageCount = content.length();
				if (messageCount <= 70) {
					messageCount = 1;
				} else {
					messageCount = (messageCount + 66) / 67;
				}
				// 设置短信内容
				smsInfo.setContent(content);
				// 设置短信条数
				smsInfo.setActualDeduction(messageCount);
				map.put("smsInfo", smsInfo);
				map.put("flag", true);
			} else {
				map.put("flag", false);
			}
		} else {
			map.put("flag", false);
		}
		return map;
	}
}
