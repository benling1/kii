package s2jh.biz.shop.crm.seller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/GradeRuleController")
public class GradeRuleController {
	
	//跳转到会员分组页面
	@RequestMapping(value="")
	public String jumpGradeRule(){
		return "crms/customerManagement/customerGrade";
	}
}
