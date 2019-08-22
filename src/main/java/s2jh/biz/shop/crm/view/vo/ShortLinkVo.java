package s2jh.biz.shop.crm.view.vo;

import java.util.Date;

/** 
* @author wy
* @version 创建时间：2017年10月26日 下午3:57:58
*/
public class ShortLinkVo {
    /**
     * 订单号
     */
    private String tid;
    
    /**
     * 订单金额
     */
    private String payment;
    
    /**
     * 买家手机号码
     */
    private String buyerMobile;
    
    /**
     * 买家昵称
     */
    private String buyerNick;
    
    /**
     * 点击时间
     */
    private Date clickTime;
    
    private String clickTimeStr;
    
    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

	public Date getClickTime() {
		return clickTime;
	}

	public void setClickTime(Date clickTime) {
		this.clickTime = clickTime;
	}

	public String getClickTimeStr() {
		return clickTimeStr;
	}

	public void setClickTimeStr(String clickTimeStr) {
		this.clickTimeStr = clickTimeStr;
	}
    

    
}
