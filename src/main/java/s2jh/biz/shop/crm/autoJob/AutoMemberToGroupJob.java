package s2jh.biz.shop.crm.autoJob;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;
@MetaData("执行定时任务，更新会员所在分组")
public class AutoMemberToGroupJob extends BaseQuartzJobBean {

	@Autowired
	private AutoMemberToGroupService autoMemberToGroupService;
	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
//		autoMemberToGroupService.addMemberToGroup();
		return null;
	}

}
