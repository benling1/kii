package s2jh.biz.shop.crm.message.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.message.dao.SmsHistoryTemplateDao;
import s2jh.biz.shop.crm.message.entity.SmsHistoryTemplate;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class SmsHistoryTemplateService extends BaseService<SmsHistoryTemplate, Long>{
	
	@Autowired
	private SmsHistoryTemplateDao smsHistoryTemplateDao;
	
	@Autowired
	private MyBatisDao mybatisDao;
	
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	@Override
	protected BaseDao<SmsHistoryTemplate, Long> getEntityDao() {
		return smsHistoryTemplateDao;
	}
	
	//根据卖家编号查询所使用过的短信模板
	public Pagination findSmsHistoryTemplate(String userId,String contextPath,Integer pageNo){
		//设置起始页
		if(pageNo==null){
			pageNo =1;	
		}
		//先设置每页显示的条数为5条
		Integer currentRows = 5;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;		
		
		//计算出总页数
		int count =findCountSmsHistoryTemplate(userId);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		
		List<SmsHistoryTemplate> list = mybatisDao.findList(SmsHistoryTemplate.class.getName(), "findAll", map);
		//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
		//smsSendRecordList:每页显示的list集合或者当前页显示的list集合
		Pagination pagination = new Pagination(pageNo, currentRows, count, list);
		
		//拼接分页的后角标中的跳转路径与查询的条件
		String url =contextPath+"/historyTemplate/findAllList";
		StringBuilder params = new StringBuilder();
		pagination.pageView(url, params.toString());
		return pagination;
	}
	
	/**
	 * 根据卖家编号查询所有使用过短信模板的总数
	 * @param userId
	 * @return
	 */
	public int findCountSmsHistoryTemplate(String userId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		int count =  mybatisDao.findBy(SmsHistoryTemplate.class.getName(), "findAllCount", map);
		return count;
	}

	/**
	 * 根据类型(评价模板)和userId查询模板使用记录
	 */
	public Pagination findRateHistoryTemplate(Map<String, Object>map , Integer pageNo){
		//设置起始页
		if(pageNo==null){
			pageNo =1;
		}
		//先设置每页显示的条数为5条
		Integer currentRows = 5;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;		
		//计算出总页数
		int count = mybatisDao.findBy(SmsHistoryTemplate.class.getName(), "findAllCount", map);
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		//查询出所有的List(分页)
		List<SmsHistoryTemplate> list = mybatisDao.findList(SmsHistoryTemplate.class.getName(), "findAll", map);
		//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
		//smsSendRecordList:每页显示的list集合或者当前页显示的list集合
		Pagination pagination = new Pagination(pageNo, currentRows, count, list);
		return pagination;
	}
	
	/**
	 * 根据卖家id、type以及templateId添加历史使用
	 */
	public void addHistoryTempById(SmsHistoryTemplate smsHistoryTemplate){
		smsHistoryTemplateDao.save(smsHistoryTemplate);
	}
	
	/**
	 * 根据卖家id、type以及templateId更新历史使用
	 */
	public void updateHistoryTempById(Map<String,Object> map){
		mybatisDao.execute(SmsHistoryTemplate.class.getName(), "updateHistoryTempById", map);
	}
	
	/**
	 * 通过userId和templateId查询历史使用表中是否存在
	 */
	public SmsHistoryTemplate findTemplateBytemplateId(String userId,String templateId,String type){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("templateId", templateId);
		map.put("userId", userId);
		map.put("type", type);
		//通过userId和templateId查询历史使用表中是否存在
		SmsHistoryTemplate smsHistoryTemplate = mybatisDao.findBy(SmsHistoryTemplate.class.getName(), "findTemplateBytemplateId", map);
		return smsHistoryTemplate;
	}
	
	/**
	 * 查询历史使用的短信模板--中差评
	 * helei 2017年1月3日上午10:46:02
	 */
	public List<SmsHistoryTemplate> findListAll(Map<String,Object> map){
		List<SmsHistoryTemplate> findList = mybatisDao.findList(SmsHistoryTemplate.class.getName(), "findListAll", map);
		return findList;
	}
	
	/**
	 * 根据类型(评价模板)和userId查询模板使用记录
	 */
	public List<SmsHistoryTemplate> findHistoryTemplate(Map<String, Object>map){
		//查询出所有的List(分页)
		List<SmsHistoryTemplate> smsHisTempList = mybatisDao.findList(SmsHistoryTemplate.class.getName(), "findOrdersSmsHisTemplate", map);
		return smsHisTempList;
	}
	
}

