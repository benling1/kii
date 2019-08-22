package s2jh.biz.shop.crm.taobao.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.RedisLockServiceImpl;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.order.entity.TradeRates;
import s2jh.biz.shop.crm.order.service.TradeRatesService;
import s2jh.biz.shop.crm.order.service.TransactionTradeService;
import s2jh.biz.shop.crm.schedule.job.auto.AutoSellerRateSchduleJob;
import s2jh.biz.shop.crm.schedule.triggers.TriggerManager;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.taobao.info.ScheduleJobNameInfo;
import s2jh.biz.shop.crm.taobao.service.AutoSellerRateService;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.utils.ConversionTime;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.SecretException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TraderateAddRequest;
import com.taobao.api.response.TraderateAddResponse;

@Service
@Deprecated 
public class AutoSellerRateServiceImpl implements AutoSellerRateService {
	
	private Logger logger = LoggerFactory.getLogger(AutoSellerRateServiceImpl.class);
	@Autowired
	protected MyBatisDao myBatisDao;
	@Autowired
	private VipMemberService vipMemberService;
	@Autowired
	private TradeRatesService tradeRatesService;
	@Autowired
	private TradeInfoService tradeInfoService;
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	@Autowired
	private TransactionTradeService transactionTradeService;
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
//	@RequestMapping("/testAutoSeller")
	@Override
	public void judgeUserOrderRateCare(String sellerName,String tid,String oid){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId",sellerName);
		map.put("appraiseType", "0");
		List<OrderRateCareSetup> rateCareList = myBatisDao.findList(OrderRateCareSetup.class.getName(), "findRateCareByUserId", map);
		if(rateCareList!=null && rateCareList.size() > 0){
			String rateChoose = rateCareList.get(0).getRateChoose();
			Date startTime = null;
			boolean flag = false; //flag为true  则开始定时
			if(rateChoose!=null){//评价条件  立即评 延迟评  抢评
				Calendar cal = Calendar.getInstance();
				switch (rateChoose) {
				case "1":{
					flag = false;
					autoSellerRateFunction(tid,oid,sellerName);
					break;
				}
				case "2":{
					TradeDTO tradeDTO = tradeInfoService.findOneByTid(tid, sellerName);
					if(tradeDTO != null && tradeDTO.getEndTime() != null){
						startTime = new Date(tradeDTO.getEndTime());
					}
					if(startTime==null){
						flag = false;
					}else{
						cal.setTime(startTime);
						String deferRateDay = rateCareList.get(0).getDeferRateDay();
						if(deferRateDay != null && !"".equals(deferRateDay)){
							try {
								Integer addDay = Integer.parseInt(deferRateDay);
								cal.add(Calendar.DATE, addDay);
							} catch (Exception e) {
								cal.add(Calendar.DATE, 7);
							}
						}else{//默认七天
							cal.add(Calendar.DATE, 7);
						}
						startTime = cal.getTime();
						flag = true;
					}
					break;
				}	
				case "3":{
					TradeDTO tradeDTO =   tradeInfoService.findOneByTid(tid, sellerName);
					if(tradeDTO != null && tradeDTO.getEndTime() != null){
						startTime = new Date(tradeDTO.getEndTime());
					}
					if(startTime==null){
						flag = false;
					}else{
						cal.setTime(startTime);
						cal.add(Calendar.DATE, 15);
						cal.add(Calendar.HOUR, -2);
						startTime = cal.getTime();
						flag = true;
					}
					break;
				}	
				default:{
					flag = false;
					break;
				}
				}
				if(flag){ //定时评价
					Long nowLong = new Date().getTime();
					Long startLong = startTime.getTime();
					if(startLong>=nowLong){//开始时间要大于等于当前时间
						WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				        ServletContext servletContext = webApplicationContext.getServletContext();  
				        ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
						String time = ConversionTime.conversionTime(startTime);
						String jobName = ScheduleJobNameInfo.AUTO_SELLER_RATE+"_"+tid+"_"+oid + "_" + sellerName; //jobName   定时的类型_tid_oid    例子: autoRate_1212522124154
						TriggerManager.addJob(jobName,ScheduleJobNameInfo.AUTO_SELLER_RATE, time, AutoSellerRateSchduleJob.class,jobName,application);
						Scheduler scheduler = null;
						try {
							//添加并启动
							scheduler = StdSchedulerFactory.getDefaultScheduler();
							scheduler.start();
						} catch (SchedulerException e) {
							e.printStackTrace();
						}
					}
				}
			}else{
				logger.info("<$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$不是自动评价tid="+tid+"==oid="+oid+"===时间是："+format.format(new Date())+"$$$$$$$$$$$$$$$$>");
			}
		}else{
			logger.info("-------------------------------卖家：" + sellerName + "未设置自动评价");
		}
		
	}
	@Override
	public void autoSellerRateFunction(String tid,String oid,String sellerName) {
		long start = System.currentTimeMillis();
		try{
//		TradeDTO tradeDTO =   tradeInfoService.findOneByTid(tid, sellerName);
		Trade trade = transactionTradeService.queryTrade(tid);
		if(trade!=null){
			String sellerNick = trade.getSellerNick();
			String buyerNick =trade.getBuyerNick();
			try {
				buyerNick = judgeUserUtil.getDecryptData(buyerNick, EncrptAndDecryptClient.SEARCH, sellerNick, null);
			} catch (SecretException e1) {
//				if(ErrorUtil.isInvalidSession(e1)) {
//					logger.error("用户sessionKey失效");
//			        // 标记该sessionkey无效，重新授权之前不要再调用
//			    }else{
//			    	logger.error("加密解密出错啦,请直接呼叫wy");
//			    }
//				e1.printStackTrace();
				return ;
			}
			boolean flag = false; //标记，true才自动评价
			String type = "1"; //卖家要发送的评价  1 好评 2 中评 3 差评
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId",sellerNick);
			map.put("buyerName",buyerNick);
			map.put("sellerName",sellerNick);
			map.put("appraiseType", "0");
			String phone = trade.getReceiverMobile()==null?trade.getReceiverPhone():trade.getReceiverMobile();
			map.put("phone",phone);
			OrderRateCareSetup orderRateCareSetup = myBatisDao.findBy(OrderRateCareSetup.class.getName(), "findRateCareByUserId", map);
			if(orderRateCareSetup!=null){
				if("0".equals(orderRateCareSetup.getStatus())){//自动评价开启
					flag = true;
					Criteria criteria = Criteria.where("buyerNick").is(buyerNick);
					if(phone != null && !"".equals(phone)){
						criteria.and("phone").is(phone);
					}
					MemberDTO memberDTO = vipMemberService.findOne(new Query(criteria), sellerNick);
//					MemberInfo memberInfo = myBatisDao.findBy(MemberInfo.class.getName(), "findMemberByNick", map);
					String blacklistRateType = orderRateCareSetup.getBlacklistRateType();
					type = orderRateCareSetup.getResult(); //取得用户设置的发送评价类型
					if(memberDTO != null){
						String memberStatus = memberDTO.getStatus();
						if("2".equals(memberStatus)){ //用户是黑名单
							if(blacklistRateType != null && !"".equals(blacklistRateType)){
								//如果客户是黑名单进行的评价类型根据用户的设置发送短信
								flag = true;
								type = blacklistRateType;
							}else{
								flag = false;
							}
						}
					}
					String autoAddBlacklist = orderRateCareSetup.getAutoAddBlacklist(); //用户设置的中差评是多选   中评，差评
					if(autoAddBlacklist!=null){//根据中差评 自动给用户添加黑名单
						
							//查询订单评价信息
							TradeRates tradeRates =  myBatisDao.findBy(TradeRates.class.getName(), "findByOrderBuyer", oid);
							if(tradeRates!=null){
								String buyerRates = tradeRates.getResult();
								String userSetting[] = autoAddBlacklist.split(",");
								for (int i = 0; i < userSetting.length; i++) {
									String string = userSetting[i];
									if(string.equalsIgnoreCase(buyerRates)){
										//用户设置自动添加黑名单的  和用户的评价相符合 (中评 差评 neutral bad)
										if(memberDTO != null){
											vipMemberService.updateMemberInfoByParam(memberDTO, 2);
//											myBatisDao.execute(MemberInfo.class.getName(), "doUpdateMemberBackList", map);
										}
									}
								}
							}else{
								//没有评价信息，可以正常自动评价
							}
					}
					if(flag){
						if(type!=null){
							String selectType = null;
							switch (type) {
							case "good":{
								selectType = "good";
								break;
							}
							case "neutral":{
								selectType = "neutral";
								break;
							}	
							case "bad":{
								selectType = "bad";
								break;
							}	
							case "0":{ //0-不自动评价  1-好评 2-中评 3-差评
								selectType = "不评价";
								flag=false;
								break;
							}
							case "1":{//0-不自动评价  1-好评 2-中评 3-差评
								selectType = "good";
								break;
							}
							case "2":{//0-不自动评价  1-好评 2-中评 3-差评
								selectType = "neutral";
								break;
							}
							case "3":{//0-不自动评价  1-好评 2-中评 3-差评
								selectType = "bad";
								break;
							}
							default:
								break;
							}
							if(flag){
								map.put("type", selectType);
								map.put("userNick", sellerNick);
								/*String content = myBatisDao.findBy(SmsTemplate.class.getName(), "findTemplateByAutoRate", map);*/
								UserInfo user = myBatisDao.findBy(UserInfo.class.getName(), "findUserInfo", sellerNick);
								if(user!=null){
									//取得用户的session key
									String sessionKey = user.getAccess_token();
									if(sessionKey!=null){
										try {
											String content = "";//好中差评对应的内容
											if("good".equals(selectType)){
												content = orderRateCareSetup.getContent();
											}else if("neutral".equals(selectType)){
												content = orderRateCareSetup.getNeutralContent();
											}else if("bad".equals(selectType)){
												content = orderRateCareSetup.getBadContent();
											}else{
												content = orderRateCareSetup.getContent();
											}
											TraderateAddResponse rsp = autoTaobaoTraderate(tid,oid,sellerNick,content,type,sessionKey);
											
											if(rsp == null){
											}else/* if(rsp != null && rsp.getTradeRate() != null)*/{
												TradeRates tradeRates = new TradeRates();
												tradeRates.setOid(oid);
												tradeRates.setTid(tid);
												tradeRates.setContent(content);
												tradeRates.setRole("seller");
												tradeRates.setCreated(new Date());
												tradeRates.setLastModifiedDate(new Date());
												if(trade != null && trade.getBuyerNick() != null){
													if (EncrptAndDecryptClient.isEncryptData(sellerNick, EncrptAndDecryptClient.SEARCH)){
														tradeRates.setNick(sellerNick);
													}else{
														tradeRates.setNick(EncrptAndDecryptClient.getInstance().encryptData(sellerNick,EncrptAndDecryptClient.SEARCH,sessionKey));
													}
													if (EncrptAndDecryptClient.isEncryptData(trade.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
														tradeRates.setRatedNick(trade.getBuyerNick());
													}else{
														tradeRates.setRatedNick(EncrptAndDecryptClient.getInstance().encryptData(trade.getBuyerNick(),EncrptAndDecryptClient.SEARCH,sessionKey));
													}
												}
												tradeRates.setRateType("自动评价");
												tradeRates.setResult(selectType);
												tradeRates.setReply(orderRateCareSetup.getResult() != null?orderRateCareSetup.getResult() : "-");
												tradeRates.setItemTitle(trade.getTitle());
												tradeRates.setItemPrice(trade.getPrice() == null ? null : Double.parseDouble(trade.getPrice()));
												tradeRatesService.saveTradeRate(tradeRates);
											}
										} catch (Exception e) {
											e.printStackTrace();
										 }
										
									}//无sessionKey的信息，无法帮助用户评价
								}//查询不到用户信息
							}
						}
					}
				}//用户未开启自动评价
			}//用户未设置自动订单评价功能
		}else {
			logger.info("~~~~autoSellerRateFunction:在sysinfo中查出订单为null");
		}
		}finally{
			long end = System.currentTimeMillis();
			if((end-start)>5000){
				logger.debug("自动评价执行花费时间："+(end-start)+"ms,tid: "+tid);
			}
		}
	}
	/**
	 * 淘宝自动评价工具类
	 * @param tid 主订单ＩＤ
	 * @param oid　子订单ID
	 * @param sellerNick 卖家昵称
	 * @param content 评价内容
	 * @param type 评价类型  good bad  
	 * @param sessionKey 卖家的sessionKey
	 * @return 评价结果
	 * @throws ApiException
	 */
	private TraderateAddResponse autoTaobaoTraderate(String tid,String oid,String sellerNick,
			String content,String type,String sessionKey){
			TraderateAddResponse rsp = null;
		try {
			TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
			TraderateAddRequest req = new TraderateAddRequest();
			req.setTid(Long.valueOf(tid));
			req.setOid(Long.valueOf(oid));
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
}
