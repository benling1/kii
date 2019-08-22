package s2jh.biz.shop.crm.data.service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.shop.crm.data.dao.ShopDataStatisticsDao;
import s2jh.biz.shop.crm.data.entity.ShopDataStatistics;
import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.order.entity.TransactionOrder;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;

@Service
public class ShopDataStatisticsService extends BaseService<ShopDataStatistics, Long>{
	
	@Autowired
	private ShopDataStatisticsDao shopDataStatisticsDao;
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<ShopDataStatistics, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return shopDataStatisticsDao;
	}
	/**
	 * 定时任务查询商户店铺的具体数据  统计昨日数据
	 * @param userNick
	 */
	public void scheduleStatisticsGoods(final List<String> userList) throws Exception{
		int size = userList.size();
		if(size>0){
			size = (int) (size/0.75 + 1) ;
			Date nowDate = new Date();
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			String nowDateString = formate.format(nowDate);
			Date endDate = formate.parse(nowDateString);  //结束时间   假设现在是 2016-11-11 11:11:11  先取出结束时间      2016-11-11 00:00:00
			Calendar cal = Calendar.getInstance();    //结束时间减一天     得出开始时间为  2016-11-10 00:00:00
			cal.setTime(endDate);
			cal.add(Calendar.DATE, -1);
			Date startDate = cal.getTime();//开始时间
			Map<String,Object> sqlMap = new HashMap<String,Object>();
			sqlMap.put("startDate", startDate);
			sqlMap.put("endDate", endDate);
			Map<String,Map<String,Object>> resultMap = null;
			List<Map<String,Object>> allOrdersCountList = this.myBatisDao.findList(TransactionOrder.class.getName(), "findAllTradeCounts", sqlMap);
			resultMap = this.filtrateResultByOneGroup(userList, allOrdersCountList,size);
			Map<String,Object> allOrdersCount = resultMap.get("countMap");  //所有卖家的订单数
			List<Map<String,Object>> paymentList = this.myBatisDao.findList(TransactionOrder.class.getName(), "findTradePaymentSuccess", sqlMap);
			resultMap = this.filtrateResultByOneGroup(userList, paymentList,size);
			Map<String,Object> paymentTotal = resultMap.get("totalMap");  //所有卖家的交易成功额
			List<Map<String,Object>> waitPayList = this.myBatisDao.findList(TransactionOrder.class.getName(), "findAllTradeWaitPay", sqlMap);
			resultMap = this.filtrateResultByOneGroup(userList, waitPayList,size);
			Map<String,Object> waitPayCount = resultMap.get("countMap");  //等待支付的订单数
			List<Map<String,Object>> tradeStatusCountList = this.myBatisDao.findList(TransactionOrder.class.getName(), "findAllUserTradeCountType", sqlMap);
			resultMap = this.filtrateResultByTradeFrom(userList, tradeStatusCountList,size);
			Map<String,Object> wapTotalMap = resultMap.get("wapTotalMap");  //无线总成交额数
			Map<String,Object> jhsTotalMap = resultMap.get("jhsTotalMap");  //聚划算总成交额数
			Map<String,Object> taobaoTotalMap = resultMap.get("taobaoTotalMap");  //PC总成交额数
			
			
			
			List<Map<String,Object>> tradeStatusAllCountList = this.myBatisDao.findList(TransactionOrder.class.getName(), "findAllTradeCountOrderFrom", sqlMap);
			resultMap = this.filtrateResultByTradeFrom(userList, tradeStatusAllCountList,size);
			Map<String,Object> wapCountMap = resultMap.get("wapCountMap"); //无线昨日总笔数
			Map<String,Object> taobaoCountMap = resultMap.get("taobaoCountMap"); // PC昨日总笔数
			List<Map<String,Object>> orderRefundCountList = this.myBatisDao.findList(Orders.class.getName(), "findAllUserRefund", sqlMap);
			resultMap = this.filtrateResultByOneGroup(userList, orderRefundCountList,size);
			Map<String,Object> refundCount = resultMap.get("countMap");  //退款中的订单数
			List<Map<String,Object>> orderWaitCountList = this.myBatisDao.findList(Orders.class.getName(), "findAllUserOrderCountStatus", sqlMap);
			resultMap = this.filtrateResultByOneGroup(userList, orderWaitCountList,size);
			Map<String,Object> waitSellerGoodsCount = resultMap.get("countMap");  //等待发货的订单数
			List<Map<String,Object>> onSaleCountList =  this.myBatisDao.findList(Item.class.getName(), "findCountByOnsale", sqlMap);
			resultMap = this.filtrateResultByOneGroup(userList, onSaleCountList,size);
			Map<String,Object> onSaleCount = resultMap.get("countMap");  //出售中的商品数
			for(String sellerName:userList){
				try {
					this.doCreateShopStatisData(userList, sqlMap,sellerName, allOrdersCount, paymentTotal, waitPayCount, wapTotalMap, jhsTotalMap, taobaoTotalMap, wapCountMap, taobaoCountMap, refundCount, waitSellerGoodsCount, onSaleCount);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 根据n多map结果集来自动创建卖家会员的昨日历史数据统计  呵呵
	 * @param userList 用户昵称集合
	 * @param sqlMap 要创建的map集合
	 * @param sellerName 要创建的卖家昵称
	 * @param allOrdersCount 昨日订单数统计集合
	 * @param paymentTotal 昨日交易额集合
	 * @param waitPayCount 等待支付订单数集合
	 * @param wapTotalMap 无线交易额集合
	 * @param jhsTotalMap 聚划算交易额集合
	 * @param taobaoTotalMap PC交易额集合
	 * @param wapCountMap 无线订单数集合
	 * @param taobaoCountMap PC订单数集合 
	 * @param refundCount 退款中订单数集合
	 * @param waitSellerGoodsCount 等待卖家发货订单数集合
	 * @param onSaleCount 售货中的商品数集合
	 */
	private void doCreateShopStatisData(List<String> userList,Map<String,Object> sqlMap,String sellerName,Map<String,Object> allOrdersCount,Map<String,Object> paymentTotal,
											Map<String,Object> waitPayCount,Map<String,Object> wapTotalMap,Map<String,Object> jhsTotalMap,Map<String,Object> taobaoTotalMap,
											Map<String,Object> wapCountMap,Map<String,Object> taobaoCountMap,Map<String,Object> refundCount,Map<String,Object> waitSellerGoodsCount,
											Map<String,Object> onSaleCount){
		sqlMap.put("sellerName", sellerName);
		sqlMap.put("yesterdayOrderCounts", this.getResultNotNull(allOrdersCount, sellerName));
		sqlMap.put("orderFinshedAllMoney",this.getResultNotNull(paymentTotal, sellerName));
		sqlMap.put("waitBuyerPay", this.getResultNotNull(waitPayCount, sellerName)); 
		sqlMap.put("wifiAllMoney",this.getResultNotNull(wapTotalMap, sellerName));
		sqlMap.put("privilegeAllMoney", this.getResultNotNull(jhsTotalMap, sellerName));
		sqlMap.put("pcAllMoney", this.getResultNotNull(taobaoTotalMap, sellerName));
		sqlMap.put("wifiCounts", this.getResultNotNull(wapCountMap, sellerName));
		sqlMap.put("pcCounts", this.getResultNotNull(taobaoCountMap, sellerName));
		sqlMap.put("nowRefund", this.getResultNotNull(refundCount, sellerName));
		sqlMap.put("waitSellerSendGoods",this.getResultNotNull(waitSellerGoodsCount, sellerName));
		sqlMap.put("itemOnsaleCounts", this.getResultNotNull(onSaleCount, sellerName));
		this.myBatisDao.execute(ShopDataStatistics.class.getName(), "doCreateByShcedule", sqlMap);
	}
	/**
	 * 取得map的集合的值不为空  如果是空 则默认为0
	 * @param oldResultMap 校验的map集合
	 * @param sellerName 要查找的key
	 */
	private Object getResultNotNull(Map<String,Object> oldResultMap,String sellerName){
		if(oldResultMap==null || sellerName==null || oldResultMap.size()==0){
			return 0;
		}else{
			if(oldResultMap.containsKey(sellerName)){
				return oldResultMap.get(sellerName);
			}else{
				return 0;
			}
		}
	}
	/**
	 * 一个字段分组结果筛选工具类
	 * @param userList 正常状态的用户集合
	 * @param oldMapList 根据用户昵称分组统计后的结果集
	 * @return 根据用户筛选后的结果集 <br>
	 *  key = countMap , value = 统计的结果集     <br>
	 *  --->key = 用户名（卖家昵称） ， value = 各用户统计的结果（COUNT） <br>
	 *  key = totalMap ，value = 求和的结果集 <br>
	 *  --->key = 用户名 （卖家昵称），value = 各用户求和的结果（SUM） <br>
	 */
	private Map<String,Map<String,Object>> filtrateResultByOneGroup(List<String> userList,List<Map<String,Object>> oldMapList,int size){
		Map<String,Object> countMap = new HashMap<String,Object>(size);
		Map<String,Object> totalMap = null;
		if(oldMapList !=null && oldMapList.size()>0){
			for(Map<String,Object> map:oldMapList){
				String mapSellerNick = String.valueOf(map.get("sellerNick"));
				if(userList.contains(mapSellerNick)){
					countMap.put(mapSellerNick, map.get("count")==null?0:map.get("count"));
					if(map.containsKey("total")){
						if(totalMap==null){
							totalMap = new HashMap<String,Object>(size);
						}
						totalMap.put(mapSellerNick, map.get("total")==null?0:map.get("total"));
					}
				}
			}
		}
		Map<String,Map<String,Object>> result = new HashMap<String,Map<String,Object>>(5);
		result.put("countMap", countMap);
		result.put("totalMap", totalMap);
		return result;
	}
	/** 
	 * 对订单来源的结果集筛选
	 * @param userList 正常状态的用户集合
	 * @param oldMapList 根据用户昵称和订单来源或状态统计后的结果集
	 * @return 筛选后的结果集<br>
	 */
	private Map<String,Map<String,Object>> filtrateResultByTradeFrom(List<String> userList,List<Map<String,Object>> oldMapList,int size){
		Map<String,Object> wapCountMap = null;
		Map<String,Object> jhsCountMap = null;
		Map<String,Object> taobaoCountMap = null;
		Map<String,Object> wapTotalMap = null;
		Map<String,Object> jhsTotalMap = null;
		Map<String,Object> taobaoTotalMap = null;
		if(oldMapList !=null && oldMapList.size()>0){
			for(Map<String,Object> map:oldMapList){
				String mapSellerNick = String.valueOf(map.get("sellerNick"));
				if(userList.contains(mapSellerNick)){
					String tradeFrom = String.valueOf(map.get("tradeFrom"));
					switch (tradeFrom) {
					case TradesInfo.ORDER_FROM_WAP:{ //无线订单
						if(map.containsKey("count")){
							if(wapCountMap==null){
								wapCountMap = new HashMap<String,Object>(size);
							}
							wapCountMap.put(mapSellerNick, map.get("count")==null?0:map.get("count"));
						}
						if(map.containsKey("total")){
							if(wapTotalMap==null){
								wapTotalMap = new HashMap<String,Object>(size);
							}
							wapTotalMap.put(mapSellerNick, map.get("total")==null?0:map.get("total"));
						}
						break;
					}
					case TradesInfo.ORDER_FROM_JHS:{ //聚划算订单
						if(map.containsKey("count")){
							if(jhsCountMap==null){
								jhsCountMap = new HashMap<String,Object>(size);
							}
							jhsCountMap.put(mapSellerNick, map.get("count")==null?0:map.get("count"));
						}
						if(map.containsKey("total")){
							if(jhsTotalMap==null){
								jhsTotalMap = new HashMap<String,Object>(size);
							}
							jhsTotalMap.put(mapSellerNick, map.get("total")==null?0:map.get("total"));
						}
						break;
					}
					case TradesInfo.ORDER_FROM_TAOBAO:{ //PC订单
						if(map.containsKey("count")){
							if(taobaoCountMap==null){
								taobaoCountMap = new HashMap<String,Object>(size);
							}
							taobaoCountMap.put(mapSellerNick, map.get("count")==null?0:map.get("count"));
						}
						if(map.containsKey("total")){
							if(taobaoTotalMap==null){
								taobaoTotalMap = new HashMap<String,Object>(size);
							}
							taobaoTotalMap.put(mapSellerNick, map.get("total")==null?0:map.get("total"));
						}
						break;
					}
					default:
						break;
					}
				}
			}
		}
		Map<String,Map<String,Object>> result = new HashMap<String,Map<String,Object>>(9);
		result.put("wapCountMap", wapCountMap);
		result.put("jhsCountMap", jhsCountMap);
		result.put("taobaoCountMap", taobaoCountMap);
		result.put("wapTotalMap", wapTotalMap);
		result.put("jhsTotalMap", jhsTotalMap);
		result.put("taobaoTotalMap", taobaoTotalMap);
		return result;
	}
	
	/**
	 * 手动刷新用户的店铺数据信息
	 * @param userNick
	 */
	public void manualStatisticsGoods(String userNick,String userStartDate) throws Exception{
		if(userNick!=null && !"".equals(userNick)){
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			if(userStartDate==null){
				Date now = new Date();
				cal.setTime(now);
				cal.add(Calendar.DATE, -1);
				now = cal.getTime();
				userStartDate = formate.format(now);
			}
			Date startDate = formate.parse(userStartDate);  //考试后时间   假设现在是 2016-11-11 11:11:11  先取出结束时间      2016-11-11 00:00:00
			cal.setTime(startDate); //结束时间加一天     得出开始时间为  2016-11-12 00:00:00
			cal.add(Calendar.DATE, 1);
			Date endDate = cal.getTime();//开始时间
			Map<String,Object> sqlMap = new HashMap<String,Object>();
			sqlMap.put("sellerName", userNick);
			sqlMap.put("sllerNick", userNick);
			sqlMap.put("startDate", startDate);
			sqlMap.put("endDate", endDate);
			doRemoveStatisticsGoods(sqlMap); 
			doCreateStatisticsGoods(sqlMap);
		}
	}
	/**
	 * 删除卖家的统计数
	 * @param sqlMap
	 */
	private void doRemoveStatisticsGoods(Map<String,Object> sqlMap){
		this.myBatisDao.execute(ShopDataStatistics.class.getName(), "doRemoveDataBySellerAndTime", sqlMap);
	}
	/**
	 * 根据时间创建卖家的统计数
	 * @param sqlMap
	 * key = sellerName ，value= 卖家昵称
	 * key = sllerNick ，value= 卖家昵称
	 * key = startDate ，value= 开始时间
	 * key = endDate ，value=  结束时间
	 */
	private void doCreateStatisticsGoods(Map<String,Object> sqlMap){
		String waitBuyerPay = null;//等待买家付款
		String nowRefund = null;//退款中
		String waitSellerSendGoods = null;//等待发货
		String orderFinshedAllMoney = null;//成交总额
		String privilegeAllMoney = null;//聚划算总额
		String itemOnsaleCounts = null;//出售中的商品数量统计
		String pcAllMoney = null;//pc成交总额
		String wifiAllMoney = null;//无线成交总额
		String pcCounts = null;//pc成交订单数
		String wifiCounts = null;//无线成交订单数
		//昨日订单总数
		Integer yesterdayOrderCounts = this.myBatisDao.findBy(TransactionOrder.class.getName(), "findAllTradeCountByManual", sqlMap);
		Map<String,Object> resultMap = null;//接收查询反馈的结果
		List<Map<String,Object>> resultList = null;//查询结果集
		Iterator<Map<String,Object>> resultIter = null;
		//统计昨日所有的订单成交交易总额
		resultMap = this.myBatisDao.findBy(TransactionOrder.class.getName(), "findTradePaymentSuccessByManual", sqlMap);
		Double successPayment = (Double)resultMap.get("total");
		orderFinshedAllMoney = successPayment==null?"0":successPayment+"";
		//根据订单的状态，统计商铺等待发货的订单数统计
		resultList = this.myBatisDao.findList(Orders.class.getName(), "findAllUserOrderCountStatusByManual", sqlMap);
		resultIter = resultList.iterator();
		while(resultIter.hasNext()){
			resultMap = resultIter.next();
			String status = (String) resultMap.get("status");
			if(TradesInfo.WAIT_SELLER_SEND_GOODS.equalsIgnoreCase(status)){
				//等待发货的订单数
				Long counts = (Long)resultMap.get("count");
				if(counts!=null){
					waitSellerSendGoods = counts.toString();
				}else{
					waitSellerSendGoods = "0";
				}
			}
		}
		//统计主订单中的等待付款的订单数
		waitBuyerPay = this.myBatisDao.findBy(TransactionOrder.class.getName(), "findAllTradeWaitPayByManual", sqlMap) ;
		//根据订单来源  查询各个渠道的订单数
		resultList = this.myBatisDao.findList(TransactionOrder.class.getName(), "findAllTradeCountOrderFromByManual", sqlMap);
		resultIter = resultList.iterator();
		while(resultIter.hasNext()){
			resultMap = resultIter.next();
			String tradeFrom = (String) resultMap.get("tradeFrom");
			if(TradesInfo.ORDER_FROM_WAP.equalsIgnoreCase(tradeFrom)){
				//无线  订单数统计
				Long counts = (Long)resultMap.get("count");
				if(counts!=null){
					wifiCounts = counts.toString();
				}else{
					wifiCounts = "0";
				}
			}else if(TradesInfo.ORDER_FROM_TAOBAO.equalsIgnoreCase(tradeFrom)){
				//pc端  订单数统计
				Long counts = (Long)resultMap.get("count");
				if(counts!=null){
					pcCounts = counts.toString();
				}else{
					pcCounts = "0";
				}
			}
		}
		//根据订单来源  查询各个渠道的交易额
		resultList = this.myBatisDao.findList(TransactionOrder.class.getName(), "findAllUserTradeCountTypeByManual", sqlMap);
		resultIter = resultList.iterator();
		while(resultIter.hasNext()){
			resultMap = resultIter.next();
			String tradeFrom = (String) resultMap.get("tradeFrom");
			if(TradesInfo.ORDER_FROM_WAP.equalsIgnoreCase(tradeFrom)){
				//无线  交易额统计
				Double sum = (Double)resultMap.get("total");
				if(sum!=null){
					wifiAllMoney = sum.toString();
				}else{
					wifiAllMoney = "0";
				}
			}else if(TradesInfo.ORDER_FROM_TAOBAO.equalsIgnoreCase(tradeFrom)){
				//pc端  交易额统计
				Double sum = (Double)resultMap.get("total");
				if(sum!=null){
					pcAllMoney = sum.toString();
				}else{
					pcAllMoney = "0";
				}
			}else if(TradesInfo.ORDER_FROM_JHS.equalsIgnoreCase(tradeFrom)){
				//聚划算  总额和订单数统计
				Double sum =(Double)resultMap.get("total");
				if(sum!=null){
					privilegeAllMoney = sum.toString();
				}else{
					privilegeAllMoney = "0";
				}
			}
		}
		//退款状态   WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意) WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货) WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货) SELLER_REFUSE_BUYER(卖家拒绝退款) CLOSED(退款关闭) SUCCESS(退款成功)
		//退款中
		Integer refundCount =  this.myBatisDao.findBy(Orders.class.getName(), "findAllUserRefundByManual", sqlMap);
		nowRefund = refundCount==null?"0":refundCount+"";
		//出售中
		Integer itemOnsaleCount = this.myBatisDao.findBy(Item.class.getName(), "findCountOnsaleByManual", sqlMap);
		itemOnsaleCounts = itemOnsaleCount==null?"0":itemOnsaleCount+"";
		sqlMap.put("waitBuyerPay", waitBuyerPay==null?"0":waitBuyerPay); 
		sqlMap.put("nowRefund", nowRefund==null?"0":nowRefund);
		sqlMap.put("waitSellerSendGoods",waitSellerSendGoods==null?"0":waitSellerSendGoods);
		sqlMap.put("orderFinshedAllMoney", orderFinshedAllMoney==null?"0":orderFinshedAllMoney);
		sqlMap.put("privilegeAllMoney", privilegeAllMoney==null?"0":privilegeAllMoney);
		sqlMap.put("itemOnsaleCounts", itemOnsaleCounts==null?"0":itemOnsaleCounts);
		sqlMap.put("pcAllMoney", pcAllMoney==null?"0":pcAllMoney);
		sqlMap.put("wifiAllMoney", wifiAllMoney==null?"0":wifiAllMoney);
		sqlMap.put("pcCounts", pcCounts==null?"0":pcCounts);
		sqlMap.put("wifiCounts", wifiCounts==null?"0":wifiCounts);
		sqlMap.put("yesterdayOrderCounts", yesterdayOrderCounts==null?"0":yesterdayOrderCounts);
		this.myBatisDao.execute(ShopDataStatistics.class.getName(), "doCreateByShcedule", sqlMap);
	}
	
	//昨日数据查询
	public List<ShopDataStatistics> findYestoday(String userId,String dataType,String bTime, String eTime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("dataType", dataType);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		List<ShopDataStatistics> shopDataStatistics1 = myBatisDao.findList("s2jh.biz.shop.crm.data.entity.ShopDataStatistics", "findYesTeday", map);
		return shopDataStatistics1;
		
	}
	
	//前日数据查询
	public List<ShopDataStatistics> findBeforeYestoday(String userId,String dataType,String bTime, String eTime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("dataType", dataType);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		List<ShopDataStatistics> shopDataStatistics = myBatisDao.findList("s2jh.biz.shop.crm.data.entity.ShopDataStatistics", "findBeforeYestoday", map);
		return shopDataStatistics;
	}
	
	//历史数据查询
	public List<ShopDataStatistics> findHistoryData(String userId,String dataType,String bTime, String eTime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("dataType", dataType);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		StringBuilder params = new StringBuilder();
		if (bTime != null) {
			params.append("&beginTime=").append(bTime.toString());
		}
		if (eTime != null) {
			params.append("&endTime=").append(eTime.toString());

		}
		List<ShopDataStatistics> shopDataStatistics2 = myBatisDao.findList("s2jh.biz.shop.crm.data.entity.ShopDataStatistics", "findhistoryData", map);
		return shopDataStatistics2;
	}
	
}
