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

import s2jh.biz.shop.crm.order.dao.OrderSetupDao;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.util.ValidateOrderSetUpUtil;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;

@Service
@Transactional
public class OrderSetupService extends BaseService<OrderSetup, Long>{
	

	@Override
	protected BaseDao<OrderSetup, Long> getEntityDao() {
		return orderSetupDao;
	}
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Autowired
	private OrderSetupDao orderSetupDao;
	
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
	 * 添加基本信息
	 * @param request 
	 */
	public boolean saveOrderSetup(OrderSetup orderSetup) {
//		if(orderSetup!=null){
//			String userNick = orderSetup.getUserId();
//			if(userNick!=null){
//				
//			}
//		}
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){ //之前的验证通过了才进行更新操作
			//保存
			try {
				orderSetupDao.save(orderSetup);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return  false;
		
	}
	
	/**
	 * 需求:更新基本数据
	 * 参数:orderSetup
	 * @param request 
	 */
	public void updateOrderSetupStatus(OrderSetup orderSetup) {
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("id", orderSetup.getId());
		map.put("status", orderSetup.getStatus());
		map.put("lastModifiedDate", orderSetup.getLastModifiedDate());
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){ //之前的验证通过了才进行更新操作
			myBatisDao.execute(OrderSetup.class.getName(), "updateOrderSetupStatus", map);
		}
	}
	
	/**
	 * 需求:更新基本数据
	 * 参数:orderSetup
	 * @param request 
	 */
	public boolean updateOrderSetup(OrderSetup orderSetup) {
		Map<String,Object> map = new HashMap<String, Object>();
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){ //之前的验证通过了才进行更新操作
			//测试数据
			map.put("id", orderSetup.getId());
			map.put("startTime", orderSetup.getStartTime());
			map.put("endTime", orderSetup.getEndTime());
			map.put("payAmtOne", orderSetup.getPayAmtOne());
			map.put("payAmtTwo", orderSetup.getPayAmtTwo());
			map.put("reminderTime", orderSetup.getReminderTime());
			map.put("notSignInTimeout", orderSetup.getNotSignInTimeout());
			map.put("notUpdateTiemout", orderSetup.getNotUpdateTiemout());
			map.put("orderScopeOne", orderSetup.getOrderScopeOne());
			map.put("orderScopeTwo", orderSetup.getOrderScopeTwo());
			map.put("executeGenre", orderSetup.getExecuteGenre());
			map.put("orderId", orderSetup.getOrderId());
			map.put("status", orderSetup.getStatus());
			map.put("lastModifiedDate", orderSetup.getLastModifiedDate());
			map.put("filtingConditions",orderSetup.getFiltingConditions());
			int count = myBatisDao.execute(OrderSetup.class.getName(), "updateOrderSetupByIdToXDGH", map);
			if(count==1){
				return true; //更新成功
			}else{
				return false;//更新没成功
			}
		}else{
			return false; //验证没通过
		}
		
	}

	/**
	 *	需求:查询基本信息
	 *	参数:卖家id: userId 操作类型:settingType
	 *	返回值:orderSetup
	 */
	public OrderSetup findOrderSetupByUserIdAndSettingType(String userId, String settingType) {
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("userId", userId);
		map.put("settingType", settingType);
		List<OrderSetup> orderSetupList = myBatisDao.findList(OrderSetup.class.getName(), "findOrderSetupByUserIdAndSettingType", map);
		if(orderSetupList != null && orderSetupList.size()>0){
			OrderSetup orderSetup = orderSetupList.get(0);
			return orderSetup;
		}else{
			OrderSetup orderSetup = new OrderSetup();
			orderSetup.setCreatedBy(userId);
			orderSetup.setCreatedDate(new Date());
			orderSetup.setUserId(userId);
			orderSetup.setLastModifiedDate(new Date());
			orderSetup.setLastModifiedBy(userId);
			orderSetup.setStatus("1");
			orderSetup.setStartTime("8:00");
			orderSetup.setEndTime("21:00");
			orderSetup.setFiltingConditions("6");
			if("2".equals(settingType) || "3".equals(settingType) || "4".equals(settingType) || "5".equals(settingType)
					|| "12".equals(settingType) || "14".equals(settingType)){
				orderSetup.setReminderTime("15分钟");
			}else {
				orderSetup.setReminderTime(null);
			}
			orderSetup.setExecuteGenre(null);
			orderSetup.setSettingType(settingType);
			orderSetup.setNotSignInTimeout(null);
			orderSetup.setNotUpdateTiemout(null);
			orderSetup.setOrderScopeOne(null);
			orderSetup.setOrderScopeTwo(null);
			orderSetup.setPayAmtOne(null);
			orderSetup.setPayAmtTwo(null);
			myBatisDao.execute(OrderSetup.class.getName(), "insertOrderSetup", orderSetup);
			return orderSetup;
		}
	}
	
	/**
	 * 买家退款申请 基本设置 保存  新修改
	 * 
	 */
	public Boolean savebuyerOrderSetup(OrderSetup orderSetup, HttpServletRequest request){
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){ //之前的验证通过了才进行更新操作
			//保存
			try {
				orderSetupDao.save(orderSetup);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return  false;
	}
	/*public  void savebuyerOrderSetup(OrderSetup orderSetup, HttpServletRequest request){
		
		//4.设置类型
		orderSetup.setSettingType("29");
		//默认设置定时发送为关闭
		orderSetup.setStatus("1");
		//设置过滤条件为超出时间不发送
		orderSetup.setFiltingConditions("6");
		orderSetupDao.save(orderSetup);
  }*/
	
	/**
	 * 买家退款申请 基本设置 修改  新修改
	 * 
	 */
	public Boolean updateBuyerRefundSetting(OrderSetup orderSetup,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){
			map.put("id", orderSetup.getId());
			map.put("startTime", orderSetup.getStartTime());
			map.put("endTime", orderSetup.getEndTime());
			map.put("payAmtOne", orderSetup.getPayAmtOne());
			map.put("payAmtTwo", orderSetup.getPayAmtTwo());
			map.put("status", orderSetup.getStatus());
			int count = myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderSetup", "updateBuyerRefundSetting", map);
			if(count==1){
				return true; //成功
			}else{
				return false; //失败
			}
		}else{
			return false; //验证失败
		}
		
	}
	
/*	public void updateBuyerRefundSetting(OrderSetup orderSetup,HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("id", orderSetup.getId());
		map.put("startTime", orderSetup.getStartTime());
		map.put("endTime", orderSetup.getEndTime());
		map.put("payAmtOne", orderSetup.getPayAmtOne());
		map.put("payAmtTwo", orderSetup.getPayAmtTwo());
		map.put("status", orderSetup.getStatus());
		
		myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderSetup", "updateBuyerRefundSetting", map);
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("买家申请退款基本设置修改");
		op.setType("修改");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderSetup.getUserId());
		op.setFunctionGens("29");
		userOperationLogDao.save(op);
	}*/
	
	/**
	 * 查询买家退款申请基本设置
	 * 
	 */
	public OrderSetup findBuyerRefund(String userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<OrderSetup> orderSetupList1 = myBatisDao.findList("s2jh.biz.shop.crm.order.entity.OrderSetup", "findBuyerRefund", map);
		if(orderSetupList1 !=null && orderSetupList1.size()>0){
			OrderSetup orderSetup = orderSetupList1.get(0);
			return orderSetup;
		}
		return null;
		
	}
	
	/**
	 * 退款成功 基本设置保存方法 新修改
	 * 
	 */
	public boolean saverefundSuccess(OrderSetup orderSetup, HttpServletRequest request){
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){ //之前的验证通过了才进行更新操作
			//保存
			try {
				orderSetupDao.save(orderSetup);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return  false;
	}
	/*public void saverefundSuccess(OrderSetup orderSetup, HttpServletRequest request){
		orderSetup.setSettingType("30");
		orderSetup.setStatus("1");
		orderSetupDao.save(orderSetup);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("退款成功基本设置添加");
		op.setType("添加");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderSetup.getUserId());
		op.setFunctionGens("30");
		userOperationLogDao.save(op);
		
	}*/
	
	/**
	 * 退款成功 基本设置修改 新修改
	 * 
	 */
	public boolean updateRefundSuccess(OrderSetup orderSetup,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){
			map.put("id", orderSetup.getId());
			map.put("startTime", orderSetup.getStartTime());
			map.put("endTime", orderSetup.getEndTime());
			map.put("payAmtOne", orderSetup.getPayAmtOne());
			map.put("payAmtTwo", orderSetup.getPayAmtTwo());
			map.put("status", orderSetup.getStatus());
			int count = myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderSetup", "updateRefundSuccess", map);
			if(count == 1){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/*public void updateRefundSuccess(OrderSetup orderSetup,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("id", orderSetup.getId());
		map.put("startTime", orderSetup.getStartTime());
		map.put("endTime", orderSetup.getEndTime());
		map.put("payAmtOne", orderSetup.getPayAmtOne());
		map.put("payAmtTwo", orderSetup.getPayAmtTwo());
		map.put("status", orderSetup.getStatus());
		myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderSetup", "updateRefundSuccess", map);
	    
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("退款成功高级设置添加");
		op.setType("修改");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderSetup.getUserId());
		op.setFunctionGens("30");
		userOperationLogDao.save(op);
	
	}*/
	
	/**
	 * 退款成功 基本设置查询
	 * 
	 */
	public OrderSetup findRefundSuccess(String userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<OrderSetup> refundSuccess = myBatisDao.findList("s2jh.biz.shop.crm.order.entity.OrderSetup", "findRefundSuccess", map);
		if(refundSuccess != null && refundSuccess.size()>0){
			OrderSetup orderSetup = refundSuccess.get(0);
			return orderSetup;
			
		}
		return null;
	}
	
	/**
	 * 等待退货 基本设置 保存方法 新修改
	 * 
	 */
	public boolean savewaitingRefund(OrderSetup orderSetup,HttpServletRequest request){
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){ //之前的验证通过了才进行更新操作
			//保存
			try {
				orderSetupDao.save(orderSetup);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return  false;
	}

	/*public void savewaitingRefund(OrderSetup orderSetup,HttpServletRequest request){
		orderSetup.setSettingType("31");
		orderSetup.setStatus("1");
		orderSetupDao.save(orderSetup);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("等待退货基本设置添加");
		op.setType("添加");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderSetup.getUserId());
		op.setFunctionGens("31");
		userOperationLogDao.save(op);
		
	}*/
	
	/**
	 * 等待退货 基本设置 修改方法   新修改
	 * 
	 */
	public boolean  updatewaitingRefund(OrderSetup orderSetup,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){
			map.put("id", orderSetup.getId());
			map.put("startTime", orderSetup.getStartTime());
			map.put("endTime", orderSetup.getEndTime());
			map.put("payAmtOne", orderSetup.getPayAmtOne());
			map.put("payAmtTwo", orderSetup.getPayAmtTwo());
			map.put("status", orderSetup.getStatus());
			int count=myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderSetup", "updateWaitingRefund", map);
			if(count==1){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/*public void updatewaitingRefund(OrderSetup orderSetup,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("id", orderSetup.getId());
		map.put("startTime", orderSetup.getStartTime());
		map.put("endTime", orderSetup.getEndTime());
		map.put("payAmtOne", orderSetup.getPayAmtOne());
		map.put("payAmtTwo", orderSetup.getPayAmtTwo());
		map.put("status", orderSetup.getStatus());
		myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderSetup", "updateWaitingRefund", map);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("等待退货基本设置修改");
		op.setType("修改");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderSetup.getUserId());
		op.setFunctionGens("31");
		userOperationLogDao.save(op);
	}*/
	
	/**
	 * 等待退货 基本设置 查询方法
	 * 
	 */
	public OrderSetup findWaitingRefund(String userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<OrderSetup> waitingRefund = myBatisDao.findList("s2jh.biz.shop.crm.order.entity.OrderSetup", "findWaitingRefund", map);
		if(waitingRefund != null && waitingRefund.size()>0){
			OrderSetup orderSetup= waitingRefund.get(0);
			return orderSetup;
		}
		return null;
	}
	
	/**
	 * 拒绝退款 基本设置 保存 方法 新修改
	 * 
	 */
	public boolean saverefusedRefund(OrderSetup orderSetup,HttpServletRequest request){
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){ //之前的验证通过了才进行更新操作
			//保存
			try {
				orderSetupDao.save(orderSetup);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return  false;
	}
	/*public void saverefusedRefund(OrderSetup orderSetup,HttpServletRequest request){
		orderSetup.setSettingType("32");
		orderSetup.setStatus("1");
		orderSetupDao.save(orderSetup);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("拒绝退款基本设置添加");
		op.setType("添加");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderSetup.getUserId());
		op.setFunctionGens("32");
		userOperationLogDao.save(op);
	}*/
	
	/**
	 * 拒绝退款 基本设置 修改方法  新修改
	 * 
	 */
	public boolean updaterefusedRefund(OrderSetup orderSetup,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		boolean flag = ValidateOrderSetUpUtil.validateOrderSetupTime(orderSetup);
		if(flag){
			flag = ValidateOrderSetUpUtil.validateOrderSetupPayment(orderSetup);
		}
		if(flag){
			map.put("id", orderSetup.getId());
			map.put("startTime", orderSetup.getStartTime());
			map.put("endTime", orderSetup.getEndTime());
			map.put("payAmtOne", orderSetup.getPayAmtOne());
			map.put("payAmtTwo", orderSetup.getPayAmtTwo());
			map.put("status", orderSetup.getStatus());
			int count = myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderSetup", "updateRefusedRefund", map);
			if(count==1){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/*public void updaterefusedRefund(OrderSetup orderSetup,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("id", orderSetup.getId());
		map.put("startTime", orderSetup.getStartTime());
		map.put("endTime", orderSetup.getEndTime());
		map.put("payAmtOne", orderSetup.getPayAmtOne());
		map.put("payAmtTwo", orderSetup.getPayAmtTwo());
		map.put("status", orderSetup.getStatus());
		myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderSetup", "updateRefusedRefund", map);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("等待退货基本设置修改");
		op.setType("修改");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderSetup.getUserId());
		op.setFunctionGens("32");
		userOperationLogDao.save(op);
	}*/
	
	/**
	 * 拒绝退款 基本设置 查询方法
	 * 
	 */
	public OrderSetup findrefusedRefund(String userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<OrderSetup> refusedRefund = myBatisDao.findList("s2jh.biz.shop.crm.order.entity.OrderSetup", "findrefusedRefund", map);
		if(refusedRefund != null && refusedRefund.size()>0){
			OrderSetup orderSetup = refusedRefund.get(0);
			return orderSetup;
		}
		return null;
	}
	/**
	 * 根据id修改设置的状态
	 * ZTK2017年1月10日上午11:41:56
	 */
	public boolean updateStatusById(Map<String, Object> map){
		int updateCount = myBatisDao.execute(OrderSetup.class.getName(), "updateSetupStatus", map);
		if(updateCount == 1){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 通过userId和settingType查询Id
	 * ZTK2017年2月21日上午11:35:19
	 */
	public Long findByUserAndSettiType(String userId,String settingType){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("settingType", settingType);
		Long orderSetupID = this.myBatisDao.findBy(OrderSetup.class.getName(), "findByUserAndSettiType", map);
		return orderSetupID;
	}
	
	/**
	 * 查询status状态到top.jsp页面中
	 * helei 2017年3月2日下午5:08:21
	 */
	public List<OrderSetup> findOrderSetupOfStatus(Map<String, Object> map) {
		List<OrderSetup> list = myBatisDao.findList(OrderSetup.class.getName(), "findOrderSetupOfStatus",map);
		return list;
	}
	
	/**
	 * 查询订单中心开启的模块
	 * @return List<String>
	 * ZTK2017年4月24日下午5:36:58
	 */
	public List<String> findOrderSetupOfStatus(String userId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("status","0");
		List<String> settingTypeList = myBatisDao.findList(OrderSetup.class.getName(), "findOrderSetupIsOpen", map);
		return settingTypeList;
	}
	
	/**
	 * 
	 * 同步订单中心设置后期直接删除,请勿调用 @author:jackstraw_yu
	 * */
	@Deprecated
	public Long findAllSettingCount() {
		// TODO Auto-generated method stub
		return myBatisDao.findBy(OrderSetup.class.getName(), "findAllSettingCount", null);
	}
	
	/**
	 * 
	 * 同步订单中心设置后期直接删除,请勿调用 @author:jackstraw_yu
	 * */
	@Deprecated
	public List<OrderSetup> findAllLimitSetting(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return myBatisDao.findList(OrderSetup.class.getName(), "findAllLimitSetting", map);
	}
	
}
