package s2jh.biz.shop.crm.tradecenter.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lab.s2jh.core.annotation.MetaData;
import s2jh.biz.shop.common.deserializer.MyDeserializer;


/**	
* @Title: TradeSetVo
* @Description: (订单中心,各种设置vo类)
* @author:jackstraw_yu
*/
public class TradeSetupVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	//用户昵称
	private String  userId;
	
	//"设置类型 1-下单关怀 2-常规催付 3-二次催付 4-聚划算催付 5-预售催付 6-发货提醒 7-到达同城提醒 8-派件提醒 9-签收提醒 10-疑难件提醒 11-延时发货提醒 12-宝贝关怀 13-付款关怀 14-回款提醒 15-退款关怀 16-自动评价 17-批量评价 18-评价记录 19-中差管理 20-中差评监控 21-中差评安抚 22-中差评统计 23-中差评原因 24中差评原因设置 "
	//		+ "25-中差评原因分析 26-手动订单提醒 27-优秀催付案例 28-效果统计 29-买家申请退款 30-退款成功 31-等待退货 32-拒绝退款 33-会员短信群发 34-指定号码群发 35-订单短信群发 36-会员互动
	@JsonDeserialize(using = MyDeserializer.class)
	private String type;
	
	//设置/任务级别
	private Integer taskLevel;

	//设置/任务名称"
	@JsonDeserialize(using = MyDeserializer.class)
	private String taskName;
	
	/**
	 * true:开启
	 * false:关闭
	 * */
	//设置状态
	private Boolean status;
	
	/**
	 * true:持续开启(全时段执行,忽略minExecuteTime&maxExecuteTime 内容)
	 * false:指定开启时段(根据minExecuteTime&maxExecuteTime执行)
	 * */
	//任务执行类型
	private Boolean executeType;
	
	//任务执行开始时间
	private Date minExecuteTime;
	
	//任务执行结束时间
	private Date maxExecuteTime;
	
	//开始通知时段
	@JsonDeserialize(using = MyDeserializer.class)
	private String minInformTime;
	
	//结束通知时段
	@JsonDeserialize(using = MyDeserializer.class)
	private String maxInformTime;
	
	//TODO
	//开始排除通知时段一
	@JsonDeserialize(using = MyDeserializer.class)
	private String minPrimaryInformTime;
	
	//结束排除通知时段一
	@JsonDeserialize(using = MyDeserializer.class)
	private String maxPrimaryInformTime;
	
	//开始排除通知时段二
	@JsonDeserialize(using = MyDeserializer.class)
	private String minMiddleInformTime;
	
	//结束排除通知时段二
	@JsonDeserialize(using = MyDeserializer.class)
	private String maxMiddleInformTime;
	
	//开始排除通知时段三
	@JsonDeserialize(using = MyDeserializer.class)
	private String minSeniorInformTime;
	
	//结束排除通知时段三
	@JsonDeserialize(using = MyDeserializer.class)
	private String maxSeniorInformTime;
	
	/**
	 * true:超出时间不发送
	 * false: 超出时间次日发送
	 * */
	//超出时间段执行
	private Boolean timeOutInform;
	
	/**
	 * minutes,hours,days/"1","2","3"
	 * */
	//发送时间类型
	private Integer timeType;
	
	//发送时间具体值
	private Integer remindTime;
	
	/**
	 * true:过滤
	 * false:不过滤
	 * */
	//同一买家一天只提醒一次
	private Boolean filterOnce;
	
	/**
	 * true:过滤
	 * false:不过滤
	 * */
	//黑名单不发送
	private Boolean filterBlack;
	
	
	/**
	 * 只针对催付有效
	 * true:过滤
	 * false:不过滤
	 * */
	//同一买家有付过款的不发送
	private Boolean filterHassent;
	
	/**
	 * 订单发送范围/订单流转阻塞
	 * true:开启新任务后产生的订单
	 * false:订单状态流转至此处的订单(此状态时忽略chosenTime内容)
	 * */
	private Boolean tradeBlock;
	
	/**
	 * "勾选第二选项"的时间(为订单发送范围服务)
	 * */
	private Date chosenTime;
	
	//TODO	
	/**
	 * 顺序:未标记,红黄青蓝紫
	 * 未标记0;红:"1";黄:"2";青:"3";蓝:"4";紫:"5"
	 * 备注:如有多个使用,隔开
	 * */
	//标记类型
	@JsonDeserialize(using = MyDeserializer.class)
	private String sellerFlag;
	
	/**
	 * PC端:TAOBAO;手机:WAP;聚划算:JHS;
	 * 备注:如有多个使用,隔开
	 * */
	//订单来源
	@JsonDeserialize(using = MyDeserializer.class)
	private String tradeFrom;
	
	/**
	 * 初次下单用户:"1";店铺客户:"2";普通会员:"3";高级会员:"4";VIP会员:"5";至尊VIP会员:"6"
	 * 备注:如有多个使用,隔开
	 * */
	//会员等级
	@JsonDeserialize(using = MyDeserializer.class)
	private String memberLevel;
	
	//最小商品数量
	private Integer minProductNum;
	
	//最大商品数量
	private Integer maxProductNum;
	
	//最小支付金额
	private BigDecimal minPayment;
	
	//最大支付金额
	private BigDecimal maxPayment;
	
	/**
	 * 地区内容可参见:s2jh/biz/shop/utils/AreaUtils.java
	 * 备注:如有多个使用,隔开
	 * */
	//省
	@JsonDeserialize(using = MyDeserializer.class)
	private String province;
	
	//市
	@JsonDeserialize(using = MyDeserializer.class)
	private String city;
	/**
	 *  true:指定商品
	 *  false:排除指定商品
	 * */
	//指定商品
	private Boolean productType;

	/**
	 * 备注:如有多个使用,隔开
	 * */
	//商品ID串
	@JsonDeserialize(using = MyDeserializer.class)
	private String products;
	
	//TODO
	//短信模板内容
	@JsonDeserialize(using = MyDeserializer.class)
	private String  smsContent;
	
	/**
	 *  true:延时评价
	 *  false:立即评价(忽略:会员延时延时时间的内容)
	 * */
	//延时评价
	//delay_evaluate
	private Boolean delayEvaluate;
	
	/**
	 *  延时几天评价,注意:单位是天!!
	 * */
	//延时天数
	private Integer delayDate;
	
	/**
	 * 评价类型:好:"1";中:"2";差:"3"/好:"good";中:"neutral";差:"bad"
	 * */
	//评价类型
	@JsonDeserialize(using = MyDeserializer.class)
	private String evaluateType;
	
	/**
	 *  true:  黑名单用户不自动评价 (忽略:delayEvaluate,delayDate,evaluateType 内容)
	 *	false: 当客户是黑名单是给予指定评价
	 * */
	//黑名单时的自动评价
	private Boolean evaluateBlack;
	
	
	/**
	 * 黑名单评价类型:好:"good";中:"neutral";差:"bad"
	 * */
	//黑名单评价类型
	@JsonDeserialize(using = MyDeserializer.class)
	private String evaluateBlackType;
	
	/**
	 * 黑名单评价内容
	 * */
	//黑名单时的自动评价
	@JsonDeserialize(using = MyDeserializer.class)
	private String evaluateBlackContent;
	
	/**
	 * 备注:仅针对中差评监控有效,其他类型忽略
	 * true:买家评价中评时通知卖家
	 * false:反之
	 * */
	//中评通知我
	private Boolean neutralEvaluateInform;
	
	
	/**
	 * 备注:仅针对中差评监控有效,其他类型忽略
	 * true:买家评价差评时通知卖家
	 * false:反之
	 * */
	//差评通知我
	private Boolean badEvaluateInform;

	/**
	 * 通知手机号码按顺序排列最多添加五个,用逗号隔开
	 * **/
	//中差评-通知号码
	@JsonDeserialize(using = MyDeserializer.class)
	private String informMobile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTaskLevel() {
		return taskLevel;
	}

	public void setTaskLevel(Integer taskLevel) {
		this.taskLevel = taskLevel;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getExecuteType() {
		return executeType;
	}

	public void setExecuteType(Boolean executeType) {
		this.executeType = executeType;
	}

	public Date getMinExecuteTime() {
		return minExecuteTime;
	}

	public void setMinExecuteTime(Date minExecuteTime) {
		this.minExecuteTime = minExecuteTime;
	}

	public Date getMaxExecuteTime() {
		return maxExecuteTime;
	}

	public void setMaxExecuteTime(Date maxExecuteTime) {
		this.maxExecuteTime = maxExecuteTime;
	}

	public String getMinInformTime() {
		return minInformTime;
	}

	public void setMinInformTime(String minInformTime) {
		this.minInformTime = minInformTime;
	}

	public String getMaxInformTime() {
		return maxInformTime;
	}

	public void setMaxInformTime(String maxInformTime) {
		this.maxInformTime = maxInformTime;
	}

	public String getMinPrimaryInformTime() {
		return minPrimaryInformTime;
	}

	public void setMinPrimaryInformTime(String minPrimaryInformTime) {
		this.minPrimaryInformTime = minPrimaryInformTime;
	}

	public String getMaxPrimaryInformTime() {
		return maxPrimaryInformTime;
	}

	public void setMaxPrimaryInformTime(String maxPrimaryInformTime) {
		this.maxPrimaryInformTime = maxPrimaryInformTime;
	}

	public String getMinMiddleInformTime() {
		return minMiddleInformTime;
	}

	public void setMinMiddleInformTime(String minMiddleInformTime) {
		this.minMiddleInformTime = minMiddleInformTime;
	}

	public String getMaxMiddleInformTime() {
		return maxMiddleInformTime;
	}

	public void setMaxMiddleInformTime(String maxMiddleInformTime) {
		this.maxMiddleInformTime = maxMiddleInformTime;
	}

	public String getMinSeniorInformTime() {
		return minSeniorInformTime;
	}

	public void setMinSeniorInformTime(String minSeniorInformTime) {
		this.minSeniorInformTime = minSeniorInformTime;
	}

	public String getMaxSeniorInformTime() {
		return maxSeniorInformTime;
	}

	public void setMaxSeniorInformTime(String maxSeniorInformTime) {
		this.maxSeniorInformTime = maxSeniorInformTime;
	}

	public Boolean getTimeOutInform() {
		return timeOutInform;
	}

	public void setTimeOutInform(Boolean timeOutInform) {
		this.timeOutInform = timeOutInform;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public Integer getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Integer remindTime) {
		this.remindTime = remindTime;
	}

	public Boolean getFilterOnce() {
		return filterOnce;
	}

	public void setFilterOnce(Boolean filterOnce) {
		this.filterOnce = filterOnce;
	}

	public Boolean getFilterBlack() {
		return filterBlack;
	}

	public void setFilterBlack(Boolean filterBlack) {
		this.filterBlack = filterBlack;
	}

	public Boolean getFilterHassent() {
		return filterHassent;
	}

	public void setFilterHassent(Boolean filterHassent) {
		this.filterHassent = filterHassent;
	}

	public Boolean getTradeBlock() {
		return tradeBlock;
	}

	public void setTradeBlock(Boolean tradeBlock) {
		this.tradeBlock = tradeBlock;
	}

	public Date getChosenTime() {
		return chosenTime;
	}

	public void setChosenTime(Date chosenTime) {
		this.chosenTime = chosenTime;
	}

	public String getSellerFlag() {
		return sellerFlag;
	}

	public void setSellerFlag(String sellerFlag) {
		this.sellerFlag = sellerFlag;
	}

	public String getTradeFrom() {
		return tradeFrom;
	}

	public void setTradeFrom(String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public Integer getMinProductNum() {
		return minProductNum;
	}

	public void setMinProductNum(Integer minProductNum) {
		this.minProductNum = minProductNum;
	}

	public Integer getMaxProductNum() {
		return maxProductNum;
	}

	public void setMaxProductNum(Integer maxProductNum) {
		this.maxProductNum = maxProductNum;
	}

	public BigDecimal getMinPayment() {
		return minPayment;
	}

	public void setMinPayment(BigDecimal minPayment) {
		this.minPayment = minPayment;
	}

	public BigDecimal getMaxPayment() {
		return maxPayment;
	}

	public void setMaxPayment(BigDecimal maxPayment) {
		this.maxPayment = maxPayment;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Boolean getProductType() {
		return productType;
	}

	public void setProductType(Boolean productType) {
		this.productType = productType;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Boolean getDelayEvaluate() {
		return delayEvaluate;
	}

	public void setDelayEvaluate(Boolean delayEvaluate) {
		this.delayEvaluate = delayEvaluate;
	}

	public Integer getDelayDate() {
		return delayDate;
	}

	public void setDelayDate(Integer delayDate) {
		this.delayDate = delayDate;
	}

	public String getEvaluateType() {
		return evaluateType;
	}

	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}

	public Boolean getEvaluateBlack() {
		return evaluateBlack;
	}

	public void setEvaluateBlack(Boolean evaluateBlack) {
		this.evaluateBlack = evaluateBlack;
	}

	public String getEvaluateBlackType() {
		return evaluateBlackType;
	}

	public void setEvaluateBlackType(String evaluateBlackType) {
		this.evaluateBlackType = evaluateBlackType;
	}

	public String getEvaluateBlackContent() {
		return evaluateBlackContent;
	}

	public void setEvaluateBlackContent(String evaluateBlackContent) {
		this.evaluateBlackContent = evaluateBlackContent;
	}

	public Boolean getNeutralEvaluateInform() {
		return neutralEvaluateInform;
	}

	public void setNeutralEvaluateInform(Boolean neutralEvaluateInform) {
		this.neutralEvaluateInform = neutralEvaluateInform;
	}

	public Boolean getBadEvaluateInform() {
		return badEvaluateInform;
	}

	public void setBadEvaluateInform(Boolean badEvaluateInform) {
		this.badEvaluateInform = badEvaluateInform;
	}

	public String getInformMobile() {
		return informMobile;
	}

	public void setInformMobile(String informMobile) {
		this.informMobile = informMobile;
	}

	@Override
	public String toString() {
		return "TradeSetupVo [id=" + id + ", userId=" + userId + ", type=" + type + ", taskLevel=" + taskLevel
				+ ", taskName=" + taskName + ", status=" + status + ", executeType=" + executeType + ", minExecuteTime="
				+ minExecuteTime + ", maxExecuteTime=" + maxExecuteTime + ", minInformTime=" + minInformTime
				+ ", maxInformTime=" + maxInformTime + ", minPrimaryInformTime=" + minPrimaryInformTime
				+ ", maxPrimaryInformTime=" + maxPrimaryInformTime + ", minMiddleInformTime=" + minMiddleInformTime
				+ ", maxMiddleInformTime=" + maxMiddleInformTime + ", minSeniorInformTime=" + minSeniorInformTime
				+ ", maxSeniorInformTime=" + maxSeniorInformTime + ", timeOutInform=" + timeOutInform + ", timeType="
				+ timeType + ", remindTime=" + remindTime + ", filterOnce=" + filterOnce + ", filterBlack="
				+ filterBlack + ", filterHassent=" + filterHassent + ", tradeBlock=" + tradeBlock + ", chosenTime="
				+ chosenTime + ", sellerFlag=" + sellerFlag + ", tradeFrom=" + tradeFrom + ", memberLevel="
				+ memberLevel + ", minProductNum=" + minProductNum + ", maxProductNum=" + maxProductNum
				+ ", minPayment=" + minPayment + ", maxPayment=" + maxPayment + ", province=" + province + ", city="
				+ city + ", productType=" + productType + ", products=" + products + ", smsContent=" + smsContent
				+ ", delayEvaluate=" + delayEvaluate + ", delayDate=" + delayDate + ", evaluateType=" + evaluateType
				+ ", evaluateBlack=" + evaluateBlack + ", evaluateBlackType=" + evaluateBlackType
				+ ", evaluateBlackContent=" + evaluateBlackContent + ", neutralEvaluateInform=" + neutralEvaluateInform
				+ ", badEvaluateInform=" + badEvaluateInform + ", informMobile=" + informMobile + "]";
	}

	
}
