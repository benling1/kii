package s2jh.biz.shop.crm.message.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/smsSendInfo")
public class SmsSendInfoController {

//	@Autowired
//	private SmsSendInfoService smsSendInfoService;
//
//	// 根据卖家编号查询会员短信发送记录
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public String findAllSmsSendInfo(ModelMap model, HttpServletRequest request) {
//		String userId = (String) request.getSession().getAttribute(
//				"taobao_user_nick");
//		List<SmsSendInfo> list = smsSendInfoService
//				.findSmsSendInfo(userId, "0");
//		if (list != null && list.size() > 0) {
//			model.addAttribute("smsSendInfolist", list);
//		}
//		return "crms/storeData/kehuguanli";
//	}
//
//	// 根据卖家条件查询短信发送记录
//	@RequestMapping(value = "/querySmsSendInfo", method = RequestMethod.GET)
//	public String querySmsSendInfo(ModelMap model) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("phone", "18001300589");
//		map.put("isMember", "0");
//		List<SmsSendInfo> list = smsSendInfoService.querySmsSendInfo(map);
//		if (list != null && list.size() > 0) {
//			model.addAttribute("smsSendInfolist", list);
//		}
//		return "crms/storeData/kehuguanli";
//	}

}
