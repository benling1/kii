package s2jh.biz.shop.crm.message.service;

import java.util.HashSet;
import java.util.Set;

import s2jh.biz.shop.utils.SpringContextUtil;

public class ImportPhoneThread extends Thread{
	
	 private Set<String> phoneSet = new HashSet<String>();
	 
	 private Long importId;
	 

	public ImportPhoneThread(Set<String> phoneSet, Long importId) {
		super();
		this.phoneSet = phoneSet;
		this.importId = importId;
	}

	public Set<String> getPhoneSet() {
		return phoneSet;
	}

	public void setPhoneSet(Set<String> phoneSet) {
		this.phoneSet = phoneSet;
	}

	public Long getImportId() {
		return importId;
	}

	public void setImportId(Long importId) {
		this.importId = importId;
	}
	 
	@Override
	public void run() {
		ImportPhoneThreadService importPhoneThreadService= SpringContextUtil.getBean("importPhoneThreadService");
		importPhoneThreadService.importPhoneThread(phoneSet, importId);
	}
	 

}
