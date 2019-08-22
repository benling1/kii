package s2jh.biz.shop.crm.taobao.service.judgment.manual;

import java.util.Map;

import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Component
@Deprecated 
public class JudgeManualVendormarkUtil extends AbstractJudgeOrderSetUp {
	//判断卖家小红旗
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
			try {
				if(orderReminder!=null){
					String sellerSign = orderReminder.getSellerSign();
					if(sellerSign!=null){
						if("0".equals(sellerSign)){ //用户设置了小红旗全选
						}else{
							TradeDTO trade = (TradeDTO)map.get("trade");
							if(trade!=null){
								//查询用户的小红旗设置和 订单的具体小红旗设置
								String sellerFlag = trade.getSellerFlag();
								if(sellerSign.indexOf(sellerFlag)==-1){
									//没找到
									flag = false;
									map.put("flag", false);
								}
							}else{
								throw new Exception("手动订单，判断订单类型时未发现map集合中有手动订单设置ManualOrderCondition");
							}
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
			//执行任务链
			AbstractJudgeOrderSetUp next = super.getNext();
			if(next!=null){
				map = next.startJob(map);
			}
		}
		return map;
	}
	
}
