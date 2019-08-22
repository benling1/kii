package s2jh.biz.shop.crm.taobao.service.judgment.logistics.delay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.CacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.TransactionTradeService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.util.ScrabbleUpMessageUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;

import com.taobao.api.SecretException;
import com.taobao.api.domain.Trade;
@Component
@Deprecated 
public class JudgeDelayRemindUserAccountUtil {

	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private TradeInfoService tradeInfoService;
	@Autowired
	private TransactionTradeService transactionTradeService;
	@Autowired
	private CacheService cacheService;
	public Map<String, Object> judgeDelayRemindUserAccount(Map<String, Object> map){
		String sellerName = (String)map.get("sellerName");
		String settingType = (String)map.get("settingType");
		//查询卖家用户，检查账号是否正常
		//查询出卖家用户
		UserInfo user =myBatisDao.findBy(UserInfo.class.getName(), "findUserInfo", sellerName);
		//用户不是被锁定的，用户的账号到期时间大于当前时间 ,用户的短信剩余数量大于0 
		if(user != null  &&
				user.getStatus()==0&& 
				user.getExpirationTime()!=null &&
				user.getExpirationTime().getTime()>=new Date().getTime()){
			map.put("user", user);
			OrderSetup orderSetup = myBatisDao.findBy(OrderSetup.class.getName(), "findOrderSetupByUserIdAndSettingTypeSend", map);
			if(orderSetup !=null && 
					orderSetup.getStatus()!=null){
				if(OrderSettingInfo.ORDER_SETUP_OPEN.equals(orderSetup.getStatus())){
					Date myDate = new Date();
					//根据map查询 淘宝得到订单集合
//					List<Orders> orderList = transactionTradeService.queryOrderList(map);
//					List<Orders> orderList = transactionTradeService.findOrderList(map);
					Long bTime = 0L;
					Long eTime = 0L;
					String orderScopeOne = (String) map.get("orderScopeOne");
					String orderScopeTwo = (String) map.get("orderScopeTwo");
					if(null!=orderScopeOne&&!"".equals(orderScopeOne)&&null!=orderScopeTwo&&!"".equals(orderScopeTwo)){
						SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try {
							bTime = fm.parse(orderScopeOne + " 00:00:00").getTime();
							eTime = fm.parse(orderScopeTwo + " 23:59:59").getTime();
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					List<TradeDTO> tradeList = tradeInfoService.findTradeByDate(sellerName, bTime, eTime);
//					List<OrdersDTO> orderList = new ArrayList<OrdersDTO>();
//					//遍历订单集合
//					if(tradeList!=null&&tradeList.size()>0){
//						for(TradeDTO td :tradeList){
//							orderList.addAll(td.getOrders());
//						}
//					}
//					//--------------------------测试数据
//					map.put("orderList", orderList);
					//--------------------------
					//遍历订单集合
					//创建信息集合
					List<SmsSendInfo> smsInfoList = new ArrayList<SmsSendInfo>();
					//获取短信设置
					SmsSetting smsContent = myBatisDao.findBy(SmsSetting.class.getName(), "findSmsSettingSend",map);
					//如果短信设置不为空
					if (smsContent!=null&&smsContent.getMessageContent()!=null) {
						for (TradeDTO trade : tradeList) {
							Trade td = new Trade();
							td.setCreated(trade.getCreatedDate());
							String buyerNick = null;
							String buyerName = null;
							try {
								String token = cacheService.getJsonStr(
										RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE,
										RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY
												+ trade.getSellerNick());
								if(null!=trade.getBuyerNick()&&!"".equals(trade.getBuyerNick())&&EncrptAndDecryptClient.isEncryptData(trade.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
									buyerNick = EncrptAndDecryptClient.getInstance().decrypt(trade.getBuyerNick(), EncrptAndDecryptClient.SEARCH, token);
									td.setBuyerNick(buyerNick);
								}
								if(null!=trade.getReceiverName()&&!"".equals(trade.getReceiverName())&&EncrptAndDecryptClient.isEncryptData(trade.getReceiverName(), EncrptAndDecryptClient.SEARCH)){
									buyerName = EncrptAndDecryptClient.getInstance().decrypt(trade.getReceiverName(), EncrptAndDecryptClient.SEARCH, token);
									td.setReceiverName(buyerName);
								}
							} catch (SecretException e) {
								e.printStackTrace();
							}
							td.setTid(Long.parseLong(trade.getTid()));
							td.setPayment(trade.getPayment().toString());
							td.setReceiverCity(trade.getReceiverCity());
							//创建一条信息
							SmsSendInfo smsInfo = new SmsSendInfo();
//							smsInfo.setAutograph(smsContent.getMessageSignature());
							String sms = ScrabbleUpMessageUtil.getMessage(smsContent, sellerName, td,this.myBatisDao);
							//计算短信长度
							int messageCount = sms.length();
							if(messageCount<=70){
								messageCount=1;
							}else{
								messageCount = (messageCount+66)/67;
							}
							smsInfo.setActualDeduction(messageCount);
							smsInfo.setContent(sms);
							smsInfo.setTid(Long.parseLong(trade.getTid()));
//							smsInfo.setAutograph(smsContent.getMessageSignature());
							smsInfo.setPhone(trade.getReceiverMobile());
							
							smsInfo.setSendTime(myDate);
							smsInfo.setUserId(sellerName);
							smsInfo.setChannel("淘宝");
							if(buyerName!=null){
								smsInfo.setNickname(buyerName);//设置买家名
							}else{
								smsInfo.setNickname(trade.getBuyerNick());
							}
							smsInfo.setStatus(99);//初次短信判断标记--99
							smsInfo.setCreatedDate(myDate);
							smsInfo.setCreatedBy("1");
							smsInfo.setType(settingType);//短信类型
							smsInfoList.add(smsInfo);
						}
						map.put("smsInfoList", smsInfoList);
						map.put("flag", true);
					}else{
						map.put("flag", false);
					}
				}else{
					map.put("flag", false);
				}
			}else{
				map.put("flag", false);
			}
			
		}else{
			//用户不存在，黑名单或余额不足
			map.put("flag", false);
		}
		return map;
	}
}
