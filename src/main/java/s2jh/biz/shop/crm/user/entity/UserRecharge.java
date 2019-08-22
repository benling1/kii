package s2jh.biz.shop.crm.user.entity;

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
@Table(name = "CRM_USER_RECHARGE_RECORD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "用户充值记录表")
public class UserRecharge extends BaseNativeEntity {

	private static final long serialVersionUID = -6368632463779930636L;

	@MetaData(value = "用户ID")
	@Column(name = "userNick", nullable = false)
	private String userNick;

	@MetaData(value = "充值金额")
	@Column(name = "RECHARGEPRICE", nullable = false)
	private double rechargePrice;

	@MetaData(value = "充值类型 1-支付宝 2-微信 3-银行卡")
	@Column(name = "RECHARGE_TYPE")
	private String rechargeType;

	@MetaData(value = "单价")
	@Column(name = "UNIT_PRICE")
	private String unitPrice;

	@MetaData(value = "充值时间")
	@Column(name = "rechargeDate")
	private Date rechargeDate;

	@MetaData(value = "充值状态 1-成功 2-失败 3-待付款")
	@Column(name = "STATUS")
	private String status;

	@MetaData(value = "备注")
	@Column(name = "REMARKS")
	private String remarks;

	@MetaData(value = "订单编号")
	@Column(name = "orderId")
	private String orderId;

	@MetaData(value = "充值数量")
	@Column(name = "rechargeNUm")
	private Integer rechargeNUm;

}
