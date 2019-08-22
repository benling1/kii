package s2jh.biz.shop.crm.order.service;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.order.dao.TradeExtDao;
import s2jh.biz.shop.crm.order.entity.TradeExt;

@Service
@Transactional
public class TradeExtService extends BaseService<TradeExt, Long> {
	
	@Autowired
	private TradeExtDao tradeExtDao;
	
	@Override
	protected BaseDao<TradeExt, Long> getEntityDao() {
		return tradeExtDao;
	}

}
