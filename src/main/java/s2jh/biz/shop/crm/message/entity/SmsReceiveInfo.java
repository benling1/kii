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
@Table(name = "CRM_SMS_RECEIVE_INFO")
@Cache(usage =CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "卖家接收到短信")


public class SmsReceiveInfo extends BaseNativeEntity{

	private static final long serialVersionUID = 3764755769678462862L;
	@MetaData(value = "卖家编号")	
	@Column(name = "user_id")
	private String userId;
	
	@MetaData(value = "卖家昵称")	
	@Column(name = "taobao_nick")
	private String taobaoNick;
	
	@MetaData(value = "买家昵称")	
	@Column(name = "buyer_nick")
	private String buyerNick;
	
	@MetaData(value = "发送手机号")	
	@Column(name = "SEND_PHONE")
	private String sendPhone;
	
	@MetaData(value = "内容")	
	@Column(name = "CONTENT")
	private String content;
	
	@MetaData(value = "接收手机号")	
	@Column(name = "RECEIVE_PHONE")
	private String receivePhone;
	
	@MetaData(value = "接收时间")	
	@Column(name = "RECEIVE_DATE")
	private Date receiveDate;
	
	@MetaData(value = "备注")	
	@Column(name = "REMARKS")
	private String remarks;
	
	@MetaData(value = "短信状态(0未读，1已读)")	
	@Column(name = "status")
	private Integer status;

}
