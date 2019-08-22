package s2jh.biz.shop.crm.schedule.tradeJob;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.item.service.ItemService;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;

@MetaData("执行定时任务，读取商品.............")
public class Itemjob extends BaseQuartzJobBean {
	
	@Autowired
	private ItemService itemService;

	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		System.out.println("*****************************开始更新商品数据！********************************");
		itemService.synchronizeItem();
		System.out.println("*****************************更新商品数据已完成！********************************");
		return null;
	}
 
}
