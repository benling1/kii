package s2jh.biz.shop.crm.tradecenter.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;

@Service
//@Transactional
public class BaseProcessSetupService {
	private final Map<String,String> dictMap = new HashMap<String,String>(){   
		private static final long serialVersionUID = 4467723566485282986L;
		{  
          /* put("1", "下单关怀");put("2", "常规催付");put("3", "二次催付");put("4", "聚划算催付");
           put("5", "预售催付");put("6", "发货提醒");put("7", "到达同城提醒");put("8", "派件提醒");  
           put("9", "签收提醒");put("10", "疑难件提醒");put("11", "延时发货提醒");
           put("12", "宝贝关怀");put("13", "付款关怀");put("14", "回款提醒");
           put("16", "自动评价");put("20", "中差评监控");put("21", "中差评安抚");
           put("29", "买家申请退款"); put("30", "退款成功");put("31", "等待退货"); put("32", "拒绝退款");
           put("37", "好评提醒");*/
		   /*  put(OrderSettingInfo.ADVANCE_PUSH_PAYMENT, "预售催付");*//*put(OrderSettingInfo.ABNORMAL_GOODS_REMIND, "疑难件提醒");  新版没有该功能*/
		   put(OrderSettingInfo.CREATE_ORDER, "下单关怀");put(OrderSettingInfo.FIRST_PUSH_PAYMENT, "常规催付");
		   put(OrderSettingInfo.SECOND_PUSH_PAYMENT, "二次催付");put(OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT, "聚划算催付");
		   put(OrderSettingInfo.SHIPMENT_TO_REMIND, "发货提醒");put(OrderSettingInfo.ARRIVAL_LOCAL_REMIND, "到达同城提醒");
		   put(OrderSettingInfo.SEND_GOODS_REMIND, "派件提醒");put( OrderSettingInfo.REMIND_SIGNFOR, "签收提醒");
           put(OrderSettingInfo.DELAY_SEND_REMIND, "延时发货提醒");put(OrderSettingInfo.COWRY_CARE, "宝贝关怀");
           put(OrderSettingInfo.PAYMENT_CINCERN, "付款关怀");put(OrderSettingInfo.RETURNED_PAYEMNT, "回款提醒"); 
           put(OrderSettingInfo.AUTO_RATE, "自动评价");put(OrderSettingInfo.APPRAISE_MONITORING_ORDER, "中差评监控");
           put(OrderSettingInfo.APPRAISE_PACIFY_ORDER, "中差评安抚");put(OrderSettingInfo.REFUND_CREATED, "买家申请退款");
           put(OrderSettingInfo.REFUND_SUCCESS, "退款成功");put(OrderSettingInfo.REFUND_AGREE, "同意退款");
           put(OrderSettingInfo.REFUND_REFUSE, "拒绝退款");put(OrderSettingInfo.GOOD_VALUTION_REMIND, "提醒好评");
        }  
    };  
   
    @Autowired
	private TradeSetupService tradeSetupService;
    @Autowired
	private OrderSetupService orderSetupService;
    
    
    
    /**
     * OrderSettingInfo.CREATE_ORDER, 下单关怀 //多任务
     * */
    public TradeSetup handleCreateOrder(OrderSetup source,TradeSetup target){
		target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
    	//0--开启 1--关闭
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		target.setTaskName(returnTaskName(source.getSettingType()));
		target.setTaskLevel(1);
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//下单提醒是默认
		//老版执行通知时间:超出时间不发送
		target.setTimeOutInform(false);
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
		return target;
	}
  
 	
    /**
     * OrderSettingInfo.FIRST_PUSH_PAYMENT, 常规催付 //多任务
     * */
    public TradeSetup handleFirstPushPayment(OrderSetup source,TradeSetup target){
    	target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		target.setTaskName(returnTaskName(source.getSettingType()));
		target.setTaskLevel(1);
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//过滤条件 1-同一买家一天只催付一次 2-同一买家已付款1小时不催 3-屏蔽黑名单用户4-预售订单不催付  5-超出时间不催付 6-超出时间次日催付
		if(source.getFiltingConditions()==null || "".equals(source.getFiltingConditions())){
			target.setTimeOutInform(true);
		}else if(source.getFiltingConditions().contains("5")){
			target.setTimeOutInform(false);
		}else if(source.getFiltingConditions().contains("6")){
			target.setTimeOutInform(true);
		}else{
			target.setTimeOutInform(true);
		}
		//TODO
		//老版默认设置
		//同一买家，1天只催1次
		//同一买家有付过款的不催（1小时前后）
		//屏蔽黑名单用户
		target.setFilterBlack(true);
		target.setFilterHassent(true);
		target.setFilterOnce(true);
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//当买家签收或者下单,过后多少时间发送信息        
		//提醒时间:分钟/小时/天
		/*if(source.getReminderTime()!=null && !"".equals(source.getReminderTime().trim())){
			if(source.getReminderTime().contains("分钟")){
				target.setTimeType(1);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("分钟", "")));
			}else if(source.getReminderTime().contains("小时")){
				target.setTimeType(2);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("小时", "")));
			}else if(source.getReminderTime().contains("天")){
				target.setTimeType(3);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("天", "")));
			}
		}*/
		//常规催付:15分钟
		target.setTimeType(1);
		target.setRemindTime(15);
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
		return target;
	}
    
    /**
     * OrderSettingInfo.SECOND_PUSH_PAYMENT, "二次催付//单任务
     * */
   	public TradeSetup handleSecondPushPayment(OrderSetup source,TradeSetup target){
   		target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//过滤条件 1-同一买家一天只催付一次 2-同一买家已付款1小时不催 3-屏蔽黑名单用户4-预售订单不催付  5-超出时间不催付 6-超出时间次日催付
		if(source.getFiltingConditions().contains("5")){
			target.setTimeOutInform(false);
		}else if(source.getFiltingConditions().contains("6")){
			target.setTimeOutInform(true);
		}else{
			target.setTimeOutInform(true);
		}
		//TODO
		//老版默认设置
		//同一买家，1天只催1次
		//同一买家有付过款的不催（1小时前后）
		//屏蔽黑名单用户
		target.setFilterBlack(true);
		target.setFilterHassent(true);
		target.setFilterOnce(true);
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//提醒时间:分钟/小时/天
		/*if(source.getReminderTime()!=null && !"".equals(source.getReminderTime().trim())){
			if(source.getReminderTime().contains("分钟")){
				target.setTimeType(1);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("分钟", "")));
			}else if(source.getReminderTime().contains("小时")){
				target.setTimeType(2);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("小时", "")));
			}else if(source.getReminderTime().contains("天")){
				target.setTimeType(3);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("天", "")));
			}
		}*/
		//二次催付:30分钟
		target.setTimeType(1);
		target.setRemindTime(30);
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
   		return target;
   	}
    
  
   	/**
     * OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT, "聚划算催付//单任务
     * */
  	public TradeSetup handlePreferentialPushPayment(OrderSetup source,TradeSetup target){
  		target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//过滤条件 1-同一买家一天只催付一次 2-同一买家已付款1小时不催 3-屏蔽黑名单用户4-预售订单不催付  5-超出时间不催付 6-超出时间次日催付
		if(source.getFiltingConditions().contains("5")){
			target.setTimeOutInform(false);
		}else if(source.getFiltingConditions().contains("6")){
			target.setTimeOutInform(true);
		}else{
			target.setTimeOutInform(true);
		}
		//TODO
		//老版默认设置
		//同一买家，1天只催1次
		//同一买家有付过款的不催（1小时前后）
		//屏蔽黑名单用户
		target.setFilterBlack(true);
		target.setFilterHassent(true);
		target.setFilterOnce(true);
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//提醒时间:分钟/小时/天
		/*if(source.getReminderTime()!=null && !"".equals(source.getReminderTime().trim())){
			if(source.getReminderTime().contains("分钟")){
				target.setTimeType(1);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("分钟", "")));
			}else if(source.getReminderTime().contains("小时")){
				target.setTimeType(2);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("小时", "")));
			}else if(source.getReminderTime().contains("天")){
				target.setTimeType(3);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("天", "")));
			}
		}*/
		//聚划算催付5分钟
		target.setTimeType(1);
		target.setRemindTime(5);
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
  		return target;
  	}
    
    
  	 
  	/**
     * OrderSettingInfo.PAYMENT_CINCERN, 付款关怀 //多任务
     * */
    public TradeSetup handlePaymentCincern(OrderSetup source,TradeSetup target){
    	target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		target.setTaskName(returnTaskName(source.getSettingType()));
		target.setTaskLevel(1);
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//下单提醒是默认
		//老版执行通知时间:超出时间不发送
		target.setTimeOutInform(false);
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
		return target;
	}
    
    
 	
    /**
     * OrderSettingInfo.SHIPMENT_TO_REMIND, 发货提醒  //多任务
     * */
    public TradeSetup handleShipmentToRemind(OrderSetup source,TradeSetup target){
    	target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		target.setTaskName(returnTaskName(source.getSettingType()));
		target.setTaskLevel(1);
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//老版执行通知时间:超出时间次日发送
		target.setTimeOutInform(true);
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
		return target;
	}
    
    
   
    //TODO 不兼容
    /**
     *  OrderSettingInfo.DELAY_SEND_REMIND, 延时发货提醒 //多任务
     * */
    public TradeSetup handleDelaySendRemind(OrderSetup source,TradeSetup target){
    	target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		/*if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}*/
		target.setStatus(false);
		target.setTaskName(returnTaskName(source.getSettingType()));
		target.setTaskLevel(1);
		
		//TODO
		/*if(source.getExecuteGenre()==null || "".equals(source.getExecuteGenre()) 
			|| "0".equals(source.getExecuteGenre()) ){
			//任务执行时间==默认持续执行
			target.setExecuteType(true);
		}else{
			target.setExecuteType(false);
		}*/
    	return target;
	}
    
    
    
    /**
     * OrderSettingInfo.ARRIVAL_LOCAL_REMIND, 到达同城提醒 //多任务
     * */
    public TradeSetup handleArrivalLocalRemind(OrderSetup source,TradeSetup target){
    	target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		target.setTaskName(returnTaskName(source.getSettingType()));
		target.setTaskLevel(1);
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//TODO
		//通知时间-写死
		//默认每天9:00--21:00期间，到达同城立即发送短信，超出时间次日发送。
		target.setMaxInformTime("21:00:00");
		target.setMinInformTime("09:00:00");
		target.setTimeOutInform(true);
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
    	return target;
    }
    
    
    /**
     * OrderSettingInfo.SEND_GOODS_REMIND, 派件提醒   //多任务
     * */
    public TradeSetup handleSendGoodsRemind(OrderSetup source,TradeSetup target){
    	target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		target.setTaskName(returnTaskName(source.getSettingType()));
		target.setTaskLevel(1);
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//TODO
		//通知时间-写死
		//默认每天9:00--21:00期间，到达同城立即发送短信，超出时间次日发送。
		target.setMaxInformTime("21:00:00");
		target.setMinInformTime("09:00:00");
		target.setTimeOutInform(true);
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
    	return target;
    }

    
  
    /**
     *  OrderSettingInfo.REMIND_SIGNFOR, 签收提醒 //多任务
     * */
    public TradeSetup handleRemindSignfor(OrderSetup source,TradeSetup target){
    	target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		target.setTaskName(returnTaskName(source.getSettingType()));
		target.setTaskLevel(1);
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//TODO
		//通知时间-写死
		//默认每天9:00--21:00期间，到达同城立即发送短信，超出时间次日发送。
		target.setMaxInformTime("21:00:00");
		target.setMinInformTime("09:00:00");
		target.setTimeOutInform(true);
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
    	return target;
	}
    
    
    
    /**
     * OrderSettingInfo.COWRY_CARE, 宝贝关怀 //多任务
     * */
    public TradeSetup handleCowryCare(OrderSetup source,TradeSetup target){
		target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		target.setTaskName(returnTaskName(source.getSettingType()));
		target.setTaskLevel(1);
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//超出设置时间段，自动顺延至次日发送
		target.setTimeOutInform(true);
		//提醒时间:分钟/小时/天
		/*if(source.getReminderTime()!=null && !"".equals(source.getReminderTime().trim())){
			if(source.getReminderTime().contains("分钟")){
				target.setTimeType(1);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("分钟", "")));
			}else if(source.getReminderTime().contains("小时")){
				target.setTimeType(2);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("小时", "")));
			}else if(source.getReminderTime().contains("天")){
				target.setTimeType(3);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("天", "")));
			}
		}*/
		//宝贝关怀1天
		target.setTimeType(3);
		target.setRemindTime(1);
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
		return target;
	}
    
   
   
   
    /**
     *  OrderSettingInfo.RETURNED_PAYEMNT, 回款提醒  //多任务
     * */
    public TradeSetup handleReturnedPayemnt(OrderSetup source,TradeSetup target){
    	target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		target.setTaskName(returnTaskName(source.getSettingType()));
		target.setTaskLevel(1);
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//超出设置时间段，自动顺延至次日发送
		target.setTimeOutInform(true);
		//提醒时间:分钟/小时/天
		/*if(source.getReminderTime()!=null && !"".equals(source.getReminderTime().trim())){
			if(source.getReminderTime().contains("分钟")){
				target.setTimeType(1);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("分钟", "")));
			}else if(source.getReminderTime().contains("小时")){
				target.setTimeType(2);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("小时", "")));
			}else if(source.getReminderTime().contains("天")){
				target.setTimeType(3);
				target.setRemindTime(Integer.valueOf(source.getReminderTime().trim().replace("天", "")));
			}
		}*/
		//回款提醒1天
		target.setTimeType(3);
		target.setRemindTime(1);
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
		return target;
	}
    
    
	
    /**
     * OrderSettingInfo.REFUND_CREATED, "买家申请退款//单任务
     * */
  	public TradeSetup handleRefundCreated(OrderSetup source,TradeSetup target){
  		target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//超出设置时间段，自动顺延至次日发送
		target.setTimeOutInform(true);
		if(source.getFiltingConditions()!=null && !"".equals(source.getFiltingConditions())){
			if(source.getFiltingConditions().contains("1"))
				target.setFilterOnce(true);
			if(source.getFiltingConditions().contains("3"))
				target.setFilterBlack(true);
		}
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
  			return target;
  	}
  	    
  	
  	/**
     * OrderSettingInfo.REFUND_AGREE, "同意退款||等待退货//单任务
     * */
  	public TradeSetup handleRefundAgree(OrderSetup source,TradeSetup target){
  		target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//超出设置时间段，自动顺延至次日发送
		target.setTimeOutInform(true);
		//提醒时间:分钟/小时/天
		if(source.getFiltingConditions()!=null && !"".equals(source.getFiltingConditions())){
			if(source.getFiltingConditions().contains("1"))
				target.setFilterOnce(true);
			if(source.getFiltingConditions().contains("3"))
				target.setFilterBlack(true);
		}
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
  		return target;
  	}
  	
  	
  	
  	/**
     * OrderSettingInfo.REFUND_SUCCESS, "退款成功//单任务
     * */
  	public TradeSetup handleRefundSuccess(OrderSetup source,TradeSetup target){
  		target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//超出设置时间段，自动顺延至次日发送
		target.setTimeOutInform(true);
		//提醒时间:分钟/小时/天
		if(source.getFiltingConditions()!=null && !"".equals(source.getFiltingConditions())){
			if(source.getFiltingConditions().contains("1"))
				target.setFilterOnce(true);
			if(source.getFiltingConditions().contains("3"))
				target.setFilterBlack(true);
		}
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
  		return target;
  	}

  	    
  	
  	/**
     * OrderSettingInfo.REFUND_REFUSE, "拒绝退款//单任务
     * */
  	public TradeSetup handleRefundRefuse(OrderSetup source,TradeSetup target){
  		target.setUserId(source.getUserId());
		//类型
		target.setType(source.getSettingType());
		target.setCreatedBy(source.getUserId());
    	target.setLastModifiedBy(source.getUserId());
    	target.setCreatedDate(new Date());
    	target.setLastModifiedDate(new Date());
		//TODO??status为空
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(false);
		}
		//高级设置才有
		target.setMinPayment(source.getPayAmtOne());
		target.setMaxPayment(source.getPayAmtTwo());
		//通知时间
		if(source.getEndTime()==null || "".equals(source.getEndTime().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getEndTime().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getEndTime().trim().length()==5){
				target.setMaxInformTime(source.getEndTime().trim()+":00");
			}else  if(source.getEndTime().trim().length()==4){
				target.setMaxInformTime("0"+source.getEndTime().trim()+":00");
			}
		}
		if(source.getStartTime()==null || "".equals(source.getStartTime().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getStartTime().trim().length()==5){
				target.setMinInformTime(source.getStartTime().trim()+":00");
			}else  if(source.getStartTime().trim().length()==4){
				target.setMinInformTime("0"+source.getStartTime().trim()+":00");
			}
		}
		//超出设置时间段，自动顺延至次日发送
		target.setTimeOutInform(true);
		//提醒时间:分钟/小时/天
		if(source.getFiltingConditions()!=null && !"".equals(source.getFiltingConditions())){
			if(source.getFiltingConditions().contains("1"))
				target.setFilterOnce(true);
			if(source.getFiltingConditions().contains("3"))
				target.setFilterBlack(true);
		}
		//任务执行时间==默认持续执行
		target.setExecuteType(true);
  		return target;
  	}
  	   
 
  		
  	
  	   
  	//OrderSettingInfo.AUTO_RATE, "自动评价//单任务
  	//public TradeSetup handleAutoRate(OrderSetup source,TradeSetup target){
  			//return target;}
  	    
  	//OrderSettingInfo.APPRAISE_MONITORING_ORDER, "中差评监控//单任务
  	//public TradeSetup handleAppraiseMonitoringOrder(OrderSetup source,TradeSetup target){
  			//return target;}
  	   
  	//OrderSettingInfo.APPRAISE_PACIFY_ORDER, "中差评安抚//单任务
  	//public TradeSetup handleAppraisePacifyOrder(OrderSetup source,TradeSetup target){
  			//return target;}
  	    
  	//OrderSettingInfo.GOOD_VALUTION_REMIND, "好评提醒//单任务
  	//public TradeSetup handleGoodValutionRemind(OrderSetup source,TradeSetup target){
  			//return target;}
  	   
  	
  	//put(OrderSettingInfo.ADVANCE_PUSH_PAYMENT, "预售催付");
  	//put(OrderSettingInfo.ABNORMAL_GOODS_REMIND, "疑难件提醒");
	//put("OrderSettingInfo.ORDER_MANUAL_REMIND","手动订单提醒")
	public void processData(List<OrderSetup> list) {
		for (OrderSetup orderSetup : list) {
			if(orderSetup.getUserId()==null || "".equals(orderSetup.getUserId())
				|| orderSetup.getStatus()==null || "".equals(orderSetup.getStatus())
				|| orderSetup.getSettingType()==null || "".equals(orderSetup.getSettingType())
				//手动订单提醒
				|| OrderSettingInfo.ORDER_MANUAL_REMIND.equals(orderSetup.getSettingType())
				//疑难件提醒
				|| OrderSettingInfo.ABNORMAL_GOODS_REMIND.equals(orderSetup.getSettingType())
			)
				continue;
			mappingHandleData(orderSetup);
		}
	}
	
	private void mappingHandleData(OrderSetup source) {
		TradeSetup target = null;
		if(OrderSettingInfo.CREATE_ORDER.equals(source.getSettingType())){
			// 下单关怀 //多任务
			target = handleCreateOrder(source,new TradeSetup());
		 }else if(OrderSettingInfo.FIRST_PUSH_PAYMENT.equals(source.getSettingType())){
			 //常规催付 //多任务
		    target = handleFirstPushPayment(source,new TradeSetup());
		 }else if(OrderSettingInfo.SECOND_PUSH_PAYMENT.equals(source.getSettingType())){
			 //二次催付//单任务
		    target = handleSecondPushPayment(source,new TradeSetup());
		 }else if(OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT.equals(source.getSettingType())){
			 //聚划算催付//单任务
		   	target = handlePreferentialPushPayment(source,new TradeSetup());
		 }else if(OrderSettingInfo.PAYMENT_CINCERN.equals(source.getSettingType())){
			 //付款关怀 //多任务
		    target = handlePaymentCincern(source,new TradeSetup());
		 }else if(OrderSettingInfo.SHIPMENT_TO_REMIND.equals(source.getSettingType())){
			 //发货提醒  //多任务
		    target = handleShipmentToRemind(source,new TradeSetup());
		 }else if(OrderSettingInfo.DELAY_SEND_REMIND.equals(source.getSettingType())){
			 //延时发货提醒 //多任务
		     target = handleDelaySendRemind(source,new TradeSetup());
		 }else if(OrderSettingInfo.ARRIVAL_LOCAL_REMIND.equals(source.getSettingType())){
			 //到达同城提醒 //多任务
		     target = handleArrivalLocalRemind(source,new TradeSetup());
		 }else if(OrderSettingInfo.SEND_GOODS_REMIND.equals(source.getSettingType())){
			 //派件提醒   //多任务
		     target = handleSendGoodsRemind(source,new TradeSetup());
		 }else if(OrderSettingInfo.REMIND_SIGNFOR.equals(source.getSettingType())){
			 //签收提醒 //多任务
		     target = handleRemindSignfor(source,new TradeSetup());
		 }else if(OrderSettingInfo.COWRY_CARE.equals(source.getSettingType())){
			 //宝贝关怀 //多任务
		     target = handleCowryCare(source,new TradeSetup());
		 }else if(OrderSettingInfo.RETURNED_PAYEMNT.equals(source.getSettingType())){
			 //回款提醒  //多任务
		     target = handleReturnedPayemnt(source,new TradeSetup());
		 }else if(OrderSettingInfo.REFUND_CREATED.equals(source.getSettingType())){
			 //买家申请退款//单任务
		   	target = handleRefundCreated(source,new TradeSetup());
		 }else if(OrderSettingInfo.REFUND_SUCCESS.equals(source.getSettingType())){
			 //退款成功//单任务
		   	target = handleRefundSuccess(source,new TradeSetup());
		 }else if(OrderSettingInfo.REFUND_AGREE.equals(source.getSettingType())){
			 //同意退款//单任务
		   	target = handleRefundAgree(source,new TradeSetup());
		 }else if(OrderSettingInfo.REFUND_REFUSE.equals(source.getSettingType())){
			 //拒绝退款//单任务
		   	target = handleRefundRefuse(source,new TradeSetup());
		 }
		if(target != null){
			//根据userId&type判断存在与否
			if(tradeSetupService.isExist(target.getUserId(),target.getType())){
				//存在更新
				tradeSetupService.updateTradeSetupSync(target);
			}else{
				//不存在保存
				tradeSetupService.saveTradeSetupSync(target);
			}
		}	   
	}

	
	
	
	/**
	* @Title: returnTaskName
	* @Description: (生成一个设置名称:汉字名称+年月日时分秒)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private String returnTaskName(String type){
		String name = null;
		if(dictMap.containsKey(type)){
			name = dictMap.get(type);
		}else{
			name = "";
		}
		return name+DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYYMMDDHHMMSS);
	}
    
    
}
