<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
	<title>店铺数据-历史订单导入</title>
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
	<script type="text/javascript" src="${ctx}/crm/js/load_history_order.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery-1.10.1.min.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.form.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate.js" ></script>
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
					<div class="w100_ h48 lh50 bgc_fff font18">
						<div class="f_l h50 w4 bgc_1d9dd9"></div>
						<div class="f_l m_l15 c_384856">
							店铺数据
						</div>
						<div class="f_l m_r50 c_384856" style="margin-left: 5vw;">
							<a class="c_384856" href="${ctx}/crms/storeData/todyOrderList">
								<div class="f_l w140 text-center cursor_">
									订单列表
								</div>
							</a>
							<a class="c_384856" href="${ctx}/OrderHistoryImport/showLoadHistoryOrder">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									历史订单导入
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/shopData/smsSendRecord">
								<div class="f_l w140 text-center cursor_">
									短信发送记录
								</div>
							</a>
							<a class="c_384856" href="${ctx}/shopData/reportJsp">
								<div class="f_l w140 text-center cursor_">
									短信账单
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/storeData/todyData">
								<div class="f_l w140 text-center cursor_">
									历史数据
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/storeData/operationLog">
								<div class="f_l w140 text-center cursor_ ">
									操作日志
								</div>
							</a>
						</div>
					</div>
					
					
					<!---------------历史订单导入--------------->
					<div class="h940">
						<!----------上部---------->
						<div class="w1605 h80 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								历史订单导入
							</div>
							<!---------------描述--------------->
							<div class="font14">
								初次使用，需导入3个月以前的历史订单数据
							</div>
							
						</div>
						<!----------下部---------->
					
						<div class="w1605 h705 bgc_fff  p_l40 p_b40 discount_in m_b30">
							
							<div class="" style="overflow:hidden;">
								<div class="m_b10 m_t20 h50">
									<!-- <button class="history_list_btn tk c_00a0e9 border_00a0e9 f_l cursor_ w150 h50 lh50 font14 bgc_fff b_radius5">
									历史订单导入统计
									</button> -->
									<div class="f_l w1090 m_l10">
										<p class="font14 c_ff6363" style="font-size:0.8333vw;">
											<strong>【重要提示】</strong>根据淘宝要求，超过7天未登陆软件将停止订单数据推送，会导致订单中心不能正常发送催付物流短信，数据查询出现误差等问题。为确保功能正常使用，请务必每个店铺每周至少登陆1次软件。
										</p>
									</div>	
								</div>
								<!--------使用帮助-------->
								<div class="m_b22 font14 c_384856">
									
									<p>使用帮助:</p>
									<p class="m_t10 f_l">
										<span>第一步：CRM已自动为您同步3个月之内的历史订单，3个月之前的历史订单需要亲手动导入, 只能同步本店铺订单报表。</span>
										<!-- <span class="c_00a0e9 cursor_ downloadOrder">下载历史订单</span> -->
									</p>
									<button class=" f_l cursor_ w100 h40 lh40 font14 c_fff bgc_00a0e9 border0 b_radius5 downloadOrder">下载历史订单</button>
									<div style="clear:both;"></div>
									<p class="m_t10 m_b10">
										第二步：点击上传文件选择已下载好的<strong style="color:red">订单报表文件</strong>（ExportOrderListxxxxxxxx.csv）上传即可,<strong style="color:red">请勿更改文件内容及格式</strong>,上传过程中可以浏览其他页面。
									</p>
									
									<p class="c_ff6363">
										<!-- 请注意：确保您所上传的订单报表文件与宝贝报表文件对应，不同批次的可能会导致上传失败。 -->
									</p>
									
									<%-- <div class="upload_text_btn">
									<button class=" m_b10 cursor_ w140 h50 m_t20 lh50 font14 c_fff bgc_00a0e9 border0 b_radius5">
										<img class="m_r20" src="${ctx}/crm/images/upload.png" />
										上传文件
									</button>
									</div> --%>
									
									<div class="f_l m_b10 cursor_ w140 h50 m_t20 lh50 font14 c_fff bgc_00a0e9 border0 b_radius5" onclick="document.form1.picpath.click()">
										<form name="form1" action="addStartPage.action" id="form1" method="post">
											<img style="width:1vw;" class="m_l20" src="${ctx}/crm/images/upload.png" />
											 <input type="file" name="file" id="picpath" style="display:none;" onChange="document.form1.path.value=this.value;addMore();">
											 <input type="hidden" name="path" id="path"  readonly >
											 <span class="btn btn1" style="margin-left:1vw;">文件上传</span> 
										</form>
									</div>
									<p class="f_l" style="color:#9fa3b5;padding: 1.7vw 1vw;font-size: 0.8vw;">(请将文件大小限制在50M以内，数据量过大需分批下载并上传。)</p>
									<p class="clear"></p>
									<!-- <div id="send_import" class="w100 f_l m_l10 cursor_ h40 lh40 bgc_00a0e9 c_fff text-center b_radius5">
										<form name="form1" action="addStartPage.action" id="form1">
												 <input type="file" name="file" id="picpath" style="display:none;" onChange="document.form1.path.value=this.value;addMore();">
												<input type="hidden" name="path" id="path"  readonly >
												 <span class="btn btn1" style="margin-left:10px;" onclick="document.form1.picpath.click()">文件上传:</span> 
										</form>
									</div> -->
									
									<!-- <div class="m_b10 m_t20 h50">
										<div id="uploadFile" class="f_l tk w100 cursor_ upload_mail_list_btn h40 lh40 text-center b_radius5 border_00a0e9 c_00a0e9">
											选择文件
										</div>
										<div class="f_l lh40 m_l10 m_r10 c_cecece">
											支持xlsx、xls格式
										</div>
										<div class="f_l w110 hover_example cursor_ h40 lh40 text-center b_radius5 bgc_00a0e9 c_fff">
											模板事例
										</div>
									</div> -->
									
									<!--------查询设置-------->
									<div class="font14 c_384856 h50 lh50 m_b40" style="margin-bottom:1vw;">
										<div class="f_l m_r15">
											时间范围:
										</div>
										
										<div class="f_l position_relative">
											<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_r10" type="text" id="tser01"  value="${beginTime}" readonly onclick="$.jeDate('#tser01',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val) {valiteTwoTime();}})">
											<img style="width:1vw;" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png" />
										</div>
											
										<div class="f_l">~</div>
										
										<div class="f_l position_relative">
											<input class="bgc_f4f6fa border0 w240 p_l10 h50 m_l10 m_r40" type="text" id="tser02" value="${endTime}"  readonly onclick="$.jeDate('#tser02',{insTrigger:false,isTime:true,format:'YYYY-MM-DD',choosefun:function(elem, val) {valiteTwoTime();}})">
											<img style="width:1vw;" class="position_absolute right50 top15" src="${ctx}/crm/images/date_copy.png" />
										</div>
								
								
										<div class="f_l m_r15 m_l40">
											文件名称:
										</div>
										<input id="order_fileName_input" value="${orderName }" class="bgc_f4f6fa border0 w250 h50 f_l m_r100" />
										<div id="find_orderHistory" class="w100 h50 b_radius5 border_00a0e9 c_00a0e9 tk text-center f_l cursor_">
											查询
										</div>
										
										<form id="submit_form" action="${ctx}/OrderHistoryImport/showLoadHistoryOrder" method="post">
											<input id="order_fileName" type="hidden" name="orderName" />
											<input id="order_beginTime" type="hidden" name="beginTime" />
											<input id="order_endTime" type="hidden" name="endTime" />
										</form>
									</div>
									
									
									
									
								</div>
								
								<!--------详细数据-------->
								<div class="c_384856">
									<p style="height:1.5vw;line-height:1.5vw;margin-bottom:1vw;">文件上传完成后，您可以点击<strong style="color:red;">更新数据</strong>查看最新进度</p>
									<table id="fileTable">
									  <tr class="">
									    <th class="w75 text-center">
									    	序号
									    </th>
									    <th class="w250">订单文件</th>
									   <!--  <th class="w270">宝贝文件</th> -->
									    <th class="w200">导入时间</th>
									    <th class="w100">总行数</th>
									    <th class="w100">完成数</th>
									    <th class="w100">失败数</th>
<!-- 									    <th class="w100">重复数</th> -->
									    <th class="w150">处理状态</th>
									    <th class="w150" style="width:15vw;">操作</th>
									  </tr>
									 <c:if test="${paginationOrderImport.list.size() ==0  }">
									 	<tr>
										     <td align="center" colspan="9">暂时没有相关数据!</td>
									    </tr>
									 </c:if>
									  <c:if test="${paginationOrderImport != null }">
									   <c:forEach items="${paginationOrderImport.list }" var="orderImportList" varStatus="status">
									  	<tr class="">
										    <td class="w75 text-center">
										    	${status.index+1 }
										    </td>
										    <td class="text-center">${orderImportList.orderName }</td>
										   <%--  <td class="w270 text-center">${orderImportList.commodityName }</td> --%>
										    <td class="text-center"><fmt:formatDate value="${orderImportList.createdDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										    <td class="text-center" id="totalRowNum${orderImportList.id}">${orderImportList.sumNumber }</td>
										    <td class="text-center" id="completeNum${orderImportList.id}">${orderImportList.completedQuantity+orderImportList.repetitionNumber }</td>
										    <td class="text-center" id="failNum${orderImportList.id}">${orderImportList.errorNumber }</td>
<%-- 										    <td class="text-center">${orderImportList.repetitionNumber }</td> --%>
										    <td class="text-center" id="statetype${orderImportList.id}">
										   		<c:if test="${orderImportList.state == 0 }">
										   			导入完成
										    	</c:if>
												<c:if test="${orderImportList.state == 1 }">
										    		导入中
										    	</c:if>
											</td>
										    <td class="text-center">
										    	<c:if test="${orderImportList.state == 0 }">
										   			<div id="updata" data-id="${orderImportList.id}" class="active f_l m_l25 cursor_ w100 h40  lh40 font14 c_fff bgc_00a0e9 border0 b_radius5">
														更新数据
													</div>
										    	</c:if>
												<c:if test="${orderImportList.state == 1 }">
										    		<div id="updata" data-id="${orderImportList.id}" class=" f_l m_l25 cursor_ w100 h40  lh40 font14 c_fff bgc_00a0e9 border0 b_radius5">
														更新数据
													</div>
										    	</c:if>
										   	 	
											    <div onclick="deleteOrder('${orderImportList.id}')" class="f_l m_l25 cursor_ w100 h40  lh40 font14 c_fff bgc_00a0e9 border0 b_radius5">
														删除
												</div>
										    </td>
										  </tr>
									 	</c:forEach> 
									 </c:if> 
									  <!-- <tr class="">
									    <td class="w75 text-center">
									    	2
									    </td>
									    <td class="w180 text-center"></td>
									    <td class="w270 text-center"></td>
									    <td class="w210 text-center"></td>
									    <td class="w205 text-center"></td>
									    <td class="w180 text-center"></td>
									    <td class="w150 text-center"></td>
									  </tr>
									  <tr class="">
									    <td class="w75 text-center">
									    	3
									    </td>
									    <td class="w180 text-center"></td>
									    <td class="w270 text-center"></td>
									    <td class="w210 text-center"></td>
									    <td class="w205 text-center"></td>
									    <td class="w180 text-center"></td>
									    <td class="w150 text-center"></td>
									  </tr>
									  <tr class="">
									    <td class="w75 text-center">
									    	4
									    </td>
									    <td class="w180 text-center"></td>
									    <td class="w270 text-center"></td>
									    <td class="w210 text-center"></td>
									    <td class="w205 text-center"></td>
									    <td class="w180 text-center"></td>
									    <td class="w150 text-center"></td>
									  </tr>
									  <tr class="">
									    <td class="w75 text-center">
									    	5
									    </td>
									    <td class="w180 text-center"></td>
									    <td class="w270 text-center"></td>
									    <td class="w210 text-center"></td>
									    <td class="w205 text-center"></td>
									    <td class="w180 text-center"></td>
									    <td class="w150 text-center"></td>
									  </tr> -->
									</table>
								</div>
								
								<!--------分页-------->
	                            <div class="w1280 h24 m_t22 font14 c_8493a8 m_b40">
	                                <!-- <div class="f_l w220 h24 l_h24">共0条记录，共1页，当前为第1页</div> -->
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
	                                    <c:forEach items="${paginationOrderImport.pageView }" var="page">
												${page } 
								  		</c:forEach>
	                            </div>
								
										
									
									
									
							</div>
								
								
								
								
						</div>
							
							
							
							
					</div>
				
					
				</div>	
				
					
					
					

					
					
					
					
					
					
						
				</div>
		
			</div>
	
	</div>
<div class="position_absolute top0 z_5 top200  display_none hover_example_hide" style="left: 620px;">
	<img src="${ctx}/crm/images/example.png" />
</div>
	
	

					










<!-- 上传文件弹框-->					

<div class="w100_ h1100 rgba_000_5 position_absolute z_10 upload_text top0 display_none">
	<div class="w660  bgc_fff m_l500  " style="margin-top: 13.208vw;">
		<p class="h60 l_h55 w660 text-center font_bold font16">文件上传</p>
		<div class="m_l45">
			<div class="   m_r45 bor_b_cecece"></div>
			<div class=" m_r45 h150">
				<div class=" m_t40 m_r50 f_l">
					<div>
						<span class="c_cecece font14 m_r60">ExportOrderListxxxxx2xx.csv</span>
						<span class="c_cecece font14 m_r50">1.85mb</span>
						<span class="c_22ee00 font14 ">上传成功</span>
					</div>
					<div class="m_t30">
						<span class="c_cecece font14 m_r60">ExportOrderListxxxxxxxx.csv</span>
						<span class="c_cecece font14 m_r50">1.35mb</span>
						<span class="c_ff6363 font14 ">上传失败</span>
					</div>
					
				</div>
				<div class="m_t30 f_l">
					<button class=" cursor_ w100 h40  lh40 font14 c_fff bgc_00a0e9 border0 b_radius5">
						文件上传
					</button>
					<!-- <div class=" cursor_ w100 h40  lh40 font14 c_fff bgc_00a0e9 border0 b_radius5">
						<form name="form1" action="addStartPage.action" id="form1">
							 <input type="file" name="file" id="picpath" style="display:none;" onChange="document.form1.path.value=this.value;addMore();">
							 <input type="hidden" name="path" id="path"  readonly >
							 <span class="btn btn1" style="margin-left:10px;" onclick="document.form1.picpath.click()">文件上传</span> 
						</form>
					</div> -->
				</div>
			</div>
			<div class="  m_r45 bor_b_cecece"></div>
			
			<p class="font12 m_t20 c_cecece">文件大小控制在50mb以内，数据量过大建议分批下载并上传。</p>
			<p class="font12 c_ff6363 m_t10">注意：1.上传文件无需修改文件名称</p>
			<p class="font12 c_ff6363 m_t10 m_l35">2.请确保订单文件和宝贝文件同时上传，点击开始上传即可</p>
			
			<div class="m_t40 text-center close">
					<button class="m_b20 cursor_ w100 h40  lh40 font14 c_fff bgc_00a0e9 border0 b_radius5">
												
												开始上传
											</button>
			</div>
		
		
		</div>
	</div>
</div>	
	
	
	
	

					

	
	
<!---------------历史订单导入弹窗--------------->
<div id="historyShow" class="w100_ h1100 rgba_000_5 position_fixed z_10 history_list top0 display_none">
	
	<div class="w1030 h610 bgc_fff margin0_auto m_t100 p_t35 p_l40 p_r20 p_b20">
		<div>
			<img class="f_r cursor_   close" src="${ctx}/crm/images/chazi.png" style="width:2.1vw;margin-right: -1.781vw;margin-top: -2.5125vw;">
			</div>
		
		<div class="m_b20 h42 m_t20">
			
			<div class="cursor_ out w168 h40 lh40 f_l bgc_00a0e9 font14 b_radius5 c_fff border_cad3df text-center ">
				缺少手机号会员统计
			</div>
			
			<div class="cursor_ out w168 m_r95 h40 lh40 f_l bgc_fff font14 b_radius5 c_cad3df border_cad3df text-center ">
				历史订单导入统计
			</div>
			
			<!-- <div class="cursor_ close w100 m_r40 h42 lh42 f_l bgc_00a0e9 font14 b_radius5 c_fff text-center">
				保存
			</div> -->
			
			<!-- <div class="cursor_ close w98 h40 lh40 f_l bgc_fff font14 b_radius5 c_cad3df border_cad3df text-center ">
				关闭
			</div> -->
			
			
		</div>
		
		<div class="in">
			<div class="h47">
				<div class="f_l h45 l_h45 m_r15 c_384856 m_l40">
					年份：
				</div>
				<div class="wrap f_l h45 w130">
				    <div class="nice-select h45 w130" name="nice-select">
				    	<input type="text h45 w130" value="" readonly>
				    	<ul>
				    		<li>2016</li>
				    		<li>2015</li>
				      		<li>2014</li>
				    		<li>2013</li>
				    		<li>2012</li>
				    	</ul>
				  	</div>
				</div>
			</div>
			
			<p class="font12 c_8493a8 p_l40 m_t20">
				表中展示每个月份没有手机号的会员有多少，可通过导入历史订单补全会员手机号
			</p>
		</div>
		
		<div class="in display_none">
			<div class="h47">
				<div class="f_l h45 l_h45 m_r15 c_384856 m_l40">
					年份：
				</div>
				<div class="wrap f_l h45 w130">
				    <div class="nice-select h45 w130" name="nice-select">
				    	<input type="text h45 w130" value="" readonly>
				    	<ul>
				    		<li>2016</li>
				    		<li>2015</li>
				      		<li>2014</li>
				    		<li>2013</li>
				    		<li>2012</li>
				    	</ul>
				  	</div>
				</div>
			</div>
			
			<p class="font12 c_8493a8 p_l40 m_t20">
				表中展示每个月份没有手机号的会员有多少，可通过导入历史订单补全会员手机号
			</p>
		</div>
	
		
	</div>
	
</div>
					
	
	
	

	
				
</body>
<script>
	$(function(){
		$(".load_top").load("top.html");
		$(".load_left").load("left.html");
		$('#fileTable').on('click','#updata',function(){
			if($(this).hasClass('active'))return;
			var that=$(this);
			$.ajax({
				type:'post',
				url:'${ctx}/OrderHistoryImport/findImportData',
				data:{
					id:$(this).attr('data-id')
				},
				success:function(data){
					var data=$.parseJSON(data);
					console.log(data);
					$('#totalRowNum'+data.data[0].id).text(data.data[0].sumNumber);
					$('#completeNum'+data.data[0].id).text(parseInt(data.data[0].completedQuantity+data.data[0].repetitionNumber));
					$('#failNum'+data.data[0].id).text(data.data[0].errorNumber);
					if(data.data[0].state==1){
						$('#statetype'+data.data[0].id).text('导入中');
						
					}else{
						$('#statetype'+data.data[0].id).text('导入完成');
						that.addClass('active');
					}
					
				}
			});
		});
	});
	
	
	//文件上传
	function addMore(){
		   var reg=new RegExp("(.csv)$");
		   if(!reg.test($("#picpath").val().toLowerCase())){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("请选择正确的文件类型!!!")
				setTimeout(function(){ 
					$(".tishi_2").hide()
					window.location.href="${ctx}/OrderHistoryImport/showLoadHistoryOrder";
				},2000)
		 		return;
		 	}
		
		var size = document.getElementById('picpath').files[0].size;
		if(size>52428800){
				$(".tishi_2").show();
				$(".tishi_2").children("p").text("文件过大，请重新下载并上传!")
				setTimeout(function(){ 
					$(".tishi_2").hide();
					window.location.href="${ctx}/OrderHistoryImport/showLoadHistoryOrder";
				},2000);
		}else{
			    $(".tishi_2").show();
				$(".tishi_2").children("p").text("导入中...请勿进行其他操作，否则上传可能失败。")
				
			 	$("#form1").attr("action", "${pageContext.request.contextPath}/OrderHistoryImport/uploadOrderFile");
			 	$("#form1").ajaxSubmit({
			 		type:'post',
					dataType:'json',
					success : function(data) {
						if(data.error=='0'){
				 				$(".tishi_2").show();
				 				$(".tishi_2").children("p").text("文件处理中...请点击更新数据查看进度");
				 				setTimeout(function(){ 
				 					$(".tishi_2").hide();
			 					window.location.href="${ctx}/OrderHistoryImport/showLoadHistoryOrder";
				 				},5000);
						}else if(data.error=='2'){
				 				$(".tishi_2").show();
				 				$(".tishi_2").children("p").text("文件过大，请重新下载并上传!")
				 				setTimeout(function(){ 
				 					$(".tishi_2").hide();
								window.location.href="${ctx}/OrderHistoryImport/showLoadHistoryOrder";
				 				},2000);
								return false;
						}else if(data.error=='3'){
			 					$(".tishi_2").show();
				 				$(".tishi_2").children("p").text("导入文件不符合订单文件要求!")
				 				setTimeout(function(){ 
				 					$(".tishi_2").hide();
								window.location.href="${ctx}/OrderHistoryImport/showLoadHistoryOrder";
				 				},5000);
								return false;
						}else if(data.error=='4'){
			 					$(".tishi_2").show();
				 				$(".tishi_2").children("p").text("确保数据准确，同时只能上传一个文件!")
				 				setTimeout(function(){ 
				 					$(".tishi_2").hide();
								window.location.href="${ctx}/OrderHistoryImport/showLoadHistoryOrder";
				 				},5000);
								return false;
						}else{
				 				$(".tishi_2").show();
				 				$(".tishi_2").children("p").text("导入失败,请联系客服！！！")
				 				setTimeout(function(){ 
				 					$(".tishi_2").hide();
								window.location.href="${ctx}/OrderHistoryImport/showLoadHistoryOrder";
				 				},2000);
								return false;
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						 var status = XMLHttpRequest.status;
						 if(status == 500){
							 $(".tishi_2").show();
				 				$(".tishi_2").children("p").text("导入异常,请联系客服！！！")
				 				setTimeout(function(){
				 					$(".tishi_2").hide();
								window.location.href="${ctx}/OrderHistoryImport/showLoadHistoryOrder";
			 				},2000);
						 }
				   }
				});
		}
	}
	
	
	//点击查询获取数据查询导入发送名单
	$(function(){
		$("#find_orderHistory").click(function(){
			//获取数据
			 var fileName = $("#order_fileName_input").val();
			 var beginTime = $("#tser01").val();
			 var endTime = $("#tser02").val();
			 //添加数据
			 $("#order_fileName").val(fileName);
			 $("#order_beginTime").val(beginTime);
			 $("#order_endTime").val(endTime);
			 
			 $("#submit_form").submit();
			
		});
	});
	
	
	//删除导入的记录
	function deleteOrder(id){
		if(confirm("确认删除吗?")){
				window.location.href="${ctx}/OrderHistoryImport/deleteOrderById?id="+id;
		  }
	}
	
	//计费规则新建标签跳转
	$(".downloadOrder").click(function(){
			window.open("https://tradearchive.taobao.com/trade/itemlist/list_export_order.htm");
	});
</script>
</html>