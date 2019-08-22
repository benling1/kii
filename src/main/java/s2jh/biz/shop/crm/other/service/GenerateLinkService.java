/**
 * 
 */
package s2jh.biz.shop.crm.other.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.taobao.taobaoInfo;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.CrmServiceChannelShortlinkCreateRequest;
import com.taobao.api.response.CrmServiceChannelShortlinkCreateResponse;

/** 
 * @Title: 
 * @date 2017年1月18日--下午2:00:24 
 * @param     设定文件 
 * @return 返回类型 
 * @throws 
 */


@Service
@Transactional
public class GenerateLinkService {
	
	/**
	  * 创建人：邱洋
	  * @Title: 获取短链接
	  * @date 2017年1月18日--下午2:02:23 
	  * @return String
	  * @throws
	 */
	public static String getLink(HttpServletRequest request,String linkType,String commodityId,String activityUrl,String orderId){
		String link = null;
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		CrmServiceChannelShortlinkCreateRequest req = new CrmServiceChannelShortlinkCreateRequest();
		String token = (String) request.getSession().getAttribute("access_token");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
		req.setShortLinkName("生成短链接"+formatter.format(date));	
		if(linkType.equals("商品链接")){			
			req.setLinkType("LT_ITEM");
			commodityId =commodityId.replace(" ", "");
			req.setShortLinkData(commodityId);
		}else if(linkType.equals("活动页链接")){
			req.setLinkType("LT_ACTIVITY");
			req.setShortLinkData(activityUrl);
		}else if(linkType.equals("店铺首页")||linkType.equals("店铺链接")){			
			req.setLinkType("LT_SHOP");
			req.setShortLinkData(null);
		}else{
			req.setLinkType("LT_TRADE");
			req.setShortLinkData(orderId);
		}	
		CrmServiceChannelShortlinkCreateResponse rsp = null;
		try {
			rsp = client.execute(req, token);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		if(rsp.getMsg()!=null&&rsp.getMsg().equals("Remote service error")){
			if(rsp.getSubMsg()!=null){
				link = " "+rsp.getSubMsg()+" ";
			}			
		}else{
			if(rsp.getShortLink()!=null){
				link = " "+rsp.getShortLink()+" ";
			}			
		}
		return link;
	}
	
	/**
	 * 生成订单短链接
	 * 邱洋 2017-05-12 14:56:48
	 * @param tid
	 * @param token
	 * @return
	 */
	public static String getLink(String tid,String token){
		String link = null;
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		CrmServiceChannelShortlinkCreateRequest req = new CrmServiceChannelShortlinkCreateRequest();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
		req.setShortLinkName("订单短链接"+formatter.format(date));
		req.setLinkType("LT_TRADE");
		req.setShortLinkData(tid);
		CrmServiceChannelShortlinkCreateResponse rsp = null;
		try {
			rsp = client.execute(req, token);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		if(rsp.getMsg()!=null&&rsp.getMsg().equals("Remote service error")){
			if(rsp.getSubMsg()!=null){
				link = " "+rsp.getSubMsg()+" ";
			}			
		}else{
			if(rsp.getShortLink()!=null){
				link = " "+rsp.getShortLink()+" ";
			}			
		}
		return link;
	}
}
