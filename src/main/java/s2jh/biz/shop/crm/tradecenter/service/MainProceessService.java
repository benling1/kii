package s2jh.biz.shop.crm.tradecenter.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lab.s2jh.core.cons.RedisConstant;
import lab.s2jh.core.service.CacheService;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.order.entity.OrderRateCareSetup;
import s2jh.biz.shop.crm.order.entity.OrderSetup;
import s2jh.biz.shop.crm.order.service.OrderAdvancedSettingService;
import s2jh.biz.shop.crm.order.service.OrderRateCareSetupService;
import s2jh.biz.shop.crm.order.service.OrderSetupService;
import s2jh.biz.shop.crm.order.service.SmsSettingService;
import s2jh.biz.shop.crm.taobao.info.OrderSettingInfo;
import s2jh.biz.shop.crm.taobao.info.ReturnMessage;
import s2jh.biz.shop.crm.taobao.util.SendMessageUtil;
import s2jh.biz.shop.crm.tradecenter.entity.TradeSetup;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.JsonUtil;
import s2jh.biz.shop.utils.phoneRegExp.PhoneRegUtils;

@Service
@Deprecated
//@Transactional
public class MainProceessService {

	@Autowired
	private OrderSetupService orderSetupService;
	@Autowired
	private OrderAdvancedSettingService orderAdvancedSettingService;
	@Autowired
	private SmsSettingService smsSettingService;
	@Autowired
	private OrderRateCareSetupService orderRateCareSetupService;
	@Autowired
	private TradeSetupService tradeSetupService;
	@Autowired
	private UserInfoService userInfoService;
	
	
	//========================================
	@Autowired
	private SeniorProcessSetupService seniorProcessSetupService;
	
	@Autowired
	private BaseProcessSetupService baseProcessSetupService;
	@Autowired
	private CacheService cacheService;
	
	
	private static List<String> thisAreas = new ArrayList<String>(){
		private static final long serialVersionUID = 1613293972981552262L;
		{
			add("北京");add("天津");add("上海");add("重庆");
			add("河北省");add("山西省");add("辽宁省");add("吉林省");
			add("黑龙江省");add("江苏省");add("浙江省");add("安徽省");add("福建省");
			add("江西省");add("山东省");add("河南省");add("湖北省");add("湖南省");
			add("广东省");add("海南省");add("四川省");add("贵州省");
			add("云南省");add("陕西省");add("甘肃省");add("青海省");
			add("内蒙古自治区");add("广西壮族自治区");add("西藏自治区");
			add("宁夏回族自治区");add("新疆维吾尔自治区");
			add("香港特别行政区");add("澳门特别行政区");
			add("台湾省");
		}
	};
	
	/***
	 * 查询基础设置
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean synBaseSetupData() {
		long begin = System.currentTimeMillis();
		//都是套路
		//一查询总条数
		Long count = orderSetupService.findAllSettingCount();
		Long end = 0l,start = 0l;
		if(count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			end  = 1l;
		}else if(count%ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			 end  = count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}else{
			 end  = (count+ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE)/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("pageSize", ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
		List<OrderSetup> list = null;
		while(start<end){
			map.put("startRows", start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			if(start == (end-1))
				map.put("pageSize", count-start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			list = orderSetupService.findAllLimitSetting(map);
			if(list!=null && !list.isEmpty()){
				baseProcessSetupService.processData(list);
				++start;
			}else{
				break;
			}
		}
		System.out.println("...................基础完犊子"+(System.currentTimeMillis()-begin));
		return true;
	}

	/**
	 * 查询高级设置
	 * @return
	 */
	public boolean synAdvancedSetupData() {
		long begin = System.currentTimeMillis();
		//一查询总条数
		Long count = orderAdvancedSettingService.findAllSettingCount();
		Long end = 0l,start = 0l;
		if(count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			end  = 1l;
		}else if(count%ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			 end  = count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}else{
			 end  = (count+ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE)/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("pageSize", ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
		List<OrderAdvancedSetting> list = null;
		while(start<end){
			map.put("startRows", start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			if(start == (end-1))
				map.put("pageSize", count-start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			list = orderAdvancedSettingService.findAllLimitSetting(map);
			if(list!=null && !list.isEmpty()){
				seniorProcessSetupService.processData(list);
				++start;
			}else{
				break;
			}
		}
		System.out.println("...................高级完犊子"+(System.currentTimeMillis()-begin));
		return true;
	}

	/***
	 * 短信设置
	 * 
	 * @return
	 */
	public boolean synSmsSetupData() {
		long begin = System.currentTimeMillis();
		//一查询总条数
		Long count = smsSettingService.findAllSettingCount();
		Long end = 0l,start = 0l;
		if(count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			end  = 1l;
		}else if(count%ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			 end  = count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}else{
			 end  = (count+ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE)/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("pageSize", ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
		List<SmsSetting> list = null;
		while(start<end){
			map.put("startRows", start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			if(start == (end-1))
				map.put("pageSize", count-start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			list = smsSettingService.findAllLimitSetting(map);
			if(list!=null && !list.isEmpty()){
				this.processData(list);
				++start;
			}else{
				break;
			}
		}
		System.out.println("...................短信完犊子"+(System.currentTimeMillis()-begin));
		return true;
	}

	/**
	 *评价设置
	 * **/
	public boolean synOrderRateSetupData() {
		long begin = System.currentTimeMillis();
		//一查询总条数
		Long count = orderRateCareSetupService.findAllSettingCount();
		Long end = 0l,start = 0l;
		if(count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			end  = 1l;
		}else if(count%ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			 end  = count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}else{
			 end  = (count+ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE)/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("pageSize", ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
		List<OrderRateCareSetup> list = null;
		while(start<end){
			map.put("startRows", start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			if(start == (end-1))
				map.put("pageSize", count-start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			list = orderRateCareSetupService.findAllLimitSetting(map);
			if(list!=null && !list.isEmpty()){
				this.processTradeRateData(list);
				++start;
			}else{
				break;
			}
		}
		System.out.println("...................评价完犊子"+(System.currentTimeMillis()-begin));
		return true;
	}
	
	/**
	 * 删除订单中心设置同步的错误数据
	 * **/
	/**
	 * 写在SQL中
	 * 	status is null 
	 *	user_id is null
	 *	type is null
	 *	sms_content is null
	 *	sms_content = ''
	 * **/
	public boolean deleteErrorData() {
		long begin = System.currentTimeMillis();
		tradeSetupService.deleteErrorData();
		System.out.println("...................删除脏数据完犊子"+(System.currentTimeMillis()-begin));
		return true;
	}

	/**
	 * 查询订单中西设置
	 * 判断是否开启开启
	 * **/
	public boolean synTradeSetupToReids() {
		long begin = System.currentTimeMillis();
		//一查询总条数
		Long count = tradeSetupService.getRightDataCount();
		Long end = 0l,start = 0l;
		if(count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			end  = 1l;
		}else if(count%ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			 end  = count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}else{
			 end  = (count+ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE)/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("pageSize", ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
		List<TradeSetup> list = null;
		while(start<end){
			map.put("startRows", start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			if(start == (end-1))
				map.put("pageSize", count-start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			list = tradeSetupService.getRightLimitData(map);
			if(list!=null && !list.isEmpty()){
				this.putTradeSetupToReids(list);
				++start;
			}else{
				break;
			}
		}
		System.out.println("...................将设置放入redis完犊子"+(System.currentTimeMillis()-begin));
		return true;
	}
		
	/**
	 *将订单中心设置放入redis
	 */
	private void putTradeSetupToReids(List<TradeSetup> list) {
		boolean flag = true;
		for (TradeSetup tradeSetup : list) {
			//通过设置的user_id,未过期,sessionKey不为null的用户是否存在
			flag = userInfoService.findUserInUse(tradeSetup.getUserId());
			if(flag){
				cacheService.removeAll(OrderSettingInfo.TRADE_SETUP+tradeSetup.getUserId()+"_"+tradeSetup.getType());
				cacheService.putNoTime(OrderSettingInfo.TRADE_SETUP+tradeSetup.getUserId()+"_"+tradeSetup.getType(),  
						tradeSetup.getId().toString(),tradeSetup); 
			}
		}
	}
	
	
	
	/***
	 * 给用户发短信
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Map<String,String> startSendSms() {
		Map<String,String> resultMap = new HashMap<String,String>();
		//查询出用户没有过期,电话不为空的用户
		Long count = userInfoService.findAllUserCount();
		Long end = 0l,start = 0l;
		if(count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			end  = 1l;
		}else if(count%ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			 end  = count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}else{
			 end  = (count+ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE)/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("pageSize", ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
		List<UserInfo> list = null;
		String result = null;
		while(start<end){
			map.put("startRows", start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			if(start == (end-1))
				map.put("pageSize", count-start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			list = userInfoService.findAllUserList(map);
			if(list!=null && !list.isEmpty()){
				//开始分批发短信
				result = this.processAndSendSms(list);
				resultMap.put(start+"", result);
				++start;
			}else{
				break;
			}
		}
		return resultMap;
	}
	
	
	
	
	/**
	 * 每30 分钟执行一次
	 * 同步sessionKey
	 * 修改mysqlsessionkey
	 * 放到redis  
	 * 测试TMC
	 */
	public void synSessionKeyToReids() {
		//查询出用户没有过期,电话不为空的用户
		Long count = userInfoService.findAllUserCount();
		Long end = 0l,start = 0l;
		if(count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			end  = 1l;
		}else if(count%ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE==0){
			 end  = count/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}else{
			 end  = (count+ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE)/ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("pageSize", ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
		List<UserInfo> list = null;
		String result = null;
		while(start<end){
			map.put("startRows", start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			if(start == (end-1))
				map.put("pageSize", count-start*ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE);
			list = userInfoService.findAllUserList(map);
			if(list!=null && !list.isEmpty()){
				//开始分批发短信
				this.putSessionKeyToReids(list);
				++start;
			}else{
				break;
			}
		}
	}
	
	
	
	
	/**
	 * 将用户 的sessionkey放入redis中
	 * @param list
	 * @return
	 */
	private void putSessionKeyToReids(List<UserInfo> list) {
		for (UserInfo userInfo : list) {
			if(userInfo.getTaobaoUserNick() !=null && !"".equals(userInfo.getTaobaoUserNick())
				&& userInfo.getAccess_token()!=null && !"".equals(userInfo.getAccess_token())){
				try {
					cacheService.putNoTime(RedisConstant.RedisCacheGroup.USRENICK_TOKEN_CACHE, 
							RedisConstant.RediskeyCacheGroup.USRENICK_TOKEN_CACHE_KEY+userInfo.getTaobaoUserNick(),userInfo.getAccess_token());
				} catch (Exception e) {
					continue;
				}
			}
		}
	}

	/**
	 * 给用户发短信,提醒用户重新设置订单中心设置
	 * @param list
	 * @return
	 */
	private String processAndSendSms(List<UserInfo> list) {
		/*ArrayList<String> mobiles = new ArrayList<String>();
		for (UserInfo userInfo : list) {
			if(PhoneRegUtils.phoneValidate(userInfo.getMobile()))
				mobiles.add(userInfo.getMobile());
		}*/
		String  sendStatus =null;
		/*if(!mobiles.isEmpty()){
			//开始发短信!
			sendStatus = SendMessageUtil.sendMessage(mobiles.toArray(new String[0]),"", null, null);
			ReturnMessage returnMessage = JsonUtil.fromJson(sendStatus, ReturnMessage.class);
			sendStatus = returnMessage.getReturnCode();
		}*/
		return sendStatus;
	}

	//============================================================================
	//
	//短信
	//
	//============================================================================
	/**
	 * 处理订单中心短信设置
	 * **/
	private void processData(List<SmsSetting> list) {
		for (SmsSetting setup : list) {
			if(setup.getUserId()==null || "".equals(setup.getUserId())
				/*|| setup.getStatus()==null || "".equals(setup.getStatus())*/
				|| setup.getSettingType()==null || "".equals(setup.getSettingType())
				//手动订单提醒
				|| OrderSettingInfo.ORDER_MANUAL_REMIND.equals(setup.getSettingType())
				//疑难件提醒
				|| OrderSettingInfo.ABNORMAL_GOODS_REMIND.equals(setup.getSettingType())
				//TODO
				//TODO
				//TODO
				//TODO
				//中差评安抚 短信设置中也有该类型
				|| OrderSettingInfo.APPRAISE_PACIFY_ORDER.equals(setup.getSettingType())
			)
				continue;
			this.handleSmsData(setup,new TradeSetup());
		}
	}
	
	/*
	订单编号  :订单号  
	卖家昵称  :空
	物流公司昵称 :物流公司名称 
	下单关怀【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】
	常规催付【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】【付款链接】
	二次催付【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】【付款链接】
	聚划算催付【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】【付款链接】
	付款关怀【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】
	发货提醒【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】【物流公司名称】【运单号】【物流链接】
	同城提醒【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】【物流公司名称】【运单号】【物流链接】【到达城市】
	派件提醒【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】【物流公司名称】【运单号】【物流链接】【】
	签收提醒【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】【物流公司名称】【运单号】【确认收货链接】
	回款提醒【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】【物流公司名称】【运单号】【确认收货链接】
	宝贝关怀【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】【物流公司名称】【运单号】【确认收货链接】
	退款关怀【买家昵称】【买家姓名】【下单时间】【订单编号】【退款链接】
	中差评安抚【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】
	提醒好评【买家昵称】【买家姓名】【下单时间】【订单编号】【订单金额】【评价链接】*/
	//【 物流公司昵称】 【收货链接】【 物流公司名称】【 订单号】【 下单时间】【 卖家昵称】【 订单金额】【 买家昵称】【买家昵称 】【物流公司昵称 】
	private void handleSmsData(SmsSetting source,TradeSetup target){
		String  message = source.getMessageContent();
    	target.setUserId(source.getUserId());
    	target.setType(source.getSettingType());
    	target.setLastModifiedBy(source.getUserId());
    	target.setLastModifiedDate(new Date());
    	//此处有坑,宝贝关怀的商品选择在短信设置里
    	//TODO
    	if(OrderSettingInfo.COWRY_CARE.equals(source.getSettingType()))
	    	if(source.getItemId()!=null && !"".equals(source.getItemId())){
	    		target.setProductType(true);
				target.setProducts(source.getItemId());
	    	}
    	if(source.getMessageContent()!=null && !"".equals(source.getMessageContent())){
    		/*if(message.startsWith("【") && message.contains("】"))
    			message = message.substring(message.indexOf("】")+1);*/
    		//不能直接使用正则去除空格,避免短链接无法使用
    		//message = message.replaceAll("\\s", "")
    		//.replaceAll("【.*】", "");
    		//中文中括号内加有空格
    		if(!OrderSettingInfo.ARRIVAL_LOCAL_REMIND.equals(source.getSettingType()))
    			message = message.replace("【到达城市】", "");
    		message = message.replaceAll("【\\s* ", "【").replaceAll("\\s*】", "】")
		    		.replace("【卖家昵称】", "").replace("【订单链接】", "").replace("【收货人】", "")
					.replace("【订单编号】", "{订单编号}").replace("【订单号】", "{订单编号}")
					.replace("【物流公司昵称】", "{物流公司名称}").replace("【物流公司名称】", "{物流公司名称}")
		    		.replace("【买家昵称】", "{买家昵称}").replace("【买家姓名】", "{买家姓名}")
		    		.replace("【下单时间】", "{下单时间}").replace("【订单金额】", "{订单金额}")
		    		.replace("【付款链接】", "{付款链接}")
		    		.replace("【运单号】", "{运单号}")
		    		.replace("【物流链接】", "{物流链接}").replace("【到达城市】", "{到达城市}")
		    		.replace("【确认收货链接】", "{确认收货链接}").replace("【收货链接】", "{确认收货链接}")
		    		.replace("【退款链接】", "{退款链接}")
		    		.replace("【评价链接】", "{评价链接}");
    		message = subMessage(message);
    		//短链接两端有空格  误删
    		target.setSmsContent("".equals(message.trim())?null:message);
    	}
    	if(target != null){
    		//根据userId&type判断存在与否
			if(tradeSetupService.isExist(target.getUserId(),target.getType())){
				//存在更新
				tradeSetupService.updateTradeSetupSync(target);
			}else{
				//不存在保存
				tradeSetupService.saveTradeSetupSync(target);
			}
		}	   
	}
	//============================================================================
	//
	//短信
	//
	//============================================================================
	
	//"评价关怀与中差评管理表示字段    0--(评价关怀)自动评价   1--(中差评管理)中差评监控  2--(中差评管理)中差评安抚"
	/**
	 * 处理评价设置数据
	 * */
	private void processTradeRateData(List<OrderRateCareSetup> list) {
		for (OrderRateCareSetup setup : list) {
			if(setup.getUserId()==null || "".equals(setup.getUserId())
				|| setup.getStatus()==null || "".equals(setup.getStatus())
				|| setup.getAppraiseType()==null || "".equals(setup.getAppraiseType())
			)
				continue;
			//自动评价
			if("0".equals(setup.getAppraiseType())){
				this.handleAutoRateData(setup,new TradeSetup());
			}//中差评监控
			else if("1".equals(setup.getAppraiseType())){//中差评监控
				this.handleMonitorRateData(setup,new TradeSetup());
			}//中差评安抚
			else if("2".equals(setup.getAppraiseType())){//中差评安抚
				this.handlePlacateRateData(setup,new TradeSetup());
			}
		}
	}

	/**
	 * 自动评价
	 * */
	private void handleAutoRateData(OrderRateCareSetup source, TradeSetup target) {
		target.setUserId(source.getUserId());
		//自动评价
		target.setType(OrderSettingInfo.AUTO_RATE);
		target.setCreatedBy(source.getUserId());
		target.setCreatedDate(new Date());
		target.setLastModifiedBy(source.getUserId());
		target.setLastModifiedDate(new Date());
		//转换数据
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(true);
		}else{
			return;
		}
		if(source.getRateChoose() !=null && !"".equals(source.getRateChoose())){
			//评价条件  1-立即评 2-延迟评  3-抢评
			if("1".equals(source.getRateChoose())){
				target.setDelayEvaluate(false);
			}else if("2".equals(source.getRateChoose())
					&& source.getDeferRateDay()!=null && !"".equals(source.getDeferRateDay())){
				target.setDelayEvaluate(true);
				target.setDelayDate(Integer.valueOf(source.getDeferRateDay()));
			}else{
				target.setDelayEvaluate(false);
			}
		}
		if(source.getResult()!=null && !"".equals(source.getResult())){
			//评价类型good(好评),neutral(中评),bad(差评)
			if("good".equals(source.getResult()) && source.getContent() !=null && !"".equals(source.getContent())){
				target.setEvaluateType("good");
				target.setSmsContent(source.getContent());
			}else if("neutral".equals(source.getResult()) && source.getNeutralContent() !=null && !"".equals(source.getNeutralContent())){
				target.setEvaluateType("neutral");
				target.setSmsContent(source.getNeutralContent());
			}else if("bad".equals(source.getResult()) && source.getBadContent() !=null && !"".equals(source.getBadContent())){
				target.setEvaluateType("bad");
				target.setSmsContent(source.getBadContent());
			}else{
				return;
			}
		}else{
			return;
		}
		if(source.getBlacklistRateType()!=null && !"".equals(source.getBlacklistRateType())){
			//如果客户是黑名单进行的评价类型0-不自动评价  1-好评 2-中评 3-差评
			if("0".equals(source.getBlacklistRateType())){
				target.setEvaluateBlack(true);
			}else if("1".equals(source.getBlacklistRateType())){
				//取老字段的好评内容
				if("".equals(source.getContent()) || source.getContent()==null)
					return;
				target.setEvaluateBlack(false);
				//黑名单相关字段
				target.setEvaluateBlackType("good");
				target.setEvaluateBlackContent(source.getContent());
			}else if("2".equals(source.getBlacklistRateType())){
				if("".equals(source.getNeutralContent()) || source.getNeutralContent()==null )
					return;
				target.setEvaluateBlack(false);
				//黑名单相关字段
				target.setEvaluateBlackType("neutral");
				target.setEvaluateBlackContent(source.getNeutralContent());
			}else if("3".equals(source.getBlacklistRateType())){
				if("".equals(source.getBadContent()) || source.getBadContent()==null)
					return;
				target.setEvaluateBlack(false);
				target.setEvaluateBlackType("bad");
				target.setEvaluateBlackContent(source.getBadContent());
			}else{
				return;
			}
		}else{
			return;
		}
		if(target != null){
			//根据userId&type判断存在与否
			if(tradeSetupService.isExist(target.getUserId(),target.getType())){
				//存在更新
				tradeSetupService.updateTradeSetupSync(target);
			}else{
				//不存在保存
				tradeSetupService.saveTradeSetupSync(target);
			}
		}	   
	}
	/**
	 * 中差评监控
	 * */
	private void handleMonitorRateData(OrderRateCareSetup source, TradeSetup target) {
		target.setUserId(source.getUserId());
		target.setType(OrderSettingInfo.APPRAISE_MONITORING_ORDER);
		target.setCreatedBy(source.getUserId());
		target.setCreatedDate(new Date());
		target.setLastModifiedBy(source.getUserId());
		target.setLastModifiedDate(new Date());
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(true);
		}else{
			return;
		}
		target.setMinInformTime("00:00:00");
		target.setMaxInformTime("23:59:59");
		/**
		 * true:超出时间次日发送
		 * false: 超出时间不发送
		 * */
		target.setTimeOutInform(true);//??坑
		if(source.getStatusFiltrate()!=null && !"".equals(source.getStatusFiltrate())){
			//0-当有中评时通知我  1-当有差评时通知我
			if(source.getStatusFiltrate().contains("0"))
				target.setNeutralEvaluateInform(true);
			if(source.getStatusFiltrate().contains("1"))
				target.setBadEvaluateInform(true);
		}
		if(source.getAcceptSmsPhone()!=null && !"".equals(source.getAcceptSmsPhone())){
			target.setInformMobile(source.getAcceptSmsPhone());
		}
		if(source.getContent()!=null && !"".equals(source.getContent())){
			//又是一个坑??
			//target.setSmsContent("【客云CRM】亲爱的商家，您的店铺出现了中差评，中差评将影响宝贝排名，建议您请及时查看处理。退订回复N");
			target.setSmsContent("您好，您的店铺收到了买家的中差评，请您查看处理。");
		}
		//转换数据
		if(target != null){
			//根据userId&type判断存在与否
			if(tradeSetupService.isExist(target.getUserId(),target.getType())){
				//存在更新
				tradeSetupService.updateTradeSetupSync(target);
			}else{
				//不存在保存
				tradeSetupService.saveTradeSetupSync(target);
			}
		}	   
	}
	/**
	 * 中差评安抚
	 * */
	private void handlePlacateRateData(OrderRateCareSetup source, TradeSetup target) {
		//TODO阿門但願顔色順序不要錯
		String flagColor  = "0,1,2,3,4,5";
		String orderSource = "TAOBAO,WAP,JHS";
		target.setUserId(source.getUserId());
		target.setType(OrderSettingInfo.APPRAISE_PACIFY_ORDER);
		target.setCreatedBy(source.getUserId());
		target.setCreatedDate(new Date());
		target.setLastModifiedBy(source.getUserId());
		target.setLastModifiedDate(new Date());
		if("0".equals(source.getStatus())){
			target.setStatus(true);
		}else if("1".equals(source.getStatus())){
			target.setStatus(true);
		}else{
			return;
		}
		
		//持续执行
		target.setExecuteType(true);
		//下单提醒是默认
		target.setTimeOutInform(true);
		//通知时间
		if(source.getSendTimeTwo()==null || "".equals(source.getSendTimeTwo().trim())){
			target.setMaxInformTime("23:59:59");
		}else{
			if(source.getSendTimeTwo().trim().startsWith("24")){
				target.setMaxInformTime("23:59:59");
			}else if(source.getSendTimeTwo().trim().length()==5){
				target.setMaxInformTime(source.getSendTimeTwo().trim()+":00");
			}else  if(source.getSendTimeTwo().trim().length()==4){
				target.setMaxInformTime("0"+source.getSendTimeTwo().trim()+":00");
			}
		}
		if(source.getSendTimeOne()==null || "".equals(source.getSendTimeOne().trim())){
			target.setMinInformTime("00:00:00");
		}else{
			if(source.getSendTimeOne().trim().length()==5){
				target.setMinInformTime(source.getSendTimeOne().trim()+":00");
			}else  if(source.getSendTimeOne().trim().length()==4){
				target.setMinInformTime("0"+source.getSendTimeOne().trim()+":00");
			}
		}
		if(source.getMoneyOne()!=null && !"".equals(source.getMoneyOne())){
			target.setMinPayment(new BigDecimal(Double.valueOf(source.getMoneyOne())));
		}
		if(source.getMoneyTwo()!=null && !"".equals(source.getMoneyTwo())){
			target.setMaxPayment(new BigDecimal(Double.valueOf(source.getMoneyTwo())));
		}
		if(source.getFlagcolor()!=null && !"".equals(source.getFlagcolor())){
    		if(source.getFlagcolor().contains("1"))
    			flagColor  = flagColor.replace("1,", "");
    		if(source.getFlagcolor().contains("2"))
    			flagColor  = flagColor.replace("2,", "");
    		if(source.getFlagcolor().contains("3"))
    			flagColor  = flagColor.replace("3,", "");
    		if(source.getFlagcolor().contains("4"))
    			flagColor  = flagColor.replace("4,", "");
    		if(source.getFlagcolor().contains("5"))
    			flagColor  = flagColor.replace(",5", "");
		}
		target.setSellerFlag(flagColor);
		if(source.getOrderSource()!=null && !"".equals(source.getOrderSource())){
			if(source.getOrderSource().contains("0"))
				orderSource = orderSource.replace("TAOBAO,", "");
			if(source.getOrderSource().contains("1"))
				orderSource = orderSource.replace("WAP,", "");
			if(source.getOrderSource().contains("2"))
				orderSource = orderSource.replace(",JHS", "").replace("JHS", "");
		}
		target.setTradeFrom("".equals(orderSource)?null:orderSource);
		
		//TODO 大坑!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if(source.getLocality()!=null &&  !"".equals(source.getLocality().trim())){
			 String[] places = source.getLocality().trim().replaceAll("\\s", "")
			.replace("北京市", "北京").replace("天津市", "天津")
			.replace("上海市", "上海").replace("重庆市", "重庆").split(",|，");
			 if(places!=null&&places.length>0){
				 String areas  = StringUtils.join(thisAreas,",");
				for (String s : places) {
					areas.replace(s+",", "").replace(s, "");
				}
				target.setProvince("".equals(areas)||areas==null?null:areas);
			 }
		}
		if(source.getContent()!=null && !"".equals(source.getContent())){
			String message = source.getContent();
			/*if(message.startsWith("【") && message.contains("】"))
					message = message.substring(message.indexOf("】")+1);*/
			/*message = message.replaceAll("\\s", "")
			.replace("【卖家昵称】", "")
			.replace("【收货人】", "")
			.replace("【订单编号】", "{订单编号}").replace("【订单号】", "{订单编号}")
			.replace("【买家昵称】", "{买家昵称}").replace("【买家姓名】", "{买家姓名}")
			.replace("【下单时间】", "{下单时间}").replace("【订单金额】", "{订单金额}")
			.replaceAll("【.*】", "");*/
			message = message.replaceAll("【\\s* ", "【").replaceAll("\\s*】", "】")
		    		.replace("【卖家昵称】", "").replace("【订单链接】", "").replace("【收货人】", "").replace("【到达城市】", "")
					.replace("【订单编号】", "{订单编号}").replace("【订单号】", "{订单编号}")
					.replace("【物流公司昵称】", "{物流公司名称}").replace("【物流公司名称】", "{物流公司名称}")
		    		.replace("【买家昵称】", "{买家昵称}").replace("【买家姓名】", "{买家姓名}")
		    		.replace("【下单时间】", "{下单时间}").replace("【订单金额】", "{订单金额}")
		    		.replace("【付款链接】", "{付款链接}")
		    		.replace("【运单号】", "{运单号}")
		    		.replace("【物流链接】", "{物流链接}")
		    		.replace("【确认收货链接】", "{确认收货链接}").replace("【收货链接】", "{确认收货链接}")
		    		.replace("【退款链接】", "{退款链接}")
		    		.replace("【评价链接】", "{评价链接}");
    		message = subMessage(message);
			target.setSmsContent("".equals(message.trim())?null:message);
		}
		//转换数据
		if(target != null){
			//根据userId&type判断存在与否
			if(tradeSetupService.isExist(target.getUserId(),target.getType())){
				//存在更新
				tradeSetupService.updateTradeSetupSync(target);
			}else{
				//不存在保存
				tradeSetupService.saveTradeSetupSync(target);
			}
		}	   
	}

	//慎用!
	//TODO
	private  String subMessage(String message){
		if(message.contains("【") && message.contains("】") && message.startsWith("【")){
			/*message = message.substring(message.indexOf("】")+1);
			return subMessage(message);*/
			while(message.contains("【") && message.contains("】") && message.startsWith("【")){
				message = message.substring(message.indexOf("】")+1);
			}
		}/*else{
			return message.replace("【", "(").replace("】", ")");
		}*/
		return message.replace("【", "(").replace("】", ")");
	}
	
	
	public static void main(String[] args) {
		String message = "【萨达的】【 订单号 】【   下单时间 】【 买家昵称 】【买家姓名】测试 c.tb.cn/c.0LuwR 、商品ID c.tb.cn/c.cQK62";
		//String message = "【联翔科技】您拍下的宝贝我们一直为您留着呢，付款后给您优先发货哦，欢迎加入联翔科技QQ群278726400【618理想生活狂欢节】";
		/*message = message.replaceAll("【\\s* ", "【").replaceAll("\\s*】", "】")
	    		.replace("【卖家昵称】", "")
	    		.replace("【订单链接】", "")
				.replace("【订单编号】", "{订单编号}").replace("【订单号】", "{订单编号}")
				.replace("【物流公司昵称】", "{物流公司名称}").replace("【物流公司名称】", "{物流公司名称}")
	    		.replace("【买家昵称】", "{买家昵称}").replace("【买家姓名】", "{买家姓名}")
	    		.replace("【下单时间】", "{下单时间}").replace("【订单金额】", "{订单金额}")
	    		.replace("【付款链接】", "{付款链接}")
	    		.replace("【运单号】", "{运单号}")
	    		.replace("【物流链接】", "{物流链接}").replace("【到达城市】", "{到达城市}")
	    		.replace("【确认收货链接】", "{确认收货链接}").replace("【收货链接】", "{确认收货链接}")
	    		.replace("【退款链接】", "{退款链接}")
	    		.replace("【评价链接】", "{评价链接}");*/
		while(message.contains("【") && message.contains("】") && message.startsWith("【")){
			message = message.substring(message.indexOf("】")+1);
		}
		// message = message.replaceAll("【.*】", "");
	    		//.replaceAll("【.*】", "");
		//【萨达的】下单{订单编号}{下单时间}【  买家昵称 】{买家姓名}测试 c.tb.cn/c.0LuwR 、商品ID c.tb.cn/c.cQK62
		System.out.println(message);
	}
	
	
}
