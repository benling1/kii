/**
 * 
 */
package s2jh.biz.shop.crm.user.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.user.dao.UserLoginInfoDao;
import s2jh.biz.shop.crm.user.entity.UserLoginInfo;
import s2jh.biz.shop.utils.getIpAddress;

@Service
@Transactional
public class UserLoginInfoService extends BaseService<UserLoginInfo, Long> {
	
	@Autowired
	private UserLoginInfoDao userLoginInfoDao;
	
	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	protected BaseDao<UserLoginInfo, Long> getEntityDao() {
		return userLoginInfoDao;
	}
	
	/**
	  * 创建人：邱洋
	  * @Title: 添加用户的登录信息
	  * @date 2017年4月20日--上午11:51:17 
	  * @return void
	  * @throws
	 */
	public void addUserLoginInfo(String ipAddress,String sellerNick){
		//将登录信息添加到数据库表中
		UserLoginInfo ul = new UserLoginInfo();
		ul.setSellerNick(sellerNick);
		ul.setIpAddress(ipAddress);
		ul.setLoginTime(new Date());
		myBatisDao.execute(UserLoginInfo.class.getName(), "addUserLoginInfo", ul);
	}
}
