package s2jh.biz.shop.crm.schedule.job.auto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import s2jh.biz.shop.crm.data.service.ShopDataStatisticsService;
import s2jh.biz.shop.crm.user.entity.UserInfo;

@Component
@Deprecated 
public class AutoStaticsSellerDataJob {
	@Autowired
	private MyBatisDao myBatisDAO;
	@Autowired
	private ShopDataStatisticsService shopDataStatisticsService;
	//定时任务   统计买家昨日
	public void job(){
		List<String> userList = this.myBatisDAO.findList(UserInfo.class.getName(), "findAllByAccount", null);
		try {
			shopDataStatisticsService.scheduleStatisticsGoods(userList);
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
}
