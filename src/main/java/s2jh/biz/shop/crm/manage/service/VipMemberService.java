package s2jh.biz.shop.crm.manage.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.entity.Pageination;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.core.util.DateUtils;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.dao.MemberRepository;
import s2jh.biz.shop.crm.manage.dao.TradeRepository;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.util.idworker.MemberIdWorker;
import s2jh.biz.shop.crm.manage.vo.MemberCriteriaVo;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.message.service.SmsMobileService;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.BigDecimalUtil;
import s2jh.biz.shop.utils.MyBeanUtils;
import s2jh.biz.shop.utils.TradeStatusUtils;

import com.taobao.api.SecretException;

@Service
public class VipMemberService {

	@Autowired
	private MemberRepository  memberRepository;
	@Autowired
	private SmsRecordService  smsRecordService;
	@Autowired
	private TradeInfoService  tradeInfoService;
	@Autowired
	private SmsMobileService smsMobileService;
	@Autowired
	private MsgSendRecordService msgSendRecordService;
	@Autowired
	private TradeRepository tradeRepository;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private UserInfoService userInfoService;
	
	private static final Logger loger = Logger.getLogger(VipMemberService.class);
	
	public MemberDTO  findOne(Query query ,String userNickName){
		return memberRepository.findOne(query,userNickName);  
	}
	
	/** 
	* 通过条件查询会员集合<br/>
	* 有坑?? 集合太大??<br/>
	* @param query
	* @param  userNickName
	* @return List<MemberDTO>    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月17日 上午9:58:17
	*/
	public List<MemberDTO>  findList(Query query ,String userNickName){
		return memberRepository.find(query, userNickName);
	}
	
	public Pageination<MemberDTO>  findMemberPageList(Pageination<MemberDTO> page ,MemberCriteriaVo memberCriteriaVo ,String userNickName){
		Query query  = new Query();
		MemberCriteriaVo memberCriteriaParam =null;
		try {
 			memberCriteriaParam =  filterCondition(memberCriteriaVo);
 		    packageCriteriaParam(query,memberCriteriaParam);
 		    query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "lastTradeTime")));  
 		   loger.info("会员短信群发query:\r\n"+query.toString()+"\r\n");
		} catch (Exception e) {
			loger.error("解析参数失败！"+e.getMessage());
		}
		if( memberCriteriaParam==null) return page;
		Pageination<MemberDTO> resultPage = memberRepository.findPage(page, query, userNickName);
		if(resultPage.getDatas()!=null && resultPage.getDatas().size()>0){
			try {
				List<MemberDTO> members = EncrptAndDecryptClient.getInstance().decryptMemberListData(resultPage.getDatas(), getSessionkey(userNickName,true));
				/*resultPage.setDatas(members);
				return resultPage;*/
			} catch (Exception e) {
				loger.error("会员解密出错了!!"+e.getMessage());
				try {
					List<MemberDTO> members = EncrptAndDecryptClient.getInstance().decryptMemberListData(resultPage.getDatas(), getSessionkey(userNickName,false));
					/*resultPage.setDatas(members);
					return resultPage;*/
				} catch (Exception e1) {
					loger.error("会员<>再次<>解密出错了!!"+e1.getMessage());
				}
			}
		}
		return page;
	}
	
	public long   findMemberCountByParam(MemberCriteriaVo memberCriteriaVo ,String userNickName){
		Query query  = new Query();
		MemberCriteriaVo memberCriteriaParam =null;
		try {
			memberCriteriaParam =  filterCondition(memberCriteriaVo);
			packageCriteriaParam(query,memberCriteriaParam);
		} catch (Exception e) {
			loger.error("解析参数失败！"+e.getMessage());
		}
		return  memberCriteriaParam==null?0l:memberRepository.count(query, userNickName);
	}
	
	
	public List<String>  findSendMemberInfoList(MemberCriteriaVo memberCriteriaVo ,String userNickName){
		List<String> phonesList = null;
		Query query  = new Query();
		MemberCriteriaVo memberCriteriaParam =null;
		try {
			memberCriteriaParam =  filterCondition(memberCriteriaVo);
			packageCriteriaParam(query,memberCriteriaParam);
			int pageNumber = memberCriteriaVo.getStartRows().intValue();  
			int pageSize = memberCriteriaVo.getPageSize().intValue();
	        query.skip(pageNumber).limit(pageSize);  
			query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "_id")));  
			if(null!=memberCriteriaParam){
				List<MemberDTO> members = memberRepository.find(query ,userNickName);
				if(null!=members&&members.size()>0){
					phonesList = new ArrayList<String>();
					for (MemberDTO memberDTO : members) {
						phonesList.add(memberDTO.getPhone());
					}
				}
			}
		} catch (Exception e) {
			loger.error("解析参数失败！"+e.getMessage());
		}
		return phonesList;
	}
	//黑名单专用
	public List<MemberDTO> findListMemberDTOAndUpdate(MemberDTO memberInfo) {
		Query query = new Query();  
		if(null!=memberInfo){
			if(null!=memberInfo.getBuyerNick()&&!"".equals(memberInfo.getBuyerNick())){
				query.addCriteria(Criteria.where("buyerNick").is(memberInfo.getBuyerNick()));
			}
			if(null!=memberInfo.getMsgId()&&!"".equals(memberInfo.getMsgId())){
				query.addCriteria(Criteria.where("msgId").is(memberInfo.getMsgId()));
			}
			if(null!=memberInfo.getNUM_IID()&&!"".equals(memberInfo.getNUM_IID())){
				query.addCriteria(Criteria.where("NUM_IID").is(memberInfo.getNUM_IID()));
			}
			if(null!=memberInfo.getLastOrderStatus()&&!"".equals(memberInfo.getLastOrderStatus())){
				query.addCriteria(Criteria.where("lastOrderStatus").is(memberInfo.getLastOrderStatus()));
			}
			if(null!=memberInfo.getTradeFrom()&&!"".equals(memberInfo.getTradeFrom())){
				query.addCriteria(Criteria.where("tradeFrom").regex(".*?" + memberInfo.getTradeFrom() + ".*"));
			}
			if(null!=memberInfo.getPhone()&&!"".equals(memberInfo.getPhone())){
				query.addCriteria(Criteria.where("phone").is(memberInfo.getPhone()));
			}
			List<MemberDTO> memberDTOList = memberRepository.find(query, memberInfo.getUserId());
			 
			if(null!=memberDTOList&&memberDTOList.size()>0){
				if(null!=memberInfo.getBlackStatus()){
					Update update  = new Update();
					update.set("blackStatus", memberInfo.getBlackStatus());
					memberRepository.updateMulti(query, update,  memberInfo.getUserId()); 
				}
			}
			return memberDTOList;
		}
		return null;
	}
	
	
	public boolean updateMemberInfoByParam(MemberDTO memberInfo,Integer type) {
		boolean result = true;
		Query query = new Query();
		Update update  = new Update();
		try {
			if(null!=memberInfo){
				if(null!=type&&type==2){
					query.addCriteria(Criteria.where("phone").is(memberInfo.getPhone()));
				}else{
					query.addCriteria(Criteria.where("buyerNick").is(memberInfo.getBuyerNick()));
				}
				if(null!=memberInfo.getBuyerNick()&&!"".equals(memberInfo.getBuyerNick())){
					update.set("buyerNick", memberInfo.getBuyerNick());
				}
				if(null!=memberInfo.getMsgId()&&!"".equals(memberInfo.getMsgId())){
					update.set("msgId", memberInfo.getMsgId());
					update.addToSet("msgIdList", memberInfo.getMsgId());
				}
				if(null!=memberInfo.getNUM_IID()&&!"".equals(memberInfo.getNUM_IID())){
					update.set("NUM_IID", memberInfo.getNUM_IID());
				}
				if(null!=memberInfo.getLastOrderStatus()&&!"".equals(memberInfo.getLastOrderStatus())){
					update.set("lastOrderStatus", memberInfo.getLastOrderStatus());
				}
				if(null!=memberInfo.getTradeFrom()&&!"".equals(memberInfo.getTradeFrom())){
					update.set("tradeFrom", memberInfo.getTradeFrom());
				}
				if(null!=memberInfo.getPhone()&&!"".equals(memberInfo.getPhone())){
					update.set("phone", memberInfo.getPhone());
				}
				if(null!=memberInfo.getBlackStatus()&&!"".equals(memberInfo.getBlackStatus())){
					update.set("blackStatus", memberInfo.getBlackStatus());
				}
				if(null!=memberInfo.getCommentStatus()&&!"".equals(memberInfo.getCommentStatus())){
					update.set("commentStatus", memberInfo.getCommentStatus());
				}
				if(null!=memberInfo.getLastSendSmsTime()&&!"".equals(memberInfo.getLastSendSmsTime())){
					update.set("lastSendSmsTime", memberInfo.getLastSendSmsTime());
				}
				
				
				//---------------------------------客户详情修改----------------------------------//
				if(type==4){
					//买家昵称
					if(!"".equals(memberInfo.getBuyerNick())){
						update.set("buyerNick", memberInfo.getBuyerNick());
					}
					//邮箱
					if(!"".equals(memberInfo.getEmail())){
						update.set("email", memberInfo.getEmail());
					}
					//性别
					if(!"".equals(memberInfo.getSex())){
						update.set("sex", memberInfo.getSex());
					}
					//生日
					if(!"".equals(memberInfo.getBirthday())){
						update.set("birthday", memberInfo.getBirthday());
					}
					//年龄
					if(!"".equals(memberInfo.getAge())){
						update.set("age", memberInfo.getAge());
					}
					//微信
					if(!"".equals(memberInfo.getWechat())){
						update.set("wechat", memberInfo.getWechat());
					}
					//QQ号
					if(!"".equals(memberInfo.getQq())){
						update.set("qq", memberInfo.getQq());
					}
					//职业
					if(!"".equals(memberInfo.getOccupation())){
						update.set("occupation", memberInfo.getOccupation());
					}
				}
				if(type==3){
					//备注
					if(!"".equals(memberInfo.getRemarks())){
						update.set("remarks", memberInfo.getRemarks());
					}
					//备注时间
					if(null!=memberInfo.getRemarksTime() && !"".equals(memberInfo.getRemarks())){
						update.set("remarksTime", memberInfo.getRemarksTime());
					}
				}
				memberRepository.update(query, update, memberInfo.getUserId());
			}
		} catch (Exception e) {
			result =false;
			throw new RuntimeException();
		}
		return result;
		
	}
	
	/**
	 * Gg
	 * 黑名单 修改会员状态
	 * @param memberInfo
	 * @param type
	 * @return
	 * Gg
	 */
	public boolean updateBlackMemberInfoStatus(MemberDTO memberInfo,String type){
		boolean result = true;
		Query query = new Query();
		Update update  = new Update();
		try {
			if("1".equals(type)){
				//买家昵称
				if(null!=memberInfo.getBuyerNick()&&!"".equals(memberInfo.getBuyerNick())){
					update.set("phone", memberInfo.getPhone());
				}else{
					if(null!=memberInfo.getBuyerNick()&&!"".equals(memberInfo.getBuyerNick())){
						update.set("buyerNick", memberInfo.getBuyerNick());
					}
				}
			}else{
				if(null!=memberInfo.getBlackStatus()&&!"".equals(memberInfo.getBlackStatus())){
					update.set("blackStatus", memberInfo.getBlackStatus());
				}
				if(null!=memberInfo.getBuyerNick()&&!"".equals(memberInfo.getBuyerNick())){
					update.set("phone", memberInfo.getPhone());
				}
				if(null!=memberInfo.getBuyerNick()&&!"".equals(memberInfo.getBuyerNick())){
					update.set("buyerNick", memberInfo.getBuyerNick());
				}
			}
			memberRepository.update(query, update, memberInfo.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
//	
//	/***
//	 * 导入数据专用方法
//	 * saveOrUpdateMember:(这里用一句话描述这个方法的作用). <br/> 
//	 * @author zlp
//	 * @param memberInfo
//	 */
//	public void saveOrUpdateMember(MemberDTO memberInfo) {
//		try {
//			if(null!=memberInfo&&null!=memberInfo.getUserId()&&!"".equals(memberInfo.getUserId())){
//				if(null!=memberInfo.getBuyerNick()&&!"".equals(memberInfo.getBuyerNick())){
//					 Query query = new Query();  
//				     query.addCriteria(Criteria.where("buyerNick").is(memberInfo.getBuyerNick()));
//					 MemberDTO memberDTO = findOne(query,memberInfo.getUserId());
//					 if(null!=memberDTO){
//						 if(null!=memberInfo.getRefund_status()&&1==memberInfo.getRefund_status()){
//								Update update  = new Update();
//								update.set("refund_status", memberInfo.getRefund_status());
//								memberRepository.update(query, update, memberInfo.getUserId());
//						 }else{
//							 calculateMemberTrade(memberDTO,memberInfo);
//							 memberInfo.setCreatedDate(memberDTO.getCreatedDate());
//							 memberInfo.setTimestampId(memberDTO.getTimestampId());
//							 memberInfo.set_id(memberDTO.get_id());
//							 
//							 //新加的
//							 memberInfo.setSex(memberDTO.getSex()); 
//							 memberInfo.setAge(memberDTO.getAge()); 
//							 memberInfo.setOccupation(memberDTO.getOccupation()); 
//							 memberInfo.setBirthday(memberDTO.getBirthday()); 
//							 memberInfo.setQq(memberDTO.getQq()); 
//							 memberInfo.setWechat(memberDTO.getWechat()); 
//							 memberInfo.setPhoneRange(memberDTO.getPhoneRange()); 
//							 memberInfo.setEmail(memberDTO.getEmail()); 
//							 memberInfo.setRemarks(memberDTO.getRemarks()); 
//							 memberInfo.setEmailType(memberDTO.getEmailType()); 
//							 memberInfo.setRemarksTime(memberDTO.getRemarksTime()); 
//							 
//							 memberRepository.save(memberInfo, memberInfo.getUserId());
//						 }
//					 }else{
//						 Map<String,Object> map = new HashMap<String, Object>();
//						 memberInfo.setBlackStatus(0);
//						 memberInfo.setCommentStatus(0);
//						 memberInfo.setCreatedDate(new Date());
//						 memberInfo.setTimestampId(MemberIdWorker.getInstance().nextId());
//						 memberRepository.save(memberInfo, memberInfo.getUserId());
//					 }
//				}else{
//					 loger.error("买家昵称为空！");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			 loger.error(e.getMessage()+"分析会员"+memberInfo.getUserId()+" "+memberInfo.getTid());
//			 throw new RuntimeException();
//		}
//	}
// 


	
	public List<MemberDTO> findMemberList(MemberCriteriaVo memberCriteriaVo ,String userNickName){
		Query query  = new Query();
		try {
			long count = memberCriteriaVo.getTotalCount();
			query.limit(Integer.parseInt(count+""));
			MemberCriteriaVo memberCriteriaParam =   filterCondition(memberCriteriaVo);
			query  = packageCriteriaParam(query,memberCriteriaParam);
		} catch (Exception e) {
			loger.error("解析参数失败List！"+e.getMessage());
		}
		return  memberRepository.find( query, userNickName);
	}









	private MemberCriteriaVo filterCondition(MemberCriteriaVo memberCriteriaVo) throws Exception{
		MemberCriteriaVo  memberCriteriaParam  = new  MemberCriteriaVo();
		MyBeanUtils.copyProperties(memberCriteriaParam, memberCriteriaVo);
		List<String> itemsList = null;
		if (memberCriteriaVo.getItemIds()!= null && !"".equals(memberCriteriaVo.getItemIds().trim())) {
			itemsList = new ArrayList<String>();
			String[] items = memberCriteriaVo.getItemIds().split(",");
			for (String item : items) {
				itemsList.add(item);
			}
			memberCriteriaParam.setProductList(itemsList);
		}
		List<String> areaList = null;
		// 筛选地区参数
		if (memberCriteriaVo.getRegion() != null && !"".equals(memberCriteriaVo.getRegion().trim())) {
			areaList = new ArrayList<String>();
			String[] regions = memberCriteriaVo.getRegion().split(",");
			for (String region : regions) {
				if(!",".equals(region)){
					areaList.add(region);
				}
			}
			memberCriteriaParam.setAreaList(areaList);
		}
		if (null!=memberCriteriaVo.getLastestSend() &&!"".equals(memberCriteriaVo.getLastestSend()) ) {
			int lastSendTime = Integer.parseInt(memberCriteriaVo.getLastestSend());
			if(lastSendTime>0){
				//TODO
				//TODO
				if(memberCriteriaVo.getFull()!=null && memberCriteriaVo.getFull()
				   && memberCriteriaVo.getMsgIdList()!=null && !memberCriteriaVo.getMsgIdList().isEmpty()){
					memberCriteriaParam.setMsgIdList(memberCriteriaVo.getMsgIdList());
				}else{
					Date endDate = new Date();
					Date startDate = DateUtils.addDate(endDate , (lastSendTime*-1));
					memberCriteriaVo.setSendStartTime(startDate);
					memberCriteriaVo.setSendEndTime(endDate);
					List<MsgSendRecord> lastSendList = msgSendRecordService.queryLastSendList(memberCriteriaVo);
					List<Long> msgList = null;
					if(null!=lastSendList&&lastSendList.size()>0){
						msgList = new ArrayList<Long>();
						for (MsgSendRecord msgSendRecord : lastSendList) {
							if(!msgList.contains(msgSendRecord.getId())){
								msgList.add(msgSendRecord.getId());
							}
						}
						if(null!=msgList&&msgList.size()>0){
							memberCriteriaVo.setFull(true);
							memberCriteriaVo.setMsgIdList(msgList);
							memberCriteriaParam.setMsgIdList(msgList);
						}
					}
					
				}
			}
		}
		
		
		//黑名单--手机号和昵称
		if (memberCriteriaVo.isBlackList()) {
			/**
			 * 黑名单是手机号或者会员昵称--此处待商榷
			 * */
			memberCriteriaParam.setBlackStatus(1);
		}
		//退款--会员昵称
		if (memberCriteriaVo.isRefund()) {
			memberCriteriaParam.setRefundStatus(1);
		
		}
		//中差评--会员昵称
		if (memberCriteriaParam.isEvaluation()) {
			memberCriteriaParam.setEvaluateStatus(1);
		}
		return memberCriteriaParam;
	}



	private Query packageCriteriaParam(Query query,
			MemberCriteriaVo memberCriteriaVo) {
		if(memberCriteriaVo!=null&&memberCriteriaVo.getGroupType()!=null){
			packageMemberGroupParam(query, memberCriteriaVo);
			return query ;
		}
		if(null!=memberCriteriaVo.getRefundStatus()&&1==memberCriteriaVo.getRefundStatus()){
			query.addCriteria(Criteria.where("refund_status").ne(memberCriteriaVo.getRefundStatus()));  
		}
		if(null!=memberCriteriaVo.getBlackStatus()&&1==memberCriteriaVo.getBlackStatus()){
			query.addCriteria(Criteria.where("blackStatus").ne(memberCriteriaVo.getBlackStatus()));  
		}
		if(null!=memberCriteriaVo.getEvaluateStatus()&&1==memberCriteriaVo.getEvaluateStatus()){
			query.addCriteria(Criteria.where("commentStatus").ne(memberCriteriaVo.getEvaluateStatus()));  
		}
		if(null!=memberCriteriaVo.getOrderStatus()&&!"".equals(memberCriteriaVo.getOrderStatus())){
			 if("2".equals(memberCriteriaVo.getOrderStatus()))memberCriteriaVo.setOrderStatus(TradeStatusUtils.WAIT_BUYER_PAY);
			 if("4".equals(memberCriteriaVo.getOrderStatus()))memberCriteriaVo.setOrderStatus(TradeStatusUtils.WAIT_SELLER_SEND_GOODS);
			 if("5".equals(memberCriteriaVo.getOrderStatus()))memberCriteriaVo.setOrderStatus(TradeStatusUtils.WAIT_BUYER_CONFIRM_GOODS);
			 if("7".equals(memberCriteriaVo.getOrderStatus()))memberCriteriaVo.setOrderStatus(TradeStatusUtils.TRADE_FINISHED);
			  query.addCriteria(Criteria.where("lastOrderStatus").is(memberCriteriaVo.getOrderStatus()));  
		}
		if(null!=memberCriteriaVo.getProductList()&&memberCriteriaVo.getProductList().size()>0){
			 query.addCriteria(Criteria.where("tempProduct").in(memberCriteriaVo.getProductList()));  
		} 
		if(null!=memberCriteriaVo.getAreaList()&&memberCriteriaVo.getAreaList().size()>0){
			 query.addCriteria(Criteria.where("tempProvince").in(memberCriteriaVo.getAreaList()));  
		}
		if(null!=memberCriteriaVo.getMsgIdList()&&memberCriteriaVo.getMsgIdList().size()>0){
			query.addCriteria(Criteria.where("msgId").nin(memberCriteriaVo.getMsgIdList()));  
		}
		
		Double minTradePrice = memberCriteriaVo.getMinTradePrice();   Double maxTradePrice = memberCriteriaVo.getMaxTradePrice();
		Integer minTradeNum = memberCriteriaVo.getMinTradeNum();   Integer maxTradeNum = memberCriteriaVo.getMaxTradeNum();
		
		
		if(null!=memberCriteriaVo.getCustomerSource()&&!"".equals(memberCriteriaVo.getCustomerSource())&&!"0".equals(memberCriteriaVo.getCustomerSource())){
			if("1".equals(memberCriteriaVo.getCustomerSource()))memberCriteriaVo.setCustomerSource("WAP");
			if("2".equals(memberCriteriaVo.getCustomerSource()))memberCriteriaVo.setCustomerSource("TAOBAO");
			if("3".equals(memberCriteriaVo.getCustomerSource()))memberCriteriaVo.setCustomerSource("JHS");
			if("4".equals(memberCriteriaVo.getCustomerSource()))memberCriteriaVo.setCustomerSource("import");
			query.addCriteria(Criteria.where("tradeFrom").regex(".*?" + memberCriteriaVo.getCustomerSource() + ".*"));  
			//query.addCriteria(Criteria.where("tradeFrom").is(memberCriteriaVo.getCustomerSource()));  
		}
		
		//最后交易时间,交易额,交易量,平均客单价.......
		try {
			String tradeTimeByDay = memberCriteriaVo.getTradeTimeByDay();
			if(null!=tradeTimeByDay&&!"".equals(tradeTimeByDay)&&!"0".equals(tradeTimeByDay)){
				int tradeTimeBy = Integer.parseInt(tradeTimeByDay);
				Date endDate = new Date();
				Date startDate = DateUtils.addDate(endDate , (tradeTimeBy*-1));
				query.addCriteria(Criteria.where("lastTradeTime").gte(DateUtils.dateToLong(startDate)).lte(DateUtils.dateToLong(endDate)));
			}else{
				String tradeStartTime = memberCriteriaVo.getTradeStartTime(); String tradeEndTime = memberCriteriaVo.getTradeEndTime();
				if(null!=tradeStartTime&&!"".equals(tradeStartTime)&&null!=tradeEndTime&&!"".equals(tradeEndTime)){
					query.addCriteria(Criteria.where("lastTradeTime").gte(DateUtils.stringToLong(tradeStartTime, DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(tradeEndTime, DateUtils.DEFAULT_TIME_FORMAT)));
				}else{
					if(null!=tradeStartTime&&!"".equals(tradeStartTime)){
						query.addCriteria(Criteria.where("lastTradeTime").gte(DateUtils.stringToLong(tradeStartTime, DateUtils.DEFAULT_TIME_FORMAT)));
					}
					if(null!=tradeEndTime&&!"".equals(tradeEndTime)){
						query.addCriteria(Criteria.where("lastTradeTime").lte(DateUtils.stringToLong(tradeEndTime, DateUtils.DEFAULT_TIME_FORMAT)));
					}
				}
			}
		} catch (ParseException e) {
		}
		

		if(null!=minTradePrice&&minTradePrice!=0d&&null!=maxTradePrice&&maxTradePrice!=0d){
			query.addCriteria(Criteria.where("tradeAmount").gte(memberCriteriaVo.getMinTradePrice()).lte(memberCriteriaVo.getMaxTradePrice()));
		}else{
			if(null!=minTradePrice&&minTradePrice!=0d){
				query.addCriteria(Criteria.where("tradeAmount").gte(memberCriteriaVo.getMinTradePrice()));
			}
			if(null!=maxTradePrice&&maxTradePrice!=0d){
				query.addCriteria(Criteria.where("tradeAmount").lte(memberCriteriaVo.getMaxTradePrice()));
			}
		}
		if(null!=memberCriteriaVo.getTradeNumByTimes()&&!"".equals(memberCriteriaVo.getTradeNumByTimes())){
			int parseInt = Integer.parseInt(memberCriteriaVo.getTradeNumByTimes());
			if(parseInt>=3){
				query.addCriteria(Criteria.where("tradeCount").gt(3));
			}else if(0==parseInt){
				query.addCriteria(Criteria.where("tradeCount").is(0));
			} else{
				query.addCriteria(Criteria.where("tradeCount").is(parseInt));
			}
		}else{
			if(null!=minTradeNum&&null!=maxTradeNum){
				query.addCriteria(Criteria.where("tradeCount").gte(memberCriteriaVo.getMinTradeNum()).lte(memberCriteriaVo.getMaxTradeNum()));
			}else{
				if(null!=minTradeNum){
					query.addCriteria(Criteria.where("tradeCount").gte(memberCriteriaVo.getMinTradeNum()));
				}
				if(null!=maxTradeNum){
					query.addCriteria(Criteria.where("tradeCount").lte(memberCriteriaVo.getMaxTradeNum()));
				}
			}
			
		}

		Double minAvgPrice = memberCriteriaVo.getMinAvgPrice();   Double maxAvgPrice = memberCriteriaVo.getMaxAvgPrice();
		if(null!=minAvgPrice&&minAvgPrice!=0d&&null!=maxAvgPrice&&maxAvgPrice!=0d){
			query.addCriteria(Criteria.where("avgPrice").gte(memberCriteriaVo.getMinAvgPrice()).lte(memberCriteriaVo.getMaxAvgPrice()));
		}else{
			if(null!=minAvgPrice&&minAvgPrice!=0d){
				query.addCriteria(Criteria.where("avgPrice").gte(memberCriteriaVo.getMinAvgPrice()));
			}
			if(null!=maxAvgPrice&&maxAvgPrice!=0d){
				query.addCriteria(Criteria.where("avgPrice").lte(memberCriteriaVo.getMaxAvgPrice()));
			}
		}
		//客户昵称
		if(null!=memberCriteriaVo.getBuyerName()&& !"".equals(memberCriteriaVo.getBuyerName())){
			//query.addCriteria(Criteria.where("buyerNick").in(memberCriteriaVo.getBuyerName()));
			query.addCriteria(Criteria.where("buyerNick").regex(memberCriteriaVo.getBuyerName()));
		}
		return  query; 
	}

	private void packageMemberGroupParam(Query query,
			MemberCriteriaVo memberCriteriaVo) {
		try {
			Double minTradePrice = memberCriteriaVo.getMinTradePrice();   Double maxTradePrice = memberCriteriaVo.getMaxTradePrice();
			Integer minTradeNum = memberCriteriaVo.getMinTradeNum();   Integer maxTradeNum = memberCriteriaVo.getMaxTradeNum();
			if("1".equals(memberCriteriaVo.getGroupType())){
				// 默认分组
				Criteria c  = new  Criteria(); 
				if(null!=maxTradePrice&&maxTradePrice!=0d&&null!=maxTradeNum&&maxTradeNum!=0){
					c.orOperator(Criteria.where("tradeAmount").gte(memberCriteriaVo.getMinTradePrice()).lte(memberCriteriaVo.getMaxTradePrice())
							,Criteria.where("tradeCount").gte(memberCriteriaVo.getMinTradeNum()).lte(memberCriteriaVo.getMaxTradeNum()));
				}else{
					c.orOperator(Criteria.where("tradeAmount").gte(memberCriteriaVo.getMinTradePrice())
							,Criteria.where("tradeCount").gte(memberCriteriaVo.getMinTradeNum()));
				}
				query.addCriteria(c);
			}else{
				
				if(null!=memberCriteriaVo.getProductList()&&memberCriteriaVo.getProductList().size()>0){
					 query.addCriteria(Criteria.where("tempProduct").in(memberCriteriaVo.getProductList()));  
				} 
				if(null!=memberCriteriaVo.getAreaList()&&memberCriteriaVo.getAreaList().size()>0){
					 query.addCriteria(Criteria.where("tempProvince").in(memberCriteriaVo.getAreaList()));  
				}
				String tradeStartTime = memberCriteriaVo.getTradeStartTime(); String tradeEndTime = memberCriteriaVo.getTradeEndTime();
				if(null!=tradeStartTime&&!"".equals(tradeStartTime)&&null!=tradeEndTime&&!"".equals(tradeEndTime)){
					query.addCriteria(Criteria.where("lastTradeTime").gte(DateUtils.stringToLong(tradeStartTime, DateUtils.DEFAULT_TIME_FORMAT)).lte(DateUtils.stringToLong(tradeEndTime, DateUtils.DEFAULT_TIME_FORMAT)));
				}else{
					if(null!=tradeStartTime&&!"".equals(tradeStartTime)){
						query.addCriteria(Criteria.where("lastTradeTime").gte(DateUtils.stringToLong(tradeStartTime, DateUtils.DEFAULT_TIME_FORMAT)));
					}
					if(null!=tradeEndTime&&!"".equals(tradeEndTime)){
						query.addCriteria(Criteria.where("lastTradeTime").lte(DateUtils.stringToLong(tradeEndTime, DateUtils.DEFAULT_TIME_FORMAT)));
					}
				}
				if(null!=minTradePrice&&minTradePrice!=0d&&null!=maxTradePrice&&maxTradePrice!=0d){
					query.addCriteria(Criteria.where("tradeAmount").gte(memberCriteriaVo.getMinTradePrice()).lte(memberCriteriaVo.getMaxTradePrice()));
				}else{
					if(null!=minTradePrice&&minTradePrice!=0d){
						query.addCriteria(Criteria.where("tradeAmount").gte(memberCriteriaVo.getMinTradePrice()));
					}
					if(null!=maxTradePrice&&maxTradePrice!=0d){
						query.addCriteria(Criteria.where("tradeAmount").lte(memberCriteriaVo.getMaxTradePrice()));
					}
				}
				if(null!=minTradeNum&&null!=maxTradeNum&&maxTradeNum>0){
					query.addCriteria(Criteria.where("tradeCount").gte(memberCriteriaVo.getMinTradeNum()).lte(memberCriteriaVo.getMaxTradeNum()));
				}else{
					if(null!=minTradeNum){
						query.addCriteria(Criteria.where("tradeCount").gte(memberCriteriaVo.getMinTradeNum()));
					}
					if(null!=maxTradeNum&&maxTradeNum>0){
						query.addCriteria(Criteria.where("tradeCount").lte(memberCriteriaVo.getMaxTradeNum()));
					}
				}
				Double minAvgPrice = memberCriteriaVo.getMinAvgPrice();   Double maxAvgPrice = memberCriteriaVo.getMaxAvgPrice();
				if(null!=minAvgPrice&&minAvgPrice!=0d&&null!=maxAvgPrice&&maxAvgPrice!=0d){
					query.addCriteria(Criteria.where("avgPrice").gte(memberCriteriaVo.getMinAvgPrice()).lte(memberCriteriaVo.getMaxAvgPrice()));
				}else{
					if(null!=minAvgPrice&&minAvgPrice!=0d){
						query.addCriteria(Criteria.where("avgPrice").gte(memberCriteriaVo.getMinAvgPrice()));
					}
					if(null!=maxAvgPrice&&maxAvgPrice!=0d){
						query.addCriteria(Criteria.where("avgPrice").lte(memberCriteriaVo.getMaxAvgPrice()));
					}
				}
			}
		} catch (ParseException e) {
			 loger.error("封装分组参数失败！");
		}
	}
	
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private MemberDTO analyzePhone(String newPhone,  MemberDTO memberDTO) {
//		try {
//			if(null!=newPhone&&!"".equals(newPhone)){
//				if(null!=memberDTO.getPhone()&&!"".equals(memberDTO.getPhone())){
//					String tempPhone = memberDTO.getTempPhone();
//					if(null!=tempPhone&&!"".equals(tempPhone)){
//						Map map = JsonUtil.fromJson(tempPhone, Map.class);
//						Integer num =1;
//						if(!memberDTO.getPhone().equals(newPhone)){
//							if(map.containsKey(newPhone)){
//								/*num=Integer.valueOf((String) map.get(newPhone))+1;*/
//								num=(Integer) map.get(newPhone)+1;
//							}
//							map.put(newPhone,num);
//							Set keySet = map.keySet();
//							for (Object object : keySet) {
//								if((Integer) map.get(object)>num){
//									num = (Integer) map.get(object);
//									newPhone= (String) object;
//								}
//							}
//						}else{
//							num=(Integer) map.get(newPhone)+1;
//							map.put(newPhone,num); 
//						}
//						String json = JsonUtil.toJson(map);
//						memberDTO.setTempPhone(json);
//					}
//				}
//			}
//		} catch (NumberFormatException e) {
//			loger.error("分析手机号出错！");
//		}
//		memberDTO.setPhone(newPhone); 
//		return memberDTO;
//	}
//	
	
	
	/**
	 * 计算出成交额,成交量,成功的商品数,关闭的成交额,关闭的成交量,关闭的商品数
	 * 客单价??1-成交额/成交量??2-成交额/成功的商品数
	 * 取1
	 * */
	private void calculateMemberTrade(MemberDTO source, MemberDTO target) {
		/*判断之前的数据状态*/
			if(null!=source.getRefund_status()&&0!=source.getRefund_status()){ target.setRefund_status(source.getRefund_status());
			 }else{  target.setRefund_status(0); }
			 if(null!=source.getBlackStatus()&&0!=source.getBlackStatus()){ target.setBlackStatus(source.getBlackStatus());
			 }else{  target.setBlackStatus(0); }
			 if(null!=source.getCommentStatus()&&0!=source.getCommentStatus()){  target.setCommentStatus(source.getCommentStatus());
			 }else{  target.setCommentStatus(0); }
			 if(null!=source.getMsgId()&&0!=source.getMsgId()){  target.setMsgId(source.getMsgId()); 
			 }else{	 target.setMsgId(0l);}
			 if(null!=source.getMsgIdList()&&source.getMsgIdList().size()>0){  target.setMsgIdList(source.getMsgIdList()); 
			 }else{	 target.setMsgIdList(Arrays.asList(0l));}
			 
			 if(null!=source.getLastSendSmsTime()&&0!=source.getLastSendSmsTime()){  target.setLastSendSmsTime(source.getLastSendSmsTime());
			 }else{  target.setLastSendSmsTime(0l); }
			 
			 target.setCloseTradeCount(transFormValue(source.getCloseTradeCount(),target.getCloseTradeCount()));
			 target.setCloseTradeAmount(transFormValue(new BigDecimal(source.getCloseTradeAmount()),new BigDecimal(target.getCloseTradeAmount())));
			 target.setItemCloseCount(transFormValue(source.getItemCloseCount(),target.getItemCloseCount()));
			
			 if(null!=source.getSuccessTidList()&&source.getSuccessTidList().size()>0&&null!=target.getSuccessTidList()&&target.getSuccessTidList().size()>0){ 
				 if(source.getSuccessTidList().contains(target.getTid())){
					 target.setTradeAmount(source.getTradeAmount());
					 target.setTradeCount(source.getTradeCount());
					 target.setItemNum(source.getItemNum());
					 target.setAvgPrice(source.getAvgPrice());
					 target.setSuccessTidList(source.getSuccessTidList());
				 }else{
					   target.setTradeAmount(transFormValue(new BigDecimal(source.getTradeAmount()),new BigDecimal(target.getTradeAmount())));
					   target.setTradeCount(transFormValue(source.getTradeCount(),target.getTradeCount()));
					   target.setItemNum(transFormValue(source.getItemNum(),target.getItemNum()));
					   target.setAvgPrice(BigDecimalUtil.divide(
							   new BigDecimal(target.getTradeAmount()),2,
							   new BigDecimal(target.getTradeCount()==0||target.getTradeCount()==null?1:target.getTradeCount())).doubleValue());
					   List<String> arrList = new ArrayList<String>(source.getSuccessTidList()); 
					   arrList.add(target.getTid());
					   if(null!=arrList&&arrList.size()>0){
						   target.setSuccessTidList(arrList);
					   }
				 }
			 }else{	
			   target.setTradeAmount(transFormValue(new BigDecimal(source.getTradeAmount()),new BigDecimal(target.getTradeAmount())));
			   target.setTradeCount(transFormValue(source.getTradeCount(),target.getTradeCount()));
			   target.setItemNum(transFormValue(source.getItemNum(),target.getItemNum()));
			   target.setAvgPrice(BigDecimalUtil.divide(
					   new BigDecimal(target.getTradeAmount()),2,
					   new BigDecimal(target.getTradeCount()==0||target.getTradeCount()==null?1:target.getTradeCount())).doubleValue());
			 }
		//成交额成交量已经计算完毕,由重新计算出的值再计算出客单价
		/*target.setAvgPrice((target.getTradeAmount()==null || "".equals(target.getTradeAmount()))?null:
			new BigDecimal(Double.valueOf(target.getTradeAmount())/
							((target.getTradeCount()==null ||target.getTradeCount()==0)?1:target.getTradeCount())
							).setScale(2,BigDecimal.ROUND_HALF_UP)
		);*/
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private static String transFormValue(String paramA,String paramB){
		String result = null;
			Double a = Double.parseDouble(paramA==null || "".equals(paramA.trim()) ?"0":paramA.trim());
			Double b = Double.parseDouble(paramB==null || "".equals(paramB.trim()) ?"0":paramB.trim());
			if(a+b != 0){
				Double c = a+b;
				BigDecimal  big =  new BigDecimal(c);
				big = big.setScale(2,BigDecimal.ROUND_HALF_UP);
				result  = big.toString();
				
			}
		return result;
	}
	
	private Long transFormValue(Long paramA,Long paramB){
		/*Long result = null;
		result  = (paramA==null?0:paramA) + (paramB==null?0:paramB); 
		if(result == 0){
			return null;
		}else{
			return result;
		}*/
		return (paramA==null?0:paramA) + (paramB==null?0:paramB);
	}
	private Integer transFormValue(Integer paramA,Integer paramB){
		/*Integer result = null;
		result  = (paramA==null?0:paramA) + (paramB==null?0:paramB); 
		if(result == 0){
			return null;
		}else{
			return result;
		}*/
		return (paramA==null?0:paramA) + (paramB==null?0:paramB);
	}

	private double transFormValue(BigDecimal paramA,BigDecimal paramB){
		return BigDecimalUtil.add((paramA==null?new BigDecimal(0):paramA),(paramB==null?new BigDecimal(0):paramB)).doubleValue();
	}
	
	//多个手机号添加查询添加的手机号会员状态是否为黑名单
	public List<MemberDTO> queryByMemberPhoneStatus(String content,String userNickName){
		Query q = new Query();
		q.addCriteria(Criteria.where("phone").is(content));
		q.addCriteria(Criteria.where("blackStatus").is(0));
		List<MemberDTO> phoneStatuslist = memberRepository.find(q, userNickName);
		return phoneStatuslist;
	}
	//多个手机号添加 查询 添加的手机号是否与会员手机号相匹配
	public List<MemberDTO> queryByIsMemberPhone(String content,String userNickName){
		Query q = new Query();
		q.addCriteria(Criteria.where("phone").is(content));
		List<MemberDTO> memberInfoPhonelist = memberRepository.find(q, userNickName);
		return memberInfoPhonelist;
	}
	
	//按昵称查询此会员的所有手机号
	public List<MemberDTO> queryByMemberNick(String content, String userNickName) {
		Query q = new Query();
		q.addCriteria(Criteria.where("buyerNick").is(content));
		List<MemberDTO> mobilelist = memberRepository.find(q, userNickName);
		return mobilelist;
	}
	
	//按昵称查询会员是否存在
	public long queryMemberByNick(String content,String userNickName){
		Query q = new Query();
		q.addCriteria(Criteria.where("buyerNick").is(content));
		long buyerNickCount = memberRepository.count(q, userNickName);
		return buyerNickCount;
	}
	//添加多个会员昵称，查询会员的信息
	public List<MemberDTO> queryMemberByNicks(List<String> namelist,String userId){
		Query q = new Query();
		q.addCriteria(Criteria.where("buyerNick").in(namelist));
		List<MemberDTO> list = memberRepository.find(q, userId);
		return list;
	}
	
	//按手机号查询是否与会员手机号匹配
	public long queryMembersByMobile(String content,String userNickName){
		Query q = new Query();
		q.addCriteria(Criteria.where("phone").is(content));
		long memberInfoPhone =memberRepository.count(q, userNickName);
		return memberInfoPhone;
	}
	
	//查询添加的手机号 会员是否已通过会员昵称添加进去
	public long queryBlackStatusMemberInfo(String content,String userNickName){
		Query q = new Query();
		q.addCriteria(Criteria.where("phone").is(content));
		q.addCriteria(Criteria.where("blackStatus").is(0));
		long blackStatusMemberInfo =memberRepository.count(q, userNickName);
		return blackStatusMemberInfo;
	}
	
	//查询添加的客户昵称 会员是否已通过手机号添加进去
	public long queryBlackBuyerNickStatusMemberInfo(String content,String userNickName){
		Query q = new Query();
		q.addCriteria(Criteria.where("buyerNick").is(content));
		q.addCriteria(Criteria.where("blackStatus").is(0));
		long blackStatusMemberInfo =memberRepository.count(q, userNickName);
		return blackStatusMemberInfo;
	}
	
	//批量插入会员（邱洋—2017-06-29 16:13:21）
	public void insertMemberDTOList(List<MemberDTO> list,String userNickName){
		memberRepository.saveList(list, userNickName);
	}
	
	/**
	 * 根据msgId查询符合发送记录的会员信息
	 */
	public List<String> findMemberPhoneList(String userId,Long msgId,Date bTime,Date eTime){
		Criteria criteria = Criteria.where("userId").is(userId);
		if(msgId != null){
			ArrayList<Long> msgIds = new ArrayList<Long>();
			msgIds.add(msgId);
			criteria.and("msgIdList").in(msgIds);
		}else {
			return null;
		}
//		if(bTime != null && eTime != null){
//			criteria.and("lastTradeTime").gte(bTime.getTime()).lte(eTime.getTime());
//		}else {
//			return null;
//		}
		Query query = new Query(criteria);
		List<MemberDTO> memberDTOList = memberRepository.find(query, userId);
		List<String> phoneList = new ArrayList<String>();
		if(memberDTOList != null && memberDTOList.size() > 0){
			for (MemberDTO memberDTO : memberDTOList) {
				if(memberDTO.getPhone() != null && !"".equals(memberDTO.getPhone())){
					phoneList.add(memberDTO.getPhone());
				}
			}
		}
		return phoneList;
	}
	

	
	
	/**
	 * Gg
	 * 会员信息分页条件查询
	 * @param contextPath
	 * @param pageNo
	 * @param userNickName
	 * @param memberCriteriaVoO
	 * @return
	 * Gg
	 */
	/*public Pageination<MemberDTO> findMemberInfoPageList(String contextPath, Integer pageNo,
			 String userNickName,MemberCriteriaVo memberCriteriaVoO){
		//先设置每页显示的条数为15条
		Integer currentRows = 15;
		Query query = new Query();
		//封装params
		StringBuilder params = this.memberInfoParams(userNickName, memberCriteriaVoO);
		//封装page
		Pageination<MemberDTO> page = new Pageination<MemberDTO>();
		page.setPageNo(pageNo);
		page.setPageSize(currentRows);
		query.with(new Sort(new Sort.Order(Direction.DESC,"lastTradeTime")));
		query = packageCriteriaParam(query, memberCriteriaVoO);
		//查询数据
		if(null!=userNickName&&!"".equals(userNickName)){
			page = memberRepository.findPage(page, query, userNickName);
		}
		//封装分页数据
		page.pageView(contextPath, params.toString());
		return page;
		
	}*/
	
	/**
	 * Gg
	 * 会员信息分页条件查询(解密)
	 * @param contextPath
	 * @param pageNo
	 * @param userNickName
	 * @param memberCriteriaVoO
	 * @return
	 * Gg
	 * @throws SecretException 
	 */
	public Pageination<MemberDTO> findMemberInfoPageList(String contextPath, Integer pageNo,
			 String userNickName,MemberCriteriaVo memberCriteriaVoO,String session) throws SecretException{
		//先设置每页显示的条数为15条
		Integer currentRows = 15;
		Query query = new Query();
		//封装params
		StringBuilder params = this.memberInfoParams(userNickName, memberCriteriaVoO,session);
		//封装page
		Pageination<MemberDTO> page = new Pageination<MemberDTO>();
		page.setPageNo(pageNo);
		page.setPageSize(currentRows);
		query.with(new Sort(new Sort.Order(Direction.DESC,"lastTradeTime")));
		query = packageCriteriaParam(query, memberCriteriaVoO);
		//查询数据
		if(null!=userNickName&&!"".equals(userNickName)){
			page = memberRepository.findPage(page, query, userNickName);
		}
		//封装分页数据
		page.pageView(contextPath, params.toString());
		return page;
		
	}
	
	
	/**
	 * Gg
	 * 分页条件封装
	 * @param userId
	 * @param memberCriteriaVoO
	 * @return
	 * Gg
	 * @throws SecretException 
	 */
	private StringBuilder memberInfoParams(String userId,MemberCriteriaVo memberCriteriaVoO,String session) throws SecretException{
		StringBuilder params = new StringBuilder();
		if(userId!=null && !"".equals(userId)){
			params.append("&userId=").append(userId);
		}
		//买家名称
		if(memberCriteriaVoO.getBuyerName()!=null && !"".equals(memberCriteriaVoO.getBuyerName())){
			//将buyerNick 解密
			params.append("&buyerNick=").append(memberCriteriaVoO.getBuyerName());
		}
		//最大交易额
		if(memberCriteriaVoO.getMaxTradePrice()!=null && !"".equals(memberCriteriaVoO.getMaxTradePrice())){
			params.append("&maxTradePrice=").append(memberCriteriaVoO.getMaxTradePrice());
		}
		//最小交易额
		if(memberCriteriaVoO.getMinTradePrice()!=null && !"".equals(memberCriteriaVoO.getMinTradePrice())){
			params.append("&minTradePrice=").append(memberCriteriaVoO.getMinTradePrice());
		}
		//平均客单价---最大
		if(memberCriteriaVoO.getMaxAvgPrice()!=null && !"".equals(memberCriteriaVoO.getMaxAvgPrice())){
			params.append("&maxAvgPrice=").append(memberCriteriaVoO.getMaxAvgPrice());
		}
		//平均客单价---最小
		if(memberCriteriaVoO.getMinAvgPrice()!=null && !"".equals(memberCriteriaVoO.getMinAvgPrice())){
			params.append("&minAvgPrice=").append(memberCriteriaVoO.getMinAvgPrice());
		}
		//成功交易次数---最大
		if(memberCriteriaVoO.getMaxTradeNum()!=null && !"".equals(memberCriteriaVoO.getMaxTradeNum())){
			params.append("&maxTradeNum=").append(memberCriteriaVoO.getMaxTradeNum());
		}
		//成功交易次数---最小
		if(memberCriteriaVoO.getMinTradeNum()!=null && !"".equals(memberCriteriaVoO.getMinTradePrice())){
			params.append("&minTradeNum=").append(memberCriteriaVoO.getMinTradeNum());
		}
		//最后交易时间---开始
		if(memberCriteriaVoO.getTradeStartTime()!=null && !"".equals(memberCriteriaVoO.getTradeStartTime())){
			params.append("&tradeStartTime=").append(memberCriteriaVoO.getTradeStartTime());
		}
		//最后交易时间---结束
		if(memberCriteriaVoO.getTradeEndTime()!=null && !"".equals(memberCriteriaVoO.getTradeEndTime())){
			params.append("&tradeEndTime=").append(memberCriteriaVoO.getTradeEndTime());
		}
		//时间段查询
		if(memberCriteriaVoO.getTradeStartTime()!=null && !"".equals(memberCriteriaVoO.getTradeStartTime())){
			params.append("&tradeStartTime2=").append(memberCriteriaVoO.getTradeStartTime());
		}
		if(memberCriteriaVoO.getTradeEndTime()!=null && !"".equals(memberCriteriaVoO.getTradeEndTime())){
			params.append("&tradeEndTime2=").append(memberCriteriaVoO.getTradeEndTime());
		}
		return params;
	}
	
	/**
	 * Gg
	 * 客户详情
	 * @param buyerNick
	 * @param userNickName
	 * @return
	 * Gg
	 */
	public MemberDTO queryByMemberInfoDetails(String buyerNick,String userNickName){
		Query q = new Query();
		q.addCriteria(Criteria.where("buyerNick").is(buyerNick));
		MemberDTO memberDTO = memberRepository.findOne(q, userNickName);
		return memberDTO;
	}
	
	/**
	 * Gg
	 * 有明文的情况
	 * @param uId
	 * @param userNickName
	 * @return
	 * Gg
	 */
	public MemberDTO queryByMemberInfoDetails1(String uId,String userNickName){
		Query q = new Query();
		if(uId!=null && !"".equals(uId)){
			q.addCriteria(Criteria.where("_id").is(uId));
		}
		MemberDTO memberDTO = memberRepository.findOne(q, userNickName);
		return memberDTO;
	}
	
	/**
	 * Gg
	 * 通過 buyerNick 查詢 會員 uId
	 * @param buyerNick
	 * @param userNickName
	 * @return
	 * Gg
	 */
	public String finduId(List<String> buyerNick,String userNickName){
		Query q = new Query();
		q.addCriteria(Criteria.where("buyerNick").in(buyerNick));
		MemberDTO memberDTO = memberRepository.findOne(q, userNickName);
		return memberDTO.get_id();
	}

	/**
	 * 通过昵称查询会员等级 
	 * @return 
	 */
	public String findMemberIsCurGradeNameByNick(String buyerNick,String userNickName) {
		Query q = new Query();
		q.addCriteria(Criteria.where("buyerNick").is(buyerNick));
		MemberDTO memberDTO = memberRepository.findOne(q, userNickName);
		return memberDTO.getCurGradeName();
	}
	
	/**
	 * Gg
	 * 清空黑名单 将黑名单会员改为正常会员
	 * @param userId
	 * Gg
	 */
	public void updateMemberInfoStatus(String userId){
		Query query = new Query();
		Update update = new Update();
		update.set("blackStatus", 0);
		memberRepository.updateMemberInfoBlackStatus(query, update, userId);
	}

	
	
	/** 
	 * Gg
	 * 更新全部符合黑名单的会员
     * Gg
     */ 
	public void updateMemberBlackAll(Map<String, Object> map) {
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		String userId=(String) map.get("userId");
		String type = (String) map.get("type");
		String session = (String) map.get("session");
		List<String> data=(List<String>) map.get("datas");
		List<String> encryptionContent =  new ArrayList<String>();
		if("2".equals(type)){
			try {
				Map<String,String> encryptMap = securityClient.encrypt(data, EncrptAndDecryptClient.SEARCH, session);
				for (Entry<String,String> entry : encryptMap.entrySet()) {
					encryptionContent.add(entry.getValue());
				}
			} catch (SecretException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				Map<String,String> encryptMap = securityClient.encrypt(data, EncrptAndDecryptClient.PHONE, session);
				for (Entry<String,String> entry : encryptMap.entrySet()) {
					encryptionContent.add(entry.getValue());
				}
			} catch (SecretException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Query query = new Query();
		Update update = new Update();
		update.set("blackStatus", 1);
		String field = "phone";
		if("2".equals(type))
			field  = "buyerNick";
		query.addCriteria(Criteria.where(field).in(encryptionContent));
		long s1 = System.currentTimeMillis();
		memberRepository.updateAll(query, update, userId);
		loger.info("========================批量更新会员黑名单状态耗时:"+(System.currentTimeMillis()-s1));
	}
	
	/** 
	 * Gg
	 * 逐个修改会员状态
     * Gg
     */ 
	public void updateMemberBlack(Map<String, Object> map) {
		String userId=(String) map.get("userId");
		String type = (String) map.get("type");
		List<String> data=(List<String>) map.get("datas");
		Query query = null;
		Update update = new Update();
		update.set("blackStatus", 1);
		String field = "phone";
		if("2".equals(type))
			field  = "buyerNick";
		long s1 = System.currentTimeMillis();
		for (String str : data) {
			 query = new Query();
			 query.addCriteria(Criteria.where(field).is(str));
			 memberRepository.update(query, update, userId);
		}
		loger.info("========================逐条更新会员黑名单状态耗时:"+(System.currentTimeMillis()-s1));
	}
	
	/** 
	 * Gg
	 * 移除黑名单时将被标记黑名单的会员还原
     * Gg
     */
	public void removeMemberBlack(String userNickName,List<String> data,String field) {
		Query query = new Query();
		Update update = new Update();
		update.set("blackStatus", 0);
		query.addCriteria(Criteria.where(field).in(data));
		long s1 = System.currentTimeMillis();
		memberRepository.updateAll(query, update, userNickName);
		loger.info("========================移除黑名单还原会员黑名单状态耗时:"+(System.currentTimeMillis()-s1));
	}

	public void batchSaveDecryptMemberList(List<MemberDTO> members) {
		try {
			if(null!=members&&members.size()>0){
				List<MemberDTO> refundList = new ArrayList<MemberDTO>();
				List<MemberDTO> saveList = new ArrayList<MemberDTO>();
				List<MemberDTO> updateList = new ArrayList<MemberDTO>();
				String userNickName = members.get(0).getUserId();
				for (MemberDTO memberInfo : members) {
					if(null!=memberInfo.getBuyerNick()&&!"".equals(memberInfo.getBuyerNick())){
						 Query query = new Query();
					     query.addCriteria(Criteria.where("buyerNick").is(memberInfo.getBuyerNick()));
						 MemberDTO memberDTO = findOne(query,memberInfo.getUserId());
						 if(null!=memberDTO){
							 if(null!=memberInfo.getRefund_status()&&1==memberInfo.getRefund_status()){
								    refundList.add(memberInfo);
							 }else{
								 calculateMemberTrade(memberDTO,memberInfo);
								 memberInfo.setCreatedDate(memberDTO.getCreatedDate());
								 memberInfo.setTimestampId(memberDTO.getTimestampId());
								 memberInfo.set_id(memberDTO.get_id());
								 
								 //新加的
								 memberInfo.setSex(memberDTO.getSex()); 
								 memberInfo.setAge(memberDTO.getAge()); 
								 memberInfo.setOccupation(memberDTO.getOccupation()); 
								 memberInfo.setBirthday(memberDTO.getBirthday()); 
								 memberInfo.setQq(memberDTO.getQq()); 
								 memberInfo.setWechat(memberDTO.getWechat()); 
								 memberInfo.setPhoneRange(memberDTO.getPhoneRange()); 
								 memberInfo.setEmail(memberDTO.getEmail()); 
								 memberInfo.setRemarks(memberDTO.getRemarks()); 
								 memberInfo.setEmailType(memberDTO.getEmailType()); 
								 memberInfo.setRemarksTime(memberDTO.getRemarksTime()); 
								 if(null!=memberInfo.getTid()&&!"".equals(memberInfo.getTid())){
									 if(!memberInfo.getTid().equals(memberDTO.getTid())){
										 packageTempData(memberInfo, memberDTO);
									 }
								 }
								 updateList.add(memberInfo);
							 }
						 }else{
							 memberInfo.setBlackStatus(0);
							 memberInfo.setCommentStatus(0);
							 memberInfo.setCreatedDate(new Date());
							 memberInfo.setTimestampId(MemberIdWorker.getInstance().nextId());
							 saveList.add(memberInfo);
						 }
					}else{
						 loger.error("买家昵称为空！");
					}
				}
				if(null!=refundList&&refundList.size()>0){
					for (MemberDTO memberDTO : refundList) {
						Query query = new Query();  
						query.addCriteria(Criteria.where("buyerNick").is(memberDTO.getBuyerNick()));
						Update update  = new Update();
						update.set("refund_status", memberDTO.getRefund_status());
						memberRepository.update(query, update, memberDTO.getUserId());
					}
				}
				if(null!=saveList&&saveList.size()>0){
					memberRepository.batchInsertDecryptMember(saveList,userNickName);
				}
				if(null!=updateList&&updateList.size()>0){
					for (MemberDTO memberDTO : updateList) {
						 memberRepository.save(memberDTO,userNickName);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			 loger.error(e.getMessage()+"批量保存失败！");
			 throw new RuntimeException();
		}
		
	}

	
	
	/**
	 * 根据条件查询一个集合
	 * ZTK2017年7月24日下午3:37:25
	 */
	public List<MemberDTO> findMemberList(Query query,String userId){
		List<MemberDTO> memberList = memberRepository.find(query, userId);
		return memberList;
	}
	
	/**
	 * 查询店铺会员个数
	 * ZTK2017年7月25日下午4:42:41
	 */
	public long findMemberCount(String userId){
		if(userId != null && !"".equals(userId)){
			long count = memberRepository.count(new Query(), userId);
			return count;
		}else {
			return 0;
		}
	}
	
	
	/**
	 * Gg
	 * 黑名单手动添加，添加(昵称，手机号都要有)
	 * @param userNickName
	 * @param mobile
	 * @param type
	 * @return
	 * Gg
	 */
	public MemberDTO queryByMemberInfoNick(String userNickName,String mobile,String type){
		Query q = new Query();
		if("1".equals(type)){
			q.addCriteria(Criteria.where("phone").is(mobile));
		}else{
			q.addCriteria(Criteria.where("buyerNick").is(mobile));
		}
		MemberDTO memberDTO = memberRepository.findOne(q, userNickName);
		return memberDTO;
	}
	
	/**
	 * Gg
	 * 黑名单导入,(昵称，手机号都要有)
	 * @param userNickName
	 * @param data
	 * @param type
	 * @return
	 * Gg
	 */
	public List<MemberDTO> queryByMemberDTO(String userNickName,List<String> data,String type){
		Query query = new Query();
		if("1".equals(type)){
			query.addCriteria(Criteria.where("phone").in(data));
		}else{
			query.addCriteria(Criteria.where("buyerNick").in(data));
		}
		List<MemberDTO> memberDTOList = memberRepository.find(query, userNickName);
		return memberDTOList;
	}
	
	private void packageTempData(MemberDTO memberInfo, MemberDTO memberDTO) {
		 List<String> tids = new ArrayList<String>();
	     List<String> tempTid = memberInfo.getTempTid();
	     List<String> tempTid1 = memberDTO.getTempTid();
	     if(null!=tempTid1){
		     for (String string : tempTid1) {
		    	 if(!tids.contains(string)){
		    		 tids.add(string);
		    	 }
			 }
	     } 
	     if(null!=tempTid){
		     for (String string : tempTid) {
		    	 if(!tids.contains(string)){
		    		 tids.add(string);
		    	 }
		     }
	     }
	     memberInfo.setTempTid(tids);
	     tids=null;
		
	     List<String> tradeFroms = new ArrayList<String>();
	     List<String> tempTradeFrom = memberInfo.getTempTradeFrom();
	     List<String> tempTradeFrom1 = memberDTO.getTempTradeFrom();
	     if(null!=tempTradeFrom){
	    	 for (String string : tempTradeFrom) {
	    		 if(!tradeFroms.contains(string)){
	    			 tradeFroms.add(string);
	    		 }
	    	 }
	     }
	     if(null!=tempTradeFrom1){
	    	 for (String string : tempTradeFrom1) {
	    		 if(!tradeFroms.contains(string)){
	    			 tradeFroms.add(string);
	    		 }
	    	 }
	     }
	     memberInfo.setTempTradeFrom(tradeFroms);
	     tradeFroms=null;
	     
	     List<String> provinces = new ArrayList<String>();
	     List<String> tempProvince = memberInfo.getTempProvince();
	     List<String> tempProvince1 = memberDTO.getTempProvince();
	     if(null!=tempProvince){
	    	 for (String string : tempProvince) {
	    		 if(!provinces.contains(string)){
	    			 provinces.add(string);
	    		 }
	    	 }
	     }
	     if(null!=tempProvince1){
	    	 for (String string : tempProvince1) {
	    		 if(!provinces.contains(string)){
	    			 provinces.add(string);
	    		 }
	    	 }
	     }
	     memberInfo.setTempProvince(provinces);
	     provinces=null;
	     List<String> products = new ArrayList<String>();
	     List<String> product1 = memberInfo.getTempProduct();
	     List<String> product2 = memberDTO.getTempProduct();
	     if(null!=product1){
	    	 for (String string : product1) {
	    		 if(!products.contains(string)){
	    			 products.add(string);
	    		 }
	    	 }
	     }
	     if(null!=product2){
	    	 for (String string : product2) {
	    		 if(!products.contains(string)){
	    			 products.add(string);
	    		 }
	    	 }
	     }
	     memberInfo.setTempProduct(products);
	     products=null;
	}
	
	
	/*
	 * 同步旧库数据到新库数据专用
	 */
	public List<MemberDTO>  findMigrateMemberDataList(String userNickName,String objectId,int limit){
		if(null!=userNickName&&!"".equals(userNickName)&&null!=objectId&&!"".equals(objectId)){
			Query query = new Query();
			if(!"0".equals(objectId)){
				query.addCriteria(Criteria.where("_id").gt(new ObjectId(objectId)));
			}
			query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "_id")));  
			query.skip(0).limit(limit);
			return memberRepository.find(query,userNickName);  
		}else{
			return null;
		}
	}
	
	
	
	
	/**
	 * @Description:获取加密解密使用 的token
	 * @Copy_author jackstraw_yu
	 */
	 private  String 	getSessionkey(String userNickName,boolean flag){
		    String  token = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName);
			if(null!=token&&!"".equals(token)&&flag){
				 return token;
			}else{
				UserInfo user = userInfoService.queryUserTokenInfo(userNickName);
				if(user!=null)
					if(null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
						 cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userNickName,user.getAccess_token());
						 return user.getAccess_token(); 
					}
			}
			return "";
	 }
	 
	 /**
	  * 更新会员中差评状态
	  * ztk2017年10月10日下午4:36:58
	  */
	 public boolean updateMemberCommentStatus(String buyerNick,String userId){
		 try {
			 Criteria criteria = new Criteria("userId").is("userId");
			 criteria.and("buyerNick").is(buyerNick);
			 Update update = new Update();
			 update.set("commentStatus", 1);
			 memberRepository.update(new Query(criteria), update, userId);
			 return true;
		 } catch (Exception e) {
			e.printStackTrace();
			return false;
		 }
	 }
	 
	 
	/** 
	 * 短信接口上行,接收到退订回N<br/>
	 * 设置会员为黑名单 <br/> 
	 * @param  memberInfo
	 * @param  status    设定文件 
	 * @return void    返回类型 
	 * @author jackstraw_yu
	 * @date 2017年11月17日 上午10:33:28
	 */
	public void updateMemberBlackStatus(MemberDTO memberInfo,Integer status){
			Query query = new Query();
			Update update  = new Update();
			update.set("blackStatus",status);
			//更新全部符合该手机号的会员
			//query.addCriteria(Criteria.where("_id").is(memberInfo.get_id()));
			query.addCriteria(Criteria.where("phone").is(memberInfo.getPhone()));
			memberRepository.update(query, update, memberInfo.getUserId());
		}
		
}
 