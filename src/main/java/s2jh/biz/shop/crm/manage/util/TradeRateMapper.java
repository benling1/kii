package s2jh.biz.shop.crm.manage.util;

import com.taobao.api.domain.TradeRate;

import s2jh.biz.shop.crm.manage.entity.TradeRateDTO;

public class TradeRateMapper extends Mapper<TradeRateDTO,TradeRate> {

	private static TradeRateMapper tradeRateMapper;
	
	private TradeRateMapper(){
		super(TradeRateDTO.class,TradeRate.class);
	}
	
	public TradeRateMapper getInstance(){
		if(tradeRateMapper == null ){
			tradeRateMapper = new TradeRateMapper();
		}
		return tradeRateMapper;
	}
	
	@Override
	public TradeRate mapToEntity(TradeRateDTO value) {
		TradeRate tr = super.mapToEntity(value);
		return tr;
	}

	@Override
	public TradeRateDTO mapToValue(TradeRate entity) {
		return this.mapToValue(entity, false);
	}
	
	public TradeRateDTO mapToValueForBackend(TradeRate entity) {
		return super.mapToValue(entity);
	}
	
	public TradeRateDTO mapToValue(TradeRate entity,boolean loadDetail) {
		if(null!=entity){
			TradeRateDTO drto=super.mapToValue(entity);
			return drto;
		}else{
			return null;
		}
	}
	
}
