<%@page import="java.util.ArrayList"%>
<%@page import="s2jh.biz.shop.crm.manage.entity.TradeDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>营销中心-订单短信群发</title>
	<%@ include file="/common/common.jsp"%>
	
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		
	<!--兼容360meta-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/laypage.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/crm/css/xuanzeshangpin.css"/>
		
    <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
    <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
	<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js" ></script> --%>
	<script type="text/javascript" src="${ctx}/crm/js/send_group_order_mail.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/util.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/laypage.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/xuanzeshangpin.js"></script>
<%-- 	<script type="text/javascript" src="${ctx}/crm/js/text_designate_number.js"></script> --%>
</head>
<body>
   <!------------- 商品ID弹出框------------------->
	<div class="rgba_000_5 h100_ w100_ position_fixed z_13 display_none SHOPTCKID">
				<div class="w790 bgc_fff m_t5vw padding1_5625vw m_l27_89vw">
				      <div class="w100_ text-center m_b30">选择指定商品</div>
				     <!--  <input type="hidden" id="itemsId"> -->
				      <div class="w790 m_b20 clearfix">
						  <div class="wrap f_l h45 lh45 m_r15">
						  			      <div class="nice-select h45 lh45 w6_458vw" name="nice-select" id="big_timeType">
										    	<input class="h45 lh45" type="text" value="全部" readonly id="show_TimeType">
										    	<ul id="_timeType">
										    		<li value="1">全部</li>
										    	</ul>
										    	<input type="hidden" name="timeType" value="" id="timeType"/>
										  </div>
						  </div>
						  <div class="f_l h45 lh45 m_r10 font16">关键字：</div>
						  <input id="titleName" class="bgc_f4f6fa border0 b_radius5 w430 h45 lh45 f_l m_r20" value="" type="text"/>
						  <div onclick="searchItem(0,7)" class="chaxun bgc_00a0e9 c_fff cursor_">查询</div>
				      </div>
					  
					  <div class="w790 h55 lh55">
					  		<div class="w75 h55 text-center bgc_e3e7f0 f_l">选择</div>
					  		<div class="w620 h55 text-center bgc_e3e7f0 f_l">宝贝名称</div>
					  		<div class="w95 h55 text-center bgc_e3e7f0 f_l">金额</div>
					  </div>
					  <div class="w790 h380 overflow_auto">
						  <table id="searchItem" class="w773 text-center">	
						 </table>
					 </div>
					 <div class="f_r w470 h24 l_h22 c_8493a8 m_t10 m_b30 ajax_page">
                                    <!-- <div class="f_l w45 h22 bor_e0e6ef text-center m_r4 cursor_ font12">上一页</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_ font12 bgc_00a0e9 c_fff">1</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_ font12">2</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_ font12">3</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_ font12">4</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_ font12">5</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_ font12">6</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_ font12">7</div>
                                    <div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_ font12">8</div>
                                    <div class="f_l w22 h22 text-center m_r4 font12">...</div>
                                    <div class="f_l w45 h22 bor_e0e6ef text-center m_r20 cursor_ font12">下一页</div>
                                    <div class="f_l w45 h22 m_r4"><input class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8" type="text" value=""></div>
                                    <div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_ font12">确定</div> -->
                      </div>
                      <div class="clear w790 h42 margin0_auto">
                            <div class="w214 margin0_auto">
                                  <div onclick="addItems()" class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ Cancel">
                                                          			          确定
                                  </div>
                                  <div class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ Cancel close_this">
                                                                		取消
                                  </div>
                            </div>
                      </div>   
                      
				
				</div>
	</div>
	
	
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
					<div class="w100_ lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							营销中心
						</div>
						<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
							<a class="c_384856" href="${ctx}/marketingCenter">
								<div class="f_l w140 text-center cursor_">
									会员短信群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/smsSendAppoint/appointNumberSend">
								<div class="f_l w140 text-center cursor_ ">
									指定号码群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/marketingCenter/list?label=1">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									订单短信群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/msgSendRecord/memberSendRecord">
								<div class="f_l w140 text-center cursor_">
									短信发送记录
								</div>
							</a>
							<a class="c_384856" href="${ctx}/member/msgSendRecord">
							<div class="f_l w200 text-center cursor_">会员短信群发效果分析</div>
						</a>
						<a class="c_384856" href="${ctx}/appointNumber/msgSendRecord">
							<div class="f_l w200 text-center cursor__">
								指定号码发送效果分析
							</div>
						</a>
						<a class="c_384856" href="${ctx}/order/msgSendRecord">
							<div class="f_l w200 text-center cursor__">
								订单短信群发效果分析
							</div>
						</a>
						</div>
						<div class="clear"></div>
					</div>
					
					
					<!---------------会员短信群发--------------->
					<div class="">
						<!----------上部---------->
						<div class="w1605 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								订单短信群发
							</div>
							<!---------------描述--------------->
							<div class="font14 m_b20">
								为了不影响客户休息，建议您发送短信时间在8点至22点之间。
							</div>
							<input id="message" name="message" value="${message}" type="hidden">
							
							<form id="formId" action="${ctx}/crms/list" method="post">
								<div class="h50 lh50  m_b20">
								<div class="h50  f_l c_384856 m_r15">
									买家昵称:
									<input id="queryKey" name="queryKey" value="${queryKey}" type="hidden">
								</div>
								<input style="width:13vw;margin-right:0.9vw;" class="bgc_fff border0 b_radius5 w330 h50 f_l" id="buyerId" value="${oVo.buyerNick}" type="text" name="buyerNick"/>
								
								
								
								<div class="h50 f_l c_384856 m_r15">
									订单编号:
								</div>
								<input id="totalOrderNum" value="${totalSize}" type="hidden">
								<input style="width:12.5vw" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"  class="bgc_fff border0 b_radius5 w330 h50 f_l m_r30" id="orderId" value="${oVo.tid}" type="text" name="tid"/>
								<div class="f_l m_r15">
										订单金额:
									</div>
									<input class="bgc_fff border0 b_radius5 w150 h50 f_l m_r10" value="${oVo.paymentBefore}" id="feeBefore" name="paymentBefore" onkeyup="this.value=this.value.replace(/\D/g, '')" onblur="this.value=this.value.replace(/\D/g, '')"/>
									<div class="f_l">~</div>
									<input class="bgc_fff border0 b_radius5 w150 h50 f_l m_l10 m_r15" value="${oVo.paymentAfter}" id="feeAfter" name="paymentAfter" onkeyup="this.value=this.value.replace(/\D/g, '')" onblur="this.value=this.value.replace(/\D/g, '')"/>
								
								
								
								
								
							</div>
								
								
							
							<div class="h50 lh50 m_b20">
							
								<div class="f_l m_r15">
									订单状态:
								</div>
									<input id="status" type="hidden" name="status" value="${oVo.status}">
								<div class="wrap f_l h50 m_r15">
								    <div class="z_5 nice-select w228 h50" name="nice-select">
								    	<input id="statusId" class="h50" type="text" value="全部" readonly>
								    	<ul id="statusUl">
								    		<li value="ALL">全部</li>
								    		<li value="WAIT_BUYER_PAY">等待买家付款</li>
								    		<li value="WAIT_SELLER_SEND_GOODS">等待卖家发货</li>
								      		<li value="WAIT_BUYER_CONFIRM_GOODS">等待买家确认收货</li>
								    		<li value="TRADE_BUYER_SIGNED">卖家已签收(货到付款专用)</li>
								    		<li value="TRADE_FINISHED">交易成功</li>
								    		<li value="CLOSED">交易关闭</li>
								    	</ul>
								  	</div>
								</div>
								
								
								
								<div class="f_l m_r15">
									下单时间:
								</div>
								
								<div class="f_l position_relative">
									<input class="bgc_fff border0 w230 p_l10 h50 m_r10" value="${oVo.startOrderDate}" type="text" id="tser11" name="startOrderDate" readonly>
									
									
									<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png"/>
								</div>
									
								<div class="f_l lh50">~</div>
								
								<div class="f_l position_relative">
									<input class="bgc_fff border0 w230 p_l10 h50 m_l10 m_r40" value="${oVo.endOrderDate}" type="text" id="tser12" name="endOrderDate" readonly>
									<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png"/>
								</div>
								<div class="f_l m_r15">
										订单来源:
								</div>
									<input id="orderFrom" type="hidden" name="orderFrom" value="${oVo.orderFrom}">
								<div class="wrap f_l h50 m_r20">
								    <div class="z_4 nice-select w228 h50" name="nice-select">
								    	<input id="orderFromId" class="h50" type="text" value="全部" readonly>
								    	<ul id="orderFromUl">
								    		<li value="ALL">全部</li>
								    		<li value="TAOBAO">PC端</li>
								    		<li value="WAP,WAP">手机端</li>
								    		<li value="import">导入</li>
								    	</ul>
								  	</div>
								</div>
								
							</div>
							
							
							
							<div class="more_detail">
							
								<div class="h50 lh50 m_b20">
								
									
								
									<div class="f_l m_r15">
										退款状态:
									</div>
										<input id="refundStatus" type="hidden" name="refundStatus" value="${oVo.refundStatus}">
									<div class="wrap f_l h50 m_r15">
									    <div class="z_4 nice-select w228 h50" name="nice-select" >
									    	<input id="refundStatusId" class="h50" type="text" value="全部" readonly>
									    	<ul id="refundStatusUl">
									    		<li value="ALL">全部</li>
									    		<li value="WAIT_SELLER_AGREE">买家申请退款，等待卖家消息</li>
									      		<li value="WAIT_BUYER_RETURN_GOODS">等待买家退货</li>
									    		<li value="WAIT_SELLER_CONFIRM_GOODS">买家已经退货，等待卖家确认收货</li>
									    		<li value="SELLER_REFUSE_BUYER">卖家拒绝退款</li>
									    		<li value="CLOSED">退款关闭</li>
									    		<li value="SUCCESS">退款成功</li>
									    		<li value="TOTAL">全部订单</li>
									    	</ul>
									  	</div>
									</div>
									
									<div class="f_l m_r15">
										发货时间:
									</div>
									
									<div class="f_l position_relative">
										<input class="bgc_fff border0 w230 p_l10 h50 m_r10" value="${oVo.consignTimeBefore}" type="text" id="tser13" name="consignTimeBefore" readonly>
										<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
									</div>
										
									<div class="f_l lh50">~</div>
									
									<div class="f_l position_relative">
										<input class="bgc_fff border0 w230 p_l10 h50 m_l10 m_r40" value="${oVo.consignTimeAfter}" type="text" id="tser14" name="consignTimeAfter" readonly>
										<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
									</div>
									<div class="h50 f_l c_384856 m_r15">
										选择商品:
									</div>
									
									<div id="xzspImg" style="line-height:0" class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
							    		<img class="cursor_ group_check_ w20 h20" src="${ctx}/crm/images/black_check.png" />
							    		<input type="hidden" id="xzspVal" value="">
							    	</div>
							    	<span style="float:left;height: 2.604166vw;line-height: 2.604166vw; margin-left:0.5vw">全部商品</span>
							    	<a href="javascript:;" class="serchBtn" id="xzspBtn">自定义</a>
									<input  type="hidden" class="bgc_fff border0 b_radius5 w330 h50 f_l m_r30" id="numIid" value="${oVo.numIidStr}" type="text" name="numIidStr"/>
									
									
								</div>
								
								
								
								<div class="h50 lh50 m_b20">
									<div class="f_l m_r15">
										评价状态:
									</div>
										<input id="rateStatus" name="rateStatus" class="h50" type="hidden" value="${oVo.rateStatus}">
									<div class="wrap f_l h50 m_r15">
									    <div class=" nice-select w228 h50" name="nice-select">
									    	<input id="rateStatusId" class="h50" type="text" value="全部" readonly>
									    	<ul id="rateStatusUl">
									    		<li value="全部">全部</li>
									    		<li value="买家未评">买家未评</li>
									    		<li value="卖家未评">卖家未评</li>
									      		<li value="买家已评，卖家未评">买家已评，卖家未评</li>
									    		<li value="买家未评，卖家已评">买家未评，卖家已评</li>
									    		<li value="双方已评价">双方已评价</li>
									    	</ul>
									  	</div>
									</div>
									
									
								
									
									
								
									<div class="f_l m_r15">
										确认时间:
									</div>
									
									<div class="f_l position_relative">
										<input class="bgc_fff border0 w230 p_l10 h50 m_r10" value="${oVo.endTimeBefore}" type="text" id="tser15" name="endTimeBefore" readonly>
										<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
									</div>
										
									<div class="f_l lh50">~</div>
									
									<div class="f_l position_relative">
										<input class="bgc_fff border0 w230 p_l10 h50 m_l10 m_r40" value="${oVo.endTimeAfter}" type="text" id="tser16" name="endTimeAfter" readonly>
										<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
									</div>
									
									<div class="f_l m_r15">
										所属地区:
									</div>
									<div id="ssdqImg" style="line-height:0" class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
							    		<img class="cursor_ group_check_ w20 h20" src="${ctx}/crm/images/black_check.png" />
							    		<input type="hidden" id="ssdqVal" value="">
							    	</div>
							    	<span style="float:left;height: 2.604166vw;line-height: 2.604166vw;margin-left:0.5vw">全部地区</span>
							    	<a href="javascript:;" class="serchBtn" id="ssdqBtn">自定义</a>
									<input type="hidden" class="bgc_fff border0 b_radius5 w170 h50 f_l m_r20 area_select_" value="${oVo.receiverState}" id="stateId" name="receiverState" readonly/>
								</div>
								<div class="h50 m_b20">
								
<!-- 									<div class="f_l m_r15 lh50"> -->
<!-- 										订单类型: -->
<!-- 									</div> -->
<%-- 										<input id="step01" type="hidden" name="step01" value="${oVo.step01}" > --%>
<%-- 										<input id="step02" type="hidden" name="step02" value="${oVo.step02}" > --%>
<!-- 									<div class="wrap f_l h50 lh50 m_r10"> -->
<!-- 									    <div class="z_3 nice-select w128 h50 lh50" name="nice-select"> -->
<!-- 									    	<input id="step01Id" class="h50 lh50" type="text" value="全部" readonly> -->
<!-- 									    	<ul id="step01Ul"> -->
<!-- 									    		<li value="全部">全部</li> -->
<!-- 									    	</ul>	 -->
<!-- 									  	</div> -->
<!-- 									</div> -->
<!-- 									<div class="f_l lh50">~</div> -->
<!-- 									<div class="wrap f_l h50 lh50 m_r15 m_l10"> -->
<!-- 									    <div class="z_3 nice-select w128 h50 lh50" name="nice-select"> -->
<!-- 									    	<input id="step02Id" class="h50 lh50" type="text" value="全部" readonly> -->
<!-- 									    	<ul id="step02Ul"> -->
<!-- 									    		<li value="全部">全部</li> -->
<!-- 									    	</ul> -->
<!-- 									  	</div> -->
<!-- 									</div> -->
									
									<div class="f_l m_r15 lh50">
										订单标识:
									</div>
									<input type="hidden" name="sellerFlagStr" id="flagId" value="${oVo.sellerFlagStr}"/>
									<ul class="h50 f_l font14 c_384856">
											<li class="h50 f_l m_r15">
										    	<div onclick="orderFlag(0)" id="hui" class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l text-left lh50 m_l10">
										    		<img style="width:1vw;" src="${ctx}/crm/images/hui.png" />
										    	</div>
											</li>
											<li class="h50 f_l m_r15">
										    	<div onclick="orderFlag(1)" id="hong" class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l text-left lh50 m_l10">
										    		<img style="width:1vw;" src="${ctx}/crm/images/redq.png" />
										    	</div>
											</li>
											<li class="h50 f_l m_r15">
										    	<div onclick="orderFlag(2)" id="huang" class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l text-left lh50 m_l10">
										    		<img  style="width:1vw;"src="${ctx}/crm/images/yellowq.png" />
										    	</div>
											</li>
											<li class="h50 f_l m_r15">
										    	<div onclick="orderFlag(3)" id="lv" class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l text-left lh50 m_l10">
										    		<img style="width:1vw;" src="${ctx}/crm/images/greenq.png" />
										    	</div>
											</li>
											<li class="h50 f_l m_r15">
										    	<div onclick="orderFlag(4)" id="lan" class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l text-left lh50 m_l10">
										    		<img style="width:1vw;" src="${ctx}/crm/images/blueq.png" />
										    	</div>
											</li>
											<li class="h50 f_l m_r15">
										    	<div onclick="orderFlag(5)" id="zi" class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l text-left lh50 m_l10">
										    		<img style="width:1vw;" src="${ctx}/crm/images/violetq.png" />
										    	</div>
											</li>
										</ul>
										<div class="f_l m_r10 lh50">
											短信过滤:
										</div>
											<input id="smsFile" name="smsFile" type="hidden" value="${oVo.smsFile}">
										<div class="wrap f_l h50 lh50">
										    <div class="z_3 nice-select w150 h50 lh50" name="nice-select">
										    	<input id="smsFileId" class="h50 lh50" type="text" value="不过滤" readonly>
										    	<ul id="smsFileUl">
										    		<li value="0">不过滤</li>
										    		<li value="1">当天</li>
										    		<li value="2">一天</li>
										    		<li value="3">两天</li>
										      		<li value="4">三天</li>
										    		<li value="5">四天</li>
										    		<li value="6">五天</li>
										    	</ul>
										  	</div>
										</div>
									
									
										
									</div>
									
							</div>
							
							
							
							
							<%-- <input id="hideConditionId" value="${hideCondition}"> --%>
							<div class="h50 lh50 p_r325">
							
								<div onclick="reset()" class="w100 resetting h48 b_radius5 c_00a0e9 border_00a0e9 tk text-center f_r cursor_">
									重置
								</div>
								
								<div onclick="formSubmit('formId')" class="m_r20 w100 h48 b_radius5 c_00a0e9 border_00a0e9 tk text-center f_r cursor_ chaxun_form">
									查询
								</div>
								
<!-- 								<div class="f_r h50 lh50 c_00a0e9 cursor_ more_detail_btn m_r20"> -->
<!-- 									更多条件 ︾ -->
<!-- 								</div> -->
									
							</div>
							<input type="hidden" id="showMore" name="showMore" value="${showMore }">
							</form>
							
							<div class="h50 lh50 m_t25">
							<input id="showName" type="hidden" value="${show_name}">
								<div>
									<a id="show_1" href="${ctx}/crms/list?label=1" onClick="addLabel('1')" class="font16 f_l out c_384856 w140 h50 bgc_fff cursor_ text-center">近三个月订单</a>
								</div>
								<div>
									<a id="show_2" href="${ctx}/crms/list?label=2" onClick="addLabel('2')" class="font16 f_l out c_384856 w140 h50 bgc_e3e7f0 cursor_ text-center">全部订单</a>
								</div>
								<div>
									<a id="show_3" href="${ctx}/crms/list?label=3" onClick="addLabel('3')" class="font16 f_l out c_384856 w140 h50 bgc_e3e7f0 cursor_ text-center">等待买家付款</a>
								</div>
								<div>
									<a id="show_4" href="${ctx}/crms/list?label=4" onClick="addLabel('4')" class="font16 f_l out c_384856 w140 h50 bgc_e3e7f0 cursor_ text-center">等待发货</a>
								</div>
								<div>
									<a id="show_5" href="${ctx}/crms/list?label=5" onClick="addLabel('5')" class="font16 f_l out c_384856 w140 h50 bgc_e3e7f0 cursor_ text-center">已发货</a>
								</div>
								<div>
									<a id="show_6" href="${ctx}/crms/list?label=6" onClick="addLabel('6')" class="font16 f_l out c_384856 w140 h50 bgc_e3e7f0 cursor_ text-center">交易成功订单</a>
								</div>
								<div>
									<a id="show_7" href="${ctx}/crms/list?label=7" onClick="addLabel('7')" class="font16 f_l out c_384856 w140 h50 bgc_e3e7f0 cursor_ text-center">需评价订单</a>
								</div>
								<div>
									<a id="show_8" href="${ctx}/crms/list?label=8" onClick="addLabel('8')" class="font16 f_l out c_384856 w140 h50 bgc_e3e7f0 cursor_ text-center">已关闭订单</a>
								</div>
							</div>
						</div>
						<!----------下部---------->
						<div id="send_show_1" class="w1605  in bgc_fff p_t20 p_l40 p_b40">
							<input id="ctx" type="hidden" value="${ctx}">
							 <div class="h42 m_b10 p_l15">
								<div class="m_t10 cursor_ all_check w20 h20 border_d9dde5 b_radius5 f_l quan_xuan">
						    		<img id="checkAllId"  class="w20 h20 cursor_ check_ display_none 2check_box_2" src="${ctx}/crm/images/black_check.png" />
						    	</div>
						    	<div class="f_l text-left lh42 h42 c_384856 m_l10 m_r10">
						    		全选
						    	</div>
						    	<div onclick="sendCheckSms();" class="m_r20 font14 w230 h40 lh40 b_radius5 c_00a0e9 border_00a0e9 tk text-center f_l cursor_">
									编辑并发送短信给已选订单
								</div>
						    	<div onclick="sendAllSms();" class="m_r20 font14 w250 h40 lh40 b_radius5 c_00a0e9 border_00a0e9 tk text-center f_l cursor_">
									编辑并发送短信给列表全部订单
								</div>
						    	
							</div>
							<img class="sdg" src="${ctx}/crm/images/yu-jiazai.gif"/>
							<%-- <img class="faSongZhong display_none" src="${ctx}/crm/images/yu-jiazai.gif"/> --%>
						<%-- <c:if test="${pagination.list.size() > 0}"> --%>
							<%-- <c:forEach items="${pagination.list}" var="trade"> --%>
							<c:if test="${datas != null}">
							<c:forEach items="${datas}" var="trade">
								<div class=" w1280 m_b15 check_all">
									<div class="h55 bgc_f1f3f7 p_l15 p_r25">
										<%-- <input id="mobileId" type="hidden" value="${orders.receiverMobile}"/> --%>
										<div class="m_t17 m_r10 cursor_ check w20 h20 border_d9dde5 b_radius5 f_l">
							    			<img class="w20 h20 cursor_ check_ display_none dan_xuan" src="${ctx}/crm/images/black_check.png" />
							    		</div>
							    		<p class="lh55 h55 font16 c_384856 f_l">
							    			订单编号：
							    			<span>${trade.tid}</span>
							    			下单时间：
							    			<span><fmt:formatDate value="${trade.createdUTC}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
							    			<span><font>(</font>
							    				<c:choose>
							    					<c:when test="${trade.tradeFrom == 'import'}">
							    						导入
							    					</c:when>
							    					<c:otherwise>
							    						${trade.tradeFrom}
							    					</c:otherwise>
							    				</c:choose>
							    			<font>)</font></span>
							    		</p>
									</div>
									<div class="w1120 bgc_fafafa p_l15 p_r25" style="position: relative;">
										<div class="f_l orderListH">
											<c:forEach items="${trade.orders}" var="order">
												<div>
													<div style="width:33vw;" class="h75 f_l p_t35">
														<img class="w50 h50 f_l  text-center" src="${order.picPath}">
														<div style="width:30vw;" class=" h50 l_h50 f_l overflow_hidden">${order.title}</div>
													</div>
													<div style="width:3vw;" class=" h75 f_l p_t35">
														<div class=" text-center">${order.price}</div>
														<div class=" text-center">${order.num}</div>
													</div>
													<div style="clear:both;"></div>
												</div>
											</c:forEach>
										</div>
										<div class="f_l detailH" style="position: absolute;right: 0;top: 40%;">
											<div class=" f_l" style="width:5vw;">
												<p class="text-center">
													<span class="nick_name">${trade.buyerNick}</span>
													<br/>
													<span class="">${fn:substring(trade.receiverMobile, 0, 3)}****${fn:substring(trade.receiverMobile, 7, 11)}</span>
													<input class="phone_text" value="${trade.receiverMobile}" type="hidden">
												</p>
											</div>
											<div class=" f_l" style="width:6vw;">
												<p class="text-center">
													<span>${trade.payment}</span>
													<br/>
													<span>${trade.num}</span>
												</p>
											</div>
											<div class=" f_l" style="width:9vw;">
											     	<div class=" text-center">
											     		<c:if test="${trade.status == 'TRADE_NO_CREATE_PAY'}">没有创建支付宝交易</c:if>
												   		<c:if test="${trade.status == 'WAIT_BUYER_PAY'}">等待买家付款</c:if>
													    <c:if test="${trade.status == 'WAIT_SELLER_SEND_GOODS'}">等待卖家发货</c:if>
													    <c:if test="${trade.status == 'WAIT_BUYER_CONFIRM_GOODS'}">等待买家确认收货</c:if> 
													    <c:if test="${trade.status == 'TRADE_BUYER_SIGNED'}">买家已签收</c:if>
													    <c:if test="${trade.status == 'TRADE_FINISHED'}">交易成功</c:if>
													    <c:if test="${trade.status == 'TRADE_CLOSED'}">付款以后用户退款成功，交易自动关闭</c:if>
													    <c:if test="${trade.status == 'TRADE_CLOSED_BY_TAOBAO'}">付款以前，卖家或买家主动关闭交易</c:if>
													    <c:if test="${trade.status == 'PAY_PENDING'}">国际信用卡支付付款确认中</c:if>
											     	</div>
											</div>
											<div style="margin-left: 2vw;" class="send_message order_mail_window_btn font14 w100 h40 lh40 b_radius5 c_00a0e9 border_00a0e9 tk text-center f_r cursor_">
												发送短信
												<input id="smsNumId" type="hidden"/>
												<input class="trade_id" type="hidden" value="${trade.tid}">
											</div>
											<div style="clear:both;"></div>
										</div>
										<div style="clear:both;"></div>
									</div>
								
								</div>	
																
							</c:forEach>
							</c:if>
						<c:if test="${datas.size() == 0}">
							<div class="h50 text-center font16 c_384856" style="width:51.041666vw;">暂时没有相关数据！！</div>
						</c:if>
							<!--------分页-------->
                           <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
                                <div class="f_r w525 h24 l_h22 font12">
                                	<c:if test="${datas.size() > 0}">
	                                  <c:forEach items="${pagination.pageView}" var="page">
											${page}
									  </c:forEach>
                                	</c:if>
                                </div>
                            </div>
						</div>
					
					</div>
					
				</div>	
						
				</div>
		
			</div>
	
					
<div id="showTemp" class="w100_ h100_ rgba_000_5 position_fixed top0 z_12 order_mail_window display_none">
	<input id="hideShowTemp" type="hidden" value="${tShow}">
	<div style="height:39vw;"  class="w1390 bgc_f1f3f7 margin0_auto position_fixed top100 left0 right0">
		<div class="font16 c_38485 text-center h50 lh50">
			选择短信模板
		</div>
		<div>
		<input id="smsTempId" type="hidden" type="text">
			<div class="w535 f_l h665 bgc_fff">
				<p class="text-center font16" style="color:#ff0000;">已选订单<span id="orderNum">	0 </span>单</p>
				<input id="totalSizeId" value="${totalCount}" type="hidden">
				<p class="c_384856 text-center font16 m_b10 m_t25">
					编辑内容     
				</p>
<!-- 				<p class="c_ff6363 text-center font14 m_b10"> -->
<!-- 					若含有促销词如:微信、QQ、店庆等,请务必在短信内容末尾添加“退订回N” -->
<!-- 				</p> -->
				<div class="margin0_auto b_radius5 bgc_f4f6fa position_relative">
					<div class="w500 h280">
						<textarea id="areaId" maxlength="500" class="text_area w480 p_b40 h250 border0 bgc_f4f6fa b_radius5 p_l10 p_r10 p_t10">【淘宝】</textarea>
					</div>
					<!-- <div class="w440 c_cecece p_l20 h40 lh40 b_radius5">
									已输入：<span id="inputNum" class="text_count">0</span>/500字 当前计费：<span id="contePrice">1</span>
									条 <a href="${ctx }/crms/home/notice#duanxinxiangguan" class="c_00a0e9 cursor_">计费规则</a>
								</div> -->
					<div class="w500 row" >
						<div class="w320 c_cecece p_l20 h30 lh30 b_radius5 col-md-8">
						已输入：<span id="inputNum" class="text_count">8</span> 当前计费：<span id="contePrice">1</span>
						条 <a id="JFGZ" class="c_00a0e9 cursor_" target="_blank">计费规则</a>
						</div>
						<div class="w80 h30 bgc_f4f6fa lh30 position_absolute right20" style="bottom:0;">
							<div class="c_bfbfbf m_r15 f_r w98" >退订回
	                        	<input maxlength="2" id="unsubscribeMSG" class="w20 h20 l_h20 border_0 bgc_f4f6fa" placeholder="N" value="N" readonly/>
	                        </div>
						</div>
					</div>
				</div>
				
				<div style="height:2vw;line-height:2vw;margin-left:1.3vw;" class="f_l c_384856 m_r15">
					活动名称:
				</div>
				<input style="height:2vw;" class="bgc_f4f6fa border_d9dde5 b_radius5 w200 f_l m_r30" id="activityId" value="${activityName}" type="text" name="activityName"/>
				<p style="color:red; padding-left:1.2vw;clear:both;">为了您方便查找历史群发短信息，请添加活动名称</p>
				<input id="smsType" type="hidden">
				<input id="tradeTids" type="hidden" value="${tradeTids}">
				<input id="phoneIds" type="hidden" class="phone" value="${phoneIds}">
				<input id="nickNameIds" type="hidden" class="nickName" value="${nickNameIds}">
				<input id="tradeTid" type="hidden">
				<input id="phoneId" type="hidden" class="phone">
				<input id="nickNameId" type="hidden" class="nickName">
				<!-- <div class="p_l25 h40 m_t15">
					<div class="c_384856 f_l h40 lh40 m_r10">
						插入标签:
					</div>
					<div class="f_l b_radius5 text-center m_r5 w100 h38 lh38 border_00a0e9 c_00a0e9 font16 tk cursor_" onclick="addConsignee('【收货人】')">
						收货人
					</div>
					<div class="f_l b_radius5 text-center m_r5 w100 h38 lh38 border_00a0e9 c_00a0e9 font16 tk cursor_" onclick="addConsignee('【订单编号】')">
						订单编号
					</div>
					<div class="f_l b_radius5 text-center m_r5 w100 h38 lh38 border_00a0e9 c_00a0e9 font16 tk cursor_" onclick="addConsignee('【买家昵称】')">
						买家昵称
					</div>
 					<div class="f_l b_radius5 text-center m_r5 w100 h38 lh38 border_00a0e9 c_00a0e9 font16 tk cursor_" onclick="addConsignee('【付款链接】')">
 						付款链接
 					</div>
				</div> -->
				<!-- <p class="c_ff6363 font14 m_t20 p_l25">
					提示：链接前后须有空格分隔，请勿删除前后空格。
				</p> -->
				<div class="p_l25 h40 m_t15">
					<div class="f_l lh40 c_384856">辅助工具：</div>
					<div
						class="tk border_00a0e9 short_url_btn c_00a0e9 short_url_btn f_l m_r15 w110 cursor_ h40 lh40 text-center b_radius5" onclick="getCursortPosition(document.getElementById('areaId'))">
						生成短网址</div>
				</div>
				<div class="p_l25 h50 m_t15">
					<div class="c_384856 f_l h50 lh50 m_r10">
						签名:
						<input id="qianMing" value="淘宝" type="hidden">
					</div>
					<div class="wrap f_l h50 m_r20">
					    <div class="z_5 nice-select w123 h45" name="nice-select">
					    	<input id="autographId" class="h50" type="text" value="淘宝" readonly>
					    	<ul id="signUl">
					    		<li class="custom_none"><a>淘宝</a></li>
					    		<li class="custom_none"><a>天猫</a></li>
					      		<li class="custom_none"><a>京东</a></li>
					    		<li class="custom"><a>自定义</a></li>
					    	</ul>
					  	</div>
					</div>
					<div class="f_l custom_hide display_none">
						<input class="border0 bgc_f4f6fa h50 lh50" id="signId" type="text" value="" placeholder="自定义签名" />
			    	</div>
				</div>
				
				
				
				<div class="h52 m_t5 p_l25 c_384856">
					<div class="f_l h50 lh50 m_r10">
						发送方式:
					</div>
					<input id="send_input" type="hidden" value="1" name="send_time_type">
					<div>
				    	<div id="lijiSend" class="m_t15 cursor_ time_show_unable group_check group_check1 w20 h20 border_d9dde5 b_radius5 f_l">
				    		<img style="width:1vw;" class="cursor_ group_check_" src="${ctx}/crm/images/black_check.png" />
				    	</div>
				    	<div class="f_l font14 text-left lh50 m_l10 m_r10">
				    		立即发送
				    	</div>
				    </div>
				    <div>
				    	<div id="dingshiSend" class="m_t15 cursor_ time_show group_check group_check1 w20 h20 border_d9dde5 b_radius5 f_l">
				    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
				    	</div>
				    	<div class="f_l font14 text-left lh50 m_l10 m_r10">
				    		定时发送
				    	</div>
				    </div>	
				    <input id="groupSingle" type="hidden">
				    <div class="time_hide display_none">
						<div class="f_l position_relative">
							<input class="bgc_f4f6fa border0 w170 p_l10 h40 m_r10" type="text" id="tser21"  readonly onclick="$.jeDate('#tser21',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm'})">
							<img class="position_absolute right20 top10" src="${ctx}/crm/images/date_copy.png" />
						</div>
			    	</div>
				</div>
				<div class="w232 h40 margin0_auto text-center font16 m_b25">
					<div onclick="sendMessage()" class="a f_l b_radius5 m_r30 w100 lh40 bgc_00a0e9  c_fff cursor_ sendMessage">
						确定发送
					</div>
					<div onclick="cancel()" class="f_l b_radius5 w100 h38 lh38 border_cad3df close_this c_cad3df cursor_">
						取消
					</div>
					
				</div>
			</div>
			<div class="w840 f_l m_l15 h665 bgc_fff">
				<div class="lh50 w100_ bgc_e3e7f0 text-center font16 c_384856">
					<input id="showType" type="hidden" value="${show_type}">
					<ul id="typeId" class="window_out">
							<li onclick="findTemplate('未付款提醒')" id="show_type1" class="w140 h50 f_l cursor_ bgc_fff">未付款提醒</li>
								<%-- <a href="${ctx}/crms/marketingCenter/list?type=未付款提醒"></a> --%>
							<li onclick="findTemplate('付款关怀')" id="show_type2" class="w140 h50 f_l cursor_">付款关怀</li>
								<%-- <a href="${ctx}/crms/marketingCenter/list?type=付款关怀"></a> --%>
							<li onclick="findTemplate('延迟发货')" id="show_type3" class="w140 h50 f_l cursor_">延迟发货</li>
								<%-- <a href="${ctx}/crms/marketingCenter/list?type=延迟发货"></a> --%>
							<li onclick="findTemplate('发货提醒')" id="show_type4" class="w140 h50 f_l cursor_">发货提醒</li>
								<%-- <a href="${ctx}/crms/marketingCenter/list?type=发货提醒"></a> --%>
							<li onclick="findTemplate('回款关怀')" id="show_type5" class="w140 h50 f_l cursor_">回款关怀</li>
								<%-- <a href="${ctx}/crms/marketingCenter/list?type=回款关怀"></a> --%>
							<li onclick="findSmsSendRecord()" id="show_type6" class="w140 h50 f_l cursor_">历史记录</li>
								<%-- <a href="${ctx}/crms/marketingCenter/list?type=历史记录"></a> --%>
							<li class="clear"></li>
					</ul>
				</div>
				<!--------详细数据-------->
				<div class="c_384856 font14 m_t15  overflow_auto h490">
					<table class="margin0_auto smsTemplate_table" id="tableD">
					  <%-- <c:forEach items="${pagi.list}" var="smsTemp">
					  	<tr class=" text-center" >
					    <td  class="w660 h100">${smsTemp.content}</td>
					    <td class="w145 h100">
					    	<div id="faSong" class="w100 margin0_auto b_radius5 h38 lh38 cursor_ border_00a0e9 c_00a0e9 tk" onclick="addContent('${smsTemp.content}')">
					    		<input id="faSongHide" type="hidden" value="${history}">
					    		使用
					    	</div>
					    </td>
					  </tr>
					  </c:forEach> --%>
					</table>
				</div>
				
				<!--------详细数据-------->
				
				
				<!--------分页-------->
	            <div class="h24 m_t22 font14 c_8493a8 m_b40 p_r15">
	                <div class="f_r w470 h24 l_h22 font12">
	                    <c:forEach items="${pagi.pageView}" var="pagiV">
							${pagiV}
					    </c:forEach>
	                </div>
	            </div>
			</div>
		</div>
	</div>
</div>

<!--选择所在地弹出框start-->
  <div class="rgba_000_5 w1920 h100_ display_none ChoiceArea" style="z-index:10;">
                      <div class="w750 p_b33 bgc_fff m_t100 m_l500 fasf">
						<div class="h50 p_t20 text-center font16">选择所在地区</div>
						<div class="h20 p_t20 text-center font16 display_none" id="payAttention" style="color:red">*地区最多选择三个!</div>
					
						<div class="font14 c_384856 p_l50 place_check">
							<!--华东-->
							<ul class="h50 gangaotai_ul_1">
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										上海
										<input type="hidden" value="上海">
									</div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										浙江<input type="hidden" value="浙江省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										江苏<input type="hidden" value="江苏省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										安徽<input type="hidden" value="安徽省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										江西<input type="hidden" value="江西省"></div>
								</li>
							</ul>

							<!--华北-->
							<ul class="h50 gangaotai_ul_1">
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										北京<input type="hidden" value="北京"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										天津<input type="hidden" value="天津"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										河北<input type="hidden" value="河北省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										山西<input type="hidden" value="山西省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										山东<input type="hidden" value="山东省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										内蒙古<input type="hidden" value="内蒙古自治区"></div>
								</li>
							</ul>

							<!--华中-->
							<ul class="h50 gangaotai_ul_1">
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										湖北<input type="hidden" value="湖北省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										湖南<input type="hidden" value="湖南省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										河南<input type="hidden" value="河南省"></div>
								</li>
							</ul>

							<!--华南-->
							<ul class="h50 gangaotai_ul_1">
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										广东<input type="hidden" value="广东省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										广西<input type="hidden" value="广西壮族自治区"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										福建<input type="hidden" value="福建省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										海南<input type="hidden" value="海南省"></div>
								</li>
							</ul>

							<!--东北-->
							<ul class="h50 gangaotai_ul_1">
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										辽宁<input type="hidden" value="辽宁省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										吉林<input type="hidden" value="吉林省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										黑龙江<input type="hidden" value="黑龙江省"></div>
								</li>
							</ul>

							<!--西北-->
							<ul class="h50 gangaotai_ul_1">
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										陕西<input type="hidden" value="陕西省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										新疆<input type="hidden" value="新疆维吾尔自治区"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										青海<input type="hidden" value="青海省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										宁夏<input type="hidden" value="宁夏回族自治区"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										甘肃<input type="hidden" value="甘肃省"></div>
								</li>
							</ul>

							<!--西南-->
							<ul class="h50 gangaotai_ul_1">
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										四川<input type="hidden" value="四川省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										云南<input type="hidden" value="云南省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										西藏<input type="hidden" value="西藏自治区"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										贵州<input type="hidden" value="贵州省"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										重庆<input type="hidden" value="重庆"></div>
								</li>
							</ul>

							<!--港澳台-->
							<ul class="h50 gangaotai_ul_1">
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										香港<input type="hidden" value="香港特别行政区"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										澳门<input type="hidden" value="澳门特别行政区"></div>
								</li>
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										台湾<input type="hidden" value="台湾省"></div>
								</li>
							</ul>

							<!--海外-->
							<ul class="h50 gangaotai_ul_1">
								<li class="f_l 1check_box w90 li_">
									<div
										class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
										海外<input type="hidden" value="海外"></div>
								</li>
							</ul>

						</div>
						<div class="h40 margin0_auto w225">
                                <div onclick="customRegion()" class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ save_area">
                                    	保存
                                </div>
                                <div class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ cancel_area">
                                    	取消
                                </div>
						</div>
					</div>
    </div> 
    
    
    <!---------------短网址生成弹窗--------------->
	<div style="height: 100vw;"
		class="w100_ rgba_000_5 position_absolute z_12 short_url top0 display_none">

		<div
			class="w740 bgc_fff margin0_auto position_fixed top200 left550  p_t20 p_l20 p_r20 p_b20">

			<div class="c_384856 text-center font18 h25 lh25">短网址生成</div>
			<div class="h40 p_l70 m_t40 m_b20">
				<%-- <div class="f_l m_r40 short_out">
					<div
						class="m_t10  cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
						<img class="cursor_ one_check_ display_none"
							src="${ctx}/crm/images/black_check.png" />
					</div>
					<div class="m_l10 f_l font14 c_384856 lh40">活动链接</div>
				</div> --%>
				<input type="hidden" name="linkType" id="linkType" value="店铺链接">
				<div class="margin0_auto  w300">
					<div class="f_l m_r40 short_out" onclick="linkDate('店铺链接');">
						<div
							class="m_t10  cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l">
							<img class="cursor_ w20 one_check_"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">店铺链接</div>
					</div>
					<div class="f_l short_out" onclick="linkDate('商品链接');">
						<div
							class="m_t10  cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l AddSpecified_link">
							<img class="cursor_ w20 one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">商品链接</div>
					</div>
				</div>
			</div>
			<!-- <input
				class="w570 short_in h40 bgc_f4f6fa border_0 m_l70 p_l10 m_b30"
				placeholder="请输入您需要转换的网址" /> -->
			<!-- <input class="w570 h40 bgc_f4f6fa border_0 m_l70 p_l10 m_b30 text-center" placeholder="请输入短链接名称！" id="linkName"/> -->
			<div id="storeshow"
				class="w570 short_in h40 font14 m_l70 text-center m_b30 c_384856">
				店铺链接直接点击确定按钮即可</div>
			<input
				class="w570 short_in h40 bgc_f4f6fa border_0 m_l70 p_l10 m_b30 display_none text-center"
				placeholder="商品ID" id="commodityId" onkeyup="this.value=this.value.replace(/\D/g, '')" onblur="this.value=this.value.replace(/\D/g, '')" />

			<div class="margin0_auto" style="width: 13vw; height: 3vw;">
				<div onclick="linkSubmit();"
					class="cursor_ m_r30 f_l save_short_url w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk b_radius5">
					确定</div>
				<div
					class="cursor_ f_l save_short_url w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk b_radius5">
					取消</div>
			</div>

			<div style="padding-left: 6vw;">
				<p class="font16 c_ff6363"></p>
				<p class="font14 c_ff6363">温馨提示：插入短链接时，请不要去掉短链接两边的空格，不然会造成短链接失效。</p>
			</div>

		</div>

	</div>
    
    <div id="xuanzeshangpinBox" class="ordersmsqf">
			<h3 class="clearfix">
				<i></i>
				<span>选择商品</span>
			</h3>
			
			<div class="xuanzeshangpinBox">
				<div class="xuanzeshangpinsearchBox clearfix">
					<div class="xuanzeshangpinSearchDiv clearfix">
						<label>
							商品ID
						</label>
						<input type="text" name="" id="numId" value="" />
					</div>
					<div class="xuanzeshangpinSearchDiv" style="margin-right:0;">
						<label>
							关键字
						</label>
						<input type="text" name="" id="gjztitle" value="" />
					</div>
					<div class="SearchRightDiv clearfix">
						<div class="xuanzeshangpinSearchRightDiv">
							<i></i>
							<label>只显示上架</label>
							<input type="hidden" name="shelves" id="shelves" value="0" />
						</div>
						<div class="xuanzeshangpinSearchRightDiv">
							<i></i>
							<label>只显示待上架</label>
							<input type="hidden" name="waitgrounding" id="waitgrounding" value="0" />
						</div>
						<div class="xuanzeshangpinSearchRightDiv">
							<i></i>
							<label>只显示导入订单关联商品</label>
							<input type="hidden" name="relation" id="relation" value="0" />
						</div>
						<a id='xuanzeshangpinSearch' href="javascript:;">搜索</a>
					</div>
					
				</div>	
				<div class="xuanzeshangpinTableBox clearfix">
					<div class="xuanzeshangpinLeftTable">
						<table class="tableLeftdead" border="0" cellspacing="0" cellpadding="0">
							<tr class="trone">
								<td class="tdone">
									<i class="qx"></i>
									<span>全选</span>
								</td>
								<td class="tdtwo">商品ID</td>
								<td class="tdtdree">宝贝图片</td>
								<td class="tdfour">宝贝名称</td>
								<td class="tdfive">金额</td>
							</tr>
						</table>
						<div class="tableLeftBody">
							<table class="leftTable" border="0" cellspacing="0" cellpadding="0">
<!-- 								<tr class="trtwo"> -->
<!-- 									<td class="tdone"> -->
<!-- 										<i data-id="1" class="dx"></i> -->
<!-- 									</td> -->
<!-- 									<td class="tdtwo">1</td> -->
<!-- 									<td class="tdtdree"> -->
<!-- 										<img src="images/u399.png"/> -->
<!-- 									</td> -->
<!-- 									<td class="tdfour">名称名称名称名名称</td> -->
<!-- 									<td class="tdfive">99</td> -->
<!-- 								</tr> -->
								
								
								
								
							</table>
							<div id="xuanzeshangpinpage">
								
							</div>
						</div>
					</div>	
					<div class="xuanzeshangpinRightTable">
						<div class="RightTableTop">
							<div class="yxDiv">
								已选商品 <span>0</span>个
							</div>	
							<a id="removesp" href="javascript:;">删除选中商品</a>
						</div>
						<table class="rightTableHead" border="0" cellspacing="0" cellpadding="0">
							<tr class="trone">
								<td class="tdone">
									<i class="qx"></i>
									<span>全选</span>
								</td>
								<td class="tdtwo">商品ID</td>
								<td class="tdthree">宝贝图片</td>
								<td class="tdfour">宝贝名称</td>
								<td class="tdfive">金额</td>
							</tr>
						</table>
						<div class="rightTableBody">
							<table  border="0" cellspacing="0" cellpadding="0">
								
								
							</table>
						</div>
					</div>
				</div>
				<div class="btn">
					<a class="qd" href="javascript:;">确定</a>
					<a class="qx" href="javascript:;">取消</a>
				</div>
			</div>
			
		</div>
        <input type="hidden" id="SPID" value="">   
        <input type="hidden" id="GJZ" value="">
        <input type="hidden" id="SHOWSJ" value="">
        <input type="hidden" id="SHOWDSJ" value="">
        <input type="hidden" id="GLSP" value="">
<!--选择所在地弹出框end-->	
	<div id="showSendBox">
		<h3><img class="fl" src="${ctx}/crm/images/showBoxIcon.png"><span class="fl">温馨提示</span></h3>
		<p>当前短信字数为<strong></strong>字，每条短信计费 <em></em> 条</p>
		<div class="showSendBoxBtn">
			<a href="javascript:;" class="qd">确定发送</a>
			<a href="javascript:;" class="qx">取消</a>
		</div>
	</div>
	<input type="hidden" id="urlVal" value="${ctx}">
<!-- 	<div class="markBg"></div>			 -->
<%@ include file="/WEB-INF/views/crms/header/itemUtils.jsp" %>
</body>

<script type="text/javascript">
$(function(){
	if($('#numIid').val()){
		$('#xzspImg input').val('');
		$('#xzspImg img').hide();
		var len=$('#numIid').val().split(',').length;
		$('#xzspBtn').text('已选择'+len+'件商品');
	}else{
		$('#xzspImg input').val('1');
		$('#xzspImg img').show();
		
		$('#xzspBtn').text('自定义');
	}
	if($('#stateId').val()){
		$('#ssdqImg input').val('');
		$('#ssdqImg img').hide();
		var len=$('#stateId').val().split(',').length;
		$('#ssdqBtn').text('已选择'+len+'个地区');
	}else{
		$('#ssdqImg input').val('1');
		$('#ssdqImg img').show();
		
		$('#ssdqBtn').text('自定义');
	}
})
$(".one_check_only_555").click(function(){
	$(this).children(".one_check_").show();
	$(this).parent().siblings().children().children(".one_check_").hide();
});
<!-- 割---------------------------------------------- -->
	$(".area_select_").click(function(){
		$(".ChoiceArea").show();
		if($('#stateId').val()){
			var locality = $("#stateId").val(); 

			var localityList = locality.split(",");
			var getprovince = $(".place_check").children().find('.li_').children().next().children('input');
			for(var i=0;i<localityList.length;i++){
				for(var j=0;j<getprovince.length;j++){
					if(getprovince.eq(j).val()==localityList[i]){
						getprovince.eq(j).parent().prev().addClass("bgc_check_blue");
					}
				}
			}
		}else{
			$('.1check_box_2').removeClass('bgc_check_blue');
		}
	});

	$(".save_area").click(function(){
		  $(".ChoiceArea").hide();
	});

	$(".cancel_area").click(function(){
		  $(".ChoiceArea").hide();
		  $('.1check_box_2').removeClass('bgc_check_blue');
	});

	
	$(".1check_box").click(function(){
		$(this).children(".1check_box_1").toggleClass("bgc_check_blue");
		$(this).parent().siblings().children().children(".1check_box_1").removeClass("bgc_check_blue");
	});

	
	$(".1check_box_2").click(function(){
		/* alert($(".bgc_check_blue").length) */
// 		if($(".gangaotai_ul_1").children(".li_").children(".bgc_check_blue").length>2){
// 			$(this).removeClass("bgc_check_blue");
// 			$("#payAttention").show();
// 		} else{
// 			$(this).toggleClass("bgc_check_blue");
// 			$("#payAttention").hide();
// 		}
		if($(this).hasClass('bgc_check_blue')){
			$(this).removeClass('bgc_check_blue');
		}else{
			$(this).addClass('bgc_check_blue');
		}
	});
	
	
	
	$("#areaId").on("keyup",function(){
		addLength();
	});
	//订单状态的选择
	$('#statusUl li').click(function(){
		$('#status').val($(this).attr('value'));
		$('#statusId').val($(this).attr('value'));
	});
	//评价状态的选择
	$('#rateStatusUl li').click(function(){
		$('#rateStatus').val($(this).attr('value'));
	});
	//订单来源的选择
	$('#orderFromUl li').click(function(){
		$('#orderFrom').val($(this).attr('value'));
	})
	//退款状态的选择
	$('#refundStatusUl li').click(function(){
		$('#refundStatus').val($(this).attr('value'));
	})
	//预售状态的选择
	$('#step01 li').click(function(){
		$('#step01').val($(this).attr('value'));
	})
	$('#step02 li').click(function(){
		$('#step02').val($(this).attr('value'));
	})
	//短信过滤的选择
	$('#smsFileUl li').click(function(){
		$('#smsFile').val($(this).attr('value'));
	})
	//全选全不选效果
	$('.quan_xuan').click(function(){
		var cs = $('.quan_xuan').children().attr('style');
		if(cs == null || cs=="display: none;"){
			$('#send_show_1').find('.dan_xuan').hide();
			cs = $('#checkAllId').hide;
		}else{
			$('#send_show_1').find('.dan_xuan').show();
			cs = $('#checkAllId').show();
		}
	});
	//选择短信模板
	function findTemplate(type){
		$('#tableD').empty();
		$('#smsType').val(type);
		var url = "${ctx}/crms/orderCenter/smsTemplate";
		$.post(url,{"type":type},function(data){
			//历史使用记录 注释：需要展示此订单的发送记录，不是模板使用记录
			/* var historyTemplates = data.historyTemplates;
			var appendHisTemp = "";
			$.each(historyTemplates,function(i,result){
				appendHisTemp = "<tr class=' text-center'><td class='w660 h100'>"+result.content+"</td><td class='w145 h100'><div id='smsId"+result.templateId+"' onclick='addContent("+result.templateId+")' class='w100 margin0_auto b_radius5 h38 lh38 cursor_ radius10 border_00a0e9 c_00a0e9 tk'>使用<input id='content' type='hidden' value='"+result.content+"'></div></td></tr>";
				$('#tableD').append(appendHisTemp);
			}); */
			//短信模板
			var smsTemplates = data.smsTemplates;
			var appendTemp = "";
			$.each(smsTemplates,function(i,result){
				appendTemp = "<tr class=' text-center'><td class='w660 h100'>"+result.content+"</td><td class='w145 h100'><div id='smsId"+result.id+"' onclick='addContent("+result.id+")' class='w100 margin0_auto b_radius5 h38 lh38 cursor_ radius10 border_00a0e9 c_00a0e9 tk'>使用<input id='content' type='hidden' value='"+result.content+"'></div></td></tr>";
				$('#tableD').append(appendTemp);
			});
		},'json');
	}
	
	//根据订单编号和卖家查询短信发送记录
	function findSmsSendRecord(){
		$('#tableD').empty();
		var tradeTid = $('#tradeTid').val();
		var url = "${ctx}/crms/findSendRecordByOid";
		$.post(url,{"tradeTid":tradeTid},function(data){
			if(data.flag){
				//已选短信历史发送记录
				var sendRecords = data.sendRecordList;
				var appendRecord = "";
				$.each(sendRecords,function(i,result){
					appendRecord = "<tr class=' text-center'><td class='w660 h100'>"+result.content+"</td><td class='w145 h100'><div id='smsId"+i+"' onclick='addSmsContent("+i+")' class='w100 margin0_auto b_radius5 h38 lh38 cursor_ radius10 border_00a0e9 c_00a0e9 tk'>使用<input id='content' type='hidden' value='"+result.content+"'></div></td></tr>";
					$('#tableD').append(appendRecord);
				});
			}else{
				$('#tableD').append(data.message);
			}
			
		},'json');
	};
	//点击使用将历史发送记录添加到内容框中
	function addSmsContent(id){
		$('.text_area').val('');
		var smsId = $('#smsId'+id);
		var smsContent = smsId.children().val();
		var lastIndex = smsContent.lastIndexOf('退订回');
		var content = smsContent.substring(0,lastIndex);
		$('.text_area').val(content);
		addLength();
	}
	
	//提交表单查询
	function formSubmit(FormId){
		var styleVal = $(".more_detail").attr("style");
		if(styleVal != null && styleVal != '' && styleVal !='undefined'){
			$("#showMore").val(styleVal);
		}
		$('.chaxun_form').addClass('display_none');
		document.getElementById(FormId).submit();
	}
	//重置按钮
	function reset(){
		location.href = "${ctx}/crms/marketingCenter/list";
	}
	
	/* 点击单一筛选条件切换视图 */
	$(function(){
		var message = $('#message').val();
		if(message == 1){
			$(".sdg").show();
			location.href = "${ctx}/crms/list?label=1";
		}
		
		
	 var showName = $('#showName').val();
	 if(showName == "1"){
		 $('#show_1').removeClass("bgc_e3e7f0").addClass("bgc_fff");
		 $('#show_2').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_3').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_4').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_5').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_6').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_7').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_8').addClass("bgc_e3e7f0").removeClass("bgc_fff");
	 }else if(showName == "2"){
		 $('#show_2').removeClass("bgc_e3e7f0").addClass("bgc_fff"); 
		 $('#show_1').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_3').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_4').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_5').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_6').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_7').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_8').addClass("bgc_e3e7f0").removeClass("bgc_fff");
	 }else if(showName == "3"){
		 $('#show_3').removeClass("bgc_e3e7f0").addClass("bgc_fff");
		 $('#show_1').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_4').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_2').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_5').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_6').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_7').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_8').addClass("bgc_e3e7f0").removeClass("bgc_fff");
	 }else if(showName == "4"){
		 $('#show_4').removeClass("bgc_e3e7f0").addClass("bgc_fff");
		 $('#show_1').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_3').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_5').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_2').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_6').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_7').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_8').addClass("bgc_e3e7f0").removeClass("bgc_fff");
	 }else if(showName == "5"){
		 $('#show_5').removeClass("bgc_e3e7f0").addClass("bgc_fff");
		 $('#show_1').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_3').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_4').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_6').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_2').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_7').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_8').addClass("bgc_e3e7f0").removeClass("bgc_fff");
	 }else if(showName == "6"){
		 $('#show_6').removeClass("bgc_e3e7f0").addClass("bgc_fff");
		 $('#show_1').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_3').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_4').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_5').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_7').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_2').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_8').addClass("bgc_e3e7f0").removeClass("bgc_fff");
	 }else if(showName == "7"){
		 $('#show_7').removeClass("bgc_e3e7f0").addClass("bgc_fff");
		 $('#show_1').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_3').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_4').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_5').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_6').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_8').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_2').addClass("bgc_e3e7f0").removeClass("bgc_fff");
	 }else if(showName == "8"){
		 $('#show_8').removeClass("bgc_e3e7f0").addClass("bgc_fff");
		 $('#show_1').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_3').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_4').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_5').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_6').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_7').addClass("bgc_e3e7f0").removeClass("bgc_fff");
		 $('#show_2').addClass("bgc_e3e7f0").removeClass("bgc_fff");
	 }
	 var statusVal = $('#status').val();
	 //回显订单状态
	 if(statusVal == 'ALL' ||　statusVal == 'TOTAL'){
		 $('#statusId').val("全部");
	 }else if(statusVal == 'WAIT_BUYER_PAY'){
		 $('#statusId').val("等待买家付款");
	 }else if(statusVal == 'WAIT_SELLER_SEND_GOODS'){
		 $('#statusId').val("等待卖家发货");
	 }else if(statusVal == 'WAIT_BUYER_CONFIRM_GOODS'){
		 $('#statusId').val("等待买家确认收货");
	 }else if(statusVal == 'TRADE_BUYER_SIGNED'){
		 $('#statusId').val("卖家已签收(货到付款专用)");
	 }else if(statusVal == 'TRADE_FINISHED'){
		 $('#statusId').val("交易成功");
	 }else if(statusVal == 'CLOSED'){
		 $('#statusId').val("交易关闭");
	 }else{
		 $('#statusId').val("全部");
	 }
	 //回显评价状态
	 var rateStatusVal = $('#rateStatus').val();
	 if(rateStatusVal == '全部'){
		 $('#rateStatusId').val("全部");
	 }else if(rateStatusVal == '买家未评'){
		 $('#rateStatusId').val("买家未评");
	 }else if(rateStatusVal == '卖家未评'){
		 $('#rateStatusId').val("卖家未评");
	 }else if(rateStatusVal == '买家已评，卖家未评'){
		 $('#rateStatusId').val("买家已评，卖家未评");
	 }else if(rateStatusVal == '买家未评，卖家已评'){
		 $('#rateStatusId').val("买家未评，卖家已评");
	 }else if(rateStatusVal == '双方已评价'){
		 $('#rateStatusId').val("双方已评价");
	 }
	 
	 //回显订单来源
	 var orderFromVal = $('#orderFrom').val();
	 if(orderFromVal == 'ALL'){
		 $('#orderFromId').val("全部");
	 }else if(orderFromVal == 'TAOBAO'){
		 $('#orderFromId').val("PC端");
	 }else if(orderFromVal == 'WAP,WAP'){
		 $('#orderFromId').val("手机");
	 }else if(orderFromVal == 'import'){
		 $('#orderFromId').val("导入");
	 }
	 
	 //回显退款状态
	 var refundStatusVal = $('#refundStatus').val();
	 if(refundStatusVal == 'ALL'){
		$('#refundStatusId').val("全部");
	 }else if(refundStatusVal == 'WAIT_SELLER_AGREE'){
		$('#refundStatusId').val("买家申请退款，等待卖家消息");
	 }else if(refundStatusVal == 'WAIT_BUYER_RETURN_GOODS'){
		$('#refundStatusId').val("等待买家退货");
	 }else if(refundStatusVal == 'WAIT_SELLER_CONFIRM_GOODS'){
		$('#refundStatusId').val("买家已经退货，等待卖家确认收货");
	 }else if(refundStatusVal == 'SELLER_REFUSE_BUYER'){
		$('#refundStatusId').val("卖家拒绝退款");
	 }else if(refundStatusVal == 'CLOSED'){
		$('#refundStatusId').val("退款关闭");
	 }else if(refundStatusVal == 'SUCCESS'){
		$('#refundStatusId').val("退款成功");
	 }else if(refundStatusVal == 'TOTAL'){
		 $('#refundStatusId').val("全部");
	 }
	 
	 //回显预售状态
	 var step01Val = $('#step01').val();
	 var step02Val = $('#step02').val();
	 if(step01Val == "全部"){
		 $('step01Id').val("全部");
	 }else if(step01Val == "预售"){
		 $('step01Id').val("预售");
	 }else if(step01Val == "非预售"){
		 $('step01Id').val("非预售");
	 }
	 if(step02Val == 'ALL'){
		 $('step02Id').val("全部");
	 }else if(step02Val == 'RONT_NOPAID_FINAL_NOPAID'){
		 $('step02Id').val("定金未付尾款未付");
	 }else if(step02Val == 'FRONT_PAID_FINAL_NOPAID'){
		 $('step02Id').val("定金已付尾款未付");
	 }else if(step02Val == 'FRONT_PAID_FINAL_PAID'){
		 $('step02Id').val("定金和尾款都付");
	 }
	 
	 //回显短信过滤天数
	 var smsFileVal = $('#smsFile').val();
	 if(smsFileVal == '0'){
		 $('#smsFileId').val("不过滤");
	 }else if(smsFileVal == '2'){
		 $('#smsFileId').val("一天");
	 }else if(smsFileVal == '3'){
		 $('#smsFileId').val("两天");
	 }else if(smsFileVal == '4'){
		 $('#smsFileId').val("三天");
	 }else if(smsFileVal == '5'){
		 $('#smsFileId').val("四天");
	 }else if(smsFileVal == '6'){
		 $('#smsFileId').val("五天");
	 }else if(smsFileVal =='1'){
		 $('#smsFileId').val("当天");
	 }
	 
	 //隐藏显示更多条件
	/*  var hideCondiVal = $('#hideConditionId').val();
	 if(hideCondiVal == '1'){
		 $('.more_detail_btn').show();
	 }else{
		 $('.more_detail_btn').hide();
	 } */

	
	});
	//隐藏预加载图标
	window.onload=function(){
		$(".sdg").hide();
	}
	//订单标示，小旗子
	function orderFlag(flag){
		var flagVal = $('#flagId').val();
		var flagArr;//根据","切分后得到的数组
		var flags = '';//flagId最终要放入的值
		if(flagVal == ''){
			flags = flag;
		}else{
			flagArr = flagVal.split(',');
			var flagIndex = flagArr.indexOf(flag + '');
			if(flagIndex > -1){
				flagArr.splice(flagIndex,1);
				for(var i = 0;i < flagArr.length;i++){
					if(flags == ''){
						flags = flagArr[0];
					}else{
						flags = flags + "," + flagArr[i];
					}
				}
			}else{
				flags = flagVal + "," + flag;
			}
		}
		$('#flagId').val(flags);
	};
	
	//页面回显条件 （小旗子）
	$(function(){
		var flagVal = $('#flagId').val();
		if(flagVal == ''){
			$('#hui').children().show();
			$('#hong').children().show();
			$('#huang').children().show();
			$('#lv').children().show();
			$('#lan').children().show();
			$('#zi').children().show();
			$('#flagId').val('0,1,2,3,4,5');
		}else{
			var flagArr = flagVal.split(",");
			for(var i = 0; i < flagArr.length; i++){
				if(flagArr[i] == 0){
					$('#hui').children().show();
				}else if(flagArr[i] == 1){
					$('#hong').children().show();
				}else if(flagArr[i] == 2){
					$('#huang').children().show();
				}else if(flagArr[i] == 3){
					$('#lv').children().show();
				}else if(flagArr[i] == 4){
					$('#lan').children().show();
				}else if(flagArr[i] == 5){
					$('#zi').children().show();
				}
			}
		}
		
		//回显多条件查询
// 		var showMoreVal = $("#showMore").val();
// 		if(showMoreVal == "display: block;"){
// 			$(".more_detail").removeClass("display_none");
// 		}else{
// 			$(".more_detail").addClass("display_none");
// 		}
		
	});
	
	
	//定位光标的位置，将内容分割成两个部分
	var startContent,endContent;
	function getCursortPosition(obj){		
		if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
	        var startPos = obj.selectionStart,
	            endPos = obj.selectionEnd,
	            cursorPos = startPos,
	            tmpStr = obj.value;
	        startContent =tmpStr.substring(0, startPos);
	        endContent =tmpStr.substring(endPos, tmpStr.length);
	    } 
	}

	/* 点击收货人时将收货人添加到短信内容框内 */
	function addConsignee(button){
		var content= document.getElementById('areaId');
		if(content.value.split(button).length-1>0){
			return ;
		}else{
			getCursortPosition(content);
			$(".text_area").val(startContent+button+endContent);
		}
		addLength();
	}
		$("#signUl li").click(function(){
			var content= document.getElementById("areaId").value;
		    var liString =  $(this).find("a").eq(0).text();
			//判断短信模板选择中的文本域是否有值
			if(content == null){
				if(liString == "自定义"){
					$('#qianMing').val('');
					$('#signId').keyup(function(){
						
						var cusVal = $('#signId').val();
						alert("cusVal.is:"+ cusVal);
						$('.text_area').val("【"+cusVal+"】");
						addLength();
						$('#qianMing').val(cusVal);
		    		});
				}else{
					$('.text_area').val("【"+liString+"】");
					$('#qianMing').val(liString);
				}
			}else{
				if(liString == "自定义"){
					$('#qianMing').val('');
					var cusVal = $('#signId').val();
					if(cusVal == ''){
						$('#signId').keyup(function(){
							cusVal = $('#signId').val();
							$('.text_area').val("【"+cusVal+"】"+content.replace("【淘宝】","").replace("【天猫】","").replace("【京东】","").replace("【"+cusVal+"】",""));
							if(cusVal == ''){
								$(".tishi_2").show();
								$(".tishi_2").children("p").text("短信签名不能为空")
								setTimeout(function(){ 
									$(".tishi_2").hide()
								},3000)
								return ;
							}
							$('#qianMing').val(cusVal);
							addLength();
			    		});
					}else{
						$('.text_area').val("【"+cusVal+"】"+content.replace("【淘宝】","").replace("【天猫】","").replace("【京东】","").replace("【"+cusVal+"】",""));
						$('#qianMing').val(cusVal);
						addLength();
					}
				}else{
					var cusVal = $('#signId').val();
					var signVal = "【"+liString+"】";
					$('.text_area').val("【"+liString+"】"+ content.replace("【淘宝】","").replace("【天猫】","").replace("【京东】","").replace("【"+cusVal+"】","").replace("【】",""));
					$('#qianMing').val(liString);
				}
			}
			addLength();
		 });
	/*判断短信输入框内容长度，修改计费条数 */
	function addLength(){
		var length=$(".text_area").val().length + 4;
		if(length>70){
			document.getElementById("contePrice").innerText=Math.ceil(length/67);			
		}else if(length<=70){
			document.getElementById("contePrice").innerText=1;
		}
		var textContent = $("#areaId").val();
		var unsubscribeMSG = $("#unsubscribeMSG").val();
		var endLength = textContent.length + 3 + unsubscribeMSG.length;
		$("#inputNum").html(endLength);
// 		document.getElementById("inputNum").innerText=length;
	};

	//点击使用添加模板到编辑框，并修改短信字数
	/* $('#faSong').click(function(){
		$("#tableD tr").click(function() {
	        var cont = $(this).children('td').eq(0).text();
			$('#areaId').val(cont);
	    });
	}); */
	function addContent(id){
		var defaultSign = $('#autographId').val();
		var customSign = $('#signId').val();
		if(defaultSign == '自定义'){
			$('.text_area').val("【"+customSign + "】");
		}else{
			$('.text_area').val("【"+defaultSign + "】");
		}
		var smsId = $('#smsId'+id);
		var smsContent = smsId.children().val();
		var content = $('.text_area').val();
		$('.text_area').val(content + smsContent);
		$('#smsTempId').val(id);
		addLength();
	}
	//点击发送短信，将当前数据存放到选择短信模板页面中
	$('#send_show_1').children('.check_all').find('.send_message').click(function(){
		$('#tableD').empty();
		$('#groupSingle').val('1');
		$('#orderNum').text('1');
		$('#show_type1').addClass('bgc_fff');
		$('#show_type2').removeClass('bgc_fff');
		$('#show_type3').removeClass('bgc_fff');
		$('#show_type4').removeClass('bgc_fff');
		$('#show_type5').removeClass('bgc_fff');
		$('#show_type6').removeClass('bgc_fff');
		var tradeId = $(this).find('.trade_id').val();
		var nickName = $(this).parent().find('.nick_name').text();
		var phone = $(this).parent().find('.phone_text').val();
		$('#tradeTid').val(tradeId);
		$('#nickNameId').val(nickName);
		$('#phoneId').val(phone);
		$('#smsType').val("未付款提醒");
		var type = "未付款提醒";
		var url = "${ctx}/crms/orderCenter/smsTemplate";
		$.post(url,{"type":type},function(data){
			var smsTemplates = data.smsTemplates;
			var appendTemp = "";
			$.each(smsTemplates,function(i,result){
				appendTemp = "<tr class=' text-center'><td class='w660 h100'>"+result.content+"</td><td class='w145 h100'><div id='smsId"+result.id+"' onclick='addContent("+result.id+")' class='w100 margin0_auto b_radius5 h38 lh38 cursor_ radius10 border_00a0e9 c_00a0e9 tk'>使用<input id='content' type='hidden' value='"+result.content+"'></div></td></tr>";
				$('#tableD').append(appendTemp);
			});
		},"json");
	});
	
	//获取所有的发送对象的编号，放到一个input里
	function sendAllSms(){
		$('#groupSingle').val('2');
		$('#tableD').empty();
		//禁止滚动条
		  $(document.body).css({
		    "overflow-x":"hidden",
		    "overflow-y":"hidden"
		  });
		$(".order_mail_window").show();
		$('#orderNum').text($('#totalOrderNum').val());
		var dataTid = $('#tradeTids').val();
		var dataNickName = $('#nickNameIds').val();
		var dataPhone = $('#phoneIds').val();
		var totalCount = $('#totalSizeId').val();
		if(totalCount == '0'){
			$('#orderNum').text(0);
		}else{
			$('#orderNum').text(totalCount);
		}
		$('#tradeTid').val(dataTid);
		$('#nickNameId').val(dataNickName);
		$('#phoneId').val(dataPhone);
		$('#smsType').val("未付款提醒");
		
		var type = "未付款提醒";
		var url = "${ctx}/crms/orderCenter/smsTemplate";
		$.post(url,{"type":type},function(data){
			var smsTemplates = data.smsTemplates;
			var appendTemp = "";
			$.each(smsTemplates,function(i,result){
				appendTemp = "<tr class=' text-center'><td class='w660 h100'>"+result.content+"</td><td class='w145 h100'><div id='smsId"+result.id+"' onclick='addContent("+result.id+")' class='w100 margin0_auto b_radius5 h38 lh38 cursor_ radius10 border_00a0e9 c_00a0e9 tk'>使用<input id='content' type='hidden' value='"+result.content+"'></div></td></tr>";
				$('#tableD').append(appendTemp);
			});
		},"json");
	};
	//获取选中的发送对象的编号，放到一个input里
	function sendCheckSms(){
		$('#tableD').empty();
		$('#groupSingle').val('1');
		var dataTid;
		var dataNickName;
		var dataPhone;
		var checkNumber = 0;
		if($('#send_show_1').children('.check_all').length > 0){
			for(var i = 0;i<$('#send_show_1').children('.check_all').length;i++){
				if($('#send_show_1').children('.check_all').eq(i).find('.dan_xuan').attr('style') == "display: inline;"){
					checkNumber += 1;
					if(dataPhone != null){
						dataPhone = dataPhone + "," + $('#send_show_1').children('.check_all').eq(i).find('.phone_text').val();
					}else{
						dataPhone = $('#send_show_1').children('.check_all').eq(i).find('.phone_text').val();
					}
					if(dataTid != null){
						dataTid = dataTid + "," + $('#send_show_1').children('.check_all').eq(i).find('.trade_id').val();
					}else{
						dataTid = $('#send_show_1').children('.check_all').eq(i).find('.trade_id').val();
					}
					if(dataNickName != null){
						dataNickName = dataNickName + "," + $('#send_show_1').children('.check_all').eq(i).find('.nick_name').text();
					}else{
						dataNickName = $('#send_show_1').children('.check_all').eq(i).find('.nick_name').text();
					}
				}
			}
				$('#orderNum').text(checkNumber);
			if(checkNumber < 1){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("请选中订单")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
			}else{
				//禁止滚动条
				  $(document.body).css({
				    "overflow-x":"hidden",
				    "overflow-y":"hidden"
				  });
				$(".order_mail_window").show();
			}
		}else{
		}
		$('#tradeTid').val(dataTid);
		$('#nickNameId').val(dataNickName);
		$('#phoneId').val(dataPhone);
		$('#smsType').val("未付款提醒");
		var type = "未付款提醒";
		var url = "${ctx}/crms/orderCenter/smsTemplate";
		$.post(url,{"type":type},function(data){
			var smsTemplates = data.smsTemplates;
			var appendTemp = "";
			$.each(smsTemplates,function(i,result){
				appendTemp = "<tr class=' text-center'><td class='w660 h100'>"+result.content+"</td><td class='w145 h100'><div id='smsId"+result.id+"' onclick='addContent("+result.id+")' class='w100 margin0_auto b_radius5 h38 lh38 cursor_ radius10 border_00a0e9 c_00a0e9 tk'>使用<input id='content' type='hidden' value='"+result.content+"'></div></td></tr>";
				$('#tableD').append(appendTemp);
			});
		},"json");
	};
	
	//发送时间设置内容
	$("#lijiSend").click(function(){
		$("#send_input").val('1');
// 		$(this).children('img').show();
		
	});
	$("#dingshiSend").click(function(){
		$("#send_input").val('2');
		var timestamp = Date.parse(new Date())+1800000;
		$("#tser21").val(show(timestamp));
// 		$(this).children('img').show();
	});
	//获取转换后的时间
	function show(timestamp){
		var nowDate = new Date(timestamp);
		var nowYear = nowDate.getFullYear();
		var nowMonth = nowDate.getMonth()+1;
		var nowDay = nowDate.getDate();
		var nowTime = nowDate.getHours() + ":" + nowDate.getMinutes();
		var now = nowYear + '-' +    (nowMonth<10 ? '0' : '') + nowMonth + '-' +    (nowDay<10 ? '0' : '') + nowDay + ' ' + nowTime;
   		return now;
	}
	
	//调用短信接口，发送短信
	function sendMessage(){
		var queryKey = $('#queryKey').val();
		
		var content = $('#areaId').val() + "退订回N";
		var type = $('#smsType').val();
		var autograph = $('#autographId').val();
		var sendTime = $('#tser21').val();
		var send_time_type = $('#send_input').val();
		var unsubscribeMSGVal = $('#unsubscribeMSG').val();
		var signVal = $('#qianMing').val();
		var activityName = $('#activityId').val();
		var qianming=$('#qianMing').val();
		var signId=$('#signId').val();
		var regName=content.match(/(【)([\S\s\t]*?)(】)|(\[)([\S\s\t]*?)(\])/g);
		var num=content.match(/\[|\]|【|】/g);
		var reEsIndex = /(【)|(\[)/g;

		if(num==null){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text('短信文案中无签名')
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return;
		}else if(Math.floor(num.length/2)>1){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text('短信文案中不能出现两个【】或者[]请确认短信文案')
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return;
		}else if(regName[0].indexOf('【'+qianming+'】')==-1){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text('文案签名与渠道签名不符')
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return;
		}else if(content.search(reEsIndex)>0){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text('签名必须放在文案开头')
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return;
		}else if(qianming==''){
			if(signId==''){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text('自定义签名不能为空')
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},2000)
				return;
			}
		}
		//获取当前时间后半小时，并转换
		var tser01Val = $("#tser21").val();// 定时发送的时间
		var minDate = minDateJudge();
		var newDate = new Date(minDate);
		var setDate = new Date(tser01Val);
		if($('#send_input').val()==2){
			if(tser01Val != "" && setDate<newDate){
				$(".tishi_2").show();
	    		$(".tishi_2").children("p").text("定时发送时间应设置在半小时以后！")
	    		setTimeout(function(){ 
	    			$(".tishi_2").hide()
	    		},3000);
	    		return;
			}
			
		}
		$('#showSendBox').show();
		$('.markBg').show();
		$('#showSendBox p strong').text($('#inputNum').text());
		$('#showSendBox p em').text($('#contePrice').text());
		
		
	};
	$(function(){
		
		$('#showSendBox .qd').click(function(){
			var _this=$(this);
			if(_this.hasClass('jz'))return;
			_this.addClass('jz');
			var queryKey = $('#queryKey').val();
			var content = $('#areaId').val() + "退订回N";
			var type = $('#smsType').val();
			var autograph = $('#autographId').val();
			var sendTime = $('#tser21').val();
			var send_time_type = $('#send_input').val();
			var unsubscribeMSGVal = $('#unsubscribeMSG').val();
			var signVal = $('#qianMing').val();
			var activityName = $('#activityId').val();

			if(send_time_type == '2'){
				if(sendTime == ''){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("请设置定时发送时间!")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
					return;
				}
// 				var date = new Date().getTime()/1000;//获取当前时间  
// 				var resultDate = new Date(sendTime).getTime()/1000;
// 				var s1 = date.getTime(), s2 = resultDate.getTime();
// 				var total = (s2 - s1) / 1000;
// 				var day = parseInt(total / (24 * 60 * 60));//计算整数天数
// 				var afterDay = total - day * 24 * 60 * 60;//取得算出天数后剩余的秒数
// 				var hour = parseInt(afterDay / (60 * 60));//计算整数小时数
// 				var afterHour = total - day * 24 * 60 * 60 - hour * 60 * 60;//取得算出小时数后剩余的秒数
// 				var min = parseInt(afterHour / 60);//计算整数分
				
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("正在保存中,请勿重复提交,请在订单发送记录页面查看发送进度");
				setTimeout(function(){ 
// 					window.location.href = "${ctx}/msgSendRecord/toSendRecord";
				},3000)
			}else if(send_time_type == '1'){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("正在发送中,请勿重复提交,请在订单发送记录页面查看发送进度");
				setTimeout(function(){ 
// 					window.location.href = "${ctx}/msgSendRecord/orderSendRecord";
				},3000)
			}
			$('.sendMessage').removeAttr('onclick');
			/* setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000) */
			var groupSingle = $('#groupSingle').val();
			var url = "${ctx}/order/singleSms";
			if(groupSingle == '1'){
				var tids = $('#tradeTid').val();
				var nickNames = $('#nickNameId').val();
				var phones = $('#phoneId').val();
				url = "${ctx}/order/singleSms";
			}else if(groupSingle == '2'){
				url = "${ctx}/crms/groupSms";
				var totalCount = $('#totalSizeId').val();
			}
			//点击确认发送，将模板添加到历史使用模板中
			var smsTempId = $('#smsTempId').val();
			$.post(url,{"totalCount":totalCount,"signVal":signVal,"smsTempId":smsTempId,"unsubscribeMSGVal":unsubscribeMSGVal,"send_time_type":send_time_type,"sendTime":sendTime,
				"autograph":autograph,"content":content,"type":type,"activityName":activityName,"queryKey":queryKey,"phones":phones,"tids":tids,"nickNames":nickNames},function(data){
				
				
		
				
				$('.sendMessage').attr('onclick','sendMessage()');
				$(".tishi_2").show();
				$(".tishi_2").children("p").text(data.msg)
				//后台是否成功，成功则跳转，否则留在本页面
				var skip = data.skip;
				if(skip == 1){
					if(send_time_type == '2'){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text(data.msg);
						setTimeout(function(){ 
		 					window.location.href = "${ctx}/msgSendRecord/toSendRecord";
		 					_this.removeClass('jz');
						},3000)
					}else if(send_time_type == '1'){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text(data.msg);
						setTimeout(function(){ 
		 					window.location.href = "${ctx}/msgSendRecord/orderSendRecord";
		 					_this.removeClass('jz');
						},3000)
					}
				}else if(skip == 0){
					if(send_time_type == '2'){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text(data.msg);
						setTimeout(function(){ 
							window.location.href = "${ctx}/crms/marketingCenter/list";
							_this.removeClass('jz');
						},3000)
					}else if(send_time_type == '1'){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text(data.msg);
						setTimeout(function(){ 
							window.location.href = "${ctx}/crms/marketingCenter/list";
							_this.removeClass('jz');
						},3000)
					}
				}
				setTimeout(function(){ 
					$(".tishi_2").hide();
				},3000)
// 				$('#areaId').val('【淘宝】');//短信内容编辑框设置为默认值【淘宝】
// 				$('#inputNum').text('8');//短信计费字数默认是8
// 				$('#contePrice').text('1');//短信计费条数默认是1
// 				$('#autographId').val("淘宝");//签名默认选中【淘宝】
// 				$('#send_input').val('1');//发送设置为立即发送
// 				$('#lijiSend').find('img').show();//设置勾选
// 				$('#dingshiSend').find('img').hide();
// 				$(".time_hide").toggle();
// 				$('.custom_hide').hide();
// 				$('.custom_hide').find('input').val('');
// 				$('.text_area').val("【淘宝】");
// 				$('#activityId').val('');
			});
		});
		$('#showSendBox .qx').click(function(){
			$('#showSendBox').hide();
			$('.markBg').hide();
		});
	});
	//计费规则新建标签跳转
	$("#JFGZ").click(function(){
			var ctx = $("#ctx").val();
			window.open(ctx+"/crms/home/notice#duanxinxiangguan");
			/* window.open("${ctx}/crms/home/notice#duanxinxiangguan"); */
	});
	
	
	//选择收货地区的时获取勾选的地区
	function customRegion (){
		var regionName = "";
		var regionNameb=[];
		var getprovince=$(".place_check").children().find('.li_').children('.bgc_check_blue').next().children('input');
		for(var i=0;i<getprovince.length;i++){
			regionName+=getprovince.eq(i).val()+",";
			regionNameb.push(getprovince.eq(i).val());
		}
		regionName=regionName.substring(0,regionName.length-1);
		//alert(regionName);
		$("#stateId").val(regionName);
		
		if(regionNameb.length){
			$('#ssdqBtn').text('已选择'+regionNameb.length+'个地区');
			$('#ssdqImg img').hide();
			$('#ssdqImg input').val('');
		}else{
			$('#ssdqBtn').text('自定义');
			$('#ssdqImg img').show();
			$('#ssdqImg input').val('1');
		}
	}
	
	//回显地区选择数据
	$(function(){
		var locality = $("#stateId").val(); 

		var localityList = locality.split(",");
		var getprovince = $(".place_check").children().find('.li_').children().next().children('input');
		for(var i=0;i<localityList.length;i++){
			for(var j=0;j<getprovince.length;j++){
				if(getprovince.eq(j).val()==localityList[i]){
					getprovince.eq(j).parent().prev().addClass("bgc_check_blue");
				}
			}
		}
	});
	
	//取消按钮
	function cancel(){
		$('#areaId').val('【淘宝】');//短信内容编辑框设置为默认值【淘宝】
		$('#inputNum').text('8');//短信计费字数默认是8
		$('#contePrice').text('1');//短信计费条数默认是1
		$('#autographId').val("淘宝");//签名默认选中【淘宝】
		$('#send_input').val('1');//发送设置为立即发送
		$('#lijiSend').find('img').show();//设置勾选
		/* $('#tser11').hide(); */
		$('#dingshiSend').find('img').hide();
		$(".time_hide").hide();
		$('.custom_hide').hide();
		$('.custom_hide').find('input').val('');
		$('.text_area').val("【淘宝】");
		$('#activityId').val('');
	}
	
	//获取生成链接的类型
	function linkDate(link){
		if(link=='商品链接'){
			$('#commodityId').show();
			$('#storeshow').hide();
		}else{
			$('#storeshow').show();
			$('#commodityId').hide();
		}
		$("#linkType").val(link);
	}
	
	//根据生成链接类型，将请求发送到后台生成短链接
	function linkSubmit(){
		var linktype=$("#linkType").val();
		var linkName=$("#linkName").val();
		var commodityId=$("#commodityId").val();
		if(linktype == ""){
    		$(".tishi_2").show();
    		$(".tishi_2").children("p").text("请选择链接类型")
    		setTimeout(function(){ 
    			$(".tishi_2").hide()
    		},3000)
		}else{
			var url = "${ctx}/GenerateLinkTo/generateLink";
			$.post(url,{"linkType":linktype,"linkName":linkName,"commodityId":commodityId},function (data) {
				if(data.link != null){
					 $(".text_area").val(startContent+data.link+endContent);
					 addLength();
				}
			});
		}
	}
	
//-----------------------------------Ajax分页-----------------------------------------------------------------------------------------------
	var itemCount;//符合查找条件的商品总页数，分页参考
	var pageIndex = 0;//当前页，默认为0
	var pageSize = 7;//每页显示个数为7
	/*商品ID弹出框*/	
	　  /*$("#numIid").attr("disabled",true);*/
	  
	  $("#numIid").click(function(){
// 		  $(".SHOPTCKID").show();
// 		  $('#titleName').val('');
// 		  $(document.body).css({
// 			    "overflow-x":"hidden",
// 			    "overflow-y":"hidden"
// 			  });
// 		  searchItem(0,7);
			
	  })
	  $(".Cancel").click(function(){
		  $(".SHOPTCKID").hide();
	  })
	//按条件查找商品
	function searchItem(pageIndex,pageSize){
		  $('#searchItem').empty();
		  $('.ajax_page').empty();
		  var name = $('#titleName').val();
		 
		var url = "${ctx}/crms/orderCenter/findItemList";
		$.post(url,{"pageIndex":pageIndex,"pageSize":pageSize,"name":name},function(data){
			var items = data.itemList;
			var appendItem;
			itemCount = Math.ceil(data.itemCount/pageSize);
			var numIdArr=[];
			if($('#numIid').val()){
			 	numIdArr=$('#numIid').val().split(',');
			}
			if(data.flag){
				$.each(items,function(i,result){
					
					appendItem = "<tr class='w790 h55'><td class='w75 h55 text-center bgc_fafafa'>"
						   + "<div class='w20 h20 bgc_e0e6ef margin0_auto b_radius5 GXK'></div>"
						   + "<input type='hidden' class='num_iid' value='"+result.numIid+"'>"
						   + "</td><td class='w620 h55 bgc_fafafa'><img class='w54 h45 m_t5 m_l10 m_r20 f_l' src='"+result.url+"'><p class='w400 h55 lh55 f_l overflow_hidden'>"+result.title+"</p></td>"
						   + "<td class='w78 h55 text-center bgc_fafafa'>"+result.price+"</td></tr>";
					$('#searchItem').append(appendItem);
				
				
				
				
				});
				if($('#numIid').val()){
					for(var i=0;i<numIdArr.length;i++){
						for(var j=0;j<items.length;j++){
							if(numIdArr[i]==items[j].numIid){
								$('#searchItem tr').eq(j).children('td').children('div').addClass('bgc_check_blue');
							}
							
							
						}
					}
				}
				
				//查询结果所有的商品
				
			}else{
				$('#searchItem').append(data.message);
			}
			//分页
			var page = '<div id="userPage" align="center" ><font size="2">共'+itemCount+'页</font><font size="2">第'
			+(pageIndex+1)+'页</font> <a href="javascript:void" onclick="goToFirstPage()" id="aFirstPage" >首页</a> '
			+' <a href="javascript:void" onclick="goToPrePage()" id="aPrePage">上一页</a> '
			+' <a href="javascript:void" onclick="goToNextPage()" id="aNextPage">下一页</a> '
			+' <a href="javascript:void" onclick="goToEndPage()" id="aEndPage">尾页</a>';
			page+='</div>';
            /* $("#serchResult").append(page); */
            $('.ajax_page').append(page);
		},'json');
	};
	//首页
	function goToFirstPage(){
		pageIndex = 0;
		searchItem(pageIndex,pageSize);
	}
	//前一页
	function goToPrePage(){
		pageIndex -= 1;
		pageIndex = pageIndex >= 0 ? pageIndex : 0;
		searchItem(pageIndex,pageSize);
	}
	//后一页
	function goToNextPage(){
		if(pageIndex + 1 < itemCount){
			pageIndex += 1;
		}
		searchItem(pageIndex,pageSize);
	}
	//尾页
	function goToEndPage(){
		pageIndex = itemCount -1;
		searchItem(pageIndex,pageSize);
	}
	//添加本页所有勾选的商品到指定的文本框中
	function addItems(){
		$('#numIid').val('');
		var itemGXK = $('#searchItem').find('div');
		for(var i =0;i<itemGXK.length;i++){
			if(itemGXK.eq(i).hasClass('bgc_check_blue')){
				if($('#numIid').val() == ''){
					$('#numIid').val(itemGXK.eq(i).next().val());
				}else{
					$('#numIid').val($('#numIid').val() +","+itemGXK.eq(i).next().val());
				}
			}
		}
	}
	function minDateJudge(){
		var millis;
		var time = new Date()
		millis = time.getTime()+30*60000;
		var myDate = new Date(millis);
		var nowYear = myDate.getFullYear();
		var nowMonth = myDate.getMonth()+1;
		var nowDay = myDate.getDate();
		var nowTime = myDate.getHours() + ":" + myDate.getMinutes();
		var now = nowYear+"-"+nowMonth+"-"+nowDay+" "+nowTime
   		return now;
	}
	
</script>
</html>
