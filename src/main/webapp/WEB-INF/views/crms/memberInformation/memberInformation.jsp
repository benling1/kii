<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>客户管理-会员信息</title>
		<%@ include file="/common/common.jsp"%>


		<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/base.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/member-information.css"/>


		<script src="${ctx}/crm/js/jquery.jedate.js"></script>
	    <script src="${ctx}/crm/js/member-information.js"></script>
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
						</a>
<!-- 						<a class="c_384856" -->
<%-- 							href="${ctx}/crms/customerManagement/memberGradation"> --%>
<!-- 							<div class="f_l w140 text-center cursor_"> -->
<!-- 								会员等级划分</div> -->
<!-- 						</a> -->
						 <%-- <a class="c_384856"
							href="${ctx }/crms/customerManagement/blacklistManagemen">
							<div class="f_l w140 text-center cursor_">黑名单管理</div>
						</a> --%>
<%-- 						 <a class="c_384856" href="${ctx}/sellerGroup/findSellerGroup"> --%>
<!-- 							<div class="f_l w140 text-center cursor_">会员分组</div> -->
<!-- 						</a>  -->
					</div>
				</div>
				
				<div class="w1655 h80 bgc_f1f3f7 c_384856 p_l40 p_t30">
								
								<!---------------标题--------------->
								<div class="font24 m_b10">
									会员信息
								</div>
								<!---------------描述--------------->
								<div class="font14">
									显示所有会员信息，可以查看、修改、增加客户详细信息如：邮箱、微信、生日等。
								</div>
								
				</div>

				<!---------------会员等级划分--------------->
				<div class="member-informationBox">
				<form action="${ctx}/crms/memberInformation/memberInformation" method="post" id="memberInfoSearch">
			<div class="member-informationScreenBox">
				
				<div class="member-informationScreenTop clearfix">
					<!-- <div class="membership-level clearfix" style="width:20vw">
						<label style="width:7vw">会员等级：</label>
						<div class="nice-select" name="nice-select" style="width:10vw;" >
							<input type="hidden" value="">
							<p>所有分组</p>
							<ul class="member-grouping-nav">
								<li value="">所有分组</li>
								<li value="1">普通会员</li>
								<li value="2">高级会员</li>
								<li value="3">VIP会员</li>
								<li value="4">至尊VIP会员</li>
								<li value="5">自定义分组</li>
							</ul>
						</div>
						
					</div> -->
					<div class="consumption-amount" style="margin-left:0; width:20vw;">
						<label style="width:7vw;">累计消费金额：</label>	
						<div class="consumption-amountInput">
							<input type="text" name="minTradePrice"  value="${minTradePrice }" onkeyup="if(isNaN(value))execCommand('undo')"  onafterpaste="if(isNaN(value))execCommand('undo')" id="minTradePrice"/>
							<span>~</span>
							<input type="text" name="maxTradePrice"  value="${maxTradePrice }" onkeyup="if(isNaN(value))execCommand('undo')"  onafterpaste="if(isNaN(value))execCommand('undo')" id="maxTradePrice"/>
						</div>
					</div>
					<div class="transaction-time clearfix">
						<label style="width:9vw;">最后交易时间：</label>
						<div class="transaction-timeInputBox">
						<!-- value="${tradeStartTime}" value="${tradeEndTime}" -->
							<div class="transaction-timeInput">
								<input name="tradeStartTime" style="width: 10vw;" 
										class="" type="text"
										id="tser01" readonly
										value="${tradeStartTime}"/>	
								<img style="width: 1vw;top:0.25125vw" class="position_absolute right20 top15"
								src="${ctx}/crm/images/date_copy.png"  />
							</div>
							<span>~</span>
							<div class="transaction-timeInput">
								<input name="tradeEndTime" style="width: 10vw;" 
										class="" type="text"
										id="tser02" readonly
										value="${tradeEndTime}"/>	
								<img style="width: 1vw;top:0.25125vw" class="position_absolute right20 top15"
								src="${ctx }/crm/images/date_copy.png"  />
							</div>
						</div>
						
					</div>
				</div>		
				
				<div class="member-informationScreenTop clearfix">
<!-- 					<div class="membership-level clearfix"> -->
<!-- 						<label>客户来源：</label> -->
<!-- 						<div class="nice-select" name="nice-select"> -->
<!-- 							<p>所有来源</p> -->
<!-- 							<ul class="member-grouping-nav"> -->
<!-- 								<li value="">所有分组</li> -->
<!-- 								<li value="1">普通会员</li> -->
<!-- 								<li value="2">高级会员</li> -->
<!-- 								<li value="3">VIP会员</li> -->
<!-- 								<li value="4">至尊VIP会员</li> -->
<!-- 								<li value="5">自定义分组</li> -->
<!-- 							</ul> -->
<!-- 						</div> -->
						
<!-- 					</div> -->
					<div class="consumption-amount" style="margin-left:0;width:20vw">
						<label style="width:7vw">成功交易笔数：</label>	
						<div class="consumption-amountInput">
							<input onkeyup="this.value=this.value.replace(/\D/g,'')" type="text" name="minTradeNum" id="minTradeNum" value="${minTradeNum }" />
							<span>~</span>
							<input onkeyup="this.value=this.value.replace(/\D/g,'')" type="text" name="maxTradeNum" id="maxTradeNum" value="${maxTradeNum }" />
						</div>
					</div>
<!-- 					<div class="transaction-time clearfix"> -->
<!-- 						<label style="width:9vw;">未交易时间：</label> -->
<!-- 						<div class="nice-select" name="nice-select" style="width:10vw; float:left;" > -->
<%-- 							<input type="hidden" id="filtrateTime" value="${filtrateTime}" name="filtrateTime"> --%>
<!-- 							<p> -->
<%-- 							   <c:if test="${filtrateTime==null}">不限</c:if> --%>
<%-- 							   <c:if test="${filtrateTime==0}">近3天</c:if> --%>
<%-- 							   <c:if test="${filtrateTime==1}">近7天</c:if> --%>
<%-- 							   <c:if test="${filtrateTime==2}">近10天</c:if> --%>
<%-- 							   <c:if test="${filtrateTime==3}">近15天</c:if> --%>
<%-- 							   <c:if test="${filtrateTime==4}">近1个月</c:if> --%>
<%-- 							   <c:if test="${filtrateTime==5}">近3个月</c:if> --%>
<%-- 							   <c:if test="${filtrateTime==6}">近半年</c:if> --%>
<%-- 							   <c:if test="${filtrateTime==7}">近一年</c:if> --%>
<!-- 							</p> -->
<!-- 							<ul class="member-grouping-nav"> -->
<!-- 							    <li value="">不限</li> -->
<!-- 								<li value="0">近3天</li> -->
<!-- 								<li value="1">近7天</li> -->
<!-- 								<li value="2">近10天</li> -->
<!-- 								<li value="3">近15天</li> -->
<!-- 								<li value="4">近1个月</li> -->
<!-- 								<li value="5">近3个月</li> -->
<!-- 								<li value="6">近半年</li> -->
<!-- 								<li value="7">近一年</li> -->
<!-- 							</ul> -->
<!-- 						</div> -->
<!-- 						<div class="transaction-timeInputBox transaction-timeInputBox-v"> -->
<!-- 							<div class="transaction-timeInput"> -->
<!-- 								<input name="tradeStartTime2" style="width: 9vw;" -->
<%-- 										class="" type="text" value="${tradeStartTime2}" --%>
<!-- 										id="tser03" readonly -->
<!-- 										/>	 -->
<!-- 								<img style="width: 1vw;top:0.25125vw" class="position_absolute right20 top15" -->
<%-- 								src="${ctx}/crm/images/date_copy.png"  /> --%>
<!-- 							</div> -->
<!-- 							<span>~</span> -->
<!-- 							<div class="transaction-timeInput"> -->
<!-- 								<input name="tradeEndTime2" style="width: 9vw;" -->
<!-- 										class="" type="text" -->
<%-- 										id="tser04" readonly value="${tradeEndTime2}" --%>
<!-- 										/>	 -->
<!-- 								<img style="width: 1vw;top:0.25125vw" class="position_absolute right20 top15" -->
<%-- 								src="${ctx}/crm/images/date_copy.png"  /> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<a href="javascript:;" class="transaction-timeInput-zdy">自定义</a> -->
<!-- 					</div> -->
				
				</div>		
				
				<div class="member-informationScreenTop clearfix">
					<div class="membership-level clearfix" style="width:20vw">
						<label style="width:7vw">会员昵称：</label>
						<input type="text" name="buyerNick" id="buyerNick" value="${buyerNick }" class="member-name" style="width:10.6vw" />
					</div>
					<div class="consumption-amount clearfix">
						<label>平均客单价（元）：</label>	
						<div class="consumption-amountInput">
							<input type="text" name="minAvgPrice" id="minAvgPrice" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" value="${minAvgPrice }" />
							<span>~</span>
							<input type="text" name="maxAvgPrice" id="maxAvgPrice" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" value="${maxAvgPrice }" />
						</div>
					</div>
					
				</div>		
			
				<div class="member-informationScreenBtnBox clearfix">
					<a href="javascript:;" class="member-informationScreenBtn" onclick="findMemberInfo('memberInfoSearch')">搜索</a>	
				</div>
			</div>
			</form>	
			<table class="member-informationTable" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th style="width: 10%;">序号</th>
					<th style="width: 11%;">会员昵称</th>
					<!-- <th style="width: 11%;">会员等级</th> -->
					<th style="width: 11%;">累计消费金额</th>
					<th style="width: 11%;">成功交易笔数</th>
					<th style="width: 11%;">平均客单价</th>
					<!-- <th style="width: 10%;">客户来源</th> -->
					<th style="width: 16%;">最后交易时间</th>
					<th style="width: 9%;">操作</th>
				</tr>
				<c:choose>
				    <c:when test="${pagination == null}">
					  	  	<tr class="h50 bgc_f1f3f7">
							    <td class="w105 text-center" colspan="7">暂时没有相关数据！</td>
					  		</tr>
					 </c:when>
					
					 <c:when test="${pagination.datas.size() > 0}">
						  	 <c:forEach items="${pagination.datas}" var="memberInformationlist" varStatus="var">
						  	       
							  	 <tr class="odd">
								   <td>${var.getCount()}</td>
									<td>${memberInformationlist.buyerNick}</td>
									<!-- <td>至尊Vip</td> -->
									<td>${memberInformationlist.tradeAmount }</td>
									<td>${memberInformationlist.tradeCount }</td>
									<td>${memberInformationlist.avgPrice }</td>
									<%-- <td>${memberInformationlist.lastTradeTime }</td> --%>
									<td><fmt:formatDate value='${memberInformationlist.lastTradeTimeUTC }' pattern='yyyy-MM-dd HH:mm:ss' /> </td>
									<td>
										<%-- <a href="javascript:void(0);" onclick='memberInfoDetails("${memberInformationlist.buyerNick}")'>客户详情</a> --%>
										<a href="javascript:void(0);" onclick='memberInfoDetails("${memberInformationlist._id}")'>客户详情</a>
									</td>
				                 </tr>
						  	 </c:forEach>
					 </c:when>
				</c:choose>
			</table>
			<!-- --------------------------------分页---------------------------------------- -->
		   <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
		   <input type="hidden" value="${totalPage}" id="totalPage"/>
		   <input type="hidden" value="${pageNo }" id="pageNo"/>
                    <c:forEach items="${pagination.pageView }" var="page">
						${page } 
				  </c:forEach>
           </div>
          <!-- --------------------------------分页---------------------------------------- -->
		</div>
		<div class="tishi_2">
			<p>请输入正确的数据！</p>
			
		</div>




			</div>







		</div>

	</div>





</body>

<script>
	//提交form表单查询
	function findMemberInfo(memberInfoSearch){
		var totalPage = $("#totalPage").val();
		var pageNo = $("#PAGENO").val();
		if(parseInt(pageNo)>parseInt(totalPage)){
			alert("输入页数不能大于总页数！");
		}else{
			document.getElementById(memberInfoSearch).submit();
		}
	};
	
   //客户详情跳转
   function memberInfoDetails(_id){
	   var uId =_id;
	   //alert(uId);
	   location.href = "${ctx}/crms/memberInformation/memberInfoDetails?uId="+_id;
   }
   $(function(){
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
		$('#tser01').on('click',function(){
		  	$('#tser01').jeDate(start);
	   	});
		$('#tser02').on('click',function(){
			$('#tser02').jeDate(end);
		});
		$('#tser03').on('click',function(){
		  	$('#tser03').jeDate(start);
	   	});
		$('#tser04').on('click',function(){
			$('#tser04').jeDate(end);
		});
   });
   
</script>
</html>
