package s2jh.biz.shop.crm.feedback.entity;

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
@Table(name = "CRM_FEEDBACK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value ="客户反馈信息")
public class FeedBack extends BaseNativeEntity{
	private static final long serialVersionUID = 4386721832098019347L;

	@MetaData(value="卖家用户Id")
	@Column(name="USER_ID")
	private String userId;
	
	@MetaData(value="反馈内容")
	@Column(name="FEEDBACK_CONTENT")
	private String feedbackContent;
	
	@MetaData(value="反馈图片")
	@Column(name="FEEDBACK_IMAGE")
	private String feedbackImage;
	
	@MetaData(value="联系方式")
	@Column(name="CONTACT_MODE")
	private String contactMode;

	@MetaData(value="是否阅读反馈 true ,false")
	@Column(name="FEEDBACK_READ")
	private Boolean feedbackRead;
	
	@MetaData(value="创建时间")
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	@MetaData(value="最后修改时间")
	@Column(name="LAST_AMEND_DATE")
	private Date lastAmendDATE;
}
