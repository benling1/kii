package s2jh.biz.shop.crm.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.shop.crm.order.dao.OrderSetupDao;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;

@Service
@Transactional
public class OrderSetupWLTXService extends BaseService<OrderSetup, Long>{

	@Override
	protected BaseDao<OrderSetup, Long> getEntityDao() {
		return orderSetupDao;
	}
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Autowired
	private OrderSetupDao orderSetupDao;
	
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	/**
	* @Title: saveOrderSetup
	* @Description: TODO(订单中心>>物流提醒保存基本设置)
	* @param @param orderSetup    参数
	* @return void    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public void saveOrderSetup(OrderSetup orderSetup,UserOperationLog op) {
		
		//添加操作日志
		//UserOperationLog op = new UserOperationLog();
		
		//判断操作类型
		if("6".equals(orderSetup.getSettingType())){
			op.setRemark("物流提醒基础设置");
			op.setFunctions("物流提醒基础设置");
		}
		if("7".equals(orderSetup.getSettingType())){
			op.setRemark("到达同城提醒基础设置");
			op.setFunctions("到达同城提醒基础设置");
		}
		if("8".equals(orderSetup.getSettingType())){
			op.setRemark("派件提醒基础设置");
			op.setFunctions("派件提醒基础设置");
		}

		if(orderSetup.getId()==null){
			try {
				orderSetupDao.save(orderSetup);
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
			map.put("id", orderSetup.getId());
			map.put("startTime", orderSetup.getStartTime());
			map.put("endTime", orderSetup.getEndTime());
			map.put("payAmtOne", orderSetup.getPayAmtOne());
			map.put("payAmtTwo", orderSetup.getPayAmtTwo());
			try {
				myBatisDao.execute(OrderSetup.class.getName(), "updateOrderSetupByIdToXDGH", map);
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
	 * @return 
	* @Title: queryOrderSetup
	* @Description: TODO(订单中心>>物流提醒查询基本设置)
	* @param @param orderSetup    参数
	* @return void    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public OrderSetup queryOrderSetup(OrderSetup orderSetup) {
		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("userId", orderSetup.getUserId());
		map.put("settingType", orderSetup.getSettingType());
		OrderSetup queryOrderSetup = myBatisDao.findBy(OrderSetup.class.getName(), "findOrderSetupByUserIdAndSettingType", map);
		
		return queryOrderSetup;
	}

	/**
	* @Title: updateStatus
	* @Description: TODO(开启或者关闭提醒)
	* @param @param orderSetup
	* @param @param op    参数
	* @return void    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public void updateStatus(OrderSetup orderSetup,UserOperationLog op) {
		
		//判断操作类型
		if("6".equals(orderSetup.getSettingType())){
			op.setRemark("开启物流提醒");
			op.setFunctions("关闭物流提醒");
		}
		if("7".equals(orderSetup.getSettingType())){
			op.setRemark("开启到达同城提醒");
			op.setFunctions("关闭到达同城提醒");
		}
		if("8".equals(orderSetup.getSettingType())){
			op.setRemark("开启派件提醒");
			op.setFunctions("关闭派件提醒");
		}

		Map<String,Object> map = new HashMap<String, Object>();
		//测试数据
		map.put("userId", orderSetup.getUserId());
		map.put("settingType", orderSetup.getSettingType());
		map.put("status", orderSetup.getStatus());
		try {
			myBatisDao.execute(OrderSetup.class.getName(), "updateStatus", map);
			op.setType("修改");
			//操作日志数据补全
			op.setState("成功");
		} catch (Exception e) {
			// TODO: handle exception
			op.setState("失败");
		}
			
		userOperationLogDao.save(op);
	}
}
