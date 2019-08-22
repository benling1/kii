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
@Table(name = "CRM_SMS_Template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "短信模板")
public class SmsTemplate extends BaseNativeEntity{
		
	private static final long serialVersionUID = -4542932219106123307L;
		
	@MetaData(value="短信模板内容")
	@Column(name="content")
	private String content;
	
	@MetaData(value="短信类型(聚划算、上新、周年庆、国庆节、情人节、评价模板---- 新加：1下单关怀，2常规催付，3二次催付，4聚划算催付，5预售催付，6发货提醒，7到达同城提醒"
			+ "8派件提醒，9签收提醒，10疑难件提醒，11延时发货提醒，12宝贝关怀，13付款关怀，14回款提醒，16自动评价，20中差评监控，21中差评安抚，29买家申请退款，30退款成功，31等待退货，32拒绝退款，37好评提醒")
	@Column(name="type")
	private String type;
	/*@MetaData(value="短信类型(聚划算、上新、周年庆、国庆节、情人节、评价模板")
	@Column(name="type")
	private String type;*/
	
	@MetaData(value="添加时间")
	@Column(name="createDate")
	private String createDate;
	
	@MetaData(value="模板使用热度")
	@Column(name="fashion")
	private String fashion;
	
	@MetaData(value="模板名称")
	@Column(name="name")
	private String name;
	
	@MetaData(value="用户昵称")
	@Column(name="user_nick")
	private String userNick;
}
