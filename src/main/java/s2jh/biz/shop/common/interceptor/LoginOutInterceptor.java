package s2jh.biz.shop.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import s2jh.biz.shop.common.exception.LoginOutException;
import s2jh.biz.shop.crm.validate.SessionTimeoutException;

public class LoginOutInterceptor implements HandlerInterceptor{
	
	private  static Logger logger = LoggerFactory.getLogger(LoginOutInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean flag = true ;	
		/*String userNick = (String)request.getSession().getAttribute("taobao_user_nick");
		if(userNick==null){
			*//***
			 * 判断是否是ajax请求
			 * 是:在响应位置添加登出
			 * *//*
			if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ 
                logger.info("-------------------------------------an ajax reuqest has happened");
                response.setHeader("sessionstatus", "timeout");  
                //return false;
                //返回到配置文件中定义的路径  
                throw new LoginOutException("Login TimeOut");
            }else{
                flag = false;
				response.sendRedirect("https://login.taobao.com/member/login.jhtml"); 
            }  
		}*/
		/*try {
			} catch (Exception e) {
				logger.error("-------------------------------------interceptor exception occurred");
			}*/
		return flag;
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
