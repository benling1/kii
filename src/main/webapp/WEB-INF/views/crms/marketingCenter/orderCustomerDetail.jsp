<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>客户详情</title>
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
								<div class="f_l w200 text-center cursor__">
									指定号码发送效果分析
								</div>
							</a>
							<a class="c_384856" href="${ctx}/order/msgSendRecord">
								<div class="f_l w200 text-center cursor__ bgc_e3e7f0">
									订单短信群发效果分析
								</div>
							</a>	
						</div>
						<div class="clear"></div>
					</div>
					
					
					<!---------------订单短信群发效果分析--------------->
					<div class="">
						<!----------上部---------->
						<div class="w1605 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!-- -------------标题------------- -->
							<div class="font24 m_b10">
								订单短信群发效果分析
							</div>
							<!-- -------------描述------------- -->
							<div class="font14 m_b20">
								帮助商家更好的了解短信发送的效果，以及根据效果制定新的营销活动
							</div>
						</div>
				
		<!----------下部---------->
				<a href="${ctx}/order/msgSendRecord">
					<div class="font16 f_l h50 lh50 bgc_e3e7f0 out c_384856 w140 h50 cursor_ one text-center">
						订单短信群发群发列表
					</div>
				</a>
				<div class="h50 lh50 f_l hide_this">
					<!-- <div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
						活动名称
					</div> -->
					<a href="${ctx}/order/effectIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
							效果分析
						</div>
					</a>
					<a href="${ctx}/order/customerIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_fff cursor_ text-center">
							客户详情
						</div>
					</a>
					<a href="${ctx}/order/itemIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
							商品详情
						</div>
					</a>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
							
				
				<div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in position_relative three">
					<form id="formId" action="${ctx}/order/customerIndex" method="post">
					
					<div style="line-height: 2.5vw;">
						<div class="f_l">
							订单来源：
							<input type="hidden" id="orderSource" name="orderSource" value="${orderSource}">
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
							客户类型：
							<input type="hidden" id="customerType" name="customerType" value="${customerType}">
						</div>
						<div class="wrap f_l h50 lh50 w140"style="margin: 0 1vw;">
							<div class="nice-select h50 lh50 w117 z_3"
								name="nice-select">
								<input id="customerTypeId" readonly="readonly"
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="下单客户">
								<ul id="customerTypeUl">
									<li value="total">下单客户</li>
									<li value="success">成交客户</li>
									<li value="wait">订单未付款客户</li>
									<li value="fail">退款客户</li>
								</ul>
							</div>
						</div>
						<div class="f_l">
							效果分析日期：
							<input type="hidden" id="dayNum" name="dayNum" value="${dayNum}">
						</div>
						<div class="f_l position_relative">
							<input style="width:10vw; border: 1px solid #cad3df; border-radius:0.5vw;" readonly="readonly" type="text" class="bgc_fff w230 p_l10 h50 m_r10" value="${beginTime}" />
						</div>
						<div class="f_l lh50">~</div>
						<div class="f_l position_relative">
							<input style="width:10vw;border: 1px solid #cad3df;border-radius:0.5vw;" readonly="readonly" type="text" class="bgc_fff w230 p_l10 h50 m_r10" value="${endTime}"/>
						</div>
<!-- 						<div onclick="subForm('formId')" id="subFormId" class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_"> -->
<!-- 							搜索 -->
<!-- 						</div> -->
						<div id="subFormId" onclick="subForm('formId')" class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
							搜索
						</div>
						<div class="clear"></div>
							
					</div>
					
					<div style="line-height: 2.5vw;margin-top: 1vw;">
					
						<div class="f_l">
							买家昵称：
						</div>
						<input name="buyerNick" value="${buyerNick}" style="border: 1px solid #cad3df;border-radius:0.5vw;" class="bgc_fff f_l w230 p_l10 h50 m_r10" />
					
						<div class="f_l">
							手机号：
						</div>
						<input name="phone" value="${phone}" style="border: 1px solid #cad3df;border-radius:0.5vw;" class="bgc_fff f_l w230 p_l10 h50 m_r10" />
					
						<div class="f_l">
							商品ID：
						</div>
						<input name="itemId" value="${itemId}" style="border: 1px solid #cad3df;border-radius:0.5vw;" class="bgc_fff f_l w230 p_l10 h50 m_r10" />
						<div class="clear"></div>
<!-- 						<p class="reminder">温馨提示：此处数据量较大，请您点击搜索查看效果分析。</p> -->
					</div>
					</form>
					<table border="0" class="font14 item_table" style="margin-top:1.5vw;">
							<tr class="bgc_e3e7f0">
								<th style="width: 5vw;" class=" h60 text-center">序号</th>
								<th style="width: 8vw;" class=" h60 text-center">买家昵称</th>
								<th style="width: 10vw;" class=" h60 text-center">收货人姓名</th>
								<th style="width: 8vw;" class=" h60 text-center">手机号</th>
								<th style="width: 8vw;" class=" h60 text-center">会员等级</th>
								<th style="width: 8vw;" class=" h60 text-center">订单金额</th>
								<th style="width: 8vw;" class=" h60 text-center">订单数</th>
								<th style="width: 8vw;" class=" h60 text-center">商品数</th>
								<th style="width: 10vw;" class=" h60 text-center">地址</th>
							</tr>
							<c:choose>
								<c:when test="${pagination.list.size() > 0}">
									<%int i = 0; %>
									<c:forEach items="${pagination.list}" var="customerDetail">
										<tr>
											<td class=" h60 text-center"><%=++i %></td>
											<td class=" h60 text-center">${customerDetail.buyerNick}</td>
											<td class=" h60 text-center">${customerDetail.receiverName}</td>
											<td class=" h60 text-center">${customerDetail.receiverMobile}</td>
											<td class=" h60 text-center">${customerDetail.memberlevel}</td>
											<td class=" h60 text-center">
												<fmt:formatNumber value="${customerDetail.totalOrderMoney}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
											<td class=" h60 text-center">${customerDetail.orderNum}</td>
											<td class=" h60 text-center">${customerDetail.itemNum}</td>
											<td class=" h60 text-center">${customerDetail.receiverAddress}</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<td class=" h60 text-center" colspan="9">暂时没有相关数据！</td>
								</c:otherwise>
							</c:choose>
							
						</table>
						<div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_r w525 h24 l_h22 font12">
                                	<c:choose>
										<c:when test="${pagination.list.size() > 0}">
											<c:forEach items="${pagination.pageView}" var="page">
													${page}
											  </c:forEach>
										</c:when>
										<c:otherwise>
											<td class=" h60 text-center"></td>
										</c:otherwise>
									</c:choose>
                                </div>
                         </div>
				</div>
					</div>
				</div>	
				</div>
			</div>
</body>
<script>
	function subForm(formId){
		$('#' + formId).submit();
		$('#subFormId').addClass('display_none');
	}
	
	$(function(){
		
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
		
		//订单来源回显
		var orderSourceVal = $('#orderSource').attr('value');
		if(orderSourceVal == 'WAP,WAP'){
			$('#orderSourceId').attr('value','手机端');
		}else if(orderSourceVal == 'TAOBAO'){
			$('#orderSourceId').attr('value','PC端');
		}else{
			$('#orderSourceId').attr('value','全部');
		}
		
		//客户类型回显
		var customerTypeVal = $('#customerType').attr('value');
		if(customerTypeVal == 'total'){
			$('#customerTypeId').attr('value','下单客户');
		}else if(customerTypeVal == 'success'){
			$('#customerTypeId').attr('value','成交客户');
		}else if(customerTypeVal == 'wait'){
			$('#customerTypeId').attr('value','订单未付款客户');
		}else if(customerTypeVal == 'fail'){
			$('#customerTypeId').attr('value','退款客户');
		}
		
		//筛选天数回显
		var dayNumVal = $('#dayNum').attr('value');
		if(dayNumVal == '1'){
			$('#dayNumId').attr('value','近1天');
		}else if(dayNumVal == '7'){
			$('#dayNumId').attr('value','近7天');
		}else if(dayNumVal == '15'){
			$('#dayNumId').attr('value','近15天');
		}else if(dayNumVal == '30'){
			$('#dayNumId').attr('value','近30天');
		}
	})

	$(document).on('click', '.check_this', function() {
		$(".hide_this").show();
	});
	
	//订单来源选择
	$('#orderSourceUl li').click(function(){
		var sourceLi = $(this).attr('value');
		$('#orderSource').attr('value',sourceLi);
	})
	
	//客户类型选择
	$('#customerTypeUl li').click(function(){
		var cusTypeLi = $(this).attr('value');
		$('#customerType').attr('value',cusTypeLi);
	})
	
	//筛选天数选择
	$('#dayNumUl li').click(function(){
		$('#dayNum').val($(this).attr('value'));
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
	$('.check_this').click(function(){
		alert($(this).children('input').val());
		window.location.href = "${ctx}/member/effectPicture?msgId=" + $(this).children('input').val();
	})
	

</script>
</html>
