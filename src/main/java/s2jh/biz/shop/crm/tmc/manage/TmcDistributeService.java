package s2jh.biz.shop.crm.tmc.manage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.internal.tmc.Message;

import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.TmcInfo;
import s2jh.biz.shop.crm.tmc.rabbit.RabbitProducer;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;

/** 
* @author wy   TMC消息管理类，消息分发
* @version 创建时间：2017年8月31日 上午11:50:00
*/
@Service
public class TmcDistributeService {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(TmcDistributeService.class);
	@Autowired
	private CacheService cacheService;
	@Autowired
	private RabbitProducer rabbitProducer;
	private String tmcExChange ;
	private String tmcTradePattern ;
	private String tmcFuwuPattern ;
	public TmcDistributeService(){
		ResourceBundle  resource=ResourceBundle.getBundle("rabbitmq");
		this.tmcExChange = resource.getString("tmc.exchange");
		this.tmcTradePattern = resource.getString("tmc.trade.pattern");
		this.tmcFuwuPattern = resource.getString("tmc.fuwu.pattern");
	}
	/**
	 * 消息分发中心
	 * @author: wy
	 * @time: 2017年8月31日 下午2:52:43
	 * @param message tmc的内容和主题
	 */
	public void sendMessageToQueue(Message message){
		String topic  = message.getTopic();
		String content  = message.getContent();
		JSONObject json = JSONObject.parseObject(content);
		json.put(OrderSettingInfo.TOPIC, topic);
		if(TmcInfo.FUWU_ORDERPAID_TOPIC.equals(topic)||TmcInfo.FUWU_SERVICE_OPEN_TOPIC.equals(topic)){
			//服务类信息  spring-rabbitmq.xml
			this.logger.debug("消息准备分发到服务队列中， "+json.toJSONString());
			this.rabbitProducer.sendQueue(this.tmcExChange, this.tmcFuwuPattern, json.toJSONString());
		}else{
			boolean flag = this.validateTopic(content, topic);
			if(flag){
				//经过查询redis，发现用户对应的设置存在开始进行消息分发
				this.logger.debug("消息准备分发到订单中心队列中， "+json.toJSONString());
				this.rabbitProducer.sendQueue(this.tmcExChange, this.tmcTradePattern, json.toJSONString());
			}else{
				this.logger.debug("用户未开启对应设置，消息废弃 ， "+json.toJSONString());
			}
		}
	}
	
	
	/**
	 * 校验当前的消息主题对应的卖家设置是否有开启
	 * @author: wy
	 * @time: 2017年8月31日 下午2:55:38
	 * @param content 消息的内容，获取卖家昵称
	 * @param topic 消息的主题，获取对应的类型
	 * @return true，转发到中间件，false，不转发
	 */
	private boolean  validateTopic(String content,String topic){
		//查询topic对应用户的设置是否开启
		boolean flag = false; 
		JSONObject parseJSON = JSONObject.parseObject(content);
		switch (topic) {
			case TmcInfo.TRADE_CREATE_TOPIC:{
				//订单创建消息，下单催付
				String sellerNick = parseJSON.getString("seller_nick");
				flag = this.getTradeCreatTopic(sellerNick);
				if(!flag){
					this.logger.debug("该tmc消息，用户未开启对应的设置,消息的主题: "+topic+" ,消息的内容："+content);
				}
				break;
			}
			case TmcInfo.TRADE_BUYERPAY_TOPIC:{
				//付款消息
				String sellerNick = parseJSON.getString("seller_nick");
				boolean exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.PAYMENT_CINCERN);
				if(exists){
					flag = true;
				}else{
					//延迟发货提醒
					exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.DELAY_SEND_REMIND);
					if(exists){
						flag = true;
					}else{
						this.logger.debug("该tmc消息，用户未开启对应的设置,消息的主题: "+topic+" ,消息的内容："+content);
					}
				}
				break;
			}
			case TmcInfo.TRADE_CHANGE_TOPIC:{
				//订单变更消息
				this.logger.debug("该tmc消息暂不处理,消息的主题: "+topic+" ,消息的内容："+content);
				break;
			}
			case TmcInfo.LOGSTIC_DETAIL_TOPIC:{
				//物流消息  直接转发
				//：CREATE:物流订单创建, CONSIGN:卖家发货, GOT:揽收成功, ARRIVAL:进站, DEPARTURE:出站, SIGNED:签收成功, SENT_SCAN:派件扫描, FAILED:签收失败/拒签, LOST:丢失, SENT_CITY:到货城市, TO_EMS:订单转给EMS, OTHER:其他事件/操作
				//物流消息，消息中没有卖家昵称     示例--> {"time":"2017-08-31 00:17:34","desc":"[深圳市]深圳分拨中心 已发出","company_name":"天天快递","out_sid":"667863666892","action":"DEPARTURE","tid":52591087832483333}
				String action = parseJSON.getString("action");
				if(action!=null){
					if("CONSIGN,SENT_CITY,ARRIVAL,TMS_STATION_IN,SENT_SCAN,TMS_DELIVERING,SIGNED,TMS_SIGN,STA_SIGN".contains(action) || action.endsWith("_SIGN")){
						flag = true;
					}else{
						flag = false;
					}
				}else{
					flag = false;
				}
				break;
			}
			case TmcInfo.TRADE_SUCCESS_TOPIC :{ 
				// 订单成功
				//自动评价
				String sellerNick = parseJSON.getString("seller_nick");
				boolean exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.AUTO_RATE);
				if(exists){
					flag = true;
				}else{
					//好评提醒
					exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.GOOD_VALUTION_REMIND);
					if(exists){
						flag = true;
					}else{
						this.logger.debug("该tmc消息，用户未开启对应的设置,消息的主题: "+topic+" ,消息的内容："+content);
					}
				}
				break;
			}
			case TmcInfo.REFUND_CREATED_TOPIC :{ 
				//退款创建消息
				String sellerNick = parseJSON.getString("seller_nick");
				boolean exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.REFUND_CREATED);
				if(exists){
					flag = true;
				}else{
					this.logger.debug("该tmc消息，用户未开启对应的设置,消息的主题: "+topic+" ,消息的内容："+content);
				}
				break;
			}
			case TmcInfo.REFUND_AGREE_TOPIC :{ 
				//卖家同意退款消息
				String sellerNick = parseJSON.getString("seller_nick");
				boolean exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.REFUND_AGREE);
				if(exists){
					flag = true;
				}else{
					this.logger.debug("该tmc消息，用户未开启对应的设置,消息的主题: "+topic+" ,消息的内容："+content);
				}
				break;
			}
			case TmcInfo.REFUND_SUCCESS_TOPIC :{ 
				//退款成功
				String sellerNick = parseJSON.getString("seller_nick");
				boolean exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.REFUND_SUCCESS);
				if(exists){
					flag = true;
				}else{
					this.logger.debug("该tmc消息，用户未开启对应的设置,消息的主题: "+topic+" ,消息的内容："+content);
				}
				break;
			}
			case TmcInfo.REFUND_REFUSE_TOPIC :{ 
				//退款拒绝
				String sellerNick = parseJSON.getString("seller_nick");
				boolean exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.REFUND_REFUSE);
				if(exists){
					flag = true;
				}else{
					this.logger.debug("该tmc消息，用户未开启对应的设置,消息的主题: "+topic+" ,消息的内容："+content);
				}
				break;
			}
			case TmcInfo.TRADE_RATED_TOPIC:{
				//评价信息      中差评安抚和监控改为手动拉取实现
				this.logger.debug("该tmc消息暂不处理,消息的主题: "+topic+" ,消息的内容："+content);
				break;
			}
			default:{
				break;
			}
		}
		return flag;
	}
	/**
	 * 判断订单创建topic对应的卖家是否有开启对应的设置
	 * @author: wy
	 * @time: 2017年8月31日 下午2:23:41
	 * @param sellerNick 卖家昵称
	 * @return true 为开启，false为关闭
	 */
	private boolean getTradeCreatTopic(String sellerNick){
		boolean exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.CREATE_ORDER);
		if(exists){
			return true;
		}
		//常规催付
		exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.FIRST_PUSH_PAYMENT);
		if(exists){
			return true;
		}
		//二次催付
		exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.SECOND_PUSH_PAYMENT);
		if(exists){
			return true;
		}
		//聚划算催付
		exists = this.cacheService.getByName(OrderSettingInfo.TRADE_SETUP+sellerNick+"_"+OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT);
		if(exists){
			return true;
		}
		return false;
	}
	/**
	 * 为订单中心的设置排序，优先级越高的放前面
	 * @author: wy
	 * @time: 2017年9月5日 上午11:59:53
	 * @param c 集合
	 * @return
	 */
	public static List<TradeSetup> sortTradeSetup(Collection<Object> c){
		if(c==null || c.size()==0){
			return null;
		}
		List<TradeSetup> list = new ArrayList<TradeSetup>();
		for (Object value : c) {
			String tradeSetupString = String.valueOf(value);
			TradeSetup tradeSetup= JSONObject.parseObject(tradeSetupString, TradeSetup.class);
			if(tradeSetup.getStatus()==null || !tradeSetup.getStatus()){
				continue;
			}
			list.add(tradeSetup);
		}
		if(list.size()==1){
			return list;
		}
		Collections.sort(list);
		return list;
	}
}
