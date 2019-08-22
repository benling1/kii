<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>

<%@ include file="/common/common.jsp"%>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />


<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!--兼容360meta-->

<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="renderer" content="webkit">


<%-- <link rel="stylesheet" href="${ctx}/crm/css/public_new.css" /> --%>
<link rel="stylesheet" href="${ctx}/crm/css/index.css" />

<!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
<!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

<script type="text/javascript" src="${ctx}/crm/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/index.js"></script>
<script type="text/javascript" src="${ctx}/crm/js/highcharts.js"></script>

<style>
.member-roll{overflow:hidden; position:relative; height:2.1rem;}
.hdIndexBannerBox{
	width: 52.08vw;
    height: 31.25vw;
    position: fixed;
    left: 50%;
    top: 50%;
    margin-left: -26.04vw;
    margin-top: -15.625vw;
    z-index:55;
}
.hdIndexBannerBox i{
	position: absolute;
    width: 0.78125vw;
    height: 0.78125vw;
    background: url('${ctx}/crm/images/bgicon.png') 0 -19.7vw;
    background-size:2.604vw 20.8vw;
    right: 0.6208vw;
    top: 0.6208vw;
    cursor: pointer;
}
.hdIndexBannerBox img{
	width:100%;
	height:100%;
}
#markBgHd{
	width: 100%;
    height: 100%;
    background: rgba(0,0,0,0.5);
    left: 0;
    top: 0;
    position: fixed;
    z-index: 31;
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/crms/header/top.jsp"%>
	<%@ include file="/WEB-INF/views/crms/header/header.jsp"%>
	<div class="text-center llq_tip display_none font16"
	style="position: fixed; top: 0; background-color: #fefca2; width: 98%; z-index: 20; height: 2.1vw; line-height: 2.1vw; left: 0.5vw; border-radius: 10px; box-shadow: 5px 7px 5px #888888; border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">
		<div class="margin0_auto text-center" style="width: 24vw;">
			<p class="f_l" style="height: 2.1vw; line-height: 2.1vw;">建议使用</p>
			<p class="f_l" style="height: 2.1vw; line-height: 2.1vw;">
				<a href="http://www.firefox.com.cn/" class="font16 cursor_"
					style="margin-left: 3vw;"><img class=""
					style="width: 1.15vw; vertical-align: middle;"
					src="${ctx }/crm/images/firefox.png" /><span>火狐</span></a> <a
					href="http://rj.baidu.com/soft/detail/14744.html?ald" class="font16 cursor_"
					style="margin-left: 3vw;"><img class=""
					style="width: 1.15vw; vertical-align: middle;"
					src="${ctx }/crm/images/google.png" /><span>谷歌</span></a> <a
					href="http://chrome.360.cn/" class="font16 cursor_"
					style="margin-left: 3vw;"><img class=""
					style="width: 1.15vw; vertical-align: middle;"
					src="${ctx }/crm/images/360.png" /><span>360</span></a>
			</p>
			<p class="f_l"
				style="height: 2.1vw; line-height: 2.1vw; margin-left: 0.3vw;">
				浏览器</p>
			<p class="clear"></p>
		</div>
		<p class="" style="margin-top: -1.6vw; width: 98vw;">
			<img class="chachacha" style="float: right; margin-right: 0.5vw;"
				src="${ctx }/crm/images/guanbi.png" />
		</p>
	</div>
	
	<div class="w1900">
		<!-------------------------左部，侧边栏------------------------->
		<div class="load_left"></div>


		<!-------------------------右部------------------------->
		<div class="w1700 m_l200">
			<!--------------------顶部导航栏-------------------->
			<div class="load_top"></div>


			<!--------------------主要内容区-------------------->
			<!--------------------订单数-------------------->
			<input type="hidden" id="totalTrade" value="${totalTrade}">
			<!--------------------待付款订单数-------------------->
			<input type="hidden" id="waitPayTrade" value="${waitPayTrade}">
			<!--------------------昨日发货订单数-------------------->
			<input type="hidden" id="succPayTrade" value="${succConsignTrade}">
			<!--------------------待发货订单数-------------------->
			<input type="hidden" id="waitConsignTrade" value="${waitConsignTrade}">
			<!--------------------退款中订单数-------------------->
			<input type="hidden" id="refundTrade" value="${refundTrade}">
			<!--------------------已付款金额-------------------->
			<input type="hidden" id="succPayment" value="${succPayment}">
			<!--------------------本月短信消费金额-------------------->
			<input type="hidden" id="monthSmsMoney"  value="${monthSmsMoney}">
			<!--------------------本月催付挽回金额-------------------->
			<input type="hidden" id="monthReminderMoney"  value="${monthReminderMoney}">
			<!--------------------本月会员营销金额-------------------->
			<input type="hidden" id="monthMemberMoney"  value="${monthMemberMoney}">
			<!--------------------今天短信消费金额-------------------->
			<input type="hidden" id="todaySmsMoney"  value="${todaySmsMoney}">
			<!--------------------今天催付挽回金额-------------------->
			<input type="hidden" id="todayReminderMoney"  value="${todayReminderMoney}">
			<!--------------------今天会员营销金额-------------------->
			<input type="hidden" id="todayMemberMoney"  value="${todayMemberMoney}">
			<!--------------------昨日短信消费金额-------------------->
			<input type="hidden" id="yesterSmsMoney"  value="${yesterSmsMoney}">
			<!--------------------昨日催付挽回金额-------------------->
			<input type="hidden" id="yesterReminderMoney"  value="${yesterReminderMoney}">
			<!--------------------昨日会员营销金额-------------------->
			<input type="hidden" id="yesterMemberMoney"  value="${yesterMemberMoney}">
			<!--------------------本店铺会员数-------------------->
			<input type="hidden" id="memberCount" value="${memberCount}">
			
			<input type="hidden" id="settingType" value="${settingTypeStr}">
			<div class=" m_l60">
						
				<div id="kyIndexBox" class="clearfix">
					<div class="kyIndex-left">
						<div class="shortcut-entrance">
							<div class="kyIndexTitle clearfix">
								<i></i>	
								<h3>快速入口</h3>
							</div>	
							<div class="kyIndex-content">
								<ul class="clearfix">
									<li class="xiadanguanhuai">
										<a href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH">
											<img class="imgone" src="${ctx }/crm/images/xiadanguanhuai.png"/>
<%-- 											<img class="imgtwo" src="${ctx }/crm/images/xiadanguanhuai_J.png"/> --%>
										</a>
									</li>
									<li class="changguicuifu">
										<a href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH#/promptCare/promptCareIndex">
											<img class="imgone" src="${ctx }/crm/images/changguicuifu.png"/>
<%-- 											<img class="imgtwo" src="${ctx }/crm/images/changguicuifu_J.png"/> --%>
										</a>
									</li>
									<li class="fukuanguanhuai">
										<a href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH#/paymentConcern/paymentConcernIndex">
											<img class="imgone" src="${ctx }/crm/images/fukuanguanhuai.png"/>
<%-- 											<img class="imgtwo" src="${ctx }/crm/images/fukuanguanhuai_J.png"/> --%>
										</a>
									</li>
									<li class="qianshoutixing">
										<a href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH#/logisticsReminder/signReminderIndex">
											<img class="imgone" src="${ctx }/crm/images/qianshoutixing.png"/>
<%-- 											<img class="imgtwo" src="${ctx }/crm/images/qianshoutixing_J.png"/> --%>
										</a>
									</li>
									<li class="baobeiguanhuai">
										<a href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH#/babyCare/babyCareIndex">
											<img class="imgone" src="${ctx }/crm/images/baobeiguanhuai.png"/>
<%-- 											<img class="imgtwo" src="${ctx }/crm/images/baobeiguanhuai_J.png"/> --%>
										</a>
									</li>
									<li class="">
										<a href="${ctx}/marketingCenter">
											<img style="height: 1.26rem;" class="imgone" src="${ctx }/crm/images/huiyuanduanxinqunfa.png"/>
											
										</a>
									</li>
									<li class="">
										<a href="${ctx}/smsSendAppoint/appointNumberSend">
											<img style="height: 1.26rem;" class="imgone" src="${ctx }/crm/images/zhidinghaomafasong.png"/>
			
										</a>
									</li>
									<li class="">
										<a href="${ctx}/OrderHistoryImport/showLoadHistoryOrder">
											<img style="height: 1.26rem;" class="imgone" src="${ctx }/crm/images/lishidingdandaoru.png"/>
											
										</a>
									</li>
									<li class="huikuantixing">
										<a href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH#/payment/paymentRemindIndex">
											<img class="imgone" src="${ctx }/crm/images/huikuantixing.png"/>
<%-- 											<img class="imgtwo" src="${ctx }/crm/images/huikuantixing_J.png"/> --%>
										</a>
									</li>
									<li class="zhongchapingguanli">
										<a href="${ctx}/placeAnOrderCare/queryBasicAndAdvancedXDGH#/evaluationManagement/middleSchoolReviewMonitor?type=20">
											<img style="height: 1.26rem;" class="imgone" src="${ctx }/crm/images/zhongchapingguanli.png"/>
<%-- 											<img style="height: 1.26rem;" class="imgtwo" src="${ctx }/crm/images/zhongchapingguanli_J.png"/> --%>
										</a>
									</li>
								</ul>
							</div>
						</div>	
						<div class="effect-analysis">
							<div class="kyIndexTitle clearfix">
								<i class="effect-analysisIcon"></i>	
								<h3>效果分析</h3>
							</div>
							<div style="position:relative;width:10rem;height:4rem;">
								<img class="loadingImg" src="${ctx }/crm/images/jiazai.gif" style="position:absolute;width:2rem;height:2rem;margin-left:-1rem;margin-top:-1rem;left:50%;top:50%;">
							</div>
							<div id="effect-analysis">
									
							</div>
						</div>
						
							
							<div class="member-entrance">
							
								<div class="kyIndexTitle clearfix">
									<i class="member-entranceIcon"></i>	
									<h3>会员信息</h3>
								</div>
								<div id="member-entrance">
									<div style="position:relative;width:10rem;height:3.6rem;">
										<img class="loddingImg2" src="${ctx }/crm/images/jiazai.gif" style="position:absolute;width:2rem;height:2rem;margin-left:-1rem;margin-top:-1rem;left:50%;top:50%;">
									</div>
									<div class="member-entranceBox">
										
										<div class="member-entranceBox-top clearfix">
											<p>
												<span>目前店铺会员共：</span>
												<em><a id="storeRenNum" href="${ctx}/crms/memberInformation/memberInformation" target="_black"></a></em>
												<span>人</span>
											</p>	
											<p class="daoru">会员数据不准确？快去 <a href="${ctx}/OrderHistoryImport/showLoadHistoryOrder">导入历史订单</a> 吧 !</p>
										</div>	
										<div class="member-entranceBox-bottom clearfix">
											<h4><i></i>会员回复短信内容</h4>
											<div class="member-roll" id="breakNews">
												<ul id="member-rollUl" class="list6">
		
													
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						
						
					</div>	
					<div class="kyIndex-right">
						<div class="system-announcement">
							<div class="kyIndexTitle clearfix">
								<i class="system-announcementIcon"></i>	
							    
								<h3>系统公告</h3>
							</div>
							<div class="system-announcementBox">
								<div id="system-announcement-roll" class="clearfix">
									<i></i>
									<ul>
										<li>物流短信到达率<span>98.5</span>%</li>
										<li>营销中心短信到达率<span>98.5</span>%</li>
										<li>电信通道<span>正常</span></li>
										<li>移动通道<span>正常</span></li>
										<li>联通通道<span>正常</span></li>
									</ul>
								</div>	
								<div class="system-announcementTab">
									<ul>
										<li class="on"><img src="${ctx }/crm/images/index_01.png"/></li>
										<li><img src="${ctx }/crm/images/index_02.png"/></li>
									</ul>
									<!--<ol class="clearfix">
										<li></li>
										<li></li>
									</ol>-->
								</div>
							</div>
						</div>	
						<div class="store-data">
							<div class="kyIndexTitle clearfix">
								<i class="store-dataIcon"></i>	
								<h3>店铺数据</h3>
							</div>
							<div class="store-dataBox">
								<div class="store-dataTitleBox clearfix">
									<p style="margin-right:.5rem;">昨日数据</p>
									<p>历史数据</p>
								</div>
								<ul class="clearfix">
									<li class="clearfix" style="padding-top:0;">
										
										<div class="store-dataDiv orderDiv">
											<p >0</p>
											<span>订单数</span>
										</div>
									</li>
									
									<li style="padding-top:0;">
									
										<div class="store-dataDiv pending-paymentDiv">
											<p>0</p>
											<span>待付款单数</span>
										</div>
									</li>
									<li>
										
										<div class="store-dataDiv payment-amountDiv">
											<p>0</p>
											<span>付款金额</span>
										</div>
									</li>
									<li>
										
										<div class="store-dataDiv paidDiv">
											<p>0</p>
											<span>待发货订单数</span>
										</div>
									</li>
									<li>
										
										<div class="store-dataDiv shippedDiv">
											<p>0</p>
											<span>已发货订单</span>
										</div>
									</li>
									<li>
										
										<div class="store-dataDiv refundDiv">
											<p>0</p>
											<span>退款中订单</span>
										</div>
									</li>
								</ul>	
							</div>
						</div>
						<div class="common-problem">
							<div class="kyIndexTitle clearfix">
								<i class="common-problemIcon"></i>	
								<h3>常见问题</h3>
							</div>
							<div class="common-problemBox">
								<ul>
<!-- 									<li class="clearfix"> -->
<%-- 										<a href="${ctx}/crms/home/notice#huiyuandengjihuafen"> --%>
<!-- 											<i></i> -->
<!-- 											<span>会员等级划分</span> -->
<!-- 											<em>2016-06-28</em> -->
<!-- 										</a> -->
<!-- 									</li> -->
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice.html#huiyuanfenzu">
											<i></i>
											<span>会员分组</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#huiyuanhudong">
											<i></i>
											<span>会员互动</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#heimingdanguanli">
											<i></i>
											<span>黑名单管理</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#duanxinyingxiao">
											<i></i>
											<span>短信营销</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#shoujihaoshezhi">
											<i></i>
											<span>手机号设置</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#duodianpubangding">
											<i></i>
											<span>多店铺绑定</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#dingdanliebiao">
											<i></i>
											<span>订单列表</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#duanxinfasongjilu">
											<i></i>
											<span>短信发送记录</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#dianpushuju">
											<i></i>
											<span>店铺数据</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#caozuorizhi">
											<i></i>
											<span>操作日志</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#qita">
											<i></i>
											<span>其他</span>
											<em>2016-06-28</em>
										</a>
									</li>
									<li class="clearfix">
										<a href="${ctx}/crms/home/notice#duanxinxiangguan">
											<i></i>
											<span>短信相关</span>
											<em>2016-06-28</em>
										</a>
									</li>
								</ul>	
							</div>
						</div>
					</div>	
				</div>
						
		

				
			
			</div>
		</div>
	</div>
	<input type="hidden" value="${msg}" id="msg">
	<div style="position: fixed; top: 0; width: 100%; height: 100%; z-index: 10; display: none;" class="xszy_" onclick="sxJsp();">
		<img style="width: 100%; height: 100%;" src="${ctx}/crm/images/xinshouzhiyin.png">
		<div class="close_xszy cursor_" style="width: 12%; height: 6%; position: fixed; bottom: 14%; left: 46%;"></div>
	</div>
	<input type="hidden" id="userId" value="${taobao_user_nick}">
	<div class="position_fixed top0 z_20 left0 guide_img display_none" id="hongbaoshow" style="width:100%; height:100%;">
		<div class="w1903 h1000 "
			style="display: block; background: rgba(0, 0, 0, 0.5); width:100%; height:100%;">
			<div class="p_t185 ">
				<div class=" w620 h615 bgi margin0_auto" style="position:relative">
					<img id="hongbaoclose" src="${ctx}/crm/images/shibaiicon2.png">
					<div class="p_t145">
						<div class=" m_l110 ">
							<div>
								<div class="font16 c_384856 f_l h50 l_h50 m_r10  ">手机号</div>
								<input maxlength="11" class="display_block phone_num bgc_f4f6fa border0 f_l h50 l_h50 w250 SJ " id="phoneNum1" onBlur="writePhone()" />
							</div>
							<div class=" w327 h50 p_t15 clear">
								<div class="font16 c_384856 f_l h50 l_h50 m_r10  ">验证码</div>
								<input class="display_block phone_num bgc_f4f6fa border0 f_l h50 l_h50 w130 SJ YZ" id="phoneCode1" />
								<!-- <div class="w100 h50 f_l  m_l20 lh50 text-center c_fff bgc_00a0e9 b_radius5 cursor_  HQ font16" >获取验证码</div> -->
								<!-- onclick="getMessage()" -->
								<input class="w100 h50 f_l  m_l20 l_h50 text-center c_fff bgc_00a0e9 radius5 cursor_ HQ font16" readOnly value="获取验证码" style="disabled: true" />

								<div class="w100 h42 f_l  m_l20  l_h42 text-center c_fff bgc_d9d9d9 radius5 cursor_ font14 display_none JS ">
									<span class="NB" id="timer">60</span>s后重新获取
								</div>
							</div>
							<div class="clear p_t15">
								<div class="font16 c_384856 f_l h50 l_h50 m_r15  ">QQ号</div>
								<input maxlength="11" class="display_block phone_num bgc_f4f6fa border0 f_l h50 l_h50 w250 SJ " id="qqNum" />
							</div>
						</div>
						<div class="h100 w560 clear p_t25">
							<div class="w95 h100 b_radius50 margin0_auto  z_21 close cursor_"
								onclick="saveMessage()"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	
	
	
	<!-- 双11活动 -->
<!-- 	<%-- <div class="position_fixed 11huodong top0 left0 display_none" style="background: rgba(0, 0, 0, 0.5); width:100%; height:100%;z-index:21;"> -->
<!-- 		<div style="width: 11rem;position:fiexd;left: 50%;position: relative;margin-left: -5.5rem;top: 1rem; z-index:20;"> -->
			<img style="width: 11rem;" id="huodongclose" src="${ctx}/crm/images/11HuoDong.jpg">
<!-- 			<div class="closeHuoDong" style="width: 1rem;height: 1rem;position: absolute;cursor: pointer;right:0;top:0;background-color:#fff;opacity:0;filter:alpha(opacity=0);  z-index:22;"></div> -->
<!-- 		</div> -->
<!-- 	</div> --> --%>
	
	
	
	
<!-- 	972 997 -->
	<div class="position_fixed top0 z_20 left0 guide w100_ h100_ display_none" style="background: rgba(0, 0, 0, 0.5);display:none;">
		<div class=" bgi2 position_absolute w960 h470 top180 m_l_480 left50_">
			<div class="position_absolute w40 h40 cursor_ display_block CloseIMG radius50 right20">
			</div>
		</div>
	</div>
<!-- 	<div class="hdIndexBannerBox"> -->
<!-- 		<i></i> -->
<%-- 		<img src="${ctx}/crm/images/hdindex.jpg"> --%>
<!-- 	</div> -->
<!-- 	<div id="markBgHd"></div> -->
	<script type="text/javascript">
		$('.CloseIMG').on('click', function() {
			$('.guide').hide();
		})
		
		
	</script>


</body>

	

<script>
	//测试同步测试库的数据
	$('#testSych').click(function(){
		$.post("${ctx}/effect",function(data){
			alert(data.msg);
		},'json')
	})
	//测试同步测试库的数据
	$('#testUpdate').click(function(){
// 		var h = $('#h').val();
// 		var m = $('#m').val();
		var status = $('#status').val();
		$.post("${ctx}/shopData/reportList",{"dateType":status},function(data){
			alert(data.dataList+"--"+data.totalPage + "--" + data.totalCount);
		},'json')
	})
	
	
	
	
	//手机号输入失去焦点,后台判断手机号是否存在与格式
	function writePhone() {
		var phoneNum = $("#phoneNum1").val();
		//判断手机号必需匹配正则
		if (!(/^(13[0-9]|14[5,7]|15[0-3,5-9]|166|17[6-8]|18[0-9]|19[8,9])\d{8}$/.test(phoneNum))) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("手机号码有误或为空，请重填!")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
			return;
		}
	}

	window.onload = function() {
		var msg = $("#msg").val();
		if (msg != null && msg != "") {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text(msg)
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000);
		}
	}

	function saveMessage() {
		var qqNum = $("#qqNum").val();
		//手机接收到的code
		var phoneCode = $("#phoneCode1").val();
		var phoneNum = $("#phoneNum1").val();
		if (phoneNum == null || phoneNum == '') {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("手机号码为空!")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
			return false;
		}
		if (isNaN(qqNum)) {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("QQ号书写格式错误!")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
			return false;
		}
		if (phoneCode == null || phoneCode == '') {
			$(".tishi_2").show();
			$(".tishi_2").children("p").text("验证码不能为空!")
			setTimeout(function() {
				$(".tishi_2").hide()
			}, 3000)
			return false;
		}

		//开始保存
		$.ajax({
			url : "${ctx}/systemManage/saveUserInformation",
			data : {
				"mobile" : phoneNum,
				"qqNum" : qqNum,
				"code" : phoneCode
			},
			type : "post",
			success : function(data) {
				if (data.status == true) {
					$(".guide_img").hide();
// 					$(".11huodong").show();
// 					$(".xszy_").show();
// 					$('.guide').show();
				} else {
					$(".guide_img").show();
				}

				$(".tishi_2").show();
				$(".tishi_2").children("p").text(data.message)
				setTimeout(function() {
					$(".tishi_2").hide()
				}, 3000)
			},
			dataType : 'json'
		})
	}

	//判断小红框是否展开或者隐藏
	$(function() {
		$.ajax({
			url : "${ctx}/systemManage/queryUserInformation",
			data : {},
			type : "post",
			success : function(data) {
				
				if (!data.status) {
					$(".guide_img").hide();
			
				} else {
					
					$(".guide_img").show();
				}
			},
			dataType : 'json'
		});
		
		$('#hongbaoclose').click(function(){

			$('#hongbaoshow').hide();
			$.ajax({
				url:'${ctx}/systemManage/markIndexShow',
				data:{
					flag:true
				},
				type:'post',
				success:function(data){
					console.log(data);
				}
			})

			
		});
		function myBrowser() {
			var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串

			if (userAgent.indexOf("Firefox") > -1) {
				return "FF";
			} //判断是否Firefox浏览器
			if (userAgent.indexOf("Chrome") > -1) {
				return "Chrome";
			}
			if (userAgent.indexOf("compatible") > -1
					&& userAgent.indexOf("MSIE") > -1) {
				return "IE";
			}
			; //判断是否IE浏览器
		}

		//以下是调用上面的函数
		var mb = myBrowser();

		if ("FF" == mb) {
			$(".llq_tip").hide();
		}
		if ("Chrome" == mb) {
			$(".llq_tip").hide();
		}
		if ("IE" == mb) {
			$(".llq_tip").slideDown(1000);
		}
		;
	})

	//=========用于绑定的事件=================
	function doDisabled() {
		$(".HQ").attr("disabled", true);//此处的标签必须是input????
		window.setTimeout(doAbled, 1000);
	}
	function doAbled() {
		$(".HQ").attr("disabled", false);
	}

	//填写手机号获取验证码
	$(function() {
		//绑定点击事件
		$(".jia_zai").hide();
		$('.HQ').bind("click", function() {
			doDisabled();
			var mobile = $("#phoneNum1").val();
			//判断手机号必需匹配正则
			if (!(/^(13[0-9]|14[5,7]|15[0-3,5-9]|166|17[6-8]|18[0-9]|19[8,9])\d{8}$/.test(mobile))) {
				return false;
			} else {
				$('.HQ').hide();
				$('.JS').show();
				waitTime();
				doDisabled();
			}
			//使用ajax异步提交
			$.ajax({
				url : "${ctx}/systemManage/indexSecurityCode",
				data : {"mobile":mobile},
				type : "post",
				success : function(data) {
					if (data.status == false) {
						$(".tishi_2").show();
						$(".tishi_2").children("p").text(data.message)
						setTimeout(function() {
							$(".tishi_2").hide()
						}, 3000)
					}
				},
				dataType : 'json'
			})
		})
	})
	//60秒倒计时函数
	function waitTime() {
		var T = 60;
		timer = null;
		timer = setInterval(function() {
			T--;
			document.getElementById('timer').innerHTML = T;
			if (T == 0) {
				clearInterval(timer);
				document.getElementById('timer').innerHTML = 60;
				$('.JS').hide();
				$('.HQ').show();
			}
		}, 1000)
	}

	//点击完引导页后刷新页面
	function sxJsp() {
		location.href = "${ctx}/getUserInfo/getDate";
	}
	if(!sessionStorage.getItem('totalTrade')){
		
		$.ajax({
			url : "${ctx}/member/indexData",
			type : "get",
			success : function(data) {
				sessionStorage.setItem('monthSmsMoney', data.monthSmsMoney);
				sessionStorage.setItem('monthReminderMoney', data.monthReminderMoney);
				sessionStorage.setItem('monthMemberMoney', data.monthMemberMoney);
				sessionStorage.setItem('todaySmsMoney', data.todaySmsMoney);
				sessionStorage.setItem('todayReminderMoney', data.todayReminderMoney);
				sessionStorage.setItem('todayMemberMoney', data.todayMemberMoney);
				sessionStorage.setItem('yesterSmsMoney', data.yesterSmsMoney);
				sessionStorage.setItem('yesterReminderMoney', data.yesterReminderMoney);
				sessionStorage.setItem('yesterMemberMoney', data.yesterMemberMoney);
				sessionStorage.setItem('totalTrade', data.totalTrade);
				sessionStorage.setItem('waitPayTrade', data.waitPayTrade);
				sessionStorage.setItem('succConsignTrade', data.succConsignTrade);
				sessionStorage.setItem('waitConsignTrade', data.waitConsignTrade);
				sessionStorage.setItem('refundTrade', data.refundTrade);
				sessionStorage.setItem('succPayment', data.succPayment);
				sessionStorage.setItem('id', 1);
				showData();
			},
			dataType : 'json'
		});
		
	}
	$(function(){
		showData();
		memberInformation();
		
	});
	function memberInformation(){
		var nowDate=new Date();
		if(localStorage.getItem('memberInformationTime')){
			$('.loddingImg2').parent('div').hide();
			var oldDate=localStorage.getItem('memberInformationTime');
			var json=$.parseJSON(localStorage.getItem('memberInformationData'));
			var data=json;
			$('#storeRenNum').text(data.memberCount);
			
			if(data.receiveInfos){
				for(var i=0;i<data.receiveInfos.length;i++){
					var aLi=$('<li  class="clearfix"></li>');
					aLi.html(
						'<i>'+data.receiveInfos[i].createdDate+'</i>'	
						+'<span>'+data.receiveInfos[i].sendPhone+':</span>'
						+'<em>'+data.receiveInfos[i].content+'</em>'
						+'<a href="${ctx}/memberInteraction/sllerSmsReceiveAll">查看详情</a>'
					);
					$('#member-rollUl').append(aLi);
				}
			}
			if(nowDate-oldDate>3600000){
				localStorage.removeItem('memberInformationTime');
				localStorage.removeItem('memberInformationData');
			}
		}else{
			
			localStorage.setItem('memberInformationTime',nowDate.getTime());
			$('.member-entranceBox').hide();
			
				$.ajax({
					url:'${ctx}/home/getIndexInfo',
					type:'post',
					data:{
						userId:$('#userId').val()
					},
					success:function(data){
						$('.loddingImg2').parent('div').hide();
						if(data.rc==100){
							var data=data.data;
							$('.member-entranceBox').show();
							console.log(data);
							
							var str=JSON.stringify(data);
							localStorage.setItem('memberInformationData',str);
							
							$('#storeRenNum').text(data.memberCount);
							
							if(data.receiveInfos){
								for(var i=0;i<data.receiveInfos.length;i++){
									var aLi=$('<li  class="clearfix"></li>');
									aLi.html(
										'<i>'+data.receiveInfos[i].createdDate+'</i>'	
										+'<span>'+data.receiveInfos[i].sendPhone+':</span>'
										+'<em>'+data.receiveInfos[i].content+'</em>'
										+'<a href="${ctx}/memberInteraction/sllerSmsReceiveAll">查看详情</a>'
									);
									$('#member-rollUl').append(aLi);
								}
							}
						}else{
							var aDiv=$('<div style="height:3.6rem;line-height:3.6rem;text-align:center" class="memberInformationError">加载失败，请刷新重新加载！</div>');
							$('#member-entrance').append(aDiv);
							localStorage.removeItem('memberInformationTime');
						}
						
						
					}
				})
			
			
		}
	}
	function showData(){
		//订单数
		var totalTrade=parseInt(sessionStorage.getItem('totalTrade'));
		//待付款订单数
		var waitPayTrade=parseInt(sessionStorage.getItem('waitPayTrade'));
		//已付款订单数
		var succPayTrade=parseInt(sessionStorage.getItem('succConsignTrade'));
		//待发货订单数
		var waitConsignTrade=parseInt(sessionStorage.getItem('waitConsignTrade'));
		//退款中订单数
		var refundTrade=parseInt(sessionStorage.getItem('refundTrade'));
		//已付款金额
		var succPayment=parseInt(sessionStorage.getItem('succPayment'));
		//本月短信消费金额
		var monthSmsMoney=sessionStorage.getItem('monthSmsMoney');
		//本月催付挽回金额
		var monthReminderMoney=sessionStorage.getItem('monthReminderMoney');
		//本月营销金额
		var monthMemberMoney=sessionStorage.getItem('monthMemberMoney');
		//今天短信消费金额
		var todaySmsMoney=sessionStorage.getItem('todaySmsMoney');
		//今天催付挽回金额
		var todayReminderMoney=sessionStorage.getItem('todayReminderMoney');
		//今天会员营销金额
		var todayMemberMoney=sessionStorage.getItem('todayMemberMoney');
		//昨日短信消费金额
		var yesterSmsMoney=sessionStorage.getItem('yesterSmsMoney');
		//昨日催付挽回金额
		var yesterReminderMoney=sessionStorage.getItem('yesterReminderMoney');
		//昨日会员营销金额
		var yesterMemberMoney=sessionStorage.getItem('yesterMemberMoney');
		
		
		if(!effectAnalysis()){
			$('#effect-analysis').prop({
				title:'由于初次没有数据，此处显示为假数据'
			});
			//本月短信消费金额
			monthSmsMoney=2000;
			//本月催付挽回金额
			monthReminderMoney=7000;
			//本月营销金额
			monthMemberMoney=9000;
			//今天短信消费金额
			todaySmsMoney=1000;
			//今天催付挽回金额
			todayReminderMoney=5000;
			//今天会员营销金额
			todayMemberMoney=6000;
			//昨日短信消费金额
			yesterSmsMoney=1000;
			//昨日催付挽回金额
			yesterReminderMoney=2000;
			//昨日会员营销金额
			yesterMemberMoney=3000;
		}
		//日期计算
		var oDate=new Date();
		var oYear=oDate.getFullYear();
		var oMon=oDate.getMonth()+1;
		var oDateDay=oDate.getDate();
		
		if(sessionStorage.getItem('id')){
			showHiggcharts({
				id:'#effect-analysis',
				nowMon:oYear+'/'+oMon,
				nowDate:oYear+'/'+oMon+'/'+oDateDay,
				yesterday:oYear+'/'+oMon+'/'+(oDateDay-1),
				nmonthSmsMoney:monthSmsMoney,
				nmonthReminderMoney:monthReminderMoney,
				nmonthMemberMoney:monthMemberMoney,
				ntodaySmsMoney:todaySmsMoney,
				ntodayReminderMoney:todayReminderMoney,
				ntodayMemberMoney:todayMemberMoney,
				nyesterSmsMoney:yesterSmsMoney,
				nyesterReminderMoney:yesterReminderMoney,
				nyesterMemberMoney:yesterMemberMoney
			});
			if(totalTrade.toString().length>=5){
				$('.orderDiv p').text(parseInt((totalTrade/10000))+'万');
			}else{
				$('.orderDiv p').text(totalTrade);
			}
			if(waitPayTrade.toString().length>=5){
				$('.pending-paymentDiv p').text(parseInt((waitPayTrade/10000))+'万');
			}else{
				$('.pending-paymentDiv p').text(waitPayTrade);
			}
			if(succPayTrade.toString().length>=5){
				$('.shippedDiv p').text(parseInt((succPayTrade/10000))+'万');
			}else{
				$('.shippedDiv p').text(succPayTrade);
			}
			if(succPayment.toString().length>=5){
				$('.payment-amountDiv p').text(parseInt((succPayment/10000))+'万');
			}else{
				$('.payment-amountDiv p').text(succPayment);
			}
			if(waitConsignTrade.toString().length>=5){
				$('.paidDiv p').text(parseInt((waitConsignTrade/10000))+'万');
			}else{
				$('.paidDiv p').text(waitConsignTrade);
			}
			if(refundTrade.toString().length>=5){
				$('.refundDiv p').text(parseInt((refundTrade/10000))+'万');
			}else{
				$('.refundDiv p').text(refundTrade);
			}
			$('.loadingImg').parent().hide();
		}
		
		//页面大小改变调用效果分析higgchrats图形
		$(window).resize(function(){
			if(sessionStorage.getItem('totalTrade')){
				showHiggcharts({
					id:'#effect-analysis',
					nowMon:oYear+'/'+oMon,
					nowDate:oYear+'/'+oMon+'/'+oDateDay,
					yesterday:oYear+'/'+oMon+'/'+(oDateDay-1),
					nmonthSmsMoney:monthSmsMoney,
					nmonthReminderMoney:monthReminderMoney,
					nmonthMemberMoney:monthMemberMoney,
					ntodaySmsMoney:todaySmsMoney,
					ntodayReminderMoney:todayReminderMoney,
					ntodayMemberMoney:todayMemberMoney,
					nyesterSmsMoney:yesterSmsMoney,
					nyesterReminderMoney:yesterReminderMoney,
					nyesterMemberMoney:yesterMemberMoney
				});
				$('.loadingImg').parent().hide();
			}else{
				$('.loadingImg').parent().show();
			}
		});
	}
	
	//封装higgcharts调用
	function showHiggcharts(json){
		console.log(json)
		var thisWidth=$(json.id).width();
		var thisHeight=$(json.id).height();
		
		var nowMon=json.nowMon;
		var nowDate=json.nowDate;
		var yesterday=json.yesterday;
		
		var smonthSmsMoney=Number(json.nmonthSmsMoney);
		var smonthReminderMoney=Number(json.nmonthReminderMoney);
		var smonthMemberMoney=Number(json.nmonthMemberMoney);
		var stodaySmsMoney=Number(json.ntodaySmsMoney);
		var stodayReminderMoney=Number(json.ntodayReminderMoney);
		var stodayMemberMoney=Number(json.ntodayMemberMoney);
		var syesterSmsMoney=Number(json.nyesterSmsMoney);
		var syesterReminderMoney=Number(json.nyesterReminderMoney);
		var syesterMemberMoney=Number(json.nyesterMemberMoney);
		
		
		$(json.id).highcharts({
	        chart: {
	            type: 'column',
	            width:thisWidth-20,
	            height:thisHeight
	        },
	        title: {
	            text: ''
	        },
//			        subtitle: {
//			            text: '数据来源: WorldClimate.com'
//			        },
	        xAxis: {
	            categories: [
	                '本月数据',
	                '昨日数据',
	                '今日数据'
	            ],
	            crosshair: true
	        },
	        legend:{
	        	align:'right',
	        	verticalAlign: 'top'
	        },
	        credits: {
			    enabled:false
			},
	        yAxis: {
	            min: 0,
	            title: {
	                text: ''
	            }
	        },
	        tooltip: {
	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	            '<td style="padding:0"><b>{point.y:.2f} 元</b></td></tr>',
	            footerFormat: '</table>',
	            shared: true,
	            useHTML: true
	        },
	        plotOptions: {
	            column: {
	                pointPadding: 0.1,
	                borderWidth: 0
	            }
	        },
	        series: [{
	            name: '短信消费金额',
	            color: '#fa7946',
	            data: [smonthSmsMoney,syesterSmsMoney,stodaySmsMoney ]
	        }, {
	            name: '催付挽回金额',
	            color: '#f43c3c',
	            data: [smonthReminderMoney,syesterReminderMoney,stodayReminderMoney ]
	        }, {
	            name: '会员营销转化金额	',
	            color: '#52abe3',
	            data: [smonthMemberMoney,syesterMemberMoney,stodayMemberMoney ]
	        }]
	    });
	    
	}
	function effectAnalysis(json){
		//本月短信消费金额
		var monthSmsMoney=sessionStorage.getItem('monthSmsMoney');
		//本月催付挽回金额
		var monthReminderMoney=sessionStorage.getItem('monthReminderMoney');
		//本月营销金额
		var monthMemberMoney=sessionStorage.getItem('monthMemberMoney');
		//今天短信消费金额
		var todaySmsMoney=sessionStorage.getItem('todaySmsMoney');
		//今天催付挽回金额
		var todayReminderMoney=sessionStorage.getItem('todayReminderMoney');
		//今天会员营销金额
		var todayMemberMoney=sessionStorage.getItem('todayMemberMoney');
		//昨日短信消费金额
		var yesterSmsMoney=sessionStorage.getItem('yesterSmsMoney');
		//昨日催付挽回金额
		var yesterReminderMoney=sessionStorage.getItem('yesterReminderMoney');
		//昨日会员营销金额
		var yesterMemberMoney=sessionStorage.getItem('yesterMemberMoney');
		var bOk=false;
		if(monthSmsMoney!=''&&monthSmsMoney!=0){
			bOk=true;
		}else if(monthReminderMoney!=''&&monthReminderMoney!=0){
			bOk=true;
		}else if(monthMemberMoney!=''&&monthMemberMoney!=0){
			bOk=true;
		}else if(todaySmsMoney!=''&&todaySmsMoney!=0){
			bOk=true;
		}else if(todayReminderMoney!=''&&todayReminderMoney!=0){
			bOk=true;
		}else if(todayMemberMoney!=''&&todayMemberMoney!=0){
			bOk=true;
		}else if(yesterSmsMoney!=''&&yesterSmsMoney!=0){
			bOk=true;
		}else if(yesterReminderMoney!=''&&yesterReminderMoney!=0){
			bOk=true;
		}else if(yesterMemberMoney!=''&&yesterMemberMoney!=0){
			bOk=true;
		}
		return bOk;
	}
// 	$('.hdIndexBannerBox i').click(function(){
// 		$('.hdIndexBannerBox').hide();
// 		$('#markBgHd').hide();
// 	})
</script>


<script type="text/javascript" src="${ctx}/crm/js/index2.js"></script>
</html>