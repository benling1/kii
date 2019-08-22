package s2jh.biz.shop.crm.taobao.service.ordercare;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.taobao.service.judgment.JudgmentMainUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;
@Service
@Deprecated 
public class PlaceAnOrderCareServiceImpl implements PlaceAnOrderCareService {

	@Autowired
	private JudgmentMainUtil judgmentMainUtil;
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;
	
	@Override
	public boolean tradeCreate(JSONObject parseJSON) throws Exception {
		Thread.sleep(3000);
		Date myDate = new Date();
		//下单关怀
		try {
			//进行业务的基本逻辑判断
			Map<String,Object> map = this.judgmentMainUtil.startJudgeOrder(parseJSON, myDate, "1");
			//逻辑判断都通过了  开始准备调用工具类发送短信
			if((boolean)map.get("flag")){
				taoBaoSendMessageUtil.sendSingleMessage(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//首次催付短信设置
			this.judgmentMainUtil.startJudgeOrder(parseJSON, myDate, "2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//直接设置二次催付的时间
			this.judgmentMainUtil.startJudgeOrder(parseJSON, new Date(), "3");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//判断订单是否是聚划算来源
			this.judgmentMainUtil.startJudgeOrder(parseJSON, myDate, "4");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
