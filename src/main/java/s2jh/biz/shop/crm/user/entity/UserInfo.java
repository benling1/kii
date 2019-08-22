package s2jh.biz.shop.crm.user.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "CRM_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "用户登录表")
public class UserInfo extends BaseNativeEntity {

	private static final long serialVersionUID = -6963381365184549852L;

	@MetaData(value = "淘宝账户对应Id")
	@Column(name = "taobao_user_id")
	private String taobaoUserId;

	@MetaData(value = "淘宝子账户对应Id")
	@Column(name = "sub_taobao_user_id")
	private String subtaobaoUserId;

	@MetaData(value = "创建时间")
	@Column(name = "Create_Time")
	private Date createTime;

	@MetaData(value = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@MetaData(value = "最后一次登录时间")
	@Column(name = "last_login_date")
	private Date lastLoginDate;

	@MetaData(value = "账户过期时间")
	@Column(name = "expirationTime")
	private Date expirationTime;

	@MetaData(value = "用户手机号")
	@Column(name = "mobile")
	private String mobile;

	@MetaData(value = "top分配给用户的key")
	@Column(name = "appkey")
	private String appkey;

	@MetaData(value = "淘宝账号(昵称)")
	@Column(name = "taobao_user_nick", length = 200)
	private String taobaoUserNick;

	@MetaData(value = "淘宝子账号")
	@Column(name = "sub_taobao_user_nick")
	private String subtaobaoUserNick;

	@MetaData(value = "修改时间")
	@Column(name = "modify_time")
	private Date modifyTime;

	@MetaData(value = "邮件剩余数量")
	@Column(name = "email_num")
	private Integer emailNum;

	@MetaData(value = "卖家用户的sessionKey")
	@Column(name = "access_token")
	private String access_token;
	
	
	@MetaData(value = "卖家用户的QQ号")
	@Column(name = "qqNum")
	private String qqNum;
	
	@MetaData(value = "店铺名称（短信签名）")
	@Column(name = "shopName")
	private String shopName;
	
	
	@MetaData(value = "token过期时间")
	@Column(name = "expires_in")
	private String expires_in;
	
	@MetaData(value = "黑名单状态")
	@Column(name = "blackStatus")
	private String blackStatus;
	
	@MetaData(value = "用户等级，分为20个级别")
	@Column(name = "level")
	private Long level;
	
	/**
	 * 短信余额
	 */
	@Transient
    private Long userAccountSms;

	/**
     * 是否赠过送客户500条短信
     * true:是
     * false:没有/且用户不希望在展示首页小红包
     * null:没有
     * */
    @MetaData(value = "是否赠送500条短信")
    @Column(name = "has_provide")
    private Boolean hasProvide;
}
