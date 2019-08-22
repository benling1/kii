package s2jh.biz.shop.crm.order.service;

import java.util.HashMap;
import java.util.Map;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.order.dao.OrderAdvancedSettingDao;
import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
@Service
@Transactional
public class OrderAdvancedSettingWLTXService extends BaseService<OrderAdvancedSetting, Long> {

	@Override
	protected BaseDao<OrderAdvancedSetting, Long> getEntityDao() {
		return orderAdvancedSettingDao;
	}
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private OrderAdvancedSettingDao orderAdvancedSettingDao;
	
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	/**
	* @Title: saveorderAdvancedSetting
	* @Description: TODO(修改或者保存高级设置)
	* @param @param OrderAdvancedSetting    参数
	* @return void    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public void saveOrderAdvancedSetting(OrderAdvancedSetting orderAdvancedSetting,UserOperationLog op){
		
		//如果卖家都不标记,清空小旗帜
		if("0".equals(orderAdvancedSetting.getVendormark())){
			orderAdvancedSetting.setFlagcolor(null);
		}
		//如果选择全部商品,就把商品itemId清空
		if("0".equals(orderAdvancedSetting.getProductSelect())){
			orderAdvancedSetting.setItemId(null);
		}

		
		//添加操作日志
		//UserOperationLog op = new UserOperationLog();

		//判断操作类型
		if("6".equals(orderAdvancedSetting.getSettingType())){
			op.setRemark("物流提醒高级设置");
			op.setFunctions("物流提醒高级设置");
		}
		if("7".equals(orderAdvancedSetting.getSettingType())){
			op.setRemark("到达同城提醒高级设置");
			op.setFunctions("到达同城提醒高级设置");
		}
		if("8".equals(orderAdvancedSetting.getSettingType())){
			op.setRemark("派件提醒高级设置");
			op.setFunctions("派件提醒高级设置");
		}
		
		if(orderAdvancedSetting.getId()==null){
			try {
				orderAdvancedSettingDao.save(orderAdvancedSetting);
				op.setType("添加");
				//操作日志数据补全
				op.setState("成功");
			} catch (Exception e) {
				// TODO: handle exception
				op.setState("失败");
			}
			
		}else{
			Map<String,Object> map = new HashMap<String, Object>();
			//测试数据
			map.put("id", orderAdvancedSetting.getId());
			map.put("flagcolor", orderAdvancedSetting.getFlagcolor());
			map.put("locality", orderAdvancedSetting.getLocality());
			map.put("itemId", orderAdvancedSetting.getItemId());
			map.put("vendormark", orderAdvancedSetting.getVendormark());
			map.put("orderSource", orderAdvancedSetting.getOrderSource());
			map.put("memberLevel", orderAdvancedSetting.getMemberLevel());
			map.put("productSelect", orderAdvancedSetting.getProductSelect());
			map.put("settingType", orderAdvancedSetting.getSettingType());
			
			try {
				myBatisDao.execute(OrderAdvancedSetting.class.getName(), "updateOrderAdvancedSetting", map);
				op.setType("修改");
				//操作日志数据补全
				op.setState("成功");
			} catch (Exception e) {
				// TODO: handle exception
				op.setState("失败");
			}
			
		}
		
		userOperationLogDao.save(op);
	}

	
	/**
	* @Title: queryOrderAdvancedSetting
	* @Description: TODO(物流提醒>>查询高级设置)
	* @param @param orderAdvancedSetting
	* @param @return    参数
	* @return OrderAdvancedSetting    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public OrderAdvancedSetting queryOrderAdvancedSetting(
			OrderAdvancedSetting orderAdvancedSetting) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("userId", orderAdvancedSetting.getUserId());
		map.put("settingType", orderAdvancedSetting.getSettingType());
		orderAdvancedSetting = myBatisDao.findBy(OrderAdvancedSetting.class.getName(), "findOrderAdvancedSettingByUserIdAndSettingType", map);
		
		return orderAdvancedSetting;
	}

	
}
