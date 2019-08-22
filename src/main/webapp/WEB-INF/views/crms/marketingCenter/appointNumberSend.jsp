<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>营销中心-指定号码群发</title>
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
<script type="text/javascript"
	src="${ctx}/crm/js/text_designate_number.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/jquery.form.js"></script>
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
			<div class="w1645  m_l30">
				<div class="w100_ lh50 bgc_fff font18">
					<div class="f_l h50 w4 bgc_1d9dd9"></div>
					<div class="f_l m_l15 c_384856">营销中心</div>
					<div class="f_l m_r50 c_384856"  style="margin-left: 5vw;">
						<a class="c_384856" href="${ctx}/marketingCenter">
							<div class="f_l w140 text-center cursor_">会员短信群发</div>
						</a> <a class="c_384856"
							href="${ctx}/smsSendAppoint/appointNumberSend">
							<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
								指定号码群发</div>
						</a> <a class="c_384856"
							href="${ctx}/crms/marketingCenter/list?label=1">
							<div class="f_l w140 text-center cursor_">订单短信群发</div>
						</a> <a class="c_384856"
							href="${ctx}/msgSendRecord/memberSendRecord">
							<div class="f_l w140 text-center cursor_">短信发送记录</div>
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




				<!---------------指定号码群发--------------->
				<div class="">
					<!----------上部---------->
					<div class="w1605 h130 bgc_f1f3f7 c_384856 p_l40 p_t30">

						<!---------------标题--------------->

						<div class="font24 m_b10 ">指定号码发送</div>

						<!---------------描述--------------->
						<div class="font14">
							批量录入手机号码发送短信,自动过滤重复号码和错误号码。为了不影响客户休息，建议您发送短信时间在8点至22点之间。</div>

						<div class="font16 c_384856 h50 lh50 m_t18">
							<a class="c_384856"
								href="${ctx}/smsSendAppoint/appointNumberSend">
								<div id="text_designate_out_1"
									class="w140 h55 text-center f_l bgc_fff text_designate_out cursor_ display_none">
									指定号码发送</div>
							</a>
						</div>
					</div>


					<!----------下部---------->


					<form id="smsSendForm"
						action="${ctx}/smsSendAppoint/marketingCenter/saveSmsSendRecord"
						method="post">
						<div id="text_designate_in_1"
							class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in position_relative">

							<div class="">
								<div class="f_l lh40 c_384856">
									发送方式： <input id="send_mode_input" type="hidden" value="1"
										name="sendMode" /> <input id="error" type="hidden"
										value="${error }" />
								</div>
								<div class="f_l">
									<div class="f_l m_r60">
										<div id="manual_div"
											class="m_t10 cursor_ o_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ o_check_ w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">手动输入号码</div>
									</div>
									<div class="f_l m_r15">
										<div id="upload_div"
											class="m_t10 cursor_ o_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ o_check_ display_none w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">批量上传号码</div>
									</div>

									<div class="f_l upload_in display_none">

										<%-- <a class="c_384856" href="${ctx}/smsSendAppoint/findSmsSendlist"> --%>
										<div onclick="findSmsSendlist();"
											class="f_l tk w100 cursor_ upload_mail_list_btn h40 lh40 text-center b_radius5 border_00a0e9 c_00a0e9">
											选择文件</div>
										<!-- </a> -->
										<div class="f_l lh40 m_l10 m_r10 c_cecece">支持xlsx、xls格式
										</div>
										<div class="moban f_l">
										
											<div
												class="f_l w110 hover_example cursor_ h40 lh40 text-center b_radius5 bgc_00a0e9 c_fff">
												模板事例</div>
											<div
												class="position_absolute top0 z_5   display_none hover_example_hide"
												 style="left:5.729166vw;">
												<img src="${ctx}/crm/images/example3.png" /> <input type="hidden"
													id="ctx" value="${ctx}">
											</div>
										</div>
									</div>

								</div>
								<div class="clear"></div>
							</div>
							<div class="h60 m_t25">
								<div class="lh40 c_384856 f_l">活动名称：</div>
								<input id="activityName" name="activityName" class="bgc_f4f6fa border_d9dde5 b_radius5 w200 h40 m_l10 m_r25"/><span class="lh40" style="color:red;">为了您方便查找历史群发短信，请添加活动名称。</span>
							</div>
							<div class="h60 m_t25 fileNameDiv" style="display: none;">
								文件名称：
								<span id="fileNameShow" style="color: green;"></span>
							</div>
							<div class="h230 m_t30 manual_in">
								<div class="f_l lh40 c_384856">
									手机号码：
									<input id="phonesId" type="hidden"/>
								</div>
								<div class="f_l b_radius5 bgc_f4f6fa">
									<textarea id="phone_number_Content" name="recNum"
										class="text_area_phone w460 h180 border0 bgc_f4f6fa b_radius5 p_l10 p_r10 p_t10"></textarea>
									<div class="w440 c_cecece p_l20 h40 lh40 b_radius5">
										<span style="color: gray">多个号码使用英文逗号(",")隔开</span>
									</div>
								</div>
							</div>

							<%-- <div class="h40 m_t25">
								<div class="f_l lh40 c_384856">
									短信渠道：
								</div>
								<input id="note_ditch_input" type="text" value="0" name="channel" />
								<div class="f_l">
									<div class="f_l m_r50 qianming_none">
								    	<div id="taobao_div" class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div class="m_l10 f_l font14 c_384856 lh40">
								    		淘宝
								    	</div>
									</div>
									<div class="f_l m_r50 qianming_none">
								    	<div id="jingdong_div" class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div class="m_l10 f_l font14 c_384856 lh40">
								    		京东
								    	</div>
									</div>
									<div class="f_l m_r50 qianming_none">
								    	<div id="tianmao_div" class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div class="m_l10 f_l font14 c_384856 lh40">
								    		天猫
								    	</div>
									</div>
									<div class="f_l m_r50 qianming_done">
								    	<div id="zidingyi_div" class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div class="m_l10 f_l font14 c_384856 lh40">
								    		自定义签名
								    	</div>
									</div>
								</div>
							</div> --%>
							<div class="font14 h52 m_b15">
								<div class="f_l font16 lh52 lh52 c_384856">
									短信渠道： <input id="note_ditch_input" type="hidden" name="channel"
										value="1" />
								</div>
								<input type="hidden" id="smsChannel" value="【淘宝】">
								<div class="f_l m_r60 m_t16 qianming_none">
									<div onclick="addConsignee('【淘宝】');"
										class="cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l">
										<img id="taobao" class="cursor_ one_check_ w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="m_l10 f_l font14 c_384856">淘宝</div>
								</div>
								<div class="f_l m_t16 m_r60 qianming_none">
									<div  onclick="addConsignee('【京东】');"
										class="cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l">
										<img id="jingdong" class="cursor_ one_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="m_l10 f_l font14 c_384856">京东</div>
								</div>
								<div class="f_l m_t16 m_r60 qianming_none">
									<div  onclick="addConsignee('【天猫】');"
										class="cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l">
										<img id="tianmao" class="cursor_ one_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="m_l10 f_l font14 c_384856">天猫</div>
								</div>
								<div class="f_l m_t16 qianming_done">
									<div  onclick="addConsignee(null);"
										class="cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l">
										<img id="zidingyi" class="cursor_ one_check_ display_none w20 h20"
											src="${ctx}/crm/images/black_check.png" />
									</div>
									<div class="m_l10 f_l font14 c_384856">自定义</div>
								</div>

							</div>

							<div class="h40 m_t25 dxqm display_none">
								<div class="lh40 c_384856 f_l">短信签名：</div>
								<input name="autograph" id="biaoqian_input"
									onkeyup="addConsignee(null)" onblur="addConsignee(null)"
									class="bgc_f4f6fa qianming_ border0 w215 h50 f_l m_r10"
									/> <input type="hidden" id="copyConsignee" />
							</div>

							<div class="h150 m_t30">
								<!-- <div class="f_l lh40 c_384856">
									短信内容：
								</div>
								<div class="f_l b_radius5 bgc_f4f6fa position_relative">
									<textarea maxlength="500" class="text_area w460 h100 border0 bgc_f4f6fa b_radius5 p_l10 p_r10 p_t10"></textarea>
									<div class="w440 c_cecece p_l20 h40 lh40 b_radius5">
										已输入：<span class="text_count">0</span>/500字
										
										当前计费：<span>1</span> 条
										<a href="${ctx }/crms/home/notice#duanxinxiangguan" class="c_00a0e9 cursor_">计费规则</a>
									</div>
									<div class="w80 h20 bgc_f4f6fa lh20 position_absolute right20 bottom15">
										<div class="f_l">
											退订回
										</div>
										<div class="f_l">
											"<input maxlength="2" class="w15 text-center border0 bgc_f4f6fa c_384856" />"
										</div>
									</div>
								</div> -->
								<div class="f_l lh40 c_384856">短信内容：</div>
								<div class="f_l b_radius5 bgc_f4f6fa position_relative">
									<input type="hidden" id="textContent1" name="content">
									<input type="hidden" id="textContent2">
									<textarea id="textContent" maxlength="500"
										class="text_area w460 h100 border0 bgc_f4f6fa b_radius5 p_l10 p_r10 p_t10"
										onkeyup="addLength();" onblur="leaveEditMessage()">【淘宝】</textarea>
									<div class="w440 c_cecece p_l20 h40 lh40 b_radius5">
										已输入：<span id="inputNum" class="text_count">0</span> 当前计费：<span
											id="contePrice">1</span> 条 <a id="jfgz"
											class="c_00a0e9 cursor_">计费规则</span></a>
									</div>
									<div
										class="w80 h20 bgc_f4f6fa lh30 position_absolute right20 bottom15">
										<div class="c_bfbfbf m_r5 f_r w98">
											退订回 <input maxlength="2" id="unsubscribeMSG"
												class="w20 h20 l_h20 border_0 bgc_f4f6fa"
												readonly="readonly" value="N" />
										</div>
									</div>
								</div>
							</div>

							<div class="h40 m_t25 c_384856">

								<div class="f_l lh40 c_384856">辅助工具：</div>
								<div
									class="tk border_00a0e9 short_url_btn c_00a0e9 short_url_btn f_l m_r15 w110 cursor_ h40 lh40 text-center b_radius5" onclick="getCursortPosition(document.getElementById('textContent'))">
									生成短网址</div>
								<!-- <div onclick="choseTemplate('recentFestival',null);"
									class="f_l text_model_btn w110 cursor_ h40 lh40 text-center b_radius5 tk border_00a0e9 c_00a0e9">
									选择模板</div> -->
									<div onclick="choseTemplate('recommendedScene',null);"
									class="f_l text_model_btn w110 cursor_ h40 lh40 text-center b_radius5 tk border_00a0e9 c_00a0e9">
									选择模板</div>
								<!-- <a class="f_l text_model_btn w110 cursor_ h40 lh40 text-center b_radius5 tk border_00a0e9 c_00a0e9" onclick="findSmsTemplate(' ');">选择模板</a> -->
							</div>



							<div class="h46 m_t25 m_b25">
								<div class="f_l lh40 c_384856">
									重复发送过滤： <input id="send_filter" type="hidden" value="0"
										name="sendShield" />
								</div>
								<div class="f_l">
									<div class="f_l m_r40">
										<div id="shield_div"
											class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ one_check_ display_none w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">屏蔽</div>
									</div>
								</div>

								<div class="wrap f_l h45 lh45 m_r10">
									<div class="nice-select h45 lh45 w140" name="nice-select">
										<input id="sendDay" class="h45 lh45" type="text" value="1" readonly
											name="sendDay">
										<ul style="z-index: 1;">
											<li>1</li>
											<li>2</li>
											<li>3</li>
											<li>4</li>
											<li>5</li>
											<li>6</li>
											<li>7</li>
											<li>8</li>
											<li>9</li>
											<li>10</li>
											<li>20</li>
											<li>30</li>
										</ul>
									</div>
								</div>

								<div class="f_l lh40 c_384856">天内发送过的号码，不再发送</div>
							</div>

							<div class="h40">
								<div class="f_l lh40 c_384856">发送时间：</div>
								<input id="send_input" type="hidden" value="1"
									name="sendTimeType">
								<div class="f_l">
									<div class="f_l m_r40 set_time_none">
										<div id="lijiSend"
											class="m_t10 cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ one_check_ display_none w20 h20"
												src="${ctx}/crm/images/black_check.png"
												style="display: inline;" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">立即发送</div>
									</div>
									<div class="f_l">
										<div id="dingshiSend"
											class="m_t10 set_time cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ one_check_ display_none w20 h20"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">定时发送</div>
									</div>
									<div class="f_l position_relative m_l10 set_time_ display_none">
										<input
											class="bgc_f1f3f7 b_radius5 border0 w230 p_l10 h40 m_r10"
											type="text" id="tser01" placeholder="请选择时间" name="sendTime"
											readonly
											onclick="$.jeDate('#tser01',{minDate: $.nowDate(0),insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm'})">
										<img style="width: 1vw;" class="position_absolute right20 top12"
											src="${ctx}/crm/images/date_copy.png" />
									</div>
								</div>
								<div class="clear"></div>
								<p style="color:red;">提示:定时发送时间必须晚于当前时间半个小时以上</p>
							</div>




							<div class="p_l180 h40 m_t70">
								<input type="hidden" name="templateId" id="templateId" />
								<div onclick="submitForm('smsSendForm');"
									class="f_l m_r15 w110 cursor_ h40 lh40 text-center b_radius5 bgc_00a0e9 c_fff">
									确定发送</div>
								<!-- <input class="f_l m_r15 w110 cursor_ h40 lh40 text-center b_radius5 bgc_00a0e9 c_fff" type="submit" value="确定发送"/> -->
								<div
									class="f_l text_test_btn w110 cursor_ h40 lh40 text-center b_radius5 bgc_d4e100 c_fff">
									短信测试</div>

							</div>
<!-- 再次发送的数据 -->
	<input type="hidden" value="${againPhones }" id="againPhones" />
	<input type="hidden" value="${againContent }" id="againContent" />

							<div class="c_ff6363 font16" style="margin-top: 5vw;">

								<!-- <div>掌柜须知：</div>
								<div class="p_l15 m_t15">
									<p class="m_b10">1)
										为避免打扰买家休息,营销短信发送(审核)时间是：8:00-21:00，其他时间的短信自动顺延发送(审核),审核通过的定时发送不受影响!</p>
									<p class="m_b10">2)
										短信发送,成功率有保障!若出现手机无法接收短信,请检查手机安全软件是否拦截,是否停机等常见情况,也可拨打客服电话协助优化。</p>
								</div> -->

							</div>


							<div class="position_absolute iphone w327 top76 h642" style="left:37vw">
								<textarea disabled="disabled"
									class="text_area_copy m_l45 m_t150 w250 h370 border0"></textarea>
							</div>



						</div>
					</form>


					<!----------下部---------->
					<div id="text_designate_in_2"
						class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in display_none">
						<form
							action="${ctx}/smsSendAppoint/marketingCenter/findSmsSendRecordByCondition"
							method="post">


							<!--------查询设置-------->
							<div class="font14 c_384856 h45 lh45">
								<div class="f_l m_r15 font16">发送时间：</div>


								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10"
										name="beginTime" value="${beginTime }" type="text" id="tser07"
										readonly> <img style="width:1vw;"
										class="position_absolute right20 top15"
										src="${ctx}/crm/images/date_copy.png" />
								</div>

								<div class="f_l h50 lh50">~</div>

								<div class="f_l position_relative">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40"
										name="endTime" value="${endTime }" type="text" id="tser02"
										readonly> <img style="width:1vw;"
										class="position_absolute right50 top15"
										src="${ctx}/crm/images/date_copy.png" />
								</div>




								<!-- <div class="f_l m_r15 font16">
									短信批次：
								</div>
								<div class="wrap font16 f_l h45 lh45 m_r70">
								    <div class="nice-select h45 lh45" name="nice-select">
								    	<input class="h45 lh45" type="text" value="" readonly>
								    	<ul>
								    		<li>全部</li>
								    		<li>全部</li>
								    	</ul>
								  	</div>
								</div> -->

								<input
									class="w100 h45 font16 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_"
									type="submit" value="查询" />

							</div>
							<div class="font14 c_384856 h50 lh50 m_t20 m_b20">
								<div class="f_l m_r15 font16">手机号码：</div>
								<input id="phone" class="bgc_f4f6fa border0 w250 h50 f_l m_r35"
									name="phone" value="${phone}" />
								<div class="f_l m_r15 font16">发送状态：</div>
								<div class="wrap f_l font16 h45 lh45 m_r70">
									<div class="nice-select h45 lh45" name="nice-select">
										<input id="status_page" class="h45 lh45" type="text"
											value="${status_page }" name="status_page" readonly>
										<!-- <input id="status_page_input" type="text" name="status_page" readonly> -->
										<ul>
											<li id="status_page_3">全部</li>
											<li id="status_page_2">成功</li>
											<li id="status_page_1">失败</li>
										</ul>
									</div>
								</div>
								<div
									class="w200 font16 h45 lh45 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
									<a
										class="w200 font16 h45 lh45 b_radius5 c_00a0e9 tk text-center f_l cursor_"
										href="${ctx}/smsSendAppoint/marketingCenter/findSmsSendRecordByOneMonth">
										查看一个月以前的记录 </a>
								</div>


							</div>
						</form>


						<!--------操作选项-------->
						<div class="font14 h50 m_b15 p_r250">

							<!-- <div class="m_r70 download_btn font16 tk border_00a0e9 c_00a0e9 w100 lh52 h52 b_radius5 text-center f_l cursor_">
									导出
								</div> -->

							<%-- 								<a class="m_r70 font16 tk border_00a0e9 c_00a0e9 w100 lh52 h52 b_radius5 text-center f_l cursor_" href="${ctx}/smsSendAppoint/marketingCenter/smsSendRecordByExport">导出</a> --%>
							<button
								class="m_r70 font16 tk border_00a0e9 c_00a0e9 w100 lh52 h52 b_radius5 text-center f_l cursor_"
								id="exportFile1">导出</button>

							<div onclick="findHistorySms()"
								class="w100 font16 cursor_ h40 b_radius5 border_d9dde5 f_r text-center c_cad3df tk lh40">
								历史记录</div>

							<div onclick="nearOneSky()"
								class="w100 font16 h40 cursor_ b_radius5 border_d9dde5 f_r text-center c_cad3df tk lh40">
								近一天</div>

						</div>


						<!--------详细数据-------->
						<div class="c_384856 font14">
							<table>
								<tr class="">
									<th class="w105">序号</th>
									<th class="w375">内容</th>
									<th class="w125">渠道</th>
									<th class="w185">模板</th>
									<th class="w125">发送状态</th>
									<th class="w125">数量</th>
									<th class="w230">日期</th>
								</tr>
								<c:if test="${pagination.list.size() ==0 }">
									<tr>
										<th class="w75 text-center" align="center" colspan="7">暂时没有相关数据!</th>
									</tr>
								</c:if>
								<c:if test="${pagination.list != null }">
									<c:forEach items="${pagination.list }" var="smsSendRecord"
										varStatus="status">
										<tr class=" text-center">
											<td class="w105">${status.index+1 }</td>
											<td class="w375">${smsSendRecord.content }</td>
											<td class="w125">${smsSendRecord.channel }</td>
											<td class="w185">${smsSendRecord.code }</td>
											<td class="w125"><c:if
													test="${smsSendRecord.status == '1' }">
								    	发送失败
								    	</c:if> <c:if test="${smsSendRecord.status =='2' }">
								   		发送成功
								    	</c:if> <span id="status_show"></span> <%-- ${smsSendRecord.status } --%>
											</td>
											<td class="w125">${smsSendRecord.actualDeduction }</td>
											<td class="w230"><fmt:formatDate
													value="${smsSendRecord.sendTime }"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
										</tr>
									</c:forEach>
								</c:if>


								<!--  <tr class=" text-center">
								    <td class="w105">1</td>
								    <td class="w375"></td>
								    <td class="w125"></td>
								    <td class="w185"></td>
								    <td class="w125"></td>
								    <td class="w125"></td>
								    <td class="w230"></td>
								  </tr>
								  <tr class=" text-center">
								    <td class="w105">1</td>
								    <td class="w375"></td>
								    <td class="w125"></td>
								    <td class="w185"></td>
								    <td class="w125"></td>
								    <td class="w125"></td>
								    <td class="w230"></td>
								  </tr>
								  <tr class=" text-center">
								    <td class="w105">1</td>
								    <td class="w375"></td>
								    <td class="w125"></td>
								    <td class="w185"></td>
								    <td class="w125"></td>
								    <td class="w125"></td>
								    <td class="w230"></td>
								  </tr>
								  <tr class=" text-center">
								    <td class="w105">1</td>
								    <td class="w375"></td>
								    <td class="w125"></td>
								    <td class="w185"></td>
								    <td class="w125"></td>
								    <td class="w125"></td>
								    <td class="w230"></td>
								  </tr>
								  <tr class=" text-center">
								    <td class="w105">1</td>
								    <td class="w375"></td>
								    <td class="w125"></td>
								    <td class="w185"></td>
								    <td class="w125"></td>
								    <td class="w125"></td>
								    <td class="w230"></td>
								  </tr>
								  <tr class=" text-center">
								    <td class="w105">1</td>
								    <td class="w375"></td>
								    <td class="w125"></td>
								    <td class="w185"></td>
								    <td class="w125"></td>
								    <td class="w125"></td>
								    <td class="w230"></td>
								  </tr>
								  <tr class=" text-center">
								    <td class="w105">1</td>
								    <td class="w375"></td>
								    <td class="w125"></td>
								    <td class="w185"></td>
								    <td class="w125"></td>
								    <td class="w125"></td>
								    <td class="w230"></td>
								  </tr>
								  <tr class=" text-center">
								    <td class="w105">1</td>
								    <td class="w375"></td>
								    <td class="w125"></td>
								    <td class="w185"></td>
								    <td class="w125"></td>
								    <td class="w125"></td>
								    <td class="w230"></td>
								  </tr>
								  <tr class=" text-center">
								    <td class="w105">1</td>
								    <td class="w375"></td>
								    <td class="w125"></td>
								    <td class="w185"></td>
								    <td class="w125"></td>
								    <td class="w125"></td>
								    <td class="w230"></td>
								  </tr> -->
							</table>
						</div>


						<!--------分页-------->
						<div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
							<!-- <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div> -->
							<div class="f_r w470 h24 l_h22 font12">
								<!-- <div class="f_l w45 h22 bor_e0e6ef text-center m_r4 cursor_">上一页</div>
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
                                    <div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div> -->
								<c:if test="${pagination.pageView != null }">
									<c:forEach items="${pagination.pageView }" var="page">
										${page } 
								  </c:forEach>
								</c:if>
							</div>
						</div>

					</div>

					<!----------下部---------->
					<div id="text_designate_in_3"
						class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in display_none">

						<!----------页面优先显示---------->
						<div class="outside display_none">
							<!--------详细数据-------->
							<div class="c_384856 font14 text-center">
								<table>
									<tr class="">
										<th class="w70">序号</th>
										<th class="w120">批次名称</th>
										<th class="w140">创建人</th>
										<th class="w115">目标数量</th>
										<th class="w300">短信内容</th>
										<th class="w110">状态</th>
										<th class="w125">发送时间</th>
										<th class="w140">短网址点击(PV)</th>
										<th class="w155">操作</th>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td>
											<div
												class="enter_detail bgc_00a0e9 c_fff font14 h40 lh40 w100 b_radius5 margin0_auto cursor_ check_detail">效果分析报告</div>
										</td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</table>
							</div>

							<!--------分页-------->
							<div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
								<div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
										<input class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
											type="text" value="">
									</div>
									<div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
								</div>
							</div>
						</div>


						<!----------页面隐藏内容---------->
						<div class="detail">
							<div class="h40 lh40 m_b50">
								<p class="c_384856 f_l font14">
									<span class="font24">效果分析报告</span> <span class="c_8493a8">活动名称：
										<span> 模板.xlsx2016789645415 </span>
									</span>
								</p>
								<div
									class="m_l10 cursor_ enter_outside bgc_00a0e9 f_l c_fff font14 text-center h40 w100 b_radius5">
									进入列表</div>
							</div>
							<!----------选项卡---------->
							<div class=" h42">
								<div
									class="text-center detail_out f_l w170 h40 lh40 b_radius5 c_fff bgc_00a0e9 cursor_ border_cad3df">
									发送详情</div>
								<div
									class="text-center detail_out f_l w170 h40 lh40 b_radius5 c_cad3df cursor_ border_cad3df">
									效果汇总</div>
								<div
									class="text-center detail_out f_l w170 h40 lh40 b_radius5 c_cad3df cursor_ border_cad3df">
									客户明细</div>
								<div
									class="text-center detail_out f_l w170 h40 lh40 b_radius5 c_cad3df cursor_ border_cad3df">
									商品明细</div>

							</div>


							<!----------选项卡内容---------->
							<div class=" m_t35 font16 c_384856 detail_in">

								<p class="m_b35">
									活动名称:<span class="c_00a0e9 m_l15">模板.xlsx20160718112527</span>
								</p>

								<p class="m_b35">
									发送时间:<span class="m_l15">2016-07-18 11:25</span>
								</p>

								<p class="m_b35">
									发送状态:<span class="c_35ff35 m_l15">发送完成</span>
								</p>

								<p class="m_b35">
									短信内容:<span class="m_l15"></span>
								</p>

								<p class="m_b35">
									会员条件:<span class="m_l15"></span>
								</p>

							</div>



							<!----------选项卡内容---------->
							<div class=" m_t35 font16 c_384856 detail_in display_none">



							</div>



							<!----------选项卡内容---------->
							<div class=" m_t35 font16 c_384856 detail_in display_none">
								<div class="h50">
									<div class="f_l m_r15 font16 h45 lh45">客户类型：</div>
									<div class="wrap font16 w250 f_l h45 lh45 m_r25">
										<div class="nice-select h45 lh45" name="nice-select">
											<input class="h45 lh45" type="text" value="成交客户" readonly>
											<ul>
												<li>成交客户</li>
												<li>意向客户</li>
												<li>拍下客户</li>
											</ul>
										</div>
									</div>

									<div class="f_l m_r15 font16 h45 lh45">终端类型：</div>
									<div class="wrap font16 f_l h45 lh45 m_r10">
										<div class="nice-select w180 h45 lh45" name="nice-select">
											<input class="h45 lh45" type="text" value="" readonly>
											<ul>
												<li>全部终端</li>
												<li>PC终端</li>
												<li>无线端</li>
											</ul>
										</div>
									</div>
									<div class="f_l m_r15 font16 h50 lh50">手机号：</div>
									<input class="bgc_f4f6fa border0 b_radius5 w280 h50 f_l m_r10" />

									<div class="f_l m_r15 font16 h50 lh50">卖家姓名：</div>
									<input class="bgc_f4f6fa border0 b_radius5 w190 h50 f_l m_r10" />
								</div>
								<div class="h50 m_t25">
									<div class="f_l m_r15 font16 h45 lh45">评价时间：</div>

									<div class="f_l position_relative">
										<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10"
											type="text" id="tser03" readonly
											onclick="$.jeDate('#tser03',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})">
										<img style="width:1vw;" class="position_absolute right20 top15"
											src="${ctx}/crm/images/date_copy.png" />
									</div>

									<div class="f_l h50 lh50">~</div>

									<div class="f_l position_relative">
										<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40"
											type="text" id="tser04" readonly
											onclick="$.jeDate('#tser04',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})">
										<img style="width:1vw;" class="position_absolute right50 top15"
											src="${ctx}/crm/images/date_copy.png" />
									</div>




									<div class="f_l m_r15 font16 h50 lh50">商品ID：</div>
									<input class="bgc_f4f6fa border0 b_radius5 w425 h50 f_l m_r30" />
									<div
										class="border_00a0e9 c_00a0e9 tk b_radius5 w100 h50 cursor_ lh50 f_l text-center">
										查询</div>

								</div>
								<div
									class="border_00a0e9 download_btn cursor_ c_00a0e9 m_b25 tk m_t25 b_radius5 w100 h50 lh50 text-center">
									导出</div>



								<!--------详细数据-------->
								<div class="c_384856 p_l35 text-center">
									<table>
										<tr class="">
											<th class="w85">买家昵称</th>
											<th class="w85">卖家姓名</th>
											<th class="w85">性别</th>
											<th class="w85">年龄</th>
											<th class="w85">手机号</th>
											<th class="w85">生日</th>
											<th class="w85">邮箱</th>
											<th class="w85">会员等级</th>
											<th class="w85">信用等级</th>
											<th class="w85">订单金额</th>
											<th class="w85">订单笔数</th>
											<th class="w85">商品件数</th>
											<th class="w85">省份</th>
											<th class="w85">城市</th>
											<th class="w85">地址</th>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</table>
								</div>


								<!--------分页-------->
								<div class="h24 m_t22 font14 c_8493a8 m_b40 p_r280">
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



							<!----------选项卡内容---------->
							<div class=" m_t35 font14 c_384856 detail_in display_none">

								<div class="h50 m_t25">
									<div class="f_l m_r15 h45 lh45">评价时间：</div>

									<div class="f_l position_relative">
										<input class="bgc_f4f6fa border0 w130 p_l10 h50 m_r10"
											type="text" id="tser05" readonly
											onclick="$.jeDate('#tser05',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})">
										<img style="width:1vw;" class="position_absolute right20 top15"
											src="${ctx}/crm/images/date_copy.png" />
									</div>

									<div class="f_l h50 lh50">~</div>

									<div class="f_l position_relative">
										<input class="bgc_f4f6fa border0 w130 p_l10 h50 m_l10 m_r20"
											type="text" id="tser06" readonly
											onclick="$.jeDate('#tser06',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})">
										<img class="position_absolute right30 top15"
											src="${ctx}/crm/images/date_copy.png" />
									</div>



									<div class="f_l m_r15 h45 lh45">客户类型：</div>
									<div class="wrap w140 f_l h45 lh45 m_r25">
										<div class="nice-select w140 h45 lh45" name="nice-select">
											<input class="h45 lh45" type="text" value="" readonly>
											<ul>
												<li>成交客户</li>
												<li>意向客户</li>
												<li>拍下客户</li>
											</ul>
										</div>
									</div>

									<div class="f_l m_r15 h45 lh45">终端类型：</div>
									<div class="wrap f_l h45 lh45 m_r10">
										<div class="nice-select w125 h45 lh45" name="nice-select">
											<input class="h45 lh45" type="text" value="" readonly>
											<ul>
												<li>全部终端</li>
												<li>PC终端</li>
												<li>无线端</li>
											</ul>
										</div>
									</div>


									<div class="f_l m_r15 h50 lh50">商品ID：</div>
									<input class="bgc_f4f6fa border0 b_radius5 w225 h50 f_l m_r30" />
									<div
										class="border_00a0e9 c_00a0e9 cursor_ tk b_radius5 w100 h50 lh50 f_l text-center">
										查询</div>

								</div>
								<div
									class="border_00a0e9 download_btn cursor_ c_00a0e9 tk m_b25 m_t25 b_radius5 w100 h50 lh50 text-center">
									导出</div>



								<!--------详细数据-------->
								<div class="c_384856 p_l35 text-center">
									<table>
										<tr class="">
											<th class="w85">买家昵称</th>
											<th class="w85">卖家姓名</th>
											<th class="w85">性别</th>
											<th class="w85">年龄</th>
											<th class="w85">手机号</th>
											<th class="w85">生日</th>
											<th class="w85">邮箱</th>
											<th class="w85">会员等级</th>
											<th class="w85">信用等级</th>
											<th class="w85">订单金额</th>
											<th class="w85">订单笔数</th>
											<th class="w85">商品件数</th>
											<th class="w85">省份</th>
											<th class="w85">城市</th>
											<th class="w85">地址</th>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</table>
								</div>


								<!--------分页-------->
								<div class="h24 m_t22 font14 c_8493a8 m_b40 p_r280">
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

					<!----------下部---------->
					<div
						class="w1620 bgc_fff p_t30 p_l40 p_b40 text_designate_in m_b30 display_none">

						<div class="">

							<!--------使用帮助-------->
							<div class="m_b22 font14 c_384856">


								<div class="h50 m_b35 m_t35">
									<div class="font16 f_l font14 c_384856 h50 lh50 m_r15">
										发送时间：</div>
									<button
										class=" analysed_btn f_l cursor_ w140 h50 lh50 font14 m_r15 c_fff bgc_00a0e9 border0 b_radius5">
										<img class="m_r20" src="${ctx}/crm/images/add_list.png" />
										添加分组
									</button>
									<div class="font16 f_l font14 c_384856 h50 lh50">
										已添加<span class="c_00a0e9">0</span>个分组
									</div>
								</div>
								<!--------查询设置-------->
								<div class="font14 c_384856 h50 m_b40">

									<div class="font16 f_l font14 c_384856 lh40">转化周期：</div>
									<div class="f_l m_r40">
										<div
											class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">3天</div>
									</div>

									<div class="f_l m_r40">
										<div
											class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">5天</div>
									</div>

									<div class="f_l m_r40">
										<div
											class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">7天</div>
									</div>

									<div class="f_l m_r40">
										<div
											class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856 lh40">15天</div>
									</div>
									<div
										class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
										开始分析</div>
								</div>

								<div class="m_b10 m_t20 h50">
									<button
										class="download font16 f_l cursor_ w100 h50 lh50 font14 c_fff bgc_00a0e9 border0 b_radius5">
										导出明细</button>
								</div>


							</div>

							<!--------详细数据-------->
							<div class="c_384856">
								<table>
									<tr class="">
										<th class="w100 text-center">序号</th>
										<th class="w375">内容</th>
										<th class="w125">渠道</th>
										<th class="w185">模板</th>
										<th class="w125">发送状态</th>
										<th class="w125">数量</th>
										<th class="w230">日期</th>
									</tr>
									<tr class="">
										<td class="w100 text-center">1</td>
										<td class="w375 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w185 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w230 text-center"></td>
									</tr>
									<tr class="">
										<td class="w100 text-center">2</td>
										<td class="w375 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w185 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w230 text-center"></td>
									</tr>
									<tr class="">
										<td class="w100 text-center">3</td>
										<td class="w375 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w185 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w230 text-center"></td>
									</tr>
									<tr class="">
										<td class="w100 text-center">4</td>
										<td class="w375 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w185 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w230 text-center"></td>
									</tr>
									<tr class="">
										<td class="w100 text-center">5</td>
										<td class="w375 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w185 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w230 text-center"></td>
									</tr>
									<tr class="">
										<td class="w100 text-center">6</td>
										<td class="w375 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w185 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w230 text-center"></td>
									</tr>
									<tr class="">
										<td class="w100 text-center">7</td>
										<td class="w375 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w185 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w230 text-center"></td>
									</tr>
									<tr class="">
										<td class="w100 text-center">8</td>
										<td class="w375 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w185 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w230 text-center"></td>
									</tr>
									<tr class="">
										<td class="w100 text-center">9</td>
										<td class="w375 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w185 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w230 text-center"></td>
									</tr>
									<tr class="">
										<td class="w100 text-center">10</td>
										<td class="w375 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w185 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w125 text-center"></td>
										<td class="w230 text-center"></td>
									</tr>
								</table>
							</div>

							<!--------分页-------->
							<div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
								<div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div>
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
										<input class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
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
				<div class="margin0_auto  w300" style="width:22.6vw;">
					<div class="f_l m_r40 short_out" onclick="linkDate('店铺链接');">
						<div
							class="m_t10  cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l">
							<img class="cursor_ w20 one_check_"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">店铺链接</div>
					</div>
					<div class="f_l short_out m_r40" onclick="linkDate('商品链接');">
						<div
							class="m_t10  cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l AddSpecified_link">
							<img class="cursor_ w20 one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">商品链接</div>
					</div>
					<div class="f_l short_out m_r40" onclick="linkDate('活动页链接');">
						<div
							class="m_t10  cursor_ one_check_only_555 w20 h20 border_d9dde5 b_radius5 f_l activityLink">
							<img class="cursor_ w20 one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">活动页链接</div>
					</div>
					
				</div>
			</div>
			<!-- <input
				class="w570 short_in h40 bgc_f4f6fa border_0 m_l70 p_l10 m_b30"
				placeholder="请输入您需要转换的网址" /> -->
			<!-- <input class="w570 h40 bgc_f4f6fa border_0 m_l70 p_l10 m_b30 text-center" placeholder="请输入短链接名称！" id="linkName"/> -->
			<div
				class="w570 short_in h40 font14 m_l70 text-center m_b30 c_384856">
				店铺链接直接点击确定按钮即可</div>
			<input
				class="w570 short_in h40 bgc_f4f6fa border_0 m_l70 p_l10 m_b30 display_none text-center"
				placeholder="商品ID" id="commodityId" onkeyup="this.value=this.value.replace(/\D/g, '')" onblur="this.value=this.value.replace(/\D/g, '')" />
			<div id="activityLinkTxtBox">
				<textarea class="activityLinkTxt w570 showBoxthree" placeholder="在此输入活动网址"></textarea>
			</div>
			<div class="margin0_auto" style="width: 13vw; height: 3vw;">
				<div onclick="linkSubmit();"
					class="cursor_ m_r30 f_l save_short_url w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk b_radius5">
					确定</div>
				<div
					class="cursor_ f_l save_short_url w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk b_radius5">
					取消</div>
			</div>

			<div style="padding-left: 6vw;">
				<p class="font16 c_ff6363">温馨提示：</p>
				<p class="font14 c_ff6363">1.插入短链接时，请不要去掉短链接两边的空格，不然会造成短链接失效。</p>
				<p class="font14 c_ff6363">2. 添加短链接时请勿删除链接前后的空格，否则手机端网址将不能打开。</p>
				<p class="font14 c_ff6363">3. 所添加网址必须是taobao.com、tmall.com、jaeapp.com这三个域名下。</p>
			</div>

		</div>

	</div>



	<!---------------测试短信弹窗--------------->
	<%-- <div class="w100_ h1440 rgba_000_5 position_absolute z_10 text_model_window top0 display_none">
	
	<div class="w1000 bgc_fff left400 p_b20 position_fixed top100">
		
		<div class="c_384856 text-center font18 h90 bgc_f1f3f7 p_t20">
			
			<div class="text-center m_b15">
				
			</div>
			
			<div class="m_l30 h50">
				<div class="text_model out cursor_ bgc_fff w140 h50 lh50 text-center c_384856 f_l">
					近期节日
				</div>
				<div class="text_model out cursor_ bgc_e3e7f0 w140 h50 lh50 text-center c_384856 f_l">
					推荐场景
				</div>
				<div class="text_model out cursor_ bgc_e3e7f0 w140 h50 lh50 text-center c_384856 f_l">
					历史发送
				</div>
			</div>
			
			
			
		</div>
		<div class="in">
			
			<div class="h40 p_l70 m_t40 m_b20">
				<div class="f_l jr_out m_r40">
			    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    	<div class="m_l10 f_l font14 c_384856 lh40">
			    		七夕
			    	</div>
				</div>
				<div class="f_l jr_out m_r40">
			    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    	<div class="m_l10 f_l font14 c_384856 lh40">
			    		七夕
			    	</div>
				</div>
				<div class="f_l jr_out m_r40">
			    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    	<div class="m_l10 f_l font14 c_384856 lh40">
			    		七夕
			    	</div>
				</div>
			</div>
			
			<!--------详细数据-------->
			<div class="c_384856 p_l35">
				<table>
				  <tr class="">
				    <th class="w75">全选</th>
				    <th class="w760">宝贝名称</th>
				    <th class="w90">操作</th>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center">
				    	<div class="border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto b_radius5">
				    		使用
				    	</div>
				    </td>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center"></td>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center"></td>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center"></td>
				  </tr>
				</table>
			</div>
			
			
			<!--------分页-------->
	        <div class="h24 m_t22 font14 c_8493a8 m_b40 p_r30">
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
			
			<div class="h40 p_l70 m_t40 m_b20">
				<div class="f_l m_r40">
			    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    	<div class="m_l10 f_l font14 c_384856 lh40">
			    		七夕
			    	</div>
				</div>
				<div class="f_l m_r40">
			    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    	<div class="m_l10 f_l font14 c_384856 lh40">
			    		七夕
			    	</div>
				</div>
				<div class="f_l m_r40">
			    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    	<div class="m_l10 f_l font14 c_384856 lh40">
			    		七夕
			    	</div>
				</div>
			</div>
			
			<!--------详细数据-------->
			<div class="c_384856 p_l35">
				<table>
				  <tr class="">
				    <th class="w75">全选</th>
				    <th class="w760">宝贝名称</th>
				    <th class="w90">操作</th>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center">
				    	<div class="border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto cursor_ b_radius5">
				    		使用
				    	</div>
				    </td>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center"></td>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center"></td>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center"></td>
				  </tr>
				</table>
			</div>
			
			
			<!--------分页-------->
	        <div class="h24 m_t22 font14 c_8493a8 m_b40 p_r30">
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
			
			<div class="h40 p_l70 m_t40 m_b20">
				<div class="f_l m_r40">
			    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    	<div class="m_l10 f_l font14 c_384856 lh40">
			    		七夕
			    	</div>
				</div>
				<div class="f_l m_r40">
			    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    	<div class="m_l10 f_l font14 c_384856 lh40">
			    		七夕
			    	</div>
				</div>
				<div class="f_l m_r40">
			    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    	<div class="m_l10 f_l font14 c_384856 lh40">
			    		七夕
			    	</div>
				</div>
			</div>
			
			<!--------详细数据-------->
			<div class="c_384856 p_l35">
				<table>
				  <tr class="">
				    <th class="w75">全选</th>
				    <th class="w760">宝贝名称</th>
				    <th class="w90">操作</th>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center">
				    	<div class="border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto cursor_ b_radius5">
				    		使用
				    	</div>
				    </td>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center"></td>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center"></td>
				  </tr>
				  <tr class="">
				    <td class="w75 text-center"></td>
				    <td class="w760 text-center"></td>
				    <td class="w90 text-center"></td>
				  </tr>
				</table>
			</div>
			
			
			<!--------分页-------->
	        <div class="h24 m_t22 font14 c_8493a8 m_b40 p_r30">
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
	
		<div class="h42 margin0_auto w234">
			<div class="w100 close f_l m_r30 h40 lh40 border_00a0e9 tk cursor_ c_00a0e9 font16 text-center b_radius5">
				确定
			</div>
			<div class="w100 close f_l h40 lh40 border_00a0e9 tk cursor_ c_00a0e9 font16 text-center b_radius5">
				取消
			</div>
		</div>
	</div>
	
</div> --%>


	<!-- 测试复制 -->
	<div
		class="w100_ h1370 rgba_000_5 position_absolute z_10 text_model_window top0 display_none">

		<div class="w1000 bgc_fff left400 p_b20 position_fixed top100">

			<div class="c_384856 text-center font18 bgc_f1f3f7 p_t20">

				<div class="text-center m_b15">选择短信模板</div>

				<div class="m_l30 ">
					<!-- <div
						class="text_model out cursor_ bgc_fff w140 h50 lh50 text-center c_384856 f_l temp1"
						id="jinqijieri">
						<a class="w140 h50 inline_block"
							onclick="choseTemplate('recentFestival',null)">近期节日</a>
					</div> -->
					<div
						class="text_model out cursor_ bgc_fff w140 h50 lh50 text-center c_384856 f_l temp1"
						id="tuijianchangjing">
						<a class="w140 h50 inline_block"
							onclick="choseTemplate('recommendedScene',null)">推荐场景</a>
					</div>
					<div
						class="text_model out cursor_ bgc_e3e7f0 w140 h50 lh50 text-center c_384856 f_l temp1"
						id="lishifasong">
						<a class="w140 h50 inline_block"
							onclick="choseTemplate('sendHistory',null)">历史发送</a>
					</div>
					<div class="clear"></div>
				</div>



			</div>
			<%-- <div class="in display_none jqjr temp2">

				<div class="h40 p_l70 m_t40 m_b20">
					<div class="f_l jr_out m_r40 jqjr">
						<div
							class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							<img class="cursor_ w20 one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" id="jqjrys" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40" id="recentFestival"></div>
					</div>
				</div>

				<!--------详细数据-------->
				<div class="c_384856 p_l35 h380 w920 overflow_auto m_r15">
					<table id="table_jinqijieri">
						<tr class="" id="table_jinqijieri_tr">
							<th class="w75">序号</th>
							<th class="w760">短信内容</th>
							<th class="w90">操作</th>
						</tr>
						<c:if test="${pagiList !=null}">						
							<c:forEach items="${pagiList.list }" var="smsTemplate"
								varStatus="var">
								<tr class="">
									<td class="w75 text-center">${var.getCount()}</td>
									<td class="w760 text-center">${smsTemplate.content}</td>
									<td class="w90 text-center">
										<div
											class="border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto cursor_ b_radius5" 
											 onclick="addContent('${smsTemplate.content}');">
											使用</div>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>


				<!--------分页-------->
				<div class="h24 m_t22 font14 c_8493a8 m_b40 p_r30">
					<div class="f_r w470 h24 l_h22 font12">
						<c:if test="${pagiList !=null}">
							<c:forEach items="${pagiList.pageView }" var="page">
								${page } 
						  	</c:forEach>
					  </c:if>
					</div>
				</div>

			</div> --%>

			<div class="in  tuijian temp2">

				<div class="h40 p_l70 m_t40 m_b20">
					<div class="f_l m_r40">
						<div
							class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l"
							onclick="choseTemplate('recommendedScene','聚划算')">
							<img class="cursor_ w20 one_check_ tempImg"
								src="${ctx}/crm/images/black_check.png" id="jhs" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">聚划算</div>
					</div>
					<div class="f_l m_r40">
						<div
							class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l"
							onclick="choseTemplate('recommendedScene','上新')">
							<img class="cursor_ w20 one_check_ display_none tempImg"
								src="${ctx}/crm/images/black_check.png" id="sx" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">上新</div>
					</div>
					<div class="f_l m_r40">
						<div
							class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l"
							onclick="choseTemplate('recommendedScene','周年庆')">

							<img class="cursor_ w20 one_check_ display_none tempImg"
								src="${ctx}/crm/images/black_check.png" id="znq" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">周年庆</div>
					</div>
				</div>

				<!--------详细数据-------->
				<div class="c_384856 p_l35 h380 w920 overflow_auto m_r15">
					<table id="table_tuijianchangjin">
						<tr class="" id="table_tuijianchangjin_tr">
							<th class="w75">序号</th>
							<th class="w760">短信内容</th>
							<th class="w90">操作</th>
						</tr>
						<%-- <c:if test="${pagiList!=null }">
							<c:forEach items="${pagiList.list }" var="smsTemplate"
								varStatus="var">
								<tr class="">
									<td class="w75 text-center">${var.getCount()}</td>
									<td class="w760 text-center">${smsTemplate.content}</td>
									<td class="w90 text-center">
										<div
											class="border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto cursor_ b_radius5"
											onclick="addContent('${smsTemplate.content}');">
											使用</div>
									</td>
								</tr>
							</c:forEach>
						</c:if> --%>
					</table>
				</div>


				<!--------分页-------->
				<%-- <div class="h24 m_t22 font14 c_8493a8 m_b40 p_r30">
					<div class="f_r w470 h24 l_h22 font12">
						<c:if test="${pagiList!=null }">
							<c:forEach items="${pagiList.pageView }" var="page">
								${page } 
						  	</c:forEach>
						 </c:if>
					</div>
				</div> --%>

			</div>

			<div class="in display_none lishijilu temp2">

				<div class="h20 p_l70 m_t20 m_b20">
					<%-- <div class="f_l m_r40">
						<div
							class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							<img class="cursor_ one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">七夕</div>
					</div>
					<div class="f_l m_r40">
						<div
							class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							<img class="cursor_ one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">七夕</div>
					</div>
					<div class="f_l m_r40">
						<div
							class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							<img class="cursor_ one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">七夕</div>
					</div> --%>
				</div>

				<!--------详细数据-------->
				<div class="c_384856 p_l35 h380 w920 overflow_auto m_r15">
					<table id="table_lishifasong">
						<tr class="" id="table_lishifasong_tr">
							<th class="w75">序号</th>
							<th class="w760">短信内容</th>
							<th class="w90">操作</th>
						</tr>
						<%-- <c:if test="${pagi!=null}">
							<c:forEach items="${pagi.list }" var="smsHistoryTemplate" varStatus="var">
								<tr class="">
									<td class="w75 text-center">${var.getCount()}</td>
									<td class="w760 text-center">${smsHistoryTemplate.content}</td>
									<td class="w90 text-center">
										<div class="border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto cursor_ b_radius5"
										 onclick="addContent('${smsHistoryTemplate.content}');">
											使用
										</div>
									</td>
						</tr>	
							</c:forEach>
						</c:if> --%>

					</table>
				</div>


				<!--------分页-------->
				<%-- <div class="h24 m_t22 font14 c_8493a8 m_b40 p_r30">
					<div class="f_r w470 h24 l_h22 font12">
						<c:if test="${pagi !=null }">
							<c:forEach items="${pagi.pageView }" var="page1">
								${page1 } 
						  	</c:forEach>
						 </c:if>
					</div>
				</div> --%>

			</div>

			<div class="h42 margin0_auto" style="margin-top: 1vw; width: 13vw;">
				<div
					class="w100 close f_l m_r30 h40 lh40 border_00a0e9 tk cursor_ c_00a0e9 font16 text-center b_radius5">
					确定</div>
				<div
					class="w100 close f_l h40 lh40 border_00a0e9 tk cursor_ c_00a0e9 font16 text-center b_radius5">
					取消</div>
			</div>
		</div>

	</div>




	<!---------------短信测试弹窗--------------->
	<div
		class="w100_ h1440 rgba_000_5 position_absolute z_10 text_test_window top0 display_none">

		<div
			class="w830 bgc_fff margin0_auto left0 right0 p_b20 position_fixed top200">

			<div class="cursor_ close">
				<img class="f_r m_r_15 m_t_15" src="${ctx}/crm/images/chazi.png" />
			</div>

			<div class="c_384856 text-center font18  p_t20">

				<div class="text-center m_b15">短信测试</div>



			</div>
			<div class="font14 w750 margin0_auto m_b30">
				<div class="f_l c_384856" style="line-height: 2vw;">手机号码：</div>
				<input class="w540 p_l10 f_l h40 lh40 border0 bgc_f4f6fa Phone"
					placeholder="测试号码不能超过5个，输入多个时用逗号隔开" /> <input type="hidden"
					id="smsSign" value="${ShopName }" />
				<div style="margin-top: 1vw;"
					class="w100 f_l m_l30 cursor_ h40 lh40 bgc_00a0e9 c_fff text-center b_radius5 cheShiFaSong1 close">
					测试</div>
				<div class="clear"></div>
			</div>

			<!--------详细数据-------->
			<!-- <div class="c_384856 w750 margin0_auto">
			<table>
			  <tr class="">
			    <th class="w90">发送总数</th>
			    <th class="w80">成功总数</th>
			    <th class="w350">短信内容</th>
			    <th class="w115">任务状态</th>
			    <th class="w130">操作</th>
			  </tr>
			  <tr class="">
			    <td class="w90 text-center">5</td>
			    <td class="w80 text-center">5</td>
			    <td class="w350 text-center"></td>
			    <td class="w115 text-center"></td>
			    <td class="w130 text-center">
			    	<div class="cursor_ detail_btn margin0_auto b_radius5 w100 h40 lh40 text-center bgc_00a0e9 c_fff">
			    		查看清单
			    	</div>
			    </td>
			  </tr>
			</table>
			<table class="m_t10 display_none detail">
			  <tr class="m_t10">
			    <td class="w90 text-center">手机号</td>
			    <td class="w680 text-center">17711111111</td>
			  </tr>
			  <tr class="">
			    <td class="w90 text-center">状态</td>
			    <td class="w680 text-center c_22ee00">发送成功</td>
			  </tr>
			</table>
		</div> -->

			<div class="c_ff6363 font14 m_t30 p_l40 w760 lh25">
				<p>温馨提示：</p>
				<p>*短信每条70个字，若使用标签，以替换后的实际长度计算。</p>
				<p>*若出现短信拦截情况,是短信中某个词被手机软件检测为垃圾短信,只需多次尝试,去除拦截词即可，如果有链接地址，必须要前后加空格噢！若有其他问题,请直接联系客服.</p>
			</div>

		</div>

	</div>






	<!---------------导入发送名单弹窗--------------->
	<div id="send_import_show"
		class="w100_ h1370 rgba_000_5 position_absolute z_12 upload_mail_list top0 display_none">

		<div
			class="w930 p_l35 p_r35 bgc_fff margin0_auto left0 right0 p_b20 position_fixed top110">

			<div class="c_384856 text-center font18  p_t20">

				<div class="text-center m_b15">导入发送名单</div>



			</div>



			<div class="font14 h40 lh40 m_b30">
				<div class="f_l c_384856">文件名：</div>
				<input id="sendlist_fileName_input"
					class="w150 p_l10 f_l h40 lh40 border0 bgc_f4f6fa"
					value="${fileName }" />


				<div id="send_import" onclick="document.form1.picpath.click()"
					class="w100 f_l m_l10 cursor_ h40 lh40 bgc_00a0e9 c_fff text-center b_radius5">
					<!-- <input type="hidden" />
						<input id="send_import" type="button" value="文件上传">
						<span onclick="">文件上传</span> 
						文件上传 -->


					<form name="form1" action="addStartPage.action" id="form1">
						<input type="file" name="file" id="picpath" style="display: none;"
							onChange="document.form1.path.value=this.value;addMore();">
						<input type="hidden" name="path" id="path" readonly> <span
							class="btn btn1" style="margin-left: 10px;"
							>文件上传:</span>
					</form>


				</div>
				<div class="f_l c_384856 m_l10">上传时间：</div>
				<div class="f_l position_relative">
					<input class="bgc_f4f6fa border0 w165 p_l10 h40 m_r10" type="text"
						id="tser11" value="${bTime }" readonly
						onclick="$.jeDate('#tser11',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})">
					<img class="position_absolute right20 top10"
						src="${ctx}/crm/images/date_copy.png" />
				</div>

				<div class="f_l h50 lh50">~</div>

				<div class="f_l position_relative m_r20">
					<input class="bgc_f4f6fa border0 w165 p_l10 h40 m_l10" type="text"
						id="tser12" value="${eTime }" readonly
						onclick="$.jeDate('#tser12',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})">
					<img class="position_absolute right20 top10"
						src="${ctx}/crm/images/date_copy.png" />
				</div>
				<div id="find_Sendlist" onclick="findSendlist();"
					class="w100 f_l cursor_ h40 lh40 bgc_00a0e9 c_fff text-center b_radius5">
					查询</div>
				<!-- <input class="w100 f_l cursor_ h40 lh40 bgc_00a0e9 c_fff text-center b_radius5" type="submit" value="查询"> -->

			</div>
			<form id="submit_Sendlist"
				action="${ctx}/smsSendAppoint/findSmsSendlist">
				<input id="sendlist_fileName" type="hidden" name="fileName" /> <input
					id="sendlist_beginTime" type="hidden" name="beginTime" /> <input
					id="sendlist_endTime" type="hidden" name="endTime" />
			</form>


			<!--------详细数据-------->
			<table style="width:100%">
				<tr>
					<th class="w60">序号</th>
					<th class="w115">上传文件</th>
					<th class="w100">记录数</th>
					<th class="w80">成功数</th>
					<th class="w80">失败数</th>
					<th class="w100">处理状态</th>
					<th class="w125">导入时间</th>
					<th class="w230">操作</th>
				</tr>
			</table>
			<div class="overflow_auto h230">
				<table class="sendlist_tr" style="width:100%">
					<%-- <tr >
			    <th class="w75">
			    	<div class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    </th>
			    <th class="w85">序号</th>
			    <th class="w115">上传文件</th>
			    <th class="w100">记录数</th>
			    <th class="w200">处理状态</th>
			    <th class="w125">导入时间</th>
			    <th class="w250">操作</th>
			  </tr> --%>
					<%-- <c:if test="${paginationSendlist != null }">
				 <c:forEach items="${paginationSendlist.list }" var="smsSendlistImportList" varStatus="status">
				  <tr class="">
				    <td class="w75">
				    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
				    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
				    	</div>
				    </td>
				    
				    
				    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${status.index+1 }</td>
				    <td>   ${smsSendlistImportList.fileName }</td>
				    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${smsSendlistImportList.sendNumber }</td>
				    <td>
					<c:if test="${smsSendlistImportList.state == 1 }">
			    		导入失败
			    	</c:if>
			   		<c:if test="${smsSendlistImportList.state == 0 }">
			   			导入成功
			    	</c:if>
					</td>
				    <td><fmt:formatDate value="${smsSendlistImportList.importTime }" pattern="yyyy-MM-dd" /></td>
				    <td>
					    <a class="c_384856" onclick="deleteSendlist(${smsSendlistImportList.id });" >	
					    	<div class="cursor_ f_l m_l10 detail_btn margin0_auto b_radius5 w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk">
					    		删除
					    	</div>
					    </a>
					    
					    <div onclick="addSendlist(${smsSendlistImportList.id });" class="cursor_ f_l m_l10 margin0_auto close b_radius5 w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk">
					    		使用
					    </div>
				    </td>
				  </tr>
			  	</c:forEach> 
			 </c:if> --%>
					<%--  <tr class="">
			    <td class="w75">
			    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    </td>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td></td>
			  </tr>
			  <tr class="">
			    <td class="w75">
			    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    </td>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td></td>
			  </tr> --%>
				</table>
				<img class="sdg" src="${ctx}/crm/images/yu-jiazai.gif" style="display: none">
			</div>
			<!--------分页-------->
			<div class="w930 h24 m_t22 font14 c_8493a8 m_b40">
				<div class="f_r w470 h24 l_h22 font12">
					<!--  <div class="f_l w45 h22 bor_e0e6ef text-center m_r4 cursor_">上一页</div>
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
	            <div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div> -->

					<%-- <c:forEach items="${paginationSendlist.pageView }" var="page">
						${page } 
		  		</c:forEach> --%>
				</div>
			</div>

			<div class="c_8493a8 font14 m_t30 p_l40 w760 lh25">
				<p class="m_l_35">使用帮助：</p>
				<p class="m_l_25">1、直接上传所需发送的号码文件，如选择之前已上传过的历史文件，可直接在列表点击使用即可。</p>
				<p class="m_l_25">2、文件上传处理状态显示“上传完成”即可选择此文件，进入下一步操作。</p>
				<p class="m_l_25">3、单个文件上传最大限制为15万手机号，如果超出请您分批上传。</p>
			</div>

			<div class="c_8493a8 font14 m_t30 p_l40 w760 lh25">
				<p class="m_l_35">温馨提示：1.重复号码发送时会自动去重。</p>

			</div>

			<div style="width: 15vw;" class=" h42 margin0_auto m_t30 m_b20">


				<div
					class="cursor_ f_l m_r70 margin0_auto close b_radius5 w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk">
					确认</div>

				<div
					class="cursor_ f_l margin0_auto b_radius5 close w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk">
					取消</div>


			</div>

		</div>

	</div>












	




	<!---------------导出弹窗--------------->
	<div
		class="w100_ h1450 rgba_000_5 position_absolute z_10 top0 download display_none">

		<div
			class="w590 h200 bgc_fff position_fixed top250 left600 p_t20 p_l20 p_r20 p_b20">

			<div class="text-center cursor_ c_384856 font16 m_b20 h22 lh22">
				导出提示</div>

			<!--文件导入-->
			<%-- <form action="${ctx}/smsSendAppoint/marketingCenter/smsSendRecordByExport" method="get"> --%>
			<div class="w480 h42 margin0_auto">
				<div class="f_l h42 lh42 m_r5 font16">任务名称:</div>
				<input id="export_name" placeholder="必填且不可重复"
					class="w380 h42 p_l10 border0 f_l lh42 b_radius5 bgc_f4f6fa"
					name="export_name"></input>
			</div>

			<div class="w232 h42 margin0_auto m_t30 m_b20">
				<div id="submit_export"
					class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 close cursor_ font16">
					确定</div>
				<!-- <input class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 close cursor_ font16"  type="submit" value="确定" /> -->
				<div
					class="m_l30 close f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
			</div>
			<!-- </form> -->
			<div class="p_l55 c_ff6363 text-left m_t10 m_b20 font14">
				任务名称必填且不能重复</div>

		</div>

	</div>








	<!---------------分析弹窗--------------->
	<div
		class="w100_ h1370 rgba_000_5 position_absolute z_10 analysed top0 display_none">

		<div
			class="w930 p_l35 p_r35 bgc_fff margin0_auto left0 right0 p_b20 position_fixed top110">

			<div class="c_384856 text-center font18  p_t20">

				<div class="text-center m_b15">选择需要分析的活动组</div>



			</div>
			<div class="font14 h40 m_b30">

				<div class="wrap f_l h45 lh45 m_r10">
					<div class="nice-select h45 lh45 w173" name="nice-select">
						<input class="h45 lh45" type="text" value="指定号码发送" readonly>
						<ul>
							<li>指定号码发送</li>
							<li>会员营销</li>
						</ul>
					</div>
				</div>


				<div class="f_l h40 lh40 c_384856">商品ID：</div>
				<input class="w220 p_l10 f_l h40 lh40 border0 m_r10 bgc_f4f6fa" />
				<div class="f_l h40 lh40 c_384856">关键字：</div>
				<input class="w220 p_l10 f_l h40 lh40 border0 bgc_f4f6fa m_r35" />


				<div
					class="w100 f_l cursor_ h40 lh40 border_00a0e9 c_00a0e9 tk text-center b_radius5">
					搜索</div>

			</div>

			<!--------详细数据-------->
			<div class="c_384856 margin0_auto m_b10">
				<table>
					<tr class="">
						<th class="w75">
							<div
								class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
								<img class="cursor_ one_check_ display_none"
									src="${ctx}/crm/images/black_check.png" />
							</div>
						</th>
						<th class="w315">活动名称</th>
						<th class="w395">发送时间</th>
						<th class="w140">目标客户</th>
					</tr>
					<tr class="">
						<td class="w75">
							<div
								class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
								<img class="cursor_ one_check_ display_none"
									src="${ctx}/crm/images/black_check.png" />
							</div>
						</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr class="">
						<td class="w75">
							<div
								class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
								<img class="cursor_ one_check_ display_none"
									src="${ctx}/crm/images/black_check.png" />
							</div>
						</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr class="">
						<td class="w75">
							<div
								class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
								<img class="cursor_ one_check_ display_none"
									src="${ctx}/crm/images/black_check.png" />
							</div>
						</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr class="">
						<td class="w75">
							<div
								class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
								<img class="cursor_ one_check_ display_none"
									src="${ctx}/crm/images/black_check.png" />
							</div>
						</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>

			</div>
			<!--------分页-------->
			<div class="w930 h24 m_t22 font14 c_8493a8 m_b40">
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
						<input class="w45 h22 text-center bgc_f1f3f7 border0 c_8493a8"
							type="text" value="">
					</div>
					<div class="f_l w50 h24 bgc_e1ebf9 text-center cursor_">确定</div>
				</div>
			</div>


			<div class="w275 h42 margin0_auto m_t30 m_b20">


				<div
					class="cursor_ f_l m_r70 margin0_auto close b_radius5 w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk">
					确认</div>

				<div
					class="cursor_ f_l margin0_auto b_radius5 close w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk">
					取消</div>


			</div>

		</div>

	</div>


	<div class="rgba_000_5 display_none showError"
		style="width: 100%; height: 100%; position: fixed; top: 0; z-index: 111;">
		<div
			style="position: fixed; top: 12vw; z-index: 112; width: 21vw; background-color: #ffffff; left: 45vw; padding-bottom: 1vw;">

			<div style="width: 20vw; height: 12vw; padding: 1vw;">
				<p>
					导入成功: <span id="sendNumber"></span>条
				</p>
				<p>
					导入失败: <span id="errorNumber"></span>条
				</p>
				失败数据: <br />
				<div id="errorPhone"
					style="word-break: break-all; overflow-y: scroll; height: 9vw;"></div>
			</div>

			<div
				class="cursor_ margin0_auto closeError b_radius5 w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk">
				关闭</div>
		</div>
	</div>
	
	<div id="showSendBox">
		<h3><img class="fl" src="${ctx}/crm/images/showBoxIcon.png"><span class="fl">温馨提示</span></h3>
		<p>当前短信字数为<strong></strong>字，每条短信计费 <em></em> 条</p>
		<div class="showSendBoxBtn">
			<a href="javascript:;" class="qd">确定发送</a>
			<a href="javascript:;" class="qx">取消</a>
		</div>
	</div>
<!-- 	<div class="markBg"></div> -->
	
<!-- 引入商品jsp -->	
 <%@ include file="/WEB-INF/views/crms/header/itemUtils.jsp" %>
</body>
<script>
	
	//获取页面回显返回的error
	$(function(){
		var error = $("#error").val();
		if(error != ''){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text(error)
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
		}
		$('#phone_number_Content').on('keyup',function(){
			$(this).val($(this).val().replace(/^\s+|\s+$/g, ''))
		});
		addAgainSend();
	});
	
	function addAgainSend(){
		//再次发送填充数据
		var againPhones = $("#againPhones").val();
		var againContent = $("#againContent").val();
		if(againPhones != '' && againContent != ''){
			$(".text_area_phone").val(againPhones);
			$(".text_area").val(againContent.replace("退订回N",""));
			if(againContent.indexOf("【淘宝】")>-1){
				$("#taobao").show();
				$("#jingdong").hide();
				$("#tianmao").hide();
				$("#zidingyi").hide();
				addConsignee('【淘宝】');
			}else if(againContent.indexOf("【京东】")>-1){
				$("#taobao").hide();
				$("#jingdong").show();
				$("#tianmao").hide();
				$("#zidingyi").hide();
				addConsignee('【京东】');
			}else if(againContent.indexOf("【天猫】")>-1){
				$("#taobao").hide();
				$("#jingdong").hide();
				$("#tianmao").show();
				$("#zidingyi").hide();
				addConsignee('【天猫】');
			}else{
				$("#taobao").hide();
				$("#jingdong").hide();
				$("#tianmao").hide();
				$("#zidingyi").show();
				var content = againContent.split("【");
				content = content[1].split("】");
				$("#biaoqian_input").val(content[0]);
				addConsignee(null);
			}
			addLength();
		}
	}
	
	
	//给短信框中添加默认值
	$(function(){
		$(".text_area_copy").text($("#textContent").val());
		addLength();
	});
	//======查看发送记录js=====
	$(function(){
		var flag ="${flag}";
		//显示页面样式
		if(flag==1){
			$("#text_designate_out_1").removeClass("bgc_fff").addClass("bgc_e3e7f0");
			$("#text_designate_out_2").addClass("bgc_fff").removeClass("bgc_e3e7f0");
			$("#text_designate_in_2").show();
			$("#text_designate_in_1").hide();
		}
		if(flag==0){
			$("#text_designate_out_2").removeClass("bgc_fff").addClass("bgc_e3e7f0");
			$("#text_designate_out_1").addClass("bgc_fff").removeClass("bgc_e3e7f0");
			$("#text_designate_in_1").show();
			$("#text_designate_in_2").hide();
		}
		
		if(flag==2){
			$("#send_import_show").show();
			$("#send_mode_input").val('2');
			$("#upload_div").$(".o_check_").show;
			
		}
	});
	//导出
	/* $(function(){
		$("#submit_export").click(function(){
			var export_name = $("#export_name").val();
			var beginTime = $("#tser07").val();
			var endTime = $("#tser02").val();
			var phone = $("#phone").val();
			var status_page = $("#status_page").val();
			
			window.window.location.href="${ctx}/smsSendAppoint/marketingCenter/smsSendRecordByExport?export_name="
					+export_name+"&beginTime="
					+beginTime+"&endTime="
					+endTime+"&beginTime="
					+beginTime+"&phone="
					+phone+"&status_page="+status_page;
		});
	}); */
	
	
	
	//====== 指定号码发送js ======
		//加载页面时判断显示哪一部分模块内容（1：点击发送对象，2：选择模板——近期节日，3—5：选择模板——推荐场景，6：历史记录）
	/* function findSmsTemplate(type) {
		
		//设置值
		var contentVal=$("#phone_number_Content").val()
		if(type!=" "){
			window.location.href = "${ctx}/smsSendAppoint/findSmsTemplate?type="
					+ type+"&contentVal="+contentVal;
		}else{
			window.location.href = "${ctx}/smsSendAppoint/findSmsTemplate?contentVal="+contentVal;
		}
	}; */
	
	function findAllList() {
		//设置值
		var contentVal=$("#phone_number_Content").val()
		window.location.href = "${ctx}/smsSendAppoint/findAllList?contentVal="+contentVal;
	}
	
	//当页面加载完成后获取值
		/* window.onload = function(){
			var   phonenumber = $("#phone_number_input").val();
			$("#phone_number_Content").val(phonenumber)
		} */
	
	
	
	//加载页面时判断显示哪一部分模块内容（1：点击发送对象，2：选择模板——近期节日，3—5：选择模板——推荐场景，6：历史记录）
	$(function() {
		var show =<%=request.getAttribute("show")%>;		
		if (show != null && show == 1) {
			$(".sifting").show();
			$(".more_detail").show();
			addState();
		} else if (show != null && show == 2) {
			$(".text_model_window").show();
			$("#jqjrys").show();
		} else if (show != null && show >= 3) {
			$(".text_model_window").show();
			$(".jqjr").hide();
			$(".tuijian").show();
			$(".lishijilu").hide();
			document.getElementById("tuijianchangjing").className = "text_model out cursor_ bgc_fff w140 h50 lh50 text-center c_384856 f_l";
			document.getElementById("jinqijieri").className = "text_model out cursor_ bgc_e3e7f0 w140 h50 lh50 text-center c_384856 f_l";
			if (show == 4) {
				$("#sx").show();
			} else if (show == 5) {
				$("#znq").show();
			} else if(show == 6){
				$(".jqjr").hide();
				$(".tuijian").hide();
				$(".lishijilu").show();
				
				$("#tuijianchangjing").removeClass("bgc_fff").addClass("bgc_e3e7f0");
				$("#lishifasong").addClass("bgc_fff").removeClass("bgc_e3e7f0");
				
			} else{
				$("#jhs").show();
			}
		}
	});
	//将先中模板的内容添加到内容框，并修改输入文字，修改计费条数
	function addContent(content,id){
		var consignee = $("#smsChannel").val();
		$("#templateId").val(id);
		$(".text_model_window").hide();
		content = consignee+content;
		$(".text_area").val(content);
		
		$(document.body).css({
			  "overflow-x":"auto",
			  "overflow-y":"auto"
			  });
		addLength();
	};
	//判断短信输入框内容长度，修改计费条数
	function addLength(){
		var content = $(".text_area").val();
		if(content != ""){
			$(".text_area_copy").text(content.replace("退订回N","")+"退订回N");
		}else{
			$(".text_area_copy").text(content);
		}
		var contentCopy = $(".text_area_copy").text();
		var len = contentCopy.length;
		$("#inputNum").text(len);
		if(len>70){
			$("#contePrice").text(Math.ceil(len/67));
		}else{
			if(len==0){
				$("#contePrice").text(0);
			}else{
				$("#contePrice").text(1);
			}
		}
	}
	
	//修改短信内容==onblur
	function leaveEditMessage(){
		/* var content = $(".text_area_copy").val();
		$(".text_area").val(content); */
		addLength();
	}
	
	 
	//发送时间设置内容
	$(function(){
		$("#lijiSend").click(function(){
			$("#send_input").val('1');
		});
		$("#dingshiSend").click(function(){
			$("#send_input").val('2');
			var timestamp = Date.parse(new Date())+1800000;
			$("#tser01").val(show(timestamp));
		});
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
	
	
	//手机号码内容设置
	function addPhone(){
		//设置长度
		var length=$("#phone_number_Content").val().length;
		document.getElementById("phone_Num").innerText=length;
		//设置值
		var contentVal=$("#phone_number_Content").val()
		$("#phone_number_input").val(contentVal);
	}
	
	//发送方式设置内容
	$(function(){
		$("#manual_div").click(function(){
			$("#manual_div").children(".o_check_").show()
			$("#upload_div").children(".o_check_").hide();
			$(".manual_in").show();
			$(".upload_in").hide();
			$("#send_mode_input").val('1');
			$(".fileNameDiv").hide();
		});
		$("#upload_div").click(function(){
			$("#manual_div").children(".o_check_").hide()
			$("#upload_div").children(".o_check_").show();
			$(".manual_in").hide();
			$(".upload_in").show();
			$("#send_mode_input").val('2');
			var phonesId = $("#phonesId").val();
        	if(phonesId!=""){
        		$(".fileNameDiv").show();
        	}
		});
		
	});
	
	//重复发送过滤设置内容
	var shieldNum = 0
	$(function(){
		$("#shield_div").click(function(){
			if(shieldNum%2==0){
				$("#send_filter").val('1');
			}else{
				$("#send_filter").val('0');
			}
			shieldNum++;
			
		});
	});
	
	
	//短信渠道的时候,复值
	function addConsignee(button){
		if(button=='【淘宝】'){
			$("#note_ditch_input").val('1');
			$(".dxqm").addClass("display_none")
		}
		if(button=='【京东】'){
			$("#note_ditch_input").val('2');
			$(".dxqm").addClass("display_none")
		}
		if(button=='【天猫】'){
			$("#note_ditch_input").val('3');
			$(".dxqm").addClass("display_none")
		}
		if(button==null){
			$("#note_ditch_input").val('4');
			$(".dxqm").removeClass("display_none")
		}
		
		//获取短信内容
		var messageContent = $(".text_area").val()+"";
		
		//获取输入框的值
		var messageConsignee = $("#biaoqian_input").val();
		if(messageConsignee !=null && messageConsignee !=""){
			messageConsignee = "【"+messageConsignee+"】";
		}
		//获取拷贝框的值
		var copyConsignee = $("#copyConsignee").val();
		if(copyConsignee ==null || copyConsignee=="" ){
			copyConsignee = messageConsignee;
		}
		
		if(button==null){
			//替换
			messageContent = messageConsignee+messageContent.replace(copyConsignee,"").replace("【天猫】","").replace("【京东】","").replace("【淘宝】","");
			//备份
			$("#copyConsignee").val(messageConsignee);
			
			//短信签名
			$("#smsChannel").val(messageConsignee);
			
		}else{
				messageContent = button+ messageContent.replace("【天猫】","").replace("【京东】","").replace("【淘宝】","").replace(copyConsignee,"");
				//短信签名
				$("#smsChannel").val(button);
		}
		
		//展示
		$(".text_area").val(messageContent);

		//计算长度
		addLength();
	}
	
	//文件上传
	function addMore(){
		var reg=new RegExp("(.xls|.xlsx)$");
			   if(!reg.test($("#picpath").val().toLowerCase())){
					$(".tishi_2").show();
					$(".tishi_2").children("p").text("请选择正确的文件类型")
					setTimeout(function(){ 
						$(".tishi_2").hide()
					},3000)
			 		return;
			 	}
	   $(".tishi_2").show();
		$(".tishi_2").children("p").text("上传中....")
		
	 	$("#form1").attr("action", "${ctx}/smsSendAppoint/uploadFileIsPhone");
	 	$("#form1").ajaxSubmit({
	 		type:'post',
			dataType:'json',
			success : function(data) {
				if (data.error=='2'){
					 $(".tishi_2").show();
						$(".tishi_2").children("p").text("处理中...请点击查询按钮查看进度")
						setTimeout(function(){
							$(".tishi_2").hide()
						},5000);
						findSmsSendlist();
				}else{
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("文件导入异常！请重试。")
						setTimeout(function(){
							$(".tishi_2").hide()
						},5000);
						findSmsSendlist();
				}
			}
		});
	 	
	}
	
	
	
	//删除导入的数据
	function deleteSendlist(sendlistId,state){
			if(confirm("确认要删除？")){
				var url = "${ctx}/smsSendAppoint/deleteSmsSendlist";
				$.post(url, {"sendlistId":sendlistId}, function (data) {
					$("#phonesId").val("");
		        	$("#fileNameShow").text("");
		        	$(".fileNameDiv").hide();
					findSmsSendlist();
				});
			} 
	}
	$("#exportFile1").on("click",function(){
		var flag = confirm("是否导出当前查询内容？");
		if(flag){
			var startTime = $("#tser07").val();
			var endTime = $("#tser02").val();
			var phone = $("#phone").val();
			var sendStatus = $("#status_page").val();
			var ctx = $("#ctx").val();
//			console.log("发送时间： " + startTime);
//			console.log("结束时间： " + endTime);
//			console.log("手机号码： " + phone);
//			console.log("发送状态： " + sendStatus);
			window.open(ctx+"/smsSendAppoint/marketingCenter/smsSendRecordByExport"+
					"?startTime="+startTime+"&endTime="+endTime+
					"&phone="+phone+"&sendStatus="+sendStatus);
		}
	});
	//点击使用将电话号码查询出来放入到电话中
	var clickFlag = false;
	function addSendlist(importPhoneById,state){
		if(clickFlag==true){
			return;
		}
		clickFlag = true;
		if(state == 2){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("上传中数据不能使用!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			findSmsSendlist();
			},2000);
		}else{
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("加载中...")
			var url = "${ctx}/smsSendAppoint/findImportPhoneById";
			$.post(url, {"sendlistId": importPhoneById}, function (data) {
				clickFlag = false;
				if(data.state != null){
					if (data.state==1) {
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("导入失败数据不能使用!")
						setTimeout(function(){ 
							$(".tishi_2").hide()
						},2000)
			        }else if(data.state==2){
			        	$(".tishi_2").show();
						$(".tishi_2").children("p").text("上传中数据不能使用!")
						setTimeout(function(){ 
							$(".tishi_2").hide()
						findSmsSendlist();
						},2000);
			        }else{
			        	$("#phonesId").val(importPhoneById);
			        	$("#fileNameShow").text(data.fileName)
			        	$(".fileNameDiv").show();
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("使用成功")
						setTimeout(function(){ 
							$(".tishi_2").hide()
						},3000)
			        	$("#send_import_show").hide();
			        }
				}
		    });
		}
		
	}
	
	//ajax查询导入发送名单
	function findSmsSendlist(){
		$("#sendlist_fileName_input").val("");
		$("#tser11").val("");
		$("#tser12").val("");
		findSendlist();
	} 
	
	function FormatDate (strTime) {
	    var date = new Date(strTime);
	    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	}
	
	//点击查询通过条件查询导入发送名单数据
	var clickFlag = false;
	function findSendlist(){
		if(clickFlag==true){
			return;
		}
		clickFlag = true;
			//获取数据
			 var fileName = $("#sendlist_fileName_input").val();
			 var beginTime = $("#tser11").val();
			 var endTime = $("#tser12").val();
			 //使用添加数据
			 $('.sendlist_tr tr').remove()
			 $(".sdg").show();
				var url = "${ctx}/smsSendAppoint/findSmsSendlist";
				$.post(url,{"fileName":fileName,"beginTime":beginTime,"endTime":endTime},function (data) {
					clickFlag = false;
					$(".sdg").hide();
					var list = data.paginationSendlist;
					var item;
					if(list ==null || list=="undefined" || list=='' ){
						item = "<tr><td colspan='8' style='height:50px' class='text-center'>暂时没有相关数据!</td></tr>";
						$('.sendlist_tr').append(item);
					}else{
						$.each(list,function(i,result){
							var state = "";
							if(result.state==0){
								state="上传完成";
							}else if(result.state==1){
								state="上传失败";
							}else if(result.state==2){
								state="上传中";
							}
							var importTime = FormatDate(result.importTime);
							var num = i+1;
							item = "<tr><td class='w60 text-center'>"+num+"</td>"
							+"<td class='w115 text-center'>"+result.fileName+"</td>"
							+"<td class='w100 text-center'>"+result.sendNumber+"</td>"
							+"<td class='w80 text-center'>"+result.successNumber+"</td>"
							+"<td class='w80 text-center'>"+result.errorNumber+"</td>"
							+"<td class='w100 text-center'>"+state+"</td>"
							+"<td class='w125 text-center'>"+importTime+"</td>"
							+"<td class='w230 text-center'><div onclick='deleteSendlist("+result.id +","+result.state+");' class='cursor_ f_l m_l10 detail_btn margin0_auto b_radius5 w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk'>删除</div>"
							+"<div onclick='addSendlist("+result.id +","+result.state+");' class='cursor_ f_l m_l10 margin0_auto close b_radius5 w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk'>使用</div></td></tr>";
							$('.sendlist_tr').append(item);
						}); 
					}
				});
	}
	
	//测试短信发送
	$(".cheShiFaSong1").click(function(){
		var that=$(this);
		if($(this).hasClass('on'))return;
		$(this).addClass('on');	
		var phonez=/^(13[0-9]|14[5,7]|15[0-3,5-9]|166|17[6-8]|18[0-9]|19[8,9])\d{8}$/;
		var phone=$(".Phone").val();//获取电话
		var content = $(".text_area").val();//获取短信内容
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
					that.removeClass('on');
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
				},'json');

			}else if(phoneflag == 1){
				that.removeClass('on');
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("手机号错误!")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)   
			}else if(phoneflag==2){
				that.removeClass('on');
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("手机号不能大于5个")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)   
			}
		}else{
			that.removeClass('on');
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容不能为空!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)  
		}
	});
	
	
	
	
	//提交表单,传入form表单id获取提交表单
	var flag = false;
	function submitForm(fomrId){
		if(flag == true){
			return;
		}
		
		var sendMode = $("#send_mode_input").val();// 发送方式 1--手动输入号码,2---批量上传号码
		var channel = $("#note_ditch_input").val();// 短信渠道 1--淘宝 2--京东 , 3--天猫 , 4---自定义签名
		var sendShield = $("#send_filter").val();// 重复发送过滤 0--不屏蔽 1--屏蔽
		var sendDay = $("#sendDay").val();// 屏蔽后设置的天数
		var templateId = $("#templateId").val();// 使用的短信模板id
		var activityName = $("#activityName").val();//活动名称
		var textContent = $(".text_area_copy").text();// 短信内容
		var phoneNums = $("#phone_number_Content").val();// 手机号码
		var smsChannel = $("#smsChannel").val();// 短信签名
		var sendInput = $("#send_input").val();// 发送时间 1---立即发送 2---定时发送
		var tser01Val = $("#tser01").val();// 定时发送的时间
		var phonesId = $("#phonesId").val();// 获取上传号码的id
		
		var i = textContent.indexOf(smsChannel);//判断短信中是否有签名
		
		//获取当前时间后半小时，并转换
		var minDate = minDateJudge();
		var newDate = new Date(minDate);
		var setDate = new Date(tser01Val);
		
		//校验电话号码
		var phoneNum = phoneNums.split(",");
		
		var result = validatePhone(phoneNum);
		var regName=textContent.match(/(【)([\S\s\t]*?)(】)|(\[)([\S\s\t]*?)(\])/g);
		var num=textContent.match(/\[|\]|【|】/g);
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
		}else if(regName[0].indexOf(smsChannel)==-1){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text('文案签名与渠道签名不符')
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return;
		}else if(textContent.search(reEsIndex)>0){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text('签名必须放在文案开头')
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return;
		}
		if((phoneNums == null&&sendMode == 1) || (phoneNums ==""&&sendMode == 1)){
			$(".tishi_2").show();
    		$(".tishi_2").children("p").text("手机号不能为空！")
    		setTimeout(function(){ 
    			$(".tishi_2").hide()
    		},3000)
		}else if(phoneNums!="" &&　phoneNums.indexOf("，")>-1 && sendMode == 1){
			$(".tishi_2").show();
    		$(".tishi_2").children("p").text("多个号码使用英文逗号隔开！")
    		setTimeout(function(){ 
    			$(".tishi_2").hide()
    		},3000)
		}else if(phoneNums!="" && result.length!=0 && sendMode == 1){
			$(".tishi_2").show();
    		$(".tishi_2").children("p").text("您输入的手机号码不正确："+result)
    		setTimeout(function(){ 
    			$(".tishi_2").hide()
    		},3000)
		}else if((phonesId == null&&sendMode == 2) || (phonesId ==""&&sendMode == 2)){
			$(".tishi_2").show();
    		$(".tishi_2").children("p").text("请选择要发送的手机号文件!")
    		setTimeout(function(){ 
    			$(".tishi_2").hide()
    		},3000)
		}else if(textContent == null || textContent == ""){
			$(".tishi_2").show();
    		$(".tishi_2").children("p").text("短信内容不能为空！")
    		setTimeout(function(){ 
    			$(".tishi_2").hide()
    		},3000)
		}else if(i == -1 || smsChannel == ""){
			$(".tishi_2").show();
    		$(".tishi_2").children("p").text("短信签名不能为空!")
    		setTimeout(function(){ 
    			$(".tishi_2").hide()
    		},3000)
		}else if(sendInput =="2" && tser01Val == ""){
			$(".tishi_2").show();
    		$(".tishi_2").children("p").text("定时发送时间不能为空!")
    		setTimeout(function(){ 
    			$(".tishi_2").hide()
    		},3000)
		}else if(sendInput =="2" && tser01Val != "" && setDate<newDate){
				$(".tishi_2").show();
	    		$(".tishi_2").children("p").text("定时发送时间应设置在半小时以后！")
	    		setTimeout(function(){ 
	    			$(".tishi_2").hide()
	    		},3000);
		}else{
			$('#showSendBox').show();
			$('.markBg').show();
			$('#showSendBox p strong').text($('#inputNum').text());
			$('#showSendBox p em').text($('#contePrice').text());
		}
		
	}
	$(function(){
		$('#showSendBox .qd').click(function(){
			if($(this).hasClass('jz'))return;
			$(this).addClass('jz');
			var sendMode = $("#send_mode_input").val();// 发送方式 1--手动输入号码,2---批量上传号码
			var channel = $("#note_ditch_input").val();// 短信渠道 1--淘宝 2--京东 , 3--天猫 , 4---自定义签名
			var sendShield = $("#send_filter").val();// 重复发送过滤 0--不屏蔽 1--屏蔽
			var sendDay = $("#sendDay").val();// 屏蔽后设置的天数
			var templateId = $("#templateId").val();// 使用的短信模板id
			var activityName = $("#activityName").val();//活动名称
			var textContent = $(".text_area_copy").text();// 短信内容
			var phoneNums = $("#phone_number_Content").val();// 手机号码
			var smsChannel = $("#smsChannel").val();// 短信签名
			var sendInput = $("#send_input").val();// 发送时间 1---立即发送 2---定时发送
			var tser01Val = $("#tser01").val();// 定时发送的时间
			var phonesId = $("#phonesId").val();// 获取上传号码的id

			if(sendInput =="2"){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("定时保存中...");
			}else{
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("短信发送中...");
			}
			
			var _this=$(this);
			
			//使用ajax提交参数
		 	$.ajax({
		 		url:"${ctx}/smsSendAppoint/marketingCenter/saveSmsSendRecord",
		 		type:'post',
				dataType:'json',
				data:{"sendMode":sendMode,"recNum":phoneNums,"channel":channel,"autograph":smsChannel,"content":textContent,"sendShield":sendShield,"sendDay":sendDay,"sendTimeType":sendInput,"sendTime":tser01Val,"templateId":templateId,"activityName":activityName,"phonesId":phonesId},
				success : function(data) {
					
					if(data.error != ""){

						$(".tishi_2").show();
			    		$(".tishi_2").children("p").text(data.error)
			    		setTimeout(function(){ 
			    			$(".tishi_2").hide();
			    			_this.removeClass('jz');	
			    		},4000);
					}else{
						// 发送时间 1---立即发送 2---定时发送
			    		if(data.sendTimeType == 1){
			    			//跳转发送记录
			    			window.location.href="${ctx}/msgSendRecord/specificSendRecord";
			    			_this.removeClass('jz');	
			    		}else if(data.sendTimeType == 2){
			    			//跳转定时保存记录
			    			window.location.href="${ctx}/msgSendRecord/toSendRecord";
			    			_this.removeClass('jz');	
			    		}
					}

					
					
				}
			});
		});
		$('#showSendBox .qx').click(function(){
			$('#showSendBox').hide();
			$('.markBg').hide();
		});
	});
	//点击历史记录
	function findHistorySms(){
		window.location.href="${ctx}/smsSendAppoint/marketingCenter/findSmsSendRecordByCondition";
	}
	function nearOneSky(){
		window.location.href="${ctx}/smsSendAppoint/marketingCenter/findSmsSendRecordByCondition?nearOneSky=oneSky";
	}
	
	
	
	
	//获取生成链接的类型
	function linkDate(link){
		$("#linkType").val(link);
		if(link=='店铺链接'){
			$('.showBoxone').show();
			$('.showBoxtwo').hide();
			$('.showBoxthree').hide();
		}else if(link=='商品链接'){
			$('.showBoxone').hide();
			$('.showBoxtwo').show();
			$('.showBoxthree').hide();
		}else if(link=='活动页链接'){
			
			$('.showBoxone').hide();
			$('.showBoxtwo').hide();
			$('.showBoxthree').show();
		}
	}
	
	//根据生成链接类型，将请求发送到后台生成短链接
	function linkSubmit(){
		var linktype=$("#linkType").val();
		var linkName=$("#linkName").val();
		var commodityId=$("#commodityId").val();
		var activityUrl= $('.activityLinkTxt').val();
		if(linktype == ""){
    		$(".tishi_2").show();
    		$(".tishi_2").children("p").text("请选择链接类型")
    		setTimeout(function(){ 
    			$(".tishi_2").hide()
    		},3000)
		}else{
			var url = "${ctx}/GenerateLinkTo/generateLink";
			$.post(url,{"linkType":linktype,"linkName":linkName,"commodityId":commodityId,activityUrl:activityUrl},function (data) {
				$('#commodityId').val('');
				$('.activityLinkTxt').val('');
				if(!data.message){
					$(".tishi_2").show();
		    		$(".tishi_2").children("p").text("网址错误！")
		    		setTimeout(function(){ 
		    			$(".tishi_2").hide()
		    		},3000)
		    		return;
				}
				if(data.link != null){
					 $(".text_area").val(startContent+data.link+endContent);
					 addLength();
				}
			});
		}
	}
	
	
	
	//选择模板
	function choseTemplate(item,type){
		
		if(item=='recentFestival'){
			$(".temp2").eq(0).show();
			$(".temp2").eq(1).hide();
			$(".temp2").eq(2).hide();
			
			$(".temp1").eq(0).addClass("bgc_fff").removeClass("bgc_e3e7f0");
			$(".temp1").eq(1).removeClass("bgc_fff").addClass("bgc_e3e7f0");
			$(".temp1").eq(2).removeClass("bgc_fff").addClass("bgc_e3e7f0");
			
			$(".tempImg").eq(0).hide();
			$(".tempImg").eq(1).hide();
			$(".tempImg").eq(2).hide();
		}
		//清空数据
		//$('#table_jinqijieri_tr').siblings().remove();
		$('#table_tuijianchangjin_tr').siblings().remove();
		$('#table_lishifasong_tr').siblings().remove();
		//item主题,type类型
		var item =item;
		var type =type;
			/* if(item =='recentFestival'){
				
			 $.ajax({
					url:"${ctx}/SmsTemplate/findSmsTemplate",
					data:{"item":item,"type":type},
					type: "post",
					success:function(data){
						var festival = data.festival;
						var smsTemplateList = data.smsTemplateList;
						
						if(festival != null){
							$("#recentFestival").html(festival.name);
						}
						var appendTemp = "";
						if(smsTemplateList ==null || smsTemplateList=="undefined" || smsTemplateList=='' ){
							appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
						$('#table_jinqijieri').append(appendTemp);
						}else{
							$.each(smsTemplateList,function(index,smsTemplate){
								var num = index+1;
								appendTemp = "<tr class=''><td class='w75 text-center'>"
											 +num+"</td><td class='w760 text-center'>"
											 +smsTemplate.content+"</td><td class='w90 text-center'>"
											 +"<div class='border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto cursor_ b_radius5'" 
							 				 +"onClick=\"addContent("+"'"+smsTemplate.content+"','"+smsTemplate.id+"')\">"
											 +"使用</div></td></tr>";
									
							$('#table_jinqijieri').append(appendTemp);
							});
						}
					},
					dataType:'JSON'
				}); 
			}else */ if(item =='recommendedScene'){
				/* 
				if(type != null && type=="聚划算"){
					$("#jhs").show();
					$("#sx").hide();
					$("#znq").hide();
				}else if(type != null &&　type=="上新"){
					$("#jhs").hide();
					$("#sx").show();
					$("#znq").hide();
				}else if(type != null &&　type=="周年庆"){
					$("#jhs").hide();
					$("#sx").hide();
					$("#znq").show();
				}   */
				
				 $.ajax({
						url:"${ctx}/SmsTemplate/findSmsTemplate",
						data:{"item":item,"type":type},
						type: "post",
						success:function(data){
							var smsTemplateList = data.smsTemplateList;
							var appendTemp = "";
							if(smsTemplateList ==null || smsTemplateList=="undefined" || smsTemplateList=='' ){
								appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
							$('#table_tuijianchangjin').append(appendTemp);
							}else{
								$.each(smsTemplateList,function(index,smsTemplate){
									var num = index+1;
									appendTemp = "<tr class=''><td class='w75 text-center'>"
												 +num+"</td><td class='w760 text-center'>"
												 +smsTemplate.content+"</td><td class='w90 text-center'>"
												 +"<div class='border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto cursor_ b_radius5'" 
								 				 +"onClick=\"addContent("+"'"+smsTemplate.content+"','"+smsTemplate.id+"')\">"
												 +"使用</div></td></tr>";
										
								$('#table_tuijianchangjin').append(appendTemp);
								});
							}
						},
						dataType:'JSON'
					}); 
				
			}else if(item =='sendHistory'){
				 $.ajax({
						url:"${ctx}/historyTemplate/findAllList",
						data:{"type":"指定号码群发"},
						type: "post",
						success:function(data){
							var smsHistoryTemplates = data;
							var appendTemp = "";
							if(smsHistoryTemplates ==null || smsHistoryTemplates=="undefined" || smsHistoryTemplates=='' ){
								appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
							$('#table_lishifasong').append(appendTemp);
							}else{
								$.each(smsHistoryTemplates,function(index,smsHistoryTemplate){
									var num = index+1;
									appendTemp = "<tr class=''><td class='w75 text-center'>"
												 +num+"</td><td class='w760 text-center'>"
												 +smsHistoryTemplate.content+"</td><td class='w90 text-center'>"
												 +"<div class='border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto cursor_ b_radius5'" 
								 				 +"onClick=\"addContent("+"'"+smsHistoryTemplate.content+"','"+smsHistoryTemplate.id+"')\">"
												 +"使用</div></td></tr>";
										
								$('#table_lishifasong').append(appendTemp);
								});
							}
						},
						dataType:'JSON'
					}); 
			}

	}
	
	//计费规则新建标签跳转
	$("#jfgz").click(function(){
			var ctx = $("#ctx").val();
			window.open(ctx+"/crms/home/notice#duanxinxiangguan");
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
	
	$('.activityLink').on('click',function(){
		$('#activityLinkTxtBox').show();
	});
	$('#hideTxtBoxQX').on('click',function(){
		$('#commodityId').val('');
		$('.activityLinkTxt').val('');
	});
</script>
</html>