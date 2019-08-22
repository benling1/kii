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
@Table(name = "CRM_MSGRECORD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "短信群发总记录")

public class MsgSendRecord extends BaseNativeEntity{
	
	private static final long serialVersionUID = 7286487347938768845L;
	
	@MetaData(value ="卖家昵称")
	@Column(name="user_Id")
	private String userId;
	
	@MetaData(value ="短信批量发送总数")
	@Column(name="total_count")
	private Integer totalCount;
	
	@MetaData(value ="短信批量发送成功总数")
	@Column(name="succeed_count")
	private Integer succeedCount;
	
	@MetaData(value ="短信批量发送失败总数/调用接口失败")
	@Column(name="failed_count")
	private Integer failedCount;
	
	@MetaData(value ="短信批量发送手机号错误总数")
	@Column(name="wrong_count")
	private Integer wrongCount;
	
	@MetaData(value ="短信批量发送手机号重复总数")
	@Column(name="repeat_count")
	private Integer repeatCount;
	
	@MetaData(value ="短信批量发送黑名单总数")
	@Column(name="black_count")
	private Integer blackCount;
	
	@MetaData(value ="短信批量发送被屏蔽总数")
	@Column(name="sheild_count")
	private Integer sheildCount;
	
	@MetaData(value ="状态--1:全部成功/2:全部失败/3:部分成功/4:发送中/5:发送完成(该字段作为识别字段)")
	@Column(name="status")
	private String status;
	
	@MetaData(value ="短信基础/模板内容")
	@Column(name="template_content")
	private String templateContent;
	
	@MetaData(value ="活动名称")
	@Column(name="activity_name")
	private String activityName;
	
	@MetaData(value ="创建时间")
	@Column(name="send_creat")
	private Date sendCreat;
	
	@MetaData(value ="短信类型")
	@Column(name="type")
	private String type;
	
	
	@MetaData(value ="是否删除(显示或者隐藏)--true:显示 /false:不显示  默认保存true")
	@Column(name="is_show")
	private Boolean isShow;
	
	@MetaData(value ="是否已发送--true:已发送/false:未发送,未发送说明是定时任务的总记录待发送")
	@Column(name="is_sent")
	private Boolean isSent;

}
