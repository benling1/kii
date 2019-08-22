package s2jh.biz.shop.crm.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;

/** 
* @author wy
* @version 创建时间：2017年11月13日 下午2:40:49
* 用户短信余额表
*/
@Getter
@Setter
@Entity
@Table(name = "CRM_USER_ACCOUNT")
@MetaData(value = "用户账户表")
public class UserAccount extends BaseNativeEntity{
    
    private static final long serialVersionUID = -6963381365184544552L;

    @MetaData(value = "淘宝账户对应Id")
    @Column(name = "user_id")
    private String userId;
    
    @MetaData(value = "短信剩余数量")
    @Column(name = "sms_num")
    private Long smsNum;
    
}
