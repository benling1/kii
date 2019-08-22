package s2jh.biz.shop.crm.marketingCenter.service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.message.service.SmsBlackListService;


@Component
public class ProcessUpdateMemberInfoStatusQueue {
//
//	//队列用于存放淘宝订单集合
//		public  static  BlockingQueue<Map<String,Object>> smsBlackQueue = new ArrayBlockingQueue<Map<String,Object>>(10000);
//		
//		
//		
//		@Autowired
//		private MemberInfoService memberInfoService;
//		
//		//内部类,监控tradeQueue内是否存在数据
//		public class UpdateMemberInfoStatusProcessor{
//			Thread[] processors;
//			//volatile int s =1;
//			public UpdateMemberInfoStatusProcessor(){
//				//开启50个线程获取队列中的数据并处理
//				processors = new Thread[3];
//				Runnable worker = new Runnable() {
//	                public void run() {
//	                	while(true){
//		                    try {
//		                    	Map<String,Object> map =  smsBlackQueue.take();
//	                            if(null!=map && !map.isEmpty()){
//                            		//开始修改会员状态
//                            		memberInfoService.processDatas(map);
//	                            }
//		                    } catch (Exception e) {
//		                       e.printStackTrace();
//		                    }
//	                	}
//	                }
//	            };
//	            for (int i = 0; i < processors.length; i++) {
//	            	processors[i] = new Thread(worker,"trade-thread_"+i);
//				}
//			}
//			
//			/**
//	         * 开启处理过程
//	         */
//	        public synchronized void start(){
//	            for(int i=0;i<processors.length;i++){
//	            	processors[i].start();
//	            }
//	        }
//		}
//		
//		public UpdateMemberInfoStatusProcessor startProcess() {
//		    //回调函数处理 
//			UpdateMemberInfoStatusProcessor processor = new UpdateMemberInfoStatusProcessor();
//			 //processor.start();
//			return processor;
//		}
//	
//		
//		public void killProcess(UpdateMemberInfoStatusProcessor processor){
//			while(true){
//				try {
//					if(smsBlackQueue.size()==0){
//						Thread.sleep(1000);
//						processor = null;
//						break;
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
}
