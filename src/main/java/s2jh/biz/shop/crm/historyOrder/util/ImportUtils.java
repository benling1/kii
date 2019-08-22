package s2jh.biz.shop.crm.historyOrder.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import s2jh.biz.shop.crm.item.entity.ItemImport;
import s2jh.biz.shop.utils.IdUtils;
public class ImportUtils {
	//时间格式
	public static final String DATE_FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_TWO = "yyyy/MM/dd HH:mm";
	private static final DecimalFormat df = new DecimalFormat("0.00");
	/**
	 * 通过方法获取数据
	 * @return
	 */
	public static String getValFromArray(Map<String, Integer> headerMap,
			String[] dataArr, String header) {
		if (headerMap.containsKey(header)) {
			return dataArr[headerMap.get(header)];
		}
		return "";
	}
	
	/**
	 * 处理有符号的订单编号 helei 2017年3月6日下午4:13:07
	 */
	public static String getTid(String tid) {
		String newTid = "";
		try {
			newTid = tid.trim();
			if(newTid.startsWith("=")){
				newTid = newTid.substring(2, newTid.length()-1);
			}
		} catch (Exception e) {
			return tid;
		}
		return newTid;
	}
	
	/**
	 * 获取交易的状态转换 helei 2017年2月10日下午12:03:25
	 * @param cause 
	 */
	public static String getdeal(String dealStatus, String cause) {
		String status = "";
		if (dealStatus.equals("等待买家付款")) {
			status = "WAIT_BUYER_PAY";
		}
		if (dealStatus.equals("买家已付款，等待卖家发货")) {
			status = "WAIT_SELLER_SEND_GOODS";
		}
		if (dealStatus.equals("等待卖家发货")) {
			status = "WAIT_SELLER_SEND_GOODS";
		}
		if (dealStatus.equals("卖家部分发货")) {
			status = "SELLER_CONSIGNED_PART";
		}
		if (dealStatus.equals("等待买家确认收货")) {
			status = "WAIT_BUYER_CONFIRM_GOODS";
		}
		if (dealStatus.equals("卖家已发货，等待买家确认")) {
			status = "WAIT_BUYER_CONFIRM_GOODS";
		}
		if (dealStatus.equals("买家已签收")) {
			status = "TRADE_BUYER_SIGNED";
		}
		if (dealStatus.equals("交易成功")) {
			status = "TRADE_FINISHED";
		}
		if (dealStatus.equals("交易关闭")) {
			status = "TRADE_CLOSED";
			if("买家未付款".equals(cause)){
				status = "TRADE_CLOSED_BY_TAOBAO";
			}
		}
		if (dealStatus.equals("交易被淘宝关闭")) {
			status = "TRADE_CLOSED_BY_TAOBAO";
		}
		if (dealStatus.equals("没有创建外部交易")) {
			status = "TRADE_NO_CREATE_PAY";
		}
		if (dealStatus.equals("余额宝0元购合约中")) {
			status = "WAIT_PRE_AUTH_CONFIRM";
		}
		if (dealStatus.equals("外卡支付付款确认中")) {
			status = "PAY_PENDING";
		}
		if (dealStatus.equals("所有买家未付款的交易")) {
			status = "ALL_WAIT_PAY";
		}
		if (dealStatus.equals("所有关闭的交易")) {
			status = "ALL_CLOSED";
		}
		return status;
	}
	
	/**
	 * 获取省份 helei 2017年3月6日上午10:51:44
	 */
	public static String getProvinces(String receiverAddress) {
			String[] cityList = receiverAddress.split(" ");
			try {
				return cityList[0];
			} catch (Exception e) {
				return null;
			}
	}
	
	/**
	 * 获取城市 helei 2017年3月6日上午10:51:44
	 */
	public static String getCity(String receiverAddress) {
		String[] cityList = receiverAddress.split(" ");
		try {
			return cityList[1];
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 电话号码的处理 helei 2017年2月10日下午2:48:33
	 */
	public static String getPhone(String phone) {
		String newPhone = "";
		if (phone.indexOf("'") != -1) {
			newPhone = phone.substring(1, phone.length());
		} else {
			newPhone = phone;
		}
		return newPhone;
	}
	
	
	
	/**
	 * 订单编号校验
	 * @author Administrator_HL
	 * @data 2017年5月17日 上午10:56:56
	 */
	public static boolean judgeOrderId(String tid) {
		boolean matches = false;
		try {
			Pattern p = Pattern.compile("[0-9]+");
			matches = p.matcher(tid).matches();
		} catch (Exception e) {
			return false;
		}
		return matches;
	}
	
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	public static Date timeFormat(String time,String format) throws ParseException{
		// 时间格式化
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(time);
	}
	
	
	/**
	 * 获取所以宝贝标题&创建商品id
	 * @return
	 */
	public static Map<String,List<String>> getImportTitles(Map<String, Integer> headerMap,
			List<String[]> datasList) {
		Map<String,List<String>> map= new HashMap<String,List<String>>();
		for (String[] dataArr : datasList) {
			try {
				List<String> list = new ArrayList<String>();
				String title = getValFromArray(headerMap, dataArr, "宝贝标题");
				if(null != title && !"".equals(title)){
					String price = unitPrice(
							getValFromArray(headerMap, dataArr, "总金额"),
							getValFromArray(headerMap, dataArr, "宝贝总数量"));
					String itemId = IdUtils.getTradeId();
					list.add(itemId);
					list.add(price);
					map.put(title.trim(), list);
				}
			} catch (Exception e) {
				continue;
			}
		}
		return map;
	}

	/**
	 * 计算单价
	 */
	private static String unitPrice(String totalFee, String num) {
		try {
			double price = Double.parseDouble(totalFee)/Double.parseDouble(num);
			return Double.parseDouble(df.format(price))+"";
		} catch (Exception e) {
			return "0";
		}
	}

	/*
	 * 将该用户的商品和当前导入的比对 --key需要与待导入的订单比对，value--需要将数据保存到新建商品表
	 */
	public static Map<Map<String, List<String>>, List<ItemImport>> comparisonItemTitle(
			Map<String, Long> itemMap, Map<String, List<String>> importTitles,String userId) {
		Map<Map<String, List<String>>, List<ItemImport>> returnMap = new HashMap<Map<String, List<String>>, List<ItemImport>>();
		
		Map<String, List<String>> returnKay = new HashMap<String, List<String>>();
		
		List<ItemImport> returnValue = new ArrayList<ItemImport>();
		Set<Entry<String,List<String>>> entrySet = importTitles.entrySet();
		
		for (Entry<String, List<String>> entry : entrySet) {
			List<String> itemLong = new ArrayList<String>();
			try {
				if(entry.getKey().length()>255){
					continue;
				}
				if(itemMap.containsKey(entry.getKey())){
					itemLong.add(itemMap.get(entry.getKey())+"");
					returnKay.put(entry.getKey(), itemLong);
				}else{
					//封装itemImport对象
					String price = entry.getValue().get(1);
					Long itemId = Long.parseLong(entry.getValue().get(0));
					itemLong.add(itemId+"");
					itemLong.add("import");
					returnKay.put(entry.getKey(), itemLong);
					ItemImport saveItem = packagingItemImport(entry.getKey(),itemId,price,userId);
					returnValue.add(saveItem);
				}
			} catch (Exception e) {
				continue;
			}
		}
		returnMap.put(returnKay, returnValue);
		return returnMap;
	}

	/**
	 * 封装ItemImport
	 */
	private static ItemImport packagingItemImport(String key, Long itemId,
			String price,String userId) {
		ItemImport itemImport = new ItemImport();
		itemImport.setTitle(key);
		itemImport.setNick(userId);
		itemImport.setNumIid(itemId);
		itemImport.setPrice(price);
		itemImport.setApproveStatus("import");
		return itemImport;
	}

	/*
	 * 获取mapkey数据
	 */
	public static Map<String, List<String>> getMapKey(
			Map<Map<String, List<String>>, List<ItemImport>> map) {
		Map<String, List<String>> hashMap = new HashMap<String, List<String>>();
		Set<Entry<Map<String,List<String>>,List<ItemImport>>> entrySet = map.entrySet();
		for (Entry<Map<String, List<String>>, List<ItemImport>> entry : entrySet) {
			Map<String, List<String>> key = entry.getKey();
			hashMap.putAll(key);
		}
		return hashMap;
	}

	/*
	 * 获取mapvalue数据
	 */
	public static List<ItemImport> getMapValue(
			Map<Map<String, List<String>>, List<ItemImport>> map) {
		List<ItemImport> list = new ArrayList<ItemImport>();
		Set<Entry<Map<String,List<String>>,List<ItemImport>>> entrySet = map.entrySet();
		for (Entry<Map<String, List<String>>, List<ItemImport>> entry : entrySet) {
			List<ItemImport> value = entry.getValue();
			list.addAll(value);
		}
		return list;
	}
}
