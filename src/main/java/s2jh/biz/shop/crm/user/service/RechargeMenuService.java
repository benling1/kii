package s2jh.biz.shop.crm.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.user.dao.RechargeMenuDao;
import s2jh.biz.shop.crm.user.entity.RechargeMenu;
import s2jh.biz.shop.utils.SpringContextUtil;

/**
 * @ClassName: UserInfoService
 * @Description: TODO(项目列表(充值套餐))
 * @author:jackstraw_yu
 * @date 2016年12月21日
 *
 */
@Service
@Transactional
public class RechargeMenuService extends BaseService<RechargeMenu, Long> {

	@Autowired
	private RechargeMenuDao rechargeMenuDao;

	@Override
	protected BaseDao<RechargeMenu, Long> getEntityDao() {
		return rechargeMenuDao;
	}

	@Autowired
	private MyBatisDao myBatisDao;

	/**
	 * @Title: queryMidList
	 * @Description: TODO(获取项目列表的收费代码的集合)
	 * @param @return 参数
	 * @return List<String> 返回类型
	 * @author:jackstraw_yu
	 * @throws
	 */
	public List<String> queryMidList() {
		MyBatisDao myBatisDao = SpringContextUtil.getBean("myBatisDao");
		List<String> list = myBatisDao.findList(RechargeMenu.class.getName(),
				"queryMidList", null);
		return list;
	}

	/**
	 * @Title: queryRechargeMenu
	 * @Description: TODO(通过mid获取RechargeMenu对象)
	 * @param @param mid
	 * @param @return 参数
	 * @return RechargeMenu 返回类型
	 * @author:jackstraw_yu
	 * @throws
	 */
	public RechargeMenu queryRechargeMenu(String mid) {
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("mid", mid);
		MyBatisDao myBatisDao = SpringContextUtil.getBean("myBatisDao");
		RechargeMenu rechargeMenu = myBatisDao.findBy(
				RechargeMenu.class.getName(), "queryRechargeMenu", hashMap);
		return rechargeMenu;
	}

	/**
	* @Title: queryRechargeMenuList
	* @Description: TODO(查询出充值项目列表,其中不包含父级mid)
	* @param @return    参数
	* @return List<RechargeMenu>    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public List<RechargeMenu> queryRechargeMenuList() {

		List<RechargeMenu> rechargeMenuList = myBatisDao.findList(RechargeMenu.class.getName(), "queryRechargeMenuList", null);
		return rechargeMenuList;
	}
		
}
