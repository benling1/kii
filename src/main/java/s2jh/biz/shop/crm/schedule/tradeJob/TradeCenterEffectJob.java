package s2jh.biz.shop.crm.schedule.tradeJob;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.tradecenter.service.TradeCenterEffectService;
import lab.s2jh.module.schedule.BaseQuartzJobBean;

public class TradeCenterEffectJob extends BaseQuartzJobBean {

	@Autowired
	private TradeCenterEffectService tradeCenterEffectService;
	
	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		tradeCenterEffectService.tradeCenterEffectJob(new Date(), null, null);
		return null;
	}

}
