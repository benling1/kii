package s2jh.biz.shop.crm.manage.dao.impl;

import java.util.List;
import java.util.Map;

import lab.s2jh.core.dao.mongo.BaseMongoDAOImpl;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import s2jh.biz.shop.crm.manage.dao.TradeRepository;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.order.pojo.EffectNum;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Repository
public class TradeRepositoryImpl extends BaseMongoDAOImpl<TradeDTO> implements TradeRepository{
	

	/**
	 * @Description:查询出符合时间内用户的成交额
	 * @author jackstraw_yu
	 */
	@SuppressWarnings("unchecked")
	public double sumPayment(Query query, String userNickName){
		double total = 0.0;  
        String reduce = "function(doc, aggr){"+  
                		"aggr.total += doc.payment;"+  
                		"}";  
        String a = getCollectionName(userNickName);
        DBObject result = mongoTemplate.getCollection(a).group(
        		new BasicDBObject("sellerNick", 1),   
                query.getQueryObject(),   
                new BasicDBObject("total", total),  
                reduce);  
		Map<String,BasicDBObject> map = result.toMap();  
        if(map.size() > 0){  
            BasicDBObject bdbo = map.get("0");  
            if(bdbo != null && bdbo.get("total") != null)  
                total = bdbo.getDouble("total");  
        }  
        return total;  
	}

	
	 /**
	    * 求数据总和
	    * ZTK2017年6月29日上午11:41:03
	    */
	    public EffectNum sum(Query query,Aggregation aggregation, String userNickName) {
	    	String collectionName = getCollectionName(userNickName);
	    	if("".equals(collectionName)){
	    		return null;
	    	}
	    	AggregationResults<EffectNum> aggregate = this.mongoTemplate.aggregate(aggregation, collectionName, EffectNum.class);
	    	List<EffectNum> mappedResults = aggregate.getMappedResults();
	    	DBObject dbObject = query.getQueryObject();
	    	List<?> distinct = mongoTemplate.getCollection(collectionName).distinct("buyerNick",dbObject);
	    	if(mappedResults != null && mappedResults.size() > 0){
	    		if(distinct != null && distinct.size() > 0){
	    			mappedResults.get(0).setCustomerNum(distinct.size());
	    		}
	    		return mappedResults.get(0);
	    	}else {
				return null;
			}
	    	
	    }    
	  
	    /**
	     * 客户详情聚合
	     * @query 查询条件
	     * @userNickName
	     * @aggregation 聚合内容
	     * @clazz output类型
	     */
		public <T> List<T> findAggregateList(Query query,String userNickName, Aggregation aggregation,Class<T> clazz) {
			String collectionName = getCollectionName(userNickName);
			if("".equals(collectionName)){
				return null;
			}
			AggregationResults<T> aggregate = this.mongoTemplate.aggregate(aggregation, collectionName, clazz);
			List<T> customerDetails = aggregate.getMappedResults();
			return customerDetails;
	    }

		
	/**
	 * @Description:查询出符合时间内用户的成交额
	 * @author jackstraw_yu
	 */
	@SuppressWarnings("unchecked")
	public long sumTradeNum(Query query, String userNickName){
		long total = 0;  
        String reduce = "function(doc, aggr){"+  
                		"aggr.total += doc.num;"+  
                		"}";  
        DBObject result = mongoTemplate.getCollection(getCollectionName(userNickName)).group(
        		new BasicDBObject("sellerNick", 1),   
                query.getQueryObject(),   
                new BasicDBObject("total", total),  
                reduce);  
		Map<String,BasicDBObject> map = result.toMap();  
        if(map.size() > 0){  
            BasicDBObject bdbo = map.get("0");  
            if(bdbo != null && bdbo.get("total") != null)  
                total = bdbo.getLong("total");  
        }  
        return total;  
	}

	@Override
	public void inserBatchDecryptTrade(List<TradeDTO> tradeDTOList,
			String userNickName) {
		String collectionName = getCollectionName(userNickName);
		if("".equals(collectionName)){
			return;
		}else{ this.mongoTemplate.insert(tradeDTOList,collectionName);}
	}
	
}
