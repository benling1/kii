package lab.s2jh.core.mq.artifact;


import java.util.List;

/**
 * 类名称：SmsJobArtifact <br/>
 * 类描述：消息实体,加入了消息内容 是有用的 <br/>
 * 创建时间：2016年03月08日 下午1:36:48 <br/>
 * @author zlp
 * @version V1.0
 */
public class SmsJobArtifact extends JobArtifact {
	
//	private static final Logger log = Logger.getLogger(SmsJobArtifact.class);
	
	private static final long serialVersionUID = 2124257217233357559L;
	//项目模板
	//private SmsTemplate smsTemplate;
	//private SmsMessageBM smsMessageBM;
	//保存短信时的时间戳
	private long timestamp;
	
	private String message ;
	
	private List<String> phones;
	
	private String type;     //  短信类型
	private String userId;   //  用户id
	private String tid; // tradeId
	private Long msgId;
	private String  status;
	private String channel ; //  渠道类型
	private Integer actualDeduction ;// 条数
	private String autograph;// 签名
	private String ip;// 签名

	/**
	 * @param operation
	 * @param messageType
	 */
	public SmsJobArtifact(String operation, String messageType) {
		super(operation, messageType);
	}

	public long getTimestamp() {
		return timestamp;
	}

	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		String sms = "messageType:"+getMessageType()+",operation:"+getOperation();
		return sms;
	}


	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getActualDeduction() {
		return actualDeduction;
	}

	public void setActualDeduction(Integer actualDeduction) {
		this.actualDeduction = actualDeduction;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
