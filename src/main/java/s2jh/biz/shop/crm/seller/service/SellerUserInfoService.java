package s2jh.biz.shop.crm.seller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.seller.dao.SellerUserInfoDao;
import s2jh.biz.shop.crm.seller.entity.SellerUserInfo;
import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

@Service
@Transactional
public class SellerUserInfoService extends BaseService<SellerUserInfo, Long>{
	
	@Autowired
	private SellerUserInfoDao sellerUserInfoDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	

	@Override
	protected BaseDao<SellerUserInfo, Long> getEntityDao() {
		return sellerUserInfoDao;
	}
	
	

}
