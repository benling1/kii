package s2jh.biz.shop.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/** 
* @Title: 保留会员筛选条件
* @date 2017年5月18日 上午9:26:13
* @author jackstraw_yu    
* @throws 
*/
public class StoreMapCache {

	private static  Map<String,HashMap<String,HashMap<String,Object>>> sCMap = null;
	private static StoreMapCache mapCache = new StoreMapCache();
	
	private StoreMapCache() {
		 sCMap = 
			 new  ConcurrentHashMap<String,HashMap<String,HashMap<String,Object>>>();
	}
    public static StoreMapCache getMapCache(){
    	
        return mapCache;
    }
    
    
    /**
	 * 向当前userId下的map中添加会员
	 * */
	public void putMap(String userId,String key,HashMap<String,Object> map){
		HashMap<String,HashMap<String,Object>> hashMap = sCMap.get(userId);
		if(hashMap!=null){
			hashMap.put(key, map);
		}else{
			hashMap = new HashMap<String, HashMap<String,Object>>();
			hashMap.put(key, map);
		}
		sCMap.put(userId,hashMap);
	}
	
	/**
	 * 移除当前userId下的map中会员
	 * */
	@SuppressWarnings("null")
	public void removeMap(String userId,String key){
		HashMap<String,HashMap<String,Object>> hashMap = sCMap.get(userId);
		if(hashMap!=null){
			return;
		}else{
			hashMap.remove(key);
		}
	} 
	
	/**
	 * 获取当前userId下的map中会员
	 * */
	@SuppressWarnings("null")
	public HashMap<String, Object>  getMap(String userId,String key){
		HashMap<String,HashMap<String,Object>>  hashMap = sCMap.get(userId);
		if(hashMap == null){
			return null;
		}else{
			return hashMap.get(key);
		}
	} 

	/**
	 * 取出当前userId下的map中会员
	 * */
	@SuppressWarnings("null")
	public HashMap<String,Object> takeMap(String userId,String key){
		HashMap<String, HashMap<String,Object>> hashMap = sCMap.get(userId);
		if(hashMap == null){
			return null;
		}else{
			removeMap(userId,key);
			return hashMap.get(key);
		}
	} 
	
	/**
	 * 清空当前userId下所有数据
	 * */
	@SuppressWarnings("null")
	public void removeUserData(String userId){
		 sCMap.remove(userId);
	} 
	
}
