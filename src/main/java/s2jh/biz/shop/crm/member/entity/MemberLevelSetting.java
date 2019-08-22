package s2jh.biz.shop.crm.member.entity;

import java.math.BigDecimal;
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
@Table(name = "CRM_MEMBER_LEVEL_SETTING")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "会员等级设置")
public class MemberLevelSetting extends BaseNativeEntity{

	private static final long serialVersionUID = 7635333658152914902L;
	
	@MetaData(value="用户ID")
	@Column(name="USERID")
	private String userId;
	
	@MetaData(value="交易额度")
	@Column(name="TRADINGVOLUME")
	private BigDecimal tradingVolume;
	
	@MetaData(value="交易笔数")
	@Column(name="TURNOVER")
	private Integer turnover;
	
	@MetaData(value="折扣率")
	@Column(name="DISCOUNT")
	private String discount;
	
	@MetaData(value="创建时间")
	@Column(name="CTIME")
	private Date ctime;
	
	@MetaData(value="会员等级 0-店铺会员  1-普通会员 2-高级会员 3-vip会员 4-至尊vip会员")
	@Column(name="MEMBERLEVEL")
	private String memberlevel;
	
	@MetaData(value="是否启用 0-是 1-否")
	@Column(name="ENABLED")
	private String enabled;
	
	@MetaData(value="是否归类 0-是 1-否")
	@Column(name="ISSORTOUT")
	private String isSortOut;
	
	@MetaData(value="开启状态 0-开启 1-关闭")
	@Column(name="STATUS")
	private String status;
	
	@MetaData(value="分组编号")
	@Column(name="groupId")
	private String groupId;
	
	@MetaData(value="是否设置达到某一会员等级的交易量和交易额")
	@Column(name="HIERARCHY")
	private String hierarchy;
	
	

}
