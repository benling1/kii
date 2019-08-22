package s2jh.biz.shop.crm.user.dao;

import org.springframework.stereotype.Repository;
import lab.s2jh.core.dao.jpa.BaseDao;
import s2jh.biz.shop.crm.user.entity.UserLoginInfo;

@Repository
public interface UserLoginInfoDao extends BaseDao<UserLoginInfo,Long> {

}
