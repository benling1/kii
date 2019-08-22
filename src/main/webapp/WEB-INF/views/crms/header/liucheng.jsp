<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%@ include file="/common/common.jsp"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="clear w1620 bgc_fff c_384856 p_l40 p_t15 LiuCheng">
                        	<div class="w1620">
                               	<a class="f_l selectI" href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH">
                               		<img class="f_l liuCh1" style="width: 3.646vw;" src="${ctx}/crm/images/xiadanguanhuai.png"/>
                               		<img class="f_l liuCh1_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/xiadanguanhuai_J.png"/>
                               		<div style="clear:both;"></div>
                               	</a>
                                <img class="f_l m_t45 m_l22 m_r22" style="width: 2.709vw;" src="${ctx}/crm/images/hengdian.png"/>
                                <a class="f_l selectI" href="${ctx}/crms/order/normal">
	                                <img class="f_l liuCh2" style="width: 3.646vw;" src="${ctx}/crm/images/changguicuifu.png"/>
	                                <img class="f_l liuCh2_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/changguicuifu_J.png"/>
                               		<div style="clear:both;"></div>
                                </a>
                                <img class="f_l m_t45 m_l22 m_r22" style="width: 2.709vw;" src="${ctx}/crm/images/hengdian.png"/>
                                <a class="f_l selectI" href="${ctx}/crms/order/again">
                                	<img class="f_l liuCh3" style="width: 3.646vw;" src="${ctx}/crm/images/ercicuifu.png"/>
                                	<img class="f_l liuCh3_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/ercicuifu_J.png"/>
                               		<div style="clear:both;"></div>
                                </a>
                                <img class="f_l m_t45 m_l22 m_r22" style="width: 2.709vw;" src="${ctx}/crm/images/hengdian.png"/>
                                <a class="f_l selectI" href="${ctx}/paymentCare/queryPaymentCare">
                                	<img class="f_l liuCh4" style="width: 3.646vw;" src="${ctx}/crm/images/fukuanguanhuai.png"/>
                                	<img class="f_l liuCh4_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/fukuanguanhuai_J.png"/>
                               		<div style="clear:both;"></div>
                                </a>
                                <img class="f_l m_t45 m_l22 m_r22" style="width: 2.709vw;" src="${ctx}/crm/images/hengdian.png"/>
                                <a class="f_l selectI" href="${ctx}/logisticsRemind/queryAllSettings">
                                	<img class="f_l liuCh5" style="width: 3.646vw;" src="${ctx}/crm/images/fahuotixing.png"/>
                                	<img class="f_l liuCh5_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/fahuotixing_J.png"/>
                               		<div style="clear:both;"></div>
                                </a>
                                <img class="f_l m_t45 m_l22 m_r22" style="width: 2.709vw;" src="${ctx}/crm/images/hengdian.png"/>
                                <a class="f_l selectI" href="${ctx}/achieveLocality/queryAllSettings">
                                	<img class="f_l liuCh6" style="width: 3.646vw;" src="${ctx}/crm/images/daodatongchengtixing.png"/>
                                	<img class="f_l liuCh6_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/daodatongchengtixing_J.png"/>
                               		<div style="clear:both;"></div>
                                </a>
                                <img class="f_l m_t45 m_l22 m_r22" style="width: 2.709vw;" src="${ctx}/crm/images/hengdian.png"/>
                                <a class="f_l selectI" href="${ctx}/deliverRemind/queryAllSettings">
                               		<img class="f_l liuCh7" style="width: 3.646vw;" src="${ctx}/crm/images/paijiantixing.png"/>
                               		<img class="f_l liuCh7_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/paijiantixing_J.png"/>
                               		<div style="clear:both;"></div>
                                </a>
                                <img class="f_l m_t45 m_l22 m_r22" style="width: 2.709vw;" src="${ctx}/crm/images/hengdian.png"/>
                                <a class="f_l selectI" href="${ctx}/signInRemind/querySignInRemind">
                                	<img class="f_l liuCh8" style="width: 3.646vw;" src="${ctx}/crm/images/qianshoutixing.png"/>
                                	<img class="f_l liuCh8_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/qianshoutixing_J.png"/>
                               		<div style="clear:both;"></div>
                                </a>
                                <img class="f_l m_t45 m_l22 m_r22" style="width: 2.709vw;" src="${ctx}/crm/images/hengdian.png"/>
                                <a class="f_l selectI" href="${ctx}/returnedmoneyWarn/queryReturnedMoneyWarn">
                                	<img class="f_l liuCh9" style="width: 3.646vw;" src="${ctx}/crm/images/huikuantixing.png"/>
                                	<img class="f_l liuCh9_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/huikuantixing_J.png"/>
                               		<div style="clear:both;"></div>
                                </a>
                                <img class="f_l m_t45 m_l22 m_r22" style="width: 2.709vw;" src="${ctx}/crm/images/hengdian.png"/>
                                <a class="f_l selectI" href="${ctx}/crms/reviewCare">
                                	<img class="f_l  liuCh10" style="width: 3.646vw;" src="${ctx}/crm/images/pingjiatixing.png"/>
                                	<img class="f_l  liuCh10_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/pingjiatixing_J.png"/>
                               		<div style="clear:both;"></div>
                                </a>
                                <div style="clear:both;"></div>
                            </div>
                            <div class="w1620" >
                            	<div class="w115  text-center f_l" style="margin-left: 12.3vw;">
                                	<img style="height: 2.709vw;" src="${ctx}/crm/images/shudian.png"/>
                                    <div>
                                    	<a class="selectI" style="position: relative;" href="${ctx}/crms/order/jhs">
		                               		<img class="liuCh11" style="width: 3.646vw;" src="${ctx}/crm/images/juhuasuancuifu.png"/>
		                               		<img class="liuCh11_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/juhuasuancuifu_J.png"/>
                                   		</a>
                                 	</div>
                                </div>
                                <div class="w115  text-center f_l" style="margin-left:7vw;">
                                	<img style="height: 2.709vw;" src="${ctx}/crm/images/shudian.png"/>
                                    <div>
                                    	<a class="selectI" style="position: relative;" href="${ctx}/OrderReminder/jumOrderReminder">
		                               		<img class="liuCh12" style="width: 3.646vw;" src="${ctx}/crm/images/shoudong_dingdantixing.png"/>
		                               		<img class="liuCh12_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/shoudong_dingdantixing_J.png"/>
                                   		</a>
                                 	</div>
                                </div>
                                <div class="w115  text-center f_l" style="margin-left:2.5vw;">
                                	<img style="height: 2.709vw;" src="${ctx}/crm/images/shudian.png"/>
                                    <div>
                                    	<a class="selectI" style="position: relative;" href="${ctx}/delayRemind/queryDelayRemind">
		                               		<img class="liuCh13" style="width: 3.646vw;" src="${ctx}/crm/images/yanshifahuotixing.png"/>
		                               		<img class="liuCh13_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/yanshifahuotixing_J.png"/>
                                   		</a>
                                  	</div>
                                </div>
                                <div class="w115  text-center f_l" style="margin-left:6.5vw;">
                                	<img style="height: 2.709vw;" src="${ctx}/crm/images/shudian.png"/>
                                    <div>
                                    	<a class="selectI" style="position: relative;" href="${ctx}/puzzleRemind/queryPuzzleRemind">
		                               		<img  class="liuCh14" style="width: 3.646vw;" src="${ctx}/crm/images/yinanjiantixing.png"/>
		                               		<img  class="liuCh14_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/yinanjiantixing_J.png"/>
                                   		</a>
                                  	</div>
                                </div>
                                <div class="w115  text-center f_l m_l300" style="margin-left: 7.5vw;">
                                	<img style="height: 2.709vw;" src="${ctx}/crm/images/shudian.png"/>
                                    <div>
                                    	<a class="selectI" style="position: relative;" href="${ctx}/cowryCare/queryCowryCare">
		                               		<img class="liuCh15" style="width: 3.646vw;" src="${ctx}/crm/images/baobeiguanhuai.png"/>
		                               		<img class="liuCh15_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/baobeiguanhuai_J.png"/>
                                   		</a>
                                   	</div>
                                </div>
                                <div class="w115  text-center f_l m_l48">
                                	<img style="height: 2.709vw;" src="${ctx}/crm/images/shudian.png"/>
                                    <div>
                                    	<a class="selectI" style="position: relative;" href="${ctx}/crms/refundCare/buyerRefund">
		                               		<img class="liuCh16" style="width: 3.646vw;" src="${ctx}/crm/images/tuikuanguanhuai.png"/>
		                               		<img class="liuCh16_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/tuikuanguanhuai_J.png"/>
                                   		</a>
                                   	</div>
                                </div>
                                <div class="w115  text-center f_l m_l48">
                                	<img style="height: 2.709vw;" src="${ctx}/crm/images/shudian.png"/>
                                    <div>
                                    	<a class="selectI " style="position: relative;" href="${ctx}/appraiseAmend/showAppraiseAmend">
		                               		<img class="liuCh17" style="width: 3.646vw;" src="${ctx}/crm/images/zhongchapingguanli.png"/>
		                               		<img class="liuCh17_J display_none" style="width: 3.646vw;" src="${ctx}/crm/images/zhongchapingguanli_J.png"/>
                                   		</a>
                                   	</div>
                                </div>
                               <%--  <div class="w115  text-center f_l m_l48">
                                	<img style="height: 2.709vw;" src="${ctx}/crm/images/shudian.png"/>
                                    <div>
                                    	<a class="selectI" style="position: relative;" href="${ctx}/crms/reviewCare">
		                               		<img class="display_none liuCh17" style="position: absolute;width: 1vw;height: 1vw;" src="${ctx}/crm/images/tishi.png"/>
                                    		<img style="width: 3.646vw;" src="${ctx}/crm/images/weipingjiatixing.png"/>
                                   		</a>
                                   	</div>
                                </div> --%>
                                <div class="clear"></div>
                            </div>
                   	    </div>
</body>
<script type="text/javascript">
	
	$(function(){
		loadMap();
	})
	
	function loadMap(){
		var url = "${ctx}/getStatusList";
		$.post(url,function(data){
			var rateCareList = data.rateCareSetupList;
			var sttingTypeList = data.settingTypeList;
			//评价
			if(rateCareList.indexOf('0') == -1){
				$('.liuCh10').removeClass("display_none");
				$('.liuCh10_J').addClass("display_none");
			}else{
				$('.liuCh10').addClass("display_none");
				$('.liuCh10_J').removeClass("display_none");
			}
			if(rateCareList.indexOf('1') == -1 && rateCareList.indexOf('2') == -1){
				$('.liuCh17').removeClass("display_none");
				$('.liuCh17_J').addClass("display_none");
			}else{
				$('.liuCh17').addClass("display_none");
				$('.liuCh17_J').removeClass("display_none");
			}
			
			//订单短信
			if(sttingTypeList.indexOf('1') == -1){
				$('.liuCh1').removeClass("display_none");
				$('.liuCh1_J').addClass("display_none");
			}else{
				$('.liuCh1').addClass("display_none");
				$('.liuCh1_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('2') == -1){
				$('.liuCh2').removeClass("display_none");
				$('.liuCh2_J').addClass("display_none");
			}else{
				$('.liuCh2').addClass("display_none");
				$('.liuCh2_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('3') == -1){
				$('.liuCh3').removeClass("display_none");
				$('.liuCh3_J').addClass("display_none");
			}else{
				$('.liuCh3').addClass("display_none");
				$('.liuCh3_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('13') == -1){
				$('.liuCh4').removeClass("display_none");
				$('.liuCh4_J').addClass("display_none");
			}else{
				$('.liuCh4').addClass("display_none");
				$('.liuCh4_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('6') == -1){
				$('.liuCh5').removeClass("display_none");
				$('.liuCh5_J').addClass("display_none");
			}else{
				$('.liuCh5').addClass("display_none");
				$('.liuCh5_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('7') == -1){
				$('.liuCh6').removeClass("display_none");
				$('.liuCh6_J').addClass("display_none");
			}else{
				$('.liuCh6').addClass("display_none");
				$('.liuCh6_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('8') == -1){
				$('.liuCh7').removeClass("display_none");
				$('.liuCh7_J').addClass("display_none");
			}else{
				$('.liuCh7').addClass("display_none");
				$('.liuCh7_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('9') == -1){
				$('.liuCh8').removeClass("display_none");
				$('.liuCh8_J').addClass("display_none");
			}else{
				$('.liuCh8').addClass("display_none");
				$('.liuCh8_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('14') == -1){
				$('.liuCh9').removeClass("display_none");
				$('.liuCh9_J').addClass("display_none");
			}else{
				$('.liuCh9').addClass("display_none");
				$('.liuCh9_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('4') == -1){
				$('.liuCh11').removeClass("display_none");
				$('.liuCh11_J').addClass("display_none");
			}else{
				$('.liuCh11').addClass("display_none");
				$('.liuCh11_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('26') == -1){
				$('.liuCh12').removeClass("display_none");
				$('.liuCh12_J').addClass("display_none");
			}else{
				$('.liuCh12').addClass("display_none");
				$('.liuCh12_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('10') == -1){
				$('.liuCh14').removeClass("display_none");
				$('.liuCh14_J').addClass("display_none");
			}else{
				$('.liuCh14').addClass("display_none");
				$('.liuCh14_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('12') == -1){
				$('.liuCh15').removeClass("display_none");
				$('.liuCh15_J').addClass("display_none");
			}else{
				$('.liuCh15').addClass("display_none");
				$('.liuCh15_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('29') == -1 && sttingTypeList.indexOf('30') == -1
					&& sttingTypeList.indexOf('31') == -1 && sttingTypeList.indexOf('32') == -1){
				$('.liuCh16').removeClass("display_none");
				$('.liuCh16_J').addClass("display_none");
			}else{
				$('.liuCh16').addClass("display_none");
				$('.liuCh16_J').removeClass("display_none");
			}
			if(sttingTypeList.indexOf('11') == -1){
				$('.liuCh13').removeClass("display_none");
				$('.liuCh13_J').addClass("display_none");
			}else{
				$('.liuCh13').addClass("display_none");
				$('.liuCh13_J').removeClass("display_none");
			}
		},'json');
	}
	
</script>
</html>