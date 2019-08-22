package s2jh.biz.shop.crm.message.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
import s2jh.biz.shop.crm.message.entity.SmsHistoryTemplate;
import s2jh.biz.shop.crm.message.service.SmsHistoryTemplateService;
import s2jh.biz.shop.utils.pagination.Pagination;

@Controller
@RequestMapping(value="/historyTemplate")
public class SmsHistoryTemplateController {
	@Autowired
	private SmsHistoryTemplateService smsHistoryTemplateService;
	
	/*//根据卖家编号查询所有模板使用信息
	@RequestMapping(value="/findAllList")
	public String findAllList(HttpServletRequest request,Integer pageNo,Model model){
		HttpSession session = request.getSession();
		String userId= (String) session.getAttribute("taobao_user_nick");

		//使用分页工具进行分页列表查询
		String contextPath = request.getContextPath();
		Pagination pagi= smsHistoryTemplateService.findSmsHistoryTemplate(userId,contextPath,pageNo);
		model.addAttribute("show",6);
		model.addAttribute("pagi",pagi);
		return "crms/marketingCenter/memberMessageSend";
	}*/
	
		
		/**
		* @Title: findAllList
		* @Description: TODO(根据卖家编号查询所有模板使用信息,使用Ajax异步访问)
		* @param @param request
		* @param @param pageNo
		* @param @param model
		* @param @return    参数
		* @author:jackstraw_yu
		* @throws
		*/
		@RequestMapping(value="/findAllList")
		public void findAllList(String type,HttpServletRequest request,Integer pageNo,HttpServletResponse response){
			HttpSession session = request.getSession();
			String userId= (String) session.getAttribute("taobao_user_nick");

			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("type", type);
			
			List<SmsHistoryTemplate> smsHistoryTemplates= smsHistoryTemplateService.findListAll(map);
			
			Gson gson = new Gson();
			String smsHistoryTemplatesJ = gson.toJson(smsHistoryTemplates);
			
			response.setContentType("application/json;charset=UTF-8");
			try {
				response.getWriter().write(smsHistoryTemplatesJ);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
}
