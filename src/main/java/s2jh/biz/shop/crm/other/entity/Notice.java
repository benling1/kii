package s2jh.biz.shop.crm.other.entity;

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
@Table(name = "CRM_SYSTEM_ANNOUNCEMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "系统公告")
public class Notice extends BaseNativeEntity{

	private static final long serialVersionUID = -915951156358084319L;
	
	@MetaData(value="公告标题")
	@Column(name="TITLE")
	private String title;
	
	@MetaData(value="公告内容")
	@Column(name="CONTENT")
	private String content;
	
	@MetaData(value="来源")
	@Column(name="SOURCE")
	private String source;
	
	@MetaData(value="创建时间")
	@Column(name="CREATEDATE")
	private Date createdate;
	
	@MetaData(value="修改时间")
	@Column(name="MODIFYDATE")
	private Date modifydate;

}
