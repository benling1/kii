package lab.s2jh.core.mq.artifact;


import org.apache.log4j.Logger;

import com.taobao.api.internal.tmc.Message;

/**
 * 类名称：TmcJobArtifact <br/>
 * 类描述：消息实体,加入了消息内容 是有用的 <br/>
 * 创建时间：2016年04月27日 下午1:36:48 <br/>
 * @author zlp
 * @version V1.0
 */
public class TmcJobArtifact extends JobArtifact {
	
	private static final Logger log = Logger.getLogger(TmcJobArtifact.class);
	
	private static final long serialVersionUID = 2124257217233357559L;
	//保存tmc时的时间戳
	private long timestamp;
	
	private Message message ;
	

	/**
	 * @param operation
	 * @param messageType
	 */
	public TmcJobArtifact(String operation, String messageType) {
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
		String message = "messageType:"+getMessageType()+",operation:"+getOperation();
		return message;
	}
	 

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	
}
