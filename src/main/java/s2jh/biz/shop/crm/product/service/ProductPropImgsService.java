package s2jh.biz.shop.crm.product.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.product.dao.ProductPropImgsDao;
import s2jh.biz.shop.crm.product.entity.ProductPropImgs;

@Service
@Transactional
public class ProductPropImgsService extends BaseService<ProductPropImgs, Long>{
	
	@Autowired
	private ProductPropImgsDao productPropImgsDao;
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<ProductPropImgs, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return productPropImgsDao;
	}

}
