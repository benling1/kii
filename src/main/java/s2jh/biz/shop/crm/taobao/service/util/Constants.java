package s2jh.biz.shop.crm.taobao.service.util;
/** 
* 公用常量类。
* @author wy
* @version 创建时间：2017年7月28日 上午11:23:46
*/
public abstract class Constants {
	/** TOP协议入参共享参数 **/
	public static final String APP_KEY = "app_key";
	public static final String FORMAT = "format";
	public static final String METHOD = "method";
	public static final String TIMESTAMP = "timestamp";
	public static final String VERSION = "v";
	public static final String SIGN = "sign";
	public static final String SIGN_METHOD = "sign_method";
	public static final String PARTNER_ID = "partner_id";
	public static final String SESSION = "session";
	public static final String SIMPLIFY = "simplify";
	public static final String TARGET_APP_KEY = "target_app_key";

	/** TOP协议出参共享参数 */
	public static final String ERROR_RESPONSE = "error_response";
	public static final String ERROR_CODE = "code";
	public static final String ERROR_MSG = "msg";
	public static final String ERROR_SUB_CODE = "sub_code";
	public static final String ERROR_SUB_MSG = "sub_msg";

	/** 奇门协议共享参数 */
	public static final String QIMEN_CLOUD_ERROR_RESPONSE = "response";
	public static final String QM_ROOT_TAG = "request";
	public static final String QM_CUSTOMER_ID = "customerId";
	public static final String QM_CONTENT_TYPE = "text/xml;charset=utf-8";

	/** TOP默认时间格式 **/
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** TOP Date默认时区 **/
	public static final String DATE_TIMEZONE = "GMT+8";

	/** UTF-8字符集 **/
	public static final String CHARSET_UTF8 = "UTF-8";

	/** HTTP请求相关 **/
	public static final String METHOD_POST = "POST";
	public static final String METHOD_GET = "GET";
	public static final String CTYPE_FORM_DATA = "application/x-www-form-urlencoded";
	public static final String CTYPE_FILE_UPLOAD = "multipart/form-data";
	public static final String CTYPE_TEXT_XML = "text/xml";
	public static final String CTYPE_TEXT_PLAIN = "text/plain";
	public static final String CTYPE_APP_JSON = "application/json";
}
