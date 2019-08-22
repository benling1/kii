package s2jh.biz.shop.crm.sendSms;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab.s2jh.core.dao.mybatis.MyBatisDao;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.vip.service.VipUserService;

@Controller
@RequestMapping(value="crm/test")
public class SendSMSController {
	
	@Autowired
	 private UserInfoService userInfoService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private MobileSettingService mobileSettingService;
	
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;
	
	@Autowired
	private VipUserService vipUserService;
	
	/**
	 * 调用短信发送工具测试发送短信
	 * @throws IOException 
	 * 
	 */
	@RequestMapping(value="/sendSMS")
	public String sendSMS(String content,String phone,String autograph,HttpServletRequest request,HttpServletResponse response) throws IOException{
			//卖家id
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("taobao_user_nick");
			//计算短信长度
			int messageCount = content.length();
			if(messageCount<=70){
				messageCount=1;
			}else{
				messageCount = (messageCount+66)/67;
			}
				//创建对象封装参数,输出到前台
				JSONObject  json = new JSONObject();
				String sendStatus = null;

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("flag", true);
				map.put("userSend", true);
				
				/*查询当前用户是否为vip*/
				boolean isVip = vipUserService.findVipUserIfExist(userId);
				
				//创建SmsSendInfo封装数据调用短信发送方法
				SmsSendInfo smsSendInfo = new SmsSendInfo();
				smsSendInfo.setUserId(userId);//用户ID
				smsSendInfo.setActualDeduction(messageCount);//实际扣除短信条数
//				smsSendInfo.setAutograph(autograph);//短信签名
				smsSendInfo.setContent(content);//短信内容
				smsSendInfo.setType("99");
				smsSendInfo.setVip(isVip);//当前用户是否vip
				
				String[] phoneList = phone.split(",");
				for (int i = 0; i < phoneList.length; i++) {
					smsSendInfo.setSendTime(new Date());//发送时间
					smsSendInfo.setPhone(phoneList[i]);//接收人手机号
//					smsSendInfo.setAutograph(autograph);//短信签名
					smsSendInfo.setCreatedDate(new Date());//创建时间
					map.put("smsInfo",smsSendInfo);
					map = taoBaoSendMessageUtil.sendSingleMessage(map);
					sendStatus = (String)map.get("sendStatus");
					
				}
				
				
			json.put("send", sendStatus);
			//将封装好的json数据,输出到前台
			response.setContentType("application/json; charset=utf-8");  
			response.getWriter().write(json.toString());
		return null;
	}

}
