package s2jh.biz.shop.crm.seller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab.s2jh.core.util.DateUtils;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taobao.api.SecretException;

import s2jh.biz.shop.crm.buyers.service.BuyerGroupInfoService;
import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.buyers.web.MemberInfoController;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.vo.MemberCriteriaVo;
import s2jh.biz.shop.crm.order.service.OrderService;
import s2jh.biz.shop.crm.seller.entity.SellerGroup;
import s2jh.biz.shop.crm.seller.entity.SellerGroupRule;
import s2jh.biz.shop.crm.seller.service.SellerGroupRuleService;
import s2jh.biz.shop.crm.seller.service.SellerGroupService;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;

@Controller
@RequestMapping(value = "/sellerGroup")
public class SellerGroupController {

	@Autowired
	private SellerGroupService sellerGroupService;

	@Autowired
	private SellerGroupRuleService sellerGroupRuleService;

	@Autowired
	private MemberInfoService memberInfoService;

	@Autowired
	private BuyerGroupInfoService buyerGroupInfoService;

	@Autowired
	private UserOperationLogDao userlog;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private VipMemberService vipMemberService;

	@Autowired
	private MemberInfoController memberInfoController;
	
	/**
	 * 跳转到会员分组页面,并查询卖家所有用户分组(分页)
	 */
	@RequestMapping(value = "/findSellerGroup")
	public String findMemberGroup(Model model, HttpServletRequest request,
			Integer pageNo) {
// 		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		/* String userId = "小白你什么都不知道哦"; */
		String memberType = request.getParameter("memberType");
		//暂时注释掉,愿世界和平,代码没有坑,阿门!
		// 判断有没有默认分组，没有则添加      
		/*List<SellerGroup> list = sellerGroupService
				.findDefaultSellerGroup(userId);
		if (list.size() <= 0) {
			sellerGroupService.addDefaultGroup(userId);
		}*/
		// 使用分页工具进行分页列表查询
		String contextPath = request.getContextPath();
		Pagination pagi = sellerGroupService.findAllSellerGroup(userId,
				contextPath, pageNo, memberType);
		if (memberType != null && memberType != "") {
			model.addAttribute("memberType", memberType);
		} else {
			model.addAttribute("memberType", "0");
		}
		// 会员分组添加成功后，跳转的页面
		int pageNomax = 0;
		int count = pagi.getTotalCount();
		
		// 每页显示条数
		int currentRows = 10;
		if (count % currentRows == 0) {
			pageNomax = (count + (currentRows - 1)) / currentRows + 1;
		} else {
			pageNomax = (count + (currentRows - 1)) / currentRows;
		}
		model.addAttribute("pageNoMax", pageNomax);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pagi", pagi);
		return "crms/customerManagement/memberGrouping";
//		
	}
	
	
	/**
	 * 根据分组编号删除分组
	 * 
	 */
	@SuppressWarnings({ "static-access" })
	@RequestMapping(value = "/delSellerGroup", method = RequestMethod.POST)
	public String delSellerGroup(Integer groupId, PrintWriter writer,
			HttpServletRequest request,Integer ruleId) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		getIpAddress getip = new getIpAddress();
		String ip = getip.getIpAddress(request);
		int date = sellerGroupService.deleteSellerGroup(groupId);
		JSONObject jsonObj = new JSONObject();
		UserOperationLog log = new UserOperationLog();
		if (date == 1) {
			jsonObj.put("success", true);
			jsonObj.put("info", "删除数据成功!");
			log.setState("成功");
		} else {
			log.setState("失败");
		}
		log.setRemark("删除会员分组");
		log.setUserId(userId);
		log.setFunctions("会员分组");
		log.setType("删除");
		log.setDate(new Date());
		log.setIpAdd(ip);
		userlog.save(log);
		
		sellerGroupRuleService.delSellerGroupRule(ruleId);
		
		writer.print(jsonObj);
		return null;
	}

	/**
	 * 添加会员分组
	 * 
	 */
	@RequestMapping(value = "/addSellerGroup", method = RequestMethod.POST)
	public String addSellerGroupRule(PrintWriter writer,
			HttpServletRequest request, String tradeStatus, String tradeNum,
			String accumulatedStatus, String averagePrice, String tradeDays,
			String tradeType,String minTradeNum, String maxTradeNum,
			String minAccumulatedAmount, String maxAccumulatedAmount,
			String minAveragePrice, String maxAveragePrice, String groupName,
			String remarks, String province,  String itemIds) {
		MemberCriteriaVo mcr = new MemberCriteriaVo();
		Long groupId = null;
		// 卖家编号
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");

		// 查询是否存在该会员分组
		List<SellerGroup> list = sellerGroupService.existenceSellerGroupInfo(userId,groupName);
		if (list != null&&list.size()>0) {
			JSONObject jsonObj = new JSONObject();
			// 添加修改分组规则操作日志
			jsonObj.put("success", true);
			jsonObj.put("info", "会员分组名称重复!");
			writer.print(jsonObj);
			return null;
		}
		// 获取操作人的ip地址
		String ip = getIpAddress.getIpAddress(request);

		
		// 根据平均客单价条件设置最小、最大平均客单价
		if (averagePrice != null && !averagePrice.equals("")) {
			if (averagePrice.equals("1~100")) {
				minAveragePrice = "1";
				maxAveragePrice = "100";
			} else if (averagePrice.equals("100~200")) {
				minAveragePrice = "100";
				maxAveragePrice = "200";
			} else if (averagePrice.equals("200~300")) {
				minAveragePrice = "200";
				maxAveragePrice = "300";
			}
		}
		// 根据累计金额条件设置最小、最大累计金额
		if (accumulatedStatus != null && accumulatedStatus != "") {
			if (accumulatedStatus.equals("1~100元")) {
				minAccumulatedAmount = "1";
				maxAccumulatedAmount = "100";
			} else if (accumulatedStatus.equals("100~200元")) {
				minAccumulatedAmount = "100";
				maxAccumulatedAmount = "200";
			} else if (accumulatedStatus.equals("200~300元")) {
				minAccumulatedAmount = "200";
				maxAccumulatedAmount = "300";
			}
		}
		//添加交易次数查询条件
		if(minTradeNum!=null&&!"".equals(minTradeNum)&&maxTradeNum!=null&&!"".equals(maxTradeNum)){
			mcr.setMinTradeNum(Integer.parseInt(minTradeNum));
			mcr.setMaxTradeNum(Integer.parseInt(maxTradeNum));
		}else if(tradeNum != null&&!"".equals(tradeNum)){
			if (tradeNum.equals("3次以上")) {
				mcr.setTradeNumByTimes("4");
				mcr.setMinTradeNum(4);
			} else if (!tradeNum.equals("0")) {
				mcr.setMinTradeNum(Integer.parseInt(tradeNum));
				mcr.setMaxTradeNum(Integer.parseInt(tradeNum));
			} 
		}
		if(province!=null&&!"".equals(province)){
			mcr.setRegion(province.replaceAll("市", ""));
		}
		mcr.setMinAvgPrice((minAveragePrice==null||minAveragePrice.equals("")?null:Double.parseDouble(minAveragePrice)));
		mcr.setMaxAvgPrice((minAveragePrice==null||minAveragePrice.equals("")?null:Double.parseDouble(maxAveragePrice)));
		mcr.setMinTradePrice((minAccumulatedAmount == null || minAccumulatedAmount.equals("")) ? null : Double.parseDouble(minAccumulatedAmount));
		mcr.setMaxTradePrice((maxAccumulatedAmount == null || maxAccumulatedAmount.equals("")) ? null : Double.parseDouble(maxAccumulatedAmount));
		mcr.setUserId(userId);
		mcr.setGroupType("2");
		if(!"不限".equals(tradeStatus)&&tradeStatus!=null&&!"".equals(tradeStatus)&&tradeType!=null&&!"0".equals(tradeDays)){
			//获取最近交易设置的时间
			Calendar c = Calendar.getInstance();
			if(tradeType.equals("天")&&tradeDays!=null){
				c.add(Calendar.DATE, - Integer.parseInt(tradeDays) );  
			}else if(tradeType.equals("月")&&tradeDays!=null){
				c.add(Calendar.MONTH, - Integer.parseInt(tradeDays) );  
			}else if(tradeType.equals("年")&&tradeDays!=null){
				c.add(Calendar.YEAR, - Integer.parseInt(tradeDays) );  
			}
			
			Date startTime = c.getTime();
			mcr.setTradeStartTime(DateUtils.dateToString(startTime, DateUtils.DEFAULT_TIME_FORMAT));
			mcr.setTradeEndTime(DateUtils.dateToString(new Date(), DateUtils.DEFAULT_TIME_FORMAT));
		}

		// 根据商品编号查询购买过该商品的客户名称
		List<String> productList = new ArrayList<String>();
		if (itemIds != null && !itemIds.equals("")) {
		 String[] split = itemIds.split(",");
			for(String product:split){
				productList.add(product);
			}
			mcr.setProductList(productList);
			mcr.setItemIds(itemIds);
		}
		// 根据分组规则查询用户会员数据
		Long num = vipMemberService.findMemberCountByParam(mcr, userId);
		// 创建分组
		SellerGroup sel = new SellerGroup();
		sel.setUserId(userId);
		sel.setGroupName(groupName);
		sel.setRemark(remarks);
		sel.setStatus("1");
		sel.setMemberType("2");
		sel.setMemberCount(num.intValue());
		sel.setGroupCreate(new Date());
		groupId = sellerGroupService.addSellerGroup(sel);

		// 添加修改分组规则操作日志
		UserOperationLog sglog = new UserOperationLog();
		if (groupId != null) {
			sglog.setState("成功");
		} else {
			sglog.setState("失败");
		}
		sglog.setRemark("添加会员分组");
		sglog.setUserId(userId);
		sglog.setFunctions("会员分组");
		sglog.setType("添加");
		sglog.setDate(new Date());
		sglog.setIpAdd(ip);
		userlog.save(sglog);

		// 将数分组规则据添加到对象里
		SellerGroupRule sr = new SellerGroupRule();
		sr.setProvince(province);
		sr.setMinAccumulatedAmount(minAccumulatedAmount);
		sr.setMaxAccumulatedAmount(maxAccumulatedAmount);
		sr.setMinAveragePrice(minAveragePrice);
		sr.setMaxAveragePrice(maxAveragePrice);
		sr.setRemarks(remarks);
		sr.setItemIds(itemIds);
		sr.setTradeTimeStatus(tradeStatus);
		if(tradeDays!=null&&!"".equals(tradeDays)){
			sr.setTradeDays(Integer.parseInt(tradeDays));
		}
		if(tradeType!=null&&!"".equals(tradeType)){
			sr.setTradeType(tradeType);
		}
		sr.setUserId(userId);
		sr.setCreatedDate(new Date());
		// 根据交易次数条件设置最小交易次数和最大交易次数
		if (tradeNum != null && !tradeNum.equals("") && tradeNum.equals("3次以上")) {
			sr.setMinTradeNum(4);
			sr.setMaxTradeNum(null);
		} else if (minTradeNum != null && minTradeNum != ""
				&& maxTradeNum != null && maxTradeNum != "") {
			sr.setMinTradeNum(Integer.parseInt(minTradeNum));
			sr.setMaxTradeNum(Integer.parseInt(maxTradeNum));
		} else {
			sr.setMinTradeNum(Integer.parseInt(tradeNum));
			sr.setMaxTradeNum(Integer.parseInt(tradeNum));
		}
		if (groupId != null) {
			sr.setGroupId(groupId);
		}
		// 添加分组规则条件，并反回主键id
		Long ruleId = sellerGroupRuleService.addSellerGroupRule(sr);
		UserOperationLog log2 = new UserOperationLog();
		if (ruleId != null) {
			log2.setState("成功");
		} else {
			log2.setState("失败");
		}
		log2.setRemark("添加会员分组规则");
		log2.setUserId(userId);
		log2.setFunctions("会员分组规则");
		log2.setType("添加");
		log2.setDate(new Date());
		log2.setIpAdd(ip);
		userlog.save(log2);

		// 根据分组编号添加规则编号
		sel.setGroupId(groupId);
		sel.setRule_id(ruleId);
		int date = sellerGroupService.updateSelleGroup(sel);

		JSONObject jsonObj = new JSONObject();
		// 添加修改分组规则操作日志

		if (date == 1) {
			jsonObj.put("success", true);
			jsonObj.put("info", "添加数据成功!");
		}
		writer.print(jsonObj);
		return null;
	}

	/**
	 * 根据分组编号查询分组信息
	 * 
	 * @throws IOException
	 * 
	 */
	@RequestMapping(value = "/updateMemberGroup")
	public void updateMemberGroup(String groupId, Model model,
			HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		SellerGroup sg = sellerGroupService.findSellerGroup(groupId);
		SellerGroupRule sgr = new SellerGroupRule();
		if (sg != null && sg.getRule_id() != null) {
			sgr = sellerGroupRuleService.querySellerGroup(sg.getRule_id());
		}
		/*
		 * model.addAttribute("sellerGroup", sg);
		 * model.addAttribute("sellerGroupRule", sgr);
		 * model.addAttribute("memberType", "3");
		 */
		json.put("itemIds", sgr.getItemIds());
		json.put("message", true);
		json.put("sgrId", sgr.getId());
		json.put("sgGroupName", sg.getGroupName());
		json.put("sgRemark", sg.getRemark());
		json.put("sgGroupId", sg.getGroupId());
		json.put("memberType", "3");
		json.put("sgrTradeTimeStatus", sgr.getTradeTimeStatus());
		json.put("tradeDays", sgr.getTradeDays());
		json.put("tradeType", sgr.getTradeType());
		json.put("sgrMaxTradeNum", sgr.getMaxTradeNum());
		json.put("sgrMinTradeNum", sgr.getMinTradeNum());
		json.put("sgrMaxAccumulatedAmount", sgr.getMaxAccumulatedAmount());
		json.put("sgrMinAccumulatedAmount", sgr.getMinAccumulatedAmount());
		json.put("sgrMaxAveragePrice", sgr.getMaxAveragePrice());
		json.put("sgrMinAveragePrice", sgr.getMinAveragePrice());
		json.put("sgrMemberGrade", sgr.getMemberGrade());
		json.put("sgrProvince", sgr.getProvince());
		response.getWriter().write(json.toString());
	}

	/**
	 * 根据编号修改数据
	 * 
	 */
	@RequestMapping(value = "/updateSellerGroup")
	public String updateSellerGroup(PrintWriter writer,
			HttpServletRequest request, String tradeStatus, String tradeNum,
			String tradeDays, String tradeType, String minTradeNum,
			String maxTradeNum, String minAccumulatedAmount,
			String maxAccumulatedAmount, String minAveragePrice,
			String maxAveragePrice, String groupName, String remark,
			String province, String memberGrade, String groupId, String ruleId,
			String itemIds) {

		// 获取卖家用户的编号
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		String ip = getIpAddress.getIpAddress(request);
		MemberCriteriaVo mcr = new MemberCriteriaVo();
		// 根据交易时间状态设置开始时间和结束时间
		if(null!=groupName&&!"".equals(groupName)){
			List<SellerGroup> list = sellerGroupService.existenceSellerGroupInfo(userId,groupName);
			if (list != null&&list.size()>0) {
				SellerGroup sellerGroup = list.get(0);
				if(!groupId.equals(String.valueOf(sellerGroup.getGroupId()))){
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("success", true);
					jsonObj.put("info", "1");
					writer.print(jsonObj);
					return null;
				}
			}
		}else{
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("success", true);
			jsonObj.put("info", "2");
			writer.print(jsonObj);
			return null;
		}

		// 将数分组规则据添加到对象里
		SellerGroupRule sr = new SellerGroupRule();
		sr.setProvince(province);
		if(tradeDays!=null&&!"".equals(tradeDays)){
			sr.setTradeDays(Integer.parseInt(tradeDays));
		}
		if(tradeType!=null&&!"".equals(tradeType)){
			sr.setTradeType(tradeType);
		}
		sr.setMinAccumulatedAmount(minAccumulatedAmount);
		sr.setMaxAccumulatedAmount(maxAccumulatedAmount);
		sr.setMinAveragePrice(minAveragePrice);
		sr.setMaxAveragePrice(maxAveragePrice);
		sr.setMemberGrade(memberGrade);
		sr.setRemarks(remark);
		sr.setTradeTimeStatus(tradeStatus);
		sr.setUserId(userId);
		sr.setLastModifiedDate(new Date());
		if (ruleId != null && ruleId != "") {
			sr.setId(Long.parseLong(ruleId));
		}
		sr.setItemIds(itemIds);
		// 根据交易次数条件设置最小交易次数和最大交易次数
		if (tradeNum != null && !tradeNum.equals("") && tradeNum.equals("4")) {
			sr.setMinTradeNum(4);
			sr.setMaxTradeNum(null);
		} else if (minTradeNum != null && minTradeNum != ""
				&& maxTradeNum != null && maxTradeNum != "") {
			sr.setMinTradeNum(Integer.parseInt(minTradeNum));
			sr.setMaxTradeNum(Integer.parseInt(maxTradeNum));
		} else if (tradeNum != null && tradeNum != ""&&!"0".equals(tradeNum)) {
			sr.setMinTradeNum(Integer.parseInt(tradeNum));
			sr.setMaxTradeNum(Integer.parseInt(tradeNum));
		}
		if (groupId != null) {
			sr.setGroupId(Long.parseLong(groupId));
		}

		// 修改用户分组规则
		int date = sellerGroupRuleService.updateSellerGroupRule(sr);

		UserOperationLog log2 = new UserOperationLog();
		if (date > 0) {
			log2.setState("成功");
		} else {
			log2.setState("失败");
		}
		log2.setRemark("修改会员分组规则");
		log2.setUserId(userId);
		log2.setFunctions("会员分组规则");
		log2.setType("修改");
		log2.setDate(new Date());
		log2.setIpAdd(ip);
		userlog.save(log2);
	
		
		if(!"不限".equals(tradeStatus)&&tradeStatus!=null&&!"".equals(tradeStatus)&&tradeType!=null&&!"0".equals(tradeDays)){
			//获取最近交易设置的时间
			Calendar c = Calendar.getInstance();
			if(tradeType.equals("天")&&tradeDays!=null){
				c.add(Calendar.DATE, - Integer.parseInt(tradeDays) );  
			}else if(tradeType.equals("月")&&tradeDays!=null){
				c.add(Calendar.MONTH, - Integer.parseInt(tradeDays) );  
			}else if(tradeType.equals("年")&&tradeDays!=null){
				c.add(Calendar.YEAR, - Integer.parseInt(tradeDays) );  
			}
			Date startTime = c.getTime();
			mcr.setTradeStartTime(DateUtils.dateToString(startTime, DateUtils.DEFAULT_TIME_FORMAT));
			mcr.setTradeEndTime(DateUtils.dateToString(new Date(), DateUtils.DEFAULT_TIME_FORMAT));
		}
		
		//添加交易次数查询条件
		if(minTradeNum!=null&&!"".equals(minTradeNum)&&maxTradeNum!=null&&!"".equals(maxTradeNum)){
			mcr.setMinTradeNum(Integer.parseInt(minTradeNum));
			mcr.setMaxTradeNum(Integer.parseInt(maxTradeNum));
		}else if(tradeNum != null&&!"".equals(tradeNum)){
			if (tradeNum.equals("4")) {
				mcr.setTradeNumByTimes("4");
				mcr.setMinTradeNum(4);
			} else if (!tradeNum.equals("0")) {
				mcr.setMinTradeNum(Integer.parseInt(tradeNum));
				mcr.setMaxTradeNum(Integer.parseInt(tradeNum));
			} 
		}
		if(null!=province&&!"".equals(province)){
			mcr.setRegion( province.replaceAll("市", ""));
		}
		mcr.setMinAvgPrice((minAveragePrice==null||minAveragePrice.equals("")?null:Double.parseDouble(minAveragePrice)));
		mcr.setMaxAvgPrice((minAveragePrice==null||minAveragePrice.equals("")?null:Double.parseDouble(maxAveragePrice)));
		mcr.setMinTradePrice((minAccumulatedAmount == null || minAccumulatedAmount.equals("")) ? null : Double.parseDouble(minAccumulatedAmount));
		mcr.setMaxTradePrice((maxAccumulatedAmount == null || maxAccumulatedAmount.equals("")) ? null : Double.parseDouble(maxAccumulatedAmount));
		mcr.setUserId(userId);
		// 根据商品编号查询购买过该商品的客户名称
		List<String> productList = new ArrayList<String>();
		if (itemIds != null && !itemIds.equals("")) {
		 String[] split = itemIds.split(",");
			for(String product:split){
				productList.add(product);
			}
			mcr.setProductList(productList);
			mcr.setItemIds(itemIds);
		}
		
		mcr.setGroupType("2");
		// 根据分组规则查询用户数量
		Long num = vipMemberService.findMemberCountByParam(mcr, userId);				
		SellerGroup sg = new SellerGroup();
		if (groupId != null) {
			sg.setGroupId(Long.parseLong(groupId));
		}
		sg.setMemberCount(num.intValue());
		sg.setGroupName(groupName);
		sg.setRemark(remark);
		int sgdate = sellerGroupService.updateSelleGroup(sg);
		JSONObject jsonObj = new JSONObject();
		if (sgdate > 0) {
			jsonObj.put("success", true);
			jsonObj.put("info", "修改数据成功!");
		}
		writer.print(jsonObj);
		return null;
	}
	
	
	/**
	* 创建人：邱洋
	* @Title: updateSellerGroupCount 
	* @Description: TODO(更新会员分组的数量) 
	* @param @param groupId
	* @param @param groupType
	* @param @param request
	* @param @param writer
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/updateSellerGroupCount", method = RequestMethod.POST)
	@SuppressWarnings("static-access")
	public String updateSellerGroupCount(String groupId,String groupType,HttpServletRequest request,PrintWriter writer){
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		MemberCriteriaVo memberCriteriaVo = new MemberCriteriaVo();
		memberCriteriaVo.setGroupId(groupId);
		memberCriteriaVo.setGroupType(groupType);
		memberCriteriaVo.setUserId(userId);
		MemberCriteriaVo memberCriteriaVoParam = memberInfoController.createMemberCriteriaVoParam(memberCriteriaVo);
		Long num = vipMemberService.findMemberCountByParam(memberCriteriaVoParam, userId);
		
		SellerGroup sel = new SellerGroup();
		sel.setUserId(userId);
		if(groupId!=null){
			sel.setGroupId(Long.parseLong(groupId));
		}else{
			return null;
		}
		sel.setMemberCount(num.intValue());
		sel.setLastModifiedDate(new Date());
		Integer status = sellerGroupService.updateSelleGroup(sel);
		
		getIpAddress getip = new getIpAddress();
		String ip = getip.getIpAddress(request);
		// 更新会员分组会员数量
		UserOperationLog sglog = new UserOperationLog();
		if (status > 0) {
			sglog.setState("成功");
		} else {
			sglog.setState("失败");
		}
		sglog.setRemark("更新会员分组数据");
		sglog.setUserId(userId);
		sglog.setFunctions("会员分组");
		sglog.setType("更新");
		sglog.setDate(new Date());
		sglog.setIpAdd(ip);
		userlog.save(sglog);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("groupNum", num);
		writer.print(jsonObj.toString());
		
		return null;
	}
}
