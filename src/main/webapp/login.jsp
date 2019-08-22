<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>
<%@ include file="/common/common.jsp"%>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!--兼容360meta-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="${ctx}/crm/css/public.css" />


<script type="text/javascript" src="${ctx}/crm/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/index.js"></script>

</head>
<body>
   <form   method="post" action="${ctx}/local/test/index">
	用户名 ：<input name="userName" value=""/>
	用户token：<input name="token" value=""/>
	<input type="submit" value="提交"/>
	</form>
</body>
</html>