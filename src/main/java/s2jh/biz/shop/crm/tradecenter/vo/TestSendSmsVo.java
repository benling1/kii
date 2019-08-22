package s2jh.biz.shop.crm.tradecenter.vo;

import java.io.Serializable;

import lab.s2jh.core.annotation.MetaData;

/**	
* @Title: TestSendSmsVo
* @Description: (测试短信发送vo类)
*/
public class TestSendSmsVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@MetaData(value="短信内容")
	private String content;
	
	@MetaData(value="短信接收号码")
	private String phone;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
