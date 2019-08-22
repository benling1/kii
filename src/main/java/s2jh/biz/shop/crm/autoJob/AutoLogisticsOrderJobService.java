package s2jh.biz.shop.crm.autoJob;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.apache.jasper.tagplugins.jstl.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.order.entity.LogisticsOrder;
import s2jh.biz.shop.crm.order.service.LogisticsOrderService;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Shipping;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.LogisticsOrdersGetRequest;
import com.taobao.api.response.LogisticsOrdersGetResponse;
@Service
@Transactional
public class AutoLogisticsOrderJobService{
	
	@Autowired
	private LogisticsOrderService logisticsOrderService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	
	private List<String> userNickList = new ArrayList<String>();
	/**
	 * 封装参数,调用淘宝接口获取数据,
	 * helei 2017年1月13日上午9:58:31
	 */
	public void executeLogisticsOrder(String time){
		//获取开始时间
		String startCreated = countStartCreated(time);
		
		//获取结束时间
		String endCreated = countEndCreated(time);
		
		//获取所以得Token,通过Token查询出物流信息
		List<UserInfo> userInfos = userInfoService.findUserInfoToken();
		
		//循环所以得Token数据查询出物流信息
		try {
			if(userInfos !=null && userInfos.size()>0){
				for (UserInfo userInfo : userInfos) {
					if(userInfo.getAccess_token() != null && !userInfo.getAccess_token().equals("")){
						//定义页码
						Long pageNo = 0L;
						//定义条数接收
						Long shippingTotal = 0L;
						do {
							pageNo++;
							shippingTotal = findShipping(null, null, null, null, null, null, null, pageNo, 100L, startCreated, endCreated, userInfo.getAccess_token(),userInfo.getTaobaoUserNick());
						} while (shippingTotal==100);
					}
				}
			}
			
			//判断userNickList是否为空如果不为空就在次查询出token调用淘宝获取物流信息
			if(userNickList != null && userNickList.size()>0){
				for (String userNick : userNickList) {
					
					if(userNick != null && !userNick.equals("")){
						String accessToken = userInfoService.findUserInfoTokens(userNick);
						if(accessToken != null && !accessToken.equals("")){
							//定义页码
							Long pageNo = 0L;
							//定义条数接收
							Long shippingTotal = 0L;
							do {
								pageNo++;
								shippingTotal = findShipping(null, null, null, null, null, null, null, pageNo, 100L, startCreated, endCreated, accessToken,null);
							} while (shippingTotal==100);
						}
					}
				}
			}
			
			
			
			
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 调用淘宝接口获取物流信息
	 * helei 2017年1月11日下午3:00:27
	 */
	private Long  findShipping( Long tid,
								String buyerNick,
								String status,
								String sellerConfirm,
								String receiverName,
								String freightPayer,
								String type,
								Long pageNo,
								Long pageSize,
								String startCreated,
								String endCreated,
								String sessionKey,
								String taobaoUserNick) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
		LogisticsOrdersGetRequest req = new LogisticsOrdersGetRequest();
		req.setFields("tid,order_code,status,is_quick_cod_order,seller_nick,buyer_nick,delivery_start,delivery_end,out_sid,item_title,receiver_name,receiver_phone,receiver_mobile,type,freight_payer,seller_confirm,company_name,is_success,created,modified,is_spilt,sub_tids,total_results");
		if(tid !=null){
			req.setTid(tid);//交易ID
		}
		if(buyerNick !=null){
			req.setBuyerNick(buyerNick);//买家昵称 
		}
		if(status !=null){
			req.setStatus(status);//物流状态.查看数据结构 Shipping 中的status字段. 
		}
		if(sellerConfirm !=null){
			req.setSellerConfirm(sellerConfirm);//卖家是否发货.可选值:yes(是),no(否).如:yes 
		}
		if(receiverName !=null){
			req.setReceiverName(receiverName);//收货人姓名
		}
		if(startCreated !=null){
			req.setStartCreated(StringUtils.parseDateTime(startCreated));//创建时间开始
		}
		if(endCreated !=null){
			req.setEndCreated(StringUtils.parseDateTime(endCreated));//创建时间结束
		}
		if(freightPayer !=null){
			req.setFreightPayer(freightPayer);//谁承担运费.可选值:buyer(买家),seller(卖家).如:buyer 
		}
		if(type !=null){
			req.setType(type);//物流方式.可选值:post(平邮),express(快递),ems(EMS).如:post 
		}
		if(pageNo !=null){
			req.setPageNo(pageNo);//页码.该字段没传 或 值<1 ,则默认page_no为1 
		}
		if(pageSize !=null){
			req.setPageSize(pageSize);//每页条数.该字段没传 或 值<1 ,则默认page_size为40   默认值：40  最大值：100
		}
		LogisticsOrdersGetResponse rsp = client.execute(req, sessionKey);
		
		//定义返回总条数;
		Long results = 0L;
		if(rsp!=null){
			//获取淘宝list数据
			List<Shipping> shippings = rsp.getShippings();
		
			//获取subCode判断是否是session过期
			String subCode = rsp.getSubCode();
			if(subCode !=null && subCode.equals("invalid-sessionkey")){
				if(taobaoUserNick!=null && !taobaoUserNick.equals("")){
					userNickList.add(taobaoUserNick);
				}
			}
			//调用方法解析数据并保存
			if(shippings !=null && shippings.size()>0){
				circulationData(shippings);
			}
			//获取总条数据
			results = rsp.getTotalResults();
			if(results==null){
				results = 0L;
			}
		}
		return results;
	}	
	
	
	/**
	 * 循环数据,封装数据,保存或修改数据
	 * helei 2017年1月12日下午5:55:56
	 */
	/**
	 * helei 2017年1月14日下午3:40:20
	 */
	public void circulationData(List<Shipping> shippings){
			
		//创建savelist封装数据,批量保存到数据库
		List<LogisticsOrder> saveList = new ArrayList<LogisticsOrder>();
		
		//创建updatelist封装数据,批量修改数据库中的数据
		/*List<LogisticsOrder> updateList = new ArrayList<LogisticsOrder>();*/
			
			//循环淘宝获取的物流信息
			for (Shipping shipping : shippings) {
				//创建LogisticsOrder对象封装数据,
				LogisticsOrder logisticsOrder = new LogisticsOrder();
				
				//将淘宝的物流信息封装到本地对象中
				logisticsOrder = shippingToLogisticsOrder(logisticsOrder, shipping);
				
				//判断该数据是保存还是修改
				int count = logisticsOrderService.findLogisticsOrderByOrderCode(logisticsOrder.getOrderCode());
				if(count == 1){
					/*logisticsOrder.setLastModifiedDate(new Date());
					updateList.add(logisticsOrder);*/
					logisticsOrderService.updateLogisticsOrder(logisticsOrder);
				}else{
					logisticsOrder.setCreatedDate(new Date());
					saveList.add(logisticsOrder);
				}
			}
			
			//调用批量保存方法
			if(saveList != null && saveList.size()>0){
				logisticsOrderService.save(saveList);
			}

			//调用批量修改方法
			/*if(updateList != null && updateList.size()>0){
				logisticsOrderService.updateLogisticsOrderList(updateList);
			}*/
		}
	
	
	
	/**
	 * 更新或添加物流订单信息
	 * helei 2017年1月12日上午11:49:16
	 */
	private void saveOrUpdateLogisticsOrder(LogisticsOrder logisticsOrder){
		int count = logisticsOrderService.findLogisticsOrderByOrderCode(logisticsOrder.getOrderCode());
		if(count == 1){
			logisticsOrderService.updateLogisticsOrder(logisticsOrder);
		}else{
			logisticsOrderService.saveLogisticsOrder(logisticsOrder);
		}
	}
	
	/**
	 * 将淘宝数据封装到本地LogisticsOrder对象中
	 * helei 2017年1月11日下午4:37:46
	 */
	private LogisticsOrder shippingToLogisticsOrder(LogisticsOrder logisticsOrder,Shipping shipping){
		logisticsOrder.setOrderCode(shipping.getOrderCode());
		logisticsOrder.setStatus(shipping.getStatus());
		logisticsOrder.setIsQuickCodOrder(shipping.getIsQuickCodOrder());
		logisticsOrder.setSellerNick(shipping.getSellerNick());
		logisticsOrder.setBuyerNick(shipping.getBuyerNick());
		logisticsOrder.setDeliveryStart(shipping.getDeliveryStart());
		logisticsOrder.setDeliveryEnd(shipping.getDeliveryEnd());
		logisticsOrder.setOutSid(shipping.getOutSid());
		logisticsOrder.setItemTitle(shipping.getItemTitle());
		logisticsOrder.setReceiverName(shipping.getReceiverName());
		logisticsOrder.setReceiverPhone(shipping.getReceiverPhone());
		logisticsOrder.setReceiverMobile(shipping.getReceiverMobile());
		logisticsOrder.setType(shipping.getType());
		logisticsOrder.setFreightPayer(shipping.getFreightPayer());
		logisticsOrder.setSellerConfirm(shipping.getSellerConfirm());
		logisticsOrder.setCompanyName(shipping.getCompanyName());
		logisticsOrder.setIsSuccess(shipping.getIsSuccess());
		logisticsOrder.setCreated(shipping.getCreated());
		logisticsOrder.setModified(shipping.getModified());
		logisticsOrder.setIsSpilt(shipping.getIsSpilt());
		if(shipping.getSubTids()!=null && !shipping.getSubTids().equals("")){
			logisticsOrder.setSubTids(jointSubTids(shipping.getSubTids()));
		}	
		logisticsOrder.setTid(shipping.getTid());
		return logisticsOrder;
	}
	
	/**
	 * 计算出当前时间
	 * helei 2017年1月11日下午3:02:39
	 */
	private String countEndCreated(String time){
		Calendar calendar = Calendar.getInstance();//获取的是系统当前时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		String endCreated=sdf.format(calendar.getTime());
		return endCreated+" "+time;
	}

	/**
	 * 计算出
	 * helei 2017年1月11日下午3:04:56
	 */
	private String countStartCreated(String time){
		Calendar calendar = Calendar.getInstance();//获取的是系统当前时间
		calendar.add(Calendar.DATE, -1);
		String startCreated = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		return startCreated+" "+time;
	}
	
	/**
	 * 将list中的long数据拼接为字符串
	 * helei 2017年1月11日下午3:43:27
	 */
	private String jointSubTids(List<Long> longs){
		String subTids = "";
		for (Long subTid : longs) {
			subTids+=subTid+",";
		}
		if(subTids != null && !"".equals(subTids)){
			subTids = subTids.substring(0, subTids.length()-1);
		}
		return subTids;
	}
}
