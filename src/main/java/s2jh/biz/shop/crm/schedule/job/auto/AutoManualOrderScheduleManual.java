package s2jh.biz.shop.crm.schedule.job.auto;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import s2jh.biz.shop.crm.schedule.triggers.TriggerManager;
import s2jh.biz.shop.crm.taobao.info.ScheduleJobNameInfo;
import s2jh.biz.shop.crm.taobao.service.judgment.manual.JudgmentManualStartUtil;
//手动提醒定时发送
@Deprecated 
public class AutoManualOrderScheduleManual implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap dataMap= arg0.getJobDetail().getJobDataMap();
		String jobName = dataMap.getString("jobName");//  定时的类型_卖家昵称     例子:manual_crazyboy     
		ApplicationContext servletContent = (ApplicationContext) dataMap.get("servletContent");
		JudgmentManualStartUtil judgmentManualStartUtil= servletContent.getBean(JudgmentManualStartUtil.class);
		if(jobName.contains(ScheduleJobNameInfo.AUTO_MANUAL_ORDERS)){
			String param[] = jobName.split("_");
			String sellerNick = param[1];
			try {
				judgmentManualStartUtil.sendMessage(sellerNick);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		TriggerManager.delJob(jobName, ScheduleJobNameInfo.AUTO_MANUAL_ORDERS);//删除一次性定时任务
	}
}
