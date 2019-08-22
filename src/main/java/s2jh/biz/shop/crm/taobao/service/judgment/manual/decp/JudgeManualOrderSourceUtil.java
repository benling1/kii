package s2jh.biz.shop.crm.taobao.service.judgment.manual.decp;

import java.util.Map;

import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Deprecated
public class JudgeManualOrderSourceUtil extends AbstractJudgeOrderSetUp {
	//订单来源
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
			try {
				if(orderReminder!=null){
					String userOrderSource =  orderReminder.getOrderSource();
					if(userOrderSource!=null){
						Orders order = (Orders)map.get("order");
						if(order!=null){
							String orderSource = order.getOrderFrom();
								switch (userOrderSource) {
								case "1":{//手机端
									if("1".equals(orderSource)){
									}else{
										flag = false;
										map.put("flag", false);
									}
									break;
								}
								case "2":{//PC端
									if("2".equals(orderSource)){
									}else{
										flag = false;
										map.put("flag", false);
									}
									break;
								}
								case "3":{ //聚划算
									if("3".equals(orderSource)){
									}else{
										flag = false;
										map.put("flag", false);
									}
									break;
								}
								case "0":{ //不限
									break;
								}
								default:
									break;
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
