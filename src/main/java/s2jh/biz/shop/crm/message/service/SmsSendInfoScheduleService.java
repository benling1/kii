package s2jh.biz.shop.crm.message.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.SecretException;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.util.SmsSchedulePay;
import s2jh.biz.shop.crm.message.service.util.SmsSheduleOrderSuccess;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;

@Service
public class SmsSendInfoScheduleService {
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(SmsSendInfoScheduleService.class);
	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	@Autowired
	private SmsRecordService  smsRecordService;
	public boolean doCreateRate(String sellerNick,String content,String rateType,Date startDate,Long tid,Long oid){
		if(startDate==null){
			return false;
		}
		if(startDate.getTime()<(System.currentTimeMillis()+1000)){
			startDate= new Date(System.currentTimeMillis()+1000);
		}
		SmsSendInfo smsSendInfo = new SmsSendInfo();
		smsSendInfo.setNickname("~Et8xePTHeXvgJUUGR2edvgAsd==~gUkS56uv~2~~");//假用户名
		smsSendInfo.setPhone("$LlJUyVaAffUSJb+UasdJHTbQ==$EYqiNasdd+39TXm3a3msQ==$1$$");
		smsSendInfo.setRateType(rateType);
		smsSendInfo.setContent(content);
		smsSendInfo.setUserId(sellerNick);
		smsSendInfo.setType(OrderSettingInfo.AUTO_RATE);
		smsSendInfo.setTid(tid);
		smsSendInfo.setOid(oid);
		smsSendInfo.setStartSend(startDate);
		Integer count = myBatisDao.findBy(SmsSendInfo.class.getName() + "Schedule", "doCreateByRate",smsSendInfo);
		if(count!=1){
			return false;
		}
		return true;
	}
	/**
	 * 自动创建定时发送短信任务
	 * @param smsSendInfo
	 * @return
	 */
	public boolean doAutoCreate(final SmsSendInfo smsSendInfo){
		if(smsSendInfo==null){
			return false;
		}
		try {
			String session = judgeUserUtil.getUserTokenByRedis(smsSendInfo.getUserId());
			if(!EncrptAndDecryptClient.isEncryptData(smsSendInfo.getNickname(), EncrptAndDecryptClient.SEARCH)){
				smsSendInfo.setNickname(EncrptAndDecryptClient.getInstance().encrypt(smsSendInfo.getNickname(), EncrptAndDecryptClient.SEARCH, session));
			}
			if(!EncrptAndDecryptClient.isEncryptData(smsSendInfo.getPhone(), EncrptAndDecryptClient.PHONE)){
				smsSendInfo.setPhone(EncrptAndDecryptClient.getInstance().encrypt(smsSendInfo.getPhone(), EncrptAndDecryptClient.PHONE, session));
			}
		} catch (SecretException e) {
			return false;
		}
		if(smsSendInfo.getStartSend()==null){
			logger.debug("************tid:"+smsSendInfo.getTid()+",类型："+(String)smsSendInfo.getType()+",开始时间为空，不允许创建无开始时间的短信   **********");
			return false;
		}
		if(smsSendInfo.getStartSend().getTime()<(System.currentTimeMillis()+60000)){
			smsSendInfo.setStartSend(new Date(System.currentTimeMillis()+60000));
		}
		if(smsSendInfo.getEndSend()!=null){
			if(smsSendInfo.getEndSend().getTime()<smsSendInfo.getStartSend().getTime()){
				logger.debug("************tid:"+smsSendInfo.getTid()+",类型："+(String)smsSendInfo.getType()+",结束时间比开始比时间小，错误   **********");
				return false;
			}
		}
		Integer count = myBatisDao.findBy(SmsSendInfo.class.getName() + "Schedule", "findMessageByAdd",smsSendInfo);
		if(count==0){
			logger.debug("*******************tid:"+smsSendInfo.getTid()+",类型："+(String)smsSendInfo.getType()+",创建定时短信  开始********************");
			int i = myBatisDao.execute(SmsSendInfo.class.getName() + "Schedule", "doCreateByScheduleSend",smsSendInfo);
			logger.debug("*******************tid:"+smsSendInfo.getTid()+",类型："+(String)smsSendInfo.getType()+",创建定时短信  插入："+i+"条********************");
		}else{
			logger.debug("*******************tid:"+smsSendInfo.getTid()+",类型："+(String)smsSendInfo.getType()+",创建定时短信  失败有重复数据"+count+"********************");
		}
		return true;
	}
	/**
	 * 当项目启动的时候，返回近一个小时未被发送的定时任务短信
	 * @return
	 */
	@Transactional
	public List<SmsSendInfo> findOneHourMessage(){
		return myBatisDao.findList(SmsSendInfo.class.getName() + "Schedule","findBySendMessageOneHour", null);
	}
	/**
	 * 付款完毕后 ，删除催付款短信
	 * @param sellerName 卖家昵称
	 * @param tid 主订单ID
	 */
	@Transactional
	public void delSmsScheduleByPay(String sellerName,Long tid){
//		if(sellerName!=null&&tid!=null){
//			Runnable work = new SmsSchedulePay(sellerName,tid,myBatisDao);
//			MyFixedThreadPool.getMySmsThreadPool().execute(work);
//		}
	}
	/**
	 * 付款后删除所有的定时短信
	* 创建人：邱洋
	* @Title: delSmsBySellerNick 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param sellerNick
	* @param @param buyerNick    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void delSmsBySellerNick(final String sellerNick,final String buyerNick) {
//		MyFixedThreadPool.getMySmsThreadPool().execute(new Runnable() {
//			@Override
//			public void run() {
//				if(ValidateUtil.isEmpty(sellerNick) || ValidateUtil.isEmpty(buyerNick)){
//					return ;
//				}
//				String buyerName = buyerNick;
//				try {
//					if(!EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
//						String session = judgeUserUtil.getUserTokenByRedis(sellerNick);
//						buyerName = EncrptAndDecryptClient.getInstance().encrypt(buyerNick, EncrptAndDecryptClient.SEARCH, session);
//					}
//					Map<String,Object> map = new HashMap<String,Object>();
//					map.put("sellerNick", sellerNick);
//					map.put("buyerNick", buyerName);
//					List<Long> delList = myBatisDao.findList(SmsSendInfo.class.getName() + "Schedule","findIdBySellerNick", map);
//					if(delList==null||delList.size()==0){
////						logger.debug("用户付款一小时，准备删除一小时内的短信，未找到要删除的短信");
//						return ;
//					}else{
////						long l = System.currentTimeMillis();
////						logger.debug(l+"用户付款一小时，准备删除一小时内的短信，查询出来的催付短信有："+delList.size()+"条，要删除的id是："+Arrays.toString(delList.toArray()));
//						map.put("delList", delList);
//						 myBatisDao.execute(SmsSendInfo.class.getName() + "Schedule", "doRemoveSms", map);
////						logger.debug(l+"成功删除了"+i+"条");
//					}
//				} catch (SecretException e) {
//				}
//			}
//		});
	}
	/**
	 * 订单结束后  删除所有定时短信
	 * @param sellerName 卖家昵称
	 * @param tid 主订单ID
	 */
	@Transactional
	public void delSmsScheduleByOrderSuccess(String sellerName,Long tid){
//		if(sellerName!=null&&tid!=null){
//			Runnable work = new SmsSheduleOrderSuccess(sellerName,tid,myBatisDao);
//			MyFixedThreadPool.getMySmsThreadPool().execute(work);
//		}
	}
	/**
	 * 定时短信发送成功后，自动删除对应的记录
	 * @param id 定时任务主键ID
	 */
	public void delSmsScheduleBySendSuccess(Long id){
		if(id!=null){
			this.myBatisDao.execute(SmsSendInfo.class.getName() + "Schedule", "doRemoveMessageBySendSuccess", id);
		}
	}
	
	/**
	  * 创建人：邱洋
	  * @Title: 删除schedule表中的定时发送任务
	  * @date 2017年5月3日--下午8:22:52 
	  * @return void
	  * @throws
	 */
	public void delSmsScheduleByIds(List<Long> ids){
		HashMap<String, Object>   map  = new HashMap<String, Object>();
		map.put("delList", ids);
		myBatisDao.execute(SmsSendInfo.class.getName() + "Schedule", "doRemoveSms", map);
	}
	public boolean findSentCitySms(String sellerNick,String buyerPhone,String modeType) throws SecretException{
		if(ValidateUtil.isEmpty(sellerNick) || ValidateUtil.isEmpty(buyerPhone) || ValidateUtil.isEmpty(modeType)){
			return true;
		}
		if(!EncrptAndDecryptClient.isEncryptData(buyerPhone, EncrptAndDecryptClient.PHONE)){
			String session = this.judgeUserUtil.getUserTokenByRedis(sellerNick);
			buyerPhone = EncrptAndDecryptClient.getInstance().encrypt(buyerPhone, EncrptAndDecryptClient.PHONE, session);
		}
		boolean flag = this.smsRecordService.findSmsSellerNickAndPhone(sellerNick, buyerPhone, modeType);
		if(flag){
			return true;
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("type", modeType);
		map.put("phone", buyerPhone);
		map.put("sellerNick", sellerNick);
		Integer i = this.myBatisDao.findBy(SmsSendInfo.class.getName() + "Schedule", "findSmsBySellerNickAndPhone", map);
		if(i==0){
			return false;
		}
		return true;
	}
	public Date findByTidAndType(Long tid,String type){
		if(ValidateUtil.isEmpty(type) || ValidateUtil.isEmpty(tid)){
			return null;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tid", tid);
		map.put("type", type);
		return this.myBatisDao.findBy(SmsSendInfo.class.getName() + "Schedule", "findByTidAndType", map);
	}
	public void doUpdateSms(SmsSendInfo smsSendInfo){
		if(smsSendInfo==null){
			return;
		}
		if(smsSendInfo.getId()==null){
			return ;
		}
		if(smsSendInfo.getStartSend().getTime()<(System.currentTimeMillis()+60*1000)){
			smsSendInfo.setStartSend(new Date(System.currentTimeMillis()+60*1000));
		}
		smsSendInfo.setEndSend(null);
		this.myBatisDao.execute(SmsSendInfo.class.getName()+"Schedule", "doUpdateEndTimeBySendError", smsSendInfo);
		this.logger.debug("短信延后成功  "+smsSendInfo.getUserId() + " 类型：" + smsSendInfo.getType() + "id:"+smsSendInfo.getId());
	}
}
