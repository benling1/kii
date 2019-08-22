package s2jh.biz.shop.crm.taobao.service.refund.seller.refuse.agreement;

import net.sf.json.JSONObject;

@Deprecated 
public interface RefundSellerRefuseAgreementService {

	boolean refuseRefund(JSONObject parseJSON) throws Exception;
}
