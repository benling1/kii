package s2jh.biz.shop.crm.roaming.entity;


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
@Table(name = "CRM_roaming_message_result")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "聊天记录查询结果")
public class RoamingResult extends BaseNativeEntity {

	private static final long serialVersionUID = 4193945580264950452L;

	@MetaData(value="消息唯一ID")
	@Column(name="UUID")
    private Long uuid; 

	@MetaData(value="用户id")
	@Column(name="USER_ID")
    private Long userId; 

	@MetaData(value="是否为淘宝账号")
	@Column(name="TAOBAO_ACCOUNT")
    private Boolean taobaoAccount = Boolean.FALSE; 

	@MetaData(value="账户appkey")
	@Column(name="APP_KEY")
    private String appKey; 

	@MetaData(value="消息类型")
	@Column(name="TYPE")
    private String type; 

	@MetaData(value="值（聊天内容）")
	@Column(name="VALUE")
    private String value; 

	@MetaData(value="消息时间（UTC时间）")
	@Column(name="TIME")
    private Long time; 

	@MetaData(value="消息方向。user1 -> user2 = 0 , user2->user1 = 1")
	@Column(name="DIRECTION")
    private Integer direction; 
	
}