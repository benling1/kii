package s2jh.biz.shop.crm.message.entity;

import java.util.Date;

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
@Table(name = "CRM_SMS_SEND_SCHEDULE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "短信发送详情")
public class SmsSendInfoSchedule extends BaseNativeEntity{

	private static final long serialVersionUID = 8573348205456977770L;
	
	@MetaData(value="用户ID")
	@Column(name="user_id",nullable = false)
	private String userId;
	
	@MetaData(value="接收人手机号")
	@Column(name="PHONE")
	private String phone;
	
	@MetaData(value="接收人昵称")
	@Column(name="NICKNAME")
	private String nickname;
	
	@MetaData(value="短息内容")
	@Column(name="CONTENT")
	private String content;
	
	@MetaData(value="短信类型")
	@Column(name="TYPE")
	private String type;
	
	@MetaData(value="发送时间")
	@Column(name="send_time")
	private Date sendTime;
	
	
	/*发送成功--1
	手机号码不正确--2
	短信余额不足--3
	重复被屏蔽--4
	黑名单--5
	等待被发送--6
	初次短信判断标记--99
	同一买家一天只催付一次--7
	同一买家已付款1小时不催--8
	屏蔽黑名单用户--9
	未设置短信提示语--10*/
	@MetaData(value="短信发送状态")
	@Column(name="status")
	private Integer status;
	
	@MetaData(value="实际扣除短信条数")
	@Column(name="actual_deduction")
	private Integer actualDeduction;
	
	@MetaData(value="短信渠道")
	@Column(name="channel")
	private String channel;
	

	@MetaData(value="短信定时开始时间比如10:00，12:25，可以为空")
	@Column(name="startSend")
	private Date startSend;
	
	@MetaData(value="短信定时结束小时，如果发送时已经超出了结束延后次日发送，可以为空")
	@Column(name="endSend")
	private Date endSend;
	
	@MetaData(value="超时次日发送短信")
	@Column(name="delayDate")
	private Boolean delayDate;
	
	@MetaData(value="短信被定时执行次数，反复延期不能超过五次，超过五次短信无法发送或者是处理效率速度需要提升")
	@Column(name="flag")
	private int flag;
	
	
	@MetaData(value="要发送的短信主订单号")
	@Column(name="tid")
	private Long tid;

	@MetaData(value="总记录id")
	@Column(name="msg_id")
	private Long msgId;
	
	@MetaData(value="子订单号，自动评价内容专属")
	@Column(name="oid")
	private Long oid;
	
	@MetaData(value="评价类型，自动评价内容专属")
	@Column(name="rate_type")
	private String rateType;
	
	/**
	 * true:过滤
	 * false:不过滤
	 * */
	@MetaData(value="同一买家一天只提醒一次")
	@Column(name="filter_once")
	private Boolean filterOnce;
	
	/**
	 * 只针对催付有效
	 * true:过滤
	 * false:不过滤
	 * */
	@MetaData(value="同一买家有付过款的不发送")
	@Column(name="filter_hassent")
	private Boolean filterHassent;
	

	@MetaData(value="任务名称")
	@Column(name="taskId")
	private String taskId;
	
	@MetaData(value="中差评监控手机号码")
	@Column(name="inform_Mobile")
	private String informMobile;
}
