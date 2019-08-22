package s2jh.biz.shop.crm.schedule.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import com.taobao.api.internal.util.NamedThreadFactory;

public class TransactionThreadPool {
	private  static int threadCount = 100;
	private TransactionThreadPool(){};
	//private final static ExecutorService tradeThreadPool = Executors.newFixedThreadPool(100);  
	private final static ExecutorService tradeThreadPool = new ThreadPoolExecutor(20, threadCount, 15 * 2, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(2000), new NamedThreadFactory("tradeData-thread-worker"), new AbortPolicy());
	/**
	 * 取得固定大小的线程池对象，不可在其余地方创建新的线程池对象，避免内存溢出
	 * @return
	 */
	public static ExecutorService  getTradeThreadPool(){
		return tradeThreadPool; 
	}
}
