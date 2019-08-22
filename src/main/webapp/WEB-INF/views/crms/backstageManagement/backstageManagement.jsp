<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>后台管理</title>
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
	<script type="text/javascript" src="${ctx}/crm/js/back-stage.js" ></script>

		
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
				<div class="w1660  m_l30">
					<div class="w100_ h48 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							后台管理
						</div>
						<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
							<div class="f_l w140 cursor_ bgc_f1f3f7 text-center phone_num_set_out">
								手机号设置
							</div>
							<div class="f_l w140 text-center cursor_ binding_store_out" onclick="remindShop()">
								多店铺绑定
							</div>
						</div>
					</div>
					
					
					<!---------------多店铺绑定--------------->
					<div class="binding_store display_none">
						<!----------上部---------->
						<div class="w1620 h90 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								多店铺绑定
							</div>
							<!---------------描述--------------->
							<div class="font14">
								可以讲部分客户添加到黑名单中，在群发短信时，可以过滤掉黑名单客户，不向黑名单客户发送短信
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1620 h675 bgc_fff p_t35 p_l40">
							
							<!---------------注意事项--------------->
							<div class="c_ff6363 lh35 h210">
								<div class="f_l">注意：</div>
								<div class="f_l w920">
									<div>1、被绑定店铺必须订购本客户端服务</div>
									<div>2、子店铺申请授权后，记录下关联ID以及发送至手机的验证码，登录主店铺进行验证</div>
									<div>3、授权成功后，当前店铺将作为子店铺失效无法登录，统一在授权店铺（主店铺）中管理</div>
									<div>4、绑定成功后，需要重新登录主店铺才能生效</div>
									<div>5、【重要提示】根据淘宝要求，超过7天未登录软件将停止订单数据推送，会导致订单中心不能正常发送催付物流短信，数据查询出现误差等问题。未确保功能正常使用，请务必每个店铺每周至少登录1次软件。（多店铺绑定请确保主店铺每周登录1次）</div>
								</div>
							</div>
							
							
							
							
							<!---------------授权--------------->
							<div class="w100_ h42 lh42 m_t40 font16">
								<div class="c_384856 f_l">授权给店铺：</div>
								<input class="f_l w225 border0 h42 bgc_f4f6fa m_l10 m_r15" disabled="disabled" />
								<div class="c_00a0e9 border_00a0e9 f_l  w100 text-center m_r15 b_radius5">申请绑定</div>
								<div class="c_00a0e9 border_00a0e9 tk f_l  w155 text-center b_radius5 cursor_">查看操作帮助文档</div>
							</div>
							
							<!---------------操作步骤引导--------------->
							<p class="c_384856 m_t70">操作步骤引导</p>
							<div class="m_t70 p_l70">
								<img src="${ctx}/crm/images/guide.png" />
							</div>
							
						</div>
					
					
					</div>
					
					
					
					<!---------------手机号设置--------------->
					<%-- <form action="${ctx}/backstageManagement/saveMobileSetting" method="post" id="saveSetting" > --%>
					<div class="phone_num_set">
						<!----------上部---------->
						<div class="w1620 h90 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								手机号设置
							</div>
							<!---------------描述--------------->
							<div class="font14">
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1620 bgc_fff p_t35 p_l40" style="padding-bottom:1vw;">
							<div class="phoneSetupH3">
								<h3 class="clearfix"><i></i><span>手机号设置</span></h3>
							</div>
							<div class="phoneSetupBox">
							
								<!---------------设置--------------->
								
								<div class="m_b10">
									<div class="clearfix">
										<div class="font16 c_384856 f_l h50 lh50" style="width:6.25vw;">接受提醒手机号</div>
										<p class="phoneNum2"></p>
										<input disabled="disabled" maxlength="11" class="display_block phone_num bgc_f4f6fa border0 f_l h50 lh50 w220 f_l m_l10 m_r10" id="phoneNum" name="phoneNum" type="text" onblur="checkPhoneNum()" onchange="checkPhoneNum()" onkeyup="checkPhoneNum()"/>
										
										<input type="hidden" name="oldPNum" id="oldPNum">
										<div class="w100 h42 f_l  m_l20 lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ display_none HQ">获取验证码</div>
										<div class=" h42 f_l  m_l20  lh42 text-center c_red  b_radius5 cursor_ font14 display_none TS ">请输入正确手机号</div>
										<div class="w100 h42 f_l  m_l20  lh42 text-center c_fff bgc_d9d9d9 b_radius5 cursor_ font14 display_none JS "><span class="NB" id="timer">60</span>s后重新获取</div>
										
										<div class="w100 h42 f_l tk change_number lh42 text-center c_00a0e9 border_00a0e9 b_radius5 cursor_" id="xg">编辑</div>
									</div>
									<div class="font16 c_384856   clbo m_l30  clearfix " id="phoneCode">
										<label class="f_l" style="width:6.25vw; height:2.604166vw;line-height:2.604166vw;">验证码</label>
										<input disabled="disabled" maxlength="11" class="w220 phone_num bgc_f4f6fa border0  h50 lh50   m_l10 m_r10  YZM" id="sendCode" name="phoneCode"/>
										<span class="h42 m_l20  lh42 text-center c_red  b_radius5 cursor_ font14  TS_1 display_none">请输入正确验证码</span>
									</div>
								</div>
								<div class="font16 c_384856  m_r30">
									手机号设置后，可免费接受以下信息！
								</div>
								<!---------------设置详情--------------->
								<div class="all_change no_click setupxq">
									<div class="w550 h50">
										<div class="f_l 1check_box m_t15">
									    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l" id="expediting">
									    	</div>
<!-- 									    	<span class="f_l yuandian"></span> -->
									    	<div class="m_l10 cursor_ f_l font14 c_a8b2c0">
									    		催付发送数量，短信发送量和当前短信条数（每天9点发送前一天数据）
									    	</div>
									    	<input type="hidden" name="id" value="${mobileSetting.id}" id="mobileSettingid"/>
									    	<input  type="hidden" name="expediting" class="expediting" value="" />
										</div>
									</div>
									<div >
										<div class="f_l 1check_box m_t15 ">
									    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l" id="messageRemainder">
									    	</div>
<!-- 									    	<span class="f_l yuandian"></span> -->
									    	<div class="m_l10 cursor_ f_l font14 c_a8b2c0 ">
									    		短信余额不足报警：剩余条数不足
									    	</div>
									    	
									    	<input  type="hidden" name="messageRemainder" class="messageRemainder"/>
										</div>
										<p class="messageCountNum f_l m_t15"></p>
										<input class="bgc_f4f6fa border0 h50 lh50 w120 f_l m_l10 m_r10 text-center" value="50" id="messageCount" name="messageCount" onkeyup="checkMessage()" />
										<div class="c_a8b2c0 font14 f_l h50 lh50 m_r20">
											条开始提醒
										</div>
										<div class="c_a8b2c0 font14 f_l h50 lh50 " style="color:red" id="startRemind">
											
										</div>
									</div>
									<div class="clear"></div>
									<div class="w500 h50">
										<div class="f_l 1check_box m_t15">
									    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l" id="serviceExpire">
									    	</div>
<!-- 									    	<span class="f_l yuandian"></span> -->
									    	<div class="m_l10 cursor_ f_l font14 c_a8b2c0">
									    		软件过期提醒
									    	</div>
									    	<input  type="hidden" name="serviceExpire" class="serviceExpire" value=""/>
										</div>
									</div>
									<div class="w500 h50">
										<div class="f_l 1check_box m_t15">
									    	<div class="cursor_  w20 h20  border_d9dde5 b_radius5 f_l bgc_check_blue" id="activityNotice">
									    	</div>
<!-- 									    	<span class="f_l yuandian"></span> -->
									    	<div class="m_l10 cursor_ f_l font14 c_a8b2c0">
									    		最新促销活动通知
									    	</div>
									    	<input  type="hidden" name="activityNotice" class="activityNotice" value=""/>
										</div>
									</div>
								</div>
								
								
								<!---------------提示--------------->
								<div class="c_ff6363 font16 m_t30">
									温馨提示：为避免打扰客户，日消耗短信总量100条以上才会发送【催付发送数量、短信发送量和当前短信条数】短信
								</div>
								
								
								
								<div class=" display_none save_cancel">
									
									<div class=" m_l100 m_t30 overflow_hidden" style="margin-left:29.208333vw">
										<!-- <div class="w100 h42 f_l tk lh42 BC text-center c_00a0e9 border_00a0e9 b_radius5 cursor_">保存</div> -->
										<div class="w100 h42 f_l tk m_r20 save lh42 text-center c_00a0e9 border_00a0e9 b_radius5 cursor_" id="saveOperator" onclick="saveOperator(this)">保存</div>
									<div class="w100 h42 f_l tk lh42 cancel text-center c_00a0e9 border_00a0e9 b_radius5 cursor_ m_l40" onclick="cancelOperator()">取消</div>
								</div>
								
							</div>
							
							</div>
							<div class="phoneSetupH3 smsSetUpH3">
								<h3 class="clearfix"><i></i><span>订单短信签名设置</span></h3>
							</div>
							<div class="smsSetupBox ">
								<div class="clearfix">
									<label class="f_l" style="width:6.25vw; height:2.604166vw;line-height:2.604166vw;">订单短信签名</label>
									<input class="display_block  bgc_f4f6fa border0 f_l h50 lh50 w220 f_l m_l10 m_r10" type="text" value="北京冰点零度" id="smsqianming">
									<p id="smsqianmingP" class="f_l display_block   border0 f_l h50 lh50  f_l m_l10 m_r10">北京冰点零度</p>
									<a href="javascript:;" id="smsbj" class="w100 h42 f_l tk lh42 text-center c_00a0e9 border_00a0e9 b_radius5 cursor_">编辑</a>
								</div>
								<div class=" m_l100 m_t30 overflow_hidden" id="smsBtn" style="margin-left:29.208333vw">
									<!-- <div class="w100 h42 f_l tk lh42 BC text-center c_00a0e9 border_00a0e9 b_radius5 cursor_">保存</div> -->
									<div id="smsBtnqd" class="w100 h42 f_l tk m_r20 save lh42 text-center c_00a0e9 border_00a0e9 b_radius5 cursor_">保存</div>
									<div id="smsBtnqx" class="w100 h42 f_l  lh42  text-center c_00a0e9 border_00a0e9 b_radius5 cursor_ m_l40">取消</div>
								</div>
							</div>
						</div>
					
					</div>
					<!-- </form> -->
					
					
					<!---------------添加黑名单弹窗--------------->
					<div class="w1673 h1000 bgc_e3e7f0 position_absolute z_5 top40 hmd display_none">
						
						
						<div class="w590 h460 bgc_fff margin0_auto m_t100 p_t20 p_l20 p_r20 p_b40">
							
							
							<div class="text-center c_384856 font16 m_b20 h22 lh22">
								添加黑名单
							</div>
							
							<!--添加黑名单选中状态-->
							<div class="m_b20 h22">
								<div class="c_384856 font16 f_l">黑名单类型：</div>
								<div class="f_l c_a8b2c0 font14 black_uncheck cursor_">
									<div class="w20 h20 b_radius20 bgc_e0e6ef f_l">
										<img class="display_none black_check" src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l m_l10">买家昵称</div>
								</div>
								<div class="f_l c_a8b2c0 font14 m_l20 black_uncheck cursor_">
									<div class="w20 h20 b_radius20 bgc_e0e6ef f_l">
										<img class="display_none black_check" src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="f_l m_l10">手机号码</div>
								</div>
							</div>
							
							<!--黑名单输入框-->
							<textarea class="w589 bgc_f4f6fa h285 border0 m_b55 hmd_text"></textarea>
							
							<!--保存取消按钮-->
							<div class="w232 h42 margin0_auto">
								<div class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ save_hmd">保存</div>
								<div class="m_l30 cancel_hmd f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8">取消</div>
							</div>
							
						</div>
						
					</div>
					
					
					<!---------------导入黑名单弹窗--------------->
					<div class="w1673 h1000 bgc_e3e7f0 position_absolute z_5 top40 upload display_none">
						
						
						<div class="w590 h200 bgc_fff margin0_auto m_t100 p_t20 p_l20 p_r20 p_b20">
							
							
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
								<div class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ save_upload font16">确定</div>
								<div class="m_l30 cancel_upload f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
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
})

var timer=null;
//定义一个手机号全局变量
oldPNum =null;
//定义一个倒计时的全局变量
var T=60;
//页面加载时获取属性值并向页面中添加选中未选中
$(function(){
	var expediting =<%=request.getAttribute("expediting")%>;
	if(expediting !=null && expediting==true){
		$("#expediting").addClass("bgc_check_blue");
	}else{ 
		$("#expediting").removeClass("bgc_check_blue");
	}
	
	var messageRemainder =<%=request.getAttribute("messageRemainder")%>;
	if(messageRemainder !=null && messageRemainder==true){
		$("#messageRemainder").addClass("bgc_check_blue");
	}else{ 
		$("#messageRemainder").removeClass("bgc_check_blue");
	}
	
	var messageCount =<%=request.getAttribute("messageCount")%>;
	if(messageCount!=null && messageCount >= 0){
		$("#messageCount").val(messageCount);
		$('.messageCountNum').text(messageCount);
	}else{
		$("#messageCount").val(0);
		$('.messageCountNum').text('0');
	}
	
	var serviceExpire =<%=request.getAttribute("serviceExpire")%>;
	if(serviceExpire !=null && serviceExpire==true){
		$("#serviceExpire").addClass("bgc_check_blue");
	}else{ 
		$("#serviceExpire").removeClass("bgc_check_blue");
	}
	

	
		$("#activityNotice").attr("disabled",true);
	
	
	var phoneNum =<%=request.getAttribute("phoneNum")%>;
	if(phoneNum!=null){
		$("#phoneNum").val(phoneNum);
		oldPNum = phoneNum;
		$("#phoneNum").val(oldPNum);
		$('.phoneNum2').text(phoneNum);
		$('#oldPNum').val(phoneNum);
	}
	
	var message =<%=request.getAttribute("message")%>;
	if(message!=null){

		$(".tishi_2").show();
		$(".tishi_2").children("p").text(message)
		setTimeout(function(){ 
			$(".tishi_2").hide()
		},3000)
	}
	
	
});

//点击修改
$(".change_number").click(function(){
	var oldPNum=$('#oldPNum').val();
	$(".all_change").removeClass("no_click");
	$(".save_cancel").show();
	//$(".HQ").show();
	$(".phone_num").attr("disabled",false);
	$(".phone_num").show();
	$('.phoneNum2').hide();
	$('#xg').hide();
	$('#messageCount').show();
	$('.messageCountNum').hide();
	$('.1check_box_1').show();
	$('.yuandian').hide();
	$('#activityNotice').show();
	if(oldPNum!=null){
	//不为空时候 发送验证码先隐藏
		$(".HQ").hide();
		$(".JS").hide();
		$("#phoneCode").hide();
	}else{
		$(".HQ").show();
    	$("#phoneCode").show();
	}
	
});


//保存操作,使用ajax异步提交
function saveOperator(e){
	if($(e).hasClass('on'))return;
	$(e).addClass('on');
	var oldPNum=$("#oldPNum").val();
	var phoneNum = $("#phoneNum").val();
	var id = $("#mobileSettingid").val();
	//js验证--短信余额
	 var messageCount = $("#messageCount").val();
	 
	 if((messageCount !=null || messageCount !="")&& isNaN(messageCount)){
		  
		 $("#startRemind").html("*短信余额提醒条数必须是数字!");
		 $(e).removeClass('on');
		 return;
	 }else{
		 $("#startRemind").html("");
	 } 
	 
	//js验证--手机号

	if(phoneNum==null ||phoneNum==""){
		$('.TS').html('*手机号不能为空!');
		$('.TS').show();
		$(e).removeClass('on');
		return;
	}else if(!(/^(13[0-9]|14[5,7]|15[0-3,5-9]|166|17[6-8]|18[0-9]|19[8,9])\d{8}$/.test(phoneNum))){ 
        //alert("手机号码错误!"); 
    	$('.TS').html("*手机号码错误!");
    	$('.TS').show();
    	$(e).removeClass('on');
    	return;
    }else{
    	$('.TS').html("");
    	$('.TS').hide();
    }
	var phoneCode = $("#sendCode").val();
	//新旧手机号不一致,且验证码为空
	if(((oldPNum!=null&&oldPNum != phoneNum) && (phoneCode==null ||phoneCode==""))||((oldPNum ==null || oldPNum == "") && (phoneCode==null ||phoneCode==""))){
		$('.TS').html("*验证码不能为空!");
    	$('.TS').show();
    	$(e).removeClass('on');
    	return;
	}else if((phoneCode !=null && phoneCode !="") && !/^\d{6}$/.test(phoneCode)){
		$('.TS').html("*验证码输入错误!");
    	$('.TS').show();
    	$(e).removeClass('on');
    	return;
	}else{
		$('.TS').html("");
    	$('.TS').hide();
	}
	
	var expediting;
	if($("#expediting").hasClass("bgc_check_blue")){
		$(".expediting").val(true);
		expediting = true;
	}else{
		expediting = false;
	}
	
	var messageRemainder;
	if($("#messageRemainder").hasClass("bgc_check_blue")){
		messageRemainder =true;
	}else{
		messageRemainder =false;
	}
	
	var serviceExpire;
	if($("#serviceExpire").hasClass("bgc_check_blue")){
		serviceExpire = true;
	}else{
		serviceExpire = false;
	}
	
	var activityNotice;
	if($("#activityNotice").hasClass("bgc_check_blue")){
		activityNotice = true;
	}else{
		activityNotice = false;
	}
		 
	$.ajax({
		url:"${ctx}/systemManage/saveMobileSetting",
		data:{"messageCount":messageCount,"lastMobile":oldPNum,"phoneNum":phoneNum,"expediting":expediting,"code":phoneCode,
			"messageRemainder":messageRemainder,"serviceExpire":serviceExpire,"activityNotice":activityNotice,"id":id},

		type: "post",
		success:function(data){
			console.log(data);
			$(e).removeClass('on');
			if(data.status==false){
				$('.TS').html(data.message);
		    	$('.TS').show();
			}else{
				if(data.key){
					$("#mobileSettingid").val(data.key);
				}
				
				if(data.status== true){
					$('.phoneNum2').text(phoneNum);
					$('#oldPNum').val(phoneNum);
				}else{
					$('.phoneNum2').text($('#oldPNum').val());
				}
				$(".save_cancel").show();
				$(".HQ").hide();
				$(".phone_num").attr("disabled",true);
				$(".phone_num").hide();
				$('.phoneNum2').show();

				$('#xg').show();
				$('#messageCount').hide();
				$('.messageCountNum').show();
				$('#oldPNum').hide();
				$('.messageCountNum').text(messageCount);
				$('.yuandian').show();
				
				$('#phoneCode').hide();
				$('#phoneNum').val($('#oldPNum').val());
				$('#sendCode').val('');
				$('.JS #timer').text('60');
				clearTimeout(timer);
				$(".tishi_2").show();
				$(".tishi_2").children("p").text(data.message)
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
		    	$('.TS').show();
		    	$(".save_cancel").hide();
		    	
		    	//保存成功后禁止修改!
		    	$(".all_change").addClass("no_click");
		    	$(".phone_num").attr("disabled",true);
		    	$("#sendCode").val("");
			}
		},
		dataType:'json'
	})
	

}

//取消操作 重新加载
function cancelOperator(){

	$(".save_cancel").show();

	$(".phone_num").attr("disabled",true);
	$(".phone_num").hide();
	$('.phoneNum2').show();
	$('#xg').show();
	
	$('.yuandian').show();
	
	$('#phoneCode').hide();
	$('#phoneNum').val($('#oldPNum').val());
	$('#sendCode').val('');
	$('.JS #timer').text('60');
	clearTimeout(timer);
	$('.TS').hide();
	
}

//短信余额验证
  function checkMessage(){
	 var messageCount = $("#messageCount").val();
	 if(messageCount==null || messageCount==""){
		 $("#startRemind").html("*短信余额提醒条数不能为空!");
	 }else if(isNaN(messageCount)){
		   //alert("短信余额提醒条数必须是数字!");
		 $("#startRemind").html("*短信余额提醒条数必须是数字!")
	 }else{
		 $("#startRemind").html("");
	 }
	 
  };
  
//手机号验证,并判断用户是否修改了手机号码,若修改过 须重新获取验证码
  function checkPhoneNum(){
	

	var oldPNum=$("#oldPNum").val();
	var phoneNum = $("#phoneNum").val();
	if(phoneNum==null ||phoneNum==""){
		$('.TS').html('*手机号不能为空!');
		$('.TS').show();
		$('.JS #timer').text('60');
		$("#phoneCode").hide();
    	$('.JS').hide();
    	$(".HQ").hide();
		clearTimeout(timer);
		return;
	}else if(!(/^(13[0-9]|14[5,7]|15[0-3,5-9]|166|17[6-8]|18[0-9]|19[8,9])\d{8}$/.test(phoneNum))){ 
        
    	$('.TS').html("*手机号码错误!");
    	$('.TS').show();
    	$('.JS #timer').text('60');
    	$("#phoneCode").hide();
    	$('.JS').hide();
    	$(".HQ").hide();
		clearTimeout(timer);
    	return;
    }else if(oldPNum !=phoneNum){

    	$(".HQ").show();
    	$("#phoneCode").show();
    	$('.JS').hide();
    	$('.JS #timer').text('60');
    	//隐藏提示
    	$('.TS').html("");
    	$('.TS').hide();
    	clearTimeout(timer);
    }else{

    	//隐藏发送验证码
    	$(".HQ").hide();
    	$("#phoneCode").hide();
    	
    	//隐藏提示
    	$('.TS').html("");
    	$('.TS').hide();
    	$('.JS #timer').text('60');
    	$('.JS').hide();
    	clearTimeout(timer);
    } 
  }; 
  
 
  
  function remindShop(){
	  //多店铺绑定--给用户提示 暂时不开放,后期删除改事件
		$(".tishi_2").show();
		$(".tishi_2").children("p").text("该功能暂未开放,敬请期待!!")
		setTimeout(function(){ 
			$(".tishi_2").hide()
		},2000)
  }

 $('.HQ').click(function(){
  	
  	//获取手机号并验证
 	var phoneNum = $("#phoneNum").val();
	if(phoneNum==null ||phoneNum==""){
		$('.TS').html('*手机号不能为空!');
		$('.TS').show();
		return;
	}else if(!(/^(13[0-9]|14[5,7]|15[0-3,5-9]|166|17[6-8]|18[0-9]|19[8,9])\d{8}$/.test(phoneNum))){ 
        //alert("手机号码错误!"); 
        //$("#startRemind").html("手机号码错误!");
    	$('.TS').html("*手机号码错误!");
    	$('.TS').show();
    	return;
    }else{
    	$('.HQ').hide();
		$('.JS').show();
		$('.TS').hide();
  		waitTime(60);
    }

	//使用ajax异步回去手机验证码
	$.ajax({
		url:"${ctx}/systemManage/backstageSecurityCode",
		data:{"mobile":phoneNum},
		type: "post",
		success:function(data){
			if(!data.status){
				$('.TS').html(data.message);
		    	$('.TS').show();
		    	$("#sendCode").val("");
			} 
		},
		dataType:'json'
	})
 	
 })	
  
  	
  //60秒倒计时函数
	function waitTime(s){
	 
		var S=s;
		clearTimeout(timer);
		timer=setInterval(function(){
			S--;
			console.log(S);
			document.getElementById('timer').innerHTML=S;
			if(S==0){
				clearInterval(timer);
				S=60;
				document.getElementById('timer').innerHTML=60;
				$('.JS').hide();
				$('.HQ').show();
			}	
		},1000)
	} 

 	$.ajax({
		url:"${ctx}/systemManage/showShopName",
		type: "post",
		success:function(data){
			var json=$.parseJSON(data);
			$('#smsqianming').val(json.shopName);
			$('#smsqianmingP').text(json.shopName);
			sessionStorage.setItem('oldSmsName',json.shopName);
		}
	})
  	$('#smsbj').click(function(){
  		$(this).hide();
  		$('.smsSetupBox p').hide();
  		$('.smsSetupBox input').show();
  		$('#smsBtn').show();
  	});
  	
  	$('#smsBtnqd').click(function(){
  		var thisVal=$('.smsSetupBox input').val();
  		
  		$.ajax({
  			url:"${ctx}/systemManage/modifyShopName",
  			type:"post",
  			data:{
  				shopName:$.trim(thisVal)
  			},
  			success:function(data){
  				console.log(data);
  				var json=$.parseJSON(data);
  				if(json.rc==100){
  					$(".tishi_2").show();
  					$(".tishi_2").children("p").text("签名修改成功！")
  					setTimeout(function(){ 
  						$(".tishi_2").hide()
  					},2000);
  					$('.smsSetupBox p').show();
  			  		$('.smsSetupBox input').hide();
  			  		$('.smsSetupBox input').val(thisVal);
  			  		$('.smsSetupBox p').text(thisVal);
  			  		$('#smsbj').show();
  			  		$('#smsBtn').hide();
  			  		sessionStorage.setItem('oldSmsName',thisVal);
  			  		if(sessionStorage.getItem('name')){
  			  			sessionStorage.setItem('name',thisVal);
  			  		}
  				}else{
  					$(".tishi_2").show();
  					$(".tishi_2").children("p").text("签名修改失败！")
  					setTimeout(function(){ 
  						$(".tishi_2").hide()
  					},2000)
  				}
  			}
  		})
  	});
  	$('#smsBtnqx').click(function(){
  		$('.smsSetupBox p').show();
  		$('.smsSetupBox input').hide();
  		$('.smsSetupBox input').val(sessionStorage.getItem('oldSmsName'));
  		$('#smsbj').show();
  		$('#smsBtn').hide();
  	});
</script>
</html>
