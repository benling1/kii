package s2jh.biz.shop.crm.manage.base;

import java.util.HashMap;

import s2jh.biz.shop.utils.JsonUtil;

/**   
 * @Title: BaseController.java
 * @Description: controller 类 基类
 * @author zlp
 * @date 2017年5月27日 上午9:50:17
 * @version V1.0
 */
public class BaseController extends BaseComponent {
	/** 返回结果JSON */
	protected String rsJson(int rc, String msg){
		return rsMap(rc, msg).toJson();
	}
	
	/** 返回结果封装类 */
	protected static class ResultJson {
		private HashMap<String, Object> map = new HashMap<String, Object>();
		
		public ResultJson() {
			map.put("rc", 0);
			map.put("msg", "");
		}
		public ResultJson(String msg) {
			map.put("rc", 0);
			map.put("msg", msg);
		}
		public ResultJson(int rc, String msg) {
			map.put("rc", rc);
			map.put("msg", msg);
		}
		public ResultJson put(String key, Object value){
			map.put(key, value);
			return this;
		}
		public HashMap<String, Object> map(){
			return map;
		}
		public String build(){
			return JsonUtil.toJson(map);
		}
	}
}
