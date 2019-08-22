<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
	<title>中评数统计</title>
	<%@ include file="/common/common.jsp"%>
	<jsp:useBean id="now" class="java.util.Date" scope="page"/>
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
	<script type="text/javascript" src="${ctx}/crm/js/cuifutxing.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/zhongchaping.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/highcharts.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/exporting.js" ></script>
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
				<div class="w1660 m_t70 m_l30 ">
					<div class="w100_ h50 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856" style="font-size:1vw;">
							订单中心
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
					
				
                    <div class="w1660  bgc_f1f3f7 c_384856 ">
                        <div class="p_l40 p_t27">
                            <p class="font20" id="tit">中差评统计</p>
                            <p class="font14 p_t8">查询每日中差评记录和处理统计，实时跟踪店铺好评趋势。</p>
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
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseAmend/showAppraiseAmend">中差评查看</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseMonitoring/showAppraiseMonitoring">中差评监控</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraisePacify/showAppraisePacify">中差评安抚</a></li>
                            <%-- <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 ping_1 cursor_"><a class="c_384856 display_block"  href="${ctx}/crms/appraise/middleBadAssessment?filtrateTime=最近7天">中差评统计</a></li> --%>
                            <li class="f_l w140 text-center h40 l_h40 bgc_fff ping_1 cursor_"><a class="c_384856 display_block"  href="${ctx}/crms/appraise/middleBadAssessment?filtrateTime=最近7天">中差评统计</a></li>
                            <!-- <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a class="c_384856 display_block"  href="zcp_shezhi.html">中差评原因设置</a></li> -->
                           <%--  <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseAnalyze/showAppraiseCauseAnalyze">中差评原因分析</a></li> --%>
                        </ul>
                        
                    </div>
                    
                 
                    	
                    
                
      				<!-- 中差评统计-->
                    <div class=" ping_2">
                    	<div class="w1660  bgc_fff">
                    		<div class="w1290 font14">
                    			<form action="${ctx}/crms/appraise/middleAssessment" method="post">
                    			<div class=" w1290 p_l70 h40 p_t40 l_h40">
                    				<div class="f_l">统计时间范围：</div>
									<input type="hidden" id="filtrateTime" name="filtrateTime" value="${filtrateTime }" />
								    <div id="sx0" onclick="addFiltrateTime('最近7天');" class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef bgc_e0e6ef">
								    	<img class="cursor_ one_check_ display_none m_b10 w20 h20" src="${ctx}/crm/images/black_check.png" />
								    </div>
								    <div  class="f_l m_r35 l_h20">
								    	最近7天
								    </div>
									
									<div id="sx1" onclick="addFiltrateTime('最近15天');" class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
								    	<img class="cursor_ one_check_ display_none m_b10 w20 h20" src="${ctx}/crm/images/black_check.png" />
								    </div>
								    <div class="f_l m_r35 l_h20">
								    	最近15天
								    </div>
									<div id="sx2" onclick="addFiltrateTime('最近一个月');" class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
								    	<img class="cursor_ one_check_ display_none m_b10 w20 h20" src="${ctx}/crm/images/black_check.png" />
								    </div>
								    <div  class="f_l m_r15 l_h20">
								    	最近一个月
								    </div>
									
									<div class="f_l position_relative">
										<!-- <input name="beginTime"  class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" id="tser01"  readonly onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD ',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})"> -->
										<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" id="tser07"  readonly onclick="$.jeDate('#tser07',{insTrigger:false,isTime:true,format:'YYYY-MM-DD '})" 
									name="beginTime" value="<fmt:formatDate value='${beginTime }' pattern='yyyy-MM-dd' />" >
										<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
									</div>
										
									<div class="f_l">~</div>
									
									<div class="f_l position_relative">
										<!-- <input name="endTime" class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" id="tser02"  readonly onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD ',choosefun:function(elem, val){valiteTwoTime();},okfun:function(elem, val) {valiteTwoTime();}})"> -->
										<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" id="tser08"  readonly onclick="$.jeDate('#tser08',{insTrigger:false,isTime:true,format:'YYYY-MM-DD '})"
									name="endTime" value="<fmt:formatDate value='${endTime }' pattern='yyyy-MM-dd' />" >
										<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
									</div>
								        <input id="sx3" onclick="addFiltrateTime('查询');" class="m_r45 w100 lh50 bgc_fff h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center  f_l cursor_" type="submit" value="查询">
								       
                    			</div> 
 							 </form> 
                    			
                    			<div class="w810 h50 p_t40 p_l70">
	                    		    <div >
								    	<a href="${ctx}/crms/appraise/middleBadAssessment?filtrateTime=最近7天" class=" w100 h40 border_d9dde5 b_radius5  text-center f_l l_h40 c_8493a8 cursor_">	中差评数</a>
							        </div>
							        <div >
								        <a href="${ctx}/crms/appraise/middleAssessment?filtrateTime=最近7天" class="  w100 h40 border_d9dde5 b_radius5 text-center f_l l_h40 c_fff cursor_ bgc_00a0e9">	中评数</a>
								    	<%-- <a href="${ctx}/crms/appraise/middleAssessment?filtrateTime="+filtrateTime class="  w100 h40 border_d9dde5 b_radius5 text-center f_l l_h40 c_fff cursor_ bgc_00a0e9">	中评数</a> --%>
							        </div>
							        <div >
								    	<a href="${ctx}/crms/appraise/badAssessment?filtrateTime=最近7天" class=" w100 h40 border_d9dde5 b_radius5  text-center f_l l_h40 c_8493a8 cursor_">	差评数</a>
							        </div>
							      
							    </div>
                    			
                    			<!--  中评数-->
                    			<div class=" tongji2">
	                    			<!-- <div id="container" class="w1290 h380 p_l70 p_t20">
	                    				曲线图1
	                    			</div> -->
	                    			
	                    			<div class="w1280  p_l70 p_t20"style="margin-bottom:1vw;">
	                    					<table class="text-center font14">
										        <tr class=" h55 bgc_f1f3f7 ">
													<td class="w280 bgc_f1f3f7"></td>
													<th class="w470 bgc_f1f3f7">查询汇总</th>
													<td class="w470 bgc_f1f3f7"></td>
													
												</tr>
										        
												<tr class=" h55 bgc_fafafa ">
													<td class="w280 bgc_fafafa">中评数</td>
													<td class="w470 bgc_fafafa">差评数</td>
													<td class="w470 bgc_fafafa">中差评总数</td>
													
												</tr>
												<tr class=" h55 bgc_f4f4f4 ">
													<%-- <td class="w280 bgc_f4f4f4">${ratedVo.neutral}</td> --%>
													<td class="w280 bgc_f4f4f4">
													 <c:if test="${ratedVo.neutral==null}">0</c:if>
													 <c:if test="${ratedVo.neutral !=null}">${ratedVo.neutral}</c:if>
													</td>
													<td class="w470 bgc_f4f4f4">
													 <c:if test="${ratedVo.bad==null}">0</c:if>
													 <c:if test="${ratedVo.bad!=null}">${ratedVo.bad}</c:if>
													</td>
													<td class="w470 bgc_f4f4f4">
													 <c:if test="${ratedVo.volume==null}">0</c:if>
													 <c:if test="${ratedVo.volume!=null}">${ratedVo.volume}</c:if>
													</td>
													
												</tr>
											</table>
											
											<table class="text-center font14 m_t20">
										        <tr class=" h55 bgc_f1f3f7 ">
													<td class="w260 bgc_f1f3f7">统计日期</td>
													<td class="w310 bgc_f1f3f7">中评数</td>
													<td class="w320 bgc_f1f3f7">差评数</td>
													<td class="w340 bgc_f1f3f7">中差评总数</td>
													
												</tr>
												<c:if test="${pagination.list.size() ==0 }">
												  <tr>
												    <td colspan="8">暂时没有相关记录！！！</td>
												  </tr>
											   </c:if>
											   <c:if test="${pagination.list.size() !=0 }">
											       <c:forEach items="${pagination.list}" var="ratedVoList"> 
													<tr class=" h55 bgc_fafafa ">
														<td class="w260 bgc_fafafa">
														 <c:if test="${ratedVoList.created==null}"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />
														 </c:if>
														 <c:if test="${ratedVoList.created!=null}"><fmt:formatDate value='${ratedVoList.created}' pattern='yyyy-MM-dd' /></c:if>
														</td>
														<td class="w310 bgc_f4f4f4">
														 <c:if test="${ratedVoList.neutral==null}">0</c:if>
														 <c:if test="${ratedVoList.neutral!=null}">${ratedVoList.neutral}</c:if>
														</td>
														<td class="w320 bgc_fafafa">
														 <c:if test="${ratedVoList.bad==null}">0</c:if>
														 <c:if test="${ratedVoList.bad!=null}">${ratedVoList.bad }</c:if>
														</td>
														<td class="w340 bgc_f4f4f4">
														 <c:if test="${ratedVoList.volume==null}">0</c:if>
														 <c:if test="${ratedVoList.volume!=null}">${ratedVoList.volume}</c:if>
														</td>
														
													</tr>
													<%-- <tr class=" h55 bgc_f4f4f4 ">
														<td class="w260 bgc_f4f4f4"><fmt:formatDate value='${ratedVoList.created}' pattern='yyyy-MM-dd hh:mm:ss' /></td>
														<td class="w310 bgc_f4f4f4"></td>
														<td class="w320 bgc_f4f4f4"></td>
														<td class="w340 bgc_f4f4f4"></td>
													</tr> --%>
												 </c:forEach>
											  </c:if>
											</table>
	                    			</div>
	                    			 <div class="w1160 h24  font14 c_8493a8 m_b40 m_l160">
		                                
		                                <div class="f_r w470 h24 l_h22 font12">
		                                   
		                                    <c:forEach items="${pagination.pageView }" var="page">
												${page } 
								  			</c:forEach>
		                                    
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
	
	$(function(){
		//1,回显快速筛选勾选状态
		var filtrateTime =  $('#filtrateTime').val();
		if(filtrateTime =='最近7天'){
			$('#sx0').addClass('bgc_check_blue');
		}
		if(filtrateTime =='最近15天'){
			$('#sx1').addClass('bgc_check_blue');
		}
		if(filtrateTime =='最近一个月'){
			$('#sx2').addClass('bgc_check_blue');
		}
	    /* if(filtrateTime =='查询'){
			$('#sx3').addClass('bgc_check_blue');
		} */
	})
	
	$("#sx0").click(function(){
		$('#sx1').removeClass('bgc_check_blue');
		$('#sx2').removeClass('bgc_check_blue');
		$('#sx3').removeClass('bgc_check_blue');
	});
	$("#sx1").click(function(){
		$('#sx0').removeClass('bgc_check_blue');
		$('#sx2').removeClass('bgc_check_blue');
		$('#sx3').removeClass('bgc_check_blue');
	});
	$("#sx2").click(function(){
		$('#sx1').removeClass('bgc_check_blue');
		$('#sx0').removeClass('bgc_check_blue');
		$('#sx3').removeClass('bgc_check_blue');
	});
	$("#sx3").click(function(){
		$('#sx1').removeClass('bgc_check_blue');
		$('#sx0').removeClass('bgc_check_blue');
		$('#sx2').removeClass('bgc_check_blue');
	});
	
	//-------------------------------------------------
	//设置快速筛选条件
	function addFiltrateTime(data){
		//alert(data)
		//数据回显时使用
		 $('#filtrateTime').val(data);
		var filtrateTime = $('#filtrateTime').val();
		if(filtrateTime=='最近7天'){
			window.location.href="${ctx}/crms/appraise/middleAssessment?filtrateTime=最近7天";
		}else if(filtrateTime=='最近15天'){
			window.location.href="${ctx}/crms/appraise/middleAssessment?filtrateTime=最近15天";
		}else if(filtrateTime=='最近一个月'){
			window.location.href="${ctx}/crms/appraise/middleAssessment?filtrateTime=最近一个月";
		}else{
		    window.location.href="${ctx}/crms/appraise/middleAssessment?filtrateTime="+filtrateTime;
		}
		/* "${ctx}/crms/appraise/middleBadAssessment?filtrateTime=最近7天" */
		
		
	}
	
	
//-----------------------------------中评数曲线图-------------------------------------
   <%--  $(function(){
		
		var data  = <%=request.getAttribute("data")%>;
		var dateTime = data.dateTime;
		var dataValue = data.dataValue;
		//alert(dateTime)
		//alert(dataValue)
		
		 //曲线图
	    $('#container').highcharts({
	        title: {
	            text: '中评数',
	            x: 0 //center
	        },
	        subtitle: {
	            text: 'Middle Assessment',
	            x: 0
	        },
	        xAxis: {
	         /*   categories: ['1', '2', '3', '4', '5', '6',
	                '7', '8', '9', '10', '11', '12'],  */
	                //显示时间
	                categories :dateTime,
	            tickInterval:0
	        },
	        yAxis: {
	            title: {
	                text: '中评数 (个)'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: '条'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        /*  series: [{
	            name: '中评数',
	            data: [10, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
	        }, {
	            name: '差评数',
	            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
	        }]  */
	        //异步查询出的数据
	        series:dataValue
	    });
	}) --%>
</script>
</html>
