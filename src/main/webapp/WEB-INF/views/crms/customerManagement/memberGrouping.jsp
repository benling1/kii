<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>客户管理-会员分组</title>
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
	<script type="text/javascript" src="${ctx}/crm/js/vip_grouping.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/util.js"></script>
	<script type="text/javascript" src="${ctx}/crm/js/drag.js"></script>
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
					<div class="w1645  m_l30">
						<div class="w100_ h48 lh50 bgc_fff font18">
							<div class="f_l h50 w4 bgc_1d9dd9"></div>
							<div class="f_l m_l15 c_384856">
								客户管理
							</div>
							<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
								
								
								<a class="c_384856" href="${ctx}/sellerGroup/findSellerGroup">
									<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
										会员分组
									</div>
								</a>
								<a class="c_384856" href="${ctx}/crms/memberInformation/memberInformation">
									<div class="f_l w140 text-center cursor_">会员信息</div>
								</a>
								<a class="c_384856" href="${ctx}/memberInteraction/memberSmsSendAndReceIve">
									<div class="f_l w140 text-center cursor_">
										会员互动
									</div>
								</a>
								<a class="c_384856"
									href="${ctx}/crms/marketingCenter/smsBlack">
									<div class="f_l w140 text-center cursor_">黑名单列表</div>
								</a>
<%-- 								<a class="c_384856" href="${ctx}/crms/customerManagement/memberGradation"> --%>
<!-- 									<div class="f_l w140 text-center cursor_"> -->
<!-- 										会员等级划分 -->
<!-- 									</div> -->
<!-- 								</a> -->
								<%-- <a class="c_384856" href="${ctx}/crms/customerManagement/blacklistManagemen">
									<div class="f_l w140 text-center cursor_">
										黑名单管理
									</div>
								</a> --%>
							</div>
						</div>
						
						
						
						<!---------------会员分组--------------->
						<div class="h940">
							<!----------上部---------->
							<div class="w1655 h80 bgc_f1f3f7 c_384856 p_l40 p_t30">
								
								<!---------------标题--------------->
								<div class="font24 m_b10">
									会员分组
								</div>
								<!---------------描述--------------->
								<div class="font14">
									默认分组，必须导入历史订单后才会准确，在没有导入历史订单前，建议新建分组。
								</div>
								
							</div>
							<!----------下部---------->
						
							<div class="w1655  bgc_fff p_t30 p_l40 p_b40 discount_in m_b30">
								
								<div class="">
									<!--------分组设置-------->
									<div class="m_b22">
										<div class="h22 m_b35">
											<!-- <div class="f_l m_r60"  onclick="findSellerGroup('');">
												<div class="f_l 1check_box_2">
											    	<div id="quanbufenzu" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
											    	</div>
											    	<div class="m_l10 cursor_ f_l font14 c_384856">
											    		全部分组
											    	</div>
												</div>
											</div>
											<div class="f_l m_r60"  onclick="findSellerGroup('1');">
												<div class="f_l 1check_box_2">
											    	<div  id="moren" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
											    	</div>
											    	<div class="m_l10 cursor_ f_l font14 c_384856">
											    		默认分组
											    	</div>
												</div>
											</div>
											<div class="f_l m_r60"  onclick="findSellerGroup('2');">
												<div class="f_l 1check_box_2">
											    	<div id="niming" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
											    	</div>
											    	<div class="m_l10 cursor_ f_l font14 c_384856">
											    		自定义分组
											    	</div>
												</div>
											</div> -->
											
											
											<!-- <div class="f_r c_384856 p_r30">
												上次更新时间<span>2016-10.19 15:30</span>
											</div> -->
										</div>
										<button class="add_group_btn cursor_ w140 h50 lh50 font14 c_fff bgc_00a0e9 border0 b_radius5">
											<img class="m_r20 w14 h14 vercel" src="${ctx}/crm/images/add_list.png" />
											创建分组
										</button>
									</div>
									
									<!--------详细数据-------->
									<div class="c_384856 text-center font16">
										<table>
										  <tr style="height:2.6vw;" class="">
										    <th class="w75">
										    	序号
										    </th>
										    <th class="w220">分组名称</th>
										    <th class="w175">客户数(人)</th>
										    <th class="w165">分组来源</th>
										    <th class="w375">说明</th>
										    <th style="width:21.5vw;" class="w290">操作</th>
										  </tr>
								  		<c:forEach items="${pagi.list}" var="sellerGroup"
										varStatus="var">
											<tr>
												<td>${var.getCount()}</td>											
												<td>${sellerGroup.groupName}</td>
												<td class="c_00a0e9" id = "${sellerGroup.groupId}">${sellerGroup.memberCount}</td>
												<td>${sellerGroup.memberType}</td>
												<td>${sellerGroup.remark}</td>
												<td>
													<div onclick="getMemberGroup('${sellerGroup.groupId}','${sellerGroup.memberType}');"
														class="m_l20 f_l m_r10 cursor_ marketing_vip w98 h33 lh33 bgc_fff c_00a0e9 text-center border_00a0e9 b_radius5">
														会员营销</div> 
													<c:if test="${sellerGroup.memberType =='自定义分组'}">
														<div onclick="updateMemberGroup('${sellerGroup.groupId}');"
															class="update_btn m_r10 f_l cursor_ marketing_vip w58 h33 lh33 bgc_fff change_group_btn c_00a0e9 text-center border_00a0e9 b_radius5">
															修改</div>
														<div
															class="f_l cursor_ marketing_del w58 h33 lh33 bgc_fff c_ff6363 text-center border_ff6363 b_radius5"
															onclick="delSellerGroup('${sellerGroup.groupId}','${sellerGroup.rule_id}');">
															删除</div>
													</c:if>
													<div style="margin-left:0.520833vw; padding:0 0.5vw;"
															class="f_l cursor_ marketing_del  h33 lh33 bgc_fff c_ff6363 text-center border_ff6363 b_radius5"
															onclick="updateSellerGroupCount('${sellerGroup.groupId}','${sellerGroup.memberType}');">
															更新会员数量</div>
												</td>
											</tr>
									</c:forEach>
										</table>
									</div>
									<input id="pageNoMaxId" type="hidden" value="${pageNoMax}">
									<input id="pageNoId" type="hidden" value="${pageNo}">
									<!--------分页-------->
		                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
		                               <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
										<c:forEach items="${pagi.pageView }" var="page">
											${page } 
								 	 	</c:forEach>
									</div>
		                            </div>
								</div>
							</div>
						</div>
					</div>	
				</div>
			</div>
	</div>
	<!---------------创建分组弹窗--------------->
		<div class="markBg"></div>
							
		<div class=" bgc_fff  p_t20 p_l20 p_r20 p_b20 w1240 addGrouping bombBox" id="bombBox">
			
				
			<!----------标题---------->
			<div class="c_384856 font18 text-center m_b55 m_t35 addGroupingTitle">
				创建分组
			</div>
			
			<!----------筛选---------->
			<div>
				<!----------块1---------->
				<div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22" style="width: 5.3vw">
						最近交易:
					</div>
					<div class="f_l font16 h22">
						<!----------选择区域1---------->
						<input id="tradeStatus"  type="hidden">
						<ul class="area_check">
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_2">
							    	<div onclick="addTradeStatus('');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l bgc_check_blue">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		不限
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_2">
							    	<div onclick="addTradeStatus('近7天');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		近7天
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_2">
							    	<div onclick="addTradeStatus('近1个月');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		近1个月
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_2">
							    	<div onclick="addTradeStatus('近3个月');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		近3个月
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_2">
							    	<div onclick="addTradeStatus('近半年');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		近半年
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l date_set zdy">
						    	<div class="f_l 1check_box_2" onclick="addTradeStatus('自定义');">
							    	<div  class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856" style="width:3.5vw">
							    		自定义
							    	</div>
								</div>
							</li>
							<li class="f_l m_t_15 display_none zdy_1" style="padding-top: 0.6vw;">	
								<p style="height: 1.5vw; line-height: 1.5vw; margin-right:0.5vw;" class="f_l">近</p>
								<input id="tradeTime" type="text" class="f_l h50" style="margin-right:1vw;width: 6vw;height: 1.5vw;" onkeyup="value=value.replace(/[^\d]/g,'')" />
								<div class="wrap f_l">
								    <div class="nice-select h50" style="width: 3vw;height: 1.5vw;" name="nice-select">
								    	<input id="tradeTimeType"  type="text" style="height: 1.5vw;" readonly value="天" />
								    	<ul style="z-index:2;">
								    		<li value="1" >天</li>
								    		<li value="2" >月</li>
								    		<li value="3" >年</li>
								    	</ul>
								  	</div>
								</div>
								<div class="clear"></div>
							</li>
						</ul>
					</div>
				</div>
				
				<!----------块2---------->
				<div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22" style="width: 5.3vw">
						交易次数:
					</div>
					<div class="f_l font16 h22">
						<!----------选择区域1---------->
						<input id="tradeNum" type="hidden" value="0">
						<ul class="time_check">
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2" onclick="getTradeNum('0');">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l bgc_check_blue ">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		不限
							    	</div>
								</div>
							</li>							
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2" onclick="getTradeNum('1');" >
							    	<div  class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		1次
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2"  onclick="getTradeNum('2');">
							    	<div  class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		2次
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div onclick="getTradeNum('3');" class="f_l 1check_box_2">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		3次
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2" onclick="getTradeNum('3次以上');">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		3次以上
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l time_set zdy">
						    	<div class="f_l 1check_box_2" onclick="getTradeNum('');">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856"  style="width:3.5vw">
							    		自定义
							    	</div>
								</div>
							</li>
							
							<li class="f_l m_t_15 display_none zdy_1">
								<input id="minTradeNum"  onblur="zhengze('minTradeNum','maxTradeNum')" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_r10"
								 type="text" onkeyup="value=value.replace(/[^\d]/g,'')" >
								<span class="f_l" style="height:2.604166vw;; line-height:2.604166vw;margin-right:0.5vw ">次</span>
									
								<div class="f_l h50 lh50">~</div>
								
								<input id="maxTradeNum" onblur="zhengze('minTradeNum','maxTradeNum')" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_l10" 
								type="text" onkeyup="value=value.replace(/[^\d]/g,'')">
								<span class="f_l" style="height:2.604166vw;; line-height:2.604166vw;margin-left:0.5vw ">次</span>
							</li>
						</ul>
					</div>
				</div>
				
				<!----------块3---------->
				<div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22" style="width: 5.3vw">
						累计金额:
					</div>
					<div class="f_l font16 h22">
						<!----------选择区域1---------->
						<input id="accumulatedStatus" type="hidden">
						<ul class="time_check">
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div onclick="getAccumulatedStatus('0');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l bgc_check_blue">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		不限
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div onclick="getAccumulatedStatus('1~100元');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		1~100元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div onclick="getAccumulatedStatus('100~200元');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		100~200元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div onclick="getAccumulatedStatus('200~300元');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		200~300元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l time_set zdy">
						    	<div class="f_l 1check_box_2">
							    	<div onclick="getAccumulatedStatus('自定义');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856" style="width:3.5vw">
							    		自定义
							    	</div>
								</div>
							</li>
							
							<li class="f_l m_t_15 display_none zdy_1">
								<input id="minAccumulatedAmount" onblur="zhengze('minAccumulatedAmount','maxAccumulatedAmount')" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_r10" type="text" onkeyup="value=value.replace(/[^\d]/g,'');test();">
								<span class="f_l" style="height:2.604166vw;; line-height:2.604166vw;margin-right:0.5vw ">元</span>
								<div class="f_l h50 lh50">
									~
								</div>
								<input id="maxAccumulatedAmount" onblur="zhengze('minAccumulatedAmount','maxAccumulatedAmount')" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_l10" type="text" onkeyup="value=value.replace(/[^\d]/g,'');test();">
								<span class="f_l" style="height:2.604166vw;; line-height:2.604166vw;margin-left:0.5vw ">元</span>
							</li>
						</ul>
					</div>
				</div>
				
				<!----------块4---------->
				<div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22" style="width: 5.3vw">
						 平均订单金额:
					</div>
					<div class="f_l font16 h22">
						<input id="averagePrice" type="hidden">
						<!----------选择区域1---------->
						<ul>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div onclick="getAveragePrice('0');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l bgc_check_blue">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		不限
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div onclick="getAveragePrice('1~100');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		1~100元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div onclick="getAveragePrice('100~200');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		100~200元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div onclick="getAveragePrice('200~300');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		200~300元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l time_set zdy">
						    	<div class="f_l 1check_box_2">
							    	<div onclick="getAveragePrice('自定义');" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856" style="width:3.5vw">
							    		自定义
							    	</div>
								</div>
							</li>
							
							<li class="f_l m_t_15 display_none zdy_1">
								<input id="minAveragePrice" onblur="zhengze('minAveragePrice','maxAveragePrice')" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_r10" type="text" onkeyup="value=value.replace(/[^\d]/g,'')">
								<span class="f_l" style="height:2.604166vw;; line-height:2.604166vw;margin-right:0.5vw ">元</span>
								<div class="f_l h50 lh50">
									~
								</div>
								
								<input id="maxAveragePrice" onblur="zhengze('minAveragePrice','maxAveragePrice')" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_l10" type="text" onkeyup="value=value.replace(/[^\d]/g,'')">
								<span class="f_l" style="height:2.604166vw;; line-height:2.604166vw;margin-left:0.5vw ">元</span>
							</li>
						</ul>
					</div>
				</div>
				
				<!----------块5---------->
				<!-- <div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22">
						会员等级:
					</div>
					<div class="f_l font16 h22">
						<input id="memberGrade" type="hidden">
						--------选择区域1--------
						<ul id="memberGradeClass">
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_2">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l bgc_check_blue">
							    		<input type="hidden" value="">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		所有会员
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_3">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l ">
							    		<input type="hidden" value="0">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		店铺会员
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_3">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l ">
							    		<input type="hidden" value="1">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		普通会员
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_3">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l ">
							    		<input type="hidden" value="2">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		高级会员
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_3">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l ">
							    		<input type="hidden" value="3">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		VIP会员
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_3">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l ">
							    		<input type="hidden" value="4">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		至尊VIP会员
							    	</div>
								</div>
							</li>
						</ul>
					</div>
				</div> -->
				
				<!----------块6---------->
				<div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22"  style="width: 5.3vw">
						地区:
					</div>
					<div class="f_l font16 h22">
						<!----------选择区域1---------->
						<input type="hidden" id="provinceStatus">
						<ul>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2" onclick="getProvinceStatus('0');">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l bgc_check_blue" >
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		默认全部
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2" onclick="getProvinceStatus('1');">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l position_relative JiangZheHu" >
							    	<span class="position_absolute w200 l_h22 left_70 top_32 c_384856 bgc_f4f6fa text-center display_none font14">江苏省，浙江省，上海</span>
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		江浙沪
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2" onclick="getProvinceStatus('2');">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l JiangZheHu position_relative" >
							    	<span class="position_absolute w200 l_h22 left_70 top_32 c_384856 bgc_f4f6fa text-center display_none font14">新疆、西藏、云南省、青海省</span>
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		偏远地区
							    	</div>
								</div>
							</li>
							
							<li class="h22 lh22 f_l time_set zdy_" onclick="getProvinceStatus('3');">
						    	<div class="f_l 1check_box_2">
							    	<div class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l" >
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		自定义
							    	</div>
								</div>
							</li>
						</ul>
					</div>
					<div id="sp_z0" onclick="findItem()"
					class="w140 h38 lh38  b_radius5 bgc_00a0e9 c_fff text-center m_l18 f_l  cursor_ AddSpecified dj_2">
					选择指定商品</div>
				</div>
			</div>
			<div class="m_b20">
				<div class="w80 text-right c_384856 f_l font14 m_r30 h50 lh50">
					分组名称:
				</div>
				<input id="groupName" class="group_name w500 h50 lh50 border0 bgc_f4f6fa b_radius5" />
				
			</div>
			
			<div>
				<div class="w80 text-right c_384856 f_l font14 m_r30 h50 lh50">
					说明:
				</div>
				<input id="remarks" class="explain w500 h50 lh50 border0 bgc_f4f6fa b_radius5" />
				
			</div>
			
			<!--保存取消按钮-->
			<div class="w232 h42 margin0_auto m_t40">
				<div class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_" onclick="addMemberGroup();">保存</div>
				<div class="m_l30 close f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8">取消</div>
			</div>
		</div>
	
						
		
		<!--选择所在地弹出框start-->
        <div id="dqtk" class="rgba_000_5 w100_ area_check_window h100_ position_fixed top0 z_13 display_none" style="z-index:51;">
            <div class="w1325 p_b33 bgc_fff m_t100 m_l300 fasf">
                <div class="h50 p_t20 text-center font16">选择所在地区</div>
                <div class="font14 c_384856 h50 lh50 p_l50">
	                
	                
	                <div class="f_l w90 1check_box all_check">
				    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
				    	</div>
				    	<div class="m_l10 cursor_ f_l font14 c_384856">
				    		全选
				    	</div>
					</div>

	                <!-- 
	                <div class="f_l w90 1check_box all_notcheck">
				    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
				    	</div>
				    	<div class="m_l10 cursor_ f_l font14 c_384856">
				    		全不选
				    	</div>
					</div> -->
					<div class="f_l 1check_box far_notcheck">
				    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
				    	</div>
				    	<div class="m_l10 cursor_ f_l font14 c_384856">
				    		排除边远山区
				    	</div>
					</div>
	            </div>
				<div class="font14 c_384856 p_l50 place_check">
					<!--华东-->
					<ul class="h50 gangaotai_ul">
						<li class="f_l 1check_box w165 gangaotai">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">华东</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">上海市</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">浙江省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">江苏省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">安徽省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">江西省</div>
						</li>
					</ul>
					
					<!--华北-->
					<ul class="h50 gangaotai_ul">
						<li class="f_l 1check_box w165 gangaotai">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">华北</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">北京市</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">天津市</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">河北省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">山西省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">山东省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">内蒙古自治区</div>
						</li>
					</ul>
					
					<!--华中-->
					<ul class="h50 gangaotai_ul">
						<li class="f_l 1check_box w165 gangaotai">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">华中</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">湖北省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">湖南省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">河南省</div>
						</li>
					</ul>
					
					<!--华南-->
					<ul class="h50 gangaotai_ul">
						<li class="f_l 1check_box w165 gangaotai">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">华南</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">广东省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">海南省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">福建省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">广西壮族自治区</div>
						</li>
					</ul>
					
					<!--东北-->
					<ul class="h50 gangaotai_ul">
						<li class="f_l 1check_box w165 gangaotai">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">东北</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">辽宁省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">吉林省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">黑龙江省</div>
						</li>
					</ul>
					
					<!--西北-->
					<ul class="h50 gangaotai_ul">
						<li class="f_l 1check_box w165 gangaotai">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">西北</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">陕西省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">青海省</div>
						</li>
						<li class="f_l 1check_box w200 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">新疆维吾尔族自治区</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">甘肃省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">宁夏回族自治区</div>
						</li>
					</ul>
					
					<!--西南-->
					<ul class="h50 gangaotai_ul">
						<li class="f_l 1check_box w165 gangaotai">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">西南</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">四川省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">云南省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">西藏自治区</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">贵州省</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">重庆市</div>
						</li>
					</ul>
					
					<!--港澳台-->
					<ul class="h50 gangaotai_ul">
						<li class="f_l 1check_box w165 gangaotai">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">港澳台</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">香港特别行政区</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">澳门特别行政区</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">台湾省</div>
						</li>
					</ul>
					
					<!--海外-->
					<ul class="h50 gangaotai_ul">
						<li class="f_l 1check_box w165 gangaotai">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">海外</div>
						</li>
						<li class="f_l 1check_box w165 li_">
					    	<div class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
					    	</div>
					    	<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">海外</div>
						</li>
					</ul>
					
				</div>
				
				
				<div class="h40 margin0_auto w250">
					<input class="w100 bgc_fff border_00a0e9 c_00a0e9 tk b_radius5 h40 font16 close_1" type="submit" value="确定" />
				
					<input class="w100 bgc_fff border_00a0e9 c_00a0e9 tk b_radius5 h40 font16 close_1 m_l20" type="submit" value="取消" />
				</div>
				
            </div>
        </div>
		
		
	<!---------------修改分组弹窗--------------->
	<div class="w100_ h100_ rgba_000_5 position_fixed z_11 top0 update display_none">
							
		<div class=" bgc_fff margin0_auto m_t100 p_t20 p_l20 p_r20 p_b20 w1240">
			
				
			<!----------标题---------->
			<div class="c_384856 font18 text-center m_b55 m_t35">
				修改分组
			</div>
			
			<!----------筛选---------->
			<div>
				<!----------块1---------->
				<div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22"  style="width: 5.3vw">
						最近交易:
					</div>
					<input id="sgId" type="hidden" <%-- value="${sellerGroup.groupId} "--%>>
					<input id="sgrId" type="hidden" <%-- value="${sellerGroupRule.id} "--%>>
					<input id="memberTypeId" type="hidden">
					<div class="f_l font16 h22">
						<!----------选择区域1---------->
						<ul id="zjjy" class="area_check">
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="tradeStatusbx" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div  class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		不限
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
								
								
						    	<div class="f_l 1check_box_2">
							    	<div id="jqt" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		近7天
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
								
								
						    	<div class="f_l 1check_box_2">
							    	<div id="jygy" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		近1个月
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
								
								
						    	<div class="f_l 1check_box_2">
							    	<div id="jsgy" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		近3个月
							    	</div>
								</div>
								
							</li>
							<li class="h22 lh22 f_l">
						    	<div class="f_l 1check_box_2">
							    	<div id="jbn" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		近半年
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l date_set zdy">
						    	<div class="f_l 1check_box_2">
							    	<div id="tradezdy" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		自定义
							    	</div>
								</div>
							</li>
							
							<li id="tradeTimeInput" class="f_l m_t_15 display_none zdy_1" style="padding-top: 0.6vw;">	
								<input id="tradeTime1" type="text" class="f_l h50" style="margin-right:1vw;width: 6vw;height: 1.5vw;" onkeyup="value=value.replace(/[^\d]/g,'')" />
								<div class="wrap f_l">
								    <div class="nice-select h50" style="width: 3vw;height: 1.5vw;" name="nice-select">
								    	<input id="tradeTimeType1"  type="text" style="height: 1.5vw;" readonly value="天" />
								    	<ul style="z-index:2;">
								    		<li value="1" >天</li>
								    		<li value="2" >月</li>
								    		<li value="3" >年</li>
								    	</ul>
								  	</div>
								</div>
								<div class="clear"></div>
							</li>
<!-- 							<li id="tradeTimeInput" class="f_l m_t_15 display_none zdy_1"> -->
<!-- 								<div class="f_l position_relative"> -->
<%-- 									<input  value="${sellerGroupRule.minTradeTime}" class="bgc_f4f6fa border0 w120 p_l10 h50 m_r10" type="text" id="tser99"  --%>
<!-- 									 readonly onclick="$.jeDate('#tser99',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})"> -->
<%-- 									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" /> --%>
<!-- 								</div> -->
									
<!-- 								<div class="f_l h50 lh50">~</div> -->
								
<!-- 								<div class="f_l position_relative"> -->
<%-- 									<input value="${sellerGroupRule.maxTradeTime}" class="bgc_f4f6fa border0 w120 p_l10 h50 m_l10" type="text" id="tser100"   --%>
<!-- 									readonly onclick="$.jeDate('#tser100',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})"> -->
<%-- 									<img class="position_absolute right15 top15" src="${ctx}/crm/images/date_copy.png" /> --%>
<!-- 								</div> -->
<!-- 							</li> -->
						</ul>
					</div>
				</div>
				
				<!----------块2---------->
				<div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22"  style="width: 5.3vw">
						交易次数:
					</div>
					<div class="f_l font16 h22">
						<!----------选择区域1---------->
						<ul id="jycs" class="time_check">
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="tradeNum0" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		不限
							    	</div>
								</div>
							</li>
							
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="tradeNum1"  class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		1次
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="tradeNum2" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		2次
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="tradeNum3" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		3次
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="tradeNum4" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		3次以上
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l time_set zdy">
						    	<div class="f_l 1check_box_2">
							    	<div id="tradeNum5" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		自定义
							    	</div>
								</div>
							</li>
							
							<li id="tradeNumInput" class="f_l m_t_15 display_none zdy_1">
								<input id="tradeNum01" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_r10" type="text"
								value="${sellerGroupRule.minTradeNum}" onblur="zhengze('tradeNum01','tradeNum02')" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
								
									
								<div class="f_l h50 lh50">~</div>
								
								<input id="tradeNum02" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_l10" type="text"
								value="${sellerGroupRule.maxTradeNum}" onblur="zhengze('tradeNum01','tradeNum02')" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
								
							</li>
							
							
							
						</ul>
					</div>
				</div>
				
				<!----------块3---------->
				<div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22"  style="width: 5.3vw">
						累计金额:
					</div>
					<div class="f_l font16 h22">
						<!----------选择区域1---------->
						<ul id="ljje" class="time_check">
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="lj0" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		不限
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="lj1" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		1~100元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="lj2" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		100~200元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="lj3" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		200~300元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l time_set zdy">
						    	<div class="f_l 1check_box_2">
							    	<div id="lj4" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		自定义
							    	</div>
								</div>
							</li>
							
							<li id="ljInput"  class="f_l m_t_15 display_none zdy_1">
								<input id="ljje01" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_r10" onblur="zhengze('ljje01','ljje02')" type="text" value="${sellerGroupRule.minAccumulatedAmount }" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
								
									
								<div class="f_l h50 lh50">
									~
								</div>
								
								<input id="ljje02" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_l10" onblur="zhengze('ljje01','ljje02')" type="text" value="${sellerGroupRule.maxAccumulatedAmount }" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
								
							</li>
							
							
							
						</ul>
					</div>
				</div>
				
				<!----------块4---------->
				<div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22"  style="width: 5.3vw">
						 平均订单金额:
					</div>
					<div class="f_l font16 h22">
						<!----------选择区域1---------->
						<ul id="pjkdj">
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="avgPrice0" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		不限
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="avgPrice1" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		1~100元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="avgPrice2" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		100~200元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="avgPrice3" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		200~300元
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l time_set zdy">
						    	<div class="f_l 1check_box_2">
							    	<div id="avgPrice4" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		自定义
							    	</div>
								</div>
							</li>
							
							<li id="avgPriceInput" class="f_l m_t_15 display_none zdy_1">
								<input id="pjkdj01" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_r10" type="text"
								value="${sellerGroupRule.minAveragePrice}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onblur="zhengze('pjkdj01','pjkdj02')">
								<div class="f_l h50 lh50">
									~
								</div>
								<input id="pjkdj02" class="f_l bgc_f4f6fa border0 w120 p_l10 h50 m_l10" type="text" 
								value="${sellerGroupRule.maxAveragePrice}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onblur="zhengze('pjkdj01','pjkdj02')">
							</li>
						</ul>
					</div>
				</div>
				
				<!----------块5---------->
				<!-- <div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22">
						会员等级:
					</div>
					<div class="f_l font16 h22">
						--------选择区域1--------
						<ul id="hydj">
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="hy" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    		<input type="hidden" value="">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		所有会员
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_3" onclick="removeClass();">
							    	<div id="hy0" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    		<input type="hidden" value="0">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		店铺会员
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_3" onclick="removeClass();">
							    	<div id="hy1" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    		<input type="hidden" value="1">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		普通会员
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_3" onclick="removeClass();">
							    	<div id="hy2" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    		<input type="hidden" value="2">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		高级会员
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_3" onclick="removeClass();">
							    	<div id="hy3" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    		<input type="hidden" value="3">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		VIP会员
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_3" onclick="removeClass();">
							    	<div id="hy4" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    		<input type="hidden" value="4">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		至尊VIP会员
							    	</div>
								</div>
							</li>
						</ul>
					</div>
				</div> -->
				
				<!----------块6---------->
				<div class="c_384856 h22 m_b40 check_part">
					<div class="w80 text-right f_l font14 m_r30 h22 lh22" style="width: 5.3vw">
						地区:
					</div>
					<div class="f_l font16 h22">
						<!----------选择区域1---------->
						<ul id="shengfen">
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="dq0" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		默认全部
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="dq1" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		江浙沪
							    	</div>
								</div>
							</li>
							<li class="h22 lh22 f_l">
								
						    	<div class="f_l 1check_box_2">
							    	<div id="dq2" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		偏远地区
							    	</div>
								</div>
							</li>
							
							<li class="h22 lh22 f_l time_set zdy_">
						    	<div class="f_l 1check_box_2">
							    	<div id="dq3" class="cursor_ 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							    	</div>
							    	<div class="m_l10 w90 c_384856 cursor_ f_l font14 c_384856">
							    		自定义
							    	</div>
								</div>
							</li>
							
						</ul>
					</div>
					<div id="sp_z0" onclick="findItem()"
					class="w140 h38 lh38  b_radius5 bgc_00a0e9 c_fff text-center m_l18 f_l  cursor_ AddSpecified dj_2">
					选择指定商品</div>
				</div>
				
				
				
			</div>
			<div class="m_b20">
				<div class="w80 text-right c_384856 f_l font14 m_r30 h50 lh50">
					分组名称:
				</div>
				<input id="upGroupName" class="group_name w500 h50 lh50 border0 bgc_f4f6fa b_radius5"  <%-- value="${sellerGroup.groupName}" --%>/>
				
			</div>
			
			<div>
				<div class="w80 text-right c_384856 f_l font14 m_r30 h50 lh50">
					说明:
				</div>
				<input id="upRemark" class="explain w500 h50 lh50 border0 bgc_f4f6fa b_radius5" <%-- value="${sellerGroup.remark}" --%>/>
				
			</div>
			
			<!--保存取消按钮-->
			<div class="w232 h42 margin0_auto m_t40">
				<div class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ closeSave" onclick="updateSellerGroup();">保存</div>
				<div class="m_l30 close f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8"
				>取消</div>
			</div>
		</div>
	</div>
	<!--选择商品弹出框start-->
	<div class="rgba_000_5 w1920 h100_ ChoiceSpecified  display_none">
		<div class="w1000 h580 p_b33 bgc_fff m_t100 "
			style="margin-left: 24.625vw;">
			<div class="w936 h546 margin0_auto bgc_fff">
				<p class="text-center p_t20 p_b40 font16">选择商品</p>
				<input id="appoint_ItemId" type="hidden" />
				<form class="font14" action="#">
					<p class="f_l">
						商品ID:<input onkeyup='this.value=this.value.replace(/\D/gi,"")' id="itemId_input"
							class="h50 w240 border0 outline_none b_radius5 m_l15 m_r20 bgc_f4f6fa" />
					</p>
					<p class="f_l">
						关键字:<input  id="itemName_input"
							class="h50 w240 border0 outline_none b_radius5 m_l15 m_r18 bgc_f4f6fa" />
					</p>
					<p class="f_l">
					<div id="putaway_div"
						class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 zhixianshishangjia ">
					</div>
					<input id="status_input" type="hidden" value="2" />
					<div class="m_l10 f_l font14 c_384856 lh50">
						只显示上架<input onclick="findItemByData(this);"
							class="h40 w80 border0 outline_none b_radius5 m_l35 bgc_00a0e9 c_fff cursor_"
							type="button" value="搜索" />
					</div>
					</p>
				</form>
				<div class="clear"></div>
				<div class="h300 overflow_auto">
					<table border="0" class="font14 m_t13 item_table">
						<tr class="bgc_e3e7f0 item_tr">
							<!-- <th class="w80 h60 text-center">全选<div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 fuxuankuang bgc_check_blue' ></div></th> -->
			<th class="w80 h60 text-center" style="width:8vw;">
             <div class="f_l 1check_box ">
					<div
						class="cursor_ m_t10 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l VIPGXK fuxuankuang " style='margin-left:1.4vw;'>
					</div>
					<div class="m_l20 cursor_ f_l font18 c_384856" style="margin-top:0.3vw;"> 全选</div>
				</div>                           
            </th>
							<th style="width: 8vw;" class=" h60 text-center">图片</th>
							<th style="width: 35vw;" class=" h60 text-center">宝贝名称</th>
							<th style="width: 8vw;" class=" h60 text-center">金额</th>
						</tr>
					</table>
				</div>

				<!--确定保存start-->

				<div class="w936 h42 m_t50 margin0_auto">
					<div class="w214 margin0_auto">
						<div onclick="addItemId();"
							class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ SpecifiedOut  Qita">
							确定</div>
						<div
							class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ SpecifiedOut">
							取消</div>
					</div>
				</div>

				<!--确定保存end-->
			</div>


		</div>
	</div>	
</body>
<script type="text/javascript">

//判断分组类型，选中勾选框
$(function() {
<%-- 	var memberType="<%=request.getAttribute("memberType")%>"; --%>
//	if(memberType==1){	
//		$("#moren").addClass("bgc_check_blue");		
//	}else if(memberType==2){	
//		$("#niming").addClass("bgc_check_blue");		
//	}else{	
//		$("#quanbufenzu").addClass("bgc_check_blue");		
//	}
//	
	var bombBoxHeight=$('.bombBox').height();
	$('.bombBox').css({
		'margin-top':-(bombBoxHeight/2)
	});
//	darg('bombBox');
//	
//
});

function darg(id){
	var oBox = document.getElementById(id);
	
	var disX = 0;//鼠标在div中的位置
	var disY = 0;//鼠标在div中的位置
	oBox.onmousedown=function(ev){
		$('#'+id).css({
			'margin-left':0,
			'margin-top':0,
			left:'10%',
			top:'10%'
		});
		var oEvent = ev||event;
		disX = oEvent.clientX-oBox.offsetLeft;
		disY = oEvent.clientY-oBox.offsetTop;
		document.onmousemove=function(ev){
			//改变div的位置
			var oEvent = ev||event;
			var l = oEvent.clientX-disX;
			var t = oEvent.clientY-disY;
			if(l<0){
				l=0;
			}else if(l>=document.documentElement.clientWidth-oBox.offsetWidth){
				l=document.documentElement.clientWidth-oBox.offsetWidth;
			}
			if(t<0){
				t=0;
			}else if(t>=document.documentElement.clientHeight-oBox.offsetHeight){
				t=document.documentElement.clientHeight-oBox.offsetHeight;
			}
			oBox.style.left = l+'px';
			oBox.style.top = t+'px';
		};
		document.onmouseup=function(){
			document.onmousemove=null;
			document.onmouseup=null;
			oBox.releaseCapture&&oBox.releaseCapture();
		};

		oBox.setCapture&&oBox.setCapture();
		
	};
}


//根据分组类型查询
//function findSellerGroup(memberType) {	
//	window.location.href = "${ctx}/sellerGroup/findSellerGroup?&memberType="+memberType;
//};

//根据分组ID删除分组
 function delSellerGroup(id,ruleId){
	 if(confirm("确认删除吗")){
		}else{		  
		   return;
		  }
	/*  var status;
	 if($("#quanbufenzu").hasClass('bgc_check_blue')){
		 status ="";
	 }else if($("#moren").hasClass('bgc_check_blue')){
		 status ="1"
	 }else if($("#niming").hasClass('bgc_check_blue')){
		 status ="2"
	 } */
	 var pageNoMax = $('#pageNoMaxId').val();
	 var pageNo = $('#pageNoId').val();
	$.ajax({ 
        type: "post", 
        url: "${ctx}/sellerGroup/delSellerGroup", 
        dataType: "json", 
        data:{groupId:id,ruleId:ruleId},
        success: function (data) { 
				$(".tishi_2").show();
 				$(".tishi_2").children("p").text(data.info)
 				setTimeout(function(){ 
					$(".tishi_2").hide()
 				},3000)
        	//window.location.href = "${ctx}/sellerGroup/findSellerGroup?&memberType="+status;
 				window.location.href = "${ctx}/sellerGroup/findSellerGroup";
        }, 
        error: function () { 
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("删除数据失败！")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
        	window.location.href="${ctx}/sellerGroup/findSellerGroup?pageNo=" + pageNo;
        } 
	});	
};

//获取会员分组编号，并跳转到会员短信群发页面
function getMemberGroup(groupId,groupType){
	window.location.href="${ctx}/memberInfoController/getMemberIds?&groupId="+groupId+"&groupType="+groupType;
}

//获取最近交易状态，写入到一个input标签里
function addTradeStatus(tradeStatus){
	$("#tradeStatus").val(tradeStatus);
}

//获取交易次数条件选择
function getTradeNum(TradeNum){
	$("#tradeNum").val(TradeNum);
}

//获取累计金额条件选择
function getAccumulatedStatus(accumulatedStatus){
	$("#accumulatedStatus").val(accumulatedStatus);
}

//获取平均客单价选择条件
function getAveragePrice(averagePrice){
	$("#averagePrice").val(averagePrice);
}

//添加会员分组
function addMemberGroup(){
	var tradeStatus = $("#tradeStatus").val();//获取最近交易状态
	var tradeNum = $("#tradeNum").val();//获取交易次数条件选择
	var accumulatedStatus = $("#accumulatedStatus").val();//获取累计金额条件选择
	var averagePrice = $("#averagePrice").val();//获取平均客单价选择条件
	var minTradeNum=$("#minTradeNum").val();//获取最小交易次数
	var maxTradeNum=$("#maxTradeNum").val();//获取最大交易次数
	var minAccumulatedAmount=$("#minAccumulatedAmount").val();//获取最小累计金额
	var maxAccumulatedAmount=$("#maxAccumulatedAmount").val();//获取最大累计金额
	var minAveragePrice=$("#minAveragePrice").val();//获取最小平均客单价
	var maxAveragePrice=$("#maxAveragePrice").val();//获取最大平均客单价
	var groupName=$("#groupName").val();//获取分组名称
	var remarks=$("#remarks").val();//获取说明
	var province;//获取选择的地区
	var pageNoMax = $('#pageNoMaxId').val();//获取最后一页，实现添加成功后停留在最后一页
	var itemIds = $("#appoint_ItemId").val();
	
	//获取选中的地区
	var provinceStatus=$("#provinceStatus").val();
	if(provinceStatus==0){
		province=null;
	}else if(provinceStatus==1){
		province="上海市,浙江省,江苏省";
	}else if(provinceStatus==2){
		province="新疆维吾尔自治区,云南省,青海省,西藏自治区";
	}else if(provinceStatus==3){
		var getprovince=$(".place_check").children().find('.li_').children('.bgc_check_blue').next();
		for(var i=0;i<getprovince.length;i++){
			if(province!=null&&province!=""){
				province=province+","+getprovince[i].innerText;
			}else{
				province=getprovince[i].innerText;
			}
		}
	}
	//获取最近交易时间
	var tradeDays,tradeType;
	if(tradeStatus=="自定义"){
		tradeDays = $("#tradeTime").val();
		tradeType = $("#tradeTimeType").val();
	}else if(tradeStatus=="近7天"){
		tradeDays =7;
		tradeType="天";
	}else if(tradeStatus=="近1个月"){
		tradeDays =1;
		tradeType="月";
	}else if(tradeStatus=="近3个月"){
		tradeDays =3;
		tradeType="月";
	}else if(tradeStatus=="近半年"){
		tradeDays =6;
		tradeType="月";
	}else{
		tradeDays = null;
		tradeType = null;
	}
	if(tradeNum==""|| tradeNum==null){
		if(minTradeNum==null||minTradeNum==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最小交易次数！")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			return false;
		}else if(maxTradeNum==null || maxTradeNum==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最大交易次数！")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			return false;
		}else if(parseFloat(maxTradeNum) < parseFloat(minTradeNum)){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("最小交易次数不能大于最大交易次数")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000);
			return false;
		}
	}
	
	if(accumulatedStatus=="自定义"){
		if(minAccumulatedAmount==null||minAccumulatedAmount==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最小累计金额！")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			return false;
		}else if(maxAccumulatedAmount==null || maxAccumulatedAmount==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最大累计金额！")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			return false;
		}else if(parseFloat(minAccumulatedAmount) > parseFloat(maxAccumulatedAmount)){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("最小累计金额不能大于最大累计金额")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000);
			return false;
		} 
	}
	
	if(averagePrice=="自定义"){
		if(minAveragePrice==null||minAveragePrice==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最小平均客单价！")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			return false;
		}else if(maxAveragePrice==null || maxAveragePrice==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最大平均客单价！")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			return false;
		}else if(parseFloat(minAveragePrice) > parseFloat(maxAveragePrice)){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("最小平均客单价不能大于最大平均客单价")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000);
			return false;
		} 
	}
	
// 	if(!$('#remarks').val()){
// 		$(".tishi_2").show();
// 		$(".tishi_2").children("p").text("说明不能为空，请填写！")
// 		setTimeout(function(){ 
// 			$(".tishi_2").hide()
// 		},3000);
// 		return false;
// 	}
    if(groupName==null||groupName==""){
		$(".tishi_2").show();
		$(".tishi_2").children("p").text("请输入分组名称！")
		setTimeout(function(){ 
			$(".tishi_2").hide()
		},3000)
		return false;
	}
    
    $(".add_group").hide();
	$.ajax({ 
        type: "post", 
        url: "${ctx}/sellerGroup/addSellerGroup", 
        dataType: "json", 
        data:{
        	tradeStatus:tradeStatus,
        	tradeNum:tradeNum,
        	accumulatedStatus:accumulatedStatus,
        	averagePrice:averagePrice,
        	tradeStatus:tradeStatus,
        	tradeDays:tradeDays,
        	tradeType:tradeType,
        	minTradeNum:minTradeNum,
        	maxTradeNum:maxTradeNum,
        	minAccumulatedAmount:minAccumulatedAmount,
        	maxAccumulatedAmount:maxAccumulatedAmount,
        	minAveragePrice:minAveragePrice,
        	maxAveragePrice:maxAveragePrice,
        	groupName:groupName,
        	remarks:remarks,
        	province:province,
        	itemIds:itemIds
        	},
        success: function (data) { 
			if(data.info=='会员分组名称重复!'){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text(data.info);
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},2000);
	        	$(".add_group").hide();
	        	return;
			}else{
				$(".tishi_2").show();
				$(".tishi_2").children("p").text(data.info);
				setTimeout(function(){ 
	        		window.location.href="${ctx}/sellerGroup/findSellerGroup?pageNo=" + pageNoMax;
				},1000);
			}
			
        	
// 			setTimeout("window.location.reload(http://www.baidu.com)",1000);
//         	window.location.href="${ctx}/sellerGroup/findSellerGroup?pageNo=" + pageNoMax;
        }, 
        error: function () { 
    		$(".tishi_2").show();
    		$(".tishi_2").children("p").text("添加数据失败！");
    		setTimeout(function(){ 
    			$(".tishi_2").hide()
    			window.location.href="${ctx}/sellerGroup/findSellerGroup";
    		},3000);
        } 
	});	
}

//获取地区的选中状态
function getProvinceStatus(provinceStatus){

	$("#provinceStatus").val(provinceStatus);	
}

//跳转到修改分组controller控制器
function updateMemberGroup(groupId){
	/* window.location.href="${ctx}/sellerGroup/updateMemberGroup?&groupId="+groupId; */
	var url = "${ctx}/sellerGroup/updateMemberGroup";
	$.post(url,{"groupId":groupId},function(data){
		console.log(data);
		if(data.message){
			$('#sgrId').val(data.sgrId);
			$('#sgId').val(data.sgGroupId);
			$('#upGroupName').val(data.sgGroupName);
			$('#upRemark').val(data.sgRemark);
			$('#memberTypeId').val(data.memberType);
			
			//获取最近交易选择状态，添加上样式
			var tradeTimeStatus=data.sgrTradeTimeStatus;
			$("#jqt").removeClass("bgc_check_blue");
			$("#jygy").removeClass("bgc_check_blue");
			$("#jsgy").removeClass("bgc_check_blue");
			$("#jbn").removeClass("bgc_check_blue");
			$("#tradezdy").removeClass("bgc_check_blue");
			$("#tradeStatusbx").removeClass("bgc_check_blue");
			
			if(tradeTimeStatus!=null&&tradeTimeStatus=="近7天"){
				$("#jqt").addClass("bgc_check_blue");
				$("#jygy").removeClass("bgc_check_blue");
				$("#jsgy").removeClass("bgc_check_blue");
				$("#jbn").removeClass("bgc_check_blue");
				$("#tradezdy").removeClass("bgc_check_blue");
				$("#tradeStatusbx").removeClass("bgc_check_blue");
				$("#tradeTimeInput").hide();
				$("#tradeTimeType1").val("天");
				$("#tradeTime1").val(null);
			}else if(tradeTimeStatus!=null&&tradeTimeStatus=="近1个月"){
				$("#jygy").addClass("bgc_check_blue");
				$("#jqt").removeClass("bgc_check_blue");
				$("#jsgy").removeClass("bgc_check_blue");
				$("#jbn").removeClass("bgc_check_blue");
				$("#tradezdy").removeClass("bgc_check_blue");
				$("#tradeStatusbx").removeClass("bgc_check_blue");
				$("#tradeTimeInput").hide();
				$("#tradeTimeType1").val("天");
				$("#tradeTime1").val(null);
			}else if(tradeTimeStatus!=null&&tradeTimeStatus=="近3个月"){
				$("#jsgy").addClass("bgc_check_blue");
				$("#jqt").removeClass("bgc_check_blue");
				$("#jygy").removeClass("bgc_check_blue");
				$("#jbn").removeClass("bgc_check_blue");
				$("#tradezdy").removeClass("bgc_check_blue");
				$("#tradeStatusbx").removeClass("bgc_check_blue");
				$("#tradeTimeInput").hide();
				$("#tradeTimeType1").val("天");
				$("#tradeTime1").val(null);
			}else if(tradeTimeStatus!=null&&tradeTimeStatus=="近半年"){
				$("#jbn").addClass("bgc_check_blue");
				$("#jqt").removeClass("bgc_check_blue");
				$("#jygy").removeClass("bgc_check_blue");
				$("#jsgy").removeClass("bgc_check_blue");
				$("#tradezdy").removeClass("bgc_check_blue");
				$("#tradeStatusbx").removeClass("bgc_check_blue");
				$("#tradeTimeInput").hide();
				$("#tradeTimeType1").val("天");
				$("#tradeTime1").val(null);
			}else if(tradeTimeStatus!=null&&tradeTimeStatus=="自定义"){
				$("#tradezdy").addClass("bgc_check_blue");
				$("#tradeTimeType1").val(data.tradeType);
				$("#tradeTime1").val(data.tradeDays);
				$("#tradeTimeInput").show();
				$("#tradeStatusbx").removeClass("bgc_check_blue");
				$("#jqt").removeClass("bgc_check_blue");
				$("#jygy").removeClass("bgc_check_blue");
				$("#jsgy").removeClass("bgc_check_blue");
				$("#jbn").removeClass("bgc_check_blue");
			}else{
				$("#tradeStatusbx").addClass("bgc_check_blue");
				$("#jqt").removeClass("bgc_check_blue");
				$("#jygy").removeClass("bgc_check_blue");
				$("#jsgy").removeClass("bgc_check_blue");
				$("#jbn").removeClass("bgc_check_blue");
				$("#tradezdy").removeClass("bgc_check_blue");
				$("#tradeTimeInput").hide();
				$("#tradeTimeType1").val("天");
				$("#tradeTime1").val(null);
			}
			
			//获取交易次数并添加选中样式
			var maxTradeNum=data.sgrMaxTradeNum;
// 			alert(maxTradeNum);
			var minTradeNum = data.sgrMinTradeNum;
// 			alert(minTradeNum);
			if(maxTradeNum==1&&minTradeNum==1){
				$("#tradeNum1").addClass("bgc_check_blue");
				$("#tradeNum2").removeClass("bgc_check_blue");
				$("#tradeNum3").removeClass("bgc_check_blue");
				$("#tradeNum4").removeClass("bgc_check_blue");
				$("#tradeNum0").removeClass("bgc_check_blue");
				$("#tradeNum5").removeClass("bgc_check_blue");
			}else if(maxTradeNum==2&&minTradeNum==2){
				$("#tradeNum2").addClass("bgc_check_blue");
				$("#tradeNum1").removeClass("bgc_check_blue");
				$("#tradeNum3").removeClass("bgc_check_blue");
				$("#tradeNum4").removeClass("bgc_check_blue");
				$("#tradeNum0").removeClass("bgc_check_blue");
				$("#tradeNum5").removeClass("bgc_check_blue");
			}else if(maxTradeNum==3&&minTradeNum==3){
				$("#tradeNum3").addClass("bgc_check_blue");
				$("#tradeNum2").removeClass("bgc_check_blue");
				$("#tradeNum1").removeClass("bgc_check_blue");
				$("#tradeNum4").removeClass("bgc_check_blue");
				$("#tradeNum0").removeClass("bgc_check_blue");
				$("#tradeNum5").removeClass("bgc_check_blue");
			}else if(maxTradeNum==null&&minTradeNum==4){
				$("#tradeNum4").addClass("bgc_check_blue");
				$("#tradeNum2").removeClass("bgc_check_blue");
				$("#tradeNum1").removeClass("bgc_check_blue");
				$("#tradeNum3").removeClass("bgc_check_blue");
				$("#tradeNum0").removeClass("bgc_check_blue");
				$("#tradeNum5").removeClass("bgc_check_blue");
			}/* else if(maxTradeNum==4&&minTradeNum==4){
			/* else if(maxTradeNum==null&&minTradeNum==4){ 
				$("#tradeNum4").addClass("bgc_check_blue");
				$("#tradeNum1").removeClass("bgc_check_blue");
				$("#tradeNum2").removeClass("bgc_check_blue");
				$("#tradeNum3").removeClass("bgc_check_blue");
				$("#tradeNum0").removeClass("bgc_check_blue");
				$("#tradeNum5").removeClass("bgc_check_blue");
			} */else if(maxTradeNum ==0||maxTradeNum==null){
				$("#tradeNum0").addClass("bgc_check_blue");
				$("#tradeNum4").removeClass("bgc_check_blue");
				$("#tradeNum1").removeClass("bgc_check_blue");
				$("#tradeNum2").removeClass("bgc_check_blue");
				$("#tradeNum3").removeClass("bgc_check_blue");
				$("#tradeNum5").removeClass("bgc_check_blue");
			}else{
				$("#tradeNum5").addClass("bgc_check_blue");
				$("#tradeNum01").val(data.sgrMinTradeNum);
				$("#tradeNum02").val(data.sgrMaxTradeNum);
				$("#tradeNumInput").show();
				$("#tradeNum4").removeClass("bgc_check_blue");
				$("#tradeNum1").removeClass("bgc_check_blue");
				$("#tradeNum2").removeClass("bgc_check_blue");
				$("#tradeNum3").removeClass("bgc_check_blue");
				$("#tradeNum0").removeClass("bgc_check_blue");
			}
			
			//获取累计金额并添加选中样式
			var maxAccumulatedAmount=data.sgrMaxAccumulatedAmount;
			var minAccumulatedAmount = data.sgrMinAccumulatedAmount;
			if(maxAccumulatedAmount==null||maxAccumulatedAmount==""){
				$("#lj0").addClass("bgc_check_blue");
				$("#lj1").removeClass("bgc_check_blue");
				$("#lj2").removeClass("bgc_check_blue");
				$("#lj3").removeClass("bgc_check_blue");
				$("#lj4").removeClass("bgc_check_blue");
			}else if(maxAccumulatedAmount==100&&minAccumulatedAmount==1){
				$("#lj1").addClass("bgc_check_blue");
				$("#lj0").removeClass("bgc_check_blue");
				$("#lj2").removeClass("bgc_check_blue");
				$("#lj3").removeClass("bgc_check_blue");
				$("#lj4").removeClass("bgc_check_blue");
			}else if(maxAccumulatedAmount==200&&minAccumulatedAmount==100){
				$("#lj2").addClass("bgc_check_blue");
				$("#lj1").removeClass("bgc_check_blue");
				$("#lj0").removeClass("bgc_check_blue");
				$("#lj3").removeClass("bgc_check_blue");
				$("#lj4").removeClass("bgc_check_blue");
			}else if(maxAccumulatedAmount==300&&minAccumulatedAmount==200){
				$("#lj3").addClass("bgc_check_blue");
				$("#lj1").removeClass("bgc_check_blue");
				$("#lj2").removeClass("bgc_check_blue");
				$("#lj0").removeClass("bgc_check_blue");
				$("#lj4").removeClass("bgc_check_blue");
			}else{
				$("#lj4").addClass("bgc_check_blue");
				$("#ljje01").val(data.sgrMinAccumulatedAmount);
				$("#ljje02").val(data.sgrMaxAccumulatedAmount);
				$("#ljInput").show();
				$("#lj1").removeClass("bgc_check_blue");
				$("#lj2").removeClass("bgc_check_blue");
				$("#lj3").removeClass("bgc_check_blue");
				$("#lj0").removeClass("bgc_check_blue");
			}
			
			//获取平均客单价并添加选中样式
			var maxAveragePrice=data.sgrMaxAveragePrice;
			var minAveragePrice = data.sgrMinAveragePrice;
			if(maxAveragePrice==null||maxAveragePrice==""){
				$("#avgPrice0").addClass("bgc_check_blue");
				$("#avgPrice1").removeClass("bgc_check_blue");
				$("#avgPrice2").removeClass("bgc_check_blue");
				$("#avgPrice3").removeClass("bgc_check_blue");
				$("#avgPrice4").removeClass("bgc_check_blue");
			}else if(maxAveragePrice==100&&minAveragePrice==1){
				$("#avgPrice1").addClass("bgc_check_blue");
				$("#avgPrice0").removeClass("bgc_check_blue");
				$("#avgPrice2").removeClass("bgc_check_blue");
				$("#avgPrice3").removeClass("bgc_check_blue");
				$("#avgPrice4").removeClass("bgc_check_blue");
			}else if(maxAveragePrice==200&&minAveragePrice==100){
				$("#avgPrice2").addClass("bgc_check_blue");
				$("#avgPrice1").removeClass("bgc_check_blue");
				$("#avgPrice0").removeClass("bgc_check_blue");
				$("#avgPrice3").removeClass("bgc_check_blue");
				$("#avgPrice4").removeClass("bgc_check_blue");
			}else if(maxAveragePrice==300&&minAveragePrice==200){
				$("#avgPrice3").addClass("bgc_check_blue");
				$("#avgPrice1").removeClass("bgc_check_blue");
				$("#avgPrice2").removeClass("bgc_check_blue");
				$("#avgPrice0").removeClass("bgc_check_blue");
				$("#avgPrice4").removeClass("bgc_check_blue");
			}else{
				$("#avgPrice4").addClass("bgc_check_blue");
				$("#pjkdj01").val(data.sgrMinAveragePrice);
				$("#pjkdj02").val(data.sgrMaxAveragePrice);
				$("#avgPriceInput").show();
				$("#avgPrice1").removeClass("bgc_check_blue");
				$("#avgPrice2").removeClass("bgc_check_blue");
				$("#avgPrice3").removeClass("bgc_check_blue");
				$("#avgPrice0").removeClass("bgc_check_blue");
			}
			
			//获取地区并添加选中样式
			var province =data.sgrProvince;
			if(province==null||province==""){
				$("#dq0").addClass("bgc_check_blue");
				$("#dq1").removeClass("bgc_check_blue");
				$("#dq2").removeClass("bgc_check_blue");
				$("#dq3").removeClass("bgc_check_blue");
			}else if(province=="上海市,浙江省,江苏省"){
				$("#dq1").addClass("bgc_check_blue");
				$("#dq0").removeClass("bgc_check_blue");
				$("#dq2").removeClass("bgc_check_blue");
				$("#dq3").removeClass("bgc_check_blue");
			}else if(province=="新疆维吾尔自治区,云南省,青海省,西藏自治区"){
				$("#dq2").addClass("bgc_check_blue");
				$("#dq1").removeClass("bgc_check_blue");
				$("#dq0").removeClass("bgc_check_blue");
				$("#dq3").removeClass("bgc_check_blue");
			}else{
				$("#dq3").addClass("bgc_check_blue");
				$("#dq1").removeClass("bgc_check_blue");
				$("#dq2").removeClass("bgc_check_blue");
				$("#dq0").removeClass("bgc_check_blue");
				var provinceList=province.split(",");
				var getprovince=$(".li_");
				var content=$(".li_").find(".c_384856");
				var clas=$(".li_").find(".c_384856").prev();				
				for(var i=0;i<getprovince.length;i++){
					for(var j=0;j<provinceList.length;j++){
						if(content.eq(i).text()==provinceList[j]){						
							clas.eq(i).addClass("bgc_check_blue");
						}
					}
				}
			}
		}
		$("#appoint_ItemId").val(data.itemIds);
	},"json");
}

//修改用户分组
function updateSellerGroup(){
	
	//获取最近交易被勾选的
	var tradeStatus,tradeDays,tradeType;
	var zjjy=$("#zjjy").children().find('.1check_box_1');
	tradeStatus=getSelected(zjjy);	
	if(tradeStatus==5){
		tradeDays = $("#tradeTime1").val();
		tradeType = $("#tradeTimeType1").val();
		tradeStatus ="自定义";
	}else if(tradeStatus==1){
		tradeDays =7;
		tradeType="天";
		tradeStatus ="近7天";
	}else if(tradeStatus==2){
		tradeDays =1;
		tradeType="月";
		tradeStatus ="近1个月";
	}else if(tradeStatus==3){
		tradeDays =3;
		tradeType="月";
		tradeStatus ="近3个月";
	}else if(tradeStatus==4){
		tradeDays =6;
		tradeType="月";
		tradeStatus ="近半年";
	}else{
		tradeDays = null;
		tradeType = null;
		tradeStatus ="不限";
	}

	//获取交易次数
	var minTradeNum,maxTradeNum,tradeNum;
	var jycs=$("#jycs").children().find('.1check_box_1');
	var tradenum=getSelected(jycs);
	if(tradenum==4){
		minTradeNum=4;
		//maxTradeNum=null;
	}else if(tradenum==5){
		//tradeNum="自定义";
		minTradeNum=$("#tradeNum01").val();
		//交易次数 自定义 判断
		if(minTradeNum==null || minTradeNum==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最小交易次数！");
			$("#tradeNumInput").show();
			$("#tradeNum01").val("");
			$("#tradeNum02").val("");
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return false;
		}
		maxTradeNum=$("#tradeNum02").val();
		if(maxTradeNum==null || maxTradeNum==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最大交易次数！");
			$("#tradeNumInput").show();
			$("#tradeNum01").val("");
			$("#tradeNum02").val("");
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return false;
		}
	}else{
		tradeNum=tradenum;
		minTradeNum=null;
		maxTradeNum=null;
	}
	
	//获取累计金额
	var minAccumulatedAmount,maxAccumulatedAmount;
	var ljje=$("#ljje").children().find('.1check_box_1');
	var accumulated=getSelected(ljje);
	if(accumulated==1){
		minAccumulatedAmount=1;
		maxAccumulatedAmount=100;
	}else if(accumulated==2){
		minAccumulatedAmount=100;
		maxAccumulatedAmount=200;
	}else if(accumulated==3){
		minAccumulatedAmount=200;
		maxAccumulatedAmount=300;
	}else if(accumulated==4){
		minAccumulatedAmount=$("#ljje01").val();
		//累计金额自定义判断
		if(minAccumulatedAmount==null || minAccumulatedAmount==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最小累计金额！");
			$("#ljInput").show();
			$("#ljje01").val("");
			$("#ljje02").val("");
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return false;
		}
		maxAccumulatedAmount=$("#ljje02").val();
		if(maxAccumulatedAmount==null || maxAccumulatedAmount==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最大累计金额！");
			$("#ljInput").show();
			$("#ljje01").val("");
			$("#ljje02").val("");
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return false;
		}
	}else{
		minAccumulatedAmount=null;
		maxAccumulatedAmount=null;
	}
	//获取平均客单价
	var minAveragePrice,maxAveragePrice;
	var pjkdj=$("#pjkdj").children().find('.1check_box_1');
	var averagePrice=getSelected(pjkdj);
	if(averagePrice==1){
		minAveragePrice=1;
		maxAveragePrice=100;
	}else if(averagePrice==2){
		minAveragePrice=100;
		maxAveragePrice=200;
	}else if(averagePrice==3){
		minAveragePrice=200;
		maxAveragePrice=300;
	}else if(averagePrice==4){
		minAveragePrice=$("#pjkdj01").val();
		//平均客单价自定义判断
		if(minAveragePrice==null || minAveragePrice==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最小平均客单价!");
			$("#avgPriceInput").show();
			$("#pjkdj01").val("");
			$("#pjkdj02").val("");
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return false;
		}
		maxAveragePrice=$("#pjkdj02").val();
		if(maxAveragePrice==null || maxAveragePrice==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入自定义最大平均客单价!");
			$("#avgPriceInput").show();
			$("#pjkdj01").val("");
			$("#pjkdj02").val("");
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return false;
		}
	}else{
		minAveragePrice=null;
		maxAveragePrice=null;
	}
	
	//获取选中省份
	var province;
	var shengfen=$("#shengfen").children().find('.1check_box_1');
	var pvi=getSelected(shengfen);
	if(pvi==1){
		province="上海市,浙江省,江苏省";
	}else if(pvi==2){
		province="新疆维吾尔自治区,云南省,青海省,西藏自治区";
	}else if(pvi==3){
		var getprovince=$(".place_check").children().find('.li_').children('.bgc_check_blue').next();
		for(var i=0;i<getprovince.length;i++){
			if(province!=null&&province!=""){
				province=province+","+getprovince[i].innerText;
			}else{
				province=getprovince[i].innerText;
			}
		}
	}else{
		province=null;
	}
	

	//获取商品编号
	var itemIds = $("#appoint_ItemId").val();
	var groupName=$("#upGroupName").val();//获取分组名称
	var remark=$("#upRemark").val();//获取分组说明
	
	var groupId=$("#sgId").val();//获取会员分组编号
	var ruleId=$("#sgrId").val();//获取会员分组规则编号
	
	var pageNoMax = $('#pageNoMaxId').val();
	var pageNo = $('#pageNoId').val();
	
	//分组名称必填
	if(groupName==null||groupName==""){
		$(".tishi_2").show();
		$(".tishi_2").children("p").text("分组名称不可为空!")
		setTimeout(function(){ 
			$(".tishi_2").hide()
		},3000)
		return false;
	}
	
	
	$(".add_group").hide();
	$.ajax({ 
        type: "post", 
        url: "${ctx}/sellerGroup/updateSellerGroup", 
        dataType: "json", 
        data:{
        	tradeNum:tradenum,
        	tradeStatus:tradeStatus,
        	tradeDays:tradeDays,
        	tradeType:tradeType,        	
        	minTradeNum:minTradeNum,
        	maxTradeNum:maxTradeNum,
        	minAccumulatedAmount:minAccumulatedAmount,
        	maxAccumulatedAmount:maxAccumulatedAmount,
        	minAveragePrice:minAveragePrice,
        	maxAveragePrice:maxAveragePrice,
        	groupName:groupName,
        	remark:remark,
        	province:province,
        	ruleId:ruleId,
        	groupId:groupId,
        	itemIds:itemIds
        	},
        	   
        success: function (data) { 
			if(data.info==1){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text('分组名称不能重复！')
				setTimeout(function(){ 
					$(".tishi_2").hide();
					
				},2000);
				return;
			}else if(data.info==2){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text('分组名称不能为空！')
				setTimeout(function(){ 
					$(".tishi_2").hide();
					
				},2000);
				return;
			}else{
				$(".tishi_2").show();
				$(".tishi_2").children("p").text('保存成功！')
			
				setTimeout(function(){ 
					
					window.location.href="${ctx}/sellerGroup/findSellerGroup?pageNo=" + pageNo;
				},2000)
			}
			
			
        }, 
        error: function () { 
    		$(".tishi_2").show();
    		$(".tishi_2").children("p").text("修改分组信息失败！")
    		setTimeout(function(){ 
    			$(".tishi_2").hide();
    			window.location.href="${ctx}/sellerGroup/findSellerGroup?pageNo=" + pageNo;
    		},3000)
        } 
        
	});	
	
	
}

//根据判断返回第几个被选中
function getSelected(test){
	for(var i=0;i<test.length;i++){
		if(test.eq(i).hasClass("bgc_check_blue")){
			return i;
		}
	} 
}


//使用ajax查询商品数据,回显页面
function findItem(){
	$('.item_tr').siblings().remove();
	var url = "${ctx}/item/queryItem";
	$.post(url,function (data) {
			var item = data.itemList;
			var item;
			var imgUrl = null;
			if(item ==null || item=="undefined" || item=='' ){
				item = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
				$('.item_table').append(item);
			}else{
				$.each(item,function(i,result){
					if(result.url==null){
						imgUrl =imgUrl= "<img style='width:4.17vw;height:4.17vw;' src='${ctx}/crm/images/no.png'>";
					}else{
						imgUrl= "<img style='width:4.17vw;height:4.17vw;' "+"src='"+result.url+"'>";
					}
					 item = "<tr><td class='h60 text-center'><div  style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div bgc_check_blue'>"
							+"<input type='hidden' value='"+result.numIid+"'/></div></td>"
							+"<td class='h60 text-center'>"
							+imgUrl
							+"</td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>"; 
					 
					$('.item_table').append(item);
					//alert(result.name);
					/* item = "<tr><td class='h60 text-center'><div class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'><input type='hidden' value="+result.numIid+"></div></td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>";
					$('.item_table').append(item); */
				});
				checkItem();
			}
		 	var a = $(".item_table").children().find(".bgc_check_blue").length;
		 	var b = $(".item_table").children().find("tr").length*1-1;
			if( a==b ){
				$(".item_table").children().find(".fuxuankuang").addClass("bgc_check_blue");
			}else{
				$(".item_table").children().find(".fuxuankuang").removeClass("bgc_check_blue");
			};
	});
}

//使用ajax通过查询条件查询商品信息
function findItemByData(e){
	if($(e).hasClass('on'))return;
	$(e).addClass('on');

	$('.item_tr').siblings().remove();
	var itemId = $("#itemId_input").val();//获取商品ID
	var itemName = $("#itemName_input").val();//关键字(商品名称)
	var status = $("#status_input").val();//获取只显示上架
	var url = "${ctx}/item/queryItem";
	$.post(url,{"commodityId":itemId,"name":itemName,"status":status},function (data) {
			$(e).removeClass('on');

			var item = data.itemList;
			var item;
			var imgUrl = null;
			if(item ==null || item=="undefined" || item=='' ){
				item = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
				$('.item_table').append(item);
			}else{
				$.each(item,function(i,result){
					if(result.url==null){
						imgUrl =imgUrl= "<img style='width:4.17vw;height:4.17vw;' src='${ctx}/crm/images/no.png'>";
					}else{
						imgUrl= "<img style='width:4.17vw;height:4.17vw;' "+"src='"+result.url+"'>";
					}
					 item = "<tr><td class='h60 text-center'><div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div bgc_check_blue'>"
							+"<input type='hidden' value='"+result.numIid+"'/></div></td>"
							+"<td class='h60 text-center'>"
							+imgUrl
							+"</td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>"; 
					 
					$('.item_table').append(item);
					//alert(result.name);
					/* item = "<tr><td class='h60 text-center'><div class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'><input type='hidden' value="+result.numIid+"></div></td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>";
					$('.item_table').append(item); */
				});
				checkItem();
			}
	});
}

//只显示上架设置内容
$(function(){
	$("#putaway_div").click(function(){
		var status = $("#status_input").val();
		if(status==2){
			$("#status_input").val('1');
		}else{
			$("#status_input").val('2');
		}
		
	});
});

//回显已选择商品选中状态
function checkItem(){
	var itemidInput = $(".item_check_div");
	var itemIds = $("#appoint_ItemId").val();
	var itemIdList = itemIds.split(",");
	if(itemIds!=null&&itemIds!=""){
		for(var i=0;i<itemidInput.length;i++){
			itemidInput.eq(i).removeClass("bgc_check_blue");
			for(var j=0;j<itemIdList.length;j++){
				 if(itemidInput.eq(i).children().val()==itemIdList[j]){
					itemidInput.eq(i).addClass("bgc_check_blue");
				}
			} 
		}
	}
}

//当点击确定时获得选中的商品id
function addItemId(){
	var itemIds="";
	var divCheck = $(".item_check_div");
	for(var i=0;i<divCheck.length;i++){
		if(divCheck.eq(i).hasClass("bgc_check_blue")){
			 var val = divCheck.eq(i).children().val();
			 itemIds+=val+",";
		}
	}
	itemIds=itemIds.substring(0,itemIds.length-1);
	$("#appoint_ItemId").val(itemIds);
}

//正则表达式
function zhengze(value1,value2){
	var val1 = $("#"+value1).val();
	var val2 = $("#"+value2).val();
	val1 = Number(val1);
	val2 = Number(val2);
	if(val2!=null&&val2!=""&&val1>val2){
		$(".tishi_2").show();
		$(".tishi_2").children("p").text("请输入正确的数据！")
		setTimeout(function(){ 
		$(".tishi_2").hide()
		},1500)

// 		alert("请输入正确的数据！");
		$("#"+value1).val(null);
		$("#"+value2).val(null);	
	}
	
}

//更新会员分组的数量
function updateSellerGroupCount(groupId,groupType){
	var url = "${ctx}/sellerGroup/updateSellerGroupCount";
	$(".tishi_2").show();
	$(".tishi_2").children("p").text("正在更新会员数量！")
	if(groupType=="自定义分组"){
		groupType = 2;
	}else{
		groupType =1;
	}
	$.post(url,{"groupId":groupId,"groupType":groupType},function (data) {
		var obj = new Function("return" + data)();
		if(obj.groupNum!=null){
			$("#"+groupId).text(obj.groupNum);
				$(".tishi_2").children("p").text("更新成功！");
			setTimeout(function(){ 
				$(".tishi_2").hide()
				},1500)
		}
	});
}
</script>
</html>
