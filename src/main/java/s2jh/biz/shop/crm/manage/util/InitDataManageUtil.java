/** 
 * Project Name:s2jh4net 
 * File Name:InitDataManageUtil.java 
 * Package Name:s2jh.biz.shop.crm.manage.util 
 * Date:2017年6月6日下午5:10:48 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
  
package s2jh.biz.shop.crm.manage.util;  

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.handler.impl.DefaultHandlerChain;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.util.DateUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.SynchronousTradeHelper;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.user.service.UserInfoService;

/** 
 * ClassName:InitDataManageUtil <br/> 
 * Date:     2017年6月6日 下午5:10:48 <br/> 
 * @author   zlp
 * @version   1.0     
 */
@Component
public class InitDataManageUtil implements Runnable {

	@Autowired
	private VipMemberService vipMemberService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SmsRecordService smsRecordService;

	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private SynchronousTradeHelper synchronousTradeHelper;
    @Resource(name="mongoTemplate")
    protected MongoTemplate mongoTemplate;  
	@Resource
	private DefaultHandlerChain orderHandlerChain;
	@Resource
	private DefaultHandlerChain smsRecordHandlerChain;
	@Resource
	private DefaultHandlerChain importTradeHandlerChain;
	@Resource
	private DefaultHandlerChain encrptTradeHandlerChain;
	@Resource
	private DefaultHandlerChain syncEffectHandlerChain;

	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Autowired
	private MsgSendRecordService msgSendRecordService;

	public static   int sleepTime = 1000;
	public static   int sleepTimePer = 10000;
	private static  int interputTime = 1000*60;
	private static final Logger loger = Logger.getLogger(InitDataManageUtil.class);
	
	@Override
	public void run() {
//		 loger.info("开始执行");
//		 doEffectPictureData();
		doRepairTbData();
//		 loger.info("结束执行"); 
	}
	
	public void doEffectPictureData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String msgIdNode = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_MSGID_KEY);
				List<Long> msgIds = msgSendRecordService.findMsgIdByTime(null, null, new Date(),null);
				if(msgIds != null && !msgIds.isEmpty()){
					Map map = null;
					loger.info("<:::|:::::::::::::::::>同步所有的msg记录，查询出msgId的个数:" + msgIds.size());
					for (Long msgId : msgIds) {
						try {
							 cacheService.putNoTime(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_MSGID_KEY,msgId);
							 loger.info("开始同步"+msgId); 
							 map = new HashMap();
							 map.put("msgId",msgId);
							 MsgSendRecord msgRecord = msgSendRecordService.findOne(Long.valueOf(msgId));
							 Long startTime = DateUtils.dateToLong(msgRecord.getCreatedDate());
							 Date endDate = DateUtils.addDate(msgRecord.getCreatedDate(), 15);
							 Long endTime = DateUtils.dateToLong(endDate);
							 Query query = new Query();
						     query.addCriteria(Criteria.where("created").gte(startTime).lte(endTime));
						     query.addCriteria(Criteria.where("type").ne("import"));
							 List<TradeDTO> migrateTradeDataList = tradeInfoService.findMigrateTradeDataList(query,msgRecord.getUserId(),"0",1,false);
							 if(null!=migrateTradeDataList&&migrateTradeDataList.size()>0){
								 String startId = migrateTradeDataList.get(0).get_id();
								 cacheService.putNoTime(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SYNC_EFFECT_DATA_KEY,startId);
								 syncEffectHandlerChain.doHandle(map);
								 Thread.sleep(sleepTime);
							 }
						}catch (Exception e) {
							loger.info("同步失败"+msgId); 
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
	
	
	
//	public void doEncrptTradeData(){
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				List<UserInfo> queryUserInfos = userInfoService.queryActiveUserInfos(null);
//				try {
//					 Map map = null;
//					 for (UserInfo userInfo : queryUserInfos) {
//						 map = new HashMap();
//						 map.put("user",userInfo);
//						 encrptTradeHandlerChain.doHandle(map);
//						 Thread.sleep(sleepTime);
//					 }
//					 loger.info("同步完毕");
//					 Thread.sleep(interputTime);
//				} catch (Exception e) {
//				  e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	public void doSmsRecordData() {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				for (;;) {
//					try {
//					    Long maxId = myBatisDao.findBy(SmsSendRecord.class.getName(),
//							  "findRecordMaxId", null);
//						
//						String id = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.SMSRECORD_NODE_KEY);
//						if(maxId>Long.valueOf(id)){
//							smsRecordHandlerChain.doHandle(null);
//							Thread.sleep(sleepTime);
//						}else{
//							loger.info("抵达当前数据我要睡"+interputTime+"    ----   "+ maxId +"  ***  "+ id);
//							Thread.sleep(interputTime);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
//	}
	
	
	public void doRepairTbData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						String startTime = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.TRADE_NODE_KEY_START_TIME);
						long startToLong = DateUtils.stringToLong(startTime, DateUtils.DEFAULT_TIME_FORMAT);
						long endToLong = DateUtils.dateToLong(new Date());
						if(startToLong<endToLong-interputTime){
							orderHandlerChain.doHandle(null);
							loger.info("我要睡"+sleepTime);
							Thread.sleep(sleepTime);
						}else{
							loger.info("抵达当前时间我要睡"+interputTime);
							Thread.sleep(interputTime);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	
//	
//	public void doImportTbData() {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				for (;;) {
//					try {
//						String startTime = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.NODE_DATA_CACHE, RedisConstant.RediskeyCacheGroup.TRADE_IMPORT_NODE_KEY_START_TIME);
//						long startToLong = DateUtils.stringToLong(startTime, DateUtils.DEFAULT_TIME_FORMAT);
//						long endToLong = DateUtils.dateToLong(new Date());
//						if(startToLong<endToLong-interputTime){
//							importTradeHandlerChain.doHandle(null);
//							loger.info("导入数据我要睡"+sleepTime);
//							Thread.sleep(sleepTime);
//						}else{
//							loger.info("导入数据抵达当前时间我要睡"+interputTime);
//							Thread.sleep(interputTime);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
//	}
	
//	UserTableDTO usertable = null;
//	 usertable = new UserTableDTO();
//	 usertable.setDataCount(0l);
//	 usertable.setUserId(String.valueOf(44));
//	 usertable.setUserNickName("哈数据库等哈");
//	 cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TABLE_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TABLE_CACHE_KEY+"哈数据库等哈",JsonUtil.toJson(usertable));
	
	 
}
  