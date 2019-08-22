/*
 * create on 2008-8-27
 * Copy right (2008)
 * Author Tan Nailiang
 * HNA Systems All rights reserved
 */
package lab.s2jh.core.mq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import lab.s2jh.core.mq.artifact.JobArtifact;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * @ClassName: JmsProducer <br/>
 * @Description: Producer <br/>
 * @CreateDate: 2017年03月07日 下午4:36:50 <br/>
 * @author zlp
 * @version V1.0
 */
public class JmsProducer {
	
	private final static Log log=LogFactory.getLog(JmsProducer.class);
	
	/**
	 * jmsTemplate	
	 */
	private JmsTemplate jmsTemplate;
	
	/**
	 * destination
	 */
	private Destination destination;

	/**
	 * @param jmsTemplate the jmsTemplate to set
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
	public void send(final JobArtifact fact) {
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				log.debug("Sending to " + destination);
				log.debug("content=" + fact.toString());
				
				Message m = session.createObjectMessage(fact);
				m.setStringProperty("type", fact.getClass().getSimpleName());
				m.setStringProperty("text", fact.toString());
				return m;
			}
		});
	}
	

}
