package s2jh.biz.shop.crm.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.user.dao.OrderApplicationInfoDao;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.OrderApplicationInfo;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.SpringContextUtil;

@Service
@Transactional
public class OrderApplicationInfoService extends
		BaseService<OrderApplicationInfo, Long> {

	@Autowired
	private OrderApplicationInfoDao orderApplicationInfoDao;

	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<OrderApplicationInfo, Long> getEntityDao() {
		return orderApplicationInfoDao;
	}

	/**
	 * @Title: updateOrderApplicationInfo
	 * @Description: TODO(修改或添加用户的订购)
	 * @param @param orderApplicationInfo 参数
	 * @return void 返回类型
	 * @author:jackstraw_yu
	 * @throws
	 */
	public void updateOrderApplicationInfo(
			OrderApplicationInfo orderApplicationInfo) {

		OrderApplicationInfoDao orderApplicationInfoDao = SpringContextUtil
				.getBean("orderApplicationInfoDao");
		MyBatisDao myBatisDao = SpringContextUtil.getBean("myBatisDao");
		UserOperationLogDao userOperationLogDao = SpringContextUtil
				.getBean("userOperationLogDao");

		// 判断数据库是否存在该用户
		// 通过用户的淘宝昵称:userNick,判断用户是否存在
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNick", orderApplicationInfo.getUserNick());
		OrderApplicationInfo orderApp = myBatisDao.findBy(
				OrderApplicationInfo.class.getName(),
				"queryOrderApplicationInfo", map);

		// 修改的数据
		map.put("subId", orderApplicationInfo.getSubId());// 订购记录id
		map.put("articleCode", orderApplicationInfo.getArticleCode());// 应用收费代码
		map.put("itemName", orderApplicationInfo.getItemName());// 收费项目名称
		map.put("itemCode", orderApplicationInfo.getItemCode());// 收费项目代码
		map.put("expireNotice", orderApplicationInfo.isExpireNotice());// 是否到期提醒
		map.put("endDate", orderApplicationInfo.getEndDate());// 订购关系到期时间
		map.put("effectStatus", orderApplicationInfo.getEffectStatus());// 状态(1=有效
																		// 2=过期)
		map.put("autosub", orderApplicationInfo.isAutosub());// 是否自动续费
		map.put("articleName", orderApplicationInfo.getArticleName());// 应用名称

		// 添加操作日志(使用一个对象时第一条数据会被覆盖)
		UserOperationLog userOperationLog = new UserOperationLog();
		userOperationLog.setUserId("系统管理员");
		userOperationLog.setCreatedDate(new Date());
		userOperationLog.setDate(new Date());

		if (orderApp != null) {
			try {
				// 1存在==>>修改到期时间
				myBatisDao.execute(OrderApplicationInfo.class.getName(),
						"updateOrderApplicationInfo", map);
				// 1.1添加日志
				userOperationLog.setType("修改");
				userOperationLog.setRemark("添加用户订购服务信息/到期时间");
				userOperationLog.setFunctions("添加用户订购服务信息/到期时间");
				userOperationLog.setState("成功");
			} catch (Exception e) {
				userOperationLog.setState("失败");
			}
			// 1.1添加修改日志
		} else {
			try {
				// 2不存在==>>添加一条新用户的数据
				orderApplicationInfoDao.save(orderApplicationInfo);
				// 2.1添加添加用户的的日志
				userOperationLog.setType("添加");
				userOperationLog.setRemark("添加用户订购服务信息");
				userOperationLog.setFunctions("添加用户订购服务信息");
				userOperationLog.setState("成功");
			} catch (Exception e) {
				userOperationLog.setState("失败");
			}
			// 2.1添加添加用户的的日志
		}
		userOperationLogDao.save(userOperationLog);

	}

}
