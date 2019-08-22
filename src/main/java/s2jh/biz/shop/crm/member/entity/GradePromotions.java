package s2jh.biz.shop.crm.member.entity;

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
@Table(name = "CRM_GRADE_PROMOTIONS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "买家会员等级")
public class GradePromotions extends BaseNativeEntity{
	
	private static final long serialVersionUID = -4180922022391635640L;
	
	@MetaData(value="买家会员级别 ")
	@Column(name="CUR_GRADE",nullable=false)
	private String curGrade;
	
	@MetaData(value="买家会员名称 0-店铺客户 1-普通会员 2-高级会员 3-vip会员 4-至尊vip会员")
	@Column(name="cur_grade_name",nullable=false)
	private String curGradeName;
	
	@MetaData(value="会员级别折扣率")
	@Column(name="discount",nullable=false)
	private Long discount;
	
	@MetaData(value="升级到下一个级别的需要的交易金额")
	@Column(name="next_upgrade_amount",nullable=false)
	private Long nextUpgradeAmount;
	
	@MetaData(value="升级到下一个级别的需要的交易量")
	@Column(name="next_upgrade_count",nullable=false)
	private Long nextUpgradeCount;
	
	@MetaData(value="升级到下一个级别的需要的交易量")
	@Column(name="next_grade_name",nullable=false)
	private String nextGradeName;
	
	@MetaData(value="下一个级别名称")
	@Column(name="next_grade",nullable=false)
	private String nextGrade;
	
	@MetaData(value="下一个级别名称")
	@Column(name="user_id",nullable=false)
	private String userId;
	
	@MetaData(value="启用状态（0:启用，1:未启用）")
	@Column(name="status",nullable=false)
	private String status;
	
}
