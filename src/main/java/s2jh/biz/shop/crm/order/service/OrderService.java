package s2jh.biz.shop.crm.order.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.BaseService;

import org.activiti.engine.impl.transformer.StringToDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.manage.dao.TradeRepository;
import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.message.dao.SmsTemplateDao;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;
import s2jh.biz.shop.crm.order.dao.OrdersDao;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.order.entity.OrdersList;
import s2jh.biz.shop.crm.order.entity.TransactionOrder;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.vo.OrdersVo;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class OrderService extends BaseService<Orders, Long> {
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private TradeRepository tradeRepository;
	
//	@Autowired
//	private SmsTemplateDao smsTemplateDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private UserOperationLogDao userOperationLogDao;
		
	@Override
	protected BaseDao<Orders, Long> getEntityDao() {
		return ordersDao;
	}
	
//	//短信发送详情，查询短信发送时间
//	@Autowired
//	private SmsSendRecordService smsSendRecordService;
	
	/**
	 * 调用操作日志方法保存操作日志
	 */
	public void saveUserOperationLog(UserOperationLog op) {
		//保存操作日志
		userOperationLogDao.save(op);
	}
	
//	/**
//	 * 根据卖家查询条件查询订单列表
//	 * @param contextPath
//	 * @param oVo
//	 * @param pageNo
//	 * @param bTime
//	 * @return
//	 */
//	public Map<String,Object> findOrdersBycondition(OrdersVo oVo,Integer pageNo,Date eTime,String userId,List<String> orderList){
//		//设置起始页
//		if(pageNo == null){
//			pageNo = 1;
//		}
//		//设置每页显示条数为5
//		Integer currentRows = 10;
//		//计算查询初始行数
//		Integer startRows = (pageNo - 1) * currentRows;
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//			try {
//				/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");*/
//				if(oVo.getTid() != null && !"".equals(oVo.getTid())){
//					map.put("tid", oVo.getTid());
//				}
//				if(oVo.getBuyerNick() != null && !"".equals(oVo.getBuyerNick())){
//					map.put("buyerNick", oVo.getBuyerNick());
//				}
//				if(oVo.getStartOrderDate() != null && !"".equals(oVo.getStartOrderDate())){
//					map.put("startOrderDate",DateUtils.convertDate(oVo.getStartOrderDate()));
//				}
//				if(oVo.getEndOrderDate() != null && !"".equals(oVo.getEndOrderDate())){
//					map.put("endOrderDate",DateUtils.convertDate(oVo.getEndOrderDate()));
//				}
//				if(oVo.getConsignTimeBefore() != null && !"".equals(oVo.getConsignTimeBefore())){
//					map.put("consignTimeBefore",DateUtils.convertDate(oVo.getConsignTimeBefore()));
//				}
//				if(oVo.getConsignTimeAfter() != null && !"".equals(oVo.getConsignTimeAfter())){
//					map.put("consignTimeAfter", DateUtils.convertDate(oVo.getConsignTimeAfter()));
//				}
//				if(oVo.getOrderFrom() != null && !"".equals(oVo.getOrderFrom())){
//					map.put("orderFrom", oVo.getOrderFrom());
//				}
//				if(oVo.getRefundStatus() != null && !"".equals(oVo.getRefundStatus())){
//					map.put("refundStatus", oVo.getRefundStatus());
//				}
//				if(oVo.getPaymentBefore() != null && !"".equals(oVo.getPaymentBefore())){
//					map.put("paymentBefore", Double.parseDouble(oVo.getPaymentBefore()));
//				}
//				if(oVo.getPaymentAfter() != null && !"".equals(oVo.getPaymentAfter())){
//					map.put("paymentAfter", Double.parseDouble(oVo.getPaymentAfter()));
//				}
//				if(oVo.getReceiverDistrict() != null && !"".equals(oVo.getReceiverDistrict())){
//					map.put("receiverDistrict", oVo.getReceiverDistrict());
//				}
//				if(oVo.getEndTimeBefore() != null && !"".equals(oVo.getEndTimeBefore())){
//					map.put("endTimeBefore", DateUtils.convertDate(oVo.getEndTimeBefore()));
//				}
//				if(oVo.getEndTimeAfter() != null && !"".equals(oVo.getEndTimeAfter())){
//					map.put("endTimeAfter", DateUtils.convertDate(oVo.getEndTimeAfter()));
//				}
//				if(oVo.getStepTradeStatus() != null && !"".equals(oVo.getStepTradeStatus())){
//					map.put("stepTradeStatus", oVo.getStepTradeStatus());
//				}
//				if(oVo.getNumIidList() != null && !"".equals(oVo.getNumIidList())){
//					map.put("numIidList", oVo.getNumIidList());
//				}
//				//订单状态
//				if("CLOSED".equals(oVo.getStatus())){
//					map.put("status1", "TRADE_CLOSED");
//					map.put("status2", "TRADE_CLOSED_BY_TAOBAO");
//				}else if(oVo.getStatus() == null || "".equals(oVo.getStatus())){
//					map.put("status", null);
//				}else if("ALL".equals(oVo.getStatus()) || "TOTAL".equals(oVo.getStatus())){
//					map.put("status", null);
//				}else{
//					map.put("status", oVo.getStatus());
//				}
//				if("双方已评价".equals(oVo.getRateStatus())){
//					map.put("buyerRate", 1);
//					map.put("sellerRate", 1);
//					map.put("status", "TRADE_FINISHED");
//				}else if("买家未评".equals(oVo.getRateStatus())){
//					map.put("buyerRate", 0);
//					map.put("sellerRate", null);
//					map.put("status", "TRADE_FINISHED");
//				}else if("卖家未评".equals(oVo.getRateStatus())){
//					map.put("sellerRate", 0);
//					map.put("buyerRate", null);
//					map.put("status", "TRADE_FINISHED");
//				}else if("买家已评，卖家未评".equals(oVo.getRateStatus())){
//					map.put("buyerRate", 1);
//					map.put("sellerRate", 0);
//					map.put("status", "TRADE_FINISHED");
//				}else if("买家未评，卖家已评".equals(oVo.getRateStatus())){
//					map.put("buyerRate", 0);
//					map.put("sellerRate", 1);
//					map.put("status", "TRADE_FINISHED");
//				}
//				map.put("tid", oVo.getTid());
//				//地区
//				List<String> stateList = null;
//				if(oVo.getReceiverState() != null && !"".equals(oVo.getReceiverState())){
//					String[] stateArr = oVo.getReceiverState().split(",");
//					stateList = new ArrayList<String>();
//					for(int i = 0;i < stateArr.length; i++){
//						stateList.add(stateArr[i]);
//					}
//					if(stateList == null || stateList.size() == 0){
//						stateList = null;
//					}
//				}
//				if(userId != null && !"".equals(userId)){
//					map.put("sellerNick", userId);
//				}
//				map.put("orderList",orderList);
//				map.put("stateList", stateList);
//				//订单标识
//				List<Integer> sellerFlagList = new ArrayList<Integer>();
//				if(oVo.getSellerFlagStr() != null && !"".equals(oVo.getSellerFlagStr())){
//					String[] sellerFlags = (oVo.getSellerFlagStr() + "").split(",");
//					for (int i = 0; i < sellerFlags.length; i++) {
//						sellerFlagList.add(Integer.parseInt(sellerFlags[i]));
//					}
//					map.put("sellerFlagList", sellerFlagList);
//				}else{
//					sellerFlagList = null;
//				}
//				
//				if(startRows != null && !"".equals(startRows)){
//					map.put("startRows",startRows);
//				}else{
//					map.put("startRows",0);
//				}
//				if(currentRows != null && !"".equals(currentRows)){
//					map.put("currentRows", currentRows);
//				}else{
//					map.put("currentRows", 10);
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//查询满足条件的所有结果（分页）
//			/*TransactionOrder transactionOrder = myBatisDao.findBy(TransactionOrder.class.getName(), "isExistTransactionOrder", map); 
//			System.out.println(transactionOrder.getId());
//			System.out.println(transactionOrder.getOrderList().size());*/
//			List<TransactionOrder> limitList = myBatisDao.findList(TransactionOrder.class.getName(), "findOrdersBycondition", map);
//			double pageCount = limitList.size() / 10;
//			List<TransactionOrder> rsultLimitList = new ArrayList<TransactionOrder>();
//			if(limitList != null){
//				if(startRows == Math.floor(pageCount) * 10){
//					rsultLimitList = limitList.subList((int)pageCount * 10, limitList.size());
//				}else {
//					rsultLimitList = limitList.subList(startRows, startRows + 10);
//				}
//			}
//			//查询满足条件的所有订单（不分页）
//			/*Map<String, Object> orderMap = new HashMap<String,Object>();
//			orderMap.putAll(map);
//			orderMap.put("startRows", null);
//			orderMap.put("currentRows", null);*/
//			//所得结果
//			/*List<TransactionOrder> list = myBatisDao.findList(TransactionOrder.class.getName(), "findOrdersBycondition", orderMap);
//			int count = list.size();*/
//			if(limitList != null && limitList.size() > 0){
//				for (TransactionOrder order : limitList) {
//					if(order.getTid() == null || "".equals(order.getTid())){
//						order.setTid("");
//					}
//					if(order.getBuyerNick() == null || "".equals(order.getBuyerNick())){
//						order.setBuyerNick("");
//					}
//					if(order.getReceiverMobile() == null || "".equals(order.getReceiverMobile())){
//						order.setReceiverMobile("");
//					}
//				}
//			}
//			//分页插件填充pagination
//			Pagination pagination = new Pagination(pageNo, currentRows, limitList.size(), rsultLimitList);
//			/*if(pagination.getList() == null || pagination.getList().size() == 0){
//				pagination = null;
//			}*/
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			resultMap.put("list", limitList);
//			resultMap.put("pagination", pagination);
//			return resultMap;
//	}
	/**
	 * 已停止使用的方法
	 * 滑静
	 * @param oVo
	 * @param pageNo
	 * @param eTime
	 * @param userId
	 * @param orderList
	 * @return
	 */
//	public Map<String,Object> findOrdersBycondition(OrdersVo oVo,Integer pageNo,Date eTime,String userId,List<String> orderList){
//		//设置起始页
//		if(pageNo == null){
//			pageNo = 1;
//		}
//		//设置每页显示条数为5
//		Integer currentRows = 10;
//		//计算查询初始行数
//		Integer startRows = (pageNo - 1) * currentRows;
//		
//		Pageination<TradeDTO> page = new Pageination<TradeDTO>();
//		
//		Map<String, Object> map = new HashMap<String, Object>();
////		TradeDTO oVo = new TradeDTO();
//		Query query = new Query();
//			try {
//				/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");*/
//				if(oVo.getTid() != null && !"".equals(oVo.getTid())){
//					query.addCriteria(Criteria.where("tid").is(oVo.getTid()));
//				}
//				if(oVo.getBuyerNick() != null && !"".equals(oVo.getBuyerNick())){
//					query.addCriteria(Criteria.where("buyerNick").is(oVo.getBuyerNick()));
//				}
//				if(oVo.getStartOrderDate() != null && !"".equals(oVo.getStartOrderDate()) && oVo.getEndOrderDate() != null && !"".equals(oVo.getEndOrderDate())){
//					query.addCriteria(Criteria.where("createdUTC").gte(lab.s2jh.core.util.DateUtils.stringToDate(oVo.getStartOrderDate(), DateUtils.DETAIL_DATE_FORMAT)).lte(lab.s2jh.core.util.DateUtils.stringToDate(oVo.getEndOrderDate(), DateUtils.DETAIL_DATE_FORMAT)));
//				}
//				if(oVo.getConsignTimeBefore() != null && !"".equals(oVo.getConsignTimeBefore()) && oVo.getConsignTimeAfter() != null && !"".equals(oVo.getConsignTimeAfter())){
////					query.addCriteria(Criteria.where("consignTime").gte(Long.parseLong(oVo.getConsignTimeBefore())).lte(Long.parseLong(oVo.getConsignTimeAfter())));
//					query.addCriteria(Criteria.where("consignTimeUTC").gte(lab.s2jh.core.util.DateUtils.stringToDate(oVo.getConsignTimeBefore(), DateUtils.DETAIL_DATE_FORMAT)).lte(lab.s2jh.core.util.DateUtils.stringToDate(oVo.getConsignTimeAfter(), DateUtils.DETAIL_DATE_FORMAT)));
//				}
//				if(oVo.getOrderFrom() != null && !"".equals(oVo.getOrderFrom())){
//					String[] split = oVo.getOrderFrom().split(",");
//					for (String string : split) {
//						query.addCriteria(Criteria.where("tradeFrom").is(string));
//					}
//				}
//				if(oVo.getRefundStatus() != null && !"".equals(oVo.getRefundStatus())){
//					query.addCriteria(Criteria.where("refundStatus").is(oVo.getRefundStatus()));
//				}
//				if(oVo.getPaymentBefore() != null && !"".equals(oVo.getPaymentBefore()) && oVo.getPaymentAfter() != null && !"".equals(oVo.getPaymentAfter())){
//					query.addCriteria(Criteria.where("payment").gte(Double.parseDouble(oVo.getPaymentBefore())).lte(Double.parseDouble(oVo.getPaymentAfter())));
//				}
//				
//				if(oVo.getReceiverDistrict() != null && !"".equals(oVo.getReceiverDistrict())){
//					query.addCriteria(Criteria.where("receiverDistrict").is(oVo.getReceiverDistrict()));
//				}
//				if(oVo.getEndTimeBefore() != null && !"".equals(oVo.getEndTimeBefore()) && oVo.getEndTimeAfter() != null && !"".equals(oVo.getEndTimeAfter())){
//					query.addCriteria(Criteria.where("endTime").gte(Long.parseLong(oVo.getEndTimeBefore())).lte(Long.parseLong(oVo.getEndTimeAfter())));
//				}
//				
//				if(oVo.getStepTradeStatus() != null && !"".equals(oVo.getStepTradeStatus())){
//					query.addCriteria(Criteria.where("stepTradeStatus").is(oVo.getStepTradeStatus()));
//				}
//				
//				//NUM_IID    
//				if(oVo.getNumIidList() != null && !"".equals(oVo.getNumIidList())){
//					query.addCriteria(Criteria.where("orders.numIid").regex(".*?" + oVo.getNumIidList() + ".*"));
//				}
//				//订单状态
//				if("CLOSED".equals(oVo.getStatus())){
//					query.addCriteria(Criteria.where("status").is("TRADE_CLOSED"));
//					query.addCriteria(Criteria.where("status").is("TRADE_CLOSED_BY_TAOBAO"));
//				}else if(oVo.getStatus() == null || "".equals(oVo.getStatus())){
////					query.addCriteria(Criteria.where("status").is(null));
//				}else if("ALL".equals(oVo.getStatus()) || "TOTAL".equals(oVo.getStatus())){
////					query.addCriteria(Criteria.where("status").is(null));
//				}else{
//					query.addCriteria(Criteria.where("status").is(oVo.getStatus()));
//				}
//				if("双方已评价".equals(oVo.getRateStatus())){
//					query.addCriteria(Criteria.where("buyerRate").is(1));
//					query.addCriteria(Criteria.where("sellerRate").is(1));
//					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				}else if("买家未评".equals(oVo.getRateStatus())){
//					query.addCriteria(Criteria.where("buyerRate").is(0));
////					query.addCriteria(Criteria.where("sellerRate").is(null));
//					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				}else if("卖家未评".equals(oVo.getRateStatus())){
//					query.addCriteria(Criteria.where("sellerRate").is(0));
////					query.addCriteria(Criteria.where("buyerRate").is(null));
//					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				}else if("买家已评，卖家未评".equals(oVo.getRateStatus())){
//					query.addCriteria(Criteria.where("buyerRate").is(1));
//					query.addCriteria(Criteria.where("sellerRate").is(0));
//					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				}else if("买家未评，卖家已评".equals(oVo.getRateStatus())){
//					query.addCriteria(Criteria.where("buyerRate").is(0));
//					query.addCriteria(Criteria.where("sellerRate").is(1));
//					query.addCriteria(Criteria.where("status").is("TRADE_FINISHED"));
//				}
//				query.addCriteria(Criteria.where("tid").is(oVo.getTid()));
//				//地区
//				List<String> stateList = null;
//				if(oVo.getReceiverState() != null && !"".equals(oVo.getReceiverState())){
//					String[] stateArr = oVo.getReceiverState().split(",");
//					stateList = new ArrayList<String>();
//					for(int i = 0;i < stateArr.length; i++){
//						stateList.add(stateArr[i]);
//					}
//					if(stateList == null || stateList.size() == 0){
//						stateList = null;
//					}
//				}
//				if(userId != null && !"".equals(userId)){
//					query.addCriteria(Criteria.where("sellerNick").is(userId));
//				}
//				if(orderList != null && !"".equals(orderList)){
//					query.addCriteria(Criteria.where("tid").nin(orderList));
//				}
//				if(orderList != null && !"".equals(orderList)){
//					query.addCriteria(Criteria.where("receiverState").in(stateList));
//				}
//				//订单标识
//				List<Integer> sellerFlagList = new ArrayList<Integer>();
//				if(oVo.getSellerFlagStr() != null && !"".equals(oVo.getSellerFlagStr())){
//					String[] sellerFlags = (oVo.getSellerFlagStr() + "").split(",");
//					for (int i = 0; i < sellerFlags.length; i++) {
//						sellerFlagList.add(Integer.parseInt(sellerFlags[i]));
//					}
//					query.addCriteria(Criteria.where("sellerFlag").in(sellerFlagList));
//				}else{
//					sellerFlagList = null;
//				}
//				
//				if(startRows != null && !"".equals(startRows)){
//					page.setPageNo(startRows);
//				}else{
//					page.setPageNo(0);
//				}
//				if(currentRows != null && !"".equals(currentRows)){
//					page.setPageSize(currentRows);
//				}else{
//					page.setPageSize(10);
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//查询满足条件的所有结果（分页）
////			List<TradeDTO> limitList = tradeRepository.find(query, userId);
//			Pageination<TradeDTO> pagination = tradeRepository.findPage(page, query, userId);
//			Map<String, Object> resultMap = new HashMap<String, Object>();
////			resultMap.put("list", limitList);
//			resultMap.put("pagination", pagination);
//			return resultMap;
//	}
	
		/**
		 * 根据条件获取所有的短信模板
		 * @param type
		 * @param pageNo
		 * @param contextPath
		 * @return
		 */
		public Pagination findAllSmsTemplate(String type,Integer pageNo,String contextPath){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", type);
			//设置起始页
			if(pageNo==null){
				pageNo =1;	
			}
			//先设置每页显示的条数为5条
			Integer currentRows = 5;
			//计算出起始行数
			Integer startRows = (pageNo-1)*currentRows;		
			
			//计算出总页数
			Integer totalCount= countSmsTemplate(map);
			
			map.put("startRows", startRows);
			map.put("currentRows", currentRows);
			List<SmsTemplate> list = myBatisDao.findList(SmsTemplate.class.getName(), "findAll", map);
			//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
			//smsSendRecordList:每页显示的list集合或者当前页显示的list集合
			Pagination pagination = new Pagination(pageNo, currentRows, totalCount, list);
			
			StringBuilder params = new StringBuilder();
			if(type!=null){
				params.append("&type=").append(type);
			}
			//拼接分页的后角标中的跳转路径与查询的条件
			String url = contextPath+"/crms/marketingCenter/list";
			pagination.pageView(url, params.toString());
			return pagination;
		}
		
		/**
		 * 根据条件获取短信模板的总数
		 * @param map
		 * @return
		 */
		public int countSmsTemplate(Map<String,Object> map){
			int count = myBatisDao.findBy(SmsTemplate.class.getName(), "findSmsTemplateCount", map);
			return count;
		}

		
		
//		/**
//		* @Title: queryOrdersPagination
//		* @Description: TODO(调用service层查询出pagination列表)
//		* @param @param contextPath
//		* @param @param pageNo
//		* @param @param map
//		* @param @return    参数
//		* @return Pagination    返回类型
//		* @throws
//		*/
//		@SuppressWarnings("unchecked")
//		public Pagination queryOrdersPagination(String contextPath,Integer pageNo, Map<String, Object> map) {
//			//取出相关参数
//			String timeType = null;
//			String beginTime = null;
//			String endTime = null;
//			String buyerNick = null;
//			String orderId = null;
//			String status =null;
//			String userId = null;
//			ArrayList<String> orderFrom = null;
//			//String [] orderFrom = null;
//			//封装params
//			StringBuilder params = new StringBuilder();
//			if(map !=null && map.size()>0){
//				if(map.get("timeType")!=null && !"".equals(map.get("timeType"))){
//					timeType = (String) map.get("timeType");
//					params.append("&timeType=").append(timeType);
//				}
//				if(map.get("beginTime")!=null && !"".equals(map.get("beginTime"))){
//					beginTime = (String) map.get("beginTime");
//					params.append("&beginTime=").append(beginTime);
//				}
//				if(map.get("endTime")!=null && !"".equals(map.get("endTime"))){
//					endTime = (String) map.get("endTime");
//					params.append("&endTime=").append(endTime);
//				}
//				if(map.get("buyerNick")!=null && !"".equals(map.get("buyerNick"))){
//					buyerNick = (String) map.get("buyerNick");
//					params.append("&buyerNick=").append(buyerNick);
//				}
//				if(map.get("orderId")!=null && !"".equals(map.get("orderId"))){
//					orderId = (String) map.get("orderId");
//					params.append("&orderId=").append(orderId);
//				}
//				if(map.get("status")!=null && !"".equals(map.get("status"))){
//					status = (String) map.get("status");
//					params.append("&status=").append(status);
//				}
//				if(map.get("orderFrom")!=null){
//					orderFrom = (ArrayList<String>) map.get("orderFrom");
//					//orderFrom = (String []) map.get("orderFrom");
//					if(orderFrom.size()>0){
//						for(String s:orderFrom){
//							params.append("&orderFrom=").append(s);
//						}
//					}
//				}
//				if(map.get("userId")!=null){
//					userId = (String) map.get("userId");
//					params.append("&userId=").append(userId);
//				}
//			}
//			
//			
//			
//			Map<String,Object> hashmap = new HashMap<String,Object>();
//				//判断要检索的时间类型
//			if(timeType !=null){
//				if(timeType.equals("created")){
//					hashmap.put("b_created", beginTime);
//					hashmap.put("e_created", endTime);
//				}
//				else if(timeType.equals("payTime")){
//					hashmap.put("b_payTime", beginTime);
//					hashmap.put("e_payTime", endTime);
//				}
//				else if(timeType.equals("consignTtime")){
//					hashmap.put("b_consignTtime", beginTime);
//					hashmap.put("e_consignTtime", endTime);
//				}
//				else if(timeType.equals("modified")){
//					hashmap.put("b_modified", beginTime);
//					hashmap.put("e_modified", endTime);
//				}
//				else if(timeType.equals("endTime")){
//					hashmap.put("b_endTime", beginTime);
//					hashmap.put("e_endTime", endTime);
//				}
//			}
//			
//				//将订单状态转换为数组
//				String[] statusArry = null;
//				if(status !=null && !"".equals(status)){
//					statusArry= status.split(",");
//				}
//				hashmap.put("buyerNick", buyerNick);
//				hashmap.put("orderId", orderId);
//				hashmap.put("status", statusArry);
//				hashmap.put("orderFrom", orderFrom);
//				hashmap.put("userId", userId);
//			
//				/**
//				 * 联表查询
//				 * */
//			//获得总条数
//			Integer totalCount = queryOrdersCount(hashmap);
//				//获得分页
//			//先设置每页显示的条数为5条
//			Integer currentRows = 5;
//			//计算出起始行数
//			Integer startRows = (pageNo-1)*currentRows;	
//			hashmap.put("startRows", startRows);
//			hashmap.put("currentRows", currentRows);
//			//联查三张表:CRM_ORDERS,CRM_TRADE,CRM_SHOP_DATA 获得自定义对象OrdersList
//			List<OrdersList> list = myBatisDao.findList(Orders.class.getName(), "queryOrdersPagination", hashmap);
//			//手机号中间4位模糊处理
//			for (OrdersList ol : list) {
//				if(ol.getReceiverMobile() !=null){
//					ol.setReceiverMobile(ol.getReceiverMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
//				}
//			}
//			//判空
//			if(list!=null&&list.size()>0){
//				Map<String, Object> hashMap = new HashMap<String, Object>();
//				//联查CRM_PRODUCT,CRM_ITEM 
//				for(OrdersList  o :list){
//					if(o.getItemId()!=null){
//						hashMap.put("itemId", o.getItemId());
//							OrdersList orders =	myBatisDao.findBy(Orders.class.getName(), "queryOrdersItem", hashMap);
//							if(orders !=null){
//								o.setItemName(orders.getItemName());
//								o.setProductName(orders.getProductName());
//							}
//						}
//					}
//			}
//			//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
//			Pagination pagination = new Pagination(pageNo, currentRows, totalCount, list);
//			pagination.pageView(contextPath, params.toString());	
//			return pagination;
//		}
		
//		/**
//		* @Title: queryOrdersCount
//		* @Description: TODO(调用service层查询出pagination的总条数)
//		* @param @param hashmap
//		* @param @return    参数
//		* @return Integer    返回类型
//		* @author:jackstraw_yu
//		* @throws
//		*/
//		public Integer queryOrdersCount(Map<String,Object> hashmap){
//			Long l = myBatisDao.findBy(Orders.class.getName(), "queryOrdersCount",hashmap);
//			Integer i = Integer.valueOf(l.toString());
//			return i;
//		}
		
		/**
		 * 根据条件查询所需的所有数据集合
		 */
//		public List<Orders> findOrdersByQuery(String startOrderDate,String endOrderDate,String status,String userId){
//			Map<String, Object> map = new HashMap<String,Object>();
//			map.put("startOrderDate", startOrderDate);
//			map.put("endOrderDate", endOrderDate);
//			map.put("status", status);
//			map.put("userId", userId);
//			List<Orders> findList = myBatisDao.findList(Orders.class.getName(), "findOrdersToDelayRemind", map);
//					
//			return findList;
//		}
		
//		public List<OrdersDTO> findOrdersByQuery(String startOrderDate,String endOrderDate,String status,String userId){
//			Query query = new Query();
//			List<OrdersDTO> findList = null;
//			try {
//				if(startOrderDate != null && !"".equals(startOrderDate) && endOrderDate != null && !"".equals(endOrderDate)){
////					query.addCriteria(Criteria.where("created").gte(lab.s2jh.core.util.DateUtils.stringToLong(startOrderDate, lab.s2jh.core.util.DateUtils.DEFAULT_TIME_FORMAT)).lte(lab.s2jh.core.util.DateUtils.stringToLong(endOrderDate, lab.s2jh.core.util.DateUtils.DEFAULT_TIME_FORMAT)));
//					query.addCriteria(Criteria.where("createdUTC").gte(lab.s2jh.core.util.DateUtils.stringToDate(startOrderDate, lab.s2jh.core.util.DateUtils.DEFAULT_DATE_FORMAT)).lte(lab.s2jh.core.util.DateUtils.stringToDate(endOrderDate, lab.s2jh.core.util.DateUtils.DEFAULT_DATE_FORMAT)));
//				}
//				if(status != null && !"".equals(status)){
//					query.addCriteria(Criteria.where("status").is(status));
//				}
//				if(userId != null && !"".equals(userId)){
//					query.addCriteria(Criteria.where("sellerNick").is(userId));
//				}
//				List<TradeDTO> list = tradeRepository.find(query, userId);
//				findList = new ArrayList<OrdersDTO>();
//				for (TradeDTO tradeDTO : list) {
//					List<OrdersDTO> orders = tradeDTO.getOrders();
//					for (OrdersDTO ordersDTO : orders) {
//						findList.add(ordersDTO);
//					}
//				}
//			} catch (ParseException e) {
//				e.printStackTrace();
//				throw new RuntimeException(e);
//			}
//			return findList;
//		}
		
//		/**
//		 * 按照时间升序查询未评价订单列表
//		 * @throws ParseException 
//		 */
//		public Pagination findOrdersByCreatedAsc(Integer pageNo,Map<String, Object> map) throws ParseException{
//			//设置每页显示的条数为5条
//			Integer currentRows = 10;
//			//计算出起始行数
//			Integer startRows = (pageNo-1)*currentRows;
//			//查询集合中的总个数
//			int totalCount = myBatisDao.findBy(Orders.class.getName(), "findOrdersByCreatedAscCount", map);
//			map.put("startRows", startRows);
//			map.put("currentRows", currentRows);
//			List<Orders> findList = myBatisDao.findList(Orders.class.getName(), "findOrdersByCreatedAsc", map);
//			for (Orders orders : findList) {
//				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				if(orders.getEndTime() != null && !"".equals(orders.getEndTime())){
//					orders.setRateOverDate(fmt.parse(DateUtils.getTimeByDay(fmt.format(orders.getEndTime()), 15)));
//				}
//			}
//			Pagination pagination = new Pagination(pageNo, currentRows, totalCount, findList);
//			return pagination;
//		}
//		
//		/**
//		 * 通过订单id查询电话号码======================
//		 */
//		public Orders queryPhoneByOrderId(String orderId) {
//			Map<String, Object> map = new HashMap<String,Object>();
//			map.put("orderId", orderId);
//			Orders orders = myBatisDao.findBy(Orders.class.getName(), "queryPhoneByOrderId", map);
//			return orders;
//		}
		
		/**
//		 * 根据订单id更新订单中卖家评价为已评价
//		 * ZTK2017年1月6日下午1:58:18
//		 */
//		public boolean updateSellerRate(Map<String, Object> map){
//			int updateCount = myBatisDao.execute(Orders.class.getName(), "updateOrdersById", map);
//			if(updateCount == 1){
//				return true;
//			}else {
//				return false;
//			}
//		}

		/**
		 * @param userId 
		* @Title: queryRefundNicks
		* @Description: TODO(查询出退过款的客户 的昵称集合)
		* @param @return    参数
		* @return List<String>    返回类型
		* @author:jackstraw_yu
		* @throws
		*/
//		public List<String> queryRefundNicks(String userId) {
//			HashMap<String, Object> hashMap = new HashMap<String,Object>();
//			hashMap.put("userId", userId);
//			return myBatisDao.findList(Orders.class.getName(), "queryRefundNicks", hashMap);
//		}
		
//		/**
//		 * 通过oid查询orders
//		 * ZTK2017年2月27日下午4:16:47
//		 */
//		public Orders findOrderByOid(String oid){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("oid",oid);
//			Orders order = myBatisDao.findBy(Orders.class.getName(), "findOrderByOid", map);
//			return order;
//		}

		
//		/*
//		 * 保存数据
//		 */
//		public void insertOrder(Orders order) {
//			myBatisDao.execute(Orders.class.getName(), "insertOrder", order);
//		}

		
//		/**
//		 * 通过tid查询order数据是否存在
//		 */
//		public Integer queryOrdersToCount(String tid) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("tid",tid);
//			Integer i = myBatisDao.findBy(Orders.class.getName(), "queryOrdersToCount", map);
//			return i;
//		}
		
//		/**
//		  * 创建人：邱洋
//		  * @Title: 根据商品编号查询购买过该商品的客户昵称
//		  * @date 2017年4月10日--上午10:28:41 
//		  * @return List<String>
//		  * @throws
//		 */
//		public List<String> getMemberNickList(String itemIds[],String userId){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("userId",userId);
//			map.put("itemIds",itemIds);
//			List<String> list = myBatisDao.findList(Orders.class.getName(), "getMemberInfoList", map);
//			return list;
//		}
		
//		/**
//		 * 根据主订单查询子订单
//		 */
//		public List<Orders> findRefundStasus(String tid){	
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("tid", tid);
//			List<Orders> ordersList = myBatisDao.findList(Orders.class.getName(), "findRefundStasus", map);
//			return ordersList;
//		}
		
//		/**
//		 * 通过tid查询对应的orderList
//		 * ZTK2017年6月13日下午1:56:27
//		 */
//		public List<Orders> findOrderByTid(String tid){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("tid", tid);
//			List<Orders> ordersList = myBatisDao.findList(Orders.class.getName(), "findOrderByTid", map);
//			return ordersList;
//		}
		
		
//		/**
//		 * 创建人：邱洋
//		 * @title 根据oid修改order表中订单的退款状态
//		 * @DATE 2017-06-13 10:39
//		 * @param list
//		 */
//		public void updateOrdersRefundStatus(List<Orders> list){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("orderList",list);
//			myBatisDao.execute(Orders.class.getName(),"updateOrdersRefundStatus", map);
//		}
//		
//		/**
//		 * 根据tid查询对应子订单所有的商品数量 
//		 * ZTK2017年6月23日下午6:56:03
//		 */
//		@Deprecated
//		public int findNumsByTid(List<String> tidList){
//			Map<String,Object> map= new HashMap<String, Object>();
//			map.put("tidList", tidList);
//			int nums = myBatisDao.findBy(Orders.class.getName(), "findNumsByTid", map);
//			return nums;
//		}
		
//		/**
//		 * 会员短信群发效果分析查询下单总客户数，总金额，订单总数，商品总数
//		 */
//		@Deprecated
//		public Orders findTotalOrderNum(String userId,List<String> phoneList,Date bTime,
//				Date eTime,String orderSource,List<String> statusList,String refundStatus){
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("userId", userId);
//			map.put("phoneList", phoneList);
//			map.put("bTime", bTime);
//			map.put("eTime", eTime);
//			map.put("orderSource", orderSource);
//			map.put("statusList", statusList);
//			map.put("refundStatus", refundStatus);
//			Orders orders = myBatisDao.findBy(Orders.class.getName(), "findTotalOrderNum", map);
//			return orders;
//		}
		
//		/**
//		 * 会员短信群发效果分析统计每天成交总客户数，成交总金额，成交订单总数，成交商品总数 
//		 */
//		@Deprecated
//		public Orders findSuccessOrderNum(String userId,
//				String orderSource,List<String> statusList,Date successDay){
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("userId", userId);
//			map.put("successDay", successDay);
//			map.put("orderSource", orderSource);
//			map.put("statusList", statusList);
//			Orders orders = myBatisDao.findBy(Orders.class.getName(), "findSuccessOrderNum", map);
//			return orders;
//		}
		
//		/**
//		 * 会员短信群发客户详情
//		 * @param userId
//		 * @param orderSource
//		 * @param buyerNick
//		 * @param phone
//		 * @param itemId
//		 * @param customerType
//		 * @return
//		 */
//		@Deprecated
//		public Pagination memberCustomerDetail(String userId,String orderSource,String buyerNick,
//				String phone,String itemId,String customerType,Integer pageNo){
//			Map<String,Object> map = new HashMap<>();
//			//设置每页显示的条数为5条
//			Integer currentRows = 10;
//			//计算出起始行数
//			Integer startRows = (pageNo-1)*currentRows;
//			List<String> statusList = null;
//			List<String> refundStatusList = null;
//			switch (customerType) {
//			case "total":
//				break;
//			case "success":
//				statusList = new ArrayList<>();
//				statusList.add("TRADE_FINISHED");
//				statusList.add("WAIT_SELLER_SEND_GOODS");
//				statusList.add("WAIT_BUYER_CONFIRM_GOODS");
//				statusList.add("TRADE_BUYER_SIGNED");
//				break;
//			case "wait":
//				statusList = new ArrayList<>();
//				statusList.add("WAIT_BUYER_PAY");
//				statusList.add("TRADE_CLOSED");
//				break;
//			case "fail":
//				refundStatusList = new ArrayList<>();
//				refundStatusList.add("SUCCESS");
//				break;
//			default:
//				break;
//			}
//			
//			map.put("userId", userId);
//			map.put("orderSource", orderSource);
//			map.put("buyerNick", buyerNick);
//			map.put("phone", phone);
//			map.put("itemId", itemId);
//			map.put("statusList", statusList);
//			map.put("refundStatusList", refundStatusList);
//			List<Orders> orderList = myBatisDao.findList(Orders.class.getName(), "memberCustomerDetail", map);
//			Pagination pagination = new Pagination(pageNo, currentRows, orderList.size(), orderList);
//			
//			return pagination;
//		}
}

