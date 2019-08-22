package s2jh.biz.shop.crm.manage.handler.mq;

import java.util.List;
import java.util.Map;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;
import lab.s2jh.core.mq.artifact.ImportTradeJobArtifact;
import lab.s2jh.core.mq.artifact.TradeJobArtifact;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SynchronousTradeHelper;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;

/**
 * 类名称：TmcSendHandler <br/>
 * 类描述：Send TMC <br/>
 * 创建时间：2017年03月08日 下午7:17:10 <br/>
 * 
 * @author zlp
 * @version V1.0
 */
@Service("importTradeConvertHandler")
public class ImportTradeConvertHandler implements Handler {
	
	
	
	private static final Logger log = Logger.getLogger(ImportTradeConvertHandler.class);

	@Autowired
	private TransactionOrderService transactionOrderService;
	
	
	@Autowired
	private SynchronousTradeHelper  synchronousTradeHelper;  

	public void doHandle(@SuppressWarnings("rawtypes") Map map)
			throws HandlerException {
		ImportTradeJobArtifact ja = (ImportTradeJobArtifact) map.get(ImportTradeJobArtifact.class.getSimpleName());
		if (ja != null) {
		  List<TradeDTO> messageList = ja.getMessage();
		  if(null!=messageList&&messageList.size()>0){
//			  synchronousTradeHelper.loadConvertImportTradeData(messageList);
		  }else{
			  log.info("messageList为空！");
		  }
		}
	}
}
