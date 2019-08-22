package s2jh.biz.shop.crm.product.entity;

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

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_product_imgs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "产品的子图片")
public class ProductImgs extends BaseNativeEntity{

	private static final long serialVersionUID = -3996932823258331120L;
	
	@MetaData(value="图片所属产品的ID")
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@MetaData(value="图片地址.(绝对地址,格式:http://host/image_path)")
	@Column(name="URL")
	private String url;
	
	@MetaData(value="图片序号。产品里的图片展示顺序，数据越小越靠前。要求是正整数。")
	@Column(name="POSITION")
	private String position;
	
	@MetaData(value="添加时间.格式:yyyy-mm-dd hh:mm:ss")
	@Column(name="CREATED")
	private Date created;
	
	@MetaData(value="修改时间.格式:yyyy-mm-dd hh:mm:ss")
	@Column(name="MODIFIED")
	private Date modified;

}
