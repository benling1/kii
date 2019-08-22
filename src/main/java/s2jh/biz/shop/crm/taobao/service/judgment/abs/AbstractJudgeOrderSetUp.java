package s2jh.biz.shop.crm.taobao.service.judgment.abs;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
@Deprecated 
public abstract class AbstractJudgeOrderSetUp {
	@Autowired
	protected MyBatisDao myBatisDao;
	
	protected AbstractJudgeOrderSetUp nextJudgeOrderSetUp;
	/**
	 * 处理请求的方法
	 */
	public abstract Map<String,Object> startJob(Map<String,Object> map);
	/** 
     * 取值方法 
     */  
	public AbstractJudgeOrderSetUp getNext(){
		return this.nextJudgeOrderSetUp;
	}
	 /** 
     * 赋值方法，设置后继的责任对象 
     */  
    public void setNext(AbstractJudgeOrderSetUp nextJudgeOrderSetUp) {  
        this.nextJudgeOrderSetUp = nextJudgeOrderSetUp;  
    }  
}
