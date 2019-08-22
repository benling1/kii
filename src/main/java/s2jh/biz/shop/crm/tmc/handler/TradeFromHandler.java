package s2jh.biz.shop.crm.tmc.handler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.tmc.entity.TmcMessages;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;

/** 
 * 校验订单的来源选择
* @author wy
*/
@Component("tradeFromHandler")
public class TradeFromHandler implements Handler {
	private final Logger logger = Logger.getLogger(TradeFromHandler.class);
	
	@Override
	public void doHandle(@SuppressWarnings("rawtypes") Map map) throws HandlerException {
		TmcMessages tmcMessages = (TmcMessages) map.get("tmcMessages");
		if(tmcMessages==null){
			this.logger.debug("传递的对象为空，无法进行判断！！！");
			return;
		}
		if(!tmcMessages.getFlag()){
			//前面判断为不通过，不进行下面判断
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
		String sellerFroms = tradeSetup.getTradeFrom();
		if(ValidateUtil.isEmpty(sellerFroms)){
			return ;
		}
		String[] sellerFromArr = sellerFroms.split(",");
		String tradeFrom = tmcMessages.getTrade().getTradeFrom();
		tmcMessages.setFlag(false); 
		for (String sellerFrom : sellerFromArr) {
			if(tradeFrom.contains(sellerFrom)){
				tmcMessages.setFlag(true);
				return ;
			}
		}
		this.logger.debug("订单来源判断未通过，订单来源: "+tradeFrom+" ,卖家选择的来源: "+sellerFroms+" tid:"+tmcMessages.getTid()+",类型："+tmcMessages.getTradeSetup().getType()+" 用户设置的ID:+"+tradeSetup.getId());
	}
	
}
