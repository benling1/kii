package s2jh.biz.shop.crm.schedule.rateJob;

import java.util.List;

import s2jh.biz.shop.crm.order.entity.TradeRates;

public class TradeRateResponse {

	private Integer total_results;
	private String request_id;
	private TradeRatess trade_rates;
	public Integer getTotal_results() {
		return total_results;
	}
	public void setTotal_results(Integer total_results) {
		this.total_results = total_results;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public TradeRatess getTrade_rates() {
		return trade_rates;
	}
	public void setTrade_rates(TradeRatess trade_rates) {
		this.trade_rates = trade_rates;
	}
	@Override
	public String toString() {
		return "TradeRateResponse [total_results=" + total_results
				+ ", request_id=" + request_id + ", trade_rates=" + trade_rates
				+ "]";
	}
	
	
}
