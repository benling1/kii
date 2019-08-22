//package s2jh.biz.shop.crm.schedule.job.auto;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//
//import lab.s2jh.core.dao.mybatis.MyBatisDao;
//import lab.s2jh.module.schedule.BaseQuartzJobBean;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.quartz.JobExecutionContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
//import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
//import s2jh.biz.shop.crm.member.entity.MemberLevelSetting;
//import s2jh.biz.shop.crm.member.service.MemberLevelSettingService;
// 
//
///** 
// * AutoUpdateMemberCountScheduleJob <br/> 
// * Date:     2017年04月08日 下午3:25:55 <br/> 
// * @author   zlp
// * @version  1.0
// * 更新基础会员信息
// */
//@Service
//public class AutoUpdateMemberCountScheduleJob extends BaseQuartzJobBean {
//
//	@Autowired
//	private MemberInfoService memberInfoService;
//
//	@Autowired
//	private MyBatisDao myBatisDao;
//	
//	private static final Log logger = LogFactory.getLog(AutoUpdateMemberCountScheduleJob.class);
////	public  void  autoUpdateMemberInfo(){
////		//查询已经进行基础设置的用户   
////		logger.debug("自动更新会员分组数量job开始");
////		List<MemberLevelSetting> memberLevelSettingList = myBatisDao.findList("s2jh.biz.shop.crm.member.entity.MemberLevelSetting","findSetUpUserId", null);
////		if(null!=memberLevelSettingList&&memberLevelSettingList.size()>0){
////			
////			logger.debug("设置不为空！");
////			Map<String,List<MemberLevelSetting>>  map  = new HashMap<String,List<MemberLevelSetting>>();
////			List<MemberLevelSetting>  list  = null;
////			// list 数据中会同一个卖家  会包含 多条数据    默认是四条  此方法 将数据 拆分成 一对多的关系   方便后续业务处理 
////			for (MemberLevelSetting memberLevelSetting : memberLevelSettingList) {
////				try {
////					Set<String> keySet = map.keySet();
////					if(!keySet.contains(memberLevelSetting.getUserId())){
////						list= new ArrayList<MemberLevelSetting>();
////						list.add(memberLevelSetting);
////						map.put(memberLevelSetting.getUserId(), list);
////					}else{
////						List<MemberLevelSetting> mlsList = map.get(memberLevelSetting.getUserId());
////						mlsList.add(memberLevelSetting);
////					}
////				} catch (Exception e) {
////					logger.error("自动分组处理"+memberLevelSetting.getUserId()+"会员失败！");
////				}
////			}
////			Set<String> keySet = map.keySet();
////			
////			
////			Map<String,Object>  paramMap  =  null;
////			// 遍历map的size  封装查询 满足用户分组条件的参数  包含  用户 id   普通 、  高级、 vip、 至尊vip 会员 购买数量 购买金额
////			for (String key : keySet) {
////				paramMap = new HashMap<String,Object>();
////				paramMap.put("userId", key);
////				List<MemberLevelSetting> listMemberSetting = map.get(key);
////				// 封装每个用户的设置参数   
////				for (MemberLevelSetting memberLevelSetting : listMemberSetting) {
////					createParamMap(paramMap, memberLevelSetting);
////			    }asdfasdfasdfasdfasdfasdf
////				List<MemberInfo> memberInfoList = myBatisDao.findList("s2jh.biz.shop.crm.buyers.entity.MemberInfo","queryMemberCountByLevel", paramMap);
////				updateMemberCountLogic(memberInfoList);
////			}
////		}
////    }
//	/**
//	 * 
//	 * updateMemberCountLogic:(这里用一句话描述这个方法的作用). <br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * @author zlp
//	 * @param memberInfoList
//	 */
//    @Transactional(propagation=Propagation.REQUIRES_NEW)
//	private void updateMemberCountLogic(List<MemberInfo> memberInfoList) {
//		Map<String, Object> map = null;
//		for (MemberInfo memberInfo : memberInfoList) {
//			logger.debug("开始更新会员id"+memberInfo.getUserId()+"等级"+memberInfo.getGradeId()+"数量"+memberInfo.getTradeCount());
//			if(null!=memberInfo.getUserId()&&!"".equals(memberInfo.getUserId())){
//				map = new HashMap<String, Object>();
//				map.put("userId", memberInfo.getUserId());
//				map.put("memberCount", memberInfo.getTradeCount());
//				if(memberInfo.getGradeId()==1){ map.put("group_name", "普通会员"); }
//				else if(memberInfo.getGradeId()==2){ map.put("group_name", "高级会员");}
//				else if(memberInfo.getGradeId()==3){ map.put("group_name", "VIP会员");}
//				else if(memberInfo.getGradeId()==4){ map.put("group_name", "至尊VIP会员");}
//				myBatisDao.execute("s2jh.biz.shop.crm.seller.entity.SellerGroup", "updateSellerGroupMemberCountByUserId", map);
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * createParamMap:(这里用一句话描述这个方法的作用). <br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * @author zlp
//	 * @param paramMap
//	 * @param memberLevelSetting
//	 */
//	private void createParamMap(Map<String, Object> paramMap,
//			MemberLevelSetting memberLevelSetting) {
//		if("1".equals(memberLevelSetting.getMemberlevel())) {
//			paramMap.put("tradeCount1",memberLevelSetting.getTurnover() );
//			paramMap.put("tradeAmount1",memberLevelSetting.getTradingVolume());
//		}
//		if("2".equals(memberLevelSetting.getMemberlevel())) {
//			paramMap.put("tradeCount2",memberLevelSetting.getTurnover() );
//			paramMap.put("tradeAmount2",memberLevelSetting.getTradingVolume());
//		}
//		if("3".equals(memberLevelSetting.getMemberlevel())) {
//			paramMap.put("tradeCount3",memberLevelSetting.getTurnover() );
//			paramMap.put("tradeAmount3",memberLevelSetting.getTradingVolume());
//		}
//		if("4".equals(memberLevelSetting.getMemberlevel())) {
//			paramMap.put("tradeCount4",memberLevelSetting.getTurnover() );
//			paramMap.put("tradeAmount4",memberLevelSetting.getTradingVolume());
//		}
//	}
//	/* (non-Javadoc)
//	 * @see lab.s2jh.module.schedule.BaseQuartzJobBean#executeInternalBiz(org.quartz.JobExecutionContext)
//	 */
////	@Override
////	protected String executeInternalBiz(JobExecutionContext context) {
////		autoUpdateMemberInfo();
////		return null;
////	}
////	
////	try {
////		queue.put(paramMap);
////	} catch (InterruptedException e) {
////	}
////		paramMap = new HashMap<String,Object>();
////		paramMap.put("FINALDATALOGO","123");
////		asyncUpdateMemberCount();
////	paramMap= null;
////	private void asyncUpdateMemberCount() {
////		//处理业务
////		for (int i = 0; i < 3; i++) {
////			new Thread() {
////				public void run() {
////					while (true) {
////						Map<String,Object>  map  = queue.take();
////						
////					}
////				}
////			}.start();
////		}
////	}
//
//}
