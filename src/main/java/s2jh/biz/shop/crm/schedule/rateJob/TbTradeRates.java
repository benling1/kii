package s2jh.biz.shop.crm.schedule.rateJob;

import java.util.Date;

import javax.persistence.Column;

import lab.s2jh.core.annotation.MetaData;

public class TbTradeRates {

	private Long id;
	private String oid;
	private String tid;
	private String role;
	private String nick;
	private String result;
	private Date created;
	private String rated_nick;
	private String item_title;
	private double item_price;
	private String content;
	private String reply;
	private Long numIid;
	private boolean valid_score;
	private String rate_type;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	private String createdBy;
	
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
	public String getRated_nick() {
		return rated_nick;
	}
	public void setRated_nick(String rated_nick) {
		this.rated_nick = rated_nick;
	}
	public String getItem_title() {
		return item_title;
	}
	public void setItem_title(String item_title) {
		this.item_title = item_title;
	}
	public double getItem_price() {
		return item_price;
	}
	public void setItem_price(double item_price) {
		this.item_price = item_price;
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
	public boolean isValid_score() {
		return valid_score;
	}
	public void setValid_score(boolean valid_score) {
		this.valid_score = valid_score;
	}
	public String getRate_type() {
		return rate_type;
	}
	public void setRate_type(String rate_type) {
		this.rate_type = rate_type;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "TbTradeRates [id=" + id + ", oid=" + oid + ", tid=" + tid
				+ ", role=" + role + ", nick=" + nick + ", result=" + result
				+ ", created=" + created + ", rated_nick=" + rated_nick
				+ ", item_title=" + item_title + ", item_price=" + item_price
				+ ", content=" + content + ", reply=" + reply + ", numIid="
				+ numIid + ", valid_score=" + valid_score + ", rate_type="
				+ rate_type + ", lastModifiedBy=" + lastModifiedBy
				+ ", lastModifiedDate=" + lastModifiedDate + ", createdBy="
				+ createdBy + "]";
	}
	
}
