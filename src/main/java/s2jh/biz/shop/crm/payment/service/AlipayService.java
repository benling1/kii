package s2jh.biz.shop.crm.payment.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.payment.util.PayUtils;
import s2jh.biz.shop.crm.taobao.info.SendMessageStatusInfo;
import s2jh.biz.shop.crm.user.entity.UserRecharge;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.user.service.UserRechargeService;

@Service
public class AlipayService {
	private static final Log log = LogFactory.getLog(AlipayService.class);
	@Autowired
	private UserRechargeService userRechargeService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserAccountService userAccountService;
	
	
    /**
	 * 处理支付通知
	 * @param out_trade_no
	 * @param trade_status
	 * @param total_amount
	 */
	public synchronized String disposePayNotify(String trade_status,
			String out_trade_no, Double total_amount) {
    	log.info("*********************************处理异步支付通知--状态：("+trade_status+")，订单：("+out_trade_no+")，金额：("+total_amount+")");
    	UserRecharge ur= userRechargeService.findUserRechargeByOrderId(out_trade_no);
    	if(null != ur){
			if (trade_status.equals(PayUtils.TRADE_FINISHED)
					|| trade_status.equals(PayUtils.TRADE_SUCCESS)) {
    			if("3".equals(ur.getStatus()) && PayUtils.comparePrice(total_amount,ur.getRechargePrice())){
    				boolean result = this.addUserSms(ur);
					log.info("*******************************充值结果"+result);
					if(result){
						// 充值成功
						ur.setStatus("1");
						userRechargeService.updateUserRechargeStatus(ur);
						return "success";
					}else{
						// 充值失败
						ur.setStatus("2");
						userRechargeService.updateUserRechargeStatus(ur);
						return "";
					}
    			}else{
    				log.error("*********************************订单编号：("+out_trade_no+")不符合充值条件！！！");
    				return "";
    			}
    		}else if(trade_status.equals(PayUtils.TRADE_CLOSED) && "3".equals(ur.getStatus())){
    			ur.setStatus("2");
    			userRechargeService.updateUserRechargeStatus(ur);
    			log.info("*********************************自定义充值短信--订单号：("+out_trade_no+")支付关闭!");
    			return "";
    		}else{
    			log.info("*********************************自定义充值短信--订单号：("+out_trade_no+")未支付成功!");
    			return "";
    		}
    	}else{
    		log.info("*********************************自定义充值短信--订单号：("+out_trade_no+")不存在!");
    		return "";
    	}
	}


	/**
	 * @Description: 封装充值参数 
	 * @author HL
	 * @date 2017年11月17日 上午11:35:44
	 */
	private boolean addUserSms(UserRecharge ur) {
		return userAccountService.doUpdateUserSms(
				ur.getUserNick(), SendMessageStatusInfo.ADD_SMS,
				ur.getRechargeNUm(), "短信套餐购买", ur.getUserNick(),
				null, "自定义充值，短信数量：" + ur.getRechargeNUm(),
				UserAccountService.NO_TIME);
	}


	/**
     * 创建充值记录，
     */
    public void insertUserRechargeRecord(Double totalAmount, Integer rechargeNum,
			String userNick, String outTradeNo) throws Exception{
			Date date = new Date();
			UserRecharge ur = new UserRecharge();
			ur.setUserNick(userNick);
			ur.setRechargePrice(totalAmount);
			ur.setRechargeType("1");
			ur.setUnitPrice(PayUtils.univalence(rechargeNum));
			ur.setRechargeDate(date);
			ur.setStatus("3");
			ur.setRemarks("自定义充值");
			ur.setOrderId(outTradeNo);
			ur.setRechargeNUm(rechargeNum);
			ur.setCreatedDate(date);
			ur.setLastModifiedDate(date);
			userRechargeService.insertUserRechargeRecord(ur);
	}
}
