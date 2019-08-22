package s2jh.biz.shop.crm.other.entity;

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

@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "crm_tasknode")
@Getter
@Setter
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskNode extends BaseNativeEntity{

	private static final long serialVersionUID = 1L;

	@MetaData(value = "订单同步全局任务节点")
	@Column(name = "task_node")
	private Integer taskNode;
	
	@MetaData(value = "订单同步时间任务节点")
	@Column(name = "task_endTime")
	private Date taskEndTime;
	
	@MetaData(value = "订单同步类型")
	@Column(name = "type")
	private String type;
}
