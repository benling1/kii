package s2jh.biz.shop.crm.seller.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.message.entity.SmsMobile;
import s2jh.biz.shop.crm.message.service.SmsMobileService;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserOperationLogService;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;

@Controller
@RequestMapping(value = "")
public class BlackManagermentController {

	@Autowired
	private SmsMobileService smsMobileService;

	@Autowired
	private UserOperationLogService userOperationLogService;

	/**
	 * 手机号黑名单添加
	 * 
	 * @param SmsMobile
	 * @param redirectAttributes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/addSmsBlackMobile", method = RequestMethod.POST)
	public void addMobile(SmsMobile smsMobile, HttpServletResponse response,
			HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute(
				"taobao_user_nick");

		JSONObject js = new JSONObject();

		// 根据手机号和判断是否
		List<SmsMobile> list = new ArrayList<SmsMobile>();
		List<String> mobileList = new ArrayList<String>();

		if (smsMobile != null && smsMobile.getMobile() != null) {
			mobileList.add(smsMobile.getMobile());
			list = smsMobileService.findSmsBlacklist(userId, mobileList);
		}
		if(list!=null&&list.size()>0){
			js.put("message", false);
			js.put("content", "该手机号已经存在黑名单中！");
			response.setContentType("application/json;charset=UTF-8");
			try {
				response.getWriter().write(js.toJSONString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		try {

			// smsMobileService.addSmsMobile(smsMobile,request);
			smsMobileService.addSmsMobile(smsMobile, request, userId);
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
	 * 分页+查询
	 * 
	 * @param SmsMobile
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/crms/customerManagement/blacklistManagemen")
	public String blackmanagerment(Model model, String mobile,
			String beginTime, String endTime, String remark, Integer pageNo,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute(
				"taobao_user_nick");
		/* String userId= "crazyboy"; */
		/*
		 * map.put("userId", userId); map.put("mobile", mobile);
		 * map.put("beginTime", beginTime); map.put("endTime", endTime);
		 * map.put("remark", remark);
		 */
		Date bTime = null;// 起始时间
		Date eTime = null;// 结束时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime != null && !"".equals(beginTime)) {
			try {
				bTime = dateFormat.parse(beginTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (endTime != null && !"".equals(endTime)) {
			try {
				eTime = dateFormat.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String contextPath = request.getContextPath();
		Pagination pagination = smsMobileService.findSmsMobileBlack(userId,
				contextPath, beginTime, endTime, pageNo, remark, mobile);
		model.addAttribute("pagination", pagination);
		model.addAttribute("smsMobile", new SmsMobile());
		model.addAttribute("mobile", mobile);
		model.addAttribute("endTime", eTime);
		model.addAttribute("beginTime", bTime);
		model.addAttribute("remark", remark);
		return "crms/customerManagement/blacklistManagemen";

	}

	/**
	 * 客户管理 移除手机黑名单
	 * 
	 * @param SmsMobile
	 * @param redirectAttributes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/smsMobileRemove", method = RequestMethod.POST)
	public void smsMobileRemove(SmsMobile smsMobile, Model model,
			HttpServletResponse response, Integer id, HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute(
				"taobao_user_nick");
		// String userId= "crazyboy";
		JSONObject js = new JSONObject();
		try {

			smsMobileService.updateisDelete(id);
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

		UserOperationLog op = new UserOperationLog();
		op.setUserId(userId);
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setDate(new Date());
		op.setFunctions("移出手机黑名单");
		op.setType("移除");
		op.setRemark("客户管理，移除手机黑名单");
		op.setState("成功");
		userOperationLogService.save(op);

	}
}
