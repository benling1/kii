package s2jh.biz.shop.crm.message.dao;

import org.springframework.stereotype.Repository;

import lab.s2jh.core.dao.jpa.BaseDao;
import s2jh.biz.shop.crm.message.entity.SmsSendRecord;
@Repository
public interface SmsSendAppointDao extends BaseDao<SmsSendRecord, Long>{

}
