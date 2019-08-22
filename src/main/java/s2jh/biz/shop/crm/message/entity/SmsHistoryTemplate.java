package s2jh.biz.shop.crm.message.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_SMS_HistoryTemplate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "短信模板使用信息")

public class SmsHistoryTemplate extends BaseNativeEntity{

	
	private static final long serialVersionUID = -1968637050132207514L;
		
	@MetaData(value ="卖家编号")
	@Column(name="userId")
	private String userId;
	
	@MetaData(value ="短信模板编号")
	@Column(name="templateId")
	private String templateId;

	@MetaData(value ="短信模板类型")
	@Column(name="type")
	private String type;
	
	@MetaData(value ="短信内容")
	@Column(name="content")
	private String content;
}
