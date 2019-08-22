package s2jh.biz.shop.crm.message.entity;

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
@Table(name = "CRM_SMS_BLACKLIST")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "短信黑名单")

public class SmsBlacklist extends BaseNativeEntity{
	
	private static final long serialVersionUID = 7286487347938768845L;
	
	@MetaData(value ="客户昵称")
	@Column(name="NICK")
	private String nick;
	
	@MetaData(value="手机号")
	@Column(name="PHONE")
	private String phone;
	
	@MetaData(value="黑名单类型 1-手机号 2-客户昵称")
	@Column(name="TYPE")
	private String type;
	
	@MetaData(value="添加时间")
	@Column(name="CREATEDATE")
	private Date createdate;
	
	@MetaData(value="添加来源 1-黑名单导入 2- 黑名单添加 3-回复退订")
	@Column(name="ADD_SOURCE")
	private String addSource;
	
	@MetaData(value="备注")
	@Column(name="REMARKS")
	private String remarks;
	
	@MetaData(value="添加用户编号")
	@Column(name="USERID")
	private String userId;
	
	
	@MetaData(value="内容")
	@Column(name="CONTENT")
	private String content;
	
	@MetaData(value="是否移除黑名单 0-是(不是黑名单) 1-否(是黑名单)")
	@Column(name="IS_DELETE")
	private String isDelete;

}
