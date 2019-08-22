package lab.s2jh.core.cons;


import java.util.Map;

import org.springframework.stereotype.Service;

@Service("redisConstant")
public class RedisConstant{
	
	private Map<String,Long> redisExpireTimeMap;
	

	//判断cacheName是否存在
	public boolean isExistCache(String cacheName){
		if(redisExpireTimeMap.containsKey(cacheName)){
			return true;
		}else{
			return false;
		}
	}
	
	//根据cacheName获取过期
	public long getExpireTime(String cacheName){
		if(redisExpireTimeMap.containsKey(cacheName)){
			return redisExpireTimeMap.get(cacheName);
		}else{
			return -1;
		}
	}
	public RedisConstant() {
	}
	public RedisConstant(Map<String, Long> redisExpireTimeMap) {
		super();
		this.redisExpireTimeMap = redisExpireTimeMap;
	}
	
	
	
	
 
	
	/**
	 * 类名称：RedisCacheGroup <br/>
	 * 类描述：RedisCacheGroup组 <br/>
	 * 创建时间：2017年5月21日 下午5:20:57 <br/>
	 * @author zlp
	 * @version V1.0
	 */
	public static class RedisCacheGroup {
		//用户名称对应的mongo表缓存
		public static final String USRENICK_TABLE_CACHE = "USERNICKTABLECACHE";
		//用户名称对应的token缓存
		public static final String USRENICK_TOKEN_CACHE = "USRENICKTOKENCACHE";
		//数据缓存节点
		public static final String NODE_DATA_CACHE = "NODEDATACACHE";
		//会员群发缓存查询条件
		public static final String MEMBER_BATCH_SEND_DATA_CACHE = "MEMBERBATCHSENDDATACACHE";
		//订单群发缓存查询条件
		public static final String ORDER_BATCH_SEND_DATA_CACHE = "ORDERBATCHSENDDATACACHE";
		//验证码
		public static final String VALIDATE_CODE_DATA_CACHE = "VALIDATECODEDATACACHE";
		//控制用户发送验证码的上限
		public static final String VALIDATE_CODE_TOP_CACHE = "VALIDATECODETOPCACHE";
		//用户发送短信msg缓存
		public static final String MESSAGE_MSG_CACHE = "MESSAGEMSGCACHE";
		//店铺数据缓存
		public static final String SHOP_DATA_CACHE = "SHOPDATACACHE";
		//用户过期时间
		public static final String SELLER_EXPIRATION_TIME ="SELLEREXPIRATIONTIME";
		//用户session过期时间（单位：毫秒）
		public static final String SELLER_EXPIRATION_DATE ="SELLEREXPIRATIONDATE";
		//后台设置--短信签名修改缓存
		public static final String SHOP_NAME_CACHE="SHOPNAMECACHE";
	}
	
	/**
	 * 类名称：RediskeyCacheGroup <br/>
	 * 类描述：RediskeyCacheGroup组 <br/>
	 * 创建时间：2017年5月21日 下午5:20:57 <br/>
	 * @author zlp
	 * @version V1.0
	 */
	public static class RediskeyCacheGroup {
		//tradenode 缓存key
		/***
		 * 
		 * 数据缓存的节点key 信息
		 */
		public static final String USRENICK_TABLE_CACHE_KEY= "USRENICKTABLECACHEKEY";
		public static final String USRENICK_TOKEN_CACHE_KEY= "USRENICKTOKENCACHEKEY";
		public static final String TRADE_NODE_KEY_START_TIME = "TRADENODESTARTTIMEKEY";
		public static final String TRADE_NODE_KEY_END_TIME = "TRADENODEENDTIMEKEY";
		public static final String TOTAL_DATA_COUNT_KEY = "TOTALDATACOUNTKEY";
		
		public static final String TRADE_IMPORT_NODE_KEY_START_TIME = "TRADEIMPORTNODESTARTTIMEKEY";
		public static final String TRADE_IMPORT_NODE_KEY_END_TIME = "TRADEIMPORTNODEENDTIMEKEY";
		public static final String TOTAL_IMPORT_DATA_COUNT_KEY = "TOTALIMPORTDATACOUNTKEY";
		
		
		
		
		
		
		
        public static final String MEMBER_BATCH_SEND_DATA_KEY = "MEMBERBATCHSEND";
		
		public static final String ORDER_BATCH_SEND_DATA_KEY = "ORDERBATCHSENDDATAKEY";
		
		public static final String VALIDATE_CODE_KEY = "VALIDATECODE";
		public static final String VALIDATE_CODE_TOP_KEY = "VALIDATECODETOP";
		
		public static final String SHOP_DATA_KEY = "SHOPDATAKEY";  
		
		
		
		
		public static final String SMSRECORD_NODE_KEY = "SMSRECORD";
		public static final String TOTAL_SMSRECORD_DATA_KEY = "TOTALSMSRECORDDATAKEY";
		
		public static final String SYNC_HISTORY_TRADE_DATA_KEY = "SYNCHISTORYTRADEDATAKEY";
		
		public static final String TOTAL_SYNC_HISTORY_TRADE_DATA_KEY = "TOTALSYNCHISTORYTRADEDATAKEY";
		
		public static final String SYNC_HISTORY_MEMBER_DATA_KEY = "SYNCHISTORYMEMBERDATAKEY";
		
		public static final String TOTAL_SYNC_HISTORY_MEMBER_DATA_KEY = "TOTALSYNCHISTORYMEMBERDATAKEY";
		
		public static final String SYNC_EFFECT_MSGID_KEY = "SYNCEFFECTMSGIDKEY";
		
		public static final String SYNC_EFFECT_DATA_KEY = "SYNCEFFECTDATAKEY";
		
		public static final String SYNC_RELATION_ITEM_DATA_KEY = "SYNCRELATIONITEMDATAKEY";
		
		public static final String SYNC_EFFECT_RECORD_DATA_KEY = "SYNCEFFECTRECORDDATAKEY";
		
		//后台管理--短信签名KEY
		public static final String SHOP_NAME_KEY = "SHOP_NAME_KEY";
		
		//踢用户下线
		public static final String LOGIN_FORBIDDEN_USER = "LOGIN_FORBIDDEN_USER";
	}
	
}
