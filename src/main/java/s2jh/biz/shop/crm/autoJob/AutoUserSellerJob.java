package s2jh.biz.shop.crm.autoJob;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.api.ApiException;

import lab.s2jh.module.schedule.BaseQuartzJobBean;

public class AutoUserSellerJob extends BaseQuartzJobBean {
	
	@Autowired
	private AutoUserSellerService autoUserSellerService;
	
	@Override
    protected String executeInternalBiz(JobExecutionContext context) {
		try {
			autoUserSellerService.sellerUserInfo();
		} catch (ApiException e) {
			e.printStackTrace();
		}
        return null;
    }

}
