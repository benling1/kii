package s2jh.biz.shop.crm.message.web;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.manage.base.BaseComponent;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;

/** 
* @ClassName: MsgRecordController 
* @Description: (营销中心>>短信发送记录/群发记录) 
* @author jackstraw_yu 
* @date 2017年4月21日 下午6:24:27 
*  
*/
@Controller
@RequestMapping(value="/msgSendRecord")
public class MsgSendRecordController extends BaseController{
	
	
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	
	private static final Log logger = LogFactory.getLog(MsgSendRecordController.class);
	
	/** 
	* @Title: memberSendRecord 
	* @Description: (会员群发记录>>查询) 
	* @date 2017年4月24日 上午11:04:03 
	* @author jackstraw_yu 
	*/
	@RequestMapping(value="/memberSendRecord"/*,method= RequestMethod.POST*/)
	public String memberSendRecord(Model model,HttpServletRequest request,
			String beginTime,String endTime,String activityName,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if("".equals(beginTime)) beginTime=null;
		if("".equals(endTime)) endTime=null;
		Date bTime = DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm");
		Date eTime = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm");
		String msgType= MsgType.MSG_HYDXQF;
		String contextPath = request.getContextPath()+"/msgSendRecord/memberSendRecord";
		if(activityName!=null) activityName=activityName.trim();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", bTime);
		map.put("endTime", eTime);
		map.put("activityName", activityName);
		map.put("isSent", true);
		map.put("type", msgType);
		map.put("userId", userId);
		Pagination pagination = msgSendRecordService.sendRecordPagination(contextPath,pageNo,map);
		model.addAttribute("pagination",pagination);
		model.addAttribute("beginTime", bTime);
		model.addAttribute("endTime", eTime);
		model.addAttribute("activityName", activityName);
		return "crms/marketingCenter/messageSendHistory";
		
	}
	
	
	/** 
	* @Title: msgSendRecordLastMonth 
	* @Description: (会员短信群发>>上一个月的发送记录) 
	* @date 2017年4月24日 下午5:03:06 
	* @author jackstraw_yu 
	*/
	@RequestMapping(value="/memberSendRecordLastMonth"/*,method= RequestMethod.POST*/)
	public String memberSendRecordLastMonth(Model model,HttpServletRequest request,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		Map<String, Date> resultMap = caculateLasMonthDate();
		Date beginTime = resultMap.get("beginTime");
		Date endTime = resultMap.get("endTime");
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		String  type = MsgType.MSG_HYDXQF;
		String contextPath = request.getContextPath()+"/msgSendRecord/memberSendRecordLastMonth";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("isSent", true);
		map.put("type", type);
		map.put("userId", userId);
		Pagination pagination = msgSendRecordService.sendRecordPagination(contextPath,pageNo,map);
		model.addAttribute("pagination",pagination);
		return "crms/marketingCenter/messageSendHistory";
	}
	
	
	/** 
	* @Title: specificSendRecord 
	* @Description: (指定号码群发记录>>查询) 
	* @date 2017年4月24日 下午6:57:28 
	* @author jackstraw_yu 
	*/
	@RequestMapping(value="/specificSendRecord"/*,method= RequestMethod.POST*/)
	public String specificSendRecord(Model model,HttpServletRequest request,
			String beginTime,String endTime,String activityName,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if("".equals(beginTime)) beginTime=null;
		if("".equals(endTime)) endTime=null;
		Date bTime = DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm");
		Date eTime = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm");
		String msgType= MsgType.MSG_ZDHMQF;
		String contextPath = request.getContextPath()+"/msgSendRecord/specificSendRecord";
		if(activityName!=null) activityName=activityName.trim();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", bTime);
		map.put("endTime", eTime);
		map.put("activityName", activityName);
		map.put("isSent", true);
		map.put("type", msgType);
		map.put("userId", userId);
		Pagination pagination = msgSendRecordService.sendRecordPagination(contextPath,pageNo,map);
		model.addAttribute("pagination2",pagination);
		model.addAttribute("beginTime", bTime);
		model.addAttribute("endTime", eTime);
		model.addAttribute("activityName", activityName);
		model.addAttribute("show", 2);
		return "crms/marketingCenter/messageSendHistory";
		
	}
	
	/** 
	* @Title: specificSendRecordLastMonth 
	* @Description: (指定发送记录>>上一个月的记录) 
	* @date 2017年4月24日 下午7:07:28 
	* @author jackstraw_yu 
	*/
	@RequestMapping(value="/specificSendRecordLastMonth"/*,method= RequestMethod.POST*/)
	public String specificSendRecordLastMonth(Model model,HttpServletRequest request,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		Map<String, Date> resultMap = caculateLasMonthDate();
		Date beginTime = resultMap.get("beginTime");
		Date endTime = resultMap.get("endTime");
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		String  type = MsgType.MSG_ZDHMQF;
		String contextPath = request.getContextPath()+"/msgSendRecord/specificSendRecordLastMonth";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("isSent", true);
		map.put("type", type);
		map.put("userId", userId);
		Pagination pagination = msgSendRecordService.sendRecordPagination(contextPath,pageNo,map);
		model.addAttribute("pagination2",pagination);
		model.addAttribute("show", 2);
		return "crms/marketingCenter/messageSendHistory";
	}
	
	
	
	
	
	/** 
	* @Title: orderSendRecord 
	* @Description: (订单发送记录>>查询) 
	* @date 2017年4月24日 下午7:06:51 
	* @author jackstraw_yu 
	*/
	@RequestMapping(value="/orderSendRecord"/*,method= RequestMethod.POST*/)
	public String orderSendRecord(Model model,HttpServletRequest request,
			String beginTime,String endTime,String activityName,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if("".equals(beginTime)) beginTime=null;
		if("".equals(endTime)) endTime=null;
		Date bTime = DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm");
		Date eTime = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm");
		String msgType= MsgType.MSG_DDDXQF;
		String contextPath = request.getContextPath()+"/msgSendRecord/orderSendRecord";
		if(activityName!=null) activityName=activityName.trim();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", bTime);
		map.put("endTime", eTime);
		map.put("activityName", activityName);
		map.put("isSent", true);
		map.put("type", msgType);
		map.put("userId", userId);
		Pagination pagination = msgSendRecordService.sendRecordPagination(contextPath,pageNo,map);
		model.addAttribute("pagination3",pagination);
		model.addAttribute("beginTime", bTime);
		model.addAttribute("endTime", eTime);
		model.addAttribute("activityName", activityName);
		model.addAttribute("show", 3);
		return "crms/marketingCenter/messageSendHistory";
		
	}
	
	/** 
	* @Title: orderSendRecordLastMonth 
	* @Description:  (订单短信群发>>上一个月的记录) 
	* @date 2017年4月25日 下午3:59:55 
	* @author jackstraw_yu 
	*/
	@RequestMapping(value="/orderSendRecordLastMonth"/*,method= RequestMethod.POST*/)
	public String orderSendRecordLastMonth(Model model,HttpServletRequest request,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		Map<String, Date> resultMap = caculateLasMonthDate();
		Date beginTime = resultMap.get("beginTime");
		Date endTime = resultMap.get("endTime");
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		String  type = MsgType.MSG_DDDXQF;
		String contextPath = request.getContextPath()+"/msgSendRecord/orderSendRecordLastMonth";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("isSent", true);
		map.put("type", type);
		map.put("userId", userId);
		Pagination pagination = msgSendRecordService.sendRecordPagination(contextPath,pageNo,map);
		model.addAttribute("pagination3",pagination);
		model.addAttribute("show", 3);
		return "crms/marketingCenter/messageSendHistory";
	}
	
	
	/** 
	* @Title: deleteSendRecord 
	* @Description:  (删除短信记录:发送总记录&详情记录/隐藏处理) 
	* @date 2017年5月2日 下午4:49:01 
	* @author jackstraw_yu 
	*/
	@RequestMapping(value="/deleteSendRecord"/*,method= RequestMethod.POST*/)
	public @ResponseBody
	String deleteSendRecord(Model model,HttpServletRequest request,HttpServletResponse response,
			Integer recordId,String type){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("recordId", recordId);
		map.put("type", type);
		map.put("userId", userId);
		boolean flag = msgSendRecordService.deleteSendRecord(map);
		return rsMap(100, "").put("message", flag).toJson();
	}
	
	
	
	/** 
	* @Title: toSendRecord 
	* @Description:  (短信发送记录查询未发送的总记录) 
	* @date 2017年5月2日 下午4:58:17 
	* @author jackstraw_yu 
	*/
	@RequestMapping(value="/toSendRecord"/*,method= RequestMethod.POST*/)
	public String toSendRecord(Model model,HttpServletRequest request,
			String beginTime,String endTime,String activityName,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		if("".equals(beginTime)) beginTime=null;
		if("".equals(endTime)) endTime=null;
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Date bTime = DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm");
		Date eTime = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm");
		String contextPath = request.getContextPath()+"/msgSendRecord/toSendRecord";
		if(activityName!=null) activityName=activityName.trim();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", bTime);
		map.put("endTime", eTime);
		map.put("activityName", activityName);
		map.put("isSent", false);
		map.put("userId", userId);
		Pagination pagination = msgSendRecordService.sendRecordPagination(contextPath,pageNo,map);
		model.addAttribute("pagination4",pagination);
		model.addAttribute("beginTime", bTime);
		model.addAttribute("endTime", eTime);
		model.addAttribute("activityName", activityName);
		model.addAttribute("show", 4);
		return "crms/marketingCenter/messageSendHistory";
		
	}
	
	
	
	
	/** 
	* @Title: cancelSendRecord 
	* @Description:  (短信发送记录>>取消定时发送
	* 直接删除总记录?
	* 直接删除schedule记录?
	* ) 
	* @date 2017年5月6日 上午11:30:07 
	* @author jackstraw_yu 
	*/
	@RequestMapping(value="/cancelSendRecord"/*,method= RequestMethod.POST*/)
	public @ResponseBody 
	String cancelSendRecord(Model model,HttpServletResponse response,HttpServletRequest request,
							String type,Integer recordId,String status){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(status==null || "".equals(status.trim()) 
			|| !"5".equals(status.trim())
			|| userId==null || "".equals(userId))
			return rsMap(100, "").put("message", false).toJson();
		try {
			//删除定时总记录&要发送的 定时信息
			msgSendRecordService.removeMsgSchedule(userId,recordId,type);
			return rsMap(100, "").put("message", true).toJson();
		} catch (Exception e) {
			logger.error("取消定时数据异常!!..........."+e.getMessage());
			return rsMap(100, "").put("message", false).toJson();
		}
		
	}
	
	
	
	/**
	 * 获取群发短信的各种统计
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getEveryCount")
	public void getEveryCount(Model model,HttpServletRequest request,HttpServletResponse response,
			Integer recordId,String type){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		JSONObject js = new JSONObject();
		MsgSendRecord msg = msgSendRecordService.queryMsgSendRecord(userId,recordId,type);
		if(msg!=null){
			int length = 1;
			if(msg.getTemplateContent().length()>70)
				length = (msg.getTemplateContent().length()+66)/67;
			/**
			 * 1目标发送客户总数
			 * 2成功发送客户数
			 * 3计费条数
			 * 4手机号码不正确
			 * 5重复号码条数
			 * 6黑名单
			 * 7重复发送被屏蔽
			 * 8其他失败原因(短息接口返回失败)
			 * */
			//1目标发送客户总数---可查询msgSendRecord表
			js.put("totalCustom", msg.getTotalCount());
			//2成功发送客户数
			//js.put("successCustom", msg.getSucceedCount()-msg.getFailedCount());
			js.put("successCustom", msg.getSucceedCount());
			//3计费条数
			js.put("actualCount", msg.getSucceedCount()*length);
			//4手机号码不正确
			js.put("wrongNum", msg.getWrongCount());
			//5重复号码条数
			js.put("repeatNum", msg.getRepeatCount());
			//6黑名单
			js.put("blackCount", msg.getBlackCount());
			//7重复发送被屏蔽
			js.put("repeatSend", msg.getSheildCount());
			//8其他失败原因(短息接口返回失败)
			js.put("failedCount", msg.getFailedCount());
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	/**
	 * 获取上一个月的开始结束时间
	 * **/
	private Map<String,Date> caculateLasMonthDate(){
		Map<String,Date> map = new HashMap<String,Date>();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		int MaxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set( c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay, 23, 59, 59);
		Date endTime =c.getTime();
		map.put("endTime", endTime);
		int MinDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
		c.set( c.get(Calendar.YEAR), c.get(Calendar.MONTH), MinDay, 00, 00, 00);
		Date beginTime = c.getTime();
		map.put("beginTime", beginTime);
		return map;
		
	}
	
}
