package s2jh.biz.shop.crm.product.entity;


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
@Table(name = "CRM_product_extra_infos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value ="产品扩展信息")
public class ProductExtraInfos extends BaseNativeEntity{

	private static final long serialVersionUID = -5766446546597183249L;
	
	@MetaData(value="产品ID")
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@MetaData(value="产品扩展信息键")
	@Column(name="FIELD_KEY")
	private String fieldKey;
	
	@MetaData(value="产品扩展信息名称")
	@Column(name="FIELD_NAME")
	private String fieldName;
	
	@MetaData(value="产品扩展信息简介")
	@Column(name="FIELD_VALUE")
	private String fieldValue;
	

}
