package s2jh.biz.shop.crm.taobao.service.judgment.appraise;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
/**
 * 判断不发送短信的订单来源
 * @author Administrator
 *
 */
@Component
@Deprecated 
public class JudgeAppraiseOrderSourceUtil extends AbstractJudgeOrderSetUp {
	private  final Log logger = LogFactory.getLog(JudgeAppraiseOrderSourceUtil.class);
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		if ((boolean)map.get("flag")) {
			OrderRateCareSetup orderRateCareSetup = (OrderRateCareSetup) map.get("orderRateCareSetup");
			//获取到中差评安抚不发送订单来源的设置
			String orderSource = orderRateCareSetup.getOrderSource();
			//如果不发送订单来源不为空
			if (orderSource!=null && ! orderSource.equals("")) {
				orderSource = orderSource.replaceAll("0", TradesInfo.ORDER_FROM_TAOBAO);
				orderSource = orderSource.replaceAll("1", TradesInfo.ORDER_FROM_WAP);
				orderSource = orderSource.replaceAll("2", TradesInfo.ORDER_FROM_JHS);
				Trade trade = (Trade)map.get("trade");
				//获取订单来源
				String orderFrom = trade.getTradeFrom();
				if(orderFrom==null){
					this.logger.debug("中差评   订单来源为空   ----- 订单号： "+map.get("tid"));
					map.put("flag", false);
					return map;
				}
				boolean flag = false;
				String str[] = orderFrom.split(",");
				for (String string : str) {
					//如果订单来源在不发送的订单来源中存在，则不发送
					if(orderSource.contains(string)){
						flag = true;
						break;
					}
				}
				if(flag){
					this.logger.debug("中差评   订单来源判断失败   ----- 订单号： "+map.get("tid")+" ，订单来源："+orderFrom+",用户的设置不发送订单来源为："+orderSource);
					map.put("flag", false);
					return map;
				}
			}
		}
		//责任链判断
		if(super.getNext()!=null){
			super.getNext().startJob(map);
		}
		return map;
	}

}
