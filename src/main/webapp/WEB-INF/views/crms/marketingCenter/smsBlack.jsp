<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
	<title>营销中心-短信黑名单</title>
	<%@ include file="/common/common.jsp"%>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		
	<!--兼容360meta-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta name="renderer" content="webkit">
	<link rel="stylesheet" href="${ctx}/crm/css/base.css" />
	<link rel="stylesheet" href="${ctx}/crm/css/blacklist.css" />
		
    <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
    <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
	<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js" ></script> --%>
	<script type="text/javascript" src="${ctx}/crm/js/text_blacklist.js" ></script>
	<script src="${ctx}/crm/js/jquery.form.js" type="text/javascript" ></script>
	 <script type="text/javascript" src="${ctx}/crm/js/util.js"></script>	
	 <script type="text/javascript" src="${ctx}/crm/js/blacklist.js"></script>
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
							<div class="f_l w140 text-center cursor_">会员信息</div>
						</a>
						<a class="c_384856"
							href="${ctx}/memberInteraction/memberSmsSendAndReceIve">
							<div class="f_l w140 text-center cursor_">会员互动</div>
						</a>
						<a class="c_384856"
							href="${ctx}/crms/marketingCenter/smsBlack">
							<div class="f_l w140 text-center cursor_ bgc_e3e7f0">黑名单列表</div>
						</a> 
<!-- 						<a class="c_384856" -->
<%-- 							href="${ctx}/crms/customerManagement/memberGradation"> --%>
<!-- 							<div class="f_l w140 text-center cursor_ "> -->
<!-- 								会员等级划分</div> -->
<!-- 						</a> -->
						<%-- <a class="c_384856"
							href="${ctx }/crms/customerManagement/blacklistManagemen">
							<div class="f_l w140 text-center cursor_">黑名单管理</div>
						</a> --%>
					</div>
				</div>
					<%-- <div class="w100_ lh50 bgc_fff font18">
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
							<a class="c_384856" href="${ctx}/crms/marketingCenter/list">
								<div class="f_l w140 text-center cursor_">
									订单短信群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/marketingCenter/smsBlack">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									短信黑名单
								</div>
							</a>
							<a class="c_384856" href="${ctx}/msgSendRecord/memberSendRecord"">
								<div class="f_l w140 text-center cursor_">
									短信发送记录
								</div>
							</a>
							<a class="c_384856" href="${ctx}/member/msgSendRecord">
							<div class="f_l w200 text-center cursor_">会员短信群发效果分析</div>
						</a>
						</div>
						<div class="clear"></div>
					</div> --%>
					
					
					<!---------------会员短信群发--------------->
					<div class=" ">
						<!----------上部---------->
						<div class="w1605 h100 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								短信黑名单
							</div>
							<!---------------描述--------------->
							<div class="font14">
								可以将部分客户添加到黑名单中，在群发短信时，可以过滤掉黑名单客户，不向黑名单客户发送短信
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1605 m_b30 bgc_fff p_t35 p_l40 p_b40">
							
							<!--------查询设置-------->
						<form action="${ctx}/crms/marketingCenter/smsBlack" method="post" id="buyerNameOrNumber">
							<div class="font14 c_384856 h47 lh45 m_b35">
								<div class="f_l m_r15 font16">
									买家昵称/手机号码:
								</div>
								
								<input class="bgc_f4f6fa border0 w250 h45 lh45 f_l m_r30" name="content" value="${phone }"/>
								<input class="m_r45 w100 lh50 bgc_fff h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center  f_l cursor_" type="submit" value="查询">
								<!-- <div class="w100 h45 lh45 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
									查询
								</div> -->
								<a class="addblacklist" href="javascript:;">添加黑名单</a>
								<a class="batchblacklist" href="javascript:;">批量添加黑名单</a>
								<a class="removeblacklist" href="javascript:;">移除黑名单</a>
								<a class="emptyblacklist" href="javascript:;" onclick='removeBlack()'>清空黑名单</a>
							</div>
						</form>	
						
						
<!-- 							<div class="h45 m_b40 p_r330"> -->
							
							 <!-- <input class="m_r45 w100 lh50 bgc_fff h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center  f_l cursor_" type="submit" value="删除"> -->
							<!--  <div class="w100 h45 lh45 b_radius5 c_fff bgc_ff6261 text-center f_l cursor_">
									删除
							 </div> -->
								
							 <%--  <c:forEach items="${params.addSource}" var="Value">
									<div class="w100 add_hmd_window_btn h45 lh45 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_r cursor_">
										${Value.primaryValue}
									</div>
									
									<div class="w100 hmd_window_btn h45 lh45 b_radius5 border_00a0e9 c_00a0e9 tk text-center m_r25 f_r cursor_">
										${Value.primaryValue}
									</div>
								</c:forEach> --%>
								
								
								 
									
									<!-- <div class="w100 hmd_window_btn h45 lh45 b_radius5 border_00a0e9 c_00a0e9 tk text-center m_r25 f_r cursor_">
										黑名单导入
									</div> --> 
								
<!-- 							</div> -->
							
							<!--------详细数据-------->
							<div class="c_384856 text-center blacklistIcon" style="position: relative;">
								<table>
								  <tr class="">
								   	<th style="width:7vw;"><i class="blacklistqx"><img src="${ctx}/crm/images/black_check.png"></i><span>全选</span></th>
								    <th class="w370">黑名单客户昵称/手机号码</th>
								    <th class="w135">黑名单类型</th>
								    <th class="w175">添加时间</th>
								    <th class="w245">添加来源</th>
								    <th class="w280">操作</th>
								  </tr>
								<c:forEach items="${pagination.list}" var="blackSms">
								  <tr class="">
								  	<td>
								  	<i class="blackId blacklistdx">
								  		<img src="${ctx}/crm/images/black_check.png">
								  	</i>
								  		<input value="${blackSms.type}" type="hidden"/>
								  		<input value="${blackSms.content}" type="hidden"/>
								  	</td>
								    <td>${blackSms.content}</td>
								    <td>
									    <c:if test="${blackSms.type==1}">
									    	手机号
									    </c:if>
									    <c:if test="${blackSms.type==2}">
									    	客户昵称
									    </c:if>
								    </td>
								    <td><fmt:formatDate value='${blackSms.createdate}' pattern='yyyy-MM-dd hh:mm:ss' /> </td>
								    
								    <td>${blackSms.addSource}</td>
								    <td>
								    	<button class="w100 h48 lh48 bgc_fff border_00a0e9 c_00a0e9 tk cursor_ b_radius5"  onclick='smsDelete("${blackSms.content}")' value="移出黑名单">
								    		 移出黑名单
								    	</button>
								    </td>
								  </tr>
								 </c:forEach> 
								</table>
								 <img class="sdg" src="${ctx}/crm/images/yu-jiazai.gif" style="position: absolute;top: 50%;left: 30%;display:none;"/>
							</div>
							
							
							
							<!--------分页-------->
                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
	                            <c:forEach items="${pagination.pageView }" var="page">
											${page } 
							    </c:forEach>
                            </div>
							
						</div>
					
					
					</div>
					
					
				</div>	
				
					
					
					
						
				</div>
		
			</div>
	
	</div>

	
	

					

	
	

					

<!---------------导入黑名单弹窗--------------->
<div class="w100_ h1030 rgba_000_5 position_absolute z_10 top0 hmd_window display_none">
	
	<div class="w590  bgc_fff margin0_auto m_t260 p_t20 p_l20 p_r20 p_b20">
		
		<div class="text-center c_384856 font16 m_b20 h22 lh22" id="one">
			黑名单导入
		</div>
		
		<!--文件导入-->
		<div class="w480 h42 margin0_auto">
			<div class="f_l h42 lh42 m_r5 font16">上传文件:</div>
			<div class="w300 h42 f_l lh42 m_r5 cursor_ b_radius5 bgc_f4f6fa"></div>
			<div class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ font16">选择文件</div>
		</div>
		<div class="c_ff6363 text-center m_t10 m_b20 font14">
			注：此处请导csv，txt，xls文件
		</div>
		
		<div class="w232 h42 margin0_auto font16 text-center">
			<div class="w100 h42 f_l lh42 close c_fff bgc_00a0e9 b_radius5 cursor_">确定</div>
			<div class="m_l30 f_l border_cad3df close cursor_ w100 h40 lh40 b_radius5 c_8493a8">取消</div>
		</div>
		
	</div>
	
</div>	



<!---------------添加黑名单弹窗--------------->
<%-- <form:form action="${ctx}/smsBlack" method="post" modelAttribute="smsBlacklist">
<div class="w100_ h1030 rgba_000_5 position_absolute z_10 top0 add_hmd_window display_none" >
	
	<div class="w590 bgc_fff margin0_auto m_t150 p_t20 p_l20 p_r20 p_b20">
		
		<div class="text-center c_384856 font16 m_b20 h22 lh22" id="two">
			添加黑名单
		    	 <form:hidden path="type" id="type_s"/>
		</div>
		
		<!--文件导入-->
		<div class="w480 h50 margin0_auto">
			<div class="f_l h50 lh50 m_r5 font16 c_384856">黑名单类型:</div>
	    	<c:forEach items="${params.smsBlack}" var="Value">
	    	<div class="f_l 1check_box m_t13 m_l10 m_r15" id="type">
	    	<!--  -->
		    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
		    	</div>
		    	<div class="m_l10 cursor_ f_l font14 c_384856 primary_value">
		    		${Value.primaryValue}
		    	</div>
		    	<input type="hidden"  value="${Value.primaryKey}" name="key" id="typeOne${Value.primaryKey}"/>
			</div>
	    	</c:forEach>
		</div>
		
		<form:textarea class="w590 h285 border0 bgc_f4f6fa m_b10" path="content" id="content"/>
		<!-- <p class="c_red font14 text-center h40 lh40">
		    如果添加多条数据，请用英文逗号分隔！！！
		</p> -->
		<div class="w232 h42 margin0_auto font16 text-center">
			<div class="w100 h42 f_l lh42 close c_fff bgc_00a0e9 b_radius5 cursor_" id="queding" onclick='queding()'>确定</div>
			<div class="m_l30 f_l border_cad3df close cursor_ w100 h40 lh40 b_radius5 c_8493a8" id="qx">取消</div>
		</div>
		
	</div>
	
</div>	
</form:form> --%>

		<!-- ---------------------------------------------新修改黑名单弹窗------------------------------------------------------------- -->
 <form:form action="${ctx}/smsBlack" method="post" modelAttribute="smsBlacklist">
		<div class="blacklistBox">
			<div class="blacklistTitle">添加黑名单
			</div>
			<div class="blacklistTypeBox clearfix">
				<label>黑名单类型：<form:hidden path="type" id="type_s"/></label>
				<c:forEach items="${params.smsBlack}" var="Value">
					 <div class="phoneblacklist blacklistDiv clearfix  1check_box" id="type">
						<i class=""></i>
						<input type="hidden" value="${Value.primaryKey}" name="key" id="typeOne${Value.primaryKey}"/>
						<span>${Value.primaryValue}</span>
					 </div>
				</c:forEach>
			</div>
			<div class="blacklistprompt">
				提示：多个<span>手机号</span>添加需要通过逗号隔开	
			</div>
			<div class="blacklist-textarea">
			<form:textarea class="w590 h285 border0 bgc_f4f6fa m_b10" path="content" id="content"/>
			   <%-- <form:textarea class="phone" name="" rows="" cols="" placeholder="13811111111，13811111112,13811111113" path="content" id="content"/> --%>
				<!-- <textarea class="phone" name="" rows="" cols="" placeholder="13811111111，13811111112,13811111113"></textarea> -->	
			</div>
			<div class="blacklistBtn clearfix">
				<p class="">您输入的手机号有误，请重新填写</p>
				<div class="blacklistBtnBox clearfix">
					<input class="qd" type="button"  value="确定"  id="queding" onclick="success()"/>
					<input class="qx" type="button" name="" id="" value="取消" />
				</div>
			</div>
		</div>
  </form:form>
  <!-- ---------------------------------------------新修改黑名单弹窗------------------------------------------------------------- -->

<!-- ------------------------------------------------批量添加黑名单窗口------------------------------------------------------------------------ -->
		<div class="batchblacklistBox">
			<h3 class="batchblacklistTitle">批量添加黑名单</h3>
			<div class="batchblacklistscreen clearfix">
			<form:form action="${ctx}/smsBlack" method="post" modelAttribute="smsBlacklist">
			     <label>黑名单类型： <form:hidden path="type" id="type_s"/> </label>
				<div class="batchblacklistscreenCheckBox clearfix">
					<c:forEach items="${params.smsBlack}" var="Value">
						 <div class="phoneblacklist blacklistDiv clearfix  1check_box" id="type">
							<i class=""></i>
							<input type="hidden" value="${Value.primaryKey}" name="key" id="typeTwo${Value.primaryKey}"/>
							<span>${Value.primaryValue}</span>
						 </div>
					</c:forEach>
				</div>
			</form:form>
				<div class="batchblacklistbtnSc" onclick="document.form1.picpath.click()">
					<form name="form1" action="addStartPage.action" id="form1">
						<input type="file" name="file" id="picpath" style="display: none;"
							onChange="document.form1.path.value=this.value;addMore();">
						<input type="hidden" name="path" id="path" readonly> 
					</form>
					文件上传
				</div>
				<!-- <div class="batchblacklistbtnSc">
					<input type="file" name="" id="" value="" />
					上传文件
				</div> -->
				<div class="batchblacklistbtnScBox">
					<a class="batchblacklistbtnScShow" href="javascript:;">批量上传模板</a>
					<img class="batchblacklistbtnScShowImg" src="${ctx}/crm/images/phone.png"/>
				</div>
			</div>
			<div class="batchblacklistP clearfix">
				<span>提示：</span>
				<div>
					<!-- <p>上传数据会自动去重，如果<span>手机号</span>不存在已有会员中，则不能添加到黑名单。</p> -->
					<p>上传文件数量不能超过20万条数据，删除黑名单文件不会影响已上传的黑名单</p>
				</div>
			</div>
			<!-- --------------------------------文件上传页面查询------------------------------------------ -->
				<div class="batchblacklistTableBox">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th class="thone">序号</th>
							<th class="thtwo">文件名称</th>
							<th class="ththree">上传时间</th>
							<th class="thfour">处理状态</th>
							<th class="thfive">上传数</th>
							<th class="thsix">成功数</th>
							<th class="thseven">操作</th>
						</tr>
						<c:forEach items="${params.smsSendlistImportList}" var="smsSendlist" varStatus="var">
							<tr class="odd">
								<td style="display: none;" id="importId">${smsSendlist.id}</td>
								<td>${var.getCount()}</td>
								<td>${smsSendlist.fileName}</td>
								<td>
									<p><fmt:formatDate value='${smsSendlist.importTime}' pattern='yyyy-MM-dd' /></p>
									<!-- <p>15:00:23</p> -->
								</td>
								<td>
								  <c:if test="${smsSendlist.state==0}">导入完成</c:if>
								  <c:if test="${smsSendlist.state==1}">导入失败</c:if>
								  <c:if test="${smsSendlist.state==2}">导入中</c:if>
								<%-- ${smsSendlist.state} --%>
								</td>
								<td>${smsSendlist.sendNumber }</td>
								<td>${smsSendlist.successNumber }</td>
								<td>
									<a class="delete" href="javascript:;">删除</a>
								</td>
							</tr>
							<div id="deleteBox">
								<p>您是否要删除该上传记录?</p>
								<div class="deleteBtn clearfix">
									
									<a class="qx" href="javascript:;">取消</a>
									<a class="qd" href="javascript:;" onclick='smsImportDelete(${smsSendlist.id})'>确定</a>
								</div>
							</div>
						</c:forEach>
						<!-- <tr>
							<td>1</td>
							<td>黑名单啦啦啦啦啦啦啦</td>
							<td>
								<p>2017-6-14</p>
								<p>15:00:23</p>
							</td>
							<td>上传完成</td>
							<td>500</td>
							<td>500</td>
							<td>
								<a href="javascript:;">详情</a>
								<a href="javascript:;">删除</a>
							</td>
						</tr> -->
					</table>
				</div>
			<!-- -------------------------------------------------------------------------- -->
			<div class="batchblacklistBtn">
				<a href="javascript:;">关闭</a>

			</div>
		</div>
<!-- ------------------------------------------------批量添加黑名单窗口------------------------------------------------------------------------ -->
		<div class="detailsBox phonedetails">
			<div class="detailsFail clearfix">
				<label>上传失败数：</label>
				<span>0</span>
			</div>
			<div class="details clearfix">
				<div>
					<label for="">号码重复数：</label>
					<span>0</span>
				</div>
				<div>
					<label for="">号码错误：</label>
					<span>0</span>
				</div>
				<div>
					<label for="">号码与现有店铺会员不匹配：</label>
					<span>0</span>
				</div>
			</div>
			<div class="detailsBtn">
				<a href="javascript:;">关闭</a>
			</div>
		</div>
		<div class="detailsBox namedetails">
			<div class="detailsFail clearfix">
				<label>上传失败数：</label>
				<span>0</span>
			</div>
			<div class="details clearfix">
				<div>
					<label for="">号码重复数：</label>
					<span>0</span>
				</div>

				<div>
					<label for="">号码与现有店铺会员不匹配：</label>
					<span>0</span>
				</div>
			</div>
			<div class="detailsBtn">
				<a href="javascript:;">关闭</a>
			</div>
		</div>
		<!-- <div id="deleteBox">
			<p>您是否要删除该上传记录?</p>
			<div class="deleteBtn clearfix">
				
				<a class="qx" href="javascript:;">取消</a>
				<a class="qd" href="javascript:;" onclick='smsImportDelete()'>确定</a>
			</div>
		</div> -->
		<div id="reomveblacklistBox">
			<p>您是否要将选中的黑名单移除?</p>
			<div class="deleteBtn clearfix">
				
				<a class="qx" href="javascript:;">取消</a>
				<a class="qd" href="javascript:;" onclick='removeBlackOne()'>确定</a>
			</div>
		</div>
		<!-- <div id="emptyblacklistBox">
			<p>您是否要将选中的黑名单移除?</p>
			<div class="deleteBtn clearfix">
				
				<a class="qx" href="javascript:;">取消</a>
				<a class="qd" href="javascript:;">确定</a>
			</div>
		</div> -->
		<div class="mark"></div>
	
	

	
				
</body>

<script>

//黑名单添加时默认选择手机号
 $(function(){
	var typeOne = $("#typeOne1").val();
	$("#typeOne1").siblings('i').addClass('on');
	$("#type_s").val(typeOne);
});
///批量添加黑名单默认选择手机号
 $(function(){
		var typeTwo = $("#typeTwo1").val();
		$("#typeTwo1").siblings('i').addClass('on');
		$("#type_s").val(typeTwo);
	});

$(function(){
	$(".load_top").load("top.html");
	$(".load_left").load("left.html");
})
window.onload=function(){
		$(".sdg").hide();
	}
	

//黑名单添加
	function queding1(){
		var type= $("#type_s").val();
		var phoneflag = 1;//手机号发送判断标识
		var phonez=/^1[3|4|5|7|8]\d{9}$/;
		var content = $("#content").val();
		//判断号码内容是否成功 
		var phoneList = content.split(",");
		for(var i=0;i<phoneList.length;i++){
			if (phoneList[i].match(phonez)){
				phoneflag = 0;
			} else{
				phoneflag=1;
				break;
			}
		}
		if(phoneflag == 1){
			
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("手机号码格式不正确!")
			setTimeout(function(){
				$(".tishi_2").hide()
			},300000)
			
		}else if(phoneflag == 0){
			var type= $("#type_s").val();
			 $.ajax({
					url:"${ctx}/smsBlack",
					type:"post",
					data:{"content":content,"type":type},
				    success: function(data) {
			        	
						if(data.message == true){
							$(".tishi_2").show();
							$(".tishi_2").children("p").text("添加数据成功!")
							setTimeout(function(){ 
								$(".tishi_2").hide()
							},300000)
							$("#content").val(""); 
							$("#type").val("");
						//2,根据筛选的条件进行页面跳转
						location.href="${ctx}/crms/marketingCenter/smsBlack";
						}else{
							if(data.message!=null){

								$(".tishi_2").show();
								$(".tishi_2").children("p").text(data.message)
								setTimeout(function(){ 
									$(".tishi_2").hide()
								},300000)
								return;
							}
							$(".tishi_2").show();
							$(".tishi_2").children("p").text("添加数据失败,请联系系统管理员或重新操作!")
							setTimeout(function(){
								$(".tishi_2").hide()
							},300000)
							return;
						} 
			        } ,
					
					dataType:'json',
				})
		}
		
		$("#content").val(""); 
		$("#type").val("");
	} 
	
	function success(){
		var type= $("#type_s").val();
		if( type == 1){
			queding1();
		}else if(type == 2){
			var content= $("#content").val();
			if(content == ''){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("内容不能为空!")
				setTimeout(function(){
					$(".tishi_2").hide()
				},300000)
				location.href="${ctx}/crms/marketingCenter/smsBlack";
				return;
			}
			 $.ajax({
					url:"${ctx}/smsBlack",
					type:"post",
					data:{"content":content,"type":type},
				    success: function(data) {
			        	
						if(data.message == true){
							$(".tishi_2").show();
							$(".tishi_2").children("p").text("添加数据成功!")
							setTimeout(function(){
								$(".tishi_2").hide()
							},300000)
							$("#content").val(""); 
							$("#type").val("");
						//2,根据筛选的条件进行页面跳转
						location.href="${ctx}/crms/marketingCenter/smsBlack";
						}else{
							if(data.message!=null){

								$(".tishi_2").show();
								$(".tishi_2").children("p").text(data.message)
								setTimeout(function(){ 
									$(".tishi_2").hide()
								},300000)
								return;
							}
							$(".tishi_2").show();
							$(".tishi_2").children("p").text("添加数据失败,请联系系统管理员或重新操作!")
							setTimeout(function(){
								$(".tishi_2").hide()
							},300000)
							return;
						} 
			        } ,
					
					dataType:'json',
				})
		}
		if(type == ''){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请选择黑名单添加类型!")
			setTimeout(function(){
				$(".tishi_2").hide()
			},300000)
			location.href="${ctx}/crms/marketingCenter/smsBlack";
		}
		
		//location.href="${ctx}/crms/marketingCenter/smsBlack";
		
	}
	
	function smsDelete(content){
		var content = content;
		if(confirm("确定移出黑名单!")){
			jQuery.ajax({
			url:"${ctx}/smsBlackDelete",
			type:"post",
			data:{"content":content},
			type:"post",
		    success: function(data) {
		    	if(data.message == true){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("移除成功!")
					setTimeout(function(){
						$(".tishi_2").hide()
					},3000)
					$("#content").val(""); 
					$("#type").val("");
					$(".sdg").show();
				//2,根据筛选的条件进行页面跳转
				location.href="${ctx}/crms/marketingCenter/smsBlack";
				}else{
					if(data.message!=null){

						$(".tishi_2").show();
						$(".tishi_2").children("p").text(data.message)
						setTimeout(function(){ 
							$(".tishi_2").hide()
						},300000)
						return;
					}
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("删除数据失败,请联系系统管理员或重新操作!")
					setTimeout(function(){
						$(".tishi_2").hide()
					},300000)
					return;
				} 
	        } ,
			
			dataType:'json',
		})
	}
}
	
	
	//清空黑名单
	function removeBlack(){
		if(confirm("确定清空黑名单列表!")){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("黑名单列表清空中....")
			jQuery.ajax({
				url:"${ctx}/deleteSmsBlack",
				type:"post",
				 success: function(data){
					if(data.message == true){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("黑名单列表已清空!")
						setTimeout(function(){
							$(".tishi_2").hide()
						},300000)
					//2,根据筛选的条件进行页面跳转
					location.href="${ctx}/crms/marketingCenter/smsBlack";
					}else{
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("清空失败，请联系管理员!")
						setTimeout(function(){
							$(".tishi_2").hide()
						},300000)
					//2,根据筛选的条件进行页面跳转
					location.href="${ctx}/crms/marketingCenter/smsBlack";
					}
				},
				dataType:'json',
			})
	    }else{
	    	$('.mark').hide();
	    }		
	}
	
	//点击查询按钮，查询买家昵称或手机号码
	function sendForm(){
		$("#buyerNameOrNumber").submit();
	}
	
	
	$("#qx").click(function(){
		$("#content").val("");
	});
	
	//文件上传
	 function addMore(){
		var reg=new RegExp("(.xls|.xlsx)$");
			   if(!reg.test($("#picpath").val().toLowerCase())){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("请选择正确的文件类型")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
			 		return;
			 	}
		  var size = document.getElementById('picpath').files[0].size;
			   if(size>2366923 ){
				   $(".tishi_2").show();
				   $(".tishi_2").children("p").text("上传文件数量不能大于20万!")
					setTimeout(function(){
						$(".tishi_2").hide()
					},3000);
				   return;
			   } 
			   
			 //黑名单上传类型
				var type= $("#type_s").val();
				if(type==null){
					$(".tishi_2").children("p").text("请选择上传黑名单类型")
					setTimeout(function(){
						$(".tishi_2").hide()
					},3000);
					return;
				}
		
			  $(".tishi_2").show();
			 $(".tishi_2").children("p").text("上传中....")
		$("#form1").ajaxSubmit({
			url:"${ctx}/uploadMemberInfoPhone",
			type:"post",
			data:{"type":type},
		    success: function(data) {
		    	if(data.message == true){
					$(".tishi_2").children("p").text("上传成功!")
					setTimeout(function(){
						$(".tishi_2").hide()
					},3000);
					//2,根据筛选的条件进行页面跳转
					location.href="${ctx}/crms/marketingCenter/smsBlack";
				}else{
					$(".tishi_2").children("p").text("上传失败，请联系管理员或者重新操作!")
					setTimeout(function(){
						$(".tishi_2").hide()
					},3000);
				}
	        } ,
			
			dataType:'json',
		})
	}
	
	//删除黑名单导入文件
	function smsImportDelete(id){
		var id= id;
		jQuery.ajax({
			url:"${ctx}/removeSmsImport",
			type:"post",
			data:{"id":id},
			type:"post",
		    success: function(data) {
		    	if(data.message == true){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("黑名单文件删除成功！")
					setTimeout(function(){
						$(".tishi_2").hide()
					},6000)
				//2,根据筛选的条件进行页面跳转
				location.href="${ctx}/crms/marketingCenter/smsBlack";
				}else{
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("删除失败,请刷新重试或者联系管理员！")
					setTimeout(function(){
						$(".tishi_2").hide()
					},300000)
					return;
				} 
	        } ,
			
			dataType:'json',
		})
	}
	
	//批量移除黑名单
	function removeBlackOne(){
		var nicks="";
		var mobiles = "";
		var divCheck = $(".blackId");
		for(var i=0;i<divCheck.length;i++){
			if(divCheck.eq(i).hasClass("on")){
				 var val = divCheck.eq(i).next().val(); 
				 if(val=="1"||val==1){
					 mobiles+=divCheck.eq(i).next().next().val()+",";
				 }else{
					 nicks+=divCheck.eq(i).next().next().val()+",";
				 }
			}
		}
		mobiles=mobiles.substring(0,mobiles.length-1);
		nicks=nicks.substring(0,nicks.length-1);
		jQuery.ajax({
			url:"${ctx}/batchDelete",
			type:"post",
			data:{"mobiles":mobiles,"nicks":nicks},
			type:"post",
		    success: function(data) {
		    	if(data.message == true){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("删除成功");
					setTimeout(function(){
						$(".tishi_2").hide();
					},3000);
					//2,根据筛选的条件进行页面跳转
					location.href="${ctx}/crms/marketingCenter/smsBlack";
				}else{
					$(".tishi_2").show();
					$(".tishi_2").children("p").text(data.message);
					setTimeout(function(){ 
						$(".tishi_2").hide();
						//2,根据筛选的条件进行页面跳转
						//location.href="${ctx}/crms/marketingCenter/smsBlack";
					},3000);
				} 
	        } ,
			dataType:'json',
		})
	}
</script>
</html>
