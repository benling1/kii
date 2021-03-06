package s2jh.biz.shop.crm.message.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.service.DistributedLock;
import lab.s2jh.core.service.RedisLockServiceImpl;
import lab.s2jh.core.util.DateUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
import s2jh.biz.shop.crm.manage.service.BatchOperateSmsService;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.vo.MemberCriteriaVo;
import s2jh.biz.shop.crm.manage.vo.SendMsgVo;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.processor.SendAble;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.vip.service.VipUserService;
import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.support.service.MultithreadBatchSmsService;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.phoneRegExp.PhoneRegUtils;

import com.taobao.api.SecretException;
/** 
* @ClassName: MsgSendService 
* @Description: TODO(会员短信群发>>发送短信) 
* @author jackstraw_yu 
* @date 2017年5月3日 上午10:16:58 
*  
*/
@Service
public class MsgSendService{

	@Autowired
	private MultithreadBatchSmsService multithreadService;
	@Autowired
	private SmsRecordService smsRecordService;
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private DistributedLock distributedLock;
	@Autowired
	private VipMemberService vipMemberService;
	@Autowired
	private MobileSettingService mobileSettingService;
	@Autowired
	private BatchOperateSmsService batchOperateSmsService;
	@Autowired
	private SmsSendRecordService smsSendRecordService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	@Autowired
	private VipUserService vipUserService;
	
	private static final Log logger = LogFactory.getLog(MsgSendService.class);
	
	
	public void sendBatchMsg(SendMsgVo sendMsgVo) throws Exception {
		if(sendMsgVo.getTotalCount() ==null || sendMsgVo.getTotalCount() ==0 || sendMsgVo.getSchedule()==null)
			throw new Exception("会员短信群发数据筛选异常!");
//		try {
//			distributedLock.tryLock(RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+sendMsgVo.getQueryKey()+"-"+sendMsgVo.getUserId());
			/*MemberCriteriaVo memberCriteriaVo = cacheService.get(RedisConstant.RedisCacheGroup.MEMBER_BATCH_SEND_DATA_CACHE, 
					RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+sendMsgVo.getQueryKey()+"-"+sendMsgVo.getUserId(), MemberCriteriaVo.class);*/
			MemberCriteriaVo memberCriteriaVo =redisLockServiceImpl.getValue(
					RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+sendMsgVo.getQueryKey()+"-"+sendMsgVo.getUserId(),
					MemberCriteriaVo.class);
			if(null!=memberCriteriaVo){
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
					processMsgSendData(sendMsgVo,memberCriteriaVo,msg,startTime,endTime);
				}else{
					processScheduleData(sendMsgVo,memberCriteriaVo,msg,startTime,endTime);
				}
			}
//		}finally{  
//			distributedLock.unLock(RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+sendMsgVo.getQueryKey()+"-"+sendMsgVo.getUserId());
//		}
	}
	


	/**
	 * 立即发送:处理短信群发数据
	 * 一,筛选错误手机号,重复手机号....
	 * 四,更新总表
	 */
	@SuppressWarnings("unchecked")
	private void processMsgSendData(final SendMsgVo sendMsgVo,final MemberCriteriaVo memberCriteriaVo,
									final MsgSendRecord msg,
									final Date startTime, final Date endTime){
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
			@Override
			public void run() {
				//辅助验证的集合
				Set<String> vSet = new HashSet<String>();
			    List<String> wrongNums = new ArrayList<String>(),repeatNums = new ArrayList<String>();
			    Map<String,Object> sortMap = null;
			    int successNo =0,errorNo=0;
			    boolean isLast = false;
				long end = 0l,start = 0l,exist=0l,unExist=0l, totalCount = sendMsgVo.getTotalCount();
				if(totalCount/ConstantUtils.PROCESS_PAGE_SIZE_MAX==0){
					end  = 1l;
				}else if(totalCount%ConstantUtils.PROCESS_PAGE_SIZE_MAX==0){
					 end  = totalCount/ConstantUtils.PROCESS_PAGE_SIZE_MAX;
				}else{
					 end  = (totalCount+ConstantUtils.PROCESS_PAGE_SIZE_MAX)/ConstantUtils.PROCESS_PAGE_SIZE_MAX;
				}
				memberCriteriaVo.setPageSize(ConstantUtils.PROCESS_PAGE_SIZE_MAX);
				while(start<end){
					if(start == (end-1)){
						memberCriteriaVo.setPageSize(totalCount-start*ConstantUtils.PROCESS_PAGE_SIZE_MAX);
					}
					logger.info("会员短信群发-立即发送:第"+(start+1)+"次循环^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					memberCriteriaVo.setStartRows(start*ConstantUtils.PROCESS_PAGE_SIZE_MAX);
					long s1 = System.currentTimeMillis();
					/***
					 * 查询出数据
					 **/
					logger.info("会员短信群发-立即发送:筛选条件打印"+"\r\n"+memberCriteriaVo.toString()+"\r\n");
					List<String> nums  =  vipMemberService.findSendMemberInfoList(memberCriteriaVo,memberCriteriaVo.getUserId());
					start++;
					if(nums==null || nums.isEmpty()){
						unExist++;
						continue;
					}
					exist++;
					logger.info("会员短信群发-立即发送:查询出"+(nums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					logger.info("会员短信群发-立即发送:此次查询时间开销"+(System.currentTimeMillis()-s1)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					List<String> decNums = decryptMemeberMobiles(nums,sendMsgVo.getUserId());
					if(decNums==null || decNums.isEmpty()){
						logger.info("会员短信群发-立即发送:此次查询结果为空!"+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
						continue;
					}
					logger.info("会员短信群发-立即发送:解密后的数据大小为"+(decNums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					//筛选出重复手机号,错误手机号.....
					sortMap = sortDataBatch(decNums,vSet);
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
						List<String> sendNums = (ArrayList<String>)sortMap.get("sendNums");
						if(sendNums.size()>0){
							//successNo+=sendNums.size();
							if((exist+unExist)==end) isLast = true;//TODO 最后一次发送
							Map<String, Integer> resultMap = batchSendMsg( sendNums.toArray(new String[0]),sendMsgVo,isLast);
							if(resultMap!=null&&!resultMap.isEmpty()){
								successNo += (resultMap.get("succeedNum")==null?0:resultMap.get("succeedNum"));
								errorNo += (resultMap.get("failedNum")==null?0:resultMap.get("failedNum"));
							}
						}
					}
					vSet.addAll(nums);
				}
				if(wrongNums.size()>0){
					msg.setWrongCount(wrongNums.size());
					logger.info("会员短信群发-立即发送:筛选出手机号错误,共"+(wrongNums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					smsRecordService.saveErrorMsgNums(encryptMemeberMobiles(wrongNums,sendMsgVo.getUserId()),sendMsgVo,MsgType.SMS_STATUS_WRONGNUM,startTime);
					//smsSendRecordService.saveErrorMsgNums(wrongNums,sendMsgVo,MsgType.SMS_STATUS_WRONGNUM,startTime);
				}
				if(repeatNums.size()>0){
					msg.setRepeatCount(repeatNums.size());
					logger.info("会员短信群发-立即发送:筛选出手机号重复,共"+(repeatNums.size())+"条数据 ^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					smsRecordService.saveErrorMsgNums(encryptMemeberMobiles(repeatNums,sendMsgVo.getUserId()),sendMsgVo,MsgType.SMS_STATUS_REPEATNUM,startTime);
					//smsSendRecordService.saveErrorMsgNums(repeatNums,sendMsgVo,MsgType.SMS_STATUS_REPEATNUM,startTime);
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
	 * 去除重复手机号,错误手机号...
	 * */
	@SuppressWarnings("unchecked")
	private void processScheduleData(final SendMsgVo sendMsgVo, final MemberCriteriaVo memberCriteriaVo, 
									 final MsgSendRecord msg,
									 final Date startTime,final Date endTime) {
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
			@Override
			public void run() {
				Set<String> vSet = new HashSet<String>();
				List<String> sendNums = new ArrayList<String>(),wrongNums = new ArrayList<String>(),repeatNums = new ArrayList<String>();
				Map<String,Object> sortMap = null;
				Integer wrongNo = null,repeatNo = null /*,successNo =0*/;
				long end = 0l,start = 0l;
				long totalCount = sendMsgVo.getTotalCount();
				if(totalCount/ConstantUtils.PROCESS_PAGE_SIZE_MAX==0){
					end  = 1l;
				}else if(totalCount%ConstantUtils.PROCESS_PAGE_SIZE_MAX==0){
					 end  = totalCount/ConstantUtils.PROCESS_PAGE_SIZE_MAX;
				}else{
					 end  = (totalCount+ConstantUtils.PROCESS_PAGE_SIZE_MAX)/ConstantUtils.PROCESS_PAGE_SIZE_MAX;
				}
				memberCriteriaVo.setPageSize(ConstantUtils.PROCESS_PAGE_SIZE_MAX);
				while(start<end){
					if(start == (end-1)){
						memberCriteriaVo.setPageSize(totalCount-start*ConstantUtils.PROCESS_PAGE_SIZE_MAX);
					}
					memberCriteriaVo.setStartRows(start*ConstantUtils.PROCESS_PAGE_SIZE_MAX);
					logger.info("会员短信群发-定时发送:第"+(start+1)+"次循环^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					long s1 = System.currentTimeMillis();
					/**
					 * 查询出数据
					 * **/
					List<String> nums  =  vipMemberService.findSendMemberInfoList(memberCriteriaVo,memberCriteriaVo.getUserId());
					start++;
					if(nums==null || nums.isEmpty()){
						continue;
					}
					logger.info("会员短信群发-定时发送:查询出"+(nums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					logger.info("会员短信群发-定时发送:此次查询时间开销"+(System.currentTimeMillis()-s1)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					List<String> decNums = decryptMemeberMobiles(nums,sendMsgVo.getUserId());
					if(decNums==null || decNums.isEmpty()){
						continue;
					}
					logger.info("会员短信群发-立即发送:解密后的数据大小为"+(decNums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					//过滤数据
					sortMap = sortDataBatch(decNums,vSet);
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
					vSet.addAll(nums);
				}
				if(sendNums.size()>0){
					//successNo+=sendNums.size();
					splitScheduleData(encryptMemeberMobiles(sendNums,sendMsgVo.getUserId()),sendMsgVo,startTime,endTime);
				}
				if(wrongNums.size()>0){
					wrongNo = wrongNums.size();
					logger.info("会员短信群发-定时发送:筛选出手机号错误,共"+(wrongNums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					smsRecordService.saveErrorMsgNums(encryptMemeberMobiles(wrongNums,sendMsgVo.getUserId()),sendMsgVo,MsgType.SMS_STATUS_WRONGNUM,startTime);
					//smsSendRecordService.saveErrorMsgNums(wrongNums,sendMsgVo,MsgType.SMS_STATUS_WRONGNUM,startTime);
				}
				if(repeatNums.size()>0){
					repeatNo = repeatNums.size();
					logger.info("会员短信群发-定时发送:筛选出手机号重复,共"+(wrongNums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
					smsRecordService.saveErrorMsgNums(encryptMemeberMobiles(repeatNums,sendMsgVo.getUserId()),sendMsgVo,MsgType.SMS_STATUS_REPEATNUM,startTime);
					//smsSendRecordService.saveErrorMsgNums(wrongNums,sendMsgVo,MsgType.SMS_STATUS_WRONGNUM,startTime);
				}
				//更新总记录
				//msg.setSucceedCount(successNo);
				//定时这里设置一个0值
				msg.setSucceedCount(0);
				msg.setFailedCount(0);
				msg.setRepeatCount(repeatNo);
				msg.setWrongCount(wrongNo);
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
		logger.info("会员短信群发-定时发送:"+sendMsgVo.getUserId()+"保存"+(sendNums.size())+"条定时数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		//Integer end = 0,start = 0,node=200000,dataSize = sendNums.size();
		//加密后单条手机号长度变长:原理13位加密后54位
		Integer end = 0,start = 0,node=100000,dataSize = sendNums.size();
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
			logger.info("会员短信群发-定时发送:"+sendMsgVo.getUserId()+"保存第"+start+"次组装的数据,数据量为:"+subNums.size()+"条^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
			saveMsgScheduleBatch(subNums,sendMsgVo,startTime,endTime);
		}

	}
	
	/**
	 * 调用短信群发接口,返回成功失败的条数 
	 */
	public Map<String,Integer> batchSendMsg(String [] phones,SendMsgVo sendMsgVo,boolean isLast) {
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
		/*是否是最后一批发送*/
		batchSmsData.setLast(isLast);
		/*是否是立即发送*/
		batchSmsData.setNow(true);
		////batchOperateSmsService.batchOperateSms(batchSmsData);
		multithreadService.batchOperateSms(batchSmsData);
		int sNum = batchSmsData.getSuccess();
		int fNum = batchSmsData.getFail();
		Map<String,Integer> resultMap =null;
		if(sNum!=0 || fNum!=0){
			resultMap = new HashMap<String,Integer>();
			resultMap.put("succeedNum", sNum);
			resultMap.put("failedNum", fNum);
		}
		logger.info("会员短信群发-立即发送:批量发送时间开销:"+(System.currentTimeMillis()-s)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
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
			logger.error("会员短信群发-定时发送:保存单笔定时数据失败!!^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		}
		logger.info("会员短信群发-定时发送:保存单笔定时数据时间开销:"+(System.currentTimeMillis()-s)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
	}

	
	
	
	
	
	/** 
	*  核心方法
	*  (会员短信群发:大数据量时筛选手机号错误,手机号重复) 
	* @author jackstraw_yu    
	*/
	private Map<String, Object>  sortDataBatch(List<String> nums,Set<String> vSet) {
		logger.info("会员短信群发:核心筛选区接收到"+(nums.size())+"条数据^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		List<String> mList =  new ArrayList<String>(nums),wrongNums = new ArrayList<String>(),repeatNums = new ArrayList<String>();
		Map<String,Object> hashMap = new HashMap<String,Object>();
		String str = null;
		Set<String> numSet =null,vNumSet =null;
		Iterator<String> iterator = mList.iterator();
		long h1 = System.currentTimeMillis();
		//校验会员手机号--订单抽取出的会员信息手机号有的会有问题,需要进行正则校验!
		while(iterator.hasNext()){
			str = iterator.next();
			if(str==null || !PhoneRegUtils.phoneValidate(str)){
				wrongNums.add(str);
				iterator.remove();
			}
		}
		long h2 = System.currentTimeMillis();
		logger.info("会员短信群发:送筛选出错误手机号,时间开销"+(h2-h1)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
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
		logger.info("会员短信群发:筛选出重复手机号,时间开销"+(h3-h2)+"millis^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
		if(!wrongNums.isEmpty()){
			hashMap.put("wrongNums", wrongNums);
		}
		return hashMap;
	}
	
	
	/**
	 * 会员短信群发:保存总记录
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
	 * @Description:会员短信群发,解密手机号
	 * @author jackstraw_yu
	 */
	private List<String> decryptMemeberMobiles(List<String> mobiles,String nick) {
		EncrptAndDecryptClient client = null; 
		List<String> list = null;
		String key = null;
		if(mobiles!=null && mobiles.size()>0){
			client =  EncrptAndDecryptClient.getInstance();
			list = new ArrayList<String>();
			key = getSessionkey(nick,true);
			for (String num : mobiles) {
				try {
					if(!EncrptAndDecryptClient.isEncryptData(num, EncrptAndDecryptClient.PHONE)){
						list.add(num);
					}else{
						try {
							list.add(client.decrypt(num, EncrptAndDecryptClient.PHONE,key));
						} catch (Exception e) {
							logger.error("会员短信群发单条手机号解密失败!");
							key = getSessionkey(nick,false);
							try {
								list.add(client.decrypt(num, EncrptAndDecryptClient.PHONE,key));
							} catch (Exception e1) {
								logger.error("会员短信群发单条手机号<>再次<>解密失败!");
								//break;
							}
							continue;
						}
					}
				} catch (SecretException e) {
					logger.error("判断会员短信群发单条手机号是否加密失败!...手机号内容:"+num);
					continue;
				}
			}	
			return  list.isEmpty()?null:list;
		}
		
		return list;
	}
	
	
	/**
	 * @Description:会员短信群发,加密手机号
	 * @author jackstraw_yu
	 */
	private List<String> encryptMemeberMobiles(List<String> mobiles,String nick) {
		EncrptAndDecryptClient client = null; 
		List<String> list = null;
		String key = null,encrypt = null;
		if(mobiles!=null && mobiles.size()>0){
			client =  EncrptAndDecryptClient.getInstance();
			list = new ArrayList<String>();
			key = getSessionkey(nick,true);
			for (String num : mobiles) {
				try {
					encrypt = client.encrypt(num, EncrptAndDecryptClient.PHONE,key);
					list.add(encrypt);
				} catch (Exception e) {
					logger.error("会员短信群发单条手机号加密失败!");
					key = getSessionkey(nick,false);
					try {
						encrypt = client.encrypt(num, EncrptAndDecryptClient.PHONE,key);
						list.add(encrypt);
					} catch (Exception e1) {
						logger.error("会员短信群发单条手机号<>再次<>加密失败!");
						//break;
					}
					continue;
				}
			}	
			return list.isEmpty()?null:list;
		}
		
		return list;
	}
	
	
	
	/**
	 * @Description:会员短信群发,加密手机号--批量
	 * @author jackstraw_yu
	 */
	@Deprecated
	private List<String> encryptMobilesBatch(List<String> mobiles,String nick) {
		EncrptAndDecryptClient client = null; 
		List<String> list = null;
		String key = null;
		Map<String, String> encrypt = null;
		if(mobiles!=null && mobiles.size()>0){
			client =  EncrptAndDecryptClient.getInstance();
			list = new ArrayList<String>();
			key = getSessionkey(nick,true);
			try {
				encrypt = client.encrypt(mobiles, EncrptAndDecryptClient.PHONE,key);
				list = new ArrayList<String>(encrypt.values());
			} catch (SecretException e) {
				logger.error("会员短信群发批量手机号加密失败!");
				key = getSessionkey(nick,false);
				try {
					encrypt = client.encrypt(mobiles, EncrptAndDecryptClient.PHONE,key);
					list = new ArrayList<String>(encrypt.values());
				} catch (SecretException e1) {
					logger.error("会员短信群发批量手机号<>再次<>加密失败!");
				}
			}
		}
		return list;
	}
	
	
	/**
	 * @Description:会员短信群发,解密手机号--批量
	 * @author jackstraw_yu
	 */
	@Deprecated
	private List<String> decryptMobilesBatch(List<String> mobiles,String nick) {
		EncrptAndDecryptClient client = null; 
		List<String> list = null;
		String key = null;
		Map<String, String> decrypt = null;
		if(mobiles!=null && mobiles.size()>0){
			client =  EncrptAndDecryptClient.getInstance();
			key = getSessionkey(nick,true);
			try {
				if(EncrptAndDecryptClient.isPartEncryptData(mobiles, EncrptAndDecryptClient.PHONE)){
					decrypt = client.decrypt(mobiles, EncrptAndDecryptClient.PHONE, key);
					list = new ArrayList<String>(decrypt.values());
				}else{
					return mobiles;
				}
			} catch (SecretException e) {
				logger.error("会员短信群发批量手机号解密失败!");
				key = getSessionkey(nick,false);
				try {
					if(EncrptAndDecryptClient.isPartEncryptData(mobiles, EncrptAndDecryptClient.PHONE)){
						decrypt = client.decrypt(mobiles, EncrptAndDecryptClient.PHONE, key);
						list = new ArrayList<String>(decrypt.values());
					}else{
						return mobiles;
					}
				} catch (SecretException e1) {
					logger.error("会员短信群发批量手机号<>再次<>解密失败!");
				}
			}
		}
		return list;
	}
	
	
	
	
	
	/**
	 * @Description:获取加密解密使用 的token
	 * @Copy_author jackstraw_yu
	 */
	 private  String 	getSessionkey(String userNickName,boolean flag){
		    String  token = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName);
			if(null!=token&&!"".equals(token)&&flag){
				 return token;
			}else{
				UserInfo user = userInfoService.queryUserTokenInfo(userNickName);
				if(user!=null)
					if(null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
						 cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName,user.getAccess_token());
						 return user.getAccess_token(); 
					}
			}
			return "";
	   }
	

	/**
	 * 使用多线程发送个性短信,此方法暂时废弃
	 */
	@Deprecated
	public Map<String,Integer> multiThreadSendMsg(List<MemberInfo> sendList,String content,String type,String autograph,
			String userId,String ip) {
		List<MemberInfo> list = new CopyOnWriteArrayList<MemberInfo>();
		list.addAll(sendList);
		int succeedNum = 0;
		int failedNum = 0;
		// 创建SmsSendInfo封装数据调用短信发送方法
		if(list.size()<=50){
			String [] phones =  new String[1];
			for (int i = 0; i < sendList.size(); i++) {
				phones[0] = sendList.get(i).getPhone();
				BatchSmsData  batchSmsData = new BatchSmsData(phones);
				batchSmsData.setType(type);
				batchSmsData.setIpAdd(ip);
				batchSmsData.setChannel(autograph);
				batchSmsData.setAutograph(autograph);
				batchSmsData.setUserId(userId);
				batchSmsData.setContent(content.replace("【买家昵称】", sendList
						.get(i).getBuyerNick()));
				//multithreadService.batchOperateSms(batchSmsData);
				succeedNum += batchSmsData.getSuccess();
				failedNum += batchSmsData.getFail();
			}	
		}else{
			List<SendAble> processor =  new ArrayList<SendAble>();
			//ExecutorService pool = Executors.newCachedThreadPool();
			ExecutorService pool = MyFixedThreadPool.getMyFixedThreadPool();
			List<Future<String>> result =  null;
			String status = "";
			//创建50个线程
			for (int i = 0; i < 50; i++) {
				SendAble sendAble = new SendAble();
				processor.add(sendAble);
			}
			//数量大于50即使用50个线程来处理数据
			logger.info("开始多线程发送短信,要处理的数据量:"+list.size());
			out:for (int i = 0; i < (list.size()+50)/50; i++) {
				logger.info("第"+i+"次批量处理数据!");
				result =  new ArrayList<Future<String>>();
				//执行线程
				for (int j = i*50,n = 0; n < processor.size(); j++,n++) {
					if(j >= list.size()) break out;
					String [] phones =  new String[1];
					phones[0] = sendList.get(j).getPhone();
					logger.info("该次要发送的手机号为:"+Arrays.toString(phones));
					BatchSmsData  batchSmsData = new BatchSmsData(phones);
					batchSmsData.setType(type);
					batchSmsData.setIpAdd(ip);
					batchSmsData.setChannel(autograph);
					batchSmsData.setAutograph(autograph);
					batchSmsData.setUserId(userId);
					batchSmsData.setContent(content.replace("【买家昵称】", sendList
							.get(j).getBuyerNick()));
					//multithreadService.batchOperateSms(batchSmsData);
					processor.get(n).setbData(batchSmsData);
					result.add(pool.submit(processor.get(n)));//此处 已经执行“批量”发送!
				}
				//获取结果
				for (int t = 0; t < result.size(); t++) {
					//获得第一个的结果，如果调用get方法，当前线程会等待任务执行完毕后才往下执行
					try {
						status = result.get(t).get();
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					if(status !=null && "success".equals(status)){
						succeedNum++;
					}else{
						failedNum++;
					}
				}
			}
		}
		Map<String,Integer> resultMap =null;
		if(succeedNum!=0 || failedNum!=0){
			resultMap = new HashMap<String,Integer>();
			resultMap.put("succeedNum", succeedNum);
			resultMap.put("failedNum", failedNum);
		}
		return resultMap;
	}
	
}
