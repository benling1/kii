package s2jh.biz.shop.crm.expediting.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import s2jh.biz.shop.crm.expediting.entity.ExpeditingCase;
import s2jh.biz.shop.crm.expediting.service.ExpeditingCaseService;

@Controller
@RequestMapping(value="/expeditingCase")
public class ExpeditingCaseController {
	@Autowired
	private ExpeditingCaseService expeditingCaseService;
	
	//查询所有优秀催付案例
	@RequestMapping(value="",method=RequestMethod.GET)
	public String findAllExpeditingCase(Model model){
		List<ExpeditingCase> list = expeditingCaseService.findAllExpeditingCase();
		if(list!=null&&list.size()>0){
			model.addAttribute("expeditingCaseList", list);
		}
		return "";
	}
}
