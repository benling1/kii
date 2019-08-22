package s2jh.biz.shop.crm.taobao.service.judgment.manual;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.order.entity.TradeRates;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Component
@Deprecated 
public class JudgeManualBlacklistUtil extends AbstractJudgeOrderSetUp {
	@Autowired
	private VipMemberService vipMemberService;
	
	//黑名单中差评过滤
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
			try {
				if(orderReminder!=null){
					String filterCondition = orderReminder.getFilterType();
					if(filterCondition!=null){
						TradeDTO trade = (TradeDTO)map.get("trade");
						if(trade!=null){
							Map<String,String> selectMap = new HashMap<String,String>();
							//过滤条件存在
							if("0".equals(filterCondition)){//黑名单过滤
								selectMap.put("sellerName", trade.getSellerNick());
								selectMap.put("buyerName", trade.getBuyerNick());
								selectMap.put("phone", trade.getReceiverMobile());
								
								MemberDTO md = vipMemberService.queryByMemberInfoDetails(trade.getBuyerNick(),trade.getSellerNick());
//								MemberInfo memberInfo = myBatisDao.findBy(MemberInfo.class.getName(), "findBlackListByNick", map);
								if(md!=null&&md.getBlackStatus()==1){ //根据买卖家昵称和黑名单表示查询出来用户不为空，即该买家在卖家的标记中为黑名单
									map.put("flag", false);
									flag = false;
								}
							}else if("1".equals(filterCondition)){ //中差评过滤
								//通过订单ID查询 评价信息
								TradeRates tradeRates = myBatisDao.findBy(TradeRates.class.getName(), "findByOrderBuyer", trade.getTid());
								if(tradeRates!=null){
									String result = tradeRates.getResult();
									if("good".endsWith(result)){ //评价结果,可选值:good(好评),neutral(中评),bad(差评)
										//评价是好评，可以发短信
										map.put("flag", true);
										flag = true;
									}else{
										//中差评，不发短信
										map.put("flag", false);
										flag = false;
									}
								}else{
									//无记录，未评价
									map.put("flag", false);
									flag = false;
								}
							
							}else if("0,1".equals(filterCondition)){
								//黑名单过滤
								selectMap.put("sellerName", trade.getSellerNick());
								selectMap.put("buyerName", trade.getBuyerNick());
								selectMap.put("phone", trade.getReceiverMobile());
								MemberDTO md = vipMemberService.queryByMemberInfoDetails(trade.getBuyerNick(),trade.getSellerNick());
//								MemberInfo memberInfo = myBatisDao.findBy(MemberInfo.class.getName(), "findBlackListByNick", map);
								if(md!=null&&md.getBlackStatus()==1){ //根据买卖家昵称和黑名单表示查询出来用户不为空，即该买家在卖家的标记中为黑名单
									map.put("flag", false);
									flag = false;
									return map;
								}
								
								//通过订单ID查询 评价信息
								TradeRates tradeRates = myBatisDao.findBy(TradeRates.class.getName(), "findByOrderBuyer", trade.getTid());
								if(tradeRates!=null){
									String result = tradeRates.getResult();
									if("good".endsWith(result)){ //评价结果,可选值:good(好评),neutral(中评),bad(差评)
										//评价是好评，可以发短信
										map.put("flag", true);
										flag = true;
									}else{
										//中差评，不发短信
										map.put("flag", false);
										flag = false;
									}
								}else{
									//无记录，未评价
									map.put("flag", false);
									flag = false;
								}
								
							}
						}else{
							flag =false;
							map.put("flag", false);
							throw new Exception("手动订单，判断订单类型时未发现map集合中有订单Trade");
						}
					}else{
						//用户未设置过滤条件，直接跳过
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
