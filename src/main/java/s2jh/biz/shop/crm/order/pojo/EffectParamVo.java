/** 
 * Project Name:s2jh4net 
 * File Name:EffectParamVo.java 
 * Package Name:s2jh.biz.shop.crm.order.pojo 
 * Date:2017年9月14日下午4:57:32 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
  
package s2jh.biz.shop.crm.order.pojo;  

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/** 
 * ClassName:EffectParamVo <br/> 
 * Date:     2017年9月14日 下午4:57:32 <br/> 
 * @author   zlp
 * @version   1.0     
 */
public class EffectParamVo {
	
	private  Map<String,Set<String>> partMap = new Hashtable<String,Set<String>>();
	private  Map<String,Set<String>> totalBuyerMap = new Hashtable<String,Set<String>>();
	private  Map<String,Set<String>> payBuyerMap = new Hashtable<String,Set<String>>();
	private  Map<String,Set<String>> waitPayBuyerMap = new Hashtable<String,Set<String>>();
	private  Map<String,Set<String>> refundBuyerMap = new Hashtable<String,Set<String>>();
	
	public Map<String, Set<String>> getPartMap() {
		return partMap;
	}
	public void setPartMap(Map<String, Set<String>> partMap) {
		this.partMap = partMap;
	}
	public Map<String, Set<String>> getTotalBuyerMap() {
		return totalBuyerMap;
	}
	public void setTotalBuyerMap(Map<String, Set<String>> totalBuyerMap) {
		this.totalBuyerMap = totalBuyerMap;
	}
	public Map<String, Set<String>> getPayBuyerMap() {
		return payBuyerMap;
	}
	public void setPayBuyerMap(Map<String, Set<String>> payBuyerMap) {
		this.payBuyerMap = payBuyerMap;
	}
	public Map<String, Set<String>> getWaitPayBuyerMap() {
		return waitPayBuyerMap;
	}
	public void setWaitPayBuyerMap(Map<String, Set<String>> waitPayBuyerMap) {
		this.waitPayBuyerMap = waitPayBuyerMap;
	}
	public Map<String, Set<String>> getRefundBuyerMap() {
		return refundBuyerMap;
	}
	public void setRefundBuyerMap(Map<String, Set<String>> refundBuyerMap) {
		this.refundBuyerMap = refundBuyerMap;
	}
}
  