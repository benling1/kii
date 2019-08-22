package s2jh.biz.shop.crm.historyOrder.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.util.DateUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.historyOrder.util.ImportUtils;
import s2jh.biz.shop.crm.historyOrder.web.OrderHistoryImportController;
import s2jh.biz.shop.crm.item.service.ItemImportService;
import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SynchronousTradeHelper;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;

@Service
public class ImportThreadService {
	private static final Log logger = LogFactory.getLog(ImportThreadService.class);
	@Autowired
	private OrderHistoryImportService orderHistoryImportService;
	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private SynchronousTradeHelper synchronousTradeHelper;
	@Autowired
	private TradeInfoService tradeInfoService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	@Autowired
	private ItemImportService itemImportService;
	/**
	 * 使用线程添加数据 helei 2017年3月24日上午10:17:52
	 * 
	 * @throws Exception
	 */
	public void threadImport(List<String[]> datasList,
			Map<String, Integer> headerMap, String userId,
			Long historyImportId,Map<String,List<String>> itemTitleMap){
		
		//保存mysql方法
//		this.threadImportIsMysql(datasList,headerMap,userId,historyImport);
		
		//保存mongo方法
		this.threadImportIsMongo(datasList,headerMap,userId,historyImportId,itemTitleMap);
	}
	
	
	/**
	 * 将数据导入到mongo=============================mongo========================================
	 */
	private void threadImportIsMongo(List<String[]> datasList,
			Map<String, Integer> headerMap, String userId,
			Long historyImportId,Map<String,List<String>> itemTitleMap) {
		
		//删除商品list
		Set<Long> itemIds = new HashSet<Long>();
		
		OrderHistoryImportController.table.put(Thread.currentThread().getName(), userId);
		//定义异常保存的数据
		Map<String,Object> exceptionMap = new HashMap<String ,Object>();
		String exception = null;

		//创建list封装TradeDTO对象，订单保存
		List<TradeDTO> tradeListToSave = new ArrayList<TradeDTO>();
		
		
			//处理条数	//成功条数	 //重复数量	
		int rsCount=0,successCount=0,repetitionNumber=0;
		for (int i = 0; i < datasList.size(); i++) {
			rsCount ++;
			//创建list封装OrdersDTO对象，订单保存
			List<OrdersDTO> orderListToSave= new ArrayList<OrdersDTO>();
			
			// 创建TradeDTO对象封装参数
			TradeDTO trade = new TradeDTO();
			// 创建OrdersDTO对象封装数据
			OrdersDTO order = new OrdersDTO();

			String tid = ImportUtils.getValFromArray(headerMap, datasList.get(i),
					"订单编号");
			String newTid = "";
			if (tid != null && !tid.equals("")) {
				newTid = ImportUtils.getTid(tid);
				boolean judgeOrderId = ImportUtils.judgeOrderId(newTid);
				if(judgeOrderId){
					trade.setTid(newTid);
					order.setOid(newTid);
					order.setTid(newTid);
				}else{
					logger.error("失败----订单编号格式错误："+tid);
					continue;
				}
			}else{
				logger.error("失败----订单编号为空！！！");
				continue;
			}
			String buyerNick = ImportUtils.getValFromArray(headerMap,
					datasList.get(i), "买家会员名");
			if (buyerNick != null && !buyerNick.equals("")) {
				trade.setBuyerNick(buyerNick);
				order.setBuyerNick(buyerNick);
			}
			String price = ImportUtils.getValFromArray(headerMap, datasList.get(i),
					"买家应付货款");
			if (price != null && !price.equals("")) {
				trade.setPrice(new BigDecimal( price).doubleValue());
				order.setPrice(new BigDecimal( price).doubleValue());
			}
			String postFee = ImportUtils.getValFromArray(headerMap, datasList.get(i),
					"买家应付邮费");
			if (postFee != null && !postFee.equals("")) {
				trade.setPostFee(postFee);

			}
			String totalFee = ImportUtils.getValFromArray(headerMap, datasList.get(i),
					"总金额");
			if (totalFee != null && !totalFee.equals("")) {
				trade.setTotalFee(new BigDecimal( totalFee).doubleValue());
				order.setTotalFee(new BigDecimal( totalFee).doubleValue());
			}
			String payment = ImportUtils.getValFromArray(headerMap, datasList.get(i),
					"买家实际支付金额");
			if (payment != null && !payment.equals("")) {
				double parseDouble = Double.parseDouble(payment);
				trade.setPayment(parseDouble);
				order.setPayment(parseDouble);
			}
			String status = ImportUtils.getValFromArray(headerMap, datasList.get(i),
					"订单状态");
			String cause = ImportUtils.getValFromArray(headerMap, datasList.get(i),
					"订单关闭原因");
			if (status != null && !status.equals("")) {
				String getdeal = ImportUtils.getdeal(status,cause);
				trade.setStatus(getdeal);
				order.setStatus(getdeal);
			}
			String buyerMessage = ImportUtils.getValFromArray(headerMap,
					datasList.get(i), "买家留言");
			if (buyerMessage != null && !buyerMessage.equals("")) {
				trade.setBuyerMessage(buyerMessage);
			}
			String receiverName = ImportUtils.getValFromArray(headerMap,
					datasList.get(i), "收货人姓名");
			if (receiverName != null && !receiverName.equals("")) {
				trade.setReceiverName(receiverName);
				order.setReceiverName(receiverName);
			}
			String receiverAddress = ImportUtils.getValFromArray(headerMap,
					datasList.get(i), "收货地址");
			if (receiverAddress != null && !receiverAddress.equals("")) {
				String city = ImportUtils.getCity(receiverAddress);
				String provinces = ImportUtils.getProvinces(receiverAddress);
				trade.setReceiverAddress(receiverAddress);
				trade.setReceiverCity(city);
				trade.setReceiverState(provinces);
				order.setReceiverCity(city);
			}
			String receiverPhone = ImportUtils.getValFromArray(headerMap,
					datasList.get(i), "联系电话");
			if (receiverPhone != null && !receiverPhone.equals("")) {
				trade.setReceiverPhone(ImportUtils.getPhone(receiverPhone));
			}
			String receiverMobile = ImportUtils.getValFromArray(headerMap,
					datasList.get(i), "联系手机");
			if (receiverMobile != null && !receiverMobile.equals("")) {
				String phone = ImportUtils.getPhone(receiverMobile);
				trade.setReceiverMobile(phone);
				order.setReceiverMobile(phone);
			}
			String created = ImportUtils.getValFromArray(headerMap, datasList.get(i),"订单创建时间");
			if (created != null && !created.equals("")) {
				try {
					trade.setCreatedUTC(ImportUtils.timeFormat(created, ImportUtils.DATE_FORMAT_ONE));
					trade.setCreated(DateUtils.stringToLong(created, ImportUtils.DATE_FORMAT_ONE));
					order.setCreatedUTC(ImportUtils.timeFormat(created, ImportUtils.DATE_FORMAT_ONE));
					order.setCreated(DateUtils.stringToLong(created, ImportUtils.DATE_FORMAT_ONE));
				} catch (Exception e) {
					try {
						trade.setCreatedUTC(ImportUtils.timeFormat(created, ImportUtils.DATE_FORMAT_TWO));
						trade.setCreated(DateUtils.stringToLong(created, ImportUtils.DATE_FORMAT_TWO));
						order.setCreatedUTC(ImportUtils.timeFormat(created, ImportUtils.DATE_FORMAT_TWO));
						order.setCreated(DateUtils.stringToLong(created, ImportUtils.DATE_FORMAT_TWO));
					} catch (Exception e1) {
						logger.error("失败----订单创建时间格式错误："+created+"，正确格式：yyyy-MM-dd HH:mm:ss");
						continue;
					}
				}
			}
			String payTime = ImportUtils.getValFromArray(headerMap, datasList.get(i),"订单付款时间");
			if (payTime != null && !payTime.equals("")) {
				try {
					trade.setPayTimeUTC(ImportUtils.timeFormat(payTime, ImportUtils.DATE_FORMAT_ONE));
					trade.setPayTime(DateUtils.stringToLong(payTime, ImportUtils.DATE_FORMAT_ONE));
				} catch (ParseException e) {
					try {
						trade.setPayTimeUTC(ImportUtils.timeFormat(payTime, ImportUtils.DATE_FORMAT_TWO));
						trade.setPayTime(DateUtils.stringToLong(payTime, ImportUtils.DATE_FORMAT_TWO));
					} catch (ParseException e1) {
						logger.error("失败----订单付款时间格式错误："+payTime+"，正确格式：yyyy-MM-dd HH:mm:ss");
						continue;
					}
				}
			}
			String omnichannelParam = ImportUtils.getValFromArray(headerMap,
					datasList.get(i), "宝贝标题");
			if (omnichannelParam != null && !omnichannelParam.equals("")) {
				order.setTitle(omnichannelParam);
				trade.setOmnichannelParam(omnichannelParam);
				if (null != itemTitleMap && itemTitleMap.size() > 0
						&& itemTitleMap.containsKey(omnichannelParam.trim())) {
						trade.setNUM_IID(itemTitleMap.get(omnichannelParam.trim()).get(0));
						order.setNumIid(Long.parseLong(itemTitleMap.get(omnichannelParam.trim()).get(0)));
					}
			}
			String num = ImportUtils.getValFromArray(headerMap, datasList.get(i),
					"宝贝总数量");
			if (num != null && !num.equals("")) {
				trade.setNum(Long.parseLong(num));
				order.setNum(Long.parseLong(num));
			}
			String sellerNick = ImportUtils.getValFromArray(headerMap,
					datasList.get(i), "店铺名称");
			if (sellerNick != null && !sellerNick.equals("")) {
				trade.setTitle(sellerNick);
			}
			// 设置userId
			trade.setSellerNick(userId);
			order.setSellerNick(userId);

			// 给type字段复制import--标识是导入的订单数据
			trade.setType("import");
			trade.setTradeFrom("import");
			order.setType("import");
			order.setOrderFrom("import");
			trade.setSellerFlag("0");
			order.setSellerFlag(0);
			trade.setNodeFlag(1l);
			// 通过tid查询是否有数据
			TradeDTO tradeDTO = tradeInfoService.findOneByTid(newTid, userId);
			// 调用方法保存订单数据
			if (tradeDTO == null) {
				if (trade != null && order != null) {
					try {
						//保存对象到list中
						orderListToSave.add(order);
						trade.setOrders(orderListToSave);
						tradeListToSave.add(trade);
						successCount++;//成功记录
					} catch (Exception e) {
						logger.error("失败----保存订单异常："+e.getMessage());
						// 解析异常,继续下次循环
						try {
							exception = e.getMessage();
							exceptionMap.put(newTid,(exception.length()>50000)?exception.substring(0, exception.length()/2):exception);
							//保存异常信息
							this.insertExceptionMap(exceptionMap);
						} catch (Exception e1) {
							continue;
						}
						continue;
					}
				}
			}else{
				repetitionNumber++;
				logger.error("失败----订单编号重复："+newTid);
				List<String> list = itemTitleMap.get(omnichannelParam.trim());
				if (null != list && list.size() > 1 && null != list.get(1)
						&& "import".equals(list.get(1))) {
					itemIds.add(order.getNumIid());
				}
			}
			//如果订单重复就删除该订单的商品
			if (itemIds != null && itemIds.size() > 0
					&& itemIds.size() % 500 == 0) {
				itemImportService.batchDeleteImportItems(itemIds);
			}
			
			
			
			
			
			
			//当数量到达设定数量时,执行更新操作
			if (i % 1000 == 0&&i != 0) {
				//批量保存订单数据
				try {
					//获取卖家token
					String token = this.getUserToken(userId);
					List<TradeDTO> encryptTradeListData = EncrptAndDecryptClient.getInstance().encryptTradeListData(tradeListToSave, token);
					synchronousTradeHelper.handleAferCreateData(encryptTradeListData, userId);
					tradeListToSave = new ArrayList<TradeDTO>();
				} catch (Exception e) {
					successCount=0;
					logger.error("失败----保存订单失败："+e.getMessage());
				}
				
				//修改总记录
				try {
					orderHistoryImportService.updateImportHistory(historyImportId,rsCount,successCount,repetitionNumber);
					rsCount=0;successCount=0;repetitionNumber=0;
				} catch (Exception e) {
					logger.error("失败----修改总记录："+e.getMessage());
				}
			}
			
		}
		
		//当所有循环完成后在执行更新操作
		if(rsCount != 0){
			//批量保存订单数据
			try {
				//获取卖家token
				String token = this.getUserToken(userId);
				List<TradeDTO> encryptTradeListData = EncrptAndDecryptClient.getInstance().encryptTradeListData(tradeListToSave, token);
				synchronousTradeHelper.handleAferCreateData(encryptTradeListData, userId);
				tradeListToSave = new ArrayList<TradeDTO>();
			} catch (Exception e) {
				successCount=0;
				logger.error("失败----保存订单失败："+e.getMessage());
			}
			
			//修改总记录
			try {
				orderHistoryImportService.updateImportHistory(historyImportId,rsCount,successCount,repetitionNumber);
				rsCount=0;successCount=0;repetitionNumber=0;
			} catch (Exception e) {
				logger.error("失败----修改总记录："+e.getMessage());
			}
		}
		
		//如果订单重复就删除该订单的商品
		if (itemIds != null && itemIds.size() > 0) {
			itemImportService.batchDeleteImportItems(itemIds);
		}
		
		
		OrderHistoryImportController.table.remove(Thread.currentThread().getName());
	}


	
	/**
	 * 将数据导入mysql==================================mysql====================================
	 */
//	private void threadImportIsMysql(List<String[]> datasList,
//			Map<String, Integer> headerMap, String userId,
//			OrderHistoryImport historyImport) {
//
//		//定义异常保存的数据
//		Map<String,Object> exceptionMap = new HashMap<String ,Object>();
//		String exception = null;
//		
//		//创建list对象封装TransactionOrderd对象，会员分离
//		List<TransactionOrder> tradeList = new ArrayList<TransactionOrder>();
//		
//		//创建list封装TransactionOrder对象，订单保存
//		List<TransactionOrder> tradeListToSave = new ArrayList<TransactionOrder>();
//		//创建list封装Order对象，订单保存
//		List<Orders> orderListToSave= new ArrayList<Orders>();
//		
//		// 时间格式化
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			//处理条数	//成功条数		
//		int rsCount=0,successCount=0;
//		for (int i = 0; i < datasList.size(); i++) {
//			rsCount ++;
//			// 创建TransactionOrderd对象封装参数
//			TransactionOrder trade = new TransactionOrder();
//			// 创建Orders对象封装数据
//			Orders order = new Orders();
//
//			String tid = this.getValFromArray(headerMap, datasList.get(i),
//					"订单编号");
//			String newTid = "";
//			if (tid != null && !tid.equals("")) {
//				newTid = this.getTid(tid);
//				boolean judgeOrderId = this.judgeOrderId(newTid);
//				if(judgeOrderId){
//					trade.setTid(newTid);
//					order.setOid(newTid);
//					order.setTid(newTid);
//				}else{
//					logger.error("失败----订单编号格式错误："+tid);
//					continue;
//				}
//			}else{
//				logger.error("失败----订单编号为空！！！");
//				continue;
//			}
//			String buyerNick = this.getValFromArray(headerMap,
//					datasList.get(i), "买家会员名");
//			if (buyerNick != null && !buyerNick.equals("")) {
//				trade.setBuyerNick(buyerNick);
//				order.setBuyerNick(buyerNick);
//			}
//			String price = this.getValFromArray(headerMap, datasList.get(i),
//					"买家应付货款");
//			if (price != null && !price.equals("")) {
//				trade.setPrice(price);
//				order.setPrice(price);
//			}
//			String postFee = this.getValFromArray(headerMap, datasList.get(i),
//					"买家应付邮费");
//			if (postFee != null && !postFee.equals("")) {
//				trade.setPostFee(postFee);
//
//			}
//			String totalFee = this.getValFromArray(headerMap, datasList.get(i),
//					"总金额");
//			if (totalFee != null && !totalFee.equals("")) {
//				trade.setTotalFee(totalFee);
//				order.setTotalFee(totalFee);
//			}
//			String payment = this.getValFromArray(headerMap, datasList.get(i),
//					"买家实际支付金额");
//			if (payment != null && !payment.equals("")) {
//				double parseDouble = Double.parseDouble(payment);
//				trade.setPayment(parseDouble);
//				order.setPayment(parseDouble);
//			}
//			String status = this.getValFromArray(headerMap, datasList.get(i),
//					"订单状态");
//			if (status != null && !status.equals("")) {
//				String getdeal = getdeal(status);
//				trade.setStatus(getdeal);
//				order.setStatus(getdeal);
//			}
//			String buyerMessage = this.getValFromArray(headerMap,
//					datasList.get(i), "买家留言");
//			if (buyerMessage != null && !buyerMessage.equals("")) {
//				trade.setBuyerMessage(buyerMessage);
//			}
//			String receiverName = this.getValFromArray(headerMap,
//					datasList.get(i), "收货人姓名");
//			if (receiverName != null && !receiverName.equals("")) {
//				trade.setReceiverName(receiverName);
//				order.setReceiverName(receiverName);
//			}
//			String receiverAddress = this.getValFromArray(headerMap,
//					datasList.get(i), "收货地址");
//			if (receiverAddress != null && !receiverAddress.equals("")) {
//				String city = getCity(receiverAddress);
//				trade.setReceiverAddress(receiverAddress);
//				trade.setReceiverCity(city);
//				order.setReceiverCity(city);
//			}
//			String receiverPhone = this.getValFromArray(headerMap,
//					datasList.get(i), "联系电话");
//			if (receiverPhone != null && !receiverPhone.equals("")) {
//				trade.setReceiverPhone(getPhone(receiverPhone));
//			}
//			String receiverMobile = this.getValFromArray(headerMap,
//					datasList.get(i), "联系手机");
//			if (receiverMobile != null && !receiverMobile.equals("")) {
//				String phone = getPhone(receiverMobile);
//				trade.setReceiverMobile(phone);
//				order.setReceiverMobile(phone);
//			}
//			String created = this.getValFromArray(headerMap, datasList.get(i),"订单创建时间");
//			if (created != null && !created.equals("")) {
//				try {
//					trade.setCreated(sdf.parse(created));
//					order.setCreated(sdf.parse(created));
//				} catch (Exception e) {
//					logger.error("失败----订单创建时间格式错误："+created+"，正确格式：yyyy-MM-dd HH:mm:ss");
//					continue;
//				}
//			}
//			String payTime = this.getValFromArray(headerMap, datasList.get(i),"订单付款时间");
//			if (payTime != null && !payTime.equals("")) {
//				try {
//					trade.setPayTime(sdf.parse(payTime));
//				} catch (ParseException e) {
//					logger.error("失败----订单付款时间格式错误："+payTime+"，正确格式：yyyy-MM-dd HH:mm:ss");
//					continue;
//				}
//			}
//			String omnichannelParam = this.getValFromArray(headerMap,
//					datasList.get(i), "宝贝标题");
//			if (omnichannelParam != null && !omnichannelParam.equals("")) {
//				order.setTitle(omnichannelParam);
//				trade.setOmnichannelParam(omnichannelParam);
//			}
//			String num = this.getValFromArray(headerMap, datasList.get(i),
//					"宝贝总数量");
//			if (num != null && !num.equals("")) {
//				trade.setNum(num);
//				order.setNum(Long.parseLong(num));
//			}
//			String sellerNick = this.getValFromArray(headerMap,
//					datasList.get(i), "店铺名称");
//			if (sellerNick != null && !sellerNick.equals("")) {
//				trade.setTitle(sellerNick);
//			}
//			// 设置userId
//			trade.setSellerNick(userId);
//			order.setSellerNick(userId);
//
//			// 给type字段复制import--标识是导入的订单数据
//			trade.setType("import");
//			trade.setTradeFrom("import");
//			order.setType("import");
//			order.setOrderFrom("import");
//
//			// 通过tid查询是否有数据
//			Integer orderCount = orderService.queryOrdersToCount(newTid);
//			Integer tradeCount = transactionOrderService.queryTransactionOrderCount(newTid);
//			// 调用方法保存订单数据
//			if (orderCount == 0 && tradeCount==0) {
//				if (trade != null && order != null) {
//					try {
//						//单条保存
//						/*this.insertOrder(order);
//						this.insertTransactionOrde(trade);*/
//						
//						//保存对象到list中
//						orderListToSave.add(order);
//						tradeListToSave.add(trade);
//						
//						successCount++;
//						tradeList.add(trade);
//					} catch (Exception e) {
//						logger.error("失败----保存订单异常："+e.getMessage());
//						// 解析异常,继续下次循环
//						try {
//							exception = e.getMessage();
//							exceptionMap.put(newTid,(exception.length()>50000)?exception.substring(0, exception.length()/2):exception);
//							//保存异常信息
//							this.insertExceptionMap(exceptionMap);
//						} catch (Exception e1) {
//							continue;
//						}
//						continue;
//					}
//				}
//			}else{
//				successCount++;
//				logger.error("失败----订单编号重复："+newTid);
//			}
//			
//			//当数量到达设定数量时,执行更新操作
//			if (i % 100 == 0&&i != 0) {
//				//批量保存订单数据
//				try {
//					this.insertOrderList(orderListToSave);
//					this.insertTransactionOrdeList(tradeListToSave);
//					orderListToSave= new ArrayList<Orders>();
//					tradeListToSave= new ArrayList<TransactionOrder>();
//				} catch (Exception e) {
//					logger.error("失败----保存订单失败："+e.getMessage());
//				}
//				
//				//修改总记录
//				try {
//					this.updateImportHistory(historyImport,rsCount,successCount);
//					rsCount=0;successCount=0;
//				} catch (Exception e) {
//					logger.error("失败----修改总记录："+e.getMessage());
//				}
//				
//				//订单中的会员做处理
//				try {
//					memberInfoService.convertMemberInfos(tradeList);
//					tradeList= new ArrayList<TransactionOrder>();
//				} catch (Exception e) {
//					logger.error("失败----会员分离失败："+e.getMessage());
//				}
//			}
//			
//		}
//		
//		//当所有循环完成后在执行更新操作
//		if(rsCount != 0){
//			//批量保存订单数据
//			try {
//				this.insertOrderList(orderListToSave);
//				this.insertTransactionOrdeList(tradeListToSave);
//				orderListToSave= new ArrayList<Orders>();
//				tradeListToSave= new ArrayList<TransactionOrder>();
//			} catch (Exception e) {
//				logger.error("失败----保存订单失败："+e.getMessage());
//			}
//			
//			//修改总记录
//			try {
//				this.updateImportHistory(historyImport,rsCount,successCount);
//			} catch (Exception e) {
//				logger.error("失败----修改总记录："+e.getMessage());
//			}
//
//			//当所有循环完成后,订单中的会员做处理
//			try {
//				memberInfoService.convertMemberInfos(tradeList);
//				tradeList= new ArrayList<TransactionOrder>();
//			} catch (Exception e) {
//				logger.error("失败----会员分离失败："+e.getMessage());
//			}
//		}
//		
//	}

//	//批量保存订单
//	private void insertTransactionOrdeList(List<TransactionOrder> tradeListToSave) {
//		if(tradeListToSave!=null && tradeListToSave.size()>0){
//			myBatisDao.execute(TransactionOrder.class.getName(), "insertTransactionOrdeList", tradeListToSave);
//		}
//		
//	}

//	//批量保存订单
//	private void insertOrderList(List<Orders> orderListToSave) {
//		if(orderListToSave!=null && orderListToSave.size()>0){
//			myBatisDao.execute(Orders.class.getName(), "insertOrderList", orderListToSave);
//		}
//	}

//	/*
//	 * 保存数据
//	 */
//	public void insertTransactionOrde(TransactionOrder trade) {
//		myBatisDao.execute(TransactionOrder.class.getName(), "insertTransactionOrde", trade);
//	}
	//	/*
//	 * 保存数据
//	 */
//	public void insertOrder(Orders order) {
//		myBatisDao.execute(Orders.class.getName(), "insertOrder", order);
//	}
	/**
	 * 订单导入出现异常的订单,做异常信息保存
	 * @param exceptionMap
	 */
	private void insertExceptionMap(Map<String, Object> exceptionMap) {
		Map<String, Object> hap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : exceptionMap.entrySet()) {
			hap.put("tid", entry.getKey());
			hap.put("message", entry.getValue());
			try {
				myBatisDao.execute(Orders.class.getName(), "insertExceptionMap", hap);
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	/*
	 * 获取token
	 */
	private String getUserToken(String userId) {
		String token = judgeUserUtil.getUserTokenByRedis(userId);
		if(null==token || "".equals(token)){
			UserInfo user = userInfoService.queryUserTokenInfo(userId);
			token =user.getAccess_token();
		}
		return token;
	}
}



