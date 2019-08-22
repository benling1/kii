package s2jh.biz.shop.crm.taobao.service.judgment.manual.decp;

import java.util.Map;

import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Deprecated
public class JudgeManualOrderPriceUtil extends AbstractJudgeOrderSetUp {
	//判断订单金额是否在用户设置范围之内
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
			try {
				if(orderReminder!=null){
					//取得用户设置的 最大最小金额 和订单的实际付款  进行比较
					Double minOrderPriceString = orderReminder.getMinOrderPrice();
					Double maxOrderPriceString = orderReminder.getMaxOrderPrice();
					Orders order = (Orders)map.get("order");
					if(order!=null){
						Double paymentString = order.getPayment();
						if(paymentString!=null){
							double payment = paymentString;
							if(minOrderPriceString!=null && maxOrderPriceString!=null){ //最大最小值都存在，符合两个条件
								Double minOrderPrice = minOrderPriceString;
								Double maxOrderPice =  maxOrderPriceString;
								if(payment >= minOrderPrice && payment <=maxOrderPice){
									flag = true;
									map.put("flag", true);
								}else{
									flag = false;
									map.put("flag", false);
								}
							}else{
								if(maxOrderPriceString == null && minOrderPriceString!=null){ //金额最大值不存在，只需要比较  订单的金额 比用户设置的最小值大即可
									double minOrderPrice = minOrderPriceString;
									if(payment >= minOrderPrice ){
										flag = true;
										map.put("flag", true);  
									}else{
										flag = false;
										map.put("flag", false);
									}
								}else if(minOrderPriceString == null && maxOrderPriceString!=null){ //金额最小值不存在，只需要比较  订单的金额 比用户设置的最小值大即可
									double maxOrderPice =  maxOrderPriceString;
									if(payment <= maxOrderPice){
										flag = true;
										map.put("flag", true);
									}else{
										flag = false;
										map.put("flag", false);
									}
								}
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
