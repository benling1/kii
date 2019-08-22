package s2jh.biz.shop.crm.message.web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.s2jh.core.service.RedisLockServiceImpl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;










import com.taobao.api.SecretException;

import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsReceiveInfo;
import s2jh.biz.shop.crm.message.service.SmsReceiveInfoService;
import s2jh.biz.shop.crm.message.service.SmsSendInfoService;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.ExcelExportUtils;
import s2jh.biz.shop.utils.JsonUtil;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;


 /**
* @ClassName: memberInteractionController
* @Description: TODO(客户管理->会员互动:由于会员互动的主要内容是短信内容->短信发送&短信接收,所以放到message这一个包)
* @author:jackstraw_yu
* @date 2016年11月28日
*
*/
@Controller
@RequestMapping(value="/memberInteraction")
public class MemberInteractionController {

	private Map<String,Object> paramMap = new HashMap<String, Object>();
	@Autowired
	private SmsSendInfoService smsSendInfoService;
	
	@Autowired
	private SmsReceiveInfoService smsReceiveInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	
	/**
	* @Title: toMemberInteraction
	* @Description: TODO(会员互动的页面跳转)
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/memberSmsSendAndReceIve")
	public String toMemberInteraction(){
		
		//return "crms/customerManagement/memberInteraction";
		return "redirect:/memberInteraction/sllerSmsReceiveUnread";
	}
	
	/**
	 * @Title: toMemberInteraction
	 * @Description: TODO(会员互动记录查询未读的回复信息)
	 * @return String    返回类型
	 * @author:jackstraw_yu
	 * @throws
	 */
	@RequestMapping(value="/sllerSmsReceiveUnread")
	public String sllerSmsReceiveUnread(Model model,HttpServletRequest request,
		String sendPhone,
		@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		
		if(sendPhone!=null){
			sendPhone = sendPhone.trim();
		}
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "crazyboy";
		Integer type = 24;//会员互动的短信类
		//未读短信
		Integer status = 0;
		
		String contextPath = request.getContextPath()+"/memberInteraction/sllerSmsReceiveUnread";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sendPhone", sendPhone);
		map.put("type", type);
		map.put("userId", userId);
		map.put("status", status);
		
		//调用service查询出会员互动的短信详情列表分页
		Pagination pagination = null;
		try {
			pagination = smsReceiveInfoService.querySmsReceiveInfoPagination(contextPath, pageNo, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(pagination != null){
			StringBuilder params = new StringBuilder();
			if(sendPhone != null && !"".equals(sendPhone)){
				params.append("&sendPhone=").append(sendPhone);
			}
			pagination.pageView(contextPath, params.toString());
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("show", 1);
		model.addAttribute("sendPhone", sendPhone);
		model.addAttribute("shopName", request.getSession().getAttribute("shopName"));
		return "crms/customerManagement/memberInteraction";
	}
	
	/**
	* @Title: sllerSmsReceiveRemark
	* @Description: TODO(会员互动记录->一键标记为已读)
	* @param     参数:Integer [] 
	* @return void    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	@RequestMapping(value="/sllerSmsReceiveRemark")
	public String sllerSmsReceiveRemark(Integer[] ids ,HttpServletRequest request){

		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "crazyboy";
		
		//添加操作日志
		UserOperationLog userOperationLog = new UserOperationLog();
		userOperationLog.setUserId(userId);
		userOperationLog.setCreatedBy(userId);
		userOperationLog.setCreatedDate(new Date());
		//ip地址
		userOperationLog.setIpAdd(getIpAddress.getIpAddress(request));
		
		//调用service层将会员互动中未读信息标记成已读
		smsReceiveInfoService.remarkSmsReceiveInfo(ids,userOperationLog);

		
		//重定向到会员互动记录
		return "redirect:/memberInteraction/sllerSmsReceiveUnread";
	}
	
	//=================================卖家回复内容查询========================
	
	/**
	 * 买家回复内容查询
	 * ZTK2017年5月17日下午4:56:51
	 */
	@RequestMapping(value="/sllerSmsReceiveAll")
	public String sllerSmsReceiveAll(Model model,HttpServletRequest request,
			String sendPhone,String buyerNick,String status,String beginTime,String endTime ,String ifContain,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		try {
			String userId = (String) request.getSession().getAttribute("taobao_user_nick");
			String sessionKey = userInfoService.validateFindSessionKey(userId);
			Map<String,Object> queryMap = new HashMap<String,Object>();
			Integer type = 24;//会员互动的短信类
			if(sendPhone!=null){
				sendPhone = sendPhone.trim();
			}
			if(buyerNick!=null){
				buyerNick = buyerNick.trim();
			}
			//将前台传入的字符串日期转换成Date类型
			Date bTime = DateUtils.convertDate(beginTime);
			Date eTime = DateUtils.convertDate(endTime);
			if("2".equals(status)){
				queryMap.put("status", null);
			}else{
				queryMap.put("status", status);
			}
			if("1".equals(ifContain)){
				queryMap.put("containN", null);
				queryMap.put("notContainN", "N");
			}else if("2".equals(ifContain)){
				queryMap.put("containN", "N");
				queryMap.put("notContainN", null);
			}else{
				queryMap.put("containN", null);
				queryMap.put("notContainN", null);
			}
			String contextPath = request.getContextPath()+"/memberInteraction/sllerSmsReceiveAll";
			//初始化加密
			EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
			if(sendPhone != null && !"".equals(sendPhone)){
				if(EncrptAndDecryptClient.isEncryptData(sendPhone, EncrptAndDecryptClient.PHONE)){
					queryMap.put("sendPhone", sendPhone);
				}else {
					queryMap.put("sendPhone", decryptClient.encryptData(sendPhone, EncrptAndDecryptClient.PHONE, sessionKey));
				}
			}else {
				queryMap.put("sendPhone", null);
			}
			if(buyerNick != null && !"".equals(buyerNick)){
				if(EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
					queryMap.put("buyerNick", buyerNick);
				}else {
					queryMap.put("buyerNick", decryptClient.encryptData(buyerNick, EncrptAndDecryptClient.SEARCH, sessionKey));
				}
			}else {
				queryMap.put("buyerNick", null);
			}
			queryMap.put("type", type);
			queryMap.put("userId", userId);
			queryMap.put("beginTime", beginTime);
			queryMap.put("endTime", endTime);
			redisLockServiceImpl.putStringValueWithExpireTime(userId + "queryMap", JsonUtil.toJson(queryMap), TimeUnit.HOURS, 1L);
			Pagination pagination = smsReceiveInfoService.querySmsReceiveInfoPagination(contextPath, pageNo, queryMap);
			StringBuilder params = new StringBuilder();
			if(type != null && !"".equals(type)){
				params.append("&type=").append(type);
			}
			if(beginTime != null && !"".equals(beginTime)){
				params.append("&beginTime=").append(beginTime);
			}
			if(endTime != null && !"".equals(endTime)){
				params.append("&endTime=").append(endTime);
			}
			if(sendPhone != null && !"".equals(sendPhone)){
				params.append("&sendPhone=").append(sendPhone);
			}
			if(status != null && !"".equals(status)){
				params.append("&status=").append(status);
			}
			if(buyerNick != null && !"".equals(buyerNick)){
				params.append("&buyerNick=").append(buyerNick);
			}
			if(userId != null && !"".equals(userId)){
				params.append("&userId=").append(userId);
			}
			if(ifContain != null && !"".equals(ifContain)){
				params.append("&ifContain=").append(ifContain);
			}
			//分页视图
			pagination.pageView(contextPath, params.toString());
			
			model.addAttribute("pagination2", pagination);
			model.addAttribute("show", 2);
			//显示页面回显
			model.addAttribute("sendPhone", sendPhone);
			model.addAttribute("status", status);
			model.addAttribute("buyerNick", buyerNick);
			model.addAttribute("beginTime", bTime);
			model.addAttribute("endTime", eTime);
			model.addAttribute("ifContain", ifContain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "crms/customerManagement/memberInteraction";
	}
	
	/**
	 * 导出买家回复信息
	 * ZTK2017年5月17日下午5:02:46
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/sllerSmsReceiveCheckout")
	public String sllerSmsReceiveCheckout(HttpServletRequest request,HttpServletResponse response){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String,Object> queryMap = redisLockServiceImpl.getValue(userId + "queryMap", Map.class);
		List<SmsReceiveInfo> smsReceiveInfoList = null;
		try {
			smsReceiveInfoList = smsReceiveInfoService.findReceiveInfo(queryMap);
		} catch (UnsupportedEncodingException | SecretException e1) {
			e1.printStackTrace();
		}
		//.循环每一条数据将数据放入数组在将数组放入list
		List<Object[]> dataList = new ArrayList<Object[]>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String status = "";
		if(smsReceiveInfoList != null && smsReceiveInfoList.size() > 0){
			for(int x=1,i=0;i<smsReceiveInfoList.size();x++,i++){
				if(smsReceiveInfoList.get(i) != null && smsReceiveInfoList.get(i).getStatus() != null 
						&& smsReceiveInfoList.get(i).getStatus()==1){
					status = "已读";
				}else if(smsReceiveInfoList.get(i) != null && smsReceiveInfoList.get(i).getStatus() != null
						&&smsReceiveInfoList.get(i).getStatus()==0){
					status = "未读";
				}else{
					status = "未读";
				}
				//将数据放入数组放入数组
				//添加数据到list
				dataList.add(new Object[]{x+"",smsReceiveInfoList.get(i).getBuyerNick()+"",smsReceiveInfoList.get(i).getSendPhone()+"",
						smsReceiveInfoList.get(i).getContent()+"",status+"",
						dateFormat.format(smsReceiveInfoList.get(i).getReceiveDate())+""});
			}
		}
		//表头列名
		String[] rowName = new String[]{"序号","买家昵称","手机号","短信内容","是否已读","发送时间"};
		try {
			//使用工具生产Excel表格
			HSSFWorkbook workbook = ExcelExportUtils.export("买家回复内容记录", rowName, dataList);
			response.setContentType("application/x-msdownload");
	        response.setHeader("Content-Disposition","attachment;filename=document.xls");
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//=========================卖家发送记录=============================
	/**
	* @Title: sellerSendHistory
	* @Description: TODO(查询会员互动中卖家发送记录)
	* @param  参数:pageNo,phone,satus,nickName,beginTime,endTime
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	/*@RequestMapping(value="/sellerSendHistory")
	public String sellerSendHistory(Model model,HttpServletRequest request,
			String phone,Integer status,String nickname,String beginTime,String endTime,
			@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "crazyboy";
		Integer type = 24;//会员互动的短信类型
		
		if(phone!=null){
			phone = phone.trim();
		}if(nickname!=null){
			nickname = nickname.trim();
		}
		//将前台传入的字符串日期转换成Date类型
		Date bTime = convertDate(beginTime);
		Date eTime = convertDate(endTime);
		
		String contextPath = request.getContextPath()+"/memberInteraction/sellerSendHistory";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phone", phone);
		map.put("nickname", nickname);
		map.put("status", status);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("type", type);
		map.put("userId", userId);
		
		//调用smsSendInfoService查询出会员互动的短信详情列表分页
		Pagination pagination = smsSendInfoService.findSmsSendInfoPagination(contextPath, pageNo, map);
		model.addAttribute("pagination3", pagination);
		model.addAttribute("show", 3);
		model.addAttribute("phone", phone);
		model.addAttribute("nickname", nickname);
		model.addAttribute("status", status);
		model.addAttribute("beginTime", bTime);
		model.addAttribute("endTime", eTime);
		return "crms/customerManagement/memberInteraction";
	}*/
	
	
	/**
	* @Title: sellerSendHistoryCheckout
	* @Description: TODO(短信互动中查询卖家发送记录的导出)
	* @param ids
	* @param  response
	* @return String    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	/*@RequestMapping(value="/sellerSendHistoryCheckout")
	public String sellerSendHistoryCheckout(Integer[] ids,HttpServletResponse response){
	
		//调用smsSendInfoService查询出会员互动的短信详情的的list集合
		List<SmsSendInfo> smsSendInfoList = smsSendInfoService.getSmsSendInfoList(ids);
		
		*//**
		 * 导出
		 * **//*
		
		//.循环每一条数据将数据放入数组在将数组放入list
		List<Object[]> dataList = new ArrayList<Object[]>();
		发送成功--1
		手机号码不正确--2
		短信余额不足--3
		重复被屏蔽--4
		黑名单--5
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String status = "";
		String fTime = null;
		for(int x=1,i=0;i<smsSendInfoList.size();x++,i++){
			
			if(smsSendInfoList.get(i).getStatus()==1){
				status="发送成功";
			}
			else if(smsSendInfoList.get(i).getStatus()==2){
				status="手机号码不正确";
			}
			else if(smsSendInfoList.get(i).getStatus()==3){
				status="短信余额不足";
			}
			else if(smsSendInfoList.get(i).getStatus()==4){
				status="重复被屏蔽";
			}
			else if(smsSendInfoList.get(i).getStatus()==5){
				status="黑名单";
			}
			
			if(smsSendInfoList.get(i).getSendTime()!=null && !"".equals(smsSendInfoList.get(i).getSendTime())){
				fTime = dateFormat.format(smsSendInfoList.get(i).getSendTime());
			}
			
			//将数据放入数组放入数组
			//添加数据到list
			dataList.add(new Object[]{x+"",smsSendInfoList.get(i).getNickname()+"",smsSendInfoList.get(i).getPhone()+"",
					smsSendInfoList.get(i).getContent()+"",smsSendInfoList.get(i).getChannel()+"",status+"",
					smsSendInfoList.get(i).getActualDeduction()+"",fTime});
			
		}
		
		//表头列名
		String[] rowName = new String[]{"序号","买家昵称","手机号","短信内容","短信通道","发送状态","实际扣费(条)","发送时间"};
		try {
			//使用工具生产Excel表格
			HSSFWorkbook workbook = ExcelExportUtils.export("卖家发送记录", rowName, dataList);
			response.setContentType("application/x-msdownload");
	        response.setHeader("Content-Disposition",
	                "attachment;filename=document.xls");
			
			 workbook.write(response.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	//=========================日期转换=============================
	//定义一个日期的工具转换方法
	public Date convertDate(String time){
		Date Ctime = null;//结束时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(time!=null && !"".equals(time)){
			try {
				Ctime = dateFormat.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return Ctime;
	} */
}
