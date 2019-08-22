package s2jh.biz.shop.crm.tmc.handler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.SecretException;
import com.taobao.api.domain.Trade;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;
import s2jh.biz.shop.crm.message.service.SmsBlackListService;
import s2jh.biz.shop.crm.tmc.entity.TmcMessages;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;

/** 
 * 校验黑名单过滤 选择
* @author wy
*/
@Component("tradeBlackListHandler")
public class TradeBlackListHandler implements Handler {
	private final Logger logger = Logger.getLogger(TradeBlackListHandler.class);
	@Autowired
	private SmsBlackListService smsBlackListService;
	@Override
	public void doHandle(@SuppressWarnings("rawtypes") Map map) throws HandlerException {
		TmcMessages tmcMessages = (TmcMessages) map.get("tmcMessages");
		if(tmcMessages==null){
			this.logger.debug("传递的对象为空，无法进行判断！！！");
			return;
		}
		if(!tmcMessages.getFlag()){
			return;
		}
		TradeSetup tradeSetup = tmcMessages.getTradeSetup();
		if(tradeSetup == null){
			this.logger.debug("传递的用户设置对象为空，无法进行判断！！！");
			return ;
		}
		if(tmcMessages.getTrade()==null){
			this.logger.debug("订单对象为空，无法进行判断！！！");
			return ;
		}
		Boolean filterBlack = tradeSetup.getFilterBlack();
		if(filterBlack==null){
			return ;
		}
		//过滤
		if(filterBlack){ 
			Trade trade = tmcMessages.getTrade();
			try {
				boolean blackFlag = this.smsBlackListService.isExists(trade.getSellerNick(), trade.getBuyerNick(),trade.getReceiverMobile()==null?trade.getReceiverPhone():trade.getReceiverMobile());
				if(blackFlag){
					//订单来源  不对
					tmcMessages.setFlag(false); 
					this.logger.debug("黑名单筛选不通过 tid:"+tmcMessages.getTid()+",类型："+tmcMessages.getTradeSetup().getType()+" 用户设置的ID:+"+tradeSetup.getId());
					return ;
				}
			} catch (SecretException e) {
				e.printStackTrace();
			}
		}
	}
	
}
