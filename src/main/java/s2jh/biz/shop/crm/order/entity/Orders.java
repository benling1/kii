package s2jh.biz.shop.crm.order.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.experimental.Accessors;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Transient;
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_ORDERS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "订单列表")
public class Orders extends BaseNativeEntity{

	private static final long serialVersionUID = -4773286203943149371L;
	
	@MetaData(value="子订单ID")
	@Column(name="oid",nullable=false)
	private String oid;
	
	@MetaData(value="父订单ID")
	@Column(name="tid",nullable=false)
	private String tid;
	
	@MetaData(value="交易商品所对应的类目ID")
	@Column(name="cid")
	private Long cid;
	
	@MetaData(value="商品数字ID")
	@Column(name="num_iid")
	private Long numIid;
	
	@MetaData(value="套餐ID")
	@Column(name="item_meal_id")
	private Long itemMealId;
	
	@MetaData(value="商品的最小库存sku的ID")
	@Column(name="sku_id")
	private String skuId;
	
	@MetaData(value="最近退款ID")
	@Column(name="refund_id")
	private Long refundId;
	
	@MetaData(value="捆绑的子订单号")
	@Column(name="bind_oid")
	private Long bindOid;
	
	@MetaData(value="套餐值")
	@Column(name="item_meal_name")
	private String itemMealName;
	
	@MetaData(value="商品图片的绝对路径")
	@Column(name="pic_path")
	private String picPath;
	
	@MetaData(value="卖家昵称")
	@Column(name="seller_nick")
	private String sellerNick;
	
	@MetaData(value="买家昵称")
	@Column(name="buyer_nick")
	private String buyerNick;
	
	@MetaData(value="退款状态 WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意) WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货) WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货) SELLER_REFUSE_BUYER(卖家拒绝退款) CLOSED(退款关闭) SUCCESS(退款成功)   1-买家申请退款，等待卖家同意 2-卖家同意退款，等待买家退货3-买家已经退货，等待卖家确认收货 4- 卖家拒绝退款 5-退款关闭 6-退款成功")
	@Column(name="refund_status")
	private String refundStatus;
	
	@MetaData(value="商家外部编码")
	@Column(name="outer_iid")
	private String outeriid;
	
	@MetaData(value="订单快照URL")
	@Column(name="snapshot_url")
	private String snapshotUrl;
	
	@MetaData(value="订单快照详细信息")
	@Column(name="snapshot")
	private String snapshot;
	
	@MetaData(value="订单超时到期时间")
	@Column(name="timeout_action_time")
	private Date timeoutActionTime;
	
	@MetaData(value="买家是否已评价")
	@Column(name="buyer_rate")
	private Boolean buyerRate;
	
	@MetaData(value="卖家是否已评价")
	@Column(name="seller_rate")
	private Boolean sellerRate;
	
	@MetaData(value="卖家类型 1-商城商家  2-普通卖家")
	@Column(name="seller_type")
	private String sellerType;
	
	@MetaData(value="天猫子订单税费")
	@Column(name="sub_order_tax_fee")
	private String subOrderTaxFee;
	
	@MetaData(value="天猫子订单税率")
	@Column(name="sub_order_tax_rate")
	private String subOrderTaxRate;
	
	@MetaData(value="订单状态")
	@Column(name="status")
	private String status;
	
	@MetaData(value="商品标题")
	@Column(name="title")
	private String title;
	
	@MetaData(value="交易类型     import--导入的订单")
	@Column(name="type")
	private String type;
	
	@MetaData(value="商品字符串编号")
	@Column(name="iid")
	private String iid;
	
	@MetaData(value="商品价格")
	@Column(name="price")
	private String price;
	
	@MetaData(value="购买数量")
	@Column(name="num")
	private Long num;
	
	@MetaData(value="外部网店自己定义的sku编号")
	@Column(name="outer_sku_id")
	private String outerSkuId;
	
	@MetaData(value="子订单来源")
	@Column(name="order_from")
	private String orderFrom;
	
	@MetaData(value="应付金额")
	@Column(name="total_fee")
	private String totalFee;
	
	@MetaData(value="子订单应付金额")
	@Column(name="payment")
	private Double payment;
	
	@MetaData(value="子订单优惠金额")
	@Column(name="discount_fee")
	private String discountFee;
	
	@MetaData(value="手工调整金额")
	@Column(name="adjust_fee")
	private String adjustFee;
	
	@MetaData(value="订单修改时间")
	@Column(name="modified")
	private Date modified;
	
	@MetaData(value="sku的值")
	@Column(name="sku_properties_name")
	private String skuPropertiesName;
	
	@MetaData(value="是否超卖")
	@Column(name="is_oversold")
	private Boolean isOversold = Boolean.FALSE;
	
	@MetaData(value="是否为服务订单")
	@Column(name="is_service_order")
	private Boolean isServiceOrder = Boolean.FALSE;
	
	@MetaData(value="子订单交易结束时间")
	@Column(name="end_time")
	private Date endTime;
	
	@MetaData(value="子订单发货时间")
	@Column(name="consign_time")
	private String consignTime;
	
	@MetaData(value="top动态字段")
	@Column(name="order_attr")
	private String orderAttr;
	
	@MetaData(value="子订单运送方式")
	@Column(name="shipping_type")
	private String shippingType;
	
	@MetaData(value="子订单发货的快递公司")
	@Column(name="logistics_company")
	private String logisticsCompany;
	
	@MetaData(value="子订单包裹的运单号")
	@Column(name="invoice_no")
    private String invoiceNo;
	
	@MetaData(value="订单是否有相应的代销采购单")
	@Column(name="is_daixiao")
	private Boolean isdaixiao = Boolean.FALSE;
	
	@MetaData(value="分摊之后的实付金额")
	@Column(name="divide_order_fee")
	private String divideOrderFee;
	
	@MetaData(value="优惠分摊")
	@Column(name="part_mjz_discount")
	private String partMjzDiscount;
	
	@MetaData(value="对用门票有效的外部ID")
	@Column(name="ticket_outer_id")
	private String ticketOuterId;
	
	@MetaData(value="门票有效期的key")
	@Column(name="ticket_expdate_key")
	private String ticketExpdateKey;
	
	@MetaData(value="发货的仓库编码")
	@Column(name="store_code")
	private String storeCode;
	
	@MetaData(value="订单是否是www订单")
	@Column(name="is_www")
	private Boolean isWww = Boolean.FALSE;
	
	@MetaData(value="支持家装类的物流")
	@Column(name="tmser_spu_code")
	private String tmserSpuCode;
	
	@MetaData(value="bind_oid字段的升级")
	@Column(name="bind_oids")
	private String bindOids;
	
	@MetaData(value="预售订单征集状态 1-征集中 2-征集成功 3-征集失败")
	@Column(name="zhengji_status")
	private String zhengjiStatus;
	
	@MetaData(value="免单资格属性")
	@Column(name="md_qualification")
	private String mdQualification;
	
	@MetaData(value="免单金额")
	@Column(name="md_fee")
	private String mdFee;
	
	@MetaData(value="定制信息")
	@Column(name="customization")
	private String customization;
	
	@MetaData(value="库存类型")
	@Column(name="inv_type")
	private String invType;
	
	@MetaData(value="是否发货 1- 是 2-否")
	@Column(name="is_sh_ship")
	private Boolean isShShip=Boolean.FALSE;
	
	@MetaData(value="仓储信息")
	@Column(name="shipper")
	private String shipper;
	
	@MetaData(value="订单履行类型")
	@Column(name="f_type")
	private String fType;
	
	@MetaData(value="订单履行状态")
	@Column(name="f_status")
	private String fStatus;
	
	@MetaData(value="订单履行内容")
	@Column(name="F_TERM")
	private String FTERM;
	
	@MetaData(value="天猫搭配包")
	@Column(name="COMBO_ID")
	private String comboId;
	
	@MetaData(value="主商品订单ID")
	@Column(name="ASSEMBLY_RELA")
	private String assemblyRela;
	
	@MetaData(value="价格")
	@Column(name="ASSEMBLY_PRICE")
	private String assemblyPrice;
	
	@MetaData(value="assemblyItem")
	@Column(name="ASSEMBLY_ITEM")
	private String assemblyItem;

	@MetaData(value="收货人所在地区")
	@Column(name="receiver_district")
	private String receiverDistrict;
	
	@MetaData(value="收货人所在城市")
	@Column(name="receiver_city")
	private String receiverCity;
	
	@MetaData(value="分段付款状态")
	@Column(name="step_trade_status")
	private	String stepTradeStatus;
	
	@MetaData(value="订单创建时间")
	@Column(name="created")
	private Date created;
	
	@MetaData(value="收货人姓名")
	@Column(name="receiver_name")
	private String receiverName;
	
	@MetaData(value="收货人手机")
	@Column(name="receiver_mobile")
	private String receiverMobile;
	
	@MetaData(value="买家备注旗帜0-灰代表全部，1-红，2-黄，3-绿，4-蓝，5-紫")
	@Column(name="buyer_flag")
	private Integer buyerFlag;
	
	@MetaData(value="卖家备注旗帜0-灰代表全部，1-红，2-黄，3-绿，4-蓝，5-紫")
	@Column(name="seller_flag")
	private Integer sellerFlag;
	
	@Transient
	private TransactionOrder  transactionOrder;//和trade一对多查询
	
	@Transient
	private Date rateOverDate;//评价过期时间(订单中心--批量评价)
	
	public Date getRateOverDate() {
		return rateOverDate;
	}

	public void setRateOverDate(Date rateOverDate) {
		this.rateOverDate = rateOverDate;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public Long getItemMealId() {
		return itemMealId;
	}

	public void setItemMealId(Long itemMealId) {
		this.itemMealId = itemMealId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public Long getBindOid() {
		return bindOid;
	}

	public void setBindOid(Long bindOid) {
		this.bindOid = bindOid;
	}

	public String getItemMealName() {
		return itemMealName;
	}

	public void setItemMealName(String itemMealName) {
		this.itemMealName = itemMealName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getOuteriid() {
		return outeriid;
	}

	public void setOuteriid(String outeriid) {
		this.outeriid = outeriid;
	}

	public String getSnapshotUrl() {
		return snapshotUrl;
	}

	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public Date getTimeoutActionTime() {
		return timeoutActionTime;
	}

	public void setTimeoutActionTime(Date timeoutActionTime) {
		this.timeoutActionTime = timeoutActionTime;
	}

	public Boolean getBuyerRate() {
		return buyerRate;
	}

	public void setBuyerRate(Boolean buyerRate) {
		this.buyerRate = buyerRate;
	}

	public Boolean getSellerRate() {
		return sellerRate;
	}

	public void setSellerRate(Boolean sellerRate) {
		this.sellerRate = sellerRate;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getSubOrderTaxFee() {
		return subOrderTaxFee;
	}

	public void setSubOrderTaxFee(String subOrderTaxFee) {
		this.subOrderTaxFee = subOrderTaxFee;
	}

	public String getSubOrderTaxRate() {
		return subOrderTaxRate;
	}

	public void setSubOrderTaxRate(String subOrderTaxRate) {
		this.subOrderTaxRate = subOrderTaxRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getOuterSkuId() {
		return outerSkuId;
	}

	public void setOuterSkuId(String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public String getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(String adjustFee) {
		this.adjustFee = adjustFee;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getSkuPropertiesName() {
		return skuPropertiesName;
	}

	public void setSkuPropertiesName(String skuPropertiesName) {
		this.skuPropertiesName = skuPropertiesName;
	}

	public Boolean getIsOversold() {
		return isOversold;
	}

	public void setIsOversold(Boolean isOversold) {
		this.isOversold = isOversold;
	}

	public Boolean getIsServiceOrder() {
		return isServiceOrder;
	}

	public void setIsServiceOrder(Boolean isServiceOrder) {
		this.isServiceOrder = isServiceOrder;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getConsignTime() {
		return consignTime;
	}

	public void setConsignTime(String consignTime) {
		this.consignTime = consignTime;
	}

	public String getOrderAttr() {
		return orderAttr;
	}

	public void setOrderAttr(String orderAttr) {
		this.orderAttr = orderAttr;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Boolean getIsdaixiao() {
		return isdaixiao;
	}

	public void setIsdaixiao(Boolean isdaixiao) {
		this.isdaixiao = isdaixiao;
	}

	public String getDivideOrderFee() {
		return divideOrderFee;
	}

	public void setDivideOrderFee(String divideOrderFee) {
		this.divideOrderFee = divideOrderFee;
	}

	public String getPartMjzDiscount() {
		return partMjzDiscount;
	}

	public void setPartMjzDiscount(String partMjzDiscount) {
		this.partMjzDiscount = partMjzDiscount;
	}

	public String getTicketOuterId() {
		return ticketOuterId;
	}

	public void setTicketOuterId(String ticketOuterId) {
		this.ticketOuterId = ticketOuterId;
	}

	public String getTicketExpdateKey() {
		return ticketExpdateKey;
	}

	public void setTicketExpdateKey(String ticketExpdateKey) {
		this.ticketExpdateKey = ticketExpdateKey;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Boolean getIsWww() {
		return isWww;
	}

	public void setIsWww(Boolean isWww) {
		this.isWww = isWww;
	}

	public String getTmserSpuCode() {
		return tmserSpuCode;
	}

	public void setTmserSpuCode(String tmserSpuCode) {
		this.tmserSpuCode = tmserSpuCode;
	}

	public String getBindOids() {
		return bindOids;
	}

	public void setBindOids(String bindOids) {
		this.bindOids = bindOids;
	}

	public String getZhengjiStatus() {
		return zhengjiStatus;
	}

	public void setZhengjiStatus(String zhengjiStatus) {
		this.zhengjiStatus = zhengjiStatus;
	}

	public String getMdQualification() {
		return mdQualification;
	}

	public void setMdQualification(String mdQualification) {
		this.mdQualification = mdQualification;
	}

	public String getMdFee() {
		return mdFee;
	}

	public void setMdFee(String mdFee) {
		this.mdFee = mdFee;
	}

	public String getCustomization() {
		return customization;
	}

	public void setCustomization(String customization) {
		this.customization = customization;
	}

	public String getInvType() {
		return invType;
	}

	public void setInvType(String invType) {
		this.invType = invType;
	}

	public Boolean getIsShShip() {
		return isShShip;
	}

	public void setIsShShip(Boolean isShShip) {
		this.isShShip = isShShip;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getfType() {
		return fType;
	}

	public void setfType(String fType) {
		this.fType = fType;
	}

	public String getfStatus() {
		return fStatus;
	}

	public void setfStatus(String fStatus) {
		this.fStatus = fStatus;
	}

	public String getFTERM() {
		return FTERM;
	}

	public void setFTERM(String fTERM) {
		FTERM = fTERM;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	public String getAssemblyRela() {
		return assemblyRela;
	}

	public void setAssemblyRela(String assemblyRela) {
		this.assemblyRela = assemblyRela;
	}

	public String getAssemblyPrice() {
		return assemblyPrice;
	}

	public void setAssemblyPrice(String assemblyPrice) {
		this.assemblyPrice = assemblyPrice;
	}

	public String getAssemblyItem() {
		return assemblyItem;
	}

	public void setAssemblyItem(String assemblyItem) {
		this.assemblyItem = assemblyItem;
	}

	public String getReceiverDistrict() {
		return receiverDistrict;
	}

	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public String getStepTradeStatus() {
		return stepTradeStatus;
	}

	public void setStepTradeStatus(String stepTradeStatus) {
		this.stepTradeStatus = stepTradeStatus;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	public Integer getBuyerFlag() {
		return buyerFlag;
	}

	public void setBuyerFlag(Integer buyerFlag) {
		this.buyerFlag = buyerFlag;
	}

	public Integer getSellerFlag() {
		return sellerFlag;
	}

	public void setSellerFlag(Integer sellerFlag) {
		this.sellerFlag = sellerFlag;
	}


	public TransactionOrder getTransactionOrder() {
		return transactionOrder;
	}

	public void setTransactionOrder(TransactionOrder transactionOrder) {
		this.transactionOrder = transactionOrder;
	}

	@Override
	public String toString() {
		return "Orders [oid=" + oid + ", tid=" + tid + ", cid=" + cid
				+ ", numIid=" + numIid + ", itemMealId=" + itemMealId
				+ ", skuId=" + skuId + ", refundId=" + refundId + ", bindOid="
				+ bindOid + ", itemMealName=" + itemMealName + ", picPath="
				+ picPath + ", sellerNick=" + sellerNick + ", buyerNick="
				+ buyerNick + ", refundStatus=" + refundStatus + ", outeriid="
				+ outeriid + ", snapshotUrl=" + snapshotUrl + ", snapshot="
				+ snapshot + ", timeoutActionTime=" + timeoutActionTime
				+ ", buyerRate=" + buyerRate + ", sellerRate=" + sellerRate
				+ ", sellerType=" + sellerType + ", subOrderTaxFee="
				+ subOrderTaxFee + ", subOrderTaxRate=" + subOrderTaxRate
				+ ", status=" + status + ", title=" + title + ", type=" + type
				+ ", iid=" + iid + ", price=" + price + ", num=" + num
				+ ", outerSkuId=" + outerSkuId + ", orderFrom=" + orderFrom
				+ ", totalFee=" + totalFee + ", payment=" + payment
				+ ", discountFee=" + discountFee + ", adjustFee=" + adjustFee
				+ ", modified=" + modified + ", skuPropertiesName="
				+ skuPropertiesName + ", isOversold=" + isOversold
				+ ", isServiceOrder=" + isServiceOrder + ", endTime=" + endTime
				+ ", consignTime=" + consignTime + ", orderAttr=" + orderAttr
				+ ", shippingType=" + shippingType + ", logisticsCompany="
				+ logisticsCompany + ", invoiceNo=" + invoiceNo
				+ ", isdaixiao=" + isdaixiao + ", divideOrderFee="
				+ divideOrderFee + ", partMjzDiscount=" + partMjzDiscount
				+ ", ticketOuterId=" + ticketOuterId + ", ticketExpdateKey="
				+ ticketExpdateKey + ", storeCode=" + storeCode + ", isWww="
				+ isWww + ", tmserSpuCode=" + tmserSpuCode + ", bindOids="
				+ bindOids + ", zhengjiStatus=" + zhengjiStatus
				+ ", mdQualification=" + mdQualification + ", mdFee=" + mdFee
				+ ", customization=" + customization + ", invType=" + invType
				+ ", isShShip=" + isShShip + ", shipper=" + shipper
				+ ", fType=" + fType + ", fStatus=" + fStatus + ", FTERM="
				+ FTERM + ", comboId=" + comboId + ", assemblyRela="
				+ assemblyRela + ", assemblyPrice=" + assemblyPrice
				+ ", assemblyItem=" + assemblyItem + ", receiverDistrict="
				+ receiverDistrict + ", receiverCity=" + receiverCity
				+ ", stepTradeStatus=" + stepTradeStatus + ", created="
				+ created + ", receiverName=" + receiverName
				+ ", receiverMobile=" + receiverMobile + ", buyerFlag="
				+ buyerFlag + ", sellerFlag=" + sellerFlag + ", rateOverDate="
				+ rateOverDate + "]";
	}


}
