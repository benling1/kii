package s2jh.biz.shop.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class RequestUtil {

	/**
	 * cookie名称
	 */
	private static final String COOKIE_NAME = "_ati";

	/** 
	* @Description 获取孔明锁cookieName:_ati的值
	* @param  request
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2018年2月2日 下午4:58:32
	*/
	public static String getAtiValue(HttpServletRequest request){
		//获取Cookie  
		Cookie[] cookies = request.getCookies();
		//获取Cookie中的令牌
		//1:有,直接返回此令牌
		if(null != cookies && cookies.length > 0){
			for (Cookie cookie : cookies){
				if(COOKIE_NAME.equals(cookie.getName())) 
					if( cookie.getValue() != null && !"".equals(cookie.getValue().trim()))
						return cookie.getValue();
			}
		}
		return null;
	}

	
	
	/** 
	* @Description 获取请求者的ip
	* @param  request
	* @return String    返回类型 
	* @author jackstraw_yu
	* @date 2018年2月2日 下午5:07:36
	*/
	public static String getRequestorIpAddress(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip==null || "".equals(ip.trim())?null:ip;  
	}
	
	
	
}
