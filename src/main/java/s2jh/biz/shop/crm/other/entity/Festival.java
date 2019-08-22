package s2jh.biz.shop.crm.other.entity;

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
@Table(name = "CRM_festival")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "节日信息")
public class Festival extends BaseNativeEntity{
		
	private static final long serialVersionUID = 4091223918911309471L;
	
	@MetaData(value="节日名")
	@Column(name="name")
	private String name;
	
	@MetaData(value="节日时间")
	@Column(name="dateTime")
	private String dateTime;
}
