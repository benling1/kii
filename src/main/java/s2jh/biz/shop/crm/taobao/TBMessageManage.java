package s2jh.biz.shop.crm.taobao;

import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageStatus;

import lab.s2jh.core.service.RedisLockServiceImpl;
import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.taobao.service.SendMessageService;
import s2jh.biz.shop.crm.taobao.service.appraise.monitoring.AppraiseService;
import s2jh.biz.shop.crm.taobao.service.delItem.DelItemService;
import s2jh.biz.shop.crm.taobao.service.judgment.logistics.LogisticsMainUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.logistics.consign.LogisticsService;
import s2jh.biz.shop.crm.taobao.service.ordercare.PlaceAnOrderCareService;
import s2jh.biz.shop.crm.taobao.service.refund.created.RefundCreatedService;
import s2jh.biz.shop.crm.taobao.service.refund.seller.refuse.agreement.RefundSellerRefuseAgreementService;
import s2jh.biz.shop.crm.taobao.service.refund.success.RefundSuccessService;
import s2jh.biz.shop.crm.taobao.service.refund.waiting.WaitingRefundService;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;

//接收消息处理类
@Component
@Deprecated 
public class TBMessageManage  {
	Logger logger = org.slf4j.LoggerFactory.getLogger(TBMessageManage.class);
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private LogisticsMainUtil logisticsMainUtil;
	@Autowired
	private RefundCreatedService refundCreatedService;
	@Autowired
	private RefundSuccessService refundSuccessService;
	@Autowired
	private PlaceAnOrderCareService placeAnOrderCareService;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private AppraiseService appraiseService;
//	@Autowired
//	private UpdateItemService updateItemService;
	@Autowired
	private DelItemService delItemService;
	@Autowired
	private RefundSellerRefuseAgreementService refundSellerRefuseAgreementService;
	@Autowired
	private WaitingRefundService waitingRefundService;
	@Autowired
	private RedisLockServiceImpl redisLock;
	public TBMessageManage() {
		
	}
	public void run(Message message,MessageStatus status) throws Exception {
		long start = System.currentTimeMillis();
		try {
			logger.info("消息开始处理啦,主题：" + message.getTopic() + "，消息状态: " +message.getContent());
			JSONObject parseJSON = JSONObject.fromObject(message.getContent());
			switch (message.getTopic()) {
			case "taobao_fuwu_OrderPaid": {// 服务订单支付消息
				break;
			}
			case "taobao_trade_TradeSuccess": {// 买家-商户   交易成功消息
				// 找到对应的业务层处理
				try {
					logger.info("~~~~~~~~自动评价1：tradeSuccess");
					this.sendMessageService.tradeSuccess(parseJSON);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			case "taobao_trade_TradeClose": {// 买家-商户  关闭交易消息
				this.sendMessageService.tradeClose(parseJSON);
				break;
			}
			case "taobao_trade_TradeChanged": {// 买家-商户   订单信息变更消息
				this.sendMessageService.tradeChanged(parseJSON);
				break;
			}
			case "taobao_trade_TradeBuyerPay":{
				this.sendMessageService.payRemind(parseJSON);
				break;
			}
			case "taobao_trade_TradeCreate": {// 买家-商户   创建淘宝交易消息
//			case "taobao_trade_TradeAlipayCreate": {// 买家-商户   创建淘宝交易消息
				this.placeAnOrderCareService.tradeCreate(parseJSON);
				break;
			}
			case "taobao_refund_RefundSuccess": {//买家退款成功
				this.refundSuccessService.refundSuccess(parseJSON);
				break;
			}
			case "taobao_refund_RefundCreated": {// 买家申请退货消息
				this.refundCreatedService.refundCreate(parseJSON);
				break;
			}
			case "taobao_refund_RefundSellerRefuseAgreement":{
				this.refundSellerRefuseAgreementService.refuseRefund(parseJSON);
				break;
			}
			case "taobao_refund_RefundSellerAgreeAgreement":{
				this.waitingRefundService.waitingRefund(parseJSON);
				break;
			}
			case "taobao_logistics_LogsticDetailTrace":{//物流详情跟踪消息
				try {
					//action 事件名称。类型如下：CREATE:物流订单创建, CONSIGN:卖家发货, GOT:揽收成功, ARRIVAL:进站, DEPARTURE:出站, SIGNED:签收成功,
					//            SENT_SCAN:派件扫描, FAILED:签收失败/拒签, LOST:丢失, SENT_CITY:到货城市, TO_EMS:订单转给EMS, OTHER:其他事件/操作
//					parseJSON = JSONObject.fromObject("{'action':'CONSIGN','company_name':'顺丰','desc':'已经到达地点1','out_sid':'078869350417','tid':'193546700400400','time':'2015-05-27 16:33:04'}");
					String action = parseJSON.getString("action");
					if (action.equals("CONSIGN")) {//卖家发货
						this.logisticsService.logisticsConsign(parseJSON);
					}
					if (action.equals("SENT_CITY")||action.contains("ARRIVAL")||action.contains("TMS_STATION_IN")) {//到货城市
						this.logisticsService.logisticsSentCity(parseJSON);
					}
					if (action.equals("SENT_SCAN")||action.equals("TMS_DELIVERING")) {//派件
						this.logisticsService.logisticsSentScan(parseJSON);
					}
					if (action.equals("SIGNED")||action.equals("TMS_SIGN")) {//签收
						String outSid = null;
						if (parseJSON.containsKey("out_sid")) {
							outSid = parseJSON.getString("out_sid");//运单号
						}
						if(ValidateUtil.isEmpty(outSid)){
							this.logger.debug("当前物流运单号为空"+parseJSON.getString("tid"));
							return ;
						}
						long l = this.redisLock.setnx(outSid+"_singed_lock", System.currentTimeMillis()+"", 1800L);
						if(l==0){
							this.logger.debug("物流签收加锁失败，消息丢弃"+parseJSON.getString("tid"));
							return ;
						}
						try {
							this.logisticsService.logisticsSigned(parseJSON);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							this.sendMessageService.goodsCowryCareSinged(parseJSON);//宝贝关怀
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							this.sendMessageService.returnedMoneyWarn(parseJSON);//回款提醒
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					String puzzle = "FAILED,LOST,TO_EMS";
					if (puzzle.contains(action)) {//是疑难件
						this.logisticsService.logisticsPuzzleRemind(parseJSON,new Date(), "10");
					}
					//物流更新成功后，设置短信  状态为物流更新超时
					if ("GOT".equals(action) || "ARRIVAL".equals(action) || "DEPARTURE".equals(action) ||"SENT_SCAN".equals(action)) {
						this.logisticsMainUtil.logisticsSendMessage(parseJSON, new Date(), "10");
					}
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			case "taobao_trade_TradeRated":{//交易评价变更消息
				this.appraiseService.appraiseRated(parseJSON);
				break;
			}
			case "taobao_item_ItemDelete":{//商品删除消息
				this.delItemService.delItem(parseJSON);
				break;
			}
			default:
				break;
			}
		} finally {
			long end = System.currentTimeMillis();
			long result = end-start;
			if(result>5000){
				logger.debug("tmc执行结束，执行花费时间："+(result)+"ms,执行topic："+message.getTopic()+" 执行json："+message.getContent());
			}
		}
	}
}
