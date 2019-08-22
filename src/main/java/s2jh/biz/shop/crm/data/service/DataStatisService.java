package s2jh.biz.shop.crm.data.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.data.dao.DataStatisDao;
import s2jh.biz.shop.crm.data.entity.DataStatis;

@Service
@Transactional
public class DataStatisService extends BaseService<DataStatis, Long>{
	
	@Autowired
	private DataStatisDao dataStatisDao;
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<DataStatis, Long> getEntityDao() {
		return dataStatisDao;
	}
	
	//昨日订单数
	public Integer YesterDayOrderNum(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayOrderNum", null);
	}
	
	//昨日退款中
	public Integer YesterDayRefundment(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayRefundment", null);
	}
	
	//待付款
	public Integer YesterDayPendingPayment(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayPendingPayment", null);
	}
	
	//待发货
	public Integer YesterDayPendingConsignment(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayPendingConsignment", null);
	}
	
	//购物车宝贝
	public Integer YesterDayShoppingCartCommodity(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayShoppingCartCommodity", null);
	}
	
	//直通车账户余额
	public BigDecimal YesterDayZtcAccountBalance(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayZtcAccountBalance", null);
		
	}
	
	//被浏览的宝贝
	public Integer YesterDayBrowseCommodity(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayBrowseCommodity", null);
	}
	
	//总访量
	public Integer YesterDayAccessAmount(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayAccessAmount", null);
	}
	
	//Pc流量
	public String YesterDayPcFlow(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayPcFlow", null);
	}
	
	//无线流量
	public String YesterDayWapFlow(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayWapFlow", null);
	}
	
	//成交金额
	public BigDecimal YesterDayTransactionPrice(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayTransactionPrice", null);
	}
	
	// -----------------------------------------前日数据查询-----------------------------------------
	
	//前日订单
	public Integer BeforeYesterDayOrderNum(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayOrderNum", null);
	}
	
	//退款中
	public Integer BeforeYesterDayRefundment(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayRefundment", null);
	}
	
	//待付款
	public Integer BeforeYesterDayPendingPayment(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayPendingPayment", null);
	}
	
	//待发货
	public Integer BeforeYesterDayPendingConsignment(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayPendingConsignment", null);
	}
	
	//购物车宝贝
	public Integer BeforeYesterDayShoppingCartCommodity(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayShoppingCartCommodity", null);
	}
	
	//直通车账户余额
	public BigDecimal BeforeYesterDayZtcAccountBalance(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayZtcAccountBalance", null);
	}
	
	//被浏览的宝贝
	public Integer BeforeYesterDayBrowseCommodity(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayBrowseCommodity", null);
	}
	
	//总访客
	public Integer BeforeYesterDayAccessAmount(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayAccessAmount", null);
	}
	
	//Pc流量
	public String BeforeYesterDayPcFlow(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayPcFlow", null);
	}
	
	//无线流量
	public String BeforeYesterDayWapFlow(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayWapFlow", null);
	}
	
	//成交金额
	public BigDecimal BeforeYesterDayTransactionPrice(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayTransactionPrice", null);
	}
	
	// -----------------------------------------店铺昨日数据-----------------------------------------
	
	//出售中
	public BigDecimal YesterDaySale(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayTransactionPrice", null);
	}
	
	//被收藏的宝贝
	public Integer YesterDayCollectionCommodity(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayCollectionCommodity", "null");
	}
	
	//聚划算成交额
	public BigDecimal YesterDayJhsTransactionPrice(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayJhsTransactionPrice", null);
	}
	
	//PC订单数
	public Integer YesterDayPcOrderNum(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayPcOrderNum", null);
	}
	
	//无线订单数
	public Integer YesterDayWapOrderNum(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayWapOrderNum", null);
	}
	
	//pc成交额
	public Integer YesterDayPcTransactionPrice(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayPcTransactionPrice", null);
	}
	
	//无线成交额
	public Integer YesterDayWapTransactionPrice(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findYesterDayWapTransactionPrice", null);
	}
	
	// -----------------------------------------店铺前日数据-----------------------------------------
	//出售中
	public Integer BeforeYesterDaySale(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDaySale", null);
	}
	
	//被收藏的宝贝
	public Integer BeforeYesterDayCollectionCommodity(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayCollectionCommodity", "null");
	}
	
	//聚划算成交额
	public BigDecimal BeforeYesterDayJhsTransactionPrice(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayJhsTransactionPrice", null);
	}
	
	//PC订单数
	public Integer BeforeYesterDayPcOrderNum(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayPcOrderNum", null);
	}
	
	//无线订单数
	public Integer BeforeYesterDayWapOrderNum(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayWapOrderNum", null);
	}
	
	//pc成交额
	public Integer BeforeYesterDayPcTransactionPrice(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayPcTransactionPrice", null);
	}
	
	//无线成交额
	public Integer BeforeYesterDayWapTransactionPrice(){
		return myBatisDao.findBy("s2jh.biz.shop.crm.data.entity.DataStatis", "findBeforeYesterDayWapTransactionPrice", null);
	}
}