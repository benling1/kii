package s2jh.biz.shop.crm.goods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import s2jh.biz.shop.crm.goods.entity.CommodityGrouping;
import s2jh.biz.shop.crm.goods.vo.CommodityArtifact;
@Repository
public interface CommodityGroupingDao{
	
	/**
	 * 商品分组查询
	 * @param commodityGroupingVo
	 * @return
	 */
	List<CommodityGrouping> findCommodityGrouping(CommodityArtifact comm);
	
	
	/**
	 * Gg
	 * 查询分组名是否存在
	 * @param commodityGroupingVo
	 * @return
	 * Gg
	 */
	int findGroupName(CommodityArtifact vo);
	
	
	/**
	 * Gg
	 * 修改分组查询分组名是否存在
	 * @param comm
	 * @return
	 * Gg
	 */
	List<CommodityGrouping> findUpdateGroupName(CommodityArtifact comm);
	
    
	/**
	 * Gg
	 * 修改分组(新)
	 * @param vo
	 * Gg
	 */
	 void updateCommodityGroup(CommodityArtifact vo);
	 
	/**
	 * Gg
	 * 商品分组保存方法(新)
	 * @param commodityGrouping
	 * Gg
	 */
	void saveCommodityGroup(CommodityArtifact comm);
	
	/**
	 * 通過分組名查询分组ID
	 * @param vo
	 * @return
	 */
	Long findGroupId(CommodityArtifact comm);
    
	/**
	 * 商品分组主页面，查询总条数
	 * @param userId
	 * @return
	 */
	Long findTotalCountBy(CommodityArtifact comm);
	
	/**
	 * 设置页查询 分组名称
	 * @param comm
	 * @return 
	 */
	List<CommodityGrouping> findCommName(CommodityArtifact comm);
	
	/**
	 * 删除分组
	 * @param comm
	 */
	void deleteGroup(CommodityArtifact comm);
	
	/**
	 * 商品分组回显信息
	 * @param comm
	 * @return
	 */
	CommodityGrouping updateGroupEcho(CommodityArtifact comm);
    
	/**
	 * 查询所有商品的分组(商品缩写页面)
	 * @Title: listItemGroup 
	 * @param @param userId
	 * @param @return 设定文件 
	 * @return List<CommodityGrouping> 返回类型 
	 * @throws
	 */
	List<CommodityGrouping> listItemGroup(@Param("userId")String userId);
}


