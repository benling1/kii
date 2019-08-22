package s2jh.biz.shop.crm.tradecenter.vo;

import java.io.Serializable;
import java.util.Date;

import lab.s2jh.core.annotation.MetaData;


/**	
* @Title: SmsRecordDTOVo
* @Description: (订单中心,发送记录查询vo类)
*/
public class SmsRecordDTOVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@MetaData(value="发送状态 1：发送失败，2：发送成功，3：手机号码不正确，4：号码重复， 5 ：黑名单， 6 ：重复被屏蔽 /重复发送")
	private Integer status;
	
	@MetaData(value="短信类型 设置类型 1-下单关怀 2-常规催付 3-二次催付 4-聚划算催付 5-预售催付 "
			+ "6-发货提醒 7-到达同城提醒 8-派件提醒 9-签收提醒 10-疑难件提醒 11-延时发货提醒 12-宝贝关怀 13-付款关怀 "
			+ "14-回款提醒 15-退款关怀 16-自动评价 17-批量评价 18-评价记录 19-中差评查看 20-中差评监控 21-中差评安抚 "
			+ "22-中差评统计 23-中差评原因 24中差评原因设置 25-中差评原因分析 26-手动订单提醒 27-优秀催付案例 28-效果统计 "
			+ "29-买家申请退款 30-退款成功 31-等待退货 32-拒绝退款 33-会员短信群发 34-指定号码群发 35-订单短信群发 36-会员互动")
	private String type;
	
	@MetaData(value="买家昵称")
	private String buyerNick;
	
	@MetaData(value="短信接收号码")
	private String recNum;
	
	@MetaData(value="订单编号")
	private String orderId;
	
	@MetaData(value="发送开始时间")
	private String bTime;
	
	@MetaData(value="发送结束时间")
	private String eTime;
	
	@MetaData(value="发送开始时间")
	private Long beginTime;
	
	@MetaData(value="发送结束时间")
	private Long endTime;
	
	@MetaData(value="电话是否模糊")
	private boolean like;
	
	@MetaData(value="分页页码")
	private String pageNo;
	
	@MetaData(value="买家昵称、短信接收号码、订单编号")
    private String parameters;
	
	@MetaData(value="订单中心任务名称，可为空")
	private String taskName;
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getbTime() {
		return bTime;
	}

	public void setbTime(String bTime) {
		this.bTime = bTime;
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}


	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getRecNum() {
		return recNum;
	}

	public void setRecNum(String recNum) {
		this.recNum = recNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

}
