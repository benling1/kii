package s2jh.biz.shop.crm.order.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lab.s2jh.core.entity.Pageination;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.order.service.OrderRateCareSetupService;
import s2jh.biz.shop.crm.order.service.SmsSendRecordToAppraiseService;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.getIpAddress;

@Controller
@RequestMapping(value="/appraiseMonitoring")
public class AppraiseMonitoringController {
	@Autowired 
	private SmsSendRecordToAppraiseService appraiseMonitoringService;
	
	@Autowired
    private UserInfoService userInfoService;
	
	@Autowired
	private OrderRateCareSetupService orderRateCareSetupService;
	
	/**
	 * 查询中差评监控设置数据
	 * helei 2017年1月3日下午6:27:16
	 */
	@RequestMapping(value="/showAppraiseMonitoring")
	public String showAppraiseMonitoring(HttpServletRequest request, String saveFlag,Model model){
		//.卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		
		//测试数据//===================
		//String userId = "crzzyboy";
		String appraiseType = "1";

		OrderRateCareSetup orderRateCareSetup = orderRateCareSetupService.findOrderRateCareSetup(userId,appraiseType);
		
		model.addAttribute("orderRateCareSetup", orderRateCareSetup);
		model.addAttribute("saveFlag", saveFlag);
		return "crms/appraise/appraiseMonitoring";
	}

	/**
	 * 查看发送记录
	 * helei 2017年1月6日下午4:50:50
	 */
	@RequestMapping(value="/queryAppraiseMonitoring")
	public String queryAppraiseMonitoring(String bTime,String eTime,Integer pageNo,String flag,HttpServletRequest request,Model model){
		
		//卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		//测试数据//======================
		//String userId = "crzzyboy";
		
		if(pageNo == null){
			pageNo = 1;
		}
		String contextPath = request.getContextPath();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bTime", DateUtils.convertStringToDate(bTime));
		map.put("eTime", DateUtils.convertStringToDate(eTime));
		map.put("userId", userId);
		map.put("type", 20);
		Pageination<SmsRecordDTO> pagination = appraiseMonitoringService.findPaginationByCondition(pageNo, map);
		//路径
		String url = contextPath + "/appraiseMonitoring/queryAppraiseMonitoring";
		//封装params
		StringBuilder params = new StringBuilder();
		if(bTime != null && !"".equals(bTime)){
			params.append("&bTime=").append(bTime);
		}
		if(eTime != null && !"".equals(eTime)){
			params.append("&eTime=").append(eTime);
		}
		if(flag != null && !"".equals(flag)){
			params.append("&flag=").append(flag);
		}
		pagination.pageView(url, params.toString());
		
		//将数据回显页面
		model.addAttribute("bTime", bTime);
		model.addAttribute("eTime", eTime);
		model.addAttribute("flag", flag);
		model.addAttribute("pagination", pagination);
		
		return "crms/appraise/appraiseMonitoring";
	}
	
	
	/**
	 * 中差评监控设置
	 * helei 2017年1月3日下午4:52:26
	 */
	@RequestMapping(value="/saveOrUpdateMonitoring")
	public String saveOrUpdateMonitoring(RedirectAttributes attr,OrderRateCareSetup orderRateCareSetup,HttpServletRequest request,Model model){
		//.卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		
		//测试数据//===================
		//String userId = "crzzyboy";
		
		//定义保存成功标识
		String saveFlag = "";
		
		//创建日志对象
		UserOperationLog op = new UserOperationLog();
		
		//判断id是否为空
		if(orderRateCareSetup.getId() != null && !"".equals(orderRateCareSetup.getId())){
			try {
				orderRateCareSetup.setLastModifiedDate(new Date());
				orderRateCareSetupService.updateOrderRateCareSetup(orderRateCareSetup);
				op.setState("成功");
				saveFlag = "success";
			} catch (Exception e) {
				op.setState("失败");
				saveFlag = "error";
			}finally{
				/**
				 * 添加操作日志
				 */
				//操作日志数据补全
				op.setFunctions("中差评监控");
				op.setType("修改");
				op.setDate(new Date());
				op.setRemark("中差评监控设置");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(userId);
				op.setFunctionGens("20");
				//调用日志方法添加日志
				orderRateCareSetupService.saveUserOperationLog(op);
			}
		}else{
			//补全数据
			orderRateCareSetup.setUserId(userId);
			orderRateCareSetup.setAppraiseType("1");
			orderRateCareSetup.setCreatedDate(new Date());
			
			try {
				//添加基本信息
				orderRateCareSetupService.saveOrderRateCareSetup(orderRateCareSetup);
				op.setState("成功");
				saveFlag = "success";
			} catch (Exception e) {
				op.setState("失败");
				saveFlag = "error";
			}finally{
				
				/**
				 * 添加操作日志
				 */
				//操作日志数据补全
				op.setFunctions("中差评监控");
				op.setType("添加");
				op.setDate(new Date());
				op.setRemark("中差评监控设置");
				op.setIpAdd(getIpAddress.getIpAddress(request));
				op.setUserId(userId);
				op.setFunctionGens("20");
				//调用日志方法添加日志
				orderRateCareSetupService.saveUserOperationLog(op);
			}
		}
		attr.addAttribute("saveFlag", saveFlag);
		String sellerNick = (String) request.getSession().getAttribute("taobao_user_nick");
		this.userInfoService.addUserPermitByMySql(sellerNick, null);
		return "redirect:showAppraiseMonitoring";
	}
}
