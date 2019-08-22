package s2jh.biz.shop.crm.historyOrder.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.historyOrder.dao.OrderHistoryImportDao;
import s2jh.biz.shop.crm.historyOrder.entity.OrderHistoryImport;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class OrderHistoryImportService extends BaseService<OrderHistoryImport, Long>{
	
	@Autowired
	private OrderHistoryImportDao orderHistoryImportDao;

	@Autowired
	private MyBatisDao myBatisDao;
	
	@Override
	protected BaseDao<OrderHistoryImport, Long> getEntityDao() {
		return orderHistoryImportDao;
	}
	
	//根据卖家编号查询用户历史导入记录
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public List<OrderHistoryImport> findAllOrderHistoryImport(Map map){
		List<OrderHistoryImport> list= myBatisDao.findList(OrderHistoryImport.class.getName(), "findAllOrderHistoryImport", map);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	//根据卖家条件查询用户历史导入记录
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OrderHistoryImport> queryOrderHistoryImport(Map map){
		List<OrderHistoryImport> list= myBatisDao.findList(OrderHistoryImport.class.getName(), "findAllOrderHistoryImport", map);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}

	//通过条件查询出历史订单导入数据,返回分页对象
	@SuppressWarnings("unused")
	public Pagination findPaginationByCondition(String contextPath, Date bTime,
			Date eTime, Integer pageNo, String orderName, String userId) {
		//设置起始页
		if(pageNo==null){
			pageNo =1;	
		}
		//先设置每页显示的条数为3条
		Integer currentRows = 3;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;
		//计算出总页数
		Integer totalCount = findTotalCountByCondition(bTime,eTime, orderName,userId );
		Integer totalPage = (int) Math.ceil(1.0*totalCount/currentRows);
		//调用dao层查询出每页显示的list<OrderHistoryImportList>集合
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("orderName", orderName);
		map.put("userId", userId);
		map.put("startRows",startRows);
		map.put("currentRows", currentRows);
		List<OrderHistoryImport> OrderHistoryImportList = myBatisDao.findLimitList(OrderHistoryImport.class.getName(), "queryOrderHistoryImport", map, currentRows);
		
		
		//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
		//OrderHistoryImportListList:每页显示的list集合或者当前页显示的list集合
		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, OrderHistoryImportList);
		
		StringBuilder params = new StringBuilder();
		if(bTime!=null){
			params.append("&beginTime=").append(bTime.toString());
		}
		if(eTime!=null){
			params.append("&endTime=").append(eTime.toString());
		}
		if(orderName!=null && !"".equals(orderName)){
			params.append("&orderName=").append(orderName);
		}
		if(userId!=null && !"".equals(userId)){
			params.append("&userId=").append(userId);
		}
		//拼接分页的后角标中的跳转路径与查询的条件
		String url =contextPath+"/OrderHistoryImport/showLoadHistoryOrder";
		pagination.pageView(url, params.toString());
		return pagination;
		
	}
	
	//根据条件查询出总条数
	public Integer findTotalCountByCondition(Date bTime,Date eTime,String orderName ,String userId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("orderName", orderName);
		map.put("userId", userId);
		Long totalCount = myBatisDao.findBy(OrderHistoryImport.class.getName(), "queryOrderHistoryImportCount", map);
		Integer i = Integer.valueOf(totalCount.toString());
		return i;
		
	}
	
	/**
	 * 保存历史订单导入记录
	 * helei 2017年2月9日下午2:38:36
	 */
	public Long insertOrderHistoryImport(OrderHistoryImport orderImport) {
		//保存数据到数据库
		myBatisDao.execute(OrderHistoryImport.class.getName(), "insertOrderHistoryImport", orderImport);
		return orderImport.getId();
	}

	/**
	 * 通过id删除导入记录
	 * helei 2017年2月10日下午4:49:36
	 */
	public void deleteOrderById(String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		myBatisDao.findBy(OrderHistoryImport.class.getName(), "deleteOrderById", map);
	}

	/**
	 * 通过id查询出订单导入的对象
	 * @param id
	 * @return
	 */
	public OrderHistoryImport queryOrderHistoryImportByid(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		OrderHistoryImport orderHistoryImport = myBatisDao.findBy(OrderHistoryImport.class.getName(), "queryOrderHistoryImportByid", map);
		return orderHistoryImport;
	}

	
	/**
	 * 线程调用的更新操作
	 * helei 2017年3月24日下午2:21:24
	 */
	public synchronized void updateImportHistory(Long historyImportId,int rsCount,int successCount, int repetitionNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", historyImportId);
		map.put("completeNumber", rsCount);
		map.put("completedQuantity", successCount);
		map.put("repetitionNumber", repetitionNumber);
		
		myBatisDao.execute(OrderHistoryImport.class.getName(), "updateImportHistoryByIdIncrement", map);
		myBatisDao.execute(OrderHistoryImport.class.getName(), "updateImportHistoryState", map);
		
//			OrderHistoryImport orderImport = new OrderHistoryImport();
//			// 创建对象封装数据
//			orderImport.setId(historyImportId);
//			orderImport.setCompleteNumber(rsCount);// 处理完成行数
//			orderImport.setCompletedQuantity(successCount);// 已完成数量
//			orderImport.setRepetitionNumber(repetitionNumber);// 重复数量
//			// 调用保存历史订单导入记录方法,保存数据
//			this.updateImportHistoryById(orderImport);
	}
	
	/**
	 * 更新历史订单导入的数据
	 * helei 2017年3月24日下午2:42:14
	 */
	public synchronized void updateImportHistoryById(OrderHistoryImport historyImport) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("completeNumber", historyImport.getCompleteNumber());
		map.put("completedQuantity", historyImport.getCompletedQuantity());
		map.put("repetitionNumber", historyImport.getRepetitionNumber());
		map.put("id", historyImport.getId());
		
		myBatisDao.execute(OrderHistoryImport.class.getName(), "updateImportHistoryByIdIncrement", map);
		myBatisDao.execute(OrderHistoryImport.class.getName(), "updateImportHistoryState", map);
	}

	public List<OrderHistoryImport> findImportDataById(String id) {
		String[] split = id.split(",");
		List<String> asList = Arrays.asList(split);
		List<OrderHistoryImport> data = myBatisDao.findList(OrderHistoryImport.class.getName(), "findImportDataById", asList);
		return data;
	}
}
