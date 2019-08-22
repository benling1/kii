package s2jh.biz.shop.crm.taobao.service.judgment.manual;

import java.util.Map;

import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Component
@Deprecated 
public class JudgeManualGoodsUtil extends AbstractJudgeOrderSetUp {
	//评价状态 
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
			try {
				if(orderReminder!=null){
					String productSelect = orderReminder.getSelectCommodityType();
					if(productSelect==null || "0".equals(productSelect)){
						//用户未设置商品过滤或者 选择的是 全部商品
					}else{
						TradeDTO trade = (TradeDTO)map.get("trade");
						if(trade!=null){
							String tradeNumIid = trade.getNUM_IID();
							if(tradeNumIid!=null){
								//对订单中的商品ID字段先进行拆分，如果有的话 拆出来一个个在商家的过滤条件中查找，  indexOf() 如果找不到返回-1
								String tradeGoods[] = tradeNumIid.split(",");
								String sellerGoods = orderReminder.getCommodityIds();
								if(sellerGoods!=null){ //用户设置的商品ID不为空
									if("1".equals(productSelect)){//指定商品发送
										for (int i = 0; i < tradeGoods.length; i++) {
											//在商品过滤中没找到，则意味着不是商户指定发送的     不发送短信
											if(sellerGoods.indexOf(tradeGoods[i])==-1){
												map.put("flag", false);
											}
										}
									}else if("2".equals(productSelect)){//排除指定商品发送
										for (int i = 0; i < tradeGoods.length; i++) {
											//在商品过滤中找到，则意味着不是商户排除指定发送的     不发送短信
											if(sellerGoods.indexOf(tradeGoods[i])!=-1){
												map.put("flag", false);
											}
										}
									}else {
										//用户设置错误，不发送短信
										flag = false;
										map.put("flag", false);
									}
								}
							}
						}else{
							flag =false;
							map.put("flag", false);
							throw new Exception("手动订单，判断订单类型时未发现map集合中有订单Trade");
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
