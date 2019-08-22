package s2jh.biz.shop.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * 订单导入文件的头信息比对工具类
 */
public class OrderHeaderUtil {

	private static Set<String> headers = new HashSet<String>(){
		private static final long serialVersionUID = 1L;
		{
			add("订单编号");add("买家会员名");	add("买家应付货款");add("买家应付邮费");
			add("总金额");add("买家实际支付金额");add("订单状态");add("买家留言");
			add("收货人姓名");add("收货地址");add("联系电话");	add("联系手机");
			add("订单创建时间");add("订单付款时间");add("宝贝标题");add("宝贝总数量");
			add("店铺名称");
		}
	};
	
	public static Set<String> getHeader(){
		return headers;
	}
}
