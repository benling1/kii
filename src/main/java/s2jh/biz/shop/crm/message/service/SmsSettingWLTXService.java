package s2jh.biz.shop.crm.message.service;

import java.util.HashMap;
import java.util.Map;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.message.dao.SmsSettingDao;
import s2jh.biz.shop.crm.message.entity.SmsSetting;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;

@Service
@Transactional
public class SmsSettingWLTXService extends BaseService<SmsSetting, Long>{
	
	@Autowired
	private SmsSettingDao smsSettingDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	@Override
	protected BaseDao<SmsSetting, Long> getEntityDao() {
		return smsSettingDao;
	}

	/**
	* @Title: saveSmsSetting
	* @Description: TODO(保存或者修改短信发送设置)
	* @param @param smsSetting    参数
	* @return void    返回类型
	* @author:jackstraw_yu
	* @throws
	*/
	public void saveSmsSetting(SmsSetting smsSetting,UserOperationLog op) {
		//添加操作日志
		//UserOperationLog op = new UserOperationLog();
		//判断操作类型
		if("6".equals(smsSetting.getSettingType())){
			op.setRemark("物流提醒短信设置");
			op.setFunctions("物流提醒短信设置");
		}
		if("7".equals(smsSetting.getSettingType())){
			op.setRemark("到达同城提醒短信设置");
			op.setFunctions("到达同城提醒短信设置");
		}
		if("8".equals(smsSetting.getSettingType())){
			op.setRemark("派件提醒短信设置");
			op.setFunctions("派件提醒短信设置");
		}
				
		
		if(smsSetting.getId()==null){
			try {
				smsSettingDao.save(smsSetting);
				op.setType("添加");
				//操作日志数据补全
				op.setState("成功");
			} catch (Exception e) {
				// TODO: handle exception
				op.setState("失败");
			}
			
		}else{
			Map<String,Object> map = new HashMap<String, Object>();
			//测试数据
			map.put("messageContent", smsSetting.getMessageContent());
			map.put("id", smsSetting.getId());
			/*map.put("settingType", smsSetting.getSettingType());
			map.put("userId", smsSetting.getUserId());*/
			try {
				myBatisDao.execute(SmsSetting.class.getName(), "updateSmsSettingById", map);
				op.setType("修改");
				//操作日志数据补全
				op.setState("成功");
			} catch (Exception e) {
				// TODO: handle exception
				op.setState("失败");
			}
			
		}
		
		userOperationLogDao.save(op);
		
	}
	
	

}
