package s2jh.biz.shop.crm.message.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.vo.MemberCriteriaVo;
import s2jh.biz.shop.crm.message.dao.MsgSendRecordDao;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class MsgSendRecordService extends BaseService<MsgSendRecord, Long> {

	@Autowired
	private MsgSendRecordDao msgSendRecordDao;
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Autowired
	private SmsRecordService smsRecordService;
	
	@Override
	protected BaseDao<MsgSendRecord, Long> getEntityDao() {
		return msgSendRecordDao;
	}

	private static final Log logger = LogFactory.getLog(MsgSendRecordService.class);

	/** 
	* @Title: sendRecordPagination 
	* @Description:  (这里用一句话描述这个方法的作用) 
	* @param   contextPath
	* @param   pageNo
	* @param   map
	* @param      设定文件 
	* @return Pagination    返回类型 
	* @throws
	* @date 2017年4月24日 上午11:06:11 
	* @author jackstraw_yu 
	*/
	public Pagination sendRecordPagination(String contextPath, Integer pageNo, Map<String, Object> hashmap) {
		StringBuilder params = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginTime = null;
		Date endTime = null;
		String type = null;
		String userId = null;
		String activityName = null;
		Boolean isSent = null;
		if (hashmap != null) {
			if (hashmap.get("beginTime") != null) {
				beginTime = (Date) hashmap.get("beginTime");
				params.append("&beginTime=").append(dateFormat.format(beginTime));
			}
			if (hashmap.get("endTime") != null) {
				endTime = (Date) hashmap.get("endTime");
				params.append("&endTime=").append(dateFormat.format(endTime));
			}
			if (hashmap.get("type") != null) {
				type = (String) hashmap.get("type");
				params.append("&type=").append(type);
			}
			if (hashmap.get("userId") != null) {
				userId = (String) hashmap.get("userId");
				params.append("&userId=").append(userId);
			}
			if (hashmap.get("activityName") != null) {
				activityName = (String) hashmap.get("activityName");
				params.append("&activityName=").append(activityName);
			}
			if (hashmap.get("isSent") != null) {
				isSent = (boolean) hashmap.get("isSent");
				params.append("&isSent=").append(isSent);
			}
		}
		Integer currentRows = ConstantUtils.PAGE_SIZE_MIN;
		Integer startRows = (pageNo - 1) * currentRows;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("activityName", activityName);
		map.put("isSent", isSent);
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		map.put("userId", userId);
		Integer totalCount = queryMsgCount(map);
		List<MsgSendRecord> smsSendRecordList = null;
		if(isSent==true){
			smsSendRecordList = myBatisDao.findLimitList(
					MsgSendRecord.class.getName(), "sendRecordPagination", map,
					currentRows);
		}else{
			smsSendRecordList = myBatisDao.findLimitList(
					MsgSendRecord.class.getName(), "toSendRecordPagination", map,
					currentRows);
		}
		Pagination pagination = new Pagination(pageNo, currentRows, totalCount,
				smsSendRecordList);
		pagination.pageView(contextPath, params.toString());
		return pagination;
	}
	/** 
	 * @Title: queryLastSendList 
	 * @author jackstraw_yu 
	 */
	public List<MsgSendRecord> queryLastSendList(MemberCriteriaVo memberCriteriaVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", memberCriteriaVo.getType());
		map.put("beginTime", memberCriteriaVo.getSendStartTime());
		map.put("endTime", memberCriteriaVo.getSendEndTime());
		map.put("userId", memberCriteriaVo.getUserId());
		List<MsgSendRecord> findList =  myBatisDao.findList(MsgSendRecord.class.getName(), "queryLastSendList", map);
		return findList;
	}

	/** 
	* @Title: queryMsgCount 
	* @Description:  (获取短信发送总记录的总条数) 
	* @param   hashmap
	* @return Integer    返回类型 
	* @throws
	* @date 2017年4月24日 上午11:19:41 
	* @author jackstraw_yu 
	*/
	public Integer queryMsgCount(Map<String, Object> hashmap) {
		Long totalCount = myBatisDao.findBy(MsgSendRecord.class.getName(),
				"queryMsgCount", hashmap);
		Integer i = Integer.valueOf(totalCount.toString());
		return i;

	}


	/** 
	* @Title: deleteSendRecord 
	* @Description:  (将msgSendReocrd与对应的smsSendReocrd隐藏起来) 
	* @date 2017年4月25日 下午3:02:08 
	* @author jackstraw_yu 
	*/
	public boolean deleteSendRecord(Map<String, Object> map) {
		boolean flag = false;
		if((String)map.get("userId")==null || "".equals((String)map.get("userId"))) return flag;
		try {
			//将msgReocrd隐藏
			myBatisDao.execute(MsgSendRecord.class.getName(), "hideMsgSendRecord", map);
			//myBatisDao.execute(SmsSendRecord.class.getName(), "hideSmsSendRecord", map);
			smsRecordService.hideSmsRecord(map);
			//将smsSendReocrd隐藏
			flag =true; 
		} catch (Exception e) {
			logger.error("删除短信发送记录失败!!^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
			flag = false;
		}
		return flag;
	}


	/** 
	* @Title: saveMsg 
	* @Description:  (会员短信群发:保存发送总记录) 
	* @date 2017年4月27日 下午6:23:34 
	* @author jackstraw_yu 
	*/
	public void saveMsg(MsgSendRecord msg) {
		myBatisDao.execute(MsgSendRecord.class.getName(), "saveMsg", msg);
	}


	/** 
	* @Title: updateMsg 
	* @Description:  (会员短信群发:更新总记录的发送状态) 
	* @param @param msg    设定文件 
	* @return void    返回类型 
	* @throws
	* @date 2017年4月28日 上午9:45:09 
	* @author jackstraw_yu 
	*/
	public void updateMsg(MsgSendRecord msg) {
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("msg", msg);
		myBatisDao.execute(MsgSendRecord.class.getName(), "updateMsg", hashMap);
	}


	/** 
	* @Title: querySmsRecordContent 
	* @Description: TODO(短信群发>>再次发送,查询出发送总记录的的模板内容) 
	* @param   userId
	* @param   recorId
	* @param   type
	* @return String    返回类型 
	* @throws
	* @date 2017年5月5日 下午4:26:20 
	* @author jackstraw_yu 
	*/
	public String querySmsRecordContent(String userId, Integer recordId, String type) {
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("userId", userId);
		hashMap.put("recordId", recordId);
		hashMap.put("type", type);
		String  content  = myBatisDao.findBy(MsgSendRecord.class.getName(), "querySmsRecordContent", hashMap);
		return content;
	}
	
	/**
	 * 保存短信群发总记录数据
	 * @author Administrator_HL
	 * @data 2017年4月25日 下午2:11:49
	 */
	public Long insertMsgSendRecord(MsgSendRecord msgSendRecord){
		myBatisDao.execute(MsgSendRecord.class.getName(), "insertMsgSendRecord", msgSendRecord);
		return msgSendRecord.getId();
		
	}
	
	/**
	 * 根据id更新总记录
	 * ZTK2017年5月4日下午6:38:26
	 */
	public void updateMsgRecordById(MsgSendRecord msgRecord){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("succeedCount", msgRecord.getSucceedCount());
		map.put("failedCount", msgRecord.getFailedCount());
		map.put("repeatCount", msgRecord.getRepeatCount());
		map.put("blackCount", msgRecord.getBlackCount());
		map.put("status", msgRecord.getStatus());
		map.put("isSent", msgRecord.getIsSent());
		map.put("id", msgRecord.getId());
		myBatisDao.execute(MsgSendRecord.class.getName(), "updateMsgRecordById", map);
	}
	
	/**
	 * 根据MsgId更新短信发送成功数量和短信发送失败数量
	 * 滑静2017年5月9日下午6:38:26
	 */
	public void updateMsgRecordByMsgId(MsgSendRecord msgRecord){
		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("status", msgRecord.getStatus());
		map.put("isSent", msgRecord.getIsSent());
		map.put("id", msgRecord.getId());
		map.put("failedCount", msgRecord.getFailedCount());
		map.put("succeedCount", msgRecord.getSucceedCount());
		myBatisDao.execute(MsgSendRecord.class.getName(), "updateMsgRecordByMsgId", map);
	}


	/** 
	* @Title: removeMsgSchedule 
	* @Description:  (删除定时总记录与详细(要发送的短信,保存错误的记录)
	* 存在情况:定时还没有完全保存进去客户点击删除操作!!!,切记切记!!
	* ) 
	* @date 2017年5月6日 上午11:53:54 
	* @author jackstraw_yu 
	 * @throws Exception 
	*/
	@Transactional//(propagation=Propagation.REQUIRED) 
	public void removeMsgSchedule(String userId, Integer recordId, String type){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId",userId);
		map.put("recordId", recordId);
		map.put("type", type);
		int result = myBatisDao.execute(
				SmsSendInfo.class.getName() + "Schedule",
				"removeSmsSchedule", map);
		myBatisDao.execute(MsgSendRecord.class.getName(), 
				"removeMsgSchedule", map);
		/*myBatisDao.execute(
				SmsSendRecord.class.getName(),
				"removeSmsRecord", map);*/
		//删除mongo中的定时记录
		smsRecordService.removeSmsRecord(map);
	}
	
	
		


	/**
	 *发送记录-查询出总记录
	 */
	public MsgSendRecord queryMsgSendRecord(String userId,Integer recordId, String type) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId",userId);
		map.put("recordId", recordId);
		map.put("type", type);
		return myBatisDao.findBy(MsgSendRecord.class.getName(), 
				"queryMsgSendRecord", map);
	}
	
	/**
	 * 会员营销效果分析查询发送总记录
	 * ZTK2017年6月15日下午4:26:10
	 */
	private Pagination findMsgRecordForMember(Map<String, Object> map,Integer pageNo){
		Integer currentRows = 10;
		Integer startRows = (pageNo - 1) * currentRows;
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		List<MsgSendRecord> msgSendRecords = myBatisDao.findList(MsgSendRecord.class.getName(), "findMsgRecordByType", map);
		return null;
	}
	
	/**
	 * 通过userId、msgId、type查询短信发送成功总数
	 * @author 滑静
	 * @data 2017年5月9日 下午16:43:10
	 */
	//2成功发送客户数//此方法有会员昵称分组改为通过手机号分组
	public Integer getSuccessCustomByMsgId(String userId,Long msgId,String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("msgId", String.valueOf(msgId));
		map.put("type", type);
		Long l = myBatisDao.findBy(MsgSendRecord.class.getName(),
		"getSuccessCustom", map);
		Integer i = Integer.valueOf(l.toString());
		return i;
	}
	
	/**
	 * 通过userId、msgId、type查询短信发送总数
	 * @author 滑静
	 * @data 2017年5月9日 下午16:43:10
	 */
	// 1目标发送客户总数//此方法有通过会员分组改为查询总计的的总条数
	public Integer getTotalCustomByMsgId(String userId,Long msgId,String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("msgId", String.valueOf(msgId));
		map.put("type", type);
		Long l = myBatisDao.findBy(MsgSendRecord.class.getName(),
				"getTotalCustom", map);
		Integer i = Integer.valueOf(l.toString());
		return i;
	}
	
	/**
	 * 查询时间范围内的会员发送的msgId
	 * ZTK2017年7月25日下午3:33:30
	 */
	public List<Long> findMemberMsgIdByTime(String userId,Date beginTime,Date endTime){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("type", "33");
		map.put("status", "5");
		List<Long> msgIds = myBatisDao.findList(MsgSendRecord.class.getName(), "findMsgIdByTime", map);
		if(msgIds != null && !msgIds.isEmpty()){
			return msgIds;
		}
		return null;
	}
	
	/**
	 * 查询时间范围内的msgId
	 * ZTK2017年7月25日下午3:33:30
	 */
	public List<Long> findMsgIdByTime(String userId,Date beginTime,Date endTime,Long msgId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("status", "5");
		map.put("msgId", msgId);
		map.put("userId", userId);
		List<Long> msgIds = myBatisDao.findList(MsgSendRecord.class.getName(), "findMsgIdByTime", map);
		if(msgIds != null && !msgIds.isEmpty()){
			return msgIds;
		}
		return null;
	}
}
