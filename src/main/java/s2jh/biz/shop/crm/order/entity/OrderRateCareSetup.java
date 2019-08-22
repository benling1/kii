package s2jh.biz.shop.crm.order.entity;

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
@Table(name = "CRM_ORDER_RATECARE_SETUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "订单中心自动评价设置")
public class OrderRateCareSetup extends BaseNativeEntity {

	/**
	 * 订单中心自动评价设置
	 */
	private static final long serialVersionUID = 1L;

	@MetaData(value ="子订单id")
	@Column(name="oid")
	private String oid;
	
	@MetaData(value ="自动评价是否开启0-开启  1-关闭")
	@Column(name="status")
	private String status;
	
	@MetaData(value ="评价类型good(好评),neutral(中评),bad(差评)")
	@Column(name="result")
	private String result;
	
	@MetaData(value ="客户是否是黑名单0-是黑名单  1-不是黑名单")
	@Column(name="isblacklist")
	private Integer isBlacklist;
	
	@MetaData(value ="评价条件  1-立即评 2-延迟评  3-抢评")
	@Column(name="rate_choose")
	private String rateChoose;
	
	@MetaData(value ="如果客户是黑名单进行的评价类型0-不自动评价  1-好评 2-中评 3-差评")
	@Column(name="blacklist_rate_type")
	private String blacklistRateType;
	
	@MetaData(value ="用户id")
	@Column(name="user_id")
	private String userId;
	
	@MetaData(value ="买家中评或差评自动加入黑名单  neutral(中评),bad(差评)")
	@Column(name="auto_addBlacklist")
	private String autoAddBlacklist;

	@MetaData(value ="评价内容")
	@Column(name="content")
	private String content;
	
	@MetaData(value ="延迟评价中延迟几天评价")
	@Column(name="defer_rate_day")
	private String deferRateDay;
	
	@MetaData(value ="评价关怀与中差评管理表示字段    0--(评价关怀)自动评价   1--(中差评管理)中差评监控  2--(中差评管理)中差评安抚")
	@Column(name="appraise_type")
	private String appraiseType;
	
	
	//===========中差评使用字段======================
	@MetaData(value ="中差评监控--是否开启条件筛选  0-当有中评时通知我  1-当有差评时通知我")
	@Column(name="status_filtrate")
	private String statusFiltrate;
	
	@MetaData(value ="中差评监控--接受短信号码设置")
	@Column(name="accept_sms_phone")
	private String acceptSmsPhone;
	
	@MetaData(value ="中差评安抚--发送时间一")
	@Column(name="send_time_one")
	private String sendTimeOne;
	
	@MetaData(value ="中差评安抚--发送时间一")
	@Column(name="send_time_two")
	private String sendTimeTwo;
	
	@MetaData(value ="中差评安抚--实付金额一")
	@Column(name="money_one")
	private String moneyOne;
	
	@MetaData(value ="中差评安抚--实付金额二")
	@Column(name="money_two")
	private String moneyTwo;
	
	@MetaData(value="中差评安抚--不发送标旗订单  1-红 2-黄 3-绿 4-蓝 5-粉   多个使用,好隔开")
	@Column(name="flagcolor")
	private String flagcolor;
	
	@MetaData(value="中差评安抚--不发送订单来源  0-PC端(电脑) 1-手机端  2-聚划算  多个使用,好隔开")
	@Column(name="ORDER_SOURCE")
	private String orderSource;
	
	@MetaData(value="中差评安抚--不发送地区筛选  省份汉字全称用,隔开")
	@Column(name="locality")
	private String locality;
	
	@MetaData(value="中评内容")
	@Column(name="neutral_content")
	private String neutralContent;
	
	@MetaData(value="差评内容")
	@Column(name="bad_content")
	private String badContent;
}
