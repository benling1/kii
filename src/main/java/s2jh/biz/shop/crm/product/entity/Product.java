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
@Table(name = "CRM_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "产品信息")

public class Product extends BaseNativeEntity{


	private static final long serialVersionUID = -6735586126504718816L;
	
	@MetaData(value="产品ID")
	@Column(name="PRODUCT_ID")
	private String productId;
	
	@MetaData(value="卖家编号")
	@Column(name="USER_ID")
	private Long userId;
	
	@MetaData(value="品类ID")
	@Column(name="COMMODITY_ID")
	private Long commodityId;
	
	@MetaData(value="商品类目ID.必须是叶子类目ID")
	@Column(name="CID")
	private Long cid;

	@MetaData(value="产品名称")
	@Column(name="NAME")
	private String name;
	
	@MetaData(value="产品的非关键属性列表.格式:pid:vid;pid:vid.")
	@Column(name="BINDS")
	private String binds;
	
	@MetaData(value="产品的销售属性列表.格式:pid:vid;pid:vid")
	@Column(name="SALE_PROPS")
	private String saleProps;
	
	@MetaData(value="产品的市场价.单位为元.精确到2位小数;如:200.07")
	@Column(name="PRICE")
	private String price;
	
	@MetaData(value="产品的描述.最大25000个字节")
	@Column(name="describe")
	private String describe;
	
	@MetaData(value="产品的主图片地址.(绝对地址,格式:http://host/image_path)")
	@Column(name="PIC_URL")
	private String picUrl;
	
	@MetaData(value="修改时间.格式:yyyy-mm-dd hh:mm:ss")
	@Column(name="MODIFIED")
	private Date modified;
	
	@MetaData(value="当前状态(0 商家确认 1 屏蔽 3 小二确认 2 未确认 -1 删除)")
	@Column(name="STATUS")
	private String status;
	
	@MetaData(value="产品的级别level")
	@Column(name="LEVEL")
	private String level;
	
	@MetaData(value="产品对应的图片路径")
	@Column(name="PIC_PATH")
	private String picPath;
	
	@MetaData(value="产品的评分次数")
	@Column(name="RATE_NUM")
	private String rateNum;
	
	@MetaData(value="产品的销售量")
	@Column(name="SALE_NUM")
	private String saleNum;
	
	@MetaData(value="产品的店内价格")
	@Column(name="SHOP_PRICE")
	private String shopPrice;
	
	@MetaData(value="产品的标准价格")
	@Column(name="STANDARD_PRICE")
	private String standardPrice;
	
	@MetaData(value="垂直市场,如：3（3C），4（鞋城）")
	@Column(name="VERTICAL_MARKET")
	private String verticalMarket;
	
	@MetaData(value="用户自定义属性,结构：pid1:value1;pid2:value2 例如：“20000:优衣库”，表示“品牌:优衣库”")
	@Column(name="CUSTOMER_PROPS")
	private String customerProps;
	
	@MetaData(value="销售属性值别名。格式为pid1:vid1:alias1;pid1:vid2:alia2。")
	@Column(name="PROPERTYALIAS")
	private String propertyAlias;
	
	@MetaData(value="外部产品ID")
	@Column(name="OUTER_ID")
	private String outerId;
	
	@MetaData(value="创建时间.格式:yyyy-mm-dd hh:mm:ss")
	@Column(name="CREATED")
	private Date created;
	
	@MetaData(value="淘宝标准产品编码")
	@Column(name="TSC")
	private String tsc;
	
	@MetaData(value="商品类目名称")
	@Column(name="CAT_NAME")
	private String catName;
	
	@MetaData(value="产品的关键属性列表.格式：pid:vid;pid:vid")
	@Column(name="PROPS")
	private String props;
	
	@MetaData(value="产品的关键属性字符串列表.比如:品牌:诺基亚;型号:N73(注：属性名称中的冒号':'被转换为：'#cln#'; 分号';'被转换为：'#scln# ')")
	@Column(name="PROPS_STR")
	private String propsStr;
	
	@MetaData(value="产品的非关键属性字符串列表")
	@Column(name="BINDS_STR")
	private String bindsStr;
	
	@MetaData(value="产品的销售属性字符串列表.格式同props_str(注：属性名称中的冒号':'被转换为：'#cln#'; 分号';'被转换为：'#scln# ')")
	@Column(name="SALE_PROPS_STR")
	private String salePropsStr;
	
	@MetaData(value="产品的collect次数（不提供数据，返回0)")
	@Column(name="COLLECT_NUM")
	private String collectNum;
	
	@MetaData(value="产品卖点描述，长度限制20个汉字")
	@Column(name="SELL_PT")
	private String sellPt;
	
	@MetaData(value="标识是否为达尔文体系下的产品 。 如果为空表示是非达尔文体系下的产品 如果cspu:0 表示是达尔文体系下的产品，有cspu正在待小二审核，但不能发布商品。 如果cspu:1 表示是达尔文体系下的产品，且有小二确认的cspu，能发布商品")
	@Column(name="CSPU_FEATURE")
	private String cspuFeature;
	
	@MetaData(value="模板ID")
	@Column(name="TEMPLATE_ID")
	private Long templateId;
	
	@MetaData(value="标识套装产品是否有效，无效的套装产品需要重新发布")
	@Column(name="IS_SUITE_EFFECTIVE")
	private Boolean isSuiteEffective;
	
	@MetaData(value="套装产品关联的子规格,同时该字段不为空标识该产品是套装产品")
	@Column(name="SUITE_ITEMS_STR")
	private String suiteItemsStr;
	
	@MetaData(value="产品条码信息，仅在taobao.products.search接口且商城可用")
	@Column(name="BARCODE_STR")
	private String barcodeStr;
	
	
	
}
