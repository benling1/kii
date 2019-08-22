package s2jh.biz.shop.crm.taobao.service.judgment.manual.decp;

import java.util.Map;

import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Deprecated
public class JudgeManualEvaluateStatusUtil extends AbstractJudgeOrderSetUp {
	//评价状态 
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
			try {
				if(orderReminder!=null){
					String evaluateStatus = orderReminder.getEvaluateStatus();
					if(evaluateStatus ==null || "0".equals(evaluateStatus)){
						//未设置或者  评价状态为不限
					}else{
						Orders order = (Orders)map.get("order");
						if(order!=null){
							Boolean buyerRate  = order.getBuyerRate(); //买家是否已评价
							Boolean seller_rate = order.getSellerRate(); //卖家是否已评价
							switch (evaluateStatus) {
							case "1":{//买家未评价
								if(buyerRate!=null){
									if(buyerRate){  
										flag =false;
										map.put("flag", false); //买家已经评价，不符合条件
									}else{ 
										flag =true;
										map.put("flag", true);
									}
								}else{
									flag =false;
									map.put("flag", false);	//买家数据为空，不符合条件
								}
								break;
							}
							case "2":{ //买家未评价，卖家已评价
								if(buyerRate!=null){
									if(buyerRate){ 
										flag =false;
										map.put("flag", false); //买家已经评价，不符合条件
									}else{
										if(seller_rate!=null){
											if(seller_rate){
												flag = true;
												map.put("flag", true); //卖家已评价，符合条件
											}else{
												flag =false;
												map.put("flag", false); //卖家未评价，不符合条件
											}
										}else{
											flag =false;
											map.put("flag", false); //卖家家数据为空，不符合条件
										}
									}
								}
								break;
							}	
							case "3":{ //买家已评价
								if(buyerRate!=null){
									if(buyerRate){  
										flag =true;
										map.put("flag", true); //买家已经评价，符合条件
									}else{ 
										flag =false;
										map.put("flag", false);
									}
								}else{
									flag =false;
									map.put("flag", false);	//买家数据为空，不符合条件
								}
								break;
							}	
							default:
								break;
							}
						}else{
							flag =false;
							map.put("flag", false);
							//无订单
							throw new Exception("手动订单，判断订单类型时未发现map集合中有订单Orders");
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
