package s2jh.biz.shop.crm.tradecenter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lab.s2jh.core.util.DateUtils;
import lab.s2jh.core.util.NumberUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.dao.SmsRecordRepository;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.order.pojo.OrderReminderEffectVo;
import s2jh.biz.shop.crm.order.pojo.ReminderNum;
import s2jh.biz.shop.crm.tradecenter.dao.TradeCenterEffectDao;
import s2jh.biz.shop.crm.tradecenter.entity.TradeCenterEffect;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.view.service.ShortLinkService;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.TradeStatusUtils;

import com.alibaba.fastjson.JSONObject;

@Service
public class TradeCenterEffectService {

	private Logger logger = LoggerFactory.getLogger(TradeCenterEffectService.class);
	
	@Autowired
	private TradeCenterEffectDao tradeCenterEffectDao;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private SmsRecordRepository smsRecordRepository;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Autowired
	private TradeSetupService tradeSetupService;
	
	@Autowired
	private ShortLinkService shortLinkService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 新增一条记录
	 * ztk2017年11月24日下午2:06:02
	 */
	public void saveTradeCenterEffect(TradeCenterEffect tradeCenterEffect){
		tradeCenterEffectDao.saveTradeCenterEffect(tradeCenterEffect);
	}
	
	/**
	 * 更新一条记录
	 * ztk2017年11月24日下午1:58:22
	 */
	public void updateTradeCenterEffect(TradeCenterEffect tradeCenterEffect){
		tradeCenterEffectDao.updateTradeCenterEffect(tradeCenterEffect);
	}
	
	/**
	 * 根据条件查询一条记录
	 * ztk2017年11月24日下午1:59:02
	 */
	public TradeCenterEffect queryTradeEffect(TradeCenterEffect tradeCenterEffect){
		TradeCenterEffect tradeEffect = tradeCenterEffectDao.queryTradeEffect(tradeCenterEffect);
		return tradeEffect;
	}
	
	/**
	 * 查询多条记录
	 * ztk2017年11月24日下午2:00:45
	 */
	public List<TradeCenterEffect> queryTradeEffectList(TradeCenterEffect tradeCenterEffect){
		List<TradeCenterEffect> tradeEffectList = tradeCenterEffectDao.queryTradeEffectList(tradeCenterEffect);
		return tradeEffectList;
	}
	
	/**
	 * 订单中心效果分析聚合查询(按照日期正序排列)
	 * ztk2017年12月1日下午4:43:12
	 */
	public List<TradeCenterEffect> aggregateTradeCenterList(OrderReminderEffectVo orderReminderEffectVo){
		List<TradeCenterEffect> tradeCenterEffectList = this.tradeCenterEffectDao.aggregateTradeCenterList(orderReminderEffectVo);
		return tradeCenterEffectList;
	}
	
	/**
	 * 首页催付金额聚合查询
	 * ztk2017年12月6日下午12:15:16
	 */
	public TradeCenterEffect aggregateEarningFee(String userId,String type,Date startTime,Date endTime){
		OrderReminderEffectVo queryEffectVo = new OrderReminderEffectVo();
		queryEffectVo.setUserId(userId);
		queryEffectVo.setType(MsgType.MSG_CGCF);
		queryEffectVo.setStartEffectTime(startTime);
		queryEffectVo.setEndEffectTime(endTime);
		TradeCenterEffect tradeCenterEffect = this.tradeCenterEffectDao.aggregateEarningFee(queryEffectVo);
		return tradeCenterEffect;
	}
	
	/**
	 * 每天定时3小时跑定时，保存订单中心效果分析数据到mysql
	 * ztk2017年11月24日下午5:36:39
	 */
	public void tradeCenterEffectJob(Date synchTime,Integer h,Integer m){
		Date startTime = DateUtils.addHour(synchTime, -2);
		if(h != null){
			startTime = DateUtils.addHour(synchTime, -h);
		}
		if(m != null){
			startTime = DateUtils.addMinute(synchTime, -m);
		}
		Date endTime = synchTime;
		List<String> userIdList = userInfoService.findUserAccessNotNull();
//		List<String> userIdList = new ArrayList<String>();
//		userIdList.add("哈数据库等哈");
		for (int i = 0; i < userIdList.size(); i++) {
			String userId = userIdList.get(i);
			//催付，回款等都是3天未付款就自动取消
			for (int j = 0; j < 3; j++) {
				Date nDaysAgo = DateUtils.nDaysAgo(j, startTime);
				Date effectTimeStart = DateUtils.getStartTimeOfDay(nDaysAgo);
				Date effectTimeEnd = DateUtils.getEndTimeOfDay(nDaysAgo);
				List<Long> taskIdList = tradeSetupService.queryTaskByUserNick(userId,true);
				if(taskIdList != null && !taskIdList.isEmpty()){
					for (Long taskId : taskIdList) {
						Criteria criteria = new Criteria();
						criteria.and("taskId").is(taskId);
						if(j == 0){
							criteria.and("sendLongTime").gte(effectTimeStart.getTime()).lte(endTime.getTime());
						}else {
							criteria.and("sendLongTime").gte(effectTimeStart.getTime()).lte(effectTimeEnd.getTime());
						}
						List<SmsRecordDTO> recordDTOList = smsRecordRepository.find(new Query(criteria), userId);
						List<String> tidList = new ArrayList<String>();//催付订单数
						int smsNum = 0;//短信消费条数
						int linkNum = 0;//短信内连接数
						int customerClick = 0;//客户点击量
						int pageClick = 0;//页面点击量
						if(null == recordDTOList || recordDTOList.isEmpty()){
							continue;
						}else {
							logger.info("~~~~taskId是："+taskId+"第："+j+"天之前的recordDTOList的个数："+recordDTOList.size());
							//点击量(暂时不上)
							JSONObject allEffect = shortLinkService.getAllEffect(userId, recordDTOList.get(0).getType(), taskId, effectTimeStart, effectTimeEnd);
							//统计0,1,2天前发送订单中心短信的订单数
							for (int k = 0; k < recordDTOList.size(); k++) {
								SmsRecordDTO smsRecordDTO = recordDTOList.get(k);
								if(smsRecordDTO != null && smsRecordDTO.getOrderId() != null){
									tidList.add(smsRecordDTO.getOrderId());
									smsNum += NumberUtils.getResult(smsRecordDTO.getActualDeduction());
								}
							}
							//根据计算所有类型的回款数据，保存到tradeCenterEffect
							TradeCenterEffect tradeCenterEffect = sumTradeCenterEffect(userId, recordDTOList.get(0).getType(), tidList, null, null);
							if(tradeCenterEffect != null){
								logger.info("~~~~taskId是："+taskId+"第："+j+"天之前根据计算所有类型的回款数据不为null");
								tradeCenterEffect.setUserId(userId);
								tradeCenterEffect.setTaskId(taskId);
								tradeCenterEffect.setEffectTime(effectTimeStart);
								tradeCenterEffect.setSmsNum(smsNum);
								tradeCenterEffect.setSmsMoney(NumberUtils.getTwoDouble(smsNum * 0.05));
								//点击量(暂时不上)
								linkNum = allEffect.getInteger("total");
								customerClick = allEffect.getInteger("customerClickNum");
								pageClick = allEffect.getInteger("pageClickNum");
								tradeCenterEffect.setCustomerClick(customerClick);
								tradeCenterEffect.setLinkNum(linkNum);
								tradeCenterEffect.setPageClick(pageClick);
								tradeCenterEffect.setCreatedBy(userId);
								tradeCenterEffect.setCreatedDate(new Date());
								tradeCenterEffect.setLastModifiedBy(userId);
								tradeCenterEffect.setLastModifiedDate(new Date());
								logger.info("~~~~taskId是："+taskId+"第："+j+"天之前的催付订单数：" + tradeCenterEffect.getTargetOrder());
								logger.info("~~~~taskId是："+taskId+"第："+j+"天之前的回款订单数：" + tradeCenterEffect.getEarningFee());
								TradeCenterEffect tradeEffect = this.queryTradeEffect(tradeCenterEffect);
								if(tradeEffect != null){
									this.updateTradeCenterEffect(tradeCenterEffect);
								}else {
									this.saveTradeCenterEffect(tradeCenterEffect);
								}
							}else {
								logger.info("~~~~taskId是："+taskId+"第："+j+"天之前根据计算所有类型的回款数据为null");
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 计算所有类型的回款数据
	 * ztk2017年11月30日上午10:50:57
	 */
	public TradeCenterEffect sumTradeCenterEffect(String userId,String type,List<String> tidList,
			Date startTime,Date endTime){
		TradeCenterEffect tradeCenterEffect = new TradeCenterEffect();
		List<String> statusList = new ArrayList<String>();
		int targetOrder = 0,earningOrder = 0;
		double targetFee = 0.00,earningFee = 0.00;
		if(MsgType.MSG_JHSCF.equals(type) || MsgType.MSG_ECCF.equals(type)){//聚划算
			targetOrder = tidList.size();
			ReminderNum targetNum = tradeInfoService.sumReminderNum(userId, tidList, null,startTime,endTime);
			if(targetNum != null){
				targetFee = NumberUtils.getResult(targetNum.getTargetMoney());
			}
			statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
			statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
			statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
			statusList.add(TradeStatusUtils.TRADE_FINISHED);
			statusList.add(TradeStatusUtils.TRADE_CLOSED);
			ReminderNum successNum = tradeInfoService.sumReminderNum(userId, tidList, statusList,startTime,endTime);
			if(successNum != null){
				earningFee = NumberUtils.getResult(successNum.getTargetMoney());
				earningOrder = NumberUtils.getResult(successNum.getTargetOrder());
			}
		}else if(MsgType.MSG_CGCF.equals(type)){//常规或二次
			List<String> ecTidList = new ArrayList<String>();//二次催付的结果tidList
			ReminderNum erCiReminderNum = smsRecordService.findRecordTidAndSmsNum(MsgType.MSG_ECCF, userId, startTime, endTime,null);
			if(erCiReminderNum != null){
				ecTidList = erCiReminderNum.getTidList();//二次催付过的订单
			}
			targetOrder = tidList.size();
			ReminderNum targetNum = tradeInfoService.sumReminderNum(userId, tidList,null,startTime,endTime);
			if(targetNum != null){
				targetFee = NumberUtils.getResult(targetNum.getTargetMoney());
			}
			if(tidList != null){
				tidList.removeAll(ecTidList);
			}
			statusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
			statusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
			statusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
			statusList.add(TradeStatusUtils.TRADE_FINISHED);
			statusList.add(TradeStatusUtils.TRADE_CLOSED);
			ReminderNum successNum = tradeInfoService.sumReminderNum(userId, tidList,statusList,startTime,endTime);
			if(successNum != null){
				earningFee = NumberUtils.getResult(successNum.getTargetMoney());
				earningOrder = NumberUtils.getResult(successNum.getTargetOrder());
			}
		}else if(MsgType.MSG_HKTX.equals(type)){//回款提醒
			targetOrder = tidList.size();
			ReminderNum targetNum = tradeInfoService.sumReminderNum(userId, tidList,null,startTime,endTime);
			if(targetNum != null){
				targetFee = NumberUtils.getResult(targetNum.getTargetMoney());
			}
			statusList.add(TradeStatusUtils.TRADE_FINISHED);
			statusList.add(TradeStatusUtils.TRADE_CLOSED);
			ReminderNum successNum = tradeInfoService.sumRefundNum(userId, tidList, statusList, startTime,endTime);
			if(successNum != null){
				earningFee = NumberUtils.getResult(successNum.getTargetMoney());
				earningOrder = NumberUtils.getResult(successNum.getTargetOrder());
			}
		}else if(MsgType.MSG_HPTX.equals(type)){//好评提醒
			targetOrder = tidList.size();
		}else if(MsgType.MSG_FHTX.equals(type) || MsgType.MSG_DDTCTX.equals(type) || MsgType.MSG_PJTX.equals(type) || 
				MsgType.MSG_QSTX.equals(type) || MsgType.MSG_BBGH.equals(type)){//物流提醒和宝贝关怀
			targetOrder = tidList.size();
		}
		tradeCenterEffect.setTargetFee(targetFee);
		tradeCenterEffect.setTargetOrder(targetOrder);
		tradeCenterEffect.setEarningFee(earningFee);
		tradeCenterEffect.setEarningOrder(earningOrder);
		tradeCenterEffect.setType(type);
		return tradeCenterEffect;
	}
	
	/**
	 * 更新或者插入记录
	 * ztk2017年12月1日上午11:57:21
	 */
	public void insertOrUpdateTradeCenterEffect(TradeCenterEffect tradeCenterEffect){
		try {
			TradeCenterEffect queryEffect = new TradeCenterEffect();
			queryEffect.setTaskId(tradeCenterEffect.getTaskId());
			queryEffect.setUserId(tradeCenterEffect.getUserId());
			queryEffect.setEffectTime(tradeCenterEffect.getEffectTime());
			TradeCenterEffect tradeEffect = this.queryTradeEffect(queryEffect);
			if(tradeEffect != null){
				tradeEffect.setCustomerClick(NumberUtils.getResult(tradeEffect.getCustomerClick()) + NumberUtils.getResult(tradeCenterEffect.getCustomerClick()));
				tradeEffect.setPageClick(NumberUtils.getResult(tradeEffect.getPageClick()) + NumberUtils.getResult(tradeCenterEffect.getPageClick()));
				tradeEffect.setLinkNum(NumberUtils.getResult(tradeEffect.getLinkNum()) + NumberUtils.getResult(tradeCenterEffect.getLinkNum()));
				tradeEffect.setTargetFee(NumberUtils.getResult(tradeEffect.getTargetFee()) + NumberUtils.getResult(tradeCenterEffect.getTargetFee()));
				tradeEffect.setTargetOrder(NumberUtils.getResult(tradeEffect.getTargetOrder()) + NumberUtils.getResult(tradeCenterEffect.getTargetOrder()));
				tradeEffect.setEarningFee(NumberUtils.getResult(tradeEffect.getEarningFee()) + NumberUtils.getResult(tradeCenterEffect.getEarningFee()));
				tradeEffect.setEarningOrder(NumberUtils.getResult(tradeEffect.getEarningOrder()) + NumberUtils.getResult(tradeCenterEffect.getEarningOrder()));
				tradeEffect.setSmsNum(NumberUtils.getResult(tradeEffect.getSmsNum()) + NumberUtils.getResult(tradeCenterEffect.getSmsNum()));
				tradeEffect.setSmsMoney(NumberUtils.getResult(tradeEffect.getSmsMoney()) + NumberUtils.getResult(tradeCenterEffect.getSmsMoney()));
				tradeEffect.setEffectTime(tradeCenterEffect.getEffectTime());
				tradeEffect.setLastModifiedDate(new Date());
				this.updateTradeCenterEffect(tradeEffect);
			}else {
				this.saveTradeCenterEffect(tradeCenterEffect);
			}
		} catch (Exception e) {
			logger.info("insertOrUpdateTradeCenterEffect保存失败---->:"+ tradeCenterEffect);
			e.printStackTrace();
		}
	}
}
