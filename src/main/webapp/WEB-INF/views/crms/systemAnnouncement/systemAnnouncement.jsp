<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>系统消息</title>
<%@ include file="/common/common.jsp"%>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!--兼容360meta-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="renderer" content="webkit">



<!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
<!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.${ctx}/crm/js/1.3.0/respond.min.js"></script>
    <![endif]-->

<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js"></script> --%>
<script type="text/javascript" src="${ctx}/crm/js/cuifutxing.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/changguicuifu.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/util.js"></script>


</head>
<body>
	<%@ include file="/WEB-INF/views/crms/header/top.jsp"%>
	<%@ include file="/WEB-INF/views/crms/header/header.jsp"%>
	<div class="w1903 h100_">
		<!-------------------------左部，侧边栏------------------------->
		<div class="load_left"></div>


		<!-------------------------右部------------------------->
		<div class="w1703 m_l200 p_b33">
			<!--------------------顶部导航栏-------------------->
			<div class="load_top"></div>

			<!--------------------主要内容区-------------------->
			<div class="w1660 m_t70 m_l30">
				<div class="w100_ h50 lh50 bgc_fff font18">
					<div class="f_l h50 w4 bgc_1d9dd9"></div>
					<div class="f_l m_l15 c_384856">消息</div>
				</div>
				<div class="w1620 h80  bgc_f1f3f7 c_384856 p_l40">
					<div class="font20 p_t27" id="HEADER">系统消息</div>
				</div>

				<div class="w1620 h830 bgc_fff p_l40 p_t50">
					<div class="w1106">

						<c:if test="${pagination.list.size() ==0 }">
							<div class="f_l w426 text-center">抱歉,暂时没有相关数据!</div>
						</c:if>
						<c:if test="${pagination.list.size() !=0 }">
							<form action="${ctx}/remarkAll" method="post"
								id="SystemAnnouncementsForm">
								<c:forEach items="${pagination.list }" var="systemAnnouncement">
									<input type="hidden" name="ids" value="${systemAnnouncement.id }">
									<div class="w1106 clear p_t10" onclick="remarkOne('${systemAnnouncement.id }','${systemAnnouncement.status }')">
										<div class="w1070 border_bottom_f1f3f7 p_t10 p_b15 m_l10 f_l">
											<div class="w1070">
												<div class="f_l m_l10 font18 cursor_ XTXX c_384856">
													${systemAnnouncement.title }</div>
												
											<!-- 已读框是否显示 -->	    
											<c:if test="${systemAnnouncement.status ==0 }">
												<div class="c_00a0e9 font12 text-center border_00a0e9 f_l h15 m_t5 m_l10 w35 lh15" id="status_${systemAnnouncement.id }">
													未读   
												</div>
											</c:if>	
												
												<div class="f_r font14 c_8493a8"><fmt:formatDate value="${systemAnnouncement.createdDate }" pattern="yyyy-MM-dd HH:mm:ss" /></div>
											</div>
											<p
												class="w1070 p_t10 word_wrap clear in45 l_h22 font14 c_8493a8 display_none">
												 ${systemAnnouncement.content }</p>
										</div>
									</div>
								</c:forEach>
							</form>
						</c:if>

					</div>



					<div class="w1106 clear p_t10">
						<div
							class="w115 h42 l_h42 bgc_00a0e9 c_fff b_radius5 m_l14 f_l text-center font14 m_t10 cursor_ YJBJ" onclick="remarkAll()">一键标记已读</div>
						<div class="f_l w935 h24 m_t22 m_l20 font14 c_8493a8">
							
                            <c:forEach items="${pagination.pageView }" var="page">
										${page } 
							</c:forEach>
						</div>
					</div>


				</div>








			</div>


		</div>

	</div>




</body>
<script>
	$(function() {
		$(".load_top").load("top.html");
		$(".load_left").load("left.html");
	})
	
	//使用ajax单个公告标记为已读
  function remarkOne(id,status){
	var id = id;
	var status = status;
	
	//状态为未读才使用异步
	if(status != 1){
		//使用ajax提交
		$.ajax({
			url:"${ctx}/remarkOne",
			data:{"id":id},
			type: "post",
			success:function(data){
				 if(data.message == true){
					 //标记成功将未读框移除
					$("#status_"+id).remove()
				}else{
	 				$(".tishi_2").show();
	 				$(".tishi_2").children("p").text("标记已读操作失败,请联系系统管理员或重新操作!")
	 				setTimeout(function(){ 
	 					$(".tishi_2").hide()
	 				},3000)
					return;
				} 
			},
			dataType:'json'
		});
	}
	
} 
	
//讲整个当前列表的公告标记为已读
function remarkAll(){
	if(confirm("确定全部标记为已读?")){
		$("#SystemAnnouncementsForm").submit();
	}
}
</script>
</html>
