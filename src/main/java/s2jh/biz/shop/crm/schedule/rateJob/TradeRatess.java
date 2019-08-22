package s2jh.biz.shop.crm.schedule.rateJob;

import java.util.List;

public class TradeRatess {

	private List<TbTradeRates> trade_rate;

	public List<TbTradeRates> getTrade_rate() {
		return trade_rate;
	}

	public void setTrade_rate(List<TbTradeRates> trade_rate) {
		this.trade_rate = trade_rate;
	}

	@Override
	public String toString() {
		return "TradeRatess [trade_rate=" + trade_rate + "]";
	}

}
