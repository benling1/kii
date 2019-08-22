/** 
 * Project Name:s2jh4net 
 * File Name:TestClass.java 
 * Package Name:s2jh.biz.shop.crm.taobao.util 
 * Date:2017年3月18日下午3:25:55 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
  
package s2jh.biz.shop.crm.manage.service;  

import org.springframework.stereotype.Service;

/** 
 * MultithreadService <br/> 
 * Date:     2017年3月18日 下午3:25:55 <br/> 
 * @author   zlp
 * @version   1.0
 */
@Service
public class BatchOperateSmsService {
 
//	@Resource
//	private MyBatisDao myBatisDao;
//	
//	@Autowired
//	private UserInfoService userInfoService;
//	
//	private static final Log logger = LogFactory.getLog(BatchOperateSmsService.class);
//
//    /**
//     * 
//     * BatchSmsData 调用前请赋值  手机号数组 String data[]  存入手机号
//     * 						 userId 
//     *                       短信签名autograph
//     *                       短信内容 content
//     *                       依次赋值
//     *  批量发短信主方法
//     * @author zlp
//     * @param obj
//     */
//    public void batchOperateSms(final BatchSmsData obj) {
//    	 int isBigData = judgeIsBigData(obj);
//    	 logger.info("数据判断"+isBigData);
//		 if(2==isBigData){
//			 batchHandleSmallData(obj);
//		 }else if(3==isBigData){
//			 batchHandleBigData(obj);
//		 }
//		 logger.info("批量发短信end");
//	}
//    
//    private void batchHandleBigData(final BatchSmsData obj) {
//		logger.info("开始扣费！大数据");
//		boolean result = deductionUserSms(obj);
//		if(result){
//		    logger.info("扣费成功！"+obj.getUserId()+obj.getTotal());
//		    
//		    String[] datas = obj.getDatas();
//			List<String> phoneList = new ArrayList<String>();
//			SmsJobArtifact smsJobArtifact = new SmsJobArtifact("批量发短信",obj.getType());
//			
//			smsJobArtifact.setMessage(obj.getContent());
//			smsJobArtifact.setMsgId(obj.getMsgId()); 
//			smsJobArtifact.setTid(obj.getTid()); 
//			smsJobArtifact.setUserId(obj.getUserId()); 
//			smsJobArtifact.setType(obj.getType()); 
//			smsJobArtifact.setIp(obj.getIpAdd()); 
//			
//			for (int i = 0; i < datas.length; i++) {
//				if(i!=0&&i%500==0){
//					 smsJobArtifact.setPhones(phoneList);
//					 JmsProducer producer = (JmsProducer) SpringContextUtil.getBean("jmsMemberSMSProducer");
//					 producer.send(smsJobArtifact);
//					 phoneList = new ArrayList<String>();
//					 smsJobArtifact = new SmsJobArtifact("批量发短信",obj.getType());
//					 smsJobArtifact.setMessage(obj.getContent());
//					 smsJobArtifact.setMsgId(obj.getMsgId()); 
//					 smsJobArtifact.setTid(obj.getTid()); 
//					 smsJobArtifact.setUserId(obj.getUserId()); 
//					 smsJobArtifact.setType(obj.getType()); 
//					 smsJobArtifact.setIp(obj.getIpAdd()); 
//					 smsJobArtifact.setChannel(obj.getChannel()); 
//					 smsJobArtifact.setAutograph(obj.getAutograph()); 
//					 smsJobArtifact.setActualDeduction(obj.getActualDeduction()); 
//				}
//				phoneList.add(datas[i]);
//			}
//			if(null!=phoneList&&phoneList.size()>0){
//				 smsJobArtifact.setPhones(phoneList);
//				 JmsProducer producer = (JmsProducer) SpringContextUtil.getBean("jmsMemberSMSProducer");
//				 producer.send(smsJobArtifact);
//			}
//			
//	    }else{
//		    logger.info("扣费失败！"+obj.getUserId()+obj.getTotal()); 
//	    }
//	}
//	
//    
//    
//    
//    
//    
//    
//    private void batchHandleSmallData(BatchSmsData obj) {
//    	logger.info("开始扣费！小数据");
//	 	boolean result = deductionUserSms(obj);
//	    if(result){
//	    	unifySendSmsData(obj,obj.getDatas());
//	    	handleLogicAfterSendSms(obj,false);
//	    }
//	}
//    
//    private void unifySendSmsData(final BatchSmsData obj, String[] data) {
//	    String s = Arrays.toString(data).replace("[", "").replace("]", "").replace(" ", "");
//		Map<String,Object>  linkedMap =  new LinkedHashMap<String, Object>();
//		String result = SendMessageUtil.sendMessage(s,obj.getContent(), null, null);
//		ReturnMessage returnMessage = JsonUtil.fromJson(result, ReturnMessage.class);
//		String status = returnMessage.getReturnCode();
//		if("100".equals(status)){
//			obj.setSuccess(obj.getSuccess()+data.length);
//		    linkedMap.put(status, Arrays.asList(data));
//		 	obj.getSuccessData().add(linkedMap);
//		}else{
//			obj.setFail(obj.getFail()+data.length);
//			linkedMap.put(status, Arrays.asList(data));
//			obj.getFailData().add(linkedMap);
//		}
//    }
//    
//    public void handleLogicAfterSendSms( BatchSmsData obj,boolean isASync){
//		logger.info("isASync！"+isASync+"失败数量"+obj.getFail());
//		if(obj.getFail()!=0){
//			 resumeUserSms(obj);
//		}
//		logger.info("同步处理");
//		batchHandleResultData(obj);
//	}
//  
//    public void  batchHandleResultData(final BatchSmsData obj){
//		if(obj.getFailData().size()>0){
//			logger.info("保存失败短信！");
//			batchHandleRecord(obj,1);
//		}
//		if(obj.getSuccessData().size()>0){
//			logger.info("保存成功短信！");
//			batchHandleRecord(obj,2);
//		}
//	}
//    
//    
//    @Transactional(propagation=Propagation.REQUIRES_NEW)
//	public List<SmsSendRecord> batchHandleRecord(final BatchSmsData obj,int type){
//		logger.info("type 2 是保存  info 表记录！ "+type);
//		List<SmsSendRecord> smsSendRecordList =null;
//		logger.info("保存所有发送的短信");
//		try {
//			List<Map<String, Object>> failData = type==1?obj.getFailData():obj.getSuccessData();
//			for (Map<String, Object> map : failData) {
//				smsSendRecordList = new ArrayList<SmsSendRecord>();
//				Set<String> keySet = map.keySet();
//				for (String key : keySet) {
//					@SuppressWarnings("rawtypes")
//					List list = (List)map.get(key);
//					SmsSendRecord  smsSendRecord  = null;
//					for (Object object : list) {
//						smsSendRecord = new SmsSendRecord();
//						Date date = new Date();
//						smsSendRecord.setContent(obj.getContent());
//						smsSendRecord.setSendTime(date);
//						smsSendRecord.setReceiverTime(date);
//						smsSendRecord.setType(obj.getType());
//						smsSendRecord.setChannel(obj.getChannel());
//						smsSendRecord.setActualDeduction(obj.getActualDeduction());
//					    smsSendRecord.setResultCode(key);
//					    smsSendRecord.setOrderId(obj.getTid()==null?null:obj.getTid());
//					    smsSendRecord.setMsgId(obj.getMsgId()==null?null:obj.getMsgId());
//					    
//					    smsSendRecord.setStatus("100".equals(key)?SendMessageStatusInfo.RECORDSUCESS:SendMessageStatusInfo.RECORDFAIL);
//						smsSendRecord.setAutograph(obj.getAutograph());
//						smsSendRecord.setUserId(obj.getUserId());
//						smsSendRecord.setRecNum(object.toString());
//						smsSendRecordList.add(smsSendRecord);
//					}
//				}
//				if(null!=smsSendRecordList&&smsSendRecordList.size()>0){
//					Thread.sleep(500);
//					logger.info("保存  record 表记录！");
//					  myBatisDao.execute(SmsSendRecord.class.getName(), "insertbatchRecord", smsSendRecordList);
//					if(type==2){
//						batchHandleSmsInfoData(smsSendRecordList);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return type==1?null:smsSendRecordList;
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
//			
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
//			myBatisDao.execute(SmsSendInfo.class.getName(), "insertbatchSmsInfo", smsSendInfoList);
//		}
//		logger.info("保存  info 表记录！结束 ");
//	}
//	
//
//	
//    /**
//     * judgeIsBigData:判断参数数据是否是大数据   大数据 则调用多线程异步处理 . <br/> 
//     * @author zlp
//     * @param  BatchSmsData   
//     * @return int 1 是参数错误  2标识小数据  3 标识大数据
//     */
//	private int judgeIsBigData( BatchSmsData obj){
//		int flag = 1;
//		if(null!=obj&&obj.getTotal()>0){
//			logger.info("短信内容"+obj.getContent()+"发送条数"+obj.getTotal());
//			if(obj.getTotal()<=10000){ flag = 2; }else{ flag = 3;}
//		}
//		return flag;
//	}
//    
//    
//
//    @Transactional(propagation=Propagation.REQUIRES_NEW)
//	private boolean  resumeUserSms( BatchSmsData obj) {
//    	boolean result = false;
//		try {
//			result  = userInfoService.updateUserSms(obj.getUserId(), SendMessageStatusInfo.ADD_SMS, 
//														obj.getFail()*obj.getActualDeduction(),obj.getType(),obj.getUserId(),obj.getIpAdd(),
//														OperationLogStatus.USER_OPERATION_MORE);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("短信内容"+obj.getContent()+"恢复"+obj.getUserId()+"发送条数"+obj.getTotal()+"失败"+e.getMessage());
//		}
//		return  result;
//	}
//
//    @Transactional(propagation=Propagation.REQUIRES_NEW)
//	private boolean deductionUserSms(  BatchSmsData obj) {
//    	boolean result = false;
//		try {
//			result = userInfoService.updateUserSms(obj.getUserId(), SendMessageStatusInfo.DEL_SMS, 
//														obj.getDatas().length*obj.getActualDeduction(),obj.getType(),obj.getUserId(),obj.getIpAdd(),
//														OperationLogStatus.USER_OPERATION_MORE);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("短信内容"+obj.getContent()+"扣费"+obj.getUserId()+"发送条数"+obj.getTotal()+"失败"+e.getMessage());
//			result = false;
//		}
//		return result;
//	}
}
  