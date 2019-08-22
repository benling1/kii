package s2jh.biz.shop.crm.buyers.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.util.DateUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.buyers.dao.MemberInfoDao;
import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
import s2jh.biz.shop.crm.buyers.queue.MemberInfoQueue;
import s2jh.biz.shop.crm.manage.dao.MemberRepository;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.member.service.MemberLevelSettingService;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.order.entity.TransactionOrder;
import s2jh.biz.shop.crm.order.service.OrderService;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.taobao.info.TradesInfo;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.AreaUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BasicMember;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.CrmMembersIncrementGetRequest;
import com.taobao.api.response.CrmMembersIncrementGetResponse;

@Service
public class MemberInfoService extends BaseService<MemberInfo, Long> {
	@Autowired
	private MemberInfoDao memberInfoDao;

	@Autowired
	private TransactionOrderService transactionOrderService;

	@Autowired
	private MyBatisDao mybatisDao;

	@Autowired
	private UserInfoService userInfoSrvice;

	@Autowired
	private OrderService orderService;

	@Autowired
	private VipMemberService vipMemberService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberLevelSettingService memberLevelSettingService;

	@Override
	protected BaseDao<MemberInfo, Long> getEntityDao() {
		return memberInfoDao;
	}

	/**
	 * 根据买家卖家昵称和订单信息自动创建新的会员信息
	 * 
	 * @param map
	 *            key = buyerName ，value= 买家昵称 <br>
	 *            key = sellerName ，value= 卖家昵称 <br>
	 *            key = tid ，value= 主订单id <br>
	 *            key = type ，value= 订单成功，订单创建 订单关闭 success，create，close <br>
	 *            key = memberStatus ，value= 1 正常 2 黑名单
	 * @param myBatisDao
	 * @return MemberInfo 实例化赋值成功的对象
	 */
//	@Transactional
//	public MemberInfo getSuccessMemberInfo(Map<String, Object> map) {
//		// 为买家用户进行信息填充保存 拉黑 -。-
//		List<Map<String, Object>> listCount = this.mybatisDao.findList(
//				TransactionOrder.class.getName(), "findByMemberInfoByTrade",
//				map);
//		String tid = String.valueOf(map.get("tid"));
//		String type = (String) map.get("type");
//		MemberInfo memberInfo = new MemberInfo();
//		Trade trade = transactionOrderService.queryTrade(tid);
//		// TransactionOrder trade =
//		// this.mybatisDao.findBy(TransactionOrder.class.getName(),
//		// "isExistTransactionOrder", "3129131499585256");
//		if (trade != null) {
//			String memberStatus = (String) map.get("memberStatus");
//			Long successCount = null;// 交易成功次数
//			Long colseCount = null;// 交易关闭次数
//			String successMoney = null;// 交易成功总额
//			String colseMoney = null;// 交易关闭总额
//			Double averagePrice = null;// 平均客单价
//			Integer successNum = null;// 成功的宝贝数
//			Integer itemCloseCount = null;// 失败的宝贝数
//			Iterator<Map<String, Object>> iterCount = listCount.iterator();
//			while (iterCount.hasNext()) {
//				Map<String, Object> mapInfo = iterCount.next();
//				String status = (String) mapInfo.get("status");
//				if (TradesInfo.TRADE_FINISHED.equals(status)) { // 交易成功
//					successCount = (Long) mapInfo.get("count");
//					successMoney = (Double) mapInfo.get("total") == null ? "0"
//							: (Double) mapInfo.get("total") + "";
//					averagePrice = (Double) mapInfo.get("avg");
//					Double cowryNum = (Double) mapInfo.get("cowry");
//					if (cowryNum != null) {
//						successNum = cowryNum.intValue();
//					} else {
//						successNum = 0;
//					}
//				} else if (TradesInfo.TRADE_CLOSED_BY_TAOBAO.equals(status)) {// 交易关闭
//					colseCount = (Long) mapInfo.get("count");
//					colseMoney = (Double) mapInfo.get("total") == null ? "0"
//							: (Double) mapInfo.get("total") + "";
//					Double cowryNum = (Double) mapInfo.get("cowry");
//					if (cowryNum != null) {
//						itemCloseCount = cowryNum.intValue();
//					} else {
//						itemCloseCount = 0;
//					}
//				}
//			}
//			memberInfo.setBuyerNick(trade.getBuyerNick());// 买家昵称
//			memberInfo.setUserId(trade.getSellerNick());// 卖家昵称
//			if (memberStatus == null) {
//				memberInfo.setStatus("1");// 用户状态 正常
//			} else {
//				memberInfo.setStatus(memberStatus);// 用户状态 根据传递的参数设置
//			}
//			Date nowDate = new Date();
//			memberInfo.setLastModifiedDate(nowDate);
//			memberInfo.setGradeId(0L);
//			try {
//				memberInfo.setProvince(trade.getReceiverState());// 地区
//				memberInfo.setCity(trade.getReceiverCity());// 城市
//				memberInfo.setLastTradeTime(trade.getCreated());
//				String phone = trade.getReceiverMobile();
//				if (phone == null) {
//					phone = trade.getReceiverPhone();
//				}
//				memberInfo.setPhone(phone);
//				memberInfo.setPhoneRange(phone.substring(0, 3));
//				if ("success".equals(type)) {// 交易成功
//					long gradeid = memberLevelSettingService
//							.updateMemberInfoGrade(trade.getSellerNick(),
//									trade.getPayment(), "1",
//									trade.getBuyerNick());
//					memberInfo.setGradeId(gradeid);
//					memberInfo.setRelationSource(1);
//					memberInfo.setTradeCount(successCount == null ? 1
//							: successCount);// 交易成功笔数
//					memberInfo.setTradeAmount(successMoney == null ? trade
//							.getPayment() + "" : successMoney);// 交易成功的金额
//					memberInfo.setCloseTradeAmount(colseMoney == null ? "0"
//							: colseMoney);// 交易关闭总额
//					memberInfo.setCloseTradeCount(colseCount == null ? 0
//							: colseCount);// 交易关闭次数
//					memberInfo
//							.setAvgPrice(averagePrice == null ? new BigDecimal(
//									trade.getPayment()) : new BigDecimal(
//									averagePrice));
//					memberInfo.setItemNum(successNum == null ? Integer
//							.parseInt(trade.getNum() + "") : successNum);
//					memberInfo.setItemCloseCount(itemCloseCount == null ? 0
//							: itemCloseCount);
//				} else if ("close".equals(type)) {// 交易关闭
//					memberInfo.setRelationSource(2);
//					memberInfo.setGradeId(0L);
//					memberInfo.setTradeCount(successCount == null ? 0
//							: successCount);// 交易成功笔数
//					memberInfo.setTradeAmount(successMoney == null ? "0"
//							: successMoney);// 交易成功的金额
//					memberInfo.setCloseTradeAmount(colseMoney == null ? trade
//							.getPayment() + "" : colseMoney);// 交易关闭总额
//					memberInfo.setCloseTradeCount(colseCount == null ? 1
//							: colseCount);// 交易关闭次数
//					memberInfo
//							.setAvgPrice(averagePrice == null ? new BigDecimal(
//									"0") : new BigDecimal(averagePrice));
//					memberInfo.setItemNum(successNum == null ? 0 : successNum);
//					memberInfo
//							.setItemCloseCount(itemCloseCount == null ? Integer
//									.parseInt(trade.getNum() + "")
//									: itemCloseCount);
//				} else {
//					memberInfo.setRelationSource(2);
//					memberInfo.setGradeId(0L);
//					memberInfo.setTradeCount(successCount == null ? 0
//							: successCount);// 交易成功笔数
//					memberInfo.setTradeAmount(successMoney == null ? "0"
//							: successMoney);// 交易成功的金额
//					memberInfo.setCloseTradeAmount(colseMoney == null ? "0"
//							: colseMoney);// 交易关闭总额
//					memberInfo.setCloseTradeCount(colseCount == null ? 0
//							: colseCount);// 交易关闭次数
//					memberInfo
//							.setAvgPrice(averagePrice == null ? new BigDecimal(
//									"0") : new BigDecimal(averagePrice));
//					memberInfo.setItemNum(successNum == null ? 0 : successNum);
//					memberInfo.setItemCloseCount(itemCloseCount == null ? 0
//							: itemCloseCount);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				memberInfo.setRelationSource(2);
//				memberInfo.setGradeId(0L);
//				memberInfo.setTradeCount(successCount == null ? 0
//						: successCount);// 交易成功笔数
//				memberInfo.setTradeAmount(successMoney == null ? "0"
//						: successMoney);// 交易成功的金额
//				memberInfo.setCloseTradeAmount(colseMoney == null ? "0"
//						: colseMoney);// 交易关闭总额
//				memberInfo.setCloseTradeCount(colseCount == null ? 0
//						: colseCount);// 交易关闭次数
//				memberInfo.setAvgPrice(averagePrice == null ? new BigDecimal(
//						"0") : new BigDecimal(averagePrice));
//				memberInfo.setItemNum(successNum == null ? 0 : successNum);
//				memberInfo.setItemCloseCount(itemCloseCount == null ? 0
//						: itemCloseCount);
//			}
//			if (successCount != null && successCount > 0) {
//				memberInfo.setRelationSource(1);// 关系来源 交易成功
//			} else if (colseCount != null && colseCount > 0) {
//				memberInfo.setRelationSource(2);// 关系来源 交易关闭
//			}
//			memberInfo.setRemarks("自动创建");
//			memberInfo.setRegisterDate(nowDate);
//			List<MemberInfo> oldMemberInfo = this.mybatisDao.findList(
//					MemberInfo.class.getName(), "findMemberByNickUpdate",
//					memberInfo);
//			if (oldMemberInfo == null || oldMemberInfo.size() == 0) {
//				try {
//					this.mybatisDao.execute(MemberInfo.class.getName(),
//							"doCreateMemberByAuto", memberInfo);
//					this.mybatisDao.execute(MemberInfo.class.getName(),
//							"doUpdateMemberCount", memberInfo);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else {
//				mybatisDao.execute(MemberInfo.class.getName(),
//						"doUpdateMemberBackList", map);
//			}
//		}
//		return memberInfo;
//	}
//
//	// 根据条件分页查询会员信息
//	public List<MemberInfo> queryMemberInfo(Map<String, Object> map) {
//
//		List<MemberInfo> list = mybatisDao.findList(MemberInfo.class.getName(),
//				"queryMemberInfo", map);
//		return list;
//	}
//
//	// 根据条件查询所有会员信息的总数
//	public int queryMemberInfoCount(Map<String, Object> map) {
//		int count = mybatisDao.findBy(MemberInfo.class.getName(),
//				"queryMemberInfoCount", map);
//		return count;
//	}
//
//	// 根据买家编号查询信息
//	public List<MemberInfo> findMember(Map<String, Object> map) {
//		List<MemberInfo> list = mybatisDao.findList(MemberInfo.class.getName(),
//				"findMemberNum", map);
//		if (list != null && list.size() > 0) {
//			return list;
//		} else {
//			return null;
//		}
//	}
//
//	// 根据条件查询所有的用户信息
//	public List<MemberInfo> queryList(Map<String, Object> map) {
//		List<MemberInfo> list = mybatisDao.findList(MemberInfo.class.getName(),
//				"querylist", map);
//		if (list != null && list.size() > 0) {
//			return list;
//		} else {
//			return null;
//		}
//	}
//
//	/**
//	 * 根据条件查询所有的用户总数
//	 * 
//	 * @param map
//	 * @return
//	 */
//	public int queryListNum(Map<String, Object> map) {
//		int num = mybatisDao.findBy(MemberInfo.class.getName(), "querylistNum",
//				map);
//		return num;
//	}
//
//	/**
//	 * @Title: queryList
//	 * @Description: (通过会员的buyuerId集合查询出会员集合)
//	 * @param @param ids
//	 * @param @return 参数
//	 * @return List<MemberInfo> 返回类型
//	 * @author:jackstraw_yu
//	 * @throws
//	 */
//	public List<MemberInfo> queryList(String[] ids, String userId) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("ids", ids);
//		hashMap.put("userId", userId);
//		List<MemberInfo> list = mybatisDao.findList(MemberInfo.class.getName(),
//				"queryMemberInfoList", hashMap);
//		return list;
//	}
//
//	/**
//	 * 根据查询所有的会员信息（只取5000条）
//	 * 
//	 * @param map
//	 * @return
//	 */
//	public List<MemberInfo> queryListAll(Map<String, Object> map) {
//		List<MemberInfo> list = mybatisDao.findList(MemberInfo.class.getName(),
//				"querylist", map);
//		return list;
//	}
//
//	// 通过营销中心添加黑名单添加 更改 MemberInfo 的黑名单状态(黑名单用户)
//	public void updateStatus(Long id) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("id", id);
//		mybatisDao.execute(MemberInfo.class.getName(),
//				"updateMemberInfoStatus", map);
//	}
//
//	// 通过营销中心添加黑名单添加 更改 MemberInfo 的黑名单状态(正常用户)
//	public int updateStatusOne(Integer smsBlackListId) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("smsBlackListId", smsBlackListId);
//		return mybatisDao.execute(MemberInfo.class.getName(),
//				"updateMemberInfoStatusOne", map);
//	}
//
//	// 通过营销中心改变MemberInfo 的 SmsBlackListId
//	public void updateMemberInfoSmsBlackListId(Long ids, String buyerNick,
//			String userId) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("ids", ids);
//		map.put("buyerNick", buyerNick);
//		map.put("userId", userId);
//		mybatisDao.execute(MemberInfo.class.getName(),
//				"updateMemberInfoBlackListId", map);
//	}
//
//	// 更改MemberInfo SmsBlackListId 的值
//	public int updateMemberInfoSmsBlackListIdOne(Integer smsBlackListId) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("smsBlackListId", smsBlackListId);
//		return mybatisDao.execute(MemberInfo.class.getName(),
//				"updateSmsBlackListIdOne", map);
//
//	}
//
//	/**
//	 * @Title: queryPhoneList
//	 * @Description: (通过用户昵称list查询出手机号list)
//	 * @param nickList
//	 * @param 参数
//	 * @return List<String> 返回类型
//	 * @author:jackstraw_yu
//	 * @throws
//	 */
//	public List<String> queryPhoneList(List<String> nickList) {
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("nickList", nickList);
//		List<String> findList = mybatisDao.findList(MemberInfo.class.getName(),
//				"queryPhoneList", map);
//		return findList;
//	}
//
//	/**
//	 * 创建人：邱洋
//	 * 
//	 * @Title: 根据卖家昵称和买家昵称查询会员信息
//	 * @date 2017年2月22日--下午1:59:10
//	 * @return MemberInfo
//	 * @throws
//	 */
//	public List<MemberInfo> getMemberInfo(String taobao_user_nick,
//			String buyerNick) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", taobao_user_nick);
//		map.put("buyerNick", buyerNick);
//		List<MemberInfo> memberInfo = mybatisDao.findList(
//				MemberInfo.class.getName(), "getMemberInfo", map);
//		return memberInfo;
//	}
//
//	/**
//	 * @Title: updateMemberInfoBatch
//	 * @Description: (定时任务,批量更新用户信息)
//	 * @param 参数
//	 * @return void 返回类型
//	 * @author:jackstraw_yu
//	 * @throws
//	 */
//	@SuppressWarnings("null")
//	@Deprecated
//	public void updateMemberInfoBatch(List<String> strList) {
//
//		// 首先查询出卖家信息列表
//		List<UserInfo> userList = userInfoSrvice.queryUserInfos(strList);
//
//		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
//				taobaoInfo.appKey, taobaoInfo.appSecret);
//		CrmMembersIncrementGetRequest req = new CrmMembersIncrementGetRequest();
//		CrmMembersIncrementGetResponse rsp = null;
//		// 设置查询条件
//		long grade, currentPage;
//		Long mills = System.currentTimeMillis();
//		// 上一个小时的前10分钟
//		req.setStartModify(new Date(mills - 70 * 60 * 1000));
//		// req.setStartModify(new Date(0));
//
//		/*
//		 * req.setGrade(2L);
//		 * req.setStartModify(StringUtils.parseDateTime("2000-01-01 00:00:00"));
//		 * req.setEndModify(StringUtils.parseDateTime("2000-01-01 00:00:00"));
//		 * req.setPageSize(10L); req.setCurrentPage(5L);
//		 * CrmMembersIncrementGetResponse rsp = client.execute(req, sessionKey);
//		 * System.out.println(rsp.getBody());
//		 */
//
//		// 为防止程序执行时,部分用户的access_token过期,使用该变量补充======>次变量会进行递归
//		List<String> userIds = null;
//		// 遍历卖家集合
//		// 以一个用户为单位创建会员集合
//		List<MemberInfo> memberInfos = null;
//		List<ArrayList<BasicMember>> membersList = null;
//		if (userList != null && userList.size() > 0) {
//			userIds = new ArrayList<String>();
//			out: for (UserInfo userInfo : userList) {
//				// 如果用户的token为空,直接跳过;用户的淘宝昵称也不能为空否则也将跳过.
//				if (userInfo.getAccess_token() == null
//						|| "".equals(userInfo.getAccess_token())
//						|| userInfo.getTaobaoUserNick() == null
//						|| "".equals(userInfo.getTaobaoUserNick())) {
//					continue;
//				}
//
//				System.out.println("<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-正在获取"
//						+ userInfo.getTaobaoUserNick()
//						+ "账户下的数据->->->->->->->->->->->->->->->->->");
//
//				membersList = new ArrayList<ArrayList<BasicMember>>();
//				middle: for (int i = 0; i <= 4; i++) {
//					grade = i;
//					// 当前页最小值为1
//					inner: for (int j = 1; true; j++) {
//						currentPage = j;
//						// 会员等级从0到4循环查询
//						req.setGrade(grade);
//						req.setPageSize(100L);
//						req.setCurrentPage(currentPage);
//						try {
//							rsp = client.execute(req,
//									userInfo.getAccess_token());
//						} catch (Exception e) {
//							e.printStackTrace();
//							// 转换问题,转换程序异常: 直接全部跳出
//							/*
//							 * grade的取值不正确 会员等级的取值范围为[0,1,2,3,4]
//							 * isv.parameters-mismatch
//							 * :start_modify-and-end_modify 最早修改日期大于最迟修改日期
//							 * 检查start_modify和end_modify字段，前者的值不能大于后者
//							 * isp.crmtop-service-unavailable 当前的服务不可用 请稍侯再试
//							 * isv.invalid-permission 当前登录权限不够 检查权限
//							 * isp.service-unavailable 当前服务不可用 请联系ISP接口提供者
//							 * isp.top-remote-unknown-error 当前服务不可用 请联系ISP接口提供者
//							 */
//							break out;
//						}
//						// 用户重新登陆的时候,getAccess_token会重新刷新
//						// {"error_response":{"code":27,"msg":"Invalid session","sub_code":"invalid-sessionkey","request_id":"rxvsf5lo4ufl"}}
//						if (rsp != null && rsp.getSubCode() != null
//								&& "27".equals(rsp.getSubCode())) {
//							// 创建一个补充集合,用重新获取新的用户的token
//							userIds.add(userInfo.getTaobaoUserId());
//							// 直接查询下一个用户的会员
//							break middle;
//						} else if (rsp == null || rsp.getMembers() == null
//								|| rsp.getMembers().size() <= 0) {
//							// 返回空白:内部参数已经查询不到数据,继续外部循环
//							break inner;
//						} else {
//							ArrayList<BasicMember> members = (ArrayList<BasicMember>) rsp
//									.getMembers();
//							if (members != null || members.size() > 0) {
//								membersList.add(members);
//								System.out
//										.println("<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-正在获取"
//												+ userInfo.getTaobaoUserNick()
//												+ "账户下的:"
//												+ members.size()
//												+ "条数据->->->->->->->->->->->->->->->->->");
//							}
//						}
//					}
//				}
//				// 以会员为单位进行会员转换
//				// 重点:添加userId
//				if (membersList.size() > 0) {
//					memberInfos = convertmemberInfoList(membersList,
//							userInfo.getTaobaoUserNick());
//				}
//				// 如果membersList内容为空的时候,memberInfos没有new 此时memberInfos为null
//				if (memberInfos != null && memberInfos.size() > 0) {
//					System.out
//							.println("<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-已经转换"
//									+ userInfo.getTaobaoUserNick() + "账户下的"
//									+ memberInfos.size()
//									+ "条数据->->->->->->->->->->->->->->->->->");
//					// 保存或者更新==批量
//					saveUpdateMemberInfos(memberInfos);
//				}
//
//			}
//
//		}
//
//		// 所用用户查询完毕后,判断补充的用户是否存在
//		if (userIds != null && userIds.size() > 0) {
//			System.out.println("<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-开始递归"
//					+ userIds.size() + "条数据->->->->->->->->->->->->->->->->->");
//			// 此时将进行递归
//			// 判断集合中的元素是否为空???
//			updateMemberInfoBatch(userIds);
//		}
//
//	}
//
//	/**
//	 * @Title: convertmemberInfoList
//	 * @Description: (内部转换:将淘宝的memberInfo转换为本地的memberInfo||上层已经判空)
//	 * @author:jackstraw_yu
//	 * @throws
//	 */
//	public List<MemberInfo> convertmemberInfoList(
//			List<ArrayList<BasicMember>> membersList, String nick) {
//		List<MemberInfo> memberInfos = new ArrayList<MemberInfo>();
//		MemberInfo me = null;
//		for (ArrayList<BasicMember> arrayList : membersList) {
//			for (BasicMember bm : arrayList) {
//				me = new MemberInfo();
//				// String groupIds; 该字段没有关联
//				me.setUserId(nick);
//				me.setBuyerNick(bm.getBuyerNick());
//				me.setBuyerId(bm.getBuyerId().toString());
//				me.setCloseTradeAmount(bm.getCloseTradeAmount());
//				me.setCloseTradeCount(bm.getCloseTradeCount());
//				me.setItemNum(bm.getItemNum() == null ? null : Integer
//						.valueOf(bm.getItemNum().toString()));
//				me.setLastTradeTime(bm.getLastTradeTime());
//				me.setRelationSource(bm.getRelationSource() == null ? null
//						: Integer.valueOf(bm.getRelationSource().toString()));
//				// 为空或者正常
//				me.setStatus((bm.getStatus() == null || "normal".equals(bm
//						.getStatus())) ? "1" : "2");
//				me.setTradeAmount(me.getTradeAmount());
//				me.setTradeCount(bm.getTradeCount());
//				// 手动添加最后修改时间
//				me.setLastModifiedDate(new Date());
//				me.setGradeId(bm.getGrade());
//				// 添加会员退款状态（1为退过款，0为未退过款）
//				me.setRefundStatus("0");
//				// 设置等级名称
//				/**
//				 * 会员等级，0：店铺客户，1：普通会员，2：高级会员，3：VIP会员， 4：至尊VIP会员
//				 */
//				if (bm.getGrade() != null && 0l == bm.getGrade()) {
//					me.setCurGradeName("店铺客户");
//				} else if (bm.getGrade() != null && 1l == bm.getGrade()) {
//					me.setCurGradeName("普通会员");
//				} else if (bm.getGrade() != null && 2l == bm.getGrade()) {
//					me.setCurGradeName("高级会员");
//				} else if (bm.getGrade() != null && 3l == bm.getGrade()) {
//					me.setCurGradeName("VIP会员");
//				} else if (bm.getGrade() != null && 4l == bm.getGrade()) {
//					me.setCurGradeName("至尊VIP会员");
//				}
//
//				memberInfos.add(me);
//			}
//		}
//		return memberInfos;
//	}
//
//	/**
//	 * @title 将通过接口调取的会员集合转换成MemberDTO集合
//	 * @title 邱洋
//	 */
//	public List<MemberDTO> convertmemberInfo(List<BasicMember> membersList,
//			String nick) {
//		List<MemberDTO> memberInfos = new ArrayList<MemberDTO>();
//		MemberDTO me = null;
//		for (BasicMember cm : membersList) {
//			me = new MemberDTO();
//			me.setUserId(nick);
//			me.setBuyerNick(cm.getBuyerNick());
//			me.setBuyerId(cm.getBuyerId());
//			me.setCloseTradeAmount((cm.getCloseTradeAmount() == null
//					|| "".equals(cm.getCloseTradeAmount()) ? 0.00 : Double
//					.parseDouble(cm.getCloseTradeAmount())));
//			me.setCloseTradeCount(cm.getCloseTradeCount());
//			me.setItemNum(cm.getItemNum() == null ? null : Integer.valueOf(cm
//					.getItemNum().toString()));
//			me.setLastTradeTime((cm.getLastTradeTime() == null
//					|| "".equals(cm.getLastTradeTime()) ? 0L : DateUtils
//					.dateToLong(cm.getLastTradeTime())));
//			me.setRelationSource(cm.getRelationSource() == null ? null
//					: Integer.valueOf(cm.getRelationSource().toString()));
//			// 为空或者正常
//			me.setStatus((cm.getStatus() == null || "normal".equals(cm
//					.getStatus())) ? "1" : "2");
//			me.setTradeAmount((cm.getTradeAmount() == null
//					|| "".equals(cm.getTradeAmount()) ? 0.00 : Double
//					.parseDouble(cm.getTradeAmount())));
//			me.setTradeCount(cm.getTradeCount());
//			// 手动添加最后修改时间
//			me.setLastModifiedDate(cm.getLastTradeTime());
//			me.setGradeId(cm.getGrade());
//			// 设置等级名称
//			/**
//			 * 会员等级，0：店铺客户，1：普通会员，2：高级会员，3：VIP会员， 4：至尊VIP会员
//			 */
//			if (cm.getGrade() != null && 0l == cm.getGrade()) {
//				me.setCurGradeName("店铺客户");
//			} else if (cm.getGrade() != null && 1l == cm.getGrade()) {
//				me.setCurGradeName("普通会员");
//			} else if (cm.getGrade() != null && 2l == cm.getGrade()) {
//				me.setCurGradeName("高级会员");
//			} else if (cm.getGrade() != null && 3l == cm.getGrade()) {
//				me.setCurGradeName("VIP会员");
//			} else if (cm.getGrade() != null && 4l == cm.getGrade()) {
//				me.setCurGradeName("至尊VIP会员");
//			}
//
//			memberInfos.add(me);
//		}
//
//		return memberInfos;
//	}
//
//	/**
//	 * @Title: saveUpdateMemberInfos
//	 * @Description: (更新或者保存会员信息:存在即更新,不存在即保存)
//	 * @param @param memberInfos 参数
//	 * @return void 返回类型
//	 * @author:jackstraw_yu
//	 * @throws
//	 */
//	@Deprecated
//	public void saveUpdateMemberInfos(List<MemberInfo> memberInfos) {
//		List<MemberInfo> updateMe = new ArrayList<MemberInfo>();
//		Iterator<MemberInfo> iterator = memberInfos.iterator();
//		MemberInfo me = null;
//		boolean flag = false;
//		while (iterator.hasNext()) {
//			me = iterator.next();
//			try {
//				flag = isExist(me.getBuyerNick(), me.getUserId());
//			} catch (Exception e) {
//				flag = true;
//			}
//			// 存在
//			if (flag) {
//				updateMe.add(me);
//				iterator.remove();
//			}
//		}
//		// 判断不存在的是否还存在即入参集合是否为空,不为空批量插入
//		if (memberInfos.size() > 0) {
//			insertMemberInfoList(memberInfos);
//		}
//		if (updateMe.size() > 0) {
//			Map<String, Object> hashMap = new HashMap<String, Object>();
//			hashMap.put("updateMe", updateMe);
//			/*
//			 * try { mybatisDao.execute(MemberInfo.class.getName(),
//			 * "updateMemberInfos", hashMap); } catch (Exception e) {
//			 * e.printStackTrace(); }
//			 */
//
//		}
//
//	}
//
//	/**
//	 * @Title: isExist
//	 * @Description: (通过会员昵称与会员的卖家昵称判断会员是否存在;存在返回true,不存在返回false)
//	 * @param buyerNick
//	 * @param userId
//	 * @param @return 参数
//	 * @return boolean 返回类型
//	 * @author:jackstraw_yu
//	 * @throws
//	 */
//	@Deprecated
//	public boolean isExist(String buyerNick, String userId) {
//		Map<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("buyerNick", buyerNick);
//		hashMap.put("userId", userId);
//		// 本地会员表会 一个会员名称多个数据/手机号
//		List<MemberInfo> meList = mybatisDao.findList(
//				MemberInfo.class.getName(), "isExist", hashMap);
//		return (meList == null || meList.size() == 0) ? false : true;
//	}
//
//	/**
//	 * @Title: insertMemberInfoList
//	 * @Description: (添加新用户)
//	 * @param: @param memberInfos
//	 * @return: void
//	 * @throws
//	 * @date: 2017年4月1日 下午12:13:23
//	 * @author: jackstraw_yu
//	 */
//	@Deprecated
//	public void insertMemberInfoList(List<MemberInfo> memberInfos) {
//		for (MemberInfo memberInfo : memberInfos) {
//			/*
//			 * try { mybatisDao.execute(MemberInfo.class.getName(),
//			 * "insertMemberInfoList", memberInfo); } catch (Exception e) {
//			 * e.printStackTrace(); }
//			 */
//		}
//	}
//
//	// ===================================================
//
//	/**
//	 * 从订单中抽取出会员数据
//	 * 
//	 * @author:jackstraw_yu
//	 * 
//	 * */
//
//	// ====================================================
//	/**
//	 * @Title: convertMemberInfoData
//	 * @Description: (从订单同步中提取出会员信息)
//	 * @date 2017年4月8日 下午3:05:06
//	 * @author jackstraw_yu
//	 */
//	public void convertMemberInfoData(List<Trade> tradeList) {
//		List<MemberInfo> members = null;
//		MemberInfo mInfo = null;
//		String key = null;
//		// key--buyer_nick&sellerNick();value--MemberInfo
//		Map<String, Object> mMap = null;
//		if (tradeList != null && tradeList.size() > 0) {
//			mMap = new HashMap<String, Object>();
//			/**
//			 * 从订单里边分离出会员信息: 订单同步的部分---List<Trade>
//			 */
//			for (Trade t : tradeList) {
//				// 卖家昵称或者买家昵称为空,直接跳过
//				if (t.getBuyerNick() == null
//						|| t.getSellerNick() == null
//						|| t.getStatus() == null
//						// 只选择订单状态不会再次发生变化的订单提取出会员信息
//						|| (!"TRADE_FINISHED".equals(t.getStatus())
//								&& !"TRADE_CLOSED_BY_TAOBAO".equals(t
//										.getStatus()) && !"TRADE_CLOSED"
//									.equals(t.getStatus()))) {
//					continue;
//				}
//				// 订单数据一个买家可能回对应多个卖家,多个订单数据
//				key = t.getBuyerNick() + "&" + t.getSellerNick();
//				if (mMap.get(key) == null) {
//					mInfo = new MemberInfo();
//					mInfo.setUserId(t.getSellerNick());
//					mInfo.setBuyerNick(t.getBuyerNick());
//					mInfo.setMemberInfoCode(t.getBuyerNick(), t.getSellerNick());
//					mInfo.setPhone((t.getReceiverMobile() == null || ""
//							.equals(t.getReceiverMobile())) ? t
//							.getReceiverPhone() : t.getReceiverMobile());
//					mInfo.setLastTradeTime(t.getCreated());
//					// 交易成功
//					if ("TRADE_FINISHED".equals(t.getStatus())) {
//						mInfo.setTradeAmount((t.getPayment() == null || ""
//								.equals(t.getPayment())) ? null : t
//								.getPayment());
//						mInfo.setItemNum(t.getNum() == null ? null : Integer
//								.valueOf(t.getNum().toString()));
//						mInfo.setTradeCount(1l);
//						mInfo.setRelationSource(1);
//					}// 退款或者交易关闭都算作交易关闭
//					else if ("TRADE_CLOSED_BY_TAOBAO".equals(t.getStatus())
//							|| "TRADE_CLOSED".equals(t.getStatus())) {
//						mInfo.setCloseTradeAmount(t.getPayment() == null ? null
//								: t.getPayment().toString());
//						mInfo.setItemCloseCount(t.getNum() == null ? null
//								: Integer.valueOf(t.getNum().toString()));
//						// 交易笔记----一个主订单代表一笔
//						mInfo.setCloseTradeCount(1l);
//						mInfo.setRelationSource(2);
//					}
//					mInfo.setAvgPrice((mInfo.getTradeAmount() == null || ""
//							.equals(mInfo.getTradeAmount())) ? null
//							: new BigDecimal(Double.valueOf(mInfo
//									.getTradeAmount())
//									/ ((mInfo.getItemNum() == null || mInfo
//											.getItemNum() == 0) ? 1 : mInfo
//											.getItemNum())).setScale(2,
//									BigDecimal.ROUND_HALF_UP));
//					// 获取会员的地区
//					if (t.getReceiverState() != null
//							&& !"".equals(t.getReceiverState())) {
//						mInfo.setProvince(t.getReceiverState());
//					} else {
//						mInfo.setProvince(getArea(t.getReceiverAddress()));
//					}
//					mMap.put(key, mInfo);
//				} else {
//					mInfo = (MemberInfo) mMap.get(key);
//					mInfo.setUserId(t.getSellerNick());
//					mInfo.setBuyerNick(t.getBuyerNick());
//					mInfo.setMemberInfoCode(t.getBuyerNick(), t.getSellerNick());
//					mInfo.setPhone((t.getReceiverMobile() == null || ""
//							.equals(t.getReceiverMobile())) ? t
//							.getReceiverPhone() : t.getReceiverMobile());
//					if ((t.getCreated() == null || mInfo.getLastTradeTime() == null)) {
//						mInfo.setLastTradeTime(t.getCreated() == null ? mInfo
//								.getLastTradeTime() : t.getCreated());
//					} else {
//						mInfo.setLastTradeTime(t.getCreated().before(
//								mInfo.getLastTradeTime()) ? mInfo
//								.getLastTradeTime() : t.getCreated());
//					}
//					// 交易成功
//					if ("TRADE_FINISHED".equals(t.getStatus())) {
//						if (mInfo.getTradeAmount() == null
//								|| "".equals(mInfo.getTradeAmount())) {
//							mInfo.setTradeAmount((t.getPayment() == null || ""
//									.equals(t.getPayment())) ? null : t
//									.getPayment());
//						} else {
//							mInfo.setTradeAmount((((t.getPayment() == null || ""
//									.equals(t.getPayment())) ? 0 : Double
//									.valueOf(t.getPayment())) + Double
//									.valueOf(mInfo.getTradeAmount()))
//									+ "");
//						}
//						if (mInfo.getItemNum() == null) {
//							mInfo.setItemNum(t.getNum() == null ? null
//									: Integer.valueOf(t.getNum().toString()));
//						} else {
//							mInfo.setItemNum(t.getNum() == null ? 0 : Integer
//									.valueOf(t.getNum().toString())
//									+ mInfo.getItemNum());
//						}
//						if (mInfo.getTradeCount() == null) {
//							mInfo.setTradeCount(1l);
//						} else {
//							mInfo.setTradeCount(mInfo.getTradeCount() + 1);
//						}
//						mInfo.setRelationSource(1);
//					}// 退款或者交易关闭都算作交易关闭
//					else if ("TRADE_CLOSED_BY_TAOBAO".equals(t.getStatus())
//							|| "TRADE_CLOSED".equals(t.getStatus())) {
//						if (mInfo.getCloseTradeAmount() == null
//								|| "".equals(mInfo.getCloseTradeAmount())) {
//							mInfo.setCloseTradeAmount(t.getPayment() == null ? null
//									: t.getPayment());
//						} else {
//							mInfo.setCloseTradeAmount((Double.valueOf(mInfo
//									.getCloseTradeAmount()) + ((t.getPayment() == null || ""
//									.equals(t.getPayment())) ? 0 : Double
//									.valueOf(t.getPayment())))
//									+ "");
//						}
//						if (mInfo.getItemCloseCount() == null) {
//							mInfo.setItemCloseCount(t.getNum() == null ? null
//									: Integer.valueOf(t.getNum().toString()));
//						} else {
//							mInfo.setItemCloseCount(mInfo.getItemCloseCount()
//									+ (t.getNum() == null ? 0 : Integer
//											.valueOf(t.getNum().toString())));
//						}
//						// 交易笔记----一个主订单代表一笔
//						if (mInfo.getCloseTradeCount() == null) {
//							mInfo.setCloseTradeCount(1l);
//						} else {
//							mInfo.setCloseTradeCount(mInfo.getCloseTradeCount() + 1);
//						}
//						mInfo.setRelationSource(2);
//					}
//					mInfo.setAvgPrice((mInfo.getTradeAmount() == null || ""
//							.equals(mInfo.getTradeAmount())) ? null
//							: new BigDecimal(Double.valueOf(mInfo
//									.getTradeAmount())
//									/ ((mInfo.getItemNum() == null || mInfo
//											.getItemNum() == 0) ? 1 : mInfo
//											.getItemNum())).setScale(2,
//									BigDecimal.ROUND_HALF_UP));
//					// 获取会员的地区
//					if (t.getReceiverState() != null
//							&& !"".equals(t.getReceiverState())) {
//						mInfo.setProvince(t.getReceiverState());
//					} else {
//						mInfo.setProvince(getArea(t.getReceiverAddress()));
//					}
//					mMap.put(key, mInfo);
//				}
//			}
//			// 将map中的所有value放入集合中
//			if (!mMap.isEmpty()) {
//				members = new ArrayList<MemberInfo>();
//				MemberInfo member = null;
//				Set<String> keySet = mMap.keySet();
//				for (String str : keySet) {
//					member = (MemberInfo) mMap.get(str);
//					members.add(member);
//				}
//			}
//		}
//
//		// 判断会员集合,保存或者更新
//		if (members != null && members.size() > 0) {
//			// 使用队列单条保存
//			for (MemberInfo memberInfo : members) {
//				try {
//					memberProcessor.queue.put(memberInfo);
//				} catch (Exception e) {
//					e.printStackTrace();
//					continue;
//				}
//			}
//			/*
//			 * //使用队列批量保存 try { memberProcessor.queue.put(members); } catch
//			 * (Exception e) { e.printStackTrace(); }
//			 */
//		}
//
//	}
//
//	/**
//	 * @Title: convertMemberInfos
//	 * @Description: (从导入订单中提取出会员信息并保存)
//	 * @date 2017年4月8日 下午3:40:43
//	 * @author jackstraw_yu
//	 */
//	public void convertMemberInfos(List<TransactionOrder> tradeList) {
//		List<MemberInfo> members = null;
//		MemberInfo mInfo = null;
//		String key = null;
//		// key--buyer_nick&sellerNick();value--MemberInfo
//		Map<String, Object> mMap = null;
//		if (tradeList != null && tradeList.size() > 0) {
//			mMap = new HashMap<String, Object>();
//			/**
//			 * 从订单里边分离出会员信息: 订单导入部分---List<TransactionOrder>
//			 */
//			// 订单导入部分---List<TransactionOrder>
//			for (TransactionOrder t : tradeList) {
//				// 卖家昵称或者买家昵称为空,直接跳过
//				if (t.getBuyerNick() == null
//						|| t.getSellerNick() == null
//						|| t.getStatus() == null
//						// 只选择订单状态不会再次发生变化的订单提取出会员信息
//						|| (!"TRADE_FINISHED".equals(t.getStatus())
//								&& !"TRADE_CLOSED_BY_TAOBAO".equals(t
//										.getStatus()) && !"TRADE_CLOSED"
//									.equals(t.getStatus()))) {
//					continue;
//				}
//				// 订单数据一个买家可能回对应多个卖家,多个订单数据
//				key = t.getBuyerNick() + "&" + t.getSellerNick();
//				if (mMap.get(key) == null) {
//					mInfo = new MemberInfo();
//					mInfo.setUserId(t.getSellerNick());
//					mInfo.setBuyerNick(t.getBuyerNick());
//					mInfo.setMemberInfoCode(t.getBuyerNick(), t.getSellerNick());
//					mInfo.setPhone((t.getReceiverMobile() == null || ""
//							.equals(t.getReceiverMobile())) ? t
//							.getReceiverPhone() : t.getReceiverMobile());
//					mInfo.setLastTradeTime(t.getCreated());
//					// 交易成功
//					if ("TRADE_FINISHED".equals(t.getStatus())) {
//						mInfo.setTradeAmount(t.getPayment() == null ? null : t
//								.getPayment().toString());
//						mInfo.setItemNum((t.getNum() == null || "".equals(t
//								.getNum())) ? null
//								: Integer.valueOf(t.getNum()));
//						// 交易笔记----一个主订单代表一笔
//						mInfo.setTradeCount(1l);
//						mInfo.setRelationSource(1);
//					}// 退款或者交易关闭都算作交易关闭
//					else if ("TRADE_CLOSED_BY_TAOBAO".equals(t.getStatus())
//							|| "TRADE_CLOSED".equals(t.getStatus())) {
//						mInfo.setCloseTradeAmount(t.getPayment() == null ? null
//								: t.getPayment().toString());
//						mInfo.setItemCloseCount((t.getNum() == null || ""
//								.equals(t.getNum())) ? null : Integer.valueOf(t
//								.getNum()));
//						// 交易笔记----一个主订单代表一笔
//						mInfo.setCloseTradeCount(1l);
//						mInfo.setRelationSource(2);
//					}
//					// 通过上述赋值的值来计算会员的客单价-----总金额/商品数量
//					mInfo.setAvgPrice((mInfo.getTradeAmount() == null || ""
//							.equals(mInfo.getTradeAmount())) ? null
//							: new BigDecimal(Double.valueOf(mInfo
//									.getTradeAmount())
//									/ ((mInfo.getItemNum() == null || mInfo
//											.getItemNum() == 0) ? 1 : mInfo
//											.getItemNum())).setScale(2,
//									BigDecimal.ROUND_HALF_UP));
//					// 获取会员的地区
//					if (t.getReceiverState() != null
//							&& !"".equals(t.getReceiverState())) {
//						mInfo.setProvince(t.getReceiverState());
//					} else {
//						mInfo.setProvince(getArea(t.getReceiverAddress()));
//					}
//					mMap.put(key, mInfo);
//				} else {
//					mInfo = (MemberInfo) mMap.get(key);
//					mInfo.setUserId(t.getSellerNick());
//					mInfo.setBuyerNick(t.getBuyerNick());
//					mInfo.setMemberInfoCode(t.getBuyerNick(), t.getSellerNick());
//					mInfo.setPhone((t.getReceiverMobile() == null || ""
//							.equals(t.getReceiverMobile())) ? t
//							.getReceiverPhone() : t.getReceiverMobile());
//					if ((t.getCreated() == null || mInfo.getLastTradeTime() == null)) {
//						mInfo.setLastTradeTime(t.getCreated() == null ? mInfo
//								.getLastTradeTime() : t.getCreated());
//					} else {
//						mInfo.setLastTradeTime(t.getCreated().before(
//								mInfo.getLastTradeTime()) ? mInfo
//								.getLastTradeTime() : t.getCreated());
//					}
//					// 交易成功
//					if ("TRADE_FINISHED".equals(t.getStatus())) {
//						if (mInfo.getTradeAmount() == null
//								|| "".equals(mInfo.getTradeAmount())) {
//							mInfo.setTradeAmount(t.getPayment() == null ? null
//									: t.getPayment().toString());
//						} else {
//							mInfo.setTradeAmount(((t.getPayment() == null ? 0
//									: t.getPayment()) + Double.valueOf(mInfo
//									.getTradeAmount()))
//									+ "");
//						}
//						if (mInfo.getItemNum() == null) {
//							mInfo.setItemNum((t.getNum() == null || "".equals(t
//									.getNum())) ? null : Integer.valueOf(t
//									.getNum()));
//						} else {
//							mInfo.setItemNum((t.getNum() == null || "".equals(t
//									.getNum())) ? 0 : Integer.valueOf(t
//									.getNum()) + mInfo.getItemNum());
//						}
//						if (mInfo.getTradeCount() == null) {
//							mInfo.setTradeCount(1l);
//						} else {
//							mInfo.setTradeCount(mInfo.getTradeCount() + 1);
//						}
//						mInfo.setRelationSource(1);
//					}// 退款或者交易关闭都算作交易关闭
//					else if ("TRADE_CLOSED_BY_TAOBAO".equals(t.getStatus())
//							|| "TRADE_CLOSED".equals(t.getStatus())) {
//						if (mInfo.getCloseTradeAmount() == null
//								|| "".equals(mInfo.getCloseTradeAmount())) {
//							mInfo.setCloseTradeAmount(t.getPayment() == null ? null
//									: t.getPayment().toString());
//						} else {
//							mInfo.setCloseTradeAmount((Double.valueOf(mInfo
//									.getCloseTradeAmount()) + (t.getPayment() == null ? 0
//									: t.getPayment()))
//									+ "");
//						}
//						if (mInfo.getItemCloseCount() == null) {
//							mInfo.setItemCloseCount((t.getNum() == null || ""
//									.equals(t.getNum())) ? null : Integer
//									.valueOf(t.getNum()));
//						} else {
//							mInfo.setItemCloseCount(mInfo.getItemCloseCount()
//									+ ((t.getNum() == null || "".equals(t
//											.getNum())) ? 0 : Integer.valueOf(t
//											.getNum())));
//						}
//						// 交易笔记----一个主订单代表一笔
//						if (mInfo.getCloseTradeCount() == null) {
//							mInfo.setCloseTradeCount(1l);
//						} else {
//							mInfo.setCloseTradeCount(mInfo.getCloseTradeCount() + 1);
//						}
//						mInfo.setRelationSource(2);
//					}
//					// 通过上述赋值的值来计算会员的客单价
//					mInfo.setAvgPrice((mInfo.getTradeAmount() == null || ""
//							.equals(mInfo.getTradeAmount())) ? null
//							: new BigDecimal(Double.valueOf(mInfo
//									.getTradeAmount())
//									/ ((mInfo.getItemNum() == null || mInfo
//											.getItemNum() == 0) ? 1 : mInfo
//											.getItemNum())).setScale(2,
//									BigDecimal.ROUND_HALF_UP));
//					// 获取会员的地区
//					if (t.getReceiverState() != null
//							&& !"".equals(t.getReceiverState())) {
//						mInfo.setProvince(t.getReceiverState());
//					} else {
//						mInfo.setProvince(getArea(t.getReceiverAddress()));
//					}
//					mMap.put(key, mInfo);
//				}
//			}
//		}
//		// 将map中的所有value放入集合中
//		if (!mMap.isEmpty()) {
//			members = new ArrayList<MemberInfo>();
//			MemberInfo member = null;
//			Set<String> keySet = mMap.keySet();
//			for (String str : keySet) {
//				member = (MemberInfo) mMap.get(str);
//				members.add(member);
//			}
//		}
//		// 判断会员集合,保存或者更新
//		if (members != null && members.size() > 0) {
//			// 使用队列单条保存
//			for (MemberInfo memberInfo : members) {
//				try {
//					memberProcessor.queue.put(memberInfo);
//				} catch (Exception e) {
//					e.printStackTrace();
//					continue;
//				}
//			}
//			/*
//			 * try { memberProcessor.queue.put(members); } catch (Exception e) {
//			 * e.printStackTrace(); }
//			 */
//		}
//	}
//
//	// ============================================================
//
//	/**
//	 * 使用队列处理会员信息
//	 * */
//
//	// ============================================================
//
//	@Autowired
//	private MemberInfoQueue memberProcessor;
//
//	private static final Log logger = LogFactory
//			.getLog(MemberInfoService.class);
//
//	/**
//	 * @Title: processMemberInfo
//	 * @Description: (队列处理会员>>处理单条会员数据)
//	 * @date 2017年4月12日 下午5:03:14
//	 * @author jackstraw_yu
//	 */
//	public void processMemberInfo(MemberInfo memberInfo) {
//		Long mId = null;
//		boolean flag = false;
//		try {
//			mId = isExistEnhance(memberInfo);
//			// 为空即不存在,反之存在
//			flag = (mId == null ? false : true);
//		} catch (Exception e) {
//			flag = true;
//		}
//		// 存在
//		if (flag) {
//			// 存在时使用主键更新
//			memberInfo.setId(mId);
//			// 更新会员
//			updateMemberInfo(memberInfo);
//		} else {
//			try {
//				// 保存
//				saveMemberInfo(memberInfo);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	/**
//	 * @Title: processMemberInfosBatch
//	 * @Description: (队列处理会员>>处理单条会员数据)
//	 * @date 2017年6月17日 下午4:28:16
//	 * @author: jackstraw_yu
//	 */
//	public void processMemberInfosBatch(List<MemberInfo> members) {
//		List<MemberInfo> updateMe = new ArrayList<MemberInfo>();
//		Iterator<MemberInfo> iterator = members.iterator();
//		MemberInfo memberInfo = null;
//		Long mId = null;
//		boolean flag = false;
//		while (iterator.hasNext()) {
//			memberInfo = iterator.next();
//			try {
//				mId = isExistEnhance(memberInfo);
//				// 为空即不存在,反之存在
//				flag = mId == null ? false : true;
//			} catch (Exception e) {
//				flag = true;
//			}
//			// 存在
//			if (flag) {
//				// 存在时使用主键更新
//				memberInfo.setId(mId);
//				updateMe.add(memberInfo);
//				iterator.remove();
//			}
//		}
//		// 判断不存在的是否还存在即入参集合是否为空,不为空批量插入
//		if (members.size() > 0) {
//			saveMembersBatch(members);
//		}
//		if (updateMe.size() > 0) {
//			updateMembersBatch(updateMe);
//		}
//
//	}
//
//	/**
//	 * @Title: saveMemberInfoBatch
//	 * @Description: (订单同步>>批量保存会员信息)
//	 * @date 2017年6月17日 下午4:34:37
//	 * @author jackstraw_yu
//	 * @throws
//	 */
//	public void saveMembersBatch(List<MemberInfo> members) {
//		Map<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("members", members);
//		try {
//			mybatisDao.execute(MemberInfo.class.getName(), "saveMembersBatch",
//					hashMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * @Title: saveMemberInfo
//	 * @Description: (保存会员信息>>保存单条会员信息)
//	 * @date 2017年4月17日 下午3:10:36
//	 * @author jackstraw_yu
//	 */
//	public void saveMemberInfo(MemberInfo memberInfo) {
//		try {
//			mybatisDao.execute(MemberInfo.class.getName(), "saveMemberInfo",
//					memberInfo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @Title: updateMembersBatch
//	 * @Description: (更新会员信息操作>>批量更新)
//	 * @param: @param members
//	 * @return: void
//	 * @throws
//	 * @date: 2017年4月5日 下午5:57:00
//	 * @author: jackstraw_yu
//	 */
//	public void updateMembersBatch(List<MemberInfo> members) {
//		Map<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("members", members);
//		try {
//			mybatisDao.execute(MemberInfo.class.getName(),
//					"updateMembersBatch", hashMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * @Title: updateMemberInfo
//	 * @Description: (队列单条处理数据>>更新单条会员信息)
//	 * @date 2017年4月14日 上午10:47:53
//	 * @author jackstraw_yu
//	 */
//	public void updateMemberInfo(MemberInfo memberInfo) {
//		try {
//			mybatisDao.execute(MemberInfo.class.getName(), "updateMemberInfo",
//					memberInfo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * @Title: isExistEnhance
//	 * @Description: (判断会员是否存在:会员昵称,卖家昵称)
//	 * @date 2017年4月11日 上午9:06:39
//	 * @author jackstraw_yu
//	 */
//	public Long isExistEnhance(MemberInfo memberInfo) {
//		Long isExist = null;
//		Map<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("buyerNick", memberInfo.getBuyerNick());
//		hashMap.put("userId", memberInfo.getUserId());
//		// 本地会员表会 一个会员名称多个数据/手机号
//		isExist = mybatisDao.findBy(MemberInfo.class.getName(),
//				"isExistEnhance", hashMap);
//		return isExist;
//	}
//
//	/**
//	 * @Title: getArea
//	 * @Description: (内部方法调用,调用工具的静态方法返回所在城市)
//	 * @date: 2017年4月6日 下午7:59:47
//	 * @author: jackstraw_yu
//	 */
//	private String getArea(String area) {
//		String place = null;
//		if (area != null && !"".equals(area)) {
//			String[] split = area.split("市|省|\b|,|，| ");
//			List<String> asList = Arrays.asList(split);
//			place = AreaUtils.getArea(asList);
//		}
//		return place;
//	}
//
//	/**
//	 * 创建人：邱洋
//	 * 
//	 * @Title: 点击会员营销时，查询当前默认分组的所有会员信息
//	 * @date 2017年4月11日--上午11:18:34
//	 * @return List<MemberInfo>
//	 * @throws
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public List<MemberInfo> getDefaultGroupMemberInfo(Map map) {
//		List<MemberInfo> list = new ArrayList<MemberInfo>();
//		list = mybatisDao.findList(MemberInfo.class.getName(),
//				"getDefaultGroupMemberInfo", map);
//		return list;
//	}
//
//	/**
//	 * 点击会员营销时，查询当前默认分组的所有会员总数量
//	 * 
//	 * @param map
//	 * @return int
//	 */
//	public int getDefaultGroupMemberInfoNum(Map<String, Object> map) {
//		int num = mybatisDao.findBy(MemberInfo.class.getName(),
//				"getDefaultGroupMemberInfoNum", map);
//		return num;
//	}
//
//	/**
//	 * 根据源号码查询会员信息 ZTK2017年5月16日下午2:45:50
//	 */
//	public MemberInfo findMemberInfoByPhone(String phone) {
//		List<MemberInfo> memberInfos = mybatisDao.findList(
//				MemberInfo.class.getName(), "findMemberInfoByPhone", phone);
//		if (memberInfos != null && memberInfos.size() > 0) {
//			return memberInfos.get(0);
//		}
//		return null;
//	}
//
//	/**
//	 * @Title: queryLimitList
//	 * @Description: (根据条件查询出会员集合 营销中心:会员筛选==前100条数据)
//	 * @param dataMap
//	 * @param 设定文件
//	 * @return List<MemberInfo> 返回类型
//	 * @date 2017年5月22日 下午3:44:31
//	 * @author jackstraw_yu
//	 * @throws
//	 */
//	public List<MemberInfo> queryLimitList(Map<String, Object> dataMap) {
//
//		return mybatisDao.findList(MemberInfo.class.getName(),
//				"queryLimitList", dataMap);
//	}
//
//	/**
//	 * @Title: queryMemberCount
//	 * @Description: 根据条件查询出会员总条数)
//	 * @param @param dataMap
//	 * @param @return 设定文件
//	 * @return Object 返回类型
//	 * @date 2017年5月22日 下午3:46:30
//	 * @author jackstraw_yu
//	 * @throws
//	 */
//	public Long queryMemberCount(Map<String, Object> dataMap) {
//
//		return mybatisDao.findBy(MemberInfo.class.getName(),
//				"queryMemberCount", dataMap);
//	}
//
//	/**
//	 * @Title: queryNumList
//	 * @Description: (会员短信群发：直接根据条件查询手机号 )
//	 * @param @param dataMap
//	 * @param @return 设定文件
//	 * @return List<String> 返回类型
//	 * @date 2017年5月25日 上午9:05:53
//	 * @author jackstraw_yu
//	 * @throws
//	 */
//	public List<String> queryNumList(Map<String, Object> dataMap) {
//		if (dataMap.containsKey("memberGroup")
//				&& dataMap.get("memberGroup") != null
//				&& (boolean) dataMap.get("memberGroup") == true) {
//			return mybatisDao.findList(MemberInfo.class.getName(),
//					"getMemberInfoToPhone", dataMap);
//		} else {
//			return mybatisDao.findList(MemberInfo.class.getName(),
//					"queryNumList", dataMap);
//		}
//	}
//
//	/**
//	 * 通过昵称查询会员等级 ZTK2017年6月14日下午8:00:19
//	 */
//
//	public MemberInfo findMemberInfoByNick(Map<String, Object> dataMap) {
//		MemberInfo memberInfo = mybatisDao.findBy(MemberInfo.class.getName(),
//				"findMemberInfoByNick", dataMap);
//		if (memberInfo != null && !"".equals(memberInfo)) {
//			return memberInfo;
//		} else {
//			return null;
//		}
//	}
//
//	public String findMemberInfoByNick(String userId, String buyerNick) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("buyerNick", buyerNick);
//		String curGradeName = mybatisDao.findBy(MemberInfo.class.getName(),
//				"findMemberInfoByNick", map);
//		return curGradeName;
//
//	}
//
//	/**
//	 * 创建人：邱洋
//	 * 
//	 * @title 修改用户的退款状态
//	 * @DATE 2017-06-13 10:39
//	 * @param memberInfos
//	 */
//	public void UpdateMemberInfoRerundStatus(List<MemberInfo> memberInfos) {
//		if (memberInfos != null && memberInfos.size() > 0) {
//			Map<String, Object> hashMap = new HashMap<String, Object>();
//			hashMap.put("memberInfos", memberInfos);
//			try {
//				mybatisDao.execute(MemberInfo.class.getName(),
//						"updateMemberInfoRefundStatus", hashMap);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 线程调用更新用户退款状态和订单的退款状态
//	 * 
//	 * @param memberInfos
//	 * @param orderList
//	 */
//	public void updateRerundStatus(List<MemberInfo> memberInfos,
//			List<Orders> orderList) {
//		try {
//			UpdateMemberInfoRerundStatus(memberInfos);
//			orderService.updateOrdersRefundStatus(orderList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @title 邱洋
//	 * @title 根据会员昵称拉取会员的交易数据
//	 * @date 2017-06-23 11:54:30
//	 * @param userId
//	 * @param list
//	 */
//	public void updateMemberInfos(String userId) {
//		Long pageSize = 0L;
//		// 调用查询会员总数的方法
//		Long memberNum = getMemberInfoDateNumber(userId);
//
//		// 获取总页数
//		if (memberNum % 100 == 0) {
//			pageSize = memberNum / 100;
//		} else {
//			pageSize = (memberNum / 100) + 1;
//		}
//		// 根据总页数分面调取接口
//		for (Long i = 1L; i <= pageSize; i++) {
//			asyncHandleData(userId, i);
//		}
//		// String buyerNick = null;
//		//
//		// if(list==null){
//		// int start = 0;
//		// Map<String,Object> map = new HashMap<String, Object>();
//		// map.put("userId", userId);
//		// map.put("maxNum", 5000);
//		// while(true){
//		// list = new ArrayList<MemberInfo>();
//		// map.put("start", start);
//		// try {
//		// list = queryList(map);
//		// if(list!=null&&list.size()>0){
//		// for(int i=0;i<list.size();i++){
//		// if(buyerNick == null ){
//		// buyerNick = list.get(i).getBuyerNick();
//		// }else{
//		// buyerNick = buyerNick + "," + list.get(i).getBuyerNick();
//		// }
//		// if((i!=0&&(i%100)==0)||buyerNick.length()>1000){
//		// buyerNick.replace(","+list.get(i).getBuyerNick(), "");
//		// asyncHandleData(userId, buyerNick);
//		// buyerNick = list.get(i).getBuyerNick();
//		// continue;
//		// }
//		// }
//		// start ++;
//		// }else{
//		// break;
//		// }
//		// } catch (Exception e) {
//		// e.printStackTrace();
//		// }
//		// }
//		// }else{
//		// for(int i=0;i<list.size();i++){
//		// if(buyerNick == null ){
//		// buyerNick = list.get(i).getBuyerNick();
//		// }else{
//		// buyerNick = buyerNick + "," + list.get(i).getBuyerNick();
//		// }
//		// if((i%100)==0||buyerNick.length()>1000){
//		// buyerNick.replace(","+list.get(i).getBuyerNick(), "");
//		// asyncHandleData(userId, buyerNick);
//		// buyerNick = list.get(i).getBuyerNick();
//		// continue;
//		// }
//		// }
//		// }
//	}
//
//	/**
//	 * 创建人：邱洋
//	 * 
//	 * @Title: updateMemberInfoDateNumber
//	 * @Description: TODO(获取用户在淘宝的客户总数)
//	 * @param @param userId
//	 * @param @param request 设定文件
//	 * @return void 返回类型
//	 * @throws
//	 */
//	public Long getMemberInfoDateNumber(String userId) {
//		Long Number = 0L;
//		String session = null;
//		// 根据用户昵称获取调用接口的sessionkey
//		try {
//			UserInfo ui = userInfoSrvice.getUserInfoByNick(userId);
//			if (ui != null && session == null) {
//				session = ui.getAccess_token();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// 调取批量获取会员接口，返回会员总数
//		CrmMembersIncrementGetResponse rsp = null;
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
//					taobaoInfo.appKey, taobaoInfo.appSecret);
//			CrmMembersIncrementGetRequest req = new CrmMembersIncrementGetRequest();
//			req.setPageSize(100L);
//			req.setCurrentPage(1L);
//			rsp = client.execute(req, session);
//			Number = rsp.getTotalResult();
//		} catch (ApiException e) {
//			e.printStackTrace();
//		}
//		return Number;
//	}
//
//	/**
//	 * @title 邱洋
//	 * @title 根据客户昵称和会员昵称调取会员信息
//	 * @param userId
//	 * @param buyerNick
//	 * @param session
//	 * @return
//	 */
//	public void getMemberInfoInteface(String userId, Long pageNo) {
//		/* 根据客户名称查询用户的accessToken */
//		String session = null;
//		try {
//			UserInfo ui = userInfoSrvice.getUserInfoByNick(userId);
//			if (ui != null) {
//				session = ui.getAccess_token();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url,
//				taobaoInfo.appKey, taobaoInfo.appSecret);
//		CrmMembersIncrementGetRequest req = new CrmMembersIncrementGetRequest();
//		req.setPageSize(100L);
//		req.setCurrentPage(pageNo);
//		CrmMembersIncrementGetResponse rsp = null;
//		try {
//			rsp = client.execute(req, session);
//		} catch (ApiException e1) {
//			e1.printStackTrace();
//		}
//		List<BasicMember> members = null;
//		if (rsp.getMembers() != null && rsp.getMembers().size() > 0) {
//			members = rsp.getMembers();
//		}
//		List<MemberDTO> milist = new ArrayList<MemberDTO>();
//		try {
//			milist = convertmemberInfo(members, userId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		divisionInsertOrUpdate(milist, userId);
//	}
//
//	/**
//	 * 创建人：邱洋
//	 * 
//	 * @Title: divisionInsertOrUpdate
//	 * @Description: TODO(判断查询到的会员执行insert还是update操作)
//	 * @param @param list 设定文件
//	 * @return void 返回类型
//	 * @throws
//	 */
//	public void divisionInsertOrUpdate(List<MemberDTO> list, String userId) {
//		List<String> nameList = new ArrayList<String>();
//		if (list != null && list.size() > 0) {
//			for (MemberDTO md : list) {
//				if (md != null && md.getBuyerNick() != null) {
//					nameList.add(md.getBuyerNick());
//				}
//			}
//		}
//		List<MemberDTO> updateList = new ArrayList<MemberDTO>();// 存放更新的会员信息
//		List<MemberDTO> delList = new ArrayList<MemberDTO>();// 存放根据会员昵称查询到的会员信息
//		if (nameList != null && nameList.size() > 0) {
//			delList = vipMemberService.queryMemberByNicks(nameList, userId);
//		}
//		// 将需要update的会员名称筛选出来放入delList中
//		if (updateList != null && updateList.size() > 0) {
//			for (MemberDTO me : list) {
//				for (MemberDTO me1 : delList) {
//					if (me.getBuyerNick().equals(me1.getBuyerNick())) {
//						updateList.add(me);
//					}
//				}
//			}
//		}
//		if (updateList != null && updateList.size() > 0) {
//			list.removeAll(updateList);
//			// 更新会员数据
//			updateMeberDTO(updateList, userId);
//		}
//		if (list != null && list.size() > 0) {
//			// 批量插入新会员
//			vipMemberService.insertMemberDTOList(list, userId);
//		}
//	}
//
//	/**
//	 * 创建人：邱洋
//	 * 
//	 * @Title: updateMeberDTO
//	 * @Description: TODO(更新会员的数据)
//	 * @param @param list
//	 * @param @param userId 设定文件
//	 * @return void 返回类型
//	 * @throws
//	 */
//	public void updateMeberDTO(List<MemberDTO> list, String userId) {
//		if (list != null && list.size() > 0) {
//			for (MemberDTO me : list) {
//				Query query = new Query();
//				Update update = new Update();
//				query.addCriteria(Criteria.where("buyerNick").is(
//						me.getBuyerNick()));
//				if (me != null) {
//					if (me.getTradeCount() != null) {
//						update.set("tradeCount", me.getTradeCount());
//					}
//					if (me.getTradeAmount() != null) {
//						update.set("tradeAmount", me.getTradeAmount());
//					}
//					if (me.getCloseTradeAmount() != null) {
//						update.set("closeTradeAmount", me.getCloseTradeAmount());
//					}
//					if (me.getCloseTradeCount() != null) {
//						update.set("closeTradeCount", me.getCloseTradeCount());
//					}
//					if (me.getItemNum() != null) {
//						update.set("itemNum", me.getItemNum());
//					}
//					if (me.getItemCloseCount() != null) {
//						update.set("itemCloseCount", me.getItemCloseCount());
//					}
//					if (me.getLastTradeTime() != null) {
//						update.set("lastTradeTime", me.getLastTradeTime());
//					}
//					if (me.getGradeId() != null) {
//						update.set("gradeId", me.getGradeId());
//					}
//					if (me.getRelationSource() != null) {
//						update.set("relationSource", me.getRelationSource());
//					}
//					if (me.getLastModifiedDate() != null) {
//						update.set("lastModifiedDate", me.getLastModifiedDate());
//					}
//					if (me.getStatus() != null) {
//						update.set("status", me.getStatus());
//					}
//					if (me.getTradeAmount() != null
//							&& me.getTradeCount() != null) {
//						update.set("avgPrice",
//								(me.getTradeAmount() / me.getTradeCount()));
//					}
//				}
//				try {
//					memberRepository.update(query, update, userId);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	/**
//	 * @title 创建人：邱洋
//	 * @Title: 开启一个线程同步新用户一天之内的会员数据
//	 * @date 2017年6月23日--上午12:06:43
//	 * @return void
//	 * @throws
//	 */
//	private void asyncHandleData(final String userId, final Long pageNo) {
//		MyFixedThreadPool.getMyMemberInfoThreadPool().execute(new Thread() {
//			@Override
//			public void run() {
//				getMemberInfoInteface(userId, pageNo);
//			}
//		});
//	}
//
//	/**
//	 * 会员营销效果分析————客户详情页面 ZTK2017年7月4日下午4:57:00
//	 */
//	@Deprecated
//	public Pagination memberEffectList(String userId, Long msgId,
//			Date beginTime, Date endTime, String buyerNick, String phone,
//			String itemId, Integer pageNo) {
//		Criteria criteria = new Criteria();
//		criteria.and("userId").is(userId).and("created").gte(beginTime)
//				.lte(endTime);
//		if (msgId != null) {
//			criteria.and("msgId").is(msgId);
//		}
//		if (buyerNick != null && !"".equals(buyerNick)) {
//			criteria.and("buyerNick").is(buyerNick);
//		}
//		if (phone != null && !"".equals(phone)) {
//			criteria.and("phone").is(phone);
//		}
//		if (itemId != null && !"".equals(itemId)) {
//			criteria.and("NUM_IID").is(itemId);
//		}
//		Query query = new Query(criteria);
//		long count = memberRepository.count(query, userId);
//		Pageination<MemberDTO> initPage = new Pageination<MemberDTO>(pageNo,
//				10, count);
//		Pageination<MemberDTO> memberPage = memberRepository.findPage(initPage,
//				query, userId);
//		List<MemberDTO> memberList = memberPage.getDatas();
//		/*
//		 * Pagination pagination = new Pagination(pageNo, 10, count,
//		 * memberList);
//		 */
//		return null;
//	}
//
//	
//	/**
//	 * 批量修改会员状态
//	 */
//	public void processDatas(Map<String,Object> map){
//		System.out.println("........................"+map.entrySet().size());
//		String userId=(String) map.get("userId");
//		String type = (String) map.get("type");
//		List<String> data=(List<String>) map.get("datas");
//		MemberDTO memberInfo = null;
//		for (String mobile : data) {
//			if("2".equals(type)){
//				memberInfo =new MemberDTO();
//				memberInfo.setUserId(userId);
//				memberInfo.setBuyerNick(mobile);
//				List<MemberDTO> findListMemberDTOByParam = vipMemberService.findListMemberDTOByParam(memberInfo);
//				for (MemberDTO memberDTO : findListMemberDTOByParam) {
//					memberInfo = new MemberDTO();
//					memberInfo.setUserId(userId);
//					memberInfo.setBuyerNick(memberDTO.getBuyerNick());
//					memberInfo.setBlackStatus(1);
//					vipMemberService.updateBlackMemberInfoStatus(memberInfo, type);
//					/*vipMemberService.updateMemberInfoByParam(memberInfo,1);*/
//				}
//			}else{
//				memberInfo =new MemberDTO();
//				memberInfo.setUserId(userId);
//				memberInfo.setPhone(mobile);
//				List<MemberDTO> findListMemberDTOByParam = vipMemberService.findListMemberDTOByParam(memberInfo);
//				for (MemberDTO memberDTO : findListMemberDTOByParam) {
//					memberInfo = new MemberDTO();
//					memberInfo.setUserId(userId);
//					memberInfo.setBuyerNick(memberDTO.getBuyerNick());
//					memberInfo.setBlackStatus(1);
//					vipMemberService.updateBlackMemberInfoStatus(memberInfo, type);
//					/*vipMemberService.updateMemberInfoByParam(memberInfo,1);*/
//				}
//			}
//			
//		}
//	}
}
