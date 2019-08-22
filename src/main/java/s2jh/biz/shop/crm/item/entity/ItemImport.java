package s2jh.biz.shop.crm.item.entity;

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

@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Getter
@Setter
@Table(name = "crm_item_import")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "订单导入拆分商品信息")
public class ItemImport extends BaseNativeEntity {
	private static final long serialVersionUID = 6498313918853359597L;
	
	@MetaData(value = "卖家nick")
	@Column(name = "nick")
	private String nick;
	
	@MetaData(value = "商品数字id")
	@Column(name = "num_iid")
	private Long numIid;
	
	@MetaData(value = "商品标题,不能超过60字节")
	@Column(name = "title")
	private String title;
	
	@MetaData(value = "商品价格，格式：5.00；单位：元；精确到：分")
	@Column(name = "price")
	private String price;
	
	@MetaData(value = "商品上传后的状态。onsale出售中，instock库中，import订单导入创建")
	@Column(name = "approve_status")
	private String approveStatus;

	@MetaData(value = "图片路径")
	@Column(name = "url")
	private String url;
	
	@Transient
    private String itemId;
}
