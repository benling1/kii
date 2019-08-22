package s2jh.biz.shop.crm.other.dao;

import lab.s2jh.core.dao.jpa.BaseDao;

import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.other.entity.Notice;

@ResponseBody
public interface NoticeDao extends BaseDao<Notice, Long>{

}
