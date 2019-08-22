<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>宝贝关怀</title>
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
<script type="text/javascript" src="${ctx}/crm/js/baobeiguanhuai.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/util.js"></script>
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
			<div class="w1660 m_t70 m_l30">
				<div class="w100_ lh50 bgc_fff font18">
					<div class="f_l h50 w4 bgc_1d9dd9"></div>
					<div class="f_l m_l15 c_384856" style="font-size: 1vw;">订单中心</div>
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
							<li class="f_l w140 text-center bgc_f1f3f7"><a
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
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block"
								href="${ctx}/appraiseAmend/showAppraiseAmend"> 中差评管理 </a></li>
							<li class="w140 bgc_fff text-center f_l"><a
								class="c_384856 display_block"
								href="${ctx}/OrderReminder/jumOrderReminder"> 手动订单提醒 </a></li>
							<li style="clear: both;"></li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<div class="w1660  bgc_f1f3f7 c_384856">
					<!--宝贝关怀-->
					<div class=" p_l40 p_t27">
						<p class="font20  ">宝贝关怀</p>
						<p class="font14 p_t8">可针对不同商品编排不同短信内容，在宝贝签收时自动发送关怀短信，如宝贝使用说明等</p>
						<div class="h40 ">
							<div
								class="w110 h40 l_h40 f_r bgc_fff m_r15 text-center  cursor_ ZhanKai p_t_12">
								<img class="m_r10 JTX display_none"
									src="${ctx}/crm/images/箭头下.png" /> <img class="m_r10 JTS"
									src="${ctx}/crm/images/箭头上.png" /> <span class="LiuChengText">收回流程</span>
							</div>
						</div>
						<input type="hidden" id="msg" name="msg" value="${msg}">
					</div>
				</div>
				<%@ include file="/WEB-INF/views/crms/header/liucheng.jsp"%>
				<div class="bgc_f1f3f7" style="width: 84.5vw; padding-left: 2vw;">
					<ul class="h40 font14" style="padding-top: 3vw;">
						<a href="${ctx}/cowryCare/queryCowryCare" class="c_384856">
							<li class="f_l w140 text-center h40 l_h40 bgc_fff cursor_ ">宝贝关怀</li>
						</a>
						<a href="#" class="c_384856"
							onclick="findOrderSendRedord('formId')"><li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ ">发送记录</li></a>
						<div style=""></div>
					</ul>
				</div>
				<div class="w1660 h700 bgc_fff p_b100 head_li">

					<!--基本设置，修改-->
					<p class="p_t50 m_l50 font16 c_384856">
						基本设置<span class="font14 m_l10 c_00a0e9 cursor_ change">修改</span>
					</p>
					<div class="head_li h270">
						<div class="font14 m_l80 c_384856 change_1 position_absolute z_-1">
							<p class="m_t40 font14">
								发送时间：<span class="font14 m_l20">签收 <span class=""><span
										class="hour">${orderSetupCowry.reminderTime }</span></span>, 后发送
								</span>
							</p>
							<p class="font14 m_t40">
								通知时间：<span class="m_l20"><span class="settime1">${orderSetupCowry.startTime }</span>
									~ <span class="settime2">${orderSetupCowry.endTime }</span></span>
								超出设置时间段，自动顺延至次日发送
							</p>
							<p class="font14 m_t40">
								实付金额：<span class="font14 m_l20 total_money"> <c:choose>
										<c:when
											test="${orderSetupCowry.payAmtOne==null && orderSetupCowry.payAmtTwo==null }">
											不限
								    </c:when>
										<c:when test="${orderSetupCowry.payAmtOne==null}">
											0元~${orderSetupCowry.payAmtTwo }元
								    </c:when>
										<c:when test="${orderSetupCowry.payAmtTwo==null}">
											${orderSetupCowry.payAmtOne }元~0元
								    </c:when>
										<c:otherwise>  
								    	${orderSetupCowry.payAmtOne }元~${orderSetupCowry.payAmtTwo }元
								    </c:otherwise>
									</c:choose>

								</span>
							</p>

						</div>
						<form action="${ctx}/sendRecord/orderCenterRecord" method="post"
							id="formId">
							<input type="hidden" name="recordFlag" value="4">
						</form>


						<!--点击修改后-->
						<form id="setup_form"
							action="${ctx}/cowryCare/saveOrderSetupToCowry" method="post">
							<input type="hidden" value="${orderSetupCowry.id }" name="id" />
							<div class="position_absolute display_none change_2">
								<div class="f_l h45 lh45 font14 m_l80 c_384856 m_t20">
									发送时间:</div>
								<div class="w600 h45 f_l m_t18 z_10 m_l10">

									<div class="f_l h45 lh45 c_384856 p_r10 font14">签收</div>
									<div class="wrap f_l h45 lh45  ">
										<div class="nice-select h45 lh45 nice-selectw  z_5"
											name="nice-select">
											<input readonly="readonly" class="h45 lh45 w140 save1"
												type="text" name="reminderTime"
												value="${orderSetupCowry.reminderTime }"
												style="color: #000000;">
											<ul>
												<li>0分钟</li>
												<li>15分钟</li>
												<li>30分钟</li>
												<li>1小时</li>
												<li>2小时</li>
												<li>3小时</li>
												<li>4小时</li>
												<li>5小时</li>
												<li>6小时</li>
												<li>7小时</li>
												<li>8小时</li>
												<li>9小时</li>
												<li>10小时</li>
												<li>11小时</li>
												<li>12小时</li>
												<li>24小时</li>
												<li>36小时</li>
												<li>2天</li>
												<li>3天</li>
												<li>5天</li>
												<li>7天</li>
												<li>15天</li>
												<li>30天</li>
											</ul>
										</div>
									</div>
									<div class="f_l h45 lh45 c_384856 p_l10 font14">后，开始发送</div>
								</div>
								<div class="clbo f_l h45 lh45 font14 m_l80 c_384856 m_t20">
									通知时间:</div>
								<div class="w1000 h45 f_l m_t18">
									<div class="wrap f_l h45 lh45 w140 m_l10 ">
										<div class="nice-select h45 lh45 nice-selectw"
											name="nice-select">
											<input readonly="readonly" id="startTime"
												class="h45 lh45 w140 settime" type="text" name="startTime"
												value="${orderSetupCowry.startTime }"
												style="color: #000000;">
											<ul>
												<li>00:00</li>
												<li>01:00</li>
												<li>02:00</li>
												<li>03:00</li>
												<li>04:00</li>
												<li>05:00</li>
												<li>06:00</li>
												<li>07:00</li>
												<li>08:00</li>
												<li>09:00</li>
												<li>10:00</li>
												<li>11:00</li>
												<li>12:00</li>
												<li>13:00</li>
												<li>14:00</li>
												<li>15:00</li>
												<li>16:00</li>
												<li>17:00</li>
												<li>18:00</li>
												<li>19:00</li>
												<li>20:00</li>
												<li>21:00</li>
												<li>22:00</li>
												<li>23:00</li>
											</ul>
										</div>
									</div>
									<div class="f_l  w50 h45 lh45 text-center font14 m_l10">~</div>
									<div class="wrap f_l h45 lh45 w140  ">
										<div class="nice-select h45 lh45 nice-selectw"
											name="nice-select">
											<input readonly="readonly" id="endTime"
												class="h45 lh45 w140 settime_" type="text" name="endTime"
												value="${orderSetupCowry.endTime }" style="color: #000000;">
											<ul>
												<li>00:00</li>
												<li>01:00</li>
												<li>02:00</li>
												<li>03:00</li>
												<li>04:00</li>
												<li>05:00</li>
												<li>06:00</li>
												<li>07:00</li>
												<li>08:00</li>
												<li>09:00</li>
												<li>10:00</li>
												<li>11:00</li>
												<li>12:00</li>
												<li>13:00</li>
												<li>14:00</li>
												<li>15:00</li>
												<li>16:00</li>
												<li>17:00</li>
												<li>18:00</li>
												<li>19:00</li>
												<li>20:00</li>
												<li>21:00</li>
												<li>22:00</li>
												<li>23:00</li>
											</ul>
										</div>
									</div>
									<div class="f_l m_l10 font14 c_8493a8 h45 lh45 m_l28 m_r10">
										超出设置时间段，自动顺延至次日发送</div>
									<div id="timeMSG" class="col-md-6 lh45"
										style="margin-left: 33.333333vw;">
										<font></font>
									</div>
								</div>
								<div class="clbo f_l h45 lh45 font14 m_l80 c_384856 m_t20">
									实付金额:</div>
								<div class="w1000 h45 f_l m_t18">
									<div class="f_l b_radius5 bgc_f4f6fa h45 w160 m_l10">
										<input id="minPayment"
											class="b_radius5 bgc_f4f6fa w120 f_l border0 h45 lh45 p_l15 limit-1"
											name="payAmtOne" value="${orderSetupCowry.payAmtOne }"
											onkeyup="value=value.replace(/[^\d.]/g,'')" placeholder="不限" />
										<div class="b_radius5 f_l w20 text-center h45 lh45"></div>
									</div>
									<div class="f_l  w40 h45 lh45 text-center font16 ">~</div>
									<div class="f_l b_radius5 bgc_f4f6fa h45 w160">
										<input id="maxPayment"
											class="b_radius5 bgc_f4f6fa w120 f_l border0 h45 lh45 p_l15 limit-2"
											name="payAmtTwo" value="${orderSetupCowry.payAmtTwo }"
											placeholder="不限" onkeyup="value=value.replace(/[^\d.]/g,'')" />
										<div class="b_radius5 f_l w20 text-center h45 lh45 font14">元</div>
									</div>
									<div id="priceMSG" class="col-md-7 lh45"
										style="margin-left: 20.833333vw;">
										<font></font>
									</div>
									<!--<div class="f_l m_l10 font14 c_ff6363 h45 lh45 ">-->
									<!--超出设置时间段，自动顺延至次日发送-->
									<!--</div>-->
								</div>
								<div class="clbo m_l860 w260">
									<div onclick="submitForm('setup_form')"
										class="f_l w100 h40 bgc_00a0e9 l_h40 text-center c_fff b_radius5 cursor_ save">
										保存</div>
									<div
										class="f_l w100 h40 l_h40 text-center border_cad3df b_radius5 m_l40 cursor_ cancel">
										取消</div>
								</div>
							</div>
						</form>

					</div>
					<!--宝贝设置-->
					<p class="m_l50 font16 c_384856">宝贝设置</p>
					<div class="h80">
						<input id="appoint_ItemId" type="hidden"
							value="${smsSetting.itemId }" />
						<div onclick="findItem()"
							class="f_l font14 bgc_00a0e9 w140 h40 text-center lh40 m_l70 m_t40 b_radius5 c_fff cursor_ select yitianjia">添加指定商品</div>
						<div
							class="f_l m_l15 c_384856 font16 w140 h40 text-center lh40 m_t40 Yitianjia display_none  item_show">
							已添加<span id="item_span"></span>个商品
						</div>
					</div>
					<div class="clbo m_l70 ">
						<!--温馨提示-->
						<p class="c_384856 font16 c_384856 lh40 m_t40">温馨提示：</p>
						<p class="c_384856 font16 m_t15 m_l40">-添加商品或类目</p>
						<p class="c_384856 font16 m_t15 m_l40">-开启成功后，会按照设置的时间自动发送关怀短信</p>
						<p class="c_384856 font16 m_t15 m_l40">-商品最多可添加50条</p>
					</div>
					<!--开启宝贝关怀-->
					<div
						class="w225 h50 bgc_00a0e9 c_fff text-center b_radius5 margin0_auto l_h50  cursor_">
						<input type="button" id="addstatus" value="开启宝贝关怀"
							class="w226 h50 lh50  b_radius5 bgc_00a0e9 c_fff text-center margin0_auto cursor_  font16 lijikaiqi_ border0" />
						<input id="buttonId" type="hidden"
							value="${orderSetupCowry.status }" /> <input id="orderSetupId"
							type="hidden" value="${orderSetupCowry.id }" />
					</div>
				</div>
			</div>
		</div>

















	</div>





	<!---------------添加指定商品，导入弹窗--------------->
	<div
		class="w100_ rgba_000_5 position_absolute top0 select1 z_12 display_none"
		style="height: 76vw;">

		<div
			class="w1000 h540 bgc_fff p_t20 p_l20 p_r20 p_b20 po z_10 position_fixed top200 left400 xiayibu">
			<div class="m_b20 h42">
				<h3 class="font16 text-center c_384856">选择商品</h3>
			</div>
			<div class="h47">
				<div class="f_l m_r15 m_l28 h50 l_h50 c_384856 font14"
					style="margin-left: 0.458333vw;">商品ID:</div>
				<input id="itemId_input" class="bgc_f4f6fa border0 w270 h50 f_l"
					style="width: 13.270833vw;" />
				<div class="f_l m_r15 m_l10 h50 l_h50 c_384856 font14">关键字:</div>
				<input id="itemName_input"
					class="bgc_f4f6fa border0 w270 h50 f_l m_r35" />
				<div id="putaway_div"
					class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 zhixianshishangjia ">
				</div>
				<input id="status_input" type="hidden" value="2" />
				<div class="f_l text-left m_l10 m_t15 font14">只显示上架</div>
				<div onclick="findItemByData();"
					class="w80 h40 lh40  b_radius5 bgc_00a0e9 c_fff text-center m_l20 f_l cursor_ Save font14">
					搜索</div>
			</div>
			<!--选择-->
			<div class="c_384856 clbo m_t15 h300 overflow_auto">
				<table border="0" class="font14 m_t13 item_table">
					<tr class="bgc_e3e7f0 item_tr">
						<%-- <th class="w80 h60 text-center"><button class="w80 h60 text-center>全选</button></th> --%>
						<!-- 						<th class="w80 h60 text-center"><div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 fuxuankuang bgc_check_blue' >&nbsp;</div>全选</th> -->
						<th class="w80 h60 text-center" style="width: 8vw;">
							<div class="f_l 1check_box ">
								<div
									class="cursor_ m_t10 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l VIPGXK fuxuankuang "
									style='margin-left: 1.4vw;'></div>
								<div class="m_l20 cursor_ f_l font18 c_384856"
									style="margin-top: 0.3vw;">全选</div>
							</div>
						</th>
						<th style="width: 8vw;" class=" h60 text-center">图片</th>
						<th style="width: 35vw;" class=" h60 text-center">宝贝名称</th>
						<th style="width: 8vw;" class=" h60 text-center">金额</th>
					</tr>
				</table>
				<img class="sdg" src="${ctx}/crm/images/yu-jiazai.gif" style="display: none">
			</div>
			<!-- <div class="c_384856 clbo m_t15">
						<table class="m_l15">
						  <tr class="w930 h55">
						    <th class="w85">选择</th>
						    <th class="w760">店铺中宝贝</th>
						    <th class="w85">金额</th>
						  </tr>
						  <tr class="w930 h55">
						    <td class="position_relative p_l10 z_1 w65">
						    	<div></div>
						    </td>
						    <td class="w85">
						    	<span class="cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 m_t13 f_l m_l30">
                                                            <img class="cursor_ one_check_ display_none" src="images/black_check.png" />
						    </td>
						    <td class="w760"></td>
						    <td class="w85"></td>
						  </tr>
						  <tr class="w930 h55 text-center">
						    <td class="position_relative p_l10 z_1 w65">
						    	<div></div>
						    </td>
						    <td class="w85 text-center">
						    	<span class="cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 m_t13 f_l m_l30">
                                                            <img class="cursor_ one_check_ display_none" src="images/black_check.png" />
						    </td>
						    <td class="w760"></td>
						    <td class="w85"></td>
						  </tr>
						  <tr class="w930 h55">
						    <td class="position_relative p_l10 z_1 w65">
						    	<div></div>
						    </td>
						    <td class="w85"><span class="cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 m_t13 f_l m_l30">
                                                            <img class="cursor_ one_check_ display_none" src="images/black_check.png" /></td>
						    <td class="w760"></td>
						    <td class="w85"></td>
						  </tr>
						  <tr class="w930 h55">
						    <td class="position_relative p_l10 z_1 w65">
						    	<div></div>
						    </td>
						    <td class="w85 text-center">
						    	<span class="cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 m_t13 f_l m_l30">
                                                            <img class="cursor_ one_check_ display_none" src="images/black_check.png" />
						    </td>
						    <td class="w760 "></td>
						    <td class="w85"></td>
						  </tr>
						</table>
				</div> -->
			<!--------分页-------->
			<!-- div class="w1000 h24 m_t22 font10 c_8493a8 m_b40">
                    <div class="f_r w470 h24 l_h22 font10 m_r50">
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
                        <div class="f_l w45 h22 m_r4"><input class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8" type="text" value="36"></div>
                        <div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
                    </div>
                </div> -->
			<div class="p_l20 margin50_auto w230">
				<div onclick="addItemId();"
					class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_">
					下一步</div>
				<div
					class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_  select3">
					取消</div>
			</div>


		</div>

	</div>
	<div
		class="w100_ rgba_000_5 position_absolute top0 z_12 selectnx1 display_none"
		style="height: 76vw;">

		<!--<div class="w200 h100 position_fixed bgc_000000 top695 right800 z_13 hover_test_show">
		<p>注意事项：</p>
		<p></p>
		<p></p>
	</div>-->


		<div
			class="h600 bgc_fff  p_t20 p_l20 p_r20 p_b20 z_12 position_fixed top200 left400"
			style="height: 31.40625vw; width: 55.083vw;">
			<div class=" iphone w327 h642 top0 right10 position_absolute">
				<textarea id="copycontent" disabled="disabled"
					class="text_area_copy m_l45 m_t150 w250 h370 border0 bgc_fff">
									${smsSetting.messageContent }
								</textarea>
				<input id="contentShow" value="${smsSetting.messageContent }"
					type="hidden">
			</div>
			<!------------------------------    短信设置编辑部分        -------------------------------------->
			<div>
				<form id="sms_form" action="${ctx}/cowryCare/saveOrUpdateSmsSetting"
					method="post">
					<div class="font14 c_384856 h40 p_l30 m_t10">
						<div class="f_l">
							短信变量: <input id="messageId" type="hidden" name="messageVariable"
								value="${smsSetting.messageVariable }">
						</div>
						<div class="f_l m_r15">
							<ul>

								<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
									class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
									onclick="addConsignee(document.getElementById('textContent'),'【订单号】');">
									订单号 <span
									class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认5个字，以实际变量为准</span>
								</li>
								<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
									class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
									onclick="addConsignee(document.getElementById('textContent'),'【下单时间】');">
									下单时间 <span
									class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
								</li>
								<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
									class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
									onclick="addConsignee(document.getElementById('textContent'),'【买家昵称】');">
									买家昵称 <span
									class="position_absolute w226 l_h40 left-46 top_2_3vw  b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
								</li>
								<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
									class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
									onclick="addConsignee(document.getElementById('textContent'),'【买家姓名】');">
									买家姓名 <span
									class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
								</li>
								<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
									class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
									onclick="addConsignee(document.getElementById('textContent'),'【订单金额】');">
									订单金额 <span
									class="position_absolute w226 l_h40 left-46 top_2_3vw  b_radius5 bgc_d3d3d3 c_fff display_none s">默认6个字，以实际变量为准</span>
								</li>
								<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
									class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
									onclick="addConsignee(document.getElementById('textContent'),'【运单号】');">
									运单号 <span
									class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认7个字，以实际变量为准</span>
								</li>
								<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
									class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
									onclick="addConsignee(document.getElementById('textContent'),'【物流公司名称】');">
									物流公司名称 <span
									class="position_absolute w226 l_h40 left-46 top_2_3vw b_radius5 bgc_d3d3d3 c_fff display_none s">默认4个字，以实际变量为准</span>
								</li>
								<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
									class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
									onclick="addConsignee(document.getElementById('textContent'),'【到达城市】');">
									到达城市 <span
									class="position_absolute w226 l_h40 left-46 top_2_3vw  b_radius5 bgc_d3d3d3 c_fff display_none s">默认4个字，以实际变量为准</span>
								</li>
								<li style="font-size: 0.7vw; padding: 0.3vw 0.5vw;"
									class="b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ position_relative ydh"
									onclick="addConsignee(document.getElementById('textContent'),'【物流链接】');">
									物流链接 <span
									class="position_absolute w226 l_h40 left-46 top_2_3vw  b_radius5 bgc_d3d3d3 c_fff display_none s">默认17个字，以实际变量为准</span>
								</li>
							</ul>


						</div>

					</div>

					<div class="font14 c_384856 h40 lh40 p_l30 m_t30">
						<div class="f_l m_r10">短信内容:</div>
						<div class="f_l w540 h285 bgc_f4f6fa radius10">
							<input type="hidden" name="id" value="${smsSetting.id }">
							<input id="ItemId_form" type="hidden" name="itemId"
								value="${smsSetting.itemId }" /> <input type="hidden"
								id="textContent1" name="messageContent"
								value="${smsSetting.messageContent }">
							<textarea id="textContent"
								class="w500 h240 outline_none radius10 border0 bgc_f4f6fa p_l20 p_t15 p_r20 Letter11  text_area"
								onkeyup="addLength();"></textarea>
							<div class="h30 m_t_24 m_l20 c_bfbfbf f_l">
								已输入：<span id="inputNum" class="text_count">0</span> 当前计费：<span
									id="contePrice">1</span>条
							</div>
							<input id="actualDeduction_input" type="hidden" name="smsNumber"
								value="${smsSetting.smsNumber }" />
							<div class="h30 m_t_24 c_bfbfbf f_r m_r15">
								退订回 <input readonly="readonly" maxlength="2"
									class="w20 h20 l_h20 border_0 bgc_f4f6fa" value="N" />
							</div>
						</div>
					</div>

					<div class="clbo w1030 font14 h40 l_h40 m_l_400"
						style="margin-left: 16.833333vw;">
						<div class="f_l">
							<div class="c_00a0e9 m_r15 f_l cursor_ ChangeLink"
								onclick="getCursortPosition(document.getElementById('textContent'));">转化为短链接</div>
							<div class="c_00a0e9 m_r15 f_l cursor_ Library"
								onclick="findTemplate('默认')">引用短语库</div>
							<div class="c_00a0e9 m_r15 f_l cursor_ ClickPhrase">另存为短语库</div>
						</div>
					</div>

					<div class="f_l w1030 font14 c_384856 h45 lh45 p_l30"
						style="margin-bottom: 1vw;">
						<div class="m_r10">
							短信签名: <input style="width: 10vw;"
								class="h50  bgc_f4f6fa p_l15 outline_none border0 shopName sms_sign"
								disabled="disabled" type="text" value="${ShopName }"
								onkeyup="updateShopName(this.value,'textContent');" /> <img
								src="${ctx }/crm/images/bianji.png"
								style="width: 1.2vw; margin-left: 0.5vw; cursor: pointer;"
								onclick="updateDisabled();"> <input id="smsSign"
								type="hidden" name="messageSignature"
								value="${smsSetting.messageSignature }" />
						</div>

					</div>
					<div class="f_l w1030 font14 c_384856 h45 lh45 p_l30">
						<div class="m_r10">
							测试手机: <input
								class="h50 lh50 w410 bgc_f4f6fa p_l15 outline_none border0 Phone"
								type="text" placeholder="可输入5个测试手机号，以逗号隔开" /> <span
								class="m_l10 c_00a0e9 m_l20 test cursor_ hover_test ceShiFaSong1">测试发送</span>

						</div>

					</div>

					<div class="f_l m_t50 m_l_400">
						<div onclick="submitSmsForm('sms_form')"
							class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ Saveb_">
							保存</div>
						<div id="cancel"
							class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ Savebb">
							取消</div>
					</div>
				</form>
			</div>
		</div>
	</div>


	<!--短信设置end-->
	<!--添加链接弹出框start-->
	<div class="rgba_000_5 w1920 h100_ display_none ChoiceLink z_13"
		style="z-index: 13;">
		<div class="w630  p_b40 bgc_fff m_t100 m_l500" style="height: 30vw;">
			<div class="h60 lh60 w630 text-center font16 bgc_f1f3f7">添加短链接</div>
			<input id="linkData_input" type="hidden" value="0">
			<ul class="h53 lh50 bgc_f1f3f7">
				<li onclick="linkDataInput('0')"
					class="f_l h53 w140 text-center bgc_fff TaoBaoLianJie1">淘宝短链</li>
				<!--                                                 <li onclick="linkDataInput('1')" class="f_l h53 w140 text-center bgc_e3e7f0 TaoBaoLianJie1">客户端短链</li> -->
			</ul>
			<form action="#" class="p_l24  p_r20 TaoBaoLianJie2">
				<p class="l_h32 font14 m_b30">
					说明：网址必须是taobao.com、tmall.com、jaeapp.com这三个域名下。<br />
					点击效果,需在淘宝后台ECRM中查看提示：默认网站、后都加空格，可确保手机打开无异常兼容设备识别。另外直接从外部复制粘贴的网址也请务必前后加空格，否则将导致无法正常打开。
				</p>
				<div>
					<input id="linkType" type="hidden" value="店铺首页">
					<div class="f_l m_r10">
						<div class="f_l font14  lh45">类型:</div>
					</div>
					<div class="f_l m_r10">
						<div onclick="linkData('店铺首页')"
							class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK bgc_check_blue DianPuShouYe">
						</div>
						<div class="m_l10 f_l font14  lh45 c_8493a8">店铺首页</div>
					</div>
					<div class="f_l m_r10">
						<div onclick="linkData('商品链接')"
							class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK ShopLianJie AddSpecified_link">
						</div>
						<div class="m_l10 f_l font14  lh45 c_8493a8">商品链接</div>
					</div>
					<div class="f_l">
						<div onclick="linkData('活动页链接')"
							class="m_t13 cursor_ one_check_only w20 h20 bgc_e0e6ef b_radius5 f_l GXK HuoDongLianJie">
						</div>
						<div class="m_l10 f_l font14  lh45 c_8493a8">活动页链接</div>
					</div>
					<div class="f_r m_r15">
						<a
							href="https://crm.bbs.taobao.com/detail.html?spm=a210m.7802402.0.0.zeU4Ci_a210m.7802402.0.0.lk6D3P_a210m.7802402.0.0.Dd9Nw4&postId=1622316"
							target="_blank">
							<div class="m_l10 f_l font14 c_00a0e9 lh45 cursor_">
								淘宝短链接使用教程</div>
						</a>
					</div>
				</div>
				<div class="clear"></div>
				<div class="m_t20 ShopId display_none">
					<span class="font14 p_r20">商品ID:</span> <input id="commodityId"
						class="h42 l_h42 p_l15 outline_none border0 b_radius5 w274 bgc_f4f6fa"
						type="text" />
				</div>
				<div class="HuoDongWangZhi display_none">
					<div class="m_b10">
						<p class="f_l">活动网址 :</p>
						<input id="activityUrl"
							class="w500 h65 border0 bgc_f4f6fa b_radius8 f_l" type="text"
							value="">
					</div>
					<div class="clear font14 c_cbcdcf m_t15 p_t10">
						常见问题：PC端自定义活动页面，因淘宝无线端没有对应的H5活动页面如直接转化可能会导致跳转至手淘店铺首页，解决方法：建议先到无线运营中心->无线店铺->店铺装修->自定义页面”生成H5活动页面，再进行转化。
					</div>
				</div>
			</form>
			<form action="#"
				class="p_l24 p_t30 p_r40 p_l40 display_none TaoBaoLianJie2">
				<p class="l_h32 font14 m_b30">
					提示：默认网址前、后都加空格，可确保手机打开无异常兼容设备识别。另外直接从外部复制
					粘贴的网址也请务必前后加空格，否则将导致无法正常打开。</p>
				<textarea id="kehuduan"
					class="w540 h160 b_radius5 p_l15 p_t15 c_cecece outline_none border0 bgc_f4f6fa"
					placeholder="请输入网址："></textarea>
			</form>
			<!--确定保存start-->
			<div class="w630 h42 m_t20 margin0_auto">
				<div class="w300 margin0_auto">
					<div onclick="linkSubmit()"
						class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ LinkOut">
						确定</div>
					<div
						class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l50 f_l cursor_ LinkOut">
						取消</div>
				</div>
			</div>
			<!--确定保存end-->
		</div>
	</div>
	<!--添加链接弹出框start-->
	<!--另存为短语库start-->
	<div class="rgba_000_5 w1920 h100_ display_none ChoicePhrase z_13"
		style="z-index: 13;">
		<div class="w490 h230 p_b10 bgc_fff m_t250 m_l500">
			<div class="h64 lh64 m_b10 text-center font16">另存为短语库</div>
			<div class="w426 margin0_auto">
				<input id="saveSms"
					class="w411 h42 b_radius5 border0 outline_none bgc_f4f6fa p_l15 c_cecece"
					type="text" placeholder="模板名称" />
			</div>

			<!--确定保存start-->
			<div class="w490 h42 m_t36 margin0_auto">
				<div class="w260 margin0_auto">
					<div onclick="saveTemplate()"
						class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ PhraseOut">
						确定</div>
					<div
						class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l50 f_l cursor_ PhraseOut">
						取消</div>
				</div>
			</div>
			<!--确定保存end-->

		</div>
	</div>
	<!--另存为短语库end-->

	<!--引用短语库 start-->
	<div
		class="rgba_000_5 w1920 h100_  PhraseLibrary display_none z_13 text_model_window "
		style="z-index: 13;">
		<div class="w1000 h580 p_b33 bgc_fff m_t100 m_l300">
			<div class="w1000 h103 p_t20 bgc_f1f3f7 font16">
				<div class="text-center w1000">引用模板</div>
				<ul class="h40 p_t41 font14 m_l34">
					<li onclick="findTemplate('默认')"
						class="f_l w140 text-center h40 l_h40 bgc_fff    cursor_ DYK1">系统短语库</li>
					<li onclick="findTemplate('自定义')"
						class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 cursor_ DYK1">自定义短语库</li>
				</ul>
			</div>
			<div class="w936 h546 margin0_auto DYK2">
				<div class="font14 c_384856 h45 l_h45">
					<div class="clear f_l default">
						<div onclick="findTemplate('默认')"
							class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK GXK1 bgc_check_blue">
						</div>
						<div class="m_l10 f_l font14 c_384856">默认</div>
					</div>
					<div class="f_l m_l10">
						<div onclick="findTemplate('常规版')"
							class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
						</div>
						<div class="m_l10 f_l font14 c_384856">常规版</div>
					</div>
					<div class="f_l m_l10">
						<div onclick="findTemplate('创意版')"
							class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
						</div>
						<div class="m_l10 f_l font14 c_384856">创意版</div>
					</div>
					<div class="f_l m_l10">
						<div onclick="findTemplate('幽默版')"
							class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
						</div>
						<div class="m_l10 f_l font14 c_384856">幽默版</div>
					</div>
					<div class="f_l m_l10">
						<div onclick="findTemplate('萌版')"
							class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
						</div>
						<div class="m_l10 f_l font14 c_384856">萌版</div>
					</div>
					<div class="f_l m_l10">
						<div onclick="findTemplate('按时间更新')"
							class="m_t13 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ GXK">
						</div>
						<div class="m_l10 f_l font14 c_384856">按时间更新</div>
					</div>
				</div>
				<div class="clear"></div>
				<div class="h300 overflow_auto">
					<table border="0" class="font14 m_t13 temp_table1">
						<tr class="bgc_e3e7f0 temp_tr1">
							<td class="w77 h50 text-center">序号</td>
							<td class="w500 h50 text-center">短信内容</td>
							<td class="w128 h50 text-center">热度</td>
							<td class="w120 h50 text-center"></td>
						</tr>
					</table>
				</div>
				<!-- <table border="0" class="font14">
                                                    <thead>
                                                        <tr class="bgc_e3e7f0">
                                                            <td class="w77 h50 text-center" >序号</td>
                                                            <td class="w602 h50 text-center">短信内容</td>
                                                            <td class="w128 h50 text-center">热度</td>
                                                            <td class="w120 h50 text-center"></td>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr class="bgc_fafafa">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                            <td class="w120 h50 text-center">
                                                            	<div class="w100 h42 l_h42 bor_00a0e9 margin0_auto b_radius5 c_00a0e9">使用</div>
                                                            </td>
                                                        </tr>
                                                        <tr class="bgc_f4f4f4">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                            <td class="w120 h50 text-center"></td>
                                                        </tr>
                                                        <tr class="bgc_fafafa">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                            <td class="w120 h50 text-center"></td>
                                                        </tr>
                                                        <tr class="bgc_f4f4f4">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                            <td class="w120 h50 text-center"></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                分页样式start
                                                <div class="w936 h24 m_t10 font14 c_8493a8 ">
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
                                                </div> -->
				<!--分页样式end-->
				<!--确定保存start-->

				<div class="w936 h42 m_t50 margin0_auto">
					<div class="w214 margin0_auto">
						<div
							class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ LibraryOut">
							确定</div>
						<div
							class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ LibraryOut">
							取消</div>
					</div>
				</div>

				<!--确定保存end-->
			</div>

			<div class="w936 h546 margin0_auto DYK2 display_none">
				<div class="h300 overflow_auto clear">
					<table border="0" class="font14 m_t45 temp_table2">
						<tr class="bgc_e3e7f0 temp_tr2">
							<td class="w77 h50 text-center">序号</td>
							<td class="w258 h50 text-center">模板名称</td>
							<td class="w595 h50 text-center">短信内容（展开全部）</td>
							<td class="w100 h50 text-center"></td>
						</tr>
					</table>
				</div>
				<!-- <table border="0" class="font14 m_t45">
                                                    <thead>
                                                        <tr class="bgc_e3e7f0">
                                                            <td class="w77 h50 text-center" >序号</td>
                                                            <td class="w258 h50 text-center">模板名称</td>
                                                            <td class="w595 h50 text-center">短信内容（展开全部）</td>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr class="bgc_fafafa">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                        </tr>
                                                        <tr class="bgc_f4f4f4">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                        </tr>
                                                        <tr class="bgc_fafafa">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                        </tr>
                                                        <tr class="bgc_f4f4f4">
                                                            <td class="w77 h50 text-center" ></td>
                                                            <td class="w602 h50 text-center"></td>
                                                            <td class="w128 h50 text-center"></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                分页样式start
                                                <div class="w936 h24 m_t10 font14 c_8493a8 z_8">
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
                                                </div> -->
				<!--分页样式end-->
				<!--确定保存start-->

				<div class="w936 h42 m_t50 margin0_auto">
					<div class="w214 margin0_auto">
						<div
							class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ LibraryOut">
							确定</div>
						<div
							class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ LibraryOut">
							取消</div>
					</div>
				</div>

				<!--确定保存end-->
			</div>
		</div>
	</div>
	<!--引用短语库 end-->



	</div>
	</div>
	</div>

	<!-- 引入商品jsp -->
	<%@ include file="/WEB-INF/views/crms/header/itemUtils.jsp"%>

</body>
<script>
	$(function(){
		$(".load_top").load("top.html");
		$(".load_left").load("left.html");
		$('#maxPayment').on("blur",function(){
			var number = $("#maxPayment").val();
			if(number!=null){
				if(number==""){
					validateTwoPrice();
				}else{
					var regex3 = /^\d+(\.\d{0,2})?$/;
					if(!regex3.test(number)){
						 $("#maxPayment").val("");
						confirm("您输入的金额："+number+"不正确，请重新输入！");
					}else{
						validateTwoPrice();
					}
				}
			}
		});
		$('#maxPayment').on("keyup",function(){
			this.value=this.value.match(/\d+(\.\d{0,2})?/g);
		});
		$('#minPayment').on("keyup",function(){
			this.value=this.value.match(/\d+(\.\d{0,2})?/g);
		});
		$('#minPayment').on("blur",function(){
			var number = $("#minPayment").val();
			if(number!=null){
				if(number==""){
					validateTwoPrice();
				}else{
					var regex3 = /^\d+(\.\d{0,2})?$/;
					if(!regex3.test(number)){
						 $("#minPayment").val("");
						confirm("您输入的金额："+number+"不正确，请重新输入！");
					}else{
						validateTwoPrice();
					}
				}
			}
		});
	});
	
	function validateTwoPrice(msg){
		$(priceMSG).empty();
		var maxPayment = $("#maxPayment").val();
		var minPayment = $("#minPayment").val();
		if(maxPayment == null || minPayment == null){
		}else{
			if(maxPayment!="" && minPayment!=""){
				var maxPaymentFloat = parseFloat(maxPayment);
				var minPaymentFloat = parseFloat(minPayment);
				if(maxPaymentFloat<minPaymentFloat){
//						if(msg==""){
//							$("#minPayment").val("");
//						}
					$("#priceMSG").html("<font style='color: red;'>实付金额最大值不能小于最小值</font>");
					return false;
				}
			}
		}
		return true;
	}

function validateTwoTime(msg){
	$(timeMSG).empty();
	var endTime = $("#endTime").val();
	var startTime = $("#startTime").val();
	if(startTime==undefined || endTime == undefined){
	}else{
		if(endTime!="" && startTime!=""){
			var endTimeFloat = parseFloat(endTime.substring(0,2));
			var startTimeFloat = parseFloat(startTime.substring(0,2));
			if(endTimeFloat<startTimeFloat){
				if(msg==""){
					$("#endTime").val("");
				}
				$(timeMSG).html("<font style='color: red;'>结束时间不能小于开始时间</font>");
				return false;
			}else if(endTimeFloat==startTimeFloat){
				if(msg==""){
					$("#endTime").val("");
				}
				$(timeMSG).html("<font style='color: red;'>结束时间不能等于开始时间</font>");
				return false;
			}
		}
	}
	return true;
}


	//提交表单,传入form表单id获取提交表单 
	function submitForm(fomrId){
		var endTime = $("#endTime").val();
		var startTime = $("#startTime").val();
		var maxPayment = $("#maxPayment").val();
		var minPayment = $("#minPayment").val();
		var maxPaymentFloat = parseFloat(maxPayment);
		var minPaymentFloat = parseFloat(minPayment);
		if(startTime != '' && endTime != ''){
			var startTimeFloat = parseFloat(startTime.substring(0,2));
			var endTimeFloat = parseFloat(endTime.substring(0,2));
			if(endTimeFloat<startTimeFloat){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("保存失败，结束时间不能小于开始时间,请重新设置")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
				return false;
			}else if(endTimeFloat==startTimeFloat){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("保存失败，结束时间不能等于开始时间，请重新设置")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
				return false;
			}else if(maxPaymentFloat<minPaymentFloat){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("保存失败，实付金额最大值不能小于最小值，请重新设置")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
				return false;
			}else{
				document.getElementById(fomrId).submit();
			}
		}else{
			document.getElementById(fomrId).submit();
		}
		//document.getElementById(fomrId).submit();
	}
	
	window.onload = function(){
// 		var smsSign = $("#smsSign").val();
// 		if(smsSign==''||smsSign==null){
// 			var content = $("#textContent1").val();
// 			if(content.startsWith("【短信签名】")){
// 				$(".text_area").val($("#textContent1").val());
// 			}else{
// 				$(".text_area").val("【短信签名】"+$("#textContent1").val());
// 			}
// 		}else{
// 			var content = $("#textContent1").val();
// 			var smsSingChange = "【"+smsSign+"】";
// 			if(content.startsWith(smsSingChange)){
// 				$(".text_area").val($("#textContent1").val());
// 			}else{
// 				$(".text_area").val(smsSingChange+$("#textContent1").val());
// 			}
// 		}
		addLength();
		var msg = $("#msg").val();
		if(msg!=null && msg != ""){
			$(".tishi_2").show();
				$(".tishi_2").children("p").text(msg)
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000);
		}
	}
	
	//使用ajax查询商品数据,回显页面
	function findItem(){
		$("#itemId_input").val("");
		$("#itemName_input").val("");
		$("#status_input").val('2');//获取只显示上架
		$("#putaway_div").removeClass('bgc_check_blue');
		findItemByData();
	}
	
	//使用ajax通过查询条件查询商品信息
	var clickFlag = false;
	function findItemByData(){
		if(clickFlag==true){
			return;
		}
		clickFlag = true;
		
		$('.item_tr').siblings().remove();
		 $(".sdg").show();
		var itemId = $("#itemId_input").val();//获取商品ID
		var itemName = $("#itemName_input").val();//关键字(商品名称)
		var status = $("#status_input").val();//获取只显示上架

		
		var url = "${ctx}/item/queryItem";
		$.post(url,{"commodityId":itemId,"name":itemName,"status":status},function (data) {
				clickFlag = false;
				$(".sdg").hide();
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
						 item = "<tr><td class='h60 text-center'><div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'>"
								+"<input type='hidden' value='"+result.numIid+"'/></div></td>"
								+"<td class='h60 text-center'>"
								+imgUrl
								+"</td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>"; 
						 
						$('.item_table').append(item);
					});
					checkItem();
				}
		});
	}
	
	//只显示上架设置内容
	var shieldNum = 0
	$(function(){
		$("#putaway_div").click(function(){
			if(shieldNum%2==0){
				$("#status_input").val('1');
			}else{
				$("#status_input").val('2');
			}
			shieldNum++;
			
		});
	});
	
	//当点击下一步时获得选中的商品id
	function addItemId(){
		var itemIds="";//拼接商品id
		var Idlength=0;//获取商品个数
		var divCheck = $(".item_check_div");
// 		var content = $("#textContent").val();
// 		if(content.indexOf("【${ShopName}】")<0){
// 			content = "【${ShopName}】"+" "+content;
// 		}
// 		$("#textContent").val(content);
		for(var i=0;i<divCheck.length;i++){
			if(divCheck.eq(i).hasClass("bgc_check_blue")){
				Idlength++;
				 var val = divCheck.eq(i).children().val();
				 itemIds+=val+",";
			}
		}
		
		if(Idlength>50){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("商品最多可添加50条 ！！！")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000);
		}else{
			itemIds=itemIds.substring(0,itemIds.length-1);
			//alert(Idlength);
			$("#ItemId_form").val(itemIds);
			
			//获得span标签,并赋值商品个数
			$("#item_span").text(Idlength);
			
			//打开窗口
              $(".selectnx1").show();
              $('.select1').hide();
		}
		
	}
	
	//显示引用短语库
	function findTemplate(smsTemp){
		$('.temp_tr1').siblings().remove();
		$('.temp_tr2').siblings().remove();

		$(".DYK2").eq(0).show()
		$(".DYK2").eq(1).hide()
		
		$(".DYK1").eq(0).addClass("bgc_fff").removeClass("bgc_e3e7f0")
		$(".DYK1").eq(1).removeClass("bgc_fff").addClass("bgc_e3e7f0")
		
		
		if(smsTemp=="默认"){
			$(".default").children(".GXK1").addClass("bgc_check_blue");
			$(".GXK1").parent(".default").siblings().children(".GXK").removeClass("bgc_check_blue")
		}
		$.ajax({
			url:"${ctx}/crms/smsTemplate",
			type:"post",
			data:{"type":smsTemp},
			dataType:"json",
			success:function(data){
				var smsTemplate = data.smsTemplate;
				var appendTemp = "";
				if(smsTemp == "自定义"){
					if(smsTemplate ==null || smsTemplate=="undefined" || smsTemplate=='' ){
						appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
						$('.temp_table2').append(appendTemp);
					}else{
						$.each(smsTemplate,function(i,result){
							appendTemp = "<tr class='bgc_fafafa'><td class='w77 h50 text-center' >"+(i+1)+"</td><td class='w500 h50 text-center'>"+result.name+"</td><td class='w128 h50 text-center'>"+result.content+"</td><td class='w120 h50 text-center'><div  class='w100 h42 l_h42 bor_00a0e9 margin0_auto b_radius5 c_00a0e9 Template_div cursor_'>使用<input type='hidden' value='"+result.content+"' /></div></td></tr>";
							$('.temp_table2').append(appendTemp);
						});
					}
				}else{
					if(smsTemplate ==null || smsTemplate=="undefined" || smsTemplate=='' ){
						appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
						$('.temp_table1').append(appendTemp);
					}else{
						$.each(smsTemplate,function(i,result){
							appendTemp = "<tr class='bgc_fafafa'><td class='w77 h50 text-center' >"+(i+1)+"</td><td class='w500 h50 text-center'>"+result.content+"</td><td class='w128 h50 text-center'>"+result.fashion+"</td><td class='w120 h50 text-center'><div  class='w100 h42 l_h42 bor_00a0e9 margin0_auto b_radius5 c_00a0e9 Template_div cursor_'>使用<input type='hidden' value='"+result.content+"' /></div></td></tr>";
							$('.temp_table1').append(appendTemp);																																																					
						});
					}
				}
			}
		});
	}
	
	//判断短信输入框内容长度，修改计费条数
	function addLength(){
		var contentVal=$("#textContent").val()
		$("#textContent1").val(contentVal);
		//将小手机里的内容末尾添加退订回N 
		if(contentVal == ""){
			$(".text_area_copy").val(contentVal);
		}else{
			$(".text_area_copy").val(contentVal.replace("退订回N","")+"退订回N");
		}
		var len=$(".text_area_copy").val().length;
		document.getElementById("inputNum").innerText=len;
		if(len>70){
			$("#actualDeduction_input").val(Math.ceil(len/67));
			document.getElementById("contePrice").innerText=Math.ceil(len/67);			
		}else{
			$("#actualDeduction_input").val(Math.ceil(len/70));
			document.getElementById("contePrice").innerText=Math.ceil(len/70);
		}
		
		
	}
	//回显短信到编辑部分
	$(function(){
		var textContent1 = $('#textContent1').val();
		$('#textContent').val(textContent1);
		addLength();
	});
	
	
	//光标点击
	function addConsignee(obj,str) {
		var content = $("#textContent").val();
	 	if(content.indexOf(str)>0){
	 		return false;
	 	}
		
	    if (document.selection) {
	        var sel = document.selection.createRange();
	        sel.text = str;
	    } else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
	        var startPos = obj.selectionStart,
	            endPos = obj.selectionEnd,
	            cursorPos = startPos,
	            tmpStr = obj.value;
	        obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
	        cursorPos += str.length;
	        obj.selectionStart = obj.selectionEnd = cursorPos;
	    } else {
	        obj.value += str;
	    }
	    $("#copycontent").html($("#textContent").val());
	    addLength();
	}
	
	//点击使用将模板添加到文本域中
	$(document).on("click", ".Template_div", function () {
		var content = $(this).children().val();
		$("#messageId").val('');
		$(".text_area").val(content);			
		$("#textContent1").val(content);
		$(".text_model_window").hide();		
		addLength();
	});
	
	//另存为短语库
	function saveTemplate(){
		//短语模板的名字
		var smsName = $('#saveSms').val();
		var smsContent = $('.text_area').val();
		$.ajax({
			url:"${ctx}/crms/saveSmsTemplate",
			type:"post",
			data:{"smsName":smsName,"smsContent":smsContent},
			success:function(data){
				if(data.message){
 					$(".tishi_2").show();
 					$(".tishi_2").children("p").text("保存短语成功")
 					setTimeout(function(){ 
 						$(".tishi_2").hide()
 					},3000)
				}else{
 					$(".tishi_2").show();
 					$(".tishi_2").children("p").text("保存短语失败")
 					setTimeout(function(){ 
 						$(".tishi_2").hide()
 					},3000)
				}
			}
		});
	}
	
	//测试短信发送
	$(".ceShiFaSong1").click(function(){
		var phonez=/^1[3|4|5|7|8]\d{9}$/;
		var phone=$(".Phone").val();//获取电话
		var content = $("#textContent1").val();//获取短信内容
		var autograph = $("#smsSign").val();//短信签名
		
		var phoneflag = 1;//手机号发送判断标识
		
	if(content != ""){
		content = content.replace("退订回N","")+"退订回N";
		//判断号码内容是否成功 
		var phoneList = phone.split(",");
		if(phoneList.length <= 5){
			for(var i=0;i<phoneList.length;i++){
				if (phoneList[i].match(phonez)){
					phoneflag = 0;
				} else{
					phoneflag=1;
					break;
				}
			}	
		}else{
			phoneflag=2;
		}	
		
		if(phoneflag == 0){
		 	var url = "${ctx}/crm/test/sendSMS";
			$.post(url,{"content":content,"phone":phone,"autograph":autograph},function (data) {
					if(data.send=='100'){
		 				$(".tishi_2").show();
		 				$(".tishi_2").children("p").text("发送成功!")
		 				setTimeout(function(){ 
		 					$(".tishi_2").hide()
		 				},3000)
					}else{
		 				$(".tishi_2").show();
		 				$(".tishi_2").children("p").text("发送失败!")
		 				setTimeout(function(){ 
		 					$(".tishi_2").hide()
		 				},3000)
					}
			});
		}else if(phoneflag == 1){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("手机号错误!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)   
		}else if(phoneflag==2){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("手机号不能大于5个")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)   
		}
	}else{
		$(".tishi_2").show();
		$(".tishi_2").children("p").text("短信内容不能为空!")
		setTimeout(function(){ 
			$(".tishi_2").hide()
		},3000)  
	}
	
	});
	
	//回显已选择商品选中状态
	function checkItem(){
		var itemidInput = $(".item_check_div");
		var itemIds = $("#appoint_ItemId").val();
		var itemIdList = itemIds.split(",");
		for(var i=0;i<itemidInput.length;i++){
			 for(var j=0;j<itemIdList.length;j++){
				if(itemidInput.eq(i).children().val()==itemIdList[j]){
					itemidInput.eq(i).addClass("bgc_check_blue");
				}
			} 
		}
	}
	
		//提交宝贝设置数据
		//保存短信设置功能 
		function submitSmsForm(fomrId){
			var messageSignature = $('.sms_sign').val();
			$('#smsSign').val(messageSignature);
			var messageContent = $('#textContent').val();
			if(messageContent == "" || messageContent == null){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("短信内容不能为空!!!")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
			}else if(messageContent.indexOf("【" + messageSignature + "】")<0){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("请核对短信签名是否正确!!!")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
			}else{
				$("#textContent1").val(messageContent);
				$('.select1').hide();
	           	$('.selectnx1').hide();
	           	$(".bl1").hide();
	           	$(".Yitianjia").show();
				document.getElementById(fomrId).submit();
			}
	}
		
	//显示指定的商品个数
	$(function(){
		//获得span标签,并赋值商品个数
		var Idlength = $("#appoint_ItemId").val();
		if(Idlength == ''){
			$("#item_span").text(0);
			$('.item_show').show();
		}else{
			var IdlengthList = Idlength.split(",").length;
			$("#item_span").text(IdlengthList);
			$('.item_show').show();
		}
	});
	
	
	//开启关闭设置数据
	$(function(){
		$("#addstatus").click(function(){
			var buttonId = $("#buttonId").val();
			if(buttonId == '0'){
				$("#buttonId").val('1');
			}else if(buttonId == '1'){
				$("#buttonId").val('0');
			}
			addstatus();
		});
	});
	//显示开启或关闭
	$(function(){
		var buttonId = $("#buttonId").val();
		if(buttonId == '0'){
			$("#addstatus").val("已开启宝贝关怀");
		}
		if(buttonId == '1'){
			$("#addstatus").val("已关闭宝贝关怀");
		}
	});
	//获取开启或关闭状态
	function addstatus(){
		var buttonId = $("#buttonId").val();
		var orderSetupId = $("#orderSetupId").val();
		if(orderSetupId == ''){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请将以上数据设置完毕")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)   
		}else{
			var url="${ctx}/placeAnOrderCare/openOrCloseStatus";
			$.post(url,{"buttonId":buttonId,"orderSetupId":orderSetupId},function (data) {
				if(data.status != null){
					if(data.status == '0'){
		 				$(".tishi_2").show();
		 				$(".tishi_2").children("p").text("开启成功")
		 				setTimeout(function(){ 
		 					$(".tishi_2").hide()
		 					loadMap();
		 				},3000)
						$("#addstatus").val("已开启宝贝关怀");
					}else if(data.status == '1'){
		 				$(".tishi_2").show();
		 				$(".tishi_2").children("p").text("关闭成功")
		 				setTimeout(function(){ 
		 					$(".tishi_2").hide()
		 					loadMap();
		 				},3000)
						$("#addstatus").val("已关闭宝贝关怀");
					}
				}else{ 
	 				$(".tishi_2").show();
	 				$(".tishi_2").children("p").text("开启或关闭失败")
	 				setTimeout(function(){ 
	 					$(".tishi_2").hide()
	 					loadMap();
	 				},3000)
				}
			});
		}
	}
	
	
	
	//获得生成短连接的类型
	function linkData(data){
		$('#linkType').val(data);
	}
	//短连接生成
	 function linkDataInput(data){
		$("#linkData_input").val(data);
	}
	//根据生成链接类型，将请求发送到后台生成短链接
	function linkSubmit(){
		var linkData = $("#linkData_input").val();
		var linkType = $("#linkType").val();//链接类型
		var commodityId = $("#commodityId").val();//商品id
		var activityUrl = $("#activityUrl").val();//活动网址
		var kehuduan = $("#kehuduan").val();
		if(linkData == 0){
			var url = "${ctx}/GenerateLinkTo/generateLink";
			$.post(url,{"linkType":linkType,"commodityId":commodityId,"activityUrl":activityUrl,"linkName":linkType},function (data) {
				if(data.link != null){
					 $("#textContent").val(startContent+data.link+endContent);
					 addLength();
				}
			});
		}else{
			var textContent = $("#textContent").val();
			$("#textContent").val(textContent+kehuduan);
			addLength();
		}
		
		
	}
	
	
	//点击取消获取之前短信放入内容
	
	$("#cancel").click(function(){
		var contentShow = $("#contentShow").val();
		$("#textContent").val(contentShow);
		$(".text_area_copy").val(contentShow);
		addLength();
		
		$(document.body).css({
			  "overflow-x":"auto",
			  "overflow-y":"auto"
			  });
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
	 //提交form表单
	function findOrderSendRedord(formId){
		document.getElementById(formId).submit();
	};
</script>
</html>
