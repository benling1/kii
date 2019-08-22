package s2jh.biz.shop.crm.manage.handler;

import java.util.Map;

import org.apache.log4j.Logger;

import lab.s2jh.core.handler.Handler;
import lab.s2jh.core.handler.exception.HandlerException;
import s2jh.biz.shop.crm.manage.entity.LogType;
import s2jh.biz.shop.crm.view.vo.HttpResult;

/**
 * 类名称：FetchOrderHandler <br/>
 * 类描述：拉取sysInfo 订单数据 <br/>
 * 创建时间：2017年05月20日 下午7:17:10 <br/>
 * 
 * @author zlp
 * @version V1.0
 */
@SuppressWarnings({ "rawtypes","unchecked"})
public abstract class LogAccessForwardHandler implements Handler {

	private static final Logger loger = Logger.getLogger(LogAccessForwardHandler.class);

	public void doHandle(Map map) throws HandlerException {
		LogType loginType = (LogType)map.get(LogType.class.getName());
		loger.info(loginType);
		String result=null;
		if(LogType.LOGIN_TYPE == loginType) {
			result= handleLoginLogType(map);
		}
		else if(LogType.ORDER_TYPE == loginType) {
			result= handleOrderLogType(map);
		}
		else if(LogType.SENDORDER_TYPE == loginType) {
			result= handleSendOrderLogType(map);
		}
		else if(LogType.ACESSDB_TYPE == loginType) {
			result= handleAccessDBLogType(map);
		}
		else if(LogType.BATCH_LOG_TYPE == loginType){
			result= handleBatchLogType(map);
		}
		else{
		    throw new RuntimeException("错误的日志上传类型："+loginType);
		}
		if(result!=null){
			map.put(HttpResult.class.getName(), result);
			log(map);
		}
	}
	protected abstract String handleLoginLogType(Map map);
	protected abstract String handleOrderLogType(Map map);
	protected abstract String handleSendOrderLogType(Map map);
	protected abstract String handleAccessDBLogType(Map map);
	protected abstract String handleBatchLogType(Map map);
	protected abstract void log(Map map);
}
