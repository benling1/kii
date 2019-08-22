package s2jh.biz.shop.crm.taobao.service;

import java.util.Date;
@Deprecated 
public interface ScheduleSendMessageService {
	/**
	 * 定时任务检查筛选短信 并发送
	 */
	public void checkAndSend(Date nowDate);
	/**
	 * 服务器启动后，检测一小时之前未发送的短信，扫描出来后并发送
	 */
	public void sendOneHourSms();
}
