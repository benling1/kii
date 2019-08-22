package s2jh.biz.shop.crm.order.service;

import java.util.Date;
import java.util.Map;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.util.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.manage.dao.SmsRecordRepository;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.message.dao.SmsSendAppointDao;
import s2jh.biz.shop.crm.message.entity.SmsSendRecord;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;

@Service
@Transactional
public class SmsSendRecordToAppraiseService extends BaseService<SmsSendRecord, Long> {

	@Autowired
	private SmsSendAppointDao smsSendAppointDao;
	@Autowired
	private SmsRecordRepository smsRecordRepository;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	//操作日志dao
	@Autowired
	private UserOperationLogDao userOperationLogDao;

	@Override
	protected BaseDao<SmsSendRecord, Long> getEntityDao() {
		return smsSendAppointDao;
	}
	
	//===========中差评监控============
//	/**
//	 * 根据条件查询条数
//	 */
//	public Integer findTotalCountByCondition(Map<String, Object> map){
//		Long totalCount = myBatisDao.findBy(SmsSendRecord.class.getName(), "findTotalCountToAppraiseMonitoring", map);
//		Integer i = Integer.valueOf(totalCount.toString());
//		return i;
//		
//	}
			
//	/**
//	 * 根据条件查询展示分页数据
//	 */
//	public Pagination findPaginationByCondition(Integer pageNo,Map<String, Object> map){
//		//设置每页显示的条数为5条
//		Integer currentRows = 5;
//		//计算出起始行数
//		Integer startRows = (pageNo-1)*currentRows;		
//		//计算出总页数
//		Integer totalCount = findTotalCountByCondition(map);
//		map.put("startRows",startRows);
//		map.put("currentRows", currentRows);
//		List<SmsSendRecord> smsSendRecordList = myBatisDao.findLimitList(SmsSendRecord.class.getName(), "findLimitListToAppraiseMonitoring", map, currentRows);
//		//封装分页数据
//		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, smsSendRecordList);
//		return pagination;
//	}
	
	
	
	//================中差评安抚===============
//	/**
//	 * 根据条件查询条数
//	 */
//	public Integer findTotalCountToPacify(Map<String, Object> map){
//		Long totalCount = myBatisDao.findBy(SmsSendRecord.class.getName(), "findTotalCountToAppraisePacify", map);
//		Integer i = Integer.valueOf(totalCount.toString());
//		return i;
//		
//	}
			
//	/**
//	 * 根据条件查询展示分页数据
//	 */
//	public Pagination findPaginationToPacify(Integer pageNo,Map<String, Object> map){
//		//设置每页显示的条数为5条
//		Integer currentRows = 5;
//		//计算出起始行数
//		Integer startRows = (pageNo-1)*currentRows;		
//		//计算出总页数
//		Integer totalCount = findTotalCountToPacify(map);
//		map.put("startRows",startRows);
//		map.put("currentRows", currentRows);
//		List<SmsSendRecord> smsSendRecordList = myBatisDao.findList(SmsSendRecord.class.getName(), "findLimitListToAppraisePacify", map);
//		//封装分页数据
//		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, smsSendRecordList);
//		return pagination;
//	}
	
	
	
	/**
	 * 根据条件查询展示分页数据
	 */
	public Pageination<SmsRecordDTO> findPaginationByCondition(Integer pageNo,Map<String, Object> map){
		//设置每页显示的条数为5条
		Integer currentRows = 5;
		String userId = (String) map.get("userId");
		//封装查询条件
		Query query = new Query();
		packagingSmsRecordQueryParam(query,map);
		
		//封装page
		Pageination<SmsRecordDTO> page = new Pageination<SmsRecordDTO>();
		page.setPageNo(pageNo);
		page.setPageSize(currentRows);
		//查询数据
		if(null!=userId&&!"".equals(userId)){
			page = smsRecordRepository.findPage(page, query, userId);
		}
		return page;
	}

	/**
	 * 封装参数
	 * @param map 
	 * @param query 
	 */
	private void packagingSmsRecordQueryParam(Query query, Map<String, Object> map) {
		//获取参数
		Date bTime = (Date) map.get("bTime");
		Date eTime = (Date) map.get("eTime");
		String type = (String) map.get("type");
		String recNum = (String) map.get("recNum");
		String orderId = (String) map.get("orderId");
		String buyerNick = (String) map.get("buyerNick");
		if(bTime != null && eTime != null){
			query.addCriteria(Criteria.where("sendLongTime").gte(DateUtils.dateToLong(bTime)).lte(DateUtils.dateToLong(eTime)));
		}else if(bTime != null){
			query.addCriteria(Criteria.where("sendLongTime").gte(DateUtils.dateToLong(bTime)));
		}else if(eTime != null){
			query.addCriteria(Criteria.where("sendLongTime").lte(DateUtils.dateToLong(eTime)));
		}
		if(recNum!=null && !"".equals(recNum)){
			query.addCriteria(Criteria.where("recNum").in(recNum));
		}
		if(orderId!=null && !"".equals(orderId)){
			query.addCriteria(Criteria.where("orderId").in(orderId));
		}
		if(buyerNick!=null && !"".equals(buyerNick)){
			query.addCriteria(Criteria.where("buyerNick").regex(buyerNick));
		}
		query.addCriteria(Criteria.where("type").in(type));
	}
}
