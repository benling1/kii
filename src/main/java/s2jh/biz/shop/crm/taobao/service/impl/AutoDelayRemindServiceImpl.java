package s2jh.biz.shop.crm.taobao.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.SecretException;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.taobao.service.AutoDelayRemindService;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.util.ScrabbleUpMessageUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;
@Service
@Deprecated 
public class AutoDelayRemindServiceImpl implements AutoDelayRemindService {

	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private TradeInfoService tradeInfoService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	private static final Log logger = LogFactory.getLog(AutoDelayRemindServiceImpl.class);
	@Override
	public void autoDelayRemindFunction(String sellerName) {

		//查询到所有settingtype是延迟发货11，status是0开启，executegenre是1执行周期每天的设置集合
		OrderSetup orderSetup = new OrderSetup();
		orderSetup.setSettingType("11");
		orderSetup.setUserId(sellerName);
		orderSetup.setExecuteGenre("1");//执行周期每天
		orderSetup.setStatus("0");
		//通过卖家名和settingtype查询用户设置
		List<OrderSetup> orderSetups = myBatisDao.findList(OrderSetup.class.getName(), "findAllDelayRemind", orderSetup);
		if (orderSetups!=null && orderSetups.size()>0) {
			orderSetup = orderSetups.get(0);
			Date lastModifiedDate = orderSetup.getLastModifiedDate();
			
			
			Map<String,Object> map = new HashMap<String,Object>();
			//获取到卖家设置的订单范围
			String orderScopeOne = orderSetup.getOrderScopeOne();
			String orderScopeTwo = orderSetup.getOrderScopeTwo();
			map.put("orderScopeOne", orderScopeOne);
			map.put("orderScopeTwo", orderScopeTwo);
			map.put("sellerName", sellerName);
			map.put("settingType", "11");
			map.put("tmc", 1);
			//查询出卖家用户
			UserInfo user =myBatisDao.findBy(UserInfo.class.getName(), "findUserInfo", sellerName);
			map.put("user", user);
			Date myDate = new Date();
			map.put("myDate", myDate);
			//根据map查询 淘宝得到订单集合
			
			
			Long bTime = 0L;
			Long eTime = 0L;
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
//			List<OrdersDTO> orderList = new ArrayList<OrdersDTO>();
//			//遍历订单集合
//			if(tradeList!=null&&tradeList.size()>0){
//				for(TradeDTO td :tradeList){
//					orderList.addAll(td.getOrders());
//				}
//			}
//			map.put("orderList", orderList);
			
			//创建信息集合
			List<SmsSendInfo> smsInfoList = new ArrayList<SmsSendInfo>();
			//获取短信设置
			SmsSetting smsContent = myBatisDao.findBy(SmsSetting.class.getName(), "findSmsSettingSend",map);
			//如果短信设置不为空
			if (smsContent!=null&&smsContent.getMessageContent()!=null) {
				for (TradeDTO tradeDTO : tradeList) {
					//创建一条信息
					SmsSendInfo smsInfo = new SmsSendInfo();
//					smsInfo.setAutograph(smsContent.getMessageSignature());
					String sms = ScrabbleUpMessageUtil.getMessage(smsContent, sellerName, tradeDTO,this.myBatisDao);
					//计算短信长度
					int messageCount = sms.length();
					if(messageCount<=70){
						messageCount=1;
					}else{
						messageCount = (messageCount+66)/67;
					}
					smsInfo.setActualDeduction(messageCount);
					smsInfo.setContent(sms);
//					smsInfo.setOid(orders.getOid());
					smsInfo.setTid(Long.parseLong(tradeDTO.getTid()));
//					smsInfo.setAutograph(smsContent.getMessageSignature());
					//设置手机号码  解密
					try {
						String sessionKey = judgeUserUtil.getUserTokenByRedis(sellerName);
						smsInfo.setPhone(judgeUserUtil.getDecryptData(tradeDTO.getReceiverMobile(), EncrptAndDecryptClient.PHONE, null, sessionKey));
						smsInfo.setNickname(judgeUserUtil.getDecryptData(tradeDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH, null, sessionKey));
					} catch (SecretException e) {
//						if(ErrorUtil.isInvalidSession(e)) {
//							logger.error("用户sessionKey失效");
//					        // 标记该sessionkey无效，重新授权之前不要再调用
//					    }else{
//					    	logger.error("加密解密出错啦,请直接呼叫wy");
//					    }
//						e.printStackTrace();
						break ;
					}
					smsInfo.setSendTime(myDate);
					smsInfo.setUserId(sellerName);
					smsInfo.setChannel("淘宝");
					//设置买家名
					smsInfo.setStatus(99);//初次短信判断标记--99
					smsInfo.setCreatedDate(lastModifiedDate);
					smsInfo.setCreatedBy("1");
					smsInfo.setType("11");//短信类型
					
					smsInfoList.add(smsInfo);
					
				}
//				map.put("smsInfoList", smsInfoList);
				map.put("flag", true);
			}
			if (smsInfoList!=null && smsInfoList.size()>0) {
				for (SmsSendInfo smsSendInfo : smsInfoList) {
					map.put("smsInfo", smsSendInfo);
					//发送信息
					taoBaoSendMessageUtil.sendSingleMessage(map);
				}
				
			}else{
				//没有要发送的短信
				System.out.println("无可发短信");
			}
		}
		
	}

}
