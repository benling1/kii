package s2jh.biz.shop.crm.message.service.processor;

import java.util.concurrent.Callable;

import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.support.service.MultithreadBatchSmsService;
import s2jh.biz.shop.utils.SpringContextUtil;

public class SendAble implements Callable<String> {

	private BatchSmsData bData;
	

	public void setbData(BatchSmsData bData) {
		this.bData = bData;
	}

	@SuppressWarnings("unused")
	@Override
	public String call() throws Exception {
		MultithreadBatchSmsService multithreadService = SpringContextUtil.getBean("multithreadBatchSmsService");
		multithreadService.batchOperateSms(bData);
		int sNum = bData.getSuccess();
		if(sNum ==0){
			return "success";
		}else{
			return "failed";
		}
	}

}
