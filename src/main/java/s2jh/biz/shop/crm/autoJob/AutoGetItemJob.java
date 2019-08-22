package s2jh.biz.shop.crm.autoJob;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.api.ApiException;

import lab.s2jh.module.schedule.BaseQuartzJobBean;

public class AutoGetItemJob extends BaseQuartzJobBean{
	
	@Autowired
	private AutoGetItemService autoGetItemService;

	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		
		try {
			autoGetItemService.itemInformation();
		} catch (ApiException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

}
