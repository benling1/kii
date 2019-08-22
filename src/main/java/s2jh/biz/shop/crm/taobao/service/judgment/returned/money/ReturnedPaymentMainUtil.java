package s2jh.biz.shop.crm.taobao.service.judgment.returned.money;

import java.text.SimpleDateFormat;
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
import s2jh.biz.shop.crm.taobao.service.judgment.JudgeUserAccountUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.JudgeUserOrderSetUpUtil;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;
@Component  //回款提醒
@Deprecated 
public class ReturnedPaymentMainUtil {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ReturnedPaymentMainUtil.class);
	@Autowired
	private JudgeUserAccountUtil judgeUserAccountUtil;
	@Autowired
	private TransactionOrderService transactionOrderService;
	@Autowired
	private	JudgeUserOrderSetUpUtil judgeUserOrderSetUpUtil;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	public Map<String,Object> sendMessage(JSONObject parseJSON,Date myDate,String orderSetupStatus) throws Exception{
		Map<String,Object> map = new ConcurrentHashMap<>();
		String tid = parseJSON.getString("tid");
		Trade trade = this.transactionOrderService.queryTrade(tid);
		map.put("parseJSON", parseJSON);
		if(trade ==null){
			String sessionKey = judgeUserUtil.getUserTokenByRedis(String.valueOf(map.get("sellerName")));
			trade = TaoBaoClientUtil.getTradeByTaoBaoAPI(Long.parseLong(tid), sessionKey);
		}
		if(trade!=null){
			String buyerName = trade.getBuyerNick();
			String sellerName = trade.getSellerNick();
			OrderSetup orderSetup  = judgeUserUtil.isOrderSetupOpen(sellerName, orderSetupStatus);
			if(orderSetup!=null){
				UserInfo user = judgeUserUtil.isNormalUser(sellerName);
				if(user!=null){
					map.put("sellerName", sellerName);
					map.put("trade", trade);
					map.put("buyerName", buyerName);
					map.put("settingType", orderSetupStatus);
					map.put("flag", true);
					map.put("tid", tid);
					map.put("tmc", 1);
					map.put("startDateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(parseJSON.getString("time")));
					SmsSendInfo smsInfo  = new SmsSendInfo();
					smsInfo.setChannel("淘宝");
					//设置短信发送的  发送时间，卖家账号 ，短信内容（暂时未JSON数据），卖家姓名
					smsInfo.setSendTime(myDate);
					smsInfo.setStatus(99);
					smsInfo.setCreatedDate(myDate);
					smsInfo.setCreatedBy("1");
					smsInfo.setType(orderSetupStatus);
					map.put("myDate", myDate);
					smsInfo.setUserId(sellerName);
					smsInfo.setNickname(buyerName);
					map.put("smsInfo", smsInfo);
					this.judgeUserAccountUtil.setNext(this.judgeUserOrderSetUpUtil);
					map = this.judgeUserAccountUtil.startJob(map);
				}else{
					map.put("flag", false);
				}
			}else{
				map.put("flag", false);
			}
			this.logger.debug("*******************tid:"+tid+",类型："+orderSetupStatus+",回款提醒的的逻辑结束："+map.get("flag")+"********************");
		}else{
			map.put("orderIsNull", true);
			map.put("flag", false);
			this.logger.debug("****************************订单号："+tid+"*****       回款提醒的订单查询不到啦-.-          *****************************************");
		}
		return map;
	}
}
