<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>营销中心-短信发送记录</title>
	<%@ include file="/common/common.jsp"%>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		
	<!--兼容360meta-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta name="renderer" content="webkit">

	<link rel="stylesheet" href="${ctx}/crm/css/jquery.kpage.css" />
	<link rel="stylesheet" href="${ctx}/crm/css/laypage.css" />	
    <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
    <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.${ctx}/crm/js/1.3.0/respond.min.js"></script>
    <![endif]-->

	<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js" ></script> --%>
	<script type="text/javascript" src="${ctx}/crm/js/text_history_yxzx.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/util.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/util.js"></script>		
	<script type="text/javascript" src="${ctx}/crm/js/jquery.kpage.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/laypage.js" ></script>
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
					<div class="w100_ h50 lh50 bgc_fff font18">
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
								<div class="f_l w140 text-center cursor_">
									订单短信群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/msgSendRecord/memberSendRecord">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
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
								<div class="f_l w200 text-center cursor__">
									订单短信群发效果分析
								</div>
							</a>
						</div>
					</div>
					
				
						
					<!---------------短信发送记录--------------->
					<div class="h940">
						<!----------上部---------->
						<div class="w1605 h130 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								短信发送记录
							</div>
							<!---------------描述--------------->
							<div class="font14">
								可以查看会员短信任务记录，可以查看发送的详细清单，支持一键补发！
							</div>
							
							<div class="font16 c_384856 h50 lh50 m_t18">
								<a class="c_384856" href="${ctx}/msgSendRecord/memberSendRecord">
									<div class="w140 h50 text-center f_l bgc_fff text_out cursor_" id="shangbu1">
										会员群发记录
									</div>
								</a>
								<a class="c_384856" href="${ctx}/msgSendRecord/specificSendRecord">
									<div class="w200 h50 text-center f_l bgc_e3e7f0 text_out cursor_" id="shangbu2">
										指定号码发送记录
									</div>
								</a>
								<a class="c_384856" href="${ctx}/msgSendRecord/orderSendRecord">
									<div class="w200 h50 text-center f_l bgc_e3e7f0 text_out cursor_" id="shangbu3">
										订单发送记录
									</div>
								</a>
								<a class="c_384856" href="${ctx}/msgSendRecord/toSendRecord" >
									<div class="w200 h50 text-center f_l bgc_e3e7f0 text_out cursor_" id="shangbu4">
										定时待发送记录
									</div>
								</a>
							</div>
						</div>
						<!----------下部1---------->
						<div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_in" id="_xiabu1">
							<p style="color:red;font-size:16px;">短信发送中，您可进行其他操作，稍后可刷新查看最新短信发送结果。</p>
							<!-- 会员群发记录查询 -->
							<form action="${ctx}/msgSendRecord/memberSendRecord" method="post">
							<div class="font14 c_384856 h40 lh42 m_t20 m_b20">
								<div class="f_l m_r15">
									发送时间:
								</div>
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" name="beginTime" value="<fmt:formatDate value='${beginTime }' pattern='yyyy-MM-dd HH:mm:ss' />" type="text" id="tser01"  readonly>
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								<div class="f_l">~</div>
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r10" name="endTime" value="<fmt:formatDate value='${endTime }' pattern='yyyy-MM-dd HH:mm:ss' />" type="text" id="tser02" readonly >
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								<div class="f_l l_h40">
								活动主题:<input style="width: 10vw; margin-right: 3vw;" class="bgc_f4f6fa border_d9dde5 b_radius5 w240 h40 m_l16" name="activityName" type="text" value="${activityName}" />	
								</div>
								<input class="m_r45 w100 lh50 bgc_fff h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center  f_l cursor_" type="submit" value="查询">
								<div class="w200 lh50 bgc_fff h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center f_l cursor_">
									<a class="w200 inline_block c_00a0e9 tk" href="${ctx}/msgSendRecord/memberSendRecordLastMonth">
									查看一个月以前的记录
									</a>
								</div>
							</form>	
							</div>
							
							
							<!--------详细数据-------->
							<div class="c_384856 font14 text-center">
								<table>
								  <tr>
								     <th class="w125">序号</th>
									 <th class="w120">活动名称</th>
									 <th class="w120">发送时间</th>
									 <th class="w120" style="width:22vw;">短信模板内容</th>
									 <th class="w300">任务状态</th>
									 <th class="w230">操作</th>
								  </tr>
								  <!-- 短信发送记录>>>>>>会员群发记录的动态数据 -->
								  <c:if test="${pagination.list.size() ==0 }">
									  <tr>
									    <td colspan="6">没有相关数据,请重新查询或更改相关筛选条件!</td>
									  </tr>
								  </c:if>
								 <c:if test="${pagination.list.size() !=0 }">
									<c:forEach items="${pagination.list }" var="msgSendRecord" varStatus="status">
									<tr class="toggleTr">
									  <td>${status.index+1 }</td>
									  <td>${msgSendRecord.activityName }</td>
									  <td><fmt:formatDate value="${msgSendRecord.sendCreat }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									  <td><div class="toggleDiv">${msgSendRecord.templateContent }</div></td>
									  <td>
									  	<ul>
									    	<%-- <c:if test="${msgSendRecord.status ==1 }"><li >全部成功</li></c:if>
									    	<c:if test="${msgSendRecord.status ==2 }"><li >全部失败</li></c:if>
									    	<c:if test="${msgSendRecord.status ==3 }"><li >部分成功</li></c:if> --%>
									    	<c:if test="${msgSendRecord.status ==4 }"><li >发送中</li></c:if>
									    	<c:if test="${msgSendRecord.status ==5 }"><li >发送完成</li></c:if>
									  	</ul>
									  </td>
									  <td>
											<div class="wrap h45 lh45 w108 margin0_auto">
											    <div class="nice-select w108 h45 lh45" name="nice-select">
											    	<input class="h45 lh45 position_relative z_3" type="text" value="短信操作" readonly>
											    	<ul class="position_relative z_4">
											    		<li class="check_list1_btn" onclick="openView(${msgSendRecord.id},${msgSendRecord.type})">
												    		<%-- <a class="c_333333" href="javascript:viod(0);" onclick="openView(${msgSendRecord.id},${msgSendRecord.type})">
															查看清单
															</a> --%>
															查看清单
														</li>
														<li onclick="delIt(${msgSendRecord.id},${msgSendRecord.type})">
												      		删除记录
											      		</li>
											    	</ul>
											  	</div>
											</div>
									  </td>
									</tr>
									</c:forEach> 
								</c:if>  
								</table>
							</div>
							
							
							<!--------分页-------->
                           <div class="w800 h24 m_t22 font14 c_8493a8 m_b40">
                                
                                <div class="f_r w800 h24 l_h22 font12">
                                  <c:forEach items="${pagination.pageView }" var="page">
										${page } 
								  </c:forEach>
                                </div>
                            </div>
							
						</div>
						
						<!----------下部2---------->
						<div class="w1605  bgc_fff p_t35 p_l40 p_b40 text_in" id="_xiabu2">
							<p style="color:red;font-size:16px;">短信发送中，您可进行其他操作，稍后可刷新查看最新短信发送结果。</p>
							<form action="${ctx}/msgSendRecord/specificSendRecord" method="post">
							<div class="font14 c_384856 h40 lh42 m_t20 m_b20">
								<div class="f_l m_r15">
									发送时间:
								</div>
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" name="beginTime" type="text" id="tser03" value="<fmt:formatDate value='${beginTime }' pattern='yyyy-MM-dd HH:mm:ss' />" readonly >
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								<div class="f_l">~</div>
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" name="endTime" type="text" id="tser04" value="<fmt:formatDate value='${endTime }' pattern='yyyy-MM-dd HH:mm:ss' />" readonly >
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								<div class="f_l l_h40">
								活动主题:<input style="width: 10vw; margin-right: 3vw;" class="bgc_f4f6fa border_d9dde5 b_radius5 w240 h40 m_l16" name="activityName" type="text" value="${activityName}" />	
								</div>
								<input class="m_r45 w100 lh50 bgc_fff h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center  f_l cursor_" type="submit" value="查询">
								<div class="w200 lh50 h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center f_l cursor_">
									<!-- //查看一个月以前的记录 -->
									<a class="w200 inline_block c_00a0e9 tk" href="${ctx}/msgSendRecord/specificSendRecordLastMonth">
									查看一个月以前的记录
									</a>
								</div>
								
							</div>
							</form>
							
							<!--------详细数据-------->
							<div class="c_384856 font14 text-center">
								<table>
								  <tr>
								    <th class="w125">序号</th>
							    <th class="w120">活动名称</th>
							    <th class="w120">发送时间</th>
							    <th class="w120" style="width:22vw;">短信模板内容</th>
							    <th class="w300">任务状态</th>
							    <th class="w230">操作</th>
								  </tr>
								  <c:if test="${pagination2.list.size() ==0 }">
									  <tr>
									    <td colspan="6">没有相关数据,请重新查询或更改相关筛选条件!</td>
									  </tr>
								  </c:if>
								 <c:if test="${pagination2.list.size() !=0 }">
								  <c:forEach items="${pagination2.list }" var="msgSendRecord" varStatus="status">
								  <tr class="toggleTr">
									   <td>${status.index+1 }</td>
									   <td>${msgSendRecord.activityName }</td>
									   <td><fmt:formatDate value="${msgSendRecord.sendCreat }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									   <td><div class="toggleDiv">${msgSendRecord.templateContent }</div></td>
									   <td>
										   <ul>
											  <%--  <c:if test="${msgSendRecord.status ==1 }"><li >全部成功</li></c:if>
									    	   <c:if test="${msgSendRecord.status ==2 }"><li >全部失败</li></c:if>
									    	   <c:if test="${msgSendRecord.status ==3 }"><li >部分成功</li></c:if> --%>
									    	   <c:if test="${msgSendRecord.status ==4 }"><li >发送中</li></c:if>
									    	   <c:if test="${msgSendRecord.status ==5 }"><li >发送完成</li></c:if>
										   </ul>
									   </td>
									   <td>
										<div class="wrap h45 lh45 w108 margin0_auto">
											   <div class="nice-select w108 h45 lh45" name="nice-select">
											    <input class="h45 lh45 position_relative z_3" type="text" value="短信操作" readonly>
											    <ul class="position_relative z_4">
											    	<li class="check_list2_btn" onclick="openView('${msgSendRecord.id}','${msgSendRecord.type}')">
												    	查看清单
											    	</li>
											    	<li onclick="sendAgain(${msgSendRecord.id},${msgSendRecord.type})">
												      	再次发送
											      	</li>
											      	<li onclick="delIt(${msgSendRecord.id},${msgSendRecord.type})">
												      	删除记录
											      	</li>
											    </ul>
											  	</div>
										</div>
									   </td>
								  </tr>
								 </c:forEach>
								 </c:if>   
								</table>
							</div>
							
							
							 <!--------分页-------->
                             <div class="w800 h24 m_t22 font14 c_8493a8 m_b10">
                                <div class="f_r w800 h24 l_h22 font12" >
                                  <c:forEach items="${pagination2.pageView }" var="page">
										${page } 
								  </c:forEach>
                                </div>
                            </div>
                            
							
						</div>
						
						<!----------下部3---------->
						<div class="w1605  bgc_fff p_t35 p_l40 p_b40 text_in" id="_xiabu3">
							<p style="color:red;font-size:16px;">短信发送中，您可进行其他操作，稍后可刷新查看最新短信发送结果。</p>
							<form action="${ctx}/msgSendRecord/orderSendRecord" method="post">
							<div class="font14 c_384856 h40 lh42 m_t20 m_b20">
								<div class="f_l m_r15">
									发送时间:
								</div>
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" name="beginTime" value="<fmt:formatDate value='${beginTime }' pattern='yyyy-MM-dd HH:mm:ss' />" type="text" id="tser05"  readonly >
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								<div class="f_l">~</div>
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" name="endTime" type="text" id="tser06" value="<fmt:formatDate value='${endTime }' pattern='yyyy-MM-dd HH:mm:ss' />" readonly >
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								<div class="f_l l_h40">
								活动主题:<input style="width: 10vw; margin-right: 3vw;" class="bgc_f4f6fa border_d9dde5 b_radius5 w240 h40 m_l16" name="activityName" type="text" value="${activityName}" />	
								</div>
								<input class="m_r45 w100 lh50 bgc_fff h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center  f_l cursor_" type="submit" value="查询">
								<div class="w200 lh50 h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center f_l cursor_">
									<!-- 查看一个月以前的记录 -->
									<a class="w200 inline_block c_00a0e9 tk" href="${ctx}/msgSendRecord/orderSendRecordLastMonth">
									查看一个月以前的记录
									</a>
								</div>
							</div>
							</form>
							
							<!--------详细数据-------->
							<div class="c_384856 font14 text-center">
								<table>
								  <tr>
								    <th class="w125">序号</th>
								    <th class="w120">活动名称</th>
								    <th class="w120">发送时间</th>
								    <th class="w120" style="width:22vw;">短信模板内容</th>
								    <th class="w300">任务状态</th>
								    <th class="w230">操作</th>
								  </tr>
								  <c:if test="${pagination3.list.size() ==0 }">
									  <tr>
									    <td colspan="8">没有相关数据,请重新查询或更改相关筛选条件!</td>
									  </tr>
								  </c:if>  
								  <c:if test="${pagination3.list.size() !=0 }">
								  <c:forEach items="${pagination3.list }" var="msgSendRecord" varStatus="status">
									 <tr class="toggleTr on">
									    <td>${status.index+1 }</td>
									    <td>${msgSendRecord.activityName }</td>
									    <td><fmt:formatDate value="${msgSendRecord.sendCreat }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									    <td><div class="toggleDiv">${msgSendRecord.templateContent }</div></td>
									    <td>
										    <ul>
											  <%--  <c:if test="${msgSendRecord.status ==1 }"><li >全部成功</li></c:if>
									    	   <c:if test="${msgSendRecord.status ==2 }"><li >全部失败</li></c:if>
									    	   <c:if test="${msgSendRecord.status ==3 }"><li >部分成功</li></c:if> --%>
									    	   <c:if test="${msgSendRecord.status ==4 }"><li >发送中</li></c:if>
									    	   <c:if test="${msgSendRecord.status ==5 }"><li >发送完成</li></c:if>
										    </ul>
									    </td>
									    <td>
											<div class="wrap h45 lh45 w108 margin0_auto">
											    <div class="nice-select w108 h45 lh45" name="nice-select">
											    	<input class="h45 lh45 position_relative z_3" type="text" value="短信操作" readonly>
											    	<ul class="position_relative z_4">
											    		<li class="check_list3_btn" onclick="openView('${msgSendRecord.id}','${msgSendRecord.type}')">
												    		查看清单
											    		</li>
											      		<li onclick="delIt(${msgSendRecord.id},${msgSendRecord.type})">
												      		删除记录
											      		</li>
											    	</ul>
											  	</div>
											</div>
									    </td>
									  </tr>
								  </c:forEach> 
							      </c:if>  
							  </table>
							</div>
							
                            <div class="w800 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_r w800 h24 l_h22 font12">
                                  <c:forEach items="${pagination3.pageView }" var="page">
										${page } 
								  </c:forEach>
                                </div>
                            </div>
						</div>
						
						<!----------下部4---------->
						<div class="w1605 bgc_fff p_t35 p_l40 p_b40 text_in m_b30" id="_xiabu4">
							<p style="color:red;font-size:16px;text-align:center;">提示:取消定时发送短信时间最少为短信发送前10分钟,否则不能保证定时短信取消成功!</p>
							<form action="${ctx}/msgSendRecord/toSendRecord" method="post">
							<div class="font14 c_384856 h50 lh50 m_t20 m_b20">
								<div class="f_l m_r15">
									发送时间:
								</div>
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" name="beginTime" value="<fmt:formatDate value='${beginTime }' pattern='yyyy-MM-dd HH:mm:ss' />" type="text" id="tser07"  readonly >
									<img class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								<div class="f_l">~</div>
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" name="endTime" type="text" id="tser08" value="<fmt:formatDate value='${endTime }' pattern='yyyy-MM-dd HH:mm:ss' />" readonly>
									<img class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								<div class="f_l l_h40">
								活动主题:<input style="width: 10vw; margin-right: 3vw;" class="bgc_f4f6fa border_d9dde5 b_radius5 w240 h40 m_l16" name="activityName" type="text" value="${activityName}" />	
								</div>
								<!-- <div style="margin-left: 3vw;" class=" w100 lh40 h40 b_radius5 tk border_00a0e9 c_00a0e9 text-center f_l cursor_">
									查询
								</div> -->
								<input style="margin-left: 3vw;" class=" w100 lh40 h40 b_radius5 tk border_00a0e9 c_00a0e9 text-center f_l cursor_" type="submit" value="查询"/>
							</div>
							</form>
							
							<!--------详细数据-------->
							<div class="c_384856 font14 text-center">
								<table>
								  <tr>
								    <th class="w125">序号</th>
								    <th class="w120">活动名称</th>
								    <th class="w120">创建时间</th>
								    <th class="w120">发送时间</th>
								    <th class="w120" style="width:22vw;">短信模板内容</th>
								    <th class="w300">短信类型</th>
								    <th class="w230">操作</th>
								  </tr>
								  <c:if test="${pagination4.list.size() ==0 }">
									  <tr>
									    <td colspan="8">没有相关数据,请重新查询或更改相关筛选条件!</td>
									  </tr>
								  </c:if>  
								  <c:if test="${pagination4.list.size() !=0 }">
								  <c:forEach items="${pagination4.list }" var="msgSendRecord" varStatus="status">
									<tr class="toggleTr3">
									    <td>${status.index+1 }</td>
									    <td>${msgSendRecord.activityName }</td>
									    <td><fmt:formatDate value="${msgSendRecord.createdDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									    <td><fmt:formatDate value="${msgSendRecord.sendCreat }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									    <td><div class="toggleDiv3">${msgSendRecord.templateContent }</div></td>
									    <td>
										    <ul>
											   <c:if test="${msgSendRecord.type =='33' }"><li>会员短信群发</li></c:if>
									    	   <c:if test="${msgSendRecord.type =='34' }"><li>指定号码发送</li></c:if>
									    	   <c:if test="${msgSendRecord.type =='35' }"><li>订单短信群送</li></c:if>
										    </ul>
									    </td>
									    <td> 
									    <c:choose>  
										   <c:when test="${msgSendRecord.status =='4' }">
										   		<button style="width: 4vw; border: 1px solid rgb(0, 160, 233); color: rgb(0, 160, 233); border-radius: 5px; font-size: 0.8vw; height: 1.7vw;"  href="javacript:void(0);">
											    		保存中
										    	</button>
										   </c:when>  
										   <c:otherwise> 
											    <button style="width: 4vw; border: 1px solid rgb(0, 160, 233); color: rgb(0, 160, 233); border-radius: 5px; font-size: 0.8vw; height: 1.7vw;"  
											    onclick="cancelSend(${msgSendRecord.id},${msgSendRecord.type},${msgSendRecord.status})">
											    		取消定时
										    	</button>
										   </c:otherwise>  
										</c:choose>  
									    	<div class="display_none" style="color:red">当定时发送时间接近当前时间时,可能无法完全移除要定时发送的短信!</div>
									    	 <!-- <button style="width: 4vw; border: 1px solid rgb(0, 160, 233); color: rgb(0, 160, 233); border-radius: 5px; font-size: 0.8vw; height: 1.7vw;" >
									    		操作 onMoseover="remind()"   title="当定时发送时间接近当前时间时,可能无法完全移除要定时发送的短信!"
									    	</button> -->
									    </td>
									  </tr>
								  </c:forEach> 
							      </c:if>  

								</table>
							</div>
							
							
							<!--------分页-------->
                            <div class="w800 h24 m_t22 font14 c_8493a8 m_b40">
                                 <div class="f_r w800 h24 l_h22 font12">
                                  <c:forEach items="${pagination4.pageView }" var="page">
										${page } 
								  </c:forEach>
                                </div>
                            </div>
							
						</div>
						
						
					</div>
					
				
				</div>	
					
						
				</div>
		
			</div>
	
	</div>

	
	

					

	
	

					

<!---------------弹窗1--------------->
<div class="w100_ h1100 rgba_000_5 position_absolute z_10 check_list1 top0 display_none">
	<div class="w1000  bgc_fff left300 position_fixed p_t35 p_l40 p_r20 p_b20" style="top:5vw;">
		<div>
			<img class="f_r close m_t_50 m_r_40 cursor_" src="${ctx}/crm/images/chazi.png" />
		</div>
		<div class="font16 c_384856 text-center h25 lh25">
			短信效果统计
		</div>
		<div class="c_384856 m_t20 h40">
			<div class="f_l m_r10 font16 h40 lh40">
				手机号码：
			</div>
			<input class="bgc_f4f6fa border0 w250 h40 lh40 f_l m_r15" name="mobile" id="mobile_1" type="text"/>
			<div class="f_l h40 lh40 m_r15 font16">
				发送结果：
			</div>
			<div class="wrap f_l font16 h40 lh40 m_r15">
			    <div class="nice-select h40 lh40"  name="nice-select">
			    	<input class="h40 lh40" type="text" id="me1"  readonly>
			    	<ul  id="_ul1">
			    		<li value="">全部</li>
			    		<li value="1">发送失败</li>
			    		<li value="2">发送成功</li>
			    		<li value="3">手机号码不正确</li>
			    		<li value="4">号码重复</li>
			    		<li value="5">黑名单</li>
			    		<li value="6">重复被屏蔽</li>
			    	</ul>
			  	</div>
			<!-- 隐藏选中的ul中lid值 -->
			<input type="hidden" class="_ul1" name="status" value="">
			</div>
			<input class="w100 h38 f_l lh38 border_00a0e9 c_00a0e9 tk cursor_ b_radius5 text-center font16" type="button"  value="查询" onclick="searchDetail()"/>
			<div class="w100 close m_l25 h38 f_l lh38 border_00a0e9 c_00a0e9 tk cursor_ b_radius5 text-center font16">
				返回列表
			</div>
			<div style="clear:both;"></div>
		</div>
		<div class="font16 c_384856 m_t30 count_1">
			<div class="">
				<div class="f_l">
					<div class="f_l">目标发送客户总数:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="totalCustom_1">0</span>人
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">成功发送客户总数:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="successCustom_1">0</span>人
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">实际扣除短信条数:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="actualCount_1">0</span>条
					</div>
					<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div style="margin-top:0.3vw;">
				<div class="f_l">
					<div class="f_l">手机号不正确:</div>
					<div style="width: 10.7vw;" class="m_l20 f_l">
						<span id="wrongNum_1">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">号码重复:</div>
					<div style="width: 12.3vw;" class="m_l20 f_l">
						<span id="repeatNum_1">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">黑名单:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="blackCount_1">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div style="margin-top:0.3vw;">
				<div class="f_l">
					<div class="f_l">重复发送被屏蔽:</div>
					<div style="width: 9.9vw;" class="m_l20 f_l">
						<span id="repeatSend_1">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">其他原因失败:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="failedCount_1">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
			</div>
		</div>
		
		
		<table class="c_384856 text-center font16 m_t30 detail1">
			<tr class="detail_1">
				<th class="w160">手机号码</th>
				<th class="w150">发送时间</th>
				<th class="w340">短信内容</th>	
				<th class="w165">扣费条数</th>
				<th class="w110">发送结果</th>
			</tr>
		</table>
		<!--------分页-------->
        <div class="w945 h24 m_t22 font14 c_8493a8 m_b40">
            <div class="f_r   l_h22 font12 detail_1_pageView" id="page1">
            </div> 
            <div style="clear:both;"></div> 	
        </div>
		
	
	</div>
	
</div>







<!---------------弹窗2--------------->
<div class="w100_ h1100 rgba_000_5 position_absolute z_10 check_list2 top0 display_none">
	
	<div class="w1000  bgc_fff left300 position_fixed p_t35 p_l40 p_r20 p_b20" style="top:5vw;">
		<div>
			<img class="f_r close m_t_50 m_r_40 cursor_" src="${ctx}/crm/images/chazi.png" />
		</div>
		<div class="font16 c_384856 text-center h25 lh25">
			短信效果统计
		</div>
		<div class="c_384856 m_t20 h40">
			<div class="f_l m_r10 font16 h40 lh40">
				手机号码：
			</div>
			<input class="bgc_f4f6fa border0 w250 h40 lh40 f_l m_r15" name="mobile" id="mobile_2"  type="text"/>
			<div class="f_l h40 lh40 m_r15 font16">
				发送结果：
			</div>
			<div class="wrap f_l font16 h40 lh40 m_r15">
			    <div class="nice-select h40 lh40"  name="nice-select">
			    	<input class="h40 lh40" id="me2" type="text" readonly>
			    	<ul  id="_ul2">
			    		<li value="">全部</li>
			    		<li value="1">发送失败</li>
			    		<li value="2">发送成功</li>
			    		<li value="3">手机号码不正确</li>
			    		<li value="4">号码重复</li>
			    		<li value="5">黑名单</li>
			    		<li value="6">重复被屏蔽</li>
			    	</ul>
			  	</div>
			<!-- 隐藏选中的ul中lid值 -->
			<input type="hidden" class="_ul2" name="status" value="">
			</div>
			<input class="w100 h38 f_l lh38 border_00a0e9 c_00a0e9 tk cursor_ b_radius5 text-center font16" type="button" value="查询" onclick="searchDetail()" />
			<div class="w100 close m_l25 h38 f_l lh38 border_00a0e9 c_00a0e9 tk cursor_ b_radius5 text-center font16">
				返回列表
			</div>
			
		</div>
		<div class="font16 c_384856 m_t30 count_2">
			<div class="">
				<div class="f_l">
					<div class="f_l">目标发送客户总数:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="totalCustom_2">0</span>人
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">成功发送客户总数:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="successCustom_2">0</span>人
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">实际扣除短信条数:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="actualCount_2">0</span>条
					</div>
					<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div style="margin-top:0.3vw;">
				<div class="f_l">
					<div class="f_l">手机号不正确:</div>
					<div style="width: 10.7vw;" class="m_l20 f_l">
						<span id="wrongNum_2">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">号码重复:</div>
					<div style="width: 12.3vw;" class="m_l20 f_l">
						<span id="repeatNum_2">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">黑名单:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="blackCount_2">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div style="margin-top:0.3vw;">
				<div class="f_l">
					<div class="f_l">重复发送被屏蔽:</div>
					<div style="width: 9.9vw;" class="m_l20 f_l">
						<span id="repeatSend_2">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">其他原因失败:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="failedCount_2">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
			</div>
		</div>
		
		
		<table class="c_384856 text-center font16 m_t30 detail2">
			<tr class="detail_2">
				<th class="w160">手机号码</th>
				<th class="w150">发送时间</th>
				<th class="w340">短信内容</th>
				<th class="w165">扣费条数</th>
				<th class="w110">发送结果</th>
			</tr>
		</table>
		<!--------分页-------->
        <div class="w945 h24 m_t22 font14 c_8493a8 m_b40">
            <div class="f_r  l_h22 font12 detail_2_pageView" id="page2">
            </div>
        </div>
		
	
	</div>
	
</div>










<!---------------弹窗3--------------->
<div class="w100_ h1100 rgba_000_5 position_absolute z_10 check_list3 top0 display_none">
	
	<div class="w1000  bgc_fff left300 position_fixed p_t35 p_l40 p_r20 p_b20" style="top:5vw;">
		<div>
			<img class="f_r close m_t_50 m_r_40 cursor_" src="${ctx}/crm/images/chazi.png" />
		</div>
		<div class="font16 c_384856 text-center h25 lh25">
			短信效果统计
		</div>
		<div class="c_384856 m_t20 h40">
			<div class="f_l m_r10 font16 h40 lh40">
				手机号码：
			</div>
			<input class="bgc_f4f6fa border0 w250 h40 lh40 f_l m_r15" name="mobile" id="mobile_3" type="text"/>
			<div class="f_l h40 lh40 m_r15 font16">
				发送结果：
			</div>
			<div class="wrap f_l font16 h40 lh40 m_r15">
			    <div class="nice-select h40 lh40"  name="nice-select">
			    	<input class="h40 lh40" id="me3" type="text" readonly>
			    	<ul  id="_ul3">
			    		<li value="">全部</li>
			    		<li value="1">发送失败</li>
			    		<li value="2">发送成功</li>
			    		<li value="3">手机号码不正确</li>
			    		<li value="4">号码重复</li>
			    		<li value="5">黑名单</li>
			    		<li value="6">重复被屏蔽</li>
			    	</ul>
			  	</div>
			<!-- 隐藏选中的ul中lid值 -->
			<input type="hidden" class="_ul3" name="status" value="">
			</div>
			<input class="w100 h38 f_l lh38 border_00a0e9 c_00a0e9 tk cursor_ b_radius5 text-center font16" type="button" value="查询" onclick="searchDetail()" />
			<div class="w100 close m_l25 h38 f_l lh38 border_00a0e9 c_00a0e9 tk cursor_ b_radius5 text-center font16">
				返回列表
			</div>
			
		</div>
		<div class="font16 c_384856 m_t30 count_3">
			<div class="">
				<div class="f_l">
					<div class="f_l">目标发送客户总数:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="totalCustom_3">0</span>人
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">成功发送客户总数:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="successCustom_3">0</span>人
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">实际扣除短信条数:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="actualCount_3">0</span>条
					</div>
					<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div style="margin-top:0.3vw;">
				<div class="f_l">
					<div class="f_l">手机号不正确:</div>
					<div style="width: 10.7vw;" class="m_l20 f_l">
						<span id="wrongNum_3">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">号码重复:</div>
					<div style="width: 12.3vw;" class="m_l20 f_l">
						<span id="repeatNum_3">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">黑名单:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="blackCount_3">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div style="margin-top:0.3vw;">
				<div class="f_l">
					<div class="f_l">重复发送被屏蔽:</div>
					<div style="width: 9.9vw;" class="m_l20 f_l">
						<span id="repeatSend_3">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="f_l">
					<div class="f_l">其他原因失败:</div>
					<div style="width: 9vw;" class="m_l20 f_l">
						<span id="failedCount_3">0</span>个
					</div>
					<div style="clear:both;"></div>
				</div>
				<div style="clear:both;"></div>
			</div>
		</div>
		
		
		<table class="c_384856 text-center font16 m_t30 detail3">
			<tr class="detail_3">
				<th class="w160">手机号码</th>
				<th class="w150">发送时间</th>
				<th class="w340">短信内容</th>
				<th class="w165">扣费条数</th>
				<th class="w110">发送结果</th>
			</tr>
		</table>
		<!--------分页-------->
        <div class="w945 h24 m_t22 font14 c_8493a8 m_b40">
            <div class="f_r   l_h22 font12 detail_3_pageView" id="page3">
            </div>
        </div>
		
	
	</div>
	
</div>
	
	
	
	

	
				
</body>
<script type="text/javascript">
$(function(){
	$(".load_top").load("top.html");
	$(".load_left").load("left.html");
	
	//关于日期 的校验
	var start = {
	    format: 'YYYY-MM-DD hh:mm:ss',
	    isinitVal:false,
	    festival:false,
	    ishmsVal:false,
		ishmsLimit:false,
	    maxDate: '2099-06-30 23:59:59', 
	    choosefun: function(elem,datas){
	        end.minDate = datas;
	    }
	};
	var end = {
	    format: 'YYYY年MM月DD日 hh:mm:ss',
	    minDate: $.nowDate(0), 
	    festival:false,
		ishmsLimit:true,
	    maxDate: '2099-06-16 23:59:59',
	    choosefun: function(elem,datas){
	        start.maxDate = datas;
	    }
	};
	$('#inpstart').jeDate(start);
	$('#inpend').jeDate(end);
})



window.onload = function(){
	//读取第一个ul中选中的值 并赋值到第一个弹窗的form中的<input type="hidden" name=status中
    var obj_lis = document.getElementById("_ul1").getElementsByTagName("li");
    for(i=0;i<obj_lis.length;i++){
        obj_lis[i].onclick = function(){
        	$("._ul1").val($(this).val()==0?"":$(this).val());
        	$("#me1").val($(this).text());
        }
    }
	
  //读取第二个ul中选中的值 并赋值到第一个弹窗的form中的<input type="hidden" name=status中
    var obj_lis2 = document.getElementById("_ul2").getElementsByTagName("li");
    for(i=0;i<obj_lis2.length;i++){
        obj_lis2[i].onclick = function(){
        	$("._ul2").val($(this).val()==0?"":$(this).val());
        	$("#me2").val($(this).text());
        }
    } 
    //读取第二个ul中选中的值 并赋值到第一个弹窗的form中的<input type="hidden" name=status中
    var obj_lis3 = document.getElementById("_ul3").getElementsByTagName("li");
    for(i=0;i<obj_lis3.length;i++){
        obj_lis3[i].onclick = function(){
        	$("._ul3").val($(this).val()==0?"":$(this).val());
        	$("#me3").val($(this).text());
        }
    } 
 }
//---------------------各种窗口显示----------------------------
$(function(){
	var show =<%=request.getAttribute("show")%>;
	//指定号码发送记录
	if(show != null && show == 2){			
 	 $("#shangbu1").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#shangbu2").addClass("bgc_fff").removeClass("bgc_e3e7f0");
     $("#shangbu3").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#shangbu4").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#_xiabu1").hide();
     $("#_xiabu2").show();
     $("#_xiabu3").hide();
     $("#_xiabu4").hide();
	}
	//订单发送记录
	else if(show !=null && show == 3){			
 	 $("#shangbu1").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#shangbu3").addClass("bgc_fff").removeClass("bgc_e3e7f0");
     $("#shangbu2").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#shangbu4").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#_xiabu1").hide();
     $("#_xiabu3").show();
     $("#_xiabu2").hide();
     $("#_xiabu4").hide();
	}
	else if(show !=null && show == 4){			
 	 $("#shangbu1").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#shangbu4").addClass("bgc_fff").removeClass("bgc_e3e7f0");
     $("#shangbu2").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#shangbu3").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#_xiabu1").hide();
     $("#_xiabu4").show();
     $("#_xiabu2").hide();
     $("#_xiabu3").hide();
	}else{
	 $("#shangbu4").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#shangbu1").addClass("bgc_fff").removeClass("bgc_e3e7f0");
     $("#shangbu2").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#shangbu3").removeClass("bgc_fff").addClass("bgc_e3e7f0");
     $("#_xiabu4").hide();
     $("#_xiabu1").show();
     $("#_xiabu2").hide();
     $("#_xiabu3").hide();
	}
})	


//定义一个全局的type
var globalType = null;
var globalId = null;
/* var globalMobile = null;
var globalStatus = null; */

//展开弹框
function openView(recordId,type){
	globalType = type;
	globalId = recordId;
	//清除数据
	$('.detail_1').siblings().remove();
	$('.detail_2').siblings().remove();
	$('.detail_3').siblings().remove();
	$('.detail_1_pageView').html("");
	$('.detail_2_pageView').html("");
	$('.detail_3_pageView').html("");
	$('.count_1').find("span").html("0");
	$('.count_2').find("span").html("0");
	$('.count_3').find("span").html("0");
	if(globalType!=null && globalType== '33'){
		$(".check_list1").show();
		getCount(recordId,type);
		showDetail(recordId,type,null,null,1);
	}else if(globalType!=null && globalType== '34'){
		$(".check_list2").show();
		getCount(recordId,type);
		showDetail(recordId,type,null,null,1);
	}else if(globalType!=null && globalType== '35'){
		$(".check_list3").show();
		getCount(recordId,type);
		showDetail(recordId,type,null,null,1);
	}
}
function searchDetail(){
	$('.detail_1').siblings().remove();
	$('.detail_2').siblings().remove();
	$('.detail_3').siblings().remove();
	$('.detail_1_pageView').html("");
	$('.detail_2_pageView').html("");
	$('.detail_3_pageView').html("");
	var recordId = globalId;
	var type =  globalType;
	var mobile;
	var status;
	if(globalType!=null && globalType== '33'){
		mobile = $("#mobile_1").val();
		status = $("._ul1").val();
		showDetail(recordId,type,mobile,status,1);
	}else if(globalType!=null && globalType== '34'){
		mobile = $("#mobile_2").val();
		status = $("._ul2").val();
		showDetail(recordId,type,mobile,status,1);
	}else if(globalType!=null && globalType== '35'){
		mobile = $("#mobile_3").val();
		status = $("._ul3").val();
		showDetail(recordId,type,mobile,status,1);
	}
}

//禁止重复提交
var count_flag = false;
function getCount(recordId,type){
	if(count_flag==true){
		return;
	}
	count_flag==true;
	$.ajax({
		//url:"${ctx}/smsSendRecord/getEveryCount",
		url:"${ctx}/msgSendRecord/getEveryCount",
		data:{"recordId":recordId,"type":type},
		type: "post",
		success:function(data){
			count_flag==false;
			if(type!=null && type== '33'){
				$("#totalCustom_1").html(data.totalCustom);
				$("#successCustom_1").html(data.successCustom);
				$("#actualCount_1").html(data.actualCount);
				$("#wrongNum_1").html(data.wrongNum);
				$("#repeatNum_1").html(data.repeatNum);
				$("#blackCount_1").html(data.blackCount);
				$("#repeatSend_1").html(data.repeatSend);
				$("#failedCount_1").html(data.failedCount);
			}else if(type!=null && type== '34'){
				$("#totalCustom_2").html(data.totalCustom);
				$("#successCustom_2").html(data.successCustom);
				$("#actualCount_2").html(data.actualCount);
				$("#wrongNum_2").html(data.wrongNum);
				$("#repeatNum_2").html(data.repeatNum);
				$("#blackCount_2").html(data.blackCount);
				$("#repeatSend_2").html(data.repeatSend);
				$("#failedCount_2").html(data.failedCount);
			}else if(type!=null && type== '35'){
				$("#totalCustom_3").html(data.totalCustom);
				$("#successCustom_3").html(data.successCustom);
				$("#actualCount_3").html(data.actualCount);
				$("#wrongNum_3").html(data.wrongNum);
				$("#repeatNum_3").html(data.repeatNum);
				$("#blackCount_3").html(data.blackCount);
				$("#repeatSend_3").html(data.repeatSend);
				$("#failedCount_3").html(data.failedCount);
			}
        },
		dataType:'json'
	}); 
}
//禁止重复提交
var detail_flag = false;
function showDetail(recordId,type,mobile,status,pageNo){

	$('.detail_1').siblings().remove();
	$('.detail_2').siblings().remove();
	$('.detail_3').siblings().remove();
	$('.detail_1_pageView').html("");
	$('.detail_2_pageView').html("");
	$('.detail_3_pageView').html("");
	var dataDetail;
	if(detail_flag==true){
		return;
	}
	detail_flag = true;
	$.ajax({
	 	url:"${ctx}/smsSendRecord/smsSendDetail",
		data:{"recordId":recordId,"type":type,"mobile":mobile,"status":status,"pageNo":pageNo},
		type: "post",
		success:function(data){
			detail_flag = false;
			show(recordId,type,mobile,status,pageNo,data);
        },
		dataType:'json'
	}); 
}


function show(recordId,type,mobile,status,pageNo,data){
	 if(data.totalCount > 0){

    	$.each(data.datas,function(index,smsSendRecord){
				var sendTime = "";
				var result = "";
				if(smsSendRecord.status==1){
					result = "发送失败";
				}else if(smsSendRecord.status==2){
					result = "发送成功";
				}else if(smsSendRecord.status==3){
					result = "手机号码不正确";
				}else if(smsSendRecord.status==4){
					result = "号码重复";
				}else if(smsSendRecord.status==5){
					result = "黑名单";
				}else if(smsSendRecord.status==6){
					result = "重复被屏蔽";
				}
				if(smsSendRecord.sendTime !=null){
					var time = Date.parse(smsSendRecord.sendTime);
					var date = new Date(time);
					sendTime = date.toLocaleString().replace("年","-").replace("月","-").replace("日","-").replace("上午","").replace("下午","");
					 
					sendTime =smsSendRecord.sendTime;
				}else{
					semdTime = "";
				}
				dataDetail = 
				"<tr class='toggleTr2'><td>"+smsSendRecord.recNum+"</td>"
				+"<td>"+sendTime+"</td>"
				+"<td><div class='toggleDiv2'>"+smsSendRecord.content+"</div></td>"
				
				+"<td>"+smsSendRecord.actualDeduction+"</td>"
				+"<td>"+result+"</td></tr>";
				if(type!=null && type== '33'){
					
					$('.detail1').append(dataDetail);
				}else if(type!=null && type== '34'){
					
					$('.detail2').append(dataDetail);
				}else if(type!=null && type== '35'){
					
					$('.detail3').append(dataDetail);
				}
			});
    		if(mobile==''){
    			mobile=null;
    		}
    		if(status==''){
    			status=null;
    		}
    		toggleShow('.toggleTr2','.toggleDiv2','2');
    		var iNow=pageNo;
    		var iNowLen='';
    		var maxNum=Math.ceil(data.totalCount/5);
			if(type!=null && type== '33'){
				laypage({
				  	cont: $('#page1'), //容器。值支持id名、原生dom对象，jquery对象,
				  	pages: maxNum, //总页数
				  	skip: true, //是否开启跳页
				  	curr:pageNo,
				  	skin: '#2d8cf0',
				  	groups: 3, //连续显示分页数
				  	jump: function(obj, first){ //触发分页后的回调
				  		if(!first){
				  			$.ajax({
				  			 	url:"${ctx}/smsSendRecord/smsSendDetail",
				  				data:{"recordId":recordId,"type":type,"mobile":mobile,"status":status,"pageNo":obj.curr},
				  				type: "post",
				  				success:function(data){
				  					console.log(data);
				  					$('.detail_1').siblings('tr').remove();
				  					detail_flag = false;
				  					show(recordId,type,mobile,status,obj.curr,data);
				  		        },
				  				dataType:'json'
				  			}); 
				  		}
				  	}
				})
			}else if(type!=null && type== '34'){

				
				laypage({
				  	cont: $('#page2'), //容器。值支持id名、原生dom对象，jquery对象,
				  	pages: maxNum, //总页数
				  	skip: true, //是否开启跳页
				  	curr:pageNo,
				  	skin: '#2d8cf0',
				  	groups: 3, //连续显示分页数
				  	jump: function(obj, first){ //触发分页后的回调
				  		if(!first){
				  			$.ajax({
				  			 	url:"${ctx}/smsSendRecord/smsSendDetail",
				  				data:{"recordId":recordId,"type":type,"mobile":mobile,"status":status,"pageNo":obj.curr},
				  				type: "post",
				  				success:function(data){
				  					console.log(data);
				  					$('.detail_2').siblings('tr').remove();
				  					detail_flag = false;
				  					show(recordId,type,mobile,status,obj.curr,data);
				  		        },
				  				dataType:'json'
				  			}); 
				  		}
				  	}
				})
			}else if(type!=null && type== '35'){

				laypage({
				  	cont: $('#page3'), //容器。值支持id名、原生dom对象，jquery对象,
				  	pages: maxNum, //总页数
				  	skip: true, //是否开启跳页
				  	curr:pageNo,
				  	skin: '#2d8cf0',
				  	groups: 3, //连续显示分页数
				  	jump: function(obj, first){ //触发分页后的回调
				  		if(!first){
				  			$.ajax({
				  			 	url:"${ctx}/smsSendRecord/smsSendDetail",
				  				data:{"recordId":recordId,"type":type,"mobile":mobile,"status":status,"pageNo":obj.curr},
				  				type: "post",
				  				success:function(data){
				  					console.log(data);
				  					$('.detail_3').siblings('tr').remove();
				  					detail_flag = false;
				  					show(recordId,type,mobile,status,obj.curr,data);
				  		        },
				  				dataType:'json'
				  			}); 
				  		}
				  	}
				})
			}
				

    } else {
   	 if(type!=null && type== '33'){
	     		$('.detail1').append("<tr><td colspan='5'>没有相关数据,请重新查询或更改相关筛选条件!</td></tr>");
			}else if(type!=null && type== '34'){
				$('.detail2').append("<tr><td colspan='5'>没有相关数据,请重新查询或更改相关筛选条件!</td></tr>");
			}else if(type!=null && type== '35'){
				$('.detail3').append("<tr><td colspan='5'>没有相关数据,请重新查询或更改相关筛选条件!</td></tr>");
			}
    }
}





//删除相关短信记录并跳转页面
function delIt(Id,Type){
	var id = Id;
	var type = Type;
	if(confirm("您确定发送相关短信息?")){			
		$.ajax({
			url:"${ctx}/msgSendRecord/deleteSendRecord",
			data:{"recordId":id,"type":type},
			type: "post",
			success:function(data){
				if(data.message == true){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("短信删除成功!")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
					if(type == '33'){
						location.href="${ctx}/msgSendRecord/memberSendRecord";
					}else if(type == '34'){
						location.href="${ctx}/msgSendRecord/specificSendRecord";
					}else if(type == '35'){
						location.href="${ctx}/msgSendRecord/orderSendRecord";
					}
				}else{
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("短信删除失败,请联系系统管理员或者重新操作!")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
				}
			},
			dataType:'json'
		}); 
	}
}


function sendAgain(value,type){
	var recordId = value;
	//确认是否发送
	if(confirm("您确定再次发送该相关短信?")){
		location.href = "${ctx}/smsSendAppoint/specificNumSendAgain?recordId="+recordId+"&type="+type;
	}
}

 
//删除相关短信记录并跳转页面
function cancelSend(Id,Type,Status){
	var id = Id;
	var type = Type;
	var status = Status;
	if(confirm("您确定取消该定时发送?")){			
		$.ajax({
			url:"${ctx}/msgSendRecord/cancelSendRecord",
			data:{"recordId":id,"type":type,"status":status},
			type: "post",
			success:function(data){
				if(data.message == true){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("取消定时成功!")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
					location.href="${ctx}/msgSendRecord/toSendRecord";
				}else{
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("取消定时失败,请联系系统管理员或者重新操作!")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
				}
			},
			dataType:'json'
		}); 
	}
}
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

$(function(){
// 	$('.detail_2_pageView,.detail_1_pageView,.detail_3_pageView').on('click','span',function(){

// 		if($(this).hasClass('active'))return;
// 		var pageNum=$(this).attr('value');
// 		var recordId=$(this).attr('recordId');
// 		var type=$(this).attr('type');
// 		var mobile=$(this).attr('mobile');
// 		var status=$(this).attr('status');
// 		if(status=='null'){
// 			status=null;
// 		}
// 		if(mobile=='null'){
// 			mobile=null;
// 		}
// 		showDetail(recordId,type,mobile,status,pageNum);
// 	});
	
	toggleShow('.toggleTr','.toggleDiv','3');
	toggleShow('.toggleTr3','.toggleDiv3','4');
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
	$('#tser01').click(function(){
		$('#tser01').jeDate(start);
	});
	$('#tser02').click(function(){
		$('#tser02').jeDate(end);	
	});
	$('#tser03').click(function(){
		$('#tser03').jeDate(start);
	});
	$('#tser04').click(function(){
		$('#tser04').jeDate(end);	
	});
	$('#tser05').click(function(){
		$('#tser05').jeDate(start);
	});
	$('#tser06').click(function(){
		$('#tser06').jeDate(end);	
	});
	$('#tser07').click(function(){
		$('#tser07').jeDate(start);
	});
	$('#tser08').click(function(){
		$('#tser08').jeDate(end);	
	});
});

</script>
</html>
	