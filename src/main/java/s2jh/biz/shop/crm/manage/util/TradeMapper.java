package s2jh.biz.shop.crm.manage.util;

import java.util.ArrayList;
import java.util.List;

import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;

import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;

public class TradeMapper extends Mapper<TradeDTO, Trade>{
	
	private static TradeMapper tradeMapper;
	
	private TradeMapper() {
		super(TradeDTO.class, Trade.class);
	}
	
	public static TradeMapper getInstance() {
		if (tradeMapper == null) {
			tradeMapper = new TradeMapper();
		}
		return tradeMapper;
	}

	@Override
	public Trade mapToEntity(TradeDTO value) {
		Trade t = super.mapToEntity(value);
		return t;
	}

	/* (non-Javadoc)
	 * @see com.hna.mars.core.mapper.Mapper#mapToValue(java.lang.Object)
	 */
	@Override
	public TradeDTO mapToValue(Trade entity) {
		return this.mapToValue(entity, false);
	}
	
	public TradeDTO mapToValueForBackend(Trade entity) {
		return super.mapToValue(entity);
	}
	
	
	public TradeDTO mapToValue(Trade entity,boolean loadDetail) {
		if(null!=entity){
			TradeDTO dto=super.mapToValue(entity);
			dto.setNUM_IID(entity.getNumIid()==null?null:entity.getNumIid().toString());
			if(loadDetail){
				if(null!=entity.getOrders()&&entity.getOrders().size()>0){
					List<OrdersDTO> orderDTOList = new ArrayList<OrdersDTO>();
					for (Order order : entity.getOrders()) {
						OrdersDTO mapToValue = OrdersMapper.getInstance().mapToValue(order);
						mapToValue.setTid(String.valueOf(entity.getTid()));
						mapToValue.setReceiverDistrict(entity.getReceiverDistrict());
						mapToValue.setReceiverCity(entity.getReceiverCity());
						mapToValue.setStepTradeStatus(entity.getStepTradeStatus());
						mapToValue.setCreatedUTC(entity.getCreated());
						mapToValue.setReceiverName(entity.getReceiverName());
						mapToValue.setReceiverMobile(entity.getReceiverMobile() == null
								? entity.getReceiverPhone() : entity.getReceiverMobile());
						mapToValue.setBuyerFlag(entity.getBuyerFlag() == null ? null : entity.getBuyerFlag().intValue());
						mapToValue.setSellerFlag(entity.getSellerFlag() == null ? null: entity.getSellerFlag().intValue());
						// 后续补加字段
						mapToValue.setModifiedUTC(entity.getModified());
						orderDTOList.add(mapToValue);
					}
					dto.setOrders(orderDTOList);
				}
			}
			return dto;
		}else{
			return null;
		}
	}
	
	
	
	
	
}
