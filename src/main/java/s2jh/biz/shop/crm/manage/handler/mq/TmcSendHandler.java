package s2jh.biz.shop.crm.manage.handler.mq;

import java.util.Map;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;
import lab.s2jh.core.mq.artifact.TmcJobArtifact;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.taobao.TBMessageManage;

import com.taobao.api.internal.tmc.Message;

/**
 * 类名称：TmcSendHandler <br/>
 * 类描述：Send TMC <br/>
 * 创建时间：2017年03月08日 下午7:17:10 <br/>
 * 
 * @author zlp
 * @version V1.0
 */
@Service("tmcSendHandler")
public class TmcSendHandler implements Handler {
	private static final Logger log = Logger.getLogger(TmcSendHandler.class);


	@Autowired
	private TBMessageManage taoBaoMessageManage;

	public void doHandle(@SuppressWarnings("rawtypes") Map map)
			throws HandlerException {
		log.debug("#arrive tmcSendHandler#");
		TmcJobArtifact ja = (TmcJobArtifact) map.get(TmcJobArtifact.class
				.getSimpleName());
		if (ja != null) {
			log.info("接收tmc");
			Message message = ja.getMessage(); //
			System.out.println(message);
		}
	}
}
