package s2jh.biz.shop.crm.other.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.other.dao.NoticeDao;
import s2jh.biz.shop.crm.other.entity.Notice;


@Service
@Transactional
public class NoticeService extends BaseService<Notice, Long>{
	
	@Autowired
	private NoticeDao noticeDao;

	@Override
	protected BaseDao<Notice, Long> getEntityDao() {
		return noticeDao;
	}
	
	@Autowired
	private MyBatisDao myBatisDao;

	@SuppressWarnings({"rawtypes","unchecked"})
	public List<Notice> findNotice(Map map){
		List<Notice> list = myBatisDao.findList("s2jh.biz.shop.crm.other.entity.Notice", "findNotice", map);
		return list;
		
	}
	
	/*public WebPage<Map<String, Object>> findWebPage(Map<String, Object> parameters, Pageable pageable) {
		WebPage<Map<String, Object>> page = myBatisDao.findWebPage("s2jh.biz.shop.crm.other.entity.Notice" ,"findNotice", parameters,
				pageable);
		return page;
	}*/
	
	
}
