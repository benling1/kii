package s2jh.biz.shop.crm.autoJob;

import lab.s2jh.core.dao.mybatis.MyBatisDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.item.service.ItemService;

import com.taobao.api.ApiException;

@Service
@Transactional
public class AutoGetItemService {

	@Autowired
	private ItemService itemService;

	@Autowired
	private MyBatisDao myBatisDao;

	public void itemInformation() throws ApiException {
		String itemId="544146687282";
		String userId = "小白你什么都没看见哦";
		
	}

}
