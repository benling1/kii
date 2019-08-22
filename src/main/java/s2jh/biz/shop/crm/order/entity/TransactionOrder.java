package s2jh.biz.shop.crm.order.entity;

/*import java.math.String;*/
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseEntity;
import lombok.experimental.Accessors;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_TRADE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "主交易订单信息")
public class TransactionOrder extends BaseEntity<String>{

	private static final long serialVersionUID = 4498366225784875392L;
	
	@MetaData(value="交易编号")
	@Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "assigned")
	private String tid;
	
	@MetaData(value="卖家昵称")
	@Column(name="SELLER_NICK")
	private String sellerNick;
	
	@MetaData(value="商品图片绝对路径")
	@Column(name="PIC_PATH")
	private String picPath;
	
	@MetaData(value="实付金额")
	@Column(name="PAYMENT")
	private Double payment;
	
	@MetaData(value="卖家是否已评价")
	@Column(name="SELLER_RATE")
	private Boolean sellerRate = Boolean.FALSE;
	
	@MetaData(value="邮费")
	@Column(name="POST_FEE")
	private String postFee;
	
	@MetaData(value="收货人姓名")
	@Column(name="RECEIVER_NAME")
	private String receiverName;
	
	@MetaData(value="收货人所在省份")
	@Column(name="RECEIVER_STATE")
	private String receiverState;
	
	@MetaData(value="收货人详细地址")
	@Column(name="RECEIVER_ADDRESS")
	private String receiverAddress;
	
	@MetaData(value="收货人邮编")
	@Column(name="RECEIVER_ZIP")
	private String receiverZip;
	
	@MetaData(value="收货人手机号")
	@Column(name="RECEIVER_MOBILE")
	private String receiverMobile;
	
	@MetaData(value="收货人电话号码")
	@Column(name="RECEIVER_PHONE")
	private String receiverPhone;
	
	@MetaData(value="卖家发货时间")
	@Column(name="CONSIGN_TIME")
	private Date consignTime;
	
	@MetaData(value="卖家实际收到的支付宝打款金额")
	@Column(name="RECEIVED_PAYMENT")
	private String receivedPayment;
	
	@MetaData(value="商家的预计发货时间")
	@Column(name="EST_CON_TIME")
	private String estConTime;
	
	@MetaData(value="发票类型 1-电子发票 2-纸质发票")
	@Column(name="INVOICE_KIND")
	private String invoiceKind;
	
	@MetaData(value="收货人国籍")
	@Column(name="RECEIVER_COUNTRY")
	private String receiverCountry;
	
	@MetaData(value="收货人街道地址")
	@Column(name="RECEIVER_TOWN")
	private String receiverTown;
	
	@MetaData(value="天猫国际官网直供主订单关税税费")
	@Column(name="ORDER_TAX_FEE")
	private Integer orderTaxFee;
	
	@MetaData(value="返红包的金额")
	@Column(name="PAID_COUPON_FEE")
	/*private BigDecimal paidCouponFee;*/
	private Double paidCouponFee;
	
	@MetaData(value="门店自提，总店发货，分店取货的门店自提订单标识")
	@Column(name="SHOP_PICK")
	private String shopPick;
	
	@MetaData(value="商品购买数量")
	@Column(name="NUM")
	private String num;
	
	@MetaData(value="商品数字编号")
	@Column(name="NUM_IID")
	private String NUM_IID;
	
	@MetaData(value="交易状态 1-没有创建支付宝交易 2-等待买家付款 3-卖家部分发货 4-等待卖家发货，即买家已付款 5-等待买家确认收货，及卖家已发货 6-买家已签收，货到付款专用 7-交易成功")
	@Column(name="STATUS")
	private String status;
	
	@MetaData(value="交易标题")
	@Column(name="TITLE")
	private String title;
	
	@MetaData(value="交易类型列表  import--导入的订单")
	@Column(name="TYPE")
	private String type;
	
	@MetaData(value="商品价格")
	@Column(name="PRICE")
	private String price;
	
	@MetaData(value="优惠金额")
	@Column(name="DISCOUNT_FEE")
	private String discountFee;
	
	@MetaData(value="是否包含邮费")
	@Column(name="HAS_POST_FEE")
	private Boolean hasPostFee = Boolean.FALSE;
	
	@MetaData(value="商品金额")
	@Column(name="TOTAL_FEE")
	private String totalFee;
	
	@MetaData(value="交易创建时间")
	@Column(name="CREATED")
	private Date created;
	
	@MetaData(value="付款时间")
	@Column(name="PAY_TIME")
	private Date payTime;
	
	@MetaData(value="交易修改时间")
	@Column(name="MODIFIED")
	private Date modified;
	
	@MetaData(value="交易结束时间")
	@Column(name="END_TIME")
	private Date endTime;
	
	@MetaData(value="买家留言")
	@Column(name="BUYER_MESSAGE")
	private String buyerMessage;
	
	@MetaData(value="买家备注")
	@Column(name="BUYER_MEMO")
	private String buyerMemo;
	
	@MetaData(value="买家备注旗帜")
	@Column(name="BUYER_FLAG")
	private Integer buyerFlag;
	
	@MetaData(value="卖家备注")
	@Column(name="SELLER_MEMO")
	private String sellerMemo;
	
	@MetaData(value="卖家备注旗帜")
	@Column(name="SELLER_FLAG")
	private String sellerFlag;
	
	@MetaData(value="发票抬头")
	@Column(name="INVOICE_NAME")
	private String invoiceName;
	
	@MetaData(value="发票类型")
	@Column(name="INVOICE_TYPE")
	private String invoiceType;
	
	@MetaData(value="买家昵称")
	@Column(name="BUYER_NICK")
	private String buyerNick;
	
	@MetaData(value="top动态字段")
	@Column(name="TRADE_ATTR")
	private String tradeAttr;
	
	@MetaData(value="使用信用卡支付金额数")
	@Column(name="CREDIT_CARD_FEE")
	private String creditCardFee;
	
	@MetaData(value="分阶段付款的状态")
	@Column(name="STEP_TRADE_STATUS")
	private String stepTradeStatus;
	
	@MetaData(value="分阶段付款的已付款金额")
	@Column(name="STEP_PAID_FEE")
	private String stepPaidFee;
	
	@MetaData(value="订单异常给用户的描述")
	@Column(name="MARK_DESC")
	private String markDesc;
	
	@MetaData(value="创建交易的物流方式")
	@Column(name="SHIPPING_TYPE")
	private String shippingType;
	
	@MetaData(value="买家货到付款服务费")
	@Column(name="BUYER_COD_FEE")
	private String buyerCodFee;
	
	@MetaData(value="卖家手工调整金额")
	@Column(name="ADJUST_FEE")
	private String adjustFee;
	
	@MetaData(value="交易内部来源")
	@Column(name="TRADE_FROM")
	private String tradeFrom;
	
	@MetaData(value="买家是否已评价")
	@Column(name="BUYER_RATE")
	private Boolean buyerRate = Boolean.FALSE;
	
	@MetaData(value="收货人的所在城市")
	@Column(name="RECEIVER_CITY")
	private String receiverCity;
	
	@MetaData(value="收货人所在地区")
	@Column(name="RECEIVER_DISTRICT")
	private String receiverDistrict;
	
	@MetaData(value="导购宝")
	@Column(name="O2O")
	private String o2o;
	
	@MetaData(value="导购员ID")
	@Column(name="O2O_GUIDE_ID")
	private String o2oGuideId;
	
	@MetaData(value="导购员门店ID")
	@Column(name="O2O_SHOP_ID")
	private String o2oShopId;
	
	@MetaData(value="导购员名称")
	@Column(name="O2O_GUIDE_NAME")
	private String o2oGuideName;
	
	@MetaData(value="导购员门店名称")
	@Column(name="O2O_SHOP_NAME")
	private String o2oShopName;
	
	@MetaData(value="导购宝提货方式 1-店内提货 2-线上发货")
	@Column(name="O2O_DELIVERY")
	private String o2oDelivery;
	
	@MetaData(value="天猫电子凭证家装")
	@Column(name="ETICKET_SERVICE_ADDR")
	private String eticketServiceAddr;
	
	@MetaData(value="处方药为审核状态")
	@Column(name="RX_AUDIT_STATUS")
	private String rxAuditStatus;
	
	@MetaData(value="时间段")
	@Column(name="ES_RANGE")
	private String esRange;
	
	@MetaData(value="时间")
	@Column(name="ES_DATE")
	private String esDate;
	
	@MetaData(value="时间")
	@Column(name="OS_DATE")
	private String osDate;
	
	@MetaData(value="时间段")
	@Column(name="OS_RANGE")
	private String osRange;
	
	@MetaData(value="")
	@Column(name="COUPON_FEE")
	private Integer couponFee;
	
	@MetaData(value="是否邮关订单")
	@Column(name="POST_GATE_DECLARE")
	private Boolean postGateDeclare = Boolean.FALSE;
	
	@MetaData(value="是否为跨境订单")
	@Column(name="CROSS_BONDED_DECLARE")
	private Boolean crossBondedDeclare = Boolean.FALSE;
	
	@MetaData(value="渠道商品相关字段")
	@Column(name="OMNICHANNEL_PARAM")
	private String omnichannelParam;
	
	@MetaData(value="组合商品")
	@Column(name="ASSEMBLY")
	private String assembly;
	
	@MetaData(value="top拦截标识 0-不拦截 1-拦截")
	@Column(name="TOP_HOLD")
	private Integer topHold;
	
	@MetaData(value="星盘标识字段")
	@Column(name="OMNI_ATTR")
	private String omniAttr;
	
	@MetaData(value="星盘业务字段")
	@Column(name="OMNI_PARAM")
	private String omniParam;
	
	@MetaData(value="是否屏蔽发货")
	@Column(name="IS_SH_SHIP")
	private Boolean isShShip =Boolean.FALSE;
	
	@MetaData(value="抢单状态 1-未处理待分发 2-抢单中 3-已发货")
	@Column(name="O2O_SNATCH_STATUS")
	private String o2oSnatchStatus;
	
	@MetaData(value="垂直市场")
	@Column(name="MARKET")
	private String market;
	
	@MetaData(value="电子凭证扫码购")
	@Column(name="ET_TYPE")
	private String etType;
	
	@MetaData(value="扫码购关联门店")
	@Column(name="ET_SHOP_ID")
	private Long etShopId;
	
	@MetaData(value="门店预约自提订单标")
	@Column(name="OBS")
	private String obs;
	 
	@Transient
	private  List<Orders>  orderList;
	
	

	public List<Orders> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Orders> orderList) {
		this.orderList = orderList;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	

	/**
	 * @return the payment
	 */
	public Double getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Boolean getSellerRate() {
		return sellerRate;
	}

	public void setSellerRate(Boolean sellerRate) {
		this.sellerRate = sellerRate;
	}

	public String getPostFee() {
		return postFee;
	}

	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverState() {
		return receiverState;
	}

	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverZip() {
		return receiverZip;
	}

	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public Date getConsignTime() {
		return consignTime;
	}

	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}

	public String getReceivedPayment() {
		return receivedPayment;
	}

	public void setReceivedPayment(String receivedPayment) {
		this.receivedPayment = receivedPayment;
	}

	public String getEstConTime() {
		return estConTime;
	}

	public void setEstConTime(String estConTime) {
		this.estConTime = estConTime;
	}

	public String getInvoiceKind() {
		return invoiceKind;
	}

	public void setInvoiceKind(String invoiceKind) {
		this.invoiceKind = invoiceKind;
	}

	public String getReceiverCountry() {
		return receiverCountry;
	}

	public void setReceiverCountry(String receiverCountry) {
		this.receiverCountry = receiverCountry;
	}

	public String getReceiverTown() {
		return receiverTown;
	}

	public void setReceiverTown(String receiverTown) {
		this.receiverTown = receiverTown;
	}

	public Integer getOrderTaxFee() {
		return orderTaxFee;
	}

	public void setOrderTaxFee(Integer orderTaxFee) {
		this.orderTaxFee = orderTaxFee;
	}

	public Double getPaidCouponFee() {
		return paidCouponFee;
	}

	public void setPaidCouponFee(Double paidCouponFee) {
		this.paidCouponFee = paidCouponFee;
	}

	public String getShopPick() {
		return shopPick;
	}

	public void setShopPick(String shopPick) {
		this.shopPick = shopPick;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getNUM_IID() {
		return NUM_IID;
	}

	public void setNUM_IID(String nUM_IID) {
		NUM_IID = nUM_IID;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public Boolean getHasPostFee() {
		return hasPostFee;
	}

	public void setHasPostFee(Boolean hasPostFee) {
		this.hasPostFee = hasPostFee;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
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

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getBuyerMemo() {
		return buyerMemo;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	public Integer getBuyerFlag() {
		return buyerFlag;
	}

	public void setBuyerFlag(Integer buyerFlag) {
		this.buyerFlag = buyerFlag;
	}

	public String getSellerMemo() {
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	public String getSellerFlag() {
		return sellerFlag;
	}

	public void setSellerFlag(String sellerFlag) {
		this.sellerFlag = sellerFlag;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getTradeAttr() {
		return tradeAttr;
	}

	public void setTradeAttr(String tradeAttr) {
		this.tradeAttr = tradeAttr;
	}

	public String getCreditCardFee() {
		return creditCardFee;
	}

	public void setCreditCardFee(String creditCardFee) {
		this.creditCardFee = creditCardFee;
	}

	public String getStepTradeStatus() {
		return stepTradeStatus;
	}

	public void setStepTradeStatus(String stepTradeStatus) {
		this.stepTradeStatus = stepTradeStatus;
	}

	public String getStepPaidFee() {
		return stepPaidFee;
	}

	public void setStepPaidFee(String stepPaidFee) {
		this.stepPaidFee = stepPaidFee;
	}

	public String getMarkDesc() {
		return markDesc;
	}

	public void setMarkDesc(String markDesc) {
		this.markDesc = markDesc;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getBuyerCodFee() {
		return buyerCodFee;
	}

	public void setBuyerCodFee(String buyerCodFee) {
		this.buyerCodFee = buyerCodFee;
	}

	public String getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(String adjustFee) {
		this.adjustFee = adjustFee;
	}

	public String getTradeFrom() {
		return tradeFrom;
	}

	public void setTradeFrom(String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}

	public Boolean getBuyerRate() {
		return buyerRate;
	}

	public void setBuyerRate(Boolean buyerRate) {
		this.buyerRate = buyerRate;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public String getReceiverDistrict() {
		return receiverDistrict;
	}

	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}

	public String getO2o() {
		return o2o;
	}

	public void setO2o(String o2o) {
		this.o2o = o2o;
	}

	public String getO2oGuideId() {
		return o2oGuideId;
	}

	public void setO2oGuideId(String o2oGuideId) {
		this.o2oGuideId = o2oGuideId;
	}

	public String getO2oShopId() {
		return o2oShopId;
	}

	public void setO2oShopId(String o2oShopId) {
		this.o2oShopId = o2oShopId;
	}

	public String getO2oGuideName() {
		return o2oGuideName;
	}

	public void setO2oGuideName(String o2oGuideName) {
		this.o2oGuideName = o2oGuideName;
	}

	public String getO2oShopName() {
		return o2oShopName;
	}

	public void setO2oShopName(String o2oShopName) {
		this.o2oShopName = o2oShopName;
	}

	public String getO2oDelivery() {
		return o2oDelivery;
	}

	public void setO2oDelivery(String o2oDelivery) {
		this.o2oDelivery = o2oDelivery;
	}

	public String getEticketServiceAddr() {
		return eticketServiceAddr;
	}

	public void setEticketServiceAddr(String eticketServiceAddr) {
		this.eticketServiceAddr = eticketServiceAddr;
	}

	public String getRxAuditStatus() {
		return rxAuditStatus;
	}

	public void setRxAuditStatus(String rxAuditStatus) {
		this.rxAuditStatus = rxAuditStatus;
	}

	public String getEsRange() {
		return esRange;
	}

	public void setEsRange(String esRange) {
		this.esRange = esRange;
	}

	public String getEsDate() {
		return esDate;
	}

	public void setEsDate(String esDate) {
		this.esDate = esDate;
	}

	public String getOsDate() {
		return osDate;
	}

	public void setOsDate(String osDate) {
		this.osDate = osDate;
	}

	public String getOsRange() {
		return osRange;
	}

	public void setOsRange(String osRange) {
		this.osRange = osRange;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

	public Boolean getPostGateDeclare() {
		return postGateDeclare;
	}

	public void setPostGateDeclare(Boolean postGateDeclare) {
		this.postGateDeclare = postGateDeclare;
	}

	public Boolean getCrossBondedDeclare() {
		return crossBondedDeclare;
	}

	public void setCrossBondedDeclare(Boolean crossBondedDeclare) {
		this.crossBondedDeclare = crossBondedDeclare;
	}

	public String getOmnichannelParam() {
		return omnichannelParam;
	}

	public void setOmnichannelParam(String omnichannelParam) {
		this.omnichannelParam = omnichannelParam;
	}

	public String getAssembly() {
		return assembly;
	}

	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}

	public Integer getTopHold() {
		return topHold;
	}

	public void setTopHold(Integer topHold) {
		this.topHold = topHold;
	}

	public String getOmniAttr() {
		return omniAttr;
	}

	public void setOmniAttr(String omniAttr) {
		this.omniAttr = omniAttr;
	}

	public String getOmniParam() {
		return omniParam;
	}

	public void setOmniParam(String omniParam) {
		this.omniParam = omniParam;
	}

	public Boolean getIsShShip() {
		return isShShip;
	}

	public void setIsShShip(Boolean isShShip) {
		this.isShShip = isShShip;
	}

	public String getO2oSnatchStatus() {
		return o2oSnatchStatus;
	}

	public void setO2oSnatchStatus(String o2oSnatchStatus) {
		this.o2oSnatchStatus = o2oSnatchStatus;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getEtType() {
		return etType;
	}

	public void setEtType(String etType) {
		this.etType = etType;
	}

	public Long getEtShopId() {
		return etShopId;
	}

	public void setEtShopId(Long etShopId) {
		this.etShopId = etShopId;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public String toString() {
		return "TransactionOrder [tid=" + tid + ", sellerNick=" + sellerNick
				+ ", picPath=" + picPath + ", payment=" + payment
				+ ", sellerRate=" + sellerRate + ", postFee=" + postFee
				+ ", receiverName=" + receiverName + ", receiverState="
				+ receiverState + ", receiverAddress=" + receiverAddress
				+ ", receiverZip=" + receiverZip + ", receiverMobile="
				+ receiverMobile + ", receiverPhone=" + receiverPhone
				+ ", consignTime=" + consignTime + ", receivedPayment="
				+ receivedPayment + ", estConTime=" + estConTime
				+ ", invoiceKind=" + invoiceKind + ", receiverCountry="
				+ receiverCountry + ", receiverTown=" + receiverTown
				+ ", orderTaxFee=" + orderTaxFee + ", paidCouponFee="
				+ paidCouponFee + ", shopPick=" + shopPick + ", num=" + num
				+ ", NUM_IID=" + NUM_IID + ", status=" + status + ", title="
				+ title + ", type=" + type + ", price=" + price
				+ ", discountFee=" + discountFee + ", hasPostFee=" + hasPostFee
				+ ", totalFee=" + totalFee + ", created=" + created
				+ ", payTime=" + payTime + ", modified=" + modified
				+ ", endTime=" + endTime + ", buyerMessage=" + buyerMessage
				+ ", buyerMemo=" + buyerMemo + ", buyerFlag=" + buyerFlag
				+ ", sellerMemo=" + sellerMemo + ", sellerFlag=" + sellerFlag
				+ ", invoiceName=" + invoiceName + ", invoiceType="
				+ invoiceType + ", buyerNick=" + buyerNick + ", tradeAttr="
				+ tradeAttr + ", creditCardFee=" + creditCardFee
				+ ", stepTradeStatus=" + stepTradeStatus + ", stepPaidFee="
				+ stepPaidFee + ", markDesc=" + markDesc + ", shippingType="
				+ shippingType + ", buyerCodFee=" + buyerCodFee
				+ ", adjustFee=" + adjustFee + ", tradeFrom=" + tradeFrom
				+ ", buyerRate=" + buyerRate + ", receiverCity=" + receiverCity
				+ ", receiverDistrict=" + receiverDistrict + ", o2o=" + o2o
				+ ", o2oGuideId=" + o2oGuideId + ", o2oShopId=" + o2oShopId
				+ ", o2oGuideName=" + o2oGuideName + ", o2oShopName="
				+ o2oShopName + ", o2oDelivery=" + o2oDelivery
				+ ", eticketServiceAddr=" + eticketServiceAddr
				+ ", rxAuditStatus=" + rxAuditStatus + ", esRange=" + esRange
				+ ", esDate=" + esDate + ", osDate=" + osDate + ", osRange="
				+ osRange + ", couponFee=" + couponFee + ", postGateDeclare="
				+ postGateDeclare + ", crossBondedDeclare="
				+ crossBondedDeclare + ", omnichannelParam=" + omnichannelParam
				+ ", assembly=" + assembly + ", topHold=" + topHold
				+ ", omniAttr=" + omniAttr + ", omniParam=" + omniParam
				+ ", isShShip=" + isShShip + ", o2oSnatchStatus="
				+ o2oSnatchStatus + ", market=" + market + ", etType=" + etType
				+ ", etShopId=" + etShopId + ", obs=" + obs + ", orderList="
				+ 111 + "]";
	}



	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Persistable#getId()
	 */
	@Override
	@Transient
	public String getId() {
		// TODO Auto-generated method stub
		return this.tid;
	}
	
}
