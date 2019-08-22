package s2jh.biz.shop.crm.manage.handler.mq;

import java.util.List;
import java.util.Map;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;
import lab.s2jh.core.mq.artifact.TradeJobArtifact;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.service.SynchronousTradeHelper;
import s2jh.biz.shop.crm.order.pojo.TbTransactionOrder;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;

/**
 * 类名称：TmcSendHandler <br/>
 * 类描述：Send TMC <br/>
 * 创建时间：2017年03月08日 下午7:17:10 <br/>
 * 
 * @author zlp
 * @version V1.0
 */
@Service("tradeConvertHandler")
public class TradeConvertHandler implements Handler {
	
	
	
	private static final Logger log = Logger.getLogger(TradeConvertHandler.class);

	@Autowired
	private TransactionOrderService transactionOrderService;
	
	
	@Autowired
	private SynchronousTradeHelper  synchronousTradeHelper;  

	public void doHandle(@SuppressWarnings("rawtypes") Map map)
			throws HandlerException {
//		log.debug("#arrive tradeConvertHandler#");
		TradeJobArtifact ja = (TradeJobArtifact) map.get(TradeJobArtifact.class.getSimpleName());
		if (ja != null) {
		  List<TbTransactionOrder> messageList = ja.getMessage();
		  if(null!=messageList&&messageList.size()>0){
			  synchronousTradeHelper.loadConvertTbTradeData(messageList);
		  }else{
			  log.info("messageList为空！");
		  }
		}
	}
}
