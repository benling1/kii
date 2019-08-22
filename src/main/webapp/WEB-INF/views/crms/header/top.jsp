<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ include file="/common/common.jsp"%> --%>
<!--------------------顶部导航栏-------------------->
<!-- <div class="h50 w1700 bgc_ffffff right0 position_fixed top0 z_10"> -->
<!-- 	<div class="f_l l_h40"> -->
<%-- 		<%-- <a href="${ctx}/home/index/getData"> --%>
<%-- 			<img class="w20 h20 va_middle m_l40 m_r60 cursor_" src="${ctx}/crm/images/home.png" /> --%>
<%-- 		</a> --%>
<%-- 		<a href="${ctx}/member/index"> --%>
<%-- 			<img class="w20 h20 va_middle m_l40 m_r60 cursor_" src="${ctx}/crm/images/home.png" /> --%>
<!-- 		</a> -->
<%-- 		<a href="${ctx}/systemMessage"> --%>
<%-- 			<img class="w20 h20 va_middle cursor_ l_h40" src="${ctx}/crm/images/mail.png" /> --%>
<!-- 		</a> -->
<!-- 	</div> -->
<!-- 	<div class="f_r"> -->
<!-- 		<div class=" f_l m_r15 m_t10 font16">010-51078040-819</div> -->
<!-- 		<div class=" f_l m_r15"> -->
<%-- 			<img class="w20 h20 va_middle m_t10 cursor_" src="${ctx}/crm/images/kefu.png" /> --%>
<!-- 		</div> -->

<!-- 		<div class=" f_l m_r15"> -->
<%-- 			<img class=" va_middle m_t10 w20" src="${ctx}/crm/images/date.png" /> --%>
<!-- 		</div> -->
<!-- 		<div class="m_r40 f_l l_h40 c_7da2bb w170 time font16 h40"> -->
<!-- 			<!--<span class="m_r20">2016年5月6日</span><span class="m_r10">PM</span><span>13:20</span>-->
<!-- 		</div> -->

<!-- 		<div class="f_l m_r20 "> -->
<%-- 			<img class="w24 m_t10 right_top_set_btn cursor_ va_middle" src="${ctx}/crm/images/set.png" /> --%>
<!-- 		</div> -->
<!-- 	</div> -->
	<!--------------------顶部设置栏-------------------->
<!-- 	<div class="w430 h1020 top34 bgc_ffffff overflow-y position_fixed right0 right_top_set display_none"> -->

<!-- 		<div class="w100_ bor_b_e6eaef p_l30"> -->

<!-- 			<div class="w100_ h50 bor_b_e6eaef m_t30"> -->
<!-- 				<div class="f_l m_r10"> -->
<%-- 					<img class="w30" src="${ctx}/crm/images/no_pic.png" /> --%>
<!-- 				</div> -->
<!-- 				<div class="f_l h30 lh30 c_596671 font18">付款提醒</div> -->
<!-- 			</div> -->

<!-- 			<div class="w100_  bor_b_e6eaef"> -->
<!-- 				<div class="f_l"> -->
<!-- 					<div class="c_596671 m_b10 m_t20 font16">常规催付</div> -->
<!-- 					<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div> -->
<!-- 				</div> -->

<!-- 				<div class="f_l m_l60 m_t28"> -->
<!-- 					<div class="f_l m_r10 m_t6"> -->
<!-- 						<input type="hidden" id="cg_id" /> -->
<%-- 						<img id="img_cg" class="green_check cursor_ w55" src="${ctx}/crm/images/green_uncheck.png" /> --%>
<!-- 						<input type="hidden" id="cg_status" /> -->
<!-- 					</div> -->
<!-- 					<div class="f_l w20 h20 m_t12"> -->
<%-- 						<a href="${ctx}/crms/order/normal"> --%>
<%-- 							<img class="w20 cursor_" src="${ctx}/crm/images/set.png" /> --%>
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="clear"></div> -->

<!-- 			</div> -->

<!-- 			<div class="w100_"> -->
<!-- 				<div class="f_l"> -->
<!-- 					<div class="c_596671 m_b10 m_t20 font16">付款关怀</div> -->
<!-- 					<div class="font12 w230 c_cbcdcf">当买家付款后，软件会自动发送短信给买家</div> -->
<!-- 				</div> -->

<!-- 				<div class="f_l m_l60 m_t28"> -->
<!-- 					<div class="f_l m_r10 m_t6"> -->
<!-- 						<input type="hidden" id="fk_id"/> -->
<%-- 						<img id="img_fk" class="green_check cursor_ w55" src="${ctx}/crm/images/green_uncheck.png"/> --%>
<!-- 						<input type="hidden" id="fk_status"/> -->
<!-- 					</div> -->
<!-- 					<div class="f_l w20 h20 m_t12"> -->
<%-- 						<a href="${ctx}/paymentCare/queryPaymentCare"> --%>
<%-- 						<img class="w20 cursor_" src="${ctx}/crm/images/set.png" /> --%>
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="clear"></div> -->

<!-- 			</div> -->

<!-- 		</div> -->

<!-- 		<div class="w100_ bor_b_e6eaef p_l30"> -->

<!-- 			<div class="w100_ h50 bor_b_e6eaef m_t30"> -->
<!-- 				<div class="f_l m_r10"> -->
<%-- 					<img class="w30" src="${ctx}/crm/images/no_pic.png" /> --%>
<!-- 				</div> -->
<!-- 				<div class="f_l h30 lh30 c_596671 font18">物流提醒</div> -->
<!-- 				<div class="clear"></div> -->
<!-- 			</div> -->

<!-- 			<div class="w100_ bor_b_e6eaef"> -->
<!-- 				<div class="f_l"> -->
<!-- 					<div class="c_596671 m_b10 m_t20 font16">延时发货提醒</div> -->
<!-- 					<div class="font12 w230 c_cbcdcf">当订单超过一定时间未发货，发送短信安抚买家</div> -->
<!-- 				</div> -->

<!-- 				<div class="f_l m_l60 m_t28"> -->
<!-- 					<div class="f_l m_r10 m_t6 "> -->
<!-- 						<input type="hidden" id="ys_id" /> -->
<%-- 						<img id="img_ys" class="green_check cursor_ w55" src="${ctx}/crm/images/green_uncheck.png" /> --%>
<!-- 						<input type="hidden" id="ys_status" /> -->
<!-- 					</div> -->
<!-- 					<div class="f_l w20 h20 m_t12"> -->
<%-- 						<a href="${ctx}/delayRemind/queryDelayRemind"> --%>
<%-- 							<img class="w20 cursor_" src="${ctx}/crm/images/set.png" /> --%>
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="clear"></div> -->

<!-- 			</div> -->

<!-- 			<div class="w100_"> -->
<!-- 				<div class="f_l"> -->
<!-- 					<div class="c_596671 m_b10 m_t20 font16">物流提醒</div> -->
<!-- 					<div class="font12 w230 c_cbcdcf">订单发货时，自动发送短信提醒买家注意收货</div> -->
<!-- 				</div> -->

<!-- 				<div class="f_l m_l60 m_t28"> -->
<!-- 					<div class="f_l m_r10 m_t6 "> -->
<!-- 						<input type="hidden" id="wl_id" /> -->
<%-- 						<img id="img_wl" class="green_check cursor_ w55" src="${ctx}/crm/images/green_uncheck.png" /> --%>
<!-- 						<input type="hidden" id="wl_status" /> -->
<!-- 					</div> -->
<!-- 					<div class="f_l w20 h20 m_t12"> -->
<%-- 						<a href="${ctx}/logisticsRemind/queryAllSettings"> --%>
<%-- 							<img class="w20 cursor_" src="${ctx}/crm/images/set.png" /> --%>
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="clear"></div> -->

<!-- 			</div> -->

<!-- 			<div class="w100_"> -->
<!-- 				<div class="f_l"> -->
<!-- 					<div class="c_596671 m_b10 m_t20 font16">到达同城提醒</div> -->
<!-- 					<div class="font12 w230 c_cbcdcf">当快递到达买家所在城市时，提醒买家注意查收包裹</div> -->
<!-- 				</div> -->

<!-- 				<div class="f_l m_l60 m_t28"> -->
<!-- 					<div class="f_l m_r10 m_t6 "> -->
<!-- 						<input type="hidden" id="tc_id" /> -->
<%-- 						<img id="img_tc" class="green_check cursor_ w55" src="${ctx}/crm/images/green_uncheck.png" /> --%>
<!-- 						<input type="hidden" id="tc_status" /> -->
<!-- 					</div> -->
<!-- 					<div class="f_l w20 h20 m_t12"> -->
<%-- 						<a href="${ctx}/achieveLocality/queryAllSettings"> --%>
<%-- 							<img class="w20 cursor_" src="${ctx}/crm/images/set.png" /> --%>
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="clear"></div> -->

<!-- 			</div> -->

<!-- 			<div class="w100_"> -->
<!-- 				<div class="f_l"> -->
<!-- 					<div class="c_596671 m_b10 m_t20 font16">签收提醒</div> -->
<!-- 					<div class="font12 w230 c_cbcdcf">当客户签收包裹超过一定时间未确认收货，提醒买家确认收货</div> -->
<!-- 				</div> -->

<!-- 				<div class="f_l m_l60 m_t28"> -->
<!-- 					<div class="f_l m_r10 m_t6 "> -->
<!-- 						<input type="hidden" id="qs_id" /> -->
<%-- 						<img id="img_qs" class="green_check cursor_ w55" src="${ctx}/crm/images/green_uncheck.png" /> --%>
<!-- 						<input type="hidden" id="qs_status" /> -->
<!-- 					</div> -->
<!-- 					<div class="f_l w20 h20 m_t12"> -->
<%-- 						<a href="${ctx}/signInRemind/querySignInRemind"> --%>
<%-- 							<img class="w20 cursor_" src="${ctx}/crm/images/set.png" /> --%>
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="clear"></div> -->

<!-- 			</div> -->

<!-- 			<div class="w100_"> -->
<!-- 				<div class="f_l"> -->
<!-- 					<div class="c_596671 m_b10 m_t20 font16">回款提醒</div> -->
<!-- 					<div class="font12 w230 c_cbcdcf">当买家确认收货时，自动发送短信感谢买家</div> -->
<!-- 				</div> -->

<!-- 				<div class="f_l m_l60 m_t28"> -->
<!-- 					<div class="f_l m_r10 m_t6 "> -->
<!-- 						<input type="hidden" id="hk_id" /> -->
<%-- 						<img id="img_hk" class="green_check cursor_ w55" src="${ctx}/crm/images/green_uncheck.png" /> --%>
<!-- 						<input type="hidden" id="hk_status" /> -->
<!-- 					</div> -->
<!-- 					<div class="f_l w20 h20 m_t12"> -->
<%-- 						<a href="${ctx}/returnedmoneyWarn/queryReturnedMoneyWarn"> --%>
<%-- 							<img class="w20 cursor_" src="${ctx}/crm/images/set.png" /> --%>
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="clear"></div> -->

<!-- 			</div> -->


<!-- 		</div> -->

<!-- 		<div class="w100_ bor_b_e6eaef p_l30 m_b150"> -->

<!-- 			<div class="w100_ bor_b_e6eaef m_t30"> -->
<!-- 				<div class="f_l m_r10"> -->
<%-- 					<img class="w30" src="${ctx}/crm/images/no_pic.png" /> --%>
<!-- 				</div> -->
<!-- 				<div class="f_l h30 lh30 c_596671 font18">评价及退款</div> -->
<!-- 				<div class="clear"></div> -->
<!-- 			</div> -->

<!-- 			<div class="w100_ bor_b_e6eaef"> -->
<!-- 				<div class="f_l"> -->
<!-- 					<div class="c_596671 m_b10 m_t20 font16">买家申请退款</div> -->
<!-- 					<div class="font12 w230 c_cbcdcf">当淘宝处理买家退款成功时，发送短信提醒买家</div> -->
<!-- 				</div> -->

<!-- 				<div class="f_l m_l60 m_t28"> -->
<!-- 					<div class="f_l m_r10 m_t6 "> -->
<!-- 						<input type="hidden" id="tk_id" /> -->
<%-- 						<img id="img_tk"class="green_check cursor_ w55" src="${ctx}/crm/images/green_uncheck.png" /> --%>
<!-- 						<input type="hidden" id="tk_status" /> -->
<!-- 					</div> -->
<!-- 					<div class="f_l w20 h20 m_t12"> -->
<%-- 						<a href="${ctx}/crms/refundCare/buyerRefund"> --%>
<%-- 							<img class="w20 cursor_" src="${ctx}/crm/images/set.png" /> --%>
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="clear"></div> -->

<!-- 			</div> -->

<%-- 			<%-- <div class="w100_ bor_b_e6eaef"> --%>
<%-- 								<div class="f_l"> --%>
<%-- 									<div class="c_596671 m_b10 m_t20">评价修改</div> --%>
<%-- 									<div class="font12 w230 c_cbcdcf">通过短信与买家进行交流,修改中差评,大大降低修改中差评的成本</div> --%>
<%-- 								</div> --%>
								
<%-- 								<div class="f_l m_l60" style="margin-top: 1.4vw;"> --%>
<%-- 									<div class="f_l m_r10 m_t6 "> --%>
<%-- 										<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" /> --%>
<%-- 									</div> --%>
<%-- 									<div class="f_l w20 h20 "style="margin-top: 0.6vw;"> --%>
<%-- 										<a href="${ctx}/appraiseAmend/showAppraiseAmend"> --%>
<%-- 											<img class="w20 cursor_" src="${ctx}/crm/images/set.png" /> --%>
<%-- 										</a> --%>
<%-- 									</div> --%>
<%-- 								</div> --%>
<%-- 								<div class="clear"></div> --%>
								
<%-- 							</div> --%>

<!-- 			<div class="w100_"> -->
<!-- 				<div class="f_l"> -->
<!-- 					<div class="c_596671 m_b10 m_t20 font16">中差评安抚</div> -->
<!-- 					<div class="font12 w230 c_cbcdcf">当买家给店铺差评时，发送短信安抚买家</div> -->
<!-- 				</div> -->

<!-- 				<div class="f_l m_l60 m_t28"> -->
<!-- 					<div class="f_l m_r10 m_t6 "> -->
<!-- 						<input type="hidden" id="af_id" /> -->
<%-- 						<img id="img_af" class="green_check cursor_ w55" src="${ctx}/crm/images/green_uncheck.png" /> --%>
<!-- 						<input type="hidden" id="af_status" /> -->
<!-- 						<input type="hidden" id="af_flag" value="pacify" /> -->
<!-- 					</div> -->
<!-- 					<div class="f_l w20 h20 m_t12"> -->
<%-- 						<a href="${ctx}/appraisePacify/showAppraisePacify"> --%>
<%-- 							<img class="w20 cursor_" src="${ctx}/crm/images/set.png" /> --%>
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="clear"></div> -->

<!-- 			</div> -->

<!-- 		</div> -->



<!-- 	</div> -->



<!-- </div> -->

<div class="position_fixed top0 right45_ top40_ z_21 toggle_tishi display_none">
	<img class="toggle_tishi_img" src="" />
</div>


<div class="position_fixed  left0 display_none right0 top40_ z_21 tishi_2 text-center font30 c_fff">
	<p class="tishi_2_text bgc_000000 padding20 radius10"style="padding: 1vw;display: inline;">
		<%-- <img class="display_none" src="${ctx}/crm/images/yu-jiazai.gif"/> --%>
	</p>
</div>
<input type="hidden" value="${ctx}" id="ctx">

<div id="rightsidebarBox">
	<div class="rightsidebarBox">
		<div class="rightsidebarDiv kefu">
			<i></i>	
			<div class="kefuDiv">
				<p class="title">联系我们</p>
				<div class="kefuLink">
					<a href="http://amos.alicdn.com/getcid.aw?v=2&uid=%E5%8C%97%E4%BA%AC%E5%86%B0%E7%82%B9%E9%9B%B6%E5%BA%A6&site=cntaobao&s=1&groupid=0&charset=utf-8" target="_blank" class="clearfix">
						<span>在线客服</span>
						<img src="${ctx}/crm/images/kftx.png"/>
					</a>
				</div>
				<img style="width: 1.62rem; height: .45rem; display: block; padding-left: .2rem; margin-top: .1rem;" src="${ctx}/crm/images/t.png"/>
			</div>
		</div>
		<div class="rightsidebarDiv yijianfankui">
			<i></i>	
		</div>	
		<div class="rightsidebarDiv blackTop">
			<i></i>	
		</div>	
	</div>
</div>
<div class="yijianfankuiBox">
	<div class="titleH3 clearfix">
		<img src="${ctx}/crm/images/fankuiicon.png"/>
		<span>意见反馈</span>
		<em></em>
	</div>
	<div class="bodyBox">
		<p class="title">您好，客云感谢您填写意见反馈，我们会听取您的意见反馈优化产品，感谢您对客云的支持。</p>	
		<div class="bodyDiv clearfix">
			<label class="clearfix"><i>*</i>反馈内容</label>
			<textarea id="feedbackContent"></textarea>
		</div>
		<div class="bodyDiv clearfix">
			<label class="clearfix">图片说明</label>
			<div class="uploadImgBox">
				<div class="clearfix">
					<div class="uploadImgDiv">
						<a class="uploadImgA" href="javascript:;">+ 添加图片</a>
						<input type="file" id="uploadInput"  multiple="multiple"  name="fileAttach" onchange="xmTanUploadImg(this)"/>  
					</div>	
					<p>最多可上传5张图片，支持：jpg/png格式</p>
				</div>
				<div class="uploadImgUl">
					<ul class="clearfix" id="imgboxid">
<!-- 						<li> -->
<%-- 							<img src="${ctx}/crm/images/customer_service_bg.png"/> --%>
<!-- 							<i class="closeImg"></i> -->
<!-- 						</li> -->
					</ul>	
				</div>
			</div>
			
		</div>
		<div class="bodyDiv clearfix">
			<label class="clearfix">联系方式</label>
			<div class="telInput">
				<input type="text" name="" id="contactMode" value="" placeholder="手机／QQ／邮箱／旺旺" />	
				<p>如果对您的反馈我们有不清楚的地方，请让我们通过联系方式联系到您。</p>
			</div>
		</div>
		<div class="bodyBtn">
			<a href="javascript:;">提交</a>
		</div>
	</div>
	
</div>
<div class="markBg"></div> 
<input id="src" type="hidden" value="${ctx}">
<div class="msgAlert"></div>
<script type="text/javascript">

$(function(){
	$(".llq_tip").slideDown(1000);
	$(".chachacha").click(function(){
		$(".llq_tip").slideUp(1000);
	});
});
	/*点击开关提醒*/
	var clickFlag = 0;
	$(".green_check").click(function(){
		if(clickFlag == 0){
			clickFlag++;
			var id = $(this).prev().val();
			var status  = $(this).next().val();
			var flag  = $(this).next().next().val();
			
			if(id == "" || status == "" ){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("请点击设置按钮,将数据设置完毕!")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},5000);
				clickFlag = 0
			}else{
				if(status == '0'){
					$(this).next().val("1");
				}else if(status == '1'){
					$(this).next().val("0");
				}
				var statusId  = $(this).next().val();
				
				var url="${ctx}/top/openOrCloseStatus";
				$.post(url,{"buttonId":statusId,"orderSetupId":id,"flag":flag},function (data) {
					clickFlag = 0
					if(data.status != null){
						if(data.status == '0'){
							$(".tishi_2").show();
			 				$(".tishi_2").children("p").text("开启成功")
			 				setTimeout(function(){ 
			 					$(".tishi_2").hide()
			 				},3000)
							onOrOffStatus();
			 				loadMap();
						}else if(data.status == '1'){
							$(".tishi_2").show();
			 				$(".tishi_2").children("p").text("关闭成功")
			 				setTimeout(function(){ 
			 					$(".tishi_2").hide()
			 				},3000)
							onOrOffStatus();
			 				loadMap();
						}
					}else{
						$(".tishi_2").show();
		 				$(".tishi_2").children("p").text("开启或关闭失败")
		 				setTimeout(function(){ 
		 					$(".tishi_2").hide()
		 				},3000)
					}
				});
			}
			
		}
	});
	/*点击顶部设置按钮显示右边框*/
	/* $(".right_top_set_btn").hover(function(){
		$(".right_top_set").show()
		var url="${ctx}/top/findOrderSetupOfStatus";
		$.post(url,{},function (data) {
			var listVal = data.list;
			if(listVal !=null || listVal!="undefined" || listVal!='' ){
				$.each(listVal,function(i,result){
					if(result.settingType == '2'){
						$("#cg_id").val(result.id);
						$("#cg_status").val(result.status);
					}
					if(result.settingType == '13'){
						$("#fk_id").val(result.id);
						$("#fk_status").val(result.status);
					}
					if(result.settingType == '11'){
						$("#ys_id").val(result.id);
						$("#ys_status").val(result.status);
					}
					if(result.settingType == '6'){
						$("#wl_id").val(result.id);
						$("#wl_status").val(result.status);
					}
					if(result.settingType == '7'){
						$("#tc_id").val(result.id);
						$("#tc_status").val(result.status);
					}
					if(result.settingType == '9'){
						$("#qs_id").val(result.id);
						$("#qs_status").val(result.status);
					}
					if(result.settingType == '14'){
						$("#hk_id").val(result.id);
						$("#hk_status").val(result.status);
					}
					if(result.settingType == '29'){
						$("#tk_id").val(result.id);
						$("#tk_status").val(result.status);
					}
				});
				if( data.orderRateId != null && data.orderRateStatus != ""){
					$("#af_id").val(data.orderRateId);
					$("#af_status").val(data.orderRateStatus);
				}
				onOrOffStatus();
			}
		});
	},function(){
	    $(".right_top_set").hide();
	}); 
	$(".right_top_set").hover(function(){
	    $(".right_top_set").show();
	},function(){
	    $(".right_top_set").hide();
	}); 
}); */

//开关按钮显示状态
/* function onOrOffStatus(){
	var ctx = $("#ctx").val();
	var greenVal = $(".green_check");
	for(var i=0;i<greenVal.length;i++){
		var status = greenVal.eq(i).next().val();
		if(status == '0' && status != ""){
			greenVal.eq(i).attr({"src":ctx+"/crm/images/green_check.png"});
		}else{
			greenVal.eq(i).attr({"src":ctx+"/crm/images/green_uncheck.png"});
		}
	} 
} */
		
		
	/*顶部时间显示*/
	$(function(){
		
			function show(){
				var nowDate = new Date();
				var nowYear = nowDate.getFullYear();
				var nowMonth = nowDate.getMonth()+1;
				var nowDay = nowDate.getDate();
				var nowTime = nowDate.getHours() + ":" + nowDate.getMinutes() + ":" + nowDate.getSeconds();
				var now = nowYear + '/' +    (nowMonth<10 ? '0' : '') + nowMonth + '/' +    (nowDay<10 ? '0' : '') + nowDay + '  ' + nowTime;
		   		$('.time').text(now);
			}
			setInterval(show,1000);
		
	});
	
	
	//点击后修改短信签名输入框的状态为可编辑
	function updateDisabled(){
		$(".shopName").attr("disabled",false);
	}
	//修改用户的shopName（即短信签名）
	function updateShopName(shopName,contentId){
		var content =$("#"+contentId).val();
		var startNum = content.indexOf("【");
		var endNum = content.indexOf("】");
		var newContent;
		if(shopName==null||shopName==""){
			if(startNum<=0){
				newContent = content.substring(endNum+1,content.length);
				$("#"+contentId).val(newContent);
			}
		}else{
			if(startNum==0){
				newContent = content.substring(0,startNum+1)+shopName+content.substring(endNum,content.length);
				$("#"+contentId).val(newContent);
			}else if(shopName!=null&&shopName!=""){
				$("#"+contentId).val("【"+shopName+"】"+content);
			}
		}
		
		addLength();
	}
	
</script>


