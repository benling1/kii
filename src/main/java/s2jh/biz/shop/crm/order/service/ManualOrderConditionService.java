package s2jh.biz.shop.crm.order.service;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.order.dao.ManualOrderConditionDao;
import s2jh.biz.shop.crm.order.entity.ManualOrderCondition;

@Service
@Transactional
public class ManualOrderConditionService extends BaseService<ManualOrderCondition, Long>{
	
	@Autowired
	private ManualOrderConditionDao manualOrderConditionDao;
	
	@Override
	protected BaseDao<ManualOrderCondition, Long> getEntityDao() {
		return manualOrderConditionDao;
	}

}
