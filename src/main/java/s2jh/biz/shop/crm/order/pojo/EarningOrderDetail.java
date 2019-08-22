package s2jh.biz.shop.crm.order.pojo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单中心催付提醒效果分析的每日详情
 * @author Administrator
 *
 */
@Setter
@Getter
public class EarningOrderDetail {

	//回款订单号
	private String orderId;
	//催付时间
	private Date sendTime;
	//买家昵称
	private String buyerNick;
	//手机号
	private String recNum;
	//回款订单金额
	private Double earningFee;
	//付款时间
	private Date payTime;
	//点击时间
	private Date clickTime;
}
