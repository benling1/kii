package s2jh.biz.shop.crm.message.entity;


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
@Table(name = "CRM_SMS_MOBILE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "手机黑名单")
public class SmsMobile extends BaseNativeEntity{

	private static final long serialVersionUID = 79151901888562578L;
	
	@MetaData(value="卖家用户ID")
	@Column(name="USERID")
	private String userId;
	
	@MetaData(value="买家会员手机号")
	@Column(name="MOBILE")
	private String mobile;
	
	@MetaData(value="创建时间")
	@Column(name="CTIME")
	private Date ctime;
	
	@MetaData(value="黑名单Id")
	@Column(name="BLICKLISTID")
	private Long blicklistId;
	
	@MetaData(value="是否移除黑名单 0-是(不是黑名单) 1-否(是黑名单)---短信发送排除表")
	@Column(name="ISDELETE")
	private String isDelete;
	
	@MetaData(value="备注")
	@Column(name="REMARK")
	private String remark;
	
	
	
	@MetaData(value="添加类型（只针对于客户管理的手机添加）")
	@Column(name="TYPE")
	private String type;
	
	
	@MetaData(value="买家昵称")
	@Column(name="BUYER_NICK")
	private String buyerNick;

}
