package s2jh.biz.shop.crm.manage.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.taobao.api.SecretException;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.data.entity.ShopDataStatistics;
import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.manage.dao.TradeRepository;
import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.util.idworker.TradeIdWorker;
import s2jh.biz.shop.crm.manage.vo.MemberCriteriaVo;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.order.pojo.CustomerDetail;
import s2jh.biz.shop.crm.order.pojo.EffectNum;
import s2jh.biz.shop.crm.order.pojo.ItemDetail;
import s2jh.biz.shop.crm.order.pojo.ReminderNum;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.vo.OrdersVo;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.TradeStatusUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
public class TradeInfoService {

	@Autowired
	private TradeRepository  tradeRepository;
	@Resource(name="mongoTemplate")
    protected MongoTemplate mongoTemplate;  
	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private VipMemberService vipMemberService;
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	@Autowired
	private CacheService cacheService;
	private static final Logger loger = Logger.getLogger(TradeInfoService.class);
	
	/***
	 * 
	 * description 根据条件查询分页数据 默认100条 按创建时间DESC排序
	 * @author zlp
	 * @param page
	 * @param ovo
	 * @param userNickName  NOT NUll
	 * @info  此方法有坑   请勿调用    后果自负
	 * @return
	 */
	public Pageination<TradeDTO>  findTradePageList(Pageination<TradeDTO> page ,OrdersVo ovo ,String userNickName){
		if(null!=userNickName&&!"".equals(userNickName)){
			Query query  = new Query();
			OrdersVo ordersVoParam =null;
			try {
				ordersVoParam =  filterCondition(ovo);
				packageCriteriaParam(query,ordersVoParam);
				query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "created")));  
			} catch (Exception e) {
				loger.error("解析参数失败！"+e.getMessage());
			}
			return  ordersVoParam==null?page:tradeRepository.findPage(page, query, userNickName);
		}else{
			return null;
		}
	}
	
	/**
	 * 订单短信群发根据查询条件查询待发送的手机号
	 * ZTK2017年8月24日下午1:36:56
	 */
	public List<TradeDTO>  findSendOrderInfoList(OrdersVo ordersVo ,String userNickName){
		List<TradeDTO> tradeList = null;
		Query query  = new Query();
		OrdersVo ordersVoParam =null;
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String sessionKey = userInfoService.validateFindSessionKey(userNickName);
		try {
			ordersVoParam =  filterCondition(ordersVo);
			packageCriteriaParam(query,ordersVoParam);
			int pageNumber = ordersVo.getStartRow().intValue();  
			int pageSize = ordersVo.getPageSize().intValue();
	        query.skip(pageNumber).limit(pageSize);  
			query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "_id")));  
			if(null!=ordersVoParam){
				tradeList = tradeRepository.find(query ,userNickName);
				if(null!=tradeList&&tradeList.size()>0){
//					Update update  = new Update();
//					update.set("msgId", ordersVo.getMsgId());
//					tradeRepository.updateMulti(query, update, userNickName); 
					Date log6 = new Date();
					for (TradeDTO tradeDto : tradeList) {
						if(tradeDto != null && tradeDto.getReceiverMobile() != null && !"".equals(tradeDto.getReceiverMobile())){
							if(EncrptAndDecryptClient.isEncryptData(tradeDto.getReceiverMobile(), EncrptAndDecryptClient.PHONE)){
								tradeDto.setReceiverMobile(decryptClient.decryptData(tradeDto.getReceiverMobile(), EncrptAndDecryptClient.PHONE, sessionKey));
							}else {
								tradeDto.setReceiverMobile(tradeDto.getReceiverMobile());
							}
						}
					}
					Date log7 = new Date();
					loger.info("订单短信群发全部发送~~~~~~~~~~~~for循环解密手机号时间：" + (log7.getTime() - log6.getTime()) + "毫秒");
				}
			}
		} catch (Exception e) {
			loger.error("解析参数失败！"+e.getMessage());
		}
		return tradeList;
	}


	/***
	 * 
	 * description 查询单个trade对象
	 * @author zlp
	 * @param query
	 * @param userNickName  NOT NUll
	 * @info  此方法有坑   请勿调用    后果自负
	 * @return
	 */
	public TradeDTO  findOne(Query query ,String userNickName){
		return tradeRepository.findOne(query,userNickName);  
	}
	
	public TradeDTO  findOneByTid(String tid,String userNickName){
		if(null!=tid&&!"".equals(tid)){
			Query query = new Query();
			query.addCriteria(Criteria.where("tid").is(tid));
			return tradeRepository.findOne(query,userNickName);  
		}else{
			return null;
		}
	}
	/***
	 * 
	 * description 查询Listtrade对象
	 * @author zlp
	 * @param query
	 * @param userNickName  NOT NUll
	 * @info  此方法有坑   请勿调用    后果自负
	 * @return
	 */
	public List<TradeDTO>  findList(Query query ,String userNickName){
		return tradeRepository.find(query,userNickName);  
	}
	
	/***
	 * 
	 * description 业务方法  封装mongo query查询条件
	 * @author zlp
	 * @param query
	 * @param OrdersVo ordersVoParam
	 * @info  此方法有坑   请勿调用    后果自负
	 * @return
	 * @throws SecretException 
	 */
	
	private void packageCriteriaParam(Query query, OrdersVo ordersVoParam) throws SecretException {
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String sessionKey = userInfoService.validateFindSessionKey(ordersVoParam.getUserId());
		if(null!=ordersVoParam.getTid()&&!"".equals(ordersVoParam.getTid())){
			query.addCriteria(Criteria.where("tid").is(ordersVoParam.getTid()));  
		}
		if(null!=ordersVoParam.getBuyerNick()&&!"".equals(ordersVoParam.getBuyerNick())){
			if(EncrptAndDecryptClient.isEncryptData(ordersVoParam.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
				query.addCriteria(Criteria.where("buyerNick").is(ordersVoParam.getBuyerNick()));  
			}else {
				query.addCriteria(Criteria.where("buyerNick").is(decryptClient.encryptData(ordersVoParam.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey)));  
			}
		}
		if(null!=ordersVoParam.getOrderFrom()&&!"".equals(ordersVoParam.getOrderFrom())){
			query.addCriteria(Criteria.where("tradeFrom").regex(".*?" + ordersVoParam.getOrderFrom() + ".*"));  
		}
		if(null!=ordersVoParam.getRefundStatus()&&!"".equals(ordersVoParam.getRefundStatus())){
			query.addCriteria(Criteria.where("orders.refundStatus").is(ordersVoParam.getRefundStatus()));  
		}
		if(null!=ordersVoParam.getReceiverDistrict()&&!"".equals(ordersVoParam.getReceiverDistrict())){
			query.addCriteria(Criteria.where("receiverCity").is(ordersVoParam.getReceiverDistrict()));  
		}
		if(ordersVoParam.getNumIidList() != null && !"".equals(ordersVoParam.getNumIidList())){
			query.addCriteria(Criteria.where("orders.numIid").in(ordersVoParam.getNumIidList()));  
		}
		if(ordersVoParam.getDateEndTimeAfter() != null && ordersVoParam.getDateEndTimeBefor() != null){
			query.addCriteria(Criteria.where("orders.endTime").gte(ordersVoParam.getDateEndTimeBefor().getTime()).lte(ordersVoParam.getDateEndTimeAfter().getTime()));
		}else if(ordersVoParam.getDateEndTimeAfter() == null && ordersVoParam.getDateEndTimeBefor() != null){
			query.addCriteria(Criteria.where("orders.endTime").gte(ordersVoParam.getDateEndTimeBefor().getTime()));
		}else if(ordersVoParam.getDateEndTimeAfter() != null && ordersVoParam.getDateEndTimeBefor() == null){
			query.addCriteria(Criteria.where("orders.endTime").lte(ordersVoParam.getDateEndTimeAfter().getTime()));
		}
		if(ordersVoParam.getStatus() != null && !"".equals(ordersVoParam.getStatus())){
			if("ALL".equals(ordersVoParam.getStatus()) || "TOTAL".equals(ordersVoParam.getStatus())){
			}else if("CLOSED".equals(ordersVoParam.getStatus())){
				Criteria c  = new  Criteria(); 
				c.orOperator(Criteria.where("status").is("TRADE_CLOSED"),Criteria.where("status").is("TRADE_CLOSED_BY_TAOBAO"));
				query.addCriteria(c);
			}else {
				query.addCriteria(Criteria.where("status").is(ordersVoParam.getStatus()));  
			}
		}
		//地区
		if(ordersVoParam.getReceiverState() != null && !"".equals(ordersVoParam.getReceiverState())){
			String[] stateArr = ordersVoParam.getReceiverState().split(",");
			List<String> stateList = Arrays.asList(stateArr);
			if(stateList != null && !stateList.isEmpty()){
				query.addCriteria(Criteria.where("receiverState").in(stateList));
			}
		}
		List<String> sellerFlagList = new ArrayList<String>();
		if(ordersVoParam.getSellerFlagStr() != null && !"".equals(ordersVoParam.getSellerFlagStr())){
			String[] sellerFlags = (ordersVoParam.getSellerFlagStr() + "").split(",");
			for (int i = 0; i < sellerFlags.length; i++) {
				sellerFlagList.add(sellerFlags[i]);
			}
			if(null != sellerFlagList && sellerFlagList.size()>0){
				query.addCriteria(Criteria.where("sellerFlag").in(sellerFlagList));   
			}
		} 
		
		if("双方已评价".equals(ordersVoParam.getRateStatus())){
			query.addCriteria(Criteria.where("buyerRate").is(true).andOperator(Criteria.where("sellerRate").is(true).andOperator(Criteria.where("status").is(TradeStatusUtils.TRADE_FINISHED))));  
		}else if("买家未评".equals(ordersVoParam.getRateStatus())){
			query.addCriteria(Criteria.where("buyerRate").is(false).andOperator(Criteria.where("status").is(TradeStatusUtils.TRADE_FINISHED)));  
		}else if("卖家未评".equals(ordersVoParam.getRateStatus())){
			query.addCriteria(Criteria.where("sellerRate").is(false).andOperator(Criteria.where("status").is(TradeStatusUtils.TRADE_FINISHED)));  
		}else if("买家已评，卖家未评".equals(ordersVoParam.getRateStatus())){
			query.addCriteria(Criteria.where("buyerRate").is(true).andOperator(Criteria.where("sellerRate").is(false).andOperator(Criteria.where("status").is(TradeStatusUtils.TRADE_FINISHED))));  
		}else if("买家未评，卖家已评".equals(ordersVoParam.getRateStatus())){
			query.addCriteria(Criteria.where("buyerRate").is(false).andOperator(Criteria.where("sellerRate").is(true).andOperator(Criteria.where("status").is(TradeStatusUtils.TRADE_FINISHED))));  
		}
		
		if(null!=ordersVoParam.getMsgIdList()&&ordersVoParam.getMsgIdList().size()>0){
			query.addCriteria(Criteria.where("msgId").nin(ordersVoParam.getMsgIdList()));  
		}
		
		//交易时间查询
		if(ordersVoParam.getStartTime()!=null && !"".equals(ordersVoParam.getStartTime())&&ordersVoParam.getEndTime()!=null && !"".equals(ordersVoParam.getEndTime())){
			try {
				query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(ordersVoParam.getStartTime(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(ordersVoParam.getEndTime(), DateUtils.DEFAULT_TIME_FORMAT)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			if(ordersVoParam.getStartTime() != null && !"".equals(ordersVoParam.getStartTime())){
				try {
					query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(ordersVoParam.getStartTime(), DateUtils.DEFAULT_TIME_FORMAT)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				if(ordersVoParam.getEndTime()!=null && !"".equals(ordersVoParam.getEndTime())){
					try {
						query.addCriteria(Criteria.where("created").lte(DateUtils.stringToLong(ordersVoParam.getEndTime(),DateUtils.DEFAULT_TIME_FORMAT)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		handleTimeCriteria(query, ordersVoParam);
	}

	/**
	 * 订单短信群发封装查询条件
	 * ZTK2017年8月24日下午1:42:12
	 */
	private void handleTimeCriteria(Query query, OrdersVo ordersVoParam) {
		try {
			if(ordersVoParam.getStartOrderDate() != null && !"".equals(ordersVoParam.getStartOrderDate())&&ordersVoParam.getEndOrderDate() != null && !"".equals(ordersVoParam.getEndOrderDate())){
				query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(ordersVoParam.getStartOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(ordersVoParam.getEndOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
			}else{
				if(ordersVoParam.getStartOrderDate() != null && !"".equals(ordersVoParam.getStartOrderDate())){
					query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(ordersVoParam.getStartOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
				}else if(ordersVoParam.getEndOrderDate() != null && !"".equals(ordersVoParam.getEndOrderDate())){
					query.addCriteria(Criteria.where("created").lte(DateUtils.stringToLong(ordersVoParam.getEndOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
				}
			}
			if(ordersVoParam.getConsignTimeBefore() != null && !"".equals(ordersVoParam.getConsignTimeBefore())&&ordersVoParam.getConsignTimeAfter() != null && !"".equals(ordersVoParam.getConsignTimeAfter())){
				query.addCriteria(Criteria.where("consignTime").gte(DateUtils.stringToLong(ordersVoParam.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(ordersVoParam.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
			}else{
				if(ordersVoParam.getConsignTimeBefore() != null && !"".equals(ordersVoParam.getConsignTimeBefore())){
					query.addCriteria(Criteria.where("consignTime").gte(DateUtils.stringToLong(ordersVoParam.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)));
				}else if(ordersVoParam.getConsignTimeAfter() != null && !"".equals(ordersVoParam.getConsignTimeAfter())){
					query.addCriteria(Criteria.where("consignTime").lte(DateUtils.stringToLong(ordersVoParam.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
				}
			}
			if(ordersVoParam.getPaymentBefore() != null && !"".equals(ordersVoParam.getPaymentBefore())&&ordersVoParam.getPaymentAfter() != null && !"".equals(ordersVoParam.getPaymentAfter())){
				query.addCriteria(Criteria.where("payment").gte(Double.parseDouble(ordersVoParam.getPaymentBefore())).lte(Double.parseDouble(ordersVoParam.getPaymentAfter())));
			}else{
				if(ordersVoParam.getPaymentBefore() != null && !"".equals(ordersVoParam.getPaymentBefore())){
					query.addCriteria(Criteria.where("payment").gte(Double.parseDouble(ordersVoParam.getPaymentBefore())));
				}else if(ordersVoParam.getPaymentAfter() != null && !"".equals(ordersVoParam.getPaymentAfter())){
					query.addCriteria(Criteria.where("payment").lte(Double.parseDouble(ordersVoParam.getPaymentAfter())));
				}
			}
		} catch (ParseException e) {
		}
	}
	
	/**
	 * 订单短信群发页面过滤最近几天发送过的订单
	 * ZTK2017年8月24日下午1:41:09
	 */
	private OrdersVo filterCondition(OrdersVo ovo) {
		if (null!=ovo.getSmsFile() &&!"".equals(ovo.getSmsFile()) ) {
			int lastSendTime = Integer.parseInt(ovo.getSmsFile());
			if(lastSendTime>0){
				Date endDate = new Date();
				Date startDate = DateUtils.addDate(endDate , (lastSendTime*-1));
				MemberCriteriaVo	memberCriteriaVo  = new  MemberCriteriaVo();
				memberCriteriaVo.setSendStartTime(startDate);
				memberCriteriaVo.setSendEndTime(endDate);
				memberCriteriaVo.setType(MsgType.MSG_DDDXQF);
				memberCriteriaVo.setUserId(ovo.getUserId());
				List<MsgSendRecord> lastSendList = msgSendRecordService.queryLastSendList(memberCriteriaVo);
				List<Long> msgList = null;
				if(null!=lastSendList&&lastSendList.size()>0){
					msgList = new ArrayList<Long>();
					for (MsgSendRecord msgSendRecord : lastSendList) {
						if(!msgList.contains(msgSendRecord.getId())){
							msgList.add(msgSendRecord.getId());
						}
					}
					if(null!=msgList&&msgList.size()>0){
						ovo.setMsgIdList(msgList);
					}
				}
			}
		}
		return ovo;
	}
	/***
	 * 导数据专用方法
	 * saveTradeInfo:(这里用一句话描述这个方法的作用). <br/> 
	 * @author zlp
	 * @param tradeDTO
	 */
	public void saveTradeInfo(TradeDTO tradeDTO) {
		if(null!=tradeDTO&&null!=tradeDTO.getSellerNick()&&!"".equals(tradeDTO.getSellerNick())){
			try {
				Query query = new Query();  
				query.addCriteria(Criteria.where("tid").is(tradeDTO.getTid()));
				TradeDTO tradeInfo = findOne(query,tradeDTO.getSellerNick());
				if(null!=tradeInfo){
					if(null!=tradeInfo.getLastSendSmsTime())tradeDTO.setLastSendSmsTime(tradeInfo.getLastSendSmsTime());
					if(null!=tradeInfo.getMsgId())tradeDTO.setMsgId(tradeInfo.getMsgId());
					tradeDTO.set_id(tradeInfo.get_id());
					tradeDTO.setCreatedDate( tradeInfo.getCreatedDate() );
					tradeDTO.setLastModifiedDate(new Date());
					tradeDTO.setTimestampId(tradeInfo.getTimestampId());
				}else{
					tradeDTO.setLastSendSmsTime(0l);
					tradeDTO.setMsgId(0l);
					tradeDTO.setCreatedDate( new Date() );
					tradeDTO.setLastModifiedDate( new Date() );
					tradeDTO.setTimestampId(TradeIdWorker.getInstance().nextId());
				}
				tradeRepository.save(tradeDTO, tradeDTO.getSellerNick());
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
	}

	
	/**
	 * 店铺数据，订单列表，查询订单数据
	 */
	public Pageination<TradeDTO> queryTradePagination(String contextPath, Integer pageNo,
			String buyerNick, String orderId, String status, String userId,
			List<String> orderFrom, String timeType, String beginTime, String endTime) {
		//先设置每页显示的条数为5条
		Integer currentRows = 5;
		//封装params
		StringBuilder params = this.packagingParams(timeType, beginTime,
				endTime, buyerNick, orderId, status, orderFrom, userId);
			
		//封装查询条件
		Query query = new Query();
		packagingTradeQueryParam(query,timeType, beginTime, endTime, buyerNick,
				orderId, status, orderFrom,userId);
		
		//封装page
		Pageination<TradeDTO> page = new Pageination<TradeDTO>();
		page.setPageNo(pageNo);
		page.setPageSize(currentRows);
		//查询数据
		if(null!=userId&&!"".equals(userId)){
			page = tradeRepository.findPage(page, query, userId);
		}
		List<TradeDTO> datas = page.getDatas();
		//手机号中间4位模糊处理
		if(datas != null && datas.size()>0){
			String token = cacheService.getJsonStr(
					RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE,
					RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY
							+ userId);
			for (TradeDTO t : datas) {
				if(null != t.getReceiverMobile()&& !"".equals(t.getReceiverMobile())){
					try {
						if(EncrptAndDecryptClient.isEncryptData(t.getReceiverMobile(), EncrptAndDecryptClient.PHONE)){
							String decrypt = EncrptAndDecryptClient.getInstance().decrypt(t.getReceiverMobile(),EncrptAndDecryptClient.PHONE,token);
							t.setReceiverMobile(decrypt.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
						}else{
							t.setReceiverMobile(t.getReceiverMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(null !=t.getBuyerNick() && !"".equals(t.getBuyerNick())){
					try {
						if(EncrptAndDecryptClient.isEncryptData(t.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
							String decrypt = EncrptAndDecryptClient.getInstance().decrypt(t.getBuyerNick(),EncrptAndDecryptClient.SEARCH,token);
							t.setBuyerNick(decrypt);
						}
					} catch (SecretException e) {
						e.printStackTrace();
					}
				}
				
				if(null !=t.getReceiverName() && !"".equals(t.getReceiverName())){
					try {
						if(EncrptAndDecryptClient.isEncryptData(t.getReceiverName(), EncrptAndDecryptClient.SEARCH)){
							String decrypt = EncrptAndDecryptClient.getInstance().decrypt(t.getReceiverName(),EncrptAndDecryptClient.SEARCH,token);
							t.setReceiverName(decrypt);
						}
					} catch (SecretException e) {
						e.printStackTrace();
					}
				}
			}
		}
		//封装分页数据
		page.pageView(contextPath, params.toString());
		return page;
	}
	/**
	* 创建人：邱洋
	* @Title: DecryptTradeDTO 
	* @Description: TODO(将查询到的订单数据解密) 
	* @param @param userId
	* @param @param list
	* @param @return    设定文件 
	* @return List<TradeDTO>    返回类型 
	* @throws
	 */
	public List<TradeDTO> DecryptTradeDTO(String userId,List<TradeDTO> list){
		List<TradeDTO> DecryptList = new ArrayList<TradeDTO>();
		if(list!=null&&list.size()>0){
			String token = cacheService.getJsonStr(
					RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE,
					RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY
							+ userId);
			List<String> buyerNickList = new ArrayList<String>();
			List<String> phoneList = new ArrayList<String>();
			List<String> buyerNameList = new ArrayList<String>();
			Map<String,String> buyerNickMap = new HashMap<String, String>();
			Map<String,String> phoneMap = new HashMap<String, String>();
			Map<String,String> buyerNameMap = new HashMap<String, String>();
			for(TradeDTO trade :list){
				buyerNickList.add(trade.getBuyerNick());
				phoneList.add(trade.getReceiverMobile());
				buyerNameList.add(trade.getReceiverName());
			}
			try {
				if(EncrptAndDecryptClient.isEncryptData(buyerNickList, EncrptAndDecryptClient.SEARCH)){
					buyerNickMap = EncrptAndDecryptClient.getInstance().decrypt(buyerNickList, EncrptAndDecryptClient.SEARCH, token);
				}
				if(EncrptAndDecryptClient.isEncryptData(phoneList, EncrptAndDecryptClient.PHONE)){
					phoneMap = EncrptAndDecryptClient.getInstance().decrypt(phoneList, EncrptAndDecryptClient.PHONE, token);
				}
				if(EncrptAndDecryptClient.isEncryptData(buyerNameList, EncrptAndDecryptClient.SEARCH)){
					buyerNameMap = EncrptAndDecryptClient.getInstance().decrypt(buyerNameList, EncrptAndDecryptClient.SEARCH, token);
				}
				for(TradeDTO trade :list){
					if(buyerNickMap.get(trade.getBuyerNick())!=null){
						trade.setBuyerNick(buyerNickMap.get(trade.getBuyerNick()));
					}
					if(null!=phoneMap.get(trade.getReceiverMobile())){
						trade.setReceiverMobile(phoneMap.get(trade.getReceiverMobile()).replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
					}else{
						trade.setReceiverMobile(trade.getReceiverMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
					}
					if(null!=buyerNameMap.get(trade.getReceiverName())){
						trade.setReceiverName(buyerNameMap.get(trade.getReceiverName()));
					}
					DecryptList.add(trade);
				}
			} catch (SecretException e) {
				e.printStackTrace();
			}
		}
		return DecryptList;
	}

	/***
	 * 
	 * description 业务方法  封装mongo query查询条件
	 * @author zlp
	 * @param userId 
	 * @info  此方法有坑   请勿调用    后果自负
	 * @return
	 */
	private void packagingTradeQueryParam(Query query,String timeType, String beginTime,
			String endTime, String buyerNick, String orderId, String status,
			List<String> orderFrom, String userId) {
		
		try {
			if(null!=timeType&&!"".equals(timeType)&&"created".equals(timeType)){
				if(beginTime != null && !"".equals(beginTime)&&endTime != null && !"".equals(endTime)){
					query.addCriteria(Criteria.where("createdUTC").gte(DateUtils.stringToDate(beginTime, DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToDate(endTime, DateUtils.DEFAULT_TIME_FORMAT)));
				}else{
					if(beginTime != null && !"".equals(beginTime)){
						query.addCriteria(Criteria.where("createdUTC").gte(DateUtils.stringToDate(beginTime, DateUtils.DEFAULT_TIME_FORMAT)));
					}else if(endTime != null && !"".equals(endTime)){
						query.addCriteria(Criteria.where("createdUTC").lte(DateUtils.stringToDate(endTime, DateUtils.DEFAULT_TIME_FORMAT)));
					}
				} 
			}
			if(null!=timeType&&!"".equals(timeType)&&"consignTtime".equals(timeType)){
				if(beginTime != null && !"".equals(beginTime)&&endTime != null && !"".equals(endTime)){
					query.addCriteria(Criteria.where("consignTimeUTC").gte(DateUtils.stringToDate(beginTime, DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToDate(endTime, DateUtils.DEFAULT_TIME_FORMAT)));
				}else{
					if(beginTime != null && !"".equals(beginTime)){
						query.addCriteria(Criteria.where("consignTimeUTC").gte(DateUtils.stringToDate(beginTime, DateUtils.DEFAULT_TIME_FORMAT)));
					}else if(endTime != null && !"".equals(endTime)){
						query.addCriteria(Criteria.where("consignTimeUTC").lte(DateUtils.stringToDate(endTime, DateUtils.DEFAULT_TIME_FORMAT)));
					}
				} 
			}
			
			if(null!=buyerNick&&!"".equals(buyerNick)){
				//加密字段，查询数据
				try {
					String session = userInfoService.findUserInfoTokens(userId);
					String encryptData = EncrptAndDecryptClient.getInstance().search(buyerNick, EncrptAndDecryptClient.SEARCH,session);
					query.addCriteria(Criteria.where("buyerNick").regex(".*"+encryptData+".*"));  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(null!=orderId&&!"".equals(orderId)){
				query.addCriteria(Criteria.where("tid").is(orderId));  
			}
			if(null!=status&&!"".equals(status)){
				String[] split = status.split(",");
				List<String> asList = Arrays.asList(split);
				query.addCriteria(Criteria.where("status").in(asList));  
			}
			if(null!=orderFrom&&orderFrom.size()>0){
				query.addCriteria(Criteria.where("orders.orderFrom").in(orderFrom));  
			}
			query.with(new Sort(Direction.DESC, "_id"));
		} catch (Exception e) {
			loger.error("解析参数失败！"+e.getMessage());
		}
	}


	private StringBuilder packagingParams(String timeType, String beginTime,
			String endTime, String buyerNick, String orderId, String status,
			List<String> orderFrom, String userId) {
		StringBuilder params = new StringBuilder();
		if(timeType!=null && !"".equals(timeType)){
			params.append("&timeType=").append(timeType);
		}
		if(beginTime!=null && !"".equals(beginTime)){
			params.append("&beginTime=").append(beginTime);
		}
		if(endTime!=null && !"".equals(endTime)){
			params.append("&endTime=").append(endTime);
		}
		if(buyerNick!=null && !"".equals(buyerNick)){
			params.append("&buyerNick=").append(buyerNick);
		}
		if(orderId!=null && !"".equals(orderId)){
			params.append("&orderId=").append(orderId);
		}
		if(status!=null && !"".equals(status)){
			params.append("&status=").append(status);
		}
		if(orderFrom!=null){
			if(orderFrom.size()>0){
				for(String s:orderFrom){
					params.append("&orderFrom=").append(s);
				}
			}
		}
		if(userId!=null && !"".equals(userId)){
			params.append("&userId=").append(userId);
		}
		
		return params;
	}
//	/**
//	* 创建人：邱洋
//	* @Title: getTradeStatus 
//	* @Description: TODO(根据用户昵称查询用户成交总笔数) 
//	* @param @param userList
//	* @param @param startDate
//	* @param @param endDate
//	* @param @return    设定文件 
//	* @return Map<String,Map<String,Object>>    返回类型 
//	* @throws
//	 */
//	@SuppressWarnings("static-access")
//	public Map<String,Object> getTradeStatus(String userId,Date startDate,Date endDate){
//		Map<String,Object> result = new HashMap<String,Object>();
//		
//		Criteria c = new Criteria();
//		if(startDate!=null&&endDate!=null){
//			c.where("created").lte(endDate).gte(startDate);
//		}
//		Aggregation agg = Aggregation.newAggregation(
//				Aggregation.project("trade_from"),
//				Aggregation.match(c),
//				Aggregation.group("trade_from").count().as("count")
//				);
//		
//		
//		List<TradeVo> customerPage = tradeRepository.findAggregateList(null, userId, agg,TradeVo.class);
//		if(customerPage.size()>0){
//			for(TradeVo tv :customerPage){
//				if(tv!=null){
//					switch(tv.getTradeFrom()){
//						case TradesInfo.ORDER_FROM_WAP:{
//							result.put("wapCount", tv.getCount());
//							break;
//						}
//						case TradesInfo.ORDER_FROM_TAOBAO:{
//							result.put("taobaoCount", tv.getCount());
//							break;
//						}
//						case TradesInfo.ORDER_FROM_JHS:{
//							result.put("jhsCount", tv.getCount());
//							break;
//						}
//					}
//				}
//			}
//		}
//		return result;
//	}
	public EffectNum findTotalOrderNum(String userId,Date bTime,List<String> phones,
			Date eTime,String orderSource,List<String> statusList,Boolean refundStatus) throws SecretException{
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String sessionKey = userInfoService.validateFindSessionKey(userId);
		//总订单数、总客户数、总商品数、总金额数
		Criteria criteria = Criteria.where("sellerNick").is(userId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		if(bTime != null && eTime != null){
			long startTime = bTime.getTime();
			long overTime = eTime.getTime();
			criteria.and("created").gte(startTime).lte(overTime);
		}
		if(orderSource != null && !"".equals(orderSource) && !"TOTAL".equals(orderSource)){
			criteria.and("tradeFrom").regex(".*?" + orderSource + ".*");
		}
		if(refundStatus != null){
			criteria.and("refundFlag").is(refundStatus);
		}
		if(statusList != null &&statusList.size() > 0){
			criteria.and("status").in(statusList);
		}
		if(phones != null && phones.size() > 0){
			if(EncrptAndDecryptClient.isEncryptData(phones, EncrptAndDecryptClient.PHONE)){
				criteria.and("receiverMobile").in(phones);
			}else {
//				List<String> phonList = new ArrayList<String>(decryptClient.encryptListData(phones, EncrptAndDecryptClient.PHONE, sessionKey).values());
				List<String> phonList = new ArrayList<String>();
				for (int i = 0; i < phones.size(); i++) {
					if(phones.get(i) != null && !"".equals(phones.get(i))){
						if(!"$".equals(phones.get(i).substring(0, 1))){
							phonList.add(decryptClient.encryptData(phones.get(i), EncrptAndDecryptClient.PHONE, sessionKey));
						}else {
							phonList.add(phones.get(i));
						}
					}
				}
				criteria.and("receiverMobile").in(phonList);
			}
		}else {
			return null;
		}
		Query query = new Query(criteria);
		Aggregation tradeAgg = Aggregation.newAggregation(
					Aggregation.project("sellerNick","num","payment","created","receiverMobile","tradeFrom","refundFlag","status"),
					Aggregation.match(criteria),
					Aggregation.group("sellerNick").sum("payment").as("payment").sum("num").as("itemNum").count().as("orderNum")
				);
		EffectNum effectNum = tradeRepository.sum(query, tradeAgg, userId);
		return effectNum;
	}
	/**
	 * 用于查询效果分析的客户详情页面
	 * ZTK2017年6月30日下午3:50:30
	 * @throws SecretException
	 */
	public Pagination customerDetailList(String userId,List<String> statusList,
			Date bTime,Date eTime,String buyerNick,String phone,String itemId,Integer pageNo,Boolean refundStatus,List<String> phones,String orderSource) throws SecretException{
		Criteria criteria = Criteria.where("sellerNick").is(userId);
		//先设置每页显示的条数为10条
		Integer currentRows = 10;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;
		String sessionKey = userInfoService.validateFindSessionKey(userId);
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		if(bTime != null){
			long startTime = bTime.getTime();
			long overTime = eTime.getTime();
			criteria.and("created").gte(startTime).lte(overTime);
			try {
				loger.info("~~~~~~~~~~~~~~~~~~~~~created:" + DateUtils.longToString(startTime, DateUtils.DEFAULT_TIME_FORMAT) + "and+++" + DateUtils.longToString(overTime, DateUtils.DEFAULT_TIME_FORMAT));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(buyerNick != null && !"".equals(buyerNick)){
			if(EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
				criteria.and("buyerNick").is(buyerNick);
			}else {
				criteria.and("buyerNick").is(decryptClient.encryptData(buyerNick, EncrptAndDecryptClient.SEARCH, sessionKey));
			}
		}
		if(orderSource != null && !"".equals(orderSource)){
			criteria.and("tradeFrom").regex(".*?" + orderSource + ".*");
		}
		if(phone != null && !"".equals(phone)){
			String encrPhone = decryptClient.encryptData(phone, EncrptAndDecryptClient.PHONE, sessionKey);
			if(phones != null && !phones.isEmpty() && phones.contains(encrPhone)){
				if(EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
					criteria.and("receiverMobile").is(phone);
				}else {
					criteria.and("receiverMobile").is(encrPhone);
				}
			}else {
				return new Pagination(pageNo, 10, 0, null);
			}
		}else {
			if(phones != null && phones.size() > 0){
				if(EncrptAndDecryptClient.isEncryptData(phones, EncrptAndDecryptClient.PHONE)){
					criteria.and("receiverMobile").in(phones);
				}else {
//					List<String> phoneList = new ArrayList<String>(decryptClient.encryptListData(phones, EncrptAndDecryptClient.PHONE, sessionKey).values());
					List<String> phoneList = new ArrayList<String>();
					for (int i = 0; i < phones.size(); i++) {
						if(phones.get(i) != null && !"".equals(phones.get(i))){
							if(!"$".equals(phones.get(i).substring(0, 1))){
								phoneList.add(decryptClient.encryptData(phones.get(i), EncrptAndDecryptClient.PHONE, sessionKey));
							}else {
								phoneList.add(phones.get(i));
							}
						}
					}
					criteria.and("receiverMobile").in(phoneList);
				}
			}else {
				return new Pagination(pageNo, 10, 0, null);
			}
		}
		if(itemId != null && !"".equals(itemId)){
			criteria.and("orders.numIid").is(Long.parseLong(itemId));
		}
		if(statusList != null && statusList.size() > 0){
			criteria.and("status").in(statusList);
		}
		if(refundStatus != null){
			criteria.and("refundFlag").is(refundStatus);
		}
		Query query = new Query(criteria);
		Aggregation customerAgg = Aggregation.newAggregation(
				Aggregation.project("refundFlag","status","created","buyerNick","payment","num","tid","sellerNick","receiverAddress","receiverName","receiverMobile","orders","tradeFrom"),
				Aggregation.match(criteria),
				Aggregation.group("buyerNick").first("buyerNick").as("buyerNick").first("receiverMobile").as("receiverMobile").
				first("receiverAddress").as("receiverAddress").first("receiverName").as("receiverName")
				.sum("payment").as("totalOrderMoney").sum("num").as("itemNum").count().as("orderNum"),
				Aggregation.sort(new Sort(new Sort.Order(Sort.Direction.DESC, "totalOrderMoney"))),
				Aggregation.skip(startRows),
				Aggregation.limit(currentRows)
				);
		Aggregation sizeAggregation = Aggregation.newAggregation(
				Aggregation.project("refundFlag","status","created","buyerNick","payment","num","tid","sellerNick","receiverAddress","receiverName","receiverMobile","orders","tradeFrom"),
				Aggregation.match(criteria),
				Aggregation.group("buyerNick").count().as("orderNum")
				);
		List<CustomerDetail> customerPage = tradeRepository.findAggregateList(query, userId, customerAgg,CustomerDetail.class);
		if(customerPage != null && customerPage.size() > 0){
			for (CustomerDetail customerDetail : customerPage) {
				if(customerDetail != null && customerDetail.getBuyerNick() != null && !"".equals(customerDetail.getBuyerNick())){
					String groupName = vipMemberService.findMemberIsCurGradeNameByNick(customerDetail.getBuyerNick(),userId);
					customerDetail.setMemberlevel(groupName);
					if(EncrptAndDecryptClient.isEncryptData(customerDetail.getBuyerNick(),  EncrptAndDecryptClient.SEARCH)){
						customerDetail.setBuyerNick(decryptClient.decryptData(customerDetail.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey));
					}
				}
				if(customerDetail != null && customerDetail.getReceiverMobile() != null && !"".equals(customerDetail.getReceiverMobile())){
					if(EncrptAndDecryptClient.isEncryptData(customerDetail.getReceiverMobile(), EncrptAndDecryptClient.PHONE)){
						customerDetail.setReceiverMobile(decryptClient.decryptData(customerDetail.getReceiverMobile(), EncrptAndDecryptClient.PHONE, sessionKey));
					}
				}
				if(customerDetail != null && customerDetail.getReceiverName() != null && !"".equals(customerDetail.getReceiverName())){
					if(EncrptAndDecryptClient.isEncryptData(customerDetail.getReceiverName(), EncrptAndDecryptClient.SIMPLE)){
						customerDetail.setReceiverName(decryptClient.decryptData(customerDetail.getReceiverName(), EncrptAndDecryptClient.SIMPLE, sessionKey));
					}
				}
				if(customerDetail != null && customerDetail.getReceiverAddress() != null && !"".equals(customerDetail.getReceiverAddress())){
					if(EncrptAndDecryptClient.isEncryptData(customerDetail.getReceiverAddress(), EncrptAndDecryptClient.SIMPLE)){
						customerDetail.setReceiverAddress(decryptClient.decryptData(customerDetail.getReceiverAddress(), EncrptAndDecryptClient.SIMPLE, sessionKey));
					}
				}
			}
		}
		List<CustomerDetail> sizePage = tradeRepository.findAggregateList(query, userId, sizeAggregation,CustomerDetail.class);
		Pagination pagination = new Pagination(pageNo, currentRows, sizePage.size(), customerPage);
		return pagination;
	}
	
	
	/**
	 * 效果分析，商品详情页面查询满足条件的订单集合
	 * ZTK2017年7月3日上午10:26:11
	 * @throws SecretException 
	 */
	public Pagination findTradesEffect(String userId,List<String> phoneList,String orderSource,
			List<String> statusList,Long itemId,Boolean refundStatus,Date bTime,Date eTime,Integer pageNo) throws SecretException{
		//先设置每页显示的条数为10条
		Integer currentRows = 10;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String sessionKey = userInfoService.validateFindSessionKey(userId);
		Criteria criteria = Criteria.where("sellerNick").is(userId);
		if(orderSource != null && !"".equals(orderSource)){
			criteria.and("tradeFrom").regex(".*?" +orderSource+ ".*");
		}
		if(statusList != null && statusList.size() > 0){
			criteria.and("status").in(statusList);
		}
		if(itemId != null){
			criteria.and("orders.numIid").is(itemId);
		}
		if(phoneList != null && phoneList.size() > 0){
			if(EncrptAndDecryptClient.isEncryptData(phoneList, EncrptAndDecryptClient.PHONE)){
				criteria.and("receiverMobile").in(phoneList);
			}else {
//				List<String> phonesList = new ArrayList<String>(decryptClient.encryptListData(phoneList, EncrptAndDecryptClient.PHONE, sessionKey).values());
				List<String> phonesList = new ArrayList<String>();
				for (int i = 0; i < phoneList.size(); i++) {
					if(!"$".equals(phoneList.get(i).substring(0, 1))){
						phonesList.add(decryptClient.encryptData(phoneList.get(i), EncrptAndDecryptClient.PHONE, sessionKey));
					}else {
						phonesList.add(phoneList.get(i));
					}
				}
				criteria.and("receiverMobile").in(phonesList);
			}
		}else {
			return new Pagination(pageNo, 10, 0, null);
		}
		if(refundStatus != null){
			criteria.and("refundFlag").is(refundStatus);
		}
		if(bTime != null && eTime != null){
			long startTime = bTime.getTime();
			long overTime = eTime.getTime();
			criteria.and("created").gte(startTime).lte(overTime);
		}
		/*
		 * project:列出所有本次查询的字段，包括查询条件的字段和需要搜索的字段；
		 * match:搜索条件criteria
		 * unwind：某一个字段是集合，将该字段分解成数组
		 * group：分组的字段，以及聚合相关查询
		 * 		first:就是first，没有什么sao操作；
		 * 		sum：求和(同sql查询)
		 * 		count：数量(同sql查询)
		 * 		as:别名(同sql查询)
		 * 		addToSet：将符合的字段值添加到一个集合或数组中
		 * sort：排序
		 * skip&limit：分页查询
		 */
		Aggregation itemAggregation = Aggregation.newAggregation(
					Aggregation.project("orders","sellerNick","buyerNick","tradeFrom","status","receiverMobile","refundFlag","created"),
					Aggregation.match(criteria),
					Aggregation.unwind("orders"),
					Aggregation.group("orders.numIid").first("orders.title").as("itemName").first("orders.price").as("itemPrice")
					.sum("orders.payment").as("totalOrderMoney").first("orders.numIid").as("itemId")
					.sum("orders.num").as("itemNum").count().as("orderNum").addToSet("buyerNick").as("customerList"),
					Aggregation.sort(new Sort(new Sort.Order(Sort.Direction.DESC, "totalOrderMoney"))),
					Aggregation.skip(startRows),
					Aggregation.limit(currentRows)
				);
		Aggregation itemAggCount = Aggregation.newAggregation(
					Aggregation.project("orders","sellerNick","buyerNick","tradeFrom","status","receiverMobile","refundFlag","created"),
					Aggregation.match(criteria),
					Aggregation.unwind("orders"),
					Aggregation.group("orders.numIid").first("orders.numIid").as("itemId")
			);
		Query query = new Query(criteria);
		List<ItemDetail> itemDetailList1 = tradeRepository.findAggregateList(query, userId, itemAggregation,ItemDetail.class);
		ArrayList<ItemDetail> itemDetailList = new ArrayList<ItemDetail>();
		if(itemDetailList1 != null && !itemDetailList1.isEmpty()){
			itemDetailList = new ArrayList<ItemDetail>(itemDetailList1);
			if(itemId != null){
				for (Iterator<ItemDetail> iterator = itemDetailList.iterator(); iterator.hasNext();) {
					ItemDetail itemDetail = iterator.next();
					long remItemId = itemDetail.getItemId();
					if(remItemId != itemId){
						iterator.remove();
					}
				}
			}
		}
		List<ItemDetail> totalItemList1 = tradeRepository.findAggregateList(query, userId, itemAggCount,ItemDetail.class);
		ArrayList<ItemDetail> totalItemList = new ArrayList<ItemDetail>();
		if(totalItemList1 != null && !totalItemList1.isEmpty()){
			totalItemList = new ArrayList<ItemDetail>(totalItemList1);
			if(itemId != null){
				for (Iterator<ItemDetail> iterator = totalItemList.iterator(); iterator.hasNext();) {
					ItemDetail itemDetail = iterator.next();
					long remItemId = itemDetail.getItemId();
					if(remItemId != itemId){
						iterator.remove();
					}
				}
			}
		}
		Pagination pagination = new Pagination(pageNo, currentRows, totalItemList.size(), itemDetailList);
		return pagination;
	}
	
	/**
	 * 计算订单中心效果分析(计算催付、付款金额，催付、付款订单，)
	 * ZTK2017年7月10日下午12:13:13
	 */
	public ReminderNum sumReminderNum(String userId,List<String> tidList,List<String> statusList,
			Date beginTime,Date endTime){
		Criteria criteria = Criteria.where("sellerNick").is(userId);
		if(beginTime != null && endTime != null){
			criteria.and("payTime").gte(beginTime.getTime()).lte(endTime.getTime());
		}
		if(tidList != null && tidList.size() > 0){
			criteria.and("tid").in(tidList);
		}else {
			return null;
		}
		if(statusList != null && !statusList.isEmpty()){
			criteria.and("status").in(statusList);
		}
		Aggregation aggregation = Aggregation.newAggregation(
					Aggregation.project("status","tid","payment","sellerNick","payTime"),
					Aggregation.match(criteria),
					Aggregation.group("sellerNick").sum("payment").as("targetMoney").count().as("targetOrder")
				);
		List<ReminderNum> ReminderNums = tradeRepository.findAggregateList(new Query(criteria), userId, aggregation, ReminderNum.class);
		ReminderNum result = null;
		if(ReminderNums != null && ReminderNums.size() > 0){
			result = ReminderNums.get(0);
		}
		return result;
	}
	
	/**
	 * 计算订单中心效果分析(计算回款提醒、付款金额以及订单，)
	 * ZTK2017年7月10日下午12:13:13
	 */
	public ReminderNum sumRefundNum(String userId,List<String> tidList,List<String> statusList,
			Date beginTime,Date endTime){
		Criteria criteria = Criteria.where("sellerNick").is(userId);
		if(beginTime != null && endTime != null){
			criteria.and("endTime").gte(beginTime.getTime()).lte(endTime.getTime());
		}
		if(tidList != null && tidList.size() > 0){
			criteria.and("tid").in(tidList);
		}else {
			return null;
		}
		if(statusList != null && !statusList.isEmpty()){
			criteria.and("status").in(statusList);
		}
		Aggregation aggregation = Aggregation.newAggregation(
					Aggregation.project("status","tid","payment","sellerNick","endTime"),
					Aggregation.match(criteria),
					Aggregation.group("sellerNick").sum("payment").as("targetMoney").count().as("targetOrder")
				);
		List<ReminderNum> ReminderNums = tradeRepository.findAggregateList(new Query(criteria), userId, aggregation, ReminderNum.class);
		ReminderNum result = null;
		if(ReminderNums != null && ReminderNums.size() > 0){
			result = ReminderNums.get(0);
		}
		return result;
	}
	
	
	/**
	 * @Description:查询出符合时间内,订单来源,用户的订单成交创建数
	 * @author jackstraw_yu
	 */
	private long findTradeCount(String userId,String tradeFrom,Date beginTime,Date endTime) {
		Query query = new Query();
		if(beginTime!=null && endTime!=null){
			query.addCriteria(Criteria.where("createdUTC").lte(endTime).gte(beginTime));
		}else{
			if(beginTime!=null)
				query.addCriteria(Criteria.where("createdUTC").gte(beginTime));
			if(endTime!=null)
				query.addCriteria(Criteria.where("createdUTC").lte(endTime));
		}
		if(tradeFrom!=null && !"".equals(tradeFrom)){
			query.addCriteria(Criteria.where("tradeFrom").regex("^.*"+tradeFrom+"*.$"));	
		}
		return tradeRepository.count(query,userId);
	}

	/**
	 * @Description:查询出符合條件的商品件数
	 * @author jackstraw_yu
	 */
	private long findTradeNum(String userId, String status,String timeType,
								Boolean isRefund,Date beginTime,Date endTime){
		Query query = new Query();
		if(beginTime!=null && endTime!=null){
			query.addCriteria(Criteria.where(timeType).lte(endTime).gte(beginTime));
		}else{
			if(beginTime!=null)
				query.addCriteria(Criteria.where(timeType).gte(beginTime));
			if(endTime!=null)
				query.addCriteria(Criteria.where(timeType).lte(endTime));
		}
		if(isRefund!=null){
			query.addCriteria(Criteria.where("refundFlag").is(isRefund));
		}
		query.addCriteria(Criteria.where("status").is(status));
		return tradeRepository.sumTradeNum(query,userId);
	}
	
	
	/**
	 * @Description:查询出符合条件的用户的成交额
	 * @author jackstraw_yu
	 */
	private double findTradePayment(String userId, String tradeFrom ,Date beginTime,Date endTime){
		Query query = new Query();
		if(beginTime!=null && endTime!=null){
			query.addCriteria(Criteria.where("payTimeUTC").lte(endTime).gte(beginTime));
		}else{
			if(beginTime!=null)
				query.addCriteria(Criteria.where("payTimeUTC").gte(beginTime));
			if(endTime!=null)
				query.addCriteria(Criteria.where("payTimeUTC").lte(endTime));
		}
		if(tradeFrom!=null &&!"".equals(tradeFrom)){
			query.addCriteria(Criteria.where("tradeFrom").regex("^.*"+tradeFrom+"*.$"));	
		}
		return tradeRepository.sumPayment(query,userId);
	}
	
	
	/**
	 * @Description:组装数据
	 * @author jackstraw_yu
	 */
	private void addShopDataStatistics(List<ShopDataStatistics> datas,String userId,String type,String count){
		ShopDataStatistics data = new ShopDataStatistics();
		data.setDataType(type);
		data.setChangeQuantity(count);
		data.setUserId(userId);
		datas.add(data);
	}
	
	
	
	/**
	 * @Description:获取昨日,前日的各种订单数据--->首页
	 * @author jackstraw_yu
	 */
	public List<ShopDataStatistics>  getTradeDataByDate(Map<String, Date> map, String userId) {
		long s1 = System.currentTimeMillis();
		/** '订单数','无线订单数','PC订单数'				----->订单数
		 * '退款中','待付款','待发货','出售中'			----->商品件数
	     * '成交额','PC成交额','无线成交额','聚划算成交额' ----->成交额
	     */
		/**
		 * 交易内部来源。WAP(手机);HITAO(嗨淘);TOP(TOP平台);TAOBAO(普通淘宝);JHS(聚划算)一笔订单可能同时有以上多个标记，则以逗号分隔
		 */
		/**
		 * 交易状态。可选值:    
		 * TRADE_NO_CREATE_PAY(没有创建支付宝交易)
		 * WAIT_BUYER_PAY(等待买家付款)
		 * SELLER_CONSIGNED_PART(卖家部分发货)
		 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
		 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) 
		 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) 
		 * TRADE_FINISHED(交易成功) 
		 * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) 
		 * TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易) 
		 * PAY_PENDING(国际信用卡支付付款确认中) 
		 * WAIT_PRE_AUTH_CONFIRM(0元购合约中)
		 */
		ArrayList<ShopDataStatistics> tradeList = new ArrayList<ShopDataStatistics>();
		//1.订单数
		long allCount = findTradeCount(userId,null,map.get("minTime"),map.get("maxTime"));
		addShopDataStatistics(tradeList,userId,"订单数",allCount+"");
		//2.无线订单数
		long wapCount = findTradeCount(userId,"WAP",map.get("minTime"),map.get("maxTime"));
		addShopDataStatistics(tradeList,userId,"无线订单数",wapCount+"");
	    //3.PC订单数  
		long pcCount = findTradeCount(userId,"TAOBAO",map.get("minTime"),map.get("maxTime"));
		addShopDataStatistics(tradeList,userId,"PC订单数",pcCount+"");
	    
		
		//4.退款中
		// "status":"TRADE_FINISHED"<--->refundFlag==true
		long refundNum = findTradeNum(userId,"TRADE_FINISHED","modifiedUTC",true,map.get("minTime"),map.get("maxTime"));
		addShopDataStatistics(tradeList,userId,"退款中",refundNum+"");
		//5.待付款
		long waitPayNum = findTradeNum(userId,"TRADE_NO_CREATE_PAY","createdUTC",null,map.get("minTime"),map.get("maxTime"));
		addShopDataStatistics(tradeList,userId,"待付款",waitPayNum+"");
		//6.待发货
		long waitSendNum = findTradeNum(userId,"WAIT_SELLER_SEND_GOODS","modifiedUTC",null,map.get("minTime"),map.get("maxTime"));
		addShopDataStatistics(tradeList,userId,"待发货",waitSendNum+"");
		//7.出售中
	    //查询商品表 <-----------sql查询item--------------->
		Map<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("userId", userId);
		//hashMap.put("minTime", map.get("minTime"));
		hashMap.put("maxTime", map.get("maxTime"));
		long onsale = myBatisDao.findBy(Item.class.getName(), "findCountOnsale",hashMap);
		addShopDataStatistics(tradeList,userId,"出售中",onsale+"");
		
	    //______________________________________________________________
		
		//8.成交额
		double allPayment = findTradePayment(userId,null ,map.get("minTime"),map.get("maxTime"));
		addShopDataStatistics(tradeList,userId,"成交额",new BigDecimal(allPayment).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
		//9.PC成交额
		double pcPayment = findTradePayment(userId,"TAOBAO",map.get("minTime"),map.get("maxTime"));
		addShopDataStatistics(tradeList,userId,"PC成交额",new BigDecimal(pcPayment).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
		//10.无线成交额
		double wapPayment = findTradePayment(userId,"WAP",map.get("minTime"),map.get("maxTime"));
		addShopDataStatistics(tradeList,userId,"无线成交额",new BigDecimal(wapPayment).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
		//11.聚划算成交额
		double jhsPayment = findTradePayment(userId,"JHS",map.get("minTime"),map.get("maxTime"));
		addShopDataStatistics(tradeList,userId,"聚划算成交额",new BigDecimal(jhsPayment).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
	    
		loger.info("统计全部店铺数据耗时"+(System.currentTimeMillis()-s1)+"millis ^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		if(tradeList.isEmpty())
			return null;
		return tradeList;
	}


	/**
	 * @Description:获取昨日,前日的各种订单数据--->查询页
	 * @author jackstraw_yu
	 */
	public List<ShopDataStatistics> queryShopData(String userId, Map<String, Date> map, String type) {
		 /**
		   * "1" "订单数",	"2" "退款中 ,	"3" "待付款 "
		   * "4" "待发货 ,		"5" "出售中 ",	"6" "成交额 "
		   * "13" "聚划算成交额 ",	"14" "PC订单数 ",		"15" "PC成交额 
		   * "16" "无线成交额 ",	"17" "无线订单数 
		   */		
		List<ShopDataStatistics> datas=  new ArrayList<ShopDataStatistics>();
		if(type==null || "".equals(type)){
			datas = getTradeDataByDate(map,userId);
		}else if("1".equals(type)){
			long allCount = findTradeCount(userId,null,map.get("minTime"),map.get("maxTime"));
			addShopDataStatistics(datas,userId,"订单数",allCount+"");
		}else if("2".equals(type)){
			long refundNum = findTradeNum(userId,"TRADE_FINISHED","modifiedUTC",true,map.get("minTime"),map.get("maxTime"));
			addShopDataStatistics(datas,userId,"退款中",refundNum+"");
		}else if("3".equals(type)){
			long waitPayNum = findTradeNum(userId,"TRADE_NO_CREATE_PAY","createdUTC",null,map.get("minTime"),map.get("maxTime"));
			addShopDataStatistics(datas,userId,"待付款",waitPayNum+"");
		}else if("4".equals(type)){
			long waitSendNum = findTradeNum(userId,"WAIT_SELLER_SEND_GOODS","modifiedUTC",null,map.get("minTime"),map.get("maxTime"));
			addShopDataStatistics(datas,userId,"待发货",waitSendNum+"");
		}else if("5".equals(type)){
			Map<String, Object> hashMap = new HashMap<String,Object>();
			hashMap.put("userId", userId);
			//hashMap.put("minTime", map.get("minTime"));
			hashMap.put("maxTime", map.get("maxTime"));
			long onsale = myBatisDao.execute(Item.class.getName(), "findCountOnsale",hashMap);
			addShopDataStatistics(datas,userId,"出售中",onsale+"");
		}else if("6".equals(type)){
			double allPayment = findTradePayment(userId,null ,map.get("minTime"),map.get("maxTime"));
			addShopDataStatistics(datas,userId,"成交额",new BigDecimal(allPayment).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
		}else if("13".equals(type)){
			double jhsPayment = findTradePayment(userId,"JHS",map.get("minTime"),map.get("maxTime"));
			addShopDataStatistics(datas,userId,"聚划算成交额",new BigDecimal(jhsPayment).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
		}else if("14".equals(type)){
			long pcCount = findTradeCount(userId,"TAOBAO",map.get("minTime"),map.get("maxTime"));
			addShopDataStatistics(datas,userId,"PC订单数",pcCount+"");
		}else if("15".equals(type)){
			double pcPayment = findTradePayment(userId,"TAOBAO",map.get("minTime"),map.get("maxTime"));
			addShopDataStatistics(datas,userId,"PC成交额",new BigDecimal(pcPayment).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
		}else if("16".equals(type)){
			double wapPayment = findTradePayment(userId,"WAP",map.get("minTime"),map.get("maxTime"));
			addShopDataStatistics(datas,userId,"无线成交额",new BigDecimal(wapPayment).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
		}else if("17".equals(type)){
			long wapCount = findTradeCount(userId,"WAP",map.get("minTime"),map.get("maxTime"));
			addShopDataStatistics(datas,userId,"无线订单数",wapCount+"");
		}
		if(datas ==null || datas.isEmpty())
			return null;
		return datas;
	}


//	public Map<String, Object> findOrdersBycondition(OrdersVo oVo, Integer pageNo, Date eTime, String userId,
//			List<String> orderList) {
//		//设置起始页
//		if(pageNo == null){
//			pageNo = 1;
//		}
//		//设置每页显示条数为5
//		Integer currentRows = 10;
//		//计算查询初始行数
//		Integer startRows = (pageNo - 1) * currentRows;
//		
//		Pageination<TradeDTO> page = new Pageination<TradeDTO>();
//		
//		Query query = new Query();
//			try {
//				/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");*/
//				if(oVo.getTid() != null && !"".equals(oVo.getTid())){
//					query.addCriteria(Criteria.where("tid").is(oVo.getTid()));
//				}
//				if(oVo.getBuyerNick() != null && !"".equals(oVo.getBuyerNick())){
//					query.addCriteria(Criteria.where("buyerNick").is(oVo.getBuyerNick()));
//				}
//				if(oVo.getStartOrderDate() != null && !"".equals(oVo.getStartOrderDate()) && oVo.getEndOrderDate() != null && !"".equals(oVo.getEndOrderDate())){
//					query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(oVo.getStartOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getEndOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
//				}
//				if(oVo.getConsignTimeBefore() != null && !"".equals(oVo.getConsignTimeBefore()) && oVo.getConsignTimeAfter() != null && !"".equals(oVo.getConsignTimeAfter())){
//					query.addCriteria(Criteria.where("consignTime").gte(DateUtils.stringToLong(oVo.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
////					query.addCriteria(Criteria.where("consignTimeUTC").gte(DateUtils.stringToDate(oVo.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
//				}
//				if(oVo.getOrderFrom() != null && !"".equals(oVo.getOrderFrom())){
//					query.addCriteria(Criteria.where("tradeFrom").is(oVo.getOrderFrom()));
//				}
//				if(oVo.getRefundStatus() != null && !"".equals(oVo.getRefundStatus())){
//					query.addCriteria(Criteria.where("refundStatus").is(oVo.getRefundStatus()));
//				}
//				if(oVo.getPaymentBefore() != null && !"".equals(oVo.getPaymentBefore()) && oVo.getPaymentAfter() != null && !"".equals(oVo.getPaymentAfter())){
//					query.addCriteria(Criteria.where("payment").gte(Double.parseDouble(oVo.getPaymentBefore())).lte(Double.parseDouble(oVo.getPaymentAfter())));
//				}
//				
//				if(oVo.getReceiverDistrict() != null && !"".equals(oVo.getReceiverDistrict())){
//					query.addCriteria(Criteria.where("receiverDistrict").is(oVo.getReceiverDistrict()));
//				}
//				if(oVo.getEndTimeBefore() != null && !"".equals(oVo.getEndTimeBefore()) && oVo.getEndTimeAfter() != null && !"".equals(oVo.getEndTimeAfter())){
//					query.addCriteria(Criteria.where("endTime").gte(DateUtils.stringToLong(oVo.getEndTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getEndTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
//				}
//				
//				if(oVo.getStepTradeStatus() != null && !"".equals(oVo.getStepTradeStatus())){
//					query.addCriteria(Criteria.where("stepTradeStatus").is(oVo.getStepTradeStatus()));
//				}
//				
//				//NUM_IID    
//				if(oVo.getNumIidList() != null && !"".equals(oVo.getNumIidList())){
//					query.addCriteria(Criteria.where("orders.numIid").regex(".*?" + oVo.getNumIidList() + ".*"));
//				}
//				//订单状态
//				if("CLOSED".equals(oVo.getStatus())){
//					query.addCriteria((Criteria.where("status").is("TRADE_CLOSED")).orOperator(Criteria.where("status").is("TRADE_CLOSED_BY_TAOBAO")));
//				}else if(oVo.getStatus() == null || "".equals(oVo.getStatus())){
////					query.addCriteria(Criteria.where("status").is(null));
//				}else if("ALL".equals(oVo.getStatus()) || "TOTAL".equals(oVo.getStatus())){
////					query.addCriteria(Criteria.where("status").is(null));
//				}else{
//					query.addCriteria(Criteria.where("status").is(oVo.getStatus()));
//				}
//				if("双方已评价".equals(oVo.getRateStatus())){
//					query.addCriteria(Criteria.where("buyerRate").is(1));
//					query.addCriteria(Criteria.where("sellerRate").is(1));
////					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				}else if("买家未评".equals(oVo.getRateStatus())){
//					query.addCriteria(Criteria.where("buyerRate").is(0));
//					query.addCriteria(Criteria.where("sellerRate").is(null));
////					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				}else if("卖家未评".equals(oVo.getRateStatus())){
//					query.addCriteria(Criteria.where("sellerRate").is(0));
//					query.addCriteria(Criteria.where("buyerRate").is(null));
////					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				}else if("买家已评，卖家未评".equals(oVo.getRateStatus())){
//					query.addCriteria(Criteria.where("buyerRate").is(1));
//					query.addCriteria(Criteria.where("sellerRate").is(0));
////					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				}else if("买家未评，卖家已评".equals(oVo.getRateStatus())){
//					query.addCriteria(Criteria.where("buyerRate").is(0));
//					query.addCriteria(Criteria.where("sellerRate").is(1));
////					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				}
//				query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
////				query.addCriteria(Criteria.where("tid").is(oVo.getTid()));
//				//地区
//				List<String> stateList = null;
//				if(oVo.getReceiverState() != null && !"".equals(oVo.getReceiverState())){
//					String[] stateArr = oVo.getReceiverState().split(",");
//					stateList = new ArrayList<String>();
//					for(int i = 0;i < stateArr.length; i++){
//						stateList.add(stateArr[i]);
//					}
//					if(stateList == null || stateList.size() == 0){
//						stateList = null;
//					}
//				}
//				if(userId != null && !"".equals(userId)){
//					query.addCriteria(Criteria.where("sellerNick").is(userId));
//				}
////				if(orderList != null && !"".equals(orderList)){
////					query.addCriteria(Criteria.where("tid").nin(orderList));
////				}
//				if(stateList != null && !"".equals(stateList)){
//					query.addCriteria(Criteria.where("receiverState").in(stateList));
//				}
//				//订单标识
//				List<Integer> sellerFlagList = new ArrayList<Integer>();
//				if(oVo.getSellerFlagStr() != null && !"".equals(oVo.getSellerFlagStr())){
//					String[] sellerFlags = (oVo.getSellerFlagStr() + "").split(",");
//					for (int i = 0; i < sellerFlags.length; i++) {
//						sellerFlagList.add(Integer.parseInt(sellerFlags[i]));
//					}
//					query.addCriteria(Criteria.where("sellerFlag").in(sellerFlagList));
//				}else{
//					sellerFlagList = null;
//				}
//				
//				if(startRows != null && !"".equals(startRows)){
//					page.setPageNo(startRows);
//				}else{
//					page.setPageNo(0);
//				}
//				if(currentRows != null && !"".equals(currentRows)){
//					page.setPageSize(currentRows);
//				}else{
//					page.setPageSize(10);
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//查询满足条件的所有结果（分页）
//			List<TradeDTO> limitList = tradeRepository.find(query, userId);
//			Pageination<TradeDTO> pagination = tradeRepository.findPage(page, query, userId);
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			resultMap.put("list", limitList);
//			resultMap.put("pagination", pagination);
//			return resultMap;
//	}
	
	public Pageination<TradeDTO> findOrdersByPageination(OrdersVo oVo, Integer pageNo, Date eTime, String userId,
			List<String> orderList) {
		//设置起始页
		if(pageNo == null){
			pageNo = 1;
		}
		//设置每页显示条数为5
		Integer currentRows = 10;
		//计算查询初始行数
		Integer startRows = (pageNo - 1) * currentRows;
		
		Pageination<TradeDTO> page = new Pageination<TradeDTO>();
		
		Query query = new Query();
			try {
				/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");*/
				if(oVo.getTid() != null && !"".equals(oVo.getTid())){
					query.addCriteria(Criteria.where("tid").is(oVo.getTid()));
				}
				if(oVo.getBuyerNick() != null && !"".equals(oVo.getBuyerNick())){
					query.addCriteria(Criteria.where("buyerNick").is(oVo.getBuyerNick()));
				}
				if(oVo.getStartOrderDate() != null && !"".equals(oVo.getStartOrderDate()) && oVo.getEndOrderDate() != null && !"".equals(oVo.getEndOrderDate())){
					query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(oVo.getStartOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getEndOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
//					query.addCriteria(Criteria.where("createdUTC").gte(DateUtils.stringToDate(oVo.getStartOrderDate(), DateUtils.DEFAULT_DATE_FORMAT)).lte(DateUtils.stringToDate(oVo.getEndOrderDate(), DateUtils.DEFAULT_DATE_FORMAT)));
				}
				if(oVo.getConsignTimeBefore() != null && !"".equals(oVo.getConsignTimeBefore()) && oVo.getConsignTimeAfter() != null && !"".equals(oVo.getConsignTimeAfter())){
					query.addCriteria(Criteria.where("consignTime").gte(DateUtils.stringToLong(oVo.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
//					query.addCriteria(Criteria.where("consignTimeUTC").gte(DateUtils.stringToDate(oVo.getConsignTimeBefore(), DateUtils.DEFAULT_DATE_FORMAT)).lte(DateUtils.stringToDate(oVo.getConsignTimeAfter(), DateUtils.DEFAULT_DATE_FORMAT)));
				}
				if(oVo.getOrderFrom() != null && !"".equals(oVo.getOrderFrom())){
					query.addCriteria(Criteria.where("tradeFrom").is(oVo.getOrderFrom()));
				}
				if(oVo.getRefundStatus() != null && !"".equals(oVo.getRefundStatus())){
					query.addCriteria(Criteria.where("refundStatus").is(oVo.getRefundStatus()));
				}
				if(oVo.getPaymentBefore() != null && !"".equals(oVo.getPaymentBefore()) && oVo.getPaymentAfter() != null && !"".equals(oVo.getPaymentAfter())){
					query.addCriteria(Criteria.where("payment").gte(Double.parseDouble(oVo.getPaymentBefore())).lte(Double.parseDouble(oVo.getPaymentAfter())));
				}
				
				if(oVo.getReceiverDistrict() != null && !"".equals(oVo.getReceiverDistrict())){
					query.addCriteria(Criteria.where("receiverDistrict").is(oVo.getReceiverDistrict()));
				}
				if(oVo.getEndTimeBefore() != null && !"".equals(oVo.getEndTimeBefore()) && oVo.getEndTimeAfter() != null && !"".equals(oVo.getEndTimeAfter())){
					query.addCriteria(Criteria.where("endTime").gte(DateUtils.stringToLong(oVo.getEndTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getEndTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
				}
				
				if(oVo.getStepTradeStatus() != null && !"".equals(oVo.getStepTradeStatus())){
					query.addCriteria(Criteria.where("stepTradeStatus").is(oVo.getStepTradeStatus()));
				}
				
				//NUM_IID    
				if(oVo.getNumIidList() != null && !"".equals(oVo.getNumIidList())){
					query.addCriteria(Criteria.where("orders.numIid").regex(".*?" + oVo.getNumIidList() + ".*"));
				}
				//订单状态
				if("CLOSED".equals(oVo.getStatus())){
					query.addCriteria((Criteria.where("status").is("TRADE_CLOSED")).orOperator(Criteria.where("status").is("TRADE_CLOSED_BY_TAOBAO")));
				}else if(oVo.getStatus() == null || "".equals(oVo.getStatus())){
//					query.addCriteria(Criteria.where("status").is(null));
				}else if("ALL".equals(oVo.getStatus()) || "TOTAL".equals(oVo.getStatus())){
//					query.addCriteria(Criteria.where("status").is(null));
				}else{
					query.addCriteria(Criteria.where("status").is(oVo.getStatus()));
				}
				if("双方已评价".equals(oVo.getRateStatus())){
					query.addCriteria(Criteria.where("buyerRate").is(1));
					query.addCriteria(Criteria.where("sellerRate").is(1));
				}else if("买家未评".equals(oVo.getRateStatus())){
					query.addCriteria(Criteria.where("buyerRate").is(0));
					query.addCriteria(Criteria.where("sellerRate").is(null));
				}else if("卖家未评".equals(oVo.getRateStatus())){
					query.addCriteria(Criteria.where("sellerRate").is(0));
					query.addCriteria(Criteria.where("buyerRate").is(null));
				}else if("买家已评，卖家未评".equals(oVo.getRateStatus())){
					query.addCriteria(Criteria.where("buyerRate").is(1));
					query.addCriteria(Criteria.where("sellerRate").is(0));
				}else if("买家未评，卖家已评".equals(oVo.getRateStatus())){
					query.addCriteria(Criteria.where("buyerRate").is(0));
					query.addCriteria(Criteria.where("sellerRate").is(1));
				}
				query.addCriteria(Criteria.where("status").is(TradeStatusUtils.TRADE_FINISHED));
				//地区
				List<String> stateList = null;
				if(oVo.getReceiverState() != null && !"".equals(oVo.getReceiverState())){
					String[] stateArr = oVo.getReceiverState().split(",");
					stateList = new ArrayList<String>();
					for(int i = 0;i < stateArr.length; i++){
						stateList.add(stateArr[i]);
					}
					if(stateList == null || stateList.size() == 0){
						stateList = null;
					}
				}
				if(userId != null && !"".equals(userId)){
					query.addCriteria(Criteria.where("sellerNick").is(userId));
				}
				if(orderList != null && !"".equals(orderList)){
					query.addCriteria(Criteria.where("tid").nin(orderList));
				}
				if(stateList != null && !"".equals(stateList)){
					query.addCriteria(Criteria.where("receiverState").in(stateList));
				}
				//订单标识
				List<Integer> sellerFlagList = new ArrayList<Integer>();
				if(oVo.getSellerFlagStr() != null && !"".equals(oVo.getSellerFlagStr())){
					String[] sellerFlags = (oVo.getSellerFlagStr() + "").split(",");
					for (int i = 0; i < sellerFlags.length; i++) {
						sellerFlagList.add(Integer.parseInt(sellerFlags[i]));
					}
					query.addCriteria(Criteria.where("sellerFlag").in(sellerFlagList));
				}else{
					sellerFlagList = null;
				}
				
				if(startRows != null && !"".equals(startRows)){
					page.setPageNo(startRows);
				}else{
					page.setPageNo(0);
				}
				if(currentRows != null && !"".equals(currentRows)){
					page.setPageSize(currentRows);
				}else{
					page.setPageSize(10);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			//查询满足条件的所有结果（分页）
			Pageination<TradeDTO> pagination = tradeRepository.findPage(page, query, userId);
			return pagination;
	}
	
	public List<TradeDTO> findTradeDTOByList(OrdersVo oVo, Integer pageNo, Date eTime, String userId,
			List<String> orderList) {
		//设置起始页
		if(pageNo == null){
			pageNo = 1;
		}
		//设置每页显示条数为5
		Integer currentRows = 10;
		//计算查询初始行数
		Integer startRows = (pageNo - 1) * currentRows;
		
		Pageination<TradeDTO> page = new Pageination<TradeDTO>();
		
		Query query = new Query();
			try {
				/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");*/
				if(oVo.getTid() != null && !"".equals(oVo.getTid())){
					query.addCriteria(Criteria.where("tid").is(oVo.getTid()));
				}
				if(oVo.getBuyerNick() != null && !"".equals(oVo.getBuyerNick())){
					query.addCriteria(Criteria.where("buyerNick").is(oVo.getBuyerNick()));
				}
				if(oVo.getStartOrderDate() != null && !"".equals(oVo.getStartOrderDate()) && oVo.getEndOrderDate() != null && !"".equals(oVo.getEndOrderDate())){
					query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(oVo.getStartOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getEndOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
				}
				if(oVo.getConsignTimeBefore() != null && !"".equals(oVo.getConsignTimeBefore()) && oVo.getConsignTimeAfter() != null && !"".equals(oVo.getConsignTimeAfter())){
					query.addCriteria(Criteria.where("consignTime").gte(DateUtils.stringToLong(oVo.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
//					query.addCriteria(Criteria.where("consignTimeUTC").gte(DateUtils.stringToDate(oVo.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
				}
				if(oVo.getOrderFrom() != null && !"".equals(oVo.getOrderFrom())){
					query.addCriteria(Criteria.where("tradeFrom").is(oVo.getOrderFrom()));
				}
				if(oVo.getRefundStatus() != null && !"".equals(oVo.getRefundStatus())){
					query.addCriteria(Criteria.where("refundStatus").is(oVo.getRefundStatus()));
				}
				if(oVo.getPaymentBefore() != null && !"".equals(oVo.getPaymentBefore()) && oVo.getPaymentAfter() != null && !"".equals(oVo.getPaymentAfter())){
					query.addCriteria(Criteria.where("payment").gte(Double.parseDouble(oVo.getPaymentBefore())).lte(Double.parseDouble(oVo.getPaymentAfter())));
				}
				
				if(oVo.getReceiverDistrict() != null && !"".equals(oVo.getReceiverDistrict())){
					query.addCriteria(Criteria.where("receiverDistrict").is(oVo.getReceiverDistrict()));
				}
				if(oVo.getEndTimeBefore() != null && !"".equals(oVo.getEndTimeBefore()) && oVo.getEndTimeAfter() != null && !"".equals(oVo.getEndTimeAfter())){
					query.addCriteria(Criteria.where("endTime").gte(DateUtils.stringToLong(oVo.getEndTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(oVo.getEndTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
				}
				
				if(oVo.getStepTradeStatus() != null && !"".equals(oVo.getStepTradeStatus())){
					query.addCriteria(Criteria.where("stepTradeStatus").is(oVo.getStepTradeStatus()));
				}
				
				//NUM_IID    
				if(oVo.getNumIidList() != null && !"".equals(oVo.getNumIidList())){
					query.addCriteria(Criteria.where("orders.numIid").regex(".*?" + oVo.getNumIidList() + ".*"));
				}
				//订单状态
				if("CLOSED".equals(oVo.getStatus())){
					query.addCriteria((Criteria.where("status").is("TRADE_CLOSED")).orOperator(Criteria.where("status").is("TRADE_CLOSED_BY_TAOBAO")));
				}else if(oVo.getStatus() == null || "".equals(oVo.getStatus())){
//					query.addCriteria(Criteria.where("status").is(null));
				}else if("ALL".equals(oVo.getStatus()) || "TOTAL".equals(oVo.getStatus())){
//					query.addCriteria(Criteria.where("status").is(null));
				}else{
					query.addCriteria(Criteria.where("status").is(oVo.getStatus()));
				}
				if("双方已评价".equals(oVo.getRateStatus())){
					query.addCriteria(Criteria.where("buyerRate").is(1));
					query.addCriteria(Criteria.where("sellerRate").is(1));
//					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
				}else if("买家未评".equals(oVo.getRateStatus())){
					query.addCriteria(Criteria.where("buyerRate").is(0));
					query.addCriteria(Criteria.where("sellerRate").is(null));
//					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
				}else if("卖家未评".equals(oVo.getRateStatus())){
					query.addCriteria(Criteria.where("sellerRate").is(0));
					query.addCriteria(Criteria.where("buyerRate").is(null));
//					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
				}else if("买家已评，卖家未评".equals(oVo.getRateStatus())){
					query.addCriteria(Criteria.where("buyerRate").is(1));
					query.addCriteria(Criteria.where("sellerRate").is(0));
//					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
				}else if("买家未评，卖家已评".equals(oVo.getRateStatus())){
					query.addCriteria(Criteria.where("buyerRate").is(0));
					query.addCriteria(Criteria.where("sellerRate").is(1));
//					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
				}
				query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				query.addCriteria(Criteria.where("tid").is(oVo.getTid()));
				//地区
				List<String> stateList = null;
				if(oVo.getReceiverState() != null && !"".equals(oVo.getReceiverState())){
					String[] stateArr = oVo.getReceiverState().split(",");
					stateList = new ArrayList<String>();
					for(int i = 0;i < stateArr.length; i++){
						stateList.add(stateArr[i]);
					}
					if(stateList == null || stateList.size() == 0){
						stateList = null;
					}
				}
				if(userId != null && !"".equals(userId)){
					query.addCriteria(Criteria.where("sellerNick").is(userId));
				}
//				if(orderList != null && !"".equals(orderList)){
//					query.addCriteria(Criteria.where("tid").nin(orderList));
//				}
				if(stateList != null && !"".equals(stateList)){
					query.addCriteria(Criteria.where("receiverState").in(stateList));
				}
				//订单标识
				List<Integer> sellerFlagList = new ArrayList<Integer>();
				if(oVo.getSellerFlagStr() != null && !"".equals(oVo.getSellerFlagStr())){
					String[] sellerFlags = (oVo.getSellerFlagStr() + "").split(",");
					for (int i = 0; i < sellerFlags.length; i++) {
						sellerFlagList.add(Integer.parseInt(sellerFlags[i]));
					}
					query.addCriteria(Criteria.where("sellerFlag").in(sellerFlagList));
				}else{
					sellerFlagList = null;
				}
				
				if(startRows != null && !"".equals(startRows)){
					page.setPageNo(startRows);
				}else{
					page.setPageNo(0);
				}
				if(currentRows != null && !"".equals(currentRows)){
					page.setPageSize(currentRows);
				}else{
					page.setPageSize(10);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			//查询满足条件的所有结果（分页）
			List<TradeDTO> limitList = tradeRepository.find(query, userId);
			return limitList;
	}
	
	/**
	 * Gg
	 * 查询 buyer Nick 是明文还是密文
	 * @param userNickName
	 * @param buyerNick
	 * @return
	 * Gg
	 */
	public long quneryBuyerNickCount(String userNickName,String buyerNick){
		Query q = new Query();
		q.addCriteria(Criteria.where("buyerNick").is(buyerNick));
		long buyerNickCount =tradeRepository.count(q, userNickName);
		return buyerNickCount;
	}
	/**
	 * Gg
	 * 会员信息--订单信息查询
	 * @param contextPath
	 * @param pageNo
	 * @param userNickName
	 * @param ordersVoParam
	 * @param buyerNick
	 * @return
	 * Gg
	 * @throws SecretException 
	 */
	public Pageination<TradeDTO> findByTradeDTOList(String contextPath, Integer pageNo,
			 String userNickName,OrdersVo ordersVoParam,String buyerNick,String session)throws SecretException{
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		//先设置每页显示的条数为15条
		Integer currentRows = 15;
		Query query = new Query();
		//封装params
		StringBuilder params = this.tradeInfoParams(userNickName, ordersVoParam,buyerNick);
		//封装page
		Pageination<TradeDTO> page = new Pageination<TradeDTO>();
		page.setPageNo(pageNo);
		page.setPageSize(currentRows);
		//查询数据 buyerNick 是明文还是密文
		Long count = this.quneryBuyerNickCount(userNickName, buyerNick);
		if(count==0){
			String encryptionBuyerNick = securityClient.encrypt(buyerNick, EncrptAndDecryptClient.SEARCH, session);
			try {
				query.addCriteria(Criteria.where("buyerNick").is(encryptionBuyerNick));
			} catch (Exception e) {
				query.addCriteria(Criteria.where("buyerNick").is(buyerNick));
				loger.info("*********************用户"+userNickName+"的会员"+buyerNick+"解密出错，session可能过期或者为空！*****************");
			}
		}else{
			query.addCriteria(Criteria.where("buyerNick").is(buyerNick));
		}
		query.with(new Sort(new Sort.Order(Direction.DESC,"createdUTC")));
		packageCriteriaParam(query, ordersVoParam);
		//查询数据
		if(null!=userNickName&&!"".equals(userNickName)){
			page =tradeRepository.findPage(page, query, userNickName);
		}
		//封装分页数据
		page.pageView(contextPath, params.toString());
		return page;
		
	}
	
	/**
	 * Gg
	 * 分页条件封装
	 * @param userNickName
	 * @param ordersVoParam
	 * @param buyerNick
	 * @return
	 * Gg
	 */
	private StringBuilder tradeInfoParams(String userNickName,OrdersVo ordersVoParam,String buyerNick){
		StringBuilder params = new StringBuilder();
		if(userNickName!=null && !"".equals(userNickName)){
			params.append("&userId=").append(userNickName);
		}
		if(buyerNick!=null && !"".equals(buyerNick)){
			params.append("&buyerNick=").append(buyerNick);
		}
		if(ordersVoParam.getStatus()!=null && !"".equals(ordersVoParam.getStatus())){
			if(ordersVoParam.getStatus().equals("WAIT_BUYER_PAY")){
				params.append("&status=").append(1);
			}
			if(ordersVoParam.getStatus().equals("WAIT_SELLER_SEND_GOODS")){
				params.append("&status=").append(2);
			}
			if(ordersVoParam.getStatus().equals("WAIT_BUYER_CONFIRM_GOODS")){
				params.append("&status=").append(3);
			}
			if(ordersVoParam.getStatus().equals("TRADE_FINISHED")){
				params.append("&status=").append(4);
			}
			if(ordersVoParam.getStatus().equals("CLOSED")){
				params.append("&status=").append(5);
			}
			/*if(ordersVoParam.getStatus().equals("TRADE_CLOSED_BY_TAOBAO")){
				params.append("&status=").append(5);
			}
			if(ordersVoParam.getStatus().equals("TRADE_CLOSED")){
				params.append("&status=").append(6);
			}*/
			/*params.append("&status=").append(ordersVoParam.getStatus());*/
		}
		if(ordersVoParam.getEndTimeBefore()!=null && !"".equals(ordersVoParam.getEndTimeBefore())){
			params.append("&endTimeBefore=").append(ordersVoParam.getEndTimeBefore());
		}
		if(ordersVoParam.getEndTimeAfter()!=null && !"".equals(ordersVoParam.getEndTimeAfter())){
			params.append("&endTimeAfter=").append(ordersVoParam.getEndTimeAfter());
		}
		
		return params;
	}


	/**
	 * 通过tid查询Trade
	 * 滑静2017年7月13日下午3:30:44
	 */
	public TradeDTO findTradeById(String tid,String userNickName){
		Query query = new Query();
		if(tid != null && !"".equals(tid)){
			query.addCriteria(Criteria.where("tid").is(tid));
		}
		if(tid != null && !"".equals(tid)){
			TradeDTO trade = tradeRepository.findOne(query, userNickName);
			if(trade != null){
				return trade;
			}else {
				return null;
			}
		}else{
			return null;
		}
	}
	

	/**
	* 创建人：邱洋
	* @Title: saveTradeList 
	* @Description: TODO(批量保存订单trade方法) 
	* @param @param list
	* @param @param userId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void saveTradeList(List<TradeDTO> list,String userId){
		tradeRepository.saveList(list, userId);
	}
	
	/**
	* 创建人：邱洋
	* @Title: updateTradeList 
	* @Description: TODO(循环保存订单trade) 
	* @param @param list
	* @param @param userId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateTradeList(List<TradeDTO> list,String userId){
		if(null!=list&&list.size()>0){
			for(TradeDTO td:list){
				Query query = new Query();
				Update update  = new Update();
				query.addCriteria(Criteria.where("tid").is(td.getTid()));
				update.set("sellerNick", td.getSellerNick());
				update.set("pic_path", td.getPicPath());
				update.set("payment", td.getPayment());
				update.set("paymentMoney", td.getPaymentMoney());
				update.set("sellerRate", td.getSellerRate());
				update.set("postFee", td.getPostFee());
				update.set("receiverName", td.getReceiverName());
				update.set("receiverState", td.getReceiverState());
				update.set("receiverAddress", td.getReceiverAddress());
				update.set("receiverZip", td.getReceiverZip());
				update.set("receiverMobile", td.getReceiverMobile());
				update.set("receiverPhone", td.getReceiverPhone());
				update.set("consignTimeUTC", td.getConsignTimeUTC());
				update.set("consignTime", td.getConsignTime());
				update.set("receivedPayment", td.getReceivedPayment());
				update.set("estConTime", td.getEstConTime());
				update.set("invoiceKind", td.getInvoiceKind());
				update.set("receiverCountry", td.getReceiverCountry());
				update.set("receiverTown", td.getReceiverTown());
				update.set("orderTaxFee", td.getOrderTaxFee());
				update.set("paidCouponFee", td.getPaidCouponFee());
				update.set("shopPick", td.getShopPick());
				update.set("num", td.getNum());
				update.set("NUM_IID", td.getNUM_IID());
				update.set("status", td.getStatus());
				update.set("title", td.getTitle());
				update.set("type", td.getType());
				update.set("price", td.getPrice());
				update.set("discountFee", td.getDiscountFee());
				update.set("hasPostFee", td.getHasPostFee());
				update.set("totalFee", td.getTotalFee());
				update.set("createdUTC", td.getCreatedUTC());
				update.set("created", td.getCreated());
				update.set("payTimeUTC", td.getPayTimeUTC());
				update.set("payTime", td.getPayTime());
				update.set("modifiedUTC", td.getModifiedUTC());
				update.set("modified", td.getModified());
				update.set("endTimeUTC", td.getEndTimeUTC());
				update.set("endTime", td.getEndTime());
				update.set("buyerMessage", td.getBuyerMessage());
				update.set("buyerMemo", td.getBuyerMemo());
				update.set("buyerFlag", td.getBuyerFlag());
				update.set("sellerMemo", td.getSellerMemo());
				update.set("sellerFlag", td.getSellerFlag());
				update.set("invoiceName", td.getInvoiceName());
				update.set("invoiceType", td.getInvoiceType());
				update.set("buyerNick", td.getBuyerNick());
				update.set("tradeAttr", td.getTradeAttr());
				update.set("creditCardFee", td.getCreditCardFee());
				update.set("stepTradeStatus", td.getStepTradeStatus());
				update.set("stepPaidFee", td.getStepPaidFee());
				update.set("markDesc", td.getMarkDesc());
				update.set("shippingType", td.getShippingType());
				update.set("buyerCodFee", td.getBuyerCodFee());
				update.set("adjustFee", td.getAdjustFee());
				update.set("tradeFrom", td.getTradeFrom());
				update.set("buyerRate", td.getBuyerRate());
				update.set("receiverCity", td.getReceiverCity());
				update.set("receiverDistrict", td.getReceiverDistrict());
				update.set("o2o", td.getO2o());
				update.set("o2oGuideId", td.getO2oGuideId());
				update.set("o2oShopId", td.getO2oShopId());
				update.set("o2oGuideName", td.getO2oGuideName());
				update.set("o2oShopName", td.getO2oShopName());
				update.set("o2oDelivery", td.getO2oDelivery());
				update.set("eticketServiceAddr", td.getEticketServiceAddr());
				update.set("rxAuditStatus", td.getRxAuditStatus());
				update.set("esRange", td.getEsRange());
				update.set("esDate", td.getEsDate());
				update.set("osDate", td.getOsDate());
				update.set("osRange", td.getOsRange());
				update.set("couponFee", td.getCouponFee());
				update.set("postGateDeclare", td.getPostGateDeclare());
				update.set("crossBondedDeclare", td.getCrossBondedDeclare());
				update.set("omnichannelParam", td.getOmnichannelParam());
				update.set("assembly", td.getAssembly());
				update.set("topHold", td.getTopHold());
				update.set("omniAttr", td.getOmniAttr());
				update.set("omniParam", td.getOmniParam());
				update.set("isShShip", td.getIsShShip());
				update.set("o2oSnatchStatus", td.getO2oSnatchStatus());
				update.set("market", td.getMarket());
				update.set("etType", td.getEtType());
				update.set("etShopId", td.getEtShopId());
				update.set("obs", td.getObs());
				update.set("buyerEmail", td.getBuyerEmail());
				update.set("buyer_alipay_no", td.getBuyer_alipay_no());
				update.set("alipayId", td.getAlipayId());
				update.set("alipayNo", td.getAlipayNo());
				update.set("alipayUrl", td.getAlipayUrl());
				update.set("areaId", td.getAreaId());
				update.set("arriveCutTime", td.getArriveCutTime());
				update.set("arriveInterval", td.getArriveInterval());
				update.set("asyncModified", td.getAsyncModified());
				update.set("buyerArea", td.getBuyerArea());
				update.set("buyerIp", td.getBuyerIp());
				update.set("buyerObtainPointFee", td.getBuyerObtainPointFee());
				update.set("msgId", td.getMsgId());
				update.set("lastSendSmsTime", td.getLastSendSmsTime());
				update.set("orders", td.getOrders());
				tradeRepository.update(query, update, userId);
			}
		}
	}
	
	/**
	* 创建人：邱洋
	* @Title: findTradeByDate 
	* @Description: TODO(查询一定时间范围内卖家未发货的订单) 
	* @param @param userId
	* @param @param bTime
	* @param @param eTime
	* @param @return    设定文件 
	* @return List<TradeDTO>    返回类型 
	* @throws
	 */
	public List<TradeDTO> findTradeByDate(String userId,Long bTime,Long eTime){
		if(bTime<=0L&&eTime<=0L){
			return null;
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("created").lte(eTime).gte(bTime));
		query.addCriteria(Criteria.where("status").is("WAIT_SELLER_SEND_GOODS"));
		return tradeRepository.find(query, userId);
	}
	
	/**
	* 创建人：邱洋
	* @Title: findTradePayTime 
	* @Description: TODO(查询买家的最后一次交易时间) 
	* @param @param userId
	* @param @param buyerNick
	* @param @return    设定文件 
	* @return Date    返回类型 
	* @throws
	 */
	public Date findTradePayTime(String userId,String buyerNick){
		Query query = new Query();
		if(buyerNick!=null&&!"".equals(buyerNick)){
			query.addCriteria(Criteria.where("buyerNick").is(buyerNick));
			query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"payTime")));
		}
		List<TradeDTO> list = tradeRepository.find(query, userId);
		if(list!=null&&list.size()>0){
			Date payTime = list.get(0).getPayTimeUTC();
			return payTime;
		}else{
			return null;
		}
	}
	
	
	/**
	 * @Description:通过oid查询出子订单(中差评查看时调用!)
	 * @author jackstraw_yu
	 */
	public OrdersDTO queryMobileByOrderId(String oid,String userNickName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orders").elemMatch(Criteria.where("oid").is(oid)));
		TradeDTO trade = tradeRepository.findOne(query, userNickName);
		if(trade!=null){
			List<OrdersDTO> orders = trade.getOrders();
			if(orders!=null&&!orders.isEmpty()){
				for (OrdersDTO ordersDTO : orders) {
					if(ordersDTO.getOid().equals(oid)){
						return ordersDTO;
					}
				}
			}
		}
		return null;
	}
	
	
	/**
	 * @Description:查询卖家交易成功且未评价的所有订单,订单结束时间不能大于15天(//加载自动评价订单任务调用)
	 * @author jackstraw_yu
	 */
	public List<OrdersDTO> queryAutoRateOrder(String userNickName) {
		/**
		 * seller_rate=0  
		 * status = 'TRADE_FINISHED' 
		 * end_time BETWEEN date_sub(now(),interval 15 day) AND now()
		 * seller_nick=#{sllerNick}
		*/
		Date maxTime = new Date();
		Date minTime = DateUtils.addDate(maxTime, -15);
		Query query = new Query();
		query.addCriteria(Criteria.where("orders").elemMatch(
				Criteria.where("sellerRate").is(false))
				.and("status").is("TRADE_FINISHED")
				.and("endTimeUTC").lte(maxTime).gte(minTime)
				);
		List<OrdersDTO> dtos = null;
		List<TradeDTO> trades = tradeRepository.find(query, userNickName);
		if(trades!=null && !trades.isEmpty()){
			dtos = new ArrayList<OrdersDTO>();
			for (TradeDTO tradeDTO : trades) {
				List<OrdersDTO> orders = tradeDTO.getOrders();
				if(orders!=null&&!orders.isEmpty()){
					for (OrdersDTO order : orders) {
						if(order.getSellerRate()!=null && order.getSellerRate()==false
						   &&order.getStatus()!=null && "TRADE_FINISHED".equals(order.getStatus()) 
						   && order.getEndTimeUTC()!=null 
						   && minTime.before(order.getEndTimeUTC()) && maxTime.after(order.getEndTimeUTC())
						   )
							dtos.add(order);
					}
				}
			}
		}
		return dtos;
	}

	/**
	 * @Description:查询出子订单(//演示发货提醒?调用)
	 * @author jackstraw_yu
	 */
	public List<OrdersDTO> queryOrdersByTimeAndStatus(HashMap<Object, Object> map) {
		/*status
		seller_nick = #{seller_nick}
		created---startTime-->%Y-%m-%d
		endTime---created*/
		Date minTime =null,maxTime = null;
		String status = null,userNickName=null;
		List<OrdersDTO> dtos = null;
		if(map.get("sellerName")!=null && !"".equals((String)map.get("sellerName"))){
			userNickName = (String)map.get("sellerName");
		}else{
			return dtos;
		}
		if(map.get("startTime")!=null)
			minTime = DateUtils.composeDateTime((Date)map.get("startTime"), true);
		if(map.get("endTime")!=null)
			maxTime = DateUtils.composeDateTime((Date)map.get("endTime"), false);
		if(map.get("status")!=null && "".equals((String)map.get("status")))
			status = (String)map.get("status");
		
		Query query = new Query();
		Criteria elemMatch =null;
		if(minTime!=null&&maxTime!=null) {
			elemMatch = Criteria.where("createdUTC").lte(maxTime).gte(minTime);
		}else{
			if(minTime!=null){
				elemMatch = Criteria.where("createdUTC").gte(minTime);
			}if(maxTime!=null){
				elemMatch = Criteria.where("createdUTC").lte(maxTime);
			}
		}
		if(status!=null){
			if(elemMatch==null){
				elemMatch = Criteria.where("status").is(status);
			}else{
				elemMatch = elemMatch.and("status").is(status);
			}
		}
		if(elemMatch!=null)
			query.addCriteria(Criteria.where("orders").elemMatch(elemMatch));
		List<TradeDTO> trades = tradeRepository.find(query, userNickName);		
		if(trades!=null && !trades.isEmpty()){
			dtos = new ArrayList<OrdersDTO>();
			for (TradeDTO tradeDTO : trades) {
				List<OrdersDTO> orders = tradeDTO.getOrders();
				if(orders!=null&&!orders.isEmpty()){
					for (OrdersDTO order : orders) {
						/*if(order.getStatus()!=null && !"".equals(order.getStatus())
						   && order.getEndTimeUTC()!=null 
						   && minTime.before(order.getEndTimeUTC()) && maxTime.after(order.getEndTimeUTC())
						   )*/
						order.setBuyerNick(tradeDTO.getBuyerNick());
							dtos.add(order);
					}
				}
			}
		}
		return dtos;
	}

	/**
	 * @Description:查询子订单退款状态?(演示发货提醒?调用)
	 * @author jackstraw_yu
	 */
	public List<String> findOrdersByTid(Map<String, Object> map) {
		List<String> list = null;
		if(map.get("userNickName")==null || "".equals((String)map.get("userNickName")))
			return list;
		Query query = new Query();
		query.addCriteria(Criteria.where("tid").is(map.get("tid")));
		TradeDTO trade = tradeRepository.findOne(query, (String)map.get("userNickName"));	
		if(trade!=null){
			List<OrdersDTO> orders = trade.getOrders();
			if(orders!=null && !orders.isEmpty()){
				list = new ArrayList<String>();
				for (OrdersDTO order : orders) {
					list.add(order.getRefundStatus());
				}
			}
		}
		return list;
	}
	
	
	
	/**
	 * 根据条件查询所需的所有数据集合
	 * 滑静20170715
	 */
	public List<OrdersDTO> findOrdersByQuery(String startOrderDate,String endOrderDate,String status,String userId){
		Query query = new Query();
		List<OrdersDTO> findList = null;
		try {
			if(startOrderDate != null && !"".equals(startOrderDate) && endOrderDate != null && !"".equals(endOrderDate)){
//				query.addCriteria(Criteria.where("created").gte(lab.s2jh.core.util.DateUtils.stringToLong(startOrderDate, lab.s2jh.core.util.DateUtils.DEFAULT_TIME_FORMAT)).lte(lab.s2jh.core.util.DateUtils.stringToLong(endOrderDate, lab.s2jh.core.util.DateUtils.DEFAULT_TIME_FORMAT)));
				query.addCriteria(Criteria.where("createdUTC").gte(lab.s2jh.core.util.DateUtils.stringToDate(startOrderDate, lab.s2jh.core.util.DateUtils.DEFAULT_TIME_FORMAT)).lte(lab.s2jh.core.util.DateUtils.stringToDate(endOrderDate, lab.s2jh.core.util.DateUtils.DEFAULT_TIME_FORMAT)));
			}
			if(status != null && !"".equals(status)){
				query.addCriteria(Criteria.where("status").is(status));
			}
			if(userId != null && !"".equals(userId)){
				query.addCriteria(Criteria.where("sellerNick").is(userId));
			}
			List<TradeDTO> list = tradeRepository.find(query, userId);
			findList = new ArrayList<OrdersDTO>();
			for (TradeDTO tradeDTO : list) {
				List<OrdersDTO> orders = tradeDTO.getOrders();
				for (OrdersDTO ordersDTO : orders) {
					findList.add(ordersDTO);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return findList;
	}
	
	
	
	/***
	 * 
	 * description 批量插入Trade 同步sysInfo 数据专用方法
	 * @author zlp
	 * @info  此方法有坑   请勿调用    后果自负
	 * @return
	 */
	public void batchInsertDecryptTradeList(List<TradeDTO> tradeDTOList,String userNickName) {
		if(null!=userNickName&&!"".equals(userNickName)){
			try {
				List<TradeDTO> saveList = new ArrayList<TradeDTO>();
				List<TradeDTO> updateList = new ArrayList<TradeDTO>();
				for (TradeDTO tradeDTO : tradeDTOList) {
					Query query = new Query();
					query.addCriteria(Criteria.where("tid").is(tradeDTO.getTid()));
					TradeDTO tradeInfo = findOne(query,tradeDTO.getSellerNick());
					if(null!=tradeInfo){
						if(null!=tradeInfo.getLastSendSmsTime())tradeDTO.setLastSendSmsTime(tradeInfo.getLastSendSmsTime());
						if(null!=tradeInfo.getMsgId())tradeDTO.setMsgId(tradeInfo.getMsgId());
						if(null!=tradeInfo.getNodeFlag())tradeDTO.setNodeFlag(tradeInfo.getNodeFlag());
						tradeDTO.set_id(tradeInfo.get_id());
						tradeDTO.setCreatedDate( tradeInfo.getCreatedDate() );
						tradeDTO.setLastModifiedDate(new Date());
						tradeDTO.setTimestampId(tradeInfo.getTimestampId());
						updateList.add(tradeDTO);
					}else{
						tradeDTO.setLastSendSmsTime(0l);
						tradeDTO.setMsgId(0l);
						tradeDTO.setCreatedDate( new Date() );
						tradeDTO.setLastModifiedDate( new Date() );
						tradeDTO.setTimestampId(TradeIdWorker.getInstance().nextId());
						saveList.add(tradeDTO);
					}
				}
				if(null!=saveList&&saveList.size()>0){
					tradeRepository.inserBatchDecryptTrade(saveList, userNickName);
				}
				if(null!=updateList&&updateList.size()>0){
					for (TradeDTO tradeInfo : updateList) {
						tradeRepository.save(tradeInfo,userNickName);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
	
	/**
	 * 很据条件统计数量
	 * ZTK2017年7月25日上午10:46:29
	 */
	public long count(Query query , String userId){
		if(userId != null && !"".equals(userId)){
			long count = tradeRepository.count(query, userId);
			return count;
		}
		return 0l;
	}
	
	/**
	 * double类型的sum计算
	 * ZTK2017年7月25日上午11:11:32
	 */
	public double sumPayment(Query query,String userId){
		if(userId != null && !"".equals(userId)){
			double sumPayment = tradeRepository.sumPayment(query, userId);
			return sumPayment;
		}
		return 0.0;
	}

   /*
    * 同步旧库数据到新库数据专用
    */
	public List<TradeDTO>  findMigrateTradeDataList(Query query ,String userNickName,String objectId,int limit,boolean isEqual){
		if(null!=userNickName&&!"".equals(userNickName)&&null!=objectId&&!"".equals(objectId)){
			if(!"0".equals(objectId)){
				if(isEqual){
					query.addCriteria(Criteria.where("_id").gte(new ObjectId(objectId)));
				}else{
					query.addCriteria(Criteria.where("_id").gt(new ObjectId(objectId)));
				}
			}
			query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "_id")));  
			query.skip(0).limit(limit);
			return tradeRepository.find(query,userNickName);  
		}else{
			return null;
		}
	}
	/**
	 * tmc查询mongo的解密后的trade
	 * @author: wy
	 * @time: 2017年9月8日 上午10:21:25
	 * @param tid
	 * @param userNickName
	 * @return
	 */
	public TradeDTO  findOneByTidTMC(String tid,String userNickName){
		if(ValidateUtil.isEmpty(tid)){
			return null;
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("tid").is(tid));
		TradeDTO tradeDTO = tradeRepository.findOne(query,userNickName);  
		if(tradeDTO!=null){
			try {
				if(EncrptAndDecryptClient.isEncryptData(tradeDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
					String session = this.judgeUserUtil.getUserTokenByRedis(tradeDTO.getSellerNick());
					//判断买家昵称是否为密文，如果是则解密
					tradeDTO.setBuyerNick(EncrptAndDecryptClient.getInstance().decrypt(tradeDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH, session));
					//判断买家手机号是否为密文，如果是则解密
					tradeDTO.setReceiverMobile(judgeUserUtil.getDecryptData(tradeDTO.getReceiverMobile(), EncrptAndDecryptClient.PHONE,null, session));
					//判断买家座机是否为密文，如果是则解密
					tradeDTO.setReceiverPhone(judgeUserUtil.getDecryptData(tradeDTO.getReceiverPhone(), EncrptAndDecryptClient.SIMPLE, null, session));
					//判断买家姓名是否为密文，如果是则解密
					tradeDTO.setReceiverName(judgeUserUtil.getDecryptData(tradeDTO.getReceiverName(), EncrptAndDecryptClient.SEARCH,null, session));
					//判断收货人街道地址是否为密文，如果是则解密
					tradeDTO.setReceiverAddress(judgeUserUtil.getDecryptData(tradeDTO.getReceiverAddress(), EncrptAndDecryptClient.SEARCH,null, session));
				}
				return tradeDTO;
			} catch (SecretException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/*private void packageCriteriaParam(BasicDBObject queryObject, OrdersVo ordersVoParam) throws SecretException {
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String sessionKey = userInfoService.validateFindSessionKey(ordersVoParam.getUserId());
//		BasicDBObject queryObject = new BasicDBObject();
		if(null!=ordersVoParam.getTid()&&!"".equals(ordersVoParam.getTid())){
			queryObject.append("tid", ordersVoParam.getTid());
//			query.addCriteria(Criteria.where("tid").is(ordersVoParam.getTid()));  
		}
		if(null!=ordersVoParam.getBuyerNick()&&!"".equals(ordersVoParam.getBuyerNick())){
			if(EncrptAndDecryptClient.isEncryptData(ordersVoParam.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
//				query.addCriteria(Criteria.where("buyerNick").is(ordersVoParam.getBuyerNick()));  
				queryObject.append("buyerNick", ordersVoParam.getBuyerNick());
			}else {
//				query.addCriteria(Criteria.where("buyerNick").is(decryptClient.encryptData(ordersVoParam.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey)));  
				queryObject.append("buyerNick", decryptClient.encryptData(ordersVoParam.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey));
			}
		}
		if(null!=ordersVoParam.getOrderFrom()&&!"".equals(ordersVoParam.getOrderFrom())){
//			query.addCriteria(Criteria.where("tradeFrom").is(ordersVoParam.getOrderFrom()));  
			queryObject.append("tradeFrom", ordersVoParam.getOrderFrom());
		}
		if(null!=ordersVoParam.getRefundStatus()&&!"".equals(ordersVoParam.getRefundStatus())){
//			query.addCriteria(Criteria.where("orders.refundStatus").is(ordersVoParam.getRefundStatus()));  
			queryObject.append("orders.refundStatus", ordersVoParam.getRefundStatus());
		}
		if(null!=ordersVoParam.getReceiverDistrict()&&!"".equals(ordersVoParam.getReceiverDistrict())){
//			query.addCriteria(Criteria.where("receiverCity").is(ordersVoParam.getReceiverDistrict()));  
			queryObject.append("receiverCity", ordersVoParam.getReceiverDistrict());
		}
		if(ordersVoParam.getNumIidList() != null && !"".equals(ordersVoParam.getNumIidList())){
//			query.addCriteria(Criteria.where("orders.numIid").in(ordersVoParam.getNumIidList()));  
			queryObject.append("orders.numIid", ordersVoParam.getNumIidList());
		}
		if(ordersVoParam.getDateEndTimeAfter() != null && ordersVoParam.getDateEndTimeBefor() != null){
//			query.addCriteria(Criteria.where("orders.endTime").gte(ordersVoParam.getDateEndTimeBefor().getTime()).lte(ordersVoParam.getDateEndTimeAfter().getTime()));
			queryObject.append("orders.endTime", new BasicDBObject(QueryOperators.GTE, ordersVoParam.getDateEndTimeBefor().getTime()));
			queryObject.append("orders.endTime", new BasicDBObject(QueryOperators.LTE, ordersVoParam.getDateEndTimeAfter().getTime()));
		}else if(ordersVoParam.getDateEndTimeAfter() == null && ordersVoParam.getDateEndTimeBefor() != null){
//			query.addCriteria(Criteria.where("orders.endTime").gte(ordersVoParam.getDateEndTimeBefor().getTime()));
			queryObject.append("orders.endTime", new BasicDBObject(QueryOperators.GTE, ordersVoParam.getDateEndTimeBefor().getTime()));
		}else if(ordersVoParam.getDateEndTimeAfter() != null && ordersVoParam.getDateEndTimeBefor() == null){
//			query.addCriteria(Criteria.where("orders.endTime").lte(ordersVoParam.getDateEndTimeAfter().getTime()));
			queryObject.append("orders.endTime", new BasicDBObject(QueryOperators.LTE, ordersVoParam.getDateEndTimeAfter().getTime()));
		}
		if(ordersVoParam.getStatus() != null && !"".equals(ordersVoParam.getStatus())){
			if("ALL".equals(ordersVoParam.getStatus()) || "TOTAL".equals(ordersVoParam.getStatus())){
			}else if("CLOSED".equals(ordersVoParam.getStatus())){
//				Criteria c  = new  Criteria(); 
//				c.orOperator(Criteria.where("status").is("TRADE_CLOSED"),Criteria.where("status").is("TRADE_CLOSED_BY_TAOBAO"));
//				query.addCriteria(c);
				BasicDBList values = new BasicDBList();
				values.add("TRADE_CLOSED");
				values.add("TRADE_CLOSED_BY_TAOBAO");
				queryObject.append("status", new BasicDBObject(QueryOperators.IN, values));
			}else {
//				query.addCriteria(Criteria.where("status").is(ordersVoParam.getStatus()));  
				queryObject.append("status", ordersVoParam.getStatus());
			}
		}
		//地区
		if(ordersVoParam.getReceiverState() != null && !"".equals(ordersVoParam.getReceiverState())){
			String[] stateArr = ordersVoParam.getReceiverState().split(",");
			List<String> stateList = Arrays.asList(stateArr);
			if(stateList != null && !stateList.isEmpty()){
				BasicDBList values = new BasicDBList();
				values.addAll(stateList);
				queryObject.append("receiverState", new BasicDBObject(QueryOperators.IN, values));
//				query.addCriteria(Criteria.where("receiverState").in(stateList));
			}
		}
//		List<String> sellerFlagList = new ArrayList<String>();
		if(ordersVoParam.getSellerFlagStr() != null && !"".equals(ordersVoParam.getSellerFlagStr())){
			String[] sellerFlags = (ordersVoParam.getSellerFlagStr() + "").split(",");
			BasicDBList values = new BasicDBList();
			for (int i = 0; i < sellerFlags.length; i++) {
				values.add(sellerFlags[i]);
			}
			if(null!=values && values.size()>0){
//				query.addCriteria(Criteria.where("sellerFlag").in(sellerFlagList));   
				queryObject.append("sellerFlag", new BasicDBObject(QueryOperators.IN, values));
			}
		} 
		
		if("双方已评价".equals(ordersVoParam.getRateStatus())){
//			query.addCriteria(Criteria.where("buyerRate").is(true).andOperator(Criteria.where("sellerRate").is(true).andOperator(Criteria.where("status").is("TRADE_FINISHED"))));  
			queryObject.append("buyerRate", true);queryObject.append("sellerRate", true);queryObject.append("status", "TRADE_FINISHED");
		}else if("买家未评".equals(ordersVoParam.getRateStatus())){
//			query.addCriteria(Criteria.where("buyerRate").is(false).andOperator(Criteria.where("status").is("TRADE_FINISHED")));  
			queryObject.append("buyerRate", false);queryObject.append("status", "TRADE_FINISHED");
		}else if("卖家未评".equals(ordersVoParam.getRateStatus())){
//			query.addCriteria(Criteria.where("sellerRate").is(false).andOperator(Criteria.where("status").is("TRADE_FINISHED")));  
			queryObject.append("sellerRate", false);queryObject.append("status", "TRADE_FINISHED");
		}else if("买家已评，卖家未评".equals(ordersVoParam.getRateStatus())){
//			query.addCriteria(Criteria.where("buyerRate").is(true).andOperator(Criteria.where("sellerRate").is(false).andOperator(Criteria.where("status").is("TRADE_FINISHED"))));  
			queryObject.append("buyerRate", true);queryObject.append("sellerRate", false);queryObject.append("status", "TRADE_FINISHED");
		}else if("买家未评，卖家已评".equals(ordersVoParam.getRateStatus())){
//			query.addCriteria(Criteria.where("buyerRate").is(false).andOperator(Criteria.where("sellerRate").is(true).andOperator(Criteria.where("status").is("TRADE_FINISHED"))));  
			queryObject.append("buyerRate", false);queryObject.append("sellerRate", true);queryObject.append("status", "TRADE_FINISHED");
		}
		
		if(null!=ordersVoParam.getMsgIdList()&&ordersVoParam.getMsgIdList().size()>0){
//			query.addCriteria(Criteria.where("msgId").nin(ordersVoParam.getMsgIdList()));  
			BasicDBList values = new BasicDBList();
			values.addAll(ordersVoParam.getMsgIdList());
			queryObject.append("msgId", new BasicDBObject(QueryOperators.NIN,values));
		}
		
		//交易时间查询
		if(ordersVoParam.getStartTime()!=null && !"".equals(ordersVoParam.getStartTime())&&ordersVoParam.getEndTime()!=null && !"".equals(ordersVoParam.getEndTime())){
			try {
//				query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(ordersVoParam.getStartTime(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(ordersVoParam.getEndTime(), DateUtils.DEFAULT_TIME_FORMAT)));
				queryObject.append("created", new BasicDBObject(QueryOperators.GTE, DateUtils.stringToLong(ordersVoParam.getStartTime(), DateUtils.DEFAULT_TIME_FORMAT)));
				queryObject.append("created", new BasicDBObject(QueryOperators.LTE, DateUtils.stringToLong(ordersVoParam.getEndTime(), DateUtils.DEFAULT_TIME_FORMAT)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			if(ordersVoParam.getStartTime() != null && !"".equals(ordersVoParam.getStartTime())){
				try {
//					query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(ordersVoParam.getStartTime(), DateUtils.DEFAULT_TIME_FORMAT)));
					queryObject.append("created", new BasicDBObject(QueryOperators.GTE, DateUtils.stringToLong(ordersVoParam.getStartTime(), DateUtils.DEFAULT_TIME_FORMAT)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				if(ordersVoParam.getEndTime()!=null && !"".equals(ordersVoParam.getEndTime())){
					try {
//						query.addCriteria(Criteria.where("created").lte(DateUtils.stringToLong(ordersVoParam.getEndTime(),DateUtils.DEFAULT_TIME_FORMAT)));
						queryObject.append("created", new BasicDBObject(QueryOperators.LTE, DateUtils.stringToLong(ordersVoParam.getEndTime(), DateUtils.DEFAULT_TIME_FORMAT)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		handleTimeCriteria(queryObject, ordersVoParam);
	}


	private void handleTimeCriteria(BasicDBObject queryObject, OrdersVo ordersVoParam) {
		try {
			if(ordersVoParam.getStartOrderDate() != null && !"".equals(ordersVoParam.getStartOrderDate())&&ordersVoParam.getEndOrderDate() != null && !"".equals(ordersVoParam.getEndOrderDate())){
//				query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(ordersVoParam.getStartOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(ordersVoParam.getEndOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
				queryObject.append("created", new BasicDBObject(QueryOperators.GTE, DateUtils.stringToLong(ordersVoParam.getStartOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
				queryObject.append("created", new BasicDBObject(QueryOperators.LTE, DateUtils.stringToLong(ordersVoParam.getEndOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
			}else{
				if(ordersVoParam.getStartOrderDate() != null && !"".equals(ordersVoParam.getStartOrderDate())){
//					query.addCriteria(Criteria.where("created").gte(DateUtils.stringToLong(ordersVoParam.getStartOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
					queryObject.append("created", new BasicDBObject(QueryOperators.GTE, DateUtils.stringToLong(ordersVoParam.getStartOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
				}else if(ordersVoParam.getEndOrderDate() != null && !"".equals(ordersVoParam.getEndOrderDate())){
//					query.addCriteria(Criteria.where("created").lte(DateUtils.stringToLong(ordersVoParam.getEndOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
					queryObject.append("created", new BasicDBObject(QueryOperators.LTE, DateUtils.stringToLong(ordersVoParam.getEndOrderDate(), DateUtils.DEFAULT_TIME_FORMAT)));
				}
			}
			if(ordersVoParam.getConsignTimeBefore() != null && !"".equals(ordersVoParam.getConsignTimeBefore())&&ordersVoParam.getConsignTimeAfter() != null && !"".equals(ordersVoParam.getConsignTimeAfter())){
//				query.addCriteria(Criteria.where("consignTime").gte(DateUtils.stringToLong(ordersVoParam.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(ordersVoParam.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
				queryObject.append("consignTime", new BasicDBObject(QueryOperators.GTE, DateUtils.stringToLong(ordersVoParam.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)));
				queryObject.append("consignTime", new BasicDBObject(QueryOperators.LTE, DateUtils.stringToLong(ordersVoParam.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
			}else{
				if(ordersVoParam.getConsignTimeBefore() != null && !"".equals(ordersVoParam.getConsignTimeBefore())){
//					query.addCriteria(Criteria.where("consignTime").gte(DateUtils.stringToLong(ordersVoParam.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)));
					queryObject.append("consignTime", new BasicDBObject(QueryOperators.GTE, DateUtils.stringToLong(ordersVoParam.getConsignTimeBefore(), DateUtils.DEFAULT_TIME_FORMAT)));
				}else if(ordersVoParam.getConsignTimeAfter() != null && !"".equals(ordersVoParam.getConsignTimeAfter())){
//					query.addCriteria(Criteria.where("consignTime").lte(DateUtils.stringToLong(ordersVoParam.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
					queryObject.append("consignTime", new BasicDBObject(QueryOperators.LTE, DateUtils.stringToLong(ordersVoParam.getConsignTimeAfter(), DateUtils.DEFAULT_TIME_FORMAT)));
				}
			}
			if(ordersVoParam.getPaymentBefore() != null && !"".equals(ordersVoParam.getPaymentBefore())&&ordersVoParam.getPaymentAfter() != null && !"".equals(ordersVoParam.getPaymentAfter())){
//				query.addCriteria(Criteria.where("totalFee").gte(Double.parseDouble(ordersVoParam.getPaymentBefore())).lte(Double.parseDouble(ordersVoParam.getPaymentAfter())));
				queryObject.append("totalFee", new BasicDBObject(QueryOperators.GTE, Double.parseDouble(ordersVoParam.getPaymentBefore())));
				queryObject.append("totalFee", new BasicDBObject(QueryOperators.LTE, Double.parseDouble(ordersVoParam.getPaymentAfter())));
			}else{
				if(ordersVoParam.getPaymentBefore() != null && !"".equals(ordersVoParam.getPaymentBefore())){
//					query.addCriteria(Criteria.where("totalFee").gte(Double.parseDouble(ordersVoParam.getPaymentBefore())));
					queryObject.append("totalFee", new BasicDBObject(QueryOperators.GTE, Double.parseDouble(ordersVoParam.getPaymentBefore())));
				}else if(ordersVoParam.getPaymentAfter() != null && !"".equals(ordersVoParam.getPaymentAfter())){
//					query.addCriteria(Criteria.where("totalFee").lte(Double.parseDouble(ordersVoParam.getPaymentAfter())));
					queryObject.append("totalFee", new BasicDBObject(QueryOperators.LTE, Double.parseDouble(ordersVoParam.getPaymentAfter())));
				}
			}
		} catch (ParseException e) {
		}
	}*/
}
