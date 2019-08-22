package s2jh.biz.shop.crm.taobao.service.judgment.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.dao.mybatis.MyBatisDaoT;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.TradeFullinfoGetResponse;

import s2jh.biz.shop.crm.order.entity.OrderAdvancedSetting;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.order.pojo.TbTransactionOrder;
import s2jh.biz.shop.crm.taobao.service.judgment.abs.AbstractJudgeOrderSetUp;

@Component
@Deprecated 
public class JudgeGoodsUtil extends AbstractJudgeOrderSetUp {
	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(JudgeGoodsUtil.class);

	@Autowired
	private MyBatisDaoT myBatisDaoT;

	@Override
	public Map<String, Object> startJob(Map<String, Object> map) {
		if ((boolean) map.get("flag") == true) { // 之前已经判断为发送短信，后续才判断
			OrderAdvancedSetting orderAdvancedSetting = (OrderAdvancedSetting) map
					.get("orderAdvancedSetting");
			String sellerProductSelect = orderAdvancedSetting != null ? orderAdvancedSetting
					.getProductSelect() : null;
			// 商品选择不为空且不是全选
			if (sellerProductSelect != null && !"0".equals(sellerProductSelect)&&!"".equals(sellerProductSelect)) {
				// 卖家商城用户设置的过滤商品ID不为空且不是空字符串
				String sellerGoods = orderAdvancedSetting != null ? orderAdvancedSetting
						.getItemId() : null;
				if (sellerGoods != null) {
					Trade trade = (Trade) map.get("trade");
					String oderGoods = getItemNumId(trade);
					if(oderGoods==null){
						this.logger
						.debug("*******************tid:"
								+ map.get("tid")
								+ ",类型："
								+ map.get("settingType")
								+ ",商品编号未查询到！不发送短信********************");
						map.put("flag", false);
						return map;
					}
					// 对用户设置的商品ID字段先进行拆分，如果有的话 拆出来一个个在商家的过滤条件中查找， indexOf()
					// 如果找不到返回-1
					String sellerGoodsArray[] = sellerGoods.split(",");
					if ("1".equals(sellerProductSelect)) {
						for (int i = 0; i < sellerGoodsArray.length; i++) {
							String goods = sellerGoodsArray[i];
							// 在商品过滤中找到，则意味着是商户指定发送的 发送短信
							if (oderGoods.contains(goods)) {
								map.put("flag", true);
								break;
							} else {
								map.put("flag", false);
							}
						}
						if ((boolean) map.get("flag") == false) {
							this.logger
									.debug("*******************tid:"
											+ map.get("tid")
											+ ",类型："
											+ map.get("settingType")
											+ ",商品过滤--指定发送  订单和设置条件不符合********************");
						}
					} else if ("2".equals(sellerProductSelect)) {
						boolean sendFlag = false;
						for (int i = 0; i < sellerGoodsArray.length; i++) {
							String goods = sellerGoodsArray[i];
							// 在商品过滤中找到，则意味着是商户排除指定商品发送的 不发送短信
							if (!oderGoods.contains(goods)) {
								sendFlag = true;
								break;
							}
						}
						if(!sendFlag){
							this.logger
							.debug("*******************tid:"
									+ map.get("tid")
									+ ",类型："
									+ map.get("settingType")
									+ ",商品过滤--排除指定发送  订单和设置条件不符合********************");
							map.put("flag", false);
						}
					} else {
						// 用户设置错误，不发送短信
						this.logger
								.debug("*******************tid:"
										+ map.get("tid")
										+ ",类型："
										+ map.get("settingType")
										+ ",用户设置的商品过滤条件错误了  只能是0、1和2********************");
						map.put("flag", false);
					}

				}
			}
		}
		// 责任链判断
		if (super.getNext() != null) {
			map = super.getNext().startJob(map);
		}
		return map;
	}

	/**
	 * 创建人：邱洋
	 * 
	 * @Title: getItemNumId
	 * @Description: TODO(根据tid订单号查询子订单购买的商品编号)
	 * @param @param tid
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String getItemNumId(Trade trade) {
		String oderGoods = null;
		List<Order> list = new ArrayList<Order>();

		if (trade != null && trade.getNumIid() != null) {
			oderGoods = trade.getNumIid().toString();
		} else if (trade != null) {
			list = trade.getOrders();
			if (list == null || list.size() == 0) {
				TbTransactionOrder tbTransactionOrder = myBatisDaoT.findBy(
						TbTransactionOrder.class.getName(),
						"getTbTransactionOrders", trade.getTid());
				if (tbTransactionOrder != null) {
					String jdpResponse = tbTransactionOrder.getJdpResponse();
					TradeFullinfoGetResponse rsp = null;
					try {
						rsp = TaobaoUtils.parseResponse(jdpResponse,
								TradeFullinfoGetResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					list = rsp.getTrade().getOrders();
				}else{
					return null;
				}
			}
			for (Order od : list) {
				if (oderGoods != null && od.getNumIid() != null) {
					oderGoods = oderGoods + "," + od.getNumIid().toString();
				} else if (od.getNumIid() != null) {
					oderGoods = od.getNumIid().toString();
				}
			}
		}
		return oderGoods;
	}
}
