/** 
 * Project Name:s2jh4net 
 * File Name:EncrptAndDecryptUtil.java 
 * Package Name:s2jh.biz.shop.crm.manage.util 
 * Date:2017年7月25日上午10:50:21 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
  
package s2jh.biz.shop.crm.manage.util;  

import java.util.List;
import java.util.Map;

import s2jh.biz.shop.crm.manage.entity.MemberDTO;
import s2jh.biz.shop.crm.manage.entity.OrdersDTO;
import s2jh.biz.shop.crm.manage.entity.SmsRecordDTO;
import s2jh.biz.shop.crm.manage.entity.TradeDTO;
import s2jh.biz.shop.crm.taobao.taobaoInfo;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.SecretException;
import com.taobao.api.security.SecurityClient;

/** 
 * ClassName:EncrptAndDecryptUtil <br/> 
 * Date:     2017年7月25日 上午10:50:21 <br/> 
 * @author   zlp
 * @version   1.0   
 *   
 */
public class EncrptAndDecryptClient extends SecurityClient{
	
	//模糊的加密方式  带检索需求Type：常规的为search，手机号码为 phone。  不带检索需求的Type：常规的为simple类型，手机号码为phone.
	public static final String SEARCH = "search";
	public static final String PHONE = "phone";
	public static final String SIMPLE = "simple";

	private EncrptAndDecryptClient(DefaultTaobaoClient taobaoClient,
			String randomNum) {
		super(taobaoClient, randomNum);
	}
    private static EncrptAndDecryptClient secretClient = null;
 
    public static synchronized EncrptAndDecryptClient getInstance() {
       if (secretClient == null) {
    	   secretClient = new EncrptAndDecryptClient(new DefaultTaobaoClient(taobaoInfo.serviceUrl, taobaoInfo.appKey, taobaoInfo.appSecret),taobaoInfo.securityToken);
       }
       return secretClient;
    }
    //针对单条数据进行加密
    /**
     * 加密（每个用户单独分配秘钥）
     * 
     * @see #encrypt(String, String, String, Long)
     * @return
     */
    public String encryptData(String data,String type,String session) throws SecretException{
    	return secretClient.encrypt(data, type, session);
    }
    //针对单条数据进行加密
    /**
     * 加密（每个用户单独分配秘钥）
     * 
     * @see #encrypt(String, String, String, Long)
     * @return
     */
    public String validateAndEncryptData(String data,String type,String session) throws SecretException{
    	boolean encryptData = EncrptAndDecryptClient.isEncryptData(data, type);
		if(!encryptData){
			return secretClient.encrypt(data, type, session);
		}else{
			return data;
		}
    }
    //针对单条数据进行解密
    /**
     * 解密（每个用户单独分配秘钥）
     * 
     * @param data
     *            密文数据 手机号码格式：$手机号码前3位明文$base64(encrypt(phone后8位))$111$
     *            simple格式：~base64(encrypt(nick))~111~
     * @param type
     *            解密字段类型(例如：simple\phone)
     * @param session
     *            用户身份,用户级加密必填
     * @return
     */
    public String decryptData(String data,String type,String session) throws SecretException{
    	return secretClient.decrypt(data, type, session);
    }
    //针对单条数据进行解密
    /**
     * 解密（每个用户单独分配秘钥）
     * 
     * @param data
     *            密文数据 手机号码格式：$手机号码前3位明文$base64(encrypt(phone后8位))$111$
     *            simple格式：~base64(encrypt(nick))~111~
     * @param type
     *            解密字段类型(例如：simple\phone)
     * @param session
     *            用户身份,用户级加密必填
     * @return
     */
    public String validateAndDecryptData(String data,String type,String session) throws SecretException{
    	boolean encryptData = EncrptAndDecryptClient.isEncryptData(data, type);
		if(encryptData){
			return secretClient.decrypt(data, type, session);
		}else{
			return data;
		}
    }
    
    //针对批量条数据进行加密
    /**
     * 批量加密（每个用户单独分配秘钥）
     * 
     * @see #encrypt(String, String, String, Long)
     * @param dataList
     * @param type
     * @param session
     * @return key=明文数据，value=密文数据
     * @throws SecretException
     */
    public Map<String,String> encryptListData(List<String> dataList, String type,String session) throws SecretException{
    	return secretClient.encrypt(dataList, type, session);
    }
    //针对批量数据进行解密
    /**
     * 批量解密（每个用户单独分配秘钥）
     * 
     * @see #decrypt(String, String, String)
     * @param dataList
     * @param type
     * @param session
     * @return key=密文数据，value=明文数据
     * @throws SecretException
     */
    public Map<String,String> decryptListData(List<String> dataList, String type,String session) throws SecretException{
    	return secretClient.decrypt(dataList, type, session);
    }
    
    /***
     * 
     * encryptTradeListData:同步数据专用. <br/> 
     * @author zlp
     * @param dataList
     * @param session
     * @return
     * @throws SecretException
     */
    public List<TradeDTO> encryptTradeListData(List<TradeDTO> tradeDTOList,String session) throws SecretException{
   
		 if(null!=tradeDTOList&&tradeDTOList.size()>0){
			 for (TradeDTO tradeDTO : tradeDTOList) {
				 if(null!=tradeDTO.getBuyerNick()&&!"".equals(tradeDTO.getBuyerNick())){
					 String encryptData = encryptData(tradeDTO.getBuyerNick(),EncrptAndDecryptClient.SEARCH,session);
					 tradeDTO.setBuyerNick(encryptData);
					 List<OrdersDTO> orders = tradeDTO.getOrders();
					 for (OrdersDTO ordersDTO : orders) {
						 ordersDTO.setBuyerNick(encryptData);
					 }
				 }
				 if(null!=tradeDTO.getReceiverName()&&!"".equals(tradeDTO.getReceiverName())){
					 String encryptData = encryptData(tradeDTO.getReceiverName(),EncrptAndDecryptClient.SEARCH,session);
					 tradeDTO.setReceiverName(encryptData);
					 List<OrdersDTO> orders = tradeDTO.getOrders();
					 for (OrdersDTO ordersDTO : orders) {
						 ordersDTO.setReceiverName(encryptData);
					 }
				 }
				 if(null!=tradeDTO.getReceiverMobile()&&!"".equals(tradeDTO.getReceiverMobile())){
					 String encryptData = encryptData(tradeDTO.getReceiverMobile(),EncrptAndDecryptClient.PHONE,session);
					 tradeDTO.setReceiverMobile(encryptData);
					 List<OrdersDTO> orders = tradeDTO.getOrders();
					 for (OrdersDTO ordersDTO : orders) {
						 ordersDTO.setReceiverMobile(encryptData);
					 }
				 }
				 if(null!=tradeDTO.getReceiverPhone()&&!"".equals(tradeDTO.getReceiverPhone())){
					 String encryptData = encryptData(tradeDTO.getReceiverPhone(),EncrptAndDecryptClient.SIMPLE,session);
					 tradeDTO.setReceiverPhone(encryptData);
				 }
				 if(null!=tradeDTO.getBuyerEmail()&&!"".equals(tradeDTO.getBuyerEmail())){
					 String encryptData = encryptData(tradeDTO.getBuyerEmail(),EncrptAndDecryptClient.SEARCH,session);
					 tradeDTO.setBuyerEmail(encryptData);
				 }
				 if(null!=tradeDTO.getBuyer_alipay_no()&&!"".equals(tradeDTO.getBuyer_alipay_no())){
					 String encryptData = encryptData(tradeDTO.getBuyer_alipay_no(),EncrptAndDecryptClient.SIMPLE,session);
					 tradeDTO.setBuyer_alipay_no(encryptData);
				 }
				 if(null!=tradeDTO.getAlipayNo()&&!"".equals(tradeDTO.getAlipayNo())){
					 String encryptData = encryptData(tradeDTO.getAlipayNo(),EncrptAndDecryptClient.SIMPLE,session);
					 tradeDTO.setAlipayNo(encryptData);
				 }
				 if(null!=tradeDTO.getReceiverAddress()&&!"".equals(tradeDTO.getReceiverAddress())){
					 String encryptData = encryptData(tradeDTO.getReceiverAddress(),EncrptAndDecryptClient.SIMPLE,session);
					 tradeDTO.setReceiverAddress(encryptData);
				 }
			}
		 }
		return tradeDTOList;
    }
    
    /***
     * 
     * encryptTradeListData:同步数据专用. <br/> 
     * @author zlp
     * @param dataList
     * @param session
     * @return
     * @throws SecretException
     */
    public TradeDTO encryptTradeData(TradeDTO tradeDTO,String session) throws SecretException{
		if(null!=tradeDTO.getBuyerNick()&&!"".equals(tradeDTO.getBuyerNick())){
			 String encryptData = encryptData(tradeDTO.getBuyerNick(),EncrptAndDecryptClient.SEARCH,session);
			 tradeDTO.setBuyerNick(encryptData);
			 List<OrdersDTO> orders = tradeDTO.getOrders();
			 for (OrdersDTO ordersDTO : orders) {
				 ordersDTO.setBuyerNick(encryptData);
			 }
		 }
		 if(null!=tradeDTO.getReceiverName()&&!"".equals(tradeDTO.getReceiverName())){
			 String encryptData = encryptData(tradeDTO.getReceiverName(),EncrptAndDecryptClient.SEARCH,session);
			 tradeDTO.setReceiverName(encryptData);
			 List<OrdersDTO> orders = tradeDTO.getOrders();
			 for (OrdersDTO ordersDTO : orders) {
				 ordersDTO.setReceiverName(encryptData);
			 }
		 }
		 if(null!=tradeDTO.getReceiverMobile()&&!"".equals(tradeDTO.getReceiverMobile())){
			 String encryptData = encryptData(tradeDTO.getReceiverMobile(),EncrptAndDecryptClient.PHONE,session);
			 tradeDTO.setReceiverMobile(encryptData);
			 List<OrdersDTO> orders = tradeDTO.getOrders();
			 for (OrdersDTO ordersDTO : orders) {
				 ordersDTO.setReceiverMobile(encryptData);
			 }
		 }
		 if(null!=tradeDTO.getReceiverPhone()&&!"".equals(tradeDTO.getReceiverPhone())){
			 String encryptData = encryptData(tradeDTO.getReceiverPhone(),EncrptAndDecryptClient.SIMPLE,session);
			 tradeDTO.setReceiverPhone(encryptData);
		 }
		 if(null!=tradeDTO.getBuyerEmail()&&!"".equals(tradeDTO.getBuyerEmail())){
			 String encryptData = encryptData(tradeDTO.getBuyerEmail(),EncrptAndDecryptClient.SEARCH,session);
			 tradeDTO.setBuyerEmail(encryptData);
		 }
		 if(null!=tradeDTO.getBuyer_alipay_no()&&!"".equals(tradeDTO.getBuyer_alipay_no())){
			 String encryptData = encryptData(tradeDTO.getBuyer_alipay_no(),EncrptAndDecryptClient.SIMPLE,session);
			 tradeDTO.setBuyer_alipay_no(encryptData);
		 }
		 if(null!=tradeDTO.getAlipayNo()&&!"".equals(tradeDTO.getAlipayNo())){
			 String encryptData = encryptData(tradeDTO.getAlipayNo(),EncrptAndDecryptClient.SIMPLE,session);
			 tradeDTO.setAlipayNo(encryptData);
		 }
		 if(null!=tradeDTO.getReceiverAddress()&&!"".equals(tradeDTO.getReceiverAddress())){
			 String encryptData = encryptData(tradeDTO.getReceiverAddress(),EncrptAndDecryptClient.SIMPLE,session);
			 tradeDTO.setReceiverAddress(encryptData);
		 }
		return tradeDTO;
    }

    
    
    /***
     * 
     * encryptTradeListData:同步数据专用. <br/> 
     * @author zlp
     * @param dataList
     * @param session
     * @return
     * @throws SecretException
     */
    public List<TradeDTO> decryptTradeListData(List<TradeDTO> dataList,String session) throws SecretException{
		if(dataList!=null&&dataList.size()>0){
				for (TradeDTO tradeDTO : dataList) {
					 if(null!=tradeDTO.getBuyerNick()&&!"".equals(tradeDTO.getBuyerNick())){
						 String encryptData = decryptData(tradeDTO.getBuyerNick(),EncrptAndDecryptClient.SEARCH,session);
						 tradeDTO.setBuyerNick(encryptData);
						 List<OrdersDTO> orders = tradeDTO.getOrders();
						 for (OrdersDTO ordersDTO : orders) {
							 ordersDTO.setBuyerNick(encryptData);
						 }
					 }
					 if(null!=tradeDTO.getReceiverName()&&!"".equals(tradeDTO.getReceiverName())){
						 String encryptData = decryptData(tradeDTO.getReceiverName(),EncrptAndDecryptClient.SEARCH,session);
						 tradeDTO.setReceiverName(encryptData);
						 List<OrdersDTO> orders = tradeDTO.getOrders();
						 for (OrdersDTO ordersDTO : orders) {
							 ordersDTO.setReceiverName(encryptData);
						 }
					 }
					 if(null!=tradeDTO.getReceiverMobile()&&!"".equals(tradeDTO.getReceiverMobile())){
						 String encryptData = decryptData(tradeDTO.getReceiverMobile(),EncrptAndDecryptClient.PHONE,session);
						 tradeDTO.setReceiverMobile(encryptData);
						 List<OrdersDTO> orders = tradeDTO.getOrders();
						 for (OrdersDTO ordersDTO : orders) {
							 ordersDTO.setReceiverMobile(encryptData);
						 }
					 }
					 if(null!=tradeDTO.getReceiverPhone()&&!"".equals(tradeDTO.getReceiverPhone())){
						 String encryptData = decryptData(tradeDTO.getReceiverPhone(),EncrptAndDecryptClient.SIMPLE,session);
						 tradeDTO.setReceiverPhone(encryptData);
					 }
					 if(null!=tradeDTO.getBuyerEmail()&&!"".equals(tradeDTO.getBuyerEmail())){
						 String encryptData = decryptData(tradeDTO.getBuyerEmail(),EncrptAndDecryptClient.SEARCH,session);
						 tradeDTO.setBuyerEmail(encryptData);
					 }
					 if(null!=tradeDTO.getBuyer_alipay_no()&&!"".equals(tradeDTO.getBuyer_alipay_no())){
						 String encryptData = decryptData(tradeDTO.getBuyer_alipay_no(),EncrptAndDecryptClient.SIMPLE,session);
						 tradeDTO.setBuyer_alipay_no(encryptData);
					 }
					 if(null!=tradeDTO.getAlipayNo()&&!"".equals(tradeDTO.getAlipayNo())){
						 String encryptData = decryptData(tradeDTO.getAlipayNo(),EncrptAndDecryptClient.SIMPLE,session);
						 tradeDTO.setAlipayNo(encryptData);
					 }
					 
					 if(null!=tradeDTO.getReceiverAddress()&&!"".equals(tradeDTO.getReceiverAddress())){
						 String encryptData = decryptData(tradeDTO.getReceiverAddress(),EncrptAndDecryptClient.SIMPLE,session);
						 tradeDTO.setReceiverAddress(encryptData);
					 }
				 }
		 }else{return null;}
    	return dataList;
    }
    
    
    
    /**
     * 
     * 
     * 
     */
    /***
     * 
     * encryptMemberListData:同步数据专用. <br/> 
     * @author zlp
     * @param dataList
     * @param session
     * @return
     * @throws SecretException
     */
    public List<MemberDTO> encryptMemberListData(List<MemberDTO> dataList,String session) throws SecretException{
		if(dataList!=null&&dataList.size()>0){
				for (MemberDTO member : dataList) {
				 if(null!=member.getBuyerNick()&&!"".equals(member.getBuyerNick())){
					 String encryptData = encryptData(member.getBuyerNick(),EncrptAndDecryptClient.SEARCH,session);
					 member.setBuyerNick(encryptData);
				 }
				 if(null!=member.getReceiverName()&&!"".equals(member.getReceiverName())){
					 String encryptData = encryptData(member.getReceiverName(),EncrptAndDecryptClient.SEARCH,session);
					 member.setReceiverName(encryptData);
				 }
				 if(null!=member.getPhone()&&!"".equals(member.getPhone())){
					 String encryptData = encryptData(member.getPhone(),EncrptAndDecryptClient.PHONE,session);
					 member.setPhone(encryptData);
				 }
				 if(null!=member.getEmail()&&!"".equals(member.getEmail())){
					 String encryptData = encryptData(member.getEmail(),EncrptAndDecryptClient.SEARCH,session);
					 member.setEmail(encryptData);
				 }
			 }
		 }else{return null;}
     	return dataList;
    }
    
    
    /***
     * 
     * decryptMemberListData:同步数据专用. <br/> 
     * @author zlp
     * @param dataList
     * @param session
     * @throws SecretException
     */
    public List<MemberDTO> decryptMemberListData(List<MemberDTO> dataList,String session) throws SecretException{
		if(dataList!=null&&dataList.size()>0){
				for (MemberDTO member : dataList) {
					 if(null!=member.getBuyerNick()&&!"".equals(member.getBuyerNick())){
						 String encryptData = decrypt(member.getBuyerNick(),EncrptAndDecryptClient.SEARCH,session);
						 member.setBuyerNick(encryptData);
					 }
					 if(null!=member.getReceiverName()&&!"".equals(member.getReceiverName())){
						 String encryptData = decrypt(member.getReceiverName(),EncrptAndDecryptClient.SEARCH,session);
						 member.setReceiverName(encryptData);
					 }
					 if(null!=member.getPhone()&&!"".equals(member.getPhone())){
						 String encryptData = decrypt(member.getPhone(),EncrptAndDecryptClient.PHONE,session);
						 member.setPhone(encryptData);
					 }
					 if(null!=member.getEmail()&&!"".equals(member.getEmail())){
						 String encryptData = decrypt(member.getEmail(),EncrptAndDecryptClient.SEARCH,session);
						 member.setEmail(encryptData);
					 }
				 }
		 }else{return null;}
    	return dataList;
    }
    
    
    /***
     * 
     * encryptMemberListData:同步数据专用. <br/> 
     * @author zlp
     * @param dataList
     * @param session
     * @return
     * @throws SecretException
     */
    public List<SmsRecordDTO> encryptSmsRecordListData(List<SmsRecordDTO> dataList,String session) throws SecretException{
		if(dataList!=null&&dataList.size()>0){
				for (SmsRecordDTO smsRecordDTO : dataList) {
				 if(null!=smsRecordDTO.getRecNum()&&!"".equals(smsRecordDTO.getRecNum())){
					 String encryptData = encryptData(smsRecordDTO.getRecNum(),EncrptAndDecryptClient.PHONE,session);
					 smsRecordDTO.setRecNum(encryptData);
				 }else{
					 smsRecordDTO.setRecNum("");
				 }
				 if(null!=smsRecordDTO.getBuyerNick()&&!"".equals(smsRecordDTO.getBuyerNick())){
					 String encryptData = encryptData(smsRecordDTO.getBuyerNick(),EncrptAndDecryptClient.SEARCH,session);
					 smsRecordDTO.setBuyerNick(encryptData);
				 }else{
					 smsRecordDTO.setBuyerNick("");
				 }
				 if(null!=smsRecordDTO.getNickname()&&!"".equals(smsRecordDTO.getNickname())){
					 String encryptData = encryptData(smsRecordDTO.getNickname(),EncrptAndDecryptClient.SEARCH,session);
					 smsRecordDTO.setNickname(encryptData);
				 }
			 }
		 }else{return null;}
     	return dataList;
    }
    /***
     * 
     * decryptSmsRecordListData:同步数据专用. <br/> 
     * @author zlp
     * @param dataList
     * @param session
     * @throws SecretException
     */
    public List<SmsRecordDTO> decryptSmsRecordListData(List<SmsRecordDTO> dataList,String session) throws SecretException{
		if(dataList!=null&&dataList.size()>0){
			for (SmsRecordDTO smsRecordDTO : dataList) {
			 if(null!=smsRecordDTO.getRecNum()&&!"".equals(smsRecordDTO.getRecNum())){
				 String decrptData = decrypt(smsRecordDTO.getRecNum(),EncrptAndDecryptClient.SEARCH,session);
				 smsRecordDTO.setRecNum(decrptData);
			 }
		 }
	    }else{return null;}
    	return dataList;
    }
    /***
     * 
     * 
     *     /***
     * 
     * encryptTradeListData:同步数据专用. <br/> 
     * @author zlp
     * @param dataList
     * @param session
     * @return
     * @throws SecretException
     */
//    public Map<String,Object> encryptTradeListData(List<TradeDTO> dataList,String session) throws SecretException{
//    	 Map<String,Object> map = new HashMap<String,Object>();
//    	 map.put("result","false");
//    	 if(dataList!=null&&dataList.size()>0){
//    		 try {
//				for (TradeDTO tradeDTO : dataList) {
//					 if(null!=tradeDTO.getBuyerNick()&&!"".equals(tradeDTO.getBuyerNick())){
//						 String encryptData = encryptData(tradeDTO.getBuyerNick(),EncrptAndDecryptClient.SIMPLE,session);
//						 tradeDTO.setBuyerNick(encryptData);
//						 List<OrdersDTO> orders = tradeDTO.getOrders();
//						 for (OrdersDTO ordersDTO : orders) {
//							 ordersDTO.setBuyerNick(encryptData);
//						 }
//					 }
//					 if(null!=tradeDTO.getReceiverName()&&!"".equals(tradeDTO.getReceiverName())){
//						 String encryptData = encryptData(tradeDTO.getReceiverName(),EncrptAndDecryptClient.SIMPLE,session);
//						 tradeDTO.setReceiverName(encryptData);
//						 List<OrdersDTO> orders = tradeDTO.getOrders();
//						 for (OrdersDTO ordersDTO : orders) {
//							 ordersDTO.setReceiverName(encryptData);
//						 }
//					 }
//					 if(null!=tradeDTO.getReceiverMobile()&&!"".equals(tradeDTO.getReceiverMobile())){
//						 String encryptData = encryptData(tradeDTO.getReceiverMobile(),EncrptAndDecryptClient.PHONE,session);
//						 tradeDTO.setReceiverMobile(encryptData);
//						 List<OrdersDTO> orders = tradeDTO.getOrders();
//						 for (OrdersDTO ordersDTO : orders) {
//							 ordersDTO.setReceiverMobile(encryptData);
//						 }
//					 }
//					 if(null!=tradeDTO.getReceiverPhone()&&!"".equals(tradeDTO.getReceiverPhone())){
//						 String encryptData = encryptData(tradeDTO.getReceiverPhone(),EncrptAndDecryptClient.SIMPLE,session);
//						 tradeDTO.setReceiverPhone(encryptData);
//					 }
//					 if(null!=tradeDTO.getBuyerEmail()&&!"".equals(tradeDTO.getBuyerEmail())){
//						 String encryptData = encryptData(tradeDTO.getBuyerEmail(),EncrptAndDecryptClient.SIMPLE,session);
//						 tradeDTO.setBuyerEmail(encryptData);
//					 }
//					 if(null!=tradeDTO.getBuyer_alipay_no()&&!"".equals(tradeDTO.getBuyer_alipay_no())){
//						 String encryptData = encryptData(tradeDTO.getBuyer_alipay_no(),EncrptAndDecryptClient.SIMPLE,session);
//						 tradeDTO.setBuyer_alipay_no(encryptData);
//					 }
//					 if(null!=tradeDTO.getAlipayNo()&&!"".equals(tradeDTO.getAlipayNo())){
//						 String encryptData = encryptData(tradeDTO.getAlipayNo(),EncrptAndDecryptClient.SIMPLE,session);
//						 tradeDTO.setAlipayNo(encryptData);
//					 }
//				 }
//				 map.put("result","true");
//				 map.put("list",dataList);
//			} catch (Exception e) {
//				 map.put("result","false");
//			}
//    	 }
//    	 return map;
//    }
}
  