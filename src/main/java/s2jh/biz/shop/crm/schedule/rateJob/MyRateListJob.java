package s2jh.biz.shop.crm.schedule.rateJob;

import java.text.SimpleDateFormat;
import java.util.Date;

import lab.s2jh.module.schedule.BaseQuartzJobBean;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.order.service.TradeRatesService;
import s2jh.biz.shop.utils.DateUtils;
public class MyRateListJob extends BaseQuartzJobBean {
	
	public Logger Logger = LoggerFactory.getLogger(MyRateListJob.class);

	@Autowired
	private TradeRatesService tradeRatesService;

	protected String executeInternalBiz(JobExecutionContext context) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = "06:00:00";
		String endDate = "23:59:59";
		String MornDate = "00:00:00";
		String NighDate = "24:00:00";
		boolean inMorn = DateUtils.isInDate(MornDate, startDate);
		boolean inNigh = DateUtils.isInDate(endDate, NighDate);
		if(inMorn || inNigh){
			Logger.info("zzzzzzzzzzzzzztttttttttttttttkkkkkkkkkkk的定时任务不执行了，在08:00-22:00之间,时间是:"
					+ format.format(new Date()));
			return null;
		}
		Logger.info("zzzzzzzzzzzzzzzzzzzzztttttttttttttttttttttkkkkkkkkkkkkkk的定时任务执行了，时间是:"
						+ format.format(new Date()));
		tradeRatesService.saveTradeRate();
		return null;
	}
}