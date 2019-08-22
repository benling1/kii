package s2jh.biz.shop.crm.item.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.item.entity.ItemImport;
import s2jh.biz.shop.crm.item.service.ItemImportService;
import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.crm.manage.base.BaseController;

@Controller
@RequestMapping(value = "item")
public class ItemController extends BaseController{
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemImportService itemImportService;
	/**
	 * 查询商品到下单关怀
	 * @throws IOException 
	 */
	@RequestMapping(value = "/queryItem")
	public String queryItem(Model model,
										String commodityId,
										String name,
										String status,
										HttpServletRequest request,
										HttpServletResponse response) throws IOException{
		
		//卖家id(从域对象中获取数据)
		HttpSession session = request.getSession();
		String userNick = (String) session.getAttribute("taobao_user_nick");
		/*String userNick = "crzzyboy";*/
		
		//判断status是否等于1,等于就复制条件
		if(status!=null && status.equals("1")){
			status="onsale";
		}else{
			status=null;
		}
		List<Item> itemList = itemService.queryItem(commodityId,name,status,userNick);
		//创建对象封装参数,输出到前台
		JSONObject  json = new JSONObject();
		json.put("itemList", itemList);
		//将封装好的json数据,输出到前台
		response.setContentType("application/json; charset=utf-8");  
		response.getWriter().write(json.toString());
		
		return null;
	}
	
	/**
	 * @Description: 查询同步商品和导入拆分商品，计算分页数据  
	 * @author HL
	 * @date 2017年11月28日 下午5:31:30
	 */
	@ResponseBody
	@RequestMapping(value="/findItemData", produces="application/json;charset=UTF-8")
	public String findItemData(String numIid, String itemId, String title,
			String onsaleStatus, String importStatus, String instockStatus,
			Integer pageNo, HttpServletRequest request) {
		String userNick = (String) request.getSession().getAttribute("taobao_user_nick");
		//设置每页显示5条数据
		Integer currentRows =5;
		//计算出起始行数
		Integer startRows = 0;
		if(null == pageNo || pageNo<1){
			pageNo = 1;
		}
		startRows = (pageNo-1)*currentRows;
		List<String> status = null;
		String[] numIids = null;
		if (null != onsaleStatus || null != importStatus
				|| null != instockStatus) {
			status = new ArrayList<String>();
			if ("1".equals(onsaleStatus)) {
				status.add("onsale");
			}
			if ("1".equals(importStatus)) {
				status.add("import");
			}
			if ("1".equals(instockStatus)) {
				status.add("instock");
			}
			if(status.isEmpty()){
				status = null;
			}
		}
		
		if(null != numIid && !"".equals(numIid)){
			numIids = numIid.split(",");
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
	    map.put("numIid", numIids);
	    map.put("title", title);
	    map.put("status", status);
	    map.put("userNick", userNick);
	    map.put("startRows", startRows);
	    map.put("currentRows", currentRows);
	    Map<String,Object> result = itemImportService.findItemData(map);
	    List<ItemImport> datas = itemImportService.findItemImportAndItem(itemId,userNick);
	    return rsMap(100, "success").put("pageData",result).put("datas",datas).toJson();
	}
}
