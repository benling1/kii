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

<script src="${ctx}/crm/js/pingjiaguanhuai_2.js" type="text/javascript"
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
				<div class="w1660 h340  bgc_f1f3f7 c_384856  ">
					<div class="p_l40 p_t27">
						<p class="font20">批量评价</p>
						<p class="font14 p_t8">筛选所有可以评价的未评价订单进行评价</p>
						<div class="h40">
							<div
								class="w110 h40 l_h40 f_r bgc_fff m_r15 text-center  cursor_ ZhanKai">
								<img class="m_r10 JTX display_none"
									src="${ctx}/crm/images/箭头下.png" /> <img class="m_r10 JTS"
									src="${ctx}/crm/images/箭头上.png" /> <span class="LiuChengText">收回流程</span>
							</div>
						</div>
					</div>
					<%@ include file="/WEB-INF/views/crms/header/liucheng.jsp" %>
					<div class="font16 c_384856 h50 lh50 m_t30 ">
						<div class="w150 h50 text-center f_l bgc_e3e7f0 cursor_">
							<a class="c_384856 display_block" href="${ctx}/crms/reviewCare">自动评价</a>
						</div>
						<div class="w150 h50 text-center f_l bgc_fff cursor_">
							<a class="c_384856 display_block" href="${ctx}/crms/batchReview">批量评价</a>
						</div>
						<div class="w150 h50 text-center f_l bgc_e3e7f0 cursor_">
							<a class="c_384856 display_block" href="${ctx}/crms/reviewRecord">评价记录</a>
						</div>
					</div>
				</div>
				<!----------下部---------->
				<div class="p_1  w1660 h1100 bgc_fff ">
					<p class="p_t15 m_l80 font14 c_384856">提示：订单交易成功后15天内进行评价，请注意评价有效时间，快要过期的订单会列在前头</p>
					<div class="h250 xia_bu">
						<form id="formId" action="${ctx}/crms/batchReview" method="post">
							<div class="h22 font14 c_384856 m_t35 m_l80 screen_">
								<div class="f_l h22 lh22 m_r10 font16">
									快速筛选： <input id="screenId" name="screen" value="${screen}"
										type="hidden">
								</div>

								<ul class=" h22 lh22 f_l ">
									<li class="h22 lh22 f_l m_r20">
										<div onclick="addScreen('0')"
											class="cursor_  w20 h20 border_d9dde5 b_radius5 f_l bgc_e0e6ef quanbu">
											<img class="cursor_  display_none w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l text-left m_l10">全部</div>
									</li>
									<li class="h22 lh22 f_l m_r20">
										<div onclick="addScreen('1')"
											class="cursor_  w20 h20 border_d9dde5 b_radius5 f_l bgc_e0e6ef quanbu1">
											<img class="cursor_  display_none w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l text-left m_l10">7天后过期</div>
									</li>
									<li class="h22 lh22 f_l m_r20">
										<div onclick="addScreen('2')"
											class="cursor_  w20 h20 border_d9dde5 b_radius5 f_l bgc_e0e6ef quanbu2">
											<img class="cursor_ display_none w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l text-left m_l10">1天后过期</div>
									</li>
									<li class="h22 lh22 f_l m_r20">
										<div onclick="addScreen('3')"
											class="cursor_  w20 h20 border_d9dde5 b_radius5 f_l bgc_e0e6ef quanbu3">
											<img class="cursor_  display_none w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l text-left m_l10">1小时后过期</div>
									</li>
								</ul>
							</div>
							<div class="f_l h22 lh22  m_t45 m_l80 font16">
								买家评价： <input id="rateId" name="buyerRate" value="${buyerRate}"
									type="hidden">
							</div>
							<div class="w160 h45 f_l m_t35 m_r10 m_t16">
								<div class=" f_l h45 lh45 w160 m_l10 ">
									<div class="nice-select h45 lh45 w160" name="nice-select">
										<input id="rate" readonly="readonly"
											class="h45 lh45 w160 settime" value="全部" type="text"
											placeholder="全部">
										<ul id="rateUl">
											<li value="all">全部</li>
											<li value="0">买家未评</li>
											<li value="1">买家已评</li>
										</ul>
									</div>
								</div>
							</div>
							<div class="f_l h22 lh22  m_t45 m_l40 font16">交易成功时间：</div>
							<div class="h45 f_l  m_r25 m_t35 ">
								<div class=" f_l h45 lh45 w200 m_l10 ">
									<div class="nice-select h45 lh45 " name="nice-select"
										id="nice-select4" style="width: 11.416666vw;">
										<input class="bgc_f4f6fa border0 w240  h50 font12"
											name="bTime" value="${bTime}" id="tser01" readonly
											onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
									</div>
								</div>
							</div>
							
							<div class="f_l  w50 h45 lh45 text-center font16 m_l10 m_t30">~</div>
							<div class="h45 f_l  m_r25 m_t35 ">
								<div class=" f_l h45 lh45 w200 ">
									<div class="nice-select h45 lh45" name="nice-select"
										id="nice-select5" style="width: 11.416666vw;">
										<input class="bgc_f4f6fa border0 w240  h50 " name="eTime"
											value="${eTime}" type="text" id="tser02" readonly
											onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
									</div>
								</div>
							</div>
							<div class="f_l m_r15 m_l25 m_t45 h22 lh22 m_l25">客户昵称 :</div>
							<input class="bgc_f4f6fa border0 w170 h50 f_l m_r10 m_t35"
								name="buyerNick" value="${buyerNick}" />
						</form>
						<div onclick="formSubmit('formId')"
							class="f_l w80 h40 l_h40 text-center bgc_00a0e9 b_radius5 cursor_ c_fff m_l10 m_t45">
							查询</div>
						<div onclick="rateCheckOrder()"
							class="clbo m_b18 m_t18 f_l  w100 h40 l_h40 text-center bgc_00a0e9 b_radius5 cursor_ c_fff m_l80 tianjia_1">
							批量评价</div>
						<div onclick="rateAllOrder()"
							class=" m_t18 m_b18 f_l w155 h40 l_h40 text-center bgc_00a0e9 b_radius5 cursor_ c_fff m_l20 tianjia_1">
							一键评价全部订单</div>
						<div class="clbo c_384856 m_l80 font14 check_all">
							<table>
								<tr class="w1280 h54">
									<th class="position_relative z_1 w65 p_l10 ">
										<div class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
										    		<img class="cursor_ one_check_ display_none" src="images/black_check.png" />
										    		</div>

										    	<div class="m_t10 cursor_ all_check w20 h20 border_d9dde5 b_radius5 f_l">
								    				<img class="cursor_ check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
								    			</div>
									    		<div class="p_l20 m_t10">全选</div>
								    		</th>
										    <th class="w350">宝贝名称</th>
										    <th class="w160">订单编号</th>
										    <th class="w100">买家昵称</th>
										    <th class="w100">实付金额</th>
										    <th class="w160">成功交易时间</th>
										    <th class="w160">买家评价状态</th>
										    <th class="w160">评价剩余时间(小时)</th>
										    <th class="w120">操作</th>
										  </tr>
										  <!-- 获得当前时间 -->
										  <jsp:useBean id="nowDate" class="java.util.Date"></jsp:useBean>
										  <c:if test="${pagination != null}">
										  	<c:forEach items="${pagination.list}" var="orders">
										  	   <tr class="w1280 h54">
										     	<td class="position_relative p_l10 z_1 w65">
											    	<div class="m_t17 m_r10 cursor_ check w20 h20 border_d9dde5 b_radius5 f_l">
									    				<img class="cursor_ check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png"/>
									    				<input class="oid" type="hidden" value="${orders.oid}"/>
									    				<input class="tid" type="hidden" value="${orders.tid}"/>
									    			</div>
									    			<div></div>
								    			</td>
											    <td class="w350 lh50  c_384856 font16 p_l10">
											    	<img class="f_l m_t4 m_r15 cursor_ w50 h50" src="${orders.picPath}"/>
											    	<p class="one_line_only w200 h50 ">${orders.title}</p>
											    	<!-- <span>width:200px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis; border:1px solid red</span>
											    	<span>width:320px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;</span> -->

											    </td>
											    <td class="w160 text-center c_384856 font16">
											    	${orders.oid}
											    </td>
											    <td class="w100 text-center c_384856 font16">
											    	${orders.buyerNick}
											    </td>
											    <td class="w100 text-center c_384856 font16">
											    	${orders.payment}
											    </td>
											    <td class="w160 text-center font16 c_384856">
											    	<fmt:formatDate value="${orders.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
											    </td>
											    <td class="w160 text-center font16 c_384856">
											    	<c:if test="${orders.buyerRate == true}">

											    		买家已评
											    	</c:if> <c:if test="${orders.buyerRate == false}">
											    		买家未评
											    	</c:if></td>
											<td class="w160 text-center font16 c_384856"><fmt:formatDate
													var="rateOverDate" value="${orders.rateOverDate}"
													pattern="yyyy-MM-dd HH:mm:ss" /> <fmt:parseDate
													var="overDate" value="${rateOverDate}"
													pattern="yyyy-MM-dd HH:mm:ss" /> <c:set var="overTime"
													value="${overDate.time-nowDate.time}"></c:set> <c:choose>
													<c:when test="${overTime/1000/60/60/24 > 0.5}">
														<fmt:formatNumber value="${overTime/1000/60/60/24 - 0.5}"
															pattern="#0" />天
															<fmt:formatNumber value="${overTime/1000/60/60 % 24}"
															pattern="#0.00" />小时
														</c:when>
													<c:when test="${overTime/1000/60/60/24 > 0}">
														<fmt:formatNumber value="${overTime/1000/60/60/24}"
															pattern="#0" />天
															<fmt:formatNumber value="${overTime/1000/60/60 % 24}"
															pattern="#0.00" />小时
														</c:when>
													<c:otherwise>
															过期时间:<fmt:formatDate value="${orders.rateOverDate}"
															pattern="yyyy-MM-dd HH:mm:ss" />
													</c:otherwise>
												</c:choose></td>
											<td class="w120">
												<div onclick="findAllTemp('${orders.oid}')"
													class="cursor_ margin0_auto marketing_vip w70 h33 lh33 bgc_fff c_00a0e9 text-center border_00a0e9 b_radius5 tianjia_1">
													评价</div>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</table>
						</div>
						<!--------分页-------->
						<div class="w1380 h24 m_t22 font14 c_8493a8 m_b40">
							<div class="f_r w470 h24 l_h22 font12">
								<c:if test="${pagination.list.size() > 0}">
									<c:forEach items="${pagination.pageView}" var="page">
                                    		${page}
                                    	</c:forEach>
								</c:if>
							</div>
						</div>
					</div>
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
					<!----------下部---------->
					<div class="w1620 h675 bgc_fff p_t35 p_l40">

						<!---------------注意事项--------------->

						<!---------------授权--------------->

					</div>

				</div>

				<!---------------手机号设置--------------->

				<!---------------添加黑名单弹窗--------------->



				<!---------------导入黑名单弹窗--------------->

			</div>
		</div>

	</div>




	<div id="showTemp"
		class="w100_ h1000 rgba_000_5 position_fixed top0 display_none z_12 tianjia_2">
		<div class=" position_absolute top100 left320">
			<div class=" h718 bgc_f1f3f7 change_text "
				style="width: 60.5208333vw;">
				<img class=" f_r w40 h40 close_tianjia_2 cursor_"
					src="${ctx}/crm/images/chazi.png" />
				<div class="w1102 h50 text-center l_h50">选择评价模板</div>
				<div class=" h668 ">
					<div class="w495 h668 bgc_fff f_l">
						<div class="w495 h75">
							<div class="w495 h20 text-center p_t15 font14">编辑内容</div>
						</div>
						<div class="w458 h363 bgc_fff">
							<textarea maxlength="500"
								class="bgc_f4f6fa text_area border_d9dde5 b_radius5 w458 h363 m_l15"
								onkeyup="addLength();"></textarea>
							<input type="hidden">
							<div class="h30 w458 m_t_24 m_l20 c_bfbfbf"
								style="margin-top: -2.25vw;">
								已输入：<span id="inputNum" class="text_count">0</span>/500字

							</div>

						</div>
						<input id="resultId" type="hidden" value="good">
						<ul id="resultUl" class=" h22 lh22  m_t40 m_l20">
							<li class="h22 lh22 f_l m_r20">
								<div onclick="addResult('good')"
									class="pingjia cursor_ group_check_only w20 h20 border_d9dde5 b_radius5 f_l hao">
									<img class="cursor_ group_check_ w20 h20"
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
						<input id="oidId" type="hidden" class="oid_text"> <input
							id="tidId" type="hidden" class="tid_text">
						<div class="w495 h40 m_t60 ">
							<div onclick="rateOrder()"
								class="m_l180 w100 h40 border_00a0e9 c_00a0e9 close tk b_radius5 text-center f_l l_h40 cursor_ font14  m_l10 close_inside">
								确定</div>

						</div>
					</div>

					<div class="w595 h668 f_l bgc_fff m_l10 ">
						<div class="w595 h50 bgc_f1f3f7">
							<div id="anliId"
								class="w130 h50 text-center out bgc_fff l_h50 f_l cursor_ moban1 muban_all">
								案例模板</div>

							<div id="lishiId" onclick="findHistoryTemp()"
								class="w150 h50 text-center out l_h50 bgc_e3e7f0 f_l cursor_ moban1">
								历史使用</div>

						</div>

						<div class="in moban2">

							<div class="w560 h490 m_l15 m_t10 overflow_auto">
								<table id="smsTable" class="margin0_auto smsTemplate_table">
									<%-- <c:if test="${paginationTemp!=null}">
								<c:forEach items="${paginationTemp.list}" var="temp">
									<tr class=" h105 bgc_fafafa">
										<td class=" " style="width:20.833333vw;">${temp.content}</td>
										<td class="w160 ">
											<div onclick="addContent('${temp.id}'+','+'${temp.content}')" class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">
												<input type="hidden" value="${temp.id}">
												使用
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:if> --%>
								</table>
							</div>

							<div class="w560 h24 m_t22 font14 c_8493a8 m_b40">

								<%-- <div class="f_r w470 h24 l_h22 font12">
		                    <c:if test="${paginationTemp != null}">
		                    	<c:forEach items="${paginationTemp.pageView}" var="page">
		                    		${page}
		                    	</c:forEach>
		                    </c:if>
		                </div> --%>
							</div>

						</div>
						<div class="in display_none moban2">

							<div class="w560 h490 m_l15 m_t10 overflow_auto">
								<table id="historyTable">
								</table>
							</div>

							<div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">

								<div class="f_r w470 h24 l_h22 font12"></div>
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
	});
	$('#rateUl li').click(function(){
		$('#rateId').val($(this).attr("value"));
	});
	//全选全不选效果
	$('.all_check').click(function(){
		var cs = $('.all_check').children().attr("style");
		if(cs == null || cs=="display: none;"){
			$('.check_all').find('.check').children().hide();
		}else{
			$('.check_all').find('.check').children().show();
		}
	});
	//一键评价所有订单
	function rateAllOrder(){
		var content = $('.text_area').val();
		var dataOid;
		var dataTid;
		for(var i = 0;i<$('.check_all').find('.check').length;i++){
			if(dataOid != null){
				dataOid = dataOid + "," + $('.check_all').find('.check').eq(i).find('.oid').val();
			}else{
				dataOid = $('.check_all').find('.check').eq(i).find('.oid').val();
			}
			if(dataTid != null){
				dataTid = dataTid + "," + $('.check_all').find('.check').eq(i).find('.tid').val();
			}else{
				dataTid = $('.check_all').find('.check').eq(i).find('.tid').val();
			}
		}
		/* if($('.check_all').find('.check').length<1){
			location.reload();
		}else{ */
			$('#oidId').val(dataOid);
			$('#tidId').val(dataTid);
			findAllTemp();
		/* } */
		
		/* var url = "${ctx}/crms/rateOrder";
		$.post(url,{"oids":dataOid,"tids":dataTid,"content":content},function(data){
			alert(data.message);
		},"json") */
	};
	//评价选中订单
	function rateCheckOrder(){
		var content = $('.text_area').val();
		var dataOid;
		var dataTid;
		for(var i = 0;i<$('.check_all').find('.check').length;i++){
			if($('.check_all').find('.check').eq(i).find('img').attr('style') == "display: inline;"){
				if(dataOid != null){
					dataOid = dataOid + "," + $('.check_all').find('.check').eq(i).find('.oid').val();
				}else{
					dataOid = $('.check_all').find('.check').eq(i).find('.oid').val();
				}
				if(dataTid != null){
					dataTid = dataTid + "," + $('.check_all').find('.check').eq(i).find('.tid').val();
				}else{
					dataTid = $('.check_all').find('.check').eq(i).find('.tid').val();
				}
			}
		}
		/* if($('.check_all').find('.check').length<1){
			location.reload();
		} */
		$('#oidId').val(dataOid);
		$('#tidId').val(dataTid);
		findAllTemp();
		/* var url = "${ctx}/crms/rateOrder";
		$.post(url,{"oids":dataOid,"tids":dataTid,"content":content},function(data){
			alert(data.message);
		},"json") */
	};
	//点击快速筛选将条件添加到INPUT框中
	function addScreen(num){
		window.location.href="${ctx}/crms/batchReview?screen="+num;
	}
																						
	//批量评价表单提交
	function formSubmit(formId){
		document.getElementById(formId).submit();
	}
	//页面回显快速筛选勾选框
	$(function(){
		if($('#show_1').val()==1){	
			$('#showTemp').removeClass("display_none");
		}
		var num = $('#screenId').val();
		if(num == 0){
			$('.quanbu').children().removeClass("display_none");
		}else if(num == 1){
			$('.quanbu1').children().removeClass("display_none");
		}else if(num == 2){
			$('.quanbu2').children().removeClass("display_none");
		}else if(num == 3){
			$('.quanbu3').children().removeClass("display_none");
		}
		if($('#show_2').val()==1){
			$('#showTemp').removeClass("display_none");
			$('#anliId').removeClass("bgc_fff").addClass("bgc_e3e7f0");
			$('#lishiId').addClass("bgc_fff").removeClass("bgc_e3e7f0");
		}
		
		//买家评价条件回显
		var rateVal = $('#rateId').val();
		if(rateVal == 'all'){
			$('#rate').val("全部");
		}else if(rateVal == '0'){
			$('#rate').val("买家未评");
		}else if(rateVal == '1'){
			$('#rate').val("买家已评");
		}
	});
	//评价模板内容框字数的改变
	function addLength(){
		var length = $('.text_area').val().length;
		document.getElementById('inputNum').innerText = length;
	}
	//点击评价按钮查询出评价模板
	function findAllTemp(orderId){
		$('#smsTable').empty();
		var oids = $('.check_all').find('.check').children('.oid').val();
		var tids = $('.check_all').find('.check').children('.tid').val();
		$('#oidId').val(oids);
		$('#tidId').val(tids);
		$('.text_area').next().val(orderId);
		var url = "${ctx}/crms/rateTemplate";
		$.post(url,{"type":"评价模板"},function(data){
			if(data.smsTemplate != null){
				var smsTemplate = data.smsTemplate;
				var appendTemp = "";
				$.each(smsTemplate,function(i,result){
					appendTemp = "<tr class='h105 bgc_fafafa'><td class='w400'>"+result.content+"</td><td class='w160 '><div onclick='addContent("+result.id+")' id='click_use"+result.id+"' class='w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9'>使用<input id='content' type='hidden' value='"+result.content+"'/></div></td></tr>";
					$('#smsTable').append(appendTemp);
				});
			}else{
				$('#smsTable').append("暂时没有相关数据！");
			}
		});
	}
	//添加模板到文本域
	function addContent(id){
		$('.text_area').val('');
		var click_use = $("#click_use"+id);
		var sms= click_use.children().val();
		$('.text_area').val(sms);
		addLength();
		var type = $('#resultId').val();
		$.ajax({
			url:"${ctx}/crms/addHistoryTemp",
			data:{"type":type,"id":id},
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.message){ 
					/* $(".tishi_2").show();
					$(".tishi_2").children("p").text("success")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000) */
				}
			}
		});
	}
	//历史记录
	function findHistoryTemp(){
		$('#historyTable').empty();
		var url = "${ctx}/crms/rateTemplate";
		$.post(url,{"type":"历史使用"},function(data){
			var historyTemp = data.historyTemp;
			var appendTemp = "";
			$.each(historyTemp,function(i,result){
				appendTemp = "<tr class='h105 bgc_fafafa'><td class='w400'>"+result.content+"</td><td class='w160 '><div onclick='addContent("+result.id+")' id='click_use"+result.id+"' class='w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9'>使用<input id='content' type='hidden' value='"+result.content+"'/></div></td></tr>";
				$('#historyTable').append(appendTemp);
			});
		});
	};
	//选择评价模板弹出框选择评价类型
	function addResult(result){
		var resultVal = $('#resultId').val();
		if(resultVal == result){
			$('#resultId').val('');
		}else{
			$('#resultId').val(result);
		}
	}
	
	//点击确定按钮评价该订单
	function rateOrder(){
		var content = $('.text_area').val();
		var oids = $('#oidId').val();
		var tids = $('#tidId').val();
		var result = $('#resultId').val();
		var oidsList = oids.split(',');
		var url = "${ctx}/crms/rateOrder";
		if(oids == null || oids == ''){     
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请选择要评价的订单")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)    
			window.location.reload();
		}
		if(oidsList.length > 1){
			$.post(url,{"oids":oids,"tids":tids,"content":content,"result":result},function(data){

				$(".tishi_2").show();
				$(".tishi_2").children("p").text(data.message)
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
			},"json")
		}else{
			$.post(url,{"oids":oids,"content":content,"tids":tids,"result":result},function(data){
				if(data != null){

					$(".tishi_2").show();
					$(".tishi_2").children("p").text(data.message)
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
				}else{
					window.location.reload();
				}
			},"json");
		}
	}
</script>
</html>
