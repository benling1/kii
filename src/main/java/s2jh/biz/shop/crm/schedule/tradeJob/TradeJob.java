package s2jh.biz.shop.crm.schedule.tradeJob;


import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.write.DateFormat;
import net.sf.json.JSONObject;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.order.service.TransactionTradeService;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.module.schedule.BaseQuartzJobBean;
@MetaData("执行定时任务，读取.............")
public class TradeJob extends BaseQuartzJobBean{
	
//	@Autowired
//	private TransactionTradeService transactionTradeService;
	
	@Override
	protected String executeInternalBiz(JobExecutionContext context){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");
	
//		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&开始保存%%%%%%%%%%%%%%%%%%%%"+sdf.format(new Date()));
		
//		transactionTradeService.saveTradeToLocalityEnhance();
//		transactionOrderService.saveTradeToLocalityEnhance();
//		transactionTradeService.saveTradeToLocalityEn();
//		transactionTradeService.timeLoadTbTradeData();
		//获取创建淘宝交易消息并存储到本地的tid列表中
		//测试使用
		/*JSONObject parseJSON = JSONObject.fromObject(
				"{\"buyer_nick\":\"hello\",\"oid\":1234567890,\"payment\":12.5,\"seller_nick\":\"hi\",\"status\":\"taobao_trade_TradeCreate\",\"tid\":\"13423234234234234\",\"type\":\"auction(拍卖)\"}");
		transactionOrderService.saveTid(parseJSON );*/
		
//		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&结束保存%%%%%%%%%%%%%%%%%%%%"+sdf.format(new Date()));
		return null;
	}

	

}
