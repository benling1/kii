package s2jh.biz.shop.crm.taobao.service.judgment.logistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
@Component
@Deprecated 
public class JudgeDelayRemindUtil extends AbstractJudgeOrderSetUp {

	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		Boolean flag = (Boolean) map.get("flag");
		//如果前面都通过，并且是延迟发货提醒
		if (flag&&map.get("settingType").equals("11")) {
			String startTime = (String) map.get("startTime");
			String endTime = (String)map.get("endTime");
			String executeGenre = (String) map.get("executeGenre");
			Date myDate = (Date) map.get("myDate");
			
			if (startTime!=null && endTime!=null) {
				//获得订单筛选的开始和结束时间
				HashMap<Object, Object> setMap = new HashMap<>();
				setMap.put("startTime", startTime);
				setMap.put("endTime", endTime);
				setMap.put("status", "WAIT_SELLER_SEND_GOODS");
				setMap.put("executeGenre",executeGenre);
				//@author:jackstraw_yu 修改
				setMap.put("sellerName",map.get("sellerName"));
				//根据时间范围和状态查询所有订单集合
				//List<Orders> orders = myBatisDao.findList(Orders.class.getName(), "findOrdersByTimeAndStatus", setMap);
				List<OrdersDTO> orders = tradeInfoService.queryOrdersByTimeAndStatus(setMap);
				List<SmsSendInfo> sendInfos = new ArrayList<SmsSendInfo>();
				for (OrdersDTO order : orders) {
					SmsSendInfo smsInfo = (SmsSendInfo) map.get("smsInfo");
					String phone = order.getReceiverMobile();
					String nickName = order.getBuyerNick();
					String oid = order.getOid();
					smsInfo.setPhone(phone);
					smsInfo.setNickname(nickName);
					smsInfo.setSendTime(myDate);
					if(map.containsKey("tid")){
						smsInfo.setTid(Long.parseLong(String.valueOf(map.get("tid"))));
					}
					sendInfos.add(smsInfo);
					
				}
				//如果是立即提醒，则只发送一次
				if (executeGenre.equals("0")) {
					map.put("orders", orders);
					map.put("merge", "merge");
					
				}else{
					//否则启动定时任务，多次发送
					
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
