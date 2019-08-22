package s2jh.biz.shop.crm.expediting.service;

import java.util.List;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.expediting.dao.ExpeditingCaseDao;
import s2jh.biz.shop.crm.expediting.entity.ExpeditingCase;

@Service
@Transactional
public class ExpeditingCaseService extends BaseService<ExpeditingCase, Long>{
	
	@Autowired
	private ExpeditingCaseDao expeditingCaseDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Override
	protected BaseDao<ExpeditingCase, Long> getEntityDao() {
		return expeditingCaseDao;
	}
	
	//查询所有的优秀催付案例	
	public List<ExpeditingCase> findAllExpeditingCase(){		
		List<ExpeditingCase> list = myBatisDao.findList(ExpeditingCase.class.getName(), "findAllExpeditingCase", null);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
}
