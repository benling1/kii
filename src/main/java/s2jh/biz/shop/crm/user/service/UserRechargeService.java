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

import s2jh.biz.shop.crm.user.dao.UserRechargeDao;
import s2jh.biz.shop.crm.user.entity.UserRecharge;
import s2jh.biz.shop.utils.SpringContextUtil;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class UserRechargeService extends BaseService<UserRecharge, Long> {

	@Autowired
	private UserRechargeDao userRechargeDao;

	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<UserRecharge, Long> getEntityDao() {
		return userRechargeDao;
	}

	/**
	 * 根据订单编号查询充值信息
	 * 
	 * @param map
	 * @return
	 */
	public Long getUserRechar(Map<String, Object> map) {
		long l = ((MyBatisDao) SpringContextUtil.getBean("myBatisDao")).findBy(
				UserRecharge.class.getName(), "queryUserRechargeCount", map);
		return l;
	}

	/**
	 * 根据订单编号查询充值详细信息
	 * 
	 * @param map
	 * @return UserRecharge
	 */
	public UserRecharge getUserRechargeInfo(Map<String, Object> map) {
		MyBatisDao mybatisDao = SpringContextUtil.getBean("myBatisDao");
		UserRecharge ur = mybatisDao.findBy(UserRecharge.class.getName(),
				"queryUserRecharge", map);
		return ur;
	}
	
	/** 
	* @Description: 保存一条充值记录,充值菜单列表 
	* @param  userRecharge  充值文件 
	* @return void    返回类型 
	* @author jackstraw_yu
	* @date 2017年12月5日 上午9:45:02
	*/
	public void saveUserRechargeRecord(UserRecharge userRecharge) {
		myBatisDao.execute(UserRecharge.class.getName(),
					"saveUserRechargeRecord", userRecharge);
	}
	
	

	/**
	* @Title: queryPagination
	* @Description: (查询出充值记录的分页)
	* @param  taobao_user_nick
	* @param  contextPath
	* @return Pagination    返回类型
	* @author:jackstraw_yu
	*/
	public Pagination queryPagination(String taobao_user_nick,String contextPath,Integer pageNo) {
		//根据条件查询出总条数
		int totalCount = getTotalCount(taobao_user_nick);
		//先设置每页显示的条数为5条
		Integer currentRows = 8;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;
		HashMap<String, Object> hashMap = new HashMap<String , Object>();
		hashMap.put("taobao_user_nick",taobao_user_nick);
		hashMap.put("currentRows",currentRows);
		hashMap.put("startRows",startRows);
		//查询冲当前页的充值记录列表
		List<UserRecharge> userRechargeList = myBatisDao.findLimitList(UserRecharge.class.getName(), "queryPagination", hashMap,currentRows);
		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, userRechargeList);
		StringBuilder params = new StringBuilder();
		if(taobao_user_nick != null){
			params.append("&taobao_user_nick=").append(taobao_user_nick);
		}
		//分页视图
		pagination.pageView(contextPath, params.toString());
		return pagination;
	}
	
	/**
	* @Title: getCount
	* @Description: 通过用户昵称  查询出总条数
	* @param  taobao_user_nick
	* @return Integer    返回类型
	* @author:jackstraw_yu
	*/
	private Integer getTotalCount(String taobao_user_nick){
		HashMap<String, Object> hashMap = new HashMap<String , Object>();
		hashMap.put("taobao_user_nick",taobao_user_nick);
		Long count = myBatisDao.findBy(UserRecharge.class.getName(), "getTotalCount", hashMap);
		return count==null?0:Integer.valueOf(count.toString());
	}

	
	
	
	
	
	
	/** @param ur====================================HL===================== */
	/**
	 * 保存充值记录
	 * @param ur
	 */
	public void insertUserRechargeRecord(UserRecharge ur) {
		//保存数据到数据库
		int i = myBatisDao.execute(UserRecharge.class.getName(), "insertUserRechargeRecord", ur);
		if(i==0){
			throw new RuntimeException("insert UserRecharge error");
		}
	}

	/**
	 * 通过充值订单id查询充值记录
	 * @param out_trade_no
	 * @return
	 */
	public UserRecharge findUserRechargeByOrderId(String out_trade_no) {
		try {
			UserRecharge ur = myBatisDao.findBy(UserRecharge.class.getName(), "findUserRechargeByOrderId", out_trade_no);
			return ur;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 更改充值记录状态
	 * @param userRecharge
	 */
	public void updateUserRechargeStatus(UserRecharge userRecharge) {
		userRecharge.setLastModifiedDate(new Date());
		//保存数据到数据库
		myBatisDao.execute(UserRecharge.class.getName(), "updateUserRechargeStatus", userRecharge);
	}

	/**
	 * 查询充值记录状态
	 * @param payTrade
	 * @return
	 */
	public String findPayStatus(String payTrade) {
		try {
			UserRecharge ur = myBatisDao.findBy(UserRecharge.class.getName(), "findUserRechargeByOrderId", payTrade);
			return ur.getStatus();
		} catch (Exception e) {
			return null;
		}
	}

	
	/**  @param ur====================================HL===================== */
}
