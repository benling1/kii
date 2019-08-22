<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>营销中心-会员短信群发</title>
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
	<script type="text/javascript" src="${ctx}/crm/js/vip_text.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/util.js" ></script>
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
				<div class="w1645 m_t70 m_l30 m_b30">
					<div class="w100_ h50 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							营销中心
						</div>
						<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
							<a class="c_384856" href="${ctx}/marketingCenter">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									会员短信群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/smsSendAppoint/appointNumberSend">
								<div class="f_l w140 text-center cursor_ ">
									指定号码群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/marketingCenter/list?label=1">
								<div class="f_l w140 text-center cursor_">
									订单短信群发
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/marketingCenter/smsBlack">
								<div class="f_l w140 text-center cursor_">
									短信黑名单
								</div>
							</a>
							<a class="c_384856" href="${ctx}/smsSendRecord/marketingCenter/messageSendHistory">
								<div class="f_l w140 text-center cursor_">
									短信发送记录
								</div>
							</a>
							<a class="c_384856" href="${ctx}/member/msgSendRecord">
								<div class="f_l w200 text-center cursor_">
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
					</div>
					
					
					<!---------------会员短信群发--------------->
					<div class=" ">
						<!----------上部---------->
						<div class="w1605 h100 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								会员短信群发
							</div>
							<!---------------描述--------------->
							<div class="font14">
								批量录入手机号码，发送短信,自动过滤手机格式不正确,重复号码，短信常规发送时间为:早8-晚9,年货节期间不限制时间
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1605 bgc_fff p_t35 p_l40 p_b40 ddzx_in_in position_relative">
							
							<div class="">
								<div class="f_l lh40 c_384856">
									群发对象：
								</div>
								<div class="f_l w140 sifting_btn tk border_00a0e9 c_00a0e9 cursor_ h40 lh40 text-center b_radius5">
									点击选择发送对象
								</div>
								<div class="clear"></div>
							</div>
							
							<!--------操作选项-------->
							<div class="font14 h52 m_b15">
								<div class="f_l font16 lh52 lh52 c_384856">
									短信渠道：
								</div>
								<div class="f_l m_r60 m_t16 qianming_none">
							    	<div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							    		<img class="cursor_ one_check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="m_l10 f_l font14 c_384856">
							    		淘宝
							    	</div>
								</div>
								<div class="f_l m_t16 m_r60 qianming_none">
							    	<div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							    		<img class="cursor_ one_check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="m_l10 f_l font14 c_384856">
							    		京东
							    	</div>
								</div>
								<div class="f_l m_t16 m_r60 qianming_none">
							    	<div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							    		<img class="cursor_ one_check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="m_l10 f_l font14 c_384856">
							    		天猫
							    	</div>
								</div>
								<div class="f_l m_t16 qianming_done">
							    	<div class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
							    		<img class="cursor_ one_check_ display_none w20 h20" src="${ctx}/crm/images/black_check.png" />
							    	</div>
							    	<div class="m_l10 f_l font14 c_384856">
							    		自定义
							    	</div>
								</div>
								
							</div>
							
							<div class="h52 lh52">
								
								<div class="f_l lh52 c_384856">
									短信签名：
								</div>
								<input class="bgc_f4f6fa qianming_ border0 w215 h50 f_l m_r10" />
								
							</div>
							
							
							
							<div class="h430 m_t30">
								<div class="f_l lh40 c_384856">
									短信内容：
								</div>
								<div class="f_l b_radius5 bgc_f4f6fa position_relative">
									<textarea maxlength="500" class="text_area w460 h380 border0 bgc_f4f6fa b_radius5 p_l10 p_r10 p_t10"></textarea>
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
								</div>
							</div>
							
							<div class="h42 m_t25 c_384856">
								
								<div class="f_l lh40 c_384856">
									短信变量：
								</div>
								<div class="short_url_btn f_l m_r15 w110 cursor_ h40 lh40 text-center b_radius5 border_00a0e9 c_00a0e9 tk">
									生成短网址
								</div>
								<div class="f_l text_model_btn w110 cursor_ h40 lh40 text-center b_radius5 border_00a0e9 c_00a0e9 tk">
									选择模板
								</div>
							</div>
							
							<div class="h42 m_t25 c_384856">
								
								<div class="f_l lh40 c_384856">
									内容新标签：
								</div>
								<div class="f_l m_r15 w110 cursor_ h40 lh40 text-center b_radius5 border_00a0e9 c_00a0e9 tk">
									收货人
								</div>
								<p class="f_l h40 lh40 font14 c_ff6363">
									标签将自动替换为客户姓名
								</p>
							</div>
							
							
							<div class="h40 m_t15">
								<div class="f_l lh40 c_384856">
									发送时间：
								</div>
								<div class="f_l">
									<div class="f_l m_r40 set_time_none">
								    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div class="m_l10 f_l font14 c_384856 lh40">
								    		立即发送
								    	</div>
									</div>
									<div class="f_l set_time">
								    	<div class="m_t10 cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
								    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
								    	</div>
								    	<div class="m_l10 f_l font14 c_384856 lh40">
								    		定时发送
								    	</div>
									</div>
									<div class="f_l position_relative m_l10 set_time_ display_none">
										<input class="bgc_f1f3f7 b_radius5 border0 w230 p_l10 h40 m_r10" type="text" id="tser01" placeholder="请选择时间"  readonly onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
										<img style="width: 1vw;" class="position_absolute right20 top12" src="${ctx}/crm/images/date_copy.png" />
									</div>
								</div>
							</div>
							
							
							
							
							<div class="p_l180 h40 m_t70">
								
								<div class="f_l m_r15 w110 cursor_ h40 lh40 text-center b_radius5 bgc_00a0e9 c_fff">
									确定发送
								</div>
								<div class="f_l text_test_btn w110 cursor_ h40 lh40 text-center b_radius5 bgc_d4e100 c_fff">
									短信测试
								</div>
								
							</div>
							
							
							
							
							
							<div class="position_absolute iphone w327 left640 top240 h642">
								<textarea  disabled="disabled" class="text_area_copy m_l45 m_t150 w250 h370 border0"></textarea>
							</div>
							
							
							
						</div>
					
					
					</div>
					
					
				
					
				</div>	
				
					
					

					
					
					
						
				</div>
		
			</div>
	
	</div>

	
	

					

	
	

					

	

<!---------------短网址生成弹窗--------------->
<div class="w100_ h1370 rgba_000_5 position_absolute z_10 short_url top0 display_none">
	
	<div class="w740 bgc_fff margin0_auto position_fixed top200 left550  p_t20 p_l20 p_r20 p_b20">
		
		<div class="c_384856 text-center font18 h25 lh25">
			短网址生成
		</div>
		<div class="h40 p_l70 m_t40 m_b20">
			<div class="f_l m_r40 short_out">
		    	<div class="m_t10  cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
		    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
		    	</div>
		    	<div class="m_l10 f_l font14 c_384856 lh40">
		    		活动链接
		    	</div>
			</div>
			<div class="f_l m_r40 short_out">
		    	<div class="m_t10  cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
		    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
		    	</div>
		    	<div class="m_l10 f_l font14 c_384856 lh40">
		    		店铺连接
		    	</div>
			</div>
			<div class="f_l m_r40 short_out">
		    	<div class="m_t10  cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
		    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
		    	</div>
		    	<div class="m_l10 f_l font14 c_384856 lh40">
		    		商品链接
		    	</div>
			</div>
		</div>
		<input class="w570 short_in h40 bgc_f4f6fa border_0 m_l70 p_l10 m_b30" placeholder="请输入您需要转换的网址" />
		
		<div class="w570 short_in h40 font14 m_l70 text-center m_b30 display_none c_384856">
			店铺链接直接点击提交按钮即可
		</div>
		
		<input class="w570 short_in h40 bgc_f4f6fa border_0 m_l70 p_l10 m_b30 display_none" placeholder="请输入需要生成短网址的 商品链接 或 商品ID" />
		
		<div class="h42 w234 margin0_auto">
			<div class="cursor_ m_r30 f_l save_short_url w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk b_radius5">
				确定
			</div>
			<div class="cursor_ f_l save_short_url w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk b_radius5">
				取消
			</div>
		</div>
		
		<div class="m_l70 m_b10">
			<p class="font16 c_ff6363">温馨提示：</p>
			<p class="font14 c_ff6363">凡taobao.com,tmall.com,jaeapp.com生成的短网址,手机短信打开后直接呼起手机淘宝</p>
		</div>
		
	</div>
	
</div>

<!---------------短信测试弹窗--------------->
<div class="w100_ h1370 rgba_000_5 position_absolute z_10 text_test_window top0 display_none">
	
	<div class="w830 bgc_fff margin0_auto left0 right0 p_b20 position_fixed top200">
		
		<div class="cursor_ close">
			<img class="f_r m_r_15 m_t_15" src="${ctx}/crm/images/chazi.png" />
		</div>
		
		<div class="c_384856 text-center font18  p_t20">
			
			<div class="text-center m_b15">
				短信测试
			</div>
			
			
			
		</div>
		<div class="font14 h40 lh40 w750 margin0_auto m_b30">
			<div class="f_l c_384856">
				手机号码：
			</div>
			<input class="w540 p_l10 f_l h40 lh40 border0 bgc_f4f6fa" placeholder="测试号码不能超过5个，输入多个时用逗号隔开" />
			<div class="w100 f_l m_l30 cursor_ h40 lh40 bgc_00a0e9 c_fff text-center b_radius5">
				测试
			</div>
		</div>
		
		<!--------详细数据-------->
		<div class="c_384856 w750 margin0_auto">
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
			  <tr class="">
			    <td class="w90 text-center"></td>
			    <td class="w80 text-center"></td>
			    <td class="w350 text-center"></td>
			    <td class="w115 text-center"></td>
			    <td class="w130 text-center"></td>
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
		</div>
		
		<div class="c_ff6363 font14 m_t30 p_l40 w760 lh25">
			<p>温馨提示：</p>
			<p>*短信每条70个字，若使用标签，以替换后的实际长度计算。</p>
			<p>*若出现短信拦截情况,是短信中某个词被手机软件检测为垃圾短信,只需多次尝试,去除拦截词即可，如果有链接地址，必须要前后加空格噢！若有其他问题,请直接联系客服.</p>
		</div>
		
	</div>
	
</div>

<!---------------测试短信弹窗--------------->
<div class="w100_ h1370 rgba_000_5 position_absolute z_10 text_model_window top0 display_none">
	
	<div class="w1000 bgc_fff left400 p_b20 position_fixed top100">
		
		<div class="c_384856 text-center font18 h90 bgc_f1f3f7 p_t20">
			
			<div class="text-center m_b15">
				选择短信模板
			</div>
			
			<div class="m_l30 h50">
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
				</table>
			</div>
			
			
			<!--------分页-------->
	        <div class="h24 m_t22 font14 c_8493a8 m_b40 p_r30">
	            <div class="f_r w470 h24 l_h22 font12">
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
				</table>
			</div>
			
			
			<!--------分页-------->
	        <div class="h24 m_t22 font14 c_8493a8 m_b40 p_r30">
	            <div class="f_r w470 h24 l_h22 font12">
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
				</table>
			</div>
			
			
			<!--------分页-------->
	        <div class="h24 m_t22 font14 c_8493a8 m_b40 p_r30">
	            <div class="f_r w470 h24 l_h22 font12">
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
	
</div>




<!---------------筛选发送对象弹窗--------------->
<div class="w100_ h1370 rgba_000_5 position_absolute z_12 top0 sifting display_none">
	
	<div class="w1325 bgc_fff left400 h870 position_fixed top35">
		<div>
			<img class="f_r cursor_ m_r_15 m_t_15 close" src="${ctx}/crm/images/chazi.png" />
		</div>
		<div class="c_384856 text-center font18 h90 p_t20 m_b20">
			筛选发送对象
		</div>
		
		<div class="h45 font14">
			
			<div class="f_l">
				<div class="f_l m_r10 h45 lh45 c_384856 m_l55">
					客户等级:
				</div>
				<div class="wrap f_l h45 m_r10">
				    <div class="z_5 nice-select w228 h43" name="nice-select">
				    	<input class="h50" type="text" value="所有等级" readonly>
				    	<ul>
				    		<li>全部</li>
				    		<li>全部</li>
				      		<li>全部</li>
				    		<li>全部</li>
				    		<li>全部</li>
				    	</ul>
				  	</div>
				</div>
			</div>
			
			<div class="f_l">
				<div class="f_l m_r10 h45 lh45 c_384856">
					客户来源:
				</div>
				<div class="wrap f_l h45 m_r10">
				    <div class="z_5 nice-select w228 h43" name="nice-select">
				    	<input class="h50" type="text" value="全部" readonly>
				    	<ul>
				    		<li>全部</li>
				    		<li>全部</li>
				      		<li>全部</li>
				    		<li>全部</li>
				    		<li>全部</li>
				    	</ul>
				  	</div>
				</div>
			</div>
			
			<div class="f_l h45 lh45">
				
				
				<div class="f_l m_r10 h45 lh45 c_384856">
					最后交易时间:
				</div>
				
				<div class="f_l position_relative">
					<input class="bgc_f4f6fa border0 w120 p_l10 h45 m_r10" type="text" id="tser05"  readonly onclick="$.jeDate('#tser05',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
					<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
				</div>
					
				<div class="f_l">至</div>
				
				<div class="f_l position_relative">
					<input class="bgc_f4f6fa border0 w120 p_l10 h45 m_l10" type="text" id="tser06"  readonly onclick="$.jeDate('#tser06',{insTrigger:false,isTime:true,format:'YYYY-MM-DD hh:mm:ss'})">
					<img class="position_absolute right10 top15" src="${ctx}/crm/images/date_copy.png" />
				</div>
				
				
				
				
				
			</div>
			
			
			
			
		</div>
		
		<div class="h45 m_t20 font14">
			
			<div class="f_l">
				<div class="f_l m_r10 h45 lh45 c_384856 m_l70">
					交易量:
				</div>
				<input class="bgc_f4f6fa f_l w135 h45 border0 b_radius5" />
				<div class="f_l h45 lh45 m_l10 m_r10">至</div>
				<input class="bgc_f4f6fa f_l w135 h45 border0 b_radius5" />
			</div>
			
			<div class="f_l">
				<div class="f_l m_r10 h45 lh45 c_384856 m_l20">
					交易额:
				</div>
				<input class="bgc_f4f6fa f_l w135 h45 border0 b_radius5" />
				<div class="f_l h45 lh45 m_l10 m_r10">至</div>
				<input class="bgc_f4f6fa f_l w135 h45 border0 b_radius5" />
			</div>
			
			<div class="f_l h45 lh45">
				
				<div class="f_l m_r10 h45 lh45 c_384856 m_l15">
					客户昵称:
				</div>
				<input class="bgc_f4f6fa f_l w220 h45 border0 b_radius5" />
				
			</div>
			
			<div class="f_l cursor_ w140 h45 lh45 text-center font14 border_00a0e9 b_radius5 m_l35 c_00a0e9 tk">
				搜索
			</div>
			
			
			
		</div>
		
		
		<div class="h45 m_t20 font14">
			
			<div class="f_l">
				<div class="f_l m_r10 h45 lh45 c_384856 m_l55">
					信用等级:
				</div>
				<input class="bgc_f4f6fa f_l w135 h45 border0 b_radius5" />
				<div class="f_l h45 lh45 m_l10 m_r10">至</div>
				<input class="bgc_f4f6fa f_l w135 h45 border0 b_radius5" />
			</div>
			
			<div class="f_l">
				<div class="f_l m_r10 h45 lh45 c_384856 m_l20">
					客单价:
				</div>
				<input class="bgc_f4f6fa f_l w135 h45 border0 b_radius5" />
				<div class="f_l h45 lh45 m_l10 m_r10">至</div>
				<input class="bgc_f4f6fa f_l w135 h45 border0 b_radius5" />
			</div>
			
			<div class="f_l h45 lh45">
				
				<div class="f_l m_r10 h45 lh45 c_384856 m_l15">
					地区:
				</div>
				
				<div class="wrap f_l h45 m_r10">
				    <div class="nice-select z_2 w228 h43" name="nice-select">
				    	<input class="h50" type="text" value="请选择地区" readonly>
				    	<ul>
				    		<li>北京</li>
				    		<li>上海</li>
				      		<li>苏州</li>
				    		<li>浙江</li>
				    	</ul>
				  	</div>
				</div>
				
			</div>
			
			<div class="f_l w140 h45 lh45 c_00a0e9 cursor_ more_detail_btn text-center font14 m_l35">
				更多条件
			</div>
			
		</div>
		
		
		<div class="h45 m_t20 font14">
			
			
			<div class="h45 f_l">
				<div class="f_l m_r10 h45 lh45 c_384856 m_l55">
					指定商品:
				</div>
				<div class="f_l check_product_btn cursor_ w100 h45 lh45 text-center font14 border_00a0e9 b_radius5 c_00a0e9 tk">
					默认全部
				</div>
					
			</div>
			
			
			<div class="h45 f_l m_l210">
				<div class="f_l c_384856 m_r15 lh50">
					屏蔽选项:
				</div>
				<ul class="h50 f_l font14 c_384856">
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ all_can_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ all_can_check_" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		短信黑名单客户
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ all_can_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ all_can_check_" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		给过我中差评客户
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ all_can_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ all_can_check_" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		退过款的客户
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ all_can_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ all_can_check_" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		
					    		<div class="f_l">屏蔽近</div>
					    		
					    		<div class="wrap f_l h45 m_r10 m_l10">
								    <div class="nice-select w68 h43" name="nice-select">
								    	<input class="h50" type="text" value="0" readonly>
								    	<ul>
								    		<li>0</li>
								    		<li>1</li>
								      		<li>2</li>
								    		<li>3</li>
								    	</ul>
								  	</div>
								</div>
					    		
					    		<div class="f_l">天发送客户(选0表示不屏蔽)</div>
					    		
					    		
					    	</div>
						</li>
					</ul>
		    	
			</div>
			
			


		</div>
		
		
		
		
		<div class="h45 m_t20 font14 more_detail display_none">
			
			
			<div class="h45 f_l m_l55">
				<div class="f_l c_384856 m_r15 lh50">
					订单来源:
				</div>
				<ul class="h50 f_l font14 c_384856">
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		PC端
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		聚划算
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		手机
					    	</div>
						</li>
					</ul>
		    	
			</div>
			
			
			<div class="h45 f_l m_l60">
				<div class="f_l c_384856 m_r15 lh50">
					客户身份:
				</div>
				<ul class="h50 f_l font14 c_384856">
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		学生
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		IT
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		金融族
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		白领族
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		公务员
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		居家族
					    	</div>
						</li>
						<li class="h50 f_l m_r15">
					    	<div class="m_t15 cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
					    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
					    	</div>
					    	<div class="f_l text-left lh50 m_l10">
					    		其他
					    	</div>
						</li>
					</ul>
		    	
			</div>
			
			


		</div>
		
		<div class="m_t20 c_384856 text-center">
			<table class="margin0_auto">
				<tr>
				    <th class="position_relative z_1 w65 p_l10 ">
				    	<div class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
				    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
				    	</div>
				    	<div class="p_l20">全选</div>
				    </th>
					<th class="w245">客户昵称</th>
					<th class="w120">客户等级</th>
					<th class="w185">最后交易时间</th>
					<th class="w210">交易量</th>
					<th class="w195">交易额</th>
					<th class="w200">交易来源</th>
				</tr>
				<tr>
				    <td class="position_relative p_l10 z_1">
				    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
				    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
				    	</div>
				    	<div></div>
				    </td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
				    <td class="position_relative p_l10 z_1">
				    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
				    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
				    	</div>
				    	<div></div>
				    </td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
				    <td class="position_relative p_l10 z_1">
				    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
				    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
				    	</div>
				    	<div></div>
				    </td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
				    <td class="position_relative p_l10 z_1">
				    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
				    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
				    	</div>
				    	<div></div>
				    </td>
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
		<div class="cursor_ w250 margin0_auto h45 lh45 text-center font14 border_00a0e9 b_radius5 c_00a0e9 tk">
			一键发送筛选出来的全部用户
		</div>
		
		
		
		
	</div>
	
</div>


<!---------------选择商品弹窗--------------->
<div class="w100_ h1370 rgba_000_5 position_absolute z_10 check_product top0 display_none">
	
	<div class="w930 p_l35 p_r35 bgc_fff margin0_auto left0 right0 p_b20 position_fixed top110">
		
		<div class="c_384856 text-center font18  p_t20">
			
			<div class="text-center m_b15">
				选择商品
			</div>
			
			
			
		</div>
		<div class="font14 h40 m_b30">
			<div class="f_l h40 lh40 c_384856">
				商品ID：
			</div>
			<input class="w265 p_l10 f_l h40 lh40 border0 m_r10 bgc_f4f6fa" />
			<div class="f_l h40 lh40 c_384856">
				关键字：
			</div>
			<input class="w265 p_l10 f_l h40 lh40 border0 bgc_f4f6fa" />
			
			<div class="cursor_ one_check_only m_t10 m_l15 w20 h20 border_d9dde5 b_radius5 f_l">
	    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
	    	</div>
	    	<div class="m_l10 f_l font14 h40 lh40 c_384856 m_r35">
	    		只显示上架
	    	</div>
			
			
			
			<div class="w100 f_l cursor_ h40 lh40 border_00a0e9 c_00a0e9 tk text-center b_radius5">
				搜索
			</div>
			
		</div>
		
		<!--------详细数据-------->
		<div class="c_384856 margin0_auto m_b10">
			<table>
			  <tr class="">
			    <th class="w75">
			    	<div class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    </th>
			    <th class="w760">店铺中宝贝</th>
			    <th class="w90">金额</th>
			  </tr>
			  <tr class="">
			    <td class="w75">
			    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    </td>
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
			  </tr>
			  <tr class="">
			    <td class="w75">
			    	<div class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 margin0_auto top15">
			    		<img class="cursor_ one_check_ display_none" src="${ctx}/crm/images/black_check.png" />
			    	</div>
			    </td>
			    <td></td>
			    <td></td>
			  </tr>
			</table>
			
		</div>
		<!--------分页-------->
	    <div class="w930 h24 m_t22 font14 c_8493a8 m_b40">
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
		
		
		<div class="w275 h42 margin0_auto m_t30 m_b20">
			
			
			<div class="cursor_ f_l m_r70 margin0_auto close_product b_radius5 w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk">
	    		确认
	    	</div>
	    	
	    	<div class="cursor_ f_l margin0_auto b_radius5 close_product w100 h40 lh40 text-center border_00a0e9 c_00a0e9 tk">
	    		取消
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
	