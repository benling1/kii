package s2jh.biz.shop.crm.message.templateVo;

import java.io.Serializable;
import java.util.List;

import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsTemplateVo implements Serializable{

	/**
	 * 短信模板vo
	 */
	private static final long serialVersionUID = -3576103944491803134L;
	
	/*模板类型*/
	private String type;
	
	/*模板名称*/
	private String name;
	
	/*模板内容*/
	private String content;
	
	/*用户Id*/
	private String userId;
	
	/*短语库ID*/
	private Long id;
	
	/*页面展示类型 系统短语库  自定义短语库*/
	private String queryType;
	
	/*系统短语库展示list*/
	private List<SmsTemplate> smsTemplate;
}
