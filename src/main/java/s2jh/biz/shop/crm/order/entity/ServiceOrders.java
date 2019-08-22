package s2jh.biz.shop.crm.order.entity;

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
@Table(name = "CRM_Service_ORDERS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "服务子订单列表")
public class ServiceOrders extends BaseNativeEntity{
	
	private static final long serialVersionUID = 3370918075685648604L;
	
	@MetaData(value="子订单ID")
	@Column(name="OID",nullable=false)
	private Long oid;
	
	@MetaData(value="服务所属的交易订单号")
	@Column(name="item_oid",nullable=false)
	private Long itemOid;
	
	@MetaData(value="服务数字编号")
	@Column(name="service_id",nullable=false)
	private Long serviceId;
	
	@MetaData(value="服务详情的URL地址")
	@Column(name="service_detail_url",nullable=false)
	private String serviceDetailUrl;
	
	@MetaData(value="购买数量")
	@Column(name="num",nullable=false)
	private Long num;	
	
	@MetaData(value="服务价格")
	@Column(name="price",nullable=false)
	private String price;
	
	@MetaData(value="子订单实付金额")
	@Column(name="payment",nullable=false)
	private String payment;
	
	@MetaData(value="商品名称")
	@Column(name="title",nullable=false)
	private String title;
	
	@MetaData(value="服务子订单总费用")
	@Column(name="total_fee",nullable=false)
	private String totalFee;
	
	@MetaData(value="买家昵称")
	@Column(name="buyer_nick",nullable=false)
	private String buyerNick;
	
	@MetaData(value="最近退款的编号")
	@Column(name="refund_id",nullable=false)
	private Long refundId;
	
	@MetaData(value="卖家昵称")
	@Column(name="seller_nick",nullable=false)
	private String sellerNick;

	@MetaData(value="服务图片地址")
	@Column(name="pic_path",nullable=false)
	private String picPath;
	
	@MetaData(value="支持家装类物流的类型")
	@Column(name="tmser_spu_code",nullable=false)
	private String tmserSpuCode;
	
}
