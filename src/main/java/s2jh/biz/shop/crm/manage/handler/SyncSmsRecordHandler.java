package s2jh.biz.shop.crm.manage.handler;

import java.util.Map;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.service.SynchronousTradeHelper;

/**
 * 类名称：FetchOrderHandler <br/>
 * 类描述：拉取sysInfo 订单数据 <br/>
 * 创建时间：2017年05月20日 下午7:17:10 <br/>
 * 
 * @author zlp
 * @version V1.0
 */
@Service("syncSmsRecordHandler")
public class SyncSmsRecordHandler implements Handler {

	private static final Logger loger = Logger.getLogger(SyncSmsRecordHandler.class);

	@Autowired
	private  SynchronousTradeHelper synchronousTradeHelper;
	public void doHandle(@SuppressWarnings("rawtypes") Map map)
			throws HandlerException {
          long startTime = System.currentTimeMillis();
	    
//	      synchronousTradeHelper.syncSmsRecordData(map);
	    
	      loger.info("查询总共处理时间"+(System.currentTimeMillis()-startTime)+"毫秒");
	}
}
