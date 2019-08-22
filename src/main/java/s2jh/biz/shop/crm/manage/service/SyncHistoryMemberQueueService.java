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

import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.taobao.GetTaobaoMSG;
/** 
 * ClassName:TmcThreadUtil <br/> 
 * Date:     2017年4月6日 上午10:32:02 <br/> 
 * @author   zlp
 * @version   1.0     
 */
@Component
public class SyncHistoryMemberQueueService {
		
		public    static BlockingQueue<List<MemberDTO>> queue = new ArrayBlockingQueue<List<MemberDTO>>(GetTaobaoMSG.MaxSize);
		@Autowired
		private SynchronousTradeHelper  synchronousTradeHelper;  
		
		private static final Log logger = LogFactory.getLog(SyncHistoryMemberQueueService.class);
		
		public static long count = 0l;
	
		//回调处理链条
	    interface DataHandler{
	    	void doHandler(List<MemberDTO> message);
	    }
	    
	    //error处理链条
	    interface ErrorHandler{
	    	void doHandler(Throwable t);
	        public static final ErrorHandler PRINTER = new ErrorHandler() {
	            public void doHandler(Throwable t) {
	                t.printStackTrace();
	                logger.error("SyncHistoryMemberQueueService处理线程异常");
	            }
	        };
	    }
	    
	    /**
	     * 数据处理器
	     */
	    class SyncHistoryMemberQueueServiceDataProcessor{
	        private ErrorHandler errorHandler = ErrorHandler.PRINTER;
	        /**
	         * 数据处理线程
	         */
	        private Thread[] proccessors;
	        public SyncHistoryMemberQueueServiceDataProcessor() {
	            //默认创建处理的线程数，与CPU处理的内核数相同
	            proccessors = new Thread[40];
	            Runnable worker = new Runnable() {
	                public void run() {
	                	for(;;){
		                    try {
		                    	List<MemberDTO> memberList =  queue.take();
	                    		if(null!=memberList&&memberList.size()>0){
	                    			count++;
	                    			if(count%100==0){
	                    				logger.info("------------------------------------从SyncHistoryMemberQueueService队列取走一个元素，元素大小"+memberList.size()+"队列剩余"+(GetTaobaoMSG.MaxSize-queue.size())+"个元素 ");
	                    				count=0l; 
	                    			}
//	                    			synchronousTradeHelper.handleHistoryMemberData(memberList);
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
		 SyncHistoryMemberQueueServiceDataProcessor processor = new SyncHistoryMemberQueueServiceDataProcessor();
		 processor.start();
		 logger.info("SyncHistoryMemberQueueService批处理线程处理完毕！");
		 
	}
}
	  
