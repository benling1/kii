package s2jh.biz.shop.crm.tmc.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BillRecordDto;
import com.taobao.api.domain.IncomeConfirmDto;
import com.taobao.api.request.FuwuSpBillreordAddRequest;
import com.taobao.api.request.FuwuSpConfirmApplyRequest;
import com.taobao.api.response.FuwuSpBillreordAddResponse;
import com.taobao.api.response.FuwuSpConfirmApplyResponse;

import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.TmcInfo;
import s2jh.biz.shop.crm.tmc.rabbit.RabbitFuwuConsumer;
import s2jh.biz.shop.crm.user.entity.RechargeMenu;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.entity.UserRecharge;
import s2jh.biz.shop.crm.user.service.RechargeMenuService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.user.service.UserRechargeService;
import s2jh.biz.shop.utils.SpringContextUtil;

/** 
 * 服务tmc消息管理类
* @author wy
* @version 创建时间：2017年9月5日 下午5:13:20
*/
@Service
@SuppressWarnings("unused")
public class FuwuTmcManageService {
	private Logger logger = LoggerFactory.getLogger(FuwuTmcManageService.class);
	
	@Autowired
	private UserRechargeService userRechargeService;
	
	@Autowired
	private RechargeMenuService rechargeMenuService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	public void doHandle(String str){
		this.logger.debug("服务tmc开始处理-->"+str);
    	JSONObject json = JSONObject.parseObject(str);
    	String topic = json.getString(OrderSettingInfo.TOPIC);
    	if(TmcInfo.FUWU_ORDERPAID_TOPIC.equals(topic)){
    	    this.logger.debug("准备充值 " + json);
    		this.fuwuOrderPaid(json);
    	}else if(TmcInfo.FUWU_SERVICE_OPEN_TOPIC.equals(topic)){
//    		this.addUser(json);
    	}else{
    		this.logger.debug("错误的消息！！！ "+str);
    	}
	}
	
	
	
	 /**
     * 服务支付消息
     * @author: wy
     * @time: 2017年9月5日 下午4:53:44
     * @param content
     */
    private void fuwuOrderPaid(JSONObject content){
        this.logger.debug("准备充值： " + content);
    	String articleCode = content.getString("article_code");
    	if (taobaoInfo.appCode.equals(articleCode)) {
//			addUser(content);
		} else {
			addUserRecharge(content);
		}
    }
    
    private void addUserRecharge(JSONObject json) {
		// 判空
		if (json != null ) {
			// 获取订单id
			String orderId = json.getString("order_id");
			Map<String, Object> map = new HashMap<String, Object>(5);
			map.put("orderId", orderId);
			// 查询充值列表,判断充值列表中是否存在该order_id
			long l = userRechargeService.getUserRechar(map);
			// 不存在该orderId
			if (l == 0) {
				UserRecharge userRecharge = new UserRecharge();
				// 充值用户昵称
				userRecharge.setUserNick(json.getString("nick"));
				// 充值用户编号
				userRecharge.setOrderId(json.getString("order_id"));

				// 调用RechargeMenuService获取到短信套餐的条数
				RechargeMenu rechargeMenu = rechargeMenuService
						.queryRechargeMenu(json.getString("item_code"));
				if (rechargeMenu != null) {
					userRecharge.setRechargeNUm(rechargeMenu.getNum());
				}

				String string = json.getString("total_pay_fee");
				double rechargePrice = Double.parseDouble(json
						.getString("total_pay_fee"));
				// 1分换算成1元
				userRecharge.setRechargePrice(rechargePrice / 100);
				userRecharge.setRechargeDate(new Date());
				//充值,保存记录
				userInfoService.doUserRecharge(userRecharge);
				getUserRechargeInfo(json.getString("order_id"));

			}
		}
	}
    
    /**
	 * 判断用户是否存在，不存在则添加新用户；如果存在则更新用户应用到期时间
	 * 
	 * @param message
	 */
    private void addUser(JSONObject json) {
//		// String
//		// content="{'refund_fee':'0','biz_type':2,'order_cycle':'2','article_name':'客云CRM','article_code':'FW_GOODS-1952286','order_id':310022087290045,'create':'2017-03-10 11:20:03','order_status':3,'pay_status':1,'biz_order_id':310022087290045,'fee':'1500.0','prom_fee':'0.0','order_cycle_end':'2017-05-10 00:00:00','total_pay_fee':'1500.0','item_code':'FW_GOODS-1952286-1','nick':'哈数据库等哈','item_name':'经典版','version_no':1,'order_cycle_start':'2017-04-10 00:00:00','outer_trade_code':''}";
//		if (json != null ) {
//			// 根据用户昵称查询用户是否存在
//			UserInfo ui = null;
//			ui = userInfoService.findUserInfo(json.getString("nick"));
//
//			SimpleDateFormat dateFormat = new SimpleDateFormat(
//					"yyyy-MM-dd HH:mm:ss");
//			// 判断用户是否存在，不存在则添加新用户，存在则更新用户应用的到期时间
//			if (ui == null) {
//				ui = new UserInfo();
//				// ui.setTaobaoUserId(json.getString("taobao_user_id"));
//				ui.setTaobaoUserNick(json.getString("nick"));
//				ui.setCreateTime(new Date());
//				try {
//					ui.setExpirationTime(dateFormat.parse(json
//							.getString("order_cycle_end")));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				ui.setSmsNum(0);
//				ui.setEmailNum(0);
//				ui.setStatus(0);
//				ui.setLastLoginDate(new Date());
//				userInfoService.addUserInfo(ui);
//			} else {
//				// 更新用户应用到期时间
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("taobao_user_nick", ui.getTaobaoUserNick());
//				try {
//					map.put("expiration_time",
//							dateFormat.parse(json.getString("order_cycle_end")));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				ui.setLastLoginDate(new Date());
//				userInfoService.updateUserInfo(map);
//			}
//		}
	}
    /**
	 * 根据订单编号查询订单信息，并生成帐单上传到淘宝
	 * 
	 * @param orderId
	 * @return
	 */
	private void getUserRechargeInfo(String orderId) {
		Map<String, Object> map = new HashMap<String, Object>(5);
		map.put("orderId", orderId);
		UserRecharge ur = userRechargeService.getUserRechargeInfo(map);
		// 根据卖家昵称查询用户信息（取出用户的sessionKey即access_token）
		UserInfo ui = new UserInfo();
		ui = userInfoService.findUserInfo(ur.getUserNick());
		if (ur == null) {
			return;
		}
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
				taobaoInfo.appKey, taobaoInfo.appSecret);
		//上传充值订单详情
		FuwuSpBillreordAddRequest req = new FuwuSpBillreordAddRequest();
		BillRecordDto obj1 = new BillRecordDto();
		obj1.setStartDate(ur.getRechargeDate());
		if (ur.getStatus() != null) {
			obj1.setStatus(Long.parseLong(ur.getStatus()));
		}
		obj1.setAppkey(taobaoInfo.appKey);
		obj1.setType(1L);
		if (ui != null && ui.getMobile() != null) {
			obj1.setTargetNo(ui.getMobile());
		} else {
			obj1.setTargetNo(ui.getId().toString());
		}
		Long fee = new Double(ur.getRechargePrice() * 100).longValue();
		obj1.setFee(fee);
		obj1.setNick(ur.getUserNick());
		obj1.setOutConfirmId(ur.getId().toString());
		if (ur.getOrderId() != null) {
			obj1.setOrderId(Long.parseLong(ur.getOrderId()));
		}
		obj1.setOutOrderId(ur.getOrderId());
		req.setParamBillRecordDTO(obj1);
		FuwuSpBillreordAddResponse rsp = null;
		try {
			rsp = client.execute(req, ui.getAccess_token());
		} catch (ApiException e) {
			e.printStackTrace();
		}

		//创建确认单，申请用户确认
		FuwuSpConfirmApplyRequest req1 = new FuwuSpConfirmApplyRequest();
		IncomeConfirmDto obj2 = new IncomeConfirmDto();
		obj2.setFee(fee);
		obj2.setNick(ur.getUserNick());
		obj2.setAppkey(taobaoInfo.appKey);
		if (ur.getOrderId() != null) {
			obj2.setOrderId(Long.parseLong(ur.getOrderId()));
		}
		obj2.setOutConfirmId(ur.getId().toString());
		req1.setParamIncomeConfirmDTO(obj2);
		FuwuSpConfirmApplyResponse rsp1 = null;
		try {
			rsp1 = client.execute(req1, ui.getAccess_token());
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}
}
