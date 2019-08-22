package s2jh.biz.shop.crm.autoJob;

import java.text.SimpleDateFormat;
import java.util.Date;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
@MetaData("执行定时任务，读取.............")
public class AutoLogisticsOrderJob  extends BaseQuartzJobBean{
	
	@Autowired
	private AutoLogisticsOrderJobService autoLogisticsOrderJobService;
	
	
	@Override
	protected String executeInternalBiz(JobExecutionContext context){
		System.err.println(">>>>>>>>>>>>同步物流数据>>>>>>>>>>>>"+new Date().toString());
		
		//必须传入item值格式("HH:mm:ss")如:00:00:00
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");  
		String item=sdf.format(date);
		autoLogisticsOrderJobService.executeLogisticsOrder(item);
		return null;
	}
}
