package s2jh.biz.shop.crm.member.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lab.s2jh.core.dao.mybatis.MyBatisDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import s2jh.biz.shop.crm.buyers.service.BuyerGroupInfoService;
import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.member.entity.MemberLevelSetting;
import s2jh.biz.shop.crm.member.service.MemberLevelSettingModel;
import s2jh.biz.shop.crm.member.service.MemberLevelSettingService;
import s2jh.biz.shop.crm.seller.service.SellerGroupService;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.getIpAddress;

@Controller
@RequestMapping(value = "")
public class MemberLevelSettingController {

	@Autowired
	private MemberLevelSettingService memberLevelSettingService;

	@Autowired
	private MemberInfoService memberInfoService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SellerGroupService sellerGroupService;

	@Autowired
	private BuyerGroupInfoService buyerGroupInfoService;

	@Autowired
	private MyBatisDao myBatisDao;

	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	private static final Log logger = LogFactory.getLog(MemberLevelSettingController.class);

	/* 保存,修改会员等级设置
	 * result 为true 调用接口,反之将不予推送至淘宝
	 * 接口名称 taobao.crm.grade.set (卖家设置等级规则)
	 * MemberLevelSetting
	 */
	@RequestMapping(value = "/updateMemberSetting", method = RequestMethod.POST)
	public String bcMemberSetting(Model model,MemberLevelSetting memberLevelSetting,
			MemberLevelSettingModel memberLevel, HttpServletRequest request,String memberlevel)
			throws Exception {
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		
		if(null!=userId&&!"".equals(userId)){
			Map<String,Object> map  = new HashMap<String,Object>();
			map.put("memberLevel", memberLevel);
			map.put("userId", userId);
			map.put("userIp", getIpAddress.getIpAddress(request));
			boolean result = memberLevelSettingService.updateMemberSetting(map);
			if(result){
				//调用接口
				memberLevelSettingService.findFourLevels(userId, request);
			}else{
				logger.error("更新会员基础设置 失败    将不推送设置到淘宝！");	
			}
		}else{
			logger.error("用户基础会员设置 用户为空");
		}
		List<MemberLevelSetting> memberLevelSetting1 = memberLevelSettingService.findFourLevel(userId);
		model.addAttribute("memberLevelSetting", memberLevelSetting1);
		return "crms/customerManagement/memberGradation";
	}


	/* 取消所有会员折扣
	 * 接口名称 taobao.crm.grade.set (卖家设置等级规则)
	 * MemberLevelSetting
	 */
	@RequestMapping(value = "/updateDiscount", method = RequestMethod.POST)
	public String updateDiscount(MemberLevelSetting memberLevel, String discount,
			HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		memberLevel.setCtime(new Date());
		
		//调用修改方法
		memberLevelSettingService.updateDiscount(discount, userId, request);
		
		// 操作日志
		UserOperationLog o = new UserOperationLog();
		o.setFunctions("取消所有店铺客户折扣");
		o.setType("取消");
		o.setOperator(memberLevel.getUserId());
		o.setState("成功");
		o.setDate(new Date());
		o.setRemark("会员等级划分");
		userOperationLogDao.save(o);
		
		//调用接口
		memberLevelSettingService.findFourLevels(userId, request);
		return "redirect:crms/customerManagement/memberGradation";
	}

//	// 会员归类
//	@RequestMapping(value = "/rank", method = RequestMethod.POST)
//	public String memberRank(HttpServletRequest request) {
//		String userId = (String) request.getSession().getAttribute(
//				"taobao_user_nick");
//		// String userId = "crzzyboy";
//		String ip = getIpAddress.getIpAddress(request);
//		// 查询所有默认分组编号
//		List<SellerGroup> sglist = sellerGroupService
//				.findDefaultSellerGroup(userId);
//
//		// 删除会员跟组之间的关联
//		if (sglist != null && sglist.size() > 0) {
//			for (int i = 0; i < sglist.size(); i++) {
//				BuyerGroupInfo bgi = new BuyerGroupInfo();
//				bgi.setUserId(userId);
//				bgi.setGroupId(sglist.get(i).getGroupId());
//				buyerGroupInfoService.delBuyerGroupInfo(bgi);
//			}
//		}
//
//		// 查询所有默认分组设置的条件
//		List<MemberLevelSetting> mlslist = memberLevelSettingService
//				.findFourLevel(userId);
//		List<MemberInfo> memberIdlist = new ArrayList<MemberInfo>();// 存放已经分组过的会员id
//
//		List<MemberInfo> memlist = new ArrayList<MemberInfo>();//
//		int sdate = 0;
//		if (mlslist != null && mlslist.size() > 0) {
//			for (int i = mlslist.size() - 1; i >= 0; i--) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("tradeAmount", mlslist.get(i).getTradingVolume());
//				map.put("tradeCount", mlslist.get(i).getTurnover());
//
//				if (memlist != null && memlist.size() > 0) {
//					for (int j = 0; j < memlist.size(); j++) {
//
//						// 将已经添加过分组的用户编号放入list中
//						memberIdlist.add(memlist.get(j));
//					}
//					map.put("memberIdlist", memberIdlist);
//				}
//				map.put("userId", userId);
//				memlist = memberInfoService.queryList(map);
//
//				if (memlist != null && memlist.size() > 0) {
//					for (int x = 0; x < memlist.size(); x++) {
//						// 添加用户和组之间的关联关系
//						BuyerGroupInfo bg = new BuyerGroupInfo();
//						if (memlist.get(x).getId() != null) {
//							bg.setBuyerID(memlist.get(x).getId()
//									.toString());
//						}
//						if (mlslist.get(i).getGroupId() != null) {
//							bg.setGroupId(Long.parseLong(mlslist.get(i)
//									.getGroupId()));
//						}
//
//						bg.setCreatedDate(new Date());
//						bg.setUserId(userId);
//						sdate = buyerGroupInfoService.addBuyerGroupInfo(bg);
//					}
//				}
//				// 修改会员分组的用户数量
//				SellerGroup sg = new SellerGroup();
//				int sgsdate = 0;
//				if (memlist != null && memlist.size() > 0) {
//					sg.setGroupId(Long.parseLong(mlslist.get(i).getGroupId()));
//					sg.setMemberCount(memlist.size());
//					sg.setRemark(mlslist.get(i).getMemberlevel());
//					sgsdate = sellerGroupService.updateSelleGroup(sg);
//				}
//				// 添加修改会员分组日志
//				if (sgsdate > 0) {
//					UserOperationLog sgslog = new UserOperationLog();
//					if (sdate > 0) {
//						sgslog.setState("成功");
//					} else {
//						sgslog.setState("失败");
//					}
//					sgslog.setRemark("修改会员分组");
//					sgslog.setUserId(userId);
//					sgslog.setFunctions("会员分组");
//					sgslog.setType("修改");
//					sgslog.setDate(new Date());
//					sgslog.setIpAdd(ip);
//					userOperationLogDao.save(sgslog);
//				}
//			}
//			// 添加操作日志
//			UserOperationLog bglog = new UserOperationLog();
//			if (sdate > 0) {
//				bglog.setState("成功");
//				bglog.setRemark("买家用户分组信息");
//				bglog.setUserId(userId);
//				bglog.setFunctions("用户分组信息");
//				bglog.setType("添加");
//				bglog.setDate(new Date());
//				bglog.setIpAdd(ip);
//				userOperationLogDao.save(bglog);
//			} 			
//		}
//		return "redirect:crms/customerManagement/memberGradation";
//	}
	
    
	@RequestMapping(value = "/crms/customerManagement/memberGradation")
	public String memberlevelsetting(Model model, HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute(
				"taobao_user_nick");
		//先删除旧数据
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		memberLevelSettingService.findMemberlevel1(request);
		List<MemberLevelSetting> memberLevelSetting = memberLevelSettingService.findFourLevel(userId);
		model.addAttribute("memberLevelSetting", memberLevelSetting);
		return "crms/customerManagement/memberGradation";
	}
	

}
