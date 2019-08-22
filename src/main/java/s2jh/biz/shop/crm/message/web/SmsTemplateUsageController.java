package s2jh.biz.shop.crm.message.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.message.service.SmsTemplateService;
import s2jh.biz.shop.crm.message.templateVo.SmsTemplateVo;
import s2jh.biz.shop.utils.JsonUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
@Controller
@RequestMapping(value = "/smsTemplateUsage")
public class SmsTemplateUsageController extends BaseController{
	
	private static final Log logger = LogFactory.getLog(SmsTemplateUsageController.class);
	
	@Autowired
	private SmsTemplateService smsTemplateService;
	
	/**
	 * Gg
	 * 设置页 引用短语库查询
	 * @param rquest
	 * @param params
	 * @return
	 * Gg
	 */
	@RequestMapping(value="/findTemplate",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String findTemplate(HttpServletRequest request,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId="哈数据库等哈";
		SmsTemplateVo smsTem =null;
		try {
			smsTem = parseParamToObj(params);
		} catch (Exception e) {
			logger.error("***************解析参数异常"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(smsTem==null) rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		//系统短语库查询
		if(smsTem.getQueryType().equals("系统短语库")){
			List<SmsTemplate> temList= smsTemplateService.findSmsTem(smsTem);
			if(temList!=null && !temList.isEmpty()){
				smsTem = new SmsTemplateVo();
				smsTem.setSmsTemplate(temList);
			}
		}else if(smsTem.getQueryType().equals("自定义短语库")){
			smsTem.setUserId(userId);
			List<SmsTemplate> temzdyList= smsTemplateService.findSmsTemZdy(smsTem);
			if(temzdyList!=null && !temzdyList.isEmpty()){
				smsTem = new SmsTemplateVo();
				smsTem.setSmsTemplate(temzdyList);
			}
		}else{
			return rsMap(101, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).put("data",smsTem).toJson();
	}
	
	/**
	 * 另存为短语库
	 * @param rquest
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/saveSmsTemContent",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String saveSmsTemContent(HttpServletRequest request,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId="哈数据库等哈";
		SmsTemplateVo smsTem =null;
		try {
			smsTem = parseParamToObj(params);
		} catch (Exception e) {
			logger.error("***************解析参数异常"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(smsTem.getType()==null || smsTem.getName()==null || smsTem.getContent()==null) 
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		smsTem.setUserId(userId);
		//判断模板名称不能重复保存
		if(smsTem.getName()!=null && !"".equals(smsTem.getName())){
			boolean result = this.findTemName(smsTem);
			if(!result){
				logger.error("************用户"+userId+"另存为短语库失败,模板名称重复!***********");
				return  rsMap(101, "操作失败").put("status", "模板名称不能重复，请重新操作！").toJson();
			}
		}
		try {
			//保存短语库
			smsTemplateService.saveSmsTemContent(smsTem);
		} catch (Exception e) {
			logger.info("**************************用户"+userId+"保存短信模板失败*********"+e.getMessage());
			return  rsMap(101, "操作失败!").put("status", false).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).toJson();
	}
	
	/**
	 * Gg
	 * 删除自定义短语库
	 * @param request
	 * @param params
	 * @return
	 * Gg
	 */
	@RequestMapping(value="/deleteTemZdy",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String deleteTemZdy(HttpServletRequest request,@RequestBody String params){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId="哈数据库等哈";
		SmsTemplateVo smsTem =null;
		try {
			smsTem = parseParamToObj(params);
		} catch (Exception e) {
			logger.error("***************解析参数异常"+e.getMessage());
			return  rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		}
		if(smsTem==null) rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		if(smsTem.getId()==null) rsMap(102, "操作失败,请重新操作或联系系统管理员!").put("status", false).toJson();
		smsTem.setUserId(userId);
		try {
			//删除自定义短语库
			smsTemplateService.deleteTemZdy(smsTem);
		} catch (Exception e) {
			logger.info("**************************用户"+userId+"删除自定义短语失败***"+e.getMessage());
			return  rsMap(101, "操作失败!").put("status", false).toJson();
		}
		return rsMap(100, "操作成功").put("status", true).toJson();
	}
//==============================================================================================================
	/**
	 * 转化实体Vo
	 * @param params
	 * @return
	 */
	private SmsTemplateVo parseParamToObj(String params){
		SmsTemplateVo smsTem  = null;
		if(params!=null){
			JSONObject parseObject = JSON.parseObject(params);
			String object = parseObject.getString("params");
			smsTem = JsonUtil.fromJson(object, SmsTemplateVo.class);
		}
		return smsTem;
	}
	
	/**
	 * 另存为短语库，查询模板名称是否重复
	 * @param smsTem
	 * @return
	 */
	private boolean findTemName(SmsTemplateVo smsTem){
		boolean result = true;
		int count = smsTemplateService.findSmsTemName(smsTem);
		if(count!=0){
			result = false;
		}
		return result;
	}
}
