package s2jh.biz.shop.crm.message.entity;

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
@Table(name = "CRM_SMS_SENDLIST_IMPORT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "导入发送名单")

public class SmsSendlistImport extends BaseNativeEntity{

	private static final long serialVersionUID = 1354621838687468042L;

/*	@MetaData(value="主键")
	@Column(name="id")
	private int id;*/
	
	@MetaData(value="上传的文件名称")
	@Column(name="file_name")
	private String fileName;
	
	
	@MetaData(value="上传的状态 0--导入完成   1--导入失败  2--导入中")
	@Column(name="state")
	private int state;
	
	@MetaData(value="操作用户")
	@Column(name="user_id")
	private String userId;

	@MetaData(value="上传的总电话条数")
	@Column(name="send_number")
	private int sendNumber;
	
	@MetaData(value="导入成功的电话条数")
	@Column(name="success_number")
	private int successNumber;
	
	@MetaData(value="导入失败的电话条数")
	@Column(name="error_number")
	private int errorNumber;
	
	@MetaData(value="导入时间")
	@Column(name="import_time")
	private Date importTime;
	
	@MetaData(value="导入的电话号码")
	@Column(name="import_phone")
	private String importPhone;
	
	@MetaData(value="导入的类型 1-黑名单导入")
	@Column(name="TYPE")
	private String type;
	
}
