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
@Table(name = "CRM_TRADE_EXT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "交易扩展表信息")
public class TradeExt extends BaseNativeEntity {

	private static final long serialVersionUID = -6432179969065597623L;
	
	@MetaData(value="交易订单ID")
	@Column(name="tID",nullable=false)
	private Long tid;
	
	@MetaData(value="enable前扩展标识位")
	@Column(name="before_enable_flag",nullable=false)
	private Long beforeEnableFlag;
	
	@MetaData(value="关闭订单前扩展标识位")
	@Column(name="before_close_flag",nullable=false)
	private Long beforeCloseFlag;
	
	@MetaData(value="付款前扩展标识位")
	@Column(name="before_pay_flag",nullable=false)
	private Long beforePayFlag;
	
	@MetaData(value="发货前扩展标识位")
	@Column(name="before_ship_flag",nullable=false)
	private Long beforeShipFlag;
	
	@MetaData(value="确认收货前扩展标识位")
	@Column(name="before_confirm_flag",nullable=false)
	private Long beforeConfirmFlag;
	
	@MetaData(value="评价前扩展标识位")
	@Column(name="before_rate_flag",nullable=false)
	private Long beforeRateFlag;
	
	@MetaData(value="退款前扩展标识位")
	@Column(name="before_refund_flag",nullable=false)
	private Long beforeRefundFlag;
	
	@MetaData(value="修改前扩展标识位")
	@Column(name="before_modify_flag",nullable=false)
	private Long beforeModifyFlag;
	
	@MetaData(value="第三方状态，第三方自由定义")
	@Column(name="third_party_status",nullable=false)
	private Long thirdPartyStatus;
	
	@MetaData(value="第三方个性化数据")
	@Column(name="extra_data",nullable=false)
	private String extraData;
	
	@MetaData(value="Attributes标记")
	@Column(name="ext_attributes",nullable=false)
	private String extAttributes;
}
