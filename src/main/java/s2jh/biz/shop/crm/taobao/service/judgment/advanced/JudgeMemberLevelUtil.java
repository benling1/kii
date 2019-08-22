package s2jh.biz.shop.crm.taobao.service.judgment.advanced;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Component
@Deprecated 
public class JudgeMemberLevelUtil extends AbstractJudgeOrderSetUp{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeMemberLevelUtil.class);
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		/*if((boolean)map.get("flag") == true ){	// 之前已经判断为不发送短信，后续都不再判断
			OrderAdvancedSetting orderAdvancedSetting = (OrderAdvancedSetting)map.get("orderAdvancedSetting");
			String sellerMemberLevel = orderAdvancedSetting!=null?orderAdvancedSetting.getMemberLevel():null;
			if(sellerMemberLevel!= null && !"0".equals(sellerMemberLevel)){
				Trade trade = (Trade)map.get("trade");
				if(trade!=null){
					map.put("phone", trade.getReceiverMobile()==null?trade.getReceiverPhone():trade.getReceiverMobile());
					MemberInfo memberInfo = myBatisDao.findBy(MemberInfo.class.getName(), "findMemberByNick", map);
					//用户的等级不等于空
					Long memberGrade = memberInfo!=null?memberInfo.getGradeId():null;
					if(memberGrade!=null){
						//  indexOf() 如果找不到返回-1 如果能在用户设置的会员等级中找不到会员的对应等级，则不发送短信
						Long buyerGrade  = memberInfo.getGradeId();
						map.put("buyerGrade", buyerGrade);
						SmsSendInfo smsInfo = (SmsSendInfo)map.get("smsInfo");
						smsInfo.setIsMember(buyerGrade!=null?buyerGrade>0?1 : 0 : 0);
						if(sellerMemberLevel.indexOf(memberGrade.toString())==-1){
							this.logger.debug("******************* 用户等级和买家设置的等级不符  ********************");
							map.put("flag", false);
						}
					}else{
						//用户的等级设置不为空  但是买家等级为空
						this.logger.debug("******************* 用户的等级设置不为空  但是买家等级为空  ********************");
						map.put("flag", false);
					}
				}
			}
		}
		//责任链判断
		if(super.getNext()!=null){
			map = super.getNext().startJob(map);
		}*/
		return map;
	}

}
