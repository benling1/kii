package s2jh.biz.shop.crm.taobao.service.judgment.appraise;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Component
@Deprecated 
public class JudgeAppraiseMoneyUtil extends AbstractJudgeOrderSetUp {
	private  final Log logger = LogFactory.getLog(JudgeAppraiseMoneyUtil.class);
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		if ((boolean)map.get("flag")) {
			Trade trade = (Trade) map.get("trade");
			String paymentString = trade.getPayment();
			Double payment = null;
			try {
				payment = Double.parseDouble(paymentString);
			} catch (Exception e) {
				e.printStackTrace();
				this.logger.debug("中差评   用户设置的有错误  "+map.get("tid"));
				map.put("flag", false);
				return map;
			}
			OrderRateCareSetup orderRateCareSetup = (OrderRateCareSetup) map.get("orderRateCareSetup");
			if (orderRateCareSetup!=null) {
				String moneyOne = orderRateCareSetup.getMoneyOne();
				String moneyTwo = orderRateCareSetup.getMoneyTwo();
				if (moneyOne!=null && !moneyOne.equals("")) {
					//如果有最低值，判断
					if (payment<Double.parseDouble(moneyOne)) {
						this.logger.debug("中差评   金额比较失败 "+map.get("tid"));
						map.put("flag", false);
						return map;
					}
				}
				if (moneyTwo !=null && !moneyTwo.equals("")) {
					//如果有最高值，判断
					if (payment>Double.parseDouble(moneyTwo)) {
						this.logger.debug("中差评   金额比较失败  "+map.get("tid"));
						map.put("flag", false);
						return map;
					}
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
