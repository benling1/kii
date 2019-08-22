package s2jh.biz.shop.crm.taobao.service.judgment.setup;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
/**
 * 判断疑难件提醒的工具类
 * 超时多久未更新物流
 * @author Administrator
 *
 */
@Component
@Deprecated 
public class JudgeUpdatePuzzleRemindUtil extends AbstractJudgeOrderSetUp {

	@SuppressWarnings("static-access")
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");//前面都判断成功后
		String action = (String) map.get("action");
		String puzzle = "FAILED,LOST,TO_EMS";
		if (flag&&map.get("settingType").equals("10")) {
			//如果是疑难件，丢失件等，不用判断发货几天不签收 ,立即发送
			if (action!=null&&puzzle.contains(action)) {
				
			}else{
				
				//获取到基本设置
				OrderSetup orderSetup = (OrderSetup) map.get("orderSetup");
				//获取到在途中超过几天不更新物流信息的设置
				String notUpdateTiemout = orderSetup.getNotUpdateTiemout();
				Date myDate = (Date) map.get("myDate");
				//如果不为空
				if (notUpdateTiemout!=null&&!notUpdateTiemout.equals("")) {
					//获取到短信
					SmsSendInfo smsInfo = (SmsSendInfo) map.get("smsInfo");
					//获取到订单
					Trade trade = (Trade) map.get("trade");
					SmsSendInfo info = new SmsSendInfo();
					info.setTid(trade.getTid());
					info.setType("10-2");
					
					//根据类型和订单号查出未发送的短信短信
					info = myBatisDao.findBy(SmsSendInfo.class.getName()+"Schedule", "findByTypeAndTid", info);
					//如果物流存在
					if (info!=null) {
						//重新设置发送时间
						Calendar cal = Calendar.getInstance();
						cal.add(cal.DATE, Integer.parseInt(notUpdateTiemout));
						info.setStartSend(cal.getTime());
						map.put("smsInfo", info);
					}else{
						//如果第一次更新物流信息
						//设置短信的发送时间
						smsInfo.setStartSend(myDate);
						map.put("settingType", "10-2");
						map.put("smsInfo", smsInfo);
					}
				}
				
			}
		}
		//责任链判断
		AbstractJudgeOrderSetUp next = super.getNext();
		if(next!=null){
			map = next.startJob(map);
		}
		return map;
	}

}
