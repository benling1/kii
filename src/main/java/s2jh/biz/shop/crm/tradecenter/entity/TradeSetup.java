package s2jh.biz.shop.crm.tradecenter.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.web.json.JsonViews;
import lombok.Getter;
import lombok.Setter;

@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_TRADE_SETUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "订单中心设置")
@Getter
@Setter
public class TradeSetup implements Serializable,Comparable<TradeSetup>{

	private static final long serialVersionUID = 6120845944295133347L;

	/**
	 * 发送时间:
	 * 下单关怀:没有发送时间(符合条件,立即发送??);
	 * 常规催付,二次催付,聚划算催付(多久发送);
	 * 付款关怀:"付款后立即发送付款关怀";
	 * 发货提醒:"订单状态变为卖家已发货是发送提醒";
	 * 延时发货提醒:"付款后,请选择***未发货进行安抚"(多久发送);
	 * 到达同城提醒:"物流显示到达客户所在城市时发送";
	 * 派件提醒:"物流显示派送时发送";
	 * 签收提醒:"物流显示签收时发送";
	 * 宝贝关怀:"物流显示签收后,请选择***发送宝贝关怀"(多久发送);
	 * 回款提醒:"收货后,请输入***请选择**(timeUnit)*发送回款提醒"(多久发送);
	 * 退款关怀:"买家发起退款申请后发送";
	 * 
	 * 好评管理:"确认收货后请选择***未进行评价"
	 * 中差评安抚:"接收到买家中差评时"
	 * 
	 * 自动评价/中差评监控
	 * 
	 * */
	
		@MetaData("主键")
	    @Id
	    @GeneratedValue(generator = "idGenerator")
	    @GenericGenerator(name = "idGenerator", strategy = "native")
	    @JsonProperty
	    private Long id;
	
		@MetaData(value = "乐观锁版本")
	    @Version
	    @Column(name = "optlock", nullable = false,columnDefinition="INT default 0")
	    @JsonProperty
	    @JsonView(JsonViews.Admin.class)
	    private Integer version = 0;
	
		//@JsonIgnore
	    @Column(length = 100)
	    @JsonProperty
	    private String createdBy;
	
	    //@JsonIgnore
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonProperty
	    protected Date createdDate;
	
	    //@JsonIgnore
	    @Column(length = 100)
	    @JsonProperty
	    private String lastModifiedBy;
	
	    //@JsonIgnore
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonProperty
	    private Date lastModifiedDate;
	
		@MetaData(value="用户昵称")
		@Column(name="user_id")
		private String  userId;
		
		@MetaData(value="设置类型 1-下单关怀 2-常规催付 3-二次催付 4-聚划算催付 5-预售催付 6-发货提醒 7-到达同城提醒 8-派件提醒 9-签收提醒 10-疑难件提醒 11-延时发货提醒 12-宝贝关怀 13-付款关怀 14-回款提醒 15-退款关怀 16-自动评价 17-批量评价 18-评价记录 19-中差管理 20-中差评监控 21-中差评安抚 22-中差评统计 23-中差评原因 24中差评原因设置 "
				+ "25-中差评原因分析 26-手动订单提醒 27-优秀催付案例 28-效果统计 29-买家申请退款 30-退款成功 31-等待退货 32-拒绝退款 33-会员短信群发 34-指定号码群发 35-订单短信群发 36-会员互动 37-好评提醒")
		@Column(name="type")
		private String type;
		
		@MetaData(value="设置/任务级别")
		@Column(name="task_level")
		private Integer taskLevel;
	
		@MetaData(value="设置/任务名称")
		@Column(name="task_name")
		private String taskName;
		
		/**
		 * true:开启
		 * false:关闭
		 * */
		@MetaData(value="设置状态")
		@Column(name="status")
		private Boolean status;
		
		/**
		 * 软删除,保存到数据库时默认true
		 * json序列化时,忽略该字段
		 * false:已删除,此数据无效
		 * true:使用中
		 * */
		@JsonIgnore
		@MetaData(value="是否删除")
		@Column(name="in_use")
		private Boolean inUse = true;
		
		/**
		 * true:持续开启(全时段执行,忽略minExecuteTime&maxExecuteTime 内容)
		 * false:指定开启时段(根据minExecuteTime&maxExecuteTime执行)
		 * */
		@MetaData(value="任务执行类型")
		@Column(name="execute_type")
		private Boolean executeType;
		
		@MetaData(value="任务执行开始时间")
		@Column(name="min_execute_time")
		private Date minExecuteTime;
		
		@MetaData(value="任务执行结束时间")
		@Column(name="max_execute_time")
		private Date maxExecuteTime;
		
		@MetaData(value="开始通知时段")
		@Column(name="min_inform_time")
		private String minInformTime;
		
		@MetaData(value="结束通知时段")
		@Column(name="max_inform_time")
		private String maxInformTime;
		
		@MetaData(value="开始排除通知时段一")
		@Column(name="min_primary_inform_time")
		private String minPrimaryInformTime;
		
		@MetaData(value="结束排除通知时段一")
		@Column(name="max_primary_inform_time")
		private String maxPrimaryInformTime;
		
		@MetaData(value="开始排除通知时段二")
		@Column(name="min_middle_inform_time")
		private String minMiddleInformTime;
		
		@MetaData(value="结束排除通知时段二")
		@Column(name="max_middle_inform_time")
		private String maxMiddleInformTime;
		
		@MetaData(value="开始排除通知时段三")
		@Column(name="min_senior_inform_time")
		private String minSeniorInformTime;
		
		@MetaData(value="结束排除通知时段三")
		@Column(name="max_senior_inform_time")
		private String maxSeniorInformTime;
		
		/**
		 * true:超出时间次日发送
		 * false: 超出时间不发送
		 * */
		@MetaData(value="超出时间段执行")
		@Column(name="time_out_inform")
		private Boolean timeOutInform;
		
		/**
		 * minutes,hours,days/"1","2","3"
		 * 1:分钟
		 * 2:小时
		 * 3:天
		 * */
		@MetaData(value="发送时间类型")
		@Column(name="time_Type")
		private Integer timeType;
		
		@MetaData(value="发送时间具体值")
		@Column(name="remind_time")
		private Integer remindTime;
		
		/**
		 * true:过滤
		 * false:不过滤
		 * */
		@MetaData(value="同一买家一天只提醒一次")
		@Column(name="filter_once")
		private Boolean filterOnce;
		
		/**
		 * true:过滤
		 * false:不过滤
		 * */
		@MetaData(value="黑名单不发送")
		@Column(name="filter_black")
		private Boolean filterBlack;
		
		/**
		 * 只针对催付有效
		 * true:过滤
		 * false:不过滤
		 * */
		@MetaData(value="同一买家有付过款的不发送")
		@Column(name="filter_hassent")
		private Boolean filterHassent;
		
		/**
		 * 订单发送范围/订单流转阻塞
		 * true:开启新任务后产生的订单
		 * false:订单状态流转至此处的订单(此状态时忽略chosenTime内容)
		 * */
		@MetaData(value="订单发送范围")
		@Column(name="trade_block")
		private Boolean tradeBlock;
		
		/**
		 * 订单开启时间(为订单发送范围服务)
		 * */
		@MetaData(value="订单开启时间")
		@Column(name="chosen_time")
		private Date chosenTime;
		
		
		//TODO	
		/**
		 * 卖家备注旗帜（与淘宝网上订单的卖家备注旗帜对应，只有卖家才能查看该字段）未标记类型:0;红、黄、绿、蓝、紫 分别对应 1、2、3、4、5
		 * */
		@MetaData(value="标记类型")
		@Column(name="seller_flag")
		private String sellerFlag;
		
		/**
		 * PC端:TAOBAO;手机:WAP,WAP;聚划算:JHS;  详情参考 TradesInfo.java
		 * 备注:如有多个使用,隔开   使用常量！！！不允许手动编辑输入
		 * */
		@MetaData(value="订单来源")
		@Column(name="trade_from")
		private String tradeFrom;
		
		/**
		 * 初次下单用户:"1";店铺客户:"2";普通会员:"3";高级会员:"4";VIP会员:"5";至尊VIP会员:"6"
		 * 备注:如有多个使用,隔开
		 * */
		@MetaData(value="会员等级")
		@Column(name="member_level")
		private String memberLevel;
		
		@MetaData(value="最小商品数量")
		@Column(name="min_product_num")
		private Integer minProductNum;
		
		@MetaData(value="最大商品数量")
		@Column(name="max_product_num")
		private Integer maxProductNum;
		
		@MetaData(value="最小支付金额")
		@Column(name="min_payment")
		private BigDecimal minPayment;
		
		@MetaData(value="最大支付金额")
		@Column(name="max_payment")
		private BigDecimal maxPayment;
		
		/**
		 * 地区内容可参见:s2jh/biz/shop/utils/AreaUtils.java
		 * 备注:如有多个使用,隔开
		 * */
		@MetaData(value="省")
		@Column(name="province")
		private String province;
		
		@MetaData(value="市")
		@Column(name="city")
		private String city;
		
		/**
		 *  true:指定商品
		 *  false:排除指定商品
		 * */
		@MetaData(value="指定商品")
		@Column(name="product_type")
		private Boolean productType;
	
		/**
		 * 备注:如有多个使用,隔开
		 * */
		@MetaData(value="商品ID串")
		@Column(name="products")
		private String products;
		
		//TODO
		@MetaData(value="短信模板内容")
		@Column(name="sms_content")
		private String  smsContent;
		
		/**
		 *  true:延时评价
		 *  false:立即评价(忽略:会员延时延时时间的内容)
		 * */
		@MetaData(value="延时评价")
		@Column(name="delay_evaluate")
		private Boolean delayEvaluate;
		
		/**
		 *  延时几天评价,注意:单位是天!!
		 * */
		@MetaData(value="延时天数")
		@Column(name="delay_date")
		private Integer delayDate;
		
		/**
		 * 评价类型:好:"good";中:"neutral";差:"bad"
		 * */
		@MetaData(value="评价类型")
		@Column(name="evaluate_type")
		private String evaluateType;
		
		/**
		 *  true:  黑名单用户不自动评价 (忽略:delayEvaluate,delayDate,evaluateType 内容)
		 *	false: 当客户是黑名单是给予指定评价
		 * */
		@MetaData(value="黑名单时的自动评价")
		@Column(name="evaluate_black")
		private Boolean evaluateBlack;
		
		
		/**
		 * 黑名单评价类型:好:"good";中:"neutral";差:"bad"
		 * */
		@MetaData(value="黑名单评价类型")
		@Column(name="evaluate_black_type")
		private String evaluateBlackType;
		
		/**
		 * 黑名单评价内容
		 * */
		@MetaData(value="黑名单时的自动评价")
		@Column(name="evaluate_black_content")
		private String evaluateBlackContent;
		
		/**
		 * 备注:仅针对中差评监控有效,其他类型忽略
		 * true:买家评价中评时通知卖家
		 * false:反之
		 * */
		@MetaData(value="中评通知我")
		@Column(name="neutral_evaluate_inform")
		private Boolean neutralEvaluateInform;
		
		
		/**
		 * 备注:仅针对中差评监控有效,其他类型忽略
		 * true:买家评价差评时通知卖家
		 * false:反之
		 * */
		@MetaData(value="差评通知我")
		@Column(name="bad_evaluate_inform")
		private Boolean badEvaluateInform;
		
		/**
		 * 通知手机号码按顺序排列最多添加五个,用逗号隔开
		 * **/
		@MetaData(value="中差评-通知号码")
		@Column(name="inform_mobile")
		private String informMobile;

		@Override
		public int compareTo(TradeSetup tradeSetup) {
			//以优先等级为准，等级数越小，等级越高，例如1级为最高级，最优先。如果优先等级相同以最后修改时间较早为准。  (循环的时候因为排序原因从小到大，所以此处倒回来排)
			if(tradeSetup == null)
				return 1;
			if(this.getTaskLevel() ==null)
				return 1;
			if(tradeSetup.getTaskLevel()==null)
				return -1;
			if(this.getTaskLevel()<tradeSetup.getTaskLevel()){
				return -1;
			}else if(this.getTaskLevel()>tradeSetup.getTaskLevel()){
				return 1;
			}else{
				if(this.getLastModifiedDate()==null){
					return 1;
				}else if(tradeSetup.getLastModifiedDate()==null){
					return -1;
				}else{
					if(this.getLastModifiedDate().getTime()<tradeSetup.getLastModifiedDate().getTime()){
						return -1;
					}else if(this.getLastModifiedDate().getTime()>tradeSetup.getLastModifiedDate().getTime()){
						return 1;
					}else{
						if(this.getId()==null){
							return 1;
						}else if(tradeSetup.getId()==null){
							return -1;
						}else if(this.getId()>tradeSetup.getId()){
							return -1;
						}else if(this.getId()<tradeSetup.getId()){
							return 1;
						}
					}
				}
			}
			return 0;
		}
		
		
}
