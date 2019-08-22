<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>营销中心-订单短信群发效果分析</title>
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
					<div class="font16 f_l h50 lh50 bgc_fff out c_384856 w140 h50 cursor_ one text-center">
						订单短信群发群发列表
					</div>
				</a>
				<div class="h50 lh50 f_l display_none hide_this">
					<!-- <div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
						活动名称
					</div> -->
					<a href="${ctx}/order/effectIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
							效果分析
						</div>
					</a>
					<a href="${ctx}/order/customerIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
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
				<div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in position_relative four">
					
					<div style="line-height: 2.5vw;">
						<form id="formId" action="${ctx}/order/msgSendRecord" method="post">
							<div class="f_l"style="margin-right: 1vw;">
								发送时间：
							</div>
							<div class="f_l position_relative">
								<input style="border: 1px solid #cad3df; border-radius:0.5vw;" name="bTime" class="bgc_fff w230 p_l10 h50 m_r10" value="${bTime}" readonly="true" type="text" id="tser31"  >
								<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png"/>
							</div>
							<div class="f_l lh50">~</div>
							<div class="f_l position_relative">
								<input style="border: 1px solid #cad3df;border-radius:0.5vw;" name="eTime" class="bgc_fff border0 w230 p_l10 h50 m_l10 m_r40" readonly="true" value="${eTime}" type="text" id="tser32" >
								<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png"/>
							</div>
							<div class="f_l">
								活动名称：
								<input type="hidden" id="ctx" value="${ctx}">
							</div>
							<input id="activityNameId" name="activityName" value="${activityName}" style="border: 1px solid #cad3df;border-radius:0.5vw;" class="bgc_fff f_l w230 p_l10 h50 m_r10" />
							<div onclick="subForm('formId')" class="w100 h48 b_radius5 c_00a0e9 border_00a0e9 tk text-center f_l cursor_">
								查询
							</div>
							<div class="clear"></div>
						</form>
					</div>
					
					<table border="0" class="font14 item_table" style="margin-top:3.5vw;">
							<tr class="bgc_e3e7f0">
								<th style="width: 6vw;" class=" h60 text-center">序号</th>
								<th style="width: 15vw;" class=" h60 text-center">活动名称</th>
								<th style="width: 15vw;" class=" h60 text-center">发送时间</th>
								<th style="width: 20vw;" class=" h60 text-center">短信模板内容</th>
								<th style="width: 8vw;" class=" h60 text-center">任务状态</th>
								<th style="width: 8vw;" class=" h60 text-center">效果分析</th>
							</tr>
							<% int i = 1; %>
							<c:choose>
								<c:when test="${pagination.list.size() > 0}">
									<c:forEach items="${pagination.list}" var="msgRecords">
										<tr class="toggleTr">
											<td class=" h60 text-center"><%= i++ %></td>
											<td class=" h60 text-center">${msgRecords.activityName}</td>
											<td class=" h60 text-center"><fmt:formatDate value="${msgRecords.sendCreat}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td class=" h60 text-center"><div class="toggleDiv">${msgRecords.templateContent}</div></td>
											<td class=" h60 text-center">
												<input type="hidden" value="${msgRecords.status}">
											<c:choose>
													<c:when test="${msgRecords.status == '1'}">全部成功</c:when>
													<c:when test="${msgRecords.status == '2'}">全部失败</c:when>
													<c:when test="${msgRecords.status == '3'}">部分成功</c:when>
													<c:when test="${msgRecords.status == '4'}">发送中</c:when>
													<c:when test="${msgRecords.status == '5'}">发送完成</c:when>
													<c:otherwise>部分成功</c:otherwise>
												</c:choose></td>
											<td class="chakan h60 text-center c_00a0e9 cursor_ check_this">查看<input type="hidden" id="msgId" value="${msgRecords.id}"></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:when test="${pagination.list.size() == 0}">
									<td class=" h60 text-center" colspan="6">没有数据</th>
								</c:when>
								<c:otherwise>
									<td class=" h60 text-center" colspan="6">没有数据</th>
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
	//时间插件需要点俩次的解决方法
	$('#tser31,#tser32').click(function(){
		console.log(1);
	});
	function subForm(formId){
		$('#' + formId).submit();
	}
	/* $(document).on('click', '.check_this', function() {
		$(".hide_this").show();
	}); */
	
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
	
	/* $(".set_time").click(function(){
			$(this).toggleClass("bgc_check_blue");
			$(this).next().next(".set_this_time").toggleClass("display_none");
	}); */
	$('.check_this').click(function(){
		var thisStatus = $(this).prev().children('input').val();
		if(thisStatus == '4'){
			return;
		}else{
			$(".hide_this").show();
			var ctxVal = $('#ctx').val();
			window.location.href = ctxVal + "/order/effectIndex?msgId=" + $(this).children('input').val();
		}
	})
	
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
	$(function(){
		toggleShow('.toggleTr','.toggleDiv',3);
		var start={
			insTrigger:false,
			isTime:true,
			format:'YYYY-MM-DD hh:mm:ss',
			choosefun: function(elem, val, date){

		        end.minDate = val; //开始日选好后，重置结束日的最小日期
		    }
		};	
		var end={
			insTrigger:false,
			isTime:true,
			format:'YYYY-MM-DD hh:mm:ss', //最大日期
		    choosefun: function(elem, val, date){

		        start.maxDate = val; //将结束日的初始值设定为开始日的最大日期
		    }	
		}
		$('#tser31').click(function(){
			$('#tser31').jeDate(start);
		});
		$('#tser32').click(function(){
			$('#tser32').jeDate(end);	
		});
	});
	//短信内容展开显示
	function toggleShow(sName,sNameChlid,num){
		$(sName).each(function(index){
			$(this).children('td').eq(num).css({
				width:'12.92vw'
			});
			$(this).children('td').eq(num).children('div').css({
				height:'2.6vw',
				overflow:'hidden'
			});
			if($(this).children('td').eq(num).children('div').text().length>48){
				$(this).addClass('on');
				$(this).children('td').eq(num).on('click',function(){
					if($(this).parents(sName).hasClass('on')){
						$(this).children(sNameChlid).css({
							height:'auto'
						});
						$(this).parents(sName).removeClass('on');
					}else{
						$(this).children('td').eq(num).children('div').css({
							height:'2.6vw'
						});
						$(this).parents(sName).addClass('on');	
					}
					
				});
			}else{
				$(this).removeClass('on');
				$(this).children('td').eq(num).children('div').css({
					height:'auto'
				});
			}
			
		});
	}
</script>
</html>
