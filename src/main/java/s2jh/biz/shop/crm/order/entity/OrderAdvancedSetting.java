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
@Table(name = "CRM_ORDER_ADVANCED_SETTING")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "下单关怀-高级条件设置")
public class OrderAdvancedSetting extends BaseNativeEntity{
	
	private static final long serialVersionUID = -8208951022817898679L;
	
	@MetaData(value="卖家用户ID")
	@Column(name="USER_ID")
	private String userId;
	
	@MetaData(value="买家用户ID")
	@Column(name="MEMEB_INFO_ID")
	private Long memebInfoId;
	
	@MetaData(value="商品ID")
	@Column(name="ITEM_ID")
	private String itemId;
	
	@MetaData(value="地区筛选  0--默认全部    --省份汉字全称用,隔开")
	@Column(name="LOCALITY")
	private String locality;
	
	@MetaData(value="卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记")
	@Column(name="VENDOR_MARK")
	private String vendormark;
	
	@MetaData(value="屏蔽卖家标记小旗颜色  1-红 2-黄 3-绿 4-蓝 5-粉")
	@Column(name="FLAGCOLOR")
	private String flagcolor;
	
	@MetaData(value="订单来源 0-不限 1-Pc端 2-移动端")
	@Column(name="ORDER_SOURCE")
	private String orderSource;
	
	@MetaData(value="会员等级 0-不限 1-首次到店会员2-店铺会员 3-普通会员 4-高级会员 5-VIP会员 6-至尊VIP会员")
	@Column(name="MEMBER_LEVEL")
	private String memberLevel;
	
	@MetaData(value="商品选择 0-全部商品 1-指定商品 2-排除指定商品")
	@Column(name="PRODUCE_SELECT")
	private String productSelect;
	
	@MetaData(value="设置类型 1-下单关怀 2-常规催付 3-二次催付 4-聚划算催付 5-预收催付 6-发货提醒 7-到达同城提醒 8-派件提醒 9-签收提醒 10-疑难件提醒 11-延时发货提醒 12-宝贝关怀 "
			+ "13-付款关怀 14-回款提醒 15-退款关怀 16-自动评价 17-批量评价 18-评价记录 19-中差管理 20-中差评监控 21-中差评安抚 22-中差评统计 23-中差评原因 24中差评原因设置 "
			+ "25-中差评原因分析 26-手动订单提醒 27-优秀催付案例 28-效果统计 29-买家申请退款 30-退款成功 31-等待退货 32-拒绝退款")
	@Column(name="SETTING_TYPE")
	private String settingType;
	
	@MetaData(value="是否开启或关闭状态 0--开启 1--关闭")
	@Column(name="status")
	private String status;
	
}
