package s2jh.biz.shop.crm.schedule.job.auto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.schedule.triggers.TriggerManager;
import s2jh.biz.shop.crm.taobao.info.ScheduleJobNameInfo;
import s2jh.biz.shop.crm.taobao.service.ScheduleSendMessageService;
import s2jh.biz.shop.crm.taobao.service.impl.AutoSellerRateServiceImpl;
import s2jh.biz.shop.crm.taobao.service.judgment.manual.JudgmentManualStartUtil;
import s2jh.biz.shop.crm.tmc.schedule.TmcScheduleService;
import s2jh.biz.shop.utils.ConversionTime;
@Component
@SuppressWarnings("unused")
public class InitAutoOneScheduleRunnable implements Runnable{
	@Autowired
	protected MyBatisDao myBatisDao;
	@Autowired
	protected ScheduleSendMessageService scheduleSendMessageService;
	@Autowired
	protected TradeInfoService tradeInfoService;
	@Autowired
	protected TmcScheduleService tmcScheduleService;
	@Override
	public void run() {
//		//服务器启动后，检测一小时之前未发送的短信，扫描出来后并发送
		tmcScheduleService.initSendHourSms();
//		checkOneHourSendSms();
//		try {
//			Thread.sleep(60000);  //等待60s
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
////		test();
//		//加载手动订单提醒定时任务
//		addAutoManualOrders();
//		//加载自动评价订单任务
//		addAutoSellerRate();
////		加载延迟发货提醒的定时任务
//		addAutoDelayRemind();
	}
	private void checkOneHourSendSms(){
		this.scheduleSendMessageService.sendOneHourSms();
	}
	/**
	 * 启动tomcat的时候加载自动评价定时任务
	 */
	private void addAutoSellerRate(){
		System.out.println("*****************        启动tomcat的时候加载自动评价定时任务         *********************");
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
        ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		List<OrderRateCareSetup> orderRateCareSetupList = myBatisDao.findList(OrderRateCareSetup.class.getName(), "findSelleNickByOpen", null);
		Iterator<OrderRateCareSetup> iterOrderRateCareSetupList = orderRateCareSetupList.iterator();
		Calendar cal = Calendar.getInstance();
		OrdersDTO orders = null;
		Date startTime = null;
		while (iterOrderRateCareSetupList.hasNext()) {
			OrderRateCareSetup orderRateCareSetup = (OrderRateCareSetup) iterOrderRateCareSetupList.next();
			String sellerNick = orderRateCareSetup.getUserId();
			String rateChoose = orderRateCareSetup.getRateChoose();
			//查询卖家交易成功且未评价的所有订单,订单结束时间不能大于15天
			//List<Orders> orderList = myBatisDao.findList(Orders.class.getName(), "findAllByAutoRateOrder", sellerNick);
			//author:jackstraw_yu 修改
			List<OrdersDTO> orderList = tradeInfoService.queryAutoRateOrder(sellerNick);
			Iterator<OrdersDTO> orderIter = orderList.iterator();
			while (orderIter.hasNext()) {
				orders = (OrdersDTO) orderIter.next();
				String tid = orders.getTid();
				String oid = orders.getOid();
				startTime = orders.getEndTimeUTC();
				cal.setTime(startTime);
				switch (rateChoose) {
				case "抢评":{//订单结束前一小时评价
					cal.add(Calendar.DATE, 15);
					cal.add(Calendar.HOUR, -2);
					startTime = cal.getTime();
					break;
				}
				case "延迟评":{
					String deferRateDay = orderRateCareSetup.getDeferRateDay();
					if(deferRateDay!=null){
						try {
							Integer addDay = Integer.parseInt(deferRateDay);
							cal.add(Calendar.DATE, addDay);
						} catch (Exception e) {
							cal.add(Calendar.DATE, 7);
						}
					}else{//默认七天
						cal.add(Calendar.DATE, 7);
					}
					startTime = cal.getTime();
					break;
				}	
				case "立即评":{
					AutoSellerRateServiceImpl autoSellerRateServiceImpl = application.getBean(AutoSellerRateServiceImpl.class);
					autoSellerRateServiceImpl.autoSellerRateFunction(tid, oid,sellerNick);
					break;
				}
				default:
					break;
				}
				Long nowLong = new Date().getTime();
				Long startLong = startTime.getTime();
				if(startLong>nowLong){//开始时间要大于等于当前时间
					String time = ConversionTime.conversionTime(startTime);
					String jobName = ScheduleJobNameInfo.AUTO_SELLER_RATE + "_" + tid + "_" + oid + "_" + sellerNick; //jobName   定时的类型_tid_oid    例子: autoRate_1212522124154
					TriggerManager.addJob(jobName,ScheduleJobNameInfo.AUTO_SELLER_RATE, time, AutoSellerRateSchduleJob.class,jobName,application);
					Scheduler scheduler = null;
					try {
						//添加并启动
						scheduler = StdSchedulerFactory.getDefaultScheduler();
						scheduler.start();
					} catch (SchedulerException e) {
						e.printStackTrace();
					}
				}else {
					AutoSellerRateServiceImpl autoSellerRateServiceImpl = application.getBean(AutoSellerRateServiceImpl.class);
					autoSellerRateServiceImpl.autoSellerRateFunction(tid, oid,sellerNick);
				}
			}
		}
	}
	/**
	 * 手动订单提醒定时任务
	 */
	private void addAutoManualOrders(){
		System.out.println("*****************        启动tomcat的时候加载手动订单提醒定时任务         *********************");
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
        ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		List<OrderReminder> orderReminderList = myBatisDao.findList(OrderReminder.class.getName(), "findAllByScheduleSend", null);
		Iterator<OrderReminder> iterOrderReminder = orderReminderList.iterator();
		while (iterOrderReminder.hasNext()) {
			//取得具体的手动提醒订单，并添加到内存的定时任务中
			OrderReminder orderReminder = (OrderReminder) iterOrderReminder.next();
			Date startTime = orderReminder.getStartTime();
			if(startTime!=null){
				Date nowDate = new Date();
				Long nowDateLong = nowDate.getTime();
				Long startTimeLong = startTime.getTime();
				String sellerNick = orderReminder.getUserId();
				if(startTimeLong > nowDateLong){
					String time = ConversionTime.conversionTime(startTime);
					String jobName = ScheduleJobNameInfo.AUTO_MANUAL_ORDERS + "_" + sellerNick; //jobName  定时的类型_卖家昵称     例子:manual_crazyboy   
					TriggerManager.addJob(jobName,ScheduleJobNameInfo.AUTO_MANUAL_ORDERS, time, AutoManualOrderScheduleManual.class,jobName,application);
					Scheduler scheduler = null;
					try {
						//添加并启动
						scheduler = StdSchedulerFactory.getDefaultScheduler();
						scheduler.start();
					} catch (SchedulerException e) {
						e.printStackTrace();
					}
				}else if(startTimeLong == nowDateLong){
					//定时时间和当前时间相等  马上执行
					JudgmentManualStartUtil judgmentManualStartUtil = application.getBean(JudgmentManualStartUtil.class);
					try {
						judgmentManualStartUtil.sendMessage(sellerNick);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					
				}
			}
		}
	}
	/**
	 * 延迟发货提醒定时任务
	 */
	private void addAutoDelayRemind(){
		System.out.println("**********************启动tomcat的时候加载延迟发货提醒定时任务*******************************");
		//拿到service对象
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
        ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		
		
//		AutoDelayRemindScheduleJob autoDelayRemindScheduleJob = (AutoDelayRemindScheduleJob) ac.getBean("autoDelayRemindServiceImpl");
		//查询到所有settingtype是延迟发货11，status是0开启，executegenre是1执行周期每天的设置集合
		OrderSetup orderSetup = new OrderSetup();
		orderSetup.setExecuteGenre("1");
		orderSetup.setSettingType("11");
		orderSetup.setStatus("0");
		List<OrderSetup> orderSetups = myBatisDao.findList(OrderSetup.class.getName(), "findAllDelayRemind", orderSetup);
		Iterator<OrderSetup> iterator = orderSetups.iterator();
		while(iterator.hasNext()){
			//获取具体的基本设置
			OrderSetup setup = iterator.next();
			String sellerName = setup.getUserId();
			Date lastModifiedTime = setup.getLastModifiedDate();
			String time = ConversionTime.conversionDayTime(lastModifiedTime);
			time = "0 21 12 * * ?";
			String jobName = ScheduleJobNameInfo.AUTO_DELAY_REMIND+"_"+sellerName;
			TriggerManager.addJob(jobName, ScheduleJobNameInfo.AUTO_DELAY_REMIND, time, AutoDelayRemindScheduleJob.class, jobName,application);
			Scheduler scheduler = null;
			try {
				//添加并启动
				scheduler = StdSchedulerFactory.getDefaultScheduler();
//				scheduler = stdSchedulerFactory.getScheduler();
				scheduler.start();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}
	@Deprecated 
	private void test(){
	    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();  
        ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		String time = null;
		try {
			time = ConversionTime.conversionTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2017-01-20 10:55"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		TriggerManager.addJob("test","default", time,s2jh.biz.shop.crm.schedule.job.auto.TestAuto.class,"test",application);
		Scheduler scheduler = null;
		try {
			//添加并启动
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
