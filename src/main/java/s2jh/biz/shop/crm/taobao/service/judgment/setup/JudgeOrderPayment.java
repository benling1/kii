package s2jh.biz.shop.crm.taobao.service.judgment.setup;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
//订单金额判断
@Component
@Deprecated 
public class JudgeOrderPayment extends AbstractJudgeOrderSetUp {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeOrderPayment.class);
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag==true){
			BigDecimal deliveryPay = null;//订单的实付金额
			if (map.containsKey("refund_fee")) {
				deliveryPay = new BigDecimal(String.valueOf(map.get("refund_fee")));
			}
			if(deliveryPay == null){	
				Trade trade = (Trade)map.get("trade");
				deliveryPay = new BigDecimal(trade.getPayment());
			}
			OrderSetup orderSetup = (OrderSetup)map.get("orderSetup");
			if(deliveryPay!=null && orderSetup!= null ){
				BigDecimal payAmtOne = orderSetup.getPayAmtOne();
				BigDecimal payAmtTwo = orderSetup.getPayAmtTwo();
				if (payAmtOne!=null&&payAmtTwo!=null) {
					if(payAmtOne.compareTo(deliveryPay)<=0 && //BigDecimal的compareTo方法来进行比较大小。 返回的结果是int类型,-1表示小于,0是等于,1是大于
							payAmtTwo.compareTo(deliveryPay)>=0){
						map.put("flag", true);
					}else {
						this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",用户设置的金额两个都存在时，订单金额比较失败 ，结束********************");
						map.put("flag", false);
					}
				}else{
					if(payAmtTwo == null && payAmtOne!=null){  //用户设置的最大值不存在
						if(payAmtOne.compareTo(deliveryPay)==-1){
						}else{
							this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",用户设置的最大值不存在时，订单金额比较失败，结束********************");
							flag = false;
							map.put("flag", false);
						}
					}else if(payAmtOne==null && payAmtTwo!=null){ //用户设置的最小值不存在
						if(payAmtTwo.compareTo(deliveryPay)==1){
						}else{
							this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",用户设置的最小值不存在时，订单金额比较失败，结束********************");
							flag = false;
							map.put("flag", false);
						}
					}
				}
				
			}
		}
		if(flag){
			//责任链判断
			if(super.getNext()!=null){
				map = super.getNext().startJob(map);
			}
		}
		return map;
	}

}
