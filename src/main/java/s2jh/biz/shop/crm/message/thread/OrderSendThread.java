package s2jh.biz.shop.crm.message.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import lab.s2jh.core.dao.mybatis.MyBatisDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import s2jh.biz.shop.crm.manage.dao.TradeRepository;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.SmsMobileService;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.taobao.util.ScrabbleUpMessageUtil;
import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.support.service.MultithreadBatchSmsService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.getIpAddress;

public class OrderSendThread implements Callable<Map<String,Object>> {
	
	private static Logger logger = LoggerFactory.getLogger(OrderSendThread.class);
	
//	private TransactionOrderService transactionOrderService;
	private TradeInfoService tradeInfoService;
//	private TradeRepository tradeRepository;
	private String userId;
	private List<String> tidList;
	private List<String> nickNameList;
	private List<String> phoneList;
	private String autograph;
	private String content;
	private HttpServletRequest request;
	private MultithreadBatchSmsService multithreadService;
	private SmsMobileService smsMobileService;
	private String send_time_type;
//	private SmsSendRecordService smsSendRecordService;
	private SmsRecordService smsRecordService;
//	private SmsRecordRepository smsRecordRepository;
	private String sendTime;
	private String threadName;
	private String activityName;
	private Long msgId;
	private MyBatisDao myBatisDao;

//	public OrderSendThread(TransactionOrderService transactionOrderService, String userId,String autograph, 
	public OrderSendThread(TradeInfoService tradeInfoService, String userId,String autograph, 
//	public OrderSendThread(TradeRepository tradeRepository, String userId,String autograph, 
			List<String> tidList, List<String> nickNameList,List<String> phoneList,String content,String sendTime,
			HttpServletRequest request,MultithreadBatchSmsService multithreadService,SmsMobileService smsMobileService,
//			String send_time_type,SmsSendRecordService smsSendRecordService,MobileSettingService mobileSettingService,
			String send_time_type,SmsRecordService smsRecordService,MobileSettingService mobileSettingService,
//			String send_time_type,SmsRecordRepository smsRecordRepository,MobileSettingService mobileSettingService,
			String threadName,String activityName,Long msgId,MyBatisDao myBatisDao) {
//		this.transactionOrderService = transactionOrderService;
		this.tradeInfoService = tradeInfoService;
//		this.tradeRepository = tradeRepository;
		this.userId = userId;
		this.autograph = autograph;
		this.tidList = tidList;
		this.nickNameList = nickNameList;
		this.phoneList = phoneList;
		this.content = content;
		this.request = request;
		this.multithreadService = multithreadService;
		this.smsMobileService = smsMobileService;
		this.send_time_type = send_time_type;
//		this.smsSendRecordService = smsSendRecordService;
		this.smsRecordService = smsRecordService;
//		this.smsRecordRepository = smsRecordRepository;
		this.sendTime = sendTime;
		this.threadName = threadName;
		this.activityName = activityName;
		this.msgId = msgId;
		this.myBatisDao = myBatisDao;
	}



	public Map<String, Object> call() throws Exception  {
		int SUCCESS_NUM = 0;//统计成功条数
		int BLACK_NUM = 0;//黑名单中的条数
		int  FAIL_NUM = 0;//发送失败条数
		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~执行线程Thread--" + threadName + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Date date1 = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, String> returnMap = removeRepeat(userId, tidList, nickNameList, phoneList);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if(returnMap != null){
			if(returnMap.get("remNum") != null && !"0".equals(returnMap)){
				BLACK_NUM = Integer.parseInt(returnMap.get("remNum"));
				for (int i = 0; i < Integer.parseInt(returnMap.get("remNum")); i++) {
//					SmsSendRecord smsSendRecord = new SmsSendRecord();
					SmsRecordDTO smsSendRecord = new SmsRecordDTO();
					String removeTid = returnMap.get("removeTid" + i);
					String removeNickName = returnMap.get("removeNickName" + i);
					String removePhone = returnMap.get("removePhone" + i);
//					TransactionOrder trade = transactionOrderService.findTradeById(removeTid);
//					TradeDTO trade = tradeRepository.findTradeById(removeTid);
					TradeDTO trade = tradeInfoService.findTradeById(removeTid,userId);
					String msgContent = ScrabbleUpMessageUtil.getMessage(content, userId, trade,autograph);
					smsSendRecord.setUserId(userId);
					smsSendRecord.setBuyerNick(removeNickName);
					smsSendRecord.setActivityName(activityName);
					smsSendRecord.setContent(msgContent);
					smsSendRecord.setType("35");
					smsSendRecord.setRecNum(removePhone);
					smsSendRecord.setActualDeduction(0);
					smsSendRecord.setStatus(5);
					smsSendRecord.setAutograph(autograph);
					smsSendRecord.setSendTime(new Date());
					smsSendRecord.setOrderId(removeTid);
					smsSendRecord.setMsgId(msgId);
					smsSendRecord.setShow(true);
//					smsSendRecordService.insertSmsSendRecord(smsSendRecord);
					smsRecordService.insertSmsSendRecord(smsSendRecord);
//					smsRecordRepository.insertSmsSendRecord(smsSendRecord);
					
				}
			}
			
			//1是立即发送
			if("1".equals(send_time_type)){
				for (int i = 0; i < Integer.parseInt(returnMap.get("totalNum")); i++) {
//					TransactionOrder trade = transactionOrderService.findTradeById(returnMap.get("tid" + i));
//					TradeDTO trade = tradeRepository.findTradeById(returnMap.get("tid" + i));
					TradeDTO trade = tradeInfoService.findTradeById(returnMap.get("tid" + i),userId);
					String msgContent = ScrabbleUpMessageUtil.getMessage(content, userId, trade,autograph);
					String[] numbers = {returnMap.get("phone" + i)};
					BatchSmsData batchSmsData = new BatchSmsData(numbers);
					batchSmsData.setIpAdd(getIpAddress.getIpAddress(request));
					batchSmsData.setUserId(userId);
					batchSmsData.setActualDeduction(Integer.parseInt(returnMap.get("totalNum")));
					batchSmsData.setContent(msgContent);
					batchSmsData.setChannel(autograph);
					batchSmsData.setTid(returnMap.get("tid" + i));
					batchSmsData.setType("35");//订单短信群发
					batchSmsData.setMsgId(msgId);
					multithreadService.batchOperateSms(batchSmsData);
					SUCCESS_NUM += batchSmsData.getSuccess();
					FAIL_NUM += batchSmsData.getFail();
				}
				/*mobileSettingService.sendSmsCountRemind(userId);*/
			}else if("2".equals(send_time_type)){
				for (int i = 0; i < Integer.parseInt(returnMap.get("totalNum")); i++) {
//					TransactionOrder trade = transactionOrderService.findTradeById(returnMap.get("tid" + i));
//					TradeDTO trade = tradeRepository.findTradeById(returnMap.get("tid" + i));
					TradeDTO trade = tradeInfoService.findTradeById(returnMap.get("tid" + i),userId);
					String msgContent = ScrabbleUpMessageUtil.getMessage(content, userId, trade,autograph);
					SmsSendInfo smsSendInfo = new SmsSendInfo();
					smsSendInfo.setUserId(userId);
					smsSendInfo.setTid(Long.parseLong(returnMap.get("tid"+i)));
					smsSendInfo.setNickname(returnMap.get("nickName"+i));
					smsSendInfo.setPhone(returnMap.get("phone"+i));
					smsSendInfo.setContent(msgContent);
					smsSendInfo.setType("35");
					smsSendInfo.setLastModifiedBy(userId);
					smsSendInfo.setLastModifiedDate(new Date());
					smsSendInfo.setMsgId(msgId);
					smsSendInfo.setCreatedDate(new Date());
					smsSendInfo.setChannel(autograph);
					smsSendInfo.setStartSend(DateUtils.convertStringToDateNoSS(sendTime));
					smsSendInfo.setSendTime(DateUtils.convertStringToDateNoSS(sendTime));
					myBatisDao.execute(SmsSendInfo.class.getName() + "Schedule", "doCreateByScheduleSend",smsSendInfo);
				}
			}
			
		}
		Date date2 = new Date();
		logger.debug("~~~~~~~~~~~~~~~~~~~~~~~~~"+threadName+"线程耗费时间~"+(date2.getTime() - date1.getTime())+"ms~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		logger.debug(threadName + "线程取出的数据" + "    SUCCESS_NUM:" + SUCCESS_NUM + "    FAIL_NUM:" + FAIL_NUM + "    BLACK_NUM:" + BLACK_NUM);
		resultMap.put("SUCCESS_NUM", SUCCESS_NUM);
		resultMap.put("FAIL_NUM", FAIL_NUM);
		resultMap.put("BLACK_NUM", BLACK_NUM);
		return resultMap;
	}
	
	/**
	 * 排除黑名单手机号，同時保持nickName和oid可以對應
	 * ZTK2017年2月28日下午3:58:33
	 */
	private Map<String, String> removeRepeat(String userId,List<String> initTidList,List<String> initNickNameList,List<String> initPhoneList){
		Map<String ,String> map = new HashMap<String, String>();
		if(initPhoneList != null && initPhoneList.size() > 0){
			//屏蔽黑名单(根据卖家查询手机以及昵称黑名单)
			List<String> blackPhoneList = smsMobileService.queryBlackPhoneNumList(userId);
			/*List<String> blackNickList = smsMobileService.findBlackNickList(userId);*/
			//比对两个list中的电话号码是否一致,放入list
			int remNum = 0;//统计黑名单手机和昵称的个数
			int totalNum = 0;//统计不是黑名单手机和昵称的个数
			for(int m = 0; m< phoneList.size(); m++){
				if(blackPhoneList.contains(phoneList.get(m))){
					map.put("removePhone" + remNum, phoneList.get(m));
					map.put("removeNickName" + remNum, nickNameList.get(m));
					map.put("removeTid" + remNum, tidList.get(m));
					remNum ++;
				}else{
					map.put("phone" + totalNum, phoneList.get(m));
					map.put("nickName" + totalNum, nickNameList.get(m));
					map.put("tid" + totalNum, tidList.get(m));
					totalNum ++ ;
				}
		 	}
		 	//加入黑名单的昵称的排除
			/*for (int i = 0; i < nickNameList.size(); i++) {
					if(blackNickList.contains(nickNameList.get(i))){
						map.put("removePhone" + remNum, phoneList.get(i));
						map.put("removeNickName" + remNum, nickNameList.get(i));
						map.put("removeTid" + remNum, tidList.get(i));
						remNum ++;
					}else{
						map.put("phone" + totalNum, phoneList.get(i));
						map.put("nickName" + totalNum, nickNameList.get(i));
						map.put("tid" + totalNum, tidList.get(i));
						totalNum ++ ;
					}
			}*/
			map.put("remNum", remNum + "");
			map.put("totalNum", totalNum + "");
			return map;
		}else{
			return null;
		}
	}

}
