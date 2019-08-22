package s2jh.biz.shop.crm.taobao.util;

import s2jh.biz.shop.utils.SimpleHttpClient;




public class RealSendMessageUtil {
	private static final RealSendMessageUtil realSend = new RealSendMessageUtil();
	private RealSendMessageUtil(){}
	public static RealSendMessageUtil getRealSendMessageUtil(){
		return realSend;
	}
	/**
	 * 发送短信
	 * @param phone
	 * @param content
	 * @return
	 */
	public String sendSmsApi(String phone,String content){
//		String result = SendMessageUtil.sendMessage(phone,content, null, null);
//		ReturnMessage returnMessage = JsonUtil.fromJson(result, ReturnMessage.class);
//		return  returnMessage.getReturnCode();
		return "100";
//		return SimpleHttpClient.send("http://223.223.180.20:9885/c123/sendsms","205094", "789384", phone,content, null, null);
//		return   SendMessageUtil.sendMessage(phone,content, null, null);
	}
}
