/** 
 * Project Name:s2jh4net 
 * File Name:TestClass.java 
 * Package Name:s2jh.biz.shop.crm.taobao.util 
 * Date:2017年3月18日下午3:25:55 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
  
package s2jh.biz.shop.support.service;  
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.info.OperationLogStatus;
import s2jh.biz.shop.crm.taobao.info.ReturnMessage;
import s2jh.biz.shop.crm.taobao.info.SendMessageStatusInfo;
import s2jh.biz.shop.crm.taobao.util.SendMessageUtil;
import s2jh.biz.shop.crm.taobao.util.SendMessageVipUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.utils.JsonUtil;
import s2jh.biz.shop.utils.MsgType;


/** 
 * MultithreadService <br/> 
 * Date:     2017年3月18日 下午3:25:55 <br/> 
 * @author   zlp
 * @version   1.0
 */
@Service
public class MultithreadBatchSmsService {
 
	@Resource
	private MyBatisDao myBatisDao;
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private VipMemberService vipMemberService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private  SmsRecordService smsRecordService;
	@Autowired
	private CacheService cacheService;
	
	private static final Log logger = LogFactory.getLog(MultithreadBatchSmsService.class);
    //回调处理链条
    interface DataHandler{
        void doHandler(String[] data);
    }
    //error处理链条
    interface ErrorHandler{
        void doHandler(Throwable t);
        public static final ErrorHandler PRINTER = new ErrorHandler() {
            public void doHandler(Throwable t) {
                t.printStackTrace();
                logger.error("批量处理线程异常");
            }
        };
    }
    /**
     * 数据处理器
     */
    class BigDataProcessor{
        /**
         * 记录的分隔符，每个分隔符占一行。
         */
        public static final String DEFAULT_SEPERATOR = "***";
        public static final int COUNT = 1000;
        /**
         * 用于干掉处理数据的线程。
         */
        public final Object POISON = new Object();
        private BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(64);
        private String seperator = DEFAULT_SEPERATOR;
        private ErrorHandler errorHandler = ErrorHandler.PRINTER;
        /**
         * 用于终止读取线程，非强制终止。
         */
        private volatile boolean running = false;
        /**
         * 数据读取线程
         */
        private Thread readerThread;
        /**
         * 数据处理线程
         */
        private Thread[] proccessors;
        public BigDataProcessor(final LinkedBlockingQueue<String> dataQuee, final DataHandler handler) {
        	readerThread = new Thread(new Runnable() {
                public void run() {
                    try {
                    	// 将批量数据分批存入队列
                        ArrayList<String> cache = new ArrayList<String>();
                        while(running&&dataQuee!=null&&dataQuee.size()>0){
                        	String takedata = dataQuee.take();
                            if(seperator.equals(takedata)){
                            	String[] data = cache.toArray(new String[cache.size()]);
                            	cache.clear();
                            	queue.put(data);
                            }else{
                                cache.add(takedata);
                            }
                        }
                        if(null!=cache&&cache.size()>0){
                        	String[] data = cache.toArray(new String[cache.size()]);
                            cache.clear();
                            queue.put(data);
                        }
                    } catch (Throwable t) {
                        errorHandler.doHandler(t);
                    } finally {
                    	try {
                            queue.put(POISON);
                        } catch (InterruptedException e) {
                        	errorHandler.doHandler(e);
                        }
                    }
                }
            },"reader_thread");
            //默认创建处理的线程数，与CPU处理的内核数相同
            proccessors = new Thread[Runtime.getRuntime().availableProcessors()];
            Runnable worker = new Runnable() {
                public void run() {
                	for(;;){
	                    try {
                            Object obj = queue.take();
                            if(obj==POISON){
                                queue.put(obj);
                                break;
                            }else{
                                String[] data =(String[])obj;
                                handler.doHandler(data);
                            }
	                    } catch (Throwable t) {
	                        errorHandler.doHandler(t);
	                    }
                	}
                }
            };
            for(int i=0;i<proccessors.length;i++){
                proccessors[i] = new Thread(worker,"proccessor-thread_"+i);
            }
        }
        public void setErrorHandler(ErrorHandler errorHandler) {
            this.errorHandler = errorHandler;
        }

        public void setSeperator(String seperator) {
            this.seperator = seperator;
        }
        /**
         * 开启处理过程
         */
        public synchronized void start(){
            if(running)return ;
            running = true;
            readerThread.start();
            for(int i=0;i<proccessors.length;i++){
                proccessors[i].start();
            }
        }
        /**
         * 中断处理过程，非强制中断
         */
        public synchronized void shutdown(){
            if(running){
                running = false;
            }
        }
        /**
         * 试图等待整个处理过程完毕
         */
        public void join(){
            try {
            	readerThread.join();
            } catch (InterruptedException e) {
                errorHandler.doHandler(e);
            }
            for(int i=0;i<proccessors.length;i++){
                try {
                    proccessors[i].join();
                } catch (InterruptedException e) {
                    errorHandler.doHandler(e);
                }
            }
        }
    }
    /**
     * 
     * BatchSmsData 调用前请赋值  手机号数组 String data[]  存入手机号
     * 						 userId 
     *                       短信签名autograph
     *                       短信内容 content
     *                       依次赋值
     *  批量发短信主方法
     * @author zlp
     * @param obj
     */
    public void batchOperateSms(final BatchSmsData obj) {
    	 int isBigData = judgeIsBigData(obj);
    	 logger.info("数据判断"+isBigData);
		 if(2==isBigData){
			batchHandleSmallData(obj);
		 }else if(3==isBigData){
			batchHandleBigData(obj);
		 }
		 logger.info("批量发短信end");
	}
  

	private void batchHandleBigData(final BatchSmsData obj) {
		//声明队列 拼装队列数据
		LinkedBlockingQueue<String> dataQuee  = new LinkedBlockingQueue<String>();
		try {
			dataQuee = buildBlockQueue(obj,dataQuee);
		} catch (InterruptedException e1) {
			 logger.error("封装队列数据失败！"+obj.getTotal());
		}
		if(null!=dataQuee&&dataQuee.size()>0){
		     //队列数据没问题  先扣费  在进行发送
			 logger.info("开始扣费！");
			 boolean result = deductionUserSms(obj);
			 if(result){
				 logger.info("扣费成功！");
				 //回调函数处理
				 DataHandler dataHandler = new DataHandler() {
					 public void doHandler(String[] data) {
						 synchronized (obj) {
							 try {
								 logger.info("批处理进来了！");
								 unifySendSmsData(obj, data);
							 } catch (Exception e) {
								 logger.error("短信发送失败"+e.getMessage()+obj.getContent());
							 }
						 }
					 }
				 };
				 BigDataProcessor processor = new BigDataProcessor(dataQuee,dataHandler);
				 processor.start();
				 processor.join();
				 logger.info("批处理线程处理完毕！");
				 handleLogicAfterSendSms(obj,true);
			 }else{
				 logger.info("余额不足"+obj.getUserId()); 
			 }
		}
	}
	
	private void batchHandleSmallData(BatchSmsData obj) {
	 	boolean result = deductionUserSms(obj);
	    if(result){
	    	unifySendSmsData(obj,obj.getDatas());
	    	handleLogicAfterSendSms(obj,false);
	    }
	}
	
	private void unifySendSmsData(final BatchSmsData obj, String[] data) {
	    String s = Arrays.toString(data).replace("[", "").replace("]", "").replace(" ", "");
		Map<String,Object>  linkedMap =  new LinkedHashMap<String, Object>();
		String status = null;
		if(obj.isVip()){
			String result = SendMessageVipUtil.sendMessage(s,obj.getContent(), null, obj.getUserId());
			ReturnMessage returnMessage = JsonUtil.fromJson(result, ReturnMessage.class);
			status = returnMessage.getReturnCode();
		}else{
			String result = SendMessageUtil.sendMessage(s,obj.getContent(), null, obj.getUserId());
			ReturnMessage returnMessage = JsonUtil.fromJson(result, ReturnMessage.class);
			status = returnMessage.getReturnCode();
		}
		if("100".equals(status)){
			obj.setSuccess(obj.getSuccess()+data.length);
		    linkedMap.put(status, Arrays.asList(data));
		 	obj.getSuccessData().add(linkedMap);
		}else{
			obj.setFail(obj.getFail()+data.length);
			linkedMap.put(status, Arrays.asList(data));
			obj.getFailData().add(linkedMap);
		}
    }
	public void handleLogicAfterSendSms( BatchSmsData obj,boolean isASync){
		logger.info("isASync！"+isASync+"失败数量"+obj.getFail());
		if(obj.getFail()!=0){
			 resumeUserSms(obj);
		}
		if(isASync){
			logger.info("异步处理");
			asyncHandleRecord(obj);
		}else{
			logger.info("同步处理");
			batchHandleResultData(obj);
		}
	}


	private LinkedBlockingQueue<String> buildBlockQueue(final BatchSmsData obj,LinkedBlockingQueue<String> dataQuee)
			throws InterruptedException {
		//声明队列 拼装队列数据每五百条作为一个队列组
		for (int i = 0; i < obj.getTotal(); i++) {
			dataQuee.put(obj.getDatas()[i]);
			if(i!=0&&i % 500 == 0){
				dataQuee.put(BigDataProcessor.DEFAULT_SEPERATOR);
			}
		}
		logger.info("dataQuee size"+dataQuee.size());
		return dataQuee;
	}

	
    /**
     * judgeIsBigData:判断参数数据是否是大数据   大数据 则调用多线程异步处理 . <br/> 
     * @author zlp
     * @param  BatchSmsData   
     * @return int 1 是参数错误  2标识小数据  3 标识大数据
     */
	private int judgeIsBigData( BatchSmsData obj){
		int flag = 1;
		if(null!=obj&&obj.getTotal()>0){
			logger.info("短信内容"+obj.getContent()+"发送条数"+obj.getTotal());
			if(obj.getTotal()<=1000){ flag = 2; }else{ flag = 3;}
		}
		return flag;
	}
    
    

    @Transactional(propagation=Propagation.REQUIRES_NEW)
	private boolean  resumeUserSms( BatchSmsData obj) {
    	boolean result = false;
		try {
			result  = userAccountService.doUpdateUserSms(obj.getUserId(), SendMessageStatusInfo.ADD_SMS, 
														obj.getFail()*obj.getActualDeduction(),obj.getType(),obj.getUserId(),obj.getIpAdd(),
														OperationLogStatus.USER_OPERATION_MORE+"，增加短信"+obj.getFail()*obj.getActualDeduction()+"条",UserAccountService.TIME_OUT);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("短信内容"+obj.getContent()+"恢复"+obj.getUserId()+"发送条数"+obj.getTotal()+"失败"+e.getMessage());
		}
		return  result;
	}

    @Transactional(propagation=Propagation.REQUIRES_NEW)
	private boolean deductionUserSms(  BatchSmsData obj) {
    	boolean result = false;
		try {
			result = userAccountService.doUpdateUserSms(obj.getUserId(), SendMessageStatusInfo.DEL_SMS, 
														obj.getDatas().length*obj.getActualDeduction(),obj.getType(),obj.getUserId(),obj.getIpAdd(),
														OperationLogStatus.USER_OPERATION_MORE+"，扣除短信"+obj.getDatas().length*obj.getActualDeduction()+"条",UserAccountService.TIME_OUT);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("短信内容"+obj.getContent()+"扣费"+obj.getUserId()+"发送条数"+obj.getTotal()+"失败"+e.getMessage());
			result = false;
		}
		return result;
	}

	private   void asyncHandleRecord(final BatchSmsData obj) {
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
			@Override
			public void run() {
				batchHandleResultData(obj);
			}
		});
	}

	public void  batchHandleResultData(final BatchSmsData obj){
		if(obj.getFailData().size()>0){
			logger.info("保存失败短信！");
			batchHandleRecord(obj,1);
		}
		if(obj.getSuccessData().size()>0){
			logger.info("保存成功短信！");
			batchHandleRecord(obj,2);
		}
		if(obj.isNow()&&obj.isLast()){
			asyncHandleMember(obj.getUserId(), obj.getMsgId());
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<SmsRecordDTO> batchHandleRecord(final BatchSmsData obj,int type){
		logger.info("type 2 是保存  info 表记录！ "+type);
		List<SmsRecordDTO> smsSendRecordList =null;
		logger.info("保存所有发送的短信");
		try {
			List<Map<String, Object>> failData = type==1?obj.getFailData():obj.getSuccessData();
			for (Map<String, Object> map : failData) {
				smsSendRecordList = new ArrayList<SmsRecordDTO>();
				Set<String> keySet = map.keySet();
				Date dateLong = new Date();
				for (String key : keySet) {
					long time = dateLong.getTime();
					@SuppressWarnings("rawtypes")
					List list = (List)map.get(key);
					String sessionkey ="";
					try {
						sessionkey = getSessionkey(obj.getUserId());
					} catch (Exception e) {
						logger.info("保存记录session获取出错");
					}
					if("".equals(sessionkey))break;
					SmsRecordDTO  smsSendRecord  = null;
					for (Object object : list) {
						smsSendRecord = new SmsRecordDTO();
						Date date = new Date();
						smsSendRecord.setContent(obj.getContent());
						smsSendRecord.setSendTime(date);
						smsSendRecord.setReceiverTime(date);
						smsSendRecord.setType(obj.getType());
						smsSendRecord.setChannel(obj.getChannel());
						smsSendRecord.setActualDeduction(obj.getActualDeduction());
					    smsSendRecord.setResultCode(key);
					    smsSendRecord.setOrderId(obj.getTid()==null?null:obj.getTid());

					    smsSendRecord.setMsgId(obj.getMsgId()==null?null:obj.getMsgId());
					    smsSendRecord.setSource("2");
					    smsSendRecord.setStatus("100".equals(key)?SendMessageStatusInfo.RECORDSUCESS:SendMessageStatusInfo.RECORDFAIL);
						smsSendRecord.setAutograph(obj.getAutograph());
						smsSendRecord.setUserId(obj.getUserId());
						smsSendRecord.setRecNum(EncrptAndDecryptClient.getInstance().encrypt(object.toString(), EncrptAndDecryptClient.PHONE,sessionkey));
						smsSendRecord.setShow(true);
						smsSendRecord.setSendLongTime(time);
						smsSendRecordList.add(smsSendRecord);
					}
				}
				if(null!=smsSendRecordList&&smsSendRecordList.size()>0){
					Thread.sleep(200);
					logger.info("保存  record 表记录！ ");
					smsRecordService.saveSmsRecord(smsSendRecordList, obj.getUserId());
					if(type==2){
						/*if(!obj.isNow()){//不是立即发送
							batchHandleSmsInfoData(smsSendRecordList,false);	
						}else if(obj.isNow()&&obj.isLast()){//立即发送最后一批
							batchHandleSmsInfoData(smsSendRecordList,true);
						}*/
						if(!obj.isNow()){//不是立即发送
							batchHandleSmsInfoData(smsSendRecordList,false);	
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type==1?null:smsSendRecordList;
	} 
	public void  batchHandleSmsInfoData(List<SmsRecordDTO> smsSendRecordList,boolean flag){
		MemberDTO memberInfo =null;
    	long time = new Date().getTime();
    	if(null!=smsSendRecordList&&smsSendRecordList.size()>0){
    		String type = smsSendRecordList.get(0).getType();
    		Long msgId = smsSendRecordList.get(0).getMsgId();
    		String userId = smsSendRecordList.get(0).getUserId();
    		logger.info("保存  info VipMember 表记录！type "+type);
    		if(MsgType.MSG_HYDXQF.equals(type)){
    			/*if(!flag){
	    			for (SmsRecordDTO smsSendRecord : smsSendRecordList) {
	    				memberInfo =  new MemberDTO();
	    				memberInfo.setPhone(smsSendRecord.getRecNum());
	    				memberInfo.setUserId(smsSendRecord.getUserId());
	    				memberInfo.setMsgId(smsSendRecord.getMsgId());
	    				memberInfo.setLastSendSmsTime(time); 
	    				vipMemberService.updateMemberInfoByParam(memberInfo,2);
	    			}
    			}else{
    				//TODO
    				//开始异步去处理会员的发送
    				this.asyncHandleMember(userId,msgId);
    			}*/
    			if(!flag){
	    			for (SmsRecordDTO smsSendRecord : smsSendRecordList) {
	    				memberInfo =  new MemberDTO();
	    				memberInfo.setPhone(smsSendRecord.getRecNum());
	    				memberInfo.setUserId(smsSendRecord.getUserId());
	    				memberInfo.setMsgId(smsSendRecord.getMsgId());
	    				memberInfo.setLastSendSmsTime(time); 
	    				vipMemberService.updateMemberInfoByParam(memberInfo,2);
	    			}
    			}
    		}
    	}
		logger.info("保存vipMember  info 表记录！结束 ");
	}
	
	
	 private  String getSessionkey(String userNickName){
		    String  token = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName);
			if(null!=token&&!"".equals(token)){
				 return token;
			}else{
				UserInfo user = userInfoService.queryUserTokenInfo(userNickName);
				if(null!=user&&null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
					 cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName,user.getAccess_token());
					 return user.getAccess_token(); 
				}
			}
			return "";
	 }

	 
//TODO
//######################################################################
	 
	 private void asyncHandleMember(final String userId, final Long msgId) {
			MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
				@Override
				public void run() {
					logger.info("进入会员发送时间更新! userId:"+userId+",msgId:"+msgId);
					markMemberSendTime(userId,msgId);
				}
			});
	}
	 
	 private void markMemberSendTime(String userId,Long msgId){
		 //1:去mongo查询短信的总数
		 Long count = smsRecordService.findSmsRecordCount(userId,msgId,MsgType.SMS_STATUS_SUCCEED);
		 logger.info("需要更新会员发送时间的条数: "+count+",userId:"+userId+",msgId:"+msgId);
		 long c1 = System.currentTimeMillis();
		 if(count==null || count.longValue()==0) return;
		 int start=0,end=0,pageSize=1000;
		 if(count.longValue()<=pageSize){
			 end =1;
		 }else if(count.intValue()%pageSize==0){
			 end = count.intValue()/pageSize;
		 }else{
			 end = count.intValue()/pageSize+1;
		 }
		 Map<String, Object> map = new HashMap<String,Object>();
		 map.put("pageSize", pageSize);
		 while(start<end){
			logger.info("更新会员发送时间循环第 "+(start+1)+"页,userId:"+userId+",msgId:"+msgId);
			if(start == (end-1)) map.put("pageSize", count.intValue()-start*pageSize);
			map.put("startRow", start*pageSize);
			//分页查询
			List<SmsRecordDTO> list = smsRecordService.findSmsRecordList(userId,msgId, MsgType.SMS_STATUS_SUCCEED,map);
			start++;
			if(list==null || list.isEmpty()) continue;
			updateMemberSendSTamp(list);
		 }
		 logger.info("更新会员发送时间总耗时 "+(System.currentTimeMillis()-c1)+"millis,userId:"+userId+",msgId:"+msgId);
	 }


	private void updateMemberSendSTamp(List<SmsRecordDTO> list) {
		MemberDTO memberInfo =null;
		for (SmsRecordDTO smsSendRecord : list) {
			memberInfo =  new MemberDTO();
			memberInfo.setPhone(smsSendRecord.getRecNum());
			memberInfo.setUserId(smsSendRecord.getUserId());
			memberInfo.setMsgId(smsSendRecord.getMsgId());
			memberInfo.setLastSendSmsTime(smsSendRecord.getSendLongTime()); 
			vipMemberService.updateMemberInfoByParam(memberInfo,2);
		}
	}
	 
	 
}
  