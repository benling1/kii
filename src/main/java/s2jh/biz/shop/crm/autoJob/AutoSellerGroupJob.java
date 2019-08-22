package s2jh.biz.shop.crm.autoJob;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.api.ApiException;

import s2jh.biz.shop.crm.taobao.taobaoInfo;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;

@MetaData("执行定时任务，更新卖家分组")
public class AutoSellerGroupJob extends BaseQuartzJobBean{
	
	@Autowired
	private AutoSellerGroupService autoSellerGroupService;
	
	//定时任务，更新卖家分组
	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		try {
			autoSellerGroupService.getGradeRule("");
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

}
