package s2jh.biz.shop.crm.taobao.service.delItem;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.item.entity.Skus;
import s2jh.biz.shop.crm.user.entity.UserInfo;

@Service("delItemService")
@Transactional
@Deprecated 
public class DelItemServiceimpl implements DelItemService{
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	public void delItem(JSONObject parseJSON) throws Exception{
		//获取nick
		String nick = parseJSON.getString("nick");
		//获取 num_iid
		String numIid = parseJSON.getString("num_iid");
		//判断 推送的 nick 是否为空
		if(nick!=null && nick!=""){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("nick", nick);
			map.put("numIid", numIid);
			if(numIid!=null && numIid!=""){
				//删除商品
				myBatisDao.execute(Item.class.getName(), "delItem", map);
				//删除此商品相关的skus信息
				/*Map<String,Object> skumap = new HashMap<String,Object>();
				skumap.put("numIid", numIid);
				myBatisDao.execute(Skus.class.getName(), "delSkus", skumap);*/
			}
		}
		System.out.println("删除商品 监听任务已执行!!!!!!!!!!!!!!!");
	}

}
