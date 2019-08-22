<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>中差评监控</title>
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
							<li class="w140 bgc_fff text-center bgc_f1f3f7 f_l"><a
								class="c_384856 display_block"
								href="${ctx}/appraiseAmend/showAppraiseAmend"> 中差评管理 </a></li>
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block"
								href="${ctx}/OrderReminder/jumOrderReminder"> 手动订单提醒 </a></li>
							<li style="clear: both;"></li>
						</ul>
					</div>
				</div>


				<div class="w1660  bgc_f1f3f7 c_384856 ">
					<div class="p_l40 p_t27">
						<p class="font20" id="tit">中差评监控</p>
						<p class="font14 p_t8">当店铺有新的中差评时，会发送手机短信到卖家指定的手机上，只有双方互评后才会发送通知消息。请及时先给予买家评价。</p>
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
					<ul class="h40 p_t40 font14 p_l40">
						<li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/appraiseAmend/showAppraiseAmend">中差评查看</a></li>
						<li class="f_l w140 text-center h40 l_h40 bgc_fff cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/appraiseMonitoring/showAppraiseMonitoring">中差评监控</a></li>
						<li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/appraisePacify/showAppraisePacify">中差评安抚</a></li>
						<li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 ping_1 cursor_"><a
							class="c_384856 display_block" href="#"
							onclick="findOrderSendRedord('queryForm')">中差评统计</a></li>
						<%-- <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseSet/showAppraiseCauseSet">中差评原因设置</a></li> --%>
						<%-- <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseAnalyze/showAppraiseCauseAnalyze">中差评原因分析</a></li> --%>
					</ul>

				</div>
				

				<!--  中差评监控-->
				<div class=" ping_2">
					<div class="w1660 bgc_fff h865  ">
						<div class="w1360 h865 font14">
							<div class="w810 h50 p_t40 p_l70">
								<div id="jk" onclick="showMonitoring();"
									class=" w150 h40 border_d9dde5 b_radius5 text-center f_l l_h40 c_fff cursor_ bgc_00a0e9">
									中差评监控设置</div>
								<div id="ck" onclick="findOrderSendRedord('queryForm');"
									class=" w150 h40 border_d9dde5 b_radius5  text-center f_l l_h40 c_8493a8 cursor_">
									查看发送记录</div>
							</div>

							<!--   中差评监控设置-->
							<div id="jkShow" class="w1160 jiankong_2">
								<div class="w1160 h45 p_l70">

									<span class="f_l l_h45">温馨提示：因淘宝规则限制，需要双方互评后才可以发送预警，建议您开启"自动评价",确保中差评能够及时提醒!</span>

									<a class="w100 display_block f_l c_00a0e9"
										href="javascript:openNewWin()">
										<div
											class=" w100 h40 border_00a0e9 b_radius5 text-center l_h40 tk cursor_ font14  m_l20">
											点我设置</div>
									</a>

								</div>


								<form id="monitoringForm"
									action="${ctx}/appraiseMonitoring/saveOrUpdateMonitoring"
									method="post">
									<input type="hidden"
										value="【客云CRM】亲爱的商家，您的店铺出现了中差评，中差评将影响宝贝排名，建议您请及时查看处理。退订回复N"
										name="content" />
									<div class="w1160 h40  m_t20 p_l70">
										<div class="f_l">是否开启：</div>
										<input type="hidden" name="id"
											value="${orderRateCareSetup.id }" /> <input
											id="openMonitoring" type="hidden" name="status"
											value="${orderRateCareSetup.status }" />
										<div id="img0" onclick="openMonitoring('0')"
											class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15  bgc_e0e6ef">
											<img class="cursor_ one_check_ display_none m_b10 w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l m_r35 l_h20">是:</div>
										<div id="img1" onclick="openMonitoring('1')"
											class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15  bgc_e0e6ef">
											<img class="cursor_ one_check_ display_none m_b10 w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l m_r35 l_h20">否:</div>
									</div>

									<div class="w1160 h40 p_l35">
										<div class="f_l  l_h40 " style="margin-right: 0.35vw;">
											接受短信号码&nbsp;:</div>
										<input id="acceptSmsPhone"
											onkeyup="value=value.replace(/[^\d,]/g,'')"
											class="bgc_f4f6fa border_d9dde5 b_radius5 w300 h40 f_l m_r10 "
											name="acceptSmsPhone"
											value="${orderRateCareSetup.acceptSmsPhone }" />
										<div class="f_l m_r15 l_h40">多个号码用英文","隔开</div>
									</div>

									<div class="w1160 h120  m_t20 p_l70">
										<span class="f_l l_h40">是否开启：</span> <input
											id="openStatusFiltrate" type="hidden" name="statusFiltrate"
											value="${orderRateCareSetup.statusFiltrate }" />
										<div class="f_l">
											<div id="img2" onclick="openStatusFiltrate()"
												class="cursor_ m_t10 one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15  bgc_e0e6ef check_div">
												<img
													class="cursor_ one_check_ display_none m_b10 w20 h20 check_img"
													src="${ctx}/crm/images/black_check.png" /> <input
													type="hidden" value="0" />
											</div>
											<div class="f_l m_r35 lh40">当有中评时通知我</div>
										</div>

										<div class=" h40 m_l80 p_t20" style="width: 12.021vw;">
											<div id="img3" onclick="openStatusFiltrate()"
												class="cursor_ m_t10 one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15 bgc_e0e6ef check_div">
												<img
													class="cursor_ one_check_ display_none m_b10 w20 h20 check_img"
													src="${ctx}/crm/images/black_check.png" /> <input
													type="hidden" value="1" />
											</div>
											<div class="f_l m_r35 lh40">当有差评时通知我</div>
										</div>
									</div>
								</form>

								<div class="w300 h40  m_t20 p_l150">
									<div onclick="saveSet('monitoringForm')"
										class="w100 h40 border_00a0e9 c_00a0e9 b_radius5 text-center l_h40 tk cursor_ font14 ">
										保存设置</div>
								</div>
							</div>

							<div id="ckShow" class="display_none jiankong_2">
								<!--  查看发送记录  -->
								<div class="w1290 h850 p_l70">
									<div class="w1290 h50  p_t20 m_b20">
										<form id="queryForm"
											action="${ctx}/sendRecord/orderCenterRecord"
											method="post">
											<div class="f_l m_r15 l_h50">发送时间:</div>

											<input type="hidden" name="recordFlag" value="8">
											<div class="f_l position_relative">
												<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40"
													type="text" id="tser03" name="bTime" value="${bTime }"
													readonly="true"
													onclick="$.jeDate('#tser03',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val){valiteTwoMyTime();},okfun:function(elem, val) {valiteTwoMyTime();}})">
												<img style="width: 1vw;"
													class="position_absolute right50 top15"
													src="${ctx}/crm/images/date_copy.png" />
											</div>

											<div class="f_l l_h40">~</div>
											<div class="f_l position_relative">
												<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40"
													type="text" id="tser04" name="eTime" value="${eTime }"
													readonly="true"
													onclick="$.jeDate('#tser04',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val){valiteTwoMyTime();},okfun:function(elem, val) {valiteTwoMyTime();}})">
												<img style="width: 1vw;"
													class="position_absolute right50 top15"
													src="${ctx}/crm/images/date_copy.png" />
											</div>


											<div onclick="submitForm('queryForm');"
												class="w100 h40 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_ l_h40 m_r40">
												查询</div>
											<input type="hidden" id="flag" name="flag" value="${flag }" />
										</form>
									</div>

									<div class="w1290 h400  font14">
										<table class="text-center">
											<tr class="h50 bgc_f1f3f7 font12">
												<th class="w231 ">买家昵称</th>
												<th class="w231 ">订单编号</th>
												<th class="w231 ">评价时间</th>
												<th class="w231 ">评论结果</th>
												<th class="w231 ">发送时间</th>
											</tr>
											<c:if test="${pagination.list.size() ==0}">
												<tr>
													<td class="w75 text-center" align="center" colspan="5">暂时没有相关数据!</td>
												</tr>
											</c:if>
											<c:if test="${pagination != null}">
												<c:forEach items="${pagination.list}" var="smsSendRecord">
													<tr class="bgc_fafafa h50 w1155">
														<td>${smsSendRecord.buyerNick}</td>
														<td>${smsSendRecord.orderId}</td>
														<td><fmt:formatDate
																value="${smsSendRecord.receiverTime}"
																pattern="yyyy-MM-dd HH:mm:ss" /></td>
														<td>
															<div
																class="w231 margin0_auto h50 lh50 one_line_only text_detail">
																<span>${smsSendRecord.content}</span>
															</div>

														</td>
														<td><fmt:formatDate value="${smsSendRecord.sendTime}"
																pattern="yyyy-MM-dd HH:mm:ss" /></td>
													</tr>
												</c:forEach>
											</c:if>



										</table>
										<div class="c_8493a8">
											<c:if test="${pagination != null}">
												<c:forEach items="${pagination.pageView}" var="page">
		                                    		${page}
		                                    	</c:forEach>
											</c:if>
										</div>
									</div>

									<div class="w1160 h24 m_t100 font14 c_8493a8 m_b40 ">
										<div class="f_r w470 h24 l_h22 font12"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<input type="hidden" value="${saveFlag }" id="saveFlag" />


			</div>
</body>
<script>
	window.onload = function() {
		var msg = $("#msg").val();
		var saveFlag = $("#saveFlag").val();
		if (msg != null && msg != "") {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text(msg)
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000);
		}
		if (saveFlag != null && saveFlag != "") {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text(
					(saveFlag == "success") ? "保存成功" : "保存失败")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000);
		}
	}
	function valiteTwoMyTime() {
		var startTime = $(tser03).val();
		var endTime = $(tser04).val();
		//		console.log(startTime);
		//		console.log(endTime);
		//		console.log("转化时间 " + new Date(startTime));
		if (startTime != undefined && endTime != undefined && startTime != ""
				&& endTime != "") {
			var startTimeDate = new Date(startTime);
			var endTimeDate = new Date(endTime);
			if (startTimeDate > endTimeDate) {
				$(tser04).val("");
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("开始时间不能大于结束时间！")
				setTimeout(function() {
					$(".tishi_2").hide()
				}, 3000);
				return false;
			} else if (startTimeDate == endTimeDate) {
				$(tser04).val("");
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("开始时间不能等于结束时间！")
				setTimeout(function() {
					$(".tishi_2").hide()
				}, 3000);
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	$("#acceptSmsPhone").on("blur", function() {
		var phonez = /^1[3|4|5|7|8]\d{9}$/;
		var phone = $("#acceptSmsPhone").val();

		var phoneflag = 1;//手机号发送判断标识
		var phoneList = phone.split(",");
		if (phoneList.length <= 2) {
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

		if (phoneflag == 1) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("手机号错误：" + phone)
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000);
			$("#acceptSmsPhone").val("");
		} else if (phoneflag == 2) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("手机号不能大于2个\n" + phone)
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000);
			$("#acceptSmsPhone").val("");
		}
	})
	//条件数据查询form表单
	function submitForm(fomrId) {
		document.getElementById(fomrId).submit();
	}

	//保存设置
	function saveSet(fomrId) {
		var phonez = /^1[3|4|5|7|8]\d{9}$/;
		var phone = $("#acceptSmsPhone").val();

		var phoneflag = 1;//手机号发送判断标识
		var phoneList = phone.split(",");
		if (phoneList.length <= 2) {
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
			document.getElementById(fomrId).submit();
		} else if (phoneflag == 1) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("手机号错误：" + phone)
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000);
			$("#acceptSmsPhone").val("");
		} else if (phoneflag == 2) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("手机号不能大于2个\n" + phone)
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000);
			$("#acceptSmsPhone").val("");
		}
	}

	//点击查看发送记录
	//2.点击"已收好评"
	
// 	function querySmsRecord() {
// 		window.location.href = "${ctx}/appraiseMonitoring/queryAppraiseMonitoring?flag=showRecord";
// 	}

	//获取选项卡返回参数显示数据
	$(function() {
		var flag = $('#flag').val();
		if (flag == 'showRecord') {
			$("#jkShow").hide();
			$("#ckShow").show();
			$("#ck").addClass("bgc_00a0e9 c_fff").removeClass("c_8493a8");
			$("#jk").removeClass("bgc_00a0e9").addClass("c_8493a8");
		}
	});

	//点击开启按钮是否开启
	function openMonitoring(data) {
		$("#openMonitoring").val(data);
	}

	//点击开启设置过滤添加
	function openStatusFiltrate() {

		//延迟加载获取值
		setTimeout(function() {
			var filtrateVal = "";
			var divCheck = $(".check_div");
			for (var i = 0; i < divCheck.length; i++) {
				var checkImg = divCheck.eq(i).find('.check_img').attr("style");
				if (checkImg == "display: inline;") {
					var val = divCheck.eq(i).find('input').val();
					filtrateVal += val + ","
				}
			}
			filtrateVal = filtrateVal.substring(0, filtrateVal.length - 1);
			$("#openStatusFiltrate").val(filtrateVal);
		}, 100);
	}

	//获取数据并添加样式
	$(function() {
		var openMonitoring = $("#openMonitoring").val();
		if (openMonitoring == '1') {
			$("#img1").children(".one_check_").toggle();
		}
		if (openMonitoring == '0') {
			$("#img0").children(".one_check_").toggle();
		}

		var openStatusFiltrate = $("#openStatusFiltrate").val();
		var val = openStatusFiltrate.split(",");
		for (var i = 0; i < val.length; i++) {
			if (val[i] == '0') {
				$("#img2").children(".one_check_").toggle();
			}
			if (val[i] == '1') {
				$("#img3").children(".one_check_").toggle();
			}
		}
	});

	//点击中差评监控设置页面跳转查询数据
	function showMonitoring() {
		window.location.href = "${ctx}/appraiseMonitoring/showAppraiseMonitoring";
	}

	function openNewWin() {
		window.open("${ctx}/crms/reviewCare");
	}

	/*点击显示短信详情*/
	$(".text_detail").click(function() {
		$(".text_detail").addClass("one_line_only lh50 h50");
		$(this).removeClass("one_line_only lh50 h50");
	});

	//提交form表单
	function findOrderSendRedord(formId) {
		document.getElementById(formId).submit();
	};
</script>
</html>
