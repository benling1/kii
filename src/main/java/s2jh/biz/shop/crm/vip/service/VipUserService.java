package s2jh.biz.shop.crm.vip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.shop.crm.vip.dao.VipUserDao;
import s2jh.biz.shop.crm.vip.entity.VipUser;

@Service
public class VipUserService extends BaseService<VipUser, Long> {
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private VipUserDao vipUserDao;

	@Override
	protected BaseDao<VipUser, Long> getEntityDao() {
		return vipUserDao;
	}

	
	/**
	 * 查询该用户是否是vip
	 */
	public boolean findVipUserIfExist(String vipUserNick){
		try {
			Integer count = myBatisDao.findBy(VipUser.class.getName(), "findVipUserIfExist", vipUserNick);
			if(count>0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
