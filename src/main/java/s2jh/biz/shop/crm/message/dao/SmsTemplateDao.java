package s2jh.biz.shop.crm.message.dao;

import lab.s2jh.core.dao.jpa.BaseDao;

import org.springframework.stereotype.Repository;

import s2jh.biz.shop.crm.message.entity.SmsTemplate;

@Repository
public interface SmsTemplateDao extends BaseDao<SmsTemplate, Long> {

}
