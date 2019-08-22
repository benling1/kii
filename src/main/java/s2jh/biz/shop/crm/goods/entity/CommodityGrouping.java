package s2jh.biz.shop.crm.goods.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.web.json.JsonViews;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "crm_Commodity_Grouping")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "商品分组")
public class CommodityGrouping /*extends BaseNativeEntity*/ {

	private static final long serialVersionUID = 8322917664138285369L;
	
	@MetaData(value = "乐观锁版本")
    @Version
    @Column(name = "optlock", nullable = false,columnDefinition="INT default 0")
    @JsonProperty
    @JsonView(JsonViews.Admin.class)
    private Integer version = 0;

    @Column(length = 100)
    @JsonProperty
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    protected Date createdDate;

    @Column(length = 100)
    @JsonProperty
    private String lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    private Date lastModifiedDate;
	
    @MetaData("主键")
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "native")
    @JsonProperty
    private Long id;
    
    
    

	@MetaData(value="卖家ID")
	@Column(name="USERID")
	private String userId;
	
	@MetaData(value="分组名称")
	@Column(name="GROUP_NAME")
	private String groupName;
	
	@MetaData(value="商品数量")
	@Column(name="COMMODITY_NUM")
	private Integer commodityNum;
	
	@MetaData(value="备注")
	@Column(name="REMARK")
	private String remark;
	
	@MetaData(value="创建时间")
	@Column(name="CTIME")
	private Date cTime;
	
	@MetaData(value="修改时间")
	@Column(name="MODIFY_TIME")
	private Date modifyTime;
}
