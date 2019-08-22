package s2jh.biz.shop.crm.schedule.memberInfoJob;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;
@MetaData("执行定时任务,更新用户信息.............")
public class MemberInfoJob extends BaseQuartzJobBean {

	@Autowired
	private MemberInfoService memberInfoService;
	
	
	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");
//		
//		System.out.println("<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|更新用户信息开始|>|>|>|>|>|>|>|>|>|>|>|>|>|>|>|>"+sdf.format(new Date()));
//		
//		//定时更新会员信息以及列表
//		memberInfoService.updateMemberInfoBatch(null);
//		
//		
//		System.out.println("<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|<|更新用户信息结束|>|>|>|>|>|>|>|>|>|>|>|>|>|>|>|>"+sdf.format(new Date()));
		
		return null;
	}

}
