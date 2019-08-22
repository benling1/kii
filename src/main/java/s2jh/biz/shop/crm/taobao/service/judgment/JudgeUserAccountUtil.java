package s2jh.biz.shop.crm.taobao.service.judgment;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.other.service.GenerateLinkService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.taobao.util.ScrabbleUpMessageUtil;

//查询用户的账号是否正常
@Component
@Deprecated 
public class JudgeUserAccountUtil extends AbstractJudgeOrderSetUp {
	@Autowired
	private TransactionOrderService transactionOrderService;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeUserAccountUtil.class);

	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		String tid = String.valueOf(map.get("tid"));
		Trade trade = null;
		if (map.containsKey("trade")) {
			trade = (Trade) map.get("trade");
		} else {
			trade = transactionOrderService.queryTrade(tid);
			if (trade == null) {
				String sessionKey = judgeUserUtil.getUserTokenByRedis((String)map.get("sellerName"));
				trade = TaoBaoClientUtil.getTradeByTaoBaoAPI(Long.parseLong(tid),sessionKey);
			}
		}
		if (trade != null) {
			String settingType = (String) map.get("settingType");
			map = validateTradeFrom(settingType, trade, map);
			boolean flag = (boolean) map.get("flag");
			if (flag) {
				map = saveSmsSendInfo(map, trade);
				flag = (boolean) map.get("flag");
				if (flag) {
					map.put("trade", trade);
					if (super.getNext() != null) {
						map = super.getNext().startJob(map);
					}
					this.logger.debug("*******************tid:" + tid + ",类型："+ map.get("settingType") + ",判断结束："+ map.get("flag") + "********************");
				} else {
					this.logger.debug("*******************tid:" + tid + ",类型："+ map.get("settingType") + ",拼凑短信失败"+ map.get("flag") + "********************");
				}
			} else {
				// 不是聚划算
				this.logger.debug("*******************tid:" + tid + ",类型："+ map.get("settingType")+ ",订单来源判断未通过，结束********************");
				map.put("flag", false);
			}
		} else {
			// 没有查询到订单
			this.logger.debug("*******************tid:" + tid + ",类型："
					+ map.get("settingType")
					+ ",没有查询到订单，结束********************");
			map.put("flag", false);
			map.put("orderIsNull", true);
		}
		return map;
	}

	/**
	 * 在逻辑判断之前，将要发送的短信内容拼凑完毕
	 * 
	 * @param map
	 * @param trade
	 * @return
	 */
	private Map<String, Object> saveSmsSendInfo(Map<String, Object> map,
			Trade trade) {
		boolean flag = (boolean) map.get("flag");
		if (flag) {
			String sellerName = trade.getSellerNick();
			if (trade.getReceiverMobile() == null) { // 手机号码不能为空 如果为空，短信不发送
				if (trade.getReceiverPhone() != null) {
					trade.setReceiverMobile(trade.getReceiverPhone());
				} else {
					map.put("flag", false);
					return map;
				}
			}
			map.put("phone", trade.getReceiverMobile());
			SmsSendInfo smsInfo = (SmsSendInfo) map.get("smsInfo");
			// 将短信内容拼凑好
			SmsSetting smsContent = myBatisDao.findBy(
					SmsSetting.class.getName(), "findSmsSettingSend", map);
			if (smsContent != null && smsContent.getMessageContent() != null) {
				this.logger
						.debug("******************* 短信设置取得成功，开始拼凑短信内容  ********************");
//				smsInfo.setAutograph(smsContent.getMessageSignature());
				String sms = ScrabbleUpMessageUtil.getMessage(smsContent,
						sellerName, trade, this.myBatisDao);

				// String settingType = (String)map.get("settingType");
				if (map.containsKey("parseJSON")) {
					JSONObject parseJSON = (JSONObject) map.get("parseJSON");
					if (parseJSON != null) {

						// 替换短信内容中物流公司昵称标签
						if (parseJSON.containsKey("company_name")) {
							String companyName = parseJSON
									.getString("company_name");
							if (companyName != null && !companyName.equals("")) {
								sms = sms.replaceAll("【物流公司昵称】", companyName);
								sms = sms.replaceAll("【物流公司名称】", companyName);
							}
						}

						// 替换短信内容中物流运单号标签
						if (parseJSON.containsKey("out_sid")) {
							String outSid = parseJSON.getString("out_sid");
							if (outSid != null && !outSid.equals("")) {
								sms = sms.replaceAll("【运单号】", outSid);
							}
						}
						sms = sms.replaceAll("【物流公司昵称】", "");
						sms = sms.replaceAll("【物流公司名称】", "");
						sms = sms.replaceAll("【运单号】", "");
					}
				}

				// 添加付款链接或者物流信息查看链接（生成订单短链接）
				if (sms.contains("【付款链接】") || sms.contains("【物流链接】")
						|| sms.contains("【确认收货链接】") || sms.contains("【订单链接】")) {
					String tid = null;
					if (trade.getTid() != null) {
						tid = trade.getTid().toString();
					} else if (map.containsKey("tid")) {
						tid = (String) map.get("tid");
					}
					String token = judgeUserUtil.getUserTokenByRedis(sellerName);
					String link =null;
					if(token!=null){
						link = GenerateLinkService.getLink(tid,token);
					}
					if (link != null) {
						sms = sms.replaceAll("【付款链接】", " " + link + " ");
						sms = sms.replaceAll("【物流链接】", " " + link + " ");
						sms = sms.replaceAll("【订单链接】", " " + link + " ");
						sms = sms.replaceAll("【确认收货链接】", " " + link + " ");
					}
				}
				this.logger.debug("要发送的短信是：" + sms);
				// 计算短信长度
				int messageCount = sms.length();
				if (messageCount <= 70) {
					messageCount = 1;
				} else {
					messageCount = (messageCount + 66) / 67;
				}
				smsInfo.setChannel(trade.getTradeFrom());
				smsInfo.setActualDeduction(messageCount);
				smsInfo.setContent(sms);
				smsInfo.setTid(trade.getTid());
//				smsInfo.setAutograph(smsContent.getMessageSignature());
				smsInfo.setPhone(trade.getReceiverMobile());
				if (OrderSettingInfo.COWRY_CARE.equalsIgnoreCase((String) map
						.get("settingType"))) {
					// 签收 宝贝关怀
					if (smsContent.getItemId() == null) {
						this.logger.debug("*******************tid:"
								+ map.get("tid") + ",类型："
								+ map.get("settingType")
								+ ",宝贝关怀未设置宝贝！ ********************");
						map.put("flag", false);
					} else {
						map.put("smsSettingItemID", smsContent.getItemId());
						map.put("flag", true);
					}
				}
			} else {
				this.logger.debug("*******************tid:" + map.get("tid")
						+ ",类型：" + map.get("settingType")
						+ ",短信内容未设置，短信不发送 ********************");
				map.put("flag", false);
				smsInfo.setContent("卖家未设置短信提示语");
				smsInfo.setStatus(10);
			}
			map.put("smsInfo", smsInfo);
		}
		return map;
	}

	/**
	 * 检测订单的来源是否符合催付的条件，如果符合催付的条件将 订单创建时间存到 key = startDateTime中
	 * 
	 * @param settingType
	 *            当前判断的类型（下单关怀，催付，付款等）
	 * @param trade
	 *            具体的订单类型
	 * @param map
	 * @return map key = flag value = false 催付的判断不正确 true 符合条件准备开始判断后续逻辑 key =
	 *         startDateTime value = 订单开始时间，延后时间发送短信关键 （催付才有此返回）
	 */
	private Map<String, Object> validateTradeFrom(String settingType,
			Trade trade, Map<String, Object> map) {
		boolean flag = true;
		String tradeFrom = trade.getTradeFrom();
		if (tradeFrom != null) {
			if (OrderSettingInfo.FIRST_PUSH_PAYMENT.equals(settingType)) {
				if (tradeFrom.contains(TradesInfo.ORDER_FROM_JHS)) {
					flag = false; // 订单是聚划算，普通催付不针对聚划算来源订单
					this.logger.debug("*******************tid:"+ map.get("tid") + ",类型：" + map.get("settingType")+ ",订单是聚划算，普通催付不针对聚划算来源订单********************");
				} else {
					flag = true;
					// 保存订单开始时间
					map.put("startDateTime", trade.getCreated());
				}
			} else if (OrderSettingInfo.SECOND_PUSH_PAYMENT.equals(settingType)) {
				if (tradeFrom.contains(TradesInfo.ORDER_FROM_JHS)) {
					flag = false; // 订单是聚划算，普通催付不针对聚划算来源订单
					this.logger.debug("*******************tid:"+ map.get("tid") + ",类型：" + map.get("settingType")+ ",订单是聚划算，普通催付不针对聚划算来源订单********************");
				} else {
					// 判断如果为二次催付，重新设置发送时间
					String sellerName = trade.getSellerNick();
					OrderSetup os = judgeUserUtil.isOrderSetupOpen(sellerName,OrderSettingInfo.FIRST_PUSH_PAYMENT);
					if (os != null) {
						if (os.getStatus().equals("0")
								&& os.getReminderTime() != null
								&& !"".equals(os.getReminderTime())) {
							Date startDate = this.smsSendInfoScheduleService.findByTidAndType(trade.getTid(), OrderSettingInfo.FIRST_PUSH_PAYMENT);// 获取订单创建时间
							if(startDate==null){
								flag = false;
								this.logger.debug("首次催付短信未能获取，二次催付短信时间取不到，取消二次催付"+trade.getTid());
							}else{
								// 保存订单开始时间
								map.put("startDateTime", startDate);
								flag = true;
							}
						} else {
							this.logger.debug("首次催付未开启，二次催付不发送"+trade.getTid());
							flag = false;
						}
					} else {
						this.logger.debug("首次催付未开启，二次催付不发送"+trade.getTid());
						flag = false;
					}
				}
			} else if (OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT
					.equals(settingType)) { // 聚划算催付
				if (tradeFrom.contains(TradesInfo.ORDER_FROM_JHS)) {
					flag = true; // 订单是聚划算
					// 保存订单开始时间
					map.put("startDateTime", trade.getCreated());
				} else {
					this.logger.debug("*******************tid:"+ map.get("tid") + ",类型：" + map.get("settingType")+ ",订单是普通渠道，聚划算催付不针对普通来源订单********************");
					flag = false;
					// 不是聚划算
				}
			}
		} else {
			flag = false;
			this.logger.debug("*******************tid:" + map.get("tid")+ ",类型：" + map.get("settingType")+ ",订单来源是空的********************");
		}
		map.put("flag", flag);
		return map;
	}

}
