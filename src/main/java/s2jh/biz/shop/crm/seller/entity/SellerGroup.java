package s2jh.biz.shop.crm.seller.entity;

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

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_GROUPS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "卖家分组")
public class SellerGroup extends BaseNativeEntity{

	private static final long serialVersionUID = 3994608067073815545L;
	
	@MetaData(value="分组规则ID")
	@Column(name="RULE_ID")
	private Long rule_id;
	
	@MetaData(value="分组编号")
	@Column(name="GROUP_ID")
	private Long groupId;
	
	@MetaData(value="分组名称")
	@Column(name="GROUP_NAME")
	private String groupName;
	
	@MetaData(value="分组创建时间")
	@Column(name="GROUP_CREATE")
	private Date groupCreate;
	
	@MetaData(value="分组修改时间")
	@Column(name="GROUP_MODIFY")
	private Date groupModify;
	
	@MetaData(value="分组状态 1-正常 2-异常")
	@Column(name="STATUS")
	private String status;
	
	@MetaData(value="分组所拥有的会员")
	@Column(name="MEMBER_COUNT")
	private Integer memberCount;
	
	@MetaData(value="分组类型（1：默认分组，2：用户添加分组）")
	@Column(name="memberType")
	private String memberType;
	
	@MetaData(value="卖家编号")
	@Column(name="userId")
	private String userId;
	
	@MetaData(value="说明")
	@Column(name="remark")
	private String remark;

}
