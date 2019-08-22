package s2jh.biz.shop.crm.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.order.dao.OrderRateCareSetupDao;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.order.entity.TradeRates;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;

@Service
@Transactional
public class OrderRateCareSetupService extends BaseService<OrderRateCareSetup, Long> {

	private Logger logger = LoggerFactory.getLogger(OrderRateCareSetupService.class);
	
	@Autowired
	private OrderRateCareSetupDao orderRateCareSetupDao;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	protected BaseDao<OrderRateCareSetup, Long> getEntityDao() {
		return orderRateCareSetupDao;
	}
	
	@Autowired
	private MyBatisDao myBatisDao;

	
	//操作日志dao
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	/**
	 * 调用操作日志方法保存操作日志
	 */
	public void saveUserOperationLog(UserOperationLog op) {
		//保存操作日志
		userOperationLogDao.save(op);

	}

	/**
	 * 根据id修改中差评监控设置
	 * helei 2017年1月3日下午5:08:35
	 */
	public boolean updateOrderRateCareSetup(OrderRateCareSetup orderRateCareSetup) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", orderRateCareSetup.getId());
		map.put("oid", orderRateCareSetup.getOid());
		map.put("status", orderRateCareSetup.getStatus());
		map.put("result", orderRateCareSetup.getResult());
		map.put("isBlacklist", orderRateCareSetup.getIsBlacklist());
		map.put("rateChoose", orderRateCareSetup.getRateChoose());
		map.put("blacklistRateType", orderRateCareSetup.getBlacklistRateType());
		map.put("autoAddBlacklist", orderRateCareSetup.getAutoAddBlacklist());
		map.put("content", orderRateCareSetup.getContent());
		map.put("deferRateDay", orderRateCareSetup.getDeferRateDay());
		map.put("statusFiltrate", orderRateCareSetup.getStatusFiltrate());
		map.put("acceptSmsPhone", orderRateCareSetup.getAcceptSmsPhone());
		map.put("sendTimeOne", orderRateCareSetup.getSendTimeOne());
		map.put("sendTimeTwo", orderRateCareSetup.getSendTimeTwo());
		map.put("moneyOne", orderRateCareSetup.getMoneyOne());
		map.put("moneyTwo", orderRateCareSetup.getMoneyTwo());
		map.put("flagcolor", orderRateCareSetup.getFlagcolor());
		map.put("orderSource", orderRateCareSetup.getOrderSource());
		map.put("locality", orderRateCareSetup.getLocality());
		map.put("lastModifiedDate", orderRateCareSetup.getLastModifiedDate());
		map.put("lastModifiedBy", orderRateCareSetup.getLastModifiedBy());
		map.put("neutralContent", orderRateCareSetup.getNeutralContent());
		map.put("badContent", orderRateCareSetup.getBadContent());
		int count = myBatisDao.execute(OrderRateCareSetup.class.getName(), "updateOrderRateCareSetup", map);
		if(count==1){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * 添加中差评监控设置
	 * helei 2017年1月3日下午5:09:11
	 */
	public boolean saveOrderRateCareSetup(OrderRateCareSetup orderRateCareSetup) {
		try {
			myBatisDao.execute(OrderRateCareSetup.class.getName(), "addOrderRateCareSetup", orderRateCareSetup);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}


	/**
	 * 通过userId和appraiseType查询数据
	 * helei 2017年1月3日下午6:31:55
	 */
	public OrderRateCareSetup findOrderRateCareSetup(String userId,String appraiseType) {
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("userId", userId);
		map.put("appraiseType", appraiseType);
		OrderRateCareSetup orderRateCareSetup = null;
		List<OrderRateCareSetup> orderRateCareSetupList = myBatisDao.findList(OrderRateCareSetup.class.getName(), "findOrderRateCareSetup", map);
		if(orderRateCareSetupList != null && orderRateCareSetupList.size()>0){
			orderRateCareSetup = orderRateCareSetupList.get(0);
			return orderRateCareSetup;
		}else{
			try {
				if("0".equals(appraiseType)){//0--(评价关怀)自动评价   1--(中差评管理)中差评监控  2--(中差评管理)中差评安抚
					map.put("settingType", "16");
				}else if("1".equals(appraiseType)){
					map.put("settingType", "20");
				}else if("2".equals(appraiseType)){ //中差评安抚
					map.put("settingType", "21");
				}
				SmsTemplate smsTemplate = myBatisDao.findBy(SmsTemplate.class.getName(), "findAutoModel", map);
				orderRateCareSetup = new OrderRateCareSetup();
				orderRateCareSetup.setStatus("1");
				orderRateCareSetup.setUserId(userId);
				if(smsTemplate!=null){
					orderRateCareSetup.setContent(smsTemplate.getContent());
				}
				orderRateCareSetup.setAutoAddBlacklist("bad");
				orderRateCareSetup.setAppraiseType(appraiseType);
				orderRateCareSetup.setRateChoose("1");
				orderRateCareSetup.setBlacklistRateType("0");
				orderRateCareSetup.setResult("good");
				orderRateCareSetup.setCreatedDate(new Date());
				orderRateCareSetup.setLastModifiedDate(new Date());
				orderRateCareSetup.setLastModifiedBy(userId);
				myBatisDao.execute(OrderRateCareSetup.class.getName(), "doAutoCreateBySetIsNull", orderRateCareSetup);

				
				if(appraiseType.equals("2")){
					//保存短信内容到smssetting表
					SmsSetting smsSetting = new SmsSetting();
					String shopName = null;
					try {
						shopName = (String) request.getSession().getAttribute("ShopName");
					} catch (Exception e) {
					}
					if(shopName !=null && !"".equals(shopName)){
						shopName = "【"+shopName+"】";
					}else{
						shopName ="";
					}
					String content = smsTemplate.getContent();
					if(!content.startsWith("【")){
						content = shopName+content;
					}
					smsSetting.setMessageContent(content);
					smsSetting.setMessageSignature(shopName);
					smsSetting.setUserId(userId);
					smsSetting.setSettingType("21");
					myBatisDao.execute(SmsSetting.class.getName(), "doCreateAutoModel", smsSetting);
				}
				return orderRateCareSetup;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 查询中差评安抚的开启关闭状态
	 * helei 2017年3月3日下午3:05:03
	 */
	public OrderRateCareSetup findOrderRateOfStatus(Map<String, Object> map) {
		OrderRateCareSetup orderRateCareSetup= myBatisDao.findBy(OrderRateCareSetup.class.getName(), "findOrderRateOfStatus", map);
		return orderRateCareSetup;
	}

	/**
	 * 修改中差评安抚的开启关闭状态
	 * helei 2017年3月3日下午4:15:18
	 */
	public void updateOrderRateStatus(OrderRateCareSetup orderRate) {
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("id", orderRate.getId());
		map.put("status", orderRate.getStatus());
		map.put("lastModifiedDate", orderRate.getLastModifiedDate());
		
		myBatisDao.execute(OrderRateCareSetup.class.getName(), "updateOrderRateStatus", map);
	}
	
	/**
	 * 查询自动评价、中差评监控和中差评安抚中设置为开启的模块
	 * ZTK2017年4月24日下午2:48:49
	 */
	public List<String> orderRateCareSetupIsOpen(String userId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<String> statusOpenList = myBatisDao.findList(OrderRateCareSetup.class.getName(), "orderRateCareSetupIsOpen", map);
		return statusOpenList;
	}
	
	/**
	 * 根据评价类型查询内容
	 * ZTK2017年5月15日下午2:35:42
	 */
	public String queryContentByType(String userId,String result){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("result",result);
		String content = myBatisDao.findBy(OrderRateCareSetup.class.getName(), "queryContentByType", map);
		return content;
	}

	/**
	 * 根据评级类型添加对应的评价内容
	 * ZTK2017年5月19日下午12:10:44
	 */
	public boolean insertRateCareSetupByResult(String result, String content,
			String appraiseType, String userId) {
		boolean flag = true;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("result", result);
		if("neutral".equals(result)){
			map.put("neutralContent", content);
		}else if("bad".equals(result)){
			map.put("badContent", content);
		}
		map.put("appraiseType", appraiseType);
		map.put("userId", userId);
		myBatisDao.execute(OrderRateCareSetup.class.getName(), "", map);
		return flag;
	}
	
	/**
	 * 根据评级类型更新对应的评价内容
	 * ZTK2017年5月19日下午12:07:39
	 */
	public boolean updateRateCareSetupByResult(String result, String content,
			String appraiseType,String userId) {
		boolean flag = true;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("result", result);
		map.put("content", content);
		map.put("appraiseType", appraiseType);
		map.put("userId", userId);
		myBatisDao.execute(OrderRateCareSetup.class.getName(), "", map);
		return flag;
	}

	/**
	 * 拉取评价信息，查询用户是否开启中差评监控
	 */
	public boolean findStatusByUserId(String userId){
		if(userId == null || "".equals(userId)){
			return false;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		int openCount = myBatisDao.findBy(OrderRateCareSetup.class.getName(), "countTaskIsOpenByType", map);
		logger.info("<><><><><>========<><><>><><><>" + userId + "开启自动评价/中差评监控/中差评安抚的Count为" + openCount);
		if(openCount > 0){
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * 
	 * 同步订单中心设置后期直接删除,请勿调用 @author:jackstraw_yu
	 * */
	@Deprecated
	public Long findAllSettingCount() {
		// TODO Auto-generated method stub
		return myBatisDao.findBy(OrderRateCareSetup.class.getName(), "findAllSettingCount", null);
	}

	/**
	 * 
	 * 同步订单中心设置后期直接删除,请勿调用 @author:jackstraw_yu
	 * */
	@Deprecated
	public List<OrderRateCareSetup> findAllLimitSetting(Map<String, Object> map) {
		return myBatisDao.findList(OrderRateCareSetup.class.getName(), "findAllLimitSetting", map);
	}
}
