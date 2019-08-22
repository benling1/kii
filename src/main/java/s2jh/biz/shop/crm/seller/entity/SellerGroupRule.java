package s2jh.biz.shop.crm.seller.entity;

import java.sql.Date;

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
@Table(name = "CRM_GROUPS_RULE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "卖家分组规则")
public class SellerGroupRule extends BaseNativeEntity{

	private static final long serialVersionUID = -8948892853672566543L;
	
	@MetaData(value="分组ID")
	@Column(name="GROUP_ID",nullable=false)
	private Long groupId;
	
	@MetaData(value="会员昵称")
	@Column(name="BUYER_NICKNAME")
	private String buyerNickname;
	
	@MetaData(value="会员姓名")
	@Column(name="BUYER_NAME")
	private String buyerName;
	
	@MetaData(value="所在省份")
	@Column(name="PROVINCE")
	private String province;
	
	@MetaData(value="性别 1-男 2-女")
	@Column(name="SEX")
	private String sex;
	
	@MetaData(value="年龄段")
	@Column(name="AGE_RANGE")
	private String ageRange;
	
	@MetaData(value="年龄")
	@Column(name="AGE")
	private Integer age;
	
	@MetaData(value="职业")
	@Column(name="OCCUPATION")
	private String occupation;
	
	@MetaData(value="生日")
	@Column(name="BIRTHDAY")
	private String birthday;
	
	@MetaData(value="QQ号")
	@Column(name="QQ")
	private String qq;
	
	@MetaData(value="微信号")
	@Column(name="WECHAT")
	private String wechat;
	
	@MetaData(value="手机号")
	@Column(name="PHONE")
	private String phone;
	
	@MetaData(value="手机号段")
	@Column(name="PHONE_RANGE")
	private String phoneRange;
	
	@MetaData(value="邮箱")
	@Column(name="EMAIL")
	private String email;
	
	@MetaData(value="邮箱类型")
	@Column(name="EMAIL_TYPE")
	private String emailType;
	
	@MetaData(value="注册时间")
	@Column(name="REGISTER_DATE")
	private Date registerDate;
	
	@MetaData(value="评价得分")
	@Column(name="SCORE")
	private String score;
	
	@MetaData(value="备注")
	@Column(name="REMARKS")
	private String remarks;
	
	@MetaData(value="最小交易时间")
	@Column(name="minTradeTime")
	private String minTradeTime;
	
	@MetaData(value="最大交易时间")
	@Column(name="maxTradeTime")
	private String maxTradeTime;

	@MetaData(value="最大交易次数")
	@Column(name="maxTradeNum")
	private Integer maxTradeNum;
	
	@MetaData(value="最小交易次数")
	@Column(name="minTradeNum")
	private Integer minTradeNum;
	
	@MetaData(value="最小累计金额")
	@Column(name="minAccumulatedAmount")
	private String minAccumulatedAmount;
	
	@MetaData(value="最大累计金额")
	@Column(name="maxAccumulatedAmount")
	private String maxAccumulatedAmount;
	
	@MetaData(value="最大平均客单价")
	@Column(name="maxAveragePrice")
	private String maxAveragePrice;
	
	@MetaData(value="最小平均客单价")
	@Column(name="minAveragePrice")
	private String minAveragePrice;
	
	@MetaData(value="会员等级")
	@Column(name="memberGrade")
	private String memberGrade;
	
	@MetaData(value="卖家编号")
	@Column(name="userId")
	private String userId;
	
	@MetaData(value="最近交易状态")
	@Column(name="tradeTimeStatus")
	private String tradeTimeStatus;
	
	@MetaData(value="商品编号")
	@Column(name="itemIds")
	private String itemIds;
	
	@MetaData(value="最近交易时间数（只能为正整数）")
	@Column(name="tradeDays")
	private int tradeDays;
	
	@MetaData(value="最近交易时间单位（天，月，年）")
	@Column(name="tradeType")
	private String tradeType;
}
