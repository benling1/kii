package s2jh.biz.shop.crm.view.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.domain.Trade;

import lab.s2jh.core.util.DateUtils;
import s2jh.biz.shop.crm.manage.entity.LogAccessDTO;
import s2jh.biz.shop.crm.manage.entity.LogType;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.manage.service.LogAccessQueueService;
import s2jh.biz.shop.crm.manage.service.TradeInfoService;
import s2jh.biz.shop.crm.message.entity.SmsSendInfo;
import s2jh.biz.shop.crm.order.service.TransactionOrderService;
import s2jh.biz.shop.crm.taobao.service.util.JudgeUserUtil;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;
import s2jh.biz.shop.crm.taobao.util.TaoBaoClientUtil;
import s2jh.biz.shop.crm.view.util.GetCurrentPageUtil;

/** 
* @author wy
* @version 创建时间：2017年10月24日 下午6:22:30
*/
@Service
public class ShortLinkService {
    @Autowired
    private JudgeUserUtil judgeUserUtil;
    
    @Autowired
    private TransactionOrderService transactionOrderService;
    
    @Autowired
    private HttpService httpService;
    
    @Autowired
    private TradeInfoService tradeInfoServsice;
    
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ShortLinkService.class);
    
    /**
     * 短链接表名
     */
    private static final String LINK_TABLE_NAME = "crm_short_link";
    
    /**
     * 请求成功
     */
    public static final String HTTP_SUCCESS = "true";
    /**
     * 请求失败
     */
    public static final String HTTP_FAIL = "false";
    
    /**
     * 短链接服务器的地址
     */
    private String shortServerURL = "";
    /**
     * 获取短链接的url
     */
    private String getShortLinkURL ;
    /**
     * 删除短链接的url
     */
    private String removeShortLinkURL ;
    /**
     * 取得短链接效果分析短链接的url
     */
    private String getAllEffectURL ;
    /**
     * 显示具体点击详情短链接的url
     */
    private String showClickDetailURL ;
    
    public ShortLinkService(){
        ResourceBundle  resource=ResourceBundle.getBundle("application");
        this.shortServerURL = resource.getString("shortServerURL");
        this.getShortLinkURL = this.shortServerURL+"/link/getShortLink";
        this.removeShortLinkURL = this.shortServerURL+"/link/deleteById";
        this.getAllEffectURL = this.shortServerURL+"/link/getAllEffect";
        this.showClickDetailURL = this.shortServerURL+"/link/showClickDetail";
    }
    
    /**
     * 订单中心获取转换后的短链接
     * @author: wy
     * @time: 2017年10月24日 下午6:28:58
     * @param sellerNick 买家昵称
     * @param tid 主订单号
     * @param type 类型
     * @param taskId 任务id
     * @param sendDate 发送时间
     * @return 转换后的短链接加映射主键id
     */
    public String getConvertLinkByTmcTradeCenter(String sellerNick,Long tid,String type,Long taskId,Date sendDate,SmsSendInfo smsSendInfo){
        if(ValidateUtil.isEmpty(tid) ||ValidateUtil.isEmpty(sellerNick) ||ValidateUtil.isEmpty(type) ||sendDate==null ||smsSendInfo==null){
            return null;
        }
        String sessionKey = this.judgeUserUtil.getUserTokenByRedis(sellerNick);
        String taoBaoLink = TaoBaoClientUtil.creatLink(sessionKey,"LT_TRADE",String.valueOf(tid));
        Map<String,String> map = new HashMap<String,String>(10);
        map.put("taoBaoLink", taoBaoLink);
        map.put("tid", String.valueOf(tid));
        map.put("sendDate", String.valueOf(sendDate.getTime()));
        map.put("type", type);
        map.put("taskId", String.valueOf(taskId));
        map.put("sellerNick", sellerNick);
        String startNum = new SimpleDateFormat("yyMM").format(new Date());
        map.put("tableName", ShortLinkService.LINK_TABLE_NAME+startNum);
        JSONObject resultJSON = launchHttp(this.getShortLinkURL,map);
        if(resultJSON!=null){
            smsSendInfo.setShortLinkId(resultJSON.getLong("id"));
            return resultJSON.getString("link");
        }
        return  taoBaoLink;
    }
    /**
     * 根据用户、类型和所选定的时间查出对应的短链接记录统计
     * @author: wy
     * @time: 2017年10月26日 下午12:03:08
     * @param sellerNick 卖家昵称
     * @param type 类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return map集合 <br>
     *  key = pageClickNum , value = 链接总点击次数<br>
     *  key = customerClickNum , value = 用户总点击次数<br>
     *  key = customerClickRate , value = 客户点击率  
     */
    public JSONObject getAllEffect(String sellerNick,String type,Long taskId,Date startTime,Date endTime){
        JSONObject resultJSON = new JSONObject();
        resultJSON.put("result", HTTP_FAIL);
        if(ValidateUtil.isEmpty(sellerNick) ||ValidateUtil.isEmpty(type) ||startTime==null ||endTime==null){
            this.logger.debug("获取页面点击数据效果分析时参数错误，卖家昵称：" +sellerNick +",类型："+type);
            return null;
        }
        Map<String,String> param = new HashMap<String,String>(8);
        param.put("sellerNick", sellerNick);
        param.put("type", type);
        if(ValidateUtil.isNotNull(taskId)){
            param.put("taskId", String.valueOf(taskId));
        }
        param.put("startTime", String.valueOf(startTime.getTime()));
        param.put("endTime", String.valueOf(endTime.getTime()));
        resultJSON = launchHttp(this.getAllEffectURL,param);
        if(resultJSON!=null){
            return resultJSON;
        }
        return null;
    }
    /**
     * 分页显示  用户点击  详情
     * @author: wy
     * @time: 2017年10月26日 下午4:11:19
     * @param sellerNick 卖家昵称
     * @param type 类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 要显示的页数
     * @param lineSize 每页要显示的数量
     * @param isUserPage true 用户点击详情，false 页面点击详情
     * @return 返回json格式的数据<br>
     *    key = totalPage , value = 总页数<br>
     *    key = showPage , value = 显示的当前页数<br>
     *    key = data , value = 要显示的数据<br>
     */
    public JSONObject showClickDetail(String sellerNick,String type,Long taskId,Date startTime,
                                            Date endTime,Integer page,Integer lineSize,boolean isUserPage,
                                            String ipAddr,String userAti){
        JSONObject json = new JSONObject();
        json.put("result", HTTP_FAIL);
        if(ValidateUtil.isEmpty(sellerNick) ||ValidateUtil.isEmpty(type) ||startTime==null ||endTime==null){
            this.logger.debug("显示具体点击时间时参数错误，卖家昵称：" +sellerNick +",类型："+type);
            return json;
        }
        lineSize = GetCurrentPageUtil.getLineSize(lineSize);
        if(page==null){
            page = 1;
        }
        Map<String,String> param = new HashMap<String,String>(15);
        param.put("sellerNick", sellerNick);
        param.put("type", type);
        if(ValidateUtil.isNotNull(taskId)){
            param.put("taskId", String.valueOf(taskId));
        }
        param.put("startTime", String.valueOf(startTime.getTime()));
        param.put("endTime", String.valueOf(endTime.getTime()));
        param.put("page", String.valueOf(page));
        param.put("lineSize", String.valueOf(lineSize));
        param.put("isUserPage", String.valueOf(isUserPage));
        JSONObject resultJSON = launchHttp(this.showClickDetailURL,param);
        if(resultJSON==null){
            return json;
        }
        JSONArray linkList = resultJSON.getJSONArray("data");
        JSONArray newData = new JSONArray();
        Map<String, Object> params = new HashMap<String, Object>();
		LogAccessDTO logAccessDTO = new LogAccessDTO();
		logAccessDTO.setUserId(sellerNick);
		logAccessDTO.setUserIp(ipAddr);
		logAccessDTO.setAti(userAti);
		logAccessDTO.setUrl("用户订单查询");
		logAccessDTO.setOperation("用户订单查询");
		params.put(LogType.class.getName(), LogType.ORDER_TYPE);
		params.put(LogAccessDTO.class.getName(), logAccessDTO);
        for (Object object : linkList) {
            JSONObject  shortLinkVo= JSONObject.parseObject(String.valueOf(object));
            if(ValidateUtil.isEmpty(logAccessDTO.getTradeIds())){
            	logAccessDTO.setTradeIds(shortLinkVo.getString("tid"));
            }else{
            	logAccessDTO.setTradeIds(logAccessDTO.getTradeIds()+","+shortLinkVo.getString("tid"));
            }
            Trade trade = transactionOrderService.queryTradeByNoTmc(shortLinkVo.getString("tid"));
            if (trade == null) {
                TradeDTO tradeDTO = this.tradeInfoServsice.findOneByTidTMC(shortLinkVo.getString("tid"), sellerNick);
                if(tradeDTO!=null){
                    try {
                        trade = new Trade();
                        trade.setBuyerNick(tradeDTO.getBuyerNick());
                        trade.setReceiverMobile(tradeDTO.getReceiverMobile());
                        trade.setPayment(tradeDTO.getPayment()==null?"0.00":tradeDTO.getPayment().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(trade==null){
                this.logger.info("查询不到的订单id是： " +shortLinkVo.getString("tid"));
                shortLinkVo.put("buyerNick","暂时无法显示");
                shortLinkVo.put("buyerMobile","暂时无法显示");
                shortLinkVo.put("payment","暂时无法显示");
            }else{
                shortLinkVo.put("buyerNick",trade.getBuyerNick());
                shortLinkVo.put("buyerMobile",trade.getReceiverMobile());
                shortLinkVo.put("payment",trade.getPayment().toString());
            }
            if(shortLinkVo.getString("clickTime") != null){
                shortLinkVo.put("clickTimeStr",DateUtils.formatDate(shortLinkVo.getDate("clickTime"), DateUtils.DEFAULT_TIME_FORMAT));
            }
            newData.add(shortLinkVo);
        }
        try {
			LogAccessQueueService.queue.put(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
        resultJSON.remove("data");
        resultJSON.put("data", newData);
        return resultJSON;
    }
    /**
     * 根据主键ID删除链接映射关系
     * @author: wy
     * @time: 2017年10月25日 下午6:02:32
     * @param id 主键ID
     * @return 删除成功返回true 删除失败返回false
     */
    public boolean doRemoveById(Long id){
        if(ValidateUtil.isEmpty(id)){
            return false;
        }
        Map<String,String> param = new HashMap<String,String>(5);
        param.put("id", String.valueOf(id));
        JSONObject resultJSON = launchHttp(this.removeShortLinkURL,param);
        if(resultJSON!=null){
            return true;
        }
        return false;
    }
    
    /**
     * 发送http请求
     * @author: wy
     * @time: 2017年11月27日 下午3:56:04
     * @param url
     * @param param
     * @return
     */
    private JSONObject launchHttp(String url,Map<String,String> param){
        if(ValidateUtil.isEmpty(url) ||ValidateUtil.isEmpty(param)){
            return null;
        }
        String result = null;
        try {
            result = this.httpService.doGet(url, param);
            if(result==null){
                return null;
            }
            JSONObject resultJSON = JSONObject.parseObject(result);
            String flag = resultJSON.getString("result");
            if(HTTP_SUCCESS.equals(flag)){
                return resultJSON;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
   
}
