/** 
 * Project Name:s2jh4net 
 * File Name:TmcThreadUtil.java 
 * Package Name:s2jh.biz.shop.crm.taobao.util 
 * Date:2017年4月6日上午10:32:02 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
  
package s2jh.biz.shop.crm.taobao.util;  
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.internal.tmc.Message;

import s2jh.biz.shop.crm.taobao.GetTaobaoMSG;
import s2jh.biz.shop.crm.tmc.manage.TmcDistributeService;
import s2jh.biz.shop.crm.tmc.taobao.ObtainTaoBaoTmc;
/** 
 * ClassName:TmcThreadUtil <br/> 
 * Date:     2017年4月6日 上午10:32:02 <br/> 
 * @author   zlp
 * @version   1.0     
 */
@Component
public class TmcThreadUtil {
		public static boolean tmcClock = true;
//		@Autowired
//		private  TBMessageManage taoBaoMessageManage;
		@Autowired
		private TmcDistributeService tmcDistributeService;
		public  static  BlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(ObtainTaoBaoTmc.size);
		
		public  static  BlockingQueue<String> queueTid = new ArrayBlockingQueue<String>(ObtainTaoBaoTmc.size);

		private static final Log logger = LogFactory.getLog(TmcThreadUtil.class);
	
		//回调处理链条
	    interface DataHandler{
	    	void doHandler(Message message);
	    }
	    
	    //error处理链条
	    interface ErrorHandler{
	    	void doHandler(Throwable t);
	        public static final ErrorHandler PRINTER = new ErrorHandler() {
	            public void doHandler(Throwable t) {
	                t.printStackTrace();
	                logger.error("tmc处理线程异常");
	            }
	        };
	    }
	    
	    /**
	     * 数据处理器
	     */
	    class TMCDataProcessor{
	        private ErrorHandler errorHandler = ErrorHandler.PRINTER;
	        /**
	         * 数据处理线程
	         */
	        private Thread[] proccessors;
	        public TMCDataProcessor() {
	            //默认创建处理的线程数，与CPU处理的内核数相同
	            proccessors = new Thread[50];
	            Runnable worker = new Runnable() {
	                public void run() {
	                	for(;;){
		                    try {
	                    		Message message =  queue.take();
	                    		logger.debug("主题："+message.getTopic()+"，内容："+message.getContent()+"从队列取走一个元素，队列剩余"+(ObtainTaoBaoTmc.size-queue.size())+"个元素");
	                    		if(null!=message){
	                    			tmcDistributeService.sendMessageToQueue(message);
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
	    
	    
	public void handleTMCData() {
	    //回调函数处理 
		 TMCDataProcessor processor = new TMCDataProcessor();
		 processor.start();
		 logger.info("批处理线程处理完毕！");
		 
	}
}
	  
