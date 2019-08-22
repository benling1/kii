/** 
 * Project Name:s2jh4net 
 * Package Name:s2jh.biz.shop.crm.taobao.util 
 * Date:2017年4月6日上午10:32:02 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
  
package s2jh.biz.shop.crm.manage.service;  
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.order.service.EffectPictureService;
import s2jh.biz.shop.crm.taobao.GetTaobaoMSG;
/** 
 * ClassName:TmcThreadUtil <br/> 
 * Date:     2017年4月6日 上午10:32:02 <br/> 
 * @author   zlp
 * @version   1.0     
 */
@Component
public class SyncEffectQueueService {
		
		public    static BlockingQueue<Map<String,Object>> queue = new ArrayBlockingQueue<Map<String,Object>>(GetTaobaoMSG.MaxSize);
		
		
		@Autowired
		private EffectPictureService  effectPictureService;  
		private static final Log logger = LogFactory.getLog(SyncEffectQueueService.class);
		
		public static long count = 0l;
	
		//回调处理链条
	    interface DataHandler{
	    	void doHandler(Map<String,Object> message);
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
	    class SyncEffectQueueServiceDataProcessor{
	        private ErrorHandler errorHandler = ErrorHandler.PRINTER;
	        /**
	         * 数据处理线程
	         */
	        private Thread[] proccessors;
	        public SyncEffectQueueServiceDataProcessor() {
	            //默认创建处理的线程数，与CPU处理的内核数相同
	            proccessors = new Thread[40];
	            Runnable worker = new Runnable() {
	                public void run() {
	                	for(;;){
		                    try {
		                    	Map<String,Object>  map =  queue.take();
	                    		if(null!=map&&map.size()>0){
	                    			logger.info("------------------------------------从SyncHistoryTradeQueueService队列取走一个元素队列剩余"+(GetTaobaoMSG.MaxSize-queue.size())+"个元素 ");	                    				count=0l; 
	                    			effectPictureService.handleEffectData(map);
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
		SyncEffectQueueServiceDataProcessor processor = new SyncEffectQueueServiceDataProcessor();
		 processor.start();
		 logger.info("SyncEffectQueueServiceDataProcessor批处理线程处理完毕！");
		 
	}
}
	  
