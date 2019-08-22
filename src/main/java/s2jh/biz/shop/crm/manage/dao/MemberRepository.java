package s2jh.biz.shop.crm.manage.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import lab.s2jh.core.dao.mongo.BaseMongoDAO;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
public interface MemberRepository extends  BaseMongoDAO<MemberDTO>{
	
	/** 
	 * Gg
     * 清空黑名单 将黑名单会员 改为正常会员
     * Gg
     */  
    void updateMemberInfoBlackStatus(Query query, Update update,String userNickName);  
	/** 
	 * Gg
	 * 更新全部符合黑名单的会员
     * Gg
     */ 
	void updateAll(Query query, Update update, String userNickName);

	void batchInsertDecryptMember(List<MemberDTO> saveList, String userNickName);
    	
}
