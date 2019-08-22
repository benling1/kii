package s2jh.biz.shop.crm.systemAnnouncement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.entity.SmsSendRecord;
import s2jh.biz.shop.crm.systemAnnouncement.dao.SystemAnnouncementDao;
import s2jh.biz.shop.crm.systemAnnouncement.entity.SystemAnnouncement;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.entity.UserRecharge;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class SystemAnnouncementService extends BaseService<SystemAnnouncement, Long>{
	
	@Autowired
	private SystemAnnouncementDao systemAnnouncementDao;
	
	@Autowired
	private MyBatisDao mybatisDao;
	
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	@Override
	protected BaseDao<SystemAnnouncement, Long> getEntityDao() {
		return systemAnnouncementDao;
	}

	/**
	 * 根据卖家用户编号查询系统公告
	 * @param taobao_user_nick
	 * @return list
	 */
	public List<SystemAnnouncement> findAll(String taobao_user_nick){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taobao_user_nick", taobao_user_nick);		
		List<SystemAnnouncement> list = mybatisDao.findList(SystemAnnouncement.class.getName(), "findAllAnnouncement", map);
		return list;
	}

	/**
	* @Title: queryPagination
	* @Description: TODO(查询出系统公告的分页)
	* @param @param taobao_user_nick
	* @param @param contextPath
	* @param @param pageNo
	* @param @return    参数
	* @return Pagination    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public Pagination queryPagination(String taobao_user_nick,
			String contextPath, Integer pageNo) {
		
		//根据条件查询出总条数
		int totalCount = queryTotalCount(taobao_user_nick);
		//先设置每页显示的条数为5条
		Integer currentRows = 5;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;
		
		HashMap<String, Object> hashMap = new HashMap<String , Object>();
		hashMap.put("taobao_user_nick",taobao_user_nick);
		hashMap.put("currentRows",currentRows);
		hashMap.put("startRows",startRows);
		
		//查询冲当前页的充值记录列表
		List<SystemAnnouncement> systemAnnouncements = mybatisDao.findLimitList(SystemAnnouncement.class.getName(), "queryPagination", hashMap,currentRows);
		
		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, systemAnnouncements);
		
		StringBuilder params = new StringBuilder();
		if(taobao_user_nick != null){
			params.append("&taobao_user_nick=").append(taobao_user_nick);
		}
		//分页视图
		pagination.pageView(contextPath, params.toString());
		
		
		
		
		return pagination;
	}
	
	/**
	* @Title: queryCount
	* @Description: TODO(查询出系统公告的总提条数)
	* @param @param taobao_user_nick
	* @param @return    参数
	* @return Integer    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public Integer queryTotalCount(String taobao_user_nick){
		
		HashMap<String, Object> hashMap = new HashMap<String , Object>();
		hashMap.put("taobao_user_nick",taobao_user_nick);
		Long l = mybatisDao.findBy(SystemAnnouncement.class.getName(), "queryTotalCount", hashMap);
		
		return Integer.valueOf(l.toString());
	}

	
	/**
	* @Title: remarkOne
	* @Description: TODO(单条公告标记为已读)
	* @param @param id
	* @param @param taobao_user_nick
	* @param @param userOperationLog    参数
	* @return void    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public void remarkOne(Integer id, String taobao_user_nick,
			UserOperationLog userOperationLog) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("taobao_user_nick", taobao_user_nick);
		
		try {
			//调用SmsSendRecord-- 删除短信发送记录
			mybatisDao.execute(SystemAnnouncement.class.getName(), "remarkOne", map);
			//判断删除操作是否成功或失败
			userOperationLog.setState("成功");
		} catch (Exception e) {
			userOperationLog.setState("失败");
		}

		userOperationLog.setType("修改");
		
		//判断日志操作类型
		userOperationLog.setFunctions("公告标记为已读");
		//添加操作日志
		userOperationLogDao.save(userOperationLog);
	}
	
	
	public void remarkAll(Integer[] ids, String taobao_user_nick,
			UserOperationLog userOperationLog) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("taobao_user_nick", taobao_user_nick);
		
		try {
			//调用SmsSendRecord-- 删除短信发送记录
			mybatisDao.execute(SystemAnnouncement.class.getName(), "remarkAll", map);
			//判断删除操作是否成功或失败
			userOperationLog.setState("成功");
		} catch (Exception e) {
			userOperationLog.setState("失败");
		}
		
		userOperationLog.setType("修改");
		
		//判断日志操作类型
		userOperationLog.setFunctions("公告标记为已读");
		//添加操作日志
		userOperationLogDao.save(userOperationLog);
	}
}
