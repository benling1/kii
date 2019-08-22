package s2jh.biz.shop.crm.schedule.rateJob;

public class TotalResponse {

	private TradeRateResponse traderates_get_response;

	public TradeRateResponse getTraderates_get_response() {
		return traderates_get_response;
	}
	public void setTraderates_get_response(TradeRateResponse traderates_get_response) {
		this.traderates_get_response = traderates_get_response;
	}
	@Override
	public String toString() {
		return "TotalResponse [traderates_get_response="
				+ traderates_get_response + "]";
	}
}
