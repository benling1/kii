package s2jh.biz.shop.crm.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/appraiseCauseSet")
public class AppraiseCauseSetController {

	@RequestMapping(value="/showAppraiseCauseSet")
	public String showAppraiseCauseSet(Model model){
		
		return "crms/appraise/appraiseCauseSet";
	}
	
}
