<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
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
	<script type="text/javascript" src="${ctx}/crm/js/order_list.js" ></script>
		
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
				<div class="w1645 m_t70 m_l30">
					<div class="w100_ h50 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							店铺数据
						</div>
						<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
							<a class="c_384856" href="${ctx}/crms/storeData/todyOrderList">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									订单列表
								</div>
							</a>
							<a class="c_384856" href="${ctx}/OrderHistoryImport/showLoadHistoryOrder">
								<div class="f_l w140 text-center cursor_">
									历史订单导入
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/shopData/smsSendRecord">
								<div class="f_l w140 text-center cursor_">
									短信发送记录
								</div>
							</a>
							<a class="c_384856" href="today_data.html">
								<div class="f_l w140 text-center cursor_">
									<a href="${ctx}/crms/storeData/todyData">当日数据</a>
								</div>
							</a>
							<a class="c_384856" href="operation_log.html">
								<div class="f_l w140 text-center cursor_ ">
									操作日志
								</div>
							</a>
						</div>
					</div>
					
					
					<!---------------订单列表--------------->
					<div class="">
						<!----------上部---------->
						<div class="w1605 h100 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								当日订单列表
							</div>
							<!---------------描述--------------->
							<div class="font14">
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1605 m_b30 bgc_fff p_t35 p_l40 p_b40">
							
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
								<div class="w100 h45 lh45 b_radius5 tk c_00a0e9 border_00a0e9 text-center f_l cursor_">
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
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		没有创建支付宝交易
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		已下单未付款
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		卖家部分发货
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		已付款未发货
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		卖家已发货
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		买家已签收
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		交易成功
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		已退款
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r20">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
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
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		嗨淘
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r63">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		聚划算
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r48">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		普通淘宝
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r48">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		TOP平台
									    	</div>
										</li>
										<li class="h22 lh22 f_l m_r63">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
									    	</div>
									    	<div class="f_l text-left m_l10">
									    		手机
									    	</div>
										</li>
										<li class="h22 lh22 f_l">
									    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
									    		<img class="cursor_ group_check_ display_none" src="images/black_check.png" />
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