<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>中差评查看</title>
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
<script type="text/javascript" src="${ctx}/crm/js/zhongchaping.js"></script>
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
			<div class="w1660 m_t70 m_l30 ">
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
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block" href="${ctx}/crms/reviewCare">
									评价关怀 </a></li>
							<li class="w140 bgc_fff text-center bgc_f1f3f7 f_l"><a
								class="c_384856 display_block"
								href="${ctx}/appraiseAmend/showAppraiseAmend"> 中差评管理 </a></li>
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block"
								href="${ctx}/OrderReminder/jumOrderReminder"> 手动订单提醒 </a></li>
							<li style="clear:both;"></li>
						</ul>
					</div>
				</div>


				<div class="w1660  bgc_f1f3f7 c_384856" >
					<div class="p_l40 p_t27">
						<p class="font20" id="tit">中差评查看</p>
						<p class="font14 p_t8">通过短信与买家进行交流,修改中差评,大大降低修改中差评的成本！</p>
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
					<ul class="h40 p_t40 font14 p_l40">
						<li class="f_l w140 text-center  l_h40 bgc_fff  cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/appraiseAmend/showAppraiseAmend">中差评查看</a></li>
						<li class="f_l w140 text-center  l_h40 bgc_e3e7f0 ping_1 cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/appraiseMonitoring/showAppraiseMonitoring">中差评监控</a></li>
						<li class="f_l w140 text-center  l_h40 bgc_e3e7f0 ping_1 cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/appraisePacify/showAppraisePacify">中差评安抚</a></li>
						<%-- <li class="f_l w140 text-center  l_h40 bgc_e3e7f0 ping_1 cursor_"><a class="c_384856 display_block"  href="${ctx}/crms/appraise/middleBadAssessment?filtrateTime="+filtrateTime>中差评统计</a></li> --%>
						<li class="f_l w140 text-center  l_h40 bgc_e3e7f0 ping_1 cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/crms/appraise/middleBadAssessment?filtrateTime=最近7天">中差评统计</a></li>
						<%-- <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 ping_1 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseSet/showAppraiseCauseSet">中差评原因设置</a></li> --%>
						<%-- <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 ping_1 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseAnalyze/showAppraiseCauseAnalyze">中差评原因分析</a></li> --%>
					</ul>

				</div>
				<!-- 中差评修改  -->
				<div class="ping_2 ">
					<div class="w1660 bgc_fff h1000 ">
						<div class="w1380 h900 p_l70 font14">
							<div class=" w1290  h40 p_t20 l_h40">
								<span class="f_l">快速筛选：</span> <input type="hidden"
									id="filtrateTime" name="filtrateTime" value="${filtrateTime }" />
								<div id="sx0" onclick="addFiltrateTime('180天内');"
									class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
									<img class="cursor_ one_check_ display_none m_b10 w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="f_l m_r15 l_h20">180天内</div>

								<div id="sx1" onclick="addFiltrateTime('30天内');"
									class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
									<img class="cursor_ one_check_ display_none m_b10 w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="f_l m_r15 l_h20">30天内</div>
								<div id="sx2" onclick="addFiltrateTime('今日新增');"
									class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
									<img class="cursor_ one_check_ display_none m_b10 w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="f_l m_r15 l_h20">今日新增</div>
								<div id="sx3" onclick="addFiltrateTime('近三天新增');"
									class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
									<img class="cursor_ one_check_ display_none m_b10 w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="f_l m_r15 l_h20">近三天新增</div>
								<div id="sx4" onclick="addFiltrateTime('一天后过期');"
									class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
									<img class="cursor_ one_check_ display_none m_b10 w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="f_l m_r15 l_h20">一天后过期</div>
								<div id="sx5" onclick="addFiltrateTime('三天后过期');"
									class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
									<img class="cursor_ one_check_ display_none m_b10 w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="f_l m_r15 l_h20">三天后过期</div>
								<div id="sx6" onclick="addFiltrateTime('已过期');"
									class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
									<img class="cursor_ one_check_ display_none m_b10 w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="f_l m_r15 l_h20">已过期</div>
							</div>
							<div class=" w1290  h40 p_t20 l_h40">
								<span class="f_l">评价类型：</span> <input type="hidden"
									id="appraiseType" name="result" value="${result }" /> <input
									type="hidden" id="flag" value="${flag }" />
								<div id="lx0" onclick="addAppraiseType('all');"
									class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
									<img class="cursor_ one_check_ display_none m_b10 w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="f_l m_r35 l_h20">全部</div>

								<div id="lx1" onclick="addAppraiseType('neutral');"
									class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
									<img class="cursor_ one_check_ display_none m_b10 w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="f_l m_r35 l_h20">中评</div>
								<div id="lx2" onclick="addAppraiseType('bad');"
									class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
									<img class="cursor_ one_check_ display_none m_b10 w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="f_l m_r35 l_h20">差评</div>

							</div>


							<form id="queryForm"
								action="${ctx}/appraiseAmend/showAppraiseAmend" method="post">
								<div class="w1380 h50 p_t20">
									<div class="f_l m_r15 l_h40">订单查询:</div>
									<input
										class="bgc_f4f6fa border_d9dde5 b_radius5 w390 h40 f_l m_r35"
										name="orderId" value="${orderId }"
										placeholder="订单编号、买家昵称、商品标题" />
									<div class="f_l m_r15 l_h40">评价时间:</div>

									<div class="f_l position_relative">
										<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10"
											type="text" id="tser01" name="bTime" value="${bTime }"
											readonly="true"
											onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
										<img style="width: 1vw;"
											class="position_absolute right20 top15"
											src="${ctx}/crm/images/date_copy.png" />
									</div>
									<div class="f_l l_h40">~</div>
									<div class="f_l position_relative">
										<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40"
											type="text" id="tser02" name="eTime" value="${eTime }"
											readonly="true"
											onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
										<img style="width: 1vw;"
											class="position_absolute right50 top15"
											src="${ctx}/crm/images/date_copy.png" />
									</div>

									<div onclick="submitForm('queryForm');"
										class="w100 h40 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_ l_h40 m_r40">
										查询</div>
								</div>
								<div class="w810 h50 p_t20 ">
									<input type="hidden" name="result" id="result" value="${flag}" />
									<!-- <div class="f_l">
										<div id="zcp0" onclick="appraiseSelectAll();"
											class="  w100 h40 border_d9dde5 b_radius5 text-center f_l l_h40 c_fff cursor_ bgc_00a0e9">
											中差评</div>
										<div id="zcp1" onclick="appraiseSelectGood('good');"
											class="  w100 h40 border_d9dde5 b_radius5  text-center f_l l_h40 c_8493a8 cursor_">
											已改好评</div>
										<div id="zcp2" onclick="appraiseSelectGood('iSDelete');"
											class="  w100 h40 border_d9dde5 b_radius5  text-center f_l l_h40 c_8493a8 m_r600 cursor_">
											已删中差评</div>
									</div> -->
									<div class="f_r m_r100">
										<div onclick="sendSms();"
											class="w100 h40 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_ l_h40 m_r40 fasong_1 position_relative">
											批量发短信</div>
										<div onclick="appraiseSelectAll();"
											class="w100 h40 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_ l_h40">
											更新数据</div>
									</div>
								</div>
							</form>

							<!--中差评-->
							<div class="xiugai_2 check_all">

								<div class="w1290 m_t20 font14">
									<table>
										<tr class="h55">
											<th class="position_relative z_1 w78 p_l10 ">
												<div
													class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div class="p_l20">全选</div>
											</th>
											<th class="w535">商品标题</th>
											<th class="w110">评论信息</th>
											<th class="w165">评论人</th>
											<th class="w200">处理跟踪</th>
											<th class="w193">备注</th>
										</tr>
										<c:if test="${pagination.list.size() ==0}">
											<tr>
												<td class="w75 text-center" align="center" colspan="6">暂时没有相关数据!</td>
											</tr>
										</c:if>
										<c:if test="${pagination != null}">
											<c:forEach items="${pagination.list}" var="tradeRates">
												<tr class="h55 text-center">
													<td class="position_relative p_l10 z_1 w78">
														<div
															class="cursor_ one_check_only1 w20 h20 border_d9dde5 b_radius5 position_absolute top15 check">
															<img
																class="cursor_ one_check_ display_none check_img w20 h20"
																src="${ctx}/crm/images/black_check.png" /> <input
																id="input" type="hidden" value="${tradeRates.oid }">
														</div>
														<div></div>
													</td>
													<td class="w535 text-center">${tradeRates.itemTitle}</td>
													<td class="w110 text-center">
														<div style="word-break: break-all;" class="w100 margin0_auto h50 lh50 one_line_only text_detail">
																${tradeRates.content}
														</div>
													</td>
													<td class="w165 text-center">${tradeRates.nick}</td>
													<td class="w200 text-center"><c:if
															test="${tradeRates.result=='good'}">
											    好评
											    </c:if> <c:if test="${tradeRates.result=='neutral'}">
											    中评
											    </c:if> <c:if test="${tradeRates.result=='bad'}">
											    差评
											    </c:if> <c:if test="${tradeRates.result=='iSDelete'}">
											    已删中差评
											    </c:if></td>
													<td class="w193 text-center">
													<c:if test="${tradeRates.reply==null}">此评论无备注信息</c:if>
													<c:if test="${tradeRates.reply!=null}">${tradeRates.reply}</c:if>
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</table>
								</div>
								<div class="w1160 h24 m_t20 font14 c_8493a8 m_b40 ">
									<!-- <div class="f_r w470 h24 l_h22 font12"> -->
									<c:if test="${pagination != null}">
										<c:forEach items="${pagination.pageView}" var="page">
	                                    		${page}
	                                    	</c:forEach>
									</c:if>
									<!-- </div> -->
								</div>
							</div>

							<!--  已改好评  -->
							<div class="xiugai_2 display_none">

								<div class="w1290 m_t20 font14">
									<table>
										<tr class="h55">
											<th class="position_relative z_1 w78 p_l10 ">
												<div
													class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div class="p_l20">全选</div>
											</th>
											<th class="w535">宝贝信息</th>
											<th class="w110">评论信息</th>
											<th class="w165">评论人</th>
											<th class="w200">处理跟踪</th>
											<th class="w193">备注</th>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
									</table>
								</div>
								<div class="w1160 h24 m_t20 font14 c_8493a8 m_b40 ">
									<div class="f_l w220 h24 l_h24">共0条记录，共40页，当前为第1页</div>
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
											<input
												class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
												type="text" value="">
										</div>
										<div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
									</div>
								</div>

							</div>
							<!--  已收中差评  -->
							<div class="xiugai_2 display_none">

								<div class="w1290 m_t20 font14">
									<table>
										<tr class="h55">
											<th class="position_relative z_1 w78 p_l10 ">
												<div
													class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div class="p_l20">全选</div>
											</th>
											<th class="w535">宝贝信息</th>
											<th class="w110">评论信息</th>
											<th class="w165">评论人</th>
											<th class="w200">处理跟踪</th>
											<th class="w193">备注</th>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110">就感觉</td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
										<tr class="h55">
											<td class="position_relative p_l10 z_1 w78">
												<div
													class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
													<img class="cursor_ one_check_ display_none w20 h20"
														src="${ctx}/crm/images/black_check.png" />
												</div>
												<div></div>
											</td>
											<td class="w535"></td>
											<td class="w110"></td>
											<td class="w165"></td>
											<td class="w200"></td>
											<td class="w193"></td>
										</tr>
									</table>
								</div>
								<div class="w1160 h24 m_t20 font14 c_8493a8 m_b40 ">
									<div class="f_l w220 h24 l_h24">共0条记录，共40页，当前为第1页</div>
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
											<input
												class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
												type="text" value="">
										</div>
										<div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>








			</div>



			<div
				class="w100_ h1000 rgba_000_5 position_fixed top0 display_none z_10 duanxin_2">

				<div class=" position_absolute top100 left100">
					<div class="w1102 h718 bgc_f1f3f7 change_text ">
						<img class=" f_r w40 h40 close_duanxin_2 cursor_ w20 h20"
							src="${ctx}/crm/images/chazi.png" />
						<div class="w1102 h50 text-center l_h50">选择短信模板</div>
						<div class="w1102 h668 ">
							<div class="w495 h668 bgc_fff f_l">
								<div class="w495 h75">
									<div class="w495 h20 text-center p_t15 font14">编辑内容</div>
									<div class="w495 h20 c_red font14 text-center">若含有促销词如:微信、QQ、店庆等,请务必在短信内容末尾添加“退订回N、td”</div>
								</div>
								<div class="w458 h363 bgc_fff">
									<textarea maxlength="500"
										class="bgc_f4f6fa text_area border_d9dde5 b_radius5 w458 h363 m_l15"></textarea>
									<div class="h30 w458 m_t_24 m_l20 c_bfbfbf">
										已输入：<span class="text_count">0</span>/500字 <a
											href="${ctx }/crms/home/notice#duanxinxiangguan"
											class="c_00a0e9 cursor_  m_l10 guize1" target="_blank">计费规则</a>
									</div>

									<div
										class="w350 h45 bgc_fff position_absolute left45 bottom250 guize2 display_none">
										<p class="font12">1、单条70个字，超出70个字将按照67个字每条计算</p>
										<p class="font12">2、一个汉字、数字、字母、空格都算一个字</p>
										<p class="font12">3、带标签的短信按实际发出的长度计算</p>
									</div>











								</div>
								<div class="w495 h40 m_t10 m_l20">
									<span class="f_l l_h40">插入标签：</span>
									<div
										class="w100 h40 b_radius5 text-center f_l l_h40 border_00a0e9 c_00a0e9 tk cursor_ font14  m_l10">
										收货人</div>
									<div
										class="w100 h40 b_radius5 text-center f_l l_h40 border_00a0e9 c_00a0e9 tk cursor_ font14 m_l10 tishi_1 ">
										订单编号</div>
									<div
										class="w100 h40 b_radius5 text-center f_l l_h40 border_00a0e9 c_00a0e9 tk cursor_ font14 m_l10">
										买家昵称</div>
								</div>
								<div class="w495 h2 m_t10 m_l20">
									<div class="w495 h20 c_red font14 ">提示:
										链接前后须有空格分隔，请勿删除前后空格。</div>
								</div>
								<div class="w495 h40 m_t10 m_l20">
									<span class="f_l l_h40 m_r15 m_l20">签名: </span> <input
										class="bgc_f4f6fa border_d9dde5 b_radius5 w211 h40 f_l" />
								</div>
								<div class="w495 h40 m_t20  ">
									<div
										class="m_l100 w100 h40 border_00a0e9 c_00a0e9 close tk b_radius5 text-center f_l l_h40 cursor_ font14  m_l10">
										确定</div>
									<div
										class=" m_l50 w100 h40 border_00a0e9 c_00a0e9 tk b_radius5 text-center f_l l_h40 cursor_ font14  m_l10 sure_1">
										加入我的模板</div>
								</div>
							</div>

							<div class="w595 h668 f_l bgc_fff m_l10 ">
								<div class="w590 h50 bgc_f1f3f7">
									<div
										class="w130 h50 text-center out bgc_fff l_h50 f_l cursor_ moban1">
										案例模板</div>
									<div
										class="w130 h50 text-center out l_h50 bgc_e3e7f0 f_l cursor_ moban1">
										我的模板</div>
									<div
										class="w150 h50 text-center out l_h50 bgc_e3e7f0 f_l cursor_ moban1">
										历史使用</div>

								</div>

								<div class="in">

									<div class="w560 h490 m_l15 m_t10">
										<table>
											<tr class=" h105 bgc_fafafa">
												<td class="w400 "></td>
												<td class="w160 ">
													<div
														class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
												</td>
											</tr>
											<tr class="h85 bgc_f4f4f4">
												<td class="w400 "></td>
												<td class="w160">
													<div
														class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
												</td>
											</tr>
											<tr class="h75 bgc_fafafa">
												<td class="w400 "></td>
												<td class="w160 "></td>
											</tr>
											<tr class="h115 bgc_f4f4f4">
												<td class="w400 "></td>
												<td class="w160 "></td>
											</tr>
											<tr class="h105 bgc_fafafa">
												<td class="w400 "></td>
												<td class="w160 "></td>
											</tr>
										</table>
									</div>

									<div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">

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
												<input
													class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
													type="text" value="">
											</div>
											<div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
										</div>
									</div>

								</div>


								<div class="in display_none">

									<div class="w560 h490 m_l15 m_t10">
										<table>
											<tr class=" h105 bgc_fafafa">
												<td class="w400 "></td>
												<td class="w160 ">
													<div
														class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
												</td>
											</tr>
											<tr class="h85 bgc_f4f4f4">
												<td class="w400 "></td>
												<td class="w160">
													<div
														class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
												</td>
											</tr>
											<tr class="h75 bgc_fafafa">
												<td class="w400 "></td>
												<td class="w160 "></td>
											</tr>
											<tr class="h115 bgc_f4f4f4">
												<td class="w400 "></td>
												<td class="w160 "></td>
											</tr>
											<tr class="h105 bgc_fafafa">
												<td class="w400 "></td>
												<td class="w160 "></td>
											</tr>
										</table>
									</div>

									<div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">

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
												<input
													class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
													type="text" value="">
											</div>
											<div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
										</div>
									</div>

								</div>


								<div class="in display_none">

									<div class="w560 h490 m_l15 m_t10">
										<table>
											<tr class=" h105 bgc_fafafa">
												<td class="w400 "></td>
												<td class="w160 ">
													<div
														class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
												</td>
											</tr>
											<tr class="h85 bgc_f4f4f4">
												<td class="w400 "></td>
												<td class="w160">
													<div
														class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
												</td>
											</tr>
											<tr class="h75 bgc_fafafa">
												<td class="w400 "></td>
												<td class="w160 "></td>
											</tr>
											<tr class="h115 bgc_f4f4f4">
												<td class="w400 "></td>
												<td class="w160 "></td>
											</tr>
											<tr class="h105 bgc_fafafa">
												<td class="w400 "></td>
												<td class="w160 "></td>
											</tr>
										</table>
									</div>

									<div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">

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
												<input
													class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
													type="text" value="">
											</div>
											<div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
										</div>
									</div>

								</div>


							</div>
						</div>
					</div>



				</div>






			</div>



			<div
				class="z_10 w100_ h1200 rgba_000_5 position_fixed top0 fasong_2 display_none">
				<div
					class="w1390 h720 bgc_f1f3f7 position_fixed top100  left230  position_fixed">

					<img class=" f_r w40 h40 close_fasong_2 cursor_"
						src="${ctx}/crm/images/chazi.png" />
					<div class="font16 c_38485 text-center h50 lh50">选择短信模板</div>
					<div>
						<div class="w535 f_l h665 bgc_fff">
							<p class="c_384856 text-center font16 m_b10 m_t25">编辑内容</p>
							<!-- <p class="c_ff6363 text-center font14 m_b10">
											若含有促销词如:微信、QQ、店庆等,请务必在短信内容末尾添加“退订回N”
										</p> -->
							<div
								class="margin0_auto w500 h300 b_radius5 bgc_f4f6fa position_relative">
								<input type="hidden" id="phones" /> <input type="hidden"
									id="orders" /> <input type="hidden" id="nickname" />
								<textarea onkeyup="addLength()" id="send_sms_text"
									maxlength="500"
									class="text_area text_area_sms w480 p_b40 h250 border0 bgc_f4f6fa b_radius5 p_l10 p_r10 p_t10"></textarea>
								<div class="w480 c_cecece p_l20 h40 lh40 b_radius5 position_absolute bottom0">
									<div class="f_l w380 h40">
										已输入：<span id="inputNum" class="text_count">0</span> 当前计费：<span id="contePrice">0</span>
										条 <a id="JFGZ" class="c_00a0e9 cursor_" target="_blank">计费规则</a>
									</div>
									<div class="f_l">
										退订回
	                        			<input maxlength="2" id="unsubscribeMSG" class="w20 h20 l_h20 border_0 bgc_f4f6fa" placeholder="N" value="N" readonly/>
									</div>
								</div>
							</div>
							<p class="c_ff6363 font14 m_t20 p_l25">
								提示：链接前后须有空格分隔，请勿删除前后空格。</p>
							<div id="smsAutograph"
								style="margin-left: 1.3vw; margin-top: 1vw;">
								短信签名：<input style="width: 10vw;"
											class="h50  bgc_f4f6fa p_l15 outline_none border0 shopName sms_sign"
											disabled="disabled" type="text" value="${ShopName}"
											onkeyup="updateShopName(this.value,'send_sms_text');" /> <img
											src="${ctx }/crm/images/bianji.png"
											style="width: 1.2vw; margin-left: 0.5vw; cursor: pointer;"
											onclick="updateDisabled();">
							</div>

							<div class="h40 m_t15 m_l15">
								<%-- <div class="f_l lh40 c_384856">
												发送时间：
											</div>
											<div class="f_l">
												<div class="f_l m_r20 set_time_none">
											    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
											    	</div>
											    	<div class="m_l10 f_l font14 c_384856 lh40">
											    		立即发送
											    	</div>
												</div>
												<div class="f_l set_time">
											    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
											    	</div>
											    	<div class="m_l10 f_l font14 c_384856 lh40">
											    		定时发送
											    	</div>
												</div>
												<div class="f_l position_relative m_l10 set_time_ display_none">
													<input class="bgc_f1f3f7 b_radius5 border0 w200 p_l10 h40 m_r10" type="text" id="tser20" placeholder="请选择时间"  readonly onclick="$.jeDate('#tser20',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
													<img class="position_absolute right20 top12" src="${ctx}/crm/images/date_copy.png" />
												</div>
											</div> --%>
							</div>

							<div class="w232 h40 margin0_auto text-center font16 m_b25 m_t45">
								<div onclick="confirmSend()"
									class="f_l b_radius5 m_r30 w100 lh40 bgc_00a0e9 close_this c_fff cursor_ close_inside1">
									确定发送</div>
								<div
									class="f_l b_radius5 w100 h38 lh38 border_cad3df close_this c_cad3df cursor_ close_inside">
									取消</div>

							</div>




						</div>
						<div class="w840 f_l m_l15 h665 bgc_fff">
							<div
								class="lh50 w100_ h50 bgc_e3e7f0 text-center font16 c_384856">
								<ul class="window_out">
									<li onclick="template('中评')"
										class="w280 h50 f_l cursor_ bgc_fff">中评</li>

									<li onclick="template('差评')" class="w280 h50 f_l cursor_">
										差评</li>

									<li onclick="HistoryTemplate('中差评监控')"
										class="w280 h50 f_l cursor_">历史记录</li>
								</ul>
							</div>

							<!--------详细数据-------->
							<div class="c_384856 font14 m_t15 window_in overflow_auto "
								style="height: 26.041666vw">
								<table class="margin0_auto smsTemplate_table">

									<!-- <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100">
											    	<div class="w100 margin0_auto b_radius5 h38 lh38 border_00a0e9 c_00a0e9 tk">
											    		使用
											    	</div>
											    </td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100">
											    	<div class="w100 margin0_auto b_radius5 h38 lh38 border_00a0e9 c_00a0e9 tk">
											    		使用
											    	</div>
											    </td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100"></td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100"></td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100"></td>
											  </tr> -->
								</table>
							</div>

							<!--------详细数据-------->
							<!-- <div class="c_384856 font14 m_t15 window_in display_none">
											<table class="margin0_auto">
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100">
											    	<div class="w100 margin0_auto b_radius5 h38 lh38 border_00a0e9 c_00a0e9 tk">
											    		使用
											    	</div>
											    </td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100">
											    	<div class="w100 margin0_auto b_radius5 h38 lh38 border_00a0e9 c_00a0e9 tk">
											    		使用
											    	</div>
											    </td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100"></td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100"></td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100"></td>
											  </tr>
											</table>
										</div> -->

							<!--------详细数据-------->
							<!-- <div class="c_384856 font14 m_t15 window_in display_none">
											<table class="margin0_auto">
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100">
											    	<div class="w100 margin0_auto b_radius5 h38 lh38 border_00a0e9 c_00a0e9 tk">
											    		使用
											    	</div>
											    </td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100">
											    	<div class="w100 margin0_auto b_radius5 h38 lh38 border_00a0e9 c_00a0e9 tk">
											    		使用
											    	</div>
											    </td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100"></td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100"></td>
											  </tr>
											  <tr class=" text-center">
											    <td class="w660 h100"></td>
											    <td class="w145 h100"></td>
											  </tr>
											</table>
										</div> -->





							<!-- 
							            <div class="h24 m_t22 font14 c_8493a8 m_b40 p_r15">
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





						</div>
					</div>

				</div>
			</div>
			<input type="hidden" name="templateId" id="templateId" />
</body>
<script>
	
	//条件数据查询form表单
	function submitForm(fomrId){
		document.getElementById(fomrId).submit();
	}
	
	//设置快速筛选条件
	function addFiltrateTime(data){
		$('#filtrateTime').val(data);
		
		var filtrateTime = $('#filtrateTime').val();
		var result = $('#appraiseType').val();
		var flag = $('#flag').val();
		window.location.href="${ctx}/appraiseAmend/queryAppraiseAmendClick?filtrateTime="+filtrateTime+"&result="+result+"&flag="+flag;
	}
	//设置评价类型条件
	function addAppraiseType(data){
		$('#appraiseType').val(data);
		var filtrateTime = $('#filtrateTime').val();
		var result = $('#appraiseType').val();
		var flag = $('#flag').val();
		window.location.href="${ctx}/appraiseAmend/queryAppraiseAmendClick?filtrateTime="+filtrateTime+"&result="+result+"&flag="+flag;
	}
	
	
	
	//点击中差评选项卡==========
	//1.点击"中差评"
 	function appraiseSelectAll(){
 		window.location.href="${ctx}/appraiseAmend/showAppraiseAmend";
	}
	
	//2.点击"已改好评"
	function appraiseSelectGood(result){
		window.location.href="${ctx}/appraiseAmend/showAppraiseAmend?result="+result;
	}
	
	//3.点击"已删中差评"
	function appraiseSelect(data){
		$('#appraiseSelect').val(data);
	}
	//获取选项卡返回参数显示数据
	$(function(){
		var result = $('#flag').val();
		if(result=='good'){
			$("#zcp1").addClass("bgc_00a0e9 c_fff").removeClass("c_8493a8");
			$("#zcp1").siblings().removeClass("bgc_00a0e9").addClass("c_8493a8");
		}
		if(result=='iSDelete'){
			$("#zcp2").addClass("bgc_00a0e9 c_fff").removeClass("c_8493a8");
			$("#zcp2").siblings().removeClass("bgc_00a0e9").addClass("c_8493a8");
		}
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
	
		//批量发短信
		 function sendSms(){
			$('.text_area_sms').val("【${ShopName}】");
			$('.sms_sign').val("${ShopName}");
			var orderIds="";
				var divCheck = $(".check");
				for(var i=0;i<divCheck.length;i++){
					var checkImg = divCheck.eq(i).find('.check_img').attr("style");
					if(checkImg == "display: inline;"){
						var val = divCheck.eq(i).find('#input').val();
						orderIds+=val+","
					}
				}
				orderIds=orderIds.substring(0,orderIds.length-1);
				$("#orders").val(orderIds);
				if(orderIds!=""){
				    //调用ajax通过订单id获取电话号码
				    var url = "${ctx}/appraiseAmend/queryPhoneByOrderIds";
					$.post(url,{"orderIds":orderIds},function (data) {
						$("#phones").val(data.phone);
						$("#nickname").val(data.nickname)
					});
					template('中评');
					$(".fasong_2").show();
				}else{
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("请选择数据!!!")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
					$(".fasong_2").hide();
					$("#phones").val('');  
					$("#orders").val('');
				}
				
				addLength();
		}
		
		//选择短信模板点击事件查询短信数据,中评与 差评
			function template(smsType){
				$('.smsTemplate_table tr').remove();
			 	//调用ajax查询短信数据
		    	var url = "${ctx}/appraiseAmend/querySmsTemplate";
				$.post(url,{"smsType":smsType},function (data) {
					var smsTemplate  = data.smsTemplate;
					console.log(data);
					var table_tr;
					if(smsTemplate ==null || smsTemplate=="undefined" || smsTemplate=='' ){
						table_tr = "<tr><td class='w150 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
						$('.smsTemplate_table').append(table_tr);
					}else{
						$.each(smsTemplate,function(i,result){
							table_tr = "<tr class='text-center'><td class='w660 h100'>"+result.content+"</td><td class='w145 h100'><div onclick='click_use("+result.id+");' id='click_use"+result.id+"' class='w100 margin0_auto b_radius5 h38 lh38 border_00a0e9 c_00a0e9 tk cursor_'>使用<input id='content' type='hidden' value='"+result.content+"'/></div></td></tr>";
							$('.smsTemplate_table').append(table_tr);
						});
					}
				}); 
			}
		
		//查询短信模板--历史使用
		function HistoryTemplate(type){
			$('.smsTemplate_table tr').remove();
			 	//调用ajax查询短信数据
			var url = "${ctx}/historyTemplate/findAllList";
			$.post(url,{"type":"中差评查看"},function (data) {
				var smsTemplate  = data;
				var table_tr;
				if(smsTemplate ==null || smsTemplate=="undefined" || smsTemplate=='' ){
					table_tr = "<tr><td class='w150 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
					$('.smsTemplate_table').append(table_tr);
				}else{
					$.each(smsTemplate,function(i,result){
						table_tr = "<tr class='text-center'><td class='w660 h100'>"+result.content+"</td><td class='w145 h100'><div onclick='click_use("+result.id+");' id='click_use"+result.id+"' class='w100 margin0_auto b_radius5 h38 lh38 border_00a0e9 c_00a0e9 tk cursor_'>使用<input id='content' type='hidden' value='"+result.content+"'/></div></td></tr>";
						$('.smsTemplate_table').append(table_tr);
					});
				}
			}); 
		}
		
		
			//评价模板中点击使用将内容添加到textarea中<input id='id' type='text' value='"+result.id+"'/>
			function click_use(id){
				$('.text_area_sms').val('');
				var smsSign = $(".sms_sign").val();
				if(smsSign !=null && smsSign !=""){
					smsSign = "【"+smsSign+"】";
				}
				var click_use = $("#click_use"+id);
				 var sms= click_use.children().val();
				 $('.text_area_sms').val(smsSign+sms);
				 $("#templateId").val(id);
				 addLength();
			}
			
			
			//评价模板内容框字数的改变
			function addLength(){
				var content = $('.text_area_sms').val();
				var length;
				if(content != "" && content !=null){
					length = $('.text_area_sms').val().length+4;
				}else{
					length = 0;
				}
				
				if(length>70){
					document.getElementById('inputNum').innerText = length;
					$("#contePrice").text(Math.ceil(length/67));		
				}else{
					document.getElementById('inputNum').innerText = length;
					if(length==0){
						$("#contePrice").text(0);	
					}else{
						$("#contePrice").text(1); 
					}
					
				}
			}
			
			//确定发送短信点击发送短信
			function confirmSend(){
				var phone = $("#phones").val();
				var content = $("#send_sms_text").val();
				var contentNum = $("#contePrice").val();
				var orderIds = $("#orders").val();
				var nickname = $("#nickname").val();
				var templateId = $("#templateId").val();
				var smsSign = $(".sms_sign").val();
				if(content == ''){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("请填写短信内容!!!")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000);
				}else if(content.indexOf("【"+smsSign+"】") < 0){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("短信签名不能为空!!!")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000);
				}else{
					$(".fasong_2").hide();
					//发送短信前判断短信内容是否有短信签名，没有则添加上
					
					//发送短信前判断短信内容是否有退订回N，没有则添加上
					content = content.replace("退订回N", "") + "退订回N";
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("发送中....");
					
					var url = "${ctx}/crm/test/sendSMSAppraise";
					$.post(url,{"content":content,"phone":phone,"contentNum":contentNum,"orderIds":orderIds,"nickname":nickname,"templateId":templateId},function (data) {
						if(data.smsNum != null){

							$(".tishi_2").show();
							$(".tishi_2").children("p").text(data.smsNum)
							setTimeout(function(){ 
								$(".tishi_2").hide()
							},3000)
						}else{
							if(data.send=='100'){
								$(".tishi_2").show();
								$(".tishi_2").children("p").text("发送成功")
								setTimeout(function(){ 
									$(".tishi_2").hide()
								},3000)
							}else{
								if(data.sendIndex != 0){
									$(".tishi_2").show();
									$(".tishi_2").children("p").text("发送第"+data.sendIndex+"条时失败")
									setTimeout(function(){ 
										$(".tishi_2").hide()
									},3000)
								}else{
									$(".tishi_2").show();
									$(".tishi_2").children("p").text("发送失败")
									setTimeout(function(){ 
										$(".tishi_2").hide()
									},3000)
								}
							}
						}
					}); 
				}
			}
		
		
	
	$(function(){
		//1,回显快速筛选勾选状态
		var filtrateTime =  $('#filtrateTime').val();
		if(filtrateTime =='180天内'){
			$('#sx0').addClass('bgc_check_blue');
		}
		if(filtrateTime =='30天内'){
			$('#sx1').addClass('bgc_check_blue');
		}
		if(filtrateTime =='今日新增'){
			$('#sx2').addClass('bgc_check_blue');
		}
		if(filtrateTime =='近三天新增'){
			$('#sx3').addClass('bgc_check_blue');
		}
		if(filtrateTime =='一天后过期'){
			$('#sx4').addClass('bgc_check_blue');
		}
		if(filtrateTime =='三天后过期'){
			$('#sx5').addClass('bgc_check_blue');
		}
		if(filtrateTime =='已过期'){
			$('#sx6').addClass('bgc_check_blue');
		}
		
		//2.回显评价类型勾选状态
		var appraiseType = $('#appraiseType').val();
		
		if(appraiseType == 'neutral'){
			$('#lx1').addClass('bgc_check_blue')
		}
		if(appraiseType == 'bad'){
			$('#lx2').addClass('bgc_check_blue')
		}
		if(appraiseType == 'all'){
			$('#lx0').addClass('bgc_check_blue')
		}
	})
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	$("#sx0").click(function(){
		$('#sx1').removeClass('bgc_check_blue');
		$('#sx2').removeClass('bgc_check_blue');
		$('#sx3').removeClass('bgc_check_blue');
		$('#sx4').removeClass('bgc_check_blue');
		$('#sx5').removeClass('bgc_check_blue');
		$('#sx3').removeClass('bgc_check_blue');
	});
	$("#sx1").click(function(){
		$('#sx0').removeClass('bgc_check_blue');
		$('#sx2').removeClass('bgc_check_blue');
		$('#sx3').removeClass('bgc_check_blue');
		$('#sx4').removeClass('bgc_check_blue');
		$('#sx5').removeClass('bgc_check_blue');
		$('#sx6').removeClass('bgc_check_blue');
	});
	$("#sx2").click(function(){
		$('#sx1').removeClass('bgc_check_blue');
		$('#sx0').removeClass('bgc_check_blue');
		$('#sx3').removeClass('bgc_check_blue');
		$('#sx4').removeClass('bgc_check_blue');
		$('#sx5').removeClass('bgc_check_blue');
		$('#sx6').removeClass('bgc_check_blue');
	});
	$("#sx3").click(function(){
		$('#sx1').removeClass('bgc_check_blue');
		$('#sx2').removeClass('bgc_check_blue');
		$('#sx0').removeClass('bgc_check_blue');
		$('#sx4').removeClass('bgc_check_blue');
		$('#sx5').removeClass('bgc_check_blue');
		$('#sx6').removeClass('bgc_check_blue');
	});
	$("#sx4").click(function(){
		$('#sx1').removeClass('bgc_check_blue');
		$('#sx2').removeClass('bgc_check_blue');
		$('#sx3').removeClass('bgc_check_blue');
		$('#sx0').removeClass('bgc_check_blue');
		$('#sx5').removeClass('bgc_check_blue');
		$('#sx6').removeClass('bgc_check_blue');
	});
	$("#sx5").click(function(){
		$('#sx1').removeClass('bgc_check_blue');
		$('#sx2').removeClass('bgc_check_blue');
		$('#sx3').removeClass('bgc_check_blue');
		$('#sx4').removeClass('bgc_check_blue');
		$('#sx0').removeClass('bgc_check_blue');
		$('#sx6').removeClass('bgc_check_blue');
	});
	$("#sx6").click(function(){
		$('#sx1').removeClass('bgc_check_blue');
		$('#sx2').removeClass('bgc_check_blue');
		$('#sx3').removeClass('bgc_check_blue');
		$('#sx4').removeClass('bgc_check_blue');
		$('#sx5').removeClass('bgc_check_blue');
		$('#sx0').removeClass('bgc_check_blue');
	});
	$("#lx0").click(function(){
		$('#lx1').removeClass('bgc_check_blue');
		$('#lx2').removeClass('bgc_check_blue');
	});
	$("#lx1").click(function(){
		$('#lx0').removeClass('bgc_check_blue');
		$('#lx2').removeClass('bgc_check_blue');
	});
	$("#lx2").click(function(){
		$('#lx0').removeClass('bgc_check_blue');
		$('#lx1').removeClass('bgc_check_blue');
	});
	$(".text_detail").click(function(){
		$(".text_detail").addClass("one_line_only lh50 h50");
		$(this).removeClass("one_line_only lh50 h50");
	});
	//计费规则新建标签跳转
	$("#JFGZ").click(function(){
			var ctx = $("#ctx").val();
			window.open(ctx+"/crms/home/notice#duanxinxiangguan");
			/* window.open("${ctx}/crms/home/notice#duanxinxiangguan"); */
	});
</script>
</html>
