package s2jh.biz.shop.crm.autoJob;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.domain.Data;

import freemarker.template.utility.DateUtil;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.buyers.entity.BuyerGroupInfo;
import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
import s2jh.biz.shop.crm.seller.entity.SellerGroup;
import s2jh.biz.shop.crm.seller.entity.SellerGroupRule;
/**
 * 定时任务
 * 自动更新会员所在分组
 * @author Administrator
 *
 */
@Service
@Transactional
public class AutoMemberToGroupService {

	@Autowired
	private MyBatisDao myBatisDao;
//	public void addMemberToGroup(){
//		//查询出所有当天更新的会员详情
//       Map<String,Object> map = new HashMap<String,Object>();
//       map.put("status", "1");基本原则顶替基本原则顶替基本原则顶替
//		List<MemberInfo> memberInfoList = myBatisDao.findList(MemberInfo.class.getName(), "findByLastModifiedDate", map);
//		//如果list不为空
//		if (memberInfoList!=null && memberInfoList.size()>0) {
//			//查询出所有分组的设置
//			Iterator<MemberInfo> iterator = memberInfoList.iterator();
//			//遍历memberinfo集合
//			//遍历会员详情
//			while (iterator.hasNext()) {
//				//获取当当前memberinfo
//				MemberInfo memberInfo = (MemberInfo) iterator.next();
//				String userId = memberInfo.getUserId();
//				Long tradeCount = memberInfo.getTradeCount();
//				String tradeAmount = memberInfo.getTradeAmount();
//				
//				BigDecimal avgPrice = memberInfo.getAvgPrice();
//				String province2 = memberInfo.getProvince();
//				Long gradeId = memberInfo.getGradeId();
//				String buyerId = memberInfo.getBuyerId();
//				//获取到最近一次交易时间
//				Date lastTradeTime = memberInfo.getLastTradeTime();
//				//如果userId2不为空，根据userid查询当前用户的group
//				if (userId!=null) {
//					//删除该用户和买家id下的所有groupid，并减掉groups下的membercount
//					updateMemberCount(userId,buyerId);
//					
//					List<SellerGroupRule> GroupRuleList = myBatisDao.findList(SellerGroupRule.class.getName(),"querySellerGroupByUserId", userId);
//					if (GroupRuleList!=null && GroupRuleList.size()>0) {
//						
//						//遍历所有分组的设置
//						for (SellerGroupRule sellerGroupRule : GroupRuleList) {
//							Long groupId = sellerGroupRule.getGroupId();//获取到groupId
//							
//							Integer minTradeNum = sellerGroupRule.getMinTradeNum();//获取到最小交易次数
//							Integer maxTradeNum = sellerGroupRule.getMaxTradeNum();//获取到最大交易次数
//							String minAccumulatedAmount = sellerGroupRule.getMinAccumulatedAmount();//获取到最小累计金额
//							String maxAccumulatedAmount = sellerGroupRule.getMaxAccumulatedAmount();//获取到最大累计金额
//							String minAveragePrice = sellerGroupRule.getMinAveragePrice();//获取到最小平均客价单
//							String maxAveragePrice = sellerGroupRule.getMaxAveragePrice();//获取到最大平均客价单
//							String tradeTimeStatus = sellerGroupRule.getTradeTimeStatus();//获取到tradeTimeStatus
//							String memberGrade = sellerGroupRule.getMemberGrade();//获取到会员等级
//							String province = sellerGroupRule.getProvince();//获取到所在省份
//							//判断交易时间是否符合
//							//如果不为空，则说明不是不限
//							if (tradeTimeStatus!=null&&!tradeTimeStatus.equals("")) {
//								String minTradeTime = sellerGroupRule.getMinTradeTime();//获取到最小交易时间
//								//如果最小交易时间不为空，且最小交易时间大于上次交易时间，则跳出当前循环
//								if (minTradeTime!=null && !"".equals(minTradeTime)) {
//									Date parseMinTradeTime = s2jh.biz.shop.utils.DateUtils.convertStringToDate(minTradeTime);
//									if (lastTradeTime!=null&&!"".equals(lastTradeTime)) {
//										if (parseMinTradeTime.compareTo(lastTradeTime)==1) {
//											continue;
//										}
//									}else{
//										continue;
//									}
//								}
//								//如果最大交易时间不为空，且最大交易时间小于上次交易时间，则跳出当前循环
//								String maxTradeTime = sellerGroupRule.getMaxTradeTime();//获取到最大交易时间
//								if (maxTradeTime !=null && !"".equals(maxTradeTime)) {
//									Date parseMaxTradeTime = s2jh.biz.shop.utils.DateUtils.convertStringToDate(maxTradeTime);
//									parseMaxTradeTime = DateUtils.nDaysAfter(1, parseMaxTradeTime);
//									if (lastTradeTime!=null&&!"".equals(lastTradeTime)) {
//										if (parseMaxTradeTime.compareTo(lastTradeTime)==-1) {
//											continue;
//										}
//										
//									}else{
//										continue;
//									}
//									
//								}
////								Date parseMinTradeTime = DateUtils.parseTime(minTradeTime,"yyyy-MM-dd");
////								Date parseMaxTradeTime = DateUtils.parseTime(maxTradeTime,"yyyy-MM-dd");
//							}
//							//如果最小交易次数不为空，且交易次数小于最小交易次数，跳出当前循环
//							if (minTradeNum!=null && !minTradeNum.equals("")&&tradeCount<minTradeNum) {
//								continue;
//							}
//							if (maxTradeNum!=null && !maxTradeNum.equals("") && tradeCount>maxTradeNum) {
//								continue;
//							}
//							//如果最小累计金额不为空，且最小累计金额大于累计金额，跳出当前循环
//							if (tradeAmount!=null && !"".equals(tradeAmount)) {
//								Double parseTradeAmout = Double.parseDouble(tradeAmount);
//								if (minAccumulatedAmount!=null && !"".equals(minAccumulatedAmount)) {
//									Double parseMinAmount = Double.parseDouble(minAccumulatedAmount);
//									if (parseMinAmount>parseTradeAmout) {
//										continue;
//									}
//								}
//								if (maxAccumulatedAmount!=null &&!"".equals(maxAccumulatedAmount)) {
//									Double parseMaxAmount = Double.parseDouble(maxAccumulatedAmount);
//									if (parseMaxAmount<parseTradeAmout) {
//										continue;
//									}
//								}
//							}
//							//如果最大平均客单价不为空，且最大平均客单价大于平均客价单，跳出当前循环
//							if (avgPrice!=null && !"".equals(avgPrice)) {
//								if (minAveragePrice!=null && !"".equals(minAveragePrice)) {
//									BigDecimal minAverage = new BigDecimal(minAveragePrice);
//									if (minAverage.compareTo(avgPrice)==1) {
//										continue;
//									}
//								}
//								if (maxAveragePrice!=null &&!"".equals(maxAveragePrice)) {
//									BigDecimal maxAverage = new BigDecimal(maxAveragePrice);
//									if (maxAverage.compareTo(avgPrice)==-1) {
//										continue;
//									}
//								}
//							}
//							//如果会员设置等级不为空
//							if (memberGrade!=null && !"".equals(memberGrade)&& !memberGrade.contains(String.valueOf(gradeId))) {
//								continue;
//							}
//							//如果地区设置不为空
//							if (province!=null && !"".equals(province)&&!province.contains(province2)) {
//								continue;
//							}
//							
//							BuyerGroupInfo buyerGroupInfo = new BuyerGroupInfo();
//							buyerGroupInfo.setBuyerID(buyerId+"");
//							buyerGroupInfo.setGroupId(groupId);
//							buyerGroupInfo.setUserId(userId);
//							buyerGroupInfo.setCreatedDate(new Date());
//							//添加分组情况
//							myBatisDao.execute(BuyerGroupInfo.class.getName(), "addBuyerGroupInfo", buyerGroupInfo);
//							//修改groups表中的数量
//							//根据groupID查询出sellergroup
//							SellerGroup sellerGroup = myBatisDao.findBy(SellerGroup.class.getName(), "findSellerGroupByGroupId", groupId);
//							if (sellerGroup!=null) {
//								Integer memberCount = sellerGroup.getMemberCount();
//								if (memberCount==null || "".equals(memberCount)) {
//									memberCount = 0;
//								}
//								//数量加一
//								sellerGroup.setMemberCount(memberCount+1);
//								myBatisDao.execute(SellerGroup.class.getName(), "updateSellerGroup", sellerGroup);
//								
//							}
//							
//						
//						}	
//					}
//				}
//			}
//			
//		}
//		
//	}
	/**
	 * 删除该用户和买家id下的所有groupid，并减掉groups下的membercount的方法
	 * @param userId
	 * @param buyerId
	 */
	private void updateMemberCount(String userId, String buyerId) {
		//根据userid和buyerid查询
		BuyerGroupInfo buyerGroupInfo = new BuyerGroupInfo();
		buyerGroupInfo.setBuyerID(buyerId+"");
		buyerGroupInfo.setUserId(userId);
		//上面未跳出循环表示满足条件,从groupInfo表中查是否存在
		List<BuyerGroupInfo> buyerGroupInfos = myBatisDao.findList(BuyerGroupInfo.class.getName(), "queryBuyerGroupInfos", buyerGroupInfo);
		//如果集合不为空
		if (buyerGroupInfos!=null&& buyerGroupInfos.size()>0) {
			//将其从groupinfo表中删除
			myBatisDao.execute(BuyerGroupInfo.class.getName(), "delByUserIdAndBuyerId", buyerGroupInfo);
			//遍历集合，查出groupid
			for (BuyerGroupInfo buyerGroupInfo2 : buyerGroupInfos) {
				//查出groupID
				Long groupId = buyerGroupInfo2.getGroupId();
				//根据groupID查询出sellergroup
				SellerGroup sellerGroup = myBatisDao.findBy(SellerGroup.class.getName(), "findSellerGroupByGroupId", groupId);
				if (sellerGroup!=null) {
					Integer memberCount = sellerGroup.getMemberCount();
					//如果memberCount不为空且>1则数量减一
					if (memberCount!=null&&memberCount>=1) {
						sellerGroup.setMemberCount(memberCount-1);
					//否则数量设为0
					}else{
						sellerGroup.setMemberCount(0);
					}
					myBatisDao.execute(SellerGroup.class.getName(), "updateSellerGroupMemberCount", sellerGroup);
				}
				
			}
		}
	}
	
}
