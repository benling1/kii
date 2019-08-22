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
@Table(name = "CRM_ORDER_IMPORT_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value ="历史订单导入详情")
public class OrderHistoryInfo extends BaseNativeEntity{

	private static final long serialVersionUID = 5609230180419894816L;
	
	@MetaData(value="卖家用户编号")
	@Column(name="USER_ID")
	private String userId;
	
	@MetaData(value="导入记录编号")
	@Column(name="HISTORY_ID")
	private Long historyId;
	
	@MetaData(value="报错行数")
	@Column(name="ERROR_LINE")
	private Integer errorLine;
	
	@MetaData(value="报错行内容")
	@Column(name="ERROR_CONTENT")
	private String errorContent;
	
	@MetaData(value="记录时间")
	@Column(name="CREATETIME")
	private Date createtime;
	
	@MetaData(value="备注")
	@Column(name="REMARKS")
	private String remarks;

}
