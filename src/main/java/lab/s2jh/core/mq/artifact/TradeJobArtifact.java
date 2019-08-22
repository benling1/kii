package lab.s2jh.core.mq.artifact;


import java.util.List;

import s2jh.biz.shop.crm.order.pojo.TbTransactionOrder;

/**
 * 类名称：TradeJobArtifact <br/>
 * 类描述：消息实体,加入了消息内容 是有用的 <br/>
 * 创建时间：2016年04月27日 下午1:36:48 <br/>
 * @author zlp
 * @version V1.0
 */
public class TradeJobArtifact extends JobArtifact {
	
//	private static final Logger log = Logger.getLogger(TradeJobArtifact.class);
	
	
	private static final long serialVersionUID = 2124257217233357559L;
	//保存时的时间戳
	private long timestamp;
	
	private List<TbTransactionOrder> message;
	
	

	/**
	 * @param operation
	 * @param messageType
	 */
	public TradeJobArtifact(String operation, String messageType) {
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

	public List<TbTransactionOrder> getMessage() {
		return message;
	}

	public void setMessage(List<TbTransactionOrder> message) {
		this.message = message;
	}
	
}
