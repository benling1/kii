package s2jh.biz.shop.crm.order.util;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ResourceGetValueUtil {
	private ResourceGetValueUtil(){}
	/**
	 * 取得资源文件Message.properties的具体内容
	 * @param msgKey 要取得的值
	 * @param args 可变参数内容（占位符）
	 * @return
	 */
	public static String getValue(String msgKey, Object... args){
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
        ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        MessageSource messageSource = application.getBean(MessageSource.class);
        return messageSource.getMessage(msgKey, args, Locale.getDefault());
	}
}
