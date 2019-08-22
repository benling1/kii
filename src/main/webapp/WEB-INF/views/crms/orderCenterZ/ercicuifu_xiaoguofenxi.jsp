﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>订单中心-二次催付效果分析</title>
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
<script type="text/javascript" src="${ctx}/crm/js/cuifutxing.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/changguicuifu.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/diqutanchukuang.js"></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
<script type="text/javascript" src="${ctx}/crm/js/util.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/highcharts.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/orderEffer.js" ></script>

</head>
<body>
	<%@ include file="/WEB-INF/views/crms/header/top.jsp"%>
	<%@ include file="/WEB-INF/views/crms/header/header.jsp"%>
	<div class="w1903 h100_">
		<!-------------------------左部，侧边栏------------------------->
		<div class="load_left"></div>


		<!-------------------------右部------------------------->
		<div class="w1703 m_l200 p_b33">
			<!--------------------顶部导航栏-------------------->
			<div class="load_top"></div>

			<!--------------------主要内容区-------------------->
			<div class="w1660 m_t70 m_l30">
				<div class="w1660  bgc_f1f3f7 c_384856">
					<div class=" p_t27 p_l40">
						<p class="font20" id="HEADER">常规催付</p>
						<p class="font14 p_t8">当客户下单后超过一定时间后，软件会自动发送短信给客户提醒客户付款，提高转化率就靠它了！</p>
						<div class="h40">
							<div
								class="w110 h40 l_h40 f_r bgc_fff m_r15 text-center  cursor_ ZhanKai">
								<img class="m_r10 JTX display_none"
									src="${ctx}/crm/images/箭头下.png" /> <img class="m_r10 JTS"
									src="${ctx}/crm/images/箭头上.png" /> <span class="LiuChengText">收回流程</span>
							</div>
						</div>
						<input type="hidden" id="msg" name="msg" value="${msg}">
					</div>
					<%@ include file="/WEB-INF/views/crms/header/liucheng.jsp"%>
					<ul class="h40 m_t1_7 font14 p_l40">
						<a href="${ctx}/crms/order/normal" class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_ ">常规催付</li></a>
						<a href="${ctx}/crms/order/again" class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_ ">二次催付</li></a>
						<a href="${ctx}/crms/order/jhs" class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">聚划算催付</li></a>
						<a href="#" class="c_384856"
							onclick="findOrderSendRedord('formId')"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">发送记录</li></a>
						<a href="${ctx}/orderEffectCGIndex" class="c_384856"><li 
						class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_ ">常规催付效果分析</li></a>
						<%-- <a href="${ctx}/orderEffectECIndex" class="c_384856"><li 
						class="f_l w140 text-center h40 l_h40 bgc_fff  cursor_ ">二次催付效果分析</li></a>
						<a href="${ctx}/orderEffectJHSIndex" class="c_384856"><li 
						class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_ ">聚划算催付效果分析</li></a> --%>
						<%-- <a href="${ctx}/crms/order/presell" class="c_384856"><li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">预算催付</li></a> --%>
					</ul>
				</div>
				<div class="bgc_fff"style="padding-left: 5vw;padding-top: 2vw;">
					<form id="subForm" action="${ctx}/orderEffectIndexData" method="post">
						<div style="line-height: 2.5vw;">
						<input type="hidden" name="type" value="3">
						<div class="f_l">
							催付日期：
							<input type="hidden" id="dayNumId" name="dayNum" value="${dayNum}">
						</div>
						<div class="wrap f_l h45 lh45 w140"style="margin: 0 1vw;">
							<div class="nice-select h45 lh45 w117 z_3"
								name="nice-select">
								<input readonly="readonly" id="dayNum"
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="近7天">
								<ul id="time01">
									<li value="1">近1天</li>
									<li value="7">近7天</li>
									<li value="15">近15天</li>
									<li value="30">近30天</li>
								</ul>
							</div>
						</div>
						
						<div class="f_l"style="margin-right: 1vw;">
							自定义：
						</div>
						<div class="f_l position_relative">
							<input style="border: 1px solid #cad3df;" name="bTime" class="bgc_fff w230 p_l10 h50 m_r10" value="${bTime}" type="text" id="tser01" readonly="true" onclick="$.jeDate('#tser01',{trigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
							<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png"/>
						</div>
						<div class="f_l lh50">~</div>
						<div class="f_l position_relative">
							<input style="border: 1px solid #cad3df;" name="eTime" class="bgc_fff border0 w230 p_l10 h50 m_l10 m_r40" value="${eTime}" type="text" id="tser02" readonly="true" onclick="$.jeDate('#tser02',{trigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
							<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png"/>
						</div>
						<div id="subFormId" onclick="subForm('subForm')" class="w100 h48 b_radius5 c_00a0e9 border_00a0e9 tk text-center f_l cursor_">
							搜索
						</div>
						<div class="clear"></div>
						
					</div>
					</form>
				
					<div class="f_l">
						<table border="0" class="font14 item_table" style="margin-top:3.5vw;">
							<tr class="bgc_e3e7f0">
								<th style="width: 8vw;" class=" h60 text-center">催付金额</th>
								<th style="width: 8vw;" class=" h60 text-center">回款金额</th>
								<th style="width: 8vw;" class=" h60 text-center">回款金额比例</th>
							</tr>
							<tr>
								<td style="width: 8vw;" class=" h60 text-center money1"><fmt:formatNumber type="number" value="${totalMoney}" pattern="0.00" maxFractionDigits="2"/></td>
								<td style="width: 8vw;" class=" h60 text-center money2"><fmt:formatNumber type="number" value="${earningsMoney}" pattern="0.00" maxFractionDigits="2"/></td>
								<td style="width: 8vw;" class=" h60 text-center money3"><fmt:formatNumber type="percent" value="${earningsMoneyRate}" pattern="0.00" maxFractionDigits="2"></fmt:formatNumber></td>
							</tr>
						</table>
						<table border="0" class="font14 item_table" style="margin-top:5vw;">
							<tr class="bgc_e3e7f0">
								<th style="width: 8vw;" class=" h60 text-center">催付订单数</th>
								<th style="width: 8vw;" class=" h60 text-center">回款订单数</th>
								<th style="width: 8vw;" class=" h60 text-center">回款订单比例</th>
							</tr>
							<tr>
								<td style="width: 8vw;" class=" h60 text-center order1">${totalOrder}</td>
								<td style="width: 8vw;" class=" h60 text-center order2">${earningsOrder}</td>
								<td style="width: 8vw;" class=" h60 text-center order3"><fmt:formatNumber type="percent" value="${earningsOrderRate}" pattern="0.00" maxFractionDigits="2"></fmt:formatNumber></td>
							</tr>
						</table>
						<table border="0" class="font14 item_table" style="margin-top:5vw;">
							<tr class="bgc_e3e7f0">
								<th style="width: 8vw;" class=" h60 text-center">催付短信条数</th>
								<th style="width: 8vw;" class=" h60 text-center">短信消费金额</th>
								<th style="width: 8vw;" class=" h60 text-center" title="投入产出比（ROI）=短信消费金额：回款金额。在此公式中，短信每按照0.05元计算，短信消费金额为成功发送短信条数×0.05">ROI<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" /></th>
							</tr>
							<tr>
								<td style="width: 8vw;" class=" h60 text-center num1">${smsNum}</td>
								<td style="width: 8vw;" class=" h60 text-center num2"><fmt:formatNumber type="number" value="${smsMoneyDouble}" pattern="0.00" maxFractionDigits="2"/></td>
								<td style="width: 8vw;" class=" h60 text-center num3">${RIO}</td>
							</tr>
						</table>
						<input type="hidden" id="dataList" value="${dataList}">
					</div>
					<div class="f_l" id="container" style="min-width:40vw;height:30vw;margin-top: 3.5vw;margin-left: 2vw;"></div>
				
					<div class="clear"></div>
				</div>
			</div>
		</div>

	</div>

	
	<!-- 引入商品jsp -->
	<%@ include file="/WEB-INF/views/crms/header/itemUtils.jsp"%>
</body>
<script type="text/javascript">
	//页面字符串转json
	function strToJson(str){ 
		var json = (new Function("return " + str))(); 
		return json; 
	} 
	//催付日期的选择
	$('#time01 li').click(function(){
		$('#dayNumId').val($(this).val());
	})
	
	function findOrderSendRedord(formId){
		document.getElementById(formId).submit();
	};
	
	function subForm(formId){
		document.getElementById(formId).submit();
		$('#subFormId').addClass('display_none');
	}
	
	$(function(){
		//回显选择的天数
		var dayNumVal = $('#dayNumId').val();
		if(dayNumVal == '1'){
			$('#dayNum').val('近1天');
		}else if(dayNumVal == '7'){
			$('#dayNum').val('近7天');
		}else if(dayNumVal == '15'){
			$('#dayNum').val('近15天');
		}else if(dayNumVal == '30'){
			$('#dayNum').val('近30天');
		}else{
			$('#dayNum').val('-未选择-');
		}
		var dataListVal = $('#dataList').val();
		var dataList = strToJson(dataListVal);
		chart(dataList,'二次催付效果分析图');
	})
</script>
</html>
