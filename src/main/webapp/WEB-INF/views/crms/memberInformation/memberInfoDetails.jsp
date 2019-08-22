<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>客户管理-客户详情</title>
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
 <form action="${ctx}/crms/memberInformation/updateMemberInfoDetails" method="post">
        <div id="customer-details">
			<h3 class="customer-detailsH3">客户信息详情</h3>
			<ul class="customer-detailsNav">
				<li  onclick='memberInfoDetails()' class="on">客户信息</li>
				<li onclick='memberInfoOrderDetails("${decryptBuyerNick}")'>订单信息</li>
				<li onclick='memberInteraction("${decryptBuyerNick}")'>客户回复</li>
			</ul>
			<div class="customer-detailsBox">
				
				<div class="customer-detailsDiv">
					<h4 class="clearfix"><span>个人信息</span>
						<a class="xg" href="javascript:;">修改</a><!-- ${memberDTO.buyerNick} -->
						<%-- <input type="text" name="buyerNick" value="${decryptBuyerNick }" id="buyerNick"/> --%>
						<input type="hidden" name="buyerNick" value="${memberDTO.buyerNick }" id="buyerNick"/>
						<%-- <a class="bc" href="javascript:;" onclick='saveMemberInfo("${decryptBuyerNick}")'>保存</a> --%>
					    <a class="bc" href="javascript:;" onclick='saveMemberInfo("${memberDTO._id}")'>保存</a>
					</h4>
					<table class="customer-detailsTable" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdone">客户昵称</td>
							<td class="tdtwo">${decryptBuyerNick}</td>
							<td class="tdone">客户姓名</td>
							<td class="tdtwo">${receiverName}</td>
							<td class="tdthree">手机号</td>
							<td class="tdtwo">${phone}</td>
						</tr>
						<tr>
							<td>支付宝</td>
							<td></td>
							<td>电子邮箱</td>
							<td class="modify">
								<span>
								<c:if test="${email==null || email==''}">未知</c:if>
								${email}
								<%-- <c:if test="${memberDTO.email==null || memberDTO.email==''}">未知</c:if> --%>
								<%--  ${memberDTO.email} --%>
								</span>
								<div>
									<input type="text" name="email" id="email" value="${email}"/>
								</div>
							</td>
							<td>性别</td>
							<td class="modify">
								<span>
									<c:if test="${memberDTO.sex==null || memberDTO.sex==''}">未知</c:if>
								    ${memberDTO.sex}
								</span>
								<div class="modifyRadio">
								  <c:if test="${memberDTO.sex=='男' }">
								    <div>
										<input type="radio" name="sex" id="sex" value="男"  checked="checked" />
										男
									</div>
									<div>
										<input type="radio" name="sex" id="sex1" value="女" />
										女
									</div>
									<div>
										<input type="radio" name="sex" id="" value="未知" />
										未知
									</div>
								  </c:if>
								  <c:if test="${memberDTO.sex=='女' }">
								    <div>
										<input type="radio" name="sex" id="sex" value="男"   />
										男
									</div>
									<div>
										<input type="radio" name="sex" id="sex1" value="女"  checked="checked"/>
										女
									</div>
									<div>
										<input type="radio" name="sex" id="" value="未知" />
										未知
									</div>
								  </c:if>
								   <c:if test="${memberDTO.sex=='未知' || memberDTO.sex=='' || memberDTO.sex==null }">
								    <div>
										<input type="radio" name="sex" id="sex" value="男"   />
										男
									</div>
									<div>
										<input type="radio" name="sex" id="sex1" value="女"  />
										女
									</div>
									<div>
										<input type="radio" name="sex" id="" value="未知"  checked="checked"/>
										未知
									</div>
								  </c:if>
								</div>  
								  
							</td>
							
						</tr>
						<tr>
							<td>生日</td>
							<td class="modify">
								<span>
								  <c:if test="${memberDTO.birthday==null || memberDTO.birthday==''}">未知</c:if>
								   ${memberDTO.birthday}
								</span>
								<div>
									<input type="text" readonly="readonly" name="birthday" id="text01" value="${memberDTO.birthday}"/>
								</div>
							</td>
							<td>年龄</td>
							<td class="modify">
								<span>
								  <c:if test="${memberDTO.age==null || memberDTO.age==''}">未知</c:if>
								  ${memberDTO.age}
								</span>
								<div>
									<input onkeyup="if(isNaN(value))execCommand('undo')" type="text" name="age" id="age" value="${memberDTO.age}" />
								</div>
							</td>
							<td>微信</td>
							<td class="modify">
								<span>
								  <c:if test="${memberDTO.wechat==null || memberDTO.wechat==''}">未知</c:if>
								  ${memberDTO.wechat}
								</span>
								<div>
									<input type="text" name="wechat" id="wechat" value="${memberDTO.wechat}" />
								</div>
							</td>
						</tr>
						<tr>
							<td>QQ</td>
							<td class="modify">
								<span>
								 <c:if test="${memberDTO.qq==null || memberDTO.qq==''}">未知</c:if>
								 ${memberDTO.qq}
								</span>
								<div>
									<input onkeyup="if(isNaN(value))execCommand('undo')" type="text" name="qq" id="qq" value="${memberDTO.qq}" />
								</div>
							</td>
							<td>职业</td>
							<td class="modify">
								<span>
								 <c:if test="${memberDTO.occupation==null || memberDTO.occupation==''}">未知</c:if>
								 ${memberDTO.occupation}
								</span>
								<div>
									<input type="text" name="occupation" id="occupation" value="${memberDTO.occupation}" />
								</div>
							</td>
							<td>省份城市</td>
							<td>${memberDTO.province}</td>
						</tr>
						<tr>
							<td>最近收货地址</td>
							<td colspan="5" class="address clearfix">
								<span></span>
								<!-- <a class="other-address" href="javscript:;">其他地址</a> -->
							</td>
							
						</tr>
					</table>
				</div>
				
				<div class="customer-detailsDiv">
					<h4 class="clearfix"><span>交易信息</span></h4>
					<table class="customer-detailsTable" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdone">会员等级</td>
							<td class="tdtwo"></td>
							<td class="tdone">客户来源</td>
							<td class="tdtwo"></td>
							<td class="tdthree">交易次数</td>
							<td class="tdtwo">${memberDTO.tradeCount }</td>
						</tr>
						<tr>
							<td>交易额</td>
							<td>${memberDTO.tradeAmount }</td>
							<td>平均客单价</td>
							<td>${memberDTO.avgPrice }</td>
							<td>宝贝件数</td>
							<td>${memberDTO.itemNum }</td>
						</tr>
						<tr>
							<td>交易关闭次数</td>
							<td>${memberDTO.itemCloseCount }</td>
							<td>最近交易时间</td>
							<td><fmt:formatDate value='${memberDTO.lastTradeTimeUTC }' pattern='yyyy-MM-dd hh:mm:ss' /></td>
							<td>未交易时间</td>
							<td>${days}天</td>
						</tr>
						
					</table>
				</div>
				
				<div class="customer-detailsDiv customer-detailRemarks">
					<h4 class="clearfix"><span>备注</span><a class="addRemarks" href="javascript:;">添加备注</a></h4>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdone">备注内容</td>
							<td class="tdtwo">备注添加时间</td>
							<td class="tdthree">操作</td>
							
						</tr>
						<c:if test="${memberDTO.remarks==null}">
						<tr><td colspan='3'>暂时没有备注信息</td><td style="border-right-style : none"></td><td style="border-right-style : none;border-left-style : none"></td></tr>
						</c:if>
						<c:if test="${memberDTO.remarks!=null}">
							 <tr value="1">
								<td class="copsell">
									<p>
									  ${memberDTO.remarks}
									</p>
								</td>
								<td>
								  <fmt:formatDate value='${memberDTO.remarksTime}' pattern='yyyy-MM-dd hh:mm:ss' />
								</td>
								<td class="remarks">
									<a class="modify" href="javascript:;">修改</a>
									<a class="remove" href="javascript:;">删除</a>
								</td>
								
							 </tr>
						</c:if>
					</table>
				</div>
			<a class="goblock" href="${ctx}/crms/memberInformation/memberInformation">返回</a>	
			</div>
			
		</div>
		
		<div class="tishi_2">
			<p>请输入正确的数据！</p>
			
		</div>
		<div id="other-addressBox">
			<h5>多地址显示</h5>	
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tdone">收货人</td>
					<td class="tdtwo">手机号</td>
					<td class="tdthree">地址</td>
				</tr>
				<tr>
					<td>阿斯顿啥都得</td>
					<td>1381111111</td>
					<td>北京市崇文区东晓市13号781门</td>
				</tr>
				<tr>
					<td>阿斯顿啥都得</td>
					<td>1381111111</td>
					<td>北京市崇文区东晓市13号781门</td>
				</tr>
				<tr>
					<td>阿斯顿啥都得</td>
					<td>1381111111</td>
					<td>北京市崇文区东晓市13号781门</td>
				</tr>
				<tr>
					<td>阿斯顿啥都得</td>
					<td>1381111111</td>
					<td>北京市崇文区东晓市13号781门</td>
				</tr>
				<tr>
					<td>阿斯顿啥都得</td>
					<td>1381111111</td>
					<td>北京市崇文区东晓市13号781门</td>
				</tr>
				<tr>
					<td>阿斯顿啥都得</td>
					<td>1381111111</td>
					<td>北京市崇文区东晓市13号781门</td>
				</tr>
				<tr>
					<td>阿斯顿啥都得</td>
					<td>1381111111</td>
					<td>北京市崇文区东晓市13号781门</td>
				</tr>
				<tr>
					<td>阿斯顿啥都得</td>
					<td>1381111111</td>
					<td>北京市崇文区东晓市13号781门</td>
				</tr>
				
			</table>
			<a class="close" href="javascript:;">关闭</a>
		</div>
		<div id="addRemarksBox">
			<h4>添加备注：</h4>	
			<div class="addRemarksTxt">
			<textarea  id="remarks" name="remarks">${memberDTO.remarks }</textarea>
				<!-- <textarea></textarea> -->
				<p>最多可输入<span>120</span>个字，您已输入<em>0</em>个字</p>
			</div>
			<div class="addRemarksBtn clearfix">
				<a class="qx" href="javascript:;">取消</a>
				<a class="qd" href="javascript:;"  onclick='addRemarks("${memberDTO._id}")'>确定</a>
				
			</div>
		</div>
		<div id="modifyRemarksBox">
			<input type="hidden" name="" id="" value="" />
			<h4>修改备注：</h4>	
			<div class="modifyRemarksTxt">
				<textarea id="remarks1" name="remarks"></textarea>
				<p>最多可输入<span>120</span>个字，您已输入<em>0</em>个字</p>
			</div>
			<div class="modifyRemarksBtn clearfix">
				<a class="qx" href="javascript:;">取消</a>
				<a class="qd" href="javascript:;" onclick='updateRemarks("${memberDTO._id}")'>确定</a>
				
			</div>
		</div>
		<div id="removeRemarks">
			<input type="hidden" name="" id="" value="" />
			<h4>删除备注</h4>	
			<p>您确定要删除该备注？</p>
			<div class="clearfix">
				<a class="qx" href="javascript:;">取消</a>
				<a class="qd" href="javascript:;" onclick='deleteRemarks("${memberDTO._id}")'>确定</a>
			</div>
		</div>
		<div class="mask"></div>
</form>


<!-- -------------------------------------------主要内容区------------------------------------------------------ -->
			</div>
		</div>

	</div>
</body>
	<script>
	 //客户信息保存
	  function saveMemberInfo(_id){
		 //会员ID
		 var uId = _id;
		 //邮箱
		 var email = $("#email").val();
		 //性别
		 var sex = $('input:radio:checked').val();
		 //生日
		 var birthday =$("#text01").val();
		 //年龄
		 var age =$("#age").val();
		 //微信
		 var wechat = $("#wechat").val();
		 //QQ号
		 var qq = $("#qq").val();
		 //职业
		 var occupation = $("#occupation").val();
		 if(verificationsome()){
			
			jQuery.ajax({
				 url:"${ctx}/updateMemberInfoDetails",
				 type:"post",
				 data:{"uId":uId,"email":email,"sex":sex,"birthday":birthday,"age":age,"wechat":wechat,"qq":qq,"occupation":occupation},
				 success: function(data) {
					 if(data.message == true){
							$(".tishi_2").show();
							$(".tishi_2").children("p").text("保存成功!")
							setTimeout(function(){
								$(".tishi_2").hide()
								location.href="${ctx}/crms/memberInformation/memberInfoDetails?uId="+uId;
							},3000)
					}else{
						if(data.message!=null){
							$(".tishi_2").show();
							$(".tishi_2").children("p").text("保存失败,请联系系统管理员或重新操作!")
							setTimeout(function(){ 
								$(".tishi_2").hide()
							},3000)
							return;
						}
					} 
				 },
				 dataType:'json',
			 })
		}else{
			$('.tishi_2').show();
			setTimeout(function(){
				$('.tishi_2').hide();
			},1000);
		}
		 
	 }
	 
	 //添加备注信息---------------------------------------------------
	 function addRemarks(_id){
		 var uId = _id;
		 var remarks = $("#remarks").val();
		 jQuery.ajax({
			 url:"${ctx}/updateMemberInfoRemarks",
			 type:"post",
			 data:{"remarks":remarks,"uId":uId},
			 success: function(data) {
				 if(data.message == true){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("添加备注成功！")
						setTimeout(function(){
							$(".tishi_2").hide()
						},3000)
						location.href="${ctx}/crms/memberInformation/memberInfoDetails?uId="+uId;
				}else{
					if(data.message!=null){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("添加失败,请联系系统管理员或重新操作!")
						setTimeout(function(){ 
							$(".tishi_2").hide()
						},300000)
						return;
					}
				} 
			 },
			 dataType:'json',
		 })
	 }
	
	 //修改备注信息
	  function updateRemarks(_id){
		 var uId =_id;
		 var remarks = $("#remarks1").val();
		 jQuery.ajax({
			 url:"${ctx}/updateMemberInfoRemarks",
			 type:"post",
			 data:{"remarks":remarks,"uId":uId},
			 success: function(data) {
				 if(data.message == true){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("备注信息修改成功！")
						setTimeout(function(){
							$(".tishi_2").hide()
						},3000)
						location.href="${ctx}/crms/memberInformation/memberInfoDetails?uId="+uId;
				}else{
					if(data.message!=null){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("备注信息修改失败,请联系系统管理员或重新操作!")
						setTimeout(function(){ 
							$(".tishi_2").hide()
						},300000)
						return;
					}
				} 
			 },
			 dataType:'json',
		 })
	 }
	 //删除备注信息
	 function deleteRemarks(_id){
		 var uId = _id;
		 var remarks = null;
		 jQuery.ajax({
			 url:"${ctx}/updateMemberInfoRemarks",
			 type:"post",
			 data:{"remarks":remarks,"uId":uId},
			 success: function(data) {
				 if(data.message == true){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("备注信息删除成功！")
						setTimeout(function(){
							$(".tishi_2").hide()
						},3000)
						location.href="${ctx}/crms/memberInformation/memberInfoDetails?uId="+uId;
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
	 }
	 
	//订单信息页面跳转
    function memberInfoOrderDetails(decryptBuyerNick){
       var buyerNick = decryptBuyerNick;
	   location.href = "${ctx}/crms/memberInformation/memberInfoOrderDetails?buyerNick="+buyerNick;
   }
	
  //客户回复页面跳转
  function memberInteraction(decryptBuyerNick){
	  var buyerNick = decryptBuyerNick;
	  location.href = "${ctx}/crms/memberInformation/memberInteraction?buyerNick="+buyerNick;
  }
</script>
</html>
