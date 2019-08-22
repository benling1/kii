package s2jh.biz.shop.crm.message.entity;

import java.sql.Date;

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
@Table(name = "CRM_SMS_REMINDERLIST")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "短信提醒名单")
public class SmsReminderList extends BaseNativeEntity{

	private static final long serialVersionUID = -5211021292174616492L;

	@MetaData(value="添加人编号")
	@Column(name="USER_ID")
	private String userId;
	
	@MetaData(value="买家手机号（多个手机号以逗号分隔）")
	@Column(name="MOBLIE_NUMBER")
	private String moblieNumber;
	
	@MetaData(value="买家昵称")
	@Column(name="BUYER_NICKNAME")
	private String buyerNickname;
	
	@MetaData(value="买家姓名")
	@Column(name="BUYER_NAME")
	private String buyerName;
	
	@MetaData(value="下单时间")
	@Column(name="ORDER_TIME")
	private Date orderTime;
	
	@MetaData(value="短信内容")
	@Column(name="SMS_CONTENT")
	private String smsContent;
	
	@MetaData(value="卖家店铺名称")
	@Column(name="SELLER_SHOP_NAME")
	private String sellerShopName;
}
