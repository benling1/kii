package s2jh.biz.shop.crm.manage.util;

import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.message.entity.SmsSendRecord;

public class SmsRecordMapper extends Mapper<SmsRecordDTO, SmsSendRecord>{
	
	private static SmsRecordMapper SmsSendRecordMapper;
	
	private SmsRecordMapper() {
		super(SmsRecordDTO.class, SmsSendRecord.class);
	}
	
	public static SmsRecordMapper getInstance() {
		if (SmsSendRecordMapper == null) {
			SmsSendRecordMapper = new SmsRecordMapper();
		}
		return SmsSendRecordMapper;
	}

	@Override
	public SmsSendRecord mapToEntity(SmsRecordDTO value) {
		SmsSendRecord t = super.mapToEntity(value);
		return t;
	}

	/* (non-Javadoc)
	 * @see com.hna.mars.core.mapper.Mapper#mapToValue(java.lang.Object)
	 */
	@Override
	public SmsRecordDTO mapToValue(SmsSendRecord entity) {
		return this.mapToValue(entity, false);
	}
	
	public SmsRecordDTO mapToValueForBackend(SmsSendRecord entity) {
		return super.mapToValue(entity);
	}
	
	
	public SmsRecordDTO mapToValue(SmsSendRecord entity,boolean loadDetail) {
		if(null!=entity){
			SmsRecordDTO dto=super.mapToValue(entity);
			return dto;
		}else{
			return null;
		}
	}
	
}
