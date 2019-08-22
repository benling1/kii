package s2jh.biz.shop.crm.taobao.service.appraise.monitoring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.order.dao.TradeRatesDao;
import s2jh.biz.shop.crm.order.entity.TradeRates;
import s2jh.biz.shop.crm.order.service.TradeRatesService;
import s2jh.biz.shop.crm.schedule.rateJob.TbTradeRates;
import s2jh.biz.shop.crm.schedule.rateJob.TotalResponse;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.taobao.service.judgment.appraise.JudgeAppraiseMainUtil;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.util.SyncJudgeRequestUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;
import s2jh.biz.shop.crm.user.service.UserInfoService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderatesGetResponse;
@Deprecated 
@Service
public class AppraiseServiceImpl implements AppraiseService {

	@Autowired
	private JudgeAppraiseMainUtil judgeAppraiseMainUtil;
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private TradeRatesService tradeRatesService;
	
	@Autowired
	private  UserInfoService userInfoService;
	
	@Autowired
	private TradeRatesDao tradeRatesDao;
	
	@Autowired
	private  SyncJudgeRequestUtil syncJudgeRequestUtil;
	
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;
	
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	//日志
    private static final Log logger = LogFactory.getLog(AppraiseServiceImpl.class);
	@Override
	public void appraiseRated(JSONObject parseJSON) throws Exception {
		Date myDate = new Date();
		//获取到评价者
		String rater = parseJSON.getString("rater");
		String tid = parseJSON.getString("tid");
		Map<String,Object> map = new ConcurrentHashMap<String,Object>();
		map.put("rater", rater);
		String buyerName = parseJSON.getString("buyer_nick");
		map.put("buyerName", buyerName);
		String oid = parseJSON.getString("oid");
		map.put("oid", oid);
		String sellerName = parseJSON.getString("seller_nick");
		map.put("sellerName", sellerName);
		map.put("tid", tid);
		map.put("userId", sellerName);
		map.put("myDate", myDate);
		logger.info("评价tmc----------评价者"+sellerName+"--tid："+tid);
		
		//判断是否是同一个主订单，如果是则只发送一次
		/*boolean canExecute = syncJudgeRequestUtil.canExecute(tid);
		if(canExecute){
			TmcThreadUtil.queueTid.put(tid);
		}*/
		//如果评价者类型是买家，中差评监控，中差评安抚都进行判断
		if (rater.equals("buyer")) {
			logger.info("评价tmc----评价者类型是买家，评价者类型："+rater+"--评价者："+sellerName);
			//进行用户的判断并设置责任链
			//如果是中差评监控设置
			map.put("flag", true);
			try {
				map = judgeAppraiseMainUtil.AppraiseSendMessage(map,"1");
				if(map.containsKey("flag")&&(Boolean)map.get("flag")){
					map = taoBaoSendMessageUtil.sendSingleMessage(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//中差评安抚设置
			map.remove("smsInfo");
			map.put("flag", true);
			map = judgeAppraiseMainUtil.AppraiseSendMessage(map, "2");
			if (map.containsKey("flag")&&(Boolean)map.get("flag")) {
				map = taoBaoSendMessageUtil.sendSingleMessage(map);
			}
		}
		
		/**
		 * 当一个消息过来的时候,查询评价表是否存在数据,
		 * ----不存在就直接保存数据到评价表
		 * -----存在就再次调用一下淘宝接口获取内容数量,
		 * 		与评价表的数据做比较,
		 * 		如果数据条数大于评价表就将其大于的条数添加的到评价表中
		 * 		如果小于,在评价表缺少这条数据将其标记为删除的数据
		 */
		/**
		 * 获取两个id查询=======================================
		 */
//		String JsonTid = parseJSON.getString("tid");
//		String JsonOid = parseJSON.getString("oid");
//		//获取卖家昵称
//		String userId = parseJSON.getString("seller_nick");
//		
//		if(JsonTid != null && JsonOid !=null && userId !=null){
//			if(!JsonTid.equals("") && !JsonOid.equals("") && !userId.equals("")){
//				//通过两个id查询出评价表的数据
//				List<TradeRates> myTradeRates= tradeRatesService.findtradeRatesByTidAndOid(JsonTid,JsonOid);
//				
//				//调用一下淘宝接口获取数据
//				List<TradeRates> taoBaotradeRates = getTaoBaotradeRates(JsonTid, userId);
//				
//				/**
//				 * 调用方法将两个list数据传入,对其做添加或者标记为已删中差评
//				 */
//				judgeTradeRates(myTradeRates,taoBaotradeRates,tradeRatesService);
//				
//			}
//	
//		}
			
	}

	/**
	 * 调用方法将两个list数据传入,对其做添加或者标记为已删中差评
	 * helei 2017年3月13日下午6:31:26
	 */
	@SuppressWarnings("unused")
	private static void judgeTradeRates(List<TradeRates> myTradeRates,List<TradeRates> taoBaotradeRates,TradeRatesService tradeRatesService) {
		//判断ListTradeRates是否查询到数据没有就直接保存淘宝的数据到数据库
		if(myTradeRates !=null && myTradeRates.size()>0){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			//如果评价表的list数据大于淘宝的list数据,就将评价表中淘宝没有的这条数据标记为删除
			if(myTradeRates.size()>taoBaotradeRates.size()){
				Set<Integer> MySet = new HashSet<Integer>();//获取评价表与淘宝的评价相同数据,MyTradeRates中的索引
				Set<Integer> TbSet = new HashSet<Integer>();//获取评价表与淘宝的评价相同数据,taoBaotradeRates中的索引
				for (int i = 0; i < myTradeRates.size(); i++) {
					for (int j = 0; j < taoBaotradeRates.size(); j++) {
						Date mycreated = myTradeRates.get(i).getCreated();
						String MyDate = "";
						if(mycreated!=null){
							MyDate = format.format(mycreated);
						}
						Date tbcreated = taoBaotradeRates.get(j).getCreated();
						String TbDate = "";
						if(tbcreated !=null){
							TbDate = format.format(tbcreated);
						}
						if(MyDate.equals(TbDate)){
							MySet.add(i);
							TbSet.add(j);
						}
					}
				}
				
				//删除掉MyTradeRates集合对象中有MySet的索引值
				for (Integer myInt : MySet) {
					myTradeRates.remove(myInt);			
				}
				
				//将remove过后的新list
				if(myTradeRates !=null && myTradeRates.size()>0){
					for (TradeRates tradeRates : myTradeRates) {
						tradeRates.setResult("iSDelete");
						tradeRatesService.updataTradeRatesISDelete(tradeRates);
					}
				}
				
				//删除掉taoBaotradeRates集合对象中有TbSet的索引值
				for (Integer tbInt : TbSet) {
					taoBaotradeRates.remove(tbInt);			
				}
				
				//将remove过后的新list
				if(taoBaotradeRates !=null && taoBaotradeRates.size()>0){
					tradeRatesService.save(taoBaotradeRates);
				}
				
			//如果评价表的list数据小于或等于淘宝的list数据,就将淘宝多出的数据添加到表中
			}else{
				Set<Integer> TbSet = new HashSet<Integer>();//获取评价表与淘宝的评价相同数据,taoBaotradeRates中的索引
				for (int i = 0; i < myTradeRates.size(); i++) {
					for (int j = 0; j < taoBaotradeRates.size(); j++) {
						Date mycreated = myTradeRates.get(i).getCreated();
						String MyDate = "";
						if(mycreated!=null){
							MyDate = format.format(mycreated);
						}
						Date tbcreated = taoBaotradeRates.get(j).getCreated();
						String TbDate = "";
						if(tbcreated !=null){
							TbDate = format.format(tbcreated);
						}
						if(MyDate.equals(TbDate)){
							TbSet.add(j);
						}
					}
				}
				
				//删除掉taoBaotradeRates集合对象中有TbSet的索引值
				for (Integer tbInt : TbSet) {
					taoBaotradeRates.remove(tbInt);			
				}
				
				//将remove过后的新list
				if(taoBaotradeRates !=null && taoBaotradeRates.size()>0){
					tradeRatesService.save(taoBaotradeRates);
				}
				
			}
		}else{
			//如果评价表中没有数据就直接保存淘宝的评价数据到评价表中
			if(taoBaotradeRates!=null && taoBaotradeRates.size()>0){
				tradeRatesService.save(taoBaotradeRates);
			}
		}
	}


	/**
	 * 通过oid和tid调用淘宝接口查询出评价信息
	 * helei 2017年3月13日下午3:16:35
	 */
	public  List<TradeRates> getTaoBaotradeRates(String tid,String userId){
		List<TradeRates> tradeRatesList = new ArrayList<TradeRates>();
		
		try {
			if(userId==null){
				return null;
			}
			//获取token信息
			String token = judgeUserUtil.getUserTokenByRedis(userId);
			if(token == null)
				return null;
			// 调用taobao接口
			DefaultTaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
			// 设置调用接口查询条件
			TraderatesGetRequest req = new TraderatesGetRequest();
			req.setFields("tid,oid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid");
			req.setRateType("get");
			req.setRole("buyer");
			req.setTid(Long.parseLong(tid));
			// 根据评价时间查询，第一次查询需要注释掉，开始时间为定时任务开启的时间的24小时之前，结束时间为定时任务开启时间
			if (token!=null) {
				TraderatesGetResponse rsp = client.execute(req,token);
				// 通过GSON解析，使用4个实体类来接受(TotalResponse、TradeRateResponse、TradeRatess、TbTradeRates)
				GsonBuilder gsonBuidler = new GsonBuilder();
				gsonBuidler.setDateFormat("yyyy-MM-dd HH:mm:ss");
				Gson gson = gsonBuidler.create();
				TotalResponse getResponse = gson.fromJson(rsp.getBody(),TotalResponse.class);
				List<TbTradeRates> tbTradeRatesList = new ArrayList<TbTradeRates>();
				if (getResponse != null&& getResponse.getTraderates_get_response() != null&& getResponse.getTraderates_get_response().getTrade_rates() != null) {
					tbTradeRatesList = getResponse.getTraderates_get_response().getTrade_rates().getTrade_rate();
				}
				//循环复制
				if (tbTradeRatesList != null && tbTradeRatesList.size() > 0) {
					// 循环获得的评价信息，填充到tradeRates中
					for (TbTradeRates tbTradeRates : tbTradeRatesList) {
						TradeRates tradeRates = new TradeRates();
						tradeRates.setOid(tbTradeRates.getOid());
						tradeRates.setTid(tbTradeRates.getTid());
						tradeRates.setRole(tbTradeRates.getRole());
						//判断买家昵称是否是加密类型，如果是   则解密，变成名文
						try {
							if(EncrptAndDecryptClient.isEncryptData(tbTradeRates.getNick(), EncrptAndDecryptClient.SEARCH)){
							String nick = EncrptAndDecryptClient.getInstance().decryptData(tbTradeRates.getNick(), EncrptAndDecryptClient.SEARCH,token);
							tradeRates.setNick(nick);
							}else{
								tradeRates.setNick(tbTradeRates.getNick());
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						tradeRates.setResult(tbTradeRates.getResult());
						tradeRates.setCreated(tbTradeRates.getCreated());
						tradeRates.setRatedNick(tbTradeRates.getRated_nick());
						tradeRates.setItemTitle(tbTradeRates.getItem_title());
						tradeRates.setItemPrice(tbTradeRates.getItem_price());
						tradeRates.setContent(tbTradeRates.getContent());
						tradeRates.setReply(tbTradeRates.getReply());
						tradeRates.setNumIid(tbTradeRates.getNumIid());
						tradeRatesList.add(tradeRates);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tradeRatesList;
	}
}
