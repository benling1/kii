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
@Table(name = "CRM_SMS_SETTING")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "短信设置提醒")
public class SmsSetting extends BaseNativeEntity{

	private static final long serialVersionUID = -4708197491899980129L;
	
	@MetaData(value="卖家用户ID")
	@Column(name="USER_ID")
	private String userId;
	
	@MetaData(value="买家用户ID")
	@Column(name="MEMEB_INFO_ID")
	private Long memebInfoId;
	
	@MetaData(value="商品ID,多个商品使用,隔开")
	@Column(name="ITEM_ID")
	private String itemId;
	
	@MetaData(value="订单ID")
	@Column(name="ORDERID")
	private String orderId;
	
	@MetaData(value="短信变量 0-订单号 1-下单时间 2-卖家昵称 3-买家姓名")
	@Column(name="MESSAGEVARIABLE")
	private String messageVariable;
	
	@MetaData(value="短息内容")
	@Column(name="MESSAGECONTENT")
	private String messageContent;
	
	@MetaData(value="短信签名")
	@Column(name="MESSAGESIGNATURE")
	private String messageSignature;
	
	@MetaData(value="设置类型 1-下单关怀 2-常规催付 3-二次催付 4-聚划算催付 5-预收催付 6-发货提醒 7-到达同城提醒 8-派件提醒 9-签收提醒 10-疑难件提醒 11-延时发货提醒 12-宝贝关怀 "
			+ "13-付款关怀 14-回款提醒 15-退款关怀 16-自动评价 17-批量评价 18-评价记录 19-中差管理 20-中差评监控 21-中差评安抚 22-中差评统计 23-中差评原因 24中差评原因设置 "
			+ "25-中差评原因分析 26-手动订单提醒 27-优秀催付案例 28-效果统计 29-买家申请退款 30-退款成功 31-等待退货 32-拒绝退款")
	@Column(name="SETTINGTYPE")
	private String settingType;
	
	@MetaData(value="付款链接")
	@Column(name="PAYMENTLINK")
	private String paymentLink;
	
	@MetaData(value="测试手机号")
	@Column(name="MOBLIETEST")
	private String mobileTest;
	
	@MetaData(value="短信条数")
	@Column(name="SMSNUMBER")
	private int smsNumber;
	
	@MetaData(value="是否开启或关闭 0--开启 1--关闭")
	@Column(name="status")
	private String status;
	

}
