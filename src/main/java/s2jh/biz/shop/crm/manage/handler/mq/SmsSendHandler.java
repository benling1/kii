package s2jh.biz.shop.crm.manage.handler.mq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;
import lab.s2jh.core.mq.JmsProducer;
import lab.s2jh.core.mq.artifact.SmsJobArtifact;
import lab.s2jh.core.mq.artifact.SmsRecordJobArtifact;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.taobao.info.ReturnMessage;
import s2jh.biz.shop.crm.taobao.util.SendMessageUtil;
import s2jh.biz.shop.utils.JsonUtil;
import s2jh.biz.shop.utils.SpringContextUtil;

/**
 * 类名称：TmcSendHandler <br/>
 * 类描述：Send TMC <br/>
 * 创建时间：2017年03月08日 下午7:17:10 <br/>
 * @author zlp
 * @version V1.0
 */
@Service("smsSendHandler")
public class SmsSendHandler implements Handler {
	
	private static final Logger log = Logger.getLogger(SmsSendHandler.class);

	public void doHandle(@SuppressWarnings("rawtypes") Map map)
			throws HandlerException {
		log.debug("#arrive smsSendHandler#");
		SmsJobArtifact ja = (SmsJobArtifact) map.get(SmsJobArtifact.class.getSimpleName());
		if (ja != null) {
		   String phone = ja.getPhones().toString().replace("[", "").replace("]", "").replace(" ", "");
		   String result = SendMessageUtil.sendMessage(phone,ja.getMessage(), null, ja.getUserId());
		   ReturnMessage returnMessage = JsonUtil.fromJson(result, ReturnMessage.class);
		   String status = returnMessage.getReturnCode();
		   log.info(status+"短信平台状态！");
		   JmsProducer producer = (JmsProducer) SpringContextUtil.getBean("jmsSmsRecordProducer");
		   SmsRecordJobArtifact  smsRecordJobArtifact  = new SmsRecordJobArtifact("批量保存短信记录",ja.getType());
		   smsRecordJobArtifact.setMessage(ja.getMessage());
		   smsRecordJobArtifact.setPhones(ja.getPhones()); 
		   smsRecordJobArtifact.setMsgId(ja.getMsgId());  
		   smsRecordJobArtifact.setTid(ja.getTid()); 
		   smsRecordJobArtifact.setUserId(ja.getUserId()); 
		   smsRecordJobArtifact.setType(ja.getType()); 
		   smsRecordJobArtifact.setStatus(status);
		   smsRecordJobArtifact.setChannel(ja.getChannel()); 
		   smsRecordJobArtifact.setAutograph(ja.getAutograph()); 
		   smsRecordJobArtifact.setActualDeduction(ja.getActualDeduction()); 
		   smsRecordJobArtifact.setIp(ja.getIp()); 
		   producer.send(smsRecordJobArtifact);
		    
		}
	}
}
