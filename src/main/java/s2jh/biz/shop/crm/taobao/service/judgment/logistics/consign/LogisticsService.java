package s2jh.biz.shop.crm.taobao.service.judgment.logistics.consign;

import java.util.Date;

import net.sf.json.JSONObject;
@Deprecated 
public interface LogisticsService {

	boolean logisticsConsign(JSONObject parseJSON) throws Exception;

	boolean logisticsSentCity(JSONObject parseJSON) throws Exception;

	boolean logisticsSentScan(JSONObject parseJSON) throws Exception;

	boolean logisticsSigned(JSONObject parseJSON) throws Exception;

	boolean logisticsPuzzleRemind(JSONObject parseJSON) throws Exception;

	boolean delayRemind(String executeGenre,String orderScopeOne,String orderScopeTwo, String settingType, String sellerNick) throws Exception;

	boolean logisticsPuzzleRemind(JSONObject parseJSON, Date date, String string) throws Exception;


}
