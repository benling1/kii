package s2jh.biz.shop.crm.message.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.taobao.api.SecretException;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.manage.util.idworker.SmsRecordIdWorker;
import s2jh.biz.shop.crm.message.entity.MsgSendRecord;
import s2jh.biz.shop.crm.message.entity.SmsHistoryTemplate;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.message.entity.SmsSendRecord;
import s2jh.biz.shop.crm.message.entity.SmsSendlistImport;
import s2jh.biz.shop.crm.message.service.MsgSendRecordService;
import s2jh.biz.shop.crm.message.service.SmsHistoryTemplateService;
import s2jh.biz.shop.crm.message.service.SmsMobileService;
import s2jh.biz.shop.crm.message.service.SmsSendlistImportService;
import s2jh.biz.shop.crm.message.service.SmsTemplateService;
import s2jh.biz.shop.crm.other.entity.Festival;
import s2jh.biz.shop.crm.other.service.FestivalService;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.vip.service.VipUserService;
import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.support.service.MultithreadBatchSmsService;
import s2jh.biz.shop.utils.DateUtils;
import s2jh.biz.shop.utils.ExcelImportUtils;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;
import s2jh.biz.shop.utils.phoneRegExp.PhoneRegUtils;

@Controller
@RequestMapping(value = "smsSendAppoint")
public class SmsSendAppointController {
    @Autowired
    private UserAccountService userAccountService;

	// 引入导入发送记录service
	@Autowired
	private SmsSendlistImportService smsSendlistImportService;

	// 应入Service查询短信,域扣除
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private MyBatisDao myBatisDao;

	@Autowired
	private SmsMobileService smsMobileService;

	@Autowired
	private MobileSettingService mobileSettingService;

	@Autowired
	private MultithreadBatchSmsService multithreadService;

	@Autowired
	private MsgSendRecordService msgSendRecordService;

	@SuppressWarnings("unused")
	private List<SmsSendRecord> smsSendRecordList;

	@Autowired
	private MsgSendRecordService mssgSendRecordService;

	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private SmsHistoryTemplateService smsHistoryTemplateService;
	
	@Autowired
	private SmsTemplateService smsTemplateService;

	@Autowired
	private FestivalService festivalService;
	
	@Autowired
	private VipUserService vipUserService;

	// 日志
	private static final Log logger = LogFactory
			.getLog(SmsSendAppointController.class);

	/**
	 * 指定号码群发跳转页面
	 */
	@RequestMapping(value = "/appointNumberSend", method = RequestMethod.GET)
	public String MarketingCenter(Model model) {
		model.addAttribute("flag", 0);

		return "crms/marketingCenter/appointNumberSend";

	}

	// ============指定号码发送部分===========

	/**
	 * 短信模板查询
	 */
	// 根据条件查询短信模板信息（分页）
	@RequestMapping(value = "/findSmsTemplate")
	public String findSmsTemplate(Model model, HttpServletRequest request,
			Integer pageNo) {
		// 获取页面的值
		String contentVal = request.getParameter("contentVal");
		// 用户id
		String userId = (String) request.getSession().getAttribute(
				"taobao_user_nick");
		// String userId = "crzzyboy";
		Map<String, Object> map = new HashMap<String, Object>();
		String type = request.getParameter("type");// 获取短信模板类型
		map.put("type", type);
		map.put("userId", userId);
		// 获取当前日期和前一天日期，根据日期查询订单数据
		Calendar calendar = Calendar.getInstance();// 获取的是系统当前时间
		String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar
				.getTime());
		Festival fe = new Festival();
		if (type == null) {
			fe = festivalService.findFestival(todayDate);
			if (fe != null) {
				type = fe.getName();
			}
			model.addAttribute("show", 2);
		} else {
			if (type.equals("聚划算")) {
				model.addAttribute("show", 3);
			} else if (type.equals("上新")) {
				model.addAttribute("show", 4);
			} else if (type.equals("周年庆")) {
				model.addAttribute("show", 5);
			}
		}

		// 使用分页工具进行分页列表查询
		String contextPath = request.getContextPath();
		Pagination pagi = smsTemplateService.findAllSmsTemplate(pageNo, map);
		String url = contextPath + "/smsSendAppoint/findSmsTemplate";
		StringBuilder params = new StringBuilder();
		if (type != null && !"".equals(type)) {
			params.append("&type=").append(type);
		}
		pagi.pageView(url, params.toString());
		model.addAttribute("type", type);
		model.addAttribute("pagiList", pagi);
		model.addAttribute("flag", 0);

		// 将获取页面的值写回页面
		model.addAttribute("contentVal", contentVal);
		return "crms/marketingCenter/appointNumberSend";
	}

	/**
	 * 历史发送查看
	 */
	// 根据卖家编号查询所有模板使用信息
	@RequestMapping(value = "/findAllList")
	public String findAllList(HttpServletRequest request, Integer pageNo,
			Model model) {

		// 获取页面的值
		String contentVal = request.getParameter("contentVal");

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		// String userId="crzzyboy";

		// 使用分页工具进行分页列表查询
		String contextPath = request.getContextPath();
		Pagination pagi = smsHistoryTemplateService.findSmsHistoryTemplate(
				userId, contextPath, pageNo);
		// 拼接分页的后角标中的跳转路径与查询的条件
		String url = contextPath + "/smsSendAppoint/findAllList";
		StringBuilder params = new StringBuilder();
		pagi.pageView(url, params.toString());
		model.addAttribute("show", 6);
		model.addAttribute("flag", 0);
		model.addAttribute("pagi", pagi);

		// 将获取页面的值写回页面
		model.addAttribute("contentVal", contentVal);
		return "crms/marketingCenter/appointNumberSend";
	}


	/**
	 * 点击确定发送,获取数据判断是否是立即发送,或者定时发送 helei 2017年2月15日下午3:21:17
	 * sendMode,// 发送方式   1--手动输入号码,2---批量上传号码                          
	 *	recNum,// 手机号码                                                   
	 *	channel,// 短信渠道 1--淘宝 2--京东 , 3--天猫 , 4---自定义签名                  
	 *	autograph,// 短信签名                                                
	 *  content,// 短信内容                                                  
	 *  sendShield,// 重复发送过滤 0--不屏蔽 1--屏蔽                                
	 *  sendDay,// 屏蔽后设置的天数                                              
	 *  sendTimeType,// 发送时间 1---立即发送 2---定时发送                           
	 *  sendTime,// 定时发送的时间                                              
	 *  templateId,// 使用的短信模板id                                          
	 *  activityName,// 活动名称                                             
	 *  phonesId, //2---批量上传号码的记录id                                      
	 */
	@RequestMapping(value = "/marketingCenter/saveSmsSendRecord")
	public String saveSmsSendRecord(Model model, String sendMode,
			String recNum, String channel, String autograph, String content,
			String sendShield, String sendDay, String sendTimeType,
			String sendTime, String templateId, String activityName,
			String phonesId, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		String error = "";// 返回失败参数

		// 计算短信长度
		int messageCount = content.length();
		if (messageCount <= 70) {
			messageCount = 1;
		} else {
			messageCount = (messageCount + 66) / 67;
		}

		//通过批量上传号码的记录id查询电话号码
		if(sendMode!=null&&"2".equals(sendMode)&&phonesId !=null &&!"".equals(phonesId)){
			SmsSendlistImport smsSendlist = smsSendlistImportService
					.findImportPhoneById(phonesId);
			recNum = smsSendlist.getImportPhone();
			//解密电话号码
			if(null != recNum && !"".equals(recNum)){
				String[] split = recNum.split(",");
				List<String> asList = Arrays.asList(split);
				List<String> decryptPhones = decryptListPhone(userId, asList);
				if(null!= decryptPhones && decryptPhones.size()>0){
					recNum = StringUtils.join(decryptPhones.toArray(), ",");
				}else{
					recNum = null;
				}
			}
			
		}
		
		// 查询用户的短信条数
		Long smsNum = userAccountService.findUserAccountSms(userId);
		String[] recNumArr = null;
		if(recNum!=null && !"".equals(recNum)){
			recNumArr = recNum.split(",");
		}
		// 短信条数判断
		if (recNumArr!=null && recNumArr.length * messageCount > smsNum) {
			error = "短信不足,请充值!!!";
		} else {

//			recNum+=",18201667208,18800000639,18800000638,18201667208,18201667208,dsadasdas,dsadsada,dsaasdasd";
			// 去除重复号码，返回不同状态的手机号码
			Map<String, Object> removePhone = this.removePhone(recNum);

			Integer totalCount = (Integer) removePhone.get("totalCount");// 短信批量发送总数
			Set<String> correctPhone = (Set<String>) removePhone.get("phoneSet");// 正确的手机号码
			List<String> wrongPhone = (List<String>) removePhone.get("wrongNums");// 错误的手机号码
			List<String> repeatPhone = (List<String>) removePhone.get("repeatNums");// 重复的手机号码
			List<String> blackPhone = null;// 黑名单的电话号码
			List<String> shieldPhone = null;// 屏蔽的电话号码

			// 将正确的电话号码放入list中
			List<String> listPhone = new ArrayList<String>();
			listPhone.addAll(correctPhone);

			 //获取该用户下的所有黑名单电话号码并屏蔽掉
			blackPhone = this.filtrateBlackPhone(listPhone, correctPhone,
					userId);

			 //通过屏蔽后设置的天数 获取已经发送过的用户信息
			shieldPhone = this.shieldPhones(sendShield, sendDay, listPhone,
					correctPhone, blackPhone, userId);

			 //将List返回数组
			String[] Phones = new String[listPhone.size()];
			if (listPhone != null && listPhone.size() > 0) {
				Phones = listPhone.toArray(Phones);
			}

			// 将处理的手机号码在判断
			if (Phones == null || Phones.length == 0 || "".equals(content)) {
				error = "抱歉,您发送的手机号码存在黑名单或被屏蔽,请确认后再发送";
			} else {
				// 获取定时的时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				// 开始时间
				Date sTime = null;
				// 计算出结束时间
				Date endSend = null;
				if (sendTime != null && !sendTime.equals("")) {
					try {
						sTime = sdf.parse(sendTime);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(sTime);
						calendar.add(Calendar.DATE, +1);
						endSend = calendar.getTime();
					} catch (ParseException e) {
					}
				}

				try {

					// 保存总记录数据
					Long msgRecordId = this.insertMsgSendRecord(userId,
							totalCount, Phones.length, content, activityName,
							sendTimeType, sTime, wrongPhone.size(),
							repeatPhone.size(), blackPhone.size(),
							shieldPhone.size());

					// 判断是否是立即发送 还是定时发送, 立即发送:调用短信接口发送数据 ---1 定时发送:保存到数据库 ----2
					try {
						String ipAddress = getIpAddress.getIpAddress(request);
						this.threadManageSms(sendTimeType, Phones, ipAddress,
								autograph, userId, content, msgRecordId,
								totalCount, messageCount, sTime, endSend);
					} catch (Exception e) {
						logger.error("指定号码群发---短信发送异常" + e.getMessage());
						if (sendTimeType.equals("1")) {
							error = "短信发送异常，请联系客服！";
						} else {
							error = "短信定时异常，请联系客服！";
						}
					}

					// 保存错误的手机号码数据到SmsSendRecord中
					try {
						this.saveErrorSmsSendRecord(userId, activityName,
								content, autograph, msgRecordId, wrongPhone,
								repeatPhone, blackPhone, shieldPhone);
					} catch (Exception e) {
						logger.error("指定号码群发---错误手机号保存异常" + e.getMessage());
					}

					// 添加或修改使用的短信模板
					try {
						this.updateTemplateId(templateId, userId);
					} catch (Exception e) {
						logger.error("指定号码群发---短信模板使用设置异常" + e.getMessage());
					}

				} catch (Exception e) {
					logger.error("指定号码群发---添加发送总记录异常" + e.getMessage());
					if (sendTimeType.equals("1")) {
						error = "短信发送异常，请联系客服！";
					} else {
						error = "短信定时异常，请联系客服！";
					}
				}
			}

		}

		// 创建对象封装参数,输出到前台
		JSONObject json = new JSONObject();
		json.put("sendTimeType", sendTimeType);
		json.put("error", error);
		try {
			// 将封装好的json数据,输出到前台
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(json.toString());
		} catch (IOException e) {
		}

		return null;
	}

	/**
	 * 通过屏蔽后设置的天数 获取已经发送过的用户信息
	 */
	private List<String> shieldPhones(String sendShield, String sendDay,
			List<String> listPhone, Set<String> correctPhone,
			List<String> blackPhone, String userId) {
		List<String> shieldPhone = new ArrayList<String>();// 屏蔽的电话号码
		// 屏蔽已发送几天
		int sendSky = 0;
		if (sendShield.equals("1")) {
			if (sendDay != null && !sendDay.equals("")) {
				sendSky = Integer.parseInt(sendDay);
			}
		}
		if (sendSky > 0) {
			if (listPhone != null && listPhone.size() > 0) {
				List<String> ssList = smsRecordService.querySmsSendRecord(
						userId, sendSky);
				// 删除listPhone中存在屏蔽的号码
				if (ssList != null && ssList.size() > 0) {
					ssList = decryptListPhone(userId, ssList);
					listPhone.removeAll(ssList);
					if (listPhone.size() != correctPhone.size()) {
						shieldPhone.addAll(correctPhone);// 将校验去重的手机号码添加到屏蔽list
						shieldPhone.removeAll(listPhone);// 移除正确的号码
						shieldPhone.removeAll(blackPhone);// 移除黑名单号码--剩下屏蔽的号码
					}
				}
			}
		}
		return shieldPhone;
	}

	/**
	 * 获取该用户下的所有黑名单电话号码并屏蔽掉
	 */
	private List<String> filtrateBlackPhone(List<String> listPhone,Set<String> correctPhone, String userId) {
		List<String> blackPhone = new ArrayList<String>();// 黑名单的电话号码
		if (listPhone != null && listPhone.size() > 0) {
			// 查询手机号黑名单返回list
			List<String> blackPhones = smsMobileService
					.querySmsBlacklistNumList(userId);
			// 删除listPhone中存在黑名单的号码
			if (blackPhones != null && blackPhones.size() > 0) {
				blackPhones = decryptListPhone(userId, blackPhones);
				listPhone.removeAll(blackPhones);
				if (listPhone.size() != correctPhone.size()) {
					blackPhone.addAll(correctPhone);// 将校验去重的手机号码添加到黑名单list
					blackPhone.removeAll(listPhone);// 移除正确的号码，剩下黑名单的号码
				}
			}
		}
		return blackPhone;
	}

	/**
	 * 保存发送总记录
	 */
	private Long insertMsgSendRecord(String userId, Integer totalCount,
			int smsPhoneNum, String content, String activityName,
			String sendTimeType, Date sTime, int wrongPhone, int repeatPhone,
			int blackPhone, int shieldPhone) {
		// 保存总记录返回id
		MsgSendRecord msg = new MsgSendRecord();
		msg.setUserId(userId);
		msg.setTotalCount(totalCount);
		msg.setSucceedCount(0);
		msg.setFailedCount(0);
		msg.setWrongCount(wrongPhone);
		msg.setRepeatCount(repeatPhone);
		msg.setBlackCount(blackPhone);
		msg.setSheildCount(shieldPhone);
		msg.setTemplateContent(content);
		msg.setActivityName(activityName);
		msg.setType(MsgType.MSG_ZDHMQF);
		msg.setIsShow(true);
		msg.setStatus(MsgType.MSG_STATUS_SENDING);
		msg.setIsSent(sendTimeType.equals("1") ? true : false);
		msg.setSendCreat(sendTimeType.equals("1") ? new Date()
				: sTime);
		Long msgRecordId = mssgSendRecordService
				.insertMsgSendRecord(msg);
		return msgRecordId;
	}

	/**
	 * 添加或修改使用的短信模板
	 */
	private void updateTemplateId(String templateId, String userId) {
		if (templateId != null && templateId != "") {
			SmsHistoryTemplate smsHisTemp = smsHistoryTemplateService
					.findTemplateBytemplateId(userId, templateId, "指定号码群发");
			if (smsHisTemp != null && !"".equals(smsHisTemp)) {
				Map<String, Object> map = new HashMap<String, Object>();
				Date date = new Date();
				map.put("id", smsHisTemp.getId());
				map.put("templateId", templateId);
				map.put("userId", userId);
				map.put("type", "指定号码群发");
				map.put("lastModifiedDate", date);
				smsHistoryTemplateService.updateHistoryTempById(map);
			} else {
				SmsHistoryTemplate smsHistoryTemplate = new SmsHistoryTemplate();
				smsHistoryTemplate.setUserId(userId);
				smsHistoryTemplate.setTemplateId(templateId);
				smsHistoryTemplate.setType("指定号码群发");
				smsHistoryTemplate.setLastModifiedDate(new Date());
				smsHistoryTemplateService
						.addHistoryTempById(smsHistoryTemplate);
			}
		}

	}

	/**
	 * 异步处理错误的短信数据，保存短信发送详情数据
	 */
	private void saveErrorSmsSendRecord(final String userId,
			final String activityName, final String content,
			final String autograph, final Long msgRecordId,
			final List<String> wrongPhone, final List<String> repeatPhone,
			final List<String> blackPhone, final List<String> shieldPhone) {

		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread() {
			@Override
			public void run() {
				SmsRecordDTO sms = new SmsRecordDTO();
				Date date = new Date();
				sms.setUserId(userId);
				sms.setActivityName(activityName);
				sms.setContent(content);
				sms.setType(MsgType.MSG_ZDHMQF);
				sms.setActualDeduction(0);
				sms.setAutograph(autograph);
				sms.setChannel(autograph);
				sms.setSendTime(date);
				sms.setSendLongTime(date.getTime());
				sms.setTimestampId(SmsRecordIdWorker.getInstance().nextId());
				sms.setMsgId(msgRecordId);
				sms.setSource("2"); 
				sms.setShow(true);

				// 错误的手机号码
				if (wrongPhone != null && wrongPhone.size() > 0) {
					sms.setStatus(3);
					for (String phone : wrongPhone) {
						String encryptPhone = encryptPhone(userId, phone);
						sms.setRecNum(encryptPhone);
						smsRecordService.saveSingleSmsRecord(sms,userId);
					}
				}
				// 重复的手机号码
				if (repeatPhone != null && repeatPhone.size() > 0) {
					sms.setStatus(4);
					for (String phone : repeatPhone) {
						String encryptPhone = encryptPhone(userId, phone);
						sms.setRecNum(encryptPhone);
						smsRecordService.saveSingleSmsRecord(sms,userId);
					}
				}
				// 黑名单的电话号码
				if (blackPhone != null && blackPhone.size() > 0) {
					sms.setStatus(5);
					for (String phone : blackPhone) {
						String encryptPhone = encryptPhone(userId, phone);
						sms.setRecNum(encryptPhone);
						smsRecordService.saveSingleSmsRecord(sms,userId);
					}
				}
				// 屏蔽的电话号码
				if (shieldPhone != null && shieldPhone.size() > 0) {
					sms.setStatus(6);
					for (String phone : shieldPhone) {
						String encryptPhone = encryptPhone(userId, phone);
						sms.setRecNum(encryptPhone);
						smsRecordService.saveSingleSmsRecord(sms,userId);
					}
				}
			}
		});
	}

	/**
	 * 单条加密电话号码
	 * @author HL
	 * @return
	 */
	private String encryptPhone(String userId,String phone){
		String session = userInfoService.findUserInfoTokens(userId);
		if(null != session && !"".equals(session)){
			try {
				if(!EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
					String encryptData = EncrptAndDecryptClient.getInstance().encryptData(phone, EncrptAndDecryptClient.PHONE,session);
					return encryptData;
				}else{
					return phone;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 加密电话号码
	 * @author HL
	 * @return
	 */
	private List<String> encryptListPhone(String userId,List<String> phones){
		
		List<String> list = new ArrayList<String>();
		String session = userInfoService.findUserInfoTokens(userId);
		if(null != session && !"".equals(session)){
			if(null!= phones && phones.size()>0){
				for (String phone : phones) {
					try {
						if(!EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
							String encryptData = EncrptAndDecryptClient.getInstance().encryptData(phone, EncrptAndDecryptClient.PHONE,session);
							list.add(encryptData);
						}else{
							list.add(phone);
						}
					} catch (Exception e) {
						try {
							if(!EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
								session = userInfoService.findUserInfoTokens(userId);
								String encryptData = EncrptAndDecryptClient.getInstance().encryptData(phone, EncrptAndDecryptClient.PHONE,session);
								list.add(encryptData);
							}else{
								list.add(phone);
							}
							
						} catch (SecretException e1) {
							e1.printStackTrace();
							logger.error("指定号码群发加密电话号码失败"+e.getMessage());
							return null;
						}
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 解密电话号码
	 * @author HL
	 * @return
	 */
	private List<String> decryptListPhone(String userId,List<String> phones){
		
		List<String> list = new ArrayList<String>();
		String session = userInfoService.findUserInfoTokens(userId);
		if(null != session && !"".equals(session)){
			if(null != phones && phones.size()>0){
				for (String phone : phones) {
					try {
						if(EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
							String decrypt = EncrptAndDecryptClient.getInstance().decrypt(phone, EncrptAndDecryptClient.PHONE,session);
							list.add(decrypt);
						}else{
							list.add(phone);
						}
					} catch (Exception e) {
						try {
							if(EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
								session = userInfoService.findUserInfoTokens(userId);
								String decrypt = EncrptAndDecryptClient.getInstance().decrypt(phone, EncrptAndDecryptClient.PHONE,session);
								list.add(decrypt);
							}else{
								list.add(phone);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
							logger.error("指定号码群发解密电话号码失败"+e1.getMessage());
							return null;
						}
					}
				}
			}
		}
			return list;
	}
	
	
	
	
	
	/**
	 * 异步处理短信数据，立即发送和定时发送
	 */
	private void threadManageSms(final String sendTimeType,
			final String[] phones, final String ipAddress,
			final String autograph, final String userId, final String content,
			final Long msgRecordId, final Integer totalCount,
			final int messageCount, final Date sTime, final Date endSend) {
		
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread() {
			@Override
			public void run() {
				// 立即发送:调用短信接口发送数据
				if (sendTimeType.equals("1")) {
					// 调用发送短信接口
					BatchSmsData batchSmsData = new BatchSmsData(phones);
					batchSmsData.setType(MsgType.MSG_ZDHMQF);
					batchSmsData.setIpAdd(ipAddress);
					batchSmsData.setChannel(autograph);
					batchSmsData.setAutograph(autograph);
					batchSmsData.setUserId(userId);
					batchSmsData.setContent(content);
					batchSmsData.setMsgId(msgRecordId);
					/*查询当前用户是否为vip*/
					boolean isVip = vipUserService.findVipUserIfExist(userId);
					batchSmsData.setVip(isVip);
					multithreadService.batchOperateSms(batchSmsData);
					int success = batchSmsData.getSuccess();
					int fail = batchSmsData.getFail();
					//更新总记录
					MsgSendRecord msg = new MsgSendRecord();
					msg.setId(msgRecordId);
					msg.setStatus(MsgType.MSG_STATUS_SENDOVER);
					msg.setSucceedCount(success);
					msg.setFailedCount(fail);
					mssgSendRecordService.updateMsgRecordById(msg);


					// 定时发送:保存到数据库
				} else if (sendTimeType.equals("2")) {
					// 定时发送:保存到数据库,以10万长度为节点 拆封保存定时
					List<String> asList = Arrays.asList(phones);
			    	int dataSize = asList.size();
			    	int start = 0,end =0,node = 100000;
			    	List<String> subList = null;
			    	if(dataSize/node==0){
			    		end = 1;
			    	}else if(dataSize%node==0){
			    		end = dataSize/node;
			    	}else{
			    		end = (dataSize+node)/node;
			    	}
			    	while (start<end) {
			    		if(start==(end-1)){
			    			subList = asList.subList(start*node, dataSize);
			    		}else{
			    			subList = asList.subList(start*node, (start+1)*node);
			    		}
			    		start++;
			    		
			    		//加密电话号码
		    			List<String> encryptListPhone = encryptListPhone(userId, subList);
		    			if(null != encryptListPhone && encryptListPhone.size()>0){
		    				insertScheduleSend(encryptListPhone,userId,messageCount,content,sTime,endSend,autograph,msgRecordId);
		    			}else{
		    				insertScheduleSend(subList,userId,messageCount,content,sTime,endSend,autograph,msgRecordId);
		    			}
			    	}
			    	
			    	//更新总记录
					MsgSendRecord msg = new MsgSendRecord();
					msg.setId(msgRecordId);
					msg.setStatus(MsgType.MSG_STATUS_SENDOVER);
					mssgSendRecordService.updateMsgRecordById(msg);

				}

			}

		});
	}
	
	/**
	 * 批量插入定时数据
	 * @author Administrator_HL
	 * @data 2017年6月12日 下午2:40:23
	 */
	private void insertScheduleSend(List<String> subList, String userId,
			int messageCount, String content, Date sTime, Date endSend,
			String autograph, Long msgRecordId) {
		String phones = StringUtils.join(subList.toArray(), ",");
		// 创建SmsSendInfo封装数据调用短信发送方法
		SmsSendInfo smsSendInfo = new SmsSendInfo();
		smsSendInfo.setPhone(phones);// 接收人手机号
		smsSendInfo.setUserId(userId);// 用户ID
		smsSendInfo.setType(MsgType.MSG_ZDHMQF);// 短信类型
		smsSendInfo.setActualDeduction(messageCount);// 实际扣除短信条数
		smsSendInfo.setContent(content);// 短信内容
		smsSendInfo.setStartSend(sTime);// 短信定时开始时间
		smsSendInfo.setEndSend(endSend);// 短信定时结束小时
		smsSendInfo.setChannel(autograph);// 短信渠道
		smsSendInfo.setCreatedDate(new Date());// 创建时间
		smsSendInfo.setMsgId(msgRecordId);// 总记录id
		myBatisDao.execute(SmsSendInfo.class.getName() + "Schedule",
				"doCreateByScheduleSend", smsSendInfo);
	}
	
	
	
	/**
	 *  ========发送电话导入部分===========
	 */
	@RequestMapping(value = "/uploadFileIsPhone", method = RequestMethod.POST)
	public String uploadFileIsPhone(@RequestParam MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		// userId = "crzzyboy";

		// 初始默认2--导入中
		String flagPhone = "2";

		// 获取文件名
		String fileName = file.getOriginalFilename();

		// 6.定义List把电话放入
		List<List<String>> dataLists = null;
		try {
			// 读取整个excel文档
			dataLists = ExcelImportUtils.gainExcelData(file);
		} catch (Exception e) {
			flagPhone = "1";
			dataLists = null;
		}
		// 上传的总电话条数
		int sendNumber = 0;
		// 将数据循环放入Set中,去重
		Set<String> phoneSet = new HashSet<String>();
		if (dataLists != null && dataLists.size() > 0) {
			try {
				if (!PhoneRegUtils.phoneValidate(dataLists.get(0).get(0))) {
					dataLists.remove(0);// 删除第一行数据为手机号的
				}
			} catch (Exception e) {
			}
			sendNumber = dataLists.size();
			for (List<String> list : dataLists) {
				try {
					String data = list.get(0);
					phoneSet.add(data.trim());
				} catch (Exception e) {
				}
			}
		}

		// 记录重复的数据
		int repetitionNum = sendNumber - phoneSet.size();


		//创建导入记录
		SmsSendlistImport sendlistImport = new SmsSendlistImport();
		// 封装数据
		sendlistImport.setFileName(fileName);// 上传的文件名称
		if(flagPhone.equals("1")){
			sendlistImport.setState(1);// 0--导入完成 1--导入失败 2--导入中
		}else{
			sendlistImport.setState(2);// 0--导入完成 1--导入失败 2--导入中
		}
		sendlistImport.setUserId(userId);// 操作用户
		sendlistImport.setSendNumber(sendNumber);// 上传的总电话条数
		sendlistImport.setSuccessNumber(0);// 导入成功的电话条数
		sendlistImport.setErrorNumber(repetitionNum);// 导入失败的电话条数
		sendlistImport.setImportTime(new Date());// 导入时间
		// 保存数据
		Long importId = smsSendlistImportService.insertSmsSendlistImport(
				sendlistImport, request);

		// 开启线程导入数据
		this.importPhoneThread(phoneSet, importId,userId);

		// 创建对象封装参数,输出到前台
		JSONObject json = new JSONObject();
		json.putOnce("error", flagPhone);
		// 将封装好的json数据,输出到前台
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().write(json.toString());

		return null;
	}


	/**
	 * 使用线程处理电话号码
	 * @param userId 
	 */
	private void importPhoneThread(final Set<String> phoneSet,
			final Long importId, final String userId) {
		MyFixedThreadPool.getMyFixedThreadPool().execute(new Thread() {
			@Override
			public void run() {
				// 接收成功上传的电话号码
				String importPhone = "";
				// 导入成功的电话条数
				int successNumber = 0;
				// 导入失败的电话条数
				int errorNumber = 0;

				// 定义变量循环次数
				int i = 0;
				for (String phone : phoneSet) {
					i++;
					if (PhoneRegUtils.phoneValidate(phone)) {
						importPhone += phone + ",";
						successNumber++;
					} else {
						errorNumber++;
					}
					if (i % 100 == 0 && i != 0) {
						updateSmsSendlistImportById(importId, successNumber,
								errorNumber);
						i = 0;
						successNumber = 0;
						errorNumber = 0;
					}
				}
				if (importPhone != "") {
					importPhone = importPhone.substring(0,
							importPhone.length() - 1);
				}
				updateSmsSendlistImportToState(importId, successNumber,
						errorNumber, importPhone,userId);
			}
		});
	}

	/**
	 * 通过id修改导入数据变量
	 * @param id
	 */
	private void updateSmsSendlistImportById(Long id,int successNumber, int errorNumber ){
		SmsSendlistImport smsSendlistImport = new SmsSendlistImport();
		smsSendlistImport.setId(id);
		smsSendlistImport.setSuccessNumber(successNumber);
		smsSendlistImport.setErrorNumber(errorNumber);
		myBatisDao.execute(SmsSendlistImport.class.getName(), "updateSmsSendlistImportById", smsSendlistImport);
	}
	
	/**
	 * 通过id将所以的数据补全和状态改变
	 * @param userId 
	 */
	private void updateSmsSendlistImportToState(Long importId,
			int successNumber, int errorNumber, String importPhone, String userId) {
		SmsSendlistImport smsSendlistImport = new SmsSendlistImport();
		smsSendlistImport.setId(importId);
		smsSendlistImport.setSuccessNumber(successNumber);
		smsSendlistImport.setErrorNumber(errorNumber);
		
		//加密电话号码
		if(null !=importPhone && !"".equals(importPhone)){
			String[] split = importPhone.split(",");
			List<String> asList = Arrays.asList(split);
			List<String> encryptListPhone = encryptListPhone(userId, asList);
			if(null!=encryptListPhone && encryptListPhone.size()>0){
				String phones = StringUtils.join(encryptListPhone.toArray(), ",");
				smsSendlistImport.setImportPhone(phones);
			}else{
				smsSendlistImport.setImportPhone(importPhone);
			}
				
		}
		try {
			myBatisDao.execute(SmsSendlistImport.class.getName(), "updateSmsSendlistImportById", smsSendlistImport);
			myBatisDao.execute(SmsSendlistImport.class.getName(), "updateSmsSendlistImportToStateOne", importId);
			myBatisDao.execute(SmsSendlistImport.class.getName(), "updateSmsSendlistImportToStateTwo", importId);
		} catch (Exception e) {
			myBatisDao.execute(SmsSendlistImport.class.getName(), "updateSmsSendlistImportByError", importId);
		}
	}

	/**
	 * 查询到导入的发送电话号码
	 */
	@RequestMapping(value = "/findSmsSendlist")
	public String findSmsSendlist(Model model, String beginTime,
			String endTime, String fileName, Integer pageNo,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		// String userId = "crzzyboy";

		// 将前台传入的字符串日期转换成Date类型
		Date bTime = null;// 起始时间
		Date eTime = null;// 结束时间
		if (beginTime != null && !"".equals(beginTime)) {
			bTime= DateUtils.convertStringToDate(beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			eTime= DateUtils.convertStringToDate(endTime);
		}

		// 查询导入的电话号码记录
		String contextPath = request.getContextPath();
		List<SmsSendlistImport> smsSendlistImport = smsSendlistImportService
				.findPaginationBySmsSendlist(contextPath, bTime, eTime, pageNo,
						fileName, userId);

		// 创建对象封装参数,输出到前台
		JSONObject json = new JSONObject();
		json.put("paginationSendlist", smsSendlistImport);
		// 将封装好的json数据,输出到前台
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 删除导入的数据
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteSmsSendlist")
	public String deleteSmsSendlist(Model model, String sendlistId,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		// String userId = "crzzyboy";

		// 创建对象封装参数,输出到前台
		JSONObject json = new JSONObject();

		// 调用service
		try {
			smsSendlistImportService.deleteSmsSendlistById(sendlistId, request,
					userId);
			json.put("del", 0);
		} catch (Exception e) {
			json.put("del", 1);
		} finally {
			// 将封装好的json数据,输出到前台
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(json.toString());
		}

		return null;

	}

	/**
	 * 异步请求获取导入的电话号码数据
	 * 
	 */
	@RequestMapping(value = "/findImportPhoneById")
	public String findImportPhoneById(String sendlistId,
			HttpServletResponse response) {
		// 获取查询的数据
		SmsSendlistImport smsSendlist = smsSendlistImportService
				.findImportPhoneByIdIsState(sendlistId);

		// 创建对象封装参数,输出到前台
		JSONObject json = new JSONObject();
		json.put("state", smsSendlist.getState());
		json.put("fileName", smsSendlist.getFileName());
		// 将封装好的json数据,输出到前台
		response.setContentType("application/json; charset=utf-8");
		try {
			response.getWriter().write(json.toString());
		} catch (IOException e) {
		}
		return null;

	}



	/**
	 * @Title: specificNumSendAgain
	 * @Description: (短信发送记录:再次发送调转指定号码发送)
	 * @param model
	 * @param request
	 * @param recorId
	 * @param type
	 * @return String 返回类型
	 * @throws
	 * @date 2017年5月5日 下午4:24:13
	 * @author jackstraw_yu
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/specificNumSendAgain")
	public String specificNumSendAgain(Model model, HttpServletRequest request,
			Integer recordId, String type) {
		String userId = (String) request.getSession().getAttribute(
				"taobao_user_nick");
		// 1根据总记录id发送的模板内容
		String content = msgSendRecordService.querySmsRecordContent(userId,
				recordId, type);

		// 1根据总记录id查询出全部的手机号--(失败的,重复的.....发送记录都要)
		/*List<String> phoneList = smsSendRecordService.querySmsRecordMobiles(
				userId, recordId, type);*/
		List<String> phoneList =  smsRecordService.querySmsRecordMobiles(
									userId, recordId, type);
		//解密电话号码
		List<String> list = decryptListPhone(userId, phoneList);
		String phones = null;
		if (list != null && list.size() > 0) {
			phones = StringUtils.join(list.toArray(), ",");
		}

		/*
		 * 业务逻辑................... *
		 */
		model.addAttribute("againPhones", phones);
		model.addAttribute("againContent", content);
		return "crms/marketingCenter/appointNumberSend";
	}

	/**
	 * 去除重复手机号 helei 2017年2月14日下午5:25:55
	 */
	private Map<String, Object> removePhone(String recNumRequest) {
		Map<String, Object> mapPhones = new HashMap<String, Object>();

		// 将数据循环放入Set中,去重
		Set<String> phoneSet = new HashSet<String>();// 正确的手机号码
		List<String> wrongNums = new ArrayList<String>();// 错误的手机号码
		List<String> repeatNums = new ArrayList<String>();// 重复的手机号码
		Integer totalCount = 0;// 短信批量发送总数

		if (recNumRequest != null && !"".equals(recNumRequest)) {
			String[] recNumArr = recNumRequest.split(",");
			totalCount = recNumArr.length;
			// 转换arr为list
			List<String> asList = Arrays.asList(recNumArr);
			Iterator<String> iterator = asList.iterator();
			while (iterator.hasNext()) {
				String num = iterator.next();
				if (PhoneRegUtils.phoneValidate(num)) {
					phoneSet.add(num);
				} else {
					wrongNums.add(num);
				}
			}

			// 将asList赋值给repeatNums
			repeatNums.addAll(asList);
			// 移除正确的号码和错误的号码 repeatNums内都是剩余重复的的手机号
			for (String string : phoneSet) {
				repeatNums.remove(string);
			}
			for (String string : wrongNums) {
				repeatNums.remove(string);
			}

		}

		mapPhones.put("phoneSet", phoneSet);
		mapPhones.put("wrongNums", wrongNums);
		mapPhones.put("repeatNums", repeatNums);
		mapPhones.put("totalCount", totalCount);
		return mapPhones;
	}
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/showEncryptImportPhone")
	public String showEncryptImportPhone() {
		return "/crms/header/encryptImportPhone";

	}
	
	
	
	/**
	 * 将线上已导入的数据进行加密
	 * @author HL
	 * @param pageNum
	 * @param id
	 */
	@RequestMapping(value = "/encryptImportPhone")
	public void encryptImportPhone(Integer pageNum, Long id, String userId,
			HttpServletResponse response) {
		if(null==pageNum){
			pageNum = 0;
		}else if(pageNum<0){
			pageNum = 0;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageSize", 10);
		map.put("id", id);
		map.put("userId", userId);
		Integer success=0;
		JSONObject error = new JSONObject();
		List<SmsSendlistImport> List= null;
			do {
				map.put("pageNum", pageNum);
				List = myBatisDao.findList(SmsSendlistImport.class.getName(), "findImportPhoneGoEncrypt", map);
					for (SmsSendlistImport smsSendlistImport : List) {
						pageNum++;
						String importPhone = smsSendlistImport.getImportPhone();
						if(null !=importPhone && !"".equals(importPhone)){
							String[] split = importPhone.split(",");
							List<String> asList = Arrays.asList(split);
							try {
								List<String> encryptListPhone = encryptListPhoneTo(smsSendlistImport.getUserId(), asList);
								if(null !=encryptListPhone && encryptListPhone.size()>0){
									Map<String,Object> mapUp = new HashMap<String,Object>();
									String phones = StringUtils.join(encryptListPhone.toArray(), ",");
									mapUp.put("id", smsSendlistImport.getId());
									mapUp.put("importPhone", phones);
									int execute = myBatisDao.execute(SmsSendlistImport.class.getName(), "updateImportPhoneGoEncrypt", mapUp);
									if(execute>0){
										success++;
										logger.info("指定号码群发加密成功--："
												+ "--pageNum：" + pageNum
												+ "--id：" + smsSendlistImport.getId()
												+ "--userId："
												+ smsSendlistImport.getUserId());
									}
								}
							} catch (Exception e) {
								logger.info("指定号码群发加密失败--："+e.getMessage()
										+ "--pageNum：" + pageNum
										+ "--id：" + smsSendlistImport.getId()
										+ "--userId："
										+ smsSendlistImport.getUserId());
								
								error.put(smsSendlistImport.getId()+"", smsSendlistImport.getUserId());
								Map<String,Object> exceptionMap = new HashMap<String,Object>();
								String exception = e.getMessage();
								exceptionMap.put("message",(exception.length()>50000)?exception.substring(0, exception.length()/2):exception);
								exceptionMap.put("userNick", smsSendlistImport.getUserId());
								exceptionMap.put("tid", smsSendlistImport.getId());
								//保存异常信息
								this.insertExceptionMap(exceptionMap);
							}
							
						}
				 }
					
			} while (List!=null && List.size()>0);
			try {
				JSONObject json = new JSONObject();
				json.put("error", error);
				json.put("success", success);
				response.setContentType("application/json; charset=utf-8");
				response.getWriter().write(json.toString());
			} catch (Exception e) {
			}
	}
	
	
	private List<String> encryptListPhoneTo(String userId,List<String> phones) throws Exception{
		List<String> list = new ArrayList<String>();
		String session = userInfoService.findUserInfoTokens(userId);
		if(null != session && !"".equals(session)){
			if(null!= phones && phones.size()>0){
				for (String phone : phones) {
					if(!EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
						String encryptData = EncrptAndDecryptClient.getInstance().encryptData(phone, EncrptAndDecryptClient.PHONE,session);
						list.add(encryptData);
					}else{
						list.add(phone);
					}
				}
			}
		}
		return list;
	}
	
	
	
	
	private void insertExceptionMap(Map<String, Object> map) {
		myBatisDao.execute(SmsSendlistImport.class.getName(), "insertExceptionMap", map);
	}
}
