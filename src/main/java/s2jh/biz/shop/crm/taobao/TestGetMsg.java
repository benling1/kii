package s2jh.biz.shop.crm.taobao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.TradeRate;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.request.TmcUserGetRequest;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TmcUserGetResponse;
import com.taobao.api.response.TraderatesGetResponse;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.entity.TradeRates;
import s2jh.biz.shop.crm.schedule.job.auto.AutoStaticsSellerDataJob;
import s2jh.biz.shop.crm.taobao.service.SendMessageService;
import s2jh.biz.shop.crm.taobao.service.appraise.monitoring.AppraiseServiceImpl;
import s2jh.biz.shop.crm.taobao.service.impl.ScheduleSendMessageServiceImpl;
import s2jh.biz.shop.crm.taobao.service.impl.SendMessageServiceImpl;
import s2jh.biz.shop.crm.taobao.service.judgment.cowry.GoodsCareMainUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.logistics.consign.LogisticsServiceImpl;
import s2jh.biz.shop.crm.taobao.service.judgment.returned.money.ReturnedPaymentMainUtil;
import s2jh.biz.shop.crm.taobao.service.ordercare.PlaceAnOrderCareServiceImpl;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.tmc.manage.TradeTmcManageService;
import s2jh.biz.shop.crm.tmc.schedule.TmcDeleteOldSms;
import s2jh.biz.shop.crm.tmc.service.RateMonitoringPacifyService;

@Controller
public class TestGetMsg
{
  @Autowired
  private LogisticsServiceImpl logisticsServiceImpl;
  @Autowired
  private SendMessageService sendMessageService;
  @Autowired
  private SmsSendInfoScheduleService smsSendInfoScheduleService;
  @Autowired
  private SendMessageServiceImpl sendMessageServiceImpl;
  @Autowired
  private AutoStaticsSellerDataJob autoStaticsSellerDataJob;
  @Autowired
  private ScheduleSendMessageServiceImpl scheduleSendMessageServiceImpl;
  @Autowired
  private ReturnedPaymentMainUtil returnedPaymentMainUtil;
  @Autowired
  private CacheService cacheService;
  @Autowired
  private AppraiseServiceImpl appraiseServiceImpl;
  @Autowired
  private PlaceAnOrderCareServiceImpl placeAnOrderCareServiceImpl;
  @Autowired
  private GoodsCareMainUtil goodsCareMainUtil;
  @Autowired
  private TradeTmcManageService tradeTmcManageService;
  @Autowired
  private TBMessageManage tBMessageManage;
  @Autowired
  private TmcDeleteOldSms tmcDeleteOldSms;
  @Autowired
  private MyBatisDao myBatisDao;
  @Autowired
  private RateMonitoringPacifyService rateMonitoringPacifyService;
  @Autowired
  private JudgeUserUtil judgeUserUtil;
  @Resource(name = "redisTemplateLock")
  private StringRedisTemplate redisTemplate;
  private static volatile int i = 0;
  @RequestMapping("tmc")
  public void test() throws Exception{
	  JSONObject json = new JSONObject();
//	  json.put("topic", "taobao_trade_TradeCreate");
	  json.put("topic", "taobao_trade_TradeSuccess");
	  json.put("content", "{\"buyer_nick\":\"yjackstraw\",\"payment\":\"0.10\",\"status\":\"TRADE_NO_CREATE_PAY\",\"iid\":547297227206,\"oid\":58681907060570486,\"tid\":58681907060570486,\"type\":\"guarantee_trade\",\"post_fee\":\"0.00\",\"seller_nick\":\"哈数据库等哈\"}");
	  tradeTmcManageService.doHandle(json.toJSONString());
  }
  @RequestMapping("deltmc")
  public void detest() throws Exception{
//	  long start = System.currentTimeMillis();
//	  this.tmcDeleteOldSms.doHandle();
//	  long end = System.currentTimeMillis();
//	  System.out.println("花费时间"+(end-start));
	  TradeRates tradeRates = new TradeRates();
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
				taobaoInfo.appKey, taobaoInfo.appSecret);
		TraderatesGetRequest req = new TraderatesGetRequest();
		req.setFields("tid,oid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid");
		req.setRateType("get");
		req.setRole("buyer");
		String tid = String.valueOf("51089152276103100");
		req.setTid(Long.parseLong(tid));
		String sessionKey = this.judgeUserUtil.getUserTokenByRedis("哈数据库等哈");
		TraderatesGetResponse rsp = client.execute(req, sessionKey);
		List<TradeRate> tradeRateList = rsp.getTradeRates();
		for (TradeRate tradeRate : tradeRateList) {
			tradeRate.setResult("bad");
		}
		this.rateMonitoringPacifyService.doHandle("哈数据库等哈", tradeRateList);
	  
  }
  @RequestMapping({"wuliu"})
  public void wuliu(){
	  Message me1 = new Message();
	  me1.setTopic("taobao_trade_TradeRated");
	  me1.setContent("{\"buyer_nick\":\"发动机盖i你看了\",\"payment\":\"1.00\",\"status\":\"TRADE_FINISHED\",\"iid\":547399920317,\"oid\":51160554435103100,\"rater\":\"buyer\",\"tid\":51089152276103100,\"type\":\"guarantee_trade\",\"seller_nick\":\"哈数据库等哈\"}");
	  Message me2 = new Message();
//	  me2.setTopic("taobao_logistics_LogsticDetailTrace");
//	  me2.setContent("{\"time\":\"2017-08-10 15:31:09\",\"desc\":\"快件已到达【北京十里堡公司】 扫描员是【张海峰】上一站是【】\",\"company_name\":\"申通快递\",\"out_sid\":\"888748115975\",\"action\":\"ARRIVAL\",\"tid\":43895470821219993}");
//	  new Thread(){
//		  @Override
//			public void run() {
//			  try {
//				tBMessageManage.run(me1, null);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}  
//			}
//	  }.start();
//	  new Thread(){
//		  @Override
//			public void run() {
//			  try {
//				tBMessageManage.run(me2, null);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}  
//			}
//	  }.start();
//	  try {
//		this.smsSendInfoScheduleService.findSentCitySms("哈数据库等哈", "", "7");
//	} catch (SecretException e) {
//		e.printStackTrace();
//	}
  }
  @RequestMapping("rate")
  public void retedTest(){
	  Map<Object,Object> map = this.cacheService.hGetAll("SAVEORDERSETUPTOXDGHJBSEDATACACHE");
	  for (Object o : map.values()) {
		System.out.println(String.valueOf(o));
	}
  }
  @RequestMapping("rate1")
  public void retedTestw(){
//	  TradeRate rate = new TradeRate();
//	  rate.setNick("");
//	  rate.setRatedNick(ratedNick);
  }
  @RequestMapping({"TmcSw"})
  public void TmcSwitch() {
//	  new Thread(){
//			 @Override
//			public void run() {
//				 RedisLock lock = new RedisLock(redisTemplate,"test1");
//				 try {
//					  if (lock.lock()) {
//						System.out.println("无视");
//					  } 
//				}catch (InterruptedException e) {
//					e.printStackTrace();
//				}catch (Exception e) {
//					e.printStackTrace();
//				}  finally {
//					lock.unlock();
//				}
//			}
//		 }.start();
    SmsSendInfo ss = new SmsSendInfo();
    ss.setContent("1");
    ss.setActualDeduction(Integer.valueOf(1));
//    ss.setAutograph("1");
    ss.setChannel("1");
    ss.setNickname("123asdfaS爱打架卡1");
    ss.setCreatedBy("1");
    ss.setLastModifiedBy("1");
    ss.setPhone("18611223344");
    ss.setType("1");
    ss.setUserId("哈数据库等哈");
    ss.setSendTime(new Date());
    ss.setStartSend(new Date());
    ss.setEndSend(new Date());
    ss.setCreatedDate(new Date());
    ss.setTid((long)new Random().nextInt(1000));
    this.smsSendInfoScheduleService.doAutoCreate(ss);
	  
  }
  
  public static void main(String[] args) throws Exception{
	 /* TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
	  CrmServiceChannelShortlinkCreateRequest req = new CrmServiceChannelShortlinkCreateRequest();
	  req.setShortLinkName("淘短链推广商品1");
	  req.setLinkType("LT_TRADE");
	  req.setShortLinkData("16980030192939083");
	  CrmServiceChannelShortlinkCreateResponse rsp = null;
	try {
		rsp = client.execute(req, "70002101736758f92b265567446cdba69fbfec503e49a89a1d60487f26f00d47f7b51882106245636");
	} catch (ApiException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  System.out.println(rsp.getBody());*/
	 /* int contentNum=0;
	  String messageCount ="402120393012 发动机盖i你看了申通快递 12823340556103100 2017-05-05 11:17:08 哈数据库等哈 0.23 北京市（签收提醒）退订回N";
	  if (messageCount.length() <= 70) {
		  contentNum = 1;
		} else {
			contentNum = (messageCount.length() + 66) / 67;
		}
//		contentNum = contentNum;
	 
	  System.out.println(contentNum);*/
	  
	 /* TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		TradesSoldGetRequest req = new TradesSoldGetRequest();
		// 设置需要返回的字段列表
		req.setFields(
				"oid,tid,cid,num_iid,item_meal_id,sku_id,refund_id,bind_oid,item_meal_name,pic_path,seller_nick,buyer_nick,refund_status,outer_iid,snapshot_url,snapshot,timeout_action_time,buyer_rate,seller_rate,seller_type,sub_order_tax_fee,sub_order_tax_rate,status,title,type,iid,price,num,outer_sku_id,order_from,total_fee,payment,discount_fee,adjust_fee,modified,sku_properties_name,is_oversold,is_service_order,end_time,consign_time,order_attr,shipping_type,logistics_company,invoice_no,is_daixiao,divide_order_fee,part_mjz_discount,ticket_outer_id,ticket_expdate_key,store_code,is_www,tmser_spu_code,bind_oids,zhengji_status,md_qualification,md_fee,customization,inv_type,is_sh_ship,shipper,f_type,f_status,F_TERM,COMBO_ID,ASSEMBLY_RELA,ASSEMBLY_PRICE,ASSEMBLY_ITEM,receiver_district,receiver_city,step_trade_status,created,receiver_name,receiver_mobile,buyer_flag,seller_flag");
		// 设置开始时间
		req.setStartCreated(DateUtils.convertStringToDate("2017-04-01"));
		// 设置结束时间
		req.setEndCreated(DateUtils.convertStringToDate("2017-04-21"));
		// 设置状态 等待卖家发货
		req.setStatus("WAIT_SELLER_SEND_GOODS");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 

		TradesSoldGetResponse rsp = null;
		try {
			rsp = client.execute(req, "70002100030d174f1716e87d4ea2c1c61cf1a00cfd1c2498119ca02008382eb5e9ae8762529184045");
		} catch (ApiException e) {
			e.printStackTrace();
		}
		System.out.println(rsp.getBody());*/
//	 
	  
//	 String link = GenerateLinkService.getLink("19564789867231949","70002100a0285418de63c94b8990a58b9b9191b8d59c265db608d3c61aedc4bd35b7efa2564354527");
//	 System.out.println(link.length());
	  
//	  TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
//	  TmcGroupAddRequest req = new TmcGroupAddRequest();
//	  req.setGroupName("vip_user");
//	  req.setNicks("哈数据库等哈");
//	  req.setUserPlatform("tbUIC");
//	  TmcGroupAddResponse rsp = null;
//	try {
//		rsp = client.execute(req);
//	} catch (ApiException e) {
//		e.printStackTrace();
//	}
//	  System.out.println(rsp.getBody());
	  
//	  Map<String, Object> map = new HashMap<String, Object>();
//		map.put("orderId", "310787664800898");
//		UserRechargeService userRechargeService = SpringContextUtil
//				.getBean("userRechargeService");
//		UserRecharge ur = userRechargeService.getUserRechargeInfo(map);
//		if (ur == null) {
//			return;
//		}
//		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
//				taobaoInfo.appKey, taobaoInfo.appSecret);
//		LogisticsTraceSearchRequest req = new LogisticsTraceSearchRequest();
//		req.setTid(34211461854103100L);
//		req.setSellerNick("哈数据库等哈");
//		req.setIsSplit(1L);
//		req.setSubTid("33573822244103100"); 402641803438  402641803437
//		LogisticsTraceSearchResponse rsp = client.execute(req);
//		System.out.println(rsp.getBody());
//		System.out.println(rsp.getBody().startsWith("{\"logistics_trace_search_response"));
//		req.setIsSplit(1L);
//		req.setSubTid("34196763883103100");
//		rsp = client.execute(req);
//		System.out.println(rsp.getBody());
//		System.out.println(rsp.getCompanyName());
//		System.out.println(rsp.getOutSid());
//		System.out.println(rsp.getBody().startsWith("{\"logistics_trace_search_response"));
//	  System.out.println("11312312".matches("\\d+(\\.\\d+)?"));
//	  long start = System.currentTimeMillis();
//	  for (int i = 0; i < 1000000; i++) {
////		  StringUtils.isDigits(i+"");  139ms
////		  StringUtils.isDigits(1.1+i+"");
//		  ValidateUtil.isIntegerOrLong(i+""); //365ms
//	  }
//	  long end = System.currentTimeMillis();
//	  System.out.println("花费了："+(end-start)+"ms");
//	  System.out.println(StringUtils.isDigits("13123123123123123123"));
//	   for(int i = 0; i < 5; i++){
//		  for(int y = 0; y < 5; y++){
//			  if(y==1){
//				  System.out.println("y="+y);
//				  break;
//			  }
//		  }
//		  if(i>4)
//			  break;
//	  }
//	   System.out.println();
//	  List<String> all = new ArrayList<String>();
//	  all.add(",");
//	  all.add("~GUGSOAVi4t7aBRUEGbAP3Q==~MSSz~1~~");
//	  all.add("~KTZwudIOExRWLhgvpGD60g==~tVomlKV5gNG518HH~1~~");
//	  all.add("~ih8C9am2ER/Yuw+NZwA7Tqwyf/2N3LIhDCcvCfQBmTI=~YSh9e0+ub5Qws0LGl8TD~1~~");
//	  all.add("非模糊数据");
//	  if(EncrptAndDecryptClient.isEncryptData(all, EncrptAndDecryptClient.SEARCH)){
//		  System.out.println("111");
//	  }else{
//		  System.out.println("2222");
//	  }
//	  System.out.println(EncrptAndDecryptClient.getInstance().decrypt("仁川m", EncrptAndDecryptClient.SEARCH, "700021013463562960f7834b7c67147f2d2b3c435282901959aa1db04d186961e93577a1097805039"));
//	  Map<String,String> map = EncrptAndDecryptClient.getInstance().decrypt(all, EncrptAndDecryptClient.SEARCH,"70002100b03544918ea4b4592103c64f8e5aa37d462b9cedc7f9ab1280d885bc71f37372529184045");
//	  for(Entry<String,String> entry : map.entrySet()){
//		  System.out.println("解密后的数据："+entry.getValue());
//	  }
//	  System.out.println();
//	  System.out.println(EncrptAndDecryptClient.getInstance().encrypt("我是模糊数据", EncrptAndDecryptClient.SEARCH, "70002100b03544918ea4b4592103c64f8e5aa37d462b9cedc7f9ab1280d885bc71f37372529184045"));
//	 System.out.println(EncrptAndDecryptClient.getInstance().encrypt("18612345678", EncrptAndDecryptClient.PHONE, "70002100603f1671bf26f8ea6c49e1b99e770ac3278548eee7641f2728bb67dd6be52812529184045"));
//	  String str = ",1,2,3";
	  
//	  String[] s = str.split(",");
//	  System.out.println(Arrays.toString(s));
//	  s[1] = "5";
//	  System.out.println(Arrays.toString(s));
//	  s[0] = "4";
//	  System.out.println(Arrays.toString(s));
//	  s[3] = "7";
	  
	  //查询评价记录
//		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,taobaoInfo.appKey, taobaoInfo.appSecret);
//		TraderatesGetRequest req = new TraderatesGetRequest();
//		req.setFields("tid,oid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid");
//		req.setRateType("get");
//		req.setRole("buyer");
//		req.setTid(50627880389103100L);
//		TraderatesGetResponse rsp = client.execute(req,"70002100b49544992103c648e05e87ae4db7693f39f929d5f6fe45657f9ac18fe9ad5502529184045");
//		System.out.println(rsp.getBody());
	  
//	  System.out.println(EncrptAndDecryptClient.getInstance().decrypt("~pNbDFeg8K/N8vFoeQOLIIyvuFvHYpkPTG4zOycbz4sw=~UJTU/w9ZocQBykiF81nM8MldkExxKzNh~1~~", EncrptAndDecryptClient.SEARCH, "70002100b49544992103c648e05e87ae4db7693f39f929d5f6fe45657f9ac18fe9ad5502529184045"));
//	  System.out.println("123123adas,".split(",").length);
	  //查询子订单的评价结果
//	  TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,taobaoInfo.appKey, taobaoInfo.appSecret);
//	  TmallTraderateFeedsGetRequest req = new TmallTraderateFeedsGetRequest();
//	  req.setChildTradeId(50627880389103100L);
//	  TmallTraderateFeedsGetResponse rsp = client.execute(req,taobaoInfo.TEST_SESSIONKEY);
//	  System.out.println(rsp.getBody());
	  
	  ////为用户开通topic消息监听
//	  TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,taobaoInfo.appKey, taobaoInfo.appSecret);
//	  TmcUserPermitRequest req = new TmcUserPermitRequest();
////	  req.setTopics("taobao_trade_TradeCreate");
//	  TmcUserPermitResponse rsp = client.execute(req, "700021005240f1699d44073c766c67d52cc61772dcfd834160434c9c8c66b1ecd2b9c092529184045");
//	  System.out.println(rsp.getBody());
//	  
	  //获取用户的具体权限
	  TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,taobaoInfo.appKey, taobaoInfo.appSecret);
	  TmcUserGetRequest req = new TmcUserGetRequest();
	  req.setFields("user_nick,topics,user_id,is_valid,created,modified");
	  req.setNick("溜溜梅旗舰店");
//	  req.setUserPlatform("vip_user");
	  TmcUserGetResponse rsp = client.execute(req);
	  System.out.println(rsp.getBody());
	  System.out.println(rsp.getTmcUser().getTopics());
//	  System.out.println(Arrays.toString(rsp.getTmcUser().getTopics().toArray()));
	  
	  //取消用户授权
//	  TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,taobaoInfo.appKey, taobaoInfo.appSecret);
//	  TmcUserCancelRequest req = new TmcUserCancelRequest();
//	  req.setNick("哈数据库等哈");
////	  req.setUserPlatform("tbUIC");
//	  TmcUserCancelResponse rsp = client.execute(req);
//	  System.out.println(rsp.getBody());
//	  long s = System.currentTimeMillis();
//	  List<Date> list = new ArrayList<Date>();
//	  list.add(new Date(s+1000));
//	  list.add(new Date(s+1));
//	  list.add(new Date(s+100));
//	  list.add(new Date(s+1000));
//	  for (Date string : list) {
//			System.out.print(string.getTime()+",");
//	  }
//	  Collections.sort(list);
//	  System.out.println();
//	  for (Date string : list) {
//		System.out.print(string.getTime()+",");
//	  }
//	  List<String> a = list.subList(3, 4);
//	  System.out.println(Arrays.toString(list.toArray()));
//	  System.out.println(Arrays.toString(a.toArray()));
//	  SimpleDateFormat sellerFormat = new SimpleDateFormat("yyyy-MM-dd HH");
//	  System.out.println(EncrptAndDecryptClient.getInstance().decrypt("$YWoueEmh/wqQaI4w4LvqLQ==$JU3er5YwGg5BexymHZGdAA==$1$$",
//			  EncrptAndDecryptClient.PHONE, "70002101615275818932de7756e1deee1a6d7caae57cdd3d7e119af9fe299191deabdb92959723515"));
//	  System.out.println(sellerFormat.parse("2017-09-01 "+23));
//	  System.out.println(ResourceBundle.getBundle("rabbitmq").getString("tmc.exchange"));
//	  String str = "{\"area\":\"\",\"badEvaluateInform\":false,\"createdBy\":\"哈数据库等哈\",\"createdDate\":1505102194973,\"delayDate\":null,\"delayEvaluate\":false,\"evaluateBlack\":false,\"evaluateBlackContent\":\"\",\"evaluateBlackType\":\"\",\"evaluateType\":\"\",\"executeType\":true,\"extraAttributes\":null,\"filterBlack\":false,\"filterHassent\":false,\"filterOnce\":true,\"id\":19,\"informMobile\":\"\",\"lastModifiedBy\":\"哈数据库等哈\",\"lastModifiedDate\":1505102194973,\"markedRemove\":false,\"maxExecuteTime\":null,\"maxInformTime\":\"23:00:00\",\"maxMiddleInformTime\":\"\",\"maxPayment\":null,\"maxPrimaryInformTime\":\"\",\"maxProductNum\":null,\"maxSeniorInformTime\":\"\",\"memberLevel\":\"\",\"minExecuteTime\":null,\"minInformTime\":\"08:00:00\",\"minMiddleInformTime\":\"\",\"minPayment\":null,\"minPrimaryInformTime\":\"\",\"minProductNum\":null,\"minSeniorInformTime\":\"\",\"naturalEvaluateInform\":false,\"new\":false,\"notNew\":true,\"productType\":false,\"products\":\"\",\"remindTime\":null,\"sellerFlag\":\"\",\"smsContent\":\"【北京冰点零度】亲爱的{买家昵称}您于{下单时间}下了订单，感谢您选择我们，遇到任何购物问题，请联系我们客服，我们将竭诚为您服务。退订回N\",\"status\":true,\"taskLevel\":3,\"taskName\":\"11111\",\"timeOutInform\":true,\"timeType\":null,\"tradeFrom\":\"\",\"type\":\"1\",\"userId\":\"哈数据库等哈\",\"version\":0}";
//	  TradeSetup t = JSONObject.parseObject(str, TradeSetup.class); 
//	  System.out.println();
//	  String str = "【北京冰点零度】亲爱的【买家昵称】您于{下单时间}下了订单，感谢您选择我们，遇到任何购物问题，请联系我们客服，我们将竭诚为您服务。退订回N";
//	  str =str.replaceAll("【下单时间】",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//	  System.out.println(str);
//	  System.out.println("33,34".contains("3"));
  }

}
