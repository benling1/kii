package s2jh.biz.shop.crm.taobao.service.refund.created;

import java.util.Date;
import java.util.Map;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.taobao.service.judgment.JudgmentMainUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoSendMessageUtil;
@Service
@Deprecated 
public class RefundCreatedServiceImpl implements RefundCreatedService {

	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private JudgmentMainUtil judgmentMainUtil;
	@Autowired
	private TaoBaoSendMessageUtil taoBaoSendMessageUtil;
	
	@Override
	public boolean refundCreate(JSONObject parseJSON) throws Exception {
		//买家申请退款
		//进行业务的基本逻辑判断
		Map<String,Object> map = this.judgmentMainUtil.startJudgeOrder(parseJSON, new Date(), "29");
		//逻辑判断都通过了  开始准备调用工具类发送短信
		if((boolean)map.get("flag")){
			taoBaoSendMessageUtil.sendSingleMessage(map);
		}
		return true;
	}

}
