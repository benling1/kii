package s2jh.biz.shop.crm.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.DistributedLock;
import s2jh.biz.shop.crm.other.service.MobileSettingService;
import s2jh.biz.shop.crm.taobao.info.OperationLogStatus;
import s2jh.biz.shop.crm.taobao.service.util.RedisLock;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserAccount;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.crm.user.util.GetOperationFunctionString;

/** 
* @author wy
* @version 创建时间：2017年11月13日 下午3:04:49
*/

@Service
public class UserAccountService {
    @Autowired
    private MyBatisDao myBatisDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MobileSettingService mobileSettingService;
    @Autowired
    private UserOperationLogDao userOperationLogDao;
    @Autowired
    private DistributedLock distributedLock;
    @Resource(name = "redisTemplateLock")
    private StringRedisTemplate redisTemplate;
    
    public static final boolean TIME_OUT = true;
    
    public static final boolean NO_TIME = false;
    
    private  Logger logger = LoggerFactory.getLogger(UserAccountService.class);
    /**
     * 创建用户账户信息
     * @author: wy
     * @time: 2017年11月13日 下午3:18:51
     * @param sellerNick 卖家昵称，不可为空
     * @param smsNum 短信余额，如果为空默认为0
     * @return 如果创建成功返回true，失败返回false
     */
    public boolean doCreateUserAccountByUser(String sellerNick,Long smsNum){
        if(ValidateUtil.isEmpty(sellerNick)){
            return false;
        }
        Long id = this.userInfoService.getUserInfoId(sellerNick);
        if(id==null){
            return false;
        }
        if(smsNum==null){
            smsNum = 0L;
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(sellerNick);
        userAccount.setSmsNum(smsNum);
        userAccount.setId(id);
        int result = this.myBatisDao.execute(UserAccount.class.getName(), "doCreateUserAccount", userAccount);
        if(result==1){
            return true;
        }
        return false;
    }
    /**
     * 创建用户账户信息
     * @author: wy
     * @time: 2017年11月13日 下午3:18:51
     * @param sellerNick 卖家昵称，不可为空
     * @param smsNum 短信余额，如果为空默认为0
     * @return 如果创建成功返回true，失败返回false
     */
    public boolean doCreateUserAccountByUser(String sellerNick,Integer smsNum){
        if(smsNum==null){
            smsNum = 0;
        }
        return this.doCreateUserAccountByUser(sellerNick, (long)smsNum);
    }
    /**
     * 查询用户余额
     * @author: wy
     * @time: 2017年11月13日 下午3:23:05
     * @param sellerNick
     * @return
     */
    public Long findUserAccountSms(String sellerNick){
        long  smsNum =  this.myBatisDao.findBy(UserAccount.class.getName(), "findSms", sellerNick);
        return smsNum;
    }
    /**
     * 修改用户短信余额
     * @author: wy
     * @time: 2017年11月13日 下午3:23:05
     * @param userId 用户的昵称
     * @param isDelete  true删除短信，false增加短信
     * @param smsNum  更改的短信数量（正整数）
     * @param settingType 操作的类型（下单关怀，常规催付）
     * @param operator 操作人（用户自己 填昵称，系统自动发送填 auto）
     * @param ipAdd 地址ip，可为空
     * @param remark 备注，可为空  空代表发送单条短信
     * @param isTimeOut true:有超时设置，false没超时设置
     * @return 返回boolean 用户短信余额是否修改成功 true->成功,false->失败
     */
    public boolean doUpdateUserSms(String userId,Boolean isDelete,int smsNum,
            String settingType,String operator,String ipAdd,String remark,boolean isTimeOut){
        long startTime = System.currentTimeMillis();
        boolean flag = false;
        if(ValidateUtil.isEmpty(userId) ||isDelete==null ||ValidateUtil.isEmpty(smsNum)
                ||ValidateUtil.isEmpty(settingType) ||ValidateUtil.isEmpty(operator)){
            return flag;
        }
        UserOperationLog userLog = new UserOperationLog();
        userLog.setFunctionGens(settingType);
        String type = isDelete?OperationLogStatus.DEL_MESSEGE:OperationLogStatus.ADD_MESSEGE;
        userLog.setType(type);
        userLog.setOperator(operator);
        userLog.setUserId(userId);
        userLog.setFunctions(GetOperationFunctionString.getFunctionsByType(settingType));
        userLog.setDate(new Date());
        userLog.setIpAdd(ipAdd);
        userLog.setRemark(remark==null?OperationLogStatus.USER_OPERATION_SINGLE:remark);
        try {
            if(isTimeOut){
                flag = this.synUpdateUserSmsTimeOut(userId, isDelete, smsNum);
            }else{
                flag = this.synUpdateUserSmsNoTime(userId, isDelete, smsNum);
            }
        }catch (Exception e) {
            this.logger.info("用户："+userId+"扣除短信："+smsNum +"，代码错误："+e.getMessage());
            e.printStackTrace();
        } 
        finally {
            userLog.setState(flag?OperationLogStatus.USER_OPERATION_LOG_SUCCESS:OperationLogStatus.USER_OPERATION_LOG_FAIL);
            this.userOperationLogDao.save(userLog);
        }
        if(flag){
            //用户余额如果为0，则取消用户的授权
            userInfoService.doTmcUser(userId,isDelete);
            //异步调用,余额提醒重置
            mobileSettingService.proxyResetSmsRemindMark(userId);
        }
        this.logger.info("用户："+userId+"扣除短信："+smsNum+"花费了"+(System.currentTimeMillis()-startTime)+"ms");
        return flag;
    }
    /**
     * 改变用户的短信条数,超时五秒就默认为本次扣费失败
     * @param userId  用户条数
     * @param isDelete  true删除短信，false增加短信
     * @param smsNum  更改的短信数量（正整数）
     * @return 扣成功返回true,失败返回false
     */
    private  boolean synUpdateUserSmsTimeOut(String userId,boolean isDelete,int smsNum){
        long startTime = System.currentTimeMillis();
        RedisLock lock = new RedisLock(this.redisTemplate,userId+"_user");
        try {
            if(lock.lock()){
                Map<String,Object> map = new HashMap<String,Object>(5);
                map.put("sellerName", userId);
                map.put("smsNum", smsNum);
                try {
                    //扣短信
                    if(isDelete){
                        long userSmsCount = this.findUserAccountSms(userId);
                        if(smsNum>userSmsCount){
                            return false;
                        }
                        this.myBatisDao.execute(UserAccount.class.getName(), "doDeleteUserSms", map);
                        return true;
                    }
                    //增加短信
                    else {
                        this.myBatisDao.execute(UserAccount.class.getName(), "doAddUserSms", map);
                        return true;
                    }
                } catch (Exception e) {
                    this.logger.error("严重，警急！！！用户扣费错误。卖家昵称："+userId+"短信数量："+smsNum+"是否删除短信："+isDelete +"  错误："+e.getMessage()) ;
                    e.printStackTrace();
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
            long endTime = System.currentTimeMillis();
            if((endTime-startTime)>4000){
                this.logger.debug("用户扣费超时！！！ "+(endTime-startTime)+"ms  ," + userId);
            }
        }
        return false;
    }
    /**
     * 改变用户的短信条数，没有超时时间，直到数据库返回处理结果
     * @param userId  用户条数
     * @param isDelete  true删除短信，false增加短信
     * @param smsNum  更改的短信数量（正整数）
     * @return 扣成功返回true,失败返回false
     */
    private  boolean synUpdateUserSmsNoTime(String userId,boolean isDelete,int smsNum){
        long startTime = System.currentTimeMillis();
        Map<String,Object> map = new HashMap<String,Object>(5);
        map.put("sellerName", userId);
        map.put("smsNum", smsNum);
        try {
            if(this.distributedLock.tryLock(userId+"_user")){
                //扣短信
                if(isDelete){
                    long userSmsCount = this.findUserAccountSms(userId);
                    if(smsNum>userSmsCount){
                        return false;
                    }
                    this.myBatisDao.execute(UserAccount.class.getName(), "doDeleteUserSms", map);
                    return true;
                }
                //增加短信
                else {
                    this.myBatisDao.execute(UserAccount.class.getName(), "doAddUserSms", map);
                    return true;
                }
            }else{
                this.logger.debug("用户扣费竞争锁失败！ " + userId);
            }
        } catch (Exception e) {
            this.logger.error("严重，警急！！！用户扣费错误。卖家昵称："+userId+"短信数量："+smsNum+"是否删除短信："+isDelete +"  错误："+e.getMessage()) ;
            e.printStackTrace();
            return false;
        }finally{
            this.distributedLock.unLock(userId+"_user");
            long endTime = System.currentTimeMillis();
            if((endTime-startTime)>4000){
                this.logger.debug("用户扣费超时！！！ "+(endTime-startTime)+"ms  ," + userId);
            }
        }
        return false;
    }
    /**
     * 查询设置是否存在
     * @author: wy
     * @time: 2017年11月13日 下午4:43:06
     * @param userId 卖家昵称
     * @return 存在返回true，不存在返回false
     */
    public boolean findExistsUserAccount(String userId){
        int reuslt = this.myBatisDao.findBy(UserAccount.class.getName(), "findExistsUser", userId);
        if(reuslt==1){
            return true;
        }
        return false;
    }

    /**
     * 查询短信条数不为0的客户
     * ztk2017年11月29日下午2:56:44
     */
    public List<String> findSmsNumNotEmpty(){
    	List<String> userList = this.myBatisDao.findList(UserAccount.class.getName(), "findSmsNumNotEmpty", null);
    	return userList;
    }
}
