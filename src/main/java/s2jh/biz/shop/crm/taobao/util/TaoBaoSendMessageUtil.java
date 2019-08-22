package s2jh.biz.shop.crm.taobao.util;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.SecretException;

import lab.s2jh.core.service.RedisLockServiceImpl;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.entity.SmsSendRecord;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.taobao.info.OperationLogStatus;
import s2jh.biz.shop.crm.taobao.info.ReturnMessage;
import s2jh.biz.shop.crm.taobao.info.SendMessageStatusInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.utils.JsonUtil;

@Component
@Deprecated 
public class TaoBaoSendMessageUtil {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(TaoBaoSendMessageUtil.class);
	@Autowired
	private SmsRecordService smsRdService;
	
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	@Autowired
	private SmsRecordService smsRecordService;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private RedisLockServiceImpl cacheService;
	/**
	 * 短信发送工具类
	 * 
	 * @param map
	 *            需要包含 key=flag value= (之前判断的标记值如果是true才进行后续判断) <br>
	 *            key=smsInfo value= (SmsSendInfo 实例化好的对象，需要将实体类的内容设置好)<br>
	 *            key=tmc value= 1 (选填，监听tmc消息发送的才有此key)
	 * @param myBatisDao
	 *            数据库操作对象，直接外部传递
	 * @return map 将之前传进来的map进行相应返回 key=flag value= (当前逻辑判断的结果 发送成功 true)<br>
	 *         key=smsInfo value= (短信发送日志信息的最新结果 发送失败 false)<br>
	 */
	public Map<String, Object> sendSingleMessage(
			Map<String, Object> map) {
		long startTime = System.currentTimeMillis();
		boolean flag = (boolean) map.get("flag");
		if (flag) {
			SmsSendInfo smsInfo = (SmsSendInfo) map.get("smsInfo");
			if (smsInfo != null) {
				Long tid = smsInfo.getTid();
				String type = smsInfo.getType();
				if(tid!=null && type!=null){
					Long sendFlag = cacheService.setnx(tid+"_"+type+"_smslock", System.currentTimeMillis()+"", 1800L);
					if(sendFlag==0){
						this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsInfo.getId());
						map.put("flag", false);
						return map;
					}
					logger.debug("当前短信在redis中未被缓存，tid:"+smsInfo.getTid()+"type:"+smsInfo.getType());
				}
				if(ValidateUtil.isNotNull(smsInfo.getTid())){
					boolean sendCount = this.smsRecordService.findSmsRecordStatus(String.valueOf(smsInfo.getTid()), smsInfo.getType(), smsInfo.getUserId());
					if(sendCount){
						this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsInfo.getId());
						map.put("flag", false);
						return map;
					}
					logger.debug("短信记录中没有发送过的记录，tid:"+smsInfo.getTid()+"type:"+smsInfo.getType());
				}
				Integer contentNum = smsInfo.getActualDeduction();
				if (contentNum == null) {
					int messageCount = smsInfo.getContent().length();
					if (messageCount <= 70) {
						messageCount = 1;
					} else {
						messageCount = (messageCount + 66) / 67;
					}
					contentNum = messageCount;
					smsInfo.setActualDeduction(contentNum);
				}
				String userName = smsInfo.getUserId();
				map.put("contentNum", contentNum);
				map.put("userId", userName);
				if (map.containsKey("tmc")) {
					userName = "auto";
				}
				int i =1;
				if(smsInfo.getPhone().length()>11){
					String[] str = smsInfo.getPhone().split(",");
					if(str.length>0){
						i = str.length;
					}
				}
				boolean userSmsFlag = userAccountService.doUpdateUserSms(smsInfo.getUserId(),
						SendMessageStatusInfo.DEL_SMS, (contentNum*i),
						smsInfo.getType(), userName, null,
						OperationLogStatus.USER_OPERATION_SINGLE+"，扣除短信"+(contentNum*i)+"条",UserAccountService.TIME_OUT);
				if (userSmsFlag) {
					map = proxySend(map, smsInfo);
				}else{
					this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsInfo.getId());
					this.logger.debug("用户短信余额不足，扣费失败，tid: "+smsInfo.getTid()+"type:"+smsInfo.getType());
				}
			}else{
				map.put("flag", false);
			}
		}
		long endTime = System.currentTimeMillis();
		long result = endTime-startTime;
		if(result>5000){
			logger.debug("发送短信花费时间过长    ："+(result)+" ms,tid ：" + map.get("tid"));
		}
		return map;
	}

	/**
	 * 发送单条短信辅助完成类，发送完短信后会自动判断是否发送成功，如果发送成功直接保存日志，如果发送失败会帮用户恢复刚刚删除的错误短信。
	 * 不管成功还是失败，只要发送过短信都会保存一条短信发送记录
	 * 
	 * @param map
	 *            map集合 key = manipulation value = 是手动订单还是自动订单
	 * @param smsInfo
	 *            发送的具体信息
	 * @param myBatisDao
	 *            数据库操作对象
	 * @return map key = sendStatus value = 发送的具体状态 key = smsInfo value =
	 *         短信的具体内容
	 */
	private Map<String, Object> proxySend(Map<String, Object> map,
			SmsSendInfo smsInfo) {
		SmsSendRecord smsSendRecord = new SmsSendRecord();
		smsSendRecord.setChannel(smsInfo.getChannel());
		 String sendStatus =null;
		try {
			long startTime = System.currentTimeMillis();
			//判断是否是营销短信，如果为营销短信则走新平台
			if (smsInfo.getType().equals("33") || smsInfo.getType().equals("34")
					|| smsInfo.getType().equals("35") || smsInfo.getType().equals("36")) {
				sendStatus = SendMessageUtil.sendMessage(smsInfo.getPhone(),smsInfo.getContent(), null, smsInfo.getUserId());
				ReturnMessage returnMessage = JsonUtil.fromJson(sendStatus, ReturnMessage.class);
				sendStatus = returnMessage.getReturnCode();
			}else if("99".equals(smsInfo.getType())){
				sendStatus = SendMessageTestUtil.sendMessage(smsInfo.getPhone(),smsInfo.getContent(), null, smsInfo.getUserId());
				ReturnMessage returnMessage = JsonUtil.fromJson(sendStatus, ReturnMessage.class);
				sendStatus = returnMessage.getReturnCode();
			}else {
				sendStatus = SendMessageUtilForHangYe.sendMessage(smsInfo.getPhone(),smsInfo.getContent(), null, smsInfo.getUserId());
				ReturnMessage returnMessage = JsonUtil.fromJson(sendStatus, ReturnMessage.class);
				sendStatus = returnMessage.getReturnCode();
			}
			long endTime = System.currentTimeMillis();
			if((endTime-startTime)>5000){
				this.logger.debug("短信接口超时   " + smsInfo.getTid() + " ，类型："+smsInfo.getType());
			}
			map.put("sendStatus", sendStatus);
			smsSendRecord.setResultCode(sendStatus);
			smsSendRecord.setStatus(SendMessageStatusInfo.SEND_SUCCESS
					.equals(sendStatus) ? 2 : 1);
			smsInfo.setStatus(SendMessageStatusInfo.SEND_SUCCESS
					.equals(sendStatus) ? 2 : 1);
			saveSendMessageRecord(map, smsInfo);
			if (SendMessageStatusInfo.SEND_SUCCESS.equals(sendStatus)) { // 短信发送成功
				if (smsInfo.getId() != null) {
					// 删除数据库中的定时任务发送表中的数据
					smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsInfo.getId());
				}
				smsInfo.setStatus(1);
			} else { // 短信未发送成功
				this.logger.debug("定时短信未发送成功，tid："+smsInfo.getTid()+",短信接口返回的状态是："+sendStatus);
				// 刚刚删除，现在恢复用户的短信条数
				Integer contentNum = smsInfo.getActualDeduction();
				String userName = smsInfo.getUserId();
				if (map.containsKey("tmc")) {
					userName = "auto";
				}
				userAccountService.doUpdateUserSms(smsInfo.getUserId(),SendMessageStatusInfo.ADD_SMS, contentNum,
				        smsInfo.getType(), userName, null,OperationLogStatus.USER_OPERATION_SINGLE+"，短信发送失败，恢复短信"+contentNum+"条",UserAccountService.TIME_OUT);
			}
		} catch (Exception e) {
			this.logger.debug("定时短信未发送成功，tid："+smsInfo.getTid()+"出异常了");
			e.printStackTrace();
			map.put("flag", false);
		} finally {
		}
		map.put("smsInfo", smsInfo);
		return map;
	}

	/**
	 * 短信发送记录保存方法
	 * 
	 * @param map
	 *            key = buyerNick value = 买家昵称 key = userId value =
	 *            操作人的信息（系统还是商户群发）
	 * @param smsInfo
	 *            发送的具体信息
	 * @param smsSendRecord
	 *            已经实例化好的发送记录
	 * @param myBatisDao
	 *            数据库操作对象
	 */
	public void saveSendMessageRecord(Map<String, Object> map,
			SmsSendInfo smsInfo) {
		Date date = new Date();
		SmsRecordDTO srDto = new SmsRecordDTO(); 
		srDto.setRecNum(smsInfo.getPhone());
		srDto.setContent(smsInfo.getContent());
		srDto.setSendTime(date);
		srDto.setSendLongTime(date.getTime()); 
		srDto.setReceiverTime(new Date());
		srDto.setType(smsInfo.getType());
		srDto.setChannel(smsInfo.getChannel());
		srDto.setActualDeduction(smsInfo.getActualDeduction());
		srDto.setOrderId(String.valueOf(smsInfo.getTid()));
		srDto.setSource("2"); 
		if (map.containsKey("sendStatus")) {
			srDto.setResultCode((String) map.get("sendStatus"));
		}
		srDto.setStatus(smsInfo.getStatus());
		if (map.containsKey("buyerNick")) {
			if(null!=(String) map.get("buyerNick")&&!"".equals((String) map.get("buyerNick"))){
				srDto.setBuyerNick((String) map.get("buyerNick"));
			}else{
				srDto.setBuyerNick(smsInfo.getNickname());
			}
		} else {
			srDto.setBuyerNick(smsInfo.getNickname());
		}
		srDto.setNickname(smsInfo.getNickname());
		String session = this.judgeUserUtil.getUserTokenByRedis(smsInfo.getUserId());
		try {
			if(!EncrptAndDecryptClient.isEncryptData(srDto.getNickname(), EncrptAndDecryptClient.SEARCH)){
				if(session == null){
					return ;
				}
				srDto.setNickname(EncrptAndDecryptClient.getInstance().encrypt(srDto.getNickname(), EncrptAndDecryptClient.SEARCH, session));
				srDto.setBuyerNick(srDto.getNickname());
			}
			if(!EncrptAndDecryptClient.isEncryptData(srDto.getRecNum(), EncrptAndDecryptClient.PHONE)){
				srDto.setRecNum(EncrptAndDecryptClient.getInstance().encrypt(srDto.getRecNum(), EncrptAndDecryptClient.PHONE, session));
			}
		} catch (SecretException e) {
			return ;
		}
//		srDto.setAutograph(smsInfo.getAutograph());
		srDto.setUserId(smsInfo.getUserId());
		smsRdService.saveSingleSmsRecord(srDto, smsInfo.getUserId());
	}
}
