<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>客户管理</title>
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

<%-- <script type="text/javascript" src="${ctx}/crm/js/model.js"></script> --%>
<script type="text/javascript" src="${ctx}/crm/js/kehuguanli.js"></script>

</head>
<body>

	<div class="w1903 h100_">
		<!-------------------------左部，侧边栏------------------------->
		<div class="w200 h100_ bgc_1c2b36 position_fixed top0 z_10">
			<div class="m_t80 w100_ bgc_052128 h1"></div>




			<!--------------------用户信息区-------------------->
			<div class="w100_ h224 bor_b_052128 p_t35">
				<!---------------用户头像--------------->
				<div class="b_radius50 w75 h75 margin0_auto m_b10 ">
					<img class="w75 h75 b_radius50 cursor_" src="${ctx}/crm/images/user.png" />
				</div>
				<!---------------用户ID--------------->
				<div class="font16 c_fff text-center h22 l_h22 m_b30 cursor_">
					Coco!</div>
				<!---------------其他信息--------------->
				<div class="text-center c_7da2bb m_b10 font12">
					<span>短信剩余:</span><span>1256条</span>
				</div>
				<div class="text-center c_7da2bb m_b10 font12">
					<span>邮件余额:</span><span>1256封</span>
				</div>
				<div class="text-center c_7da2bb m_b10 font12">
					<span>软件到期:</span><span>1256天</span>
				</div>

			</div>


			<!--------------------数据分析-------------------->
			<div class="w100_ h300 bor_b_052128 p_t35">
				<div class="font12 c_3d596c p_l50 m_b20">数据分析</div>

				<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b20">
					<div class="f_l m_r20">
						<img class="" src="${ctx}/crm/images/Customer.png" />
					</div>
					<div class="f_l cursor_">客户管理</div>
				</div>

				<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b20">
					<div class="f_l m_r20">
						<img class="m_l3" src="${ctx}/crm/images/order.png" />
					</div>
					<div class="f_l m_l3 cursor_">订单中心</div>
				</div>

				<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b20">
					<div class="f_l m_r20">
						<img class="" src="${ctx}/crm/images/marketing.png" />
					</div>
					<div class="f_l cursor_">营销中心</div>
				</div>

				<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b20">
					<div class="f_l m_r20">
						<img class="" src="${ctx}/crm/images/back_stage.png" />
					</div>
					<div class="f_l cursor_">后台管理</div>
				</div>

				<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w105 m_b20">
					<div class="f_l m_r20">
						<img class="" src="${ctx}/crm/images/store.png" />
					</div>
					<div class="f_l cursor_">店铺数据</div>
				</div>

			</div>


			<!--------------------常用功能-------------------->
			<div class="w100_ h300 p_t35">
				<div class="font12 c_3d596c p_l35 m_b20">常用功能</div>

				<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w140 m_b20">
					<div class="f_l m_r20">
						<img class="m_l3" src="${ctx}/crm/images/recharge.png" />
					</div>
					<div class="f_l m_l3 cursor_">充值</div>
				</div>

				<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w140 m_b20">
					<div class="f_l m_r20">
						<img class="" src="${ctx}/crm/images/vip_lvl.png" />
					</div>
					<div class="f_l cursor_">会员等级划分</div>
				</div>

				<div class="margin0_auto h24 lh24 c_7da2bb l_h24 w140 m_b20">
					<div class="f_l m_r20">
						<img class="" src="${ctx}/crm/images/text_history.png" />
					</div>
					<div class="f_l cursor_">短信发送记录</div>
				</div>

			</div>















		</div>




		<!-------------------------右部------------------------->
		<div class="w1703 m_l200">
			<!--------------------顶部导航栏-------------------->
			<div class="h40 w1703 bgc_fff position_fixed top0 z_10">
				<div class="f_l lh40">
					<img class="m_l40 m_r60 cursor_" src="${ctx}/crm/images/home.png" /> <img
						class=" cursor_" src="${ctx}/crm/images/mail.png" />
				</div>
				<div class="f_r">
					<div class=" f_l m_r15">
						<img class="m_t10 cursor_" src="${ctx}/crm/images/date.png" />
					</div>
					<div class="m_r40 f_l lh40 c_7da2bb time">
						<!--<span class="m_r20">2016年5月6日</span><span class="m_r10">PM</span><span>13:20</span>-->
					</div>
					<div class="f_l m_r20">
						<img class="w32 h32 m_t3 right_top_set_btn cursor_"
							src="${ctx}/crm/images/set.png" />
					</div>
				</div>
				<!--------------------顶部设置栏-------------------->
				<div
					class="w410 h1020 bgc_fff position_fixed top40 right0 right_top_set display_none">

					<div class="w100_ bor_b_e6eaef p_l30">

						<div class="w100_ h50 bor_b_e6eaef m_t30">
							<div class="f_l m_r10">
								<img src="${ctx}/crm/images/no_pic.png" />
							</div>
							<div class="f_l h30 lh30 c_596671 font18">付款提醒</div>
						</div>

						<div class="w100_ h95 bor_b_e6eaef">
							<div class="f_l">
								<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
								<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
							</div>

							<div class="f_l m_l60">
								<div class="f_l m_r20 lh95">
									<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
								</div>
								<div class="f_l w20 h20 lh95">
									<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
								</div>
							</div>

						</div>

						<div class="w100_ h95">
							<div class="f_l">
								<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
								<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
							</div>

							<div class="f_l m_l60">
								<div class="f_l m_r20 lh95">
									<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
								</div>
								<div class="f_l w20 h20 lh95">
									<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
								</div>
							</div>

						</div>

					</div>

					<div class="w100_ bor_b_e6eaef p_l30">

						<div class="w100_ h50 bor_b_e6eaef m_t30">
							<div class="f_l m_r10">
								<img src="${ctx}/crm/images/no_pic.png" />
							</div>
							<div class="f_l h30 lh30 c_596671 font18">付款提醒</div>
						</div>

						<div class="w100_ h95 bor_b_e6eaef">
							<div class="f_l">
								<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
								<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
							</div>

							<div class="f_l m_l60">
								<div class="f_l m_r20 lh95">
									<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
								</div>
								<div class="f_l w20 h20 lh95">
									<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
								</div>
							</div>

						</div>

						<div class="w100_ h95">
							<div class="f_l">
								<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
								<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
							</div>

							<div class="f_l m_l60">
								<div class="f_l m_r20 lh95">
									<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
								</div>
								<div class="f_l w20 h20 lh95">
									<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
								</div>
							</div>

						</div>

						<div class="w100_ h95">
							<div class="f_l">
								<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
								<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
							</div>

							<div class="f_l m_l60">
								<div class="f_l m_r20 lh95">
									<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
								</div>
								<div class="f_l w20 h20 lh95">
									<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
								</div>
							</div>

						</div>

						<div class="w100_ h95">
							<div class="f_l">
								<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
								<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
							</div>

							<div class="f_l m_l60">
								<div class="f_l m_r20 lh95">
									<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
								</div>
								<div class="f_l w20 h20 lh95">
									<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
								</div>
							</div>

						</div>

						<div class="w100_ h95">
							<div class="f_l">
								<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
								<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
							</div>

							<div class="f_l m_l60">
								<div class="f_l m_r20 lh95">
									<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
								</div>
								<div class="f_l w20 h20 lh95">
									<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
								</div>
							</div>

						</div>

						<div class="w100_ h95">
							<div class="f_l">
								<div class="c_596671 m_b10 m_t20">下单未付款提醒</div>
								<div class="font12 w230 c_cbcdcf">当客户下单超过一定时间未付款后，系统会自动发送短信提醒客户付款</div>
							</div>

							<div class="f_l m_l60">
								<div class="f_l m_r20 lh95">
									<img class="green_check cursor_" src="${ctx}/crm/images/green_check.png" />
								</div>
								<div class="f_l w20 h20 lh95">
									<img class="w20 h20 cursor_" src="${ctx}/crm/images/set.png" />
								</div>
							</div>

						</div>

					</div>





				</div>
			</div>




			<!--------------------主要内容区-------------------->
			<div class="w1660 m_t70 m_l30">
				<div class="w100_ h50 lh50 bgc_fff font18">
					<div class="f_l h50 w4 bgc_1d9dd9"></div>
					<div class="f_l m_l15 c_384856">客户管理</div>
					<div class="f_r m_r50 c_384856">
						<div class="f_l w140 text-center cursor_ bgc_e3e7f0 khgl_out">
							会员等级划分</div>
						<div class="f_l w140 text-center cursor_ khgl_out">会员分组</div>
						<div class="f_l w140 text-center cursor_ khgl_out">高级分组</div>
						<div class="f_l w140 text-center cursor_ khgl_out">会员互动</div>
						<div class="f_l w140 text-center cursor_ khgl_out">黑名单管理</div>
					</div>
				</div>


				<!---------------会员等级划分--------------->
				<div class="h940 khgl_in">
					<!----------上部---------->
					<div class="w1620 h130 bgc_f1f3f7 c_384856 p_l40 p_t30">

						<!---------------标题--------------->
						<div class="font24 m_b10">会员等级设置</div>
						<!---------------描述--------------->
						<div class="font14">
							使用会员等级区分买家的级别，不同级别的买家可以享受不同的折扣率。注意最低折扣价不能低于7折，填写折扣率注意，九八折，填写9.8而不是0.98<span
								class="c_00a0e9 m_l25 cursor_">规则贴</span>
						</div>

						<div class="font16 c_384856 h50 lh50 m_t18">
							<div
								class="w140 h50 text-center f_l bgc_fff discount_out cursor_">
								折扣设置</div>
							<div
								class="w140 h50 text-center f_l bgc_e3e7f0 discount_out cursor_">
								折扣宝贝设置</div>
						</div>

					</div>
					<!----------下部---------->
					<div class="w1620 h920 bgc_fff p_t30 p_l40 p_b40 discount_in m_b30">

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
										<div class="f_l">交易额度</div>
										<div class="f_l m_l200 m_r440">交易笔数</div>
										<div class="f_l">会员权益</div>
									</div>
									<div class="h50">
										<div class="w200 h50 lh50 bgc_f4f6fa b_radius5 f_l">
											<input
												class="w125 p_l20 f_l h50 border0 bgc_f4f6fa b_radius5" />
											<div class="w55 text-center f_l">元</div>
										</div>
										<div class="f_l w50 h50 lh50 text-center">或</div>
										<div class="w200 h50 lh50 bgc_f4f6fa b_radius5 f_l">
											<input
												class="w125 p_l20 f_l h50 border0 bgc_f4f6fa b_radius5" />
											<div class="w55 text-center f_l">笔</div>
										</div>
										<div class="f_l h50 lh50 m_l20 m_r20">
											<span class="m_r100">满足这些条件可自动升级</span> <span>打</span>
										</div>
										<div class="w200 h50 lh50 bgc_f4f6fa b_radius5 f_l">
											<input
												class="w125 p_l20 f_l h50 border0 bgc_f4f6fa b_radius5" />
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
									<div
										class="save_all_discount cursor_ f_l c_fff bgc_00a0e9 h42 lh42 b_radius5 w180 text-center m_r20">
										保存店铺客户折扣设置</div>
									<div
										class="cancel_all_discount cursor_ f_l border_00a0e9 h40 lh40 b_radius5 w180 text-center">
										取消所有店铺客户折扣</div>
									<div
										class="cursor_ f_r border_00a0e9 h40 lh40 b_radius5 w100 m_r20 text-center">
										会员归类</div>
								</div>
								<!----------注意---------->
								<div class="font14 c_384856">
									注意：保存等级折扣设置后，淘宝会在下一次交易时更新等级；使用会员归类功能软件会根据当前修改的等级折扣设置为您的会员重新划分等级
								</div>
							</div>



						</div>

						<div class="f_l w460 m_t150">
							<div class="w140 h140 margin0_auto">
								<img src="${ctx}/crm/images/!.png" />
							</div>
							<div class="w335 margin0_auto c_384856 text-center m_t50">
								<div class="text-center m_b30">保存等级折扣设置后，淘宝会在下一次交易时更新等级；</div>
								<div class="text-center">
									使用会员归类功能软件会根据当前修改的等级折扣设置为您的会员重新划分等级。</div>
							</div>
						</div>


					</div>

					<div
						class="w1620 h705 bgc_fff p_t30 p_l40 p_b40 discount_in m_b30 display_none">

						<div class="">
							<!----------描述---------->
							<div class="font12 c_384856">
								设置会员等级条件和折扣必须为递增形式，并且普通会员条件至少有一个不为0，所设置折扣不能低于7折。请谨慎设置会员等级，一旦设置会员只升级不降级。详情见
							</div>

							<!----------过滤筛选---------->
							<div class="h40 lh40 m_t50 m_b30">
								<div class="f_l m_r15 c_384856">过滤宝贝范围：</div>
								<div class="wrap f_l">
									<div class="nice-select" name="nice-select">
										<input type="text" value="下单时间" readonly>
										<ul>
											<li>1</li>
											<li>2</li>
											<li>3</li>
											<li>4</li>
										</ul>
									</div>
								</div>
								<div class="f_l m_r15 c_384856 m_l40">宝贝分类：</div>
								<div class="wrap f_l">
									<div class="nice-select" name="nice-select">
										<input type="text" value="全部分类" readonly>
										<ul>
											<li>1</li>
											<li>2</li>
											<li>3</li>
											<li>4</li>
										</ul>
									</div>
								</div>
								<div class="f_l m_r15 c_384856 m_l40">宝贝名称：</div>
								<input
									class="w250 h45 lh45 border0 bgc_f4f6fa b_radius5 f_l m_r70"
									type="text">
								<div
									class="c_fff w100 h42 lh42 bgc_00a0e9 f_l text-center b_radius5 cursor_">
									确认保存</div>
							</div>

							<!----------设置打折---------->
							<div class="m_b20 h42">
								<div
									class="save_all_discount cursor_ f_l c_fff bgc_00a0e9 h42 lh42 b_radius5 w250 text-center m_r20">
									设置已选择宝贝参与会员打折</div>
								<div
									class="cancel_all_discount cursor_ f_l border_00a0e9 h40 lh40 b_radius5 w250 text-center">
									设置已取消宝贝参与会员打折</div>
							</div>


							<!--------详细数据-------->
							<div class="c_384856">
								<table>
									<tr class="">
										<th class="position_relative z_1 w65 p_l10 ">
											<div
												class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
												<img class="cursor_ one_check_ display_none"
													src="${ctx}/crm/images/black_check.png" />
											</div>
											<div class="p_l20">全选</div>
										</th>
										<th class="w535">宝贝名称</th>
										<th class="w110">宝贝价格</th>
										<th class="w235">是否参加会员打折</th>
										<th class="w315">操作</th>
									</tr>
									<tr class="">
										<td class="position_relative p_l10 z_1 w65">
											<div
												class="cursor_ cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
												<img class="cursor_ one_check_ display_none"
													src="${ctx}/crm/images/black_check.png" />
											</div>
											<div></div>
										</td>
										<td class="w495 lh50 p_l20 p_r20"><img
											class="f_l m_t4 m_r15 cursor_" src="${ctx}/crm/images/product.png" />
											<div class="f_l c_384856 font14 cursor_">
												维罗养生壶电解磁化养生壶陶瓷壶体适用任何款式厂家正品全国包邮</div></td>
										<td class="w110 text-center c_384856 font12">￥160.00</td>
										<td class="w235 text-center font14 c_ff6363">已经参加会员打折</td>
										<td class="w315">
											<div class="h35 margin0_auto w232">
												<div
													class="cursor_ f_l m_r12 w120 h35 lh35 bgc_00a0e9 c_fff text-center b_radius5">
													参加会员折扣</div>
												<div
													class="cursor_ f_l w98 h33 lh33 bgc_fff c_00a0e9 text-center border_00a0e9 b_radius5">
													去淘宝编辑</div>
											</div>
										</td>
									</tr>
									<tr class="">
										<td class="position_relative p_l10 z_1 w65">
											<div
												class="cursor_ cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
												<img class="cursor_ one_check_ display_none"
													src="${ctx}/crm/images/black_check.png" />
											</div>
											<div></div>
										</td>
										<td class="w495 lh50 p_l20 p_r20"><img
											class="f_l m_t4 m_r15 cursor_" src="${ctx}/crm/images/product.png" />
											<div class="f_l c_384856 font14 cursor_">
												维罗养生壶电解磁化养生壶陶瓷壶体适用任何款式厂家正品全国包邮</div></td>
										<td class="w110 text-center c_384856 font12">￥160.00</td>
										<td class="w235 text-center font14 c_384856">未参加会员打折</td>
										<td class="w315">
											<div class="h35 margin0_auto w232">
												<div
													class="cursor_ f_l m_r12 w120 h35 lh35 bgc_00a0e9 c_fff text-center b_radius5">
													参加会员折扣</div>
												<div
													class="cursor_ f_l w98 h33 lh33 bgc_fff c_00a0e9 text-center border_00a0e9 b_radius5">
													去淘宝编辑</div>
											</div>
										</td>
									</tr>
									<tr class="">
										<td class="position_relative p_l10 z_1 w65">
											<div
												class="cursor_ cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
												<img class="cursor_ one_check_ display_none"
													src="${ctx}/crm/images/black_check.png" />
											</div>
											<div></div>
										</td>
										<td class="w285"></td>
										<td class="w110"></td>
										<td class="w235"></td>
										<td class="w315"></td>
									</tr>
									<tr class="">
										<td class="position_relative p_l10 z_1 w65">
											<div
												class="cursor_ cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
												<img class="cursor_ one_check_ display_none"
													src="${ctx}/crm/images/black_check.png" />
											</div>
											<div></div>
										</td>
										<td class="w285"></td>
										<td class="w110"></td>
										<td class="w235"></td>
										<td class="w315"></td>
									</tr>
									<tr class="">
										<td class="position_relative p_l10 z_1 w65">
											<div
												class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
												<img class="cursor_ one_check_ display_none"
													src="${ctx}/crm/images/black_check.png" />
											</div>
											<div></div>
										</td>
										<td class="w285"></td>
										<td class="w110"></td>
										<td class="w235"></td>
										<td class="w315"></td>
									</tr>
									<tr class="">
										<td class="position_relative p_l10 z_1 w65">
											<div
												class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
												<img class="cursor_ one_check_ display_none"
													src="${ctx}/crm/images/black_check.png" />
											</div>
											<div></div>
										</td>
										<td class="w285"></td>
										<td class="w110"></td>
										<td class="w235"></td>
										<td class="w315"></td>
									</tr>
									<tr class="">
										<td class="position_relative p_l10 z_1 w65">
											<div
												class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
												<img class="cursor_ one_check_ display_none"
													src="${ctx}/crm/images/black_check.png" />
											</div>
											<div></div>
										</td>
										<td class="w285"></td>
										<td class="w110"></td>
										<td class="w235"></td>
										<td class="w315"></td>
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


				<!---------------会员分组--------------->
				<div class="h940 khgl_in display_none">
					<!----------上部---------->
					<div class="w1620 h80 bgc_f1f3f7 c_384856 p_l40 p_t30">

						<!---------------标题--------------->
						<div class="font24 m_b10">会员分组</div>
						<!---------------描述--------------->
						<div class="font14">给您的会员归类方便管理和营销</div>

					</div>
					<!----------下部---------->

					<div class="w1620 h705 bgc_fff p_t30 p_l40 p_b40 discount_in m_b30">

						<div class="">


							<!--------分组设置-------->
							<div class="m_b22">
								<div class="h22 m_b35">
									<div class="f_l m_r60">
										<div
											class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856">全部分组</div>
									</div>
									<div class="f_l m_r60">
										<div
											class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856">默认分组</div>
									</div>
									<div class="f_l">
										<div
											class="cursor_ one_check_only w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="m_l10 f_l font14 c_384856">匿名分组</div>
									</div>


									<div class="f_r">
										上次更新时间<span>2016-10.19 15:30</span>
									</div>
								</div>
								<button
									class="add_group_btn cursor_ w140 h50 lh50 font14 c_fff bgc_00a0e9 border0 b_radius5">
									<img class="m_r20" src="${ctx}/crm/images/add_list.png" /> 创建分组
								</button>
							</div>

							<!--------详细数据-------->
							<div class="c_384856">
								<table>
									<tr class="">
										<th class="w75 text-center">序号</th>
										<th class="w220">分组名称</th>
										<th class="w175">客户数(人)</th>
										<th class="w130">分组来源</th>
										<th class="w375">说明</th>
										<th class="w290">操作</th>
									</tr>
									<tr class="">
										<td class="w75 text-center">1</td>
										<td class="w220 lh50 text-center c_384856 font16">近半年老客户
										</td>
										<td class="w175 text-center c_384856 font16 c_00a0e9">
											225</td>
										<td class="w130 text-center c_384856 font16">系统分组</td>
										<td class="w375 text-center font16 c_384856">无</td>
										<td class="w290">
											<div
												class="cursor_ margin0_auto marketing_vip w98 h33 lh33 bgc_fff c_00a0e9 text-center border_00a0e9 b_radius5">
												会员营销</div>
										</td>
									</tr>
									<tr class="">
										<td class="w75 text-center">2</td>
										<td class="w220 lh50 text-center c_384856 font16">近半年老客户
										</td>
										<td class="w175 text-center c_384856 font16 c_00a0e9">
											225</td>
										<td class="w130 text-center c_384856 font16">系统分组</td>
										<td class="w375 text-center font16 c_384856">无</td>
										<td class="w290">
											<div
												class="m_l20 f_l m_r10 cursor_ marketing_vip w98 h33 lh33 bgc_fff c_00a0e9 text-center border_00a0e9 b_radius5">
												会员营销</div>
											<div
												class="m_r10 f_l cursor_ marketing_vip w58 h33 lh33 bgc_fff c_00a0e9 text-center border_00a0e9 b_radius5">
												修改</div>
											<div
												class="f_l cursor_ marketing_del w58 h33 lh33 bgc_fff c_ff6363 text-center border_ff6363 b_radius5">
												删除</div>

										</td>
									</tr>
									<tr class="">
										<td class="w75 text-center">3</td>
										<td class="w220"></td>
										<td class="w130"></td>
										<td class="w175"></td>
										<td class="w375"></td>
										<td class="w290"></td>
									</tr>
									<tr class="">
										<td class="w75 text-center">4</td>
										<td class="w220"></td>
										<td class="w130"></td>
										<td class="w175"></td>
										<td class="w375"></td>
										<td class="w290"></td>
									</tr>
									<tr class="">
										<td class="w75 text-center">5</td>
										<td class="w220"></td>
										<td class="w130"></td>
										<td class="w175"></td>
										<td class="w375"></td>
										<td class="w290"></td>
									</tr>
									<tr class="">
										<td class="w75 text-center">6</td>
										<td class="w220"></td>
										<td class="w130"></td>
										<td class="w175"></td>
										<td class="w375"></td>
										<td class="w290"></td>
									</tr>
									<tr class="">
										<td class="w75 text-center">7</td>
										<td class="w220"></td>
										<td class="w130"></td>
										<td class="w175"></td>
										<td class="w375"></td>
										<td class="w290"></td>
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


				<!---------------高级分组--------------->
				<div class="h940 khgl_in display_none">3</div>


				<!---------------会员互动--------------->
				<div class="m_b30 khgl_in display_none">
					<!----------上部---------->
					<div class="w1620 h130 bgc_f1f3f7 c_384856 p_l40 p_t30">

						<!---------------标题--------------->
						<div class="font24 m_b10">会员互动</div>
						<!---------------描述--------------->
						<div class="font14">接受买家短信回复，与客户一对一短信交流互动</div>

						<div class="font16 c_384856 h50 lh50 m_t18">
							<div class="w140 h50 text-center f_l bgc_fff viphd_out cursor_">
								会员互动记录</div>
							<div
								class="w140 h50 text-center f_l bgc_e3e7f0 viphd_out cursor_">
								买家回复内容查询</div>
							<!-- <div
								class="w140 h50 text-center f_l bgc_e3e7f0 viphd_out cursor_">
								卖家发送记录</div> -->
						</div>

					</div>
					<!----------下部---------->
					<div class="w1620 bgc_fff p_t35 p_l40 p_b40 viphd_in">

						<!--------查询设置-------->
						<div class="font14 c_384856 h50 lh50">
							<div class="f_l m_r15">手机号码:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
							<div
								class="w100 h50 b_radius5 m_r10 bgc_00a0e9 c_fff text-center f_l cursor_">
								查询</div>
							<div
								class="w100 send_text_btn h50 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
								短信发送</div>
						</div>


						<div class="font16 c_384856 h22 lh22 m_t20 m_b30">
							提示：如客户回复短信内容符合TD、T、N、（不区分大小写）3种退订规则，会自动将手机号加入到店铺黑名单。</div>


						<!--------操作选项-------->
						<div
							class="w180 font14 h52 lh52 m_b15 b_radius5 bgc_00a0e9 c_fff text-center cursor_">
							一键标记为已读</div>


						<!--------详细数据-------->
						<div class="c_384856">
							<table>
								<tr class="">
									<th class="position_relative z_1 w65 p_l10 ">
										<div
											class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="p_l20">全选</div>
									</th>
									<th class="w60">序号</th>
									<th class="w150">买家昵称</th>
									<th class="w150">手机号码</th>
									<th class="w425">短信内容</th>
									<th class="w150">未读条数</th>
									<th class="w150">最近回复时间</th>
									<th class="w100">查看</th>
								</tr>														
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">2</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w405 text-center p_l10 p_r10">
										<div class="w405 h50 lh50 one_line_only text_detail">
											短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
										</div>
									</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">3</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w425 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">4</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w425 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">5</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w425 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">6</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w425 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">7</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w425 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100"></td>
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

					<!----------下部---------->
					<div class="w1620 bgc_fff p_t35 p_l40 p_b40 viphd_in display_none">

						<!--------查询设置-------->
						<div class="font14 c_384856 h52 lh52 m_b35">
							<div class="f_l m_r15">手机号码:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />


							<div class="f_l m_r15">是否已读:</div>
							<div class="wrap f_l m_r40">
								<div class="nice-select h50" name="nice-select">
									<input class="" type="text" value="" readonly>
									<ul>
										<li>是</li>
										<li>否</li>
									</ul>
								</div>
							</div>
							<div class="f_l m_r15">买家昵称:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />

						</div>

						<div class="h52 lh52 m_b10">
							<div class="f_l m_r15 c_384856">发送时间:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
							<div class="f_l">~</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r40" />

							<div class="f_l m_r15 c_384856">短信内容:</div>
							<div class="wrap f_l m_r20">
								<div class="nice-select h50" name="nice-select">
									<input class="" type="text" value="" readonly>
									<ul>
										<li>包含</li>
										<li>不包含</li>
									</ul>
								</div>
							</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r40" />

						</div>

						<!--------导出-------->
						<div class="h50 p_r350 m_b10">
							<div
								class="w100 f_l h50 lh50 b_radius5 bgc_00a0e9 c_fff text-center cursor_">
								导出</div>
							<span class="f_r font12 c_384856">（可输入TD、T、N、等查询退订短信客户）</span>
						</div>


						<!--------详细数据-------->
						<div class="c_384856">
							<table>
								<tr class="">
									<th class="w150">买家昵称</th>
									<th class="w150">手机号码</th>
									<th class="w425">短信内容</th>
									<th class="w150">发送时间</th>
									<th class="w150">是否已读</th>
									<th class="w100">最近更新日期</th>
								</tr>
								<c:forEach items="${SmsReceiveInfolist }" var="receiveInfo">
									<tr class="">
									<td class="w150 text-center">${receiveInfo.taobaoNick}</td>
									<td class="w150 text-center">${receiveInfo.sendPhone}</td>
									<td class="w405 text-center p_l10 p_r10">
										<div class="w405 h50 lh50 one_line_only text_detail">
											${receiveInfo.content}
										</div>
									</td>
									<td class="w150 text-center">
										${receiveInfo.receiveDate}										
									</td>
									<td class="w100 text-center">${receiveInfo.status}</td>
									<td class="w150 text-center">
										<fmt:formatDate value="${receiveInfo.lastModifiedDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</td>
								</tr>
								</c:forEach>								
								<tr class="">
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w405 text-center p_l10 p_r10">
										<div class="w405 h50 lh50 one_line_only text_detail">
											短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
										</div>
									</td>
									<td class="w150 text-center"></td>
									<td class="w100 text-center"></td>
									<td class="w150"></td>
								</tr>
								<tr class="">
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w425 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100 text-center"></td>
									<td class="w150"></td>
								</tr>
								<tr class="">
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w425 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100 text-center"></td>
									<td class="w150"></td>
								</tr>
								<tr class="">
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w425 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100 text-center"></td>
									<td class="w150"></td>
								</tr>
								<tr class="">
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w425 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100 text-center"></td>
									<td class="w150"></td>
								</tr>
								<tr class="">
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w425 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w100 text-center"></td>
									<td class="w150"></td>
								</tr>
							</table>
						</div>
						<!--------分页-------->
						<div class="p_r430 h24 m_t22 font14 c_8493a8 m_b40">
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

					<!----------下部---------->
					<div class="w1620 bgc_fff p_t35 p_l40 p_b40 viphd_in display_none">

						<!--------查询设置-------->
						<div class="font14 c_384856 h52 lh52 m_b35">
							<div class="f_l m_r15">手机号码:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />


							<div class="f_l m_r15">发送状态:</div>
							<div class="wrap f_l m_r40">
								<div class="nice-select h50" name="nice-select">
									<input class="" type="text" value="" readonly>
									<ul>
										<li>发送成功</li>
										<li>发送失败</li>
									</ul>
								</div>
							</div>
							<div class="f_l m_r15">卖家昵称:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r100" />

							<div
								class="w100 f_l h50 lh50 b_radius5 bgc_00a0e9 c_fff text-center cursor_">
								查询</div>

						</div>

						<div class="h52 lh52 m_b10">
							<div class="f_l m_r15 c_384856">发送时间:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
							<div class="f_l">~</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r40" />


						</div>

						<!--------导出-------->
						<div class="h50 p_r350 m_b10">
							<div
								class="w100 f_l h50 lh50 b_radius5 bgc_00a0e9 c_fff text-center cursor_">
								导出</div>
						</div>
						<div class="c_384856 font14 m_b15">温馨提示：短信记录目前只保留2015年10月1日之后的记录。</div>


						<!--------详细数据-------->
						<div class="c_384856">
							<table>
								<tr class="">
									<th class="position_relative z_1 w65 p_l10 ">
										<div
											class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="p_l20">全选</div>
									</th>
									<th class="w60">序号</th>
									<th class="w150">买家昵称</th>
									<th class="w150">手机号码</th>
									<th class="w425">短信内容</th>
									<th class="w100">短信通道</th>
									<th class="w90">发送状态</th>
									<th class="w120">实际扣费(条)</th>
									<th class="w130">发送时间</th>
								</tr>
								<c:forEach items='${smsSendInfolist}' var='sendinfo'>
									<tr class="font12">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">${sendinfo.id }</td>
									<td class="w150 text-center">${sendinfo.nickname }</td>
									<td class="w150 text-center">${sendinfo.phone }</td>
									<td class="w405 text-center font14 p_l10 p_r10">
										<div class="w405 h50 lh50 one_line_only text_detail">
											${sendinfo.content }
										</div>
									</td>
									<td class="w100 text-center">${sendinfo.channel }</td>
									<c:if test="${sendinfo.status ==1}">
										<td class="w90 text-center">发送成功</td>
									</c:if>
									<c:if test="${sendinfo.status ==2}">
										<td class="w90 text-center">发送失败</td>
									</c:if>																		
									<td class="w120 text-center">${sendinfo.actualDeduction}</td>
									<td class="w130 text-center">
										<fmt:formatDate value="${sendinfo.sendTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
									</td>
								</tr>
								</c:forEach>
								<tr class="font12">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">1</td>
									<td class="w150 text-center">sdgsdgsdfgsdf</td>
									<td class="w150 text-center">13911112222</td>
									<td class="w405 text-center font14 p_l10 p_r10">
										<div class="w405 h50 lh50 one_line_only text_detail">
											短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
										</div>
									</td>
									<td class="w100 text-center"></td>
									<td class="w90 text-center">2</td>
									<td class="w120 text-center">100</td>
									<td class="w130 text-center">2016-12-15 18:30</td>
								</tr>
								<tr class="font12">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">2</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w405 text-center font14 p_l10 p_r10">
										<div class="w405 h50 lh50 one_line_only text_detail">
											短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容短信内容只显示一行！鼠标经过或点击展开全部完整短信内容
										</div>
									</td>
									<td class="w100 text-center"></td>
									<td class="w90 text-center"></td>
									<td class="w120 text-center"></td>
									<td class="w130 text-center"></td>
								</tr>
								<tr class="font12">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">3</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w405 text-center font14 p_l10 p_r10">
									<td class="w100 text-center"></td>
									<td class="w90 text-center"></td>
									<td class="w120 text-center"></td>
									<td class="w130 text-center"></td>
								</tr>
								<tr class="font12">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">4</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w405 text-center font14 p_l10 p_r10">
									<td class="w100 text-center"></td>
									<td class="w90 text-center"></td>
									<td class="w120 text-center"></td>
									<td class="w130 text-center"></td>
								</tr>
								<tr class="font12">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">5</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w405 text-center font14 p_l10 p_r10">
									<td class="w100 text-center"></td>
									<td class="w90 text-center"></td>
									<td class="w120 text-center"></td>
									<td class="w130 text-center"></td>
								</tr>
								<tr class="font12">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">6</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w405 text-center font14 p_l10 p_r10">
									<td class="w100 text-center"></td>
									<td class="w90 text-center"></td>
									<td class="w120 text-center"></td>
									<td class="w130 text-center"></td>
								</tr>
								<tr class="font12">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w60 text-center">7</td>
									<td class="w150 text-center"></td>
									<td class="w150 text-center"></td>
									<td class="w405 text-center font14 p_l10 p_r10">
									<td class="w100 text-center"></td>
									<td class="w90 text-center"></td>
									<td class="w120 text-center"></td>
									<td class="w130 text-center"></td>
								</tr>
							</table>
						</div>
						<!--------分页-------->
						<div class="p_r430 h24 m_t22 font14 c_8493a8 m_b40">
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


				<!---------------黑名单管理--------------->
				<div class="h940 khgl_in display_none">
					<!----------上部---------->
					<div class="w1620 h130 bgc_f1f3f7 c_384856 p_l40 p_t30">

						<!---------------标题--------------->
						<div class="font24 m_b10">黑名单管理</div>
						<!---------------描述--------------->
						<div class="font14">接受买家短信回复，与客户一对一短信交流互动</div>

						<div class="font16 c_384856 h50 lh50 m_t18">
							<div class="w140 h50 text-center f_l bgc_fff hmd_out cursor_">
								手机黑名单</div>
							<div class="w140 h50 text-center f_l bgc_e3e7f0 hmd_out cursor_">
								旺旺黑名单</div>
						</div>

					</div>
					<!----------下部---------->
					<div class="w1620 h675 bgc_fff p_t35 p_l40 p_b40 hmd_in">

						<!--------查询设置-------->
						<div class="font14 c_384856 h50 lh50">
							<div class="f_l m_r15">手机号码:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
							<div class="f_l m_r15">创建时间:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
							<div class="f_l">~</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r100" />
							<div
								class="w100 h50 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
								查询</div>
						</div>
						<div class="font14 c_384856 h50 lh50 m_t20 m_b20">
							<div class="f_l m_r15 m_l28">备注:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
						</div>

						<!--------操作选项-------->
						<div class="lh52 font14 h52 m_b15">

							<div
								class="w100 h52 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
								添加</div>
							<div
								class="w100 h50 b_radius5 bgc_fff border_00a0e9 c_00a0e9 text-center f_l cursor_ m_l20 m_r20">
								批量导入</div>
							<div
								class="w100 h50 b_radius5 bgc_fff border_00a0e9 c_00a0e9 text-center f_l cursor_">
								删除</div>
						</div>


						<!--------详细数据-------->
						<div class="c_384856">
							<table>
								<tr class="">
									<th class="position_relative z_1 w65 p_l10 ">
										<div
											class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="p_l20">全选</div>
									</th>
									<th class="w285">手机号码</th>
									<th class="w630">备注</th>
									<th class="w290">创建时间</th>
								</tr>								
								<c:forEach items='${blacklist}' var='blacklist'>
									<tr class="">
										<td class="position_relative p_l10 z_1 w65">
											<div
												class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
												<img class="cursor_ one_check_ display_none"
													src="${ctx}/crm/images/black_check.png" />
											</div>
											<div></div>
										</td>
										<td class="w285">${blacklist.phone }</td>
										<td class="w630">${blacklist.remarks }</td>
										<td class="w290">${blacklist.createdate }</td>
									</tr>
								</c:forEach>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w285"></td>
									<td class="w630"></td>
									<td class="w290"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w285"></td>
									<td class="w630"></td>
									<td class="w290"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w285"></td>
									<td class="w630"></td>
									<td class="w290"></td>
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

					<!----------下部---------->
					<div class="w1620 h675 bgc_fff p_t35 p_l40 p_b40 hmd_in">

						<!--------查询设置-------->
						<div class="font14 c_384856 h50 lh50">
							<div class="f_l m_r15">旺旺号码:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
							<div class="f_l m_r15">创建时间:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r10" />
							<div class="f_l">~</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_l10 m_r100" />
							<div
								class="w100 h50 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
								查询</div>
						</div>
						<div class="font14 c_384856 h50 lh50 m_t20 m_b20">
							<div class="f_l m_r15 m_l28">备注:</div>
							<input class="bgc_f4f6fa border0 w250 h50 f_l m_r35" />
						</div>

						<!--------操作选项-------->
						<div class="lh52 font14 h52 m_b15">

							<div
								class="w100 h52 b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_">
								添加</div>
							<div
								class="w100 h50 b_radius5 bgc_fff border_00a0e9 c_00a0e9 text-center f_l cursor_ m_l20 m_r20">
								批量导入</div>
							<div
								class="w100 h50 b_radius5 bgc_fff border_00a0e9 c_00a0e9 text-center f_l cursor_">
								删除</div>
						</div>


						<!--------详细数据-------->
						<div class="c_384856">
							<table>
								<tr class="">
									<th class="position_relative z_1 w65 p_l10 ">
										<div
											class="cursor_ all_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="p_l20">全选</div>
									</th>
									<th class="w285">手机号码</th>
									<th class="w630">备注</th>
									<th class="w290">创建时间</th>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w285"></td>
									<td class="w630"></td>
									<td class="w290"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w285"></td>
									<td class="w630"></td>
									<td class="w290"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w285"></td>
									<td class="w630"></td>
									<td class="w290"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w285"></td>
									<td class="w630"></td>
									<td class="w290"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w285"></td>
									<td class="w630"></td>
									<td class="w290"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w285"></td>
									<td class="w630"></td>
									<td class="w290"></td>
								</tr>
								<tr class="">
									<td class="position_relative p_l10 z_1 w65">
										<div
											class="cursor_ one_check w20 h20 border_d9dde5 b_radius5 position_absolute top15">
											<img class="cursor_ one_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div></div>
									</td>
									<td class="w285"></td>
									<td class="w630"></td>
									<td class="w290"></td>
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


			<!---------------添加黑名单弹窗--------------->
			<div
				class="w1673 h1000 bgc_e3e7f0 position_absolute z_5 top40 hmd display_none">


				<div
					class="w590 h460 bgc_fff margin0_auto m_t100 p_t20 p_l20 p_r20 p_b40">


					<div class="text-center c_384856 font16 m_b20 h22 lh22">
						添加黑名单</div>

					<!--添加黑名单选中状态-->
					<div class="m_b20 h22">
						<div class="c_384856 font16 f_l">黑名单类型：</div>
						<div class="f_l c_a8b2c0 font14 black_uncheck cursor_">
							<div class="w20 h20 b_radius20 bgc_e0e6ef f_l">
								<img class="display_none black_check"
									src="${ctx}/crm/images/black_check.png" />
							</div>
							<div class="f_l m_l10">买家昵称</div>
						</div>
						<div class="f_l c_a8b2c0 font14 m_l20 black_uncheck cursor_">
							<div class="w20 h20 b_radius20 bgc_e0e6ef f_l">
								<img class="display_none black_check"
									src="${ctx}/crm/images/black_check.png" />
							</div>
							<div class="f_l m_l10">手机号码</div>
						</div>
					</div>

					<!--黑名单输入框-->
					<textarea class="w589 bgc_f4f6fa h285 border0 m_b55 hmd_text"></textarea>

					<!--保存取消按钮-->
					<div class="w232 h42 margin0_auto">
						<div
							class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ save_hmd">保存</div>
						<div
							class="m_l30 cancel_hmd f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8">取消</div>
					</div>

				</div>

			</div>


			<!---------------导入黑名单弹窗--------------->
			<div
				class="w1673 h1000 bgc_e3e7f0 position_absolute z_5 top40 upload display_none">

				<div
					class="w590 h200 bgc_fff margin0_auto m_t100 p_t20 p_l20 p_r20 p_b20">

					<div class="text-center c_384856 font16 m_b20 h22 lh22">
						黑名单导入</div>

					<!--文件导入-->
					<div class="w480 h42 margin0_auto">
						<div class="f_l h42 lh42 m_r5 font16">上传文件:</div>
						<div class="w300 h42 f_l lh42 m_r5 cursor_ b_radius5 bgc_f4f6fa"></div>
						<div
							class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ font16">选择文件</div>
					</div>
					<div class="c_ff6363 text-center m_t10 m_b20 font14">
						注：此处请导csv，txt，xls文件</div>

					<div class="w232 h42 margin0_auto">
						<div
							class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ save_upload font16">确定</div>
						<div
							class="m_l30 cancel_upload f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
					</div>

				</div>

			</div>


			<!---------------发送短信弹窗--------------->
			<div
				class="w1673 h1000 bgc_e3e7f0 position_absolute z_5 top40 send_text_window display_none">

				<div
					class="w1060 h530 bgc_fff margin0_auto m_t100 p_t20 p_l20 p_r20 p_b20 position_relative">

					<div class="text-center c_384856 font16 m_b20 h22 lh22">短信发送
					</div>

					<div class="m_l70 h260">
						<div class="f_l c_384856 m_r10">短信内容:</div>
						<textarea
							class="p_l10 p_r10 p_t10 p_b25 f_l w710 h225 bgc_f4f6fa border0"></textarea>

					</div>
					<div
						class="w80 h20 bgc_ececec lh20 position_absolute right230 top300">
						<div class="f_l">退订回</div>
						<div class="f_l">
							"<input maxlength="2"
								class="w15 text-center border0 bgc_ececec c_384856" />"
						</div>
					</div>


					<div class="c_00a0e9 font14 m_t10 text-right p_r200">
						<span class="cursor_">转化为短连接</span> <span
							class="m_l30 m_r25 cursor_">引用短语库</span> <span class="cursor_">另存为短语库</span>
					</div>

					<div class="c_384856 font14 p_l70">
						<span>短信签名：</span> <span>${ShopName}</span>
					</div>



					<div class="c_ff6363 p_l150 m_t10 m_b20 font14">
						根据运营政策要求，所有短信签名前置，且不可频繁变更修改签名，如需修改签名请联系客服申请</div>

					<div class="c_384856 font14 h50 lh50 p_l70 m_b30">
						<span>手机号码：</span> <input class="w500 h50 border0 bgc_f4f6fa" />
					</div>

					<div class="w232 h42 margin0_auto">
						<div
							class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ save_text font16">保存</div>
						<div
							class="m_l30 cancel_text f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
					</div>

				</div>

			</div>


			<!---------------创建分组弹窗--------------->
			<div
				class="w1690 h1000 bgc_e3e7f0 position_absolute z_5 top40 add_group display_none">

				<div
					class="w1020 h650 bgc_fff margin0_auto m_t100 p_t20 p_l20 p_r20 p_b20">


					<!----------标题---------->
					<div class="c_384856 font18 text-center m_b55 m_t35">创建分组</div>

					<!----------筛选---------->
					<div>
						<!----------块1---------->
						<div class="c_384856 h22 m_b40">
							<div class="w60 text-right f_l font14 m_r30 h22 lh22">
								最近交易:</div>
							<div class="f_l font16 h22">
								<!----------选择区域1---------->
								<ul>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">不限</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近7天</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近1个月</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近3个月</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近半年</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">自定义</div>
									</li>
								</ul>
							</div>
						</div>


						<!----------块2---------->
						<div class="c_384856 h22 m_b40">
							<div class="w60 text-right f_l font14 m_r30 h22 lh22">
								交易次数:</div>
							<div class="f_l font16 h22">
								<!----------选择区域1---------->
								<ul>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">不限</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">0次</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">1次</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">2次</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">3次</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">3次以上</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">自定义</div>
									</li>
								</ul>
							</div>
						</div>

						<!----------块3---------->
						<div class="c_384856 h22 m_b40">
							<div class="w60 text-right f_l font14 m_r30 h22 lh22">
								最近交易:</div>
							<div class="f_l font16 h22">
								<!----------选择区域1---------->
								<ul>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">不限</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近7天</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近1个月</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近3个月</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近半年</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">自定义</div>
									</li>
								</ul>
							</div>
						</div>

						<!----------块4---------->
						<div class="c_384856 h22 m_b40">
							<div class="w60 text-right f_l font14 m_r30 h22 lh22">
								会员等级:</div>
							<div class="f_l font16 h22">
								<!----------选择区域1---------->
								<ul>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">所有会员</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">店铺会员</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">普通会员</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">高级会员</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">VIP会员</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">至尊VIP会员</div>
									</li>
								</ul>
							</div>
						</div>

						<!----------块5---------->
						<div class="c_384856 h22 m_b40">
							<div class="w60 text-right f_l font14 m_r30 h22 lh22">地区:</div>
							<div class="f_l font16 h22">
								<!----------选择区域1---------->
								<ul>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">不限</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近7天</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近1个月</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近3个月</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">近半年</div>
									</li>
									<li class="h22 lh22 f_l">
										<div
											class="cursor_ group_check w20 h20 border_d9dde5 b_radius5 f_l">
											<img class="cursor_ group_check_ display_none"
												src="${ctx}/crm/images/black_check.png" />
										</div>
										<div class="f_l w100 text-left m_l10">自定义</div>
									</li>
								</ul>
							</div>
						</div>

					</div>




					<div class="m_b20">
						<div class="w60 text-right c_384856 f_l font14 m_r30 h50 lh50">
							分组名称:</div>
						<input
							class="group_name w500 h50 lh50 border0 bgc_f4f6fa b_radius5" />

					</div>

					<div>
						<div class="w60 text-right c_384856 f_l font14 m_r30 h50 lh50">
							说明:</div>
						<input class="explain w500 h50 lh50 border0 bgc_f4f6fa b_radius5" />

					</div>

					<!--保存取消按钮-->
					<div class="w232 h42 margin0_auto m_t40">
						<div
							class="w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ save_group">保存</div>
						<div
							class="m_l30 cancel_group f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8">取消</div>
					</div>







				</div>

			</div>







		</div>

	</div>

	</div>






	<!---------------取消打折--------------->
	<div
		class="w100_ h1300 rgba_000_5 position_absolute z_10 top0 display_none cancel_discount_window">

		<div
			class="w590 h200 bgc_fff margin0_auto position_fixed top250 left0 right0 p_t20 p_l20 p_r20 p_b20">


			<div class="text-right">
				<img class="cursor_ cancel_discount_chat_windowbtn"
					src="${ctx}/crm/images/close.png" />
			</div>
			<div class="text-center c_384856 font16 m_b20 h22 lh22">提示：</div>
			<div class="text-center c_384856 font16 m_b20 h22 lh22">
				确定取消打折吗？</div>



			<div class="w232 h42 margin0_auto">
				<div
					class="cancel_discount_save w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ font16">确定</div>
				<div
					class="cancel_discount_cancel m_l30 f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
			</div>

		</div>

	</div>






	<!---------------保存打折--------------->
	<div
		class="w100_ h1300 rgba_000_5 position_absolute z_10 top0 display_none save_discount_window">

		<div
			class="w590 h200 bgc_fff margin0_auto position_fixed top250 left0 right0 p_t20 p_l20 p_r20 p_b20">


			<div class="text-right">
				<img class="cursor_ cancel_discount_chat_windowbtn"
					src="${ctx}/crm/images/close.png" />
			</div>
			<div class="text-center c_384856 font16 m_b20 h22 lh22">提示：</div>
			<div class="text-center c_384856 font16 m_b20 h22 lh22">
				确定参与会员打折吗？</div>



			<div class="w232 h42 margin0_auto">
				<div
					class="save_discount_save w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ font16">确定</div>
				<div
					class="save_discount_cancel m_l30 f_l border_cad3df cursor_ w100 h40 lh40 text-center b_radius5 c_8493a8 font16">取消</div>
			</div>

		</div>

	</div>







	<div class="chat_window display_none">
		<div class="pew">

			<div class="bor_b_2_bfbfbf h53">

				<span class="font18 m_r100">会员互动</span> <span class="font14">手机号：</span>
				<span class="font14 m_r30">13911111111</span> <span class="font14">买家：</span>
				<img class="cursor_" src="${ctx}/crm/images/wangwang.png" /> <span
					class="font14 m_r32 cursor_">xxxxxx</span> <img
					class="close_chat_window cursor_" src="${ctx}/crm/images/close_wangwang.png" />

			</div>

		</div>

		<div
			class="b_radius5 overflow-y w490 h280 bgc_fff margin0_auto p_l15 p_r15 p_t15 p_b15">

			<div class="f_r m_b20">
				<!----------发送内容---------->
				<div
					class="f_l m_r10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">

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
				<div
					class="f_r m_l10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">

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
				<div
					class="f_l m_r10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">

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
				<div
					class="f_r m_l10 b_radius5 w310 bgc_f4f4f4 c_384856 font14 p_t10 p_l10 p_r10 p_b15">

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
			<span>注：加签注【关欣康家用医疗器材】已输入<span class="c_ff6363 text_count">0</span>个字，预计扣费<span
				class="c_ff6363">1</span>条短信
			</span> <span class="cursor_ f_r">计费规则</span>
		</div>


		<div class="w520 margin0_auto h95 margin0_auto position_relative">


			<!----------内容输入区---------->
			<textarea
				class="text_area b_radius5 p_l10 p_r10 p_t10 p_b25 f_l w500 h60 bgc_f4f6fa border0"></textarea>


			<div class="w80 h20 bgc_ececec lh20 position_absolute right20 top70">
				<div class="f_l">退订回</div>
				<div class="f_l">
					"<input maxlength="2"
						class="w15 text-center border0 bgc_ececec c_384856" />"
				</div>
			</div>

			<div class="c_00a0e9 f_r font14 m_t10 text-right m_b40">
				<span class="cursor_">转化短网址</span> <span class="m_l30 m_r25 cursor_">引用短语库</span>
				<span class="cursor_">另存为短语库</span>
			</div>

			<div class="w232 h42 margin0_auto">
				<div
					class="save_chat w100 h42 f_l lh42 text-center c_fff bgc_00a0e9 b_radius5 cursor_ save_hmd">确认保存</div>
				<div
					class="cancel_chat m_l30 cancel_hmd f_l border_00a0e9 cursor_ w100 h40 lh40 text-center b_radius5 c_00a0e9">取消</div>
			</div>


		</div>



	</div>



</body>
</html>
