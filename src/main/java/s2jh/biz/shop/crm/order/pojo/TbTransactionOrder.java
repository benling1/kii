package s2jh.biz.shop.crm.order.pojo;

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
@Table(name = "jdp_tb_trade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "淘宝-主交易订单信息")
public class TbTransactionOrder extends BaseNativeEntity{

	private static final long serialVersionUID = 4498366225784875392L;
	
	@MetaData(value="交易编号")
	@Column(name="tid")
	private Long tid;
	
	@MetaData(value="状态")
	@Column(name="status")
	private String status;
	
	@MetaData(value="类型")
	@Column(name="type")
	private String type;
	
	@MetaData(value="卖家昵称")
	@Column(name="seller_nick")
	private String sellerNick;
	
	@MetaData(value="买家昵称")
	@Column(name="buyer_nick")
	private String buyerNick;
	
	@MetaData(value="创建时间")
	@Column(name="created")
	private Date created;
	
	@MetaData(value="修改时间")
	@Column(name="modified")
	private Date modified;
	
	@MetaData(value="哈希值")
	@Column(name="jdp_hashcode")
	private String jdpHashcode;      
	
	@MetaData(value="淘宝回复内容")
	@Column(name="jdp_response",length = 16777216)
	private String jdpResponse;   
	         
	@MetaData(value="淘宝修改时间")
	@Column(name="jdp_created")
	private Date jdpCreated;
	
	@MetaData(value="淘宝修改时间")
	@Column(name="jdp_modified")
	private Date jdpModified;
	         
	         


}
