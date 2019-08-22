package s2jh.biz.shop.crm.taobao.service.judgment.advanced;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
//高级条件筛选  判断地区
@Component
@Deprecated 
public class JudgeLocaltionUtil extends AbstractJudgeOrderSetUp {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeLocaltionUtil.class);
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		if((boolean)map.get("flag")==true){
			OrderAdvancedSetting orderAdvancedSetting = (OrderAdvancedSetting)map.get("orderAdvancedSetting");
			if(orderAdvancedSetting!=null){
				String userSetLocal =orderAdvancedSetting.getLocality();
				if(!"0".equals(userSetLocal)){	//用户设置的地区不是全选默认的
					Trade trade = (Trade)map.get("trade");
					if(trade!=null){
						String receiverCity = trade.getReceiverState();
						if(receiverCity!=null&&
								userSetLocal!=null &&
								userSetLocal.indexOf(receiverCity)==-1){	// indexOf() 如果找不到返回-1  订单的地区在卖家商户的短信发送条件查询不到
							this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",地区筛选  订单地区和用户设置的过滤条件不符********************");
							map.put("flag", false);
						}
					}else{
						this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",订单的对象是空的 无法判断 错误！！！ ********************");
						map.put("flag", false);
					}
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
