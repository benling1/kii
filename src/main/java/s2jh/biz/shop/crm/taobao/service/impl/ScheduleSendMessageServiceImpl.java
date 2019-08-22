package s2jh.biz.shop.crm.taobao.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.taobao.api.SecretException;
import com.taobao.api.domain.Trade;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.RedisLockServiceImpl;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.ScheduleSendMessageService;
import s2jh.biz.shop.crm.taobao.service.judgment.setup.JudgeSellerMessageUtil;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;
import s2jh.biz.shop.crm.vip.service.VipUserService;
import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.support.service.MultithreadBatchSmsService;

@Service
@Deprecated 
public class ScheduleSendMessageServiceImpl implements
		ScheduleSendMessageService {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ScheduleSendMessageServiceImpl.class);
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Autowired
	private MobileSettingService mobileSettingService;

	@Autowired
	private TransactionOrderService transactionOrderService;

	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	
	@Autowired
	private MultithreadBatchSmsService multithreadBatchSmsService;
	
	@Autowired
	private VipUserService vipUserService;

	@Autowired
	private MsgSendRecordService msgSendRecordService;// 短信成功失败信息统计

	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	
	@Autowired
	private JudgeSellerMessageUtil judgeSellerMessageUtil;
	
	@Override
	public void checkAndSend(Date nowDate) {
		// 生成现在的时间 比如 2017-01-01 12:01
		SimpleDateFormat fomartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String startSendTime = fomartDate.format(nowDate);
		Date endDate = null;
		try {
			endDate = fomartDate.parse(startSendTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.add(Calendar.MINUTE, -1);
		cal.add(Calendar.SECOND, 1);
		Date startDate = cal.getTime();
		Map<String, Date> map = new HashMap<String,Date>();
		map.put("startTime", startDate);
		map.put("endTime", endDate);
		List<SmsSendInfo> smsInfoList = myBatisDao.findList(
				SmsSendInfo.class.getName() + "Schedule", "findBySendMessage",
				map);
		SimpleDateFormat fomartDate1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		System.out.println(fomartDate1.format(new Date()));
		MultiSendMessage(smsInfoList);
	}

	@Override
	public void sendOneHourSms() {
		List<SmsSendInfo> smsInfoList = myBatisDao.findList(
				SmsSendInfo.class.getName() + "Schedule",
				"findBySendMessageOneHour", null);
		int length = smsInfoList.size();
		if (length > 0) {
			sendSingleMessage(smsInfoList);
		}
	}

	/**
	 * 短信一条一条发送
	 * 
	 * @param smsInfoList
	 */
	private void sendSingleMessage(List<SmsSendInfo> smsInfoList) {
		Iterator<SmsSendInfo> iterSmsInfo = smsInfoList.iterator();
		while (iterSmsInfo.hasNext()) {
			SmsSendInfo sendInfo = iterSmsInfo.next();
			MultithreadSendMessage(sendInfo);
		}
	}

	/**
	 * 发生短信判断
	 * 
	 * @param sendInfo
	 * @param recordMap
	 */
	private void myShceduleSend(SmsSendInfo sendInfo) {
		Map<String, Object> recordMap = new HashMap<String, Object>();
		recordMap.put("buyerNick", sendInfo.getNickname());
		recordMap.put("tmc", 1);
		if (sendInfo != null) {
			try {
				if (validateTradeStatus(sendInfo)) {
					int messageCount = sendInfo.getContent().length();
					if (messageCount <= 70) {
						messageCount = 1;
					} else {
						messageCount = (messageCount + 66) / 67;

					}
					recordMap.put("flag", true);
					recordMap.put("smsInfo", sendInfo);
					// 发送短消息工具类
					Map<String, Object> resultMap = taoBaoSendMessageUtil.sendSingleMessage(recordMap);
					boolean sendFlag = (boolean) resultMap.get("flag");
					recordMap.put("sendStatus", resultMap.get("sendStatus"));
					if (sendFlag) {
						// 短信发送成功
						sendInfo.setStatus(2);
						sendInfo.setActualDeduction(1);
					}
				} else {
					// 短信的结束时间大于当前时间 不可发送 当前短信延后次日发送
				}
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			} finally {
			}
		}
	}

	/**
	 * @descript 发短信前验证trade最新状态 防止trade状态发生变化
	 * @param sendInfo
	 * @return boolean
	 */
	public boolean validateTradeStatus(SmsSendInfo sendInfo) {
		logger.debug("发短信前验证trade开始 ");
		boolean result = true;
		try {
			// 2-常规催付 3-二次催付 4-聚划算催付 5-预售催付
			if ("2".equals(sendInfo.getType())
					|| "3".equals(sendInfo.getType())
					|| "4".equals(sendInfo.getType())
					|| "5".equals(sendInfo.getType())) {
				long status = this.redisLockServiceImpl.setnx(sendInfo.getUserId()+"_"+sendInfo.getPhone()+"_"+sendInfo.getType()+"_smslock", System.currentTimeMillis()+"", 3600L);
				if(status==0){
					logger.debug("当前短信催付已缓存，删除短信，tid:"+sendInfo.getTid()+"type:"+sendInfo.getType());
					this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(sendInfo.getId());
					return false;
				}
				logger.debug("当前短信在redis中未被缓存，tid:"+sendInfo.getTid()+"type:"+sendInfo.getType());
				boolean statusFlag = this.smsRecordService.findSmsRecordStatus1(sendInfo.getNickname(),sendInfo.getUserId(),sendInfo.getType());
				if (statusFlag) {
					logger.debug("短信记录查询到已催付过，不发送催付短信，tid:"+sendInfo.getTid()+"type:"+sendInfo.getType());
					this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(sendInfo.getId());
					return false;
				}
				logger.debug("短信记录查询到未催付短信记录，tid:"+sendInfo.getTid()+"type:"+sendInfo.getType());
				boolean oderCount = this.judgeSellerMessageUtil.getCountByOneHourPayment(sendInfo.getUserId(), sendInfo.getNickname());
				if(oderCount){
					logger.debug("当前短信在一小时内已经付过款，tid:"+sendInfo.getTid()+"type:"+sendInfo.getType());
					this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(sendInfo.getId());
					return false;
				}
				logger.debug("一小时内未付过款，tid:"+sendInfo.getTid()+"type:"+sendInfo.getType());
				// 根据tid调用淘宝接口查询trade状态
				Trade trade = transactionOrderService.queryTrade(String
						.valueOf(sendInfo.getTid()));
				// PAY_PENDING WAIT_BUYER_PAY TRADE_NO_CREATE_PAY
				if (null != trade) {
					String staus = trade.getStatus();
					if (staus.equals(TradesInfo.WAIT_BUYER_PAY)
							|| staus.equals(TradesInfo.PAY_PENDING)
							|| staus.equals(TradesInfo.TRADE_NO_CREATE_PAY)) {
						result = true;
					} else {
						logger.debug("发短信前验证tradeStatus "
								+ sendInfo.getStatus());
						result = false;
						this.smsSendInfoScheduleService.delSmsScheduleByPay(
								sendInfo.getUserId(), sendInfo.getTid());
					}
				}
			}
			// 判断如果是回款提醒，查看订单是否结束确认收货，把定时发送表的待发送的短信删除
			if ("14".equals(sendInfo.getType())) {
				Trade hkTrade = transactionOrderService.queryTrade(String
						.valueOf(sendInfo.getTid()));
				if (ValidateUtil.isNotNull(hkTrade.getStatus()) ) {
					if(hkTrade.getStatus().equals("TRADE_FINISHED")){
						this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(sendInfo.getId());
						return false;
					}
				}
			}

			// 如果是物流超时提醒
			if ("10-2".equals(sendInfo.getType())) {
				logger.debug("物流超时提醒发短信前tradeType " + sendInfo.getType());
				sendInfo.setType("10-1");
				SmsSendInfo info = myBatisDao.findBy(
						SmsSendInfo.class.getName() + "Schedule",
						"findByTypeAndTid", sendInfo);
				if (info != null) {
					Date sendTime = info.getSendTime();
					// 如果物流发送时间超过签收发送时间，则删除不发送
					if (sendTime != null
							&& sendTime.after(sendInfo.getSendTime())) {
						this.smsSendInfoScheduleService
								.delSmsScheduleBySendSuccess(sendInfo.getId());
						result = false;
					}

				}
			}
			// 如果是签收提醒
			if ("10-1".equals(sendInfo.getType())) {
				logger.debug("签收提醒发短信前tradeType " + sendInfo.getType());
				sendInfo.setSendTime(org.apache.commons.lang3.time.DateUtils
						.addDays(sendInfo.getSendTime(), 1));
				this.smsSendInfoScheduleService.doAutoCreate(sendInfo);
				return false;
			}

		} catch (Exception e) {
			logger.debug("发短信前验证trade失败" + sendInfo.getTid()
					+ sendInfo.getNickname());
			result = true;
		}
		return result;

	}

	/**
	 * 创建人：邱洋
	 * 
	 * @Title: 开启一个线程处理schedule表中的定时发送任务
	 * @date 2017年4月27日--上午10:16:23
	 * @return void
	 * @throws
	 */
	public void MultithreadSendMessage(final SmsSendInfo sendInfo) {
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread() {
			@Override
			public void run() {
				myShceduleSend(sendInfo);
				System.out.println(new Date());
			}
		});
	}

	/**
	 * 创建人：邱洋
	 * 
	 * @Title: 批量发送短信内容一样的信息
	 * @date 2017年5月3日--下午7:41:37
	 * @return void
	 * @throws
	 */
	public void MultithreadSendMessage(final String phone[],
			final String content, final String userId, final String type,
			final List<Long> idList, final Long msgId) {
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread() {
			@Override
			public void run() {
				BatchSmsData obj = new BatchSmsData(phone);
				obj.setContent(content);
				obj.setUserId(userId);
				boolean isVip = vipUserService.findVipUserIfExist(userId);
				obj.setVip(isVip);
				obj.setType(type);
				obj.setMsgId(msgId);
				multithreadBatchSmsService.batchOperateSms(obj);
				System.out
						.println("----------------------------------------------------------开始统计成功和失败短信条数----------------------------------------------------------");
				/**
				 * 在发送任务全部执行之后，统计成功和失败的短信数量并记录到短信发送总记录表crm_msgrecord 2017.05.09
				 */
				int successCustom = obj.getSuccess();
//				int total = obj.getTotal();
				// int failCustom = total - successCustom;
				int failCustom = obj.getFail();

				MsgSendRecord msgSendRecord = null;
				if (obj.getMsgId() != null) {
					// if(successCustom != 0 && failCustom >= 0){
					if (successCustom != 0 && failCustom >= 0) {
//					if (failCustom >= 0) {
						// 更新总记录表crm_msgrecord添加成功总条数和失败总条数
						msgSendRecord = new MsgSendRecord();
						msgSendRecord.setFailedCount(failCustom);
						msgSendRecord.setSucceedCount(successCustom);
//						msgSendRecord.setStatus("1");
						msgSendRecord.setIsSent(true);
						msgSendRecord.setId(msgId);
					}
					if (msgSendRecord != null) {
						// 更新数据
						msgSendRecordService
								.updateMsgRecordByMsgId(msgSendRecord);
						System.out
								.println("----------------------------------------------------------统计成功和失败短信条数完毕！！！----------------------------------------------------------");
					}
				}
				System.out.println("开始删除schedule表数据！：");
				System.out.println("idlist" + idList.get(0));
				if (idList != null && idList.size() > 0) {
					List<Long> list = new ArrayList<Long>();
					for (int i = 0; i < idList.size(); i++) {
						list.add(idList.get(i));
						if (i != 0 && i % 500 == 0) {
							try {
								smsSendInfoScheduleService
										.delSmsScheduleByIds(list);
								list = new ArrayList<Long>();
							} catch (Exception e) {
							}
						}
					}
					System.out.println(list.size() + "list内容：" + list.get(0));
					if (null != list && list.size() > 0) {
						if (list.size() == 1) {
							smsSendInfoScheduleService
									.delSmsScheduleBySendSuccess(list.get(0));
						} else {
							smsSendInfoScheduleService
									.delSmsScheduleByIds(list);
						}
					}
				}
			}
		});
	}

	public void MultithreadSendMessage(final String phone[],
			final String content, final String userId, final String type,
			final Long ids, final Long msgId) {
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread() {
			@Override
			public void run() {
				BatchSmsData obj = new BatchSmsData(phone);
				obj.setContent(content);
				obj.setUserId(userId);
				obj.setType(type);
				obj.setMsgId(msgId);
				multithreadBatchSmsService.batchOperateSms(obj);
				System.out
						.println("----------------------------------------------------------开始统计成功和失败短信条数----------------------------------------------------------");
				/**
				 * 在发送任务全部执行之后，统计成功和失败的短信数量并记录到短信发送总记录表crm_msgrecord 2017.05.09
				 */
				int successCustom = obj.getSuccess();
//				int total = obj.getTotal();
				// int failCustom = total - successCustom;
				int failCustom = obj.getFail();

				MsgSendRecord msgSendRecord = null;
				if (obj.getMsgId() != null) {
					// if(successCustom != 0 && failCustom >= 0){
					if (successCustom != 0 && failCustom >= 0) {
//						if (failCustom >= 0) {
						// 更新总记录表crm_msgrecord添加成功总条数和失败总条数
						msgSendRecord = new MsgSendRecord();
						msgSendRecord.setFailedCount(failCustom);
						msgSendRecord.setSucceedCount(successCustom);
//						msgSendRecord.setStatus("1");
						msgSendRecord.setIsSent(true);
						msgSendRecord.setId(msgId);
					}
					if (msgSendRecord != null) {
						// 更新数据
						msgSendRecordService
								.updateMsgRecordByMsgId(msgSendRecord);
						System.out
								.println("----------------------------------------------------------统计成功和失败短信条数完毕！！！----------------------------------------------------------");
					}
				}
				if (ids != null && ids != 0) {
					smsSendInfoScheduleService.delSmsScheduleBySendSuccess(ids);
				}
			}
		});
	}

	@SuppressWarnings({ "rawtypes" })
	public void MultiSendMessage(List<SmsSendInfo> list) {
		String content = null;// 短信内容
		String userId = null;// 用户昵称
		Long msgId = null;// 短信记录表的id
		String type = null;
		List<String> phone = new ArrayList<String>();// 手机号码
//		String phone = null;//手机号码文件 
		Iterator it = list.iterator();
		List<Long> listId = new ArrayList<Long>();// 存放schedule表中记录的id，发送完成之后需要根据这个ID删除数据
		while (it.hasNext()) {
			SmsSendInfo si = (SmsSendInfo) it.next();
			String session = this.judgeUserUtil.getUserTokenByRedis(si.getUserId());
			try {
				// 判断
				if (si.getType() != null && !"33".equals(si.getType())&&!"35".equals(si.getType())&&!"34".equals(si.getType())) { //非短信营销类短信
					si.setNickname(this.judgeUserUtil.getDecryptData(si.getNickname(), EncrptAndDecryptClient.SEARCH, null, session));
					si.setPhone(this.getPhoneToString(si.getPhone(), session));
					MultithreadSendMessage(si);
					continue;
					// 判断短信内容和店铺是否相同，相同则将手机号放入同一个集合中
//					if (userId != null&& content != null&& userId.equals(si.getUserId())&& content.equals(si.getContent())&& ( type.equals("36")) && msgId == si.getMsgId()) {
//						si.setPhone(this.getPhoneByOne(si.getPhone(), session));
//						phone.add(si.getPhone());
//						listId.add(si.getId());
//					} else {
//						if (phone != null && phone.size() > 0) {
//							String[] phones = this.getPhoneByCollection(phone, session);
//							MultithreadSendMessage(phones, content, userId, type,listId, msgId);
//						}
//						// 当判断短信内容不一样的这一条发送出去，
//						if ( si.getType().equals("36")) {
//							String[] phones = this.getPhoneByCollection(si.getPhone(), session);
//							MultithreadSendMessage(phones, si.getContent(),
//									si.getUserId(), si.getType(), si.getId(),
//									si.getMsgId());
//						} else {
//							// 所有的type不为营销中心的定时短信发送也走下面的
//							si.setPhone(this.getPhoneByOne(si.getPhone(), session));
//							MultithreadSendMessage(si);
//						}
//
//						content = null;
//						userId = null;
//						msgId = 0L;
//						listId = new ArrayList<Long>();
//						phone = new ArrayList<String>();
//						continue;
//					}
				} else if (si.getType() != null && ("33".equals(si.getType())||"35".equals(si.getType())||"34".equals(si.getType()))) {
					listId = new ArrayList<Long>();
					if (si != null && si.getPhone() != null) {
						si.setNickname(this.judgeUserUtil.getDecryptData(si.getNickname(), EncrptAndDecryptClient.SEARCH, null, session));
						String[] phones = this.getPhoneToArray(si.getPhone(), session);
						if(phones==null || phones.length==0){
							this.logger.equals("营销短信发送的手机号码不规范，拆分后为空，无法发送！"+si.getId());
							continue;
						}
						listId.add(si.getId());
						MultithreadSendMessage(phones, si.getContent(),
								si.getUserId(), si.getType(), listId, si.getMsgId());
					}
				}
			} catch (SecretException e) {
//				logger.error("定时任务表发送解密出错 用户："+si.getUserId()+" sessionKey："+session+"要发送的短信内容为："+si.getContent());
//				if(ErrorUtil.isInvalidSession(e)) {
//					logger.error("用户sessionKey失效");
//			        // 标记该sessionkey无效，重新授权之前不要再调用
//			    }else{
//			    	logger.error("解密出错啦,请直接呼叫wy");geini 
//			    }
//				e.printStackTrace();
				continue;
			}catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		if (phone != null && phone.size() > 0) {
			try {
				String session = this.judgeUserUtil.getUserTokenByRedis(userId);
				String[] phones = this.getPhoneToArray(phone, session);
				MultithreadSendMessage(phones, content, userId, type, listId, msgId);
			} catch (SecretException e) {
				e.printStackTrace();
			}
			
		}
		
		/**
		 * 当type==33时执行以下方法
		 */
		// String mobliles = null;
		// if (phone != null && phone.size() > 0) {
		// for (String moblie : phone) {
		// if (mobliles == null) {
		// mobliles = moblie;
		// } else {
		// mobliles = mobliles + "," + moblie;
		// }
		// }
		// }
		//
		// if (mobliles != null && mobliles.length() > 0) {
		// String[] phones = mobliles.split(",");
		// MultithreadSendMessage(phones, content, userId, type, listId,msgId);
		// }

		/**
		 * 在发送任务全部执行之后，统计成功和失败的短信数量并记录到短信发送总记录表crm_msgrecord 2017.05.09
		 */

		// if(phone.size()==500||(i+1)==list.size()){
		// int messageLength =0;
		// if(content!=null){
		// messageLength = content.length();
		// }
		// if(messageLength<70){
		// messageLength =1;
		// }else{
		// messageLength = (messageLength + 66) / 67;
		// }
		// String mobliles =null;
		// for(String moblie:phone){
		// if(mobliles==null){
		// mobliles = moblie;
		// }else{
		// mobliles = mobliles+","+moblie;
		// }
		// }
		// String [] phones = mobliles.split(",");
		// MultithreadSendMessage(phones, content, userId,
		// (messageLength*phone.size()), type);
		// phone = new ArrayList<String>();
		// }
	}
	/**
	 * 将短信中的手机号码解密，返回字符串
	 * @author: wy
	 * @time: 2017年8月2日 下午1:47:47
	 * @param phones 手机号码（可以为1个也可以为多个用英文逗号连接）
	 * @param session 卖家的sessionKey
	 * @return 解密后的数据，返回字符串
	 * @throws SecretException sessionKey过期
	 */
	private String getPhoneToString(String phones,String session) throws SecretException{
		if(ValidateUtil.isEmpty(phones)){
			return phones;
		}
		if(phones.length()<70){
			if(EncrptAndDecryptClient.isEncryptData(phones, EncrptAndDecryptClient.PHONE)){
				phones = EncrptAndDecryptClient.getInstance().decrypt(phones, EncrptAndDecryptClient.PHONE, session);
			}
			return phones;
		}
		StringBuffer result = new StringBuffer();
		String str[] = phones.split(",");
		int i = 0;
		List<String> oldList = new ArrayList<String>(str.length);
		Collections.addAll(oldList,str);
		Map<String,String> phonesMap = EncrptAndDecryptClient.getInstance().decrypt(oldList, EncrptAndDecryptClient.PHONE, session);
		if(phonesMap == null){
			return phones;
		}
		for (Entry<String,String> entry : phonesMap.entrySet()) {
			if(i==0){
				result.append(entry.getValue());
				i++;
			}else{
				result.append(",").append(entry.getValue());
			}
		}
		return result.toString();
	}
	/**
	 * 将短信中的手机号码解密，返回字符串数组
	 * @author: wy
	 * @time: 2017年8月2日 下午1:49:22
	 * @param phones 由手机号码组成的字符串（可以为1个也可以为多个用英文逗号连接）
	 * @param session 用户的sessionKey
	 * @return 解密后的数据，返回字符串数组
	 * @throws SecretException sessionKey过期
	 */
	private String[] getPhoneToArray(String phones,String session) throws SecretException{
		if(ValidateUtil.isEmpty(phones)){
			return null;
		}
		if(phones.length()<70){
			if(EncrptAndDecryptClient.isEncryptData(phones, EncrptAndDecryptClient.PHONE)){
				if(session==null){
					throw new RuntimeException("定时任务发送信息---->数据为加密数据，但是用户的session为空");
				}
				phones = EncrptAndDecryptClient.getInstance().decrypt(phones, EncrptAndDecryptClient.PHONE, session);
			}
			return phones.split(",");
		}
		String str[] =  phones.split(",");
		List<String> oldList = new ArrayList<String>(str.length);
		Collections.addAll(oldList,str);
		Map<String,String> phonesMap = EncrptAndDecryptClient.getInstance().decrypt(oldList, EncrptAndDecryptClient.PHONE, session);
		if(phonesMap==null)
			return str;
		return phonesMap.values().toArray(new String[0]);
	}
	/**
	 * 将短信中的手机号码解密，返回字符串数组
	 * @author: wy
	 * @time: 2017年8月2日 下午1:50:49
	 * @param phones 手机号码List集合
	 * @param session  用户的sessionKey
	 * @return 解密后的数据，返回字符串数组
	 * @throws SecretException sessionKey过期
	 */
	private String[] getPhoneToArray(List<String> phones,String session) throws SecretException{
		if(ValidateUtil.isEmpty(phones)){
			return null;
		}
		if(phones.size()==1){
			if(EncrptAndDecryptClient.isEncryptData(phones.get(0), EncrptAndDecryptClient.PHONE)){
				if(session==null){
					throw new RuntimeException("定时任务发送信息---->数据为加密数据，但是用户的session为空");
				}
				phones.set(0, EncrptAndDecryptClient.getInstance().decrypt(phones.get(0), EncrptAndDecryptClient.PHONE, session));
			}
			return (String[]) phones.toArray();
		}
		Map<String,String> phonesMap =EncrptAndDecryptClient.getInstance().decrypt(phones, EncrptAndDecryptClient.PHONE, session);
		if(phonesMap==null)
			return phones.toArray(new String[0]);
		return phonesMap.values().toArray(new String[0]);
	}
}
