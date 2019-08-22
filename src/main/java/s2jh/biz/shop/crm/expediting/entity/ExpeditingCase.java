package s2jh.biz.shop.crm.expediting.entity;

import java.math.BigDecimal;


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
@Table(name = "CRM_EXPEDITING_CASE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "优秀催付案例")
public class ExpeditingCase extends BaseNativeEntity {

    private static final long serialVersionUID = 618519386120361220L;	   

    @MetaData(value="店铺名称")
	@Column(name="STORE_NAME")
    private String storeName; 

    @MetaData(value="催付挽回订单数")
	@Column(name="RESTORE_ORDER")
    private Integer restoreOrder; 

    @MetaData(value="催付挽回金额")
	@Column(name="TO_SAVE_MONEY")
    private BigDecimal toSaveMoney; 

    @MetaData(value="催付成功率")
	@Column(name="CF_SUCCESS_RATIO")
    private String cfSuccessRatio; 

    @MetaData(value="短信文案")
	@Column(name="SMS_CONTENT")
    private String smsContent; 

    @MetaData(value="短信链点击次数")
	@Column(name="SMS_CLICK_TIMES")
    private Integer smsClickTimes; 

    @MetaData(value="所属行业")
	@Column(name="OWNED_INDUSTRY")
    private String ownedIndustry; 
	
}