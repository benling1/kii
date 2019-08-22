package s2jh.biz.shop.crm.tmc.rabbit;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.tmc.manage.FuwuTmcManageService;

/**
 * 服务队列消费者
 * @author zhrt2
 *
 */

public class RabbitFuwuConsumer implements MessageListener {
	
    @Autowired
    private FuwuTmcManageService fuwuTmcManageService;
    @Autowired
    private Jackson2JsonMessageConverter jsonMessageConverter;
    @Override
    public void onMessage(Message message) {
    	String str =  (String)jsonMessageConverter.fromMessage(message);
    	this.fuwuTmcManageService.doHandle(str);
    }
   
}