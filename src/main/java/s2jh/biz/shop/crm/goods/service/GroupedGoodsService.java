package s2jh.biz.shop.crm.goods.service;
import java.util.List;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.goods.dao.GroupedGoodsDao;
import s2jh.biz.shop.crm.goods.entity.GroupedGoods;
import s2jh.biz.shop.crm.goods.vo.CommodityArtifact;

@Service
@Transactional
public class GroupedGoodsService /*extends BaseService<GroupedGoods, Long>*/{
	
	@Autowired
	private GroupedGoodsDao groupedGoodsDao;
	
	@Autowired
	private CommodityGroupingService commodityGroupingService;

	/*@Override
	protected BaseDao<GroupedGoods, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	/**
	 * 新
	 * @param vo
	 */
	public void deleteGroupedGoods(CommodityArtifact artifact){
		groupedGoodsDao.deleteGroupedGoods(artifact);
	}
	
	/**
	 * 以分组页面弹窗查询
	 * @param vo
	 * @return
	 */
	public List<GroupedGoods> findGroupedGoods(CommodityArtifact artifact){
		List<GroupedGoods> list = groupedGoodsDao.findGroupedGoods(artifact);
		return list;
	}
	
	/**
	 * 修改分组查询numIid 回显
	 * @param artifact
	 * @return
	 */
	public List<String> updateGroupEchoNumIid(CommodityArtifact artifact){
		//List<String> numIidList = groupedGoodsDao.updateGroupEchoNumIid(artifact);
		return groupedGoodsDao.updateGroupEchoNumIid(artifact);
	}
	
}

