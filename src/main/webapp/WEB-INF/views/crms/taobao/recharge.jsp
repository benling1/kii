<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>充值</title>
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

	<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js" ></script> --%>
    <script type="text/javascript" src="${ctx}/crm/js/recharge.js"></script>
    <script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js"></script>
    <script type="text/javascript" src="${ctx}/crm/js/util.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/recharge.css"/>
</head>
<body>
	<%@ include file="/WEB-INF/views/crms/header/top.jsp"%>
	<%@ include file="/WEB-INF/views/crms/header/header.jsp"%>
<div class="w1903 h100_">


    	<!-------------   左边黑块导航   ------------------->
        <div class="load_left"></div>
        
        <div class="w1700 m_l200">
        	
        		<!-------------   右边顶部日期、设置栏   ------------------->
                <div class="load_top"></div>
                
                
                
                
                
                
                
                
                
                
                
                <div class="w1645 h890 bgc_fff  m_l30"><!-------------   页面内容大框   ------------------>
                
                    <div class="w1645 l_h50 font18" style="height:2.5vw;"><!-------------   充值导航栏   ------------------>
                        <div class="f_l  p_l15 bor_left_1d9dd9_4px" style="width: 2.291666vw;">充值</div>
                        <div class="f_r m_r50">
                             <a href="${ctx}/taobao/toRecharge" class="f_l w140 text-center bgc_e3e7f0 cursor_ ChongZhi1 c_384856" id="ChongZhi1_1">充值</a>
                           	 <a href="${ctx}/taobao/rechargeRecord" class="f_l w140 text-center cursor_ ChongZhi1 c_384856" id="ChongZhi1_2">充值记录</a>
                        </div>
                    </div>
                    
                    
                    
                    
                    
                    
                        
                     <div class="ChongZhi2" id="ChongZhi2_1">
                        <div class="w1645 h80 l_h80 bgc_f1f3f7 font20 c_384856">
                            <div class="m_l40">短信充值</div>
                        </div>
                     <div class="w1610 p_t40 p_l50">
                     
<!--                             <div class="w1280 h495 m_t40 font14"> -->
                            
<!--                                 <div class="w1280 h55 l_h55 bgc_f1f3f7"> -->
<!--                                     <div class="f_l w333 text-center">充值条数</div> -->
<!--                                     <div class="f_l w333 text-center">单价</div> -->
<!--                                     <div class="f_l w333 text-center">总金额</div> -->
<!--                                     <div class="f_l  text-center" style="width:14.3vw;">操作</div> -->
<!--                                 </div> -->
                            
<%--                             	<c:if test="${empty rechargeMenuList }"> --%>
<!-- 	                            	<div align="center"> -->
<!-- 	                                 	抱歉,暂时没有相关数据!! -->
<!-- 	                                </div> -->
<%--                             	</c:if> --%>
                            	
<%--                             	<c:if test="${!empty rechargeMenuList }"> --%>
<%-- 								  <c:forEach items="${rechargeMenuList }" var="rechargeMenu"> --%>
<!-- 								  <div class="w1280 h55 l_h55"> -->
<%-- 	                                    <div class="f_l w333 h55 text-center bgc_fafafa m_r2"> ${rechargeMenu.num }条短信</div> --%>
<%-- 	                                    <div class="f_l w333 h55 text-center bgc_fafafa m_r2"> ${rechargeMenu.unitPrice }元/条</div> --%>
<%-- 	                                    <div class="f_l w333 h55 text-center bgc_fafafa m_r2">${rechargeMenu.money }元</div> --%>
<!-- 	                                    <div class=" h55  bgc_fafafa f_l " style="width:14.3vw;"> -->
<!-- 		                                    <a target="_blank" class="f_l " style="color:white;margin-left: 5.5vw;" -->
<%-- 		                                      href="${ctx}/taobao/toPayUrl?itemCode=${rechargeMenu.mid}&superItemCode=${rechargeMenu.superMid}" />  --%>
<!-- 	                                        	<div class=" text-center bgc_00a0e9 m_t10" style="width:4vw;height:2vw;line-height:2vw;border-radius:10px;"> -->
<!-- 	                                     		充值 -->
<!-- 	                                     		</div> -->
<!-- 		                                     </a> -->
<!-- 		                                 </div>     -->
<!--                                	</div> -->
<%-- 							  </c:forEach> --%>
<%-- 							</c:if> --%>
                              
                                
<!--                             </div> -->
                            
<!--                         </div>  -->

						<div class="recharge">
							<p class="title">温馨提示：如充值遇到问题请联系客服</p>
							<table class="rechargeTable" border="0" cellspacing="0" cellpadding="0">
								<tr class="trone">
									<th class="thone">充值条数</th>
									<th class="thtwo">单价</th>
									<th class="ththree">总金额</th>
									<th class="thfour">操作</th>
								</tr>
								<c:if test="${empty rechargeMenuList }">
 	                            	<tr class="trtwo">
 	                                 	<td colspan="4">抱歉,暂时没有相关数据!!</td>
	                                </tr>
                            	</c:if>
                            	<c:if test="${!empty rechargeMenuList }"> 
                            		<c:forEach items="${rechargeMenuList }" var="rechargeMenu">
	                            		<tr class="trtwo">
											<td>
												<span>${rechargeMenu.num }</span>
												<em>条短信</em>
											</td>
											<td>
												<span>${rechargeMenu.unitPrice }</span>
												<em>元／条</em>
											</td>
											<td>
												<span>${rechargeMenu.money }</span>
												<em>元</em>
											</td>
											<td>
												<a class="cz" href="${ctx}/taobao/toPayUrl?itemCode=${rechargeMenu.mid}&superItemCode=${rechargeMenu.superMid}">充值</a>
											</td>	
										</tr>
                            		</c:forEach>
                            	</c:if>
<!-- 								<tr class="trtwo"> -->
<!-- 									<td> -->
<!-- 										<span>5000</span> -->
<!-- 										<em>条短信</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>0.05</span> -->
<!-- 										<em>元／条</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>250</span> -->
<!-- 										<em>.00元</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<a class="cz" href="javascript:;">充值</a> -->
<!-- 									</td>	 -->
<!-- 								</tr> -->
<!-- 								<tr class="trtwo"> -->
<!-- 									<td> -->
<!-- 										<span>10000</span> -->
<!-- 										<em>条短信</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>0.05</span> -->
<!-- 										<em>元／条</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>500</span> -->
<!-- 										<em>.00元</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<a class="cz" href="javascript:;">充值</a> -->
<!-- 									</td>	 -->
<!-- 								</tr> -->
<!-- 								<tr class="trtwo"> -->
<!-- 									<td> -->
<!-- 										<span>20000</span> -->
<!-- 										<em>条短信</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>0.05</span> -->
<!-- 										<em>元／条</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>1000</span> -->
<!-- 										<em>.00元</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<a class="cz" href="javascript:;">充值</a> -->
<!-- 									</td>	 -->
<!-- 								</tr> -->
<!-- 								<tr class="trtwo"> -->
<!-- 									<td> -->
<!-- 										<span>30000</span> -->
<!-- 										<em>条短信</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>0.05</span> -->
<!-- 										<em>元／条</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>1440</span> -->
<!-- 										<em>.00元</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<a class="cz" href="javascript:;">充值</a> -->
<!-- 									</td>	 -->
<!-- 								</tr> -->
<!-- 								<tr class="trtwo"> -->
<!-- 									<td> -->
<!-- 										<span>50000</span> -->
<!-- 										<em>条短信</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>0.05</span> -->
<!-- 										<em>元／条</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>2350</span> -->
<!-- 										<em>.00元</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<a class="cz" href="javascript:;">充值</a> -->
<!-- 									</td>	 -->
<!-- 								</tr> -->
<!-- 								<tr class="trtwo"> -->
<!-- 									<td> -->
<!-- 										<span>100000</span> -->
<!-- 										<em>条短信</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>0.05</span> -->
<!-- 										<em>元／条</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>4600</span> -->
<!-- 										<em>.00元</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<a class="cz" href="javascript:;">充值</a> -->
<!-- 									</td>	 -->
<!-- 								</tr> -->
<!-- 								<tr class="trtwo"> -->
<!-- 									<td> -->
<!-- 										<span>200000</span> -->
<!-- 										<em>条短信</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>0.05</span> -->
<!-- 										<em>元／条</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>9000</span> -->
<!-- 										<em>.00元</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<a class="cz" href="javascript:;">充值</a> -->
<!-- 									</td>	 -->
<!-- 								</tr> -->
<!-- 								<tr class="trtwo"> -->
<!-- 									<td> -->
<!-- 										<span>500000</span> -->
<!-- 										<em>条短信</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>0.04</span> -->
<!-- 										<em>元／条</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>20000</span> -->
<!-- 										<em>.00元</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<a class="cz" href="javascript:;">充值</a> -->
<!-- 									</td>	 -->
<!-- 								</tr> -->
<!-- 								<tr class="trtwo"> -->
<!-- 									<td> -->
<!-- 										<span>1000000</span> -->
<!-- 										<em>条短信</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>0.04</span> -->
<!-- 										<em>元／条</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<span>38000</span> -->
<!-- 										<em>.00元</em> -->
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<a class="cz" href="javascript:;">充值</a> -->
<!-- 									</td>	 -->
<!-- 								</tr> -->
							</table>
							<div class="customRecharge">
								<h3 class="clearfix">
									<strong>自定义充值</strong>
									<div class="f_l">
										<span class="jfgzshow"></span>
										<div class="jfgzBox">
											<img src="${ctx }/crm/images/czgzicon.png">
										</div>
									</div>
								</h3>
								<div class="customRechargeBox clearfix">
									<div class="customRechargeDiv clearfix">
										<label>充值金额</label>
										<input type="text" name="" id="czje" value="" placeholder="请输入要充值的金额" />
										<span>元</span>
									</div>
									<div class="customRechargeDiv clearfix">
										<label>充值短信条数</label>
										<p id="czts">0</p>
										<span>条</span>
									</div>
									<a class="customRechargeCz"  href="javascript:;">充值</a>
								</div>
							</div>
							<div id="customRechargeBox">
								<div class="customRechargeBox">
									<div class="title clearfix">
										<img src="${ctx }/crm/images/qianzitouIcon.png"/>
										<span>请选择支付方式</span>
									</div>	
									<div class="content">
										<div class="contentDiv clearfix">
											<label>当前充值套餐</label>	
											<p><span id="smsMoney">500</span><em>元</em><strong class="smsNumBox">(<span class="smsNum"></span>条短信)</strong></p>
										</div>	
										<div class="contentDiv clearfix">
											<label>支付方式</label>	
											<ul class="contentDivUl">
												<li class="on">支付宝扫码</li>
												<li>支付宝支付</li>
												
	<!-- 											<li>企业网银</li> -->
											</ul>
										</div>
									</div>
									<div class="btn">
										<a href="javascript:;" class="zf">支付</a>
										<a href="javascript:;" class="qx">取消</a>
									</div>
								</div>
								<div class="zfberweimaBox">
									<h3>快捷支付</h3>		
									<p>打开手机支付宝扫一扫</p>
									<div class="ewmBox">
										<img src="${ctx }/crm/images/ewm.png">
									</div>
								</div>
							</div>
							
							<p id="error">自定义充值金额或充值条数不能为空！</p>
						</div>
                      <div class="content">

   					 </div>   
          </div>
			
			<input type="hidden" value="${ctx }" id="urlVal">
</div>

<div class="ChongZhi2 display_none" id="ChongZhi2_2">
                        <div class="w1645 h80 l_h80 bgc_f1f3f7 font20 c_384856">
                            <div class="m_l40">充值记录</div>
                        </div>
                        <div class="w1610 p_t50 p_l50">
                            <div class=" h495 font14">
                            
<!--                                 <div class="w1280 h55 l_h55 bgc_f1f3f7"> -->
<!--                                     <div class="f_l w426 text-center">订单编号</div> -->
<!--                                     <div class="f_l w427 text-center">充值时间</div> -->
<!--                                     <div class="f_l w426 text-center">充值金额</div> -->
<!--                                 </div> -->
                                
<%--                                 <c:if test="${pagination.list.size() ==0 }"> --%>
<!-- 									  <div class="f_l w426 text-center">没有相关数据,请重新查询或更改相关筛选条件!</div> -->
<%-- 								</c:if> --%>
								
<%--                                 <c:if test="${pagination.list.size() !=0 }"> --%>
<%-- 								  <c:forEach items="${pagination.list }" var="userRecharge"> --%>
<!-- 	                                <div class="w1280 h55 l_h55"> -->
<%-- 	                                    <div class="f_l w414 h55 text-center bgc_fafafa m_r2">${userRecharge.orderId }</div> --%>
<%-- 	                                    <div class="f_l w443 h55 text-center bgc_fafafa m_r2"><fmt:formatDate value="${userRecharge.rechargeDate }" pattern="yyyy-MM-dd HH:mm:ss" /></div> --%>
<%-- 	                                    <div class="f_l w418 h55 text-center bgc_fafafa"><fmt:formatNumber value="${userRecharge.rechargePrice}" type="currency"/></div> --%>
<!-- 	                                </div> -->
<%--                                 </c:forEach> --%>
<%--                               </c:if> --%>
                                
                                <table class="czTable" border="0" cellspacing="0" cellpadding="0">
                                	<tr class="trone">
                                		<th>订单编号</th>
                                		<th>充值时间</th>
                                		<th>充值金额</th>
                                		<th>充值状态</th>
                                	</tr>
                                	<c:if test="${pagination.list.size() ==0 }">
                                		<tr class="trtwo">
                                			<td  colspan="4">没有相关数据,请重新查询或更改相关筛选条件!</td>
                                		</tr>
                                	</c:if>
                                	<c:if test="${pagination.list.size() !=0 }">
                                		<c:forEach items="${pagination.list }" var="userRecharge">
                                			<tr class="trtwo">
	                                			<td>${userRecharge.orderId }</td>
	                                			<td><fmt:formatDate value="${userRecharge.rechargeDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                			<td><fmt:formatNumber value="${userRecharge.rechargePrice}" type="currency"/></td>
	                                			<td>
	                                				<c:if test="${userRecharge.status== '1'}">成功</c:if>
				                                    <c:if test="${userRecharge.status== '2'}">失败</c:if>
				                                    <c:if test="${userRecharge.status== '3'}">待付款</c:if>
	                                				
	                                			</td>
	                                		</tr>
                                		</c:forEach>
                                	</c:if>
                                </table>
                            </div>
                            <div class="w1280 h24 m_t22 font14 c_8493a8">
                                <!-- <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
                                <div class="f_r w470 h24 l_h22 font12">
                                    <div class="f_l w45 h22 bor_e0e6ef text-center m_r4 cursor_">上一页</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 bgc_00a0e9 c_fff cursor_">1</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">2</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">3</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">4</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">5</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">6</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">7</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">8</div>
                                    <div class="f_l w22 h22 text-center m_r4">...</div>
                                    <div class="f_l w45 h22 bor_e0e6ef text-center m_r20 cursor_">下一页</div>
                                    <div class="f_l w45 h22 m_r4"><input class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8" type="text" value=""></div>
                                    <div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
                                </div> -->
                             
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
	/* $(function(){
		url="${path}";
		if(url!=null&&url!=""){
			window.open(url);    
		}		
	}) */
	
	//-------------------------显示充值页面------------
	$(function(){
		
		var show = <%=request.getAttribute("show")%>
		if(show!=null && show==1){
		//内容框
			$("#ChongZhi2_2").show();
			$("#ChongZhi2_1").hide();
			//标签
			$("#ChongZhi1_1").removeClass("bgc_e3e7f0").addClass("bgc_fff")
			$("#ChongZhi1_2").removeClass("bgc_fff").addClass("bgc_e3e7f0") 
		}
	})
	
	
	
	
	
	
	//------------------------------------------------------------------
	 var even = document.getElementById("licode");   
        var showqrs = document.getElementById("showqrs");
         even.onmouseover = function(){
            showqrs.style.display = "block"; 
         }
         even.onmouseleave = function(){
            showqrs.style.display = "none";
         }
         
         var out_trade_no = document.getElementById("out_trade_no");

         //设定时间格式化函数
         Date.prototype.format = function (format) {
               var args = {
                   "M+": this.getMonth() + 1,
                   "d+": this.getDate(),
                   "h+": this.getHours(),
                   "m+": this.getMinutes(),
                   "s+": this.getSeconds(),
               };
               if (/(y+)/.test(format))
                   format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
               for (var i in args) {
                   var n = args[i];
                   if (new RegExp("(" + i + ")").test(format))
                       format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
               }
               return format;
           };
           
         out_trade_no.value = 'test'+ new Date().format("yyyyMMddhhmmss");
</script>
</html>
