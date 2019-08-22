package s2jh.biz.shop.crm.taobao.service.judgment.manual;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.dao.TradeRepository;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Component
@Deprecated 
public class JudgeManualTradeCountUtil extends AbstractJudgeOrderSetUp {
	//判断交易次数是否在卖家设置的范围之内
	@Autowired
	private TradeRepository  tradeRepository;
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
			try {
				if(orderReminder!=null){
					TradeDTO trade = (TradeDTO)map.get("trade");
					if(trade!=null){
						Map<String,String> selectMap = new HashMap<String,String>();
						selectMap.put("sellerName", trade.getSellerNick());
						selectMap.put("buyerName", trade.getBuyerNick());
						String sellerName = trade.getSellerNick();
						String buyerName = trade.getBuyerNick();
						//查询买家在卖家商店的交易次数
//						int count = myBatisDao.findBy(TransactionOrder.class.getName(), "findSuccessByBuyerToSeller", selectMap);
						Long longCount = tradeRepository.count(new Query(Criteria.where("sellerNick").is(sellerName).and("buyerNick").is(buyerName).and("status").is("TRADE_FINISHED")), sellerName);
						int count = longCount.intValue();
						//取得用户设置的 交易次数进行比较
						Integer minTransactionNum = orderReminder.getMinTradeNum();
						Integer maxTransactionNum = orderReminder.getMaxTradeNum();
						if(minTransactionNum!=null && maxTransactionNum!=null){ //最大最小值都存在，符合两个条件
							if(count >= minTransactionNum && count <=maxTransactionNum){
								flag = true;
								map.put("flag", true);
							}else{
								flag = false;
								map.put("flag", false);
							}
						}else{
							if(maxTransactionNum == null && minTransactionNum!=null){ //金额最大值不存在，只需要比较  订单的金额 比用户设置的最小值大即可
								if(count >= minTransactionNum ){
									flag = true;
									map.put("flag", true);
								}else{
									flag = false;
									map.put("flag", false);
								}
							}else if(minTransactionNum == null && maxTransactionNum!=null){ //金额最小值不存在，只需要比较  订单的金额 比用户设置的最小值大即可
								if(count <= maxTransactionNum){
									flag = true;
									map.put("flag", true);
								}else{
									flag = false;
									map.put("flag", false);
								}
							}else{
								flag = true;
							}
						}		
					}else{
						flag =false;
						map.put("flag", false);
						throw new Exception("手动订单，判断订单类型时未发现map集合中有订单Trade");
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
