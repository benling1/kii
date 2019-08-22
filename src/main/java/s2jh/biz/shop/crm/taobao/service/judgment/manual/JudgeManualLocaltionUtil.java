package s2jh.biz.shop.crm.taobao.service.judgment.manual;

import java.util.Map;

import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;

@Component
@Deprecated
public class JudgeManualLocaltionUtil extends AbstractJudgeOrderSetUp {
	//手动订单 判断地区
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
			try {
				if(orderReminder!=null){
					String region = orderReminder.getProvince();
					if(region==null ||"0".endsWith(region)){
						//用户未设置地区筛选或者 默认选择全部地区
					}else{
						TradeDTO trade = (TradeDTO)map.get("trade");
						if(trade!=null){
							String receiverCity = trade.getReceiverCity();
							if(receiverCity!=null){
								if(region.indexOf(receiverCity)==-1){ 
									flag = false;
									map.put("flag", false);
								}else{
									flag = true;
									map.put("flag", true);
								}
							}
						}else{
							flag = false;
							map.put("flag", false);
						}
					}
				}else{
					throw new Exception("手动订单，判断订单类型时未发现map集合中有手动订单设置ManualOrderCondition");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(flag){
			//责任链判断
			if(super.getNext()!=null){
				super.getNext().startJob(map);
			}
		}
		return map;
	}

}
