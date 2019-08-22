package s2jh.biz.shop.crm.message.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "CRM_SMS_SEND_RECORD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "短信发送记录")
public class SmsSendRecord extends BaseNativeEntity{

	private static final long serialVersionUID = 5456663584838414056L;
	
	@MetaData(value="短信发送流水号")
	@Column(name="BIZ_ID")
	private Long bizId;
	
	@MetaData(value="短信接收号码")
	@Column(name="REC_NUM")
	private String recNum;
	
	@MetaData(value="短信错误码")
	@Column(name="RESULT_CODE")
	private String resultCode;
	
	@MetaData(value="模板编码")
	@Column(name="CODE")
	private String code;
	
	@MetaData(value="短信内容")
	@Column(name="CONTENT")
	private String content;
	
	@MetaData(value="短信接收时间")
	@Column(name="RECEIVER_TIME")
	private Date receiverTime;
	
	@MetaData(value="发送时间")
	@Column(name="SEND_TIME")
	private Date sendTime;
	
	@MetaData(value="短信类型 设置类型 1-下单关怀 2-常规催付 3-二次催付 4-聚划算催付 5-预售催付 "
			+ "6-发货提醒 7-到达同城提醒 8-派件提醒 9-签收提醒 10-疑难件提醒 11-延时发货提醒 12-宝贝关怀 13-付款关怀 "
			+ "14-回款提醒 15-退款关怀 16-自动评价 17-批量评价 18-评价记录 19-中差评查看 20-中差评监控 21-中差评安抚 "
			+ "22-中差评统计 23-中差评原因 24中差评原因设置 25-中差评原因分析 26-手动订单提醒 27-优秀催付案例 28-效果统计 "
			+ "29-买家申请退款 30-退款成功 31-等待退货 32-拒绝退款 33-会员短信群发 34-指定号码群发 35-订单短信群发 36-会员互动")
	@Column(name="TYPE")
	private String type;
	

	@MetaData(value="发送状态 1：发送失败，2：发送成功，3：手机号码不正确，4：号码重复， 5 ：黑名单， 6 ：重复被屏蔽 /重复发送")
	@Column(name="STATUS")
	private Integer status;

	@MetaData(value="短信渠道  1--淘宝 2--京东 , 3--天猫 , 4---自定义签名 ")
	@Column(name="CHANNEL")
	private String channel;

	@MetaData(value="实际扣除短信条数")
	@Column(name="ACTUAL_DEDUCTION")
	private Integer actualDeduction;

	
	@MetaData(value="订单编号")
	@Column(name="order_id")
	private String orderId;
	
	@MetaData(value="买家昵称")
	@Column(name="buyer_nick")
	private String buyerNick;

	@MetaData(value="收货人昵称")
	@Column(name="nickname")
	private String nickname;
	
	@MetaData(value="短信签名")
	@Column(name="autograph")
	private String autograph;
	
	@MetaData(value="操作人")
	@Column(name="user_id")
	private String userId;
	
	@MetaData(value="总记录id")
	@Column(name="msg_id")
	private Long msgId;
	
	
	@MetaData(value ="是否删除(显示或者隐藏)--true:显示 /false:不显示  默认保存true")
	@Column(name="is_show")
	private boolean isShow;
	
	@MetaData(value ="活动名称")
	@Column(name="activity_name")
	private String activityName;
}
