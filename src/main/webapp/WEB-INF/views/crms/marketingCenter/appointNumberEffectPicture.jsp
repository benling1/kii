<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>效果分析</title>
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
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/util.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/highcharts_memberixaoguofenxi.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/highcharts.js" ></script>
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
				<div class="w1645  m_l30">
					<div class="w100_ lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							营销中心
						</div>
						<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
							<a class="c_384856" href="${ctx}/marketingCenter">
								<div class="f_l w140 text-center cursor_">
									会员短信群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/smsSendAppoint/appointNumberSend">
								<div class="f_l w140 text-center cursor_ ">
									指定号码群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/marketingCenter/list?label=1">
								<div class="f_l w140 text-center cursor">
									订单短信群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/msgSendRecord/memberSendRecord">
								<div class="f_l w140 text-center cursor_">
									短信发送记录
								</div>
							</a>
							<a class="c_384856" href="${ctx}/member/msgSendRecord">
								<div class="f_l w200 text-center cursor__">
									会员短信群发效果分析
								</div>
							</a>	
							<a class="c_384856" href="${ctx}/appointNumber/msgSendRecord">
								<div class="f_l w200 text-center cursor__ bgc_e3e7f0">
									指定号码发送效果分析
								</div>
							</a>
							<a class="c_384856" href="${ctx}/order/msgSendRecord">
								<div class="f_l w200 text-center cursor__">
									订单短信群发效果分析
								</div>
							</a>
						</div>
						<div class="clear"></div>
					</div>
					
					
					<!---------------指定号码群发--------------->
					<div class="" id="windowwidth">
						<!----------上部---------->
						<div class="w1605 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!-- -------------标题------------- -->
							<div class="font24 m_b10">
								指定号码群发效果分析
							</div>
							<!-- -------------描述------------- -->
							<div class="font14 m_b20">
								帮助商家更好的了解短信发送的效果，以及根据效果制定新的营销活动
							</div>
						</div>
				
				<!----------下部---------->
				<a href="${ctx}/appointNumber/msgSendRecord">
					<div class="font16 f_l h50 lh50 bgc_e3e7f0 out c_384856 w140 h50 cursor_ one text-center">
						指定号码发送群发列表
					</div>
				</a>
				<div class="h50 lh50 f_l hide_this">
					<!-- <div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
						活动名称
					</div> -->
					<a href="${ctx}/appointNumber/effectIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_fff cursor_ text-center">
							效果分析
						</div>
					</a>
					<a href="${ctx}/appointNumber/customerIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
							客户详情
						</div>
					</a>
					<a href="${ctx}/appointNumber/itemIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
							商品详情
						</div>
					</a>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				
				<div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in position_relative three">
					
					
					
					<div style="line-height: 2.5vw;">
						<form id="formId" action="${ctx}/appointNumber/effectIndex" method="post">
						<div class="f_l">
							订单来源：
							<input type="hidden" id="orderSource" name="orderSource" value="${orderSource}">
							<input type="hidden" name="type" value="33">
						</div>
						<div class="wrap f_l h50 lh50 w140"style="margin: 0 1vw;">
							<div class="nice-select h50 lh50 w117 z_3"
								name="nice-select">
								<input id="orderSourceId" readonly="readonly"
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="全部">
								<ul id="orderSourceUl">
									<li value="0">全部</li>
									<li value="WAP,WAP">手机端</li>
									<li value="TAOBAO">PC端</li>
								</ul>
							</div>
						</div>
						<div class="f_l">
							效果分析日期：
							<input type="hidden" id="dayNum" name="dayNum" value="${dayNum}">
						</div>
						<div class="f_l position_relative">
							<P style="height:2.604166vw;width:10vw; line-hieght:2.604166vw;">${beginTime}</P>
<%-- 							<input style="width:10vw; border: 1px solid #cad3df; border-radius:0.5vw;" readonly="readonly" type="text" class="bgc_fff w230 p_l10 h50 m_r10" value="${beginTime}" /> --%>
						</div>
						<div class="f_l lh50">~</div>
						<div class="f_l position_relative">
							<P style="height:2.604166vw;width:10vw; line-hieght:2.604166vw;">${endTime}</P>
<%-- 							<input style="width:10vw;border: 1px solid #cad3df;border-radius:0.5vw;" readonly="readonly" type="text" class="bgc_fff w230 p_l10 h50 m_r10" value="${endTime}" /> --%>
						</div>
						<div class="wrap f_l h50 lh50 w140"style="margin: 0 1vw;">
							<div class="nice-select h50 lh50 w117 z_3"
								name="nice-select">
								<input id="dayNumId" readonly
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="3天">
								<ul id="dayNumUl">
									<li value="1">1天</li>
									<li value="2">2天</li>
									<li value="3">3天</li>
									<li value="4">4天</li>
									<li value="5">5天</li>
									<li value="6">6天</li>
									<li value="7">7天</li>
									<li value="8">8天</li>
									<li value="9">9天</li>
									<li value="10">10天</li>
								</ul>
							</div>
						</div>
<!-- 						<div class="cursor_ w20 h20 border_d9dde5 b_radius5 f_l set_time" style="margin-left: 0.5vw;margin-top: 0.7vw;"></div> -->
<!-- 						<div class="f_l"style="margin-left: 1vw;margin-right: 1vw;">自定义</div> -->
						
<!-- 						<div class="f_l set_this_time display_none"> -->
<!-- 							<div class="f_l position_relative"> -->
<%-- 								<input style="border: 1px solid #cad3df;border-radius:0.5vw; width:9vw;" name="bTime" class="bgc_fff w230 p_l10 h50 m_r10" value="${bTime}" type="text" id="tser50" onclick="$.jeDate('#tser50',{trigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})"> --%>
<%-- 								<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png"/> --%>
<!-- 							</div> -->
<!-- 							<div class="f_l lh50">~</div> -->
<!-- 							<div class="f_l position_relative"> -->
<%-- 								<input style="border: 1px solid #cad3df;border-radius:0.5vw;  width:9vw;" name="eTime" class="bgc_fff border0 w230 p_l10 h50 m_l10 m_r40" value="${eTime}" type="text" id="tser51" onclick="$.jeDate('#tser51',{trigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})"> --%>
<%-- 								<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png"/> --%>
<!-- 							</div> -->
<!-- 							<div class="clear"></div> -->
<!-- 						</div> -->
						<div id="subFormId" onclick="subForm('formId')" style="margin-left:1vw;" class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
							搜索
						</div>

						<div class="clear"></div>
<!-- 						<div class="reminder clearfix"> -->
<!-- 							<span class="fl">温馨提示：</span> -->
<!-- 							<div  class="fl"> -->
<!-- 								<p>1.此处数据量较大，请您点击搜索查看效果分析。</p> -->
<!-- 								<p>2. 由于统计数据需要时间，请您次日查看今日数据。</p> -->
<!-- 							</div> -->
							
<!-- 						</div> -->
						</form>
					</div>
					
					<input type="hidden" id="message" value="${message}" type="text">
					<table border="0" class="font14 item_table" style="margin-top:1.5vw;">
						<tr class="bgc_e3e7f0">
							<th style="width: 16vw;" class=" h60 text-center">短信群发客户</th>
							<th style="width: 16vw;" class=" h60 text-center">下单客户<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="效果分析选择时间内下订单的客户(根据买家昵称去重)"/></th>
							<th style="width: 16vw;" class=" h60 text-center">付款客户<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="效果分析选择时间内成功付款的客户(根据买家昵称去重)"/></th>
							<th style="width: 16vw;" class=" h60 text-center">订单未付款客户<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="如果一个客户下了两单，一个订单付款，一个订单未付款，那么下单客户数和未下单客户数均有该客户(根据买家昵称去重)"/></th>
							<th style="width: 16vw;" class=" h60 text-center">退款客户<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="效果分析选择时间内成功退款的客户(根据买家昵称去重)"/></th>
						</tr>
						<tr>
							<td class=" h60 text-center">群发客户数：${totalSmsCustomer}</td>
							<td class=" h60 text-center">下单客户数：${totalOrderCustomer}</td>
							<td class=" h60 text-center">付款客户数：${successCustomer}</td>
							<td class=" h60 text-center">订单未付款客户数：${waitCustomer}</td>
							<td class=" h60 text-center">退款客户数：${failCustomer}</td>
						</tr>
						<tr>
							<td class=" h60 text-center">成功发送客户数：${successSmsCustomer}</td>
							<td class=" h60 text-center">订单总金额：<fmt:formatNumber value="${totalOrderMoney}" minFractionDigits="2"></fmt:formatNumber>元</td>
							<td class=" h60 text-center">付款总金额：<fmt:formatNumber value="${successMoney}" minFractionDigits="2"></fmt:formatNumber>元</td>
							<td class=" h60 text-center">订单未付款总额：<fmt:formatNumber value="${waitMoney}" minFractionDigits="2"></fmt:formatNumber>元</td>
							<td class=" h60 text-center">退款总金额：<fmt:formatNumber value="${failMoney}" minFractionDigits="2"></fmt:formatNumber>元</td>
						</tr>
						<tr>
							<td class=" h60 text-center">发送成功率：<fmt:formatNumber value="${smsSuccessRate}" type="percent" minFractionDigits="2"></fmt:formatNumber></td>
							<td class=" h60 text-center">订单总数：${totalOrderNum}</td>
							<td class=" h60 text-center">付款订单总数：${successOrderNum}</td>
							<td class=" h60 text-center">订单未付款订单总数：${waitOrderNum}</td>
							<td class=" h60 text-center">退款订单总数：${failOrderNum}</td>
						</tr>
						<tr>
							<td class=" h60 text-center">短信发送条数：${totalSmsNum}</td>
							<td class=" h60 text-center">订单客单价：<fmt:formatNumber value="${orderCusPrice}" minFractionDigits="2"></fmt:formatNumber>元</td>
							<td class=" h60 text-center">付款客单价：<fmt:formatNumber value="${successCusPrice}" minFractionDigits="2"></fmt:formatNumber>元</td>
							<td class=" h60 text-center">订单未付款客单价：<fmt:formatNumber value="${waitCusPrice}" minFractionDigits="2"></fmt:formatNumber>元</td>
							<td class=" h60 text-center">退款客单价：<fmt:formatNumber value="${failCusPrice}" minFractionDigits="2"></fmt:formatNumber>元</td>
						</tr>
						<tr> 
							<td class=" h60 text-center">短信消费金额<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="短信消费金额为成功发送短信条数×0.05"/>：
								<fmt:formatNumber value="${smsMoney}" minFractionDigits="2"></fmt:formatNumber>元
							</td>
							<td class=" h60 text-center">订单内商品总数：${totalOrderItemNum}</td>
							<td class=" h60 text-center">付款商品数：${successItemNum}</td>
							<td class=" h60 text-center">订单未付款商品数：${waitItemNum}</td>
							<td class=" h60 text-center">退款商品数：${failItemNum}</td>
						</tr>
						<tr>
							<td class=" h60 text-center">未下单客户数：${failOrderCustomer}</td>
							<td class=" h60 text-center">平均订单商品数：<fmt:formatNumber value="${orderItemAverageNum}" minFractionDigits="2"></fmt:formatNumber></td>
							<td class=" h60 text-center">平均购买商品数：<fmt:formatNumber value="${successItemAverageNum}" minFractionDigits="2"></fmt:formatNumber></td>
							<td class=" h60 text-center">平均订单未付款商品数：<fmt:formatNumber value="${waitItemAverageNum}" minFractionDigits="2"></fmt:formatNumber></td>
							<td class=" h60 text-center">平均退款商品数：<fmt:formatNumber value="${failItemAverageNum}" minFractionDigits="2"></fmt:formatNumber></td>
						</tr>
						<tr>
							<td class=" h60 text-center">ROI<img style="width:0.8vw;" src="${ctx}/crm/images/bubble_2.png" title="投入产出比（ROI）=短信消费金额：付款金额。在此公式中，短信每按照0.05元计算"/>：${RIO}</td>
							<td class=" h60 text-center">下订单率<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="下订单率=下单客户数/成功发送短信客户数×100％"/>：<fmt:formatNumber value="${placeOrderRate}" type="percent" minFractionDigits="2"></fmt:formatNumber></td>
							<td class=" h60 text-center">付款率<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="付款率=付款客户数/下单客户数×100％"/>：<fmt:formatNumber value="${successOrderRate}" type="percent" minFractionDigits="2"></fmt:formatNumber></td>
							<td class=" h60 text-center">订单未付款率<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="订单未付款率=订单未付款客户数/下单客户数×100％"/>：<fmt:formatNumber value="${waitOrderRate}" type="percent" minFractionDigits="2"></fmt:formatNumber></td>
							<td class=" h60 text-center">退款率<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="退款率=退款客户数/付款客户数×100％"/>：<fmt:formatNumber value="${failOrderRate}" type="percent" minFractionDigits="2"></fmt:formatNumber></td>
						</tr>
					</table>
					<div>
						<div>
							<div class="effect-analysisBox">
								<div class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 text-center f_l cursor_ liebiao">
									列表
								</div>
								<div class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 text-center f_l cursor_ tubiao">
									图表
								</div>
							</div>
							<div class="clear"></div>
							<div class="lb_in">
								<table border="0" id="hTable" class="font14 item_table" style="margin-top:3.5vw;">
									<tr class="bgc_e3e7f0">
										<th style="width: 10vw;" class=" h60 text-center">日期</th>
										<th style="width: 10vw;" class=" h60 text-center">付款客户数</th>
										<th style="width: 10vw;" class=" h60 text-center">付款总金额</th>
										<th style="width: 10vw;" class=" h60 text-center">付款订单数</th>
										<th style="width: 10vw;" class=" h60 text-center">付款客单价</th>
										<th style="width: 10vw;" class=" h60 text-center">付款商品数</th>
										<th style="width: 10vw;" class=" h60 text-center">平均购买商品件数</th>
										<th style="width: 10vw;" class=" h60 text-center">付款率</th>
									</tr>
							<c:choose>
								<c:when test="${finishedDetilList.size() > 0}">
									<c:forEach items="${finishedDetilList}" var="finishedDetil">
										<tr>
											<td class=" h60 text-center">${finishedDetil.endTime}</td>
											<td class=" h60 text-center">${finishedDetil.customerNum}</td>
											<td class=" h60 text-center"><fmt:formatNumber value="${finishedDetil.successMoney}" minFractionDigits="2"></fmt:formatNumber></td>
											<td class=" h60 text-center">${finishedDetil.orderNum}</td>
											<td class=" h60 text-center"><fmt:formatNumber value="${finishedDetil.successCusPrice}" minFractionDigits="2"></fmt:formatNumber></td>
											<td class=" h60 text-center">${finishedDetil.itemNum}</td>
											<td class=" h60 text-center"><fmt:formatNumber value="${finishedDetil.successItemAverageNum}" minFractionDigits="2"></fmt:formatNumber></td>
											<td class=" h60 text-center">
												<c:if test="${finishedDetil.successItemAverageNum != null}">
													<fmt:formatNumber value="${finishedDetil.successOrderRate}" type="percent" minFractionDigits="2"></fmt:formatNumber>
												</c:if>
												<c:if test="${finishedDetil.successOrderRate == null}">
													0.00%
												</c:if>
											</td>
										</tr>
									</c:forEach>
										<tr>
											<td class=" h60 text-center">合计</td>
											<td class=" h60 text-center">${successCustomer}</td>
											<td class=" h60 text-center"><fmt:formatNumber value="${successMoney}" minFractionDigits="2"></fmt:formatNumber></td>
											<td class=" h60 text-center">${successOrderNum}</td>
											<td class=" h60 text-center"><fmt:formatNumber value="${successCusPrice}" minFractionDigits="2"></fmt:formatNumber></td>
											<td class=" h60 text-center">${successItemNum}</td>
											<td class=" h60 text-center"><fmt:formatNumber value="${successItemAverageNum}" minFractionDigits="2"></fmt:formatNumber></td>
											<td class=" h60 text-center">
												<c:if test="${successOrderRate != null}">
													<fmt:formatNumber value="${successOrderRate}" type="percent" minFractionDigits="2"></fmt:formatNumber>
												</c:if>
												<c:if test="${successOrderRate == null}">
													0.00%
												</c:if>
											</td>
										</tr>
								</c:when>
								<c:otherwise>
									<th style="width: 10vw;" class="h60 text-center" colspan="8">暂时没有相关数据！</th>
								</c:otherwise>
							</c:choose>
						</table>
							</div>
							<div class="tb_in" style="display:none;">
								<div id="container" style="height:400px;">
									
								</div>
								<div class="message">
									<input type="hidden" id="categoriesData" value="${categoriesDataList}">
									<input type="hidden" id="seriesData" value="${seriesDataList}">
								</div>
							</div>
							<div class="clear"></div>
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
		var oDate=new Date();
		var oYear=oDate.getFullYear();
		var oMon=oDate.getMonth()+1;
		var oD=oDate.getDate();
		var oH=oDate.getHours();
		var oM=oDate.getMinutes();
		var oS=oDate.getSeconds();
		$('.beginTime').text(oYear+'-'+toDub(oMon)+'-'+toDub(oD)+' '+toDub(oH)+':'+toDub(oM)+':'+toDub(oS));
	});
	function toDub(a){
		return a<10?'0'+a:''+a;
	}
	//时间插件需要点俩次的解决方法
	$('#tser50,#tser51').click(function(){
		console.log(1);
	});
	function subForm(formId){
		$('#' + formId).submit();
		$('#subFormId').addClass('display_none');
	}
	$(".tubiao").click(function(){
		$(".tb_in").show();
		$(".lb_in").hide();
		$(this).addClass("bgc_00a0e9 c_fff").removeClass("c_00a0e9");
		$(".liebiao").removeClass("bgc_00a0e9 c_fff").addClass("c_00a0e9");
	});
	$(".liebiao").click(function(){
		$(".tb_in").hide();
		$(".lb_in").show();
		$(this).addClass("bgc_00a0e9 c_fff").removeClass("c_00a0e9");
		$(".tubiao").removeClass("bgc_00a0e9 c_fff").addClass("c_00a0e9");
	});
	function strToJson(str){ 
		/* var json = (new Function("return " + str))(); 
		return json;  */
		/* var json = eval("("+str+")"); //将数据转换成json类型
        return json ; */
        
       	var strArr = str.split(",");
       	var json = (new Function("return " + strArr))(); 
		return json;
	} 

	$(function(){
		var message = $('#message').val();
		
		$(".liebiao").click();
		//订单来源回显
		var orderSourceVal = $('#orderSource').val();
		if(orderSourceVal == 'WAP,WAP'){
			$('#orderSourceId').val('手机端');
		}else if(orderSourceVal == 'TAOBAO'){
			$('#orderSourceId').val('PC端');
		}else{
			$('#orderSourceId').val('全部');
		}
		
		//筛选天数回显
		var dayNumVal = $('#dayNum').val();
		if(dayNumVal == '1'){
			$('#dayNumId').val('1天');
		}else if(dayNumVal == '2'){
			$('#dayNumId').val('2天');
		}else if(dayNumVal == '3'){
			$('#dayNumId').val('3天');
		}else if(dayNumVal == '4'){
			$('#dayNumId').val('4天');
		}else if(dayNumVal == '5'){
			$('#dayNumId').val('5天');
		}else if(dayNumVal == '6'){
			$('#dayNumId').val('6天');
		}else if(dayNumVal == '7'){
			$('#dayNumId').val('7天');
		}else if(dayNumVal == '8'){
			$('#dayNumId').val('8天');
		}else if(dayNumVal == '9'){
			$('#dayNumId').val('9天');
		}else if(dayNumVal == '10'){
			$('#dayNumId').val('10天');
		}/* else if(dayNumVal == '11'){
			$('#dayNumId').val('11天');
		}else if(dayNumVal == '12'){
			$('#dayNumId').val('12天');
		}else if(dayNumVal == '13'){
			$('#dayNumId').val('13天');
		}else if(dayNumVal == '14'){
			$('#dayNumId').val('14天');
		}else if(dayNumVal == '15'){
			$('#dayNumId').val('15天');
		} */
		var categoriesDataList = $('#categoriesData').val();
		var seriesDataList = $('#seriesData').val();
		var a = categoriesDataList.replace('[','').replace(']','').split(',');
		var b = strToJson(seriesDataList);
		doChart(a,b);
	})

	$(document).on('click', '.check_this', function() {
		$(".hide_this").show();
	});
	
	//订单来源选择
	$('#orderSourceUl li').click(function(){
		$('#orderSource').val($(this).attr('value'));
	})
	
	//筛选天数选择
	$('#dayNumUl li').click(function(){
		$('#dayNum').val($(this).val());
	})
	
	$(".one").click(function(){
		$(".hide_this").hide();
		$(".three").hide();
		$(".four").show();
		$(this).addClass("bgc_fff").removeClass("bgc_e3e7f0");
		$(".two").addClass("bgc_e3e7f0").removeClass("bgc_fff");
	});
	
	
	$(".two").click(function(){
		$(".two").addClass("bgc_e3e7f0").removeClass("bgc_fff");
		$(".one").addClass("bgc_e3e7f0").removeClass("bgc_fff");
		$(this).addClass("bgc_fff").removeClass("bgc_e3e7f0");
		var i=$(this).index();
		$(".three").hide();
		$(".four").hide();
		$(".three").eq(i).show();
	});
	
	$(".set_time").click(function(){
		  $(this).toggleClass("bgc_check_blue");
		  $(this).next().next(".set_this_time").toggleClass("display_none");
	});
	/* $('.check_this').click(function(){
		window.location.href = "${ctx}/member/effectPicture?msgId=" + $(this).children('input').val();
	}) */
	
	
	
	/*下拉框*/
	$('[name="nice-select"]').click(function(e){
		$('[name="nice-select"]').find('ul').hide();
		$(this).find('ul').show();
		e.stopPropagation();
	});
	$('[name="nice-select"] li').hover(function(e){
		$(this).toggleClass('on');
		e.stopPropagation();
	});
	$('[name="nice-select"] li').click(function(e){
		var val = $(this).text();
		var dataVal = $(this).attr("data-value");
		$(this).parents('[name="nice-select"]').find('input').val(val);
		$('[name="nice-select"] ul').hide();
		e.stopPropagation();
	});
	$(document).click(function(){
		$('[name="nice-select"] ul').hide();
	});
</script>
</html>
