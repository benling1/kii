package s2jh.biz.shop.crm.message.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.service.SmsRecordService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsBlacklist;
import s2jh.biz.shop.crm.message.entity.SmsReceiveInfo;
import s2jh.biz.shop.crm.message.service.SmsBlackListService;
import s2jh.biz.shop.crm.message.service.SmsReceiveInfoService;
import s2jh.biz.shop.crm.message.templateVo.SmsReceiveVO;
import s2jh.biz.shop.crm.tradecenter.vo.SmsRecordDTOVo;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.support.pojo.BatchSmsData;
import s2jh.biz.shop.support.service.MultithreadBatchSmsService;
import s2jh.biz.shop.utils.getIpAddress;

@Controller
@RequestMapping(value="/smsReceiveInfo")
public class SmsReceiveInfoController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(SmsReceiveInfoController.class);
	
	@Autowired
	private SmsReceiveInfoService smsReceiveInfoService;
	
	@Autowired
	private SmsRecordService smsRecordService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private SmsBlackListService smsBlackListService;
	
	@Autowired
	private MultithreadBatchSmsService  multithreadService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	//根据卖家昵称查询所有接收到的短信记录
	@RequestMapping(value="",method=RequestMethod.GET)
	public String findAllSmsReceiveInfo(Model model){
		List<SmsReceiveInfo> list =smsReceiveInfoService.findSmsReceiveInfo("邱洋");
		if(list!=null&&list.size()>0){
			model.addAttribute("SmsReceiveInfolist", list);
		}
		return "crms/storeData/kehuguanli";
	}
	
	//根据卖家条件查询接收到的短信记录
	@RequestMapping(value="/querySmsReceiveInfo",method=RequestMethod.GET)
	public String querySmsReceiveInfo(Model model){
		String taobaoNick=null;
		String sendphone=null;
		String status =null;
		String startDate=null;
		String endDate=null;
		String content =null;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("taobaoNick", taobaoNick);
		map.put("sendphone", sendphone);
		map.put("status", status);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("content", content);
		List<SmsReceiveInfo> list = smsReceiveInfoService.querySmsReceiveInfo(map);
		if(list!=null&&list.size()>0){
			model.addAttribute("SmsReceiveInfolist",list);
		}
		return "crms/storeData/kehuguanli";
	}
	
	/**
	 * 查询会员互动记录
	 * @Title: listSmsReceiveDetail 
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping(value="/records",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String listSmsReceiveDetail(HttpServletRequest request,String phone){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId == null || phone == null){
			return rsMap(102, "操作失败,请重新操作或联系管理员!").put("status", false).toJson();
		}
		SmsRecordDTOVo smsRecordDTOVo = new SmsRecordDTOVo();
		smsRecordDTOVo.setRecNum(phone);
		smsRecordDTOVo.setStatus(2);
		List<SmsRecordDTO> smsRecordDTOs = smsRecordService.listSmsRecordByQuery(smsRecordDTOVo, userId);
		List<SmsReceiveVO> smsReceiveVOs = new ArrayList<>();
		if(smsRecordDTOs != null && !smsRecordDTOs.isEmpty()){
			for (int i = 0; i < smsRecordDTOs.size(); i++) {
				SmsRecordDTO recordDTO = smsRecordDTOs.get(i);
				SmsReceiveVO smsVo = new SmsReceiveVO();
				smsVo.setContent(recordDTO.getContent());
				smsVo.setSendTime(recordDTO.getSendTime());
				smsVo.setRole("seller");
				smsVo.setPhone(recordDTO.getRecNum());
				smsReceiveVOs.add(smsVo);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("phone", phone);
		List<SmsReceiveVO> receiveVOs = smsReceiveInfoService.listReceiveInfoByBuyerNick(map);
		smsReceiveVOs.addAll(receiveVOs);
		smsReceiveVOs.removeAll(Collections.singleton(null));
		try {
			Collections.sort(smsReceiveVOs, new Comparator<SmsReceiveVO>() {
				@Override
				public int compare(SmsReceiveVO o1, SmsReceiveVO o2) {
					return o1.getSendTime().compareTo(o2.getSendTime());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsMap(100, "操作成功").put("status", true).put("data", smsReceiveVOs).toJson();
	}
	
	
	/**
	 * 发送短信
	 * ZTK2018年3月1日下午9:24:54
	 */
	@ResponseBody
	@RequestMapping(value="/receiveSendSms",produces="text/plain;charset=UTF-8")
	public String orderSmsSend(HttpServletRequest request,String phone,
			String signVal,String content){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId == null || phone == null){
			return rsMap(102, "操作失败,请重新操作或联系管理员!").put("status", false).toJson();
		}
		try {
			//判断短信签名
 			if(signVal != null && !"".equals(signVal)){
				String signValRe = signVal.replace("【", "").replace("】", "").replace(" ", "");
				if(signValRe == null || "".equals(signValRe)){
					return rsMap(101, "操作失败,短信签名不能为空或者空格").put("status", false).toJson();
				}
			}
 			List<String> phones = new ArrayList<>();
 			phones.add(phone);
 			List<SmsBlacklist> blackList = smsBlackListService.findSmsBlacklist(userId, phones);
 			if(blackList != null && !blackList.isEmpty()){
 				return rsMap(101, "操作失败,该手机号或买家已加入黑名单中").put("status", false).toJson();
 			}
			//计算短信长度
			int contentCount = content.length();
			if(contentCount <= 70){
				contentCount = 1;
			}else{
				contentCount = (contentCount + 66) / 67;
			}
			//判断短信数量
			Long smsNum = userAccountService.findUserAccountSms(userId);
			if(contentCount > smsNum){
				return rsMap(101, "操作失败,短信数量不足请充值").put("status", false).toJson();
			}
			//初始化加密 TODO
			EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
			String sessionKey = userInfoService.validateFindSessionKey(userId);
			if(EncrptAndDecryptClient.isEncryptData(phone, EncrptAndDecryptClient.PHONE)){
				phone = decryptClient.decryptData(phone, EncrptAndDecryptClient.PHONE, sessionKey);
			}
			String[] numbers = {phone};
			BatchSmsData batchSmsData = new BatchSmsData(numbers);
			batchSmsData.setIpAdd(getIpAddress.getIpAddress(request));
			batchSmsData.setUserId(userId);
			batchSmsData.setActualDeduction(contentCount);
			batchSmsData.setContent(content);
			batchSmsData.setAutograph(signVal);
			batchSmsData.setChannel(signVal);
			batchSmsData.setType("36");//会员互动
			multithreadService.batchOperateSms(batchSmsData);
		} catch (Exception e) {
			e.printStackTrace();
			return rsMap(102, "系统错误，请重新发送或联系管理员！").put("status", false).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).toJson();
	}	
	
}
