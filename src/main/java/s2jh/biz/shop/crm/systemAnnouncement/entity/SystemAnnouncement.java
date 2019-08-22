package s2jh.biz.shop.crm.systemAnnouncement.entity;

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
@Table(name = "crm_system_announcement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "系统公告")
public class SystemAnnouncement extends BaseNativeEntity{

	private static final long serialVersionUID = -6306983905782140325L;

	@MetaData(value="公告标题")
	@Column(name="title")
	private String title;
	
	@MetaData(value="公告内容")
	@Column(name="content")
	private String content;
	
	@MetaData(value="公告来源")
	@Column(name="source")
	private String source;
	
	@MetaData(value="公告创建时间")
	@Column(name="createdate")
	private Date createdate;
	
	@MetaData(value="公告修改时间")
	@Column(name="MODIFYDATE")
	private Date MODIFYDATE;
	
	@MetaData(value="公告接收人昵称(为空则所有人都接收)")
	@Column(name="userNick")
	private String userNick;
	
	@MetaData(value="状态('0'--未读,'1'--已读)")
	@Column(name="status")
	private String status;
}
