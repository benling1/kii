package s2jh.biz.shop.crm.data.web;

import lab.s2jh.core.util.DateUtils;
import lab.s2jh.module.sys.entity.DataDict;
import lab.s2jh.module.sys.service.DataDictService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import s2jh.biz.shop.crm.data.entity.ShopDataStatistics;
import s2jh.biz.shop.crm.data.service.ShopDataStatisticsService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.utils.ConstantUtils;

/**
 *店铺数据---店铺数据
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="")
public class ShopDataHistoryController {
	
	@Autowired
	private ShopDataStatisticsService shopDataStatisticsService;
	
	@Autowired
	private DataDictService dataDictService;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	@RequestMapping(value="crms/storeData/todyData")
	public String shopDataHistory(Model model,HttpServletRequest request,String dataType){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String, Object> params = new HashMap<String, Object>();
		//数据类型查询
		List<DataDict> shopDate = dataDictService.findChildrenByPrimaryKey(ConstantUtils.DataType);
		params.put("shopDate", shopDate);
		model.addAttribute("params", params);
		
		Map<String, Date> dateMap = DateUtils.caculateDate(-1);
		//昨日数据查询
		//List<ShopDataStatistics> shopDataStatistics = shopDataStatisticsService.findYestoday(userId, dataType, null, null);
		List<ShopDataStatistics> shopDataStatistics  = tradeInfoService.queryShopData(userId,dateMap,dataType);
		model.addAttribute("shopDataStatistics", shopDataStatistics);
		model.addAttribute("dataType", dataType);
		return "crms/storeData/todyData";
	}
	
	
	@RequestMapping(value="/crms/storeData/beforeyesterDayData")
	public String beforeYesterDay(Model model,String dataType,String beginTime,String endTime,HttpServletRequest request){
		Map<String, Object> params = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//数据类型查询
		List<DataDict> shopDate = dataDictService.findChildrenByPrimaryKey(ConstantUtils.DataType);
		params.put("shopDate", shopDate);
		model.addAttribute("params", params);
		Map<String, Date> dateMap = DateUtils.caculateDate(-2);
		//前日数据查询
		//List<ShopDataStatistics> shopDataStatistics1 = shopDataStatisticsService.findBeforeYestoday(userId,dataType, beginTime, endTime);
		List<ShopDataStatistics> shopDataStatistics1  = tradeInfoService.queryShopData(userId,dateMap,dataType);
		model.addAttribute("shopDataStatistics1", shopDataStatistics1);
		model.addAttribute("dataType", dataType);
		return "crms/storeData/beforeyesterDayData";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/crms/storeData/historyData")
	public String historyData(Model model,String dataType,String beginTime,String endTime,HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String, Object> params = new HashMap<String, Object>();
		//数据类型查询
		List<DataDict> shopDate = dataDictService.findChildrenByPrimaryKey(ConstantUtils.DataType);
		params.put("shopDate", shopDate);
		model.addAttribute("params", params);

		Map<String, Date> dateMap = new HashMap<String,Date>();
		dateMap.put("minTime", DateUtils.composeDateTime(beginTime,true));
		dateMap.put("maxTime", DateUtils.composeDateTime(endTime,false));
		Date minTime =null;
		Date maxTime =null;
		if(beginTime!=null && !"".equals(beginTime)) minTime = DateUtils.parseDate(beginTime, "yyyy-MM-dd");
		if(endTime!=null && !"".equals(endTime)) maxTime = DateUtils.parseDate(endTime, "yyyy-MM-dd");
		//List<ShopDataStatistics> shopDataStatistics2 = shopDataStatisticsService.findHistoryData(userId,dataType, beginTime, endTime);
		List<ShopDataStatistics> shopDataStatistics2  = tradeInfoService.queryShopData(userId,dateMap,dataType);
		model.addAttribute("shopDataStatistics2", shopDataStatistics2);
		model.addAttribute("dataType", dataType);
		model.addAttribute("beginTime", minTime);
		model.addAttribute("endTime", maxTime);
		return "crms/storeData/historyData";
	}

}
