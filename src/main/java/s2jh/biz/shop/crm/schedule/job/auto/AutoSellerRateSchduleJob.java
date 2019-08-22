package s2jh.biz.shop.crm.schedule.job.auto;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import s2jh.biz.shop.crm.schedule.triggers.TriggerManager;
import s2jh.biz.shop.crm.taobao.info.ScheduleJobNameInfo;
import s2jh.biz.shop.crm.taobao.service.AutoSellerRateService;
//定时 卖家自动评价订单
@Deprecated 
public class AutoSellerRateSchduleJob implements Job  {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap dataMap= arg0.getJobDetail().getJobDataMap();
		String jobName = dataMap.getString("jobName");//  定时的类型_订单ID    例子: autoRate_121252_1251445
		ApplicationContext servletContent = (ApplicationContext) dataMap.get("servletContent");
		AutoSellerRateService autoSellerRateService= servletContent.getBean(AutoSellerRateService.class);
		if(jobName.contains(ScheduleJobNameInfo.AUTO_SELLER_RATE)){
			String param[] = jobName.split("_");
			String oid = param[1];
			String tid = param[2];
			String userId = param[3];
			try {
				autoSellerRateService.autoSellerRateFunction(tid,oid,userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		TriggerManager.delJob(jobName, ScheduleJobNameInfo.AUTO_SELLER_RATE);//删除一次性定时任务
	}
	
}
