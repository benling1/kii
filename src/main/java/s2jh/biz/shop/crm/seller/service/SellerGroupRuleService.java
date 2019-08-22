package s2jh.biz.shop.crm.seller.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.seller.dao.SellerGroupRuleDao;
import s2jh.biz.shop.crm.seller.entity.SellerGroupRule;

@Service
@Transactional
public class SellerGroupRuleService extends BaseService<SellerGroupRule, Long> {
	
	@Autowired
	private SellerGroupRuleDao sellerGroupRuleDao;
	
	@Autowired
	private MyBatisDao mybatisDao;
	
	@Override
	protected BaseDao<SellerGroupRule, Long> getEntityDao() {
		return sellerGroupRuleDao;
	}
	
	//添加分组规则条件
	public Long addSellerGroupRule(SellerGroupRule ser){
		int date = mybatisDao.execute(SellerGroupRule.class.getName(), "addSellerGroupRule", ser);
		if(date!=0&&ser.getId()!=null){
			return ser.getId();
		}else{
			return null;
		}
	}
	
	//修改分组规则条件
	public int updateSellerGroupRule(SellerGroupRule ser){
		int date =mybatisDao.execute(SellerGroupRule.class.getName(), "updateSellerGroupRule", ser);
		return date;
	}
	
	//根据RuleId查询数据
	public SellerGroupRule querySellerGroup(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id",id);
		SellerGroupRule sgr=mybatisDao.findBy(SellerGroupRule.class.getName(), "querySellerGroup", map);
		return sgr;
	}
	
	/**
	 * 根据主键id删除会员分组设置的条件
	 * @param ruleId
	 * @return
	 */
	public int delSellerGroupRule(Integer ruleId){
		int status = mybatisDao.execute(SellerGroupRule.class.getName(), "delSellerGroupRule", ruleId);
		return status;
	}
	
	/**
	  * 创建人：邱洋
	  * @Title: 根据分组groupId查询组规则
	  * @date 2017年4月7日--下午6:06:11 
	  * @return SellerGroupRule
	  * @throws
	 */
	public SellerGroupRule findSellerGroupRule(String groupId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("groupId",groupId);
		SellerGroupRule sgr=mybatisDao.findBy(SellerGroupRule.class.getName(), "findSellerGroupRule", map);
		return sgr;
	}
}
