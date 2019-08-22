package s2jh.biz.shop.crm.order.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.order.dao.orderReminderDao;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.util.RunnableStartOrderReminder;
import s2jh.biz.shop.crm.schedule.job.auto.AutoManualOrderScheduleManual;
import s2jh.biz.shop.crm.schedule.triggers.TriggerManager;
import s2jh.biz.shop.crm.taobao.info.ScheduleJobNameInfo;
import s2jh.biz.shop.utils.ConversionTime;

@Service
@Transactional
public class OrderReminderService extends BaseService<OrderReminder, Long>{
	
	@Autowired
	private orderReminderDao orederReminderDao;
			
	@Autowired
	private MyBatisDao mybatisDao;
	
	
	@Override
	protected BaseDao<OrderReminder, Long> getEntityDao() {
		return orederReminderDao;
	}
	
	/**
	 * 根据卖家编号查询手动订单提醒条件
	 */
	public OrderReminder findOrderReminder(String userId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		OrderReminder or = mybatisDao.findBy(OrderReminder.class.getName(), "findOrderReminder", map);
		if(or==null){
			map.put("settingType", "26");
			SmsTemplate smsTemplate = mybatisDao.findBy(SmsTemplate.class.getName(), "findAutoModel", map);
			if(smsTemplate!=null){
				or = new OrderReminder();
				or.setUserId(userId);
				or.setContent(smsTemplate.getContent());
				or.setOrderStatus(null);
				or.setBookingStatus(null);
				or.setOrderType(null);
				or.setEvaluateStatus(null);
				or.setSellerSign(null);
				or.setOrderSource(null);
				or.setAlreadySendMessages(null);
				or.setSelectCommodityType(null);
				or.setSmsSign(userId);
				or.setIsTiming("1");
				or.setStatus("0");
				
				try {
					mybatisDao.execute(OrderReminder.class.getName(), "doAutoCreateByIsNull",or );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return or;
	}
	
	/**
	 * 添加手动订单提醒条件
	 */
	public int addOrderReminder(Map<String,Object> map){
		int date = mybatisDao.execute(OrderReminder.class.getName(), "addOrderReminder", map);
		return date;
	}
	
	/**
	 * 根据编号修改手动订单提醒基本条件
	 */
	public int updateOrderReminder(Map<String,Object> map){
		int date = mybatisDao.execute(OrderReminder.class.getName(), "updateOrderReminder", map);
		return date;
	}
	
	/**
	 * 根据编号修改手动订单提醒短信设置条件
	 * @return int
	 */
	public int updateOr(Map<String,Object> map){
		int date = mybatisDao.execute(OrderReminder.class.getName(), "updateOr", map);
		return date;
	}

	/**
	 * 根据id更新手动订单开启关闭状态
	 * ZTK2017年1月10日下午4:19:00
	 * @throws ParseException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public Map<String, Object> updateStatusById(Map<String, Object> map) throws ParseException, InterruptedException, ExecutionException {
		//返回参数的map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String isTiming = (String)map.get("isTiming");
		String userName = (String)map.get("userName");
		String status = (String)map.get("status");
		String timing = (String)map.get("timing");
		OrderReminder orderReminder = this.findOrderReminder(userName);
		String oldStatus = "0";
		if(orderReminder!=null){
			oldStatus = orderReminder.getStatus();
		}
		int count = mybatisDao.execute(OrderReminder.class.getName(), "updataStatusById", map);
		Date nowDate = new Date();
		if(count==1){
			if("1".equals(status)){   //状态是开启的
				ExecutorService executors = Executors.newFixedThreadPool(1);
				if("1".equals(isTiming) ){ //如果是非定时，则立即执行任务
					/*RunnableStartOrderReminder runnableStartOrderReminder = new RunnableStartOrderReminder();
					runnableStartOrderReminder.setSellerNick(userName);
					new Thread(runnableStartOrderReminder).start();*/
					RunnableStartOrderReminder runnableStartOrderReminder = new RunnableStartOrderReminder(userName);
					Future<Map<String, Object>> future = executors.submit(runnableStartOrderReminder);
					resultMap = future.get();
				}else if("0".equals(isTiming)){ //添加定时任务
					Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timing);
					if(startTime!=null){ //开始时间不能为空
						Long nowDateLong = nowDate.getTime();
						Long startTimeLong = startTime.getTime();
						String sellerNick = orderReminder.getUserId();
						if(startTimeLong>nowDateLong){
							WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
					        ServletContext servletContext = webApplicationContext.getServletContext();  
					        ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
							String timeSend = ConversionTime.conversionTime(startTime);
							String jobName = ScheduleJobNameInfo.AUTO_MANUAL_ORDERS+"_"+sellerNick; //jobName  定时的类型_卖家昵称     例子:manual_crazyboy   
							TriggerManager.addJob(jobName,ScheduleJobNameInfo.AUTO_MANUAL_ORDERS, timeSend, AutoManualOrderScheduleManual.class,jobName,application);
							Scheduler scheduler = null;
							try {
								//添加并启动
								scheduler = StdSchedulerFactory.getDefaultScheduler();
								scheduler.start();
							} catch (SchedulerException e) {
								e.printStackTrace();
							}
							resultMap.put("flag", false);
							resultMap.put("msg", "添加定时成功");
						}else if(startTimeLong==nowDateLong){
							//定时时间和当前时间相等  马上执行
							/*new Thread(new RunnableStartOrderReminder()).start();*/
							Future<Map<String, Object>> future = executors.submit(new RunnableStartOrderReminder(userName));
							resultMap = future.get();
						}else{
							//开始时间小于当前时间  定时无意义  不开启
						}
					}
				}
			}else if("1".equals(oldStatus) && "0".equals(status)){//如果之前是开启过的  现在想要关闭且这个任务的开始时间还没到的话  删除定时任务
				Date oldDate = orderReminder.getStartTime();
				Long nowDateLong = nowDate.getTime();
				if(oldDate!=null){
					Long oldDateLong = oldDate.getTime();
					if(nowDateLong>=oldDateLong){
						String jobName = ScheduleJobNameInfo.AUTO_MANUAL_ORDERS+"_"+userName; //jobName  定时的类型_卖家昵称     例子:manual_crazyboy   
						TriggerManager.delJob(jobName, ScheduleJobNameInfo.AUTO_MANUAL_ORDERS);//删除一次性定时任务
					}
				}
			}
		}else{
			
		}
		return resultMap;
	}
}
