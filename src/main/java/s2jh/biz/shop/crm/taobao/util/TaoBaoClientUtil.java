package s2jh.biz.shop.crm.taobao.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.CrmServiceChannelShortlinkCreateRequest;
import com.taobao.api.request.TmcGroupAddRequest;
import com.taobao.api.request.TmcGroupDeleteRequest;
import com.taobao.api.request.TmcUserCancelRequest;
import com.taobao.api.request.TmcUserPermitRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TraderateAddRequest;
import com.taobao.api.response.CrmServiceChannelShortlinkCreateResponse;
import com.taobao.api.response.TmcGroupAddResponse;
import com.taobao.api.response.TmcGroupDeleteResponse;
import com.taobao.api.response.TmcUserCancelResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TraderateAddResponse;

import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.taobao.info.TmcInfo;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
/**
 * 淘宝api工具类
 * @author zhrt2
 *
 */
public class TaoBaoClientUtil {
	private TaoBaoClientUtil(){};
	private static Logger logger = LoggerFactory.getLogger(TaoBaoClientUtil.class);
	public static final TaobaoClient TAOBAO_CLIENT = new DefaultTaobaoClient(taobaoInfo.url,taobaoInfo.appKey, taobaoInfo.appSecret);
	/**
	 * 根据tid和用户的sessionKey查询出对应的主订单信息<br>
	 * api网址:http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.FKc4Uq&apiId=54
	 * @param tid 主订单id
	 * @param sessionKey 登录密钥
	 * @return 如果查询到 返回trade对象 如果查询不到 返回null
	 */
	public static Trade getTradeByTaoBaoAPI(Long tid,String sessionKey){
		if(tid!=null && sessionKey!=null){
			try {
				logger.debug("调用了api订单查询  tid:   "+ tid);
				TaobaoClient client = TaoBaoClientUtil.TAOBAO_CLIENT;
				TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
				req.setFields("tid,seller_nick,trade_from,order_from,payment,seller_rate,receiver_name,receiver_state,receiver_address,receiver_mobile,receiver_phone,consign_time,receiver_country,receiver_town,num,num_iid,status,type,price,total_fee,created,pay_time,modified,end_time,seller_flag,buyer_nick,mark_desc,buyer_rate,receiver_city,receiver_district,orders,step_trade_status");
				req.setTid(tid);
				TradeFullinfoGetResponse rsp;
				rsp = client.execute(req, sessionKey);
				String subCode = rsp.getSubCode();
				if(subCode !=null && subCode.equals("invalid-sessionkey")){
					return null;
				}
				Trade trade = rsp.getTrade();
				if(trade==null)
					return null;
				//判断买家昵称是否为密文，如果是则解密
				if(EncrptAndDecryptClient.isEncryptData(trade.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
					trade.setBuyerNick(EncrptAndDecryptClient.getInstance().decrypt(trade.getBuyerNick(),EncrptAndDecryptClient.SEARCH,sessionKey));
				}
				//判断买家手机号是否为密文，如果是则解密
				if(EncrptAndDecryptClient.isEncryptData(trade.getReceiverMobile(), EncrptAndDecryptClient.PHONE)){
					trade.setReceiverMobile(EncrptAndDecryptClient.getInstance().decrypt(trade.getReceiverMobile(),EncrptAndDecryptClient.PHONE,sessionKey));
				}
				//判断买家座机是否为密文，如果是则解密
				if(EncrptAndDecryptClient.isEncryptData(trade.getReceiverPhone(), EncrptAndDecryptClient.SIMPLE)){
					trade.setReceiverPhone(EncrptAndDecryptClient.getInstance().decrypt(trade.getReceiverPhone(),EncrptAndDecryptClient.SIMPLE,sessionKey));
				}
				//判断买家姓名是否为密文，如果是则解密
				if(EncrptAndDecryptClient.isEncryptData(trade.getReceiverName(), EncrptAndDecryptClient.SEARCH)){
					trade.setReceiverName(EncrptAndDecryptClient.getInstance().decrypt(trade.getReceiverName(),EncrptAndDecryptClient.SEARCH,sessionKey));
				}
				//判断收货人街道地址是否为密文，如果是则解密
				if(EncrptAndDecryptClient.isEncryptData(trade.getReceiverAddress(), EncrptAndDecryptClient.SEARCH)){
					trade.setReceiverAddress(EncrptAndDecryptClient.getInstance().decrypt(trade.getReceiverAddress(),EncrptAndDecryptClient.SEARCH,sessionKey));
				}
				
				return trade;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 淘宝自动评价工具类   <br>
	 * api网址：http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.jiDYWd&apiId=56
	 * @param tid 主订单ＩＤ
	 * @param oid 子订单ID
	 * @param sellerNick 卖家昵称
	 * @param content 评价内容
	 * @param type 评价类型  good bad  
	 * @param sessionKey 卖家的sessionKey
	 * @return 评价结果
	 * @throws ApiException
	 */
	public static TraderateAddResponse autoTaobaoTraderate(Long tid,Long oid,String content,String type,String sessionKey){
		TraderateAddResponse rsp = null;
		try {
			TaobaoClient client = TaoBaoClientUtil.TAOBAO_CLIENT;
			TraderateAddRequest req = new TraderateAddRequest();
			req.setTid(tid);
			req.setOid(oid);
			req.setResult(type);
			req.setRole("seller");
			req.setContent(content);
			rsp = client.execute(req, sessionKey);
		} catch (ApiException e) {
			e.printStackTrace();
			logger.info("------------------------------自动评价调用接口异常！！！！----------------------------------");
		}
		if(rsp != null){
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~评价成功，结果是："+rsp.getBody()+"IIIIIIIII~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
		return rsp;
	}
	
	/**
	 * 为已开通用户添加用户分组     <br>
	 * api网址：http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.09RfdX&apiId=21983
	 * @author: wy
	 * @time: 2017年10月10日 上午11:32:05
	 * @param groppName 分组名称
	 * @param sellerNicks 卖家昵称
	 * @return true：添加成功，false：添加失败
	 */
	public static boolean doTmcGroupAdd(String groppName,String sellerNicks){
		try {
			TaobaoClient client = TaoBaoClientUtil.TAOBAO_CLIENT;
			TmcGroupAddRequest req = new TmcGroupAddRequest();
			req.setGroupName(groppName);
			req.setNicks(sellerNicks);
			TmcGroupAddResponse  rsp = client.execute(req);
			String msg = rsp.getMsg();
			if("Remote service error".equals(msg) || msg ==null){
				return false;
			}
			return true;
		} catch (ApiException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 删除指定分组下的用户     <br>
	 * api网址：http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.xJ2eyr&apiId=21982
	 * @author: wy
	 * @time: 2017年9月25日 下午5:51:32
	 * @param groppName 用户分组
	 * @param sellerNicks 买家昵称集合
	 * @return 成功返回true，失败返回false
	 */
	public static boolean doTmcGroupDelete(String groppName,String sellerNicks){
		try {
			TaobaoClient client = TaoBaoClientUtil.TAOBAO_CLIENT;
			TmcGroupDeleteRequest req = new TmcGroupDeleteRequest();
			req.setGroupName(groppName);
			req.setNicks(sellerNicks);
			TmcGroupDeleteResponse rsp = client.execute(req);
			String msg = rsp.getMsg();
			if("Remote service error".equals(msg) || msg ==null){
				return false;
			}
			return true;
		} catch (ApiException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 为已授权的用户开通消息服务,根据用户的sessionKey和具体的Topic来为用户开启消息 <br>
	 * api网址：http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.FOPKkl&apiId=21990
	 * @author: wy
	 * @time: 2017年8月23日 下午2:59:26
	 * @param sessionKey 用户的秘钥
	 * @param topics 要为用户授权的消息，如果消息为空，则会强制加上服务开通和服务支付两个消息
	 * @return 成功返回true，失败返回false
	 */
	public static  boolean doTmcUserPermit(String sessionKey,String topics){
		TaobaoClient client = TaoBaoClientUtil.TAOBAO_CLIENT;
		if(ValidateUtil.isEmpty(sessionKey)){
			return false;
		}
		if(ValidateUtil.isEmpty(topics)){
			topics = "";
		}
		if(!topics.contains(TmcInfo.FUWU_SERVICE_OPEN_TOPIC)){
			topics = TmcInfo.FUWU_SERVICE_OPEN_TOPIC + topics;
		}
		if(!topics.contains(TmcInfo.FUWU_ORDERPAID_TOPIC)){
			topics = TmcInfo.FUWU_ORDERPAID_TOPIC + topics;
		}
		TmcUserPermitRequest req = new TmcUserPermitRequest();
		req.setTopics(topics);
		try {
			client.execute(req, sessionKey);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	} 
	/**
	 * 取消用户的消息服务 <br>
	 * api网址：http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.fN0XzF&apiId=21989
	 * @author: wy
	 * @time: 2017年10月10日 上午11:50:31
	 * @param sellerNicks 卖家昵称
	 * @return 成功返回true，失败返回false
	 */
	public static boolean doTmcUserCancel(String sellerNicks){
		try {
			TaobaoClient client = TaoBaoClientUtil.TAOBAO_CLIENT;
			TmcUserCancelRequest req = new TmcUserCancelRequest();
			req.setNick("testNick");
			TmcUserCancelResponse rsp = client.execute(req);
			String msg = rsp.getMsg();
			if("Remote service error".equals(msg) || msg ==null){
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 校验数据
	 */
	private static List<String> assist = new ArrayList<String>(){
		private static final long serialVersionUID = -7379595965111490439L;
		{
			add("LT_ITEM");add("LT_ACTIVITY");
			add("LT_SHOP");add("LT_TRADE");
		}
	};
	/**
	 * 短链接响应示例
	 *  <crm_service_channel_shortlink_create_response>
	 *   	<short_link>c.tb.cn/DFe3c</short_link>
	 *  </crm_service_channel_shortlink_create_response>
	 *   
	 *	异常示示例
	 *	<error_response>
	 *    	<code>50</code>
	 *    	<msg>Remote service error</msg>
	 *    	<sub_code>isv.invalid-parameter</sub_code>
	 *    	<sub_msg>非法参数</sub_msg>
	 *  </error_response>
	 *	文档访问地址:http://open.taobao.com/docs/api.htm?spm=a219a.7386797.0.0.PygMst&source=search&apiId=25034
	 * 
	 * @Title: creatLink
	 * @Description: 根据类型:<店铺首页,商品,活动,订单>生成相关短链接
	 * @return String  返回类型
	 * @author:jackstraw_yu
	 * @throws
	*/
	public static String creatLink(String token,String linkType,String param){
		if(linkType==null || !assist.contains(linkType))
			return null;
		if(!"LT_SHOP".equals(linkType) && param==null)//短链接为店铺首页时:req.setShortLinkData(null);
			return null;
		CrmServiceChannelShortlinkCreateRequest req = new CrmServiceChannelShortlinkCreateRequest();
		req.setShortLinkName("生成短链接"+DateUtils.formatDate(new Date(), DateUtils.DEFAULT_TIME_FORMAT));	
		if("LT_ITEM".equals(linkType)){//商品
			req.setLinkType("LT_ITEM");
		}else if ("LT_ACTIVITY".equals(linkType)){//活动
			req.setLinkType("LT_ACTIVITY");
		}else if("LT_SHOP".equals(linkType)){//店铺首页
			req.setLinkType("LT_SHOP");
		}else if("LT_TRADE".equals(linkType)){//订单
			req.setLinkType("LT_TRADE");
		}else{
			return null;
		}
		req.setShortLinkData(param);
		CrmServiceChannelShortlinkCreateResponse rsp = null;
		try {
			rsp = TAOBAO_CLIENT.execute(req, token);
		} catch (Exception e) {
			logger.error("########################TaoBaoClientUtil.creatLink Exception"+e.getMessage());
			return null;
		}
		if(rsp.getMsg()!=null&&rsp.getMsg().equals("Remote service error")){
			if(rsp.getSubMsg()!=null)
				return rsp.getSubMsg();
		}else{
			if(rsp.getShortLink()!=null)
				return rsp.getShortLink();
		}
		return null;
	}
	
}
