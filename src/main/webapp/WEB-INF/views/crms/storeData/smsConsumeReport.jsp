<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信账单报表</title>
	<%@ include file="/common/common.jsp"%>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		
	<!--兼容360meta-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta name="renderer" content="webkit">
	<link rel="stylesheet" href="${ctx}/crm/css/jedate6.css">
	<link rel="stylesheet" href="${ctx}/crm/css/laypage.css">
	<link rel="stylesheet" href="${ctx}/crm/css/smsbill.css">
	<script type="text/javascript" src="${ctx}/crm/js/jquery.jedate6.min.js" ></script>
	<script type="text/javascript" src="${ctx}/crm/js/laypage.js" ></script>	
<%-- 	<script type="text/javascript" src="${ctx}/crm/js/smsbill.js" ></script>	 --%>
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
								<div class="f_l w140 text-center cursor_">
									历史订单导入
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/shopData/smsSendRecord">
								<div class="f_l w140 text-center cursor_">
									短信发送记录
								</div>
							</a>
							<a class="c_384856" href="${ctx}/shopData/reportJsp">
								<div class="f_l w140 text-center cursor_ bgc_e3e7f0">
									短信账单
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/storeData/todyData">
								<div class="f_l w140 text-center cursor_ ">
								   	历史数据
								</div>
							</a>
							<a class="c_384856" href="${ctx}/crms/storeData/operationLog">
								<div class="f_l w140 text-center cursor_">
									操作日志
								</div>
							</a>
						</div>
					</div>
					
					
					<!---------------当日数据--------------->
					<div class="m_b30">
						<!----------上部---------->
						<div class="w1605 h70 bgc_f1f3f7 c_384856 p_l40 p_t30">
							
							<!---------------标题--------------->
							<div class="font24 m_b10">
								短信账单
							</div>
							<!---------------描述--------------->
							<div class="font14">
								查看店铺近三个月以内的订单交易信息。
							</div>
							
						</div>
						
						<div class="w1605 m_b30 bgc_fff p_t35  p_b40" style="width:85.65vw;">
							<div class="bsAdminBox">
					            <div class="smsbillSearchBox">
					                <div class="smsbillSearchTopDiv clearfix">
					                    <div class="smsbillSearchDiv clearfix">
					                        <label>短信账单时间按照</label>
					                        <div class="nice-select" name="nice-select" style="z-index: 100; margin: 0 0.8333vw;width: 5.208333vw;">
					                            <input type="hidden" value="">
												<p>日</p>
												<ul class="member-grouping-nav" style="display: none;">
					                                <li class="removeStatus" data-value="day">日</li>
					                                <li class="updateStatus" data-value="month">月</li>
					                                <li class="updateStatus" data-value="year">年</li>
												</ul>
					                        </div>
					                        <label>展示</label>
					                    </div>
					                    <div class="smsbillSearchDiv clearfix">
					                        <label>选择账单时间：</label>
					                        <div class="smsbillSearchDivTime">
					                            <div class="f_l position_relative"> 
					                                <input name="tradeStartTime" style="width: 9vw;" class="bgc_f4f6fa border0 p_l10 h45 m_r10 dianji tradeStartTime" type="text" id="tser01" readonly="">
					                                <img style="width: 1vw;top:0.25125vw" class="position_absolute right20 top15" src="${ctx}/crm/images/date_copy.png">
					                            </div>
					        
					                            <div class="f_l">至</div>
					        
					                            <div class="f_l position_relative">
					                                <input name="tradeEndTime" style="width: 9vw;" class="bgc_f4f6fa border0 p_l10 h45 m_l10 dianji tradeEndTime" type="text" id="tser02" readonly="">
					                                                        
					                                <img style="width: 1vw;top:0.25125vw;" class="position_absolute right10 top15" src="${ctx}/crm/images/date_copy.png">
					                            </div>
					                        </div>
					                    </div>
					                    <div class="smsbillSearchBtn">
					                        <a href="javascript:;" class="cx">查询</a>
					                        <a href="${ctx}/shopData/report" class="dc">导出</a>
					                    </div>
					                </div>  
					                <div class="smsbillSearchTopDiv clearfix">
					                    <i class="smsbillSearchIcone"></i>
					                    <span class="smsbillNum">账单时间内，共发送<strong class="smsNum">15000</strong>条短信</span>
					                </div>  
					            </div>
					            <div class="smsbillTableBox">
					                <table class="smsbillTable">
					                    <tr class="trone">
					                        <th class="thone">时间</th>
					                        <th class="thtwo"> 扣除短信条数（条）</th>
					                        <th class="ththree">短信明细</th>
					                    </tr>
					                   
					                </table>
					                <div id="smsbillTablePage">
					
					                </div>
					            </div>
					        </div>
						</div>
					
					
					</div>
					
				</div>	
					
						
				</div>
				<div class="position_fixed  left0 display_none right0 top40_ z_21 tishi_2 text-center font30 c_fff" id="error" style="display: none;">
					<p class="tishi_2_text bgc_000000 padding20 radius10" style="padding: 1vw;display: inline;">账单开始时间不能小于结束时间！</p>
				</div>
			</div>
			
	</div>
	<input type="hidden" value="${ctx}" id="url">
	<input type="hidden" value="day" id="dateType">
</body>
<script>

$(function(){
    var start={
        trigger:"", 
		isTime:true,
        format:'YYYY-MM-DD',
        choosefun: function(elem, val, date){

            end.minDate = val; //开始日选好后，重置结束日的最小日期
        }
    };	
    var end={
        trigger:"", 
		isTime:true,
        format:'YYYY-MM-DD', //最大日期
        choosefun: function(elem, val, date){

            start.maxDate = val; //将结束日的初始值设定为开始日的最大日期
        }	
    }
    /*下拉框*/
    $('[name="nice-select"]').click(function(e){
        if($(this).hasClass('disabled'))return;
        $('[name="nice-select"]').find('ul').hide();
        $(this).find('ul').show();
        e.stopPropagation();
    });
    $('[name="nice-select"] li').hover(function(e){
        
        $(this).toggleClass('on');
        e.stopPropagation();
    });
    $('[name="nice-select"] li').click(function(e){
        var thisTxt=$(e.target).text();
        var dataVal = $(this).attr("data-value");
        $(this).parents('[name="nice-select"]').find('p').text(thisTxt);
        $(this).parents('[name="nice-select"]').find('input').val(dataVal);
        $('#dateType').val(dataVal);
        $('#tser01').val('');
        $('#tser02').val('');
        if(dataVal=='day'){
            start={
                trigger:"", 
                isTime:true,
                format:'YYYY-MM-DD',
                choosefun: function(elem, val, date){
        
                    end.minDate = val; //开始日选好后，重置结束日的最小日期
                }
            };	
            end={
                trigger:"", 
                isTime:true,
                format:'YYYY-MM-DD', //最大日期
                choosefun: function(elem, val, date){
        
                    start.maxDate = val; //将结束日的初始值设定为开始日的最大日期
                }	
            }
        }else if(dataVal=='month'){
            start={
                trigger:"", 
                isTime:true,
                format:'YYYY-MM',
                choosefun: function(elem, val, date){
        
                    end.minDate = val; //开始日选好后，重置结束日的最小日期
                }
            };	
            end={
                trigger:"", 
                isTime:true,
                format:'YYYY-MM', //最大日期
                choosefun: function(elem, val, date){
        
                    start.maxDate = val; //将结束日的初始值设定为开始日的最大日期
                }	
            }
        }else if(dataVal=='year'){
            start={
                trigger:"", 
                isTime:true,
                format:'YYYY',
                choosefun: function(elem, val, date){
        
                    end.minDate = val; //开始日选好后，重置结束日的最小日期
                }
            };	
            end={
                trigger:"", 
                isTime:true,
                format:'YYYY', //最大日期
                choosefun: function(elem, val, date){
        
                    start.maxDate = val; //将结束日的初始值设定为开始日的最大日期
                }	
            }
        }
        $('[name="nice-select"] ul').hide();
        e.stopPropagation();
    });
    $(document).click(function(){
        $('[name="nice-select"] ul').hide();
    });
    
    
    $('#tser01').on('click',function(){
        $('#tser01').jeDate(start);
    });
    $('#tser02').on('click',function(){
        $('#tser02').jeDate(end);
    }); 
    var dateType=$('#dateType').val();   
    smsbillUpData(dateType,1);
    
    $('.cx').on('click',function(){
    	var startTime=new Date($('#tser01').val());
    	var endTime=new Date($('#tser02').val());
    	if(startTime>endTime){
    		$('#error').show();
    		$('#tser01').val('');
    		$('#tser02').val('');
    		setTimeout(function(){
    			$('#error').hide();	
    		},2000);
    		return;
    	}
    	if($(this).hasClass('active'))return;
    	$(this).addClass('active');
    	var dateType=$('#dateType').val(); 
    	smsbillUpData(dateType,1);
    });
});
function smsbillUpData(dateType,pageNo){
	$('.trtwo').remove();
	if($('#tser01').val()==''&&$('#tser02').val()==''){
		$.ajax({
			url:'${ctx}/shopData/reportList',
			type:'post',
			data:{
				pageNo:pageNo,
				dateType:dateType
			},
			success:function(data){
				
				var res=$.parseJSON(data);
				var aTrTwo=$('<tr class="trtwo"></tr/>');
				aTrTwo.html('<td  colspan="3"><img src="${ctx}/crm/images/yu-jiazai.gif"></td>');
        		$('.smsbillTable').append(aTrTwo);
              	showSmsbillData(res);
              	$('.cx').removeClass('active');
			}
        })
    }else{
		$.ajax({
              type:'post',
              url:'${ctx}/shopData/reportList',
              data:{
                  dateType:dateType,
                  pageNo:pageNo,
                  bTimeStr:$('#tser01').val(),
                  eTimeStr:$('#tser02').val()
              },
              success:function(data){
            	$('.cx').removeClass('active');
              	var res=$.parseJSON(data);
              	var aTrTwo=$('<tr class="trtwo"></tr/>');
        		aTrTwo.html('<td  colspan="3"><img src="${ctx}/crm/images/yu-jiazai.gif"></td>');
        		$('.smsbillTable').append(aTrTwo);
              	showSmsbillData(res);
              	
  				
              }
          });
     
        
    }
}
function showSmsbillData(res){
	var len=res.dataList.length;
	$('.trtwo').remove();
	var dataType=$('#dateType').val();
	if(len==0){
		var aTrTwo=$('<tr class="trtwo"></tr/>');
		aTrTwo.html('<td  colspan="3">暂无数据</td>');
		$('.smsbillTable').append(aTrTwo);
		return;
	}
	for(var i=0;i<len;i++){
		var aTrTwo=$('<tr class="trtwo"></tr/>');
		aTrTwo.html(
			'<td>'+res.dataList[i].date+'</td>'
			+'<td>'+res.dataList[i].smsNum+'</td>'
			+'<td><a href="${ctx}/crms/shopData/smsSendRecord/list?dateType='+dataType+'&beginTime='+res.dataList[i].date+'&oneDay=2">查看</a></td>'
		)
		$('.smsbillTable').append(aTrTwo);
	}
	$('.smsNum').text(res.totalCount);
	
	if(res.totalPage>1){
		laypage({
	        cont: $('#smsbillTablePage'), //容器。值支持id名、原生dom对象，jquery对象,
	        pages: res.totalPage, //总页数
	        skip: true, //是否开启跳页
	        curr:res.pageNo,
	        skin: '#2d8cf0',
	        groups: 3, //连续显示分页数
	        jump: function(obj, first){ //触发分页后的回调
	        	if(!first){
	        		var dateType=$('#dateType').val(); 
	        		smsbillUpData(dateType,obj.curr);
	        	}
	        }
	    });
	}else{
		$('#smsbillTablePage *').remove();
	}
}
</script>
</html>