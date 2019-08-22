/**
 * 
 */
package s2jh.biz.shop.crm.user.entity;

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
import lombok.experimental.Accessors;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** 
 * @Title: 用户信息登录表
 * @date 2017年4月20日--上午10:46:10 
 * @param     设定文件 
 * @return 返回类型 
 * @throws 
 */

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_UserLoginInfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "用户信息登录表")
public class UserLoginInfo extends BaseNativeEntity {
	
	private static final long serialVersionUID = 1L;

	@MetaData(value = "卖家用户名")
	@Column(name = "sellerNick")
	private String sellerNick;
	
	@MetaData(value = "登录时间")
	@Column(name = "loginTime")
	private Date loginTime;
	
	@MetaData(value = "登录IP地址")
	@Column(name = "IpAddress")
	private String IpAddress;
	
}
