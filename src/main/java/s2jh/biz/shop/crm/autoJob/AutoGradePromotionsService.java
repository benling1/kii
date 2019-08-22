package s2jh.biz.shop.crm.autoJob;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.member.entity.GradePromotions;
import s2jh.biz.shop.crm.member.service.GradePromotionsService;
import s2jh.biz.shop.crm.taobao.taobaoInfo;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.CrmGradeGetRequest;
import com.taobao.api.response.CrmGradeGetResponse;

@Service
@Transactional
public class AutoGradePromotionsService {
	
	@Autowired
	private GradePromotionsService gradePromotionsService;
	
	public void updateGradePromotions(String sessionKey,String userId) throws ApiException{
		//获取CRM的地址，APPkey，secret
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		
		CrmGradeGetRequest req = new CrmGradeGetRequest();
		CrmGradeGetResponse rsp = client.execute(req, sessionKey);
		if(rsp.getGradePromotions().size()>0&&rsp.getGradePromotions()!=null){
			for(int i=0;i<rsp.getGradePromotions().size();i++){
				GradePromotions gp = new GradePromotions();
				gp.setCurGrade(rsp.getGradePromotions().get(i).getCurGrade());
				gp.setCurGradeName(rsp.getGradePromotions().get(i).getCurGradeName());
				gp.setDiscount(rsp.getGradePromotions().get(i).getDiscount());
				gp.setNextUpgradeAmount(rsp.getGradePromotions().get(i).getNextUpgradeAmount());
				gp.setNextUpgradeCount(rsp.getGradePromotions().get(i).getNextUpgradeCount());
				gp.setNextGrade(rsp.getGradePromotions().get(i).getNextGrade());
				gp.setNextGradeName(rsp.getGradePromotions().get(i).getNextGradeName());
				gp.setUserId(userId);
				gradePromotionsService.save(gp);
			}			
		}
	}
}
