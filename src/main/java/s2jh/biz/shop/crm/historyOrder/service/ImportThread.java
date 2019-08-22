package s2jh.biz.shop.crm.historyOrder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import s2jh.biz.shop.utils.SpringContextUtil;

public class ImportThread extends Thread {

	private List<String[]> subDataList = new CopyOnWriteArrayList<String[]>();
	
	private Map<String, Integer> headerMap  = new HashMap<String, Integer>();
	
	private Long orderImportId;
	
	private String userId;
	
	private Map<String,List<String>> itemTitleMap  = new HashMap<String,List<String>>();
	
	
	public ImportThread(List<String[]> subDataList,
			Map<String, Integer> headerMap, Long orderImportId,
			String userId,Map<String,List<String>> itemTitleMap) {
		super();
		this.subDataList = subDataList;
		this.headerMap = headerMap;
		this.orderImportId = orderImportId;
		this.userId = userId;
		this.itemTitleMap = itemTitleMap;
	}

	
	public Map<String, List<String>> getItemTitleMap() {
		return itemTitleMap;
	}

	public void setItemTitleMap(Map<String, List<String>> itemTitleMap) {
		this.itemTitleMap = itemTitleMap;
	}


	public List<String[]> getSubDataList() {
		return subDataList;
	}

	public void setSubDataList(List<String[]> subDataList) {
		this.subDataList = subDataList;
	}


	public Map<String, Integer> getHeaderMap() {
		return headerMap;
	}


	public void setHeaderMap(Map<String, Integer> headerMap) {
		this.headerMap = headerMap;
	}


	public Long getOrderImportId() {
		return orderImportId;
	}


	public void setOrderImportId(Long orderImportId) {
		this.orderImportId = orderImportId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void appendSubDataList(List<String[]> args){
		this.subDataList.addAll(args);
	}

	@Override
	public void run() {
		//获取对象
		ImportThreadService importThreadService = SpringContextUtil.getBean("importThreadService");
		importThreadService.threadImport(subDataList, headerMap, userId, orderImportId,itemTitleMap);
	}
	
}



