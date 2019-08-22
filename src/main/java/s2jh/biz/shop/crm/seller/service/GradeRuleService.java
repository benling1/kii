package s2jh.biz.shop.crm.seller.service;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.seller.dao.GradeRuleDao;
import s2jh.biz.shop.crm.seller.entity.GradeRule;

@Service
@Transactional
public class GradeRuleService extends BaseService<GradeRule, Long>{
	
	@Autowired
	private GradeRuleDao gradeRuleDao;
	
	@Override
	protected BaseDao<GradeRule, Long> getEntityDao() {
		return gradeRuleDao;
	}

}
