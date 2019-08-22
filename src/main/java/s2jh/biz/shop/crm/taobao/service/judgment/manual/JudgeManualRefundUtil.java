package s2jh.biz.shop.crm.taobao.service.judgment.manual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.service.OrderService;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Component
@Deprecated 
public class JudgeManualRefundUtil extends AbstractJudgeOrderSetUp {

	@Autowired
	private OrderService orderService;
	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean) map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder) map.get("orderReminder");
			try {
				if(orderReminder != null){
					String orderStatus = orderReminder.getOrderStatus();
					if("REFUND_SUCCESS".equals(orderStatus)){
						TradeDTO trade = (TradeDTO) map.get("trade");
						if(trade != null){
							Map<String,Object> selMap = new HashMap<String, Object>();
							selMap.put("tid", trade.getTid());
							//@author:jackstraw_yu 修改
							selMap.put("userNickName", trade.getSellerNick());
							//List<String> refundStatusList = myBatisDao.findList(Orders.class.getName(), "findOrdersByTid", selMap);
							//@author:jackstraw_yu 修改
							List<String> refundStatusList = tradeInfoService.findOrdersByTid(selMap);
							if(!refundStatusList.isEmpty()){
								if(refundStatusList.contains("SUCCESS")){
									flag = true;
									map.put("flag",true);
								}else {
									flag = false;
									map.put("flag",false);
								}
							}
						}else {
							flag = false;
							map.put("flag", false);
							throw new Exception("手动订单，判断订单类型时未发现map集合中有订单Trade");
						}
					}
				}else {
					throw new Exception("手动订单，判断订单类型时未发现map集合中有手动订单设置ManualOrderCondition");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(flag){
			//执行任务链
			AbstractJudgeOrderSetUp next = super.getNext();
			if(next != null){
				map = next.startJob(map);
			}
		}
		return map;
	}

}
