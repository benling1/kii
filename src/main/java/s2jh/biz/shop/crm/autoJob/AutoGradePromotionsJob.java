package s2jh.biz.shop.crm.autoJob;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.member.service.GradePromotionsService;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;

@MetaData("执行定时任务，更新会员等级")
public class AutoGradePromotionsJob extends BaseQuartzJobBean{
	
	@Autowired
	private GradePromotionsService gradePromotionsService;
	
	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		return null;
	}

}
