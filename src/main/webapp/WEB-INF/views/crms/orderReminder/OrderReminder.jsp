<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>手动订单提醒</title>
<%@ include file="/common/common.jsp"%>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!--兼容360meta-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="renderer" content="webkit">

<%--<link type="text/css" rel="stylesheet" href="${ctx}/crm/css/jedate.css">--%>


<!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
<!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js"></script> --%>
<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js"></script>
<script type="text/javascript"
	src="${ctx}/crm/js/shoudongdingdantixing.js"></script>
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
							<li class="f_l w140 text-center"><a
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
							<li class="w140 bgc_fff text-center bgc_f1f3f7 f_l"><a
								class="c_384856 display_block"
								href="${ctx}/OrderReminder/jumOrderReminder"> 手动订单提醒 </a></li>
							<li style="clear: both;"></li>
						</ul>
					</div>
				</div>
				<div class="w1660  bgc_f1f3f7 c_384856">
					<div class="p_l40 p_t27">
						<p class="font20">手动订单提醒</p>
						<p class="font14 p_t8">当客户下单后超过一定时间后，软件会自动发送短信给客户提醒客户付款，提高转化率就靠它了！</p>

						<input type="hidden" id="msg" name="msg" value="${msg}">
						<div class="h40">
							<div
								class="w110 h40 l_h40 f_r bgc_fff m_r15 text-center  cursor_ ZhanKai">
								<img class="m_r10 JTX display_none"
									src="${ctx}/crm/images/箭头下.png" /> <img class="m_r10 JTS"
									src="${ctx}/crm/images/箭头上.png" /> <span class="LiuChengText">收回流程</span>
							</div>
						</div>
					</div>
					<%@ include file="/WEB-INF/views/crms/header/liucheng.jsp"%>
					<ul class="h40 p_t38 font14 p_l40">
						<a href="${ctx}/OrderReminder/jumOrderReminder"><li
							class="f_l w140 text-center h40 l_h40 bgc_fff cursor_ c_384856">手动提醒</li></a>
						<a href="#" onclick="findOrderSendRedord('formId')">
							<li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ c_384856">发送列表</li>
						</a>
					</ul>
				</div>
				<form action="${ctx}/sendRecord/orderCenterRecord" method="post"
					id="formId">
					<input type="hidden" name="recordFlag" value="10">
				</form>
				<!--选择商品弹出框start-->
				<div class="rgba_000_5 w1920 h100_ ChoiceSpecified_ display_none">
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
									<span
										class="cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 m_t13 f_l VIPGXK">
										<img class="cursor_ one_check_ display_none"
										src="${ctx}/crm/images/black_check.png" /> <input
										id="status_input" type="hidden" value="2" />
									</span> <span class="m_l10"> 只显示上架 <input
										onclick="findItemByData();"
										class="h40 w80 border0 outline_none b_radius5 m_l35 bgc_00a0e9 c_fff cursor_"
										type="button" value="搜索" />
									</span>
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
							<!--分页样式start-->

							<!--确定保存start-->

							<div class="w936 h42 m_t50 margin0_auto">
								<div class="w214 margin0_auto">
									<div onclick="addItemId();"
										class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ SpecifiedOut">
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
				<div
					class="rgba_000_5 w100_ area_check_window h100_ position_fixed top0 z_13 display_none">
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
						<div class="h40 margin0_auto w231">
							<input
								class="w100 bgc_fff border_00a0e9 c_00a0e9 tk b_radius5 h40 font16 close_1 cursor_"
								type="submit" value="确定" /> <input
								class="w100 bgc_fff border_00a0e9 c_00a0e9 tkb_radius5  h40 font16 close_1 m_l20 cursor_"
								type="submit" value="取消" />
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
						<div class="h64 lh64 m_b10 text-center font16 ClickPhrase">另存为短语库</div>
						<div class="w426 margin0_auto">
							<input id="saveSms"
								class="w411 h42 b_radius5 border0 outline_none bgc_f4f6fa p_l15 c_cecece"
								type="text" value="模板名称" />
						</div>

						<!--确定保存start-->
						<div class="w490 h42 m_t36 margin0_auto">
							<div class="w260 margin0_auto">
								<div
									class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ PhraseOut"
									onclick="saveTemplate()">确定</div>
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

				<!----------------------------------------------------------------------------------------       订单营销                    ----------------------------------------------------------------------------------------------->
				<div class="w1660 bgc_fff TABLE2">

					<div class="w1590  c_384856 p_l50 font16 p_t40 min_h400">
						<div class="w1030">
							<div class="h47">
								<p>
									基本设置<span class="font14 c_00a0e9 p_l10 cursor_  C_XG">修改</span>
								</p>
								<c:choose>
									<c:when test="${orderReminder!=null }">
										<input id="statusId" type="hidden" name="status" value="1">
										<input id="setupId" type="hidden" name="id"
											value="${orderReminder.id}">
									</c:when>
								</c:choose>
							</div>
							<div class="die_SZ">
								<div class="w630 f_l">
									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">订单时间:</div>
										<c:choose>
											<c:when test="${orderReminder.orderDateType =='1' }">
												<div class="f_l Time1">下单时间</div>
											</c:when>
											<c:when test="${orderReminder.orderDateType =='2' }">
												<div class="f_l Time1">付款时间</div>
											</c:when>
											<c:when test="${orderReminder.orderDateType =='3' }">
												<div class="f_l Time1">发货时间</div>
											</c:when>
											<c:when test="${orderReminder.orderDateType =='4' }">
												<div class="f_l Time1">变更时间</div>
											</c:when>
											<c:when test="${orderReminder.orderDateType =='5' }">
												<div class="f_l Time1">结束时间</div>
											</c:when>
											<c:otherwise>
												<div class="f_l Time1">不限</div>
											</c:otherwise>
										</c:choose>

										<div class="f_l Time2 m_l20">${startTime}</div>
										<div class="f_l Time3 m_l20">${endTime}</div>

									</div>

									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">订单状态:</div>
										<c:if test="${ orderReminder.orderStatus !=null}">
											<c:choose>
												<c:when
													test="${ orderReminder.orderStatus=='WAIT_BUYER_PAY'}">
													<div class="f_l dingdanzhuangtai">已下单未付款</div>
												</c:when>
												<c:when
													test="${ orderReminder.orderStatus=='WAIT_SELLER_SEND_GOODS'}">
													<div class="f_l dingdanzhuangtai">已付款未发货</div>
												</c:when>
												<c:when
													test="${ orderReminder.orderStatus=='WAIT_BUYER_CONFIRM_GOODS'}">
													<div class="f_l dingdanzhuangtai">卖家已发货</div>
												</c:when>
												<c:when
													test="${ orderReminder.orderStatus=='SELLER_CONSIGNED_PART'}">
													<div class="f_l dingdanzhuangtai">卖家部分发货</div>
												</c:when>
												<c:when
													test="${ orderReminder.orderStatus=='TRADE_FINISHED'}">
													<div class="f_l dingdanzhuangtai">交易成功</div>
												</c:when>
												<c:when
													test="${ orderReminder.orderStatus=='REFUND_SUCCESS'}">
													<div class="f_l dingdanzhuangtai">已退款</div>
												</c:when>
												<c:when
													test="${ orderReminder.orderStatus=='TRADE_CLOSED_BY_TAOBAO'}">
													<div class="f_l dingdanzhuangtai">交易关闭</div>
												</c:when>
												<c:when test="${ orderReminder.orderStatus=='ALL_ORDERS'}">
													<div class="f_l dingdanzhuangtai">全部订单</div>
												</c:when>
												<c:otherwise>
													<div class="f_l dingdanzhuangtai">全部订单</div>
												</c:otherwise>
											</c:choose>
										</c:if>
										<c:if test="${orderReminder.orderStatus ==null }">
											<div class="f_l dingdanzhuangtai">全部订单</div>
										</c:if>
									</div>

									<%-- <div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">预售状态:</div>
										<c:choose>
											<c:when test="${ orderReminder.bookingStatus==1}">
												<div class="f_l yushouzhuangtai">订金未付余款未付</div>
											</c:when>
											<c:when test="${ orderReminder.bookingStatus==2}">
												<div class="f_l yushouzhuangtai">已付款未发货</div>
											</c:when>
											<c:when test="${ orderReminder.bookingStatus==3}">
												<div class="f_l yushouzhuangtai">定金和尾款都付</div>
											</c:when>
											<c:otherwise>
												<div class="f_l yushouzhuangtai">不限</div>
											</c:otherwise>
										</c:choose>
									</div> --%>


									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">订单金额:</div>
										<c:choose>
											<c:when
												test="${orderReminder.minOrderPrice !=null and  orderReminder.maxOrderPrice !=null}">
												<div class="f_l Money" id="minMoney">${orderReminder.minOrderPrice}</div>
												<div class="f_l Money">~</div>
												<div class="f_l Money" id="maxMoney">${orderReminder.maxOrderPrice}</div>
											</c:when>
											<c:otherwise>
												<div class="f_l Money">不限</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">交易次数:</div>
										<c:choose>
											<c:when
												test="${orderReminder.minTradeNum !=null and  orderReminder.maxTradeNum !=null}">
												<div class="f_l jiaoyicishu">${orderReminder.minTradeNum}</div>
												<div class="f_l jiaoyicishu">~</div>
												<div class="f_l jiaoyicishu">${orderReminder.maxTradeNum}</div>
											</c:when>
											<c:otherwise>
												<div class="f_l jiaoyicishu">不限</div>
											</c:otherwise>
										</c:choose>
									</div>

									<%-- <div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">订单类型:</div>
										<c:choose>
											<c:when test="${ orderReminder.orderType==1}">
												<div class="f_l dingdanleixing">预售</div>
											</c:when>
											<c:when test="${ orderReminder.orderType==2}">
												<div class="f_l dingdanleixing">非预售</div>
											</c:when>
											<c:otherwise>
												<div class="f_l dingdanleixing">不限</div>
											</c:otherwise>
										</c:choose>
									</div> --%>

									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">评价状态:</div>
										<c:choose>
											<c:when test="${ orderReminder.evaluateStatus==1}">
												<div class="f_l pingjiazhuangtai">买家未评价</div>
											</c:when>
											<c:when test="${ orderReminder.evaluateStatus==2}">
												<div class="f_l pingjiazhuangtai">买家未评价，卖家已评价</div>
											</c:when>
											<c:when test="${ orderReminder.evaluateStatus==3}">
												<div class="f_l pingjiazhuangtai">买家已评价</div>
											</c:when>
											<c:otherwise>
												<div class="f_l pingjiazhuangtai">不限</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="w400 f_l">
									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">地区筛选:</div>
										<c:choose>
											<c:when test="${ orderReminder.province eq '上海市,浙江省,江苏省'}">
												<div class="f_l DiQu">江浙沪</div>
											</c:when>
											<c:when
												test="${ orderReminder.province eq '新疆维吾尔自治区,云南省,青海省,西藏自治区'}">
												<div class="f_l DiQu">偏远地区</div>
											</c:when>
											<c:when test="${ orderReminder.province eq '0'}">
												<div class="f_l DiQu">默认全部</div>
											</c:when>
											<c:otherwise>
												<div class="f_l DiQu">自定义</div>
											</c:otherwise>
										</c:choose>
									</div>

									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">卖家标记:</div>
										<c:choose>
											<c:when test="${ orderReminder.sellerSign eq '0'}">
												<div class="f_l">所有卖家标记都不屏蔽</div>
											</c:when>
											<c:otherwise>
												<div class="f_l">屏蔽卖家标记</div>
											</c:otherwise>
										</c:choose>
									</div>

									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">订单来源:</div>
										<c:choose>
											<c:when test="${ orderReminder.orderSource eq '1'}">
												<div class="f_l LaiYuan">PC端</div>
											</c:when>
											<c:when test="${ orderReminder.orderSource eq '2'}">
												<div class="f_l LaiYuan">移动端</div>
											</c:when>
											<c:otherwise>
												<div class="f_l LaiYuan">不限</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">商品选择:</div>
										<c:choose>
											<c:when test="${ orderReminder.selectCommodityType eq '1'}">
												<div class="f_l AllShangPin">指定商品</div>
											</c:when>
											<c:when test="${ orderReminder.selectCommodityType eq '2'}">
												<div class="f_l AllShangPin">排除指定商品</div>
											</c:when>
											<c:otherwise>
												<div class="f_l AllShangPin">全部商品</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">已发短信过滤:</div>
										<c:choose>
											<c:when test="${ orderReminder.alreadySendMessages eq '1'}">
												<div class="f_l LaiYuan">当天</div>
											</c:when>
											<c:when test="${ orderReminder.alreadySendMessages eq '2'}">
												<div class="f_l LaiYuan">近三天</div>
											</c:when>
											<c:when test="${ orderReminder.alreadySendMessages eq '3'}">
												<div class="f_l LaiYuan">近七天</div>
											</c:when>
											<c:when test="${ orderReminder.alreadySendMessages eq '4'}">
												<div class="f_l LaiYuan">近十五天</div>
											</c:when>
											<c:otherwise>
												<div class="f_l LaiYuan">不过滤</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="font14 c_384856 h45 lh45 p_l30">
										<div class="f_l m_r25">过滤条件:</div>
										<c:if test="${ orderReminder.filterType == '1'}">
											<div class="f_l guolvtiaojian">中差评</div>
										</c:if>
										<c:if test="${ orderReminder.filterType == '0'}">
											<div class="f_l guolvtiaojian">黑名单</div>
										</c:if>
										<c:if test="${ orderReminder.filterType == '0,1'}">
											<div class="f_l guolvtiaojian">黑名单 中差评</div>
										</c:if>
									</div>
								</div>
							</div>

							<div class="display_none Live_SZ">

								<div class="font14 h40 lh40 p_l30 m_b20 text-center">
									<div class="f_l m_r15">快捷入口:</div>
									<div
										class="f_l w105 h40 bgc_00a0e9 m_r15 b_radius5 c_fff PiLiangCuiFu cursor_">批量催付</div>
									<div
										class="f_l w105 h40 bgc_00a0e9 m_r15 b_radius5 c_fff PiLiangCuiHaoPing cursor_">批量催好评</div>
									<div
										class="f_l w105 h40 bgc_00a0e9 m_r15 b_radius5 c_fff JiaoYiGuanBiGuanHuai cursor_">交易关闭关怀</div>
									<div
										class="f_l w105 h40 bgc_00a0e9 m_r15 b_radius5 c_fff BiaoJiTiXing cursor_">标记提醒</div>
									<!-- <div
										class="f_l w105 h40 bgc_00a0e9 m_r15 b_radius5 c_fff YuShouCuiWeiKuan cursor_">预售催尾款</div> -->
									<div
										class="f_l w105 h40 bgc_00a0e9 m_r15 b_radius5 c_fff YanShiFaHuoTiXing cursor_">延时发货提醒</div>
									<!-- <div
										class="f_l w105 h40 bgc_00a0e9 m_r15 b_radius5 c_fff QiTa cursor_">其他</div> -->
								</div>

								<div class="font14 c_384856 h45 lh45 p_l30 m_b20">
									<div class="f_l m_r15">订单时间:</div>

									<div class="wrap f_l h45 lh45 m_r15">
										<div class="wrap f_l h45 lh45 w140">
											<div class="nice-select h45 lh45 w117" name="nice-select">
												<c:choose>
													<c:when test="${orderReminder.orderDateType =='1' }">
														<input readonly="readonly" class="h45 lh45 w140 Time11"
															type="text" value="下单时间">
													</c:when>
													<c:when test="${orderReminder.orderDateType =='2' }">
														<input readonly="readonly" class="h45 lh45 w140 Time11"
															type="text" value="付款时间">
													</c:when>
													<c:when test="${orderReminder.orderDateType =='3' }">
														<input readonly="readonly" class="h45 lh45 w140 Time11"
															type="text" value="发货时间">
													</c:when>
													<c:when test="${orderReminder.orderDateType =='4' }">
														<input readonly="readonly" class="h45 lh45 w140 Time11"
															type="text" value="变更时间">
													</c:when>
													<c:when test="${orderReminder.orderDateType =='5' }">
														<input readonly="readonly" class="h45 lh45 w140 Time11"
															type="text" value="结束时间">
													</c:when>
													<c:otherwise>
														<input readonly="readonly" class="h45 lh45 w140 Time11"
															type="text" value="不限">
													</c:otherwise>
												</c:choose>
												<!-- <input class="h45 lh45 w140 Time11" type="text"
													placeholder="下单时间"> -->
												<ul>
													<li>不限</li>
													<li>下单时间</li>
													<li>付款时间</li>
													<li>发货时间</li>
													<li>变更时间</li>
													<li>结束时间</li>

												</ul>
											</div>
										</div>
									</div>
									<c:choose>
										<c:when
											test="${orderReminder.startTime !=null and orderReminder.endTime !=null}">
											<input type="text" id="tser01"
												class="f_l w15vw h45 b_radius5 bor_cad3df bgc_date in15 Time22"
												readonly value="${startTime }"
												onclick="$.jeDate('#tser01',{minDate: $.nowDate(0),insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
											<div class="f_l m_l15 m_r15">~</div>
											<input type="text" id="tser02"
												class="f_l w15vw h45 b_radius5 bor_cad3df bgc_date in15 Time33"
												readonly value="${endTime }"
												onclick="$.jeDate('#tser02',{minDate: $.nowDate(0),choosefun:function(elem, val) {valiteTwoTime();},minDate: $.nowDate(0),insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',okfun:function(elem, val) {valiteTwoTime();}})">
										</c:when>
										<c:otherwise>
											<input type="text" id="tser01"
												class="f_l w15vw h45 b_radius5 bor_cad3df bgc_date in15 Time22"
												readonly value="${startTime }"
												onclick="$.jeDate('#tser01',{minDate: $.nowDate(0),insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
											<div class="f_l m_l15 m_r15">~</div>
											<input type="text" id="tser02"
												class="f_l w15vw h45 b_radius5 bor_cad3df bgc_date in15 Time33"
												readonly value="${endTime }"
												onclick="$.jeDate('#tser02',{minDate: $.nowDate(0),insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
										</c:otherwise>
									</c:choose>
								</div>


								<div
									class="font14 c_384856 h45 lh45 p_l30 DingDanZhuangTai orderStatus">
									<div class="f_l m_r15">订单状态:</div>
									<div class="f_l">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK ALL_ORDERS">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											全部订单 <input type="hidden" value="ALL_ORDERS">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK WAIT_BUYER_PAY">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											已下单未付款 <input type="hidden" value="WAIT_BUYER_PAY">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK WAIT_SELLER_SEND_GOODS">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											已付款未发货 <input type="hidden" value="WAIT_SELLER_SEND_GOODS">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK WAIT_BUYER_CONFIRM_GOODS">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											卖家已发货 <input type="hidden" value="WAIT_BUYER_CONFIRM_GOODS">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK SELLER_CONSIGNED_PART">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											卖家部分发货 <input type="hidden" value="SELLER_CONSIGNED_PART">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK TRADE_FINISHED">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											交易成功 <input type="hidden" value="TRADE_FINISHED">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK REFUND_SUCCESS">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											已退款 <input type="hidden" value="REFUND_SUCCESS">
										</div>
									</div>
									<div class="f_l">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK TRADE_CLOSED_BY_TAOBAO">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											交易关闭 <input type="hidden" value="TRADE_CLOSED_BY_TAOBAO">
										</div>
									</div>
								</div>



								<div class="font14 c_384856 h45 lh45 p_l30 m_b20 clear">
									<div class="f_l m_r15">订单金额:</div>
									<div class=" f_l h45 lh45 w600 ">
										<div class="bgc_f4f6fa w140 f_l m_r10 radius10">
											<input id="minPayment"
												class="f_l bgc_f4f6fa border0 w85 h45 lh45 p_l15 outline_none Money11" />
											<div class="f_r w25">元</div>
										</div>
										<div class="f_l m_r10">~</div>
										<div class="bgc_f4f6fa w140 f_l m_r10">
											<input id="maxPayment"
												class="f_l bgc_f4f6fa border0 w85 h45 lh45 p_l15 outline_none Money22" />
											<div class="f_r w25">元</div>
										</div>
										<div id="priceMSG" class="col-md-7  f_l  lh45">
											<font></font>
										</div>
									</div>
								</div>

								<div class="font14 c_384856 h45 lh45 p_l30 m_b20">
									<div class="f_l m_r15">交易次数:</div>
									<div class=" f_l h45 lh45">
										<div class="bgc_f4f6fa w140 f_l m_r10 radius10">
											<input id="minCount"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												class="f_l bgc_f4f6fa border0 w85 h45 lh45 p_l15 outline_none jiaoyicishu11" />

										</div>
										<div class="f_l m_r10">~</div>
										<div class="bgc_f4f6fa w140 f_l m_r10">
											<input id="maxCount"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												class="f_l bgc_f4f6fa border0 w85 h45 lh45 p_l15 outline_none jiaoyicishu22" />
										</div>
										<div id="countMSG" class="col-md-7  f_l  lh45">
											<font></font>
										</div>
									</div>
								</div>



								<div class="font14 c_384856 h45 lh45 p_l30 PingJiaZhuangTai">
									<div class="f_l m_r15">评价状态:</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											不限 <input type="hidden" value="0">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											买家未评价 <input type="hidden" value="1">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											买家未评价，卖家已评价 <input type="hidden" value="2">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_5 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											买家已评价 <input type="hidden" value="3">
										</div>
									</div>
								</div>

								<div class="font14 c_384856 h50 lh50 p_l30 DiQuShaiXuan">
									<div class="f_l m_r15">地区筛选:</div>
									<input type="hidden" id="province">
									<div class="f_l m_l10" onclick="addProvince(0);">
										<div
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 bgc_check_blue">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">默认全部</div>
									</div>
									<div class="f_l m_l10" onclick="addProvince(1);">
										<div
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 JiangZheHu position_relative">
											<span
												class="position_absolute w200 l_h22 left_70 top_32 c_384856 bgc_f4f6fa text-center display_none">江苏省，浙江省，上海</span>
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">江浙沪</div>
									</div>
									<div class="f_l m_l10" onclick="addProvince(2);">
										<div
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 JiangZheHu position_relative">
											<span
												class="position_absolute w200 l_h22 left_70 top_32 c_384856 bgc_f4f6fa text-center display_none">新疆、西藏、云南省、青海省</span>
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">偏远地区</div>
									</div>
									<div class="f_l m_l10" onclick="addProvince(3);">
										<div
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 CustomArea">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50 CustomArea">
											自定义</div>
									</div>
								</div>

								<div class="font14 c_384856 h50 lh50 p_l30 mjbj">
									<div class="f_l m_r15">卖家标记:</div>
									<div class="f_l m_l10">
										<div onclick="addVendorflag('0')"
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 bgc_check_blue YouMaiJiaBiaoJiDouBuPingBi">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">
											所有卖家标记都不屏蔽 <input type="hidden" value="0">
										</div>
									</div>
									<div class="f_l m_l10">
										<div onclick="addVendorflag('1')"
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17  PingBiMaiJiaBiaoJi">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">
											屏蔽卖家标记 <input type="hidden" value="1">
										</div>
									</div>
									<div class="f_l m_t4 m_l15 display_none XiaoQiQi">
										<div class="f_l m_t2">
											<div
												class="m_t0_6 m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK">
												<input type="hidden" value="1">
											</div>
											<img class="w20 h20 m_t10" src="${ctx}/crm/images/redq.png">
										</div>
										<div class="f_l m_t2 m_l15">
											<div
												class="m_t0_6 m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK">
												<input type="hidden" value="2">
											</div>
											<img class="w20 h20 m_t10"
												src="${ctx}/crm/images/yellowq.png">
										</div>
										<div class="f_l m_t2 m_l15">
											<div
												class="m_t0_6 m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK">
												<input type="hidden" value="3">
											</div>
											<img class="w20 h20 m_t10" src="${ctx}/crm/images/greenq.png">
										</div>
										<div class="f_l m_t2 m_l15">
											<div
												class="m_t0_6 m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK">
												<input type="hidden" value="4">
											</div>
											<img class="w20 h20 m_t10" src="${ctx}/crm/images/blueq.png">
										</div>
										<div class="f_l m_t2 m_l15">
											<div
												class="m_t0_6 m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK">
												<input type="hidden" value="5">
											</div>
											<img class="w20 h20 m_t10"
												src="${ctx}/crm/images/violetq.png">
										</div>
									</div>
								</div>

								<div class="clear font14 c_384856 h50 lh50 p_l30 DingDanLaiYuan">
									<div class="f_l m_r15">订单来源:</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 bgc_check_blue">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">
											不限 <input value="0" type="hidden">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">
											PC端 <input value="1" type="hidden">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">
											移动端 <input value="2" type="hidden">
										</div>
									</div>

								</div>

								<div class="font14 c_384856 h45 lh45 p_l30 YiFaDuanXinGuoLv">
									<div class="f_l m_r15">已发短信过滤:</div>
									<div class="f_l">
										<div
											class="m_t0_4 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											不过滤 <input value="0" type="hidden">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_4 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											今天 <input value="1" type="hidden">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_4 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											近三天 <input value="2" type="hidden">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_4 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											近七天 <input value="3" type="hidden">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_4 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											近十五天 <input value="4" type="hidden">
										</div>
									</div>
								</div>

								<div class="font14 c_384856 h45 lh45 p_l30 GUOLVTIAOJIAN">
									<div class="f_l m_r15">过滤条件:</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_4 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK_filter bgc_check_blue">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											黑名单 <input value="0" type="hidden">
										</div>
									</div>
									<div class="f_l m_l10">
										<div
											class="m_t0_4 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK_filter">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">
											中差评 <input value="1" type="hidden">
										</div>
									</div>
								</div>
								<div class="font14 c_384856 h50 lh50 p_l30 ShangPinXuanZhe">
									<div class="f_l m_r15">
										商品选择: <input id="item_select" type="hidden" value="0" /> <input
											id="appoint_ItemId" type="hidden" />
									</div>
									<div class="f_l m_l10">
										<div id="sp0" onclick="addItemflag(0);"
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 bgc_check_blue AllShop">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">全部商品</div>
									</div>
									<div class="f_l m_l10">
										<div id="sp1" onclick="addItemflag(1);"
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 ZhiDingShop">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">指定商品</div>
									</div>
									<div class="f_l m_l10">
										<div id="sp2" onclick="addItemflag(2);"
											class="m_t0_8 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK m_t17 PaiChuShop">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh50">排除指定商品</div>
										<input id="productSelect" type="hidden"
											value="${orderReminder.selectCommodityType}"> <input
											id="commodityIds" type="hidden"
											value="${orderReminder.commodityIds}">
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

									<div class="f_r m_t10 p_l20 m_t20 m_l200">
										<div onclick="addOrderReminder();"
											class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ GaoJiSheZhiSave">
											保存</div>
										<div
											class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ Cancel">
											取消</div>
									</div>
								</div>
								<div style="clear: both"></div>

							</div>
						</div>
					</div>

					<!--短信设置start-->
					<div class="clear w1590  bgc_fff c_384856 p_l50 font16">
						<div class="w1030 p_b40">
							<div class="h47">
								<p>
									短信设置<span class="font14 c_00a0e9 p_l10 cursor_ CCC_XG">修改</span>
								</p>
							</div>

							<div class="die_SZ min_h500 duanxinshezhibukebianji">
								<div class="font14 c_384856 h40 lh40 p_l1_2">
									<div class="f_l m_r10">短信内容:</div>
									<div class="f_l w928 h285 bgc_f4f6fa radius10">
										<textarea id="contentShow" disabled="disabled"
											class="w888 h240 outline_none radius10 border0 bgc_f4f6fa p_l20 p_t15 p_r20 Letter"
											onkeyup="addLength()">${orderReminder.content }</textarea>
										<!-- 										<div class="h30 w928 m_t_24 m_l20 c_bfbfbf"> -->
										<!-- 											已输入：<span class="text_count" id="inputNum1">64</span>字 当前计费：<span -->
										<!-- 												id="contenPrice1">1</span>条 -->
										<!-- 										</div> -->
									</div>
								</div>
								<div class="font14 c_384856 h45 lh45 p_l30 m_t70 f_l">
									<div class="f_l m_r15">是否定时:</div>
									<input id="isTimeId" type="hidden"
										value="${orderReminder.isTiming}">
									<div class="f_l">
										<div onclick="isTime('0')"
											class="kaiqi_dingshi m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK bgc_check_blue">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">是</div>
									</div>
									<div class="f_l m_l10">
										<div onclick="isTime('1')"
											class="guanbi_dingshi m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">否</div>
									</div>
									<input value=""
										class="bgc_f4f6fa bgc_date bor_cad3df b_radius5 w240 p_l10 h50 m_l10"
										type="text" id="tser13" readonly> <a id="tishi"
										style="color: red; visibility: hidden">请选择定时发送时间！</a>
									<%-- <input value="${timing}" id="timing"
										class="workinput wicon mr25 f_l w175 h45 b_radius5 bor_cad3df bgc_date in15 m_l15 font10"
										placeholder="全部" type="text" 
										readonly onclick="testShow(this);"> --%>
									<div class="c_ec0000">提示：定时发送任务必须在半小时以后的时间段</div>
								</div>

								<div
									class="f_l w226 h50 lh50  b_radius5 bgc_00a0e9 c_fff text-center cursor_ m_t75"
									style="margin-left: 5vw;">
									<input type="button" id="kaiQiId" value="定时发送"
										onclick="sendSms()"
										class="w226 h50 lh50  b_radius5 bgc_00a0e9 c_fff text-center margin0_auto cursor_  font16 lijikaiqi border0" />
								</div>

							</div>


							<!------------------------------    短信设置编辑部分        -------------------------------------->
							<div class="Live_SZ min_h800 DXSZBianJiBuFen display_none">
								<div class="font14 c_384856 h40 p_l1_2">
									<div class="f_l">短信变量:</div>
									<div class="f_l m_r15">
										<ul>
											<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
												class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
												onclick="insertText(document.getElementById('SmsContent'),'【订单号】');">
												订单号 <span
												class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认5个字，以实际变量为准</span>
											</li>
											<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
												class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
												onclick="insertText(document.getElementById('SmsContent'),'【下单时间】');">
												下单时间 <span
												class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
											</li>
											<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
												class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
												onclick="insertText(document.getElementById('SmsContent'),'【买家昵称】');">
												买家昵称 <span
												class="position_absolute w226 l_h40 left-46 top_2_3vw  b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
											</li>
											<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
												class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
												onclick="insertText(document.getElementById('SmsContent'),'【买家姓名】');">
												买家姓名 <span
												class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
											</li>
										</ul>
									</div>
								</div>
								<div class="font14 c_384856 m_t30">
									<div class="f_l m_r10 w92">
										<div class="p_l1_2">短信内容:</div>
										<div class="m_t15 c_red">注意:短网址默认前后已加空格，请勿删除！否则可能导致网址打不开!</div>
									</div>
									<div class="f_l w928 h285 bgc_f4f6fa radius10">
										<input id="textContent1" type="hidden"
											value="${orderReminder.content}" />
										<textarea id="SmsContent"
											class="w888 h240 outline_none radius10 border0 bgc_f4f6fa p_l20 p_t15 p_r20 Letter11  text_area">${orderReminder.content }</textarea>
										<div class="h30 m_l20 c_bfbfbf f_l">
											已输入：<span class="text_count" id="inputNum">64</span>字 当前计费：<span
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
										<input id="actualDeduction_input" type="hidden" />
									</div>
								</div>
								<div class="w1030 font14 h40 l_h40">
									<div class="f_r">
										<div class="c_00a0e9 m_r15 f_l cursor_ ChangeLink"
											onclick="getCursortPosition(document.getElementById('SmsContent'));">转化为短链接</div>
										<div class="c_00a0e9 m_r15 f_l cursor_  Library"
											onclick="findTemplate('默认')">引用短语库</div>
										<div class="c_00a0e9 m_r15 f_l cursor_ ClickPhrase">另存为短语库</div>
									</div>
								</div>
								<div class="f_l w1030 font14 c_384856 h45 lh45 p_l30"
									style="margin-bottom: 1vw;">
									<div class="m_r10">
										短信签名: <input style="width: 10vw;"
											class="h50  bgc_f4f6fa p_l15 outline_none border0 shopName sms_sign"
											disabled="disabled" type="text" value="${ShopName }"
											onkeyup="updateShopName(this.value,'SmsContent');" /> <img
											src="${ctx }/crm/images/bianji.png"
											style="width: 1.2vw; margin-left: 0.5vw; cursor: pointer;"
											onclick="updateDisabled();"><input id="smsSign"
											type="hidden" name="messageSignature" value="${ShopName}" />
									</div>
								</div>
								<div class="clear"></div>
								<%-- <div class="clear w1030 font14 c_384856 h60 l_h45 p_l1 smsSign">
									<div class="f_l m_r10">个性短信签名:</div>
									<c:choose>
										<c:when test="${orderReminder.smsSign==null}">
											<div class="f_l m_l10">
												<div
													class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l GXK bgc_check_blue cursor_"
													onclick="insertText(document.getElementById('SmsContent'),'【${ShopName}】');">
												</div>
												<div class="m_l10 f_l font14 c_384856 lh40">
													淘宝 <input type="hidden" value="1">
												</div>
											</div>
											<div class="f_l m_l10 c_00a0e9 m_r10">${ShopName}</div>
											<!-- <div class="f_l m_l10">
												<div
													class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l GXK cursor_"
													onclick="insertText(document.getElementById('SmsContent'),'【京东】');">
												</div>
												<div class="m_l10 f_l font14 c_384856 lh40">
													<input type="hidden" value="2">京东
												</div>
											</div> -->
										</c:when>
										<c:when test="${orderReminder.smsSign!=null}">
											<div class="f_l m_l10">
												<div
													class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l GXK cursor_"
													onclick="insertText(document.getElementById('SmsContent'),'【${ShopName}】');">
												</div>
												<div class="m_l10 f_l font14 c_384856 lh40">
													<input type="hidden" value="1">淘宝
												</div>
											</div>
											<div class="f_l m_l10 c_00a0e9 m_r10">${ShopName}</div>
										</c:when>
										<c:otherwise>
											<div class="f_l m_l10">
												<div
													class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l GXK cursor_"
													onclick="insertText(document.getElementById('SmsContent'),'【${ShopName}】');">
												</div>
												<div class="m_l10 f_l font14 c_384856 lh40">
													<input type="hidden" value="1">淘宝
												</div>
											</div>
											<div class="f_l m_l10 c_00a0e9 m_r10">${ShopName}</div>
											<!-- <div class="f_l m_l10">
												<div
													class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l GXK cursor_"
													onclick="insertText(document.getElementById('SmsContent'),'【京东】');">
												</div>
												<div class="m_l10 f_l font14 c_384856 lh40">
													<input type="hidden" value="2">京东
												</div>
											</div> -->
										</c:otherwise>
									</c:choose>
								</div> --%>
								<p class="p_l94 lh25 font14 m_b10">
									温馨提示：<br>1）为避免打扰客户休息，营销短信发送时间为早8点到晚10点，超出时间会自动顺延发送。<br>2）根据淘宝最新规定，即日起禁止发送含有“QQ群”、“微信群”、“微信公众号”、“好评返现”、“免单”、“全5分返现”等相关信息的短信
								</p>
								<div class="f_l w1030 font14 c_384856 h45 lh45 p_l30">
									<div class="m_r10">
										<div class="f_l">测试手机:</div>
										<input id="testPhone"
											class="f_l h50 lh50 w670 bgc_f4f6fa p_l15 outline_none border0"
											type="text" placeholder="可输入5个测试手机号，以逗号隔开" />
										<div class="f_l c_00a0e9 m_l20 ceShiFaSong1 cursor_">测试发送</div>
									</div>
								</div>
								<div class="f_r m_t40">
									<div onclick="addSmsSeting('${orderReminder.id}');"
										class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_">
										保存</div>
									<div id="cancel"
										class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ DXCancel CCC_Cancel">
										取消</div>
								</div>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div>
						<div
							class="position_absolute top_38vw right240 iphone w327 h642 display_none">
							<textarea disabled="disabled"
								class="text_area_copy m_l45 m_t150 w250 h370 border0 bgc_fff">${orderReminder.content }</textarea>
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
	$('#maxPayment').on(
			"blur",
			function() {
				var number = $("#maxPayment").val();
				$("#priceMSG").empty();
				if (number != null) {
					if (number == "") {
						validateTwoPrice();
					} else {
						var regex3 = /^\d+(\.\d{0,2})?$/;
						if (!regex3.test(number)) {
							$("#maxPayment").val("");
							$("#priceMSG").html(
									"<font style='color: red;'>您输入的金额："
											+ number + "不正确，请重新输入！</font>");
							// 					confirm("您输入的金额："+number+"不正确，请重新输入！");
						} else {
							validateTwoPrice();
						}
					}
				}
			});

	function addVendorflag(flag) {
		if (flag == 0) {
			$('.XiaoQiQi').find('.MaiJiaBiaoJiGXK').removeClass(
					'bgc_check_blue');
		} else if (flag == 1) {
			$('.XiaoQiQi').find('.MaiJiaBiaoJiGXK').addClass('bgc_check_blue');
		}
	}
	function validateContent() {
		var content = $("#textContent1").val();
		var flag = true;
		if (content.contains("QQ群")) {
			flag = false;
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("抱歉，您的短信内容中有淘宝最新规定的违禁字眼：QQ群")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else if (content.contains("微信群")) {
			flag = false;
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("抱歉，您的短信内容中有淘宝最新规定的违禁字眼：微信群")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else if (content.contains("微信公众号")) {
			flag = false;
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("抱歉，您的短信内容中有淘宝最新规定的违禁字眼：微信公众号")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else if (content.contains("好评返现")) {
			flag = false;
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("抱歉，您的短信内容中有淘宝最新规定的违禁字眼：好评返现")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else if (content.contains("免单")) {
			flag = false;
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("抱歉，您的短信内容中有淘宝最新规定的违禁字眼：免单")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else if (content.contains("全5分返现")) {
			flag = false;
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("抱歉，您的短信内容中有淘宝最新规定的违禁字眼：全5分返现")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		}
	}
	function validateTwoCount() {
		$("#countMSG").empty();
		var maxCount = parseInt($("#maxCount").val());
		var minCount = parseInt($("#minCount").val());
		if (maxCount < minCount) {
			$("#countMSG").html(
					"<font style='color: red;'>最多交易次数不能低于最少交易次数</font>");
			return false;
		} else {
			return true;
		}
	}
	$("#maxCount").on("blur", function() {
		validateTwoCount();
	});
	$("#minCount").on("blur", function() {
		validateTwoCount();
	});
	$('#maxPayment').on("keyup", function() {
		this.value = this.value.match(/\d+(\.\d{0,2})?/g);
	});
	$('#minPayment').on("keyup", function() {
		this.value = this.value.match(/\d+(\.\d{0,2})?/g);
	});
	$('#minPayment').on(
			"blur",
			function() {
				$("#priceMSG").empty();
				var number = $("#minPayment").val();
				if (number != null) {
					if (number == "") {
						validateTwoPrice();
					} else {
						var regex3 = /^\d+(\.\d{0,2})?$/;
						if (!regex3.test(number)) {
							$("#minPayment").val("");
							$("#priceMSG").html(
									"<font style='color: red;'>您输入的金额："
											+ number + "不正确，请重新输入！</font>");
							// 					confirm("您输入的金额："+number+"不正确，请重新输入！");
						} else {
							validateTwoPrice();
						}
					}
				}
			});
	function validateTwoPrice(msg) {
		$(priceMSG).empty();
		var maxPayment = $("#maxPayment").val();
		var minPayment = $("#minPayment").val();
		if (maxPayment == null || minPayment == null) {
		} else {
			if (maxPayment != "" && minPayment != "") {
				var maxPaymentFloat = parseFloat(maxPayment);
				var minPaymentFloat = parseFloat(minPayment);
				if (maxPaymentFloat < minPaymentFloat) {
					//						if(msg==""){
					//							$("#minPayment").val("");
					//						}
					$("#priceMSG").html(
							"<font style='color: red;'>实付金额最大值不能小于最小值</font>");
					return false;
				}
			}
		}
		return true;
	}
	function validateTwoTime() {
		var endTime = $("#endTime").val();
		var startTime = $("#startTime").val();
		if (startTime == undefined || endTime == undefined) {
		} else {
			if (endTime != "" && startTime != "") {
				var endTimeFloat = parseFloat(endTime.substring(0, 2));
				var startTimeFloat = parseFloat(startTime.substring(0, 2));
				if (endTimeFloat < startTimeFloat) {
					$(".tishi_2").show();
					$(".tishi_2").children("p").text(endTimeFloat)
					setTimeout(function() {
						$(".tishi_2").hide()
					}, 3000)
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("结束时间不能小于开始时间")
					setTimeout(function() {
						$(".tishi_2").hide()
					}, 3000);
					return false;
				} else if (endTimeFloat == startTimeFloat) {
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("结束时间不能等于开始时间")
					setTimeout(function() {
						$(".tishi_2").hide()
					}, 3000);
					return false;
				}
			}
		}
		return true;
	}

	function getProvince() {
		var province = $(".diqu").children().find('.bgc_check_blue').next();
		//confirm(province.eq(0).children('input').val());
	}
	//判断是不是数字,不为数字时清除输入的数据
	function judgeOrderPrice(val, name) {
		// 		$("." + name).val("");
		// 		if (isNaN(val)) {
		// 			confirm("请输入数字！");
		// 			$("." + name).val("");
		// 		}
	}
	function saveButtonHide() {
		$(".Live_SZ").hide();
		$(".die_SZ").show();
		$(".Time1").text($(".Time11").val());
		$(".Time2").text($(".Time22").val());
		$(".Time3").text($(".Time33").val());
		var ShuZiZZ = /^[0-9]*$/;
		if ($(".Money11").val().match(ShuZiZZ)
				&& $(".Money22").val().match(ShuZiZZ)
				&& $(".Money11").val() !== ("") && $(".Money22").val() !== ("")) {
			$(".Money").text($(".Money11").val() + "~" + $(".Money22").val());
		} else {
			$(".Money").text("不限");
		}
		if ($(".jiaoyicishu11").val().match(ShuZiZZ)
				&& $(".jiaoyicishu22").val().match(ShuZiZZ)
				&& $(".jiaoyicishu11").val() !== ("")
				&& $(".jiaoyicishu22").val() !== ("")) {
			$(".jiaoyicishu")
					.text(
							$(".jiaoyicishu11").val() + "~"
									+ $(".jiaoyicishu22").val());
		} else {
			$(".jiaoyicishu").text("不限");
		}
		var lll = $(".DiQuShaiXuan").find(".bgc_check_blue").next("div").text();
		$(".DiQu").text(lll);
		var kkk = $(".DingDanLaiYuan").find(".bgc_check_blue").next("div")
				.text();
		$(".LaiYuan").text(kkk);
		var jjj = $(".HuiYuanDengJi").find(".bgc_check_blue").next("div")
				.text();
		$(".VipDengJi").text(jjj);
		var hhh = $(".ShangPinXuanZhe").find(".bgc_check_blue").next("div")
				.text();
		$(".AllShangPin").text(hhh);
		var PPP = $(".DingDanZhuangTai").find(".bgc_check_blue").next("div")
				.text();
		$(".dingdanzhuangtai").text(PPP);
		var ooo = $(".DingDanLeiXing").find(".bgc_check_blue").next("div")
				.text();
		$(".dingdanleixing").text(ooo);
		var iii = $(".PingJiaZhuangTai").find(".bgc_check_blue").next("div")
				.text();
		$(".pingjiazhuangtai").text(iii);
		var yyy = $(".YiFaDuanXinGuoLv").find(".bgc_check_blue").next("div")
				.text();
		$(".yifaduanxinguolv").text(yyy);
		var ccc = $(".GUOLVTIAOJIAN").find(".bgc_check_blue").next("div")
				.text();
		$(".guolvtiaojian").text(ccc);
		var mmm = $(".YuShouZhuangTai").find(".bgc_check_blue").next("div")
				.text();
		$(".yushouzhuangtai").text(mmm);

	}
	//保存基本设置
	function addOrderReminder() {
		var flag = false;
		flag = validateTwoTime();
		if (flag) {
			flag = validateTwoCount();
		}
		if (flag) {
			flag = validateTwoPrice("");
		}
		if (flag) {
			var orderTime = $(".Time11").val();//获取订单时间的类型
			var startTime = $("#tser01").val();//获取订单开始时间 
			var endTime = $("#tser02").val();//获取订单结束时间
			if(orderTime=="不限"){
				orderTime = "0";
			}
			
			//获取预售状态
			var bookingStatus = $(".bookingStatus").children().find(
					'.bgc_check_blue').next().children('input').val();
			var orderStatuss = $(".orderStatus").children().find(
					'.bgc_check_blue').next();//获取被选中的订单状态
			var orderStatus = orderStatuss.children('input').val();//被选中订单状态里的值
			var minOrderPrice = $(".Money11").val();//获取最小交易金额
			var maxOrderPrice = $(".Money22").val();//获取最大交易金额
			var minTradeNum = $(".jiaoyicishu11").val();//获取最小交易次数
			var maxTradeNum = $(".jiaoyicishu22").val();//获取最小交易次数
			//获取订单类型
			var orderType = $(".DingDanLeiXing").children().find(
					'.bgc_check_blue').next().children('input').val();
			//获取评价状态
			var evaluateStatus = $(".PingJiaZhuangTai").children().find(
					'.bgc_check_blue').next().children('input').val();

			var provinceStatus = $("#province").val();//获取省份的选中状态
			var province;//存放省份
			if (provinceStatus == 0) {
				province = 0;
			} else if (provinceStatus == 1) {
				province = "上海市,浙江省,江苏省";
			} else if (provinceStatus == 2) {
				province = "新疆维吾尔自治区,云南省,青海省,西藏自治区";
			} else if (provinceStatus == 3) {
				var getprovince = $(".place_check").children().find('.li_')
						.children('.bgc_check_blue').next().children('input');
				for (var i = 0; i < getprovince.length; i++) {
					if (province != null && province != "") {
						province = province + "," + getprovince.eq(i).val();
					} else {
						province = getprovince.eq(i).val();
					}
				}
			}

			//获取卖家标记
			var sellerSign;
			var sellerSignStatus = $(".YouMaiJiaBiaoJiDouBuPingBi").hasClass(
					'bgc_check_blue');
			if (sellerSignStatus) {
				sellerSign = "0";
			} else {
				sss = $(".MaiJiaBiaoJiGXK")
				for (var i = 0; i < sss.length; i++) {
					if (sss.eq(i).hasClass('bgc_check_blue')) {
						if (sellerSign != null) {
							sellerSign = sellerSign + ","
									+ sss.eq(i).children().val();
						} else {
							sellerSign = sss.eq(i).children().val();
						}
					}
				}
			}
			//获取订单来源
			var orderSource = $(".DingDanLaiYuan").children().children(
					'.bgc_check_blue').next().children('input').val();

			//获取已发短信过滤选择
			var alreadySendMessages = $(".YiFaDuanXinGuoLv").children()
					.children('.bgc_check_blue').next().children('input').val();

			//获取过滤条件选择
			/* var filterType = $(".GUOLVTIAOJIAN").children().children(
					'.bgc_check_blue').next().children('input').val(); */
			var filterType = "";
			var filterDiv = $(".GUOLVTIAOJIAN").children().children(
					'.bgc_check_blue');
			if (filterDiv.length == 2) {
				filterType = "0,1";
			} else {
				filterType = $(".GUOLVTIAOJIAN").children().children(
						'.bgc_check_blue').next().children('input').val();
			}
			//获取商品选择状态和选中的商品ID
			var selectCommodityType = $("#item_select").val();
			var commodityIds = $("#appoint_ItemId").val();
			$.ajax({
				type : "post",
				url : "${ctx}/OrderReminder/updateOrderReminder",
				dataType : "json",
				data : {
					orderTime : orderTime,
					startTime : startTime,
					endTime : endTime,
					bookingStatus : bookingStatus,
					orderStatus : orderStatus,
					minOrderPrice : minOrderPrice,
					maxOrderPrice : maxOrderPrice,
					minTradeNum : minTradeNum,
					maxTradeNum : maxTradeNum,
					orderType : orderType,
					evaluateStatus : evaluateStatus,
					province : province,
					sellerSign : sellerSign,
					orderSource : orderSource,
					alreadySendMessages : alreadySendMessages,
					filterType : filterType,
					selectCommodityType : selectCommodityType,
					commodityIds : commodityIds
				},
				success : function(data) {
					$(".tishi_2").show();
					$(".tishi_2").children("p").text(data.info)
					setTimeout(function() {
						$(".tishi_2").hide()
					}, 3000);
					saveButtonHide();
					window.location.reload();
				},
				error : function() {
					saveButtonHide();
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("保存失败！")
					setTimeout(function() {
						$(".tishi_2").hide()
					}, 3000);
				}
			});
		}
	}

	//获取省份的选中状态
	function addProvince(status) {
		$("#province").val(status);
	}

	//只显示上架设置内容
	$(".VIPGXK").click(function() {
		//只显示上架设置内容
		var shieldNum = $("#status_input").val();
		if (shieldNum == 2) {
			$("#status_input").val('1');
		} else {
			$("#status_input").val('2');
		}
	});
	//使用ajax通过查询条件查询商品信息
	function findItemByData() {
		$('.item_tr').siblings().remove();
		var itemId = $("#itemId_input").val();//获取商品ID
		var itemName = $("#itemName_input").val();//关键字(商品名称)
		var status = $("#status_input").val();//获取只显示上架

		var url = "${ctx}/item/queryItem";
		$
				.post(
						url,
						{
							"commodityId" : itemId,
							"name" : itemName,
							"status" : status
						},
						function(data) {
							var item = data.itemList;
							var item;
							var imgUrl = null;
							if (item == null || item == "undefined"
									|| item == '') {
								item = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
								$('.item_table').append(item);
							} else {
								$
										.each(
												item,
												function(i, result) {
													if (result.url == null) {
														imgUrl = imgUrl = "<img style='width:4.17vw;height:4.17vw;' src='${ctx}/crm/images/no.png'>";
													} else {
														imgUrl = "<img style='width:4.17vw;height:4.17vw;' "+"src='"+result.url+"'>";
													}
													item = "<tr><td class='h60 text-center'><div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'>"
															+ "<input type='hidden' value='"+result.numIid+"'/></div></td>"
															+ "<td class='h60 text-center'>"
															+ imgUrl
															+ "</td><td class='h60 text-center'>"
															+ result.title
															+ "</td><td class='h60 text-center'>"
															+ result.price
															+ "</td></tr>";

													$('.item_table').append(
															item);
													/* item = "<tr><td class='h60 text-center'><div class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'><input type='hidden' value="+result.numIid+"></div></td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>";
													$('.item_table').append(item); */
												});
								checkItem();
							}
						});
	}

	//使用ajax查询商品数据,回显页面
	function findItem() {
		$('.item_tr').siblings().remove();
		var url = "${ctx}/item/queryItem";
		$
				.post(
						url,
						function(data) {
							var item = data.itemList;
							var item;
							var imgUrl = null;
							if (item == null || item == "undefined"
									|| item == '') {
								item = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
								$('.item_table').append(item);
							} else {
								$
										.each(
												item,
												function(i, result) {
													if (result.url == null) {
														imgUrl = imgUrl = "<img style='width:4.17vw;height:4.17vw;' src='${ctx}/crm/images/no.png'>";
													} else {
														imgUrl = "<img style='width:4.17vw;height:4.17vw;' "+"src='"+result.url+"'>";
													}
													item = "<tr><td class='h60 text-center'><div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'>"
															+ "<input type='hidden' value='"+result.numIid+"'/></div></td>"
															+ "<td class='h60 text-center'>"
															+ imgUrl
															+ "</td><td class='h60 text-center'>"
															+ result.title
															+ "</td><td class='h60 text-center'>"
															+ result.price
															+ "</td></tr>";

													$('.item_table').append(
															item);
													/* item = "<tr><td class='h60 text-center'><div class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'><input type='hidden' value="+result.numIid+"></div></td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>";
													$('.item_table').append(item); */
												});
								checkItem();
							}
						});
	}


	//当点击确定时获得选中的商品id
	function addItemId() {
		var itemIds = "";
		var divCheck = $(".item_check_div");
		for (var i = 0; i < divCheck.length; i++) {
			if (divCheck.eq(i).hasClass("bgc_check_blue")) {
				var val = divCheck.eq(i).children().val();
				itemIds += val + ",";
			}
		}
		itemIds = itemIds.substring(0, itemIds.length - 1);
		$("#appoint_ItemId").val(itemIds);
	}


	//商品选择--获取标识,或商品id
	function addItemflag(flag) {
		$("#appoint_ItemId").val('');
		var productSelect = $("#productSelect").val();
		$("#item_select").val(flag);

		if (flag == productSelect) {
			var appoint_ItemId = $("#commodityIds").val();
			$("#appoint_ItemId").val(appoint_ItemId);
			checkItem();
		}
	}

	//在加载页面的时候根据用户保存的数据添加样式
	$(function() {

		//订单状态选中框添加勾选样式
		var os = $(".orderStatus").find(".GXK");
		var orderStatus = "${orderReminder.orderStatus}";
		if (orderStatus != null) {
			$("." + orderStatus).addClass("bgc_check_blue");
		}
		//os.eq(orderStatus).addClass("bgc_check_blue");

		/* //预售状态选中框添加勾选样式
		var yszt = $(".YuShouZhuangTai").find(".GXK");
		var bookingStatus = "${orderReminder.bookingStatus}";
		alert(bookingStatus);
		yszt.eq(bookingStatus).addClass("bgc_check_blue");
		 */
		//订单金额添加数据
		$(".Money11").val("${orderReminder.minOrderPrice}");
		$(".Money22").val("${orderReminder.maxOrderPrice}");

		//交易次数添加数据
		$(".jiaoyicishu11").val("${orderReminder.minTradeNum}");
		$(".jiaoyicishu22").val("${orderReminder.maxTradeNum}");

		/* //订单类型选中框添加勾选样式		
		var ot = $(".DingDanLeiXing").find(".GXK");
		var orderType = "${orderReminder.orderType}";
		ot.eq(orderType).addClass("bgc_check_blue"); */

		//评价状态选中框添加勾选样式
		var es = $(".PingJiaZhuangTai").find(".GXK");
		var evaluateStatus = "${orderReminder.evaluateStatus}";
		es.eq(evaluateStatus).addClass("bgc_check_blue");

		//卖家标记选中框添加勾选样式
		var ss = $(".mjbj").find(".GXK");
		var sellerSign = "${orderReminder.sellerSign}";
		if (sellerSign != null && sellerSign != 0) {
			$(".YouMaiJiaBiaoJiDouBuPingBi").removeClass("bgc_check_blue");
			$(".PingBiMaiJiaBiaoJi").addClass("bgc_check_blue");
			$(".XiaoQiQi").show();
			var sellerIds = sellerSign.split(",");
			for (var i = 0; i <= sellerIds.length; i++) {
				$(".XiaoQiQi").find(".MaiJiaBiaoJiGXK").eq(sellerIds[i] - 1)
						.addClass("bgc_check_blue");
			}
		} else {
			$(".YouMaiJiaBiaoJiDouBuPingBi").addClass("bgc_check_blue");
		}

		//订单来源选中框添加勾选样式
		var orders = $(".DingDanLaiYuan").find(".GXK");
		var orderSource = "${orderReminder.orderSource}";
		if (orderSource > 0) {
			orders.eq(0).removeClass("bgc_check_blue");
			orders.eq(orderSource).addClass("bgc_check_blue");
		}

		//已发短信过滤选中框添加勾选样式
		var asm = $(".YiFaDuanXinGuoLv").find(".GXK");
		var alreadySendMessages = "${orderReminder.alreadySendMessages}";
		asm.eq(alreadySendMessages).addClass("bgc_check_blue");

		//过滤条件选中框添加勾选样式
		var ft = $(".GUOLVTIAOJIAN").find(".GXK_filter");
		var filterType = "${orderReminder.filterType}";
		if (filterType == "1") {
			ft.eq(filterType).addClass("bgc_check_blue");
			ft.eq(0).removeClass("bgc_check_blue");
		} else if (filterType == "0,1") {
			ft.eq(1).addClass("bgc_check_blue");
		}

		//地区筛选选中框添加勾选样式
		var sf = $(".DiQuShaiXuan").find(".GXK");
		var province = "${orderReminder.province}";
		if (province == "上海市,浙江省,江苏省") {
			sf.eq(1).addClass("bgc_check_blue");
			sf.eq(0).removeClass("bgc_check_blue");
		} else if (province == "新疆维吾尔自治区,云南省,青海省,西藏自治区") {
			sf.eq(2).addClass("bgc_check_blue");
			sf.eq(0).removeClass("bgc_check_blue");
		} else if (province == "0") {
			sf.eq(0).addClass("bgc_check_blue");
		} else {
			sf.eq(0).removeClass("bgc_check_blue");
			sf.eq(3).addClass("bgc_check_blue");
			var pe = province.split(",");
			var diqu = $(".gangaotai_ul").children(".li_").find(".c_384856");
			for (var j = 0; j < pe.length; j++) {
				for (var x = 0; x < diqu.length; x++) {
					if (pe[j] == diqu.eq(x).find("input").val()) {
						diqu.eq(x).prev().addClass("bgc_check_blue");
						break;
					}
				}
			}
		}

		//商品选择选中框添加勾选样式
		var itemidInput = $(".item_check_div");
		var selectCommodityType = $("#productSelect").val();
		if (selectCommodityType == 0) {
			$(".AllShop").addClass("bgc_check_blue");
		} else if (selectCommodityType == 1) {
			$(".ZhiDingShop").addClass("bgc_check_blue");
			$("#sp_z0").show();
			$(".AllShop").removeClass("bgc_check_blue");
		} else {
			$(".PaiChuShop").addClass("bgc_check_blue");
			$("#sp_z1").show();
			$(".AllShop").removeClass("bgc_check_blue");
		}
		if (selectCommodityType != null && selectCommodityType != "") {
			$("#item_select").val(selectCommodityType);
		}
		//商品回显appoint_ItemId选择框
		var itemIds = "${orderReminder.commodityIds}";
		if (itemIds != '') {
			$("#appoint_ItemId").val(itemIds);
		}
		//修改设置开启关闭状态
		var status = $('#isTimeId').val();
		if (status == '1') {
			$('#kaiQiId').val('立即发送');
		} else if (status == '0') {
			$('#kaiQiId').val('定时发送');
		} else {
			$('#kaiQiId').val('定时发送');
		}
		//是否定时页面回显
		var isTimeVal = $('#isTimeId').val();
		if (isTimeVal == 0) {
			$('.kaiqi_dingshi').addClass("bgc_check_blue");
			$('.guanbi_dingshi').removeClass("bgc_check_blue");
			$('#timing').show();
		} else if (isTimeVal == 1) {
			$('.kaiqi_dingshi').removeClass("bgc_check_blue");
			$('.guanbi_dingshi').addClass("bgc_check_blue");
			$('#timing').hide();
		}

		// <!------------- 过滤条件勾选框 ---------------------->
		$(".GXK_filter").click(function() {
			$(this).toggleClass("bgc_check_blue");
		});
	})

	//点击短信变量，添加内容
	function insertText(obj, str) {
		var content = obj.value;
		var x = content.indexOf(str);
		if (x >= 0) {
			return;
		}
		if (document.selection) {
			var sel = document.selection.createRange();
			sel.text = str;
		} else if (typeof obj.selectionStart === 'number'
				&& typeof obj.selectionEnd === 'number') {
			var startPos = obj.selectionStart, endPos = obj.selectionEnd, cursorPos = startPos, tmpStr = obj.value;
			obj.value = tmpStr.substring(0, startPos) + str
					+ tmpStr.substring(endPos, tmpStr.length);
			cursorPos += str.length;
			obj.selectionStart = obj.selectionEnd = cursorPos;
		} else {
			obj.value += str;
		}
		$(".text_area_copy").text($(".text_area").val());
		addLength();
	}

	//回显已选择商品选中状态
	function checkItem() {
		var itemidInput = $(".item_check_div");
		var itemIds = $("#appoint_ItemId").val();
		var itemIdList = itemIds.split(",");
		for (var i = 0; i < itemidInput.length; i++) {
			for (var j = 0; j < itemIdList.length; j++) {
				if (itemidInput.eq(i).children().val() == itemIdList[j]) {
					itemidInput.eq(i).addClass("bgc_check_blue");
				}
			}
		}
	}

	//另存为短语库
	function saveTemplate() {
		//短语模板的名字
		var smsName = $('#saveSms').val();
		var smsContent = $('.text_area').val();
		$.ajax({
			url : "${ctx}/crms/saveSmsTemplate",
			type : "post",
			data : {
				"smsName" : smsName,
				"smsContent" : smsContent
			},
			success : function(data) {
				if (data.message) {
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("保存短语成功")
					setTimeout(function() {
						$(".tishi_2").hide()
					}, 3000);
				} else {
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("保存短语失败")
					setTimeout(function() {
						$(".tishi_2").hide()
					}, 3000);
				}
			}
		});
	}


	//点击使用将模板添加到文本域中
	$(document).on("click", ".Template_div", function() {
		var content = $(this).children().val();
		var changeContent = $(".text_area").val();
		var endContent = changeContent + "" + content;
		$("#messageId").val('');
		$(".text_area").val(endContent);
		$(".text_area_copy").text($(".text_area").val());
		addLength();
		//启用滚动条
		$(document.body).css({
			"overflow-x" : "auto",
			"overflow-y" : "auto"
		});
		$(".text_model_window").hide();
	});

	//显示引用短语库
	function findTemplate(smsTemp) {
		$('.temp_tr1').siblings().remove();
		$('.temp_tr2').siblings().remove();

		$(".DYK2").eq(0).show()
		$(".DYK2").eq(1).hide()

		$(".DYK1").eq(0).addClass("bgc_fff").removeClass("bgc_e3e7f0")
		$(".DYK1").eq(1).removeClass("bgc_fff").addClass("bgc_e3e7f0")

		if (smsTemp == "默认") {
			$(".default").children(".GXK1").addClass("bgc_check_blue");
			$(".GXK1").parent(".default").siblings().children(".GXK")
					.removeClass("bgc_check_blue")
		}
		$
				.ajax({
					url : "${ctx}/crms/smsTemplate",
					type : "post",
					data : {
						"type" : smsTemp
					},
					dataType : "json",
					success : function(data) {
						var smsTemplate = data.smsTemplate;
						var appendTemp = "";
						if (smsTemp == "自定义") {
							if (smsTemplate == null
									|| smsTemplate == "undefined"
									|| smsTemplate == '') {
								appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
								$('.temp_table2').append(appendTemp);
							} else {
								$
										.each(
												smsTemplate,
												function(i, result) {
													appendTemp = "<tr class='bgc_fafafa'><td class='w77 h50 text-center' >"
															+ (i + 1)
															+ "</td><td class='w500 h50 text-center'>"
															+ result.name
															+ "</td><td class='w128 h50 text-center'>"
															+ result.content
															+ "</td><td class='w120 h50 text-center'><div  class='w100 h42 l_h42 bor_00a0e9 margin0_auto b_radius5 c_00a0e9 Template_div cursor_'>使用<input type='hidden' value='"+result.content+"' /></div></td></tr>";
													$('.temp_table2').append(
															appendTemp);
												});
							}
						} else {
							if (smsTemplate == null
									|| smsTemplate == "undefined"
									|| smsTemplate == '') {
								appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
								$('.temp_table1').append(appendTemp);
							} else {
								$
										.each(
												smsTemplate,
												function(i, result) {
													appendTemp = "<tr class='bgc_fafafa'><td class='w77 h50 text-center' >"
															+ (i + 1)
															+ "</td><td class='w500 h50 text-center'>"
															+ result.content
															+ "</td><td class='w128 h50 text-center'>"
															+ result.fashion
															+ "</td><td class='w120 h50 text-center'><div  class='w100 h42 l_h42 bor_00a0e9 margin0_auto b_radius5 c_00a0e9 Template_div cursor_'>使用<input type='hidden' value='"+result.content+"' /></div></td></tr>";
													$('.temp_table1').append(
															appendTemp);
												});
							}
						}
					}
				});
	}

	window.onload = function() {
		addLength();
		var msg = $("#msg").val();
		if (msg != null && msg != "") {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text(msg)
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000);
		}
	}
	function valiteAddDate(val) {
		var date = new Date();//获取当前时间  
		var resultDate = new Date(val);
		
		var s1 = date.getTime(), s2 = resultDate.getTime();
 		var total = (s2 - s1) / 1000;
// 		var day = parseInt(total / (24 * 60 * 60));//计算整数天数
// 		var afterDay = total - day * 24 * 60 * 60;//取得算出天数后剩余的秒数
// 		var hour = parseInt(afterDay / (60 * 60));//计算整数小时数
// 		var afterHour = total - day * 24 * 60 * 60 - hour * 60 * 60;//取得算出小时数后剩余的秒数
// 		var min = parseInt(afterHour / 60);//计算整数分
		if(total<1800){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("定时发送任务必须在半小时以后的时间段，您选择的时间有误！")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000);
			$("#tser13").val("");
		}
// 		if (min < 30) {
// 			$(".tishi_2").show();
// 			$(".tishi_2").children("p").text("定时发送任务必须在半小时以后的时间段，您选择的时间有误！")
// 			setTimeout(function() {
// 				$(".tishi_2").hide()
// 			}, 3000);
// 			$("#tser13").val("");
// 		}
	}
	$("#tser13").on("click", function() {
		$("#tishi").css('visibility', 'hidden');
		$.jeDate('#tser13', {
			minDate : $.nowDate(0),
			insTrigger : false,
			isTime : true,
			format : 'YYYY-MM-DD hh:mm:ss',
			choosefun : function(elem, val) {
				
				valiteAddDate(val);
			},
			okfun : function(elem, val) {
				valiteAddDate(val);
			}
		});
	});
	//判断短信输入框内容长度，修改计费条数
	function addLength() {
		var contentLength = $(".text_area").val().length;
		var refundMSGLength = $("#refundMSG").length;
		var length = contentLength + 3 + refundMSGLength;
		var contentVal = $("#textContent").val();
		$("#textContent1").val(contentVal);
		var count = parseInt((length + 66) / 67);
		if (length > 70) {
			$("#actualDeduction_input").val(count);
			document.getElementById("contenPrice").innerText = count;
		} else if (length <= 70) {
			$("#actualDeduction_input").val(1);
			document.getElementById("contenPrice").innerText = 1;
		}
		document.getElementById("inputNum").innerText = length;

		var contentVal = $(".text_area").val();
		//将小手机里的内容末尾添加退订回N 
		if (contentVal == "") {
			$(".text_area_copy").val(contentVal);
			document.getElementById("inputNum").innerText = 0;
			document.getElementById("contenPrice").innerText = 0;
		} else {
			$(".text_area_copy").val(contentVal.replace("退订回N", "") + "退订回N");
		}
	};

	$(".ceShiFaSong1").click(function() {
		var that=$(this);
		if($(this).hasClass('on'))return;
		$(this).addClass('on');	
		var phonez = /^1[3|4|5|7|8]\d{9}$/;
		var phone = $("#testPhone").val();//获取电话
		var content = $("#SmsContent").val();//获取短信内容
		var autograph = $("#smsSign").val();//短信签名

		var phoneflag = 1;//手机号发送判断标识

		if (content != "") {
			content = content.replace("退订回N", "") + "退订回N";
			//判断号码内容是否成功 
			var phoneList = phone.split(",");
			if (phoneList.length <= 5) {
				for (var i = 0; i < phoneList.length; i++) {
					if (phoneList[i].match(phonez)) {
						phoneflag = 0;
					} else {
						phoneflag = 1;
						break;
					}
				}
			} else {
				phoneflag = 2;
			}

			if (phoneflag == 0) {
				var url = "${ctx}/crm/test/sendSMS";
				$.post(url, {
					"content" : content,
					"phone" : phone,
					"autograph" : autograph
				}, function(data) {
					that.removeClass('on');
					if (data.send == '100') {
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("发送成功!")
						setTimeout(function() {
							$(".tishi_2").hide()
						}, 3000)
					} else {
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("发送失败!")
						setTimeout(function() {
							$(".tishi_2").hide()
						}, 3000)
					}
				});
			} else if (phoneflag == 1) {
				that.removeClass('on');
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("手机号错误!")
				setTimeout(function() {
					$(".tishi_2").hide()
				}, 3000)
			} else if (phoneflag == 2) {
				that.removeClass('on');
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("手机号不能大于5个")
				setTimeout(function() {
					$(".tishi_2").hide()
				}, 3000)
			}
		} else {
			that.removeClass('on');
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容不能为空!")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		}

	});

	function addSmsSeting(id) {
		var itg = $(".isTiming").find(".GXK");
		var isTiming = null;
		//获取定时选中状态
		for (var i = 0; i < itg.length; i++) {
			if (itg.eq(i).hasClass("bgc_check_blue")) {
				isTiming = i;
			}
		}
		var time = $("#timing").val();
		var content = $("#SmsContent").val();
		var sSgin = $('.sms_sign').val();
		if (content == "" || content == null) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容不能为空!!!")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else if (content.indexOf("【" + sSgin + "】") < 0) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请核对短信签名是否正确!!!")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else {

			$(".duanxinshezhibukebianji").show();
			$(".DXSZBianJiBuFen").hide();
			$(".iphone").hide();

			$
					.ajax({
						type : "post",
						url : "${ctx}/OrderReminder/updateOrderReminder1",
						dataType : "json",
						data : {
							id : id,
							content : content,
							sSgin : sSgin,
							isTiming : isTiming,
							time : time
						},
						success : function(data) {
							// 				console.log("日志  ：" + data.info);
							// 				console.log("日志  ：" + data.success);
							var success = data.success;
							if (success == "true") {
								$(".tishi_2").show();
								$(".tishi_2").children("p").text(data.info)
								setTimeout(function() {
									$(".tishi_2").hide()
								}, 3000);
								location.href = "${ctx}/OrderReminder/jumOrderReminder";
							} else {
								$(".tishi_2").show();
								$(".tishi_2").children("p").text(data.info)
								setTimeout(function() {
									$(".tishi_2").hide()
								}, 3000);
							}

						},
						error : function() {
							$(".tishi_2").show();
							$(".tishi_2").children("p").text("保存失败！")
							setTimeout(function() {
								$(".tishi_2").hide()
							}, 3000);
						}
					});

		}
	}

	//是否定时
	function isTime(type) {
		$('#isTimeId').val(type);
		if ("0" === type) {
			$("#kaiQiId").val("定时发送");
			timing = $('#tser13').val();
			if (timing == "" || timing == null) {
				$('#tishi').css('visibility', 'visible');
				return;
			}
		} else if ('1' === type) {
			$("#kaiQiId").val("立即发送");
		}
		;
	};

	//点击开启或关闭设置(立即或定时)
	 var clickFlag = false;
	function sendSms() {
		if(clickFlag==true){
			return;
		}
		clickFlag = true;
		
		
		var isTiming = $('#isTimeId').val();
		var timing;
		if (isTiming == '0') {
			timing = $('#tser13').val();
			if (timing == '' || timing == null) {
				$('#tishi').css('visibility', 'visible');
				return;
			}
		} else {
			timing = null;
		}
		$(".tishi_2").show();
		$(".tishi_2").children("p").text("正在执行...")
		var id = $('#setupId').val();
		var url = "${ctx}/OrderReminder/updateStatus";
		var status = $('#statusId').val();
		$.post(url, {
			"id" : id,
			"isTiming" : isTiming,
			"status" : status,
			"timing" : timing
		}, function(data) {
			clickFlag = false;
			$(".tishi_2").show();
			$(".tishi_2").children("p").text(data.msg)
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		}, 'json')
	}

	$(function() {
		var textContent1 = $('#textContent1').val();
		$('#textContent').val(textContent1);
		addLength();
		//页面短信设置非编辑状态展示短信条数
		/* $('#inputNum1').val($('#inputNum').val());
		$('#contenPrice1').val($('#contenPrice').val()); */
	})

	//获得生成短连接的类型
	function linkData(data) {
		$('#linkType').val(data);
	}
	//短连接生成
	function linkDataInput(data) {
		$("#linkData_input").val(data);
	}
	//根据生成链接类型，将请求发送到后台生成短链接
	function linkSubmit() {
		var linkData = $("#linkData_input").val();
		var linkType = $("#linkType").val();//链接类型
		var commodityId = $("#commodityId").val();//商品id
		var activityUrl = $("#activityUrl").val();//活动网址
		var kehuduan = $("#kehuduan").val();
		if (linkData == 0) {
			var url = "${ctx}/GenerateLinkTo/generateLink";
			$.post(url, {
				"linkType" : linkType,
				"commodityId" : commodityId,
				"activityUrl" : activityUrl,
				"linkName" : linkType
			},
					function(data) {
						if (data.link != null) {
							$("#SmsContent").val(
									startContent + data.link + endContent);
							addLength();
						}
					});
		} else {
			var textContent = $("#SmsContent").val();
			$("#SmsContent").val(textContent + kehuduan);
			addLength();
		}

	}

	//点击取消获取之前短信放入内容
	$("#cancel").click(function() {
		var contentShow = $("#contentShow").val();
		$(".text_area").val(contentShow);
		$(".text_area_copy").val(contentShow);
		addLength();
	});

	//定位光标的位置，将内容分割成两个部分
	var startContent, endContent;
	function getCursortPosition(obj) {
		if (typeof obj.selectionStart === 'number'
				&& typeof obj.selectionEnd === 'number') {
			var startPos = obj.selectionStart, endPos = obj.selectionEnd, cursorPos = startPos, tmpStr = obj.value;
			startContent = tmpStr.substring(0, startPos);
			endContent = tmpStr.substring(endPos, tmpStr.length);
		}
	}

	//提交form表单
	function findOrderSendRedord(formId) {
		document.getElementById(formId).submit();
	};
</script>
</html>
