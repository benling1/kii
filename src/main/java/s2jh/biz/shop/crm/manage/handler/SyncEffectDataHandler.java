package s2jh.biz.shop.crm.manage.handler;

import java.util.Map;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.order.service.EffectPictureService;

/**
 * 类名称：SyncEffectDataHandler <br/>
 * @author zlp
 * @version V1.0
 */
@Service("syncEffectDataHandler")
public class SyncEffectDataHandler implements Handler {
	
	private static final Logger loger = Logger.getLogger(SyncEffectDataHandler.class);

	@Autowired
	private EffectPictureService effectPictureService;
	
	public void doHandle(@SuppressWarnings("rawtypes") Map map)
			throws HandlerException {
          long startTime = System.currentTimeMillis();
//          effectPictureService.syncEffectData(map);
	      loger.info("同步效果分析总共处理时间"+(System.currentTimeMillis()-startTime)+"毫秒");
	}
	
}
