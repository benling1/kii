package s2jh.biz.shop.crm.tradecenter.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import s2jh.biz.shop.crm.order.pojo.OrderReminderEffectVo;
import s2jh.biz.shop.crm.tradecenter.entity.TradeCenterEffect;

@Repository
public interface TradeCenterEffectDao {

	/**
	 * 新增一条记录
	 * ztk2017年11月24日下午1:58:00
	 */
	void saveTradeCenterEffect(TradeCenterEffect tradeCenterEffect);
	
	/**
	 * 更新一条记录
	 * ztk2017年11月24日下午1:58:22
	 */
	void updateTradeCenterEffect(TradeCenterEffect tradeCenterEffect);
	
	/**
	 * 根据条件查询一条记录
	 * ztk2017年11月24日下午1:59:02
	 */
	TradeCenterEffect queryTradeEffect(TradeCenterEffect tradeCenterEffect);
	
	/**
	 * 查询多条记录
	 * ztk2017年11月24日下午2:00:45
	 */
	List<TradeCenterEffect> queryTradeEffectList(TradeCenterEffect tradeCenterEffect);
	
	/**
	 * 订单中心效果分析聚合查询(按照日期正序排列)
	 * ztk2017年12月1日下午4:43:12
	 */
	List<TradeCenterEffect> aggregateTradeCenterList(OrderReminderEffectVo orderReminderEffectVo);
	
	/**
	 * 订单中心效果分析聚合查询(一天的记录)
	 * ztk2017年12月1日下午4:43:12
	 */
	TradeCenterEffect aggregateTradeCenterEffect(OrderReminderEffectVo orderReminderEffectVo);
	
	/**
	 * 首页催付金额聚合查询
	 * ztk2017年12月6日下午12:15:16
	 */
	TradeCenterEffect aggregateEarningFee(OrderReminderEffectVo orderReminderEffectVo);
}
