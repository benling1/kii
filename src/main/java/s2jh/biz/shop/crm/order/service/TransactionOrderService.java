package s2jh.biz.shop.crm.order.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.SecretException;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.TradeFullinfoGetResponse;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDaoT;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.shop.crm.manage.entity.LogAccessDTO;
import s2jh.biz.shop.crm.manage.entity.LogType;
import s2jh.biz.shop.crm.manage.service.LogAccessQueueService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.order.dao.TransactionOrderDao;
import s2jh.biz.shop.crm.order.entity.TransactionOrder;
import s2jh.biz.shop.crm.order.pojo.Refund;
import s2jh.biz.shop.crm.order.pojo.TbTransactionOrder;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;

/**
 * @ClassName: TransactionOrderService
 * @Description:(这里用一句话描述这个类的作用)
 * @author: jackstraw_yu
 * @date: 2017年3月21日 上午10:29:00
 * 
 */
@Service
public class TransactionOrderService extends BaseService<TransactionOrder, String> {
	@SuppressWarnings("unused")
    private static final Log logger = LogFactory.getLog(TransactionOrderService.class);
	@Autowired
	private TransactionOrderDao transactionOrderDao;

//	@Autowired
//	private OrdersDao ordersDao;
//	
//	@Autowired
//	private TradeRepository tradeRepository;
//
	@Autowired
	private JudgeUserUtil judgeUserUtil;

	@Autowired
	private MyBatisDaoT myBatisDaoT;
	

	
	// 辅助校验是否,存在还有没有保存完的tid/订单
//	private static Set<String> runningTidSet = new HashSet<String>();

//	// 数据库存储
//	public void saveTransactionOrde(TransactionOrder transactionOrde) {
//		transactionOrderDao.save(transactionOrde);
//	}
//
	@Override
	protected BaseDao<TransactionOrder, String> getEntityDao() {
		return transactionOrderDao;
	}
	
	/**
	 * 查询订单的状态
	 * @author: wy
	 * @time: 2017年10月18日 下午3:00:04
	 * @param tid
	 * @return
	 */
	public String queryTradeStatus(String tid){
	    String status = myBatisDaoT.findBy(TbTransactionOrder.class.getName(),"findTradeStatus", Long.parseLong(tid));
	    if(ValidateUtil.isEmpty(status)){
	        try {
                Thread.sleep(3000L);
                status = myBatisDaoT.findBy(TbTransactionOrder.class.getName(),"findTradeStatus", Long.parseLong(tid));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
	    }
	    return status;
	}
	/**
	 * @Title: queryTrade
	 * @Description: (通过tid获得trade对象)
	 * @param: @param
	 *             tid
	 * @param: @return
	 * @return: Trade
	 * @throws @date:
	 *             2017年3月21日 上午10:28:47
	 * @author: jackstraw_yu
	 */
	public Trade queryTrade(String tid) {
		// 定义要保存的数据
		Trade trade = null;
		// 1,根据tid查询出TbTransactionOrder(自定义对象)
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("tid", tid);
		TbTransactionOrder tbTransactionOrder = myBatisDaoT.findBy(TbTransactionOrder.class.getName(),
				"getTbTransactionOrders", hashMap);
		if(tbTransactionOrder==null){
		    try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		    tbTransactionOrder = myBatisDaoT.findBy(TbTransactionOrder.class.getName(),
	                "getTbTransactionOrders", hashMap);
		}
		if (tbTransactionOrder != null && !"".equals(tbTransactionOrder.getJdpResponse())) {
			// 解析JdpResponse
			String jdp_response = tbTransactionOrder.getJdpResponse();
			TradeFullinfoGetResponse rsp = null;
			try {
				rsp = TaobaoUtils.parseResponse(jdp_response, TradeFullinfoGetResponse.class);
			} catch (ApiException e) {
				e.printStackTrace();
			}

			if (rsp != null) {
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
				if(trade.getReceiverMobile()==null){
				    trade.setReceiverMobile(trade.getReceiverPhone());
				}
				trade.setTid(Long.parseLong(tid));
				if(trade.getTradeFrom()==null){
				    for(Order order : trade.getOrders()){
				        if(order.getOrderFrom()!=null){
				            trade.setTradeFrom(order.getOrderFrom());
				            break;
				        }
				    }
				}
			} catch (SecretException e) {
//				logger.error("tid:"+trade.getTid()+"  卖家昵称："+trade.getSellerNick()+"  sessionKey："+sessionKey);
//				if(ErrorUtil.isInvalidSession(e)) {
//					logger.error("用户sessionKey失效");
//			        // 标记该sessionkey无效，重新授权之前不要再调用
//			    }else{
////			    	logger.error("解密出错啦,请直接呼叫wy");
//			    }
//				logger.error("e.getErrCode() "+e.getErrCode() +"\r e.getErrMsg():"+e.getErrMsg() +"\r e.getMessage():"+e.getMessage() + "\r e.getSubErrMsg():"+e.getSubErrMsg());
//				e.printStackTrace();
				return null;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			LogAccessDTO logAccessDTO = new LogAccessDTO();
			logAccessDTO.setUserId("");
			logAccessDTO.setUserIp("121.199.171.206");
			logAccessDTO.setAti("");
			logAccessDTO.setUrl("tmc订单查询");
			logAccessDTO.setTradeIds(tid);
			logAccessDTO.setOperation("tmc订单查询");
			params.put(LogType.class.getName(), LogType.ORDER_TYPE);
			params.put(LogAccessDTO.class.getName(), logAccessDTO);
			try {
				LogAccessQueueService.queue.put(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return trade;
	}
	public static void main(String[] args) {
		System.out.println(new Date(1519826526353L));
	}
	/**
	 * 查询sysInfo表中的订单，不休眠，非tmc查询
	 * @author: wy
	 * @time: 2017年11月23日 下午4:46:05
	 * @param tid 主订单号
	 * @return
	 */
    public Trade queryTradeByNoTmc(String tid) {
        // 定义要保存的数据
        Trade trade = null;
        // 1,根据tid查询出TbTransactionOrder(自定义对象)
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("tid", tid);
        TbTransactionOrder tbTransactionOrder = myBatisDaoT.findBy(TbTransactionOrder.class.getName(),
                "getTbTransactionOrders", hashMap);
        if (tbTransactionOrder != null && !"".equals(tbTransactionOrder.getJdpResponse())) {
            // 解析JdpResponse
            String jdp_response = tbTransactionOrder.getJdpResponse();
            TradeFullinfoGetResponse rsp = null;
            try {
                rsp = TaobaoUtils.parseResponse(jdp_response, TradeFullinfoGetResponse.class);
            } catch (ApiException e) {
                e.printStackTrace();
            }

            if (rsp != null) {
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
                if(trade.getReceiverMobile()==null){
                    trade.setReceiverMobile(trade.getReceiverPhone());
                }
                trade.setTid(Long.parseLong(tid));
                if(trade.getTradeFrom()==null){
                    for(Order order : trade.getOrders()){
                        if(order.getOrderFrom()!=null){
                            trade.setTradeFrom(order.getOrderFrom());
                            break;
                        }
                    }
                }
            } catch (SecretException e) {
                return null;
            }
        }
        return trade;
    }
	public boolean queryTradePaymentTime(Long tid,Date startTime,Date endTime){
		if ( startTime==null ||startTime==null ||endTime==null)
			return false;
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("tid", tid);
		TbTransactionOrder tbTransactionOrder = myBatisDaoT.findBy(TbTransactionOrder.class.getName(),"getTbTransactionOrders", hashMap);
		if ( tbTransactionOrder != null &&!"".equals(tbTransactionOrder.getJdpResponse())) {
			// 解析JdpResponse
			String jdp_response = tbTransactionOrder.getJdpResponse();
			if(jdp_response.contains("pay_time")){
				JSONObject reuslt = JSONObject.parseObject(jdp_response);
				String paymentTimeString = reuslt.getJSONObject("trade_fullinfo_get_response").getJSONObject("trade").getString("pay_time");
				try {
					Date paymentTime = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss").parse(paymentTimeString);
					Long pay = paymentTime.getTime();
					if ( startTime.getTime() <=pay &&endTime.getTime() >=pay){
						return true;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			Map<String, Object> params = new HashMap<String, Object>();
			LogAccessDTO logAccessDTO = new LogAccessDTO();
			logAccessDTO.setUserId("");
			logAccessDTO.setUserIp("121.199.171.206");
			logAccessDTO.setAti("");
			logAccessDTO.setUrl("tmc订单查询");
			logAccessDTO.setTradeIds(String.valueOf(tid));
			logAccessDTO.setOperation("tmc订单查询");
			params.put(LogType.class.getName(), LogType.ORDER_TYPE);
			params.put(LogAccessDTO.class.getName(), logAccessDTO);
			try {
				LogAccessQueueService.queue.put(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
//
//
//
//	/**
//	 * 通过tid查询出是否有这条数据 helei 2017年2月10日下午3:35:48
//	 */
//	public Integer queryTransactionOrderCount(String tid) {
//		Map<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("tid", tid);
//		Integer count = myBatisDao.findBy(TransactionOrder.class.getName(), "queryTransactionOrderCount", hashMap);
//		return count;
//	}
//
//	/**
//	 * 通过tid查询Trade
//	 * ZTK2017年3月18日下午3:30:44
//	 */
////	public TransactionOrder findTradeById(String tid){
////		Map<String,Object> map = new HashMap<String, Object>();
////		map.put("tid", tid);
////		if(tid != null && !"".equals(tid)){
////			TransactionOrder trade = myBatisDao.findBy(TransactionOrder.class.getName(), "findTradeById", map);
////			if(trade != null){
////				return trade;
////			}else {
////				return null;
////			}
////		}else{
////			return null;
////		}
////	}
//	
//	public TradeDTO findTradeById(String tid,String userNickName){
//		Query query = new Query();
//		query.addCriteria(Criteria.where("tid").is(tid));
//		if(tid != null && !"".equals(tid)){
//			TradeDTO trade = tradeRepository.findOne(query, userNickName);
//			if(trade != null){
//				return trade;
//			}else {
//				return null;
//			}
//		}else{
//			return null;
//		}
//	}
//	
//	
//	
//	
//	
//	
//	//=========================================增强修改============================
//	/**   
//	 * @Title:	同步订单数据,数据量较大 
//	 *          保存故障信息的形式为key/value形式:tid,故障信息  
//	 * @date:   2017年3月25日 下午2:09:52 
//	 * @author: jackstraw_yu  
//	 */  
//	//=========================================增强修改============================
//	
//	private static final Log logger = LogFactory.getLog(TransactionOrderService.class);
//	
//	@Autowired
//	private MemberInfoService memberInfoService; 
//	
//	
//	/**   
//	 * @Title: loadTbTradeData   
//	 * @Description: (订单数据同步)   
//	 * @param: @param tbs      
//	 * @return: void      
//	 * @throws
//	 * @date:   2017年3月25日 下午3:09:01 
//	 * @author: jackstraw_yu  
//	 */  
//	public void loadConvertTbTradeData(List<TbTransactionOrder> tbs) {
//	
//		logger.info("============================================================开始转换,保存或者更新"+tbs.size()+
//				"条订单数据============================================================线程:"+Thread.currentThread().getName()+"时间:"+new Date(System.currentTimeMillis()));
//		List<TransactionOrder> transactionOrderList = null;
//		List<Orders> ordersList = null;
//		List<Trade> tradeList = null;
//		Map<String,Object> exceptionMap = null;
//		if (tbs != null && tbs.size() > 0) {
//			transactionOrderList = new ArrayList<TransactionOrder>();
//			ordersList = new ArrayList<Orders>();
//			tradeList = new ArrayList<Trade>();
//			exceptionMap = new HashMap<String ,Object>();
//			String jdp_response = null;
//			String exception = null;
//			out: for (TbTransactionOrder tbTrade : tbs) {
//				if (tbTrade.getJdpResponse() != null && !"".equals(tbTrade.getJdpResponse())) {
//					// 解析JdpResponse
//					jdp_response = tbTrade.getJdpResponse();
//					TradeFullinfoGetResponse rsp = null;
//					try {
//						rsp = TaobaoUtils.parseResponse(jdp_response, TradeFullinfoGetResponse.class);
//					} catch (Exception e) {
//						e.printStackTrace();
//						// 解析异常,继续下次循环
//						exception = e.getMessage();
//						exceptionMap.put(tbTrade.getTid().toString(),exception.substring(0, exception.length()/3));
//						continue out;
//					}
//					// 封装transactionOrder
//					Trade trade = rsp.getTrade();
//					if (trade != null) {
//						tradeList.add(trade);
//						TransactionOrder transactionOrder = null;
//						try {
//							// 使用自定义转换器
//							transactionOrder = new TransactionOrder();
//							MyBeanUtils.copyProperties(transactionOrder, trade);
//							//手动给订单赋值商品id
//							transactionOrder.setNUM_IID(trade.getNumIid()==null?null:trade.getNumIid().toString());
//						} catch (Exception e1) {
//							e1.printStackTrace();
//							//continue out;
//						}
//						transactionOrderList.add(transactionOrder);
//						// 封装子订单
//						List<Order> orders = trade.getOrders();
//						if (orders != null && orders.size() > 0) {
//							inner: for (Order o : orders) {
//								Orders newOrders = new Orders();
//								try {
//									MyBeanUtils.copyProperties(newOrders, o);
//								} catch (Exception e) {
//									e.printStackTrace();
//									//continue inner;
//								}
//								// 关联主订单
//								newOrders.setSellerNick(transactionOrder.getSellerNick());
//								newOrders.setBuyerNick(transactionOrder.getBuyerNick());
//								newOrders.setTid(transactionOrder.getTid());
//								newOrders.setReceiverDistrict(transactionOrder.getReceiverDistrict());
//								newOrders.setReceiverCity(transactionOrder.getReceiverCity());
//								newOrders.setStepTradeStatus(transactionOrder.getStepTradeStatus());
//								newOrders.setCreated(transactionOrder.getCreated());
//								newOrders.setReceiverName(transactionOrder.getReceiverName());
//								newOrders.setReceiverMobile(transactionOrder.getReceiverMobile() == null
//										? transactionOrder.getReceiverPhone() : transactionOrder.getReceiverMobile());
//								newOrders.setBuyerFlag(
//										trade.getBuyerFlag() == null ? null : trade.getBuyerFlag().intValue());
//								newOrders.setSellerFlag(transactionOrder.getSellerFlag() == null ? null
//										: Integer.valueOf(transactionOrder.getSellerFlag()));
//								// 后续补加字段
//								newOrders.setModified(transactionOrder.getModified());
//								ordersList.add(newOrders);
//							}
//						}
//					}
//				}
//			}
//		}
//		// 以一个集合为单位保存或者更新trade(分页查询的一个页面的集合)
//		Map<String, Object> tMap = null;
//		try {
//			tMap = saveOrUpdateTransactionOrderList(transactionOrderList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// 保存或者更新失败;去除其子订单
//		if (tMap != null && tMap.size() > 0 && ordersList != null && ordersList.size() > 0 ) {
//			Iterator<Orders> iterator = ordersList.iterator();
//			while (iterator.hasNext()) {
//				Orders o = iterator.next();
//				if (tMap.containsKey(o.getTid())) {
//					iterator.remove();
//				}
//			}
//		}
//		// 保存或者更新子订单
//		try {
//			saveOrUpdateOrdersList(ordersList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		//从订单中分离出会员信息
//		try {
//			memberInfoService.convertMemberInfoData(tradeList);
//		} catch (Exception e) {
//			 e.printStackTrace();
//		}
//
//		// 保存故障的tid/异常 到本地数据库
//		if((exceptionMap !=null && exceptionMap.size()>0)){
//			if(tMap != null && tMap.size() > 0){
//				exceptionMap.putAll(tMap);
//			}
//		}else{
//			if(tMap != null && tMap.size() > 0){
//				exceptionMap = tMap;
//			}
//		}
//		if((exceptionMap !=null && exceptionMap.size()>0)){
//			saveExceptionMap(exceptionMap);
//		}
//
//		logger.info("============================================================结束转换,保存或者更新"+tbs.size()+
//				"条订单数据============================================================线程:"+Thread.currentThread().getName()+"时间:"+new Date(System.currentTimeMillis()));
//		
//	}
//	
//	
//
//	
//	/**   
//	 * @Title: saveExceptionMap   
//	 * @Description: (保存故障订单的保存或者更新时发生的故障信息)   
//	 * @param: @param exceptionMap      
//	 * @return: void      
//	 * @throws
//	 * @date:   2017年3月25日 下午5:14:09 
//	 * @author: jackstraw_yu  
//	 */  
//	private void saveExceptionMap(Map<String, Object> exceptionMap) {
//		Map<String, Object> hap = new HashMap<String, Object>();
//		for (Entry<String, Object> entry : exceptionMap.entrySet()) {
//			hap.put("tid", entry.getKey());
//			hap.put("message", entry.getValue());
//			try {
//				myBatisDao.execute(Orders.class.getName(), "saveExceptionMap", hap);
//			} catch (Exception e) {
//				continue;
//			}
//		}
//	}
//
//	/**   
//	 * @Title: saveOrUpdateTransactionOrderList   
//	 * @Description: (保存或者更新主订单信息,返回有异常的订单信息:key/value)   
//	 * @param: @param transactionOrderList
//	 * @param: @return      
//	 * @return: Map<String,Object>      
//	 * @throws
//	 * @date:   2017年3月25日 下午3:58:49 
//	 * @author: jackstraw_yu  
//	 */  
//	public Map<String,Object> saveOrUpdateTransactionOrderList(List<TransactionOrder> transactionOrderList) {
//		//定义异常数据的记录
//		Map<String,Object> exceptionMap = null;
//		if (transactionOrderList != null && transactionOrderList.size() > 0) {
//			exceptionMap = new HashMap<String,Object>();
//			List<TransactionOrder> upTrades = new ArrayList<TransactionOrder>();
//			Iterator<TransactionOrder> iterator = transactionOrderList.iterator();
//			// 判断数据库是否存在该条数据==>存在-更新/不存在-保存,默认不存在
//			Boolean exists = false;
//			while (iterator.hasNext()) {
//				TransactionOrder t = iterator.next();
//				try {
//					exists = isExistTransactionOrderEnhance(t.getTid());
//				} catch (Exception e) {
//					exists = true;
//				}
//				if (exists) {
//					upTrades.add(t);
//					iterator.remove();
//				}
//			}
//
//			// 保存
//			Map<String, Object> sMap = null;
//			if (transactionOrderList.size() > 0) {
//				sMap = saveTransactionOrderList(transactionOrderList);
//			}
//			// 更新==批量更新
//			Map<String, Object> uMap = null;
//			if (upTrades.size() > 0) {
//				uMap = updateTransactionOrderList(upTrades);
//			}
//			//合并两个异常的集合
//			if(sMap!=null){
//				exceptionMap.putAll(sMap);
//			}
//			if(uMap!=null){
//				exceptionMap.putAll(uMap);
//			}
//			
//		}
//		return exceptionMap;
//	}
//
//	
//	/**   
//	 * @Title: saveOrUpdateOrdersList   
//	 * @Description: (批量保存或者更新子订单)   
//	 * @param: @param ordersList      
//	 * @return: void      
//	 * @throws
//	 * @date:   2017年3月25日 下午4:56:09 
//	 * @author: jackstraw_yu  
//	 */  
//	public void saveOrUpdateOrdersList(List<Orders> ordersList) {
//		if (ordersList != null && ordersList.size() > 0) {
//			List<Orders> upOrders = new ArrayList<Orders>();
//			Iterator<Orders> iterator = ordersList.iterator();
//			Boolean exists = false;
//			while (iterator.hasNext()) {
//				Orders o = iterator.next();
//				// 判断数据库是否存在该条数据==>存在-更新/不存在-保存
//				try {
//					exists = isExistOrdersEnhance(o.getOid());
//				} catch (Exception e) {
//					exists = true;
//				}
//				if (exists) {
//					upOrders.add(o);
//					iterator.remove();
//				}
//			}
//
//			// 批量保存
//			if (ordersList.size() > 0) {
//				saveOrdersList(ordersList);
//			}
//			// 批量更新
//			if (upOrders.size() > 0) {
//				updateOrdersList(ordersList);
//			}
//		}
//	}
//
//	
//
//	
//	/**   
//	 * @Title: saveTransactionOrderList   
//	 * @Description: (保存主订单信息,返回异常订单信息)   
//	 * @param: @param transactionOrderList
//	 * @param: @return      
//	 * @return: List<String>      
//	 * @throws
//	 * @date:   2017年3月25日 下午4:03:30 
//	 * @author: jackstraw_yu  
//	 */  
//	public Map<String ,Object> saveTransactionOrderList(List<TransactionOrder> transactionOrderList){
//		logger.info("$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%开始保存"+transactionOrderList.size()+
//				"条主订单数据%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$线程:"+Thread.currentThread().getName()+"时间"+new Date(System.currentTimeMillis()));
//		Map<String ,Object> exceptionMap = new HashMap<String,Object>();
//		String exception = null;
//		for (TransactionOrder trade : transactionOrderList) {
//			try {
//				//手写保存
//				trade.setCreatedDate(new Date());
//				insertTransactionOrderList(trade);
//			} catch (Exception e) {
//				e.printStackTrace();
//				//使用key/value形式保存异常订单信息
//				exception = e.getMessage();
//				exceptionMap.put(trade.getTid(), exception.substring(0,exception.length()/3));
//				continue;
//			}
//		}
//		logger.info("$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%结束保存"+transactionOrderList.size()+
//				"条主订单数据%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$线程:"+Thread.currentThread().getName()+"时间"+new Date(System.currentTimeMillis()));
//		//空集合返回null,
//		return exceptionMap.size()>0?exceptionMap:null;
//	}
//	
//	
//	
//	
//	
//	/**   
//	 * @Title: insertTransactionOrderList   
//	 * @Description: (保存主订单信息,异常直接抛出,由外部调用try/catch)   
//	 * @param: @param trade      
//	 * @return: void      
//	 * @throws
//	 * @date:   2017年3月28日 下午5:31:43 
//	 * @author: jackstraw_yu  
//	 */  
//	public void insertTransactionOrderList(TransactionOrder trade) {
//		Map<String ,Object> hashMap = new HashMap<String,Object>();
//		hashMap.put("trade", trade);
//		myBatisDao.execute(TransactionOrder.class.getName(), "insertTransactionOrderEnhance", hashMap);
//		
//	}
//
//	/**   
//	 * @Title: updateTransactionOrderList   
//	 * @Description: (批量保存或者更新主订单信息)   
//	 * @param: @param upTrades
//	 * @param: @return      
//	 * @return: Map<String,Object>      
//	 * @throws
//	 * @date:   2017年3月25日 下午4:56:38 
//	 * @author: jackstraw_yu  
//	 */  
//	public Map<String ,Object>  updateTransactionOrderList(List<TransactionOrder> upTrades) {
//		Map<String, Object> hashMap = new HashMap<String, Object>();
//		Map<String ,Object> exceptionMap = new HashMap<String,Object>();
//		String exception = null;
//		logger.info("$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%开始更新"+upTrades.size()+
//				"条主订单数据%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$线程:"+Thread.currentThread().getName()+"时间"+new Date(System.currentTimeMillis()));
//		for (TransactionOrder transactionOrder : upTrades) {
//				//修改时间
//			transactionOrder.setLastModifiedDate(new Date());
//			hashMap.put("trade", transactionOrder);
//			try {
//				myBatisDao.execute(TransactionOrder.class.getName(), "updateTransactionOrderEnhance", hashMap);
//			} catch (Exception e) {
//				e.printStackTrace();
//				//使用key/value形式保存异常订单信息
//				exception = e.getMessage();
//				exceptionMap.put(transactionOrder.getTid(), exception.substring(0,exception.length()/3));
//				continue;
//			}
//		}
//		logger.info("$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%结束更新"+upTrades.size()+
//				"条主订单数据%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$%$线程:"+Thread.currentThread().getName()+"时间"+new Date(System.currentTimeMillis()));
//		//空集合返回null,
//		return exceptionMap.size()>0?exceptionMap:null;
//	}
//	
//	
//	  
//	/**   
//	 * @Title: saveOrdersList   
//	 * @Description: (批量保存子订单信息)   
//	 * @param: @param ordersList      
//	 * @return: void      
//	 * @throws
//	 * @date:   2017年3月25日 下午4:54:32 
//	 * @author: jackstraw_yu  
//	 */  
//	private void saveOrdersList(List<Orders> ordersList) {
//		for (Orders orders : ordersList) {
//			try {
//				ordersDao.save(orders);
//			} catch (Exception e) {
//				e.printStackTrace();
//				continue;
//			}
//		}
//	}
//	
//	
//	
//	/**   
//	 * @Title: updateOrdersList   
//	 * @Description: (批量更新子订单信息)   
//	 * @param: @param upOrders      
//	 * @return: void      
//	 * @throws
//	 * @date:   2017年3月25日 下午4:55:02 
//	 * @author: jackstraw_yu  
//	 */  
//	public void updateOrdersList(List<Orders> upOrders) {
//		Map<String, Object> hashMap = new HashMap<String, Object>();
//		for (Orders orders : upOrders) {
//			try {
//				orders.setLastModifiedDate(new Date());
//				hashMap.put("orders", orders);
//				myBatisDao.execute(Orders.class.getName(), "updateOrdersEnhance", hashMap);
//			} catch (Exception e) {
//				e.printStackTrace();
//				continue;
//			}
//		}
//	}
//	
//	
//	/**   
//	 * @Title: isExistTransactionOrderEnhance   
//	 * @Description: (增强:判断主订单是否存在,存在返回true不存在fasle)   
//	 * @param: @param id
//	 * @param: @return      
//	 * @return: Boolean      
//	 * @throws
//	 * @date:   2017年3月21日 上午11:21:54 
//	 * @author: jackstraw_yu  
//	 */  
//	public Boolean isExistTransactionOrderEnhance(String id) {
//		Map<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("tid", id);
//		String tid = myBatisDao.findBy(TransactionOrder.class.getName(),
//				"isExistTransactionOrderEnhance", hashMap);
//		return (tid==null||"".equals(tid))?false:true;
//	}
//	
//	/**   
//	 * @Title: isExistOrdersEnhance   
//	 * @Description: (判断子订单是否存在,存在返回true,不存在返回false)   
//	 * @param: @param id
//	 * @param: @return      
//	 * @return: Boolean      
//	 * @throws
//	 * @date:   2017年3月21日 上午11:30:32 
//	 * @author: jackstraw_yu  
//	 */  
//	public Boolean isExistOrdersEnhance(String id) {
//		Map<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("oid", id);
//		String oid = myBatisDao.findBy(Orders.class.getName(), "isExistOrdersEnhance", hashMap);
//		return (oid==null||"".equals(oid))?false:true;
//	}
//	
	/**
	* 创建人：邱洋
	* @Title: getRefundNumber 
	* @Description: TODO(根据用户昵称查询退款中的数据) 
	* @param @param userId
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int getRefundNumber(String userId,Date bTime, Date eTime){
		int count = 0;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		List<String> statusList = myBatisDaoT.findList(Refund.class.getName(), "getRefundNumber", map);
		for (String status : statusList) {
			if("WAIT_SELLER_AGREE".equals(status) || "WAIT_BUYER_RETURN_GOODS".equals(status) || "WAIT_SELLER_CONFIRM_GOODS".equals(status) || "SELLER_REFUSE_BUYER".equals(status)){
				count ++ ;
			}
		}
		return count;
	}
}
