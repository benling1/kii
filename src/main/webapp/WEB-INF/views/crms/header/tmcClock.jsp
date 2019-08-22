<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%@ include file="/common/common.jsp"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<input id="buttonId" type="button" value="已开启"><!-- 0关闭   1开启 -->
	<input id="clockId" type="text">
	<input id="tmcSize" type="text">
<script type="text/javascript">
	var url = "";
	var buttonVal = $('#buttonId').val();
	if(buttonVal == 1){
		url = "${ctx}/close/tmcClock";
	}
	if(buttonVal == 0){
		url = "${ctx}/open/tmcClock";
	}
	$.posr(url,{},function(data){
		if(data.flag){
			 $('#buttonId').val("已开启");
		}else{
			$('#buttonId').val("已关闭");
		}
		$('#tmcSize').val(data.tmcSize);
	})
</script>
</body>
</html>