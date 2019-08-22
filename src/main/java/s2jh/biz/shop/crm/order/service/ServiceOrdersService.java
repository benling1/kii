package s2jh.biz.shop.crm.order.service;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.order.dao.ServiceOrdersDao;
import s2jh.biz.shop.crm.order.entity.ServiceOrders;

@Service
@Transactional
public class ServiceOrdersService extends BaseService<ServiceOrders, Long>{
	@Autowired
	private ServiceOrdersDao serviceOrdersDao;
	
	@Override
	protected BaseDao<ServiceOrders, Long> getEntityDao() {
		return serviceOrdersDao;
	}

}
