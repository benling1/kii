<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>中差评原因分析</title>
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
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

	<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js" ></script> --%>
	<script type="text/javascript" src="${ctx}/crm/js/cuifutxing.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/zhongchaping.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
	

		
</head>
<body>
	<%@ include file="/WEB-INF/views/crms/header/top.jsp"%>
	<%@ include file="/WEB-INF/views/crms/header/header.jsp"%>
	<div class="w1903 h100_">
			<!-------------------------左部，侧边栏------------------------->
			<div class="load_left"></div>

	
	
			<!-------------------------右部------------------------->
			<div class="w1703 m_l200">
				<!--------------------顶部导航栏-------------------->
				<div class="load_top"></div>
				
				
				<!--------------------主要内容区-------------------->
				<div class="w1660 m_t70 m_l30 ">
					<div class="w100_ h50 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856" style="font-size:1vw;">
							订单中心
						</div>
                        <div class="f_l ">
						<ul style="margin-left: 8.45vw;">
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH">下单关怀</a></li>
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block" href="${ctx}/crms/order/normal">催付提醒</a></li>
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/paymentCare/queryPaymentCare">付款关怀</a></li>	
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/logisticsRemind/queryAllSettings">物流提醒</a></li>
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/cowryCare/queryCowryCare">宝贝关怀</a></li>
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/returnedmoneyWarn/queryReturnedMoneyWarn">回款提醒</a></li>
							<li class="f_l w140 text-center"><a
								class="c_384856 display_block"
								href="${ctx}/crms/refundCare/buyerRefund">退款关怀</a></li>
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block" href="${ctx}/crms/reviewCare">
									评价关怀 </a></li>
							<li class="w140 bgc_fff text-center bgc_f1f3f7 f_l"><a
								class="c_384856 display_block"
								href="${ctx}/appraiseAmend/showAppraiseAmend"> 中差评管理 </a></li>
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block"
								href="${ctx}/OrderReminder/jumOrderReminder"> 手动订单提醒 </a></li>
							<li style="clear:both;"></li>
						</ul>
					</div>
					</div>
					
				
                    <div class="w1620  bgc_f1f3f7 c_384856 p_l40" style="height: 8.8vw;">
                        <div class=" p_t27">
                            <p class="font20" id="tit">中差评原因分析</p>
                            <p class="font14 p_t8">分析得一段时间的中差评原因，找准切入点后期改善。</p>
                        </div>
                        <ul class="h40 p_t40 font14 ">
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseAmend/showAppraiseAmend">中差评查看</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseMonitoring/showAppraiseMonitoring">中差评监控</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraisePacify/showAppraisePacify">中差评安抚</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 ping_1 cursor_"><a class="c_384856 display_block"  href="${ctx}/crms/appraise/middleBadAssessment?filtrateTime=最近7天">中差评统计</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseSet/showAppraiseCauseSet">中差评原因设置</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_fff cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseAnalyze/showAppraiseCauseAnalyze">中差评原因分析</a></li>
                        </ul>
                        
                    </div>
                    
                    
                    
 


                    <!-- 中差评原因分析 -->
                    <%@ include file="/WEB-INF/views/crms/header/liucheng.jsp" %>
                    <div class=" ping_2">
                    	<div class="w1660 bgc_fff h2000 " >
                    		<div class="w1380 h2000 font14">
                    			<div class=" w1380 p_l70 h40 p_t40 l_h40">
                    				<span class="f_l">统计时间范围：</span>
									
								    <div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
								    	<img class="cursor_ one_check_ display_none m_b10" src="${ctx}/crm/images/black_check.png" />
								    </div>
								    <div class="f_l m_r35 l_h20">
								    	最近7天
								    </div>
									
									<div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
								    	<img class="cursor_ one_check_ display_none m_b10" src="${ctx}/crm/images/black_check.png" />
								    </div>
								    <div class="f_l m_r35 l_h20">
								    	最近15天
								    </div>
									<div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
								    	<img class="cursor_ one_check_ display_none m_b10" src="${ctx}/crm/images/black_check.png" />
								    </div>
								    <div class="f_l m_r15 l_h20">
								    	最近一个月
								    </div>
									
									<div class="f_l position_relative">
										<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" id="tser09"  readonly onclick="$.jeDate('#tser09',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'}) ">
										<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
									</div>
										
									<div class="f_l">~</div>
									
									<div class="f_l position_relative">
										<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" id="tser10"  readonly onclick="$.jeDate('#tser10',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
										<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
									</div>
									
									
									
									
								        <div class="w100 h40 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ l_h40 m_r10">
										统计
									    </div>
									    <div class="w100 h40 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ l_h40 m_r10">
										导出
									    </div>
                    			</div> 
                    			
                    			<div class="w810 h50 p_t40 p_l70">
	                    		    <div class="fenxi_1  w150 h40 border_d9dde5 b_radius5 text-center f_l l_h40 c_fff cursor_ bgc_00a0e9">
								    		中差评概况
							        </div>
							        <div class="fenxi_1 w150 h40 border_d9dde5 b_radius5  text-center f_l l_h40 c_8493a8 cursor_">
								    		详细原因分析
							        </div>
							        <div class="fenxi_1 w150 h40 border_d9dde5 b_radius5  text-center f_l l_h40 c_8493a8 cursor_">
								    		中差评商品分析
							        </div>
							    </div>
                    			
                    			<!--  中差评概况-->
                    			<div class=" fenxi_2 h830 w1290">
	                    			<div class="w1290 h380 p_l70 p_t20">
	                    				曲线图
	                    			</div>
	                    			
	                    			<div class="w1280 h330 p_l70 p_t20">
	                    					<table class="text-center font14">
										        <tr class=" h55 bgc_fafafa ">
													<th class="w255">原因类别</th>
													<th class="w255">中评</th>
													<th class="w255">差评</th>
													<th class="w255">合计</th>
													<th class="w255">占比</th>
										        </tr>
										        
												<tr class=" h55 bgc_f4f4f4 ">
													<td class="w255"></td>
													<td class="w255"></td>
													<td class="w255"></td>
													<td class="w255"></td>
													<td class="w255"></td>
												</tr>
												
												<tr class="w255 h55 bgc_fafafa ">
													<td class="w420">gd</td>
													<td class="w80">hfjksa</td>
													<td class="w90">s</td>
													<td class="w305">fs</td>
													<td class="w260">yhr</td>
												</tr>
												
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												
												<tr class="w255 h55 bgc_fafafa ">
													<td class="w420"></td>
													<td class="w80"></td>
													<td class="w90"></td>
													<td class="w305"></td>
													<td class="w260"></td>
												</tr>
												
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</table>
	                    			</div>
                    			</div>
                    			
                    			<!-- 详细原因分析 -->
                    			<div class="display_none fenxi_2">
                    				<div class="w1290 h380 p_l70 p_t20">
	                    				曲线图
	                    			</div>
	                    			
	                    			<div class="w1280 p_l70 p_t20">
	                    					<table class="text-center font14">
										        <tr class=" h55 bgc_fafafa font14">
													<th class="w255">原因类别</th>
													<th class="w255">中评</th>
													<th class="w255">差评</th>
													<th class="w255">合计</th>
													<th class="w255">占比</th>
										        </tr>
										        
												<tr class=" h55 bgc_f4f4f4 font14">
													<td class="w255">包装问题</td>
													<td class="w255"></td>
													<td class="w255"></td>
													<td class="w255"></td>
													<td class="w255"></td>
												</tr>
												
												<tr class=" h55 bgc_fafafa font14">
													<td >宝贝详情页编写有误</td>
													<td ></td>
													<td ></td>
													<td ></td>
													<td ></td>
												</tr>
												
												<tr class="w255 h55 bgc_f4f4f4 font14">
													<td>备注留言没有满足</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												
												<tr class="w255 h55 bgc_fafafa font14">
													<td >比自己预期差</td>
													<td ></td>
													<td ></td>
													<td ></td>
													<td ></td>
												</tr>
												
												<tr class="w255 h55 bgc_f4f4f4 font14">
													<td>差评师</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 font14">
													<td>尺寸问题</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 font14">
													<td>发货慢</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>怀疑是否正品</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>价格高</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>客服没有备注或交接</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>客服业务不熟</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>快件派送问题</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>没用就乱给中差评</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>收到东西少</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>收到东西有错</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>收到坏的货物</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>售后服务差</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>售前客服态度差</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>误给中差评</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>响应时间久</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>运费问题</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>质量差</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td>各自</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												
												
												
											</table>
	                    			</div>
                    			</div>
                    			<!--  中差评商品分析  -->
                    			<div class="display_none fenxi_2 h830 w1290">
                    				<div class="w1290 h380 p_l70 p_t20">
	                    				曲线图
	                    			</div>
	                    			
	                    			<div class="w1280 h330 p_l70 p_t20">
	                    					<table class="text-center font14">
										        <tr class=" h55 bgc_fafafa ">
													<th class="w255">商品</th>
													<th class="w255">中评</th>
													<th class="w255">差评</th>
													<th class="w255">合计</th>
													<th class="w255">占比</th>
													<th class="w255">查看详细</th>
										        </tr>
										        
												<tr class=" h55 bgc_f4f4f4 ">
													<td class="w255"></td>
													<td class="w255"></td>
													<td class="w255"></td>
													<td class="w255"></td>
													<td class="w255"></td>
													<td class="w255"></td>
												</tr>
												
												<tr class=" h55 bgc_fafafa ">
													<td >gd</td>
													<td ></td>
													<td ></td>
													<td ></td>
													<td ></td>
													<td ></td>
												</tr>
												
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td ></td>
												</tr>
												
												<tr class="w255 h55 bgc_fafafa ">
													<td ></td>
													<td ></td>
													<td ></td>
													<td ></td>
													<td ></td>
													<td ></td>
												</tr>
												
												<tr class="w255 h55 bgc_f4f4f4 ">
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</table>
	                    			</div>
                    			</div>
                    			
                    			
                    			
                    		</div>
                    			
                    	</div>
                    </div>
			</div>
			
			
			
			

</body>
<script>
	$(function(){
		$(".load_top").load("top.html");
		$(".load_left").load("left.html");
	})
</script>
</html>