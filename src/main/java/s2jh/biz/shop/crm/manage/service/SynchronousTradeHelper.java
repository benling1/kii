package s2jh.biz.shop.crm.manage.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.dao.mybatis.MyBatisDaoT;
import lab.s2jh.core.handler.impl.DefaultHandlerChain;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.util.DateUtils;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.dao.SmsRecordRepository;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.util.InitDataManageUtil;
import s2jh.biz.shop.crm.manage.vo.TradeFullinfoGetResponse;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.order.pojo.TbTransactionOrder;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.AreaUtils;
import s2jh.biz.shop.utils.BigDecimalUtil;
import s2jh.biz.shop.utils.ConstantUtils;

import com.taobao.api.internal.util.TaobaoUtils;

@Service
public class SynchronousTradeHelper {
	
	private static final Logger loger = Logger.getLogger(SynchronousTradeHelper.class);

	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private MyBatisDaoT myBatisDaoT;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private DefaultHandlerChain memberHandlerChain;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	@Autowired
	private TradeInfoService tradeInfoService;
	@Autowired
	private VipMemberService vipMemberService;
	@Autowired
	private SmsRecordService smsRecordService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SmsRecordRepository smsRecordRepository;

	 
	public void syncSmsRecordData(Map map) {
		if(null!=map){
			String userNickName=(String)map.get("userNickName");
			try {
			    long endTime = DateUtils.stringToLong("2017-11-15 00:00:00", DateUtils.DEFAULT_TIME_FORMAT);
				Query query = new Query();
				query.addCriteria(Criteria.where("userId").is(userNickName));
				query.addCriteria(Criteria.where("sendLongTime").lte(endTime));
			    long dataCount = smsRecordRepository.count(query, userNickName);
			    loger.info(userNickName+"短信记录"+dataCount+"条");
		    	List<SmsRecordDTO> recordList = smsRecordService.findMigrateRecordDataList(query ,userNickName, "0", 1);
		    	loger.info(userNickName+"第一条删除保存开始！");
		    	if(null!=recordList&&recordList.size()>0){
		    		cacheService.putNoTime(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_RECORD_DATA_KEY,recordList.get(0).get_id());
		    		for (SmsRecordDTO smsRecordDTO : recordList) {
		    			query.addCriteria(Criteria.where("_id").is(new ObjectId(smsRecordDTO.get_id())));
		    			smsRecordDTO.set_id(null);
		    		}
		    		
//			    	smsRecordService.batchInsertSmsReocrdBackUp(recordList, userNickName);
//					smsRecordService.removeSmsRecordList(query ,userNickName, "0", 1);
					loger.info(userNickName+"第一条删除保存完成！");
			    	dataCount = dataCount-1;
			 	    Long pageSize = ConstantUtils.PROCESS_PAGE_SIZE_MAX;
			    	while (true) {
			    	   List<ObjectId> oidList = new ArrayList<ObjectId>();
		    		   Query query1 = new Query();
		    		   query1.addCriteria(Criteria.where("userId").is(userNickName));
		    		   query1.addCriteria(Criteria.where("sendLongTime").lte(endTime));
		    		   String id = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_RECORD_DATA_KEY);
					   if(dataCount<=ConstantUtils.PROCESS_PAGE_SIZE_MAX){
						   pageSize = dataCount;
						   
					   }
					   List<SmsRecordDTO> resultRecordList = smsRecordService.findMigrateRecordDataList(query1 ,userNickName,id,pageSize.intValue());
					   dataCount  = dataCount -ConstantUtils.PROCESS_PAGE_SIZE_MAX;
					   SmsRecordDTO lastSmsRecordDTO  = resultRecordList.get(resultRecordList.size()-1);
					   
					   cacheService.putNoTime(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_RECORD_DATA_KEY,lastSmsRecordDTO.get_id());
					   if(null!=resultRecordList&&resultRecordList.size()>0){
						   for (SmsRecordDTO smsRecordDTO : resultRecordList) {
							   oidList.add(new ObjectId(smsRecordDTO.get_id()));
							   smsRecordDTO.set_id(null);
						   }
					      Query query2 = new Query();
				   	      query2.addCriteria(Criteria.where("userId").is(userNickName));
				   	      query2.addCriteria(Criteria.where("sendLongTime").lte(endTime));
				   	      query2.addCriteria(Criteria.where("_id").in(oidList));
//						  smsRecordService.batchInsertSmsReocrdBackUp(resultRecordList, userNickName);
//						  smsRecordService.removeSmsRecordList(query2 ,userNickName,id,pageSize.intValue());
					   }
					   if(dataCount<=0){
						   break;
					   }
			    	}
		    	}
		    	loger.info("同步"+userNickName+"结束！");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void syncTbTradeData(Map map) {
		// 查询出的任务节点
		// 从redis 缓存中获取repair节点数据
		if(null==map) map = new HashMap();
		String startTime = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.TRADE_NODE_KEY_START_TIME);
		String endTime =   cacheService.getJsonStr(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.TRADE_NODE_KEY_END_TIME);
		String totalCount = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.TOTAL_DATA_COUNT_KEY);
		Date startDate = null;
		Date endDate =null;
		Long currentTotalCount = 0l;
		loger.info("节点数据开始时间"+startTime+"结束时间"+endTime+"当前数据量"+totalCount);
		if(null!=startTime&&!"".equals(startTime)&&null!=endTime&&!"".equals(endTime)) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			startDate = DateUtils.parseTime(startTime);
			endDate = DateUtils.parseTime(endTime);
				  hashMap.put("beginTime",startDate );
				  hashMap.put("endTime",  endDate);
				  Long rspCount = myBatisDaoT.findBy(TbTransactionOrder.class.getName(),
						  "rspCountEnhance", hashMap);
//				  
				  currentTotalCount=rspCount;
				  Long pageNum = 0l;
				  Long pageSize = ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
				  if(rspCount/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
					  pageNum  = 1l;
				  }else if(rspCount%ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
					pageNum  = rspCount/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
				  }else{
					pageNum  = (rspCount+ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE)/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
				  }
				  if(currentTotalCount==0)pageNum=0l;
				  hashMap.put("pageSize", pageSize);
				  List<TbTransactionOrder> rsps = null;
				  int start = 0;
				  loger.info("节点数据总量"+rspCount+"总页数"+pageNum+"判断"+start+" "+pageNum);
				  while (start < pageNum) {
					  if(start == pageNum-1) hashMap.put("pageSize", rspCount - start * pageSize);
					  try {
						  hashMap.put("startRows", start * pageSize);
						  rsps = myBatisDaoT.findList(TbTransactionOrder.class.getName(),
								  "queryTbTrades", hashMap);
						  loger.info("mongo======================第" + (start + 1)
								  + "次分发或者追加============mongo========"
								  + (rsps == null ? 0 : rsps.size())
								  + "条数据=====================");
						  start++;
						  if (null == rsps || rsps.size() == 0) break;
					       TradeQueueService.queue.put(rsps);
						   Thread.sleep(InitDataManageUtil.sleepTimePer);
						   int size = VipMemberQueueService.queue.size();
						   if(size>10000){
						   int sleep = (int)size/2000;
						   Thread.sleep(InitDataManageUtil.sleepTimePer*sleep);
						  }
					  } catch (Exception e) {
						  e.printStackTrace();
						  continue;
					  }
			       }
		   Date startNode = DateUtils.addMinute(startDate, 1);
		   Date endNode = DateUtils.addMinute(endDate, 1); 
		   currentTotalCount = Long.valueOf(totalCount)+currentTotalCount;
		   cacheService.putNoTime(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.TRADE_NODE_KEY_START_TIME,DateUtils.formatDate(startNode,"yyyy-MM-dd HH:mm:ss"));
		   cacheService.putNoTime(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.TRADE_NODE_KEY_END_TIME,DateUtils.formatDate(endNode,"yyyy-MM-dd HH:mm:ss"));
		   cacheService.putNoTime(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.TOTAL_DATA_COUNT_KEY,currentTotalCount);
		}
	}
	
    public void loadConvertTbTradeData(List<TbTransactionOrder> messageList) {
		
		if (messageList != null && messageList.size() > 0) {
			Map<String,List<TradeDTO>> userDataMap = new HashMap<String,List<TradeDTO>>();
			// 声明对象
			String jdp_response ="";
			List<TradeDTO> userDataList  =  null;
			Map<String,Object> exceptionMap  = new HashMap<String ,Object>();
			String exception = "";
			out:for (TbTransactionOrder tbTrade : messageList) {
				if (tbTrade.getJdpResponse() != null && !"".equals(tbTrade.getJdpResponse())) {
					jdp_response = tbTrade.getJdpResponse();
					TradeFullinfoGetResponse rsp = null;
					try {
						rsp = TaobaoUtils.parseResponse(jdp_response, TradeFullinfoGetResponse.class);
						if(null!=rsp){
							TradeDTO tradeDTO = rsp.getTrade();
							tradeDTO.setNodeFlag(1l);
							if(null!=tradeDTO){
								//对时间进行处理
								packageTradeDTOTime(tradeDTO);
								String allNumId = null;
								Long num  = 0l;
								List<OrdersDTO> orders = tradeDTO.getOrders();
								for (OrdersDTO ordersDTO : orders) {
                                     if(ordersDTO.getRefundId()!=null && !"".equals(ordersDTO.getRefundId())){
										tradeDTO.setRefundFlag(true);
									 }
									try {
										ordersDTO.setTid(tradeDTO.getTid());
										ordersDTO.setReceiverDistrict(tradeDTO.getReceiverDistrict());
										ordersDTO.setReceiverCity(tradeDTO.getReceiverCity());
										ordersDTO.setBuyerNick(tradeDTO.getBuyerNick());
										ordersDTO.setStepTradeStatus(tradeDTO.getStepTradeStatus());
										ordersDTO.setCreated(tradeDTO.getCreated());
										ordersDTO.setCreatedUTC(tradeDTO.getCreatedUTC()); 
										ordersDTO.setReceiverName(tradeDTO.getReceiverName());
										ordersDTO.setReceiverMobile(tradeDTO.getReceiverMobile() == null
												? tradeDTO.getReceiverPhone() : tradeDTO.getReceiverMobile());
										ordersDTO.setBuyerFlag(tradeDTO.getBuyerFlag() == null ? null : tradeDTO.getBuyerFlag().intValue());
										ordersDTO.setSellerFlag(tradeDTO.getSellerFlag() == null ? null: Integer.parseInt(tradeDTO.getSellerFlag()));
										// 后续补加字段
										ordersDTO.setModified(tradeDTO.getModified());
										ordersDTO.setModifiedUTC(tradeDTO.getModifiedUTC());
										if(null!=ordersDTO.getEndTimeUTC()){
											ordersDTO.setEndTime(DateUtils.dateToLong(ordersDTO.getEndTimeUTC())); 
										}
										if(null!=ordersDTO.getTimeoutActionTimeUTC()){
											ordersDTO.setTimeoutActionTime(DateUtils.dateToLong(ordersDTO.getTimeoutActionTimeUTC())); 
										}
										ordersDTO.setPayment(Double.valueOf(ordersDTO.getPaymentMoney()==null?"0":ordersDTO.getPaymentMoney()));
										if(ordersDTO.getPriceMoney()==null){
											ordersDTO.setPrice(0d);
										}else{
											BigDecimal pricePayment = new BigDecimal(ordersDTO.getPriceMoney());
											pricePayment= pricePayment.setScale(2, BigDecimal.ROUND_HALF_UP);
											ordersDTO.setPrice(pricePayment.doubleValue()); 
										}
										if(ordersDTO.getTotalFeeMoney()==null){
											ordersDTO.setTotalFee(0d);
										}else{
											BigDecimal totalFee = new BigDecimal(ordersDTO.getTotalFeeMoney());
											totalFee= totalFee.setScale(2, BigDecimal.ROUND_HALF_UP);
											ordersDTO.setTotalFee(totalFee.doubleValue()); 
										}
										//继续补加
										if(null!=allNumId && !"".equals(allNumId)){
											allNumId = allNumId+(ordersDTO.getNumIid()==null?"":","+ordersDTO.getNumIid());
										}else{
											allNumId = (ordersDTO.getNumIid()==null?"":""+ordersDTO.getNumIid());
										}
										num += (ordersDTO.getNum()==null?0:ordersDTO.getNum());
									} catch (Exception e) {
										loger.error("封装数据失败！"+tradeDTO.getTid()+e.getMessage());
									}
								}
								if(orders!=null && orders.size()>1){
									tradeDTO.setNum(num);
									tradeDTO.setNUM_IID(allNumId);
								}else{
									tradeDTO.setNUM_IID(String.valueOf(tradeDTO.getNumIid()));
								}
								if(userDataMap.containsKey(tradeDTO.getSellerNick())){
									List<TradeDTO> list = userDataMap.get(tradeDTO.getSellerNick());
									list.add(tradeDTO);
								}else{
									userDataList = new ArrayList<TradeDTO>();
									userDataList.add(tradeDTO);
									userDataMap.put(tradeDTO.getSellerNick(), userDataList);
								}
							}
						}
					} catch (Exception e) {
						// 解析异常,继续下次循环
						exception = e.getMessage();
						exceptionMap.put(tbTrade.getTid().toString(),exception.substring(0, exception.length()/3));
						continue out;
					}
				}
			}
			// 保存封装好的数据
			Set<String> keySet = userDataMap.keySet();
			loger.info("-----------------------------一批次用户大小"+keySet.size());
			long startTime1 = System.currentTimeMillis();
			for (String key : keySet) {
				List<TradeDTO> list = userDataMap.get(key);
				handleAferCreateData(list,key);
			}
			long endTime1 = System.currentTimeMillis();
			loger.info("保存一批Trade耗时"+(endTime1-startTime1));
		}
	}

    public void handleAferCreateData(List<TradeDTO> tradeDTOList ,String userNickName) {
		try {
			String sessionkey = getSessionkey(userNickName);
			//保存加密数据
			if(!"".equals(sessionkey)){
				tradeInfoService.batchInsertDecryptTradeList(tradeDTOList,userNickName);
				VipMemberQueueService.queue.put(tradeDTOList);
			}else{
				loger.error("+++++++++++++++++++++"+userNickName+"的sessionKey为空");
			}
		} catch (Exception e) {
			loger.info("保存trade失败！"+tradeDTOList.size());
		}
	}
	
	
	public void saveOrUpdateMember(List<TradeDTO> tradeDTOList) {
		List<MemberDTO> members =null;
		Map<String,Object> mMap =null;
		if(tradeDTOList !=null&& tradeDTOList.size()>0){
			mMap = new HashMap<String,Object>();
			/**
			 * 从订单里边分离出会员信息:
			 * 订单同步的部分---List<Trade>
			 */
			for (TradeDTO t : tradeDTOList) {
				try {
					if(null!=t.getBuyerNick()&&null!=t.getSellerNick()&&null!=t.getStatus()
					&&!"".equals(t.getBuyerNick())&&!"".equals(t.getSellerNick())&&!"".equals(t.getStatus())){
						String  key = t.getBuyerNick()+"&"+t.getSellerNick();
						//是退款的推送不提取会员信息
						createMemberInfo(key,mMap, t);
					}
				} catch (Exception e) {
					loger.error("判断会员昵称出错"+t.getSellerNick()+" "+t.getTid()+" "+e.getMessage());
				}
			}
			//将map中的所有value放入集合中
			members = packageMemberList(members, mMap);
			saveOrUpdateMemberList(members);
		}
		//判断会员集合,保存或者更新
	}

	
	
	private void saveOrUpdateMemberList(List<MemberDTO> members) {
		if(members!=null && members.size()>0){
			try {
				vipMemberService.batchSaveDecryptMemberList(members);
			} catch (Exception e) {
				loger.info("保存memberInfo失败 异常信息"+e.getMessage());
			}
		}
	}


	private List<MemberDTO> packageMemberList(List<MemberDTO> members,
			Map<String, Object> mMap) {
		if(!mMap.isEmpty()){
			members = new ArrayList<MemberDTO>();
			MemberDTO member = null;
			Set<String> keySet = mMap.keySet();
			for (String str : keySet) {
				try {
					member = (MemberDTO) mMap.get(str);
					members.add(member);
				} catch (Exception e) {
					 loger.error("获取会员数据失败！"+e.getMessage());
				}
			}
		}
		return members;
	}


	private void createMemberInfo(String key,Map<String, Object> mMap, TradeDTO t) {
		try {
			MemberDTO mInfo=null;
			if(mMap.get(key) ==null){
				mInfo = new MemberDTO();
				mInfo.setUserId(t.getSellerNick());
				mInfo.setBuyerNick(t.getBuyerNick());
				mInfo.setEmail(t.getBuyerEmail());
				mInfo.setReceiverName(t.getReceiverName()==null||"".equals(t.getReceiverName())?null:t.getReceiverName());
				mInfo.setPhone((t.getReceiverMobile()==null || "".equals(t.getReceiverMobile()))?t.getReceiverPhone():t.getReceiverMobile());
				mInfo.setLastTradeTimeUTC(t.getCreatedUTC());
				mInfo.setLastTradeTime(t.getCreated());
				//新加的字段 
				mInfo.setLastSendSmsTime(0l);
				mInfo.setMsgId(0l);
				mInfo.setMsgIdList(Arrays.asList(0l));
				mInfo.setTradeFrom(t.getTradeFrom()); 
				mInfo.setLastOrderStatus(t.getStatus()); 
				mInfo.setNUM_IID(t.getNUM_IID()); 
				mInfo.setTid(t.getTid()); 
				mInfo.setRefund_status(t.isRefundFlag()==true?1:0); 
				//添加默认值
				mInfo.setTradeCount(0l);
				mInfo.setTradeAmount(0d);
				mInfo.setAvgPrice(0d);
				mInfo.setCloseTradeAmount(0d);
				mInfo.setCloseTradeCount(0l); 
				mInfo.setItemNum(0);
				mInfo.setItemCloseCount(0);
				mInfo.setNodeFlag(1l);
				
				//交易成功
				if("TRADE_FINISHED".equals(t.getStatus())){
					mInfo.setSuccessTidList(Arrays.asList(t.getTid()));
					mInfo.setTradeAmount(t.getPayment()==null ?0d: t.getPayment());
					mInfo.setItemNum(t.getNum()==null?0: t.getNum().intValue());
					//交易笔记数----一个主订单代表一笔
					mInfo.setTradeCount(1l);
				}//退款或者交易关闭都算作交易关闭
				else if("TRADE_CLOSED_BY_TAOBAO".equals(t.getStatus()) || "TRADE_CLOSED".equals(t.getStatus())){
					mInfo.setCloseTradeAmount(t.getPayment()==null?0d: t.getPayment());
					mInfo.setItemCloseCount(t.getNum()==null ?0:t.getNum().intValue());
					mInfo.setCloseTradeCount(1l);
				}
				//交易额大于0
				if(mInfo.getTradeAmount()>0d){
					mInfo.setAvgPrice(BigDecimalUtil.divide(new BigDecimal(mInfo.getTradeAmount()),
							2,new BigDecimal(1)).doubleValue());
				}
				if(t.getReceiverState() !=null && !"".equals(t.getReceiverState())){
					mInfo.setProvince(t.getReceiverState());
				}else{
					mInfo.setProvince(getArea(t.getReceiverAddress(),t.getSellerNick()));
				}
				packageFirstTempData(mInfo);
				mMap.put(key,mInfo);
			}else{
				mInfo = (MemberDTO) mMap.get(key);
				mInfo.setNUM_IID(t.getNUM_IID()); 
				mInfo.setTid(t.getTid()); 
				mInfo.setRefund_status(t.isRefundFlag()==true?1:0); 
				//同一批订单中有重复的会员手机号,邮箱覆盖为最新的
				mInfo.setPhone((t.getReceiverMobile()==null || "".equals(t.getReceiverMobile()))
						?t.getReceiverPhone():t.getReceiverMobile());
				mInfo.setEmail(t.getBuyerEmail());
				mInfo.setReceiverName(t.getReceiverName()==null||"".equals(t.getReceiverName())?null:t.getReceiverName());
				if((t.getCreatedUTC()== null || mInfo.getLastTradeTimeUTC()== null)){
					mInfo.setLastTradeTimeUTC(t.getCreatedUTC()== null?mInfo.getLastTradeTimeUTC():t.getCreatedUTC());
					mInfo.setLastTradeTime(t.getCreated()== null?mInfo.getLastTradeTime():t.getCreated());
				}else{
					mInfo.setLastTradeTimeUTC(
							t.getCreatedUTC().before(mInfo.getLastTradeTimeUTC())?mInfo.getLastTradeTimeUTC():t.getCreatedUTC());
					mInfo.setLastTradeTime(
							t.getCreated()<(mInfo.getLastTradeTime())?mInfo.getLastTradeTime():t.getCreated());
				}
				//交易成功
				if("TRADE_FINISHED".equals(t.getStatus())){
					mInfo.setTradeAmount(BigDecimalUtil.add(new BigDecimal(mInfo.getTradeAmount()), (t.getPayment()==null?new BigDecimal(0):new BigDecimal(t.getPayment()))).doubleValue());
					mInfo.setItemNum(t.getNum()==null?0:Integer.valueOf(t.getNum().toString())+mInfo.getItemNum());
					mInfo.setTradeCount(mInfo.getTradeCount()+1);
				}//退款或者交易关闭都算作交易关闭
				else if("TRADE_CLOSED_BY_TAOBAO".equals(t.getStatus()) || "TRADE_CLOSED".equals(t.getStatus())){
					mInfo.setCloseTradeAmount(BigDecimalUtil.add(new BigDecimal(mInfo.getCloseTradeAmount()), (t.getPayment()==null?new BigDecimal(0):new BigDecimal(t.getPayment()))).doubleValue());
					mInfo.setItemCloseCount(mInfo.getItemCloseCount()+(t.getNum()==null?0:Integer.valueOf(t.getNum().toString())));
					mInfo.setCloseTradeCount(mInfo.getCloseTradeCount()+1);
				}
				if(mInfo.getTradeAmount()>0d){
						mInfo.setAvgPrice(BigDecimalUtil.divide(new BigDecimal(mInfo.getTradeAmount()),2,new BigDecimal(mInfo.getTradeCount()==0?1:mInfo.getTradeCount())).doubleValue());
				}
				//获取会员的地区
				if(t.getReceiverState() !=null && !"".equals(t.getReceiverState())){
					mInfo.setProvince(t.getReceiverState());
				}else{
					mInfo.setProvince(getArea(t.getReceiverAddress(),t.getSellerNick()));
				}
				packageTempData(mInfo);
				
				mMap.put(key,mInfo);
			}
		} catch (NumberFormatException e) {
			 loger.error("封装member基础信息失败！"+e.getMessage());
		}
	}


	private void packageTradeDTOTime(TradeDTO tradeDTO) {
		if(null!=tradeDTO){
			
			try {
				tradeDTO.setPayment(Double.valueOf(tradeDTO.getPaymentMoney()==null?"0":tradeDTO.getPaymentMoney()));
				if(tradeDTO.getReceivedPaymentMoney()==null){
					tradeDTO.setReceivedPayment(0d);
				}else{
					BigDecimal receivedPayment = new BigDecimal(tradeDTO.getReceivedPaymentMoney());
					receivedPayment= receivedPayment.setScale(2, BigDecimal.ROUND_HALF_UP);
					tradeDTO.setReceivedPayment(receivedPayment.doubleValue()); 
				}
				if(tradeDTO.getPriceMoney()==null){
					tradeDTO.setPrice(0d);
				}else{
					BigDecimal pricePayment = new BigDecimal(tradeDTO.getPriceMoney());
					pricePayment= pricePayment.setScale(2, BigDecimal.ROUND_HALF_UP);
					tradeDTO.setPrice(pricePayment.doubleValue()); 
				}
				if(tradeDTO.getTotalFeeMoney()==null){
					tradeDTO.setTotalFee(0d);
				}else{
					BigDecimal totalFee = new BigDecimal(tradeDTO.getTotalFeeMoney());
					totalFee= totalFee.setScale(2, BigDecimal.ROUND_HALF_UP);
					tradeDTO.setTotalFee(totalFee.doubleValue()); 
				}
				if(tradeDTO.getNumGoods()==null){
					tradeDTO.setNum(0l); 
				}else{
					tradeDTO.setNum(Long.valueOf(tradeDTO.getNumGoods())); 
				}
				
				if(null!=tradeDTO.getConsignTimeUTC())
				tradeDTO.setConsignTime(DateUtils.dateToLong(tradeDTO.getConsignTimeUTC())); 
				if(null!=tradeDTO.getCreatedUTC())
				tradeDTO.setCreated(DateUtils.dateToLong(tradeDTO.getCreatedUTC())); 
				if(null!=tradeDTO.getPayTimeUTC())
				tradeDTO.setPayTime(DateUtils.dateToLong(tradeDTO.getPayTimeUTC())); 
				if(null!=tradeDTO.getModifiedUTC())
				tradeDTO.setModified(DateUtils.dateToLong(tradeDTO.getModifiedUTC())); 
				if(null!=tradeDTO.getEndTimeUTC())
				tradeDTO.setEndTime(DateUtils.dateToLong(tradeDTO.getEndTimeUTC()));
			} catch (NumberFormatException e) {
				loger.error("packageTradeDTOTime失败！"+tradeDTO.getTid()+e.getMessage());
			} 
		}
		
	}



	


	
	/**   
	 * @Title: saveExceptionMap   
	 * @Description: (保存故障订单的保存或者更新时发生的故障信息)   
	 * @param: @param exceptionMap      
	 * @return: void      
	 * @date:   2017年3月25日 下午5:14:09 
	 * @author: jackstraw_yu  
	 */  
	private void saveExceptionMap(Map<String, Object> exceptionMap,Map<String, Object> tMap) {
		if((exceptionMap !=null && exceptionMap.size()>0)){
			if(tMap != null && tMap.size() > 0){
				exceptionMap.putAll(tMap);
			}
		}else{
			if(tMap != null && tMap.size() > 0){
				exceptionMap = tMap;
			}
		}
		if((exceptionMap !=null && exceptionMap.size()>0)){
			Map<String, Object> hap = new HashMap<String, Object>();
			for (Entry<String, Object> entry : exceptionMap.entrySet()) {
				hap.put("tid", entry.getKey());
				hap.put("message", entry.getValue());
				try {
					myBatisDao.execute(Orders.class.getName(), "saveExceptionMap", hap);
				} catch (Exception e) {
					continue;
				}
			}
		}
	}


	
	
	
	private String getArea(String area,String sellerNick){
		String place =null;
		try {
			String sessionkey = getSessionkey(sellerNick);
			if(!"".equals(sessionkey)){
				String decrypt = EncrptAndDecryptClient.getInstance().decrypt(area, EncrptAndDecryptClient.SIMPLE, sessionkey);
				if(decrypt!=null && !"".equals(decrypt)){
					String[] split = decrypt.split("市|省|\b|,|，| ");
					List<String> asList = Arrays.asList(split);
					place = AreaUtils.getArea(asList);
				}
			}
		} catch (Exception e) {
			return "";
		}
		return place;
	}
	
	private void packageFirstTempData(MemberDTO memberInfo) {
		 String [] numIdArr = null;
		 List<String> list = new ArrayList<String>();
		 list.add(memberInfo.getTid());
		 memberInfo.setTempTid(list); 
		 if(null!=memberInfo.getTradeFrom()&&!"".equals(memberInfo.getTradeFrom())){
			 list = new ArrayList<String>();
			 list.add(memberInfo.getTradeFrom());
			 memberInfo.setTempTradeFrom(list); 
		 }
		 if(null!=memberInfo.getNUM_IID()&&!"".equals(memberInfo.getNUM_IID())){
			 numIdArr = memberInfo.getNUM_IID().split(",");
			 list = new ArrayList<String>();
			 list.addAll(new HashSet<String>(Arrays.asList(numIdArr)));
			 Set<String> setList = new HashSet<String>(list);
			 List<String> list2 = new ArrayList<String> ();  
			 list2.addAll(setList); 
			 memberInfo.setTempProduct(list2);  
		 }
		 if(null!=memberInfo.getProvince()&&!"".equals(memberInfo.getProvince())){ 
			 list = new ArrayList<String>();
			 list.add(memberInfo.getProvince());
			 memberInfo.setTempProvince(list);
		 }
		 list = null;
	}
	 
	
	private void packageTempData(MemberDTO memberInfo) {
		List<String> tempTid = memberInfo.getTempTid();
		if(null!=tempTid&&!"".equals(memberInfo.getTid())){
			 if(!tempTid.contains(memberInfo.getTid())){
				 tempTid.add(memberInfo.getTid());
				 memberInfo.setTempTid(tempTid);
			 }
		 }
		 List<String> tempTradeFrom = memberInfo.getTempTradeFrom();
		 if(null!=tempTradeFrom&&!"".equals(memberInfo.getTradeFrom())){
			 if(null!=memberInfo.getTradeFrom()&&!"".equals(memberInfo.getTradeFrom())){
				 if(!tempTradeFrom.contains(memberInfo.getTradeFrom())){
					 tempTradeFrom.add(memberInfo.getTradeFrom());
					 memberInfo.setTempTradeFrom(tempTradeFrom);
				 }
			 }
		 }
		 List<String> tempProduct = memberInfo.getTempProduct();
		 if(null!=memberInfo.getNUM_IID()&&!"".equals(memberInfo.getNUM_IID())){
			 String[] numIdArr = memberInfo.getNUM_IID().split(",");
			 if(tempProduct == null){
				 tempProduct = new ArrayList<String> ();  
			 }
			 tempProduct.addAll(Arrays.asList(numIdArr));
			 Set<String> setList = new HashSet<String>(tempProduct);
			 List<String> list2 = new ArrayList<String> ();  
			 list2.addAll(setList); 
			 memberInfo.setTempProduct(list2);
		 }
		 List<String> tempProvince = memberInfo.getTempProvince();
		 if(null!=tempProvince&&!"".equals(memberInfo.getProvince())){
			 if(null!=memberInfo.getProvince()&&!"".equals(memberInfo.getProvince())){
				 if(!tempProvince.contains(memberInfo.getProvince())){
					 tempProvince.add(memberInfo.getProvince());
				 }
			 }
		 }
	}


   private  String 	getSessionkey(String userNickName){
	    String sessionExpire = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.SELLER_EXPIRATION_TIME, RedisConstant.RedisCacheGroup.SELLER_EXPIRATION_TIME+userNickName);
	    if(sessionExpire !=null ){
	    	if(ValidateUtil.isIntegerOrLong(sessionExpire)){
	    		 if(Long.parseLong(sessionExpire)<System.currentTimeMillis()){
	    			 return null;
	    		 }
	    	}
	    }
	    String  token = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName);
		if(null!=token&&!"".equals(token)){
			 return token;
		}else{
			UserInfo user = userInfoService.queryUserTokenInfo(userNickName);
			if(user==null)
				return null;
			String expiresIn = user.getExpires_in();
			Date lastDate = user.getLastLoginDate();
			if(ValidateUtil.isNotNull(expiresIn) && ValidateUtil.isNotNull(lastDate)){
				if(ValidateUtil.isIntegerOrLong(expiresIn)){
					Long sessionExpireTime = Long.parseLong(expiresIn)*1000+lastDate.getTime();
					cacheService.putNoTime(RedisConstant.RedisCacheGroup.SELLER_EXPIRATION_TIME, RedisConstant.RedisCacheGroup.SELLER_EXPIRATION_TIME+userNickName,sessionExpireTime);
					if(!(sessionExpireTime<System.currentTimeMillis())){
						return  null;
					}
				}
			}
			if(null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
				 cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName,user.getAccess_token());
				 return user.getAccess_token(); 
			}
		}
		return "";
   }
	
}
