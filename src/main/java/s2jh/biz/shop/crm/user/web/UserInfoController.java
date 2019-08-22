/**
 * 
 */
package s2jh.biz.shop.crm.user.web;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.data.entity.ShopDataStatistics;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.user.service.UserInfoService;

/** 
 * @Title: 获取用户信息控制器
 * @date 2017年2月20日--下午2:21:03 
 * @param     设定文件 
 * @return 返回类型 
 * @throws 
 */
@Controller
@RequestMapping(value = "/getUserInfo")
public class UserInfoController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private TradeInfoService tradeInfoService;
	
	/**
	  * 创建人：邱洋
	  * @Title: 获取短信剩余条数
	  * @date 2017年2月20日--下午2:27:13 
	  * @return String
	  * @throws
	 */
	@RequestMapping(value="/getSmsNum")
	public String getUserInfo(PrintWriter writer,HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//查询短信条数
		Long smsNum =this.userAccountService.findUserAccountSms(userId);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("smsNum", smsNum);
		writer.print(jsonObj);
		return null;
	}
	
	/**
	  * 创建人：邱洋
	  * @Title: 页面点击引导页面后获取首页数据
	  * @date 2017年3月28日--下午12:18:48 
	  * @return String
	  * @throws
	 */
	@RequestMapping(value="/getDate")
	public String getDate(HttpServletRequest request,Model model){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//昨日??
		Map<String, Date> yMap = DateUtils.caculateDate(-1);
		List<ShopDataStatistics> shopDateStatistics  = tradeInfoService.getTradeDataByDate(yMap,userId);
		model.addAttribute("shopDateStatistics", shopDateStatistics);
		//前日??
		Map<String, Date> bYMap = DateUtils.caculateDate(-2);
		List<ShopDataStatistics> beforeYestedayDate  = tradeInfoService.getTradeDataByDate(bYMap,userId);
		model.addAttribute("BeforshopDateStatistics", beforeYestedayDate);
//		try {
//			getUserCode.getData(model, userId, request);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		return "crms/home/index";
	}
	
	/**
	 * 根据用户名修改店铺名称（短信签名）
	 * @param request
	 * @param model
	 * @param writer
	 * @return
	 */
	@RequestMapping(value="/updateShopName")
	public String updateUserShopName(HttpServletRequest request,Model model,PrintWriter writer){
		HttpSession session = request.getSession();
		//获取用户昵称
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//获取修改的店铺名称（短信签名）
		String shopName = request.getParameter("shopName");
		String oldShopName = (String) session.getAttribute("ShopName");
		Map <String,Object> map = new HashMap<String,Object>();
		
		map.put("userId", userId);
		map.put("shopName", shopName);
		int x = userInfoService.updateShopName(map);
		
		JSONObject jsonObj = new JSONObject();
		if(x>0){
			jsonObj.put("success", true);
			jsonObj.put("oldShopName", oldShopName);
			// 将用户信息放入session中
			session.setAttribute("ShopName", shopName);
		}else{
			jsonObj.put("success", false);
		}
		writer.print(jsonObj);
		return null;
	}
	
}
