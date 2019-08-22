package s2jh.biz.shop.crm.autoJob;


import java.util.HashMap;
import java.util.Map;

import lab.s2jh.core.dao.mybatis.MyBatisDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.UserSellerGetRequest;
import com.taobao.api.response.UserSellerGetResponse;

import s2jh.biz.shop.crm.seller.entity.SellerUserInfo;
import s2jh.biz.shop.crm.seller.service.SellerUserInfoService;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;

@Service
@Transactional
public class AutoUserSellerService {
	
	@Autowired
	private SellerUserInfoService sellerUserInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	public void sellerUserInfo() throws ApiException{
		
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		UserSellerGetRequest req = new UserSellerGetRequest();
		req.setFields("nick,sex,type,hasMorePic,itemImgNum,itemImgSize,propImgNum,+"
				+ "propImgSize,autoRepost,promotedType,status,alipayBind,consumerProtection,+"
				+ "avatar,liangpin,signFoodSellerPromise,hasShop,isLightningConsignment,+"
				+ "hasSubStock,isGoleduSeller,magazineSubscribe,verticalMarket,onlineGaming,+"
				+ "vipInfo");
		
		/*String url="https://oauth.taobao.com/token";
		Map<String,String> props = new HashMap<String, String>();
		props.put("grant_type", "authorization_code");*/
		UserSellerGetResponse rsp = client.execute(req, "");
		SellerUserInfo su= new SellerUserInfo();
		if(rsp.getUser()!=null){
		if(rsp.getUser().getUserId()!=null){
			su.setUserId(Long.toString(rsp.getUser().getUserId()));
		}
		su.setNick(rsp.getUser().getNick());
		su.setSex(rsp.getUser().getSex());
		su.setType(rsp.getUser().getType());
		su.setHasMorePic(rsp.getUser().getHasMorePic());
		su.setItemImgNum(rsp.getUser().getItemImgNum());
		su.setItemImgSize(rsp.getUser().getItemImgSize());
		su.setPropImgNum(rsp.getUser().getPropImgNum());
		su.setPropImgSize(rsp.getUser().getPropImgSize());
		su.setAutoRepost(rsp.getUser().getAutoRepost());
		su.setPromotedType(rsp.getUser().getPromotedType());
		su.setStatus(rsp.getUser().getStatus());
		su.setAlipayBind(rsp.getUser().getAlipayBind());
		su.setConsumerProtection(rsp.getUser().getConsumerProtection());
		su.setAvatar(rsp.getUser().getAvatar());
		su.setLiangpin(rsp.getUser().getLiangpin());
		su.setSignFoodSellerPromise(rsp.getUser().getSignConsumerProtection());
		su.setHasShop(rsp.getUser().getHasShop());
		su.setIsLightningConsignment(rsp.getUser().getIsLightningConsignment());
		su.setHasSubStock(rsp.getUser().getHasSubStock());
		su.setIsGoleduSeller(rsp.getUser().getIsGoldenSeller());
		su.setMagazineSubscribe(rsp.getUser().getMagazineSubscribe());
		su.setVerticalMarket(rsp.getUser().getVerticalMarket());
		su.setOnlineGaming(rsp.getUser().getOnlineGaming());
		su.setVipInfo(rsp.getUser().getVipInfo());
		sellerUserInfoService.save(su);		
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("nowDate", new Date());
		
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		UserSellerGetRequest req = new UserSellerGetRequest();
		req.setFields("nick,sex");
		UserSellerGetResponse rsp = client.execute(req, taobaoInfo.sessionKey);
        SellerUserInfo su =new SellerUserInfo();
        List<UserInfo> userInfos =myBatisDao.findList("s2jh.biz.user.entity.userInfo",
        		"findAllUserId", params);
        if(userInfos !=null){
        	for(int i=0;i<userInfos.size();i++){
        	    su.setUserId(Long.toString(userInfos.get(i).getTaobaoUserId()));
        		su.setNick(rsp.getUser().getNick());
        		su.setSex(rsp.getUser().getSex());
        		sellerUserInfoService.save(su);
        		 
        	}
        	
        }*/
	}else{
		System.out.println("没有数据！！！！！！！！！！！！");
	}
}
	
	public static void main(String [] args) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		UserSellerGetRequest req = new UserSellerGetRequest();
		req.setFields("nick,sex");
		UserSellerGetResponse rsp = client.execute(req, "");
		System.out.println(rsp.getBody());
		
	}

}
