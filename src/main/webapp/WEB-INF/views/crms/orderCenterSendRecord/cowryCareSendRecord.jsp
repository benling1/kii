<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>发送记录</title>
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

	    <script type="text/javascript" src="${ctx}/crm/js/model.js" ></script>
        <script type="text/javascript" src="${ctx}/crm/js/chakanfasongjilu.js"></script>
        <script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js"></script>
		<script type="text/javascript" src="${ctx}/crm/js/util.js" ></script>

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
							<li class="f_l w140 text-center bgc_f1f3f7"><a
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
							<li style="clear:both;"></li>
						</ul>
					</div>
					</div>
                    <div class="w1620 bgc_f1f3f7 c_384856 p_l40">
                        <div class=" p_t27">
                            <p class="font20" id="HEADER">发送记录</p>
                            <p class="font14 p_t8">可针对不同商品编排不同短信内容，在宝贝签收时自动发送关怀短信，如宝贝使用说明等</p>
                            <div class="h40">
	                         	<div class="w110 h40 l_h40 f_r bgc_fff m_r15 text-center  cursor_ ZhanKai">
	                             	<img class="m_r10 JTX display_none" src="${ctx}/crm/images/箭头下.png"/>
	                             	<img class="m_r10 JTS" src="${ctx}/crm/images/箭头上.png"/>
	                                <span class="LiuChengText">收回流程</span>
	                            </div>
                         	</div>
                        </div>
                         
                        

                    </div>
                    <%@ include file="/WEB-INF/views/crms/header/liucheng.jsp" %>
					<div class="bgc_f1f3f7" style="width: 84.5vw;padding-left: 2vw;"> 
						<ul class="h40 font14" style="padding-top: 3vw;">
							<a href="${ctx}/cowryCare/queryCowryCare" class="c_384856"> <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">宝贝关怀</li></a>
	                        <a href="#" class="c_384856"  onclick="findOrderSendRedord('formId')"><li class="f_l w140 text-center h40 l_h40 bgc_fff cursor_ ">发送记录</li></a>
	                        <div style=""> </div>
	                    </ul>
                    </div>
                    
                    
                    
                    
 <!----------------------------------------------------------------------------------------       查看发送列表 start       ------------------------------------------------------>
                    <div class="w1620 bgc_fff p_t35 p_l40 p_b40 text_in m_b30">
                    	<div class="h103 lh103 w1280 p_l40 ">
                    	  <form id="formId" action="${ctx}/sendRecord/orderCenterRecord" method="post">
                        	<div class="font14 c_384856 h45 lh45">
								<div class="f_l m_r15 h45 lh45">
									手机号码:
								</div>
								<input name="recordFlag" value="4" type="hidden">
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" type="text" name="recNum" value="${recNum}" id="recNum"/>
								
								<div class="f_l m_r15">发送状态:</div>
								    	<input id="status" type="hidden" name="status" value="${status}"/>
								<div class="wrap f_l h45 lh45 m_r70">
								    <div class="nice-select h45 lh45 z_3" name="nice-select">
								    	<input id="statusId" class="h45 lh45" type="text" value="全部" readonly>
								    	<ul id="statusUl">
								    		<li value="0">全部</li>
								    		<li value="2">发送成功</li>
								    		<li value="1">发送失败</li>
								    	</ul>
								  	</div>
								</div>
								
								<%-- <div class="f_l m_r15">
									短信类型:
								</div>
								    <input type="hidden" id="type" name="type" value="${type}">
								<div class="wrap f_l h45 lh45 m_r65">
								    <div class="nice-select h45 lh45 z_3" name="nice-select">
								    	<input id="typeId" class="h45 lh45" type="text" value="全部" readonly/>
								    	<ul id="typeUl">
								    		<li value="0">全部</li>
								    		<li value="12">宝贝关怀</li>
								    	</ul>
								  	</div>
								</div> --%>
								
								
							</div>
							<div class="font14 c_384856 h50 lh50 m_t20 m_b20">
								<div class="f_l m_r15">
									买家昵称:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" type="text" name="buyerNick" value="${buyerNick}"/>
								
								
								<div class="f_l m_r15">
									发送时间:
								</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" value="${beginTime}" id="tser01" name="beginTime" readonly onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val) {valiteTwoTime();}})">
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
									
								<div class="f_l">~</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" value="${endTime}" id="tser02" name="endTime" readonly onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val) {valiteTwoTime();}})">
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								<div class="f_r bgc_00a0e9 w106 h40 l_h40 text-center b_radius5 c_fff cursor_" onclick="findOrderSendRedord('formId')" style="margin-right:6.9vw">查询</div>
								
							</div>
						  </form>
                        </div>
                        <div class="c_384856 p_l42">
								<table class="w1280 TTAABLE m_t30">
								  <tr class="h50 bgc_f1f3f7">
								    <td class="w105 text-center">买家昵称</td>
								    <td class="w215 text-center">订单号</td>
								    <td class="w125 text-center">手机号</td>
								    <td class="w110 text-center">发送时间</td>
                                    <td class="w510 text-center">短信内容</td>
                                    <td class="w110 text-center">状态</td>
                                    <td class="w100 text-center">计费（条）</td>
								  </tr>
								  <c:choose>
								  	  <c:when test="${pagination == null}">
								  	  	<tr class="h50 bgc_f1f3f7">
										    <td class="w105 text-center" colspan="7">暂时没有相关数据！</td>
								  		</tr>
								  	  </c:when>
									  <c:when test="${pagination.list.size() > 0}">
									  	<c:forEach items="${pagination.list}" var="sendRecord">
									  		<tr class="h50 rgb250">
											    <td class="w105 text-center">${sendRecord.buyerNick}</td>
											    <td class="w215 text-center">${sendRecord.orderId}</td>
											    <td class="w125 text-center">${sendRecord.recNum}</td>
											    <td class="w110 text-center">
											    	<fmt:formatDate value="${sendRecord.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
											    </td>
			                                    <td class="w510 text-center">
			                                    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
			                                    		<span>${sendRecord.content}</span>
			                                    	</div>
			                                    </td>
			                                    <td class="w110 text-center">
			                                    	<c:if test="${sendRecord.status == 1}">发送失败</c:if>
			                                    	<c:if test="${sendRecord.status == 2}">发送成功</c:if>
			                                    </td>
			                                    <td class="w100 text-center">
			                                    	<c:choose>
			                                    		<c:when test="${sendRecord.actualDeduction != null}">
			                                    			${sendRecord.actualDeduction}
			                                    		</c:when>
			                                    		<c:otherwise>
			                                    			0
			                                    		</c:otherwise>
			                                    	</c:choose>
			                                    </td>
									  		</tr>
									  	</c:forEach>
									  </c:when>
									  <c:otherwise>
									  	<tr class="h50 bgc_f1f3f7">
										    <td class="w105 text-center" colspan="7">暂时没有相关数据！</td>
									  	</tr>
									  </c:otherwise>
								  </c:choose>
                                  <!-- <tr class="h50 rgb244">
								    <td class="w105 text-center"></td>
								    <td class="w215 text-center">
                                    	<input class="w230 border0 rgb250 p_l20 text-center HuoDongMingCheng" type="text" value=""/>
                                        <img class="f_r m_r15 BianJi" src="images/bianji.png"/>
                                    </td>
								    <td class="w125 text-center"></td>
								    <td class="w110 text-center"></td>
                                    <td class="w510 text-center"></td>
                                    <td class="w110 text-center"></td>
                                    <td class="w100 text-center"></th>
								  </tr> -->
								</table>
							</div>
                            
                            <div class="w1280 h24 m_t22 p_l42 font14 c_8493a8 p_b65">
                                <div class="f_r w470 h24 l_h22 font12">
                                <c:choose>
                                	<c:when test="${pagination == null}">
                                		
                                	</c:when>
                                	<c:when test="${pagination.list.size() > 0}">
                                		<c:forEach items="${pagination.pageView}" var="page">
		                                 		${page}
	                                 	</c:forEach>
                                	</c:when>
                                	<c:otherwise>
                                		
                                	</c:otherwise>
                                </c:choose>
                                </div>
                            </div>
 					</div>
<!----------------------------------------------------------------------------------------        物流提醒 end                   ----------------------------------------------------------------------------------------------->




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
	   
	   //手机号码输入框汉字判断
	   $('#recNum').keyup(function(){
		  var numVal =  $(this).val();
		  if(isNaN(numVal)){
			  	$(".tishi_2").show();
				$(".tishi_2").children("p").text("手机号查找只能输入数字");
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000);
			  $('#recNum').val('');
		  }
	   })
	   
	   //提交form表单
   		function findOrderSendRedord(formId){
   			document.getElementById(formId).submit();
   		};
   		
	   //短信类型的选择
	   	$('#typeUl li').click(function(){
			$('#type').val($(this).val());
		});
	   
	   //发送状态的选择
   		$('#statusUl li').click(function(){
   			$('#status').val($(this).val());
   		});
	   
	   
	   $(function(){
  			var statusVal = $('#status').val();
  			if(statusVal == '1'){
  				$('#statusId').val("发送失败");
			}else if(statusVal == '2'){
				$('#statusId').val("发送成功");
			}else{
				$('#statusId').val("全部");
			}
	   })
	   //切换短信详情
	   $(".text_detail").click(function(){
			$(this).toggleClass("one_line_only lh50 h50");
		});
	   
   </script>
</html>
