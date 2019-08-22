package s2jh.biz.shop.crm.manage.dao;

import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import lab.s2jh.core.dao.mongo.BaseMongoDAO;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;

import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.order.pojo.EffectNum;
public interface TradeRepository extends  BaseMongoDAO<TradeDTO>{


	double sumPayment(Query query, String str);

	long sumTradeNum(Query query, String userNickName);

	
	void inserBatchDecryptTrade(List<TradeDTO> tradeDTOList, String userNickName);

    /**
     * 求数据总和
     * ZTK2017年6月29日上午11:41:03
     */
    public EffectNum sum(Query query,Aggregation aggregation,String userNickName);

    /**
     * 分页聚合查询 
     * @query:查询条件
     * @aggregation:聚合条件
     * @clazz:output类型
     * ZTK2017年7月10日下午1:42:22
     */
    public <T> List<T> findAggregateList(Query query,String userNickName,Aggregation aggregation,Class<T> clazz);


}
