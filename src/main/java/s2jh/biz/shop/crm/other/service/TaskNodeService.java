package s2jh.biz.shop.crm.other.service;

import javax.transaction.Transactional;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.other.dao.TaskNodeDao;
import s2jh.biz.shop.crm.other.entity.TaskNode;


@Service
@Transactional
public class TaskNodeService extends BaseService<TaskNode, Long>{
	@Autowired
	private TaskNodeDao taskNodeDao;
	
	@Autowired
	private MyBatisDao mybatisDao;
	
	@Override
	protected BaseDao<TaskNode, Long> getEntityDao() {
		return taskNodeDao;
	}
	
	/**
	 * 创建人：邱洋
	 * @title 根据类型查询数据
	 * @date 2017-06-13 11:23:05
	 * @param type
	 * @return
	 */
	public TaskNode getTaskNode(String type){
		TaskNode ta = mybatisDao.findBy(TaskNode.class.getName(), "findTaskNodeOne", type);
		return ta;
	}
	
	/**
	 * 创建人：邱洋
	 * @title 更新taskNode表中的数据
	 * @date 2017-06-13 11:30:05
	 * @param TaskNode
	 */
	public void updateTaskNode(TaskNode tn){
		mybatisDao.execute(TaskNode.class.getName(), "updateTaskNode", tn);
	}
}
