package s2jh.biz.shop.crm.user.dao;

import lab.s2jh.core.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

import s2jh.biz.shop.crm.user.entity.OrderApplicationInfo;

@Repository
public interface OrderApplicationInfoDao extends
		BaseDao<OrderApplicationInfo, Long> {

}
