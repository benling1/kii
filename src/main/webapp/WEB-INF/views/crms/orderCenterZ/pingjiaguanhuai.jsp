<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>评价关怀</title>
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
<!--<script src="js/changguicuifu.js" type="text/javascript" charset="utf-8"></script>-->
<!--<script type="text/javascript" src="js/model.js" ></script>-->
<!--<script src="js/xiadanguanhuai.js" type="text/javascript" ></script>
    <script type="text/javascript" src="js/cuifutxing.js" ></script>-->
<!--<script src="js/yingxiaozhongxin.js" type="text/javascript" ></script>-->
<!--<script src="js/baobeiguanhuai.js" type="text/javascript" charset="utf-8"></script>-->

<script src="${ctx}/crm/js/pingjiaguanhuai.js" type="text/javascript"
	charset="utf-8"></script>
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
		<div class="w1703 m_l200">
			<!--------------------顶部导航栏-------------------->
			<div class="load_top"></div>

			<!--------------------主要内容区-------------------->
			<div class="w1660 m_t70 m_l30">
				<div class="w100_ h50 lh50 bgc_fff font18">
					<div class="f_l h50 w4 bgc_1d9dd9"></div>
					<div class="f_l m_l15 c_384856" style="font-size:1vw;">订单中心</div>
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
							<li class="w140 bgc_fff text-center bgc_f1f3f7 f_l"><a
								class="c_384856 display_block" href="${ctx}/crms/reviewCare">
									评价关怀 </a></li>
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block"
								href="${ctx}/appraiseAmend/showAppraiseAmend"> 中差评管理 </a></li>
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block"
								href="${ctx}/OrderReminder/jumOrderReminder"> 手动订单提醒 </a></li>
							<li style="clear:both;"></li>
						</ul>
					</div>
				</div>
				<form id="formId" action="${ctx}/crms/saveAutoRate" method="post">
					<div class="w1660 bgc_f1f3f7 c_384856 ">
						<div class=" p_l40 p_t27 position_relative">
							<p class="font20">自动评价设置</p>
							<p class="font14 p_t8">检测到卖家店铺中有交易完成的订单，且订单满足您的过滤条件，系统会自动进行评价</p>
							<div class="h30">
								<div
									class="w110 h40 l_h40 f_r bgc_fff m_r15 text-center  cursor_ ZhanKai">
									<img class="m_r10 JTX display_none"
										src="${ctx}/crm/images/箭头下.png" /> <img class="m_r10 JTS"
										src="${ctx}/crm/images/箭头上.png" /> <span class="LiuChengText">收回流程</span>
								</div>
							</div>
							<input type="hidden" id="msg" name="msg" value="${msg}">
						</div>
						<%@ include file="/WEB-INF/views/crms/header/liucheng.jsp" %>
						<div class="font16 c_384856 h50 lh50 m_t25" style="padding-left: 2vw;">
							<div class="w150 h50 text-center f_l bgc_fff cursor_">
								<a class="c_384856 display_block" href="${ctx}/crms/reviewCare">自动评价</a>
							</div>
							<%-- <div class="w150 h50 text-center f_l bgc_e3e7f0 cursor_">
								<a class="c_384856 display_block" href="${ctx}/crms/batchReview">批量评价</a>
							</div> --%>
							<div class="w150 h50 text-center f_l bgc_e3e7f0 cursor_">
								<a class="c_384856 display_block"
									href="${ctx}/crms/reviewRecord">评价记录</a>
							</div>
						</div>
					</div>
					<!----------下部---------->
					<div class="w1660 bgc_fff p_1 p_t35  p_l40 ">
						<input name="id" type="hidden" value="${orderRateCareSetup.id}">
						<div class="p_l80 font16 c_38485 6">
							<div class="f_l h 22 lh22 m_r10">
								当前状态： <input id="statusId" type="hidden" name="status"
									value="${orderRateCareSetup.status}">
							</div>
							<ul class=" h22 lh22 f_l ">
								<li class="h22 lh22 f_l m_r20">
									<div onclick="addStatus('0')"
										class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l kaiqi">
										<img class="cursor_ group_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l text-left m_l10">
										<!--	<input type="radio" name="kaiqi"  id="" value="" />-->
										开启
									</div>
								</li>
								<li class="h22 lh22 f_l m_r20">
									<div onclick="addStatus('1')"
										class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l guanbi">
										<img class="cursor_ group_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l text-left m_l10">
										<!--	<input type="radio" name="kaiqi"  id="" value="" />-->
										关闭
									</div>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
						<div class="clbo  p_l80 font16 c_384856 m_t45">
							<div class="f_l h22 lh22 m_r10">
								评价条件： <input id="rateChooseId" name="rateChoose" type="hidden"
									value="${orderRateCareSetup.rateChoose}"> <input
									id="deferRateDayId" name="deferRateDay" type="hidden"
									value="${orderRateCareSetup.deferRateDay}">
							</div>
							<ul class=" h22 lh22 f_l m_r10">
								<li class="h22 lh22 f_l m_r20 pingjia_check ">
									<div onclick="addRateChoose('1')"
										class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l liji pingjia_check1">
										<img class="cursor_ group_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l text-left m_l10">
										<input value="1" type="hidden">
										<!--<input type="radio" name="pingjia"  id="" value="" />-->
										<span class="c_00a0e9 m_l10 m_r10">立即评</span>(交易成功后无论买家是否立即评价，立即评价
										)
									</div>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
						<div class="clbo  p_l80 font16 c_384856">
							<div class="f_l h22 lh22 m_r10 m_t30" style="visibility: hidden;">
								评价条件：</div>
							<ul class=" h22 lh22 f_l m_r10 m_t30">
								<li class="h22 lh22 f_l m_r20 pingjia_check">
									<div onclick="addRateChoose('2')"
										class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l yanchi pingjia_check1">
										<img class="cursor_ group_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l text-left m_l10">
										<input value="2" type="hidden">
										<!--<input type="radio" name="pingjia"  id="" value="" />-->
										<span class="c_00a0e9 m_l10 m_r10">延迟评</span>(交易成功后，延迟评价 )
									</div>
								</li>
								<li
									class="h22 lh22 f_l m_r20 pingjia_check_1 display_none check1">
									<div
										class="w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK bgc_check_blue">
									</div>
									<div class="f_l text-left m_l10">买家评价完立即评价</div>
								</li>
								<li
									class="h22 lh22 f_l m_r20 pingjia_check_2 display_none check2">
									<div
										class="w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ bgc_check_blue">
									</div>
									<div class="f_l text-left m_l10">
										<p class="f_l m_l10">交易完成后,延迟</p>
										<div class="h45 f_l m_l10 m_r10">
											<div class=" f_l m_t-8 ">
												<div class="nice-select h45 lh45 nice-selectw w50"
													name="nice-select">
													<input class="h45 lh45 settime" type="text"
														value="${orderRateCareSetup.deferRateDay}" placeholder="1">
													<ul id="deferRateUl">
														<li value="1" class="ddzx_in_out1">1</li>
														<li value="2" class="ddzx_in_out1">2</li>
														<li value="3" class="ddzx_in_out1">3</li>
														<li value="4" class="ddzx_in_out1">4</li>
														<li value="5" class="ddzx_in_out1">5</li>
														<li value="6" class="ddzx_in_out1">6</li>
														<li value="7" class="ddzx_in_out1">7</li>
														<li value="8" class="ddzx_in_out1">8</li>
														<li value="9" class="ddzx_in_out1">9</li>
														<li value="10" class="ddzx_in_out1">10</li>
														<li value="11" class="ddzx_in_out1">11</li>
														<li value="12" class="ddzx_in_out1">12</li>
														<li value="13" class="ddzx_in_out1">13</li>
														<li value="14" class="ddzx_in_out1">14</li>
													</ul>
												</div>
											</div>
										</div>
										<p class="f_l">天评价</p>
									</div>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
						<div class="clbo  p_l80 font16 c_384856">
							<div class="f_l h22 lh22 m_r10 m_t30" style="visibility: hidden;">
								评价条件：</div>
							<ul class=" h22 lh22 f_l m_r10 m_t30">
								<li class="h22 lh22 f_l m_r20 pingjia_check">
									<div onclick="addRateChoose('3')"
										class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l qiang pingjia_check1">
										<img class="cursor_ group_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l text-left m_l10">
										<input value="3" type="hidden">
										<!--<input type="radio" name="pingjia"  id="" value="" />-->
										<span class="c_00a0e9 m_l10 m_r10">抢评</span>(在交易成功后14日23小时后评价逾期
										前1小时评价 )
									</div>
								</li>
								<li
									class="h22 lh22 f_l m_r20 pingjia_check_1 display_none check3">
									<div
										class="w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK bgc_check_blue">
									</div>
									<div class="f_l text-left m_l10">
										<!--<input type="radio" name="pingjia"  id="" value="" />-->
										<span class=" m_l10 m_r10">买家评完立即评价</span>
									</div>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
						<div class="clbo  p_l64 font16 c_384856 ">
							<div class="f_l h22 lh22 m_t40 m_r10">
								黑名单设置： <input id="blackRateId" type="hidden"
									name="blacklistRateType"
									value="${orderRateCareSetup.blacklistRateType}">
							</div>
							<ul class="f_l m_t40">
								<li class="h22 lh22  m_r20">
									<div onclick="addBlackRate('0')"
										class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l buzidong">
										<img class="cursor_ group_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l text-left m_l10">
										<!--<input type="radio" name="kehu"  id="" value="" />-->
										当客户是黑名单时，不自动进行评价
									</div>
								</li>
								<li class="h22 lh22  m_r20 m_t35 f_l">
									<div onclick="addBlackRate('1')"
										class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l zidong">
										<img class="cursor_ group_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l text-left m_l10">当客户是黑名单时，自动给予指定类型评价</div>
									<div class="f_l  font16 c_384856 m_l10 ">评价类型:</div>
									<div class="w600 h45 f_l">
										<div class="wrap f_l w140 m_l10 m_t-8">
											<div class="nice-select h45 lh45 nice-selectw"
												name="nice-select">
												<input readonly="readonly" id="rateBlack"
													class="h45 lh45 w140 settime" type="text" value=""
													placeholder="好评">
												<ul id="blackRateUl">
													<li value="1" class="ddzx_in_out1">好评</li>
													<li value="2" class="ddzx_in_out1">中评</li>
													<li value="3" class="ddzx_in_out1">差评</li>
												</ul>
											</div>
										</div>
									</div>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
						<div class="clbo  p_l64 font16 c_384856">
							<div class="f_l h22 lh22 m_r10 m_t30" style="visibility: hidden;">
								黑名单设置：</div>
							<input id="blackListId" name="autoAddBlacklist" type="hidden"
								value="${orderRateCareSetup.autoAddBlacklist}">
							<ul id="blackListUl" class=" h22 lh22 f_l m_l m_t30">
								<li class="h22 lh22 f_l ">
									<div class="f_l">将给我</div>
									<div onclick="addRate('neutral')"
										class="w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK GXK1">
									</div>
									<div class="f_l text-left m_l10">
										<!--<input type="checkbox" name="PJ"  id="" value="" />-->
										中评
									</div>
								</li>
								<li class="h22 lh22 f_l m_r10">
									<div class="f_l text-left m_l10">或</div>
								</li>

								<li class="h22 lh22 f_l m_r20">
									<div onclick="addRate('bad')"
										class="m_r10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ MaiJiaBiaoJiGXK GXK2">
									</div>
									<div class="f_l text-left ">
										<!--<input type="checkbox" name="PJ"  id="" value="" />-->
										差评 的客户自动加入黑名单
									</div> <a href="javascript:openNewWin()"><div
											class="f_l w100 h40 l_h40 text-center bgc_00a0e9 b_radius5 cursor_ c_fff m_l15 m_t-8">
											设置黑名单</div></a>
								</li>
							</ul>
							<div class="clear"></div>
						</div>

						<div class="clbo  p_l80 font16 c_384856">
							<div class="f_l h22 lh22 m_t40 m_r10">
								评价类型： <input id="resultId" type="hidden" name="result"
									value="${orderRateCareSetup.result}">
							</div>
							<ul id="resultUl" class=" h22 lh22 f_l m_t40">
								<li class="h22 lh22 f_l m_r20">
									<div onclick="addResult('good')"
										class="pingjia cursor_ group_check_only w20 h20 border_d9dde5 b_radius5 f_l hao">
										<img class="cursor_ group_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l text-left m_l10">好评</div>
								</li>
								<li class="h22 lh22 f_l m_r20">
									<div onclick="addResult('neutral')"
										class="pingjia cursor_ group_check_only w20 h20 border_d9dde5 b_radius5 f_l zhong">
										<img class="cursor_ group_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l text-left m_l10">中评</div>
								</li>
								<li class="h22 lh22 f_l m_r20">
									<div onclick="addResult('bad')"
										class="pingjia cursor_ group_check_only w20 h20 border_d9dde5 b_radius5 f_l cha">
										<img class="cursor_ group_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l text-left m_l10">差评</div>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
						
						<div class="clbo  p_l80 font16">
							<div class="f_l h22 lh22 m_r10 m_t40 m_b30 c_384856">
								评价内容： <input id="resultType" type="hidden">
							</div>
							<div class="clear"></div>
						</div>
						<!-- 好评内容  -->
						<div class="pingjia_2 good_content c_384856" style="padding-left:4.2vw;">
							<div class="p_11">
								<div>
									<span style="font-size:0.85vw;">好评内容</span>
									<span class="tianjia_good cursor_"  style="color:#00a0e9;" onclick="updateContent('${orderRateCareSetup.content}','goodRate')">修改</span>
								</div>
								<div id="goodRate" style="height:5vw;" class="w936 h285 bgc_f4f6fa f_l m_t30 m_l10 p_t20 p_l20 p_r20 p_b20 radius10 border0 f_area1 m_l80">${orderRateCareSetup.content}</div>
								<input id="goodContent" name="content" type="hidden" value="${orderRateCareSetup.content}">
								<div class="clear"></div>
							</div>
						</div>
						<!-- 中评内容 -->
						<div class="pingjia_2 neutral_content c_384856" style="padding-left:4.2vw;">
							<div class="p_11">
								<div>
									<span style="font-size:0.85vw;">中评内容</span>
									<span class="tianjia_neutral cursor_" style="color:#00a0e9;" onclick="updateContent('${orderRateCareSetup.neutralContent}','neutralRate')">修改</span>
								</div>
								<div id="neutralRate" readonly="readonly" style="height:5vw;" class="w936 h285 bgc_f4f6fa f_l m_t30 m_l10 p_t20 p_l20 p_r20 p_b20 radius10 border0 f_area1 m_l80" >${orderRateCareSetup.neutralContent}</div>
								<input id="neutralContent" name="neutralContent" type="hidden" value="${orderRateCareSetup.neutralContent}">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<!-- 差评内容 -->
						<div class="pingjia_2 bad_content c_384856" style="padding-left:4.2vw;">
							<div class="p_11">
								<div>
									<span style="font-size:0.85vw;">差评内容</span>
									<span class="tianjia_bad cursor_" style="color:#00a0e9;" onclick="updateContent('${orderRateCareSetup.badContent}','badRate')">修改</span>
								</div>
								<div id="badRate" readonly="readonly" style="height:5vw;" class="w936 bgc_f4f6fa f_l m_t30 m_l10 p_t20 p_l20 p_r20 p_b20 radius10 border0 f_area1 m_l80" >${orderRateCareSetup.badContent}</div>
								<input id="badContent" name="badContent" type="hidden" value="${orderRateCareSetup.badContent}">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div onclick="formSubmit('formId')"
							class="clbo  m_t50 f_l w226 h46 l_h40 text-center bgc_00a0e9 b_radius5 cursor_ c_fff m_l584 m_b40">
							保存设置</div>
						<div class="clear"></div>
					</div>
				</form>
			</div>

			<!--<div class="w1620 bgc_fff p_t35 p_l40 p_b40 ddzx_in_in position_relative">-->
			<!--基本设置，修改-->
			<!--
                	</div>-->
			<!--</div>-->

			<!---------------多店铺绑定--------------->
			<div class="binding_store display_none">
				<!----------上部---------->
				<div class="w1620 h90 bgc_f1f3f7 c_384856 p_l40 p_t30">

					<!---------------标题--------------->
					<div class="font24 m_b10">多店铺绑定</div>
					<!---------------描述--------------->
					<div class="font14">
						可以讲部分客户添加到黑名单中，在群发短信时，可以过滤掉黑名单客户，不向黑名单客户发送短信</div>

				</div>

			</div>

			<!---------------手机号设置--------------->

			<!---------------添加黑名单弹窗--------------->

			<!---------------导入黑名单弹窗--------------->

		</div>
	</div>

	<div id="showTemp"
		class="w100_ h1000 rgba_000_5 position_fixed top0 display_none z_12 tianjia_2">
		<input id="show_1" type="hidden" value="${show_1}"> <input
			id="show_2" type="hidden" value="${show_2}">
		<div class=" position_absolute top85 left320">
			<div class="w1102 h718 bgc_f1f3f7 change_text ">
				<img class=" f_r w40 h40 close_tianjia_2 cursor_"
					src="${ctx}/crm/images/chazi.png" />
				<div class="w1102 h50 text-center l_h50">选择短信模板</div>
				<div class="w1102 h668 ">
					<div class="w495 h668 bgc_fff f_l">
						<div class="w495 h75">
							<div class="w495 h20 text-center p_t15 font14">编辑内容</div>
						</div>
						<div class="w458 h363 bgc_fff">
							<textarea id="editContent" maxlength="500"
								class="bgc_f4f6fa text_area border_d9dde5 b_radius5 w458 h363 m_l15"
								onkeyup="addLength();"></textarea>
							<div class="h30 w458 m_t_24 m_l20 c_bfbfbf"
								style="margin-top: -2.25vw">
								已输入：<span id="inputNum" class="text_count">0</span>/500字
							</div>
						</div>
						<div class="w495 h40 m_t60  ">
							<div onclick="addTextArea()"
								class="m_l180 w100 h40 border_00a0e9 c_00a0e9 close tk b_radius5 text-center f_l l_h40 cursor_ font14  m_l10 close_inside">
								确定</div>
						</div>
					</div>

					<div class="w595 h668 f_l bgc_fff m_l10 ">
						<div class="w590 h50 bgc_f1f3f7">
							<div onclick="findAllTemp()" id="anliId"
								class="w130 h50 text-center out bgc_fff l_h50 f_l cursor_ moban1">
								案例模板</div>

							<div onclick="historyTemp()" id="lishiId"
								class="w150 h50 text-center out l_h50 bgc_e3e7f0 f_l cursor_ moban1">
								历史使用</div>

						</div>

						<div class="in moban2">

							<div class="w560 h490 m_l15 m_t10 overflow_auto">
								<table id="smsTable" class="margin0_auto smsTemplate_table">
									<%-- <c:if test="${paginationTemp != null}">
							<c:forEach items="${paginationTemp.list}" var="smsTemp">
								<tr class=" h105 bgc_fafafa">
									<td class="w400 ">${smsTemp.content}</td>
									<td class="w160 ">
										<div onclick="addContent('${smsTemp.content}')" class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
									</td>
								</tr>
							</c:forEach>
						</c:if> --%>
								</table>
							</div>
							<!-- 分页 -->
							<div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">

								<div class="f_r w470 h24 l_h22 font12">
									<!-- 分页不好使了，我用下拉条了哦 -->
									<%--  <c:if test="${paginationTemp != null}">
		                	<c:forEach items="${paginationTemp.pageView}" var="page"> 
		                		${page}
		                	</c:forEach>
		                </c:if> --%>
								</div>
							</div>

						</div>

						<div class="in display_none moban2">

							<div class="w560 h490 m_l15 m_t10 h490 overflow_auto">
								<table id="historyTemp" class="margin0_auto smsTemplate_table">
									<!-- <tr class=" h105 bgc_fafafa">
								<td class="w400 "></td>
								<td class="w160 ">
									<div class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
								</td>
							</tr>
							<tr class="h85 bgc_f4f4f4">
								<td class="w400 "></td>
								<td class="w160">
									<div class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
								</td>
							</tr> -->
								</table>
							</div>

							<div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">

								<div class="f_r w470 h24 l_h22 font12">
									<!--  <div class="f_l w45 h22 bor_e0e6ef text-center m_r4 cursor_">上一页</div>
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
		                    <div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div> -->
								</div>
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
		$(".load_top").load("top.html");
		$(".load_left").load("left.html");
	})
	window.onload = function(){
		var msg = $("#msg").val();
		if(msg!=null && msg != ""){
			$(".tishi_2").show();
				$(".tishi_2").children("p").text(msg)
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000);
		}
	}
	//点击自动评价状态的开启、关闭
	function addStatus(status){
		var statusVal = $('#statusId').val();
		if(status == statusVal){
			$('#statusId').val('');
		}else{
			$('#statusId').val(status);
		}
	};
	//点击添加评价条件到input
	function addRateChoose(rateChoose){
		var rateChooseVal = $('#rateChooseId').val();
			$('#rateChooseId').val(rateChoose);
			$('#deferRateDayId').val('');
	};
	//选择延迟评价天数
	$('#deferRateUl li').click(function(){
		$('#deferRateDayId').val($(this).val());
	});
	//点击将客户是黑名单时的评价类型添加到input中
	function addBlackRate(blackRate){
		var blackRateVal = $('#blackRateId').val();
		if(blackRate == blackRateVal){
			$('#blackRateId').val('');
		}else{
			$('#blackRateId').val(blackRate);
		}
	}
	//当客户是黑名单时，选择评价类型
	$('#blackRateUl li').click(function(){
		$('#blackRateId').val($(this).val());
		$('.zidong').children().removeClass('display_none');
		$('.buzidong').children().addClass('display_none');
	});
	//中评的客户自动加入黑名单
	function addRate(neutral){
		var blackListVal = $('#blackListId').val();
		if(blackListVal == ''){
			$('#blackListId').val(neutral);
		}else{
			var balckListArr = blackListVal.split(',');
			var neuIndex = balckListArr.indexOf(neutral);
			if(neuIndex > -1){
				balckListArr.splice(neuIndex,1);
				$('#blackListId').val(balckListArr.toString());
			}else{
				balckListArr.push(neutral);
				$('#blackListId').val(balckListArr.toString());
			}
		}
	};
	//差评的客户自动加入黑名单
	/* function addBad(bad){
		var blackListVal = $('#blackListId').val();
		var balckLists = blackListVal.split(',');
		if(balckLists.length == 2){
			$('#blackListId').val('neutral');
		}else if(balckLists.length == 1){
			if(blackListVal == ''){
				$('#blackListId').val(bad);
			}else{
				if(blackListVal == bad){
					$('#blackListId').val('');
				}else{
					$('#blackListId').val(blackListVal + ',' + bad);
				}
			}
		}
	}; */
	//点击将评级类型添加到input中
	function addResult(result){
		$("#resultId").val(result);
	}
	//点击修改按钮，查询短信模板
	function findAllTemp(){
		$('#smsTable').empty();
		var url = "${ctx}/crms/autoRateTemplate";
		$.post(url,{"type":"中差评"},function(data){
			var smsTemplate = data.smsTemplate;
			var appendTemp = "";
			$.each(smsTemplate,function(i,result){																												  
				appendTemp = "<tr class='h105 bgc_fafafa'><td class='w400'>"+result.content+"</td><td class='w160 '><div onclick='addContent("+result.id+")' id='click_use"+result.id+"' class='w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9'>使用<input id='content' type='hidden' value='"+result.content+"'/></div></td></tr>";
				$('#smsTable').append(appendTemp);
			})
			addLength();
		});	
	};
	//添加模板到文本域
	function addContent(id){
		$('.text_area').val('');
		var click_use = $("#click_use"+id);
		var sms= click_use.children().val();
		$('.text_area').val(sms);
		addLength();
		var type = "好中差";
		$.ajax({
			url:"${ctx}/crms/addHistoryTemp",
			data:{"type":type,"id":id},
			post:"post",
			dataType:"json",
			success:function(data){
				if(data.message){
					//alert("success");
				}
			}
		});
	}
	//点击历史使用，查询历史使用模板
	function historyTemp(){
		$('#historyTemp').empty();
		var type = $('#resultId').val();
		var url = "${ctx}/crms/autoRateTemplate";
		$.post(url,{"type":"历史使用"},function(data){
			var historyTemp = data.historyTemp;
			var appendTemp = "";
			$.each(historyTemp,function(i,result){
				appendTemp = "<tr class='h105 bgc_fafafa'><td class='w400'>"+result.content+"</td><td class='w160 '><div onclick='addContentHis("+result.templateId+")' id='click_use_his"+result.templateId+"' class='w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9'>使用<input id='contentHis' type='hidden' value='"+result.content+"'/></div></td></tr>";
				$('#historyTemp').append(appendTemp);
			});
		});
	};
	//添加模板到文本域并添加到历史使用
	function addContentHis(id){
		$('.text_area').val('');
		var click_use = $("#click_use_his"+id);
		var sms= click_use.children().val();
		$('.text_area').val(sms);
		addLength();
		var type = "好中差";
		//点击使用模板后添加到模板使用记录
		$.ajax({
			url:"${ctx}/crms/addHistoryTemp",
			data:{"type":type,"id":id},
			post:"post",
			dataType:"json",
			success:function(data){
				if(data.message){
					//alert("success");
				}
			}
		});
	}
	//提交保存设置
	function formSubmit(formId){
		document.getElementById(formId).submit();
	};
	//页面加载
	$(function(){
		//页面回显自动评级状态
		var statusVal = $('#statusId').val();
		if(statusVal == '0'){
			$('.kaiqi').children().show();
			$('.guanbi').children().hide();
		}else if(statusVal == 1){
			$('.kaiqi').children().hide();
			$('.guanbi').children().show();
		}
		//回显评价条件
		var rateChooseVal = $('#rateChooseId').val();
		if(rateChooseVal == '1'){
			$('.liji').children().show();
			$('.liji').children().removeClass("display_none");
		}else if(rateChooseVal == '2'){
			$('.yanchi').children().show();
			$('.yanchi').children().removeClass("display_none");
			$('.check1').show();
			$('.check2').show();
		}else if(rateChooseVal == '3'){
			$('.qiang').children().show();
			$('.qiang').children().removeClass("display_none");
			$('.check3').show();
		}
		//回显黑名单设置
		var blackRateVal = $('#blackRateId').val();
		if(blackRateVal == '0'){
			$('.buzidong').children().show();
			$('.zidong').children().hide();
		}else if(blackRateVal == '1'){
			$('.zidong').children().show();
			$('.buzidong').children().hide();
			$('#rateBlack').val('好评');
		}else if(blackRateVal == '2'){
			$('.zidong').children().show();
			$('.buzidong').children().hide();
			$('#rateBlack').val('中评');
		}else if(blackRateVal == '3'){
			$('.zidong').children().show();
			$('.buzidong').children().hide();
			$('#rateBlack').val('差评');
		}
		//回显中差评自动添加到黑名单
		var blackListVal = $('#blackListId').val();

		var blackListArr = blackListVal.split(',');
		if(blackListArr.length == 2){
			$('.GXK1').addClass("bgc_check_blue");
			$('.GXK2').addClass("bgc_check_blue");
		}else if(blackListArr.length == 1){
			if(blackListVal === ('neutral')){


				$('.GXK1').addClass("bgc_check_blue");
				$('.GXK2').removeClass("bgc_check_blue");
			}else if(blackListVal === ('bad')){

				$('.GXK2').addClass("bgc_check_blue");
				$('.GXK1').removeClass("bgc_check_blue");
			}else{
				$('.GXK1').removeClass("bgc_check_blue");
				$('.GXK2').removeClass("bgc_check_blue");
			}
		}
		//回显设置评价类型/回显评价内容(好中差)
		var resultVal = $('#resultId').val();
		if(resultVal == 'good'){
			$('.hao').children().show();
		}else if(resultVal == 'neutral'){
			$('.zhong').children().show();
		}else if(resultVal == 'bad'){
			$('.cha').children().show();
		}
		
	});
	//评价模板内容框字数的改变
	function addLength(){
		var length = $('.text_area').val().length;
		document.getElementById('inputNum').innerText = length;
	};
	
	//点击模板选择框的确定将内容添加到页面对应的内容区以及隐藏域中
	function addTextArea(){
		var resultTypeVal = $('#resultType').val();
		if(resultTypeVal == 'good'){
			$('#goodRate').text($('.text_area').val());
			$('#goodContent').val($('.text_area').val());
		}else if(resultTypeVal == 'neutral'){
			$('#neutralRate').text($('.text_area').val());
			$('#neutralContent').val($('.text_area').val());
		}else if(resultTypeVal == 'bad'){
			$('#badRate').text($('.text_area').val());
			$('#badContent').val($('.text_area').val());
		}
	};
	//修改内容按钮
	function updateContent(content,id){
		findAllTemp();
		$('.text_area').val($('#' + id).text());
		addLength();
	};
	
	function openNewWin(){  
		 window.open("${ctx}/crms/marketingCenter/smsBlack");  
	}
	$('.pingjia_check').click(function(){
		$('#rateChooseId').val($(this).find('input').val());
	})
	
</script>
</html>
