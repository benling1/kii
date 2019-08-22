package s2jh.biz.shop.crm.payment.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.payment.util.QRcodePayConfig;
import s2jh.biz.shop.crm.payment.util.SkipPayConfig;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;

@Service
public class PayHelper  {
	 
	@Resource
	private  SkipPayConfig skipPayConfig;
	
	/**
	 * @Description: 支付宝---跳转支付回调参数 
	 * @author HL
	 * @date 2017年11月16日 下午4:49:05
	 */
	public String buildSkipPay(String outTradeNo,Double totalAmount,String subject ){
		String submitForm="";
		try {
			JSONObject  json = new JSONObject();
			AlipayClient  aClient = new DefaultAlipayClient(skipPayConfig.gatewayUrl, skipPayConfig.app_id, skipPayConfig.merchant_private_key, "json", 
					skipPayConfig.charset, skipPayConfig.alipay_public_key, skipPayConfig.sign_type);
			AlipayTradePagePayRequest apRequest = new AlipayTradePagePayRequest();
			apRequest.setReturnUrl(skipPayConfig.return_url);
			apRequest.setNotifyUrl(skipPayConfig.notify_url);
			json.put("out_trade_no", outTradeNo);
			json.put("total_amount", totalAmount+"");
			json.put("subject", subject);
			json.put("product_code", "FAST_INSTANT_TRADE_PAY");
			apRequest.setBizContent(json.toJSONString());
			submitForm = aClient.pageExecute(apRequest).getBody();
		} catch (AlipayApiException e) {
		}
		 return submitForm;
	 }
	
	/**
	 * @Description: 支付宝---二维码支付回调参数
	 * @author HL
	 * @date 2017年11月16日 下午4:51:26
	 */
	public AlipayF2FPrecreateResult buildQRcodePay(String outTradeNo,Double totalAmount,String subject ){
			try {
				// 创建扫码支付请求builder，设置请求参数
				AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
				    .setSubject(subject).setTotalAmount(totalAmount+"").setOutTradeNo(outTradeNo)
				    .setStoreId(QRcodePayConfig.storeId).setTimeoutExpress(QRcodePayConfig.timeoutExpress)
				    .setNotifyUrl(skipPayConfig.notify_url);//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
				return QRcodePayConfig.tradeService.tradePrecreate(builder);
			} catch (Exception e) {
				return null;
			}
	 }
}
