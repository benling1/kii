package s2jh.biz.shop.crm.manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.vo.MemberCriteriaVo;
import s2jh.biz.shop.crm.manage.vo.SendMsgVo;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.support.service.MultithreadBatchSmsService;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.phoneRegExp.PhoneRegUtils;

@Service
public class BatchSendMsgHelper {
	
//	private static final Logger loger = Logger.getLogger(BatchSendMsgHelper.class);
//
//	@Autowired
//	private VipMemberService vipMemberService;
//	
//	@Autowired
//	private MsgSendRecordService  msgSendRecordService;
//	
//	@Autowired
//	private SmsSendRecordService smsSendRecordService;
//	
//	
//	@Autowired
//	private MobileSettingService mobileSettingService;
//	
//	@Autowired
//	private MultithreadBatchSmsService multithreadService;
//	
//		
//	public void syncSendMsg(final SendMsgVo sendMsgVo,final MemberCriteriaVo memberCriteriaVo) {
//			
//		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
//			@SuppressWarnings("unchecked")
//			@Override
//			public void run() {
//			    List<MemberDTO> memberList = vipMemberService.findMemberList(memberCriteriaVo, sendMsgVo.getUserId());
//			    List<String> numList = new ArrayList<String>();
//			    for (MemberDTO memberDTO : memberList) {
//				 numList.add(memberDTO.getPhone() );
//			    }
//			     
//				Map<String,Object> dMap = sortData(numList);
//				List<String> wrongNums = null;
//				List<String> repeatNums = null;
//				String status = null;
//				String[] numStr = null;
//				int errorNum =0,successNum =0;
//				if(dMap.get("wrongNums") !=null){
//					wrongNums = (ArrayList<String>)dMap.get("wrongNums");
//				}
//				if(dMap.get("repeatNums") !=null){
//					repeatNums = (ArrayList<String>)dMap.get("repeatNums");
//				}
//				
//				long s1 = System.currentTimeMillis();
//				if(dMap.get("numStr") !=null && ((String[])dMap.get("numStr")).length>0){
//					numStr = (String[])dMap.get("numStr");
//				    batchSendMsg(numStr,sendMsgVo.getContent(),sendMsgVo.getMsgType(),sendMsgVo.getAutograph(),sendMsgVo.getUserId(),sendMsgVo.getMsgId(),sendMsgVo.getIpAddress());
//					loger.info("会员短信群发:批量短信发送时间开销:"+( System.currentTimeMillis()-s1)+" millis @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//				}
//				if(wrongNums != null && wrongNums.size()>0){
//					errorNum+=wrongNums.size();
//					smsSendRecordService.saveErrorMsgNums(wrongNums,sendMsgVo,
//							MsgType.SMS_STATUS_WRONGNUM,new Date());
//				}
//				//保存手机号重复的(异步)
//				if(repeatNums != null && repeatNums.size()>0){
//					errorNum+=repeatNums.size();
//					smsSendRecordService.saveErrorMsgNums(wrongNums,sendMsgVo,
//							MsgType.SMS_STATUS_REPEATNUM,new Date());
//				}
//				//更新总记录
//				if(successNum==memberList.size()){
//					status = MsgType.MSG_STATUS_ALLSUCCEED;
//				}else if(errorNum==memberList.size()){
//					status = MsgType.MSG_STATUS_ALLFAILED;
//				}else if(successNum>0 && successNum<memberList.size()){
//					status = MsgType.MSG_STATUS_PARTSUCCEED;
//				}else{
//					status = MsgType.MSG_STATUS_PARTSUCCEED;
//				}
//				
//				updateMsg(sendMsgVo.getMsgId(),sendMsgVo.getUserId(),status,successNum,errorNum); 
//				mobileSettingService.sendSmsCountRemind(sendMsgVo.getUserId());
//			}
//		});
//		
//	}
//	
//	public Map<String,Integer> batchSendMsg(String [] phones,String content,String type,
//			String autograph,String userId,long msgId , String ip) {
//		BatchSmsData  batchSmsData = new BatchSmsData(phones);
//		batchSmsData.setType(type);
//		batchSmsData.setIpAdd(ip);
//		batchSmsData.setChannel(autograph);
//		batchSmsData.setAutograph(autograph);
//		batchSmsData.setUserId(userId);
//		batchSmsData.setContent(content);
//		batchSmsData.setMsgId(msgId);
//		multithreadService.batchOperateSms(batchSmsData);
//		int sNum = batchSmsData.getSuccess();
//		int fNum = batchSmsData.getFail();
//		Map<String,Integer> resultMap =null;
//		if(sNum!=0 || fNum!=0){
//			resultMap = new HashMap<String,Integer>();
//			resultMap.put("succeedNum", sNum);
//			resultMap.put("failedNum", fNum);
//		}
//		return resultMap;
//	}
//
//	
//	private void updateMsg(long msgId, String userId, String status, int successNum, int errorNum) {
//		MsgSendRecord msg = new MsgSendRecord();
//		msg.setId(msgId);
//		msg.setUserId(userId);
//		msg.setStatus(status);
//		msg.setSucceedCount(successNum);
//		msg.setFailedCount(errorNum);
//		msgSendRecordService.updateMsg(msg);
//	}
//	
//	private Map<String, Object> sortData(List<String> mList) {
//		Map<String,Object> hashMap = new HashMap<String,Object>();
//		String str = null;
//		List<String> wrongNums = new ArrayList<String>();
//		Set<String> sendSet = new HashSet<String>();
//		Iterator<String> iterator = mList.iterator();
//		long h1 = System.currentTimeMillis();
//		//校验会员手机号--订单抽取出的会员信息手机号有的会有问题,需要进行正则校验!
//		while(iterator.hasNext()){
//			str = iterator.next();
//			if(str==null || !PhoneRegUtils.phoneValidate(str)){
//				wrongNums.add(str);
//				iterator.remove();
//			}else{
//				sendSet.add(str);
//			}
//		}
//		long h2 = System.currentTimeMillis();
//		loger.info("会员短信群发:批量短信发送筛选出错误手机号时间开销:"+(h2-h1)+" millis @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		if(mList.size()>0 && sendSet.size()>0 ){
//			for (String n : sendSet) {
//				mList.remove(n);
//			}
//		}
//		long h3 = System.currentTimeMillis();
//		loger.info("会员短信群发:批量短信发送筛选出重复手机号时间开销:"+(h3-h2)+" millis @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@3.2");
//		if(wrongNums.size()>0){
//			hashMap.put("wrongNums", wrongNums);
//		}if(sendSet.size()>0){
//			/*
//			 * 此返回参数针对后期个性短信替换
//			 * hashMap.put("sendNums", new ArrayList<String>(sendSet));*/
//			hashMap.put("numStr", sendSet.toArray(new String[0]));
//		}
//		if(mList.size()>0){
//			hashMap.put("repeatNums", mList);
//		}
//		return hashMap;
//	}
	
}
