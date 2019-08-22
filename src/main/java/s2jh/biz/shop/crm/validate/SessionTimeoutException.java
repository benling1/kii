package s2jh.biz.shop.crm.validate;

/**
 * @author :jacsktraw_yu
 * @deprecated: 自定义登出异常,用于系统捕获并重定向到登录页面
 *
 */
public class SessionTimeoutException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SessionTimeoutException() {
		super();
	}

	public SessionTimeoutException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SessionTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionTimeoutException(String message) {
		super(message);
	}

	public SessionTimeoutException(Throwable cause) {
		super(cause);
	}

	
}
