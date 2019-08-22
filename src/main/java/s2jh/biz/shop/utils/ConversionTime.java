/**
 * 
 */
package s2jh.biz.shop.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Title:
 * @date 2017年1月13日--下午2:20:10
 * @param 设定文件
 * @return 返回类型
 * @throws
 */
public class ConversionTime {

	/**
	 * 创建人：邱洋
	 * 
	 * @Title: 将日期转换成定时任务的格式
	 * @date 2017年1月13日--下午2:42:24
	 * @return String
	 * @throws
	 */
	public static String conversionTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH);// 获取月份
		int day = cal.get(Calendar.DATE);// 获取日
		int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
		int minute = cal.get(Calendar.MINUTE);// 分
		int second = cal.get(Calendar.SECOND);// 秒
		String time = second + " " + minute + " " + hour + " " + day + " "
				+ (month + 1) + " " + "?" + " " + year;
		return time;
	}
	/**
	 * 创建:薛园园
	 * 
	 * @Title: 将日期转换成定时任务的格式,只转每天的时间
	 * @return String
	 * @throws
	 */
	public static String conversionDayTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
		int minute = cal.get(Calendar.MINUTE);// 分
		int second = cal.get(Calendar.SECOND);// 秒
		String time = second + " " + minute + " " + hour + " * * ?";
		return time;
	}
}
