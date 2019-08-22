package s2jh.biz.shop.crm.schedule.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;
import s2jh.biz.shop.crm.other.entity.MobileSetting;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.tmc.schedule.TmcDeleteOldSms;
import s2jh.biz.shop.crm.tmc.schedule.TmcScheduleService;
import s2jh.biz.shop.crm.view.service.ShortLinkService;
@MetaData("执行定时任务，查询并执行定时发送短信任务")
/**
 * @author wy
 */
public class MyScheduleSendMessage extends BaseQuartzJobBean {
	@Autowired
	private TmcScheduleService tmcScheduleService;
	@Autowired
	private TmcDeleteOldSms tmcDeleteOldSms;
	@Autowired
	private ShortLinkService shortLinkService;
	@Autowired
	private MobileSettingService mobileSettingService;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(MyScheduleSendMessage.class);
	/**
	 * quart定时任务需要的方法     暂不使用
	 */
	@Override
	protected String executeInternalBiz(JobExecutionContext context) {
		return null;
	}
	/**
	 * spring task 定时任务
	 */
	public void runJob() {
		this.logger.info("***************************每分钟发送短信定时任务开始执行了********************************");
		SimpleDateFormat fomartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String startSendTime = fomartDate.format(new Date());
        Date endDate = null;
        try {
            endDate = fomartDate.parse(startSendTime);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.MINUTE, -1);
        Date startDate = cal.getTime();
//				sendMessageService.checkAndSend(new Date());
		tmcScheduleService.doHandle(startDate,endDate);
	}
	/**
	 * spring task 定时任务  每天凌晨执行  删除超过两条的订单中心的短信
	 */
	public void removeOldSms(){
		this.logger.info("*********************删除过期的订单中心短信******************************");
		this.tmcDeleteOldSms.doHandle();
	}
	
	
	
	/** 
	* @Description: 检索后台管理设置,实现设置的功能<br/>
	* 催付效果，短信发送量和当前短信条数(每天9点发送前一天数据)<br/> 
	* 软件过期提醒<br/> 
	* 最新促销活动通知(暂时没有该功能)<br/> 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月28日 下午12:17:46
	*/
	public void scanMobileSettingTable(){
		this.logger.info("*********************/短信当前余额/昨日短信发送量/催付发送量/软件过期提醒/******************************");
		this.mobileSettingService.scanMobileSettingTable();
	}
	
	/** 
	* @Description: 定时 联表查询用户的后台管理设置,用户表和用户余额表:<br/>
	* 1,用户不能过期(SQL已判断)<br/>
	* 2,余额不足提醒开启;且设置提醒条数大于实际余额(SQL已判断)<br/>
	* 3,满足余额提醒发送提醒短信,持续一个星期
	* 4,定时>>每天8点  ~ 晚上9点 每隔一个小时
	* @param     设定文件 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月28日 下午4:55:47
	*/
	public void scanUserSmsCountRemind(){
		this.logger.info("*********************/短信余额不足提醒/******************************");
		this.mobileSettingService.scanUserSmsCountRemind();
	}
	
	
	/** 
	* @Description: 每天夜晚12点,定时将今天已发送的后管理管理标记清除
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月29日 上午10:57:48
	*/
	public void scheduleResetFlag() {
		this.logger.info("*********************/每天夜晚12点,重置余额不足提醒标记/******************************");
		this.mobileSettingService.scheduleResetFlag();
	}
	
	
	/** 
	* @Description: 定时每隔一个小时,将过期的用户的余额不足提醒 起始时间,结束时间置空
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年12月5日 下午3:46:56
	*/
	public void scheduleResetSmsmRemider() {
		this.logger.info("*********************/每隔1个小时,重置过期用户余额不足提醒标记/******************************");
		this.mobileSettingService.scheduleResetSmsmRemider();
	}
}
