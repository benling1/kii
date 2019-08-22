package s2jh.biz.shop.crm.validate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.service.RedisLockServiceImpl;

/** 
* @ClassName: ValidationInterceptor 
* @Description:  拦截请求判断用户是否登陆,是否被标识踢下线用户
* @date 2018年3月12日 下午2:47:35 
*  
*/
public class ValidationInterceptor implements HandlerInterceptor{
	
	private  static Logger logger = LoggerFactory.getLogger(ValidationInterceptor.class);
	
	private static final String AJAX_REQUEST_HEADER_NAME = "x-requested-with";
	private static final String AJAX_REQUEST_HEADER_VALUE = "XMLHttpRequest";
	private static final String REDIRECT_PATH = "https://login.taobao.com/member/login.jhtml";
	
	
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*HttpSession session = request.getSession();
		String result = (String)session.getAttribute("taobao_user_nick");
		if(result==null || "".equals(result.trim())){
			return processRequest(request,response); 
		}
		String userName = null;
		try {
			userName = redisLockServiceImpl.getStringValue(
					RedisConstant.RediskeyCacheGroup.LOGIN_FORBIDDEN_USER
					+"-"+result.trim());
		} catch (Exception e) {
			logger.error("-------------------------------------Exception:"+e.getMessage());
			response.sendRedirect(REDIRECT_PATH); 
			return false; 
		}
		if(userName !=null && result.trim().equals(userName.trim())){
			return processRequest(request,response); 
		}*/
		return true;
	}
	
	
	/** 
	* @Description 判断用户是正常请求还是AJAX请求 
	* @param  request
	* @param  response
	* @return boolean    返回类型 
	* @author jackstraw_yu
	* @date 2018年3月12日 下午3:27:09
	* @throws Exception
	*/
	private boolean processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		if (request.getHeader(AJAX_REQUEST_HEADER_NAME) != null 
				&& request.getHeader(AJAX_REQUEST_HEADER_NAME).equalsIgnoreCase(AJAX_REQUEST_HEADER_VALUE)){ 
	            logger.info("-------------------------------------an ajax reuqest has occurred with sessionOut !");
	            response.setHeader("sessionstatus", "timeout");//在响应头设置session状态  
	            //返回到配置文件中定义的路径  
		        throw new SessionTimeoutException("login timeOut !");
		 }
		response.sendRedirect(REDIRECT_PATH); 
		return false;
	}
	
	
	
	
	
	
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	
	
}
