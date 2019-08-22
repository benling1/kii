package s2jh.biz.shop.crm.taobao.info;

public abstract class OperationLogStatus {
	/**
	 * 用户日志 类型 扣除短信数
	 */
	public final static String DEL_MESSEGE = "扣除短信数";
	/**
	 * 用户日志 类型 增加短信数
	 */
	public final static String ADD_MESSEGE = "增加短信数";
	/**
	 * 用户日志 操作成功
	 */
	public final static String USER_OPERATION_LOG_SUCCESS = "成功";
	/**
	 * 用户日志 操作失败
	 */
	public final static String USER_OPERATION_LOG_FAIL = "失败";
	/**
	 * 用户日志 操作失败
	 */
	public final static String USER_OPERATION_SINGLE = "单条短信扣费";
	/**
	 * 用户日志 操作失败
	 */
	public final static String USER_OPERATION_MORE = "群发短信扣费";
	
	
}
