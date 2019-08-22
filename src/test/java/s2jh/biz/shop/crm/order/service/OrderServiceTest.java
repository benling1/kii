package s2jh.biz.shop.crm.order.service;


import java.util.List;






import s2jh.biz.shop.crm.order.entity.TradeRates;
import s2jh.biz.shop.crm.schedule.rateJob.TbTradeRates;
import s2jh.biz.shop.crm.schedule.rateJob.TotalResponse;
import s2jh.biz.shop.crm.taobao.taobaoInfo;

import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderatesGetResponse;

public class OrderServiceTest {
	
	
	public static void main(String[] args) throws ApiException {
		TradeRatesService tradeRatesService = new TradeRatesService();
		/*String sessionKey = (String) request.getSession().getAttribute("access_key");*/
		DefaultTaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		TraderatesGetRequest req = new TraderatesGetRequest();
		req.setFields("tid,oid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid");
		req.setRateType("get");
		req.setRole("buyer");
		/*req.setResult("good");*/
		/*req.setPageNo(1L);
		req.setPageSize(100L);*/
		/*req.setPeerNick("helloworld");*/
		/*req.setStartDate(StringUtils.parseDateTime("2011-01-01 00:00:00"));
		req.setEndDate(StringUtils.parseDateTime("2011-01-02 00:00:00"));*/
		/*req.setTid(123456L);*/
		/*req.setUseHasNext(true);*/
		/*req.setNumIid(544316172896L);*/
		/*req.setTid(2926464837261714L);*/
		TraderatesGetResponse rsp = client.execute(req, "700021015316275a0f13960b549fd7b98b4b8b3fdf9146631eed17f77ba6416d72fe034806839374");
		System.out.println(rsp.getBody());
		/*JSONObject jsonObject = JSONObject.fromObject(rsp.getBody());
		System.out.println(jsonObject.getString("traderates_get_response"));
		JSONObject jsonObject2 = JSONObject.fromObject(jsonObject.getString("traderates_get_response"));
		System.out.println(jsonObject2.getString("trade_rates"));*/
		Gson gson = new Gson();
		TotalResponse getResponse = gson.fromJson(rsp.getBody(), TotalResponse.class);
		List<TbTradeRates> tbTradeRatesList = getResponse.getTraderates_get_response().getTrade_rates().getTrade_rate();
		for (TbTradeRates tbTradeRates : tbTradeRatesList) {
			TradeRates tradeRates = new TradeRates();
			tradeRates.setOid(tbTradeRates.getOid());
			tradeRates.setTid(tbTradeRates.getTid());
			tradeRates.setRole(tbTradeRates.getRole());
			tradeRates.setNick(tbTradeRates.getNick());
			tradeRates.setResult(tbTradeRates.getResult());
			tradeRates.setCreated(tbTradeRates.getCreated());
			tradeRates.setRatedNick(tbTradeRates.getRated_nick());
			tradeRates.setItemTitle(tbTradeRates.getItem_title());
			tradeRates.setItemPrice(tbTradeRates.getItem_price());
			tradeRates.setContent(tbTradeRates.getContent());
			tradeRates.setReply(tbTradeRates.getReply());
			tradeRates.setNumIid(tbTradeRates.getNumIid());
			System.out.println(tradeRates.getContent());
			tradeRatesService.saveTradeRate(tradeRates);
		}
	}
}
