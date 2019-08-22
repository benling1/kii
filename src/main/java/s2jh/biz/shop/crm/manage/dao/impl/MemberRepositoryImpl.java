package s2jh.biz.shop.crm.manage.dao.impl;

import java.util.List;

import lab.s2jh.core.dao.mongo.BaseMongoDAOImpl;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import s2jh.biz.shop.crm.manage.dao.MemberRepository;
import s2jh.biz.shop.crm.manage.entity.MemberDTO;

@Repository
public class MemberRepositoryImpl extends BaseMongoDAOImpl<MemberDTO> implements MemberRepository{

	
	@Override
	public void batchInsertDecryptMember(List<MemberDTO> saveList,
			String userNickName) {
		String collectionName = getCollectionName(userNickName);
    	if("".equals(collectionName)){
    		return;
    	}else{ 
    		this.mongoTemplate.insert(saveList, collectionName);}
	}


	@Override
	public void updateMemberInfoBlackStatus(Query query, Update update,
			String userNickName) {
		String collectionName = getCollectionName(userNickName);
    	if("".equals(collectionName)){
    		return;
    	}else{ this.mongoTemplate.updateMulti(query, update, MemberDTO.class, collectionName);}
	}
	/** 
	 * Gg
	 * 更新全部符合黑名单的会员
     * Gg
     */ 
	@Override
	public void updateAll(Query query, Update update, String userNickName) {
    	String collectionName = getCollectionName(userNickName);
    	if("".equals(collectionName)){
    		return;
    	}else{
    		mongoTemplate.updateMulti(query, update, MemberDTO.class,collectionName);
    	}
    }
}
