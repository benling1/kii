package s2jh.biz.shop.crm.order.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * 营销中心效果分析截止某一日的真实客户数
 * @author Administrator
 *
 */
@Getter
@Setter
public class EffectPictureReal {

	//截至今日总客户、截至今日付款客户、截至今日未付款客户、截至今日退款客户
	private Integer totalBuyerReal_TOTAL;
	private Integer payBuyerReal_TOTAL;
	private Integer waitPayBuyerReal_TOTAL;
	private Integer refundBuyerReal_TOTAL;
	//截至今日TAOBAO客户、截至今日TAOBAO付款客户、截至今日TAOBAO未付款客户、截至今日TAOBAO退款客户
	private Integer totalBuyerReal_TAOBAO;
	private Integer payBuyerReal_TAOBAO;
	private Integer waitPayBuyerReal_TAOBAO;
	private Integer refundBuyerReal_TAOBAO;
	//截至今日WAP总客户、截至今日WAP付款客户、截至今日WAP未付款客户、截至今日WAP退款客户
	private Integer totalBuyerReal_WAP;
	private Integer payBuyerReal_WAP;
	private Integer waitPayBuyerReal_WAP;
	private Integer refundBuyerReal_WAP;
	
}
