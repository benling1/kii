package s2jh.biz.shop.crm.message.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.CacheService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taobao.api.SecretException;

import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.pagination.Pagination;


@Controller
public class OrderCenterSendRecordController {

	@Autowired
	private SmsSendRecordService smsSendRecordService;
	
	@Autowired
	private SmsRecordService smsRecordService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private UserInfoService userInfoService;
	private static final Log logger = LogFactory.getLog(SmsSendRecordController.class);
	/**
	 * 订单中心所有页面的发送记录
	 * ZTK2017年4月20日下午6:10:57
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sendRecord/orderCenterRecord")
	public String findCuiFuSendRecord(String recNum,String status,Integer pageNo,String recordFlag,
			String buyerNick,String beginTime,String endTime,Model model,HttpServletRequest request,String type,String orderId){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Integer count = 0;
		boolean like = false;
		if(pageNo == null || pageNo <= 0){
			pageNo = 1;
		}
		if(buyerNick != null){
			buyerNick = buyerNick.replace(" ","").replace(" ","");
		}
		//封装查询数据
		if(type != null && "0".equals(type)){
			type = null;
		}
		if(status != null && "0".equals(status)){
			status = null;
		}
		String returnUrl = "";
		if(recordFlag != null && !"".equals(recordFlag)){
			switch (recordFlag) {
				case "1"://下单关怀
					if(type != null && !"".equals(type)){
					}else {
						type = "1";
					}
					returnUrl = "crms/orderCenterSendRecord/placeAnOrderRecord";
					break;
				case "2"://催付提醒
					if(type != null && !"".equals(type)){
					}else {
						type = "2,3,4";
					}
					returnUrl = "crms/orderCenterSendRecord/cuiFuSendRecord";
					break;
				case "3"://物流提醒
					if(type != null && !"".equals(type)){
					}else {
						type = "6,7,8,9,10,11";
					}
					returnUrl = "crms/order/logisticsSendRecord";
					break;
				case "4"://宝贝关怀
					if(type != null && !"".equals(type)){
					}else {
						type = "12";
					}
					returnUrl = "crms/orderCenterSendRecord/cowryCareSendRecord";
					break;
				case "5"://付款关怀
					if(type != null && !"".equals(type)){
					}else {
						type = "13";
					}
					returnUrl = "crms/orderCenterSendRecord/paymentSendRecord";
					break;
				case "6"://回款提醒
					if(type != null && !"".equals(type)){
					}else {
						type = "14";
					}
					returnUrl = "crms/orderCenterSendRecord/returnMoneySendRecord";
					break;
				case "7"://退款关怀
					if(type != null && !"".equals(type)){
					}else {
						type = "29,30,31,32";
					}
					returnUrl = "crms/orderCenterSendRecord/buyerRefundSendRecord";
					break;
				case "8":
					if(type != null && !"".equals(type)){
					}else {
						type = "20";
					}
					model.addAttribute("flag","showRecord");
					returnUrl = "crms/appraise/appraiseMonitoring";
					break;
				case "9":
					if(type != null && !"".equals(type)){
					}else {
						type = "21";
					}
					model.addAttribute("flag","showRecord");
					returnUrl = "crms/appraise/appraisePacify";
					break;
				case "10":
					if(type != null && !"".equals(type)){
					}else {
						type = "26";
					}
					returnUrl = "/crms/orderReminder/shoudong_fasongliebiao";
					break;
				default:
					if(type != null && !"".equals(type)){
					}else {
						type = "2,3,4";
					}
					returnUrl = "crms/orderCenterSendRecord/cuiFuSendRecord";
					break;
			}
		}
		SmsRecordDTO srd = new SmsRecordDTO();
		if(type!=null&&!"".equals(type)){
			srd.setType(type);
		}
		if(recNum!=null&&!"".equals(recNum)){
			if(recNum.length()==4)
				like = true;
			String phone =encryptSearchMobiles(recNum,userId);
			srd.setRecNum(phone);
		}
		/*买家昵称*/
		if(buyerNick!=null&&!"".equals(buyerNick)){
			String buyer =encryptSearchNick(buyerNick,userId);
			srd.setBuyerNick(buyer);
		}
		if(userId!=null&&!"".equals(userId)){
			srd.setUserId(userId);
		}
		if(status!=null&&!"".equals(status)){
			srd.setStatus(Integer.parseInt(status));
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long etime = 0L;
		Long btime = 0L;
		if(beginTime!=null&&!"".equals(beginTime)){
			try {
				btime = sdf.parse(beginTime + " 00:00:00").getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(endTime!=null&&!"".equals(endTime)){
			try {
				etime = sdf.parse(endTime + " 23:59:59").getTime();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		if(null!=orderId&&!"".equals(orderId)){
			srd.setOrderId(orderId);
		}
		Pageination<SmsRecordDTO> page = new Pageination<SmsRecordDTO>();
		page.setPageNo(pageNo);
		page.setPageSize(10);
		Pageination pagination = smsRecordService.findSendRecordPageList(page, srd, userId,btime,etime,like);
		List<SmsRecordDTO> list = new ArrayList<SmsRecordDTO>();
		if(pagination!=null&&pagination.getTotalCount()>0L){
			list = pagination.getDatas();
			list = smsRecordService.ConvertDate(list, userId);
			count = new Long(pagination.getTotalCount()).intValue();
		}
		Pagination pt = new Pagination(pageNo, 10, count, list);
		String url = request.getContextPath() + "/sendRecord/orderCenterRecord";
		String params = createParams(recNum, status, type, buyerNick, beginTime, endTime,recordFlag);
		pt.pageView(url, params);
		model.addAttribute("recNum",recNum );
		model.addAttribute("type",type );
		model.addAttribute("status",status );
		model.addAttribute("buyerNick", buyerNick);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime",endTime);
		model.addAttribute("orderId", orderId);
		model.addAttribute("pagination", pt);
		
		return returnUrl;
	}
	
	
	
	
	
	
	
	
	/**
	 * 公用的生成返回参数的方法
	 * ZTK2017年4月20日下午6:09:46
	 */
	public String createParams(String recNum,String status,String type,String buyerNick,
			String beginTime,String endTime,String recordFlag){
		StringBuilder params = new StringBuilder();
		if(recNum != null && !"".equals(recNum)){
			params.append("&recNum=").append(recNum);
		}
		if(status != null && !"".equals(status)){
			params.append("&status=").append(status);
		}
		if(type != null &&!"".equals(type)){
			params.append("&type=").append(type);
		}
		if(buyerNick != null && !"".equals(buyerNick)){
			params.append("&buyerNick=").append(buyerNick);
		}
		if(beginTime != null && !"".equals(beginTime)){
			params.append("&beginTime=").append(beginTime);
		}
		if(endTime != null && !"".equals(endTime)){
			params.append("&endTime=").append(endTime);
		}
		if(recordFlag != null && !"".equals(recordFlag)){
			params.append("&recordFlag=").append(recordFlag);
		}
		return params.toString();
	}
	
	
	/**
	 * @Description:订单中心短信发送记录查询:
	 * 入参手机号长度大于4则直接加密
	 * 小于等于4模糊加密
	 * @author jackstraw_yu
	 */
	private String encryptSearchMobiles(String mobile,String nick) {
		EncrptAndDecryptClient client = EncrptAndDecryptClient.getInstance(); 
		String key = getSessionkey(nick,true),encrypt=null;
		if(mobile.length()!=4){
			try {
				encrypt = client.encrypt(mobile, EncrptAndDecryptClient.PHONE,key);
			} catch (Exception e) {
				logger.error("订单中心-短信发送记录手机号查询加密失败!");
				key = getSessionkey(nick,false);
				try {
					encrypt = client.encrypt(mobile, EncrptAndDecryptClient.PHONE,key);
				} catch (Exception e1) {
					logger.error("订单中心-短信发送记录手机号查询<>再次<>加密失败!");
				}
			}
		}else{
			try {
				encrypt = client.search(mobile, EncrptAndDecryptClient.PHONE,key).replace("$1$$","").replace("$", "").replace("=", "");
			} catch (Exception e) {
				logger.error("订单中心-短信发送记录手机号模糊查询加密失败!");
				key = getSessionkey(nick,false);
				try {
					encrypt = client.search(mobile, EncrptAndDecryptClient.PHONE,key).replace("$1$$","").replace("$", "").replace("=", "");
				} catch (Exception e1) {
					logger.error("订单中心-短信发送记录手机号模糊查询<>再次<>加密失败!");
				}
			}
		}
		
		return encrypt;
	}
	
	/**
	 * @Description:订单中心短信发送记录查询,昵称加密
	 * @Copy_author jackstraw_yu
	 */
	private String encryptSearchNick(String nick,String userId) {
		EncrptAndDecryptClient client = EncrptAndDecryptClient.getInstance(); 
		String key = getSessionkey(userId,true),encrypt=null;
		try {
			encrypt = client.search(nick, EncrptAndDecryptClient.SEARCH,key);
		} catch (Exception e) {
			logger.error("订单中心-短信发送记录用户昵称模糊查询加密失败!");
			key = getSessionkey(userId,false);
			try {
				encrypt = client.search(nick, EncrptAndDecryptClient.SEARCH,key);
			} catch (Exception e1) {
				logger.error("订单中心-短信发送记录用户昵称模糊查询<>再次<>加密失败!");
			}
		}
		
		return encrypt;
	}
	
	/**
	 * @Description:获取加密解密使用 的token
	 * @Copy_author jackstraw_yu
	 */
	 private  String getSessionkey(String userNickName,boolean flag){
		    String  token = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName);
			if(null!=token&&!"".equals(token)&&flag){
				 return token;
			}else{
				UserInfo user = userInfoService.queryUserTokenInfo(userNickName);
				if(user!=null)
					if(null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
						 cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName,user.getAccess_token());
						 return user.getAccess_token(); 
					}
			}
			return "";
	   }
}
