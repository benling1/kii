package s2jh.biz.shop.crm.shopDate.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.CacheService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.manage.entity.LogAccessDTO;
import s2jh.biz.shop.crm.manage.entity.LogType;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.LogAccessQueueService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.order.service.OrderService;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.order.service.TransactionTradeService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.utils.RequestUtil;

@Controller
@RequestMapping(value = "/")
public class TodayOrderListController {
	
	private Logger logger = LoggerFactory.getLogger(TodayOrderListController.class);
	
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	
	@RequestMapping("/crms/storeData/todyOrderList")
	public String orderListIndex(Model model){
		model.addAttribute("msg", 1);
		return "crms/storeData/todyOrderList";
	}
	
	
	
	
	
	
	/**
	* @Title: todayOrderList
	* @Description: TODO(页面跳转或表单查询时获得订单列表的信息)
	* @param  model
	* @param  timeType
	* @param  beginTime
	* @param  endTime
	* @param  buyerNick
	* @param  orderId
	* @param  status
	* @param  orderFrom
	* @param  request
	* @param  pageNo
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
//	@RequestMapping(value="/crms/todyOrderList")
//	public String todayOrderList(Model model,String timeType,String beginTime,String endTime,
//			String buyerNick,String orderId,String status,String[] orderFrom,HttpServletRequest request,
//			@RequestParam(value = "pageNo", required = false,defaultValue ="1")Integer pageNo){
//		
//		//获得userId
//		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		//String userId = "crzzyboy";
//		
//		
//		//将用户手动输入的参数去两端空格
//		if(buyerNick!=null){
//			buyerNick = buyerNick.trim();
//		}
//		if(orderId!=null){
//			orderId = orderId.trim();
//		}
//		
//		//传入参数
//		Map<String,Object> map = new HashMap<String,Object>();
//		
//		//数组除去null与""
//		List<String> list = new ArrayList<String>();
//		String of = null;
//		if(orderFrom !=null && orderFrom.length>0 ){
//			for(String s :orderFrom){
//				if(!s.equals("") && s!=null){
//					list.add(s);
//					of = of+","+s;
//				}
//			}
//		}
//		while(list.size()>0 && list.contains("WAP")){
//			list.add("WAP,WAP");
//			list.remove("WAP");
//		}
//		
//		map.put("buyerNick", buyerNick);
//		map.put("orderId", orderId);
//		map.put("status", status);
//		map.put("userId", userId);
//		if(list.size()>0){
//			map.put("orderFrom", list);
//		}else{
//			map.put("orderFrom", null);	
//		}
//		//map.put("orderFrom", orderFrom);
//		map.put("timeType", timeType);
//		map.put("beginTime", beginTime);
//		map.put("endTime", endTime);
//		
//		//获得工程路径
//		String contextPath = request.getContextPath()+"/crms/todyOrderList";
//		//调用service层查询出pagination列表
//		Pagination pagination = orderService.queryOrdersPagination(contextPath,pageNo,map);
//		
//		model.addAttribute("pagination",pagination);
//		
//		
//		//时间范围不为空
//		/*if(timeType!=null && !"".equals(timeType)){
//			//日期且存在
//			if((beginTime !=null && !"".equals(beginTime)) || (endTime!=null && !"".equals(endTime))){
//				model.addAttribute("timeType",timeType);
//				if("created".equals(timeType)){
//					model.addAttribute("timeType",1);
//				}
//				else if("payTime".equals(timeType)){
//					model.addAttribute("timeType",2);
//				}
//				else if("consignTtime".equals(timeType)){
//					model.addAttribute("timeType",3);
//				}
//				else if("modified".equals(timeType)){
//					model.addAttribute("timeType",4);
//				}
//				else if("endTime".equals(timeType)){
//					model.addAttribute("timeType",5);
//				}
//			}
//		}*/
//		model.addAttribute("timeType",timeType);
//		model.addAttribute("beginTime", beginTime);
//		model.addAttribute("endTime", endTime);
//		model.addAttribute("status", status);
//		model.addAttribute("orderId", orderId);
//		model.addAttribute("buyerNick", buyerNick);
//		model.addAttribute("orderFrom", of);		
//		return "crms/storeData/todyOrderList";
//	}
//	
	
	
	
	
	
	
	@Autowired
	private TransactionTradeService transactionTradeService;
	@RequestMapping(value="/doTest1")
	public void doTest1(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");
		
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&开始保存%%%%%%%%%%%%%%%%%%%%"+sdf.format(new Date()));
		
		//transactionOrderService.saveTradeToLocality();
		//transactionOrderService.saveTradeToLocalityEnhance();
		//transactionTradeService.saveTradeToLocalityEn();
	}
	
	
	@RequestMapping(value="/doTest2")
	public void doTest2(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");
		
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&开始保存%%%%%%%%%%%%%%%%%%%%"+sdf.format(new Date()));
		
		//transactionOrderService.saveTradeToLocality();
		//transactionOrderService.saveTradeToLocalityEnhance();
//		transactionTradeService.loadTbTradeData();
		//transactionTradeService.repairTbTradeData();
	}
	
	@Autowired
	private TransactionOrderService transactionOrderService;
	@RequestMapping(value="/doTest3")
	public void doTest3(){
		// 构建上一个小时的时间内容
		Calendar calendar = Calendar.getInstance();
		/* HOUR_OF_DAY 指示一天中的小时 */
		Date endTime = calendar.getTime();
		calendar.add(Calendar.MINUTE, -10);
		Date beginTime = calendar.getTime();
		
		// 1查询出聚石塔内数据(当前时间的上一个小时的总条数)
		final HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("beginTime", beginTime);
		hashMap.put("endTime", endTime);
		
		Long rspCount =7l;
		System.out.println("===========================================查询出"+rspCount+"条=========================================");
		
		// 每页显示的条数
		Long pageSize = 3l;
		// 获取多少页
		Long pageNum = (rspCount+pageSize)/pageSize;
		// 开始批量操作
		hashMap.put("pageSize", pageSize);
		int start = 0;
		while(start < pageNum){
			hashMap.put("startRows", start * pageSize);
			start++;
			// 开始保存或者更新Trade与Orders
			//transactionOrderService.saveOrUpdateTradeOrders(hashMap);
			System.out.println("===========================================开始分批次更新或保存订单信息=========================================");

		}

			
	}

	
	
	@RequestMapping(value="/doTest4")
	public void doTest4(){
		//模拟一个单线程  保存
		//transactionOrderService.convertTransactionOrder();
		
		

			
	}
	
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@RequestMapping(value="/doTest5")
	public void doTest5(){
		
			//定时更新会员信息以及列表
//			memberInfoService.updateMemberInfoBatch(null);
		
	}
	
	
	/**
	 * 查询mongo获取订单数据
	 */
	@RequestMapping(value="/crms/todyOrderList")
	public String todayOrderList(Model model,String timeType,String beginTime,String endTime,
			String buyerNick,String orderId,String status,String[] orderFrom,HttpServletRequest request,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1")String pageNo){
		
		//获得userId
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		String token = cacheService.getJsonStr(
//				RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE,
//				RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY
//						+ userId);
		//将用户手动输入的参数去两端空格
		if(buyerNick!=null){
			buyerNick = buyerNick.trim();
		}
//		/*买家昵称*/
//		String buyerName = null;
//		if(buyerNick!=null&&!"".equals(buyerNick)){
//			try {
//				buyerName =EncrptAndDecryptClient.getInstance().search(buyerNick, EncrptAndDecryptClient.SEARCH,token);
//			} catch (SecretException e) {
//				e.printStackTrace();
//			}
//		}
		if(orderId!=null){
			orderId = orderId.trim();
		}
		
		//校验输入的分页参数是否是数字
		Integer pageNum = 1;
		try {
			pageNum = Integer.parseInt(pageNo);
		} catch (Exception e) {
			pageNum = 1;
		}
		//数组除去null与""
		List<String> list = new ArrayList<String>();
		String of = null;
		if(orderFrom !=null && orderFrom.length>0 ){
			for(String s :orderFrom){
				if(null!=s && !"".equals(s)){
					list.add(s);
					of = of+","+s;
				}
			}
			while(list.size()>0 && list.contains("WAP")){
				list.add("WAP,WAP");
				list.remove("WAP");
			}
		}
		
		//获得工程路径
		String contextPath = request.getContextPath()+"/crms/todyOrderList";
		//调用service层查询出pagination列表
		Pageination<TradeDTO> pagination = tradeInfoService.queryTradePagination(
				contextPath, pageNum, buyerNick, orderId, status, userId, list,
				timeType, beginTime, endTime);
		//TODO
		//jacsktraw_yu
		if(pagination!=null &&  pagination.getDatas()!=null && !pagination.getDatas().isEmpty()){
			StringBuilder builder = new StringBuilder();
			for (TradeDTO dto : pagination.getDatas()) {
				builder.append(dto.getTid()).append(",");
			}
			String atiValue = RequestUtil.getAtiValue(request);
	    	String ip = RequestUtil.getRequestorIpAddress(request);
	    	if(atiValue !=null && ip !=null){
	    		String path =  request.getContextPath()+"/crms/list";
	    		LogAccessDTO log = this.packageLogAccess(userId, atiValue, ip, path, 
	    				builder.substring(0,builder.length()-1).toString(), "订单查询");
	    		this.AsyncSendOrderLog(log);
	    	}
		}
			
		
		model.addAttribute("pagination",pagination);
		model.addAttribute("timeType",timeType);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("status", status);
		model.addAttribute("orderId", orderId);
		model.addAttribute("buyerNick", buyerNick);
		model.addAttribute("orderFrom", of);
		return "crms/storeData/todyOrderList";
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
