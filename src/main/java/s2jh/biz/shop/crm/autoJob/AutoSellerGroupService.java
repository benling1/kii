package s2jh.biz.shop.crm.autoJob;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.seller.entity.SellerGroup;
import s2jh.biz.shop.crm.seller.service.SellerGroupService;
import s2jh.biz.shop.crm.taobao.taobaoInfo;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.CrmGroupsGetRequest;
import com.taobao.api.response.CrmGroupsGetResponse;

@Service
@Transactional
public class AutoSellerGroupService {
	@Autowired
	private SellerGroupService sellerGroupService;
	
	public void getGradeRule(String sessionKey) throws ApiException{
		//获取CRM的地址，APPkey，secret
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey,taobaoInfo.appSecret);
		
		CrmGroupsGetRequest req = new CrmGroupsGetRequest();
		req.setPageSize(1L);//每页显示的记录数
		req.setCurrentPage(1L);//显示第几页
		CrmGroupsGetResponse rsp = client.execute(req, sessionKey);//根据卖家授权的sessionKey去淘宝接口取数据
		if(rsp.getGroups()!=null&&rsp.getGroups().size()>0){
			for(int i=0;i<rsp.getGroups().size();i++){
				SellerGroup sg = new SellerGroup();
				sg.setGroupId(rsp.getGroups().get(i).getGroupId());
				sg.setGroupName(rsp.getGroups().get(i).getGroupName());
				sg.setGroupCreate(rsp.getGroups().get(i).getGroupCreate());
				sg.setGroupModify(rsp.getGroups().get(i).getGroupModify());
				sg.setStatus(rsp.getGroups().get(i).getStatus());
				sg.setMemberCount(rsp.getGroups().get(i).getMemberCount().intValue());
				sellerGroupService.save(sg);
			}
		}
	}

}
