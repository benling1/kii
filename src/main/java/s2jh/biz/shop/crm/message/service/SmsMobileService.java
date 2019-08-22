package s2jh.biz.shop.crm.message.service;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.message.dao.SmsMobileDao;
import s2jh.biz.shop.crm.message.entity.SmsMobile;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class SmsMobileService extends BaseService<SmsMobile, Long>{
	private static Map<String,List<String>> userBlackMap =new HashMap<String,List<String>>();
	private static Map<String,Long> lastModifyTimeMap=new HashMap<String,Long>();
	
	public List<String> getUserBlackList(String uid){
		long current=System.currentTimeMillis();
		if(lastModifyTimeMap.containsKey(uid)){
			long lastTime=lastModifyTimeMap.get(uid);
			
			if(current-lastTime>60*60*1000){
				//chongxinjiazai
				synchronized (uid) {
					lastModifyTimeMap.put(uid, current);
					userBlackMap.put(uid, new ArrayList());
				}
			}
		}else{
			//chongxinjiazai
			synchronized (uid) {
				lastModifyTimeMap.put(uid, current);
				userBlackMap.put(uid, new ArrayList());
			}
		}
		
		return userBlackMap.get(uid);
	}
	
	@Autowired
	private SmsMobileDao smsMobileDao;
	
	@Autowired
	private UserOperationLogDao  userOperationLogDao;
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<SmsMobile, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return smsMobileDao;
	}
	
	@Transactional
	public void saveSmsMobile(SmsMobile  smsMobile){
		
		smsMobileDao.save(smsMobile);
	}
	
	//更改删除状态
	public int updateisDelete(Integer id){
		Map<String,Object> parameters = new HashMap<String, Object>();
		//String userId = (String) request.getSession().getAttribute("taobao_user_nick");
        //String userId= "crazyboy";
		parameters.put("id", id);
		//parameters.put("userId", userId);
		return myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsMobile", "updateisDelete", parameters);
	}
	
	
	//多条数据添加
	@Transactional
	public void addSmsMobile(SmsMobile smsMobile,HttpServletRequest request,String userId){
		String[] mobiles= smsMobile.getMobile().split(",");
		for(String mobile:mobiles){
			SmsMobile smsMobile2 = new SmsMobile();
			smsMobile2.setCtime(new Date());
			smsMobile2.setUserId(userId);
			smsMobile2.setIsDelete("1");
			smsMobile2.setType("1");
			smsMobile2.setMobile(mobile);
			smsMobile2.setRemark(smsMobile.getRemark());
			smsMobileDao.save(smsMobile2);
		}
		
	    //用户操作日志
		UserOperationLog op = new UserOperationLog();
		op.setUserId(userId);
		op.setFunctions("手机黑名单添加");
		op.setType("添加");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setDate(new Date());
		op.setState("成功");
		op.setRemark("客户管理，手机黑名单添加");
		op.setFunctionGens("33");
		userOperationLogDao.save(op);
	}
	
	//分页查询总条数
	public Integer findSmsMolieCount(String userId,String bTime,String eTime,String remark,String mobile){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("remark",remark);
		map.put("mobile", mobile);
		Long totalCount = myBatisDao.findBy("s2jh.biz.shop.crm.message.entity.SmsMobile", "findSmsMolieCount", map);
		Integer i = Integer.valueOf(totalCount.toString());
		return i;
	}
	
	//分页条件查询
	public Pagination findSmsMobileBlack(String userId,String contextPath,String bTime, String eTime,Integer pageNo,String remark,String mobile){
		if(pageNo==null){
			pageNo = 1;
		}
		//先设置每页显示的条数为3条
		Integer currentRows = 6;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;
		//计算出总页数
		Integer totalCount = findSmsMolieCount(userId,bTime,eTime,remark,mobile);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("remark", remark);
		map.put("mobile", mobile);
		List<SmsMobile> smsMobileBlack = myBatisDao.findLimitList("s2jh.biz.shop.crm.message.entity.SmsMobile", "findSmsMobileBlack", map, currentRows);
		Pagination pagination = new Pagination(pageNo,currentRows,totalCount,smsMobileBlack);
		StringBuilder params = new StringBuilder();
		if(bTime!=null){
			params.append("&beginTime=").append(bTime.toString());
		}
		if(eTime!=null){
			params.append("&endTime=").append(eTime.toString());
			
		}
		if(remark!=null){
			params.append("&remark=").append(remark);
		}
		if(mobile!=null){
			params.append("&mobile=").append(mobile);
		}
		String url =contextPath+"/crms/customerManagement/blacklistManagemen";
		pagination.pageView(url, params.toString());
		return pagination;
	}
	
	
	//更改手机号状态 ---营销中心
	public int updateSmsMobile(Integer blicklistId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("blicklistId", blicklistId);
		return myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsMobile", "updateMobile", map);
	}

	/**
	 *通过userid和电话号码查询黑名单
	 * helei 2017年2月14日下午2:31:13
	 */
	public List<SmsMobile> findSmsBlacklist(String userId,List<String> phone){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("phone", phone);
		List<SmsMobile> list = myBatisDao.findList(SmsMobile.class.getName(), "findBlackList", map);	
		return list;
	}
	
	/**
	 * 通过UerId和买家昵称查询黑名单
	 * ZTK2017年3月21日下午2:26:44
	 */
	public List<SmsMobile> findNickBlacklist(String userId,List<String> nickList){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("nickList", nickList);
		List<SmsMobile> list = myBatisDao.findList(SmsMobile.class.getName(), "findNickBlackList", map);	
		return list;
	}

	/**
	* @Title: queryBlackPhoneNumList
	* @Description: TODO(查询电话黑名单的手机号集合)
	* @param   userId
	* @param      参数
	* @return List<String>    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public List<String> queryBlackPhoneNumList(String userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<String> list = myBatisDao.findList(SmsMobile.class.getName(), "queryBlackPhoneNumList", map);
		return list;
	}

	/**
	 * 根据卖家查询黑名单昵称的集合
	 * ZTK2017年5月4日下午1:50:56
	 */
	public List<String> findBlackNickList(String userId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId",userId);
		List<String> blackNickList = myBatisDao.findList(SmsMobile.class.getName(), "findBlackNickStr", map);
		return blackNickList;
	}

	/**
	 * 查询短信黑名单返回list
	 * @author Administrator_HL
	 * @data 2017年5月20日 下午2:49:05
	 */
	public List<String> querySmsBlacklistNumList(String userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<String> list = myBatisDao.findList(SmsMobile.class.getName(), "querySmsBlacklistNumList", map);
		return list;
	}
	
}
