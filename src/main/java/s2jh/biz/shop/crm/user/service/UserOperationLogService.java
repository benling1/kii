package s2jh.biz.shop.crm.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class UserOperationLogService extends
		BaseService<UserOperationLog, Long> {

	@Autowired
	private UserOperationLogDao userOperationLogDao;

	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<UserOperationLog, Long> getEntityDao() {
		return userOperationLogDao;
	}

	// 分页 查询总数
	public Integer findTotalCountRz(String bTime, String eTime,String userId,String functionGens) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("functionGens", functionGens);
		map.put("userId", userId);
		Long totalCount = myBatisDao.findBy(
				"s2jh.biz.shop.crm.user.entity.UserOperationLog",
				"findTotalCountRz", map);
		Integer i = Integer.valueOf(totalCount.toString());
		return i;

	}

	// 条件分页查询分页
	public Pagination findoperationLog(String contextPath, String functionGens,
			String bTime, String eTime, Integer pageNo,String userId) {
		if (pageNo == null) {
			pageNo = 1;
		}
		// 先设置每页显示的条数为3条
		Integer currentRows = 15;
		// 计算出起始行数
		Integer startRows = (pageNo - 1) * currentRows;
		// 计算出总页数
		Integer totalCount = findTotalCountRz(bTime, eTime,userId,functionGens);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("functionGens", functionGens);
		List<UserOperationLog> userOperationLog = myBatisDao.findLimitList(
				"s2jh.biz.shop.crm.user.entity.UserOperationLog",
				"findoperationLog", map, currentRows);
		Pagination pagination = new Pagination(pageNo, currentRows, totalCount,
				userOperationLog);
		StringBuilder params = new StringBuilder();
		if (bTime != null) {
			params.append("&beginTime=").append(bTime.toString());
		}
		if (eTime != null) {
			params.append("&endTime=").append(eTime.toString());

		}
		if(functionGens!=null){
			params.append("&functionGens=").append(functionGens);
		}
		if(userId != null){
			params.append("&userId=").append(userId);
		}
		String url = contextPath + "/crms/storeData/operationLog";
		pagination.pageView(url, params.toString());
		return pagination;

	}

}
