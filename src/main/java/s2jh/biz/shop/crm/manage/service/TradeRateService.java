package s2jh.biz.shop.crm.manage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.dao.mybatis.MyBatisDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.entity.TradeRateDTO;
import s2jh.biz.shop.crm.order.entity.TradeRates;

import com.taobao.api.domain.TradeRate;

@Service
public class TradeRateService {
	
	private static Logger logger = LoggerFactory.getLogger(TradeRateService.class);

	@Autowired
	private TradeInfoService tradeInfoService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	
	public void saveTradeRate(List<TradeRate> tradeRateList ,String userNick) {
//		logger.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~开始执行保存mongo,list：" + tradeRateList.size() + "卖家是:" + userNick);
//		if(tradeRateList != null && tradeRateList.size() > 0){
//			for (int i = 0; i < tradeRateList.size(); i++) {
//				TradeRate tradeRate = tradeRateList.get(i);
//				if(tradeRate != null && tradeRate.getTid() != null){
//					logger.debug("~~~~~~~~~~~~~~~~~~查询出TradeRate的tid是:" +  tradeRate.getTid() + "卖家是:" + userNick);
//					TradeRateDTO tradeRateDTO = new TradeRateDTO();
//					//拉取的评价信息获得Tid
//					Long tid = tradeRate.getTid();
//					tradeRateDTO.setTid(tid + "");
//					tradeRateDTO.setOid(tradeRate.getOid() + "");
//					tradeRateDTO.setRole(tradeRate.getRole());
//					tradeRateDTO.setNick(tradeRate.getNick());
//					tradeRateDTO.setResult(tradeRate.getResult());
//					tradeRateDTO.setCreated(tradeRate.getCreated());
//					tradeRateDTO.setRatedNick(tradeRate.getRatedNick());
//					tradeRateDTO.setItemTitle(tradeRate.getItemTitle());
//					if(tradeRate.getItemPrice() !=null && !"".equals(tradeRate.getItemPrice())){
//						tradeRateDTO.setItemPrice(Double.parseDouble(tradeRate.getItemPrice()));
//					}
//					tradeRateDTO.setReply(tradeRate.getReply());
//					tradeRateDTO.setNumIid(tradeRate.getNumIid());
//					tradeRateDTO.setValidScore(tradeRate.getValidScore());
//					tradeRateDTO.setContent(tradeRate.getContent());
//					Query query = new Query();
//					query.addCriteria(Criteria.where("tid").is(tid));
//					TradeDTO tradeDTO = tradeInfoService.findOne(query, userNick);
//					logger.debug("~~~~~~~~~~~~~~~~~~~~查询出对应的TradeDTO的size：" + tradeDTO.getTradeRates().size() + "卖家是:" + userNick);
//					tradeDTO.getTradeRates().add(tradeRateDTO);
//					logger.debug("~~~~~~~~~~~~~~~~~~~~~保存成功:" + tradeDTO.getTradeRates().size() + "卖家是:" + userNick);
//				}else {
//					logger.debug("~~~~~~~~~~~~~~~~~~tid为空" +  tradeRate + "卖家是:" + userNick);
//				}
//			}
//		}else {
//			logger.debug(userNick + "本次时间段未取出新评价");
//		}
	}
	
	
	public List<TradeRates> tradeRatesList(String userId,String nick){
		Map<Object, String> map = new HashMap<Object, String>();
		map.put("userId", userId);
		map.put("nick", nick);
		List<TradeRates> tradeRatesList = myBatisDao.findList(TradeRates.class.getName(), "findMemberInfoTradeRate", map);
		return tradeRatesList;
	}
	
	
}
