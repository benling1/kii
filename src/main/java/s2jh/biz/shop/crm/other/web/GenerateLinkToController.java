package s2jh.biz.shop.crm.other.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import s2jh.biz.shop.crm.other.service.GenerateLinkService;


@Controller
@RequestMapping(value = "/GenerateLinkTo")
public class GenerateLinkToController {
	
	// 生成短链接
	@RequestMapping(value = "generateLink")
	public String generateLink(HttpServletRequest request,
								String linkType, 
								String commodityId, 
								String activityUrl,String orderId,
								HttpServletResponse response) throws IOException{
		//创建对象封装参数,输出到前台
		JSONObject  json = new JSONObject();
		
		String link = GenerateLinkService.getLink(request, linkType,commodityId,activityUrl,orderId);
		if(link != null){
			json.put("link", link);
			json.put("message", true);
		}else{
			json.put("message", false);
		}
		
		//将封装好的json数据,输出到前台
		response.setContentType("application/json; charset=utf-8");  
		response.getWriter().write(json.toString());
		return null;
	}

}
