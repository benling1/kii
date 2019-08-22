package s2jh.biz.shop.crm.schedule.tradeJob;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.order.service.EffectPictureService;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.util.DateUtils;
import lab.s2jh.module.schedule.BaseQuartzJobBean;


@MetaData("执行定时任务，保存每日效果分析到Mysql.............")
public class EffectJob extends BaseQuartzJobBean{

	private Logger logger = LoggerFactory.getLogger(EffectJob.class);
	
	@Autowired
	private EffectPictureService effectPictureService;
	
	protected String executeInternalBiz(JobExecutionContext context) {
		Date nowDate = new Date();
		//定时在第二天的凌晨2点执行，计算前一天的23:59:59
		Date yesterDayEndTime = DateUtils.getEndTimeOfDay(DateUtils.nDaysAgo(1, nowDate));
		logger.info("执行定时任务，保存每日效果分析到Mysql.............时间：" + nowDate);
//		effectPictureService.findAllMsgIdNew(yesterDayEndTime);
		effectPictureService.findAllMsgIdOfTen(nowDate);
		return null;
	}

	
}
