/** 
 * Project Name:s2jh4net 
 * Date:2017年4月6日上午10:32:02 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
  
package s2jh.biz.shop.crm.manage.service;  
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import lab.s2jh.core.handler.impl.DefaultHandlerChain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 * @author   zlp
 * @version   1.0     
 */
@Component
public class LogAccessQueueService {
	
	public  static   BlockingQueue<Map<String, Object>> queue = new ArrayBlockingQueue<Map<String, Object>>(100000);
	
	@Autowired
	private DefaultHandlerChain  logAccessHandlerChain;  
	
	private static final Log logger = LogFactory.getLog(LogAccessQueueService.class);

	//回调处理链条
    interface DataHandler{
    	void doHandler(Map<String, Object> messageList);
    }
    
    //error处理链条
    interface ErrorHandler{
    	void doHandler(Throwable t);
        public static final ErrorHandler PRINTER = new ErrorHandler() {
            public void doHandler(Throwable t) {
                t.printStackTrace();
                logger.error("logAccess处理线程异常");
            }
        };
    }
    
    /**
     * 数据处理器
     */
    class LogAccessDataProcessor{
        private ErrorHandler errorHandler = ErrorHandler.PRINTER;
        /**
         * 数据处理线程
         */
        private Thread[] proccessors;
        public LogAccessDataProcessor() {
            //默认创建处理的线程数，与CPU处理的内核数相同
            proccessors = new Thread[20];
            Runnable worker = new Runnable() {
                public void run() {
                	for(;;){
	                    try {
	                    	Map<String, Object> map  =  queue.take();
                    		if(null!=map&&map.size()>0){
                    			System.out.println("从logAccess队列取走一个元素，队列剩余"+(100000-queue.size())+"个元素");
                    			logAccessHandlerChain.doHandle(map);
                    		}else{
                    			 logger.info("logAccess队列为空！");
                    		}
	                    } catch (Throwable t) {
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
	    
	    
	public void handleLogAccessData() {
	    //回调函数处理 
		 LogAccessDataProcessor processor = new LogAccessDataProcessor();
		 processor.start();
		 logger.info("logAccess批处理线程处理完毕！");
	}
}
	  
