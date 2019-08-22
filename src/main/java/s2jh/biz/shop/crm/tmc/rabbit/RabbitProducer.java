package s2jh.biz.shop.crm.tmc.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
* @author wy
* @version 创建时间：2017年9月5日 下午2:19:52
*/
@Service
public class RabbitProducer {
	  @Autowired
	    private AmqpTemplate amqpTemplate;
	    
	    public void sendQueue(String exchangeKey, String queueKeey, Object object) {
	        // convertAndSend 将Java对象转换为消息发送至匹配key的交换机中Exchange
	        amqpTemplate.convertAndSend(exchangeKey, queueKeey, object);
	    }
}
