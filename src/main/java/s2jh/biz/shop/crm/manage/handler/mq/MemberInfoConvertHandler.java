package s2jh.biz.shop.crm.manage.handler.mq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;
import lab.s2jh.core.handler.impl.DefaultHandlerChain;
import lab.s2jh.core.mq.artifact.MemberInfoJobArtifact;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.handler.SynchronousTradeDataHandler;

/**
 * 类名称：TmcSendHandler <br/>
 * 类描述：Send TMC <br/>
 * 创建时间：2017年03月08日 下午7:17:10 <br/>
 * 
 * @author zlp
 * @version V1.0
 */
@Service("memberInfoConvertHandler")
public class MemberInfoConvertHandler implements Handler {
	
	
	
	private static final Logger log = Logger.getLogger(MemberInfoConvertHandler.class);
	@Autowired
	private DefaultHandlerChain memberHandlerChain;
	private static final Logger loger = Logger.getLogger(SynchronousTradeDataHandler.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doHandle( Map map)
			throws HandlerException {
		MemberInfoJobArtifact ja = (MemberInfoJobArtifact) map.get(MemberInfoJobArtifact.class.getSimpleName());
		if (ja != null) {
		  List<TradeDTO> messageList = ja.getMessage();
		  if(null!=messageList&&messageList.size()>0){
				Map mapParam  = new HashMap();
				long startTime = System.currentTimeMillis();
				mapParam.put("tradeList", messageList);
				memberHandlerChain.doHandle(mapParam);
				loger.info("保存"+messageList.size()+"条会员耗时"+(System.currentTimeMillis()-startTime)+"毫秒");
		  }else{
			  log.info("MemberInfomessageList为空！");
		  }
		}
	}
}
