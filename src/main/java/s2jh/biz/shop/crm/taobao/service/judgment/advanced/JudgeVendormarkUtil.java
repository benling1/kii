package s2jh.biz.shop.crm.taobao.service.judgment.advanced;

import java.util.Map;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Component
@Deprecated 
public class JudgeVendormarkUtil extends AbstractJudgeOrderSetUp{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeVendormarkUtil.class);
	
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		//判断卖家小红旗
		if((boolean)map.get("flag") == true ){	// 之前已经判断为不发送短信，后续都不再判断
			OrderAdvancedSetting orderAdvancedSetting = (OrderAdvancedSetting)map.get("orderAdvancedSetting");
			String sellerVendormark = orderAdvancedSetting != null ? orderAdvancedSetting.getVendormark() : null;
			logger.debug("0.0-->0.0-->0.0-->0.0-->0.0-->0.0-->0.0-->sellerVendormark:" + sellerVendormark);
			String flagColor = orderAdvancedSetting != null ? orderAdvancedSetting.getFlagcolor() : null;
			logger.debug("0.0-->0.0-->0.0-->0.0-->0.0-->0.0-->0.0-->flagColor:" + flagColor);
			if(sellerVendormark!=null && !"".equals(sellerVendormark) && !"0".equals(sellerVendormark)){
				if(flagColor != null && !"".equals(flagColor) && !"0".equals(flagColor)){
					Trade trade = (Trade) map.get("trade");
					String orderSellFlag = trade != null ? trade.getSellerFlag() + "" : null;
					if(orderSellFlag != null && flagColor.contains(orderSellFlag)){
						this.logger.debug("*******************tid:" + map.get("tid") + ",类型：" + map.get("settingType") + ",卖家小红旗判断不通过   ********************");
						map.put("flag", false);
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
