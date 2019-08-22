package s2jh.biz.shop.crm.product.service;
import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s2jh.biz.shop.crm.product.dao.ProductImgsDao;
import s2jh.biz.shop.crm.product.entity.ProductImgs;

@Service
@Transactional
public class ProductImgsService extends BaseService<ProductImgs, Long>{
	
	@Autowired
	private ProductImgsDao productImgsDao;
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<ProductImgs, Long> getEntityDao() {
		return productImgsDao;
	}
	
}
