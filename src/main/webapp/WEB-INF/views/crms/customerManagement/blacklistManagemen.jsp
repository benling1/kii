<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>客户管理-黑名单管理</title>
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
	<script type="text/javascript" src="${ctx}/crm/js/blacklist_management.js" ></script>
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
				<div class="w1645 m_t70 m_l30">
					<div class="w100_ h48 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							客户管理
						</div>
						<div class="f_r m_r50 c_384856">
							
<%-- 							<a class="c_384856" href="${ctx}/crms/customerManagement/memberGradation"> --%>
<!-- 								<div class="f_l w140 text-center cursor_"> -->
<!-- 									会员等级划分 -->
<!-- 								</div> -->
<!-- 							</a> -->
							<a class="c_384856" href="${ctx}/sellerGroup/findSellerGroup">
								<div class="f_l w140 text-center cursor_">
									会员分组
								</div>
							</a>
							<a class="c_384856" href="${ctx}/memberInteraction/memberSmsSendAndReceIve">
								<div class="f_l w140 text-center cursor_">
									会员互动
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/customerManagement/blacklistManagemen">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									黑名单管理
								</div>
							</a>
						</div>
					</div>
					
					
					<!---------------黑名单管理--------------->
					<div class="h940">
						<!----------上部---------->
						<div class="w1605 h130 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								黑名单管理
							</div>
							<!---------------描述--------------->
							<div class="font14">
								
							</div>
							
							<!-- <div class="font16 c_384856 h50 lh50 m_t18">
								<div class="w140 h50 text-center f_l bgc_fff cursor_">
									手机黑名单
								</div>
							</div> -->
							
						</div>
						<!----------下部---------->
						<div class="w1605 h675 bgc_fff p_t35 p_l40 p_b40 hmd_in">
							
							<!--------查询设置-------->
						<form action="${ctx}/crms/customerManagement/blacklistManagemen" method="post">	
							<div class="font14 c_384856 h50 lh50">
								<div class="f_l m_r15">
									手机号码 :
								</div>
								<input id="phone" class="bgc_f4f6fa border0 w250 h50 f_l m_r35" name="mobile" onblur="judgePhone(this.value);" value="${mobile}"/>
								<div class="f_l m_r15">
									创建时间:
								</div>
								
								
								
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" id="tser01" 
									name="beginTime"  readonly onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val) {valiteTwoTime();}})"
									value="<fmt:formatDate value='${beginTime }' pattern='yyyy-MM-dd' />" />

									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
									
								<div class="f_l">~</div>
								
								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" id="tser02"  
									name="endTime" readonly onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val) {valiteTwoTime();}})"
									value="<fmt:formatDate value='${endTime }' pattern='yyyy-MM-dd' />" >
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
								</div>
								
								
								<input class="m_r45 w100 lh50 bgc_fff h50 b_radius5 tk border_00a0e9 c_00a0e9 text-center  f_l cursor_" type="submit" value="查询">
								<!-- <div class="w100 h50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
									查询
								</div> -->
							</div>
							<div class="font14 c_384856 h50 lh50 m_t20 m_b20">
								<div class="f_l m_r15 m_l28">
									备注:
								</div>
								<input id="beizhu" class="bgc_f4f6fa border0 w250 h50 f_l m_r35" name="remark" value="${remark }"/>
							</div>
						</form>
							<!--------操作选项-------->
							<div class="lh52 font14 h52 m_b15">
								
								<div class="w100 add h52 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
									添加
								</div>
							</div>
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="w285">手机号码</th>
								    <th class="w450">备注</th>
								    <th class="w290">创建时间</th>
								    <th class="w290">操作</th>
								    
								  </tr>
									 <c:forEach items="${pagination.list}" var="smsMobileBlack">
									  <tr class="text-center">
									    <td class="w285 text-center">${smsMobileBlack.mobile}</td>
									    <td class="w450">${smsMobileBlack.remark}</td>
									    <td class="w290"><fmt:formatDate value="${smsMobileBlack.ctime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>
									    <td class="w290">
									    	
									    	<button class="m_l25 m_r20 w100 h48 lh48 bgc_fff border_00a0e9 c_00a0e9 tk cursor_ b_radius5 yichu" onclick='showRem(${smsMobileBlack.id})'  value="移出黑名单">
									    		 移出黑名单
									    	</button>
									    </td>
									  </tr>
									 </c:forEach>
								</table>
							</div>
							
							
							<!--------分页-------->
                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                            	<c:if test="${pagination != null}">
                            		<c:forEach items="${pagination.pageView }" var="page">
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




					
<!---------------添加黑名单弹窗--------------->
<form:form action="${ctx}/addSmsBlackMobile" method="post" modelAttribute="smsMobile">
<div class="w100_ h1300 rgba_000_5 position_absolute z_10 top0 hmd display_none">
	
	
	<div class="w590 bgc_fff position_fixed top200 left550 p_t20 p_l20 p_r20 p_b40"style="left: 34vw;width: 25vw;">
		
		
		<div class="text-center c_384856 font16 m_b20 h22 lh22">
			添加黑名单
		</div>
		
		<!--添加黑名单选中状态-->
		 <div class="m_b20 h22">
			<div class="c_384856 font16 f_l">黑名单类型：</div>
			
			<div class="f_l c_a8b2c0 font14 m_l10 black_uncheck cursor_">
				<div class="w20 h20 b_radius20 bgc_e0e6ef one_check_only f_l">
					<img class="" src="${ctx}/crm/images/black_check.png" />
				</div>
				<div class="f_l m_l10">手机号码</div>
			</div>
		</div>
		
			<!--黑名单输入框-->
		<div>
			<div class="f_l  w60 text-center c_384856 font16" style="height:3vw;line-height:2vw;">
				手机号:
			</div>
			<%-- <input id="phone" class="bgc_f4f6fa border0 w250 h50 f_l m_r35" name="mobile" onblur="judgePhone(this.value);" value="${mobile}"/>
			 --%><form:input style="margin-left: 0.5vw;" class="bgc_f4f6fa border0 w250 h40 f_l m_r35 hmd_text" path="mobile" id="mobile"/>
		<div class="clear"></div>
		</div>
		<div>
			<div class="f_l c_384856 h200 lh200 w60 text-center font16">
				备注:
			</div>
			<form:textarea class="w530 bgc_f4f6fa f_l h200 border0 m_b30 hmd_text" style="margin-left: 0.5vw;" path="remark" id="remark"/>
		<div class="clear"></div>
		</div>
		
		
		
		<p class="p1 display_none" style="color:#ff4545;"></p>
		
		<!--保存取消按钮-->
		<div class="w232 h42 margin0_auto">
			<div class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ preserve_phone youwu_1" id="preserve" onclick="panduan();">保存</div>
			<div id="qx" class="m_l30 f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 close c_8493a8">取消</div>
		</div>
		
	</div>
	
</div>
</form:form>



<!---------------移除黑名单弹窗--------------->
	<div
		class="w100_ h1300 rgba_000_5 position_absolute z_10 top0 display_none yichu_1">

		<div
			class="w400 h150 bgc_fff margin0_auto position_fixed top250 left0 right0 p_t20 p_l20 p_r20 p_b20">



			
			<div class="text-center c_384856 font16 m_b30 h22 lh22 m_t20">
				确定移除黑名单吗？</div>
				<input type="hidden" id="showRem" />

			
				<div class="w232 h42 margin0_auto">

					<div onclick="rem()"
						class="close w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ font16"
						>确定</div>
					<div
						class="close m_l30 f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
				</div>
			
		</div>

	</div>
	
	
	
	
	
	
	
	
<!---------------号码有误弹窗--------------->
	<div
		class="w100_ h1300 rgba_000_5 position_absolute z_10 top0 display_none youwu">

		<div
			class="w400 h150 bgc_fff margin0_auto position_fixed top250 left0 right0 p_t20 p_l20 p_r20 p_b20">
			<div class="text-center c_384856 font16 m_b30 h22 lh22 m_t20">
				手机号码有误，请重填</div>
				<input type="hidden"  />

			
				<div class="w232 h42 margin0_auto">

					<div 
						class="close w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ font16"
						>确定</div>
					<div
						class="close m_l30 f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
				</div>
			
		</div>

	</div>	
	





<!---------------导入黑名单弹窗--------------->
<div class="w100_ h1100 rgba_000_5 position_absolute z_10 top0 upload display_none">
	
	<div class="w590 h200 bgc_fff left640 p_t20 p_l20 p_r20 p_b20 position_fixed top250">
		
		<div class="text-center c_384856 font16 m_b20 h22 lh22">
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
		
		<div class="w232 h42 margin0_auto">
			<div class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ font16 close">确定</div>
			<div class="m_l30 f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16 close">取消</div>
		</div>
		
	</div>
	
</div>	

</body>
<script>
	$(function(){
		$(".load_top").load("top.html");
		$(".load_left").load("left.html");
		/* var phone = "${mobile}";
		$("#phone").val(phone);
		/* var endTime = "${endTime}"; */
		/* var beginTime = "${beginTime}"
		alert(beginTime) */
		/* $("#tser02").val(endTime);
		var beginTime = "${beginTime}";
		$("#tser01").val(beginTime); */
		/*var remark = "${remark}";
		$("#beizhu").val(remark); */
	})
	
	//黑名单手机号添加
 	function preserve(mobile,remark){
		$.ajax({
			url:"${ctx}/addSmsBlackMobile",
			type:"post",
			data:{"mobile":mobile,"remark":remark},
			success: function(data) {
				if(data.message == true){

					$(".tishi_2").show();
					$(".tishi_2").children("p").text("添加数据成功")
					setTimeout(function(){
						$(".tishi_2").hide()
					},3000);

					$("#mobile").val(""); 
					$("#remark").val("");
				//2,根据筛选的条件进行页面跳转
				location.href="${ctx}/crms/customerManagement/blacklistManagemen";
				}else{
					if(data.content!=null){
		 				$(".tishi_2").show();
		 				$(".tishi_2").children("p").text(data.content)
		 				setTimeout(function(){ 
							$(".tishi_2").hide()
		 				},3000);
						return;
					}

					$(".tishi_2").show();
					$(".tishi_2").children("p").text("删除数据失败,请联系系统管理员或重新操作")
					setTimeout(function(){
						$(".tishi_2").hide()
					},3000);

					return;
				} 
			},
			dataType:'json',
		})
		$("#mobile").val(""); 
		$("#remark").val("");
	}
	
//黑名单手机号移除
function rem(){
	var id=$("#showRem").val();;
		$.ajax({
			url:"${ctx}/smsMobileRemove",
			data:{"id":id},
			type:"post",
			success:function(data){
				if(data.message == true){

					$(".tishi_2").show();
					$(".tishi_2").children("p").text("移除成功!")
					setTimeout(function(){
						$(".tishi_2").hide()
					},3000);

					location.href="${ctx}/crms/customerManagement/blacklistManagemen";
					
				}else{

					$(".tishi_2").show();
					$(".tishi_2").children("p").text("移除失败!")
					setTimeout(function(){
						$(".tishi_2").hide()
					},3000);

					return;
				}
			},
			dataType:'json'
		})
}


//将要移除黑名单的id放入弹出框中
function showRem(id){
	$("#showRem").val(id);
}



/**
 * 判断手机号是否正确
 */
/* function judgePhone(phone){
	phone =phone.replace(/\s+/g, "");//将手机号中的所有空格去除
	if(!phone){
		$("#phone").val(null);
		return false;
	}
	if(!(/^1[34578]\d{9}$/.test(phone))){ 
		if(confirm("手机号码有误，请重填")){
		}
		$("#phone").val(null);
        return false; 
    } 
} */

$("#qx").click(function(){
	$("#mobile").val("");
	$("#remark").val("");
});
</script>
</html>