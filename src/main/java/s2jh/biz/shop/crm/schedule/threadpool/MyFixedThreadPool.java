package s2jh.biz.shop.crm.schedule.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import com.taobao.api.internal.util.NamedThreadFactory;

public class MyFixedThreadPool {
	private  static int threadCount = 500;
	private MyFixedThreadPool(){};
	private final static ExecutorService fixedThreadPool = new ThreadPoolExecutor(100, threadCount, 15 * 2, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(100000), new NamedThreadFactory("crm-thread-worker"), new AbortPolicy());
	
	private final static ExecutorService loginThreadPool = new ThreadPoolExecutor(50, 200, 15 * 2, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(100000), new NamedThreadFactory("crm-thread-worker"), new AbortPolicy());
	
//	private final static ExecutorService smsScheduleThreadPool = new ThreadPoolExecutor(100, threadCount, 15 * 2, TimeUnit.SECONDS,
//	        new ArrayBlockingQueue<Runnable>(100000), new NamedThreadFactory("crm-sms-thread"), new AbortPolicy());
//	private final static ExecutorService USER_THREAD_POOL = new ThreadPoolExecutor(1, 50, 15 * 2, TimeUnit.SECONDS,
//            new ArrayBlockingQueue<Runnable>(5000), new NamedThreadFactory("crm-user-thread"), new AbortPolicy());
	/**
	 * 取得固定大小的线程池对象，不可在其余地方创建新的线程池对象，避免内存溢出
	 * @return
	 */
	public static ExecutorService  getMyFixedThreadPool(){
		return fixedThreadPool; 
	}
	public static ExecutorService  getLoginThreadPool(){
		return loginThreadPool; 
	}
	/**
     * 取得用户登陆操作专用的线程池
     * @return
     */
    public static ExecutorService  getUserInfoThreadPool(){
//        return USER_THREAD_POOL; 
        return fixedThreadPool; 
    }
    /**
     * 定时短信专享线程池
     * @return
     */
    public static ExecutorService  getSmsScheduleThreadPool(){
//        return smsScheduleThreadPool; 
        return fixedThreadPool; 
    }
	public static int  getActiveThreadCount(){
		return ((ThreadPoolExecutor)fixedThreadPool).getActiveCount(); 
	}
	public static int  getRestThreadCount(){
		return threadCount-((ThreadPoolExecutor)fixedThreadPool).getActiveCount(); 
	}
	
}
