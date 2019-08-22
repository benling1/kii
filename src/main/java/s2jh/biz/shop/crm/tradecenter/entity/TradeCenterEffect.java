package s2jh.biz.shop.crm.tradecenter.entity;


import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import s2jh.biz.shop.crm.order.pojo.EarningOrderDetail;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_TRADE_CENTER_EFFECT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "订单中心效果分析表，定时保存")
public class TradeCenterEffect extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	@MetaData(value="id")
	@Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "assigned")
	private Long id;

	@MetaData(value="卖家昵称")
	@Column(name="USER_ID")
	private String userId;
	
	@MetaData(value="短信类型")
	@Column(name="TYPE")
	private String type;
	
	@MetaData(value="活动名称")
	@Column(name="TASK_ID")
	private Long taskId;
	
	@MetaData(value="发送短信订单数")
	@Column(name="TARGET_ORDER")
	private Integer targetOrder;
	
	@MetaData(value="发送短信订单数总金额")
	@Column(name="TARGET_FEE")
	private Double targetFee;
	
	@MetaData(value="付款订单数")
	@Column(name="EARNING_ORDER")
	private Integer earningOrder;
	
	@MetaData(value="付款订单总金额")
	@Column(name="EARNING_FEE")
	private Double earningFee;

	@MetaData(value="发送短信条数")
	@Column(name="SMS_NUM")
	private Integer smsNum;
	
	@MetaData(value="发送短信金额")
	@Column(name="SMS_MONEY")
	private Double smsMoney;
	
	@MetaData(value="短信内链接数")
	@Column(name="LINK_NUM")
	private Integer linkNum;
	
	@MetaData(value="客户点击数")
	@Column(name="CUSTOMER_CLICK")
	private Integer customerClick;
	
	@MetaData(value="页面点击数")
	@Column(name="PAGE_CLICK")
	private Integer pageClick;
	
	@MetaData(value="分析时间")
	@Column(name="EFFECT_TIME")
	private Date effectTime;
	
	@MetaData(value="分析时间")
	@Transient
	private String effectTimeStr;
	
	@MetaData(value="付款订单率")
	@Transient
	private String earningOrderRate;
	
	@MetaData(value="付款订单金额率")
	@Transient
	private String earningMoneyRate;
	
	@MetaData(value="客户点击率")
	@Transient
	private String clickRate;
	
	@MetaData(value="ROI")
	@Transient
	private String roiValue;
	
	@Override
	@Transient
	public Long getId() {
		return this.id;
	}


	
}
