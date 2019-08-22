/** 
 * Project Name:s2jh4net 
 * Package Name:s2jh.biz.shop.crm.taobao.util 
 * Date:2017年4月6日上午10:32:02 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
  
package s2jh.biz.shop.crm.manage.service;  
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.taobao.GetTaobaoMSG;
/** 
 * ClassName:TmcThreadUtil <br/> 
 * Date:     2017年4月6日 上午10:32:02 <br/> 
 * @author   zlp
 * @version   1.0     
 */
@Component
public class SyncHistoryTradeQueueService {
		
		public    static BlockingQueue<List<TradeDTO>> queue = new ArrayBlockingQueue<List<TradeDTO>>(GetTaobaoMSG.MaxSize);
		@Autowired
		private SynchronousTradeHelper  synchronousTradeHelper;  
		
		private static final Log logger = LogFactory.getLog(SyncHistoryTradeQueueService.class);
		
		public static long count = 0l;
	
		//回调处理链条
	    interface DataHandler{
	    	void doHandler(List<TradeDTO> message);
	    }
	    
	    //error处理链条
	    interface ErrorHandler{
	    	void doHandler(Throwable t);
	        public static final ErrorHandler PRINTER = new ErrorHandler() {
	            public void doHandler(Throwable t) {
	                t.printStackTrace();
	                logger.error("SyncHistoryTradeQueueService处理线程异常");
	            }
	        };
	    }
	    
	    /**
	     * 数据处理器
	     */
	    class SyncHistoryTradeQueueServiceDataProcessor{
	        private ErrorHandler errorHandler = ErrorHandler.PRINTER;
	        /**
	         * 数据处理线程
	         */
	        private Thread[] proccessors;
	        public SyncHistoryTradeQueueServiceDataProcessor() {
	            //默认创建处理的线程数，与CPU处理的内核数相同
	            proccessors = new Thread[40];
	            Runnable worker = new Runnable() {
	                public void run() {
	                	for(;;){
		                    try {
		                    	List<TradeDTO> tradeList =  queue.take();
	                    		if(null!=tradeList&&tradeList.size()>0){
	                    			count++;
	                    			if(count%100==0){
	                    				logger.info("------------------------------------从SyncHistoryTradeQueueService队列取走一个元素，元素大小"+tradeList.size()+"队列剩余"+(GetTaobaoMSG.MaxSize-queue.size())+"个元素 ");
	                    				count=0l; 
	                    			}
//	                    			synchronousTradeHelper.handleHistoryTradeData(tradeList);
		                    	 }
		                    } catch (Throwable t) {
		                    	 logger.info(333);
		                        errorHandler.doHandler(t);
		                    }
	                	}
	                }
	            };
	            for(int i=0;i<proccessors.length;i++){
	                proccessors[i] = new Thread(worker,"proccessor-thread_"+i);
	            }
	        }
	        public void setErrorHandler(ErrorHandler errorHandler) {
	            this.errorHandler = errorHandler;
	        }

	       
	        /**
	         * 开启处理过程
	         */
	        public synchronized void start(){
	            for(int i=0;i<proccessors.length;i++){
	                proccessors[i].start();
	            }
	        }
	    }
	    
	    
	public void handleTMCData() {
	    //回调函数处理 
		 SyncHistoryTradeQueueServiceDataProcessor processor = new SyncHistoryTradeQueueServiceDataProcessor();
		 processor.start();
		 logger.info("SyncHistoryTradeQueueService批处理线程处理完毕！");
		 
	}
}
	  
