package s2jh.biz.shop.crm.buyers.queue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.schedule.threadpool.MemberInfoThreadPool;



/** 
* @ClassName: MemberProcessor 
* @Description: TODO(会员数据处理队列) 
* @author jackstraw_yu 
* @date 2017年4月12日 下午4:41:53 
*  
*/
@Component
public class MemberInfoQueue {
	
//	//填充数据--批量处理
//	//public  static  BlockingQueue<List<MemberInfo>> queue = new ArrayBlockingQueue<List<MemberInfo>>(50000);
//	//填充数据--单条处理
//	public  static  BlockingQueue<MemberInfo> queue = new ArrayBlockingQueue<MemberInfo>(900000);
//	
//	@Autowired
//	private MemberInfoService memberInfoService;
//	
//	
//	// 处理数据
//	class Processor{
//        /**
//         * 数据处理线程
//         */
//        private Thread[] processors;
//        public Processor() {
//            processors = new Thread[100];
//            Runnable worker = new Runnable() {
//                public void run() {
//                	while(true){
//	                    try {
//	                    	//List<MemberInfo> members =  queue.take();
//	                    	MemberInfo member =  queue.take();
//                            if(null!=member){
//                            	//开始处理
//                            	//memberInfoService.processMemberInfosBatch(members);
//                            	memberInfoService.processMemberInfo(member);
//                            }
//	                    } catch (Exception e) {
//	                       e.printStackTrace();
//	                    }
//                	}
//                }
//            };
//            for(int i=0;i<processors.length;i++){
//                processors[i] = new Thread(worker,"memberInfo-thread_"+i);
//            }
//        }
//        /**
//         * 开启处理过程
//         */
//        public synchronized void start(){
//            for(int i=0;i<processors.length;i++){
//            	//processors[i].start();
//            processors[i].start();
//            }
//        }
//    }
//
//	public void startProcess() {
//	    //回调函数处理 
//		 Processor processor = new Processor();
//		 processor.start();
//	}
//	
	
}
