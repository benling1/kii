<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>店铺数据</title>
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
	<script type="text/javascript" src="${ctx}/crm/js/dianpushuju.js" ></script>
		
</head>
<body>
	
	<div class="w1903 h100_">
			<!-------------------------左部，侧边栏------------------------->
			<div class="w200 h100_ bgc_1c2b36 position_fixed top0 z_10">
				<div class="m_t80 w100_ bgc_052128 h1"></div>
				
				
				
				
				<!--------------------用户信息区-------------------->
				<div class="w100_ h224 bor_b_052128 p_t35">
					<!---------------用户头像--------------->
					<div class="b_radius50 w75 h75 margin0_auto m_b10 ">
						<img class="w75 h75 b_radius50 cursor_" src="${ctx}/crm/images/user.png" />
					</div>
					<!---------------用户ID--------------->
					<div class="font16 c_fff text-center h22 l_h22 m_b30 cursor_">
						Coco!
					</div>
					<!---------------其他信息--------------->
					<div class="text-center c_7da2bb m_b10 font12">
						<span>短信剩余:</span><span>1256条</span>
					</div>
					<div class="text-center c_7da2bb m_b10 font12">
						<span>邮件余额:</span><span>1256封</span>
					</div>
					<div class="text-center c_7da2bb m_b10 font12">
						<span>软件到期:</span><span>1256天</span>
					</div>
					
				</div>
				
				
				<!--------------------数据分析-------------------->
				<div class="w100_ h300 bor_b_052128 p_t35">
					<div class="font12 c_3d596c p_l50 m_b20">
						数据分析
					</div>
					
					<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b20">
						<div class="f_l m_r20">
							<img class="" src="${ctx}/crm/images/Customer.png" />
						</div>
						<div class="f_l cursor_">
							客户管理
						</div>
					</div>
					
					<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b20">
						<div class="f_l m_r20">
							<img class="m_l3" src="${ctx}/crm/images/order.png" />
						</div>
						<div class="f_l m_l3 cursor_">
							订单中心
						</div>
					</div>
					
					<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b20">
						<div class="f_l m_r20">
							<img class="" src="${ctx}/crm/images/marketing.png" />
						</div>
						<div class="f_l cursor_">
							营销中心
						</div>
					</div>
					
					<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b20">
						<div class="f_l m_r20">
							<img class="" src="${ctx}/crm/images/back_stage.png" />
						</div>
						<div class="f_l cursor_">
							后台管理
						</div>
					</div>
					
					<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b20">
						<div class="f_l m_r20">
							<img class="" src="${ctx}/crm/images/store.png" />
						</div>
						<div class="f_l cursor_">
							历史数据
						</div>
					</div>
					
				</div>
				
				
				<!--------------------常用功能-------------------->
				<div class="w100_ h300 p_t35">
					<div class="font12 c_3d596c p_l35 m_b20">
						常用功能
					</div>
					
					<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w140 m_b20">
						<div class="f_l m_r20">
							<img class="m_l3" src="${ctx}/crm/images/recharge.png" />
						</div>
						<div class="f_l m_l3 cursor_">
							充值
						</div>
					</div>
					
					<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w140 m_b20">
						<div class="f_l m_r20">
							<img class="" src="${ctx}/crm/images/vip_lvl.png" />
						</div>
						<div class="f_l cursor_">
							会员等级划分
						</div>
					</div>
					
					<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w140 m_b20">
						<div class="f_l m_r20">
							<img class="" src="${ctx}/crm/images/text_history.png" />
						</div>
						<div class="f_l cursor_">
							短信发送记录
						</div>
					</div>
					
				</div>
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			</div>


	
	
			<!-------------------------右部------------------------->
			<div class="w1703 m_l200">
				<!--------------------顶部导航栏-------------------->
				<div class="h40 w1703 bgc_fff position_fixed top0 z_10">
					<div class="f_l lh40">
						<img class="m_l40 m_r60 cursor_" src="${ctx}/crm/images/home.png" />
						<img class=" cursor_" src="${ctx}/crm/images/mail.png" />
					</div>
					<div class="f_r">
						<div class=" f_l m_r15">
							<img class="m_t10 cursor_" src="${ctx}/crm/images/date.png" />
						</div>
						<div class="m_r40 f_l lh40 c_7da2bb time">
							<!--<span class="m_r20">2016年5月6日</span><span class="m_r10">PM</span><span>13:20</span>-->
						</div>
						<div class="f_l m_r20">
							<img class="w32 h32 m_t3 right_top_set_btn cursor_" src="${ctx}/crm/images/set.png" />
						</div>	
					</div>
					<!--------------------顶部设置栏-------------------->
					<div class="w410 h1020 bgc_fff position_fixed top40 right0 right_top_set display_none">
						
						<div class="w100_ bor_b_e6eaef p_l30">
							
							<div class="w100_ h50 bor_b_e6eaef m_t30">
								<div class="f_l m_r10">
									<img src="${ctx}/crm/images/no_pic.png" />
								</div>
								<div class="f_l h30 lh30 c_596671 font18">
									付款提醒
								</div>
							</div>
							
							<div class="w100_ h95 bor_b_e6eaef">
								<div class="f_l">
									<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
									<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
								</div>
								
								<div class="f_l m_l60">
									<div class="f_l m_r20 lh95">
										<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
									</div>
									<div class="f_l w20 h20 lh95">
										<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
									</div>
								</div>
								
							</div>
							
							<div class="w100_ h95">
								<div class="f_l">
									<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
									<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
								</div>
								
								<div class="f_l m_l60">
									<div class="f_l m_r20 lh95">
										<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
									</div>
									<div class="f_l w20 h20 lh95">
										<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
									</div>
								</div>
								
							</div>
							
						</div>
						
						<div class="w100_ bor_b_e6eaef p_l30">
							
							<div class="w100_ h50 bor_b_e6eaef m_t30">
								<div class="f_l m_r10">
									<img src="${ctx}/crm/images/no_pic.png" />
								</div>
								<div class="f_l h30 lh30 c_596671 font18">
									付款提醒
								</div>
							</div>
							
							<div class="w100_ h95 bor_b_e6eaef">
								<div class="f_l">
									<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
									<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
								</div>
								
								<div class="f_l m_l60">
									<div class="f_l m_r20 lh95">
										<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
									</div>
									<div class="f_l w20 h20 lh95">
										<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
									</div>
								</div>
								
							</div>
							
							<div class="w100_ h95">
								<div class="f_l">
									<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
									<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
								</div>
								
								<div class="f_l m_l60">
									<div class="f_l m_r20 lh95">
										<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
									</div>
									<div class="f_l w20 h20 lh95">
										<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
									</div>
								</div>
								
							</div>
							
							<div class="w100_ h95">
								<div class="f_l">
									<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
									<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
								</div>
								
								<div class="f_l m_l60">
									<div class="f_l m_r20 lh95">
										<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
									</div>
									<div class="f_l w20 h20 lh95">
										<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
									</div>
								</div>
								
							</div>
							
							<div class="w100_ h95">
								<div class="f_l">
									<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
									<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
								</div>
								
								<div class="f_l m_l60">
									<div class="f_l m_r20 lh95">
										<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
									</div>
									<div class="f_l w20 h20 lh95">
										<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
									</div>
								</div>
								
							</div>
							
							<div class="w100_ h95">
								<div class="f_l">
									<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
									<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
								</div>
								
								<div class="f_l m_l60">
									<div class="f_l m_r20 lh95">
										<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
									</div>
									<div class="f_l w20 h20 lh95">
										<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
									</div>
								</div>
								
							</div>
							
							<div class="w100_ h95">
								<div class="f_l">
									<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
									<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
								</div>
								
								<div class="f_l m_l60">
									<div class="f_l m_r20 lh95">
										<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
									</div>
									<div class="f_l w20 h20 lh95">
										<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
									</div>
								</div>
								
							</div>
							
						</div>
						
						
						
						
						
					</div>
				</div>
				
				
				
				
				<!--------------------主要内容区-------------------->
				<div class="w1660  m_l30">
					<div class="w100_ h50 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							客户管理
						</div>
						<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
							<div class="f_l w140 text-center cursor_ bgc_e3e7f0 dpsj_out">
								订单列表
							</div>
							<div class="f_l w140 text-center cursor_ dpsj_out">
								历史订单导入
							</div>
							<div class="f_l w140 text-center cursor_ dpsj_out">
								短信发送记录
							</div>
							<div class="f_l w140 text-center cursor_ dpsj_out">
								当日数据
							</div>
							<div class="f_l w140 text-center cursor_ dpsj_out">
								操作日志
							</div>
						</div>
					</div>
					
					
					<!---------------订单列表--------------->
					<div class="dpsj_in display_none">
						<!----------上部---------->
						<div class="w1620 h100 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								当日订单列表
							</div>
							<!---------------描述--------------->
							<div class="font14">
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1620 m_b30 bgc_fff p_t35 p_l40 p_b40">
							
							<!--------查询设置-------->
							<div class="font14 c_384856 h47 lh45 m_b35">
								<div class="f_l m_r15">
									时间范围:
								</div>
								<div class="wrap f_l h45 lh45 m_r15">
								    <div class="nice-select h45 lh45" name="nice-select">
								    	<input class="h45 lh45" type="text" value="" readonly>
								    	<ul>
								    		<li>下单时间</li>
								    		<li>付款时间</li>
								      		<li>发货时间</li>
								    		<li>变更时间</li>
								    		<li>结束时间</li>
								    	</ul>
								  	</div>
								</div>
								
								
								<input class="bgc_f4f6fa border0 w250 h45 lh45 f_l m_r10" />
								<div class="f_l">~</div>
								<input class="bgc_f4f6fa border0 w250 h45 lh45 f_l m_l10 m_r300" />
								<div class="w100 h45 lh45 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
									查询
								</div>
							</div>
							
							
							<div class="h45 m_b40">
								
										<div class="h45 lh45 f_l c_384856 m_r15">
											买家昵称:
										</div>
										<input class="bgc_f4f6fa border0 w250 h45 lh45 f_l m_r40" />
										
										<div class="h45 lh45 f_l c_384856 m_r15 m_l40">
											订单编号:
										</div>
										<input class="bgc_f4f6fa border0 w250 h45 lh45 f_l m_r70" />
										<div class="h45 lh45 f_l c_00a0e9 more_check cursor_">
											更多》
										</div>
							</div>
							
							<div class="more_check_ display_none">
								<div class="h22 font14 c_384856">
									
									<div class="f_l h22 lh22 m_r10">
										订单状态：
									</div>
									
									<ul class=" h22 lh22 f_l">
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		没有创建支付宝交易
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		已下单未付款
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		卖家部分发货
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		已付款未发货
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		卖家已发货
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		买家已签收
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		交易成功
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		已退款
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		付款前，交易被关闭
									    	</div>
										</li>
									</ul>
								</div>
								
								
								
								
								<div class="h22 font14 c_384856 m_t30 m_b35">
									
									<div class="f_l h22 lh22 m_r10">
										订单来源：
									</div>
									
									<ul class=" h22 lh22 f_l">
										<li class="h22 lh22 f_l m_r117">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		嗨淘
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r63">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		聚划算
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r48">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		普通淘宝
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r48">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		TOP平台
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r63">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		手机
									    	</div>
										</li>
										<li class="h22 lh22 f_l">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		历史订单导入
									    	</div>
										</li>
									</ul>
								</div>
							</div>	
								
							
							
							<p class="c_ff6363 font14 m_b10">
								温馨提示：为确保数据安全性及更高安全机制，订单查询中手机号现做模糊化处理，短信发送查询均不受影响。
							</p>
							
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="w75">序号</th>
								    <th class="w105">店铺名称</th>
								    <th class="w145">订单编号</th>
								    <th class="w105">订单状态</th>
								    <th class="w105">下单时间</th>
								    <th class="w110">实付金额(元)</th>
								    <th class="w100">买家昵称</th>
								    <th class="w85">收货人姓名</th>
								    <th class="w105">收货人手机</th>
								    <th class="w120">收货人所在城市</th>
								    <th class="w110">所购产品类别</th>
								    <th class="w100">所购产品</th>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center">1</td>
								    <td class="w105 text-center">靓丽你的一生</td>
								    <td class="w145 text-center">2109020667285926</td>
								    <td class="w105 text-center">已付款未发货</td>
								    <td class="w105 text-center">2016-07-25 14:47:30</td>
								    <td class="w110 text-center">149 元</td>
								    <td class="w100 text-center">花**1</td>
								    <td class="w85 text-center">时**</td>
								    <td class="w105 text-center">136****0001</td>
								    <td class="w120 text-center">固原市</td>
								    <td class="w110 text-center">护理系列</td>
								    <td class="w100 text-center">口罩</td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w145 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w110 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w110 text-center"></td>
								    <td class="w100 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w145 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w110 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w110 text-center"></td>
								    <td class="w100 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w145 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w110 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w110 text-center"></td>
								    <td class="w100 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w145 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w110 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w110 text-center"></td>
								    <td class="w100 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w145 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w110 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w105 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w110 text-center"></td>
								    <td class="w100 text-center"></td>
								  </tr>
								</table>
							</div>
							
							
							<!--------分页-------->
                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
                                </div>
                            </div>
							
						</div>
					
					
					</div>
					
					
					<!---------------历史订单导入--------------->
					<div class="h940 dpsj_in display_none">
						<!----------上部---------->
						<div class="w1620 h80 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								历史订单导入
							</div>
							<!---------------描述--------------->
							<div class="font14">
								初次使用，需导入3个月以前的历史订单数据
							</div>
							
						</div>
						<!----------下部---------->
					
						<div class="w1620 h705 bgc_fff p_t30 p_l40 p_b40 discount_in m_b30">
							
							<div class="">
								
								<!--------使用帮助-------->
								<div class="m_b22 font14 c_384856">
									
									<p>使用帮助:</p>
									<p class="m_t10">
										<span>第一步：CRM已自动为您同步3个月之内的历史订单，3个月之前的历史订单需要亲手动导入。</span>
										<span class="c_00a0e9">点击下载</span>
									</p>
									
									<p class="m_t10 m_b10">
										第二步：点击上传文件选择已下载好的订单报表文件（ExportOrderListxxxxxxxx.csv）上传即可
									</p>
									<button class="m_b10 cursor_ w140 h50 m_t20 lh50 font14 c_fff bgc_00a0e9 border0 b_radius5">
										<img class="m_r20" src="${ctx}/crm/images/upload.png" />
										上传文件
									</button>
									
									<!--------查询设置-------->
									<div class="font14 c_384856 h50 lh50 m_b40">
										<div class="f_l m_r15">
											时间范围:
										</div>
										<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
										<div class="f_l">~</div>
										<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 " />
										<div class="f_l m_r15 m_l40">
											文件名称:
										</div>
										<input class="bgc_f4f6fa border0 w250 h50 f_l m_r100" />
										<div class="w100 h50 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
											查询
										</div>
									</div>
									
									<div class="m_b10 m_t20 h50">
										<button class="history_list_btn f_l cursor_ w140 h50 lh50 font14 c_fff bgc_00a0e9 border0 b_radius5">
										历史订单导入统计
										</button>
										<div class="f_l w1090 m_l10">
											<p class="font14 c_ff6363">
												【重要提示】根据淘宝要求，超过7天未登陆软件将停止订单数据推送，会导致订单中心不能正常发送催付物流短信，数据查询出现误差等问题。为确保功能正常使用，请务必每个店铺每周至少登陆1次软件。（多店铺绑定请确保主店铺每周登陆1次）
											</p>
										</div>	
									</div>
									
									
								</div>
								
								<!--------详细数据-------->
								<div class="c_384856">
									<table>
									  <tr class="">
									    <th class="w75 text-center">
									    	序号
									    </th>
									    <th class="w180">订单文件</th>
									    <th class="w270">宝贝文件</th>
									    <th class="w210">导入时间</th>
									    <th class="w205">总行数</th>
									    <th class="w180">处理状态</th>
									    <th class="w150">操作</th>
									  </tr>
									  <tr class="">
									    <td class="w75 text-center">
									    	1
									    </td>
									    <td class="w180 text-center"></td>
									    <td class="w270 text-center"></td>
									    <td class="w210 text-center"></td>
									    <td class="w205 text-center"></td>
									    <td class="w180 text-center"></td>
									    <td class="w150 text-center"></td>
									  </tr>
									  <tr class="">
									    <td class="w75 text-center">
									    	2
									    </td>
									    <td class="w180 text-center"></td>
									    <td class="w270 text-center"></td>
									    <td class="w210 text-center"></td>
									    <td class="w205 text-center"></td>
									    <td class="w180 text-center"></td>
									    <td class="w150 text-center"></td>
									  </tr>
									  <tr class="">
									    <td class="w75 text-center">
									    	3
									    </td>
									    <td class="w180 text-center"></td>
									    <td class="w270 text-center"></td>
									    <td class="w210 text-center"></td>
									    <td class="w205 text-center"></td>
									    <td class="w180 text-center"></td>
									    <td class="w150 text-center"></td>
									  </tr>
									  <tr class="">
									    <td class="w75 text-center">
									    	4
									    </td>
									    <td class="w180 text-center"></td>
									    <td class="w270 text-center"></td>
									    <td class="w210 text-center"></td>
									    <td class="w205 text-center"></td>
									    <td class="w180 text-center"></td>
									    <td class="w150 text-center"></td>
									  </tr>
									  <tr class="">
									    <td class="w75 text-center">
									    	5
									    </td>
									    <td class="w180 text-center"></td>
									    <td class="w270 text-center"></td>
									    <td class="w210 text-center"></td>
									    <td class="w205 text-center"></td>
									    <td class="w180 text-center"></td>
									    <td class="w150 text-center"></td>
									  </tr>
									</table>
								</div>
								
								<!--------分页-------->
	                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
	                                <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
	                                </div>
	                            </div>
								
										
									
									
									
							</div>
								
								
								
								
						</div>
							
							
							
							
					</div>
					
						
					<!---------------短信发送记录--------------->
					<div class="h940 dpsj_in">
						<!----------上部---------->
						<div class="w1620 h130 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								短信发送记录
							</div>
							<!---------------描述--------------->
							<div class="font14">
								查询店铺短信发送记录
							</div>
							
							<div class="font16 c_384856 h50 lh50 m_t18">
								<div class="w140 h50 text-center f_l bgc_fff text_out cursor_">
									短信查询
								</div>
								<div class="w200 h50 text-center f_l bgc_e3e7f0 text_out cursor_">
									指定号码发送短信查询
								</div>
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1620 h675 bgc_fff p_t35 p_l40 p_b40 text_in">
							
							<!--------查询设置-------->
							<div class="font14 c_384856 h45 lh45">
								<div class="f_l m_r15 h45 lh45">
									手机号码:
								</div>
								
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
								
								<div class="f_l m_r15">
									发送状态:
								</div>
								<div class="wrap f_l h45 lh45 m_r70">
								    <div class="nice-select h45 lh45" name="nice-select">
								    	<input class="h45 lh45" type="text" value="" readonly>
								    	<ul>
								    		<li>发送成功</li>
								    		<li>发送失败</li>
								    	</ul>
								  	</div>
								</div>
								
								<div class="f_l m_r15">
									短信类型:
								</div>
								<div class="wrap f_l h45 lh45 m_r65">
								    <div class="nice-select h45 lh45" name="nice-select">
								    	<input class="h45 lh45" type="text" value="" readonly>
								    	<ul>
								    		<li>催付提醒</li>
								    		<li>催付提醒</li>
								    		<li>催付提醒</li>
								    	</ul>
								  	</div>
								</div>
								
								<div class="w100 h45 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
									查询
								</div>
							</div>
							<div class="font14 c_384856 h50 lh50 m_t20 m_b20">
								<div class="f_l m_r15">
									买家昵称:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
								
								
								<div class="f_l m_r15">
									发送时间:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
								<div class="f_l">~</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r100" />
								
							</div>
							
							<!--------操作选项-------->
							<div class="font14 h52 m_b15">
								
								<div class="m_r70 w100 lh52 h52 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
									导入
								</div>
								<div class="f_l m_r60 m_t16">
							    	<div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="m_l10 f_l font14 c_384856">
							    		默认分组
							    	</div>
								</div>
								<div class="f_l m_t16">
							    	<div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="m_l10 f_l font14 c_384856">
							    		匿名分组
							    	</div>
								</div>
								
							</div>
							<p class="m_b15 c_ff6363 font14">
								温馨提示：短信记录目前只保留2015年10月1日之后的记录。
							</p>
							
							
							<!--------详细数据-------->
							<div class="c_384856 font14">
								<table>
								  <tr class="">
								    <th class="w75">序号</th>
								    <th class="w115">手机号码</th>
								    <th class="w350">短信内容</th>
								    <th class="w195">短信通道</th>
								    <th class="w85">短信类型</th>
								    <th class="w125">买家昵称</th>
								    <th class="w100">发送状态</th>
								    <th class="w100">实际扣费(条)</th>
								    <th class="w140">发送时间</th>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center">1</td>
								    <td class="w115 text-center">13328401827</td>
								    <td class="w330 text-center p_l10 p_r10">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    		${ShopName}主人，我是你下单成功的宝贝！梳妆打扮等待您付款，已准备出发去你那儿啦。您还不快收养我？	
								    	</div>
								    </td>
								    <td class="w195 text-center">靓丽你的一生_客云通道短信</td>
								    <td class="w85 text-center">下单关怀</td>
								    <td class="w125 text-center">tb8401827_44</td>
								    <td class="w100 text-center">发送成功</td>
								    <td class="w100 text-center">1</td>
								    <td class="w140 text-center">2016-07-25 16:14:45</td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								</table>
							</div>
							
							
							<!--------分页-------->
                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
                                </div>
                            </div>
							
						</div>
					
						<!----------下部---------->
						<div class="w1620 h675 bgc_fff p_t35 p_l40 p_b40 text_in">
							
							<!--------查询设置-------->
							<div class="font14 c_384856 h45 lh45">
								<div class="f_l m_r15 h45 lh45">
									手机号码:
								</div>
								
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
								
								<div class="f_l m_r15">
									发送状态:
								</div>
								<div class="wrap f_l h45 lh45 m_r70">
								    <div class="nice-select h45 lh45" name="nice-select">
								    	<input class="h45 lh45" type="text" value="" readonly>
								    	<ul>
								    		<li>发送成功</li>
								    		<li>发送失败</li>
								    	</ul>
								  	</div>
								</div>
								
								<div class="f_l m_r15">
									短信类型:
								</div>
								<div class="wrap f_l h45 lh45 m_r65">
								    <div class="nice-select h45 lh45" name="nice-select">
								    	<input class="h45 lh45" type="text" value="" readonly>
								    	<ul>
								    		<li>催付提醒</li>
								    		<li>催付提醒</li>
								    		<li>催付提醒</li>
								    	</ul>
								  	</div>
								</div>
								
								<div class="w100 h45 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
									查询
								</div>
							</div>
							<div class="font14 c_384856 h50 lh50 m_t20 m_b20">
								<div class="f_l m_r15">
									买家昵称:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
								
								
								<div class="f_l m_r15">
									发送时间:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
								<div class="f_l">~</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r100" />
								
							</div>
							
							<!--------操作选项-------->
							<div class="font14 h52 m_b15">
								
								<div class="m_r70 w100 lh52 h52 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
									导入
								</div>
								<div class="f_l m_r60 m_t16">
							    	<div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="m_l10 f_l font14 c_384856">
							    		默认分组
							    	</div>
								</div>
								<div class="f_l m_t16">
							    	<div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="m_l10 f_l font14 c_384856">
							    		匿名分组
							    	</div>
								</div>
								
							</div>
							<p class="m_b15 c_ff6363 font14">
								温馨提示：短信记录目前只保留2015年10月1日之后的记录。
							</p>
							
							
							<!--------详细数据-------->
							<div class="c_384856 font14">
								<table>
								  <tr class="">
								    <th class="w75">序号</th>
								    <th class="w115">手机号码</th>
								    <th class="w350">短信内容</th>
								    <th class="w195">短信通道</th>
								    <th class="w85">短信类型</th>
								    <th class="w125">买家昵称</th>
								    <th class="w100">发送状态</th>
								    <th class="w100">实际扣费(条)</th>
								    <th class="w140">发送时间</th>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center">1</td>
								    <td class="w115 text-center">13328401827</td>
								    <td class="w330 text-center p_l10 p_r10">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    		【关欣康家用医疗器械】主人，我是你下单成功的宝贝！梳妆打扮等待您付款，已准备出发去你那儿啦。您还不快收养我？	
								    	</div>
								    </td>
								    <td class="w195 text-center">靓丽你的一生_客云通道短信</td>
								    <td class="w85 text-center">下单关怀</td>
								    <td class="w125 text-center">tb8401827_44</td>
								    <td class="w100 text-center">发送成功</td>
								    <td class="w100 text-center">1</td>
								    <td class="w140 text-center">2016-07-25 16:14:45</td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w115 text-center"></td>
								    <td class="w330 text-center p_l10 p_r10 one_line_only">
								    	<div class="w330 margin0_auto h50 lh50 one_line_only text_detail">
								    	</div>
								    </td>
								    <td class="w195 text-center"></td>
								    <td class="w85 text-center"></td>
								    <td class="w125 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w140 text-center"></td>
								  </tr>
								</table>
							</div>
							
							
							<!--------分页-------->
                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
                                </div>
                            </div>
							
						</div>
					
						
					</div>
					
						
					<!---------------当日数据--------------->
					<div class="m_b30 dpsj_in display_none">
						<!----------上部---------->
						<div class="w1620 h70 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								当日数据
							</div>
							<!---------------描述--------------->
							<div class="font14">
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1620 bgc_fff p_t35 p_l40 p_b40 viphd_in">
							
							<!--------查询设置-------->
							<div class="font14 c_384856 h52 lh52">
								<div class="f_l m_r15 c_384856">
										数据类型：
									</div>
									<div class="wrap f_l h50">
									    <div class="nice-select h50" name="nice-select">
									    	<input class="h50" type="text" value="今日总访客" readonly>
									    	<ul>
									    		<li>1</li>
									    		<li>2</li>
									      		<li>3</li>
									    		<li>4</li>
									    	</ul>
									  	</div>
									</div>
								<div class="f_l m_r15 m_l40">
									日期:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
								<div class="f_l">~</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r185" />
								<div class="w100 h50 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
									查询
								</div>
							</div>
							
							
							<div class="h22 m_t30 m_b30">
								
								<div class="f_l">
							    	<div class="cursor_ f_l one_check_only w20 h20 border_d9dde5 b_radius5">
							    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="c_384856 font14 h22 lh22 m_l10 f_l">
							    		昨日数据
							    	</div>
								</div>
								
								<div class="f_l m_l45">
							    	<div class="cursor_ f_l one_check_only w20 h20 border_d9dde5 b_radius5">
							    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="c_384856 font14 h22 lh22 m_l10 f_l">
							    		前日数据
							    	</div>
								</div>
								
								
							</div>
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="w620">类别</th>
								    <th class="w620">刷新数据</th>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">订单数</td>
								    <td class="w620 text-center">50件</td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">退款中</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">待付款</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">待发货</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">出售中</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">成交额</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">购物车宝贝</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">被收藏宝贝</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">被浏览宝贝</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">总访客</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">PC流量</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">无线流量</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">聚划算成交额</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">PC订单数</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">无线订单数</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">PC成交额</td>
								    <td class="w620 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w620 text-center">无线成交额</td>
								    <td class="w620 text-center"></td>
								  </tr>
								</table>
							</div>
							
							
							
						</div>
					
						<!----------下部---------->
						<div class="w1620 bgc_fff p_t35 p_l40 p_b40 viphd_in display_none">
							
							<!--------查询设置-------->
							<div class="font14 c_384856 h52 lh52 m_b35">
								<div class="f_l m_r15">
									手机号码:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
								
								
								<div class="f_l m_r15">
									是否已读:
								</div>
								<div class="wrap f_l m_r40">
								    <div class="nice-select h50" name="nice-select">
								    	<input class="" type="text" value="" readonly>
								    	<ul>
								    		<li>是</li>
								    		<li>否</li>
								    	</ul>
								  	</div>
								</div>
								<div class="f_l m_r15">
									买家昵称:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
								
							</div>
							
							<div class="h52 lh52 m_b10">	
								<div class="f_l m_r15 c_384856">
									发送时间:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
								<div class="f_l">~</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r40" />
								
								<div class="f_l m_r15 c_384856">
									短信内容:
								</div>
								<div class="wrap f_l m_r20">
								    <div class="nice-select h50" name="nice-select">
								    	<input class="" type="text" value="" readonly>
								    	<ul>
								    		<li>包含</li>
								    		<li>不包含</li>
								    	</ul>
								  	</div>
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r40" />
								
							</div>
							
							<!--------导出-------->
							<div class="h50 p_r350 m_b10">
								<div class="w100 f_l h50 lh50 b_radius5 bgc_00a0e9 c_fff text-center cursor_">
										导出
								</div>
								<span class="f_r font12 c_384856">（可输入TD、T、N、等查询退订短信客户）</span>
							</div>
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="w150">买家昵称</th>
								    <th class="w150">手机号码</th>
								    <th class="w425">短信内容</th>
								    <th class="w150">发送时间</th>
								    <th class="w150">是否已读</th>
								    <th class="w100">最近更新日期</th>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center">sdgsdgsdfgsdf</td>
								    <td class="w150 text-center">13911112222</td>
								    <td class="w405 text-center p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
								    	</div>
								    </td>
								    <td class="w150 text-center">2016-12-12</td>
								    <td class="w100 text-center">是</td>
								    <td class="w150 text-center">2016-12-12</td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
								    	</div>
								    </td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w425 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w425 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w425 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w425 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								  <tr class="">
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w425 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w100 text-center"></td>
								    <td class="w150"></td>
								  </tr>
								</table>
							</div>
							<!--------分页-------->
                            <div class="p_r430 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
                                </div>
                            </div>
							
						</div>
					
						<!----------下部---------->
						<div class="w1620 bgc_fff p_t35 p_l40 p_b40 viphd_in display_none">
							
							<!--------查询设置-------->
							<div class="font14 c_384856 h52 lh52 m_b35">
								<div class="f_l m_r15">
									手机号码:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
								
								
								<div class="f_l m_r15">
									发送状态:
								</div>
								<div class="wrap f_l m_r40">
								    <div class="nice-select h50" name="nice-select">
								    	<input class="" type="text" value="" readonly>
								    	<ul>
								    		<li>发送成功</li>
								    		<li>发送失败</li>
								    	</ul>
								  	</div>
								</div>
								<div class="f_l m_r15">
									卖家昵称:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r100" />
								
								<div class="w100 f_l h50 lh50 b_radius5 bgc_00a0e9 c_fff text-center cursor_">
									查询
								</div>
								
							</div>
							
							<div class="h52 lh52 m_b10">	
								<div class="f_l m_r15 c_384856">
									发送时间:
								</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
								<div class="f_l">~</div>
								<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r40" />
								
								
							</div>
							
							<!--------导出-------->
							<div class="h50 p_r350 m_b10">
								<div class="w100 f_l h50 lh50 b_radius5 bgc_00a0e9 c_fff text-center cursor_">
										导出
								</div>
							</div>
							<div class="c_384856 font14 m_b15">温馨提示：短信记录目前只保留2015年10月1日之后的记录。</div>
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="position_relative z_1 w65 p_l10 ">
								    	<div class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div class="p_l20">全选</div>
								    </th>
								    <th class="w60">序号</th>
								    <th class="w150">买家昵称</th>
								    <th class="w150">手机号码</th>
								    <th class="w425">短信内容</th>
								    <th class="w100">短信通道</th>
								    <th class="w90">发送状态</th>
								    <th class="w120">实际扣费(条)</th>
								    <th class="w130">发送时间</th>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">1</td>
								    <td class="w150 text-center">sdgsdgsdfgsdf</td>
								    <td class="w150 text-center">13911112222</td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
								    	</div>
								    </td>
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center">2</td>
								    <td class="w120 text-center">100</td>
								    <td class="w130 text-center">2016-12-15 18:30</td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">2</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    	<div class="w405 h50 lh50 one_line_only text_detail">
								    		短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
								    	</div>
								    </td>
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">3</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">4</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">5</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">6</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								  <tr class="font12">
								    <td class="position_relative p_l10 z_1 w65">
								    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div></div>
								    </td>
								    <td class="w60 text-center">7</td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w405 text-center font14 p_l10 p_r10">
								    <td class="w100 text-center"></td>
								    <td class="w90 text-center"></td>
								    <td class="w120 text-center"></td>
								    <td class="w130 text-center"></td>
								  </tr>
								</table>
							</div>
							<!--------分页-------->
                            <div class="p_r430 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
                                </div>
                            </div>
							
						</div>
					
					
					</div>
					
					
					<!---------------操作日志--------------->
					<div class="dpsj_in display_none">
						<!----------上部---------->
						<div class="w1620 h100 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								操作日志
							</div>
							<!---------------描述--------------->
							<div class="font14">
								可查看近三个月具体的操作明细(只显示2015-01-01以后数据)
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1620 bgc_fff p_t35 p_l40 p_b40 hmd_in">
							
							<!--------查询设置-------->
							<div class="font14 c_384856 h47 lh45 m_b15">
								<div class="f_l m_r15">
									所属功能:
								</div>
								<div class="wrap f_l h45 lh45">
								    <div class="nice-select h45 lh45" name="nice-select">
								    	<input class="h45 lh45" type="text" value="全部" readonly>
								    	<ul>
								    		<li>1</li>
								    		<li>2</li>
								      		<li>3</li>
								    		<li>4</li>
								    	</ul>
								  	</div>
								</div>
								
								<div class="f_l m_r15 h45 lh45 m_l40">
									操作时间:
								</div>
								<input class="bgc_f4f6fa border0 w250 h45 lh45 f_l m_r10" />
								<div class="f_l">~</div>
								<input class="bgc_f4f6fa border0 w250 h45 lh45 f_l m_l10 m_r100" />
								<div class="w100 h45 lh45 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
									查询
								</div>
							</div>
							
							
							<!--------详细数据-------->
							<div class="c_384856">
								<table>
								  <tr class="">
								    <th class="w75">序号</th>
								    <th class="w185">操作人</th>
								    <th class="w150">操作功能</th>
								    <th class="w150">操作类型</th>
								    <th class="w150">操作说明</th>
								    <th class="w220">操作时间</th>
								    <th class="w160">状态</th>
								    <th class="w170">IP</th>
								  </tr>
								  <c:forEach items="${userOperationLogList}" var="operationlog">
								  	<tr class="">
									    <td class="w75 text-center">${operationlog.id}</td>
									    <td class="w185 text-center">${operationlog.operator}</td>
									    <td class="w150 text-center">${operationlog.function}</td>
									    <td class="w150 text-center">${operationlog.type}</td>
									    <td class="w150 text-center">${operationlog.remark}</td>
									    <td class="w220 text-center">${operationlog.date}</td>
									    <td class="w160 text-center">${operationlog.state}</td>
									    <td class="w170 text-center">${operationlog.ipAdd}</td>
								  	</tr>
								  </c:forEach>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w185 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w220 text-center"></td>
								    <td class="w160 text-center"></td>
								    <td class="w170 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w185 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w220 text-center"></td>
								    <td class="w160 text-center"></td>
								    <td class="w170 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w185 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w220 text-center"></td>
								    <td class="w160 text-center"></td>
								    <td class="w170 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w185 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w220 text-center"></td>
								    <td class="w160 text-center"></td>
								    <td class="w170 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w185 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w220 text-center"></td>
								    <td class="w160 text-center"></td>
								    <td class="w170 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w185 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w220 text-center"></td>
								    <td class="w160 text-center"></td>
								    <td class="w170 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w185 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w220 text-center"></td>
								    <td class="w160 text-center"></td>
								    <td class="w170 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w185 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w220 text-center"></td>
								    <td class="w160 text-center"></td>
								    <td class="w170 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w185 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w220 text-center"></td>
								    <td class="w160 text-center"></td>
								    <td class="w170 text-center"></td>
								  </tr>
								  <tr class="">
								    <td class="w75 text-center"></td>
								    <td class="w185 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w150 text-center"></td>
								    <td class="w220 text-center"></td>
								    <td class="w160 text-center"></td>
								    <td class="w170 text-center"></td>
								  </tr>
								</table>
							</div>
							
							
							<!--------分页-------->
                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
                                </div>
                            </div>
							
						</div>
					
					
					</div>
					
					
				</div>	
				
					
					
					
					<!---------------历史订单导入弹窗--------------->
					<div class="w1673 h1000 bgc_e3e7f0 position_absolute z_5 history_list top40 display_none">
						
						<div class="w1030 h610 bgc_fff margin0_auto m_t100 p_t20 p_l20 p_r20 p_b20">
							
							
							<div class="m_b20 h42">
								
								<div class="cursor_ w170 h42 lh42 f_l bgc_00a0e9 font14 b_radius5 c_fff text-center ">
									缺少手机号会员统计
								</div>
								
								<div class="cursor_ w168 m_r95 h40 lh40 f_l bgc_fff font14 b_radius5 c_cad3df border_cad3df text-center ">
									历史订单导入统计
								</div>
								
								<div class="cursor_ save_history_list w100 m_r40 h42 lh42 f_l bgc_00a0e9 font14 b_radius5 c_fff text-center">
									保存
								</div>
								
								<div class="cursor_ cancel_history_list w98 h40 lh40 f_l bgc_fff font14 b_radius5 c_cad3df border_cad3df text-center ">
									取消
								</div>
								
							</div>
							
							<div class="h47">
								<div class="f_l h45 l_h45 m_r15 c_384856 m_l40">
									年份：
								</div>
								<div class="wrap f_l h45 w130">
								    <div class="nice-select h45 w130" name="nice-select">
								    	<input type="text h45 w130" value="" readonly>
								    	<ul>
								    		<li>2016</li>
								    		<li>2015</li>
								      		<li>2014</li>
								    		<li>2013</li>
								    		<li>2012</li>
								    	</ul>
								  	</div>
								</div>
							</div>
							
							<p class="font12 c_8493a8 p_l40 m_t20">
								表中展示每个月份没有手机号的会员有多少，可通过导入历史订单补全会员手机号
							</p>
							
						
							
						</div>
						
					</div>
					
					
					
					
					
					
					
						
				</div>
		
			</div>
	
	</div>

	
	

					

	
	

					

	
	
	
	
	
	

	
				
</body>

</html>
