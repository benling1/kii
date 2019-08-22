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

import s2jh.biz.shop.crm.order.dao.OrderAdvancedSettingDao;
import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.getIpAddress;
@Service
@Transactional
public class OrderAdvancedSettingService extends BaseService<OrderSetup, Long> {

	@Override
	protected BaseDao<OrderSetup, Long> getEntityDao() {
		return null;
	}
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private OrderAdvancedSettingDao orderAdvancedSettingDao;
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
	 * 	通过id修改高级设置
	 * @param request 
	 */
	public boolean updateOrderAdvancedSetting(OrderAdvancedSetting orderAdvancedSetting) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("id", orderAdvancedSetting.getId());
		map.put("locality", orderAdvancedSetting.getLocality());
		map.put("vendormark", orderAdvancedSetting.getVendormark());
		map.put("flagcolor", orderAdvancedSetting.getFlagcolor());
		map.put("orderSource", orderAdvancedSetting.getOrderSource());
		map.put("memberLevel", orderAdvancedSetting.getMemberLevel());
		map.put("productSelect", orderAdvancedSetting.getProductSelect());
		map.put("itemId", orderAdvancedSetting.getItemId());
		map.put("lastModifiedDate", orderAdvancedSetting.getLastModifiedDate());
		Integer count = myBatisDao.execute(OrderAdvancedSetting.class.getName(), "updateOrderAdvancedSetting", map);
		if(count==1){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 添加高级设置 
	 * @param request 
	 */
	public boolean saveOrderAdvancedSetting(
			OrderAdvancedSetting orderAdvancedSetting) {
		try {
			//保存
			orderAdvancedSettingDao.save(orderAdvancedSetting);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 *  查询高级设置
	 * 	参数:卖家id: userId 操作类型:settingType
	 *	返回值:OrderAdvancedSetting
	 */
	public OrderAdvancedSetting findOrderAdvancedSettingByUserIdAndSettingType(
			String userId, String settingType) {
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("userId", userId);
		map.put("settingType", settingType);
		List<OrderAdvancedSetting> orderAdvancedSettingList = myBatisDao.findList(OrderAdvancedSetting.class.getName(), "findOrderAdvancedSettingByUserIdAndSettingType", map);
		if(orderAdvancedSettingList != null && orderAdvancedSettingList.size()>0){
			OrderAdvancedSetting orderAdvancedSetting = orderAdvancedSettingList.get(0);
			return orderAdvancedSetting;
		}
		return null;
	}
	
	
	//买家退款申请 高级设置 保存  新修改
	public boolean savebuyerAdvancedSetting(OrderAdvancedSetting orderAdvancedSetting, HttpServletRequest request){
		try {
			//保存
			orderAdvancedSettingDao.save(orderAdvancedSetting);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/*public  void savebuyerAdvancedSetting(OrderAdvancedSetting orderAdvancedSetting, HttpServletRequest request){
		
		//3.设置类型
		orderAdvancedSetting.setSettingType("29");
		//默认设置定时发送为关闭
		orderAdvancedSetting.setStatus("1");
		orderAdvancedSettingDao.save(orderAdvancedSetting);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("买家申请退款高级设置添加");
		op.setType("添加");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderAdvancedSetting.getUserId());
		op.setFunctionGens("29");
		userOperationLogDao.save(op);
			
  }*/
			
	//买家申请退款 高级设置 修改 新修改
	public boolean updateBuyerRefundAvancedSetting(OrderAdvancedSetting orderAdvancedSetting,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", orderAdvancedSetting.getId());
		map.put("locality", orderAdvancedSetting.getLocality());
		map.put("vendormark",orderAdvancedSetting.getVendormark());
		map.put("flagcolor", orderAdvancedSetting.getFlagcolor());
		map.put("orderSource", orderAdvancedSetting.getOrderSource());
		map.put("memberLevel", orderAdvancedSetting.getMemberLevel());
		map.put("productSelect", orderAdvancedSetting.getProductSelect());
		map.put("itemId", orderAdvancedSetting.getItemId());
		Integer count = myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "updateBuyerRefundAvancedSetting", map);
		if(count==1){
			return true;
		}else{
			return false;
		}
		
	}
	/*public void updateBuyerRefundAvancedSetting(OrderAdvancedSetting orderAdvancedSetting,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", orderAdvancedSetting.getId());
		map.put("locality", orderAdvancedSetting.getLocality());
		map.put("vendormark",orderAdvancedSetting.getVendormark());
		map.put("flagcolor", orderAdvancedSetting.getFlagcolor());
		map.put("orderSource", orderAdvancedSetting.getOrderSource());
		map.put("memberLevel", orderAdvancedSetting.getMemberLevel());
		map.put("productSelect", orderAdvancedSetting.getProductSelect());
		map.put("itemId", orderAdvancedSetting.getItemId());
		myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "updateBuyerRefundAvancedSetting", map);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("买家申请退款高级设置修改");
		op.setType("修改");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderAdvancedSetting.getUserId());
		op.setFunctionGens("29");
		userOperationLogDao.save(op);
	}*/
			
	//买家申请退款数据查询
	public OrderAdvancedSetting findBuyerRefundAdvanced(String userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		//String userId1 = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId1= "crazyboy";
		map.put("userId", userId);
		
		List<OrderAdvancedSetting> orderAdvancedSettinglist = myBatisDao.findList("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "findBuyerRefundAdvanced", map);
		if(orderAdvancedSettinglist !=null && orderAdvancedSettinglist.size()>0){
			OrderAdvancedSetting orderAdvancedSetting =orderAdvancedSettinglist.get(0);
			return orderAdvancedSetting;
			
		}
		return null;
	}
	
	 
	//退款成功高级设置保存 新修改
	public boolean saveRefundSuccess(OrderAdvancedSetting orderAdvancedSetting1, HttpServletRequest request){
		try {
			//保存
			orderAdvancedSettingDao.save(orderAdvancedSetting1);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/*public void saveRefundSuccess(OrderAdvancedSetting orderAdvancedSetting1, HttpServletRequest request){
		
		//3.设置类型
		orderAdvancedSetting1.setSettingType("30");
				//默认设置定时发送为关闭
		orderAdvancedSetting1.setStatus("1");
		orderAdvancedSettingDao.save(orderAdvancedSetting1);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("退款成功高级设置添加");
		op.setType("添加");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderAdvancedSetting1.getUserId());
		op.setFunctionGens("30");
		userOperationLogDao.save(op);
	}*/
	
	//退款成功 高级设置 修改   新修改
	public boolean updateRefundSuccess(OrderAdvancedSetting orderAdvancedSetting1,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", orderAdvancedSetting1.getId());
		map.put("locality", orderAdvancedSetting1.getLocality());
		map.put("vendormark",orderAdvancedSetting1.getVendormark());
		map.put("flagcolor", orderAdvancedSetting1.getFlagcolor());
		map.put("orderSource", orderAdvancedSetting1.getOrderSource());
		map.put("memberLevel", orderAdvancedSetting1.getMemberLevel());
		map.put("productSelect", orderAdvancedSetting1.getProductSelect());
		map.put("itemId", orderAdvancedSetting1.getItemId());
		Integer count = myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "updateRefundSuccess", map);
		if(count == 1){
			return true;
		}else{
			return false;
		}
	}
	/*public void updateRefundSuccess(OrderAdvancedSetting orderAdvancedSetting1,HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", orderAdvancedSetting1.getId());
		map.put("locality", orderAdvancedSetting1.getLocality());
		map.put("vendormark",orderAdvancedSetting1.getVendormark());
		map.put("flagcolor", orderAdvancedSetting1.getFlagcolor());
		map.put("orderSource", orderAdvancedSetting1.getOrderSource());
		map.put("memberLevel", orderAdvancedSetting1.getMemberLevel());
		map.put("productSelect", orderAdvancedSetting1.getProductSelect());
		map.put("itemId", orderAdvancedSetting1.getItemId());
		myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "updateRefundSuccess", map);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("退款成功高级设置修改");
		op.setType("修改");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderAdvancedSetting1.getUserId());
		op.setFunctionGens("30");
		userOperationLogDao.save(op);
	}*/
	
	//退款成功 高级设置 查询
	public OrderAdvancedSetting findbuyerSuccessFirst(String userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<OrderAdvancedSetting> orderAdvancedSettinglist1 = myBatisDao.findList("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "findBuyerRefundAdvanced1", map);
		if(orderAdvancedSettinglist1 !=null && orderAdvancedSettinglist1.size()>0){
			OrderAdvancedSetting orderAdvancedSetting =orderAdvancedSettinglist1.get(0);
			return orderAdvancedSetting;
			
		}
		return null;
	}
	
	
	//等待退货 高级设置 保存方法 新修改
	public boolean savewaitingRefundGj(OrderAdvancedSetting orderAdvancedSetting2,HttpServletRequest request){
		try {
			//保存
			orderAdvancedSettingDao.save(orderAdvancedSetting2);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/*public void savewaitingRefundGj(OrderAdvancedSetting orderAdvancedSetting2,HttpServletRequest request){
		
		orderAdvancedSettingDao.save(orderAdvancedSetting2);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("等待退货高级设置添加");
		op.setType("添加");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderAdvancedSetting2.getUserId());
		op.setFunctionGens("31");
		userOperationLogDao.save(op);
	}*/
	
	//等待退货 高级设置 修改  新修改
	public boolean updateWaitingRefundGj(OrderAdvancedSetting orderAdvancedSetting2,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", orderAdvancedSetting2.getId());
		map.put("locality", orderAdvancedSetting2.getLocality());
		map.put("vendormark",orderAdvancedSetting2.getVendormark());
		map.put("flagcolor", orderAdvancedSetting2.getFlagcolor());
		map.put("orderSource", orderAdvancedSetting2.getOrderSource());
		map.put("memberLevel", orderAdvancedSetting2.getMemberLevel());
		map.put("productSelect", orderAdvancedSetting2.getProductSelect());
		map.put("itemId", orderAdvancedSetting2.getItemId());
		int count=myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "upadteWaitingRefund", map);
		if(count==1){
			return true;
		}else{
			return false;
		}
	}
	/*public void updateWaitingRefundGj(OrderAdvancedSetting orderAdvancedSetting2,HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", orderAdvancedSetting2.getId());
		map.put("locality", orderAdvancedSetting2.getLocality());
		map.put("vendormark",orderAdvancedSetting2.getVendormark());
		map.put("flagcolor", orderAdvancedSetting2.getFlagcolor());
		map.put("orderSource", orderAdvancedSetting2.getOrderSource());
		map.put("memberLevel", orderAdvancedSetting2.getMemberLevel());
		map.put("productSelect", orderAdvancedSetting2.getProductSelect());
		map.put("itemId", orderAdvancedSetting2.getItemId());
		myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "upadteWaitingRefund", map);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("等待退货高级设置修改");
		op.setType("修改");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderAdvancedSetting2.getUserId());
		op.setFunctionGens("31");
		userOperationLogDao.save(op);
	}*/
	
	//等待退货 高级设置查询
	public OrderAdvancedSetting findWaitingRefundGj(String userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<OrderAdvancedSetting> waitingRefund = myBatisDao.findList("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "findBuyerRefundAdvanced2", map);
		if(waitingRefund != null && waitingRefund.size()>0){
			
			OrderAdvancedSetting orderAdvancedSetting = waitingRefund.get(0);
			return orderAdvancedSetting;
		}
		return null;
	}
	
	//拒绝退款 高级设置 保存方法 新修改
	public boolean saverefusedRefund(OrderAdvancedSetting orderAdvancedSetting3,HttpServletRequest request){
		try {
			//保存
			orderAdvancedSettingDao.save(orderAdvancedSetting3);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/*public void saverefusedRefund(OrderAdvancedSetting orderAdvancedSetting3,HttpServletRequest request){
		
		orderAdvancedSettingDao.save(orderAdvancedSetting3);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("拒绝退款高级设置添加");
		op.setType("添加");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderAdvancedSetting3.getUserId());
		op.setFunctionGens("32");
		userOperationLogDao.save(op);
	}*/
	 
	//拒绝退款 高级设置 修改方法  新修改
	public boolean updateRefusedrefundGj(OrderAdvancedSetting orderAdvancedSetting3,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", orderAdvancedSetting3.getId());
		map.put("locality", orderAdvancedSetting3.getLocality());
		map.put("vendormark",orderAdvancedSetting3.getVendormark());
		map.put("flagcolor", orderAdvancedSetting3.getFlagcolor());
		map.put("orderSource", orderAdvancedSetting3.getOrderSource());
		map.put("memberLevel", orderAdvancedSetting3.getMemberLevel());
		map.put("productSelect", orderAdvancedSetting3.getProductSelect());
		map.put("itemId", orderAdvancedSetting3.getItemId());
		Integer count=myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "updateRefusedrefundGj", map);
		if(count==1){
			return true;
		}else{
			return false;
		}
	}
	/*public void updateRefusedrefundGj(OrderAdvancedSetting orderAdvancedSetting3,HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", orderAdvancedSetting3.getId());
		map.put("locality", orderAdvancedSetting3.getLocality());
		map.put("vendormark",orderAdvancedSetting3.getVendormark());
		map.put("flagcolor", orderAdvancedSetting3.getFlagcolor());
		map.put("orderSource", orderAdvancedSetting3.getOrderSource());
		map.put("memberLevel", orderAdvancedSetting3.getMemberLevel());
		map.put("productSelect", orderAdvancedSetting3.getProductSelect());
		map.put("itemId", orderAdvancedSetting3.getItemId());
		myBatisDao.execute("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "updateRefusedrefundGj", map);
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		//操作日志数据补全
		op.setState("成功");
		op.setFunction("拒绝退款高级设置修改");
		op.setType("修改");
		op.setDate(new Date());
		op.setRemark("退款关怀");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(orderAdvancedSetting3.getUserId());
		op.setFunctionGens("32");
		userOperationLogDao.save(op);
	}*/
	
	//拒绝退款 高级设置 查询方法
	public OrderAdvancedSetting findRefusedRefund(String userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<OrderAdvancedSetting> refusedGj = myBatisDao.findList("s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting", "findRefusedRefundGj", map);
		if(refusedGj != null && refusedGj.size()>0){
			
			OrderAdvancedSetting orderAdvancedSetting= refusedGj.get(0);
			return orderAdvancedSetting;
		}
		return null;
	}
	
	/**
	 * 根据userId和settingType查询高级设置的id
	 * ZTK2017年2月21日上午11:55:15
	 */
	public Long findAdvanceIdByUserAndSettiType(String userId ,String settingType){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("settingType", settingType);
		Long advanceId = myBatisDao.findBy(OrderAdvancedSetting.class.getName(),"findAdvanceByUserAndSettiType", map);
		return advanceId;
	}

	
	/**
	 * 
	 * 同步订单中心设置后期直接删除,请勿调用 @author:jackstraw_yu
	 * */
	@Deprecated
	public Long findAllSettingCount() {
		return myBatisDao.findBy(OrderAdvancedSetting.class.getName(), "findAllSettingCount", null);
	}

	/**
	 * 
	 * 同步订单中心设置后期直接删除,请勿调用 @author:jackstraw_yu
	 * */
	@Deprecated
	public List<OrderAdvancedSetting> findAllLimitSetting(Map<String,Object> map) {
		return myBatisDao.findList(OrderAdvancedSetting.class.getName(), "findAllLimitSetting", map);
	}
}
