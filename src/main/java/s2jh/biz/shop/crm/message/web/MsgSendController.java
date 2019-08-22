package s2jh.biz.shop.crm.message.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.service.RedisLockServiceImpl;
import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.manage.vo.MemberCriteriaVo;
import s2jh.biz.shop.crm.manage.vo.SendMsgVo;
import s2jh.biz.shop.crm.message.service.MsgSendService;
import s2jh.biz.shop.crm.user.service.UserAccountService;
import s2jh.biz.shop.utils.MsgType;
import s2jh.biz.shop.utils.getIpAddress;

/** 
* @ClassName: MsgSendController 
* @Description:(会员短息群发>>发送短信) 
* @author jackstraw_yu 
* @date 2017年5月3日 上午10:18:04 
*/
@Controller
@RequestMapping(value = "/msgSend")
public class MsgSendController extends BaseController{

	@Autowired
	private MsgSendService msgSendService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private RedisLockServiceImpl redisLockServiceImpl;
	
	private  final Log logger = LogFactory.getLog(MsgSendController.class);
	
 
	/** 
	* @Title: memberMsgSend 
	* @Description:  (会员短息群发
	* 1,同步保存总记录然后跳转页面
	* 2,异步发送并更新总记录数据) 
	* @date 2017年5月22日 下午5:30:55
	* @author jackstraw_yu    
	* @throws 
	*/
	@RequestMapping(value = "/memberMsgSend")
	@ResponseBody
	public String memberMsgSend(HttpServletRequest request,
			HttpServletResponse response,SendMsgVo sendMsgVo ) {
		 	String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		 	sendMsgVo.setIpAddress(getIpAddress.getIpAddress(request));
		 	sendMsgVo.setMsgType(MsgType.MSG_HYDXQF);
		if(null!=userId&&!"".equals(userId)){
			sendMsgVo.setUserId(userId);
		/*MemberCriteriaVo memberCriteriaVo = cacheService.get(RedisConstant.RedisCacheGroup.MEMBER_BATCH_SEND_DATA_CACHE,
											RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+sendMsgVo.getQueryKey()+"-"+userId, MemberCriteriaVo.class);*/
			MemberCriteriaVo memberCriteriaVo =redisLockServiceImpl.getValue(
									RedisConstant.RediskeyCacheGroup.MEMBER_BATCH_SEND_DATA_KEY+"-"+sendMsgVo.getQueryKey()+"-"+userId,
									MemberCriteriaVo.class);
			if(sendMsgVo.getTotalCount()==null ||memberCriteriaVo==null
					||sendMsgVo.getTotalCount()!= memberCriteriaVo.getTotalCount())
				return rsMap(100, "操作失败,您的搜索被重置,请重新操作!").put("status", false).toJson();
			//sendMsgVo.setTotalCount(memberCriteriaVo.getTotalCount());
			if(sendMsgVo.getActivityName()!=null&&!"".equals(sendMsgVo.getActivityName()))
				sendMsgVo.setActivityName(sendMsgVo.getActivityName().trim()); 
			
			logger.info("会员短信群发开始,当前时间:"+new Date()+"^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
			Map<String, Object> oMap = validateUserOpterate(sendMsgVo);
			if ((boolean)oMap.get("flag") == false) {
				return rsMap(100, oMap.get("message")==null?"操作失败,请重新操作或联系系统管理员!":(String)oMap.get("message")).put("status", false).toJson();
			}
			//验证余额
			Map<String, Object> mMap = validateMessageCount( sendMsgVo);
			if ((boolean)mMap.get("flag") == false) {
				return rsMap(100, mMap.get("message")==null?"操作失败,请重新操作或联系系统管理员!":(String)mMap.get("message")).put("status", false).toJson();
			}
			if ("1".equals(sendMsgVo.getSendType())) {
				sendMsgVo.setSchedule(false);
			}else{
				sendMsgVo.setSchedule(true);
			}
			try {
				msgSendService.sendBatchMsg(sendMsgVo);
				return rsMap(100, sendMsgVo.getSchedule()?"定时保存成功!":"会员短信发送成功!").put("status", true).put("key", sendMsgVo.getSchedule()?"schedule":"").toJson();
			} catch (Exception e) {
				logger.error(sendMsgVo.getSchedule()?"会员短信群发定时保存异常!!":"会员短信群发立即发送异常!!"+"^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
				return rsMap(100, sendMsgVo.getSchedule()?"定时保存失败,请重新操作或者联系系统管理员!":"会员短信发送失败,请重新操作或者联系系统管理员!").put("status", false).toJson();
			}
		}else{
			return rsMap(100, "抱歉您的登录已经超时,请重新进入该系统再进行操作,谢谢!").put("status", false).toJson();
		}
	}
	
	
	/** 
	* @Title: validateUserOpterate 
	* @Description:  (会员短信群发:验证用户的一些选择上的操作) 
	* @date 2017年5月23日 上午9:25:45
	* @author jackstraw_yu    
	* @throws 
	*/
	private Map<String,Object> validateUserOpterate(SendMsgVo sendMsgVo){
		Map<String,Object> map = new HashMap<String,Object>();
		boolean flag = true;
		if (sendMsgVo.getContent() == null || "".equals(sendMsgVo.getContent())) {
			map.put("message", "短信内容不能为空!");
			flag = false;
		}else if (sendMsgVo.getAutograph() == null || "".equals(sendMsgVo.getAutograph())) {
			map.put("message", "请选择短信签名!");
			flag = false;
		}else if (sendMsgVo.getSendType() == null || "".equals(sendMsgVo.getSendType())) {
			map.put("message", "请选择立即发送或者定时发送!");
			flag = false;
		}else if ("2".equals(sendMsgVo.getSendType()) && (sendMsgVo.getSendTime() == null || "".equals(sendMsgVo.getSendTime()))) {
			map.put("message", "定时发送的时间不能为空!");
			flag = false;
		}else if("2".equals(sendMsgVo.getSendType()) && sendMsgVo.getSendTime() != null && !"".equals(sendMsgVo.getSendTime())){
			Date sTime =null;
			try {
				sTime = DateUtils.parseDate(sendMsgVo.getSendTime(), "yyyy-MM-dd HH:mm");
				if(sTime!=null && (sTime.getTime()-System.currentTimeMillis())<30*60*1000){
					map.put("message", "定时发送时间与当前时间间隔不能小于30分钟!");
					flag = false;
				}
			} catch (Exception e) {
				logger.error("会员短信群发定时发送时间转换异常!!^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^|^_^");
				map.put("message", "时间转换异常,请重新操作或者联系系统管理员!");
				flag = false;
			}
		}
		map.put("flag", flag);
		return map;
	}
	
	
	/** 
	* @Title: validateMessageCount 
	* @Description:  (会员短信群发:验证用户短信条数与用户余额) 
	* @date 2017年5月23日 上午9:36:27
	* @author jackstraw_yu    
	* @throws 
	*/
	private Map<String,Object> validateMessageCount(SendMsgVo sendMsgVo) {
		Map<String,Object> map = new HashMap<String,Object>();
		boolean flag = true;
		//会员短信剩余条数
		Long smsNum = userAccountService.findUserAccountSms(sendMsgVo.getUserId());
		//会员集合长度*每条短信大致要扣除的条数,即为要发送的短信的总条数
		int length = 1;
		if(sendMsgVo.getContent().length()>70){
			length = (sendMsgVo.getContent().length()+66)/67;
		}
		if(0 == smsNum){
			map.put("message", "短信剩余条数不足,请先充值后再发送,谢谢!");
			flag = false;
		}else if(smsNum<sendMsgVo.getTotalCount()*length){
			map.put("message", "短信剩余条数小于要发送的总条数!");
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}

	
}
