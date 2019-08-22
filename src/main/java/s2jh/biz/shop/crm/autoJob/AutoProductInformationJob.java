package s2jh.biz.shop.crm.autoJob;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.api.ApiException;

import lab.s2jh.module.schedule.BaseQuartzJobBean;

public class AutoProductInformationJob extends BaseQuartzJobBean{
	
	@Autowired
	private AutoProductInformationService autoProductInformationService;

	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		
		try {
			autoProductInformationService.productInformation();
		} catch (ApiException e) {
			
			e.printStackTrace();
		}

   
		return null;
	}

}
