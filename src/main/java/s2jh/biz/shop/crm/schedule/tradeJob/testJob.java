/**
 * 
 */
package s2jh.biz.shop.crm.schedule.tradeJob;


import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/** 
 * @Title: 
 * @date 2017年1月12日--下午1:57:42 
 * @param     设定文件 
 * @return 返回类型 
 * @throws 
 */
public class testJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap dataMap= arg0.getJobDetail().getJobDataMap();
		String love = dataMap.getString("jobName");
		String jobGroup = dataMap.getString("jobGroupName");
		System.out.println("这是"+jobGroup+"的"+love);
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★"+"这里是测试的定时任务！");  
	}

}
