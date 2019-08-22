<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
   <head>
	<title>店铺数据-操作日志</title>
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
	<script type="text/javascript" src="${ctx}/crm/js/operation_log.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
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
								<div class="f_l w140 text-center cursor_">
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
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									操作日志
								</div>
							</a>
						</div>
					</div>
					
				
					
					<!---------------操作日志--------------->
					<div class="">
						<!----------上部---------->
						<div class="w1605 h100 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								操作日志
							</div>
							<!---------------描述--------------->
							<div class="font14">
								可查看近三个月具体的操作明细(只显示2015-01-01以后数据)
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1605 bgc_fff p_t35 p_l40 p_b40 hmd_in">
							
							<!--------查询设置-------->
							<form action="${ctx}/crms/storeData/operationLog" method="post">
							<input type="hidden" name="functionGens" id="functionGens" value="${functionGens}"/>
							<div class="font14 c_384856 h47 lh45 m_b15">
								<div class="f_l m_r15">
									所属功能:
								</div>
								<div class="wrap f_l h45 lh45">
								
								    <div class="nice-select h45 lh45" name="nice-select">
								    	<input name="type" id="type" class="h45 lh45" type="text" value="${type==null?'全部':type}" readonly>
								    	
								    	<ul id="_ul1">
								    	<li><span id="spanId0">全部</span></li>
								    	 <c:forEach items="${params.functionGens1}" var="functionGens1">
								    		<li value="${functionGens1.primaryKey }" ><span id="spanId${functionGens1.primaryKey }">${functionGens1.primaryValue}</span></li>
								    	</c:forEach>
								    	
								    		<!-- <input type="hidden" value="${Value.primaryKey}" name="key"/> -->
								    	</ul>
								    	
								    	
								  	</div>
								  	
								  	
								</div>
								
								<div class="f_l m_r15 h45 lh45 m_l40">
									操作时间:
								</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" id="tser01"  readonly onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'yyyy-MM-dd hh:mm:ss',choosefun:function(elem, val) {valiteTwoTime();}})" 
									name="beginTime" value="<fmt:formatDate value='${beginTime }' pattern='yyyy-MM-dd' />" >
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
									
								<div class="f_l">~</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" id="tser02"  readonly onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'yyyy-MM-dd hh:mm:ss',choosefun:function(elem, val) {valiteTwoTime();}})"
									name="endTime" value="<fmt:formatDate value='${endTime }' pattern='yyyy-MM-dd' />" >
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								
								<input class="m_r45 w100 lh50 bgc_fff h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center  f_l cursor_" type="submit" value="查询">
								<!-- <div class="w100 h45 lh45 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
									查询
								</div> -->
							</div>
							</form>
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="w75">序号</th>
								    <th class="w185">操作人</th>
								    <th class="w150">操作功能</th>
								    <th class="w150">操作类型</th>
								    <th class="w150">操作说明</th>
								    <th class="w220">操作时间</th>
								    <th class="w160">状态</th>
								    <th class="w170">IP</th>
								  </tr>
								 
								  <c:forEach items="${pagination.list}" var="userOperationLog" varStatus="status">
									  <tr class="">
									    <td class="w75 text-center">${status.index+1}</td>
									    <td class="w185 text-center">${userOperationLog.userId ==null ?'系统管理员':userOperationLog.userId}</td>
									    <td class="w150 text-center">${userOperationLog.functionGens==null ?userOperationLog.type:userOperationLog.functionGens}</td>
									    <td class="w150 text-center">${userOperationLog.type}</td>
									    <td class="w150 text-center">${userOperationLog.remark}</td>
									    <td class="w220 text-center"><fmt:formatDate value="${userOperationLog.date}" pattern='yyyy-MM-dd HH:mm:ss' /></td>
									    <td class="w160 text-center">${userOperationLog.state}</td>
									    <td class="w170 text-center">${userOperationLog.ipAdd}</td>
									  </tr>
								 </c:forEach>
								</table>
							</div>
							
							
							<!--------分页-------->
                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                            <c:forEach items="${pagination.pageView }" var="page">
											${page } 
							    </c:forEach>
                            <!--     <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
                                </div> -->
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
		
		var functionGens = $("#functionGens").val();
		if(functionGens != ""){
			var li = $("#_ul1").children();
			for(var i=0;i<li.length;i++){
				if(li.eq(i).val()==(functionGens)){
					var id = li.eq(i).val();
					var spanId = "spanId"+id;
					var spanVal = document.getElementById(spanId).innerText;
					$("#type").val(spanVal);
				}
			}
		}
		
		/* if(functionGens == "1"){
			functionGens = "下单关怀";
			$("#type").val(functionGens);
		}else if(functionGens == "2"){
			functionGens = "常规催付";
			$("#type").val(functionGens);
		}else if(functionGens == "3"){
			functionGens = "二次催付";
			$("#type").val(functionGens);
		}else if(functionGens == "4"){
			functionGens = "聚划算催付";
			$("#type").val(functionGens);
		}else if(functionGens == "5"){
			functionGens = "预收催付";
			$("#type").val(functionGens);
		}else if(functionGens == "6"){
			functionGens = "发货提醒";
			$("#type").val(functionGens);
		}else if(functionGens == "7"){
			functionGens = "到达同城提醒";
			$("#type").val(functionGens);
		}else if(functionGens == "8"){
			functionGens = "派件提醒";
			$("#type").val(functionGens);
		}else if(functionGens == "9"){
			functionGens = "签收提醒";
			$("#type").val(functionGens);
		}else if(functionGens == "10"){
			functionGens = "疑难件提醒";
			$("#type").val(functionGens);
		}else if(functionGens == "11"){
			functionGens = "延时发货提醒";
			$("#type").val(functionGens);
		}else if(functionGens == "12"){
			functionGens = "宝贝关怀";
			$("#type").val(functionGens);
		}else if(functionGens == "13"){
			functionGens = "付款提醒";
			$("#type").val(functionGens);
		}else if(functionGens == "14"){
			functionGens = "回款提醒";
			$("#type").val(functionGens);
		}else if(functionGens == "15"){
			functionGens = "退款关怀";
			$("#type").val(functionGens);
		}else if(functionGens == "16"){
			functionGens = "自动评价";
			$("#type").val(functionGens);
		}else if(functionGens == "17"){
			functionGens = "批量评价";
			$("#type").val(functionGens);
		}else if(functionGens == "18"){
			functionGens = "评价记录";
			$("#type").val(functionGens);
		}else if(functionGens == "19"){
			functionGens = "中差管理";
			$("#type").val(functionGens);
		}else if(functionGens == "20"){
			functionGens = "中差评监控";
			$("#type").val(functionGens);
		}else if(functionGens == "21"){
			functionGens = "中差评安抚";
			$("#type").val(functionGens);
		}else if(functionGens == "22"){
			functionGens = "中差评统计";
			$("#type").val(functionGens);
		}else if(functionGens == "23"){
			functionGens = "中差评原因";
			$("#type").val(functionGens);
		}else if(functionGens == "24"){
			functionGens = "中差评原因设置";
			$("#type").val(functionGens);
		}else if(functionGens == "25"){
			functionGens = "中差评原因分析";
			$("#type").val(functionGens);
		}else if(functionGens == "26"){
			functionGens = "手动订单提醒";
			$("#type").val(functionGens);
		}else if(functionGens == "27"){
			functionGens = "优秀催付案例";
			$("#type").val(functionGens);
		}else if(functionGens == "28"){
			functionGens = "效果统计";
			$("#type").val(functionGens);
		}else if(functionGens == "29"){
			functionGens = "买家申请退款";
			$("#type").val(functionGens);
		}else if(functionGens == "30"){
			functionGens = "退款成功";
			$("#type").val(functionGens);
		}else if(functionGens == "31"){
			functionGens = "等待退货";
			$("#type").val(functionGens);
		}else if(functionGens == "32"){
			functionGens = "拒绝退款";
			$("#type").val(functionGens);
		}else if(functionGens == "33"){
			functionGens = "黑名单管理";
			$("#type").val(functionGens);
		}else{
			$("#type").val("全部");
		} */
	})
	
	$(function(){
		var obj_lis = document.getElementById("_ul1").getElementsByTagName("li");
        for(i=0;i<obj_lis.length;i++){
            obj_lis[i].onclick = function(){
            	//alert($(this).val());
            	//$("._ul1").val($(this).val()==0?"":$(this).val());
            	$("#functionGens").val($(this).val()==0?"":$(this).val());
            }

        }
	})
</script>
</html>