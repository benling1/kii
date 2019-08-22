package s2jh.biz.shop.crm.seller.service;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.seller.dao.SellerCreditDao;
import s2jh.biz.shop.crm.seller.entity.SellerCredit;

@Service
@Transactional
public class SellerCreditService extends BaseService<SellerCredit, Long>{
	
	@Autowired
	private SellerCreditDao sellerCreditDao;
	
	@Override
	protected BaseDao<SellerCredit, Long> getEntityDao() {
		return sellerCreditDao;
	}

}
