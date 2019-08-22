package s2jh.biz.shop.crm.systemAnnouncement.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2jh.biz.shop.crm.systemAnnouncement.entity.SystemAnnouncement;
import s2jh.biz.shop.crm.systemAnnouncement.service.SystemAnnouncementService;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;

@Controller
@RequestMapping(value = "")
public class SystemAnnouncementController {
	
	@Autowired
	private SystemAnnouncementService systemAnnouncementService;
	
	/**
	 * 获取系统消息，并跳转到系统消息页面
	 * 邱洋 2016-12-30 10:50
	 * @return 
	 */
	@RequestMapping(value="/systemMessage")
	public String systemAnnouncement(Model model,HttpServletRequest request,
			@RequestParam(required = false,defaultValue="1")Integer pageNo){
		
		String taobao_user_nick = (String) request.getSession().getAttribute("taobao_user_nick");
		
		//List<SystemAnnouncement> list = systemAnnouncementService.findAll(taobao_user_nick);
		//model.addAttribute("list",list);
		
		String contextPath = request.getContextPath();
		contextPath +="/systemMessage";
		Pagination pagination = systemAnnouncementService.queryPagination(taobao_user_nick,contextPath,pageNo);
		model.addAttribute("pagination", pagination);
		
		return "crms/systemAnnouncement/systemAnnouncement";
	}
	
	
	/**
	* @Title: remarkOne
	* @Description: TODO(单个公告标记为已读)
	* @param @param model
	* @param @param request
	* @param @return    参数
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/remarkOne")
	public void remarkOne(HttpServletRequest request, HttpServletResponse response,Integer id){
		String taobao_user_nick = (String) request.getSession().getAttribute("taobao_user_nick");
		
		//添加操作日志
		UserOperationLog userOperationLog = new UserOperationLog();
		userOperationLog.setUserId(taobao_user_nick);
		userOperationLog.setCreatedBy(taobao_user_nick);
		userOperationLog.setCreatedDate(new Date());
		//ip地址
		userOperationLog.setIpAdd(getIpAddress.getIpAddress(request));
		
		JSONObject js = new JSONObject();
		try {
			systemAnnouncementService.remarkOne(id,taobao_user_nick,userOperationLog);
			js.put("message", true);
		} catch (Exception e) {
			
				js.put("message", false);
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	* @Title: remarkAll
	* @Description: TODO(当前列表页的公告标记为已读)
	* @param @param model
	* @param @param request
	* @param @return    参数
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/remarkAll")
	public String remarkAll(HttpServletRequest request,Integer[] ids ){
		
		String taobao_user_nick = (String) request.getSession().getAttribute("taobao_user_nick");
		
		//添加操作日志
		UserOperationLog userOperationLog = new UserOperationLog();
		userOperationLog.setUserId(taobao_user_nick);
		userOperationLog.setCreatedBy(taobao_user_nick);
		userOperationLog.setCreatedDate(new Date());
		//ip地址
		userOperationLog.setIpAdd(getIpAddress.getIpAddress(request));
		
		systemAnnouncementService.remarkAll(ids,taobao_user_nick,userOperationLog);
		
		//重定向
		return "redirect:/systemMessage";
	}
}
