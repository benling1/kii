package s2jh.biz.shop.crm.taobao.service.judgment.manual.decp;

import java.util.Map;

import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Deprecated
public class JudgeManualOrderTypeUtil extends AbstractJudgeOrderSetUp {
	//判断订单的类型  是否预售   暂不使用
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			if(flag){
				OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
				try {
					if(orderReminder!=null){
						String orderType = orderReminder.getOrderType();
						if(orderType == null || "0".equals(orderType)){ //用户没有设置或者是订单类型不限
						}else{
							Orders order = (Orders)map.get("order");
							if(order!=null){
								if("1".equals(orderType)){ //预售
									if("pre_auth_type".equals(order.getType())){  //交易类型列表，同时查询多种交易类型可用逗号分隔。默认同时查询guarantee_trade, auto_delivery, ec, cod的4种交易类型的数据 可选值 fixed(一口价) auction(拍卖) guarantee_trade(一口价、拍卖) auto_delivery(自动发货) independent_simple_trade(旺店入门版交易) independent_shop_trade(旺店标准版交易) ec(直冲) cod(货到付款) fenxiao(分销) game_equipment(游戏装备) shopex_trade(ShopEX交易) netcn_trade(万网交易) external_trade(统一外部交易)o2o_offlinetrade（O2O交易）step (万人团)nopaid(无付款订单)pre_auth_type(预授权0元购机交易) 
										flag = true;
										map.put("flag", true);
									}else{
										flag = false;
										map.put("flag", false);
									}
								}else if("2".equals(orderType)){ //非预售
									if("pre_auth_type".equals(order.getType())){
										flag = false;
										map.put("flag", false);
									}else{
										flag = true;
										map.put("flag", true);
									}
								}
							}else{
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
		}
		//责任链判断
		if(super.getNext()!=null){
			super.getNext().startJob(map);
		}
		return map;
	}

}
