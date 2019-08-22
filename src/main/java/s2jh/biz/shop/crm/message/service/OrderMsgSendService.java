package s2jh.biz.shop.crm.message.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.DistributedLock;
import lab.s2jh.core.service.RedisLockServiceImpl;
import lab.s2jh.core.util.DateUtils;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.entity.LogAccessDTO;
import s2jh.biz.shop.crm.manage.entity.LogType;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.BatchOperateSmsService;
import s2jh.biz.shop.crm.manage.service.LogAccessQueueService;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.vo.SendMsgVo;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.vip.service.VipUserService;
import s2jh.biz.shop.crm.vo.OrdersVo;
import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.support.service.MultithreadBatchSmsService;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.phoneRegExp.PhoneRegUtils;

import com.taobao.api.SecretException;
/** 
* @ClassName: MsgSendService 
* @Description: TODO(订单短信群发>>发送短信) 
* @author jackstraw_yu 
* @date 2017年5月3日 上午10:16:58 
*  
*/
@Service
public class OrderMsgSendService{

	@Autowired
	private MultithreadBatchSmsService multithreadService;
	@Autowired
	private SmsRecordService smsRecordService;
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	@Autowired
	private DistributedLock distributedLock;
	@Autowired
	private TradeInfoService tradeInfoService;
	@Autowired
	private MobileSettingService mobileSettingService;
	@Autowired
	private BatchOperateSmsService batchOperateSmsService;
	@Autowired
	private SmsSendRecordService smsSendRecordService;
	@Autowired
	private SmsBlackListService smsBlackListService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private VipUserService vipUserService;
	
	private static final Log logger = LogFactory.getLog(OrderMsgSendService.class);
	
	
	public void batchSendOrderMsg(SendMsgVo sendMsgVo,LogAccessDTO logAccessDTO) throws Exception {
		if(sendMsgVo == null || sendMsgVo.getTotalCount() ==null || sendMsgVo.getTotalCount() ==0 || sendMsgVo.getSchedule()==null)
			throw new Exception("订单短信群发数据筛选异常!");
		try {
//			distributedLock.tryLock(RedisConstant.RediskeyCacheGroup.ORDER_BATCH_SEND_DATA_KEY+"-"+sendMsgVo.getQueryKey()+"-"+sendMsgVo.getUserId());
			OrdersVo ordersVo = redisLockServiceImpl.getValue(RedisConstant.RediskeyCacheGroup.ORDER_BATCH_SEND_DATA_KEY+"-"+sendMsgVo.getQueryKey()+"-"+sendMsgVo.getUserId(), OrdersVo.class);
			if(null!=ordersVo){
				//判断是立即发送还是定时发送
				Date startTime = null,endTime = null;
				if(sendMsgVo.getSchedule()){
					startTime = DateUtils.parseDate(sendMsgVo.getSendTime(), "yyyy-MM-dd HH:mm");
					endTime = DateUtils.addDate(startTime, 1);
				}else{
					startTime =new Date();
				}
				//验证,保存子记录与更新总记录
				MsgSendRecord msg = saveMsgRecord(sendMsgVo,startTime,MsgType.MSG_STATUS_SENDING);
				sendMsgVo.setMsgId(msg.getId());
				if(!sendMsgVo.getSchedule()){
					processMsgSendData(sendMsgVo,ordersVo,msg,logAccessDTO,startTime,endTime);
				}else{
					processScheduleData(sendMsgVo,ordersVo,msg,logAccessDTO,startTime,endTime);
				}
			}
		}finally{  
//			distributedLock.unLock(RedisConstant.RediskeyCacheGroup.ORDER_BATCH_SEND_DATA_KEY+"-"+sendMsgVo.getQueryKey()+"-"+sendMsgVo.getUserId());
		}
	}
	


	/**
	 * 立即发送:处理短信群发数据
	 * 一,筛选错误手机号,重复手机号....
	 * 四,更新总表
	 */
	@SuppressWarnings("unchecked")
	private void processMsgSendData(final SendMsgVo sendMsgVo,final OrdersVo ordersVo,
									final MsgSendRecord msg,final LogAccessDTO logAccessDTO,
									final Date startTime, final Date endTime){
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
			public void run() {
				String sessionKey = userInfoService.validateFindSessionKey(ordersVo.getUserId());
				//辅助验证的集合
				Set<String> vSet = new HashSet<String>();
				//错误、重复、黑名单手机号
			    List<String> wrongNums = new ArrayList<String>(),repeatNums = new ArrayList<String>(),blackNums = new ArrayList<String>();
			    //筛选错误、重复、黑名单手机号的resultMap
			    Map<String,Object> sortMap = null;
			    //统计发送平台返回的成功、失败个数
			    int successNo =0,errorNo=0;
				Long end = 0l,start = 0l, totalCount = sendMsgVo.getTotalCount();
				if(totalCount/ConstantUtils.PROCESS_PAGE_SIZE_MAX==0){
					end  = 1l;
				}else if(totalCount%ConstantUtils.PROCESS_PAGE_SIZE_MAX==0){
					 end  = totalCount/ConstantUtils.PROCESS_PAGE_SIZE_MAX;
				}else{
					 end  = (totalCount+ConstantUtils.PROCESS_PAGE_SIZE_MAX)/ConstantUtils.PROCESS_PAGE_SIZE_MAX;
				}
				ordersVo.setPageSize(ConstantUtils.PROCESS_PAGE_SIZE_MAX);
				int emptyNum = 0;//统计订单手机号为空的数量
				while(start<end){
					if(start == (end-1)){
						ordersVo.setPageSize(totalCount-start*ConstantUtils.PROCESS_PAGE_SIZE_MAX);
					}
					logger.info("订单短信群发-立即发送:第"+(start+1)+"次循环^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					ordersVo.setStartRow(start*ConstantUtils.PROCESS_PAGE_SIZE_MAX);
					long s1 = System.currentTimeMillis();
					/***
					 * 查询出数据
					 **/
					ordersVo.setMsgId(sendMsgVo.getMsgId());
					List<TradeDTO> tradeList = tradeInfoService.findSendOrderInfoList(ordersVo,ordersVo.getUserId());
					List<String> nums = new ArrayList<String>();
					List<String> tids = new ArrayList<String>();
					if(tradeList != null && !tradeList.isEmpty()){
						for (int i = 0; i < tradeList.size(); i++) {
							TradeDTO tradeDTO = tradeList.get(i);
							nums.add(tradeDTO.getReceiverMobile());
							tids.add(tradeDTO.getTid());
						}
					}
					try {
						orderLogBatch(tids, logAccessDTO);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					emptyNum += ordersVo.getPageSize().intValue() - nums.size();
					start++;
					if(nums==null || nums.isEmpty()){
						continue;
					}
					logger.info("订单短信群发-立即发送:查询出"+(nums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					logger.info("订单短信群发-立即发送:此次查询时间开销"+(System.currentTimeMillis()-s1)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					try {
						//筛选出重复手机号,错误手机号,黑名单手机号、昵称.....
						sortMap = sortDataBatch(nums,vSet,ordersVo.getUserId());
					} catch (SecretException e) {
						e.printStackTrace();
					}
					if(sortMap==null || sortMap.isEmpty()){
						continue;
					}
					if(sortMap.get("wrongNums") !=null){
						wrongNums.addAll((ArrayList<String>)sortMap.get("wrongNums"));
					}
					if(sortMap.get("repeatNums") !=null){
						repeatNums.addAll((ArrayList<String>)sortMap.get("repeatNums"));
					}
					if(sortMap.get("blackNums") !=null){
						blackNums.addAll((ArrayList<String>)sortMap.get("blackNums"));
					}
					if(sortMap.get("sendNums") !=null){
						List<String> sendNums = (ArrayList<String>)sortMap.get("sendNums");
						if(sendNums.size()>0){
							//successNo+=sendNums.size();
							Map<String, Integer> resultMap = batchSendMsg( sendNums.toArray(new String[0]),sendMsgVo);
							if(resultMap!=null&&!resultMap.isEmpty()){
								successNo += (resultMap.get("succeedNum")==null?0:resultMap.get("succeedNum"));
								logger.info("订单短信群发-立即发送:第"+(start+1)+"次循环^_^|^_^|" + "查询出" + (nums.size()) + "条数据," + "成功：" + resultMap.get("succeedNum") + "条");
								errorNo += (resultMap.get("failedNum")==null?0:resultMap.get("failedNum"));
							}
						}
					}
					vSet.addAll(nums);
				}
				EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
				if(wrongNums.size()>0){
					msg.setWrongCount(wrongNums.size() + emptyNum);
					logger.info("订单短信群发-立即发送:筛选出手机号错误,共"+(wrongNums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					try {
						List<String> wrongLinkedList = new LinkedList<String>();
						for (int i = 0; i < wrongNums.size(); i++) {
							String wrongNum = wrongNums.get(i);
							if(EncrptAndDecryptClient.isEncryptData(wrongNum, EncrptAndDecryptClient.PHONE)){
								wrongLinkedList.add(wrongNum);
							}else {
								wrongLinkedList.add(decryptClient.encrypt(wrongNum, EncrptAndDecryptClient.PHONE, sessionKey));
							}
						}
						smsRecordService.saveErrorMsgNums(wrongLinkedList, sendMsgVo, MsgType.SMS_STATUS_WRONGNUM, startTime);
					} catch (SecretException e) {
						e.printStackTrace();
					}
				}
				if(repeatNums.size()>0){
					msg.setRepeatCount(repeatNums.size());
					logger.info("订单短信群发-立即发送:筛选出手机号重复,共"+(repeatNums.size())+"条数据 ^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					try {
						List<String> repeatLinkedList = new LinkedList<String>();
						for (int i = 0; i < repeatNums.size(); i++) {
							String repeatNum = repeatNums.get(i);
							if(EncrptAndDecryptClient.isEncryptData(repeatNum, EncrptAndDecryptClient.PHONE)){
								repeatLinkedList.add(repeatNum);
							}else {
								repeatLinkedList.add(decryptClient.encrypt(repeatNum, EncrptAndDecryptClient.PHONE, sessionKey));
							}
						}
						smsRecordService.saveErrorMsgNums(repeatLinkedList, sendMsgVo,MsgType.SMS_STATUS_REPEATNUM, startTime);
					} catch (SecretException e) {
						e.printStackTrace();
					}
				}
				if(blackNums.size() > 0){
					msg.setBlackCount(blackNums.size());
					logger.info("订单短信群发-立即发送:筛选出手机号黑名单,共"+(blackNums.size())+"条数据 ^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					try {
						List<String> blackLinkedList = new LinkedList<String>();
						for (int i = 0; i < blackNums.size(); i++) {
							String blackNum = blackNums.get(i);
							if(EncrptAndDecryptClient.isEncryptData(blackNum, EncrptAndDecryptClient.PHONE)){
								blackLinkedList.add(blackNum);
							}else {
								blackLinkedList.add(decryptClient.encrypt(blackNum, EncrptAndDecryptClient.PHONE, sessionKey));
							}
						}
						smsRecordService.saveErrorMsgNums(blackLinkedList, sendMsgVo, MsgType.SMS_STATUS_BLAKLIST, startTime);
					} catch (SecretException e) {
						e.printStackTrace();
					}
				}
				msg.setSucceedCount(successNo);
				msg.setFailedCount(errorNo);
				msg.setStatus(MsgType.MSG_STATUS_SENDOVER);
				msgSendRecordService.updateMsg(msg);
			}
		});
	}
	
	

	/**
	 * 保存定时发送的数据
	 * 去除重复手机号,错误手机号,黑名单手机号...
	 * */
	@SuppressWarnings("unchecked")
	private void processScheduleData(final SendMsgVo sendMsgVo, final OrdersVo ordersVo, 
									 final MsgSendRecord msg,final LogAccessDTO logAccessDTO,
									 final Date startTime,final Date endTime) {
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
			@Override
			public void run() {
				Set<String> vSet = new HashSet<String>();
				//符合筛选条件的总订单、待发送订单、错误、重复、黑名单手机号
				List<String> nums=null,sendNums = new ArrayList<String>(),wrongNums = new ArrayList<String>(),repeatNums = new ArrayList<String>(),blackNums = new ArrayList<String>();
				//计算结果的map
				Map<String,Object> sortMap = null;
				//统计数量
				Integer wrongNo = null,repeatNo = null,blackNo = null /*,successNo =0*/;
				Long end = 0l,start = 0l;
				Long totalCount = sendMsgVo.getTotalCount();
				if(totalCount/ConstantUtils.PROCESS_PAGE_SIZE_MAX==0){
					end  = 1l;
				}else if(totalCount%ConstantUtils.PROCESS_PAGE_SIZE_MAX==0){
					 end  = totalCount/ConstantUtils.PROCESS_PAGE_SIZE_MAX;
				}else{
					 end  = (totalCount+ConstantUtils.PROCESS_PAGE_SIZE_MAX)/ConstantUtils.PROCESS_PAGE_SIZE_MAX;
				}
				ordersVo.setPageSize(ConstantUtils.PROCESS_PAGE_SIZE_MAX);
				int emptyNum = 0;//统计订单手机号为空的数量
				while(start<end){
					if(start == (end-1)){
						ordersVo.setPageSize(totalCount-start*ConstantUtils.PROCESS_PAGE_SIZE_MAX);
					}
					ordersVo.setStartRow(start*ConstantUtils.PROCESS_PAGE_SIZE_MAX);
					logger.info("订单短信群发-定时发送:第"+(start+1)+"次循环^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					long s1 = System.currentTimeMillis();
					//查询出符合筛选条件的数据
					List<TradeDTO> tradeList = tradeInfoService.findSendOrderInfoList(ordersVo,ordersVo.getUserId());
					nums = new ArrayList<String>();
					List<String> tids = new ArrayList<String>();
					if(tradeList != null && !tradeList.isEmpty()){
						for (int i = 0; i < tradeList.size(); i++) {
							TradeDTO tradeDTO = tradeList.get(i);
							nums.add(tradeDTO.getReceiverMobile());
							tids.add(tradeDTO.getTid());
						}
					}
					try {
						orderLogBatch(tids, logAccessDTO);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					emptyNum += ordersVo.getPageSize().intValue() - nums.size();
					start++;
					if(nums==null || nums.isEmpty()){
						continue;
					}
					logger.info("订单短信群发-定时发送:查询出"+(nums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					logger.info("订单短信群发-定时发送:此次查询时间开销"+(System.currentTimeMillis()-s1)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					//过滤数据(错误、重复、黑名单手机号)
					try {
						sortMap = sortDataBatch(nums,vSet,ordersVo.getUserId());
					} catch (SecretException e) {
						e.printStackTrace();
					}
					if(sortMap==null || sortMap.isEmpty()){
						continue;
					}
					if(sortMap.get("wrongNums") !=null){
						wrongNums.addAll((ArrayList<String>)sortMap.get("wrongNums"));
					}
					if(sortMap.get("repeatNums") !=null){
						repeatNums.addAll((ArrayList<String>)sortMap.get("repeatNums"));
					}
					if(sortMap.get("sendNums") !=null){
						sendNums.addAll((ArrayList<String>)sortMap.get("sendNums"));
					}
					if(sortMap.get("blackNums") !=null){
						blackNums.addAll((ArrayList<String>)sortMap.get("blackNums"));
					}
					vSet.addAll(nums);
				}
				EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
				String sessionKey = userInfoService.validateFindSessionKey(ordersVo.getUserId());
				if(sendNums.size()>0){
					List<String> sendList = new ArrayList<String>();
					try {
						for (int i = 0; i < sendNums.size(); i++) {
							String smsNum = sendNums.get(i);
							if(EncrptAndDecryptClient.isEncryptData(smsNum, EncrptAndDecryptClient.PHONE)){
								sendList.add(smsNum);
							}else {
								String encryNum = decryptClient.encrypt(smsNum, EncrptAndDecryptClient.PHONE,sessionKey );
								sendList.add(encryNum);
							}
						}
						splitScheduleData(sendList,sendMsgVo,startTime,endTime);
					} catch (SecretException e) {
						e.printStackTrace();
					}
				}
				try {
					if(wrongNums.size()>0){
						List<String> wrongList = new ArrayList<String>();
						wrongNo = wrongNums.size() + emptyNum;
						logger.info("订单短信群发-定时发送:筛选出手机号错误,共"+(wrongNums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
						for (int i = 0; i < wrongNums.size(); i++) {
							String smsNum = wrongNums.get(i);
							if(EncrptAndDecryptClient.isEncryptData(smsNum, EncrptAndDecryptClient.PHONE)){
								wrongList.add(smsNum);
							}else {
								String encryNum = decryptClient.encrypt(smsNum, EncrptAndDecryptClient.PHONE, sessionKey);
								wrongList.add(encryNum);
							}
						}
						smsRecordService.saveErrorMsgNums(wrongList,sendMsgVo,MsgType.SMS_STATUS_WRONGNUM,startTime);
					}
					if(repeatNums.size()>0){
						repeatNo = repeatNums.size();
						List<String> rpeatList = new ArrayList<String>();
						logger.info("订单短信群发-定时发送:筛选出手机号重复,共"+(repeatNums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
						for (int i = 0; i < repeatNums.size(); i++) {
							String smsNum = repeatNums.get(i);
							if(EncrptAndDecryptClient.isEncryptData(smsNum, EncrptAndDecryptClient.PHONE)){
								rpeatList.add(smsNum);
							}else {
								String encryNum = decryptClient.encrypt(smsNum, EncrptAndDecryptClient.PHONE, sessionKey);
								rpeatList.add(encryNum);
							}
						}
						smsRecordService.saveErrorMsgNums(rpeatList,sendMsgVo,MsgType.SMS_STATUS_REPEATNUM,startTime);
					}
					if(blackNums.size() > 0){
						blackNo = blackNums.size();
						List<String> blackList = new ArrayList<String>();
						logger.info("订单短信群发-定时发送:筛选出手机号黑名单,共"+(blackNums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
						for (int i = 0; i < blackNums.size(); i++) {
							String smsNum = blackNums.get(i);
							if(EncrptAndDecryptClient.isEncryptData(smsNum, EncrptAndDecryptClient.PHONE)){
								blackList.add(smsNum);
							}else {
								String encryNum = decryptClient.encrypt(smsNum, EncrptAndDecryptClient.PHONE, sessionKey);
								blackList.add(encryNum);
							}
						}
						smsRecordService.saveErrorMsgNums(blackList, sendMsgVo, MsgType.SMS_STATUS_BLAKLIST, startTime);
					}
				} catch (SecretException e) {
					e.printStackTrace();
				}
				//更新总记录
				//msg.setSucceedCount(successNo);
				//定时这里设置一个0值
				msg.setSucceedCount(0);
				msg.setFailedCount(0);
				msg.setRepeatCount(repeatNo);
				msg.setWrongCount(wrongNo);
				msg.setBlackCount(blackNo);
				msg.setStatus(MsgType.MSG_STATUS_SENDOVER);
				msgSendRecordService.updateMsg(msg);
			}
		});
	}
	
	
	/**
	 * 以20万长度为节点 拆分要保存的定时数据
	 * 此处取消线程异步处理---确保取消定时时候数据安全
	 * **/
	private void splitScheduleData(List<String> nums, SendMsgVo sendMsgVo, Date startTime, Date endTime) {
		if(nums.isEmpty()) return;
		List<String> sendNums = new ArrayList<String>(nums);
		logger.info("订单短信群发-定时发送:"+sendMsgVo.getUserId()+"保存"+(sendNums.size())+"条定时数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		Integer end = 0,start = 0,node=50000,dataSize = sendNums.size();
		List<String> subNums =null;
		if(dataSize/node==0){
			end	= 1;
		}else if(dataSize%node==0){
			end  = dataSize/node;
		}else{
			end  = (dataSize+node)/node;
		}
		while(start<end){
			if(start == (end-1)){
				subNums = sendNums.subList(start*node, dataSize);
			}else{
				subNums = sendNums.subList(start*node, (start+1)*node);
			}
			if(subNums==null) continue;
			start++;
			logger.info("订单短信群发-定时发送:"+sendMsgVo.getUserId()+"保存第"+start+"次组装的数据,数据量为:"+subNums.size()+"条^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
			saveMsgScheduleBatch(subNums,sendMsgVo,startTime,endTime);
		}

	}
	
	/**
	 * 调用短信群发接口,返回成功失败的条数 
	 */
	public Map<String,Integer> batchSendMsg(String [] phones,SendMsgVo sendMsgVo) {
		
		
		
		long s = System.currentTimeMillis();
		BatchSmsData  batchSmsData = new BatchSmsData(phones);
		batchSmsData.setType(sendMsgVo.getMsgType());
		batchSmsData.setIpAdd(sendMsgVo.getIpAddress());
		batchSmsData.setChannel(sendMsgVo.getAutograph());
		batchSmsData.setAutograph(sendMsgVo.getAutograph());
		batchSmsData.setUserId(sendMsgVo.getUserId());
		batchSmsData.setContent(sendMsgVo.getContent());
		batchSmsData.setMsgId(sendMsgVo.getMsgId());
		/*查询当前用户是否为vip*/
		boolean isVip = vipUserService.findVipUserIfExist(sendMsgVo.getUserId());
		batchSmsData.setVip(isVip);
		multithreadService.batchOperateSms(batchSmsData);
		int sNum = batchSmsData.getSuccess();
		int fNum = batchSmsData.getFail();
		Map<String,Integer> resultMap =null;
		if(sNum!=0 || fNum!=0){
			resultMap = new HashMap<String,Integer>();
			resultMap.put("succeedNum", sNum);
			resultMap.put("failedNum", fNum);
		}
		logger.info("订单短信群发-立即发送:批量发送时间开销:"+(System.currentTimeMillis()-s)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		return resultMap;
	}
	

	/**
	 * 保存群发短信到定时数据表,多条or一条
	 */
	private void saveMsgScheduleBatch(List<String> nums, final SendMsgVo sendMsgVo,Date startTime,Date endTime) {
		long s = System.currentTimeMillis();
		String mobiles = StringUtils.join(nums.toArray(),",");
		SmsSendInfo smsSendInfo = new SmsSendInfo();
		smsSendInfo.setUserId(sendMsgVo.getUserId());
		smsSendInfo.setMsgId(sendMsgVo.getMsgId());
		smsSendInfo.setType(sendMsgVo.getMsgType());
		smsSendInfo.setContent(sendMsgVo.getContent());
		smsSendInfo.setPhone(mobiles);
		smsSendInfo.setStartSend(startTime);
		smsSendInfo.setEndSend(endTime);
		smsSendInfo.setChannel(sendMsgVo.getAutograph());
		smsSendInfo.setCreatedDate(new Date());
		smsSendInfo.setLastModifiedDate(new Date());
		try {
			myBatisDao.execute(
					SmsSendInfo.class.getName() + "Schedule",
					"doCreateByScheduleSend", smsSendInfo);
		} catch (Exception e) {
			logger.error("订单短信群发-定时发送:保存单笔定时数据失败!!^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		}
		logger.info("订单短信群发-定时发送:保存单笔定时数据时间开销:"+(System.currentTimeMillis()-s)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
	}

	
	
	
	
	
	/** 
	*  核心方法
	*  (订单短信群发:大数据量时筛选手机号错误,手机号重复) 
	* @author jackstraw_yu    
	* @throws SecretException 
	*/
	private Map<String, Object>  sortDataBatch(List<String> nums,Set<String> vSet,String userId) throws SecretException {
		logger.info("订单短信群发:核心筛选区接收到"+(nums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		List<String> mList =  new ArrayList<String>(nums),wrongNums = new ArrayList<String>(),repeatNums = new ArrayList<String>(),blackNums = new ArrayList<String>();
		Map<String,Object> hashMap = new HashMap<String,Object>();
		String str = null;
		Set<String> numSet =null,vNumSet =null;
		Iterator<String> iterator = mList.iterator();
		long h1 = System.currentTimeMillis();
		//校验订单手机号--订单抽取出的订单信息手机号有的会有问题,需要进行正则校验!
		while(iterator.hasNext()){
			str = iterator.next();
			if(str==null || "".equals(str) || !PhoneRegUtils.phoneValidate(str)){
				wrongNums.add(str);
				iterator.remove();
			}
		}
		long h2 = System.currentTimeMillis();
		logger.info("订单短信群发:送筛选出错误手机号,时间开销"+(h2-h1)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		if(!mList.isEmpty()){
			repeatNums.addAll(mList);
			numSet = new HashSet<String>(mList);
			vNumSet = new HashSet<String>(mList);
			for (String n : numSet) {
				repeatNums.remove(n);
			}
		}
		//求得重复手机号
		if(!repeatNums.isEmpty()){
			hashMap.put("repeatNums", repeatNums);
		}
		if(vNumSet!=null && !vNumSet.isEmpty()){
			vNumSet.retainAll(vSet);
			repeatNums.addAll(vNumSet);
			hashMap.put("repeatNums", repeatNums);
			numSet.removeAll(vNumSet);
		}
		if(numSet!=null &&!numSet.isEmpty()){
			hashMap.put("sendNums", new ArrayList<String>(numSet));
		}
		long h3 = System.currentTimeMillis();
		logger.info("订单短信群发:筛选出重复手机号,时间开销"+(h3-h2)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		if(!wrongNums.isEmpty()){
			hashMap.put("wrongNums", wrongNums);
		}
		//去除黑名单手机号(黑名单手机号和黑名单昵称)
		List<String> blackPhoneList = smsBlackListService.findAllPhones(userId);
		if(numSet != null && !numSet.isEmpty()){
			if(blackPhoneList != null){
				for (String blackPhone : blackPhoneList) {
					if(numSet.contains(blackPhone)){
						blackNums.add(blackPhone);
					}
				}
				numSet.removeAll(blackNums);
			}
			hashMap.put("sendNums", new ArrayList<String>(numSet));
			if(blackNums != null && !blackNums.isEmpty()){
				hashMap.put("blackNums", blackNums);
			}
		}
		return hashMap;
	}
	
	
	/**
	 * 订单短信群发:保存总记录
	 */
	private MsgSendRecord saveMsgRecord(SendMsgVo sendMsgVo,Date sendTime,String status){
		MsgSendRecord msg = new MsgSendRecord();
		msg.setActivityName(sendMsgVo.getActivityName());
		msg.setTotalCount(Integer.parseInt(sendMsgVo.getTotalCount().toString()));
		msg.setTemplateContent(sendMsgVo.getContent());
		msg.setUserId(sendMsgVo.getUserId());
		msg.setType(sendMsgVo.getMsgType());
		msg.setSendCreat(sendTime);
		msg.setIsShow(true);
		//是定时发送--false 立即发送true
		msg.setIsSent(!sendMsgVo.getSchedule());
		msg.setStatus(status);
		//保存总记录返回总记录Id
		msgSendRecordService.saveMsg(msg);
		return msg;
	}
	
	/**
	 * 日志批量上传订单号
	 * @Title: asdasdasda 
	 * @param @param tidList
	 * @param @param logAccessDTO
	 * @param @throws InterruptedException 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	public void orderLogBatch(final List<String> tidList,final LogAccessDTO logAccessDTO) throws InterruptedException{
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
			public void run(){
				List<LogAccessDTO> logList = new ArrayList<>();
				Integer end = 0,start = 0, node=100, dataSize = tidList.size();
				List<String> subTids =null;
				if(dataSize / node==0){
					end	= 1;
				}else if(dataSize % node==0){
					end = dataSize / node;
				}else{
					end = (dataSize + node) / node;
				}
				while(start < end){
					if(start == (end - 1)){
						subTids = tidList.subList(start * node, dataSize);
					}else{
						subTids = tidList.subList(start * node, (start + 1) * node);
					}
					if(subTids == null) continue;
					start++;
					LogAccessDTO log = new LogAccessDTO();
					log.setTradeIds(StringUtils.join(subTids, ","));
					log.setTime(logAccessDTO.getTime());
					log.setUrl(logAccessDTO.getUrl());
					log.setUserId(logAccessDTO.getUserId());
					log.setUserIp(logAccessDTO.getUserIp());
					log.setOperation("发送订单短信");
					log.setAti(logAccessDTO.getAti());
					logList.add(log);
				}
				String logListJson = JSONArray.fromObject(logList).toString();
				Map<String, Object> map = new HashMap<>();
				LogAccessDTO batchLog = new LogAccessDTO();
				batchLog.setData(logListJson);
				batchLog.setMethod("order");
				batchLog.setTime(logAccessDTO.getTime());
				map.put(LogAccessDTO.class.getName(), batchLog);
				map.put(LogType.class.getName(), LogType.BATCH_LOG_TYPE);
				try {
					LogAccessQueueService.queue.put(map);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
}
