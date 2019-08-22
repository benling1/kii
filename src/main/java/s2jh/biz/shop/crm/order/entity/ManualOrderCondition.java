package s2jh.biz.shop.crm.order.entity;

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
@Table(name = "crm_manualOrderCondition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "手动提醒高级条件设置表")
public class ManualOrderCondition extends BaseNativeEntity{
	
	private static final long serialVersionUID = 4763939598872163821L;
	
	@MetaData(value="订单类型（1：下单时间，2：付款时间，3：发货时间，4：变更时间，5：结束时间）")
	@Column(name="orderCondition")
	private String orderCondition;
	
	@MetaData(value="开始时间")
	@Column(name="startDate")
	private String startDate;
	
	@MetaData(value="结束时间")
	@Column(name="endDate")
	private String endDate;
	
	@MetaData(value="订单状态(1:已下单未付款，2:已付款未发货，3:卖家已发货，4:卖家部分发货，5:交易成功，6:已退款，7:交易关闭）")
	@Column(name="orderStatus")
	private String orderStatus;
	
	@MetaData(value="最小订单金额")
	@Column(name="minOrderPrice")
	private String minOrderPrice;
		
	@MetaData(value="最大订单金额")
	@Column(name="maxOrderPice")
	private String maxOrderPice;
	
	@MetaData(value="最小交易次数")
	@Column(name="minTransactionNum")
	private Integer minTransactionNum;
	
	@MetaData(value="最大交易次数")
	@Column(name="maxTransactionNum")
	private Integer maxTransactionNum;
	
	@MetaData(value="订单类型（0：不限，1：预售，2：非预售）")
	@Column(name="orderType")
	private Integer orderType;
	
	@MetaData(value="评价状态（0：不限，1：买家未评价，2：买家未评价，卖家已评价，3：买家已评价）")
	@Column(name="evaluateStatus")
	private Integer evaluateStatus;
	
	@MetaData(value="地区")
	@Column(name="region")
	private String region;
	
	@MetaData(value="卖家备注旗帜（与淘宝网上订单的卖家备注旗帜对应，只有卖家才能查看该字段）红、黄、绿、蓝、紫 分别对应 1、2、3、4、5")
	@Column(name="sellerSign")
	private String sellerSign;
	
	@MetaData(value="订单来源（0：不限，1：手机端，2：PC端，3：聚划算）")
	@Column(name="orderSource")
	private Integer orderSource;
	

	@MetaData(value="商品选择：0：全部，1：指定，2：排除")
	@Column(name="productSelect")
	private String productSelect;
	
	@MetaData(value="商品编号")
	@Column(name="commodityIds")
	private String commodityIds;
		
	@MetaData(value="短信过滤（0：不过滤，1：今天，2：近3天，3：近7天，4：近15天）")
	@Column(name="smsFilter")
	private Integer smsFilter;
	
	@MetaData(value="过滤条件（0：黑名单，1：中差评）")
	@Column(name="filterCondition")
	private Integer filterCondition;
	
	@MetaData(value="卖家用户编号")
	@Column(name="userId")
	private String userId;
	
	@MetaData(value="是否定时(0:定时，1：不定时)")
	@Column(name="isTiming")
	private String isTiming;
	
	@MetaData(value="定时时间")
	@Column(name="timing")
	private Date timing;
	
	@MetaData(value="开启状态")
	@Column(name="status")
	private String status;
}
