package s2jh.biz.shop.crm.taobao.service;

import net.sf.json.JSONObject;
@Deprecated 
public interface SendMessageService {
	/**
	 * 交易消息成功处理类，根据不通过的状态发送不同的消息
	 * 订单状态（请关注此状态，如果为TRADE_CLOSED_BY_TAOBAO状态，则不要对此订单进行发货，切记啊！）。可选值: TRADE_NO_CREATE_PAY(没有创建支付宝交易) WAIT_BUYER_PAY(等待买家付款) WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) TRADE_BUYER_SIGNED(买家已签收,货到付款专用) TRADE_FINISHED(交易成功) TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)PAY_PENDING(国际信用卡支付付款确认中) 
	 * @param json 淘宝返回的交易成功返回的JSON数据
	 */
	public boolean tradeSuccess(JSONObject json) throws Exception;
	/**
	 * 
	  * 创建人：邱洋
	  * @Title: 付款提醒
	  * @date 2017年4月20日--下午2:29:27 
	  * @return boolean
	  * @throws
	 */
	public boolean payRemind(JSONObject json) throws Exception;
	/**
	 * 订单信息变更
	 * @param json
	 * @throws Exception
	 */
	public boolean tradeChanged (JSONObject json) throws Exception;
	/**
	 * 买家付完款，或万人团买家付完尾款 
	 * @param json
	 * @throws Exception
	 */
	public boolean tradeBuyerPay(JSONObject json) throws Exception;
	/**
	 * 订单关闭交易
	 * @param json
	 * @throws Exception
	 */
	public void tradeClose(JSONObject json) throws Exception;
	/**
	 * 宝贝关怀
	 * @param json
	 * @throws Exception
	 */
	public boolean goodsCowryCare(JSONObject json) throws Exception;
	/**
	 * 物流签收宝贝关怀
	 * @param json
	 * @throws Exception
	 */
	public boolean goodsCowryCareSinged(JSONObject json) throws Exception;
	/**
	 * 回款提醒
	 * @param json
	 * @throws Exception
	 */
	public boolean returnedMoneyWarn(JSONObject json) throws Exception;
}
