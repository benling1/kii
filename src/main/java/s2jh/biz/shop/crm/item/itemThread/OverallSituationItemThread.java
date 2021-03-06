package s2jh.biz.shop.crm.item.itemThread;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import s2jh.biz.shop.crm.item.pojo.TbItem;
import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.utils.SpringContextUtil;

public class OverallSituationItemThread extends Thread {
	
	private List<TbItem> tbItemList = new CopyOnWriteArrayList<TbItem>();

	public List<TbItem> getTbItemList() {
		return tbItemList;
	}

	public void setTbItemList(List<TbItem> tbItemList) {
		this.tbItemList = tbItemList;
	}
	
	public void appendtbItemList(List<TbItem> tbItemList){
		this.tbItemList.addAll(tbItemList);
	}
	
	@Override
	public void run(){
		ItemService  itemService = SpringContextUtil.getBean("itemService");
		//itemService.overallSituationItemThread();
		itemService.saveItemsys(tbItemList);
	}
}
