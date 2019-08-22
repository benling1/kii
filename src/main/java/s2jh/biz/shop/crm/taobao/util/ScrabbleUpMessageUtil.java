package s2jh.biz.shop.crm.taobao.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.taobao.api.domain.Trade;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.order.entity.LogisticsOrder;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.entity.Orders;

public class ScrabbleUpMessageUtil {
	private ScrabbleUpMessageUtil(){}
	/**
	 * 拼凑要发送的短信
	 * @param userName	卖家用户姓名
	 * @param order		订单
	 * @return	短信信息
	 */
	public static String getMessage(SmsSetting smsContent,String userName,Object obj,MyBatisDao myBatisDao){
		String str = null;
		if (smsContent!=null){
			String tid = null;
			str =smsContent.getMessageContent();
			if (obj instanceof Trade) {
				Trade trade = (Trade) obj;
				tid = trade.getTid()+"";
				str =str.replaceAll("【下单时间】",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trade.getCreated()));
				str =str.replaceAll("【卖家昵称】",userName);
				str =str.replaceAll("【买家昵称】",trade.getBuyerNick());
				str =str.replaceAll("【买家姓名】", trade.getReceiverName());
				str =str.replaceAll("【收货人】", trade.getReceiverName());
				str =str.replaceAll("【订单号】",""+trade.getTid());
				str =str.replaceAll("【订单编号】",""+trade.getTid());
				str =str.replaceAll("【订单金额】",trade.getPayment());
				str =str.replaceAll("【到达城市】",trade.getReceiverCity());
				str =str.replaceAll("【短信签名】", "");
				str =str.replaceAll("【退订回N】", "退订回N");
				String messageSignature = smsContent.getMessageSignature();
				messageSignature = messageSignature==null?"【短信签名】": "【"+smsContent.getMessageSignature()+"】";
				if(str.startsWith(messageSignature)||str.startsWith("【")){
				}else{
					str = messageSignature+str;
				}
			}if (obj instanceof Orders) {
				Orders order = (Orders) obj;
				tid = order.getTid()+"";
				str =str.replaceAll("【下单时间】", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreated()));
				str =str.replaceAll("【卖家昵称】",userName);
				str =str.replaceAll("【买家昵称】",order.getBuyerNick());
				str =str.replaceAll("【买家姓名】",order.getReceiverName());
				str =str.replaceAll("【订单号】",order.getTid());
				str =str.replaceAll("【订单金额】",""+order.getPayment());
				str =str.replaceAll("【到达城市】",order.getReceiverCity());
				str =str.replaceAll("【短信签名】", "");
				str =str.replaceAll("【退订回N】", "退订回N");
				String messageSignature = smsContent.getMessageSignature();
				messageSignature = messageSignature==null?"【短信签名】": "【"+smsContent.getMessageSignature()+"】";
				if(str.startsWith(messageSignature)||str.startsWith("【")){
				}else{
					str = messageSignature+str;
				}
			}
			if ( obj instanceof LogisticsOrder) {
				LogisticsOrder logisticsOrder = (LogisticsOrder) obj;
				str =str.replaceAll("【物流公司昵称】",logisticsOrder.getCompanyName());
				str =str.replaceAll("【订单号】",logisticsOrder.getOrderCode());
				str =str.replaceAll("【运单号】",logisticsOrder.getOutSid());
				String messageSignature = smsContent.getMessageSignature();
				messageSignature = messageSignature==null?"【短信签名】": "【"+smsContent.getMessageSignature()+"】";
				if(str.startsWith(messageSignature)||str.startsWith("【")){
				}else{
					str = messageSignature+str;
				}
			}
//			if(str.contains("【物流公司昵称】")||str.contains("【运单号】")){
//				if(tid!=null){//查询具体的物流订单信息  替换文本中的物流公司昵称
////					List<LogisticsOrder> logisticsOrderList = myBatisDao.findList(LogisticsOrder.class.getName(), "findByTidSendMessage", tid);
////					if(logisticsOrderList !=null && logisticsOrderList.size()>0){
////						LogisticsOrder logisticsOrder = logisticsOrderList.get(0);
////						str =str.replaceAll("【物流公司昵称】",logisticsOrder.getCompanyName());
////						str =str.replaceAll("【运单号】",logisticsOrder.getOutSid());
////					}
//				}
//			}
			if(str.contains("退订回")){
				return str;
			}else{
				return str+"退订回N" ;
			}
		} else{
			return null;
		}
			
	}
	/**
	 * 重载方法，根据收到订单提醒表来拼凑短信
	 * @param orderReminder
	 * @param userName
	 * @param order
	 * @return
	 */
	public static String getMessage(OrderReminder orderReminder,String userName,TradeDTO tradeDTO){
		String str = null;
		str = orderReminder.getContent();
		str = str.replaceAll("【下单时间】", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(tradeDTO.getCreated())));
		str = str.replaceAll("【卖家昵称】", " "+userName);
		str = str.replaceAll("【买家昵称】", " "+tradeDTO.getBuyerNick());
		str = str.replaceAll("【买家姓名】", " "+tradeDTO.getReceiverName());
		str = str.replaceAll("【收货链接】", " "+tradeDTO.getReceiverCity()+tradeDTO.getReceiverDistrict());
		str = str.replaceAll("【订单金额】", " "+tradeDTO.getTotalFee());
		str = str.replaceAll("【订单号】", " "+tradeDTO.getTid());
		str = str.replaceAll("【订单金额】", " "+tradeDTO.getTotalFee());
		str = str.replaceAll("【短信签名】", "");
		str = str.replaceAll("【退订回N】", "退订回N");
		String smsSign = orderReminder.getSmsSign();
		smsSign = smsSign ==null?"【短信签名】": "【"+smsSign+"】";
		if(str.startsWith(smsSign)||str.startsWith("【")){
			
		}else{
			str = smsSign+str;
		}
		if(str.contains("退订回")){
			return str;
		}else{
			return str+"退订回N" ;
		}
	}
	/**
	 * 重载方法，根据收到中差评安抚设置来拼凑短信
	 * @param orderRateCareSetup
	 * @param userName
	 * @param order
	 * @return
	 */
	public static String getMessage(OrderRateCareSetup orderRateCareSetup,String userName,Orders order){
		String str = null;
		str = orderRateCareSetup.getContent();
		str = str.replaceAll("【下单时间】", " "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreated()));
		str = str.replaceAll("【卖家昵称】", " "+userName);
		str = str.replaceAll("【买家昵称】", " "+order.getBuyerNick());
		str = str.replaceAll("【买家姓名】", " "+order.getBuyerNick());
		str = str.replaceAll("【收货链接】", " "+order.getReceiverCity()+order.getReceiverDistrict());
		str = str.replaceAll("【订单金额】", " "+order.getTotalFee());
		str = str.replaceAll("【订单号】", " "+order.getOid());
		str = str.replaceAll("【订单金额】", " "+order.getTotalFee());
		str = str.replaceAll("【短信签名】", "");
		str = str.replaceAll("【退订回N】", "退订回N");
		if(str.contains("退订回")){
			return str;
		}else{
			return str+"退订回N" ;
		}
	}
	/**
	 * 对半拆分字符串
	 * @param msgContent
	 * @return 字符串数组，长度为2
	 */
	public static String[] halfAndHalfString(String msgContent){
		String result[] = new String[2];
		int length = msgContent.length()/2;
		result[0] = msgContent.substring(0, length);
		result[1] = msgContent.substring(length);
		return result;
	}
	/**
	 * 字符串长度以67位单位，被多段拆分
	 * @param msgContent
	 * @return 字符串数组，长度为 （（字符串+66）/67）
	 */
	public static String[] moreSplitString(String msgContent){
		String result[] = null;
		if(msgContent.length()>70){
			int length = (msgContent.length()+66)/67;
			result = new String[length];
			for (int i = 0; i < length-1; i++) {
				result[i] = msgContent.substring(i*67, (i+1)*67);
			}
			result[length-1] = msgContent.substring((length-1)*67);
		}else{
			return halfAndHalfString(msgContent);
		}
		return result;
	}
	/**
	 * 重载方法，订单短信群发拼凑短信
	 * @param orderReminder
	 * @param userName
	 * @param order
	 * @return
	 */
//	public static String getMessage(String content,String userName,TransactionOrder trade,String smsSign){
//		if(trade != null){
//			String str = null;
//			str = content;
//			str = str.replaceAll("【下单时间】", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trade.getCreated()));
//			str = str.replaceAll("【卖家昵称】", " "+userName);
//			str = str.replaceAll("【买家昵称】", " "+trade.getBuyerNick());
//			str = str.replaceAll("【买家姓名】", " "+trade.getBuyerNick());
//			/*str = str.replaceAll("【收货链接】", " "+order.getReceiverCity()+order.getReceiverDistrict());*/
//			str = str.replaceAll("【订单金额】", " "+trade.getTotalFee());
//			str = str.replaceAll("【订单号】", " "+trade.getTid());
//			str = str.replaceAll("【订单编号】", " "+trade.getTid());
//			str = str.replaceAll("【收货人】", " "+trade.getReceiverName());
//			str = str.replaceAll("【订单金额】", " "+trade.getTotalFee());
//			str = str.replaceAll("【短信签名】", "");
//			str = str.replaceAll("【退订回N】", "退订回N");
//			smsSign = smsSign ==null?"【短信签名】": "【"+smsSign+"】";
//			if(str.startsWith(smsSign)||str.startsWith("【")){
//				
//			}else{
//				str = smsSign+str;
//			}
//			if(str.contains("退订回")){
//				return str;
//			}else{
//				return str+"退订回N" ;
//			}
//		}else{
//			return content;
//		}
//	}
	
	public static String getMessage(String content,String userName,TradeDTO trade,String smsSign){
		if(trade != null){
			String str = null;
			str = content;
			str = str.replaceAll("【下单时间】", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trade.getCreated()));
			str = str.replaceAll("【卖家昵称】", " "+userName);
			str = str.replaceAll("【买家昵称】", " "+trade.getBuyerNick());
			str = str.replaceAll("【买家姓名】", " "+trade.getBuyerNick());
			/*str = str.replaceAll("【收货链接】", " "+order.getReceiverCity()+order.getReceiverDistrict());*/
			str = str.replaceAll("【订单金额】", " "+trade.getTotalFee());
			str = str.replaceAll("【订单号】", " "+trade.getTid());
			str = str.replaceAll("【订单编号】", " "+trade.getTid());
			str = str.replaceAll("【收货人】", " "+trade.getReceiverName());
			str = str.replaceAll("【订单金额】", " "+trade.getTotalFee());
			str = str.replaceAll("【短信签名】", "");
			str = str.replaceAll("【退订回N】", "退订回N");
			smsSign = smsSign ==null?"【短信签名】": "【"+smsSign+"】";
			if(str.startsWith(smsSign)||str.startsWith("【")){
				
			}else{
				str = smsSign+str;
			}
			if(str.contains("退订回")){
				return str;
			}else{
				return str+"退订回N" ;
			}
		}else{
			return content;
		}
	}
	
//	public static String getMessage(String content,String userName,TransactionOrderDTO trade,String smsSign){
//		if(trade != null){
//			String str = null;
//			str = content;
//			str = str.replaceAll("【下单时间】", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trade.getCreated()));
//			str = str.replaceAll("【卖家昵称】", " "+userName);
//			str = str.replaceAll("【买家昵称】", " "+trade.getBuyerNick());
//			str = str.replaceAll("【买家姓名】", " "+trade.getBuyerNick());
//			/*str = str.replaceAll("【收货链接】", " "+order.getReceiverCity()+order.getReceiverDistrict());*/
//			str = str.replaceAll("【订单金额】", " "+trade.getTotalFee());
//			str = str.replaceAll("【订单号】", " "+trade.getTid());
//			str = str.replaceAll("【订单编号】", " "+trade.getTid());
//			str = str.replaceAll("【收货人】", " "+trade.getReceiverName());
//			str = str.replaceAll("【订单金额】", " "+trade.getTotalFee());
//			str = str.replaceAll("【短信签名】", "");
//			str = str.replaceAll("【退订回N】", "退订回N");
//			smsSign = smsSign ==null?"【短信签名】": "【"+smsSign+"】";
//			if(str.startsWith(smsSign)||str.startsWith("【")){
//				
//			}else{
//				str = smsSign+str;
//			}
//			if(str.contains("退订回")){
//				return str;
//			}else{
//				return str+"退订回N" ;
//			}
//		}else{
//			return content;
//		}
//	}
	
	
	
	
}




//String replace[] = str.split("】");
//StringBuffer sms = new StringBuffer("【");
//sms.append(smsContent.getMessageSignature()).append("】");
//for (int i = 0; i < replace.length; i++) {
//	switch (replace[i]) {
//		case "【付款链接":{
//			break;
//		}
//		case "【订单号":{
//			sms = sms.append(order.getOid());
//			break;
//		}	
//		case "【下单时间":{
//			str.replaceAll("【下单时间】", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreated()));
//			break;
//		}	
//		case "【卖家昵称":{
//			str.replaceAll("【卖家昵称】", order.getSellerNick());
//			break;
//		}	
//		case "【买家昵称":{
//			str.replaceAll("【买家昵称】", order.getBuyerNick());
//			break;
//		}	
//		case "【买家姓名":{
//			str.replaceAll("【买家昵称】", order.getBuyerNick());
//			break;
//		}
//		case "【收货链接":{
//			//收获城市+收获地区
//			str.replaceAll("【收货链接】", order.getReceiverCity()+order.getReceiverDistrict());
//			break;
//		}
//		case "【订单金额":{
//			str.replaceAll("【订单金额】", order.getTotalFee());
//			break;
//		}
//		case "【到达城市":{
//			
//			break;
//		}
//		case "":{
//			break;
//		}
//		default:
//			break;
//	}
//	
//}
//sms.append(replace[replace.length-1]);