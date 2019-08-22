package s2jh.biz.shop.crm.other.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.other.dao.FestivalDao;
import s2jh.biz.shop.crm.other.entity.Festival;

@Service
@Transactional
public class FestivalService extends BaseService<Festival, Long>{
	
	@Autowired
	private FestivalDao feStivalDao;
	
	@Autowired
	private MyBatisDao mybatisDao;
	
	@Override
	protected BaseDao<Festival, Long> getEntityDao() {
		return feStivalDao;
	}
	
	//根据当前时间获取最近的节日
	public Festival findFestival(String dateTime){
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("dateTime", dateTime);
		Festival festival = mybatisDao.findBy(Festival.class.getName(), "findFestivalFromDate", map);
		
		if(festival!=null){
			return festival;
		}
		return null;
	}
}
