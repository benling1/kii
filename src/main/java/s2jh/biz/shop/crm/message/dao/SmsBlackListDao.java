package s2jh.biz.shop.crm.message.dao;


import org.springframework.stereotype.Repository;

import s2jh.biz.shop.crm.message.entity.SmsBlacklist;
import lab.s2jh.core.dao.jpa.BaseDao;

@Repository
public interface SmsBlackListDao extends BaseDao<SmsBlacklist, Long>{	
	
}
