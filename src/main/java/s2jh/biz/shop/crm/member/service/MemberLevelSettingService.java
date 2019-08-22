package s2jh.biz.shop.crm.member.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.protocol.HttpService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.member.dao.MemberLevelSettingDao;
import s2jh.biz.shop.crm.member.entity.MemberLevelSetting;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.getIpAddress;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.GradePromotion;
import com.taobao.api.request.CrmGradeGetRequest;
import com.taobao.api.request.CrmGradeSetRequest;
import com.taobao.api.response.CrmGradeGetResponse;
import com.taobao.api.response.CrmGradeSetResponse;

@Service
@Transactional
public class MemberLevelSettingService extends
		BaseService<MemberLevelSetting, Long> {

	@Autowired
	private MemberLevelSettingDao memberLevelSettingDao;

	@Autowired
	private UserOperationLogDao userOperationLogDao;

	@Autowired
	private MyBatisDao myBatisDao;

	@Autowired
	private MemberInfoService memberInfoService;
	private static final Log logger = LogFactory.getLog(MemberLevelSettingService.class);
	@Override
	protected BaseDao<MemberLevelSetting, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return memberLevelSettingDao;
	}

	// 查询4个会员等级相关
	public List<MemberLevelSetting> findFourLevel(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<MemberLevelSetting> memberLevel = myBatisDao.findList(
				"s2jh.biz.shop.crm.member.entity.MemberLevelSetting",
				"findFourLevel", map);
		return memberLevel;
	}

	// 修改会员设置
	public void updateSetting(MemberLevelSetting memberLevelSetting,String ip,String userId) throws RuntimeException{
		memberLevelSetting.setUserId(userId);
		if (memberLevelSetting.getId() != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", memberLevelSetting.getId());
			map.put("tradingVolume", memberLevelSetting.getTradingVolume());
			map.put("discount", memberLevelSetting.getDiscount());
			map.put("turnover", memberLevelSetting.getTurnover());
			map.put("ctime", new Date());
			map.put("hierarchy", "true");
			UserOperationLog o = new UserOperationLog();
			o.setIpAdd(ip);
			o.setUserId(userId);
			o.setFunctions("修改店铺会员等级设置");
			o.setType("修改");
			o.setState("成功");
			o.setDate(new Date());
			o.setRemark("会员等级划分");
			userOperationLogDao.save(o);
			myBatisDao.execute("s2jh.biz.shop.crm.member.entity.MemberLevelSetting","updateSetting", map);
		} else {
			memberLevelSetting.setCtime(new Date());
			memberLevelSetting.setHierarchy("true");
			memberLevelSettingDao.save(memberLevelSetting);
			UserOperationLog o = new UserOperationLog();
			o.setIpAdd(ip);
			o.setUserId(userId);
			o.setFunctions("保存店铺会员等级设置");
			o.setType("保存");
			o.setState("成功");
			o.setDate(new Date());
			o.setRemark("会员等级划分");
			userOperationLogDao.save(o);
		}
	}

	// 修改会员设置 version 2
	public boolean updateMemberSetting(Map<String, Object> map) {
		boolean  result  =false;
		try {
			MemberLevelSettingModel memberLevel = (MemberLevelSettingModel)map.get("memberLevel");
			String userId = (String)map.get("userId");
			String ip = (String)map.get("userIp");
			// 更新会员设置
			List<MemberLevelSetting> memberSettinglist = memberLevel
					.getMemberLevelSetting();
			if (memberSettinglist != null && memberSettinglist.size() > 0) {
				for (MemberLevelSetting s : memberSettinglist) {
					updateSetting(s, ip,userId);
				}
			}
			//修改普通会员 组ID
			updateRegularMembers(userId);
			//修改高级会员组ID
			updateSeniorMember(userId);
			//修改vip会员组ID
			updateVipMember(userId);
			//修改至尊vip会员
			updateExtremeMember(userId);
			result  = true;
		} catch (Exception e) {
			result  =false;
			logger.error("更新会员基础设置失败！"+e.getMessage());
		   throw new  RuntimeException();
		}
		return   result;
	}

	// 取消所有会员折扣
	public void updateDiscount(String discount, String userId,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("discount", discount);
		map.put("userId", userId);
		myBatisDao.execute(
				"s2jh.biz.shop.crm.member.entity.MemberLevelSetting",
				"updateDiscount", map);
	}

	// 会员归类
	public void updateMemberInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		myBatisDao.execute(
				"s2jh.biz.shop.crm.member.entity.MemberLevelSetting",
				"updateMemberInfo", map);
	}

//	/**
//	 * 创建人：邱洋
//	 * 
//	 * @Title: 根据交易额和交易成功笔数是否晋升到下一等级
//	 * @date 2017年2月23日--下午2:42:10
//	 * @return void
//	 * @throws
//	 */
//	public Long updateMemberInfoGrade(String taobao_user_nick, String price,
//			String turnover, String buyerNick) {
//		Long memberLeve = 0L;
//
//		// 首先获取卖家设置的四个会员等级设置条件
//		List<MemberLevelSetting> memberlist = findFourLevel(taobao_user_nick);
//
//		// 根据买家昵称查询会员信息
//		List<MemberInfo> memberInfo = memberInfoService.getMemberInfo(
//				taobao_user_nick, buyerNick);
//		
//		int buyerGrade = memberInfo.get(0).getGradeId().intValue();//取出当前会员的会员等级
//		
//		Double TradeAmount = 0.0;//取出当前会员的所有成交金额
//		Long TradeCount = 0L; //取出当前会员的所有成功交易笔数
//		if(memberInfo!=null&&memberInfo.size()>0){
//			for(MemberInfo in:memberInfo){
//				if(in.getTradeAmount()!=null&&!"".equals(in.getTradeAmount())){
//					TradeAmount = TradeAmount + Double.parseDouble(in.getTradeAmount()) ;
//				}
//				if(in.getTradeCount()!=null&&!"".equals(in.getTradeCount())){
//					TradeCount = TradeCount + in.getTradeCount();
//				}
//				
//			}
//		}
//		
//		if (memberlist != null && memberlist.size() > 0) {
//			for (int i=memberlist.size();i>0;i--) {
//				MemberLevelSetting mls = memberlist.get(i-1);
//				if (mls != null && memberInfo != null) {
//					int conditionGrade =0;
//					if(mls.getMemberlevel()!=null&&!mls.getMemberlevel().equals("")){
//						conditionGrade = Integer.parseInt(mls.getMemberlevel());
//					}		
//					//判断目前的会员等级是否比匹配的等级高，如果高则不判断直接跳过
//					if(buyerGrade<conditionGrade){
//						// 判断交易成功金额是否可以晋升到下一等级，如果可以则修改为最新的等级
//						if (TradeAmount != null
//								&& mls.getTradingVolume() != null && price != null) {
//							BigDecimal jye = new BigDecimal(
//									Double.parseDouble(price)
//											+ TradeAmount);
//							int sta = jye.compareTo(mls.getTradingVolume());
//							if (sta >= 0 && mls.getMemberlevel() != null) {
//								memberLeve = Long.parseLong(mls.getMemberlevel());
//								break;
//							}
//						} else
//						// 判断交易成功笔数否可以晋升到下一等级，如果可以则修改为最新的等级
//						if (TradeCount != null
//								&& mls.getTurnover() != null && turnover != null) {
//							int num = Integer.parseInt(turnover)
//									+ TradeCount.intValue();
//							if (num >= mls.getTurnover()
//									&& mls.getTurnover() != null) {
//								memberLeve = Long.parseLong(mls.getMemberlevel());
//								break;
//							}
//						}
//					}else{
//						return memberLeve;
//					}
//				}
//			}
//		}
//		return memberLeve;
//	}
	
   //修改普通会员组ID
	public void updateRegularMembers(String userId) throws RuntimeException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		myBatisDao.execute(MemberLevelSetting.class.getName(), "updateRegularMembers", map);
	}
	
	//修改高级会员组ID
	public void updateSeniorMember(String userId) throws RuntimeException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		myBatisDao.execute(MemberLevelSetting.class.getName(), "updateSeniorMember", map);
	}
	
	//修改vip会员组Id
	public void updateVipMember(String userId)throws RuntimeException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		myBatisDao.execute(MemberLevelSetting.class.getName(), "updateVipMember", map);
		
	}
	
	//修改至尊vip会员组ID
	public void updateExtremeMember(String userId)throws RuntimeException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		myBatisDao.execute(MemberLevelSetting.class.getName(), "updateExtremeMember", map);
	}
	
	//调用淘宝接口将 会员等级设置 传给淘宝
	@SuppressWarnings("unused")
	public List<MemberLevelSetting> findFourLevels(String userId,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<MemberLevelSetting> memberLevel = myBatisDao.findList(
				"s2jh.biz.shop.crm.member.entity.MemberLevelSetting",
				"findFourLevel", map);
		
		//交易额度
		String amount=null;
		//交易笔数
		String count =null;
		//会员等级
		String grade=null;
		//折扣率 
		String discount = null; 
		//是否设置达到某一会员等级的交易量和交易额
		String hierarchy=null;
		
		for(int i =0;i<memberLevel.size();i++){
			if(amount!=null){
				BigDecimal amounts = null;
				amounts = new BigDecimal(memberLevel.get(i).getTradingVolume().toString());
				amounts = amounts.multiply(new BigDecimal(100));
				BigInteger amounts1 = amounts.toBigInteger();
				amount =amount+","+amounts1;
			}else{
				BigDecimal amounts = null;
				amounts = new BigDecimal(memberLevel.get(i).getTradingVolume().toString());
				amounts = amounts.multiply(new BigDecimal(100));
				BigInteger amounts1 = amounts.toBigInteger();
				amount =amounts1+"";
			}
			if(count!=null){
				count =count+","+memberLevel.get(i).getTurnover();
			}else{
				count =memberLevel.get(i).getTurnover().toString();
			}
			if(grade!=null){
				grade =grade+","+memberLevel.get(i).getMemberlevel();
			}else{
				grade =memberLevel.get(i).getMemberlevel().toString();
			}
			if(discount!=null){
				BigDecimal discounts = null;
				discounts = new BigDecimal(memberLevel.get(i).getDiscount());
				discounts=discounts.multiply(new BigDecimal(100));
				BigInteger m =discounts.toBigInteger();
				discount =discount+","+m;
			}else{
				BigDecimal discounts = null;
				discounts = new BigDecimal(memberLevel.get(i).getDiscount());
				discounts=discounts.multiply(new BigDecimal(100));
				BigInteger m =discounts.toBigInteger();
				discount =m+"";
			}
			if(hierarchy!=null){
				hierarchy=hierarchy+","+memberLevel.get(i).getHierarchy();
			}else{
				if(memberLevel.get(i).getHierarchy()!=null){
					hierarchy=memberLevel.get(i).getHierarchy().toString();
				}			
			}
		}
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		String sessionKey = (String) request.getSession().getAttribute("access_token");
		//String sessionKey="70002100124174d20967f5641d990a8153741ae8ea92784622632a83bb010a4fe33a90d2106245636";
		CrmGradeSetRequest req = new CrmGradeSetRequest();
		//交易額度
		req.setAmount(amount);
		//交易笔数
		req.setCount(count);
		//折扣率
		req.setDiscount(discount);
		//会员等级
		req.setGrade(grade);
		//是否设置达到某一会员等级的交易量和交易额
		req.setHierarchy(hierarchy);
		CrmGradeSetResponse rsp = null;
		try {
			 rsp = client.execute(req, sessionKey);
			 System.out.println("**************************同步接口调用完成"+rsp.getBody()+"**********************************");
			 System.out.println("****************************************同步错误码为"+rsp.getErrorCode()+"**********************************");
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memberLevel;
	}

	/**
	  * 创建人：邱洋
	  * @Title: 根据会员分组查询分组对应等级的条件设置
	  * @date 2017年4月7日--下午4:43:07 
	  * @return MemberLevelSetting
	  * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MemberLevelSetting getMemberLevelSettingInfo(String groupId){
		Map map = new HashMap();
		map.put("groupId", groupId);
		MemberLevelSetting ml = myBatisDao.findBy(MemberLevelSetting.class.getName(), "getMemberLevelSettingInfo", map);
		return ml;
	}
	/**
	 * 创建人：邱洋
	 * @Title: 根据会员分组查询分组对应等级的条件设置
	 * @date 2017年4月7日--下午4:43:07 
	 * @return MemberLevelSetting
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MemberLevelSetting findMemberLevelByLevelAndUserId(String userId,String memberLevel){
		Map map = new HashMap();
		map.put("memberLevel", memberLevel);
		map.put("userId", userId);
		MemberLevelSetting ml = myBatisDao.findBy(MemberLevelSetting.class.getName(), "findMemberLevelByLevelAndUserId", map);
		return ml;
	}
	
	
	/**
	 * Gg
	 * 当用户第一次进行等级设置 调用此接口
	 * 从接口查询用户设置的等级
	 * taobao.crm.grade.get
	 * Gg
	 */
	public void findMemberlevel1(HttpServletRequest request){
		MemberLevelSetting memberLevelSetting = null;
		String userId = (String) request.getSession().getAttribute(
				"taobao_user_nick");
		String sessionKey = (String) request.getSession().getAttribute("access_token");
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		CrmGradeGetRequest req = new CrmGradeGetRequest();
		CrmGradeGetResponse rsp;
		List<GradePromotion> gps = null;
		Map<String,Long> map = null;
		List<MemberLevelSetting> mY =null;
		List<MemberLevelSetting> mB =null;
		String errorCode =null;
		try {
			rsp = client.execute(req, sessionKey);
			gps = rsp.getGradePromotions();
			errorCode = rsp.getErrorCode();
			System.out.println("*************查询查询接口调用完成"+rsp.getBody()+"******************************************");
			System.out.println("**********************************************查询查询返回码为"+rsp.getErrorCode()+"***********************");
		}catch (Exception e) {
			e.printStackTrace();
		}
		//如果返回码为空的话，删除本地的数据，同步新的数据
		if(errorCode==null){
			if(gps!=null && !gps.isEmpty()){
				mY = new ArrayList<MemberLevelSetting>();
				mB = new ArrayList<MemberLevelSetting>();
				map  = new HashMap<String,Long>();
				for (GradePromotion gp : gps) {
					memberLevelSetting= new MemberLevelSetting();
					memberLevelSetting.setUserId(userId);
					memberLevelSetting.setCtime(new Date());
					//买家交易 额度
					long amount= gp.getNextUpgradeAmount();
					BigDecimal amounts = new BigDecimal(amount);
					amounts = amounts.divide(new BigDecimal(100));
					memberLevelSetting.setTradingVolume(amounts);
					//笔数
					String count = gp.getNextUpgradeCount().toString();
					Integer counts = Integer.valueOf(count);
					memberLevelSetting.setTurnover(counts);
					
					String curGrade =gp.getCurGrade();
					//买家折扣
					Long discount = gp.getDiscount();
					map.put(curGrade, discount);
					//会员等级
					String nextGrade = gp.getNextGrade();
					if(nextGrade!=null && !"".equals(nextGrade) && !"0".equals(nextGrade)){
						memberLevelSetting.setMemberlevel(nextGrade);
						mY.add(memberLevelSetting);
					}else{
						mB.add(memberLevelSetting);
					}
					
				}
				if((mY!=null && mY.size()>0) && (map!=null && !map.isEmpty())){
					for (MemberLevelSetting ml : mY) {
						//
						Long disCounts = map.get(ml.getMemberlevel());
						Double dc = 1.0*disCounts/100;
						String disCount = dc.toString();
						ml.setDiscount(disCount);
					}
					
				}
			}
			//判断要disCount的list是否为空，不为空保存
			if(mY!=null){
			   for (MemberLevelSetting memberLevelSetting2 : mY) {
				   HashMap<String,Object> maps  = new HashMap<String,Object>();
				   maps.put("userId", userId);
				   maps.put("memberLevel", memberLevelSetting2.getMemberlevel());
				   MemberLevelSetting  memberLevel = myBatisDao.findBy(MemberLevelSetting.class.getName(), "findMemberLevelByLevelAndUserId", maps);
				   if(null!=memberLevel){
						Map<String, Object> mapParam = new HashMap<String, Object>();
						mapParam.put("id", memberLevel.getId());
						mapParam.put("tradingVolume", memberLevelSetting2.getTradingVolume());
						mapParam.put("discount", memberLevelSetting2.getDiscount());
						mapParam.put("turnover", memberLevelSetting2.getTurnover());
						mapParam.put("ctime", new Date());
						mapParam.put("hierarchy", "true");
						myBatisDao.execute("s2jh.biz.shop.crm.member.entity.MemberLevelSetting","updateSetting", mapParam);
				   }else{
					   memberLevelSettingDao.save(memberLevelSetting2);
				   }
			   }
			}
			//判断不要disCcount的list是否为空，不为空保存
			if(mB!=null){
				for (MemberLevelSetting memberLevelSetting2 : mB) {
					HashMap<String,Object> maps  = new HashMap<String,Object>();
					maps.put("userId", userId);
					maps.put("memberLevel", memberLevelSetting2.getMemberlevel());
					MemberLevelSetting  memberLevel = myBatisDao.findBy(MemberLevelSetting.class.getName(), "findMemberLevelByLevelAndUserId", maps);
					if(null!=memberLevel){
						Map<String, Object> mapParam = new HashMap<String, Object>();
						mapParam.put("id", memberLevel.getId());
						mapParam.put("tradingVolume", memberLevelSetting2.getTradingVolume());
						mapParam.put("discount", memberLevelSetting2.getDiscount());
						mapParam.put("turnover", memberLevelSetting2.getTurnover());
						mapParam.put("ctime", new Date());
						mapParam.put("hierarchy", "true");
						myBatisDao.execute("s2jh.biz.shop.crm.member.entity.MemberLevelSetting","updateSetting", request);
					}else{
					   memberLevelSettingDao.save(memberLevelSetting2);
					}
				}
			}
			Map<String ,Object> maps =  new HashMap<String, Object>();
			maps.put("userId", userId);
			//删除交易金额为0的数据
			myBatisDao.execute(MemberLevelSetting.class.getName(), "delShopMember", maps);
	}else{
		System.out.println("***************************同步更新数据失败失败失败失败失败失败失败失败失败失败，请刷新重试！！！！！！！！！！！！！！！！！！！！！！！！！***********************************");
	}
}
	//删除旧数据
	public void delMemberLevel(String userId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		myBatisDao.execute(MemberLevelSetting.class.getName(), "delMemberLevel", map);
	}
	 
}
