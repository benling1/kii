<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>客户管理-会员互动</title>
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
        <script src="https://oss.maxcdn.com/libs/respond.${ctx}/crm/js/1.3.0/respond.min.js"></script>
    <![endif]-->

	<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js" ></script> --%>
	<script type="text/javascript" src="${ctx}/crm/js/vip_interact.js" ></script>
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
				<div class="w1645 m_l30">
					<div class="w100_ h48 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							客户管理
						</div>
						<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
							
							
							<a class="c_384856" href="${ctx}/sellerGroup/findSellerGroup">
								<div class="f_l w140 text-center cursor_">
									会员分组
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/memberInformation/memberInformation">
								<div class="f_l w140 text-center cursor_">会员信息</div>
							</a>
							<a class="c_384856" href="${ctx}/memberInteraction/memberSmsSendAndReceIve">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									会员互动
								</div>
							</a>
							<a class="c_384856"
								href="${ctx}/crms/marketingCenter/smsBlack">
								<div class="f_l w140 text-center cursor_">黑名单列表</div>
							</a>
<%-- 							<a class="c_384856" href="${ctx}/crms/customerManagement/memberGradation"> --%>
<!-- 								<div class="f_l w140 text-center cursor_"> -->
<!-- 									会员等级划分 -->
<!-- 								</div> -->
<!-- 							</a> -->
							<%-- <a class="c_384856" href="${ctx}/crms/customerManagement/blacklistManagemen">
								<div class="f_l w140 text-center cursor_">
									黑名单管理
								</div>
							</a> --%>
						</div>
					</div>
					
				
				
						
					<!---------------会员互动--------------->
					<div class="m_b30">
						<!----------上部---------->
						<div class="w1605  bgc_f1f3f7 c_384856 p_l40 p_t30" style="height:7.1vw;">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								会员互动
							</div>
							<!---------------描述--------------->
							<div class="font14">
								接受买家短信回复，与客户一对一短信交流互动
							</div>
							
							<div class="font16 c_384856 h50 lh50" style="margin-top: 1.15vw;">
								<a class="c_384856" href="${ctx}/memberInteraction/sllerSmsReceiveUnread">
									<div class="w140 text-center f_l bgc_fff viphd_out cursor_" id="huiyuanhudongjilu" style="height: 2.7vw;">
										会员互动记录
									</div>
								</a>
								<a class="c_384856" href="${ctx}/memberInteraction/sllerSmsReceiveAll">
									<div class="w150 text-center f_l bgc_e3e7f0 viphd_out cursor_" id="maijiahuifuneirong" style="height: 2.7vw;">
										买家回复内容查询
									</div>
								</a>
								<%-- <a class="c_384856" href="${ctx}/memberInteraction/sellerSendHistory">
									<div class="w140 text-center f_l bgc_e3e7f0 viphd_out cursor_" id="maijiafasongjilu" style="height: 2.7vw;">
										卖家发送记录
									</div>
								</a> --%>
							</div>
							
						</div>
						<!----------下部:会员互动记录---------->
						<div class="w1605 bgc_fff p_t35 p_l40 p_b40 viphd_in" id="_huiyuanhudongjilu">
							
							<!--------查询设置-------->
							<div class="font14 c_384856 h50 lh50">
								<form action="${ctx}/memberInteraction/sllerSmsReceiveUnread" method="post">
								<div class="f_l m_r15">
									手机号码:
								</div>
								<input onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"  class="bgc_f4f6fa border0 w250 h50 f_l m_r35 phone" type="text" name="sendPhone" value="${sendPhone}" onblur="judgePhone(this.value);"/>
								<input class="w100 h50 b_radius5 bgc_fff m_r10 border_00a0e9 c_00a0e9 tk text-center f_l cursor_" type="submit" value="查询">
								</form>
								<div class="w100 send_text_btn b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_ display_none" style="height:2.455vw;">
									短信发送
								</div>
							</div>
							
							
							<div class="font16 c_384856 h22 lh22 m_t20 m_b30">
								提示：如客户回复短信内容符合N，会自动将手机号加入到店铺黑名单。
							</div>
							
							
							<!--------操作选项-------->
						 <form  method="post" action="${ctx}/memberInteraction/sllerSmsReceiveRemark" onsubmit="return remark()" >
							<input class="w180 bgc_fff font14 h52 m_b15 b_radius5 border_00a0e9 c_00a0e9 tk text-center cursor_" 
							 value="一键标记为已读"  type="submit" />

							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <%-- <th class="position_relative z_1 w65 p_l10 ">
								    	<div class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute " >
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div class="p_l20">全选</div>
								    </th> --%>
								    <th class="w60">序号</th>
								    <th class="w150">买家昵称</th>
								    <th class="w150">手机号码</th>
								    <th class="w425">短信内容</th>
								    <th class="w150">接收的手机号</th>
								    <th class="w150">最近回复时间</th>
								    <th class="w100">操作</th>
								  </tr>
								  
								   <c:if test="${pagination.list.size() ==0 }">
									  <tr class="">
									    <td class="position_relative p_l10 z_1 w65" colspan="8" align="center">没有相关数据,请重新查询或更改相关筛选条件!</td>
									  </tr>
								 	</c:if>
								
							 	  <c:if test="${pagination3.list.size() !=0 }">
							      <c:forEach items="${pagination.list }" var="smsReceiveInfo" varStatus="status">
								  <tr class="">
								    <%-- <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    <input type="hidden" name="ids" value="${smsReceiveInfo.id }"  />
								    </td> --%>
								    <input type="hidden" name="ids" value="${smsReceiveInfo.id }"/>
								    
								    <td class="w60 text-center">${status.index+1 }</td>
								    <td class="w150 text-center">${smsReceiveInfo.buyerNick }</td>
								    <td class="w150 text-center">${smsReceiveInfo.sendPhone }</td>
								    <td class="w405 text-center p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		${smsReceiveInfo.content }
								    	</div>
								    </td>
								    <td class="w150 text-center">${smsReceiveInfo.receivePhone }</td>
								    <td class="w150 text-center"><fmt:formatDate value="${smsReceiveInfo.receiveDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								    <td class="w100 text-center">
								    	<!-- <div class="chat_btn tk w58 h33 lh33 border_00a0e9 c_00a0e9 b_radius5 margin0_auto cursor_ text-center">
								    		查询
								    	</div> -->
								    	<a href="javascript:;" class="replyBtn" data-phone="${smsReceiveInfo.sendPhone }" >回复</a>
								    </td>
								  </tr>
								 </c:forEach>
								 </c:if>
								
								</table>
							</div>
							 </form>
							
							<!--------分页-------->
                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                              
                                <c:forEach items="${pagination.pageView }" var="page">
										${page } 
								</c:forEach>
                            </div>
							
						</div>
						<input type="hidden" id="src" value="${ctx}">
						<input type="hidden" id="smsqianming" value="">
						
						<div class="alertBox">
							<i></i>
							<div class="title">
								确定要发送吗？
							</div>
							<div class="btnBox">
								<a href="javascript:;" class="qr">确认</a>
								<a href="javascript:;" class="qx">取消</a>
							</div>
						</div>
						<div class="replyBOX">
							<h3>互动回复<i class="closeReplyBOX"></i></h3>
							<div class="showReplyListBox">
								<div class="clearfix">
									<i class="fl"></i>
									<p class="fl flP"><i></i>啦啦啦啦啦</p>
								</div>
								<div class="clearfix">
									<i class="fr"></i>
									<p class="fr frP"><i></i>啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦</p>
								</div>
								
							</div>
							<div class="replyTxtBox">
								<textarea></textarea>
							</div>
							<div class="replyBottomBox">
								<p class="showNum">已输入<span class="replyNum">0</span>个字，计费<span class="replyTiao">1</span>条</p>
								<div class="jfgz">
									<a href="javascript:;">计费规则</a>
									<p>一条短信70个字，短信内容超过70个字每条短信均按照67个字进行计费，其中汉字、英文字母、阿拉伯数字、标点符号均算作一个字。短信变量字数以实际发送为准。</p>
								</div>
								<a href="javascript:;" class="send">发送</a>
								
							</div>
						</div>
						<div id="markBg2" class=""></div>
						<!----------下部:买家回复内容---------->
						<div class="w1605 bgc_fff p_t35 p_l40 p_b40 viphd_in display_none" id="_maijiahuifuneirong">
							<form action="${ctx}/memberInteraction/sllerSmsReceiveAll" method="post">
							<!--------查询设置-------->
							<div class="font14 c_384856 h52 lh52 m_b35">
								<div class="f_l m_r15">
									手机号码:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35 phone"  type="text" name="sendPhone" onblur="judgePhone(this.value)" value="${sendPhone}" onkeyup="this.value=this.value.replace(/\D/g, '')"/>
								
								
								<div class="f_l m_r15">
									是否已读:
									<input id="status" type="hidden" name="status" value="${status}">
								</div>
								<div class="wrap f_l m_r40">
								    <div class="nice-select h50" name="nice-select">
								    	<input id="statusId" type="text" readonly value="全部">
								    	<ul id="statusUl" style="z-index:2;">
								    		<li value="2" >全部</li>
								    		<li value="0" >否</li>
								    		<li value="1" >是</li>
								    	</ul>
								  	</div>
								</div>
								<div class="f_l m_r15">
									买家昵称:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" type="text" name="buyerNick" value="${buyerNick}"/>
								
								<input onclick="submitButton()" class="w100 f_l h50 bgc_fff b_radius5 border_00a0e9 c_00a0e9 tk text-center cursor_" type="submit" value="查询">
							</div>
							
							<div class="h52 lh52 m_b10">	
								<div class="f_l m_r15 c_384856">
									发送时间:
								</div>
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" id="tser01" value="<fmt:formatDate value='${beginTime }' pattern='yyyy-MM-dd HH:mm:ss' />" name="beginTime" readonly onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
									
								<div class="f_l">~</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" id="tser02" value="<fmt:formatDate value='${endTime }' pattern='yyyy-MM-dd HH:mm:ss' />" name="endTime" readonly onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})"  >
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								<div class="f_l m_r15 c_384856">
									短信内容:
									<input id="ifContain" name="ifContain" type="hidden" value="${ifContain}">
								</div>
								<div class="wrap f_l m_r20">
								    <div class="nice-select h50" name="nice-select">
								    	<input id="ifContainId" type="text" value="全部" readonly />
								    	<ul id="containUl">
								    		<li value="0">全部</li>
								    		<li value="1">不包含N</li>
								    		<li value="2">包含N</li>
								    	</ul>
								  	</div>
								</div>
								<vid class="bgc_f4f6fa border0 w400 h50 f_l m_l10 m_r40" />（可选择包含或者不包含N查询退订短信客户） </div>
								<%-- <input  type="hidden" name="smsParam" value="${smsParam}" id="judgTN" class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r40" /> --%>
							</form>
							
							
							
							
							<!--------导出-------->
							<div class="h50 p_r350 m_b10">
								<input class="w100 f_l h50 bgc_fff b_radius5 border_00a0e9 c_00a0e9 tk text-center cursor_" type="button" onclick="checkout()" value="导出"/>
							</div>
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="w150">序号</th>
								    <th class="w150">买家昵称</th>
								    <th class="w150">手机号码</th>
								    <th class="w425">短信内容</th>
								    <th class="w150">是否已读</th>
								    <th class="w150">发送时间</th>
								  </tr>
								  
								  <c:if test="${pagination2.list.size() !=0 }">
								     <c:forEach items="${pagination2.list }" var="smsReceiveInfo" varStatus="status">
								  <tr class="">
								  	<input type="hidden" name="idss" value="${smsReceiveInfo.id }" id="id_${status.index+1 }" />
								    <td class="w150 text-center">${status.index+1 }</td>
								    <td class="w150 text-center">${smsReceiveInfo.buyerNick }</td>
								    <td class="w150 text-center">${smsReceiveInfo.sendPhone }</td>
								    <td class="w405 text-center p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		${smsReceiveInfo.content }
								    	</div>
								    </td>
								    <td class="w100 text-center">
									    <ul>
										    <c:if test="${smsReceiveInfo.status ==1 }"><li >是</li></c:if>
										    <c:if test="${smsReceiveInfo.status ==0 }"><li >否</li></c:if>
									    </ul>
								    </td>
								    <td class="w150 text-center"><fmt:formatDate value="${smsReceiveInfo.receiveDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								  </tr>
								  	</c:forEach>
								 
								  </c:if>
								   <c:if test="${pagination2.list.size() ==0 }">
								  	<tr class="">
								    <td colspan="6" align="center">没有相关数据,请重新查询或更改相关筛选条件!</td>
								  	</tr>
								  </c:if>
								</table>
							</div>
							<!--------分页-------->
                            <div class="p_r430 h24 m_t22 font14 c_8493a8 m_b40">
                              
                                <c:forEach items="${pagination2.pageView }" var="page">
										${page } 
								 </c:forEach>
                            </div>
							
						</div>
					
						<!----------下部:卖家发送记录---------->
						<div class="w1605 bgc_fff p_t35 p_l40 p_b40 viphd_in display_none" id="_maijiafasongjilu">
							
							<!--------查询设置-------->
							<form action="${ctx}/memberInteraction/sellerSendHistory" method="post">
							<div class="font14 c_384856 h52 lh52 m_b35">
								<div class="f_l m_r15">
									手机号码:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35 phone" name="phone" value="${phone }" type="text" onblur="judgePhone(this.value)" onkeyup="this.value=this.value.replace(/\D/g, '')"/>
								
								<!-- 隐藏选中的ul中lid值 -->
								<input type="hidden" class="_ul2" name="status" value="${status }" />
								<div class="f_l m_r15">
									发送状态:
								</div>
								<div class="wrap f_l m_r40">
								    <div class="nice-select h50" name="nice-select">
								    	<input class="" type="text" readonly
								    		value="<c:if test='${status ==null }'>全部</c:if><c:if test='${status ==1 }'>发送成功</c:if><c:if test='${status ==2 }'>手机号码不正确</c:if><c:if test='${status ==3 }'>短信余额不足</c:if><c:if test='${status ==4 }'>重复被屏蔽</c:if><c:if test='${status ==5 }'>黑名单</c:if>" />
								    	<ul  id="_ul2"style="z-index:2;">
								    		<li value="">全部</li>
								    		<li value="1">发送成功</li>
								    		<li value="2">手机号码不正确</li>
								    		<li value="3">短信余额不足</li>
								    		<li value="4">重复被屏蔽</li>
								    		<li value="5">黑名单</li>
								    	</ul>
								  	</div>
								</div>
								<div class="f_l m_r15">
									卖家昵称:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r100" name="nickname" value="${nickname }" type="text"/>
								
								<input class="w100 f_l h50 bgc_fff b_radius5 border_00a0e9 c_00a0e9 tk text-center cursor_" type="submit" value="查询">
							</div>
							
							<div class="h52 lh52 m_b10">	
								<div class="f_l m_r15 c_384856">
									发送时间:
								</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" id="tser03"  name="beginTime" value="<fmt:formatDate value='${beginTime }' pattern='yyyy-MM-dd HH:mm:ss' />" readonly onclick="$.jeDate('#tser03',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})" id="dateTime3">
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
									
								<div class="f_l">~</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" id="tser04" name="endTime" value="<fmt:formatDate value='${endTime }' pattern='yyyy-MM-dd HH:mm:ss' />" readonly onclick="$.jeDate('#tser04',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})" onblur="judgeDateTime();" id="dateTime4">
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
							
								
							</div>
							</form>	
							
							<!--------导出-------->
							<form action="${ctx}/memberInteraction/sellerSendHistoryCheckout" method="post" >
							<div class="h50 p_r350 m_b10">
								<!-- <div class="w100 f_l h50 lh50 b_radius5 border_00a0e9 c_00a0e9 tk text-center cursor_" onclick="checkout()">
										导出
								</div> -->
								<input class="w100 f_l h50 bgc_fff b_radius5 border_00a0e9 c_00a0e9 tk text-center cursor_" type="submit" value="导出" />
							</div>
							<div class="c_384856 font14 m_b15">温馨提示：短信记录目前只保留2015年10月1日之后的记录。</div>
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								   <%--  <th class="position_relative z_1 w65 p_l10 ">
								    	<div class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute quan_xuan_1">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div class="p_l20">全选</div>
								    </th> --%>
								    <th class="w60">序号</th>
								    <th class="w150">买家昵称</th>
								    <th class="w150">手机号码</th>
								    <th class="w425">短信内容</th>
								    <th class="w100">短信通道</th>
								    <th class="w90">发送状态</th>
								    <th class="w120">实际扣费(条)</th>
								    <th class="w130">发送时间</th>
								  </tr>
								  
								  <!-- 卖家发送记录的分页列表 -->
								  <c:if test="${pagination3.list.size() ==0 }">
								  <tr class="font12">
								    <td colspan="9" align="center">没有相关数据,请重新查询或更改相关筛选条件!</td>
								  </tr>
								  </c:if>
								  
								  <c:if test="${pagination3.list.size() !=0 }">
								     <c:forEach items="${pagination3.list }" var="smsSendInfo" varStatus="status">
								  <tr class="font12">
								   <%--  <td class="position_relative p_l10 z_1 w65 id="dx">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    		<input type="hidden" name="ids" value="${smsSendInfo.id}" />
								    	</div>
								    	<div></div>
								    </td> --%>
								    <input type="hidden" name="ids" value="${smsSendInfo.id}" />
								    <td class="w60 text-center">${status.index+1 }</td>
								    <td class="w150 text-center">${smsSendInfo.nickname }</td>
								    <td class="w150 text-center">${smsSendInfo.phone }</td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		${smsSendInfo.content }
								    	</div>
								    </td>
								    <td class="w100 text-center">${smsSendInfo.channel }</td>
								    <td class="w90 text-center">
								    	<ul>
								    	<c:if test="${smsSendInfo.status ==1 }"><li >发送成功</li></c:if>
								    	<c:if test="${smsSendInfo.status ==2 }"><li >手机号码不正确</li></c:if>
								    	<c:if test="${smsSendInfo.status ==3 }"><li >短信余额不足</li></c:if>
								    	<c:if test="${smsSendInfo.status ==4 }"><li >重复被屏蔽</li></c:if>
								    	<c:if test="${smsSendInfo.status ==5 }"><li >黑名单</li></c:if>
								    	</ul>
								    </td>
								    <td class="w120 text-center">${smsSendInfo.actualDeduction }</td>
								    <td class="w130 text-center"><fmt:formatDate value="${smsSendInfo.sendTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								  </tr>
								  </c:forEach>
								  </c:if>
								</table>
							</div>
							</form>
							<!--------分页-------->
                            <div class="p_r430 h24 m_t22 font14 c_8493a8 m_b40">
                                 <c:forEach items="${pagination3.pageView}" var="page">
										${page} 
								 </c:forEach>
                            </div>
							
						</div>
					
					
					</div>
					
						
					
				</div>	
				
							
				</div>
		
			</div>
	




	
    <div class="chat_window display_none">
        <div class="pew">
            
            <div class="bor_b_2_bfbfbf h53">
            	
	            <span class="font18 m_r100">会员互动</span>
	            <span class="font14">手机号：</span>
	            <span class="font14 m_r30">13911111111</span>
	            <span class="font14">买家：</span>
	            <img class="cursor_" src="${ctx}/crm/images/wangwang.png" />
	            <span class="font14 m_r32 cursor_">xxxxxx</span>
	            <img class="close cursor_" src="${ctx}/crm/images/close_wangwang.png" />
	            
            </div>
            
        </div>
        
        <div class="b_radius5 overflow-y w490 h280 bgc_fff margin0_auto p_l15 p_r15 p_t15 p_b15">
        	
        	<div class="f_r m_b20">
        		<!----------发送内容---------->
        		<div class="f_l m_r10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">
        			
        			<p class="m_b15">2016-10.15 15:30</p>
        			<p>
        				一区二三里
        			</p>
        		
        		</div>
        		<!----------头像---------->
        		<div class="f_l w37 h37 b_radius50 cursor_">
        			<img class="w37 h37 b_radius50" src="${ctx}/crm/images/user.png" />
        		</div>
        	</div>
        	
        	<div class="f_l m_b20">
        		<!----------发送内容---------->
        		<div class="f_r m_l10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">
        			
        			<p class="m_b15">2016-10.15 15:30</p>
        			<p>
        				烟村四五家
        			</p>
        		
        		</div>
        		<!----------头像---------->
        		<div class="f_r w37 h37 b_radius50 cursor_">
        			<img class="w37 h37 b_radius50" src="${ctx}/crm/images/user.png" />
        		</div>
        	</div>
        	
        	<div class="f_r m_b20">
        		<!----------发送内容---------->
        		<div class="f_l m_r10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">
        			
        			<p class="m_b15">2016-10.15 15:30</p>
        			<p>
        				亭台六七座
        			</p>
        		
        		</div>
        		<!----------头像---------->
        		<div class="f_l w37 h37 b_radius50 cursor_">
        			<img class="w37 h37 b_radius50" src="${ctx}/crm/images/user.png" />
        		</div>
        	</div>
        	
        	<div class="f_l m_b20">
        		<!----------发送内容---------->
        		<div class="f_r m_l10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">
        			
        			<p class="m_b15">2016-10.15 15:30</p>
        			<p>
        				八九十只花
        			</p>
        		
        		</div>
        		<!----------头像---------->
        		<div class="f_r w37 h37 b_radius50 cursor_">
        			<img class="w37 h37 b_radius50" src="${ctx}/crm/images/user.png" />
        		</div>
        	</div>
        	
        </div>
        
        <div class="font12 c_384856 text-center m_t15 m_b15 p_r15">
        	<span>注：加签注【关欣康家用医疗器材】已输入<span class="c_ff6363 text_count" >0</span>个字，预计扣费<span class="c_ff6363" >1</span>条短信</span>
        	<span class="cursor_ f_r">计费规则</span>
        </div>
        
        
        <div class="w520 margin0_auto h95 margin0_auto position_relative">
        	
        	
        	<!----------内容输入区---------->
			<textarea class="text_area b_radius5 p_l10 p_r10 p_t10 p_b25 f_l w500 h60 bgc_f4f6fa border0"></textarea>
        	
        	
			<div class="w80 h20 bgc_ececec lh20 position_absolute right20 top70">
				<div class="f_l">
					退订回
				</div>
				<div class="f_l">
					"<input maxlength="2" class="w15 text-center border0 bgc_ececec c_384856" />"
				</div>
			</div>
			
			<div class="c_00a0e9 f_r font14 m_t10 text-right m_b40">
				<span class="cursor_">转化短网址</span>
				<span class="m_l30 m_r25 cursor_">引用短语库</span>
				<span class="cursor_ ">另存为短语库</span>
			</div>
			
			<div class="w232 h42 margin0_auto">
				<div class="save_chat w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ ">确认保存</div>
				<div class="close m_l30 cancel_hmd f_l border_00a0e9 cursor_ w100 h40 lh40 text-center b_radius5 c_00a0e9">取消</div>
			</div>
        	
        	
        </div>
        
        
        
    </div>
	
	
<!---------------发送短信弹窗--------------->
<div class="w100_ h1100 rgba_000_5 position_absolute z_10 top0 text_window display_none">
	
	<div class="w1060 h530 bgc_fff margin0_auto p_t20 p_l20 p_r20 p_b20 position_fixed top240 left330">
		
		<div class="text-center c_384856 font16 m_b20 h22 lh22">
			短信发送
		</div>
		
		<div class="m_l70 h260">
			<div class="f_l c_384856 m_r10">
				短信内容:
			</div>
			<div class="   position_relative">
				<textarea class="p_l10 p_r10 p_t10 p_b25 f_l w710 h225 bgc_f4f6fa border0" id="messageContent" onKeyup="editMessage()" onBlur="leaveEditMessage()"></textarea>
				
			
				<div class="w440 c_cecece p_l100 h40 lh40 b_radius5 f_l position_absolute " style="top:11.5vw">
					已输入：<span class="text_count" id="inputNum">0</span>/500字
					
					当前计费：<span id="contePrice">0</span> 条
					<a href="${ctx }/crms/home/notice#duanxinxiangguan" class="c_00a0e9 cursor_" target="_blank">计费规则</a>
				</div>
				<div class="w100 h25 bgc_ececec lh20 position_absolute right230  f_l" style="top:12vw">
					<div class="f_l">
						退订回
					</div>
					<div class="f_l">
						"<input maxlength="2" class="w15 text-center border0 bgc_ececec c_384856" readOnly id="isTui" value="N"/>"
					</div>
				</div>
			</div>	
		</div>
		
		
		<div class="c_00a0e9 font14 m_t10 text-right p_r200">
			<span class="cursor_ ChangeLink">转化为短连接</span>
			<span class="m_l30 m_r25 cursor_ Library">引用短语库</span>
			<span class="cursor_ ClickPhrase">另存为短语库</span>
		</div>
		
		<div class="c_384856 font14 p_l70">
			<span>短信签名：</span>
			<span><c:if test="${ShopName !=null}">【${ShopName}】</c:if></span>
		</div>
		
		
		
		<div class="c_ff6363 p_l150 m_t10 m_b20 font14">
			
		</div>
		
		<div class="c_384856 font14 h50 lh50 p_l70 m_b30">
			<span>手机号码：</span>
			<input class="w500 h50 border0 bgc_f4f6fa" id="cellPhone"/>
		</div>
		
		<div class="w232 h42 margin0_auto">
			<div class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_  font16" onclick="sendMessage()">保存</div>
			<div class="m_l30 close f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
		</div>
		
	</div>
	
</div>







<!--另存为短语库start-->
<div class="rgba_000_5 w1920 h100_ display_none ChoicePhrase">
    <div class="w490 h230 p_b10 bgc_fff m_t250 m_l500">
        <div class="h64 lh64 m_b10 text-center font16">另存为短语库</div>
        <div class="w426 margin0_auto">
            <input class="w411 h42 b_radius5 border0 outline_none bgc_f4f6fa p_l15 c_cecece"  type="text" placeholder="不限" id="smsTemplateName"/>
        </div>

        <!--确定保存start-->
        <div class="w490 h42 m_t36 margin0_auto">
            <div class="w260 margin0_auto">
                <div class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ PhraseOut" onclick="saveTemplate()">
                    确定
                </div>
                <div class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l50 f_l cursor_ PhraseOut">
                    取消
                </div>
            </div>
        </div>
        <!--确定保存end-->

    </div>
</div>





<!--添加链接弹出框start-->
<div class="rgba_000_5 w1920 h100_ display_none ChoiceLink">
    <div class=" h530 p_b40 bgc_fff m_t100 m_l500" style="width:34.8vw;">
        <div class="h60 lh60 text-center font16 bgc_f1f3f7" style="width:34.8vw;">添加短链接</div>
        <input id="linkData_input" type="hidden"  value="0">
        <ul class="h53 lh50 bgc_f1f3f7">
            <li onclick="linkDataInput('0')" class="f_l h53 w140 text-center bgc_fff TaoBaoLianJie1">淘宝短链</li>
            <!-- <li onclick="linkDataInput('1')" class="f_l h53 w140 text-center bgc_e3e7f0 TaoBaoLianJie1">客户端短链</li> -->
        </ul>
        <form action="#" class="p_l24 p_t30 p_r20 TaoBaoLianJie2">
            <p class="l_h32 font14 m_b30">
                说明：网址必须是taobao.com、tmall.com、jaeapp.com这三个域名下。<br/>
                点击效果,需在淘宝后台ECRM中查看提示：默认网站、后都加空格，可确保手机打开无异常兼容设备识别。另外直接从外部复制粘贴的网址也请务必前后加空格，否则将导致无法正常打开。
            </p>
            <div>
            <input id="linkType" type="hidden" value="商品链接">
                <div class="f_l m_r20">
                    <div class="f_l font14  lh45">
                        类型:
                    </div>
                </div>
                <div class="f_l m_r15">
                    <div onclick="linkData('商品链接')" class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK bgc_check_blue ShopLianJie">
                    </div>
                    <div class="m_l10 f_l font14  lh45 c_8493a8">
                        商品链接
                    </div>
                </div>
                <div class="f_l m_r15">
                    <div onclick="linkData('店铺首页')" class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK DianPuShouYe">
                    </div>
                    <div class="m_l10 f_l font14  lh45 c_8493a8">
                        店铺首页
                    </div>
                </div>
                <div class="f_l m_r15">
                    <div onclick="linkData('活动页链接')" class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK HuoDongLianJie">
                    </div>
                    <div class="m_l10 f_l font14  lh45 c_8493a8">
                        活动页链接
                    </div>
                </div>
                <div class="f_r m_r15">
                <a href="https://crm.bbs.taobao.com/detail.html?spm=a210m.7802402.0.0.zeU4Ci_a210m.7802402.0.0.lk6D3P_a210m.7802402.0.0.Dd9Nw4&postId=1622316" target="_blank">
                    <div class="m_l10 f_l font14 c_00a0e9 lh45" >
                        淘宝短链接使用教程
                    </div>
                    </a>
                </div>
            </div>
            <div class="clear"></div>
            <div class="m_t20 ShopId">
                <span class="font14 p_r20">商品ID:</span>
                <input onkeyup="judgNum()" id="commodityId" class="h42 l_h42 p_l15 outline_none border0 b_radius5 w274 bgc_f4f6fa" type="text"/>
            </div>
            <div class="HuoDongWangZhi display_none">
            	<div class="m_b10">
               		 <p class="f_l">活动网址 : </p>
                     <input id="activityUrl" class="w500 h65 border0 bgc_f4f6fa b_radius8 f_l" type="text" value="">
                </div>
                <div class="clear font14 c_cbcdcf m_t15 p_t10">
                	常见问题：PC端自定义活动页面，因淘宝无线端没有对应的H5活动页面如直接转化可能会导致跳转至手淘店铺首页，解决方法：建议先到无线运营中心->无线店铺->店铺装修->自定义页面”生成H5活动页面，再进行转化。
                </div>
            </div>
        </form>
        <form action="#" class="p_l24 p_t30 p_r40 p_l40 display_none TaoBaoLianJie2">
            <p class="l_h32 font14 m_b30">
                提示：默认网址前、后都加空格，可确保手机打开无异常兼容设备识别。另外直接从外部复制
                粘贴的网址也请务必前后加空格，否则将导致无法正常打开。
            </p>
            <textarea id="kehuduan" class="w540 h160 b_radius5 p_l15 p_t15 c_cecece outline_none border0 bgc_f4f6fa">请输入网址：</textarea>
        </form>
        <!--确定保存start-->
        <div class="w630 h42 m_t20 margin0_auto">
            <div class=" margin0_auto" style="width: 13.625vw;">
                <div onclick="linkSubmit()" class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ LinkOut">
                    确定
                </div>
                <div class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l50 f_l cursor_ LinkOut">
                    取消
                </div>
            </div>
        </div>
        <!--确定保存end-->
    </div>
</div>






<!--引用短语库 start-->
<div class="rgba_000_5 w1920 h100_  PhraseLibrary z_10 display_none text_model_window">
    <div class="w1000 h580 p_b33 bgc_fff m_t100 m_l300">
	    <div class="w1000 h103 p_t20 bgc_f1f3f7 font16">
        	<div class="text-center w1000">引用模板</div>
            <ul class=" p_t41 font14 m_l34" style="height:1.9vw;">
                <li onclick="findTemplate('默认')" class="f_l w140 text-center l_h40 bgc_fff    cursor_ DYK1" style="height:1.9vw;">系统短语库</li>
                <li onclick="findTemplate('自定义')" class="f_l w140 text-center l_h40 bgc_e3e7f0 cursor_ DYK1" style="height:1.9vw;">自定义短语库</li>
            </ul>
        </div>
        <div class="w936 h546 margin0_auto DYK2">
        	<div class="font14 c_384856 h45 l_h45">
                <div class="f_l">
                    <div class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK bgc_check_blue" onclick="findTemplate('默认')">
                    </div>
                    <div class="m_l10 f_l font14 c_384856">
                        默认
                    </div>
                </div>
                <div class="f_l m_l10">
                    <div class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK" onclick="findTemplate('常规版')">
                 	</div>
                    <div class="m_l10 f_l font14 c_384856">
                        常规版
                    </div>
                </div>
                <div class="f_l m_l10">
                    <div class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK" onclick="findTemplate('创意版')">
                 	</div>
                    <div class="m_l10 f_l font14 c_384856">
                        创意版
                    </div>
                </div>
                <div class="f_l m_l10">
                    <div class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK" onclick="findTemplate('幽默版')">
                    </div>
                    <div class="m_l10 f_l font14 c_384856">
                        幽默版
                    </div>
                </div>
                <div class="f_l m_l10">
                    <div class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK" onclick="findTemplate('萌版')">
                    </div>
                    <div class="m_l10 f_l font14 c_384856">
                        萌版
                    </div>
                </div>
                <div class="f_l m_l10">
                    <div class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK" onclick="findTemplate('按时间更新')">
                    </div>
                    <div class="m_l10 f_l font14 c_384856">
                        按时间更新
                    </div>
                </div>
    		</div>
            
            <div class="h300 overflow_auto">
               <table border="0" class="font14 m_t13 temp_table1">
                      	<tr class="bgc_e3e7f0 temp_tr1">
                           <td class="w77 h50 text-center" >序号</td>
                           <td class="w500 h50 text-center">短信内容</td>
                           <td class="w128 h50 text-center">热度</td>
                           <td class="w120 h50 text-center"></td>
                       </tr>
               </table>
            </div>
             <div class="w936 h42 m_t50 margin0_auto">
                 <div class="w214 margin0_auto">
                     <div class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ LibraryOut">
                        	 确定
                     </div>
                     <div class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ LibraryOut">
                         	取消
                     </div>
                 </div>
             </div>
            
            <!--分页样式start-->
            
            <!--分页样式end-->
            <!--确定保存start-->
  			
            <!--确定保存end-->
        </div>
		 
		<div class="w936 h546 margin0_auto DYK2 display_none">
           
            <div class="h300 overflow_auto">
          		 <table border="0" class="font14 m_t45 temp_table2">
                   <tr class="bgc_e3e7f0 temp_tr2">
                       <td class="w77 h50 text-center" >序号</td>
                       <td class="w258 h50 text-center">模板名称</td>
                       <td class="w595 h50 text-center">短信内容</td>
                       <td class="w100 h50 text-center" ></td>
                   </tr>
           	</table>
            </div>
           <!--分页样式start-->
          
           <!--分页样式end-->
           <!--确定保存start-->

               <div class="w936 h42 m_t50 margin0_auto">
                   <div class="w214 margin0_auto">
                       <div class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ LibraryOut">
                          		 确定
                       </div>
                       <div class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ LibraryOut">
                          	 取消
                       </div>
                   </div>
               </div>

           <!--确定保存end-->
        </div> 	
    </div>
</div>

                  



				
</body>
<script type="text/javascript">

//定义一个短信签名的全局路劲
var  shopName = null;

	$(function(){
		$(".load_top").load("top.html");
		$(".load_left").load("left.html");
	})
	
	//买家回复内容查询是否已读选择
	$('#statusUl li').click(function(){
		$('#status').val($(this).val());
	})
	//买家回复内容查询是否包含N
	$('#containUl li').click(function(){
		$('#ifContain').val($(this).val());
	})	
	
	//页面一加载获取店铺签名
	 //shopName="${shopName}" 
	 shopName="lllll"
	if(shopName !=null && shopName != ""){
		$("#messageContent").val("【"+shopName+"】");
		//计算短信长度和字数
		var content = $("#messageContent").val();
		var len=content.length;
		if(len>70){
			$("#contePrice").text(Math.ceil(len/67));
		}else{
			if(len==0){
				$("#contePrice").text(0);
			}else{
				$("#contePrice").text(1);
			}
		}
		$("#inputNum").text(len);
	}
	
		 
	 
	
	//获取选中的ul->li值
	window.onload = function(){
		//读取第二个ul中选中的值 并赋值到第一个弹窗的form中的<input type="hidden" name=status中
        var obj_lis2 = document.getElementById("_ul2").getElementsByTagName("li");
        for(i=0;i<obj_lis2.length;i++){
            obj_lis2[i].onclick = function(){
            	//alert($(this).val());
            	$("._ul2").val($(this).val()==0?"":$(this).val());
            }
	   }
	}
	
	//会员互动->一键标记为已读:表单提交确认
	function remark(){
		if(window.confirm("确认将列表中的全部信息标记为已读?")){
			return true;
		}else{
			return false;
		}
		
	}
	
	//买家内容回复-->导出
	function checkout(){
		//获取所有的ids值
		var arr = new Array();
		var inputs = document.getElementsByName("idss");
		for(var i =0;i<inputs.length;i++){
			arr[i] = inputs[i].value;
		}
		location.href="${ctx}/memberInteraction/sllerSmsReceiveCheckout?ids="+arr;
	}
	//页面加载时的选择框
	$(function(){
		var show =<%=request.getAttribute("show")%>;
		//会员互动记录
		if(show!=null&&show==1){			
	 	 $("#maijiahuifuneirong").removeClass("bgc_fff").addClass("bgc_e3e7f0");
	     $("#huiyuanhudongjilu").addClass("bgc_fff").removeClass("bgc_e3e7f0");
	     $("#maijiafasongjilu").removeClass("bgc_fff").addClass("bgc_e3e7f0");
	     
	    $("#_maijiahuifuneirong").hide();
	     $("#_huiyuanhudongjilu").show();
	     $("#_maijiafasongjilu").hide();
		} 

		//卖家内容回复
		else if(show!=null&&show==2){			
	 	 $("#huiyuanhudongjilu").removeClass("bgc_fff").addClass("bgc_e3e7f0");
	     $("#maijiahuifuneirong").addClass("bgc_fff").removeClass("bgc_e3e7f0");
	     $("#maijiafasongjilu").removeClass("bgc_fff").addClass("bgc_e3e7f0");
	     
	    $("#_huiyuanhudongjilu").hide();
	     $("#_maijiahuifuneirong").show();
	     $("#_maijiafasongjilu").hide();
		} 

		//卖家发送记录
		else if(show!=null&&show==3){
	 	 $("#huiyuanhudongjilu").removeClass("bgc_fff").addClass("bgc_e3e7f0");
	     $("#maijiafasongjilu").addClass("bgc_fff").removeClass("bgc_e3e7f0");
	     $("#maijiahuifuneirong").removeClass("bgc_fff").addClass("bgc_e3e7f0");
	     
	     $("#_huiyuanhudongjilu").hide();
	     $("#_maijiafasongjilu").show();
	     $("#_maijiahuifuneirong").hide(); 
		}
		
		//回显是否已读
		var statusVal = $('#status').val();
		if(statusVal == '1'){
			$('#statusId').val('是');
		}else if(statusVal == '0'){
			$('#statusId').val('否');
		}else{
			$('#statusId').val('全部');
		}
		
		//回显短信内容包含N
		var containVal = $('#ifContain').val();
		if(containVal == '1'){
			$('#ifContainId').val('不包含N');
		}else if(containVal == '2'){
			$('#ifContainId').val('包含N');
		}else{
			$('#ifContainId').val('全部');
		}
	})
	
	
	//------------------------短信发送弹框----------------------------------
		function sendMessage(){
			var content = $("#messageContent").val();
			//var ist = $("#isTui").val();
			var phoneNum = $("#cellPhone").val();
			//判断短信内容不能为空
			if(content ==null ||content ==''){
	    		$(".tishi_2").show();
	    		$(".tishi_2").children("p").text("短信内容不能为空!")
	    		setTimeout(function(){ 
	    			$(".tishi_2").hide()
	    		},3000)
				return;
			}
			//判断手机号必需匹配正则
			var phoneNum = phoneNum.trim();
			if(phoneNum=="" ||phoneNum ==null || !(/^1[34578]\d{9}$/.test(phoneNum))){ 
	    		$(".tishi_2").show();
	    		$(".tishi_2").children("p").text("手机号码有误或为空，请重填!")
	    		setTimeout(function(){ 
	    			$(".tishi_2").hide()
	    		},3000)
		        return; 
		    } 
			
			if(shopName=="" ||shopName ==null){ 
	    		$(".tishi_2").show();
	    		$(".tishi_2").children("p").text("短信签名不能为空")
	    		setTimeout(function(){ 
	    			$(".tishi_2").hide()
	    		},3000)
		        return; 
		    } 
			
			//确认是否发送
			if(confirm("您确定发送相关短信?")){
				$.ajax({
					url:"${ctx}/smsSendAppoint/sendSingleMessage",
					data:{"content":content,"phoneNum":phoneNum,"autograph": shopName},
					type: "post",
					success:function(data){
						if(data.status != "100"){
							//弹框不能关闭
							$(".text_window").show();

							$(".tishi_2").show();
							$(".tishi_2").children("p").text(data.message)
							setTimeout(function(){ 
								$(".tishi_2").hide()
							},3000)
						}else{

							$(".tishi_2").show();
							$(".tishi_2").children("p").text(data.message)
							setTimeout(function(){ 
								$(".tishi_2").hide()
							},3000)
							$(".text_window").hide();
						}
					},
					dataType:'json'
				});
			}
		}
	
	//-------短信设置-------------------------------------
	   
	   function findTemplate(smsTemp){
	 		$('.temp_tr1').siblings().remove();
	 		$('.temp_tr2').siblings().remove();
	 		$.ajax({
	 			url:"${ctx}/crms/smsTemplate",
	 			type:"post",
	 			data:{"type":smsTemp},
	 			dataType:"json",
	 			success:function(data){
	 				var smsTemplate = data.smsTemplate;
	 				var appendTemp = "";
	 				if(smsTemp == "自定义"){
	 					$.each(smsTemplate,function(i,result){
	 						appendTemp = "<tr class='bgc_fafafa'><td class='w77 h50 text-center' >"+i+"</td><td class='w500 h50 text-center'>"+result.name+"</td><td class='w128 h50 text-center'>"+result.content+"</td><td class='w120 h50 text-center'><div  class='w100 h42 l_h42 bor_00a0e9 margin0_auto b_radius5 c_00a0e9 Template_div '>使用<input type='hidden' value='"+result.content+"' /></div></td></tr>";
	 						$('.temp_table2').append(appendTemp);
	 					});
	 				}else{
	 					$.each(smsTemplate,function(i,result){
	 						appendTemp = "<tr class='bgc_fafafa'><td class='w77 h50 text-center' >"+i+"</td><td class='w500 h50 text-center'>"+result.content+"</td><td class='w128 h50 text-center'>"+result.fashion+"</td><td class='w120 h50 text-center'><div  class='w100 h42 l_h42 bor_00a0e9 margin0_auto b_radius5 c_00a0e9 Template_div '>使用<input type='hidden' value='"+result.content+"' /></div></td></tr>";
	 						$('.temp_table1').append(appendTemp);																																																					
	 					});
	 				}
	 			}
	 		});
	 	}
	   
	 //点击使用将模板添加到文本域中
	 	$(document).on("click", ".Template_div", function () {
	 		var content = $(this).children().val();
	 		$("#messageContent").val(content);	
			$(".text_model_window").hide();		
	 	});
	   
	   
	   
	   //------------------另存为短语库---------------------
	 	function saveTemplate(){
	 		//短语模板的名字
	 		var smsName = $('#smsTemplateName').val();
	 		var smsContent = $('#messageContent').val();
	 		
	 		if(smsContent==null || smsContent ==''){
	    		$(".tishi_2").show();
	    		$(".tishi_2").children("p").text("短信内容不能为空!")
	    		setTimeout(function(){ 
	    			$(".tishi_2").hide()
	    		},3000)
				return false;
 			}
 			if(smsName==null || smsName ==''){
	    		$(".tishi_2").show();
	    		$(".tishi_2").children("p").text("标题内容不能为空!")
	    		setTimeout(function(){ 
	    			$(".tishi_2").hide()
	    		},3000)
				return false;
 			}
	 		if(confirm("您确认保存该短语")){
	 			$.ajax({
	 				url:"${ctx}/crms/saveSmsTemplate",
	 				type:"post",
	 				data:{"smsName":smsName,"smsContent":smsContent},
	 				success:function(data){
	 					if(data.message){
	 			    		$(".tishi_2").show();
	 			    		$(".tishi_2").children("p").text("保存短语成功")
	 			    		setTimeout(function(){ 
	 			    			$(".tishi_2").hide()
	 			    		},3000)
	 					}else{
	 			    		$(".tishi_2").show();
	 			    		$(".tishi_2").children("p").text("保存短语失败")
	 			    		setTimeout(function(){ 
	 			    			$(".tishi_2").hide()
	 			    		},3000)
	 					}
	 				}
	 			});
	 		}
	 	}

	 	//获得生成短连接的类型
		function linkData(data){
			$('#linkType').val(data);
		}
		//短连接生成
		 function linkDataInput(data){
			$("#linkData_input").val(data);
		}
		//根据生成链接类型，将请求发送到后台生成短链接
		function linkSubmit(){
			var linkData = $("#linkData_input").val();
			var linkType = $("#linkType").val();//链接类型
			var commodityId = $("#commodityId").val();//商品id
			var activityUrl = $("#activityUrl").val();//活动网址
			var kehuduan = $("#kehuduan").val();
			if(linkData == 0){
				var url = "${ctx}/GenerateLinkTo/generateLink";
				$.post(url,{"linkType":linkType,"commodityId":commodityId,"activityUrl":activityUrl,"linkName":linkType},function (data) {
					if(data.link != null){
						 var textContent = $("#messageContent").val();
						 $("#messageContent").val(textContent+data.link);
					}
				});
			}else{
				var textContent = $("#messageContent").val();
				$("#messageContent").val(textContent+kehuduan);
			}
			
			addLength();
		}
		
		//判断手机号是否正确
		function judgePhone(phone){
			//^[1-9]\d*$
			/* if(phone!=""&&!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(phone))){  */
			if(phone.trim()!=""&&(!(/^[0-9]\d*$/.test(phone.trim()))||phone.trim().length>11)){ 
		    		$(".tishi_2").show();
			    		$(".tishi_2").children("p").text("请输入正确的手机号码！")
			    		setTimeout(function(){ 
			    			$(".tishi_2").hide()
			    		},3000)
		        $(".phone").val(null);
		        return false; 
		    } 
		}
		
		//判断商品id是否为数字
		function judgNum(){
			var numIdVal = $('#commodityId').val();
			if(!isNaN(numIdVal)){
			}else{
				confirm("不是数字,订单ID需全是数字，请重新输入");
				$('#commodityId').val('');
			}
		}
		
		
	//=============编辑短信内容===============
	function editMessage(){
		/* //获取短信内容			
		var content = $("#messageContent").val();	 */
		//计算短信长度和条数
		addLength();
	 
	}
	


	function leaveEditMessage(){
		//获取短信内容			
		var content = $("#messageContent").val();
		if(shopName ==null ||shopName ==""){
			shopName ="";
		}
		if(content !=null && content !=""){
			content = "【"+shopName+"】" + content.replace("【"+shopName+"】","").replace("退订回N","")+"退订回N";
			$("#messageContent").val(content);
		}
		addLength();
	}
	
	
	//计算短信内容长度
	//计算长度
	//判断短信输入框内容长度，修改计费条数
	function addLength(){
		var content = $("#messageContent").val();
		var len=content.length;
		if(len>70){
			$("#contePrice").text(Math.ceil(len/67));
		}else{
			if(len==0){
				$("#contePrice").text(0);
			}else{
				$("#contePrice").text(1);
			}
		}
		$("#inputNum").text(len);
	}
		
</script>
</html>
