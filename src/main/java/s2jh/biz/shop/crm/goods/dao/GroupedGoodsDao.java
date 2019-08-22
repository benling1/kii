package s2jh.biz.shop.crm.goods.dao;

import java.util.List;

import s2jh.biz.shop.crm.goods.entity.GroupedGoods;
import s2jh.biz.shop.crm.goods.vo.CommodityArtifact;


public interface GroupedGoodsDao{
	
	/**
	 * Gg
	 * 查询 已分组的商品(加条件)
	 * @param groupedGoodsVo
	 * @return
	 * Gg
	 */
	List<GroupedGoods> findGroupedGoods(CommodityArtifact artifact);
	
	
	/**
	 * Gg
	 * 保存分组商品的numIid
	 * @param groupedGoods
	 * Gg
	 */
	void saveGroupedGoods(GroupedGoods groupedGoods);
	
	/**
	 * 删除分组商品信息
	 */
	void deleteGroupedGoods(CommodityArtifact artifact);
	
	
	/**
	 * Gg
	 * 批量保存商品信息
	 * @param list
	 * Gg
	 */
	void saveGroupedGoodsBatch(List<GroupedGoods> list);
	
	/**
	 * 设置页查询分组名称
	 * @param vo
	 */
	void findGroupName(CommodityArtifact vo);
	
	/**
	 * 查询修改分组回显的NumIid
	 * @param artifact
	 * @return
	 */
	List<String> updateGroupEchoNumIid(CommodityArtifact artifact);
}


