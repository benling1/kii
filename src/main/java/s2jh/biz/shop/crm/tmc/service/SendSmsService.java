package s2jh.biz.shop.crm.tmc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lab.s2jh.core.dao.mybatis.MyBatisDaoT;
import lab.s2jh.core.service.RedisLockServiceImpl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.message.service.SmsSendInfoService;
import s2jh.biz.shop.crm.order.pojo.TbTransactionOrder;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.taobao.info.OperationLogStatus;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.ReturnMessage;
import s2jh.biz.shop.crm.taobao.info.SendMessageStatusInfo;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.taobao.util.SendMessageUtil;
import s2jh.biz.shop.crm.taobao.util.SendMessageUtilForHangYe;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.tmc.entity.TmcMessages;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.view.service.ShortLinkService;
import s2jh.biz.shop.utils.JsonUtil;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.SecretException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.LogisticsTraceSearchRequest;
import com.taobao.api.response.LogisticsTraceSearchResponse;

/** 发送短信
* @author wy
* @version 创建时间：2017年9月4日 下午12:04:30
*/
@Service
public class SendSmsService {
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	@Autowired
	private MyBatisDaoT myBatisDaoT;
	@Autowired
	private TransactionOrderService transactionOrderService;
	@Autowired
	private RedisLockServiceImpl cacheService;
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	@Autowired
	private SmsSendInfoService smsSendInfoService;
	@Autowired
	private SmsRecordService smsRdService;
	@Autowired
	private ShortLinkService shortLinkService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private ItemService itemService;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SendSmsService.class);
	public void doHandle(TmcMessages tmcMessages){
		if(!tmcMessages.getFlag()){
			return;
		}
		if(tmcMessages.getTradeSetup()==null){
			return;
		}
		if(tmcMessages.getTrade()==null){
			return;
		}
		if(tmcMessages.getUser()==null){
			return;
		}
		String smsCotent = tmcMessages.getTradeSetup().getSmsContent();
		smsCotent = this.getNewSms(smsCotent, tmcMessages);
		if(ValidateUtil.isEmpty(smsCotent)){
			logger.debug("短内容为空，无法发送，tid: "+tmcMessages.getTid()+" type:"+tmcMessages.getSettingType());
			return ;
		}
		SmsSendInfo smsSendInfo = this.getSmsInfo(smsCotent, tmcMessages);
		if(smsSendInfo==null){
			this.logger.debug("短信手机号码为空，无法发送  "+" tid："+tmcMessages.getTid()+ " 类型:"+tmcMessages.getSettingType()+",id："+tmcMessages.getTradeSetup().getId());
			return ;
		}
		if(tmcMessages.getSendSchedule()==null){
			tmcMessages.setSendSchedule(false);
		}
		this.logger.debug("拼凑好的短信内容为: "+smsCotent +" tid："+tmcMessages.getTid()+ " 类型:"+tmcMessages.getSettingType()+",id："+tmcMessages.getTradeSetup().getId()+"是否为定时短信:"+tmcMessages.getSendSchedule());;
		if(tmcMessages.getSendSchedule()){ 
			//定时短信
			long nowDate = System.currentTimeMillis()+60000;
			if(smsSendInfo.getStartSend().getTime()<(nowDate)){
				smsSendInfo.setStartSend(new Date(nowDate));
				smsSendInfo.setEndSend(null);
			}
			this.smsSendInfoScheduleService.doAutoCreate(smsSendInfo);
		}else{ //立即发送短信
			this.sendSingleSms(smsSendInfo);
		}
	}
	/**
	 * 发送单条短信
	 * @author: wy
	 * @time: 2017年9月4日 下午3:49:09
	 * @param smsSendInfo 短信实体类
	 * @return 是否发送成功
	 */
	public boolean sendSingleSms(SmsSendInfo smsSendInfo){
		long startTime = System.currentTimeMillis();
		try {
		    this.logger.debug("发送短信，tid: "+smsSendInfo.getTid()+" type:"+smsSendInfo.getType());
			if(ValidateUtil.isEmpty(smsSendInfo.getContent())){
				logger.debug("短内容为空，无法发送，tid: "+smsSendInfo.getTid()+" type:"+smsSendInfo.getType());
				return false;
			}
			if(smsSendInfo.getStartSend()!=null){
				if(smsSendInfo.getStartSend().getTime()>(System.currentTimeMillis()+6000000)){
				    this.logger.debug("开始时间大于当前时间10分钟，延后发送，tid: "+smsSendInfo.getTid()+" type:"+smsSendInfo.getType());
					this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
					this.smsSendInfoScheduleService.doAutoCreate(smsSendInfo);
					return true;
				}
			}
			if(smsSendInfo.getEndSend()!=null){
				if(smsSendInfo.getEndSend().getTime()<System.currentTimeMillis()){
					this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
					logger.debug("短信超时，不允许发送，tid: "+smsSendInfo.getTid()+"type:"+smsSendInfo.getType()+"  结束时间："+smsSendInfo.getEndSend());
					return false;
				}
			}
			if(smsSendInfo.getFilterOnce()==null){
				smsSendInfo.setFilterOnce(false);
			}
			if(this.getOnceByTrade(smsSendInfo)){
                //同一个订单，只发送一次
                this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
                this.logger.debug("同一个订单，只发送一次订单，tid: "+smsSendInfo.getTid()+"type:"+smsSendInfo.getType());
                return false;
            }
			if(smsSendInfo.getFilterOnce()){
				if(this.getOnceDayByBuyer(smsSendInfo)){
					//同一买家一天只发送一次
					this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
					this.logger.debug("同一买家一天只发送一次，tid: "+smsSendInfo.getTid()+"type:"+smsSendInfo.getType());
					return false;
				}
			}
			if(OrderSettingInfo.PAYMENT_SMS_TYPE.contains(smsSendInfo.getType())){ 
				//催付，同一买家一小时内有付过款的不催
				if(smsSendInfo.getFilterHassent()==null){
					smsSendInfo.setFilterHassent(false);
				}
				if(smsSendInfo.getFilterHassent()){
					boolean flag = this.getCountByOneHourPayment(smsSendInfo.getUserId(), smsSendInfo.getNickname());
					if(flag){
						this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
						this.logger.debug("同一买家一小时内有付过款的不催，tid: "+smsSendInfo.getTid()+"type:"+smsSendInfo.getType());
						return false;
					}
				}
			}
			if(OrderSettingInfo.APPRAISE_MONITORING_ORDER.equals(smsSendInfo.getType())){
				//中差评监控   一个手机号码相同内容只能发送3次，不同的短信内容只能发送15次
				String phone = this.isRateMonitoringSend(smsSendInfo.getUserId(), smsSendInfo.getPhone(), smsSendInfo.getInformMobile());
				if(ValidateUtil.isEmpty(phone)){
					this.logger.debug("中差评监控，手机号码获取不正确tid: " + +smsSendInfo.getTid()+" type:"+smsSendInfo.getType() + " 要发送的手机号码："+smsSendInfo.getPhone() + "备选手机号码："+smsSendInfo.getInformMobile());
					return false;
				}
				smsSendInfo.setPhone(phone);
			}
			String type = smsSendInfo.getType();
			smsSendInfo.setCreatedDate(new Date());
			if(!("33".equals(type) ||"34".equals(type) ||"35".equals(type) ||"36".equals(type) ||"99".equals(type))){
			    if(!this.replaceLinkByTradeCenter(smsSendInfo)){
			        this.logger.debug("订单中心短信短链接变量替换失败，tid: "+smsSendInfo.getTid()+" type:"+smsSendInfo.getType());
                    this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
                    return false;
	            }
			}
			int num = this.getActualDeduction(smsSendInfo.getContent());
            int length = 1;
            if(smsSendInfo.getPhone().length()>15){
                length = smsSendInfo.getPhone().split(",").length;
            }
            smsSendInfo.setActualDeduction(num);
			//先扣费，再发短信
			boolean userSmsFlag = this.userAccountService.doUpdateUserSms(smsSendInfo.getUserId(),SendMessageStatusInfo.DEL_SMS, (num*length),
					smsSendInfo.getType(), smsSendInfo.getUserId(), null,OperationLogStatus.USER_OPERATION_SINGLE+"，扣除短信"+(num*length)+"条",UserAccountService.TIME_OUT);
			if(!userSmsFlag){
				this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
				this.logger.debug("用户短信余额不足，扣费失败，tid: "+smsSendInfo.getTid()+" type:"+smsSendInfo.getType());
				return false;
			}
			String sendStatus = SendMessageStatusInfo.SEND_FAIL;
			try {
				if("33".equals(type) ||"34".equals(type) ||"35".equals(type) ||"36".equals(type) ||"99".equals(type) ){
					//如果为营销短信则走新平台
					sendStatus = SendMessageUtil.sendMessage(smsSendInfo.getPhone(),smsSendInfo.getContent(), null, smsSendInfo.getUserId());
					ReturnMessage returnMessage = JsonUtil.fromJson(sendStatus, ReturnMessage.class);
					sendStatus = returnMessage.getReturnCode();
				}else{ 
                    sendStatus = SendMessageUtilForHangYe.sendMessage(smsSendInfo.getPhone(),smsSendInfo.getContent(), null, smsSendInfo.getUserId());
                    ReturnMessage returnMessage = JsonUtil.fromJson(sendStatus, ReturnMessage.class);
                    sendStatus = returnMessage.getReturnCode();
				}
				smsSendInfo.setStatus(SendMessageStatusInfo.SEND_SUCCESS.equals(sendStatus) ? 2 : 1);
			} finally {
				this.saveSendMessageRecord(smsSendInfo, sendStatus);
			}
			if (SendMessageStatusInfo.SEND_SUCCESS.equals(sendStatus)) { 
				// 短信发送成功
				this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
				this.logger.debug("短信发送成功tid： " + smsSendInfo.getTid() + " type:" +smsSendInfo.getType());
				return true;
			}else{//发送短信失败，恢复短信
			    this.shortLinkService.doRemoveById(smsSendInfo.getShortLinkId());
				this.userAccountService.doUpdateUserSms(smsSendInfo.getUserId(),SendMessageStatusInfo.ADD_SMS, (num*length),
						smsSendInfo.getType(), smsSendInfo.getUserId(), null,OperationLogStatus.USER_OPERATION_SINGLE+"，短信发送失败，恢复短信"+(num*length)+"条",UserAccountService.TIME_OUT);
				this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
				this.logger.debug("短信发送失败tid： " + smsSendInfo.getTid() + " type:" +smsSendInfo.getType() +",短信接口返回的状态是："+sendStatus);
				return false;
			}
		} finally {
			long endTime = System.currentTimeMillis();
			if((endTime-startTime)>5000){
				this.logger.debug("短信发送处理时间过长   时间："+(endTime-startTime)+"ms,tid: "+smsSendInfo.getTid()+" ,内容" +smsSendInfo.getContent());
			}
		}
	}
	/**
	 * 替换订单中心中的链接
	 * @author: wy
	 * @time: 2017年10月25日 下午2:54:13
	 * @param smsSendInfo
	 * @return
	 */
	private boolean replaceLinkByTradeCenter(SmsSendInfo smsSendInfo){
	    String oldSms = smsSendInfo.getContent();
	    //替换链接变量
        if(oldSms.contains("链接}")){
            if(smsSendInfo==null ||smsSendInfo.getTid()==null){
                return false;
            }
            String link = this.shortLinkService.getConvertLinkByTmcTradeCenter(smsSendInfo.getUserId(), smsSendInfo.getTid(), 
                    smsSendInfo.getType(),smsSendInfo.getTaskId(),smsSendInfo.getCreatedDate(),smsSendInfo);
            this.logger.debug("替换好的短链是：" + link +" tid:"+smsSendInfo.getTid()+"type:"+smsSendInfo.getType());
            if(link!=null){
                oldSms = oldSms.replaceAll("\\{付款链接\\}", " "+link+" ");
                oldSms = oldSms.replaceAll("\\{物流链接\\}", " "+link+" ");
                oldSms = oldSms.replaceAll("\\{订单链接\\}", " "+link+" ");
                oldSms = oldSms.replaceAll("\\{退款链接\\}", " "+link+" ");
                oldSms = oldSms.replaceAll("\\{评价链接\\}", " "+link+" ");
                oldSms = oldSms.replaceAll("\\{确认收货链接\\}", " "+link+" ");
                smsSendInfo.setContent(oldSms);
            }else{
                return false ;
            }
        }
        return true;
	}
	/**
	 * 查询手机号码相同内容是否发送超过3次，不同的内容是否发送超过20次
	 * @author: wy
	 * @time: 2017年9月8日 下午4:45:44
	 * @param sellerNick 卖家昵称
	 * @param sendPhone 要发送的手机号码
	 * @param phones 手机号码集
	 * @return
	 */
	private String isRateMonitoringSend(String sellerNick,String sendPhone,String phones){
		try {
			String session = this.judgeUserUtil.getUserTokenByRedis(sellerNick);
			if(ValidateUtil.isEmpty(sellerNick) || ValidateUtil.isEmpty(sendPhone) || ValidateUtil.isEmpty(phones)){
				return sendPhone;
			}
			List<Map<String,String>> recordList = this.smsSendInfoService.findNowSendHistory(sellerNick, OrderSettingInfo.APPRAISE_MONITORING_ORDER);
			if(ValidateUtil.isEmpty(recordList)){
				return sendPhone;
			}
			Set<String> phoneSet = new HashSet<String>();
			//不能发送的手机号码集合
			String validatePhone=null,validateContent = null ;
			int phoneCount = 0 , contentCount = 0;
			for (Map<String, String> map : recordList) {
				String newPhone = String.valueOf(map.get("phone"));
				String newContent = String.valueOf(map.get("content"));
				if(validatePhone==null){
					validatePhone = newPhone;
					validateContent = newContent;
					phoneCount = 1;
					contentCount = 1;
					continue ;
				}
				if(validatePhone.equals(newPhone)){
					phoneCount++;
					if(validateContent.equals(newContent)){
						contentCount++;
					}else{
						validateContent = newContent;
						contentCount = 1;
					}
					if(contentCount == OrderSettingInfo.MAX_CONTENT_SAME_COUNT){
						phoneSet.add(validatePhone);
					}
				}else{
					validatePhone = newPhone;
					validateContent = newContent;
					phoneCount = 1;
					contentCount = 1;
				}
				if(phoneCount==OrderSettingInfo.MAX_CONTENT_DIFFERENT_COUNT){
					phoneSet.add(validatePhone);
				}
			}
			if(ValidateUtil.isEmpty(phoneSet)){
				return sendPhone;
			}
			Set<String> newPhoneSet = new HashSet<String>();
			for (String string : phoneSet) {
				if(EncrptAndDecryptClient.isEncryptData(string, EncrptAndDecryptClient.PHONE)){
					string = EncrptAndDecryptClient.getInstance().decrypt(string, EncrptAndDecryptClient.PHONE, session);
				}
				newPhoneSet.add(string);
			}
			if(!newPhoneSet.contains(sendPhone)){
				return sendPhone;
			}
			String[] sellerPhones = phones.split(",");
			if(sellerPhones.length<=0){
				return null;
			}
			String resultPhone = null;
			for (String string : sellerPhones) {
				if(!newPhoneSet.contains(string)){
					resultPhone = string;
					break;
				}
			}
			if(EncrptAndDecryptClient.isEncryptData(resultPhone, EncrptAndDecryptClient.PHONE)){
				resultPhone = EncrptAndDecryptClient.getInstance().decrypt(resultPhone, EncrptAndDecryptClient.PHONE, session);
			}
			return resultPhone;
		} catch (SecretException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 保存发送短信记录
	 * @author: wy
	 * @time: 2017年9月5日 上午11:22:13
	 * @param smsSendInfo 短信记录实体类
	 * @param sendStatus 发送短信状态
	 */
	private void saveSendMessageRecord(SmsSendInfo smsSendInfo,String sendStatus){
		if(sendStatus==null){
			sendStatus="异常";
		}
		SmsRecordDTO srDto = new SmsRecordDTO(); 
		srDto.setRecNum(smsSendInfo.getPhone());
		srDto.setContent(smsSendInfo.getContent());
		srDto.setSendTime(smsSendInfo.getCreatedDate());
		srDto.setSendLongTime(smsSendInfo.getCreatedDate().getTime()); 
		srDto.setReceiverTime(smsSendInfo.getCreatedDate());
		srDto.setType(smsSendInfo.getType());
		srDto.setChannel(smsSendInfo.getChannel());
		srDto.setActualDeduction(smsSendInfo.getActualDeduction());
		srDto.setOrderId(String.valueOf(smsSendInfo.getTid()));
		srDto.setSource("2"); 
		srDto.setResultCode(sendStatus);
		srDto.setStatus(smsSendInfo.getStatus());
		srDto.setBuyerNick(smsSendInfo.getNickname());
		srDto.setTaskId(smsSendInfo.getTaskId());
		srDto.setNickname(smsSendInfo.getNickname());
		String session = this.judgeUserUtil.getUserTokenByRedis(smsSendInfo.getUserId());
		try {
			String buyerNick = srDto.getNickname();
			if(!EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
				if(session == null){
					return ;
				}
				srDto.setNickname(EncrptAndDecryptClient.getInstance().encrypt(buyerNick, EncrptAndDecryptClient.SEARCH, session));
				srDto.setBuyerNick(srDto.getNickname());
			}
			if(!EncrptAndDecryptClient.isEncryptData(srDto.getRecNum(), EncrptAndDecryptClient.PHONE)){
				srDto.setRecNum(EncrptAndDecryptClient.getInstance().encrypt(srDto.getRecNum(), EncrptAndDecryptClient.PHONE, session));
			}
		} catch (SecretException e) {
			return ;
		}
		srDto.setUserId(smsSendInfo.getUserId());
		smsRdService.saveSingleSmsRecord(srDto, smsSendInfo.getUserId());
		//创建保存的临时短信
		this.smsSendInfoService.saveSmsTemporary(smsSendInfo);
	}
	/**
	 * 一个买家一天只发送一次
	 * @author: wy
	 * @time: 2017年9月4日 下午3:45:13
	 * @param smsSendInfo
	 * @return true:已发送过 false：未发送
	 */
	private boolean getOnceDayByBuyer(SmsSendInfo smsSendInfo){
		if(ValidateUtil.isEmpty(smsSendInfo.getUserId()) ||ValidateUtil.isEmpty(smsSendInfo.getNickname())){
			return true;
		}
		long sendFlag = cacheService.setnx(smsSendInfo.getUserId()+"_"+smsSendInfo.getNickname()+"_"+smsSendInfo.getType()+"_smslock", System.currentTimeMillis()+"", 1800L);
		if(sendFlag==0){
			logger.debug("当前短信在redis中已缓存，tid:"+smsSendInfo.getTid()+"type:"+smsSendInfo.getType());
			return true;
		}
		return this.smsSendInfoService.isExists(smsSendInfo.getUserId(),smsSendInfo.getNickname(),smsSendInfo.getType());
	}
	/**
	 * 一次订单只发送一次
	 * @author: wy
	 * @time: 2017年9月4日 下午3:38:15
	 * @param smsSendInfo
	 * @return true:已发送过 false：未发送
	 */
	private boolean getOnceByTrade(SmsSendInfo smsSendInfo){
		if(smsSendInfo.getTid()==null && smsSendInfo.getType()==null){
			return true;
		}
		Long sendFlag = cacheService.setnx(smsSendInfo.getTid()+"_"+smsSendInfo.getType()+"_smslock", System.currentTimeMillis()+"", 900L);
		if(sendFlag==0){
			logger.debug("当前短信在redis中已被缓存，tid:"+smsSendInfo.getTid()+"type:"+smsSendInfo.getType());
			return true;
		}
		return this.smsSendInfoService.isExists(smsSendInfo.getTid(), smsSendInfo.getType());
	}
	/**
	 * 拼凑成短信实体类
	 * @author: wy
	 * @time: 2017年9月4日 下午2:14:56
	 * @param smsContent 要发送的短信内容
	 * @param tmcMessages 
	 * @return 短信
	 */
	private SmsSendInfo getSmsInfo(String smsContent,TmcMessages tmcMessages){
		SmsSendInfo smsSendInfo  = new SmsSendInfo();
		smsSendInfo.setUserId(tmcMessages.getTrade().getSellerNick());
		if(OrderSettingInfo.APPRAISE_MONITORING_ORDER.equals(tmcMessages.getSettingType())){
			//中差评监控  发卖家手机号码
			String sellerPhone = tmcMessages.getTradeSetup().getInformMobile();
			if(ValidateUtil.isEmpty(sellerPhone)){
				return null;
			}
			String[] phones = sellerPhone.split(",");
			if(phones.length<=0){
				return null;
			}
			smsSendInfo.setPhone(phones[0]);
		}else{
			smsSendInfo.setPhone(tmcMessages.getTrade().getReceiverMobile()==null?
					tmcMessages.getTrade().getReceiverPhone():tmcMessages.getTrade().getReceiverMobile());
		}
		smsSendInfo.setNickname(tmcMessages.getTrade().getBuyerNick());
		smsSendInfo.setContent(smsContent);
		smsSendInfo.setType(tmcMessages.getSettingType());
		smsSendInfo.setChannel(tmcMessages.getTrade().getTradeFrom());
		smsSendInfo.setStartSend(tmcMessages.getSendTime());
		smsSendInfo.setEndSend(tmcMessages.getSellerEndDate());
		smsSendInfo.setTid(tmcMessages.getTid());
		smsSendInfo.setTaskId(tmcMessages.getTradeSetup().getId());
		smsSendInfo.setFilterOnce(tmcMessages.getTradeSetup().getFilterOnce());
		smsSendInfo.setFilterHassent(tmcMessages.getTradeSetup().getFilterHassent()==null?
								false:tmcMessages.getTradeSetup().getFilterHassent());
		smsSendInfo.setDelayDate(tmcMessages.getTradeSetup().getTimeOutInform());
		smsSendInfo.setInformMobile(tmcMessages.getTradeSetup().getInformMobile());
		return smsSendInfo;
	}
	/**
	 * 获取具体的短信扣费条数
	 * @author: wy
	 * @time: 2017年9月4日 下午1:55:24
	 * @param content 短信的内容
	 * @return 短信条数
	 */
	private Integer getActualDeduction(String content){
		if(ValidateUtil.isEmpty(content)){
			return 0;
		}
		int messageCount = content.length();
		if (messageCount <= 70) {
			messageCount = 1;
		} else {
			messageCount = (messageCount + 66) / 67;
		}
		return messageCount;
	}
	/**
	 * 拼凑短信内容（不对短链接进行拼凑） //TODO
	 * @author: wy
	 * @time: 2017年9月4日 下午12:13:21
	 * @param oldSms 未拼凑的短信内容
	 * @param trade 订单
	 * @param userInfo 用户的签名
	 * @return 拼凑好的短信内容
	 */
	private String getNewSms(String oldSms,TmcMessages tmcMessages){
		Trade trade = tmcMessages.getTrade();
		UserInfo userInfo = tmcMessages.getUser();
		oldSms =oldSms.replaceAll("\\{下单时间\\}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trade.getCreated()));
		oldSms =oldSms.replaceAll("\\{买家昵称\\}",trade.getBuyerNick());
		oldSms =oldSms.replaceAll("\\{买家姓名\\}", trade.getReceiverName());
		oldSms =oldSms.replaceAll("\\{收货人\\}", trade.getReceiverName());
		oldSms =oldSms.replaceAll("\\{订单编号\\}",String.valueOf(trade.getTid()));
		oldSms =oldSms.replaceAll("\\{订单金额\\}",trade.getPayment());
		oldSms =oldSms.replaceAll("\\{到达城市\\}",trade.getReceiverCity());
		oldSms =oldSms.replaceAll("\\{短信签名\\}", "");
		oldSms =oldSms.replaceAll("\\{退订回N\\}", "退订回N");
		if(oldSms.contains("运单号") || oldSms.contains("物流公司名称")){
			if(OrderSettingInfo.SHIPMENT_TO_REMIND.equals(tmcMessages.getSettingType()) ||
					OrderSettingInfo.ARRIVAL_LOCAL_REMIND.equals(tmcMessages.getSettingType()) ||
					OrderSettingInfo.SEND_GOODS_REMIND.equals(tmcMessages.getSettingType()) ||
					OrderSettingInfo.REMIND_SIGNFOR.equals(tmcMessages.getSettingType()) ||
					OrderSettingInfo.COWRY_CARE.equals(tmcMessages.getSettingType()) ||
					OrderSettingInfo.RETURNED_PAYEMNT.equals(tmcMessages.getSettingType())){
				//发货，到达同城，派件，签收，宝贝关怀，回款提醒
				JSONObject content = tmcMessages.getTmcContent();
				if(content!=null && content.containsKey("company_name")){
					oldSms =oldSms.replaceAll("\\{运单号\\}",content.getString("out_sid"));
					oldSms =oldSms.replaceAll("\\{物流公司名称\\}",content.getString("company_name"));
				}else{
					try {
						oldSms = this.getLogisticsCompany(oldSms, tmcMessages.getTrade());
					} catch (ApiException e) {
						e.printStackTrace();
						oldSms =null;
						this.logger.debug("淘宝获取物流异常：" +oldSms + " 卖家昵称："+tmcMessages.getTrade().getSellerNick() +" tmc:"+content);
					}catch(NullPointerException e){
					    e.printStackTrace();
					    this.logger.debug("物流公司昵称错误 消息：" +oldSms + " 卖家昵称："+tmcMessages.getTrade().getSellerNick() +" tmc:"+content);
					    throw e;
					}
				}
			}
		}
		if(oldSms.contains("到达城市")){
			oldSms =oldSms.replaceAll("\\{到达城市\\}",tmcMessages.getTrade().getReceiverCity());
		}
		judgeItem:if(oldSms.contains("宝贝昵称")){
			logger.info("~~~~~~~~~~~~~~~有宝贝名称的变量替换~~~~~~~~~~~~~~~~~");
			List<Order> orders = trade.getOrders();
			//订单中包含的商品id集合
			List<String> itemIds = null;
			Long itemId = null;
			if(orders != null && !orders.isEmpty()){
				itemIds = new ArrayList<>();
				for (Order order : orders) {
					itemIds.add(order.getNumIid().toString());
				}
			}
			if(itemIds == null || itemIds.isEmpty()){
				logger.info("~~~~~~~~~~~~~~~宝贝名称的变量替换ordersANDItemgIds为空,break~~~~~~~~~~~~~~~~~");
				break judgeItem;
			}
			TradeSetup tradeSetup = tmcMessages.getTradeSetup();
			if(null == tradeSetup.getProductType()){//未指定商品
				itemId = Long.parseLong(itemIds.get(0));
				logger.info("~~~~~~~~~~~~~~~有宝贝名称的变量替换,未指定商品~~~~itemId:" + itemId);
			}else {
				if(tradeSetup.getProductType()){//指定商品发送
					logger.info("~~~~~~~~~~~~~~~有宝贝名称的变量替换，指定商品发送~~~~~~~~~~~~~~~~~");
					String productStr = tradeSetup.getProducts();
					String[] products = productStr.split(",");
					itemIds.retainAll(Arrays.asList(products));
					if(itemIds != null && !itemIds.isEmpty()){
						itemId = Long.parseLong(itemIds.get(0));
					}else{
						itemId = orders.get(0).getNumIid();
					}
				}else {//排除指定商品发送
					logger.info("~~~~~~~~~~~~~~~有宝贝名称的变量替换，指定商品不发送~~~~~~~~~~~~~~~~~");
					String productStr = tradeSetup.getProducts();
					String[] products = productStr.split(",");
					itemIds.removeAll(Arrays.asList(products));
					if(itemIds != null && !itemIds.isEmpty()){
						itemId = Long.parseLong(itemIds.get(0));
					}
				}
			}
			if(itemId == null){
				logger.info("~~~~~~~~~~~~~~~有宝贝名称的变量替换，筛选完成后itemId为空，break~~~~~~~~~~~~~~~~~");
				break judgeItem;
			}
			String subtitle = itemService.findSubtitleById(itemId);
			if(subtitle != null){
				oldSms =oldSms.replaceAll("\\{宝贝昵称\\}", subtitle);
				logger.info("~~~~~~~~~~~~~~~有宝贝名称的变量替换，subtitle不为空，oldSms："+ oldSms +"~~~~~~~~~~~~~~~~~");
			}else {
				for (Order order : orders) {
					
					if((long)itemId == (long)order.getNumIid()){
						String title = order.getTitle();
						if(title != null && !"".equals(title)){
							if(title.length() > 20){
								oldSms =oldSms.replaceAll("\\{宝贝昵称\\}", title.substring(0, 20));
							}else {
								oldSms =oldSms.replaceAll("\\{宝贝昵称\\}", title);
							}
						}
						break;
					}
				}
				logger.info("~~~~~~~~~~~~~~~有宝贝名称的变量替换，subtitle为空，oldSms："+ oldSms +"~~~~~~~~~~~~~~~~~");
			}
		}
		if(!oldSms.startsWith("【")){
			if(ValidateUtil.isEmpty(userInfo.getShopName())){
				oldSms = "【"+userInfo.getTaobaoUserNick()+"】"+oldSms;
			}else{
				oldSms = "【"+userInfo.getShopName()+"】"+oldSms;
			}
		}
		if(!oldSms.endsWith("退订回N")){
			oldSms = oldSms + "退订回N";
		}
		return oldSms;
	}
	/**
	 * 获取物流公司昵称
	 * @author: wy
	 * @time: 2017年9月13日 下午5:59:27
	 * @param str
	 * @param trade
	 * @return
	 * @throws ApiException
	 */
	private String getLogisticsCompany(String str,Trade trade) throws ApiException{
		if(str.contains("物流公司昵称")||str.contains("运单号")||str.contains("物流公司")||str.contains("物流公司名称")){
			if(trade!=null){
				//查询具体的物流订单信息  替换文本中的物流公司昵称
				TaobaoClient client = TaoBaoClientUtil.TAOBAO_CLIENT;
				LogisticsTraceSearchRequest req = new LogisticsTraceSearchRequest();
				req.setTid(trade.getTid());
				req.setSellerNick(trade.getSellerNick());
				LogisticsTraceSearchResponse rsp = client.execute(req);
				String result = rsp.getBody();
				if(result.contains("当前操作的订单是拆单订单，拆单标记和子订单列表都必须传递")){
				    String logisticsCompany = "";
				    List<Order> list = trade.getOrders();
				    if(ValidateUtil.isNotNull(list)){
				        for (Order order : list) {
                            String orderLogisticsCompany = order.getLogisticsCompany();
                            if(ValidateUtil.isNotNull(orderLogisticsCompany)){
                                logisticsCompany = orderLogisticsCompany;
                                break;
                            }
                        }
				    }
					str = str.replaceAll("\\{物流公司名称\\}", logisticsCompany);
					str = str.replaceAll("\\{运单号\\}", "");
					logger.debug("tid:"+trade.getTid()+"  拆单订单  "+str);
				}
				else if(result.startsWith("{\"logistics_trace_search_response")){
				    String companyName = rsp.getCompanyName();
				    if(ValidateUtil.isEmpty(companyName)){
				        this.logger.debug("物流公司名称获取失败，请求api的结果为："+result +"，要替换的内容为："+str + ",tid: "+ trade.getTid() + " 卖家昵称："+trade.getSellerNick());
				        return null;
				    }
				    this.logger.debug("卖家发货，物流公司名称：" + rsp.getCompanyName());
					str = str.replaceAll("\\{物流公司名称\\}", rsp.getCompanyName());
					str = str.replaceAll("\\{运单号\\}", rsp.getOutSid());
				} else{
					logger.debug("tid:"+trade.getTid()+"物流消息查询失败\r"+rsp.getBody());
				}
			}
		}else{
			logger.debug("tid:"+trade.getTid()+"  未发现要拼凑的短信变量");
		}
		return str;
	}
	/**
	 * 查询一小时内是否有付过款的订单
	 * @author: wy
	 * @time: 2017年7月26日 上午11:15:07
	 * @param sellerNick 卖家昵称
	 * @param buyerNick 买家昵称
	 * @return true 代表已付过款， false 代表一小时内未付过款
	 * @throws SecretException 加解密异常
	 */
	private boolean getCountByOneHourPayment(String sellerNick,String buyerNick){
        try {
			if(sellerNick!=null&&buyerNick!=null){
				String sessionKey = this.judgeUserUtil.getUserTokenByRedis(sellerNick);
				Date startTime = null;
				Date endTime = new Date();
				Calendar cal = Calendar.getInstance();
			    cal.setTime(endTime);
			    cal.add(Calendar.HOUR_OF_DAY, -1);
			    startTime = cal.getTime();
			    if(!EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
					if(sessionKey == null){
						return true;
					}
					buyerNick = EncrptAndDecryptClient.getInstance().encrypt(buyerNick, EncrptAndDecryptClient.SEARCH, sessionKey);
				}
			    Map<String,Object> findmap = new HashMap<String, Object>(5);
			    findmap.put("bTime", startTime);
			    findmap.put("eTime",endTime);
			    findmap.put("sellerNick",sellerNick);
			    findmap.put("buyerNick",buyerNick);
			    List<Map<String,Object>> mapList = this.myBatisDaoT.findList(TbTransactionOrder.class.getName(), "findTradePayTime", findmap);
			    if(mapList!=null && mapList.size()>0){
			    	for (Map<String, Object> map : mapList) {
			        	try {
			        		String buyerNickMap = String.valueOf(map.get("buyerNick"));
							if(buyerNick.equals(buyerNickMap)){
								String status = String.valueOf(map.get("status"));
								if(TradesInfo.SELLER_CONSIGNED_PART.equals(status)||TradesInfo.WAIT_SELLER_SEND_GOODS.equals(status) ||TradesInfo.WAIT_BUYER_CONFIRM_GOODS.equals(status)){
									//三个状态 代表付款一小时
									String tid = String.valueOf(map.get("tid"));
									boolean flag = this.transactionOrderService.queryTradePaymentTime(Long.parseLong(tid), startTime, endTime);
									if(flag){
										return true;
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			    }
			}
			return false;
		} catch (SecretException e) {
			e.printStackTrace();
			return false;
		}
	}
}
