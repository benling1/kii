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

<script src="${ctx}/crm/js/pingjiaguanhuai_3.js" type="text/javascript"
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
				<div class="w1660 h350  bgc_f1f3f7 c_384856  ">
					<div class="p_l40 p_t27">
						<p class="font20">评价记录</p>
						<p class="font14 p_t8">可以查看自动评价和批量评价的历史记录！(只保留最近一个月的记录)</p>
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
					<div class="font16 c_384856 h50 lh50 m_t30 p_l40">
						<div class="w150 h50 text-center f_l bgc_e3e7f0 cursor_">
							<a class="c_384856 display_block" href="${ctx}/crms/reviewCare">自动评价</a>
						</div>
						<%-- <div class="w150 h50 text-center f_l bgc_e3e7f0 cursor_">
							<a class="c_384856 display_block" href="${ctx}/crms/batchReview">批量评价</a>
						</div> --%>
						<div class="w150 h50 text-center f_l bgc_fff cursor_">
							<a class="c_384856 display_block" href="${ctx}/crms/reviewRecord">评价记录</a>
						</div>
					</div>
				</div>
				<!----------下部---------->
				<div class="p_1  w1660 h1100 bgc_fff">
					<p class="p_t15 m_l80 font14 c_384856">提示：订单交易成功后15天内进行评价，请注意评价有效时间，快要过期的订单会列在前头</p>
					<form id="formSubmit" action="${ctx}/crms/reviewRecord"
						method="post">
						<div>
							<%-- <div class="f_l h22 lh22  m_t35 m_l80 font16">
								评价方式： <input id="rateTypeId" name="rateType"
									value="${tradeRates.rateType}" type="hidden">
							</div>
							<div class="w200 h45 f_l  m_r25 m_t25 m_l10">
								<div class=" f_l h45 lh45 m_l10 ">
									<div class="nice-select h45 lh45 w200 z_2" name="nice-select">
										<input id="rateType" readonly="readonly"
											class="h45 lh45 w200 settime" type="text" value="全部"
											placeholder="全部">
										<ul id="rateTypeUl">
											<li value="全部">全部</li>
											<li value="自动评价">自动评价</li>
											<li value="批量评价">批量评价</li>
										</ul>
									</div>
								</div>
							</div> --%>
							<div class="f_l m_r15 m_l25 m_t35 h22 lh22 " style="margin-left: 4.202vw;">买家昵称 :</div>
							<input class="bgc_f4f6fa border0 w300 h50 f_l m_r10 m_t25"
								name="nick" value="${tradeRates.nick}" />
							<div class="f_l m_r15 m_l25 m_t35 h22 lh22 m_l25">订单号:</div>
							<input class="bgc_f4f6fa border0 w450 h50 f_l m_r10 m_t25"
								name="oid" value="${tradeRates.oid}"
								onkeyup="value=value.replace(/[^\d]/g,'')" />
						</div>
						<div>
							<div class="clbo f_l h22 lh22  m_t35 m_l80 font16">
								评价结果： <input id="resultId" name="result"
									value="${tradeRates.result}" type="hidden">
							</div>
							<div class="h45 f_l  m_r25 m_t25 m_l10">
								<div class=" f_l h45 lh45 m_l10 ">
									<div class="nice-select h45 lh45" name="nice-select">
										<input id="result" readonly="readonly"
											class="h45 lh45 w200 settime" value="全部" type="text"
											placeholder="全部">
										<ul id="resultUl">
											<li value='all'>全部</li>
											<li value='good'>好评</li>
											<li value='neutral'>中评</li>
											<li value='bad'>差评</li>
										</ul>
									</div>
								</div>
							</div>
							<div class=" f_l h22 lh22  m_t35 m_l20 font16">评价时间：</div>
							<div class="h45 f_l m_t25 ">
								<div class=" f_l h45 lh45 m_l10 ">
									<div class="nice-select h45 lh45" name="nice-select"
										id="nice-select4">
										<input class="bgc_f4f6fa border0 w240  h50 " name="bTime"
											value="${bTime}" type="text" id="tser03" readonly
											onclick="$.jeDate('#tser03',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val){valiteMyTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
									</div>
								</div>
							</div>
							<div class="f_l  w50 h45 lh45 text-center font16 m_l10 m_t25">~</div>
							<div class="h45 f_l  m_r25 m_t25 m_b55">
								<div class=" f_l h45 lh45 w200 m_l10 ">
									<div class="nice-select h45 lh45" name="nice-select"
										id="nice-select5">
										<input class="bgc_f4f6fa border0 w240  h50 " name="eTime"
											value="${eTime}" type="text" id="tser04" readonly
											onclick="$.jeDate('#tser04',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val){valiteMyTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})">
									</div>
								</div>
							</div>
							<div onclick="searchButton('formSubmit')"
								class="f_l w100 h40 l_h40 text-center bgc_00a0e9 b_radius5 cursor_ c_fff m_l45 m_t25">
								查询</div>
						</div>
					</form>
					<div class="clbo c_384856 m_l80 font14">
						<table>
							<tr class="w1280 h54">
								<th class="w480">订单号</th>
								<th class="w100">买家昵称</th>
								<th class="w100">评价时间</th>
								<th class="w320">评价内容</th>
								<th class="w140">评价结果</th>
								<th class="w140">备注</th>
							</tr>
							<c:choose>
								<c:when test="${pagination == null}">
									<tr class="w1280 h54">
										<td class="w480 text-center" colspan="6">暂时没有相关数据！</td>
									</tr>
								</c:when>
								<c:when test="${pagination.list.size() > 0}">
									<c:forEach items="${pagination.list}" var="tradeRates">
										<tr class="w1280 h54">
											<td class="w480 text-center c_384856 font14">${tradeRates.oid}</td>
											<td class="w100 text-center c_384856 font14">${tradeRates.nick}</td>
											<td class="w100 text-center c_384856 font14"><fmt:formatDate
													value="${tradeRates.created}" pattern="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td class="w320 text-center c_384856 font14">${tradeRates.content}</td>
											<td class="w140 text-center c_384856 font14"><c:if
													test="${tradeRates.result == 'good'}">好评</c:if> <c:if
													test="${tradeRates.result == 'neutral'}">中评</c:if> <c:if
													test="${tradeRates.result == 'bad'}">差评</c:if></td>
											<td class="w140 text-center c_384856 font14">${tradeRates.reply}</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="w1280 h54">
										<td class="w480 text-center" colspan="6">暂时没有相关数据！</td>
									</tr>
								</c:otherwise>
							</c:choose>
							
							<!-- <tr class="w1280 h54">
										    <td class="w480 text-center c_384856 font14">维罗养生壶电解磁化养生壶陶瓷</td>
										    <td class="w100 text-center c_384856 font14"></td>
										    <td class="w100 text-center c_384856 font14"></td>
										    <td class="w320 text-center c_384856 font14"></td>
										    <td class="w140 text-center c_384856 font14"></td>
										    <td class="w140 text-center c_384856 font14"></td>
										  </tr> -->
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




	<div
		class="w100_ h1000 rgba_000_5 position_fixed top0 display_none z_10 tianjia_2">

		<div class=" position_absolute top100 left320">
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
							<textarea maxlength="500"
								class="bgc_f4f6fa text_area border_d9dde5 b_radius5 w458 h363 m_l15"></textarea>
							<div class="h30 w458 m_t_24 m_l20 c_bfbfbf">
								已输入：<span class="text_count">0</span>/500字



							</div>




						</div>



						<div class="w495 h40 m_t60  ">
							<div
								class="m_l180 w100 h40 border_00a0e9 c_00a0e9 close tk b_radius5 text-center f_l l_h40 cursor_ font14  m_l10 close_inside">
								确定</div>

						</div>
					</div>

					<div class="w595 h668 f_l bgc_fff m_l10 ">
						<div class="w590 h50 bgc_f1f3f7">
							<div
								class="w130 h50 text-center out bgc_fff l_h50 f_l cursor_ moban1">
								案例模板</div>

							<div
								class="w150 h50 text-center out l_h50 bgc_e3e7f0 f_l cursor_ moban1">
								历史使用</div>

						</div>

						<div class="in moban2">

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

							<!-- <div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">
		    
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



		</div>


	</div>










</body>
<script>
function valiteMyTwoTime(){
	var startTime = $(tser03).val();
	var endTime = $(tser04).val();
//	console.log(startTime);
//	console.log(endTime);
//	console.log("转化时间 " + new Date(startTime));
	if(startTime!=undefined && endTime!=undefined && startTime!="" && endTime!=""){
		var startTimeDate = new Date(startTime);
		var endTimeDate = new Date(endTime);
		console.log(startTimeDate>endTimeDate);
		if(startTimeDate > endTimeDate){
			$(tser04).val("");
			confirm("开始时间不能大于结束时间！");
			return false;
		}else if(startTimeDate == endTimeDate){
			$(tser04).val("");
			confirm("开始时间不能等于结束时间！");
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}
	$(function(){
		$(".load_top").load("top.html");
		$(".load_left").load("left.html");
		
		//回显评价状态
		var resultVal = $('#resultId').val();
		if(resultVal == 'good'){
			$('#result').val("好评");
		}else if(resultVal == 'neutral'){
			$('#result').val("中评");
		}else if(resultVal == 'bad'){
			$('#result').val("差评");
		}else{
			$('#result').val("全部");
		}
		
		//回显评价方式
		/* var rateTypeVal = $('#rateType').val();
		if(rateTypeVal == '批量评价'){
			$('#rateType').val("批量评价");
		}else if(rateTypeVal == '自动评价'){
			$('#rateType').val("自动评价");
		}else{
			$('#rateType').val("全部");
		} */
	})
	function searchButton(formId){
		document.getElementById(formId).submit();
	}
	$('#resultUl li').click(function(){
		$('#resultId').val($(this).attr("value"));
	});
	/* $('#rateTypeUl li').click(function(){
		$('#rateTypeId').val($(this).attr("value"));
	}); */
</script>
</html>
