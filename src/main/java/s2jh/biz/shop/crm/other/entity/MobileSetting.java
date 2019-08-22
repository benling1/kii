package s2jh.biz.shop.crm.other.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.Accessors;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_MOBILE_SETTING")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "手机号设置")
public class MobileSetting extends BaseNativeEntity {

	private static final long serialVersionUID = 1683290615878411556L;

	@MetaData(value = "卖家编号")	
	@Column(name = "user_id",nullable =false)
	private String userId;
	
	@MetaData(value = "催付效果(true:开启,false:关闭)")	
	@Column(name = "expediting")
	private Boolean expediting;
	
	@MetaData(value = "短信余额不足提醒(true:开启,false:关闭)")	
	@Column(name = "message_remainder")
	private Boolean messageRemainder;
	
	@MetaData(value = "短信余额不足提醒")	
	@Column(name = "message_count")
	private Integer messageCount;
	
	@MetaData(value = "软件过期提醒(true:开启,false:关闭)")	
	@Column(name = "service_expire")
	private Boolean serviceExpire;
	
	@MetaData(value = "最新活动促销(true:开启,false:关闭)")	
	@Column(name = "activity_notice")
	private Boolean activityNotice;
	
	@MetaData(value = "手机号")	
	@Column(name = "phone_num")
	private String phoneNum;
	
	@MetaData(value = "短信余额查询提醒-是否发送过发送(true:发送过,false:没有发送)")	
	@Column(name = "flag")
	private Boolean flag = false;
	
	@MetaData(value = "短信余额查询提醒-第一次发送时间")	
	@Column(name = "start_time")
	private Date startTime;
	
	@MetaData(value = "短信余额查询提醒-最后时间:与第一次发送的间隔一个月")	
	@Column(name = "end_time")
	private Date endTime;
	
	/**
	 * 下面的字段,其他业务请勿随便调用,后果自负,有坑!
	 * */
	/**
	 * 用户服务过期时间,关联用户表时用到
	 * 其他地方请勿随便调用
	 * */
	private transient Date expirationTime;
	/**
	 * 短信余额
	 * */
	private transient Long smsNum;
}
