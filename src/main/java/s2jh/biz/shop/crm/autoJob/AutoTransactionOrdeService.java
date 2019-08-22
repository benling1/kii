package s2jh.biz.shop.crm.autoJob;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.order.entity.PromotionDetails;
import s2jh.biz.shop.crm.order.entity.ServiceOrders;
import s2jh.biz.shop.crm.order.entity.TradeExt;
import s2jh.biz.shop.crm.order.entity.TransactionOrder;
import s2jh.biz.shop.crm.order.service.OrderService;
import s2jh.biz.shop.crm.order.service.PromotionDetailsService;
import s2jh.biz.shop.crm.order.service.ServiceOrdersService;
import s2jh.biz.shop.crm.order.service.TradeExtService;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;

@Service
@Transactional
public class AutoTransactionOrdeService {
	
	@Autowired
	private TransactionOrderService transactionOrderService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PromotionDetailsService promotionDetailsService;
	
	@Autowired
	private ServiceOrdersService serviceOrdersService;
	
	@Autowired
	private TradeExtService tradeExtService;
	
	@Autowired
	private UserInfoService userInfoService;
		
	
	/**
	 * 根据卖家淘宝编号更新订单信息，更新时间为一天
	 * @throws ApiException
	 */
	public void wirteOrdersInfo() throws ApiException{
			//获取CRM的地址，APPkey，secret
			TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
			TradesSoldGetRequest req = new TradesSoldGetRequest();
			req.setFields("tid,seller_nick,pic_path,payment,seller_rate,post_fee,receiver_name,receiver_state,receiver_address,+"
			 + "receiver_zip,receiver_mobile,receiver_phone,consign_time,received_payment,receiver_country,receiver_town,+"
			 + "order_tax_fee,shop_pick,num,num_iid,status,title,type,price,discount_fee,total_fee,created,pay_time,modified,+"
			 + "end_time,seller_flag,buyer_nick,has_buyer_message,credit_card_fee,step_trade_status,step_paid_fee,mark_desc,shipping_type,+"
			 + "adjust_fee,trade_from,buyer_rate,receiver_city,receiver_district,o2o,o2o_guide_id,o2o_shop_id,o2o_guide_name,o2o_shop_name,+"
			 + "o2o_delivery,rx_audit_status,post_gate_declare,cross_bonded_declare");
			
			//获取当前日期和前一天日期，根据日期查询订单数据
			Calendar calendar = Calendar.getInstance();//获取的是系统当前时间
			String todayDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	        calendar.add(Calendar.DATE, -1);    //得到前一天
	        String  yestedayDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());        
		 
			//req.setStartCreated(StringUtils.parseDateTime(todayDate));//查询开始日期	       
			//req.setEndCreated(StringUtils.parseDateTime(yestedayDate));//查询结束日期
	        
	        //测试变量  
	        req.setStartCreated(StringUtils.parseDateTime("2016-08-18 00:00:00"));
	        req.setEndCreated(StringUtils.parseDateTime("2016-08-20 00:00:00"));
			//req.setStatus("ALL_WAIT_PAY");//订单状态
			
			TradesSoldGetResponse rsp = client.execute(req, "");//根据必要条件调用淘宝接口取数据		 
			
			for(int i=0;i<rsp.getTrades().size();i++){
			
			 if(rsp.getTrades()!=null){	
				 TransactionOrder or = new TransactionOrder();//获取主交易订单对象
				 or.setTid(Long.toString(rsp.getTrades().get(i).getTid()));
				 or.setSellerNick(rsp.getTrades().get(i).getSellerNick());
				 or.setPicPath(rsp.getTrades().get(i).getPicPath());
				 or.setPayment(Double.parseDouble(rsp.getTrades().get(i).getPayment()));
				 or.setSellerRate(rsp.getTrades().get(i).getSellerRate());
				 or.setPostFee(rsp.getTrades().get(i).getPostFee());
				 or.setReceiverName(rsp.getTrades().get(i).getReceiverName());
				 or.setReceiverState(rsp.getTrades().get(i).getReceiverState());
				 or.setReceiverAddress(rsp.getTrades().get(i).getReceiverAddress());
				 or.setReceiverZip(rsp.getTrades().get(i).getReceiverZip());
				 or.setReceiverMobile(rsp.getTrades().get(i).getReceiverMobile());
				 or.setReceiverPhone(rsp.getTrades().get(i).getReceiverPhone());
				 or.setConsignTime(rsp.getTrades().get(i).getConsignTime());
				 or.setReceivedPayment(rsp.getTrades().get(i).getReceivedPayment());
				 or.setReceiverCountry(rsp.getTrades().get(i).getReceiverCountry());
				 or.setReceiverTown(rsp.getTrades().get(i).getReceiverTown());
				 if(rsp.getTrades().get(i).getOrderTaxFee()!=null){
					 or.setOrderTaxFee(Integer.parseInt(rsp.getTrades().get(i).getOrderTaxFee()));
				 }			 
				 or.setShopPick(rsp.getTrades().get(i).getShopPick());
				 if(rsp.getTrades().get(i).getNum()!=null){
					 or.setNum(Long.toString(rsp.getTrades().get(i).getNum()));
				 }			 
				 if(rsp.getTrades().get(i).getNumIid()!=null){
					 or.setNUM_IID(Long.toString(rsp.getTrades().get(i).getNumIid()));
				}			
				 or.setStatus(rsp.getTrades().get(i).getStatus());
				 or.setTitle(rsp.getTrades().get(i).getTitle());
				 or.setType(rsp.getTrades().get(i).getType());
				 or.setPrice(rsp.getTrades().get(i).getPrice());
				 or.setDiscountFee(rsp.getTrades().get(i).getDiscountFee());
				 or.setTotalFee(rsp.getTrades().get(i).getTotalFee());
				 or.setCreated(rsp.getTrades().get(i).getCreated());
				 or.setPayTime(rsp.getTrades().get(i).getPayTime());
				 or.setModified(rsp.getTrades().get(i).getModified());
				 or.setEndTime(rsp.getTrades().get(i).getEndTime());
				 if(rsp.getTrades().get(i).getSellerFlag()!=null){
					or.setSellerFlag(rsp.getTrades().get(i).getSellerFlag().toString());
				 }			 
				 or.setBuyerNick(rsp.getTrades().get(i).getBuyerNick());
				 or.setTradeAttr(rsp.getTrades().get(i).getTradeAttr());
				 or.setCreditCardFee(rsp.getTrades().get(i).getCreditCardFee());
				 or.setStepTradeStatus(rsp.getTrades().get(i).getStepTradeStatus());
				 or.setStepPaidFee(rsp.getTrades().get(i).getStepPaidFee());
				 or.setMarkDesc(rsp.getTrades().get(i).getMarkDesc());
				 or.setShippingType(rsp.getTrades().get(i).getShippingType());	
				 or.setAdjustFee(rsp.getTrades().get(i).getAdjustFee());
				 or.setTradeFrom(rsp.getTrades().get(i).getTradeFrom());
				 or.setBuyerRate(rsp.getTrades().get(i).getBuyerRate());
				 or.setReceiverCity(rsp.getTrades().get(i).getReceiverCity());
				 or.setReceiverDistrict(rsp.getTrades().get(i).getReceiverDistrict());
				 or.setO2o(rsp.getTrades().get(i).getO2o());
				 or.setO2oGuideId(rsp.getTrades().get(i).getO2oGuideId());
				 or.setO2oShopId(rsp.getTrades().get(i).getO2oShopId());
				 or.setO2oGuideName(rsp.getTrades().get(i).getO2oGuideName());
				 or.setO2oShopName(rsp.getTrades().get(i).getO2oShopName());
				 or.setO2oDelivery(rsp.getTrades().get(i).getO2oDelivery());
				 or.setRxAuditStatus(rsp.getTrades().get(i).getRxAuditStatus());
				 or.setPostGateDeclare(rsp.getTrades().get(i).getPostGateDeclare());
				 or.setCrossBondedDeclare(rsp.getTrades().get(i).getCrossBondedDeclare());
				 
				 transactionOrderService.save(or); 
			 }
			 
			 //更新订单列表数据			 		 
			 if(rsp.getTrades().get(i).getOrders()!=null){
				 Orders od= new Orders();
				 if(rsp.getTrades().get(i).getOrders().get(i).getOid()!=null){
					 od.setOid(rsp.getTrades().get(i).getOrders().get(i).getOid().toString());
				 }				
				 //od.setTid(rsp.getTrades().get(i).getTid());
				 od.setItemMealName(rsp.getTrades().get(i).getOrders().get(i).getItemMealName());
				 od.setPicPath(rsp.getTrades().get(i).getOrders().get(i).getPicPath());
				 od.setSellerNick(rsp.getTrades().get(i).getOrders().get(i).getSellerNick());
				 od.setBuyerNick(rsp.getTrades().get(i).getOrders().get(i).getBuyerNick());
				 od.setRefundStatus(rsp.getTrades().get(i).getOrders().get(i).getRefundStatus());
				 od.setOuteriid(rsp.getTrades().get(i).getOrders().get(i).getOuterIid());
				 od.setSnapshot(rsp.getTrades().get(i).getOrders().get(i).getSnapshot());
				 od.setSnapshotUrl(rsp.getTrades().get(i).getOrders().get(i).getSnapshotUrl());
				 od.setTimeoutActionTime(rsp.getTrades().get(i).getOrders().get(i).getTimeoutActionTime());
				 od.setBuyerRate(rsp.getTrades().get(i).getOrders().get(i).getBuyerRate());
				 od.setSellerRate(rsp.getTrades().get(i).getOrders().get(i).getSellerRate());
				 od.setSellerType(rsp.getTrades().get(i).getOrders().get(i).getSellerType());
				 od.setCid(rsp.getTrades().get(i).getOrders().get(i).getCid());
				 od.setSubOrderTaxFee(rsp.getTrades().get(i).getOrders().get(i).getSubOrderTaxFee());
				 od.setSubOrderTaxRate(rsp.getTrades().get(i).getOrders().get(i).getSubOrderTaxRate());				
				 od.setStatus(rsp.getTrades().get(i).getOrders().get(i).getStatus()); 
				 od.setTitle(rsp.getTrades().get(i).getOrders().get(i).getTitle());
				 od.setType(rsp.getTrades().get(i).getOrders().get(i).getType());
				 od.setIid(rsp.getTrades().get(i).getOrders().get(i).getIid());
				 od.setPrice(rsp.getTrades().get(i).getOrders().get(i).getPrice());
				 od.setNumIid(rsp.getTrades().get(i).getOrders().get(i).getNumIid());
				 od.setItemMealId(rsp.getTrades().get(i).getOrders().get(i).getItemMealId());
				 od.setSkuId(rsp.getTrades().get(i).getOrders().get(i).getSkuId());
				 od.setNum(rsp.getTrades().get(i).getOrders().get(i).getNum());
				 od.setOuterSkuId(rsp.getTrades().get(i).getOrders().get(i).getOuterSkuId());
				 od.setOrderFrom(rsp.getTrades().get(i).getOrders().get(i).getOrderFrom());
				 od.setTotalFee(rsp.getTrades().get(i).getOrders().get(i).getTotalFee());
				 od.setPayment(Double.parseDouble(rsp.getTrades().get(i).getOrders().get(i).getPayment()));
				 od.setDiscountFee(rsp.getTrades().get(i).getOrders().get(i).getDiscountFee());
				 od.setAdjustFee(rsp.getTrades().get(i).getOrders().get(i).getAdjustFee());
				 od.setModified(rsp.getTrades().get(i).getOrders().get(i).getModified());
				 od.setSkuPropertiesName(rsp.getTrades().get(i).getOrders().get(i).getSkuPropertiesName());
				 od.setRefundId(rsp.getTrades().get(i).getOrders().get(i).getRefundId());
				 od.setIsOversold(rsp.getTrades().get(i).getOrders().get(i).getIsOversold());
				 od.setIsServiceOrder(rsp.getTrades().get(i).getOrders().get(i).getIsServiceOrder());
				 od.setEndTime(rsp.getTrades().get(i).getOrders().get(i).getEndTime());
				 od.setConsignTime(rsp.getTrades().get(i).getOrders().get(i).getConsignTime());
				 od.setOrderAttr(rsp.getTrades().get(i).getOrders().get(i).getOrderAttr());
				 od.setShippingType(rsp.getTrades().get(i).getOrders().get(i).getShippingType());
				 od.setBindOid(rsp.getTrades().get(i).getOrders().get(i).getBindOid());
				 od.setLogisticsCompany(rsp.getTrades().get(i).getOrders().get(i).getLogisticsCompany());
				 od.setInvoiceNo(rsp.getTrades().get(i).getOrders().get(i).getInvoiceNo());
				 od.setIsdaixiao(rsp.getTrades().get(i).getOrders().get(i).getIsDaixiao());
				 od.setDivideOrderFee(rsp.getTrades().get(i).getOrders().get(i).getDivideOrderFee());
				 od.setPartMjzDiscount(rsp.getTrades().get(i).getOrders().get(i).getPartMjzDiscount());
				 od.setTicketOuterId(rsp.getTrades().get(i).getOrders().get(i).getTicketOuterId());
				 od.setTicketExpdateKey(rsp.getTrades().get(i).getOrders().get(i).getTicketExpdateKey());
				 od.setStoreCode(rsp.getTrades().get(i).getOrders().get(i).getStoreCode());
				 od.setIsWww(rsp.getTrades().get(i).getOrders().get(i).getIsWww());
				 od.setTmserSpuCode(rsp.getTrades().get(i).getOrders().get(i).getTmserSpuCode());
				 od.setBindOids(rsp.getTrades().get(i).getOrders().get(i).getBindOids());
				 od.setZhengjiStatus(rsp.getTrades().get(i).getOrders().get(i).getZhengjiStatus());
				 od.setMdQualification(rsp.getTrades().get(i).getOrders().get(i).getMdQualification());
				 od.setMdFee(rsp.getTrades().get(i).getOrders().get(i).getMdFee());
				 od.setCustomization(rsp.getTrades().get(i).getOrders().get(i).getCustomization());
				 od.setInvType(rsp.getTrades().get(i).getOrders().get(i).getInvType());
				 od.setIsShShip(rsp.getTrades().get(i).getOrders().get(i).getIsShShip());
				 od.setShipper(rsp.getTrades().get(i).getOrders().get(i).getShipper());
				 od.setFTERM(rsp.getTrades().get(i).getOrders().get(i).getfType());
				 od.setfStatus(rsp.getTrades().get(i).getOrders().get(i).getfStatus());
				 od.setFTERM(rsp.getTrades().get(i).getOrders().get(i).getfTerm());
				 od.setComboId(rsp.getTrades().get(i).getOrders().get(i).getComboId());
				 od.setAssemblyRela(rsp.getTrades().get(i).getOrders().get(i).getAssemblyRela());
				 od.setAssemblyPrice(rsp.getTrades().get(i).getOrders().get(i).getAssemblyPrice());
				 od.setAssemblyItem(rsp.getTrades().get(i).getOrders().get(i).getAssemblyItem());
				 orderService.save(od);
			 }
			 
			 //更新优惠信息
			 if(rsp.getTrades().get(i).getPromotionDetails()!=null){
				 PromotionDetails pd= new PromotionDetails();
				 if(rsp.getTrades().get(i).getPromotionDetails().get(i).getId()!=null){
					 pd.setOid(rsp.getTrades().get(i).getPromotionDetails().get(i).getId().toString()); 
				 }
				 pd.setPromotionName(rsp.getTrades().get(i).getPromotionDetails().get(i).getPromotionName());
				 pd.setDiscountFee(rsp.getTrades().get(i).getPromotionDetails().get(i).getDiscountFee());
				 pd.setGiftItemId(rsp.getTrades().get(i).getPromotionDetails().get(i).getGiftItemId());
				 pd.setGiftItemName(rsp.getTrades().get(i).getPromotionDetails().get(i).getGiftItemName());
				 pd.setGiftItemNum(rsp.getTrades().get(i).getPromotionDetails().get(i).getGiftItemNum());
				 pd.setPromotionId(rsp.getTrades().get(i).getPromotionDetails().get(i).getPromotionId());
				 pd.setPromotionDesc(rsp.getTrades().get(i).getPromotionDetails().get(i).getPromotionDesc());
				 
				 promotionDetailsService.save(pd);
			 }
			 
			 //更新虚拟订单信息
			 if(rsp.getTrades().get(i).getServiceOrders()!=null){
				ServiceOrders so= new ServiceOrders();				
				so.setOid(rsp.getTrades().get(i).getServiceOrders().get(i).getOid());
				so.setOid(rsp.getTrades().get(i).getServiceOrders().get(i).getItemOid());				
				so.setServiceId(rsp.getTrades().get(i).getServiceOrders().get(i).getServiceId());
				so.setServiceDetailUrl(rsp.getTrades().get(i).getServiceOrders().get(i).getServiceDetailUrl());
				so.setNum(rsp.getTrades().get(i).getServiceOrders().get(i).getNum());
				so.setPrice(rsp.getTrades().get(i).getServiceOrders().get(i).getPrice());
				so.setPayment(rsp.getTrades().get(i).getServiceOrders().get(i).getPayment());
				so.setTitle(rsp.getTrades().get(i).getServiceOrders().get(i).getTitle());
				so.setTotalFee(rsp.getTrades().get(i).getServiceOrders().get(i).getTotalFee());
				so.setBuyerNick(rsp.getTrades().get(i).getServiceOrders().get(i).getBuyerNick());
				so.setRefundId(rsp.getTrades().get(i).getServiceOrders().get(i).getRefundId());
				so.setSellerNick(rsp.getTrades().get(i).getServiceOrders().get(i).getSellerNick());
				so.setPicPath(rsp.getTrades().get(i).getServiceOrders().get(i).getPicPath());
				so.setTmserSpuCode(rsp.getTrades().get(i).getServiceOrders().get(i).getTmserSpuCode());
				
				serviceOrdersService.save(so);
			 }
			 
			 //更新交易扩展信息表
			 if(rsp.getTrades().get(i).getTradeExt()!=null){
				 TradeExt te = new TradeExt();
				 te.setTid(rsp.getTrades().get(i).getTid());
				 te.setBeforeEnableFlag(rsp.getTrades().get(i).getTradeExt().getBeforeEnableFlag());
				 te.setBeforeCloseFlag(rsp.getTrades().get(i).getTradeExt().getBeforeCloseFlag());
				 te.setBeforePayFlag(rsp.getTrades().get(i).getTradeExt().getBeforePayFlag());
				 te.setBeforeShipFlag(rsp.getTrades().get(i).getTradeExt().getBeforeShipFlag());
				 te.setBeforeConfirmFlag(rsp.getTrades().get(i).getTradeExt().getBeforeConfirmFlag());
				 te.setBeforeRateFlag(rsp.getTrades().get(i).getTradeExt().getBeforeRateFlag());
				 te.setBeforeRefundFlag(rsp.getTrades().get(i).getTradeExt().getBeforeRefundFlag());
				 te.setBeforeModifyFlag(rsp.getTrades().get(i).getTradeExt().getBeforeModifyFlag());
				 te.setThirdPartyStatus(rsp.getTrades().get(i).getTradeExt().getThirdPartyStatus());
				 te.setExtAttributes(rsp.getTrades().get(i).getTradeExt().getExtAttributes());
				 te.setExtraData(rsp.getTrades().get(i).getTradeExt().getExtraData());
				 
				 tradeExtService.save(te);
			 }
		 }
	}	
	
	public static void main(String[] args) {
		/*Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
		String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);    //得到前一天
        String  yestedayDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        System.out.println(yestedayDate);
        System.out.println(today);
		       */
		List<UserInfo> list = new ArrayList<UserInfo>();
		//UserInfoService us = new UserInfoService();
		//list = us.getUserInfoAll();
		System.out.println(list.size());
       } 
}
