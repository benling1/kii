package s2jh.biz.shop.crm.tmc.handler;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Order;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;
import s2jh.biz.shop.crm.tmc.entity.TmcMessages;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;

/** 
 * 校验 商品数量
* @author wy
*/
@Component("tradeProductNumkHandler")
public class TradeProductNumkHandler implements Handler {
	private final Logger logger = Logger.getLogger(TradeProductNumkHandler.class);
	
	@Override
	public void doHandle(@SuppressWarnings("rawtypes") Map map) throws HandlerException {
		TmcMessages tmcMessages = (TmcMessages) map.get("tmcMessages");
		if(tmcMessages==null){
			this.logger.debug("传递的对象为空，无法进行判断！！！");
			return;
		}
		//前面判断为不通过，不进行下面判断
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
		List<Order> orders = tmcMessages.getTrade().getOrders();
		//商品总数量
		Long tradeNum = 0L; 
		for (Order order : orders) {
			tradeNum = tradeNum + order.getNum();
		}
		Integer minProductNum = tradeSetup.getMinProductNum();
		if(minProductNum!=null){
			if(minProductNum>tradeNum){
				tmcMessages.setFlag(false);
				this.logger.debug("商品数量："+tradeNum+"比用户设置的商品数量最小值："+minProductNum+" 小 tid:"+tmcMessages.getTid()+",类型："+tmcMessages.getTradeSetup().getType()+" 用户设置的ID:+"+tradeSetup.getId());
				return ;
			}
		}
		Integer maxProductNum = tradeSetup.getMaxProductNum();
		if(maxProductNum!=null){
			if(maxProductNum<tradeNum){
				tmcMessages.setFlag(false);
				this.logger.debug("商品数量："+tradeNum+"比用户设置的商品数量最大值："+maxProductNum+" 大 tid:"+tmcMessages.getTid()+",类型："+tmcMessages.getTradeSetup().getType()+" 用户设置的ID:+"+tradeSetup.getId());
				return ;
			}
		}
	}
	
}
