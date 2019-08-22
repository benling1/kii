package s2jh.biz.shop.crm.schedule.job.auto;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import s2jh.biz.shop.crm.taobao.service.judgment.manual.JudgmentManualStartUtil;
@Deprecated 
public class TestAuto  implements Job {
//	@Autowired
//	private JudgmentManualStartUtil judgmentManualStartUtil;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap= context.getJobDetail().getJobDataMap();
		ApplicationContext servletContent = (ApplicationContext) dataMap.get("servletContent");
		JudgmentManualStartUtil judgmentManualStartUtil= servletContent.getBean(JudgmentManualStartUtil.class);
		try {
			judgmentManualStartUtil.sendMessage("crazy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
