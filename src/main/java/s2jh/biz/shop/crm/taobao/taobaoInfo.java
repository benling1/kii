package s2jh.biz.shop.crm.taobao;



public class taobaoInfo {

	// 淘宝正式环境地址
	public static final String url = "http://gw.api.taobao.com/router/rest";

	// 淘宝正式环境换取token地址
	public static final String tokenUrl = "https://oauth.taobao.com/token";
	
	// 公网回调地址
	public static final String hdUrl ="http://b.kycrm.com:81/getUserCode/login";

	// 测试回调地址
//	public static final String hdUrl = "http://office.duanpianl.com:88/s2jh4net/getUserCode/login";
	public static final String getTokenUrl ="http://b.kycrm.com:81/getUserCode/token";

	// 创建应用时，TOP颁发的唯一标识，TOP通过App Key来鉴别应用的身份。调用接口时必须传入的参数。
	public static final String appKey = "21800702";

	// App Secret是TOP给应用分配的密钥，开发者需要妥善保存这个密钥，这个密钥用来保证应用来源的可靠性，防止被伪造。
	public static final String appSecret = "d42fb4b3d5bc4304415665c3cf870912";

	// 聚石塔rds名称（用于添加同步用户）
	public static final String jst = "rm-vy14617996v77fs5h";

	// 客云CRM服务应用code
	public static final String appCode = "FW_GOODS-1952286";
	
	//购买应用页面地址
	public static final String payUrl = "redirect:https://fuwu.taobao.com/ser/detail.htm?service_code=FW_GOODS-1952286&selected_item_code=FW_GOODS-1952286-1&redirect=1";
	
	//TMC推送消息所添加的服务列表
	public static final String topIc = "taobao_fuwu_ServiceOpen,taobao_fuwu_OrderPaid,taobao_logistics_LogsticDetailTrace,taobao_fuwu_OrderCreated,taobao_fuwu_OrderClosed,taobao_refund_RefundCreated,taobao_refund_RefundSuccess,taobao_item_ItemDelete,taobao_trade_TradeCreate,taobao_trade_TradeCloseAndModifyDetailOrder,taobao_trade_TradeClose,taobao_trade_TradeBuyerPay,taobao_trade_TradeSellerShip,taobao_trade_TradePartlyRefund,taobao_trade_TradeSuccess,taobao_trade_TradeRated,taobao_trade_TradeChanged,taobao_trade_TradeAlipayCreate";
	
	// 数据库连接地址（本地）
//	public static final String jdbcUrl = "jdbc:mysql://172.18.21.41:3306/crm_customermanagement?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	// 连接数据库用户名（本地）
//	public static final String user = "crm";
	// 连接数据库密码（本地）
//	public static final String password = "crm";
	
	// 数据库连接地址
	public static final String jdbcUrl = "jdbc:mysql://rm-vy14617996v77fs5h.mysql.rds.aliyuncs.com:3306/sys_info?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	// 连接数据库用户名
	public static final String user = "jusrsqk9uvqg";
	// 连接数据库密码
	public static final String password = "Duanpian2016";
	
	/*加密数据使用的安全令牌码*/
	public static final String securityToken = "qE8Vqx/iYqZwJ0BfiRN5icpnam51pdwbZMLIzi+CV7c=";
	
	/*加密数据使用的服务器路径*/
	public static final String serviceUrl = "https://eco.taobao.com/router/rest";

	/*将用户状态设置为黑名单*/
	public static final String blackStatus = "1";
}
