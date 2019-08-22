package s2jh.biz.shop.crm.order.entity;

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
@Table(name = "crm_trade_rates")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "评价列表")
public class TradeRates extends BaseNativeEntity{

	private static final long serialVersionUID = 7323864663934535233L;
	
	@MetaData(value="子订单ID")
	@Column(name="oid",nullable=false)
	private String oid;
	
	@MetaData(value="交易ID")
	@Column(name="tid",nullable=false)
	private String tid;
	
	@MetaData(value="评价者角色.可选值:seller(卖家),buyer(买家)")
	@Column(name="role")
	private String role;
	
	@MetaData(value="评价者昵称")
	@Column(name="nick")
	private String nick;
	
	@MetaData(value="评价结果,可选值:good(好评),neutral(中评),bad(差评),(已删中差评)iSDelete")
	@Column(name="result")
	private String result;
	
	@MetaData(value="评价创建时间,格式:yyyy-MM-dd HH:mm:ss")
	@Column(name="created")
	private Date created;
	
	@MetaData(value="被评价者昵称")
	@Column(name="rated_nick")
	private String ratedNick;
	
	@MetaData(value="商品标题")
	@Column(name="item_title")
	private String itemTitle;
	
	@MetaData(value="商品价格,精确到2位小数;单位:元.如:200.07，表示:200元7分")
	@Column(name="item_price")
	private Double itemPrice;
	
	@MetaData(value="评价内容,最大长度:500个汉字")
	@Column(name="content")
	private String content;
	
	@MetaData(value="评价解释,最大长度:500个汉字")
	@Column(name="reply")
	private String reply;
	
	@MetaData(value="商品的数字ID")
	@Column(name="num_iid")
	private Long numIid;
	
	@MetaData(value="评价信息是否用于记分， 可取值：true(参与记分)和false(不参与记分)")
	@Column(name="valid_score")
	private Boolean validScore;
	
	@MetaData(value="评价方式  自动评价和批量评价")
	@Column(name="rate_type")
	private String rateType;
	
}
