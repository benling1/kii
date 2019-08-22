package s2jh.biz.shop.crm.home.support.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.OrderRateCareSetupService;
import s2jh.biz.shop.crm.order.service.OrderSetupService;

@Controller
@RequestMapping(value = "/top")
public class TopOfOpenOrCloseController {
	
	@Autowired
	private OrderSetupService orderSetupService;
	
	@Autowired
	private OrderRateCareSetupService orderRateCareSetupService;

	/**
	 * 查询orderSetup的status状态
	 * helei 2017年3月2日下午5:01:39
	 * @throws IOException 
	 */
	@RequestMapping(value="/findOrderSetupOfStatus")
	@ResponseBody
	public Map<String, Object> findOrderSetupOfStatus(HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		//卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		/*userId = "小白你什么都没看见哦";*/
		//创建map封装参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		
		List<OrderSetup> list = orderSetupService.findOrderSetupOfStatus(map);
		
		OrderRateCareSetup orderRate  = orderRateCareSetupService.findOrderRateOfStatus(map);
		
		//创建对象json对像封装参数,输出到前台
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Long orderRateId = null;
		String orderRateStatus = "";
		if(orderRate != null){
			orderRateId = orderRate.getId();
			orderRateStatus = orderRate.getStatus();
		}
		resultMap.put("list", list);
		resultMap.put("orderRateId", orderRateId);
		resultMap.put("orderRateStatus", orderRateStatus);
		return resultMap;
	}
	
	
	/**
	 * 顶部设置的开启或关闭状态
	 * helei 2017年3月2日下午4:32:46
	 */
	@RequestMapping(value="/openOrCloseStatus" ,method=RequestMethod.POST)
	public String openOrCloseStatus(String buttonId,String orderSetupId,String flag,HttpServletResponse response) throws IOException{
		
		//判断是否有次标识,存在就修改中差评安抚的开启关闭状态
		if(flag!=null && flag.equals("pacify")){
			OrderRateCareSetup orderRate = new OrderRateCareSetup();
			orderRate.setId(Long.parseLong(orderSetupId));
			orderRate.setStatus(buttonId); 
			orderRate.setLastModifiedDate(new Date());
			//创建对象封装参数,输出到前台
			JSONObject  json = new JSONObject();
			try {
				orderRateCareSetupService.updateOrderRateStatus(orderRate);
				json.put("status", buttonId);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				//将封装好的json数据,输出到前台
				response.setContentType("application/json; charset=utf-8");  
				response.getWriter().write(json.toString());
			}

		}else{
			OrderSetup orderSetup = new OrderSetup();
			orderSetup.setId(Long.parseLong(orderSetupId));
			orderSetup.setStatus(buttonId); 
			orderSetup.setLastModifiedDate(new Date());
			//创建对象封装参数,输出到前台
			JSONObject  json = new JSONObject();
			try {
				orderSetupService.updateOrderSetupStatus(orderSetup);
				json.put("status", buttonId);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				//将封装好的json数据,输出到前台
				response.setContentType("application/json; charset=utf-8");  
				response.getWriter().write(json.toString());
			}
			
		}
		
		
		
		
		return null;
	}
}
