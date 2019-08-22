package s2jh.biz.shop.crm.taobao.service.judgment.logistics.delay;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.taobao.api.SecretException;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.schedule.job.auto.AutoDelayRemindScheduleJob;
import s2jh.biz.shop.crm.schedule.triggers.TriggerManager;
import s2jh.biz.shop.crm.taobao.info.ScheduleJobNameInfo;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;
import s2jh.biz.shop.utils.ConversionTime;
/**
 * 延迟发货提醒的主方法
 * @author Administrator
 *
 */
@Component
@Deprecated 
public class JudgeDelayRemindMainUtil {


	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private JudgeDelayRemindUserAccountUtil judgeDelayRemindUserAccountUtil;
	@Autowired
	private TriggerManager triggerManager;
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;

	@Autowired
	private CacheService cacheService;
	
	public void delayRemind(String buttonId, String orderSetupId) {
		Long id = Long.parseLong(orderSetupId);
		//根据id查询出orderSetup
		OrderSetup orderSetup = myBatisDao.findBy(OrderSetup.class.getName(), "findById", id);
		//拿到卖家用户名
		String userId = orderSetup.getUserId();
		//判断是否开启定时延迟发货提醒
		//如果开启
		if (buttonId.equals("0")) {
			//判断是否是立即执行
			//获取到卖家设置的订单范围
			String orderScopeOne = orderSetup.getOrderScopeOne();
			String orderScopeTwo = orderSetup.getOrderScopeTwo();
			//查询map
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orderScopeOne", orderScopeOne);
			map.put("orderScopeTwo", orderScopeTwo);
			map.put("sellerName", userId);
			map.put("settingType", "11");//11延时发货
			map.put("tmc", 1);
			//用户检测
			map = judgeDelayRemindUserAccountUtil.judgeDelayRemindUserAccount(map);
			//如果判断通过
			if ((Boolean)map.get("flag")) {
				//拿到执行类型
				String executeGenre = orderSetup.getExecuteGenre();
				//如果是立即执行
				if (executeGenre.equals("0")) {
					@SuppressWarnings("unchecked")
					List<SmsSendInfo> smsInfoList = (List<SmsSendInfo>) map.get("smsInfoList");
					//遍历
					for (SmsSendInfo smsSendInfo : smsInfoList) {
						try {
							String token = cacheService.getJsonStr(
									RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE,
									RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY
											+ smsSendInfo.getUserId());
							if(null!=smsSendInfo.getPhone()&&EncrptAndDecryptClient.isEncryptData(smsSendInfo.getPhone(), EncrptAndDecryptClient.PHONE)){
								String phone = EncrptAndDecryptClient.getInstance().decrypt(smsSendInfo.getPhone(), EncrptAndDecryptClient.PHONE, token);
								smsSendInfo.setPhone(phone);
							}
							if(smsSendInfo.getNickname()!=null&&EncrptAndDecryptClient.isEncryptData(smsSendInfo.getNickname(), EncrptAndDecryptClient.SEARCH)){
								String nickname = EncrptAndDecryptClient.getInstance().decrypt(smsSendInfo.getNickname(), EncrptAndDecryptClient.SEARCH, token);
								smsSendInfo.setNickname(nickname);
							}
						} catch (SecretException e) {
							e.printStackTrace();
						}
						map.put("smsInfo", smsSendInfo);
						//直接发送，并且发送一次
						taoBaoSendMessageUtil.sendSingleMessage(map);
						
					}
				}else if(executeGenre.equals("1")){
					//如果执行周期是每天。添加定时任务
					Date lastModifiedTime = orderSetup.getLastModifiedDate();
					String time = ConversionTime.conversionDayTime(lastModifiedTime);
//					String time = "42 52 9 * * ?";//测试数据
					String jobName = ScheduleJobNameInfo.AUTO_DELAY_REMIND+"_"+userId;
					WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
			        ServletContext servletContext = webApplicationContext.getServletContext();  
			        ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
					TriggerManager.addJob(jobName, ScheduleJobNameInfo.AUTO_DELAY_REMIND, time, AutoDelayRemindScheduleJob.class, jobName,application);
					Scheduler scheduler = null;
					try {
						//添加并启动
						scheduler = StdSchedulerFactory.getDefaultScheduler();
//						scheduler = stdSchedulerFactory.getScheduler();
						scheduler.start();
					} catch (SchedulerException e) {
						e.printStackTrace();
					}
				}
				
			}
			
			
		//如果关闭
		}else if(buttonId.equals("1")){
			//删除定时任务
			String jobName = ScheduleJobNameInfo.AUTO_DELAY_REMIND+"_"+userId;
			TriggerManager.delJob(jobName, ScheduleJobNameInfo.AUTO_DELAY_REMIND);
		}
	}
	
	
}
