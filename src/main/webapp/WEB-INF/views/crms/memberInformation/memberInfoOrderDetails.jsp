<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>客户管理-客户订单详情</title>
		<%@ include file="/common/common.jsp"%>

		<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/base.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/member-information.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/jedate.css"/>

		<script src="${ctx}/crm/js/jquery.jedate.js"></script>
		<script src="${ctx}/crm/js/member-information.js"></script>
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
			<div class="w1645 m_t70 m_l30">
				<div class="w100_ h48 lh50 bgc_fff font18">
					<div class="f_l h50 w4 bgc_1d9dd9"></div>
					<div class="f_l m_l15 c_384856">客户管理</div>
					<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">

						
						 <a class="c_384856" href="${ctx}/sellerGroup/findSellerGroup">
							<div class="f_l w140 text-center cursor_">会员分组</div>
						</a> 
						 <a class="c_384856" href="${ctx}/crms/memberInformation/memberInformation">
							<div class="f_l w140 text-center cursor_ bgc_e3e7f0">会员信息</div>
						</a>
						<a class="c_384856"
							href="${ctx}/memberInteraction/memberSmsSendAndReceIve">
							<div class="f_l w140 text-center cursor_">会员互动</div>
						</a>
						<a class="c_384856"
							href="${ctx}/crms/marketingCenter/smsBlack">
							<div class="f_l w140 text-center cursor_">黑名单列表</div>
						</a> <%-- <a class="c_384856"
							href="${ctx }/crms/customerManagement/blacklistManagemen">
							<div class="f_l w140 text-center cursor_">黑名单管理</div>
						</a> --%>
<!-- 						<a class="c_384856" -->
<%-- 							href="${ctx}/crms/customerManagement/memberGradation"> --%>
<!-- 							<div class="f_l w140 text-center cursor_"> -->
<!-- 								会员等级划分</div> -->
<!-- 						</a> -->
					</div>
				</div>
				
				<div class="w1655 h80 bgc_f1f3f7 c_384856 p_l40 p_t30">
								
								<!---------------标题--------------->
								<div class="font24 m_b10">
									客户信息详情
								</div>
								
				</div>
				
<!-- -------------------------------------------主要内容区------------------------------------------------------ -->
 <div id="customer-details">
			<h3 class="customer-detailsH3" >客户订单详情</h3>
			<ul class="customer-detailsNav">
				<li  onclick='memberInfoDetails("${decryptBuyerNick}")'>客户信息</li>
				<li  class="on" onclick='memberInfoOrderDetails("${memberDTO.buyerNick}")'>订单信息</li>
				<li onclick='memberInfoHf("${decryptBuyerNick}")'>客户回复</li>
			</ul>
			<input type="hidden" name="buyerNick" value="${decryptBuyerNick }" id="buyerNick"/>
			<div class="order-detalilsBox clearfix">
			<form action="${ctx}/crms/memberInformation/memberInfoOrderDetails" method="post" id="memberInfoSearch">
				<input type="hidden" name="buyerNick" value="${decryptBuyerNick }">
				<div class="order-detalilsSearch clearfix">
					<div class="searchDivBox clearfix">
						<label>交易时间：</label>	
						<div class="searchTime">

							<input style="border:0.052083vw solid #CAD3DF; width:9vw;" type="text" name="startTime" id="test5" value="${startTime}" readonly="true" />

							<span>~</span>


							<input style="border:0.052083vw solid #CAD3DF; width:9vw;" type="text" name="endTime" id="test6" value="${endTime}" readonly="true" />

						</div>
					</div>	
					<div class="searchDivBox clearfix">
						<label>订单状态：</label>
						<div class="nice-select" name="nice-select">
						<input id="status" type="hidden" name="status" value="${status}">
							<p>
							  <c:if test="${status==0}">全部</c:if>
							  <c:if test="${status==1}">等待买家付款</c:if>
							  <%-- <c:if test="${status==2}">买家已付款</c:if> --%>
							  <c:if test="${status==2}">等待卖家发货</c:if>
							  <c:if test="${status==3}">卖家已发货</c:if>
							  <c:if test="${status==4}">交易成功</c:if>
							  <c:if test="${status==5}">交易关闭</c:if>
							  <%-- <c:if test="${status==6}">退款中的订单</c:if> --%>
							</p>
							<ul class="member-grouping-nav" id="statusUl">
								<li value="0">全部</li>
								<li value="1">等待买家付款</li>
								<!-- <li value="2">买家已付款</li> -->
								<li value="2">等待卖家发货</li>
								<li value="3">卖家已发货</li>
								<li value="4">交易成功</li>
								<li value="5">交易关闭</li>
								<!-- <li value="6">退款中的订单</li> -->
							</ul>
						</div>
					</div>
					<a class="searchBtn" href="javascript:;" onclick="findMemberInfo('memberInfoSearch')">搜索</a>
				</div>
				</form>	
				<div class="order-detalilsTableBox">
					<table class="order-detalilsTable" border="0" cellspacing="0" cellpadding="0">
						<tr class="trone">
						    <th class="thone">订单号</th>
							<th class="thtwo">交易时间</th>
							<th class="ththree">订单状态</th>
							<th class="thfour">宝贝名称</th>
							<th class="thfour">宝贝属性</th>
							<th class="thseven">卖家标记</th>
							<th class="thfive">价格</th>
							<th class="thsix">数量</th>
							<th class="thseven">状态</th>
						</tr>
						<tr class="trHr"></tr>
						<!-- 遍历查询子订单 -->
						<c:forEach items="${pagination.datas}" var="trade">
						   <c:forEach items="${trade.orders}" var="order"> 
						   <tr class="trtwo">
						       <td>${order.tid}</td>
						       <!-- 交易时间 -->
							   <td><fmt:formatDate value='${order.createdUTC }' pattern='yyyy-MM-dd HH:mm:ss' /></td>
							   <!-- 订单状态 -->
							   <td>
								  <c:if test="${order.status=='TRADE_FINISHED'}">交易成功</c:if>
								  <c:if test="${order.status=='TRADE_NO_CREATE_PAY'}">没有创建支付宝交易</c:if>
								  <c:if test="${order.status=='WAIT_BUYER_PAY'}">等待买家付款</c:if>
								  <c:if test="${order.status=='WAIT_SELLER_SEND_GOODS'}">等待卖家发货</c:if>
								  <c:if test="${order.status=='WAIT_BUYER_CONFIRM_GOODS'}">卖家已发货</c:if>
								  <c:if test="${order.status=='TRADE_BUYER_SIGNE'}">买家已签收</c:if>
								  <c:if test="${order.status=='TRADE_CLOSED'}">交易关闭</c:if>
								  <c:if test="${order.status=='TRADE_CLOSED_BY_TAOBAO'}">交易关闭</c:if>
							  </td>
							   <td class="tdfour">${order.title}</td>
							   <td class="tdfour">${order.skuPropertiesName}</td>
							   <td class="tdfour">
							    <c:if test="${order.sellerFlag==0}">
							      <img src="${ctx}/crm/images/hui.png" alt="灰色"/>
							    </c:if>
							    <c:if test="${order.sellerFlag==1}">
							      <img src="${ctx}/crm/images/redq.png" alt="红色"/>
							    </c:if>
							    <c:if test="${order.sellerFlag==2}">
							      <img src="${ctx}/crm/images/yellowq.png" alt="黄色"/>
							    </c:if>
							    <c:if test="${order.sellerFlag==3}">
							      <img src="${ctx}/crm/images/greenq.png" alt="绿色"/>
							    </c:if>
							    <c:if test="${order.sellerFlag==4}">
							      <img src="${ctx}/crm/images/blueq.png" alt="蓝色"/>
							    </c:if>
							    <c:if test="${order.sellerFlag==5}">
							      <img src="${ctx}/crm/images/violetq.png" alt="紫色"/>
							    </c:if>
							   </td>
							  <!-- 价格 -->
							  <td>${order.payment}</td>
							  <!-- 数量 -->
							  <td>${order.num}</td>
							  <!-- 状态 -->
							   <td>
								  <c:if test="${order.status=='TRADE_FINISHED'}">交易成功</c:if>
								  <c:if test="${order.status=='TRADE_NO_CREATE_PAY'}">没有创建支付宝交易</c:if>
								  <c:if test="${order.status=='WAIT_BUYER_PAY'}">等待买家付款</c:if>
								  <c:if test="${order.status=='WAIT_SELLER_SEND_GOODS'}">等待卖家发货</c:if>
								  <c:if test="${order.status=='WAIT_BUYER_CONFIRM_GOODS'}">卖家家已发货</c:if>
								  <c:if test="${order.status=='TRADE_BUYER_SIGNE'}">买家已签收</c:if>
								  <c:if test="${order.status=='TRADE_CLOSED'}">交易关闭</c:if>
								  <c:if test="${order.status=='TRADE_CLOSED_BY_TAOBAO'}">交易关闭</c:if>
							  </td>
							 </tr>
						 </c:forEach>
						</c:forEach>
					</table>
					<!-- --------------------------------分页---------------------------------------- -->
				   <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
				   <input type="hidden" value="${totalPage}" id="totalPage" />
				   <input type="hidden" value="${pageNo }" id="pageNo"/>
		                    <c:forEach items="${pagination.pageView }" var="page">
								${page } 
						  </c:forEach>
		           </div>	
				</div>
				<a class="goblock" href="${ctx}/crms/memberInformation/memberInformation">返回</a>
			</div>
			
		</div>


<!-- -------------------------------------------主要内容区------------------------------------------------------ -->
			</div>
		</div>

	</div>
</body>
<script>
//客户信息页面跳转
function memberInfoDetails(decryptBuyerNick){
	var buyerNick = $("#buyerNick").val();
	location.href = "${ctx}/crms/memberInformation/memberInfoDetails?buyerNick="+buyerNick;
}
//客户回复页面跳转
function memberInfoHf(){
	var buyerNick = $("#buyerNick").val();
	location.href = "${ctx}/crms/memberInformation/memberInteraction?buyerNick="+buyerNick;
}

//form表单提交查询
function findMemberInfo(memberInfoSearch){
	var totalPage = $("#totalPage").val();
	var pageNo = $("#PAGENO").val();
	if(parseInt(pageNo)>parseInt(totalPage)){
		alert("输入页数不能大于总页数！");
	}else{
		document.getElementById(memberInfoSearch).submit();
	}
}
$(function(){
	var start={
		insTrigger:false,
		isTime:true,
		format:'YYYY-MM-DD hh:mm:ss',
		choosefun: function(elem, val, date){

	        end.minDate = val; //开始日选好后，重置结束日的最小日期
	    }
	};	
	var end={
		insTrigger:false,
		isTime:true,
		format:'YYYY-MM-DD hh:mm:ss', //最大日期
	    choosefun: function(elem, val, date){

	        start.maxDate = val; //将结束日的初始值设定为开始日的最大日期
	    }	
	}
	$('#test5').click(function(){
		$('#test5').jeDate(start);
	});
	$('#test6').click(function(){
		$('#test6').jeDate(end);	
	});
});
</script>
</html>