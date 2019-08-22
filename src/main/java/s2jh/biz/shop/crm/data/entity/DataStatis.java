package s2jh.biz.shop.crm.data.entity;

import java.math.BigDecimal;
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
@Table(name = "CRM_data_statis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value ="数据统计表")
public class DataStatis extends BaseNativeEntity{

	private static final long serialVersionUID = 3941633239873371389L;
	
	@MetaData(value="用户id")
	@Column(name="USER_ID")
	private Long userId;
	
	@MetaData(value="创建时间")
	@Column(name="CREATEDATE")
	private Date createdate;
	
	@MetaData(value="订单数")
	@Column(name="ORDER_NUM")
	private Integer orderNum;
	
	@MetaData(value="退款中")
	@Column(name="REFUNDMENT")
	private Integer refundment;
	
	@MetaData(value="待付款")
	@Column(name="PENDING_PAYMENT")
	private Integer pendingPayment;
	
	@MetaData(value="待发货")
	@Column(name="PENDING_CONSIGNMENT")
	private Integer pendingConsignment;
	
	@MetaData(value="销售中")
	@Column(name="SALE")
	private Integer sale;
	
	@MetaData(value="客单价（每一个顾客平均购买商品的金额，也即是平均交易金额）")
	@Column(name="PER_CUSTOMER_TRANSACTION")
	private BigDecimal perCustomerTransaction;
	
	@MetaData(value="全店成交转化（成交转化率=成交用户数/访客数）")
	@Column(name="TRANSACTION_CONVERSION")
	private String transactionConversion;
	
	@MetaData(value="成交额")
	@Column(name="TRANSACTION_PRICE")
	private BigDecimal transactionPrice;
	
	@MetaData(value="购物车宝贝")
	@Column(name="SHOPPING_CART_COMMODITY")
	private Integer shoppingCartCommodity;
	
	@MetaData(value="被收藏的宝贝")
	@Column(name="COLLECTION_COMMODITY")
	private Integer collectionCommodity;
	
	@MetaData(value="被浏览过的宝贝")
	@Column(name="BROWSE_COMMODITY")
	private Integer browseCommodity;
	
	@MetaData(value="买家秀点赞数")
	@Column(name="BUYER_PRAISE_NUM")
	private Integer buyerPraiseNum;
	
	@MetaData(value="买家秀浏览量")
	@Column(name="BUYER_ACCESS_AMOUNT")
	private Integer buyerAccessAmount;
	
	@MetaData(value="买家评论数")
	@Column(name="BUYER_COMMENT_NUM")
	private Integer buyerCommentNum;
	
	@MetaData(value="访问总量")
	@Column(name="ACCESS_AMOUNT")
	private Integer accessAmount;
	
	@MetaData(value="总流量")
	@Column(name="TOTAL_FLOW")
	private String totalFlow;
	
	@MetaData(value="pc流量")
	@Column(name="PC_FLOW")
	private String pcFlow;
	
	@MetaData(value="无线流量")
	@Column(name="WAP_FLOW")
	private String wapFlow;
	
	@MetaData(value="聚划算成交额")
	@Column(name="JHS_transaction_price")
	private BigDecimal jhsTransactionPrice;
	
	@MetaData(value="pc订单数")
	@Column(name="PC_ORDER_NUM")
	private Integer pcOrderNum;
	
	@MetaData(value="无线订单数")
	@Column(name="WAP_ORDER_NUM")
	private Integer wapOrderNum;
	
	@MetaData(value="pc成交额")
	@Column(name="PC_TRANSACTION_PRICE")
	private Integer pcTransactionPrice;
	
	@MetaData(value="无线成交额")
	@Column(name="WAP_TRANSACTION_PRICE")
	private Integer wapTransactionPrice;
	
	@MetaData(value="已厨窗推荐")
	@Column(name="CC_RECOMMEND")
	private Integer ccRecommend;
	
	@MetaData(value="直通车点击转化率")
	@Column(name="ZTC_CLICK_CONVERSION_RATE")
	private String ztcClickConversionRate;
	
	@MetaData(value="直通车账户余额")
	@Column(name="ZTC_ACCOUNT_BALANCE")
	private BigDecimal ztcAccountBalance;
	
	@MetaData(value="直通车展现量")
	@Column(name="ZTC_SHOW_NUM")
	private Integer ztcShowNum;
	
	@MetaData(value="直通车花费")
	@Column(name="ztc_spend")
	private BigDecimal ztcSpend;
	
	@MetaData(value="直通车投入产出比")
	@Column(name="ztc_investment_produce")
    private String ztcInvestmentProduce;
	
	@MetaData(value="数据类型 1-订单数 2-退款中 3-待付款 4-待发货 5-出售中 6-成交额 7-购物车宝贝 8-被收藏宝贝"
			+ "9-被浏览宝贝 10-总访客 11-PC流量 12-无线流量 13-聚划算成交额 14-PC订单数 15-PC成交额 16-无线成交额"
			+ "17-无线订单数")
	@Column(name="DATA_TYPE")
	private String dataType;
}
