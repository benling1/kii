package s2jh.biz.shop.crm.tradecenter.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.OrderAdvancedSettingService;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.order.service.SmsSettingService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.ReturnMessage;
import s2jh.biz.shop.crm.taobao.util.SendMessageUtil;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;
import s2jh.biz.shop.crm.tradecenter.service.MainProceessService;
import s2jh.biz.shop.crm.tradecenter.service.TradeSetupService;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.JsonUtil;
import s2jh.biz.shop.utils.phoneRegExp.PhoneRegUtils;

/**	
* @Title: SynTradeSetupController
* @Description: (同步订单中心设置并转换为新的设置)
* @author:jackstraw_yu
*/
@SuppressWarnings("unused")
@Controller
@RequestMapping(value="/tradeSetup")
public class SynTradeSetupController {

	

	/***
	 * 同步:
     * tip1:延时发货完全不兼容,同步到新版时默认关闭
     * tip2:回款关怀没有高级设置一说
     * tip3:老版订单中心:自动评价,中差评监控,中差评安抚<====>新版:自动评价,中差评监控,中差评安抚,(好评提醒:无)
     * tip4:下单关怀,所有催付不要卖家标记(其他设置保留卖家标记)
     * tip5:老版卖家标记有Vendormark&flagColor两个字段维护,"卖家标记 0-所有卖家标记都不屏蔽 1-屏蔽卖家标记" 但是现在数据库该字段有空(null)
     * **/
	
	
	/***
	 * 放入redis:
     * tip1:同步所有订单中心的设置
     * tip2:删除status/user_id/type is null的设置
     * tip3:status=true;user_id/type is not null 放入redis中时 判断用户是否存在,是否过期,是否登录过(sessionKey是否为空)
     * **/
	
	
	//控制同步
	private Boolean control_syn = true;
	//控制发短信
	private Boolean control_send = true;
	
	
	
	@Autowired
	private MainProceessService mainProceessService; 
	
	//同步,转换订单中心设置,删除脏数据
	@RequestMapping(value="/startSynTradeSetup")
	public @ResponseBody
	String startSynTradeSetup(Model model,HttpServletRequest request){
		long start = System.currentTimeMillis();
		//已经关闭控制
		if(!control_syn)
			return "control_syn:"+control_syn;
		boolean base = mainProceessService.synBaseSetupData();//1同步基础设置
		if(!base && !control_syn)
			return "synBaseSetupData:"+base;
		boolean advance  = mainProceessService.synAdvancedSetupData();//2同步高级设置
		if(!advance && !control_syn)
			return "synAdvancedSetupData:"+advance;
		boolean sms =  mainProceessService.synSmsSetupData();//3同步短信设置
		if(!sms && !control_syn)
			return "synSmsSetupData:"+sms;
		boolean rate =  mainProceessService.synOrderRateSetupData();//4同步评价设置
		if(!rate && !control_syn)
			return "synRateData:"+rate;
		boolean delete = mainProceessService.deleteErrorData();//5删除同步中的垃圾数据
		if(!delete && !control_syn)
			return "synRateData:"+delete;
	/*	boolean put = mainProceessService.putTradeSetupToReids();//6查询转换后的订单中心的设置并将开启的放入redis中
		if(!put && !control)
			return "putTradeSetupToReids:"+put;*/
		return "syn data spent:"+(System.currentTimeMillis()-start);
	}
	
	//将转换后的订单中心的设置放入redis
	@RequestMapping(value="/synTradeSetupToReids")
	public @ResponseBody
	String synTradeSetupToReids(Model model,HttpServletRequest request){
		long start = System.currentTimeMillis();
		if(control_syn)
			mainProceessService.synTradeSetupToReids();//6查询转换后的订单中心的设置并将开启的放入redis中
		return "put data to redis spent:"+(System.currentTimeMillis()-start);
	}
	
	
	//将用户的sessionKey 放入redis
	@RequestMapping(value="/synSessionKeyToReids")
	public @ResponseBody
	String synSessionKeyToReids(Model model,HttpServletRequest request){
		long start = System.currentTimeMillis();
		if(control_syn)
			mainProceessService.synSessionKeyToReids();//6查询转换后的订单中心的设置并将开启的放入redis中
		return "put sessionKey to redis spent:"+(System.currentTimeMillis()-start);
	}
	
	
	
	
	
	/**
	 * 关闭同步设置功能
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/endSynTradeSetup")
	public @ResponseBody
	String endSynTradeSetup(Model model,HttpServletRequest request){
		//已经关闭控制
		control_syn =  false;
		return "control_syn:"+control_syn;
	}
	
	/**
	 * 给用户发短信
	 * @param model
	 * @param request
	 * @return
	 */
//	@RequestMapping(value="/startSendSms")
//	public @ResponseBody
//	String startSendSms(Model model,HttpServletRequest request){
//		//已经关闭控制
//		if(!control_send)
//			return "control_send:"+control_send;
//		Map<String, String> map = mainProceessService.startSendSms();
//		return "send sms status :"+map;
//	}
	
	
	/**
	 * 关闭发短信功能
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/endSendSms")
	public @ResponseBody
	String endSendSms(Model model,HttpServletRequest request){
		//已经关闭控制
		control_send =  false;
		return "control_send:"+control_send;
	}
	
}
