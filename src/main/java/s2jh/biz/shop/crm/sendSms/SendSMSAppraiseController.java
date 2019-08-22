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

import com.taobao.api.SecretException;

import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsHistoryTemplate;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.service.SmsHistoryTemplateService;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.taobao.info.SendMessageStatusInfo;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;
import s2jh.biz.shop.crm.user.service.UserInfoService;

@Controller
@RequestMapping(value="crm/test")
public class SendSMSAppraiseController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private MobileSettingService mobileSettingService;
	
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;
	
	/**
	 * 历史发送查看
	 */
	@Autowired
	private SmsHistoryTemplateService smsHistoryTemplateService;
	
	/**
	 * 调用短信发送工具测试发送短信
	 * @throws IOException 
	 * 
	 */
	@RequestMapping(value="/sendSMSAppraise")
	public String sendSMS(String content,
						String phone,
						String contentNum,
						String orderIds,
						String nickname,
						String templateId,// 使用的短信模板id
						HttpServletRequest request,HttpServletResponse response) throws IOException{
			//卖家id
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("taobao_user_nick");
			//测试userId
			//String userId = "test2";
			
			//计算短信长度
			int messageCount = content.length();
			if(messageCount<=70){
				messageCount=1;
			}else{
				messageCount = (messageCount+66)/67;
			}
			
			
			//创建对象封装参数,输出到前台
			JSONObject  json = new JSONObject();
			
			
			
			//创建SmsSendInfo封装数据调用短信发送方法
			SmsSendInfo smsSendInfo = new SmsSendInfo();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flag", true);
			map.put("userSend", true);
			String[] phoneList = phone.split(",");
			String[] orderIdList = orderIds.split(",");
			String[] nicknameList = nickname.split(",");
			String sendStatus = null;
			int flagIndex = 0;
			
			String sessionKey = userInfoService.findUserInfoTokens(userId);
			for (int i = 0; i < phoneList.length; i++) {
				smsSendInfo.setUserId(userId);//用户ID
				smsSendInfo.setType("19");
				smsSendInfo.setActualDeduction(messageCount);//实际扣除短信条数
				smsSendInfo.setContent(content);//短信内容
				smsSendInfo.setSendTime(new Date());//发送时间
				try {
					if(EncrptAndDecryptClient.isEncryptData(phoneList[i], EncrptAndDecryptClient.PHONE)){
						String decrypt = EncrptAndDecryptClient.getInstance().decrypt(phoneList[i], EncrptAndDecryptClient.PHONE,sessionKey);
						smsSendInfo.setPhone(decrypt);//接收人手机号
					}else{
						smsSendInfo.setPhone(phoneList[i]);//接收人手机号
					}
					if(nicknameList != null && nicknameList.length>0){
						if(EncrptAndDecryptClient.isEncryptData(nicknameList[i], EncrptAndDecryptClient.SEARCH)){
							String decrypt = EncrptAndDecryptClient.getInstance().decrypt(nicknameList[i], EncrptAndDecryptClient.SEARCH,sessionKey);
							smsSendInfo.setNickname(decrypt);//接收人昵称
						}else{
							smsSendInfo.setNickname(nicknameList[i]);//接收人昵称
						}
					}
				} catch (SecretException e) {
				}
				if(orderIdList != null && orderIdList.length>0){
					smsSendInfo.setOid(Long.parseLong(orderIdList[i]));//要发送的短信订单号
				}
				map.put("smsInfo",smsSendInfo);
				map = taoBaoSendMessageUtil.sendSingleMessage(map);
				if(map.containsKey("sendStatus")){
					sendStatus = (String)map.get("sendStatus");
				}
				if(map.containsKey("nosms")){
					if(i==0){
						json.put("smsNum", "短信不足,请充值!!!");
					}else{
						int a= i+1;
						json.put("smsNum", "发送第"+a+"条时,短信不足,请充值!!!");
					}
					break;
				}
				if(!SendMessageStatusInfo.SEND_SUCCESS.equals(sendStatus)){
					flagIndex = i+1;
				}
			}
			
			
			
			/**
			 * 添加或修改使用的短信模板
			 */
			if (templateId != null && templateId != "") {
						SmsHistoryTemplate smsHisTemp = smsHistoryTemplateService
						.findTemplateBytemplateId(userId, templateId,"中差评查看");
				if (smsHisTemp != null && !"".equals(smsHisTemp)) {
					Map<String, Object> mapTemp = new HashMap<String, Object>();
					Date date = new Date();
					map.put("id", smsHisTemp.getId());
					map.put("templateId", templateId);
					map.put("userId", userId);
					map.put("type", "中差评查看");
					map.put("lastModifiedDate", date);
					smsHistoryTemplateService.updateHistoryTempById(mapTemp);
				} else {
					SmsHistoryTemplate smsHistoryTemplate = new SmsHistoryTemplate();
					smsHistoryTemplate.setUserId(userId);
					smsHistoryTemplate.setTemplateId(templateId);
					smsHistoryTemplate.setType("中差评查看");
					smsHistoryTemplate.setLastModifiedDate(new Date());
					smsHistoryTemplateService
					.addHistoryTempById(smsHistoryTemplate);
				}
			}
			
			
			
			json.put("send", sendStatus);
			json.put("sendIndex", flagIndex);
			//将封装好的json数据,输出到前台
			response.setContentType("application/json; charset=utf-8");  
			response.getWriter().write(json.toString());
		return null;
	}

}
