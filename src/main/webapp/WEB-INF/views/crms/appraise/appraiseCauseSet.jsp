<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>中差评原因设置</title>
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
                            <p class="font20" id="tit">中差评原因设置</p>
                            <p class="font14 p_t8">为店铺中差评打上设置的原因标签，便于后期分析和改善。</p>
                        </div>
                        <ul class="h40 p_t40 font14 ">
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseAmend/showAppraiseAmend">中差评查看</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseMonitoring/showAppraiseMonitoring">中差评监控</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_"><a class="c_384856 display_block"  href="${ctx}/appraisePacify/showAppraisePacify">中差评安抚</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 ping_1 cursor_"><a class="c_384856 display_block"  href="${ctx}/crms/appraise/middleBadAssessment?filtrateTime=最近7天">中差评统计</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_fff  cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseSet/showAppraiseCauseSet">中差评原因设置</a></li>
                            <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseAnalyze/showAppraiseCauseAnalyze">中差评原因分析</a></li>
                        </ul>
                        <%@ include file="/WEB-INF/views/crms/header/liucheng.jsp" %>
                    </div>
              
                    <!-- 中差评原因设置-->
                    <div class=" ping_2">
                    	<div class="w1660  bgc_fff font14">
                    		<div class="w1290  p_t15 p_l70 font14">
                    			<p class="font14 c_384856 m_b20">提示：系统默认归纳了常见中差评原因，您可以对其进行修改或者增添自定义原因</p>
                    		<div class="c_384856 font14 w_1000 m_b20 h45">
                    			<div class="w75 h45 l_h45 c_384856 f_l">原因归类:</div>
                    			<ul class="text-center">
                    				<li class="f_l w100 h45 l_h45 m_r20 bgc_00a0e9 c_fff radius10 cursor_ border_d9dde5 wenti_1">全部</li>
                    				<li class="f_l w100 h45 l_h45 m_r20 c_8493a8 cursor_ border_d9dde5 radius10 wenti_1">产品问题</li>
                    				<li class="f_l w100 h45 l_h45 m_r20 c_8493a8 cursor_ border_d9dde5 radius10 wenti_1">物流问题</li>
                  					<li class="f_l w100 h45 l_h45 m_r20 c_8493a8 cursor_ border_d9dde5 radius10 wenti_1">客服问题</li>
                    				<li class="f_l w100 h45 l_h45 m_r20 c_8493a8 cursor_ border_d9dde5 radius10 wenti_1">配发货问题</li>
                    				<li class="f_l w100 h45 l_h45 m_r20 c_8493a8 cursor_ border_d9dde5 radius10 wenti_1">严重出错</li>
                    				<li class="f_l w100 h45 l_h45 m_r20 c_8493a8 cursor_ border_d9dde5 radius10 wenti_1">其他问题</li>
                    			</ul>
                    			</div>
                    		</div>
                    		<div class="w120 h50 lh50 bgc_00a0e9 c_fff text-center cursor_ m_l80 m_b40 radius10 yuanyin_1 position_relative">
                    			<img class="m_r10 " src="${ctx}/crm/images/add_list.png" />新建原因
                    		</div>
                    		
							<div class="c_384856 p_l70 wenti_2 font14">
								<table>
								  <tr>
								    <th class="w405 font14 text-center">宝贝信息</th>
								    <th class="w190 text-center">原因名称</th>
								    <th class="w405 text-center">原因关键字</th>
								    <th class="w280 text-center">操作</th>
								  </tr>
								  <tr>
								    <td rowspan="3" class="text-center ">产品问题</td>
								    <td class="text-center">尺寸问题</td>
								    <td class="text-center">太大了，有点大，太小了，有点小，偏小，偏大</td>
								    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_ reason_1">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
								  </tr>
                                  <tr>
                                  	<td class="text-center">质量查</td>
                                    <td class="text-center">质量差，质量太差，质量有点差，瑕疵，褪色，手感差，掉色</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_ reason_1">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center">价格高</td>
                                    <td class="text-center">太贵，不便宜，死贵</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_ reason_1">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
                                  
                                  <tr>
								    <td rowspan="3" class="text-center ">产品问题</td>
								    <td class="text-center ">尺寸问题</td>
								    <td class="text-center ">太大了，有点大，太小了，有点小，偏小，偏大</td>
								    <td class="text-center ">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_ reason_1">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
								  </tr>
                                  <tr>
                                  	<td class="text-center ">质量查</td>
                                    <td class="text-center ">质量差，质量太差，质量有点差，瑕疵，褪色，手感差，掉色</td>
                                    <td class="text-center ">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_ reason_1">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center ">价格高</td>
                                    <td class="text-center ">太贵，不便宜，死贵</td>
                                    <td class="text-center ">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_ reason_1">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
                                  
								  <tr>
                                  	<td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                    <td class="text-center"></td>
                                  </tr>
								</table>
							
							
							<div class="w1290 h50 m_t40 font14 c_8493a8 m_b40 ">
		                                <div class="f_l w220 h24 l_h24 p_l70">共0条记录，共1页，当前为第1页</div>
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
		                    
		                    <!-- 产品问题  -->
							<div class="wenti_2 display_none c_384856 p_l70 w1290 h430 font14">
								<table>
									<tr>
								    <th class="w405">宝贝信息</th>
								    <th class="w190">原因名称</th>
								    <th class="w405">原因关键字</th>
								    <th class="w280">操作</th>
								  </tr>
								  <tr>
								    <td rowspan="3" class="text-center">产品问题</td>
								    <td class="text-center">尺寸问题</td>
								    <td class="text-center">太大了，有点大，太小了，有点小，偏小，偏大</td>
								    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
								  </tr>
                                  <tr>
                                  	<td class="text-center">质量查</td>
                                    <td class="text-center">质量差，质量太差，质量有点差，瑕疵，褪色，手感差，掉色</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center">价格高</td>
                                    <td class="text-center">太贵，不便宜，死贵</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
								</table>
							</div>
							
							<!-- 物流问题  -->
							<div class="wenti_2 display_none c_384856 p_l70 w1290 h430 font14">
								<table>
									<tr>
								    <th class="w405">宝贝信息</th>
								    <th class="w190">原因名称</th>
								    <th class="w405">原因关键字</th>
								    <th class="w280">操作</th>
								  </tr>
								  <tr>
								    <td rowspan="3" class="text-center">产品问题</td>
								    <td class="text-center">尺寸问题</td>
								    <td class="text-center">太大了，有点大，太小了，有点小，偏小，偏大</td>
								    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
								  </tr>
                                  <tr>
                                  	<td class="text-center">质量查</td>
                                    <td class="text-center">质量差，质量太差，质量有点差，瑕疵，褪色，手感差，掉色</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center">价格高</td>
                                    <td class="text-center">太贵，不便宜，死贵</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
								</table>
							</div>
							
							<!-- 客服问题  -->
							<div class="wenti_2 display_none c_384856 p_l70 w1290 h430 font14">
								<table>
									<tr>
								    <th class="w405">宝贝信息</th>
								    <th class="w190">原因名称</th>
								    <th class="w405">原因关键字</th>
								    <th class="w280">操作</th>
								  </tr>
								  <tr>
								    <td rowspan="3" class="text-center">产品问题</td>
								    <td class="text-center">尺寸问题</td>
								    <td class="text-center">太大了，有点大，太小了，有点小，偏小，偏大</td>
								    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
								  </tr>
                                  <tr>
                                  	<td class="text-center">质量查</td>
                                    <td class="text-center">质量差，质量太差，质量有点差，瑕疵，褪色，手感差，掉色</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center">价格高</td>
                                    <td class="text-center">太贵，不便宜，死贵</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
								</table>
							</div>
							
							<!-- 配发货问题  -->
							<div class="wenti_2 display_none c_384856 p_l70 w1290 h430 font14">
								<table>
									<tr>
								    <th class="w405">宝贝信息</th>
								    <th class="w190">原因名称</th>
								    <th class="w405">原因关键字</th>
								    <th class="w280">操作</th>
								  </tr>
								  <tr>
								    <td rowspan="3" class="text-center">产品问题</td>
								    <td class="text-center">尺寸问题</td>
								    <td class="text-center">太大了，有点大，太小了，有点小，偏小，偏大</td>
								    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
								  </tr>
                                  <tr>
                                  	<td class="text-center">质量查</td>
                                    <td class="text-center">质量差，质量太差，质量有点差，瑕疵，褪色，手感差，掉色</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center">价格高</td>
                                    <td class="text-center">太贵，不便宜，死贵</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
								</table>
							</div>
							
							<!-- 严重出错  -->
							<div class="wenti_2 display_none c_384856 p_l70 w1290 h430 font14">
								<table>
									<tr>
								    <th class="w405">宝贝信息</th>
								    <th class="w190">原因名称</th>
								    <th class="w405">原因关键字</th>
								    <th class="w280">操作</th>
								  </tr>
								  <tr>
								    <td rowspan="3" class="text-center">产品问题</td>
								    <td class="text-center">尺寸问题</td>
								    <td class="text-center">太大了，有点大，太小了，有点小，偏小，偏大</td>
								    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
								  </tr>
                                  <tr>
                                  	<td class="text-center">质量查</td>
                                    <td class="text-center">质量差，质量太差，质量有点差，瑕疵，褪色，手感差，掉色</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center">价格高</td>
                                    <td class="text-center">太贵，不便宜，死贵</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
								</table>
							</div>
							
							<!-- 其他问题  -->
							<div class="wenti_2 display_none c_384856 p_l70 w1290 h430 font14">
								<table>
									<tr>
								    <th class="w405">宝贝信息</th>
								    <th class="w190">原因名称</th>
								    <th class="w405">原因关键字</th>
								    <th class="w280">操作</th>
								  </tr>
								  <tr>
								    <td rowspan="3" class="text-center">产品问题</td>
								    <td class="text-center">尺寸问题</td>
								    <td class="text-center">太大了，有点大，太小了，有点小，偏小，偏大</td>
								    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
								  </tr>
                                  <tr>
                                  	<td class="text-center">质量查</td>
                                    <td class="text-center">质量差，质量太差，质量有点差，瑕疵，褪色，手感差，掉色</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
                                  <tr>
                                  	<td class="text-center">价格高</td>
                                    <td class="text-center">太贵，不便宜，死贵</td>
                                    <td class="text-center">
                                    	<div class="f_l w102 h44 l_h44 bgc_00a0e9 b_radius5 c_fff m_l30 cursor_">修改</div>
                                        <div class="f_l w102 h42 l_h42 bor_ccd5e0 b_radius5 m_l25 c_8493a8 cursor_">删除</div>
                                    </td>
                                  </tr>
								</table>
							</div>
		                    
                    		</div>
                    		
                    	</div>

				
			</div>

<div class="w100_ h1000 rgba_000_5 position_fixed top0 display_none z_10 duanxin_2">
	       
    <div class=" position_absolute top100 left100">
    <div class="w1102 h718 bgc_f1f3f7 change_text ">
    	<img class=" f_r w40 h40 close_duanxin_2 cursor_" src="${ctx}/crm/images/chazi.png" /> 
    	<div class="w1102 h50 text-center l_h50">
    		选择短信模板
    	</div>
    	<div class="w1102 h668 ">
    		<div class="w495 h668 bgc_fff f_l">
    			<div class="w495 h75">
    				<div class="w495 h20 text-center p_t15 font14">编辑内容</div>
    				<div class="w495 h20 c_red font14 text-center">若含有促销词如:微信、QQ、店庆等,请务必在短信内容末尾添加“退订回N、td”</div>
    			</div>
    			<div class="w458 h363 bgc_fff">
    				<textarea maxlength="500" class="bgc_f4f6fa text_area border_d9dde5 b_radius5 w458 h363 m_l15" ></textarea>
    			    <div class="h30 w458 m_t_24 m_l20 c_bfbfbf">已输入：<span class="text_count">0</span>/500字
    			    
    			    
    			    <a  href="${ctx }/crms/home/notice#duanxinxiangguan" class="c_00a0e9 cursor_  m_l10 guize1" target="_blank">计费规则</a>
    			    </div>
    			    
    			    <div class="w350 h45 bgc_fff position_absolute left45 bottom250 guize2 display_none">
    			    	<p class="font12">1、单条70个字，超出70个字将按照67个字每条计算</p>
    			    	<p class="font12">2、一个汉字、数字、字母、空格都算一个字</p>
    			    	<p class="font12">3、带标签的短信按实际发出的长度计算</p>
    			    </div>
    			    
    		
								
    			    
    			    
    			    
    			    
    			    
    			    
    			    
    			    
    			</div>
    			<div class="w495 h40 m_t10 m_l20">
    				<span class="f_l l_h40">插入标签：</span>
		            <div class="w100 h40 b_radius5 text-center f_l l_h40 border_00a0e9 c_00a0e9 tk cursor_ font14  m_l10">
			收货人
		            </div> 
		            <div class="w100 h40 b_radius5 text-center f_l l_h40 border_00a0e9 c_00a0e9 tk cursor_ font14 m_l10 tishi_1 ">
			订单编号
		            </div> 
		            <div class="w100 h40 b_radius5 text-center f_l l_h40 border_00a0e9 c_00a0e9 tk cursor_ font14 m_l10">
			买家昵称
		            </div> 
    			</div>
    			<div class="w495 h2 m_t10 m_l20">
    				<div class="w495 h20 c_red font14 ">提示: 链接前后须有空格分隔，请勿删除前后空格。</div>
    			</div>
    			<div class="w495 h40 m_t10 m_l20">
    				<span class="f_l l_h40 m_r15 m_l20">签名: </span>
		            <input class="bgc_f4f6fa border_d9dde5 b_radius5 w211 h40 f_l" />
    			</div>
    			<div class="w495 h40 m_t20  ">
    				<div class="m_l100 w100 h40 border_00a0e9 c_00a0e9 close tk b_radius5 text-center f_l l_h40 cursor_ font14  m_l10">
			确定
		            </div>
		            <div class=" m_l50 w100 h40 border_00a0e9 c_00a0e9 tk b_radius5 text-center f_l l_h40 cursor_ font14  m_l10 sure_1">
			加入我的模板
		            </div>
    			</div>
    		</div>
    	
    	    <div class="w595 h668 f_l bgc_fff m_l10 ">
    			<div class="w590 h50 bgc_f1f3f7">
    				<div class="w130 h50 text-center out bgc_fff l_h50 f_l cursor_ moban1">
    					案例模板
    				</div>
    				<div class="w130 h50 text-center out l_h50 bgc_e3e7f0 f_l cursor_ moban1">
    					我的模板
    				</div>
    				<div class="w150 h50 text-center out l_h50 bgc_e3e7f0 f_l cursor_ moban1">
    					历史使用
    				</div>
    				
    			</div>
    			
    			<div class="in">
    				
					<div class="w560 h490 m_l15 m_t10">
						<table>
							<tr class=" h105 bgc_fafafa">
								<td class="w400 "></td>
								<td class="w160 ">
									<div class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
								</td>
							</tr>
							<tr class="h85 bgc_f4f4f4">
								<td class="w400 "></td>
								<td class="w160">
									<div class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
								</td>
							</tr>
							<tr class="h75 bgc_fafafa">
								<td class="w400 "></td>
								<td class="w160 "></td>
							</tr>
							<tr class="h115 bgc_f4f4f4">
								<td class="w400 "></td>
								<td class="w160 "></td>
							</tr>
							<tr class="h105 bgc_fafafa">
								<td class="w400 "></td>
								<td class="w160 "></td>
							</tr>
						</table>
					</div>
					
					<div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">
		    
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
    	    
    			
    			<div class="in display_none">
    				
					<div class="w560 h490 m_l15 m_t10">
						<table>
							<tr class=" h105 bgc_fafafa">
								<td class="w400 "></td>
								<td class="w160 ">
									<div class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
								</td>
							</tr>
							<tr class="h85 bgc_f4f4f4">
								<td class="w400 "></td>
								<td class="w160">
									<div class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
								</td>
							</tr>
							<tr class="h75 bgc_fafafa">
								<td class="w400 "></td>
								<td class="w160 "></td>
							</tr>
							<tr class="h115 bgc_f4f4f4">
								<td class="w400 "></td>
								<td class="w160 "></td>
							</tr>
							<tr class="h105 bgc_fafafa">
								<td class="w400 "></td>
								<td class="w160 "></td>
							</tr>
						</table>
					</div>
					
					<div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">
		    
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
    	    
    			
    			<div class="in display_none">
    				
					<div class="w560 h490 m_l15 m_t10">
						<table>
							<tr class=" h105 bgc_fafafa">
								<td class="w400 "></td>
								<td class="w160 ">
									<div class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
								</td>
							</tr>
							<tr class="h85 bgc_f4f4f4">
								<td class="w400 "></td>
								<td class="w160">
									<div class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
								</td>
							</tr>
							<tr class="h75 bgc_fafafa">
								<td class="w400 "></td>
								<td class="w160 "></td>
							</tr>
							<tr class="h115 bgc_f4f4f4">
								<td class="w400 "></td>
								<td class="w160 "></td>
							</tr>
							<tr class="h105 bgc_fafafa">
								<td class="w400 "></td>
								<td class="w160 "></td>
							</tr>
						</table>
					</div>
					
					<div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">
		    
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
    
    
    
    
		<div class="w500 h180 bgc_fff  tishi_2  position_fixed z_11 left330 top420  display_none">
		<img class=" f_r w40 h40 close_tishi_2 cursor_" src="${ctx}/crm/images/chazi.png" />
	    	<div class="w300 h30 p_t10 p_l70">提示</div>
	    	<p class="font14 p_l70 w400 p_t10">(订单编号)实际替换后长度约为16个字，实际扣费以发出字</p>
	    	<p class="font14 p_l70 w400">数为准，您是否确定添加?</p>
	    	
	    	<div class="w232 h40 margin0_auto text-center font16  p_t20">

				<div class="f_l b_radius5 m_r30 w100 lh40 bgc_00a0e9 close_this c_fff cursor_ close_inside">
					确定
				</div>
				<div class="f_l b_radius5 w100 h38 lh38 border_cad3df close_this c_cad3df cursor_ close_inside">
					取消
				</div>
				
			</div>
    	</div>	                                    
		         
		<div class="w500 h180 bgc_fff  sure_2  position_fixed z_11 left330 top420  display_none">
		<img class=" f_r w40 h40 close_sure_2 cursor_" src="${ctx}/crm/images/chazi.png" />
	    	<div class="w300 h30 p_t10 p_l70">提示</div>
	    	<p class="font14 p_l70 w400 p_t10">保存短信模板成功</p>
	    	
	    	
	    	<div class="w232 h40 margin0_auto text-center font16  p_t20">

				<div class="f_l b_radius5 m_r30 w100 lh40 bgc_00a0e9 close_this c_fff cursor_ close_inside">
					确定
				</div>
				
				
			</div>
    	</div>         
		          
		
		                           
</div>

				<div class="z_10 w100_ h1200 rgba_000_5 position_fixed top0 yuanyin_2 display_none">
					<div class=" position_fixed left630 top200">
			                    <div class="w580 h575 bgc_fff c_000">
			                    	<img class=" f_r w40 h40 close_yuanyin_2 cursor_" src="${ctx}/crm/images/chazi.png" />
			                    	<div class="w580 h55 text-center p_t20 ">	
				                    	
				                    		增加原因
				                    		
				                    	
				                    	
				                    </div>	
			                    	<div class="w510 p_l70 h45">
										<div class="f_l m_r15 l_h45">
											关键字:
										</div>
										<div class="wrap f_l h45 lh45 m_r20" >
										    <div class="nice-select h45 lh45" name="nice-select">
										    	<input class="h45 lh45" type="text" value="" readonly placeholder="  产品问题">
										    	<ul>
										    		<li>客服问题</li>
										    		<li>物流问题</li>
										    		<li>配发货问题</li>
										    		<li>严重出错</li>
										    		<li>其他问题</li>
										    	</ul>
										  	</div>
										</div>
			                    	</div>
			                    	<div class="w510 h45 p_l50 m_t20">
			                    		<div class="f_l m_r15 l_h40">
												原因名称 :
											</div>
											<input class="bgc_f4f6fa border_d9dde5 b_radius5 w250 h40 f_l m_r35" placeholder="名称不超过十个字，不能重复"/>
			                    	</div>
			                    	<div class="w510 h270 p_l35 m_t20">
			                    		<div class="f_l m_r15">
												内容关键字 :
											</div>
											<textarea class="bgc_f4f6fa border_d9dde5 b_radius5 w250 h270 f_l m_r35" placeholder="评价内容关键字用于自动归类中差评原因，多个关键字用逗号隔开，如:“服务太差，客服不回复”"></textarea>
			                    	</div>
			                    	<div class="w255 h45 m_t40 p_l150 text-center">
			                    		<ul>
			                    		<li class="f_l w100 h40 l_h40 m_r20 bgc_00a0e9 c_fff radius10 cursor_ border_d9dde5 close_inside">确定</li>
	                    				<li class="f_l w100 h40 l_h40 m_r20 c_8493a8 cursor_ border_d9dde5 radius10 close_inside">取消</li>
	                    				</ul>
			                    	</div>
			                    </div>
		                    	</div>

					</div>


					
					<div class="z_10 w100_ h1200 rgba_000_5 position_fixed top0 reason_2 display_none">
							<div class="  position_fixed left630 top200 ">
			                    <div class="w710 h575 bgc_fff c_000">
			                    	<img class=" f_r w40 h40 close_reason_2 cursor_" src="${ctx}/crm/images/chazi.png" />
			                    	<div class="w660 h55 text-center p_t20 ">	
				                    	
				                    		修改原因
				                    		
				                    	
				                    	
				                    </div>	
			                    	<div class="w510 p_l140 h45">
										<div class="f_l m_r15 l_h45">
											关键字:
										</div>
										<div class="wrap f_l h45 lh45 m_r20" >
										    <div class="nice-select h45 lh45" name="nice-select">
										    	<input class="h45 lh45" type="text" value="" readonly placeholder="  产品问题">
										    	<ul>
										    		<li>客服问题</li>
										    		<li>物流问题</li>
										    		<li>配发货问题</li>
										    		<li>严重出错</li>
										    		<li>其他问题</li>
										    	</ul>
										  	</div>
										</div>
			                    	</div>
			                    	<div class="w510 h45 p_l117 m_t20">
			                    		<div class="f_l m_r15 l_h40">
												原因名称 :
											</div>
											<input class="bgc_f4f6fa border_d9dde5 b_radius5 w250 h40 f_l m_r35" placeholder="名称不超过十个字，不能重复"/>
			                    	</div>
			                    	<div class="w500 h250 p_l70 m_t20">
			                    		<div class="f_l m_r15">
												评价内容关键字 :
											</div>
											<textarea class="bgc_f4f6fa border_d9dde5 b_radius5 w250 h250 f_l m_r35" placeholder="评价内容关键字用于自动归类中差评原因，多个关键字用逗号隔开，如:“服务太差，客服不回复”"></textarea>
											
			                    	</div>
			                    	<p class="font12 c_red p_l200 m_t10">注意：修改后的评价内容关键字，只对新产生的评价生效！</p>
			                    	
			                    	<div class="w255 h45 m_t40 p_l200 text-center">
			                    		<ul>
			                    		<li class="f_l w100 h40 l_h40 m_r20 bgc_00a0e9 c_fff radius10 cursor_ border_d9dde5 close_inside">确定</li>
	                    				<li class="f_l w100 h40 l_h40 m_r20 c_8493a8 cursor_ border_d9dde5 radius10 close_inside">取消</li>
	                    				</ul>
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
