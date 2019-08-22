package s2jh.biz.shop.crm.order.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.order.dao.PromotionDetailsDao;
import s2jh.biz.shop.crm.order.entity.PromotionDetails;

@Service
@Transactional
public class PromotionDetailsService extends BaseService<PromotionDetails, Long> {
	
	@Autowired
	private PromotionDetailsDao promotionDetailsDao;
	
	@Override
	protected BaseDao<PromotionDetails, Long> getEntityDao() {
		return promotionDetailsDao;
	}

}
