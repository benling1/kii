package s2jh.biz.shop.crm.schedule.tradeJob;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.util.DateUtils;
import lab.s2jh.core.util.NumberUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.manage.dao.SmsRecordRepository;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.order.service.EffectPictureService;
import s2jh.biz.shop.crm.tradecenter.entity.TradeCenterEffect;
import s2jh.biz.shop.crm.tradecenter.service.TradeCenterEffectService;
import s2jh.biz.shop.crm.tradecenter.service.TradeSetupService;
import s2jh.biz.shop.crm.user.service.UserInfoService;

@Controller
public class SynchEffectPicture {

	
	private Logger logger = LoggerFactory.getLogger(SynchEffectPicture.class);
	
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private EffectPictureService effectPictureService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SmsRecordRepository smsRecordRepository;
	
	@Autowired
	private TradeSetupService tradeSetupService;
	
	@Autowired
	private TradeCenterEffectService tradeCenterEffectService;
	
	/**
	 * 同步所有的msg记录，保存每日记录到Mqsql中
	 * ZTK2017年9月4日下午2:43:41
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String,Object> synchEffect(String userId,String type,Integer status){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<SmsRecordDTO> recordDTOs = new ArrayList<SmsRecordDTO>();
		try {
			for (int i = 0;i<50000;i++) {
				SmsRecordDTO smsSendRecord = new SmsRecordDTO();
				Date date = new Date();
				smsSendRecord.setContent("测试常规5w");
				smsSendRecord.setSendTime(date);
				smsSendRecord.setReceiverTime(date);
				smsSendRecord.setType(type);
				smsSendRecord.setChannel("");
				smsSendRecord.setActualDeduction(1);
			    smsSendRecord.setResultCode("");
			    smsSendRecord.setOrderId("12345678"+i);
			    smsSendRecord.setMsgId(null);
			    smsSendRecord.setSource("2");
			    smsSendRecord.setStatus(status);
				smsSendRecord.setAutograph("1");
				smsSendRecord.setUserId(userId);
				smsSendRecord.setRecNum("100000000000" + i);
				smsSendRecord.setShow(true);
				smsSendRecord.setSendLongTime(date.getTime());
				recordDTOs.add(smsSendRecord);
			}
			smsRecordService.saveSmsRecord(recordDTOs, userId);
			resultMap.put("msg", "更新完成");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("msg", "更新输错");
		}
		return resultMap;
		}
	
	
	/**
	 * 同步所有的msg记录，保存每日记录到Mqsql中
	 * ZTK2017年9月4日下午2:43:41
	 */
	@RequestMapping("/effect")
	@ResponseBody
	public Map<String,Object> synchEffect(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~进来了");
		try {
//			effectPictureService.findAllMsgIdNew(DateUtils.getEndTimeOfDay(new Date()));
			effectPictureService.findAllMsgIdOfTen(new Date());
			resultMap.put("msg", "更新完成");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("msg", "更新输错");
		}
		return resultMap;
	}
	
	/**
	 * 更新订单中心发送记录中的taskName为taskId
	 * ztk2017年11月2日下午5:10:00
	 */
	@RequestMapping("/updateTaskId")
	@ResponseBody
	public Map<String,Object> updateTaskNameToId(Integer h,Integer m){
		Date synchTime = new Date();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			tradeCenterEffectService.tradeCenterEffectJob(synchTime, h, m);
			resultMap.put("msg", "更新完成");
		} catch (Exception e) {
			resultMap.put("msg", "更新出错");
			e.printStackTrace();
		}
		return resultMap;
//		try {
//			List<String> userList = userInfoService.findAllUserByLoginTime();
//			int i = 0;
//			List<String> types = new ArrayList<String>();
//			types.add("2");types.add("3");types.add("4");types.add("12");types.add("14");
//			types.add("6");types.add("7");types.add("8");types.add("9");types.add("37");
//			if(userList != null && !userList.isEmpty()){
//				for (int k = 0; k < userList.size(); k++) {
//					String userId = userList.get(k);
//					logger.info(">>>>更新订单中心发送记录中的taskName为taskId,查询出的店铺数:" + userList.size() + "第" + (++i) + "次查询");
//					Criteria criteria = new Criteria("userId").is(userId);
//	 				criteria.and("type").in(types);
//	 				List<SmsRecordDTO> recordDTOs = smsRecordRepository.find(new Query(criteria), userId);
//					if(recordDTOs != null && !recordDTOs.isEmpty()){
//						for(int j = 0;j < recordDTOs.size(); j++){
//							logger.info(">>>>>>>" + userId + "的总发送记录个数:" + recordDTOs.size() + "-->第" + j + "次更新");
//							if(recordDTOs.get(j).getTaskName() != null && !"".equals(recordDTOs.get(j).getTaskName())){
//								Long taskId = tradeSetupService.queryIdByTaskName(userId, recordDTOs.get(j).getType(), recordDTOs.get(j).getTaskName());
//								if(null == taskId){
//									continue;
//								}
//								recordDTOs.get(j).setTaskId(taskId);
//								smsRecordService.saveSingleSmsRecord(recordDTOs.get(j), userId);
//							}
//						}
//					}
//				}
//			}
//			resultMap.put("msg", "更新完成");
//		} catch (Exception e) {
//			e.printStackTrace();
//			resultMap.put("msg", "更新输错");
//		}
		
	}
	
	/**
	 * 订单中心效果分析，同步到2017-11-15
	 * ztk2017年12月7日下午3:21:05
	 */
	@RequestMapping("/tradeCenterEffect")
	@ResponseBody
	public void asdjas(){
		Runnable runnable = new Runnable() {
			public void run() {
				List<String> userIdList = userInfoService.findUserAccessNotNull();
//				List<String> userIdList = new ArrayList<String>();
//				userIdList.add("哈数据库等哈");
				Long diffDay = DateUtils.getDiffDay("2017-11-15 00:00:00", "2017-12-05 23:59:59");
				Date endEffectNode = null;
				try {
					endEffectNode = DateUtils.stringToDate("2017-12-05 23:59:59", DateUtils.DEFAULT_TIME_FORMAT);
				if(userIdList != null && !userIdList.isEmpty()){
					for (int i = 0; i < userIdList.size(); i++) {
						String userId = userIdList.get(i);
						List<Long> taskIdList = tradeSetupService.queryTaskByUserNick(userId,null);
						if(taskIdList != null && !taskIdList.isEmpty()){
							for (int j = 0; j < diffDay.intValue(); j++) {
								Date effectTimeStart = DateUtils.getStartTimeOfDay(DateUtils.nDaysAgo(j, endEffectNode));
								Date effectTimeEnd = DateUtils.getEndTimeOfDay(DateUtils.nDaysAgo(j, endEffectNode));
								for (Long taskId : taskIdList) {
									try {
										Criteria criteria = new Criteria();
										criteria.and("taskId").is(taskId);
										criteria.and("sendLongTime").gte(effectTimeStart.getTime()).lte(effectTimeEnd.getTime());
										List<SmsRecordDTO> recordDTOList = smsRecordRepository.find(new Query(criteria), userId);
										List<String> targetTidList = new ArrayList<String>();
										int smsNum = 0;
										if(recordDTOList == null || recordDTOList.isEmpty()){
											logger.info("~~~~taskId是："+taskId+"第："+j+"天之前发送记录为null");
											continue;
										}else {
											for (int k = 0; k < recordDTOList.size(); k++) {
												SmsRecordDTO smsRecordDTO = recordDTOList.get(k);
												if(smsRecordDTO != null && smsRecordDTO.getOrderId() != null){
													targetTidList.add(smsRecordDTO.getOrderId());
													smsNum += NumberUtils.getResult(smsRecordDTO.getActualDeduction());
												}
											}
											TradeCenterEffect tradeCenterEffect = tradeCenterEffectService.sumTradeCenterEffect(userId, recordDTOList.get(0).getType(), targetTidList, null, null);
											if(tradeCenterEffect != null){
												logger.info("~~~~taskId是："+taskId+"第："+j+"天之前根据计算所有类型的回款数据不为null");
												tradeCenterEffect.setUserId(userId);
												tradeCenterEffect.setTaskId(taskId);
												tradeCenterEffect.setEffectTime(effectTimeStart);
												tradeCenterEffect.setSmsNum(smsNum);
												tradeCenterEffect.setSmsMoney(NumberUtils.getTwoDouble(smsNum * 0.05));
												//点击量(暂时不上)
												/*linkNum = allEffect.getInteger("total");
												customerClick = allEffect.getInteger("customerClickNum");
												pageClick = allEffect.getInteger("pageClickNum");*/
//												tradeCenterEffect.setCustomerClick(customerClick);
//												tradeCenterEffect.setLinkNum(linkNum);
//												tradeCenterEffect.setPageClick(pageClick);
												tradeCenterEffect.setCreatedBy(userId);
												tradeCenterEffect.setCreatedDate(new Date());
												tradeCenterEffect.setLastModifiedBy(userId);
												tradeCenterEffect.setLastModifiedDate(new Date());
												logger.info("~~~~taskId是："+taskId+"第："+j+"天之前的催付订单数：" + tradeCenterEffect.getTargetOrder() + "回款订单数：" + tradeCenterEffect.getEarningOrder());
												tradeCenterEffectService.saveTradeCenterEffect(tradeCenterEffect);
											}else {
												logger.info("~~~~taskId是："+taskId+"第："+j+"天之前根据计算所有类型的回款数据为null");
											}
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
				} catch (Exception e) {
					logger.info("~~~同步订单中心效果分析出错");
					e.printStackTrace();
				}
				logger.info("~~~同步订单中心效果分析完成");
			}
		};
		new Thread(runnable).start();
	}
	
}
