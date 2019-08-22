package s2jh.biz.shop.crm.order.thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.order.pojo.EffectPictureReal;
import s2jh.biz.shop.utils.TradeStatusUtils;

public class EffectBuyerRealCountThread implements Callable<EffectPictureReal> {

	private Logger logger = LoggerFactory.getLogger(EffectBuyerRealCountThread.class);
	
	private List<TradeDTO> tradeList;
	
	public EffectBuyerRealCountThread(List<TradeDTO> tradeList){
		logger.info("!开始统计真实数据~~~~~~~~~~~~~~~~~~~!!!!!!!!!!!!!初始化,tradeList的大小：" + tradeList.size());
		this.tradeList = tradeList;
	}
	
	public EffectPictureReal call() throws Exception {
		logger.info("!开始统计真实数据~~~~~~~~~~~~~~~~~~~!!!!!!!!!!!!!list。size：" + tradeList.size());
		EffectPictureReal effectPictureReal = new EffectPictureReal();
		//付款的订单状态的list
		List<String> payStatusList = new ArrayList<String>();
		payStatusList.add(TradeStatusUtils.TRADE_FINISHED);
		payStatusList.add(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
		payStatusList.add(TradeStatusUtils.TRADE_CLOSED);
		payStatusList.add(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
		payStatusList.add(TradeStatusUtils.TRADE_BUYER_SIGNED);
		List<String> waitStatusList = new ArrayList<String>();
		waitStatusList.add(TradeStatusUtils.WAIT_BUYER_PAY);
		waitStatusList.add(TradeStatusUtils.TRADE_CLOSED_BY_TAOBAO);
		Set<String> totalBuyerSet_TOTAL = new HashSet<String>(),payBuyerSet_TOTAL = new HashSet<String>(),waitBuyerSet_TOTAL = new HashSet<String>(),refundBuyerSet_TOTAL = new HashSet<String>();
		Set<String> totalBuyerSet_TAOBAO = new HashSet<String>(),payBuyerSet_TAOBAO = new HashSet<String>(),waitBuyerSet_TAOBAO = new HashSet<String>(),refundBuyerSet_TAOBAO = new HashSet<String>();
		Set<String> totalBuyerSet_WAP = new HashSet<String>(),payBuyerSet_WAP = new HashSet<String>(),waitBuyerSet_WAP = new HashSet<String>(),refundBuyerSet_WAP = new HashSet<String>();
		for (int i = 0; i < tradeList.size(); i++) {
			TradeDTO trade = tradeList.get(i);
			logger.info("~~~~~~~~~~~~~~查询真实数据，第" + i +"次循环订单");
			if(trade != null){
				totalBuyerSet_TOTAL.add(trade.getBuyerNick());
				if(payStatusList.contains(trade.getStatus())){
					payBuyerSet_TOTAL.add(trade.getBuyerNick());
				}
				if(waitStatusList.contains(trade.getStatus()) && false == trade.isRefundFlag()){
					waitBuyerSet_TOTAL.add(trade.getBuyerNick());
				}
				if(true == trade.isRefundFlag()){
					refundBuyerSet_TOTAL.add(trade.getBuyerNick());
				}
				if(trade.getTradeFrom().contains("TAOBAO")){
					totalBuyerSet_TAOBAO.add(trade.getBuyerNick());
					if(payStatusList.contains(trade.getStatus())){
						payBuyerSet_TAOBAO.add(trade.getBuyerNick());
					}
					if(waitStatusList.contains(trade.getStatus()) && false == trade.isRefundFlag()){
						waitBuyerSet_TAOBAO.add(trade.getBuyerNick());
					}
					if(true == trade.isRefundFlag()){
						refundBuyerSet_TAOBAO.add(trade.getBuyerNick());
					}
				}
				if(trade.getTradeFrom().contains("WAP")){
					totalBuyerSet_WAP.add(trade.getBuyerNick());
					if(payStatusList.contains(trade.getStatus())){
						payBuyerSet_WAP.add(trade.getBuyerNick());
					}
					if(waitStatusList.contains(trade.getStatus()) && false == trade.isRefundFlag()){
						waitBuyerSet_WAP.add(trade.getBuyerNick());
					}
					if(true == trade.isRefundFlag()){
						refundBuyerSet_WAP.add(trade.getBuyerNick());
					}
				}
			}
		}
		effectPictureReal.setTotalBuyerReal_TOTAL(totalBuyerSet_TOTAL.size());
		effectPictureReal.setPayBuyerReal_TOTAL(payBuyerSet_TOTAL.size());
		effectPictureReal.setWaitPayBuyerReal_TOTAL(waitBuyerSet_TOTAL.size());
		effectPictureReal.setRefundBuyerReal_TOTAL(refundBuyerSet_TOTAL.size());
		effectPictureReal.setTotalBuyerReal_TAOBAO(totalBuyerSet_TAOBAO.size());
		effectPictureReal.setPayBuyerReal_TAOBAO(payBuyerSet_TAOBAO.size());
		effectPictureReal.setWaitPayBuyerReal_TAOBAO(waitBuyerSet_TAOBAO.size());
		effectPictureReal.setRefundBuyerReal_TAOBAO(refundBuyerSet_TAOBAO.size());
		effectPictureReal.setTotalBuyerReal_WAP(totalBuyerSet_WAP.size());
		effectPictureReal.setPayBuyerReal_WAP(payBuyerSet_WAP.size());
		effectPictureReal.setWaitPayBuyerReal_WAP(waitBuyerSet_WAP.size());
		effectPictureReal.setRefundBuyerReal_WAP(refundBuyerSet_WAP.size());
		return effectPictureReal;
	}
	

}
