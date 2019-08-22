package s2jh.biz.shop.crm.seller.entity;

import java.math.BigDecimal;
import java.sql.Date;

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
@Table(name = "CRM_GRADE_RULE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "卖家等级规则")
public class GradeRule extends BaseNativeEntity{

	private static final long serialVersionUID = 6649932433667148724L;
	
	@MetaData(value="创建人")
	@Column(name="CRM_GRADE_RULE",nullable=false)
	private Long userId;
	
	@MetaData(value="交易额")
	@Column(name="AMOUNT")
	private BigDecimal amount;
	
	@MetaData(value="交易量")
	@Column(name="COUNT")
	private Integer count;
	
	@MetaData(value="会员等级 0-店铺客户 1-普通会员 2-高级会员 3-vip会员 4-至尊vip会员")
	@Column(name="GRADE")
	private Integer grade;
	
	@MetaData(value="会员折扣率")
	@Column(name="DISCOUNT")
	private Integer discount;
	
	@MetaData(value="是否达到某一会员交易量和交易额")
	@Column(name="HIERARCHY")
	private Boolean hierarchy = Boolean.FALSE;
	
	@MetaData(value="创建时间")
	@Column(name="CREATEDATE")
	private Date createDate;

}
