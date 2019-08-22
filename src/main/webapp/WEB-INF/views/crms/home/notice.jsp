<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
	<title>常见问题</title>
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

	<script type="text/javascript" src="${ctx}/crm/js/QandA.js" ></script>
		
</head>
<body>
	<%@ include file="/WEB-INF/views/crms/header/top.jsp"%>
	<%@ include file="/WEB-INF/views/crms/header/header.jsp"%>
	<div class="w1903 h100_">
			<!-------------------------左部，侧边栏------------------------->
			<div class="load_left"></div>
	
	
			<!-------------------------右部------------------------->
			<div class="w1700 m_l200">
				<!--------------------顶部导航栏-------------------->
				<div class="load_top"></div>
				
				
				<!--------------------主要内容区-------------------->
				<div class="w1660 bgc_fff m_t90 m_l30">
                
                    <div class="w1610 h50 l_h50 font16">
                        <div class="f_l p_l15 bor_left_1d9dd9_4px c_384856 font16">常见问题</div>
                    </div>
                    
                    <div class="gonggao">
                        <div class="w1640 h80 l_h80 bgc_f1f3f7 font24 c_384856">
                            <div class="m_l40">常见问题</div>
                        </div>
                        <div class="w1600 p_t50 p_b50 p_l50 bgc_fff">
                        	
                        	
                        
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="huiyuandengjihuafen">会员等级划分</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">1、会员折扣设置完成显示在哪边？交易记录里如何显示？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
	      								客云的会员等级折扣和官方保持同步。当客户登录淘宝，点开宝贝详情页，就可查看对应等级折扣。
	      							</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">2、客户等级等级划分里的交易额和交易量可以设定一个吗？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">可以。二者是或者的关系，可任意设定一个或者两个。</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">3、可以取消会员折扣吗？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">可以。直接在【客户管理】-【客户等级划分】-【取消会员折扣】</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">4、如果宝贝既有会员折扣，又有限时折扣，客户享受哪个折扣？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">如果一款宝贝既有会员折扣，又有限时折扣，那么客户享受的是最低折扣价。</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">5、为什么我没有设置会员等级折扣，订购客云后会员等级折扣就自动设置好了？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">我们客云CRM的会员等级折扣都是和官方或者其他第三方软件设定的会员等级折扣是同步的。如果在官方或者其他第三方软件有设定过会员等级折扣，那么订购我们软件之后，会员等级折扣也会自动同步过来。</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">6、如果在其他软件里设置了会员折扣，和客云这边冲突吗？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">不冲突。以最新设置的为标准。</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">7、在客云crm软件或者客户管理里面添加了黑名单，还可以享受会员折扣吗？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">在客云crm或者客户管理中被加入黑名单的会员可以在卖家店铺里进行正常的购物，但无法享受会员折扣优惠。</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">8、会员等级折扣设置好是实时更新的吗？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">是的。会员等级设置好之后，有24小时的更新时间。当买家订单交易成功后，满足不同等级的条件，系统会自动更新到对应等级。如果比较着急的亲们，可以直接在我们客云这设定个会员归类，系统会直接更新会员等级折扣。</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">9、为什么有些客户是普通会员，有些客户是店铺会员？</p>
	    							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">因为淘宝的会员等级调整，以前只有普通、高级、vip至尊vip这四个等级。2014年6月份新增了店铺客户这个会员等级 （所有买过您宝贝的买家 ）。现在店铺设置的条件，只要不满足普通、高级、vip和至尊vip这四个条件的客户，都会自动划分为店铺会员。</p>
	      							
	      						</div>
							
							</div>


                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="huiyuanfenzu">会员分组</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">1、 客户交易额和交易量是累计的吗？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									是的，客户资料这边的交易额和交易量都是累计的。
	      							</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">2、 这些客户是开店以来的吗？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
	      								是的，09年开店以来的所有客户数据。
	      							</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">3、 支持自定义客户分组吗？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
										支持的，软件里有系统分组，也支持自定义客户分组。
	      							</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">4、 自定义创建好的分组可以直接发送短信吗？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
	      								可以。直接在客户分组——会员营销操作，系统自动跳转会员短信群发页面。
	      							</p>
	      							
	      						</div>
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">5、客户分组数量为什么一直不变？</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
										长时间未登录软件，系统有些数据不会自动更新。亲这边只需要点击分组信息当中的刷新按钮，客户数据就会自动更新过来。
	      							</p>
	      							
	      						</div>
							
							</div>




                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="huiyuanhudong">会员互动</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										1、 会员互动的短信是如何实现营销效果的？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									自动接收买家回复短信内容或直接下发短信给买家，实现一对一交流互动。 
	      							
	      							</p>
	      							
	      						</div>
							
							</div>




                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="heimingdanguanli">黑名单管理</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										1、 可以设置会员黑名单吗？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									可以。在【会员管理】--【黑名单管理】界面，可以查看到黑名单管理功能。设置黑名单以后，在卖家发送任何短信的时候，后台数据库自动过滤掉黑名单客户，不向黑名单中的手机号码发送短信。 
	      							
	      							</p>
	      							
	      						</div>
							
							</div>





                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="duanxinyingxiao">短信营销</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										1、支持订单短信群发吗？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
										支持。
									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										2、短信内容支持自定义吗？一条短信多少个字？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_red padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									可以。一条短信70个字，但是一条短信内容超过70个字每条短信按照67个字进行计费，例如 一条短信70个字 计费1条，一条短信71个字计费2条，一条短信140个字计费3条 一条短信134个字计费2条；其中汉字、英文字母、阿拉伯数字、标点符号均算作一个字。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										3、 短信发送的号码是什么开头的？ 到达率如何？    							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									移动、电信、联通三网都是106号码开头，到达率98%以上，不到达也不计费。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										4、短信发送有时间限制吗？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									为了不影响客户的休息时间，所有短信一律是早上9点至晚上9点期间发送，如果晚上9点后有发送任务，也会统一在次日早上9点后发出，物流到货提醒是早上8点开始发送
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										5、短信中可以添加链接吗？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									可以。如果加链接的话。可以生成短网址，占短信字数少。链接复制到短信内容中，前后各加一个空格。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										6、可以过滤中差评客户吗？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									可以，系统自动过滤近半年中差评客户。如果有不想发的客户，也可以添加到短信黑名单中，短信群发自动过滤不发。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										7、为什么筛选群发的客户那么少？	      							
	      							
	      							</p>
	      							<div class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
	      								<p>（1）因为淘宝不提供3个月前的客户手机号码的接口，系统只显示近三个月的客户。</p>
											
										<p>（2）近5天有群发短信，系统默认的自动过滤近5天的客户，只需在群发短信这边勾选屏蔽项。</p>

      									
	      							</div>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										8、为什么短信充值和消耗数量兑不上？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									因为短信消耗记录数据量比较大，我们入住淘宝的聚石塔服务器 数据量过大，会自动进行数据备份，所以短信消耗记录 我们这边只能查看到近一个月的数据，超过一个月的，要技术后台联系小二，调取备份数据给亲们。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										9、有短信模板吗？是否支持自定义添加模板？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									有的。针对不同节日、场景，我们都设置了短信模板，支持添加和修改。也可以添加自己短信模板。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										10、营销短信发送注意事项	      							
	      							
	      							</p>
	      							<div class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									<p>a. 如果短信内容中添加短信链接，可以生成下短网址。链接前后各添加一个空格。</p>
										<p>b. 短信内容中打折、促销、包邮、秒杀等词汇尽量少些，群发之前可以先测试下。</p>
										<p>c. 注意短信发送时间，避免打扰买家休息。</p>
										<p>d. 短信群发尽量在活动前2~3天发送，确保短信送达，做活动预热。</p>
										<p>e. 如有不想发送短信的客户，可提前加入短信黑名单，系统自动过滤处理不发。</p>

	      							</div>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										11、营销短信会重复发送吗？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									不会重复。系统会自动过滤重复客户，不多发，也不计费。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										12、营销短信支持定时发送吗？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									可以。在【短信营销】--【会员短信群发】界面，可以查看到定时发送功能。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										13、不想发送的客户可以不发吗？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									可以。如果有不需要发送的客户，可以直接添加到短信黑名单。【短信营销】——【短信黑名单】中添加即可。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										14、为什么显示发送成功，短信没收到？	      							
	      							
	      							</p>
	      							<div class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									
      								<p>我们客云三网都是106优质通道，短信确保是正常发送和接收 。如短信收不到，亲们先别着急，可先看以下几种短信收不到的情况分析：</p>
									<p>（1 ） 短信被手机拦截软件拦截。目前大部分手机都是智能手机，比如360安全卫士或者手机管家，或者系统自带的拦截软件（小米手机等）。短信内容中促销词汇过多，会直接被拦截软件拦截。关键词：打折、促销、包邮、秒杀等。</p>
										促销词汇转换：<br />
										封顶——低至<br />
										优惠券——代金券<br />
										双11——双十一<br />
										全场——满场<br />
										若短信内容中加链接，店铺名是xx旗舰店、xx专卖店要换成xx官方店<br />
										抢购——疯抢<br />
										包邮——免邮、包油<br />
										多款——多样<br />
										新品——上新<br />
										详情——详见<br />
										低价——底价<br />
										9折起——9折<br />
										优惠——划算<br />
										折上折 直接去掉<br />
										红包——荭包<br />
										促销——大促<br />
										限量版、数量有限——限数疯抢<br />
										仅限今天——就在今天<br />
										赶紧疯抢——赶紧下手<br />
										老客户——老顾客<br />
										领取——领<br />
										大卖——大促<br />
										礼物——li物<br />
										恭候咨询——恭候到来<br />
										限量——定量<br />
										秒杀——miao杀<br />
										折扣——哲扣 或者  zhe扣<br />
										疯抢——疯购<br />
										9.9元——9块9<br />
										<p>若短信内容中加链接，店铺名是xx旗舰店、xx专卖店要换成xx官方店。</p>
										<p>注：以上关键词转换，我们只提取了部分，拦截软件的拦截关键词都在不断更新，若有没提出的关键词，亲们可自行测试。试将短信内容一句一句测试。第一句测试收到加上后面一句测。以此类推。</p>
										<p>Ps: 如果有客户不清楚自己手机是否有拦截软件，可以先测试下简单的系统内容（亲爱的，我们准备了丰富的年货，赶快行动起来噢！）看下是否可以正常接收。如果正常接收，那说明自己编辑的那条短信被拦截了。可检查下手机。</p>
										<p>（2）  手机终端问题。比如：手机短信中心号码不正确、第三方软件冲突所致、被转移垃圾箱、场合信号不稳定、欠费停用、收件箱溢出、机身内存不足、手机黑名单短信电话限制功能开启、手机本身质量问题山ZAI机。</p>

      									
      									
	      							</div>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										15、可以添加自己号码做短信测试吗？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									可以。短信群发之前，可在【短信营销】--【指定号码发送】这边，输入号码，做短信测试。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										16、为什么手机会重复收到多条短信？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									遇到这种情况，亲们不用慌张着急。我们客云一直使用106优质通道，确保短信到达率和时效性，不会乱发乱扣费。若客户收到多条短信，是客户手机端信号问题，只计费一条。我们短信运营商当接收到短信提交任务，会直接发送给客户手机卡上，如果客户手机卡未接受，运营商会一直提交发送，直到手机卡有接收返回状态为止。遇到这种情况，可以先让买家重启手机，看下是否正常，若是重启之后，还是收到多条，可把手机卡放到另外正常手机端上装下即可。
      									
	      							</p>
	      							
	      						</div>
							
							</div>








                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="shoujihaoshezhi">手机号设置</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										1、短信余额预警的手机号在哪里更改？
										
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
										可在【后台管理】--【手机号设置】这边，点击修改，输入号码，确定修改。
									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										2、如何开启短信剩余量提醒？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_red padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									当软件里面的短信余额为0，正常的营销短信发送会直接停止！积累的多天的客户体验，直接从100降至为0.让亲情何以堪？为了杜绝此类事情的发生，应当直接从源头上避免。一键开启短信剩余量提醒，方便、快捷，客户良好体验一直都在。进入【后台管理】--【手机号设置】。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										3、 短信发送的号码是什么开头的？ 到达率如何？    							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									移动、电信、联通三网都是106号码开头，到达率98%以上，不到达也不计费。
      									
	      							</p>
	      							
	      						</div>
	      						
	      						
							</div>








                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="duodianpubangding">多店铺绑定</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										提示：功能正在逐渐开放，请亲们尽请期待！
										
	      							</p>
	      							
	      						</div>
	      						
	      						
							</div>








                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="dingdanliebiao">订单列表</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										用户可以查询订单历史记录。 路径：店铺数据--订单查询
										
	      							</p>
	      							
	      						</div>
	      						
							</div>








                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="duanxinfasongjilu">短信发送记录</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										可以查询所有通过本软件发送的短信信息，根据不同短信类型区分查询，还包括短信账单（条数与金额）统计。路径：店铺数据--短信发送记录。
										
	      							</p>
	      							
	      						</div>
	      						
							</div>








                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="dianpushuju">店铺数据</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
	      								依据店铺的实际情况，全面展现数据，帮助减轻卖家店铺成本，提高后台管理运营的效率。路径：店铺数据--店铺数据。
	      								
	      							</p>
	      							
	      						</div>
	      						
							</div>








                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="caozuorizhi">操作日志</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
	      								操作日志可以记录下系统所产生的所有行为，并按照规范模板表达出来。有效记录卖家操作行为习惯，制定更符合自己的运营方式，降低店家工作负荷。
	      								
	      							</p>
	      							
	      						</div>
	      						
							</div>








                        	
                        	
                        	
                        	
                        	
                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30" id="qita">其他</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										1、如何订购客云CRM及注意事项？
										
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
										1、订购成功后，进入淘宝授权页面，点击最下方‘授权’按钮，可以直接进入客云产品页面 。<br />
										2、购买客云: 登录淘宝à卖家中心à卖家服务市场。搜索‘客云’订购我们的服务。<br />
										3、退出页面后，下次登录可以直接在淘宝卖家中心à我购买的服务处点击客云图标登录。<br />

										
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										2、如何充值短信？充值记录在哪边查询？？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_red padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									方式一、进入【首页】-【充值】页面。选择充值项目，点击立即充值付款即可完成充值。<br />
										方式二、联系在线客服客云软件，客服提供公司支付宝或银行卡打款充值，打款完成，截图给客服，客服帮忙充值到账。

      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										3、 为什么充值了短信没有到账？    							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									当短信充值好之后，点击软件右上角退出重新授权登录下软件，短信会立即到账的。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										4、订购软件后短信什么时候赠送到账？    							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									购买之后短信是立即到账，若一次性充值短信量比较大，可以联系软件客服咨询价格。<span class="c_red">特别注意：软件服务一旦到期，即使有剩余短信在软件中，软件也是不可使用的！</span>这种情况下，亲可以续费软件，剩余短信还存在于软件，可继续正常使用。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										5、店铺签名在哪里修改？    							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									方式一、进入含有短信发送的页面。选择【短信渠道】--【自定义】，可编辑店铺签名。<br />
										方式二、联系在线客服帮您后台修改。

	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										6、【一键短网址】功能介绍？    							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									1、 功能名称：【一键短网址】应用，提供订单相关短网址，直接作为变量加入短信内容中，买家手机端收到短信后直接点击短网址可一键完成付款、确认收货、评价、及查看物流信息等操作，更直接快速完成订单相关操作，买家体验升级，更能有效提高卖家店铺DSR评分。
										<br />具体功能模块包括：【付款链接】【物流链接】【确认收货链接】【评价链接】相关应用。
										<br />1)【付款链接】：可在催付提醒编辑短信内容过程中直接插入付款链接，买家收到短信直接点击链接可手机端直接进行付款操作，提高催付转化率。
										<br />2)【物流链接】：可以在发货提醒、同城提醒、签收提醒短信编辑内容中增加物流链接，买家手机端收到可直接点击链接查看自己订单的物流流转信息。
										<br />3)【确认收货链接】：可在回款提醒功能短信编辑中直接插入短网址链接，买家收到短信直接点击链接可手机端直接进行确认收货操作，更快速直接帮助卖家回笼资金。
										<br />4)【评价链接】: 在催好评功能短信内容中直接加入评价链接，买家手机端收到可直接点击链接对购买宝贝完成评价操作。
										<br />2、所在位置： 订单中心各个模板短信编辑框
										<br />3、操作方法：以下以确认收货链接为例介绍
										<br />1）点击【订单中心】——【回款提醒】，进入短信设置页面，鼠标放置 “插入确认收货链接”右边会展示手机端 预览效果图
										<br />2）点击“插入确认收货链接”即可作为变量直接插入链接至短信内容里，正式发送时会自动替换为对应的订单真实确认收货链接。注意右边短信预览字数，加入网址后自动增加21个字符（短网址20个字符，默认后尾1个空格，共计21个字符）。
										<br />保存即可
										<br />4、短网址常见问题解答：
										<br />1）点击测试发送，手机收到网址打开后为什么显示测试模板页，不是真实的链接不能操作？
										<br />答：短网址是作为变量直接插入短信内容中，测试发送的是短网址变量模板如：http://tra.so/xxxxxx正式发送网址后尾xxxxxx会自动替换为随机6位英文字母，与买家真实订单对应。测试发送模板无真实订单信息所以无法打开查看，提供测试模板供参考。
										<br />2）短网址插入后怎么计算字数的？
										<br />答：网址固定格式http://tra.so/xxxxxx 共计20个字符，插入后为确保手机端打开无异常会默认后尾加1个空格，共计21个字符。会自动计算在短信字数内，可查看预览字数。

      									
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										7、【多店铺绑定】功能介绍？    							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									1、功能名称：【多店铺绑定】
										<br />2、所在位置：首页——后台管理——多店铺绑定
										<br />3、功能亮点：绑定成功后，可以在主店铺里同时管理多个子店铺，实现多店铺管理。
										<br />4、操作方法：
										<br />1）登陆店铺A，输入需要授权给的店铺旺旺名B：只有订购过本产品的店铺才可以进行多店铺绑定。
										<br />2） 点击申请绑定，会生成关联ID及验证码，验证码会发送至当前店铺A淘宝预留手机号上。关联ID需要复制备用。
										<br />3) 登录店铺B，进入后台管理——多店铺绑定。可以看到店铺A发送的多店铺绑定申请，输入关联ID及验证码点击“确认绑定”完成绑定操作。
										<br />5、绑定成功后，店铺B自动默认为主店铺，店铺A将作为子店铺不可登陆。需要在主店铺中统一管理子店铺。店铺A作为子店铺无法登陆。如有余额可联系客服转至主店铺使用。
										<br />注意：绑定成功后需要主店铺重新登陆才可以生效。
										<br />6、多店铺绑定常见问题解答：
										<br />1）多店铺绑定怎么设置哪个是主店铺呢？
										<br />答：店铺A授权至店铺B，则店铺B自动默认为主店铺，店铺A会作为子店铺，绑定成功后店铺A将无法登陆，需要在店铺B主店铺中统一管理，一个主店铺可以绑定多个子店铺。
										<br />2）已绑定成功为什么看不到多店铺切换按钮？
										<br />答：绑定成功后需要重新登陆才可以生效。

      									
      									
	      							</p>
	      							
	      						</div>
	      						
	      						
							</div>



                        	<div class="w1100 border_f2f2f2 padding10 m_b30">
	                        	<p class="font18 c_384856 m_b30"id="duanxinxiangguan">短信相关</p>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										1、短信如何收费的？内容字数有没有限制？是否支持上下行？
										
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
										短信是按条收费具体需要咨询客户经理。
										<span class="c_red">
											一条短信70个字，但是一条短信内容超过70个字每条短信按照67个字进行计费，例如 一条短信70个字 计费1条，一条短信71个字计费2条，一条短信140个字计费3条 一条短信134个字计费2条；其中汉字、英文字母、阿拉伯数字、标点符号均算作一个字。支持上下行。
										</span>
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										2、短信通道下发接收到的号码是什么？到达率如何？	      							
	      							
	      							</p>
	      							<p class="display_none answer c_red padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									短信发送显示号码统一是106开头的号码，且到达率在98%左右。 
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										3、定时短信已发送，未到发送时间能否拦截呢？    							
	      							
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									可以拦截。需要在定时时间的至少前30分钟联系客服帮您后台操作取消。
      									
	      							</p>
	      							
	      						</div>
	                        	
	      						<div class="m_b20 cursor_">
	      							
	      							<p class="c_888888 question">
	      								
										4、如何查询短信消耗记录？
									
	      							</p>
	      							<p class="display_none answer c_333333 padding10_15 w1000 bgc_f2f2f2 font14 border_t_dddddd p_b70">
      									
      									方式一、进入【店铺数据】-【短信发送记录】页面。选择时间范围，查询记录。
										<br/>方式二、联系客云在线客服，客服帮忙查询通知。


      									
	      							</p>
	      							
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
