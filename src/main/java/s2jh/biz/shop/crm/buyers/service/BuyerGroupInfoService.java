package s2jh.biz.shop.crm.buyers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.buyers.dao.BuyerGroupInfoDao;
import s2jh.biz.shop.crm.buyers.entity.BuyerGroupInfo;

@Service
@Transactional
public class BuyerGroupInfoService extends BaseService<BuyerGroupInfo, Long>{
	
	@Autowired
	private BuyerGroupInfoDao buyerGroupInfoDao;
	
	@Autowired
	private MyBatisDao mybatisDao;
	
	@Override
	protected BaseDao<BuyerGroupInfo, Long> getEntityDao() {
		return buyerGroupInfoDao;
	}
	
	//根据会员分组编号查询所有的会员编号
	public List<BuyerGroupInfo> findBuyerInfo(String groupId,String userId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("groupId",groupId);
		map.put("userId",userId);
		List<BuyerGroupInfo> list = mybatisDao.findList(BuyerGroupInfo.class.getName(), "findBuyerGroupInfo", map);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	//添加会员跟会员分组之间的绑定关系
	public int addBuyerGroupInfo(BuyerGroupInfo bgf){
		int date = mybatisDao.execute(BuyerGroupInfo.class.getName(), "addBuyerGroupInfo", bgf);
		return date;
	}
	
	//根据编号删除会员分组关联信息
	public int delBuyerGroupInfo(BuyerGroupInfo bgf){
		int date = mybatisDao.execute(BuyerGroupInfo.class.getName(), "delBuyerGroupInfo", bgf);
		return date;
	}
}
