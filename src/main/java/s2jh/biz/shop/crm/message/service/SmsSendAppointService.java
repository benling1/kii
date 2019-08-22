package s2jh.biz.shop.crm.message.service;

import java.text.SimpleDateFormat;
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

import s2jh.biz.shop.crm.message.dao.SmsSendAppointDao;
import s2jh.biz.shop.crm.message.entity.SmsSendRecord;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class SmsSendAppointService extends BaseService<SmsSendRecord, Long> {

	@Autowired
	private SmsSendAppointDao smsSendAppointDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	//操作日志dao
	@Autowired
	private UserOperationLogDao userOperationLogDao;

	@Override
	protected BaseDao<SmsSendRecord, Long> getEntityDao() {
		return smsSendAppointDao;
	}
	
//	//营销中心页面中----短信发送记录页面的跳转----会员群发记录,输入日期查询并获取总条数
//		public Integer findTotalCountByCondition(Date bTime,Date eTime,String phone ,Integer status,String userId ){
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("bTime", bTime);
//			map.put("eTime", eTime);
//			map.put("phone", phone);
//			map.put("status", status);
//			map.put("userId", userId);
//			Long totalCount = myBatisDao.findBy(SmsSendRecord.class.getName(), "findTotalCountByCondition", map);
//			Integer i = Integer.valueOf(totalCount.toString());
//			return i;
//			
//		}
		
//		//营销中心页面中----短信发送记录页面的跳转----会员群发记录,输入日期查询并获取分页的list集合
//		@SuppressWarnings("unused")
//		public Pagination findPaginationByCondition(String contextPath,Date bTime,Date eTime,Integer pageNo,Integer status,String userId){
//			//设置起始页
//			if(pageNo==null){
//				pageNo =1;	
//			}
//			//先设置每页显示的条数为3条
//			Integer currentRows = 3;
//			//计算出起始行数
//			Integer startRows = (pageNo-1)*currentRows;
//			//计算出总页数
//			Integer totalCount = findTotalCountByCondition(bTime,eTime,null ,status, userId);
//			Integer totalPage = (int) Math.ceil(1.0*totalCount/currentRows);
//			//调用dao层查询出每页显示的list<SmsSendRecord>集合
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("bTime", bTime);
//			map.put("eTime", eTime);
//			map.put("status", status);
//			map.put("startRows",startRows);
//			map.put("currentRows", currentRows);
//			map.put("userId", userId);
//			List<SmsSendRecord> smsSendRecordList = myBatisDao.findLimitList(SmsSendRecord.class.getName(), "findLimitListCondition", map, currentRows);
//			
//			
//			//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
//			//smsSendRecordList:每页显示的list集合或者当前页显示的list集合
//			Pagination pagination = new Pagination(pageNo, currentRows, totalCount, smsSendRecordList);
//			
//			StringBuilder params = new StringBuilder();
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
//			if(bTime!=null){
//				params.append("&beginTime=").append(sdf.format(bTime));
//			}
//			if(eTime!=null){
//				params.append("&endTime=").append(sdf.format(eTime));
//			}
//			if(status!=null){
//				if(status==2){
//					params.append("&status_page=").append("成功");
//				}
//				if(status==1){
//					params.append("&status_page=").append("失败");
//				}
//			}
//			
//			//拼接分页的后角标中的跳转路径与查询的条件
//			String url =contextPath+"/smsSendAppoint/marketingCenter/findSmsSendRecordByCondition";
//			pagination.pageView(url, params.toString());
//			return pagination;
//		}
//		/**
//		 * 查询要导出的全部内容
//		 * @param bTime 开始时间 
//		 * @param eTime 结束时间 
//		 * @param phone 要查询的手机号码
//		 * @param status 要查询的状态 成功 1   失败2  全部null
//		 * @param userId 卖家账号
//		 * @return
//		 */
//		public List<SmsSendRecord> findAllSmsSendRecord(
//												Date bTime, 
//												Date eTime, 
//												String phone,
//												Integer status,
//												String userId){
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("bTime", bTime);
//			map.put("eTime", eTime);
//			map.put("status", status);
//			map.put("phone", phone);
//			map.put("userId", userId);
//			List<SmsSendRecord> smsSendRecordList = myBatisDao.findList(SmsSendRecord.class.getName(), "findListCondition", map);
//			return smsSendRecordList;
//		}

//		/**
//		 * 通过电话查询数据
//		 */
//		@SuppressWarnings("unused")
//		public Pagination findPaginationByPhoneAndCondition(
//													String contextPath,
//													Date bTime, 
//													Date eTime, 
//													Integer pageNo, 
//													String phone,
//													Integer status,
//													String userId) {
//			//设置起始页
//			if(pageNo==null){
//				pageNo =1;	
//			}
//			//先设置每页显示的条数为3条
//			Integer currentRows = 3;
//			//计算出起始行数
//			Integer startRows = (pageNo-1)*currentRows;
//			//计算出总页数
//			Integer totalCount = findTotalCountByCondition(bTime,eTime, phone,status, userId );
//			Integer totalPage = (int) Math.ceil(1.0*totalCount/currentRows);
//			//调用dao层查询出每页显示的list<SmsSendRecord>集合
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("bTime", bTime);
//			map.put("eTime", eTime);
//			map.put("status", status);
//			map.put("phone", phone);
//			map.put("startRows",startRows);
//			map.put("currentRows", currentRows);
//			map.put("userId", userId);
//			List<SmsSendRecord> smsSendRecordList = myBatisDao.findLimitList(SmsSendRecord.class.getName(), "findLimitListByPhone", map, currentRows);
//			
//			
//			//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
//			//smsSendRecordList:每页显示的list集合或者当前页显示的list集合
//			Pagination pagination = new Pagination(pageNo, currentRows, totalCount, smsSendRecordList);
//			
//			StringBuilder params = new StringBuilder();
//			if(bTime!=null){
//				params.append("&beginTime=").append(bTime.toString());
//			}
//			if(eTime!=null){
//				params.append("&endTime=").append(eTime.toString());
//			}
//			if(status!=null){
//				if(status==2){
//					params.append("&status_page=").append("成功");
//				}
//				if(status==1){
//					params.append("&status_page=").append("失败");
//				}
//			}
//			if(phone!=null && !"".equals(phone)){
//				params.append("&phone=").append(phone);
//			}
//			//拼接分页的后角标中的跳转路径与查询的条件
//			String url =contextPath+"/smsSendAppoint/marketingCenter/findSmsSendRecordByCondition";
//			pagination.pageView(url, params.toString());
//			return pagination;
//			}
		
		
		
//		/**
//		 * 通过电话查询导出
//		 */
//
//		public Pagination findPaginationByPhoneIsExport(Date bTime, Date eTime,
//				String phone, Integer status,String userId) {
//			
//			//调用dao层查询出每页显示的list<SmsSendRecord>集合
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("bTime", bTime);
//			map.put("eTime", eTime);
//			map.put("status", status);
//			map.put("phone", phone);
//			map.put("userId", userId);
//			List<SmsSendRecord> smsSendRecordList = myBatisDao.findList(SmsSendRecord.class.getName(), "findPhonetListByExport", map);
//			Pagination pagination = new Pagination();
//			pagination.setList(smsSendRecordList);
//			return pagination;
//		}

//		/**
//		 * 通过条件查询导出
//		 */
//		public Pagination findPaginationByConditionIsExport(Date bTime,
//				Date eTime, Integer status,String userId) {
//			//调用dao层查询出每页显示的list<SmsSendRecord>集合
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("bTime", bTime);
//			map.put("eTime", eTime);
//			map.put("status", status);
//			map.put("userId", userId);
//			List<SmsSendRecord> smsSendRecordList = myBatisDao.findList(SmsSendRecord.class.getName(), "findConditionListByExport", map);
//			Pagination pagination = new Pagination();
//			pagination.setList(smsSendRecordList);
//			return pagination;
//		}

//		/**
//		 * 保存指定号码群发信息
//		 * 设置需要发送的内容
//		 */
//		public void saveSmsSendRecord(SmsSendRecord smsSendRecord,HttpServletRequest request,String userId) {
//			
//			//保存数据到数据库
//			smsSendAppointDao.save(smsSendRecord);
//			
//			
//			//添加操作日志数据
//			//操作日志
//			UserOperationLog op = new UserOperationLog();
//			op.setFunctions("指定号码群发添加");
//			op.setType("添加");
//			op.setDate(new Date());
//			op.setState("成功");
//			op.setRemark("指定号码群发添加");
//			op.setIpAdd(getIpAddress.getIpAddress(request));
//			op.setUserId(userId);
//			userOperationLogDao.save(op);
//			
//		}

		
	
}
