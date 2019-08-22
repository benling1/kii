package s2jh.biz.shop.crm.order.dao;

import org.springframework.stereotype.Repository;

import lab.s2jh.core.dao.jpa.BaseDao;
import s2jh.biz.shop.crm.order.entity.ServiceOrders;

@Repository
public interface ServiceOrdersDao extends BaseDao<ServiceOrders, Long> {

}
