package s2jh.biz.shop.crm.manage.handler.mq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;
import lab.s2jh.core.mq.artifact.SmsRecordJobArtifact;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.entity.SmsSendRecord;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.taobao.info.OperationLogStatus;
import s2jh.biz.shop.crm.taobao.info.SendMessageStatusInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;

/**
 * 类名称：SmsRecordHandler <br/>
 * 类描述：异步保存短信记录 <br/>
 * 创建时间：2017年03月08日 下午7:17:10 <br/>
 * @author zlp
 * @version V1.0
 */
@Service("smsRecordHandler")
public class SmsRecordHandler implements Handler {
	
	private static final Logger log = Logger.getLogger(SmsRecordHandler.class);
	
	@Autowired
	private  SmsRecordService smsRecordService;
	@Autowired
	private  VipMemberService vipMemberService;
	@Resource
	private MyBatisDao myBatisDao;
	
	@Autowired
	private MsgSendRecordService  msgSendRecordService;
	@Autowired
	private UserInfoService userInfoService;
	
	//声明对象锁
	private  Object lock = new Object();
	
	private static final Log logger = LogFactory.getLog(SmsRecordHandler.class);
	
	
	
	
//	public void doHandle(@SuppressWarnings("rawtypes") Map map)
//			throws HandlerException {
////		log.debug("#arrive smsRecordHandler#");
//		SmsRecordJobArtifact ja = (SmsRecordJobArtifact) map.get(SmsRecordJobArtifact.class.getSimpleName());
//		if (ja != null) {
//		  List<SmsRecordDTO>	smsSendRecordList = new ArrayList<SmsRecordDTO>();
//		  SmsRecordDTO  smsSendRecord = null;
//		  MemberDTO memberInfo = null;
//		   log.info("保存短信记录"+ja.getMessage());
//		   Date date = new Date();
//		   for (String  phone : ja.getPhones()) {
//			   smsSendRecord = new SmsRecordDTO();
//			   smsSendRecord.setContent(ja.getMessage());
//			   smsSendRecord.setSendTime(date);
//			   smsSendRecord.setReceiverTime(date);
//			   smsSendRecord.setType(ja.getType());
//			   smsSendRecord.setChannel(ja.getChannel());
//			   smsSendRecord.setActualDeduction(ja.getActualDeduction());
//			   smsSendRecord.setResultCode(ja.getStatus());
//			   smsSendRecord.setOrderId(ja.getTid()==null?null:ja.getTid());
//			   smsSendRecord.setMsgId(ja.getMsgId()==null?null:ja.getMsgId());
//			   
//			   smsSendRecord.setStatus("100".equals(ja.getStatus())?SendMessageStatusInfo.RECORDSUCESS:SendMessageStatusInfo.RECORDFAIL);
//			   smsSendRecord.setAutograph(ja.getAutograph());
//			   smsSendRecord.setUserId(ja.getUserId());
//			   smsSendRecord.setRecNum(phone);
//		       smsSendRecordList.add(smsSendRecord);
//		       
//		       if(MsgType.MSG_HYDXQF.equals(ja.getType())){
//		    	   memberInfo = new MemberDTO();
//		    	   if(null!=ja.getUserId()&&!"".equals(ja.getUserId())&&null!=phone&&!"".equals(phone)){
//		    		   memberInfo.setPhone(phone);
//		    		   memberInfo.setUserId(ja.getUserId());
//		    		   memberInfo.setMsgId(ja.getMsgId());
//		    		   memberInfo.setLastSendSmsTime(date.getTime()); 
//		    		   vipMemberService.updateMemberInfoByParam(memberInfo,2);
//		    	   }
//		       }
//		    }
//		    smsRecordService.saveSmsRecord(smsSendRecordList, ja.getUserId());
//		    if(null!=ja.getStatus()&&!"100".equals(ja.getStatus())){
//		    	//保存总表记录
////		    	MsgSendRecord msg = new MsgSendRecord();
////				msg.setId(ja.getMsgId());
////				msg.setUserId(ja.getUserId());
////				msg.setFailedCount(errorNum);
////				msgSendRecordService.updateMsg(msg);
//		    }
//		    
//		}
//	}
	
	
//	public void doHandle(@SuppressWarnings("rawtypes") Map map)
//			throws HandlerException {
////		log.debug("#arrive smsRecordHandler#");
//		SmsRecordJobArtifact ja = (SmsRecordJobArtifact) map.get(SmsRecordJobArtifact.class.getSimpleName());
//		if (ja != null) {
//		  List<SmsSendRecord>	smsSendRecordList = new ArrayList<SmsSendRecord>();
//		  SmsSendRecord  smsSendRecord = null;
//		  MemberDTO memberInfo = null;
//		   String status = ja.getStatus();
//		   String message = ja.getMessage();
//		   String type = ja.getType();
//		   log.info("保存短信记录"+message);
//		   Date date = new Date();
//		   for (String  phone : ja.getPhones()) {
//			    smsSendRecord = new SmsSendRecord();
//				smsSendRecord.setContent(message);
//				smsSendRecord.setSendTime(date);
//				smsSendRecord.setReceiverTime(date);
//				smsSendRecord.setType(type);
//				smsSendRecord.setChannel(ja.getChannel());
//				smsSendRecord.setActualDeduction(ja.getActualDeduction());
//			    smsSendRecord.setResultCode(status);
//			    smsSendRecord.setOrderId(ja.getTid()==null?null:ja.getTid());
//			    smsSendRecord.setMsgId(ja.getMsgId()==null?null:ja.getMsgId());
//			    smsSendRecord.setStatus("100".equals(status)?SendMessageStatusInfo.RECORDSUCESS:SendMessageStatusInfo.RECORDFAIL);
//				smsSendRecord.setAutograph(ja.getAutograph());
//				smsSendRecord.setUserId(ja.getUserId());
//				smsSendRecord.setRecNum(phone);
//				smsSendRecordList.add(smsSendRecord);
//		       
//		        if("100".equals(status)){
//		    	   memberInfo = new MemberDTO();
//		    	   if(null!=ja.getUserId()&&!"".equals(ja.getUserId())&&null!=phone&&!"".equals(phone)){
//		    		   memberInfo.setPhone(phone);
//		    		   memberInfo.setUserId(ja.getUserId());
////		    		   memberInfo.setMsgId(ja.getMsgId());
//		    		   memberInfo.setLastSendSmsTime(date.getTime()); 
//		    		   vipMemberService.updateMemberInfoByParam(memberInfo,2);
//		    	   }
//		        }
//		    }
////		   	TRY {
////			THREAD.SLEEP(500);
////			} CATCH (INTERRUPTEDEXCEPTION E) {
////			}
//		    logger.info("保存  record 表记录！队列");
//		    myBatisDao.execute(SmsSendRecord.class.getName(), "insertbatchRecord", smsSendRecordList);
//		   	if(!"100".equals(status) ){
//		   		logger.info("失败恢复条数！");
//		   		synchronized(this.lock){
//		   		   resumeUserSms(ja);
//		   		MsgSendRecord msgRecord = new MsgSendRecord();
//		   		msgRecord.setFailedCount(ja.getPhones().size());
//		   		msgRecord.setId(ja.getMsgId());
//		   		msgSendRecordService.updateMsgRecordById(msgRecord);
//		   		}
//		   	}else{
//		   		if(null!=smsSendRecordList&&smsSendRecordList.size()>0){
//		   			myBatisDao.execute(SmsSendRecord.class.getName(), "insertbatchRecord", smsSendRecordList);
//		   			batchHandleSmsInfoData(smsSendRecordList);
//		   		}
//		   	}
//		    
//		}
//	}
	
//	 @Transactional(propagation=Propagation.REQUIRES_NEW)
//	private boolean  resumeUserSms( SmsRecordJobArtifact ja) {
//    	boolean result = false;
//		try {
//			result  = userInfoService.updateUserSms(ja.getUserId(), SendMessageStatusInfo.ADD_SMS, 
//					ja.getPhones().size()*ja.getActualDeduction(),ja.getType(),ja.getUserId(),ja.getIp(),
//														OperationLogStatus.USER_OPERATION_MORE);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("短信内容"+ja.getMessage()+"恢复"+ja.getUserId()+"发送条数"+ja.getPhones().size()+"失败"+e.getMessage());
//		}
//		return  result;
//	}
//	
//    public void  batchHandleSmsInfoData(List<SmsSendRecord> smsSendRecordList){
//		logger.info("保存  info 表记录！ ");
//		List<SmsSendInfo> smsSendInfoList = new ArrayList<SmsSendInfo>();
//		for (SmsSendRecord smsSendRecord : smsSendRecordList) {
//			SmsSendInfo  smsSendInfo = new SmsSendInfo();
//			smsSendInfo.setContent(smsSendRecord.getContent());
//			smsSendInfo.setSendTime(smsSendRecord.getSendTime());
//			smsSendInfo.setType(smsSendRecord.getType());
//			smsSendInfo.setChannel(smsSendRecord.getChannel());
//			if(null!=smsSendRecord.getOrderId()&&!"".equals(smsSendRecord.getOrderId())){
//				smsSendInfo.setTid(Long.valueOf(smsSendRecord.getOrderId()));
//			}
//			smsSendInfo.setActualDeduction(smsSendRecord.getActualDeduction());
//		    smsSendInfo.setStatus(smsSendRecord.getStatus()==2?SendMessageStatusInfo.INFOSUCCESS:SendMessageStatusInfo.INFOFAIL);
//			smsSendInfo.setAutograph(smsSendRecord.getAutograph());
//			smsSendInfo.setUserId(smsSendRecord.getUserId());
//			smsSendInfo.setPhone(smsSendRecord.getRecNum());
//			smsSendInfo.setRecordId(smsSendRecord.getId());
//			smsSendInfoList.add(smsSendInfo);
//		}
//		
//		if(null!=smsSendInfoList&&smsSendInfoList.size()>0){
//			logger.info("保存  info 表记录！ size"+smsSendInfoList.size());
////			myBatisDao.execute(SmsSendInfo.class.getName(), "insertbatchSmsInfo", smsSendInfoList);
//		}
//		logger.info("保存  info 表记录！结束 ");
//	}

	@Override
	public void doHandle(Map map) throws HandlerException {
		
	}
}
