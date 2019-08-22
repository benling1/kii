package s2jh.biz.shop.crm.tradecenter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.order.pojo.OrderReminderEffectVo;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.tradecenter.dao.TradeSetupDao;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;
import s2jh.biz.shop.crm.tradecenter.vo.TradeSetupVO;

@Service
//@Transactional
public class TradeSetupService /*extends BaseService<TradeSetup, Long>*/  {

	@Autowired
	private TradeSetupDao tradeSetupDao;
	
	@Autowired
	private CacheService cacheService;
	
	 /**
		 * 数据集合
		 * TradeSetupService实例化时创建
		 * 订单发送范围校验
		 * */
    private final List<String> BLOCK_LIST = new ArrayList<String>(){   
		private static final long serialVersionUID = -237244155080108106L;
		{  
			//"付款关怀", "发货提醒"
			add(OrderSettingInfo.PAYMENT_CINCERN);add(OrderSettingInfo.SHIPMENT_TO_REMIND);
			// "延时发货提醒", "到达同城提醒"
			add(OrderSettingInfo.DELAY_SEND_REMIND); add(OrderSettingInfo.ARRIVAL_LOCAL_REMIND);
			//"派件提醒", "签收提醒"
			add(OrderSettingInfo.SEND_GOODS_REMIND);add( OrderSettingInfo.REMIND_SIGNFOR);
			//"宝贝关怀", "回款提醒"
			add(OrderSettingInfo.COWRY_CARE);add(OrderSettingInfo.RETURNED_PAYEMNT); 
			//"买家申请退款", "同意退款"
			add(OrderSettingInfo.REFUND_CREATED);add(OrderSettingInfo.REFUND_AGREE);
			//"拒绝退款", "退款成功"
			add(OrderSettingInfo.REFUND_REFUSE); add(OrderSettingInfo.REFUND_SUCCESS);
			//"提醒好评"
			add(OrderSettingInfo.GOOD_VALUTION_REMIND);
        }  
    };

	/*@Override
	protected BaseDao<TradeSetup, Long> getEntityDao() {
		return null;
	}*/
	/**
	 * 查询开启的类型   动态监听tmc
	 * @author: wy
	 * @time: 2017年9月20日 下午3:25:49
	 * @param sellerNick 卖家昵称
	 * @return 开启的设置类型集合
	 */
	public List<String> findTypeBySellerNickTmc(String sellerNick){
		if(ValidateUtil.isEmpty(sellerNick)){
			return null;
		}
		List<String> list = tradeSetupDao.findTypeOpenBySellerNick(sellerNick);
		return list;
	}
	/**
	 * 判断是否有自动评价关怀设置
	 * @param sellerNick
	 * @return true 存在 ，false 不存在
	 */
	public boolean findAutoRateExists(String sellerNick){
		long l = this.tradeSetupDao.findAutoRateExists(sellerNick);
		if(l>0){
			return true;
		}
		return false;
	}
	/**
	* @Title: showSetupMenu
	* @Description: (展示各种订单中心的设置是否开启或者关闭)
	* @return Map<String, Boolean>    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public Map<String, Boolean> showSetupMenu(String userId, Set<String> set) {
		/**true:全部开启;false:为设置或者未开启
		 * dao:返回值示例
		 * type		status
		 *  1		 1
		 *  1		 0
		 *  2		 1
		 *  2		 1		
		 * **/
		/*Map<String, String> hashMap = new HashMap<String,String>();
		hashMap.put("userId", userId);
		List<TradeSetup> setups = tradeSetupDao.showSetupMenu(hashMap);
		Map<String, Boolean>  map = null;
		if(setups!=null && !setups.isEmpty()){
			//1:获得已做的设置是否全部开启或者关闭
			map = new HashMap<String, Boolean>();
			for (TradeSetup tradeSetup : setups) {
				if(map.containsKey(tradeSetup.getType())){
					if(!tradeSetup.getStatus())
						map.put(tradeSetup.getType(), tradeSetup.getStatus());
				}else{
					map.put(tradeSetup.getType(), tradeSetup.getStatus());
				}
			}
		}
		//2:获取订单中心所有设置类型与已设置的差集
		if(map!=null && !map.isEmpty())
			set.removeAll(map.keySet());
		if(set.size()>0){
			if(map ==null || map.isEmpty()) map = new HashMap<String, Boolean>();
			for (String str : set) {
				map.put(str, false);
			}
		}*/
		Map<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("userId", userId);
		//次数参数写死,值参数status为true的
		hashMap.put("status", true);
		//只返回设置为true的实体,通过类型分组
		List<TradeSetup> setups = tradeSetupDao.showSetupMenu(hashMap);
		Map<String, Boolean>  map = null;
		if(setups!=null && !setups.isEmpty()){
			map = new HashMap<String, Boolean>();
			for (TradeSetup tradeSetup : setups) {
				map.put(tradeSetup.getType(), tradeSetup.getStatus());
			}
		}
		if(map!=null && !map.isEmpty())
			set.removeAll(map.keySet());
		if(set.size()>0){
			if(map ==null || map.isEmpty()) map = new HashMap<String, Boolean>();
			for (String str : set) {
				map.put(str, false);
			}
		}
		return map;
	}

	/**
	* @Title: showSingleTypeMenu
	* @Description: (展示单个类型的订单中心的设置是否开启或者关闭)
	* @return Boolean    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public Boolean showSingleTypeMenu(TradeSetupVO vo) {
		/**
		 * 该类型设置不存在或者存在中有一条未开启返回false
		 * 全部存在且开启返回true
		 * **/
		Map<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("userId", vo.getUserId());
		hashMap.put("type", vo.getType());
		List<TradeSetup> setups = tradeSetupDao.showSetupMenu(hashMap);
		if(setups !=null && !setups.isEmpty()){
			for (TradeSetup tradeSetup : setups) {
				if(!tradeSetup.getStatus())
					return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 保存之前:
	 * 查询当前改用设置的最大任务级别:a
	 * 对当前任务设置任务级别:a+1
	 * */
	public Integer queryMaxTaskLevelByType(TradeSetup tradeSetup){
		return tradeSetupDao.queryMaxTaskLevelByType(tradeSetup);
	}

	/**
	* @Title: saveTradeSetup
	* @Description: (保存各种订单中心的设置)
	* @return Long    返回类型
	* @author:jackstraw_yu
	*/
	@Transactional
	public TradeSetup saveTradeSetup(TradeSetup tradeSetup) {
		//添加功能:订单发送短信范围
		//保存数据(insert)时判断 判断是否开始  添加一个开启时间
		if(BLOCK_LIST.contains(tradeSetup.getType()) && tradeSetup.getTradeBlock().equals(true))
			tradeSetup.setChosenTime(new Date());
		tradeSetupDao.saveTradeSetup(tradeSetup);
		if(tradeSetup.getId()==null){
			throw new RuntimeException("tradeCenter:can not return primary key after saving a tradeSetup !");
		}
		//TODO
		//设置时开启时,放入redis
		if(tradeSetup.getStatus())
			putDataToRedis(tradeSetup);
		return tradeSetup;
	}

	/**
	* @Title: queryTradeSetup
	* @Description: (根据买家昵称和设置类型查询 该类型设置总条数)
	* @return long    返回类型
	* @author:jackstraw_yu
	*/
	public long queryTradeSetupCount(String userId ,String type){
		Map<String, String> map = new HashMap<String,String>();
		map.put("userId", userId);
		map.put("type", type);
		return tradeSetupDao.queryTradeSetupCount(map);
	}

	/**
	* @Title: queryTradeSetupByTaskName
	* @Description: (同类型设置通过名称查询是否存在该设置)
	* @return long    返回类型
	* @author:jackstraw_yu
	*/
	public TradeSetup queryTradeSetupByTaskName(TradeSetupVO vo) {
		return tradeSetupDao.queryTradeSetupByTaskName(vo);
	}

	/**
	* @Title: queryTradeSetupTable
	* @Description: (筛选多任务订单中心相关设置集合)
	* @return List<TradeSetup>   返回类型
	* @author:jackstraw_yu
	*/
	public List<TradeSetup> queryTradeSetupTable(TradeSetupVO setVo) {
		return tradeSetupDao.queryTradeSetupTable(setVo);
	}

	/**
	* @Title: querySingleTradeSetup
	* @Description: (查询单条订单中心相关设置)
	* @return List<TradeSetup>   返回类型
	* @author:jackstraw_yu
	*/
	public TradeSetup querySingleTradeSetup(TradeSetupVO setup) {
		return tradeSetupDao.querySingleTradeSetup(setup);
	}
	
	/**
	 * 订单中心根据条件筛选任务名称列表
	 * ztk2017年9月25日下午4:08:36
	 */
	public List<OrderReminderEffectVo> queryTradeSetupTaskNames(OrderReminderEffectVo orderEffectVo){
		return tradeSetupDao.queryTradeSetupTaskNames(orderEffectVo);
	}
	
	/**
	 * 订单中心根据任务名称查询任务Id
	 * ztk2017年10月30日下午11:11:26
	 */
	public Long queryTradeSetupId(OrderReminderEffectVo orderEffectVo){
		return tradeSetupDao.queryTradeSetupId(orderEffectVo);
	}
	
	/**
	* @Title: showSingleTradeSetup
	* @Description: (筛选单个订单中心相关设置;用于数据回填)
	* @return TradeSetup   返回类型
	* @author:jackstraw_yu
	*/
	public TradeSetup showSingleTradeSetup(TradeSetupVO setVo) {
		return tradeSetupDao.querySingleTradeSetup(setVo);
	}
	
	/**
	* @Title: deleteTradeSetup
	* @Description: (删除订单中心相关设置)
	* @return void   返回类型
	* @author:jackstraw_yu
	 *@throws Exception 
	*/
	@Transactional
	public void deleteTradeSetup(TradeSetupVO setVo) throws Exception {
		tradeSetupDao.deleteTradeSetup(setVo);
		/*if(delete!=1){
			throw new Exception("tradeCenter:delete tradeSetup more than one result!");
		}*/
		//删除该设置时,从redis取出
		//TODO
		removeDataFromRedis(setVo);
	}


	/**
	* @Title: switchTradeSetup
	* @Description: (打开或者关闭订单中心相关设置)
	* @return void   返回类型
	* @author:jackstraw_yu
	*/
	@Transactional
	public void switchTradeSetup(TradeSetupVO voSetup) {
		tradeSetupDao.switchTradeSetup(voSetup);
		//TODO
		//打开设置,放入redis/关闭设置,重redis取出
		//打开
		if(voSetup.getStatus()){
			TradeSetup setup = tradeSetupDao.querySingleTradeSetup(voSetup);
			putDataToRedis(setup);
		}else{//关闭
			removeDataFromRedis(voSetup);
		}
	}


	/**
	* @Title: updateTradeSetup
	* @Description: (修改订单中心相关设置)
	* @return void   返回类型
	* @author:jackstraw_yu
	 * @throws Exception 
	*/
	@Transactional
	public void updateTradeSetup(TradeSetup tradeSetup) throws Exception {
		TradeSetup setup = null;
		//更新之前判断需不需要对**/选中时间进行赋值
		if(BLOCK_LIST.contains(tradeSetup.getType()) && tradeSetup.getTradeBlock().equals(true)){
			//更新数据为真时先查询出就数据的状态是否为真
			//为真时对chonsenTime赋值为null,由sql判空更新;为假时,重新覆盖chonsenTime
			setup = tradeSetupDao.queryTradeSetupById(tradeSetup.getId());
			if(setup!=null){
				if(setup.getTradeBlock() != null && !setup.getTradeBlock()){
					tradeSetup.setChosenTime(new Date());
				}else{
					tradeSetup.setChosenTime(null);
				}
			}else{
				throw new RuntimeException("tradeCenter:updateTradeSetup Exception!");
			}
		}
		tradeSetupDao.updateTradeSetup(tradeSetup);
		//TODO
		//修改设置后判断是否关闭,开启:重新覆盖redis中设置,关闭:从redis取出
		//打开
		setup = tradeSetupDao.queryTradeSetupById(tradeSetup.getId());
		if(setup!=null){
			if(setup.getStatus()){
				putDataToRedis(setup);
			}else{//关闭
				TradeSetupVO vo = new TradeSetupVO();
				vo.setId(setup.getId());
				vo.setUserId(setup.getUserId());
				vo.setType(setup.getType());
				removeDataFromRedis(vo);
			}
		}else{
			throw new RuntimeException("tradeCenter:updateTradeSetup Exception!");
		}
	}


	/**
	* @Title: resetTradeSetupLevel
	* @Description: (修改订单中心设置的任务级别)
	* @return void   返回类型
	* @author:jackstraw_yu
	*/
	@Transactional
	public void resetTradeSetupLevel(TradeSetupVO setVo) {
		tradeSetupDao.resetTradeSetupLevel(setVo);
		/**
		 * 修改任务级别需要判断该任务是否是开启或者关闭
		 * 开启或者关闭的参数必须有web传入
		 * */
		TradeSetup tradeSetup = tradeSetupDao.querySingleTradeSetup(setVo);
		if(tradeSetup!=null && tradeSetup.getStatus())
			putDataToRedis(tradeSetup);
	}

	/**
	* @Title: putDataToRedis
	* @Description: 将设置放入redis
	* @return void   返回类型
	* @author:jackstraw_yu
	*/
	private void putDataToRedis(TradeSetup tradeSetup){
		cacheService.putNoTime(OrderSettingInfo.TRADE_SETUP+tradeSetup.getUserId()+"_"+tradeSetup.getType(),  
				tradeSetup.getId().toString(),tradeSetup);
	}
	
	/**
	* @Title: removeDataFromRedis
	* @Description: 将设置移出redis
	* @return void   返回类型
	* @author:jackstraw_yu
	*/
	private void removeDataFromRedis(TradeSetupVO tradeSetup){
		cacheService.remove(OrderSettingInfo.TRADE_SETUP+tradeSetup.getUserId()+"_"+tradeSetup.getType(),  
						tradeSetup.getId().toString());
	}
		
	/**
	 * 查询taskName对应的id
	 * ztk2017年11月3日上午10:06:30
	 */
	public Long queryIdByTaskName(String userId,String type,String taskName){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		if(userId == null || type == null || taskName == null){
			return null;
		}
		dataMap.put("userId", userId);
		dataMap.put("type", type);
		dataMap.put("taskName", taskName);
		Long taskId = tradeSetupDao.queryIdByTaskName(dataMap);
		return taskId;
	}	
		
	/**
	* @Title: isExist
	* @Description: (判断该设置是否存在)
	* @return boolean   返回类型
	* @author:jackstraw_yu
	*/
	@Deprecated
	//@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Boolean isExist(String userId,String type) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("userId", userId);
		map.put("type", type);
		TradeSetup tradeSetup = tradeSetupDao.isExist(map);
		return tradeSetup==null?false:true;
	}
	
	/**
	* @Title: isExist
	* @Description: (判断该设置是否存在)
	* @return boolean   返回类型
	* @author:jackstraw_yu
	*/
	@Deprecated
	//@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void updateTradeSetupSync(TradeSetup tradeSetup) {
		tradeSetupDao.updateTradeSetupSync(tradeSetup);
	}
	
	@Deprecated
	//@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void saveTradeSetupSync(TradeSetup tradeSetup) {
		tradeSetupDao.saveTradeSetup(tradeSetup);
	}
	@Deprecated
	public void deleteErrorData() {
		tradeSetupDao.deleteErrorData();
	}
	@Deprecated
	public long getRightDataCount() {
		return tradeSetupDao.getRightDataCount();
	}
	@Deprecated
	public List<TradeSetup> getRightLimitData(Map<String, Object> map) {
		return tradeSetupDao.getRightLimitData(map);
	}
	
	/**
	 * 根据用户查询设置的任务(需要效果分析的类型中'2','3','4','6','7','8','9','12','14','37')
	 * ztk2017年11月30日下午4:55:44
	 */
	public List<Long> queryTaskByUserNick(String userId,Boolean inUse){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("inUse", inUse);
		return tradeSetupDao.queryTaskByUserNick(map);
	}
	
}
