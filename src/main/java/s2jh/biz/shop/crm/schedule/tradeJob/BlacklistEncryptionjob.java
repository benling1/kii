package s2jh.biz.shop.crm.schedule.tradeJob;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.api.SecretException;

import s2jh.biz.shop.crm.message.service.SmsBlackListService;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;

@MetaData("执行定时任务，将黑名单列表未加密的 数据 加密.............")
public class BlacklistEncryptionjob extends BaseQuartzJobBean {
	
	@Autowired
	private SmsBlackListService smsBlackListService;

	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		System.out.println("*****************************开始加密黑名单数据！********************************");
		smsBlackListService.encryptionJob();
		System.out.println("*****************************黑名单加密执行完毕！********************************");
		return null;
	}
 
}
