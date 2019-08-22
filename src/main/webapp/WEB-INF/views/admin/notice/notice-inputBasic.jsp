<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告内容编辑</title>
</head>
<body>
	<form:form class="form-horizontal  form-label-stripped form-validation"
		action="${ctx}/admin/notice/notice/edit" method="post" modelAttribute="entity">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<div class="form-actions">
			<button class="btn blue" type="submit" data-grid-reload="#grid-notice-notice-index">
				<i class="fa fa-check"></i> 保存
			</button>
			<button class="btn default" type="button" data-dismiss="modal">取消</button>
		</div>
		<div class="form-body">
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label">公告标题</label>
						<div class="controls">
							<form:input path="title" />
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label">公告来源</label>
						<div class="controls">
							<form:input path="source" />
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label">公告内容</label>
						<div class="controls">
							<form:textarea path="content" class="form-control"  data-htmleditor="kindeditor" data-height="300px"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>
