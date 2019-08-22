package s2jh.biz.shop.crm.member.web;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.CacheService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.vo.MemberCriteriaVo;
import s2jh.biz.shop.crm.message.entity.SmsReceiveInfo;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.vo.OrdersVo;

import com.taobao.api.SecretException;
@Controller
@RequestMapping(value = "")
public class MemberInformationController {
	
	@Autowired
	private VipMemberService vipMemberService;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	private static final Log logger = LogFactory.getLog(MemberInformationController.class);
	
	/**
	 * Gg
	 * 会员信息主页面查询(解密)
	 * @param request
	 * @param model
	 * @param pageNo
	 * @param buyerNick
	 * @param maxTradePrice
	 * @param minTradePrice
	 * @param minAvgPrice
	 * @param maxAvgPrice
	 * @param response
	 * @param minTradeNum
	 * @param maxTradeNum
	 * @param tradeStartTime
	 * @param tradeEndTime
	 * @param filtrateTime
	 * @param nowTime
	 * @param tradeStartTime1
	 * @param tradeEndTime1
	 * @param tradeStartTime2
	 * @param tradeEndTime2
	 * @return
	 * Gg
	 * @throws SecretException 
	 */
	@RequestMapping(value = "/crms/memberInformation/memberInformation")
	public String memberInformation(HttpServletRequest request,Model model,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1")Integer pageNo,
			String buyerNick,String maxTradePrice,String minTradePrice,String minAvgPrice, String maxAvgPrice,
			HttpServletResponse response,Integer minTradeNum,Integer maxTradeNum,String tradeStartTime,
			String tradeEndTime,Integer filtrateTime,String nowTime,String tradeStartTime1,String tradeEndTime1,
			String tradeStartTime2,String tradeEndTime2) throws SecretException {
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		MemberCriteriaVo memberCriteriaVoO =  new MemberCriteriaVo();
		if(buyerNick!=null){
			//条件搜索 buyerNick 加密
			String encryptionBuyerNick = EncrptAndDecryptClient.getInstance().search(buyerNick, EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId));
			memberCriteriaVoO.setBuyerName(encryptionBuyerNick);
		}
		if(userId!=null){
			memberCriteriaVoO.setUserId(userId);
		}
		if(maxTradePrice!=null && !"".equals(maxTradePrice)){
			//最大交易金额
			memberCriteriaVoO.setMaxTradePrice(Double.valueOf(maxTradePrice));
		}
		if(minTradePrice!=null && !"".equals(minTradePrice)){
			//最小交易金额
			memberCriteriaVoO.setMinTradePrice(Double.valueOf(minTradePrice));
		}
		if(minAvgPrice!=null && !"".equals(minAvgPrice)){
			//平均客单价--最小
			memberCriteriaVoO.setMinAvgPrice(Double.valueOf(minAvgPrice));
		}
		if(maxAvgPrice!=null && !"".equals(maxAvgPrice)){
			//平均客单价--最大
			memberCriteriaVoO.setMaxAvgPrice(Double.valueOf(maxAvgPrice));
		}
		if(minTradeNum!=null && !"".equals(minTradeNum)){
			//成功交易次数--最小
			memberCriteriaVoO.setMinTradeNum(minTradeNum);
		}
		if(maxTradeNum!=null && !"".equals(maxTradeNum)){
			//成功交易次数--最大
			memberCriteriaVoO.setMaxTradeNum(maxTradeNum);
		}
		if(tradeStartTime!=null && !"".equals(tradeStartTime)){
			//最后交易时间--开始
			memberCriteriaVoO.setTradeStartTime(tradeStartTime);
		}
		if(tradeEndTime!=null && !"".equals(tradeEndTime)){
			//最后交易时间--结束
			memberCriteriaVoO.setTradeEndTime(tradeEndTime);
		}
		
		//时间段查询
		Calendar calendar = Calendar.getInstance();
		//定义参数获取计算后的时间
		String countTime = "";
		nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		if(filtrateTime!=null){
			//最近3天
			if(filtrateTime==0){
				calendar.add(Calendar.DATE, -2);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				tradeStartTime2 = countTime.toString();
				tradeEndTime2 = nowTime.toString();
			}
			//最近7天
			if(filtrateTime==1){
				calendar.add(Calendar.DATE, -6);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				tradeStartTime2 = countTime.toString();
				tradeEndTime2 = nowTime.toString();
			}
			//最近10天
			if(filtrateTime==2){
				calendar.add(Calendar.DATE, -9);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				tradeStartTime2 = countTime.toString();
				tradeEndTime2 = nowTime.toString();
			}
			//最近15天
			if(filtrateTime==3){
				calendar.add(Calendar.DATE, -14);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				tradeStartTime2 = countTime.toString();
				tradeEndTime2 = nowTime.toString();
			}
			//最近1个月
			if(filtrateTime==4){
				calendar.add(Calendar.MONTH, -1);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				tradeStartTime2 = countTime.toString();
				tradeEndTime2 = nowTime.toString();
			}
			//最近半年
			if(filtrateTime==5){
				calendar.add(Calendar.MONTH, -6);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				tradeStartTime2 = countTime.toString();
				tradeEndTime2 = nowTime.toString();
			}
			//最近1年
			if(filtrateTime==6){
				calendar.add(Calendar.MONTH, -12);
				countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				tradeStartTime2 = countTime.toString();
				tradeEndTime2 = nowTime.toString();
			}
		}
		//时间段查询
		if(tradeStartTime2!=null && !"".equals(tradeStartTime2)){
			memberCriteriaVoO.setTradeStartTime(tradeStartTime2);
		}
		if(tradeEndTime2!=null && !"".equals(tradeEndTime2)){
			memberCriteriaVoO.setTradeEndTime(tradeEndTime2);
		}
		//获得工程路径
		String contextPath = request.getContextPath()+"/crms/memberInformation/memberInformation";
		Pageination<MemberDTO> pagination = vipMemberService.findMemberInfoPageList(contextPath, pageNo, userId, memberCriteriaVoO,this.getSessionkey(userId));
		MemberDTO memberDTO = null;
		if(pagination.getDatas()!=null && pagination.getDatas().size()>0){
			for(int i =0 ;i<pagination.getDatas().size();i++){
				memberDTO = pagination.getDatas().get(i);
				String encryptionBuyerNick = pagination.getDatas().get(i).getBuyerNick();
				try {
					memberDTO.setBuyerNick(securityClient.decrypt(encryptionBuyerNick, EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId)));
				} catch (Exception e) {
					memberDTO.setBuyerNick(pagination.getDatas().get(i).getBuyerNick());
					logger.error("昵称解密出错"+e.getMessage());
				}
				pagination.getDatas().set(i,memberDTO);
			}
			model.addAttribute("pagination", pagination);
		}else{
			model.addAttribute("pagination", null);
		}
		model.addAttribute("totalPage", pagination.getTotalPage());
		model.addAttribute("buyerNick", buyerNick);
		model.addAttribute("maxTradePrice", maxTradePrice);
		model.addAttribute("minTradePrice", minTradePrice);
		model.addAttribute("minAvgPrice", minAvgPrice);
		model.addAttribute("maxAvgPrice", maxAvgPrice);
		model.addAttribute("minTradeNum", minTradeNum);
		model.addAttribute("maxTradeNum", maxTradeNum);
		model.addAttribute("tradeStartTime", tradeStartTime);
		model.addAttribute("tradeEndTime", tradeEndTime);
		model.addAttribute("tradeStartTime1", tradeStartTime1);
		model.addAttribute("tradeEndTime1", tradeEndTime1);
		model.addAttribute("filtrateTime", filtrateTime);
		model.addAttribute("tradeStartTime2", tradeStartTime2);
		model.addAttribute("tradeEndTime2", tradeEndTime2);
		model.addAttribute("pageNo", pageNo);
		return "crms/memberInformation/memberInformation";
		
	}
	/**
	 * Gg
	 * 会员详情
	 * @param request
	 * @param model
	 * @param buyerNick
	 * @return
	 * Gg
	 * @throws SecretException 
	 */
	@RequestMapping(value = "/crms/memberInformation/memberInfoDetails")
	public String memberInfoDetails(HttpServletRequest request,Model model,String uId,String buyerNick) throws SecretException{
		//用户UserId
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		//通过 buyerNick 查询 uId
		if(buyerNick!=null){
			String encryptionBuyerNick = securityClient.encrypt(buyerNick, EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId));
			List<String> nicks = new ArrayList<String>();
			nicks.add(buyerNick);
			nicks.add(encryptionBuyerNick);
			uId = vipMemberService.finduId(nicks, userId);
		}
		MemberDTO memberDTO = vipMemberService.queryByMemberInfoDetails1(uId, userId);
		//计算未交易天数(当前时间-最后交易时间)
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		if(memberDTO!=null){
			if(memberDTO.getLastTradeTime()!=null){
				try {
					  //系统当前时间
				      Date d1 = df.parse(df.format(new Date()));
				      //用户最后交易时间
				      Date d2 = df.parse(df.format(memberDTO.getLastTradeTime()));  
				      long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别  
				      long days = diff / (1000 * 60 * 60 * 24);  
				      model.addAttribute("days", days);
					
				} catch (Exception e) {
					logger.info("*********谁知道啥情况！！！！！！！************"+e.getMessage());
				}
			}
			//邮箱解密
			if(memberDTO.getEmail()!=null){
				try {
					if(this.getSessionkey(userId)!=null){
						String email = securityClient.decrypt(memberDTO.getEmail(), EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId));
						model.addAttribute("email", email);
					}
				} catch (Exception e) {
					model.addAttribute("email",memberDTO.getEmail());
					logger.info("*******用户"+userId+"的会员"+memberDTO.getBuyerNick()+"邮箱解密失败，session可能过期或者为空！*****************"+e.getMessage());
				}
			}
			//手机号解密
			if(memberDTO.getPhone()!=null){
				try {
					if(this.getSessionkey(userId)!=null){
						String phone = securityClient.decrypt(memberDTO.getPhone(), EncrptAndDecryptClient.PHONE, this.getSessionkey(userId));
						model.addAttribute("phone", phone);
					}
				} catch (Exception e) {
					model.addAttribute("phone", memberDTO.getPhone());
					logger.info("******用户"+userId+"的会员"+memberDTO.getBuyerNick()+"手机号号码解密失败，session可能过期或者为空！*****"+e.getMessage());
				}
			}
			//会员真实姓名
			if(memberDTO.getReceiverName()!=null){
				try {
					if(this.getSessionkey(userId)!=null){
						String receiverName = securityClient.decrypt(memberDTO.getReceiverName(), EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId));
						model.addAttribute("receiverName", receiverName);
					}
				} catch (Exception e) {
					model.addAttribute("receiverName", memberDTO.getReceiverName());
					logger.info("*********用户"+userId+"的会员"+memberDTO.getBuyerNick()+"会员真实姓名解密失败，session可能过期或者为空！********"+e.getMessage());
				}
			}
			//用户昵称解密解密
			if(memberDTO.getBuyerNick()!=null){
				try {
					if(this.getSessionkey(userId)!=null){
						String decryptBuyerNick = securityClient.decrypt(memberDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId));
						model.addAttribute("decryptBuyerNick", decryptBuyerNick);
					}
				} catch (Exception e) {
					model.addAttribute("decryptBuyerNick", memberDTO.getBuyerNick());
					logger.info("*****用户"+userId+"的会员"+memberDTO.getBuyerNick()+"会员昵称解密失败，session可能过期或者为空！**********"+e.getMessage());
				}
			}
		}
		model.addAttribute("memberDTO", memberDTO);
		return "crms/memberInformation/memberInfoDetails";
	}
	
	/**
	 * Gg
	 * 会员信息修改(加密版本)
	 * @param buyerNick
	 * @param request
	 * @param response
	 * @param email
	 * @param sex
	 * @param birthday
	 * @param age
	 * @param wechat
	 * @param qq
	 * @param occupation
	 * Gg
	 * @throws SecretException 
	 */
	@RequestMapping(value = "/updateMemberInfoDetails",method = RequestMethod.POST)
	public void  updateMemberInfoDetails(String uId,HttpServletRequest request,HttpServletResponse response,
			String email,String sex,String birthday,Long age,String wechat,String qq,String occupation) throws SecretException{
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//加密接口
	    EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		JSONObject js = new JSONObject();
		MemberDTO memberInfo = null;
		if(uId!=null && !"".equals(uId)){
			MemberDTO memberDTO = vipMemberService.queryByMemberInfoDetails1(uId, userId);
			if(memberDTO!=null){
				memberInfo = new MemberDTO();
				memberInfo.setUserId(userId);
				memberInfo.setBuyerNick(memberDTO.getBuyerNick());
				if(email!=null && !"".equals(email)){
					//加密邮箱
					try {
						String encryptedEmail = securityClient.encrypt(email, EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId));
						memberInfo.setEmail(encryptedEmail);
					} catch (SecretException e) {
						logger.error("用户"+userId+email+"邮箱加密出错"+e.getMessage());
						memberInfo.setEmail(null);
					}
				}else{
					memberInfo.setEmail(null);
				}
				if(sex!=null && !"".equals(sex)){
					memberInfo.setSex(sex);
				}else{
					memberInfo.setSex(null);
				}
				if(birthday!=null && !"".equals(birthday)){
					memberInfo.setBirthday(birthday);
				}else{
					memberInfo.setBirthday(null);
				}
				if(age!=null && !"".equals(age)){
					memberInfo.setAge(age);
				}else{
					memberInfo.setAge(null);
				}
				if(wechat!=null && !"".equals(wechat)){
					memberInfo.setWechat(wechat);
				}
				if(qq!=null && !"".equals(qq)){
					memberInfo.setQq(qq);
				}else{
					memberInfo.setQq(null);
				}
				if(occupation!=null && !"".equals(occupation)){
					memberInfo.setOccupation(occupation);
				}else{
					memberInfo.setOccupation(null);
				}
				try {
					vipMemberService.updateMemberInfoByParam(memberInfo, 4);
					js.put("message", true);
				} catch (Exception e) {
					js.put("message", false);
				}
			}
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gg
	 * 修改备注信息
	 * type值 传3
	 * @param buyerNick
	 * @param request
	 * @param remarks
	 * @param response
	 * Gg
	 * @throws SecretException 
	 */
	@RequestMapping(value = "/updateMemberInfoRemarks",method = RequestMethod.POST)
	public void updateRemarks(String uId,HttpServletRequest request,String remarks,HttpServletResponse response) throws SecretException{
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		JSONObject js = new JSONObject();
		MemberDTO memberInfo = null;
		if(uId!=null && !"".equals(uId)){
			MemberDTO memberDTO = vipMemberService.queryByMemberInfoDetails1(uId, userId);
			if(memberDTO!=null){
				memberInfo = new MemberDTO();
				memberInfo.setUserId(userId);
				memberInfo.setBuyerNick(memberDTO.getBuyerNick());
				if(!"".equals(remarks)){
					memberInfo.setRemarks(remarks);
					memberInfo.setRemarksTime(new Date());
				}
				try {
					vipMemberService.updateMemberInfoByParam(memberInfo, 3);
					js.put("message", true);
				} catch (Exception e) {
					js.put("message", false);
				}
			}
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gg
	 * 会员订单详情
	 * @param request
	 * @param model
	 * @param buyerNick
	 * @param status
	 * @param endTimeBefore
	 * @param endTimeAfter
	 * @param pageNo
	 * @return
	 * Gg
	 * @throws SecretException 
	 */
	@RequestMapping(value = "/crms/memberInformation/memberInfoOrderDetails")
	public String memberInfoOrderDetails(HttpServletRequest request,Model model,String buyerNick,
			Integer status,String startTime,String endTime,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1")Integer pageNo) throws SecretException{
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		OrdersVo oVo = new OrdersVo();
		//订单状态
		if(status!=null && !"".equals(status)){
			if(status==0){
				oVo.setStatus("");
			}
			//等待买家付款
			if(status==1){
				oVo.setStatus("WAIT_BUYER_PAY");
			}
			//买家已付款
			if(status==2){
				oVo.setStatus("WAIT_SELLER_SEND_GOODS");
			}
			//卖家已发货
			if(status==3){
				oVo.setStatus("WAIT_BUYER_CONFIRM_GOODS");
			}
			//交易成功
			if(status==4){
				oVo.setStatus("TRADE_FINISHED");
			}
			//交易关闭
			if(status==5){
				oVo.setStatus("CLOSED");
			}
			/*if(status==5){
				oVo.setStatus("TRADE_CLOSED_BY_TAOBAO");
			}
			//退款中的订单
			if(status==6){
				oVo.setStatus("TRADE_CLOSED");
			}*/
			
		}
		//交易时间--前
		if(startTime!=null && !"".equals(startTime)){
			oVo.setStartTime(startTime);
		}
		//交易时间--后
		if(endTime!=null && !"".equals(endTime)){
			oVo.setEndTime(endTime);
		}
		//将buyerNikc解密回显
		if(buyerNick!=null && !"".equals(buyerNick)){
			String decryptBuyerNick = securityClient.decrypt(buyerNick, EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId));
			model.addAttribute("decryptBuyerNick", decryptBuyerNick);
		}
		//获得工程路径
		String contextPath = request.getContextPath()+"/crms/memberInformation/memberInfoOrderDetails";
		Pageination<TradeDTO> pagination =tradeInfoService.findByTradeDTOList(contextPath, pageNo, userId, oVo,buyerNick,this.getSessionkey(userId));
		if(pagination!=null && pagination.getDatas().size()>0){
			model.addAttribute("pagination", pagination);
		}else{
			model.addAttribute("pagination", null);
		}
		if(status!=null){
			model.addAttribute("status", status);
		}else{
			model.addAttribute("status", 0);
		}
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		if(pagination != null){
			model.addAttribute("totalPage", pagination.getTotalPage());
		}
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("buyerNick", buyerNick);
		System.out.println(buyerNick);
		return "crms/memberInformation/memberInfoOrderDetails";
	}
	/**
	 * Gg
	 * 买家回复
	 * @param request
	 * @param model
	 * @param buyerNick
	 * @return
	 * Gg
	 * @throws SecretException 
	 */
	@RequestMapping(value = "/crms/memberInformation/memberInteraction")
	public String memberInteraction(HttpServletRequest request,Model model,String buyerNick) throws SecretException{
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("buyerNick", securityClient.encrypt(buyerNick, EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId)));
		List<SmsReceiveInfo> smsReceiveInfoList = myBatisDao.findList(SmsReceiveInfo.class.getName(), "findBuyerContent", map);
		SmsReceiveInfo smsReceiveInfos =null;
		if(smsReceiveInfoList!=null && smsReceiveInfoList.size()>0){
			for (int i = 0; i < smsReceiveInfoList.size(); i++) {
				smsReceiveInfos = smsReceiveInfoList.get(i);
				String sendPhone = smsReceiveInfoList.get(i).getSendPhone();
				smsReceiveInfos.setSendPhone(securityClient.decrypt(sendPhone, EncrptAndDecryptClient.PHONE, this.getSessionkey(userId)));
				try {
					smsReceiveInfos.setContent(URLDecoder.decode(smsReceiveInfoList.get(i).getContent(), "utf-8"));
				} catch (Exception e) {
					logger.error("买家回复主页面查询出错"+e.getMessage());
				}
				smsReceiveInfoList.set(i, smsReceiveInfos);
				System.out.println(smsReceiveInfos.getSendPhone());
			}
			model.addAttribute("smsReceiveInfoList", smsReceiveInfoList);
		}else{
			model.addAttribute("smsReceiveInfoList", null);
		}
		model.addAttribute("buyerNick", buyerNick);
		return "crms/memberInformation/memberInteraction";
	}
	
	/**
	 * Gg
	 * 根据Id删除评论信息
	 * @param id
	 * @param request
	 * Gg
	 */
	@RequestMapping(value = "/deleteSmsReceiveInfo",method = RequestMethod.POST)
	public void deleteSmsReceiveInfo(Integer id,HttpServletRequest request,HttpServletResponse response){
		JSONObject js = new JSONObject();
		if(id!=null){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", id);
			try {
				myBatisDao.execute(SmsReceiveInfo.class.getName(), "deleteSmsReceiveInfo", map);
				js.put("message", true);
			} catch (Exception e) {
				logger.error("删除买家回复评论出错"+id+e.getMessage());
				js.put("message", false);
			}
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取session
	 * @param userId
	 * @return
	 */
	private String getSessionkey(String userId){
		String  session = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userId);
		if(session!=null && !"".equals(session)){
			return session;
		}else{
			UserInfo user = userInfoService.queryUserTokenInfo(userId);
			if(user!=null){
				if(null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
					cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userId,user.getAccess_token());
					return user.getAccess_token(); 
				}
			}
		}
		return null;
	}
}

