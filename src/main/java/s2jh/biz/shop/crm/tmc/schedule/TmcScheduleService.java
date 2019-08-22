package s2jh.biz.shop.crm.tmc.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.SecretException;
import com.taobao.api.domain.Trade;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.tmc.service.SendSmsService;
import s2jh.biz.shop.crm.tmc.service.TmcTradeSuccessService;
import s2jh.biz.shop.crm.vip.service.VipUserService;
import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.support.service.MultithreadBatchSmsService;

/** 
* @author wy
* @version 创建时间：2017年9月6日 下午4:14:55
*/
@Service
public class TmcScheduleService {
private Logger logger = org.slf4j.LoggerFactory.getLogger(TmcScheduleService.class);
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Autowired
	private VipUserService vipUserService;
	
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	
	@Autowired
	private MultithreadBatchSmsService multithreadBatchSmsService;

	@Autowired
	private MsgSendRecordService msgSendRecordService;

	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	@Autowired
	private SendSmsService sendSmsService;
	
	@Autowired
	private TransactionOrderService transactionOrderService;
	
	@Autowired
	private TradeInfoService tradeInfoServsice;
	
	@Autowired
	private TmcTradeSuccessService tmcTradeSuccessService;
	
	public void doHandle(Date startDate,Date endDate){
	    this.logger.info("扫描开始时间："+startDate+"，结束时间："+ endDate); 
		// 生成现在的时间 比如 2017-01-01 12:01
		Map<String, Date> map = new HashMap<String,Date>(5);
		map.put("startTime", startDate);
		map.put("endTime", endDate);
		List<SmsSendInfo> smsInfoList = this.myBatisDao.findList(SmsSendInfo.class.getName() + "Schedule", "findBySendMessage",map);
		if(ValidateUtil.isEmpty(smsInfoList)){
			return ;
		}
		this.logger.info("本次扫描出定时短信：size "+ smsInfoList.size()); 
		for (SmsSendInfo smsSendInfo : smsInfoList) {
		    this.logger.debug("短信从数据库中取出：" + smsSendInfo.getTid() + " 类型："+smsSendInfo.getType() + " id:"+smsSendInfo.getId());
			try { //33,34,35,36,99
				String type = smsSendInfo.getType();
				if("33".equals(type) ||"34".equals(type) ||"35".equals(type) ||"36".equals(type) ||"99".equals(type) ){
					//营销短信
					this.doMarketingSms(smsSendInfo);
				}else{ //订单中心类单条短信
				    this.logger.debug("发送短信准备放入队列tid：" + smsSendInfo.getTid() + " 类型："+smsSendInfo.getType() + " id:"+smsSendInfo.getId());
					sendTradeCenterSms(smsSendInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 定时服务器启动时，扫描一个小时之内的未发送短信
	 * @author: wy
	 * @time: 2017年9月21日 上午11:51:39
	 */
	public void initSendHourSms(){
		List<SmsSendInfo> smsInfoList = myBatisDao.findList(SmsSendInfo.class.getName() + "Schedule","findBySendMessageOneHour", null);
		if(ValidateUtil.isEmpty(smsInfoList)){
			return ;
		}
		for (SmsSendInfo smsSendInfo : smsInfoList) {
			sendTradeCenterSms(smsSendInfo);
		}
	}
	/**
	 * 多线程处理订单中心的定时短信
	 * @author: wy
	 * @time: 2017年9月21日 上午11:49:03
	 * @param smsSendInfo
	 */
	private void sendTradeCenterSms(final  SmsSendInfo smsSendInfo) {
		MyFixedThreadPool.getSmsScheduleThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				try {
					doTradeCenterSms(smsSendInfo);
				} catch (Exception e) {
				    logger.info("定时消息处理错误tid：" + smsSendInfo.getTid() + " type:" + smsSendInfo.getType() + " 错误："+e.getMessage());;
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 订单中心类定时短信处理
	 * @author: wy
	 * @time: 2017年9月6日 下午4:39:59
	 * @param smsSendInfo
	 * @throws SecretException
	 */
	private void doTradeCenterSms(SmsSendInfo smsSendInfo){
		if(smsSendInfo==null){
			return ;
		}
		this.logger.debug("定时短信准备开始校验tid：" + smsSendInfo.getTid() + " 类型："+smsSendInfo.getType());
		if(smsSendInfo.getEndSend()!=null){
			if(smsSendInfo.getEndSend().getTime()<System.currentTimeMillis()){
				this.delayTomorrow(smsSendInfo);
				return ;
			}
		}
		if(OrderSettingInfo.AUTO_RATE.equals(smsSendInfo.getType())){
			//自动评价不发送短信，走单独的处理
			logger.debug("定时自动评价  "+smsSendInfo.getUserId() + " 类型：" +smsSendInfo.getType());
			this.tmcTradeSuccessService.autoSellerRateBySchedule(smsSendInfo);
			this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
			return ;
		}
		else if(OrderSettingInfo.PAYMENT_SMS_TYPE.contains(smsSendInfo.getType())){
			//催付短信
			if(this.isTradePay(smsSendInfo.getTid(), smsSendInfo.getUserId())){
				this.logger.debug("定时短信催付订单已经付过款，不再发送催付短信 ,tid: "+smsSendInfo.getTid());
				//已经付过款，不发送短信
				this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
				return ;
			}
		}
		else if(OrderSettingInfo.RETURNED_PAYEMNT.equals(smsSendInfo.getType())){
			//回款提醒
			if(this.isTradeFinished(smsSendInfo.getTid(), smsSendInfo.getUserId())){
				this.logger.debug("回款提醒订单已结束，不需要发送短信 "+smsSendInfo.getTid());
				this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
				return ;
			}
		}
		else if(OrderSettingInfo.DELAY_SEND_REMIND.equals(smsSendInfo.getType())){
			//延迟发货
			if(this.isTradeSellerSendGoods(smsSendInfo.getTid(), smsSendInfo.getUserId())){
				this.logger.debug("卖家已发货，不需要发送短信 "+smsSendInfo.getTid());
				this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
				return ;
			}
		}
		else if(OrderSettingInfo.GOOD_VALUTION_REMIND.equals(smsSendInfo.getType())){
			//好评提醒
            if(this.isBuyerRated(smsSendInfo.getTid(), smsSendInfo.getUserId())){
                this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
                this.logger.debug("好评提醒，买家已评价，不发送短信，tid: "+smsSendInfo.getTid()+" type:"+smsSendInfo.getType());
                return;
            }
        }
		String session = this.judgeUserUtil.getUserTokenByRedis(smsSendInfo.getUserId());
		try {
            smsSendInfo.setNickname(this.judgeUserUtil.getDecryptData(smsSendInfo.getNickname(), EncrptAndDecryptClient.SEARCH, null, session));
            smsSendInfo.setPhone(this.getPhoneToString(smsSendInfo.getPhone(), session));
            this.logger.debug("定时短信校验结束tid：" + smsSendInfo.getTid() + " 类型："+smsSendInfo.getType());
            this.sendSingleSms(smsSendInfo);
		} catch (SecretException e) {
            e.printStackTrace();
            this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
        }
	}
	/**
     * 买家是否评价过
     * @author: wy
     * @time: 2017年9月7日 下午2:16:58
     * @param tid
     * @return true->买家已评价, false->买家未评价
     */
    private boolean isBuyerRated(Long tid,String sellerNick){
        if(ValidateUtil.isEmpty(tid) ||ValidateUtil.isEmpty(sellerNick) ){
            return true;
        }
        Trade trade = this.transactionOrderService.queryTrade(String.valueOf(tid));
        if(trade == null){
            TradeDTO tradeDTO = this.tradeInfoServsice.findOneByTidTMC(String.valueOf(tid), sellerNick);
            if(tradeDTO!=null){
                if(tradeDTO.getBuyerRate()!=null){
                    return tradeDTO.getBuyerRate();
                }
            }
            return false;
        }
        if(trade.getBuyerRate()==null){
            return false;
        }
        return trade.getBuyerRate();
    }
	/**
	 * 卖家是否已发货
	 * @author: wy
	 * @time: 2017年9月13日 下午4:19:13
	 * @param tid 主订单ID
	 * @param sellerNick 卖家昵称
	 * @return true:卖家已发货  false:卖家未发货
	 */
	private boolean isTradeSellerSendGoods(long tid,String sellerNick){
		String status = this.getTradeStatus(tid, sellerNick);
		if(ValidateUtil.isEmpty(status)){
			return false;
		}
		if(TradesInfo.WAIT_SELLER_SEND_GOODS.equalsIgnoreCase(status)){
			return false;
		}
		return true;
	}
	/**
	 * 订单是否已经结束
	 * @author: wy
	 * @time: 2017年9月8日 下午2:36:59
	 * @param tid 订单号
	 * @param sellerNick 卖家昵称
	 * @return true->已经结束,false->未结束
	 */
	private boolean isTradeFinished(long tid,String sellerNick){
		String status = this.getTradeStatus(tid, sellerNick);
		if(ValidateUtil.isEmpty(status)){
			return false;
		}
		if(TradesInfo.TRADE_FINISHED.equalsIgnoreCase(status)){
			return true;
		}
		return false;
	}
	/**
	 * 订单是否已经付过款
	 * @author: wy
	 * @time: 2017年9月8日 下午2:24:02
	 * @param tid 订单号
	 * @param sellerNick 卖家昵称
	 * @return true->已经付款，->未付款
	 */
	private boolean isTradePay(long tid,String sellerNick){
		String status = this.getTradeStatus(tid, sellerNick);
		if(ValidateUtil.isEmpty(status)){
			return false;
		}
		if (status.equals(TradesInfo.WAIT_BUYER_PAY)
				|| status.equals(TradesInfo.PAY_PENDING)
				|| status.equals(TradesInfo.TRADE_NO_CREATE_PAY)) {
			//等待付款的状态,未支付
			return false;
		}
		//校验是否为预售订单，如果为预售订单，则不催付
		Trade trade = this.transactionOrderService.queryTrade(String.valueOf(tid));
		if(trade==null){
		    this.logger.debug("订单查询为空，不催付, tid： "+tid);
		    return false;
		}
		String stepTradeStatus = trade.getStepTradeStatus();
        if(ValidateUtil.isNotNull(stepTradeStatus)){
            if(!TradesInfo.FRONT_NOPAID_FINAL_NOPAID.equals(stepTradeStatus)){
                this.logger.debug("预售订单，不催付, tid： "+tid);
                return false;
            }
        }
		return true;
	}
	/**
	 * 获取订单的状态，查询sys_info,mongo,api
	 * @author: wy
	 * @time: 2017年9月8日 下午2:31:06
	 * @param tid 订单号
	 * @param sellerNick 卖家昵称
	 * @return 订单的状态，订单查询不到时，返回null
	 */
	private String getTradeStatus(long tid,String sellerNick){
	    String status = transactionOrderService.queryTradeStatus(String.valueOf(tid));
		if (ValidateUtil.isEmpty(status)) {
			TradeDTO tradeDTO = this.tradeInfoServsice.findOneByTidTMC(String.valueOf(tid), sellerNick);
			if(tradeDTO!=null){
				status = tradeDTO.getStatus();
			}else{
				String sessionKey = judgeUserUtil.getUserTokenByRedis(sellerNick);
				Trade trade = TaoBaoClientUtil.getTradeByTaoBaoAPI(tid,sessionKey);
				if(trade!=null){
				    status = trade.getStatus();
				}
			}
		}
		if(status == null){
			this.logger.debug("定时短信催付订单查询为空 ,tid: "+tid);
			return null;
		}
		return status;
	}
	/**
	 * 时间不对的短信，开始时间加一天。次日发送 //TODO 暂定
	 * @author: wy
	 * @time: 2017年9月6日 下午5:42:02
	 * @param smsSendInfo
	 */
	private void delayTomorrow(SmsSendInfo smsSendInfo){
		if(smsSendInfo.getDelayDate()==null){
		    this.logger.debug("结束时间小于开始时间，短信无法发送 tid: "+smsSendInfo.getTid()+" 开始时间："+smsSendInfo.getStartSend()+"，结束时间"+smsSendInfo.getEndSend());
		    this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
			return;
		}
		if(smsSendInfo.getDelayDate()){
			Calendar cal = Calendar.getInstance();
			cal.setTime(smsSendInfo.getStartSend());
			cal.add(Calendar.DATE, 1);
			smsSendInfo.setStartSend(cal.getTime());
			smsSendInfo.setEndSend(null);
			this.smsSendInfoScheduleService.doUpdateSms(smsSendInfo);
		}else{
		    this.logger.debug("结束时间小于开始时间，短信无法发送 tid: "+smsSendInfo.getTid()+" 开始时间："+smsSendInfo.getStartSend()+"，结束时间"+smsSendInfo.getEndSend());
		    this.smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
		}
	}
	/**
	 * 发送单个手机号码短信
	 * @author: wy
	 * @time: 2017年9月6日 下午4:39:37
	 * @param smsSendInfo
	 */
	private void sendSingleSms(final SmsSendInfo smsSendInfo){
		sendSmsService.sendSingleSms(smsSendInfo);
	}
	/**
	 * 发送营销类短信
	 * @author: wy
	 * @time: 2017年9月6日 下午4:23:10
	 * @param smsSendInfo
	 * @throws SecretException
	 */
	private void doMarketingSms(final SmsSendInfo smsSendInfo) throws SecretException{
		String session = this.judgeUserUtil.getUserTokenByRedis(smsSendInfo.getUserId());
		final String[] phones =  this.getPhoneToArray(smsSendInfo.getPhone(), session);
		if(phones==null || phones.length==0){
			this.logger.equals("营销短信发送的手机号码不规范，拆分后为空，无法发送！"+smsSendInfo.getId());
			return;
		}
		MyFixedThreadPool.getSmsScheduleThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				BatchSmsData obj = new BatchSmsData(phones);
				obj.setContent(smsSendInfo.getContent());
				obj.setUserId(smsSendInfo.getUserId());
				boolean isVip = vipUserService.findVipUserIfExist(smsSendInfo.getUserId());
				obj.setVip(isVip);
				obj.setType(smsSendInfo.getType());
				obj.setMsgId(smsSendInfo.getMsgId());
				multithreadBatchSmsService.batchOperateSms(obj);
				/**
				 * 在发送任务全部执行之后，统计成功和失败的短信数量并记录到短信发送总记录表crm_msgrecord 2017.05.09
				 */
				int successCustom = obj.getSuccess();
//				int total = obj.getTotal();
				// int failCustom = total - successCustom;
				int failCustom = obj.getFail();

				MsgSendRecord msgSendRecord = null;
				if (obj.getMsgId() != null) {
					// if(successCustom != 0 && failCustom >= 0){
					if (successCustom >= 0 && failCustom >= 0) {
//					if (failCustom >= 0) {
						// 更新总记录表crm_msgrecord添加成功总条数和失败总条数
						msgSendRecord = new MsgSendRecord();
						msgSendRecord.setFailedCount(failCustom);
						msgSendRecord.setSucceedCount(successCustom);
//						msgSendRecord.setStatus("1");
						msgSendRecord.setIsSent(true);
						msgSendRecord.setId(smsSendInfo.getMsgId());
					}
					if (msgSendRecord != null) {
						// 更新数据
						msgSendRecordService.updateMsgRecordByMsgId(msgSendRecord);
					}
				}
				smsSendInfoScheduleService.delSmsScheduleBySendSuccess(smsSendInfo.getId());
				logger.debug("营销短信发送成功 "+smsSendInfo.getUserId() + " 类型：" +smsSendInfo.getType());
			}
		});
	}
	
	
	
	/**
	 * 将短信中的手机号码解密，返回字符串
	 * @author: wy
	 * @time: 2017年8月2日 下午1:47:47
	 * @param phones 手机号码（可以为1个也可以为多个用英文逗号连接）
	 * @param session 卖家的sessionKey
	 * @return 解密后的数据，返回字符串
	 * @throws SecretException sessionKey过期
	 */
	private String getPhoneToString(String phones,String session) throws SecretException{
		if(ValidateUtil.isEmpty(phones)){
			return phones;
		}
		if(phones.length()<150){
			if(EncrptAndDecryptClient.isEncryptData(phones, EncrptAndDecryptClient.PHONE)){
				phones = EncrptAndDecryptClient.getInstance().decrypt(phones, EncrptAndDecryptClient.PHONE, session);
			}
			return phones;
		}else{
			this.logger.equals("单条短信解密错误，手机号码长度有误 手机号："+ phones);
			return null;
		}
//		StringBuffer result = new StringBuffer();
//		String str[] = phones.split(",");
//		int i = 0;
//		List<String> oldList = new ArrayList<String>(str.length);
//		Collections.addAll(oldList,str);
//		Map<String,String> phonesMap = EncrptAndDecryptClient.getInstance().decrypt(oldList, EncrptAndDecryptClient.PHONE, session);
//		if(phonesMap == null){
//			return phones;
//		}
//		for (Entry<String,String> entry : phonesMap.entrySet()) {
//			if(i==0){
//				result.append(entry.getValue());
//				i++;
//			}else{
//				result.append(",").append(entry.getValue());
//			}
//		}
//		return result.toString();
	}
	/**
	 * 将短信中的手机号码解密，返回字符串数组
	 * @author: wy
	 * @time: 2017年8月2日 下午1:49:22
	 * @param phones 由手机号码组成的字符串（可以为1个也可以为多个用英文逗号连接）
	 * @param session 用户的sessionKey
	 * @return 解密后的数据，返回字符串数组
	 * @throws SecretException sessionKey过期
	 */
	private String[] getPhoneToArray(String phones,String session) throws SecretException{
		if(ValidateUtil.isEmpty(phones)){
			return null;
		}
		if(phones.length()<70){
			if(EncrptAndDecryptClient.isEncryptData(phones, EncrptAndDecryptClient.PHONE)){
				if(session==null){
					throw new RuntimeException("定时任务发送信息---->数据为加密数据，但是用户的session为空");
				}
				phones = EncrptAndDecryptClient.getInstance().decrypt(phones, EncrptAndDecryptClient.PHONE, session);
			}
			return phones.split(",");
		}
		String[] str =  phones.split(",");
		List<String> oldList = new ArrayList<String>(str.length);
		Collections.addAll(oldList,str);
		Map<String,String> phonesMap = EncrptAndDecryptClient.getInstance().decrypt(oldList, EncrptAndDecryptClient.PHONE, session);
		if(phonesMap==null){
			return str;
		}
		return phonesMap.values().toArray(new String[0]);
	}
	/**
	 * 将短信中的手机号码解密，返回字符串数组
	 * @author: wy
	 * @time: 2017年8月2日 下午1:50:49
	 * @param phones 手机号码List集合
	 * @param session  用户的sessionKey
	 * @return 解密后的数据，返回字符串数组
	 * @throws SecretException sessionKey过期
	 */
	@SuppressWarnings("unused")
	private String[] getPhoneToArray(List<String> phones,String session) throws SecretException{
		if(ValidateUtil.isEmpty(phones)){
			return null;
		}
		if(phones.size()==1){
			if(EncrptAndDecryptClient.isEncryptData(phones.get(0), EncrptAndDecryptClient.PHONE)){
				if(session==null){
					throw new RuntimeException("定时任务发送信息---->数据为加密数据，但是用户的session为空");
				}
				phones.set(0, EncrptAndDecryptClient.getInstance().decrypt(phones.get(0), EncrptAndDecryptClient.PHONE, session));
			}
			return phones.toArray(new String[0]);
		}
		Map<String,String> phonesMap =EncrptAndDecryptClient.getInstance().decrypt(phones, EncrptAndDecryptClient.PHONE, session);
		if(phonesMap==null){
			return phones.toArray(new String[0]);
		}
		return phonesMap.values().toArray(new String[0]);
	}
}
