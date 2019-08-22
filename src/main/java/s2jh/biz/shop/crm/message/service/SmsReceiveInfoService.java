package s2jh.biz.shop.crm.message.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.SecretException;

import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.dao.SmsReceiveInfoDao;
import s2jh.biz.shop.crm.message.entity.SmsReceiveInfo;
import s2jh.biz.shop.crm.message.templateVo.SmsReceiveVO;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class SmsReceiveInfoService extends BaseService<SmsReceiveInfo, Long>{
	
	private Logger logger = LoggerFactory.getLogger(SmsReceiveInfoService.class);
	
	@Autowired
	private SmsReceiveInfoDao smsReceiveInfoDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	
	@Override
	protected BaseDao<SmsReceiveInfo, Long> getEntityDao() {
		return smsReceiveInfoDao;
	}
	
	/**
	 * 根据卖家昵称查询所有接收到的短信记录
	 */
	public List<SmsReceiveInfo> findSmsReceiveInfo(String taobaoNick){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("taobaoNick", taobaoNick);
		List<SmsReceiveInfo> list = myBatisDao.findList(SmsReceiveInfo.class.getName(), "findAllSmsReceiveInfo", map);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据卖家条件查询所接收到的短信记录
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SmsReceiveInfo> querySmsReceiveInfo(Map map){
		List<SmsReceiveInfo> list = myBatisDao.findList(SmsReceiveInfo.class.getName(), "querySmsReceiveInfo", map);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}

	/**
	 * 根据卖家条件查询所接收到的短信记录的分页
	 * ZTK2017年5月17日下午4:57:22
	 * @throws SecretException 
	 * @throws UnsupportedEncodingException 
	 */
	public Pagination querySmsReceiveInfoPagination(String contextPath,Integer pageNo,Map<String, Object> hashmap) throws SecretException, UnsupportedEncodingException{
		//先设置每页显示的条数为5条
		Integer currentRows = 10;
		//计算出起始行数
		Integer startRows = (pageNo-1) * currentRows;
		hashmap.put("startRows",startRows);
		hashmap.put("currentRows", currentRows);
		Integer totalCount = findTotalCountByMap(hashmap);
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String sessionKey = userInfoService.validateFindSessionKey((String) hashmap.get("userId"));
		List<SmsReceiveInfo> smsReceiveInfoList = myBatisDao.findLimitList(SmsReceiveInfo.class.getName(), "findLimitList", hashmap, currentRows);
		if(smsReceiveInfoList != null && !smsReceiveInfoList.isEmpty()){
			for (SmsReceiveInfo smsReceiveInfo : smsReceiveInfoList) {
				if(smsReceiveInfo.getBuyerNick() != null && EncrptAndDecryptClient.isEncryptData(smsReceiveInfo.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
					smsReceiveInfo.setBuyerNick(decryptClient.decryptData(smsReceiveInfo.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey));
				}
				if(smsReceiveInfo.getSendPhone() != null && EncrptAndDecryptClient.isEncryptData(smsReceiveInfo.getSendPhone(), EncrptAndDecryptClient.PHONE)){
					smsReceiveInfo.setSendPhone(decryptClient.decryptData(smsReceiveInfo.getSendPhone(), EncrptAndDecryptClient.PHONE, sessionKey));
				}
				if(smsReceiveInfo.getContent() != null && !"".equals(smsReceiveInfo.getContent())){
					smsReceiveInfo.setContent(URLDecoder.decode(smsReceiveInfo.getContent(), "utf-8"));
				}
			}
		}
		//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, smsReceiveInfoList);
		return pagination;
	}
	
	/**
	 * 根据卖家条件查询所接收到的短信记录(不分页)
	 * ZTK2017年5月17日下午5:18:53
	 * @throws SecretException 
	 * @throws UnsupportedEncodingException 
	 */
	public List<SmsReceiveInfo> findReceiveInfo(Map<String, Object> hashmap) throws SecretException, UnsupportedEncodingException{
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String sessionKey = userInfoService.validateFindSessionKey((String) hashmap.get("userId"));
		List<SmsReceiveInfo> receiveInfos = myBatisDao.findList(SmsReceiveInfo.class.getName(), "findList", hashmap);
		if(receiveInfos != null && !receiveInfos.isEmpty()){
			for (SmsReceiveInfo smsReceiveInfo : receiveInfos) {
				if(EncrptAndDecryptClient.isEncryptData(smsReceiveInfo.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
					smsReceiveInfo.setBuyerNick(decryptClient.decryptData(smsReceiveInfo.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey));
				}
				if(EncrptAndDecryptClient.isEncryptData(smsReceiveInfo.getSendPhone(), EncrptAndDecryptClient.PHONE)){
					smsReceiveInfo.setSendPhone(decryptClient.decryptData(smsReceiveInfo.getSendPhone(), EncrptAndDecryptClient.PHONE, sessionKey));
				}
				if(smsReceiveInfo.getContent() != null && !"".equals(smsReceiveInfo.getContent())){
					smsReceiveInfo.setContent(URLDecoder.decode(smsReceiveInfo.getContent(), "utf-8"));
				}
			}
		}
		return receiveInfos;
	}
	
	/**
	 * 根据卖家条件查询所接收到的短信记录(分页)
	 * zlp年11月24日下午13:18:53
	 * @throws SecretException 
	 * @throws UnsupportedEncodingException 
	 */
	public List<SmsReceiveInfo> findReceiveInfoLimitList(Map<String, Object> hashmap) throws SecretException, UnsupportedEncodingException{
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String sessionKey = userInfoService.validateFindSessionKey((String) hashmap.get("userId"));
		List<SmsReceiveInfo> receiveInfos = myBatisDao.findList(SmsReceiveInfo.class.getName(), "findReceiveInfoLimitList", hashmap);
		if(receiveInfos != null && !receiveInfos.isEmpty()){
			for (SmsReceiveInfo smsReceiveInfo : receiveInfos) {
				try {
					if(EncrptAndDecryptClient.isEncryptData(smsReceiveInfo.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
						smsReceiveInfo.setBuyerNick(decryptClient.decryptData(smsReceiveInfo.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey));
					}
					if(EncrptAndDecryptClient.isEncryptData(smsReceiveInfo.getSendPhone(), EncrptAndDecryptClient.PHONE)){
						smsReceiveInfo.setSendPhone(decryptClient.decryptData(smsReceiveInfo.getSendPhone(), EncrptAndDecryptClient.PHONE, sessionKey));
					}
					if(smsReceiveInfo.getContent() != null && !"".equals(smsReceiveInfo.getContent())){
						smsReceiveInfo.setContent(URLDecoder.decode(smsReceiveInfo.getContent(), "utf-8"));
						if(smsReceiveInfo.getContent().length()>30){
							String substring = smsReceiveInfo.getContent().substring(0,30);
							smsReceiveInfo.setContent(substring+"...");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return receiveInfos;
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		String encode = URLEncoder.encode("","utf-8");
		System.out.println(encode);
		String decode = URLDecoder.decode(encode+"..", "utf-8");
		System.out.println(decode);
	}
	
	/**
	 * 根据卖家查询条件查询总条数
	 */
	private Integer findTotalCountByMap(Map<String, Object> hashmap) {
		Long totalCount = myBatisDao.findBy(SmsReceiveInfo.class.getName(), "findTotalCountByMap", hashmap);
		Integer i = Integer.valueOf(totalCount.toString());
		return i;
	}
	
	/**
	 * 调用service层将会员互动中未读信息标记成已读
	 */
	public void remarkSmsReceiveInfo(Integer[] ids,UserOperationLog userOperationLog) {
		
		Map<String,Integer[]> map = new HashMap<String,Integer[]>();
		map.put("ids", ids);
		
		try {
			//标记未读信息
			myBatisDao.execute(SmsReceiveInfo.class.getName(), "remarkSmsReceiveInfo", map);
			//判断删除操作是否成功或失败
			userOperationLog.setState("成功");
			
		} catch (Exception e) {
			userOperationLog.setState("失败");
		}

		userOperationLog.setType("会员互动记录标记成已读信息");
		userOperationLog.setRemark("会员互动记录标记成已读信息");
		
		//日志操作类型
		userOperationLog.setFunctions("会员短信群发");
	
		userOperationLogDao.save(userOperationLog);
		
	}

	/**
	 * 通过查询获得买家回复内容的list列表
	 */
	public List<SmsReceiveInfo> getSmsReceiveInfoList(Integer[] ids) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		List <SmsReceiveInfo> list = myBatisDao.findList(SmsReceiveInfo.class.getName(), "getSmsReceiveInfoList", map);
		return list;
	}
	
	/**
	 * 添加方法
	 * ZTK2017年5月16日上午10:06:46
	 */
	public void insertReceiveInfo(SmsReceiveInfo smsReceiveInfo){
		myBatisDao.execute(SmsReceiveInfo.class.getName(), "insertSmsReceiverInfo", smsReceiveInfo);
	}
	
	/**
	 * 根据买家昵称查询所接收到的短信记录(不分页)
	 * @Title: listReceiveInfoByBuyerNick 
	 * @param @param hashmap
	 * @param @return
	 * @param @throws SecretException
	 * @param @throws UnsupportedEncodingException 设定文件 
	 * @return List<SmsReceiveVO> 返回类型 
	 * @throws
	 */
	public List<SmsReceiveVO> listReceiveInfoByBuyerNick(Map<String, Object> hashmap){
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		String sessionKey = userInfoService.validateFindSessionKey((String) hashmap.get("userId"));
		String phone = (String) hashmap.get("phone");
		try {
			if(EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
				hashmap.put("phone", phone);
			}else {
				hashmap.put("phone", decryptClient.encryptData(phone, EncrptAndDecryptClient.PHONE, sessionKey));
			}
		} catch (SecretException e) {
			e.printStackTrace();
		}
		List<SmsReceiveInfo> receiveInfos =
				myBatisDao.findList(SmsReceiveInfo.class.getName(), "listReceiveInfoByBuyerNick", hashmap);
		List<SmsReceiveVO> receiveVOs = new ArrayList<>();
		if(receiveInfos != null && !receiveInfos.isEmpty()){
			for (SmsReceiveInfo smsReceiveInfo : receiveInfos) {
				SmsReceiveVO smsReceiveVO = new SmsReceiveVO();
				try {
					smsReceiveVO.setSendTime(smsReceiveInfo.getReceiveDate());
					if(smsReceiveInfo.getContent() != null && !"".equals(smsReceiveInfo.getContent())){
						smsReceiveVO.setContent(URLDecoder.decode(smsReceiveInfo.getContent(), "utf-8"));
					}else {
						smsReceiveVO.setContent("");
					}
					receiveVOs.add(smsReceiveVO);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		return receiveVOs;
	}
}
