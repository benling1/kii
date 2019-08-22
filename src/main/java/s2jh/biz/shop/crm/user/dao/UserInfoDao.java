package s2jh.biz.shop.crm.user.dao;

import lab.s2jh.core.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;

import s2jh.biz.shop.crm.user.entity.UserInfo;

@Repository
public interface UserInfoDao extends BaseDao<UserInfo, Long> {

}
