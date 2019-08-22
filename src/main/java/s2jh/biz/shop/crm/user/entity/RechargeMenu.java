package s2jh.biz.shop.crm.user.entity;

import java.math.BigDecimal;
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
@Table(name = "crm_recharge_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "收费项目列表")
public class RechargeMenu extends BaseNativeEntity {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = 1489699002461250259L;

	@MetaData(value = "收费项目代码")
	@Column(name = "mid")
	private String mid;

	@MetaData(value = "父级收费项目代码")
	@Column(name = "super_mid")
	private String superMid;

	@MetaData(value = "收费项目名称录账号")
	@Column(name = "name")
	private String name;

	@MetaData(value = "金额")
	@Column(name = "money")
	private BigDecimal money;

	@MetaData(value = "对应短信数量")
	@Column(name = "num")
	private Integer num;

	@MetaData(value = "对应短信单价")
	@Column(name = "unitPrice")
	private Double unitPrice;

	
	@MetaData(value = "免费试用:false-否,true-是")
	@Column(name = "is_probational")
	private boolean isProbational;

	@MetaData(value = ":false-不可订购,true-可订购")
	@Column(name = "status")
	private boolean status;

	
	
}
