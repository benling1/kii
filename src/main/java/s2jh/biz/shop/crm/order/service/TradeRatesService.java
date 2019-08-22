package s2jh.biz.shop.crm.order.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.order.dao.TradeRatesDao;
import s2jh.biz.shop.crm.order.entity.RatedVo;
import s2jh.biz.shop.crm.order.entity.TradeRates;
import s2jh.biz.shop.crm.other.entity.TaskNode;
import s2jh.biz.shop.crm.other.service.TaskNodeService;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.tmc.service.RateMonitoringPacifyService;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.SecretException;
import com.taobao.api.domain.TradeRate;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderatesGetResponse;

@Service
@Transactional
public class TradeRatesService extends BaseService<TradeRates, Long> {
	
	private static Logger logger = LoggerFactory.getLogger(TradeRatesService.class);
	@Autowired
	private TradeRatesDao tradeRatesDao;
	
	@Autowired
	private MyBatisDao mybatisDao;
	
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private OrderRateCareSetupService orderRateCareSetupService;
	
	@Autowired
	private RateMonitoringPacifyService rateMonitoringPacifyService;
	
	@Autowired
	private TaskNodeService taskNodeService;
	
	@Autowired
	private VipMemberService vipMemberService;
	
	@Override
	protected BaseDao<TradeRates, Long> getEntityDao() {
		return tradeRatesDao;
	}
	
	/**
	 * 根据卖家编号和条件获取评价信息
	 * @param map
	 * @param userId
	 * @return
	 */
	public List<String> queryTradeRatesNicks(Map<String,Object> map,String userId){
		map.put("userId", userId);
		List<String> list = mybatisDao.findList(TradeRates.class.getName(), "queryTradeRates", map);
		return list;
	}
	
	/**
	 * 根据多条件查询评价列表
	 * 中差评查看使用---请勿修改
	 */
	public Pagination findTradeRatesByQuery(Integer pageNo,Map<String, Object> map){
		//设置每页显示的条数为5条
		Integer currentRows = 5;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;		
		//查询集合中的总个数
		int totalCount = mybatisDao.findBy(TradeRates.class.getName(), "findTradeRatesToCount", map);
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		List<TradeRates> findList = mybatisDao.findList(TradeRates.class.getName(), "findTradeRatesToList", map);
		if(null !=findList &&findList.size()>0){
			for (TradeRates tradeRates : findList) {
				String nick = tradeRates.getNick();
				String userId = (String) map.get("ratedNick");
				String session = userInfoService.findUserInfoTokens(userId);
				try {
					if(EncrptAndDecryptClient.isEncryptData(nick, EncrptAndDecryptClient.SEARCH)){
						String decrypt = EncrptAndDecryptClient.getInstance().decrypt(nick, EncrptAndDecryptClient.SEARCH,session);
						tradeRates.setNick(decrypt);
					}
				} catch (SecretException e) {
					e.printStackTrace();
					logger.error("中差评查询解密昵称失败"+e.getMessage());
				}
			}
		}
		
		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, findList);
		return pagination;
	}
	
	/**
	 * 中差评统计 查询汇总
	 */
	public RatedVo findAggregatezcaggregate(String ratedNick){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ratedNick",ratedNick);
		RatedVo ratedVo = mybatisDao.findBy("s2jh.biz.shop.crm.order.entity.TradeRates", "findAggregatezcaggregate", map);
		return ratedVo;
	}
	
	/**
	 * 中差评统计 折线图展示查询
	 */
	/*public List<RatedVo> findZxStatistics(String nick){
		Map<String,Object> map = new HashMap<String,Object>();
		int[] neutral =
		return null;
		
	}*/
	
	/**
	 * 条件分页总数查询
	 */
	public  Integer findAggregatezcaggregateCount(String ratedNick,String bTime,String eTime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ratedNick", ratedNick);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		Long totalCount = mybatisDao.findBy("s2jh.biz.shop.crm.order.entity.TradeRates", "findAggregatezcaggregateCount", map);
		Integer i = Integer.valueOf(totalCount.toString());
		return i;
	}
	
	/**
	 * 中差评统计 分页条件查询
	 */
	public Pagination findAggregatezcaggregate1(String ratedNick,String contextPath,String bTime, String eTime,Integer pageNo){
		if(pageNo==null){
			pageNo = 1;
		}
		//先设置每页显示的条数为3条
		Integer currentRows = 6;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;
		//计算出总页数
		Integer totalCount = findAggregatezcaggregateCount(ratedNick,bTime,eTime);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ratedNick", ratedNick);
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		List<RatedVo> ratedVos = mybatisDao.findLimitList("s2jh.biz.shop.crm.order.entity.TradeRates", "findAggregatezcaggregate1",map, currentRows);
		Pagination pagination = new Pagination(pageNo,currentRows,totalCount,ratedVos);
		StringBuilder params = new StringBuilder();
		if(bTime!=null){
			params.append("&bTime=").append(bTime);
		}
		if(eTime!=null){
			params.append("&eTime=").append(eTime);
			
		}
		String url =contextPath+"/crms/appraise/middleBadAssessment";
		pagination.pageView(url, params.toString());
		return pagination;
	}
	
	
	//曲线图查询封装数据
	/*public List<Map<String, Object>> findAggregatezcaggregate2(List<String> list,Date bTime,Date eTime,String ratedNick){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("ratedNick", ratedNick);

		JSONObject neutralJson = new JSONObject();
		JSONObject  badJson = new JSONObject();
		
		ArrayList<Integer> neutralList = new ArrayList<Integer>();
		ArrayList<Integer> badList = new ArrayList<Integer>();
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		
		if(list!=null){
			for(String s :list){
				map.put("dateTime", s);
				RatedVo ratedVo = mybatisDao.findBy("s2jh.biz.shop.crm.order.entity.TradeRates", "findRatedVo", map);
				if(ratedVo !=null){
					
					neutralList.add(ratedVo.getNeutral());
					badList.add(ratedVo.getBad());
				}else{
					neutralList.add(0);
					badList.add(0);
				}
			}
		}
		//中评
		neutralJson.put("name", "中评数");
		neutralJson.put("data", neutralList);
		
		//差评
		badJson.put("name", "差评数");
		badJson.put("data", badList);
		
		mapList.add(neutralJson);
		mapList.add(badJson);
		
		return mapList;
	}*/
	
	/**
	 * 添加评价列表
	 * ZTK2017年1月6日下午2:37:18
	 */
	public boolean saveTradeRate(TradeRates tradeRates){
		//oid,tid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid
		/*Map<String,Object> map = new HashMap<String, Object>();
		map.put("oid", tradeRates.getOid());
		map.put("tid", tradeRates.getTid());
		map.put("role", tradeRates.getRole());
		map.put("nick", tradeRates.getNick());
		map.put("result", tradeRates.getResult());
		map.put("created", tradeRates.getCreated());
		map.put("ratedNick", tradeRates.getRatedNick());
		map.put("itemTitle", tradeRates.getItemTitle());
		map.put("itemPrice", tradeRates.getItemPrice());
		map.put("content", tradeRates.getContent());
		map.put("reply", tradeRates.getReply());
		map.put("numIid", tradeRates.getNumIid());
		map.put("lastModifiedBy", tradeRates.getNick());
		map.put("lastModifiedDate", new Date());*/
		TradeRates saveRate = tradeRatesDao.save(tradeRates);
		if(saveRate != null){
			return true;
		}else {
			return false;
		}
//		mybatisDao.execute(TradeRates.class.getName(), "saveTradeRates", map);
	}
	
	
	/**
	 * 中评数 分页条件查询
	 */
	public Pagination findAggregatezcaggregate2(String ratedNick,String contextPath,String bTime, String eTime,Integer pageNo){
		if(pageNo==null){
			pageNo = 1;
		}
		//先设置每页显示的条数为3条
		Integer currentRows = 6;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;
		//计算出总页数
		Integer totalCount = findAggregatezcaggregateCount(ratedNick,bTime,eTime);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ratedNick", ratedNick);
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		List<RatedVo> ratedVos = mybatisDao.findLimitList("s2jh.biz.shop.crm.order.entity.TradeRates", "findAggregatezcaggregate1",map, currentRows);
		Pagination pagination = new Pagination(pageNo,currentRows,totalCount,ratedVos);
		StringBuilder params = new StringBuilder();
		if(bTime!=null){
			params.append("&bTime=").append(bTime);
		}
		if(eTime!=null){
			params.append("&eTime=").append(eTime);
			
		}
		String url =contextPath+"/crms/appraise/middleAssessment";
		pagination.pageView(url, params.toString());
		return pagination;
	}
	
	//中评数统计曲线图查询封装数据
		/*public List<Map<String, Object>> findAggregatezcaggregate3(List<String> list,Date bTime,Date eTime,String ratedNick){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("bTime", bTime);
			map.put("eTime", eTime);
			map.put("ratedNick", ratedNick);

			JSONObject neutralJson = new JSONObject();
			//JSONObject  badJson = new JSONObject();
			
			ArrayList<Integer> neutralList = new ArrayList<Integer>();
			//ArrayList<Integer> badList = new ArrayList<Integer>();
			
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			
			if(list!=null){
				for(String s :list){
					map.put("dateTime", s);
					RatedVo ratedVo = mybatisDao.findBy("s2jh.biz.shop.crm.order.entity.TradeRates", "findRatedVo", map);
					if(ratedVo !=null){
						
						neutralList.add(ratedVo.getNeutral());
						//badList.add(ratedVo.getBad());
					}else{
						neutralList.add(0);
						//badList.add(0);
					}
				}
			}
			//中评
			neutralJson.put("name", "中评数");
			neutralJson.put("data", neutralList);
			
			//差评
			badJson.put("name", "差评数");
			badJson.put("data", badList);
			
			mapList.add(neutralJson);
			mapList.add(badJson);
			
			return mapList;
		}*/
		
		/**
		 * 差评数 分页条件查询
		 */
		public Pagination findAggregatezcaggregate3(String ratedNick,String contextPath,String bTime, String eTime,Integer pageNo){
			if(pageNo==null){
				pageNo = 1;
			}
			//先设置每页显示的条数为3条
			Integer currentRows = 6;
			//计算出起始行数
			Integer startRows = (pageNo-1)*currentRows;
			//计算出总页数
			Integer totalCount = findAggregatezcaggregateCount(ratedNick,bTime,eTime);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ratedNick", ratedNick);
			map.put("startRows", startRows);
			map.put("currentRows", currentRows);
			map.put("bTime", bTime);
			map.put("eTime", eTime);
			List<RatedVo> ratedVos = mybatisDao.findLimitList("s2jh.biz.shop.crm.order.entity.TradeRates", "findAggregatezcaggregate1",map, currentRows);
			Pagination pagination = new Pagination(pageNo,currentRows,totalCount,ratedVos);
			StringBuilder params = new StringBuilder();
			if(bTime!=null){
				params.append("&bTime=").append(bTime);
			}
			if(eTime!=null){
				params.append("&eTime=").append(eTime);
				
			}
			String url =contextPath+"/crms/appraise/badAssessment";
			pagination.pageView(url, params.toString());
			return pagination;
		}
		
		//差评数统计曲线图查询封装数据
		/*public List<Map<String, Object>> findAggregatezcaggregate4(List<String> list,Date bTime,Date eTime,String ratedNick){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("bTime", bTime);
			map.put("eTime", eTime);
			map.put("ratedNick", ratedNick);
	
			//JSONObject neutralJson = new JSONObject();
			JSONObject  badJson = new JSONObject();
			
			//ArrayList<Integer> neutralList = new ArrayList<Integer>();
			ArrayList<Integer> badList = new ArrayList<Integer>();
			
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			
			if(list!=null){
				for(String s :list){
					map.put("dateTime", s);
					RatedVo ratedVo = mybatisDao.findBy("s2jh.biz.shop.crm.order.entity.TradeRates", "findRatedVo", map);
					if(ratedVo !=null){
						
						//neutralList.add(ratedVo.getNeutral());
					badList.add(ratedVo.getBad());
					}else{
						//neutralList.add(0);
						badList.add(0);
					}
				}
			}
			//中评
			//neutralJson.put("name", "中评数");
			//neutralJson.put("data", neutralList);
			
			//差评
			badJson.put("name", "差评数");
			badJson.put("data", badList);
			
			//mapList.add(neutralJson);
			mapList.add(badJson);
			
			return mapList;
		}*/
		
		/**
		 * 更新评价列表
		 * ZTK2017年1月10日下午6:03:47
		 */
		public void updataTradeRates(TradeRates tradeRates){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("oid", tradeRates.getOid());
			map.put("tid", tradeRates.getTid());
			map.put("role", tradeRates.getRole());
			map.put("nick", tradeRates.getNick());
			map.put("result", tradeRates.getResult());
			map.put("created", tradeRates.getCreated());
			map.put("ratedNick", tradeRates.getRatedNick());
			map.put("itemTitle", tradeRates.getItemTitle());
			map.put("itemPrice", tradeRates.getItemPrice());
			map.put("reply", tradeRates.getReply());
			map.put("numIid", tradeRates.getNumIid());
			map.put("id", tradeRates.getId());
			map.put("createdBy", tradeRates.getCreatedBy());
			map.put("createdDate", tradeRates.getCreatedDate());
			map.put("lastModifiedBy", tradeRates.getLastModifiedBy());
			map.put("lastModifiedDate", tradeRates.getLastModifiedDate());
			map.put("rateType", tradeRates.getRateType());
			map.put("valid_score", tradeRates.getValidScore());
			mybatisDao.execute(TradeRates.class.getName(), "updateTradesRateById", map);
		}	
		
		/**
		 * 查询评价表中所有的id
		 * ZTK2017年1月11日下午5:54:56
		 */
		public int findCountById(Long id){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", 18);
			int count = mybatisDao.findBy(TradeRates.class.getName(), "findCountById", map);
			return count;
		}

		/**
		 * 通过tid和oid查询TradeRates表的数据
		 * helei 2017年3月13日下午2:40:27
		 */
		public List<TradeRates> findtradeRatesByTidAndOid(String jsonTid,String jsonOid) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("oid", jsonOid);
			map.put("tid", jsonTid);
			List<TradeRates> findList = mybatisDao.findList(TradeRates.class.getName(), "findtradeRatesByTidAndOid", map);
			return findList;
		}

		/**
		 * 更新评价表的数据标识已被删除中差评
		 * helei 2017年3月13日下午5:34:25
		 */
		public void updataTradeRatesISDelete(TradeRates tradeRates) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("lastModifiedDate", new Date());
			map.put("result", tradeRates.getResult());
			map.put("id", tradeRates.getId());
			mybatisDao.execute(TradeRates.class.getName(), "updataTradeRatesISDelete", map);
		}
		
		/**
		 * 定时同步评价数据到CRM数据库
		 * ZTK2017年3月20日下午5:28:50
		 */
		public void saveTradeRate(){
		try {
			int i = 0;
			// 调用taobao接口
			DefaultTaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
			TraderatesGetRequest neutralReq = new TraderatesGetRequest();
			TraderatesGetRequest badReq = new TraderatesGetRequest();
			Date date1 = new Date();
			// 查询userInfo表里的所有的token
			List<UserInfo> userInfoList = userInfoService.findUserInfoToken();
			for (UserInfo userInfo : userInfoList) {
				String userId = userInfo.getTaobaoUserNick();
//				if(!"哈数据库等哈".equals(userId)){
//					continue;
//				}
				// 全部的token
				Long userInfoId = userInfo.getId();
				logger.debug("=====================评价同步：：取出userInfoId:"+userInfoId);
				//查询此用户中差评监控是否开启。未开启，则不获得评价信息。
				boolean isOpen = orderRateCareSetupService.findStatusByUserId(userId);
				if(isOpen){
				}else{
					continue;
				}
				String accessToken = userInfo.getAccess_token();
				if(accessToken == null || "".equals(accessToken)){
					continue;
				}
				//拉取中评信息，使用该accessToken查询用户表是否过期，如果过期就通过userInfoId查询信的accessToken;
				UserInfo userIn = mybatisDao.findBy(UserInfo.class.getName(), "findUserInfoBytoken", accessToken);
				if(userIn != null ){
					logger.debug("===================================================评价同步：：：：accessToken未过期");
				}else{
					accessToken = mybatisDao.findBy(UserInfo.class.getName(), "findTokenById", userInfoId);
					logger.debug("=================评价同步：：：：accessToken过期啦！！取出新的accessToken:"+accessToken);
				}
				TraderatesGetResponse neutralRsp = installReq(client, neutralReq, accessToken, "neutral");
				i ++;
				saveTradeRates(neutralRsp,userId);
				//拉取差评信息，使用该accessToken查询用户表是否过期，如果过期就通过userInfoId查询信的accessToken;
				UserInfo userIn1 = mybatisDao.findBy(UserInfo.class.getName(), "findUserInfoBytoken", accessToken);
				if(userIn1 != null ){
					logger.debug("===================================================评价同步：：：：accessToken未过期");
				}else{
					accessToken = mybatisDao.findBy(UserInfo.class.getName(), "findTokenById", userInfoId);
					logger.debug("=================评价同步：：：：accessToken过期啦！！取出新的accessToken:"+accessToken);
				}
				TraderatesGetResponse badRsp = installReq(client, badReq, accessToken, "bad");
				i ++;
				saveTradeRates(badRsp,userId);
			}
			Date date2 = new Date();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("taskEndTime", date2);
			map.put("type", "tradeRates");
			map.put("i", i);
			logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!调用接口次数：" + i);
			TaskNode taskNode = taskNodeService.getTaskNode("tradeRates");
			if(taskNode != null){
				mybatisDao.execute(TaskNode.class.getName(), "updateTaskTime", map);
			}else {
				mybatisDao.execute(TaskNode.class.getName(), "insertTaskNode", map);
			}
			logger.debug("保存最后一次获取时间:" + date2);
			logger.debug("<<<<<<<<<<========<<<<<<<<<获取本次时间段评价完成,耗时:"+ (date2.getTime() - date1.getTime()) + ">>>>>>>>>>==========>>>>>>>>>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 拉取评价信息设置拉取条件
	 * ZTK2017年8月22日上午11:56:53
	 */
	public TraderatesGetResponse installReq(DefaultTaobaoClient client,TraderatesGetRequest req,
			String accessToken,String result){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String taskNodeType = "tradeRates";//评价
		TaskNode taskNode = mybatisDao.findBy(TaskNode.class.getName(), "findTaskLastTime", taskNodeType);
		// 设置调用接口查询条件
		req.setFields("tid,oid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid");
		req.setRateType("get");
		req.setRole("buyer");
		req.setPageSize(150l);
		req.setResult(result);
		// 根据评价时间查询，第一次查询需要注释掉，开始时间为定时任务开启的时间的24小时之前，结束时间为定时任务开启时间
		req.setEndDate(new Date());
		if(taskNode != null && taskNode.getTaskEndTime() != null){
			req.setStartDate(taskNode.getTaskEndTime());
		}else{
			req.setStartDate(StringUtils.parseDateTime(DateUtils.getTimeByHour(format.format(new Date()), -2)));
		}
		TraderatesGetResponse rsp = null;
		try {
			rsp = client.execute(req, accessToken);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return rsp;
	}
	
	/**
	 * 保存拉取的淘宝评价信息
	 * ZTK2017年8月22日下午12:03:42
	 */
	public void saveTradeRates(TraderatesGetResponse rsp,String userId){
		if(rsp != null && rsp.getTradeRates() != null && rsp.getTradeRates().size() > 0){
			logger.debug("~~~~~~~~~~~~~~" + "" + "搜索到的评价总条数数：" + rsp.getTotalResults());
			List<TradeRate> tradeRatesList = rsp.getTradeRates();
			// 循环获得的评价信息，填充到tradeRates中
			for (TradeRate tradeRate : tradeRatesList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tid", tradeRate.getTid());
				map.put("oid", tradeRate.getOid());
				map.put("role", tradeRate.getRole());
				map.put("nick", tradeRate.getNick());
				map.put("result", tradeRate.getResult());
				map.put("created", tradeRate.getCreated());
				map.put("ratedNick", tradeRate.getRatedNick());
				map.put("itemTitle", tradeRate.getItemTitle());
				map.put("itemPrice", tradeRate.getItemPrice());
				map.put("content", tradeRate.getContent());
				map.put("reply", tradeRate.getReply());
				map.put("numIid", tradeRate.getNumIid());
				map.put("validScore", tradeRate.getValidScore());
				map.put("createdDate", tradeRate.getCreated());
				// 保存中评TradeRates实体到CRM数据库
				mybatisDao.execute(TradeRates.class.getName(), "insertTradeRate", map);
				//更新会员是否为中差评
				boolean isSuccess = vipMemberService.updateMemberCommentStatus(tradeRate.getNick(), userId);
				if(isSuccess){
					logger.info("-------取出评价数据--更新会员中差评状态成功-------");
				}else{
					logger.info("-------取出评价数据--更新会员中差评状态失败-------");
				}
				logger.info("-------取出评价数据--评价类型是:"+tradeRate.getResult()+"------保存到crm数据库-------");
				logger.info("-------取出评价数据--list的数量:"+tradeRatesList.size()+"------tid："+tradeRate.getTid()+"-oid:"+tradeRate.getOid()+"--------");
			}
			try {
				rateMonitoringPacifyService.doHandle(userId, tradeRatesList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
		
	/**
	 * 评价关怀--评价记录的查询
	 * ZTK2017年3月31日下午5:08:36
	 * @throws SecretException 
	 */
	public Pagination queryTradeRates(Map<String,Object> map,Integer pageNo,String userId) throws SecretException{
		//设置每页显示的条数为5条
		Integer currentRows = 10;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;		
		//查询集合中的总个数
		int totalCount = mybatisDao.findBy(TradeRates.class.getName(), "findAllTradeRatesCount", map);
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		List<TradeRates> findList = mybatisDao.findList(TradeRates.class.getName(), "findAllTradeRates", map);
		//查询用户sessionKey
		String sessionKey = userInfoService.validateFindSessionKey(userId);
		if(findList != null && !findList.isEmpty()){
			for (TradeRates tradeRates : findList) {
				if(tradeRates.getNick() != null && !"".equals(tradeRates.getNick())){
					if(EncrptAndDecryptClient.isEncryptData(tradeRates.getNick(), EncrptAndDecryptClient.SEARCH)){
						tradeRates.setNick(EncrptAndDecryptClient.getInstance().decryptData(tradeRates.getNick(), EncrptAndDecryptClient.SEARCH, sessionKey));
					}
				}
			}
		}
		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, findList);
		return pagination;
		
	}
	
}
