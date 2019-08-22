<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset=utf-8>
    <title>订单中心</title>
    <%@ include file="/common/common.jsp"%>
    <link href="${ctx}/placeAnOrderCare/static/css/app.07686c2de7b666117dbb33f240357dc6.css" rel=stylesheet></head>
  <body>
  	<%@ include file="/WEB-INF/views/crms/header/top.jsp"%>
	<%@ include file="/WEB-INF/views/crms/header/header.jsp"%>
    <div id=app></div>
    <script type=text/javascript src="${ctx}/placeAnOrderCare/static/js/manifest.ae35892023bb55c96dee.js"></script>
    <script type=text/javascript src="${ctx}/placeAnOrderCare/static/js/vendor.770d25ffd322e9b819e3.js"></script>
    <script type=text/javascript src="${ctx}/placeAnOrderCare/static/js/app.f0eaa4070a4f115875ff.js"></script>
  	<input type="hidden" id="hreflink" value="${ctx}/backstageManagement/mobileSetting">
  </body>
</html>