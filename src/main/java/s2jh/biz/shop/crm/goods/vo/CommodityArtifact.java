package s2jh.biz.shop.crm.goods.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @deprecated：创建分组与查询分组商品条件的vo
 * @author Administrator
 *
 */
@Getter
@Setter
public class CommodityArtifact implements Serializable {

	private static final long serialVersionUID = -7396610260604460226L;
	
	/**
	 * 分组id
	 * */
	private Long groupId;
	
	/**
	 * 所属用户
	 * */
	private String userId;
	
	/**
	 * 分组名称
	 * */
	private String groupName;
	
	/**
	 * 分组的商品id
	 */
	private String[] numIid;
	
	/**
	 * 分组的商品数量
	 *
	*/
	private Integer commodityNum;
	
	/**
	 * 分组的备注
	 * */
	private String remark;
	
	/**
	 * 分组的修改时间
	 */
	private Date modifyTime;
	
	
	/**
	 * 分组的id
	 * */
	private Long queryGroupId;
	
	/**
	 * 起始行
	 */
	private Integer startRows;
	
	/**
	 * 
	 */
	private Integer currentRows;
	
	//========================================
	//========================================
	
	/**
	 * 商品数字Id
	 * */
	private String queryNumIid;
	
	/**
	 * 商品关键字
	 * */
	private String title;
	
	/**
	 * 是否上架上架
	 * */
	private String queryIshow;
	
	/**
	 * 第几页
	 * */
	private Integer pageNo;
	
	
}
