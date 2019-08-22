package s2jh.biz.shop.crm.message.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.google.gson.Gson;

import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.message.service.SmsTemplateService;
import s2jh.biz.shop.crm.other.entity.Festival;
import s2jh.biz.shop.crm.other.service.FestivalService;
import s2jh.biz.shop.utils.pagination.Pagination;

@Controller
@RequestMapping(value = "/SmsTemplate")
public class SmsTemplateController {
	@Autowired
	private SmsTemplateService smsTemplateService;

	@Autowired
	private FestivalService festivalService;

	/*// 根据条件查询短信模板信息（分页）
	@RequestMapping(value = "/findSmsTemplate")
	public String findSmsTemplate(Model model, HttpServletRequest request,
			Integer pageNo) {
		String type = request.getParameter("type");// 获取短信模板类型
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		// 获取当前日期和前一天日期，根据日期查询订单数据
		Calendar calendar = Calendar.getInstance();// 获取的是系统当前时间
		String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar
				.getTime());
		Festival fe = new Festival();
		if (type == null) {
			fe = festivalService.findFestival(todayDate);
			if (fe != null) {
				type = fe.getName();
			}
			model.addAttribute("show", 2);

		} else {
			if (type.equals("聚划算")) {
				model.addAttribute("show", 3);
			} else if (type.equals("上新")) {
				model.addAttribute("show", 4);
			} else if (type.equals("周年庆")) {
				model.addAttribute("show", 5);
			}
		}

		// 使用分页工具进行分页列表查询
		String contextPath = request.getContextPath();
		Pagination pagi = smsTemplateService.findAllSmsTemplate(pageNo,map);
		String url = contextPath + "/SmsTemplate/findSmsTemplate";
		StringBuilder params = new StringBuilder();
		if(type != null && !"".equals(type)){
			params.append("&type=").append(type);
		}
		pagi.pageView(url, params.toString());
		model.addAttribute("type", type);
		model.addAttribute("pagination", pagi);
		return "crms/marketingCenter/memberMessageSend";
	}*/
	
		// 根据条件查询短信模板信息(@auth:jackstraw_yu 使用ajax访问,列表展示)
		@RequestMapping(value = "/findSmsTemplate")
		public void findSmsTemplate(HttpServletRequest request,HttpServletResponse response,String type) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", type);
			// 获取当前日期和前一天日期，根据日期查询订单数据
			Calendar calendar = Calendar.getInstance();// 获取的是系统当前时间
			String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar
					.getTime());
			Festival festival = null;
			if(type ==null || "".equals(type)){
				festival  = festivalService.findFestival(todayDate);
			}
			List<SmsTemplate> smsTemplateList = smsTemplateService.findAllSmsTemplates(map);
			
			Gson gson = new Gson();
			/*String smsTemplates= gson.toJson(smsTemplateList);
			String festivalJ = gson.toJson(festival);*/
			HashMap<String, Object> hashMap = new HashMap<String ,Object>();
			hashMap.put("smsTemplateList", smsTemplateList);
			hashMap.put("festival", festival);
			
			
			response.setContentType("application/json;charset=UTF-8");
			try {
				response.getWriter().write(gson.toJson(hashMap));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
