<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-------------------------左部，侧边栏------------------------->
<div class="w217 h100_ bgc_1c2b36 position_fixed top0 z_12">
	<div class="w100_ h40 p_t10 bor_b_052128">
		<div class="margin0_auto w95 h30">
			<a href="${ctx}/member/index"><img class="margin0_auto w95" src="${ctx}/crm/images/logo.png" /></a>
		</div>
	</div>




	<!--------------------用户信息区-------------------->
	<div class="p_t20 p_b20 m_l20 m_r20 m_t20" style="background-color:#31404A;">
		<!---------------用户ID--------------->
		<div class="font16 c_fff text-center  l_h22 m_b10 cursor_">
			${taobao_user_nick}</div>
		<!---------------其他信息--------------->
		<div class="text-center c_7da2bb m_b10 font12">
			<span>短信剩余:</span><span id ="smsNum"></span>
		</div>

		<div class="text-center c_7da2bb m_b10 font12">
			<span>软件到期剩余:</span>
			<c:choose>
   				<c:when test="${dayCount !=null && dayCount != 0}">  
        			<span>${dayCount}天</span>      
   				</c:when>
  			 	<c:otherwise> 
     				<span>${hourCount}小时</span>
	 			</c:otherwise>
			</c:choose>
		</div>
		<div onclick="renew()" class="text-center margin0_auto c_000 font12 l_h24 cursor_ b_radius4 bgc_yel w100 h24">
			<span>立即续购</span>
		</div>

	</div>


	<!--------------------数据分析-------------------->
	<div class="w100_">
	
		
		<ul class="w100_ most_use font14 c_7da2bb">
		
			<li class="cursor_ mostUse">
				<a class="display_block c_7da2bb" href="${ctx}/sellerGroup/findSellerGroup">
					<p class="text-center h55 l_h55"><img class="w20 h20 m_r25 va_middle" src="${ctx}/crm/images/Customer.png" /><span>客户管理</span></p>
				</a>
			</li>
		
			<li class="cursor_ mostUse">
				<a class="display_block c_7da2bb" href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH">
					<p class="text-center h55 l_h55"><img class="w20 h20 m_r25 va_middle" src="${ctx}/crm/images/order.png" /><span>订单中心</span></p>
				</a>
			</li>
		
			<li class="cursor_ mostUse">
				<a class="display_block c_7da2bb" href="${ctx}/marketingCenter">
					<p class="text-center h55 l_h55"><img class="w20 h20 m_r25 va_middle" src="${ctx}/crm/images/marketing.png" /><span>营销中心</span></p>
				</a>
			</li>
		
			<li class="cursor_ mostUse">
				<a class="display_block c_7da2bb" href="${ctx}/systemManage/mobileSetting">
					<p class="text-center h55 l_h55"><img class="w20 h20 m_r25 va_middle" src="${ctx}/crm/images/back_stage.png" /><span>后台管理</span></p>
				</a>
			</li>
		
			<li class="cursor_ mostUse">
				<a class="display_block c_7da2bb" href="${ctx}/crms/storeData/todyOrderList">
					<p class="text-center h55 l_h55"><img class="w20 h20 m_r25 va_middle" src="${ctx}/crm/images/store.png" /><span>店铺数据</span></p>
				</a>
			</li>
		
			<li class="cursor_ mostUse">
				<a class="display_block c_7da2bb" href="${ctx}/taobao/toRecharge">
					<p class="text-center h55 l_h55"style="padding-right: 0.28rem;"><img class="w20 h20 m_r25 va_middle" src="${ctx}/crm/images/recharge.png" /><span>充值</span></p>
				</a>
			</li>
		
			<li class="cursor_ mostUse">
				<a class="display_block c_7da2bb" href="${ctx}/crms/shopData/smsSendRecord">
					<p class="text-center h55 l_h55"><img class="w20 h20 m_r25 va_middle" src="${ctx}/crm/images/text_history.png" /><span>短信发送记录</span></p>
				</a>
			</li>
		
		</ul>
		
		
	
	
	
	
<!-- 		<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b10"> -->

<!-- 			<a class="c_7da2bb" -->
<%-- 				href="${ctx}/crms/customerManagement/memberGradation"> --%>
<!-- 				<div class="f_l m_r10"> -->
<%-- 					<img class="w20 h20" src="${ctx}/crm/images/Customer.png" /> --%>
<!-- 				</div> -->
<!-- 				<div class="f_l cursor_ font14">客户管理</div> -->
<!-- 			</a> -->

<!-- 		</div> -->

<!-- 		<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b10"> -->

<!-- 			<a class="c_7da2bb" -->
<%-- 				href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH"> --%>
<!-- 				<div class="f_l m_r10"> -->
<%-- 					<img class="w20 h20" src="${ctx}/crm/images/order.png" /> --%>
<!-- 				</div> -->
<!-- 				<div class="f_l  cursor_ font14">订单中心</div> -->
<!-- 			</a> -->

<!-- 		</div> -->

<!-- 		<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b10"> -->
<%-- 			<a class="c_7da2bb" href="${ctx}/marketingCenter"> --%>
<!-- 				<div class="f_l m_r10"> -->
<%-- 					<img class="w20 h20" src="${ctx}/crm/images/marketing.png" /> --%>
<!-- 				</div> -->
<!-- 				<div class="f_l cursor_ font14">营销中心</div> -->
<!-- 			</a> -->
<!-- 		</div> -->

<!-- 		<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b10"> -->
<%-- 			<a class="c_7da2bb" href="${ctx}/backstageManagement/mobileSetting"> --%>
<!-- 				<div class="f_l m_r10"> -->
<%-- 					<img class="w20 h20" src="${ctx}/crm/images/back_stage.png" /> --%>
<!-- 				</div> -->
<!-- 				<div class="f_l cursor_ font14">后台管理</div> -->
<!-- 			</a> -->
<!-- 		</div> -->

<!-- 		<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b10"> -->
<%-- 			<a class="c_7da2bb" href="${ctx}/crms/storeData/todyOrderList"> --%>
<!-- 				<div class="f_l m_r10"> -->
<%-- 					<img class="w20 h20" src="${ctx}/crm/images/store.png" /> --%>
<!-- 				</div> -->
<!-- 				<div class="f_l cursor_ font14">店铺数据</div> -->
<!-- 			</a> -->
<!-- 		</div> -->

	</div>

</div>
<script type="text/javascript">
$(function(){
	 $.post("${ctx}/getUserInfo/getSmsNum", { }, function (data) {
	      $("#smsNum").html(data.smsNum+"条");
	    },"json")
})

 function renew(){
	window.open("${ctx}/getUserCode/login");
}
</script>
