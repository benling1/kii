package s2jh.biz.shop.crm.taobao.service.refund.waiting;

import net.sf.json.JSONObject;

@Deprecated 
public interface WaitingRefundService {

	boolean waitingRefund(JSONObject parseJSON) throws Exception;
}
