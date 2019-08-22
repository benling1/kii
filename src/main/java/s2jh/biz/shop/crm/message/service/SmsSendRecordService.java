package s2jh.biz.shop.crm.message.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.BaseService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.manage.dao.SmsRecordRepository;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.vo.SendMsgVo;
import s2jh.biz.shop.crm.message.dao.SmsSendRecordDao;
import s2jh.biz.shop.crm.message.entity.SmsSendRecord;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.pagination.MyPagination;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class SmsSendRecordService extends BaseService<SmsSendRecord, Long> {
	
	@Autowired
	private SmsSendRecordDao smsSendRecordDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private SmsRecordRepository smsRecordRepository;

	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	@Override
	protected BaseDao<SmsSendRecord, Long> getEntityDao() {
		return smsSendRecordDao;
	}

//	private static final Log logger = LogFactory.getLog(SmsSendRecordService.class);

//	/**
//	 * 短信发送记录>>详情统计
//	 * */
//	// 1目标发送客户总数//此方法有通过会员分组改为查询总计的的总条数
//	public Integer getTotalCustom(String userId,Integer recordId,String type) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("recordId", recordId);
//		map.put("type", type);
//		Long l = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"getTotalCustom", map);
//		Integer i = Integer.valueOf(l.toString());
//		return i;
//	}
//	//2成功发送客户数//此方法有会员昵称分组改为通过手机号分组
//	public Integer getSuccessCustom(String userId,Integer recordId,String type) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("recordId", recordId);
//		map.put("type", type);
//		Long l = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"getSuccessCustom", map);
//		Integer i = Integer.valueOf(l.toString());
//		return i;
//	}
//	//3实际扣费条数
//	public Integer getActualCount(String userId,Integer recordId,String type) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("recordId", recordId);
//		map.put("type", type);
//		Long l = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"getActualCount", map);
//		l = l==null?0:l;
//		Integer i = Integer.valueOf(l.toString());
//		return i;
//	}
//	//4手机号码不正确
//	public Integer getWrongNum(String userId,Integer recordId,String type) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("recordId", recordId);
//		map.put("type", type);
//		Long l = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"getWrongNum", map);
//		Integer i = Integer.valueOf(l.toString());
//		return i;
//	}
//	//5重复手机号
//	public Integer getRepeatNum(String userId,Integer recordId,String type) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("recordId", recordId);
//		map.put("type", type);
//		Long l = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"getRepeatNum", map);
//		Integer i = Integer.valueOf(l.toString());
//		return i;
//	}
//	//6黑名单
//	public Integer getBlackCount(String userId,Integer recordId,String type) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("recordId", recordId);
//		map.put("type", type);
//		Long l = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"getBlackCount", map);
//		Integer i = Integer.valueOf(l.toString());
//		return i;
//	}
//	//7重复发送被屏蔽
//	public Integer getRepeatSend(String userId,Integer recordId,String type) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("recordId", recordId);
//		map.put("type", type);
//		Long l = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"getRepeatSend", map);
//		Integer i = Integer.valueOf(l.toString());
//		return i;
//	}
//	//8其他原因失败
//	public Integer getFailedCount(String userId,Integer recordId,String type) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("recordId", recordId);
//		map.put("type", type);
//		Long l = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"getFailedCount", map);
//		Integer i = Integer.valueOf(l.toString());
//		return i;
//	}
//	
//
//	/**
//	 * 店铺数据短信发送记录查询（不分页）
//	 */
//	public List<SmsSendRecord> findSendRecordList(Map<String, Object> map) {
//		map.remove("startRows");
//		map.remove("currentRows");
//		List<SmsSendRecord> list = myBatisDao.findList(
//				SmsSendRecord.class.getName(), "findSendRecordByQuery", map);
//		return list;
//	}
//
//	/**
//	 * 店铺数据短信发送记录查询（分页）
//	 */
//	@SuppressWarnings("unused")
//	public Pagination findSendRecordByQuery(String contextPath, Integer pageNo,
//			Map<String, Object> map) {
//		// 设置起始页
//		if (pageNo == null) {
//			pageNo = 1;
//		}
//		// 先设置每页显示的条数为3条
//		Integer currentRows = 5;
//		// 计算出起始行数
//		Integer startRows = (pageNo - 1) * currentRows;
//		// 查询数据总数
//		Integer totalCount = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"findSendRecordByQueryCount", map);
//		// 总页数
//		Integer totalPage = (int) Math.ceil(1.0 * totalCount / currentRows);
//		// 根据卖家条件查询的集合
//		if (startRows == null || currentRows == null) {
//			map.put("startRows", 1);
//			map.put("currentRows", 5);
//		}
//		map.put("startRows", startRows);
//		map.put("currentRows", currentRows);
//		List<SmsSendRecord> list = myBatisDao.findList(
//				SmsSendRecord.class.getName(), "findSendRecordByQuery", map);
//		// 分页工具类pagination
//		Pagination pagination = new Pagination(pageNo, currentRows, totalCount,
//				list);
//		return pagination;
//	}
//
//	/**
//	 * 手动订单提醒发送列表查询（分页）
//	 */
//	@SuppressWarnings("unused")
//	public Pagination findReminderSendList(Integer pageNo,
//			Map<String, Object> map) {
//		// 设置起始页
//		if (pageNo == null) {
//			pageNo = 1;
//		}
//		// 先设置每页显示的条数为3条
//		Integer currentRows = 5;
//		// 计算出起始行数
//		Integer startRows = (pageNo - 1) * currentRows;
//		// 查询数据总数
//		Integer totalCount = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"findReminderSendListCount", map);
//		// 总页数
//		Integer totalPage = (int) Math.ceil(1.0 * totalCount / currentRows);
//		// 根据卖家条件查询的集合
//		map.put("startRows", startRows);
//		map.put("currentRows", currentRows);
//		List<SmsSendRecord> list = myBatisDao.findList(
//				SmsSendRecord.class.getName(), "findReminderSendList", map);
//		// 分页工具类pagination
//		Pagination pagination = new Pagination(pageNo, currentRows, totalCount,
//				list);
//		return pagination;
//	}
//	
////	@SuppressWarnings("unused")
////	public Pageination<SmsRecordDTO> findReminderSendList(Integer pageNo,
////			Query query,String userId) {
////		// 设置起始页
////		if (pageNo == null) {
////			pageNo = 1;
////		}
////		// 先设置每页显示的条数为3条
////		Integer currentRows = 5;
////		// 计算出起始行数
////		Integer startRows = (pageNo - 1) * currentRows;
////		// 查询数据总数
//////		Integer totalCount = myBatisDao.findBy(SmsSendRecord.class.getName(),
//////				"findReminderSendListCount", map);
////		Integer totalCount = (int) smsRecordRepository.count(query, userId);
////		// 总页数
////		Integer totalPage = (int) Math.ceil(1.0 * totalCount / currentRows);
////		// 根据卖家条件查询的集合
//////		map.put("startRows", startRows);
//////		map.put("currentRows", currentRows);
//////		List<SmsSendRecord> list = myBatisDao.findList(
//////				SmsSendRecord.class.getName(), "findReminderSendList", map);
//////		List<SmsRecordDTO> list = smsRecordRepository.find(query, userId);
////		Pageination<SmsRecordDTO> page = new Pageination<SmsRecordDTO>();
////		page.setPageNo(pageNo);
////		page.setPageSize(currentRows);
////		page.setTotalCount(totalCount);
////		Pageination<SmsRecordDTO> pagination = smsRecordRepository.findPage(page, query, userId);
////		// 分页工具类pagination
//////		Pagination pagination = new Pagination(pageNo, currentRows, totalCount,
//////				list);
////		return pagination;
////	}
//
//	/**
//	 * 根据id更新活动名称
//	 */
//	public void updateActivityNameById(Long id, String activityName,
//			UserOperationLog log) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("id", id);
//		map.put("activityName", activityName);
//		myBatisDao.execute(SmsSendRecord.class.getName(),
//				"updateActivityNameById", map);
//		userOperationLogDao.save(log);
//	}
//	
////	public void updateActivityNameById(Long id, String activityName,
////			UserOperationLog log,String userId) {
////		Query query = new Query();
////		query.addCriteria(Criteria.where("id").is(id));
////		Update update = new Update();
////		update.set("activityName", activityName);
////		smsRecordRepository.update(query, update, userId);
////		userOperationLogDao.save(log);
////	}
//
//	/**
//	 * 查询发送失败的记录 ZTK2017年2月10日下午12:09:53
//	 */
//	/*
//	 * public List<SmsSendRecord> findSendFailList(Map<String, Object> queryMap)
//	 * { Map<String,Object> map = new HashMap<String, Object>();
//	 * map.put("buyerNick", queryMap.get("buyerNick")); map.put("bTime",
//	 * queryMap.get("bTime")); map.put("eTime", queryMap.get("eTime"));
//	 * map.put("userId", queryMap.get("userId")); map.put("status", 1);
//	 * List<SmsSendRecord> list =
//	 * myBatisDao.findList(SmsSendRecord.class.getName(),
//	 * "findSendRecordByQuery", map); return list; }
//	 */
//
//	/**
//	 * 查询发送成功条数以及实际扣费条数 ZTK2017年2月17日下午5:43:40
//	 */
//	public Map<String, Object> findMessageSuccessAndDeductCount(
//			Map<String, Object> map) {
//		if (map.containsKey("status")) {
//			map.remove("status");
//		}
//		Map<String, Object> queryMap = myBatisDao.findBy(
//				SmsSendRecord.class.getName(),
//				"findMessageSuccessAndDeductCount", map);
//		return queryMap;
//	}
//
//	/**
//	 * 短信群发页面-查询订单最后一次发送时间 ZTK2017年2月24日上午10:48:17
//	 */
//	public List<SmsSendRecord> findOrderLastsendTime(String userId, Date bTime,
//			Date eTime) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("bTime", bTime);
//		map.put("eTime", eTime);
//		List<SmsSendRecord> lastSendTimeList = myBatisDao.findList(
//				SmsSendRecord.class.getName(), "findOrderLastsendTime", map);
//		return lastSendTimeList;
//	}
//	//mongodb
////	public List<SmsRecordDTO> findOrderLastsendTime(String userId, Date bTime,
////			Date eTime) {
////		Query query = new Query();
//////		query.addCriteria(Criteria.where("userId").is(userId));
////		query.addCriteria(Criteria.where("bTime").is(bTime));
////		query.addCriteria(Criteria.where("eTime").is(eTime));
////		List<SmsRecordDTO> lastSendTimeList = smsRecordRepository.find(query, userId);
////		return lastSendTimeList;
////	}
//
//	/**
//	 * 订单短信群发-根据订单id和卖家查询短信发送记录 ZTK2017年3月7日下午3:38:06
//	 */
//	public List<SmsSendRecord> findSendRecordByOid(String userId,
//			String tradeTid) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("orderId", tradeTid);
//		List<SmsSendRecord> sendRecordList = myBatisDao.findList(
//				SmsSendRecord.class.getName(), "findSendRecordByOid", map);
//		if (sendRecordList != null && !"".equals(sendRecordList)) {
//			return sendRecordList;
//		} else {
//			return null;
//		}
//	}
//	//mongodb
////	public List<SmsRecordDTO> findSendRecordByOid(String userId,
////			String tradeTid) {
////		Query query = new Query();
//////		query.addCriteria(Criteria.where("userId").is(userId));
////		query.addCriteria(Criteria.where("tid").is(tradeTid));
////		List<SmsRecordDTO> sendRecordList = smsRecordRepository.find(query, userId);
////		if (sendRecordList != null && !"".equals(sendRecordList)) {
////			return sendRecordList;
////		} else {
////			return null;
////		}
////	}
//
//	/**
//	 * 查询物流的短信发送记录 helei 2017年3月23日下午3:39:40
//	 */
//	public Pagination findSmsRecordToLogistics(Integer pageNo,
//			Map<String, Object> map) {
//
//		// 设置起始页
//		if (pageNo == null || pageNo == 0) {
//			pageNo = 1;
//		}
//		// 先设置每页显示的条数为5条
//		Integer currentRows = 5;
//		// 计算出起始行数
//		Integer startRows = (pageNo - 1) * currentRows;
//		// 查询数据总数
//		Integer totalCount = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"findSmsRecordToLogisticsCount", map);
//		// 根据卖家条件查询的集合
//		map.put("startRows", startRows);
//		map.put("currentRows", currentRows);
//		List<SmsSendRecord> list = myBatisDao.findList(
//				SmsSendRecord.class.getName(), "findSmsRecordToLogistics", map);
//		// 分页工具类pagination
//		Pagination pagination = new Pagination(pageNo, currentRows, totalCount,
//				list);
//		return pagination;
//	}
//	
//	/**
//	  * 创建人：邱洋
//	  * @Title: 根据订单编号和短信发送类型查询是否发送过短信
//	  * @date 2017年4月15日--下午1:59:41 
//	  * @return Boolean
//	  * @throws
//	 */
//	public Boolean findSmsRecordStatus(String tid,String type){
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("tid", tid);
//		map.put("type", type);
//		int ssr = myBatisDao.findBy(SmsSendRecord.class.getName(), "findSmsRecordStatus", map);
//		if(ssr>0){
//			return true;
//		}
//		return false;
//	}
//
//	/** 
//	* @Title: smsSendPagination 
//	* @Description:  (群发记录详情>>ajax分页) 
//	* @param   pageNo
//	* @param   map
//	* @param @return    设定文件 
//	* @return MyPagination    返回类型 
//	* @throws
//	* @date 2017年4月25日 下午6:03:53 
//	* @author jackstraw_yu 
//	*/
//	public MyPagination smsSendPagination(Integer pageNo, Map<String, Object> hashmap) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		String userId = null;
//		Integer recordId = null;
//		String type = null;
//		String mobile = null;
//		Integer status = null;
//		if (hashmap != null) {
//			if (hashmap.get("userId") != null) {
//				userId = (String) hashmap.get("userId");
//				map.put("userId", userId);
//			}
//			if (hashmap.get("recordId") != null) {
//				recordId = (Integer) hashmap.get("recordId");
//				map.put("recordId", recordId);
//			}
//			if (hashmap.get("type") != null) {
//				type = (String) hashmap.get("type");
//				map.put("type", type);
//			}
//			if (hashmap.get("mobile") != null) {
//				mobile = (String) hashmap.get("mobile");
//				map.put("mobile", mobile);
//			}
//			if (hashmap.get("status") != null) {
//				status = (Integer) hashmap.get("status");
//				map.put("status", status);
//			}
//		}
//		// 先设置每页显示的条数为5条
//		Integer pageSize = ConstantUtils.PAGE_SIZE_MIN;
//		Integer startRows = (pageNo - 1) * pageSize;
//		map.put("pageSize", pageSize);
//		map.put("startRows", startRows);
//		Integer count  =  smsSendCount(map);
//		List<SmsSendRecord> sendList = myBatisDao.findLimitList(
//				SmsSendRecord.class.getName(), "smsSendPagination", map,
//				null);
//		MyPagination pagination = new MyPagination(pageNo, pageSize, count,
//				sendList);
//		//pagination.pageView();内部类需要调用才会创建
//		return pagination;
//	}
//
//	/** 
//	* @Title: smsSendCount 
//	* @Description:  (群发记录详情>>ajax分页;总条数) 
//	* @param   hashmap
//	* @param      设定文件 
//	* @return Integer    返回类型 
//	* @throws
//	* @date 2017年4月25日 下午6:09:37 
//	* @author jackstraw_yu 
//	*/
//	private Integer smsSendCount(Map<String, Object> hashmap) {
//		Long totalCount = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"smsSendCount", hashmap);
//		Integer i = Integer.valueOf(totalCount.toString());
//		return i;
//	}
//
//
//	/** 
//	* @Title: saveWrongNumSend 
//	* @Description:  (保存会员短信群发时错误的手机号的短信信息) 
//	* @param   wrongMemberInfos
//	* @param   content
//	* @param   type
//	* @param   userId
//	* @param   autograph
//	* @param   status
//	* @param   msgId    设定文件 
//	* @return void    返回类型 
//	* @throws
//	* @date 2017年5月3日 下午3:32:50 
//	* @author jackstraw_yu 
//	*/
//	public void saveErrorMsgNums(final List<String> wrongNums,final SendMsgVo sendMsgVo,final Integer status,final Date startTime) {
//		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
//			@Override
//			public void run() {
//				SmsSendRecord smsRecord = null;
//				for (String str : wrongNums) {
//					smsRecord = new SmsSendRecord();
//					smsRecord.setUserId(sendMsgVo.getUserId());
//					smsRecord.setMsgId(sendMsgVo.getMsgId());
//					//错误的短信或者 没有发送出去的的短信 记录实际扣费数为1条
//					smsRecord.setActualDeduction(1);
//					smsRecord.setType(sendMsgVo.getMsgType());
//					smsRecord.setStatus(status);
//					//smsRecord.setBuyerNick(memberInfo.getBuyerNick());
//					smsRecord.setContent(sendMsgVo.getContent());
//					smsRecord.setRecNum(str);
//					smsRecord.setSendTime(startTime);
//					smsRecord.setChannel(sendMsgVo.getAutograph());
//					smsRecord.setAutograph(sendMsgVo.getAutograph());
//					smsRecord.setShow(true);
//					try {
//						smsSendRecordDao.save(smsRecord);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//	}
//
//	
//	
//	/**
//	 * 订单中心查询每个模块的发送记录
//	 * ZTK2017年4月20日下午3:30:27
//	 */
//	public Pagination findOrderCenterSendRecord(Map<String,Object> map,Integer pageNo){
//		//findOrderCenterSendRecord  findOrderCenterSendRecordCount
//		// 先设置每页显示的条数为10条
//		Integer currentRows = 10;
//		// 计算出起始行数
//		Integer startRows = (pageNo - 1) * currentRows;
//		//返回集合总数(不分页)
//		int count = myBatisDao.findBy(SmsSendRecord.class.getName(), "findOrderCenterSendRecordCount", map);
//		if(startRows != null && !"".equals(startRows)){
//			map.put("startRows",startRows);
//		}else{
//			map.put("startRows",0);
//		}
//		if(currentRows != null && !"".equals(currentRows)){
//			map.put("currentRows", currentRows);
//		}else{
//			map.put("currentRows", 10);
//		}
//		List<SmsSendRecord> sendRecordList = myBatisDao.findList(SmsSendRecord.class.getName(), "findOrderCenterSendRecord", map);
//		Pagination pagination = new Pagination(pageNo, currentRows, count, sendRecordList);
//		return pagination;
//	}
//
//
//	/** 
//	* @Title: querySmsRecordMobiles 
//	* @Description: TODO(短信发送记录>>再次发送,查询出上次发送的所有手机号码) 
//	* @param   userId
//	* @param   recordId
//	* @param   type
//	* @param      设定文件 
//	* @return List<String>    返回类型 
//	* @throws
//	* @date 2017年5月5日 下午4:23:49 
//	* @author jackstraw_yu 
//	*/
//	public List<String> querySmsRecordMobiles(String userId,String recordId,String type){
//		Map<String,Object> map = new HashMap<String ,Object>();
//		map.put("recordId", recordId);
//		map.put("userId", userId);
//		map.put("type", type);
//		List<String> phones  = myBatisDao.findList(SmsSendRecord.class.getName(), "querySmsRecordMobiles", map);
//		return phones;
//	}
//
//	
//	/**
//	 * 添加错误手机号码的记录--营销中心--批量发短信记录
//	 * @author Administrator_HL
//	 * @data 2017年5月4日 下午7:43:10
//	 */
//	public void insertSmsSendRecord(SmsSendRecord smsSendRecord) {
//		myBatisDao.execute(SmsSendRecord.class.getName(), "insertSmsSendRecord", smsSendRecord);
//	}
//	//mongodb
////	public void insertSmsSendRecord(SmsRecordDTO smsSendRecord) {
////		smsRecordRepository.save(smsSendRecord, smsSendRecord.getUserId());
////	}
//	
//	/**
//	 * 通过userId、msgId、type查询短信发送总数
//	 * @author 滑静
//	 * @data 2017年5月9日 下午16:43:10
//	 */
//	// 1目标发送客户总数//此方法有通过会员分组改为查询总计的的总条数
//	public Integer getTotalCustomByMsgId(String userId,Long msgId,String type) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("msgId", String.valueOf(msgId));
//		map.put("type", type);
//		Long l = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"getTotalCustom", map);
//		Integer i = Integer.valueOf(l.toString());
//		return i;
//	}
//	/**
//	 * 通过userId、msgId、type查询短信发送成功总数
//	 * @author 滑静
//	 * @data 2017年5月9日 下午16:43:10
//	 */
//	//2成功发送客户数//此方法有会员昵称分组改为通过手机号分组
//	public Integer getSuccessCustomByMsgId(String userId,Long msgId,String type) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("msgId", String.valueOf(msgId));
//		map.put("type", type);
//		Long l = myBatisDao.findBy(SmsSendRecord.class.getName(),
//				"getSuccessCustom", map);
//		Integer i = Integer.valueOf(l.toString());
//		return i;
//	}
//
//	/**
//	 * 通过手机号查询买家最近发送的短信与卖家信息
//	 * ZTK2017年5月18日上午9:43:40
//	 */
//	public SmsSendRecord findRecordByPhone(String phone){
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("recNum",phone);
//		List<SmsSendRecord> recordList = myBatisDao.findList(SmsSendRecord.class.getName(),"findSendRecordByPhone", map);
//		if(recordList != null && recordList.size() > 0){
//			return recordList.get(0);
//		}else {
//			return null;
//		}
//	}
//
//	/**
//	 * 会员互动查询
//	 * ZTK2017年5月22日上午11:53:51
//	 */
//	public Pagination findMemberInteractHistory(Map<String, Object> map,Integer pageNo) {
//		//设置每页显示10条
//		Integer currentRows = 10;
//		Integer startRows = (pageNo - 1) * currentRows;
//		map.put("startRows", startRows);
//		map.put("currentRows", currentRows);
//		List<SmsSendRecord> sendRecordList = myBatisDao.findList(SmsSendRecord.class.getName(), "", map);
//		Integer totalCount = myBatisDao.findBy(SmsSendRecord.class.getName(), "", map);
//		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, sendRecordList);
//		return pagination;
//	}
//	
//	/**
//	 * 催付效果统计：根据筛选天数以及type查询发送记录
//	 * ZTK2017年6月6日上午11:15:02
//	 */
//	public List<String> findRecordTidByType(String type,String userId,
//			Date beginTime,Date endTime){
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("type", type);
//		map.put("userId",userId);
//		map.put("beginTime", beginTime);
//		map.put("endTime", endTime);
//		List<String> tidList = myBatisDao.findList(SmsSendRecord.class.getName(), "findRecordTidByType", map);
//		return tidList;
//	}
//	
//	/**
//	 * 催付效果分析根据tid的集合查询短信消费条数
//	 * ZTK2017年7月10日下午2:12:33
//	 */
//	public int findSmsNumByTid(String userId,List<String> tids,String type){
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("tids", tids);
//		map.put("type", type);
//		int smsNum = myBatisDao.findBy(SmsSendRecord.class.getName(), "findSmsNumByTids", map);
//		return smsNum;
//	}
//	
//	/**
//	 * 会员效果统计，查找发送记录
//	 * ZTK2017年6月8日下午4:06:48
//	 */
//	public List<String> findMarketingSendRecordByType(String type,String userId,
//			Date beginTime,Date endTime,String msgId){
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("type", type);
//		map.put("userId",userId);
//		map.put("beginTime", beginTime);
//		map.put("endTime", endTime);
//		map.put("msgId", msgId);
//		List<String> phoneList = myBatisDao.findList(SmsSendRecord.class.getName(), "findMarketingSendRecordByType", map);
//		return phoneList;
//	}
	
}
