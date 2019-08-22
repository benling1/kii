package s2jh.biz.shop.crm.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.service.RedisLockServiceImpl;
import lab.s2jh.core.util.DateUtils;
import lab.s2jh.core.util.NumberUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.crm.manage.dao.SmsRecordRepository;
import s2jh.biz.shop.crm.manage.dao.TradeRepository;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.SyncEffectQueueService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.InitDataManageUtil;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.order.dao.EffectPictureDao;
import s2jh.biz.shop.crm.order.entity.EffectPicture;
import s2jh.biz.shop.crm.order.pojo.EffectNum;
import s2jh.biz.shop.crm.order.pojo.EffectParamVo;
import s2jh.biz.shop.crm.order.pojo.EffectPictureReal;
import s2jh.biz.shop.crm.order.thread.EffectBuyerRealCountThread;
import s2jh.biz.shop.crm.order.thread.EffectPictureManageThread;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.utils.BigDecimalUtil;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.JsonUtil;
import s2jh.biz.shop.utils.TradeStatusUtils;

@Service
@Transactional
public class EffectPictureService extends BaseService<EffectPicture, Long>{

	private Logger logger = LoggerFactory.getLogger(EffectPictureService.class);
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Autowired
	private SmsRecordRepository smsRecordRepository;
	@Autowired
	private TradeRepository  tradeRepository;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Autowired
	private EffectPictureDao effectPictureDao;
	
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private VipMemberService vipMemberService;
	
	public static   Map<Long,Object> map = new HashMap<Long,Object>();
	
	//声明对象锁
	private  Object lock = new Object();
	
	protected BaseDao<EffectPicture, Long> getEntityDao() {
		return effectPictureDao;
	}

	/**
	 * 效果分析表的添加操作
	 * ZTK2017年8月31日下午2:04:27
	 */
	public boolean insertEffectPicture(EffectPicture effectPicture) throws Exception{
		synchronized(this.lock){
			if(effectPicture != null){
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("msgId", effectPicture.getMsgId());
				paramMap.put("days", effectPicture.getDays());
				paramMap.put("ordersource", effectPicture.getOrderSource());
				EffectPicture existEffectPicture = myBatisDao.findBy(EffectPicture.class.getName(), "findEffectByParam", paramMap);
				if(null!=existEffectPicture){
					
					Map<String,Object> dataMap = new HashMap<String, Object>();
					createEffectPictureObj(effectPicture, existEffectPicture);
					dataMap.put("effectTime", effectPicture.getEffectTime());
					dataMap.put("totalBuyer", effectPicture.getTotalBuyer());
					dataMap.put("totalOrder", effectPicture.getTotalOrder());
					dataMap.put("totalItem", effectPicture.getTotalItem());
					dataMap.put("payBuyer", effectPicture.getPayBuyer());
					dataMap.put("payOrder", effectPicture.getPayOrder());
					dataMap.put("payItem", effectPicture.getPayItem());
					dataMap.put("waitPayBuyer", effectPicture.getWaitPayBuyer());
					dataMap.put("waitPayOrder", effectPicture.getWaitPayOrder());
					dataMap.put("waitPayItem", effectPicture.getWaitPayItem());
					dataMap.put("refundBuyer", effectPicture.getRefundBuyer());
					dataMap.put("refundOrder", effectPicture.getRefundOrder());
					dataMap.put("refundItem", effectPicture.getRefundItem());
					
					dataMap.put("totalFee", effectPicture.getTotalFee());
					dataMap.put("payFee", effectPicture.getPayFee());
					dataMap.put("waitPayFee", effectPicture.getWaitPayFee());
					dataMap.put("refundFee", effectPicture.getRefundFee());
					
					dataMap.put("totalBuyerReal", effectPicture.getTotalBuyerReal());
					dataMap.put("payBuyerReal", effectPicture.getPayBuyerReal());
					dataMap.put("waitPayBuyerReal", effectPicture.getWaitPayBuyerReal());
					dataMap.put("refundBuyerReal", effectPicture.getRefundBuyerReal());
					dataMap.put("lastModifiedBy", effectPicture.getUserId());
					dataMap.put("lastModifiedDate", new Date());
					dataMap.put("id", existEffectPicture.getId());
					int insertCount = myBatisDao.execute(EffectPicture.class.getName(), "updateEffectByParam", dataMap);
					if(insertCount == 1){
						return true;
					}
				}else{
					Map<String,Object> dataMap = new HashMap<String, Object>();
					dataMap.put("userId", effectPicture.getUserId());
					dataMap.put("msgId", effectPicture.getMsgId());
					dataMap.put("sendTime", effectPicture.getSendTime());
					dataMap.put("orderSource", effectPicture.getOrderSource());
					dataMap.put("effectTime", effectPicture.getEffectTime());
					dataMap.put("days", effectPicture.getDays());
					dataMap.put("totalFee", effectPicture.getTotalFee()==null?0d:effectPicture.getTotalFee());
					dataMap.put("totalBuyer", effectPicture.getTotalBuyer()==null?0:effectPicture.getTotalBuyer());
					dataMap.put("totalOrder", effectPicture.getTotalOrder()==null?0:effectPicture.getTotalOrder());
					dataMap.put("totalItem", effectPicture.getTotalItem()==null?0l:effectPicture.getTotalItem());
					dataMap.put("payFee", effectPicture.getPayFee()==null?0d:effectPicture.getPayFee());
					dataMap.put("payBuyer", effectPicture.getPayBuyer()==null?0:effectPicture.getPayBuyer());
					dataMap.put("payOrder", effectPicture.getPayOrder()==null?0:effectPicture.getPayOrder());
					dataMap.put("payItem", effectPicture.getPayItem()==null?0l:effectPicture.getPayItem());
					dataMap.put("waitPayFee", effectPicture.getWaitPayFee()==null?0d:effectPicture.getWaitPayFee());
					dataMap.put("waitPayBuyer", effectPicture.getWaitPayBuyer()==null?0:effectPicture.getWaitPayBuyer());
					dataMap.put("waitPayOrder", effectPicture.getWaitPayOrder()==null?0:effectPicture.getWaitPayOrder());
					
					dataMap.put("waitPayItem", effectPicture.getWaitPayItem()==null?0l:effectPicture.getWaitPayItem());
					dataMap.put("refundFee", effectPicture.getRefundFee()==null?0d:effectPicture.getRefundFee());
					dataMap.put("refundBuyer", effectPicture.getRefundBuyer()==null?0:effectPicture.getRefundBuyer());
					dataMap.put("refundOrder", effectPicture.getRefundOrder()==null?0:effectPicture.getRefundOrder());
					dataMap.put("refundItem", effectPicture.getRefundItem()==null?0l:effectPicture.getRefundItem());
					dataMap.put("totalBuyerReal", NumberUtils.getResult(effectPicture.getTotalBuyerReal()));
					dataMap.put("payBuyerReal", NumberUtils.getResult(effectPicture.getPayBuyerReal()));
					dataMap.put("waitPayBuyerReal", NumberUtils.getResult(effectPicture.getWaitPayBuyerReal()));
					dataMap.put("refundBuyerReal", NumberUtils.getResult(effectPicture.getRefundBuyerReal()));
					dataMap.put("createdBy", effectPicture.getUserId());
					dataMap.put("createdDate", new Date());
					dataMap.put("lastModifiedBy", effectPicture.getUserId());
					dataMap.put("lastModifiedDate", new Date());
					int insertCount = myBatisDao.execute(EffectPicture.class.getName(), "insertEffectPicture", dataMap);
					if(insertCount == 1){
						return true;
					}
				}
			}
		}
		
		return false;
	}

	private void createEffectPictureObj(EffectPicture effectPicture,
			EffectPicture existEffectPicture) {
		if (existEffectPicture.getTotalOrder()==null) existEffectPicture.setTotalOrder(0);
		if (existEffectPicture.getTotalBuyer()==null) existEffectPicture.setTotalBuyer(0);
		if (existEffectPicture.getPayBuyer()==null) existEffectPicture.setPayBuyer(0);
		if (existEffectPicture.getPayOrder()==null) existEffectPicture.setPayOrder(0);
		if (existEffectPicture.getWaitPayBuyer()==null) existEffectPicture.setWaitPayBuyer(0);
		if (existEffectPicture.getWaitPayOrder()==null) existEffectPicture.setWaitPayOrder(0);
		if (existEffectPicture.getRefundBuyer()==null) existEffectPicture.setRefundBuyer(0);
		if (existEffectPicture.getRefundOrder()==null) existEffectPicture.setRefundOrder(0);
		
		if (existEffectPicture.getRefundItem()==null) existEffectPicture.setRefundItem(0l);
		if (existEffectPicture.getTotalItem()==null) existEffectPicture.setTotalItem(0l);
		if (existEffectPicture.getPayItem()==null) existEffectPicture.setPayItem(0l);
		if (existEffectPicture.getWaitPayItem()==null) existEffectPicture.setWaitPayItem(0l);
		
		if (existEffectPicture.getRefundFee()==null) existEffectPicture.setRefundFee(0d);
		if (existEffectPicture.getPayFee()==null) existEffectPicture.setPayFee(0d);
		if (existEffectPicture.getWaitPayFee()==null) existEffectPicture.setWaitPayFee(0d);
		if (existEffectPicture.getTotalFee()==null) existEffectPicture.setTotalFee(0d);
		
		
		if (effectPicture.getTotalOrder()==null) effectPicture.setTotalOrder(0);
		if (effectPicture.getTotalBuyer()==null) effectPicture.setTotalBuyer(0);
		if (effectPicture.getPayBuyer()==null) effectPicture.setPayBuyer(0);
		if (effectPicture.getPayOrder()==null) effectPicture.setPayOrder(0);
		if (effectPicture.getWaitPayBuyer()==null) effectPicture.setWaitPayBuyer(0);
		if (effectPicture.getWaitPayOrder()==null) effectPicture.setWaitPayOrder(0);
		if (effectPicture.getRefundBuyer()==null) effectPicture.setRefundBuyer(0);
		if (effectPicture.getRefundOrder()==null) effectPicture.setRefundOrder(0);
		
		if (effectPicture.getRefundItem()==null) effectPicture.setRefundItem(0l);
		if (effectPicture.getTotalItem()==null) effectPicture.setTotalItem(0l);
		if (effectPicture.getPayItem()==null) effectPicture.setPayItem(0l);
		if (effectPicture.getWaitPayItem()==null) effectPicture.setWaitPayItem(0l);
		
		if (effectPicture.getRefundFee()==null) effectPicture.setRefundFee(0d);
		if (effectPicture.getPayFee()==null) effectPicture.setPayFee(0d);
		if (effectPicture.getWaitPayFee()==null) effectPicture.setWaitPayFee(0d);
		if (effectPicture.getTotalFee()==null) effectPicture.setTotalFee(0d);
	}
	
	/**
	 * 根据msgId查询效果分析表中的数据最后一天（昨日）的数据
	 * ZTK2017年8月31日下午5:35:37
	 */
	public EffectPicture findEffectByMsgId(Long msgId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("msgId", msgId);
		EffectPicture effectPicture = myBatisDao.findBy(EffectPicture.class.getName(), "findEffectByMsgId", map);
		return effectPicture;
	}
	
	
	/**
	 * 查询最近15天发送的msgId
	 * ZTK2017年8月31日下午5:13:51
	 */
	public void findAllMsgId(Date dayEndTime){
		String msgIdStr = redisLockServiceImpl.getValue("lastSychMsgId",String.class);
		Long msgIdLong = null;
		if(msgIdStr != null && !"".equals(msgIdStr)){
			msgIdLong = Long.parseLong(msgIdStr);
		}else {
			msgIdLong = 1L;
		}
		List<Long> msgIds = msgSendRecordService.findMsgIdByTime(null,DateUtils.nDaysAgo(15, new Date()), dayEndTime,msgIdLong);
		logger.info("!!!!!!!!!!!!!startDate:" + DateUtils.formatDate(DateUtils.nDaysAgo(15, new Date()),"yyyy-MM-dd hh:mm:ss") + "endDate:" + DateUtils.formatDate(dayEndTime,"yyyy-MM-dd hh:mm:ss"));
		int i = 0;
		if(msgIds != null && !msgIds.isEmpty()){
			logger.info("~~~~~~~~~~查询最近15天发送的msgId:msgIds的size：" + msgIds.size());
			for (Long msgId : msgIds) {
				redisLockServiceImpl.putStringValueWithExpireTime("lastSychMsgId", msgId.toString(), TimeUnit.HOURS, 10L);
				List<String> phoneList = new ArrayList<String>();
				MsgSendRecord msgRecord = msgSendRecordService.findOne(msgId);
				if(msgRecord != null){
					try {
						Criteria criteria = new Criteria("userId").is(msgRecord.getUserId());
						criteria.and("status").is(2);
						criteria.and("type").is(msgRecord.getType());
						if(msgId != null){
							criteria.and("msgId").is(msgId);
							phoneList = smsRecordService.findNumList(new Query(criteria), msgRecord.getUserId());
						}
						if(phoneList != null){
							logger.info("~~~~~~~~~~查询最近15天发送的msgId第" + (++i) + "此查找,msgId:" + msgId + "群发手机个数:" + phoneList.size());
						}else {
							continue;
						}
						//查询发送日期与今日的相差天数，set到days字段
						Long days = DateUtils.getDiffDay(msgRecord.getSendCreat(), new Date());
						if(15 < days){
							continue;
						}
						Date todayStart = DateUtils.getStartTimeOfDay(dayEndTime);//今天0点
						EffectPicture todayEffect_WAP = todayEffectPicture(msgRecord.getUserId(), todayStart, dayEndTime, phoneList, "WAP,WAP",msgRecord.getSendCreat());
						EffectPicture todayEffect_TAOBAO = todayEffectPicture(msgRecord.getUserId(), todayStart, dayEndTime, phoneList, "TAOBAO",msgRecord.getSendCreat());
						EffectPicture todayEffect_TOTAL = todayEffectPicture(msgRecord.getUserId(), todayStart, dayEndTime, phoneList, "TOTAL",msgRecord.getSendCreat());
						paddingAndSave(todayEffect_WAP, msgId, days.intValue() + 1, msgRecord.getSendCreat());
						paddingAndSave(todayEffect_TAOBAO, msgId, days.intValue() + 1, msgRecord.getSendCreat());
						paddingAndSave(todayEffect_TOTAL, msgId, days.intValue() + 1, msgRecord.getSendCreat());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return;
	}
	
	/**
	 * 查询最近15天发送的msgId(NEW)
	 * ZTK2017年9月21日下午2:13:51
	 */
	public void findAllMsgIdNew(Date dayEndTime){
		String msgIdStr = redisLockServiceImpl.getValue("lastSychMsgId",String.class);
		Long msgIdLong = 1L;
		if(msgIdStr != null && !"".equals(msgIdStr)){
			msgIdLong = Long.parseLong(msgIdStr);
		}
		List<Long> msgIds = msgSendRecordService.findMsgIdByTime(null,DateUtils.nDaysAgo(10, dayEndTime), dayEndTime,msgIdLong);
		logger.info("!!!!!!!!!!!!!startDate:" + DateUtils.formatDate(DateUtils.nDaysAgo(10, dayEndTime),"yyyy-MM-dd HH:mm:ss") + "endDate:" + DateUtils.formatDate(dayEndTime,"yyyy-MM-dd HH:mm:ss"));
		int i = 0;
		if(msgIds != null && !msgIds.isEmpty()){
			logger.info("~~~~~~~~~~查询最近10天发送的msgId:msgIds的size：" + msgIds.size());
			for (Long msgId : msgIds) {
				
				redisLockServiceImpl.putStringValueWithExpireTime("lastSychMsgId", msgId.toString(), TimeUnit.HOURS, 10L);
				List<String> phoneList = new ArrayList<String>();
				MsgSendRecord msgRecord = msgSendRecordService.findOne(msgId);
				if(msgRecord != null){
					if("大哥要啥".equals(msgRecord.getUserId())){
						continue;
					}
					try {
						Criteria criteria = new Criteria("userId").is(msgRecord.getUserId());
						criteria.and("status").is(2);
						criteria.and("type").is(msgRecord.getType());
						if(msgId != null){
							criteria.and("msgId").is(msgId);
							phoneList = smsRecordService.findNumList(new Query(criteria), msgRecord.getUserId());
						}
						if(phoneList != null){
							logger.info("~~~~~~~~~~查询最近10天发送的msgId第" + (++i) + "此查找,msgId:" + msgId + "群发手机个数:" + phoneList.size());
						}else {
							continue;
						}
						//查询发送日期与今日的相差天数，set到days字段
						Long days = DateUtils.getDiffDay(msgRecord.getSendCreat(), dayEndTime);
						if(9 < days){
							continue;
						}
						//计算分析数据的某天的开始时间
						Date startTime = null;
						if(days == 0){
							startTime = msgRecord.getSendCreat();
						}else {
							startTime = DateUtils.getStartTimeOfDay(dayEndTime);
						}
						Date fifteenTime = msgRecord.getSendCreat();
						List<TradeDTO> todayTradeList = todayEffectPictureNew(msgRecord,startTime, dayEndTime, phoneList,null);
						List<TradeDTO> fifteenTradeList = todayEffectPictureNew(msgRecord,fifteenTime, dayEndTime, phoneList,null);
						Map<String, EffectPicture> effectMap = appendPicture(todayTradeList, msgRecord, dayEndTime);
						ExecutorService executors = MyFixedThreadPool.getMyFixedThreadPool();
						Future<EffectPictureReal> effectPictureRealFuture = executors.submit(new EffectBuyerRealCountThread(fifteenTradeList));
						EffectPictureReal effectPictureReal = effectPictureRealFuture.get();
						EffectPicture effectPicture_TOTAL = null;
						EffectPicture effectPicture_TAOBAO = null;
						EffectPicture effectPicture_WAP = null;
						if(effectMap != null){
							effectPicture_TOTAL = effectMap.get("TOTAL");
							effectPicture_TAOBAO = effectMap.get("TAOBAO");
							effectPicture_WAP = effectMap.get("WAP");
						}
						if(effectPictureReal != null){
							if(effectPicture_TOTAL != null){
								effectPicture_TOTAL.setTotalBuyerReal(effectPictureReal.getTotalBuyerReal_TOTAL());
								effectPicture_TOTAL.setPayBuyerReal(effectPictureReal.getPayBuyerReal_TOTAL());
								effectPicture_TOTAL.setWaitPayBuyerReal(effectPictureReal.getWaitPayBuyerReal_TOTAL());
								effectPicture_TOTAL.setRefundBuyerReal(effectPictureReal.getRefundBuyerReal_TOTAL());
								paddingAndSave(effectPicture_TOTAL, msgId, days.intValue() + 1, msgRecord.getSendCreat());
							}
							if(effectPicture_TAOBAO != null){
								effectPicture_TAOBAO.setTotalBuyerReal(effectPictureReal.getTotalBuyerReal_TAOBAO());
								effectPicture_TAOBAO.setPayBuyerReal(effectPictureReal.getPayBuyerReal_TAOBAO());
								effectPicture_TAOBAO.setWaitPayBuyerReal(effectPictureReal.getWaitPayBuyerReal_TAOBAO());
								effectPicture_TAOBAO.setRefundBuyerReal(effectPictureReal.getRefundBuyerReal_TAOBAO());
								paddingAndSave(effectPicture_TAOBAO, msgId, days.intValue() + 1, msgRecord.getSendCreat());
							}
							if(effectPicture_WAP != null){
								effectPicture_WAP.setTotalBuyerReal(effectPictureReal.getTotalBuyerReal_WAP());
								effectPicture_WAP.setPayBuyerReal(effectPictureReal.getPayBuyerReal_WAP());
								effectPicture_WAP.setWaitPayBuyerReal(effectPictureReal.getWaitPayBuyerReal_WAP());
								effectPicture_WAP.setRefundBuyerReal(effectPictureReal.getRefundBuyerReal_WAP());
								paddingAndSave(effectPicture_WAP, msgId, days.intValue() + 1, msgRecord.getSendCreat());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return;
	}
	
	/**
	 * 获取今日的营销分析数据(NEW)
	 * ztk2017年9月20日下午2:10:54
	 */
	public List<TradeDTO> todayEffectPictureNew(MsgSendRecord msgRecord,Date startTime,Date endTime,
			List<String> phoneList,String orderSource)throws Exception{
		
		List<TradeDTO> tradeDTOList = new ArrayList<TradeDTO>();
		//根据条件查询订单(时间、手机号)，如果手机号数量大于5w和小于5w的计算方式区别
		if(phoneList != null && !phoneList.isEmpty()){
			if(phoneList.size() > ConstantUtils.PROCESS_PAGE_SIZE_OVER){
				//手机号大于5万
				tradeDTOList = tradeMatchRecord(msgRecord.getUserId(), startTime, endTime, phoneList,null);
			}else {
				//手机号小于5万
				Criteria criteria = new Criteria("sellerNick").is(msgRecord.getUserId());
				criteria.and("receiverMobile").in(phoneList);
				if(startTime != null && endTime != null){
					criteria.and("created").gte(startTime.getTime()).lte(endTime.getTime());
				}
				if(orderSource != null && !"".equals(orderSource) && !"TOTAL".equals(orderSource)){
					criteria.and("tradeFrom").regex(".*?" + orderSource + ".*");
				}
				tradeDTOList = tradeInfoService.findList(new Query(criteria), msgRecord.getUserId());
			}
			return tradeDTOList;
		}
		return null;
	}
	
	/**
	 * 手机号大于5w时，通过订单匹配手机号，查询出对应的数据
	 * ztk2017年9月20日下午2:26:23
	 */
	public List<TradeDTO> tradeMatchRecord(String userId,Date beginTime,Date endTime,List<String> phones,String orderSource){
		List<TradeDTO> tradeList = new ArrayList<TradeDTO>();
		Criteria criteria = new Criteria("sellerNick").is(userId);
		if(beginTime != null && endTime != null){
			criteria.and("created").gte(beginTime.getTime()).lte(endTime.getTime());
		}
		if(orderSource != null && !"".equals(orderSource) && !"TOTAL".equals(orderSource)){
			criteria.and("tradeFrom").regex(".*?" + orderSource + ".*");
		}
		List<TradeDTO> TradeDTOS = tradeInfoService.findList(new Query(criteria), userId);
		if(TradeDTOS != null){
			for (int i = 0; i < TradeDTOS.size(); i++) {
				TradeDTO tradeDTO = TradeDTOS.get(i);
				if(tradeDTO != null && tradeDTO.getReceiverMobile() != null && phones.contains(tradeDTO.getReceiverMobile())){
					tradeList.add(tradeDTO);
				}
			}
		}
		return tradeList;
	}
	
	/**
	 * 对筛选出的订单进行统计计算，得到效果分析所需的数据
	 * @throws Exception 
	 */
	public Map<String, EffectPicture> appendPicture(List<TradeDTO> tradeList,MsgSendRecord msgRecord,Date endTime) throws Exception{
		//每个msgId对应三条记录(TAOBAO,WAP,TOTAL)
		EffectPicture effectPicture_TAOBAO = new EffectPicture();
		EffectPicture effectPicture_WAP = new EffectPicture();
		EffectPicture effectPicture_TOTAL = new EffectPicture();
		System.err.println("~~~~~~~~~~~~~endTime:" + endTime);
		effectPicture_TAOBAO.setEffectTime(DateUtils.nDaysAgo(1, endTime));
		effectPicture_WAP.setEffectTime(DateUtils.nDaysAgo(1, endTime));
		effectPicture_TOTAL.setEffectTime(DateUtils.nDaysAgo(1, endTime));
		effectPicture_TOTAL.setOrderSource("TOTAL");
		effectPicture_TAOBAO.setOrderSource("TAOBAO");
		effectPicture_WAP.setOrderSource("WAP,WAP");
		effectPicture_TOTAL.setUserId(msgRecord.getUserId());
		effectPicture_TAOBAO.setUserId(msgRecord.getUserId());
		effectPicture_WAP.setUserId(msgRecord.getUserId());
		effectPicture_TAOBAO.setCreatedBy(msgRecord.getUserId());
		effectPicture_TOTAL.setCreatedBy(msgRecord.getUserId());
		effectPicture_WAP.setCreatedBy(msgRecord.getUserId());
		effectPicture_TAOBAO.setLastModifiedBy(msgRecord.getUserId());
		effectPicture_TOTAL.setLastModifiedBy(msgRecord.getUserId());
		effectPicture_WAP.setLastModifiedBy(msgRecord.getUserId());
		Set<String> totalBuyerSet_TOTAL = new HashSet<String>(),payBuyerSet_TOTAL = new HashSet<String>(),waitBuyerSet_TOTAL = new HashSet<String>(),refundBuyerSet_TOTAL = new HashSet<String>();
		Set<String> totalBuyerSet_TAOBAO = new HashSet<String>(),payBuyerSet_TAOBAO = new HashSet<String>(),waitBuyerSet_TAOBAO = new HashSet<String>(),refundBuyerSet_TAOBAO = new HashSet<String>();
		Set<String> totalBuyerSet_WAP = new HashSet<String>(),payBuyerSet_WAP = new HashSet<String>(),waitBuyerSet_WAP = new HashSet<String>(),refundBuyerSet_WAP = new HashSet<String>();
		for (int i = 0; i < tradeList.size(); i++) {
			TradeDTO tradeDTO = tradeList.get(i);
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~同步效果分析:tradeList的大小是:" + tradeList.size());
			if(tradeDTO != null){
				totalBuyerSet_TOTAL.add(tradeDTO.getBuyerNick());
				effectPicture_TOTAL.setTotalFee(NumberUtils.getResult(effectPicture_TOTAL.getTotalFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
				effectPicture_TOTAL.setTotalItem(NumberUtils.getResult(effectPicture_TOTAL.getTotalItem()) + NumberUtils.getResult(tradeDTO.getNum()));
				logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~同步效果分析:totalFee是:" + tradeDTO.getPayment());
				paddingEffect(effectPicture_TOTAL, tradeDTO,payBuyerSet_TOTAL,waitBuyerSet_TOTAL,refundBuyerSet_TOTAL);
				if(tradeDTO.getTradeFrom().contains(TradesInfo.ORDER_FROM_TAOBAO)){
					totalBuyerSet_TAOBAO.add(tradeDTO.getBuyerNick());
					effectPicture_TAOBAO.setTotalFee(NumberUtils.getResult(effectPicture_TAOBAO.getTotalFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
					effectPicture_TAOBAO.setTotalItem(NumberUtils.getResult(effectPicture_TAOBAO.getTotalItem()) + NumberUtils.getResult(tradeDTO.getNum()));
					effectPicture_TAOBAO.setTotalOrder(NumberUtils.getResult(effectPicture_TAOBAO.getTotalOrder()) + 1);
					paddingEffect(effectPicture_TAOBAO, tradeDTO,payBuyerSet_TAOBAO,waitBuyerSet_TAOBAO,refundBuyerSet_TAOBAO);
				}else if(tradeDTO.getTradeFrom().contains(TradesInfo.ORDER_FROM_WAP)){
					totalBuyerSet_WAP.add(tradeDTO.getBuyerNick());
					effectPicture_WAP.setTotalFee(NumberUtils.getResult(effectPicture_WAP.getTotalFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
					effectPicture_WAP.setTotalItem(NumberUtils.getResult(effectPicture_WAP.getTotalItem()) + NumberUtils.getResult(tradeDTO.getNum()));
					effectPicture_WAP.setTotalOrder(NumberUtils.getResult(effectPicture_WAP.getTotalOrder()) + 1);
					paddingEffect(effectPicture_WAP, tradeDTO,payBuyerSet_WAP,waitBuyerSet_WAP,refundBuyerSet_WAP);
				}
			}
		}
		effectPicture_TOTAL.setTotalOrder(tradeList.size());
		effectPicture_TOTAL.setTotalBuyer(totalBuyerSet_TOTAL.size());
		effectPicture_TAOBAO.setTotalBuyer(totalBuyerSet_TAOBAO.size());
		effectPicture_WAP.setTotalBuyer(totalBuyerSet_WAP.size());
		effectPicture_TOTAL.setPayBuyer(payBuyerSet_TOTAL.size());
		effectPicture_TOTAL.setWaitPayBuyer(waitBuyerSet_TOTAL.size());
		effectPicture_TOTAL.setRefundBuyer(refundBuyerSet_TOTAL.size());
		effectPicture_TAOBAO.setPayBuyer(payBuyerSet_TAOBAO.size());
		effectPicture_TAOBAO.setWaitPayBuyer(waitBuyerSet_TAOBAO.size());
		effectPicture_TAOBAO.setRefundBuyer(refundBuyerSet_TAOBAO.size());
		effectPicture_WAP.setPayBuyer(payBuyerSet_WAP.size());
		effectPicture_WAP.setWaitPayBuyer(waitBuyerSet_WAP.size());
		effectPicture_WAP.setRefundBuyer(refundBuyerSet_WAP.size());
		
		//保存到数据库
//		paddingAndSave(effectPicture_TOTAL, msgRecord.getId(), DateUtils.getDiffDay(msgRecord.getSendCreat(), endTime).intValue() + 1, msgRecord.getSendCreat());
//		paddingAndSave(effectPicture_TAOBAO, msgRecord.getId(), DateUtils.getDiffDay(msgRecord.getSendCreat(), endTime).intValue() + 1, msgRecord.getSendCreat());
//		paddingAndSave(effectPicture_WAP, msgRecord.getId(), DateUtils.getDiffDay(msgRecord.getSendCreat(), endTime).intValue() + 1, msgRecord.getSendCreat());
		Map<String, EffectPicture> resultMap = new HashMap<String, EffectPicture>();
		resultMap.put("TOTAL", effectPicture_TOTAL);
		resultMap.put(TradesInfo.ORDER_FROM_TAOBAO, effectPicture_TAOBAO);
		resultMap.put(TradesInfo.ORDER_FROM_WAP, effectPicture_WAP);
		return resultMap;
	}
	
	/**
	 * NEW封装效果分析对象(TOTAL,TAOBAO,WAP对应的付款、待付款、退款状态)
	 * ztk2017年9月20日下午5:39:13
	 */
	public void paddingEffect(EffectPicture effectPicture,TradeDTO trade,Set<String> payBuyerSet,
			Set<String> waitPayBuyerSet,Set<String> refundBuyerSet){
		//付款的订单状态的list
		List<String> payStatusList = new ArrayList<String>();
		payStatusList.add(TradeStatusUtils.TRADE_FINISHED);
		payStatusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
		payStatusList.add(TradeStatusUtils.TRADE_CLOSED);
		payStatusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
		payStatusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
		List<String> waitStatusList = new ArrayList<String>();
		waitStatusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
		waitStatusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
		if(payStatusList.contains(trade.getStatus())){
			effectPicture.setPayFee(NumberUtils.getResult(effectPicture.getPayFee()) + NumberUtils.getResult(trade.getPayment()));
			effectPicture.setPayItem(NumberUtils.getResult(effectPicture.getPayItem()) + NumberUtils.getResult(trade.getNum()));
			effectPicture.setPayOrder(NumberUtils.getResult(effectPicture.getPayOrder()) + 1);
			payBuyerSet.add(trade.getBuyerNick());
		}
		if(waitStatusList.contains(trade.getStatus()) && false == trade.isRefundFlag()){
			effectPicture.setWaitPayFee(NumberUtils.getResult(effectPicture.getWaitPayFee()) + NumberUtils.getResult(trade.getPayment()));
			effectPicture.setWaitPayItem(NumberUtils.getResult(effectPicture.getWaitPayItem()) + NumberUtils.getResult(trade.getNum()));
			effectPicture.setWaitPayOrder(NumberUtils.getResult(effectPicture.getWaitPayOrder()) + 1);
			waitPayBuyerSet.add(trade.getBuyerNick());
		}
		if(true == trade.isRefundFlag()){
			effectPicture.setRefundFee(NumberUtils.getResult(effectPicture.getRefundFee()) + NumberUtils.getResult(trade.getPayment()));
			effectPicture.setRefundItem(NumberUtils.getResult(effectPicture.getRefundItem()) + NumberUtils.getResult(trade.getNum()));
			effectPicture.setRefundOrder(NumberUtils.getResult(effectPicture.getRefundOrder()) + 1);
			refundBuyerSet.add(trade.getBuyerNick());
		}
	}
	
	
	/**
	 * 获取今日的营销分析数据
	 * ZTK2017年9月1日上午10:40:47
	 */
	public EffectPicture todayEffectPicture(String userId,Date StartDate,Date endDate,
			List<String> phoneList,String orderSource,Date created) throws Exception{
		if(phoneList != null && !phoneList.isEmpty()){
			EffectPicture effectPicture = new EffectPicture();
			effectPicture.setUserId(userId);
			effectPicture.setEffectTime(StartDate);
			effectPicture.setOrderSource(orderSource);
			if(phoneList.size() > ConstantUtils.PROCESS_PAGE_SIZE_OVER){
				//下单数据
				EffectNum createdEffect = tradeMatchRecordEffect(userId, StartDate, endDate, phoneList, null, orderSource,null);
				EffectNum createdRealEffect = tradeMatchRecordEffect(userId, created, endDate, phoneList, null, orderSource,null);
				//付款数据
				List<String> statusList = new ArrayList<String>();
				statusList.add(TradeStatusUtils.TRADE_FINISHED);
				statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
				statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
				statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
				statusList.add(TradeStatusUtils.TRADE_CLOSED);
				EffectNum successEffect = tradeMatchRecordEffect(userId, StartDate, endDate, phoneList, statusList, orderSource,null);
				EffectNum successRealEffect = tradeMatchRecordEffect(userId, created, endDate, phoneList, statusList, orderSource,null);
				//未付款数据
				statusList.clear();
				statusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
				statusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
				EffectNum waitEffect = tradeMatchRecordEffect(userId, StartDate, endDate, phoneList, statusList, orderSource,false);
				EffectNum waitRealEffect = tradeMatchRecordEffect(userId, created, endDate, phoneList, statusList, orderSource,false);
				//退款数据
				EffectNum refundEffect = tradeMatchRecordEffect(userId, StartDate, endDate, phoneList, null, orderSource,true);
				EffectNum refundRealEffect = tradeMatchRecordEffect(userId, created, endDate, phoneList, null, orderSource,true);
				if(createdEffect != null){
					effectPicture.setTotalOrder(createdEffect.getOrderNum());
					effectPicture.setTotalFee(createdEffect.getPayment());
					effectPicture.setTotalBuyer(createdEffect.getCustomerNum());
					effectPicture.setTotalItem(createdEffect.getItemNum());
				}
				if(successEffect != null){
					effectPicture.setPayOrder(successEffect.getOrderNum());
					effectPicture.setPayFee(successEffect.getPayment());
					effectPicture.setPayBuyer(successEffect.getCustomerNum());
					effectPicture.setPayItem(successEffect.getItemNum());		
				}
				if(waitEffect != null){
					effectPicture.setWaitPayOrder(waitEffect.getOrderNum());
					effectPicture.setWaitPayFee(waitEffect.getPayment());
					effectPicture.setWaitPayBuyer(waitEffect.getCustomerNum());
					effectPicture.setWaitPayItem(waitEffect.getItemNum());
				}
				if(refundEffect != null){
					effectPicture.setRefundOrder(refundEffect.getOrderNum());
					effectPicture.setRefundFee(refundEffect.getPayment());
					effectPicture.setRefundBuyer(refundEffect.getCustomerNum());
					effectPicture.setRefundItem(refundEffect.getItemNum());
				}
				if(createdRealEffect != null){
					effectPicture.setTotalBuyerReal(NumberUtils.getResult(createdRealEffect.getCustomerNum()));
				}
				if(successRealEffect != null){
					effectPicture.setPayBuyerReal(NumberUtils.getResult(successRealEffect.getCustomerNum()));
				}
				if(waitRealEffect != null){
					effectPicture.setWaitPayBuyerReal(NumberUtils.getResult(waitRealEffect.getCustomerNum()));
				}
				if(refundRealEffect != null){
					effectPicture.setRefundBuyerReal(NumberUtils.getResult(refundRealEffect.getCustomerNum()));
				}
			}else {//手机号小于5w，直接使用mongo进行查询
				//下单数据
				EffectNum createdEffect = tradeInfoService.findTotalOrderNum(userId, StartDate,phoneList, endDate, orderSource, null, null);
				EffectNum createdRealEffect = tradeInfoService.findTotalOrderNum(userId, StartDate,phoneList, endDate, orderSource, null, null);
				//付款数据
				List<String> statusList = new ArrayList<String>();
				statusList.add(TradeStatusUtils.TRADE_FINISHED);
				statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
				statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
				statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
				statusList.add(TradeStatusUtils.TRADE_CLOSED);
				//付款数据
				EffectNum successEffect = tradeInfoService.findTotalOrderNum(userId, StartDate, phoneList, endDate, orderSource, statusList, null);
				EffectNum successRealEffect = tradeInfoService.findTotalOrderNum(userId, StartDate, phoneList, endDate, orderSource, statusList, null);
				//未付款数据
				statusList.clear();
				statusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
				statusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
				EffectNum waitEffect = tradeInfoService.findTotalOrderNum(userId, StartDate, phoneList, endDate, orderSource, statusList, false);
				EffectNum waitRealEffect = tradeInfoService.findTotalOrderNum(userId, created, phoneList, endDate, orderSource, statusList, false);
				//退款数据
				EffectNum refundEffect = tradeInfoService.findTotalOrderNum(userId, StartDate, phoneList, endDate, orderSource, null, true);
				EffectNum refundRealEffect = tradeInfoService.findTotalOrderNum(userId, created, phoneList, endDate, orderSource, null, true);
				if(createdEffect != null){
					effectPicture.setTotalOrder(createdEffect.getOrderNum());
					effectPicture.setTotalFee(createdEffect.getPayment());
					effectPicture.setTotalBuyer(createdEffect.getCustomerNum());
					effectPicture.setTotalItem(createdEffect.getItemNum());
				}
				if(successEffect != null){
					effectPicture.setPayOrder(successEffect.getOrderNum());
					effectPicture.setPayFee(successEffect.getPayment());
					effectPicture.setPayBuyer(successEffect.getCustomerNum());
					effectPicture.setPayItem(successEffect.getItemNum());		
				}
				if(waitEffect != null){
					effectPicture.setWaitPayOrder(waitEffect.getOrderNum());
					effectPicture.setWaitPayFee(waitEffect.getPayment());
					effectPicture.setWaitPayBuyer(waitEffect.getCustomerNum());
					effectPicture.setWaitPayItem(waitEffect.getItemNum());
				}
				if(refundEffect != null){
					effectPicture.setRefundOrder(refundEffect.getOrderNum());
					effectPicture.setRefundFee(refundEffect.getPayment());
					effectPicture.setRefundBuyer(refundEffect.getCustomerNum());
					effectPicture.setRefundItem(refundEffect.getItemNum());
				}
				if(createdRealEffect != null){
					effectPicture.setTotalBuyerReal(NumberUtils.getResult(createdRealEffect.getCustomerNum()));
				}
				if(successRealEffect != null){
					effectPicture.setPayBuyerReal(NumberUtils.getResult(successRealEffect.getCustomerNum()));
				}
				if(waitRealEffect != null){
					effectPicture.setWaitPayBuyerReal(NumberUtils.getResult(waitRealEffect.getCustomerNum()));
				}
				if(refundRealEffect != null){
					effectPicture.setRefundBuyerReal(NumberUtils.getResult(refundRealEffect.getCustomerNum()));
				}
			}
			return effectPicture;
		}
		return null;
	}
	
	
	/**
	 * 通过订单匹配发送记录（效果分析发送手机号大于5w）
	 * ZTK2017年8月30日上午10:57:49
	 */
	public EffectNum tradeMatchRecordEffect(String userId,Date beginTime,Date endTime,List<String> phones,
			List<String> statusList,String orderSource,Boolean refundFlag) throws Exception{
		EffectNum effectNum = new EffectNum();
		double payment = 0.0;//金额
		int orderNum = 0;//下单数
		int customerNum = 0;//客户数
		long itemNum = 0;//商品数量
		if(phones != null && !phones.isEmpty()){
			try {
				Criteria criteria = Criteria.where("sellerNick").is(userId);
				if(beginTime != null && endTime != null){
					criteria.and("created").gte(beginTime.getTime()).lte(endTime.getTime());
				}
				if(statusList != null && !statusList.isEmpty()){
					criteria.and("status").in(statusList);
				}
				if(orderSource != null && !"".equals(orderSource) && !"TOTAL".equals(orderSource)){
					criteria.and("tradeFrom").regex(".*?" + orderSource + ".*");
				}
				if(refundFlag != null){
					criteria.and("refundFlag").is(refundFlag);
				}
				List<TradeDTO> tradeDTOs = tradeInfoService.findList(new Query(criteria), userId);
				if(tradeDTOs != null){
					Set<String> buyerSet = new HashSet<String>();
					for (int i = 0; i < tradeDTOs.size(); i++) {
						TradeDTO tradeDTO = tradeDTOs.get(i);
						if(tradeDTO != null && tradeDTO.getReceiverMobile() != null && phones.contains(tradeDTO.getReceiverMobile())){
							payment += NumberUtils.getResult(tradeDTO.getReceivedPayment());
							buyerSet.add(tradeDTO.getBuyerNick());
							itemNum += tradeDTO.getNum();
						}
					}
					orderNum = tradeDTOs.size();
					customerNum = buyerSet.size();
				}
				effectNum.setCustomerNum(customerNum);
				effectNum.setItemNum(itemNum);
				effectNum.setOrderNum(orderNum);
				effectNum.setPayment(payment);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return effectNum;
		}else {
			return null;
		}
	}
	
	
	/**
	 * 填充并保存
	 * ZTK2017年9月1日下午3:15:29
	 * @throws Exception 
	 */
	public void paddingAndSave(EffectPicture effectPicture,Long msgId,Integer days,
			Date sendTime) throws Exception{
		if(effectPicture != null){
			effectPicture.setMsgId(msgId);
			effectPicture.setDays(days);
			effectPicture.setSendTime(sendTime);
			insertEffectPicture(effectPicture);
		}
	}
	
	/**
	 * 效果分析页面的分析数据
	 * ZTK2017年9月4日上午11:10:16
	 */
	public EffectPicture findEffectBySource(Long msgId,String orderSource,String userId,
			int days){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("msgId", msgId);
		dataMap.put("orderSource", orderSource);
		dataMap.put("days", days);
		dataMap.put("userId", userId);
		EffectPicture effectPicture = myBatisDao.findBy(EffectPicture.class.getName(), "findTotalEffectByMsgId", dataMap);
		return effectPicture;
	}
	
	/**
	 * 效果分析页面的每日数据
	 */
	public List<EffectPicture> allEffectByDay(Long msgId,String orderSource,String userId,
			int days){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("msgId", msgId);
		dataMap.put("orderSource", orderSource);
		dataMap.put("days", days);
		dataMap.put("userId", userId);
		List<EffectPicture> effectPictures = myBatisDao.findList(EffectPicture.class.getName(), "findEffectByDays", dataMap);
		return effectPictures;
	}
	
	/**
	 * 首页计算会员营销数据
	 * ZTK2017年9月6日上午11:03:54
	 */
	public double findSuccessPayFeeByTime(String userId,Date bTime,Date eTime,Long msgId){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		dataMap.put("userId", userId);
		dataMap.put("bTime", bTime);
		dataMap.put("eTime", eTime);
		dataMap.put("msgId", msgId);
		
		Double payFee = myBatisDao.findBy(EffectPicture.class.getName(), "findSuccessPayFeeByTime", dataMap);
		return NumberUtils.getResult(payFee);
	}

	public void syncEffectData(Map<String,Object> map) {
		Long msgId = (Long)map.get("msgId");
		MsgSendRecord msgRecord = msgSendRecordService.findOne(msgId);
		if(msgRecord != null){
			try {
			   long currentNum = 1l;
			   // 查询msgId 对应的发送记录手机号，存入缓存map
			   List<String>  recodList =  putRecordIntoCache(msgRecord);
			   boolean isEqual=false;
			   Long startTime = DateUtils.dateToLong(msgRecord.getCreatedDate());
			   Date endDate = DateUtils.addDate(msgRecord.getCreatedDate(), 15);
			   Long endTime = DateUtils.dateToLong(endDate);
			   logger.info("开始时间"+DateUtils.dateToString(msgRecord.getCreatedDate(), DateUtils.DEFAULT_TIME_FORMAT)+"结束时间"+DateUtils.dateToString(endDate, DateUtils.DEFAULT_TIME_FORMAT));	
			   //根据时间筛选满足条件的订单(发送记录后15天)
			   Query query = new Query();
			   query.addCriteria(Criteria.where("created").gte(startTime).lte(endTime));
			   query.addCriteria(Criteria.where("type").ne("import"));
			   long dataCount = tradeInfoService.count(query, msgRecord.getUserId());
			   logger.info("用户15天订单总数记录"+msgRecord.getUserId()+"数据总量"+dataCount);
			   Long pageSize = ConstantUtils.PROCESS_PAGE_SIZE_MAX;
			   List<TradeDTO> allTradeList = new  ArrayList<TradeDTO>();
			   while (true) {
				   Query query1 = new Query();
				   query1.addCriteria(Criteria.where("created").gte(startTime).lte(endTime));
				   query1.addCriteria(Criteria.where("type").ne("import"));
				   String id = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_DATA_KEY);
				   logger.info("用户订单总数记录数据开始id"+id);
				   if(dataCount<ConstantUtils.PROCESS_PAGE_SIZE_MAX){
					   pageSize = dataCount;
				   }
				   if(currentNum==1){ isEqual=true;}else{isEqual=false;}
				   List<TradeDTO> migrateTradeDataList = tradeInfoService.findMigrateTradeDataList(query1,msgRecord.getUserId(),id,pageSize.intValue(),isEqual);
				   if (null == migrateTradeDataList || migrateTradeDataList.size() == 0) break;
				   try {
					   logger.info("一批数据size"+migrateTradeDataList.size()+"当前页数"+currentNum);
					   currentNum++;
					   TradeDTO tradeDTO  = migrateTradeDataList.get(migrateTradeDataList.size()-1);
					   cacheService.putNoTime(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_DATA_KEY,tradeDTO.get_id());
					   dataCount = dataCount-pageSize;
					   allTradeList.addAll(migrateTradeDataList);
					   if(pageSize<ConstantUtils.PROCESS_PAGE_SIZE_MAX){
						   Map<String,Object>  resultMap = new HashMap<String,Object>();
						   List<TradeDTO> tradeList = packValidTradeList(allTradeList,recodList);
						   System.out.println("fdasfffffffffffffff"+JsonUtil.toJson(tradeList));
						   resultMap.put("tradeList", tradeList);
						   resultMap.put("msgRecord", msgRecord);
						   SyncEffectQueueService.queue.put(resultMap);
						   Thread.sleep(InitDataManageUtil.sleepTimePer);
						   int size = SyncEffectQueueService.queue.size();
						   if(size>5){
							   Thread.sleep(InitDataManageUtil.sleepTimePer*5);
						   }
						   break;
					   }
					} catch (Exception e) {
						 e.printStackTrace();
						 continue;
					}
			   }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			logger.info("msgId记录为空！"+msgId);	
		}
	}

	private List<TradeDTO> packValidTradeList(List<TradeDTO> allTradeList,
			List<String> recodList) {
		List<TradeDTO> resultLit  = new ArrayList<TradeDTO>();
		if(null!=recodList&&recodList.size()>0){
			Map<String,List<TradeDTO>> m  = new LinkedHashMap<String, List<TradeDTO>>();
			List<TradeDTO> tradeList  = null;
			for (TradeDTO tradeDTO : allTradeList) {
				tradeDTO.setOrders(null);
				List<TradeDTO> list = m.get(tradeDTO.getReceiverMobile());
				if(null!=list&&list.size()>0){
					list.add(tradeDTO);
				}else{
					tradeList = new ArrayList<TradeDTO>();
					tradeList.add(tradeDTO);
					m.put(tradeDTO.getReceiverMobile(),tradeList);
				}
			}
			Set<String> keySet = m.keySet();
			recodList.retainAll(keySet); 
			
			for (String string : recodList) {
				List<TradeDTO> list = m.get(string);
				resultLit.addAll(list);
			}
		}
		return resultLit;
	}
	
	private BigDecimal anasyItemPrice(TradeDTO tradeDTO){
		BigDecimal  result = new BigDecimal(0);
		if(null!=tradeDTO){
			Double price = tradeDTO.getPrice();
			Long num = tradeDTO.getNum();
			if(null==num||0==num){
				num=1l;
			}
			result = BigDecimalUtil.divide(new BigDecimal(price),2, new BigDecimal(num));
		}
		return  result;
	}
	
	public void handleEffectData(Map<String, Object> map) {
		//匹配记录
//		@SuppressWarnings("unchecked")
//		List<TradeDTO> tradeList = (List<TradeDTO>) map.get("tradeList");
//		MsgSendRecord msgRecord = (MsgSendRecord)map.get("msgRecord");
//		Map<String, EffectPicture> matchTradeFromSmsRecord = null;
//		
//		EffectParamVo  effectParamVo = new EffectParamVo();
//	    
//		if(null!=tradeList&&tradeList.size()>0&&null!=msgRecord){
//			Long msgId = msgRecord.getId();
//			matchTradeFromSmsRecord = matchTradeFromSmsRecord(tradeList, msgRecord, msgId,null,effectParamVo);
//		}else{
//			logger.info("队列传过来的参数为空");	
//		}
//		// 保存匹配记录
//		if(null!=matchTradeFromSmsRecord){
//				for(Entry<String,EffectPicture> entry : matchTradeFromSmsRecord.entrySet()){
//					try {
//						packeageFinalLogic(entry,effectParamVo);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//		}
		Set<String> itemSet =  new HashSet<String>();
		Set<String> existItemSet =  new HashSet<String>();
		List<TradeDTO> tradeList = (List<TradeDTO>) map.get("tradeList");
		String userNick = (String)map.get("userNick");
		List<Item> itemList =  (List<Item>) map.get("itemList"); 
		
		
		Map<String,BigDecimal>  itemMap1 = new HashMap<String, BigDecimal>();
		for (Item item : itemList) {
			existItemSet.add(item.getTitle());
		}
		if(null!=tradeList&&tradeList.size()>0){
			for (TradeDTO tradeDTO : tradeList) {
				String title = createTitle(tradeDTO);
				if(null!=title&&!"".equals(title)){
					itemSet.add(title);
					itemMap1.put(title,anasyItemPrice(tradeDTO));
				}
			}
		}
		itemSet.removeAll(existItemSet);
		
		List<Item> insertItemList = new ArrayList<Item>();
		Item  item = null;
		for (String title : itemSet) {
			item = new Item();
			item.setTitle(title);
			item.setNick(userNick);
			long currentTimeMillis = System.currentTimeMillis();
			item.setNumIid(currentTimeMillis);
			item.setNum(0);
			item.setApproveStatus("instock");
			item.setItem_desc("system");
			item.setCreatedDate(new Date());
			item.setPrice(String.valueOf(itemMap1.get(title)));
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
			item.setCreatedDate(new Date());
			insertItemList.add(item);
		}
		Map<String,Long>  itemMap = itemService.insertAndFindItenList(insertItemList,userNick);
		
		int i = 0;
		MemberDTO memberInfo =null;
		for (TradeDTO tradeDTO : tradeList) {
			try {
				String title = createTitle(tradeDTO);
				if(null!=title&&!"".equals(title)){
					Long numIId = itemMap.get(title);
					i++;
					 if(i!=0&&i%500==0){ Thread.sleep(1000); }
					 memberInfo =  new MemberDTO();
					 memberInfo.setBuyerNick(tradeDTO.getBuyerNick()); 
					 memberInfo.setNUM_IID(String.valueOf(numIId)); 
					 memberInfo.setUserId(userNick);
					 vipMemberService.updateMemberInfoByParam(memberInfo,5);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
		
		
//		try {
//			String title="";
//			List<TradeDTO> tradeList = (List<TradeDTO>) map.get("tradeList");
//			String userNick = (String)map.get("userNick");
//			int i =0 ;
//			MemberDTO memberInfo =null;
//			for (TradeDTO tradeDTO : tradeList) {
//				 if(null!=tradeDTO.getOmnichannelParam()&&!"".equals(tradeDTO.getOmnichannelParam())){
//					 title=tradeDTO.getOmnichannelParam();
//				 }else{
//					 if(tradeDTO.getSellerNick().equals(tradeDTO.getTitle())){
//						 List<OrdersDTO> orders = tradeDTO.getOrders();
//						 if(null!=orders&&orders.size()>0){
//							 title = orders.get(0).getTitle();
//						 }
//					 }else{
//						 title=tradeDTO.getTitle();
//					 }
//				 }
//				 if(null!=title&&!"".equals(title)){
//					 String matchNumIId = matchNumIId(title);
//					 if(null!=matchNumIId&&!"".equals(matchNumIId)){
//						 String[] split = matchNumIId.split(","); 
//						 for (String string : split) {
//							 i++;
//							 if(i!=0&&i%500==0){
//								 Thread.sleep(1000);
//							 }
//							 memberInfo =  new MemberDTO();
//							 memberInfo.setBuyerNick(tradeDTO.getBuyerNick()); 
//							 memberInfo.setNUM_IID(string); 
//							 memberInfo.setUserId(userNick);
//							 vipMemberService.updateMemberInfoByParam(memberInfo,5);
//						 }
//					 }
//				 }
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println(currentTimeMillis);
		
	}

	private String createTitle(TradeDTO tradeDTO) {
		String title="";
		if(null!=tradeDTO.getOmnichannelParam()&&!"".equals(tradeDTO.getOmnichannelParam())){
			 title=tradeDTO.getOmnichannelParam();
		 }else{
			 if(tradeDTO.getSellerNick().equals(tradeDTO.getTitle())){
				 List<OrdersDTO> orders = tradeDTO.getOrders();
				 if(null!=orders&&orders.size()>0){
					 title = orders.get(0).getTitle();
				 }
			 }else{
				 title=tradeDTO.getTitle();
			 }
		 }
		return title;
	}
	
	public String   matchNumIId(String title){
		if(title.contains("海鲜")&&title.contains("组合"))return "559649060467";
		if(title.contains("香酥鱼")&&title.contains("组合"))return "559650032073";
		if(title.contains("鱿鱼鲜")&&(title.contains("组合")||title.contains("整箱")))return "559798526499";
		if(title.contains("干制鱼")&&(title.contains("组合")||title.contains("整箱")))return "559868047138";
		if(title.contains("海鲜礼盒")&&title.contains("套餐"))return "548095307174";
		if(title.contains("海鲜")&&title.contains("年货礼盒"))return "524363258251";
		if(title.contains("香酥刀鱼鱼干"))return "524565734995";
		if(title.contains("不带籽墨鱼仔"))return "524576740402";
		if(title.contains("小黄鱼")&&title.contains("包邮"))return "559487635463";
		if(title.contains("碳烤鱼片干"))return "43346007557";
		if(title.contains("即食小鱼干"))return "556875736616";
		if(title.contains("烤鱼排骨"))return "43367182415,559423434680";
		if(title.contains("鱿鱼片仔")&&title.contains("年货 "))return "525501936938";
		if(title.contains("东海刀鱼鱼干"))return "43368114322";
		if(title.contains("带须不带籽墨鱼仔"))return "542448271346";
		if(title.contains("安康鱼片"))return "559271084047,542703672991,531484959104";
		if(title.contains("不带籽墨鱼仔须"))return "542555484537";
		if(title.contains("香酥黄花鱼"))return "542703400011";
		if(title.contains("毛毛鱼")||title.contains("口水鱼"))return "559364477096";
		if(title.contains("日式烤鳗鱼"))return "528989173374,43655154029";
		if(title.contains("铁板鱿鱼"))return "559423022279,542730518506";
		if(title.contains("手撕风琴鱿鱼"))return "542729598008,531564349604";
		if(title.contains("豆豉迪仔鱼"))return "43589219457";
		if(title.contains("豆腐鱼干"))return "542874844257";
		if(title.contains("香酥带鱼"))return "543034538983,531490831603,44742342129";
		if(title.contains("香辣带鱼"))return "531545502511";
		if(title.contains("烤鱿鱼须片"))return "543093136442,559485871562";
		if(title.contains("鱿鱼头足"))return "531486659180";
		if(title.contains("碳烤风琴鱿鱼"))return "44282757773";
		if(title.contains("整箱")&&title.contains("年货"))return "559557683399";
		if(title.contains("黄花鱼干小鱼仔"))return "531545106078";
		if(title.contains("香酥鱼排骨"))return "531548150993";
		if(title.contains("黄花鱼干")&&title.contains("包邮"))return "520111502202";
		if(title.contains("带籽鱿鱼仔"))return "520189506940";
		if(title.contains("香酥鱼骨粒"))return "531570797404";
		if(title.contains("毛毛鱼口水鱼"))return "520510745461";
		if(title.contains("烧烤墨鱼仔"))return "531591736611";
		if(title.contains("安康鱼松"))return "520529417596";
		if(title.contains("日式烤鳗片"))return "531599032313,559486599557";
		if(title.contains("烧烤鱿鱼须"))return "521161062269";
		if(title.contains("鱿鱼条"))return "43367902429";
		if(title.contains("珍味鱼脯"))return "531593172970";
		if(title.contains("香酥玉鳎"))return "43747468845";
		if(title.contains("鱼骨头干"))return "43344071621";
		if(title.contains("黄花鱼"))return "43674715927";
		if(title.contains("碳烤风琴鱿鱼丝"))return "44301392352";
		if(title.contains("香酥鱼骨粒"))return "45704539484,45786357197";
		if(title.contains("香烤龙头鱼"))return "559739977625";
		if(title.contains("马面鱼"))return "523964020123,559274284039,43401485259,531597760038";
		if(title.contains("小黄鱼"))return "524700561507,43403437876,529585457166,44384290951,520005492072,531568197417";
		if(title.contains("干货年货"))return "525365137674";
		return  "";
	}
	
	
	 
	private void packeageFinalLogic(Entry<String, EffectPicture> entry,EffectParamVo effectParamVo) throws Exception {
		 String key = entry.getKey();
		 EffectPicture value = entry.getValue();
		 packageRealCustomerData(value,key,effectParamVo);
		 insertEffectPicture(value);
	}
	
	
	
	private void packageRealCustomerData(EffectPicture effectPicture,String key,EffectParamVo effectParamVo ) {
		
		Map<String, Set<String>> partMap = effectParamVo.getPartMap();
		Map<String, Set<String>> payBuyerMap = effectParamVo.getPayBuyerMap();
		Map<String, Set<String>> waitPayBuyerMap = effectParamVo.getWaitPayBuyerMap();
		Map<String, Set<String>> refundBuyerMap = effectParamVo.getRefundBuyerMap();
		
		
		Set<String> set =  partMap.get(key);
	    Set<String> set2 = payBuyerMap.get(key);
	    Set<String> set3 = waitPayBuyerMap.get(key);
	    Set<String> set4 = refundBuyerMap.get(key);
	    if(null!=set&&set.size()>0)  effectPicture.setTotalBuyer(set.size());
	    if(null!=set2&&set2.size()>0) effectPicture.setPayBuyer(set2.size());
	    if(null!=set3&&set3.size()>0) effectPicture.setWaitPayBuyer(set3.size());
	    if(null!=set4&&set4.size()>0)effectPicture.setRefundBuyer(set4.size());
	    String[] split = key.split("==");
	    String  msgId = split[0];
	    String  source =  split[1]; 
	    int  total = Integer.parseInt(split[2]);
		Set<String> realPartSet = new TreeSet<String>();
		Set<String> payBuyerSet = new TreeSet<String>();
		Set<String> waitPayBuyerSet = new TreeSet<String>();
		Set<String> refundBuyerSet = new TreeSet<String>();
	    for (int i = 0; i <= total; i++) {
	    	 Set<String> set5 = partMap.get(msgId+"=="+source+"=="+i);
	    	 Set<String> set6 = payBuyerMap.get(msgId+"=="+source+"=="+i);
	    	 Set<String> set7 = waitPayBuyerMap.get(msgId+"=="+source+"=="+i);
	    	 Set<String> set8 = refundBuyerMap.get(msgId+"=="+source+"=="+i);
	    	 if(null!=set5&&set5.size()>0)  realPartSet.addAll(set5);
	 	     if(null!=set6&&set6.size()>0)  payBuyerSet.addAll(set6);
	 	     if(null!=set7&&set7.size()>0)  waitPayBuyerSet.addAll(set7);
	 	     if(null!=set8&&set8.size()>0)  refundBuyerSet.addAll(set8);
		}
	    if(null!=realPartSet&&realPartSet.size()>0)  effectPicture.setTotalBuyerReal(realPartSet.size());
	    if(null!=payBuyerSet&&payBuyerSet.size()>0)  effectPicture.setPayBuyerReal(payBuyerSet.size());
	    if(null!=waitPayBuyerSet&&waitPayBuyerSet.size()>0)  effectPicture.setWaitPayBuyerReal(waitPayBuyerSet.size());
	    if(null!=refundBuyerSet&&refundBuyerSet.size()>0)  effectPicture.setRefundBuyerReal(refundBuyerSet.size());
	}

	

	private Map<String,EffectPicture>  matchTradeFromSmsRecord(List<TradeDTO> tradeList,
			MsgSendRecord msgRecord, Long msgId, List<String> smsReocrdList,EffectParamVo effectParamVo) {
		EffectPicture effectPicture = null;
		EffectPicture totalEffect = null;
		Map<String,EffectPicture> m = new HashMap<String,EffectPicture>();
		Map<String,EffectPicture> mTotal = new HashMap<String,EffectPicture>();
		for (TradeDTO tradeDTO : tradeList) {
			try {
				if(tradeDTO != null && tradeDTO.getReceiverMobile() != null){
					Long diffDay2 = DateUtils.getDiffDay(msgRecord.getCreatedDate(),DateUtils.longToDate(tradeDTO.getCreated(), DateUtils.DEFAULT_TIME_FORMAT))+1;
					String orderSource = "";
					if(tradeDTO.getTradeFrom().contains(TradesInfo.ORDER_FROM_TAOBAO)){ orderSource="TAOBAO";
					}else if(tradeDTO.getTradeFrom().contains(TradesInfo.ORDER_FROM_WAP)){
						orderSource="WAP,WAP";
					}else{ continue; }
					
					if(null==mTotal.get(msgId+"=="+diffDay2)){
						totalEffect = new EffectPicture();
						int days = diffDay2.intValue();
						totalEffect.setDays(days);
						totalEffect.setMsgId(msgRecord.getId());
						totalEffect.setUserId(msgRecord.getUserId());
						totalEffect.setSendTime(msgRecord.getCreatedDate());
						totalEffect.setOrderSource("TOTAL");
						totalEffect.setCreatedDate(new Date());
						totalEffect.setLastModifiedDate(new Date());
						totalEffect.setCreatedBy(tradeDTO.getSellerNick());
						totalEffect.setLastModifiedBy(tradeDTO.getSellerNick());
						totalEffect.setEffectTime(DateUtils.longToDate(tradeDTO.getCreated(),"yyyy-MM-dd 23:59:59"));
						packageTotalFirstEffectPicture(totalEffect, mTotal, tradeDTO, "TOTAL", msgRecord);
					}else{
						totalEffect = mTotal.get(msgId+"=="+diffDay2);
						packageTotalEffectPicture(totalEffect, mTotal, tradeDTO, "TOTAL", msgRecord);
					}
					
					if(tradeDTO.getTradeFrom().contains(TradesInfo.ORDER_FROM_TAOBAO)){
						if(null!=m.get(msgId+"==TAOBAO"+"=="+diffDay2)){
							effectPicture = m.get(msgId+"==TAOBAO"+"=="+diffDay2);
							packageEffectPicture(effectPicture, m, tradeDTO, "TAOBAO", msgRecord,effectParamVo);
						}else{
							effectPicture = new EffectPicture();
							Long diffDay = DateUtils.getDiffDay(msgRecord.getCreatedDate(),DateUtils.longToDate(tradeDTO.getCreated(), DateUtils.DEFAULT_TIME_FORMAT))+1;
							int days = diffDay.intValue();
							effectPicture.setDays(days);
							effectPicture.setMsgId(msgRecord.getId());
							effectPicture.setUserId(msgRecord.getUserId());
							effectPicture.setSendTime(msgRecord.getCreatedDate());
							effectPicture.setOrderSource(TradesInfo.ORDER_FROM_TAOBAO);
							effectPicture.setCreatedDate(new Date());
							effectPicture.setLastModifiedDate(new Date());
							effectPicture.setCreatedBy(tradeDTO.getSellerNick());
							effectPicture.setLastModifiedBy(tradeDTO.getSellerNick());
							effectPicture.setEffectTime(DateUtils.longToDate(tradeDTO.getCreated(),"yyyy-MM-dd 23:59:59"));
							packageFirstEffectPicture(effectPicture, m, tradeDTO, TradesInfo.ORDER_FROM_TAOBAO, msgRecord,effectParamVo);
						}
						paddingMap(effectPicture, tradeDTO, orderSource, diffDay2, effectParamVo.getPartMap());
					}else if(tradeDTO.getTradeFrom().contains(TradesInfo.ORDER_FROM_WAP)){
						if(null!=m.get(msgId+"==WAP,WAP"+"=="+diffDay2)){
							effectPicture = m.get(msgId+"==WAP,WAP"+"=="+diffDay2);
							packageEffectPicture(effectPicture, m, tradeDTO, "WAP,WAP", msgRecord,effectParamVo);
						}else{
							effectPicture = new EffectPicture();
							Long diffDay = DateUtils.getDiffDay(msgRecord.getCreatedDate(),DateUtils.longToDate(tradeDTO.getCreated(), DateUtils.DEFAULT_TIME_FORMAT))+1;
							int days =diffDay.intValue();
							effectPicture.setDays(days);
							effectPicture.setMsgId(msgRecord.getId());
							effectPicture.setUserId(msgRecord.getUserId());
							effectPicture.setSendTime(msgRecord.getCreatedDate());
							effectPicture.setOrderSource("WAP,WAP");
							effectPicture.setCreatedDate(new Date());
							effectPicture.setLastModifiedDate(new Date());
							effectPicture.setLastModifiedDate(new Date());
							effectPicture.setCreatedBy(tradeDTO.getSellerNick());
							effectPicture.setEffectTime(DateUtils.longToDate(tradeDTO.getCreated(),"yyyy-MM-dd 23:59:59"));
							packageFirstEffectPicture(effectPicture, m, tradeDTO, "WAP,WAP", msgRecord,effectParamVo);
						}
						paddingMap(effectPicture, tradeDTO, orderSource, diffDay2, effectParamVo.getPartMap());
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(null!=mTotal){
			for(Entry<String,EffectPicture> entry : mTotal.entrySet()){
				try {
					EffectPicture value = entry.getValue();
					packageTotalRealCustomerData(value,entry.getKey(),effectParamVo);
					insertEffectPicture(value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
		
		
		return m;
	}
	
	private void packageTotalRealCustomerData(EffectPicture effectPicture,String key ,EffectParamVo effectParamVo) {
		Map<String, Set<String>> partMap = effectParamVo.getPartMap();
		Map<String, Set<String>> payBuyerMap = effectParamVo.getPayBuyerMap();
		Map<String, Set<String>> waitPayBuyerMap = effectParamVo.getWaitPayBuyerMap();
		Map<String, Set<String>> refundBuyerMap = effectParamVo.getRefundBuyerMap();
		String key1 = "";
		String key2 = "";
		String[] split = key.split("==");
		key1=split[0]+"==TAOBAO=="+split[1];
		key2=split[0]+"==WAP,WAP=="+split[1];
		Set<String> setT = new HashSet<String>();
		Set<String> setB = new HashSet<String>();
		Set<String> setP = new HashSet<String>();
		Set<String> setR = new HashSet<String>();
		
		Set<String> set =  partMap.get(key1);
		Set<String> set1 =  partMap.get(key2);
		if(null!=set&&set.size()>0)  setT.addAll(set);
		if(null!=set1&&set1.size()>0) setT.addAll(set1);
		Set<String> set2 =  payBuyerMap.get(key1);
		Set<String> set3 =  payBuyerMap.get(key2);
		if(null!=set2&&set2.size()>0)  setB.addAll(set2);
		if(null!=set3&&set3.size()>0) setB.addAll(set3);
		Set<String> set4 =  waitPayBuyerMap.get(key1);
		Set<String> set5 =  waitPayBuyerMap.get(key2);
		if(null!=set4&&set4.size()>0)  setP.addAll(set4);
		if(null!=set5&&set5.size()>0) setP.addAll(set5);
		Set<String> set6 =  refundBuyerMap.get(key1);
		Set<String> set7 =  refundBuyerMap.get(key2);
		if(null!=set6&&set6.size()>0)  setR.addAll(set6);
		if(null!=set7&&set7.size()>0) setR.addAll(set7);
		
	    if(null!=setT&&setT.size()>0)  effectPicture.setTotalBuyer(setT.size());
	    if(null!=setB&&setB.size()>0) effectPicture.setPayBuyer(setB.size());
	    if(null!=setP&&setP.size()>0) effectPicture.setWaitPayBuyer(setP.size());
	    if(null!=setR&&setR.size()>0)effectPicture.setRefundBuyer(setR.size());
	    
 
		Set<String> realPartSet = new TreeSet<String>();
		Set<String> payBuyerSet = new TreeSet<String>();
		Set<String> waitPayBuyerSet = new TreeSet<String>();
		Set<String> refundBuyerSet = new TreeSet<String>();
		String msgId = split[0];
		int total = Integer.parseInt(split[1]);
		String[] sourceArr ={"TAOBAO","WAP,WAP"};
	    for (int i = 1; i <= total; i++) {
	    	 for (String source : sourceArr) {
	    		 Set<String> set9 = partMap.get(msgId+"=="+source+"=="+i);
	    		 Set<String> set10 = payBuyerMap.get(msgId+"=="+source+"=="+i);
	    		 Set<String> set11 = waitPayBuyerMap.get(msgId+"=="+source+"=="+i);
	    		 Set<String> set12 = refundBuyerMap.get(msgId+"=="+source+"=="+i);
	    		 if(null!=set9&&set9.size()>0)  realPartSet.addAll(set9);
	    		 if(null!=set10&&set10.size()>0)  payBuyerSet.addAll(set10);
	    		 if(null!=set11&&set11.size()>0)  waitPayBuyerSet.addAll(set11);
	    		 if(null!=set12&&set12.size()>0)  refundBuyerSet.addAll(set12);
			 }	
		}
	    if(null!=realPartSet&&realPartSet.size()>0)  effectPicture.setTotalBuyerReal(realPartSet.size());
	    if(null!=payBuyerSet&&payBuyerSet.size()>0)  effectPicture.setPayBuyerReal(payBuyerSet.size());
	    if(null!=waitPayBuyerSet&&waitPayBuyerSet.size()>0)  effectPicture.setWaitPayBuyerReal(waitPayBuyerSet.size());
	    if(null!=refundBuyerSet&&refundBuyerSet.size()>0)  effectPicture.setRefundBuyerReal(refundBuyerSet.size());
	}
	
	/**
	 * 添加不是第一次的数据
	 * ZTK2017年9月7日下午6:37:45
	 * @throws Exception 
	 */
	public void packageEffectPicture(EffectPicture effectPicture,Map<String,EffectPicture> map,TradeDTO tradeDTO,
			String orderSource,MsgSendRecord msgRecord,EffectParamVo effectParamVo) throws Exception{
		
		effectPicture.setTotalFee(NumberUtils.getResult(effectPicture.getTotalFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
		effectPicture.setTotalItem(NumberUtils.getResult(effectPicture.getTotalItem()) + NumberUtils.getResult(tradeDTO.getNum()));
		effectPicture.setTotalOrder(NumberUtils.getResult(effectPicture.getTotalOrder()) + 1);
		//付款数据
		Long diffDay2 = DateUtils.getDiffDay(msgRecord.getCreatedDate(),DateUtils.longToDate(tradeDTO.getCreated(), DateUtils.DEFAULT_TIME_FORMAT))+1;
		List<String> payStatusList = new ArrayList<String>();
		payStatusList.add(TradeStatusUtils.TRADE_FINISHED);
		payStatusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
		payStatusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
		payStatusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
		payStatusList.add(TradeStatusUtils.TRADE_CLOSED);
		//未付款数据
		List<String> waitStatusList = new ArrayList<String>();
		waitStatusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
		waitStatusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
		if(payStatusList.contains(tradeDTO.getStatus())){
			paddingMap(effectPicture, tradeDTO, orderSource, diffDay2,effectParamVo.getPayBuyerMap());
			effectPicture.setPayFee(NumberUtils.getResult(effectPicture.getPayFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
			effectPicture.setPayItem(NumberUtils.getResult(effectPicture.getPayItem()) + NumberUtils.getResult(tradeDTO.getNum()));
			effectPicture.setPayOrder(NumberUtils.getResult(effectPicture.getPayOrder()) + 1);
		}
		if(waitStatusList.contains(tradeDTO.getStatus()) && !true == tradeDTO.isRefundFlag()){
			paddingMap(effectPicture, tradeDTO, orderSource, diffDay2,effectParamVo.getWaitPayBuyerMap());
			effectPicture.setWaitPayFee(NumberUtils.getResult(effectPicture.getWaitPayFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
			effectPicture.setWaitPayItem(NumberUtils.getResult(effectPicture.getWaitPayItem()) + NumberUtils.getResult(tradeDTO.getNum()));
			effectPicture.setWaitPayOrder(NumberUtils.getResult(effectPicture.getWaitPayOrder()) + 1);
		}
		if(true == tradeDTO.isRefundFlag()){
			paddingMap(effectPicture, tradeDTO, orderSource, diffDay2,effectParamVo.getRefundBuyerMap());
			effectPicture.setRefundFee(NumberUtils.getResult(effectPicture.getRefundFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
			effectPicture.setRefundItem(NumberUtils.getResult(effectPicture.getRefundItem()) + NumberUtils.getResult(tradeDTO.getNum()));
			effectPicture.setRefundOrder(NumberUtils.getResult(effectPicture.getRefundOrder()) + 1);
		}
		map.put(effectPicture.getMsgId()+"=="+orderSource+"=="+diffDay2, effectPicture);
	}
	
	/**
	 * 添加第一次的数据
	 * ZTK2017年9月7日下午7:03:03
	 * @throws Exception 
	 */
	public void packageFirstEffectPicture(EffectPicture effectPicture,Map<String,EffectPicture> map,TradeDTO tradeDTO,
			String orderSource,MsgSendRecord msgRecord,EffectParamVo effectParamVo) throws Exception{
		effectPicture.setTotalFee(tradeDTO.getPayment());
		effectPicture.setTotalItem(tradeDTO.getNum());
		effectPicture.setTotalOrder(1);
		//付款数据
		Long diffDay2 = DateUtils.getDiffDay(msgRecord.getCreatedDate(),DateUtils.longToDate(tradeDTO.getCreated(), DateUtils.DEFAULT_TIME_FORMAT))+1;
		List<String> payStatusList = new ArrayList<String>();
		payStatusList.add(TradeStatusUtils.TRADE_FINISHED);
		payStatusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
		payStatusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
		payStatusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
		payStatusList.add(TradeStatusUtils.TRADE_CLOSED);
		//未付款数据
		List<String> waitStatusList = new ArrayList<String>();
		waitStatusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
		waitStatusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
		if(payStatusList.contains(tradeDTO.getStatus())){
			paddingMap(effectPicture, tradeDTO, orderSource, diffDay2,effectParamVo.getPayBuyerMap());
			effectPicture.setPayFee(tradeDTO.getPayment());
			effectPicture.setPayItem(tradeDTO.getNum());
			effectPicture.setPayOrder(1);
		}
		if(waitStatusList.contains(tradeDTO.getStatus()) && !true == tradeDTO.isRefundFlag()){
			paddingMap(effectPicture, tradeDTO, orderSource, diffDay2,effectParamVo.getWaitPayBuyerMap());
//			effectPicture.setWaitPayBuyer(1);
			effectPicture.setWaitPayFee(tradeDTO.getPayment());
			effectPicture.setWaitPayItem(tradeDTO.getNum());
			effectPicture.setWaitPayOrder(1);
		}
		if(true == tradeDTO.isRefundFlag()){
//			effectPicture.setRefundBuyer(1);
			paddingMap(effectPicture, tradeDTO, orderSource, diffDay2,effectParamVo.getRefundBuyerMap());
			effectPicture.setRefundFee(tradeDTO.getPayment());
			effectPicture.setRefundItem(tradeDTO.getNum());
			effectPicture.setRefundOrder(1);
		}
		map.put(effectPicture.getMsgId()+"=="+orderSource+"=="+diffDay2, effectPicture);
	}

	private void paddingMap(EffectPicture effectPicture, TradeDTO tradeDTO,
			String orderSource, Long diffDay2,Map<String,Set<String>> paramMap) {
		if(null!=paramMap&&paramMap.size()>0){
			Set<String> set = paramMap.get(effectPicture.getMsgId()+"=="+orderSource+"=="+diffDay2);
			if(null!=set&&set.size()>0){
				set.add(tradeDTO.getBuyerNick());
			}else{
				Set<String>buyerNickSet = new TreeSet<String>();
				buyerNickSet.add(tradeDTO.getBuyerNick());
				paramMap.put(effectPicture.getMsgId()+"=="+orderSource+"=="+diffDay2,buyerNickSet);
			}
		}else{
			Set<String>buyerNickSet = new TreeSet<String>();
			buyerNickSet.add(tradeDTO.getBuyerNick());
			paramMap.put(effectPicture.getMsgId()+"=="+orderSource+"=="+diffDay2,buyerNickSet);
		}
	}
	
	
	public List<String>  putRecordIntoCache(MsgSendRecord msgRecord){
		return findSmsRecordListByMsgId(msgRecord);
	}
	
	public List<String> findSmsRecordListByMsgId(MsgSendRecord msgRecord) {
		List<String> phoneList = new ArrayList<String>();
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(msgRecord.getUserId()));
		query.addCriteria(Criteria.where("type").is(msgRecord.getType()));
		query.addCriteria(Criteria.where("status").is(2));
		query.addCriteria(Criteria.where("msgId").is(msgRecord.getId()));
	    long dataCount = smsRecordRepository.count(query, msgRecord.getUserId());
	    if(dataCount<10000){
	    	phoneList = smsRecordService.findNumList(query, msgRecord.getUserId());
	    }else{
	    	List<SmsRecordDTO> recordList = smsRecordService.findMigrateRecordDataList(query ,msgRecord.getUserId(), "0", 1);
	    	phoneList.add(recordList.get(0).getRecNum());
	    	dataCount = dataCount-1;
	    	cacheService.putNoTime(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_RECORD_DATA_KEY,recordList.get(0).get_id());
	 	    Long pageSize = ConstantUtils.PROCESS_PAGE_SIZE_MAX;
	    	while (true) {
    		    Query query1 = new Query();
    		    query1.addCriteria(Criteria.where("userId").is(msgRecord.getUserId()));
    			query1.addCriteria(Criteria.where("type").is(msgRecord.getType()));
    			query1.addCriteria(Criteria.where("status").is(2));
    			query1.addCriteria(Criteria.where("msgId").is(msgRecord.getId()));  
			   String id = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_RECORD_DATA_KEY);
			   if(dataCount<ConstantUtils.PROCESS_PAGE_SIZE_MAX){
				   pageSize = dataCount;
			   }
			   List<SmsRecordDTO> resultRecordList = smsRecordService.findMigrateRecordDataList(query1 ,msgRecord.getUserId(),id,pageSize.intValue());
			   dataCount  = dataCount -ConstantUtils.PROCESS_PAGE_SIZE_MAX;
			   SmsRecordDTO lastSmsRecordDTO  = resultRecordList.get(resultRecordList.size()-1);
			   cacheService.putNoTime(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_RECORD_DATA_KEY,lastSmsRecordDTO.get_id());
			  
			   if(null!=resultRecordList&&resultRecordList.size()>0){
				   List<String>  numList = new ArrayList<String>();
				   for (SmsRecordDTO smsRecordDTO : resultRecordList) {
					   numList.add(smsRecordDTO.getRecNum());
				   }
				   phoneList.addAll(numList);
			   }
			   if(pageSize<ConstantUtils.PROCESS_PAGE_SIZE_MAX){
				   break;
			   }
	    	}
	    }
		return  phoneList;
	}
	
	/**
	 * 查询查询满足条件的真实客户数
	 * ZTK2017年9月12日下午5:41:22
	 */
	public EffectPicture findRealBuyerNum(Long msgId ,Integer days,String orderSource){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("days", days);
		map.put("msgId", msgId);
		map.put("orderSource", orderSource);
		EffectPicture effectPicture = myBatisDao.findBy(EffectPicture.class.getName(), "findRealBuyerNum", map);
		return effectPicture;
	}
	
	
	
	
	

	/**
	 * 添加不是第一次的数据
	 * ZTK2017年9月7日下午6:37:45
	 * @throws Exception 
	 */
	public void packageTotalEffectPicture(EffectPicture effectPicture,Map<String,EffectPicture> map,TradeDTO tradeDTO,
			String orderSource,MsgSendRecord msgRecord) throws Exception{
		effectPicture.setTotalFee(NumberUtils.getResult(effectPicture.getTotalFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
		effectPicture.setTotalItem(NumberUtils.getResult(effectPicture.getTotalItem()) + NumberUtils.getResult(tradeDTO.getNum()));
		effectPicture.setTotalOrder(NumberUtils.getResult(effectPicture.getTotalOrder()) + 1);
		//付款数据
		List<String> payStatusList = new ArrayList<String>();
		payStatusList.add(TradeStatusUtils.TRADE_FINISHED);
		payStatusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
		payStatusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
		payStatusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
		payStatusList.add(TradeStatusUtils.TRADE_CLOSED);
		//未付款数据
		List<String> waitStatusList = new ArrayList<String>();
		waitStatusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
		waitStatusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
		if(payStatusList.contains(tradeDTO.getStatus())){
			effectPicture.setPayFee(NumberUtils.getResult(effectPicture.getPayFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
			effectPicture.setPayItem(NumberUtils.getResult(effectPicture.getPayItem()) + NumberUtils.getResult(tradeDTO.getNum()));
			effectPicture.setPayOrder(NumberUtils.getResult(effectPicture.getPayOrder()) + 1);
		}
		if(waitStatusList.contains(tradeDTO.getStatus()) && !true == tradeDTO.isRefundFlag()){
			effectPicture.setWaitPayFee(NumberUtils.getResult(effectPicture.getWaitPayFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
			effectPicture.setWaitPayItem(NumberUtils.getResult(effectPicture.getWaitPayItem()) + NumberUtils.getResult(tradeDTO.getNum()));
			effectPicture.setWaitPayOrder(NumberUtils.getResult(effectPicture.getWaitPayOrder()) + 1);
		}
		if(true == tradeDTO.isRefundFlag()){
			effectPicture.setRefundFee(NumberUtils.getResult(effectPicture.getRefundFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
			effectPicture.setRefundItem(NumberUtils.getResult(effectPicture.getRefundItem()) + NumberUtils.getResult(tradeDTO.getNum()));
			effectPicture.setRefundOrder(NumberUtils.getResult(effectPicture.getRefundOrder()) + 1);
		}
	}
	
	/**
	 * 添加第一次的数据
	 * ZTK2017年9月7日下午7:03:03
	 * @throws Exception 
	 */
	public void packageTotalFirstEffectPicture(EffectPicture effectPicture,Map<String,EffectPicture> map,TradeDTO tradeDTO,
			String orderSource,MsgSendRecord msgRecord) throws Exception{
		effectPicture.setTotalFee(tradeDTO.getPayment());
		effectPicture.setTotalItem(tradeDTO.getNum());
		effectPicture.setTotalOrder(1);
		//付款数据
		List<String> payStatusList = new ArrayList<String>();
		payStatusList.add(TradeStatusUtils.TRADE_FINISHED);
		payStatusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
		payStatusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
		payStatusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
		payStatusList.add(TradeStatusUtils.TRADE_CLOSED);
		//未付款数据
		List<String> waitStatusList = new ArrayList<String>();
		waitStatusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
		waitStatusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
		if(payStatusList.contains(tradeDTO.getStatus())){
			effectPicture.setPayFee(tradeDTO.getPayment());
			effectPicture.setPayItem(tradeDTO.getNum());
			effectPicture.setPayOrder(1);
		}
		if(waitStatusList.contains(tradeDTO.getStatus()) && !true == tradeDTO.isRefundFlag()){
			effectPicture.setWaitPayFee(tradeDTO.getPayment());
			effectPicture.setWaitPayItem(tradeDTO.getNum());
			effectPicture.setWaitPayOrder(1);
		}
		if(true == tradeDTO.isRefundFlag()){
			effectPicture.setRefundFee(tradeDTO.getPayment());
			effectPicture.setRefundItem(tradeDTO.getNum());
			effectPicture.setRefundOrder(1);
		}
		map.put(effectPicture.getMsgId()+"=="+effectPicture.getDays(), effectPicture);
	}

	
	/**
	 * 查询三小时交易的订单，同步效果分析表(十天)
	 * ztk2017年11月14日下午4:26:09
	 */
	public void findAllMsgIdOfTen(Date dayEndTime){
		String msgIdStr = redisLockServiceImpl.getValue("lastSychMsgId",String.class);
		Long msgIdLong = 1L;
//		if(msgIdStr != null && !"".equals(msgIdStr)){
//			msgIdLong = Long.parseLong(msgIdStr);
//		}
		List<Long> msgIds = msgSendRecordService.findMsgIdByTime(null,DateUtils.nDaysAgo(10, dayEndTime), dayEndTime,msgIdLong);
		if(msgIds != null && !msgIds.isEmpty()){
			for (int i = 0; i < msgIds.size(); i++) {
				logger.info("第" + i + "个msgId是:" + msgIds.get(i));
			}
			ExecutorService myThreadPool = MyFixedThreadPool.getMyFixedThreadPool();
			if(msgIds.size() <= 50){
				EffectPictureManageThread manageThread = new EffectPictureManageThread(msgIds, dayEndTime, smsRecordService, msgSendRecordService, tradeInfoService, myBatisDao,myThreadPool);
				myThreadPool.execute(manageThread);
			}else {
				int simpleSize = msgIds.size() / 4;
				myThreadPool.execute(new EffectPictureManageThread(msgIds.subList(0, simpleSize), dayEndTime, smsRecordService, msgSendRecordService, tradeInfoService, myBatisDao,myThreadPool));
				myThreadPool.execute(new EffectPictureManageThread(msgIds.subList(simpleSize, simpleSize * 2), dayEndTime, smsRecordService, msgSendRecordService, tradeInfoService, myBatisDao,myThreadPool));
				myThreadPool.execute(new EffectPictureManageThread(msgIds.subList(simpleSize * 2, simpleSize * 3), dayEndTime, smsRecordService, msgSendRecordService, tradeInfoService, myBatisDao,myThreadPool));
				myThreadPool.execute(new EffectPictureManageThread(msgIds.subList(simpleSize * 3, simpleSize * 4), dayEndTime, smsRecordService, msgSendRecordService, tradeInfoService, myBatisDao,myThreadPool));
				if(simpleSize * 4 <=  msgIds.size()){
					myThreadPool.execute(new EffectPictureManageThread(msgIds.subList(simpleSize * 4, msgIds.size()), dayEndTime, smsRecordService, msgSendRecordService, tradeInfoService, myBatisDao,myThreadPool));
				}
			}
//			for (int i = 0; i < msgIds.size(); i++) {
//				List<Long> msgIdList = new ArrayList<Long>();
//				msgIdList.add(msgIds.get(i));
//				logger.info("第" + (i + 1) + "个线程");
//				myThreadPool.execute(new EffectPictureManageThread(msgIdList, dayEndTime, smsRecordService, msgSendRecordService, tradeInfoService, myBatisDao));
//			}
		}
		return;
	}

	/**
	 * 计算今日营销金额
	 * ztk2017年11月28日上午10:45:44
	 */
	public double findTodayMemberMoney(String userId,Date startTime,Date endTime) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("orderSource", "TOTAL");
		map.put("userId", userId);
		EffectPicture effectPicture = myBatisDao.findBy(EffectPicture.class.getName(), "findTodayMemberMoney", map);
		if(effectPicture != null){
			return NumberUtils.getResult(effectPicture.getPayFee());
		}
		return 0;
	}
	
	
}
