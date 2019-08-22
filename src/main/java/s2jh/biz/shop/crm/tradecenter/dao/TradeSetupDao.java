package s2jh.biz.shop.crm.tradecenter.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import s2jh.biz.shop.crm.order.pojo.OrderReminderEffectVo;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;
import s2jh.biz.shop.crm.tradecenter.vo.TradeSetupVO;
@Repository
public interface TradeSetupDao  {

	/**
	* @Title: showSetupMenu
	* @Description: (展示各种订单中心的设置是否开启或者关闭)
	* @return List<TradeSetup>    返回类型
	* @author:jackstraw_yu
	*/
	List<TradeSetup> showSetupMenu(Map<String, Object> map);
	
	
	
	/**
	* @Title: saveTradeSetup
	* @Description: (保存各种订单中心的设置)
	* @return void    返回类型
	* @author:jackstraw_yu
	*/
	void saveTradeSetup(TradeSetup tradeSetup);


	/**
	* @Title: queryTradeSetup
	* @Description: (根据买家昵称和设置类型查询 改类型设置总条数)
	* @return long    返回类型
	* @author:jackstraw_yu
	*/
	long queryTradeSetupCount(Map<String, String> map);


	/**
	* @Title: queryTradeSetupByTaskName
	* @Description: (同类型设置通过名称查询是否存在该设置)
	* @return long    返回类型
	* @author:jackstraw_yu
	*/
	 TradeSetup queryTradeSetupByTaskName(TradeSetupVO vo);

	/**
	* @Title: queryTradeSetupTable
	* @Description: (筛选订单中心相关设置列表)
	* @return List<TradeSetup>    返回类型
	* @author:jackstraw_yu
	*/
	List<TradeSetup> queryTradeSetupTable(TradeSetupVO setVo);

	/**
	* @Title: querySingleTradeSetup
	* @Description: (筛选单个订单中心相关设置)
	* @return  TradeSetup    返回类型
	* @author:jackstraw_yu
	*/
	TradeSetup querySingleTradeSetup(TradeSetupVO setVo);
	
	/**
	 * 订单中心根据条件筛选任务名称列表
	 * ztk2017年9月25日下午4:07:26
	 */
	List<OrderReminderEffectVo> queryTradeSetupTaskNames(OrderReminderEffectVo orderEffectVo);
	
	/**
	 * 订单中心根据任务名称查询任务Id
	 * ztk2017年10月30日下午11:11:26
	 */
	Long queryTradeSetupId(OrderReminderEffectVo orderEffectVo);
	
	/**
	* @Title: deleteTradeSetup
	* @Description: (筛选订单中心相关设置)
	* @return Long    返回类型
	* @author:jackstraw_yu
	*/
	Long deleteTradeSetup(TradeSetupVO setVo);

	/**
	* @Title: switchTradeSetup
	* @Description: (打开或者关闭订单中心相关设置)
	* @return Long    返回类型
	* @author:jackstraw_yu
	*/
	Long switchTradeSetup(TradeSetupVO setVo);

	/**
	* @Title: updateTradeSetup
	* @Description: (修改订单中心相关设置)
	* @return Long    返回类型
	* @author:jackstraw_yu
	*/
	void updateTradeSetup(TradeSetup tradeSetup);

	/**
	* @Title: resetTradeSetupLevel
	* @Description: (修改订单中心设置的任务级别)
	* @return Long    返回类型
	* @author:jackstraw_yu
	*/
	void resetTradeSetupLevel(TradeSetupVO setVo);

	/**
	* @Title: queryMaxTaskLevelByType
	* @Description: (查询当前类型设置的最大任务级别)
	* @return Long    返回类型
	* @author:jackstraw_yu
	*/
	Integer queryMaxTaskLevelByType(TradeSetup tradeSetup);


	/**
	* @Title: queryTradeSetupById
	* @Description: (通过id查询当前设置)
	* @return TradeSetup    返回类型
	* @author:jackstraw_yu
	*/
	TradeSetup queryTradeSetupById(Long id);

	/**
	* @Title: findTypeOpenBySellerNick
	* @Description: (判断该设置是否存在)
	* @return boolean   返回类型
	* @author:jackstraw_yu
	*/
	List<String> findTypeOpenBySellerNick(String userId);
	
	/**
	 * 查询taskName对应的id
	 * ztk2017年11月3日上午10:05:14
	 */
	Long queryIdByTaskName(Map<String, Object> dataMap);
	
	
	
	/**
	* @Title: isExist
	* @Description: (判断该设置是否存在)
	* @return boolean   返回类型
	* @author:jackstraw_yu
	*/
	@Deprecated
	TradeSetup isExist(Map<String, String> map);
	@Deprecated
	void updateTradeSetupSync(TradeSetup tradeSetup);
	@Deprecated
	void deleteErrorData();
	@Deprecated
	long getRightDataCount();
	@Deprecated
	List<TradeSetup> getRightLimitData(Map<String, Object> map);

	/**
	 * 根据用户查询设置的任务(需要效果分析的类型中'2','3','4','6','7','8','9','12','14','37')
	 * ztk2017年11月30日下午4:54:13
	 */
	List<Long> queryTaskByUserNick(Map<String,Object> map);
	
	/**
	 * 查询卖家的自动评价关怀设置是否存在
	 * @param sellerNick
	 * @return
	 */
	Long findAutoRateExists(String sellerNick);
}
