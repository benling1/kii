package s2jh.biz.shop.crm.item.pojo;


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
@Table(name = "jdp_tb_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "新建商品信息")
public class TbItem extends BaseNativeEntity{

	private static final long serialVersionUID = -5507052077351738568L;
	
	@MetaData(value = "商品数字id")
	@Column(name = "num_iid")
	private Long num_iid;
	
	@MetaData(value="卖家昵称")
	@Column(name="nick")
	private String nick;
	
	@MetaData(value="商品标题,不能超过60字节")
	@Column(name="title")
	private String title;
	
	@MetaData(value="商品上传后的状态。onsale出售中，instock库中")
	@Column(name="approve_status")
	private String approvestatus;
	
	@MetaData(value="橱窗推荐,true/false")
	@Column(name="has_showcase")
	private String hasshowcase;
	
	@MetaData(value="发布时间")
	@Column(name="created")
	private Date created;
	
	@MetaData(value="商品修改时间")
    @Column(name="modified")
	private Date modified;
	
	@MetaData(value="商品类目ID")
	@Column(name="cid")
	private String cid;
	
	@MetaData(value="支持会员打折,true/false")
	@Column(name="has_discount")
	private String hasdiscount;
	
	@MetaData(value="hash代码")
	@Column(name="jdp_hashcode")
	private String jdphashcode;
	
	@MetaData(value="")
	@Column(name="jdp_response")
	private String jdpresponse;
	
	@MetaData(value="")
	@Column(name="jdp_delete")
	private Integer jdpdelete;
	
	@MetaData(value="")
	@Column(name="jdp_created")
	private Date jdpcreated;
	
	@MetaData(value="")
	@Column(name="jdp_modified")
	private Date jdpmodified;
}
