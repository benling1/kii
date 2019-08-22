package s2jh.biz.shop.crm.order.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.util.DateUtils;
import lab.s2jh.core.util.NumberUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.order.entity.EffectPicture;
import s2jh.biz.shop.crm.order.pojo.EffectPictureReal;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.TradeStatusUtils;

public class EffectPictureManageThread implements Runnable {

	private SmsRecordService smsRecordService;
	
	private MsgSendRecordService msgSendRecordService;
	
	private TradeInfoService tradeInfoService;
	
//	private EffectPictureService effectPictureService;
	
	private MyBatisDao myBatisDao;
	
	private List<Long> msgIds;
	
	private Date dayEndTime;
	
	private ExecutorService executors;
	
	private Logger logger = LoggerFactory.getLogger(EffectPictureManageThread.class);
	
	public EffectPictureManageThread(List<Long> msgIds, Date dayEndTime,SmsRecordService smsRecordService,
			MsgSendRecordService msgSendRecordService,
			TradeInfoService tradeInfoService,
			MyBatisDao myBatisDao,ExecutorService executors) {
		super();
		this.smsRecordService = smsRecordService;
		this.msgSendRecordService = msgSendRecordService;
		this.tradeInfoService = tradeInfoService;
		this.myBatisDao = myBatisDao;
		this.msgIds = msgIds;
		this.dayEndTime = dayEndTime;
		this.executors = executors;
	}

	public void run() {
		if(msgIds != null && !msgIds.isEmpty()){
			logger.info("多线程msgId从:" + msgIds.get(0) + "到" + msgIds.get(msgIds.size() - 1));
			for (int i = 0; i < msgIds.size(); i++) {
				Long msgId = msgIds.get(i);
				List<String> phoneList = new ArrayList<String>();
				MsgSendRecord msgRecord = msgSendRecordService.findOne(msgId);
				if(msgRecord != null){
					if("大哥要啥".equals(msgRecord.getUserId()) || "滔之浪".equals(msgRecord.getUserId())){
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
						if(phoneList != null && !phoneList.isEmpty()){
						}else {
							continue;
						}
						//查询发送日期与今日的相差天数，set到days字段
						Long days = DateUtils.getDiffDay(msgRecord.getSendCreat(), dayEndTime);
						Long diff = dayEndTime.getTime() - msgRecord.getSendCreat().getTime();
					    long nh = 1000 * 60 * 60;
						Long hour = diff / nh;
						if(9 < days){
							continue;
						}
						//计算分析数据的某天的开始时间
						Date startTime = null;
						if(hour < 3){
							startTime = msgRecord.getSendCreat();
						}else {
							startTime = DateUtils.addHour(dayEndTime,-3);
						}
						Date tenAgoTime = msgRecord.getSendCreat();
						List<TradeDTO> todayTradeList = todayEffectPictureNew(msgRecord,startTime, dayEndTime, phoneList,null);
						List<TradeDTO> tenTradeList = todayEffectPictureNew(msgRecord,tenAgoTime, dayEndTime, phoneList,null);
						Map<String, EffectPicture> effectMap = appendPicture(todayTradeList, msgRecord, dayEndTime);
//						Future<EffectPictureReal> effectPictureRealFuture = executors.submit(new EffectBuyerRealCountThread(tenTradeList));
//						EffectPictureReal effectPictureReal = new EffectPictureReal();
//						if(effectPictureRealFuture.isDone()){
//							effectPictureReal = effectPictureRealFuture.get();
//						}
						EffectPictureReal effectPictureReal = effectPictureRealSum(tenTradeList);
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
					logger.info("msgId:" + msgId + "执行完毕");
				}
				
				
				
			}
		}
		
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
		effectPicture_TAOBAO.setEffectTime(endTime);
		effectPicture_WAP.setEffectTime(endTime);
		effectPicture_TOTAL.setEffectTime(endTime);
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
			if(tradeDTO != null){
				totalBuyerSet_TOTAL.add(tradeDTO.getBuyerNick());
				effectPicture_TOTAL.setTotalFee(NumberUtils.getResult(effectPicture_TOTAL.getTotalFee()) + NumberUtils.getResult(tradeDTO.getPayment()));
				effectPicture_TOTAL.setTotalItem(NumberUtils.getResult(effectPicture_TOTAL.getTotalItem()) + NumberUtils.getResult(tradeDTO.getNum()));
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
	 * 效果分析表的添加操作
	 * ZTK2017年8月31日下午2:04:27
	 */
	public boolean insertEffectPicture(EffectPicture effectPicture) throws Exception{
			if(effectPicture != null){
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("msgId", effectPicture.getMsgId());
				paramMap.put("days", effectPicture.getDays());
				paramMap.put("ordersource", effectPicture.getOrderSource());
				EffectPicture existEffectPicture = myBatisDao.findBy(EffectPicture.class.getName(), "findEffectByParam", paramMap);
				if(null!=existEffectPicture){
					
					Map<String,Object> dataMap = new HashMap<String, Object>();
//					createEffectPictureObj(effectPicture, existEffectPicture);
					dataMap.put("effectTime", effectPicture.getEffectTime());
					dataMap.put("totalBuyer", NumberUtils.getResult(existEffectPicture.getTotalBuyer()) + NumberUtils.getResult(effectPicture.getTotalBuyer()));
					dataMap.put("totalOrder", NumberUtils.getResult(existEffectPicture.getTotalOrder()) + NumberUtils.getResult(effectPicture.getTotalOrder()));
					dataMap.put("totalItem", NumberUtils.getResult(existEffectPicture.getTotalItem()) + NumberUtils.getResult(effectPicture.getTotalItem()));
					dataMap.put("payBuyer", NumberUtils.getResult(existEffectPicture.getPayBuyer()) + NumberUtils.getResult(effectPicture.getPayBuyer()));
					dataMap.put("payOrder", NumberUtils.getResult(existEffectPicture.getPayOrder()) + NumberUtils.getResult(effectPicture.getPayOrder()));
					dataMap.put("payItem", NumberUtils.getResult(existEffectPicture.getPayItem()) + NumberUtils.getResult(effectPicture.getPayItem()));
					dataMap.put("waitPayBuyer", NumberUtils.getResult(existEffectPicture.getWaitPayBuyer()) + NumberUtils.getResult(effectPicture.getWaitPayBuyer()));
					dataMap.put("waitPayOrder", NumberUtils.getResult(existEffectPicture.getWaitPayOrder()) + NumberUtils.getResult(effectPicture.getWaitPayOrder()));
					dataMap.put("waitPayItem", NumberUtils.getResult(existEffectPicture.getWaitPayItem()) + NumberUtils.getResult(effectPicture.getWaitPayItem()));
					dataMap.put("refundBuyer", NumberUtils.getResult(existEffectPicture.getRefundBuyer()) + NumberUtils.getResult(effectPicture.getRefundBuyer()));
					dataMap.put("refundOrder", NumberUtils.getResult(existEffectPicture.getRefundOrder()) + NumberUtils.getResult(effectPicture.getRefundOrder()));
					dataMap.put("refundItem", NumberUtils.getResult(existEffectPicture.getRefundItem()) + NumberUtils.getResult(effectPicture.getRefundItem()));
					
					dataMap.put("totalFee", NumberUtils.getResult(existEffectPicture.getTotalFee()) + NumberUtils.getResult(effectPicture.getTotalFee()));
					dataMap.put("payFee", NumberUtils.getResult(existEffectPicture.getPayFee()) + NumberUtils.getResult(effectPicture.getPayFee()));
					dataMap.put("waitPayFee", NumberUtils.getResult(existEffectPicture.getWaitPayFee()) + NumberUtils.getResult(effectPicture.getWaitPayFee()));
					dataMap.put("refundFee", NumberUtils.getResult(existEffectPicture.getRefundFee()) + NumberUtils.getResult(effectPicture.getRefundFee()));
					
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
					dataMap.put("totalFee", NumberUtils.getResult(effectPicture.getTotalFee()));
					dataMap.put("totalBuyer", NumberUtils.getResult(effectPicture.getTotalBuyer()));
					dataMap.put("totalOrder", NumberUtils.getResult(effectPicture.getTotalOrder()));
					dataMap.put("totalItem", NumberUtils.getResult(effectPicture.getTotalItem()));
					dataMap.put("payFee", NumberUtils.getResult(effectPicture.getPayFee()));
					dataMap.put("payBuyer", NumberUtils.getResult(effectPicture.getPayBuyer()));
					dataMap.put("payOrder", NumberUtils.getResult(effectPicture.getPayOrder()));
					dataMap.put("payItem", NumberUtils.getResult(effectPicture.getPayItem()));
					dataMap.put("waitPayFee", NumberUtils.getResult(effectPicture.getWaitPayFee()));
					dataMap.put("waitPayBuyer", NumberUtils.getResult(effectPicture.getWaitPayBuyer()));
					dataMap.put("waitPayOrder", NumberUtils.getResult(effectPicture.getWaitPayOrder()));
					
					dataMap.put("waitPayItem", NumberUtils.getResult(effectPicture.getWaitPayItem()));
					dataMap.put("refundFee", NumberUtils.getResult(effectPicture.getRefundFee()));
					dataMap.put("refundBuyer", NumberUtils.getResult(effectPicture.getRefundBuyer()));
					dataMap.put("refundOrder", NumberUtils.getResult(effectPicture.getRefundOrder()));
					dataMap.put("refundItem", NumberUtils.getResult(effectPicture.getRefundItem()));
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
		return false;
	}
	
	/**
	 * 计算真实数据
	 * ztk2017年11月27日上午10:30:07
	 */
	public EffectPictureReal effectPictureRealSum(List<TradeDTO> tradeList){
		EffectPictureReal effectPictureReal = new EffectPictureReal();
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
		Set<String> totalBuyerSet_TOTAL = new HashSet<String>(),payBuyerSet_TOTAL = new HashSet<String>(),waitBuyerSet_TOTAL = new HashSet<String>(),refundBuyerSet_TOTAL = new HashSet<String>();
		Set<String> totalBuyerSet_TAOBAO = new HashSet<String>(),payBuyerSet_TAOBAO = new HashSet<String>(),waitBuyerSet_TAOBAO = new HashSet<String>(),refundBuyerSet_TAOBAO = new HashSet<String>();
		Set<String> totalBuyerSet_WAP = new HashSet<String>(),payBuyerSet_WAP = new HashSet<String>(),waitBuyerSet_WAP = new HashSet<String>(),refundBuyerSet_WAP = new HashSet<String>();
		for (int i = 0; i < tradeList.size(); i++) {
			TradeDTO trade = tradeList.get(i);
			if(trade != null){
				totalBuyerSet_TOTAL.add(trade.getBuyerNick());
				if(payStatusList.contains(trade.getStatus())){
					payBuyerSet_TOTAL.add(trade.getBuyerNick());
				}
				if(waitStatusList.contains(trade.getStatus()) && false == trade.isRefundFlag()){
					waitBuyerSet_TOTAL.add(trade.getBuyerNick());
				}
				if(true == trade.isRefundFlag()){
					refundBuyerSet_TOTAL.add(trade.getBuyerNick());
				}
				if(trade.getTradeFrom().contains("TAOBAO")){
					totalBuyerSet_TAOBAO.add(trade.getBuyerNick());
					if(payStatusList.contains(trade.getStatus())){
						payBuyerSet_TAOBAO.add(trade.getBuyerNick());
					}
					if(waitStatusList.contains(trade.getStatus()) && false == trade.isRefundFlag()){
						waitBuyerSet_TAOBAO.add(trade.getBuyerNick());
					}
					if(true == trade.isRefundFlag()){
						refundBuyerSet_TAOBAO.add(trade.getBuyerNick());
					}
				}
				if(trade.getTradeFrom().contains("WAP")){
					totalBuyerSet_WAP.add(trade.getBuyerNick());
					if(payStatusList.contains(trade.getStatus())){
						payBuyerSet_WAP.add(trade.getBuyerNick());
					}
					if(waitStatusList.contains(trade.getStatus()) && false == trade.isRefundFlag()){
						waitBuyerSet_WAP.add(trade.getBuyerNick());
					}
					if(true == trade.isRefundFlag()){
						refundBuyerSet_WAP.add(trade.getBuyerNick());
					}
				}
			}
		}
		effectPictureReal.setTotalBuyerReal_TOTAL(totalBuyerSet_TOTAL.size());
		effectPictureReal.setPayBuyerReal_TOTAL(payBuyerSet_TOTAL.size());
		effectPictureReal.setWaitPayBuyerReal_TOTAL(waitBuyerSet_TOTAL.size());
		effectPictureReal.setRefundBuyerReal_TOTAL(refundBuyerSet_TOTAL.size());
		effectPictureReal.setTotalBuyerReal_TAOBAO(totalBuyerSet_TAOBAO.size());
		effectPictureReal.setPayBuyerReal_TAOBAO(payBuyerSet_TAOBAO.size());
		effectPictureReal.setWaitPayBuyerReal_TAOBAO(waitBuyerSet_TAOBAO.size());
		effectPictureReal.setRefundBuyerReal_TAOBAO(refundBuyerSet_TAOBAO.size());
		effectPictureReal.setTotalBuyerReal_WAP(totalBuyerSet_WAP.size());
		effectPictureReal.setPayBuyerReal_WAP(payBuyerSet_WAP.size());
		effectPictureReal.setWaitPayBuyerReal_WAP(waitBuyerSet_WAP.size());
		effectPictureReal.setRefundBuyerReal_WAP(refundBuyerSet_WAP.size());
		return effectPictureReal;
	}
}
