package s2jh.biz.shop.crm.order.entity;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_ORDER_SETUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "下单关怀-基本设置")
public class OrderSetup extends BaseNativeEntity{

	private static final long serialVersionUID = 5999852846469855931L;
	
	@MetaData(value="卖家用户Id")
	@Column(name="USER_ID")
	private String userId;
	
	@MetaData(value="买家用户ID")
	@Column(name="MEMEB_INFO_ID")
	private Long memebInfoId;
	
	@MetaData(value="订单ID")
	@Column(name="ORDER_ID")
	private Long orderId;
	
	@MetaData(value="通知开始时间")
	@Column(name="START_TIME")
	private String startTime;
	
	@MetaData(value="通知结束时间")
	@Column(name="END_TIME")
	private String endTime;
	
	@MetaData(value="实付金额一")
	@Column(name="PAY_AMT_ONE")
	private BigDecimal payAmtOne;
	
	@MetaData(value="实付金额二")
	@Column(name="PAY_AMT_TWO")
	private BigDecimal payAmtTwo;
	
	@MetaData(value="当买家签收或者下单,过后多少时间发送信息")
	@Column(name="REMINDER_TIME")
	private String reminderTime;
	
	@MetaData(value="过滤条件 1-同一买家一天只催付一次 2-同一买家已付款1小时不催 3-屏蔽黑名单用户4-预售订单不催付  5-超出时间不催付 6-超出时间次日催付")
	@Column(name="FILTING_CONDITIONS")
	private String filtingConditions;
	
	@MetaData(value="设置类型 1-下单关怀 2-常规催付 3-二次催付 4-聚划算催付 5-预售催付 6-发货提醒 7-到达同城提醒 8-派件提醒 9-签收提醒 10-疑难件提醒 11-延时发货提醒 12-宝贝关怀 13-付款关怀 14-回款提醒 15-退款关怀 16-自动评价 17-批量评价 18-评价记录 19-中差管理 20-中差评监控 21-中差评安抚 22-中差评统计 23-中差评原因 24中差评原因设置 "
			+ "25-中差评原因分析 26-手动订单提醒 27-优秀催付案例 28-效果统计 29-买家申请退款 30-退款成功 31-等待退货 32-拒绝退款 33-会员短信群发 34-指定号码群发 35-订单短信群发 36-会员互动")
	@Column(name="SETTING_TYPE")
	private String settingType;
	
	@MetaData(value="是否开启或关闭状态 0--开启 1--关闭")
	@Column(name="status")
	private String status;
	
	@MetaData(value="物流提醒--疑难件提醒--超时未签收:发货超时多少天未签收")
	@Column(name="NOTSIGNIN_TIMEOUT")
	private String notSignInTimeout;
	
	@MetaData(value="物流提醒--疑难件提醒--超时无更新:在途超过多少天物流信息")
	@Column(name="NOTUPDATE_TIEMOUT")
	private String notUpdateTiemout;
	
	@MetaData(value="物流提醒--延时发货提醒--订单范围:前一个选择的日期")
	@Column(name="ORDERSCOPE_ONE")
	private String orderScopeOne;
	
	@MetaData(value="物流提醒--延时发货提醒--订单范围:后一个选择的日期")
	@Column(name="ORDERSCOPE_TWO")
	private String orderScopeTwo;
	
	@MetaData(value="物流提醒--延时发货提醒--执行类型:0--立即执行  1--执行周期（每天）")
	@Column(name="EXECUTEGENRE")
	private String executeGenre;
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getMemebInfoId() {
		return memebInfoId;
	}

	public void setMemebInfoId(Long memebInfoId) {
		this.memebInfoId = memebInfoId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		if("".equals(startTime) || "null".equals(startTime)){
			startTime = null;
		}
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		if("".equals(endTime) || "null".equals(endTime)){
			endTime = null;
		}
		this.endTime = endTime;
	}

	public BigDecimal getPayAmtOne() {
		return payAmtOne;
	}

	public void setPayAmtOne(BigDecimal payAmtOne) {
		this.payAmtOne = payAmtOne;
	}

	public BigDecimal getPayAmtTwo() {
		return payAmtTwo;
	}

	public void setPayAmtTwo(BigDecimal payAmtTwo) {
		this.payAmtTwo = payAmtTwo;
	}

	public String getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}

	public String getFiltingConditions() {
		return filtingConditions;
	}

	public void setFiltingConditions(String filtingConditions) {
		this.filtingConditions = filtingConditions;
	}

	public String getSettingType() {
		return settingType;
	}

	public void setSettingType(String settingType) {
		this.settingType = settingType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotSignInTimeout() {
		return notSignInTimeout;
	}

	public void setNotSignInTimeout(String notSignInTimeout) {
		this.notSignInTimeout = notSignInTimeout;
	}

	public String getNotUpdateTiemout() {
		return notUpdateTiemout;
	}

	public void setNotUpdateTiemout(String notUpdateTiemout) {
		this.notUpdateTiemout = notUpdateTiemout;
	}

	public String getOrderScopeOne() {
		return orderScopeOne;
	}

	public void setOrderScopeOne(String orderScopeOne) {
		this.orderScopeOne = orderScopeOne;
	}

	public String getOrderScopeTwo() {
		return orderScopeTwo;
	}

	public void setOrderScopeTwo(String orderScopeTwo) {
		this.orderScopeTwo = orderScopeTwo;
	}

	public String getExecuteGenre() {
		return executeGenre;
	}

	public void setExecuteGenre(String executeGenre) {
		this.executeGenre = executeGenre;
	}

	@Override
	public String toString() {
		return "OrderSetup [userId=" + userId + ", memebInfoId=" + memebInfoId
				+ ", orderId=" + orderId + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", payAmtOne=" + payAmtOne
				+ ", payAmtTwo=" + payAmtTwo + ", reminderTime=" + reminderTime
				+ ", filtingConditions=" + filtingConditions + ", settingType="
				+ settingType + ", status=" + status + ", notSignInTimeout="
				+ notSignInTimeout + ", notUpdateTiemout=" + notUpdateTiemout
				+ ", orderScopeOne=" + orderScopeOne + ", orderScopeTwo="
				+ orderScopeTwo + ", executeGenre=" + executeGenre + "]";
	}
	
	

}
