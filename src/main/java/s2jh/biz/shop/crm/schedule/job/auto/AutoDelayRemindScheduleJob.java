package s2jh.biz.shop.crm.schedule.job.auto;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.taobao.info.ScheduleJobNameInfo;
import s2jh.biz.shop.crm.taobao.service.AutoDelayRemindService;
import s2jh.biz.shop.crm.taobao.service.impl.AutoDelayRemindServiceImpl;

/**
 * 延迟发货提醒定时发送
 * @author Administrator
 *
 */
@Component
@Deprecated 
public class AutoDelayRemindScheduleJob implements Job{
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap= context.getJobDetail().getJobDataMap();
		String jobName = dataMap.getString("jobName");//  定时的类型_订单ID   
		ApplicationContext servletContent = (ApplicationContext) dataMap.get("servletContent");
		AutoDelayRemindService autoDelayRemindService = servletContent.getBean(AutoDelayRemindServiceImpl.class);
		if(jobName.contains(ScheduleJobNameInfo.AUTO_DELAY_REMIND)){
			String param[] = jobName.split("_");
			String sellerName = param[1];
			try {
				autoDelayRemindService.autoDelayRemindFunction(sellerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		TriggerManager.delJob(jobName, ScheduleJobNameInfo.AUTO_DELAY_REMIND);//删除一次性定时任务
		
	}

	
}
