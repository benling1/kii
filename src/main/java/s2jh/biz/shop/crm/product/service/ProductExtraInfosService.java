package s2jh.biz.shop.crm.product.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.product.dao.ProductExtraInfosDao;
import s2jh.biz.shop.crm.product.entity.ProductExtraInfos;

@Service
@Transactional
public class ProductExtraInfosService extends BaseService<ProductExtraInfos, Long>{
	
	@Autowired
	private ProductExtraInfosDao productExtraInfosDao;
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<ProductExtraInfos, Long> getEntityDao() {
		
		return productExtraInfosDao;
	}

}
