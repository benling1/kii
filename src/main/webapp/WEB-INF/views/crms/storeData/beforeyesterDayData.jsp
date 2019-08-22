<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
   <%@ include file="/common/common.jsp"%>
	<title>店铺数据-历史数据</title>
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
	<script type="text/javascript" src="${ctx}/crm/js/toady_data.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
		
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
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
								   	历史数据
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/storeData/operationLog">
								<div class="f_l w140 text-center cursor_">
									操作日志
								</div>
							</a>
						</div>
					</div>
					
					
					<!---------------当日数据--------------->
					<div class="m_b30">
						<!----------上部---------->
						<div class="w1605 h70 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								历史数据
							</div>
							<!---------------描述--------------->
							<div class="font14">
								查看店铺近三个月以内的订单交易信息。
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1605 bgc_fff p_t35 p_l40 p_b40 viphd_in">
							
							<!--------查询设置-------->
							<form action="${ctx}/crms/storeData/beforeyesterDayData" method="post">
							<input type="hidden" name="dataType" id="dataType" value="${dataType }"/>
							<div class="font14 c_384856 h52 lh52">
								<div class="f_l m_r15 c_384856">
										数据类型：
									</div>
									<div class="wrap f_l h50">
									    <div class="nice-select h50" name="nice-select" style="margin-right:50px;">
									    	<input class="h50" type="text" value="全部" readonly id="shopDate">
									    	<ul id="_ul1">
									    		<li value=""><span id="spanId0">全部</span></li>
									    		<c:forEach items="${params.shopDate}" var="shopData">
									    		  <li value="${shopData.primaryKey}"><span id="spanId${shopData.primaryKey}">${shopData.primaryValue }</span></li> 
									    		 <%-- <li>${shopDate1.dataType }</li> --%>
									    		</c:forEach>
									    	</ul>
									  	</div>
									</div>
								<%-- <div class="f_l m_r15 m_l40">
									日期:
								</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" id="tser01"  readonly onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})">
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
									
								<div class="f_l">~</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" id="tser02"  readonly onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})">
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div> --%>
								
								<input class="m_r45 w100 lh50 bgc_fff h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center  f_l cursor_" type="submit" value="查询">
								<!-- <div class="w100 h50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
									查询
								</div> -->
							</div>
							</form>
							
							
							<div class="h22 m_t30 m_b30">
								
								<div class="f_l data_change_out cursor_">
								<a href="${ctx}/crms/storeData/todyData">
							    	<div class="f_l one_check_only w20 h20 border_d9dde5 b_radius5">
							    		
							    		<img class="one_check_ w20 h20  display_none" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="c_384856 font14 h22 lh22 m_l10 f_l">
							    		昨日数据
							    	</div>
							    	</a>
								</div>
								
								<div class="f_l m_l45 data_change_out cursor_">
								<a href="${ctx}/crms/storeData/beforeyesterDayData">
							    	<div class="  f_l one_check_only w20 h20 border_d9dde5 b_radius5">
							    		<img class="one_check_ w20 h20" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="c_384856 font14 h22 lh22 m_l10 f_l">
							    		前日数据
							    	</div>
							    </a>	
								</div>
								
								<div class="f_l m_l45 data_change_out cursor_"> 
									<a href="${ctx}/crms/storeData/historyData">
							    	<div class="  f_l one_check_only w20 h20 border_d9dde5 b_radius5">
							    		<img class="one_check_ w20 h20 display_none" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="c_384856 font14 h22 lh22 m_l10 f_l">
							    		历史数据
							    	</div>
							    	</a>
								</div>
								
								
							</div>
							
							<div>
								
								
								<!--------详细数据-------->
								<div class="c_384856 text-center data_change_in">
									<table>
									  <tr class="">
									    <th class="w620">类别</th>
									    <th class="w620">刷新数据</th>
									  </tr>
									  <c:forEach items="${shopDataStatistics1}" var="shopData" varStatus="status">
									  <tr>
									  <%-- <c:when test="${shopData.dataType==15}">
									   <td>${shopData.dataType}</td>
									   <td><bean:write name="${shopData.changeQuantity}" format="0.00"/></td>
									  </c:when> --%>
									    <td>${shopData.dataType}</td>
									    <td><fmt:formatNumber value="${shopData.changeQuantity}" pattern="#.##" type="number" /><%-- ${shopData.changeQuantity} --%><%-- <bean:write name="${shopData.changeQuantity}" format="0.00"/> --%></td> 
									  </tr>
									  </c:forEach>
									</table>
								</div>
							
							
								
							
							</div>	
							
							
						</div>
					
						<!----------下部---------->
						<div class="w1620 bgc_fff p_t35 p_l40 p_b40 viphd_in display_none">
							
							<!--------查询设置-------->
							<div class="font14 c_384856 h52 lh52 m_b35">
								<div class="f_l m_r15">
									手机号码:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
								
								
								<div class="f_l m_r15">
									是否已读:
								</div>
								<div class="wrap f_l m_r40">
								    <div class="nice-select h50" name="nice-select">
								    	<input class="" type="text" value="" readonly>
								    	<ul>
								    		<li>是</li>
								    		<li>否</li>
								    	</ul>
								  	</div>
								</div>
								<div class="f_l m_r15">
									买家昵称:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
								
							</div>
							
							<div class="h52 lh52 m_b10">	
								<div class="f_l m_r15 c_384856">
									发送时间:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
								<div class="f_l">~</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r40" />
								
								<div class="f_l m_r15 c_384856">
									短信内容:
								</div>
								<div class="wrap f_l m_r20">
								    <div class="nice-select h50" name="nice-select">
								    	<input class="" type="text" value="" readonly>
								    	<ul>
								    		<li>包含</li>
								    		<li>不包含</li>
								    	</ul>
								  	</div>
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r40" />
								
							</div>
							
							<!--------导出-------->
							<div class="h50 p_r350 m_b10">
								<div class="w100 f_l h50 lh50 b_radius5 bgc_00a0e9 c_fff text-center cursor_">
										导出
								</div>
								<span class="f_r font12 c_384856">（可输入TD、T、N、等查询退订短信客户）</span>
							</div>
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="w150">买家昵称</th>
								    <th class="w150">手机号码</th>
								    <th class="w425">短信内容</th>
								    <th class="w150">发送时间</th>
								    <th class="w150">是否已读</th>
								    <th class="w100">最近更新日期</th>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center">sdgsdgsdfgsdf</td>
								    <td class="w150 text-center">13911112222</td>
								    <td class="w405 text-center p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
								    	</div>
								    </td>
								    <td class="w150 text-center">2016-12-12</td>
								    <td class="w100 text-center">是</td>
								    <td class="w150 text-center">2016-12-12</td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
								    	</div>
								    </td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w425 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w425 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w425 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w425 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w425 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								</table>
							</div>
							<!--------分页-------->
                            <div class="p_r430 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
                            </div>
							
						</div>
					
						<!----------下部---------->
						<div class="w1620 bgc_fff p_t35 p_l40 p_b40 viphd_in display_none">
							
							<!--------查询设置-------->
							<div class="font14 c_384856 h52 lh52 m_b35">
								<div class="f_l m_r15">
									手机号码:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
								
								
								<div class="f_l m_r15">
									发送状态:
								</div>
								<div class="wrap f_l m_r40">
								    <div class="nice-select h50" name="nice-select">
								    	<input class="" type="text" value="" readonly>
								    	<ul>
								    		<li>发送成功</li>
								    		<li>发送失败</li>
								    	</ul>
								  	</div>
								</div>
								<div class="f_l m_r15">
									卖家昵称:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r100" />
								
								<div class="w100 f_l h50 lh50 b_radius5 bgc_00a0e9 c_fff text-center cursor_">
									查询
								</div>
								
							</div>
							
							<div class="h52 lh52 m_b10">	
								<div class="f_l m_r15 c_384856">
									发送时间:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
								<div class="f_l">~</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r40" />
								
								
							</div>
							
							<!--------导出-------->
							<div class="h50 p_r350 m_b10">
								<div class="w100 f_l h50 lh50 b_radius5 bgc_00a0e9 c_fff text-center cursor_">
										导出
								</div>
							</div>
							<div class="c_384856 font14 m_b15">温馨提示：短信记录目前只保留2015年10月1日之后的记录。</div>
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="position_relative z_1 w65 p_l10 ">
								    	<div class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div class="p_l20">全选</div>
								    </th>
								    <th class="w60">序号</th>
								    <th class="w150">买家昵称</th>
								    <th class="w150">手机号码</th>
								    <th class="w425">短信内容</th>
								    <th class="w100">短信通道</th>
								    <th class="w90">发送状态</th>
								    <th class="w120">实际扣费(条)</th>
								    <th class="w130">发送时间</th>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">1</td>
								    <td class="w150 text-center">sdgsdgsdfgsdf</td>
								    <td class="w150 text-center">13911112222</td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
								    	</div>
								    </td>
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center">2</td>
								    <td class="w120 text-center">100</td>
								    <td class="w130 text-center">2016-12-15 18:30</td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">2</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
								    	</div>
								    </td>
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">3</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">4</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">5</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">6</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">7</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								</table>
							</div>
							<!--------分页-------->
                            <div class="p_r430 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
		
		var dataType = $("#dataType").val();
		if(dataType != ""){
			var li = $("#_ul1").children();
			for(var i=0;i<li.length;i++){
				if(li.eq(i).val()==(dataType)){
					var id = li.eq(i).val();
					var spanId = "spanId"+id;
					var spanVal = document.getElementById(spanId).innerText;
					$("#shopDate").val(spanVal);
				}
			}
		}
	})
	
	$(function(){
		var obj_lis = document.getElementById("_ul1").getElementsByTagName("li");
        for(i=0;i<obj_lis.length;i++){
            obj_lis[i].onclick = function(){
            	//alert($(this).val());
            	//$("._ul1").val($(this).val()==0?"":$(this).val());
            	$("#dataType").val($(this).val()==0?"":$(this).val());
            	
            }
        }
	})
</script>
</html>