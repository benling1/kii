package s2jh.biz.shop.crm.order.util;



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import s2jh.biz.shop.crm.taobao.service.judgment.manual.JudgmentManualStartUtil;

public class RunnableStartOrderReminder implements Callable<Map<String,Object>>{
	private String sellerNick;
	
	
	public RunnableStartOrderReminder() {
	}

	public RunnableStartOrderReminder(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	@Override
	public Map<String,Object> call() {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
			ServletContext servletContext = webApplicationContext.getServletContext();  
		    ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    JudgmentManualStartUtil judgmentManualStartUtil= application.getBean(JudgmentManualStartUtil.class);
	    try {
	    	resultMap = judgmentManualStartUtil.sendMessage(sellerNick);
	    	System.err.println("立即发送线程取出successNum:" + resultMap.get("successNum"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return resultMap;
	}
	/*public String getSellerNick() {
		return sellerNick;
	}
	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}*/
	
}
