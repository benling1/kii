package s2jh.biz.shop.crm.autoJob;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.api.ApiException;

import s2jh.biz.shop.crm.taobao.taobaoInfo;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;

@MetaData("执行定时任务，更新买家会员信息")
public class AutoMemberInfoJob extends BaseQuartzJobBean{

	@Autowired
	private AutoMemberInfoService autoMemberInfoService;
	//定时任务，根据卖家更新买家会员信息
	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		try {
			autoMemberInfoService.updateMemberInfo("");
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

}
