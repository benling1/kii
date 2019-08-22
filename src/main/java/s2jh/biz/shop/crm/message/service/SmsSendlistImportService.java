package s2jh.biz.shop.crm.message.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.message.dao.SmsSendlistImportDao;
import s2jh.biz.shop.crm.message.entity.SmsSendlistImport;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class SmsSendlistImportService extends BaseService<SmsSendlistImport, Long>  {
	@Autowired
	private SmsSendlistImportDao smsSendlistImportDao;

	@Autowired
	private MyBatisDao myBatisDao;
	
	//操作日志dao
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	@Override
	protected BaseDao<SmsSendlistImport, Long> getEntityDao() {
	
		return null;
	}
	
	//营销中心页面中----短信发送记录页面的跳转----会员群发记录,输入日期查询并获取总条数
	public Integer findTotalCountBySmsSendlist(Date bTime,Date eTime,String fileName,String userId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("fileName", fileName);
		map.put("userId", userId);
		Long totalCount = myBatisDao.findBy(SmsSendlistImport.class.getName(), "findTotalCountBySendlist", map);
		Integer i = Integer.valueOf(totalCount.toString());
		return i;
		
	}
	
	
	/**
	 * 查询导入的电话记录
	 */
	public List<SmsSendlistImport> findPaginationBySmsSendlist(String contextPath,
			Date bTime, Date eTime, Integer pageNo, String fileName,String userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("fileName", fileName);
		map.put("userId", userId);
		List<SmsSendlistImport> smsSendlistImportList = myBatisDao.findList(SmsSendlistImport.class.getName(), "findLimitListBySendlist", map);
		return smsSendlistImportList;
		
	}
	
	
	/**
	 * 存储导入的记录
	 * @param request 
	 */
	public Long insertSmsSendlistImport(SmsSendlistImport sendlistImport, HttpServletRequest request) {
		
		myBatisDao.execute(SmsSendlistImport.class.getName(), "insertSmsSendlistImport", sendlistImport);
			//操作日志
			UserOperationLog op = new UserOperationLog();
			op.setFunctions("导入发送名单");
			op.setType("添加");
			op.setDate(new Date());
			op.setState("成功");
			op.setRemark("导入发送名单添加");
			op.setUserId(sendlistImport.getUserId());
			op.setIpAdd(getIpAddress.getIpAddress(request));
			userOperationLogDao.save(op);
			return sendlistImport.getId();
	}

	/**
	 * 通过id指定删除的导入数据
	 * @param request 
	 * @param userId 
	 */
	public void deleteSmsSendlistById(String sendlistId, HttpServletRequest request, String userId) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("id", Integer.parseInt(sendlistId));
		
		//添加操作日志
		UserOperationLog op = new UserOperationLog();
		try {
			//调用SmsSendRecord-- 删除短信发送记录
			myBatisDao.execute(SmsSendlistImport.class.getName(), "deleteSmsSendlistById", map);
			op.setState("成功");
			
		} catch (Exception e) {
			op.setState("失败");
		}

		//操作日志数据补全
		op.setFunctions("导入发送名单");
		op.setType("删除");
		op.setDate(new Date());
		op.setRemark("导入发送名单删除");
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setUserId(userId);
		userOperationLogDao.save(op);
		
	}
	
	/**
	 * 通过id查询导入的电话号码
	 */
	public SmsSendlistImport findImportPhoneById(String id) {
		SmsSendlistImport smsSendlistImport = myBatisDao.findBy(SmsSendlistImport.class.getName(), "findImportPhoneById", id);
		return smsSendlistImport;
	}

	public SmsSendlistImport findImportPhoneByIdIsState(String id) {
		SmsSendlistImport smsSendlistImport = myBatisDao.findBy(SmsSendlistImport.class.getName(), "findImportPhoneByIdIsState", id);
		return smsSendlistImport;
	}
	
	//黑名单导入 文件查询
	public List<SmsSendlistImport> findImImportList(String userId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		List<SmsSendlistImport> smsSendlistImportList = myBatisDao.findList(SmsSendlistImport.class.getName(), "findimportBlack", map);
		return smsSendlistImportList;
	}
	
	//黑名单导入文件删除
	public int removeSmsInport(Integer id,String userId){
		Map<String,Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("userId", userId);
		return myBatisDao.execute(SmsSendlistImport.class.getName(), "deleteSmsImport", parameters);
	}
}
