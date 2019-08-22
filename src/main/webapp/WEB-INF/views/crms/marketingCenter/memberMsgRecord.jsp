<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>营销中心-会员短信群发效果分析</title>
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
				<div class="w1645 m_l30">
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
								<div class="f_l w200 text-center cursor__ bgc_e3e7f0">
									会员短信群发效果分析
								</div>
							</a>
							<a class="c_384856" href="${ctx}/appointNumber/msgSendRecord">
								<div class="f_l w200 text-center cursor__">
									指定号码发送效果分析
								</div>
							</a>
							<a class="c_384856" href="${ctx}/order/msgSendRecord">
								<div class="f_l w200 text-center cursor__">
									订单短信群发效果分析
								</div>
							</a>	
						</div>
						<div class="clear"></div>
					</div>
					
					
					<!---------------会员短信群发--------------->
					<div class="">
						<!----------上部---------->
						<div class="w1605 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!-- -------------标题------------- -->
							<div class="font24 m_b10">
								营销活动效果分析
							</div>
							<!-- -------------描述------------- -->
							<div class="font14 m_b20">
								帮助商家更好的了解短信发送的效果，以及根据效果制定新的营销活动
							</div>
							
						</div>
				
		<!----------下部---------->
				<a href="${ctx}/member/msgSendRecord">
					<div class="font16 f_l h50 lh50 bgc_fff out c_384856 w140 h50 cursor_ one text-center">
						会员短信群发列表
					</div>
				</a>
				<div class="h50 lh50 f_l display_none hide_this">
					<!-- <div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
						活动名称
					</div> -->
					<a href="${ctx}/memberEffectIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
							效果分析
						</div>
					</a>
					<a href="${ctx}/memberCustomerIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
							客户详情
						</div>
					</a>
					<a href="${ctx}/memberItemIndex">
						<div class="font16 f_l out c_384856 w140 h50 two bgc_e3e7f0 cursor_ text-center">
							商品详情
						</div>
					</a>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
					
					
					
					
				
				<div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in position_relative four">
					
					<div style="line-height: 2.5vw;">
						<form id="formId" action="${ctx}/member/msgSendRecord" method="post">
							<div class="f_l"style="margin-right: 1vw;">
								发送时间：
							</div>
							<div class="f_l position_relative">
								<input style="border: 1px solid #cad3df; border-radius:0.5vw;" name="bTime" class="bgc_fff w230 p_l10 h50 m_r10" value="${bTime}" readonly="true" type="text" id="tser31" >
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
									<td class=" h60 text-center" colspan="6">没有数据</td>
								</c:when>
								<c:otherwise>
									<td class=" h60 text-center" colspan="6">没有数据</td>
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
				
				<!-- <div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in position_relative three display_none">
					<p class="font18" style="padding-bottom: 1vw;">活动内容</p>
					
					<ul style="padding-left: 3vw;line-height: 2vw;">
					
					
						<li>
							<ul>
								<li class="f_l">
									活动名称：
								</li>
								<li class="f_l" style="width: 15vw;">
									会员短信群发
								</li>
								<li class="f_l" style="width: 15vw;">
									发送时间：28923579345
								</li>
								<li class="f_l" style="width: 15vw;">
									任务状态：全部成功
								</li>
								<li class="clear"></li>
							</ul>
						</li>
						<li>
							<ul>
								<li class="f_l">会员筛选：</li>
								<li class="f_l">
									<ul>
										<li class="f_l">
											<ul style="width: 15vw;">
												<li>
													会员分组【全部分组】
												</li>
												<li>
													订单来源【不限】
												</li>
												<li>
													过滤条件【不限】
												</li>
											</ul>
										</li>
										<li class="f_l">
											<ul style="width: 15vw;">
												<li>
													交易时间【不限】
												</li>
												<li>
													交易次数【不限】
												</li>
												<li>
													指定商品【不限】
												</li>
											</ul>
										</li>
										<li class="f_l">
											<ul style="width: 15vw;">
												<li>
													平均客单价【不限】
												</li>
												<li>
													已发送过滤【不限】
												</li>
												<li>
													累计消费金额【不限】
												</li>
											</ul>
										</li>
										<li class="f_l">
											<ul style="width: 15vw;">
												<li>
													卖家标记【不限】
												</li>
												<li>
													地区筛选【不限】
												</li>
											</ul>
										</li>
										<li class="clear"></li>
									</ul>
								</li>
								<li class="clear"></li>
							</ul>
						</li>
						<li>
							短信内容：UASKD规划开发商的过户费接口的规划if肯定很高i咖啡还是得赶快发货单据港口学费和妒忌非客户
						</li>
					
					</ul>
					
				</div> -->
				
				<div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in position_relative three display_none">
					<p>效果分析</p>
					
					
					<div style="line-height: 2.5vw;">
						<div class="f_l">
							订单来源：
						</div>
						<div class="wrap f_l h50 lh50 w140"style="margin: 0 1vw;">
							<div class="nice-select h50 lh50 w117 z_3"
								name="nice-select">
								<input readonly="readonly"
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="全部">
								<ul>
									<li value="1">全部</li>
									<li value="7">手机端</li>
									<li value="15">PC端</li>
								</ul>
							</div>
						</div>
						<div class="cursor_ w20 h20 border_d9dde5 b_radius5 f_l set_time" style="margin-left: 1.5vw;margin-top: 0.7vw;"></div>
						<div class="f_l"style="margin-left: 1vw;margin-right: 1vw;">自定义</div>
						<div class="f_l set_this_time display_none">
							<div class="f_l position_relative">
								<input style="border: 1px solid #cad3df;" name="bTime" class="bgc_fff w230 p_l10 h50 m_r10" value="${bTime}" type="text" id="tser50" readonly="true" onclick="$.jeDate('#tser50',{trigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
								<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png"/>
							</div>
							<div class="f_l lh50">~</div>
							<div class="f_l position_relative">
								<input style="border: 1px solid #cad3df;" name="eTime" class="bgc_fff border0 w230 p_l10 h50 m_l10 m_r40" value="${eTime}" type="text" id="tser51" readonly="true" onclick="$.jeDate('#tser51',{trigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
								<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png"/>
							</div>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
					</div>
					
					
					
					<table border="0" class="font14 item_table" style="margin-top:3.5vw;">
						<tr class="bgc_e3e7f0">
							<th style="width: 12vw;" class=" h60 text-center">短信群发记录</th>
							<th style="width: 12vw;" class=" h60 text-center">下单客户</th>
							<th style="width: 10vw;" class=" h60 text-center">成交客户</th>
							<th style="width: 14vw;" class=" h60 text-center">订单未付款客户<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" /></th>
							<th style="width: 12vw;" class=" h60 text-center">退款客户</th>
						</tr>
						<tr>
							<td class=" h60 text-center">群发客户数：${totalSmsCustomer}</td>
							<td class=" h60 text-center">下单客户数：${totalOrderCustomer}</td>
							<td class=" h60 text-center">成交客户数：${successCustomer}</td>
							<td class=" h60 text-center">订单未付款客户：${waitCustomer}</td>
							<td class=" h60 text-center">退款客户数：${failCustomer}</td>
						</tr>
						<tr>
							<td class=" h60 text-center">成功发送客户数：${successSmsCustomer}</td>
							<td class=" h60 text-center">订单总金额：${totalOrderMoney}</td>
							<td class=" h60 text-center">成交总金额：${successMoney}</td>
							<td class=" h60 text-center">订单未付款总额：${waitMoney}</td>
							<td class=" h60 text-center">退款总金额：${failMoney}</td>
						</tr>
						<tr>
							<td class=" h60 text-center">发送成功率：</td>
							<td class=" h60 text-center">订单总数：${totalOrderNum}</td>
							<td class=" h60 text-center">成交订单总数：${successOrderNum}</td>
							<td class=" h60 text-center">订单未付款订单总数：${waitOrderNum}</td>
							<td class=" h60 text-center">退款订单总数：${failOrderNum}</td>
						</tr>
						<tr>
							<td class=" h60 text-center">短信发送条数：${totalSmsNum}</td>
							<td class=" h60 text-center">订单客单价：</td>
							<td class=" h60 text-center">成交客单价：</td>
							<td class=" h60 text-center">订单未付款客单价：</td>
							<td class=" h60 text-center">退款客单价：</td>
						</tr>
						<tr>
							<td class=" h60 text-center">短信消费金额：${smsMoney}<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" /></td>
							<td class=" h60 text-center">订单客单价：</td>
							<td class=" h60 text-center">成交客单价：</td>
							<td class=" h60 text-center">订单未付款客单价：</td>
							<td class=" h60 text-center">退款客单价：</td>
						</tr>
						<tr>
							<td class=" h60 text-center">未下单客户数：${failOrderCustomer}</td>
							<td class=" h60 text-center">平均订单商品数：</td>
							<td class=" h60 text-center">平均购买商品数：</td>
							<td class=" h60 text-center">平均订单未付款商品数：</td>
							<td class=" h60 text-center">平均退款商品数：</td>
						</tr>
						<tr>
							<td class=" h60 text-center">RIO：<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" /></td>
							<td class=" h60 text-center">下订单率：<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" /></td>
							<td class=" h60 text-center">成交率：<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" /></td>
							<td class=" h60 text-center">订单未付款率：<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" /></td>
							<td class=" h60 text-center">退款率：<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" /></td>
						</tr>
					</table>
					<div>
						<div>
							<div class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
								列表
							</div>	
							<div class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
								图表
							</div>
							<div class="clear"></div>
						</div>
						
						
						
						
					</div>
						
					
				</div>
				
				
				
				<div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in position_relative three display_none">
					<p>客户详情</p>
					<div style="line-height: 2.5vw;">
						<div class="f_l">
							订单来源：
						</div>
						<div class="wrap f_l h50 lh50 w140"style="margin: 0 1vw;">
							<div class="nice-select h50 lh50 w117 z_3"
								name="nice-select">
								<input readonly="readonly"
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="全部">
								<ul>
									<li value="1">全部</li>
									<li value="7">手机端</li>
									<li value="15">PC端</li>
								</ul>
							</div>
						</div>
						<div class="f_l">
							客户类型：
						</div>
						<div class="wrap f_l h50 lh50 w140"style="margin: 0 1vw;">
							<div class="nice-select h50 lh50 w117 z_3"
								name="nice-select">
								<input readonly="readonly"
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="全部">
								<ul>
									<li value="1">全部</li>
									<li value="7">手机端</li>
									<li value="15">PC端</li>
								</ul>
							</div>
						</div>
						<div class="f_l">
							效果分析日期：
						</div>
						<div class="wrap f_l h50 lh50 w140"style="margin: 0 1vw;">
							<div class="nice-select h50 lh50 w117 z_3"
								name="nice-select">
								<input readonly="readonly"
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="近7天">
								<ul>
									<li value="1">近7天</li>
									<li value="7">近7天</li>
									<li value="15">近7天</li>
								</ul>
							</div>
						</div>
						<div class="cursor_ w20 h20 border_d9dde5 b_radius5 f_l set_time" style="margin-left: 1.5vw;margin-top: 0.7vw;"></div>
						<div class="f_l"style="margin-left: 1vw;margin-right: 1vw;">自定义</div>
						<div class="f_l set_this_time display_none">
							<div class="f_l position_relative">
								<input style="border: 1px solid #cad3df;" name="bTime" class="bgc_fff w230 p_l10 h50 m_r10" value="${bTime}" type="text" id="tser52" readonly="true" onclick="$.jeDate('#tser52',{trigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
								<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png"/>
							</div>
							<div class="f_l lh50">~</div>
							<div class="f_l position_relative">
								<input style="border: 1px solid #cad3df;" name="eTime" class="bgc_fff border0 w230 p_l10 h50 m_l10 m_r40" value="${eTime}" type="text" id="tser53" readonly="true" onclick="$.jeDate('#tser53',{trigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
								<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png"/>
							</div>
							
							<div class="clear"></div>
						</div>
						<div class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
							搜索
						</div>
						<div class="clear"></div>
					</div>
					
					<div style="line-height: 2.5vw;margin-top: 1vw;">
					
						<div class="f_l">
							客户姓名：
						</div>
						<input style="border: 1px solid #cad3df;" class="bgc_fff f_l w230 p_l10 h50 m_r10" />
					
						<div class="f_l">
							手机号：
						</div>
						<input style="border: 1px solid #cad3df;" class="bgc_fff f_l w230 p_l10 h50 m_r10" />
					
						<div class="f_l">
							商品ID：
						</div>
						<input style="border: 1px solid #cad3df;" class="bgc_fff f_l w230 p_l10 h50 m_r10" />
						<div class="clear"></div>
					</div>
					
					
					
					<table border="0" class="font14 item_table" style="margin-top:3.5vw;">
							<tr class="bgc_e3e7f0">
								<th style="width: 3vw;" class=" h60 text-center">序号</th>
								<th style="width: 5vw;" class=" h60 text-center">买家昵称</th>
								<th style="width: 5vw;" class=" h60 text-center">客户姓名</th>
								<th style="width: 6vw;" class=" h60 text-center">手机号</th>
								<th style="width: 6vw;" class=" h60 text-center">会员等级</th>
								<th style="width: 6vw;" class=" h60 text-center">退款订单金额</th>
								<th style="width: 6vw;" class=" h60 text-center">退款订单数</th>
								<th style="width: 6vw;" class=" h60 text-center">退款商品数</th>
								<th style="width: 10vw;" class=" h60 text-center">地址</th>
							</tr>
							<tr>
								<td class=" h60 text-center">1</td>
								<td class=" h60 text-center">XXXXXX</td>
								<td class=" h60 text-center">xxxxxx</td>
								<td class=" h60 text-center">11111111111</td>
								<td class=" h60 text-center">sjkghfjkdgk</td>
								<td class=" h60 text-center">asdfsgdds</td>
								<td class=" h60 text-center">fsdgsdg</td>
								<td class=" h60 text-center">sdgsdg</td>
								<td class=" h60 text-center">sdfsdgfsdgfsdgsdgfsdgfdgsdfgregrsdgedasgr</td>
							</tr>
							<tr>
								<td class=" h60 text-center">1</td>
								<td class=" h60 text-center">XXXXXX</td>
								<td class=" h60 text-center">xxxxxx</td>
								<td class=" h60 text-center">11111111111</td>
								<td class=" h60 text-center">sjkghfjkdgk</td>
								<td class=" h60 text-center">asdfsgdds</td>
								<td class=" h60 text-center">fsdgsdg</td>
								<td class=" h60 text-center">sdgsdg</td>
								<td class=" h60 text-center">sdfsdgfsdgfsdgsdgfsdgfdgsdfgregrsdgedasgr</td>
							</tr>
							<tr>
								<td class=" h60 text-center">1</td>
								<td class=" h60 text-center">XXXXXX</td>
								<td class=" h60 text-center">xxxxxx</td>
								<td class=" h60 text-center">11111111111</td>
								<td class=" h60 text-center">sjkghfjkdgk</td>
								<td class=" h60 text-center">asdfsgdds</td>
								<td class=" h60 text-center">fsdgsdg</td>
								<td class=" h60 text-center">sdgsdg</td>
								<td class=" h60 text-center">sdfsdgfsdgfsdgsdgfsdgfdgsdfgregrsdgedasgr</td>
							</tr>
							<tr>
								<td class=" h60 text-center">1</td>
								<td class=" h60 text-center">XXXXXX</td>
								<td class=" h60 text-center">xxxxxx</td>
								<td class=" h60 text-center">11111111111</td>
								<td class=" h60 text-center">sjkghfjkdgk</td>
								<td class=" h60 text-center">asdfsgdds</td>
								<td class=" h60 text-center">fsdgsdg</td>
								<td class=" h60 text-center">sdgsdg</td>
								<td class=" h60 text-center">sdfsdgfsdgfsdgsdgfsdgfdgsdfgregrsdgedasgr</td>
							</tr>
							<tr>
								<td class=" h60 text-center">1</td>
								<td class=" h60 text-center">XXXXXX</td>
								<td class=" h60 text-center">xxxxxx</td>
								<td class=" h60 text-center">11111111111</td>
								<td class=" h60 text-center">sjkghfjkdgk</td>
								<td class=" h60 text-center">asdfsgdds</td>
								<td class=" h60 text-center">fsdgsdg</td>
								<td class=" h60 text-center">sdgsdg</td>
								<td class=" h60 text-center">sdfsdgfsdgfsdgsdgfsdgfdgsdfgregrsdgedasgr</td>
							</tr>
						</table>
					
				</div>
				
				<div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in position_relative three display_none">
					<p>商品详情</p>
					<div style="line-height: 2.5vw;">
						<div class="f_l">
							订单来源：
							<input id="orderSource" name="orderSource" value="${orderSource}">
						</div>
						<div class="wrap f_l h50 lh50 w140"style="margin: 0 1vw;">
							<div class="nice-select h50 lh50 w117 z_3"
								name="nice-select">
								<input readonly="readonly"
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="全部">
								<ul>
									<li value="1">全部</li>
									<li value="7">手机端</li>
									<li value="15">PC端</li>
								</ul>
							</div>
						</div>
						<div class="f_l">
							客户类型：
						</div>
						<div class="wrap f_l h50 lh50 w140"style="margin: 0 1vw;">
							<div class="nice-select h50 lh50 w117 z_3"
								name="nice-select">
								<input readonly="readonly"
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="全部">
								<ul>
									<li value="1">全部</li>
									<li value="7">手机端</li>
									<li value="15">PC端</li>
								</ul>
							</div>
						</div>
						<div class="f_l">
							效果分析日期：
						</div>
						<div class="wrap f_l h50 lh50 w140"style="margin: 0 1vw;">
							<div class="nice-select h50 lh50 w117 z_3"
								name="nice-select">
								<input readonly="readonly"
									class="h45 lh45 w140 Time11" style="color: #000000"
									type="text"
									value="近7天">
								<ul>
									<li value="1">近7天</li>
									<li value="7">近7天</li>
									<li value="15">近7天</li>
								</ul>
							</div>
						</div>
						<div class="cursor_ w20 h20 border_d9dde5 b_radius5 f_l set_time" style="margin-left: 1.5vw;margin-top: 0.7vw;"></div>
						<div class="f_l"style="margin-left: 1vw;margin-right: 1vw;">自定义</div>
						<div class="f_l set_this_time display_none">
							<div class="f_l position_relative">
								<input style="border: 1px solid #cad3df;" name="bTime" class="bgc_fff w230 p_l10 h50 m_r10" value="${bTime}" type="text" id="tser52" readonly="true" onclick="$.jeDate('#tser52',{trigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
								<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png"/>
							</div>
							<div class="f_l lh50">~</div>
							<div class="f_l position_relative">
								<input style="border: 1px solid #cad3df;" name="eTime" class="bgc_fff border0 w230 p_l10 h50 m_l10 m_r40" value="${eTime}" type="text" id="tser53" readonly="true" onclick="$.jeDate('#tser53',{trigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
								<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png"/>
							</div>
							<div class="clear"></div>
						</div>
						<div class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
							搜索
						</div>
						<div class="clear"></div>
					</div>
					
					<div style="line-height: 2.5vw;margin-top: 1vw;">
					
						<div class="f_l">
							商品ID：
						</div>
						<input style="border: 1px solid #cad3df;" class="bgc_fff f_l w230 p_l10 h50 m_r10" />
						<div class="clear"></div>
					</div>
					
					
					
					<table border="0" class="font14 item_table" style="margin-top:3.5vw;">
							<tr class="bgc_e3e7f0">
								<th style="width: 3vw;" class=" h60 text-center">序号</th>
								<th style="width: 5vw;" class=" h60 text-center">买家昵称</th>
								<th style="width: 5vw;" class=" h60 text-center">客户姓名</th>
								<th style="width: 6vw;" class=" h60 text-center">手机号</th>
								<th style="width: 6vw;" class=" h60 text-center">会员等级</th>
								<th style="width: 6vw;" class=" h60 text-center">退款订单金额</th>
								<th style="width: 6vw;" class=" h60 text-center">退款订单数</th>
								<th style="width: 6vw;" class=" h60 text-center">退款商品数</th>
								<th style="width: 10vw;" class=" h60 text-center">地址</th>
							</tr>
							<tr>
								<td class=" h60 text-center">1</td>
								<td class=" h60 text-center">XXXXXX</td>
								<td class=" h60 text-center">xxxxxx</td>
								<td class=" h60 text-center">11111111111</td>
								<td class=" h60 text-center">sjkghfjkdgk</td>
								<td class=" h60 text-center">asdfsgdds</td>
								<td class=" h60 text-center">fsdgsdg</td>
								<td class=" h60 text-center">sdgsdg</td>
								<td class=" h60 text-center">sdfsdgfsdgfsdgsdgfsdgfdgsdfgregrsdgedasgr</td>
							</tr>
							<tr>
								<td class=" h60 text-center">1</td>
								<td class=" h60 text-center">XXXXXX</td>
								<td class=" h60 text-center">xxxxxx</td>
								<td class=" h60 text-center">11111111111</td>
								<td class=" h60 text-center">sjkghfjkdgk</td>
								<td class=" h60 text-center">asdfsgdds</td>
								<td class=" h60 text-center">fsdgsdg</td>
								<td class=" h60 text-center">sdgsdg</td>
								<td class=" h60 text-center">sdfsdgfsdgfsdgsdgfsdgfdgsdfgregrsdgedasgr</td>
							</tr>
							<tr>
								<td class=" h60 text-center">1</td>
								<td class=" h60 text-center">XXXXXX</td>
								<td class=" h60 text-center">xxxxxx</td>
								<td class=" h60 text-center">11111111111</td>
								<td class=" h60 text-center">sjkghfjkdgk</td>
								<td class=" h60 text-center">asdfsgdds</td>
								<td class=" h60 text-center">fsdgsdg</td>
								<td class=" h60 text-center">sdgsdg</td>
								<td class=" h60 text-center">sdfsdgfsdgfsdgsdgfsdgfdgsdfgregrsdgedasgr</td>
							</tr>
							<tr>
								<td class=" h60 text-center">1</td>
								<td class=" h60 text-center">XXXXXX</td>
								<td class=" h60 text-center">xxxxxx</td>
								<td class=" h60 text-center">11111111111</td>
								<td class=" h60 text-center">sjkghfjkdgk</td>
								<td class=" h60 text-center">asdfsgdds</td>
								<td class=" h60 text-center">fsdgsdg</td>
								<td class=" h60 text-center">sdgsdg</td>
								<td class=" h60 text-center">sdfsdgfsdgfsdgsdgfsdgfdgsdfgregrsdgedasgr</td>
							</tr>
							<tr>
								<td class=" h60 text-center">1</td>
								<td class=" h60 text-center">XXXXXX</td>
								<td class=" h60 text-center">xxxxxx</td>
								<td class=" h60 text-center">11111111111</td>
								<td class=" h60 text-center">sjkghfjkdgk</td>
								<td class=" h60 text-center">asdfsgdds</td>
								<td class=" h60 text-center">fsdgsdg</td>
								<td class=" h60 text-center">sdgsdg</td>
								<td class=" h60 text-center">sdfsdgfsdgfsdgsdgfsdgfdgsdfgregrsdgedasgr</td>
							</tr>
						</table>
						
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
			window.location.href = ctxVal + "/memberEffectIndex?msgId=" + $(this).children('input').val();
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
