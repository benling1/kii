package s2jh.biz.shop.crm.manage.util;

import s2jh.biz.shop.crm.manage.entity.OrdersDTO;

import com.taobao.api.domain.Order;

public class OrdersMapper extends Mapper<OrdersDTO, Order>{
	
	private static OrdersMapper tradeMapper;
	
	private OrdersMapper() {
		super(OrdersDTO.class, Order.class);
	}
	
	public static OrdersMapper getInstance() {
		if (tradeMapper == null) {
			tradeMapper = new OrdersMapper();
		}
		return tradeMapper;
	}

	@Override
	public Order mapToEntity(OrdersDTO value) {
		Order t = super.mapToEntity(value);
		return t;
	}

	/* (non-Javadoc)
	 * @see com.hna.mars.core.mapper.Mapper#mapToValue(java.lang.Object)
	 */
	@Override
	public OrdersDTO mapToValue(Order entity) {
		return this.mapToValue(entity, false);
	}
	
	public OrdersDTO mapToValueForBackend(Order entity) {
		return super.mapToValue(entity);
	}
	
	
	public OrdersDTO mapToValue(Order entity,boolean loadDetail) {
		if(null!=entity){
			OrdersDTO dto=super.mapToValue(entity);
			return dto;
		}else{
			return null;
		}
	}
	
}
