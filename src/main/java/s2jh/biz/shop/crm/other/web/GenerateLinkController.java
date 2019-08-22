package s2jh.biz.shop.crm.other.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import s2jh.biz.shop.crm.other.service.GenerateLinkService;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/GenerateLink")
public class GenerateLinkController {
	
	// 生成短链接
	@RequestMapping(value = "")
	public String generateLink(Model model, HttpServletRequest request,
			String linkType, String commodityId,String orderId,
			String activityUrl) throws ApiException {
		String link = GenerateLinkService.getLink(request, linkType,
				commodityId, activityUrl,orderId);
		model.addAttribute("link", link);
		return "crms/marketingCenter/memberMessageSend";
	}

}
