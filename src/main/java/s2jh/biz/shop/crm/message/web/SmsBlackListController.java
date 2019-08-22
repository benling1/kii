package s2jh.biz.shop.crm.message.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import s2jh.biz.shop.crm.message.entity.SmsBlacklist;
import s2jh.biz.shop.crm.message.service.SmsBlackListService;

@Controller
@RequestMapping(value = "/smsBlackList")
public class SmsBlackListController {
	@Autowired
	private SmsBlackListService smsBlackListService;
	
	//根据卖家编号，查询所有黑名单信息
	@RequestMapping(value="",method = RequestMethod.GET)
	public String findAllInfo(ModelMap model,HttpServletRequest request){
		String userId= (String) request.getSession().getAttribute("taobao_user_nick");//userid从session里获取
		//测试数据，用户的编号从session里获取
		List<SmsBlacklist> s =smsBlackListService.findAllSmsBlacklist(userId);
		if(s!=null&&s.size()>0){
			model.addAttribute("blacklist",s);
		}		
		return "crms/storeData/kehuguanli";
	}
	
	//根据用户选择的手机号，创建时间，备注查询黑名单信息	
	@RequestMapping(value="conditionQuery" ,method=RequestMethod.GET)
	public String conditionQuery(ModelMap model,HttpServletRequest request){
		//测试数据，这四个变量的数据从前端页面获取
		String startdate ="2016-11-08";
		String enddate ="2016-11-08";
		String phone =null;
		String remarks = null;
		String userId= (String) request.getSession().getAttribute("taobao_user_nick");//userid从session里获取
		
		List<SmsBlacklist> list = smsBlackListService.conditionQuery(userId,phone, remarks, startdate, enddate);
		if(list!=null&&list.size()>0){
			model.addAttribute("blacklist",list);
		}		
		return "crms/storeData/kehuguanli";
	}	
}
