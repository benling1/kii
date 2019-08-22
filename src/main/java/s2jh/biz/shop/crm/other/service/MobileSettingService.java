package s2jh.biz.shop.crm.other.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.other.dao.MobileSettingDao;
import s2jh.biz.shop.crm.other.entity.MobileSetting;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.info.ReturnMessage;
import s2jh.biz.shop.crm.taobao.info.SendMessageStatusInfo;
import s2jh.biz.shop.crm.taobao.util.SendMessageUtilForHangYe;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.JsonUtil;

@Service
public class MobileSettingService extends BaseService<MobileSetting, Long> {

	private static final Log logger = LogFactory.getLog(MobileSettingService.class);
	@Autowired
	private MobileSettingDao mobileSettingDao;

	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	protected BaseDao<MobileSetting, Long> getEntityDao() {
		return mobileSettingDao;
	}
	
	/*private final String  EXPIRATION_TIME= "expirationTime";
	private final String  EXPEDITING= "expediting";
	private final String  SEND_COUNT= "sendCount";
	private final String  CURRENT_COUNT= "currentCount";*/
	
	/**
	 * 催付短信的类型
	 * */
	private final List<String> SMS_TYPES_MAP = new ArrayList<String>(){
		private static final long serialVersionUID = -1931728931221564237L;
		{add("2");add("3");add("4");}
	};
	
	/** 
	* @Description: 根据用户昵称查询后台管理设置
	* @param  userId
	* @return MobileSetting    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月28日 上午10:22:39
	*/
	public MobileSetting findMobileSetting(String userId) {
		return myBatisDao.findBy(MobileSetting.class.getName(),"findMobileSetting", userId);
	}
	
	

	/** 
	* @Description 初始化保存后台管理设置:<br/> 
	* 1,保存一条初始化后台设置<br/>
	* 2,补全用户手机号,标记赠送短信<br/>
	* 3,赠送用户500条短信
	* @param  mobileSetting   后台管理设置 
	* @return Long    返回保存主键
	* @author jackstraw_yu
	* @date 2017年11月27日 下午7:01:11
	*/
	@Transactional
	public Long saveInitMobileSetting(MobileSetting mobileSetting) {
		MobileSetting result = this.findMobileSetting(mobileSetting.getUserId());
		if(result != null)
			throw new RuntimeException("用户的后台管理设置已经存在!! "+mobileSetting.getUserId());
		//1保存用户初始化话后台设置
		Long primaryKey = this.saveMobileSetting(mobileSetting);
		//2.保存用户手机号,赠送短信标记
		userInfoService.saveUserMobileInfo(mobileSetting.getPhoneNum(), null, mobileSetting.getUserId());
		//3.赠送用户500条短信余额
		boolean excute = userAccountService.doUpdateUserSms(mobileSetting.getUserId(), SendMessageStatusInfo.ADD_SMS, 
				ConstantUtils.INDEX_SEND_SMS_NUM, "首次保存后台管理设置", mobileSetting.getUserId(), null, "首次保存后台管理设置赠送500条短信",UserAccountService.NO_TIME);
		if(!excute){
			throw new RuntimeException("保存手机号码送500条短信 失败! :"+mobileSetting.getUserId());
		}
		return primaryKey;
	}


	/** 
	* @Description: 更新后台管理设置
	* @param  mobileSetting   后台管理设置 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月27日 下午7:03:35
	* @throws 
	*/
	@Transactional
	public void updateMobileSetting(MobileSetting mobileSetting) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expediting", mobileSetting.getExpediting());
		map.put("messageRemainder", mobileSetting.getMessageRemainder());
		map.put("messageCount", mobileSetting.getMessageCount());
		map.put("serviceExpire", mobileSetting.getServiceExpire());
		map.put("activityNotice", mobileSetting.getActivityNotice());
		map.put("phoneNum", mobileSetting.getPhoneNum());
		map.put("userId", mobileSetting.getUserId());
		map.put("id", mobileSetting.getId());
		int execute = myBatisDao.execute(MobileSetting.class.getName(),"updateMobileSetting", map);
		if(execute != 1)
			throw new RuntimeException("影响的行数不为1! :"+mobileSetting.getUserId());	
	}
	
	
	/** 
	* @Description: 保存后台管理设置
	* @param  mobileSetting    设定文件 
	* @return Long    返回保存主键
	* @author jackstraw_yu
	* @date 2017年11月28日 上午9:52:10
	*/
	@Transactional
	public Long saveMobileSetting(MobileSetting mobileSetting) {
		//返回主键
		myBatisDao.execute(MobileSetting.class.getName(),"insertMobileSetting", mobileSetting);
		if(mobileSetting.getId() ==null)
			throw new RuntimeException("MobileSetting:can not return primary key after saving a mobileSetting !");
		return mobileSetting.getId();
	}
	
	
//#####################################后台管理设置的功能实现#####################################
//#####################################后台管理设置的功能实现#####################################


	/** 
	* @Description: 检索后台管理设置,实现设置的功能<br/>
	* 催付效果，短信发送量和当前短信条数（每天9点发送前一天数据）<br/> 
	* 软件过期提醒<br/> 
	* 最新促销活动通知(暂时没有该功能)<br/> 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月28日 下午12:17:46
	*/
	//@Transactional:不需要事务控制
	public void scanMobileSettingTable(){
		//单线程的定时=====几万的数据能够运行
		//此方法由定时调用,定时时间每天上午9点
		//......联表查询后台管理设置表,用户表,用户余额表
		//分页处理,每页1000条
		//sql附加条件 服务未过期;其他判断由代码处理
		long count =  myBatisDao.findBy(MobileSetting.class.getName(),
									"querySettingCountJoinUser", null);
		logger.info("######################### 后台管理设置A,查询出条数:"+count);
		if(count==0) return; 
		long end = 0,start = 0,page=1000;
		if(count<page){
			end	= 1;
		}else if(count%page==0){
			end  = count/page;
		}else{
			end  = (count+page)/page;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("pageSize", page);
		while(start<end){
			map.put("startRow", start*page);
			if(start == (end-1)){
				map.put("pageSize", count-start*page);
			}
			this.processMobileSettingList(map);
			start++;
		}
	}
	
	/** 
	* @Description: 处理当前一批后台管理设置
	* @param  map    设定文件 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月28日 下午2:19:54
	*/
	private void processMobileSettingList(Map<String, Object> map) {
		long l = System.currentTimeMillis();
		List<MobileSetting> list =  myBatisDao.findList(MobileSetting.class.getName(),
				"querySettingPageJoinUser", map);
		logger.info("######################### 后台管理设置A:联表查询一批数据耗时: "+(System.currentTimeMillis()-l));
		if(list==null || list.isEmpty()) return;
		ArrayList<Map<String, Object>> sendData = new ArrayList<Map<String,Object>>();
		HashMap<String, Object> dataMap = null;
		String userId = null,phoneNum = null;
		Long expediting = null,sendCount = null,currentCount = null;
		Date expirationTime = null;
		for (MobileSetting setting : list) {
			userId = setting.getUserId();
			phoneNum = setting.getPhoneNum();
			expirationTime = setting.getExpirationTime();
			if(userId ==null||"".equals(userId)||phoneNum==null||"".equals(phoneNum)) continue;
			if(expirationTime ==null || expirationTime.before(new Date())) continue;
			dataMap = new HashMap<String,Object>();
			if(setting.getServiceExpire() !=null && setting.getServiceExpire() ==true){
				//判断是否将在7天之内过期
				if( (expirationTime.getTime()-System.currentTimeMillis()) <=ConstantUtils.EXPIRATION_TIME_SEND)
					dataMap.put("expirationTime", expirationTime);
			}
			if(setting.getExpediting() !=null && setting.getExpediting() == true){
				//为避免打扰客户，日消耗短信总量100条以上才会发送【催付效果、短信发送量和当前短信条数】短信 
				//1.1短信发送总量(每天9点发送前一天数据)
				sendCount = smsRecordService.querySendCountByType(userId,DateUtils.caculateDate(-1),null);
				if(sendCount !=null && sendCount.longValue()>=ConstantUtils.COUNT_SEND.longValue()){
					//1.2催付发送数量
					expediting = smsRecordService.querySendCountByType(userId,DateUtils.caculateDate(-1),this.SMS_TYPES_MAP);
					//1.3当前短信条数
					currentCount = setting.getSmsNum();
					dataMap.put("expediting", expediting==null?0:expediting);
					dataMap.put("sendCount", sendCount);
					dataMap.put("currentCount", currentCount==null?0:currentCount);
				}
			}
			
			/**
			 * //3短信余额不足报警：剩余条数不足XX条开始提醒<====>不在定时任务的范畴
			 * if(ms.getMessageRemainder() !=null && ms.getMessageRemainder()){
			 * 		//短信余额messageCount
			 * }
			 * //4最新促销活动通知b---暂时不做
			 * if(ms.getActivityNotice() !=null && ms.getActivityNotice()){
			 * 
			 * }
			 * */
			if(dataMap !=null && dataMap.size()>0){
				dataMap.put("userId", userId);
				dataMap.put("phoneNum", phoneNum);
				sendData.add(dataMap);
			}
		}
		//调用发送短信的方法
		if(sendData !=null && sendData.size()>0)
			sendMessage(sendData);
	}

	/** 
	* @Description: 后台管理设置,实现功能发送短信
	* @param  list    设定文件(每个元素map代表一个客户) 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月28日 下午4:30:35
	*/
	private void sendMessage(List<Map<String, Object>> list){
		/**
		 * ArrayList<Map<String, Object>> sendList
		 * 单个Map<String,Object>代表单个用户的短信发送
		 */
		String userId = null, phoneNum = null;
		Long expediting = null,sendCount = null,currentCount = null;
		Date expirationTime = null;
		String eTimeStr = null,EXPEDITING_CONTNET = null,SERVICEEXPIRE_CONTNET = null;
		String [] phones = new String[1];
		for (Map<String, Object> map : list) {
			//催付提醒
			EXPEDITING_CONTNET = ConstantUtils.MESSAGE_EXPEDITING_CONTNET;
		 	//软件过期提醒
		 	SERVICEEXPIRE_CONTNET = ConstantUtils.MESSAGE_SERVICEEXPIRE_CONTNET;
			//用户与手机号不用判空
			userId = (String) map.get("userId");
			phoneNum =  (String) map.get("phoneNum");
			phones[0] = phoneNum;
			if(map.get("expediting") != null && map.get("sendCount") != null 
											 && map.get("currentCount") != null){
				expediting = (Long) map.get("expediting");
				sendCount = (Long) map.get("sendCount");
				currentCount = (Long) map.get("currentCount");
				EXPEDITING_CONTNET =EXPEDITING_CONTNET.replace("USERID", userId)
						.replace("CURRENTCOUNT", currentCount+"").replace("SENDCOUNT", sendCount+"").replace("EXPEDITING", expediting+"");
				SendMessageUtilForHangYe.sendMessage(phones, EXPEDITING_CONTNET, null, null);
				//System.out.println("##################1:"+EXPEDITING_CONTNET);
			}
			if(map.get("expirationTime") != null){
				expirationTime = (Date) map.get("expirationTime"); 
				eTimeStr =  DateUtils.formatDate(expirationTime, "yyyy-MM-dd");
				SERVICEEXPIRE_CONTNET = SERVICEEXPIRE_CONTNET.replace("USERID", userId).replace("EXPIRATIONTIME", eTimeStr);
				SendMessageUtilForHangYe.sendMessage(phones, SERVICEEXPIRE_CONTNET, null, null);
				//System.out.println("##################2:"+SERVICEEXPIRE_CONTNET);
			}
		}
	}
	
	/** 
	* @Description: 定时 联表查询用户的后台管理设置,用户表和用户余额表:<br/>
	* 1,用户不能过期(SQL已判断)<br/>
	* 2,余额不足提醒开启;且设置提醒条数大于实际余额(SQL已判断)<br/>
	* 3,满足余额提醒发送提醒短信,持续一个星期
	* 4,定时>>每天8点  ~ 晚上9点 每隔一个小时
	* @param     设定文件 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月28日 下午4:55:47
	*/
	//@Transactional:只涉及查询,不需要事务控制
	public void scanUserSmsCountRemind(){
		long count =  myBatisDao.findBy(MobileSetting.class.getName(),"querySettingCountJoinUserAccount", null);
		logger.info("######################### 后台管理设置B,查询出条数: "+count);
		if(count==0) return; 
		long end = 0,start = 0,page=1000;
		if(count<page){
			end	= 1;
		}else if(count%page==0){
			end  = count/page;
		}else{
			end  = (count+page)/page;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("pageSize", page);
		while(start<end){
			map.put("startRow", start*page);
			if(start == (end-1)){
				map.put("pageSize", count-start*page);
			}
			this.processSmsCountRemindList(map);
			start++;
		}
	}
	
	
	/** 
	* @Description: 分页处余额不足的短信提醒数据
	* @param  map    设定文件 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月29日 上午10:27:56
	*/
	private void processSmsCountRemindList(Map<String, Object> map) {
		long l = System.currentTimeMillis();
		List<MobileSetting> list  =  myBatisDao.findList(MobileSetting.class.getName(),"querySettingPageJoinUserAccount", map);
		logger.info("######################### 后台管理设置B:联表查询一批数据耗时: "+(System.currentTimeMillis()-l));
		if(list ==null || list.isEmpty()) return;
		//发送余额不足提醒后需要标记的数据
		List<Map<String, Object>> markList = new ArrayList<Map<String,Object>>();
		for (MobileSetting setting : list) {
			anylsisDataAndSendMessage(setting,markList);
		}
		//批量操作数据库
		if(markList.size()>0)
			updateMobileSettingFlag(markList);
	}
	
	/** 
	* @Description: 标记已发送余额提醒
	* @param  map    设定文件 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月29日 上午10:27:56
	*/
	@Transactional
	public void updateMobileSettingFlag(List<Map<String, Object>> markList){
		myBatisDao.execute(MobileSetting.class.getName(),"updateMobileSettingFlag", markList);
	}
	
	
	
	/** 
	* @Description: 分析用户的后台管理设置发送余额不足提醒短信
	* @param  setting    后台设置 
	* @param  markList   发送用户标记 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月29日 上午10:29:14
	*/
	private void anylsisDataAndSendMessage(MobileSetting setting,List<Map<String, Object>> markList){
		//已标记发送,当天
		if(setting.getFlag()!=null && setting.getFlag().equals(true)) return;
		//余额不足提醒设置未开启
		if(setting.getMessageRemainder()==null || setting.getMessageRemainder().equals(false)) return;
		//下面两种为脏数据
		if(setting.getStartTime() !=null && setting.getEndTime() ==null) return;
		if(setting.getStartTime() ==null && setting.getEndTime() !=null) return;
		String MESSAGE_CONTENT  =  ConstantUtils.MESSAGE_SMSCOUNT_CONTNET;
		MESSAGE_CONTENT = MESSAGE_CONTENT.replace("USERID", setting.getUserId()).replace("COUNT",setting.getMessageCount()+"");
		String result =null;
		Date startTime = null,endTime = null;
		Map<String,Object> map =null;
		String [] phones = new String[1];
		/**
		 * 公共条件:
		 * flag = false
		 * 实际短信余额 小于 后台管理设置提醒余额(sql已判断)
		 * 是否开启余额提醒(sql已判断)
		 * */
		/**
		 * //第一次发送条件:
		 * startTime&&endTimd ==null(当前时间,一个星期)
		 * 第一次发送后:flag =true, startTime=当前时间,endTime=下个星期的此刻
		 * */
		if(setting.getStartTime() ==null && setting.getEndTime() ==null){
			phones[0] = setting.getPhoneNum();
			result = SendMessageUtilForHangYe.sendMessage(phones, MESSAGE_CONTENT, null, null);
			//System.out.println("##################1:"+MESSAGE_CONTENT);
			//构造当前时间&&下一个月的此刻
		    startTime = new Date();
	        endTime = DateUtils.addDate(startTime, 7);
		}
		/**
		 * //第N次发送条件:
		 * startTime<<当前时间 <<endTimd
		 * 第N次发送后 flag = true,
		 * */
		else if(System.currentTimeMillis()>setting.getStartTime().getTime() 
				&& System.currentTimeMillis()<setting.getEndTime().getTime()){
			phones[0] = setting.getPhoneNum();
			result = SendMessageUtilForHangYe.sendMessage(phones, MESSAGE_CONTENT, null, null);
			//System.out.println("##################2:"+MESSAGE_CONTENT);
		}else{
			return;
		}
		//result不为空代表已发送过,不管成功与否??!!
		if(result != null){
			String status = JsonUtil.fromJson(result, ReturnMessage.class).getReturnCode();
			//发送成功则标记已发送
			if("100".equals(status)){
				//发送成功则标记已发送
				map = new HashMap<String,Object>();
				map.put("userId", setting.getUserId());
				map.put("startTime", startTime);
				map.put("endTime", endTime);
				//当天的余额不足提醒已发送成功
				map.put("flag", true);
				markList.add(map);
			}
		}
	}

	
//	/** 
//	* @Description: 定时12点:<br/>
//	* 1:定时将今天已发送的后管理管理标记清除<br/>
//	* 2:定时将过期的用户的余额不足提醒 起始时间,结束时间置空<br/>
//	* 3:调用上述两个method,分别执行<br/>
//	* @param     设定文件 
//	* @return void    返回类型 
//	* @author jackstraw_yu
//	* @date 2017年12月5日 下午3:19:10
//	*/
//	public void timerResetSmsmRemider(){
//
//	}
	
	
	/** 
	* @Description: 每天夜晚12点,定时将今天已发送的后管理管理标记清除
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月29日 上午10:57:48
	*/
	@Transactional
	public void scheduleResetFlag() {
		 myBatisDao.execute(MobileSetting.class.getName(),"scheduleResetFlag", null);
	}
	
	
	
	/** 
	* @Description: 每个一个小时,<br/>
	* 定时将过期的用户的余额不足提醒 起始时间,结束时间置空.<br/>
	* 联表更新!
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月29日 上午10:57:48
	*/
	@Transactional
	public void scheduleResetSmsmRemider() {
		 myBatisDao.execute(MobileSetting.class.getName(),"scheduleResetSmsmRemider", null);
	}
	
	

	/** 
	* @Description: 重置用户余额不足提醒<br/>
	* 为了能读取到事务提交后的用户的短信余额,<br/>
	* 请注意事务的控制<br/>
	* @param  userId   用户昵称 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月29日 上午10:29:14
	*/
	@Transactional
	public void  resetSmsCountRemindMark(String userId){
		MobileSetting setting = findMobileSetting(userId);
		if(setting != null && setting.getStartTime()!=null && setting.getEndTime()!=null){
			Long account = userAccountService.findUserAccountSms(userId);
			if(account!=null && account.longValue() >= 
					(setting.getMessageCount()==null?0:setting.getMessageCount().longValue()) ){
				 myBatisDao.findList(MobileSetting.class.getName(),"resetFlagByUserId", userId);
			}
		}
	}
	
	
	
	/** 
	* @Description: 重置用户余额提醒代理方法
	* @param @param userId    设定文件 
	* @author jackstraw_yu
	* @date 2017年12月4日 下午5:41:18
	*/
	public void  proxyResetSmsRemindMark(final String userId){
		try {
			MyFixedThreadPool.getMyFixedThreadPool().execute(
				new Runnable() {
					@Override
					public void run() {
						realResetSmsRemindMark(userId);
				}
			});
		} catch (Exception e) {
			logger.error("############################# 重置余额提醒异常! :"+userId);
		}
	}
	
	/** 
	* @Description:重置用户余额提醒,真实方法
	* @param @param userId    设定文件 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年12月4日 下午5:41:41
	*/
	public void  realResetSmsRemindMark(String userId){
		MobileSetting setting = findMobileSetting(userId);
		if(setting != null && setting.getStartTime()!=null && setting.getEndTime()!=null){
			Long account = userAccountService.findUserAccountSms(userId);
			if(account!=null && account.longValue() >= 
					(setting.getMessageCount()==null?0:setting.getMessageCount().longValue()) ){
				 myBatisDao.findList(MobileSetting.class.getName(),"resetFlagByUserId", userId);
			}
		}
	}
	
	
}
