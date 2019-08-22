package s2jh.biz.shop.crm.tradecenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.order.service.OrderAdvancedSettingService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;

@Service
//@Transactional
public class SeniorProcessSetupService {
    
    @Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
    @Autowired
   	private TradeSetupService tradeSetupService;
    /**
     * OrderSettingInfo.CREATE_ORDER, 下单关怀 //多任务
     * 
     * **/
    public TradeSetup handleCreateOrder(OrderAdvancedSetting source,TradeSetup target){
    	target.setUserId(source.getUserId());target.setVersion(0);
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	//target.setSellerFlag("0,1,2,3,4,5");
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
		return target;
		
    }
  
 	
    /**
     * OrderSettingInfo.FIRST_PUSH_PAYMENT, 常规催付 //多任务
     * **/
    public TradeSetup handleFirstPushPayment(OrderAdvancedSetting source,TradeSetup target){
    	target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	/*if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
    		target.setSellerFlag("0,1,2,3,4,5");
    		//target.setSellerFlag(null);
    	}else{
    		//TODO阿門但願顔色順序不要錯
    		String flagColor  = "0,1,2,3,4,5";
    		if(source.getFlagcolor().contains("1"))
    			flagColor  = flagColor.replace("1,", "");
    		if(source.getFlagcolor().contains("2"))
    			flagColor  = flagColor.replace("2,", "");
    		if(source.getFlagcolor().contains("3"))
    			flagColor  = flagColor.replace("3,", "");
    		if(source.getFlagcolor().contains("4"))
    			flagColor  = flagColor.replace("4,", "");
    		if(source.getFlagcolor().contains("5"))
    			flagColor  = flagColor.replace(",5", "");
    		target.setSellerFlag(flagColor);
    	}*/
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
		return target;
	}
    
    
    /**
     * OrderSettingInfo.SECOND_PUSH_PAYMENT, "二次催付//单任务
     * **/
   	public TradeSetup handleSecondPushPayment(OrderAdvancedSetting source,TradeSetup target){
   		target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	/*if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
    		target.setSellerFlag("0,1,2,3,4,5");
    		//target.setSellerFlag(null);
    	}else{
    		//TODO阿門但願顔色順序不要錯
    		String flagColor  = "0,1,2,3,4,5";
    		if(source.getFlagcolor().contains("1"))
    			flagColor  = flagColor.replace("1,", "");
    		if(source.getFlagcolor().contains("2"))
    			flagColor  = flagColor.replace("2,", "");
    		if(source.getFlagcolor().contains("3"))
    			flagColor  = flagColor.replace("3,", "");
    		if(source.getFlagcolor().contains("4"))
    			flagColor  = flagColor.replace("4,", "");
    		if(source.getFlagcolor().contains("5"))
    			flagColor  = flagColor.replace(",5", "");
    		target.setSellerFlag(flagColor);
    	}*/
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
		return target;
   	}
    

   	/**
   	 * OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT, "聚划算催付//单任务 
   	 * */
  	public TradeSetup handlePreferentialPushPayment(OrderAdvancedSetting source,TradeSetup target){
  		target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	/*if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
    		target.setSellerFlag("0,1,2,3,4,5");
    		//target.setSellerFlag(null);
    	}else{
    		//TODO阿門但願顔色順序不要錯
    		String flagColor  = "0,1,2,3,4,5";
    		if(source.getFlagcolor().contains("1"))
    			flagColor  = flagColor.replace("1,", "");
    		if(source.getFlagcolor().contains("2"))
    			flagColor  = flagColor.replace("2,", "");
    		if(source.getFlagcolor().contains("3"))
    			flagColor  = flagColor.replace("3,", "");
    		if(source.getFlagcolor().contains("4"))
    			flagColor  = flagColor.replace("4,", "");
    		if(source.getFlagcolor().contains("5"))
    			flagColor  = flagColor.replace(",5", "");
    		target.setSellerFlag(flagColor);
    	}*/
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
		return target;
  	}
    

  	/**
  	 *OrderSettingInfo.PAYMENT_CINCERN, 付款关怀 //多任务 
  	 * **/
    public TradeSetup handlePaymentCincern(OrderAdvancedSetting source,TradeSetup target){
    	target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	//"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记"
    	if(source.getVendormark()!=null || "1".equals(source.getVendormark())){
	    	if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
	    		target.setSellerFlag("0,1,2,3,4,5");
	    		//target.setSellerFlag(null);
	    	}else{
	    		//TODO阿門但願顔色順序不要錯
	    		String flagColor  = "0,1,2,3,4,5";
	    		if(source.getFlagcolor().contains("1"))
	    			flagColor  = flagColor.replace("1,", "");
	    		if(source.getFlagcolor().contains("2"))
	    			flagColor  = flagColor.replace("2,", "");
	    		if(source.getFlagcolor().contains("3"))
	    			flagColor  = flagColor.replace("3,", "");
	    		if(source.getFlagcolor().contains("4"))
	    			flagColor  = flagColor.replace("4,", "");
	    		if(source.getFlagcolor().contains("5"))
	    			flagColor  = flagColor.replace(",5", "");
	    		target.setSellerFlag(flagColor);
	    	}
    	}else{
    		//不屏蔽
    		target.setSellerFlag("0,1,2,3,4,5");
    	}
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
		return target;
	}
    
    
 	
    /**
     * OrderSettingInfo.SHIPMENT_TO_REMIND, 发货提醒  //多任务 
     * **/
    public TradeSetup handleShipmentToRemind(OrderAdvancedSetting source,TradeSetup target){
    	target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	//"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记"
    	if(source.getVendormark()!=null || "1".equals(source.getVendormark())){
	    	if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
	    		target.setSellerFlag("0,1,2,3,4,5");
	    		//target.setSellerFlag(null);
	    	}else{
	    		//TODO阿門但願顔色順序不要錯
	    		String flagColor  = "0,1,2,3,4,5";
	    		if(source.getFlagcolor().contains("1"))
	    			flagColor  = flagColor.replace("1,", "");
	    		if(source.getFlagcolor().contains("2"))
	    			flagColor  = flagColor.replace("2,", "");
	    		if(source.getFlagcolor().contains("3"))
	    			flagColor  = flagColor.replace("3,", "");
	    		if(source.getFlagcolor().contains("4"))
	    			flagColor  = flagColor.replace("4,", "");
	    		if(source.getFlagcolor().contains("5"))
	    			flagColor  = flagColor.replace(",5", "");
	    		target.setSellerFlag(flagColor);
	    	}
    	}else{
    		//不屏蔽
    		target.setSellerFlag("0,1,2,3,4,5");
    	}
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
		return target;
	}
    
    
    /**
     * OrderSettingInfo.DELAY_SEND_REMIND, 延时发货提醒 //多任务
     * **/
    public TradeSetup handleDelaySendRemind(OrderAdvancedSetting source,TradeSetup target){
    	target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	//"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记"
    	if(source.getVendormark()!=null || "1".equals(source.getVendormark())){
	    	if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
	    		target.setSellerFlag("0,1,2,3,4,5");
	    		//target.setSellerFlag(null);
	    	}else{
	    		//TODO阿門但願顔色順序不要錯
	    		String flagColor  = "0,1,2,3,4,5";
	    		if(source.getFlagcolor().contains("1"))
	    			flagColor  = flagColor.replace("1,", "");
	    		if(source.getFlagcolor().contains("2"))
	    			flagColor  = flagColor.replace("2,", "");
	    		if(source.getFlagcolor().contains("3"))
	    			flagColor  = flagColor.replace("3,", "");
	    		if(source.getFlagcolor().contains("4"))
	    			flagColor  = flagColor.replace("4,", "");
	    		if(source.getFlagcolor().contains("5"))
	    			flagColor  = flagColor.replace(",5", "");
	    		target.setSellerFlag(flagColor);
	    	}
    	}else{
    		//不屏蔽
    		target.setSellerFlag("0,1,2,3,4,5");
    	}
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
		return target;
	}
    
    
    /**
     * OrderSettingInfo.ARRIVAL_LOCAL_REMIND, 到达同城提醒 //多任务 
     * **/
    public TradeSetup handleArrivalLocalRemind(OrderAdvancedSetting source,TradeSetup target){
    	target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//新版
    	//"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记"
    	if(source.getVendormark()!=null || "1".equals(source.getVendormark())){
	    	if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
	    		target.setSellerFlag("0,1,2,3,4,5");
	    		//target.setSellerFlag(null);
	    	}else{
	    		//TODO阿門但願顔色順序不要錯
	    		String flagColor  = "0,1,2,3,4,5";
	    		if(source.getFlagcolor().contains("1"))
	    			flagColor  = flagColor.replace("1,", "");
	    		if(source.getFlagcolor().contains("2"))
	    			flagColor  = flagColor.replace("2,", "");
	    		if(source.getFlagcolor().contains("3"))
	    			flagColor  = flagColor.replace("3,", "");
	    		if(source.getFlagcolor().contains("4"))
	    			flagColor  = flagColor.replace("4,", "");
	    		if(source.getFlagcolor().contains("5"))
	    			flagColor  = flagColor.replace(",5", "");
	    		target.setSellerFlag(flagColor);
	    	}
    	}else{
    		//不屏蔽
    		target.setSellerFlag("0,1,2,3,4,5");
    	}
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
		return target;
    }
    
    /**
     * OrderSettingInfo.SEND_GOODS_REMIND, 派件提醒   //多任务
     * */
    public TradeSetup handleSendGoodsRemind(OrderAdvancedSetting source,TradeSetup target){
    	target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	//"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记"
    	if(source.getVendormark()!=null || "1".equals(source.getVendormark())){
	    	if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
	    		target.setSellerFlag("0,1,2,3,4,5");
	    		//target.setSellerFlag(null);
	    	}else{
	    		//TODO阿門但願顔色順序不要錯
	    		String flagColor  = "0,1,2,3,4,5";
	    		if(source.getFlagcolor().contains("1"))
	    			flagColor  = flagColor.replace("1,", "");
	    		if(source.getFlagcolor().contains("2"))
	    			flagColor  = flagColor.replace("2,", "");
	    		if(source.getFlagcolor().contains("3"))
	    			flagColor  = flagColor.replace("3,", "");
	    		if(source.getFlagcolor().contains("4"))
	    			flagColor  = flagColor.replace("4,", "");
	    		if(source.getFlagcolor().contains("5"))
	    			flagColor  = flagColor.replace(",5", "");
	    		target.setSellerFlag(flagColor);
	    	}
    	}else{
    		//不屏蔽
    		target.setSellerFlag("0,1,2,3,4,5");
    	}
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
		return target;
    }

    
    /**
     * OrderSettingInfo.REMIND_SIGNFOR, 签收提醒 //多任务
     * */
    public TradeSetup handleRemindSignfor(OrderAdvancedSetting source,TradeSetup target){
    	target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	//"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记"
    	if(source.getVendormark()!=null || "1".equals(source.getVendormark())){
	    	if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
	    		target.setSellerFlag("0,1,2,3,4,5");
	    		//target.setSellerFlag(null);
	    	}else{
	    		//TODO阿門但願顔色順序不要錯
	    		String flagColor  = "0,1,2,3,4,5";
	    		if(source.getFlagcolor().contains("1"))
	    			flagColor  = flagColor.replace("1,", "");
	    		if(source.getFlagcolor().contains("2"))
	    			flagColor  = flagColor.replace("2,", "");
	    		if(source.getFlagcolor().contains("3"))
	    			flagColor  = flagColor.replace("3,", "");
	    		if(source.getFlagcolor().contains("4"))
	    			flagColor  = flagColor.replace("4,", "");
	    		if(source.getFlagcolor().contains("5"))
	    			flagColor  = flagColor.replace(",5", "");
	    		target.setSellerFlag(flagColor);
	    	}
    	}else{
    		//不屏蔽
    		target.setSellerFlag("0,1,2,3,4,5");
    	}
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
		return target;
	}
    
    
    /**
     *OrderSettingInfo.COWRY_CARE, 宝贝关怀 //多任务 
     * */
    public TradeSetup handleCowryCare(OrderAdvancedSetting source,TradeSetup target){
    	target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	/*if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}*/
    	//此处有坑,宝贝关怀的商品选择在短信设置里
    	//TODO
    	if(source.getItemId()!=null && !"".equals(source.getItemId())){
    		target.setProductType(true);
			target.setProducts(source.getItemId());
    	}
		return target;
	}
    
   
   
    
    /**
     * OrderSettingInfo.RETURNED_PAYEMNT, 回款提醒  //多任务 
     * **/
    //TODO
    public TradeSetup handleReturnedPayemnt(OrderAdvancedSetting source,TradeSetup target){
		return target;}
    
	
    /**
     * OrderSettingInfo.REFUND_CREATED, "买家申请退款//单任务
     * **/
  	public TradeSetup handleRefundCreated(OrderAdvancedSetting source,TradeSetup target){
  		target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	//"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记"
    	if(source.getVendormark()!=null || "1".equals(source.getVendormark())){
	    	if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
	    		target.setSellerFlag("0,1,2,3,4,5");
	    		//target.setSellerFlag(null);
	    	}else{
	    		//TODO阿門但願顔色順序不要錯
	    		String flagColor  = "0,1,2,3,4,5";
	    		if(source.getFlagcolor().contains("1"))
	    			flagColor  = flagColor.replace("1,", "");
	    		if(source.getFlagcolor().contains("2"))
	    			flagColor  = flagColor.replace("2,", "");
	    		if(source.getFlagcolor().contains("3"))
	    			flagColor  = flagColor.replace("3,", "");
	    		if(source.getFlagcolor().contains("4"))
	    			flagColor  = flagColor.replace("4,", "");
	    		if(source.getFlagcolor().contains("5"))
	    			flagColor  = flagColor.replace(",5", "");
	    		target.setSellerFlag(flagColor);
	    	}
    	}else{
    		//不屏蔽
    		target.setSellerFlag("0,1,2,3,4,5");
    	}
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
  		return target;
  			
  	}
  	    
  	
  	/**
  	 * OrderSettingInfo.REFUND_SUCCESS, "退款成功//单任务
  	 * **/
  	public TradeSetup handleRefundSuccess(OrderAdvancedSetting source,TradeSetup target){
  		target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	//"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记"
    	if(source.getVendormark()!=null || "1".equals(source.getVendormark())){
	    	if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
	    		target.setSellerFlag("0,1,2,3,4,5");
	    		//target.setSellerFlag(null);
	    	}else{
	    		//TODO阿門但願顔色順序不要錯
	    		String flagColor  = "0,1,2,3,4,5";
	    		if(source.getFlagcolor().contains("1"))
	    			flagColor  = flagColor.replace("1,", "");
	    		if(source.getFlagcolor().contains("2"))
	    			flagColor  = flagColor.replace("2,", "");
	    		if(source.getFlagcolor().contains("3"))
	    			flagColor  = flagColor.replace("3,", "");
	    		if(source.getFlagcolor().contains("4"))
	    			flagColor  = flagColor.replace("4,", "");
	    		if(source.getFlagcolor().contains("5"))
	    			flagColor  = flagColor.replace(",5", "");
	    		target.setSellerFlag(flagColor);
	    	}
    	}else{
    		//不屏蔽
    		target.setSellerFlag("0,1,2,3,4,5");
    	}
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
  		return target;
  	}

  	/**
  	 * OrderSettingInfo.REFUND_AGREE, "同意退款//单任务
  	 * **/
  	public TradeSetup handleRefundAgree(OrderAdvancedSetting source,TradeSetup target){
  		target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	//"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记"
    	if(source.getVendormark()!=null || "1".equals(source.getVendormark())){
	    	if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
	    		target.setSellerFlag("0,1,2,3,4,5");
	    		//target.setSellerFlag(null);
	    	}else{
	    		//TODO阿門但願顔色順序不要錯
	    		String flagColor  = "0,1,2,3,4,5";
	    		if(source.getFlagcolor().contains("1"))
	    			flagColor  = flagColor.replace("1,", "");
	    		if(source.getFlagcolor().contains("2"))
	    			flagColor  = flagColor.replace("2,", "");
	    		if(source.getFlagcolor().contains("3"))
	    			flagColor  = flagColor.replace("3,", "");
	    		if(source.getFlagcolor().contains("4"))
	    			flagColor  = flagColor.replace("4,", "");
	    		if(source.getFlagcolor().contains("5"))
	    			flagColor  = flagColor.replace(",5", "");
	    		target.setSellerFlag(flagColor);
	    	}
    	}else{
    		//不屏蔽
    		target.setSellerFlag("0,1,2,3,4,5");
    	}
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
  		return target;
  	}
  	    
  	/**
  	 *OrderSettingInfo.REFUND_REFUSE, "拒绝退款//单任务 
  	 * **/
  	public TradeSetup handleRefundRefuse(OrderAdvancedSetting source,TradeSetup target){
  		target.setUserId(source.getUserId());target.setVersion(0);;
    	target.setType(source.getSettingType());
    	//默认全部为null
    	//0--默认全部    --省份汉字全称用,隔开
    	if(source.getLocality()!=null && !"0".equals(source.getLocality())){
    		target.setProvince(source.getLocality().trim().replace("北京市", "北京").replace("天津市", "天津").replace("上海市", "上海").replace("重庆市", "重庆"));
    	}
    	//订单来源 0-不限 1-Pc端 2-移动端
    	if(source.getOrderSource()!=null && !"0".equals(source.getOrderSource())){
    		if("1".equals(source.getOrderSource())){
    			target.setTradeFrom("TAOBAO");
    		}else if("2".equals(source.getOrderSource())){
    			target.setTradeFrom("WAP");
    		}
    	}
    	//新版
    	//"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记"
    	if(source.getVendormark()!=null || "1".equals(source.getVendormark())){
	    	if(source.getFlagcolor()==null || "".equals(source.getFlagcolor())){
	    		target.setSellerFlag("0,1,2,3,4,5");
	    		//target.setSellerFlag(null);
	    	}else{
	    		//TODO阿門但願顔色順序不要錯
	    		String flagColor  = "0,1,2,3,4,5";
	    		if(source.getFlagcolor().contains("1"))
	    			flagColor  = flagColor.replace("1,", "");
	    		if(source.getFlagcolor().contains("2"))
	    			flagColor  = flagColor.replace("2,", "");
	    		if(source.getFlagcolor().contains("3"))
	    			flagColor  = flagColor.replace("3,", "");
	    		if(source.getFlagcolor().contains("4"))
	    			flagColor  = flagColor.replace("4,", "");
	    		if(source.getFlagcolor().contains("5"))
	    			flagColor  = flagColor.replace(",5", "");
	    		target.setSellerFlag(flagColor);
	    	}
    	}else{
    		//不屏蔽
    		target.setSellerFlag("0,1,2,3,4,5");
    	}
    	//商品选择 0-全部商品 1-指定商品 2-排除指定商品
    	if(source.getProductSelect()!=null ||  !"".equals(source.getProductSelect()) 
    			|| !"0".equals(source.getProductSelect())){
    		if("1".equals(source.getProductSelect())){
    			target.setProductType(true);
    			target.setProducts(source.getItemId());
    		}else if("2".equals(source.getProductSelect())){
    			target.setProductType(false);
    			target.setProducts(source.getItemId());
    		}
    	}
  		return target;
  	}
  	   
 
  		
  	
  	   
  	//OrderSettingInfo.AUTO_RATE, "自动评价//单任务
  	//public TradeSetup handleAutoRate(OrderAdvancedSetting source,TradeSetup target){
  			//return target;}
  	    
  	//OrderSettingInfo.APPRAISE_MONITORING_ORDER, "中差评监控//单任务
  	//public TradeSetup handleAppraiseMonitoringOrder(OrderAdvancedSetting source,TradeSetup target){
  			//return target;}
  	   
  	//OrderSettingInfo.APPRAISE_PACIFY_ORDER, "中差评安抚//单任务
  	//public TradeSetup handleAppraisePacifyOrder(OrderAdvancedSetting source,TradeSetup target){
  			//return target;}
  	    
  	//OrderSettingInfo.GOOD_VALUTION_REMIND, "好评提醒//单任务
  	//public TradeSetup handleGoodValutionRemind(OrderAdvancedSetting source,TradeSetup target){
  			//return target;}
  	   
  
  	
  	
  	
  	
  	//================================================================================================================
    
  	public void processData(List<OrderAdvancedSetting> list) {
		for (OrderAdvancedSetting setup : list) {
			if(setup.getUserId()==null || "".equals(setup.getUserId())
				/*|| setup.getStatus()==null || "".equals(setup.getStatus())*/
				|| setup.getSettingType()==null || "".equals(setup.getSettingType())
				//手动订单提醒
				|| OrderSettingInfo.ORDER_MANUAL_REMIND.equals(setup.getSettingType())
				//疑难件提醒
				|| OrderSettingInfo.ABNORMAL_GOODS_REMIND.equals(setup.getSettingType())
				//
				|| OrderSettingInfo.RETURNED_PAYEMNT.equals(setup.getSettingType())
			)
				continue;
			mappingHandleData(setup);
		}
	}
	
	private void mappingHandleData(OrderAdvancedSetting source) {
		TradeSetup target = null;
		if(OrderSettingInfo.CREATE_ORDER.equals(source.getSettingType())){
			// 下单关怀 //多任务
			target = handleCreateOrder(source,new TradeSetup());
		 }else if(OrderSettingInfo.FIRST_PUSH_PAYMENT.equals(source.getSettingType())){
			 //常规催付 //多任务
		    target = handleFirstPushPayment(source,new TradeSetup());
		 }else if(OrderSettingInfo.SECOND_PUSH_PAYMENT.equals(source.getSettingType())){
			 //二次催付//单任务
		    target = handleSecondPushPayment(source,new TradeSetup());
		 }else if(OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT.equals(source.getSettingType())){
			 //聚划算催付//单任务
		   	target = handlePreferentialPushPayment(source,new TradeSetup());
		 }else if(OrderSettingInfo.PAYMENT_CINCERN.equals(source.getSettingType())){
			 //付款关怀 //多任务
		    target = handlePaymentCincern(source,new TradeSetup());
		 }else if(OrderSettingInfo.SHIPMENT_TO_REMIND.equals(source.getSettingType())){
			 //发货提醒  //多任务
		    target = handleShipmentToRemind(source,new TradeSetup());
		 }else if(OrderSettingInfo.DELAY_SEND_REMIND.equals(source.getSettingType())){
			 //延时发货提醒 //多任务
		     target = handleDelaySendRemind(source,new TradeSetup());
		 }else if(OrderSettingInfo.ARRIVAL_LOCAL_REMIND.equals(source.getSettingType())){
			 //到达同城提醒 //多任务
		     target = handleArrivalLocalRemind(source,new TradeSetup());
		 }else if(OrderSettingInfo.SEND_GOODS_REMIND.equals(source.getSettingType())){
			 //派件提醒   //多任务
		     target = handleSendGoodsRemind(source,new TradeSetup());
		 }else if(OrderSettingInfo.REMIND_SIGNFOR.equals(source.getSettingType())){
			 //签收提醒 //多任务
		     target = handleRemindSignfor(source,new TradeSetup());
		 }else if(OrderSettingInfo.COWRY_CARE.equals(source.getSettingType())){
			 //宝贝关怀 //多任务
		     target = handleCowryCare(source,new TradeSetup());
		 }else if(OrderSettingInfo.RETURNED_PAYEMNT.equals(source.getSettingType())){
			 //回款提醒  //多任务
		     target = handleReturnedPayemnt(source,new TradeSetup());
		 }else if(OrderSettingInfo.REFUND_CREATED.equals(source.getSettingType())){
			 //买家申请退款//单任务
		   	target = handleRefundCreated(source,new TradeSetup());
		 }else if(OrderSettingInfo.REFUND_SUCCESS.equals(source.getSettingType())){
			 //退款成功//单任务
		   	target = handleRefundSuccess(source,new TradeSetup());
		 }else if(OrderSettingInfo.REFUND_AGREE.equals(source.getSettingType())){
			 //同意退款//单任务
		   	target = handleRefundAgree(source,new TradeSetup());
		 }else if(OrderSettingInfo.REFUND_REFUSE.equals(source.getSettingType())){
			 //拒绝退款//单任务
		   	target = handleRefundRefuse(source,new TradeSetup());
		 }
		if(target != null){
			//根据userId&type判断存在与否
			if(tradeSetupService.isExist(target.getUserId(),target.getType())){
				//存在更新
				tradeSetupService.updateTradeSetupSync(target);
			}else{
				//不存在保存
				tradeSetupService.saveTradeSetupSync(target);
			}
		}	   
	}

    
	

	   
	   
	   
	
	
	
}
