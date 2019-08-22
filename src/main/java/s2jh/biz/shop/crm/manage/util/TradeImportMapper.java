package s2jh.biz.shop.crm.manage.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.order.entity.Orders;
import s2jh.biz.shop.crm.order.entity.TransactionOrder;
import s2jh.biz.shop.utils.MyBeanUtils;

public class TradeImportMapper extends Mapper<TradeDTO, TransactionOrder>{
	
	private static TradeImportMapper tradeImportMapper;
	
	private TradeImportMapper() {
		super(TradeDTO.class, TransactionOrder.class);
	}
	
	public static TradeImportMapper getInstance() {
		if (tradeImportMapper == null) {
			tradeImportMapper = new TradeImportMapper();
		}
		return tradeImportMapper;
	}

	@Override
	public TransactionOrder mapToEntity(TradeDTO value) {
		TransactionOrder t = super.mapToEntity(value);
		return t;
	}

	/* (non-Javadoc)
	 * @see com.hna.mars.core.mapper.Mapper#mapToValue(java.lang.Object)
	 */
	@Override
	public TradeDTO mapToValue(TransactionOrder entity) {
		if(null!=entity){
			TradeDTO dto=super.mapToValue(entity);
			List<Orders> orderList = entity.getOrderList();
			List<OrdersDTO>  ordersDTOList  = new ArrayList<OrdersDTO>();
			if(null!=orderList&&orderList.size()>0){
				OrdersDTO  ordersDTO = null;
				for (Orders  orders : orderList) {
					ordersDTO = new OrdersDTO();
					try {
						MyBeanUtils.copyProperties(ordersDTO, orders);
					} catch (InvocationTargetException e) {
					} catch (IllegalAccessException e) {
					}
					ordersDTOList.add(ordersDTO);
				}
			}
			if(null!=ordersDTOList&&ordersDTOList.size()>0){
				dto.setOrders(ordersDTOList);
			}
			return dto;
		}else{
			return null;
		}
	}
	
}
