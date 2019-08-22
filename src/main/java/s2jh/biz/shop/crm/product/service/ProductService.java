package s2jh.biz.shop.crm.product.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.product.dao.ProductDao;
import s2jh.biz.shop.crm.product.entity.Product;

@Service
@Transactional
public class ProductService extends BaseService<Product, Long>{
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<Product, Long> getEntityDao() {
		return productDao;
	}

}
