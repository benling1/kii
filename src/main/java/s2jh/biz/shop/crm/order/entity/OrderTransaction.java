package s2jh.biz.shop.crm.order.entity;

import java.math.BigDecimal;
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
@Table(name = "CRM_order_transaction_analysis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "订单成交分析")
public class OrderTransaction extends BaseNativeEntity{

	private static final long serialVersionUID = -2413430074312741664L;

	/** 时间段（如1点，为1） **/
	@MetaData(value="时间段（如1点，为1）")
	@Column(name="DATE")
    private Integer date; 

	@MetaData(value="成交会员数")
	@Column(name="DEAL_MEMBER_NUM")
    private Integer dealMemberNum; 

	@MetaData(value="成交笔数")
	@Column(name="DEAL_NUMBER")
    private Integer dealNumber; 

	@MetaData(value="成交金额")
	@Column(name="DEAL_MONEY")
    private BigDecimal dealMoney; 

	@MetaData(value="成交商品件数")
	@Column(name="DEAL_COMMODITY_NUM")
    private Integer dealCommodityNum; 

	@MetaData(value="客单价")
	@Column(name="CUSTOMER_PRICE")
    private BigDecimal customerPrice; 

	@MetaData(value="货单价")
	@Column(name="GOODS_PRICE")
    private BigDecimal goodsPrice; 

	@MetaData(value="笔单价")
	@Column(name="EACH_PRICE")
    private BigDecimal eachPrice; 

	@MetaData(value="转化率")
	@Column(name="PERCENT_CONVERSION")
    private String percentConversion; 

	@MetaData(value="拍下会员数）")
	@Column(name="TAKE_MEMBER_NUM")
    private Integer takeMemberNum; 

	@MetaData(value="拍下笔数")
	@Column(name="TAKE_EACH_NUM")
    private Integer takeEachNum; 

	@MetaData(value="拍下金额")
	@Column(name="TAKE_PTICE")
    private BigDecimal takePrice; 

	@MetaData(value="拍下商品件数")
	@Column(name="TAKE_GOODS_NUM")
    private Integer takeGoodsNum; 

	@MetaData(value="用户编号）")
	@Column(name="USER_ID")
    private Long userId; 

	@MetaData(value="订单类型（1为PC端，2为无线端））")
	@Column(name="TYPE")
    private Integer type; 

	
	@MetaData(value="创建时间")
	@Column(name="CREATEDATE")
    private Date createdate; 
	
}