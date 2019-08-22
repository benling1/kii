package s2jh.biz.shop.crm.order.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "crm_effect_picture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "效果分析信息")
public class EffectPicture extends BaseNativeEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value="卖家昵称")
	@Column(name="user_id")
	private String userId;
	
	@MetaData(value="总记录id")
	@Column(name="msg_id")
	private Long msgId;
	
	@MetaData(value="总记录发送时间")
	@Column(name="send_time")
	private Date sendTime;
	
	@MetaData(value="订单来源")
	@Column(name="order_source")
	private String orderSource;
	
	@MetaData(value="下单总金额")
	@Column(name="total_fee")
	private Double totalFee;
	
	@MetaData(value="下单客户数")
	@Column(name="total_buyer")
	private Integer totalBuyer;
	
	@MetaData(value="截止到本日效果分析日期，真实的下单客户数")
	@Column(name="total_buyer_real")
	private Integer totalBuyerReal;
	
	@MetaData(value="总下单数")
	@Column(name="total_order")
	private Integer totalOrder;
	
	@MetaData(value="下单商品数")
	@Column(name="total_item")
	private Long totalItem;
	
	@MetaData(value="付款金额")
	@Column(name="pay_fee")
	private Double payFee;
	
	@MetaData(value="付款客户数")
	@Column(name="pay_buyer")
	private Integer payBuyer;
	
	@MetaData(value="截止到本日效果分析日期，真实的付款客户数")
	@Column(name="pay_buyer_real")
	private Integer payBuyerReal;
	
	@MetaData(value="付款订单数")
	@Column(name="pay_order")
	private Integer payOrder;
	
	@MetaData(value="付款商品数")
	@Column(name="pay_item")
	private Long payItem;
	
	@MetaData(value="未付款金额")
	@Column(name="wait_pay_fee")
	private Double waitPayFee;
	
	@MetaData(value="未付款客户")
	@Column(name="wait_pay_buyer")
	private Integer waitPayBuyer;
	
	@MetaData(value="截止到本日效果分析日期，真实的未付款客户数")
	@Column(name="wait_pay_buyer_real")
	private Integer waitPayBuyerReal;
	
	@MetaData(value="未付款订单数")
	@Column(name="wait_pay_order")
	private Integer waitPayOrder;
	
	@MetaData(value="未付款商品数")
	@Column(name="wait_pay_item")
	private Long waitPayItem;
	
	@MetaData(value="退款金额")
	@Column(name="refund_fee")
	private Double refundFee;
	
	@MetaData(value="退款客户数")
	@Column(name="refund_buyer")
	private Integer refundBuyer;
	
	@MetaData(value="截止到本日效果分析日期，真实的退款客户数")
	@Column(name="refund_buyer_real")
	private Integer refundBuyerReal;
	
	@MetaData(value="退款订单数")
	@Column(name="refund_order")
	private Integer refundOrder;
	
	@MetaData(value="退款商品数")
	@Column(name="refund_item")
	private Long refundItem;
	
	@MetaData(value="分析日期(只保存发送短信之后的15天的分析数据，每天存一条记录)")
	@Column(name="effect_time")
	private Date effectTime;
	
	@MetaData(value="针对每个批次的短信，只保存15天，days记录保存到第几天")
	@Column(name="days")
	private Integer days;
}
