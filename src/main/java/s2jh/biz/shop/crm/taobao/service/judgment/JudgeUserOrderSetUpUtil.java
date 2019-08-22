package s2jh.biz.shop.crm.taobao.service.judgment;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
import s2jh.biz.shop.crm.taobao.service.judgment.setup.JudgeOrderPayment;
import s2jh.biz.shop.crm.taobao.service.judgment.setup.JudgeReminderTimeUitl;
import s2jh.biz.shop.crm.taobao.service.judgment.setup.JudgeSellerMessageUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.setup.JudgeSignPuzzleRemindUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.setup.JudgeUpdatePuzzleRemindUtil;
//判断用户基本设置条件
@Component
@Deprecated 
public class JudgeUserOrderSetUpUtil extends AbstractJudgeOrderSetUp {
	@Autowired
	private JudgeOrderPayment judgeOrderPayment;
	@Autowired
	private JudgeSellerMessageUtil judgeSellerMessageUtil;
	@Autowired
	private JudgeReminderTimeUitl judgeReminderTimeUitl;
	@Autowired
	private JudgeSignPuzzleRemindUtil judgeSignPuzzleRemindUtil;
	@Autowired
	private JudgeUpdatePuzzleRemindUtil judgeUpdatePuzzleRemindUtil;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeUserOrderSetUpUtil.class);
	@Override
	public Map<String,Object> startJob(Map<String, Object> map) {
		long startTime = System.currentTimeMillis();
		if((boolean)map.get("flag") == true ){	// 之前已经判断为发送短信，后续才判断
			//查询卖家用过户的设置过滤条件，根据卖家用户ID查询具体的基本过滤条件
			OrderSetup orderSetup =  null;
			if(map.containsKey("orderSetup")){
				orderSetup = (OrderSetup)map.get("orderSetup");
			}else{
				orderSetup = myBatisDao.findBy(OrderSetup.class.getName(), "findOrderSetupByUserIdAndSettingTypeSend", map);
			}
			if(orderSetup !=null && orderSetup.getStatus()!=null){
				if(OrderSettingInfo.ORDER_SETUP_OPEN.equals(orderSetup.getStatus())){
					this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",基本设置是开启的状态 ，开始判断********************");
					map.put("orderSetup",orderSetup);
					//执行组装拼凑基本设置的判断   价格、延后时间换和发送时间
					this.judgeOrderPayment.setNext(this.judgeSignPuzzleRemindUtil);
					this.judgeSignPuzzleRemindUtil.setNext(this.judgeUpdatePuzzleRemindUtil);
					this.judgeUpdatePuzzleRemindUtil.setNext(this.judgeSellerMessageUtil);
					this.judgeSellerMessageUtil.setNext(this.judgeReminderTimeUitl);
					map = this.judgeOrderPayment.startJob(map);
						
				}else{
					//未开启对应的关怀
					this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",基本设置未开启 ********************");
				}
			}
		}
		if((boolean)map.get("flag")==true){
			//责任链判断
			AbstractJudgeOrderSetUp next = super.getNext();
			if(next!=null){
				map = next.startJob(map);
			}
		}
		long endTime = System.currentTimeMillis();
		long result = endTime-startTime;
		if(result>5000){
			logger.debug("基本设置花费时间过长    ："+(result)+" ms,tid ：" + map.get("tid"));
		}
		return map;
	}

}
