package s2jh.biz.shop.crm.payment.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.payment.service.AlipayService;
import s2jh.biz.shop.crm.payment.service.PayHelper;
import s2jh.biz.shop.crm.payment.util.PayUtils;
import s2jh.biz.shop.crm.user.service.UserRechargeService;
import s2jh.biz.shop.utils.IdUtils;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;

@Controller
@RequestMapping(value = "aliPay")
public class AliPayController extends BaseController {
	private static final Log log = LogFactory.getLog(AliPayController.class);
	@Autowired
	private UserRechargeService userRechargeService;
	@Autowired
	private AlipayService alipayService;
	@Autowired
	private PayHelper payHelper;
	/**
	 * @Description: 自定义短信充值----二维码支付 
	 * @author HL
	 * @param rechargeNum //必填) 短信充值条数
	 * @param totalAmount (必填) 订单总金额，单位为元，不能超过1亿元
	 * @date 2017年11月16日 下午5:24:35
	 */
    @ResponseBody
    @RequestMapping(value = "/qrCodePay", method = RequestMethod.POST)
	public String qrCodePay(Double totalAmount, Integer rechargeNum,
			HttpServletRequest request) {
    	String userNick = (String) request.getSession().getAttribute("taobao_user_nick");
		if (null != userNick && !"".equals(userNick) && null != totalAmount
				&& totalAmount > 0 && null != rechargeNum && rechargeNum > 0
				&& PayUtils.checkoutSmsAndMoney(totalAmount, rechargeNum)) {

			//响应二维码图片路径
			String  qrCodePath = "";
			
        	// (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        	String subject = PayUtils.getSubject(rechargeNum,totalAmount);
        	
            // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
            String outTradeNo = IdUtils.getTradeId();

            //获取二维码数据
            AlipayF2FPrecreateResult result = payHelper.buildQRcodePay(outTradeNo, totalAmount, subject);
            if(result !=null){
            	switch (result.getTradeStatus()) {
	                 case SUCCESS:
	                     log.info("*********************************支付宝预下单成功:"+result.getTradeStatus());
	                     AlipayTradePrecreateResponse response = result.getResponse();
		     			try {
		     				qrCodePath = PayUtils.createQrCodeImage(request,response);
		     				if(null != qrCodePath && !"".equals(qrCodePath)){
		     					//保存充值记录，
		     					alipayService.insertUserRechargeRecord(totalAmount,rechargeNum,userNick,outTradeNo);
		     				}
		     			} catch (Exception e) {
		     			}
	                     break;
	
	                 case FAILED:
	                     log.error("*********************************支付宝预下单失败!!!");
	                     break;
	
	                 case UNKNOWN:
	                     log.error("*********************************系统异常，预下单状态未知!!!");
	                     break;
	
	                 default:
	                     log.error("*********************************不支持的交易状态，交易返回异常!!!");
	                     break;
            	}
            }
            
            return qrCodePath;
    	}else{
    		log.error("*********************************参数异常：(userNick):"+userNick+"(totalAmount):"+totalAmount+"(rechargeNum):"+rechargeNum);
    	}
		return "";
    }
    
    /**
     * @Description: 自定义短信充值----跳转支付 
     * @author HL
     * @date 2017年11月16日 下午5:25:25
     * @param rechargeNum //必填) 短信充值条数
	 * @param totalAmount (必填) 订单总金额，单位为元，不能超过1亿元
     */
    @ResponseBody
	@RequestMapping(value = "/skipPay", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String skipPay(Double totalAmount, Integer rechargeNum,
			HttpServletRequest request) {
    	String userNick = (String) request.getSession().getAttribute("taobao_user_nick");

    	if (null != userNick && !"".equals(userNick) && null != totalAmount
				&& totalAmount > 0 && null != rechargeNum && rechargeNum > 0
				&& PayUtils.checkoutSmsAndMoney(totalAmount, rechargeNum)) {
        	
        	// (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
            String outTradeNo = IdUtils.getTradeId();

        	// (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
            String subject = PayUtils.getSubject(rechargeNum,totalAmount);
        	
			// 获取封装支付宝请求数据
           try {
        	    String result = payHelper.buildSkipPay(outTradeNo, totalAmount, subject);
        	    if(null != result && !"".equals(result)){
        	    	alipayService.insertUserRechargeRecord(totalAmount, rechargeNum, userNick, outTradeNo);
        	    }
            	//保存记录
            	return rsMap(100, "操作成功").put("data",result).toJson();
             } catch (Exception e) {
             }
		}else{
    		log.error("*********************************参数异常：(userNick):"+userNick+"，(totalAmount):"+totalAmount+"，(rechargeNum):"+rechargeNum);
    	}
    	return null;
    }
    
    
    
    /**
     * 接收支付宝异步返回支付通知消息
     * @author HL
     */
    @ResponseBody
    @RequestMapping(value = "/payNotify", method = RequestMethod.POST)
	public String payNotify(String out_trade_no, String trade_status,
			Double total_amount) {
    	try {
			if (null != trade_status && !"".equals(trade_status)
					&& null != out_trade_no && !"".equals(out_trade_no)
					&& null != total_amount) {
				return alipayService.disposePayNotify(trade_status,out_trade_no,total_amount);
			}
		} catch (Exception e) {
			log.error("***************************接收支付宝异步通知代码处理异常");
			e.printStackTrace();
		}
		return "";
    }
    
    
    /**
     * 查询充值记录状态
     * @author HL
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/payStatus")
	public String payStatus(String payTrade) {
		return userRechargeService.findPayStatus(payTrade);
    }
    
    /**
     * @Description: 跳转支付的页面跳转 
     * @author HL
     * @date 2017年11月16日 下午6:39:25
     */
    @RequestMapping(value = "/skipPayPage")
	public String skipPayPage(Double totalAmount, Integer rechargeNum,Model model) {
    	model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("rechargeNum", rechargeNum);
		return "crms/taobao/skipPay";
    }
}
