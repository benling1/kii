package s2jh.biz.shop.crm.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.order.dao.LogisticsOrderDao;
import s2jh.biz.shop.crm.order.entity.LogisticsOrder;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;

@Service
@Transactional
public class LogisticsOrderService extends BaseService<LogisticsOrder, Long> {
	@Override
	protected BaseDao<LogisticsOrder, Long> getEntityDao() {
		return logisticsOrderDao;
	}
	
	@Autowired
	private LogisticsOrderDao logisticsOrderDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	/**
	 * 单个添加物流订单信息信息
	 * helei 2017年1月10日下午5:34:43
	 */
	public void saveLogisticsOrder(LogisticsOrder logisticsOrder) {
		//保存
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tid", logisticsOrder.getTid());
		map.put("orderCode", logisticsOrder.getOrderCode());
		map.put("status", logisticsOrder.getStatus());
		map.put("isQuickCodOrder", logisticsOrder.getIsQuickCodOrder());
		map.put("sellerNick", logisticsOrder.getSellerNick());
		map.put("buyerNick", logisticsOrder.getBuyerNick());
		map.put("deliveryStart", logisticsOrder.getDeliveryStart());
		map.put("deliveryEnd", logisticsOrder.getDeliveryEnd());
		map.put("outSid", logisticsOrder.getOutSid());
		map.put("itemTitle", logisticsOrder.getItemTitle());
		map.put("receiverName", logisticsOrder.getReceiverName());
		map.put("receiverPhone", logisticsOrder.getReceiverPhone());
		map.put("receiverMobile", logisticsOrder.getReceiverMobile());
		map.put("type", logisticsOrder.getType());
		map.put("freightPayer", logisticsOrder.getFreightPayer());
		map.put("sellerConfirm", logisticsOrder.getSellerConfirm());
		map.put("companyName", logisticsOrder.getCompanyName());
		map.put("isSuccess", logisticsOrder.getIsSuccess());
		map.put("created", logisticsOrder.getCreated());
		map.put("modified", logisticsOrder.getModified());
		map.put("isSpilt", logisticsOrder.getIsSpilt());
		map.put("subTids", logisticsOrder.getSubTids());
		Date nowDate = new Date();
		map.put("createdDate",nowDate );
		map.put("lastModifiedDate", nowDate);
		myBatisDao.execute(LogisticsOrder.class.getName(), "saveLogisticsOrder", map);
	}
	
	/**
	 * 批量添加物流订单信息信息
	 * helei 2017年1月10日下午5:34:43
	 */
	public void saveLogisticsOrderList(List<LogisticsOrder> logisticsOrder) {
		//保存
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", logisticsOrder);
		myBatisDao.execute(LogisticsOrder.class.getName(), "saveLogisticsOrderList", map);
	}
	
	/**
	 * 单个修改物流订单信息信息
	 * helei 2017年1月10日下午5:34:43
	 */
	public void updateLogisticsOrder(LogisticsOrder logisticsOrder) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tid", logisticsOrder.getTid());
		map.put("orderCode", logisticsOrder.getOrderCode());
		map.put("status", logisticsOrder.getStatus());
		map.put("isQuickCodOrder", logisticsOrder.getIsQuickCodOrder());
		map.put("sellerNick", logisticsOrder.getSellerNick());
		map.put("buyerNick", logisticsOrder.getBuyerNick());
		map.put("deliveryStart", logisticsOrder.getDeliveryStart());
		map.put("deliveryEnd", logisticsOrder.getDeliveryEnd());
		map.put("outSid", logisticsOrder.getOutSid());
		map.put("itemTitle", logisticsOrder.getItemTitle());
		map.put("receiverName", logisticsOrder.getReceiverName());
		map.put("receiverPhone", logisticsOrder.getReceiverPhone());
		map.put("receiverMobile", logisticsOrder.getReceiverMobile());
		map.put("type", logisticsOrder.getType());
		map.put("freightPayer", logisticsOrder.getFreightPayer());
		map.put("sellerConfirm", logisticsOrder.getSellerConfirm());
		map.put("companyName", logisticsOrder.getCompanyName());
		map.put("isSuccess", logisticsOrder.getIsSuccess());
		map.put("created", logisticsOrder.getCreated());
		map.put("modified", logisticsOrder.getModified());
		map.put("isSpilt", logisticsOrder.getIsSpilt());
		map.put("subTids", logisticsOrder.getSubTids());
		myBatisDao.execute(LogisticsOrder.class.getName(), "updateLogisticsOrderByOrderCode", map);
		
	}
	
	/**
	 * 批量修改物流订单信息信息
	 * helei 2017年1月10日下午5:34:43
	 */
	/*public void updateLogisticsOrderList(List<LogisticsOrder> logisticsOrder) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", logisticsOrder);
		myBatisDao.execute(LogisticsOrder.class.getName(), "updateLogisticsOrderList", map);
		
	}*/
	
	
	
	/**
	 * 查询物流订单信息信息
	 * helei 2017年1月10日下午5:34:43
	 */
	public int findLogisticsOrderByOrderCode(String orderCode) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderCode", orderCode);
		//测试数据
		int orderCodeCount = myBatisDao.findBy(LogisticsOrder.class.getName(), "findLogisticsOrderByOrderCode", map);
		return orderCodeCount;
	}
}
