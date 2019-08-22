package s2jh.biz.shop.crm.order.thread;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import s2jh.biz.shop.crm.order.pojo.TbTransactionOrder;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.utils.SpringContextUtil;

public class TradeThread extends Thread {

//
//	private List<TbTransactionOrder> rspList = new CopyOnWriteArrayList<TbTransactionOrder>();
//
//	public List<TbTransactionOrder> getRspList() {
//		return rspList;
//	}
//
//	public void setRspList(List<TbTransactionOrder> rspList) {
//		this.rspList = rspList;
//	}
//
//	public void appendRspList(List<TbTransactionOrder> rspList) {
//		this.rspList.addAll(rspList);
//	}
//
//	@Override
//	public void run() {
//		TransactionOrderService transactionOrderService = SpringContextUtil.getBean("transactionOrderService");
//		transactionOrderService.loadConvertTbTradeData(rspList);
//		
//
//	}

}
