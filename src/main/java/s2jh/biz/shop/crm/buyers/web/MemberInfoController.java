package s2jh.biz.shop.crm.buyers.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.service.RedisLockServiceImpl;
import lab.s2jh.core.util.DateUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.vo.MemberCriteriaVo;
import s2jh.biz.shop.crm.member.entity.MemberLevelSetting;
import s2jh.biz.shop.crm.member.service.MemberLevelSettingService;
import s2jh.biz.shop.crm.seller.entity.SellerGroup;
import s2jh.biz.shop.crm.seller.entity.SellerGroupRule;
import s2jh.biz.shop.crm.seller.service.SellerGroupRuleService;
import s2jh.biz.shop.crm.seller.service.SellerGroupService;
import s2jh.biz.shop.utils.JsonUtil;


@Controller
@RequestMapping(value = "/memberInfoController")
public class MemberInfoController extends BaseController{

	@Autowired
	private MemberInfoService memberInfoService;

	@Autowired
	private SellerGroupRuleService sellerGroupRuleService;

	@Autowired
	private MemberLevelSettingService memberLevelSettingService;

	@Autowired
	private SellerGroupService sellerGroupService;

	@Autowired
	private VipMemberService vipMemberService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	

	private static final Log logger = LogFactory.getLog(MemberInfoController.class);
	
//	/**
//	 * 创建人：邱洋 @Title: 点击会员营销时，查询当前默认分组的所有会员信息 @date
//	 * 2017年4月11日--上午11:19:22 @return List<MemberInfo> @throws
//	 */
//	public List<MemberInfo> getMemberInfos(String groupId, String userId,int start,int maxNum,String mapKey) {
//		List<MemberInfo> memberInfoList = new ArrayList<MemberInfo>();
//		HashMap<String,Object> queryMap = new HashMap<String,Object>();
//		queryMap.put("userId", userId);
//		// 根据组ID获取当前组的查询条件
//		MemberLevelSetting ml = memberLevelSettingService.getMemberLevelSettingInfo(groupId);
//		if (ml == null) {
//			return null;
//		}
//		int tradeNum = ml.getTurnover();// 当前分组的成交量
//		String tradePrice = ml.getTradingVolume().toString();// 当前分组的成交金额
//
//		String highGroupId = null;
//		// 获取当前用户所有的默认分组信息
//		List<SellerGroup> list = sellerGroupService.getAllSellerGroup(userId);
//		for (int i = 0; i < list.size(); i++) {
//			if (ml.getGroupId() != null && ml.getGroupId().equals(list.get(i).getGroupId().toString())) {
//				if (i < 3) {
//					highGroupId = list.get(i + 1).getGroupId().toString();
//				}
//				break;
//			}
//		}
//
//		// 获取当前分组上一个分组的设置条件
//		MemberLevelSetting hml = null;
//		if (highGroupId != null) {
//			hml = memberLevelSettingService.getMemberLevelSettingInfo(highGroupId);
//		}
//		int nextTradeNum = 0;
//		String nextTradePrice = null;
//		if (hml != null) {
//			nextTradeNum = hml.getTurnover();
//			nextTradePrice = hml.getTradingVolume().toString();
//		}
//		if(start>0){
//			queryMap.put("start", start);
//		}
//		if(maxNum>0){
//			queryMap.put("maxNum", maxNum);
//		}
//		queryMap.put("tradeNum", tradeNum);
//		queryMap.put("tradePrice",
//				(tradePrice == null || tradePrice.equals("") ? null : Double.parseDouble(tradePrice)));
//		queryMap.put("nextTradeNum", nextTradeNum);
//		queryMap.put("nextTradePrice",
//				(nextTradePrice == null || nextTradePrice.equals("") ? null : Double.parseDouble(nextTradePrice)));
//		memberInfoList = memberInfoService.getDefaultGroupMemberInfo(queryMap);
//		int countNum = memberInfoService.getDefaultGroupMemberInfoNum(queryMap);
//		
//		HashMap<String,Object> map = new HashMap<String,Object>();
//		map.put("memberGroup", true);
//		map.put("userId", userId);
//		map.put("minTradeNum", tradeNum);
//		map.put("maxTradeNum", nextTradeNum);
//		map.put("minAccumulatedAmount", (tradePrice == null || tradePrice.equals("") ? null : Double.parseDouble(tradePrice)));
//		map.put("maxAccumulatedAmount",(nextTradePrice == null || nextTradePrice.equals("") ? null : Double.parseDouble(nextTradePrice)));
//		Long cnum = (long)countNum;
//		map.put("count", cnum);
//		StoreMapCache mapCache = StoreMapCache.getMapCache();
//		mapCache.putMap(userId, mapKey, map);
//		return memberInfoList;
//	}
//
	// 获取会员分组的会员，跳转到会员短信群发页面
	@RequestMapping(value = "/getMemberIds")
	public String getMemberIds(String groupId, Model model, HttpServletRequest request, String groupType,Pageination<MemberDTO> page) {
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		
		SellerGroup sgroup = sellerGroupService.findSellerGroupInfo(groupId);
		try {
			List<MemberDTO> datas =null;
			Pageination<MemberDTO> memberPageList = null;
			Long memberNum = 0L;
			String  queryKey = null;
			MemberCriteriaVo memberCriteriaVo = new MemberCriteriaVo();
			if(null!=groupId&&!"".equals(groupId)){
				memberCriteriaVo.setGroupId(groupId);
			}
			if(null!=groupType&&!"".equals(groupType)){
				memberCriteriaVo.setGroupType(groupType);
			}
			MemberCriteriaVo memberCriteriaVoParam = memberCriteriaVo;
			try {
				if(null!=userId&&!"".equals(userId)){
					if (null != groupId && !"".equals(groupId)) {
						memberCriteriaVoParam = createMemberCriteriaVoParam(memberCriteriaVo);
						memberCriteriaVoParam.setUserId(userId);
						if(memberCriteriaVoParam!=null){
							memberPageList = vipMemberService.findMemberPageList(page, memberCriteriaVoParam, userId);
							memberNum = memberPageList.getTotalCount();
							memberCriteriaVoParam.setTotalCount(memberNum);
							queryKey = getKey();
						}
					}
					if(memberPageList!=null){
					    datas = memberPageList.getDatas();
					}
				}			
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*cacheService.put(RedisConstant.RedisCacheGroup.MEMBER_BATCH_SEND_DATA_CACHE, 
							RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+queryKey+"-"+userId,JsonUtil.toJson(memberCriteriaVo),
							TimeUnit.HOURS,1l);*/
			redisLockServiceImpl.putStringValueWithExpireTime(
					RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+queryKey+"-"+userId,
					JsonUtil.toJson(memberCriteriaVoParam),TimeUnit.HOURS,1l);
			model.addAttribute("memberNum",(memberNum ==0L?-1:memberNum));
			model.addAttribute("groupId",groupId);
			model.addAttribute("memberInfoList", datas);
			model.addAttribute("groupName",sgroup.getGroupName());
			model.addAttribute("queryKey",queryKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "crms/marketingCenter/memberMessageSend";
	}

	/**
	 * 创建人：邱洋
	 * 
	 * @Title: 导出会员信息
	 * @date 2017年4月8日--下午4:51:48
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/downloadMemberInfo")
	public void reportMemberInfo(
			HttpServletResponse response,Model model,HttpServletRequest request,Pageination<MemberDTO> page,String queryKey) throws IOException {	// 获取当前用户编号(正常情况是从session中获取，目前为测试用)
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		if(queryKey==null || "".equals(queryKey)){
			//.........
		}
		
		
		ServletOutputStream outputStream = null;
		// 设置导出报表文件名
		String filename = new String("会员信息.xlsx".getBytes(), "ISO-8859-1");
		// 创建一个工作簿
		SXSSFWorkbook book = new SXSSFWorkbook();
		// 使用工作簿创建一个工作表
		Sheet sheet = book.createSheet();
		// 创建一行
		Row row = sheet.createRow(0);
		// 创建单元格
		Cell cell = row.createCell(0);
		// 设置第一行的内容
		cell.setCellValue("序号");
		cell = row.createCell(1);
		cell.setCellValue("买家昵称");
		cell = row.createCell(2);
		cell.setCellValue("会员等级编号");
		cell = row.createCell(3);
		cell.setCellValue("交易成功笔数");
		cell = row.createCell(4);
		cell.setCellValue("交易成功金额");
		cell = row.createCell(5);
		cell.setCellValue("交易关闭的金额");
		cell = row.createCell(6);
		cell.setCellValue("交易关闭的笔数");
		cell = row.createCell(7);
		cell.setCellValue("地区");
		cell = row.createCell(8);
		cell.setCellValue("城市");
		cell = row.createCell(9);
		cell.setCellValue("购买的宝贝件数");
		cell = row.createCell(10);
		cell.setCellValue("平均客单价");
		cell = row.createCell(11);
		cell.setCellValue("最后交易时间");
		cell = row.createCell(12);
		cell.setCellValue("交易关闭的宝贝件数");
		cell = row.createCell(13);
		cell.setCellValue("手机号");
		cell = row.createCell(14);
		cell.setCellValue("卖家编号");
		
		/*MemberCriteriaVo memberCriteriaVo = cacheService.get(RedisConstant.RedisCacheGroup.MEMBER_BATCH_SEND_DATA_CACHE, 
				RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+queryKey+"-"+userId, MemberCriteriaVo.class);*/
		MemberCriteriaVo memberCriteriaVo =redisLockServiceImpl.getValue(
				RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+queryKey+"-"+userId,
				MemberCriteriaVo.class);
		Pageination<MemberDTO> memberPageList = null;
		int start = 1;
		int maxNum = 5000;
		int runNum = 0;
		page.setPageSize(maxNum);
		while(true){
			List<MemberDTO> datas = new ArrayList<MemberDTO>();
			try {
				page.setPageNo(start);
				if(null!=memberCriteriaVo&&null!=userId&&!"".equals(userId)){
					if (null != memberCriteriaVo.getGroupId() && !"".equals(memberCriteriaVo.getGroupId())) {
						MemberCriteriaVo memberCriteriaVoParam = createMemberCriteriaVoParam(memberCriteriaVo);
						if(memberCriteriaVoParam!=null){
							memberPageList = vipMemberService.findMemberPageList(page,memberCriteriaVoParam,userId);
						}
					}else{
						memberPageList = vipMemberService.findMemberPageList(page,memberCriteriaVo,userId);
					}
					if(memberPageList!=null){
					    datas = memberPageList.getDatas();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (datas != null && datas.size() > 0) {
				sheet = exportExcel(datas, sheet, row, cell,
						runNum);
				runNum = runNum +maxNum;
				start++;
			} else {
				break;
			}
		}
		
		// 设置短信类型、短信条数与时间一列的宽度
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 4000);
		sheet.setColumnWidth(12, 6000);
		sheet.setColumnWidth(13, 4000);
		sheet.setColumnWidth(14, 6000);
		sheet.setColumnWidth(15, 4000);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename);
		try {
			outputStream = response.getOutputStream();
			book.write(outputStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(memberCriteriaVo!=null){
			model.addAttribute("groupId", memberCriteriaVo.getGroupId() );
		}
	}

	
	
	/** 
      * @Title: queryMemberInfo 
	  * @Description: (根据条件查询出会员信息: 
	  * 1,返回到页面一百条
	  * @return void    返回类型 
	  * @date 2017年5月22日 下午3:36:27
	  * @author zlp
	  * @throws 
	  */
	@RequestMapping(value = "/queryMemberInfo")
	@ResponseBody
	public String queryMemberInfo(HttpServletRequest request, Model model,Pageination<MemberDTO> page , 
			HttpServletResponse response, MemberCriteriaVo memberCriteriaVo ){
		try {
			String userId = (String) request.getSession().getAttribute("taobao_user_nick");
			Pageination<MemberDTO> memberPageList = null;
			List<MemberDTO> datas =null;
			String queryKey = null;
			long totalCount =0l;
			MemberCriteriaVo memberCriteriaVoParam = memberCriteriaVo;
			if(null!=userId&&!"".equals(userId)){
				memberCriteriaVoParam.setUserId(userId);
				if (null != memberCriteriaVo.getGroupId() && !"".equals(memberCriteriaVo.getGroupId())) {
					memberCriteriaVoParam = createMemberCriteriaVoParam(memberCriteriaVo);
					if(memberCriteriaVoParam!=null){
						memberPageList = vipMemberService.findMemberPageList(page,memberCriteriaVoParam,userId);
					}
				}else{
					memberCriteriaVoParam = memberCriteriaVo;
					    memberPageList = vipMemberService.findMemberPageList(page,memberCriteriaVoParam,userId);
				}
				if(memberPageList!=null){
				    datas = memberPageList.getDatas();
				    totalCount = memberPageList.getTotalCount();
				    memberCriteriaVoParam.setTotalCount(totalCount);
				    queryKey =  getKey();
				    if(null!=datas&&datas.size()>0){
				    	for (MemberDTO memberDTO : datas) {
				    		memberDTO.setOccupation("店铺客户");
						}
				    }
				}else{
				    datas = new ArrayList<MemberDTO>();
				    totalCount = 0l;
				}
				//将查询条件放入缓存
				//默认缓存12小时
				//cacheService.put(RedisConstant.RedisCacheGroup.MEMBER_BATCH_SEND_DATA_CACHE, RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+userId,JsonUtil.toJson(memberCriteriaVo));
				/**
				 * 搜索条件保存时间为1小时
				 */
				/*cacheService.put(RedisConstant.RedisCacheGroup.MEMBER_BATCH_SEND_DATA_CACHE, 
								RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+queryKey+"-"+userId,JsonUtil.toJson(memberCriteriaVo),
								TimeUnit.HOURS,1l);*/
				redisLockServiceImpl.putStringValueWithExpireTime(
						RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+queryKey+"-"+userId,
						JsonUtil.toJson(memberCriteriaVoParam),TimeUnit.HOURS,1l);
			}else{
				return  rsMap(1001, "error").put("msg", "用户信息为空！").toJson();
			}
			return  rsMap(100, "success").put("count", totalCount).put("mList", datas).put("queryKey",queryKey).toJson();
		} catch (Exception e) {
			e.printStackTrace();
			return  rsMap(1001, "error").put("msg", "查询失败，请联系管理员！").toJson();
		}
	}

	public MemberCriteriaVo createMemberCriteriaVoParam(
			MemberCriteriaVo memberCriteriaVo) {
		MemberCriteriaVo memberCriteriaPamrm =null;
		try {
			memberCriteriaPamrm = new  MemberCriteriaVo();
			SellerGroup sgroup = sellerGroupService.findSellerGroupInfo(memberCriteriaVo.getGroupId());
			// 判断用户分组（1为默认分组，2为用户添加分组）
			if (sgroup.getMemberType() != null && sgroup.getMemberType().equals("1")) {
				memberCriteriaPamrm.setGroupType(sgroup.getMemberType());
				// 获取默认分组的会员信息
				HashMap<String,Object> queryMap = new HashMap<String,Object>();
				queryMap.put("userId", memberCriteriaVo.getUserId());
				// 根据组ID获取当前组的查询条件
				MemberLevelSetting memberLevelSetting = memberLevelSettingService.getMemberLevelSettingInfo(memberCriteriaVo.getGroupId());
				if(null!=memberLevelSetting&&null!=memberLevelSetting.getMemberlevel()&&!"".equals(memberLevelSetting.getMemberlevel())){
					if("4".equals(memberLevelSetting.getMemberlevel())){
						memberCriteriaPamrm.setMinTradeNum(memberLevelSetting.getTurnover());
						memberCriteriaPamrm.setMinTradePrice(memberLevelSetting.getTradingVolume().doubleValue()); 
					}else{
						Integer memberlevel  =  Integer.parseInt(memberLevelSetting.getMemberlevel())+1;
						MemberLevelSetting highSettingInfo = memberLevelSettingService.findMemberLevelByLevelAndUserId(memberCriteriaVo.getUserId(),String.valueOf(memberlevel));
						memberCriteriaPamrm.setMinTradeNum(memberLevelSetting.getTurnover());
						memberCriteriaPamrm.setMinTradePrice(memberLevelSetting.getTradingVolume().doubleValue()); 
						if(null!=highSettingInfo){
							memberCriteriaPamrm.setMaxTradeNum(highSettingInfo.getTurnover());
							memberCriteriaPamrm.setMaxTradePrice(highSettingInfo.getTradingVolume().doubleValue()); 
						}
					}
					memberCriteriaPamrm.setGroupType("1");
				}else{
					return null;
				} 
			} else if (sgroup.getMemberType() != null && sgroup.getMemberType().equals("2")) {
				memberCriteriaPamrm.setGroupType(sgroup.getMemberType());
				SellerGroupRule sg = sellerGroupRuleService.findSellerGroupRule(memberCriteriaVo.getGroupId());
				if (sg != null) {
					if (sg.getProvince() != null && sg.getProvince() != "") {
						String province = sg.getProvince().replaceAll("市", "");
						String[] split = province.split(",");
						memberCriteriaPamrm.setAreaList(Arrays.asList(split));  
					}
					// 获取最大、最小累计金额
					if (sg.getMaxAccumulatedAmount() != null&&!"".equals(sg.getMaxAccumulatedAmount())) {
						memberCriteriaPamrm.setMaxTradePrice(Double.valueOf(sg.getMaxAccumulatedAmount())); 
					}
					if (sg.getMinAccumulatedAmount() != null&&!"".equals(sg.getMinAccumulatedAmount())) {
						memberCriteriaPamrm.setMinTradePrice(Double.valueOf(sg.getMinAccumulatedAmount())); 
					}
					List<String> productList = new ArrayList<String>();
					if (sg.getItemIds() != null&&!"".equals(sg.getItemIds())) {
						String[] split = sg.getItemIds().split(",");
						for(String product:split){
							productList.add(product);
						}
						memberCriteriaPamrm.setProductList(productList);  
					}

					// 获取最大、最小平均客单价
					if (sg.getMaxAveragePrice() != null&&!"".equals(sg.getMaxAveragePrice())) {
						memberCriteriaPamrm.setMaxAvgPrice(Double.valueOf(sg.getMaxAveragePrice())); 
					}
					if (sg.getMinAveragePrice() != null&&!"".equals(sg.getMinAveragePrice())) {
						memberCriteriaPamrm.setMinAvgPrice(Double.valueOf(sg.getMinAveragePrice())); 
					}
					// 获取最大、最小交易次数
					if (sg.getMaxTradeNum() != null) {
						memberCriteriaPamrm.setMaxTradeNum(sg.getMaxTradeNum()); 
					}
					if (sg.getMinTradeNum() != null) {
						memberCriteriaPamrm.setMinTradeNum(sg.getMinTradeNum()); 
					}
					memberCriteriaPamrm.setGroupType("2");
					
					//  交易时间
					if(!"不限".equals(sgroup.getStatus())&&sgroup.getStatus()!=null&&!"".equals(sgroup.getStatus())){
						//获取最近交易设置的时间
						Calendar c = Calendar.getInstance();
						if(sg!=null&&sg.getTradeType()!=null){
							if(sg.getTradeType().equals("天")&&sg.getTradeDays()!=0){
								c.add(Calendar.DATE, - sg.getTradeDays());  
							}else if(sg.getTradeType().equals("月")&&sg.getTradeDays()!=0){
								c.add(Calendar.MONTH, - sg.getTradeDays());  
							}else if(sg.getTradeType().equals("年")&&sg.getTradeDays()!=0){
								c.add(Calendar.YEAR, - sg.getTradeDays());  
							}
							Date startTime = c.getTime();
							memberCriteriaPamrm.setTradeStartTime(DateUtils.dateToString(startTime, DateUtils.DEFAULT_TIME_FORMAT));
							memberCriteriaPamrm.setTradeEndTime(DateUtils.dateToString(new Date(), DateUtils.DEFAULT_TIME_FORMAT));
						}
					}
				}else{
					return null;
				}
			 }else{
				 return null;
			 }
		} catch (NumberFormatException e) {
			 logger.error(e.getMessage()+"  用户"+memberCriteriaVo.getUserId()+"选择分组查询会员出错");
			 return null;
		}
		return memberCriteriaPamrm;
	}
	
	
	public Sheet exportExcel(List<MemberDTO> list, Sheet sheet, Row row,
			Cell cell, int rowNum) {
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(rowNum + 1 + i);
			row.createCell(0).setCellValue(rowNum + i + 1);
			if (list.get(i).getUserId() != null) {
				row.createCell(1).setCellValue(list.get(i).getBuyerNick());
			}
			row.createCell(2).setCellValue("店铺会员");
			if (list.get(i).getTradeCount() != null) {
				row.createCell(3).setCellValue(list.get(i).getTradeCount());
			}
			if (list.get(i).getTradeAmount() != null) {
				row.createCell(4).setCellValue(list.get(i).getTradeAmount()+"");
			}
			if (list.get(i).getCloseTradeAmount() != null) {
				row.createCell(5).setCellValue(
						list.get(i).getCloseTradeAmount()+"");
			}
			if (list.get(i).getCloseTradeCount() != null) {
				row.createCell(6)
						.setCellValue(list.get(i).getCloseTradeCount());
			}
			if (list.get(i).getProvince() != null) {
				row.createCell(7).setCellValue(list.get(i).getProvince());
			}
			if (list.get(i).getCity() != null) {
				row.createCell(8).setCellValue(list.get(i).getCity());
			}
			if (list.get(i).getItemNum() != null) {
				row.createCell(9).setCellValue(list.get(i).getItemNum());
			}
			if (list.get(i).getAvgPrice() != null) {
				Double avgPrice = list.get(i).getAvgPrice().doubleValue();
				row.createCell(10).setCellValue(avgPrice);
			}

			if (list.get(i).getLastTradeTime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
				row.createCell(11).setCellValue(
						sdf.format(list.get(i).getLastTradeTime()));
			}
			if (list.get(i).getItemCloseCount() != null) {
				row.createCell(12)
						.setCellValue(list.get(i).getItemCloseCount());
			}
			if (list.get(i).getPhone() != null) {
				row.createCell(13).setCellValue(list.get(i).getPhone());
			}
			if (list.get(i).getUserId() != null) {
				row.createCell(14).setCellValue(list.get(i).getUserId());
			}
		}
		return sheet;
	}

	
	/**
	 * @Title: getKey 
	 * @Description: (每次搜索会员 生成一个key) 
	 * @date 2017年5月17日 下午3:17:45 
	 * @author jackstraw_yu @throws
	 */
	private String getKey() {
		long id = System.currentTimeMillis();
		String key = id + "-";
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			key += random.nextInt(10);
		}
		return key;
	}

}
