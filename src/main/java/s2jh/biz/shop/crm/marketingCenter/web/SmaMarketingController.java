package s2jh.biz.shop.crm.marketingCenter.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "")
public class SmaMarketingController {
	
	/**
	 * 营销中心
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/crms/marketingCenter/marketingCenter",method = RequestMethod.GET)
	public String MarketingCenter(Model model){
		
		return "crms/marketingCenter/memberMessageSend";
		
	}
	

}
