package s2jh.biz.shop.crm.taobao.service.judgment;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;
import s2jh.biz.shop.crm.taobao.service.judgment.advanced.JudgeGoodsUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.advanced.JudgeLocaltionUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.advanced.JudgeOrderSourceUtil;
import s2jh.biz.shop.crm.taobao.service.judgment.advanced.JudgeVendormarkUtil;
@Component
@Deprecated 
public class JudgeUserOrderAdvancedUtil extends AbstractJudgeOrderSetUp {
	@Autowired
	private	JudgeGoodsUtil judgeGoodsUtil;
	@Autowired
	private	JudgeOrderSourceUtil judgeOrderSourceUtil;
	@Autowired
	private	JudgeLocaltionUtil judgeLocaltionUtil;
	@Autowired
	private	JudgeVendormarkUtil judgeVendormarkUtil;
//	@Autowired
//	private	JudgeMemberLevelUtil judgeMemberLevelUtil;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JudgeUserOrderAdvancedUtil.class);
	@Override
	public Map<String,Object>  startJob(Map<String,Object> map) {
		if((boolean)map.get("flag") == true ){	// 之前已经判断为发送短信，后续才判断
			//查询商户的高级过滤条件是否存在
			OrderAdvancedSetting orderAdvancedSetting = myBatisDao.findBy(OrderAdvancedSetting.class.getName(), "findOrderAdvancedSettingByUserIdAndSettingTypeSend", map);
			if(orderAdvancedSetting != null){
				this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",高级设置条件判断开始，开始********************");
				map.put("orderAdvancedSetting", orderAdvancedSetting);
				String settingType  = (String) map.get("settingType");
				//如果是同城，派件，签收提醒
				if (settingType.equals("7")) {
					//如果是到达同城提醒设置，高级设置只有卖家标记
					map=judgeVendormarkUtil.startJob(map);
				}else{
					//如果高级设置存在  查询里面的  商品筛选、地区选择、订单来源、小红旗、用户等级
					this.judgeGoodsUtil.setNext(this.judgeLocaltionUtil);
					this.judgeLocaltionUtil.setNext(this.judgeOrderSourceUtil);
					this.judgeOrderSourceUtil.setNext(this.judgeVendormarkUtil);
//					this.judgeVendormarkUtil.setNext(this.judgeMemberLevelUtil);
					map = this.judgeGoodsUtil.startJob(map);
				}
//				
			}else{
				//未开启对应的关怀
				this.logger.debug("*******************tid:"+map.get("tid")+",类型："+map.get("settingType")+",高级设置未设置********************");
			}
			//责任链判断
			if(super.getNext()!=null){
				map = super.getNext().startJob(map);
			}
		}
		return map;
	}

}
