package s2jh.biz.shop.crm.taobao.service.judgment.setup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Trade;

import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.SmsSendInfoScheduleService;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Component
@Deprecated 
public class JudgeReminderTimeUitl extends AbstractJudgeOrderSetUp {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeReminderTimeUitl.class);
	@Autowired
	private SmsSendInfoScheduleService smsSendInfoScheduleService;
	
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		long startTimeSys = System.currentTimeMillis();
		boolean flag = (boolean)map.get("flag");
		if(flag){ //前面的逻辑判断都通过了
			String type = String.valueOf(map.get("settingType"));
			if("2,3,4".contains(type)){
				Trade trade = (Trade)map.get("trade");
				String status = trade.getStatus();
				if (status.equals(TradesInfo.WAIT_BUYER_PAY)
						|| status.equals(TradesInfo.PAY_PENDING)
						|| status.equals(TradesInfo.TRADE_NO_CREATE_PAY)) {
				}else{
					this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+"当前订单为催付，但是订单状态为已付款，不催付");
					map.put("flag", false);
					return map;
				}
			}
			OrderSetup orderSetup = (OrderSetup)map.get("orderSetup");
			String reminderTime  = orderSetup.getReminderTime();
			if(reminderTime!=null&&!"".equals(reminderTime)){ //延后发送短信设置不为空 
				//取得下订单的时间，或者是签收的时间 （下单后xxx时间发送短信，签收后xxx时间发送短信）
				Date startDateTime = (Date)map.get("startDateTime");
				this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",用户有延后发送设置，开始进行延后发送短信设置 初始时间："+startDateTime+"，结束******************** 延后时间设置："+reminderTime);
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if(startDateTime!=null){
					SmsSendInfo smsInfo = (SmsSendInfo)map.get("smsInfo");
					SimpleDateFormat parserFomate = new SimpleDateFormat("yyyy-MM-dd ");
					int num = 0;
					
					//发送时间计算 
					if(reminderTime.contains("分钟")){
						String str[] = reminderTime.split("分钟");
						try{
							num = Integer.parseInt(str[0]);
						}catch(Exception e){}
						cal.setTime(startDateTime);
						cal.add(Calendar.MINUTE, num);
						startDateTime = cal.getTime();
					}else if(reminderTime.contains("小时")){
						String str[] = reminderTime.split("小时");
						try{
							num = Integer.parseInt(str[0]);
						}catch(Exception e){}
						cal.setTime(startDateTime);
						cal.add(Calendar.HOUR, num);
						startDateTime = cal.getTime();
					}else if(reminderTime.contains("天")){
						String str[] = reminderTime.split("天");
						try{
							num = Integer.parseInt(str[0]);
						}catch(Exception e){}
						cal.setTime(startDateTime);
						cal.add(Calendar.DATE, num);
						startDateTime = cal.getTime();
					}
					long startTimtLong = startDateTime.getTime();//拼凑好的发送时间  
					//取得用户设置的开始时间和结束时间
					String orderStartTime = orderSetup.getStartTime();
					String orderEndTime = orderSetup.getEndTime();
					Date sendTime = null,endTime = null;
					if(orderStartTime!=null && orderEndTime!=null){
						try {
							String dateString = parserFomate.format(new Date());  //取得要当天的日期 
							sendTime = parseDate.parse(dateString+orderStartTime);  //根据用户设置的发送时间和结束时间  拼凑具体时间
							endTime= parseDate.parse(dateString+orderEndTime);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
					if(sendTime!=null && endTime!=null){
						//发送的时间 在用户的时间范围之内
						if(startTimtLong>=sendTime.getTime() &&
								startTimtLong<=endTime.getTime()){//发送时间不在用户设置的范围内
							//调用短信发送接口  
							if(startTimtLong<=(System.currentTimeMillis()+65*1000)){ //发送时间小于当前时间   加五分钟
								cal.setTimeInMillis(System.currentTimeMillis());
								cal.add(Calendar.MINUTE, 1);
								smsInfo.setStartSend(cal.getTime());
								smsInfo.setEndSend(endTime);
								this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",原拼凑时间小于当前时间"+startDateTime+"  新拼凑后的开始时间："+smsInfo.getStartSend()+" ***************设置的延后时间:"+reminderTime+"****************");
								this.smsSendInfoScheduleService.doAutoCreate(smsInfo);
							}else{
								smsInfo.setStartSend(startDateTime);
								smsInfo.setEndSend(endTime);
								//当前短信为待发送短信
								smsInfo.setStatus(6);
								this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",短信改为定时发送  拼凑后的开始时间："+startDateTime+" ***************设置的延后时间:"+reminderTime+"****************");
								this.smsSendInfoScheduleService.doAutoCreate(smsInfo);
							}
							map.put("flag", false);
						}else{
							boolean isTomorrowFlag = false;
							if(map.containsKey("isTomorrow")){
								isTomorrowFlag = (boolean)map.get("isTomorrow");
							}
							if(isTomorrowFlag){
								if(startTimtLong<sendTime.getTime()){
									//发送时间远远小于用户设置的开始时间，   即时间还没到     直接把开始设置为用户设置的开始时间即可
									if(sendTime.getTime()<=(System.currentTimeMillis()+60000)){ //发送时间小于当前时间 不发送
										cal.setTimeInMillis(System.currentTimeMillis());
										cal.add(Calendar.MINUTE, 1);
										smsInfo.setStartSend(cal.getTime());
										smsInfo.setEndSend(endTime);
										this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",原拼凑时间小于当前时间"+startDateTime+"  新拼凑后的开始时间："+smsInfo.getStartSend()+" ***************设置的延后时间:"+reminderTime+"****************");
										this.smsSendInfoScheduleService.doAutoCreate(smsInfo);
									} else{
										smsInfo.setStartSend(sendTime);
										smsInfo.setEndSend(endTime);
										//当前短信为待发送短信
										smsInfo.setStatus(6);
										this.logger.debug("**当前设置的时间小于用户设置的时间，且用户设置的时间大于当前时间，存为定时短信******tid:"+map.get("tid")+",类型："+map.get("settingType")+",短信改为定时发送  拼凑后的开始时间："+startDateTime+" **********设置的延后时间:"+reminderTime+"*********");
										this.smsSendInfoScheduleService.doAutoCreate(smsInfo);
									}
									map.put("flag", false);
								}else{
									String dateString = parserFomate.format(startDateTime);  //延后n天后的时间
									try {
										sendTime = parseDate.parse(dateString+orderStartTime);
										endTime= parseDate.parse(dateString+orderEndTime);
									} catch (ParseException e) {
										e.printStackTrace();
									}  //根据用户设置的发送时间和结束时间  拼凑具体时间
									if(startTimtLong>=sendTime.getTime() && startTimtLong<=endTime.getTime()){
										smsInfo.setStartSend(startDateTime);
										smsInfo.setEndSend(endTime);
										smsInfo.setStatus(6);
										this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",短信改为定时发送  拼凑后的开始时间："+startDateTime+" ***************设置的延后时间:"+reminderTime+"****************");
										this.smsSendInfoScheduleService.doAutoCreate(smsInfo);
										map.put("flag", false);
									}else{
										if(startTimtLong<sendTime.getTime()){
											smsInfo.setStartSend(sendTime);
											smsInfo.setEndSend(endTime);
											smsInfo.setStatus(6);
											this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",短信改为定时发送  拼凑后的开始时间："+startDateTime+" ***************设置的延后时间:"+reminderTime+"****************");
											this.smsSendInfoScheduleService.doAutoCreate(smsInfo);
											map.put("flag", false);
										}else{
											cal.setTime(sendTime);
											cal.add(Calendar.DATE, 1);
											sendTime = cal.getTime();
											cal.setTime(endTime);
											cal.add(Calendar.DATE, 1);
											endTime = cal.getTime();
											smsInfo.setStartSend(sendTime);
											smsInfo.setEndSend(endTime);
											this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",短信改为定时发送  拼凑后的开始时间："+startDateTime+" ***************设置的延后时间:"+reminderTime+"****************");
											this.smsSendInfoScheduleService.doAutoCreate(smsInfo);
											map.put("flag", false);
										}
									}
								}
							}
						}
					}else{
						this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",发送时间和结束时间拼凑后全部为空！");
//						smsInfo.setStartSend(startDateTime);
//						cal.setTime(startDateTime);
//						cal.add(Calendar.MONTH, 1);
//						smsInfo.setEndSend(cal.getTime());
//						this.smsSendInfoScheduleService.doAutoCreate(smsInfo);
					}
					//延迟发送短信设置
					flag=false;
					map.put("flag", false);
				}
			}
		}
		long endTimeSys = System.currentTimeMillis();
		if((endTimeSys-startTimeSys)>1000){
			this.logger.info("花费时间："+(endTimeSys-startTimeSys)+"ms,tid:"+map.get("tid"));
		}
		//责任链判断
		AbstractJudgeOrderSetUp next = super.getNext();
		if(next!=null){
			map = next.startJob(map);
		}
		return map;
	}

}
