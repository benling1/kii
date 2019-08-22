<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>客户管理-客户回复</title>
		<%@ include file="/common/common.jsp"%>

		<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/base.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/member-information.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/jedate.css"/>

		<script src="${ctx}/crm/js/jquery.jedate.js"></script>
		<script src="${ctx}/crm/js/member-information.js"></script>
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
			<div class="w1645 m_t70 m_l30">
				<div class="w100_ h48 lh50 bgc_fff font18">
					<div class="f_l h50 w4 bgc_1d9dd9"></div>
					<div class="f_l m_l15 c_384856">客户管理</div>
					<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">

						
						 <a class="c_384856" href="${ctx}/sellerGroup/findSellerGroup">
							<div class="f_l w140 text-center cursor_">会员分组</div>
						</a> 
						 <a class="c_384856" href="${ctx}/crms/memberInformation/memberInformation">
							<div class="f_l w140 text-center cursor_ bgc_e3e7f0">会员信息</div>
						</a>
						<a class="c_384856"
							href="${ctx}/memberInteraction/memberSmsSendAndReceIve">
							<div class="f_l w140 text-center cursor_">会员互动</div>
						</a>
						<a class="c_384856"
							href="${ctx}/crms/marketingCenter/smsBlack">
							<div class="f_l w140 text-center cursor_">黑名单列表</div>
						</a> <%-- <a class="c_384856"
							href="${ctx }/crms/customerManagement/blacklistManagemen">
							<div class="f_l w140 text-center cursor_">黑名单管理</div>
						</a> --%>
<!-- 						<a class="c_384856" -->
<%-- 							href="${ctx}/crms/customerManagement/memberGradation"> --%>
<!-- 							<div class="f_l w140 text-center cursor_"> -->
<!-- 								会员等级划分</div> -->
<!-- 						</a> -->
					</div>
				</div>
				
				<div class="w1655 h80 bgc_f1f3f7 c_384856 p_l40 p_t30">
								
								<!---------------标题--------------->
								<div class="font24 m_b10">
									客户信息详情
								</div>
								
				</div>
				
<!-- -------------------------------------------主要内容区------------------------------------------------------ -->
          <div id="customer-details">
			<h3 class="customer-detailsH3">客户订单详情</h3>
			<ul class="customer-detailsNav">
				<li  onclick='memberInfoDetails()'>客户信息</li>
				<li onclick='memberInfoOrderDetails("${memberDTO.buyerNick}")'>订单信息</li>
				<li  class="on" onclick='memberInfoHf()'>客户回复</li>
			</ul>
			<input type="hidden" name="buyerNick" value="${buyerNick}" id="buyerNick"/>
			<div class="customer-responseBox clearfix">
				<table class="customer-responseTable" border="0" cellspacing="0" cellpadding="0">
					<tr class="trone">
						<th class="thone">手机号码</th>
						<!-- <th class="thtwo">商家发送内容</th> -->
						<th class="ththree">客户回复内容</th>
						<th class="thfour">回复时间</th>
						<th class="thfive">操作</th>
					</tr>
					<c:if test="${smsReceiveInfoList.size()==0}">
					  <tr class="trtwo" value="1">
							<td colspan="4">暂时没有互动信息！</td>
							<td>
							</td>
							<td></td>
							<td></td>
							<td>
							</td>
					</tr>
					</c:if>
					<c:forEach items="${smsReceiveInfoList}" var="smsReceiveInfo1">
						<tr class="trtwo" value="1">
						   <td style="display:none">${smsReceiveInfo1.id}</td>
							<td>${smsReceiveInfo1.sendPhone}</td>
							<!-- <td>
								<p>亲爱的vip客户，一直很少给亲发短信是怕打扰您，但这次真的不同：小店中秋大促礼券送不停还包邮!</p>
							</td> -->
							<td>${smsReceiveInfo1.content}</td>
							<td><fmt:formatDate value='${smsReceiveInfo1.receiveDate}' pattern='yyyy-MM-dd hh:mm:ss' /></td>
							<td>
								<a class="close" href="javascript:;" onclick='deleteSmsReceiveInfo("${smsReceiveInfo1.id}")'>删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<a class="goblock" href="${ctx}/crms/memberInformation/memberInformation">返回</a>
			</div>
		</div>
		<!-- <div id="removecustomer-responseBox">
			<input type="hidden" name="" id="" value="" />
			<h4>删除客户互动内容</h4>	
			<p>您确定要删除该内容？</p>
			<div class="clearfix">
				<a class="qx" href="javascript:;">取消</a>
				<a class="qd" href="javascript:;">确定</a>
			</div>
		</div> -->
		<div class="mask">
			
		</div>


<!-- -------------------------------------------主要内容区------------------------------------------------------ -->
			</div>
		</div>

	</div>
</body>
<script type="text/javascript">
//删除评论信息
  function deleteSmsReceiveInfo(id){
	  var id = id;
	  var buyerNick = $("#buyerNick").val();
	  jQuery.ajax({
			 url:"${ctx}/deleteSmsReceiveInfo",
			 type:"post",
			 data:{"id":id},
			 success: function(data) {
				 if(data.message == true){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("删除成功！")
						setTimeout(function(){
							$(".tishi_2").hide()
						},3000)
				}else{
					if(data.message!=null){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("备注信息删除失败,请联系系统管理员或重新操作!")
						setTimeout(function(){ 
							$(".tishi_2").hide()
						},300000)
						return;
					}
				} 
			 },
			 dataType:'json',
		 })
		 location.href="${ctx}/crms/memberInformation/memberInteraction?buyerNick="+buyerNick;
  }
  //客户信息页面跳转
  function memberInfoDetails(){
	  var buyerNick = $("#buyerNick").val();
	  location.href = "${ctx}/crms/memberInformation/memberInfoDetails?buyerNick="+buyerNick;
  }
  //订单信息页面跳转
  function memberInfoOrderDetails(){
	  var buyerNick = $("#buyerNick").val();
	  location.href ="${ctx}/crms/memberInformation/memberInfoOrderDetails?buyerNick="+buyerNick;
  }
</script>
</html>
