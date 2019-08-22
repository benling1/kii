package s2jh.biz.shop.crm.taobao.service.judgment.appraise;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
/**
 * 判断发送时间
 * @author Administrator
 *
 */
@Component
@Deprecated 
public class JudgeAppraiseReminderTimeUtil extends AbstractJudgeOrderSetUp {
	private  final Log logger = LogFactory.getLog(JudgeAppraiseReminderTimeUtil.class);
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		//前面的判断都通过了
		if ((boolean)map.get("flag")) {
			
			OrderRateCareSetup orderRateCareSetup = (OrderRateCareSetup) map.get("orderRateCareSetup");
			//获取到开始和结束时间
			String sendTimeOne = orderRateCareSetup.getSendTimeOne();
			String sentTimeTwo = orderRateCareSetup.getSendTimeTwo();
			//如果都不为空
			if (sendTimeOne!=null && !sendTimeOne.equals("")&&sentTimeTwo!=null && !sentTimeTwo.equals("")) {
				//获取到当前时间
				Date myDate = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
				String dateString = format.format(myDate);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					//将用户设置的开始和结束时间转成今天的格式
					Date sendTime = dateFormat.parse(dateString+sendTimeOne);
					Date endTime = dateFormat.parse(dateString+sentTimeTwo);
					//如果评价时间在用户设置的时间范围之内
					if (myDate.getTime()>sendTime.getTime()&&myDate.getTime()<endTime.getTime()) {
						//设置短信的发送时间
						SmsSendInfo smsInfo = (SmsSendInfo) map.get("smsInfo");
						smsInfo.setSendTime(myDate);
						map.put("smsInfo", smsInfo);
					}else{
						this.logger.debug("中差评   发送时间不在用户的设置范围之内  "+map.get("tid"));
						map.put("flag", false);
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
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
