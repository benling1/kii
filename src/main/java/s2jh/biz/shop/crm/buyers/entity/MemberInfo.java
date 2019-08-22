package s2jh.biz.shop.crm.buyers.entity;

import java.math.BigDecimal;
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


@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_MEMBER_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "买家会员信息")
public class MemberInfo extends BaseNativeEntity{

	private static final long serialVersionUID = 2631633136371313064L;
	
	@MetaData(value="买家会员ID")
	@Column(name="BUYER_ID",nullable=false)
	private String buyerId;
	
	@MetaData(value="会员的等级ID")
	@Column(name="GRADE_ID",nullable=false)
	private Long gradeId;
	
	@MetaData(value="卖家ID")
	@Column(name="USER_ID")
	private String userId;
	
	@MetaData(value="买家昵称")
	@Column(name="BUYER_NICK")
	private String buyerNick;
	
	@MetaData(value="会员状态 1-正常 2-黑名单")
	@Column(name="STATUS")
	private String status;
	
	@MetaData(value="交易成功笔数")
	@Column(name="TRADE_COUNT")
	private Long tradeCount;
	
	@MetaData(value="交易成功的金额")
	@Column(name="TRADE_AMOUNT")
	private String tradeAmount;
	
	@MetaData(value="交易关闭的金额")
	@Column(name="CLOSE_TRADE_AMOUNT")
	private String closeTradeAmount;
	
	@MetaData(value="交易关闭的笔数")
	@Column(name="CLOSE_TRADE_COUNT")
	private Long closeTradeCount;
	
	@MetaData(value="省份")
	@Column(name="PROVINCE")
	private String province;
	
	@MetaData(value="城市")
	@Column(name="CITY")
	private String city;
	
	@MetaData(value="购买的宝贝件数")
	@Column(name="ITEM_NUM")
	private Integer itemNum;
	
	@MetaData(value="平均客单价")
	@Column(name="AVG_PRICE")
	private BigDecimal avgPrice;
	
	@MetaData(value="关系来源 1-交易成功 2-未成交 3-卖家主动吸纳")
	@Column(name="RELATION_SOURCE")
	private Integer relationSource;
	
	@MetaData(value="最后交易时间")
	@Column(name="LAST_TRADE_TIME")
	private Date lastTradeTime;
	
	@MetaData(value="交易关闭的宝贝件数")
	@Column(name="ITEM_CLOSE_COUNT")
	private Integer itemCloseCount;
	
	@MetaData(value="性别 1-男 2-女")
	@Column(name="SEX")
	private String sex;
	
	@MetaData(value="年龄")
	@Column(name="AGE")
	private String age;
	
	@MetaData(value="职业")
	@Column(name="OCCUPATION")
	private String occupation;
	
	@MetaData(value="生日")
	@Column(name="BIRTHDAY")
	private String birthday;
	
	@MetaData(value="QQ号")
	@Column(name="QQ")
	private String qq;
	
	@MetaData(value="微信")
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
	
	@MetaData(value="买家自定义分组等级设置条件表id")
	@Column(name="MEMBER_LEVEL_SETTING_ID")
	private Long member_level_setting_id;
	
	
	@MetaData(value="短信黑名单Id")
	@Column(name="SMS_BLACKLIST_ID")
	private Long smsBlackListId;

	
	@MetaData(value="会员等级名称")
	@Column(name="cur_grade_name")
	private String curGradeName;


	@MetaData(value="会员识别码")
	@Column(name="memberInfo_code")
	private String memberInfoCode;
	
	
	@MetaData(value="会员退款状态")
	@Column(name="refund_status")
	private String refundStatus;
	
	public String getRefundStatus() {
		return refundStatus;
	}


	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}


	/**
	 * 从订单中分离出会员信息,重写会员的getter,setter,equals,hashCode:去重
	 * 
	 *
	*/


	public String getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}


	public Long getGradeId() {
		return gradeId;
	}


	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getBuyerNick() {
		return buyerNick;
	}


	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Long getTradeCount() {
		return tradeCount;
	}


	public void setTradeCount(Long tradeCount) {
		this.tradeCount = tradeCount;
	}


	public String getTradeAmount() {
		return tradeAmount;
	}


	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}


	public String getCloseTradeAmount() {
		return closeTradeAmount;
	}


	public void setCloseTradeAmount(String closeTradeAmount) {
		this.closeTradeAmount = closeTradeAmount;
	}


	public Long getCloseTradeCount() {
		return closeTradeCount;
	}


	public void setCloseTradeCount(Long closeTradeCount) {
		this.closeTradeCount = closeTradeCount;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public Integer getItemNum() {
		return itemNum;
	}


	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}


	public BigDecimal getAvgPrice() {
		return avgPrice;
	}


	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}


	public Integer getRelationSource() {
		return relationSource;
	}


	public void setRelationSource(Integer relationSource) {
		this.relationSource = relationSource;
	}


	public Date getLastTradeTime() {
		return lastTradeTime;
	}


	public void setLastTradeTime(Date lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}


	public Integer getItemCloseCount() {
		return itemCloseCount;
	}


	public void setItemCloseCount(Integer itemCloseCount) {
		this.itemCloseCount = itemCloseCount;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public String getOccupation() {
		return occupation;
	}


	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getQq() {
		return qq;
	}


	public void setQq(String qq) {
		this.qq = qq;
	}


	public String getWechat() {
		return wechat;
	}


	public void setWechat(String wechat) {
		this.wechat = wechat;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getPhoneRange() {
		return phoneRange;
	}


	public void setPhoneRange(String phoneRange) {
		this.phoneRange = phoneRange;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getEmailType() {
		return emailType;
	}


	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}


	public Date getRegisterDate() {
		return registerDate;
	}


	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}


	public String getScore() {
		return score;
	}


	public void setScore(String score) {
		this.score = score;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public Long getMember_level_setting_id() {
		return member_level_setting_id;
	}


	public void setMember_level_setting_id(Long member_level_setting_id) {
		this.member_level_setting_id = member_level_setting_id;
	}


	public Long getSmsBlackListId() {
		return smsBlackListId;
	}


	public void setSmsBlackListId(Long smsBlackListId) {
		this.smsBlackListId = smsBlackListId;
	}


	public String getCurGradeName() {
		return curGradeName;
	}


	public void setCurGradeName(String curGradeName) {
		this.curGradeName = curGradeName;
	}


	public String getMemberInfoCode() {
		return memberInfoCode;
	}


	public void setMemberInfoCode(String buyerNick ,String userId) {
		buyerNick = buyerNick==null?"":buyerNick;
		userId = userId==null?"":userId;
		this.memberInfoCode = buyerNick+"&"+userId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((avgPrice == null) ? 0 : avgPrice.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((buyerId == null) ? 0 : buyerId.hashCode());
		result = prime * result + ((buyerNick == null) ? 0 : buyerNick.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((closeTradeAmount == null) ? 0 : closeTradeAmount.hashCode());
		result = prime * result + ((closeTradeCount == null) ? 0 : closeTradeCount.hashCode());
		result = prime * result + ((curGradeName == null) ? 0 : curGradeName.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((emailType == null) ? 0 : emailType.hashCode());
		result = prime * result + ((gradeId == null) ? 0 : gradeId.hashCode());
		result = prime * result + ((itemCloseCount == null) ? 0 : itemCloseCount.hashCode());
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
		result = prime * result + ((lastTradeTime == null) ? 0 : lastTradeTime.hashCode());
		result = prime * result + ((memberInfoCode == null) ? 0 : memberInfoCode.hashCode());
		result = prime * result + ((member_level_setting_id == null) ? 0 : member_level_setting_id.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((phoneRange == null) ? 0 : phoneRange.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((qq == null) ? 0 : qq.hashCode());
		result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
		result = prime * result + ((relationSource == null) ? 0 : relationSource.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((smsBlackListId == null) ? 0 : smsBlackListId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tradeAmount == null) ? 0 : tradeAmount.hashCode());
		result = prime * result + ((tradeCount == null) ? 0 : tradeCount.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((wechat == null) ? 0 : wechat.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		/*if (!super.equals(obj))
			return false;*/
		if (getClass() != obj.getClass())
			return false;
		MemberInfo other = (MemberInfo) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (avgPrice == null) {
			if (other.avgPrice != null)
				return false;
		} else if (!avgPrice.equals(other.avgPrice))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (buyerId == null) {
			if (other.buyerId != null)
				return false;
		} else if (!buyerId.equals(other.buyerId))
			return false;
		if (buyerNick == null) {
			if (other.buyerNick != null)
				return false;
		} else if (!buyerNick.equals(other.buyerNick))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (closeTradeAmount == null) {
			if (other.closeTradeAmount != null)
				return false;
		} else if (!closeTradeAmount.equals(other.closeTradeAmount))
			return false;
		if (closeTradeCount == null) {
			if (other.closeTradeCount != null)
				return false;
		} else if (!closeTradeCount.equals(other.closeTradeCount))
			return false;
		if (curGradeName == null) {
			if (other.curGradeName != null)
				return false;
		} else if (!curGradeName.equals(other.curGradeName))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (emailType == null) {
			if (other.emailType != null)
				return false;
		} else if (!emailType.equals(other.emailType))
			return false;
		if (gradeId == null) {
			if (other.gradeId != null)
				return false;
		} else if (!gradeId.equals(other.gradeId))
			return false;
		if (itemCloseCount == null) {
			if (other.itemCloseCount != null)
				return false;
		} else if (!itemCloseCount.equals(other.itemCloseCount))
			return false;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		if (lastTradeTime == null) {
			if (other.lastTradeTime != null)
				return false;
		} else if (!lastTradeTime.equals(other.lastTradeTime))
			return false;
		if (memberInfoCode == null) {
			if (other.memberInfoCode != null)
				return false;
		} else if (!memberInfoCode.equals(other.memberInfoCode))
			return false;
		if (member_level_setting_id == null) {
			if (other.member_level_setting_id != null)
				return false;
		} else if (!member_level_setting_id.equals(other.member_level_setting_id))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (phoneRange == null) {
			if (other.phoneRange != null)
				return false;
		} else if (!phoneRange.equals(other.phoneRange))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (qq == null) {
			if (other.qq != null)
				return false;
		} else if (!qq.equals(other.qq))
			return false;
		if (registerDate == null) {
			if (other.registerDate != null)
				return false;
		} else if (!registerDate.equals(other.registerDate))
			return false;
		if (relationSource == null) {
			if (other.relationSource != null)
				return false;
		} else if (!relationSource.equals(other.relationSource))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (smsBlackListId == null) {
			if (other.smsBlackListId != null)
				return false;
		} else if (!smsBlackListId.equals(other.smsBlackListId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tradeAmount == null) {
			if (other.tradeAmount != null)
				return false;
		} else if (!tradeAmount.equals(other.tradeAmount))
			return false;
		if (tradeCount == null) {
			if (other.tradeCount != null)
				return false;
		} else if (!tradeCount.equals(other.tradeCount))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (wechat == null) {
			if (other.wechat != null)
				return false;
		} else if (!wechat.equals(other.wechat))
			return false;
		return true;
	}


	
	
	
}
