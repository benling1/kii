package s2jh.biz.shop.crm.taobao.service.judgment.manual;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;

@Component
@Deprecated 
public class JudgeManualMessageCountUtil extends AbstractJudgeOrderSetUp {
	@Autowired
	private SmsRecordService smsRecordService;
	//已发短信过滤
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		boolean flag = (boolean)map.get("flag");
		if(flag){
			OrderReminder orderReminder = (OrderReminder)map.get("orderReminder");
			try {
				if(orderReminder!=null){
					String smsFilter = orderReminder.getAlreadySendMessages();
					if(smsFilter!=null){
						Date nowDate = new Date();
						Calendar cal = Calendar.getInstance();
						TradeDTO trade = (TradeDTO)map.get("trade");
						cal.setTime(nowDate);
						switch (smsFilter) {
						case "0":{ //短信不过滤
							break;
						}
						case "1":{ //只过滤今天
							cal.add(Calendar.DATE, -1);
							flag = findSmsCount(trade,cal.getTime());
							map.put("flag", flag);
							break;
						}
						case "2":{ //近三天内
							cal.add(Calendar.DATE, -3);
							flag = findSmsCount(trade,cal.getTime());
							map.put("flag", flag);
							break;
						}
						case "3":{ //近七天
							cal.add(Calendar.DATE, -7);
							flag = findSmsCount(trade,cal.getTime());
							map.put("flag", flag);
							break;
						}
						case "4":{ //近十五天
							cal.add(Calendar.DATE, -15);
							flag = findSmsCount(trade,cal.getTime());
							map.put("flag", flag);
							break;
						}
						default:
							break;
						}
					}
				}else{
					throw new Exception("手动订单，判断订单类型时未发现map集合中有手动订单设置ManualOrderCondition");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(flag){
			//执行任务链
			AbstractJudgeOrderSetUp next = super.getNext();
			if(next!=null){
				map = next.startJob(map);
			}
		}
		return map;
	}
	/**
	 * 通过订单和开始时间，查询卖家发送手动订单给买家的短信次数
	 * @param order 具体订单信息，取得卖家昵称和买家手机号码
	 * @param startTime 开始时间
	 * @return 没有发送过0 返回true    已经发送过大于0，返回false
	 * @throws Exception
	 */
	private boolean findSmsCount(TradeDTO trade,Date startTime) throws Exception{
		boolean flag = false;
		if(trade!=null){
			Map<String,Object> selectMap = new HashMap<String,Object>();
			selectMap.put("sellerNick", trade.getSellerNick());
			selectMap.put("phone", trade.getReceiverMobile());
			selectMap.put("startTime", startTime);
			//查询买家手动订单短信发送成功次数
			Long count = smsRecordService.findSmsRecordCount("26", trade.getSellerNick(), trade.getReceiverMobile(), new Date().getTime(), startTime.getTime());
//			int count = myBatisDao.findBy(SmsSendInfo.class.getName(), "findCountBySendManaual", selectMap);
			if(count==0L){
				flag=true;
			}else{
				flag = false;
			}
		}else{
			//无订单
			throw new Exception("手动订单，判断订单类型时未发现map集合中有订单Trade");
		}
		return flag;
	}
}
