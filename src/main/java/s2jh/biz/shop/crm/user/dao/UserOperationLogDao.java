package s2jh.biz.shop.crm.user.dao;

import org.springframework.stereotype.Repository;

import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import lab.s2jh.core.dao.jpa.BaseDao;

@Repository
public interface UserOperationLogDao extends BaseDao<UserOperationLog, Long> {

}
