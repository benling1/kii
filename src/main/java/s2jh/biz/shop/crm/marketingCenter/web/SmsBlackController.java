package s2jh.biz.shop.crm.marketingCenter.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.CacheService;
import lab.s2jh.module.sys.entity.DataDict;
import lab.s2jh.module.sys.service.DataDictService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.taobao.api.SecretException;

import s2jh.biz.shop.crm.buyers.service.MemberInfoService;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.service.VipMemberService;
import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.marketingCenter.service.ProcessImportDataQueue;
import s2jh.biz.shop.crm.marketingCenter.service.ProcessImportDataQueue.SmsBlackProcessor;
import s2jh.biz.shop.crm.message.dao.SmsSendlistImportDao;
import s2jh.biz.shop.crm.message.entity.SmsBlacklist;
import s2jh.biz.shop.crm.message.entity.SmsSendlistImport;
import s2jh.biz.shop.crm.message.service.SmsBlackListService;
import s2jh.biz.shop.crm.message.service.SmsMobileService;
import s2jh.biz.shop.crm.message.service.SmsSendlistImportService;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.crm.user.service.UserOperationLogService;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.ExcelImportUtils;
import s2jh.biz.shop.utils.getIpAddress;
import s2jh.biz.shop.utils.pagination.Pagination;
import s2jh.biz.shop.utils.phoneRegExp.PhoneRegUtils;

@Controller
public class SmsBlackController extends BaseController{

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SmsBlackListService smsBlacklistService;

	@Autowired
	private UserOperationLogService userOperationLogService;

	@Autowired
	private DataDictService dataDictService;

	@Autowired
	private SmsMobileService smsMobileService;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Autowired
	private VipMemberService vipMemberService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private SmsSendlistImportDao smsSendlistImportDao;
	
	@Autowired
	private SmsSendlistImportService smsSendlistImportService;
	
	@Autowired
	private ProcessImportDataQueue processImportDataQueue;
	
	@Autowired
	private CacheService cacheService;
	
	private static final Log logger = LogFactory.getLog(SmsBlackController.class);
	
	/**
	 * Gg
	 * 黑名单添加
	 * @param smsBlacklist
	 * @param request
	 * @param response
	 * @param type
	 * Gg
	 * @throws SecretException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/smsBlack", method = RequestMethod.POST)
	public void addBlackList(SmsBlacklist smsBlacklist,HttpServletRequest request,
			HttpServletResponse response,String type) throws SecretException{
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		JSONObject js = new JSONObject();
		//查询添加内容是否已在黑名单中
		List<SmsBlacklist> lists = new ArrayList<SmsBlacklist>();
		List<String> contentList = new ArrayList<String>();
		if(smsBlacklist!=null && smsBlacklist.getContent() != null&&!"".equals(smsBlacklist.getContent())){
			if("1".equals(type)){
				contentList.add(securityClient.encrypt(smsBlacklist.getContent(), EncrptAndDecryptClient.PHONE, this.getSessionkey(userId)));
			}else{
				contentList.add(securityClient.encrypt(smsBlacklist.getContent(), EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId)));
			}
			lists =smsBlacklistService.findContent(userId, contentList);
			if(lists!=null && lists.size()>0){
				js.put("message", "添加内容已存在黑名单中!");
				response.setContentType("application/json;charset=UTF-8");
				try {
					response.getWriter().write(js.toJSONString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
		try {
			if(type!=null && !"".equals(type)){
				smsBlacklistService.saveSmsBlacklist(smsBlacklist, userId, request, type,this.getSessionkey(userId));
				js.put("message", true);
				response.setContentType("application/json;charset=UTF-8");
			}else{
				js.put("message", false);
				response.setContentType("application/json;charset=UTF-8");
			}
		} catch (Exception e) {
			// TODO: handle exception
			js.put("message", false);
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    /**Gg
     * 黑名单移除(加解密)
     * 将移出的客户恢复为正常用户
     * @param smsBlacklist
     * @param model
     * @param response
     * @param id
     * @param request
     * Gg
     */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/smsBlackDelete", method = RequestMethod.POST)
	public void smsBlackDelete(SmsBlacklist smsBlacklist, Model model,
			HttpServletResponse response, Integer id, HttpServletRequest request,
			String content) {
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		//String session = "70002100603f1671bf26f8ea6c49e1b99e770ac3278548eee7641f2728bb67dd6be52812529184045";
		String userId = (String) request.getSession().getAttribute(
				"taobao_user_nick");
		JSONObject js = new JSONObject();
		MemberDTO memberInfo = null;
		try {
			//通过 content 查询type 类型
			if(content !=null && !"".equals(content)){
				String encryContent = securityClient.encrypt(content, EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId));
				String type = smsBlacklistService.findContentType(userId, encryContent);
				if(type!=null && "2".equals(type)){
					smsBlacklistService.updateIsdelete(encryContent,userId,type);
					//将移出的会员回复为正常用户
					Long count1 = vipMemberService.queryMemberByNick(encryContent, userId);
					if(count1!=0){
						memberInfo = new MemberDTO();
						memberInfo.setUserId(userId);
						memberInfo.setBuyerNick(encryContent);
						memberInfo.setBlackStatus(0);
						vipMemberService.findListMemberDTOAndUpdate(memberInfo);
					}
					js.put("message", true);
				}else{
					//反之 加密 类型用 phone
					String encryPhone = securityClient.encrypt(content, EncrptAndDecryptClient.PHONE, this.getSessionkey(userId));
					smsBlacklistService.updateIsdelete(encryPhone,userId,"1");
					//将移出的会员回复为正常用户
					Long count = vipMemberService.queryMembersByMobile(encryPhone, userId);
					if(count!=0){
						memberInfo = new MemberDTO();
						memberInfo.setUserId(userId);
						memberInfo.setPhone(encryPhone);
						memberInfo.setBlackStatus(0);
						vipMemberService.findListMemberDTOAndUpdate(memberInfo);
					}
					js.put("message", true);
				}
			}else{
				js.put("message", false);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		UserOperationLog op = new UserOperationLog();
		op.setUserId(userId);
		op.setDate(new Date());
		op.setIpAdd(getIpAddress.getIpAddress(request));
		op.setFunctions("移出黑名单");
		op.setType("移出");
		op.setRemark("移出短信黑名单");
		op.setState("成功");
		op.setFunctionGens("33");
		userOperationLogService.save(op);
		
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gg
	 * 黑名单清空
	 * @param request
	 * @param response
	 * Gg
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteSmsBlack", method = RequestMethod.POST)
	public void deleteSmsBlackList(HttpServletRequest request,HttpServletResponse response){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		JSONObject js = new JSONObject();
		try {
			vipMemberService.updateMemberInfoStatus(userId);
			js.put("message", true);
			smsBlacklistService.deleteSmsBlack(userId);
		} catch (Exception e) {
			// TODO: handle exception
			js.put("message", false);
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 批量添加手机号
	 * 
	 */
	@SuppressWarnings({ "unchecked","unused" })
	@RequestMapping(value = "/uploadMemberInfoPhone", method = RequestMethod.POST)
	public void uploadFilesMemberInfoPhone(@RequestParam MultipartFile file,HttpServletRequest request,
		String type,HttpServletResponse response){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		String fileName = file.getOriginalFilename();
		JSONObject js = new JSONObject();
		List<List<String>> dataLists = null;
		long s1 = System.currentTimeMillis();
		try {
			// 读取整个excel文档
			dataLists = ExcelImportUtils.gainExcelData(file);
			logger.info("=========================用户:"+userId+"导入黑名单耗时:"
					+(System.currentTimeMillis()-s1)+"=========================");
		} catch (Exception e) {
			logger.info("=========================用户:"+userId+"导入黑名异常!!!");
			dataLists =null;
		}
		if(dataLists!=null && dataLists.size()!=0){
			//将解析出来的手机号放入mobileList中
			List<String> datas = new  ArrayList<String>();
			logger.info("=========================用户:"+userId+"导入黑名单数据量:"
					+dataLists.size()+"=========================");
			//type为1时验证是否为手机号
			if("1".equals(type)){
				if (!PhoneRegUtils.phoneValidate(dataLists.get(0).get(0))) {
					dataLists.remove(0);// 删除第一行数据为手机号的
				}
			}else{
				dataLists.remove(0);// 删除第一行数据为手机号的
			}
			for (int i = 0; i < dataLists.size(); i++) {
				List<String> list = dataLists.get(i);
				String mobile = list.get(0);
				datas.add(mobile);
			}
			//记录解析的手机号数
			int count = datas.size();
			if(type!=null){
				//添加一条导入记录
				this.addSmsImportRecord(userId,fileName,count);
				//开始批量处理数据
				processImportDatas(datas, userId,type,this.getSessionkey(userId));
				//processImportDatas2(datas, userId,type,this.getSessionkey(userId));
				//processUpdateMemberInfoStatus(datas,userId,type);
				js.put("message", true);
			}else{
				js.put("message", false);
			}
		}else{
			js.put("message", false);
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 多线程处理保存数据
	 * @param datas
	 * @param userId
	 * @param type
	 */
	private void processImportDatas(List<String> datas,String userId,String type,String session){
		int size = datas.size();
		SmsBlackProcessor process = processImportDataQueue.startProcess();	
		process.start();
		int turn ,start=0;
		if(size/500==0){
			turn = 1;
		}else if(size%500==0){
			turn  = size/500;
		}else{
			turn  = (size+500)/500;
		}
		while(start<turn){
			Map<String,Object> map =null;
			List<String> subList = null;
			if(start==turn-1){
				subList = new ArrayList<String>(datas.subList(start*500, datas.size()));
			}else{
				subList = new ArrayList<String>(datas.subList(start*500, (start+1)*500));
			}
			if(subList==null||subList.isEmpty())
				break;
			try {
				map = new HashMap<String, Object>();
				map.put("type", type);
				map.put("userId", userId);
				map.put("datas", subList);
				map.put("session", session);
				ProcessImportDataQueue.smsBlackQueue.put(map);
				start++;
			} catch (InterruptedException e) {
				e.printStackTrace();
				continue;
			}
		}
		processImportDataQueue.killProcess(process);
	}
	
	/*@Autowired
	private SmsBlackDataProcessor smsBlackDataProcessor;*/
	
	//测试
	/*private void processImportDatas2(List<String> datas,String userId,String type,String session){
		int size = datas.size();
		CoreProcessor processor = smsBlackDataProcessor.getProcess();
		//process.start();
		int turn ,start=0;
		if(size/500==0){
			turn = 1;
		}else if(size%500==0){
			turn  = size/500;
		}else{
			turn  = (size+500)/500;
		}
		while(start<turn){
			Map<String,Object> map =null;
			List<String> subList = null;
			if(start==turn-1){
				subList = new ArrayList<String>(datas.subList(start*500, datas.size()));
			}else{
				subList = new ArrayList<String>(datas.subList(start*500, (start+1)*500));
			}
			if(subList==null||subList.isEmpty())
				break;
			try {
				map = new HashMap<String, Object>();
				map.put("type", type);
				map.put("userId", userId);
				map.put("datas", subList);
				map.put("session", session);
				processor.getProductQueue().add(map);
				start++;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		processor.start();
		smsBlackDataProcessor.killProcessor(processor);
	}*/
	
	/**
	 * Gg
	 * 黑名单主页面查询
	 * @param model
	 * @param content
	 * @param request
	 * @param pageNo
	 * @return
	 * Gg
	 * @throws SecretException 
	 */
	@RequestMapping(value = "/crms/marketingCenter/smsBlack")
	public String MarketingCenter(Model model,HttpServletRequest request,String content,Integer pageNo) throws SecretException {
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		Map<String, Object> params = new HashMap<String, Object>();
		// 黑名单类型
		List<DataDict> smsBlack = dataDictService.findChildrenByPrimaryKey(ConstantUtils.TypeBlack);
		params.put("smsBlack", smsBlack);
		//批量添加黑名单列表
		List<SmsSendlistImport> smsSendlistImportList = smsSendlistImportService.findImImportList(userId);
		params.put("smsSendlistImportList", smsSendlistImportList);
		model.addAttribute("params", params);
		if (content != null && "".equals(content.trim()))content = null;
		String contextPath = request.getContextPath();
		Pagination pagination = smsBlacklistService.findPaginationByContent(
				userId, contextPath, content, pageNo,this.getSessionkey(userId));
		model.addAttribute("pagination", pagination);
		model.addAttribute("smsBlacklist", new SmsBlacklist());
		model.addAttribute("phone", content);
		
		return "crms/marketingCenter/smsBlack";

	}
	
	
	/**
	 * Gg
	 * 删除导入黑名单文件
	 * @param model
	 * @param response
	 * @param id
	 * @param request
	 * GG
	 */
	@RequestMapping(value = "/removeSmsImport",method = RequestMethod.POST)
	public void removeSmsImprot(Model model,
			HttpServletResponse response, Integer id, HttpServletRequest request){
		String userId= (String) request.getSession().getAttribute(
				"taobao_user_nick");
		JSONObject js = new JSONObject();
		try {
			smsSendlistImportService.removeSmsInport(id,userId);
			js.put("message", true);
		} catch (Exception e) {
			js.put("message", false);
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gg
	 * 批量移除黑名单
	 * 根据内容删除
	 * @param content
	 * @param request
	 * Gg
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public void batchDeleteSmsBlack(HttpServletRequest request,HttpServletResponse response,
									String[] nicks,String[] mobiles){
		String userId= (String) request.getSession().getAttribute("taobao_user_nick");
		//加密接口
		EncrptAndDecryptClient securityClient = EncrptAndDecryptClient.getInstance();
		JSONObject js = new JSONObject();
		if((nicks==null || nicks.length==0)&&(mobiles==null || mobiles.length==0)){
			js.put("message", "请勾选要删除的内容!");
			js.put("status", false);
		}else{
			List<String> nickList = null;
			List<String> mobileList = null;
			List <String> encryptionNicks=new ArrayList<String>();
			List<String> encryptionMobiles=new ArrayList<String>();
			try {
				if((nicks!=null && nicks.length>0)){
					nickList = Arrays.asList(nicks);
					for (String nick : nicks) {
						String encryptionNick = securityClient.encrypt(nick, EncrptAndDecryptClient.SEARCH, this.getSessionkey(userId));
						encryptionNicks.add(encryptionNick);
					}
					smsBlacklistService.batchDeleteSmsBlack(encryptionNicks, userId);
				}
				if((mobiles!=null && mobiles.length>0)){
					mobileList = Arrays.asList(mobiles);
					for(String mobile :mobiles){
						String encryptionMobile = securityClient.encrypt(mobile, EncrptAndDecryptClient.PHONE, this.getSessionkey(userId));
						encryptionMobiles.add(encryptionMobile);
					}
					smsBlacklistService.batchDeleteSmsBlack(encryptionMobiles, userId);
				}
				if(nickList!=null&&!nickList.isEmpty())
					vipMemberService.removeMemberBlack(userId,encryptionNicks,"buyerNick");
				if(mobileList!=null&&!mobileList.isEmpty())
				js.put("status", true);
				vipMemberService.removeMemberBlack(userId,encryptionMobiles,"phone");
				js.put("message", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("=========================用户:"+userId+"移除黑名单时异常!!");
				js.put("status", false);
				js.put("message", "操作失败,请重新操作或联系系统管理员!");
			}
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(js.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gg
	 * 批量添加/导入黑名单时 保存一条导入记录
	 * Gg
	 */
	private void  addSmsImportRecord(String userId,String fileName,int count){
		//创建一条导入记录
		SmsSendlistImport smsSendlistImport = new SmsSendlistImport();
		//上传的文件名
		smsSendlistImport.setFileName(fileName);
		//导入状态(默认是导入中)
		smsSendlistImport.setState(0);
		//操作用户
		smsSendlistImport.setUserId(userId);
		//上传黑名单的总数量
		smsSendlistImport.setSendNumber(count);
		//默认上传成功数是全部数量
		smsSendlistImport.setSuccessNumber(count);
		//导入时间
		smsSendlistImport.setImportTime(new Date());
		//导入类型
		smsSendlistImport.setType("1");
		smsSendlistImportDao.save(smsSendlistImport);
	}

	/**
	 * 获取session
	 * @param userId
	 * @return
	 */
	private String getSessionkey(String userId){
		String  session = cacheService.getJsonStr(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userId);
		if(session!=null && !"".equals(session)){
			return session;
		}else{
			UserInfo user = userInfoService.queryUserTokenInfo(userId);
			if(user!=null){
				if(null!=user.getAccess_token()&&!"".equals(user.getAccess_token())){
					cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userId,user.getAccess_token());
					return user.getAccess_token(); 
				}
			}
		}
		return "";
	}
}
