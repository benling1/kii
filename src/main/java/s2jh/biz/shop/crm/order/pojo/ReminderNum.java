package s2jh.biz.shop.crm.order.pojo;

import java.util.Date;
import java.util.List;

public class ReminderNum {
	private Double targetMoney;//催付金额
	private Double successMoney;//汇款金额
	private Integer targetOrder;//催付订单
	private Integer successOrder;//回款订单
	private Integer smsNum;//短信消费条数
	private Double smsMoney;//短信消费金额
	private String tids;  
	private String date;
	private Long sendTime;
	private Integer year;
	private Date dateTime;
	public Integer getYear() {
		return year;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getSendTime() {
		return sendTime;
	}
	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	public String getTids() {
		return tids;
	}
	public void setTids(String tids) {
		this.tids = tids;
	}
	private List<String> tidList;//发送记录满足条件的tid
	public List<String> getTidList() {
		return tidList;
	}
	public void setTidList(List<String> tidList) {
		this.tidList = tidList;
	}
	public Double getTargetMoney() {
		return targetMoney;
	}
	public void setTargetMoney(Double targetMoney) {
		this.targetMoney = targetMoney;
	}
	public Double getSuccessMoney() {
		return successMoney;
	}
	public void setSuccessMoney(Double successMoney) {
		this.successMoney = successMoney;
	}
	public Integer getTargetOrder() {
		return targetOrder;
	}
	public void setTargetOrder(Integer targetOrder) {
		this.targetOrder = targetOrder;
	}
	public Integer getSuccessOrder() {
		return successOrder;
	}
	public void setSuccessOrder(Integer successOrder) {
		this.successOrder = successOrder;
	}
	public Integer getSmsNum() {
		return smsNum;
	}
	public void setSmsNum(Integer smsNum) {
		this.smsNum = smsNum;
	}
	public Double getSmsMoney() {
		return smsMoney;
	}
	public void setSmsMoney(Double smsMoney) {
		this.smsMoney = smsMoney;
	}
	
}
