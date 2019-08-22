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
 * 超时多久未签收
 * @author Administrator
 *
 */
@Component
@SuppressWarnings("unused")
@Deprecated 
public class JudgeSignPuzzleRemindUtil extends AbstractJudgeOrderSetUp {

	
	@SuppressWarnings("static-access")
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");//前面都判断成功后
		String action = (String) map.get("action");
		String puzzle = "FAILED,LOST,TO_EMS";
		if (flag&&map.get("settingType").equals("10")) {
			//如果是疑难件，丢失件等，不用判断发货几天不签收 ,立即发送
			if (action!=null&&puzzle.contains(action)) {
				//获取现在时间
				Date myDate = (Date) map.get("myDate");
				//获取到短信
				SmsSendInfo smsInfo = (SmsSendInfo) map.get("smsInfo");
				smsInfo.setStartSend(myDate);
				map.put("settingType", "10");
				smsInfo.setType("10");
				map.put("smsInfo", smsInfo);
				
			}else{
				//获取到基本设置
				OrderSetup orderSetup = (OrderSetup) map.get("orderSetup");
				//获取到发货超过几天不签收
				String notSignInTimeout = orderSetup.getNotSignInTimeout();
				//获取现在时间
				Date myDate = (Date) map.get("myDate");
				//获取到短信
				SmsSendInfo smsInfo = (SmsSendInfo) map.get("smsInfo");
				//获取到订单
				Trade trade = (Trade) map.get("trade");
				//如果不为空
				if (notSignInTimeout!=null&& ! notSignInTimeout.equals("")) {
					//当前时间加未签收设置时间
					Calendar cal = Calendar.getInstance();
					notSignInTimeout = notSignInTimeout.substring(0, notSignInTimeout.length()-1);
					cal.add(cal.DATE, Integer.parseInt(notSignInTimeout));
					//设置短信的发送时间
					smsInfo.setStartSend(cal.getTime());
					map.put("settingType", "10-1");
					smsInfo.setType("10-1");
					map.put("smsInfo", smsInfo);
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

//疑难件提醒
/*if (flag&&map.get("settingType").equals("10")) {
			//获取到基本设置
			OrderSetup orderSetup = (OrderSetup) map.get("orderSetup");
			//获取到发货超过几天不签收
			String notSignInTimeout = orderSetup.getNotSignInTimeout();
			//获取现在时间
			Date myDate = (Date) map.get("myDate");
			//如果不为空
			if (notSignInTimeout!=null&& ! notSignInTimeout.equals("")) {
				Orders order = (Orders) map.get("order");
				//获取到发货时间
				String consignTime = order.getConsignTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					//将发货时间转成日期格式
					Date consignDate = dateFormat.parse(consignTime);
					//如果现在时间减去发货时间大于设置的时间
					if (((consignDate.getTime()-myDate.getTime())/3600/24)>=Long.parseLong(notSignInTimeout)) {
						//为疑难件
						
					}else{
						map.put("flag", false);
						flag=false;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (flag) {
				//获取到在途中超过几天不更新物流信息的设置
				String notUpdateTiemout = orderSetup.getNotUpdateTiemout();
				//如果不是空
				if (notUpdateTiemout!=null&&!notUpdateTiemout.equals("")) {
					//获取到最后一次物流更新的时间
					LogisticsOrder logisticsOrder = (LogisticsOrder) map.get("logisticsOrder");
					Date modified = logisticsOrder.getModified();
					if ((myDate.getTime()-modified.getTime())/24/3600>=Long.parseLong(notUpdateTiemout)) {
						//如果现在时间减去最后更新时间大于设置的时间
						//为疑难件
					}else{
						map.put("flag", false);
						flag = false;
					}
					
				}
				
			}
			
		}*/