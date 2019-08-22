package s2jh.biz.shop.crm.buyers.entity;

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
@Table(name = "crm_buyer_group_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "买家用户分组信息")
public class BuyerGroupInfo extends BaseNativeEntity{
	
	private static final long serialVersionUID = -1828272866431352895L;
	
	@MetaData(value="卖家会员ID")
	@Column(name="user_id",nullable=false)
	private String userId;
	
	@MetaData(value="买家用户组编号")
	@Column(name="group_id",nullable=false)
	private Long groupId;
	
	@MetaData(value="买家会员编号")
	@Column(name="buyer_ID",nullable=false)
	private String buyerID;
	
	@MetaData(value="备注信息")
	@Column(name="remarks",nullable=false)
	private String remarks;
	
}
