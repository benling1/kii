package s2jh.biz.shop.crm.feedback.service;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.feedback.dao.FeedBackDao;
import s2jh.biz.shop.crm.feedback.entity.FeedBack;

@Service
@Transactional
public class FeedBackService extends BaseService<FeedBack, Long>  {

	@Override
	protected BaseDao<FeedBack, Long> getEntityDao() {
		return feedBackDao;
	}
	
	@Autowired
	private FeedBackDao feedBackDao;

	@Autowired
	private MyBatisDao myBatisDao;
	

	/**
	 * 客户反馈信息保存
	 * @author HL
	 * @param orderCenterSetup
	 * @return
	 */
	public Long insertFeedBack(FeedBack feedBack){
		myBatisDao.execute(FeedBack.class.getName(), "insertFeedBack", feedBack);
		return feedBack.getId();
	}
}
