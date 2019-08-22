package s2jh.biz.shop.crm.manage.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.util.DateUtils;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.dao.SmsRecordRepository;
import s2jh.biz.shop.crm.manage.dao.impl.SmsRecordRepositoryImpl;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.util.idworker.SmsRecordIdWorker;
import s2jh.biz.shop.crm.manage.vo.SendMsgVo;
import s2jh.biz.shop.crm.order.pojo.ReminderNum;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.tradecenter.vo.SmsRecordDTOVo;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.view.util.GetCurrentPageUtil;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.TradeStatusUtils;

import com.alibaba.druid.sql.visitor.functions.If;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.taobao.api.SecretException;

@Service
public class SmsRecordService {

	@Autowired
	private SmsRecordRepository smsRecordRepository;

	@Autowired
	private SmsRecordRepositoryImpl smsRecordRepositoryImpl;

	@Resource(name = "mongoTemplate")
	protected MongoTemplate mongoTemplate;

	@Autowired
	private UserOperationLogDao userOperationLogDao;

	@Autowired
	private TradeInfoService tradeInfoService;

	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	@Autowired
	private UserInfoService userInfoService;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(SmsRecordService.class);
	// private static final Logger logger =
	// Logger.getLogger(SmsRecordService.class);

	public void saveSmsRecord(List<SmsRecordDTO> smsRecordDTOList,
			String userNick) {
		smsRecordRepository.insertBatch(smsRecordDTOList, userNick);
	}

	public void saveSingleSmsRecord(SmsRecordDTO smsRecordDTO, String userNick) {
		smsRecordRepository.save(smsRecordDTO, userNick);
	}

	/**
	 * 批量:保存过滤的手机号(号码重复,手机号不正确...)
	 * */
	public void saveErrorMsgNums(List<String> errorNums, SendMsgVo sendMsgVo,
			Integer status, Date startTime) {
		List<SmsRecordDTO> smsRecordDTOList = null;
		if (errorNums != null && errorNums.size() > 0) {
			smsRecordDTOList = new ArrayList<SmsRecordDTO>();
			SmsRecordDTO record = null;
			for (String str : errorNums) {
				record = new SmsRecordDTO();
				record.setUserId(sendMsgVo.getUserId());
				record.setMsgId(sendMsgVo.getMsgId());
				record.setActualDeduction(0);// 错误的短信或者 没有发送出去的的短信 记录实际扣费数为0条
				record.setType(sendMsgVo.getMsgType());
				record.setStatus(status);
				record.setContent(sendMsgVo.getContent());
				record.setRecNum(str);
				record.setSendTime(startTime);
				record.setSendLongTime(startTime.getTime());
				record.setTimestampId(SmsRecordIdWorker.getInstance().nextId());
				record.setSource("2");
				record.setChannel(sendMsgVo.getAutograph());
				record.setAutograph(sendMsgVo.getAutograph());
				record.setShow(true);
				smsRecordDTOList.add(record);
			}
			saveSmsRecord(smsRecordDTOList, sendMsgVo.getUserId());
		}
	}

	public List<String> findNumList(Query query, String userId) {
		List<SmsRecordDTO> list = smsRecordRepository.find(query, userId);
		List<String> numList = null;
		if (list != null && list.size() > 0) {
			numList = new ArrayList<String>();
			for (SmsRecordDTO smsRecordDTO : list) {
				if(smsRecordDTO.getRecNum() != null && !"".equals(smsRecordDTO.getRecNum())){
					numList.add(smsRecordDTO.getRecNum());
				}
			}
		}
		return numList;
	}

	/**
	 * 
	 * @Description: 查询出短信发送详情
	 * @author jackstraw_yu
	 */
	public Pageination<SmsRecordDTO> findSmsRecordPageList(
			Map<String, Object> hashmap, Pageination<SmsRecordDTO> page,
			String userNickName) {
		page.setPageSize(ConstantUtils.PAGE_SIZE_MIN);
		Query query = new Query();
		// 显示
		query.addCriteria(Criteria.where("isShow").is(true));
		if (hashmap != null) {
			if (hashmap.get("recordId") != null) {
				query.addCriteria(Criteria.where("msgId").is(
						(Integer) hashmap.get("recordId")));
			}
			if (hashmap.get("type") != null) {
				query.addCriteria(Criteria.where("type").is(
						(String) hashmap.get("type")));
			}
			if (hashmap.get("mobile") != null) {
				String mobile = (String) hashmap.get("mobile");
				if(hashmap.get("like")!=null && (boolean)hashmap.get("like")){
					// Pattern pattern = Pattern.compile("^.*"+mobile+".*$",
					// Pattern.CASE_INSENSITIVE);
					// query.addCriteria(Criteria.where("recNum").regex(pattern));
					query.addCriteria(Criteria.where("recNum").regex("^.*" + mobile + ".*$"));
				}else{
					query.addCriteria(Criteria.where("recNum").is(mobile));
				}

			}
			if (hashmap.get("status") != null) {
				query.addCriteria(Criteria.where("status").is(
						(Integer) hashmap.get("status")));
			}
		}
		return smsRecordRepository.findPage(page, query, userNickName);
	}

	/**
	 * @Description: 再次发送根据总记录id,类型查询出手机号
	 * @author jackstraw_yu
	 */
	public List<String> querySmsRecordMobiles(String userId, Integer recordId,
			String type) {
		Query query = new Query();
		query.addCriteria(Criteria.where("type").is(type)).addCriteria(
				Criteria.where("msgId").is(recordId));
		List<String> nums = findNumList(query, userId);
		return nums;
	}

	/**
	 * @Description:删除短信发送记录详情/隐藏短信发送记录详情
	 * @author jackstraw_yu
	 */
	public void hideSmsRecord(Map<String, Object> map) {
		String userNickName = (String) map.get("userId");
		Query query = new Query();
		query.addCriteria(Criteria.where("type").is((String) map.get("type")))
				.addCriteria(
						Criteria.where("msgId").is(
								(Integer) map.get("recordId")));
		Update update = new Update();
		update.set("isShow", false);
		smsRecordRepository.updateAll(query, update, userNickName);
	}

	/**
	 * @Description:取消定时发送时直接删除mongo中的发送记录
	 * @author jackstraw_yu
	 */
	public void removeSmsRecord(Map<String, Object> map) {
		/*
		 * Map<String,Object> map = new HashMap<String, Object>();
		 * map.put("userId",userId); map.put("recordId", recordId);
		 * map.put("type", type);
		 */
		String userNickName = (String) map.get("userId");
		Query query = new Query();
		query.addCriteria(Criteria.where("type").is((String) map.get("type")))
				.addCriteria(
						Criteria.where("msgId").is(
								(Integer) map.get("recordId")));
		smsRecordRepository.removeAll(query, userNickName);
	}

	/**
	 * @param like 
	 * 创建人：邱洋
	 * 
	 * @Title: findSendRecordPageList
	 * @Description: TODO(根据查询条件查询发送记录表)
	 * @param @param page
	 * @param @param smsRecordDTO
	 * @param @param userNickName
	 * @param @return 设定文件
	 * @return Pageination<SmsRecordDTO> 返回类型
	 * @throws
	 */
	public Pageination<SmsRecordDTO> findSendRecordPageList(
			Pageination<SmsRecordDTO> page, SmsRecordDTO smsRecordDTO,
			String userNickName, Long btime, Long etime, boolean like) {
		Query query = new Query();
		query = packageCriteriaParam(query, smsRecordDTO, btime, etime,like);
		return smsRecordRepository.findPage(page, query, userNickName);
	}

	/**
	 * @param like 
	 * 创建人：邱洋
	 * 
	 * @Title: packageCriteriaParam
	 * @Description: TODO(将SmsRecordDTO中传入的参数拼接成查询mongo语句)
	 * @param @param query
	 * @param @param smsRecordDTO
	 * @param @return 设定文件
	 * @return Query 返回类型
	 * @throws
	 */
	private Query packageCriteriaParam(Query query, SmsRecordDTO smsRecordDTO,
			Long btime, Long etime, boolean like) {
		if (smsRecordDTO != null) {
			if (null != smsRecordDTO.getType()
					&& !"".equals(smsRecordDTO.getType())) {
				String[] type = smsRecordDTO.getType().split(",");
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < type.length; i++) {
					list.add(type[i]);
				}
				query.addCriteria(Criteria.where("type").in(list));
			}
			if (null != btime && null != etime && btime > 0L && etime > 0L) {
				query.addCriteria(Criteria.where("sendLongTime").lte(etime)
						.gte(btime));
			}
			if (null != smsRecordDTO.getbTime()
					&& null != smsRecordDTO.geteTime()) {
				query.addCriteria(Criteria.where("sendTime")
						.lte(smsRecordDTO.getbTime())
						.gte(smsRecordDTO.geteTime()));
			}
			if (null != smsRecordDTO.getStatus()
					&& !"".equals(smsRecordDTO.getStatus())) {
				query.addCriteria(Criteria.where("status").is(
						smsRecordDTO.getStatus()));
			}
			if (null != smsRecordDTO.getBuyerNick()
					&& !"".equals(smsRecordDTO.getBuyerNick())) {
				query.addCriteria(Criteria.where("buyerNick").regex(
						"^.*" +smsRecordDTO.getBuyerNick()+ ".*$"));
						/*is(smsRecordDTO.getBuyerNick()));*/
			}
			if (null != smsRecordDTO.getRecNum()
					&& !"".equals(smsRecordDTO.getRecNum())) {
				if(like){
					query.addCriteria(Criteria.where("recNum").regex(
							"^.*" +smsRecordDTO.getRecNum()+ ".*$"));
				}else{
					query.addCriteria(Criteria.where("recNum").is(
									smsRecordDTO.getRecNum()));
				}
					
			}
			if (null != smsRecordDTO.getOrderId()
					&& !"".equals(smsRecordDTO.getOrderId())) {
				query.addCriteria(Criteria.where("orderId").is(
						smsRecordDTO.getOrderId()));
			}
		}
		query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "_id")));
		return query;
	}

	/**
	 * 创建人：邱洋
	 * 
	 * @Title: getDeduct
	 * @Description: TODO(调用获取发送记录的成功总条数方法)
	 * @param @param userId
	 * @param @param smsRecordDTO
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
//	public int getDeduct(String userId, SmsRecordDTO smsRecordDTO, Long btime,
//			Long etime) {
//		SmsRecordDTO sdto = smsRecordRepository.getSmsSendDeductionCount(
//				smsRecordDTO, userId, btime, etime);
//		if (sdto != null) {
//			return sdto.getStatus();
//		} else {
//			return 0;
//		}
//	}

	/**
	 * 订单中心催付提醒效果分析--查询订单id以及短信消费条数 ZTK2017年7月10日下午2:56:16
	 */
	public ReminderNum findRecordTidAndSmsNum(String type, String userId,
			Date beginTime, Date endTime,Long taskId) {
		Criteria criteria = Criteria.where("userId").is(userId);
		criteria.and("status").is(2);
		if (type != null && !"".equals(type)) {
			criteria.and("type").is(type);
		}
		if(taskId != null && !"".equals(taskId)){
			criteria.and("taskId").is(taskId);
		}
		if (beginTime != null && endTime != null) {
			criteria.and("sendLongTime").gte(beginTime.getTime())
					.lte(endTime.getTime());
		}
		Aggregation aggregation = Aggregation.newAggregation(Aggregation
				.project("userId", "type", "status", "sendLongTime",
						"actualDeduction", "orderId","taskId"), Aggregation
				.match(criteria),
				Aggregation.group("userId").sum("actualDeduction").as("smsNum")
						.addToSet("orderId").as("tidList"));
		List<ReminderNum> reminderNums = smsRecordRepository.findTidList(
				userId, aggregation, ReminderNum.class);
		if (reminderNums != null && reminderNums.size() > 0) {
			return reminderNums.get(0);
		} else {
			return null;
		}
	}
	

	/**
	 * 首页统计数据用(催付数据) ZTK2017年7月24日下午6:02:44
	 */
	public ReminderNum findReminderNum(String type, String userId,
			Date beginTime, Date endTime, boolean isMonth) {
		// 催付效果默认前三天发送催付短信的效果,计算三天前的时间
		Date threeDaysAgo = DateUtils.nDaysAgo(2, beginTime);
		ReminderNum threeReminderNum = null;
		if (isMonth) {
			threeReminderNum = findRecordTidAndSmsNum(type, userId, beginTime,
					endTime,null);
		} else {
			threeReminderNum = findRecordTidAndSmsNum(type, userId,
					threeDaysAgo, endTime,null);
		}
		// 计算三天前到结束时间回款效果
		List<String> statusList = new ArrayList<String>();
		statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
		statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
		statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
		statusList.add(TradeStatusUtils.TRADE_FINISHED);
		statusList.add(TradeStatusUtils.TRADE_CLOSED);
		if (threeReminderNum != null && threeReminderNum.getTidList() != null
				&& !threeReminderNum.getTidList().isEmpty()) {
			ReminderNum sumReminderNum = tradeInfoService.sumReminderNum(
					userId, threeReminderNum.getTidList(), statusList,beginTime,endTime);
			return sumReminderNum;
		}
		return null;
	}

	/*
	 * 指定号码发送屏蔽多少天发送过的号码
	 */
	public List<String> querySmsSendRecord(String userId, Integer sendDay) {
		// 获取当前日期和前一天日期，根据日期查询订单数据
		Calendar calendar = Calendar.getInstance();
		Date todayDate = calendar.getTime();// 获取的是系统当前时间
		calendar.add(Calendar.DATE, -sendDay);
		Date lastDate = calendar.getTime(); // 得到屏蔽几天的日期
		Query query = new Query();
		query.addCriteria(Criteria.where("sendTime").gte(lastDate)
				.lte(todayDate));
		query.addCriteria(Criteria.where("status").is(2));
		query.addCriteria(Criteria.where("type").in("33", "34", "35"));
		List<String> list = findNumList(query, userId);
		return list;
	}

	/**
	 * @Description:查询出一定时间内短信发送的总量
	 * @author: jackstraw_yu
	 */
	public Long querySendCountByType(String userId, Map<String, Date> map,
			List<String> types) {
		Query query = new Query();
		query.addCriteria(Criteria.where("sendTime").lte(map.get("maxTime"))
				.gte(map.get("minTime")));
		if (types != null && !types.isEmpty()) {
			query.addCriteria(Criteria.where("type").in(types));
		}
		return smsRecordRepository.count(query, userId);
	}

	/**
	 * 短信群发页面-查询订单最后一次发送时间 滑静2017年7月13日上午10:48:17
	 */
	public List<SmsRecordDTO> findOrderLastsendTime(String userId, Date bTime,
			Date eTime) {
		Query query = new Query();
		if (bTime != null && !"".equals(bTime) && eTime != null
				&& !"".equals(eTime)) {
			query.addCriteria(Criteria.where("endTime")
					.gte(DateUtils.dateToLong(bTime))
					.lte(DateUtils.dateToLong(eTime)));
		}
		List<SmsRecordDTO> lastSendTimeList = smsRecordRepository.find(query,
				userId);
		return lastSendTimeList;
	}

	/**
	 * 手动订单提醒发送列表查询（分页）
	 */
	// @SuppressWarnings("unused")
	// public Pageination<SmsRecordDTO> findReminderSendList(Integer pageNo,
	// Query query,String userId) {
	// // 设置起始页
	// if (pageNo == null) {
	// pageNo = 1;
	// }
	// // 先设置每页显示的条数为3条
	// Integer currentRows = 5;
	// // 计算出起始行数
	// Integer startRows = (pageNo - 1) * currentRows;
	// // 查询数据总数
	// Integer totalCount = (int) smsRecordRepository.count(query, userId);
	// // 总页数
	// Integer totalPage = (int) Math.ceil(1.0 * totalCount / currentRows);
	// // 根据卖家条件查询的集合
	// Pageination<SmsRecordDTO> page = new Pageination<SmsRecordDTO>();
	// page.setPageNo(pageNo);
	// page.setPageSize(currentRows);
	// page.setTotalCount(totalCount);
	// Pageination<SmsRecordDTO> pagination = null;
	// if(userId != null && !"".equals(userId)){
	// pagination = smsRecordRepository.findPage(page, query, userId);
	// }
	// return pagination;
	// }

	public Pageination<SmsRecordDTO> findReminderSendList(Integer pageNo,
			Query query, String userId) {
		// 设置起始页
		if (pageNo == null) {
			pageNo = 1;
		}
		// 先设置每页显示的条数为3条
		Integer currentRows = 5;
		// 根据卖家条件查询的集合
		Pageination<SmsRecordDTO> page = new Pageination<SmsRecordDTO>();
		page.setPageNo(pageNo);
		page.setPageSize(currentRows);
		Pageination<SmsRecordDTO> pagination = null;
		if (userId != null && !"".equals(userId)) {
			pagination = smsRecordRepository.findPage(page, query, userId);
		}
		return pagination;
	}

	/**
	 * 根据id更新活动名称
	 */
	public void updateActivityNameById(Long id, String activityName,
			UserOperationLog log, String userId) {
		Query query = new Query();
		if (id != null && !"".equals(id)) {
			query.addCriteria(Criteria.where("_id").is(id));
		}
		Update update = new Update();
		if (activityName != null && !"".equals(activityName)) {
			update.set("activityName", activityName);
		}
		if (userId != null && !"".equals(userId)) {
			smsRecordRepository.update(query, update, userId);
			userOperationLogDao.save(log);
		}
	}

	/**
	 * 订单短信群发-根据订单id和卖家查询短信发送记录滑静2017年7月13日下午3:38:06
	 */
	public List<SmsRecordDTO> findSendRecordByOid(String userId, String tradeTid) {
		Query query = new Query();
		List<SmsRecordDTO> sendRecordList = null;
		if (tradeTid != null && !"".equals(tradeTid)) {
			query.addCriteria(Criteria.where("orderId").is(tradeTid));
			sendRecordList = smsRecordRepository.find(query, userId);
		} else {
			return null;
		}
		if (sendRecordList != null && !"".equals(sendRecordList)) {
			return sendRecordList;
		} else {
			return null;
		}
	}

	/**
	 * 添加错误手机号码的记录--营销中心--批量发短信记录
	 * 
	 * @author 滑静
	 * @data 2017年7月13日 下午7:43:10
	 */
	public void insertSmsSendRecord(SmsRecordDTO smsSendRecord) {
		smsRecordRepository.save(smsSendRecord, smsSendRecord.getUserId());
	}

	/**
	 * @Title: 根据订单编号和短信发送类型查询是否发送过短信
	 * @author zlp
	 * @return Boolean
	 * @throws
	 */
	public boolean findSmsRecordStatus(String tid, String type, String userId) {
		long startTimeSys = System.currentTimeMillis();
		try {
			Query query = new Query();
			if (ValidateUtil.isNotNull(tid) && ValidateUtil.isNotNull(type) && ValidateUtil.isNotNull(userId)) {
				query.addCriteria(Criteria.where("orderId").is(tid));
				query.addCriteria(Criteria.where("type").is(type));
				return smsRecordRepository.count(query, userId) > 0 ? true : false;
			} else {
				return false;
			}
		} finally {
			long endTimeSys = System.currentTimeMillis();
			if((endTimeSys-startTimeSys)>1000){
				this.logger.debug("根据订单号和类型查询，时间太长,卖家昵称："+userId+",tid："+tid+"，类型："+type+"，耗时："+(endTimeSys-startTimeSys)+"ms");
			}
		}
	}
	
	public boolean findSmsRecordStatus1(String buyerNick,  String userId,String type) {
		long startTimeSys = System.currentTimeMillis();
		try {
			Query query = new Query();
			Date myDate = new Date();
			String year = new SimpleDateFormat("yyyy").format(myDate);
			String month = new SimpleDateFormat("MM").format(myDate);
			String day = new SimpleDateFormat("dd").format(myDate);
			Date nowDayStart = null;
			Date nowDayEnd = null;
			try {
				nowDayStart = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").parse(year + "-" + month
						+ "-" + day + " 00:00:00");
				nowDayEnd = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").parse(year + "-" + month
						+ "-" + day + " 23:59:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Long startTime =nowDayStart.getTime();
			Long endtime = nowDayEnd.getTime();
			if (null != buyerNick && !"".equals(buyerNick) && null != type && !"".equals(type)
					&& null != userId && !"".equals(userId)) {
				try {
					if(!EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
						String sessionKey = this.judgeUserUtil.getUserTokenByRedis(userId);
						if(sessionKey == null){
							return true;
						}
						buyerNick = EncrptAndDecryptClient.getInstance().encrypt(buyerNick, EncrptAndDecryptClient.SEARCH, sessionKey);
					}
					query.addCriteria(Criteria.where("buyerNick").is(buyerNick));
					query.addCriteria(Criteria.where("type").is(type));
					query.addCriteria(Criteria.where("sendLongTime").lte(endtime).gte(startTime));
					return smsRecordRepository.count(query, userId) > 0 ? true : false;
				} catch (SecretException e) {
					e.printStackTrace();//sessionKey过期，默认短信不发送且短信已发送过
					return true;
				}
			} else {
				return false;
			}
		} finally {
			long endTimeSys = System.currentTimeMillis();
			if((endTimeSys-startTimeSys)>1000){
				this.logger.debug("根据买家卖家类型查询，时间太长,卖家昵称："+userId+",买家昵称："+buyerNick+"，类型："+type+"，耗时："+(endTimeSys-startTimeSys)+"ms");
			}
		}
	}
	/**
	 * 根据手机号码卖家昵称和类型来查询短信
	 * @author: wy
	 * @time: 2017年8月10日 上午10:38:39
	 * @param sellerNick
	 * @param phone
	 * @param type
	 * @return
	 */
	public boolean findSmsSellerNickAndPhone(String sellerNick,  String phone,String type) {
		long startTimeSys = System.currentTimeMillis();
		try {
			Query query = new Query();
			Date myDate = new Date();
			String year = new SimpleDateFormat("yyyy").format(myDate);
			String month = new SimpleDateFormat("MM").format(myDate);
			String day = new SimpleDateFormat("dd").format(myDate);
			Date nowDayStart = null;
			Date nowDayEnd = null;
			try {
				nowDayStart = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").parse(year + "-" + month
						+ "-" + day + " 00:00:00");
				nowDayEnd = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").parse(year + "-" + month
						+ "-" + day + " 23:59:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Long startTime =nowDayStart.getTime();
			Long endtime = nowDayEnd.getTime();
			if (ValidateUtil.isNotNull(sellerNick) &&ValidateUtil.isNotNull(phone) &&ValidateUtil.isNotNull(type)) {
				try {
					if(!EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
						String sessionKey = this.judgeUserUtil.getUserTokenByRedis(sellerNick);
						if(sessionKey == null){
							return true;
						}
						phone = EncrptAndDecryptClient.getInstance().encrypt(phone, EncrptAndDecryptClient.PHONE, sessionKey);
					}
					query.addCriteria(Criteria.where("recNum").is(phone));
					query.addCriteria(Criteria.where("type").is(type));
					query.addCriteria(Criteria.where("sendLongTime").lte(endtime).gte(startTime));
					return smsRecordRepository.count(query, sellerNick) > 0 ? true : false;
				} catch (SecretException e) {
					e.printStackTrace();//sessionKey过期，默认短信不发送且短信已发送过
					return true;
				}
			} else {
				return false;
			}
		} finally {
			long endTimeSys = System.currentTimeMillis();
			if((endTimeSys-startTimeSys)>1000){
				this.logger.debug("根据卖家昵称和手机号码查询，时间太长,卖家昵称："+sellerNick+",手机号码："+phone+"，类型："+type+"，耗时："+(endTimeSys-startTimeSys)+"ms");
			}
		}
	}
	/**
	 * 创建人：邱洋
	 * 
	 * @Title: findSmsRecordCount
	 * @Description: TODO(**模块下（如催付）查询该手机号在一定时间是否发送过短信)
	 * @param @param type
	 * @param @param userId
	 * @param @param phone
	 * @param @param etime
	 * @param @param btime
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public Long findSmsRecordCount(String type, String userId, String phone,
			Long etime, Long btime) {
		long startTimeSys = System.currentTimeMillis();
		try {
			Query query = new Query();
			if (null != phone && !"".equals(phone) && null != type
					&& !"".equals(type) && null != userId && !"".equals(userId)
					&& etime != null && !"".equals(etime) && btime != null
					&& !"".equals(btime)) {
				query.addCriteria(Criteria.where("recNum").is(phone));
				if (type != null && !"".equals(type)) {
					query.addCriteria(Criteria.where("type").is(type));
				}
				query.addCriteria(Criteria.where("status").is(2));
				query.addCriteria(Criteria.where("sendLongTime").lte(etime)
						.gte(btime));
				return smsRecordRepository.count(query, userId);
			} else {
				return 0L;
			}
		} finally {
			long endTimeSys = System.currentTimeMillis();
			if((endTimeSys-startTimeSys)>1000){
				this.logger.debug("根据卖家昵称和手机号码查询，时间太长,卖家昵称："+userId+",手机号码："+phone+"，类型："+type+"，耗时："+(endTimeSys-startTimeSys)+"ms");
			}
		}
	}

	/**
	 * @param like 
	 * 创建人：邱洋
	 * 
	 * @Title: findSmsRecordDTOList
	 * @Description: TODO(查询所有的发送记录)
	 * @param @param userId
	 * @param @return 设定文件
	 * @return List<SmsRecordDTO> 返回类型
	 * @throws
	 */
	public List<SmsRecordDTO> findSmsRecordDTOList(String userId,
			SmsRecordDTO smsRecordDTO, Long btime, Long etime, boolean like) {
		Query query = new Query();
		query = packageCriteriaParam(query, smsRecordDTO, btime, etime,like);
		return smsRecordRepository.find(query, userId);
	}

	/**
	 * 首页昨日发货订单数
	 * ZTK2017年8月10日下午5:43:15
	 */
	@Deprecated
	public long findTidCount(String userId,Long btime, Long etime){
		Criteria criteria = new Criteria("userId").is(userId);
		criteria.and("sendLongTime").gte(btime).lte(etime);
		criteria.and("status").is(2);
		criteria.and("type").is("6");
		long count = smsRecordRepository.count(new Query(criteria), userId);
		return count;
	}
	
	
	/**
	 * @param like 
	 * 创建人：邱洋
	 * 
	 * @Title: findSmsRecordSendSuccessNumber
	 * @Description: TODO(查询发送成功的实际扣费短信条数和扣除短信条数)
	 * @param @param userId
	 * @param @param smsRecordDTO
	 * @param @param btime
	 * @param @param etime
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public SmsRecordDTO findSmsSendDeductionCount(String userId,
			SmsRecordDTO smsRecordDTO, Long btime, Long etime, boolean like) {
		return smsRecordRepository.getSmsSendDeductionCount(smsRecordDTO,
				userId, btime, etime,like);
	}


	/**
	 * 创建人：邱洋
	 * 
	 * @Title: ConvertDate
	 * @Description: TODO(判断数据是否加密，如果加密则解密成明文数据)
	 * @param @param list
	 * @param @param userId
	 * @param @return 设定文件
	 * @return List<SmsRecordDTO> 返回类型
	 * @throws
	 */
	public List<SmsRecordDTO> ConvertDate(List<SmsRecordDTO> list, String userId) {
		if (list != null && list.size() > 0) {
			String token = cacheService.getJsonStr(
					RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE,
					RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY
							+ userId);
			List<String> phonelist = new ArrayList<String>();
			List<String> buyerlist = new ArrayList<String>();
			List<SmsRecordDTO> DecryptList = new ArrayList<SmsRecordDTO>();
			try {
				for (SmsRecordDTO sd : list) {
					phonelist.add(sd.getRecNum());
					buyerlist.add(sd.getBuyerNick());
				}
				Map<String, String> phoneMap = null;
				Map<String, String> buyerMap = null;

				if (phonelist != null && phonelist.size() > 0) {
					phoneMap = EncrptAndDecryptClient.getInstance()
							.decryptListData(phonelist,
									EncrptAndDecryptClient.PHONE, token);
				}
				if (buyerlist != null && buyerlist.size() > 0) {
					buyerMap = EncrptAndDecryptClient.getInstance()
							.decryptListData(buyerlist,
									EncrptAndDecryptClient.SEARCH, token);
				}
				for (SmsRecordDTO sDTO : list) {
					if (phoneMap != null&&null!=phoneMap.get(sDTO.getRecNum())) {
						sDTO.setRecNum(phoneMap.get(sDTO.getRecNum()));
					}
					if (buyerMap != null&&null!=buyerMap.get(sDTO.getBuyerNick())) {
						sDTO.setBuyerNick(buyerMap.get(sDTO.getBuyerNick()));
					}
					DecryptList.add(sDTO);
				}
			} catch (SecretException e) {
				e.printStackTrace();
				return null;
			}
			if (DecryptList != null && DecryptList.size() > 0) {
				return DecryptList;
			}
		}
		return null;
	}
	
	
	  /*
	    * 同步旧库数据到新库数据专用
	    */
	public List<SmsRecordDTO>  findMigrateRecordDataList(Query query, String userNickName,String objectId,int limit){
		if(null!=userNickName&&!"".equals(userNickName)&&null!=objectId&&!"".equals(objectId)){
			if(!"0".equals(objectId)){
				query.addCriteria(Criteria.where("_id").gt(new ObjectId(objectId)));
			}
			query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "_id")));  
			query.skip(0).limit(limit);
			return smsRecordRepository.find(query,userNickName);  
		}else{
			return null;
		}
	}
	
	
	/**
	 * 查询短信发送记录，
	 */
	public Pageination<SmsRecordDTO> findSendRecordList(Pageination<SmsRecordDTO> page,
			SmsRecordDTOVo srdv, String userId) {
		Query query = new Query();
		query = packageCriteriaParam(query, srdv);
		Pageination<SmsRecordDTO> list = smsRecordRepository.findPage(page, query, userId);
		List<SmsRecordDTO> convertDate = ConvertDate(list.getDatas(), userId);
		list.setDatas(convertDate);
		if(null == convertDate){
			list.setTotalCount(0);
		}
		return list;
	}

	/**
	 * 将SmsRecordDTOVo的参数封装到query
	 */
	private Query packageCriteriaParam(Query query, SmsRecordDTOVo srdv) {
		if (srdv != null) {
			if (null != srdv.getTaskName() && !"".equals(srdv.getTaskName())) {
				query.addCriteria(Criteria.where("taskName").is(
						srdv.getTaskName()));
			}
			if (null != srdv.getType()
					&& !"".equals(srdv.getType())) {
				String[] type = srdv.getType().split(",");
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < type.length; i++) {
					list.add(type[i]);
				}
				query.addCriteria(Criteria.where("type").in(list));
			}
			if (null != srdv.getStatus()) {
				query.addCriteria(Criteria.where("status").is(
						srdv.getStatus()));
			}
			if(null != srdv.getBeginTime() && null != srdv.getEndTime()){
				query.addCriteria(Criteria.where("sendLongTime")
						.gte(srdv.getBeginTime()).lte(srdv.getEndTime()));
			}else{
				if(null != srdv.getBeginTime()){
					query.addCriteria(Criteria.where("sendLongTime").gte(srdv.getBeginTime()));
				}
				if(null != srdv.getEndTime()){
					query.addCriteria(Criteria.where("sendLongTime").lte(srdv.getEndTime()));
				}
			}
			if (null != srdv.getRecNum() && !"".equals(srdv.getRecNum())
				&& null != srdv.getBuyerNick() && !"".equals(srdv.getBuyerNick())
				&& null != srdv.getOrderId() && !"".equals(srdv.getOrderId())) {
				Criteria regex;
				if(srdv.isLike()){
					regex = Criteria.where("recNum").regex("^.*" + srdv.getRecNum() + ".*$");
				}else{
					regex = Criteria.where("recNum").is(srdv.getRecNum());
				}
				query.addCriteria(new Criteria().orOperator(
						Criteria.where("buyerNick").regex(
								"^.*" + srdv.getBuyerNick() + ".*$"), Criteria
								.where("orderId").is(srdv.getOrderId()),regex));
			}
			if(null != srdv.getBuyerNick() && !"".equals(srdv.getBuyerNick())
				&& null != srdv.getOrderId() && !"".equals(srdv.getOrderId())
				&& (null == srdv.getRecNum()||"".equals(srdv.getRecNum()))){
				query.addCriteria(new Criteria().orOperator(
						Criteria.where("buyerNick").regex(
								"^.*" + srdv.getBuyerNick() + ".*$"), Criteria
								.where("orderId").is(srdv.getOrderId())));
			}
			
		}
		query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "_id")));
		return query;
	}
	
	/**
	 * 根据查询条件返回指定字段
	 * BasicDBObject:query  查询条件
     * BasicDBObject:fields 指定返回字段
	 * ztk2017年9月26日上午10:34:19
	 */
	public DBCursor findRecordFields(BasicDBObject query,BasicDBObject fields,String userName){
//		Criteria criteria = new Criteria("userId").is(userId);
//		criteria.and("orderId").is(orderId).and("type").is(type);
		DBCursor dbCursor = smsRecordRepository.findFields(query, fields, userName);
//		SmsRecordDTO smsRecordDTO = smsRecordRepositoryImpl.findOne(new Query(criteria), userId);
		return dbCursor;
	}
	
	/**
	 * 根据条件查询发送记录
	 * @Title: listSmsRecordByQuery 
	 * @param @return 设定文件 
	 * @return List<SmsRecordDTO> 返回类型 
	 * @throws
	 */
	public List<SmsRecordDTO> listSmsRecordByQuery(SmsRecordDTOVo smsRecordVO,String userId){
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String sessionKey = userInfoService.validateFindSessionKey(userId);
		Criteria criteria = new Criteria();
		String phone = smsRecordVO.getRecNum();
		Integer status = smsRecordVO.getStatus();
		try {
			if(EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
				criteria.and("recNum").is(phone);
			}else {
				criteria.and("recNum").is(decryptClient.encryptData(phone, EncrptAndDecryptClient.PHONE, sessionKey));
			}
		} catch (SecretException e) {
			e.printStackTrace();
		}
		criteria.and("status").is(status);
		List<SmsRecordDTO> recordDTOList = smsRecordRepository.find(new Query(criteria), userId);
		if(recordDTOList != null && !recordDTOList.isEmpty()){
			for (SmsRecordDTO smsRecordDTO : recordDTOList) {
				try {
					if(smsRecordDTO.getBuyerNick() != null && EncrptAndDecryptClient.isEncryptData(smsRecordDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
						smsRecordDTO.setBuyerNick(decryptClient.decryptData(smsRecordDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey));
					}
					if(smsRecordDTO.getRecNum() != null && EncrptAndDecryptClient.isEncryptData(smsRecordDTO.getRecNum(), EncrptAndDecryptClient.PHONE)){
						smsRecordDTO.setRecNum(decryptClient.decryptData(smsRecordDTO.getRecNum(), EncrptAndDecryptClient.PHONE, sessionKey));
					}
				} catch (SecretException e) {
					e.printStackTrace();
				}
			}
		}
		return recordDTOList;
	}
	
	/** 
	* @Description: TODO 会员短信群发,查询此次发送的成功量
	* @param  userId
	* @param  msgId
	* @param  status
	* @return Long    返回类型 
	* @author jackstraw_yu
	* @date 2018年3月15日 下午3:57:23
	*/
	public Long findSmsRecordCount(String userId, Long msgId, Integer status) {
		Query query = new Query();
		query.addCriteria(Criteria.where("msgId").is(msgId))
			 			 .addCriteria(Criteria.where("status").is(status));
		return smsRecordRepository.count(query,userId);
	}

	public List<SmsRecordDTO> findSmsRecordList(String userId, Long msgId, Integer status, Map<String, Object> map) {
		Query query = new Query();
		query.addCriteria(Criteria.where("msgId").is(msgId))
		 	 			 .addCriteria(Criteria.where("status").is(status));
		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "_id")));  
		query.skip((Integer)map.get("startRow")).limit((Integer)map.get("pageSize"));
		return smsRecordRepository.find(query,userId);  
	}
	
	/**
	 * 按照年/月/日统计消费账单
	 * @Title: limitAggConsumeList 
	 * @param  设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	public Map<String, Object> limitAggConsumeList(String userId,Integer pageNo,
			Date starTime,Date endTime,String dateType){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(pageNo == null){
			pageNo = 1;
		}
		Criteria criteria = new Criteria();
		criteria.and("status").is(2);
		criteria.and("userId").is(userId);
		if(starTime != null && endTime != null){
			criteria.and("sendLongTime").gte(starTime.getTime()).lte(endTime.getTime());
		}else if(starTime != null && endTime == null){
			criteria.and("sendLongTime").gte(starTime.getTime());
		}else if(starTime == null && endTime != null){
			criteria.and("sendLongTime").lte(endTime.getTime());
		}
		long count = this.smsRecordRepository.count(new Query(criteria), userId);
		Integer startRows = (pageNo - 1) * ConstantUtils.PAGE_SIZE_MIDDLE;
		Aggregation aggData = null;
		Aggregation aggCount = null;
		if("year".equals(dateType)){
			aggData = Aggregation.newAggregation(
					Aggregation.project("userId","status","actualDeduction","sendLongTime")
						.andExpression("sendTime").plus(28800000).extractYear().as("year"),
					Aggregation.match(criteria),
					Aggregation.group("year").first("year").as("date")
						.sum("actualDeduction").as("smsNum"),
					Aggregation.sort(new Sort(Direction.DESC,"year")),
					Aggregation.skip(startRows),
					Aggregation.limit(ConstantUtils.PAGE_SIZE_MIDDLE)
					);
			aggCount = Aggregation.newAggregation(
					Aggregation.project("userId","status","actualDeduction","sendLongTime")
					.andExpression("sendTime").plus(28800000).extractYear().as("year"),
					Aggregation.match(criteria),
					Aggregation.group("year").first("year").as("year")
					);
		}else if("month".equals(dateType)){
			aggData = Aggregation.newAggregation(
					Aggregation.project("userId","status","actualDeduction","sendLongTime")
					.andExpression("sendTime").plus(28800000).extractMonth().as("year"),
					Aggregation.match(criteria),
					Aggregation.group("year").first("sendLongTime").as("sendTime")
						.sum("actualDeduction").as("smsNum"),
					Aggregation.sort(new Sort(Direction.DESC,"sendTime")),
					Aggregation.skip(startRows),
					Aggregation.limit(ConstantUtils.PAGE_SIZE_MIDDLE)
					);
			aggCount = Aggregation.newAggregation(
					Aggregation.project("userId","status","actualDeduction","sendLongTime")
					.andExpression("sendTime").plus(28800000).extractMonth().as("year"),
					Aggregation.match(criteria),
					Aggregation.group("year").first("sendLongTime").as("sendTime")
					);
		}else if("day".equals(dateType)){
			aggData = Aggregation.newAggregation(
					Aggregation.project("userId","status","actualDeduction","sendLongTime")
						.andExpression("sendTime").plus(28800000).extractDayOfYear().as("year"),
					Aggregation.match(criteria),
					Aggregation.group("year").first("sendLongTime").as("sendTime")
						.sum("actualDeduction").as("smsNum"),
					Aggregation.sort(new Sort(Direction.DESC,"sendTime")),
					Aggregation.skip(startRows),
					Aggregation.limit(ConstantUtils.PAGE_SIZE_MIDDLE)
					);
			aggCount = Aggregation.newAggregation(
					Aggregation.project("userId","status","actualDeduction","sendLongTime")
					.andExpression("sendTime").plus(28800000).extractDayOfYear().as("year"),
					Aggregation.match(criteria),
					Aggregation.group("year").first("sendLongTime").as("sendTime")
					);
		}
		logger.info("aggData:" + aggData);
		logger.info("aggCount:" + aggCount);
		Aggregation aggSmsNum = Aggregation.newAggregation(
				Aggregation.project("userId","status","actualDeduction","sendLongTime"),
				Aggregation.match(criteria),
				Aggregation.group("userId").sum("actualDeduction").as("smsNum")
				);
		List<ReminderNum> dataList = smsRecordRepository
				.listAggregates(new Query(criteria), userId, aggData, ReminderNum.class);
		List<ReminderNum> dataCount = smsRecordRepository
				.listAggregates(new Query(criteria), userId, aggCount, ReminderNum.class);
		logger.info("dataList : "+  dataList.size() + "dataCount:"+dataCount.size());
		List<ReminderNum> smsNums = smsRecordRepository.
				listAggregates(new Query(criteria), userId, aggSmsNum, ReminderNum.class);
		Integer totalCount = 0;
		if(smsNums != null && !smsNums.isEmpty()){
			totalCount = smsNums.get(0).getSmsNum();
		}
		if(dataList != null && !dataList.isEmpty()){
			if("day".equals(dateType)){
				for (ReminderNum reminderNum : dataList) {
					reminderNum.setDate(DateUtils.dateToString(new Date(reminderNum.getSendTime()), DateUtils.DEFAULT_DATE_FORMAT));
				}
			}else if("month".equals(dateType)){
				for (ReminderNum reminderNum : dataList) {
					reminderNum.setDate(DateUtils.dateToString(new Date(reminderNum.getSendTime()), DateUtils.SHORT_DATE_FORMAT));
				}
			}
		}
		int totalPage = GetCurrentPageUtil.getTotalPage(dataCount.size(), ConstantUtils.PAGE_SIZE_MIDDLE);
		resultMap.put("dataList", dataList);
		resultMap.put("totalPage", totalPage);
		resultMap.put("totalCount", totalCount);
		return resultMap;
	}
	
	/**
	 * 按照年/月/日统计消费账单
	 * @Title: limitAggConsumeList 
	 * @param  设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	public List<ReminderNum> aggConsumeList(String userId,
			Date starTime,Date endTime,String dateType){
		Criteria criteria = new Criteria();
		criteria.and("status").is(2);
		criteria.and("userId").is(userId);
		if(starTime != null && endTime != null){
			criteria.and("sendLongTime").gte(starTime.getTime()).lte(endTime.getTime());
		}else if(starTime != null && endTime == null){
			criteria.and("sendLongTime").gte(starTime.getTime());
		}else if(starTime == null && endTime != null){
			criteria.and("sendLongTime").lte(endTime.getTime());
		}
		Aggregation aggData = null;
		if("year".equals(dateType)){
			aggData = Aggregation.newAggregation(
					Aggregation.project("userId","status","actualDeduction","sendLongTime")
						.andExpression("sendTime").plus(28800000).extractYear().as("year"),
					Aggregation.match(criteria),
					Aggregation.group("year").first("year").as("date")
						.sum("actualDeduction").as("smsNum"),
					Aggregation.sort(new Sort(Direction.DESC,"year"))
					);
		}else if("month".equals(dateType)){
			aggData = Aggregation.newAggregation(
					Aggregation.project("userId","status","actualDeduction","sendLongTime")
					.andExpression("sendTime").plus(28800000).extractMonth().as("year"),
					Aggregation.match(criteria),
					Aggregation.group("year").first("sendLongTime").as("sendTime")
						.sum("actualDeduction").as("smsNum"),
					Aggregation.sort(new Sort(Direction.DESC,"sendTime"))
					);
		}else if("day".equals(dateType)){
			aggData = Aggregation.newAggregation(
					Aggregation.project("userId","status","actualDeduction","sendLongTime")
						.andExpression("sendTime").plus(28800000).extractDayOfYear().as("year"),
					Aggregation.match(criteria),
					Aggregation.group("year").first("sendLongTime").as("sendTime")
						.sum("actualDeduction").as("smsNum"),
					Aggregation.sort(new Sort(Direction.DESC,"sendTime"))
					);
		}
		List<ReminderNum> dataList = smsRecordRepository
				.listAggregates(new Query(criteria), userId, aggData, ReminderNum.class);
		if(dataList != null && !dataList.isEmpty()){
			if("day".equals(dateType)){
				for (ReminderNum reminderNum : dataList) {
					reminderNum.setDate(DateUtils.dateToString(new Date(reminderNum.getSendTime()), DateUtils.DEFAULT_DATE_FORMAT));
				}
			}else if("month".equals(dateType)){
				for (ReminderNum reminderNum : dataList) {
					reminderNum.setDate(DateUtils.dateToString(new Date(reminderNum.getSendTime()), DateUtils.SHORT_DATE_FORMAT));
				}
			}
		}
		return dataList;
	}
	
	/**
	 * 短信账单详情
	 * @Title: listRecordByQuery 
	 * @param @param query
	 * @param @param userId
	 * @param @return 设定文件 
	 * @return List<SmsRecordDTO> 返回类型 
	 * @throws
	 */
	public List<SmsRecordDTO> listRecordByQuery(Query query,String userId,Integer pageNo){
		if(userId == null || "".equals(userId)){
			return null;
		}
		Integer startRows = (pageNo - 1) * ConstantUtils.PAGE_SIZE_MIDDLE;
		long l1 = System.currentTimeMillis();
		query.skip(startRows).limit(ConstantUtils.PAGE_SIZE_MIDDLE);
		List<SmsRecordDTO> recordDTOs = this.smsRecordRepository.find(query, userId);
		long l2 = System.currentTimeMillis();
		if((l2 - l1) > 1000){
			logger.info("!!!!!!>>>><<<<<>>>>查询账单详情慢，userId：" + userId + "~~时长为：" + (l2 - l1));
		}
		if(recordDTOs != null && !recordDTOs.isEmpty()){
			String sessionKey = userInfoService.validateFindSessionKey(userId);
			EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
			for (SmsRecordDTO smsRecordDTO : recordDTOs) {
				try {
					if(smsRecordDTO.getRecNum() != null && EncrptAndDecryptClient.isEncryptData(smsRecordDTO.getRecNum(), EncrptAndDecryptClient.PHONE)){
						smsRecordDTO.setRecNum(decryptClient.decryptData(smsRecordDTO.getRecNum(), EncrptAndDecryptClient.PHONE, sessionKey));
					}
					if(smsRecordDTO.getBuyerNick() != null && EncrptAndDecryptClient.isEncryptData(smsRecordDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
						smsRecordDTO.setBuyerNick(decryptClient.decryptData(smsRecordDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey));
					}
				} catch (SecretException e) {
					e.printStackTrace();
				}
			}
			long l3 = System.currentTimeMillis();
			if((l3 - l2) > 1000){
				logger.info("!!!!!!>>>><<<<<>>>>查询账单详情解密解密慢，userId：" + userId + "~~时长为：" + (l3 - l2));
			}
			return recordDTOs;
		}
		return null;
	}
	
	/**
	 * 账单详情总记录数
	 * @Title: countRecordByQuery 
	 * @param @param query
	 * @param @param userId
	 * @param @return 设定文件 
	 * @return Integer 返回类型 
	 * @throws
	 */
	public Integer countRecordByQuery(Query query,String userId){
		if(userId == null && "".equals(userId)){
			return null;
		}
		this.smsRecordRepository.count(query, userId);
		
		return null;
	}
}
