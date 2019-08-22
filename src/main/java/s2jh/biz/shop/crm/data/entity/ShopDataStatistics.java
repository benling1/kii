package s2jh.biz.shop.crm.data.entity;

import java.util.Date;

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
@Table(name = "CRM_SHOP_DATA_STATISTICS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value ="店铺数据统计表")
public class ShopDataStatistics extends BaseNativeEntity{

	private static final long serialVersionUID = 8347756130798864527L;
	
	@MetaData(value="用户ID")
	@Column(name="USERID")
	private String userId;
	
	@MetaData(value="变动数量")
	@Column(name="CHANGEQUANTITY")
	private String changeQuantity;
	
	@MetaData(value="变动时间")
	@Column(name="CTIME")
	private Date cTime;
	
	@MetaData(value="数据类型 1-订单数 2-退款中 3-待付款 4-待发货 5-出售中 6-成交额 7-购物车宝贝 8-被收藏宝贝"
			+ "9-被浏览宝贝 10-总访客 11-PC流量 12-无线流量 13-聚划算成交额 14-PC订单数 15-PC成交额 16-无线成交额"
			+ "17-无线订单数 18-已厨窗推荐 19-直通车点击转化率 20-直通车账户余额 21-直通车展现量 22-直通车花费 23-总流量")
	@Column(name="DATA_TYPE")
	private String dataType;
	
}
