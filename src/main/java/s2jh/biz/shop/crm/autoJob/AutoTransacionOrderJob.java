package s2jh.biz.shop.crm.autoJob;

import lab.s2jh.module.schedule.BaseQuartzJobBean;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.api.ApiException;

import lab.s2jh.core.annotation.MetaData;
@MetaData("执行定时任务，更新卖家订单信息")
public class AutoTransacionOrderJob extends BaseQuartzJobBean {
	@Autowired
	private AutoTransactionOrdeService autoTransactionOrdeService;
	
	//执行定时任务，更新卖家订单信息
	@Override
    protected String executeInternalBiz(JobExecutionContext context) {
		//autoTransactionOrdeService.getUserInfo();
		try {
			autoTransactionOrdeService.wirteOrdersInfo();
		} catch (ApiException e) {
			e.printStackTrace();
		}
        return null;
    }
}
