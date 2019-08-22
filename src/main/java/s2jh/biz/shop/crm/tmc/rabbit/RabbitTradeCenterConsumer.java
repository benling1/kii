package s2jh.biz.shop.crm.tmc.rabbit;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.tmc.manage.TradeTmcManageService;

/**
 * 订单中心队列消费者
 * @author zhrt2
 */
public class RabbitTradeCenterConsumer implements MessageListener {
	
    @Autowired
    private TradeTmcManageService tradeTmcManageService;
    @Autowired
    private Jackson2JsonMessageConverter jsonMessageConverter;
    
    @Override
    public void onMessage(Message message) {
    	try {
    		String str = (String)jsonMessageConverter.fromMessage(message);
			this.tradeTmcManageService.doHandle(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}