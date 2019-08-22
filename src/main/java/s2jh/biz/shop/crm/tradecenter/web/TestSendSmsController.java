package s2jh.biz.shop.crm.tradecenter.web;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;
import s2jh.biz.shop.crm.tradecenter.vo.SmsRecordDTOVo;
import s2jh.biz.shop.crm.tradecenter.vo.TestSendSmsVo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.vip.service.VipUserService;
import s2jh.biz.shop.utils.JsonUtil;

@Controller
@RequestMapping(value="/tradeSetup")
public class TestSendSmsController extends BaseController{
	
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
	@RequestMapping(value="/testSendSMS" ,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String sendSMS(@RequestBody String params,HttpServletRequest request,HttpServletResponse response) throws IOException{
			//卖家id
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("taobao_user_nick");
//			userId = "哈数据库等哈";
			String sendStatus = null;

			TestSendSmsVo sms = new TestSendSmsVo();
			if (null != params && !"".equals(params)) {
				JSONObject jsonVo = new JSONObject(params);
				sms = JsonUtil.fromJson(jsonVo.getString("params"), TestSendSmsVo.class);
			}
			
			if (null != sms.getContent() && !"".equals(sms.getContent())
				&& null != sms.getPhone() && !"".equals(sms.getPhone())) {
				String content = sms.getContent();
				String phone = sms.getPhone();
				
				//计算短信长度
				int messageCount = content.length();
				if(messageCount<=70){
					messageCount=1;
				}else{
					messageCount = (messageCount+66)/67;
				}
			
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("flag", true);
				map.put("userSend", true);
				
				
				//创建SmsSendInfo封装数据调用短信发送方法
				SmsSendInfo smsSendInfo = new SmsSendInfo();
				smsSendInfo.setUserId(userId);//用户ID
				smsSendInfo.setActualDeduction(messageCount);//实际扣除短信条数
//				smsSendInfo.setAutograph(autograph);//短信签名
				smsSendInfo.setContent(content);//短信内容
				smsSendInfo.setType("99");
				/*查询当前用户是否为vip*/
				boolean isVip = vipUserService.findVipUserIfExist(userId);
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
				
			
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("stats", sendStatus);
		return rsMap(100, "操作成功").put("status", true).put("data",map).toJson();
	}

}
