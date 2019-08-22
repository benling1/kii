package lab.s2jh.core.mq.artifact;

/**
 * 类名称:JobArtifact <br/>
 * 类描述:All object to be sent to  queue must implement this interface <br/>
 * 创建时间:2017年03月07日 下午4:04:37 <br/>
 * @author zlp
 * @version V1.0
 */
public abstract class JobArtifact implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String operation;
	private String messageType;
	
	
	
	private int operateTimes=0;//操作次数
	
	
	public JobArtifact(String operation, String messageType) {
		super();
		this.operation = operation;
		this.messageType = messageType;
	}



	/**
	 * a descriptive name describing this type of operation.
	 * @return
	 */
	public String getOperation() {
		return operation;
	}



	public void setOperation(String operation) {
		this.operation = operation;
	}


	/**
	 * a descriptive name describing this type of message.
	 * @return
	 */
	public String getMessageType() {
		return messageType;
	}



	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}	

	/**
	 * @return the operateTimes
	 */
	public int getOperateTimes() {
		return this.operateTimes;
	}



	/**
	 * @param operateTimes the operateTimes to set
	 */
	public void setOperateTimes(int operateTimes) {
		this.operateTimes = operateTimes;
	}
	
	public void operateTimeAdded(){
		operateTimes++;
	}



	

	/**
	 * override to provide a meaningful text for storing into db 
	 * whenever an exception occurs.
	 */
	public abstract String toString();

}
