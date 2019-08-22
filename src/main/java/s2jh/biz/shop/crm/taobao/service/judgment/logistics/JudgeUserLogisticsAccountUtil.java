package s2jh.biz.shop.crm.taobao.service.judgment.logistics;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.LogisticsTraceSearchRequest;
import com.taobao.api.response.LogisticsTraceSearchResponse;

import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.other.service.GenerateLinkService;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.taobao.util.ScrabbleUpMessageUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;
/**
 * 查询
 * @author Administrator
 *
 */
@Component
@Deprecated 
public class JudgeUserLogisticsAccountUtil extends AbstractJudgeOrderSetUp{
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeUserLogisticsAccountUtil.class);
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		long startTime = System.currentTimeMillis();
		String sellerName = (String)map.get("sellerName");
		Trade trade = (Trade) map.get("trade");
		if(trade==null){
			if(map.containsKey("tid")){
				Long tid = Long.parseLong(String.valueOf(map.get("tid")));
				String sessionKey = judgeUserUtil.getUserTokenByRedis(sellerName);
				trade = TaoBaoClientUtil.getTradeByTaoBaoAPI(tid, sessionKey);
			}
		}
		String phone =  trade.getReceiverMobile()==null?trade.getReceiverPhone():trade.getReceiverMobile();
		map.put("phone", phone);
		SmsSendInfo smsInfo = (SmsSendInfo) map.get("smsInfo");
		//将短信内容拼凑好
		SmsSetting smsContent = myBatisDao.findBy(SmsSetting.class.getName(), "findSmsSettingSend",map);
//		if (smsContent!=null && smsContent.getMessageSignature()!=null) {
//			smsInfo.setAutograph(smsContent.getMessageSignature());
//		}
		if(smsContent!=null&&smsContent.getMessageContent()!=null){
			String sms = ScrabbleUpMessageUtil.getMessage(smsContent,sellerName, trade,this.myBatisDao);//替换短信内容中的标签对应值
			if("6".equals(String.valueOf(map.get("settingType")))){
				try {
//					logger.error("tid:"+trade.getTid()+"是发货提醒，调用物流查询接口");
					sms = this.getLogisticsCompany(sms,trade);
					logger.debug("tid:"+trade.getTid()+"发货提醒物流公司昵称替换完毕："+sms);
				} catch (ApiException e) {
					e.printStackTrace();
				}
			}else{
//				logger.error("tid:"+trade.getTid()+"不是发货提醒，不调用物流查询接口");
			}
//				sms = ScrabbleUpMessageUtil.getMessage(smsContent,sellerName, logisticsOrder);
			String company_name = (String)map.get("company_name");
			if (company_name!=null &&!"".equals(company_name)) {
				sms = sms.replaceAll("【物流公司昵称】", company_name);
				sms = sms.replaceAll("【物流公司名称】", company_name);
			}else{
				sms = sms.replaceAll("【物流公司昵称】", "");
				sms = sms.replaceAll("【物流公司名称】", "");
			}
			
			String outSid =(String)map.get("outSid");
			if (outSid!=null && !"".equals(outSid)) {
				sms = sms.replaceAll("【运单号】",outSid);
				
			}else{
				sms = sms.replaceAll("【运单号】","");
			}
			//添加付款链接或者物流信息查看链接（生成订单短链接）
			if(sms.contains("【付款链接】")||sms.contains("【物流链接】")||sms.contains("【确认收货链接】")||sms.contains("【订单链接】")){
				String tid =null;
				if(trade.getTid()!=null){
					tid= trade.getTid().toString();
				}else if(map.containsKey("tid")){
					tid = (String)map.get("tid");
				}
				UserInfo user = (UserInfo) map.get("user");
				String link = GenerateLinkService.getLink(tid,user.getAccess_token());
				if(link!=null){
					sms = sms.replaceAll("【付款链接】", " "+link+" ");
					sms = sms.replaceAll("【物流链接】", " "+link+" ");
					sms = sms.replaceAll("【订单链接】", " "+link+" ");
					sms = sms.replaceAll("【确认收货链接】", " "+link+" ");
				}
			}
			
			//计算短信长度
			int messageCount = sms.length();
			if(messageCount<=70){
				messageCount=1;
			}else{
				messageCount = (messageCount+66)/67;
			}
			this.logger.debug("要发送的短信是：" + sms);
			smsInfo.setChannel(trade.getTradeFrom());
			smsInfo.setActualDeduction(messageCount);
			smsInfo.setContent(sms);
			smsInfo.setTid(trade.getTid());
//			smsInfo.setAutograph(smsContent.getMessageSignature());
			smsInfo.setPhone(phone);
			map.put("smsInfo", smsInfo);
			map.put("flag", true);
			//责任链判断
			if(super.getNext()!=null){
				map = super.getNext().startJob(map);
			}
		}else{
			map.put("flag", false);
			smsInfo.setContent("卖家未设置短信提示语");
			smsInfo.setStatus(10);
		}
		long endTime = System.currentTimeMillis();
		long result = endTime-startTime;
		if(result>5000){
			logger.debug("物流花费时间过长    ："+(result)+" ms,tid ：" + map.get("tid"));
		}
		return map;
	}
	private String getLogisticsCompany(String str,Trade trade) throws ApiException{
		if(str.contains("【物流公司昵称】")||str.contains("【运单号】")||str.contains("【物流公司")||str.contains("【物流公司名称】")){
			if(trade!=null){//查询具体的物流订单信息  替换文本中的物流公司昵称
//				String status = trade.getStatus();
//				if(TradesInfo.SELLER_CONSIGNED_PART.equals(status)||TradesInfo.WAIT_BUYER_CONFIRM_GOODS.equals(status)||
//						TradesInfo.TRADE_BUYER_SIGNED.equals(status)||TradesInfo.TRADE_FINISHED.equals(status) ){//需要提前判断交易是否已经发货，对于没有发货的订单不要去取流转信息。
					TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
							taobaoInfo.appKey, taobaoInfo.appSecret);
					LogisticsTraceSearchRequest req = new LogisticsTraceSearchRequest();
					req.setTid(trade.getTid());
					req.setSellerNick(trade.getSellerNick());
					LogisticsTraceSearchResponse rsp = client.execute(req);
					String result = rsp.getBody();
					if(result.contains("当前操作的订单是拆单订单，拆单标记和子订单列表都必须传递")){
//						req.setIsSplit(1L);
//						req.setSubTid(trade.getOrders().get(0).getOid()+"");
//						rsp = client.execute(req);
//						result = rsp.getBody();
						str = str.replaceAll("【物流公司昵称】", "");
						str = str.replaceAll("【物流公司名称】", "");
						str = str.replaceAll("【运单号】", "");
					}
					if(result.startsWith("{\"logistics_trace_search_response")){
						str = str.replaceAll("【物流公司昵称】", rsp.getCompanyName());
						str = str.replaceAll("【物流公司名称】", rsp.getCompanyName());
						str = str.replaceAll("【运单号】", rsp.getOutSid());
						logger.debug("tid:"+trade.getTid()+"  替换变量成功"+str);
					} else{
						logger.debug("tid:"+trade.getTid()+"物流消息查询失败\r"+rsp.getBody());
					}
//				}else{
//					logger.debug("tid:"+trade.getTid()+"  暂未发货"+status);
//				}
			}
		}else{
			logger.debug("tid:"+trade.getTid()+"  未发现要拼凑的短信变量");
		}
		return str;
	}
}
