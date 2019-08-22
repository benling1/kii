package s2jh.biz.shop.crm.order.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.taobao.api.SecretException;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.security.ErrorUtil;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDaoT;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.order.entity.TransactionOrder;
import s2jh.biz.shop.crm.order.pojo.TbTransactionOrder;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;



/**
 * 查询Trade
 * 
 * @author Administrator
 *
 */
@Service
public class TransactionTradeService extends
		BaseService<TransactionOrder, Long> {

//	@Autowired
//	private TradeRepository tradeRepository;
	
	@Resource(name="mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MyBatisDaoT myBatisDaoT;

	@Autowired
	private JudgeUserUtil judgeUserUtil;
//	
//	@Autowired
//	private OrderService orderService;
//	
//	@Autowired
//	private MemberInfoService memberInfoService;

	@Override
	protected BaseDao<TransactionOrder, Long> getEntityDao() {
		return null;
	}

	private static final Log logger = LogFactory.getLog(TransactionTradeService.class);

	/**
	 * 根据tid查询Trade
	 * 
	 * @param tid
	 * @return
	 */
	public Trade queryTrade(String tid) {
		// 定义查询到的数据
		Trade trade = null;
		// 根据tid查询到TbTransactionOrder(自定义对象)
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tid", tid);
		TbTransactionOrder tbTransactionOrder = myBatisDaoT.findBy(
				TbTransactionOrder.class.getName(), "getTbTransactionOrders",
				map);
		if (tbTransactionOrder != null
				&& !"".equals(tbTransactionOrder.getJdpResponse())) {
			// 解析JdpResponse
			String jdpResponse = tbTransactionOrder.getJdpResponse();
			TradeFullinfoGetResponse rsp = null;
			try {
				rsp = TaobaoUtils.parseResponse(jdpResponse,
						TradeFullinfoGetResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 不为空
			if (rsp != null) {
				// 获取到Trade
				trade = rsp.getTrade();
			}
		}
		if(trade!=null){
			String sessionKey = judgeUserUtil.getUserTokenByRedis(trade.getSellerNick());
			try {
				if(EncrptAndDecryptClient.isEncryptData(trade.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
					if(sessionKey == null){
						return null;
					}
					//判断买家昵称是否为密文，如果是则解密
					trade.setBuyerNick(judgeUserUtil.getDecryptData(trade.getBuyerNick(), EncrptAndDecryptClient.SEARCH, null, sessionKey));
					//判断买家手机号是否为密文，如果是则解密
					trade.setReceiverMobile(judgeUserUtil.getDecryptData(trade.getReceiverMobile(), EncrptAndDecryptClient.PHONE,null, sessionKey));
					//判断买家座机是否为密文，如果是则解密
					trade.setReceiverPhone(judgeUserUtil.getDecryptData(trade.getReceiverPhone(), EncrptAndDecryptClient.SIMPLE, null, sessionKey));
					//判断买家姓名是否为密文，如果是则解密
					trade.setReceiverName(judgeUserUtil.getDecryptData(trade.getReceiverName(), EncrptAndDecryptClient.SEARCH,null, sessionKey));
					//判断收货人街道地址是否为密文，如果是则解密
					trade.setReceiverAddress(judgeUserUtil.getDecryptData(trade.getReceiverAddress(), EncrptAndDecryptClient.SEARCH,null, sessionKey));
				}
			} catch (SecretException e) {
//				logger.error("tid:"+trade.getTid()+"  卖家昵称："+trade.getSellerNick()+"  sessionKey："+sessionKey);
//				if(ErrorUtil.isInvalidSession(e)) {
//					logger.error("用户sessionKey失效");
			        // 标记该sessionkey无效，重新授权之前不要再调用
//			    }else{
//			    	logger.error("解密出错啦,请直接呼叫wy");
//			    }
//				logger.error("e.getErrCode() "+e.getErrCode() +"\r e.getErrMsg():"+e.getErrMsg() +"\r e.getMessage():"+e.getMessage() + "\r e.getSubErrMsg():"+e.getSubErrMsg());
//				e.printStackTrace();
				return null;
			}
		}
		return trade;
	}
//
//	/**
//	 * taobao.trades.sold.get (查询卖家已卖出的交易数据（根据创建时间）) 根据map查询订单集合
//	 * 
//	 * @param map
//	 * @return List<Orders>
//	 */
//	public List<Orders> queryOrderList(Map<String, Object> map) {
//		// 新建一个List集合
//		List<Orders> list = new ArrayList<Orders>();
//		String orderScopeOne = (String) map.get("orderScopeOne");
//		String orderScopeTwo = (String) map.get("orderScopeTwo");
//		UserInfo user = (UserInfo) map.get("user");
//		if (user != null) {
//			TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
//					taobaoInfo.appKey, taobaoInfo.appSecret);
//			TradesSoldGetRequest req = new TradesSoldGetRequest();
//			// 设置需要返回的字段列表
//			req.setFields("oid,tid,cid,num_iid,item_meal_id,sku_id,refund_id,bind_oid,item_meal_name,pic_path,seller_nick,buyer_nick,refund_status,outer_iid,snapshot_url,snapshot,timeout_action_time,buyer_rate,seller_rate,seller_type,sub_order_tax_fee,sub_order_tax_rate,status,title,type,iid,price,num,outer_sku_id,order_from,total_fee,payment,discount_fee,adjust_fee,modified,sku_properties_name,is_oversold,is_service_order,end_time,consign_time,order_attr,shipping_type,logistics_company,invoice_no,is_daixiao,divide_order_fee,part_mjz_discount,ticket_outer_id,ticket_expdate_key,store_code,is_www,tmser_spu_code,bind_oids,zhengji_status,md_qualification,md_fee,customization,inv_type,is_sh_ship,shipper,f_type,f_status,F_TERM,COMBO_ID,ASSEMBLY_RELA,ASSEMBLY_PRICE,ASSEMBLY_ITEM,receiver_district,receiver_city,step_trade_status,created,receiver_name,receiver_mobile,buyer_flag,seller_flag");
//			// 设置开始时间
//			req.setStartCreated(DateUtils.convertStringToDate(orderScopeOne));
//			// 设置结束时间
//			req.setEndCreated(DateUtils.convertStringToDate(orderScopeTwo));
//			// 设置状态 等待卖家发货
//			req.setStatus("WAIT_SELLER_SEND_GOODS");
//
//			TradesSoldGetResponse rsp = null;
//			try {
//				rsp = client.execute(req, user.getAccess_token());
//			} catch (ApiException e) {
//				e.printStackTrace();
//			}
//			List<Trade> trades = rsp.getTrades();
//			// 如果集合不为空，遍历
//			if (trades != null && trades.size() > 0) {
//				for (Trade trade : trades) {
//					// 获取到订单列表
//					List<Order> orderList = trade.getOrders();
//					for (Order order : orderList) {
//						// 新建一个Orders对象
//						Orders orders = new Orders();
//						BeanUtils.copyProperties(order, orders);
//						orders.setSellerNick(trade.getSellerNick());
//						orders.setBuyerNick(trade.getBuyerNick());
//						orders.setTid(trade.getTid().toString());
//						orders.setReceiverDistrict(trade.getReceiverDistrict());
//						// 设置省份
//						orders.setReceiverCity(trade.getReceiverCity());
//						orders.setStepTradeStatus(trade.getStepTradeStatus());
//						orders.setCreated(trade.getCreated());
//						orders.setReceiverName(trade.getReceiverName());
//						orders.setReceiverMobile(trade.getReceiverMobile() == null ? trade
//								.getReceiverPhone() : trade.getReceiverMobile());
//						orders.setBuyerFlag(trade.getBuyerFlag() == null ? null
//								: trade.getBuyerFlag().intValue());
//						orders.setSellerFlag(trade.getSellerFlag() == null ? null
//								: trade.getSellerFlag().intValue());
//						// 后续补加字段
//						orders.setModified(trade.getModified());
//						// 将orders对象添加到集合中
//						list.add(orders);
//					}
//
//				}
//			}
//
//		}
//		return list;
//	}
//
//	@Autowired
//	private TransactionOrderService transactionOrderService;
//
//	/**
//	 * @Title: loadTbTradeData
//	 * @Description: TODO(订单数据同步, 使用线程池 查询出对象使用key/value
//	 *               key-tid,value-exception)
//	 * @param:
//	 * @return: void
//	 * @throws
//	 * @date: 2017年3月28日 下午7:59:04
//	 * @author: jackstraw_yu
//	 */
//	public void loadTbTradeData() {
//		// 构建上一个小时的时间内容
//		Calendar calendar = Calendar.getInstance();
//		Date endTime = calendar.getTime();
//		calendar.add(Calendar.MINUTE, -10);
//		Date beginTime = calendar.getTime();
//
//		// 1查询出聚石塔内数据(当前时间的上一个小时的总条数)
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("beginTime", beginTime);
//		hashMap.put("endTime", endTime);
//
//		Long rspCount = myBatisDaoT.findBy(TbTransactionOrder.class.getName(),
//				"rspCountEnhance", hashMap);
//		logger.info("===========================================查询出" + rspCount
//				+ "条=========================================");
//
//		// 每页显示的条数
//		Long pageSize = ConstantUtils.RSP_PAGE_SIZE;
//		Long pageNum = (rspCount + pageSize) / pageSize;
//		hashMap.put("pageSize", pageSize);
//		List<TbTransactionOrder> rsps = null;
//		ExecutorService threadPool = TransactionThreadPool.getTradeThreadPool();
//		int start = 0;
//		List<TradeThread> threadList = new ArrayList<TradeThread>();
//		while (start < pageNum) {
//			hashMap.put("startRows", start * pageSize);
//			rsps = myBatisDaoT.findList(TbTransactionOrder.class.getName(),
//					"queryTbTrades", hashMap);
//			logger.info("======================第" + (start + 1)
//					+ "次分发或者追加===================="
//					+ (rsps == null ? 0 : rsps.size())
//					+ "条数据=====================");
//			TradeThread tradeThread = null;
//			start++;
//			if (Math.floor(start * 1.0 / 50) == 0) {
//				tradeThread = new TradeThread();
//			} else {
//				tradeThread = threadList.get(start % 50);
//			}
//			tradeThread.appendRspList(rsps);
//			threadList.add(tradeThread);
//		}
//		for (TradeThread t : threadList) {
//			threadPool.execute(t);
//		}
//	}
//
//	/**
//	 * @Title: timeLoadTbTradeData
//	 * @Description: TODO(定时任务:订单数据同步, 使用队列保存数据并将异常信息以key/value tid/String
//	 *               保存到数据库)
//	 * @param 设定文件
//	 * @return void 返回类型
//	 * @throws
//	 * @date 2017年4月17日 下午2:09:08
//	 * @author jackstraw_yu
//	 */
//	public void timeLoadTbTradeData() {
//		Calendar calendar = Calendar.getInstance();
//		Date endTime = calendar.getTime();
//		calendar.add(Calendar.MINUTE, -10);
//		Date beginTime = calendar.getTime();
//
//		// 从数据库中查询出上次任务结束的时间,如果上次任务时间与本次任务时间相差大于10分钟,任务中间有宕机,一上次任务结束时间为起始时间
//		Date lastEndTime = myBatisDao.findBy(Orders.class.getName(),
//				"getTaskEndTime", null);
//		if (lastEndTime != null
//				&& beginTime.getTime() - lastEndTime.getTime() >= 10 * 60 * 1000)
//			beginTime = lastEndTime;
//
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("beginTime", beginTime);
//		hashMap.put("endTime", endTime);
//		Long rspCount = myBatisDaoT.findBy(TbTransactionOrder.class.getName(),
//				"rspCountEnhance", hashMap);
//		logger.info("===========================================查询出" + rspCount
//				+ "条=========================================");
//		Long pageSize = ConstantUtils.RSP_PAGE_SIZE;
//		Long pageNum = 1l;
//		if(rspCount/pageSize==0){
//			pageNum =1l;
//		}else if(rspCount%pageSize==0){
//			pageNum = rspCount / pageSize;
//		}else{
//			pageNum = (rspCount + pageSize) / pageSize;
//		}
//		hashMap.put("pageSize", pageSize);
//		int start = 0;
//		while (start < pageNum) {
//			hashMap.put("startRows", start * pageSize);
//			List<TbTransactionOrder> rsps = myBatisDaoT.findList(TbTransactionOrder.class.getName(),
//					"queryTbTrades", hashMap);
//			logger.info("======================第" + (start + 1)
//					+ "次分发或者追加===================="
//					+ (rsps == null ? 0 : rsps.size())
//					+ "条数据=====================");
//			start++;
//			if (null == rsps || rsps.size() == 0)
//				break;
//			try {
//				// 放入队列,异步处理
//				TransactionQueue.tradeQueue.put(rsps);
//			} catch (Exception e) {
//				e.printStackTrace();
//				continue;
//			}
//		}
//		// 保存或者更新结束时间到数据库
//		hashMap.put("taskEndTime", endTime);
//		try {
//			logger.info("*************时间为："
//					+ endTime
//					+ "**************更新定时任务时间节点———————开始***********************");
//			myBatisDao.execute(Orders.class.getName(), "updateTaskEndTime",
//					hashMap);
//			logger.info("***************************更新定时任务时间节点———————结束***********************");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @Title: repairTbTradeData
//	 * @Description: TODO(备注:补充之前未同步过来的订单数据,后期可以不用或者过时; 定时任务,每天夜晚A点到B点
//	 *               ,每隔C分钟执行一次任务,定量查询10000条数据; 使用队列处理数据 在数据库定义一个任务的节点)
//	 * @param:
//	 * @return: void
//	 * @throws
//	 * @date: 2017年4月17日 下午2:09:08
//	 * @author: jackstraw_yu
//	 */
//	public void repairTbTradeData() {
//		// 查询出的任务节点
//		long node = myBatisDao.findBy(Orders.class.getName(), "getTaskNode",
//				null);
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("pageSize", 400);
//		List<TbTransactionOrder> rsps = null;
//		int start = 0;
//		// 每次定时任务转换1万条数据
//		while (start < 100) {
//			hashMap.put("node", node);
//			rsps = myBatisDaoT.findList(TbTransactionOrder.class.getName(),
//					"repairTbTradeData", hashMap);
//			logger.info("======================第" + (start + 1)
//					+ "次分发或者追加===================="
//					+ (rsps == null ? 0 : rsps.size())
//					+ "条数据=====================");
//			if (null == rsps || rsps.size() == 0)
//				break;
//			try {
//				// 放入队列,异步处理
//				TransactionQueue.tradeQueue.put(rsps);
//			} catch (Exception e) {
//				e.printStackTrace();
//				continue;
//			}
//			start++;
//			node += 400;
//		}
//		// 将节点保存到数据库
//		hashMap.put("node", node);
//		try {
//			myBatisDao.execute(Orders.class.getName(), "updateTaskNode",
//					hashMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @Title: doRepairTbData
//	 * @Description: TODO(服务一初始化执行)
//	 * @param 设定文件
//	 * @return void 返回类型
//	 * @throws
//	 * @date 2017年4月20日 下午5:14:35
//	 * @author jackstraw_yu
//	 */
//	public void doRepairTbData() {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				for (;;) {
//					try {
//						// 循环一次线程休眠十分钟
//						Thread.sleep(10 * 60 * 1000);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					repairTbTradeData();
//				}
//			}
//		}).start();
//	}
//	
//	/**
//	 * 邱洋 2017-05-10 16:11
//	 * @param map
//	 * @return
//	 */
//	@Transactional
//	public List<Orders> findOrderList(Map<String, Object> map) {
//		// 新建一个List集合
//		List<Orders> list = new ArrayList<Orders>();
//		UserInfo user = (UserInfo) map.get("user");
//		if (user != null) {
//			map.put("userId", user.getTaobaoUserNick());
//			map.put("OrderStatus", "WAIT_SELLER_SEND_GOODS");
//			list = myBatisDao.findList(Orders.class.getName(), "findOrderList",
//					map);
//		}
//		return list;
//	}
//	
//	/**
//	 * 根据tid查询trade
//	 * ZTK2017年6月6日下午12:15:11
//	 */
//	public TransactionOrder findTradeByTid(String tid){
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("tid",tid);
//		TransactionOrder trade = myBatisDao.findBy(TransactionOrder.class.getName(), "findTradeById", map);
//		return trade;
//	}
//
//	/**
//	 * 营销中心效果分析，根据买家手机号或姓名查询trade
//	 * ZTK2017年6月8日下午5:17:02
//	 */
//	@Deprecated
//	public List<TransactionOrder> findTradeByBuyer(String userId,String phone,Date bTime,Date eTime,String type,String orderSource){
//		Map<String ,Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("phone",phone);
//		map.put("bTime",bTime);
//		map.put("eTime",eTime);
//		map.put("type",type);
//		map.put("orderSource",orderSource);
//		List<TransactionOrder> tradeList = myBatisDao.findList(TransactionOrder.class.getName(), "findTradeByBuyer", map);
//		List<TransactionOrder> resultList = new ArrayList<TransactionOrder>();
//		if(orderSource != null && !"".equals(orderSource) && !"0".equals(orderSource)){
//			if(tradeList != null && tradeList.size() > 0){
//				for (TransactionOrder trade : tradeList) {
//					if(trade.getTradeFrom().contains(orderSource)){
//						resultList.add(trade);
//					}
//				}
//			}
//			return resultList;
//		}else {
//			return tradeList;
//		}
//	}
//	
//	/**
//	 * 会员短信群发效果分析查询下单总客户数，总金额，订单总数，商品总数
//	 */
//	@Deprecated
//	public Map<String,Object> findTotalOrderNum(String userId,List<String> phoneList,Date bTime,
//			Date eTime,String orderSource,List<String> statusList){
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("phoneList", phoneList);
//		map.put("bTime", bTime);
//		map.put("eTime", eTime);
//		map.put("orderSource", orderSource);
//		map.put("statusList", statusList);
//		Map<String,Object> resultMap = myBatisDao.findBy(TransactionOrder.class.getName(), "findTotalOrderNum", map);
//		return resultMap;
//	}
//	
	
}
