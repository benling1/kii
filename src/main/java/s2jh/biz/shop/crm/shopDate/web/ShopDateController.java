package s2jh.biz.shop.crm.shopDate.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.service.RedisLockServiceImpl;
import lab.s2jh.module.sys.service.DataDictService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.data.service.DataStatisService;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;
import s2jh.biz.shop.crm.order.pojo.ReminderNum;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.ExcelExportUtils;
import s2jh.biz.shop.utils.JsonUtil;
import s2jh.biz.shop.utils.pagination.Pagination;

/**
 * 店铺数据---店铺数据
 *
 * @param model
 * @return
 */
@Controller
@RequestMapping(value = "/")
public class ShopDateController {
	
	private Map<String,Object> paramMap = new HashMap<String, Object>();
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private DataStatisService dataStatisService;
	
	@Autowired
	private DataDictService dataDictService;
	
	@Autowired
	private SmsSendRecordService smsSendRecordService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private RedisLockServiceImpl redisLockService;
	
	private static final Log logger = LogFactory.getLog(ShopDateController.class);
	/**
	 * 店铺数据的短信发送记录页面的跳转
	 * @return
	 */
	@RequestMapping("/crms/shopData/smsSendRecord")
	public String smsSendRecordIndex(){
		return "redirect:/crms/shopData/smsSendRecord/list";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/crms/storeData/todyData",method = RequestMethod.GET)
	public String todyData(Model model){
		
		Map<String, Object> params = new HashMap<String, Object>();
        
		return "crms/storeData/todyData";
		
	}
	
	@RequestMapping("/shopData/reportJsp")
	public String shopDataReportJsp(){
		
		return "/crms/storeData/smsConsumeReport";
	}
	
	/**
	 * 根据店家查询条件查询短信发送记录(ZTK)
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/crms/shopData/smsSendRecord/list")
	public String findTotalCountByTime(HttpServletRequest request,Model model,Integer pageNo,
			String recNum,String status,String type,String buyerNick,String beginTime,
			String endTime,String dateType){
		//短信发送记录卖家查询条件的map集合
		String oneDay = request.getParameter("oneDay");
		String contextPath = request.getContextPath();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Date bTime = null;
		Date eTime = null;
		//手机号支持模糊查询,昵称不支持模糊查询
		boolean like = false;

		SmsRecordDTO srd = new SmsRecordDTO();
		//近一天的记录，设置时间为当前时间和一天前的时间
		if("2".equals(oneDay)){	
			if(dateType != null && beginTime != null){
				endTime = beginTime;
				if(status == null || "".equals(status)){
					status = "2";
				}
				try {
					switch (dateType) {
					case "year":
						if(beginTime.length() >= 4){
							int year = Integer.parseInt(beginTime.substring(0, 4));
							bTime = DateUtils.getYearFirst(year);
							eTime = DateUtils.getYearLast(year);
						}
						break;
					case "month":
						if(beginTime.length() >= 7){
							int year = Integer.parseInt(beginTime.substring(0, 4));
							int month = Integer.parseInt(beginTime.substring(5, 7));
							bTime = DateUtils.getFirstDayOfMonth(year, month);
							eTime = DateUtils.getLastDayOfMonth(year, month);
						}			
						break;
					case "day":
						Date date = DateUtils.convertStringToDate(beginTime);
						bTime = DateUtils.getStartTimeOfDay(date);
						eTime = DateUtils.getEndTimeOfDay(date);
						break;
					default:
						break;
					}
				} catch (NumberFormatException e) {
					logger.info("~~~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!ShopDataController.findTotalCountByTime()日期转换异常");
					e.printStackTrace();
				}
			}else {
				bTime = convertDate(beginTime);
				eTime = convertDate(endTime);
			}
			 //设置近一天是否勾选
			model.addAttribute("showOneDay","2");
		}else{
			 //如果勾选近一天，但是时间选择框不为空，则勾选历史记录按钮
			 if(beginTime != null && !"".equals(beginTime)){
				 bTime = convertDate(beginTime);
				 eTime = convertDate(endTime);
				 oneDay = "2";
				 model.addAttribute("showOneDay","2");
			 }else{
				 Calendar cd = Calendar.getInstance();
				 cd.add(Calendar.DATE, -1);
				 bTime = cd.getTime();
				 eTime = new Date();
				 //设置近一天是否勾选
				 model.addAttribute("showOneDay","1");
			 }
		}
		//封装查询条件queryMap
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
		
		if("0".equals(status)){
		}else if ("2".equals(status)) {
			srd.setStatus(2);
		}else if ("1".equals(status)) {
			srd.setStatus(1);
		}
		//短信类型
		if(type != null && !"".equals(type)&&!"0".equals(type)){
			srd.setType(type);
		}
		/*开始时间*/
		Long btime = 0L;
		if(bTime!=null){
//			srd.setbTime(bTime);
			btime = bTime.getTime();
		}
		/*结束时间*/
		Long etime = 0L;
		if(eTime!=null){
//			srd.seteTime(eTime);
			etime= eTime.getTime();
		}
		srd.setUserId(userId);
		Pageination<SmsRecordDTO> page = new Pageination<SmsRecordDTO>();
		if(pageNo==null){
			pageNo=1;
		}
		page.setPageNo(pageNo);
		page.setPageSize(10);
		Pageination pagination = smsRecordService.findSendRecordPageList(page, srd, userId,btime,etime,like);
		
		//分页查询，展示到页面
		StringBuilder params = new StringBuilder();
		if(recNum != null && !"".equals(recNum)){
			params.append("&recNum=").append(recNum);
		}
		if(status != null && !"".equals(status)){
			params.append("&status=").append(status);
		}
		if(type != null && !"".equals(type)){
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
		if(oneDay != null && !"".equals(oneDay)){
			params.append("&oneDay=").append(oneDay);
		}
		if(dateType != null && !"".equals(dateType)){
			params.append("&dateType=").append(dateType);
		}
		//拼接分页的后角标中的跳转路径与查询的条件
		
		String url = contextPath + "/crms/shopData/smsSendRecord/list";
		int count = 0;
		List<SmsRecordDTO> list = new ArrayList<SmsRecordDTO>();
		if(pagination!=null&&pagination.getTotalCount()>0L){
			list = pagination.getDatas();
			list = smsRecordService.ConvertDate(list, userId);
			count = new Long(pagination.getTotalCount()).intValue();
			Pagination pt = new Pagination(pageNo, 10, count, list);
			pt.pageView(url, params.toString());
			model.addAttribute("pagination", pt);
		}
		//设置查询条件回显
		model.addAttribute("recNum", recNum);
		model.addAttribute("type", type);
		model.addAttribute("status", status);
		model.addAttribute("buyerNick", buyerNick);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("dateType", dateType);
		long totalSms =0L;
		int deductSms =0;
		if(!"1".equals(status)){
			SmsRecordDTO sdto = smsRecordService.findSmsSendDeductionCount(userId, srd, btime, etime,like);
			if(sdto!=null){
				totalSms = sdto.getStatus();
				deductSms = sdto.getActualDeduction();
			}
		}
		//计算页面所需数据(发送短信总条数，实际扣费条数)
		model.addAttribute("totalSms", totalSms);
		model.addAttribute("deductSms", deductSms);
		return "/crms/storeData/text_history";
	}
	
	
	/**
	 * 店铺数据中导出短信发送记录
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/crms/shopData/smsSendRecord/report")
	public String reportSmsRecord(HttpServletResponse response,HttpServletRequest request,Model model,Integer pageNo,
			String recNum,String status,String type,String buyerNick,String beginTime,String endTime,String oneDayId) throws IOException{
		ServletOutputStream outputStream = null;
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		String token = (String) request.getSession().getAttribute("access_token");
		String oneDay = "";
		boolean like = false;
		//设置导出报表文件名
		String filename = new String("短信发送记录.xlsx".getBytes(),"ISO-8859-1");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//创建一个工作簿
		
		SXSSFWorkbook book = new SXSSFWorkbook();
		SmsRecordDTO srd = new SmsRecordDTO();
		if(status!=null&&!"".equals(status)&&!"全部".equals(status)){
			srd.setStatus(Integer.parseInt(status));
		}
		//封装查询条件queryMap
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
		if(type!=null&&!"".equals(type)&&!"全部".equals(type)){
			switch(type){
			case "下单关怀":
				srd.setType("1");
				break;
			case "常规催付":
				srd.setType("2");
				break;
			case "二次催付":
				srd.setType("3");
				break;
			case "聚划算催付":
				srd.setType("4");
				break;
			case "预售催付":
				srd.setType("5");
				break;
			case "发货提醒":
				srd.setType("6");
				break;
			case "到达同城提醒":
				srd.setType("7");
				break;
			case "派件提醒":
				srd.setType("8");
				break;
			case "签收提醒":
				srd.setType("9");
				break;
			case "疑难件提醒":
				srd.setType("10");
				break;
			case "延时发货提醒":
				srd.setType("11");
				break;
			case "宝贝关怀":
				srd.setType("12");
				break;
			case "付款关怀":
				srd.setType("13");
				break;
			case "回款提醒":
				srd.setType("14");
				break;
			case "退款关怀":
				srd.setType("15");
				break;
			case "自动评价":
				srd.setType("16");
				break;
			case "批量评价":
				srd.setType("17");
				break;
			case "评价记录":
				srd.setType("18");
				break;
			case "中差评管理":
				srd.setType("19");
				break;
			case "中差评监控":
				srd.setType("20");
				break;
			case "中差评安抚":
				srd.setType("21");
				break;
			case "中差评统计":
				srd.setType("22");
				break;
			case "中差评原因":
				srd.setType("23");
				break;
			case "中差评原因设置":
				srd.setType("24");
				break;
			case "中差评原因分析":
				srd.setType("25");
				break;
			case "手动订单提醒":
				srd.setType("26");
				break;
			case "优秀催付案例":
				srd.setType("27");
				break;
			case "效果统计":
				srd.setType("28");
				break;
			case "买家申请退款":
				srd.setType("29");
				break;
			case "退款成功":
				srd.setType("30");
				break;
			case "等待退货":
				srd.setType("31");
				break;
			case "拒绝退款":
				srd.setType("32");
				break;
			case "会员短信群发":
				srd.setType("33");
				break;
			case "指定号码发送":
				srd.setType("34");
				break;
			case "订单短信群发":
				srd.setType("35");
				break;
			case "会员互动":
				srd.setType("36");
				break;
			}
		}
		Long btime = 0L;
		Long etime = 0L;
		if(beginTime!=null&&!"".equals(beginTime)&&endTime!=null&&!"".equals(endTime)){
			try {
				btime = format.parse(beginTime + " 00:00:00").getTime();
				etime = format.parse(endTime + " 23:59:59").getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			if(oneDayId!=null&&oneDayId.equals("1")){
				 Calendar cd = Calendar.getInstance();
				 cd.add(Calendar.DATE, -1);
				Date bTime = cd.getTime();
				Date eTime = new Date();
				btime = bTime.getTime();
				etime = eTime.getTime();
			}
		}
		//获得集合填充到表格中
		List<SmsRecordDTO> list = smsRecordService.findSmsRecordDTOList(userId,srd,btime,etime,like);
		list = smsRecordService.ConvertDate(list, userId);
		//使用工作簿创建一个工作表
		Sheet sheet = book.createSheet();
		//创建一行
		Row row = sheet.createRow(0);
		//创建单元格
		Cell cell = row.createCell(0);
		//设置第一行的内容
		cell.setCellValue("序号");
		row.createCell(1).setCellValue("手机号码");
		row.createCell(2).setCellValue("短信内容");
		row.createCell(3).setCellValue("短信通道");
		row.createCell(4).setCellValue("短信类型");
		row.createCell(5).setCellValue("买家昵称");
		row.createCell(6).setCellValue("发送状态");
		row.createCell(7).setCellValue("实际扣费(条)");
		row.createCell(8).setCellValue("发送时间");
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i+1);
				row.createCell(0).setCellValue(i+1);
				row.createCell(1).setCellValue(list.get(i).getRecNum());
				row.createCell(2).setCellValue(list.get(i).getContent());
				row.createCell(3).setCellValue(list.get(i).getChannel());
				row.createCell(4).setCellValue(list.get(i).getType());
				if(list.get(i).getType() != null && !"".equals(list.get(i).getType())){
					switch (list.get(i).getType()) {
					case "1":row.createCell(4).setCellValue("下单关怀");break;
					case "2":row.createCell(4).setCellValue("常规催付");break;
					case "3":row.createCell(4).setCellValue("二次催付");break;	
					case "4":row.createCell(4).setCellValue("聚划算催付");break;
					case "5":row.createCell(4).setCellValue("预售催付");break;
					case "6":row.createCell(4).setCellValue("发货提醒");break;
					case "7":row.createCell(4).setCellValue("到达同城提醒");break;
					case "8":row.createCell(4).setCellValue("派件提醒");break;
					case "9":row.createCell(4).setCellValue("签收提醒");break;
					case "10":row.createCell(4).setCellValue("疑难件提醒");break;
					case "11":row.createCell(4).setCellValue("延时发货提醒");break;
					case "12":row.createCell(4).setCellValue("宝贝关怀");break;
					case "13":row.createCell(4).setCellValue("付款关怀");break;
					case "14":row.createCell(4).setCellValue("回款提醒");break;
					case "15":row.createCell(4).setCellValue("退款关怀");break;
					case "16":row.createCell(4).setCellValue("自动评价");break;
					case "17":row.createCell(4).setCellValue("批量评价");break;
					case "18":row.createCell(4).setCellValue("评价记录");break;
					case "19":row.createCell(4).setCellValue("中差评管理");break;
					case "20":row.createCell(4).setCellValue("中差评监控");break;
					case "21":row.createCell(4).setCellValue("中差评安抚");break;
					case "22":row.createCell(4).setCellValue("中差评统计");break;
					case "23":row.createCell(4).setCellValue("中差评原因");break;
					case "24":row.createCell(4).setCellValue("中差评原因设置");break;
					case "25":row.createCell(4).setCellValue("中差评原因分析");break;
					case "26":row.createCell(4).setCellValue("手动订单提醒");break;
					case "27":row.createCell(4).setCellValue("优秀催付案例");break;
					case "28":row.createCell(4).setCellValue("效果统计");break;
					case "29":row.createCell(4).setCellValue("买家申请退款");break;
					case "30":row.createCell(4).setCellValue("退款成功");break;
					case "31":row.createCell(4).setCellValue("等待退货");break;
					case "32":row.createCell(4).setCellValue("拒绝退款");break;
					case "33":row.createCell(4).setCellValue("会员短信群发");break;
					case "34":row.createCell(4).setCellValue("指定号码群发");break;
					case "35":row.createCell(4).setCellValue("订单短信群发");break;
					case "36":row.createCell(4).setCellValue("会员互动");break;
					default:break;
					}
				}
				row.createCell(5).setCellValue(list.get(i).getBuyerNick());
				if(list.get(i).getStatus() != null && !"".equals(list.get(i).getStatus())){
					if(list.get(i).getStatus() == 1){
						row.createCell(6).setCellValue("发送失败");
					}
					if(list.get(i).getStatus() == 2){
						row.createCell(6).setCellValue("发送成功");
					}
				}
				if(list.get(i).getActualDeduction() != null && !"".equals(list.get(i).getActualDeduction())){
					row.createCell(7).setCellValue(list.get(i).getActualDeduction());
				}
				if(list.get(i).getSendTime() != null && !"".equals(list.get(i).getSendTime())){
					row.createCell(8).setCellValue(format.format(list.get(i).getSendTime()));
				}
			}
			//创建第一列合计行
			row = sheet.createRow(list.size()+1);
			row.createCell(0).setCellValue("合计");//第一行，名称“合计”
			//设置合并
			sheet.addMergedRegion(new CellRangeAddress(list.size()+1,list.size()+1,(short)1,(short)4));
			sheet.addMergedRegion(new CellRangeAddress(list.size()+1,list.size()+1,(short)5,(short)8));
			int totalSms = 0;
			int deductSms = 0;
			if(!"1".equals(status)){
				SmsRecordDTO sdto = smsRecordService.findSmsSendDeductionCount(userId, srd, btime, etime,like);
				totalSms = sdto.getStatus();
				deductSms = sdto.getActualDeduction();
			}
			//设置居中
			row.createCell(1).getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
			row.createCell(1).setCellValue("发送成功"+totalSms+"条短信");
			row.createCell(5).getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
			row.createCell(5).setCellValue("实际扣费"+deductSms+"条");
			//设置短信类型、短信条数与时间一列的宽度
			sheet.setColumnWidth(4, 3500);
			sheet.setColumnWidth(7, 3500);
			sheet.setColumnWidth(8, 5000);
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
		}else{
			if(paramMap != null){
				oneDay = (String) paramMap.get(userId + "oneDay");
				if("2".equals(oneDay)){
					//设置近一天的勾选
					model.addAttribute("showOneDay","2");
				}else{								
					model.addAttribute("showOneDay","1");
				}
			}else {
				model.addAttribute("showOneDay","1");
			}
			model.addAttribute("hasData", 1);
			return "/crms/storeData/text_history";
		}
		try {
			outputStream = response.getOutputStream();
			book.write(outputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if("2".equals(oneDay)){
			 //设置近一天的勾选
			model.addAttribute("showOneDay","2");
		}else{								
			model.addAttribute("showOneDay","1");
		}
		return "/crms/storeData/text_history";
	}
	
	/**
	 * 短信账单列表(年/月/日分组)
	 * @Title: listSmsReportByDate 
	 * @param @param dateType
	 * @param @param pageNo
	 * @param @param bTimeStr
	 * @param @param eTimeStr
	 * @param @param request
	 * @param @param model
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping(value="/shopData/reportList")
	@ResponseBody
	public String listSmsReportByDate(@RequestParam(defaultValue="day")String dateType,Integer pageNo,
			String bTimeStr,String eTimeStr,HttpServletRequest request,Model model){
		if(pageNo == null){
			pageNo = 1;
		}
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
//		String userId = "哈数据库等哈";
		Date bTime = null;
		Date eTime = null;
		if("day".equals(dateType)){
			if(bTimeStr != null && !"".equals(bTimeStr)){
				bTime = DateUtils.getStartTimeOfDay(DateUtils.convertStringToDate(bTimeStr));
			}
			if(eTimeStr != null && !"".equals(eTimeStr)){
				eTime = DateUtils.getEndTimeOfDay(DateUtils.convertStringToDate(eTimeStr));
			}else {
				eTime = new Date(System.currentTimeMillis());
			}
		}else if("month".equals(dateType)){
			if(bTimeStr != null && !"".equals(bTimeStr) && bTimeStr.length() >= 7){
				bTime = DateUtils.getFirstDayOfMonth(Integer.parseInt(bTimeStr.substring(0, 4)), Integer.parseInt(bTimeStr.substring(5, 7)));
			}
			if(eTimeStr != null && !"".equals(eTimeStr) && eTimeStr.length() >= 7){
				eTime = DateUtils.getLastDayOfMonth(Integer.parseInt(eTimeStr.substring(0, 4)), Integer.parseInt(eTimeStr.substring(5, 7)));
			}else {
				eTime = new Date(System.currentTimeMillis());
			}
		}else if("year".equals(dateType)){
			if(bTimeStr != null && !"".equals(bTimeStr) && bTimeStr.length() >= 4){
				bTime = DateUtils.getYearFirst(Integer.parseInt(bTimeStr.substring(0, 4)));
			}
			if(eTimeStr != null && !"".equals(eTimeStr) && eTimeStr.length() >= 4){
				eTime = DateUtils.getYearLast(Integer.parseInt(eTimeStr.substring(0, 4)));
			}else {
				eTime = new Date(System.currentTimeMillis());
			}
		}
		Map<String, Object> resultMap = smsRecordService
				.limitAggConsumeList(userId, pageNo, bTime, eTime, dateType);
		Integer totalCount = 0;
		if(resultMap != null){
			totalCount = (Integer) resultMap.get("totalCount");
		}
		Map<String,Object> queryMap = new HashMap<>();
		queryMap.put("userId", userId);
		queryMap.put("pageNo", pageNo);
		queryMap.put("bTime", bTime);
		queryMap.put("eTime", eTime);
		queryMap.put("dateType", dateType);
		queryMap.put("totalCount", totalCount);
		redisLockService.putStringValueWithExpireTime(userId + "BILLQueryMap", JsonUtil.toJson(queryMap), TimeUnit.HOURS, 1L);
		resultMap.put("pageNo", pageNo);
		logger.info("resultMap:" + JsonUtil.toJson(resultMap));
		return JsonUtil.toJson(resultMap);
	}
	
	/**
	 * 导出短信账单列表(年/月/日分组)
	 * @Title: reportSmsBill 
	 * @param @param request
	 * @param @param response 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	@RequestMapping(value="/shopData/report")
	public String reportSmsBill(HttpServletRequest request,HttpServletResponse response){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = redisLockService.getValue(userId + "BILLQueryMap", Map.class);
		if(queryMap == null || null == queryMap.get("userId")){
			return "";
		}
		Date bTime = null;
		Date eTime = null;
		String bStrTime = (String) queryMap.get("bTime");
		String eStrTime = (String) queryMap.get("eTime");
		Integer totalCount = (Integer) queryMap.get("totalCount");
		if(bStrTime != null){
			bTime = DateUtils.convertDate(bStrTime);
		}
		if(eStrTime != null){
			eTime = DateUtils.convertDate(eStrTime);
		}
		String dateType = (String) queryMap.get("dateType");
		List<ReminderNum> dataList = smsRecordService.aggConsumeList(userId, bTime, eTime, dateType);
		List<Object[]> resultList = new ArrayList<>();
		if(dataList != null && !dataList.isEmpty()){
			for (int i = 0; i < dataList.size(); i++) {
				resultList.add(new Object[]{dataList.get(i).getDate(),dataList.get(i).getSmsNum()});
			}
			resultList.add(new Object[]{"合计",totalCount});
		}
		String[] rowNames = new String[]{"时间","扣除短信条数(条)"};
		try {
			String filename = new String("短信账单报表.xls".getBytes(),"ISO-8859-1");
			//使用工具生产Excel表格
			HSSFWorkbook workbook = ExcelExportUtils.export("短信账单报表", rowNames, resultList);
			response.setContentType("application/x-msdownload");
	        response.setHeader("Content-Disposition","attachment;filename=" + filename);
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//TODO
	
	//---------------------------------------以下为工具类方法---------------------------------------------------
	/**
	 * 定义一个日期的工具转换类
	 * @param time
	 * @return
	 */
	public Date convertDate(String time){
		
		Date Ctime = null;//结束时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(time!=null && !"".equals(time)){
			try {
				Ctime = dateFormat.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return Ctime;
		
	} 
	
	
	/**
	 * @Description:店铺数据短信发送记录查询:
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
				logger.error("店铺数据-短信发送记录手机号查询加密失败!");
				key = getSessionkey(nick,false);
				try {
					encrypt = client.encrypt(mobile, EncrptAndDecryptClient.PHONE,key);
				} catch (Exception e1) {
					logger.error("店铺数据-短信发送记录手机号查询<>再次<>加密失败!");
				}
			}
		}else{
			try {
				encrypt = client.search(mobile, EncrptAndDecryptClient.PHONE,key).replace("$1$$","").replace("$", "").replace("=", "");
			} catch (Exception e) {
				logger.error("店铺数据-短信发送记录手机号模糊查询加密失败!");
				key = getSessionkey(nick,false);
				try {
					encrypt = client.search(mobile, EncrptAndDecryptClient.PHONE,key).replace("$1$$","").replace("$", "").replace("=", "");
				} catch (Exception e1) {
					logger.error("店铺数据-短信发送记录手机号模糊查询<>再次<>加密失败!");
				}
			}
		}
		
		return encrypt;
	}
	
	/**
	 * @Description:店铺数据短信发送记录查询,昵称加密
	 * @Copy_author jackstraw_yu
	 */
	private String encryptSearchNick(String nick,String userId) {
		EncrptAndDecryptClient client = EncrptAndDecryptClient.getInstance(); 
		String key = getSessionkey(userId,true),encrypt=null;
		try {
			encrypt = client.search(nick, EncrptAndDecryptClient.SEARCH,key);
		} catch (Exception e) {
			logger.error("店铺数据-短信发送记录用户昵称模糊查询加密失败!");
			key = getSessionkey(userId,false);
			try {
				encrypt = client.search(nick, EncrptAndDecryptClient.SEARCH,key);
			} catch (Exception e1) {
				logger.error("店铺数据-短信发送记录用户昵称模糊查询<>再次<>加密失败!");
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
	 //工具类方法，添加方法在上一个TODO
}
