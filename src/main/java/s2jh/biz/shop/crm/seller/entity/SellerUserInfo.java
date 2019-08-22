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
@Table(name = "CRM_SELLER_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "卖家用户信息")
public class SellerUserInfo extends BaseNativeEntity{

	private static final long serialVersionUID = 1809230251335202438L;
	
	@MetaData(value="用户ID")
	@Column(name="USER_ID",nullable = false)
	private String userId;
	
	@MetaData(value="用户昵称")
	@Column(name="NICK")
	private String nick;
	
	@MetaData(value="性别 1-男 2-女")
	@Column(name="SEX")
	private String sex;
	
	@MetaData(value="卖家类型 1-BB商家 2-CC商家")
	@Column(name="TYPE")
	private String type;
	
	@MetaData(value="是否购买多图服务")
	@Column(name="HAS_MORE_PIC")
	private Boolean hasMorePic = Boolean.FALSE;
	
	@MetaData(value="可上传图片数量")
	@Column(name="ITEM_IMG_NUM")
	private Long itemImgNum;
	
	@MetaData(value="单张商品图片最大容量")
	@Column(name="ITEM_IMG_SIZE")
	private Long itemImgSize;
	
	@MetaData(value="可上传属性图片数量")
	@Column(name="PROP_IMG_NUM")
	private Long propImgNum;
	
	@MetaData(value="单张属性图片最大容量")
	@Column(name="PROP_IMG_SIZE")
	private Long propImgSize;
	
	@MetaData(value="是否受限制 1-是 2-否")
	@Column(name="AUTO_REPOST")
	private String autoRepost;
	
	@MetaData(value="有无实名认证 1-有 2-无")
	@Column(name="PROMOTED_TYPE")
	private String promotedType;
	
	@MetaData(value="状态 1-正常 2-未激活 3-删除 4-冻结")
	@Column(name="STATUS")
	private String status;
	
	@MetaData(value="有无绑定 1-有 2-无")
	@Column(name="ALIPAY_BIND")
	private String alipayBind;
	
	@MetaData(value="是否参加消保")
	@Column(name="CONSUMER_PROTECTION")
	private Boolean consumerProtection;
	
	@MetaData(value="用户头像地址")
	@Column(name="AVATAR")
	private String avatar;
	
	@MetaData(value="是否是无名良品用户")
	@Column(name="LIANGPIN")
	private Boolean liangpin;
	
	@MetaData(value="卖家是否签署食品卖家承诺协议")
	@Column(name="SIGN_FOOD_SELLER_PROMISE")
	private Boolean signFoodSellerPromise;
	
	@MetaData(value="用户作为卖家是否开过店")
	@Column(name="HAS_SHOP")
	private Boolean hasShop = Boolean.FALSE;
	
	@MetaData(value="是否24小时闪电发货")
	@Column(name="IS_LIGHTNING_CONSIGNMENT")
	private Boolean isLightningConsignment = Boolean.TRUE;
	
	@MetaData(value="用户是否具备修改商品减库存逻辑的权限")
	@Column(name="HAS_SUB_STOCK")
	private Boolean hasSubStock = Boolean.FALSE;
	
	@MetaData(value="是否为金牌卖家")
	@Column(name="IS_GOLEDU_SELLER")
	private Boolean isGoleduSeller = Boolean.FALSE;
	
	@MetaData(value="是否订阅了淘宝天下杂志")
	@Column(name="MAGAZINE_SUBSCRIBE")
	private Boolean magazineSubscribe=Boolean.FALSE;
	
	@MetaData(value="是否参与垂直市场类型 1-鞋城垂直市场用户 2-3C垂直市场用户")
	@Column(name="VERTICAL_MARKET")
	private String verticalMarket;
	
	@MetaData(value="是否为网游用户")
	@Column(name="ONLINE_GAMING")
	private Boolean onlineGaming =Boolean.FALSE;
	
	@MetaData(value="vip会员等级 1-普通会员 2-vip荣誉会员 3-vip1 4-vip2 5-vip3 6-vip4 7-vip5 8-vip6")
	@Column(name="vip_info")
	private String vipInfo;
}
