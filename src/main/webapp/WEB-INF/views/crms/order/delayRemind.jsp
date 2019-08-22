<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>延时发货提醒</title>
<%@ include file="/common/common.jsp"%>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!--兼容360meta-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="renderer" content="webkit">

<link rel="stylesheet" href="${ctx}/crm/css/jedate.css" />



<!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
<!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>

    <![endif]-->

<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js" ></script> --%>
<script type="text/javascript" src="${ctx}/crm/js/yanshifahuotixing.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js"></script>
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
					<div class="f_l m_l15 c_384856" style="font-size: 1vw;">订单中心
					</div>
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
							<li class="f_l w140 text-center bgc_f1f3f7"><a
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
					<div class="p_l40 p_t27">
						<p class="font20" id="HEADER">延时发货提醒</p>
						<p class="font14 p_t8">在宝贝发货时及时发送发货提醒短信到买家手机上，提醒买家宝贝已发货</p>
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
					<ul class="h40 font14 m_t1_7 p_l40">
						<a href="${ctx}/logisticsRemind/queryAllSettings" class="c_384856">
							<li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">发货提醒</li>
						</a>
						<a href="${ctx}/achieveLocality/queryAllSettings" class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">到达同城提醒</li></a>
						<a href="${ctx}/deliverRemind/queryAllSettings" class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">派件提醒</li></a>
						<a href="${ctx}/signInRemind/querySignInRemind" class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_">签收提醒</li></a>
						<a href="${ctx}/puzzleRemind/queryPuzzleRemind" class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">疑难件提醒</li></a>
						<a href="${ctx}/delayRemind/queryDelayRemind" class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_fff cursor_ ">延时发货提醒</li></a>
						<a href="#" onclick="findOrderSendRedord('formId')"
							class="c_384856"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">发送记录</li></a>
					</ul>

				</div>
				<form action="${ctx}/sendRecord/orderCenterRecord" method="post"
					id="formId">
					<input type="hidden" name="recordFlag" value="3">
				</form>

				<!--选择商品弹出框start-->
				<div class="rgba_000_5 w1920 h100_ ChoiceSpecified display_none">
					<div class="w1000 h580 p_b33 bgc_fff m_t100 m_l300">
						<div class="w936 h546 margin0_auto bgc_fff">
							<p class="text-center p_t20 p_b40 font16">选择商品</p>
							<form class="font14" action="#">
								<p class="f_l">
									商品ID:<input
										class="h50 w13_4 border0 outline_none b_radius5 m_l15 m_r18 bgc_f4f6fa"
										type="text" />
								</p>
								<p class="f_l">
									关键字:<input
										class="h50 w13_4 border0 outline_none b_radius5 m_l15 m_r18 bgc_f4f6fa"
										type="text" />
								</p>
								<p class="f_l">
									<span
										class="cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 m_t13 f_l">
										<img class="cursor_ one_check_ display_none"
										src="images/black_check.png" />
									</span> <span class="m_l10"> 只显示上架 <input
										class="h50 w110 border0 outline_none b_radius5 m_l15 bgc_f4f6fa"
										type="text" />
									</span>
								</p>
							</form>
							<div class="clear"></div>
							<table border="0" class="font14 m_t13">
								<thead>
									<tr class="bgc_e3e7f0">
										<td class="w80 h60 text-center">全选</td>
										<td class="w766 h60 text-center">宝贝名称</td>
										<td class="w80 h60 text-center">金额</td>
									</tr>
								</thead>
								<tbody>
									<tr class="bgc_fafafa">
										<td class="w80 h60 text-center"></td>
										<td class="w766 h60 text-center"></td>
										<td class="w80 h60 text-center"></td>
									</tr>
									<tr class="bgc_f4f4f4">
										<td class="w80 h60 text-center"></td>
										<td class="w766 h60 text-center"></td>
										<td class="w80 h60 text-center"></td>
									</tr>
									<tr class="bgc_fafafa">
										<td class="w80 h60 text-center"></td>
										<td class="w766 h60 text-center"></td>
										<td class="w80 h60 text-center"></td>
									</tr>
									<tr class="bgc_f4f4f4">
										<td class="w80 h60 text-center"></td>
										<td class="w766 h60 text-center"></td>
										<td class="w80 h60 text-center"></td>
									</tr>
								</tbody>
							</table>
							<!--分页样式start-->
							<div class="w936 h24 m_t10 font14 c_8493a8 ">
								<div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
								<div class="f_r w470 h24 l_h22 font12">
									<div class="f_l w45 h22 bor_e0e6ef text-center m_r4 cursor_">上一页</div>
									<div
										class="f_l w22 h22 bor_e0e6ef text-center m_r4 bgc_00a0e9 c_fff cursor_">1</div>
									<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">2</div>
									<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">3</div>
									<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">4</div>
									<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">5</div>
									<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">6</div>
									<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">7</div>
									<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">8</div>
									<div class="f_l w22 h22 text-center m_r4">...</div>
									<div class="f_l w45 h22 bor_e0e6ef text-center m_r20 cursor_">下一页</div>
									<div class="f_l w45 h22 m_r4">
										<input class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
											type="text" value="">
									</div>
									<div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
								</div>
							</div>
							<!--分页样式end-->
							<!--确定保存start-->

							<div class="w936 h42 m_t50 margin0_auto">
								<div class="w214 margin0_auto">
									<div
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
				<div class="rgba_000_5 w1920 h100_ display_none ChoiceArea">
					<div class="w750 h558 p_b33 bgc_fff m_t100 m_l500">
						<div class="h50 p_t20 text-center font16">选择所在地区</div>
						<div class="font14 c_384856 h50 lh50 p_l50">
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK QuanXuan">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh50">
									<input value=" " class="display_none" />全选
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK QuanBuXuan">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh50">
									<input value=" " class="display_none" />全不选
								</div>
							</div>
							<div class="f_l w125">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK PaiChuBianYuanDiQu">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh50">
									<input value=" " class="display_none" />排除边远山区
								</div>
							</div>
						</div>
						<div class="font14 c_384856 h45 lh45 p_l50">
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />华东
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />上海
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />浙江
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />江苏
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />安徽
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />江西
								</div>
							</div>
						</div>
						<div class="clear font14 c_384856 h45 lh45 p_l50">
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />华北
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />北京
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />天津
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />河北
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />山西
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />山东
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />内蒙古
								</div>
							</div>
						</div>
						<div class="clear font14 c_384856 h45 lh45 p_l50">
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />华中
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />湖北
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />湖南
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />河南
								</div>
							</div>
						</div>
						<div class="clear font14 c_384856 h45 lh45 p_l50">
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />华南
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />广东
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />广西
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />福建
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />海南
								</div>
							</div>
						</div>
						<div class="clear font14 c_384856 h45 lh45 p_l50">
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />东北
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />辽宁
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />吉林
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />黑龙江
								</div>
							</div>
						</div>
						<div class="clear font14 c_384856 h45 lh45 p_l50">
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />西北
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />陕西
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK XinJiang">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />新疆
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK QingHai">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />青海
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />宁夏
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />甘肃
								</div>
							</div>
						</div>
						<div class="clear font14 c_384856 h45 lh45 p_l50">
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />西南
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />四川
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK YunNan">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />云南
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK XiZang">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />西藏
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />贵州
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />重庆
								</div>
							</div>
						</div>
						<div class="clear font14 c_384856 h45 lh45 p_l50">
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />港澳台
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />香港
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />澳门
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />台湾
								</div>
							</div>
						</div>
						<div class="clear font14 c_384856 h45 lh45 p_l50">
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />海外
								</div>
							</div>
							<div class="f_l w80">
								<div
									class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK">
								</div>
								<div class="m_l10 f_l font14 c_384856 lh45">
									<input value=" " class="display_none" />海外
								</div>
							</div>
						</div>
						<div class="f_r p_l20 m_r50">
							<div
								class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ ArerOut">
								保存</div>
							<div
								class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ ArerOut">
								取消</div>
						</div>
						<div style="clear: both"></div>


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
								<div class="f_l m_l10">
									<div onclick="findTemplate('按时间更新')"
										class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
									</div>
									<div class="m_l10 f_l font14 c_384856">按时间更新</div>
								</div>
							</div>
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
							<!-- <table border="0" class="font14">
                                                    <thead>
                                                        <tr class="bgc_e3e7f0">
                                                            <td class="w77 h50 text-center" >序号</td>
                                                            <td class="w602 h50 text-center">短信内容</td>
                                                            <td class="w128 h50 text-center">热度</td>
                                                            <td class="w120 h50 text-center"></td>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr class="bgc_fafafa">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                            <td class="w120 h50 text-center">
                                                            	<div class="w100 h42 l_h42 bor_00a0e9 margin0_auto b_radius5 c_00a0e9">使用</div>
                                                            </td>
                                                        </tr>
                                                        <tr class="bgc_f4f4f4">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                            <td class="w120 h50 text-center"></td>
                                                        </tr>
                                                        <tr class="bgc_fafafa">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                            <td class="w120 h50 text-center"></td>
                                                        </tr>
                                                        <tr class="bgc_f4f4f4">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                            <td class="w120 h50 text-center"></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                分页样式start
                                                <div class="w936 h24 m_t10 font14 c_8493a8 ">
                                                    <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
                                                    </div>
                                                </div> -->
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








				<!----------------------------------------------------------------------------------------       延时发货提醒 start                   ----------------------------------------------------------------------------------------------->
				<div class="w1660 bgc_fff">

					<div class="w1590 c_384856 p_l50 font16 p_t40">
						<div class="w1030">
							<div class="h47">
								<p>
									数据筛选<span class="font14 c_00a0e9 p_l10 cursor_  C_XG">修改</span>
								</p>
							</div>
							<div class="die_SZ">
								<div class="font14 c_384856 h45 lh45 p_l30 m_b20">
									<div class="f_l m_r50">提醒类型:</div>
									<div class="f_l LiJiTiXing">
										<c:if test="${orderSetup.executeGenre ==null }">
                                        	立即提醒
                                        	</c:if>
										<c:if test="${orderSetup.executeGenre =='0' }">
                                        	立即执行
                                        	</c:if>
										<c:if test="${orderSetup.executeGenre =='1' }">
                                        	执行周期（每天）
                                        	</c:if>
									</div>
								</div>
								<div class="font14 c_384856 h45 lh45 p_l30 m_b20">
									<div class="f_l m_r50">订单范围:</div>
									<div class="f_l Time1 m_r20">${orderSetup.orderScopeOne }</div>
									<div class="f_l">至</div>
									<div class="f_l Time2 m_l20">${orderSetup.orderScopeTwo }</div>
									<div class="f_l m_l20">已付款未发货</div>
								</div>

								<div class="font14 c_384856 h45 lh45 p_l30 m_b20">
									<div class="f_l m_r50">数据预览:</div>
									<div class="f_l">
										当前数据（<span id="data_span">0</span>） <a id="click_a"
											href="javascript:openNewWin()">点击预览</a>
									</div>
									<div class="f_l"></div>
								</div>
							</div>
							<div class="display_none Live_SZ">

								<form id="setup_form"
									action="${ctx}/delayRemind/saveOrUpdateOrderSetup"
									method="post">
									<input type="hidden" value="${orderSetup.id }" name="id" />
									<%--  <input id="orderIds" type="hidden" value="${orderSetup.orderId}" name="orderId" /> --%>
									<div class="font14 c_384856 h45 lh45 p_l30 m_b20">
										<div class="f_l m_r15">订单范围:</div>

										<input type="text" id="tser01"
											class="f_l w240 h45 b_radius5 bor_cad3df bgc_date in15 Time11"
											name="orderScopeOne" value="${orderSetup.orderScopeOne }"
											readonly
											onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
										<div class="f_l m_l15 m_r15">~</div>
										<input type="text" id="tser02"
											class="f_l w240 h45 b_radius5 bor_cad3df bgc_date in15 Time22"
											name="orderScopeTwo" value="${orderSetup.orderScopeTwo }"
											readonly
											onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
									</div>
									<div
										class="font14 c_384856 h45 lh45 p_l30 m_t10 ZhiXingLeiXing">
										<div class="f_l m_r15">执行类型:</div>
										<input type="hidden" id="executeType" name="executeGenre"
											value="${orderSetup.executeGenre }" />
										<div class="f_l m_l10">
											<div id="executeType_0" onclick="executeType(0);"
												class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
											</div>
											<div class="m_l10 f_l font14 c_384856 lh40">立即执行</div>
										</div>
										<div class="f_l m_l10">
											<div id="executeType_1" onclick="executeType(1);"
												class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
											</div>
											<div class="m_l10 f_l font14 c_384856 lh40">执行周期（每天）</div>
										</div>
									</div>
								</form>
								<div class="f_r m_t10 p_l20">
									<div onclick="startScreen('setup_form');"
										class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ Save">
										开始筛选</div>
									<div
										class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ Cancel">
										取消</div>
								</div>
								<div style="clear: both"></div>

							</div>
						</div>
					</div>







					<!--短信设置start-->
					<div class="w1590  bgc_fff c_384856 p_l50 font16 DuanXinSheZhi">
						<div class="w1030 p_b40">
							<div class="h47">
								<p>
									短信设置<span class="font14 c_00a0e9 p_l10 cursor_ CCC_XG">修改</span>
								</p>
							</div>

							<div class="die_SZ duanxinshezhibukebianji">
								<div class="font14 c_384856">
									<div class="f_l m_r10">
										<div class="p_l1_2">短信内容:</div>
									</div>
									<div class="f_l w928 h285 bgc_f4f6fa radius10">
										<textarea id="contentShow" disabled="disabled"
											class="w888 h240 outline_none radius10 border0 bgc_f4f6fa p_l20 p_t15 p_r20 Letter">${smsSetting.messageContent }</textarea>
										<!-- <div class="h30 w928 m_t_24 m_l20 c_bfbfbf">已输入：64字  当前计费：1条</div> -->
									</div>
								</div>

								<div class="clear w1030 h50 p_t40">
									<div
										class="w226 h50 lh50  b_radius5 bgc_00a0e9 c_fff text-center margin0_auto cursor_">
										<input id="addstatus" type="button" value="开启延时发货提醒"
											class="w226 h50 lh50  b_radius5 bgc_00a0e9 c_fff text-center margin0_auto cursor_  font16 lijikaiqi_ border0" />
										<input id="buttonId" type="hidden"
											value="${orderSetup.status }" /> <input id="orderSetupId"
											type="hidden" value="${orderSetup.id }" />
									</div>
								</div>
							</div>


							<!------------------------------    短信设置编部分        -------------------------------------->
							<div class="Live_SZ  min_h570 DXSZBianJiBuFen display_none">
								<form id="sms_form"
									action="${ctx}/delayRemind/saveOrUpdateSmsSetting"
									method="post">
									<input type="hidden" name="id" value="${smsSetting.id }">
									<div class="font14 c_384856 h40 p_l1_2">
										<div class="f_l">
											短信变量: <input id="messageId" type="hidden"
												name="messageVariable"
												value="${smsSetting.messageVariable }">
										</div>
										<div class="f_l m_r15">
											<ul>

												<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【订单号】')">
													订单号 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认5个字，以实际变量为准</span>
												</li>
												<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【下单时间】')">
													下单时间 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
												</li>
												<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【买家昵称】')">
													买家昵称 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw  b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
												</li>
												<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【买家姓名】')">
													买家姓名 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
												</li>
												<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
													class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
													onclick="addConsignee(document.getElementById('textContent'),'【订单金额】')">
													订单金额 <span
													class="position_absolute w226 l_h40 left-46 top_2_3vw  b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
												</li>
											</ul>
										</div>
										<!-- <div onclick="addConsignee(document.getElementById('textContent'),'【付款链接】');" class="w106 h40 lh40  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_r position_relative cursor_ ChaRuFuKuanLianJian">
                                        插入付款链接
                                    </div> -->

									</div>

									<div class="font14 c_384856 m_t30">
										<div class="f_l m_r10 w92">
											<div class="p_l1_2">短信内容:</div>
											<div class="m_t15 c_red">注意:短网址默认前后已加空格，请勿删除！否则可能导致网址打不开!</div>
										</div>
										<div class="f_l w928 h285 bgc_f4f6fa radius10">
											<input type="hidden" id="textContent1"
												value="${smsSetting.messageContent }"> <input
												type="hidden" id="settingType" name="settingType" value="11">
											<textarea id="textContent" name="messageContent"
												class="w888 h240 outline_none radius10 border0 bgc_f4f6fa p_l20 p_t15 p_r20 Letter11  text_area"
												onkeyup="addLength();"></textarea>
											<div class="h30 m_l20 c_bfbfbf f_l">
												已输入：<span id="inputNum" class="text_count">0</span>字 当前计费：<span
													id="contePrice">1</span>条
											</div>
											<div class="c_bfbfbf m_r15 f_r">
												退订回 <input maxlength="2"
													class="w20 h20 l_h20 border_0 bgc_f4f6fa" placeholder="N"
													id="refundMSG" />
											</div>
											<div class="c_bfbfbf m_r15 f_r c_00a0e9">
												<a href="${ctx}/crms/home/notice#duanxinxiangguan"
													class="c_bfbfbf m_r15 f_r c_00a0e9" target="_blank">计费规则</a>
											</div>
											<input id="actualDeduction_input" type="hidden"
												name="smsNumber" value="${smsSetting.smsNumber }" />
										</div>
									</div>

									<div class="w1030 font14 h40 l_h40">
										<div class="f_r">
											<div class="c_00a0e9 m_r15 f_l cursor_ ChangeLink"
												onclick="getCursortPosition(document.getElementById('textContent'));">转化为短链接</div>
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
												onKeyUp="updateShopName(this.value,'textContent');" /> <img
												src="${ctx }/crm/images/bianji.png"
												style="width: 1.2vw; margin-left: 0.5vw; cursor: pointer;"
												onclick="updateDisabled();"> <input id="smsSign"
												type="hidden" name="messageSignature" value="${ShopName}" />
										</div>
									</div>
									<div class="f_l w1030 font14 c_384856 h45 lh45 p_l30">
										<div class="m_r10">
											测试手机: <input
												class="h50 lh50 w670 bgc_f4f6fa p_l15 outline_none border0 Phone"
												type="text" placeholder="可输入5个测试手机号，以逗号隔开" /> <a
												class="m_l10 c_00a0e9 m_l20 ceShiFaSong1 cursor_">测试发送</a>
										</div>
									</div>
									<div class="f_r m_t50">
										<div onclick="submitSmsForm('sms_form')"
											class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ Save">
											保存</div>
										<div id="cancel"
											class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ CCC_Cancel">
											取消</div>
									</div>
									<!-- <div class="clear w226 h50 lh50  b_radius5 bgc_00a0e9 c_fff text-center margin0_auto m_t60 cursor_" >
                                    立即开启
                                </div> -->
								</form>
							</div>
						</div>
					</div>

					<div>
						<div
							class="position_absolute top_28vw right240 iphone w327 h642 display_none">
							<textarea disabled="disabled"
								class="text_area_copy m_l45 m_t150 w250 h370 border0 bgc_fff">${smsSetting.messageContent}
<%--                                 ${ShopName}亲，我们的交易已经成功，希望您能确认+好评，我们才有充裕的资金流转，来提高店铺质量和服务，祝您生活愉快！ --%>
                                </textarea>
						</div>
						<img
							class="w327 h642 position_absolute display_none ShouHuoTu z_2 right240 top362"
							src="${ctx}/crm/images/iPhone-gai.png">
					</div>
					<!--短信设置end-->
				</div>
				<!----------------------------------------------------------------------------------------       延时发货提醒 end                   ----------------------------------------------------------------------------------------------->






			</div>












		</div>
	</div>

	</div>

	<!-- 引入商品jsp -->
	<%@ include file="/WEB-INF/views/crms/header/itemUtils.jsp"%>


</body>
<script>
	$(function() {
		$(".load_top").load("top.html");
		$(".load_left").load("left.html");
		showClickPreview();

		//显示开启或关闭
		var buttonId = $("#buttonId").val();
		if (buttonId == '0') {
			$("#addstatus").val("已开启延时发货提醒");
		}
		if (buttonId == '1') {
			$("#addstatus").val("已关闭延时发货提醒");
		}
	});
	function showClickPreview() {
		if ($("#data_span").text() == 0) {
			$("#click_a").addClass('display_none');
		} else {
			$("#click_a").removeClass('display_none');
		}
	}

	function startScreen(formId) {
		var startOrderDate = $("#tser01").val();
		var endOrderDate = $("#tser02").val();
		if (endOrderDate < startOrderDate) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("保存失败，结束时间不能小于开始时间,请重新设置")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
			return false;
		} else {
			var url = "${ctx}/crms/marketingCenter/dataScreenlist";
			$.post(url, {
				"status" : "WAIT_SELLER_SEND_GOODS",
				"startOrderDate" : startOrderDate,
				"endOrderDate" : endOrderDate
			}, function(data) {
				$("#data_span").text(data.list.length);
				var dataList = data.list;
				var oids = "";
				$.each(dataList, function(i, result) {
					oids += result.oid + ",";
				});
				oids = oids.substring(0, oids.length - 1);
				//$("#orderIds").val(oids);
				if (formId != '') {
					document.getElementById(formId).submit();
				}
				showClickPreview();
			});
		}

		/*  var url = "${ctx}/crms/marketingCenter/dataScreenlist";
		 $.post(url,{"status":"WAIT_SELLER_SEND_GOODS","startOrderDate":startOrderDate,"endOrderDate":endOrderDate},function (data) {
		   $("#data_span").text(data.list.length);
		   var dataList = data.list;
		   var oids="";
		   $.each(dataList,function(i,result){
				oids+=result.oid+",";
		   });
		   oids=oids.substring(0,oids.length-1);
		   //$("#orderIds").val(oids);
		   if(formId != ''){
		   document.getElementById(formId).submit();
		   }
		 		showClickPreview();	
		 }); */
	}
	/*  function startScreen(formId){
	   if(confirm("确定要保存吗?")){
		   var flag = valiteTwoTime();
			if(flag){
				document.getElementById(formId).submit();
			}
	   }
	 } */

	//点击执行类型,并负责input
	function executeType(type) {
		if (type == 0) {
			$("#executeType").val(type);
		}
		if (type == 1) {
			$("#executeType").val(type);
		}
	}

	//回显执行类型的勾选状态
	$(function() {
		var executeType = $("#executeType").val();
		if (executeType == '0') {
			$("#executeType_0").addClass("bgc_check_blue");
		}
		if (executeType == '1') {
			$("#executeType_1").addClass("bgc_check_blue");
		}

		startScreen('');
	});

	//点击预览弹出新的窗口
	function openNewWin() {
		var startOrderDate = $("#tser01").val();
		var endOrderDate = $("#tser02").val();
		window
				.open("${ctx}/crms/list?status=WAIT_SELLER_SEND_GOODS&startOrderDate="
						+ startOrderDate + "&endOrderDate=" + endOrderDate);
	}

	//短信设置部分=============================================================================
	//回显短信到编辑部分
	$(function() {
		var textContent1 = $('#textContent1').val();
		$('#textContent').val(textContent1);
		addLength();
	});

	//保存短信设置功能 
	function submitSmsForm(fomrId) {

		var messageSignature = $('.sms_sign').val();
		$('#smsSign').val(messageSignature);

		var messageContent = $('#textContent').val();
		if (messageContent == "" || messageContent == null) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容不能为空!!!")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else if (messageContent.indexOf("【" + messageSignature + "】") < 0) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请核对短信签名是否正确!!!")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else {
			$(".duanxinshezhibukebianji").show();
			$(".DXSZBianJiBuFen").hide();
			$(".iphone").hide();
			document.getElementById(fomrId).submit();
		}

	}
	window.onload = function() {
		var smsSign = "${ShopName}";
		if (smsSign == '' || smsSign == null) {
			var content = $("#textContent").val();
			if (content.startsWith("【短信签名】")) {
				$(".text_area").val($("#textContent").val());
			} else {
				$(".text_area").val("【短信签名】" + $("#textContent").val());
			}
		} else {
			var content = $("#textContent").val();
			var smsSingChange = "【" + smsSign + "】";
			if (content.startsWith(smsSingChange)) {
				$(".text_area").val($("#textContent").val());
			}
		}
		addLength();
		var msg = $("#msg").val();
		if (msg != null && msg != "") {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text(msg)
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		}
	}

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
			document.getElementById("contePrice").innerText = count;
		} else if (length <= 70) {
			$("#actualDeduction_input").val(1);
			document.getElementById("contePrice").innerText = 1;
		}
		document.getElementById("inputNum").innerText = length;

		//将小手机里的内容末尾添加退订回N 
		if (contentVal == "") {
			$(".text_area_copy").val(contentVal);
			document.getElementById("inputNum").innerText = 0;
			document.getElementById("contePrice").innerText = 0;
		} else {
			$(".text_area_copy").val(contentVal.replace("退订回N", "") + "退订回N");
		}
	};

	//光标点击
	function addConsignee(obj, str) {
		var content = $("#textContent").val();
		if (content.indexOf(str) > 0) {
			return false;
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

		addLength();
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
					}, 3000)
				} else {
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("保存短语失败")
					setTimeout(function() {
						$(".tishi_2").hide()
					}, 3000)
				}
			}
		});
	}

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

	//点击使用将模板添加到文本域中
	$(document).on("click", ".Template_div", function() {
		var content = $(this).children().val();
		var changeContent = $(".text_area").val();
		var endContent = changeContent + "" + content;
		$("#messageId").val('');
		$(".text_area").val(endContent);
		addLength();
		$(".text_area_copy").text($(".text_area").val());
		//启用滚动条
		$(document.body).css({
			"overflow-x" : "auto",
			"overflow-y" : "auto"
		});
		$(".text_model_window").hide();
	});

	//测试短信发送
	$(".ceShiFaSong1").click(function() {
		var that=$(this);
		if($(this).hasClass('on'))return;
		$(this).addClass('on');	
		var phonez = /^1[3|4|5|7|8]\d{9}$/;
		var phone = $(".Phone").val();//获取电话
		var content = $("#textContent1").val();//获取短信内容
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

	//=============================
	//开启关闭设置数据
	$("#addstatus").click(function() {
		var buttonId = $("#buttonId").val();
		if (buttonId == '0') {
			$("#buttonId").val('1');
		} else if (buttonId == '1') {
			$("#buttonId").val('0');
		}
		addstatus();
	});

	//获取开启或关闭状态
	function addstatus() {
		var buttonId = $("#buttonId").val();
		var orderSetupId = $("#orderSetupId").val();
		if (orderSetupId == '') {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请将以上数据设置完毕")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else {
			var url = "${ctx}/delayRemind/openOrCloseStatus";
			$.post(url, {
				"buttonId" : buttonId,
				"orderSetupId" : orderSetupId
			}, function(data) {
				if (data.status != null) {
					if (data.status == '0') {
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("开启成功")
						setTimeout(function() {
							$(".tishi_2").hide()
						}, 3000)
						$("#addstatus").val("已开启延时发货提醒");
						loadMap();
					} else if (data.status == '1') {
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("关闭成功")
						setTimeout(function() {
							$(".tishi_2").hide()
						}, 3000)
						$("#addstatus").val("已关闭延时发货提醒");
						loadMap();
					}
				} else {
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("开启或关闭失败")
					setTimeout(function() {
						$(".tishi_2").hide()
					}, 3000)
				}
			});
		}
	}

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
			}, function(data) {
				if (data.link != null) {
					$("#textContent")
							.val(startContent + data.link + endContent);
					addLength();
				}
			});
		} else {
			var textContent = $("#textContent").val();
			$("#textContent").val(textContent + kehuduan);
			addLength();
		}

	}
	//点击取消获取之前短信放入内容

	$("#cancel").click(function() {
		var contentShow = $("#contentShow").val();
		$("#textContent").val(contentShow);
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

	//点击快捷键开关
	$("#img_ys").click(function() {
		var status = $(this).next().val();
		if (status != '') {
			if (status == '0') {
				$("#buttonId").val('1');
				$("#addstatus").val("已关闭延时发货提醒");
			} else if (status == '1') {
				$("#buttonId").val('0');
				$("#addstatus").val("已开启延时发货提醒");
			}
		}
		loadMap();
	});

	//提交form表单
	function findOrderSendRedord(formId) {
		document.getElementById(formId).submit();
	};
</script>
</html>
