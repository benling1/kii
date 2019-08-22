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
@Table(name = "CRM_SMS_REMIND")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value ="短信提醒设置")
public class SmsRemind extends BaseNativeEntity{

	private static final long serialVersionUID = -2576965309217090510L;
	
	@MetaData(value="通知时间")
	@Column(name="NOTIFICATION_TIME")
	private String notificationTime;
	
	@MetaData(value="实际付款金额")
	@Column(name="ACTUAL_PRICE")
	private String actualPrice;
	
	@MetaData(value="区域")
	@Column(name="REGION")
	private String region;
	
	@MetaData(value="卖家标记是否屏蔽")
	@Column(name="SELLER_TAG")
	private Boolean sellerTag =Boolean.FALSE;
	
	@MetaData(value="订单来源")
	@Column(name="ORDER_SOURCE")
	private String order_source;
	
	@MetaData(value="商品编号(多个商品以逗号分隔)")
	@Column(name="commodity_number")
	private String commodityNumber;
	
	@MetaData(value="操作人编号")
	@Column(name="USER_ID")
	private Long userId;
	
	@MetaData(value="创建时间")
	@Column(name="CREATEDATE")
	private Date createdate;
	
	@MetaData(value="催付订单时间")
	@Column(name="REMINDER_ORDER_TIME")
	private Integer reminderOrderTime;
	
	@MetaData(value="提醒类型(如：下单关怀，付款关怀等)")
	@Column(name="TYPE")
	private String type;
	
	@MetaData(value="状态（0为禁，1为启用）")
	@Column(name="STATE")
	private Integer state;
	
	
}
