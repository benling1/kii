package s2jh.biz.shop.crm.schedule.TemporaryItemJob;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.item.service.ItemService;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;

@MetaData("找丢失数据任务(临时)")
public class TemporaryItemJob  extends BaseQuartzJobBean{
	
	@Autowired
	private ItemService itemService;

	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		//itemService.saveItemsG();
		System.out.println("-----------------------------任务开始执行----------------------------------");
		return null;
	}

}
