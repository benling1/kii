<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统公告</title>
</head>
<body>
	<div class="row search-form-default">
		<div class="col-md-12">
			<form method="get" class="form-inline form-validation form-search-init control-label-sm"
				data-grid-search="#grid-notice-notice-index">
				<div class="form-group">
					<div class="controls controls-clearfix">
						<input type="text" name="search['CN_code_OR_name']" class="form-control input-large" placeholder="代码，名称...">
					</div>
				</div>
				<div class="form-group search-group-btn">
					<button class="btn green" type="submmit">
						<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
					</button>
					<button class="btn default" type="reset">
						<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
					</button>
				</div>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<table id="grid-notice-notice-index"></table>
		</div>
	</div>

	<script type="text/javascript">
        $(function() {
            $("#grid-notice-notice-index").data("gridOptions", {
                url : WEB_ROOT + '/admin/notice/notice/list',
                colModel : [ {
                    label : '公告标题',
                    name : 'title',
                    width : 150,
                    editable : true
                }, {
                    label : '公告来源',
                    name : 'source',
                    width : 150,
                    editable : true
                }, {
                    label : '创建时间',
                    name : 'createdate',
                    width : 120,
                    editable : false
                }, {
                    label : '修改时间',
                    name : 'modifydate',
                    width : 120,
                    align : 'left'
                } ],
                //editurl : WEB_ROOT + '/admin/auth/role/edit',
                fullediturl : WEB_ROOT + '/admin/notice/notice/edit-tabs',
                delurl : WEB_ROOT + '/admin/notice/notice/delete'
            });
        });
    </script>
</body>
</html>