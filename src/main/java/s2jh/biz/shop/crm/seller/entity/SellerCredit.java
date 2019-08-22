package s2jh.biz.shop.crm.seller.entity;

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
@Table(name = "CRM_SELLER_CREDIT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "卖家信用")
public class SellerCredit extends BaseNativeEntity{

	private static final long serialVersionUID = -8323593139222192323L;
	
	@MetaData(value="卖家编号")
	@Column(name="USER_ID",nullable=false)
	private Long userId;
	
	@MetaData(value="信用等级")
	@Column(name="LEVEL")
	private Integer level;
	
	@MetaData(value="信用总分")
	@Column(name="SCORE")
	private Integer score;
	
	@MetaData(value="评价总条数")
	@Column(name="TOTAL_NUM")
	private Integer totalNum;
	
	@MetaData(value="收到的好评总条数")
	@Column(name="GOOD_NUM")
	private Integer goodNum;

}
