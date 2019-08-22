<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/common/common.jsp"%>
<head>
	<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/recharge.css"/>
</head>
<html>
<body>
	<input type="hidden" id="totalAmount" value="${totalAmount}">
	<input type="hidden" id="rechargeNum" value="${rechargeNum}">
	<p id="error">自定义充值金额或充值条数不能为空！</p>
</body>
<script>
$.ajax({
	url:'${ctx}/aliPay/skipPay',
	type:'post',
	data:{
		totalAmount:$('#totalAmount').val(),
		rechargeNum:$('#rechargeNum').val()
	},
	success : function(data) {
		console.log(data.data);
		if(data.data){
			var str=data.data;
			var str1=data.data.replace('<script>document.forms[0].submit();','<script id="formpost">document.forms[0].submit();document.forms[0].remove();document.getElementById("formpost").remove()');
			$('body').append(str);
			
		}else{
			$('#error').css({
				width:'130px',
				marginLeft:'-65px',
				'z-index':'1000'
			})
			$('#error').show();
			$('#error').text('充值失败!');
			setTimeout(function(){
				$('#error').hide();
				window.close();
			},2000);
		}
		
		
	}
});
</script>
</html>
