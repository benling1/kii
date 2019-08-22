package s2jh.biz.shop.crm.taobao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.user.entity.RechargeMenu;
import s2jh.biz.shop.crm.user.service.RechargeMenuService;
import s2jh.biz.shop.crm.user.service.UserRechargeService;
import s2jh.biz.shop.utils.pagination.Pagination;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.ArticleItemViewUnit;
import com.taobao.api.domain.OrderConfirmQueryDto;
import com.taobao.api.request.FuwuPurchaseOrderConfirmRequest;
import com.taobao.api.request.FuwuSkuGetRequest;
import com.taobao.api.response.FuwuPurchaseOrderConfirmResponse;
import com.taobao.api.response.FuwuSkuGetResponse;

/**
 * @ClassName: RechargeController
 * @Description: TODO(支付宝接口处理)
 * @author:jackstraw_yu
 * @date 2016年12月19日
 *
 */
@Controller
@RequestMapping(value = "/taobao")
public class RechargeController {

	@Autowired
	private RechargeMenuService rechargeMenuService;
	
	@Autowired
	private UserRechargeService userRechargeService;
	
	@Autowired
	private JudgeUserUtil judgeUserUtil;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(RechargeController.class);
	/**
	 * @Title: toTaobao
	 * @Description: TODO(点击充值中心 跳转页面)
	 * @param @return 参数
	 * @return String 返回类型
	 * @author:jackstraw_yu
	 * @throws
	 */
	@RequestMapping(value = "/toRecharge")
	public String toTaobao(Model model) {
		
		//调用service查询出所有的充值项目列表==>不包含父级Mid
		List<RechargeMenu> rechargeMenuList = rechargeMenuService.queryRechargeMenuList();
		model.addAttribute("rechargeMenuList", rechargeMenuList);
		return "crms/taobao/recharge";
	}

	/**
	 * 获取充值服务code
	 */
	@RequestMapping(value = "/toPayUrl")
	public String toPayUrl(String itemCode,String superItemCode, HttpServletRequest request) {

		// 通过code获得token与买家昵称
		HttpSession session = request.getSession();
		String taobao_user_nick = (String) session.getAttribute("taobao_user_nick");
		String access_token = this.judgeUserUtil.getUserTokenByRedis(taobao_user_nick);
		/*String access_token = "70002100420d0f188054b997c77af8bd1033d14e1dad4672cb63373649043771f4a1d742706923219";
		String taobao_user_nick = "小白你什么都没看见哦";*/
	
		// 传入获得服务信息
		Map<String, Object> more = getMore(itemCode,superItemCode, taobao_user_nick,
				access_token);
		Long cycNum = (Long) more.get("cycNum");
		Long cycUnit = (Long) more.get("cycUnit");
		String sub_itemCode = (String) more.get("sub_itemCode");

		// 付款页面:如果信息丢失会导致程序异常,使用try...catch{}
		String path = null;
		boolean flag =true;
		try {
			path = getPath(sub_itemCode, cycNum, cycUnit);
		} catch (Exception e) {
			flag =false;
			this.logger.info("生成付款链接异常");
		}
		if(flag == false){
			return "redirect:/taobao/toRecharge";
		}else{
			return "redirect:" + path;
		}

	}

	/**
	 * 根据服务code获取具体的服务信息
	 * 
	 * @param itemCode
	 * @param taobao_user_nick
	 * @param token
	 * @return
	 */
	public Map<String, Object> getMore(String itemCode,String superItemCode,
			String taobao_user_nick, String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 3、根据内购服务编号获取服务信息
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
				taobaoInfo.appKey, taobaoInfo.appSecret);
		FuwuSkuGetRequest req = new FuwuSkuGetRequest();
		// 服务代码
		req.setArticleCode(superItemCode);
		req.setNick(taobao_user_nick);
		req.setAppKey(taobaoInfo.appKey);
		FuwuSkuGetResponse rsp = null;
		try {
			rsp = client.execute(req, token);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		if (rsp.getErrorCode() != null) {
			return map;
		}
		
		 List<ArticleItemViewUnit> articleItemViewUnits = rsp.getResult().getArticleItemViewUnits();
		 if(articleItemViewUnits!=null&&articleItemViewUnits.size()>0){
			 for(ArticleItemViewUnit article : articleItemViewUnits){
				 //与前端页面传入的子code对比
				 if(article.getItemCode() !=null && article.getItemCode().equals(itemCode)){
					 
					 Long cycNum = article.getCycNum();
					 Long cycUnit = article.getCycUnit();
					 String sub_itemCode = article.getItemCode();
					 
					 map.put("cycNum", cycNum);
					 map.put("cycUnit", cycUnit);
					 map.put("sub_itemCode", sub_itemCode);
				 }
			 }
		 }		 
		
		return map;
	}

	/**
	 * 根据服务信息生成付款页面
	 * 
	 * @param sub_itemCode
	 * @param cycNum
	 * @param cycUnit
	 * @return
	 */
	public String getPath(String sub_itemCode, Long cycNum, Long cycUnit) {
		// 4、生成付款页面
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
				taobaoInfo.appKey, taobaoInfo.appSecret);
		FuwuPurchaseOrderConfirmRequest req = new FuwuPurchaseOrderConfirmRequest();
		OrderConfirmQueryDto obj1 = new OrderConfirmQueryDto();
		obj1.setAppKey(taobaoInfo.appKey);
		obj1.setItemCode(sub_itemCode);
		if(cycUnit!=null){
			obj1.setCycUnit(cycUnit.toString());
		}
		if(cycNum!=null){
			obj1.setCycNum(cycNum.toString());
		}		
		req.setParamOrderConfirmQueryDTO(obj1);
		FuwuPurchaseOrderConfirmResponse rsp = null;
		try {
			rsp = client.execute(req);
			this.logger.info("用户付款链接生成：" + rsp.getBody());
		} catch (Exception e) {
			logger.error("....................................RechargeController.getPath():Exception"+e.getMessage());
		}
		String url = rsp.getUrl();
		logger.info("....................................RechargeController.getPath():path"+url);
		return url;
	}

	
	/**
	* @Title: rechargeRecord
	* @Description: TODO(充值记录查询)
	* @param @param model
	* @param @return    参数
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value = "/rechargeRecord")
	public String rechargeRecord(Model model,HttpServletRequest request
			,@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo) {
		
		String taobao_user_nick = (String) request.getSession().getAttribute("taobao_user_nick");
		//获取访问路径
		String contextPath = request.getContextPath();
		contextPath +="/taobao/rechargeRecord";
		
		Pagination pagination = userRechargeService.queryPagination(taobao_user_nick,contextPath,pageNo);
		model.addAttribute("pagination", pagination);
		//显示充值页面
		model.addAttribute("show", 1);
		
		return "crms/taobao/recharge";
	}
}
