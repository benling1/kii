package s2jh.biz.shop.crm.order.entity;

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
@Table(name = "crm_promotion_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "优惠详情")
public class PromotionDetails extends BaseNativeEntity{
	
	private static final long serialVersionUID = 8108404090161717285L;

	@MetaData(value="子订单ID")
	@Column(name="OID",nullable=false)
	private String oid;
	
	@MetaData(value="优惠信息的名称")
	@Column(name="promotion_name",nullable=false)
	private String promotionName;
	
	@MetaData(value="优惠金额")
	@Column(name="discount_fee",nullable=false)
	private String discountFee;
	
	@MetaData(value="赠送商品的名称")
	@Column(name="gift_item_name",nullable=false)
	private String giftItemName;
	
	@MetaData(value="赠送商品的编号")
	@Column(name="gift_item_id",nullable=false)
	private String giftItemId;
	
	@MetaData(value="赠送商品的数量")
	@Column(name="gift_item_num",nullable=false)
	private String giftItemNum;
	
	@MetaData(value="优惠活动的描述")
	@Column(name="promotion_desc",nullable=false)
	private String promotionDesc;
	
	@MetaData(value="优惠活动的编号")
	@Column(name="promotion_id",nullable=false)
	private String promotionId;
}
