package s2jh.biz.shop.crm.goods.entity;
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
@Table(name = "crm_Grouped_Goods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "已分组的商品")
public class GroupedGoods extends BaseNativeEntity {

	private static final long serialVersionUID = 1881391702244522380L;

	@MetaData(value="卖家ID")
	@Column(name="USERID")
	private String userId;
	
	@MetaData(value="组ID")
	@Column(name="GROUP_ID")
	private Long groupId;
	
	@MetaData(value = "商品数字id")
	@Column(name = "num_iid")
	private Long numIid;
	
	@MetaData(value = "宝贝图片")
	@Column(name = "url")
	private String url;
	
	@MetaData(value = "宝贝名称")
	@Column(name = "title")
	private String title;
	
	@MetaData(value = "商品价格，格式：5.00；单位：元；精确到：分")
	@Column(name = "price")
	private String price;
	
	@MetaData(value = "商品上传后的状态。onsale出售中--上架，instock库中--下架")
	@Column(name = "approve_status")
	private String approveStatus;
	
	@MetaData(value="添加时间")
	@Column(name="CTIME")
	private Date cTime;
	
}
