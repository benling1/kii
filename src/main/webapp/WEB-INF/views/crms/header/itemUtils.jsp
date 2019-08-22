<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%@ include file="/common/common.jsp"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<!--选择商品弹出框start-->
                <div class="rgba_000_5 w1920 h100_ ChoiceSpecified_link  display_none" style="z-index:15;">
                    <div class="w1000 h580 p_b33 bgc_fff m_t100 m_l300 modelBody">
                        <div class="w936 h546 margin0_auto bgc_fff">
                            <p class="text-center p_t20 p_b40 font16">选择商品</p>
                            <form class="font14" action="#">
                                <p class="f_l">商品ID:<input id="itemId_input_link" class="h50 w240 border0 outline_none b_radius5 m_l15 m_r20 bgc_f4f6fa" onkeyup="this.value=this.value.replace(/\D/g, '')" onblur="this.value=this.value.replace(/\D/g, '')" /></p>
                                <p class="f_l">关键字:<input id="itemName_input_link" class="h50 w240 border0 outline_none b_radius5 m_l15 m_r18 bgc_f4f6fa"/></p>
                                <p class="f_l">
                                    <div id="putaway_div_link" class="m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ VIPGXK_link m_t17">
                    				</div>
                    				<input id="status_input_link" type="hidden"  value="2"/> 
                    				<div class="m_l10 f_l font14 c_384856 lh50">
                       					只显示上架<input onclick="findItemByDataLink();" class="h40 w80 border0 outline_none b_radius5 m_l35 bgc_00a0e9 c_fff cursor_"  type="button" value="搜索"/>
                    				</div>
                                </p>
                            </form>
                            <div class="clear"></div>
                            <div class="h300 overflow_auto">
                             <table border="0" class="font14 m_t13 item_table_link">
                                    <tr class="bgc_e3e7f0 item_tr_link">
                                       <th class="w80 h60 text-center" style="width:8vw;">
							             	<div class="f_l 1check_box ">
												<div
													class="cursor_ m_t10 1check_box_1 w20 h20 border_d9dde5 b_radius5 f_l all_check_1" style='margin-left:1.4vw;'>
												</div>
											<!-- 	class="w20 h20 bgc_e0e6ef b_radius5 cursor_ item_check_link item_check_link_1" -->
												<div class="m_l20 cursor_ f_l font18 c_384856 quanxuan" style="margin-top:0.3vw;"> 全选</div>
											</div>                           
                                       </th>
                                        <th style="width:8vw;" class=" h60 text-center" >图片</th>
                                        <th style="width:35vw;" class=" h60 text-center">宝贝名称</th>
                                        <th style="width:8vw;" class=" h60 text-center">金额</th>
                                    </tr>
                             </table>
                             <img class="sdg" src="${ctx}/crm/images/yu-jiazai.gif" style="display: none">
                            </div>
                            
                            <!--确定保存start-->

                                <div class="w936 h42 m_t50 margin0_auto">
                                    <div class="w214 margin0_auto">
                                        <div onclick="addItemIdLink();" class="w100 h42 lh42  b_radius5 bgc_00a0e9 c_fff text-center f_l cursor_ Qita">
                                            确定
                                        </div>
                                        <div class="w100 h42 lh42 b_radius5 bor_cad3df c_cad3df text-center m_l10 f_l cursor_ SpecifiedOut_link">
                                            取消
                                        </div>
                                    </div>
                                </div>

                            <!--确定保存end-->
                        </div>

					<input type="hidden" id="inputName"/>
                    </div>
                </div>
<!--选择商品弹出框end-->
</body>
<script type="text/javascript">

//获取商品id赋值的input名字
function getItemInputName(name){
	$("#itemId_input_link").val('');//获取商品ID
	$("#itemName_input_link").val('');//关键字(商品名称)
	$("#status_input_link").val('2');//获取只显示上架
	$(".VIPGXK_link").removeClass('bgc_check_blue');
	$("#inputName").val(name);
	findItemByDataLink();
	// 禁止滚动条
	$(document.body).css({
	    "overflow-x":"hidden",
	    "overflow-y":"hidden"
	  });
	$(".ChoiceSpecified_link").show();
}

//生成短网址的商品链接========================
//上架按钮样式
$(".VIPGXK_link").click(function(){
	$(this).toggleClass("bgc_check_blue");
	//只显示上架设置内容
	var shieldNum = $("#status_input_link").val();
	if(shieldNum == 2){
		$("#status_input_link").val('1');
	}else{
		$("#status_input_link").val('2');
	}
});
//显示窗口
$(".AddSpecified_link").click(function(){
	$("#itemId_input_link").val('');//获取商品ID
	$("#itemName_input_link").val('');//关键字(商品名称)
	$("#status_input_link").val('2');//获取只显示上架
	$(".VIPGXK_link").removeClass('bgc_check_blue');
	$("#inputName").val('');
	//屏蔽全选按钮
	$(".all_check_1").hide();
	$(".quanxuan").text("选项");
	findItemByDataLink();
	// 禁止滚动条
	$(document.body).css({
	    "overflow-x":"hidden",
	    "overflow-y":"hidden"
	  });
	$(".ChoiceSpecified_link").show();
});
//隐藏窗口
$(".SpecifiedOut_link").click(function(){
	// 启用滚动条
 $(document.body).css({
	  "overflow-x":"auto",
	  "overflow-y":"auto"
	  });
	$(".ChoiceSpecified_link").hide();	
});

//使用ajax通过查询条件查询商品信息
 var clickFlag = false;
function findItemByDataLink(){
		if(clickFlag==true){
			return;
		}
		clickFlag = true;
		
		$('.item_tr_link').siblings().remove();
		$(".sdg").show();
		var itemId = $("#itemId_input_link").val();//获取商品ID
		var itemName = $("#itemName_input_link").val();//关键字(商品名称)
		var status = $("#status_input_link").val();//获取只显示上架

		
		var url = "${ctx}/item/queryItem";
		$.post(url,{"commodityId":itemId,"name":itemName,"status":status},function (data) {
			clickFlag = false;
			$(".sdg").hide();
				var item = data.itemList;
				var item;
				var imgUrl = null;
				if(item ==null || item=="undefined" || item=='' ){
					item = "<tr><td class='w75 text-center' align='center' colspan='3'>暂时没有相关数据!</td></tr>";
					$('.item_table_link').append(item);
				}else{
					$.each(item,function(i,result){
						if(result.url==null){
							imgUrl =imgUrl= "<img style='width:4.17vw;height:4.17vw;' src='${ctx}/crm/images/no.png'>";
						}else{
							imgUrl= "<img style='width:4.17vw;height:4.17vw;' "+"src='"+result.url+"'>";
						}
						 item = "<tr><td class='h60 text-center'><div style='margin-left:1.4vw;' class='m_t10 w20 h20 bgc_e0e6ef b_radius5 f_l cursor_ m_t17 item_check_link item_check_link_1'>"
								+"<input type='hidden' value='"+result.numIid+"'/></div></td>"
								+"<td class='h60 text-center'>"
								+imgUrl
								+"</td><td class='h60 text-center'>"+result.title+"</td><td class='h60 text-center'>"+result.price+"</td></tr>"; 
						 
						$('.item_table_link').append(item);
						
					});
					checkItemShow();
				}
			 	var a = $(".item_table_link").children().find(".bgc_check_blue").length;
			 	var b = $(".item_table_link").children().find("tr").length*1-1;
				if( a==b ){
					$(".item_table_link").children().find(".all_check_1").addClass("bgc_check_blue");
				}else{
					$(".item_table_link").children().find(".all_check_1").removeClass("bgc_check_blue");
				};
		});
} 





//商品的选择为单选
$(document).on("click", ".item_check_link", function () {
	var inputName = $("#inputName").val();
	if(inputName != null && inputName != ''){
		var a = $(".item_table_link").children().find(".bgc_check_blue").length;
	 	var b = $(".item_table_link").children().find("tr").length*1-1;
		if($(this).hasClass("bgc_check_blue")){
			$(this).removeClass("bgc_check_blue");
			$(".item_table_link").children().find(".all_check_1").removeClass("bgc_check_blue");
		}else{
			if(   a+1==b   ){
				$(this).addClass("bgc_check_blue");
				$(".item_table_link").children().find(".all_check_1").addClass("bgc_check_blue");
			}else{
				$(this).addClass("bgc_check_blue");
			}
		};
	}else{
		$(this).toggleClass("bgc_check_blue");
		$(this).parent().parent().nextAll().children().children(".item_check_link").removeClass("bgc_check_blue");
		$(this).parent().parent().prevAll().children().children(".item_check_link").removeClass("bgc_check_blue");
	}
	
});
	


	
	


$(".all_check_1").click(function(){
	if(!$(".all_check_1").hasClass("bgc_check_blue")){
	$(".item_check_link_1").removeClass("bgc_check_blue");
	$(".item_check_link_1").addClass("bgc_check_blue");
	$(".all_check_1").addClass("bgc_check_blue");
	}
	 else{
		 $(".all_check_1").removeClass("bgc_check_blue");
		 $(".item_check_link_1").removeClass("bgc_check_blue");
		
	}
	
	 /*  $(this).parent().next(".place_check").children(".gangaotai_ul").children(".gangaotai").children(".1check_box_1").addClass("bgc_check_blue");
	  
	  $(this).parent().next(".place_check").children(".gangaotai_ul").children(".gangaotai").nextAll(".li_").children(".1check_box_1").addClass("bgc_check_blue"); */
	 
});


//当点击确定时获得选中的商品id
function addItemIdLink(){
	var inputName = $("#inputName").val();
	var itemIds="";
	var divCheck = $(".item_check_link");
	var num=0;
	for(var i=0;i<divCheck.length;i++){
		if(divCheck.eq(i).hasClass("bgc_check_blue")){
			num++;
			 var val = divCheck.eq(i).children().val();
			 itemIds+=val+",";
		}
	}
	itemIds=itemIds.substring(0,itemIds.length-1);
	//判断商品数量
	if(num>50){
		$(".tishi_2").show();
		$(".tishi_2").children("p").text("商品最多可添加50条 ！！！")
		setTimeout(function(){ 
			$(".tishi_2").hide()
		},3000)
	}else{
		if(inputName != null && inputName != ''){
			$("#"+inputName).val(itemIds);
		}else{
			$("#commodityId").val(itemIds);
		}
		 $(document.body).css({
			  "overflow-x":"auto",
			  "overflow-y":"auto"
			  });
			$(".ChoiceSpecified_link").hide();
	}
}
//回显已选择商品选中状态
function checkItemShow(){
	var inputName = $("#inputName").val();
	if(inputName != null && inputName != ''){
		var itemidInput = $(".item_check_link");
		var itemIds = $("#appoint_ItemId").val();
		var itemIdList = itemIds.split(",");
		for(var i=0;i<itemidInput.length;i++){
			 for(var j=0;j<itemIdList.length;j++){
				if(itemidInput.eq(i).children().val()==itemIdList[j]){
					itemidInput.eq(i).addClass("bgc_check_blue");
				}
			} 
		}
	}
}
</script>
</html>