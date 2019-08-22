package s2jh.biz.shop.crm.seller.service;

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

import s2jh.biz.shop.crm.member.entity.MemberLevelSetting;
import s2jh.biz.shop.crm.seller.dao.SellerGroupDao;
import s2jh.biz.shop.crm.seller.entity.SellerGroup;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class SellerGroupService extends BaseService<SellerGroup, Long> {

	@Autowired
	private SellerGroupDao sellerGroupDao;

	@Autowired
	private MyBatisDao mybatisDao;

	@Override
	protected BaseDao<SellerGroup, Long> getEntityDao() {
		return sellerGroupDao;
	}

	// 根据卖家编号查询所有分组信息
	public Pagination findAllSellerGroup(String userId, String contextPath,
			Integer pageNo, String memberType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("memberType", memberType);
		// 设置起始页
		if (pageNo == null) {
			pageNo = 1;
		}
		// 先设置每页显示的条数为10条
		Integer currentRows = 10;

		// 计算出起始行数
		Integer startRows = (pageNo - 1) * currentRows;

		// 计算出总页数
		int count = findSellerGroupCount(map);
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);

		List<SellerGroup> list = mybatisDao.findList(
				SellerGroup.class.getName(), "findAllSellerGroup", map);

		// 使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
		// smsSendRecordList:每页显示的list集合或者当前页显示的list集合
		Pagination pagination = new Pagination(pageNo, currentRows, count, list);

		StringBuilder params = new StringBuilder();
		if (memberType != null) {
			params.append("&memberType=").append(memberType);
		}

		// 拼接分页的后角标中的跳转路径与查询的条件
		String url = contextPath + "/sellerGroup/findSellerGroup";
		pagination.pageView(url, params.toString());
		return pagination;
	}

	// 根据卖家编号查询所有分组数量
	public int findSellerGroupCount(Map<String, Object> map) {
		int count = mybatisDao.findBy(SellerGroup.class.getName(),
				"findSellerGroupCount", map);
		return count;
	}

	// 根据分组编号删除会员分组
	public int deleteSellerGroup(Integer groupId) {
		int date = mybatisDao.execute(SellerGroup.class.getName(),
				"deleteSellerGroup", groupId);
		return date;
	}

	// 添加卖家设置的分组
	public Long addSellerGroup(SellerGroup sel) {
		mybatisDao.execute(SellerGroup.class.getName(), "addSellerGroup", sel);
		if (sel.getGroupId() != null) {
			return sel.getGroupId();
		} else {
			List<SellerGroup> findList = mybatisDao.findList(SellerGroup.class.getName(), "findGroupIdLast", null);
			if(findList != null && findList.get(0).getGroupId() != null){
				return findList.get(0).getGroupId();
			}else{
				return null;
			}
		}
	}

	// 根据分组编号修改数据
	public int updateSelleGroup(SellerGroup sg) {
		int date = mybatisDao.execute(SellerGroup.class.getName(),
				"updateSellerGroup", sg);
		return date;
	}

	// 根据分组编号查询数据
	public SellerGroup findSellerGroup(String id) {
		Long groupId = Long.parseLong(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		SellerGroup sg = mybatisDao.findBy(SellerGroup.class.getName(),
				"findSellerGroup", map);
		return sg;
	}
	//根据分组编号查询分组数据
	public SellerGroup findSellerGroupInfo(String Id){
		Long groupId = Long.parseLong(Id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		SellerGroup sg = mybatisDao.findBy(SellerGroup.class.getName(),
				"findSellerGroupInfo", map);
		return sg;
	}

	// 查询所有默认分组信息
	public List<SellerGroup> findDefaultSellerGroup(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<SellerGroup> list = mybatisDao.findList(
				SellerGroup.class.getName(), "findDefaultSellerGroup", map);
		return list;
	}

	/**
	 * 添加用户默认分组
	 * 
	 * @param userId
	 */
	public void addDefaultGroup(String userId) {
		SellerGroup sg = new SellerGroup();
		sg.setGroupName("普通会员");
		sg.setStatus("1");
		sg.setUserId(userId);
		sg.setGroupCreate(new Date());
		sg.setMemberCount(0);
		sg.setMemberType("1");
		sg.setRemark("普通会员");
		mybatisDao.execute(SellerGroup.class.getName(), "addSellerGroup", sg);
		
		
		HashMap<String,Object> maps  = new HashMap<String,Object>();
		 maps.put("userId", userId);
		 maps.put("memberLevel", "1");
		 MemberLevelSetting  memberLevel = mybatisDao.findBy(MemberLevelSetting.class.getName(), "findMemberLevelByLevelAndUserId", maps);
		 if(null!=memberLevel){
			 Map<String, Object> mapParam = new HashMap<String, Object>();
			 mapParam.put("id", memberLevel.getId());
			 mapParam.put("ctime", new Date());
			 mapParam.put("hierarchy", "true");
			 mapParam.put("groupId", sg.getGroupId());
			 mybatisDao.execute("s2jh.biz.shop.crm.member.entity.MemberLevelSetting","updateSetting", mapParam);
		 }
		   
		
		SellerGroup sg1 = new SellerGroup();
		sg1.setGroupName("高级会员");
		sg1.setStatus("1");
		sg1.setUserId(userId);
		sg1.setGroupCreate(new Date());
		sg1.setMemberCount(0);
		sg1.setMemberType("1");
		sg1.setRemark("高级会员");
		mybatisDao.execute(SellerGroup.class.getName(), "addSellerGroup", sg1);
		maps.put("memberLevel", "2");
		MemberLevelSetting  memberLevel1 = mybatisDao.findBy(MemberLevelSetting.class.getName(), "findMemberLevelByLevelAndUserId", maps);
		 if(null!=memberLevel1){
			 Map<String, Object> mapParam1 = new HashMap<String, Object>();
			 mapParam1.put("id", memberLevel1.getId());
			 mapParam1.put("groupId", sg1.getGroupId());
			 mapParam1.put("ctime", new Date());
			 mapParam1.put("hierarchy", "true");
			 mybatisDao.execute("s2jh.biz.shop.crm.member.entity.MemberLevelSetting","updateSetting", mapParam1);
		 } 
		SellerGroup sg2 = new SellerGroup();
		sg2.setGroupName("VIP会员");
		sg2.setStatus("1");
		sg2.setUserId(userId);
		sg2.setGroupCreate(new Date());
		sg2.setMemberCount(0);
		sg2.setMemberType("1");
		sg2.setRemark("VIP会员");
		mybatisDao.execute(SellerGroup.class.getName(), "addSellerGroup", sg2);

		maps.put("memberLevel", "3");
		MemberLevelSetting  memberLevel2 = mybatisDao.findBy(MemberLevelSetting.class.getName(), "findMemberLevelByLevelAndUserId", maps);
		 if(null!=memberLevel2){
			 Map<String, Object> mapParam2 = new HashMap<String, Object>();
			 mapParam2.put("id", memberLevel2.getId());
			 mapParam2.put("groupId", sg2.getGroupId());
			 mapParam2.put("ctime", new Date());
			 mapParam2.put("hierarchy", "true");
			 mybatisDao.execute("s2jh.biz.shop.crm.member.entity.MemberLevelSetting","updateSetting", mapParam2);
		 }
		
		SellerGroup sg3 = new SellerGroup();
		sg3.setGroupName("至尊VIP会员");
		sg3.setStatus("1");
		sg3.setUserId(userId);
		sg3.setGroupCreate(new Date());
		sg3.setMemberCount(0);
		sg3.setMemberType("1");
		sg3.setRemark("至尊VIP会员");
		mybatisDao.execute(SellerGroup.class.getName(), "addSellerGroup", sg3);
		
		maps.put("memberLevel", "4");
		
		MemberLevelSetting  memberLevel3 = mybatisDao.findBy(MemberLevelSetting.class.getName(), "findMemberLevelByLevelAndUserId", maps);
		if(null!=memberLevel3){
			 Map<String, Object> mapParam3 = new HashMap<String, Object>();
			 mapParam3.put("id", memberLevel3.getId());
			 mapParam3.put("groupId", sg3.getGroupId());
			 mapParam3.put("ctime", new Date());
			 mapParam3.put("hierarchy", "true");
			 mybatisDao.execute("s2jh.biz.shop.crm.member.entity.MemberLevelSetting","updateSetting", mapParam3);
		}
	}
	
	/**
	  * 创建人：邱洋
	  * @Title: 根据卖家编号查询所有分组（id,groupId,groupName）
	  * @date 2017年4月7日--下午2:38:25 
	  * @return List<SellerGroup>
	  * @throws
	 */
	public List<SellerGroup> getAllSellerGroup(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<SellerGroup> list = mybatisDao.findList(SellerGroup.class.getName(), "findAllGroup", map);
		return list;
	}
	
	/**
	  * 创建人：邱洋
	  * @Title: 根据用户昵称和会员分组名查询分组信息
	  * @date 2017年5月3日--上午11:13:20 
	  * @return Boolean
	  * @throws
	 */
	public List<SellerGroup> existenceSellerGroupInfo(String userId,String groupName){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("groupName", groupName);
		List<SellerGroup> list = mybatisDao.findList(SellerGroup.class.getName(), "existenceSellerGroupInfo", map);
		return list;
	}
}
