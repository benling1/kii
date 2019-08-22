package s2jh.biz.shop.crm.goods.web;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import s2jh.biz.shop.crm.goods.entity.CommodityGrouping;
import s2jh.biz.shop.crm.goods.entity.GroupedGoods;
import s2jh.biz.shop.crm.goods.service.CommodityGroupingService;
import s2jh.biz.shop.crm.goods.service.GroupedGoodsService;
import s2jh.biz.shop.crm.goods.vo.CommodityArtifact;
import s2jh.biz.shop.crm.goods.vo.CommodityViewer;
import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.utils.JsonUtil;

@Controller
@RequestMapping(value = "/commodity")
public class CommodityGroupingController extends BaseController{
	
	private static final Log logger = LogFactory.getLog(CommodityGroupingController.class);
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private GroupedGoodsService groupedGoodsService;
	
	@Autowired
	private CommodityGroupingService commodityGroupingService;
	
	/**
	 * 保存修改分组已经分组商品
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/saveGrouped",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String saveOrupdateGrouped(HttpServletRequest request,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "哈数据库等哈";
		CommodityArtifact  comm= null;
		try {
			comm = parseParamToObj(params);
		} catch (Exception e) {
			logger.error("***************解析参数异常"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(comm==null) 
			return  rsMap(101, "操作失败").put("status", false).toJson();
		if(comm.getNumIid()==null || comm.getCommodityNum() != comm.getNumIid().length)
			return rsMap(101, "操作失败").put("status", false).toJson();
		comm.setUserId(userId);
		try {
			if(comm.getGroupId()!=null){
				comm.setModifyTime(new Date());
				//判断groupName是否和其他组名重复
				List<CommodityGrouping> commodityGroupList = commodityGroupingService.findUpdateGroupName(comm);
				if(commodityGroupList!=null && commodityGroupList.size()>0){
					CommodityGrouping commodityGrouping = commodityGroupList.get(0);
					if(!comm.getGroupId().toString().equals(String.valueOf(commodityGrouping.getId()))){
						return  rsMap(101, "操作失败").put("status", "分组名重复！").toJson();
					}
				}
				commodityGroupingService.updateCommodityGroup(comm);
			}else{//2不存在-保存分组以及分组的商品
				//2.1 保存新的分组时判断groupName 是否在数据库中存在
				if(comm.getGroupName()!=null && !"".equals(comm.getGroupName())){
					boolean result = this.findGroupName(comm.getGroupName(), userId);
					if(!result){
						logger.error("*************用户"+userId+"修改分组失败，分组名重复!***********");
						return  rsMap(101, "操作失败").put("status", "分组名不能重复，请重新操作！").toJson();
					}
				}
				commodityGroupingService.saveCommodityGroup(comm);
			}
		} catch (Exception e) {
			logger.error("********用户"+userId+"保存或者更新商品分组异常!*******");
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).toJson(); 
	}
	
	
	/**
	 * 商品左右侧数据查询
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/showItemData",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String showItemData(HttpServletRequest request,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "哈数据库等哈";
		CommodityArtifact  comm= null;
		try {
			comm = parseParamToObj(params);
		} catch (Exception e) {
			logger.error("******用户"+userId+"展开商品分组弹框,解析参数异常!"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(comm ==null) return  rsMap(101, "操作失败").put("status", false).toJson();
		comm.setUserId(userId);
		/**
		 * 1,判断comm是否包含groupId
		 * 
		 * 包含：从修改页面进入，需要填充右侧数据
		 * 
		 * 不包含：从创建分组页面进入，不需要填充右侧数据
		 * 
		 * */
		@SuppressWarnings("rawtypes")
		CommodityViewer viewer = new CommodityViewer();
		List<GroupedGoods> selectData =null;
		if(comm.getGroupId()!=null){
			//1查询已分组的商品数据
			selectData = groupedGoodsService.findGroupedGoods(comm);
			viewer.setSelectData(selectData);
		}
		//根据条件查询出左侧数据
		Map<String,Object> result = itemService.findItemPagination(comm);
		if(result!=null){
			viewer.setCurrentPage(comm.getPageNo());
			viewer.setSearchData((List<Item>) result.get("data"));
			viewer.setTotalCout((Long) result.get("totalCount"));
			viewer.setTotalPage((Integer) result.get("totalPage"));
		}
		 return rsMap(100, "操作成功").put("status", true).put("data",viewer).toJson();
	}
	
	/**
	 * 修改回显接口
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/updateGroupEcho",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String updateEcho(HttpServletRequest request,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "哈数据库等哈";
		CommodityArtifact  comm= null;
		try {
			comm = parseParamToObj(params);
		} catch (Exception e) {
			logger.error("***************用户"+userId+"展开商品分组弹框,解析参数异常!*****"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(comm ==null) return  rsMap(101, "操作失败").put("status", false).toJson();
		if(comm.getGroupId()==null) rsMap(101, "操作失败").put("status", false).toJson();
		comm.setUserId(userId);
		/*查询商品分组回显信息*/
		CommodityGrouping commodityGrouping = commodityGroupingService.updateGroupEcho(comm);
		/*查询此分组的商品 numIid*/
		List<String> numIids = groupedGoodsService.updateGroupEchoNumIid(comm);
		return rsMap(100, "操作成功").put("status", true).put("data",commodityGrouping).put("numIid", numIids).toJson();
	}
	
	/**
	 * 商品分组主页面查询
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/groupedShow",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String groupedShow(HttpServletRequest request,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		CommodityArtifact  comm= null;
		try {
			comm = parseParamToObj(params);
		} catch (Exception e) {
			logger.error("***************展开商品分组弹框,解析参数异常"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(comm ==null) return  rsMap(101, "操作失败").put("status", false).toJson();
		comm.setUserId(userId);
		Map<String ,Object> groupedList = commodityGroupingService.findGroupedPagination(comm);
		if(groupedList!=null){
			return rsMap(100, "操作成功").put("status", true).put("data",groupedList).
					put("totalPage", groupedList.get("totalPage")).put("pageNo", comm.getPageNo()).toJson();
		}else{
			//查询为空时页数为1
			return rsMap(100, "操作成功").put("status", true).put("data",groupedList).
					put("totalPage", 1).put("pageNo", comm.getPageNo()).toJson();
		}

	}
	
	/**
	 * 设置页所需要的商品展示数据
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/setupCommodity",produces="text/html;charset=UTF-8",method = RequestMethod.POST)	
	@ResponseBody
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String setupCommodity(HttpServletRequest request,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "哈数据库等哈";
		CommodityArtifact  comm= null;
		try {
			comm = parseParamToObj(params);
		} catch (Exception e) {
			logger.error("***************查询设置页商品转换出错"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(comm ==null) return  rsMap(101, "操作失败").put("status", false).toJson();
		comm.setUserId(userId);
		CommodityViewer viewer =null;
		if(comm.getGroupId()!=null){
			viewer=new CommodityViewer<GroupedGoods>();
			//1查询已分组的商品数据
			List<GroupedGoods> groupedGoodsList = groupedGoodsService.findGroupedGoods(comm);
			if(groupedGoodsList!=null && !groupedGoodsList.isEmpty()){
				viewer.setSearchData(groupedGoodsList);
			}
		}else{
			viewer=new CommodityViewer<Item>();
			//根据条件查询出左侧数据
			Map<String,Object> result = itemService.findItemPagination(comm);
			if(result!=null){
				viewer.setCurrentPage(comm.getPageNo());
				viewer.setSearchData((List<Item>) result.get("data"));
				viewer.setTotalCout((Long) result.get("totalCount"));
				viewer.setTotalPage((Integer) result.get("totalPage"));
			}
		}
		//当numIid不为空的情况下查询出右侧展示的数据
		if(comm.getNumIid()!=null && comm.getNumIid().length>0){
			//1查询已分组的商品数据
			List<Item> itemData = itemService.findSetupItem(comm);
			if(itemData!=null){
				viewer.setItemData(itemData);
			}
		}
		return rsMap(100, "操作成功").put("status", true).put("data",viewer).toJson();
	}
	
	/**
	 * 商品设置页 查询分组名称下拉框
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/findGroupName",produces="text/html;charset=UTF-8",method = RequestMethod.POST)	
	@ResponseBody
	public String findGroupName(HttpServletRequest request){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "哈数据库等哈";
		CommodityArtifact comm = new CommodityArtifact();
		comm.setUserId(userId);
		List<CommodityGrouping> commNameList = commodityGroupingService.findCommName(comm);
	    return rsMap(100, "操作成功").put("status", true).put("data",commNameList).toJson();
	}
	
	/**
	 * 删除分组接口
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/deleteGroup",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String deleteGroup(HttpServletRequest request,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "哈数据库等哈";
		CommodityArtifact  comm= null;
		try {
			comm = parseParamToObj(params);
		} catch (Exception e) {
			logger.error("***************删除转换出错"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(comm ==null) return  rsMap(101, "操作失败").put("status", false).toJson();
		if(comm.getGroupId()==null) return  rsMap(101, "操作失败").put("status", false).toJson();
		comm.setUserId(userId);
		try {
			 commodityGroupingService.deleteGroup(comm);
		} catch (Exception e) {
			logger.error("***************删除分组失败"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).toJson(); 
	}
	
	/**
	 * 设置页添加短连接 商品展示
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/setupItemShow",produces="text/html;charset=UTF-8",method = RequestMethod.POST)	
	@ResponseBody
	public String setupItemShow(HttpServletRequest request,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId = "哈数据库等哈";
		CommodityArtifact  comm= null;
		try {
			comm = parseParamToObj(params);
		} catch (Exception e) {
			logger.error("***************查询设置页商品转换出错"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(comm ==null) return  rsMap(101, "操作失败").put("status", false).toJson();
		comm.setUserId(userId);
		CommodityViewer viewer =null;
		//根据条件查询出左侧数据
		Map<String,Object> result = itemService.findItemPagination(comm);
		if(result!=null){
			viewer=new CommodityViewer<Item>();
			viewer.setCurrentPage(comm.getPageNo());
			viewer.setSearchData((List<Item>) result.get("data"));
			viewer.setTotalPage((Integer) result.get("totalPage"));
		}
		return rsMap(100, "操作成功").put("status", true).put("data",viewer).toJson();
	}
	
	
//==========================================================================================================	
	/**
	 * 解析param 转换为实体vo
	 * @param params
	 * @return
	 */
	private CommodityArtifact parseParamToObj(String params){
		CommodityArtifact comm  = null;
		if(params!=null){
			JSONObject parseObject = JSON.parseObject(params);
			String object = parseObject.getString("params");
			comm = JsonUtil.fromJson(object, CommodityArtifact.class);
		}
		return comm;
	}
	
	
	/**
	 * Gg
	 * 查询分组名是否已存在数据库
	 * @param groupName
	 * @param userId
	 * @return
	 * Gg
	 */
	private boolean findGroupName(String groupName,String userId){
		boolean result = true;
		CommodityArtifact  comm = new CommodityArtifact();
		comm.setGroupName(groupName);
		comm.setUserId(userId);
		int count = commodityGroupingService.findGroupName(comm);
		if(count!=0){
			result = false;
		}
		return result;
	}
	
}

