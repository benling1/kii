package s2jh.biz.shop.crm.taobao.service.judgment.appraise;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.taobao.util.ScrabbleUpMessageUtil;
@Component
@Deprecated 
public class JudgeAppraiseUserAccountUtil extends AbstractJudgeOrderSetUp {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeAppraiseUserAccountUtil.class);
	@Autowired
	private TransactionOrderService transactionOrderService;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		String sellerName = (String) map.get("sellerName");
		//从map中获取到信息
		String tid = String.valueOf(map.get("tid"));
		String buyerName = String.valueOf(map.get("buyerName"));
		//用户不是被锁定的，用户的账号到期时间大于当前时间 ,用户的短信剩余数量大于0 
			//实际：通过tid和oid去淘宝中查
		Trade trade = this.transactionOrderService.queryTrade(tid);
		if(trade==null){
			String sessionKey = judgeUserUtil.getUserTokenByRedis(sellerName);
			trade = TaoBaoClientUtil.getTradeByTaoBaoAPI(Long.parseLong(tid), sessionKey);
		}
		if(trade!=null){
			if(trade.getModified()==null){
				if(trade.getReceiverPhone()!=null){
					trade.setReceiverMobile(trade.getReceiverPhone());
				}else{
					this.logger.debug("*********中差评的订单手机号码查不到啦，没电话号码怎么发短信 ********"+sellerName+"**********  "+map.get("tid"));
					map.put("flag", false);
					return map;
				}
			}
			map.put("trade", trade);
			map.put("settingType", "21");
			//查询短信设置
			//从map中获取到信息
			OrderRateCareSetup orderRateCareSetup = myBatisDao.findBy(OrderRateCareSetup.class.getName(), "findOrderRateCareSetupandStatus", map);
			if (orderRateCareSetup==null) {
				map.put("flag",false);
				this.logger.debug("********中差评的设置是空 *****"+sellerName+"****************  "+map.get("tid"));
				return map;
			}else{
				map.put("orderRateCareSetup", orderRateCareSetup);
				SmsSetting smsContent = myBatisDao.findBy(SmsSetting.class.getName(), "findSmsSettingSend",map);
				if(smsContent!=null&&smsContent.getMessageContent()!=null){
					SmsSendInfo smsInfo = new SmsSendInfo();
					smsInfo.setTid(Long.parseLong(tid));
					smsInfo.setNickname(buyerName);
					smsInfo.setUserId(sellerName);
					smsInfo.setChannel(trade.getTradeFrom());
//					smsInfo.setAutograph(smsContent.getMessageSignature());
					String sms = ScrabbleUpMessageUtil.getMessage(smsContent,sellerName, trade,this.myBatisDao);
					//计算短信长度
					int messageCount = sms.length();
					if(messageCount<=70){
						messageCount=1;
					}else{
						messageCount = (messageCount+66)/67;
					}
					smsInfo.setActualDeduction(messageCount);
					smsInfo.setContent(sms);
					smsInfo.setTid(trade.getTid());
					
					smsInfo.setType("21");
//					smsInfo.setAutograph(smsContent.getMessageSignature());
					smsInfo.setPhone(trade.getReceiverMobile()==null?trade.getReceiverPhone():trade.getReceiverMobile());
					map.put("smsInfo", smsInfo);
					map.put("flag", true);
				}else{
					this.logger.debug("***********中差评订d的短信内容设置为空**********"+sellerName+"********  "+map.get("tid"));
					map.put("flag", false);
				}
				//责任链判断
				if (super.getNext()!=null) {
					map = super.getNext().startJob(map);
				}
			}
		}else{
			map.put("flag", false);
			this.logger.debug("*******中差评订单查询为空啦，小伙伴快来看呀         *********"+sellerName+"******  "+map.get("tid"));
		}
			
		return map;
	}

}
