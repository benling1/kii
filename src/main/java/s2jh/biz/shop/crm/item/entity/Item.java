package s2jh.biz.shop.crm.item.entity;

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


@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "crm_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "商品信息")
public class Item extends BaseNativeEntity {

	private static final long serialVersionUID = -3920083687855728050L;

	@MetaData(value = "商品数字id")
	@Column(name = "num_iid")
	private Long numIid;

	@MetaData(value = "卖家nick")
	@Column(name = "nick")
	private String nick;

	@MetaData(value = "商品上传后的状态。onsale出售中，instock库中")
	@Column(name = "approve_status")
	private String approveStatus;

	@MetaData(value = "商品类目ID")
	@Column(name = "cid")
	private String cid;

	@MetaData(value = "橱窗推荐,true/false")
	@Column(name = "has_showcase")
	private String hasShowcase;

	@MetaData(value = "支持会员打折,true/false")
	@Column(name = "has_discount")
	private String hasDiscount;

	@MetaData(value = "发布时间")
	@Column(name = "created")
	private Date created;

	@MetaData(value = "商品修改时间")
	@Column(name = "modified")
	private Date modified;

	@MetaData(value = "商品所属的店铺内卖家自定义类目列表")
	@Column(name = "seller_cids")
	private String sellerCids;

	@MetaData(value = "商品属性 格式：pid:vid;pid:vid")
	@Column(name = "props")
	private String props;

	@MetaData(value = "用户自行输入的类目属性ID串。结构：'pid1,pid2,pid3'，如：'20000'（表示品牌） 注：通常一个类目下用户可输入的关键属性不超过1个。")
	@Column(name = "input_pids")
	private String inputPids;

	@MetaData(value = "用户自行输入的子属性名和属性值，结构:'父属性值;一级子属性名;一级子属性值;二级子属性名;自定义输入值,....',如：'耐克;耐克系列;科比系列;科比系列;2K5'，input_str需要与input_pids一一对应，注：通常一个类目下用户可输入的关键属性不超过1个。所有属性别名加起来不能超过 3999字节。")
	@Column(name = "input_str")
	private String inputStr;

	@MetaData(value = "商品数量")
	@Column(name = "num")
	private Integer num;

	@MetaData(value = "上架时间")
	@Column(name = "list_time")
	private Date listTime;

	@MetaData(value = "下架时间")
	@Column(name = "delist_time")
	private Date delistTime;

	@MetaData(value = "商品新旧程度(全新:new，闲置:unused，二手：second)")
	@Column(name = "stuff_status")
	private String stuffStatus;

	@MetaData(value = "邮政编码")
	@Column(name = "zip")
	private String zip;

	@MetaData(value = "详细地址，最大256个字节（128个中文）")
	@Column(name = "address")
	private String address;

	@MetaData(value = "所在城市（中文名称）")
	@Column(name = "city")
	private String city;

	@MetaData(value = "所在省份（中文名称）")
	@Column(name = "state")
	private String state;

	@MetaData(value = "国家名称")
	@Column(name = "country")
	private String country;

	@MetaData(value = "区/县（只适用于物流API）")
	@Column(name = "district")
	private String district;

	@MetaData(value = "运费承担方式,seller（卖家承担），buyer（买家承担）")
	@Column(name = "freight_payer")
	private String freightPayer;

	@MetaData(value = "图片路径")
	@Column(name = "url")
	private String url;

	@MetaData(value = "是否是3D淘宝的商品")
	@Column(name = "is_3D")
	private Boolean is3D;

	@MetaData(value = "商品所属卖家的信用等级数，1表示1心，2表示2心……，只有调用商品搜索:taobao.items.get和taobao.items.search的时候才能返回")
	@Column(name = "score")
	private Integer score;

	@MetaData(value = "是否承诺退换货服务!")
	@Column(name = "sell_promise")
	private Boolean sellPromise;
	
	@MetaData(value = "商品标题,不能超过60字节")
	@Column(name = "title")
	private String title;
	
	@MetaData(value = "商品类型(fixed:一口价;auction:拍卖)注：取消团购")
	@Column(name = "type")
	private String type;
	
	@MetaData(value = "商品描述, 字数要大于5个字符，小于25000个字符")
	@Column(name = "item_desc")
	private String itemDesc;
	
	@MetaData(value = "sku的id")
	@Column(name = "sku_id")
	private Long sku_id;

	@MetaData(value = "sku的销售属性组合字符串（颜色，大小，等等，可通过类目API获取某类目下的销售属性）,格式是p1:v1;p2:v2")
	@Column(name = "properties")
	private String properties;

	@MetaData(value = "属于这个sku的商品的数量")
	@Column(name = "quantity")
	private Integer quantity;

	@MetaData(value = "属于这个sku的商品的价格 取值范围:0-100000000;精确到2位小数;单位:元。如:200.07，表示:200元7分。")
	@Column(name = "sku_price")
	private String sku_price;

	@MetaData(value = "sku创建日期 时间格式：yyyy-MM-dd HH:mm:ss")
	@Column(name = "sku_created")
	private String sku_created;

	@MetaData(value = "sku最后修改日期 时间格式：yyyy-MM-dd HH:mm:ss")
	@Column(name = "sku_modified")
	private String sku_modified;

	@MetaData(value = "sku状态。 normal:正常 ；delete:删除")
	@Column(name = "sku_status")
	private String sku_status;

	@MetaData(value = "sku所对应的销售属性的中文名字串，格式如：pid1:vid1:pid_name1:vid_name1;pid2:vid2:pid_name2:vid_name2……")
	@Column(name = "sku_properties_name")
	private String sku_properties_name;

	@MetaData(value = "表示SKu上的产品规格信息")
	@Column(name = "sku_spec_id")
	private Integer sku_spec_id;

	@MetaData(value = "商品在付款减库存的状态下，该sku上未付款的订单数量")
	@Column(name = "sku_with_hold_quantity")
	private Integer withHoldQuantity;

	@MetaData(value = "sku级别发货时间")
	@Column(name = "sku_delivery_time")
	private String sku_delivery_time;

	@MetaData(value = "基础色数据")
	@Column(name = "change_prop")
	private String changeProp;

	@MetaData(value = "商品属性名称。标识着props内容里面的pid和vid所对应的名称。格式为：pid1:vid1:pid_name1:vid_name1;pid2:vid2:pid_name2:vid_name2……")
	@Column(name = "props_name")
	private String propsName;

	@MetaData(value = "消保类型，多个类型以,分割。可取以下值：2：假一赔三；4：7天无理由退换货")
	@Column(name = "promoted_service")
	private String promotedService;

	@MetaData(value = "是否24小时闪电发货")
	@Column(name = "is_lightning_consignment")
	private Boolean isLightningConsignment;

	@MetaData(value = "非分销商品：0，代销：1，经销：2")
	@Column(name = "is_fenxiao")
	private Integer isFenxiao;
	
	@MetaData(value = "商品缩写")
	@Column(name = "subtitle")
	private String subtitle;

	@Override
	public String toString() {
		return "Item [numIid=" + numIid + ", nick=" + nick + ", approveStatus="
				+ approveStatus + ", cid=" + cid + ", hasShowcase="
				+ hasShowcase + ", hasDiscount=" + hasDiscount + ", created="
				+ created + ", modified=" + modified + ", sellerCids="
				+ sellerCids + ", props=" + props + ", inputPids=" + inputPids
				+ ", inputStr=" + inputStr + ", num=" + num + ", listTime="
				+ listTime + ", delistTime=" + delistTime + ", stuffStatus="
				+ stuffStatus + ", zip=" + zip + ", address=" + address
				+ ", city=" + city + ", state=" + state + ", country="
				+ country + ", district=" + district + ", freightPayer="
				+ freightPayer + ", url=" + url + ", is3D=" + is3D + ", score="
				+ score + ", sellPromise=" + sellPromise + ", title=" + title
				+ ", type=" + type + ", itemDesc=" + itemDesc + ", sku_id="
				+ sku_id + ", properties=" + properties + ", quantity="
				+ quantity + ", sku_price=" + sku_price + ", sku_created="
				+ sku_created + ", sku_modified=" + sku_modified
				+ ", sku_status=" + sku_status + ", sku_properties_name="
				+ sku_properties_name + ", sku_spec_id=" + sku_spec_id
				+ ", withHoldQuantity=" + withHoldQuantity
				+ ", sku_delivery_time=" + sku_delivery_time + ", changeProp="
				+ changeProp + ", propsName=" + propsName
				+ ", promotedService=" + promotedService
				+ ", isLightningConsignment=" + isLightningConsignment
				+ ", isFenxiao=" + isFenxiao + ", auctionPoint=" + auctionPoint
				+ ", propertyAlias=" + propertyAlias + ", templateId="
				+ templateId + ", afterSaleId=" + afterSaleId + ", isXinpin="
				+ isXinpin + ", subStock=" + subStock + ", features="
				+ features + ", itemWeight=" + itemWeight + ", itemSize="
				+ itemSize + ", with_hold_quantity=" + with_hold_quantity
				+ ", sellPoint=" + sellPoint + ", validThru=" + validThru
				+ ", outerId=" + outerId + ", autofill=" + autofill
				+ ", descModules=" + descModules + ", customMadeTypeId="
				+ customMadeTypeId + ", wirelessDesc=" + wirelessDesc
				+ ", barcode=" + barcode + ", newprepay=" + newprepay
				+ ", price=" + price + ", postFee=" + postFee + ", expressFee="
				+ expressFee + ", emsFee=" + emsFee + ", globalStockType="
				+ globalStockType + ", globalStockCountry="
				+ globalStockCountry + ", largeScreenImageUrl="
				+ largeScreenImageUrl + "]";
	}

	@MetaData(value = "商品的积分返点比例。如:5,表示:返点比例0.5%商品的积分返点比例。如:5,表示:返点比例0.5%")
	@Column(name = "auction_point")
	private Integer auctionPoint;

	@MetaData(value = "属性值别名,比如颜色的自定义名称")
	@Column(name = "property_alias")
	private String propertyAlias;

	@MetaData(value = "页面模板id")
	@Column(name = "template_id")
	private String templateId;

	@MetaData(value = "售后服务ID,该字段仅在taobao.item.get接口中返回")
	@Column(name = "after_sale_id")
	private Long afterSaleId;

	@MetaData(value = "标示商品是否为新品。值含义：true-是，false-否。")
	@Column(name = "is_xinpin")
	private Boolean isXinpin;

	@MetaData(value = "商品是否支持拍下减库存:1支持;2取消支持(付款减库存);0(默认)不更改 集市卖家默认拍下减库存; 商城卖家默认付款减库存")
	@Column(name = "sub_stock")
	private Integer subStock;

	@MetaData(value = "宝贝特征值，只有在Top支持的特征值才能保存到宝贝上")
	@Column(name = "features")
	private String features;

	@MetaData(value = "商品的重量，用于按重量计费的运费模板。注意：单位为kg")
	@Column(name = "item_weight")
	private String itemWeight;

	@MetaData(value = "表示商品的体积，用于按体积计费的运费模板。该值的单位为立方米（m3）。该值支持两种格式的设置：格式1：bulk:3,单位为立方米(m3),表示直接设置为商品的体积。格式2：weight:10;breadth:10;height:10，单位为米（m）")
	@Column(name = "item_size")
	private String itemSize;

	@MetaData(value = "预扣库存，即付款减库存的商品现在有多少处于未付款状态的订单")
	@Column(name = "with_hold_quantity")
	private Integer with_hold_quantity;

	@MetaData(value = "商品卖点信息，天猫商家使用字段，最长150个字符")
	@Column(name = "sell_point")
	private String sellPoint;

	@MetaData(value = "有效期,7或者14（默认是7天）")
	@Column(name = "valid_thru")
	private Integer validThru;

	@MetaData(value = "商家外部编码(可与商家外部系统对接)。需要授权才能获取。")
	@Column(name = "outer_id")
	private String outerId;

	@MetaData(value = "代充商品类型。在代充商品的类目下，不传表示不标记商品类型（交易搜索中就不能通过标记搜到相关的交易了）。可选类型： no_mark(不做类型标记) time_card(点卡软件代充) fee_card(话费软件代充)")
	@Column(name = "auto_fill")
	private String autofill;

	@MetaData(value = "商品描述模块化，模块列表，由List转化成jsonArray存入，后端逻辑验证通过，拼装成模块内容+锚点导航后存入desc中。数据结构具体参见Item_Desc_Module")
	@Column(name = "desc_modules")
	private String descModules;

	@MetaData(value = "定制工具Id")
	@Column(name = "custom_made_type_id")
	private String customMadeTypeId;

	@MetaData(value = "无线的宝贝描述")
	@Column(name = "wireless_desc")
	private String wirelessDesc;

	@MetaData(value = "商品级别的条形码")
	@Column(name = "barcode")
	private String barcode;

	@MetaData(value = "是否为新消保法中的7天无理由退货")
	@Column(name = "newprepay")
	private String newprepay;

	@MetaData(value = "商品价格，格式：5.00；单位：元；精确到：分")
	@Column(name = "price")
	private String price;

	@MetaData(value = "平邮费用,格式：5.00；单位：元；精确到：分")
	@Column(name = "post_fee")
	private String postFee;

	@MetaData(value = "快递费用,格式：5.00；单位：元；精确到：分")
	@Column(name = "express_fee")
	private String expressFee;

	@MetaData(value = "ems费用,格式：5.00；单位：元；精确到：分")
	@Column(name = "ems_fee")
	private String emsFee;

	@MetaData(value = "全球购商品采购地信息（库存类型），有两种库存类型：现货和代购;参数值为1时代表现货，值为2时代表代购")
	@Column(name = "global_stock_type")
	private String globalStockType;

	@MetaData(value = "全球购商品采购地信息（地区/国家），代表全球购商品的产地信息。")
	@Column(name = "global_stock_country")
	private String globalStockCountry;

	@MetaData(value = "门店大屏图")
	@Column(name = "large_screen_image_url")
	private String largeScreenImageUrl;
	
	private Integer itemCount;//满足查询条件的商品数目，作为分页的依据

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getHasShowcase() {
		return hasShowcase;
	}

	public void setHasShowcase(String hasShowcase) {
		this.hasShowcase = hasShowcase;
	}

	public String getHasDiscount() {
		return hasDiscount;
	}

	public void setHasDiscount(String hasDiscount) {
		this.hasDiscount = hasDiscount;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getSellerCids() {
		return sellerCids;
	}

	public void setSellerCids(String sellerCids) {
		this.sellerCids = sellerCids;
	}

	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	public String getInputPids() {
		return inputPids;
	}

	public void setInputPids(String inputPids) {
		this.inputPids = inputPids;
	}

	public String getInputStr() {
		return inputStr;
	}

	public void setInputStr(String inputStr) {
		this.inputStr = inputStr;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getListTime() {
		return listTime;
	}

	public void setListTime(Date listTime) {
		this.listTime = listTime;
	}

	public Date getDelistTime() {
		return delistTime;
	}

	public void setDelistTime(Date delistTime) {
		this.delistTime = delistTime;
	}

	public String getStuffStatus() {
		return stuffStatus;
	}

	public void setStuffStatus(String stuffStatus) {
		this.stuffStatus = stuffStatus;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getFreightPayer() {
		return freightPayer;
	}

	public void setFreightPayer(String freightPayer) {
		this.freightPayer = freightPayer;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIs3D() {
		return is3D;
	}

	public void setIs3D(Boolean is3d) {
		is3D = is3d;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getSellPromise() {
		return sellPromise;
	}

	public void setSellPromise(Boolean sellPromise) {
		this.sellPromise = sellPromise;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItem_desc() {
		return itemDesc;
	}

	public void setItem_desc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Long getSku_id() {
		return sku_id;
	}

	public void setSku_id(Long sku_id) {
		this.sku_id = sku_id;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getSku_price() {
		return sku_price;
	}

	public void setSku_price(String sku_price) {
		this.sku_price = sku_price;
	}

	public String getSku_created() {
		return sku_created;
	}

	public void setSku_created(String sku_created) {
		this.sku_created = sku_created;
	}

	public String getSku_modified() {
		return sku_modified;
	}

	public void setSku_modified(String sku_modified) {
		this.sku_modified = sku_modified;
	}

	public String getSku_status() {
		return sku_status;
	}

	public void setSku_status(String sku_status) {
		this.sku_status = sku_status;
	}

	public String getSku_properties_name() {
		return sku_properties_name;
	}

	public void setSku_properties_name(String sku_properties_name) {
		this.sku_properties_name = sku_properties_name;
	}

	public Integer getSku_spec_id() {
		return sku_spec_id;
	}

	public void setSku_spec_id(Integer sku_spec_id) {
		this.sku_spec_id = sku_spec_id;
	}

	public Integer getWithHoldQuantity() {
		return withHoldQuantity;
	}

	public void setWithHoldQuantity(Integer withHoldQuantity) {
		this.withHoldQuantity = withHoldQuantity;
	}

	public String getSku_delivery_time() {
		return sku_delivery_time;
	}

	public void setSku_delivery_time(String sku_delivery_time) {
		this.sku_delivery_time = sku_delivery_time;
	}

	public String getChangeProp() {
		return changeProp;
	}

	public void setChangeProp(String changeProp) {
		this.changeProp = changeProp;
	}

	public String getPropsName() {
		return propsName;
	}

	public void setPropsName(String propsName) {
		this.propsName = propsName;
	}

	public String getPromotedService() {
		return promotedService;
	}

	public void setPromotedService(String promotedService) {
		this.promotedService = promotedService;
	}

	public Boolean getIsLightningConsignment() {
		return isLightningConsignment;
	}

	public void setIsLightningConsignment(Boolean isLightningConsignment) {
		this.isLightningConsignment = isLightningConsignment;
	}

	public Integer getIsFenxiao() {
		return isFenxiao;
	}

	public void setIsFenxiao(Integer isFenxiao) {
		this.isFenxiao = isFenxiao;
	}

	public Integer getAuctionPoint() {
		return auctionPoint;
	}

	public void setAuctionPoint(Integer auctionPoint) {
		this.auctionPoint = auctionPoint;
	}

	public String getPropertyAlias() {
		return propertyAlias;
	}

	public void setPropertyAlias(String propertyAlias) {
		this.propertyAlias = propertyAlias;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Long getAfterSaleId() {
		return afterSaleId;
	}

	public void setAfterSaleId(Long afterSaleId) {
		this.afterSaleId = afterSaleId;
	}

	public Boolean getIsXinpin() {
		return isXinpin;
	}

	public void setIsXinpin(Boolean isXinpin) {
		this.isXinpin = isXinpin;
	}

	public Integer getSubStock() {
		return subStock;
	}

	public void setSubStock(Integer subStock) {
		this.subStock = subStock;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(String itemWeight) {
		this.itemWeight = itemWeight;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public Integer getWith_hold_quantity() {
		return with_hold_quantity;
	}

	public void setWith_hold_quantity(Integer with_hold_quantity) {
		this.with_hold_quantity = with_hold_quantity;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Integer getValidThru() {
		return validThru;
	}

	public void setValidThru(Integer validThru) {
		this.validThru = validThru;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getAutofill() {
		return autofill;
	}

	public void setAutofill(String autofill) {
		this.autofill = autofill;
	}

	public String getDescModules() {
		return descModules;
	}

	public void setDescModules(String descModules) {
		this.descModules = descModules;
	}

	public String getCustomMadeTypeId() {
		return customMadeTypeId;
	}

	public void setCustomMadeTypeId(String customMadeTypeId) {
		this.customMadeTypeId = customMadeTypeId;
	}

	public String getWirelessDesc() {
		return wirelessDesc;
	}

	public void setWirelessDesc(String wirelessDesc) {
		this.wirelessDesc = wirelessDesc;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getNewprepay() {
		return newprepay;
	}

	public void setNewprepay(String newprepay) {
		this.newprepay = newprepay;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPostFee() {
		return postFee;
	}

	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}

	public String getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(String expressFee) {
		this.expressFee = expressFee;
	}

	public String getEmsFee() {
		return emsFee;
	}

	public void setEmsFee(String emsFee) {
		this.emsFee = emsFee;
	}

	public String getGlobalStockType() {
		return globalStockType;
	}

	public void setGlobalStockType(String globalStockType) {
		this.globalStockType = globalStockType;
	}

	public String getGlobalStockCountry() {
		return globalStockCountry;
	}

	public void setGlobalStockCountry(String globalStockCountry) {
		this.globalStockCountry = globalStockCountry;
	}

	public String getLargeScreenImageUrl() {
		return largeScreenImageUrl;
	}

	public void setLargeScreenImageUrl(String largeScreenImageUrl) {
		this.largeScreenImageUrl = largeScreenImageUrl;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	

	/*
	 * @MetaData(value="商品ID")
	 * 
	 * @Column(name="COMMODITY_ID") private String commodityId;
	 * 
	 * 
	 * @MetaData(value="淘宝ID")
	 * 
	 * @Column(name="USER_ID") private String userId;
	 * 
	 * @MetaData(value="父ID")
	 * 
	 * @Column(name="PARENT_ID") private Long parentId;
	 * 
	 * @MetaData(value="品牌ID")
	 * 
	 * @Column(name="BRAND_ID") private String brandId;
	 * 
	 * @MetaData(value="淘宝昵称")
	 * 
	 * @Column(name="USER_NICK") private String userNick;
	 * 
	 * @MetaData(value="商品名称")
	 * 
	 * @Column(name="NAME") private String name;
	 * 
	 * @MetaData(value="前台商品名称")
	 * 
	 * @Column(name="TITLE") private String title;
	 * 
	 * @MetaData(value="商家编码")
	 * 
	 * @Column(name="ITEM_CODE") private String itemCode;
	 * 
	 * @MetaData(value="是否sku")
	 * 
	 * @Column(name="IS_SKU") private Boolean isSku = Boolean.FALSE;
	 * 
	 * @MetaData(value="标记")
	 * 
	 * @Column(name="FLAG") private String flag;
	 * 
	 * @MetaData(value="商品类型 1-普通类型 2-组合商品 2-分销商")
	 * 
	 * @Column(name="TYPE") private String type;
	 * 
	 * @MetaData(value="商品备注")
	 * 
	 * @Column(name="REMARK") private String remark;
	 * 
	 * @MetaData(value="状态 1-有效 2-锁定")
	 * 
	 * @Column(name="STATUS") private String status;
	 * 
	 * @MetaData(value="发布版本号")
	 * 
	 * @Column(name="PUBLISH_VERSION") private Long publishVersion;
	 * 
	 * @MetaData(value="创建人")
	 * 
	 * @Column(name="CREATOR") private String creator;
	 * 
	 * @MetaData(value="创建时间")
	 * 
	 * @Column(name="GMT_CREATE") private Date gmtCreate;
	 * 
	 * @MetaData(value="最后修改人")
	 * 
	 * @Column(name="LAST_MODIFIER") private String lastModifier;
	 * 
	 * @MetaData(value="修改日期")
	 * 
	 * @Column(name="GMT_MODIFIED") private Date gmtModified;
	 * 
	 * @MetaData(value="属性")
	 * 
	 * @Column(name="PROPERTIES") private String properties;
	 * 
	 * @MetaData(value="是否易碎")
	 * 
	 * @Column(name="IS_FRIABLE") private Boolean isFriable;
	 * 
	 * @MetaData(value="是否危险品")
	 * 
	 * @Column(name="IS_DANGEROUS") private Boolean isDangerous = Boolean.FALSE;
	 * 
	 * @MetaData(value="颜色")
	 * 
	 * @Column(name="COLOR") private String color;
	 * 
	 * @MetaData(value="重量")
	 * 
	 * @Column(name="WEIGHT") private Long weight;
	 * 
	 * @MetaData(value="长度")
	 * 
	 * @Column(name="LENGTH") private Long length;
	 * 
	 * @MetaData(value="宽度")
	 * 
	 * @Column(name="WIDTH") private Long width;
	 * 
	 * @MetaData(value="高度")
	 * 
	 * @Column(name="HEIGHT") private Long height;
	 * 
	 * @MetaData(value="立方mm")
	 * 
	 * @Column(name="VOLUME") private Long volume;
	 * 
	 * @MetaData(value="货类")
	 * 
	 * @Column(name="GOODS_CAT") private String goodsCat;
	 * 
	 * @MetaData(value="计价货类")
	 * 
	 * @Column(name="PRICING_CAT") private String pricingCat;
	 * 
	 * @MetaData(value="包装材料")
	 * 
	 * @Column(name="PACKAGE_MATERIAL") private String packageMaterial;
	 * 
	 * @MetaData(value="价格")
	 * 
	 * @Column(name="PRICE") private Long price;
	 */

}
