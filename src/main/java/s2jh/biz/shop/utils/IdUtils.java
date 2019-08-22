package s2jh.biz.shop.utils;

import s2jh.biz.shop.crm.manage.util.idworker.TradeIdWorker;

public class IdUtils {
	/**  
     * 生成订单编号  
     * @return  
     */    
    public static String getTradeId() {    
    	long nextId = TradeIdWorker.getInstance().nextId();
		return String.valueOf(nextId);
    }
}
