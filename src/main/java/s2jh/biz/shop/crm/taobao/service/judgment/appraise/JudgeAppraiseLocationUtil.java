package s2jh.biz.shop.crm.taobao.service.judgment.appraise;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
/**
 * 判断不发送短信的收货地区
 * @author Administrator
 *
 */
@Component
@Deprecated 
public class JudgeAppraiseLocationUtil extends AbstractJudgeOrderSetUp {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeAppraiseLocationUtil.class);
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		if ((boolean)map.get("flag")) {
			OrderRateCareSetup orderRateCareSetup = (OrderRateCareSetup)map.get("orderRateCareSetup");
			String locality = orderRateCareSetup.getLocality();
			
			if(locality!=null&& !locality.equals("")){
				//获取到订单
				Trade trade = (Trade)map.get("trade");
				String receiverCity = trade!=null?trade.getReceiverState():null;
				//如果买家的收货地址在设置的不收货地区中，则不发信息
				if (locality.contains(receiverCity)) {
					this.logger.debug("********中差评的收货地址在设置的不发送短信收货地区中*** "+map.get("tid") +"  ，订单的省份："+receiverCity +"，用户的设置为："+locality);
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
