package s2jh.biz.shop.crm.autoJob;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.buyers.entity.MemberInfo;
import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.taobao.taobaoInfo;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.CrmMembersIncrementGetRequest;
import com.taobao.api.response.CrmMembersIncrementGetResponse;

@Service
@Transactional
public class AutoMemberInfoService {
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	public void updateMemberInfo(String sessionkey) throws ApiException{
		//获取CRM的地址，APPkey，secret
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		CrmMembersIncrementGetRequest req = new CrmMembersIncrementGetRequest();
		
		//req.setGrade(2L);//会员等级
		
		//获取当前日期和前一天日期，根据日期查询订单数据
		Calendar calendar = Calendar.getInstance();//获取的是系统当前时间
		String todayDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);    //得到前一天
        String  yestedayDate= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());        
	 	yestedayDate= yestedayDate+" 00:00:00";
		req.setStartModify(StringUtils.parseDateTime(yestedayDate));//最早修改时间
		req.setEndModify(StringUtils.parseDateTime(todayDate));//最后修改时间
		req.setPageSize(10L);//每页显示的会员数
		req.setCurrentPage(1L);//显示第几页会员
		CrmMembersIncrementGetResponse rsp = client.execute(req, sessionkey);
		if(rsp.getMembers()!=null){
			for(int i=0;i<rsp.getMembers().size();i++){
				MemberInfo memberInfo = new MemberInfo();
				memberInfo.setBuyerId(rsp.getMembers().get(i).getBuyerId().toString());
				memberInfo.setBuyerNick(rsp.getMembers().get(i).getBuyerNick());
				memberInfo.setStatus(rsp.getMembers().get(i).getStatus());
				memberInfo.setGradeId(rsp.getMembers().get(i).getGrade());
				memberInfo.setTradeCount(rsp.getMembers().get(i).getTradeCount());
				memberInfo.setTradeAmount(rsp.getMembers().get(i).getTradeAmount());
				memberInfo.setCloseTradeCount(rsp.getMembers().get(i).getCloseTradeCount());
				memberInfo.setCloseTradeAmount(rsp.getMembers().get(i).getCloseTradeAmount());
				memberInfoService.save(memberInfo);
				System.out.println("添加第"+i+"条成功！————————————————————");
			}
		}		
	}
	
}
