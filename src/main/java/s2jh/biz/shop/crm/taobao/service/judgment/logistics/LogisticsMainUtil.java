package s2jh.biz.shop.crm.taobao.service.judgment.logistics;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lab.s2jh.core.service.RedisLockServiceImpl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;

import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.order.service.TransactionTradeService;
import s2jh.biz.shop.crm.taobao.service.judgment.JudgeUserOrderAdvancedUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.JudgeUserOrderSetUpUtil;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.utils.MyBeanUtils;
/**
 * 物流提醒判断
 * @author Administrator
 *
 */
@Component
@Deprecated 
public class LogisticsMainUtil{

	@Autowired
	private JudgeUserLogisticsAccountUtil judgeUserLogisticsAccountUtil;
	@Autowired
	private JudgeUserOrderAdvancedUtil judgeUserOrderAdvancedUtil;
	@Autowired
	private JudgeUserOrderSetUpUtil judgeUserOrderSetUpUtil;
	@Autowired
	private JudgeDelayRemindUtil judgeDelayRemindUtil;
	@Autowired
	private TransactionTradeService transactionTradeService;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	@Autowired
	private RedisLockServiceImpl redisLock;
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(LogisticsMainUtil.class);
	public Map<String,Object> logisticsSendMessage(JSONObject parseJSON,Date myDate,String orderSetupStatus) throws Exception{
		long startTime = System.currentTimeMillis();
		Map<String,Object> map = new ConcurrentHashMap<>();
		map.put("tmc", 1);
		map.put("parseJSON", parseJSON);
		String tid = parseJSON.getString("tid");//订单编号
//		String oid = parseJSON.getString("oid");//订单编号
		String outSid = null;
		if (parseJSON.containsKey("out_sid")) {
			outSid = parseJSON.getString("out_sid");//运单号
			map.put("outSid", outSid);
		}
		if (parseJSON.containsKey("company_name")) {
			String company_name = parseJSON.getString("company_name");//物流公司名称
			map.put("company_name", company_name);
			
		}
		String desc = parseJSON.getString("desc");
		String buyerName=null;
		String sellerName = null;
		Trade trade = transactionTradeService.queryTrade(tid);
		if(trade!=null){
			List<Order> orderList = trade.getOrders();
			//获取到其中一个子订单
			if (orderList!=null&&orderList.size()>0) {
				Order order = orderList.get(0);
				map.put("oid", order.getOid());
				Orders orders   = new  Orders();
				MyBeanUtils.copyProperties(orders, order);
				map.put("order", orders);
			}
			
			buyerName = trade.getBuyerNick();
			sellerName = trade.getSellerNick();
			map.put("buyerName",buyerName);
			map.put("sellerName", sellerName);
			map.put("trade", trade);
			
			map.put("desc", desc);
			UserInfo user = judgeUserUtil.isNormalUser(sellerName);
			if (user!=null) {
				map.put("user", user);
				OrderSetup orderSetup = judgeUserUtil.isOrderSetupOpen(sellerName, orderSetupStatus);
				if (orderSetup!=null) {
					long start = System.currentTimeMillis();
					if(!this.isLocked(outSid, orderSetupStatus)){
						long end = System.currentTimeMillis();
						this.logger.info(outSid+"_"+orderSetupStatus+"_outSidLock 物流缓存加锁失败,花费"+(end-start)+"ms,"+parseJSON);
						map.put("flag", false);
						return map;
					}
//					if("7".equals(orderSetupStatus)){
//						boolean smsFlag = this.smsSendInfoScheduleService.findSentCitySms(trade.getSellerNick(), trade.getReceiverMobile(), "7");
//						if(smsFlag){
//							long end = System.currentTimeMillis();
//							this.logger.info("同城短信已经发送过，无需重复发送判断"+(end-start)+"ms,"+parseJSON);
//							map.put("flag", false);
//							return map;
//						}
//					}
					long end = System.currentTimeMillis();
					this.logger.info(outSid+"_"+orderSetupStatus+"_outSidLock 物流缓存设置成功,花费"+(end-start)+"ms,"+parseJSON);
					map.put("orderSetup", orderSetup);
					SmsSendInfo smsInfo  = new SmsSendInfo();
					smsInfo.setUserId(sellerName);
					smsInfo.setNickname(buyerName);
					map.put("settingType", orderSetupStatus);
					map.put("tid", tid);
				
					map.put("flag", true);
					map.put("myDate", myDate);
					//获取到收货城市
					String receiverCity = trade.getReceiverCity();
					//如果收货城市为以下四个城市，则说明是直辖市
					String city ="北京市，上海市，天津市，重庆市";
					if (city.contains(receiverCity)) {
						//获取直辖市
						receiverCity = trade.getReceiverState();
					}
					String action = parseJSON.getString("action");
					map.put("action", action);
					if (orderSetupStatus.equals("7")) {
						//如果到货城市和收货城市不同
						if (!desc.contains(receiverCity)) {
							map.put("flag", false);
							return map;
						}
					}
					//设置短信发送的  发送时间，卖家账号 ，短信内容（暂时未JSON数据），卖家姓名
					smsInfo.setSendTime(myDate);
					
					smsInfo.setContent(parseJSON.toString());
					smsInfo.setChannel("淘宝");
					if(tid != null && !"".equals(tid)){
						smsInfo.setTid(Long.parseLong(tid));
					}
					smsInfo.setStatus(99);//初次短信判断标记--99
					smsInfo.setCreatedDate(myDate);
					smsInfo.setCreatedBy(sellerName);
					smsInfo.setType(orderSetupStatus);
					map.put("smsInfo", smsInfo);
					long endTime = System.currentTimeMillis();
					long result = endTime-startTime;
					if(result>5000){
						logger.debug("物流花费时间过长    ："+(result)+" ms,tid ：" + map.get("tid"));
					}
					//账号登录检测   卖家短信高级设置  卖家短信基本设置
					this.judgeUserLogisticsAccountUtil.setNext(this.judgeUserOrderAdvancedUtil);
					this.judgeUserOrderAdvancedUtil.setNext(this.judgeUserOrderSetUpUtil);
					try{
						//开始执行任务链
						map = this.judgeUserLogisticsAccountUtil.startJob(map);
					}catch(Exception e){
						e.printStackTrace();
					}finally {
					}
				}else{
					map.put("flag", false);
					return map;
				}
				
			}else{
				map.put("flag", false);
				return map;
			}
			
		}else{
			map.put("flag", false);
			map.put("orderIsNull", true);
		}
		return map;
	}
	public Map<String,Object> logisticsSendMessage(Map<String,Object> map) throws Exception{
		try{
			//开始执行任务链
			map =judgeDelayRemindUtil.startJob(map);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
		}
		return map;
	}
	/**
	 * 物流加锁
	* 创建人：邱洋
	* @Title: isLocked 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param outSid
	* @param @param orderType
	* @param @param time
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	private boolean isLocked(String outSid,String orderType){
		if(ValidateUtil.isEmpty(outSid) ||ValidateUtil.isEmpty(orderType) ){
			return false;
		}
		long result = this.redisLock.setnx(outSid+"_"+orderType+"_outSidLock", System.currentTimeMillis()+"", 3600L);
		if(result==1){
			return true;
		}else{
			return false;
		}
	}
}
