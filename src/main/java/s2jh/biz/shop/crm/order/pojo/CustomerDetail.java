package s2jh.biz.shop.crm.order.pojo;

import java.util.List;

/**
 * 营销中心效果分析客户详情
 * @ClassName: CustomerDetail  
 * @author ztk
 * @date 2018年4月23日 下午12:01:15
 */
public class CustomerDetail {
	
	private String buyerNick;//买家昵称
	private String receiverName;//买家姓名
	private String receiverMobile;//电话号
	private String memberlevel;//会员等级
	private Double totalOrderMoney;//订单金额
	private Integer orderNum;//订单数
	private Integer itemNum;//商品数
	private String receiverAddress;//地址
	
	private Double successMoney;//成交总金额
	private Integer successOrderNum;//成交订单数
	private Integer successItemNum;//成交商品数
	
	private Double waitMoney;//未付款总金额
	private Integer waitOrderNum;//未付款订单数
	private Integer waitItemNum;//未付款商品数
	
	private Double failMoney;//退款总金额
	private Integer failOrderNum;//退款订单数
	private Long failItemNum;//退款商品数
	
	private List<Long> itemIdList;
	private List<String> orderSourceList;
	public String getBuyerNick() {
		return buyerNick;
	}
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getMemberlevel() {
		return memberlevel;
	}
	public void setMemberlevel(String memberlevel) {
		this.memberlevel = memberlevel;
	}
	public Double getTotalOrderMoney() {
		return totalOrderMoney;
	}
	public void setTotalOrderMoney(Double totalOrderMoney) {
		this.totalOrderMoney = totalOrderMoney;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public Double getSuccessMoney() {
		return successMoney;
	}
	public void setSuccessMoney(Double successMoney) {
		this.successMoney = successMoney;
	}
	public Integer getSuccessOrderNum() {
		return successOrderNum;
	}
	public void setSuccessOrderNum(Integer successOrderNum) {
		this.successOrderNum = successOrderNum;
	}
	public Integer getSuccessItemNum() {
		return successItemNum;
	}
	public void setSuccessItemNum(Integer successItemNum) {
		this.successItemNum = successItemNum;
	}
	public Double getWaitMoney() {
		return waitMoney;
	}
	public void setWaitMoney(Double waitMoney) {
		this.waitMoney = waitMoney;
	}
	public Integer getWaitOrderNum() {
		return waitOrderNum;
	}
	public void setWaitOrderNum(Integer waitOrderNum) {
		this.waitOrderNum = waitOrderNum;
	}
	public Integer getWaitItemNum() {
		return waitItemNum;
	}
	public void setWaitItemNum(Integer waitItemNum) {
		this.waitItemNum = waitItemNum;
	}
	public Double getFailMoney() {
		return failMoney;
	}
	public void setFailMoney(Double failMoney) {
		this.failMoney = failMoney;
	}
	public Integer getFailOrderNum() {
		return failOrderNum;
	}
	public void setFailOrderNum(Integer failOrderNum) {
		this.failOrderNum = failOrderNum;
	}
	public Long getFailItemNum() {
		return failItemNum;
	}
	public void setFailItemNum(Long failItemNum) {
		this.failItemNum = failItemNum;
	}
	public List<Long> getItemIdList() {
		return itemIdList;
	}
	public void setItemIdList(List<Long> itemIdList) {
		this.itemIdList = itemIdList;
	}
	public List<String> getOrderSourceList() {
		return orderSourceList;
	}
	public void setOrderSourceList(List<String> orderSourceList) {
		this.orderSourceList = orderSourceList;
	}
	
	
}
