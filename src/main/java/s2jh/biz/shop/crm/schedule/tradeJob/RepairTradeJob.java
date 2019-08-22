package s2jh.biz.shop.crm.schedule.tradeJob;


import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.write.DateFormat;
import net.sf.json.JSONObject;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.order.service.TransactionTradeService;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;
@MetaData("执行定时任务，读取.............")
public class RepairTradeJob extends BaseQuartzJobBean{
	
//	@Autowired
//	private TransactionTradeService transactionTradeService;
	
	@Override
	protected String executeInternalBiz(JobExecutionContext context){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");
//	
//		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&订单同步全局定时任务开始保存%%%%%%%%%%%%%%%%%%%%"+sdf.format(new Date()));
//		transactionTradeService.repairTbTradeData();
//		
//		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&订单同步全局定时任务结束保存%%%%%%%%%%%%%%%%%%%%"+sdf.format(new Date()));
		return null;
	}

	

}
