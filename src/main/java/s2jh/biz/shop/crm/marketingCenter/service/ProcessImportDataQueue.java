package s2jh.biz.shop.crm.marketingCenter.service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.message.service.SmsBlackListService;


@Component
public class ProcessImportDataQueue {

	//队列用于存放淘宝订单集合
	public  static  BlockingQueue<Map<String,Object>> smsBlackQueue = new ArrayBlockingQueue<Map<String,Object>>(10000);
		
	@Autowired
	private VipMemberService vipMemberService;
	@Autowired
	private SmsBlackListService smsBlacklistService;
		
	//内部类,监控smsBlackQueue内是否存在数据
	public class SmsBlackProcessor{
		Thread[] processors;
		volatile Boolean flag = true;
		public SmsBlackProcessor(){
			//开启3个线程获取队列中的数据并处理
			processors = new Thread[3];
			Runnable worker = new Runnable() {
                public void run() {
                	while(flag){
	                    try {
	                    	Map<String,Object> map =  smsBlackQueue.take();
                            if(null!=map && !map.isEmpty()){
                            	//开始处理
                            	//1向黑名单表插入数据
                            	smsBlacklistService.processDatas(map);
                            	//2更新会员
                            	vipMemberService.updateMemberBlackAll(map);
                            }
	                    } catch (Exception e) {
	                       e.printStackTrace();
	                    }
                	}
                }
            };
            for (int i = 0; i < processors.length; i++) {
            	processors[i] = new Thread(worker,"process_importData_thread"+i);
			}
		}
		
		/**
         * 开启处理过程
         */
        public synchronized void start(){
            for(int i=0;i<processors.length;i++){
            	processors[i].start();
            }
        }

        /**
         * 将标记复制
         * */
		public void setFlag(Boolean flag) {
			this.flag = flag;
		}
        
        
	}
		
	public SmsBlackProcessor startProcess() {
	    //回调函数处理 
		SmsBlackProcessor processor = new SmsBlackProcessor();
		return processor;
	}

	
	public void killProcess(SmsBlackProcessor processor){
		while(true){
			try {
				if(smsBlackQueue.size()==0){
					Thread.sleep(1000);
					processor.setFlag(false);
					processor = null;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
