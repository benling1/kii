package s2jh.biz.shop.crm.order.thread;

import s2jh.biz.shop.crm.manage.entity.LogAccessDTO;
import s2jh.biz.shop.crm.manage.vo.SendMsgVo;
import s2jh.biz.shop.crm.message.service.OrderMsgSendService;

public class TradeSendMessageThread implements Runnable{
	
	private OrderMsgSendService orderMsgSendService;
	private SendMsgVo sendMsgVo;
	private LogAccessDTO logAccessDTO;
	
	public TradeSendMessageThread(OrderMsgSendService orderMsgSendService,SendMsgVo sendMsgVo ,LogAccessDTO logAccessDTO){
		this.orderMsgSendService = orderMsgSendService;
		this.sendMsgVo = sendMsgVo;
		this.logAccessDTO = logAccessDTO;
	}
	
	public void run() {
		try {
			orderMsgSendService.batchSendOrderMsg(sendMsgVo,logAccessDTO);
			System.out.println("~~~~~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@thread");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
