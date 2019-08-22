package s2jh.biz.shop.crm.historyOrder.entity;

import java.sql.Date;

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
@Table(name = "CRM_ORDER_HISTORY_IMPORT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value ="历史订单导入记录")
public class OrderHistoryImport extends BaseNativeEntity{

	private static final long serialVersionUID = -5591543932424293916L;
	
	@MetaData(value="用户编号")
	@Column(name="USER_ID")
	private String userId;
	
	@MetaData(value="导入的订单文件名称")
	@Column(name="ORDER_NAME")
	private String orderName;
	
	@MetaData(value="商品文件名称")
	@Column(name="COMMODITY_NAME")
	private String commodityName;
	
	@MetaData(value="总行数")
	@Column(name="SUM_NUMBER")
	private Integer sumNumber;
	
	@MetaData(value="处理状态    0 --导入完成   1--导入中  ")
	@Column(name="STATE")
	private String state;
	
	@MetaData(value="处理完成行数")
	@Column(name="COMPLETE_NUMBER")
	private Integer completeNumber;
	
	@MetaData(value="报错总行数")
	@Column(name="ERROR_NUMBER")
	private Integer errorNumber;
	
	@MetaData(value="成功数量")
	@Column(name="COMPLETED_QUANTITY")
	private Integer completedQuantity;
	
	@MetaData(value="重复数量")
	@Column(name="REPETITION_NUMBER")
	private Integer repetitionNumber;
	

}
