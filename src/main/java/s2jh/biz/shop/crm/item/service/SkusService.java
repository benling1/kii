package s2jh.biz.shop.crm.item.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.dao.mybatis.MyBatisDaoT;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.item.dao.SkusDao;
import s2jh.biz.shop.crm.item.entity.Skus;

@Service
@Transactional
public class SkusService extends BaseService<Skus, Long>{
	
	@Autowired
	private SkusDao skusDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private MyBatisDaoT myBatisDaoT;

	@Override
	protected BaseDao<Skus, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
