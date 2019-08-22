package s2jh.biz.shop.crm.order.entity;

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
@Table(name = "crm_orderReminder")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "手动订单提醒表")
public class OrderReminder extends BaseNativeEntity{
	
	private static final long serialVersionUID = 5842239797330295391L;
	
	@MetaData(value="订单时间(1：下单时间，2：付款时间，3：发货时间，4：变更时间，5:结束时间)")
	@Column(name="orderDateType")
	private String orderDateType;
	
	@MetaData(value="结束时间")
	@Column(name="endTime")
	private Date endTime;
	
	@MetaData(value="开始时间")
	@Column(name="startTime")
	private Date startTime;
	
	@MetaData(value="订单状态(0：全部订单，1：已下单未付款，2：已付款未发货，3：卖家已发货，4：卖家部分发货，5：交易成功，6：已退款，7：交易关闭)")
	@Column(name="orderStatus")
	private String orderStatus;
	
	@MetaData(value="预售状态(0：不限，1：订金未付余款未付，2：已付款未发货，3：定金和尾款都付)")
	@Column(name="bookingStatus")
	private String bookingStatus;
	
	@MetaData(value="最小订单金额")
	@Column(name="minOrderPrice")
	private Double minOrderPrice;
	
	@MetaData(value="最大订单金额")
	@Column(name="maxOrderPrice")
	private Double maxOrderPrice;
	
	@MetaData(value="最小交易次数")
	@Column(name="minTradeNum")
	private Integer minTradeNum;
	
	@MetaData(value="最大交易次数")
	@Column(name="maxTradeNum")
	private Integer maxTradeNum;
	
	@MetaData(value="订单类型(0：不限，1：预售，2：非预售)")
	@Column(name="orderType")
	private String orderType;
	
	@MetaData(value="评价状态(0：不限，1：买家未评价，2：买家未评价，卖家已评价，3：买家已评价)")
	@Column(name="evaluateStatus")
	private String evaluateStatus;
	
	@MetaData(value="地区(省份)")
	@Column(name="province")
	private String province;
	
	@MetaData(value="卖家标记(0：不屏蔽，1：红色，2：黄色，3：绿色，4：蓝色，5：紫色)")
	@Column(name="sellerSign")
	private String sellerSign;
	
	@MetaData(value="订单来源(0：不限，1：PC端，2：移动端)")
	@Column(name="orderSource")
	private String orderSource;
	
	@MetaData(value="已发送短信过滤（0：不过滤，1：当天，2：近三天，3：近七天，4：近十五天）")
	@Column(name="alreadySendMessages")
	private String alreadySendMessages;
	
	@MetaData(value="过滤条件(0：黑名单，1：中差评)")
	@Column(name="filterType")
	private String filterType;
	
	@MetaData(value="卖家编号")
	@Column(name="userId")
	private String userId;
	
	@MetaData(value="商品选择状态（0：全部商品，1：指定商品，2：排除指定商品）")
	@Column(name="selectCommodityType")
	private String selectCommodityType;
	
	@MetaData(value="商品编号(存放多个以英文逗号分隔)")
	@Column(name="commodityIds")
	private String commodityIds;
	
	@MetaData(value="短信内容")
	@Column(name="content")
	private String content;
	
	@MetaData(value="短信签名")
	@Column(name="smsSign")
	private String smsSign;
	
	@MetaData(value="是否定时(0:定时，1：不定时)")
	@Column(name="isTiming")
	private String isTiming;
	
	@MetaData(value="定时时间")
	@Column(name="timing")
	private Date timing;
	
	@MetaData(value="开启状态 1--开启 0--关闭")
	@Column(name="status")
	private String status;
	
}
