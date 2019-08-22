<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
	<title>店铺数据-订单列表</title>
	<%@ include file="/common/common.jsp"%>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		
	<!--兼容360meta-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta name="renderer" content="webkit">

	
		
    <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
    <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

	<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js" ></script> --%>
	<script type="text/javascript" src="${ctx}/crm/js/order_list.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/util.js"></script>	
</head>
<body>
	<%@ include file="/WEB-INF/views/crms/header/top.jsp"%>
<%@ include file="/WEB-INF/views/crms/header/header.jsp"%>
	<div class="w1903 h100_">
			<!-------------------------左部，侧边栏------------------------->
			<div class="load_left"></div>
	
	
			<!-------------------------右部------------------------->
			<div class="w1703 m_l200">
				<!--------------------顶部导航栏-------------------->
				<div class="load_top"></div>
				
				
				
				<!--------------------主要内容区-------------------->
				<div class="w1645 m_l30">
					<div class="w100_ h48 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							店铺数据
						</div>
						<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
							<a class="c_384856" href="${ctx}/crms/storeData/todyOrderList">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									订单列表
								</div>
							</a>
							<a class="c_384856" href="${ctx}/OrderHistoryImport/showLoadHistoryOrder">
								<div class="f_l w140 text-center cursor_">
									历史订单导入
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/shopData/smsSendRecord">
								<div class="f_l w140 text-center cursor_">
									短信发送记录
								</div>
							</a>
							<a class="c_384856" href="${ctx}/shopData/reportJsp">
								<div class="f_l w140 text-center cursor_">
									短信账单
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/storeData/todyData">
								<div class="f_l w140 text-center cursor_">
									历史数据
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/storeData/operationLog">
								<div class="f_l w140 text-center cursor_ ">
									操作日志
								</div>
							</a>
						</div>
					</div>
					
					
					<!---------------订单列表--------------->
					<div class="">
						<!----------上部---------->
						<div class="w1605 h100 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								订单列表
							</div>
							<!---------------描述--------------->
							<div class="font14">
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1605 m_b30 bgc_fff p_t35 p_l40 p_b40">
							<input id="msg" name="msg" value="${msg}" type="hidden">
							<!--------查询设置-------->
							<form id="queryForm" action="${ctx}/crms/todyOrderList" method="post">
							<div class="font14 c_384856 h47 lh45 m_b35">
								<div class="f_l m_r15">
									时间范围:
								</div>
								<div class="wrap f_l h45 lh45 m_r15">
								    <div class="nice-select h45 lh45" name="nice-select" id="big_timeType">
								    	<input class="h45 lh45" type="text" value="下单时间" readonly id="show_TimeType">
								    	<ul id="_timeType">
								    		<!-- <li value="createdDate">下单时间</li>
								    		<li value="payDate">付款时间</li>
								      		<li value="sendDate">发货时间</li>
								    		<li value="modifyDate">变更时间</li>
								    		<li value="endDate">结束时间</li> -->
<!-- 								    		<li value="0">--全部--</li> -->
								    		<li value="1">下单时间</li>
								    		<!-- <li value="2">付款时间</li> -->
								      		<li value="3">发货时间</li>
								    		<!-- <li value="4">变更时间</li>
								    		<li value="5">结束时间</li> -->
								    	</ul>
								    	<input type="hidden" name="timeType" value="created" id="timeType"/>
								  	</div>
								</div>
								
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" id="tser01" value="beginTime"  name="beginTime" readonly onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val) {valiteTwoTime();}})">
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
									
								<div class="f_l">~</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" id="tser02" value="endTime"  name="endTime" readonly onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val) {valiteTwoTime();}})">
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								
								
								
								
								<div onclick="submitForm('queryForm');" class="w100 h45 lh45 b_radius5 tk c_00a0e9 border_00a0e9 text-center f_l cursor_ bgc_fff">
									查询
								</div>
								<!-- <input class="w100 h45 lh45 b_radius5 tk c_00a0e9 border_00a0e9 text-center f_l cursor_ bgc_fff" type="submit" value="查询"> -->
								
							</div>
							
							
							<div class="h45 m_b40">
								
										<div class="h45 lh45 f_l c_384856 m_r15">
											买家昵称:
										</div>
										<input id="buyerNick" class="bgc_f4f6fa border0 w250 h45 lh45 f_l m_r40" name="buyerNick" />
										
										<div class="h45 lh45 f_l c_384856 m_r15 m_l40">
											订单编号:
										</div>
										<input id="orderId" class="bgc_f4f6fa border0 w250 h45 lh45 f_l m_r70" name="orderId" value="${orderId }"/>
										<div  class="h45 lh45 f_l" id="orderIdMSG">
										</div>
										<!-- <div class="h45 lh45 f_l c_00a0e9 more_check cursor_">
											更多》
										</div> -->
										
										<!-- <div class="f_l m_l60 m_t10 display_none"  id="nnone" >
											<font color="red">时间范围与订单状态只能选择一个作为筛选条件!!</font>
										</div> -->
										
							</div>
							
							<div id="ddzt" class="more_check_">
								<div class="h22 font14 c_384856">
									
									<div class="f_l h22 lh22 m_r10">
										订单状态：
									</div>
									<input type="hidden" name="status" value="${status}" id="status" />
									<ul class=" h22 lh22 f_l">
									<%-- 	<li class="h22 lh22 f_l m_r20 ">
									    	<div class="cursor_ all_check_after w20 h20 border_d9dde5 b_radius5 f_l" id="TRADE_NO_CREATE_PAY">
									    		<img class="cursor_ w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		全选
									    	</div>
										</li> --%>
										<li class="h22 lh22 f_l m_r20" onclick="TRADE_NO_CREATE_PAY()">
									    	<div class="cursor_ check_out w20 h20 border_d9dde5 b_radius5 f_l" id="TRADE_NO_CREATE_PAY">
									    		<img class="cursor_ check_in w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<input type="hidden" value="TRADE_NO_CREATE_PAY" />
									    	<div class="f_l text-left m_l10">
									    		没有创建支付宝交易
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20" onclick="WAIT_BUYER_PAY()">
									    	<div class="cursor_ check_out w20 h20 border_d9dde5 b_radius5 f_l" id="WAIT_BUYER_PAY">
									    		<img class="cursor_ check_in w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<input type="hidden" value="WAIT_BUYER_PAY" />
									    	<div class="f_l text-left m_l10">
									    		等待买家付款
									    	</div>
										</li>
										<%-- <li class="h22 lh22 f_l m_r20" >
									    	<div class="cursor_  w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ check_in w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		卖家部分发货(此功能暂时无法执行)
									    	</div>
										</li> --%>
										<li class="h22 lh22 f_l m_r20" onclick="WAIT_SELLER_SEND_GOODS()">
									    	<div class="cursor_ check_out w20 h20 border_d9dde5 b_radius5 f_l" id="WAIT_SELLER_SEND_GOODS">
									    		<img class="cursor_ check_in w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<input type="hidden" value="WAIT_SELLER_SEND_GOODS" />
									    	<div class="f_l text-left m_l10">
									    		等待卖家发货
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20" onclick="WAIT_BUYER_CONFIRM_GOODS()">
									    	<div class="cursor_ check_out w20 h20 border_d9dde5 b_radius5 f_l" id="WAIT_BUYER_CONFIRM_GOODS">
									    		<img class="cursor_ check_in w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<input type="hidden" value="WAIT_BUYER_CONFIRM_GOODS" />
									    	<div class="f_l text-left m_l10">
									    		等待买家确认收货
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20" onclick="TRADE_BUYER_SIGNED()">
									    	<div class="cursor_ check_out w20 h20 border_d9dde5 b_radius5 f_l" id="TRADE_BUYER_SIGNED">
									    		<img class="cursor_ check_in w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<input type="hidden" value="TRADE_BUYER_SIGNED" />
									    	<div class="f_l text-left m_l10">
									    		买家已签收
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20" onclick="TRADE_FINISHED()">
									    	<div class="cursor_ check_out w20 h20 border_d9dde5 b_radius5 f_l" id="TRADE_FINISHED">
									    		<img class="cursor_ check_in w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<input type="hidden" value="TRADE_FINISHED" />
									    	<div class="f_l text-left m_l10">
									    		交易成功
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20" onclick="TRADE_CLOSED()">
									    	<div class="cursor_ check_out w20 h20 border_d9dde5 b_radius5 f_l" id="TRADE_CLOSED">
									    		<img class="cursor_ check_in w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<input type="hidden" value="TRADE_CLOSED" />
									    	<div class="f_l text-left m_l10">
									    		已退款
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20" onclick="TRADE_CLOSED_BY_TAOBAO()">
									    	<div class="cursor_ check_out w20 h20 border_d9dde5 b_radius5 f_l" id="TRADE_CLOSED_BY_TAOBAO">
									    		<img class="cursor_ check_in w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<input type="hidden" value="TRADE_CLOSED_BY_TAOBAO" />
									    	<div class="f_l text-left m_l10">
									    		付款前，交易被关闭
									    	</div>
										</li>
									</ul>
								</div>
								
								
								
								
								<div class="h22 font14 c_384856 m_t30 m_b35">
									
									<div class="f_l h22 lh22 m_r10">
										订单来源：
									</div>
									
									<ul class=" h22 lh22 f_l">
										<%-- <li class="h22 lh22 f_l " onclick="hitao()" style="margin-right:2.094vw;">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l" id="_hitao" >
									    		<img class="cursor_ group_check_ w20 h20 display_none"  src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		嗨淘
									    	</div>
									    	<input name="orderFrom" type="hidden" value="" id="hitao"/>
										</li> --%>
										<%-- <li class="h22 lh22 f_l m_r63" onclick="jhs()">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l" id="_jhs">
									    		<img class="cursor_ group_check_ w20 h20 display_none"  src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		聚划算
									    	</div>
									    	<input name="orderFrom" type="hidden" value="" id="jhs"/>
										</li> --%>
										<li class="h22 lh22 f_l m_r48" onclick="taobao()">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l" id="_taobao">
									    		<img class="cursor_ group_check_ w20 h20 display_none"  src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		普通淘宝
									    	</div>
									    	<input name="orderFrom" type="hidden" value="" id="taobao"/>
										</li>
										<%-- <li class="h22 lh22 f_l m_r48" onclick="topp()">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l" id="_topp" >
									    		<img class="cursor_ group_check_ w20 h20 display_none"  src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		TOP平台
									    	</div>
									    	<input name="orderFrom" type="hidden" value="" id="topp"/>
										</li> --%>
										<li class="h22 lh22 f_l m_r63" onclick="wap()">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l" id="_wap" >
									    		<img class="cursor_ group_check_ w20 h20 display_none"  src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		手机
									    	</div>
									    	<input name="orderFrom" type="hidden" value="" id="wap"/>
										</li>
										<li class="h22 lh22 f_l m_r63" onclick="importData()">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l" id="_importData" >
									    		<img class="cursor_ group_check_ w20 h20 display_none"  src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		导入
									    	</div>
									    	<input name="orderFrom" type="hidden" value="" id="importData"/>
										</li>
										<%-- <li class="h22 lh22 f_l">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		历史订单导入
									    	</div>
										</li> --%>
									</ul>
								</div>
							</div>	
							</form>
							
							
							<p class="c_ff6363 font14 m_b10">
								温馨提示：为确保数据安全性及更高安全机制，订单查询中手机号现做模糊化处理，短信发送查询均不受影响。
							</p>
							
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="w75">序号</th>
								    <th class="w105">店铺名称</th>
								    <th class="w145">订单编号</th>
								    <th class="w105">订单状态</th>
								    <th class="w105" id="changeTime">下单时间</th>
								    <th class="w110">实付金额(元)</th>
								    <th class="w100">买家昵称</th>
								    <th class="w85">收货人姓名</th>
								    <th class="w105">收货人手机</th>
								    <th class="w120">收货人所在城市</th>
								   <!--  <th class="w110">所购产品类别</th> -->
								    <th class="w100">所购产品</th>
								    <th class="w100">订单来源</th>
								  </tr>
								 
								  <!-- 后台数据 -->
								  <c:if test="${pagination.datas.size() ==0 }">
									  <tr class="">
									    <td class="w75 text-center" colspan="13">没有相关数据,请重新查询或更改相关筛选条件!</td>
									  </tr>
								  </c:if>
								  
								  <c:if test="${pagination.datas.size() !=0 }">
								  	<c:forEach items="${pagination.datas }" var="ordersList" varStatus="status">
									  <tr class="">
									    <td class="w75 text-center">${status.index+1 }</td>
									    <td class="w105 text-center">${ShopName}</td>
									    <td class="w145 text-center">${ordersList.tid }</td>
									    <td class="w105 text-center">
									    <ul>
										    <c:if test="${ordersList.status =='TRADE_NO_CREATE_PAY'}"><li >没有创建支付宝交易</li></c:if>
										    <c:if test="${ordersList.status == 'WAIT_BUYER_PAY'}"><li >等待买家付款</li></c:if>
										    <c:if test="${ordersList.status == 'WAIT_SELLER_SEND_GOODS' }"><li >等待卖家发货</li></c:if>
										    <c:if test="${ordersList.status == 'WAIT_BUYER_CONFIRM_GOODS'}"><li >等待买家确认收货</li></c:if>
										    <c:if test="${ordersList.status == 'TRADE_BUYER_SIGNED' }"><li >买家已签收</li></c:if>
										    <c:if test="${ordersList.status == 'TRADE_FINISHED' }"><li >交易成功</li></c:if>
										    <c:if test="${ordersList.status == 'TRADE_CLOSED' }"><li >已退款</li></c:if>
										    <c:if test="${ordersList.status == 'TRADE_CLOSED_BY_TAOBAO' }"><li >付款前，交易被关闭</li></c:if>
										    <c:if test="${ordersList.status == 'PAY_PENDING' }"><li >国际信用卡支付付款确认中</li></c:if>
									    </ul>
									    </td>
									    <td class="w105 text-center">
									    <ul>
									    	<c:if test="${empty timeType}"><li > <fmt:formatDate value="${ordersList.createdUTC }" pattern="yyyy-MM-dd HH:mm:ss" /></li></c:if>
										    <c:if test="${timeType =='created' }"><li > <fmt:formatDate value="${ordersList.createdUTC }" pattern="yyyy-MM-dd HH:mm:ss" /></li></c:if>
										    <c:if test="${timeType =='payTime' }"><li > <fmt:formatDate value="${ordersList.payTimeUTC }" pattern="yyyy-MM-dd HH:mm:ss" /></li></c:if>
										    <c:if test="${timeType =='consignTtime' }"><li > <fmt:formatDate value="${ordersList.consignTimeUTC }" pattern="yyyy-MM-dd HH:mm:ss" /></li></c:if>
										    <c:if test="${timeType =='modified' }"><li > <fmt:formatDate value="${ordersList.modifiedUTC }" pattern="yyyy-MM-dd HH:mm:ss" /></li></c:if>
										    <c:if test="${timeType =='endTime' }"><li > <fmt:formatDate value="${ordersList.endTimeUTC }" pattern="yyyy-MM-dd HH:mm:ss" /></li></c:if>
										    </ul>
									    </td>
									    <td class="w110 text-center">${ordersList.payment }</td>
									    <td class="w100 text-center">${ordersList.buyerNick }</td>
									    <td class="w85 text-center">${ordersList.receiverName }</td>
									    <td class="w105 text-center">${ordersList.receiverMobile }</td>
									    <td class="w120 text-center">${ordersList.receiverCity }</td>
<%-- 									    <td class="w110 text-center">${ordersList.productName }</td> --%>
									    <td class="w100 text-center">
									    	
									    	<div style="word-break: break-all;" class="w100 margin0_auto h50 lh50 one_line_only text_detail">
												${ordersList.orders[0].title }
											</div>
									    	
									    </td>
									    <td class="w100 text-center">
									    	  <c:if test="${ordersList.orders[0].orderFrom =='WAP,WAP'}">手机</c:if>
									    	  <c:if test="${ordersList.orders[0].orderFrom =='WAP'}">手机</c:if>
									    	  <c:if test="${ordersList.orders[0].orderFrom =='TAOBAO'}">普通淘宝</c:if>
									    	  <c:if test="${ordersList.orders[0].orderFrom =='JHS'}">聚划算</c:if>
									    	  <c:if test="${ordersList.orders[0].orderFrom =='HITAO'}">嗨淘</c:if>
									    	  <c:if test="${ordersList.orders[0].orderFrom =='TOPP'}">TOP平台</c:if>
									    	  <c:if test="${ordersList.orders[0].orderFrom =='import'}">导入</c:if>
									    </td>
									  </tr>
								 	 </c:forEach>
								  </c:if>
								  
								</table>
							</div>
							
							
							<!--------分页-------->
                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                             <img class="sdg" src="${ctx}/crm/images/yu-jiazai.gif"/>
                              <!--   <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
                                <div class="f_r w470 h24 l_h22 font12">
                                    <div class="f_l w45 h22 bor_e0e6ef text-center m_r4 cursor_">上一页</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 bgc_00a0e9 c_fff cursor_">1</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">2</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">3</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">4</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">5</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">6</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">7</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">8</div>
                                    <div class="f_l w22 h22 text-center m_r4">...</div>
                                    <div class="f_l w45 h22 bor_e0e6ef text-center m_r20 cursor_">下一页</div>
                                    <div class="f_l w45 h22 m_r4"><input class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8" type="text" value=""></div>
                                    <div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
                                </div> -->
                                  <c:forEach items="${pagination.pageView }" var="page">
										${page } 
								  </c:forEach>
                            </div>
							
						</div>
					
					
					</div>
						
					
				</div>	
						
				</div>
		
			</div>
	
	</div>


	
				
</body>
<script>
	$(function(){
		$(".text_detail").click(function(){
			$(".text_detail").addClass("one_line_only lh50 h50");
			$(this).removeClass("one_line_only lh50 h50");
		});
		
	/* 	$(".all_check_after").click(function(){
			if(   $(this).children("img").is(":visible")   ){
				$(this).children("img").hide();
				$(this).parent().nextAll("li").children().find(".check_in").hide();
			}else{
				$(this).children("img").show();
				$(this).parent().nextAll("li").children().find(".check_in").show();
			};
		});
		 */
		
		 //订单状态选择为多选及样式的添加,复制给隐藏域
		$(".check_out").click(function(){
			if(   $(this).children("img").is(":visible")   ){
				$(this).children("img").hide();
				$(".all_check_after").children("img").hide();
			}else{
				$(this).children("img").show();
			};
			var status = "";
			var checkShow = $(".check_in");
			for(var i=0;i<checkShow.length;i++){
				if(checkShow.eq(i).is(":visible")){
					 var val = checkShow.eq(i).parent().next("input").val();
					 status+=val+",";
				}
			}
			status=status.substring(0,status.length-1);
			$("#status").val(status);
		});
		
		
		var timeType = "${timeType}";
		if(timeType=="created"){
			$("#show_TimeType").val("下单时间");
			$("#changeTime").html("下单时间");
		}else if(timeType=="payTime"){
			$("#show_TimeType").val("付款时间");
			$("#changeTime").html("付款时间");
		}else if(timeType=="consignTtime"){
			$("#show_TimeType").val("发货时间");
			$("#changeTime").html("发货时间");
		}else if(timeType=="modified"){
			$("#show_TimeType").val("变更时间");
			$("#changeTime").html("变更时间");
		}else if(timeType=="endTime"){
			$("#show_TimeType").val("结束时间");
			$("#changeTime").html("结束时间");
		}
		if(timeType ==null || timeType ==""){
			$("#timeType").val("created");
		}else{
			$("#timeType").val(timeType);
		}
		var beginTime = "${beginTime}";
		$("#tser01").val(beginTime);
		var endTime = "${endTime}";
		$("#tser02").val(endTime);
		var orderId = "${orderId}";
		$("#orderId").val(orderId);
		var buyerNick = "${buyerNick}";
		$("#buyerNick").val(buyerNick);

		//显示订单状态的勾选
		var orderState = $("#status").val();
		if(orderState!=null && orderState != ""){
			var statusArry = orderState.split(",");
			for(var i =0;i<statusArry.length;i++){
				if(statusArry[i]=="TRADE_NO_CREATE_PAY"){
					$("#TRADE_NO_CREATE_PAY").children('img').show();
				}
				if(statusArry[i]=="WAIT_BUYER_PAY"){
					$("#WAIT_BUYER_PAY").children('img').show();
				}
				if(statusArry[i]=="WAIT_SELLER_SEND_GOODS"){
					$("#WAIT_SELLER_SEND_GOODS").children('img').show();
				}
				if(statusArry[i]=="TRADE_CLOSED_BY_TAOBAO"){
					$("#TRADE_CLOSED_BY_TAOBAO").children('img').show();
				}
				if(statusArry[i]=="TRADE_CLOSED"){
					$("#TRADE_CLOSED").children('img').show();
				}
				if(statusArry[i]=="TRADE_FINISHED"){
					$("#TRADE_FINISHED").children('img').show();
				}
				if(statusArry[i]=="TRADE_BUYER_SIGNED"){
					$("#TRADE_BUYER_SIGNED").children('img').show();
				}
				if(statusArry[i]=="WAIT_BUYER_CONFIRM_GOODS"){
					$("#WAIT_BUYER_CONFIRM_GOODS").children('img').show();
				}
			}
		}
		
		var orderFrom = "${orderFrom}";
		var of;
		if(orderFrom!=null){
			of = orderFrom.split(",");
		}
		if(of.length>1){
			$("#ddzt").show();
		}
		for(var i =0;i<of.length;i++){
			if(of[i]=="hitao"){
				$("#_hitao").children('img').show();
				$("#hitao").val("hitao");
			}
			if(of[i]=="jhs"){
				$("#_jhs").children('img').show();
				$("#jhs").val("jhs");
			}
			if(of[i]=="TAOBAO"){
				$("#_taobao").children('img').show();
				$("#taobao").val("TAOBAO");
			}
			if(of[i]=="top"){
				$("#_topp").children('img').show();
				$("#topp").val("top");
			}
			if(of[i]=="WAP"){
				$("#_wap").children('img').show();
				$("#wap").val("WAP,WAP");
			}
			if(of[i]=="import"){
				$("#_importData").children('img').show();
				$("#importData").val("import");
			}
		}
		
		//加载转圈圈
		var msgVal = $('#msg').val();
		if(msgVal == 1){
			$(".sdg").show();
			location.href = "${ctx}/crms/todyOrderList";
		}
	});
	
	//选中查询中的时间类型并将列表表头中的字段替换掉
	 window.onload = function(){
			$("#orderId").on("keyup",function(){
				this.value=this.value.replace(/\D/g,'');
			});		
// 			$("#orderId").on("change",function(){
// 				var orderIdValue = $("#orderId").val();
// 				var number = /^\d+$/;
// 				if(number.test(orderIdValue)){
// 					$("#orderIdMSG").html("");
// 					//console.log("是数字");
// 				}else{
// 					$("#orderId").val("");
// 					$("#orderIdMSG").html("<font style='color: red;'>订单编号必须为纯数字组成！&nbsp;&nbsp;</font>");
// 					//console.log("不是数字");
// 				}
// 			});
			//隐藏预加载图标
			$(".sdg").hide();
			
			//读取第一个ul中选中的值 并赋值到第一个弹窗的form中的<input type="hidden"中
	        var obj_lis = document.getElementById("_timeType").getElementsByTagName("li");
	        for(i=0;i<obj_lis.length;i++){
	            obj_lis[i].onclick = function(){
	            	//alert($(this).val());
	            	//alert($(this).html());
	            	//$("#timeType").val($(this).val()==0?"":$(this).val());
	            	if($(this).val()==0){
		            	$("#timeType").val("");
	            	}
	            	else if($(this).val()==1){
		            	$("#timeType").val("created");
	            	}
	            	else if($(this).val()==2){
		            	$("#timeType").val("payTime");
	            	}
	            	else if($(this).val()==3){
		            	$("#timeType").val("consignTtime");
	            	}
	            	else if($(this).val()==4){
		            	$("#timeType").val("modified");
	            	}
	            	else if($(this).val()==5){
		            	$("#timeType").val("endTime");
	            	}else{
		            	$("#timeType").val("");	
	            	}
	            	//将表头中的时间类型清空
	            	//clearStatus();
	            }
	        }
	    }
	

	
	//点击订单来源时赋值
	function hitao(){
		//alert($("#_hitao").children('img').attr('style'));
		if($("#_hitao").children('img').attr('style') == "display: inline;"){
			$("#hitao").val("hitao");
		}else{
			$("#hitao").val("");
		}
	}
	function jhs(){
		//alert($("#_jhs").children('img').attr('style'));
		if($("#_jhs").children('img').attr('style') == "display: inline;"){
			$("#jhs").val("jhs");
		}else{
			$("#jhs").val("");
		}
	}
	function taobao(){
		//alert($("#_taobao").children('img').attr('style'));
		if($("#_taobao").children('img').attr('style') == "display: inline;"){
			$("#taobao").val("TAOBAO");
		}else{
			$("#taobao").val("");
		}
	}
	
	//使用top时 异常!
	function topp(){
		if($("#_topp").children('img').attr('style') == "display: inline;"){
			$("#topp").val("top");
		}else{
			$("#topp").val("");
		}
	}
	
	function wap(){
		if($("#_wap").children('img').attr('style') == "display: inline;"){
			$("#wap").val("WAP,WAP");
		}else{
			$("#wap").val("");
		}
	}
	
	function importData(){
		if($("#_importData").children('img').attr('style') == "display: inline;"){
			$("#importData").val("import");
		}else{
			$("#importData").val("");
		}
	}
	
	//点击订单状态是赋值
	/* //没有创建支付宝交易
	function TRADE_NO_CREATE_PAY(){
		//点击订单状态是显示提示消息
		$("#nnone").removeClass("display_none").addClass("display_inline");
		if($("#TRADE_NO_CREATE_PAY").children('img').attr('style') == "display: inline;"){
			$("#status").val("TRADE_NO_CREATE_PAY");
		}else{
			$("#status").val("");
		}
		//清空时间范围的选中的值
		clearUl();
	}
	//已下单未付款
	function WAIT_BUYER_PAY(){
		$("#nnone").removeClass("display_none").addClass("display_inline");
		if($("#WAIT_BUYER_PAY").children('img').attr('style') == "display: inline;"){
			$("#status").val("WAIT_BUYER_PAY");
		}else{
			$("#status").val("");
		}
		clearUl();
	}
	//等待卖家发货
	function WAIT_SELLER_SEND_GOODS(){
		$("#nnone").removeClass("display_none").addClass("display_inline");
		if($("#WAIT_SELLER_SEND_GOODS").children('img').attr('style') == "display: inline;"){
			$("#status").val("WAIT_SELLER_SEND_GOODS");
		}else{
			$("#status").val("");
		}
		clearUl();
	}
	//卖家已发货,等待买家签收
	function WAIT_BUYER_CONFIRM_GOODS(){
		$("#nnone").removeClass("display_none").addClass("display_inline");
		if($("#WAIT_BUYER_CONFIRM_GOODS").children('img').attr('style') == "display: inline;"){
			$("#status").val("WAIT_BUYER_CONFIRM_GOODS");
		}else{
			$("#status").val("");
		}
		clearUl();
	}
	//买家已签收
	function TRADE_BUYER_SIGNED(){
		$("#nnone").removeClass("display_none").addClass("display_inline");
		if($("#TRADE_BUYER_SIGNED").children('img').attr('style') == "display: inline;"){
			$("#status").val("TRADE_BUYER_SIGNED");
		}else{
			$("#status").val("");
		}
		clearUl();
	}
	//交易成功
	function TRADE_FINISHED(){
		$("#nnone").removeClass("display_none").addClass("display_inline");
		if($("#TRADE_FINISHED").children('img').attr('style') == "display: inline;"){
			$("#status").val("TRADE_FINISHED");
		}else{
			$("#status").val("");
		}
		clearUl();
	}
	//付款以后用户退款成功，交易自动关闭
	function TRADE_CLOSED(){
		$("#nnone").removeClass("display_none").addClass("display_inline");
		if($("#TRADE_CLOSED").children('img').attr('style') == "display: inline;"){
			$("#status").val("TRADE_CLOSED");
		}else{
			$("#status").val("");
		}
		clearUl();
	}
	//付款以前，卖家或买家主动关闭交易)
	function TRADE_CLOSED_BY_TAOBAO(){
		$("#nnone").removeClass("display_none").addClass("display_inline");
		if($("#TRADE_CLOSED_BY_TAOBAO").children('img').attr('style') == "display: inline;"){
			$("#status").val("TRADE_CLOSED_BY_TAOBAO");
		}else{
			$("#status").val("");
		}
		clearUl();
	}
	 */
	//清空ul列表
	 var clearUl = function(){
		//清空时间范围的隐藏域 的值
		$("#timeType").val("")
		//清空时间选项的值
		$("#tser01").val("");
		$("#tser02").val("");
		
		
		/* $("#_timeType").html(
				"<li value='0'>--全部--</li>"
	    		+"<li value='1'>下单时间</li>"
	    		+"<li value='2'>付款时间</li>"
	      		+"<li value='3'>发货时间</li>"
	    		+"<li value='4'>修改时间</li>"
	    		+"<li value='5'>结束时间</li>"); */
		
	}
	//清空订单状态
	var clearStatus = function(){
		//将订单状态的值清空
    	$("#status").val("");
    	$("#TRADE_NO_CREATE_PAY").children('img').css("display","none");
    	$("#WAIT_BUYER_PAY").children('img').css("display","none");
    	$("#WAIT_SELLER_SEND_GOODS").children('img').css("display","none");
    	$("#WAIT_BUYER_CONFIRM_GOODS").children('img').css("display","none");
    	$("#TRADE_BUYER_SIGNED").children('img').css("display","none");
    	$("#TRADE_FINISHED").children('img').css("display","none");
    	$("#TRADE_CLOSED").children('img').css("display","none");
    	$("#TRADE_CLOSED_BY_TAOBAO").children('img').css("display","none");
	}
	
	
	//提交查询表单
	function submitForm(formId){
		var timeType = $("#show_TimeType").val();
		var test01 = $("#tser01").val();
		var tser02=$("#tser02").val();
		if(timeType == "发货时间"){
			if(test01=="" || test01==null || tser02=="" || tser02==null){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("请选择时间范围！！！")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
			}else{
				document.getElementById(formId).submit();
			}
		}else{
			document.getElementById(formId).submit();
		}
	}
</script>
</html>