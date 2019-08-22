<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/crm/js/rem-order.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/jquery.js"></script>
<script type="text/javascript" src="http://g.tbcdn.cn/sj/securesdk/0.0.3/securesdk_v2.js" id="J_secure_sdk_v2" data-appkey="21800702"></script>
<link rel="stylesheet" href="${ctx}/crm/css/public.css" />
<script type="text/javascript" src="${ctx}/crm/js/rightsidebar.js"></script>


<link rel="shortcut icon" href="${ctx}/crm/images/bitbug_favicon.ico" />
<script type="text/javascript">
$.ajaxSetup({ 
	contentType:"application/x-www-form-urlencoded;charset=utf-8", 
    complete:function(XMLHttpRequest,textStatus){ 
    		//通过XMLHttpRequest取得响应头，sessionstatus，
            var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); 
            if(sessionstatus=="timeout"){ 
            	//如果超时就处理 ，指定要跳转的页面
            	window.location.href='https://login.taobao.com/member/login.jhtml'; 
             } 
        } 
});
</script>
