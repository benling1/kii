package s2jh.biz.shop.crm.order.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import s2jh.biz.shop.crm.order.entity.RatedVo;
import s2jh.biz.shop.crm.order.service.TradeRatesService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

@Controller
@RequestMapping(value="")
public class MiddleBadAssessmentController {
	
	@Autowired
	private TradeRatesService tradeRatesService;
	
	@RequestMapping(value="/crms/appraise/middleBadAssessment")
	public String rangeQuery(HttpServletRequest request,String filtrateTime,String nowTime,
			Integer pageNo,String beginTime,String endTime,Model model){
		String ratedNick=request.getSession().getAttribute("taobao_user_nick").toString();
		//String nick="crzzyboy";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ratedNick", ratedNick);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		
		//获取当前时间
				Calendar calendar = Calendar.getInstance();
				
				//定义参数获取计算后的时间
				String countTime = "";
			
				//获取计算后的到期时间
				String dueTime ="";
				
				nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				if(filtrateTime.equals("最近7天")){
					calendar.add(Calendar.DATE, -6);
					countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
					beginTime = countTime.toString();
					endTime = nowTime.toString();
				}else if(filtrateTime.equals("最近15天")){
					calendar.add(Calendar.DATE, -14);
					countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
					beginTime = countTime.toString();
					endTime = nowTime.toString();
				}else if(filtrateTime.equals("最近一个月")){
					calendar.add(Calendar.MONTH, -1);
					countTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
					beginTime = countTime.toString();
					endTime = nowTime.toString();
				}else if(filtrateTime.equals("查询")){
					try {
						ArrayList<String> arrayList = getBetweenTwoDate(beginTime,endTime);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(pageNo == null){
					pageNo = 1;
				}
				String contextPath = request.getContextPath();
				/*Map<String, Object> map = new HashMap<String, Object>();
				map.put("nick", nick);*/
				Date  bTime = DateUtils.convertDate(countTime);//起始时间
				Date eTime = DateUtils.convertDate(nowTime);//结束时间
				
				Pagination pagination = tradeRatesService.findAggregatezcaggregate1(ratedNick, contextPath, beginTime, endTime, pageNo);
				//路径
				String url = contextPath + "/crms/appraise/middleBadAssessment";
				//封装params
				StringBuilder params = new StringBuilder();
				if(filtrateTime != null && !"".equals(filtrateTime)){
					params.append("&filtrateTime=").append(filtrateTime);
				}
				if(nowTime != null && !"".equals(nowTime)){
					params.append("&nowTime=").append(nowTime);
				}
				if(beginTime!=null && !"".equals(beginTime)){
					params.append("&beginTime=").append(beginTime);
				}
				if(endTime!=null && !"".equals(endTime)){
					params.append("&endTime=").append(endTime);
				}
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(beginTime!=null && !"".equals(beginTime)){
					try {
						bTime = dateFormat.parse(beginTime);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(endTime!=null && !"".equals(endTime)){
					try {
						eTime = dateFormat.parse(endTime);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				pagination.pageView(url, params.toString());
				
				//汇总查询
				RatedVo ratedVo = tradeRatesService.findAggregatezcaggregate(ratedNick);
				model.addAttribute("ratedVo", ratedVo);
				model.addAttribute("filtrateTime", filtrateTime);
				model.addAttribute("pagination", pagination);
				model.addAttribute("beginTime", bTime);
				model.addAttribute("endTime", eTime);
				
				
				
				//----------------------中差评折线图-------------
				
				/*ArrayList<String> arrayList = new ArrayList<String>();
				Calendar calendar1 = Calendar.getInstance();
			    if(filtrateTime.equals("最近7天")){
					calendar1.add(Calendar.DATE, -7);
					//拼装时间：2016-12-07,2017-12-06……
					for(int i =1;i<=7;i++){
						calendar1.add(Calendar.DATE, +1);
						String dataTime = new SimpleDateFormat("yyyy-MM-dd").format(calendar1.getTime());
						arrayList.add(dataTime);
					}
					
				}else if(filtrateTime.equals("最近15天")){
					calendar1.add(Calendar.DATE, -15);
					for(int i =1;i<=15;i++){
						calendar1.add(Calendar.DATE, +1);
						String dataTime = new SimpleDateFormat("yyyy-MM-dd").format(calendar1.getTime());
						arrayList.add(dataTime);
					}
				}else if(filtrateTime.equals("最近一个月")){
					calendar1.add(Calendar.MONTH, -1);
					String paramStartDate = "";
					String paramEndDate = "";

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					Date dateNow = new Date();
					Date dateBefore = new Date();

					Calendar cal = Calendar.getInstance();
					cal.setTime(dateNow);
					cal.add(Calendar.MONTH, -1);
					dateBefore = cal.getTime();
					paramEndDate = sdf.format(dateNow);
					paramStartDate = sdf.format(dateBefore);
					int spaceDay = 0;
					try {
						spaceDay = (int) ((sdf.parse(paramEndDate).getTime() -  sdf.parse(paramStartDate).getTime())
						        / (1000 * 60 * 60 * 24));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int i=1;i<=spaceDay;i++){
						calendar1.add(Calendar.DATE, +1);
						String dataTime = new SimpleDateFormat("yyyy-MM-dd").format(calendar1.getTime());
						arrayList.add(dataTime);
					}
					
				}else if(filtrateTime.equals("查询")){
					
					try {
						arrayList = getBetweenTwoDate(beginTime,endTime);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}	
				//调用service层查询出每天的中差评数据量
			    
			    	List<Map<String,Object>> mapList = tradeRatesService.findAggregatezcaggregate2(arrayList, bTime, eTime,ratedNick);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("dateTime", arrayList);
					jsonObject.put("dataValue", mapList);
					model.addAttribute("data", jsonObject.toJSONString());*/
				
		        return "crms/appraise/middleBadAssessment";
	}
	
	//自定義方法，获得两个字符串形式 的日期之间所有日期的集合
	  public  ArrayList<String> getBetweenTwoDate(String beginDate, String endDate) throws ParseException {    
		  
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		  ArrayList<String> arrayList = new ArrayList<String>();
		  arrayList.add(beginDate);
		  Calendar cal = Calendar.getInstance();
		    //使用给定的 Date 设置此 Calendar 的时间
		    cal.setTime(sdf.parse(beginDate));
		    while (true) {
		        //根据日历的规则，为给定的日历字段添加或减去指定的时间量
		        cal.add(Calendar.DAY_OF_MONTH, 1);
		        // 测试此日期是否在指定日期之后
		        if (sdf.parse(endDate).after(cal.getTime())) {
		        	arrayList.add(sdf.format(cal.getTime()));
		        } else {
		            break;
		        }
		    }
		    arrayList.add(endDate);;//把结束时间加入集合
		    return arrayList;
		}

}
