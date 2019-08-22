package s2jh.biz.shop.crm.order.service;


import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.TraderateAddRequest;
import com.taobao.api.response.TraderateAddResponse;

import s2jh.biz.shop.crm.taobao.taobaoInfo;

public class Test {

	public static void main(String[] args) throws ApiException {
		/*Date date1 = new Date();
		System.out.println(date1);
		"70002101236035648d8fe158657a383a72f46eebc09385741b67b6d5e3860a811f123fa2706923219"
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(format.format(date1));
		String dateString = DateUtils.getTimeByHour(format.format(date1), -24);
		System.out.println(dateString);
		int findCountById = tradeRatesService.findCountById(18L);
		System.out.println(findCountById);*/
		//---------------------------------------------同步评价测试------------------------------------------------------------
		/*SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		DefaultTaobaoClient client = new DefaultTaobaoClient(
				taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		TraderatesGetRequest req = new TraderatesGetRequest();
		req.setFields("tid,oid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid");
		req.setRateType("get");
		req.setRole("buyer");
		// 根据评价时间查询，第一次查询需要注释掉，开始时间为定时任务开启的时间的24小时之前，结束时间为定时任务开启时间
		req.setStartDate(StringUtils.parseDateTime(DateUtils
				.getTimeByHour(format.format(new Date()), -720)));
		req.setEndDate(StringUtils.parseDateTime(format
				.format(new Date())));
		GsonBuilder gsonBuidler = new GsonBuilder();
		gsonBuidler.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = gsonBuidler.create();
		TraderatesGetResponse rsp = client.execute(req,
				"70002101615275818932de795a71da1ec0db9dc0820d1f4814c8c748d286514416680fb3037728885");
		
		TotalResponse getResponse = gson.fromJson(rsp.getBody(),
				TotalResponse.class);
		System.err.println(getResponse);
		*/
		
		//-----------------------------------------新增评价--------------------------------------------------------------------------
		DefaultTaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,taobaoInfo.appKey,taobaoInfo.appSecret);
		//添加到评价列表
		TraderateAddRequest addRequest = new TraderateAddRequest();
		addRequest.setOid(3119036439477018L);
		addRequest.setTid(3119036439467018L);
		addRequest.setContent("haoping");
		addRequest.setResult("good");
		addRequest.setRole("seller");
		TraderateAddResponse response2 = client.execute(addRequest, "70002100444d0f188054b995b0aeea0a1adab1fd2daa1798c6bf77b412f877514f384d9714903996");
		System.out.println(response2.getBody());
		System.out.println(response2.getErrorCode());
		System.out.println(response2.getMsg());
		System.out.println(response2.getParams());
		System.out.println(response2.getSubCode());
		System.out.println(response2.getSubMsg());
		System.out.println(response2.getTradeRate());
		
		
	}
}