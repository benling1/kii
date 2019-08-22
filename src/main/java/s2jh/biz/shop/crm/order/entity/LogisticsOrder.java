package s2jh.biz.shop.crm.order.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_ORDER_LOGISTICS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "物流订单信息")
public class LogisticsOrder extends BaseNativeEntity{

	private static final long serialVersionUID = -8824447663957587524L;
	
	@MetaData(value="交易ID")
	@Column(name="TID")
	private Long  tid;
	
	@MetaData(value="物流订单编号")
	@Column(name="ORDER_CODE")
	private String orderCode;
	
	@MetaData(value="物流订单状态")
	@Column(name="STATUS")
	private String status;
	
	@MetaData(value="是否标为快捷订单")
	@Column(name="IS_QUICK_COD_ORDER")
	private Boolean isQuickCodOrder;
	
	@MetaData(value="卖家昵称")
	@Column(name="SELLER_NICK")
	private String sellerNick;
	
	@MetaData(value="买家昵称")
	@Column(name="BUYER_NICK")
	private String buyerNick;
	
	@MetaData(value="预约取货开始时间")
	@Column(name="DELIVERY_START")
	private Date deliveryStart;
	
	@MetaData(value="预约取货结束时间")
	@Column(name="DELIVERY_END")
	private Date deliveryEnd;
	
	@MetaData(value="运单号 具体一个物流公司的运单编码")
	@Column(name="OUT_SID")
	private String outSid;
	
	@MetaData(value="货物名称")
	@Column(name="ITEM_TITLE")
	private String itemTitle;
	
	@MetaData(value="收件人姓名")
	@Column(name="RECEIVER_NAME")
	private String receiverName;
	
	@MetaData(value="收件人电话")
	@Column(name="RECEIVER_PHONE")
	private String receiverPhone;
	
	@MetaData(value="收件人手机号")
	@Column(name="RECEIVER_MOBILE")
	private String receiverMobile;
	
	@MetaData(value="物流方式 ")
	@Column(name="TYPE")
	private String type;
	
	@MetaData(value="谁承担运费")
	@Column(name="FREIGHT_PAYER")
	private String freightPayer;
	
	@MetaData(value="卖家是否确认发货")
	@Column(name="SELLER_CONFIRM")
	private String sellerConfirm;
	
	@MetaData(value="物流公司名称")
	@Column(name="COMPANY_NAME")
	private String companyName;
	
	@MetaData(value="返回发货是否成功")
	@Column(name="IS_SUCCESS")
	private Boolean isSuccess;
	
	@MetaData(value="运单创建时间")
	@Column(name="CREATED")
	private Date created;
	
	@MetaData(value="运单修改时间")
	@Column(name="MODIFIED")
	private Date modified;
	
	@MetaData(value="表明是否拆单")
	@Column(name="IS_SPILT")
	private Long isSpilt;
	
	@MetaData(value="拆单子订单列表，对应的数据是：该物流订单下的全部子订单(多个使用,号隔开)")
	@Column(name="SUB_TIDS")
	private String subTids;
	
	

}
