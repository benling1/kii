﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>订单中心-常规催付</title>
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
<script type="text/javascript" src="${ctx}/crm/js/util.js"></script>

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
				<div class="w100_ h50 lh50 bgc_fff font18">
					<div class="f_l h50 w4 bgc_1d9dd9"></div>
					<div class="f_l m_l15 c_384856" style="font-size: 1vw;">订单中心</div>
					<div class="f_l ">
						<ul style="margin-left: 8.45vw;">
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH">下单关怀</a></li>
							<li class="f_l w140 text-center bgc_f1f3f7"><a
								class="c_384856 display_block" href="${ctx}/crms/order/normal">催付提醒</a></li>
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/paymentCare/queryPaymentCare">付款关怀</a></li>
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/logisticsRemind/queryAllSettings">物流提醒</a></li>
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/cowryCare/queryCowryCare">宝贝关怀</a></li>
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/returnedmoneyWarn/queryReturnedMoneyWarn">回款提醒</a></li>
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/crms/refundCare/buyerRefund">退款关怀</a></li>
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block" href="${ctx}/crms/reviewCare">
									评价关怀 </a></li>
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block"
								href="${ctx}/appraiseAmend/showAppraiseAmend"> 中差评管理 </a></li>
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block"
								href="${ctx}/OrderReminder/jumOrderReminder"> 手动订单提醒 </a></li>
							<li style="clear: both;"></li>
						</ul>
					</div>
				</div>
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
							class="f_l w140 text-center h40 l_h40 bgc_fff  cursor_ ">常规催付</li></a>
						<a href="${ctx}/crms/order/again" class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_ ">二次催付</li></a>
						<a href="${ctx}/crms/order/jhs" class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">聚划算催付</li></a>
						<a href="#" class="c_384856"
							onclick="findOrderSendRedord('formId')"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">发送记录</li></a>
						<a href="${ctx}/orderEffectCGIndex" class="c_384856"><li
						class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_ ">常规催付效果分析</li></a>
						<%-- <a href="${ctx}/crms/order/presell" class="c_384856"><li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">预算催付</li></a> --%>
					</ul>
				</div>
				<!--选择商品弹出框start-->
				<div class="rgba_000_5 w1920 h100_ ChoiceSpecified_  display_none">
					<div class="w1000 h580 p_b33 bgc_fff m_t100 m_l300">
						<div class="w936 h546 margin0_auto bgc_fff">
							<p class="text-center p_t20 p_b40 font16">选择商品</p>
							<form class="font14" action="#">
								<p class="f_l">
									商品ID:<input id="itemId_input"
										class="h50 w13_4 border0 outline_none b_radius5 m_l15 m_r18 bgc_f4f6fa"
										type="text" />
								</p>
								<p class="f_l">
									关键字:<input id="itemName_input"
										class="h50 w13_4 border0 outline_none b_radius5 m_l15 m_r18 bgc_f4f6fa"
										type="text" />
								</p>
								<p class="f_l">
								<div id="putaway_div"
									class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17">
								</div>
								<input id="status_input" type="hidden" value="2" />
								<div class="m_l10 f_l font14 c_384856 lh50">
									只显示上架<input onclick="findItemByData();"
										class="h40 w80 border0 outline_none b_radius5 m_l35 bgc_00a0e9 c_fff cursor_"
										type="button" value="搜索" />
								</div>
								</p>
							</form>
							<div class="clear"></div>
							<div class="h300 overflow_auto">
								<table border="0" class="font14 m_t13 item_table">
									<tr class="bgc_e3e7f0 item_tr">
										<th class="w80 h60 text-center">全选</th>
										<th style="width: 8vw;" class=" h60 text-center">图片</th>
										<th style="width: 35vw;" class=" h60 text-center">宝贝名称</th>
										<th style="width: 8vw;" class=" h60 text-center">金额</th>
									</tr>
								</table>
							</div>

							<!--确定保存start-->

							<div class="w936 h42 m_t50 margin0_auto">
								<div class="w214 margin0_auto">
									<div onclick="addItemId();"
										class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ SpecifiedOut  Qita">
										确定</div>
									<div
										class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ SpecifiedOut">
										取消</div>
								</div>
							</div>

							<!--确定保存end-->
						</div>


					</div>
				</div>
				<!--选择商品弹出框end-->
				<!--选择所在地弹出框start-->
				<div class="rgba_000_5 w1920 h100_ display_none ChoiceArea">
					<div class="w750 p_b33 bgc_fff m_t100 m_l500 fasf">
						<div class="h50 p_t20 text-center font16">选择所在地区</div>

						<div class="font14 c_384856 h50 lh50 p_l50">

							<div class="f_l w90 1check_box all_check">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 c_384856">全选</div>
							</div>


							<!-- <div class="f_l w90 1check_box all_notcheck">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 c_384856">全不选</div>
							</div> -->
							<div class="f_l 1check_box far_notcheck">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 c_384856">排除边远山区</div>
							</div>
						</div>
						<div class="font14 c_384856 p_l50 place_check">
							<!--华东-->
							<ul class="h50 gangaotai_ul">
								<li class="f_l 1check_box w90 gangaotai">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										华东</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										上海 <input type="hidden" value="上海市">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										浙江<input type="hidden" value="浙江省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										江苏<input type="hidden" value="江苏省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										安徽<input type="hidden" value="安徽省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										江西<input type="hidden" value="江西省">
									</div>
								</li>
							</ul>

							<!--华北-->
							<ul class="h50 gangaotai_ul">
								<li class="f_l 1check_box w90 gangaotai">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										华北</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										北京<input type="hidden" value="北京市">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										天津<input type="hidden" value="天津市">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										河北<input type="hidden" value="河北省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										山西<input type="hidden" value="山西省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										山东<input type="hidden" value="山东省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										内蒙古<input type="hidden" value="内蒙古自治区">
									</div>
								</li>
							</ul>

							<!--华中-->
							<ul class="h50 gangaotai_ul">
								<li class="f_l 1check_box w90 gangaotai">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										华中</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										湖北<input type="hidden" value="湖北省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										湖南<input type="hidden" value="湖南省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										河南<input type="hidden" value="河南省">
									</div>
								</li>
							</ul>

							<!--华南-->
							<ul class="h50 gangaotai_ul">
								<li class="f_l 1check_box w90 gangaotai">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										华南</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										广东<input type="hidden" value="广东省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										广西<input type="hidden" value="广西壮族自治区">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										福建<input type="hidden" value="福建省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										海南<input type="hidden" value="海南省">
									</div>
								</li>
							</ul>

							<!--东北-->
							<ul class="h50 gangaotai_ul">
								<li class="f_l 1check_box w90 gangaotai">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										东北</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										辽宁<input type="hidden" value="辽宁省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										吉林<input type="hidden" value="吉林省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										黑龙江<input type="hidden" value="黑龙江省">
									</div>
								</li>
							</ul>

							<!--西北-->
							<ul class="h50 gangaotai_ul">
								<li class="f_l 1check_box w90 gangaotai">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										西北</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										陕西<input type="hidden" value="陕西省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										新疆<input type="hidden" value="新疆维吾尔自治区">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										青海<input type="hidden" value="青海省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										宁夏<input type="hidden" value="宁夏回族自治区">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										甘肃<input type="hidden" value="甘肃省">
									</div>
								</li>
							</ul>

							<!--西南-->
							<ul class="h50 gangaotai_ul">
								<li class="f_l 1check_box w90 gangaotai">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										西南</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										四川<input type="hidden" value="四川省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										云南<input type="hidden" value="云南省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										西藏<input type="hidden" value="西藏自治区">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										贵州<input type="hidden" value="贵州省">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										重庆<input type="hidden" value="重庆市">
									</div>
								</li>
							</ul>

							<!--港澳台-->
							<ul class="h50 gangaotai_ul">
								<li class="f_l 1check_box w90 gangaotai">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										港澳台</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										香港<input type="hidden" value="香港特别行政区">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										澳门<input type="hidden" value="澳门特别行政区">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										台湾<input type="hidden" value="台湾省">
									</div>
								</li>
							</ul>

							<!--海外-->
							<ul class="h50 gangaotai_ul">
								<li class="f_l 1check_box w90 gangaotai">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										海外</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										海外<input type="hidden" value="海外">
									</div>
								</li>
							</ul>

						</div>
						<div class="h40 margin0_auto w225">
							<div onclick="customRegion()"
								class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ ArerOut">
								保存</div>
							<div
								class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ ArerOut">
								取消</div>
						</div>
					</div>
				</div>
				<!--选择所在地弹出框end-->

				<!--添加链接弹出框start-->
				<div class="rgba_000_5 w1920 h100_ display_none ChoiceLink">
					<div class="w630 p_b40 bgc_fff m_t100 m_l500">
						<div class="h60 lh60 w630 text-center font16 bgc_f1f3f7">添加短链接</div>
						<input id="linkData_input" type="hidden" value="0">
						<ul class="h53 lh50 bgc_f1f3f7">
							<li onclick="linkDataInput('0')"
								class="f_l h53 w140 text-center bgc_fff TaoBaoLianJie1">淘宝短链</li>
							<!--                                                 <li onclick="linkDataInput('1')" class="f_l h53 w140 text-center bgc_e3e7f0 TaoBaoLianJie1">客户端短链</li> -->
						</ul>
						<form action="#" class="p_l24 p_t30 p_r20 TaoBaoLianJie2">
							<p class="l_h32 font14 m_b30">
								说明：网址必须是taobao.com、tmall.com、jaeapp.com这三个域名下。<br />
								点击效果,需在淘宝后台ECRM中查看提示：默认网站、后都加空格，可确保手机打开无异常兼容设备识别。另外直接从外部复制粘贴的网址也请务必前后加空格，否则将导致无法正常打开。
							</p>
							<div>
								<input id="linkType" type="hidden" value="店铺首页">
								<div class="f_l m_r10">
									<div class="f_l font14  lh45">类型:</div>
								</div>
								<div class="f_l m_r10">
									<div onclick="linkData('店铺首页')"
										class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK bgc_check_blue DianPuShouYe">
									</div>
									<div class="m_l10 f_l font14  lh45 c_8493a8">店铺首页</div>
								</div>
								<div class="f_l m_r10">
									<div onclick="linkData('商品链接')"
										class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK ShopLianJie AddSpecified_link">
									</div>
									<div class="m_l10 f_l font14  lh45 c_8493a8">商品链接</div>
								</div>
								<div class="f_l">
									<div onclick="linkData('活动页链接')"
										class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK HuoDongLianJie">
									</div>
									<div class="m_l10 f_l font14  lh45 c_8493a8">活动页链接</div>
								</div>
								<div class="f_r m_r15">
									<a
										href="https://crm.bbs.taobao.com/detail.html?spm=a210m.7802402.0.0.zeU4Ci_a210m.7802402.0.0.lk6D3P_a210m.7802402.0.0.Dd9Nw4&postId=1622316"
										target="_blank">
										<div class="m_l10 f_l font14 c_00a0e9 lh45">淘宝短链接使用教程</div>
									</a>
								</div>
							</div>
							<div class="clear"></div>
							<div class="m_t20 ShopId display_none">
								<span class="font14 p_r20">商品ID:</span> <input id="commodityId"
									class="h42 l_h42 p_l15 outline_none border0 b_radius5 w274 bgc_f4f6fa"
									type="text" />
							</div>
							<div class="HuoDongWangZhi display_none">
								<div class="m_b10">
									<p class="f_l">活动网址 :</p>
									<input id="activityUrl"
										class="w500 h65 border0 bgc_f4f6fa b_radius8 f_l" type="text"
										value="">
								</div>
								<div class="clear font14 c_cbcdcf m_t15 p_t10">
									常见问题：PC端自定义活动页面，因淘宝无线端没有对应的H5活动页面如直接转化可能会导致跳转至手淘店铺首页，解决方法：建议先到无线运营中心->无线店铺->店铺装修->自定义页面”生成H5活动页面，再进行转化。
								</div>
							</div>
						</form>
						<form action="#"
							class="p_l24 p_t30 p_r40 p_l40 display_none TaoBaoLianJie2">
							<p class="l_h32 font14 m_b30">
								提示：默认网址前、后都加空格，可确保手机打开无异常兼容设备识别。另外直接从外部复制
								粘贴的网址也请务必前后加空格，否则将导致无法正常打开。</p>
							<textarea id="kehuduan"
								class="w540 h160 b_radius5 p_l15 p_t15 c_cecece outline_none border0 bgc_f4f6fa">请输入网址：</textarea>
						</form>
						<!--确定保存start-->
						<div class="w630 h42 m_t20 margin0_auto">
							<div class="w300 margin0_auto">
								<div onclick="linkSubmit()"
									class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ LinkOut">
									确定</div>
								<div
									class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l50 f_l cursor_ LinkOut">
									取消</div>
							</div>
						</div>
						<!--确定保存end-->
					</div>
				</div>
				<!--添加链接弹出框start-->

				<!--另存为短语库start-->
				<div class="rgba_000_5 w1920 h100_ display_none ChoicePhrase">
					<div class="w490 h230 p_b10 bgc_fff m_t250 m_l500">
						<div class="h64 lh64 m_b10 text-center font16">另存为短语库</div>
						<div class="w426 margin0_auto">
							<input id="saveSms"
								class="w411 h42 b_radius5 border0 outline_none bgc_f4f6fa p_l15 c_cecece"
								type="text" placeholder="模板名称" />
						</div>

						<!--确定保存start-->
						<div class="w490 h42 m_t36 margin0_auto">
							<div class="w260 margin0_auto">
								<div onclick="saveTemplate()"
									class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ PhraseOut">
									确定</div>
								<div
									class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l50 f_l cursor_ PhraseOut">
									取消</div>
							</div>
						</div>
						<!--确定保存end-->

					</div>
				</div>
				<!--另存为短语库end-->

				<!--引用短语库 start-->
				<div
					class="rgba_000_5 w1920 h100_  PhraseLibrary display_none text_model_window">
					<input id="showTep" type="hidden" value="${showTemplate}">
					<div class="w1000 h580 p_b33 bgc_fff m_t100 m_l300">
						<div class="w1000 p_t20 bgc_f1f3f7 font16">
							<div class="text-center w1000">引用模板</div>
							<ul class="h40 p_t41 font14 m_l34">
								<li onclick="findTemplate('默认')"
									class="f_l w140 text-center h40 l_h40 bgc_fff    cursor_ DYK1">系统短语库</li>
								<li onclick="findTemplate('自定义')"
									class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ DYK1">自定义短语库</li>
							</ul>
						</div>
						<div class="w936 h546 margin0_auto DYK2">
							<div class="font14 c_384856 h45 l_h45">
								<div class="f_l default">
									<div onclick="findTemplate('默认')"
										class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK GXK1 bgc_check_blue">
									</div>
									<div class="m_l10 f_l font14 c_384856">默认</div>
								</div>
								<div class="f_l m_l10">
									<div onclick="findTemplate('常规版')"
										class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
									</div>
									<div class="m_l10 f_l font14 c_384856">常规版</div>
								</div>
								<div class="f_l m_l10">
									<div onclick="findTemplate('创意版')"
										class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
									</div>
									<div class="m_l10 f_l font14 c_384856">创意版</div>
								</div>
								<div class="f_l m_l10">
									<div onclick="findTemplate('幽默版')"
										class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
									</div>
									<div class="m_l10 f_l font14 c_384856">幽默版</div>
								</div>
								<div class="f_l m_l10">
									<div onclick="findTemplate('萌版')"
										class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
									</div>
									<div class="m_l10 f_l font14 c_384856">萌版</div>
								</div>
								<div onclick="findTemplate('按时间更新')" class="f_l m_l10">
									<div class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
									</div>
									<div class="m_l10 f_l font14 c_384856">按时间更新</div>
								</div>
							</div>

							<!--分页样式start-->
							<%-- <div class="w936 h24 m_t10 font14 c_8493a8 ">
                                                    <div class="f_r w470 h24 l_h22 font12">
                                                        <c:if test="${smsTemplate!=null }">
															<c:forEach items="${smsTemplate.pageView }" var="page">
																${page} 
														  	</c:forEach>
														 </c:if>
                                                    </div>
                                                </div> --%>
							<!--分页样式end-->
							<!--确定保存start-->

							<div class="h300 overflow_auto">
								<table border="0" class="font14 m_t13 temp_table1">
									<tr class="bgc_e3e7f0 temp_tr1">
										<td class="w77 h50 text-center">序号</td>
										<td class="w500 h50 text-center">短信内容</td>
										<td class="w128 h50 text-center">热度</td>
										<td class="w120 h50 text-center"></td>
									</tr>
								</table>
							</div>
							<div class="w936 h42 m_t50 margin0_auto">
								<div class="w214 margin0_auto">
									<div
										class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ LibraryOut">
										确定</div>
									<div
										class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ LibraryOut">
										取消</div>
								</div>
							</div>

						</div>

						<div class="w936 h546 margin0_auto DYK2 display_none">
							<div class="h14"></div>
							<div class="h300 overflow_auto">
								<table border="0" class="font14 temp_table2">
									<tr class="bgc_e3e7f0 temp_tr2">
										<td class="w77 h50 text-center">序号</td>
										<td class="w258 h50 text-center">模板名称</td>
										<td class="w595 h50 text-center">短信内容（展开全部）</td>
										<td class="w100 h50 text-center"></td>
									</tr>
								</table>
							</div>
							<!--分页样式start-->

							<!--分页样式end-->
							<!--确定保存start-->

							<div class="w936 h42 m_t50 margin0_auto">
								<div class="w214 margin0_auto">
									<div
										class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ LibraryOut">
										确定</div>
									<div
										class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ LibraryOut">
										取消</div>
								</div>
							</div>

							<!--确定保存end-->
						</div>
					</div>
				</div>
				<!--引用短语库 end-->

				<!----------------------------------------------------------------------------------------       常规催付                    ----------------------------------------------------------------------------------------------->
				<div class="w1660 bgc_fff">

					<div class="w1590  c_384856 p_l50 font16 p_t40">
						<div class="w1030">
							<div class="h47">
								<p>
									基本设置<span class="font14 c_00a0e9 p_l10 cursor_  C_XG">修改</span>
								</p>
							</div>
							<div class="die_SZ">
								<div
									class="w1000 font14 c_384856 h45 lh45 p_l30 m_b20 cuifutongzhishijian">
									<div class="f_l m_r50">通知时间:</div>
									<div class="f_l m_r5 Time">
										<c:choose>
											<c:when test="${orderSetupNormal.startTime==null}">不限</c:when>
											<c:otherwise>${orderSetupNormal.startTime}</c:otherwise>
										</c:choose>
									</div>
									<div class="f_l m_r5">~</div>
									<div class="f_l m_r25 Time2">
										<c:choose>
											<c:when test="${orderSetupNormal.endTime==null}">不限</c:when>
											<c:otherwise>${orderSetupNormal.endTime}</c:otherwise>
										</c:choose>
									</div>
									<div class="f_l">
										<c:if test="${orderSetupNormal.filtingConditions==5}">超出时间不催付</c:if>
										<c:if test="${orderSetupNormal.filtingConditions==6}">超出时间次日催付</c:if>
									</div>
								</div>

								<div class="font14 c_384856 h45 lh45 p_l30 m_b20">
									<div class="f_l m_r50">实付金额:</div>
									<div id="minMoney" class="f_l Money m_r5">
										<c:choose>
											<c:when test="${orderSetupNormal.payAmtOne==null}">不限</c:when>
											<c:otherwise>${orderSetupNormal.payAmtOne}</c:otherwise>
										</c:choose>
									</div>
									<div class="f_l m_r5">~</div>
									<div id="maxMoney" class="f_l Money">
										<c:choose>
											<c:when test="${orderSetupNormal.payAmtTwo==null}">不限</c:when>
											<c:otherwise>${orderSetupNormal.payAmtTwo}</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="font14 c_384856 h45 lh45 p_l30 m_b20">
									<div class="f_l m_r50">催付订单:</div>
									<div class="f_l Time3">下单${orderSetupNormal.reminderTime}后，未付款</div>
								</div>
							</div>
							<form action="${ctx}/sendRecord/orderCenterRecord" method="post"
								id="formId">
								<input type="hidden" name="recordFlag" value="2">
							</form>

							<div class="display_none Live_SZ">
								<form id="changGuiForm" action="${ctx}/crms/order/normal/setup"
									method="post">
									<div class="w1000 font14 c_384856 h45 lh45 p_l30 m_b20">
										<input id="filtInput" type="hidden" name="filtingConditions"
											value="${orderSetupNormal.filtingConditions}"> <input
											id="statusId" type="hidden" name="status"
											value="${orderSetupNormal.status}"> <input
											id="setupId" type="hidden" name="id"
											value="${orderSetupNormal.id}">
										<div class="f_l m_r15">通知时间:</div>

										<div class="wrap f_l h45 lh45 m_r15 CUIFUTONGZHISHIJIAN">
											<div class="wrap f_l h45 lh45 w140">
												<div class="nice-select h45 lh45 w117 z_3"
													name="nice-select">
													<input readonly="readonly" id="startTime"
														class="h45 lh45 w140 Time11" style="color: #000000"
														type="text" name="startTime"
														value="${orderSetupNormal.startTime}">
													<ul id="time01">
														<li>8:00</li>
														<li>9:00</li>
														<li>10:00</li>
														<li>11:00</li>
														<li>12:00</li>
														<li>13:00</li>
														<li>14:00</li>
														<li>15:00</li>
														<li>16:00</li>
														<li>17:00</li>
														<li>18:00</li>
														<li>19:00</li>
														<li>20:00</li>
														<li>21:00</li>
													</ul>
												</div>
											</div>
											<div class="f_l m_l10">~</div>
											<div class="wrap f_l h45 lh45 w140 m_l10">
												<div class="nice-select h45 lh45 w117" name="nice-select">
													<input readonly="readonly" id="endTime"
														class="h45 lh45 w140 Time22 " style="color: #000000"
														type="text" name="endTime"
														value="${orderSetupNormal.endTime}">
													<ul id="time02">
														<li>8:00</li>
														<li>9:00</li>
														<li>10:00</li>
														<li>11:00</li>
														<li>12:00</li>
														<li>13:00</li>
														<li>14:00</li>
														<li>15:00</li>
														<li>16:00</li>
														<li>17:00</li>
														<li>18:00</li>
														<li>19:00</li>
														<li>20:00</li>
														<li>21:00</li>
														<li>22:00</li>
													</ul>
												</div>
											</div>
										</div>
										<div class="f_l m_l15">
											<div
												class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK GXKTime1">
											</div>
											<div class="m_l10 f_l font14 c_384856 lh40">超出时间不催付</div>
										</div>
										<div class="f_l m_l10">
											<div
												class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK GXKTime2">
											</div>
											<div class="m_l10 f_l font14 c_384856 lh40">超出时间次日催付</div>
										</div>
									</div>
									<div class="font14 c_384856 h45 lh45 p_l30 m_b20">
										<div class="f_l m_r15">实付金额:</div>
										<div class=" f_l h45 lh45">
											<div class="bgc_f4f6fa w140 f_l m_r10 radius10">
												<input
													class="f_l bgc_f4f6fa border0 w85 h45 lh45 p_l15 outline_none Money11"
													type="text" name="payAmtOne" id="minPayment"
													value="${orderSetupNormal.payAmtOne}" />
												<div class="f_r w25">元</div>
											</div>
											<div class="bgc_f4f6fa w140 f_l m_r10">
												<input
													class="f_l bgc_f4f6fa border0 w85 h45 lh45 p_l15 outline_none Money22"
													type="text" name="payAmtTwo" id="maxPayment"
													value="${orderSetupNormal.payAmtTwo}" />
												<div class="f_r w25">元</div>
											</div>
										</div>
									</div>

									<div class="font14 c_384856  lh45 p_l30 m_b20 clearfix">
										<div class="f_l m_r15">催付订单:</div>
										<div class="f_l">
										<p style="color:#ff5751;">淘抢购催付请设置在15分钟以内。</p>
										<div class="clearfix">
										<div class="wrap f_l h45 lh45 m_r15">
											<div class="f_l m_r5 c_cbcdcf">下单</div>
											<div class="wrap f_l h45 lh45 w140">
												<div class="nice-select h45 lh45 w117 z_2"
													name="nice-select">
													<input readonly="readonly" id="cuiFuId"
														class="h45 lh45 w140 Time11 " style="color: #000000"
														type="text" name="reminderTime"
														value="${orderSetupNormal.reminderTime}">
													<ul id="cuiFu00">
														<li>0分钟</li>
														<li>3分钟</li>
														<li>5分钟</li>
														<li>7分钟</li>
														<li>10分钟</li>
														<li>15分钟</li>
														<li>30分钟</li>
														<li>1小时</li>
														<li>2小时</li>
														<li>3小时</li>
														<li>4小时</li>
														<li>5小时</li>
														<li>6小时</li>
														<li>7小时</li>
														<li>8小时</li>
														<li>9小时</li>
														<li>10小时</li>
														<li>11小时</li>
														<li>12小时</li>
														<li>24小时</li>
														<li>36小时</li>
														<li>2天</li>
														<li>3天</li>
														<li>5天</li>
														<li>7天</li>
														<li>15天</li>
														<li>30天</li>
													</ul>
												</div>
											</div>
											</div>
											<div class="f_l c_cbcdcf">后，开始发送。</div>
											</div>
										</div>
									</div>
									<div class="font14 c_384856 h45 lh45 p_l30 m_t10">
										<div class="f_l m_r15">过滤条件:</div>
										<div class="f_l m_l10">
											<div
												class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l bgc_check_blue">
											</div>
											<div class="m_l10 f_l font14 c_384856 lh40">
												同一买家，1天只催1次</div>
										</div>
										<div class="f_l m_l10">
											<div
												class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l bgc_check_blue">
											</div>
											<div class="m_l10 f_l font14 c_384856 lh40">
												同一买家有付过款的不催（1小时前后）</div>
										</div>
										<div class="f_l m_l10">
											<div
												class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l bgc_check_blue">
											</div>
											<div class="m_l10 f_l font14 c_384856 lh40">屏蔽黑名单用户</div>
										</div>
										<!-- <div class="f_l m_l10">
											<div
												class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l bgc_check_blue">
											</div>
											<div class="m_l10 f_l font14 c_384856 lh40">预售订单不催付</div>
										</div> -->
									</div>
								</form>
								<div class="f_r m_t10 p_l20">
									<div onclick="submitFormOrderSetup('changGuiForm')" id="save"
										class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ Save Save1">
										保存</div>
									<div
										class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ Cancel">
										取消</div>
								</div>
								<div style="clear: both"></div>

							</div>
						</div>
					</div>






					<!--高级设置start-->

					<div class="w1590  bgc_fff c_384856 p_l50 font16 GaoJiSheZhi">
						<div class="w1030">
							<div class="h47">
								<p>
									高级设置<span class="font14 c_00a0e9 p_l10 cursor_ C_XG">修改</span>
								</p>
							</div>
							<div class="die_SZ m_b40">
								<div class="font14 c_384856 h50 lh50 p_l30">
									<div class="f_l m_r25">地区筛选:</div>
									<div class="f_l DiQu">
										<c:choose>
											<c:when test="${orderAdvancedSettingNormal.locality==null }">
	                                        	默认全部
										    </c:when>
											<c:when test="${orderAdvancedSettingNormal.locality=='0' }">
											   		 默认全部  
										    </c:when>
											<c:when
												test="${orderAdvancedSettingNormal.locality=='上海市,浙江省,江苏省' }">
											   		 江浙沪 
										    </c:when>
											<c:when
												test="${orderAdvancedSettingNormal.locality=='新疆维吾尔自治区,云南省,青海省,西藏自治区' }">
											   		偏远地区  
										    </c:when>
											<c:otherwise>  
											    	自定义
										    </c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="font14 c_384856 h50 lh50 p_l30">
									<div class="f_l m_r25">卖家标记:</div>
									<div class="f_l">
										<c:if test="${orderAdvancedSettingNormal.vendormark==null}">
												所有卖家标记都不屏蔽
	                                        </c:if>
										<c:if test="${orderAdvancedSettingNormal.vendormark=='0'}">
												所有卖家标记都不屏蔽
	                                        </c:if>
										<c:if test="${orderAdvancedSettingNormal.vendormark=='1'}">
												屏蔽卖家标记
	                                        </c:if>

									</div>
								</div>

								<div class="font14 c_384856 h50 lh50 p_l30">
									<div class="f_l m_r25">订单来源:</div>
									<div class="f_l LaiYuan">
										<c:if test="${orderAdvancedSettingNormal.orderSource==null }">
												不限
                                        </c:if>
										<c:if test="${orderAdvancedSettingNormal.orderSource=='0' }">
												不限
                                        </c:if>
										<c:if test="${orderAdvancedSettingNormal.orderSource=='1' }">
												PC端
                                        </c:if>
										<c:if test="${orderAdvancedSettingNormal.orderSource=='2' }">
												移动端
                                        </c:if>
									</div>
								</div>

								<!-- <div class="font14 c_384856 h50 lh50 p_l30">
									<div class="f_l m_r25">会员等级:</div>
									<div class="f_l VipDengJi">
										<span id="memberLevelId">不限</span>
									</div>
								</div> -->

								<div class="font14 c_384856 h50 lh50 p_l30">
									<div class="f_l m_r25">商品选择:</div>
									<div class="f_l AllShangPin">
										<c:choose>
											<c:when
												test="${orderAdvancedSettingNormal.productSelect=='0'}">
                                        		全部商品
                                        	</c:when>
											<c:when
												test="${orderAdvancedSettingNormal.productSelect=='1'}">
                                        		指定商品
                                        	</c:when>
											<c:when
												test="${orderAdvancedSettingNormal.productSelect=='2'}">
                                        		排除指定商品
                                        	</c:when>
											<c:otherwise>
                                        		全部商品
                                        	</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>

							<!----------------------------------------------     高级设置编辑部分           ----------------------------------------------------->

							<div class="display_none Live_SZ">

								<div class="font14 c_384856 h50 lh50 p_l30 DiQuShaiXuan">
									<div class="f_l m_r15">
										地区筛选:
										<!-- <input id="region_filtrate" type="text" name="" value="0" /> -->
										<input id="region_name" type="hidden" value="0" />
									</div>
									<div class="f_l m_l10">
										<div id="dq0" onclick="addRegionflag(0);"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 bgc_check_blue">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">默认全部</div>
									</div>
									<div class="f_l m_l10">
										<div id="dq1" onclick="addRegionflag(1);"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 JiangZheHu position_relative">
											<span
												class="position_absolute w200 l_h22 left_70 top_32 c_384856 bgc_f4f6fa text-center display_none">江苏省，浙江省，上海</span>
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">江浙沪</div>
									</div>
									<div class="f_l m_l10">
										<div id="dq2" onclick="addRegionflag(2);"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 JiangZheHu position_relative">
											<span
												class="position_absolute w200 l_h22 left_70 top_32 c_384856 bgc_f4f6fa text-center display_none">新疆、西藏、云南省、青海省</span>
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">偏远地区</div>
									</div>
									<div class="f_l m_l10">
										<div id="dq3" onclick="addRegionflag(3);"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 CustomArea">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50 CustomArea">
											自定义</div>
									</div>
								</div>

								<div class="font14 c_384856 h50 lh50 p_l30">
									<div class="f_l m_r15">
										卖家标记: <input id="vendor_flag" type="hidden" value="0" /> <input
											id="shield_flag" type="hidden" />
									</div>
									<div class="f_l m_l10">
										<div id="mj0" onclick="addVendorflag(0);"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 bgc_check_blue YouMaiJiaBiaoJiDouBuPingBi">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">所有卖家标记都不屏蔽</div>
									</div>
									<div class="f_l m_l10">
										<div id="mj1" onclick="addVendorflag(1);"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17  PingBiMaiJiaBiaoJi">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">屏蔽卖家标记</div>
									</div>
									<div class="f_l m_t4 m_l15 display_none XiaoQiQi">
										<div class="f_l m_t2">
											<div id="mjC1" onclick="addShieldflag();"
												class="m_t10 m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK vendor_flag_div">
												<input type="hidden" value="1" />
											</div>
											<img class="w20 h20 m_t10" src="${ctx}/crm/images/redq.png">
										</div>
										<div class="f_l m_t2 m_l15">
											<div id="mjC2" onclick="addShieldflag();"
												class="m_t10 m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK vendor_flag_div">
												<input type="hidden" value="2" />
											</div>
											<img class="w20 h20 m_t10"
												src="${ctx}/crm/images/yellowq.png">
										</div>
										<div class="f_l m_t2 m_l15">
											<div id="mjC3" onclick="addShieldflag();"
												class="m_t10 m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK vendor_flag_div">
												<input type="hidden" value="3" />
											</div>
											<img class="w20 h20 m_t10" src="${ctx}/crm/images/greenq.png">
										</div>
										<div class="f_l m_t2 m_l15">
											<div id="mjC4" onclick="addShieldflag();"
												class="m_t10 m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK vendor_flag_div">
												<input type="hidden" value="4" />
											</div>
											<img class="w20 h20 m_t10" src="${ctx}/crm/images/blueq.png">
										</div>
										<div class="f_l m_t2 m_l15">
											<div id="mjC5" onclick="addShieldflag();"
												class="m_t10 m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK vendor_flag_div">
												<input type="hidden" value="5" />
											</div>
											<img class="w20 h20 m_t10"
												src="${ctx}/crm/images/violetq.png">
										</div>
									</div>
								</div>
								<div class="clear font14 c_384856 h50 lh50 p_l30 DingDanLaiYuan">
									<div class="f_l m_r15">
										订单来源: <input id="order_source" type="hidden" value="0" />
									</div>
									<div class="f_l m_l10">
										<div id="dl0" onclick="addOrderSource(0);"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 bgc_check_blue">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">不限</div>
									</div>
									<div class="f_l m_l10">
										<div id="dl1" onclick="addOrderSource(1);"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">PC端</div>
									</div>
									<div class="f_l m_l10">
										<div id="dl2" onclick="addOrderSource(2);"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">移动端</div>
									</div>

								</div>
								<!-- <div class="font14 c_384856 h50 lh50 p_l30 HuiYuanDengJi">
									<div class="f_l m_r15">
										会员等级: <input id="member_grade" type="hidden" value="0" />
									</div>
									<div class="f_l m_l10">
										<div id="hy0" onclick="addMemberGrade();"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ ALLVIPGXK m_t17 bgc_check_blue member_grade_div">
											<input type="hidden" value="0" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">不限</div>
									</div>
									<div class="f_l m_l10">
										<div id="hy1" onclick="addMemberGrade();"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 member_grade_div">
											<input type="hidden" value="1" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">首次到店会员</div>
									</div>
									<div class="f_l m_l10">
										<div id="hy2" onclick="addMemberGrade();"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 member_grade_div">
											<input type="hidden" value="2" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">店铺会员</div>
									</div>
									<div class="f_l m_l10">
										<div id="hy3" onclick="addMemberGrade();"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 member_grade_div">
											<input type="hidden" value="3" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">普通会员</div>
									</div>
									<div class="f_l m_l10">
										<div id="hy4" onclick="addMemberGrade();"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 member_grade_div">
											<input type="hidden" value="4" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">高级会员</div>
									</div>
									<div class="f_l m_l10">
										<div id="hy5" onclick="addMemberGrade();"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 member_grade_div">
											<input type="hidden" value="5" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">VIP会员</div>
									</div>
									<div class="f_l m_l10">
										<div id="hy6" onclick="addMemberGrade();"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 member_grade_div">
											<input type="hidden" value="6" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">至尊VIP会员</div>
									</div>


								</div> -->
								<div class="font14 c_384856 h50 lh50 p_l30 ShangPinXuanZhe">
									<div class="f_l m_r15">
										商品选择: <input id="item_select" type="hidden" value="0" /> <input
											id="appoint_ItemId" type="hidden" />
									</div>
									<div class="f_l m_l10">
										<div id="sp0" onclick="addItemflag('0');"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 bgc_check_blue AllShop">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">全部商品</div>
									</div>
									<div class="f_l m_l10">
										<div id="sp1" onclick="addItemflag('1');"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 ZhiDingShop">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">指定商品</div>
									</div>
									<div class="f_l m_l10">
										<div id="sp2" onclick="addItemflag('2');"
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 PaiChuShop">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">排除指定商品</div>
									</div>



									<div id="sp_z0" onclick="getItemInputName('appoint_ItemId')"
										class="w140 h38 lh38  b_radius5 bgc_00a0e9 c_fff text-center m_l18 m_t5 f_l display_none cursor_ AddSpecified">
										添加指定商品</div>
									<div id="sp_z1" onclick="getItemInputName('appoint_ItemId')"
										class="w140 h38 lh38  b_radius5 bgc_00a0e9 c_fff text-center m_l18 m_t5 f_l cursor_ display_none XuanZePaiCheSpecified">
										选择排除指定商品</div>
									<!-- <div class="f_r m_l10 display_none qitashop">
										<div
											class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ m_t17 QiTaWeiZhiDing">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">
											其他未指定商品，发送默认短信内容</div>
									</div>

									<div class="WeiZhiDingShop display_none">
										<table class="w930">
											<tr class="h55 bgc_f1f3f7">
												<td class="w190 text-center">其他未指定商品</td>
												<td class="w470 text-center">对应短信内容</td>
												<td class="w270 text-center">操作</td>
											</tr>
											<tr class="h55 rgb250">
												<td class="w190 text-center"></td>
												<td class="w470 text-center"></td>
												<td class="w270 text-center">
													<div
														class="w105 h40 l_h40 b_radius8 bgc_f33434 c_fff f_l m_l24 DeleteQuxiao cursor_">删除</div>
													<div
														class="w105 h40 l_h40 b_radius8 bor_cad3df c_8493a8 f_l m_l16 cursor_ XGXGXG">修改</div>
												</td>
											</tr>
										</table>
									</div> -->



								</div>

								<div class="f_r m_t150 p_l20">
									<div onclick="submitFormAdvanced('advanced_form')"
										class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ Cancel">
										保存</div>
									<div
										class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ Cancel">
										取消</div>
								</div>
								<div style="clear: both"></div>
								<!-- 保存按钮点击提交form表单 -->
								<form id="advanced_form"
									action="${ctx}/crms/order/normal/advance" method="post">
									<input type="hidden" name="id"
										value="${orderAdvancedSettingNormal.id }" />
									<%-- 地区筛选 --详细地区--%>
									<input id="locality" type="hidden" name="locality"
										value="${orderAdvancedSettingNormal.locality }" />
									<%-- 地区筛选 --详细地区--%>
									<input id="vendormark" type="hidden" name="vendormark"
										value="${orderAdvancedSettingNormal.vendormark }" />
									<%-- 卖家标记--标识 --%>
									<input id="flagcolor" type="hidden" name="flagcolor"
										value="${orderAdvancedSettingNormal.flagcolor }" />
									<%-- 卖家标记--屏蔽的卖家标记小旗颜色 --%>
									<input id="orderSource" type="hidden" name="orderSource"
										value="${orderAdvancedSettingNormal.orderSource }" />
									<%-- 订单来源 --%>
									<input id="memberLevel" type="hidden" name="memberLevel"
										value="${orderAdvancedSettingNormal.memberLevel }" />
									<%-- 会员等级 --%>
									<input id="productSelect" type="hidden" name="productSelect"
										value="${orderAdvancedSettingNormal.productSelect }" />
									<%-- 商品选择--标识 --%>
									<input id="itemId" type="hidden" name="itemId"
										value="${orderAdvancedSettingNormal.itemId }" />
									<%-- 商品选择--商品id --%>
								</form>
							</div>

						</div>
					</div>
					<!--高级设置end-->

					<!--短信设置start-->
					<div class="w1590  bgc_fff c_384856 p_l50 font16 DuanXinSheZhi">
						<div class="w1030 p_b40">
							<div class="h47">
								<p>
									短信设置<span class="font14 c_00a0e9 p_l10 cursor_ CCC_XG">修改</span>
								</p>
							</div>

							<div class="die_SZ duanxinshezhibukebianji">
								<div class="font14 c_384856 h40 lh40 p_l30">
									<div class="f_l m_r10">短信内容:</div>
									<div class="f_l w928 h285 bgc_f4f6fa radius10">
										<textarea id="contentShow" disabled="disabled"
											class="w888 h240 outline_none radius10 border0 bgc_f4f6fa p_l20 p_t15 p_r20 Letter">${smsSetting.messageContent}</textarea>
									</div>
								</div>

								<div class="clear w1030 h50 p_t40">
									<div
										class="w226 h50 lh50  b_radius5 bgc_00a0e9 c_fff text-center margin0_auto cursor_">
										<input type="button" name="" id="kaiQiId" value="开启催付提醒"
											class="w226 h50 lh50  b_radius5 bgc_00a0e9 c_fff text-center margin0_auto cursor_  font16 lijikaiqi border0" />
									</div>
								</div>
							</div>
							<!------------------------------    短信设置编辑部分        -------------------------------------->
							<div class="Live_SZ min_h570  DXSZBianJiBuFen display_none">
								<form id="smsSubmit" action="${ctx}/crms/normal/sms"
									method="post">
									<div class="font14 c_384856 h40 p_l1_2">
										<div class="f_l">
											短信变量: <input id="messageId"
												value="${smsSetting.messageVariable}" type="hidden"
												name="messageVariable">
										</div>
										<div class="f_l m_r15">
											<ul>
												<li
													style="font-size: 0.7vw;padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【订单号】');">
													订单号 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认5个字，以实际变量为准</span>
												</li>
												<li
													style="font-size: 0.7vw;padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【下单时间】');">
													下单时间 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
												</li>
												<li
													style="font-size: 0.7vw;padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【买家昵称】');">
													买家昵称 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw  b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
												</li>
												<li
													style="font-size: 0.7vw;padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【买家姓名】');">
													买家姓名 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
												</li>
												<li
													style="font-size: 0.7vw;padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【订单金额】');">
													订单金额 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认4个字，以实际变量为准</span>
												</li>
												<li
													style="font-size: 0.7vw;padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【付款链接】');">
													付款链接 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认17个字，以实际变量为准</span>
												</li>
											</ul>
										</div>
									</div>
									<input id="smsContent" type="hidden" name="messageContent">
									<input id="smsSign" name="messageSignature" type="hidden"
										value="${ShopName}"> <input id="testPhoneForm"
										type="hidden" name="mobileTest"> <input
										id="smsSettingId" name="id" type="hidden"
										value="${smsSetting.id}"> <input
										id="actualDeduction_input" value="${smsSetting.smsNumber}"
										name="smsNumber" type="hidden" />
								</form>
								<div class="font14 c_384856 m_t30">
									<div class="f_l m_r10 w92">
										<div class="p_l1_2">短信内容:</div>
										<div class="m_t15 c_red">注意:短网址默认前后已加空格，请勿删除！否则可能导致网址打不开!</div>
									</div>
									<div class="f_l w928 h285 bgc_f4f6fa radius10">
										<input id="textContent1" type="hidden"
											value="${smsSetting.messageContent}" /> <input type="hidden"
											id="settingType" name="settingType" value="2">
										<textarea id="textContent" maxlength="500"
											class="w888 h240 outline_none radius10 border0 bgc_f4f6fa p_l20 p_t15 p_r20 Letter11  text_area"
											onkeyup="addLength()"></textarea>
										<div class="h30 m_l20 c_bfbfbf f_l">
											已输入：<span id="inputNum" class="text_count">0</span>字 当前计费：<span
												id="contenPrice">1</span>条
										</div>
										<div class="c_bfbfbf m_r15 f_r">
											退订回 <input maxlength="2"
												class="w20 h20 l_h20 border_0 bgc_f4f6fa" placeholder="N"
												id="refundMSG" />
										</div>
										<div>
											<a href="${ctx}/crms/home/notice#duanxinxiangguan"
												class="c_bfbfbf m_r15 f_r c_00a0e9" target="_blank">计费规则</a>
										</div>

									</div>
								</div>

								<div class="w1030 font14 h40 l_h40">
									<div class="f_r">
										<div class="c_00a0e9 m_r15 f_l cursor_ ChangeLink"
											onclick="getCursortPosition(document.getElementById('textContent'));">转化为短链接</div>
										<div class="c_00a0e9 m_r15 f_l cursor_ Library"
											onclick="findTemplate('默认')">
											<%-- <a href="${ctx}/crms/smsTemplate"> --%>
											引用短语库
											<!-- </a> -->
										</div>
										<div class="c_00a0e9 m_r15 f_l cursor_ ClickPhrase">另存为短语库</div>
									</div>
								</div>
								<div class="f_l w1030 font14 c_384856 h45 lh45 p_l30" style="margin-bottom: 1vw;">
									<div class="m_r10">
										短信签名: <input style="width: 10vw;"
											class="h50  bgc_f4f6fa p_l15 outline_none border0 shopName sms_sign"
											disabled="disabled" type="text" value="${ShopName }"
											onkeyup="updateShopName(this.value,'textContent');" /> <img
											src="${ctx }/crm/images/bianji.png"
											style="width: 1.2vw; margin-left: 0.5vw; cursor: pointer;"
											onclick="updateDisabled();">
											<%-- <span class="m_l10 c_00a0e9 sms_sign">${ShopName}</span> --%>

									</div>
								</div>
								<div class="f_l w1030 font14 c_384856 h45 lh45 p_l30">
									<div class="m_r10">
										测试手机: <input id="testPhone" value="${smsSetting.mobileTest}"
											class="h50 lh50 w670 bgc_f4f6fa p_l15 outline_none border0 Phone"
											type="text" placeholder="可输入5个测试手机号，以逗号隔开" /> <a
											class="m_l10 c_00a0e9 m_l20 ceShiFaSong1 cursor_">测试发送</a>
									</div>

								</div>

								<div class="f_r m_t50">
									<div onclick="smsSubmit('smsSubmit')"
										class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ Save_">
										保存</div>
									<div id="cancel"
										class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ CCC_Cancel">
										取消</div>
								</div>


							</div>

						</div>
					</div>
					<div>
						<div
							class="position_absolute top_46vw right240 iphone w327 h642 display_none">
							<textarea disabled="disabled"
								class="text_area_copy m_l45 m_t150 w250 h370 border0 bgc_fff">${smsSetting.messageContent}</textarea>
						</div>
						<img
							class="w327 h642 position_absolute display_none ShouHuoTu z_2 right240 top_666"
							src="${ctx}/crm/images/iPhone-gai.png">
					</div>
					<!--短信设置end-->
				</div>

			</div>
		</div>

	</div>


	<!-- 引入商品jsp -->
	<%@ include file="/WEB-INF/views/crms/header/itemUtils.jsp"%>
</body>
<script type="text/javascript">
	/* $(function(){
		$(".load_top").load("top.html");
		$(".load_left").load("left.html");
	}) */
	//---------------------------------基本设置---------------------------------------
	function validateTwoPrice(){
			var maxPayment = $("#maxPayment").val();
			var minPayment = $("#minPayment").val();
			if(maxPayment == null || minPayment == null){
			}else{
				if(maxPayment!="" && minPayment!=""){
					var maxPaymentFloat = parseFloat(maxPayment);
					var minPaymentFloat = parseFloat(minPayment);
					if(maxPaymentFloat<minPaymentFloat){
						$(".tishi_2").show();
		 				$(".tishi_2").children("p").text("实付金额最大值不能小于最小值")
		 				setTimeout(function(){ 
		 					$(".tishi_2").hide()
		 				},3000)
						return false;
					}
				}
			}
			return true;
		}
	function validateTwoTime(){
		var endTime = $("#endTime").val();
		var startTime = $("#startTime").val();
		if(startTime==undefined || endTime == undefined){
		}else{
			if(endTime!="" && startTime!=""){
				var endTimeFloat = parseFloat(endTime.substring(0,2));
				var startTimeFloat = parseFloat(startTime.substring(0,2));
				if(endTimeFloat<startTimeFloat){
					$(".tishi_2").show();
	 				$(".tishi_2").children("p").text("结束时间不能小于开始时间")
	 				setTimeout(function(){ 
	 					$(".tishi_2").hide()
	 				},3000)
	 				$("#endTime").val('');
	 				$("#startTime").val('');
					return false;
				}else if(endTimeFloat==startTimeFloat){
					$(".tishi_2").show();
	 				$(".tishi_2").children("p").text("结束时间不能等于开始时间")
	 				setTimeout(function(){ 
	 					$(".tishi_2").hide()
	 				},3000)
	 				$("#endTime").val('');
	 				$("#startTime").val('');
					return false;
				}
			}
		}
		return true;
	}
	$("#maxPayment").on("blur",function(){
		var number = $("#maxPayment").val();
		if(number!=null){
			if(number==""){
				validateTwoPrice();
			}else{
				var regex3 = /^\d+(\.\d{0,2})?$/;
				if(!regex3.test(number)){
					 $("#maxPayment").val("");
					$(".tishi_2").show();
	 				$(".tishi_2").children("p").text("您输入的金额："+number+"不正确，请重新输入！")
	 				setTimeout(function(){ 
	 					$(".tishi_2").hide()
	 				},3000)
				}else{
					validateTwoPrice();
				}
			}
		}
	});
	$('#maxPayment').on("keyup",function(){
		this.value=this.value.match(/\d+(\.\d{0,2})?/g);
	});
	$('#minPayment').on("keyup",function(){
		this.value=this.value.match(/\d+(\.\d{0,2})?/g);
	});
	$('#minPayment').on("blur",function(){
		var number = $("#minPayment").val();
		if(number!=null){
			if(number==""){
				validateTwoPrice();
			}else{
				var regex3 = /^\d+(\.\d{0,2})?$/;
				if(!regex3.test(number)){
					 $("#minPayment").val("");
					$(".tishi_2").show();
	 				$(".tishi_2").children("p").text("您输入的金额："+number+"不正确，请重新输入！")
	 				setTimeout(function(){ 
	 					$(".tishi_2").hide()
	 				},3000)
				}else{
					validateTwoPrice();
				}
			}
		}
	});
	function formSubmit(formId){
		document.getElementById(formId).submit();
	};
	//通知时间框赋值
	$('#time01 li').click(function(){
		$('#startTime').val($(this).text());
	});
	$('#time02 li').click(function(){
		$('#endTime').val($(this).text());
	});
	//设置催付指定时间内未付款
	$('#cuiFu00 li').click(function(){
		$('#cuiFuId').val($(this).text());
	});
	//选中筛选条件赋值给input
	//$('.CUIFUTONGZHISHIJIAN').find('.GXK').hasClass('bgc_check_blue')
	$('.GXKTime1').click(function(){
		$('#filtInput').val('5');
	});
	$('.GXKTime2').click(function(){
		$('#filtInput').val('6');
	});
	//回显过滤条件勾选框
	$(function(){
		var filting = $('#filtInput').val();
		if(filting == 5){
			$('.GXKTime1').addClass('bgc_check_blue');
			$('.GXKTime2').removeClass('bgc_check_blue');
		}
		if(filting == 6){
			$('.GXKTime2').addClass('bgc_check_blue');
			$('.GXKTime1').removeClass('bgc_check_blue');
		}
	});
	//-----------------------------------------高级设置---------------------------------------------
	//提交表单,传入form表单id获取提交表单 
	function submitForm(fomrId){
		document.getElementById(fomrId).submit();
	};
	//使用ajax查询商品数据,回显页面
	function findItem(){
		$('.item_tr').siblings().remove();
		var url = "${ctx}/item/queryItem";
		$.post(url,function (data) {
				var item = data.itemList;
				var item;
				var imgUrl = null;
				if(item ==null || item=="undefined" || item=='' ){
					item = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
					$('.item_table').append(item);
				}else{
					$.each(item,function(i,result){
						if(result.url==null){
							imgUrl =imgUrl= "<img style='width:4.17vw;height:4.17vw;' src='${ctx}/crm/images/no.png'>";
						}else{
							imgUrl= "<img style='width:4.17vw;height:4.17vw;' "+"src='"+result.url+"'>";
						}
						 item = "<tr><td class='h60 text-center'><div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'>"
								+"<input type='hidden' value='"+result.numIid+"'/></div></td>"
								+"<td class='h60 text-center'>"
								+imgUrl
								+"</td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>"; 
						 
						$('.item_table').append(item);
						/* item = "<tr><td class='h60 text-center'><div class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'><input type='hidden' value="+result.numIid+"></div></td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>";
						$('.item_table').append(item); */
					});
					checkItem();
				}
		});
	};
	window.onload = function(){
// 		var smsSign = $("#smsSign").val();
// 		if(smsSign==''||smsSign==null){
// 			var content = $("#textContent1").val();
// 			if(content.startsWith("【短信签名】")){
// 				$(".text_area").val($("#textContent1").val());
// 			}else{
// 				$(".text_area").val("【短信签名】"+$("#textContent1").val());
// 			}
// 		}else{
// 			var content = $("#textContent1").val();
// 			var smsSingChange = "【"+smsSign+"】";
// 			if(content.startsWith(smsSingChange)){
// 				$(".text_area").val($("#textContent1").val());
// 			}else{
// 				$(".text_area").val(smsSingChange+$("#textContent1").val());
// 			}
// 		}
		addLength();
		var msg = $("#msg").val();
		if(msg!=null && msg != ""){
			$(".tishi_2").show();
				$(".tishi_2").children("p").text(msg)
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000);
		}
	}

	function submitFormOrderSetup(fomrId){
		var endTime = $("#endTime").val();
		var startTime = $("#startTime").val();
		var maxPayment = $("#maxPayment").val();
		var minPayment = $("#minPayment").val();
		var maxPaymentFloat = parseFloat(maxPayment);
		var minPaymentFloat = parseFloat(minPayment);
		if(endTime != '' && startTime != ''){
			var startTimeFloat = parseFloat(startTime.substring(0,2));
			var endTimeFloat = parseFloat(endTime.substring(0,2));
			if(endTimeFloat<startTimeFloat){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("保存失败，结束时间不能小于开始时间,请重新设置")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
				return false;
			}else if(endTimeFloat==startTimeFloat){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("保存失败，结束时间不能等于开始时间,请重新设置")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
				return false;
			}else if(maxPaymentFloat<minPaymentFloat){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("保存失败，实付金额最大值不能小于最小值，请重新设置")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
				return false;
			}else{
				document.getElementById(fomrId).submit();
			}
		}else{
			document.getElementById(fomrId).submit();
		}
		/* if(confirm("确定要保存吗?")){
			var flag = validateTwoPrice();
			if(flag == true){
				flag = validateTwoTime();
				if(flag){
					document.getElementById(fomrId).submit();
				}
			}
//	 		document.getElementById(fomrId).submit();
		} */
	};
	//使用ajax通过查询条件查询商品信息
	function findItemByData(){
		$('.item_tr').siblings().remove();
		var itemId = $("#itemId_input").val();//获取商品ID
		var itemName = $("#itemName_input").val();//关键字(商品名称)
		var status = $("#status_input").val();//获取只显示上架

		
		var url = "${ctx}/item/queryItem";
		$.post(url,{"commodityId":itemId,"name":itemName,"status":status},function (data) {
				var item = data.itemList;
				var item;
				var imgUrl = null;
				if(item ==null || item=="undefined" || item=='' ){
					item = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
					$('.item_table').append(item);
				}else{
					$.each(item,function(i,result){
						if(result.url==null){
							imgUrl =imgUrl= "<img style='width:4.17vw;height:4.17vw;' src='${ctx}/crm/images/no.png'>";
						}else{
							imgUrl= "<img style='width:4.17vw;height:4.17vw;' "+"src='"+result.url+"'>";
						}
						 item = "<tr><td class='h60 text-center'><div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'>"
								+"<input type='hidden' value='"+result.numIid+"'/></div></td>"
								+"<td class='h60 text-center'>"
								+imgUrl
								+"</td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>"; 
						 
						$('.item_table').append(item);
						/* item = "<tr><td class='h60 text-center'><div class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'><input type='hidden' value="+result.numIid+"></div></td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>";
						$('.item_table').append(item); */
					});
					checkItem();
				}
		});
	};
	
	//只显示上架设置内容
	var shieldNum = 0;
	$(function(){
		$("#putaway_div").click(function(){
			if(shieldNum%2==0){
				$("#status_input").val('1');
			}else{
				$("#status_input").val('2');
			}
			shieldNum++;
			
		});
	});
	
	
	//当点击确定时获得选中的商品id
	function addItemId(){
		var itemIds="";
		var divCheck = $(".item_check_div");
		for(var i=0;i<divCheck.length;i++){
			if(divCheck.eq(i).hasClass("bgc_check_blue")){
				 var val = divCheck.eq(i).children().val();
				 itemIds+=val+",";
			}
		}
		itemIds=itemIds.substring(0,itemIds.length-1);
		$("#appoint_ItemId").val(itemIds);
	};
	
	//商品选择--获取标识,或商品id
	function addItemflag(flag){
		$("#appoint_ItemId").val('');
		var productSelect = $("#productSelect").val();
		$("#item_select").val(flag);
		
		if(flag == productSelect ){
			var appoint_ItemId = $("#itemId").val();
			$("#appoint_ItemId").val(appoint_ItemId);
			checkItem();
		}
	};
	
	
	//会员等级--获取标识
	function addMemberGrade(){
		//延迟加载获取值
		setTimeout(function () { 
			var memberflag="";
			var divCheck = $(".member_grade_div");
			for(var i=0;i<divCheck.length;i++){
				if(divCheck.eq(i).hasClass("bgc_check_blue")){
					 var val = divCheck.eq(i).children().val();
					 memberflag+=val+",";
				}
			}
			memberflag=memberflag.substring(0,memberflag.length-1);
			$("#member_grade").val(memberflag);
		}, 100);
	};
	
	//订单来源--获取标识
	function addOrderSource(flag){
		$("#order_source").val(flag);
	};
	
	//卖家标记  -- 获取标识
	function addVendorflag(flag){
		$("#vendor_flag").val(flag);
		if(flag==0){
			$("#shield_flag").val('');
		}else{
			$(".vendor_flag_div").addClass("bgc_check_blue");
			addShieldflag();
		}
	};
	//屏蔽卖家镖旗
	function addShieldflag(){
		//延迟加载获取值
		setTimeout(function () { 
		
			var shieldflag="";
			var divCheck = $(".vendor_flag_div");
			for(var i=0;i<divCheck.length;i++){
				if(divCheck.eq(i).hasClass("bgc_check_blue")){
					 var val = divCheck.eq(i).children().val();
					 shieldflag+=val+",";
				}
			}
			shieldflag=shieldflag.substring(0,shieldflag.length-1);
			$("#shield_flag").val(shieldflag);
		}, 100);
	};
	
	
	//地区筛选--获取标识
	function addRegionflag(flag){
		$("#region_name").val(flag);locality
		//判断传入的数字给出对应的值
		if(flag==1){
			$("#region_name").val("上海市,浙江省,江苏省");
		}
		if(flag==2){
			$("#region_name").val("新疆维吾尔自治区,云南省,青海省,西藏自治区");
		}
		if(flag==3){
			customRegion();
		}
	};
	
	//选择自定义地区的时获取勾选的地区
	function customRegion (){
		var regionName = "";
		var getprovince=$(".place_check").children().find('.li_').children('.bgc_check_blue').next().children('input');
		for(var i=0;i<getprovince.length;i++){
			regionName+=getprovince.eq(i).val()+",";
		}
		regionName=regionName.substring(0,regionName.length-1);
		$("#region_name").val(regionName);
	};
	
	
	//点击高级设置的保存获取设置的参数复制给对象的隐藏form表单提交数据
	function submitFormAdvanced(fomrId){
		//地区数据
		var locality = $("#region_name").val();
		$("#locality").val(locality);//添加到提交后台form表单input中
		
		//卖家标记--标识
		var vendormark = $("#vendor_flag").val();
		$("#vendormark").val(vendormark);//添加到提交后台form表单input中
		
		//卖家标记--屏蔽的卖家标记小旗颜色
		var flagcolor = $("#shield_flag").val();
		$("#flagcolor").val(flagcolor);//添加到提交后台form表单input中
		
		//订单来源
		var orderSource  = $("#order_source").val();
		$("#orderSource").val(orderSource);//添加到提交后台form表单input中
		
		//会员等级
		var memberLevel = $("#member_grade").val();
		$("#memberLevel").val(memberLevel);//添加到提交后台form表单input中
		
		//商品选择--标识
		var productSelect = $("#item_select").val();
		$("#productSelect").val(productSelect);//添加到提交后台form表单input中
		if(productSelect==""||productSelect==null){
			productSelect=0
		}

		//商品选择--商品id
		var itemIds = $("#appoint_ItemId").val();
		$("#itemId").val(itemIds);//添加到提交后台form表单input中
		
		//提交表单
		document.getElementById(fomrId).submit();
	};
	
	//获取会员等级回显设置数据
	$(function(){
  		var p = $("#memberLevelId");//获取标签
  		var memberShow = "";
		var memberLevel = $("#memberLevel").val();
		if(memberLevel != null && memberLevel !=""){
			if(memberLevel=='0'){
				p.text("不限");
			}else{
				var memberLeveVal = memberLevel.split(",");
				for(var i=0;i<memberLeveVal.length;i++){
					if(memberLeveVal[i]=='1'){
						memberShow+="首次到店会员     ";
					}
					if(memberLeveVal[i]=='2'){
						memberShow+="店铺会员       ";			
					}
					if(memberLeveVal[i]=='3'){
						memberShow+="普通会员       ";
					}
					if(memberLeveVal[i]=='4'){
						memberShow+="高级会员      ";
					}
					if(memberLeveVal[i]=='5'){
						memberShow+="VIP会员      ";
					}
					if(memberLeveVal[i]=='6'){
						memberShow+="至尊VIP会员   ";
					}
	
				}
				//添加到标签中
				p.text(memberShow);
			}
		}
		
	});
	
	
	//设置编辑部分的选中状态
	$(function(){
		//1,设置地区选中状态
		var locality = $("#locality").val();
		if(locality==''){
			$("#dq0").addClass("bgc_check_blue");
		}else if(locality=='0'){
			$("#dq0").addClass("bgc_check_blue");
		}else if(locality=='上海市,浙江省,江苏省'){
			$("#dq1").addClass("bgc_check_blue");
			$("#dq0").removeClass("bgc_check_blue");
			
		}else if(locality=='新疆维吾尔自治区,云南省,青海省,西藏自治区'){
			$("#dq2").addClass("bgc_check_blue");
			$("#dq0").removeClass("bgc_check_blue");
		}else{
			$("#dq3").addClass("bgc_check_blue");
			$("#dq0").removeClass("bgc_check_blue");
			var localityList = locality.split(",");
			var getprovince = $(".place_check").children().find('.li_').children().next().children('input');
			for(var i=0;i<localityList.length;i++){
				for(var j=0;j<getprovince.length;j++){
					if(getprovince.eq(j).val()==localityList[i]){
						getprovince.eq(j).parent().prev().addClass("bgc_check_blue");
					}
				}
			}
		}
		
		//2.卖家标记--标识
		var vendormark = $("#vendormark").val();
		if(vendormark=='0'){
			$("#mj0").addClass("bgc_check_blue");
		}else if(vendormark=='1') {
			$("#mj1").addClass("bgc_check_blue");
			$("#mj0").removeClass("bgc_check_blue");
			
		}
		
		//3.卖家标记--屏蔽的卖家标记小旗颜色
		var flagcolor = $("#flagcolor").val();
		var flagcolorList = flagcolor.split(",");
		for(var i=0;i<flagcolorList.length;i++){
			if(flagcolorList[i]=='1'){
				$("#mjC1").addClass("bgc_check_blue");
			}
			if(flagcolorList[i]=='2'){
				$("#mjC2").addClass("bgc_check_blue");
			}
			if(flagcolorList[i]=='3'){
				$("#mjC3").addClass("bgc_check_blue");
			}
			if(flagcolorList[i]=='4'){
				$("#mjC4").addClass("bgc_check_blue");
			}
			if(flagcolorList[i]=='5'){
				$("#mjC5").addClass("bgc_check_blue");
			}
		}
	
		//4.订单来源 
		var orderSource = $("#orderSource").val();
		if(orderSource == '0'){
			$("#dl0").addClass("bgc_check_blue");
		}
		if(orderSource == '1'){
			$("#dl1").addClass("bgc_check_blue");
			$("#dl0").removeClass("bgc_check_blue");
		}
		if(orderSource == '2'){
			$("#dl2").addClass("bgc_check_blue");
			$("#dl0").removeClass("bgc_check_blue");
		}
		
		//5.会员等级
		var memberLevel = $("#memberLevel").val();
		if(memberLevel=='0'){
			$("#hy0").addClass("bgc_check_blue");
		}else{
			var memberLevelList = memberLevel.split(",");
			for(var i=0;i<memberLevelList.length;i++){
				if(memberLevelList[i]=='1'){
					$("#hy1").addClass("bgc_check_blue");
					$("#hy0").removeClass("bgc_check_blue");
				}
				if(memberLevelList[i]=='2'){
					$("#hy2").addClass("bgc_check_blue");
					$("#hy0").removeClass("bgc_check_blue");
				}
				if(memberLevelList[i]=='3'){
					$("#hy3").addClass("bgc_check_blue");
					$("#hy0").removeClass("bgc_check_blue");
				}
				if(memberLevelList[i]=='4'){
					$("#hy4").addClass("bgc_check_blue");
					$("#hy0").removeClass("bgc_check_blue");
				}
				if(memberLevelList[i]=='5'){
					$("#hy5").addClass("bgc_check_blue");
					$("#hy0").removeClass("bgc_check_blue");
				}
				if(memberLevelList[i]=='6'){
					$("#hy6").addClass("bgc_check_blue");
					$("#hy0").removeClass("bgc_check_blue");
				}
			}
		}
		
		//6商品选择--标识
		var productSelect = $("#productSelect").val();
			if(productSelect=='0'){
				$("#sp0").addClass("bgc_check_blue");
			}
			if(productSelect=='1'){
				$("#sp1").addClass("bgc_check_blue");
				$("#sp_z0").show();
				$("#sp0").removeClass("bgc_check_blue");
			}
			if(productSelect=='2'){
				$("#sp2").addClass("bgc_check_blue");
				$("#sp_z1").show();
				$("#sp0").removeClass("bgc_check_blue");
			}
		
	
			
			//获取form表单的后台数据回显到对应的前台input中
			//地区数据
			var locality = $("#locality").val();
			if(locality !=''){
				$("#region_name").val(locality);
			}
			
			
			//卖家标记--标识
			var vendormark = $("#vendormark").val();
			if(vendormark !=''){
				$("#vendor_flag").val(vendormark);
			}
			
			//卖家标记--屏蔽的卖家标记小旗颜色
			var flagcolor = $("#flagcolor").val();
			if(flagcolor !=''){
				$("#shield_flag").val(flagcolor);
			}
			if(vendormark == '1'){
				$(".XiaoQiQi").show();
			}
			//订单来源
			var orderSource  = $("#orderSource").val();
			if(orderSource !=''){
				$("#order_source").val(orderSource);
			}
			
			//会员等级
			var memberLevel = $("#memberLevel").val();
			if(memberLevel !=''){
				$("#member_grade").val(memberLevel);
			}
			
			//商品选择--标识
			var productSelect = $("#productSelect").val();
			if(productSelect !=''){
				$("#item_select").val(productSelect);
			}
	
			//商品选择--商品id
			var itemIds = $("#itemId").val();
			if(itemIds !=''){
				$("#appoint_ItemId").val(itemIds);
			}
			//修改设置开启关闭状态
			var status = $('#statusId').val();
			if(status == '0'){
				$('#kaiQiId').val('已开启催付提醒');
				$('#liuCh2').val("liuCh20");
			}else if(status == '1'){
				$('#kaiQiId').val('已关闭催付提醒');
				$('.liuCh2').addClass("display_none");
			}
			
			
	});	
	
	//回显已选择商品选中状态
	function checkItem(){
		var itemidInput = $(".item_check_div");
		var itemIds = $("#appoint_ItemId").val();
		var itemIdList = itemIds.split(",");
		for(var i=0;i<itemidInput.length;i++){
			 for(var j=0;j<itemIdList.length;j++){
				if(itemidInput.eq(i).children().val()==itemIdList[j]){
					itemidInput.eq(i).addClass("bgc_check_blue");
				}
			} 
		}
	};
//--------------------------------------------------短信设置---------------------------------------------------------	
	//将先中模板的内容添加到内容框，并修改输入文字，修改计费条数
	/* function addContent(content){
		var textcontent= document.getElementById("textContent").value;
		$(".text_area").val(content);			
		$("#textContent1").val(content);
		$(".text_model_window").hide();		
		document.getElementById("inputNum").innerText=content.length;
		if(content.length>70){
			document.getElementById("contenPrice").innerText=Math.ceil(content.length/67);			
		}
	}; */
	//提交短信form
	function smsSubmit(formId){
		//内容赋值给input
		var messageSignature = $('.sms_sign').val();
		$('#smsSign').val(messageSignature);
		var messageContent = $('#textContent').val();
		$('#smsContent').val(messageContent);
		if(messageContent == "" || messageContent == null){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容不能为空!!!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
		}else if(messageContent.indexOf("【" + messageSignature + "】")<0){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请核对短信签名是否正确!!!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
		}else{
			  $(".duanxinshezhibukebianji").show();
			  $(".DXSZBianJiBuFen").hide();
			  $(".iphone").hide();
			  $(".Letter").val($(".Letter11").val());
			document.getElementById(formId).submit();
		}
	}
	//判断短信输入框内容长度，修改计费条数
	function addLength(){
		var contentLength = $(".text_area").val().length;
		var refundMSGLength = $("#refundMSG").length;
		var length=contentLength+ 3 +refundMSGLength;
		var contentVal=$("#textContent").val();
		$("#textContent1").val(contentVal);
		var count = parseInt((length+66)/67);
		if(length>70){
			$("#actualDeduction_input").val(count);
			document.getElementById("contenPrice").innerText=count;			
		}else if(length<=70){
			$("#actualDeduction_input").val(1);
			document.getElementById("contenPrice").innerText=1;
		}
		document.getElementById("inputNum").innerText=length;
		
		
		//将小手机里的内容末尾添加退订回N 
		if(contentVal == ""){
			$(".text_area_copy").val(contentVal);
			document.getElementById("inputNum").innerText=0;
			document.getElementById("contenPrice").innerText=0;
		}else{
			$(".text_area_copy").val(contentVal.replace("退订回N","")+"退订回N");
		}
	};
	
	$('.ydh').click(function(){
		var messageVartotal = $('#messageId').val();
		var messageVar = $(this).children('input').val();
		var str;
		if(messageVartotal == ''){
			messageVartotal = messageVar;
		}else{
			str = messageVartotal.split(",");
			for(var i=0;i<str.length;i++){
				if(str[i] == messageVar){
					return ;
				}
			}
			messageVartotal = messageVartotal +","+ messageVar;
		}
		$('#messageId').val(messageVartotal);
		
	});
	
	//显示引用短语库
	function findTemplate(smsTemp){
		$('.temp_tr1').siblings().remove();
		$('.temp_tr2').siblings().remove();

		$(".DYK2").eq(0).show()
		$(".DYK2").eq(1).hide()
		
		$(".DYK1").eq(0).addClass("bgc_fff").removeClass("bgc_e3e7f0")
		$(".DYK1").eq(1).removeClass("bgc_fff").addClass("bgc_e3e7f0")
		
		
		if(smsTemp=="默认"){
			$(".default").children(".GXK1").addClass("bgc_check_blue");
			$(".GXK1").parent(".default").siblings().children(".GXK").removeClass("bgc_check_blue")
		}
		$.ajax({
			url:"${ctx}/crms/smsTemplate",
			type:"post",
			data:{"type":smsTemp},
			dataType:"json",
			success:function(data){
				var smsTemplate = data.smsTemplate;
				var appendTemp = "";
				if(smsTemp == "自定义"){
					if(smsTemplate ==null || smsTemplate=="undefined" || smsTemplate=='' ){
						appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
						$('.temp_table2').append(appendTemp);
					}else{
						$.each(smsTemplate,function(i,result){
							appendTemp = "<tr class='bgc_fafafa'><td class='w77 h50 text-center' >"+(i+1)+"</td><td class='w500 h50 text-center'>"+result.name+"</td><td class='w128 h50 text-center'>"+result.content+"</td><td class='w120 h50 text-center'><div  class='w100 h42 l_h42 bor_00a0e9 margin0_auto b_radius5 c_00a0e9 Template_div cursor_'>使用<input type='hidden' value='"+result.content+"' /></div></td></tr>";
							$('.temp_table2').append(appendTemp);
						});
					}
				}else{
					if(smsTemplate ==null || smsTemplate=="undefined" || smsTemplate=='' ){
						appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
						$('.temp_table1').append(appendTemp);
					}else{
						$.each(smsTemplate,function(i,result){
							appendTemp = "<tr class='bgc_fafafa'><td class='w77 h50 text-center' >"+(i+1)+"</td><td class='w500 h50 text-center'>"+result.content+"</td><td class='w128 h50 text-center'>"+result.fashion+"</td><td class='w120 h50 text-center'><div  class='w100 h42 l_h42 bor_00a0e9 margin0_auto b_radius5 c_00a0e9 Template_div cursor_'>使用<input type='hidden' value='"+result.content+"' /></div></td></tr>";
							$('.temp_table1').append(appendTemp);																																																					
						});
					}
				}
			}
		});
	};
	
	//另存为短语库
	function saveTemplate(){
		//短语模板的名字
		var smsName = $('#saveSms').val();
		var smsContent = $('.text_area').val();
		$.ajax({
			url:"${ctx}/crms/saveSmsTemplate",
			type:"post",
			data:{"smsName":smsName,"smsContent":smsContent},
			success:function(data){
				if(data.message){
 					$(".tishi_2").show();
 					$(".tishi_2").children("p").text("保存短语成功")
 					setTimeout(function(){ 
 						$(".tishi_2").hide()
 					},3000)
				}else{
 					$(".tishi_2").show();
 					$(".tishi_2").children("p").text("保存短语失败")
 					setTimeout(function(){ 
 						$(".tishi_2").hide()
 					},3000)
				}
			}
		});
	};
	//点击使用将模板添加到文本域中
	$(document).on("click", ".Template_div", function () {
		var content = $(this).children().val();
		var changeContent = $("#textContent").val();
		var endContent = changeContent+""+content;
		$("#messageId").val('');
		$(".text_area").val(endContent);			
		//$(".text_area").val("【关欣康家用医疗器械】"+content);			
		addLength();
		$(".text_area_copy").text($("#textContent").val());
		//启用滚动条
		  $(document.body).css({
		  "overflow-x":"auto",
		  "overflow-y":"auto"
		  });
		$(".text_model_window").hide();	
	});
	//点击使用将模板添加到文本域中
	/* $(document).on("click", ".Template_custom", function () {
		var content = $(this).children().val();
		var ShopName = ${ShopName};
		$("#messageId").val('');
		$(".text_area").val(ShopName+content);			
		$("#textContent1").val(content);
		$(".text_model_window").hide();		
		document.getElementById("inputNum").innerText=content.length;
		if(content.length>70){
			document.getElementById("contenPrice").innerText=Math.ceil(content.length/67);			
		}
	}); */
	
	//光标点击
	function addConsignee(obj,str) {
    	var content= document.getElementById("textContent");	
	    if(content.value.split(str).length-1>0){
			return ;
	    }else{
	    	if (document.selection) {
		        var sel = document.selection.createRange();
		        sel.text = str;
		    } else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
		        var startPos = obj.selectionStart,
		            endPos = obj.selectionEnd,
		            cursorPos = startPos,
		            tmpStr = obj.value;
		        obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
		        cursorPos += str.length;
		        obj.selectionStart = obj.selectionEnd = cursorPos;
		    } else {
		        obj.value += str;
		    }
		    $('.text_area_copy').text($('.text_area').val());
		    addLength();
	    }
	};
	/* $(document).on("click", ".Template_div", function () {
		//获取短信内容
		var textContent = $('#textContent').val();
		var content = $(this).children().val();
		$(".text_area").val(textContent+content);			
		$("#textContent1").val(content);
		$(".text_model_window").hide();		
		document.getElementById("inputNum").innerText=content.length;
		if(content.length>70){
			document.getElementById("contenPrice").innerText=Math.ceil(content.length/67);			
		}
		addLength();
	}); */

	//测试短信发送
	$(".ceShiFaSong1").click(function(){
		var that=$(this);
		if($(this).hasClass('on'))return;
		$(this).addClass('on');	
		    var phonez=/^1[3|4|5|7|8]\d{9}$/;
			var phone=$(".Phone").val();//获取电话
			var content = $("#textContent1").val();//获取短信内容
			var autograph = $("#smsSign").val();//短信签名
			
			var phoneflag = 1;//手机号发送判断标识
			
		if(content != ""){
			content = content.replace("退订回N","")+"退订回N";
			//判断号码内容是否成功 
			var phoneList = phone.split(",");
			if(phoneList.length <= 5){
				for(var i=0;i<phoneList.length;i++){
					if (phoneList[i].match(phonez)){
						phoneflag = 0;
					} else{
						phoneflag=1;
						break;
					}
				}	
			}else{
				phoneflag=2;
			}	
			
			if(phoneflag == 0){
			 	var url = "${ctx}/crm/test/sendSMS";
				$.post(url,{"content":content,"phone":phone,"autograph":autograph},function (data) {
					that.removeClass('on');
						if(data.send=='100'){
			 				$(".tishi_2").show();
			 				$(".tishi_2").children("p").text("发送成功!")
			 				setTimeout(function(){ 
			 					$(".tishi_2").hide()
			 				},3000)
						}else{
			 				$(".tishi_2").show();
			 				$(".tishi_2").children("p").text("发送失败!")
			 				setTimeout(function(){ 
			 					$(".tishi_2").hide()
			 				},3000)
						}
				});
			}else if(phoneflag == 1){
				that.removeClass('on');
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("手机号错误!")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)   
			}else if(phoneflag==2){
				that.removeClass('on');
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("手机号不能大于5个")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)   
			}
		}else{
			that.removeClass('on');
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容不能为空!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)  
		}
	
	});
	//点击开启或关闭设置
	$('.lijikaiqi').click(function(){
		var id = $('#setupId').val();
		var status = $('#statusId').val();
		if(status == '1'){
			$('#statusId').val('0');
			status = '0';
		}else if(status == '0'){
			$('#statusId').val('1');
			status = '1';
		}else{
			$('#statusId').val('1');
			status == '1';
		}
		if(id == ''){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("启奏陛下！基本设置还未保存哦~")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)  
			return;
		}else{
			var url = "${ctx}/crms/updateStatus";
			$.post(url,{"id":id,"status":status},function(data){
				if(data.message){
					$(".tishi_2").show();
	 				$(".tishi_2").children("p").text("设置成功")
	 				setTimeout(function(){ 
	 					$(".tishi_2").hide()
	 				},3000)
					/*  0--开启 1--关闭   */
					if(status == '1'){
						$('#kaiQiId').val('已关闭催付提醒');
						loadMap();
					}else if(status == '0'){
						$('#kaiQiId').val('已开启催付提醒');
						loadMap();
					}
				}else{
					$(".tishi_2").show();
	 				$(".tishi_2").children("p").text("设置失败")
	 				setTimeout(function(){ 
	 					$(".tishi_2").hide()
	 				},3000)
				}
			},"json");
		}
	});
	//回显短信到编辑部分
	$(function(){
		var textContent1 = $('#textContent1').val();
		$('#textContent').val(textContent1);
		addLength();
	});
	
	

	//获得生成短连接的类型
	function linkData(data){
		$('#linkType').val(data);
	}
	//短连接生成
	 function linkDataInput(data){
		$("#linkData_input").val(data);
	}
	//根据生成链接类型，将请求发送到后台生成短链接
	function linkSubmit(){
		var linkData = $("#linkData_input").val();
		var linkType = $("#linkType").val();//链接类型
		var commodityId = $("#commodityId").val();//商品id
		var activityUrl = $("#activityUrl").val();//活动网址
		var kehuduan = $("#kehuduan").val();
		if(linkData == 0){
			var url = "${ctx}/GenerateLinkTo/generateLink";
			$.post(url,{"linkType":linkType,"commodityId":commodityId,"activityUrl":activityUrl,"linkName":linkType},function (data) {
				if(data.link != null){
					 $("#textContent").val(startContent+data.link+endContent);
					 addLength();
				}
			});
		}else{
			var textContent = $("#textContent").val();
			$("#textContent").val(textContent+kehuduan);
			addLength();
		}
		
		
	};
	
	
	//点击取消获取之前短信放入内容
	
	$("#cancel").click(function(){
		var contentShow = $("#contentShow").val();
		$("#textContent").val(contentShow);
		$(".text_area_copy").val(contentShow);
		addLength();
	});
	
	//定位光标的位置，将内容分割成两个部分
	var startContent,endContent;
	function getCursortPosition(obj){		
		if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
	        var startPos = obj.selectionStart,
	            endPos = obj.selectionEnd,
	            cursorPos = startPos,
	            tmpStr = obj.value;
	        startContent =tmpStr.substring(0, startPos);
	        endContent =tmpStr.substring(endPos, tmpStr.length);
	    } 
	}
	
	
	//点击快捷键开关
	$("#img_cg").click(function(){
		var status  = $(this).next().val();
		if(status != ''){
			if(status == '0'){
				$('#statusId').val('1');
				$('#kaiQiId').val('已关闭催付提醒');
			}else if(status == '1'){
				$('#statusId').val('0');
				$('#kaiQiId').val('已开启催付提醒');
			}
		}
	});
	
	 //提交form表单
	function findOrderSendRedord(formId){
		document.getElementById(formId).submit();
	};
</script>
</html>
