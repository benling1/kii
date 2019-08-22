package s2jh.biz.shop.crm.order.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.RedisLockServiceImpl;
import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.manage.entity.LogAccessDTO;
import s2jh.biz.shop.crm.manage.entity.LogType;
import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.LogAccessQueueService;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.vo.SendMsgVo;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.message.service.OrderMsgSendService;
import s2jh.biz.shop.crm.message.service.SmsTemplateService;
import s2jh.biz.shop.crm.order.thread.TradeSendMessageThread;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.vo.OrdersVo;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.JsonUtil;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.RequestUtil;
import s2jh.biz.shop.utils.TradeStatusUtils;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;


@Controller
@RequestMapping("/crms")
public class OrdersController  extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(OrdersController.class);
	
	private String contextPath = "crm.kycrm.com";
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private SmsTemplateService smsTemplateService;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private OrderMsgSendService orderMsgSendService;
	
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	
	@RequestMapping("/marketingCenter/list")
	public String loadOrderIndex(Model model){
		model.addAttribute("message", 1);
		return "/crms/marketingCenter/send_group_order_mail";
	}
	
	/**
	 * 根据卖家查询条件查询列表
	 * @param request
	 * @param model
	 * @param pageNo
	 * @return
	 */
	//TODO
	//ZTK线上在用
	@SuppressWarnings({ "unused"})
	@RequestMapping("/list")
	public String findOrdersBycondition(HttpServletRequest request,Model model,Pageination<TradeDTO> page , String pageNo,
			String queryKey,HttpServletResponse response,OrdersVo oVo){
		String contextPath = request.getContextPath();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId != null && !"".equals(userId)){
			oVo.setUserId(userId);
		}else {
			return "/crms/marketingCenter/send_group_order_mail";
		}
		if(oVo.getLabel() == null || "".equals(oVo.getLabel())){
			oVo.setLabel("1");
			String lastLabel = redisLockServiceImpl.getString(userId + "_lastLabel");
			if(lastLabel != null && !"".equals(lastLabel)){
				oVo.setLabel(lastLabel);
			}
		}
		if(pageNo != null && !"".equals(pageNo)){
			if(queryKey != null && !"".equals(queryKey)){
				oVo = redisLockServiceImpl.getValue(RedisConstant.RediskeyCacheGroup.ORDER_BATCH_SEND_DATA_KEY+"-"+queryKey+"-"+userId, OrdersVo.class);
			}else {
				queryKey =  getKey();
			}
		}else {
			queryKey =  getKey();
			pageNo = "1";
		}
		String show_name = oVo.getLabel();
		//设置传递参数的页面的分页路径
		String url = contextPath + "/crms/list";
		//返回给页面show_name，设置显示
		if( show_name !=null && (show_name.equals("1") || show_name.equals("1?"))){
			url = contextPath + "/crms/list?label=1";
			model.addAttribute("show_name", 1);
		}else if( show_name !=null && (show_name.equals("2") || show_name.equals("2?"))){
			if(oVo.getStatus() != null && !"".equals(oVo.getStatus())){
			}else {
				oVo.setStatus("TOTAL");
			}
			url = contextPath + "/crms/list?label=2";
			model.addAttribute("show_name", 2);
		}else if( show_name !=null && (show_name.equals("3") || show_name.equals("3?"))){
			oVo.setStatus(TradeStatusUtils.WAIT_BUYER_PAY);
			url = contextPath + "/crms/list?label=3";
			model.addAttribute("show_name", 3);
		}else if( show_name !=null && (show_name.equals("4") || show_name.equals("4?"))){
			oVo.setStatus(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
			url = contextPath + "/crms/list?label=4";
			model.addAttribute("show_name", 4);
		}else if( show_name !=null && (show_name.equals("5") || show_name.equals("5?"))){
			oVo.setStatus(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
			url = contextPath + "/crms/list?label=5";
			model.addAttribute("show_name", 5);
		}else if( show_name !=null && (show_name.equals("6") || show_name.equals("6?"))){
			oVo.setStatus(TradeStatusUtils.TRADE_FINISHED);
			url = contextPath + "/crms/list?label=6";
			model.addAttribute("show_name", 6);
		}else if( show_name !=null && (show_name.equals("7") || show_name.equals("7?"))){
			oVo.setStatus(TradeStatusUtils.TRADE_FINISHED);
			oVo.setRateStatus("买家未评");
			url = contextPath + "/crms/list?label=7";
			model.addAttribute("show_name", 7);
		}else if( show_name !=null && (show_name.equals("8") || show_name.equals("8?"))){
			oVo.setStatus("CLOSED");
			url = contextPath + "/crms/list?label=8";
			model.addAttribute("show_name", 8);
		}
		
		page.setPageNo(Integer.parseInt(pageNo));
		page.setPageSize(10);
		try {
			if(null!=userId&&!"".equals(userId)){
				oVo.setUserId(userId);
				Pageination<TradeDTO> tadePageList = null;
				List<TradeDTO> datas =null;
				long totalCount =0l;
				OrdersVo  orderParam = createTradeQueryParam(oVo);
				tadePageList = 	tradeInfoService.findTradePageList(page,orderParam,userId);
				if(tadePageList!=null){
				    datas = tadePageList.getDatas();
				    EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
				    if(datas != null && !datas.isEmpty() && datas.size() > 0){
				    	String sessionKey = userInfoService.validateFindSessionKey(userId);
				    	List<String> tids = new ArrayList<String>();//TODO jackstraw_yu添加
				    	for (TradeDTO tradeDTO : datas) {
							if(tradeDTO != null && tradeDTO.getBuyerNick() != null && !"".equals(tradeDTO.getBuyerNick())){
								if(EncrptAndDecryptClient.isEncryptData(tradeDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
									tradeDTO.setBuyerNick(decryptClient.decryptData(tradeDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey));
								}
							}
							if(tradeDTO != null && tradeDTO.getReceiverMobile() != null && !"".equals(tradeDTO.getReceiverMobile())){
								if(EncrptAndDecryptClient.isEncryptData(tradeDTO.getReceiverMobile(), EncrptAndDecryptClient.PHONE)){
									tradeDTO.setReceiverMobile(decryptClient.decryptData(tradeDTO.getReceiverMobile(), EncrptAndDecryptClient.PHONE, sessionKey));
								}
							}
							tids.add(tradeDTO.getTid());//TODO jackstraw_yu添加
						}
				    	//TODO jackstraw_yu添加
				    	String atiValue = RequestUtil.getAtiValue(request);
				    	String ip = RequestUtil.getRequestorIpAddress(request);
				    	if(atiValue !=null && ip !=null){
				    		//String path =  request.getContextPath()+"/crms/list";
				    		String path =  this.contextPath+"/crms/list";
				    		LogAccessDTO log = this.packageLogAccess(userId, atiValue, ip, path, 
				    								StringUtils.join(tids, ","), "订单查询");
				    		this.AsyncSendOrderLog(log);
				    	}
				    }
				    totalCount = tadePageList.getTotalCount();
				    oVo.setTotalCount(totalCount);
				}else{
				    datas = new ArrayList<TradeDTO>();
				    totalCount = 0l;
				}
				//搜索条件保存时间为1小时
				redisLockServiceImpl.putStringValueWithExpireTime(RedisConstant.RediskeyCacheGroup.ORDER_BATCH_SEND_DATA_KEY+"-"+queryKey+"-"+userId,JsonUtil.toJson(orderParam), TimeUnit.HOURS, 1L);
				//保存上一次搜索的标签1-8
//				redisLockServiceImpl.putStringValueWithExpireTime(userId + "_lastLabel", show_name, TimeUnit.MINUTES, 10L);
				redisLockServiceImpl.putString(userId + "_lastLabel", show_name);
				Pagination pagination = new Pagination(Integer.parseInt(pageNo), 10, (int)totalCount, datas);
				pagination.pageView(url, "&queryKey="+queryKey);
				model.addAttribute("totalCount", totalCount);
				model.addAttribute("oVo", oVo);
				model.addAttribute("queryKey", queryKey);
				model.addAttribute("datas", datas);
				model.addAttribute("pagination", pagination);
			}else{
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/crms/marketingCenter/send_group_order_mail";
	}
	
	/**
	 * 封装页面参数
	 * @author zlp
	 * @param oVo
	 */
	private OrdersVo createTradeQueryParam(OrdersVo oVo) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//赋值商品id
		if(null!=oVo.getNumIidStr()&&!"".equals(oVo.getNumIidStr())){
			List<Long> numIidList = new ArrayList<Long>();
			String[] numIids = oVo.getNumIidStr().split(",");
			for (int i = 0; i < numIids.length; i++) {
				numIidList.add(Long.parseLong(numIids[i]));
			}
			oVo.setNumIidList(numIidList);
	    }
		if(oVo.getEndTimeAfter() != null && !"".equals(oVo.getEndTimeAfter())){
			oVo.setDateEndTimeAfter(DateUtils.convertDate(oVo.getEndTimeAfter()));
		}
		if(oVo.getEndTimeBefore() != null && !"".equals(oVo.getEndTimeBefore())){
			oVo.setDateEndTimeBefor(DateUtils.convertDate(oVo.getEndTimeBefore()));
		}
		if("TOTAL".equals(oVo.getStatus())){
		}else {
			if("1".equals(oVo.getLabel())){
				if(oVo.getStartOrderDate() == null || "".equals(oVo.getStartOrderDate())){
					oVo.setStartOrderDate(DateUtils.getTimeByMoth(format.format(new Date()), -3));
				}
			}
		}
		if(oVo.getEndOrderDate() == null || "".equals(oVo.getEndOrderDate())){
			oVo.setEndOrderDate(format.format(new Date()));	
		}
		if("ALL".equals(oVo.getStatus()) || "TOTAL".equals(oVo.getStatus())){
			oVo.setStatus(null);
		}
		if("ALL".equals(oVo.getOrderFrom())){
			oVo.setOrderFrom(null);
		}
		if("ALL".equals(oVo.getRefundStatus())){
			oVo.setRefundStatus(null);
		}
		if("全部".equals(oVo.getRateStatus())){
			oVo.setRateStatus(null);
		}
		return oVo;
	}

	/**
	 * 何磊的方法
	 * 2017年4月8日上午10:15:33添加注释
	 *///
	@RequestMapping("/marketingCenter/dataScreenlist")
	@ResponseBody
	//TODO
	//HL  线上不用
	public Map<String,Object> findListByQuery(
									String startOrderDate,
									String endOrderDate,
									String status,
									HttpServletRequest request,
									Model model,
									HttpServletResponse response) throws IOException{
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		List<OrdersDTO> list = tradeInfoService.findOrdersByQuery(startOrderDate,endOrderDate,status,userId);
		//创建对象封装参数,输出到前台
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", list);
		return resultMap;
	}
	
	/**
	 * 根据类型选择短信模板
	 * ZTK2017年1月18日上午9:58:57
	 */
	@RequestMapping("/orderCenter/smsTemplate")
	@ResponseBody
	public Map<String,Object> findSmsTemplate(String type,HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> queryMap = new HashMap<String, Object>();
		//String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		
		/*String userId = (String) request.getSession().getAttribute("taobao_user_nick");*/
		Map<String,Object> resultMap = new HashMap<String, Object>();
		queryMap.put("type", type);
		List<SmsTemplate> smsTemplates = smsTemplateService.findAllSmsTemplates(queryMap);
		resultMap.put("smsTemplates", smsTemplates);
		return resultMap;
	}
	
	
	/**
	 * 查找时间满足过滤条件的订单集合
	 * ZTK2017年2月25日下午5:41:26
	 */
	public List<String> findSendRecordList(String userId,Date bTime,Date eTime){
		List<String> orderList = new ArrayList<String>();
		List<SmsRecordDTO> orderLastsendTimeList = smsRecordService.findOrderLastsendTime(userId, bTime, eTime);
		//使用list集合接受符合短信过滤条件的订单id
		for (SmsRecordDTO smsSendRecord : orderLastsendTimeList) {
			orderList.add(smsSendRecord.getOrderId());
		}
		if(orderList == null || orderList.size() == 0){
			orderList = null;
		}
		return orderList;
	}
	
	
	/**
	 * 订单短信群发-根据订单id和卖家查询短信发送记录
	 * ZTK2017年3月7日下午3:48:14
	 */
	@RequestMapping("/findSendRecordByOid")
	@ResponseBody
	public Map<String,Object> findSendRecordByOid(HttpServletRequest request,HttpServletResponse response,String tradeTid){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		List<SmsRecordDTO> sendRecordList = smsRecordService.findSendRecordByOid(userId, tradeTid);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if(sendRecordList != null && sendRecordList.size() > 0){
			resultMap.put("flag",true);
			resultMap.put("sendRecordList", sendRecordList);
		}else{
			resultMap.put("flag",false);
			resultMap.put("message", "暂时没有发送记录!!!");
		}
		return resultMap;
	}
	
	/**
	 * 订单短信群发中选择商品id调用的方法
	 * ZTK2017年3月8日上午10:55:12
	 */
	@RequestMapping("/orderCenter/findItemList")
	@ResponseBody
	public Map<String,Object> findAllItem(String name,String pageIndex,String pageSize,
			HttpServletRequest request){
		String userNick = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<Item> itemList = itemService.findItemByCondition(userNick, name, Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
		int itemCount = itemService.findItemByConditionCount(userNick, name);
		if(itemList != null && itemList.size() > 0){
			resultMap.put("itemList", itemList);
			resultMap.put("itemCount", itemCount);
			resultMap.put("flag", true);
		}else{
			resultMap.put("message", "没有对应商品！！！");
			resultMap.put("itemCount", itemCount);
		}
		return resultMap;
	}
	
	
	

	/** 
	* @Title: validateUserOpterate 
	* @Description:  (订单短信群发:验证用户的一些选择上的操作) 
	* @date 2017年5月23日 上午9:25:45
	* @author jackstraw_yu    
	* @throws 
	*/
	private Map<String,Object> validateUserOpterate(SendMsgVo sendMsgVo){
		Map<String,Object> map = new HashMap<String,Object>();
		boolean flag = true;
		if (sendMsgVo.getContent() == null || "".equals(sendMsgVo.getContent())) {
			map.put("message", "短信内容不能为空!");
			flag = false;
		}  
		if (sendMsgVo.getSignVal() == null || "".equals(sendMsgVo.getSignVal())) {
			String signValRe = sendMsgVo.getSignVal().replace("【", "").replace("】", "").replace(" ", "");
			if(signValRe == null || "".equals(signValRe)){
				map.put("message", "短信签名不能为空或者空格");
				flag = false;
		}
		} 
		if (sendMsgVo.getSend_time_type() == null || "".equals(sendMsgVo.getSend_time_type())) {
			map.put("message", "请选择立即发送或者定时发送!");
			flag = false;
		} 
		if ("2".equals(sendMsgVo.getSend_time_type()) && (sendMsgVo.getSendTime() == null || "".equals(sendMsgVo.getSendTime()))) {
			map.put("message", "定时发送的时间不能为空!");
			flag = false;
		} 
		if("2".equals(sendMsgVo.getSendType()) && sendMsgVo.getSendTime() != null && !"".equals(sendMsgVo.getSendTime())){
			Date sTime =null;
			try {
				sTime = lab.s2jh.core.util.DateUtils.parseDate(sendMsgVo.getSendTime(), "yyyy-MM-dd HH:mm");
				if(sTime!=null && (sTime.getTime()-System.currentTimeMillis())<30*60*1000){
					map.put("message", "定时发送时间与当前时间间隔不能小于30分钟!");
					flag = false;
				}
			} catch (Exception e) {
				logger.error("订单短信群发定时发送时间转换异常!!^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
				map.put("message", "时间转换异常,请重新操作或者联系系统管理员!");
				flag = false;
			}
		}
		map.put("flag", flag);
		return map;
	}
	
	/** 
	* @Title: validateMessageCount 
	* @Description:  (订单短信群发:验证用户短信条数与用户余额) 
	* @date 2017年5月23日 上午9:36:27
	* @author jackstraw_yu    
	* @throws 
	*/
	private Map<String,Object> validateMessageCount(SendMsgVo sendMsgVo) {
		Map<String,Object> map = new HashMap<String,Object>();
		boolean flag = true;
		//订单短信剩余条数
		long smsNum = userAccountService.findUserAccountSms(sendMsgVo.getUserId());
		//订单集合长度*每条短信大致要扣除的条数,即为要发送的短信的总条数
		int length = 1;
		if(sendMsgVo.getContent().length()>70){
			length = (sendMsgVo.getContent().length()+66)/67;
		}
		if(0 == smsNum){
			map.put("message", "短信剩余条数不足,请先充值后再发送,谢谢!");
			flag = false;
		}else if(smsNum<sendMsgVo.getTotalCount()*length){
			map.put("message", "短信剩余条数小于要发送的总条数!");
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}
	
	/**
	 * 订单短信群发时，全部发送
	 * ZTK2017年5月17日上午9:59:17
	 * @throws InterruptedException 
	 */
	@RequestMapping("/groupSms")
	@ResponseBody
	//TODO
	//ZTK 会员短信群发-->写在service层
	public Map<String, Object> groupSendMessage( SendMsgVo  sendMsgVo ,
			HttpServletRequest request,HttpServletResponse response) throws InterruptedException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("skip", 1);
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
	 	sendMsgVo.setIpAddress(getIpAddress.getIpAddress(request));
	 	sendMsgVo.setMsgType(MsgType.MSG_DDDXQF);
		
	 	if(null!=userId&&!"".equals(userId)){
			sendMsgVo.setUserId(userId);
			OrdersVo ordersVo = redisLockServiceImpl.getValue(RedisConstant.RediskeyCacheGroup.ORDER_BATCH_SEND_DATA_KEY+"-"+sendMsgVo.getQueryKey()+"-"+userId, OrdersVo.class);
			if(sendMsgVo.getTotalCount()==null ||ordersVo==null
					||sendMsgVo.getTotalCount()!= ordersVo.getTotalCount()){
				resultMap.put("msg", "操作失败,您的搜索被重置,请重新操作!");
				resultMap.put("status", false);
				resultMap.put("skip", 0);
				return resultMap;
//			return rsMap(100, "操作失败,您的搜索被重置,请重新操作!").put("status", false).toJson();
			}
			if(sendMsgVo.getActivityName()!=null&&!"".equals(sendMsgVo.getActivityName()))
				sendMsgVo.setActivityName(sendMsgVo.getActivityName().trim()); 
			
			logger.info("订单短信群发开始,当前时间:"+new Date()+"^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
			Map<String, Object> oMap = validateUserOpterate(sendMsgVo);
			if ((Boolean)oMap.get("flag") == false) {
				resultMap.put("msg", oMap.get("message")==null?"操作失败,请重新操作或联系系统管理员!":(String)oMap.get("message"));
				resultMap.put("status", false);
				resultMap.put("skip", 0);
				return resultMap;
//				return rsMap(100, oMap.get("message")==null?"操作失败,请重新操作或联系系统管理员!":(String)oMap.get("message")).put("status", false).toJson();
			}
			//验证余额
			Map<String, Object> mMap = validateMessageCount( sendMsgVo);
			if ((Boolean)mMap.get("flag") == false) {
				resultMap.put("msg", mMap.get("message")==null?"操作失败,请重新操作或联系系统管理员!":(String)mMap.get("message"));
				resultMap.put("status", false);
				resultMap.put("skip", 0);
				return resultMap;
//				return rsMap(100, mMap.get("message")==null?"操作失败,请重新操作或联系系统管理员!":(String)mMap.get("message")).put("status", false).toJson();
			}
			if ("1".equals(sendMsgVo.getSend_time_type())) {
				sendMsgVo.setSchedule(false);
			}else{
				sendMsgVo.setSchedule(true);
			}
			try {
				
				//TODO jacsktraw_yu
				String atiValue = RequestUtil.getAtiValue(request);
		    	String ip = RequestUtil.getRequestorIpAddress(request);
		    	LogAccessDTO log =null;
		    	if(atiValue !=null && ip !=null){
		    		//String path =  request.getContextPath()+"/crms/list";
		    		String path =  this.contextPath+"/crms/list";
		    		log = this.packageLogAccess(userId, atiValue, ip, path,null, "订单查询");
		    	}
				TradeSendMessageThread tradeSendMessageThread = new TradeSendMessageThread(orderMsgSendService, sendMsgVo,log);
				new Thread(tradeSendMessageThread).start();
//				orderMsgSendService.batchSendOrderMsg(sendMsgVo);
				resultMap.put("msg", sendMsgVo.getSchedule()?"定时保存成功!":"订单短信发送成功!");
				logger.info(sendMsgVo.getSchedule()?"定时保存成功!":"订单短信发送成功!");
				resultMap.put("status", true);
				resultMap.put("key", sendMsgVo.getSchedule()?"schedule":"");
				return resultMap;
//				return rsMap(100, sendMsgVo.getSchedule()?"定时保存成功!":"订单短信发送成功!").put("status", true).put("key", sendMsgVo.getSchedule()?"schedule":"").toJson();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(sendMsgVo.getSchedule()?"订单短信群发定时保存异常!!":"订单短信群发立即发送异常!!"+"^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
				resultMap.put("msg", sendMsgVo.getSchedule()?"定时保存失败,请重新操作或者联系系统管理员!":"订单短信发送失败,请重新操作或者联系系统管理员!");
				resultMap.put("status", false);
				resultMap.put("skip", 0);
				return resultMap;
//				return rsMap(100, sendMsgVo.getSchedule()?"定时保存失败,请重新操作或者联系系统管理员!":"订单短信发送失败,请重新操作或者联系系统管理员!").put("status", false).toJson();
			}
		}else{
			resultMap.put("msg", "抱歉您的登录已经超时,请重新进入该系统再进行操作,谢谢!");
			resultMap.put("status", false);
			resultMap.put("skip", 0);
			return resultMap;
//			return rsMap(100, "抱歉您的登录已经超时,请重新进入该系统再进行操作,谢谢!").put("status", false).toJson();
		}
	}
	
	/**
	 * @Title: getKey 
	 * @Description: (每次搜索订单 生成一个key) 
	 * @date 2017年5月17日 下午3:17:45 
	 * @author jackstraw_yu @throws
	 */
	private String getKey() {
		long id = System.currentTimeMillis();
		String key = id + "-";
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			key += random.nextInt(10);
		}
		return key;
	}
	
//=======================================添加日志上传method========================
	
	
	
	
	/** 
	* @Description 打包日志对象
	* @param  userId
	* @param  ati
	* @param  ip
	* @param  url
	* @param  tids
	* @param  operation
	* @return LogAccessDTO    返回类型 
	* @author jackstraw_yu
	* @date 2018年2月2日 下午5:43:26
	*/
	private LogAccessDTO packageLogAccess(String userId,String ati,String ip,
										 	String url,String tids,String operation){
		LogAccessDTO log = new LogAccessDTO();
		log.setUserId(userId);
		log.setAti(ati);
		log.setUserIp(ip);
		log.setUrl(url);
		log.setTradeIds(tids);
		log.setOperation(operation);
		log.setTime(String.valueOf(System.currentTimeMillis()));
		return log;
	}
	
	
	/** 
	* @Description 上传订单日志 
	* @param  userId    设定文件 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2018年2月2日 下午5:22:43
	*/
	private void AsyncSendOrderLog(final LogAccessDTO log){
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
			@Override
			public void run() {
				try {
					 Map<String, Object> map = new HashMap<String, Object>();
					 map.put(LogAccessDTO.class.getName(), log);
					 map.put(LogType.class.getName(), LogType.ORDER_TYPE);
					 LogAccessQueueService.queue.put(map);
				} catch (Exception e) {
					logger.error("##################### AsyncSendOrderLog() Exception:"+e.getMessage());
				}
			}
		});
	}
	
	
	
	
	
	
}