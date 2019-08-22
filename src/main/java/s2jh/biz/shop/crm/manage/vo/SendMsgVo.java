/** 
 * Project Name:s2jh4net 
 * File Name:SendMsgVo.java 
 * Package Name:s2jh.biz.shop.crm.manage.entity 
 * Date:2017年5月27日下午8:19:30 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
  
package s2jh.biz.shop.crm.manage.vo;  

import java.io.Serializable;

/** 
 * ClassName:SendMsgVo <br/> 
 * Date:     2017年5月27日 下午8:19:30 <br/> 
 * @author   zlp
 * @version   1.0     
 */
public class SendMsgVo implements Serializable{

	/** 
	 * Project Name:s2jh4net 
	 * File Name:SendMsgVo.java 
	 * Package Name:s2jh.biz.shop.crm.manage.entity 
	 * Date:2017年5月27日下午8:19:42 
	 * Copyright (c) 2017,  All Rights Reserved. 
	 * 
	 */  
	
	private static final long serialVersionUID = -6652268350905001750L;
	
	private  String content;
	private  String msgType;
	private  String autograph;
	private  String userId;
	private  String ipAddress;
	private  String activityName;
	private  String sendTime;
	private  String sendType;
	
	private Long totalCount;
	
	private long msgId;
	
	//通过该字段区分是定时发送还是立即发送
	//使用整型,在发送时候必须赋值
	//true:定时发送,false:立即发送
	private Boolean schedule;
	
	private String queryKey;
	
	
	//订单短信群发参数
	private String smsTempId ;
	private String send_time_type;
	private String type;
	private String unsubscribeMSGVal;
	private String signVal;
	
	
	
	public String getSend_time_type() {
		return send_time_type;
	}
	public void setSend_time_type(String send_time_type) {
		this.send_time_type = send_time_type;
	}
	public String getSmsTempId() {
		return smsTempId;
	}
	public void setSmsTempId(String smsTempId) {
		this.smsTempId = smsTempId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnsubscribeMSGVal() {
		return unsubscribeMSGVal;
	}
	public void setUnsubscribeMSGVal(String unsubscribeMSGVal) {
		this.unsubscribeMSGVal = unsubscribeMSGVal;
	}
	public String getSignVal() {
		return signVal;
	}
	public void setSignVal(String signVal) {
		this.signVal = signVal;
	}
	public Boolean getSchedule() {
		return schedule;
	}
	public void setSchedule(Boolean schedule) {
		this.schedule = schedule;
	}
	public long getMsgId() {
		return msgId;
	}
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getQueryKey() {
		return queryKey;
	}
	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
  