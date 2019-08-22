/**
 * 
 */
package s2jh.biz.shop.crm.taobao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.TradesSoldIncrementGetResponse;

/**
 * @Title:
 * @date 2017年4月5日--下午4:05:04
 * @param 设定文件
 * @return 返回类型
 * @throws
 */

@Component
@Transactional
public class FirstGetUserTrade {

	@Autowired
	private TradeInfoService tradeInfoService;
	

	/**
	 * 创建人：邱洋
	 * 
	 * @Title: 根据用户信息查询当前卖家1天内的订单信息，并保存到数据库
	 * @date 2017年4月5日--下午5:03:16
	 * @return void
	 * @throws
	 */
	public void firstGetUserTrade(String userId, String token) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = null;// 订单查询开始时间
		String endDate = null;// 订单查询结束时间

		Calendar date = Calendar.getInstance();// 获取当前的时间
		endDate = formatter.format(date.getTime());
		date.add(Calendar.DAY_OF_MONTH, -1);// 获取一天前的时间
		startDate = formatter.format(date.getTime());

		Long pageNo = 1L;// 获取订单的起始页数
		boolean has_next = true;

		while (has_next) {
			TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
					taobaoInfo.appKey, taobaoInfo.appSecret);
			TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();
			req.setFields("tid,seller_nick,pic_path,payment,seller_rate,post_fee,receiver_name,receiver_state,receiver_address,receiver_zip,receiver_mobile,receiver_phone,consign_time,received_payment,receiver_country,receiver_town,order_tax_fee,shop_pick,num,num_iid,status,title,type,price,discount_fee,total_fee,created,pay_time,modified,end_time,seller_flag,buyer_nick,has_buyer_message,credit_card_fee,step_trade_status,step_paid_fee,mark_desc,shipping_type,adjust_fee,buyer_rate,receiver_city,receiver_district,orders,rx_audit_status,post_gate_declare,cross_bonded_declare,order_tax_promotion_fee");
			req.setStartModified(StringUtils.parseDateTime(startDate));
			req.setEndModified(StringUtils.parseDateTime(endDate));
			req.setPageNo(pageNo);
			req.setPageSize(50L);
			req.setUseHasNext(true);
			TradesSoldIncrementGetResponse rsp = null;
			try {
				rsp = client.execute(req, token);
				asyncHandleData(rsp, userId,token);
			} catch (Exception e) {
				e.printStackTrace();
			}
			pageNo++;// 页数自增
			if (rsp.getHasNext() != null) {
				has_next = rsp.getHasNext();// 是否存在下一页，则继续查询
			} else {
				has_next = false;
			}
		}
	}

	/**
	 * 创建人：邱洋
	 * @Title: 创建一个线程，调用数据保存方法
	 * @date 2017年4月7日--下午1:37:00
	 * @return void
	 * @throws
	 */
	private void asyncHandleData(final TradesSoldIncrementGetResponse rsp,
			final String userId,final String token) {
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread() {
			@Override
			public void run() {
				addTrade(rsp, userId,token);
			}
		});
	}

	/**
	 * 创建人：邱洋
	 * @Title:将调取接口的订单保存到list中，进行批量保存
	 * @date 2017年4月5日--下午6:06:42
	 * @return void
	 * @throws
	 */
	@Transactional
	public void addTrade(TradesSoldIncrementGetResponse rsp, String userId,String token) {
		List<TradeDTO> transactionOrderList = new ArrayList<TradeDTO>();
		List<OrdersDTO> upOrders = new ArrayList<OrdersDTO>();
		if (rsp.getTrades() != null && rsp.getTrades().size() > 0) {
			for (int i = 0; i < rsp.getTrades().size(); i++) {
				// 将数据添加到trade表中
				TradeDTO t = new TradeDTO();
				if (rsp.getTrades().get(i).getTid() != null) {
					t.setTid(rsp.getTrades().get(i).getTid().toString());
				}
				t.setSellerNick(rsp.getTrades().get(i).getSellerNick());
				t.setPicPath(rsp.getTrades().get(i).getPicPath());
				if (rsp.getTrades().get(i).getPayment() != null) {
					t.setPayment(Double.parseDouble(rsp.getTrades().get(i)
							.getPayment()));
				}
				t.setSellerRate(rsp.getTrades().get(i).getSellerRate());
				t.setPostFee(rsp.getTrades().get(i).getPostFee());
				t.setReceiverZip(rsp.getTrades().get(i).getReceiverZip());
				t.setReceiverState(rsp.getTrades().get(i).getReceiverState());
				t.setReceiverName(rsp.getTrades().get(i).getReceiverName());
				t.setReceiverAddress(rsp.getTrades().get(i).getReceiverAddress());
				t.setBuyerNick(rsp.getTrades().get(i).getBuyerNick());
				t.setReceiverMobile(rsp.getTrades().get(i).getReceiverMobile());
				//判断字段是否需要加密，如果为密文则解密
//				try {
//					if(EncrptAndDecryptClient.isEncryptData(rsp.getTrades().get(i).getReceiverName(), EncrptAndDecryptClient.SEARCH)){
//						String ReceiverName = EncrptAndDecryptClient.getInstance().decryptData(rsp.getTrades().get(i).getReceiverName(),  EncrptAndDecryptClient.SEARCH, token);
//						t.setBuyerNick(ReceiverName);
//					}else{
//						t.setReceiverName(rsp.getTrades().get(i).getReceiverName());
//					}
//					if(EncrptAndDecryptClient.isEncryptData(rsp.getTrades().get(i).getReceiverAddress(), EncrptAndDecryptClient.SEARCH)){
//						String ReceiverAddress = EncrptAndDecryptClient.getInstance().decryptData(rsp.getTrades().get(i).getReceiverAddress(),  EncrptAndDecryptClient.SEARCH, token);
//						t.setBuyerNick(ReceiverAddress);
//					}else{
//						t.setReceiverAddress(rsp.getTrades().get(i).getReceiverAddress());
//					}
//					if(EncrptAndDecryptClient.isEncryptData(rsp.getTrades().get(i).getReceiverMobile(),EncrptAndDecryptClient.PHONE)){
//						String ReceiverMobile = EncrptAndDecryptClient.getInstance().decryptData(rsp.getTrades().get(i).getReceiverMobile(), EncrptAndDecryptClient.PHONE, token);
//						t.setBuyerNick(ReceiverMobile);
//					}else{
//						t.setReceiverMobile(rsp.getTrades().get(i).getReceiverMobile());
//					}
//					if(EncrptAndDecryptClient.isEncryptData(rsp.getTrades().get(i).getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
//						String buyerNick = EncrptAndDecryptClient.getInstance().decryptData(rsp.getTrades().get(i).getBuyerNick(),  EncrptAndDecryptClient.SEARCH, token);
//						t.setBuyerNick(buyerNick);
//					}else{
//						t.setBuyerNick(rsp.getTrades().get(i).getBuyerNick());
//					}
//				} catch (SecretException e) {
//					e.printStackTrace();
//				}
				t.setReceiverPhone(rsp.getTrades().get(i).getReceiverPhone());
				if(null!=rsp.getTrades().get(i).getConsignTime()){
					t.setConsignTime(rsp.getTrades().get(i).getConsignTime().getTime());
				}
				t.setConsignTimeUTC(rsp.getTrades().get(i).getConsignTime());
				if(null!=rsp.getTrades().get(i).getReceivedPayment()){
					t.setReceivedPayment(Double.parseDouble(rsp.getTrades().get(i).getReceivedPayment()));
				}
				t.setEstConTime(rsp.getTrades().get(i).getEstConTime());
				t.setInvoiceKind(rsp.getTrades().get(i).getInvoiceKind());
				t.setReceiverCountry(rsp.getTrades().get(i).getReceiverCountry());
				t.setReceiverTown(rsp.getTrades().get(i).getReceiverTown());
				if (rsp.getTrades().get(i).getOrderTaxFee() != null) {
					t.setOrderTaxFee(Integer.parseInt(rsp.getTrades().get(i).getOrderTaxFee()));
				}
				if (rsp.getTrades().get(i).getPaidCouponFee() != null) {
					t.setPaidCouponFee(Double.parseDouble(rsp.getTrades().get(i).getPaidCouponFee()));
				}
				t.setShopPick(rsp.getTrades().get(i).getShopPick());
				if (rsp.getTrades().get(i).getNum() != null) {
					t.setNum(rsp.getTrades().get(i).getNum());
				}
				if (rsp.getTrades().get(i).getNumIid() != null) {
					t.setNUM_IID(rsp.getTrades().get(i).getNumIid().toString());
				}
				t.setStatus(rsp.getTrades().get(i).getStatus());
				t.setTitle(rsp.getTrades().get(i).getTitle());
				t.setType(rsp.getTrades().get(i).getType());
				if(null!=rsp.getTrades().get(i).getPrice()){
					t.setPrice(Double.parseDouble(rsp.getTrades().get(i).getPrice()));
				}
				t.setDiscountFee(rsp.getTrades().get(i).getDiscountFee());
				t.setHasPostFee(rsp.getTrades().get(i).getHasPostFee());
				if(null!=rsp.getTrades().get(i).getTotalFee()){
					t.setTotalFee(Double.parseDouble(rsp.getTrades().get(i).getTotalFee()));
				}
				if(null!=rsp.getTrades().get(i).getCreated()){
					t.setCreated(rsp.getTrades().get(i).getCreated().getTime());
				}
				t.setCreatedUTC(rsp.getTrades().get(i).getCreated());
				if(null!=rsp.getTrades().get(i).getPayTime()){
					t.setPayTime(rsp.getTrades().get(i).getPayTime().getTime());
				}
				t.setPayTimeUTC(rsp.getTrades().get(i).getPayTime());
				if(null!=rsp.getTrades().get(i).getModified()){
					t.setModified(rsp.getTrades().get(i).getModified().getTime());
				}
				t.setModifiedUTC(rsp.getTrades().get(i).getModified());
				if(null!=rsp.getTrades().get(i).getEndTime()){
					t.setEndTime(rsp.getTrades().get(i).getEndTime().getTime());
				}
				t.setEndTimeUTC(rsp.getTrades().get(i).getEndTime());
				t.setBuyerMessage(rsp.getTrades().get(i).getBuyerMessage());
				t.setBuyerMemo(rsp.getTrades().get(i).getBuyerMemo());
				if (rsp.getTrades().get(i).getBuyerFlag() != null) {
					String buyerFlag = rsp.getTrades().get(i).getBuyerFlag()
							.toString();
					t.setBuyerFlag(Integer.valueOf(buyerFlag));
				}
				t.setSellerMemo(rsp.getTrades().get(i).getSellerMemo());
				if (rsp.getTrades().get(i).getSellerFlag() != null) {
					t.setSellerFlag(rsp.getTrades().get(i).getSellerFlag()
							.toString());
				}
				t.setInvoiceName(rsp.getTrades().get(i).getInvoiceName());
				t.setInvoiceType(rsp.getTrades().get(i).getInvoiceType());
				t.setTradeAttr(rsp.getTrades().get(i).getTradeAttr());
				t.setCreditCardFee(rsp.getTrades().get(i).getCreditCardFee());
				t.setStepTradeStatus(rsp.getTrades().get(i)
						.getStepTradeStatus());
				t.setStepPaidFee(rsp.getTrades().get(i).getStepPaidFee());
				t.setMarkDesc(rsp.getTrades().get(i).getMarkDesc());
				t.setShippingType(rsp.getTrades().get(i).getShippingType());
				t.setBuyerCodFee(rsp.getTrades().get(i).getBuyerCodFee());
				t.setAdjustFee(rsp.getTrades().get(i).getAdjustFee());
				t.setTradeFrom(rsp.getTrades().get(i).getTradeFrom());
				t.setBuyerRate(rsp.getTrades().get(i).getBuyerRate());
				t.setReceiverCity(rsp.getTrades().get(i).getReceiverCity());
				t.setReceiverDistrict(rsp.getTrades().get(i)
						.getReceiverDistrict());
				t.setO2o(rsp.getTrades().get(i).getO2o());
				t.setO2oGuideId(rsp.getTrades().get(i).getO2oGuideId());
				t.setO2oShopId(rsp.getTrades().get(i).getO2oShopId());
				t.setO2oGuideName(rsp.getTrades().get(i).getO2oGuideName());
				t.setO2oShopName(rsp.getTrades().get(i).getO2oShopName());
				t.setO2oDelivery(rsp.getTrades().get(i).getO2oDelivery());
				t.setEticketServiceAddr(rsp.getTrades().get(i)
						.getEticketServiceAddr());
				t.setRxAuditStatus(rsp.getTrades().get(i).getRxAuditStatus());
				t.setEsRange(rsp.getTrades().get(i).getEsRange());
				t.setEsDate(rsp.getTrades().get(i).getEsDate());
				t.setOsDate(rsp.getTrades().get(i).getOsDate());
				t.setOsRange(rsp.getTrades().get(i).getOsRange());
				if (rsp.getTrades().get(i).getCouponFee() != null) {
					String couponFee = rsp.getTrades().get(i).getCouponFee()
							.toString();
					t.setCouponFee(Integer.valueOf(couponFee));
				}
				t.setPostGateDeclare(rsp.getTrades().get(i)
						.getPostGateDeclare());
				t.setCrossBondedDeclare(rsp.getTrades().get(i)
						.getCrossBondedDeclare());
				t.setOmnichannelParam(rsp.getTrades().get(i)
						.getOmnichannelParam());
				t.setAssembly(rsp.getTrades().get(i).getAssembly());
				if (rsp.getTrades().get(i).getTopHold() != null) {
					String topHold = rsp.getTrades().get(i).getTopHold()
							.toString();
					t.setTopHold(Integer.valueOf(topHold));
				}
				t.setOmniAttr(rsp.getTrades().get(i).getOmniAttr());
				t.setOmniParam(rsp.getTrades().get(i).getOmniParam());
				t.setIsShShip(rsp.getTrades().get(i).getIsShShip());
				t.setO2oSnatchStatus(rsp.getTrades().get(i)
						.getO2oSnatchStatus());
				t.setMarket(rsp.getTrades().get(i).getMarket());
				t.setEtType(rsp.getTrades().get(i).getEtType());
				t.setEtShopId(rsp.getTrades().get(i).getEtShopId());
				t.setObs(rsp.getTrades().get(i).getObs());
				t.setCreatedBy(userId);
				t.setCreatedDate(new Date());

				transactionOrderList.add(t);// 将trade对象添加到list集合中
				if (rsp.getTrades().get(i).getOrders() != null
						&& rsp.getTrades().get(i).getOrders().size() > 0) {
					for (int j = 0; j < rsp.getTrades().get(i).getOrders()
							.size(); j++) {
						OrdersDTO o = new OrdersDTO();
						if (rsp.getTrades().get(i).getOrders().get(j).getOid() != null) {
							o.setOid(rsp.getTrades().get(i).getOrders().get(j)
									.getOid().toString());
						}
						if (rsp.getTrades().get(i).getTid() != null) {
							o.setTid(rsp.getTrades().get(i).getTid().toString());
						}
						o.setItemMealName(rsp.getTrades().get(i).getOrders()
								.get(j).getItemMealName());
						o.setPicPath(rsp.getTrades().get(i).getOrders().get(j)
								.getPicPath());
						o.setSellerNick(rsp.getTrades().get(i).getOrders()
								.get(j).getSellerNick());
						o.setBuyerNick(rsp.getTrades().get(i).getOrders()
								.get(j).getBuyerNick());
						o.setRefundStatus(rsp.getTrades().get(i).getOrders()
								.get(j).getRefundStatus());
						o.setOuteriid(rsp.getTrades().get(i).getOrders().get(j)
								.getOuterIid());
						o.setSnapshotUrl(rsp.getTrades().get(i).getOrders()
								.get(j).getSnapshotUrl());
						o.setSnapshot(rsp.getTrades().get(i).getOrders().get(j)
								.getSnapshot());
						if(null!=rsp.getTrades().get(i).getOrders().get(j).getTimeoutActionTime()){
							o.setTimeoutActionTime(rsp.getTrades().get(i)
									.getOrders().get(j).getTimeoutActionTime().getTime());
						}
						o.setBuyerRate(rsp.getTrades().get(i).getOrders()
								.get(j).getBuyerRate());
						o.setSellerRate(rsp.getTrades().get(i).getOrders()
								.get(j).getSellerRate());
						o.setSellerType(rsp.getTrades().get(i).getOrders()
								.get(j).getSellerType());
						o.setCid(rsp.getTrades().get(i).getOrders().get(j)
								.getCid());
						o.setSubOrderTaxFee(rsp.getTrades().get(i).getOrders()
								.get(j).getSubOrderTaxFee());
						o.setSubOrderTaxRate(rsp.getTrades().get(i).getOrders()
								.get(j).getSubOrderTaxRate());
						o.setStatus(rsp.getTrades().get(i).getOrders().get(j)
								.getStatus());
						o.setTitle(rsp.getTrades().get(i).getOrders().get(j)
								.getTitle());
						o.setType(rsp.getTrades().get(i).getOrders().get(j)
								.getType());
						o.setIid(rsp.getTrades().get(i).getOrders().get(j)
								.getIid());
						if(null!=rsp.getTrades().get(i).getOrders().get(j).getPrice()){
							o.setPrice(Double.parseDouble(rsp.getTrades().get(i).getOrders().get(j)
									.getPrice()));
						}
						o.setNumIid(rsp.getTrades().get(i).getOrders().get(j)
								.getNumIid());
						o.setItemMealId(rsp.getTrades().get(i).getOrders()
								.get(j).getItemMealId());
						o.setSkuId(rsp.getTrades().get(i).getOrders().get(j)
								.getSkuId());
						o.setNum(rsp.getTrades().get(i).getOrders().get(j)
								.getNum());
						o.setOuterSkuId(rsp.getTrades().get(i).getOrders()
								.get(j).getOuterSkuId());
						o.setOrderFrom(rsp.getTrades().get(i).getOrders()
								.get(j).getOrderFrom());
						if(null!=rsp.getTrades().get(i).getOrders().get(j)
								.getTotalFee()){
							o.setTotalFee(Double.parseDouble(rsp.getTrades().get(i).getOrders().get(j)
									.getTotalFee()));
						}
						if (rsp.getTrades().get(i).getOrders().get(j)
								.getPayment() != null) {
							o.setPayment(Double.valueOf(rsp.getTrades().get(i)
									.getOrders().get(j).getPayment()));
						}
						o.setDiscountFee(rsp.getTrades().get(i).getOrders()
								.get(j).getDiscountFee());
						o.setAdjustFee(rsp.getTrades().get(i).getOrders()
								.get(j).getAdjustFee());
						if(null!=rsp.getTrades().get(i).getOrders().get(j)
								.getModified()){
							o.setModified(rsp.getTrades().get(i).getOrders().get(j)
									.getModified().getTime());
						}
						o.setSkuPropertiesName(rsp.getTrades().get(i)
								.getOrders().get(j).getSkuPropertiesName());
						o.setRefundId(rsp.getTrades().get(i).getOrders().get(j)
								.getRefundId());
						o.setIsOversold(rsp.getTrades().get(i).getOrders()
								.get(j).getIsOversold());
						o.setIsServiceOrder(rsp.getTrades().get(i).getOrders()
								.get(j).getIsServiceOrder());
						if(null!=rsp.getTrades().get(i).getOrders().get(j)
								.getEndTime()){
							o.setEndTime(rsp.getTrades().get(i).getOrders().get(j)
									.getEndTime().getTime());
						}
						o.setConsignTime(rsp.getTrades().get(i).getOrders()
								.get(j).getConsignTime());
						o.setOrderAttr(rsp.getTrades().get(i).getOrders()
								.get(j).getOrderAttr());
						o.setShippingType(rsp.getTrades().get(i).getOrders()
								.get(j).getShippingType());
						o.setBindOid(rsp.getTrades().get(i).getOrders().get(j)
								.getBindOid());
						o.setLogisticsCompany(rsp.getTrades().get(i)
								.getOrders().get(j).getLogisticsCompany());
						o.setInvoiceNo(rsp.getTrades().get(i).getOrders()
								.get(j).getInvoiceNo());
						o.setIsdaixiao(rsp.getTrades().get(i).getOrders()
								.get(j).getIsDaixiao());
						o.setDivideOrderFee(rsp.getTrades().get(i).getOrders()
								.get(j).getDivideOrderFee());
						o.setPartMjzDiscount(rsp.getTrades().get(i).getOrders()
								.get(j).getPartMjzDiscount());
						o.setTicketOuterId(rsp.getTrades().get(i).getOrders()
								.get(j).getTicketOuterId());
						o.setTicketExpdateKey(rsp.getTrades().get(i)
								.getOrders().get(j).getTicketExpdateKey());
						o.setStoreCode(rsp.getTrades().get(i).getOrders()
								.get(j).getStoreCode());
						o.setIsWww(rsp.getTrades().get(i).getOrders().get(j)
								.getIsWww());
						o.setTmserSpuCode(rsp.getTrades().get(i).getOrders()
								.get(j).getTmserSpuCode());
						o.setBindOids(rsp.getTrades().get(i).getOrders().get(j)
								.getBindOids());
						o.setZhengjiStatus(rsp.getTrades().get(i).getOrders()
								.get(j).getZhengjiStatus());
						o.setMdQualification(rsp.getTrades().get(i).getOrders()
								.get(j).getMdQualification());
						o.setMdFee(rsp.getTrades().get(i).getOrders().get(j)
								.getMdFee());
						o.setCustomization(rsp.getTrades().get(i).getOrders()
								.get(j).getCustomization());
						o.setInvType(rsp.getTrades().get(i).getOrders().get(j)
								.getInvType());
						o.setIsShShip(rsp.getTrades().get(i).getOrders().get(j)
								.getIsShShip());
						o.setShipper(rsp.getTrades().get(i).getOrders().get(j)
								.getShipper());
						o.setfType(rsp.getTrades().get(i).getOrders().get(j)
								.getfType());
						o.setfStatus(rsp.getTrades().get(i).getOrders().get(j)
								.getfStatus());
						o.setFTERM(rsp.getTrades().get(i).getOrders().get(j)
								.getfTerm());
						o.setComboId(rsp.getTrades().get(i).getOrders().get(j)
								.getComboId());
						o.setAssemblyRela(rsp.getTrades().get(i).getOrders()
								.get(j).getAssemblyRela());
						o.setAssemblyItem(rsp.getTrades().get(i).getOrders()
								.get(j).getAssemblyItem());
						o.setAssemblyPrice(rsp.getTrades().get(i).getOrders()
								.get(j).getAssemblyPrice());
						o.setCreatedBy(userId);
						o.setReceiverDistrict(rsp.getTrades().get(i)
								.getReceiverDistrict());
						o.setReceiverCity(rsp.getTrades().get(i)
								.getReceiverCity());
						o.setStepTradeStatus(rsp.getTrades().get(i)
								.getStepTradeStatus());
						o.setReceiverName(rsp.getTrades().get(i)
								.getReceiverName());
						o.setReceiverMobile(rsp.getTrades().get(i)
								.getReceiverMobile());
						if(null!=rsp.getTrades().get(i).getCreated()){
							o.setCreated(rsp.getTrades().get(i).getCreated().getTime());
						}
						if (rsp.getTrades().get(i).getBuyerFlag() != null) {
							String buyerFlag = rsp.getTrades().get(i)
									.getBuyerFlag().toString();
							o.setBuyerFlag(Integer.valueOf(buyerFlag));
						}
						if (rsp.getTrades().get(i).getSellerFlag() != null) {
							String sellerFlag = rsp.getTrades().get(i)
									.getSellerFlag().toString();
							o.setSellerFlag(Integer.valueOf(sellerFlag));
						}
						upOrders.add(o);
					}
				}
				t.setOrders(upOrders);
			}
		}
		// 将list中的数据保存到数据库
		try {
			saveOrUpdateTradeList(transactionOrderList,userId);
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	/**
	* 创建人：邱洋
	* @Title: saveOrUpdateTradeList 
	* @Description: TODO(判断订单执行update还是insert) 
	* @param @param tradeList
	* @param @param userId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void saveOrUpdateTradeList(List<TradeDTO> tradeList,String userId) {
		// 定义异常数据的记录
		if (tradeList != null && tradeList.size() > 0) {
			List<TradeDTO> upTrades = new ArrayList<TradeDTO>();
			Iterator<TradeDTO> iterator = tradeList.iterator();
			// 判断数据库是否存在该条数据==>存在-更新/不存在-保存,默认不存在
			Boolean exists = false;
			while (iterator.hasNext()) {
				TradeDTO t = iterator.next();
				try {
					exists = isExistTradeEnhance(t.getTid(),userId);
				} catch (Exception e) {
					exists = true;
				}
				if (exists) {
					upTrades.add(t);
					iterator.remove();
				}
			}
			// 保存==批量保存
			if (null!=tradeList&&tradeList.size() > 0) {
				tradeInfoService.saveTradeList(tradeList,userId);
			}
			// 更新
			if (upTrades!=null&&upTrades.size() > 0) {
				tradeInfoService.updateTradeList(upTrades,userId);
			}
		}
	}

	/**
	 * 创建人：邱洋
	 * 
	 * @Title: isExistOrdersEnhance
	 * @Description: TODO(根据tid判断订单是否存在)
	 * @param @param tid
	 * @param @param userId
	 * @param @return 设定文件
	 * @return Boolean 返回类型
	 * @throws
	 */
	public Boolean isExistTradeEnhance(String tid, String userId) {
		TradeDTO t = tradeInfoService.findOneByTid(tid, userId);
		return (t == null ? false : true);
	}
}
