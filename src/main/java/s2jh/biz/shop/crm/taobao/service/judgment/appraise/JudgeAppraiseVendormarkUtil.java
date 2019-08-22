package s2jh.biz.shop.crm.taobao.service.judgment.appraise;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;

@Component
@Deprecated 
public class JudgeAppraiseVendormarkUtil extends AbstractJudgeOrderSetUp{
	private  final Log logger = LogFactory.getLog(JudgeAppraiseVendormarkUtil.class);
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		//判断不发送镖旗订单
		if ((boolean)map.get("flag")) {//如果之前判断为true
			//获取到设置
			OrderRateCareSetup orderRateCareSetup = (OrderRateCareSetup) map.get("orderRateCareSetup");
			//获取到旗子颜色
			String flagcolor = orderRateCareSetup.getFlagcolor();
			if (flagcolor!=null && !flagcolor.equals("")) {
				//获取到订单
				Trade trade = (Trade)map.get("trade");
				String  orderSellFlag = trade!=null?trade.getSellerFlag()+"" : null; ;
				//如果卖家标记在不发送的标旗订单中，则不发送信息
				if(flagcolor.indexOf(orderSellFlag+"")!=-1){
					this.logger.debug("**************** 卖家小红旗判断不通过 *****************  "+map.get("tid"));
					map.put("flag", false);
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
