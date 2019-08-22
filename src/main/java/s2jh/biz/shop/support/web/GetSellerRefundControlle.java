package s2jh.biz.shop.support.web;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.message.entity.SmsReceiveInfo;
import s2jh.biz.shop.crm.message.service.SmsBlackListService;
import s2jh.biz.shop.crm.message.service.SmsReceiveInfoService;
import s2jh.biz.shop.crm.message.service.SmsSendRecordService;
import s2jh.biz.shop.crm.taobao.util.SyncJudgeRequestUtil;
import s2jh.biz.shop.crm.user.service.UserInfoService;

@Controller
public class GetSellerRefundControlle {
	
	private Logger logger = LoggerFactory.getLogger(GetSellerRefundControlle.class);
	
	@Autowired
	private SmsReceiveInfoService smsReceiveInfoService;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Autowired
	private SmsSendRecordService smsSendRecordService;
	
	@Autowired
	private SyncJudgeRequestUtil syncJudgeRequestUtil;
	
	@Autowired
	private VipMemberService VipMemberService;

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private SmsBlackListService smsBlacklistService;

	@RequestMapping("/testGetSellerRefund")
	public void testGetSellerRefund(String name,String src,String dest,String content,
			String time,String reference) throws ParseException{
		logger.debug("请求来啦~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		logger.debug("name:" + name + "  src:" + src + "   dest:" + dest + "   content:" + content + "   time:" + time
				+ "   reference:" + reference);
		if(!"".equals(src)){
			String receive = src+content;
			boolean result = syncJudgeRequestUtil.canExecute(receive);
			//初始化加密
			EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
			try {
				if(result){
					if(reference != null && !"".equals(reference)){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SmsReceiveInfo smsReceiveInfo = new SmsReceiveInfo();
						if(content == null){
							content = "";
						}
						String encodeContent = URLEncoder.encode(content, "utf-8");
						smsReceiveInfo.setContent(encodeContent);//回复内容
						smsReceiveInfo.setRemarks(dest);//备注
						smsReceiveInfo.setCreatedDate(new Date());
						String sessionKey = userInfoService.validateFindSessionKey(reference);
						if(EncrptAndDecryptClient.isEncryptData(src, EncrptAndDecryptClient.PHONE)){
							smsReceiveInfo.setSendPhone(src);//发送手机号
						}else {
							smsReceiveInfo.setSendPhone(decryptClient.encryptData(src, EncrptAndDecryptClient.PHONE, sessionKey));//发送手机号
						}
						smsReceiveInfo.setReceivePhone(dest);//接受号码
						smsReceiveInfo.setStatus(0);//是否已读 0:未读 1::已读
						logger.debug("1111~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						if(time != null){
							smsReceiveInfo.setReceiveDate(dateFormat.parse(time));//回复时间
						}
						smsReceiveInfo.setLastModifiedDate(new Date());
						//通过用户号码src查询买家和卖家(SmsSendRecord)
						/*MemberInfo memberInfo = memberInfoService.findMemberInfoByPhone(src);*/
						//SmsSendRecord sendRecord = smsSendRecordService.findRecordByPhone(src);
						MemberDTO memberDTO = null;
						List<MemberDTO> members = null;
						if(src != null && !"".equals(src) && reference != null && !"".equals(reference)){
							if(EncrptAndDecryptClient.isEncryptData(src, EncrptAndDecryptClient.PHONE)){
								//memberDTO = VipMemberService.findOne(new Query(Criteria.where("phone").is(src)), reference);
								members = VipMemberService.findList(new Query(Criteria.where("phone").is(src)), reference);
							}else {
								//memberDTO = VipMemberService.findOne(new Query(Criteria.where("phone").is(decryptClient.encrypt(src, EncrptAndDecryptClient.PHONE, sessionKey))), reference);
								members = VipMemberService.findList(new Query(Criteria.where("phone").is(decryptClient.encrypt(src, EncrptAndDecryptClient.PHONE, sessionKey))), reference);
							}
						}else {
							return ;
						}
						if(members != null && members.size()>0){
							memberDTO = anylsistMemberList(members);
						}
						logger.debug("22222~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						if(memberDTO != null){
							smsReceiveInfo.setTaobaoNick(memberDTO.getUserId());//淘宝昵称
							if(EncrptAndDecryptClient.isEncryptData(memberDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH)){
								smsReceiveInfo.setLastModifiedBy(memberDTO.getBuyerNick());
								smsReceiveInfo.setBuyerNick(memberDTO.getBuyerNick());//买家昵称
							}else {
								smsReceiveInfo.setBuyerNick(decryptClient.encryptData(memberDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey));//发送手机号
								smsReceiveInfo.setLastModifiedBy(decryptClient.encryptData(memberDTO.getBuyerNick(), EncrptAndDecryptClient.SEARCH, sessionKey));//发送手机号
							}
							smsReceiveInfo.setUserId(memberDTO.getUserId());//卖家ID
							logger.debug("333~~~~~~memberDTO不为空~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}else {
							smsReceiveInfo.setTaobaoNick(null);
							smsReceiveInfo.setLastModifiedBy(null);
							smsReceiveInfo.setUserId(null);
							smsReceiveInfo.setBuyerNick(null);
						}
						logger.debug("444~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						smsReceiveInfoService.insertReceiveInfo(smsReceiveInfo);
						logger.debug("smsReceiveInfoService 保存成功" + smsReceiveInfo.getSendPhone() + smsReceiveInfo.getContent());
						//客户回复退订回N 的短信,标记会员为黑名单
						if(encodeContent != null && memberDTO != null){
							String smsContent = encodeContent.trim().replaceAll("\\s", "").toUpperCase();
							if(smsContent.equals("N") || smsContent.equals("TD")){
								logger.info("555有回复退订!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
								// 黑名单状态 (blackStatus) 1表示是 0 表示不是
								VipMemberService.updateMemberBlackStatus(memberDTO, 1);
								//在黑名单列表中插入一条数据
								smsBlacklistService.addsmsBlacklist(memberDTO,"回复退订","1","1");
							}
						}
					}
				}else{
					logger.debug("消息推送重复了哦"+receive);	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/** 
	* 分析会员,获取一个 会员集合中id最小的
	* @param  members
	* @return MemberDTO    返回类型 
	* @author jackstraw_yu
	* @date 2017年11月16日 下午6:05:59
	*/
	private MemberDTO anylsistMemberList(List<MemberDTO> members){
		MemberDTO member =null;
		long timeStamp = 0,shift=0;
		if(members==null || members.isEmpty()){
			return null;
		}else if(members.size()==1){
			return members.get(0);
		}else{
			member = members.get(0);
			timeStamp = new ObjectId(member.get_id()).getTime();
			for (MemberDTO memberDTO : members) {
				shift = new ObjectId(memberDTO.get_id()).getTime();
				if(timeStamp > shift ){
					member = memberDTO;
					timeStamp = shift;
				}
			}
		}
		return member;
	}
	
}
