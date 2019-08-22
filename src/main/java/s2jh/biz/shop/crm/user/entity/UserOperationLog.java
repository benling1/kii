package s2jh.biz.shop.crm.user.entity;

import java.util.Date;

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
@Table(name = "CRM_OPERATION_LOG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "用户操作日志")
public class UserOperationLog extends BaseNativeEntity {

	private static final long serialVersionUID = 1946429588574404744L;

	@MetaData(value = "用户ID")
	@Column(name = "USER_ID")
	private String userId;

	@MetaData(value = "操作人")
	@Column(name = "OPERATOR")
	private String operator;

	@MetaData(value = "操作功能 ")
	@Column(name = "FUNCTIONS")
	private String functions;

	@MetaData(value = "操作类型 1-登录 ")
	@Column(name = "TYPE")
	private String type;

	@MetaData(value = "操作时间")
	@Column(name = "DATE")
	private Date date;

	@MetaData(value = "状态 1-成功 2-失败")
	@Column(name = "STATE")
	private String state;

	@MetaData(value = "IP地址")
	@Column(name = "IP_ADD")
	private String ipAdd;

	@MetaData(value = "备注")
	@Column(name = "REMARK")
	private String remark;

	@MetaData(value = "所属功能 1-下单关怀 2-常规催付 3-二次催付 4-聚划算催付 5-预收催付 6-发货提醒 7-到达同城提醒 8-派件提醒 9-签收提醒 10-疑难件提醒 11-延时发货提醒 12-宝贝关怀 "
			+ "13-付款关怀 14-回款提醒 15-退款关怀 16-自动评价 17-批量评价 18-评价记录 19-中差管理 20-中差评监控 21-中差评安抚 22-中差评统计 23-中差评原因 24中差评原因设置 "
			+ "25-中差评原因分析 26-手动订单提醒 27-优秀催付案例 28-效果统计 29-买家申请退款 30-退款成功 31-等待退货 32-拒绝退款 33-黑名单管理")
	@Column(name = "FUNCTIONGENS")
	private String functionGens;

	public UserOperationLog(){};
	
	/**
	 * 
	 * @param userId 	不多解释
	 * @param operator	操作人
	 * @param function	操作功能
	 * @param type		操作类型 1-登录 
	 * @param date		操作时间
	 * @param state		状态 1-成功 2-失败
	 * @param ipAdd 	IP地址
	 * @param remark 	备注
	 * @param functionGens所属功能 1-下单关怀 2-常规催付 3-二次催付 4-聚划算催付 5-预收催付 6-发货提醒 7-到达同城提醒 8-派件提醒 9-签收提醒 10-疑难件提醒 11-延时发货提醒 12-宝贝关怀 "
			+ "13-付款关怀 14-回款提醒 15-退款关怀 16-自动评价 17-批量评价 18-评价记录 19-中差管理 20-中差评监控 21-中差评安抚 22-中差评统计 23-中差评原因 24中差评原因设置 "
			+ "25-中差评原因分析 26-手动订单提醒 27-优秀催付案例 28-效果统计 29-买家申请退款 30-退款成功 31-等待退货 32-拒绝退款 33-黑名单管理
	 */
	public UserOperationLog(String userId, String operator, String functions,
			String type, Date date, String state, String ipAdd, String remark,
			String functionGens) {
		super();
		this.userId = userId;
		this.operator = operator;
		this.functions = functions;
		this.type = type;
		this.date = date;
		this.state = state;
		this.ipAdd = ipAdd;
		this.remark = remark;
		this.functionGens = functionGens;
	}

}
