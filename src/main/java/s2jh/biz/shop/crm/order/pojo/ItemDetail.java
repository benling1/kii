package s2jh.biz.shop.crm.order.pojo;

import java.util.List;
import java.util.Set;
/**
 * 营销中心效果分析商品详情
 * @ClassName: ItemDetail  
 * @author ztk
 * @date 2018年4月23日 下午12:01:53
 */
public class ItemDetail {

	private Long itemId;//商品id
	private String itemName;//商品名称
	private Double itemPrice;//商品价格
	private Long chooseCusNumber;//选择该商品的客户数
	private Double totalOrderMoney;//订单金额
	private Integer orderNum;//订单数
	private Long itemNum;//商品数
	private List<String> customerList;
	
	public List<String> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<String> customerList) {
		this.customerList = customerList;
	}
	private Long successCusNumber;//成交该商品的客户数
	private Double successMoney;//成交总金额
	private Integer successOrderNum;//成交订单数
	private Long successItemNum;//成交商品数
	
	private Long waitCusNumber;//等待付款该商品的客户数
	private Double waitMoney;//未付款总金额
	private Integer waitOrderNum;//未付款订单数
	private Long waitItemNum;//未付款商品数
	
	private Long failCusNumber;//退款该商品的客户数
	private Double failMoney;//退款总金额
	private Integer failOrderNum;//退款订单数
	private Long failItemNum;//退款商品数
	private Set<String> orderSourceSet;//订单来源
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Long getChooseCusNumber() {
		return chooseCusNumber;
	}
	public void setChooseCusNumber(Long chooseCusNumber) {
		this.chooseCusNumber = chooseCusNumber;
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
	public Long getItemNum() {
		return itemNum;
	}
	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}
	public Long getSuccessCusNumber() {
		return successCusNumber;
	}
	public void setSuccessCusNumber(Long successCusNumber) {
		this.successCusNumber = successCusNumber;
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
	public Long getSuccessItemNum() {
		return successItemNum;
	}
	public void setSuccessItemNum(Long successItemNum) {
		this.successItemNum = successItemNum;
	}
	public Long getWaitCusNumber() {
		return waitCusNumber;
	}
	public void setWaitCusNumber(Long waitCusNumber) {
		this.waitCusNumber = waitCusNumber;
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
	public Long getWaitItemNum() {
		return waitItemNum;
	}
	public void setWaitItemNum(Long waitItemNum) {
		this.waitItemNum = waitItemNum;
	}
	public Long getFailCusNumber() {
		return failCusNumber;
	}
	public void setFailCusNumber(Long failCusNumber) {
		this.failCusNumber = failCusNumber;
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
	public Set<String> getOrderSourceSet() {
		return orderSourceSet;
	}
	public void setOrderSourceSet(Set<String> orderSourceSet) {
		this.orderSourceSet = orderSourceSet;
	}
	
}
