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
	<form id="form" action="${ctx}/smsSendAppoint/encryptImportPhone">	
		id:<input name="id" type="text" />
		userId<input name="userId" type="text" />
		pageNum<input name="pageNum" type="text" />
		<input type="submit" value="执行操作" onclick="submit()" /><br>
	</form>	
<script type="text/javascript">
var clickFlag = false;
function submit(){
	if(clickFlag==true){
		return;
	}
	clickFlag = true;
	$("#form").ajaxSubmit({
 		type:'post',
		dataType:'json',
		success : function(data) {
			clickFlag = false;
			$('.size').text(data.success);
	});
}
</script>
</body>
</html>