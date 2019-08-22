package s2jh.biz.shop.crm.tradecenter.web;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.goods.entity.CommodityGrouping;
import s2jh.biz.shop.crm.goods.service.CommodityGroupingService;
import s2jh.biz.shop.crm.goods.service.GroupedGoodsService;
import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.crm.item.vo.ItemVO;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.manage.base.BaseComponent.ResultMap;
import s2jh.biz.shop.crm.message.web.MsgSendController;
import s2jh.biz.shop.crm.order.util.ResourceGetValueUtil;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.GetUserCode;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;
import s2jh.biz.shop.crm.tradecenter.service.TradeSetupService;
import s2jh.biz.shop.crm.tradecenter.vo.TradeSetupVO;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.view.util.GetCurrentPageUtil;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.JsonUtil;
import s2jh.biz.shop.utils.MyBeanUtils;
import s2jh.biz.shop.utils.phoneRegExp.PhoneRegUtils;

/**	
* @Title: TradSetController
* @Description: (订单中心,各种设置)
* @author:jackstraw_yu
*/
@SuppressWarnings("unused")
@Controller
@RequestMapping(value="/tradeSetup")
public class TradeSetupController extends BaseController {
	
	@Autowired
	private TradeSetupService tradeSetupService;
	
	@Autowired
	private UserInfoService userInfoService; 
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CommodityGroupingService commodityGroupingService;
	
	@Autowired
	private GroupedGoodsService groupedGoodsService;

	private static final Log logger = LogFactory.getLog(TradeSetupController.class);
	//解析时间用
	private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	//正则校验时间用
	private final Pattern regexTime = Pattern.compile("(([01][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]");  
	
	
	/**
	 * 数据集合
	 * TradeSetupController实例化时创建
	 * 订单中心设置类型-名称
	 * */
	private final Map<String,String> DICT_MAP = new HashMap<String,String>(){   
		private static final long serialVersionUID = 4467723566485282986L;
		{  
          /* put("1", "下单关怀");put("2", "常规催付");put("3", "二次催付");put("4", "聚划算催付");
           put("5", "预售催付");put("6", "发货提醒");put("7", "到达同城提醒");put("8", "派件提醒");  
           put("9", "签收提醒");put("10", "疑难件提醒");put("11", "延时发货提醒");
           put("12", "宝贝关怀");put("13", "付款关怀");put("14", "回款提醒");
           put("16", "自动评价");put("20", "中差评监控");put("21", "中差评安抚");
           put("29", "买家申请退款"); put("30", "退款成功");put("31", "等待退货"); put("32", "拒绝退款");
           put("37", "好评提醒");*/
		   /*  put(OrderSettingInfo.ADVANCE_PUSH_PAYMENT, "预售催付");*//*put(OrderSettingInfo.ABNORMAL_GOODS_REMIND, "疑难件提醒");  新版没有该功能*/
		   put(OrderSettingInfo.CREATE_ORDER, "下单关怀");put(OrderSettingInfo.FIRST_PUSH_PAYMENT, "常规催付");
		   put(OrderSettingInfo.SECOND_PUSH_PAYMENT, "二次催付");put(OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT, "聚划算催付");
		   put(OrderSettingInfo.SHIPMENT_TO_REMIND, "发货提醒");put(OrderSettingInfo.ARRIVAL_LOCAL_REMIND, "到达同城提醒");
		   put(OrderSettingInfo.SEND_GOODS_REMIND, "派件提醒");put( OrderSettingInfo.REMIND_SIGNFOR, "签收提醒");
           put(OrderSettingInfo.DELAY_SEND_REMIND, "延时发货提醒");put(OrderSettingInfo.COWRY_CARE, "宝贝关怀");
           put(OrderSettingInfo.PAYMENT_CINCERN, "付款关怀");put(OrderSettingInfo.RETURNED_PAYEMNT, "回款提醒"); 
           put(OrderSettingInfo.AUTO_RATE, "自动评价");put(OrderSettingInfo.APPRAISE_MONITORING_ORDER, "中差评监控");
           put(OrderSettingInfo.APPRAISE_PACIFY_ORDER, "中差评安抚");put(OrderSettingInfo.REFUND_CREATED, "买家申请退款");
           put(OrderSettingInfo.REFUND_SUCCESS, "退款成功");put(OrderSettingInfo.REFUND_AGREE, "同意退款");
           put(OrderSettingInfo.REFUND_REFUSE, "拒绝退款");put(OrderSettingInfo.GOOD_VALUTION_REMIND, "提醒好评");
        }  
    };
    /**
	 * 数据集合
	 * TradeSetupController实例化时创建
	 * 订单发送范围校验
	 * */
    private final List<String> BLOCK_LIST = new ArrayList<String>(){   
		private static final long serialVersionUID = -237244155080108106L;
		{  
			//"付款关怀", "发货提醒"
			add(OrderSettingInfo.PAYMENT_CINCERN);add(OrderSettingInfo.SHIPMENT_TO_REMIND);
			// "延时发货提醒", "到达同城提醒"
			add(OrderSettingInfo.DELAY_SEND_REMIND); add(OrderSettingInfo.ARRIVAL_LOCAL_REMIND);
			//"派件提醒", "签收提醒"
			add(OrderSettingInfo.SEND_GOODS_REMIND);add( OrderSettingInfo.REMIND_SIGNFOR);
			//"宝贝关怀", "回款提醒"
			add(OrderSettingInfo.COWRY_CARE);add(OrderSettingInfo.RETURNED_PAYEMNT); 
			//"买家申请退款", "同意退款"
			add(OrderSettingInfo.REFUND_CREATED);add(OrderSettingInfo.REFUND_AGREE);
			//"拒绝退款", "退款成功"
			add(OrderSettingInfo.REFUND_REFUSE); add(OrderSettingInfo.REFUND_SUCCESS);
			//"提醒好评"
			add(OrderSettingInfo.GOOD_VALUTION_REMIND);
        } 
    };
    
    /**
     * 短信内容变量与其长度
     * */
    private final Map<String,Integer> PARAM_MAP = new HashMap<String,Integer>(){   
		private static final long serialVersionUID = -5426703862520846913L;
		{  
			/**
			 * 	{买家昵称}:8;{买家姓名}:4;{下单时间}:19;
			 *	{订单编号}:17;{订单金额}:7;{付款链接}:17;
			 *	{物流公司名称}:4;{运单号}:13;{物流链接}:17;
			 *	{到达城市}:5;{确认收货链接}:17;{退款链接}:17;
			 *	{评价链接}:17;
			 * */
           put("{买家昵称}",8); put("{买家姓名}",4); put("{下单时间}",19);
           put("{订单编号}",17); put("{订单金额}",7); put("{付款链接}",17);
           put("{物流公司名称}",4); put("{运单号}",13); put("{物流链接}",17);
           put("{到达城市}",5); put("{确认收货链接}",17); put("{退款链接}",17);
           put("{评价链接}",17);
        }  
    };  
    
    /**
	 * 数据集合;单任务集合
	 * */
    private  final List<String>  SINGLE_LIST = new ArrayList<String>(){   
		private static final long serialVersionUID = -5426703862520846913L;
		{  
			add(OrderSettingInfo.SECOND_PUSH_PAYMENT);//二次催付
			add(OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT);//聚划算催付
			add(OrderSettingInfo.REFUND_CREATED);//买家申请退款
			add(OrderSettingInfo.REFUND_AGREE);//同意退款
			add(OrderSettingInfo.REFUND_REFUSE);//拒绝退款
			add(OrderSettingInfo.REFUND_SUCCESS);//"退款成功
			add(OrderSettingInfo.AUTO_RATE); //自动评价
			add(OrderSettingInfo.APPRAISE_MONITORING_ORDER);//中差评监控
			add(OrderSettingInfo.APPRAISE_PACIFY_ORDER);//中差评安抚
			add(OrderSettingInfo.GOOD_VALUTION_REMIND);//提醒好评
        }  
    };  
    /**
   	 * 数据集合;多任务集合 
   	 * */
   private  final List<String> MULTI_LIST = new ArrayList<String>(){   
	   private static final long serialVersionUID = -7237592889901587410L;
	   {  
   			add(OrderSettingInfo.CREATE_ORDER);//下单关怀
   			add(OrderSettingInfo.FIRST_PUSH_PAYMENT);//常规催付
            add(OrderSettingInfo.SHIPMENT_TO_REMIND);//发货提醒
   			add(OrderSettingInfo.REMIND_SIGNFOR);//签收提醒
            add(OrderSettingInfo.ARRIVAL_LOCAL_REMIND);//到达同城提醒
   			add(OrderSettingInfo.SEND_GOODS_REMIND);//派件提醒
            add(OrderSettingInfo.DELAY_SEND_REMIND);//延时发货提醒
   			add(OrderSettingInfo.COWRY_CARE);//宝贝关怀
            add(OrderSettingInfo.PAYMENT_CINCERN);//付款关怀
   			add(OrderSettingInfo.RETURNED_PAYEMNT);//回款提醒
   	   }  
   };  
   /**
  	 * 数据集合;需要选择发送时间
  	 * */
   private  final List<String> REMIND_LIST = new ArrayList<String>(){   
 	   private static final long serialVersionUID = -7237592889901587410L;
 	   {  
 			add(OrderSettingInfo.SECOND_PUSH_PAYMENT);//二次催付
 			add(OrderSettingInfo.PREFERENTIAL_PUSH_PAYMENT);//聚划算催付
 			add(OrderSettingInfo.AUTO_RATE); //自动评价
 			add(OrderSettingInfo.APPRAISE_MONITORING_ORDER);//中差评监控
 			add(OrderSettingInfo.GOOD_VALUTION_REMIND);//好评提醒
    		add(OrderSettingInfo.CREATE_ORDER);//下单关怀
    		add(OrderSettingInfo.FIRST_PUSH_PAYMENT);//常规催付
            add(OrderSettingInfo.DELAY_SEND_REMIND);//延时发货提醒
            add(OrderSettingInfo.COWRY_CARE);//宝贝关怀
    		add(OrderSettingInfo.RETURNED_PAYEMNT);//回款提醒
 	  }
    };
    

	
	/**
	* @Title: showSetupMenu
	* @Description: (展示各种订单中心的设置是否开启或者关闭)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/showSetupMenu",/*method=RequestMethod.POST,*/produces="text/html;charset=UTF-8")
	public @ResponseBody
	String showSetupMenu(Model model,HttpServletRequest request){ 
		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String,Boolean> map =null;
		try {
			//整个用户的订单中心个各种设置在biz层进行判断
			//true:有一个开启;false:未设置或者位全部开启
			map = tradeSetupService.showSetupMenu(userId,new HashSet<String>(DICT_MAP.keySet()));
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.showSetupMenu() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).put("data",map).toJson();
	}
	
	
	/**
	* @Title: showShopName
	* @Description: (订单中心显示店铺名称?签名)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/showShopName",/*method=RequestMethod.POST,*/produces="text/html;charset=UTF-8")
	public @ResponseBody
	String showShopName(Model model,HttpServletRequest request){ 
		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
		String shopName = null;
		try {
			shopName = queryShopName(userId);
			if(shopName==null)
				return rsMap(101, "操作失败").put("status", false).toJson();
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.showSetupMenu() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).put("data",shopName).toJson();
	}
	
	/**
	* @Title: showSingleTypeMenu
	* @Description: (展示单个类型的订单中心的设置是否开启或者关闭)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	//@RequestMapping(value="/showSingleTypeMenu",/*method=RequestMethod.POST,*/produces="text/html;charset=UTF-8")
//	private /*@ResponseBody*/
//	String showSingleTypeMenu(Model model,HttpServletRequest request,@RequestBody String params){ 
//		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
//		TradeSetupVo setup = null;
//		try {
//			setup = parseJsonToObj(params);
//		} catch (Exception e) {
//			logger.error("##################### TradeSetupController.parseJsonToObj() Exception:"+e.getMessage());
//			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
//		}
//		if(setup==null){
//			return  rsMap(101, "操作失败").put("status", false).toJson();
//		}
//		setup.setUserId(userId);
//		if(setup.getType()==null || "".equals(setup.getType())){
//			return  rsMap(101, "操作失败").put("status", false).toJson();
//		}else{
//			Boolean data = null;
//			try {
//				//查询
//				data = tradeSetupService.showSingleTypeMenu(setup);
//			} catch (Exception e) {
//				logger.error("##################### TradeSetupController.showSingleTypeMenu() Exception:"+e.getMessage());
//				return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
//			}
//			return rsMap(100, "操作成功").put("status", true).put("data",data).toJson();
//		}
//	}
	
	
	/**
	* @Title: saveTradeSetup
	* @Description: (保存各种订单中心的设置)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/saveTradeSetup",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String saveTradeSetup(Model model,HttpServletRequest request,@RequestBody String params){ 
		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
		TradeSetupVO setup = null;
		try {
			setup = parseJsonToObj(params);
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.parseJsonToObj() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(setup==null)
			return  rsMap(101, "操作失败").put("status", false).toJson();
		setup.setUserId(userId);
		//TODO
		try {
			//1校验用户的设置
			Map<String, Object> map = checkTradeSetup(setup,true);
			if(!(boolean)map.get("flag")){
				return  rsMap(101, "操作失败").put("status", false).put("information",map.get("message")).toJson();
			}else{
				TradeSetup tradeSetup = null,tradeSetupVo = null;
				//2加工数据
				tradeSetup = handleSaveSetupData(setup,new TradeSetup());
				//多任务时需设置级别
				if(MULTI_LIST.contains(tradeSetup.getType())){
					Integer level = tradeSetupService.queryMaxTaskLevelByType(tradeSetup);
					tradeSetup.setTaskLevel(level==null?1:++level);
				}
				//3保存??是否返回实体
				tradeSetupVo = tradeSetupService.saveTradeSetup(tradeSetup);
				addUserPermitByMySql(tradeSetup.getUserId());
				return rsMap(100, "操作成功").put("status", true).put("data",tradeSetupVo).toJson();
			}
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.saveTradeSetup() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
	}
	
	
	/**
	* @Title: queryTradeSetupTable
	* @Description: (查询订单中心设置多任务列表)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/queryTradeSetupTable",/*method=RequestMethod.POST,*/produces="text/html;charset=UTF-8")
	public @ResponseBody
	String queryTradeSetupTable(Model model,HttpServletRequest request,@RequestBody String params){ 
		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
		TradeSetupVO setup = null;
		try {
			setup = parseJsonToObj(params);
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.parseJsonToObj() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(setup==null)
			return  rsMap(101, "操作失败").put("status", false).toJson();
		setup.setUserId(userId);
		//搜索参数:任务名称,任务状态;类型,用户名称
		if(setup.getType()==null || "".equals(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(!MULTI_LIST.contains(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else{
			List<TradeSetup> data = null;
			try {
				//查询
				data = tradeSetupService.queryTradeSetupTable(setup);
			} catch (Exception e) {
				logger.error("##################### TradeSetupController.queryAllTradeSetup() Exception:"+e.getMessage());
				return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
			}
			return rsMap(100, "操作成功").put("status", true).put("data",data).toJson();
		}
	}
	
	
	/**
	* @Title: querySingleTradeSetup
	* @Description: (查询订单中心设置单条设置)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/querySingleTradeSetup",/*method=RequestMethod.POST,*/produces="text/html;charset=UTF-8")
	public @ResponseBody
	String querySingleTradeSetup(Model model,HttpServletRequest request,@RequestBody String params){ 
		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
		TradeSetupVO setup = null;
		try {
			setup = parseJsonToObj(params);
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.parseJsonToObj() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(setup==null)
			return  rsMap(101, "操作失败").put("status", false).toJson();
		setup.setUserId(userId);
		//搜索参数:任务名称,任务状态;类型,用户名称
		if(setup.getType()==null || "".equals(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(!SINGLE_LIST.contains(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else{
			TradeSetup tradeSetup = null;
			try {
				//查询
				tradeSetup = tradeSetupService.querySingleTradeSetup(setup);
			} catch (Exception e) {
				logger.error("##################### TradeSetupController.queryAllTradeSetup() Exception:"+e.getMessage());
				return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
			}
			return rsMap(100, "操作成功").put("status", true).put("data",tradeSetup).toJson();
		}
	}
	
	
	
	/**
	* @Title: showSingleTradeSetup
	* @Description: (查询单个订单中心设置,用户数据回填)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/showSingleTradeSetup",/*method=RequestMethod.POST,*/produces="text/html;charset=UTF-8")
	public @ResponseBody
	String showSingleTradeSetup(Model model,HttpServletRequest request,@RequestBody String params){ 
		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
		TradeSetupVO setup = null;
		try {
			setup = parseJsonToObj(params);
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.parseJsonToObj() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(setup==null)
			return  rsMap(101, "操作失败").put("status", false).toJson();
		setup.setUserId(userId);
		//搜索参数:任务名称,任务状态;类型,用户名称
		if(setup.getType()==null || "".equals(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(!DICT_MAP.containsKey(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(setup.getId()==null){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else{
			TradeSetup tradeSetup = null;
			try {
				//查询
				tradeSetup = tradeSetupService.showSingleTradeSetup(setup);
			} catch (Exception e) {
				logger.error("##################### TradeSetupController.querySingleTradeSetup() Exception:"+e.getMessage());
				return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
			}
			return rsMap(100, "操作成功").put("status", true).put("data",tradeSetup).toJson();
		}
	}
	
	
	/**
	* @Title: deleteTradeSetup
	* @Description: (查询订单中心设置)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/deleteTradeSetup",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String deleteTradeSetup(Model model,HttpServletRequest request,@RequestBody String params){ 
		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
		TradeSetupVO setup = null;
		try {
			setup = parseJsonToObj(params);
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.parseJsonToObj() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(setup==null)
			return  rsMap(101, "操作失败").put("status", false).toJson();
		setup.setUserId(userId);
		//必须参数:id,类型;用户名称
		if(setup.getType()==null || "".equals(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(!DICT_MAP.containsKey(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(setup.getId()==null){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else{
			try {
				//查询
				tradeSetupService.deleteTradeSetup(setup);
				addUserPermitByMySql(setup.getUserId());
			} catch (Exception e) {
				logger.error("##################### TradeSetupController.deleteTradeSetup() Exception:"+e.getMessage());
				return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
			}
			return rsMap(100, "操作成功").put("status", true).toJson();
		}
	}
	
	
	/**
	* @Title: switchTradeSetup
	* @Description: (打开或关闭订单中心设置)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/switchTradeSetup",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String switchTradeSetup(Model model,HttpServletRequest request,@RequestBody String params){ 
		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
		TradeSetupVO setup = null;
		try {
			setup = parseJsonToObj(params);
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.parseJsonToObj() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(setup==null)
			return  rsMap(101, "操作失败").put("status", false).toJson();
		setup.setUserId(userId);
		//必须参数:id,类型,状态;用户名称
		if(setup.getType()==null || "".equals(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(!DICT_MAP.containsKey(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(setup.getId()==null){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(setup.getStatus()==null){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else{
			try {
				//打开或者关闭
				tradeSetupService.switchTradeSetup(setup);
				addUserPermitByMySql(setup.getUserId());
			} catch (Exception e) {
				logger.error("##################### TradeSetupController.switchTradeSetup() Exception:"+e.getMessage());
				return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
			}
			return rsMap(100, "操作成功").put("status", true).toJson();
		}
	}
	
	
	/**
	* @Title: updateTradeSetup
	* @Description: (修改订单中心设置)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/updateTradeSetup",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String updateTradeSetup(Model model,HttpServletRequest request,@RequestBody String params){ 
		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
		TradeSetupVO setup = null;
		try {
			setup = parseJsonToObj(params);
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.parseJsonToObj() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(setup==null)
			return  rsMap(101, "操作失败").put("status", false).toJson();
		setup.setUserId(userId);
		try {
			Map<String, Object> map = checkTradeSetup(setup,false);
			if(!(boolean)map.get("flag")){
				return  rsMap(101, "操作失败").put("status", false).put("information",map.get("message")).toJson();
			}else if(setup.getId()==null){
				return  rsMap(101, "操作失败").put("status", false).toJson();
			}else{
				TradeSetup tradeSetup =null;
				//转换
				tradeSetup = handleUpdateSetupData(setup,new TradeSetup());
				//更新??是否返回实体
				tradeSetupService.updateTradeSetup(tradeSetup);
				addUserPermitByMySql(setup.getUserId());
				return rsMap(100, "操作成功").put("status", true).toJson();
			}
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.updateTradeSetup() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
	}
	
	/**
	* @Title: restTradeSetupLevel
	* @Description: (修改订单中心设置的任务级别)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/resetTradeSetupLevel",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String resetTradeSetupLevel(Model model,HttpServletRequest request,@RequestBody String params){ 
		String userId  = (String) request.getSession().getAttribute("taobao_user_nick");
		TradeSetupVO setup = null;
		try {
			setup = parseJsonToObj(params);
		} catch (Exception e) {
			logger.error("##################### TradeSetupController.parseJsonToObj() Exception:"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(setup==null)
			return  rsMap(101, "操作失败").put("status", false).toJson();
		setup.setUserId(userId);
		if(setup.getType()==null || "".equals(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(!DICT_MAP.containsKey(setup.getType())){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(setup.getId()==null){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else if(setup.getTaskLevel()==null){
			return  rsMap(101, "操作失败").put("status", false).toJson();
		}else{
			try {
				//打开或者关闭
				tradeSetupService.resetTradeSetupLevel(setup);
				addUserPermitByMySql(setup.getUserId());
			} catch (Exception e) {
				logger.error("##################### TradeSetupController.restTradeSetupLevel() Exception:"+e.getMessage());
				return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
			}
			return rsMap(100, "操作成功").put("status", true).toJson();
		}
	}
	
	/**
	* @Title: parseJsonToObj
	* @Description: (前端传递参数转换成TradeSetupVo)
	* @return TradeSetupVo    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private TradeSetupVO parseJsonToObj(String params){
		TradeSetupVO setup = null;
		if(params!=null){
			JSONObject parseObject = JSON.parseObject(params);
			String object = parseObject.getString("params");
			//setup = JsonUtil.fromJson(object, TradeSetupVO.class);
			setup = JSON.parseObject(object, TradeSetupVO.class);
		}
		return setup;
	}
	
	/**
	 * @throws Exception 
	* @Title: checkTradeSetup
	* @Description: (校验用户保存的订单中心的设置& 非必须字段值赋空值!!)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private Map<String,Object> checkTradeSetup(TradeSetupVO vo,boolean control) throws Exception{
		if(vo.getType()==null || "".equals(vo.getType()) || !DICT_MAP.containsKey(vo.getType()))
			return resultMap(false,"设置类型错误!");
		if(vo.getStatus()==null)
			return resultMap(false,"设置状态错误!");
		//短信内容不能过长,大于500字
		if(vo.getSmsContent()==null || "".equals(vo.getSmsContent())){
			return resultMap(false,"短信内容不能为空!");
		}else{
			String shopName  = queryShopName(vo.getUserId());
			if(!checkSmsContent(vo.getSmsContent(),shopName==null?"【】":"【"+shopName+"】","退订回N"))
				return resultMap(false,"短信内容不能大于500字!");
		}
		//仅仅针对自动评价
		if(OrderSettingInfo.AUTO_RATE.equals(vo.getType())){
			if(vo.getDelayEvaluate()==null||vo.getEvaluateBlack()==null)
				return resultMap(false,"必选项不能为空!");
			if(!vo.getDelayEvaluate())vo.setDelayDate(null);
			if(vo.getEvaluateBlack()){
				vo.setEvaluateBlackType(null);
				vo.setEvaluateBlackContent(null);
			}else{
				if(vo.getEvaluateBlackContent()!=null && "".equals(vo.getEvaluateBlackContent()) && vo.getEvaluateBlackContent().length()>500)
					return resultMap(false,"黑名单评价内容不能大于500字!");
			}
		}else if(OrderSettingInfo.APPRAISE_MONITORING_ORDER.equals(vo.getType())){
			if(vo.getTimeOutInform()==null)
				return resultMap(false,"必选项不能为空!");
			if(vo.getInformMobile()!=null && !"".equals(vo.getInformMobile())){
				String[] mobiles = vo.getInformMobile().split(",|，");
				if(mobiles!=null && mobiles.length>=1){
					for (String m : mobiles) {
						if(!PhoneRegUtils.phoneValidate(m))
							return resultMap(false,"通知号码填写错误!");
					}
				}else{
					return resultMap(false,"通知号码填写错误!");
				}
			}
		}else{
			//卖家标记(屏蔽/不屏蔽),指定商品(指定/排除指定)
			/*|| vo.getSellerRemark()==null || vo.getProductType()==null*/
			//执行类型(持续开启/定时开启),超出时间(不发送/次日发送)
			if(vo.getExecuteType()==null || vo.getTimeOutInform()==null)
				return resultMap(false,"必选项不能为空!");
			//定时开启,时间不能为空
			if(vo.getExecuteType() == null 
				|| (!vo.getExecuteType() 
				&& ( vo.getMinExecuteTime()==null || vo.getMaxExecuteTime()==null
					 || vo.getMaxExecuteTime().before(vo.getMinExecuteTime()) 
					) )
			  )
				return resultMap(false,"时间为空或错误!");
			//立即执行
			if(vo.getExecuteType()){
				 vo.setMinExecuteTime(null);
				 vo.setMaxExecuteTime(null); 
			}
		}
		if(BLOCK_LIST.contains(vo.getType())){
			if(vo.getTradeBlock()==null)
				return resultMap(false,"发送短信订单范围不能为空!");
		}else{
			vo.setTradeBlock(null);
			vo.setChosenTime(null);
		}
		//通知时间&排除通知时间单独验证
		Map<String,Object> result =  checkInformTime(vo);
		if(!(boolean)result.get("flag"))
			return resultMap(false,(String)result.get("message"));
		if(REMIND_LIST.contains(vo.getType()))
			if((vo.getTimeType() !=null && vo.getRemindTime()==null)
				|| (vo.getTimeType() ==null && vo.getRemindTime()!=null)
			  )
				return resultMap(false,"通知时间参数错误!");
		//仅保存时调用调用该校验时:
		if(control){
			//多任务允许创建最多20个
			long count = tradeSetupService.queryTradeSetupCount(vo.getUserId(), vo.getType());
			if(MULTI_LIST.contains(vo.getType())){
				if(count>=20)
					return resultMap(false,"设置数量达到上限!");
			}else{
				if(count>=1)
					return resultMap(false,"该类型设置已经存在!");
				vo.setTaskLevel(null);
				vo.setTaskName(null);
			}
		}
		if(MULTI_LIST.contains(vo.getType())){
			//多任务名称的任务是任务名称不能相同
			if(vo.getTaskName()==null || "".equals(vo.getTaskName()))
				return resultMap(false,"该设置的任务名称不能为空!");
			TradeSetup setup = tradeSetupService.queryTradeSetupByTaskName(vo);
			//保存或者修改校验逻辑不一致
			//1保存时num查询结果只能为0
			if(control){
				if(setup !=null)
					return resultMap(false,"该设置的任务名称已存在!");
			}else{
			//2修改时通过userId&type&taskName 查询出的结果为空或者两者实体的id相等
				if(setup!=null && !setup.getId().equals(vo.getId()))
					return resultMap(false,"该设置的任务名称已存在!");
			}
			
		}
		//商品数量后者大于前者
		if(vo.getMinProductNum()!=null && vo.getMaxProductNum()!=null
			&&(vo.getMaxProductNum().compareTo(vo.getMinProductNum())==-1))
			return resultMap(false,"商品数量填写错误!");
		if(vo.getMinPayment()!=null && vo.getMaxPayment()!=null
			&& (vo.getMaxPayment().compareTo(vo.getMinPayment())==-1))
			return resultMap(false,"支付金额填写或错误!");
		return resultMap(true,"");
	}
	
	/**TODO
	* @Title: checkSmsContent
	* @Description: 校验短信长度 不能大于500;未做重复变量的校验(有坑)
	* @return boolean
	* @author:jackstraw_yu
	* @throws
	*/
	private boolean checkSmsContent(String message,String prefix,String suffix){
		int length = prefix.length()+suffix.length();
		for (String key : PARAM_MAP.keySet()) {
			if(message.contains(key)){
				message = message.replace(key, "");
				length += PARAM_MAP.get(key);
			}
		}
		length += message.length();
		if(length>500)
			return false;
		return true;
	}
	
	
	/**
	* @Title: checkInformTime
	* @Description: (校验通知时间);所有时段必须是关闭!
	* @return Map<String,Object>    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private Map<String,Object> checkInformTime(TradeSetupVO vo) throws Exception{
		Date minInformTime =  regexParseTime(vo.getMinInformTime());
		Date maxInformTime = regexParseTime(vo.getMaxInformTime());
		Date minPrimaryInformTime = regexParseTime(vo.getMinPrimaryInformTime());
		Date maxPrimaryInformTime = regexParseTime(vo.getMaxPrimaryInformTime());
		Date minMiddleInformTime = regexParseTime(vo.getMinMiddleInformTime());
		Date maxMiddleInformTime =  regexParseTime(vo.getMaxMiddleInformTime());
		Date minSeniorInformTime = regexParseTime(vo.getMinSeniorInformTime());
		Date maxSeniorInformTime = regexParseTime(vo.getMaxSeniorInformTime());
		if(minInformTime!=null){
			if(maxInformTime == null || maxInformTime.before(minInformTime))
				return resultMap(false,"通知时间填写错误!");
			//fromTime判断
			if(minPrimaryInformTime!=null && minPrimaryInformTime.before(minInformTime))
				return resultMap(false,"排除时间填写错误!");
			if(minMiddleInformTime!=null && minMiddleInformTime.before(minInformTime))
				return resultMap(false,"排除时间填写错误!");
			
			if(minSeniorInformTime!=null && minSeniorInformTime.before(minInformTime))
				return resultMap(false,"排除时间填写错误!");	
			//toTime判断
			if(maxPrimaryInformTime!=null && maxPrimaryInformTime.before(minInformTime))
				return resultMap(false,"排除时间填写错误!");
			if(maxMiddleInformTime!=null && maxMiddleInformTime.before(minInformTime))
				return resultMap(false,"排除时间填写错误!");
			if(maxSeniorInformTime!=null && maxSeniorInformTime.before(minInformTime))
				return resultMap(false,"排除时间填写错误!");
		}
		if(maxInformTime!=null){
			if(minInformTime ==null ||  maxInformTime.before(minInformTime))
				return resultMap(false,"通知时间填写错误!");
			//fromTime
			if(maxPrimaryInformTime!=null && maxInformTime.before(maxPrimaryInformTime))
				return resultMap(false,"排除时间填写错误!");
			if(maxMiddleInformTime!=null && maxInformTime.before(maxMiddleInformTime))
				return resultMap(false,"排除时间填写错误!");
			if(maxSeniorInformTime!=null && maxInformTime.before(maxSeniorInformTime))
				return resultMap(false,"排除时间填写错误!");
			//toTime
			if(minPrimaryInformTime!=null && maxInformTime.before(minPrimaryInformTime))
				return resultMap(false,"排除时间填写错误!");
			if(minMiddleInformTime!=null && maxInformTime.before(minMiddleInformTime))
				return resultMap(false,"排除时间填写错误!");
			if(minSeniorInformTime!=null && maxInformTime.before(minSeniorInformTime))
				return resultMap(false,"排除时间填写错误!");
		}
		//======================================================
		//	自身比较或者排除时间是否交叉
		//======================================================
		//一,
		if(minPrimaryInformTime!=null && maxPrimaryInformTime!=null){
			if(maxPrimaryInformTime.before(minPrimaryInformTime))
				return resultMap(false,"排除时间填写错误!");
			//校验时间是否交叉
			if(minMiddleInformTime!=null && maxMiddleInformTime !=null)
				if(!checkOverlapTime(minPrimaryInformTime,maxPrimaryInformTime,
						minMiddleInformTime,maxMiddleInformTime))
					return resultMap(false,"排除时间填写错误!");
			
			if(minSeniorInformTime!=null && maxSeniorInformTime!=null)
				if(!checkOverlapTime(minPrimaryInformTime,maxPrimaryInformTime,
						minSeniorInformTime,maxSeniorInformTime))
					return resultMap(false,"排除时间填写错误!");
		}
		//时间段必须是关闭
		if((minPrimaryInformTime !=null && maxPrimaryInformTime ==null) 
			|| (minPrimaryInformTime ==null && maxPrimaryInformTime !=null))
			return resultMap(false,"排除时间填写错误!");
		//二,
		if(minMiddleInformTime!=null && maxMiddleInformTime !=null){
			if(maxMiddleInformTime.before(minMiddleInformTime))
				return resultMap(false,"排除时间填写错误!");
			//校验时间是否交叉
			if(minPrimaryInformTime!=null && maxPrimaryInformTime !=null)
				if(!checkOverlapTime(minMiddleInformTime,maxMiddleInformTime,
						minPrimaryInformTime,maxPrimaryInformTime))
					return resultMap(false,"排除时间填写错误!");
			if(minSeniorInformTime!=null && maxSeniorInformTime!=null)
				if(!checkOverlapTime(minMiddleInformTime,maxMiddleInformTime,
						minSeniorInformTime,maxSeniorInformTime))
					return resultMap(false,"排除时间填写错误!");
		}
		//时间段必须是关闭
		if((minMiddleInformTime !=null && maxMiddleInformTime ==null) 
				|| (minMiddleInformTime ==null && maxMiddleInformTime !=null))
			return resultMap(false,"排除时间填写错误!");
		//三,
		if(minSeniorInformTime!=null && maxSeniorInformTime!=null){
			if(maxSeniorInformTime.before(minSeniorInformTime))
				return resultMap(false,"排除时间填写错误!");
			//校验时间是否交叉
			if(minPrimaryInformTime!=null && maxPrimaryInformTime !=null)
				if(!checkOverlapTime(minSeniorInformTime,maxSeniorInformTime,
						minPrimaryInformTime,maxPrimaryInformTime))
					return resultMap(false,"排除时间填写错误!");
			if(minSeniorInformTime!=null && maxSeniorInformTime!=null)
				if(!checkOverlapTime(minSeniorInformTime,maxSeniorInformTime,
						minMiddleInformTime,maxMiddleInformTime))
					return resultMap(false,"排除时间填写错误!");
		}
		//时间段必须是关闭
		if((minSeniorInformTime !=null && maxSeniorInformTime ==null) 
				|| (minSeniorInformTime ==null && maxSeniorInformTime !=null))
			return resultMap(false,"排除时间填写错误!");
		return  resultMap(true,"");
	}
	
	/**
	* @Title: resultMap
	* @Description: (返回结果的map)
	* @return Map<String,Object>    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private Map<String,Object> resultMap(boolean flag,String message){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("flag", flag);
		map.put("message", message);
		return map;
	}
	
	/**
	* @Title: checkOverlapTime
	* @Description: (校验排时间是否交叉)
	* @return boolean    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private boolean checkOverlapTime(Date minSourceTime,Date maxSourceTime,Date minTargetTime,Date maxTargetTime){
		if(minSourceTime.after(minTargetTime) && minSourceTime.before(maxTargetTime))
			return false;
		if(maxSourceTime.after(minTargetTime) && maxSourceTime.before(maxTargetTime))
			return false;
		if(minTargetTime.after(minSourceTime) && minTargetTime.before(maxSourceTime))
			return false;
		if(maxTargetTime.after(minSourceTime) && maxTargetTime.before(maxSourceTime))
			return false;
		return true;
	}
	
	/**
	* @Title: regexParseTime
	* @Description: (解析时:分:秒是否符合规则,不符合直接抛异常)
	* @return TradeSetup    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private Date regexParseTime(String time) throws Exception{
		if(time!=null && !"".equals(time)){
			logger.info("##################### TradeSetupController.regexParseTime() Parameter:"+time);
			if(!regexTime.matcher(time).matches()){
				throw  new Exception("##################### TradeSetupController.regexParseTime() Exception: Invalid argument");
			}else{
				return formatter.parse(time);
			}
		}
		return null;
	}
	
	
	
	/**
	* @Title: handleSaveSetupData
	* @Description: (转换保存订单设置的实体)
	* @return TradeSetup    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private TradeSetup handleSaveSetupData(TradeSetupVO vo,TradeSetup setup) throws Exception{
		//属性copy
		MyBeanUtils.copyProperties(setup, vo);
		setup.setCreatedBy(vo.getUserId());
		setup.setLastModifiedBy(vo.getUserId());
		setup.setCreatedDate(new Date());
		setup.setLastModifiedDate(new Date());
		if(vo.getExecuteType()!=null)
			if(vo.getExecuteType()){
				//为真:立即执行,置空
				setup.setMinExecuteTime(null);
				setup.setMaxExecuteTime(null);
			}else{
				setup.setMinExecuteTime(vo.getMinExecuteTime());
				setup.setMaxExecuteTime(vo.getMaxExecuteTime());
			}
		//各种set/get
//		setup.setProvince(vo.getProvince());
//		setup.setState(vo.getState());
//		setup.setBadEvaluateInform(vo.getBadEvaluateInform());
//		setup.setDelayDate(vo.getDelayDate());
//		setup.setDelayEvaluate(vo.getDelayEvaluate());
//		setup.setEvaluateBlack(vo.getEvaluateBlack());
//		setup.setEvaluateBlackContent(vo.getEvaluateBlackContent());
//		setup.setEvaluateBlackType(vo.getEvaluateBlackType());
//		setup.setEvaluateType(vo.getEvaluateType());
//		setup.setExecuteType(vo.getExecuteType());
//		setup.setFilterBlack(vo.getFilterBlack());
//		setup.setFilterOnce(vo.getFilterOnce());
//		setup.setMaxExecuteTime(vo.getMaxExecuteTime());
//		setup.setMaxInformTime(vo.getMaxInformTime());
//		setup.setMaxPayment(vo.getMaxPayment());
//		setup.setMaxProductNum(vo.getMaxProductNum());
//		setup.setMemberLevel(vo.getMemberLevel());
//		setup.setMinExecuteTime(vo.getMinExecuteTime());
//		setup.setMinInformTime(vo.getMinInformTime());
//		setup.setMinPayment(vo.getMinPayment());
//		setup.setMinProductNum(vo.getMinProductNum());
//		setup.setMinProductNum(vo.getMinProductNum());
//		setup.setNeutralEvaluateInform(vo.getNeutralEvaluateInform());
//		setup.setProducts(vo.getProducts());
//		setup.setProductType(vo.getProductType());
//		setup.setSellerFlag(vo.getSellerFlag());
//		setup.setSellerRemark(vo.getSellerRemark());
//		setup.setSmsContent(vo.getSmsContent());
//		setup.setStatus(vo.getStatus());
//		setup.setTaskLevel(vo.getTaskLevel());
//		setup.setTaskName(vo.getTaskName());
//		setup.setTime(vo.getTime());
//		setup.setTimeOutInform(vo.getTimeOutInform());
//		setup.setTimeType(vo.getTimeType());
//		setup.setTradeFrom(vo.getTradeFrom());
//		setup.setType(vo.getType());
//		setup.setUserId(vo.getUserId());
		return setup;
	}
	
	
	/**
	* @Title: handleUpdateSetupData
	* @Description: (转换更新订单设置的实体)
	* @return TradeSetup    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private TradeSetup handleUpdateSetupData(TradeSetupVO vo,TradeSetup setup) throws Exception{
		//属性copy
		MyBeanUtils.copyProperties(setup, vo);
		setup.setLastModifiedBy(vo.getUserId());
		setup.setLastModifiedDate(new Date());
		setup.setId(vo.getId());
		if(vo.getExecuteType()!=null)
			if(vo.getExecuteType()){
				//为真:立即执行,置空
				setup.setMinExecuteTime(null);
				setup.setMaxExecuteTime(null);
			}else{
				setup.setMinExecuteTime(vo.getMinExecuteTime());
				setup.setMaxExecuteTime(vo.getMaxExecuteTime());
			}
		return 	setup;
	}
	
	/**
	* @Title: queryShopName
	* @Description: (获取店铺签名,签名为空时 以店铺昵称为准)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private String queryShopName(String userId){
    	String shopName=null;
    	shopName = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.SHOP_NAME_CACHE, 
    			RedisConstant.RediskeyCacheGroup.SHOP_NAME_KEY+userId);
    	if(shopName==null || "".equals(shopName)){
    		shopName = userInfoService.queryShopName(userId);
    		if(shopName!=null && !"".equals(shopName))
    			cacheService.putNoTime(RedisConstant.RedisCacheGroup.SHOP_NAME_CACHE, 
    					RedisConstant.RediskeyCacheGroup.SHOP_NAME_KEY+userId,shopName );
    	}
    	return shopName;
    }
	

	/**
	* @Title: returnTaskName
	* @Description: (生成一个设置名称:汉字名称+年月日时分秒)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	private String returnTaskName(String type){
		String name = null;
		if(DICT_MAP.containsKey(type)){
			name = DICT_MAP.get(type);
		}else{
			name = "";
		}
		return name+DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYYMMDDHHMMSS);
	}
	
	

	/**
	* @Title: addUserPermitByMySql
	* @return void   返回类型
	* @author:jackstraw_yu
	*/
	private void addUserPermitByMySql(final String userId){
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread(){
			@Override
			public void run() {
				try {
					userInfoService.addUserPermitByMySql(userId,null);
				} catch (Exception e) {
					logger.error("##################### TradeSetupController.addUserPermitByMySql() Exception:"+e.getMessage());
				}
			}
		});
	}
	
	/**
	 * 商品分组名称列表
	 * @Title: listItemGroupName 
	 * @param @param params
	 * @param @param request
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws,produces="text/html;charset=UTF-8"
	 */
	@RequestMapping(value="/listGroupName")
	@ResponseBody
	public String listItemGroupName(HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		List<CommodityGrouping> itemGroups;
		try {
			itemGroups = commodityGroupingService.listItemGroup(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return rsMap(101, "操作失败").put("status", false).toJson();
		}
		ResultMap<String,Object> resultMap = rsMap(100, "操作成功").put("status", true);
		resultMap.put("data", itemGroups);
		return resultMap.toJson();
	}
	
	/**
	 * 分页查询商品列表
	 * @Title: limitListItem 
	 * @param @param params
	 * @param @param request
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws,produces="text/html;charset=UTF-8"
	 */
	@RequestMapping(value="/listItem")
	@ResponseBody
	public String limitListItem(@RequestBody String params,HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		logger.info("asdasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		ItemVO itemVO = null;
		try {
			JSONObject parseObject = JSON.parseObject(params);
			String object = parseObject.getString("params");
			itemVO = JSON.parseObject(object, ItemVO.class);
		} catch (Exception e) {
			logger.info("~~~~~~~~~~~~~~~~~~~json转对象异常");
			e.printStackTrace();
			return rsMap(102, "操作失败，请重新操作或联系管理员").put("status", false).toJson();
		}
		if(itemVO == null){
			return rsMap(101, "操作失败").put("status", false).toJson();
		}
		itemVO.setUserId(userId);
		ResultMap<String, Object> resultMap = rsMap(100, "操作成功").put("status", true);
		List<Item> itemList = itemService.listItemByTitle(itemVO);
		Integer itemCount = itemService.countItemByTitle(itemVO);
		int totalPage = GetCurrentPageUtil.getTotalPage(itemCount, ConstantUtils.PAGE_SIZE_MIN);
		return resultMap.put("data", itemList).put("totalPage", totalPage).put("pageNo", itemVO.getPageNo()).toJson();
	}
	
	/**
	 * 更改商品缩写
	 * @Title: updateItemSubtitle 
	 * @param @param params
	 * @param @param request
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws,produces="text/html;charset=UTF-8"
	 */
	@RequestMapping(value="/updateSubtitle")
	@ResponseBody
	public String updateItemSubtitle(@RequestBody String params,HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		ItemVO itemVO = null;
		try {
			JSONObject parseObject = JSON.parseObject(params);
			String object = parseObject.getString("params");
			itemVO = JSON.parseObject(object, ItemVO.class);
		} catch (Exception e) {
			logger.info("~~~~~~~~~~~~~~~~~~~json转对象异常");
			e.printStackTrace();
			return rsMap(102, "操作失败，请重新操作或联系管理员").put("status", false).toJson();
		}
		if(itemVO == null){
			return rsMap(101, "操作失败").put("status", false).toJson();
		}
		
		String subtitle = itemVO.getSubtitle();
		Long itemId = itemVO.getItemId();
		try {
			itemService.updateSubtitleById(itemId, subtitle);
		} catch (Exception e) {
			e.printStackTrace();
			return rsMap(102, "操作失败，请重新操作或联系管理员").put("status", false).toJson();
		}
		
		return rsMap(100, "操作成功").put("status", true).toJson();
	}

}
