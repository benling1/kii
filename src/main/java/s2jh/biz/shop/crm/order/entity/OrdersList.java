package s2jh.biz.shop.crm.order.entity;

import java.util.Date;

import lab.s2jh.core.entity.BaseNativeEntity;

/**
* @ClassName: OrdersList
* @Description: TODO(自定义实体  用于订单列表的显示)
* @author:jackstraw_yu
* @date 2016年12月5日
*
*/
public class OrdersList extends BaseNativeEntity {

	//序号 	店铺名称 	订单编号 	订单状态 	下单时间 	实付金额(元) 	买家昵称 	收货人姓名 	收货人手机 	收货人所在城市 	所购产品类别 	所购产品  订单来源
	
	//店铺名称
	private String shopName;
	
	//订单编号
	private String orderId;
	
	//订单状态
	private String status;
	
	//实际金额
	private String actualMoney;
	
	//买家昵称
	private String buyerNick;
	//收货人姓名
	private String receiverName;
	//收货人手机
	private String receiverMobile;
	//收货人所在城市
	private String receiverCity;
	
	//下单时间
	private Date created;
	//付款时间
	private Date payTime;
	//修改时间
	private Date modified;
	//结束时间
	private Date endTime;
	//发货时间
	private Date consignTime;
		
	
	//所购产品id
	private String itemId; 
	
	//所购产品----名称
	private String itemName;
	
	//所购产品类别
	private String productName;
	
	//订单来源
	private String orderFrom;
	
	public OrdersList() {
		super();
	}



	public OrdersList(String shopName, String orderId, String status, String actualMoney, String buyerNick,
			String receiverName, String receiverMobile, String receiverCity, Date created, Date payTime, Date modified,
			Date endTime, Date consignTime, String itemId, String itemName, String productName, String orderFrom) {
		super();
		this.shopName = shopName;
		this.orderId = orderId;
		this.status = status;
		this.actualMoney = actualMoney;
		this.buyerNick = buyerNick;
		this.receiverName = receiverName;
		this.receiverMobile = receiverMobile;
		this.receiverCity = receiverCity;
		this.created = created;
		this.payTime = payTime;
		this.modified = modified;
		this.endTime = endTime;
		this.consignTime = consignTime;
		this.itemId = itemId;
		this.itemName = itemName;
		this.productName = productName;
		this.orderFrom = orderFrom;
	}



	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(String actualMoney) {
		this.actualMoney = actualMoney;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getreceiverCity() {
		return receiverCity;
	}

	public void setreceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getConsignTime() {
		return consignTime;
	}

	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}
	

	

	
	
	
	
	
}
