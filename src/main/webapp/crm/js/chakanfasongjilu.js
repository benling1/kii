﻿// JavaScript Document

	$(function(){
		
		$(".HuoDongMingCheng").each(function(index, element) {
			var val=$(".HuoDongMingCheng");
            if(val.eq(index).val().length==0||val.eq(index).val()==''){
				val.eq(index).siblings(".BianJi").hide();
				}
        });
		
			$(".C_XG").click(function(){
					$(this).parent().parent().siblings(".die_SZ").hide();
					$(this).parent().parent().siblings(".Live_SZ").show();
					
			});
			
//		11111111111111111111111111111111111	
		$(".XGXGXG").click(function(){
				$(this).parents(".GaoJiSheZhi").next(".DuanXinSheZhi").find(".die_SZ").hide();
				$(this).parents(".GaoJiSheZhi").next(".DuanXinSheZhi").find(".Live_SZ").show();
			});
			
			

		$(".Save").click(function(){
			

			$(this).parent().parent(".Live_SZ").hide();
			$(this).parent().parent().siblings(".die_SZ").show();




			


			
			$(".Letter").val($(".Letter11").val());


		})
		
		
		$(".GaoJiSheZhiSave").click(function(){
			$(this).parent().parent().parent(".Live_SZ").hide();
			$(this).parent().parent().parent().siblings(".die_SZ").show();
			
			$(".Time1").text($(".Time11").val());
			$(".Time2").text($(".Time22").val());
			$(".Time3").text($(".Time33").val());
			
			
			
			var ShuZiZZ=/^[0-9]*$/;



			if($(".Money11").val().match(ShuZiZZ) && $(".Money22").val().match(ShuZiZZ) && $(".Money11").val()!==("") && $(".Money22").val()!==(""))
														{$(".Money").text($(".Money11").val()+"~"+$(".Money22").val());}else{$(".Money").text("不限");}
			
			if($(".jiaoyicishu11").val().match(ShuZiZZ) && $(".jiaoyicishu22").val().match(ShuZiZZ) && $(".jiaoyicishu11").val()!==("") && $(".jiaoyicishu22").val()!==(""))
														{$(".jiaoyicishu").text($(".jiaoyicishu11").val()+"~"+$(".jiaoyicishu22").val());}else{$(".jiaoyicishu").text("不限");}										
														
			
			var lll=$(".DiQuShaiXuan").find(".bgc_check_blue").next("div").text();
			$(".DiQu").text(lll);
			var kkk=$(".DingDanLaiYuan").find(".bgc_check_blue").next("div").text();
			$(".LaiYuan").text(kkk);
			var jjj=$(".HuiYuanDengJi").find(".bgc_check_blue").next("div").text();
			$(".VipDengJi").text(jjj);
			var hhh=$(".ShangPinXuanZhe").find(".bgc_check_blue").next("div").text();
			$(".AllShangPin").text(hhh);
			var PPP=$(".DingDanZhuangTai").find(".bgc_check_blue").next("div").text();
			$(".dingdanzhuangtai").text(PPP);
			var ooo=$(".DingDanLeiXing").find(".bgc_check_blue").next("div").text();
			$(".dingdanleixing").text(ooo);
			var iii=$(".PingJiaZhuangTai").find(".bgc_check_blue").next("div").text();
			$(".pingjiazhuangtai").text(iii);
			var yyy=$(".YiFaDuanXinGuoLv").find(".bgc_check_blue").next("div").text();
			$(".yifaduanxinguolv").text(yyy);
			var ccc=$(".GUOLVTIAOJIAN").find(".bgc_check_blue").next("div").text();
			$(".guolvtiaojian").text(ccc);
			
			
			var mmm=$(".YuShouZhuangTai").find(".bgc_check_blue").next("div").text();
			$(".yushouzhuangtai").text(mmm);
			
		})
		
		
		




			$(".Cancel").click(function(){
					$(this).parent().parent().parent(".Live_SZ").hide();
					$(this).parent().parent().parent().siblings(".die_SZ").show();
			
			})
			$(".DXCancel").click(function(){
				$(this).parent().parent(".Live_SZ").hide();
				$(this).parent().parent().siblings(".die_SZ").show();
			})
			
			
			
		    /*下拉框*/
			$('[name="nice-select"]').click(function(e){
				$('[name="nice-select"]').find('ul').hide();
				$(this).find('ul').show();
				e.stopPropagation();
			});
			$('[name="nice-select"] li').hover(function(e){
				$(this).toggleClass('on');
				e.stopPropagation();
			});
			$('[name="nice-select"] li').click(function(e){
				var val = $(this).text();
				var dataVal = $(this).attr("data-value");
				$(this).parents('[name="nice-select"]').find('input').val(val);
				$('[name="nice-select"] ul').hide();
				e.stopPropagation();
			});
			$(document).click(function(){
				$('[name="nice-select"] ul').hide();
			});
			
			
			
		
		   $(".JiangZheHu").mouseover(function(){
				$(this).find("span").show();
			});
			$(".JiangZheHu").mouseout(function(){
				$(this).find("span").hide();
			});

        //点击更多start
        $("#more").mouseover(function(){
            $(".bind_ol").show();
        });
        $("#more").mouseout(function(){
            $(".bind_ol").hide();
        });
        //点击更多end

			
			
			
			
			//<!--------------  选项卡   ---------------->
			var  TABLE1=$(".TABLE1");
			var  TABLE2=$(".TABLE2");
			TABLE1.click(function(){
				 $(this).css('background','#fff').siblings(".TABLE1").css('background','#e3e7f0')
				var i=$(this).index();				
				 TABLE2.hide();
				 TABLE2.eq(i).show();
				 
				 $("#HEADER").text($(this).text());
			});
			
			
			<!-------------    勾选框    ----------------------->
			$(".GXK").click(function(){
				$(this).addClass("bgc_check_blue")
				$(this).parent().siblings().children(".GXK").removeClass("bgc_check_blue")	
			})
			
			
			
			//<!-------------    商品弹框    ----------------------->
			//		11111111111111111111111111111111111	
			$(".AddSpecified").click(function(){
				$(".ChoiceSpecified").show();
			});
			$(".XuanZePaiCheSpecified").click(function(){
				$(".ChoiceSpecified").show();
			});
			
			$(".SpecifiedOut").click(function(){
				$(".ChoiceSpecified").hide();	
			});
			
			
			$(".AllShop").click(function(){
				$(this).parent().siblings(".AddSpecified").hide();	
				$(this).parent().siblings(".qitashop").hide();
				$(this).parent().siblings(".XuanZePaiCheSpecified").hide();	 
			});
			$(".PaiChuShop").click(function(){
				$(this).parent().siblings(".AddSpecified").hide();	
				$(this).parent().siblings(".qitashop").hide();
				$(this).parent().siblings(".WeiZhiDingShop").addClass("display_none");
				$(this).parent().siblings().find(".QiTaWeiZhiDing").removeClass("bgc_check_blue");
				$(this).parent().siblings(".XuanZePaiCheSpecified").show();
				
			});
			$(".ZhiDingShop").click(function(){
				$(this).parent().siblings(".AddSpecified").show();	
				$(this).parent().siblings(".qitashop").show();
				$(this).parent().siblings(".XuanZePaiCheSpecified").hide();	
			});
			
			$(".QiTaWeiZhiDing").click(function(){
				$(this).parent().siblings(".WeiZhiDingShop").toggleClass("display_none");
				$(this).toggleClass("bgc_check_blue");
			});
			
			$(".DeleteQuxiao").click(function(){
				$(".WeiZhiDingShop").addClass("display_none");
				$(".QiTaWeiZhiDing").removeClass("bgc_check_blue");
			});
			
			
			
			
			
			
			
			<!-------------    链接弹框    ----------------------->
			
			
			var  TaoBaoLianJie1=$(".TaoBaoLianJie1");
			var  TaoBaoLianJie2=$(".TaoBaoLianJie2");
			TaoBaoLianJie1.click(function(){
				 $(this).css('background','#fff').siblings(".TaoBaoLianJie1").css('background','#e3e7f0')
				var i=$(this).index();				
				 TaoBaoLianJie2.hide();
				 TaoBaoLianJie2.eq(i).show();
			});
			
			
			$(".ChangeLink").click(function(){
				$(".ChoiceLink").show();
			});
			
			$(".LinkOut").click(function(){
				$(".ChoiceLink").hide();	
			});
			
			
			
			
			
			
			$(".ShopLianJie").click(function(){
				$(".ShopId").show();	
				$(".HuoDongWangZhi").hide();
			});
			$(".DianPuShouYe").click(function(){
				$(".ShopId").hide();
				$(".HuoDongWangZhi").hide();
			});
			$(".HuoDongLianJie").click(function(){
				$(".ShopId").hide();
				$(".HuoDongWangZhi").show();
			})
			
			
			//<!-------------    短语库弹框    ----------------------->
			
			$(".ClickPhrase").click(function(){
				$(".ChoicePhrase").show();	
			});
			
			$(".PhraseOut").click(function(){
				$(".ChoicePhrase").hide();	
			});
			
			
			//<!-------------    手机模型输入同步  ---------------------------->
			
			$(".text_area").keyup(function(){  
		        $(this).next().children(".text_count").text($(this).val().length);
				$(this).parents(".TABLE2").find(".text_area_copy").text($(this).val());
		   });
		   
		   
		   <!-------------    引用短语库弹框    ----------------------->
		   	var  DYK1=$(".DYK1");
			var  DYK2=$(".DYK2");
			DYK1.click(function(){
				 $(this).css('background','#fff').siblings(".DYK1").css('background','#e3e7f0')
				var i=$(this).index();				
				 DYK2.hide();
				 DYK2.eq(i).show();
			});
		   
		   
		   
		   
		   	$(".Library").click(function(){
				$(".PhraseLibrary").show();
			});
			
			$(".LibraryOut").click(function(){
				$(".PhraseLibrary").hide();
			});
					
					
					
					
			//<!-------------------    插入付款链接------------------------------------>

			$(".ChaRuFuKuanLianJian").mouseover(function(){
                $(".ShouHuoTu").show();
                $(".iphone").hide();

            })
            $(".ChaRuFuKuanLianJian").mouseout(function(){
                $(".ShouHuoTu").hide();
                $(".iphone").show();

        })
			
		
		    //<!-------------------    测试发送------------------------------------>
					var Phone=$(".Phone");
				   var phonez=/^1[3|4|5|7|8]\d{9}$/;
			$(".CheShiFaSong").click(function(){
				   
				   
				if (Phone.val()!='' && Phone.val().match(phonez)){
					alert("发送成功");
				} else{
					alert("手机号错误");
				}
			
			});
			
			
			
			//<!---------------------------    手动订单提醒    ---------------------------------------->
			$(".PiLiangCuiFu").click(function(){
					$(".DingDanZhuangTai").show();
					$(".DingDanZhuangTai").find("div.GXK").removeClass("bgc_check_blue");
					$(".DingDanZhuangTai").find("div.GXK").eq(1).addClass("bgc_check_blue");
			});
			$(".PiLiangCuiHaoPing").click(function(){
					$(".DingDanZhuangTai").show();
					$(".DingDanZhuangTai").find("div.GXK").removeClass("bgc_check_blue");
					$(".DingDanZhuangTai").find("div.GXK").eq(5).addClass("bgc_check_blue");
			});
			$(".JiaoYiGuanBiGuanHuai").click(function(){
					$(".DingDanZhuangTai").show();
					$(".DingDanZhuangTai").find("div.GXK").removeClass("bgc_check_blue");
					$(".DingDanZhuangTai").find("div.GXK").eq(7).addClass("bgc_check_blue");
			});
			$(".BiaoJiTiXing").click(function(){
					$(".DingDanZhuangTai").show();
					$(".DingDanZhuangTai").find("div.GXK").removeClass("bgc_check_blue");
					$(".DingDanZhuangTai").find("div.GXK").eq(0).addClass("bgc_check_blue");
			});
			
			
			$(".YuShouCuiWeiKuan").click(function(){	
				$(".YuShouZhuangTai").find("div.GXK").eq(2).addClass("bgc_check_blue");
				$(".YuShouZhuangTai").find("div.GXK").eq(0).removeClass("bgc_check_blue");
				$(".YuShouZhuangTai").find("div.GXK").eq(1).removeClass("bgc_check_blue");
				$(".YuShouZhuangTai").find("div.GXK").eq(3).removeClass("bgc_check_blue");
			});
		
			$(".YanShiFaHuoTiXing").click(function(){
					$(".DingDanZhuangTai").show();
					$(".DingDanZhuangTai").find("div.GXK").removeClass("bgc_check_blue");
					$(".DingDanZhuangTai").find("div.GXK").eq(0).addClass("bgc_check_blue");
			});
			
			$(".QiTa").click(function(){
					$(".DingDanZhuangTai").show();
					$(".DingDanZhuangTai").find("div.GXK").removeClass("bgc_check_blue");
					$(".DingDanZhuangTai").find("div.GXK").eq(0).addClass("bgc_check_blue");
					$(".DingDanLeiXing").find("div.GXK").eq(0).addClass("bgc_check_blue");
					$(".PingJiaZhuangTai").find("div.GXK").eq(0).addClass("bgc_check_blue");
					$(".DiQuShaiXuan").find("div.GXK").eq(0).addClass("bgc_check_blue");
					$(".ShangPingXuanZe").find("div.GXK").eq(0).addClass("bgc_check_blue");
					$(".YiFaDuanXinGuoLv").find("div.GXK").eq(0).addClass("bgc_check_blue");
			});
					
					
					
					
					
					
					
					
					
					//运单号start
					$(".ydh").mouseover(function(){
						$(this).find("span").show();
					});
					$(".ydh").mouseout(function(){
						$(this).find("span").hide();
					});
					//运单号end	
					
					
					
					
			//<!--------------    卖家标记       ------------------>
			
					$(".PingBiMaiJiaBiaoJi").click(function(){
						$(".XiaoQiQi").show();
					});
					$(".YouMaiJiaBiaoJiDouBuPingBi").click(function(){
						$(".XiaoQiQi").hide();
					});
			
			<!-------------    系统消息勾选框    ----------------------->
			//		11111111111111111111111111111111111	
			$(".XTGXK").click(function(){
				$(this).toggleClass("bgc_check_blue");
			});
								
			$(".XTXX").click(function(){
				$(this).parent().siblings("p").toggle();
				$(this).parents().siblings().find("p").hide();
				$(this).next(".border_00a0e9").remove();
			});	
										
		
			$(".YJBJ").click(function(){
				$(".XTXX").next(".border_00a0e9").remove();
			});	
		
			
			//<!-------------    卖家标记勾选框    ---------------------->
			$(".MaiJiaBiaoJiGXK").click(function(){
				$(this).toggleClass("bgc_check_blue");
			});
			
			//<!-------------    VIP勾选框    ---------------------->
			
			
			$(".ALLVIPGXK").click(function(){
				$(this).addClass("bgc_check_blue");
				$(this).parent().siblings().find(".VIPGXK").removeClass("bgc_check_blue");
			});

			
			$(document).on("click", ".VIPGXK", function () {
				$(this).toggleClass("bgc_check_blue");
				$(this).parent().siblings().find(".ALLVIPGXK").removeClass("bgc_check_blue");
			});



		//      立即开启
		$(".lijikaiqi").click(function(){

			if (   $(this).val()=="开启手动订单提醒"   ) {
				$(this).val("关闭手动订单提醒");
				$(".toggle_tishi_img").attr({"src":"images/open_sucess.png"})
				$(".toggle_tishi").show();
				setTimeout(function(){
					$(".toggle_tishi").hide()
				},1000)

			} else
			{
				$(this).val("开启手动订单提醒");
				$(".toggle_tishi_img").attr({"src":"images/close_sucess.png"})
				$(".toggle_tishi").show();
				setTimeout(function(){
					$(".toggle_tishi").hide()
				},1000)
			}
		});
		
		
		
		$(".HuoDongMingCheng").attr("disabled",true);
		
		$(".BianJi").click(function(){
			$(".HuoDongMingCheng").attr("disabled",true);
			$(this).siblings(".HuoDongMingCheng").attr("disabled",false)
		});
		
		$(".HuoDongMingCheng").blur(function(){
			$(this).attr("disabled",true);
			
			if($(this).val().length==0){$(this).siblings(".BianJi").hide()}
		});

		$(".ZhanKai").click(function(){
 			if($(".LiuCheng").css("display")=="none"){
 				$(".LiuCheng").slideDown(200);
 			}else if($(".LiuCheng").css("display")=="block") {$(".LiuCheng").slideUp(200)};
 			$(".JTS").toggle();
 			$(".JTX").toggle();
 			if($(".JTX").css("display")=="none"){$(".LiuChengText").text("收回流程")};
 			if($(".JTS").css("display")=="none"){$(".LiuChengText").text("展开流程")};
 			
 		})


	})





















