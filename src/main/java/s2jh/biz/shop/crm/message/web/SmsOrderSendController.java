package s2jh.biz.shop.crm.message.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.api.SecretException;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import s2jh.biz.shop.crm.manage.dao.TradeRepository;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.util.idworker.SmsRecordIdWorker;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.message.service.SmsBlackListService;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.support.service.MultithreadBatchSmsService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.getIpAddress;

/**
 * 订单短信群发，发送短信Controller
 * ZTK2017年1月16日下午12:24:20
 * @throws IOException 
 * @throws InterruptedException 
 */
@Controller
@RequestMapping("/order")
public class SmsOrderSendController {
	
	private  Logger logger = LoggerFactory.getLogger(SmsOrderSendController.class);
	
	@Autowired	private UserAccountService userAccountService;

	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MultithreadBatchSmsService multithreadService;
	
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	
	@Autowired
	private SmsBlackListService smsBlackListService;
	
	@Autowired
	private TradeRepository tradeRepository;
	
	/**
	 * 排除黑名单手机号，同時保持nickName和oid可以對應
	 * ZTK2017年2月28日下午3:58:33
	 */
	private Map<String, String> removeRepeat(String userId,List<String> tidList,List<String> nickNameList,List<String> phoneList){
		//统计时间date1
		Date date1 = new Date();
		Map<String,String> resultMap = new HashMap<String, String>();
		if(phoneList != null && phoneList.size() > 0){
			//屏蔽黑名单(根据卖家查询手机以及昵称黑名单)
//			List<String> blackPhoneList = smsMobileService.queryBlackPhoneNumList(userId);
			List<String> blackPhoneList = null;
			try {
				blackPhoneList = smsBlackListService.findAllPhones(userId);
			} catch (SecretException e) {
				e.printStackTrace();
			}
//			List<SmsMobileDTO> smsMobileList = smsMobileRepositoryService.queryBlackPhoneNumList(userId);
//			List<String> blackPhoneList = new ArrayList<String>();
//			for (SmsMobileDTO smsMobileDTO : smsMobileList) {
//				String mobile = smsMobileDTO.getMobile();
//				blackPhoneList.add(mobile);
//			}
			/*List<String> blackNickList = smsMobileService.findBlackNickList(userId);*/
			//比对两个list中的电话号码是否一致,放入list
			int remNum = 0;//统计黑名单手机和昵称的个数
			int totalNum = 0;//统计不是黑名单手机和昵称的个数
		 		for(int m = 0; m< phoneList.size(); m++){
			 		if(blackPhoneList != null && blackPhoneList.contains(phoneList.get(m))){
			 			resultMap.put("removePhone" + remNum, phoneList.get(m));
						resultMap.put("removeNickName" + remNum, nickNameList.get(m));
						resultMap.put("removeTid" + remNum, tidList.get(m));
						remNum ++;
			 		}else{
			 			resultMap.put("phone" + totalNum, phoneList.get(m));
						resultMap.put("nickName" + totalNum, nickNameList.get(m));
						resultMap.put("tid" + totalNum, tidList.get(m));
						totalNum ++ ;
			 		}
			 	}
		 	resultMap.put("remNum", remNum + "");
		 	resultMap.put("totalNum", totalNum + "");
			//计算时间date2
			Date date2 = new Date();
			System.out.println("循环判断去重，黑名单时间：" + (date2.getTime() - date1.getTime()));
			return resultMap;
		}else{
			return null;
		}
	}
	
	/**
	 * 数组转成集合list
	 * ZTK2017年2月28日下午4:28:58
	 */
	private List<String> arrayToList(String strings){
		List<String> list = new ArrayList<String>();
		if(strings != null && !"".equals(strings)){
			String[] strArr = strings.split(",");
			for (int i = 0; i < strArr.length; i++) {
				list.add(strArr[i]);
			}
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * 发送短信(多线程)
	 * ZTK2017年5月3日下午9:24:54
	 */
	@ResponseBody
	@RequestMapping("/singleSms")
	public Map<String,Object> orderSmsSend(String autograph,String sendTime,String smsTempId,String tids,String nickNames,
			String phones,String send_time_type,String content,String type,String unsubscribeMSGVal,String signVal,String activityName,
			HttpServletRequest request,HttpServletResponse response) throws IOException, InterruptedException {
		if(activityName != null && !"".equals(activityName)){
			activityName = activityName.trim();
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("skip", 1);
		try {
			String userId = (String) request.getSession().getAttribute("taobao_user_nick");
			if(tids == null || "".equals(tids)){
				resultMap.put("msg","请选择订单");
				resultMap.put("skip", 0);
				return resultMap;
			}
			//提示用户的信息
			String error = "";
			//判断短信签名
 			if(signVal != null && !"".equals(signVal)){
				String signValRe = signVal.replace("【", "").replace("】", "").replace(" ", "");
				if(signValRe == null || "".equals(signValRe)){
					resultMap.put("msg","短信签名不能为空或者空格");
					resultMap.put("skip", 0);
					return resultMap;
				}
			}
			if("2".equals(send_time_type) && (sendTime == null || "".equals(sendTime))){
				resultMap.put("msg","定时发送请设置定时发送的时间");
				resultMap.put("skip", 0);
				return resultMap;
			}
			//计算短信长度
			int messageCount = content.length();
			if(messageCount<=70){
				messageCount=1;
			}else{
				messageCount = (messageCount+66)/67;
			}
			//判断短信数量
			Long smsNum = userAccountService.findUserAccountSms(userId);
			List<String> initTidList = arrayToList(tids);
			if(initTidList.size() * messageCount > smsNum){
				resultMap.put("msg", "短信数量不足，请充值");
				resultMap.put("skip", 0);
				return resultMap;
			}
			MsgSendRecord msgSendRecord = new MsgSendRecord();
//			MsgSendRecordDTO msgSendRecord = new MsgSendRecordDTO();
			msgSendRecord.setActivityName(activityName);//活动名称
			msgSendRecord.setStatus("4");//初始保存为发送中
			if("1".equals(send_time_type)){//立即发送
				msgSendRecord.setSendCreat(new Date());//发送创建时间
				msgSendRecord.setCreatedDate(new Date());
				msgSendRecord.setIsSent(true);//是否已发送，只用来区分立即和定时
			}else if("2".equals(send_time_type)){//定时发送
				msgSendRecord.setSendCreat(DateUtils.convertStringToDateNoSS(sendTime));//发送创建时间
				msgSendRecord.setCreatedDate(new Date());
				msgSendRecord.setIsSent(false);//是否已发送，只用来区分立即和定时
			}
			msgSendRecord.setIsShow(true);//显示与否(用户删除操作)
			msgSendRecord.setTotalCount(initTidList.size());//总条数
			msgSendRecord.setType("35");
			msgSendRecord.setUserId(userId);
			msgSendRecord.setTemplateContent(content);//基础短信内容
			Long msgId = msgSendRecordService.insertMsgSendRecord(msgSendRecord);
//			Long msgId = msgSendRecordRepositoryService.insertMsgSendRecord(msgSendRecord);
			List<String> initPhoneList = arrayToList(phones);
			List<String> initNickNameList = arrayToList(nickNames);
			
			//创建集合存放去重完成后的数据
			List<String> phoneList = new ArrayList<String>();
			List<String> nickNameList = new ArrayList<String>();
			List<String> tidList = new ArrayList<String>();
			//重复手机号
			int repeatNum = 0;
			//初始化加密
			EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
			String sessionKey = userInfoService.validateFindSessionKey(userId);
			if(initPhoneList != null && !initPhoneList.isEmpty()){
				Map<String,String> repeatMap = new HashMap<String, String>();
				for (int j = 0; j < initPhoneList.size(); j++) {
					if(repeatMap.containsKey(initPhoneList.get(j))){
//						SmsSendRecord smsSendRecord = new SmsSendRecord();
						SmsRecordDTO smsSendRecord = new SmsRecordDTO();
//						SmsSendRecordDTO smsSendRecord = new SmsSendRecordDTO();
						String removeTid = initTidList.get(j);
						String removeNickName = initNickNameList.get(j);
						String removePhone = initPhoneList.get(j);
//						TransactionOrder trade = transactionOrderService.findTradeById(removeTid);
//						TradeDTO trade = tradeRepository.findTradeById(removeTid);
//						TradeDTO trade = tradeInfoService.findTradeById(removeTid,userId);
//						TransactionOrderDTO trade = transactionOrderRepositoryService.findTradeById(removeTid,userId);
//						String msgContent = ScrabbleUpMessageUtil.getMessage(content, userId, trade,autograph);
						smsSendRecord.setUserId(userId);
						if(EncrptAndDecryptClient.isEncryptData(removeNickName, EncrptAndDecryptClient.SEARCH)){
							smsSendRecord.setBuyerNick(removeNickName);
						}else {
							smsSendRecord.setBuyerNick(decryptClient.encryptData(removeNickName, EncrptAndDecryptClient.SEARCH, sessionKey));
						}
						smsSendRecord.setActivityName(activityName);
						smsSendRecord.setContent(content);
						smsSendRecord.setType("35");
						if(EncrptAndDecryptClient.isEncryptData(removePhone, EncrptAndDecryptClient.PHONE)){
							smsSendRecord.setRecNum(removePhone);
						}else {
							smsSendRecord.setRecNum(decryptClient.encryptData(removePhone, EncrptAndDecryptClient.PHONE, sessionKey));
						}
						smsSendRecord.setActualDeduction(0);
						smsSendRecord.setStatus(4);
						smsSendRecord.setAutograph(autograph);
						smsSendRecord.setSendTime(new Date());
						smsSendRecord.setOrderId(removeTid);
						smsSendRecord.setMsgId(msgId);
						smsSendRecord.setShow(true);
						smsSendRecord.setSendLongTime(new Date().getTime());
						smsSendRecord.setTimestampId(SmsRecordIdWorker.getInstance().nextId());
						smsRecordService.insertSmsSendRecord(smsSendRecord);
//						smsRecordRepository.insertSmsSendRecord(smsSendRecord);
//						smsSendRecordRepositoryService.insertSmsSendRecord(smsSendRecord);
						repeatNum ++;
					}else{
						repeatMap.put(initPhoneList.get(j), initNickNameList.get(j) + ",,," + initTidList.get(j));
					}
				}
				//遍历repeatMap取出数据放到对应的list中,进行后续的操作
				Iterator<Entry<String, String>> iterator = repeatMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					phoneList.add(entry.getKey());
					String[] splits = entry.getValue().split(",,,");
					nickNameList.add(splits[0]);
					tidList.add(splits[1]);
				}
			}
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~大小小于500，单线程开始发送~~~~~~~~~");
			Date date1 = new Date();
			//排除黑名单(手机号和昵称)
			Map<String, String> phoneMap = removeRepeat(userId,tidList,nickNameList,phoneList);
			int remNum = 0;//黑名单的数量
			if(phoneMap != null){
				if(phoneMap.get("remNum") != null && !"0".equals(phoneMap.get("remNum"))){
					remNum = Integer.parseInt(phoneMap.get("remNum"));
					for (int i = 0; i < Integer.parseInt(phoneMap.get("remNum")); i++) {
//						SmsSendRecord smsSendRecord = new SmsSendRecord();
						SmsRecordDTO smsSendRecord = new SmsRecordDTO();
//						SmsSendRecordDTO smsSendRecord = new SmsSendRecordDTO();
						String removeTid = phoneMap.get("removeTid" + i);
						String removeNickName = phoneMap.get("removeNickName" + i);
						String removePhone = phoneMap.get("removePhone" + i);
//						TransactionOrder trade = transactionOrderService.findTradeById(removeTid);
//						TradeDTO trade = tradeRepository.findTradeById(removeTid);
//						TradeDTO trade = tradeInfoService.findTradeById(removeTid,userId);
//						TransactionOrderDTO trade = transactionOrderRepositoryService.findTradeById(removeTid,userId);
//						String msgContent = ScrabbleUpMessageUtil.getMessage(content, userId, trade,autograph);
						smsSendRecord.setUserId(userId);
						if(EncrptAndDecryptClient.isEncryptData(removeNickName, EncrptAndDecryptClient.SEARCH)){
							smsSendRecord.setBuyerNick(removeNickName);
						}else {
							smsSendRecord.setBuyerNick(decryptClient.encryptData(removeNickName, EncrptAndDecryptClient.SEARCH, sessionKey));
						}
						if(EncrptAndDecryptClient.isEncryptData(removePhone, EncrptAndDecryptClient.PHONE)){
							smsSendRecord.setRecNum(removePhone);
						}else {
							smsSendRecord.setRecNum(decryptClient.encryptData(removePhone, EncrptAndDecryptClient.PHONE, sessionKey));
						}
						smsSendRecord.setActivityName(activityName);
						smsSendRecord.setContent(content);
						smsSendRecord.setType("35");
						smsSendRecord.setActualDeduction(0);
						smsSendRecord.setStatus(5);
						smsSendRecord.setAutograph(autograph);
						smsSendRecord.setSendTime(new Date());
						smsSendRecord.setSendLongTime(new Date().getTime());
						smsSendRecord.setOrderId(removeTid);
						smsSendRecord.setMsgId(msgId);
						smsSendRecord.setShow(true);
						smsSendRecord.setTimestampId(SmsRecordIdWorker.getInstance().nextId());
						smsRecordService.insertSmsSendRecord(smsSendRecord);
//						smsRecordRepository.insertSmsSendRecord(smsSendRecord);
//						smsSendRecordRepositoryService.insertSmsSendRecord(smsSendRecord);
					}
				}
				if(phoneMap.get("totalNum") != null && !"0".equals(phoneMap.get("totalNum"))){
					
				}else {
					resultMap.put("skip", 0);
					resultMap.put("msg", "该手机号或买家已加入黑名单中，请确认");
				}
				//将模板添加到模板使用记录中
				if(smsTempId!=null && !"".equals(smsTempId)){
					/*smsTemplateService.addHistoryTemp(Long.parseLong(smsTempId),userId);*/
				}
				//统计发送成功短信条数
				int successNum = 0;
				int failNum = 0;
				//判断是否是立即发送，或者是定时发送(1:立即发送；2：定时发送)
				if("1".equals(send_time_type)){
					for (int i = 0; i < Integer.parseInt(phoneMap.get("totalNum")); i++) {
//						TransactionOrder trade = transactionOrderService.findTradeById(phoneMap.get("tid" + i));
//						TradeDTO trade = tradeRepository.findTradeById(phoneMap.get("tid" + i));
//						TradeDTO trade = tradeInfoService.findTradeById(phoneMap.get("tid" + i),userId);
						if(phoneMap.get("tid" + i) != null && !"".equals(phoneMap.get("tid" + i))){
							tradeRepository.updateMulti(new Query(Criteria.where("tid").is(phoneMap.get("tid" + i))), new Update().set("msgId", msgId), userId);
						}
//						TransactionOrderDTO trade = transactionOrderRepositoryService.findTradeById(phoneMap.get("tid" + i),userId);
//						String msgContent = ScrabbleUpMessageUtil.getMessage(content, userId, trade,autograph);
						String number = phoneMap.get("phone" + i);
						if(EncrptAndDecryptClient.isEncryptData(phoneMap.get("phone" + i), EncrptAndDecryptClient.PHONE)){
							number = decryptClient.decryptData(phoneMap.get("phone" + i), EncrptAndDecryptClient.PHONE, sessionKey);
						}
						String[] numbers = {number};
						BatchSmsData batchSmsData = new BatchSmsData(numbers);
						batchSmsData.setIpAdd(getIpAddress.getIpAddress(request));
						batchSmsData.setUserId(userId);
						batchSmsData.setActualDeduction(messageCount);
						batchSmsData.setContent(content);
						batchSmsData.setChannel(autograph);
						batchSmsData.setTid(phoneMap.get("tid" + i));
						batchSmsData.setMsgId(msgId);
						batchSmsData.setType("35");//订单短信群发
						multithreadService.batchOperateSms(batchSmsData);
						successNum += batchSmsData.getSuccess();
						failNum += batchSmsData.getFail();
					}
					error = "发送成功"+successNum+"条";
					msgSendRecord.setIsSent(true);//是否已发送，只用来区分立即和定时
					Date date2 = new Date();
					logger.debug("~~~~~~~~~~~~~~单线程耗费时间:" + (date2.getTime() - date1.getTime()) + "ms~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					/*mobileSettingService.sendSmsCountRemind(userId);*/
				}else if("2".equals(send_time_type)){
					for (int i = 0; i < Integer.parseInt(phoneMap.get("totalNum")); i++) {
//						TransactionOrder trade = transactionOrderService.findTradeById(phoneMap.get("tid" + i));
//						TradeDTO trade = tradeRepository.findTradeById(phoneMap.get("tid" + i));
//						TradeDTO trade = tradeInfoService.findTradeById(phoneMap.get("tid" + i),userId);
//						TransactionOrderDTO trade = transactionOrderRepositoryService.findTradeById(phoneMap.get("tid" + i),userId);
//						String msgContent = ScrabbleUpMessageUtil.getMessage(content, userId, trade,autograph);
//						SmsSendInfoDTO smsSendInfo = new SmsSendInfoDTO();
						SmsSendInfo smsSendInfo = new SmsSendInfo();
						smsSendInfo.setUserId(userId);
						smsSendInfo.setTid(Long.parseLong(phoneMap.get("tid"+i)));
						if(EncrptAndDecryptClient.isEncryptData(phoneMap.get("nickName"+i), EncrptAndDecryptClient.SEARCH)){
							smsSendInfo.setNickname(phoneMap.get("nickName"+i));
						}else {
							smsSendInfo.setNickname(decryptClient.encryptData(phoneMap.get("nickName"+i), EncrptAndDecryptClient.SEARCH, sessionKey));
						}
						if(EncrptAndDecryptClient.isEncryptData(phoneMap.get("phone"+i), EncrptAndDecryptClient.PHONE)){
							smsSendInfo.setPhone(phoneMap.get("phone"+i));
						}else {
							smsSendInfo.setPhone(decryptClient.encryptData(phoneMap.get("phone"+i), EncrptAndDecryptClient.PHONE, sessionKey));
						}
//						smsSendInfo.setNickname(phoneMap.get("nickName"+i));
//						smsSendInfo.setPhone(phoneMap.get("phone"+i));
						smsSendInfo.setContent(content);
						smsSendInfo.setType("35");
						smsSendInfo.setLastModifiedBy(userId);
						smsSendInfo.setLastModifiedDate(new Date());
						smsSendInfo.setMsgId(msgId);
						smsSendInfo.setCreatedDate(new Date());
						smsSendInfo.setChannel(autograph);
						msgSendRecord.setIsSent(false);
						smsSendInfo.setSendTime(DateUtils.convertStringToDateNoSS(sendTime));
						smsSendInfo.setStartSend(DateUtils.convertStringToDateNoSS(sendTime));
//						smsSendInfoRepositoryService.saveSystemSms(smsSendInfo);
						myBatisDao.execute(SmsSendInfo.class.getName() + "Schedule", "doCreateByScheduleSend",smsSendInfo);
					}
					error = "定时保存成功";
				}
				msgSendRecord.setStatus("5");//发送状态 4：发送中 5：发送完成
				msgSendRecord.setSucceedCount(successNum);//发送成功条数
				msgSendRecord.setFailedCount(failNum);//发送失败条数
				msgSendRecord.setBlackCount(remNum);//发送黑名单条数
				msgSendRecord.setRepeatCount(repeatNum);//发送手机号重复条数
				msgSendRecord.setId(msgId);
				msgSendRecordService.updateMsgRecordById(msgSendRecord);
//				msgSendRecordRepositoryService.updateMsgRecordById(msgSendRecord);
				resultMap.put("msg", error);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("skip", 0);
			resultMap.put("msg", "系统错误，请重新发送或联系管理员！");
		}
		return resultMap;
	}	
}
