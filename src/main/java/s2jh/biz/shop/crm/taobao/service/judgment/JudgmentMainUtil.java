package s2jh.biz.shop.crm.taobao.service.judgment;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;

@Component
@Deprecated 
public class JudgmentMainUtil {
	@Autowired
	private JudgeUserAccountUtil judgeUserAccountUtil;
	@Autowired
	private JudgeUserOrderAdvancedUtil judgeUserOrderAdvancedUtil;
	@Autowired
	private JudgeUserOrderSetUpUtil judgeUserOrderSetUpUtil;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(JudgmentMainUtil.class);

	/**
	 * 商户（卖家）的基本设置和高级设置的判断工具类 可以直接使用也可以自己重新组装整合逻辑顺序使用（judgment包下）
	 * 
	 * @param parseJSON
	 *            淘宝发送的JSON数据 需要有buyer_nick 卖家昵称 seller_nick 卖家昵称 tid 订单ID
	 * @param myDate
	 *            当前时间
	 * @param orderSetupStatus
	 *            具体的卖家设置关怀类别
	 * @return Map 集合<br>
	 *         key = flag value = (当前判断逻辑的最后结果，发送短信为true，不发送短信为false) <br>
	 *         key = smsInfo value = (要发送的短信的具体内容)<br>
	 *         key = sellerName value = (卖家姓名)<br>
	 *         key = order value = (具体的订单数据，本地数据库查询不到时，查询淘宝的订单数据)<br>
	 * @throws Exception
	 */
	public Map<String, Object> startJudgeOrder(JSONObject parseJSON,
			Date myDate, String orderSetupStatus) throws Exception {
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		String buyerName = parseJSON.getString("buyer_nick");
		String sellerName = parseJSON.getString("seller_nick");
		map.put("sellerName", sellerName);
		map.put("buyerName", buyerName);
		OrderSetup orderSetup = judgeUserUtil.isOrderSetupOpen(sellerName,orderSetupStatus);
		if (orderSetup != null) {
			UserInfo user = judgeUserUtil.isNormalUser(sellerName);
			if (user != null) {
				map.put("settingType", orderSetupStatus);
				String oid = parseJSON.getString("oid");
				String tid = parseJSON.getString("tid");
				map.put("user", user);
				map.put("orderSetup", orderSetup);
				map.put("tmc", 1);
				map.put("tid", tid);
				map.put("oid", oid);
				map.put("flag", true);
				map.put("myDate", myDate);
				this.logger.debug("******tid:" + tid + ",类型："
						+ orderSetupStatus + ",开始********");
				SmsSendInfo smsInfo = new SmsSendInfo();
				// 设置短信发送的 发送时间，卖家账号 ，短信内容（暂时未JSON数据），卖家姓名
				smsInfo.setTid(Long.valueOf(tid));
				smsInfo.setSendTime(myDate);
				smsInfo.setUserId(sellerName);
				smsInfo.setContent(parseJSON.toString());
				smsInfo.setChannel("淘宝");
				smsInfo.setNickname(buyerName);
				smsInfo.setStatus(99);// 初次短信判断标记--99
				smsInfo.setCreatedDate(myDate);
				smsInfo.setCreatedBy("1");
				smsInfo.setType(orderSetupStatus);
				map.put("smsInfo", smsInfo);
				return this.realStartJudge(map);
			}else{
				this.logger.debug("*****tid:" + parseJSON.getString("tid") + ",类型："+ orderSetupStatus + "用户余额不足******");
			}
		}else{
			this.logger.debug("*****tid:" + parseJSON.getString("tid") + ",类型："+ orderSetupStatus + "设置未开启******");
		}
		map.put("flag", false);
		return map;
	}

	/**
	 * 真实消息处理方法
	 * 
	 * @param map
	 * @return
	 */
	private Map<String, Object> realStartJudge(Map<String, Object> map) {
		// 账号登录检测 卖家短信高级设置 卖家短信基本设置
		this.judgeUserAccountUtil.setNext(this.judgeUserOrderAdvancedUtil);
		this.judgeUserOrderAdvancedUtil.setNext(this.judgeUserOrderSetUpUtil);
		try {
			// 开始执行任务链
			map = this.judgeUserAccountUtil.startJob(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
		} finally {
		}
		return map;
	}
}
