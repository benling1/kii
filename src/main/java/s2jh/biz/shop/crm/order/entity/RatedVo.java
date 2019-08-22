package s2jh.biz.shop.crm.order.entity;


import java.util.Date;

import lab.s2jh.core.entity.BaseNativeEntity;


public class RatedVo extends BaseNativeEntity{


	private Integer neutral;
	
	private Integer bad;
	
	private Integer volume;
	
	private Date created;
	
	private String nick;
	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getNeutral() {
		return neutral;
	}

	public void setNeutral(Integer neutral) {
		this.neutral = neutral;
	}

	public Integer getBad() {
		return bad;
	}

	public void setBad(Integer bad) {
		this.bad = bad;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public RatedVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
