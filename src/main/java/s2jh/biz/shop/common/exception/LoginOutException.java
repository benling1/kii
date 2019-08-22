package s2jh.biz.shop.common.exception;

/**
 * @author :jacsktraw_yu
 * @deprecated: 自定义登出异常,用于系统捕获并重定向到登录页面
 *
 */
public class LoginOutException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginOutException() {
		super();
	}

	public LoginOutException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LoginOutException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginOutException(String message) {
		super(message);
	}

	public LoginOutException(Throwable cause) {
		super(cause);
	}

	
}
