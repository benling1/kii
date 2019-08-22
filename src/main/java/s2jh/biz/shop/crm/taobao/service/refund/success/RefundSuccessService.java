package s2jh.biz.shop.crm.taobao.service.refund.success;

import net.sf.json.JSONObject;

@Deprecated 
public interface RefundSuccessService {

	public boolean refundSuccess(JSONObject parseJSON) throws Exception;
}
