package s2jh.biz.shop.crm.taobao.service.judgment.cowry;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.judgment.JudgeUserAccountUtil;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;

@Component
@Deprecated 
public class GoodsCareMainUtil {
	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(GoodsCareMainUtil.class);
	@Autowired
	private JudgeUserAccountUtil judgeUserAccountUtil;
	@Autowired
	private TransactionOrderService transactionOrderService;
	@Autowired
	private GoodsCowryCareUtil goodsCowryCareUtil;
	@Autowired
	private JudgeUserUtil judgeUserUtil;

	public Map<String, Object> sendMessage(JSONObject parseJSON, Date myDate,
			String orderSetupStatus, String type) throws Exception {
		Map<String, Object> map = new ConcurrentHashMap<>();
		String buyerName = null;
		String sellerName = null;
		String tid = parseJSON.getString("tid");
		map.put("flag", true);
		map.put("tid", tid);
		map.put("myDate", myDate);
		map.put("tmc", 1);
		SmsSendInfo smsInfo = new SmsSendInfo();
		smsInfo.setChannel("淘宝");
		// 设置短信发送的 发送时间，卖家账号 ，短信内容（暂时未JSON数据），卖家姓名
		smsInfo.setSendTime(myDate);
		smsInfo.setStatus(99);
		smsInfo.setCreatedDate(myDate);
		smsInfo.setCreatedBy("1");
		smsInfo.setType(orderSetupStatus);
		map.put("settingType", orderSetupStatus);
		map.put("parseJSON", parseJSON);
		if (OrderSettingInfo.COWRY_CARE.equalsIgnoreCase(orderSetupStatus)) {
			// 如果是货到付款，直接取得当前时间为宝贝关怀的开始时间点
			map.put("startDateTime", myDate);
		}
		if (TradesInfo.CHANGE_JSON_INFO.equals(type)) { // 货到付款
			buyerName = parseJSON.getString("buyer_nick");
			sellerName = parseJSON.getString("seller_nick");
			OrderSetup orderSetup = judgeUserUtil.isOrderSetupOpen(sellerName,orderSetupStatus);
			if (orderSetup != null) {
				UserInfo user = judgeUserUtil.isNormalUser(sellerName);
				if (user != null) {
					map.put("oid", parseJSON.getString("oid"));
					map.put("sellerName", sellerName);
					map.put("buyerName", buyerName);
					smsInfo.setUserId(sellerName);
					smsInfo.setNickname(buyerName);
					map.put("smsInfo", smsInfo);
					map = realSend(map);
					this.logger.debug("*******************tid: "+ map.get("tid") + " ,类型："+ map.get("settingType")+ ",宝贝关怀--货到付款--逻辑走完啦  ****判断结果："+map.get("flag")+"****************");
				} else {
					map.put("flag", false);
				}
			} else {
				map.put("flag", false);
			}

		} else if (TradesInfo.SINGED_JSON_INFO.equals(type)) {// 物流正常签收
			Trade trade = this.transactionOrderService.queryTrade(tid);
			if (trade != null) {
				buyerName = trade.getBuyerNick();
				sellerName = trade.getSellerNick();
				OrderSetup orderSetup = judgeUserUtil.isOrderSetupOpen(sellerName, orderSetupStatus);
				if (orderSetup != null) {
					UserInfo user = judgeUserUtil.isNormalUser(sellerName);
					if (user != null) {
						map.put("sellerName", sellerName);
						map.put("buyerName", buyerName);
						smsInfo.setUserId(sellerName);
						smsInfo.setNickname(buyerName);
						map.put("trade", trade);
						map.put("smsInfo", smsInfo);
						map = realSend(map);
						this.logger.debug("*******************tid: "+ map.get("tid") + " ,类型："+ map.get("settingType")+ ",宝贝关怀--物流签收--逻辑走完啦 ，判断结果："+map.get("flag")+" ********************");
					} else {
						map.put("flag", false);
					}
				} else {
					map.put("flag", false);
				}
			} else {
				map.put("orderIsNull", true);
				map.put("flag", false);
				this.logger.debug("*******************tid: "
						+ map.get("tid") + " ,类型："
						+ map.get("settingType")
						+ ",宝贝关怀--物流签收--订单查询不到 ********************");
				this.logger
						.debug("****************************卖家："
								+ sellerName
								+ ",买家："
								+ buyerName
								+ "*****       宝贝关怀--物流签收--订单查询不到          *****************************************");
			}
		}
		return map;
	}

	private Map<String, Object> realSend(Map<String, Object> map) {
		// 账号登录检测 卖家短信高级设置 卖家短信基本设置
		this.judgeUserAccountUtil.setNext(this.goodsCowryCareUtil);
		try {
			// 开始执行任务链
			map = this.judgeUserAccountUtil.startJob(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return map;
	}
}
