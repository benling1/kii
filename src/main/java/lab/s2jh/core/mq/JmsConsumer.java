package lab.s2jh.core.mq;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.mq.artifact.JobArtifact;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: JmsConsumer <br/>
 * @Description: Consumer <br/>
 * 创建时间:2017年03月07日 下午4:04:37 <br/>
 * @author zlp
 * @version V1.0
 */
public class JmsConsumer implements MessageListener{
	
	private final static Log log=LogFactory.getLog(JmsConsumer.class);
	
	private Map<String,Handler> handlers;


	/**
	 * @param handlers the handlers to set
	 */
	public void setHandlers(Map<String, Handler> handlers) {
		this.handlers = handlers;
	}
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
//		log.debug(message.getClass().getSimpleName());
		ObjectMessage objMessage = (ObjectMessage) message;
		try {

			JobArtifact artifact = (JobArtifact) objMessage.getObject();
//			log.debug(artifact.getClass().getSimpleName());
			if (handlers.containsKey(artifact.getClass().getSimpleName())) {
				Map<String, JobArtifact> param = new HashMap<String, JobArtifact>();
				param.put(artifact.getClass().getSimpleName(),artifact);
				Handler handler = handlers.get(artifact.getClass().getSimpleName());
//				log.debug(handler);
				handler.doHandle(param);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
