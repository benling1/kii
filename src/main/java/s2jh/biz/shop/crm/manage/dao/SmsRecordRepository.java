package s2jh.biz.shop.crm.manage.dao;

import java.util.List;

import lab.s2jh.core.dao.mongo.BaseMongoDAO;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
public interface SmsRecordRepository extends  BaseMongoDAO<SmsRecordDTO>{

	void insertBatch(List<SmsRecordDTO> smsRecordDTOList, String userNick);

	 /** 
     * @Description:通过条件查询更新全部符合条件的数据数据 
     * @author: jackstraw_yu
     */  
    void updateAll(Query query, Update update,String userNickName);  
    
    /** 
     * @Description:通过条件查询删除全部符合条件的数据数据 
     * @author: jackstraw_yu
     */  
    void removeAll(Query query,String userNickName);  
    
    <T> List<T> findTidList(String userId,Aggregation aggregation,Class<T> clazz);
    
    <T> T sum(String userId,Aggregation aggregation,Class<T> clazz);
   /**
 * @param like 
   * 创建人：邱洋
   * @Title: getSmsSendDeductionCount 
   * @Description: TODO(获取发送记录的成功总条数) 
   * @param @param srd
   * @param @param userId
   * @param @param btime
   * @param @param etime
   * @param @return    设定文件 
   * @return SmsRecordDTO    返回类型 
   * @throws
    */
    public SmsRecordDTO getSmsSendDeductionCount(SmsRecordDTO srd,String userId,Long btime,Long etime, boolean like);
    
    /**
     * 指定返回字段的查询
     * BasicDBObject:query  查询条件
     * BasicDBObject:fields 指定返回字段
     * ztk2017年9月26日下午12:05:57
     */
    public DBCursor findFields(BasicDBObject query,BasicDBObject fields,String userName);
    
    /**
     * 聚合统计列表
     * @Title: listAggregates 
     * @param @param query
     * @param @param userNickName
     * @param @param aggregation
     * @param @param clazz
     * @param @return 设定文件 
     * @return List<T> 返回类型 
     * @throws
     */
    public <T> List<T> listAggregates(Query query,String userNickName,Aggregation aggregation,Class<T> clazz);
}
