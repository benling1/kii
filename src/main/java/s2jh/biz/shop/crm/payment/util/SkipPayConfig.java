package s2jh.biz.shop.crm.payment.util;

import java.io.Serializable;



public class SkipPayConfig implements Serializable{
		/** 
	 * Project Name:s2jh4net 
	 * File Name:SkipPayConfig.java 
	 * Package Name:s2jh.biz.shop.crm.payment.util 
	 * Date:2017年11月16日下午3:29:29 
	 * Copyright (c) 2017,  All Rights Reserved. 
	 * 
	 */  
	private static final long serialVersionUID = -6390161467241573037L;

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public  String app_id  ;
	// 商户私钥，您的PKCS8格式RSA2私钥
    public  String merchant_private_key ;
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public  String alipay_public_key ;
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public  String notify_url ;
	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public  String return_url;
	// 签名方式
	public  String sign_type; 
	// 字符编码格式
	public  String charset;
	// 支付宝网关
	public  String gatewayUrl ;
	
	//获得初始化的AlipayClient
//		public static AlipayClient alipayClient;
//		//设置请求参数
//		private static AlipayTradePagePayRequest alipayRequest;
//		
//		public SkipPayConfig(){
//			System.out.println(111);
//			alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", charset, alipay_public_key, sign_type);
//			alipayRequest = new AlipayTradePagePayRequest();
//			alipayRequest.setReturnUrl(return_url);
//			alipayRequest.setNotifyUrl(notify_url);
//		} 
//		
//		public static AlipayTradePagePayRequest getAlipayRequest(String outTradeNo, Double totalAmount,
//				String subject) {
//		    JSONObject  json = new JSONObject();
//			json.put("out_trade_no", outTradeNo);
//			json.put("total_amount", totalAmount+"");
//			json.put("subject", subject);
//			json.put("product_code", "FAST_INSTANT_TRADE_PAY");
//			alipayRequest.setBizContent(json.toJSONString());
//			return alipayRequest;
//		}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getMerchant_private_key() {
		return merchant_private_key;
	}
	public void setMerchant_private_key(String merchant_private_key) {
		this.merchant_private_key = merchant_private_key;
	}
	public String getAlipay_public_key() {
		return alipay_public_key;
	}
	public void setAlipay_public_key(String alipay_public_key) {
		this.alipay_public_key = alipay_public_key;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getGatewayUrl() {
		return gatewayUrl;
	}
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
		
}
