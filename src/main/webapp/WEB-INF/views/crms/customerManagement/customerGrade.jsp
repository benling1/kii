<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<title>客户管理-会员等级划分</title>
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
	<script type="text/javascript" src="${ctx}/crm/js/VipLVL_grouping.js" ></script>
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
				<div class="w1645 m_t70 m_l30">
					<div class="w100_ h50 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							客户管理
						</div>
						<div class="f_r m_r50 c_384856">
							
<%-- 							<a class="c_384856" href="${ctx}/GradeRuleController"> --%>
<!-- 								<div class="f_l w140 text-center cursor_ bgc_e3e7f0"> -->
<!-- 									会员等级划分 -->
<!-- 								</div> -->
<!-- 							</a> -->
							<a class="c_384856" href="${ctx}/sellerGroup/findSellerGroup">
								<div class="f_l w140 text-center cursor_">
									会员分组
								</div>
							</a>
							<a class="c_384856" href="${ctx}/memberInteraction/memberSmsSendAndReceIve">
								<div class="f_l w140 text-center cursor_">
									会员互动
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/customerManagement/blacklistManagemen">
								<div class="f_l w140 text-center cursor_">
									黑名单管理
								</div>
							</a>
						</div>
					</div>
					
					
					<!---------------会员等级划分--------------->
					<div class="h940">
						<!----------上部---------->
						<div class="w1605 h130 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								会员等级设置
							</div>
							<!---------------描述--------------->
							<div class="font14">
								保存等级折扣设置后，您店铺内所有会员均按此等级进行同步，包含您导入客云CRM中的会员。<span class="c_00a0e9 m_l25 cursor_">规则贴</span>
							</div>
							
							<div class="font16 c_384856 h50 lh50 m_t18">
								<div class="w140 h50 text-center f_l bgc_fff cursor_">
									折扣设置
								</div>
							</div>
							
						</div>
						<!----------下部---------->
						<div class="w1605 h920 bgc_fff p_t30 p_l40 p_b40 m_b30">
							
							<div class="f_l ">
								<!----------描述---------->
								<div class="font12 c_384856">
									设置会员等级条件和折扣必须为递增形式，并且普通会员条件至少有一个不为0，所设置折扣不能低于7折。请谨慎设置会员等级，一旦设置会员只升级不降级。详情见
								</div>
								
								<div class="overflow-y h755 w1160 m_t50">
								
									<!----------不同等级设置---------->
									<div class="h150 c_384856 font12 bor_b_e6eaef">
										<div>
											<img src="${ctx}/crm/images/vip_1.png" />
										</div>
										<div class="h20 lh20 m_t20 m_b5">
											<div class="f_l">
												交易额度
											</div>
											<div class="f_l m_l200 m_r440">
												交易笔数
											</div>
											<div class="f_l">
												会员权益
											</div>
										</div>
										<div class="h50">
											<div class="w200 h50 lh50 bgc_f4f6fa b_radius5 f_l">
												<input class="w125 p_l20 f_l h50 border0 bgc_f4f6fa b_radius5" />
												<div class="w55 text-center f_l">元</div>
											</div>
											<div class="f_l w50 h50 lh50 text-center">
												或
											</div>
											<div class="w200 h50 lh50 bgc_f4f6fa b_radius5 f_l">
												<input class="w125 p_l20 f_l h50 border0 bgc_f4f6fa b_radius5" />
												<div class="w55 text-center f_l">笔</div>
											</div>
											<div class="f_l h50 lh50 m_l20 m_r20">
												<span class="m_r100">满足这些条件可自动升级</span>
												<span>打</span>
											</div>
											<div class="w200 h50 lh50 bgc_f4f6fa b_radius5 f_l">
												<input class="w125 p_l20 f_l h50 border0 bgc_f4f6fa b_radius5" />
												<div class="w55 text-center f_l">折</div>
											</div>
											<div class="w55 p_t15 f_l m_l90">
												<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
											</div>
										</div>
										
									</div>
								
								
								
								
								
								</div>
								
								
								
								<!----------是否保存折扣设置---------->
								<div>
									<!----------选项---------->
									<div class="h42 m_t20 m_b15">
										<div class=" save_all_discount tk cursor_ f_l c_00a0e9 border_00a0e9 h42 lh42 b_radius5 w180 text-center m_r20">
											保存店铺客户折扣设置
										</div>
										<div class=" cancel_all_discount tk cursor_ f_l c_00a0e9 border_00a0e9 h40 lh40 b_radius5 w180 text-center ">
											取消所有店铺客户折扣
										</div>
										<div class=" cursor_ f_r tk border_00a0e9 h40 lh40 b_radius5 w100 m_r20 text-center">
											会员归类
										</div>
									</div>
									<!----------注意---------->
									<div class="font14 c_384856">
										注意：保存等级折扣设置后，淘宝会在下一次交易时更新等级；使用会员归类功能软件会根据当前修改的等级折扣设置为您的会员重新划分等级
									</div>
								</div>
								
								
								
							</div>
							
							<div class="f_l m_t150 w420" >
								<div class="w140 h140 margin0_auto">
									<img class="w140" src="${ctx}/crm/images/!.png" />
								</div>
								<div class="w335 margin0_auto c_384856 text-center m_t50">
									<div class="text-center m_b30">
										保存等级折扣设置后，淘宝会在下一次交易时更新等级；
									</div>
									<div class="text-center">
										使用会员归类功能软件会根据当前修改的等级折扣设置为您的会员重新划分等级。
									</div>
								</div>
							</div>
							
							
						</div>
					
							
							
							
					</div>
					
					
					
					
				</div>	
				
				
					
					<!---------------创建分组弹窗--------------->
					<div class="w1690 h1000 bgc_e3e7f0 position_absolute z_5 top40 add_group display_none">
						
						<div class="w1020 h650 bgc_fff margin0_auto m_t100 p_t20 p_l20 p_r20 p_b20">
							
								
							<!----------标题---------->
							<div class="c_384856 font18 text-center m_b55 m_t35">
								创建分组
							</div>
							
							<!----------筛选---------->
							<div>
								<!----------块1---------->
								<div class="c_384856 h22 m_b40">
									<div class="w60 text-right f_l font14 m_r30 h22 lh22">
										最近交易:
									</div>
									<div class="f_l font16 h22">
										<!----------选择区域1---------->
										<ul>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		不限
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近7天
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近1个月
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近3个月
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近半年
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		自定义
										    	</div>
											</li>
										</ul>
									</div>
								</div>
								
								
								<!----------块2---------->
								<div class="c_384856 h22 m_b40">
									<div class="w60 text-right f_l font14 m_r30 h22 lh22">
										交易次数:
									</div>
									<div class="f_l font16 h22">
										<!----------选择区域1---------->
										<ul>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		不限
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		0次
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		1次
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		2次
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		3次
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		3次以上
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		自定义
										    	</div>
											</li>
										</ul>
									</div>
								</div>
								
								<!----------块3---------->
								<div class="c_384856 h22 m_b40">
									<div class="w60 text-right f_l font14 m_r30 h22 lh22">
										最近交易:
									</div>
									<div class="f_l font16 h22">
										<!----------选择区域1---------->
										<ul>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		不限
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近7天
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近1个月
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近3个月
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近半年
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		自定义
										    	</div>
											</li>
										</ul>
									</div>
								</div>
								
								<!----------块4---------->
								<div class="c_384856 h22 m_b40">
									<div class="w60 text-right f_l font14 m_r30 h22 lh22">
										会员等级:
									</div>
									<div class="f_l font16 h22">
										<!----------选择区域1---------->
										<ul>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		所有会员
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		店铺会员
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img style="width:3vw;" class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		普通会员
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img style="width:3vw;" class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		高级会员
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img style="width:3vw;" class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		VIP会员
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img style="width:3vw;" class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		至尊VIP会员
										    	</div>
											</li>
										</ul>
									</div>
								</div>
								
								<!----------块5---------->
								<div class="c_384856 h22 m_b40">
									<div class="w60 text-right f_l font14 m_r30 h22 lh22">
										地区:
									</div>
									<div class="f_l font16 h22">
										<!----------选择区域1---------->
										<ul>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		不限
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近7天
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近1个月
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近3个月
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		近半年
										    	</div>
											</li>
											<li class="h22 lh22 f_l">
										    	<div class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
										    		<img class="cursor_ group_check_ display_none" src="${ctx}/crm/images/black_check.png" />
										    	</div>
										    	<div class="f_l w100 text-left m_l10">
										    		自定义
										    	</div>
											</li>
										</ul>
									</div>
								</div>
								
							</div>
							
							
							
							
							<div class="m_b20">
								<div class="w60 text-right c_384856 f_l font14 m_r30 h50 lh50">
									分组名称:
								</div>
								<input class="group_name w500 h50 lh50 border0 bgc_f4f6fa b_radius5" />
								
							</div>
							
							<div>
								<div class="w60 text-right c_384856 f_l font14 m_r30 h50 lh50">
									说明:
								</div>
								<input class="explain w500 h50 lh50 border0 bgc_f4f6fa b_radius5" />
								
							</div>
							
							<!--保存取消按钮-->
							<div class="w232 h42 margin0_auto m_t40">
								<div class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ close">保存</div>
								<div class="m_l30 close f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8">取消</div>
							</div>
							
							
							
							
							
							
						
						</div>
						
					</div>	
					
					
					
					
					
					
						
				</div>
		
			</div>
	
	</div>

	
	

					
					
	<!---------------取消打折--------------->
	<div class="w100_ h1300 rgba_000_5 position_absolute z_10 top0 display_none cancel_discount_window">
		
		<div class="w590 h200 bgc_fff margin0_auto position_fixed top250 left0 right0 p_t20 p_l20 p_r20 p_b20">
			
			
			
			<div class="text-center c_384856 font16 m_b20 h22 lh22">
				提示：
			</div>
			<div class="text-center c_384856 font16 m_b20 h22 lh22">
				确定取消打折吗？
			</div>
			
			
			
			<div class="w232 h42 margin0_auto">
				<div class="close w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ font16">确定</div>
				<div class="close m_l30 f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
			</div>
			
		</div>
		
	</div>

	
	

					
					
	<!---------------保存打折--------------->
	<div class="w100_ h1300 rgba_000_5 position_absolute z_10 top0 display_none save_discount_window">
		
		<div class="w590 h200 bgc_fff margin0_auto position_fixed top250 left0 right0 p_t20 p_l20 p_r20 p_b20">
			
			<div class="text-center c_384856 font16 m_b20 h22 lh22">
				提示：
			</div>
			<div class="text-center c_384856 font16 m_b20 h22 lh22">
				确定参与会员打折吗？
			</div>
			
			
			
			<div class="w232 h42 margin0_auto">
				<div class="close w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ font16">确定</div>
				<div class="close m_l30 f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
			</div>
			
		</div>
		
	</div>
	
	
	
	
	
	
	
    <div class="chat_window display_none">
        <div class="pew">
            
            <div class="bor_b_2_bfbfbf h53">
            	
	            <span class="font18 m_r100">会员互动</span>
	            <span class="font14">手机号：</span>
	            <span class="font14 m_r30">13911111111</span>
	            <span class="font14">买家：</span>
	            <img class="cursor_" src="${ctx}/crm/images/wangwang.png" />
	            <span class="font14 m_r32 cursor_">xxxxxx</span>
	            <img class="close_chat_window cursor_" src="${ctx}/crm/images/close_wangwang.png" />
	            
            </div>
            
        </div>
        
        <div class="b_radius5 overflow-y w490 h280 bgc_fff margin0_auto p_l15 p_r15 p_t15 p_b15">
        	
        	<div class="f_r m_b20">
        		<!----------发送内容---------->
        		<div class="f_l m_r10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">
        			
        			<p class="m_b15">2016-10.15 15:30</p>
        			<p>
        				测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试
        			</p>
        		
        		</div>
        		<!----------头像---------->
        		<div class="f_l w37 h37 b_radius50 cursor_">
        			<img class="w37 h37 b_radius50" src="${ctx}/crm/images/user.png" />
        		</div>
        	</div>
        	
        	<div class="f_l m_b20">
        		<!----------发送内容---------->
        		<div class="f_r m_l10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">
        			
        			<p class="m_b15">2016-10.15 15:30</p>
        			<p>
        				测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试
        			</p>
        		
        		</div>
        		<!----------头像---------->
        		<div class="f_r w37 h37 b_radius50 cursor_">
        			<img class="w37 h37 b_radius50" src="${ctx}/crm/images/user.png" />
        		</div>
        	</div>
        	
        	<div class="f_r m_b20">
        		<!----------发送内容---------->
        		<div class="f_l m_r10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">
        			
        			<p class="m_b15">2016-10.15 15:30</p>
        			<p>
        				测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试
        			</p>
        		
        		</div>
        		<!----------头像---------->
        		<div class="f_l w37 h37 b_radius50 cursor_">
        			<img class="w37 h37 b_radius50" src="${ctx}/crm/images/user.png" />
        		</div>
        	</div>
        	
        	<div class="f_l m_b20">
        		<!----------发送内容---------->
        		<div class="f_r m_l10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">
        			
        			<p class="m_b15">2016-10.15 15:30</p>
        			<p>
        				测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试
        			</p>
        		
        		</div>
        		<!----------头像---------->
        		<div class="f_r w37 h37 b_radius50 cursor_">
        			<img class="w37 h37 b_radius50" src="${ctx}/crm/images/user.png" />
        		</div>
        	</div>
        	
        </div>
        
        <div class="font12 c_384856 text-center m_t15 m_b15 p_r15">
        	<span>注：加签注【关欣康家用医疗器材】已输入<span class="c_ff6363 text_count">0</span>个字，预计扣费<span class="c_ff6363">1</span>条短信</span>
        	<span class="cursor_ f_r"><a href="${ctx}/crms/home/notice#duanxinxiangguan" target="_blank">计费规则</a></span>
        </div>
        
        
        <div class="w520 margin0_auto h95 margin0_auto position_relative">
        	
        	
        	<!----------内容输入区---------->
			<textarea class="text_area b_radius5 p_l10 p_r10 p_t10 p_b25 f_l w500 h60 bgc_f4f6fa border0"></textarea>
        	
        	
			<div class="w80 h20 bgc_ececec lh20 position_absolute right20 top70">
				<div class="f_l">
					退订回
				</div>
				<div class="f_l">
					"<input maxlength="2" class="w15 text-center border0 bgc_ececec c_384856" />"
				</div>
			</div>
			
			<div class="c_00a0e9 f_r font14 m_t10 text-right m_b40">
				<span class="cursor_">转化短网址</span>
				<span class="m_l30 m_r25 cursor_">引用短语库</span>
				<span class="cursor_">另存为短语库</span>
			</div>
			
			<div class="w232 h42 margin0_auto">
				<div class="save_chat w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ save_hmd">确认保存</div>
				<div class="cancel_chat m_l30 cancel_hmd f_l border_00a0e9 cursor_ w100 h40 lh40 text-center b_radius5 c_00a0e9">取消</div>
			</div>
        	
        	
        </div>
        
        
        
    </div>
	
	
				
</body>
<script type="text/javascript">
	
</script>
</html>
