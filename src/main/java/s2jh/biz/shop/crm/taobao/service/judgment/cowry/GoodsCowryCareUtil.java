package s2jh.biz.shop.crm.taobao.service.judgment.cowry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.taobao.service.judgment.JudgeUserOrderSetUpUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
//宝贝关怀，特殊判断   单独独立判断
@Component
@Deprecated 
public class GoodsCowryCareUtil extends AbstractJudgeOrderSetUp{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(GoodsCowryCareUtil.class);
	@Autowired
	private	JudgeUserOrderSetUpUtil judgeUserOrderSetUpUtil;
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag") ;
		if(flag){	// 之前已经判断为发送短信，后续才判断
			if(map.containsKey("smsSettingItemID")){
				Trade trade = (Trade)map.get("trade");
				if(trade!=null ){
					String itemID = (String)map.get("smsSettingItemID");
					boolean sendFlag = true;
					if(ValidateUtil.isNotNull(itemID)){
						List<String> str = this.getItemIds(trade);
						if(ValidateUtil.isEmpty(str)){
							map.put("flag", false);
							return map;
						}
						for (String string : str) {
							if(itemID.contains(string)){
								sendFlag = false;
								break;
							}
						}
					}
					if(sendFlag){
						map.put("flag", false);
						this.logger.debug("*******************tid: "+map.get("tid")+" ,类型："+map.get("settingType")+",宝贝关怀--指定商品  订单和设置条件不符合 ********************");
						return map;
					}
				}
			}
			if(flag){
				//判断基本设置条件   商品的价格，延后时间和发送时间的范围
				map = judgeUserOrderSetUpUtil.startJob(map);
			}
		}
		AbstractJudgeOrderSetUp next = super.getNext();
		if(next!=null){
			map = next.startJob(map);
		}
		return map;
	}
	private List<String> getItemIds(Trade trade){
		if(trade == null){
			return null;
		}
		List<Order> ordes = trade.getOrders();
		List<String> list = new ArrayList<String>(ordes.size());
		for (Order order : ordes) {
			list.add(order.getNumIid()+"");
		}
		return list;
	}
}
