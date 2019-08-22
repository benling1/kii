package s2jh.biz.shop.crm.user.entity;

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
@Table(name = "CRM_OrderApplicationInfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "用户订购应该信息表")
public class OrderApplicationInfo extends BaseNativeEntity {

	private static final long serialVersionUID = 7368846969179684103L;

	@MetaData(value = "淘宝会员名")
	@Column(name = "userNick")
	private String userNick;

	@MetaData(value = "订购记录id")
	@Column(name = "subId")
	private Long subId;

	@MetaData(value = "收费项目名称")
	@Column(name = "itemName")
	private String itemName;

	@MetaData(value = "收费项目代码")
	@Column(name = "itemCode")
	private String itemCode;

	@MetaData(value = "是否到期提醒")
	@Column(name = "expireNotice")
	private boolean expireNotice;

	@MetaData(value = "订购关系到期时间")
	@Column(name = "endDate")
	private Date endDate;

	@MetaData(value = "状态(1=有效 2=过期)")
	@Column(name = "effectStatus")
	private Integer effectStatus;

	@MetaData(value = "是否自动续费")
	@Column(name = "autosub")
	private boolean autosub;

	@MetaData(value = "应用名称")
	@Column(name = "articleName")
	private String articleName;

	@MetaData(value = "应用收费代码")
	@Column(name = "articleCode")
	private String articleCode;

}
