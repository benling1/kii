package s2jh.biz.shop.crm.manage.entity;

import java.io.Serializable;
import java.util.Date;



import lab.s2jh.core.annotation.MetaData;

public class TradeRateDTO extends BaseMongoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@MetaData(value="子订单ID")
	private String oid;
	
	@MetaData(value="交易ID")
	private String tid;
	
	@MetaData(value="评价者角色.可选值:seller(卖家),buyer(买家)")
	private String role;
	
	@MetaData(value="评价者昵称")
	private String nick;
	
	@MetaData(value="评价结果,可选值:good(好评),neutral(中评),bad(差评),(已删中差评)iSDelete")
	private String result;
	
	@MetaData(value="评价创建时间,格式:yyyy-MM-dd HH:mm:ss")
	private Date created;
	
	@MetaData(value="被评价者昵称")
	private String ratedNick;
	
	@MetaData(value="商品标题")
	private String itemTitle;
	
	@MetaData(value="商品价格,精确到2位小数;单位:元.如:200.07，表示:200元7分")
	private Double itemPrice;
	
	@MetaData(value="评价内容,最大长度:500个汉字")
	private String content;
	
	@MetaData(value="评价解释,最大长度:500个汉字")
	private String reply;
	
	@MetaData(value="商品的数字ID")
	private Long numIid;
	
	@MetaData(value="评价信息是否用于记分， 可取值：true(参与记分)和false(不参与记分)")
	private Boolean validScore;
	
	@MetaData(value="评价方式  自动评价和批量评价")
	private String rateType;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getRatedNick() {
		return ratedNick;
	}

	public void setRatedNick(String ratedNick) {
		this.ratedNick = ratedNick;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public Boolean getValidScore() {
		return validScore;
	}

	public void setValidScore(Boolean validScore) {
		this.validScore = validScore;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	
}
