/**
 * 
 */
package s2jh.biz.shop.crm.message.service.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import lab.s2jh.core.dao.mybatis.MyBatisDao;

/** 
 * @Title: 
 * @date 2017年4月13日--下午6:07:06 
 * @param     设定文件 
 * @return 返回类型 
 * @throws 
 */
public class SmsSchedulePay implements Runnable {

	private String sellerName;
	private Long tid;
	private MyBatisDao myBatisDao;
	
	/** 
	* @Title: 
	* @date 2017年4月13日--下午6:08:13 
	* @param @param sellerName
	* @param @param tid
	* @param @param myBatisDao    设定文件 
	* @return 返回类型 
	* @throws 
	*/
	public SmsSchedulePay(String sellerName, Long tid, MyBatisDao myBatisDao) {
		this.sellerName = sellerName;
		this.tid = tid;
		this.myBatisDao = myBatisDao;
	}

	@Override
	public void run() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sellerName", sellerName);
		map.put("tid", tid);
		List<Long> delList = myBatisDao.findList(SmsSendInfo.class.getName() + "Schedule","findIdBySellerSendGoods", map);
		if(delList==null||delList.size()==0){
			return ;
		}else{
			map.put("delList", delList);
			myBatisDao.execute(SmsSendInfo.class.getName() + "Schedule", "doRemoveSms", map);
		}
	}

}
