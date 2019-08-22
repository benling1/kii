package s2jh.biz.shop.crm.goods.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.goods.dao.CommodityGroupingDao;
import s2jh.biz.shop.crm.goods.dao.GroupedGoodsDao;
import s2jh.biz.shop.crm.goods.entity.CommodityGrouping;
import s2jh.biz.shop.crm.goods.entity.GroupedGoods;
import s2jh.biz.shop.crm.goods.vo.CommodityArtifact;
import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.item.service.ItemService;

@Service
@Transactional
public class CommodityGroupingService /*extends BaseService<CommodityGrouping, Long>*/{
	
	@Autowired
	private GroupedGoodsService groupedGoodsService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private CommodityGroupingDao commodityGroupingDao;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private GroupedGoodsDao groupedGoodsDao;

	/*@Override
	protected BaseDao<CommodityGrouping, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	/**
	 * Gg
	 * 商品分组主页面查询
	 * @param userId
	 * @param pageNo
	 * @param contextPath
	 * @return
	 * Gg
	 */
	public Map<String,Object> findGroupedPagination(CommodityArtifact comm){
		//查询总条数
		Long totalCount = findTotalCountBy(comm);
	    //设置每页显示5条数据
	    Integer currentRows =5;
	    //计算出起始行数
	    Integer startRows = (comm.getPageNo()-1)*currentRows;
	    //计算出总页数
	    Integer totalPage = (int) Math.ceil(1.0*totalCount/currentRows);
	    comm.setCurrentRows(currentRows);
	    comm.setStartRows(startRows);
		List<CommodityGrouping> list = commodityGroupingDao.findCommodityGrouping(comm);
		 Map<String,Object> result = null;
		 if(list!=null && !list.isEmpty()){
			 result = new HashMap<String, Object>();
			 result.put("data", list);
			 result.put("totalCount", totalCount);
			 result.put("totalPage", totalPage);
			 result.put("pageNo", comm.getPageNo());
		 }
		return result;
	}
	
	
	/**
	 * Gg
	 * 查询分组总条数
	 * @param userId
	 * @return
	 * Gg
	 */
	public Long findTotalCountBy(CommodityArtifact comm){
		Long totalCount = commodityGroupingDao.findTotalCountBy(comm);
		return totalCount;
	}
	
	/**
	 * 查询分组名是否存在
	 * @param commodityGroupingVo
	 * @return
	 */
	public int findGroupName(CommodityArtifact vo){
		int count = commodityGroupingDao.findGroupName(vo);
		return count;
	}
	
	/**
	 * Gg
	 * 修改分组查询分组名是否重复
	 * @param vo
	 * @return
	 * Gg
	 */
	public List<CommodityGrouping> findUpdateGroupName(CommodityArtifact vo){
		List<CommodityGrouping> commodityGroupList = commodityGroupingDao.findUpdateGroupName(vo);
		return commodityGroupList;
	}
	
	
	/**新
	 * 修改(修改分组信息，修改分组的商品信息)
	 * @param vo
	 */
	public void updateCommodityGroup(CommodityArtifact comm) {
		//sql更新分组信息
		commodityGroupingDao.updateCommodityGroup(comm);
		//删除此分组的商品信息
		try {
			groupedGoodsService.deleteGroupedGoods(comm);
			//保存更新商品信息
			this.saveGroupedGoods(comm,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Gg
	 * 保存分组信息以及分组的商品信息
	 * @param vo
	 * Gg
	 */
	
	public void saveCommodityGroup(CommodityArtifact comm){
		//sql保存分组信息
		commodityGroupingDao.saveCommodityGroup(comm);
		//开始保存分组的商品信息，1查询 grouId
		Long groupId = this.findGroupId(comm);
		//保存分组信息
		if(groupId!=null){
			this.saveGroupedGoods(comm,groupId);
		}
		
	}
	
	/**
	 * Gg
	 * 保存商品信息
	 * @param vo
	 * Gg
	 */
	public void saveGroupedGoods(CommodityArtifact comm,Long groupId){
		List<GroupedGoods> list = new ArrayList<GroupedGoods>();
		GroupedGoods groupedGoods =null;
		for (String numIid  : comm.getNumIid()) {
			Item item = itemService.findItem(comm.getUserId(), numIid);
			if(item!=null){
				groupedGoods =  new GroupedGoods();
				groupedGoods.setUserId(comm.getUserId());
				groupedGoods.setNumIid(Long.valueOf(numIid));
				groupedGoods.setTitle(item.getTitle());
				groupedGoods.setApproveStatus(item.getApproveStatus());
				groupedGoods.setPrice(item.getPrice());
				groupedGoods.setUrl(item.getUrl());
				if(groupId!=null){
					groupedGoods.setGroupId(groupId);
				}else{
					groupedGoods.setGroupId(comm.getGroupId());
				}
				list.add(groupedGoods);
			}
		}
		if(!list.isEmpty())
			try {
				groupedGoodsDao.saveGroupedGoodsBatch(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Gg
	 * 添加商品是查询分组Id(新)
	 * @param vo
	 * @return
	 * Gg
	 */
	public Long findGroupId(CommodityArtifact comm){
		Long groupId=commodityGroupingDao.findGroupId(comm);
		return groupId;
	} 
	
	/**
	 * 设置页查询分组名称
	 * @param comm
	 * @return
	 */
	public List<CommodityGrouping> findCommName(CommodityArtifact comm){
		List<CommodityGrouping> commNameList = commodityGroupingDao.findCommName(comm);
		return commNameList;
	}
	
	/**
	 * 删除分组
	 * @param comm
	 */
	public void deleteGroup(CommodityArtifact comm){
		try {
			groupedGoodsService.deleteGroupedGoods(comm);
			//删除次groupId分组的商品
			commodityGroupingDao.deleteGroup(comm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 商品分组回显信息
	 * @param comm
	 * @return
	 */
	public CommodityGrouping updateGroupEcho(CommodityArtifact comm){
		CommodityGrouping commodityGrouping =commodityGroupingDao.updateGroupEcho(comm);
		return commodityGrouping;
	}
	
	/**
	 * 查询所有商品的分组(商品缩写页面)
	 * @Title: listItemGroup 
	 * @param @return 设定文件 
	 * @return List<CommodityGrouping> 返回类型 
	 * @throws
	 */
	public List<CommodityGrouping> listItemGroup(@Param("userId")String userId){
		if(null == userId || "".equals(userId)){
			return null;
		}
		List<CommodityGrouping> itemGroupings = commodityGroupingDao.listItemGroup(userId);
		return itemGroupings;
	}
}


