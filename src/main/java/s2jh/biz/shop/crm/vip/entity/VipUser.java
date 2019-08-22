package s2jh.biz.shop.crm.vip.entity;

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
@Table(name = "CRM_VIP_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "vip用户免审表")
public class VipUser extends BaseNativeEntity {
	private static final long serialVersionUID = 1L;
	
	@MetaData(value = "vip用户名称")
	@Column(name = "VIP_USER_NAME")
	private String vipUserNick;
	
	@MetaData(value="vip录入用户ID")
	@Column(name="USER_ID")
	private String userId;
}
