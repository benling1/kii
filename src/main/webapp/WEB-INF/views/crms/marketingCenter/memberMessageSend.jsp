<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>营销中心-会员短信群发</title>
<%@ include file="/common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js"></script> --%>
<script type="text/javascript" src="${ctx}/crm/js/vip_text.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/laypage.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/xuanzeshangpin.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
			<div class="w1645  m_l30 ">
				<div class="w100_ lh50 bgc_fff font18">
					<div class="f_l h50 w4 bgc_1d9dd9"></div>
					<div class="f_l m_l15 c_384856">营销中心</div>
					<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
						<a class="c_384856" href="${ctx}/marketingCenter">
							<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
								会员短信群发</div>
						</a> <a class="c_384856"
							href="${ctx}/smsSendAppoint/appointNumberSend">
							<div class="f_l w140 text-center cursor_ ">指定号码群发</div>
						</a> <a class="c_384856" href="${ctx}/crms/marketingCenter/list?label=1">
							<div class="f_l w140 text-center cursor_">订单短信群发</div>
						</a><a class="c_384856" href="${ctx}/msgSendRecord/memberSendRecord">
							<div class="f_l w140 text-center cursor_">短信发送记录</div>
						</a>
						<a class="c_384856" href="${ctx}/member/msgSendRecord">
								<div class="f_l w200 text-center cursor__">
									会员短信群发效果分析
								</div>
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
			</div>


			<!---------------会员短信群发--------------->
			<div style="margin-left: 1.5625vw;">
				<!----------上部---------->
				<div class="w1605 h130 bgc_f1f3f7 c_384856 p_l40 p_t30">

					<!---------------标题--------------->
					<div class="font24 m_b10">会员短信群发</div>
					<!---------------描述--------------->
					<div class="font14">
						可以向客户发送打折促销短信，节日关怀短信等。为了不影响客户休息，建议您发送短信时间在8点至22点之间。</div>

					<div class="font16 c_384856 h50 lh50 m_t18">

						<div
							class="w200 h50 text-center f_l bgc_fff text_designate_out cursor_ display_none">
							会员短信发送</div>
						<div
							class="w200 h50 text-center f_l bgc_e3e7f0 text_designate_out cursor_ display_none">
							效果统计</div>
					</div>

				</div>
				<!----------下部---------->
				<div
					class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in position_relative">

					<div class="">
						<div class="f_l lh40 c_384856">群发对象：</div>
						<div style="padding: 0.2vw;" id="sendButton"
							class="f_l sifting_btn tk border_00a0e9 c_00a0e9 cursor_ h35 lh35 text-center b_radius5 m_r20  "
							onclick="searchAll()">点击选择发送对象</div>
						<div id="memberCount"
							class="display_none f_l w300  bgc_00a0e9  c_fff cursor_ h40 lh40 text-center b_radius5">
							已选择 <span id="memberNum"></span>个用户！
						</div>
						<input id="sendMember" type="hidden" value="${memberNum }">
						<input id="queryKey" type="hidden" value="${queryKey }">
						<div class="clear"></div>
					</div>

					<!--------操作选项-------->
					<div class="font14 h52 m_b15">
						<div class="f_l font16 lh52 lh52 c_384856">短信渠道：</div>
						<input type="hidden" id="smsChannel">
						<div class="f_l m_r60 m_t16 qianming_none">
							<div onclick="addConsignee('【淘宝】')"
								class="cursor_ one_check_only_add w20 h20 border_d9dde5 b_radius5 f_l">
								<img class="cursor_ one_check_ display_inline w20 h20"
									src="${ctx}/crm/images/black_check.png" />
							</div>
							<div class="m_l10 f_l font14 c_384856">淘宝</div>
						</div>
						<div class="f_l m_t16 m_r60 qianming_none">
							<div onclick="addConsignee('【京东】')"
								class="cursor_ one_check_only_add w20 h20 border_d9dde5 b_radius5 f_l">
								<img class="cursor_ one_check_ display_none w20 h20"
									src="${ctx}/crm/images/black_check.png" />
							</div>
							<div class="m_l10 f_l font14 c_384856">京东</div>
						</div>
						<div class="f_l m_t16 m_r60 qianming_none">
							<div onclick="addConsignee('【天猫】')"
								class="cursor_ one_check_only_add w20 h20 border_d9dde5 b_radius5 f_l">
								<img class="cursor_ one_check_ display_none w20 h20"
									src="${ctx}/crm/images/black_check.png" />
							</div>
							<div class="m_l10 f_l font14 c_384856">天猫</div>
						</div>
						<div class="f_l m_t16 qianming_done">
							<div onclick="addConsignee('')"
								class="cursor_ one_check_only_add w20 h20 border_d9dde5 b_radius5 f_l">
								<img class="cursor_ one_check_ display_none w20 h20"
									src="${ctx}/crm/images/black_check.png" />
							</div>
							<div class="m_l10 f_l font14 c_384856">
								自定义&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</div>
						</div>
						<div class="display_none" id="DXQM">
							<div class="f_l lh52 c_384856 ">短信签名：</div>
							<input class="bgc_f4f6fa qianming_ border0 w215 h50 f_l m_r10"
								onkeyup="addConsignee()" onblur="addConsignee()"
								id="messageConsignee" /> <input type="hidden"
								id="copyConsignee" />
						</div>
					</div>
					<div class="font16 lh52 lh52 c_384856">

						活动名称:<input
							class="bgc_f4f6fa border_d9dde5 b_radius5 w200 h40 m_l10 m_r25 p_l10"
							id="activityName" /> <span style="color: red;">为了您方便查找历史群发短信,请添加活动名称</span>
					</div>
					<!-- 
							<div class="h52 lh52">
								
							</div>
							 -->
					<div class="h430 m_t30">
						<div class="f_l lh40 c_384856">短信内容：</div>
						<div class="f_l b_radius5 bgc_f4f6fa position_relative">
							<textarea id="textcontent" maxlength="1000"
								class="text_area w460 h380 border0 bgc_f4f6fa b_radius5 p_l10 p_r10 p_t10"
								onkeyup="editMessage()" onblur="leaveEditMessage()">【淘宝】</textarea>
							<div class="w440 c_cecece p_l20 h40 lh40 b_radius5">
								已输入：<span id="inputNum" class="text_count">8</span>&nbsp&nbsp&nbsp
								当前计费：<span id="contePrice">1</span> 条 <a
									href="${ctx}/crms/home/notice#duanxinxiangguan" target="_blank"
									class="c_00a0e9 cursor_">计费规则</a>
							</div>
							<div
								class="W100 h20 bgc_f4f6fa lh20 position_absolute right20 bottom15">
								<div class="f_l c_bfbfbf">退订回</div>
								<div class="f_l">
									<input class="w15 text-center border0 bgc_f4f6fa c_384856"
										placeholder="N" value="N" readonly id="placeholderN"
										onblur="placeholderN()" onkeyup="placeholderN()" />
								</div>
							</div>
						</div>
					</div>

					<div class="h42 m_t25 c_384856">

						<div class="f_l lh40 c_384856">短信变量：</div>
						<div
							class="short_url_btn f_l m_r15 w110 cursor_ h40 lh40 text-center b_radius5 border_00a0e9 c_00a0e9 tk"
							onclick="getCursortPosition(document.getElementById('textcontent'))">
							生成短网址</div>

						<div
							class="f_l text_model_btn w110 cursor_ h40 lh40 text-center b_radius5 border_00a0e9 c_00a0e9 tk"
							onClick="choseTemplate('recommendedScene',null)">选择模板</div>
					</div>



					<div class="h40 m_t15">
						<div class="f_l lh40 c_384856">发送时间：</div>
						<div class="f_l">
							<input id="sendStatus" name="sendStatus" type="hidden">
							<div class="f_l m_r40 set_time_none">
								<div
									class="m_t10 cursor_ one_check_only_add w20 h20 border_d9dde5 b_radius5 f_l"
									onclick="getSendStatus(1)">
									<img class="cursor_ one_check_ display_inline w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="m_l10 f_l font14 c_384856 lh40">立即发送</div>
							</div>
							<div class="f_l ">
								<div
									class="m_t10 set_time  cursor_ one_check_only_add w20 h20 border_d9dde5 b_radius5 f_l"
									onclick="getSendStatus(2)">
									<img class="cursor_ one_check_ display_none w20 h20"
										src="${ctx}/crm/images/black_check.png" />
								</div>
								<div class="m_l10 f_l font14 c_384856 lh40">定时发送</div>
							</div>
							<div class="f_l position_relative m_l10 set_time_ display_none">
								<input class="bgc_f1f3f7 b_radius5 border0 w230 p_l10 h40 m_r10"
									type="text" id="tser01" placeholder="请选择时间" readonly
									onclick="$.jeDate('#tser01',{minDate: $.nowDate(0),insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm'})">
								<img style="width: 1vw;" class="position_absolute right20 top12"
									src="${ctx}/crm/images/date_copy.png" />
							</div>
						</div>
						<div class="clear"></div>
						<p style="color:red;">提示:定时发送时间必须晚于当前时间半个小时以上</p>
					</div>




					<div class="p_l180 h40 m_t70">

						<div
							class="f_l m_r15 w110 cursor_ h40 lh40 text-center b_radius5 bgc_00a0e9 c_fff"
							onclick="sendMessage()">确定发送</div>
						<div
							class="f_l text_test_btn w110 cursor_ h40 lh40 text-center b_radius5 bgc_d4e100 c_fff">
							短信测试</div>

					</div>
					<div class="position_absolute iphone w327 left640 top240 h642">
						<textarea disabled="disabled"
							class="text_area_copy m_l45 m_t150 w250 h370 border0">【淘宝】退订回N</textarea>
					</div>
				</div>


				<!----------下部---------->
				<div
					class="w1605 bgc_fff p_t35 p_l40 p_b40 text_designate_in display_none">

					<div class="">

						<!--------使用帮助-------->
						<div class="m_b22 font14 c_384856">

							<!--------查询设置-------->
							<div class="font14 c_384856 h50 m_b40">

								<div class="font16 f_l font14 c_384856 lh40">时间范围：</div>

								<div class="f_l  m_r20 m_t10 day_only">
									<div class="cursor_  w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 c_384856">7天</div>
								</div>


								<div class="f_l  m_r20 m_t10 day_only">
									<div class="cursor_  w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 c_384856">15天</div>
								</div>

								<div class="f_l  m_r20 m_t10 day_only">
									<div class="cursor_  w20 h20 border_d9dde5 b_radius5 f_l">
									</div>
									<div class="m_l10 cursor_ f_l font14 c_384856">1个月</div>
								</div>

								<div class="f_l position_relative date_only">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10"
										type="text" id="tser07" readonly
										onclick="$.jeDate('#tser07',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})">
									<img style="width: 1vw" class="position_absolute right20 top15"
										src="${ctx}/crm/images/date_copy.png" />
								</div>

								<div class="f_l h50 lh50">~</div>

								<div class="f_l position_relative date_only">
									<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40"
										type="text" id="tser02" readonly
										onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD'})">
									<img style="width: 1vw;"
										class="position_absolute right50 top15"
										src="${ctx}/crm/images/date_copy.png" />
								</div>



								<div
									class="w100 h50 lh50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
									查询</div>
							</div>




						</div>

						<!--------详细数据-------->
						<div class="c_384856">

							<table class="text-center">
								<tr class="">
									<th class="w330 h100">
										<p class="h50 lh50">目标客户数</p>
										<p class="c_00a0e9 h50 lh50">33</p>
									</th>
									<th class="w330 h100">
										<p class="h50 lh50">发送成功率</p>
										<p class="c_00a0e9 h50 lh50">100%</p>
									</th>
									<th class="w330 h100" rowspan="4">
										<p class="h50 lh50">投入产出比ROI</p>
										<p class="c_00a0e9 h50 lh50">1:0.0</p>
									</th>
								</tr>
								<tr class="">
									<th class="w330 h100">
										<p class="h50 lh50">响应客户数</p>
										<p class="c_00a0e9 h50 lh50">0</p>
									</th>
									<th class="w330 h100">
										<p class="h50 lh50">响应订单数</p>
										<p class="c_00a0e9 h50 lh50">0</p>
									</th>
								</tr>
								<tr class="">
									<th class="w330 h100">
										<p class="h50 lh50">实际发送客户数</p>
										<p class="c_00a0e9 h50 lh50">33</p>
									</th>
									<th class="w330 h100">
										<p class="h50 lh50">累计响应金额</p>
										<p class="c_00a0e9 h50 lh50">0.00</p>
									</th>
								</tr>
								<tr>
									<th class="w330 h100">
										<p class="h50 lh50">转化率</p>
										<p class="c_00a0e9 h50 lh50">0%。</p>
									</th>
									<th class="w330 h100">
										<p class="h50 lh50">平均客单价</p>
										<p class="c_00a0e9 h50 lh50">0.00元</p>
									</th>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	</div>
	<!---------------短网址生成弹窗--------------->
	<div
		class="w100_ h1370 rgba_000_5 position_absolute z_12 short_url top0 display_none">

		<div
			class="w740 bgc_fff margin0_auto position_fixed top200 left550  p_t20 p_l20 p_r20 p_b20">

			<div class="c_384856 text-center font18 h25 lh25">短网址生成</div>

			<div class="h40 p_l70 m_t40 m_b20">

				<input type="hidden" name="linkType" id="linkType" value="店铺链接">
				<div class="margin0_auto  w300" style="width:22.6vw;">
					<div class="f_l m_r40 short_out" onclick="linkDate('店铺链接');">
						<div
							class="m_t10  cursor_ one_check_only_add w20 h20 border_d9dde5 b_radius5 f_l">
							<img class="cursor_ w20  one_check_ display_inline"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">店铺链接</div>
					</div>
					<div class="f_l short_out m_r40" onclick="linkDate('商品链接');">
						<div
							class="m_t10  cursor_ one_check_only_add w20 h20 border_d9dde5 b_radius5 f_l AddSpecified_link">
							<img class="cursor_ w20 one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">商品链接</div>
					</div>
					<div class="f_l short_out " onclick="linkDate('活动页链接');">
						<div
							class="m_t10  cursor_ one_check_only_add w20 h20 border_d9dde5 b_radius5 f_l activityLink">
							<img class="cursor_ w20 one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">活动页链接</div>
					</div>
				</div>
			</div>

			<!-- <input class="w570 h40 bgc_f4f6fa border_0 m_l70 p_l10 m_b30 text-center" placeholder="请输入短链接名称！" id="linkName"/> -->

				<div
					class="w570 short_in h40 font14 m_l70 text-center m_b30 c_384856 showBoxone">
					店铺链接直接点击确定按钮即可</div>
				<input
					class="w570 short_in h40 bgc_f4f6fa border_0 m_l70 p_l10 m_b30 display_none text-center showBoxtwo"
					placeholder="商品ID" id="commodityId"
					onkeyup="this.value=this.value.replace(/\D/g, '')"
					onblur="this.value=this.value.replace(/\D/g, '')" />
				<div id="activityLinkTxtBox">
					<textarea class="activityLinkTxt w570 showBoxthree" placeholder="在此输入活动网址"></textarea>
				</div>

			<div class="margin0_auto" style="width: 13vw; height: 3vw;">
				<div onclick="linkSubmit();"
					class="cursor_ m_r30 f_l save_short_url w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk b_radius5">
					确定</div>
				<div
					class="cursor_ f_l save_short_url w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk b_radius5 " id="hideTxtBoxQX">
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

	<!---------------短信测试弹窗--------------->
	<div
		class="w100_ h1370 rgba_000_5 position_absolute z_10 text_test_window top0 display_none">

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
				<input class="w540 p_l10 f_l h40 lh40 border0 bgc_f4f6fa"
					id="iphone" placeholder="测试号码不能超过5个，输入多个时用逗号隔开" /> <input
					type="hidden" id="smsSign" value="${ShopName }" />
				<div style="margin-top: 1vw;"
					class="w100 f_l m_l30 cursor_ h40 lh40 bgc_00a0e9 c_fff text-center b_radius5 cheShiFaSong">
					测试</div>
				<div class="clear"></div>
			</div>


			<div class="c_ff6363 font14 m_t30 p_l40 w760 lh25">
				<p>温馨提示：</p>
				<p>*短信每条70个字，若使用标签，以替换后的实际长度计算。</p>
				<p>*若出现短信拦截情况,是短信中某个词被手机软件检测为垃圾短信,只需多次尝试,去除拦截词即可，如果有链接地址，必须要前后加空格噢！若有其他问题,请直接联系客服.</p>
			</div>

		</div>

	</div>

	<!---------------测试短信弹窗--------------->
	<div
		class="w100_ h1370 rgba_000_5 position_absolute z_10 text_model_window top0 display_none">

		<div class="w1000 bgc_fff left400 p_b20 position_fixed top100">

			<div class="c_384856 text-center font18 h90 bgc_f1f3f7 p_t20">

				<div class="text-center m_b15">选择短信模板</div>

				<div class="m_l30 h50">
					<!-- <div
						class="text_model jinqiFestival out cursor_ bgc_fff w140 h50 lh50 text-center c_384856 f_l"
						onClick="choseTemplate('recentFestival',null)">近期节日</div> -->
					<div
						class="text_model tuijianchangjin out cursor_ bgc_fff w140 h50 lh50 text-center c_384856 f_l"
						onClick="choseTemplate('recommendedScene',null)">推荐场景</div>
					<div
						class="text_model out cursor_ bgc_e3e7f0 w140 h50 lh50 text-center c_384856 f_l"
						onClick="choseTemplate('sendHistory',null)">历史发送</div>
				</div>



			</div>


			<div class="in  tuijian">

				<div class="h40 p_l70 m_t40 m_b20">
					<div class="f_l m_r40">
						<div
							class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l"
							onClick="choseTemplate('recommendedScene','聚划算')">
							<img class="cursor_ w20 one_check_ "
								src="${ctx}/crm/images/black_check.png" id="jhs" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">聚划算</div>
					</div>
					<div class="f_l m_r40">
						<div
							class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l"
							onClick="choseTemplate('recommendedScene','上新')">
							<img class="cursor_ w20 one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" id="sx" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">上新</div>
					</div>
					<div class="f_l m_r40">
						<div
							class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l"
							onClick="choseTemplate('recommendedScene','周年庆')">

							<img class="cursor_ w20 one_check_ display_none"
								src="${ctx}/crm/images/black_check.png" id="znq" />
						</div>
						<div class="m_l10 f_l font14 c_384856 lh40">周年庆</div>
					</div>
				</div>

				<!--------详细数据-------->
				<div class="c_384856 p_l35 h430 overflow_auto m_r15">
					<table id="table_tuijianchangjin">
						<tr class="" id="table_tuijianchangjin_tr">
							<th class="w75">序号</th>
							<th class="w760">短信内容</th>
							<th class="w90">操作</th>
						</tr>

					</table>
				</div>

			</div>

			<div class="in display_none lishijilu">

				<div class="h20 p_l70 m_t20 m_b20"></div>



				<!--------详细数据-------->
				<div class="c_384856 p_l35 h430 overflow_auto m_r15">
					<table id="table_lishifasong">
						<tr class="" id="table_lishifasong_tr">
							<th class="w75">序号</th>
							<th class="w760">短信内容</th>
							<th class="w90">操作</th>
						</tr>
					</table>
				</div>

			</div>

			<div class="h42 margin0_auto" style="margin-top: 1vw; width: 13vw;">
				<div
					class="w100 close_template f_l m_r30 h40 lh40 border_00a0e9 tk cursor_ c_00a0e9 font16 text-center b_radius5">
					确定</div>
				<div
					class="w100 close_template f_l h40 lh40 border_00a0e9 tk cursor_ c_00a0e9 font16 text-center b_radius5">
					取消</div>
			</div>
		</div>

	</div>




	<!---------------筛选发送对象弹窗--------------->
	

		<div class="w1325 bgc_fff left400 h870 position_fixed top35 memberSelectionBox" id="memberSelectionBox">
			<h3 class="member-H3">会员筛选</h3>
			<div class="member-screen-box m_l55 clearfix">
				<div class="member-screen-boxLeft">
					<div class="member-grouping">
						<div class="member-grouping-left clearfix">
							<input type="hidden" value="" id="groupId">
							<label>会员分组 :</label>
							<div class="nice-select" name="nice-select">
							<c:if test="${groupName !=null }">
								<p>${groupName }</p>
							</c:if>
							<c:if test="${groupName ==null }">
								<p>所有分组</p>
							</c:if>
								<ul class="member-grouping-nav">
									<li class="removeStatus" value="0"  >所有分组</li>
									<c:forEach items="${groupList}" var="group">
										<li class="updateStatus" value="${group.groupId }"  >${group.groupName }
										</li>
									</c:forEach>
								</ul>
							</div>
							<a href="${ctx}/sellerGroup/findSellerGroup">添加分组</a>
						</div>
						
					</div>
					<input type="hidden" id="groupIdNum" value="${groupId}">
					<div class="member-grouping">
						<div class="member-grouping-left clearfix">
							<input type="hidden" value="0" id="customerSource">
							<label>订单来源 :</label>
							<div class="nice-select" name="nice-select">
								<p>全部</p>
								<ul class="member-grouping-nav">
									<li value="">全部</li>
									<li value="1">手机端</li>
									<li value="2">PC端</li>
									<li value="3">聚划算</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="member-grouping">
						<div class="member-grouping-left clearfix">
							<input type="hidden" value="" id="orderStatus">
							<label>订单状态 :</label>
							<div class="nice-select" name="nice-select">
								<p>全部</p>
								<ul class="member-grouping-nav">
									<li value="">全部</li>
									<li value="2">等待买家付款</li>
									<li value="4">买家已付款</li>
									<li value="5">卖家已发货</li>
									<li value="7">交易成功</li>

								</ul>
							</div>
						</div>
					</div>
					<div class="member-grouping">
						<div class="member-grouping-left clearfix">
							<input type="hidden" value="" id="lastestSend">
							<label>已发送过滤 :</label> 
							<div class="nice-select" name="nice-select">
								<p>不限</p>
								<ul class="member-grouping-nav">
									<li value="">不限</li>
									<li value="1">近1天</li>
									<li value="2">近2天</li>
									<li value="3">近3天</li>
									<li value="4">近4天</li>
									<li value="5">近5天</li>
									<li value="6">近6天</li>
									<li value="7">近7天</li>
									<li value="15">近15天</li>
									<li value="30">近30天</li>
								</ul>
							</div>
						</div>
						
					</div>
					
<!-- 					<div class="member-grouping"> -->
<!-- 						<div class="member-grouping-left clearfix"> -->
<!-- 							<input type="hidden" value="0" id=""> -->
<!-- 							<label>未交易时间:</label> -->
<!-- 							<div class="nice-select" name="nice-select"> -->
<!-- 								<p>不限</p> -->
<!-- 								<ul class="member-grouping-nav"> -->
<!-- 									<li value="0">不限</li> -->
<!-- 									<li value="1">近1天</li> -->
<!-- 									<li value="2">近2天</li> -->
<!-- 									<li value="3">近3天</li> -->
<!-- 									<li value="4">近4天</li> -->
<!-- 									<li value="5">近5天</li> -->
<!-- 									<li value="6">近6天</li> -->
<!-- 									<li value="7">近7天</li> -->
<!-- 									<li value="15">近15天</li> -->
<!-- 									<li value="30">近30天</li> -->
<!-- 								</ul> -->
<!-- 							</div> -->

<!-- 						</div> -->
						
<!-- 					</div> -->
					
					<div class="member-grouping">
						<div class="member-grouping-left clearfix">
							<input type="hidden" value="" id="itemIds">
							<label>指定商品 :</label>
							<div class="member-all-box clearfix">
								<div class="member-all clearfix">
									<i class="on memberAlli">
										<img src="${ctx}/crm/images/black_check.png" alt="">
									</i>
									<span>全部商品</span>	
								</div>
								<a  id="appoint_ItemId" class="member-alerta" href="javascript:;">自定义</a>	
							</div>

						</div>
						
					</div>
					
					<div class="member-grouping">
						<div class="member-grouping-left clearfix">
							<label>过滤条件 :</label>
							<div class="member-all-box clearfix">
								<div class="member-all clearfix">
									<input type="hidden" value="0" id="blackStatus">
									<i class="serch-tj" value="0">
										<img src="${ctx}/crm/images/black_check.png" alt="">
									</i>
									<span>黑名单</span>	
								</div>
<!-- 								<div class="member-all clearfix"> -->
<!-- 									<input type="hidden" value="0" id="evaluateStatus"> -->
<!-- 									<i class="serch-tj" value="1"> -->
<%-- 										<img src="${ctx}/crm/images/black_check.png" alt=""> --%>
<!-- 									</i> -->
<!-- 									<span>中差评</span>	 -->
<!-- 								</div> -->
							</div>

						</div>
						
					</div>
				
				</div>
				<div class="member-screen-boxRight">
					<div class="member-grouping">
						<div class="member-grouping-left clearfix member-grouping-right">
							<input type="hidden" value="" id="tradeTimeByDay">
							<label>最后交易时间 :</label>
							<div class="nice-select" name="nice-select">
								<p>不限</p>
								<ul class="member-grouping-nav">
									<li value="">不限</li>
									<li value="1">近1天</li>
									<li value="3">近3天</li>
									<li value="5">近5天</li>
									<li value="7">近7天</li>
									<li value="15">近15天</li>
									<li value="30">近30天</li>
								</ul>
							</div>
							<div class="custom-box">
								<div class="f_l position_relative"> 
									<input name="tradeStartTime" style="width: 9vw;"
										class="bgc_f4f6fa border0 p_l10 h45 m_r10 dianji tradeStartTime" type="text"
										id="tser05" readonly
										>
									<img style="width: 1vw;top:0.25125vw" class="position_absolute right20 top15"
										src="${ctx}/crm/images/date_copy.png"  />
								</div>
			
								<div class="f_l">至</div>
			
								<div class="f_l position_relative">
									<input name="tradeEndTime" style="width: 9vw;"
										class="bgc_f4f6fa border0 p_l10 h45 m_l10 dianji tradeEndTime" type="text"
										id="tser06" readonly
										>
															
									<img style="width: 1vw;top:0.25125vw;" class="position_absolute right10 top15"
										src="${ctx}/crm/images/date_copy.png" />
								</div>
							</div>
							<a class="select-a" href="javascript:;">自定义</a>
						</div>
						
					</div>
					<div class="member-grouping">
						<div class="member-grouping-left clearfix member-grouping-right">
							<input type="hidden" value="" id="tradeNumByTimes">
							<label>成功交易笔数 :</label>
							<div class="nice-select" name="nice-select">
								<p>不限</p>
								<ul class="member-grouping-nav">
									<li value="-1">不限</li>
									<li value="0">0次</li>
									<li value="1">1次</li>
									<li value="2">2次</li>
									<li value="3">3次以上</li>
								</ul>
							</div>
							<div class="custom-box">
								<input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onblur="zhengze('minTradeNum','maxTradeNum')" type="text" value="" id="minTradeNum">
								<span>~</span>
								<input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onblur="zhengze('minTradeNum','maxTradeNum')" type="text" value="" id="maxTradeNum">
							</div>
							<a class="select-a" href="javascript:;">自定义</a>
						</div>
						
					</div>
					<div class="member-grouping">
						<div class="member-grouping-left clearfix member-grouping-right">
							<input type="hidden" value="0" id="">
							<label>累计消费金额 :</label>
							<div class="nice-select" name="nice-select">
								<p>不限</p>
								<ul class="member-grouping-nav grouping-nav TradePriceNav">
									<li value="0">不限</li>
									<li value="1~100">1~100</li>
									<li value="100~200">100~200</li>
									<li value="200~300">200~300</li>
									<li value="300">300以上</li>
								</ul>
							</div>
							<div class="custom-box">
								<input onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onblur="zhengze('minTradePrice','maxTradePrice')" type="text" value="0" id="minTradePrice">
								<span>~</span>
								<input onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onblur="zhengze('minTradePrice','maxTradePrice')" type="text" value="0" id="maxTradePrice">
							</div>
							<a class="select-a zdya" href="javascript:;">自定义</a>
						</div>
						
					</div>
					<div class="member-grouping">
						<div class="member-grouping-left clearfix member-grouping-right">
							<input type="hidden" value="0">
							<label>平均客单价 :</label>
							<div class="nice-select" name="nice-select">
								<p>不限</p>
								<ul class="member-grouping-nav grouping-nav AvgPriceNav">
									<li value="0">不限</li>
									<li value="1~100">1~100</li>
									<li value="100~200">100~200</li>
									<li value="200~300">200~300</li>
									<li value="300">300以上</li>
								</ul>
							</div>
							<div class="custom-box">
								<input onblur="zhengze('minAvgPrice','maxAvgPrice')" type="text" value="0" id="minAvgPrice">
								<span>~</span>
								<input onblur="zhengze('minAvgPrice','maxAvgPrice')" type="text" value="0" id="maxAvgPrice">
							</div>
							<a class="select-a zdyb" href="javascript:;">自定义</a>
						</div>
						
					</div>
					
<!-- 					<div class="member-grouping"> -->
<!-- 						<div class="member-grouping-left clearfix member-grouping-right"> -->
<!-- 							<input type="hidden" value=""> -->
<!-- 							<label>卖家标记：</label> -->
<!-- 							<div class="member-all-box clearfix"> -->
<!-- 								<div class=" clearfix member-grouping-rSpan" style="margin-right:1vw;"> -->
<!-- 									<input type="hidden" value=""> -->
<!-- 									<i class="shield-select" value="0"> -->
<!-- 										<img src="${ctx}/crm/images/black_check.png" alt=""> -->
<!-- 									</i> -->
<!-- 									<span >不屏蔽卖家标记</span>	 -->
<!-- 								</div> -->
<!-- 								<div class="clearfix member-grouping-rSpan" > -->
<!-- 									<input type="hidden" value=""> -->
<!-- 									<i class="shield-select" value="1"> -->
<!-- 										<img src="${ctx}/crm/images/black_check.png" alt=""> -->
<!-- 									</i> -->
<!-- 									<span>屏蔽卖家标记</span> -->
<!-- 									<div class="color-signBox"> -->
<!-- 										<div class="color-signDiv"> -->
<!-- 											<i> -->
<!-- 												<img src="${ctx}/crm/images/black_check.png" alt=""> -->
<!-- 											</i> -->
<!-- 											<span> -->
<!-- 												<img src="${ctx}/crm/images/redq.png" alt=""> -->
<!-- 											</span> -->
<!-- 										</div>	 -->
<!-- 										<div class="color-signDiv"> -->
<!-- 											<i> -->
<!-- 												<img src="${ctx}/crm/images/black_check.png" alt=""> -->
<!-- 											</i> -->
<!-- 											<span> -->
<!-- 												<img src="${ctx}/crm/images/yellowq.png" alt=""> -->
<!-- 											</span> -->
<!-- 										</div> -->
<!-- 										<div class="color-signDiv"> -->
<!-- 											<i> -->
<!-- 												<img src="${ctx}/crm/images/black_check.png" alt=""> -->
<!-- 											</i> -->
<!-- 											<span> -->
<!-- 												<img src="${ctx}/crm/images/greenq.png" alt=""> -->
<!-- 											</span> -->
<!-- 										</div> -->
<!-- 										<div class="color-signDiv"> -->
<!-- 											<i> -->
<!-- 												<img src="${ctx}/crm/images/black_check.png" alt=""> -->
<!-- 											</i> -->
<!-- 											<span> -->
<!-- 												<img src="${ctx}/crm/images/greenq.png" alt=""> -->
<!-- 											</span> -->
<!-- 										</div> -->
<!-- 										<div class="color-signDiv"> -->
<!-- 											<i> -->
<!-- 												<img src="${ctx}/crm/images/black_check.png" alt=""> -->
<!-- 											</i> -->
<!-- 											<span> -->
<!-- 												<img src="${ctx}/crm/images/violetq.png" alt=""> -->
<!-- 											</span> -->
<!-- 										</div>	 -->
<!-- 									</div>	 -->
<!-- 								</div> -->
<!-- 							</div> -->

<!-- 						</div> -->
						
<!-- 					</div> -->
					
					<div class="member-grouping">
						<div class="member-grouping-left clearfix member-grouping-right">
							<label>地区筛选 :</label>
							<div class="member-all-box clearfix">
								<div class="member-all clearfix">
									
									<i class="cityscreen on">
										<img src="${ctx}/crm/images/black_check.png" alt="">
									</i>
									<span>不限</span>	
								</div>
								<input type="hidden" id="region" value="">
								<a class="cityalerta" id="selectArea" href="javascript:;">自定义</a>	
							</div>

						</div>
						
					</div>
				</div>		
			</div>
			<div class="member-btn clearfix">
				
				<div class="member-download-btn" onclick="download()">下载数据</div>
				<div class="member-search-btn">搜索</div>
			</div>
			<p class="member-title">温馨提示：筛选出的会员数据只显示前100条，如需查看所有请点击下载数据。</p>
			<div class="c_384856 text-center h380 overflow_auto member-table">
				<table class="m_l55" id="shaixuanduixiang">
					<tr id="shaixuanduixiang_tr">
						<%-- <th class="position_relative z_1 w65 p_l10 ">
							<div
								class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute"
								style="left: 0.4vw;">
								<img class="cursor_ one_check_ display_none w20"
									src="${ctx}/crm/images/black_check.png" />
							</div>
							<div class="p_l20">全选</div>
						</th> --%>
						<th class="w245 wl621">序号</th>
						<th class="w130 wl950">客户昵称</th>
						<th class="w250 wl788">会员等级</th>
						<th class="w120 wl102">最后交易时间</th>
						<th class="w120 wl104">成功交易笔数<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="成功交易笔数按照交易成功订单计算，刚刚下单的新客户由于交易还在进行中所以此处显示为0。"/></th>
						<th class="w175 wl111">累计消费金额<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="成功交易订单金额总和"/></th>
						<th class="w170 wl108">平均客单价<img style="width:0.8vw;"src="${ctx}/crm/images/bubble_2.png" title="平均客单价=累计消费金额/成功交易笔数"/></th>

					</tr>

					<c:if test="${memberInfoList!=null }">
						<c:forEach items="${memberInfoList }" var="member" varStatus="num">
							<c:if test="${num.index <100 }">
								<tr>
									<%-- <td class='position_relative p_l10 z_1'>
								<div style="top: 0.8vw;margin-left: 0.9vw;" class='cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top0' onClick='checkBox()'>
								<img  class='cursor_ one_check_ display_none' src='${ctx}/crm/images/black_check.png' /></div>
								<div><input type='hidden' value='${member.id }'></div></td> --%>
									<td><input class="buyerNick" type="hidden"
										value="${member.buyerNick }">${member.buyerNick }</td>
									<td>店铺会员</td>
									<td><fmt:formatDate value="${member.lastTradeTimeUTC }"
											type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<c:if test="${member.tradeCount ==null or member.tradeCount == '' }">
										<td>0</td>
									</c:if>
									<c:if test="${member.tradeCount !=null }">
										<td>${member.tradeCount }</td>
									</c:if>
									
									<c:if test="${member.tradeAmount ==null or member.tradeAmount == '' }">
										<td>0</td>
									</c:if>
									<c:if test="${member.tradeAmount !=null }">
										<td>${member.tradeAmount}</td>
									</c:if>
									<c:if test="${member.avgPrice !=null }">
										<td>${member.avgPrice}</td>
									</c:if>
									<c:if test="${member.avgPrice ==null }">
										<td>0</td>
									</c:if>
								</tr>
							</c:if>
						</c:forEach>
					</c:if>
				</table>
				<img class="jindu" src="${ctx }/crm/images/yu-jiazai.gif" style="display: none">
			</div>
			<!--------分页-------->
			<div class="clearfix" style="width:40vw; margin:0 auto;">
				<div style="margin-top: 2vw;"
				class="cursor_ w250 margin0_auto h45 lh45 text-center font14 border_00a0e9 b_radius5 c_00a0e9 tk"
				onclick="submitInfo()" id="submitInfo">已选择<span></span>个客户,确定发送</div>
				<div style="margin-top: 2vw;"
				class="cursor_ w250 margin0_auto h45 lh45 text-center font14 border_00a0e9 b_radius5 c_00a0e9 tk closeInfo"
				>取消发送</div>
			</div>
			

		</div>

	

	<!--选择商品弹出框start-->
	
<!-- 	<div class="rgba_000_5 w1920 h100_ ChoiceSpecified  display_none" id="memberAlart"> -->
<!-- 		<div class="w1000 h580 p_b33 bgc_fff m_t100 " -->
<!-- 			style="margin-left: 24.625vw;"> -->
<!-- 			<div class="w936 h546 margin0_auto bgc_fff"> -->
<!-- 				<p class="text-center p_t20 p_b40 font16">选择商品</p> -->
<!-- 				<input id="appoint_ItemId" type="hidden" /> -->
<!-- 				<form class="font14" action="#"> -->
<!-- 					<p class="f_l"> -->
<!-- 						商品ID:<input id="itemId_input" -->
<!-- 							class="h50 w240 border0 outline_none b_radius5 m_l15 m_r20 bgc_f4f6fa" /> -->
<!-- 					</p> -->
<!-- 					<p class="f_l"> -->
<!-- 						关键字:<input id="itemName_input" -->
<!-- 							class="h50 w240 border0 outline_none b_radius5 m_l15 m_r18 bgc_f4f6fa" /> -->
<!-- 					</p> -->
<!-- 					<p class="f_l"> -->
<!-- 					<div id="putaway_div" -->
<!-- 						class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 zhixianshishangjia"> -->
<!-- 					</div> -->
<!-- 					<input id="status_input" type="hidden" value="2" /> -->
<!-- 					<div class="m_l10 f_l font14 c_384856 lh50"> -->
<!-- 						只显示上架<input onclick="findItemByData(this);" -->
<!-- 							class="h40 w80 border0 outline_none b_radius5 m_l35 bgc_00a0e9 c_fff cursor_" -->
<!-- 							type="button" value="搜索" /> -->
<!-- 					</div> -->
<!-- 					</p> -->
<!-- 				</form> -->
<!-- 				<div class="clear"></div> -->
<!-- 				<div class="h300 overflow_auto"> -->
<!-- 					<table border="0" class="font14 m_t13 item_table"> -->
<!-- 						<tr class="bgc_e3e7f0 item_tr"> -->
<!-- 							<th class="w80 h60 text-center">全选</th> -->
<!-- 			<th class="w80 h60 text-center" style="width:8vw;"> -->
<!--              <div class="f_l 1check_box_div "> -->
<!-- 					<div -->
<!-- 						class="cursor_ m_t10 1check_box_div_1 w20 h20 border_d9dde5 b_radius5 f_l VIPGXK fuxuankuang" style='margin-left:1.4vw;'> -->
<!-- 					</div> -->
<!-- 					<div class="m_l20 cursor_ f_l font18 c_384856" style="margin-top:0.3vw;"> 全选</div> -->
<!-- 				</div>                            -->
<!--              </th> -->
<!-- 							<th style="width: 8vw;" class=" h60 text-center">图片</th> -->
<!-- 							<th style="width: 35vw;" class=" h60 text-center">宝贝名称</th> -->
<!-- 							<th style="width: 8vw;" class=" h60 text-center">金额</th> -->
<!-- 						</tr> -->
<!-- 					</table> -->
<!-- 				</div> -->

<!-- 				确定保存start -->

<!-- 				<div class="w936 h42 m_t50 margin0_auto"> -->
<!-- 					<div class="w214 margin0_auto"> -->
<!-- 						<div onclick="addItemId();" -->
<!-- 							class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ SpecifiedOut  Qita"> -->
<!-- 							确定</div> -->
<!-- 						<div -->
<!-- 							class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ SpecifiedOut selectaqx"> -->
<!-- 							取消</div> -->
<!-- 					</div> -->
<!-- 				</div> -->

<!-- 				确定保存end -->
<!-- 			</div> -->


<!-- 		</div> -->
<!-- 	</div> -->
	
	<!--选择商品弹出框end-->

	<!--选择所在地弹出框start-->
	<div class="rgba_000_5 w1920 h100_ display_none ChoiceArea"
		style="z-index: 40;" id="cityAlert">
		<div class="w750 p_b33 bgc_fff m_t100 m_l500 fasf">
			<div class="h50 p_t20 text-center font16">选择所在地区</div>
			<!-- <div class="h20 p_t20 text-center font16 display_none"
			id="payAttention" style="color: red">*地区最多选择三个!</div> -->

			<div class="font14 c_384856 p_l50 place_check">
				<ul class="h50">

					<li class="f_l w90 li_">
						<div
							class="cursor_ m_t15 all_check_555 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							全选 <input type="hidden" value="全选">
						</div>
					</li>
				</ul>
				<!--华东-->
				<ul class="h50 gangaotai_ul_1">
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							上海 <input type="hidden" value="上海">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							浙江<input type="hidden" value="浙江省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							江苏<input type="hidden" value="江苏省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							安徽<input type="hidden" value="安徽省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							江西<input type="hidden" value="江西省">
						</div>
					</li>
				</ul>

				<!--华北-->
				<ul class="h50 gangaotai_ul_1">
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							北京<input type="hidden" value="北京">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							天津<input type="hidden" value="天津市">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							河北<input type="hidden" value="河北省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							山西<input type="hidden" value="山西省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							山东<input type="hidden" value="山东省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							内蒙古<input type="hidden" value="内蒙古自治区">
						</div>
					</li>
				</ul>

				<!--华中-->
				<ul class="h50 gangaotai_ul_1">
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							湖北<input type="hidden" value="湖北省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							湖南<input type="hidden" value="湖南省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							河南<input type="hidden" value="河南省">
						</div>
					</li>
				</ul>

				<!--华南-->
				<ul class="h50 gangaotai_ul_1">
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							广东<input type="hidden" value="广东省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							广西<input type="hidden" value="广西壮族自治区">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							福建<input type="hidden" value="福建省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							海南<input type="hidden" value="海南省">
						</div>
					</li>
				</ul>

				<!--东北-->
				<ul class="h50 gangaotai_ul_1">
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							辽宁<input type="hidden" value="辽宁省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							吉林<input type="hidden" value="吉林省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							黑龙江<input type="hidden" value="黑龙江省">
						</div>
					</li>
				</ul>

				<!--西北-->
				<ul class="h50 gangaotai_ul_1">
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							陕西<input type="hidden" value="陕西省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							新疆<input type="hidden" value="新疆维吾尔自治区">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							青海<input type="hidden" value="青海省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							宁夏<input type="hidden" value="宁夏回族自治区">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							甘肃<input type="hidden" value="甘肃省">
						</div>
					</li>
				</ul>

				<!--西南-->
				<ul class="h50 gangaotai_ul_1">
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							四川<input type="hidden" value="四川省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							云南<input type="hidden" value="云南省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							西藏<input type="hidden" value="西藏自治区">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							贵州<input type="hidden" value="贵州省">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							重庆<input type="hidden" value="重庆">
						</div>
					</li>
				</ul>

				<!--港澳台-->
				<ul class="h50 gangaotai_ul_1">
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							香港<input type="hidden" value="香港特别行政区">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							澳门<input type="hidden" value="澳门特别行政区">
						</div>
					</li>
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							台湾<input type="hidden" value="台湾省">
						</div>
					</li>
				</ul>

				<!--海外-->
				<ul class="h50 gangaotai_ul_1">
					<li class="f_l 1check_box w90 li_">
						<div
							class="cursor_ m_t15 1check_box_2 w20 h20 border_d9dde5 b_radius5 f_l">
						</div>
						<div class="m_l10 cursor_ f_l font14 h50 lh50 c_384856">
							海外<input type="hidden" value="海外">
						</div>
					</li>
				</ul>

			</div>
			<div class="h40 margin0_auto w225">
				<div
					class="w100 h45 lh45  b_radius5 bgc_00a0e9 c_fff text-center m_l10 f_l cursor_ save_area">
					保存</div>
				<div
					class="w100 h43 lh45 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ cancel_area">
					取消</div>
			</div>
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
	<input type="hidden" id="urlVal" value="${ctx}">
	<div id="xuanzeshangpinBox">
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
					<a class="qx" id="qx" href="javascript:;">取消</a>
				</div>
			</div>
			
		</div>
		<input type="hidden" id="SPID" value="">   
        <input type="hidden" id="GJZ" value="">
        <input type="hidden" id="SHOWSJ" value="">
        <input type="hidden" id="SHOWDSJ" value="">
        <input type="hidden" id="GLSP" value="">
<!-- 	<div class="markBg"></div> -->
	<!--选择所在地弹出框end-->

	<!-- 引入商品jsp -->
	<%@ include file="/WEB-INF/views/crms/header/itemUtils.jsp"%>
</body>
<script>
	$(function() {
		/* //获取用户所有分组信息
		 $.ajax({
		     type: "get",
		     url: "${ctx}/sellerGroup/getAllGroup",
		     dataType: "json",
		     success: function(data){
		              var html = ''; 
		              $.each(data, function(Index, group){
		                   	html += "<li value="+group.groupId+">"+group.groupName+"</li>";
		                 });
		                 $('#customerLevel').append(html);
		              }
		 }); */
		$("#khdj").val("${groupId}");
		//页面一加载 隐藏域 短信签名赋值
		$("#smsChannel").val("【淘宝】");
		//页面一加载 隐藏域 发送时间赋值
		$("#sendStatus").val(1);

// 		$("#selectArea").click(function() {
// 			$(".ChoiceArea").show();
// 		});

		$(".1check_box_2").click(function() {
			$(this).toggleClass("bgc_check_blue");
		});

		$(".all_check_555").click(function() {
// 			$(this).toggleClass("bgc_check_blue");
// 			$(".place_check").children().find(".1check_box_2")
// 					.toggleClass("bgc_check_blue");
			if($(this).hasClass('bgc_check_blue')){
				$(this).parents('ul').siblings('ul').find('.1check_box_2').removeClass('bgc_check_blue');
				$(this).removeClass('bgc_check_blue');
			}else{
				$(this).parents('ul').siblings('ul').find('.1check_box_2').addClass('bgc_check_blue');
				$(this).addClass('bgc_check_blue');
			}
		});

		$(".save_area").click(function(){
			var area = "";
			var getprovince = $(".gangaotai_ul_1").children(".li_")
					.children(".bgc_check_blue").next().children(
							'input');
			for (var i = 0; i < getprovince.length; i++) {
				area += getprovince.eq(i).val() + ",";
			}
			area = area.substring(0, area.length - 1);
			if (area != "") {
				$("#selectArea").html(
						"已选择" + getprovince.length + "个地区");
				
			} else {
				$("#selectArea").html("自定义");
				$('.cityscreen').addClass('on');
				$('.cityscreen').siblings('input').val('1');
			}
			$("#copyArea").val(area);
			$(".ChoiceArea").hide();
			$("#region").val(area);
			$('#memberSelectionBox').show();
			$('.markBg').show();
		});

		$(".cancel_area").click(function() {
			$(".gangaotai_ul_1").children(".li_").children(
					".bgc_check_blue").removeClass("bgc_check_blue");
			$(".all_check_555").removeClass("bgc_check_blue");
			$("#selectArea").html("自定义");
			$('.cityscreen').addClass('on');
			$('.cityscreen').siblings('input').val('1');
			$("#copyArea").val("");
			$(".ChoiceArea").hide();
			$("#region").val('');
			$('#memberSelectionBox').show();
			$('.markBg').show();
		});
			

	})

	//客户等级
	$("#customerLevel li").click(function() {
		$("#khdj").val($(this).val() == 0 ? null : $(this).val());
	})
	//客户来源
	$("#customerSource li").click(function() {
		$("#khly").val($(this).val() == 0 ? null : $(this).val());
	})

	//屏蔽选项
	//1.短信黑名单
	function blackListX() {
		var show = $(".dxhmd").children("img").css("display");
		if (show == 'inline') {
			return true;
		} else {
			return false;
		}
	}
	//2.给过我中差评的客户
	function evaluationX() {
		var show = $(".ggwzcpkh").children("img").css("display");
		if (show == 'inline') {
			return true;
		} else {
			return false;
		}
	}
	//3.退过款的客户
	function refundX() {
		var show = $(".tgkdkh").children("img").css("display");
		if (show == 'inline') {
			return true;
		} else {
			return false;
		}
	}
	//4.屏蔽近
	function sheildRecentX() {
		var show = $(".pbj").children("img").css("display");
		if (show == 'inline') {
			return true;
		} else {
			return false;
		}
	}
	//屏蔽天数
	$("#sheildDate li").click(function() {
		$("#sheildDateInput").val($(this).html());
	})

	//客户身份
	function memberIdentityX() {
		var memberIdentity = "";
		var members = $(".group_check");
		for (var i = 0; i < members.length; i++) {
			if (members.eq(i).children("img").css("display") == "inline") {
				if (memberIdentity != "") {
					memberIdentity = memberIdentity + ","
							+ members.eq(i).next('div').text();
				} else {
					memberIdentity = members.eq(i).next('div').text();
				}
			}

		}
		return memberIdentity;
	}

	//营销中心筛选发送对象
	//营销中心筛选发送对象
	function searchAll() {
		
		$("#sendMember").val('');
		$('#shaixuanduixiang_tr').siblings().remove();
		$(".jindu").show();
		var sID=$('#groupIdNum').attr('value');
		//交易时间开始时间
		var sTradeStartTime=$('.tradeStartTime').val();
		//交易时间结束时间
		var sTradeEndTime=$('.tradeEndTime').val();
		//交易时间近几天
		var sTradeTimeByDay=$('#tradeTimeByDay').val();
		//交易次数最小次数
		var sMinTradeNum=$('#minTradeNum').val();
		//交易次数最大次数
		var sMaxTradeNum=$('#maxTradeNum').val();
		//交易次数近几次
		var sTradeNumByTimes=$('#tradeNumByTimes').val();
		//累计消费金额最小金额
		var nMinTradePrice=Number($('#minTradePrice').val());
		//累计消费金额最大金额
		var nMaxTradePrice=Number($('#maxTradePrice').val());

		//平均客单价最小客单金额
		var nMinAvgPrice=Number($('#minAvgPrice').val());
		//平均客单价最大客单金额
		var nMaxAvgPrice=Number($('#maxAvgPrice').val());
		//会员分组
		var sGroupId=$('#groupId').val();
		//订单来源
		var nCustomerSource=Number($('#customerSource').val());
		//订单状态
		var sOrderStatus=$('#orderStatus').val();
		//已发送过滤
		var sLastestSend=$('#lastestSend').val();
		//指定商品
		var sItemIds=$('#itemIds').val();
		//过滤条件黑名单
		var sBlackStatus=$('#blackStatus').val();
		//过滤条件中差评
		var sEvaluateStatus=$('#evaluateStatus').val();
		//地区筛选
		var sRegion=$('#region').val();
		
		if(sID>0){
			sGroupId=sID;
		}
		
		if(sGroupId==0){
			sGroupId='';
		}
		if(sOrderStatus==0){
			sOrderStatus='';
		}
		if(sLastestSend==0){
			sLastestSend='';
		}
		if(sItemIds==0){
			sItemIds='';
		}
		if(sRegion==0){
			sRegion='';
		}
		if(sTradeTimeByDay==0){
			sTradeTimeByDay='';
		}
		

		if(sMinTradeNum!=''){

			sMinTradeNum=parseInt($('#minTradeNum').val());
			

		}
		if(sMaxTradeNum!=''){
			sMaxTradeNum=parseInt($('#maxTradeNum').val());

		
		}
		if(sTradeNumByTimes!=''){
			sTradeNumByTimes=parseInt($('#tradeNumByTimes').val());
	
		}
		if(sTradeNumByTimes=='-1'){
			sTradeNumByTimes='';
		}

		$.ajax({
			url:"${ctx}/memberInfoController/queryMemberInfo",
			data:{
				groupId:sGroupId,
				customerSource:nCustomerSource,
				orderStatus:sOrderStatus,
				lastestSend:sLastestSend,
				itemIds:sItemIds,
				blackStatus:sBlackStatus,
				evaluateStatus:sEvaluateStatus,
				region:sRegion,
				tradeStartTime:sTradeStartTime,
				tradeEndTime:sTradeEndTime,
				tradeTimeByDay:sTradeTimeByDay,
				minTradeNum:sMinTradeNum,
				maxTradeNum:sMaxTradeNum,
				tradeNumByTimes:sTradeNumByTimes,
				minTradePrice:nMinTradePrice,
				maxTradePrice:nMaxTradePrice,
				minAvgPrice:nMinAvgPrice,
				maxAvgPrice:nMaxAvgPrice
			},
			type: "post",
			dataType:'JSON',
			success:function(data){
				if(data.rc>100){
					alert(data.msg);
				}else{
					var aContent='';
					if(data.mList.length==0){
						aContent = "<tr><td class='position_relative p_l10 z_1' align='center' colspan='7'>暂时没有相关数据!</td></tr>";
						$('#shaixuanduixiang').append(aContent);
						$('#submitInfo span').text('0');
					}else{
						$.each(data.mList,function(index,myContent){
							aContent = "<tr>"
								+"<td>"+(index+1)+"</td>"
								+"<td><input class='buyerNick' type='hidden' value='"+myContent.buyerNick+"'>"+myContent.buyerNick+"</td>"
								+"<td>"+myContent.occupation+"</td>"
								+"<td>"+myContent.lastTradeTimeUTC+"</td>"
								+"<td>"+myContent.tradeCount+"</td>"
								+"<td>"+myContent.tradeAmount+"</td>"
								+"<td>"+myContent.avgPrice+"</td></tr>";
							$('#shaixuanduixiang').append(aContent);
						});
						$('#submitInfo span').text(data.count);
						$('#queryKey').val(data.queryKey);
					}
				}
				
				$(".jindu").hide();
				
				
			}
		});
		
	}

	//选择与去取消选择
	$(document).on('click', '.one_check', function() {
		$(this).children(".one_check_").toggle();
	})

	//获取选中的发送对象的编号，放到一个input里
	function submitInfo() {
		/* var data;
		var len = 0;
		var trSelect = $(".one_check");
		var memberNum =$("#sendMember").val();
		var memberlength = memberNum.split(",");
		len = memberlength.length; */
		var len =$("#submitInfo span").text();

		$("#memberSelectionBox").hide();
		$('.markBg').hide();
		if(len>0){
			$("#memberNum").html(len);
			$('#sendMember').val('10');
		}else{
// 			var member="${memberNum}"
// 			if(member!=null){
// 				$("#memberNum").html(""+len"个客户，请重新选择！");	
// 			}else{
// 				$("#memberNum").html("0个客户，请重新选择！");
// 			}
			$("#memberNum").html(len);
		}		
		$("#memberCount").show();
		$("#sendButton").hide();

		//启用滚动条
		  $(document.body).css({
		  "overflow-x":"auto",
		  "overflow-y":"auto"
		  });

	}

	//会员分组=>营销中心页面一加载获取客户id
	$(function() {
		var mIds = $("#sendMember").val();

		var arrIds = new Array();
		var len = "${memberNum}";
		if (len =='-1') {
			
// 			$("#memberNum").html('0');
// 			$("#memberCount").show();
// 			$("#sendButton").hide();
// 			$(".jindu").hide(); 
			$('#sendButton').text('已选择0个用户');
			$('.nice-select').each(function(index){
				if(index>0){
					if(index!=3){
						$('.nice-select').eq(index).addClass('disabled');
					}
					
				}
			});
			$('.select-a').each(function(index){
				
					$('.select-a').eq(index).addClass('disabled');
				
			})
			$('#appoint_ItemId').addClass('disabled');
			$('#selectArea').addClass('disabled');
			
			$('#submitInfo span').text(0);
		}else if(len>0){
// 			$("#memberNum").html(len);
// 			$("#memberCount").show();
// 			$("#sendButton").hide();
// 			$(".jindu").hide(); 
			$('#sendButton').text('已选择'+len+'个用户');
			$('.nice-select').each(function(index){
				if(index>0){
					if(index!=3){
						$('.nice-select').eq(index).addClass('disabled');
					}
					
				}
			});
			$('.select-a').each(function(index){
				
					$('.select-a').eq(index).addClass('disabled');
				
			})
			$('#appoint_ItemId').addClass('disabled');
			$('#selectArea').addClass('disabled');
			
			$('#submitInfo span').text(0);
			$('#submitInfo span').text(len);
		}
	})
	
	$(".removeStatus").click(function() {
		$('.nice-select').each(function(index){
			if(index>0){
				$('.nice-select').eq(index).removeClass('disabled');
			}
		});
	})
	
// 	$(".updateStatus").click(function() {
// 		$('.nice-select').each(function(index){
// 			if(index>0){
// 				$('.nice-select').eq(index).addClass('disabled');
// 			}
// 		});
// 	})
	
	$(".qianming_done").click(function() {
		$("#DXQM").show();
	})

	//短信签名
	function addConsignee(button) {

		if (button == null || button == "") {
			$("#DXQM").show();
		} else {
			$("#DXQM").hide();
		}

		//获取短信内容
		var messageContent = $(".text_area").val() + "";

		//获取输入框的值
		var messageConsignee = $("#messageConsignee").val();
		if (messageConsignee != null && messageConsignee != "") {
			messageConsignee = "【" + messageConsignee + "】";
		}
		//获取拷贝框的值
		var copyConsignee = $("#copyConsignee").val();
		if (copyConsignee == null || copyConsignee == "") {
			copyConsignee = messageConsignee;
		}
		if (button == null) {
			//替换
			messageContent = messageConsignee
					+ messageContent.replace(copyConsignee, "").replace("【天猫】",
							"").replace("【京东】", "").replace("【淘宝】", "");
			//备份
			$("#copyConsignee").val(messageConsignee);

			//短信签名
			$("#smsChannel").val(messageConsignee);

		} else {
			messageContent = button
					+ messageContent.replace("【天猫】", "").replace("【京东】", "")
							.replace("【淘宝】", "").replace(copyConsignee, "");
			//短信签名
			$("#smsChannel").val(button);
		}
		//展示
		$(".text_area").val(messageContent);
		$(".text_area_copy").val(messageContent.replace("退订回N", "") + "退订回N");
		//重新计算长度		
		addLength();
	}

	function addReceiver(value) {

		var content = document.getElementById("textcontent");
		getCursortPosition(content);
		startContent = startContent.replace(value, "");
		endContent = endContent.replace(value, "")
		$(".text_area").val(startContent + value + endContent);
		$(".text_area_copy").val(startContent + value + endContent + "退订回N");
		//重新计算长度		
		addLength();
	}
	//计算长度
	//判断短信输入框内容长度，修改计费条数
	function addLength() {

		var content = $(".text_area_copy").val();

		var len = content.length;
		if (len > 70) {
			$("#contePrice").text(Math.ceil(len / 67));
		} else {
			if (len == 0) {
				$("#contePrice").text(0);
			} else {
				$("#contePrice").text(1);
			}
		}
		$("#inputNum").text(len);
	}

	//修改短信内容==keyUp
	function editMessage() {
		var content = $(".text_area").val();
		$(".text_area_copy").val(content.replace("退订回N", "") + "退订回N");

		//重新计算出长度
		var len = content.length;
		if (len >= 1000) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容长度不能大于1000字")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 2000)
		}
		addLength();
	}
	//修改短信内容==onblur
	function leaveEditMessage() {
		var content = $(".text_area").val();
		$(".text_area").val(content);
		$(".text_area_copy").val(content.replace("退订回N", "") + "退订回N");

		//重新计算出长度
		var len = content.length;
		if (len >= 1000) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容长度不能大于1000字")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		}
		addLength();
	}

	//使用短信模板
	//将先中模板的内容添加到内容框，并修改输入文字，修改计费条数
	function addContent(content) {
		var consignee = $("#smsChannel").val();
		$(".text_model_window").hide();

		//默认选中近期节日
		/* $(".text_model").addClass("bgc_e3e7f0").removeClass("bgc_fff");
		$(".jinqiFestival").removeClass("bgc_e3e7f0").addClass("bgc_fff"); */

		//默认选中推荐场景
		$(".text_model").addClass("bgc_e3e7f0").removeClass("bgc_fff");
		$(".tuijianchangjin").removeClass("bgc_e3e7f0").addClass("bgc_fff");

		//清空数据
		//$('#table_jinqijieri_tr').siblings().remove();
		$('#table_tuijianchangjin_tr').siblings().remove();
		$('#table_lishifasong_tr').siblings().remove();
		//$(".jqjr").show();
		$(".tuijian").show();
		$(".lishijilu").hide();

		content = consignee + content;
		$(".text_area").val(content);
		$(".text_area_copy").val(content + "退订回N");
		addLength();
	};

	//选择模板
	function choseTemplate(item, type) {

		//清空数据
		$('#table_jinqijieri_tr').siblings().remove();
		$('#table_tuijianchangjin_tr').siblings().remove();
		$('#table_lishifasong_tr').siblings().remove();
		//item主题,type类型
		var item = item;
		var type = type;
		if (item == 'recommendedScene') {

			$
					.ajax({
						url : "${ctx}/SmsTemplate/findSmsTemplate",
						data : {
							"item" : item,
							"type" : type
						},
						type : "post",
						success : function(data) {
							var smsTemplateList = data.smsTemplateList;
							var appendTemp = "";
							if (smsTemplateList == null
									|| smsTemplateList == "undefined"
									|| smsTemplateList == '') {
								appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
								$('#table_tuijianchangjin').append(appendTemp);
							} else {
								$
										.each(
												smsTemplateList,
												function(index, smsTemplate) {
													appendTemp = "<tr class=''><td class='w75 text-center'>"
															+ index
															+ "</td><td class='w760 text-center'>"
															+ smsTemplate.content
															+ "</td><td class='w90 text-center'>"
															+ "<div class='border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto cursor_ b_radius5'"
															+ "onClick=\"addContent("
															+ "'"
															+ smsTemplate.content
															+ "'"
															+ ")\">"
															+ "使用</div></td></tr>";

													$('#table_tuijianchangjin')
															.append(appendTemp);
												});
							}
						},
						dataType : 'JSON'
					});

		} else if (item == 'sendHistory') {

			$
					.ajax({
						url : "${ctx}/historyTemplate/findAllList",
						type : "post",
						success : function(data) {
							var smsHistoryTemplates = data;
							var appendTemp = "";

							if (smsHistoryTemplates == null
									|| smsHistoryTemplates == "undefined"
									|| smsHistoryTemplates == '') {
								appendTemp = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
								$('#table_lishifasong').append(appendTemp);
							} else {
								$
										.each(
												smsHistoryTemplates,
												function(index,
														smsHistoryTemplate) {
													appendTemp = "<tr class=''><td class='w75 text-center'>"
															+ index
															+ "</td><td class='w760 text-center'>"
															+ smsHistoryTemplate.content
															+ "</td><td class='w90 text-center'>"
															+ "<div class='border_00a0e9 c_00a0e9 tk w70 h40 lh40 margin0_auto cursor_ b_radius5'"
															+ "onClick=\"addContent("
															+ "'"
															+ smsHistoryTemplate.content
															+ "'"
															+ ")\">"
															+ "使用</div></td></tr>";

													$('#table_lishifasong')
															.append(appendTemp);
												});
							}
						},
						dataType : 'JSON'
					});
		}

	}

	//获取生成链接的类型
	function linkDate(link) {
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
	function linkSubmit() {
		var linktype = $("#linkType").val();
		var linkName = $("#linkName").val();

		var commodityId = document.getElementById("commodityId").value;
		var activityUrl= $('.activityLinkTxt').val();
		if (linktype == "") {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请选择链接类型")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		} else {
			var url = "${ctx}/GenerateLinkTo/generateLink";
			$.post(url, {
				"linkType" : linktype,
				"linkName" : linkName,
				"commodityId" : commodityId,
				'activityUrl':activityUrl
			}, function(data) {
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
				if (data.message) {
					if (data.link != null) {
						if(startContent==null||startContent==""){
							$(".text_area").val(
									startContent + endContent +data.link );
						}else{
							$(".text_area").val(
									startContent + data.link + endContent );
						}
						
						$(".text_area_copy").val(
								startContent + data.link
										+ endContent.replace("退订回N", "")
										+ "退订回N");
						addLength();
					}
				}
			});
		}
	}

	//获取发送状态
	function getSendStatus(status) {
		$("#sendStatus").val(status);
		var timestamp = Date.parse(new Date())+1800000;
		$("#tser01").val(show(timestamp));
	}
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

	//测试短信发送
	$(".cheShiFaSong").click(function() {
		var that=$(this);
		if($(this).hasClass('on'))return;
		$(this).addClass('on');
		var phonez = /^(13[0-9]|14[5,7]|15[0-3,5-9]|166|17[6-8]|18[0-9]|19[8,9])\d{8}$/;
		var phone = $("#iphone").val();//获取电话
		var content = $(".text_area").val();//获取短信内容
		var autograph = $("#smsSign").val();//短信签名
		var phoneflag = 1;//手机号发送判断标识

		if (content != "") {
			content = content.replace("退订回N", "") + "退订回N";
			//判断号码内容是否成功 
			var phoneList = phone.split(",");
			if (phoneList.length <= 5) {
				for (var i = 0; i < phoneList.length; i++) {
					if (phoneList[i].match(phonez)) {
						phoneflag = 0;
					} else {
						phoneflag = 1;
						break;
					}
				}
			} else {
				phoneflag = 2;
			}

			if (phoneflag == 0) {
				var url = "${ctx}/crm/test/sendSMS";
				$.post(url, {
					"content" : content,
					"phone" : phone,
					"autograph" : autograph
				}, function(data) {
					that.removeClass('on');
					if (data.send == '100') {
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("发送成功!")
						setTimeout(function() {
							$(".tishi_2").hide()
							$(".text_test_window").hide();
						}, 3000)
					} else {
						$(".tishi_2").show();
						$(".tishi_2").children("p").text("发送失败!")
						setTimeout(function() {
							$(".tishi_2").hide()
						}, 3000)
					}
				});	

				
			} else if (phoneflag == 1) {
				that.removeClass('on');
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("手机号错误!")
				setTimeout(function() {
					$(".tishi_2").hide()
				}, 3000)
			} else if (phoneflag == 2) {
				that.removeClass('on');
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("手机号不能大于5个")
				setTimeout(function() {
					$(".tishi_2").hide()
				}, 3000)
			}
		} else {
			that.removeClass('on');
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容不能为空!")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		}
	});

	var flag = false;
	//发送短信
	function sendMessage(){
		
		
		var sendCount;
		var actualCount = $('#submitInfo span').text();
		if(actualCount!=null){
			sendCount  = parseInt(actualCount);
		}
		var queryKey = $('#queryKey').val();
		//短信内容
		var content = $(".text_area_copy").val().trim();
		//发送类型
		var sendType = $("#sendStatus").val();
		//定时发送的时间
		var sendTime = $("#tser01").val();
		//短信签名
		var autograph = $("#smsChannel").val(); 
		//活动名称
		var activityName = $("#activityName").val();
	    var left = /【/g;
		var right = /】/g;
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
		}else if(regName[0].indexOf(autograph)==-1){
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
		}
		
		
		if(sendCount ==null || sendCount<1 
		  	|| queryKey==null || queryKey==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("您还没有选择要发送的对象!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return;
		}
		if(autograph ==null||autograph==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请选择短信渠道或者填写自定义短信签名!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return;
		}
		if(content ==null ||content==""||content.replace(autograph,"")==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容不能为空!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			return;
		}else if(content.indexOf(autograph)== -1){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信签名或者渠道不能为空!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			return;
		}else if(content.indexOf("【买家昵称】")>=0 && (content.match(left).length>=3 || content.match(right).length>=3)){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容书写有误(【/】不可随意添加)!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			return;
		}else if(content.indexOf("【买家昵称】")== -1 && (content.match(left).length>=2 || content.match(right).length>=2)){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("短信内容书写有误(【/】不可随意添加)!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},3000)
			return;
		} 
		if(sendType==null ||sendType==""){
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请确认是立即发送或者定时发送!")
			setTimeout(function(){ 
				$(".tishi_2").hide()
			},2000)
			return;
		}else{
			if(sendType ==2 &&(sendTime==null ||sendTime =="")){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("定时发送的时间不能为空!")
				setTimeout(function(){ 
					$(".tishi_2").hide()
				},3000)
				return;
			}
		}
		$('#showSendBox').show();
		$('.markBg').show();
		$('#showSendBox p strong').text($('#inputNum').text());
		$('#showSendBox p em').text($('#contePrice').text());

		
	}
	$(function(){
		
		$('#showSendBox .qd').click(function(){
			if($(this).hasClass('jz'))return;
			$(this).addClass('jz');
			var sendCount;
			var actualCount = $('#submitInfo span').text();
			if(actualCount!=null){
				sendCount  = parseInt(actualCount);
			}
			var queryKey = $('#queryKey').val();
			//短信内容
			var content = $(".text_area_copy").val().trim();
			//发送类型
			var sendType = $("#sendStatus").val();
			//定时发送的时间
			var sendTime = $("#tser01").val();
			//短信签名
			var autograph = $("#smsChannel").val(); 
			//活动名称
			var activityName = $("#activityName").val();
			var _this=$(this);
			
			$(".tishi_2").show();
			var text_ = '正在操作中,请勿执行其他操作!';
			$(".tishi_2").children("p").append(text_);
			$.ajax({
				url:"${ctx}/msgSend/memberMsgSend",
				//data:{"ids":ids,"content":content,"sendType":sendType,"sendTime":sendTime,"autograph":autograph,"activityName":activityName,"mapKey":mapKey},
				data:{"content":content,"sendType":sendType,"sendTime":sendTime,"autograph":autograph,"activityName":activityName,"totalCount":sendCount,"queryKey":queryKey},
				type: "post",
				success:function(data){
					
					
					$(".tishi_2").hide();
					$(".tishi_2").show();
					$(".tishi_2").children("p").html("");
					$(".tishi_2").children("p").text(data.message)
					setTimeout(function(){ 
						$(".tishi_2").hide()
						$(".tishi_2").children("p").html("");
					},3000) 
					if(data.status == true){
						var key = param = data.key;
						if(key !=null && key =="schedule"){
							setTimeout(function(){ 
								$(".tishi_2").hide()
								$(".tishi_2").children("p").html("");
								location.href="${ctx}/msgSendRecord/toSendRecord";	
								_this.removeClass('jz');
							},3000);
						}else{
							setTimeout(function(){ 
								$(".tishi_2").hide()
								$(".tishi_2").children("p").html("");
								location.href="${ctx}/msgSendRecord/memberSendRecord";
								_this.removeClass('jz');
							},3000);
						}
					}else{
						$(".tishi_2").hide();
						$(".tishi_2").show();
						$(".tishi_2").children("p").html("");
						$(".tishi_2").children("p").text(data.message)
						setTimeout(function(){ 
							$(".tishi_2").hide()
							$(".tishi_2").children("p").html("");
							_this.removeClass('jz');
						},3000);
					}
				},
				dataType:'json'
			});
		});
		$('#showSendBox .qx').click(function(){
			$('#showSendBox').hide();
			$('.markBg').hide();
		});
	});

	//=============禁止搜索连续点击==========
	function doDisabled() {
		$(".searchall").attr("disabled", true);
		window.setTimeout(doAbled, 1000);
	}
	function doAbled() {
		$(".searchall").attr("disabled", false);
	}

	//定位光标的位置，将内容分割成两个部分
	var startContent, endContent;
	function getCursortPosition(obj) {
		if (typeof obj.selectionStart === 'number'
				&& typeof obj.selectionEnd === 'number') {
			var startPos = obj.selectionStart, endPos = obj.selectionEnd, cursorPos = startPos, tmpStr = obj.value;
			startContent = tmpStr.substring(0, startPos);
			endContent = tmpStr.substring(endPos, tmpStr.length);
		}
	}

	//使用ajax查询商品数据,回显页面
	function findItem() {
		
		$('.item_tr').siblings().remove();
		var url = "${ctx}/item/queryItem";
		$.post(url,
			function(data) {
							var item = data.itemList;
							var item;
							var imgUrl = null;
							if (item == null || item == "undefined"
									|| item == '') {
								item = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
								$('.item_table').append(item);
							} else {
								$.each(
												item,
												function(i, result) {
													if (result.url == null) {
														imgUrl = imgUrl = "<img style='width:4.17vw;height:4.17vw;' src='${ctx}/crm/images/no.png'>";
													} else {
														imgUrl = "<img style='width:4.17vw;height:4.17vw;' "+"src='"+result.url+"'>";
													}
													item = "<tr><td class='h60 text-center'><div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div '>"
															+ "<input type='hidden' value='"+result.numIid+"'/></div></td>"
															+ "<td class='h60 text-center'>"
															+ imgUrl
															+ "</td><td class='h60 text-center'>"
															+ result.title
															+ "</td><td class='h60 text-center'>"
															+ result.price
															+ "</td></tr>";

													$('.item_table').append(item);
												});
								checkItem();
							}
							
						});
	}

	//使用ajax通过查询条件查询商品信息
	function findItemByData(e) {
		if($(e).hasClass('on'))return;
		$(e).addClass('on');
		$('.item_tr').siblings().remove();
		var itemId = $("#itemId_input").val();//获取商品ID
		var itemName = $("#itemName_input").val();//关键字(商品名称)
		var status = $("#status_input").val();//获取只显示上架

		var url = "${ctx}/item/queryItem";
		$
				.post(
						url,
						{
							"commodityId" : itemId,
							"name" : itemName,
							"status" : status
						},
						function(data) {
							$(e).removeClass('on');
							var item = data.itemList;
							var item;
							var imgUrl = null;
							if (item == null || item == "undefined"
									|| item == '') {
								item = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
								$('.item_table').append(item);
							} else {
								$.each(item,
										function(i, result) {
											if (result.url == null) {
												imgUrl = imgUrl = "<img style='width:4.17vw;height:4.17vw;' src='${ctx}/crm/images/no.png'>";
											} else {
												imgUrl = "<img style='width:4.17vw;height:4.17vw;' "+"src='"+result.url+"'>";
											}
											item = "<tr><td class='h60 text-center'><div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div bgc_check_blue'>"
													+ "<input type='hidden' value='"+result.numIid+"'/></div></td>"
													+ "<td class='h60 text-center'>"
													+ imgUrl
													+ "</td><td class='h60 text-center'>"
													+ result.title
													+ "</td><td class='h60 text-center'>"
													+ result.price
													+ "</td></tr>";

											$('.item_table').append(
													item);
											//alert(result.name);
											/* item = "<tr><td class='h60 text-center'><div class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK m_t17 item_check_div'><input type='hidden' value="+result.numIid+"></div></td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>";
											$('.item_table').append(item); */
										});
								checkItem();
							}
						});
	}

	//回显已选择商品选中状态
	function checkItem() {
		var itemidInput = $(".item_check_div");
		var itemIds = $("#appoint_ItemId").val();

// 		var itemIdList = itemIds.split(",");
		if(itemIds==''){
			$(".item_check_div").addClass('bgc_check_blue');
			$(".item_table").children().find(".fuxuankuang").addClass("bgc_check_blue");
		}
		
		
		for (var i = 0; i < itemidInput.length; i++) {
			for (var j = 0; j < itemIds.length; j++) {
				if (itemidInput.eq(i).children().val() == itemIds[j]) {
					itemidInput.eq(i).addClass("bgc_check_blue");
				}
			}
		}
	}

	//当点击确定时获得选中的商品id
	function addItemId() {
		var itemIds = [];
		var divCheck = $(".item_check_div");
		for (var i = 0; i < divCheck.length; i++) {
			if (divCheck.eq(i).hasClass("bgc_check_blue")) {
				var val = divCheck.eq(i).children().val();
				itemIds.push(val)
			}
		}
		if($(".item_check_div.bgc_check_blue").length>0){
			$('.memberAlli').removeClass('on');
		}else{
			$('.memberAlli').addClass('on');
		}
		$("#appoint_ItemId").val(itemIds);
		$("#itemIds").val(itemIds);
		$("#commodityId").val(itemIds);
		$('.member-alerta').text('已选择'+itemIds.length+'件商品');
	}

	//添加地区的点击事件
	function dj_1() {
		$(".ChoiceArea").show();
	}
	//添加商品选择的点击事件
	function dj_2() {
		$(".ChoiceSpecified").show();
	}

	//商品输入框只能输入啊汉字
	$('#itemId_input').keyup(function() {
		var itemIdVal = $('#itemId_input').val();
		if (isNaN(itemIdVal)) {
			$('#itemId_input').val('');
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("订单ID选择框只能输入数字")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
		}
	})

	//正则表达式
	function zhengze(value1, value2) {
		var val1 = $("#" + value1).val();
		var val2 = $("#" + value2).val();
		val1 = Number(val1);
		val2 = Number(val2);
		if (val2 != null && val2 != "" && val1 > val2) {
			
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("请输入正确的数据！")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 1500)

			// 		alert("请输入正确的数据！");
			$("#" + value1).val(null);
			$("#" + value2).val(null);
		}

	}
	
	function download(){
		var queryKey = $('#queryKey').val();
		$(".tishi_2").show();
		$(".tishi_2").children("p").text("数据下载中请耐心等候................");
		setTimeout(function(){ 
		$(".tishi_2").hide()
		},5000);
		location.href = "${ctx}/memberInfoController/downloadMemberInfo?queryKey="+queryKey;
	}
	//点击搜索后效果
	$('.member-search-btn').on('click',function(){
		searchAll();
	});
	//会员筛选弹框取消发送效果
	$('.closeInfo').on('click',function(){
//		$('.sifting').hide();
//		$('#memberCount').hide();
//		$('#sendButton').show();
//		$('#sendButton').text('点击选择发送对象');
//		'${groupName}'='';
		window.location.href='${ctx}/marketingCenter';
	});
	
	$(function(){
		var start={
			insTrigger:false,
			isTime:true,
			format:'YYYY-MM-DD hh:mm:ss',
			choosefun: function(elem, val, date){

		        end.minDate = val; //开始日选好后，重置结束日的最小日期
		    }
		};	
		var end={
			insTrigger:false,
			isTime:true,
			format:'YYYY-MM-DD hh:mm:ss', //最大日期
		    choosefun: function(elem, val, date){

		        start.maxDate = val; //将结束日的初始值设定为开始日的最大日期
		    }	
		}
		
		$('#tser05').on('click',function(){
			$('#tser05').jeDate(start);
		});
		$('#tser06').on('click',function(){
			$('#tser06').jeDate(end);
		});
		
		$('.activityLink').on('click',function(){
			$('#activityLinkTxtBox').show();
		});
		$('#hideTxtBoxQX').on('click',function(){
			$('#commodityId').val('');
			$('.activityLinkTxt').val('');
		});
	});
	
</script>
</html>
