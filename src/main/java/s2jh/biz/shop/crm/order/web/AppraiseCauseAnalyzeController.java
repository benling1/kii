package s2jh.biz.shop.crm.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/appraiseCauseAnalyze")
public class AppraiseCauseAnalyzeController {

	@RequestMapping(value="/showAppraiseCauseAnalyze")
	public String showAppraiseCauseAnalyze(Model model){
		
		return "crms/appraise/appraiseCauseAnalyze";
	}
	
}
