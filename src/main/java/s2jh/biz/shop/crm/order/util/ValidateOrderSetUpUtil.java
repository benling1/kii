package s2jh.biz.shop.crm.order.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import s2jh.biz.shop.crm.order.entity.OrderSetup;

public class ValidateOrderSetUpUtil {
	private ValidateOrderSetUpUtil(){};
	/**
	 * 判断订单基本设置的开始时间和结束时间是否正确，开始时间不能大于等于结束时间
	 * @param orderSetup 订单发送基本设置
	 * @return
	 */					  
	public static boolean validateOrderSetupTime(OrderSetup orderSetup){
		boolean flag = false;
		//判断开始时间结束时间是否正确 
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String startTimeString = orderSetup.getStartTime();
		String endTimeString = orderSetup.getEndTime();
		if(startTimeString !=null && endTimeString !=null){
			startTimeString = startTimeString.replaceAll("\\u00A0","");
			endTimeString = endTimeString.replaceAll("\\u00A0","");
			Date startTime=null,endTime=null;
			Long startTimeLong = null,endTimeLong = null;
			try {
				startTime = format.parse(startTimeString);
			} catch (Exception e) {
				if("".equals(startTimeString)){
					startTime = null;
				}else{
					e.printStackTrace();
					return false;
				}
//				try {
//					startTime = format.parse("00:00");
//				} catch (ParseException e1) {
//					e1.printStackTrace();
//				}
			}
			try {
				endTime = format.parse(endTimeString);
			} catch (Exception e) {
				if("".equals(endTimeString)){
					endTime = null;
				}else{
					e.printStackTrace();
					return false;
				}
//				try {
//					endTime = format.parse("24:00");
//				} catch (ParseException e1) {
//					e1.printStackTrace();
//				}
			}
			startTimeLong = startTime==null?0L:startTime.getTime();
			endTimeLong = endTime==null?0L:endTime.getTime();
			if(endTimeLong>startTimeLong){
				flag = true;
			}else if(startTimeLong==0L && startTimeLong==endTimeLong){ //两个都是0  证明时间未设置  或者说时间设置的不正确默认全天可发送
				flag = true;
			}
		}else{
			flag =true;
		}
		return flag;
	}
	/**
	 * 判断订单基本设置的最大金额不能小于最小金额 
	 * @param orderSetup 基本设置对象
	 * @return
	 */
	public static boolean validateOrderSetupPayment(OrderSetup orderSetup){
		boolean flag = false;
		//判断金额是否符合要求
		BigDecimal minPayment = orderSetup.getPayAmtOne();
		BigDecimal maxPayment = orderSetup.getPayAmtTwo();
		if(minPayment!=null && maxPayment!=null){
			if(maxPayment.compareTo(minPayment)==1){
				flag = true;
			}else if(maxPayment.compareTo(minPayment)==0){
				flag = true;
			}
		}else{
			flag =true;
		}
		return flag;
	}
}
