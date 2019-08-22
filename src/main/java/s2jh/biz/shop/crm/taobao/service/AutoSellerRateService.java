package s2jh.biz.shop.crm.taobao.service;
@Deprecated 
public interface AutoSellerRateService {
	/**
	 * 帮买家自动评价
	 * @param sellerNick 卖家昵称
	 * @param tid 主订单ID
	 * @param oid 订单ＩＤ
	 */
	public void autoSellerRateFunction(String tid,String oid,String sellerName);
	/**
	 * 判断评价状态 确定是否是直接评价还是定时评价,定时自动添加定时任务，直接评价则调用淘宝接口
	 * @param sellerName 卖家昵称
	 * @param tid 主订单ID
	 * @param oid 订单ID
	 */
	public void judgeUserOrderRateCare(String sellerName,String tid,String oid);
}
