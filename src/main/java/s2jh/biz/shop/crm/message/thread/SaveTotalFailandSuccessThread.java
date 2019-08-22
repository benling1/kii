package s2jh.biz.shop.crm.message.thread;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;

public class SaveTotalFailandSuccessThread implements Callable<Map<String,Object>> {
	
	private static Logger logger = LoggerFactory.getLogger(OrderSendThread.class);
	
//	private SmsSendRecordService sendRecordService;
	private MsgSendRecordService msgSendRecordService;
	private String content;// 短信内容
	private String userId;// 用户昵称
	private Long msgId;//短信记录表的id
	private String type;
	private String types;//短信发送模式
	
	
	
//	public SaveTotalFailandSuccessThread(SmsSendRecordService sendRecordService,
//			MsgSendRecordService msgSendRecordService, String content, String userId, Long msgId, String type,
//			String types) {
	public SaveTotalFailandSuccessThread(MsgSendRecordService msgSendRecordService, 
			String content, String userId, Long msgId, String type,
			String types) {
		super();
//		this.msgSendRecordService = sendRecordService;
		this.msgSendRecordService = msgSendRecordService;
		this.content = content;
		this.userId = userId;
		this.msgId = msgId;
		this.type = type;
		this.types = types;
	}



	@Override
	public Map<String, Object> call() throws Exception {
		Integer successCustom;//发送成功的短信记录数
		Integer totalCustom;//目标发送客户的短信总记录数
		Integer failCustom;//发送失败的短信总记录数
		MsgSendRecord msgSendRecord;//查询MsgSendRecord
		Set<Long> ssi = new HashSet<Long>();
		Iterator its = ssi.iterator();
		while (its.hasNext()) {
			SmsSendInfo smsSendInfo = (SmsSendInfo) its.next();
			//如果用户userId、短信内容content、msgId为null则保存
			if(userId == null && content == null && msgId == null){
				userId = smsSendInfo.getUserId();
				content = smsSendInfo.getContent();
				msgId = smsSendInfo.getMsgId();
			}
			//如果用户userId、短信内容content、msgId不为null则保存
			if (userId != null && content != null && msgId != null
					&& userId.equals(smsSendInfo.getUserId())
					&& content.equals(smsSendInfo.getContent())
					&& types.contains(smsSendInfo.getType())) {
				ssi.add(msgId);
			}
			for(@SuppressWarnings("unused") Long msg : ssi){
				msg = msgId;
				//查询短信是否发送成功
				//msgId
				successCustom = null;//发送成功的短信记录数
				totalCustom = null;//目标发送客户的短信总记录数
				failCustom = null;//发送失败的短信总记录数
				msgSendRecord = null;//查询MsgSendRecord
				if(msgId != null){
					//发送成功的短信记录数
					successCustom = msgSendRecordService.getSuccessCustomByMsgId(userId, msgId, types);
					//目标发送客户的短信总记录数
					totalCustom = msgSendRecordService.getTotalCustomByMsgId(userId, msgId, types);
					//发送失败的短信总记录数
					failCustom = totalCustom - successCustom;
					//查询MsgSendRecord
					msgSendRecord = msgSendRecordService.findOne(msgId);
					
				}
//				MsgSendRecord msgSendRecord = null;
				if(failCustom != null && successCustom != null && msgSendRecord.getStatus() != null){
					//更新总记录表crm_msgrecord添加成功总条数和失败总条数
					msgSendRecord = new MsgSendRecord();
					msgSendRecord.setFailedCount(failCustom);
					msgSendRecord.setSucceedCount(successCustom);
					msgSendRecord.setStatus(msgSendRecord.getStatus());
					msgSendRecord.setIsSent(true);
					msgSendRecord.setId(msgId);
				}
				if(msgSendRecord != null){
					//更新数据
					msgSendRecordService.updateMsgRecordByMsgId(msgSendRecord);
				}
			}
		}
		return null;
	}

}
