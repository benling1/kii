package s2jh.biz.shop.crm.member.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.member.dao.GradePromotionsDao;
import s2jh.biz.shop.crm.member.entity.GradePromotions;

@Service
@Transactional
public class GradePromotionsService extends BaseService<GradePromotions, Long>{
	@Autowired
	private GradePromotionsDao gradePromotionsDao;
	
	@Autowired
	private MyBatisDao mybatisDao;
	
	@Override
	protected BaseDao<GradePromotions, Long> getEntityDao() {
		return gradePromotionsDao;
	}
	
	//根据卖家编号查询会员设置等级信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<GradePromotions> findAllGradePromotions(Map map){		
		List<GradePromotions> list = mybatisDao.findList(GradePromotions.class.getName(), "findAllGradePromotions", map);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}

}
