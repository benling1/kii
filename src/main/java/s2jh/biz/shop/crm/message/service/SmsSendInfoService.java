package s2jh.biz.shop.crm.message.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.SecretException;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.dao.SmsSendInfoDao;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;

@Service
public class SmsSendInfoService extends BaseService<SmsSendInfo, Long>{
	public static final Integer RANGE = 10000; //每次过期短信的条数
	
	@Autowired
	private SmsSendInfoDao smsSendInfoDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	
	@Override
	protected BaseDao<SmsSendInfo, Long> getEntityDao() {
		return smsSendInfoDao;
	}
	/**
	 * 查询今天发过的短信发现记录，
	 * @author: wy
	 * @time: 2017年9月8日 下午4:56:42
	 * @param sellerNick 卖家昵称
	 * @param type 类型
	 * @return 只返回手机号码和短信内容 <br>
	 *  key = phone <br>
	 *  key = content<br>
	 */
	public List<Map<String,String>> findNowSendHistory(String sellerNick,String type){
		if(ValidateUtil.isEmpty(sellerNick) || ValidateUtil.isEmpty(type)){
			return null;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sellerNick", sellerNick);
		map.put("type", type);
		SimpleDateFormat fomartDate = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		String startSendTime = fomartDate.format(nowDate);
		Date startDate = null;
		try {
			startDate = fomartDate.parse(startSendTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		map.put("startDate", startDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DATE, 1);
		map.put("endDate", cal.getTime());
		return this.myBatisDao.findList(SmsSendInfo.class.getName(), "findSellerHistory", map);
	}
	/**
	 * 创建保存两天的短信记录
	 * @author: wy
	 * @time: 2017年9月4日 下午5:03:40
	 * @param smsSendInfo
	 * @return true 保存成功 ，false 保存失败
	 */
	public boolean saveSmsTemporary(SmsSendInfo smsSendInfo){
		try {
			String buyerNick = smsSendInfo.getNickname();
			String phone = smsSendInfo.getPhone();
			String session = judgeUserUtil.getUserTokenByRedis(smsSendInfo.getUserId());
			if(!EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
				smsSendInfo.setNickname(EncrptAndDecryptClient.getInstance().encrypt(buyerNick, EncrptAndDecryptClient.SEARCH, session));
			}
			if(!EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
				smsSendInfo.setPhone(EncrptAndDecryptClient.getInstance().encrypt(phone, EncrptAndDecryptClient.PHONE, session));
			}
			Integer result = this.myBatisDao.execute(SmsSendInfo.class.getName(), "doCreateByMessage", smsSendInfo);
			if(result==1){
				return true;
			}
			return false;
		} catch (SecretException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 根据卖家昵称和买家昵称和类型  查询短信是否有发送记录
	 * @author: wy
	 * @time: 2017年9月4日 下午6:21:10
	 * @param sellerNick 卖家昵称
	 * @param buyerNick 买家昵称
	 * @param type 类型
	 * @return true：存在 ，false：不存在
	 */
	public boolean isExists(String sellerNick,String buyerNick,String type){
		if(ValidateUtil.isEmpty(sellerNick) || ValidateUtil.isEmpty(type)|| ValidateUtil.isEmpty(buyerNick)){
			return false;
		}
		Map<String,Object> map = new HashMap<String,Object>(8);
		map.put("sellerNick", sellerNick);
		try {
            if(!EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
                String sessionKey = this.judgeUserUtil.getUserTokenByRedis(sellerNick);
                buyerNick = EncrptAndDecryptClient.getInstance().encrypt(buyerNick, EncrptAndDecryptClient.SEARCH,sessionKey);
            }
        } catch (SecretException e) {
            e.printStackTrace();
            return true;
        }
		SimpleDateFormat fomartDate = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        String startSendTime = fomartDate.format(nowDate);
        Date startDate = null;
        try {
            startDate = fomartDate.parse(startSendTime);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        map.put("startDate", startDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DATE, 1);
        map.put("endDate", cal.getTime());
		map.put("buyerNick", buyerNick);
		map.put("type", type);
		int result = this.myBatisDao.findBy(SmsSendInfo.class.getName(), "findCountByNickAndType", map);
		if(result>0){
			return true;
		}
		return false;
	}
	/**
	 * 查询订单和类型  是否有发送记录
	 * @author: wy 
	 * @time: 2017年9月4日 下午6:17:34
	 * @param tid 订单号
	 * @param type 类型 
	 * @return true：存在。false 不存在
	 */
	public boolean isExists(Long tid,String type){
		if(ValidateUtil.isEmpty(tid) || ValidateUtil.isEmpty(type)){
			return false;
		}
		Map<String,Object> map = new HashMap<String,Object>(5);
		map.put("tid", tid);
		map.put("type", type);
		int result = this.myBatisDao.findBy(SmsSendInfo.class.getName(), "findCountByTidAndType", map);
		if(result>0){
			return true;
		}
		return false;
	}
	public int findSmsByRemoveCount(Date startDate){
		if(startDate ==null){
			return 0;
		}
		int count = this.myBatisDao.findBy(SmsSendInfo.class.getName(), "findByOnceDayCount", startDate);
		return count;
	}
	/**
	 * 查询超过两天的短信
	 * @author: wy
	 * @time: 2017年9月4日 下午5:05:12
	 * @return
	 */
	public List<Long> findSmsByRemove(int page,Date startDate){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("nowDate", startDate);
		map.put("lineSize", SmsSendInfoService.RANGE);
		List<Long> ids = this.myBatisDao.findList(SmsSendInfo.class.getName(), "findByOnceDay", map);
		if(ValidateUtil.isEmpty(ids)){
			return null;
		}
		return ids;
	}
	/**
	 * 删除指定的ID，如果删除的id个数超过限制，会分多次删除
	 * @author: wy
	 * @time: 2017年9月4日 下午6:00:10
	 * @param idsList
	 * @return true 删除成功，false 删除失败
	 */
	public boolean removeIds(List<Long> idsList) {
		if(ValidateUtil.isEmpty(idsList)){
			return true;
		}
		int length = idsList.size();
		Map<String,List<Long>> map = new HashMap<String,List<Long>>(5);
		if(length<=RANGE){
			map.put("idsList", idsList);
			this.myBatisDao.execute(SmsSendInfo.class.getName(), "removeByInvalid", map);
			return true;
		}
		int i = 0;
		while(i<length){
			int toIndex = i + RANGE;
			if(toIndex>length){
				toIndex = length;
			}
			List<Long> list = idsList.subList(i, toIndex);
			map.put("idsList", list);
			this.myBatisDao.execute(SmsSendInfo.class.getName(), "removeByInvalid", map);
			i = toIndex;
		}
		return true;
	}
	//根据卖家编号查询所有卖家发送信息
//	public List<SmsSendInfo> findSmsSendInfo(String userId,String isMember){
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("isMember", isMember);
//		List<SmsSendInfo> list =myBatisDao.findList(SmsSendInfo.class.getName(), "findAllSmsSendInfo", map);
//		if(list!=null&&list.size()>0){
//			return list;
//		}else{
//			return null;
//		}		
//	}
	
	//根据卖家条件查询卖家发送短信
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public List<SmsSendInfo> querySmsSendInfo(Map map){		
//		List<SmsSendInfo> list = myBatisDao.findList(SmsSendInfo.class.getName(), "querySmsSendInfo", map);
//		if(list!=null&&list.size()>0){
//			return list;
//		}else{
//			return null;
//		}	
//	}


	//根据短信群发记录的recordId与任务状态:status查询出短信详情总条数
//	public Integer findTotalCountByQuery(Map<String,Object> hashmap){
//		Long totalCount = myBatisDao.findBy(SmsSendInfo.class.getName(), "findTotalCountByQuery", hashmap);
//		Integer i = Integer.valueOf(totalCount.toString());
//		return i;
//		
//	}
	
	//根据输入的条件查询出短信详情列表---返回分页列表
//	public Pagination findSmsSendInfoPagination(String contextPath,Integer pageNo,Map<String,Object> hashmap) {
//		/*Integer recordId,String phone,Integer status,*/
//		
//		//取出参数
//		String beginTime =null;
//		String endTime = null;
//		Integer  type = null;
//		String userId = null;
//		//订单发送记录查询--详情的条件
//		Integer recordId = null;
//		String phone = null;
//		Integer status = null;
//		String nickname = null;
//		
//		if(hashmap!=null){
//			if(hashmap.get("beginTime")!=null){
//				beginTime = (String) hashmap.get("beginTime");
//			}
//			if(hashmap.get("endTime")!=null){
//				endTime = (String) hashmap.get("endTime");
//			}
//			if(hashmap.get("type")!=null){
//			type = (Integer) hashmap.get("type");
//			}
//			if(hashmap.get("userId")!=null){
//				userId = (String) hashmap.get("userId");
//			}
//			if(hashmap.get("recordId")!=null){
//				recordId = (Integer) hashmap.get("recordId");
//			}
//			if(hashmap.get("phone")!=null){
//				phone = (String) hashmap.get("phone");
//			}
//			if(hashmap.get("status")!=null){
//				status = (Integer) hashmap.get("status");
//			}
//			if(hashmap.get("nickname")!=null){
//				nickname = (String) hashmap.get("nickname");
//			}
//			
//		}
//		
//		
//		//先设置每页显示的条数为5条
//		Integer currentRows = 5;
//		//计算出起始行数
//		Integer startRows = (pageNo-1)*currentRows;
//
//		//调用dao层查询出每页显示的list<SmsSendRecord>集合
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("startRows",startRows);
//		map.put("currentRows", currentRows);
//		map.put("recordId", recordId);
//		map.put("status", status);
//		map.put("phone", phone);
//		map.put("beginTime", beginTime);
//		map.put("endTime", endTime);
//		map.put("type", type);
//		map.put("userId", userId);
//		map.put("nickname", nickname);
//		
//		//获取出总条数
//		Integer totalCount = findTotalCountByQuery(map);
//		List<SmsSendInfo> smsSendInfoList = myBatisDao.findLimitList(SmsSendInfo.class.getName(), "findLimitList", map, currentRows);
//		
//		
//		//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
//		//smsSendInfoList:每页显示的list集合或者当前页显示的list集合
//		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, smsSendInfoList);
//		
//		StringBuilder params = new StringBuilder();
//		if(recordId!=null){
//			params.append("&recordId=").append(recordId);
//		}
//		if(status!=null){
//			params.append("&status=").append(status);
//		}
//		if(phone!=null){
//			params.append("&phone=").append(phone);
//		}
//		if(beginTime!=null){
//			params.append("&beginTime=").append(beginTime);
//		}
//		if(endTime!=null){
//			params.append("&endTime=").append(endTime);
//		}
//		if(type!=null){
//			params.append("&type=").append(type);
//		}
//		if(userId!=null){
//			params.append("&userId=").append(userId);
//		}
//		if(nickname!=null){
//			params.append("&nickname=").append(nickname);
//		}
//		
//		//拼接分页的后角标中的跳转路径与查询的条件
//		pagination.pageView(contextPath, params.toString());
//		
//		return pagination;
//	}

	
	//根据时间获取已经发送过的用户信息
//	public List<String> querySmsSendRecord(String userId,Integer sendDay){
//		//获取当前日期和前一天日期，根据日期查询订单数据
//		Calendar calendar = Calendar.getInstance();
//		Date todayDate = calendar.getTime();//获取的是系统当前时间
//		calendar.add(Calendar.DATE, -sendDay);    
//		Date  lastDate= calendar.getTime();  //得到屏蔽几天的日期
//		Query query = new Query();
//		query.addCriteria(Criteria.where("sendTime").gte(lastDate).lte(todayDate));
//		query.addCriteria(Criteria.where("status").is(2));
//		query.addCriteria(Criteria.where("type").in("33","34","35"));
//		List<String> list = smsRecordService.findNumList(query, userId);
//		return list;
//	}

	//传入数组id 查询出list<SmsSendInfo>集合
//	public List<SmsSendInfo> getSmsSendInfoList(Integer[] ids) {
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("ids", ids);
//		List <SmsSendInfo> list = myBatisDao.findList(SmsSendInfo.class.getName(), "getSmsSendInfoList", map);
//		return list;
//	}
	
	
//	//根据卖家编号查询所有卖家发送信息
//	public List<SmsSendInfo> findSmsSendInfo(String userId,String isMember){
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("isMember", isMember);
//		List<SmsSendInfo> list =myBatisDao.findList(SmsSendInfo.class.getName(), "findAllSmsSendInfo", map);
//		if(list!=null&&list.size()>0){
//			return list;
//		}else{
//			return null;
//		}		
//	}
//	
//	//根据卖家条件查询卖家发送短信
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public List<SmsSendInfo> querySmsSendInfo(Map map){		
//		List<SmsSendInfo> list = myBatisDao.findList(SmsSendInfo.class.getName(), "querySmsSendInfo", map);
//		if(list!=null&&list.size()>0){
//			return list;
//		}else{
//			return null;
//		}	
//	}
//
//
//	//根据短信群发记录的recordId与任务状态:status查询出短信详情总条数
//	public Integer findTotalCountByQuery(Map<String,Object> hashmap){
//		Long totalCount = myBatisDao.findBy(SmsSendInfo.class.getName(), "findTotalCountByQuery", hashmap);
//		Integer i = Integer.valueOf(totalCount.toString());
//		return i;
//		
//	}
//	
//	//根据输入的条件查询出短信详情列表---返回分页列表
//	public Pagination findSmsSendInfoPagination(String contextPath,Integer pageNo,Map<String,Object> hashmap) {
//		/*Integer recordId,String phone,Integer status,*/
//		
//		//取出参数
//		String beginTime =null;
//		String endTime = null;
//		Integer  type = null;
//		String userId = null;
//		//订单发送记录查询--详情的条件
//		Integer recordId = null;
//		String phone = null;
//		Integer status = null;
//		String nickname = null;
//		
//		if(hashmap!=null){
//			if(hashmap.get("beginTime")!=null){
//				beginTime = (String) hashmap.get("beginTime");
//			}
//			if(hashmap.get("endTime")!=null){
//				endTime = (String) hashmap.get("endTime");
//			}
//			if(hashmap.get("type")!=null){
//			type = (Integer) hashmap.get("type");
//			}
//			if(hashmap.get("userId")!=null){
//				userId = (String) hashmap.get("userId");
//			}
//			if(hashmap.get("recordId")!=null){
//				recordId = (Integer) hashmap.get("recordId");
//			}
//			if(hashmap.get("phone")!=null){
//				phone = (String) hashmap.get("phone");
//			}
//			if(hashmap.get("status")!=null){
//				status = (Integer) hashmap.get("status");
//			}
//			if(hashmap.get("nickname")!=null){
//				nickname = (String) hashmap.get("nickname");
//			}
//			
//		}
//		
//		
//		//先设置每页显示的条数为5条
//		Integer currentRows = 5;
//		//计算出起始行数
//		Integer startRows = (pageNo-1)*currentRows;
//
//		//调用dao层查询出每页显示的list<SmsSendRecord>集合
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("startRows",startRows);
//		map.put("currentRows", currentRows);
//		map.put("recordId", recordId);
//		map.put("status", status);
//		map.put("phone", phone);
//		map.put("beginTime", beginTime);
//		map.put("endTime", endTime);
//		map.put("type", type);
//		map.put("userId", userId);
//		map.put("nickname", nickname);
//		
//		//获取出总条数
//		Integer totalCount = findTotalCountByQuery(map);
//		List<SmsSendInfo> smsSendInfoList = myBatisDao.findLimitList(SmsSendInfo.class.getName(), "findLimitList", map, currentRows);
//		
//		
//		//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
//		//smsSendInfoList:每页显示的list集合或者当前页显示的list集合
//		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, smsSendInfoList);
//		
//		StringBuilder params = new StringBuilder();
//		if(recordId!=null){
//			params.append("&recordId=").append(recordId);
//		}
//		if(status!=null){
//			params.append("&status=").append(status);
//		}
//		if(phone!=null){
//			params.append("&phone=").append(phone);
//		}
//		if(beginTime!=null){
//			params.append("&beginTime=").append(beginTime);
//		}
//		if(endTime!=null){
//			params.append("&endTime=").append(endTime);
//		}
//		if(type!=null){
//			params.append("&type=").append(type);
//		}
//		if(userId!=null){
//			params.append("&userId=").append(userId);
//		}
//		if(nickname!=null){
//			params.append("&nickname=").append(nickname);
//		}
//		
//		//拼接分页的后角标中的跳转路径与查询的条件
//		pagination.pageView(contextPath, params.toString());
//		
//		return pagination;
//	}
//
//	
//	//根据时间获取已经发送过的用户信息
//	public List<String> querySmsSendRecord(String userId,Integer sendDay){
//		//获取当前日期和前一天日期，根据日期查询订单数据
//		Calendar calendar = Calendar.getInstance();
//		Date todayDate = calendar.getTime();//获取的是系统当前时间
//		calendar.add(Calendar.DATE, -sendDay);    
//		Date  lastDate= calendar.getTime();  //得到屏蔽几天的日期
//		Query query = new Query();
//		query.addCriteria(Criteria.where("sendTime").gte(lastDate).lte(todayDate));
//		query.addCriteria(Criteria.where("status").is(2));
//		query.addCriteria(Criteria.where("type").in("33","34","35"));
//		List<String> list = smsRecordService.findNumList(query, userId);
//		return list;
//	}
//
//	//传入数组id 查询出list<SmsSendInfo>集合
//	public List<SmsSendInfo> getSmsSendInfoList(Integer[] ids) {
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("ids", ids);
//		List <SmsSendInfo> list = myBatisDao.findList(SmsSendInfo.class.getName(), "getSmsSendInfoList", map);
//		return list;
//	}
//	
//	
//	/**
//	* @Title: querySmsSendRecord
//	* @Description: (根据时间获取已经发送过的手机列表)
//	* @param   userId
//	* @param   sendTime
//	* @param    参数
//	* @return List<String>    返回类型
//	* @author:jackstraw_yu
//	* @throws
//	*/
//	public List<String> querySmsSendPhoneNums(String userId,Integer sendTime){
//		//获取当前日期和前一天日期，根据日期查询订单数据
//		Calendar calendar = Calendar.getInstance();//获取的是系统当前时间
//		String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
//        calendar.add(Calendar.DATE, -sendTime);    //得到屏蔽几天的日期
//        String  lastDate= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());   
//		
//        Map<String,Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("lastDate",lastDate);
//		map.put("todayDate",todayDate);
//		
//		List <String> list = myBatisDao.findList(SmsSendInfo.class.getName(), "querySmsSendPhoneNums", map);
//		return list;
//	}
//
//	/**
//	* @Title: queryExpediting
//	* @Description: (后台管理设置,获得催付效果即查询出当前前一天的催付短信发送数量)
//	* @param   userId
//	* @param   bTime
//	* @param      参数
//	* @return Long    返回类型
//	* @author:jackstraw_yu
//	* @throws
//	*/
//	public Long queryExpediting(String userId, String currentTime) {
//		HashMap<String, Object> hashMap = new HashMap<String,Object>();
//		hashMap.put("userId", userId);
//		hashMap.put("currentTime", currentTime);
//		return myBatisDao.findBy(SmsSendInfo.class.getName(), "queryExpediting", hashMap);
//	}
//
//	/**
//	* @Title: querySendConut
//	* @Description: (后台管理设置,查询出当前前一天的短信发送总数量)
//	* @param   userId
//	* @param   bTime
//	* @param      参数
//	* @return Long    返回类型
//	* @author:jackstraw_yu
//	* @throws
//	*/
//	public Long querySendCount(String userId, String currentTime) {
//		HashMap<String, Object> hashMap = new HashMap<String,Object>();
//		hashMap.put("userId", userId);
//		hashMap.put("currentTime", currentTime);
//		return myBatisDao.findBy(SmsSendInfo.class.getName(), "querySendCount", hashMap);
//	}
//
//	
	/**
	* @Title: querySmsSendRecord
	* @Description: (根据时间获取已经发送过的手机列表)
	* @param   userId
	* @param   sendTime
	* @param    参数
	* @return List<String>    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
//	public List<String> querySmsSendPhoneNums(String userId,Integer sendTime){
//		//获取当前日期和前一天日期，根据日期查询订单数据
//		Calendar calendar = Calendar.getInstance();//获取的是系统当前时间
//		String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
//        calendar.add(Calendar.DATE, -sendTime);    //得到屏蔽几天的日期
//        String  lastDate= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());   
//		
//        Map<String,Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("lastDate",lastDate);
//		map.put("todayDate",todayDate);
//		
//		List <String> list = myBatisDao.findList(SmsSendInfo.class.getName(), "querySmsSendPhoneNums", map);
//		return list;
//	}

	/**
	* @Title: queryExpediting
	* @Description: (后台管理设置,获得催付效果即查询出当前前一天的催付短信发送数量)
	* @param   userId
	* @param   bTime
	* @param      参数
	* @return Long    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
//	public Long queryExpediting(String userId, String currentTime) {
//		HashMap<String, Object> hashMap = new HashMap<String,Object>();
//		hashMap.put("userId", userId);
//		hashMap.put("currentTime", currentTime);
//		return myBatisDao.findBy(SmsSendInfo.class.getName(), "queryExpediting", hashMap);
//	}

	/**
	* @Title: querySendConut
	* @Description: (后台管理设置,查询出当前前一天的短信发送总数量)
	* @param   userId
	* @param   bTime
	* @param      参数
	* @return Long    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
//	public Long querySendCount(String userId, String currentTime) {
//		HashMap<String, Object> hashMap = new HashMap<String,Object>();
//		hashMap.put("userId", userId);
//		hashMap.put("currentTime", currentTime);
//		return myBatisDao.findBy(SmsSendInfo.class.getName(), "querySendCount", hashMap);
//	}

	
	/***
	 *保存系统的短信发送 
	 * 
	 * **********************请勿注释掉该方法,谢谢合作!************************
	 * @Description:保存系统的短信验证码发送
	 * @author jackstraw_yu
	 **/
	public void saveSystemSms(SmsSendInfo sms) {
		smsSendInfoDao.save(sms);
	}

}
