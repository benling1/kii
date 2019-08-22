<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>店铺数据-短信发送记录</title>
	<%@ include file="/common/common.jsp"%>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		
	<!--兼容360meta-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta name="renderer" content="webkit">

	
		
    <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
    <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

	<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js" ></script> --%>
	<script type="text/javascript" src="${ctx}/crm/js/text_history.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/util.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/crms/header/top.jsp"%>
	<%@ include file="/WEB-INF/views/crms/header/header.jsp"%>
	<div class="w1903 h100_">
			<!-------------------------左部，侧边栏 ------------------------->
			<div class="load_left"></div>
	
	
			<!-------------------------右部¨------------------------->
			<div class="w1703 m_l200">
				<!--------------------顶部导航栏 -------------------->
				<div class="load_top"></div>
				
				
				
				<!--------------------主要内容区-------------------->
				<div class="w1645  m_l30">
					<div class="w100_ h48 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							店铺数据
						</div>
						<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
							<a class="c_384856" href="${ctx}/crms/storeData/todyOrderList">
								<div class="f_l w140 text-center cursor_">
									订单列表
								</div>
							</a>
							<a class="c_384856" href="${ctx}/OrderHistoryImport/showLoadHistoryOrder">
								<div class="f_l w140 text-center cursor_">
									历史订单导入
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/shopData/smsSendRecord">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									短信发送记录
								</div>
							</a>
							<a class="c_384856" href="${ctx}/shopData/reportJsp">
								<div class="f_l w140 text-center cursor_">
									短信账单
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/storeData/todyData">
								<div class="f_l w140 text-center cursor_">
									历史数据
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/storeData/operationLog">
								<div class="f_l w140 text-center cursor_ ">
									操作日志
								</div>
							</a>
						</div>
					</div>
					
				
						
					<!---------------短信发送记录--------------->
					<div class="h940">
						<!----------上部---------->
						<div class="w1605 h130 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								短信发送记录
							</div>
							<!---------------描述--------------->
							<div class="font14">
								查询店铺短信发送记录
							</div>
							
							<div class="font16 c_384856 h50 lh50 m_t18">
							<%-- <a class="c_384856" href="${ctx}/crms/shopData/smsSendRecord"> --%>
								<div class="w140 h50 text-center f_l bgc_fff  cursor_">
									短信查询
								</div>
							<!-- </a> -->
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_in m_b30">
							
							<form id="scrchForm" action="${ctx}/crms/shopData/smsSendRecord/list" method="post">
						<!--------查询设置-------->
							<!-- 最近一天、全部列表 -->
							<input id="oneDayId" type="hidden" name="oneDay" value="${oneDay}"/>
							<div class="font14 c_384856 h45 lh45">
								<div class="f_l m_r15 h45 lh45">手机号码:</div>
								
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
								
								<div class="f_l m_r15">短信类型:</div>
								    <input type="hidden" id="type" name="type" value="${type}"/>
								<div class="wrap f_l h45 lh45 m_r65">
								    <div class="nice-select h45 lh45 z_3" name="nice-select">
								    	<input id="typeId" class="h45 lh45" type="text" value="全部" readonly/>
								    	<ul id="typeUl">
								    		<li value="0">全部</li>
								    		<li value="1">下单关怀</li>
								    		<li value="2">常规催付</li>
								    		<li value="3">二次催付</li>
								    		<li value="4">聚划算催付</li>
								    		<!-- <li value="5">预售催付</li> -->
								    		<li value="6">发货提醒</li>
								    		<li value="7">到达同城提醒</li>
								    		<li value="8">派件提醒</li>
								    		<li value="9">签收提醒</li>
<!-- 								    		<li value="10">疑难件提醒</li> -->
								    		<li value="11">延时发货提醒</li>
								    		<li value="12">宝贝关怀</li>
								    		<li value="13">付款关怀</li>
								    		<li value="14">回款提醒</li>
<!-- 								    		<li value="15">退款关怀</li> -->
<!-- 								    		<li value="16">自动评价</li> -->
<!-- 								    		<li value="17">批量评价</li> -->
<!-- 								    		<li value="18">评价记录</li> -->
								    		<li value="19">中差评查看</li>
								    		<!-- <li value="20">中差评监控</li> -->
								    		<li value="21">中差评安抚</li>
								    		<!-- <li value="22">中差评统计</li> -->
								    		<!-- <li value="23">中差评原因</li> -->
								    		<!-- <li value="24">中差评原因设置</li>
								    		<li value="25">中差评原因分析</li> -->
<!-- 								    		<li value="26">手动订单提醒</li> -->
								    		<!-- <li value="27">优秀催付案例</li> -->
								    		<!-- <li value="28">效果统计</li> -->
								    		<li value="29">申请退款提醒</li>
								    		<li value="30">退款成功</li>
								    		<li value="31">同意退款</li>
								    		<li value="32">拒绝退款</li>
								    		<li value="33">会员短信群发</li>
								    		<li value="34">指定号码发送</li>
								    		<li value="35">订单短信群发</li>
<!-- 								    		<li value="36">会员互动</li> -->
								    		<li value="37">提醒好评</li>
								    	</ul>
								  	</div>
								</div>
								
								<div >
<!-- 									<input type="button" value="查询" class="w100 h45 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_ bgc_fff"> -->
									<a id="chaxun" class="w100 h45 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_ bgc_fff" href="javascript:;">查询</a>
								</div>
							</div>
							<div class="font14 c_384856 h50 lh50 m_t20 m_b20">
								<div class="f_l m_r15">买家昵称:</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" type="text" name="buyerNick" id="buyerNick" value="${buyerNick}"/>
								<input name="dateType" value="${dateType}" type="hidden">
								
								<div class="f_l m_r15">发送时间:</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" value="${beginTime}" id="tser01" name="beginTime" readonly onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val) {valiteTwoTime();}})">
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
									
								<div class="f_l">~</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" value="${endTime}" id="tser02" name="endTime" readonly onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss',choosefun:function(elem, val) {valiteTwoTime();}})">
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								
								
							</div>
							</form>
							<!--------操作选项-------->
							<div class="font14 h52 m_b15">
								
								<div>
<%-- 									<a href="${ctx}/crms/shopData/smsSendRecord/report" class="m_r70 w100 lh52 h52 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">导出</a> --%>
									<a href="#" class="m_r70 w100 lh52 h52 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_" onclick="downloadDate();">导出</a>
								</div>
								<div class="f_l m_r60 m_t16" >
							    	<div id="oneDayImg" class="cursor_ one_check_only w20 h20 border_d9dde5 GXK b_radius5 f_l">
							    		<input id="hidId" type="hidden" value="${showOneDay}"/>
							    		<img class="cursor_  w20 h20 one_check_" src="${ctx}/crm/images/black_check.png"/>
							    	</div>
							    	<div class="m_l10 f_l font14 c_384856">
							    		近一天
							    	</div>
								</div>
								<div class="f_l m_r60 m_t16">
							    	<div id="history" class="cursor_ one_check_only w20 h20 border_d9dde5 GXK b_radius5 f_l">
							    		<img class="cursor_ w20 h20  one_check_ display_none" src="${ctx}/crm/images/black_check.png"/>
							    	</div>
							    	<div class="m_l10 f_l font14 c_384856">
							    		历史记录
							    	</div>
								</div>
								
							</div>
<!-- 							<p class="m_b15 c_ff6363 font14"> -->
<!-- 								温馨提示：短信记录目前只保留2015年10月1日之后的记录。 -->
<!-- 							</p> -->
							
							
							<!--------详细数据-------->
							<div class="c_384856 font14" style="overflow:hidden;">
								<table  style="margin-top:30px;">
								  <tr class="">
								    <th class="w75">序号</th>
								    <th class="w115">手机号码</th>
								    <th class="w350">短信内容</th>
								    <th class="w195">短信通道</th>
								    <th class="w85">短信类型</th>
								    <th class="w125">买家昵称</th>
								    <th class="w100">发送状态</th>
								    <th class="w100">实际扣费(条)</th>
								    <th class="w140">发送时间</th>
								  </tr>
								 <!-- 页面列表序号 -->
								 <%int i = 1; %>
								 <c:if test="${pagination == null}">
									 	<tr>
										     <td align="center" colspan="9">暂时没有相关数据!</td>
									    </tr>
								 </c:if>
								 <c:if test="${pagination.list.size() > 0}">
								    <c:forEach items="${pagination.list}" var="sendRecord">
								  <tr class="">
								    	<td class="w75 text-center"><%=i++ %></td>
									    <td class="w115 text-center">${sendRecord.recNum}</td>
									    <td class="w330 text-center p_l10 p_r10">
									    	<div style="word-break: break-all;" class="w330 margin0_auto h50 lh50 one_line_only text_detail">
												${sendRecord.content}
									    	</div>
									    </td>
									    <td class="w195 text-center">
									    	<c:choose>
									    		<c:when test="${sendRecord.channel eq 'JHS'}">
									    			聚划算
									    		</c:when>
									    		<c:when test="${sendRecord.channel eq 'TAOBAO'}">
									    			淘宝
									    		</c:when>
									    		<c:when test="${sendRecord.channel eq 'WAP,WAP'}">
									    			无线
									    		</c:when>
									    		<c:otherwise>
									    			${sendRecord.channel}
									    		</c:otherwise>
									    	</c:choose>
									    </td>
									    <td class="w85 text-center">
									    	<c:if test="${sendRecord.type=='1'}">下单关怀</c:if>
									    	<c:if test="${sendRecord.type=='2'}">常规催付</c:if>
									    	<c:if test="${sendRecord.type=='3'}">二次催付</c:if>
									    	<c:if test="${sendRecord.type=='4'}">聚划算催付</c:if>
									    	<c:if test="${sendRecord.type=='5'}">预售催付</c:if>
									    	<c:if test="${sendRecord.type=='6'}">发货提醒</c:if>
									    	<c:if test="${sendRecord.type=='7'}">到达同城提醒</c:if>
									    	<c:if test="${sendRecord.type=='8'}">派件提醒</c:if>
									    	<c:if test="${sendRecord.type=='9'}">签收提醒</c:if>
									    	<c:if test="${sendRecord.type=='10'}">疑难件提醒</c:if>
									    	<c:if test="${sendRecord.type=='11'}">延时发货提醒</c:if>
									    	<c:if test="${sendRecord.type=='12'}">宝贝关怀</c:if>
									    	<c:if test="${sendRecord.type=='13'}">付款关怀</c:if>
									    	<c:if test="${sendRecord.type=='14'}">回款提醒</c:if>
									    	<c:if test="${sendRecord.type=='15'}">退款关怀</c:if>
									    	<c:if test="${sendRecord.type=='18'}">评价记录</c:if>
									    	<c:if test="${sendRecord.type=='19'}">中差评查看</c:if>
									    	<c:if test="${sendRecord.type=='20'}">中差评监控</c:if>
									    	<c:if test="${sendRecord.type=='21'}">中差评安抚</c:if>
									    	<c:if test="${sendRecord.type=='22'}">中差评统计</c:if>
									    	<c:if test="${sendRecord.type=='23'}">中差评原因</c:if>
									    	<c:if test="${sendRecord.type=='24'}">中差评原因设置</c:if>
									    	<c:if test="${sendRecord.type=='25'}">中差评原因分析</c:if>
									    	<c:if test="${sendRecord.type=='26'}">手动订单提醒</c:if>
									    	<c:if test="${sendRecord.type=='27'}">优秀催付案例</c:if>
									    	<c:if test="${sendRecord.type=='28'}">效果统计</c:if>
									    	<c:if test="${sendRecord.type=='29'}">申请退款提醒</c:if>
									    	<c:if test="${sendRecord.type=='30'}">退款成功</c:if>
									    	<c:if test="${sendRecord.type=='31'}">同意退款</c:if>
									    	<c:if test="${sendRecord.type=='32'}">拒绝退款</c:if>
									    	<c:if test="${sendRecord.type=='33'}">会员短信群发</c:if>
									    	<c:if test="${sendRecord.type=='34'}">指定号码群发</c:if>
									    	<c:if test="${sendRecord.type=='35'}">订单短信群发</c:if>
									    	<c:if test="${sendRecord.type=='36'}">会员互动</c:if>
									    	<c:if test="${sendRecord.type=='37'}">提醒好评</c:if>
									    	<c:if test="${sendRecord.type=='99'}">测试发送</c:if>
									    </td>
									    <td class="w125 text-center">${sendRecord.buyerNick}</td>
									    <td class="w100 text-center">
									    	<c:if test="${sendRecord.status==1}">发送失败</c:if>
									    	<c:if test="${sendRecord.status==2}">发送成功</c:if>
									    	<c:if test="${sendRecord.status==3}">手机号码不正确</c:if>
									    	<c:if test="${sendRecord.status==4}">号码重复</c:if>
									    	<c:if test="${sendRecord.status==5}">黑名单</c:if>
									    	<c:if test="${sendRecord.status==6}">重复被屏蔽 /重复发送</c:if>
									    </td>
									    <td class="w100 text-center">${sendRecord.actualDeduction}</td>
									    <td class="w140 text-center"><fmt:formatDate value="${sendRecord.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								  </tr>
						    		  </c:forEach>
								  <tr>
								     <th class="w125">共计</th>
								    <th class="w120" colspan="4">发送成功${totalSms}条短信</th>
								    <th class="w120" colspan="4">实际扣费${deductSms}条</th>
								  </tr>
					    		</c:if>
					    		<c:if test="${pagination.list.size() == 0}">
					    			<tr class="">
					    				<td class="w75 text-center" colspan="9">暂时没有相关数据！</td>
								    	<td class="w115" colspan="9"></td>
								    	<td class="w350" colspan="9"></td>
								    	<td class="w195" colspan="9"></td>
								    	<td class="w85" colspan="9"></td>
								    	<td class="w125" colspan="9"></td>
								    	<td class="w100" colspan="9"></td>
								    	<td class="w100" colspan="9"></td>
								    	<td class="w140" colspan="9"></td>
								    </tr>
					    		</c:if>
								</table>
								<input id="hasData" value="${hasData}" type="hidden">
							</div>
							
							
							<!--------分页-------->
                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_r h24 l_h22 font12">
                                    <c:if test="${pagination.list.size() > 0}">
                                    	<c:forEach items="${pagination.pageView}" var="page">
											${page}
								 		</c:forEach>
                                    </c:if>
                                </div>
                            </div>
							
						</div>
					
						
					</div>
					
				
				</div>	
				
				</div>
		
			</div>
	
	</div>

	
				
</body>
<script type="text/javascript">
	//设置勾选框
	$(".GXK").click(function(){
		$(this).addClass("bgc_check_blue")
		$(this).parent().siblings().children(".GXK").removeClass("bgc_check_blue")	
	})
	//发送状态
	$('#statusUl li').click(function(){
		$('#status').val($(this).val());
	});
	//短信类型
	$('#typeUl li').click(function(){
		$('#type').val($(this).val());
	});
	//判断是否选中近一天，如果选中赋值给hidden，传递到后台，查找近一天的数据
	$('#oneDayImg').click(function(){
		$('#oneDayId').val("1");
	});
	$('#history').click(function(){
		$('#oneDayId').val("2");
	});
	//设置近一天勾选框的显示
	$(function(){
		var showOne = $('#hidId').val();
		if(showOne == "1"){
			$('#oneDayImg').children('img').show();
			$('#history').children('img').hide();
			$('#oneDayId').val("1");
		}else if(showOne == "2"){
			$('#oneDayImg').children('img').hide();
			$('#history').children('img').show();
			$('#oneDayId').val("2");
		}
		$('#chaxun').click(function(){
			var phoneNum = $("#recNum").val();
			if(phoneNum==null ||phoneNum==""){
				//confirm('手机号不能为空!');
				$('#scrchForm').submit();
			}else if(!(/^1[34578]\d{9}$/.test(phoneNum))){ 
				$("#recNum").val("");
		    	confirm("请输入正确手机号!");
		    }else{
		    	$('#scrchForm').submit();
		    }
		});
		//判断手机号格式正确与否
// 		$("#recNum").on("change",function(){
// 			var phoneNum = $("#recNum").val();
// 			if(phoneNum==null ||phoneNum==""){
// 				//confirm('手机号不能为空!');
// 			}else if(!(/^1[34578]\d{9}$/.test(phoneNum))){ 
// 				$("#recNum").val("");
// 		    	confirm("请输入正确手机号!");
// 		    }
// 		});
		
		//短信类型回显
		var typeVal = $('#type').val();
		if(typeVal == '1'){
			$('#typeId').val("下单关怀");
		}else if(typeVal == '2'){
			$('#typeId').val("常规催付");
		}else if(typeVal == '3'){
			$('#typeId').val("二次催付");
		}else if(typeVal == '4'){
			$('#typeId').val("聚划算催付");
		}else if(typeVal == '5'){
			$('#typeId').val("预售催付");
		}else if(typeVal == '6'){
			$('#typeId').val("发货提醒");
		}else if(typeVal == '7'){
			$('#typeId').val("到达同城提醒");
		}else if(typeVal == '8'){
			$('#typeId').val("派件提醒");
		}else if(typeVal == '9'){
			$('#typeId').val("签收提醒");
		}else if(typeVal == '10'){
			$('#typeId').val("疑难件提醒");
		}else if(typeVal == '11'){
			$('#typeId').val("延时发货提醒");
		}else if(typeVal == '12'){
			$('#typeId').val("宝贝关怀");
		}else if(typeVal == '13'){
			$('#typeId').val("付款关怀");
		}else if(typeVal == '14'){
			$('#typeId').val("回款提醒");
		}else if(typeVal == '15'){
			$('#typeId').val("退款关怀");
		}else if(typeVal == '18'){
			$('#typeId').val("评价记录");
		}else if(typeVal == '19'){
			$('#typeId').val("中差评查看");
		}else if(typeVal == '20'){
			$('#typeId').val("中差评监控");
		}else if(typeVal == '21'){
			$('#typeId').val("中差评安抚");
		}else if(typeVal == '22'){
			$('#typeId').val("中差评统计");
		}else if(typeVal == '23'){
			$('#typeId').val("中差评原因");
		}else if(typeVal == '24'){
			$('#typeId').val("中差评原因设置");
		}else if(typeVal == '25'){
			$('#typeId').val("中差评原因分析");
		}else if(typeVal == '26'){
			$('#typeId').val("手动订单提醒");
		}else if(typeVal == '27'){
			$('#typeId').val("优秀催付案例");
		}else if(typeVal == '28'){
			$('#typeId').val("效果统计");
		}else if(typeVal == '29'){
			$('#typeId').val("申请退款提醒");
		}else if(typeVal == '30'){
			$('#typeId').val("退款成功");
		}else if(typeVal == '31'){
			$('#typeId').val("同意退款");
		}else if(typeVal == '32'){
			$('#typeId').val("拒绝退款");
		}else if(typeVal == '33'){
			$('#typeId').val("会员短信群发");
		}else if(typeVal == '34'){
			$('#typeId').val("指定号码发送");
		}else if(typeVal == '35'){
			$('#typeId').val("订单短信群发");
		}else if(typeVal == '36'){
			$('#typeId').val("会员互动");
		}else if(typeVal == '37'){
			$('#typeId').val("提醒好评");
		}else{
			$('#typeId').val("全部");
		};
		
		//发送状态回显
		var staVal = $('#status').val();
		if(staVal == '0'){
			$('#statusId').val("全部");
		}else if(staVal == '1'){
			$('#statusId').val("发送失败");
		}else if(staVal == '2'){
			$('#statusId').val("发送成功");
		};
		//导出时是否有数据
		var hasData = $('#hasData').val();
		if(hasData == '1'){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("暂时没有相关数据,请不要进行导出操作！");
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000);
		}
	});
	
	function downloadDate(){
		var recNum = $("#recNum").val();
		var statusId = $("#statusId").val();
		var typeId = $("#typeId").val();
		var buyerNick = $("#buyerNick").val();
		var bTime = $("#tser01").val();
		var eTime = $("#tser02").val();
		var oneDayId = $("#oneDayId").val();
		if(statusId=="发送成功"){
			statusId=2;
		}else if(statusId=="发送失败"){
			statusId=1;
		}
		window.location.href = "${ctx}/crms/shopData/smsSendRecord/report?recNum="+recNum+"&status="+statusId+"&type="+typeId+"&buyerNick="+buyerNick+"&beginTime="+bTime+"&endTime="+eTime+"&oneDayId="+oneDayId;
	}
</script>
</html>