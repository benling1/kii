package s2jh.biz.shop.crm.goods.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import s2jh.biz.shop.crm.goods.entity.CommodityGrouping;
import s2jh.biz.shop.crm.goods.entity.GroupedGoods;
import s2jh.biz.shop.crm.item.entity.Item;

/**
 * @deprecated:商品分组返回搜索数据的vo
 * @author Administrator
 *
 */
@Getter
@Setter
public class CommodityViewer<T> implements Serializable{
	
	private static final long serialVersionUID = -1711150185983921371L;

	/**
	 * 商品分组：左侧搜索栏数据
	 * */
	private List<T> searchData;
	//private List<Item> searchData;
	
	/**
	 * 右侧数据
	 * */
	private List<GroupedGoods> selectData;
	
	/**
	 * 设置页 numIid不为空时
	 */
	private List<Item> itemData;
	
	/**
	 * 商品主页面查询
	 */
	private List<CommodityGrouping> groupedData;
	
	/**
	 * 当前第几页
	 * */
	private Integer currentPage;
	
	/**
	 * 总条数
	 * */
	private Long totalCout;
	
	/**
	 * 总页数
	 */
	private Integer totalPage;
}
