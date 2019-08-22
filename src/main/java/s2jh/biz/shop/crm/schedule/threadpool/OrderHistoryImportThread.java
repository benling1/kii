package s2jh.biz.shop.crm.schedule.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderHistoryImportThread {
	private OrderHistoryImportThread(){};
	private final static ExecutorService tradeThreadPool = Executors.newFixedThreadPool(100);  
	/**
	 * 取得固定大小的线程池对象，不可在其余地方创建新的线程池对象，避免内存溢出
	 * @return
	 */
	public static ExecutorService  getTradeThreadPool(){
		return tradeThreadPool; 
	}
}
