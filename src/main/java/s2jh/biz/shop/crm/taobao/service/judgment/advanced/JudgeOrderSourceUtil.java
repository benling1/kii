package s2jh.biz.shop.crm.taobao.service.judgment.advanced;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;

//判断订单来源是否符合用户的设置
@Component
@Deprecated 
public class JudgeOrderSourceUtil extends AbstractJudgeOrderSetUp {
	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(JudgeOrderSourceUtil.class);

	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		if ((boolean) map.get("flag") == true) { // 之前已经判断为不发送短信，后续都不再判断
			OrderAdvancedSetting orderAdvancedSetting = (OrderAdvancedSetting) map
					.get("orderAdvancedSetting");
			String sellerOrderSource = orderAdvancedSetting != null ? orderAdvancedSetting
					.getOrderSource() : null;
			Trade trade = (Trade) map.get("trade");
			if (trade != null) {
				String tradeFrom = trade.getTradeFrom();
				if (tradeFrom != null) {
					if ("1".equals(sellerOrderSource)) { // 订单来源 0-不限 1-Pc端
															// 2-移动端
						if (tradeFrom.contains(TradesInfo.ORDER_FROM_TAOBAO)) {
						} else {
							this.logger
									.debug("*******************tid:"
											+ map.get("tid")
											+ ",类型："
											+ map.get("settingType")
											+ ",订单来源设置和实际订单来源不符， PC ********************");
							map.put("flag", false);
						}
					} else if ("2".equals(sellerOrderSource)) {
						if (tradeFrom.contains(TradesInfo.ORDER_FROM_WAP)) {
						} else {
							this.logger
									.debug("*******************tid:"
											+ map.get("tid")
											+ ",类型："
											+ map.get("settingType")
											+ ",订单来源设置和实际订单来源不符，移动端  ********************");
							map.put("flag", false);
						}
					}
				}
			} else {
				this.logger.debug("*******************tid:"
						+ map.get("tid") + ",类型："
						+ map.get("settingType")
						+ ",订单数据为空  ********************");
				map.put("flag", false);
			}
		}
		// 责任链判断
		if (super.getNext() != null) {
			super.getNext().startJob(map);
		}
		return map;
	}

}
