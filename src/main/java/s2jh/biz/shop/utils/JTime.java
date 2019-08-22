package s2jh.biz.shop.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

public class JTime {
	public static Map<String, Object> timekey = new HashMap<String, Object>();//time主属性用于存放需要保存的字段
	private static Map<String, Long> keytime = new HashMap<String, Long>();// time主属性用于存放需要保存的字段
	private static final long EXPIRATIONTIME = 1000 * 60 * 120;//两小时
	// private static final long EXPIRATIONTIME=1000*20;//测试用20秒
	private static final int START = 0;//设置执行开始时间
	private static final int INTERVAL = 1000 * 60 * 10;//设置间隔执行时间 单位/毫秒

	public static void put(String key, Object vale) {
		timekey.put(key, vale);
		keytime.put(key, new Date().getTime());
	}

	public static Object get(String key) {
		return timekey.get(key);
	}

	static {
		Timer tt = new Timer();// 定时类
		tt.schedule(new TimerTask() {// 创建一个定时任务
					public void run() {
						long nd = new Date().getTime();// 获取系统时间
						Iterator<Entry<String, Long>> entries = keytime.entrySet().iterator();
						while (entries.hasNext()) {
							Map.Entry<String, Object> entry = (Map.Entry) entries.next();
							String key = (String) entry.getKey();//获取key
							long value = (Long) entry.getValue();//获取value
							long rt = nd - value;//获取当前时间跟存入时间的差值
							if (rt > EXPIRATIONTIME) {//判断时间是否已经过期 如果过期则清楚key;否则不做处理
								timekey.put(key, null);
								entries.remove();
								System.out.println("Key:" + key + "已过期  清空");
							}
						}
					}
				}, START, INTERVAL);// 从0秒开始，每隔10分钟执行一次
	}
}
