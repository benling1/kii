package s2jh.biz.shop.crm.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.message.dao.SmsSettingDao;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.order.entity.OrderReminder;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;

@Service
@Transactional
public class SmsSettingService extends BaseService<SmsSetting, Long> {

	@Autowired
	private SmsSettingDao smsSettingDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	//操作日志dao
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	@Autowired
	private OrderReminderService orderReminderService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	protected BaseDao<SmsSetting, Long> getEntityDao() {
		return smsSettingDao;
	}
	
	/**
	 * 调用操作日志方法保存操作日志
	 */
	public void saveUserOperationLog(UserOperationLog op) {
		//保存操作日志
		userOperationLogDao.save(op);

	}

	/**
	 * 更新短信设置
	 */
	public boolean updateMessageSetting(SmsSetting smsSetting){
		//更新
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", smsSetting.getId());
		map.put("messageVariable", smsSetting.getMessageVariable());
		map.put("paymentLink", smsSetting.getPaymentLink());
		map.put("mobileTest", smsSetting.getMobileTest());
		map.put("messageContent", smsSetting.getMessageContent());
		map.put("messageSignature", smsSetting.getMessageSignature());
		map.put("smsNumber", smsSetting.getSmsNumber());
		map.put("itemId", smsSetting.getItemId());
		map.put("orderId", smsSetting.getOrderId());
		map.put("lastModifiedDate", smsSetting.getLastModifiedDate());
		int count = myBatisDao.execute(SmsSetting.class.getName(), "updateSmsSettingById", map);
		if(count==1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 保存添加短信设置
	 */
	public boolean saveMessageSetting(SmsSetting smsSetting){
		try {
			smsSettingDao.save(smsSetting);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 查询短信设置
	 */
	public SmsSetting findSmsSetting(String userId,String settingType){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("settingType", settingType);
		SmsSetting smsSetting = myBatisDao.findBy(SmsSetting.class.getName(), "findSmsSetting", map);
		if(smsSetting==null){
			SmsTemplate smsTemplate = myBatisDao.findBy(SmsTemplate.class.getName(), "findAutoModel", map);
			if(smsTemplate!=null){
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
				smsSetting = new SmsSetting();
				smsSetting.setMessageContent(content);
				smsSetting.setMessageSignature(shopName);
				smsSetting.setUserId(userId);
				smsSetting.setSettingType(settingType);
				myBatisDao.execute(SmsSetting.class.getName(), "doCreateAutoModel", smsSetting);
			}
		}
		return smsSetting;
	}
	
	//-----------------------------退款关怀短信设置-------------------------------//
	
		//买家申请退款 保存方法  新修改
	    public boolean saveBuyerrefund(SmsSetting smsSetting, HttpServletRequest request){
	    	try {
				smsSettingDao.save(smsSetting);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	    }
		/*public void saveBuyerrefund(SmsSetting smsSetting, HttpServletRequest request){
			
			smsSettingDao.save(smsSetting);
		}*/
		
		//买家申请退款 修改方法  新修改
		public boolean updateRefundBuyer(SmsSetting smsSetting,HttpServletRequest request){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", smsSetting.getId());
			map.put("userId", smsSetting.getUserId());
			map.put("settingType", smsSetting.getSettingType());
			map.put("status", smsSetting.getStatus());
			map.put("messageVariable", smsSetting.getMessageVariable());
			map.put("paymentLink", smsSetting.getPaymentLink());
			map.put("mobileTest", smsSetting.getMobileTest());
			map.put("messageContent", smsSetting.getMessageContent());
			map.put("messageSignature", smsSetting.getMessageSignature());
			map.put("smsNumber", smsSetting.getSmsNumber());
			int count = myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsSetting", "updateRefundBuyer", map);
			if(count==1){
				return true;
			}else{
				return false;
			}
		}
		/*public void updateRefundBuyer(SmsSetting smsSetting,HttpServletRequest request){
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", smsSetting.getId());
			map.put("userId", smsSetting.getUserId());
			map.put("settingType", smsSetting.getSettingType());
			map.put("status", smsSetting.getStatus());
			map.put("messageVariable", smsSetting.getMessageVariable());
			map.put("paymentLink", smsSetting.getPaymentLink());
			map.put("mobileTest", smsSetting.getMobileTest());
			map.put("messageContent", smsSetting.getMessageContent());
			map.put("messageSignature", smsSetting.getMessageSignature());
			map.put("smsNumber", smsSetting.getSmsNumber());
			myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsSetting", "updateRefundBuyer", map);
			
		}*/
		
		//买家申请退款 查询方法
		public SmsSetting findBuyerSmsSetting(String userId,String settingType){
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("settingType", settingType);
			SmsSetting smsSetting = myBatisDao.findBy(SmsSetting.class.getName(), "findSmsSetting", map);
			if(smsSetting==null){
				SmsTemplate smsTemplate = myBatisDao.findBy(SmsTemplate.class.getName(), "findAutoModel", map);
				if(smsTemplate!=null){
					smsSetting = new SmsSetting();
					smsSetting.setMessageContent(smsTemplate.getContent());
					smsSetting.setMessageSignature(userId);
					smsSetting.setUserId(userId);
					smsSetting.setSettingType(settingType);
					myBatisDao.execute(SmsSetting.class.getName(), "doUpdateAutoModel", smsSetting);
				}
			}
			return smsSetting;
		}
		
		
		//退款成功 短信设置 保存方法 新修改
		public boolean saveRefundSuccessSmsSetting(SmsSetting smsSetting,HttpServletRequest request){
			try {
				smsSettingDao.save(smsSetting);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		/*public void saveRefundSuccessSmsSetting(SmsSetting smsSetting,HttpServletRequest request){
			
			smsSettingDao.save(smsSetting);
		}*/
		
		//退款成功 短信设置 修改方法 新修改
		public boolean updateRefundSuccessSmsSetting(SmsSetting smsSetting,HttpServletRequest request){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", smsSetting.getId());
			map.put("userId", smsSetting.getUserId());
			map.put("settingType", smsSetting.getSettingType());
			map.put("status", smsSetting.getStatus());
			map.put("messageVariable", smsSetting.getMessageVariable());
			map.put("paymentLink", smsSetting.getPaymentLink());
			map.put("mobileTest", smsSetting.getMobileTest());
			map.put("messageContent", smsSetting.getMessageContent());
			map.put("messageSignature", smsSetting.getMessageSignature());
			map.put("smsNumber", smsSetting.getSmsNumber());
			int count = myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsSetting", "updateRefundSuccessSmsSetting", map);
			if(count == 1){
				return true;
			}else{
				return false;
			}
		}
		/*public void updateRefundSuccessSmsSetting(SmsSetting smsSetting,HttpServletRequest request){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", smsSetting.getId());
			map.put("userId", smsSetting.getUserId());
			map.put("settingType", smsSetting.getSettingType());
			map.put("status", smsSetting.getStatus());
			map.put("messageVariable", smsSetting.getMessageVariable());
			map.put("paymentLink", smsSetting.getPaymentLink());
			map.put("mobileTest", smsSetting.getMobileTest());
			map.put("messageContent", smsSetting.getMessageContent());
			map.put("messageSignature", smsSetting.getMessageSignature());
			map.put("smsNumber", smsSetting.getSmsNumber());
			myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsSetting", "updateRefundSuccessSmsSetting", map);
		}*/
		
		//退款成功 短信设置 查询方法
		public SmsSetting findRefundSuccessSmsSetting(String userId){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			List<SmsSetting> smsSettinglist = myBatisDao.findList("s2jh.biz.shop.crm.message.entity.SmsSetting", "findRefundSuccessSmsSetting", map);
			if(smsSettinglist != null && smsSettinglist.size()>0){
				SmsSetting smsSetting = smsSettinglist.get(0);
				return smsSetting;
			}
			return null;
		}
		
		//等待退货 短信设置 保存方法  新修改
		public boolean saveWaitingRefundSmsSetting(SmsSetting smsSetting, HttpServletRequest request){
			try {
				smsSettingDao.save(smsSetting);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		/*public void saveWaitingRefundSmsSetting(SmsSetting smsSetting, HttpServletRequest request){
			
			
			smsSettingDao.save(smsSetting);
			
		}	*/	 
		//等待退货 短信设置 修改方法  新修改
		public boolean updateWaitingRefundSmsSetting(SmsSetting smsSetting,HttpServletRequest request){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", smsSetting.getId());
			map.put("userId", smsSetting.getUserId());
			map.put("settingType", smsSetting.getSettingType());
			map.put("status", smsSetting.getStatus());
			map.put("messageVariable", smsSetting.getMessageVariable());
			map.put("paymentLink", smsSetting.getPaymentLink());
			map.put("mobileTest", smsSetting.getMobileTest());
			map.put("messageContent", smsSetting.getMessageContent());
			map.put("messageSignature", smsSetting.getMessageSignature());
			map.put("smsNumber", smsSetting.getSmsNumber());
			int count = myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsSetting", "updateWaitingRefundSmsSetting", map);
			if(count==1){
				return true;
			}else{
				return false;
			}
		}
		/*public void updateWaitingRefundSmsSetting(SmsSetting smsSetting,HttpServletRequest request){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", smsSetting.getId());
			map.put("userId", smsSetting.getUserId());
			map.put("settingType", smsSetting.getSettingType());
			map.put("status", smsSetting.getStatus());
			map.put("messageVariable", smsSetting.getMessageVariable());
			map.put("paymentLink", smsSetting.getPaymentLink());
			map.put("mobileTest", smsSetting.getMobileTest());
			map.put("messageContent", smsSetting.getMessageContent());
			map.put("messageSignature", smsSetting.getMessageSignature());
			map.put("smsNumber", smsSetting.getSmsNumber());
			myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsSetting", "updateWaitingRefundSmsSetting", map);
			
		}*/
		
		//等待退货 短信设置查询
		public SmsSetting findWaitingRefundSmsSetting(String userId){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			List<SmsSetting> smsSettinglist = myBatisDao.findList("s2jh.biz.shop.crm.message.entity.SmsSetting", "findWaitingRefundSmsSetting", map);
			if(smsSettinglist != null && smsSettinglist.size()>0){
				SmsSetting smsSetting = smsSettinglist.get(0);
				return smsSetting;
			}
			return null;
			
		}
		
		  
		//拒绝退款 短信设置 保存方法  新修改
		public boolean saveRefusedRefundSmsSetting(SmsSetting smsSetting,HttpServletRequest request){
			try {
				smsSettingDao.save(smsSetting);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		/*public void saveRefusedRefundSmsSetting(SmsSetting smsSetting,HttpServletRequest request){
			//补全属性
			smsSettingDao.save(smsSetting);
			
		}*/
		
		//拒绝退款 短信设置 修改方法 新修改
		public boolean updateRefusedSmsSetting(SmsSetting smsSetting,HttpServletRequest request){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", smsSetting.getId());
			map.put("userId", smsSetting.getUserId());
			map.put("settingType", smsSetting.getSettingType());
			map.put("status", smsSetting.getStatus());
			map.put("messageVariable", smsSetting.getMessageVariable());
			map.put("paymentLink", smsSetting.getPaymentLink());
			map.put("mobileTest", smsSetting.getMobileTest());
			map.put("messageContent", smsSetting.getMessageContent());
			map.put("messageSignature", smsSetting.getMessageSignature());
			map.put("smsNumber", smsSetting.getSmsNumber());
			int count=myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsSetting", "updateRefusedRefundSmsSetting", map);
			if(count==1){
				return true;
			}else{
				return false;
			}
		}
		/*public void updateRefusedSmsSetting(SmsSetting smsSetting,HttpServletRequest request){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", smsSetting.getId());
			map.put("userId", smsSetting.getUserId());
			map.put("settingType", smsSetting.getSettingType());
			map.put("status", smsSetting.getStatus());
			map.put("messageVariable", smsSetting.getMessageVariable());
			map.put("paymentLink", smsSetting.getPaymentLink());
			map.put("mobileTest", smsSetting.getMobileTest());
			map.put("messageContent", smsSetting.getMessageContent());
			map.put("messageSignature", smsSetting.getMessageSignature());
			map.put("smsNumber", smsSetting.getSmsNumber());
			myBatisDao.execute("s2jh.biz.shop.crm.message.entity.SmsSetting", "updateRefusedRefundSmsSetting", map);
			
		}*/
		
		//拒绝退款 短信设置 查询方法
		public SmsSetting findRefusedRefundSmsSetting(String userId){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			List<SmsSetting> smsSettinglist = myBatisDao.findList("s2jh.biz.shop.crm.message.entity.SmsSetting", "findRefusedRefundSmsSetting", map);
			if(smsSettinglist != null && smsSettinglist.size()>0){
				SmsSetting smsSetting = smsSettinglist.get(0);
				return smsSetting;
			}
			return null;
		}
		
		/**
		 * 根据用户查询所有短信设置
		 * @param userId
		 * @param shopName
		 * @Date 2017-06-12 15:42
		 * @return
		 */
		public boolean findSmsSettingList(String userId,String shopName){
			List<SmsSetting> list = myBatisDao.findList("s2jh.biz.shop.crm.message.entity.SmsSetting", "findSmsSettingList", userId);
			updateSmsSettingShopName(list,shopName);
			return true;
		}
		
		/**
		 * 修改订单中心所有模板设置里的短信签名
		 * @param list
		 * @param shopName
		 * @Date 2017-06-12 15:42
		 */
		public void updateSmsSettingShopName(List<SmsSetting> list,String shopName){
			for(SmsSetting ss:list){
				String content = ss.getMessageContent();
				int start = content.indexOf("【");
				int end = content.indexOf("】");
				if(start==0&&end>0){
					content = content.substring(0,start)+"【"+shopName+"】"+content.substring(end+1,content.length());
				}else{
					content = "【"+shopName+"】"+content;
				}
				ss.setMessageContent(content);
				updateMessageSetting(ss);
			}
		}
		
		/**
		 * 修改手动订单提醒里短信签名
		 * @param sms
		 * @Date 2017-06-12 15:42
		 */
		public void updateOrderReminderSmsContent(String shopName,String userId){
			
			OrderReminder or = orderReminderService.findOrderReminder(userId);
			if(or!=null){
				Map<String,Object> map = new HashMap<String,Object>();
				String content = or.getContent();
				int start = content.indexOf("【");
				int end = content.indexOf("】");
				if(start==0&&end>0){
					content = content.substring(0,start)+"【"+shopName+"】"+content.substring(end+1,content.length());
				}else{
					content = "【"+shopName+"】"+content;
				}
				map.put("content", content);
				map.put("id", or.getId());
				map.put("smsSign", shopName);
				map.put("lastModifiedDate", new Date());
				orderReminderService.updateOr(map);
			}
			
		}
		
		/**
		 * 
		 * 同步订单中心设置后期直接删除,请勿调用 @author:jackstraw_yu
		 * */
		@Deprecated
		public Long findAllSettingCount() {
			// TODO Auto-generated method stub
			return myBatisDao.findBy(SmsSetting.class.getName(), "findAllSettingCount", null);
		}

		/**
		 * 
		 * 同步订单中心设置后期直接删除,请勿调用 @author:jackstraw_yu
		 * */
		@Deprecated
		public List<SmsSetting> findAllLimitSetting(Map<String, Object> map) {
			return myBatisDao.findList(SmsSetting.class.getName(), "findAllLimitSetting", map);
		}
}
