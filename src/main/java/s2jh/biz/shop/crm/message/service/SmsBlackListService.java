package s2jh.biz.shop.crm.message.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.core.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.api.SecretException;
import com.taobao.api.security.SecurityClient;

import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.dao.SmsBlackListDao;
import s2jh.biz.shop.crm.message.dao.SmsMobileDao;
import s2jh.biz.shop.crm.message.entity.SmsBlacklist;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;


/**
* @ClassName: SmsBlackListService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author:jackstraw_yu
* @date 2017年2月22日
*
*/
@Service
//@Transactional
public class SmsBlackListService extends BaseService<SmsBlacklist, Long>{
	
	@Autowired
	private SmsBlackListDao smsBlackListDao;
	
	@Autowired
	private SmsMobileDao smsMobileDao;
	
	@Autowired
	private UserOperationLogDao userOperationLogDao;

	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Autowired
	private VipMemberService vipMemberService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private CacheService cacheService;
	
	@Override
	protected BaseDao<SmsBlacklist, Long> getEntityDao() {
		return smsBlackListDao;
	}
	
	
	/**
	 * 根据卖家编号，查询所有黑名单信息	
	 */
	public List<SmsBlacklist> findAllSmsBlacklist(String userId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("addUserid", userId);
		List<SmsBlacklist> list =myBatisDao.findList(SmsBlacklist.class.getName(), "findAllSmsBlacklist", map);		
		return list;
	}
	
	/**
	 * 查询卖家所有黑名单的手机号
	 * 赵天奎2017年7月20日下午2:37:43
	 * @throws SecretException 
	 */
	public List<String> findAllPhones(String userId) throws SecretException{
		List<String> phones = myBatisDao.findList(SmsBlacklist.class.getName(), "findAllPhones", userId);
		if(phones != null && !phones.isEmpty()){
			List<String> resultList = new ArrayList<String>();
			EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
			String sessionKey = userInfoService.validateFindSessionKey(userId);
			for (String phone : phones) {
				if(EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
					resultList.add(decryptClient.decryptData(phone, EncrptAndDecryptClient.PHONE, sessionKey));
				}else {
					resultList.add(phone);
				}
			}
			return resultList;
		}else {
			return null;
		}
	}
	
	/**
	  * 创建人：邱洋
	  * @Title: 根据用户名和手机号查询黑名单信息
	  * @date 2017年2月10日--下午4:12:35 
	  * @return List<SmsBlacklist>
	  * @throws
	 */
	public List<SmsBlacklist> findSmsBlacklist(String userId,List<String> phone){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("phone", phone);
		List<SmsBlacklist> list = myBatisDao.findList(SmsBlacklist.class.getName(), "findBlackList", map);	
		return list;
	}
	
	/**
	 * 根据卖家输入的手机号，时间，备注查询数据
	 */
	public List<SmsBlacklist> conditionQuery(String userId,String phone,String remarks,String startDate,String endDate){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("phone", phone);
		map.put("remarks", remarks);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		List<SmsBlacklist> list = myBatisDao.findList(SmsBlacklist.class.getName(), "conditionQuery", map);
		return list;
	}
	
	public void saveSmsBlacklist(SmsBlacklist smsBlacklist,String userId,HttpServletRequest request,String type,String session) throws SecretException{
		String [] contents = smsBlacklist.getContent().trim().split(",|，");
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		MemberDTO memberInfo = null;
		for(String content:contents){
			SmsBlacklist smsBlacklist2 = new SmsBlacklist();
			memberInfo = new MemberDTO();
			memberInfo.setUserId(userId);
			if("1".equals(type)){
				String encryptionPhone =  securityClient.encrypt(content, EncrptAndDecryptClient.PHONE, session);
				MemberDTO memberDTO = vipMemberService.queryByMemberInfoNick(userId, encryptionPhone, type);
				smsBlacklist2.setUserId(userId);
				smsBlacklist2.setCreatedate(new Date());
				smsBlacklist2.setAddSource("手动添加");
				smsBlacklist2.setIsDelete("1");
				smsBlacklist2.setType(smsBlacklist.getType());
				smsBlacklist2.setContent(encryptionPhone);
				smsBlacklist2.setPhone(encryptionPhone);
				if(memberDTO!=null){
					smsBlacklist2.setNick(memberDTO.getBuyerNick());
				}
				memberInfo.setPhone(encryptionPhone);
				Long count1 = vipMemberService.queryMembersByMobile(encryptionPhone, userId);
				if(count1!=0){
					memberInfo.setBlackStatus(1);
					vipMemberService.findListMemberDTOAndUpdate(memberInfo);
					//查询昵称是否在会员表中(存在执行以下方法，不存在不执行)
				}
			}else{
				String encryptionNick = securityClient.encrypt(content, EncrptAndDecryptClient.SEARCH, session);
				MemberDTO memberDTO = vipMemberService.queryByMemberInfoNick(userId, encryptionNick, type);
				smsBlacklist2.setUserId(userId);
				if(memberDTO!=null){
					smsBlacklist2.setPhone(memberDTO.getPhone());
				}
				memberInfo.setBuyerNick(encryptionNick);
				smsBlacklist2.setCreatedate(new Date());
				smsBlacklist2.setAddSource("手动添加");
				smsBlacklist2.setIsDelete("1");
				smsBlacklist2.setType(smsBlacklist.getType());
				smsBlacklist2.setContent(encryptionNick);
				smsBlacklist2.setNick(encryptionNick);
				Long count = vipMemberService.queryMemberByNick(encryptionNick, userId);
				if(count!=0){
					memberInfo.setBlackStatus(1);
					vipMemberService.findListMemberDTOAndUpdate(memberInfo);
				}
			}
			smsBlackListDao.save(smsBlacklist2);
		}
		//操作日志
		UserOperationLog op = new UserOperationLog();
		op.setUserId(userId);
		op.setFunctions("短信黑名单添加");
		op.setType("添加");
		op.setDate(new Date());
		op.setState("成功");
		op.setRemark("短信黑名单添加");
		op.setFunctionGens("33");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		userOperationLogDao.save(op);
	}
	
	private void doLog(HttpServletRequest request, String userId) {
		//操作日志
		UserOperationLog op = new UserOperationLog();
		op.setUserId(userId);
		op.setFunctions("短信黑名单添加");
		op.setType("添加");
		op.setDate(new Date());
		op.setState("成功");
		op.setRemark("短信黑名单添加");
		op.setFunctionGens("33");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		userOperationLogDao.save(op);
	}
	
	public List<SmsBlacklist> findSmsBlack(){
		Map<String,Object> map = new HashMap<String, Object>();
		List<SmsBlacklist> smsBlack = myBatisDao.findList("s2jh.biz.shop.crm.message.entity.SmsBlacklist", "findSmsList1", map);
		return smsBlack;
		
	}
	
	
	/**
	 * 营销中心页面中----短信发送记录页面的跳转----会员群发记录,输入日期查询并获取总条数
	 */
	public Integer findTotalCountByContent(String content,String userId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("content", content);
		map.put("userId", userId);
		Long totalCount = myBatisDao.findBy("s2jh.biz.shop.crm.message.entity.SmsBlacklist", "findTotalCountByContent", map);
		Integer i = Integer.valueOf(totalCount.toString());
		return i;
		
	}
	
	/**
	 * Gg
	 * 黑名单分页查询(加密版)
	 * @param userId
	 * @param contextPath
	 * @param content
	 * @param pageNo
	 * @param session
	 * @return
	 * @throws SecretException
	 * Gg
	 */
	public Pagination findPaginationByContent(String userId,String contextPath,String content, Integer pageNo,String session) throws SecretException{
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		//设置起始页
		if(pageNo==null){
			pageNo =1;	
		}
		//先设置每页显示的条数为3条
		Integer currentRows = 15;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;
		//计算出总页数
		Integer totalCount = findTotalCountByContent(content,userId);
		//Integer totalPage = (int) Math.ceil(1.0*totalCount/currentRows);
		//调用dao层查询出每页显示的list<SmsSendRecord>集合
		Map<String,Object> map = new HashMap<String,Object>();
		//将content 加密
		try {
			if(content!=null){
				String encryContent = EncrptAndDecryptClient.getInstance().search(content, EncrptAndDecryptClient.SEARCH, session);
				//String encryContent = securityClient.encrypt(content, EncrptAndDecryptClient.SEARCH, session);
				String type = this.findContentType(userId, encryContent);
				if(type!=null && "2".equals(type)){
					map.put("content", encryContent);
				}else{
					//String encryPhone = EncrptAndDecryptClient.getInstance().search(content, EncrptAndDecryptClient.PHONE, session);
					String encryPhone = securityClient.encrypt(content, EncrptAndDecryptClient.PHONE, session);
					map.put("content", encryPhone);
				}
			}
		} catch (Exception e) {
			map.put("content", content);
			System.out.println("****************************加密出错，session 可能为空!*********************************");
			e.printStackTrace();
			// TODO: handle exception
		}
		map.put("userId", userId);
		/*map.put("content", content);*/
		map.put("startRows",startRows);
		map.put("currentRows", currentRows);
		List<SmsBlacklist> smsSendRecordList =  myBatisDao.findLimitList(SmsBlacklist.class.getName(), "querySmsList", map, currentRows);
		SmsBlacklist smsBlacklist = null;
		try {
			for(int i = 0;i<smsSendRecordList.size();i++){
				smsBlacklist = smsSendRecordList.get(i);
				String type = smsSendRecordList.get(i).getType();
				if(type.equals("1")){
					String phoneContent = smsSendRecordList.get(i).getContent();
					smsBlacklist.setContent(securityClient.decrypt(phoneContent, EncrptAndDecryptClient.PHONE, session));
					smsSendRecordList.set(i, smsBlacklist);
				}else{
					String nickContent = smsSendRecordList.get(i).getContent();
					smsBlacklist.setContent(securityClient.decrypt(nickContent, EncrptAndDecryptClient.SIMPLE, session));
					smsSendRecordList.set(i, smsBlacklist);
				}
			}
		} catch (Exception e) {
		 System.out.println("*********************解密失败，session可能为空！****************");
		 e.printStackTrace();
			// TODO: handle exception
		}
		//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
		//smsSendRecordList:每页显示的list集合或者当前页显示的list集合
		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, smsSendRecordList);
		
		StringBuilder params = new StringBuilder();
		if(content!=null){
			params.append("&content=").append(content);
		}
		if(userId != null){
			params.append("&userId=").append(userId);
		}
		
		//拼接分页的后角标中的跳转路径与查询的条件
		String url =contextPath+"/crms/marketingCenter/smsBlack";
		pagination.pageView(url, params.toString());
		return pagination;
	}	
	
	/**
	 * Gg
	 * 黑名单移除(加密)
	 * @param content
	 * @param userId
	 * @param type
	 * Gg
	 */
	public void updateIsdelete(String  content,String userId,String type){
		Map<String,Object> parameters = new HashMap<String, Object>();
		if("1".equals(type)){
			parameters.put("phone", content);
		}else{
			parameters.put("nick", content);
		}
		//parameters.put("content", content);
		parameters.put("userId", userId);
		myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsBlacklist", "updateIsdelete", parameters);
	}
	
	/**
	 * Gg
	 * 查询黑名单添加 内容是否已存在黑名单列表中
	 * @param userId
	 * @param content
	 * @return
	 * Gg
	 */
	public List<SmsBlacklist> findContent(String userId,List<String> content){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("content", content);
		List<SmsBlacklist> lists = myBatisDao.findList(SmsBlacklist.class.getName(),"findContents",map);	
		return lists;
	}


	/**
	 * Gg
	 * 黑名单上传保存操作
	 * @param map
	 * Gg
	 */
	public void processDatas(Map<String,Object> map){
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		String userId=(String) map.get("userId");
		String type = (String)map.get("type");
		String session =(String)map.get("session");
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
		
		List<SmsBlacklist> list = new ArrayList<SmsBlacklist>();
		List<MemberDTO> memberDTO= vipMemberService.queryByMemberDTO(userId, encryptionContent, type);
		SmsBlacklist smsBlack=null;
		if(memberDTO!=null && memberDTO.size()>0){
			for (MemberDTO memberDTO2 : memberDTO) {
				smsBlack = new SmsBlacklist();
				smsBlack.setUserId(userId);
				smsBlack.setType(type);
				if("1".equals(type)){
					smsBlack.setNick(memberDTO2.getBuyerNick());
					smsBlack.setPhone(memberDTO2.getPhone());
					smsBlack.setContent(memberDTO2.getPhone());
				}else{
					smsBlack.setPhone(memberDTO2.getPhone());
					smsBlack.setContent(memberDTO2.getBuyerNick());
					smsBlack.setNick(memberDTO2.getBuyerNick());
				}
				smsBlack.setIsDelete("1");
				smsBlack.setCreatedate(new Date());
				smsBlack.setAddSource("黑名单导入");
				//smsBlack各种set
				list.add(smsBlack);
			}
		}else{
			for (String mobile : encryptionContent){
				smsBlack = new SmsBlacklist();
				smsBlack.setUserId(userId);
				smsBlack.setType(type);
				if("1".equals(type)){
					smsBlack.setPhone(mobile);
					smsBlack.setContent(mobile);
				}else{
					smsBlack.setContent(mobile);
					smsBlack.setNick(mobile);
				}
				smsBlack.setIsDelete("1");
				smsBlack.setCreatedate(new Date());
				smsBlack.setAddSource("黑名单导入");
				//smsBlack各种set
				list.add(smsBlack);
		    }
		}
		//dao执行 入参list
		myBatisDao.execute(SmsBlacklist.class.getName(), "insertDataBatch",list);
	}
	
	/**
	 * Gg
	 * 清空黑名单数据
	 * Gg
	 */
	public void deleteSmsBlack(String userId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		myBatisDao.execute(SmsBlacklist.class.getName(), "deleteSmsBlack", map);
	}
	
	
	/**
	 * Gg
	 * 批量删除黑名单数据
	 * Gg
	 */
	public void batchDeleteSmsBlack(List<String> contents ,String userId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("data", contents);
		map.put("userId", userId);
		int execute = myBatisDao.execute(SmsBlacklist.class.getName(), "deleteBeachSmsBlack", map);
		System.out.println(execute);
		
	}
	
	/**
	 * Gg
	 * 移出黑名单专用(通过content 查询 type,判断需要加密的类型)
	 * @param userId
	 * @param content
	 * Gg
	 * @return 
	 */
	public String findContentType(String userId,String content){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("content", content);
		map.put("userId", userId);
		String type =myBatisDao.findBy(SmsBlacklist.class.getName(), "findContentType", map);
		return type;
	}
	
	/**
	 * Gg
	 * 定时任务加密手机号,昵称，内容
	 * @return
	 * Gg
	 * @throws SecretException 
	 */
	public String encryptionJob(){
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		Map<String,Object> map = new HashMap<String, Object>();
		List<SmsBlacklist> smsBlacklist = myBatisDao.findList(SmsBlacklist.class.getName(), "findSmsBlackListTiming", null);
		if(smsBlacklist!=null && smsBlacklist.size()>0){
			for (SmsBlacklist smsBlack : smsBlacklist) {
				if(smsBlack.getUserId()!=null && !"".equals(smsBlack.getUserId())){
					String session = this.getSessionkey(smsBlack.getUserId());
					//根据 type值 进行加密，type 为1 加密类型为 phone，反之 加密类型为 simple
					if(session!=null && !"".equals(session)){
						if(smsBlack.getType()!=null && !"".equals(smsBlack.getType())){
							try {
								if("1".equals(smsBlack.getType())){
									map.put("id", smsBlack.getId());
									//判断Nick 是否加密
									if(SecurityClient.isEncryptData(smsBlack.getNick(), EncrptAndDecryptClient.SEARCH)){
											map.put("nick", smsBlack.getNick());
									}else{
										map.put("nick", securityClient.encrypt(smsBlack.getNick(), EncrptAndDecryptClient.SEARCH, session));
									}
									//判断phone 是否加密
									if(SecurityClient.isEncryptData(smsBlack.getPhone(), EncrptAndDecryptClient.PHONE)){
											map.put("phone", smsBlack.getPhone());
									}else{
										map.put("phone", securityClient.encrypt(smsBlack.getPhone(), EncrptAndDecryptClient.PHONE, session));
									}
									//判断 content 是否加密
									if(SecurityClient.isEncryptData(smsBlack.getContent(), EncrptAndDecryptClient.PHONE)){
											map.put("content", smsBlack.getContent());
									}else{
										map.put("content", securityClient.encrypt(smsBlack.getContent(), EncrptAndDecryptClient.PHONE, session));
									}
								}else{
									map.put("id", smsBlack.getId());
									//判断Nick 是否加密
									if(SecurityClient.isEncryptData(smsBlack.getNick(), EncrptAndDecryptClient.SEARCH)){
											map.put("nick", smsBlack.getNick());
									}else{
										map.put("nick", securityClient.encrypt(smsBlack.getNick(), EncrptAndDecryptClient.SEARCH, session));
									}
									//判断phone 是否加密
									if(SecurityClient.isEncryptData(smsBlack.getPhone(), EncrptAndDecryptClient.PHONE)){
											map.put("phone", smsBlack.getPhone());
									}else{
										map.put("phone", securityClient.encrypt(smsBlack.getPhone(), EncrptAndDecryptClient.PHONE, session));
									}
									//判断 content 是否加密
									if(SecurityClient.isEncryptData(smsBlack.getContent(), EncrptAndDecryptClient.SEARCH)){
											map.put("content", smsBlack.getContent());
									}else{
										map.put("content", securityClient.encrypt(smsBlack.getContent(), EncrptAndDecryptClient.SEARCH, session));
									}
								}
								myBatisDao.execute(SmsBlacklist.class.getName(), "updateSmsBlackEncryption", map);
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}
						}
					}else{
						System.out.println("**************************加密失败，原因可能是session为空！！！！！！！！！！！*****************************");
					}
				}
			}
		}
		return null;
	}
	
	
	 /**
	  * 获取session
	  * @param userId
	  * @return
	  */
	 private String getSessionkey(String userId){
		String  session = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userId);
		if(session!=null && !"".equals(session)){
			return session;
		}else{
			UserInfo user = userInfoService.queryUserTokenInfo(userId);
			if(user!=null){
				if(null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
					cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userId,user.getAccess_token());
					return user.getAccess_token(); 
				}
			}
		}
		return "";
	}
	/**
	 * 查询当前买家是否在买家黑名单中
	 * @author: wy
	 * @time: 2017年9月1日 下午4:49:08
	 * @param sellerNick 卖家昵称
	 * @param buyerNick 买家昵称
	 * @return true 存在 false 不存在
	 * @throws SecretException 
	 */
	public boolean isExists(String sellerNick,String buyerNick,String buyerPhone) throws SecretException{
		if(ValidateUtil.isEmpty(sellerNick)){
			return false;
		}
		String session = this.getSessionkey(sellerNick);
		if(!EncrptAndDecryptClient.isEncryptData(buyerNick, EncrptAndDecryptClient.SEARCH)){
			buyerNick = EncrptAndDecryptClient.getInstance().encrypt(buyerNick, EncrptAndDecryptClient.SEARCH, session);
		}
		if(!EncrptAndDecryptClient.isEncryptData(buyerPhone, EncrptAndDecryptClient.PHONE)){
		    buyerPhone = EncrptAndDecryptClient.getInstance().encrypt(buyerPhone, EncrptAndDecryptClient.PHONE, session);
        }
		Map<String,String> map = new HashMap<String,String>(5);
		map.put("sellerNick", sellerNick);
		map.put("buyerNick", buyerNick);
		map.put("buyerPhone", buyerPhone);
		int count = this.myBatisDao.findBy(SmsBlacklist.class.getName(), "findExistsByNick", map);
		if(count == 0){
			return false;
		}else{
			return true;
		}
	}


	/** 
	* @Description: 短信上行,回复退订时,添加一条黑名单记录
	* @param  memberDTO
	* @param  source
	* @param  isDelete
	* @param  type
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月23日 上午11:19:28
	*/
	@Transactional
	public void addsmsBlacklist(MemberDTO memberDTO, String source,String isDelete,String type) {
		//首先通过手机号查询黑名单中是否存在
		Map<String,String> map = new HashMap<String,String>(5);
		map.put("userId", memberDTO.getUserId());
		map.put("mobile", memberDTO.getPhone());
		Integer count = myBatisDao.findBy(SmsBlacklist.class.getName(), "findExistsByPhone", map);
		if(count == null || count ==0){
			SmsBlacklist smsBlack = new SmsBlacklist();
			smsBlack.setUserId(memberDTO.getUserId());
			smsBlack.setContent(memberDTO.getPhone());
			smsBlack.setPhone(memberDTO.getPhone());
			smsBlack.setAddSource(source);
			smsBlack.setIsDelete(isDelete);
			smsBlack.setType(type);
			smsBlack.setCreatedate(new Date());
			smsBlack.setNick(memberDTO.getBuyerNick());

			smsBlack.setCreatedDate(new Date());
			smsBlack.setCreatedBy(memberDTO.getUserId());
			smsBlack.setLastModifiedBy(memberDTO.getUserId());
			smsBlack.setLastModifiedDate(new Date());
			smsBlackListDao.save(smsBlack);
		}
	}
	
	
}
