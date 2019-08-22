/**
 * 
 */
package s2jh.biz.shop.crm.schedule.triggers;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Title: 一次性动态定时任务
 * @date 2017年1月13日--下午1:43:44
 * @param 设定文件
 * @return 返回类型
 * @throws
 */
@Component
public class TriggerManager {
	/**
	 * @param ac 
	 * 创建人：邱洋
	 * @Title: 添加动态定时任务到Scheduler中
	 * @date 2017年1月13日--下午1:44:51
	 * @return void
	 * @throws
	 */
	@SuppressWarnings({ "deprecation" })
	public static void addJob(String JobName, String JobGroupName, String time,
			Class<? extends Job> jobClass,String parameter,ApplicationContext servletContent) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			JobDetail jobDetail = new JobDetailImpl(JobName, JobGroupName,
					jobClass);// 任务名，任务组，任务执行类
			jobDetail.getJobDataMap().put("jobName", parameter);
			jobDetail.getJobDataMap().put("servletContent", servletContent);
			CronTrigger trigger = new CronTriggerImpl(JobName, JobGroupName,
					time);// 触发器名,触发器组,触发时间
			scheduler.scheduleJob(jobDetail, trigger);// 将任务信息添加到sheduler中
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 创建人：邱洋
	 * @Title: 根据定时任务名称、组名删除任务
	 * @date 2017年1月13日--下午2:09:05
	 * @return void
	 * @throws
	 */
	public static void delJob(String JobName, String JobGroupName) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			TriggerKey triggerKey = new TriggerKey(JobName, JobGroupName);
			JobKey jobKey = new JobKey(JobName, JobGroupName);
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 删除触发器
			scheduler.deleteJob(jobKey);// 删除任务
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
