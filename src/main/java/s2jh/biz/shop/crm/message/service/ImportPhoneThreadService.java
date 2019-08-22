package s2jh.biz.shop.crm.message.service;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lab.s2jh.core.dao.mybatis.MyBatisDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.message.entity.SmsSendlistImport;
@Service
public class ImportPhoneThreadService{
	
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	/**
	 * 使用线程处理电话号码
	 */
	public void importPhoneThread(Set<String> phoneSet, Long importId) {
		// 接收成功上传的电话号码
		String importPhone = "";
		// 导入成功的电话条数
		int successNumber = 0;
		// 导入失败的电话条数
		int errorNumber = 0;
		
		//定义变量循环次数
		int i = 0;
		for (String phone : phoneSet) {
			i++;
			if (this.checkoutPhone(phone)) {
				importPhone += phone + ",";
				successNumber++;
			} else {
				errorNumber++;
			}
			if(i % 100==0 && i !=0){	
				this.updateSmsSendlistImportById(importId,successNumber,errorNumber);
				i = 0;successNumber = 0;errorNumber = 0;
			}
		}
		if (importPhone != "") {
			importPhone = importPhone.substring(0, importPhone.length() - 1);
		}
		this.updateSmsSendlistImportToState(importId,successNumber,errorNumber,importPhone);
		
	}
	
	/**
	 * 通过id修改导入数据变量
	 * @param id
	 */
	public void updateSmsSendlistImportById(Long id,int successNumber, int errorNumber ){
		SmsSendlistImport smsSendlistImport = new SmsSendlistImport();
		smsSendlistImport.setId(id);
		smsSendlistImport.setSuccessNumber(successNumber);
		smsSendlistImport.setErrorNumber(errorNumber);
		myBatisDao.execute(SmsSendlistImport.class.getName(), "updateSmsSendlistImportById", smsSendlistImport);
	}
	
	/**
	 * 通过id将所以的数据补全和状态改变
	 */
	private void updateSmsSendlistImportToState(Long importId,
			int successNumber, int errorNumber, String importPhone) {
		SmsSendlistImport smsSendlistImport = new SmsSendlistImport();
		smsSendlistImport.setId(importId);
		smsSendlistImport.setSuccessNumber(successNumber);
		smsSendlistImport.setErrorNumber(errorNumber);
		smsSendlistImport.setImportPhone(importPhone);
		myBatisDao.execute(SmsSendlistImport.class.getName(), "updateSmsSendlistImportById", smsSendlistImport);
		myBatisDao.execute(SmsSendlistImport.class.getName(), "updateSmsSendlistImportToStateOne", importId);
		myBatisDao.execute(SmsSendlistImport.class.getName(), "updateSmsSendlistImportToStateTwo", importId);
	}

	
	/**
	 * 手机号验证 helei 2017年3月7日上午11:05:26
	 */
	private boolean checkoutPhone(String mobiles) {
		Pattern p = Pattern.compile("^1(3|4|5|7|8)\\d{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

}
