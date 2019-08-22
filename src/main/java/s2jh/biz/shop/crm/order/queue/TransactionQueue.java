package s2jh.biz.shop.crm.order.queue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
import s2jh.biz.shop.crm.order.entity.TransactionOrder;
import s2jh.biz.shop.crm.order.pojo.TbTransactionOrder;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.schedule.threadpool.TransactionThreadPool;


/** 
* @ClassName: TransactionQueue 
* @Description: TODO(队列处理订单数据) 
* @author jackstraw_yu 
* @date 2017年4月14日 下午1:52:30 
*  
*/
@Component
public class TransactionQueue {

//	//队列用于存放淘宝订单集合
//	public  static  BlockingQueue<List<TbTransactionOrder>> tradeQueue = new ArrayBlockingQueue<List<TbTransactionOrder>>(9000);
//	
//	
//	@Autowired
//	private TransactionOrderService transactionOrderService;
//	
//	//内部类,监控tradeQueue内是否存在数据
//	class TransactionProcessor{
//		Thread[] processors;
//		public TransactionProcessor(){
//			//开启50个线程获取队列中的数据并处理
//			processors = new Thread[50];
//			Runnable worker = new Runnable() {
//                public void run() {
//                	while(true){
//	                    try {
//	                    	List<TbTransactionOrder> tbTrades =  tradeQueue.take();
//                            if(null!=tbTrades){
//                            	//开始处理
//                            	transactionOrderService.loadConvertTbTradeData(tbTrades);
//                            }
//	                    } catch (Exception e) {
//	                       e.printStackTrace();
//	                    }
//                	}
//                }
//            };
//            for (int i = 0; i < processors.length; i++) {
//            	processors[i] = new Thread(worker,"trade-thread_"+i);
//			}
//		}
//		
//		/**
//         * 开启处理过程
//         */
//        public synchronized void start(){
//            for(int i=0;i<processors.length;i++){
//            	processors[i].start();
//            }
//        }
//	}
//	
//	public void startProcess() {
//	    //回调函数处理 
//		TransactionProcessor processor = new TransactionProcessor();
//		 processor.start();
//	}
//	
}
