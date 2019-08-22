package s2jh.biz.shop.crm.item.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.item.entity.ItemImport;

@Service
public class ItemImportService extends BaseService<ItemImport, Long>{
	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<ItemImport, Long> getEntityDao() {
		return null;
	}

	/**
	 * @Description: 查询用户所有商品描述及商品id
	 * @author HL
	 * @date 2017年11月17日 下午4:08:55
	 */
	public Map<String, Long> findItemTitleAndItemid(String userId) {
		Map<String, Long> map = new HashMap<String, Long>();
		List<ItemImport> list = myBatisDao.findList(ItemImport.class.getName(), "findItemTitleAndItemid", userId);
		if(null != list){
			for (ItemImport item : list) {
				map.put(item.getTitle().trim(), item.getNumIid());
			}
		}
		return map;
	}
	
	/**
	 * @Description: 导入订单拆分商品保存
	 * @author HL
	 * @date 2017年11月21日 上午11:02:59
	 */
	public void insertItemImportList(List<ItemImport> list){
		if(null != list && list.size()>0){
			myBatisDao.execute(ItemImport.class.getName(), "insertItemImportList", list);
		}
	}
	
	/**
	 * 查询导入商品随机分页
	 * @param map
	 * @return
	 */
	@Deprecated
	public List<Item> findListToItemImport(Map<String, Object> map) {
		
		Integer count = myBatisDao.findBy(ItemImport.class.getName(), "findListToItemImportCount", map);
		Integer currentRows = 100;
		Integer startRows = gainPage(count,currentRows);
		map.put("startRows",startRows);
		map.put("currentRows", currentRows);
		List<ItemImport> findList = myBatisDao.findList(ItemImport.class.getName(), "findListToItemImport", map);
		ArrayList<Item> list = new ArrayList<Item>();
		if(null != findList && findList.size()>0){
			for (ItemImport itemImport : findList) {
				Item item = new Item();
				item.setNick(itemImport.getNick());
				item.setNumIid(itemImport.getNumIid());
				item.setTitle(itemImport.getTitle());
				item.setPrice(itemImport.getPrice());
				item.setApproveStatus(itemImport.getApproveStatus());
				item.setUrl(itemImport.getUrl());
				list.add(item);
			}
		}
		return list;
	}

	/**
	 * 计算随机页码 
	 */
	@Deprecated
	private static Integer gainPage(Integer count,Integer currentRows) {
		Integer startRows = 0;
		try {
			if(count !=null){
				Integer	pageNum =(count%100)==0?(count/100):(count/100)+1;
				Random random = new Random();
				Integer page = random.nextInt(pageNum);
				if(page==0){
					page = 1;
				}
				startRows = (page-1)*currentRows;
			}
		} catch (Exception e) {
		}
		return startRows;
	}
	
	
	
	/**
	 * @Description: 查询同步商品和导入拆分商品，计算分页数据 
	 * @author HL
	 * @date 2017年11月28日 下午4:37:12
	 */
	public Map<String, Object> findItemData(Map<String, Object> map) {
		int totalCount = myBatisDao.findBy(ItemImport.class.getName(), "findItemDatasCount", map);
		//计算出总页数
		Integer currentRows = (Integer) map.get("currentRows");
		Integer totalPage = (int) Math.ceil(1.0*totalCount/currentRows);
		
		List<ItemImport> list = myBatisDao.findList(ItemImport.class.getName(), "findItemDatas", map);
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("totalPage", totalPage);
		result.put("list", list);
		return result;
	}

	
	/**
	 * @Description: 通过条件查询同步商品和导入拆分商品List
	 * @author HL
	 * @date 2017年11月28日 下午5:58:16
	 */
	public List<ItemImport> findItemImportAndItem(String itemId, String userNick) {
		if(null != itemId && !"".equals(itemId)){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("numIid", itemId.split(","));
			map.put("userNick", userNick);
			List<ItemImport> list = myBatisDao.findList(ItemImport.class.getName(), "findItemDatasList", map);
			return list;
		}
		return null;
	}

	/**
	  * @Description: 如果订单重复就删除该订单的商品
	  * @author Mr.H
	  * @date 2017年12月7日 下午10:55:58
	 */
	public synchronized void batchDeleteImportItems(Set<Long> list) {
		try {
			myBatisDao.execute(ItemImport.class.getName(), "batchDeleteImportItems", new ArrayList<Long>(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}	


