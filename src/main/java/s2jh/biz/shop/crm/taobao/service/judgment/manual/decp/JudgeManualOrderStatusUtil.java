package s2jh.biz.shop.crm.taobao.service.judgment.manual.decp;

import java.util.Map;

import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Deprecated
@Component
public class JudgeManualOrderStatusUtil extends AbstractJudgeOrderSetUp {
	//订单状态判断
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
			try {
				if(orderReminder!=null){
					String userOrderStatus = orderReminder.getOrderStatus();  //取得用户设置的订单状态和  订单的状态
					if(userOrderStatus!=null){
						Orders order = (Orders)map.get("order");
						if(order!=null){
							String orderStatus = order.getStatus();
							if(userOrderStatus.equals(orderStatus)){
								//用户设置的手动订单状态和 订单状态符合
							}else{
								flag = false;
								map.put("flag", false);
							}
						}else{
							map.put("flag", false);
							throw new Exception("手动订单，判断订单状态时未发现map集合中有订单");
						}
					}
				}else{
					throw new Exception("手动订单，判断订单状态时未发现map集合中有手动订单设置");
					} 
				}
			catch (Exception e) {
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
