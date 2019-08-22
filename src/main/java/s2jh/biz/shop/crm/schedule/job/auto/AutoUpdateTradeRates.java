package s2jh.biz.shop.crm.schedule.job.auto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.CacheService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import s2jh.biz.shop.crm.manage.util.EncrptAndDecryptClient;
import s2jh.biz.shop.crm.order.entity.TradeRates;
import s2jh.biz.shop.crm.user.service.UserInfoService;

import com.taobao.api.SecretException;

@Controller
public class AutoUpdateTradeRates {

	private Logger logger = LoggerFactory.getLogger(AutoUpdateTradeRates.class);
	
	@Autowired
	private MyBatisDao mybatisDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private CacheService cacheService;
	/**
	 * 加密评价表数据
	 * ZTK2017年8月2日下午4:06:45
	 */
	@RequestMapping("/update/tradeRates")
	public void findUpdateTradeRate(){
		//最近一此更新的id
		Long lastUpdateId = 0l;
		if(cacheService.getByKey("updateTradeRates", "buyerLastUpdateId")){
			String jsonStr = cacheService.getJsonStr("updateTradeRates", "buyerLastUpdateId");
			if(jsonStr != null && !"".equals(jsonStr)){
				if(Long.parseLong(jsonStr) != 0l){
					lastUpdateId = Long.parseLong(jsonStr);
				}
			}
		}
		logger.info("加密评价表======================lastUpdateId:" + lastUpdateId);
		Date log1 = new Date();
		logger.info("加密评价表======================开始时间:" + log1);
		//初始批次
		int init = 0;
		//一次查询的数量
		int size = 1000;
		int i = 0;
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		int maxId = mybatisDao.findBy(TradeRates.class.getName(), "findByMaxId", null);
		while(lastUpdateId <= maxId){
			logger.info("~~~~~~~~~~~~加密评价：第"+ (++i) +"次循环~~~~~~~~~~~~~~~~~~~~~~~");
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("lastUpdateId", lastUpdateId);
			map.put("init", init);
			map.put("size", size);
			List<TradeRates> tradeRates = mybatisDao.findList(TradeRates.class.getName(), "findAllId", map);
			try {
				if(tradeRates != null && !tradeRates.isEmpty()){
					init += size;
					for (TradeRates tradeRate : tradeRates) {
						if(tradeRate != null && tradeRate.getRatedNick() != null && !"".equals(tradeRate.getRatedNick())){
							String sessionKey = userInfoService.validateFindSessionKey(tradeRate.getRatedNick());
							if(sessionKey == null || "".equals(sessionKey)){
								continue;
							}
							if(tradeRate.getNick() != null && !"".equals(tradeRate.getNick())){
								String nick;
								String ratedNick;
								if(EncrptAndDecryptClient.isEncryptData(tradeRate.getNick(), EncrptAndDecryptClient.SEARCH)){
									nick = tradeRate.getNick();
								}else {
									nick = decryptClient.encryptData(tradeRate.getNick(), EncrptAndDecryptClient.SEARCH, sessionKey);
								}
								if(EncrptAndDecryptClient.isEncryptData(tradeRate.getRatedNick(), EncrptAndDecryptClient.SIMPLE)){
									ratedNick = tradeRate.getRatedNick();
								}else {
									ratedNick = decryptClient.encryptData(tradeRate.getRatedNick(), EncrptAndDecryptClient.SIMPLE, sessionKey);
								}
								Map<String,Object> updateMap = new HashMap<String, Object>();
								updateMap.put("nick", nick);
								updateMap.put("ratedNick", ratedNick);
								updateMap.put("id", tradeRate.getId());
								mybatisDao.execute(TradeRates.class.getName(), "updateRateById", updateMap);
								lastUpdateId = tradeRate.getId();
							}
						}
					}
				}
				cacheService.put("updateTradeRates", "buyerLastUpdateId", lastUpdateId);
				logger.info("~~~~~~~~~~~~加密评价：第"+ i +"次循环用时："+ (new Date().getTime() - log1.getTime()) +"毫秒~~最后更新id为：" + lastUpdateId + "~~~~~~~~~~~~~~~~~~~~~");
			} catch (SecretException e) {
				logger.info("~~~~~~~~~~~~~~~~~~~~~~最后更新id为：" + lastUpdateId + "出现异常");
				e.printStackTrace();
			}
			if(lastUpdateId == maxId){
				return;
			}
		}
		
		
	}
	
	/**
	 * 加密评价表数据
	 * ZTK2017年8月2日下午4:06:45
	 */
	@RequestMapping("/update/seller/tradeRates")
	public void findUpdateTradeRate2(){
		//最近一此更新的id
		Long lastUpdateId = 0l;
		if(cacheService.getByKey("updateTradeRates", "sellerLastUpdateId")){
			String jsonStr = cacheService.getJsonStr("updateTradeRates", "sellerLastUpdateId");
			if(jsonStr != null && !"".equals(jsonStr)){
				lastUpdateId = Long.parseLong(jsonStr);
			}
		}
		logger.info("加密评价表======================lastUpdateId:" + lastUpdateId);
		Date log1 = new Date();
		logger.info("加密评价表======================开始时间:" + log1);
		//初始批次
		int init = 0;
		//一次查询的数量
		int size = 1000;
		int i = 0;
		EncrptAndDecryptClient decryptClient = EncrptAndDecryptClient.getInstance();
		int maxId = mybatisDao.findBy(TradeRates.class.getName(), "findByMaxId", null);
		while(lastUpdateId <= maxId){
			logger.info("~~~~~~~~~~~~加密评价：第"+ (++i) +"次循环~~~~~~~~~~~~~~~~~~~~~~~");
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("lastUpdateId", lastUpdateId);
			map.put("init", init);
			map.put("size", size);
			List<TradeRates> tradeRates = mybatisDao.findList(TradeRates.class.getName(), "findAllSelelrId", map);
			try {
				if(tradeRates != null && !tradeRates.isEmpty()){
					init += size;
					for (TradeRates tradeRate : tradeRates) {
						if(tradeRate != null && tradeRate.getRatedNick() != null && !"".equals(tradeRate.getRatedNick())){
							String sessionKey = userInfoService.validateFindSessionKey(tradeRate.getRatedNick());
							if(sessionKey == null || "".equals(sessionKey)){
								continue;
							}
							if(tradeRate.getNick() != null && !"".equals(tradeRate.getNick())){
								String nick;
								String ratedNick;
								if(EncrptAndDecryptClient.isEncryptData(tradeRate.getNick(), EncrptAndDecryptClient.SIMPLE)){
									nick = tradeRate.getNick();
								}else {
									nick = decryptClient.encryptData(tradeRate.getNick(), EncrptAndDecryptClient.SIMPLE, sessionKey);
								}
								if(EncrptAndDecryptClient.isEncryptData(tradeRate.getRatedNick(), EncrptAndDecryptClient.SEARCH)){
									ratedNick = tradeRate.getRatedNick();
								}else {
									ratedNick = decryptClient.encryptData(tradeRate.getRatedNick(), EncrptAndDecryptClient.SEARCH, sessionKey);
								}
								Map<String,Object> updateMap = new HashMap<String, Object>();
								updateMap.put("nick", nick);
								updateMap.put("ratedNick", ratedNick);
								updateMap.put("id", tradeRate.getId());
								mybatisDao.execute(TradeRates.class.getName(), "updateRateById", updateMap);
								lastUpdateId = tradeRate.getId();
							}
						}
					}
				}
				cacheService.put("updateTradeRates", "sellerLastUpdateId", lastUpdateId);
				logger.info("~~~~~~~~~~~~加密评价：第"+ i +"次循环用时："+ (new Date().getTime() - log1.getTime()) +"毫秒~~最后更新id为：" + lastUpdateId + "~~~~~~~~~~~~~~~~~~~~~");
			} catch (SecretException e) {
				logger.info("~~~~~~~~~~~~~~~~~~~~~~最后更新id为：" + lastUpdateId + "出现异常");
				e.printStackTrace();
			}
			if(lastUpdateId == maxId){
				return;
			}
		}
		
		
	}
}
