package s2jh.biz.shop.crm.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.message.dao.SmsSettingDao;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.order.dao.OrderAdvancedSettingDao;
import s2jh.biz.shop.crm.order.dao.OrderSetupDao;
import s2jh.biz.shop.crm.order.dao.OrdersDao;
import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

/**
 * 催付订单service
 * @author Administrator
 *
 */
@Service
@Transactional
public class ReminderNormalService extends BaseService<Orders, Long>{

	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private OrderSetupDao orderSetupDao;
	
	@Autowired
	private OrderAdvancedSettingDao orderAdvancedSettingDao;
	
	@Autowired
	private SmsSettingDao smsSettingDao;
	
	//操作日志dao
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	protected BaseDao<Orders, Long> getEntityDao() {
		return ordersDao;
	}
	
	/**
	 * 调用操作日志方法保存操作日志
	 */
	public void saveUserOperationLog(UserOperationLog op) {
		//保存操作日志
		userOperationLogDao.save(op);

	}
	
	/**
	 * 根据订单id查询下单时间(12/29更改  暂时不用)
	 */
	/*public Date findCreatedById(String id){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("oid", id);
		Date orderCreated = myBatisDao.findBy(Orders.class.getName(), "findCreatedById", map);
		return orderCreated;
	}*/
	/**
	 * 添加基本信息
	 * @param request 
	 */
	public void saveOrderSetup(OrderSetup orderSetup) {
		//保存
		orderSetupDao.save(orderSetup);
	}
	/**
	 * 需求:更新基本数据
	 * 参数:orderSetup
	 * @param request 
	 */
	public void updateOrderSetup(OrderSetup orderSetup) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", orderSetup.getId());
		map.put("startTime", orderSetup.getStartTime());
		map.put("endTime", orderSetup.getEndTime());
		map.put("payAmtOne", orderSetup.getPayAmtOne());
		map.put("payAmtTwo", orderSetup.getPayAmtTwo());
		map.put("lastModifiedBy", orderSetup.getLastModifiedBy());
		map.put("lastModifiedDate", orderSetup.getLastModifiedDate());
		map.put("reminderTime", orderSetup.getReminderTime());
		map.put("filtingConditions", orderSetup.getFiltingConditions());
		//更新
		int i = myBatisDao.execute(OrderSetup.class.getName(), "updateOrderSetupByIdToXDGH", map);
	}

	/**
	 * 添加高级设置
	 */
	public void saveOrderAdvanceSetting(OrderAdvancedSetting orderAdvancedSetting){
		//保存
		orderAdvancedSettingDao.save(orderAdvancedSetting);
	} 
	
	/**
	 * 更新高级设置
	 */
	public void updateOrderAdvanceSetting(OrderAdvancedSetting orderAdvancedSetting){
		Map<String, Object> map = new HashMap<String, Object>();	
		map.put("id", orderAdvancedSetting.getId());
		map.put("locality", orderAdvancedSetting.getLocality());
		map.put("vendormark", orderAdvancedSetting.getVendormark());
		map.put("flagcolor", orderAdvancedSetting.getFlagcolor());
		map.put("orderSource",orderAdvancedSetting.getOrderSource());
		map.put("memberLevel", orderAdvancedSetting.getMemberLevel());
		map.put("productSelect", orderAdvancedSetting.getProductSelect());
		map.put("itemId", orderAdvancedSetting.getItemId());
		map.put("lastModifiedBy", orderAdvancedSetting.getLastModifiedBy());
		map.put("lastModifiedDate", orderAdvancedSetting.getLastModifiedDate());
		//更新
		myBatisDao.execute(OrderAdvancedSetting.class.getName(), "updateOrderAdvancedSetting", map);
	}

	/**
	 * 查询高级设置
	 * @param userId
	 * @param settingType
	 */
	/*public OrderAdvancedSetting queryAdvanceSetting(Long userId, String settingType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("settingType", settingType);
		OrderAdvancedSetting orderAdvancedSetting = myBatisDao.findBy(OrderAdvancedSetting.class.getName(), "findOrderAdvancedSettingByUserIdAndSettingType", map);
		return orderAdvancedSetting;
	}*/
	
	/**
	 * 更新短信设置
	 */
	public void updateMessageSetting(SmsSetting smsSetting){
		//更新
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", smsSetting.getId());
		map.put("messageVariable", smsSetting.getMessageVariable());
		map.put("paymentLink", smsSetting.getPaymentLink());
		map.put("mobileTest", smsSetting.getMobileTest());
		map.put("messageContent", smsSetting.getMessageContent());
		map.put("messageSignature", smsSetting.getMessageSignature());
		map.put("smsNumber", smsSetting.getSmsNumber());
		map.put("lastModifiedBy", smsSetting.getLastModifiedBy());
		map.put("lastModifiedDate", smsSetting.getLastModifiedDate());
		myBatisDao.execute(SmsSetting.class.getName(), "updateSmsSettingById", map);
	}
	
	/**
	 * 保存添加短信设置
	 */
	public void saveMessageSetting(SmsSetting smsSetting){
		//保存
		smsSettingDao.save(smsSetting);
	}
	/**
	 * 查询短信设置
	 */
	public SmsSetting findSmsSetting(String userId,String settingType){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("settingType", settingType);
		SmsSetting smsSetting = myBatisDao.findBy(SmsSetting.class.getName(), "findSmsSetting", map);
		return smsSetting;
	}
}