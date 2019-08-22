<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>中差评安抚</title>
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
<script type="text/javascript" src="${ctx}/crm/js/cuifutxing.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/zhongchaping.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js"></script>
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
			<div class="w1660 m_t70 m_l30 ">
				<div class="w100_ h50 lh50 bgc_fff font18">
					<div class="f_l h50 w4 bgc_1d9dd9"></div>
					<div class="f_l m_l15 c_384856" style="font-size:1vw;">订单中心</div>
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


				<div class="w1660  bgc_f1f3f7 c_384856">
					<div class="p_l40 p_t27">
						<p class="font20" id="tit">中差评安抚</p>
						<p class="font14 p_t8">当买家给中差评时，发送安抚短信！(淘宝规则：双方互评后才能发送短信,为了保证短信发送的及时性,请在买家确认后及时评价)
						</p>
						<div class="h40">
							<div
								class="w110 h40 l_h40 f_r bgc_fff m_r15 text-center  cursor_ ZhanKai">
								<img class="m_r10 JTX display_none"
									src="${ctx}/crm/images/箭头下.png" /> <img class="m_r10 JTS"
									src="${ctx}/crm/images/箭头上.png" /> <span class="LiuChengText">收回流程</span>
							</div>
						</div>
					</div>
					<%@ include file="/WEB-INF/views/crms/header/liucheng.jsp" %>
					<ul class="h40 p_t40 font14 p_l40">
						<li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/appraiseAmend/showAppraiseAmend">中差评查看</a></li>
						<li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/appraiseMonitoring/showAppraiseMonitoring">中差评监控</a></li>
						<li class="f_l w140 text-center h40 l_h40 bgc_fff  cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/appraisePacify/showAppraisePacify">中差评安抚</a></li>
						<li
							class="f_l w140 text-center h40 l_h40 bgc_e3e7f0 ping_1 cursor_"><a
							class="c_384856 display_block"
							href="${ctx}/crms/appraise/middleBadAssessment?filtrateTime=最近7天">中差评统计</a></li>
						<%-- <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseSet/showAppraiseCauseSet">中差评原因设置</a></li> --%>
						<%-- <li class="f_l w140 text-center h40 l_h40 bgc_e3e7f0  cursor_"><a class="c_384856 display_block"  href="${ctx}/appraiseCauseAnalyze/showAppraiseCauseAnalyze">中差评原因分析</a></li> --%>
					</ul>

				</div>









				<!-- 中差评安抚    -->
				<div class=" ping_2 ">
					<div class="w1660 h1000 bgc_fff ">
						<div class="w1290 h1000 p_l70 font14">
							<div class="w1290 h50 p_t40 ">
								<div id="jk" onclick="showPacify()"
									class=" w150 h40 border_d9dde5 b_radius5 text-center f_l l_h40 c_fff cursor_ bgc_00a0e9">
									中差评安抚设置</div>
								<div id="ck" onclick="submitForm('queryForm');"
									class=" w150 h40 border_d9dde5 b_radius5  text-center f_l l_h40 c_8493a8 cursor_">
									查看发送记录</div>
							</div>

							<!--  中差评安抚设置  -->
							<div id="jkShow" class=" anfu_2">
								<div class="w1160 h80  m_t10">
									<div class="f_l font14">
										<span>温馨提示：</span><br /> <span>1、该功能不会给开启前的中差评发送短信</span><br />
										<span>2、因淘宝规则限制，需要双方互评后才可以发送预警，建议您开启“自动评价”，确保中差评能够及时提醒！</span>
									</div>
									<a class="w100 display_block f_l c_00a0e9"
										href="javascript:openNewWin(0)">
										<div
											class=" w100 h40 border_00a0e9 b_radius5 text-center l_h40 tk cursor_ font14  m_l20">
											点我设置</div>
									</a>
								</div>




								<form id="monitoringForm"
									action="${ctx}/appraisePacify/saveOrUpdatePacify" method="post">
									<input id="smsSign" name="smsSign" type="hidden" >
									<div class="w1160 h40  m_b10">
										<span class="f_l l_h40">是否开启：</span> <input type="hidden"
											name="id" value="${orderRateCareSetup.id }" /> <input
											id="openMonitoring" type="hidden" name="status"
											value="${orderRateCareSetup.status }" />

										<div id="img0" onclick="openMonitoring('0')"
											class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
											<img class="cursor_ one_check_ display_none m_b10 w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l m_r35 l_h40">是</div>

										<div id="img1" onclick="openMonitoring('1')"
											class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
											<img class="cursor_ one_check_ display_none m_b10 w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l m_r35 l_h40">否</div>
									</div>
									<div class="w1160 h80 ">
										<span>发送时间：当买卖双方互评时,若检测到有中差评，发送短信安抚买家</span>
										<div class="m_l70 m_t10">


											<div class="f_l m_r15 l_h40">每天:</div>
											<div class="wrap f_l h40 lh40 m_r20">
												<div class="nice-select h40 lh40" name="nice-select">
													<input class="h40 lh40" type="text" name="sendTimeOne"
														id="startTime" value="${orderRateCareSetup.sendTimeOne }"
														readonly>
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
											<div class="f_l l_h40 m_r20">至</div>
											<div class="wrap f_l h40 lh40 m_r20">
												<div class="nice-select h40 lh40" name="nice-select">
													<input class="h40 lh40" type="text" name="sendTimeTwo"
														id="endTime" value="${orderRateCareSetup.sendTimeTwo }"
														readonly>
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
											<div class="f_l l_h45 ">发送短信</div>
											<div id="timeMSG" class="col-md-6 lh45"
												style="margin-left: 37vw; width: 12vw"></div>
										</div>
									</div>
									<div class="w1160 h40 m_t20">
										<span class="f_l l_h40">提醒条件：</span>
										<div
											class="cursor_ one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
											<img id="money_img" class="cursor_ one_check_ display_none m_b10 w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l m_r35 l_h40">实付金额满</div>
										<input
											class="bgc_e0e6ef border_d9dde5 b_radius5 w150 h40 f_l m_r20"
											name="moneyOne" id="minPayment"
											value="${orderRateCareSetup.moneyOne }"
											onkeyup="value=value.replace(/[^\d.]/g,'')" />
										<div class="f_l l_h40 m_r20">~</div>
										<input
											class="bgc_e0e6ef border_d9dde5 b_radius5 w150 h40 f_l  m_r20"
											name="moneyTwo" id="maxPayment"
											value="${orderRateCareSetup.moneyTwo }"
											onkeyup="value=value.replace(/[^\d.]/g,'')" />
										<div class="f_l l_h40 m_r20">元</div>
										<div id="priceMSG" class="col-md-6 lh45"
											style="margin-left: 32vw; width: 15vw"></div>
									</div>
									<div class="w1160 h130 m_t20">
										<div class="w1160 h40">
											<span class="f_l l_h40">过滤条件：</span>
											<%-- <div id="hideImg" onclick="hideImg()"
												class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
												<img class="cursor_ one_check_ display_none m_b10 w20 h20"
													src="${ctx}/crm/images/black_check.png" />
											</div> --%>
											<div class="f_l m_r35 l_h40">不发送标旗订单:</div>
											<input type="hidden" id="sellerFlag" name="flagcolor"
												value="${orderRateCareSetup.flagcolor }" />
											<div class="f_l l_h40">
												<div class="f_l ">
													<div id="img2" onclick="sellerFlag()"
														class="cursor_ one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15 bgc_e0e6ef check_div"
														style="margin-top: 0.3vw;">
														<img
															class="cursor_ one_check_ display_none m_b10 check_img w20 h20"
															src="${ctx}/crm/images/black_check.png" /> <input
															type="hidden" value="1" />
													</div>

													<img src="${ctx}/crm/images/redq.png"
														style="margin-top: 0.3vw;">

												</div>
												<div class="f_l  m_l10">
													<div id="img3" onclick="sellerFlag()"
														class="cursor_ one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15 bgc_e0e6ef check_div"
														style="margin-top: 0.3vw;">
														<img
															class="cursor_ one_check_ display_none m_b10 check_img w20 h20"
															src="${ctx}/crm/images/black_check.png" /> <input
															type="hidden" value="2" />
													</div>
													<img src="${ctx}/crm/images/yellowq.png"
														style="margin-top: 0.3vw;">
												</div>
												<div class="f_l  m_l10">
													<div id="img4" onclick="sellerFlag()"
														class="cursor_ one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15 bgc_e0e6ef check_div"
														style="margin-top: 0.3vw;">
														<img
															class="cursor_ one_check_ display_none m_b10 check_img w20 h20"
															src="${ctx}/crm/images/black_check.png" /> <input
															type="hidden" value="3" />
													</div>
													<img src="${ctx}/crm/images/greenq.png"
														style="margin-top: 0.3vw;">
												</div>
												<div class="f_l  m_l10">
													<div id="img5" onclick="sellerFlag()"
														class="cursor_ one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15 bgc_e0e6ef check_div"
														style="margin-top: 0.3vw;">
														<img
															class="cursor_ one_check_ display_none m_b10 check_img w20 h20"
															src="${ctx}/crm/images/black_check.png" /> <input
															type="hidden" value="4" />
													</div>
													<img src="${ctx}/crm/images/blueq.png"
														style="margin-top: 0.3vw;">
												</div>
												<div class="f_l  m_l10">
													<div id="img6" onclick="sellerFlag()"
														class="cursor_ one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15 bgc_e0e6ef check_div"
														style="margin-top: 0.3vw;">
														<img
															class="cursor_ one_check_ display_none m_b10 check_img w20 h20"
															src="${ctx}/crm/images/black_check.png" /> <input
															type="hidden" value="5" />
													</div>
													<img src="${ctx}/crm/images/violetq.png"
														style="margin-top: 0.3vw;">
												</div>
											</div>
										</div>

										<div class="w1160 h40" style="margin-left: 4.1vw;">
											<%-- <div id="hideImg2" onclick="hideImg2()"
												class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef">
												<img class="cursor_ one_check_ display_none m_b10 w20 h20"
													src="${ctx}/crm/images/black_check.png" />
											</div> --%>
											<div class="f_l m_r35 l_h40">不发送订单来源:</div>
											<input type="hidden" id="orderSource" name="orderSource"
												value="${orderRateCareSetup.orderSource }" />
											<div class="f_l  ">
												<div id="img7" onclick="orderSource()"
													class="cursor_ one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef check_div_order">
													<img
														class="cursor_ one_check_ display_none m_b10 check_img check_img_order w20 h20"
														src="${ctx}/crm/images/black_check.png" /> <input
														type="hidden" value="0" />
												</div>
												<div class="f_l m_r35 l_h40">PC端(电脑)</div>
											</div>
											<div class="f_l  m_l10">
												<div id="img8" onclick="orderSource()"
													class="cursor_ one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef check_div_order">
													<img
														class="cursor_ one_check_ display_none m_b10 w20 h20 check_img check_img_order"
														src="${ctx}/crm/images/black_check.png" /> <input
														type="hidden" value="1" />
												</div>
												<div class="f_l m_r35 l_h40">手机端</div>
											</div>
											<div class="f_l  m_l10">
												<div id="img9" onclick="orderSource()"
													class="cursor_ one_check_only1 w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef check_div_order">
													<img
														class="cursor_ one_check_ display_none m_b10 check_img check_img_order w20 h20"
														src="${ctx}/crm/images/black_check.png" /> <input
														type="hidden" value="2" />
												</div>
												<div class="f_l m_r35 l_h40">聚划算</div>
											</div>




											<%-- <div id="img7" onclick="orderSource()" class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef check_div_order">
											        <img class="cursor_ one_check_ display_none m_b10 check_img_order" src="${ctx}/crm/images/black_check.png" />
											        <input type="hidden" value="0" />
											    </div>
											    <div class="f_l m_r35 l_h40">
											    	PC端(电脑)
											    </div>
											    <div id="img8" onclick="orderSource()" class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef check_div_order">
											        <img class="cursor_ one_check_ display_none m_b10 check_img_order" src="${ctx}/crm/images/black_check.png" />
											        <input type="hidden" value="1" />
											    </div>
											    <div class="f_l m_r35 l_h40">
											    	手机端
											    </div>
											    <div id="img9" onclick="orderSource()" class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef check_div_order">
											        <img class="cursor_ one_check_ display_none m_b10 check_img_order" src="${ctx}/crm/images/black_check.png" />
											        <input type="hidden" value="2" />
											    </div>
											    <div class="f_l m_r35 l_h40">
											    	聚划算
											    </div> --%>
										</div>

										<div class="w1160 h40 m_t10" style="margin-left: 4.1vw;">
											<div id="hideImg3"
												class="cursor_ w20 h20 border_d9dde5 b_radius5 f_l m_r15 m_t10 bgc_e0e6ef CustomArea">
												<img id="locality_img" class="cursor_ display_none m_b10 w20 h20"
													src="${ctx}/crm/images/black_check.png" />
											</div>
											<div class="f_l m_r35 l_h40">当收货地区是&nbsp;&nbsp;</div>
											<input id="locality" type="hidden" name="locality"
												value="${orderRateCareSetup.locality }" /> <input
												id="region"
												class="bgc_e0e6ef border_d9dde5 b_radius5 w300 h40 f_l areaSelect" />
											<div class="f_l l_h40 ">&nbsp;时,短信不提醒!</div>

										</div>
									</div>

									<div class="w1160 h222 m_t10">
										<span class="f_l l_h40">短信内容：</span>
										<div class="f_l w1000 h222 bgc_f4f6fa radius10">
											<input id="sms_content_id" class="sms_content" type="hidden"
												name="content" value="${orderRateCareSetup.content }" />
											<textarea id="sms_content_text"
												class="w1000 h222 outline_none radius10 border0 bgc_f4f6fa p_l20 p_t15 p_r20 sms_content areaSelect"
												placeholder="亲爱的卖家昵称，您已下单成功，付款前请勿相信不法分子冒充我司客服电话或旺旺QQ实施的行骗行为，有问题可联系客服哦~"><c:if
													test="${orderRateCareSetup.content!=null}">${orderRateCareSetup.content}</c:if></textarea>
										</div>
									</div>

								</form>

								<div class="w130 h40 m_t40 p_l1000" onclick="template('安抚')">
									<div
										class="w130 h40 lh40  b_radius5 border_00a0e9 c_00a0e9 tk text-center margin0_auto cursor_ change_text_btn  duanxin_1 position_relative">修改短信内容</div>
								</div>
								<div class="w1160 h120 p_l180 p_t20">
									<div onclick="submitFormIsSet('monitoringForm')"
										class="w226 h40 lh40  b_radius5 border_00a0e9 c_00a0e9 tk text-center margin0_auto cursor_">保存设置</div>
								</div>
							</div>

							<!-- 查看短信发送记录  -->
							<div id="ckShow" class=" anfu_2 display_none">
								<form id="queryForm"
									action="${ctx}/sendRecord/orderCenterRecord"
									method="post">
									<div class="w1290 h40 m_t20">
										<input type="hidden" name="recordFlag" value="9">
										<div class="f_l m_r15 l_h40">买家昵称:</div>
										<input
											class="bgc_f4f6fa border_d9dde5 b_radius5 w250 h40 f_l m_r35"
											name="buyerNick" value="${buyerNick }" />
										<div class="f_l m_r15 l_h40">订单号:</div>
										<input id="orderInput" onkeyup="isNumber()"
											class="bgc_f4f6fa border_d9dde5 b_radius5 w350 h40 f_l m_r35"
											name="orderId" value="${orderId }" />
										<div class="f_l m_r15 l_h40">手机号码:</div>
										<input
											class="bgc_f4f6fa border_d9dde5 b_radius5 w250 h40 f_l m_r35"
											name="recNum" value="${recNum }" onkeyup="this.value=this.value.replace(/\D/g, '')" onblur="this.value=this.value.replace(/\D/g, '')" maxlength="11" />
									</div>
									<div class="w1290 h50  p_t20 m_b20">
										<div class="f_l m_r15 l_h40">发送时间:</div>

										<div class="f_l position_relative">
											<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10"
												type="text" id="tser01" name="bTime" value="${bTime }"
												readonly="true"
												onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val) {valiteTwoTime();}})">
											<img style="width: 1vw;"
												class="position_absolute right20 top15"
												src="${ctx}/crm/images/date_copy.png" />
										</div>

										<div class="f_l l_h40">~</div>

										<div class="f_l position_relative">
											<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40"
												type="text" id="tser02" name="eTime" value="${eTime }"
												readonly="true"
												onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val) {valiteTwoTime();}})">
											<img style="width: 1vw;"
												class="position_absolute right50 top15"
												src="${ctx}/crm/images/date_copy.png" />
										</div>


										<div onclick="submitForm('queryForm');"
											class="w100 h40 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_ l_h40 m_r40">
											查询</div>
										<input type="hidden" id="flag" name="flag" value="${flag }" />
									</div>
								</form>
								<div class="w1290 font14">
									<table class="text-center">
										<tr class="h50 bgc_f1f3f7 font12">
											<th class="w195 ">买家昵称</th>
											<th class="w195 ">订单编号</th>
											<th class="w195 ">手机号码</th>
											<th class="w195 ">发送时间</th>
											<th class="w195 ">短信内容</th>
											<th class="w195 ">字数/计费</th>
										</tr>
										<c:if test="${pagination.list.size() ==0}">
											<tr>
												<td class="w75 text-center" align="center" colspan="6">暂时没有相关数据!</td>
											</tr>
										</c:if>
										<c:if test="${pagination != null}">
											<c:forEach items="${pagination.list}" var="smsSendRecord"
												varStatus="status">
												<tr class="bgc_fafafa h50 w1155">
													<td>${smsSendRecord.buyerNick}</td>
													<td>${smsSendRecord.orderId}</td>
													<td>${smsSendRecord.recNum}</td>
													<td><fmt:formatDate value="${smsSendRecord.sendTime}"
															pattern="yyyy-MM-dd HH:mm:ss" /></td>
													<td>
														<div class="w195 margin0_auto h50 lh50 one_line_only text_detail">
															<span id="content${status.index+1 }">${smsSendRecord.content}</span>	
			                                    		</div>
													</td>
													<td><span id="count${status.index+1 }"></span></td>
												</tr>
											</c:forEach>
										</c:if>
									</table>
								</div>

								<div class="w1160 h24 m_t22 font14 c_8493a8 m_b40 m_t20">
									<!--  <div class="f_r w470 h24 l_h22 font12"> -->
									<c:if test="${pagination != null}">
										<c:forEach items="${pagination.pageView}" var="page">
		                                    		${page}
		                                    	</c:forEach>
									</c:if>
									<!-- </div> -->
								</div>

							</div>
						</div>

					</div>
				</div>









			</div>



			<div
				class="w100_ h1000 rgba_000_5 position_fixed top0 display_none z_10 duanxin_2">

				<div class=" position_absolute top70 left100">
					<div class="w1102 h718 bgc_f1f3f7 change_text ">
						<img class=" f_r w40 h40 close_duanxin_2 cursor_"
							src="${ctx}/crm/images/chazi.png" />
						<div class="w1102 h50 text-center l_h50">选择短信模板</div>
						<div class="w1102 h668 ">
							<div class="w495 h668 bgc_fff f_l">
								<div class="w495" style="height: 4.4vw;">
									<div class="w495 h20 text-center p_t15 font14">编辑内容</div>
									<!-- <div class="w495 h20 c_red font14 text-center">若含有促销词如:微信、QQ、店庆等,请务必在短信内容末尾添加“退订回N、td”</div> -->
								</div>
								<div class="w458 h363 bgc_fff">
									<textarea id="text_area_sms" maxlength="500" onkeyup="addLength()"
										class="bgc_f4f6fa text_area_ border_d9dde5 b_radius5 w458 h363 m_l15 text_area_sms">${orderRateCareSetup.content!=null?orderRateCareSetup.content:'亲爱的卖家昵称，您已下单成功，付款前请勿相信不法分子冒充我司客服电话或旺旺QQ实施的行骗行为，有问题可联系客服哦~'}</textarea>
									<div class="h30 w458  m_l20 c_bfbfbf"
										style="margin-top: -1.5vw;">
										已输入：<span id="inputNum" class="text_count">0</span> 当前计费：<span id="contePrice">0</span>
										 <a href="javascript:openNewWin(1)" class="c_00a0e9 cursor_  m_l10 guize1" target="_blank">计费规则</a>
										<span style="padding-left: 6vw;">退订回N</span>
									</div>

									<div
										class="w420 h80 bgc_fff position_absolute left45 bottom250 guize2 display_none">
										<p class="font12">1、单条70个字，超出70个字将按照67个字每条计算</p>
										<p class="font12">2、一个汉字、数字、字母、空格都算一个字</p>
										<p class="font12">3、带标签的短信按实际发出的长度计算</p>
									</div>











								</div>
								<div class="w495 h40 m_t10 m_l20">
									<span class="f_l l_h40">插入标签：</span>
									<div
										onclick="addConsignee(document.getElementById('text_area_sms'),'【收货人】');"
										class="w100 h40 b_radius5 text-center f_l l_h40 border_00a0e9 c_00a0e9 tk cursor_ font14  m_l10">
										收货人</div>
									<div onclick="addConsignee(document.getElementById('text_area_sms'),'【订单编号】');"
										class="w100 h40 b_radius5 text-center f_l l_h40 border_00a0e9 c_00a0e9 tk cursor_ font14 m_l10 tishi_1_ ">
										订单编号</div>
									<div
										onclick="addConsignee(document.getElementById('text_area_sms'),'【买家昵称】');"
										class="w100 h40 b_radius5 text-center f_l l_h40 border_00a0e9 c_00a0e9 tk cursor_ font14 m_l10">
										买家昵称</div>
								</div>
								<div class="w495 h2 m_t10 m_l20">
									<div class="w495 h20 c_red font14 ">提示:
										链接前后须有空格分隔，请勿删除前后空格。</div>
								</div>
								<div class="w495 h40 m_t10 m_l20">
									<span class="f_l l_h40 m_r15 m_l20">短信签名: </span> 
									<a class="f_l l_h40 m_r15 c_00a0e9 cursor_ sms_sign">${ShopName}</a>
									<%-- <input id="addSignature" value="${ShopName}"
										class="bgc_f4f6fa border_d9dde5 b_radius5 w211 h40 f_l" readonly="readonly" /> --%>
								</div>
								<div class="w495 h40 m_t20  ">
									<div onclick="addSmsTempltate()"
										class="m_l100 w100 h40 border_00a0e9 c_00a0e9 close_ tk b_radius5 text-center f_l l_h40 cursor_ font14  m_l10">
										确定</div>
									<div onclick="addMyTempltate()"
										class=" m_l50 w150 h40 border_00a0e9 c_00a0e9 tk b_radius5 text-center f_l l_h40 cursor_ font14  m_l10 sure_1">
										加入我的模板</div>
								</div>
							</div>

							<div class="w595 h668 f_l bgc_fff m_l10 ">
								<div class="w590 h50 bgc_f1f3f7">
									<div onclick="template('安抚')"
										class="w130 h50 text-center out bgc_fff l_h50 f_l cursor_ moban1">
										案例模板</div>
									<div onclick="template('自定义安抚')"
										class="w130 h50 text-center  l_h50 bgc_e3e7f0 f_l cursor_ moban1">
										我的模板</div>
									<div onclick="HistoryTemplate('中差评安抚')"
										class="w150 h50 text-center  l_h50 bgc_e3e7f0 f_l cursor_ moban1">
										历史使用</div>

								</div>

								<div class="in">

									<div class="w560 h490 m_l15 m_t10 overflow_auto">
										<table class="margin0_auto smsTemplate_table">
											<!-- <tr class=" h105 bgc_fafafa">
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
							</tr> -->
										</table>
									</div>

									<!-- <div class="w560 h24 m_t22 font14 c_8493a8 m_b40  ">
		    
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
		            </div>  -->

								</div>


								<div class="in display_none">

									<div class="w560 h490 m_l15 m_t10">
										<table>
											<tr class=" h105 bgc_fafafa">
												<td class="w400 "></td>
												<td class="w160 ">
													<div
														class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
												</td>
											</tr>
											<tr class="h85 bgc_f4f4f4">
												<td class="w400 "></td>
												<td class="w160">
													<div
														class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
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
											<div
												class="f_l w22 h22 bor_e0e6ef text-center m_r4 bgc_00a0e9 c_fff cursor_">1</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">2</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">3</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">4</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">5</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">6</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">7</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">8</div>
											<div class="f_l w22 h22 text-center m_r4">...</div>
											<div class="f_l w45 h22 bor_e0e6ef text-center m_r20 cursor_">下一页</div>
											<div class="f_l w45 h22 m_r4">
												<input
													class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
													type="text" value="">
											</div>
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
													<div
														class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
												</td>
											</tr>
											<tr class="h85 bgc_f4f4f4">
												<td class="w400 "></td>
												<td class="w160">
													<div
														class="w100 h40 m_l30 cursor_ text-center l_h40 border_00a0e9 tk radius10 c_00a0e9">使用</div>
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
											<div
												class="f_l w22 h22 bor_e0e6ef text-center m_r4 bgc_00a0e9 c_fff cursor_">1</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">2</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">3</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">4</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">5</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">6</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">7</div>
											<div class="f_l w22 h22 bor_e0e6ef text-center m_r4 cursor_">8</div>
											<div class="f_l w22 h22 text-center m_r4">...</div>
											<div class="f_l w45 h22 bor_e0e6ef text-center m_r20 cursor_">下一页</div>
											<div class="f_l w45 h22 m_r4">
												<input
													class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
													type="text" value="">
											</div>
											<div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
										</div>
									</div>

								</div>


							</div>
						</div>
					</div>



				</div>
				<div
					class="w500 h180 bgc_fff  tishi_3  position_fixed z_11 left330 top420  display_none">
					<img class=" f_r w40 h40 close_tishi_2 cursor_"
						src="${ctx}/crm/images/chazi.png" />
					<div class="w300 h30 p_t10 p_l70">提示</div>
					<p class="font14 p_l70 w400 p_t10">(订单编号)实际替换后长度约为16个字，实际扣费以发出字数为准，您是否确定添加?</p>
					<p class="font14 p_l70 w400"></p>

					<div class="w232 h40 margin0_auto text-center font16  p_t20">

						<div
							onclick="addConsignee(document.getElementById('text_area_sms'),'【订单编号】');"
							class="f_l b_radius5 m_r30 w100 lh40 bgc_00a0e9 close_this c_fff cursor_ close_inside">
							确定</div>
						<div
							class="f_l b_radius5 w100 h38 lh38 border_cad3df close_this c_cad3df cursor_ close_inside">
							取消</div>

					</div>
				</div>

				<%-- <div class="w500 h180 bgc_fff  sure_2  position_fixed z_11 left330 top420  display_none">
		<img class=" f_r w40 h40 close_sure_2 cursor_" src="${ctx}/crm/images/chazi.png" />
	    	<div class="w300 h30 p_t10 p_l70">提示</div>
	    	<p class="font14 p_l70 w400 p_t10">保存短信模板成功</p>
	    	
	    	
	    	<div class="w232 h40 margin0_auto text-center font16  p_t20">

				<div class="f_l b_radius5 m_r30 w100 lh40 bgc_00a0e9 close_this c_fff cursor_ close_inside">
					确定
				</div>
				
				
			</div>
    	</div>     --%>



			</div>




			<div
				class="rgba_000_5 w100_ area_check_window h100_ position_fixed top0 z_13 display_none">
				<div class="w750 p_b33 bgc_fff m_t100 m_l500 fasf">
					<div class="h50 p_t20 text-center font16">选择所在地区</div>

					<div class="font14 c_384856 h50 lh50 p_l50">

						<div class="f_l w90 1check_box all_check">
							<div
								class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							</div>
							<div class="m_l10 cursor_ f_l font14 c_384856">全选</div>
						</div>


						<!-- <div class="f_l w90 1check_box all_notcheck" >
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 c_384856">全不选</div>
							</div> -->
						<div class="f_l 1check_box far_notcheck">
							<div
								class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
							</div>
							<div class="m_l10 cursor_ f_l font14 c_384856">排除边远山区</div>
						</div>
					</div>
					<div class="font14 c_384856 p_l50 place_check">
						<!--华东-->
						<ul class="h50 gangaotai_ul">
							<li class="f_l 1check_box w90 gangaotai">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									华东</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									上海 <input type="hidden" value="上海市">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									浙江<input type="hidden" value="浙江省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									江苏<input type="hidden" value="江苏省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									安徽<input type="hidden" value="安徽省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									江西<input type="hidden" value="江西省">
								</div>
							</li>
						</ul>

						<!--华北-->
						<ul class="h50 gangaotai_ul">
							<li class="f_l 1check_box w90 gangaotai">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									华北</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									北京<input type="hidden" value="北京市">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									天津<input type="hidden" value="天津市">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									河北<input type="hidden" value="河北省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									山西<input type="hidden" value="山西省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									山东<input type="hidden" value="山东省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									内蒙古<input type="hidden" value="内蒙古自治区">
								</div>
							</li>
						</ul>

						<!--华中-->
						<ul class="h50 gangaotai_ul">
							<li class="f_l 1check_box w90 gangaotai">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									华中</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									湖北<input type="hidden" value="湖北省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									湖南<input type="hidden" value="湖南省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									河南<input type="hidden" value="河南省">
								</div>
							</li>
						</ul>

						<!--华南-->
						<ul class="h50 gangaotai_ul">
							<li class="f_l 1check_box w90 gangaotai">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									华南</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									广东<input type="hidden" value="广东省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									广西<input type="hidden" value="广西壮族自治区">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									福建<input type="hidden" value="福建省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									海南<input type="hidden" value="海南省">
								</div>
							</li>
						</ul>

						<!--东北-->
						<ul class="h50 gangaotai_ul">
							<li class="f_l 1check_box w90 gangaotai">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									东北</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									辽宁<input type="hidden" value="辽宁省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									吉林<input type="hidden" value="吉林省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									黑龙江<input type="hidden" value="黑龙江省">
								</div>
							</li>
						</ul>

						<!--西北-->
						<ul class="h50 gangaotai_ul">
							<li class="f_l 1check_box w90 gangaotai">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									西北</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									陕西<input type="hidden" value="陕西省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									新疆<input type="hidden" value="新疆维吾尔自治区">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									青海<input type="hidden" value="青海省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									宁夏<input type="hidden" value="宁夏回族自治区">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									甘肃<input type="hidden" value="甘肃省">
								</div>
							</li>
						</ul>

						<!--西南-->
						<ul class="h50 gangaotai_ul">
							<li class="f_l 1check_box w90 gangaotai">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									西南</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									四川<input type="hidden" value="四川省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									云南<input type="hidden" value="云南省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									西藏<input type="hidden" value="西藏自治区">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									贵州<input type="hidden" value="贵州省">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									重庆<input type="hidden" value="重庆市">
								</div>
							</li>
						</ul>

						<!--港澳台-->
						<ul class="h50 gangaotai_ul">
							<li class="f_l 1check_box w90 gangaotai">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									港澳台</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									香港<input type="hidden" value="香港特别行政区">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									澳门<input type="hidden" value="澳门特别行政区">
								</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									台湾<input type="hidden" value="台湾省">
								</div>
							</li>
						</ul>

						<!--海外-->
						<ul class="h50 gangaotai_ul">
							<li class="f_l 1check_box w90 gangaotai">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									海外</div>
							</li>
							<li class="f_l 1check_box w90 li_">
								<div
									class="cursor_ m_t15 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l">
								</div>
								<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
									海外<input type="hidden" value="海外">
								</div>
							</li>
						</ul>

					</div>
					<div class="h40 margin0_auto w225">
						<div onclick="customRegion();"
							class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l close_1 cursor_">
							保存</div>
						<div
							class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l close_1 cursor_">
							取消</div>
					</div>
				</div>
			</div>
			<input type="hidden" value="${saveFlag }" id="saveFlag" />
</body>
<script>
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
	//条件数据查询form表单
	function submitForm(fomrId){
		document.getElementById(fomrId).submit();
	}
	
	//点击查看发送记录
	//2.点击"已收好评"
// 	function querySmsRecord(){
// 		window.location.href="${ctx}/appraisePacify/queryAppraisePacify?flag=showRecord";
// 	}
	
	//获取选项卡返回参数显示数据
	$(function(){
		var flag = $('#flag').val();
		if(flag=='showRecord'){
			$("#jkShow").hide();
		 	$("#ckShow").show();
			$("#ck").addClass("bgc_00a0e9 c_fff").removeClass("c_8493a8");
			$("#jk").removeClass("bgc_00a0e9").addClass("c_8493a8");
		}
		
		//显示保存状态
		var saveFlag = $("#saveFlag").val();
		
		if(saveFlag!=null && saveFlag != ""){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text((saveFlag == "success")?"保存成功":"保存失败")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000);
		}
		
		//调用计算费用方法
		countCost();
	});
	
	//页面加载完成后获取短信数据,并计算出数字和费用
	var index=1;
	function countCost(){
		var contentVal = $("#content"+index).text()
		if(contentVal != ''){
			if(contentVal.length>70){
				$("#count"+index).text(contentVal.length+" / "+Math.ceil(contentVal.length/67));
			}else{
				$("#count"+index).text(contentVal.length+" / "+Math.ceil(contentVal.length/70));
			}
		}else if(index==6){
			return;
		}
		index++;
		countCost();
	}
	
	//点击开启按钮是否开启
	function openMonitoring(data){
		$("#openMonitoring").val(data);
	}
	
	
	//点击不发送卖家标旗订单
	function sellerFlag(){
		$("#hideImg").children(".one_check_").hide();
		//延迟加载获取值
		setTimeout(function () { 
			var sellerFlag="";
			var divCheck = $(".check_div");
			for(var i=0;i<divCheck.length;i++){
				var checkImg = divCheck.eq(i).find('.check_img').attr("style");
				if(checkImg == "display: inline;"){
					var val = divCheck.eq(i).find('input').val();
					sellerFlag+=val+","
				}
			}
			sellerFlag=sellerFlag.substring(0,sellerFlag.length-1);
			$("#sellerFlag").val(sellerFlag);
		}, 100);
	}
	
	//点击不发送订单来源
	function orderSource(data){
		$("#hideImg2").children(".one_check_").hide();
		//延迟加载获取值
		setTimeout(function () { 
			var orderSource="";
			var divCheck = $(".check_div_order");
			for(var i=0;i<divCheck.length;i++){
				var checkImg = divCheck.eq(i).find('.check_img_order').attr("style");
				if(checkImg == "display: inline;"){
					var val = divCheck.eq(i).find('input').val();
					orderSource+=val+","
				}
			}
			orderSource=orderSource.substring(0,orderSource.length-1);
			$("#orderSource").val(orderSource);
		}, 100);
	}
	
	//选择收货地区的时获取勾选的地区
	function customRegion (){
		var regionName = "";
		var getprovince=$(".place_check").children().find('.li_').children('.bgc_check_blue').next().children('input');
		for(var i=0;i<getprovince.length;i++){
			regionName+=getprovince.eq(i).val()+",";
		}
		regionName=regionName.substring(0,regionName.length-1);
		//alert(regionName);
		$("#region").val(regionName);
		$("#locality").val(regionName);
	}
	
	//选择短信模板点击事件查询短信数据,安抚 与 我的模板
	function template(smsType){
		$('.smsTemplate_table tr').remove();
	 	//调用ajax查询短信数据
    	var url = "${ctx}/appraisePacify/querySmsTemplate";
		$.post(url,{"smsType":smsType},function (data) {
			var smsTemplate  = data.smsTemplate;
			var table_tr;
			if(smsTemplate ==null || smsTemplate=="undefined" || smsTemplate=='' ){
				table_tr = "<tr><td class='w150 text-center'>暂时没有相关数据!</td></tr>";
				$('.smsTemplate_table').append(table_tr);
			}else{
				$.each(smsTemplate,function(i,result){
					table_tr = "<tr class='text-center'><td class='w660 h100'>"+result.content+"</td><td class='w145 h100'><div onclick='click_use("+result.id+");' id='click_use"+result.id+"' class='w100 margin0_auto b_radius5 h38 lh38 border_00a0e9 c_00a0e9 tk cursor_'>使用<input id='content' type='hidden' value='"+result.content+"'/></div></td></tr>";
					$('.smsTemplate_table').append(table_tr);
				});
			}
		}); 
	}
	
	//查询短信模板--历史使用
	function HistoryTemplate(type){
		$('.smsTemplate_table tr').remove();
		 	//调用ajax查询短信数据
		var url = "${ctx}/appraisePacify/querySmsHistoryTemplate";
		$.post(url,{"type":type},function (data) {
			var smsTemplate  = data.smsTemplate;
			var table_tr;
			if(smsTemplate ==null || smsTemplate=="undefined" || smsTemplate=='' ){
				table_tr = "<tr><td class='w150 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
				$('.smsTemplate_table').append(table_tr);
			}else{
				$.each(smsTemplate,function(i,result){
					table_tr = "<tr class='text-center'><td class='w660 h100'>"+result.content+"</td><td class='w145 h100'><div onclick='click_use("+result.id+");' id='click_use"+result.id+"' class='w100 margin0_auto b_radius5 h38 lh38 border_00a0e9 c_00a0e9 tk cursor_'>使用<input id='content' type='hidden' value='"+result.content+"'/></div></td></tr>";
					$('.smsTemplate_table').append(table_tr);
				});
			}
		}); 
	}
	
	//评价模板中点击使用将内容添加到textarea中
	function click_use(id){
		$('.text_area_sms').val('');
		var click_use = $("#click_use"+id);
		 var sms= click_use.children().val();
		 $('.text_area_sms').val(sms);
		 addLength();
		 var type = "中差评安抚";
		 $.ajax({
				url:"${ctx}/crms/addHistoryTemp",
				data:{"type":type,"id":id},
				post:"post",
				success:function(data){
					if(data.message){
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("使用成功")
						setTimeout(function(){ 
							$(".tishi_2").hide()
						},3000)
					}
				}
			});
	}
	
	//评价模板内容框字数的改变
	function addLength(){
		var content = $('.text_area_sms').val();
		var length;
		if(content != "" && content !=null){
			length = $('.text_area_sms').val().length+4;
		}else{
			length = 0;
		}
		
		if(length>70){
			document.getElementById('inputNum').innerText = length;
			$("#contePrice").text(Math.ceil(length/67));		
		}else{
			document.getElementById('inputNum').innerText = length;
			if(length==0){
				$("#contePrice").text(0);	
			}else{
				$("#contePrice").text(1); 
			}
			
		}
	}
	
	//点击确定添加短信到短信内容中
		function addSmsTempltate(){
			var text_area_sms = $(".text_area_sms").val();
			var smsSign = $(".sms_sign").text();
			if(text_area_sms == ""){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("短信内容不能为空!!!")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},2000)
			}else if(text_area_sms.indexOf("【" + smsSign + "】")<0){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("请核对短信签名是否正确!!!")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},2000)
			}else{
				 $(".duanxin_2").hide();
				 $(".sms_content").val(text_area_sms);
			}
		}
	
	//光标添加短信的变量
	//光标点击
		function addConsignee(obj,str) {
			var content = $("#text_area_sms").val();
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
		    
		    addLength();
		}
	
	//加入我的模板
	function addMyTempltate(){
	 	var content = $("#text_area_sms").val();
		var type = "自定义安抚";
		var url = "${ctx}/appraisePacify/addMyTempltate";
		if(content != ''){
			$.post(url,{"type":type,"content":content},function (data) {
				if(data.success==1){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("保存短信模板成功")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
				}else{
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("保存短信模板失败")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
				}
			}); 
		}else{
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容不能为空")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
		}
	}
	
	//回显页面数据
	$(function(){
		//回显是否开启
		var openMonitoring = $("#openMonitoring").val();
		if(openMonitoring =='1'){
			$("#img1").children(".one_check_").toggle();
		}
		if(openMonitoring =='0'){
			$("#img0").children(".one_check_").toggle();
		}
	
		
		//回显不发送标旗订单
		var sellerFlag = $("#sellerFlag").val();
		if(sellerFlag == ''){
			$("#hideImg").children(".one_check_").toggle();
		}
		var val = sellerFlag.split(",");
		for(var i=0;i<val.length;i++){
			if(val[i]=='1'){
				$("#img2").children(".one_check_").toggle();
			}
			if(val[i]=='2'){
				$("#img3").children(".one_check_").toggle();
			}
			if(val[i]=='3'){
				$("#img4").children(".one_check_").toggle();
			}
			if(val[i]=='4'){
				$("#img5").children(".one_check_").toggle();
			}
			if(val[i]=='5'){
				$("#img6").children(".one_check_").toggle();
			}
		}
		
		//回显不发送订单来源
		var orderSource = $("#orderSource").val();
		if(orderSource == ''){
			$("#hideImg2").children(".one_check_").toggle();
		}
		var val = orderSource.split(",");
		for(var i=0;i<val.length;i++){
			if(val[i] =='0'){
				$("#img7").children(".one_check_").toggle();
			}
			if(val[i] =='1'){
				$("#img8").children(".one_check_").toggle();
			}
			if(val[i] =='2'){
				$("#img9").children(".one_check_").toggle();
			}
		}
		
		var locality = $("#locality").val();
		$("#region").val(locality);
		if(locality != ''){
			$("#hideImg3").children(".one_check_").toggle();
		}
		//回显地区选择数据
		var localityList = locality.split(",");
		var getprovince = $(".place_check").children().find('.li_').children().next().children('input');
		for(var i=0;i<localityList.length;i++){
			for(var j=0;j<getprovince.length;j++){
				if(getprovince.eq(j).val()==localityList[i]){
					getprovince.eq(j).parent().prev().addClass("bgc_check_blue");
				}
			}
		}
		
		
		var sms_content = $("#sms_content_id").val();
		$("#sms_content_text").val(sms_content);
		$("#text_area_sms").val(sms_content);
		addLength();
		
		//回显金额按钮
		var minPayment = $("#minPayment").val();
		var maxPayment = $("#maxPayment").val();
		if(minPayment != "" || maxPayment != ""){
			$("#money_img").show();
		}
		//回显地区按钮
		var locality = $("#locality").val();
		if(locality != ""){
			$("#locality_img").show();
			$("#locality_img").addClass("show_Area")
		}
	});
	
	//隐藏镖旗
	function hideImg(){
		$("#img2").children(".one_check_").hide();
		$("#img3").children(".one_check_").hide();
		$("#img4").children(".one_check_").hide();
		$("#img5").children(".one_check_").hide();
		$("#img6").children(".one_check_").hide();
		
		sellerFlag();
	}
	
	function hideImg2(){
		$("#img7").children(".one_check_").hide();
		$("#img8").children(".one_check_").hide();
		$("#img9").children(".one_check_").hide();
		
		orderSource();
	}
	
	//点击中差评安抚设置页面跳转查询数据
	function showPacify(){
		window.location.href="${ctx}/appraisePacify/showAppraisePacify";
	}
	
	
			
		 function openNewWin(url){  
			 if(url == 0){
				 window.open("${ctx}/crms/reviewCare");  
			 }
			 if(url == 1){
				 window.open("${ctx }/crms/home/notice#duanxinxiangguan");
			 }
		}
		 
		//点击快捷键开关
		$("#img_af").click(function(){
			var status  = $(this).next().val();
			if(status != ''){
				if(status == '0'){
					openMonitoring('1')
					$("#img1").children(".one_check_").show();
					$("#img0").children(".one_check_").hide();
				}else if(status == '1'){
					openMonitoring('0')
					$("#img0").children(".one_check_").show();
					$("#img1").children(".one_check_").hide();
				}
			}
		});
		
		
		//保存设置
		//条件数据查询form表单
	function submitFormIsSet(fomrId){
		var moneyImg = $("#money_img").attr("style");
		var localityImg = $("#locality_img").attr("style");
			if(moneyImg != "display: inline;"){
				$("#minPayment").val('');
				$("#maxPayment").val('');
			}
 			if(localityImg != "display: inline;"){
				$("#locality").val('');
				$("#region").val('');
			}
 			var smsSign = $(".sms_sign").text();
 			$("#smsSign").val(smsSign);
			document.getElementById(fomrId).submit();
			
	}
	function isNumber(){
		var orderVal = $('#orderInput').val();
		if(isNaN(orderVal)){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("订单号筛选只能输入数字,请重新输入！")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			$('#orderInput').val('');
		}
	}
	
	/*点击显示短信详情*/
	$(".text_detail").click(function(){
		$(".text_detail").addClass("one_line_only lh50 h50");
		$(this).removeClass("one_line_only lh50 h50");
	});
	
	//点击修改短信内容
	$(".change_text_btn").click(function(){
		var smsContent = $("#sms_content_text").val();
		var smsSign = $(".sms_sign").text();
		 $(".duanxin_2").show();
		if(smsSign==''||smsSign==null){
			if(smsContent.startsWith("【短信签名】")){
				$("#text_area_sms").val(smsContent);
			}else{
				$("#text_area_sms").val("【短信签名】"+smsContent);
			}
		}else{
			var smsSingChange = "【"+smsSign+"】";
			if(smsContent.startsWith(smsSingChange)){
				$("#text_area_sms").val(smsContent);
			}else{
				$("#text_area_sms").val(smsSingChange+smsContent);
			}
		}
		addLength();
   	});
	
</script>
</html>
