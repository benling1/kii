package s2jh.biz.shop.utils;

import s2jh.biz.shop.crm.taobao.taobaoInfo;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.UserSellerGetRequest;
import com.taobao.api.response.UserSellerGetResponse;

public class GetUserLevel {

	
	/**
	  * @Description: 通过用户sessionKey获取该用户淘宝等级
	  * @author Mr.H
	  * @date 2017年12月4日 下午2:18:47
	 */
	public static Long getSingleUserLevel(String sessionKey) {
		Long level = null;
		try {
			TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
					taobaoInfo.appKey, taobaoInfo.appSecret);
			UserSellerGetRequest req = new UserSellerGetRequest();
			req.setFields("nick,seller_credit");
			UserSellerGetResponse rsp = null;
			rsp = client.execute(req, sessionKey);
			level = rsp.getUser().getSellerCredit().getLevel();
		} catch (Exception e) {
			level = null;
		}
		return level;
	}
}
