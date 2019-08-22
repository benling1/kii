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
@Table(name = "CRM_product_prop_imgs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "产品的属性图片")
public class ProductPropImgs extends BaseNativeEntity{

	private static final long serialVersionUID = -5874666138925808597L;
	
	@MetaData(value="图片所属产品的ID")
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@MetaData(value="属性串(pid:vid),目前只有颜色属性.如:颜色:红色表示为　1627207:28326")
	@Column(name="PROPS")
	private String props;
	
	@MetaData(value="图片地址.(绝对地址,格式:http://host/image_path)")
	@Column(name="URL")
	private String url;
	
	@MetaData(value="图片序号。产品里的图片展示顺序，数据越小越靠前。要求是正整数。")
	@Column(name="POSITION")
	private String position;
	

}
