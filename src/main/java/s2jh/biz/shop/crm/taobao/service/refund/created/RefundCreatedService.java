package s2jh.biz.shop.crm.taobao.service.refund.created;

import net.sf.json.JSONObject;

@Deprecated 
public interface RefundCreatedService {

	boolean refundCreate(JSONObject parseJSON) throws Exception;
}
