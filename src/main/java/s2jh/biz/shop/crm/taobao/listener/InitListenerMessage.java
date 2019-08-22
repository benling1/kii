package s2jh.biz.shop.crm.taobao.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.schedule.job.auto.InitAutoOneScheduleRunnable;
import s2jh.biz.shop.crm.taobao.GetTaobaoMSG;

//监听类
@Deprecated
public class InitListenerMessage implements ServletContextListener,
		ServletContextAttributeListener {
	@Autowired
	private InitAutoOneScheduleRunnable initAutoOneScheduleRunnable;
	@Autowired
	private GetTaobaoMSG getTaobaoMSG;
	Logger logger = org.slf4j.LoggerFactory
			.getLogger(InitListenerMessage.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
//		logger.info("**** Servlet 初始化加载淘宝消息监听服务");
//		new Thread(getTaobaoMSG).start();
//		logger.info("******************* Servlet 一次性定时任务 *********************");
//		new Thread(initAutoOneScheduleRunnable).start();
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
